/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Performance Monitor Implementation
 *
 * Description:
 * Implementation of performance monitoring and profiling utilities for game development.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "PerformanceMonitor.h"
#include <algorithm>
#include <fstream>
#include <sstream>
#include <mutex>

PerformanceMonitor::PerformanceMonitor() {
    LOGI("PerformanceMonitor constructor called");
    
    initialized = false;
    realTimeMonitoringEnabled = false;
    monitoringInterval = 1.0f; // 1 second
    maxProfileEntries = 1000;
    sampleWindowSize = 60; // 60 samples for 1 second at 60 FPS
    
    // Initialize performance thresholds
    excellentThreshold = 16.0f; // 60+ FPS
    goodThreshold = 33.0f;      // 30-60 FPS
    fairThreshold = 66.0f;      // 15-30 FPS
    poorThreshold = 100.0f;     // < 15 FPS
    
    // Initialize frame timing
    frameStartTime = std::chrono::high_resolution_clock::now();
    lastFrameTime = frameStartTime;
    currentFrameTiming.frameTime = 0.0f;
    currentFrameTiming.deltaTime = 0.0f;
    currentFrameTiming.fps = 0.0f;
    
    // Initialize performance metrics
    overallMetrics.fps = 0.0f;
    overallMetrics.frameTime = 0.0f;
    overallMetrics.cpuUsage = 0.0f;
    overallMetrics.memoryUsage = 0.0f;
    overallMetrics.drawCalls = 0;
    overallMetrics.triangles = 0;
    
    // Initialize memory info
    memoryInfo.totalMemory = 0;
    memoryInfo.usedMemory = 0;
    memoryInfo.freeMemory = 0;
    memoryInfo.peakMemory = 0;
    memoryInfo.memoryUsagePercent = 0.0f;
    
    // Initialize performance monitoring
}

PerformanceMonitor::~PerformanceMonitor() {
    LOGI("PerformanceMonitor destructor called");
    shutdown();
}

void PerformanceMonitor::initialize() {
    if (initialized) {
        LOGI("PerformanceMonitor already initialized");
        return;
    }
    
    LOGI("PerformanceMonitor initializing...");
    
    // Initialize category metrics
    for (int i = 0; i < static_cast<int>(PerformanceCategory::GENERAL) + 1; ++i) {
        CategoryMetrics metrics;
        metrics.category = static_cast<PerformanceCategory>(i);
        categoryMetrics[static_cast<PerformanceCategory>(i)] = metrics;
    }
    
    initialized = true;
    LOGI("PerformanceMonitor initialized successfully");
}

void PerformanceMonitor::shutdown() {
    if (!initialized) {
        return;
    }
    
    LOGI("PerformanceMonitor shutting down...");
    
    // Clear all data
    frameTimings.clear();
    profileEntries.clear();
    activeProfiles.clear();
    profileHistory.clear();
    categoryMetrics.clear();
    memoryHistory.clear();
    performanceAlerts.clear();
    
    initialized = false;
    LOGI("PerformanceMonitor shutdown complete");
}

void PerformanceMonitor::beginFrame() {
    if (!initialized) return;
    
    frameStartTime = std::chrono::high_resolution_clock::now();
}

void PerformanceMonitor::endFrame() {
    if (!initialized) return;
    
    auto currentTime = std::chrono::high_resolution_clock::now();
    
    // Calculate frame timing
    auto frameDuration = currentTime - frameStartTime;
    auto deltaDuration = currentTime - lastFrameTime;
    
    currentFrameTiming.frameTime = std::chrono::duration<float, std::milli>(frameDuration).count();
    currentFrameTiming.deltaTime = std::chrono::duration<float, std::milli>(deltaDuration).count();
    currentFrameTiming.fps = (currentFrameTiming.deltaTime > 0.0f) ? (1000.0f / currentFrameTiming.deltaTime) : 0.0f;
    currentFrameTiming.timestamp = currentTime;
    
    // Add to frame timings history
    frameTimings.push_back(currentFrameTiming);
    if (frameTimings.size() > sampleWindowSize) {
        frameTimings.erase(frameTimings.begin());
    }
    
    // Update overall metrics
    updateOverallMetrics();
    
    // Update memory info
    updateMemoryInfo();
    
    // Check performance alerts
    checkPerformanceAlerts();
    
    lastFrameTime = currentTime;
}

FrameTiming PerformanceMonitor::getCurrentFrameTiming() const {
    return currentFrameTiming;
}

float PerformanceMonitor::getAverageFPS() const {
    if (frameTimings.empty()) return 0.0f;
    
    float totalFPS = 0.0f;
    for (const auto& timing : frameTimings) {
        totalFPS += timing.fps;
    }
    
    return totalFPS / frameTimings.size();
}

float PerformanceMonitor::getAverageFrameTime() const {
    if (frameTimings.empty()) return 0.0f;
    
    float totalFrameTime = 0.0f;
    for (const auto& timing : frameTimings) {
        totalFrameTime += timing.frameTime;
    }
    
    return totalFrameTime / frameTimings.size();
}

void PerformanceMonitor::beginProfile(const std::string& name, PerformanceCategory category) {
    if (!initialized) return;
    
    auto now = std::chrono::high_resolution_clock::now();
    activeProfiles[name] = now;
}

void PerformanceMonitor::endProfile(const std::string& name) {
    if (!initialized) return;
    
    auto it = activeProfiles.find(name);
    if (it == activeProfiles.end()) return;
    
    auto now = std::chrono::high_resolution_clock::now();
    auto duration = now - it->second;
    float durationMs = std::chrono::duration<float, std::milli>(duration).count();
    
    // Update or create profile entry
    auto profileIt = profileEntries.find(name);
    if (profileIt == profileEntries.end()) {
        ProfileEntry entry;
        entry.name = name;
        entry.category = PerformanceCategory::GENERAL; // Default category
        entry.callCount = 0;
        entry.averageDuration = 0.0f;
        profileEntries[name] = entry;
        profileIt = profileEntries.find(name);
    }
    
    ProfileEntry& entry = profileIt->second;
    entry.callCount++;
    entry.duration = durationMs;
    entry.averageDuration = (entry.averageDuration * (entry.callCount - 1) + durationMs) / entry.callCount;
    
    // Update category metrics
    updateCategoryMetrics(entry.category, durationMs);
    
    // Add to history
    profileHistory.push_back(entry);
    if (profileHistory.size() > maxProfileEntries) {
        profileHistory.erase(profileHistory.begin());
    }
    
    activeProfiles.erase(it);
}

float PerformanceMonitor::getProfileTime(const std::string& name) const {
    auto it = profileEntries.find(name);
    return (it != profileEntries.end()) ? it->second.duration : 0.0f;
}

ProfileEntry PerformanceMonitor::getProfileEntry(const std::string& name) const {
    auto it = profileEntries.find(name);
    return (it != profileEntries.end()) ? it->second : ProfileEntry();
}

PerformanceMetrics PerformanceMonitor::getOverallMetrics() const {
    return overallMetrics;
}

CategoryMetrics PerformanceMonitor::getCategoryMetrics(PerformanceCategory category) const {
    auto it = categoryMetrics.find(category);
    return (it != categoryMetrics.end()) ? it->second : CategoryMetrics();
}

std::vector<CategoryMetrics> PerformanceMonitor::getAllCategoryMetrics() const {
    std::vector<CategoryMetrics> metrics;
    for (const auto& pair : categoryMetrics) {
        metrics.push_back(pair.second);
    }
    return metrics;
}

MemoryInfo PerformanceMonitor::getMemoryInfo() const {
    return memoryInfo;
}

void PerformanceMonitor::updateMemoryInfo() {
    // This is a simplified implementation
    // In a real implementation, you would query actual memory usage
    memoryInfo.totalMemory = 1024 * 1024 * 1024; // 1GB
    memoryInfo.usedMemory = 512 * 1024 * 1024;   // 512MB
    memoryInfo.freeMemory = memoryInfo.totalMemory - memoryInfo.usedMemory;
    memoryInfo.memoryUsagePercent = (float)memoryInfo.usedMemory / memoryInfo.totalMemory * 100.0f;
    
    if (memoryInfo.usedMemory > memoryInfo.peakMemory) {
        memoryInfo.peakMemory = memoryInfo.usedMemory;
    }
    
    // Add to history
    memoryHistory.push_back(memoryInfo);
    if (memoryHistory.size() > sampleWindowSize) {
        memoryHistory.erase(memoryHistory.begin());
    }
}

size_t PerformanceMonitor::getCurrentMemoryUsage() const {
    return memoryInfo.usedMemory;
}

size_t PerformanceMonitor::getPeakMemoryUsage() const {
    return memoryInfo.peakMemory;
}

PerformanceLevel PerformanceMonitor::getPerformanceLevel() const {
    return calculatePerformanceLevel(overallMetrics.frameTime);
}

void PerformanceMonitor::setPerformanceThresholds(float excellent, float good, float fair, float poor) {
    excellentThreshold = excellent;
    goodThreshold = good;
    fairThreshold = fair;
    poorThreshold = poor;
    
    LOGI("Performance thresholds updated: Excellent=%.1f, Good=%.1f, Fair=%.1f, Poor=%.1f",
         excellent, good, fair, poor);
}

PerformanceReport PerformanceMonitor::generateReport() const {
    PerformanceReport report;
    
    report.overallMetrics = overallMetrics;
    report.overallLevel = getPerformanceLevel();
    
    // Get category metrics
    for (const auto& pair : categoryMetrics) {
        report.categoryMetrics.push_back(pair.second);
    }
    
    report.memoryInfo = memoryInfo;
    
    // Get top slowest functions
    report.topSlowestFunctions = getTopSlowestFunctions(10);
    
    // Get recommendations
    report.recommendations = getPerformanceRecommendations();
    
    return report;
}

std::vector<std::string> PerformanceMonitor::getPerformanceRecommendations() const {
    std::vector<std::string> recommendations;
    
    PerformanceLevel level = getPerformanceLevel();
    
    switch (level) {
        case PerformanceLevel::EXCELLENT:
            recommendations.push_back("Performance is excellent. Consider adding more visual effects.");
            break;
        case PerformanceLevel::GOOD:
            recommendations.push_back("Performance is good. Monitor for any performance regressions.");
            break;
        case PerformanceLevel::FAIR:
            recommendations.push_back("Performance is fair. Consider optimizing rendering or reducing quality settings.");
            recommendations.push_back("Check for memory leaks or excessive allocations.");
            break;
        case PerformanceLevel::POOR:
            recommendations.push_back("Performance is poor. Significant optimization needed.");
            recommendations.push_back("Reduce texture quality or polygon count.");
            recommendations.push_back("Optimize shaders and reduce draw calls.");
            break;
        case PerformanceLevel::CRITICAL:
            recommendations.push_back("Performance is critical. Immediate optimization required.");
            recommendations.push_back("Disable non-essential features.");
            recommendations.push_back("Reduce screen resolution or frame rate.");
            break;
    }
    
    // Add specific recommendations based on metrics
    if (overallMetrics.memoryUsage > 80.0f) {
        recommendations.push_back("High memory usage detected. Check for memory leaks.");
    }
    
    if (overallMetrics.drawCalls > 1000) {
        recommendations.push_back("High draw call count. Consider batching or instancing.");
    }
    
    return recommendations;
}

std::vector<ProfileEntry> PerformanceMonitor::getTopSlowestFunctions(int count) const {
    std::vector<ProfileEntry> entries;
    
    for (const auto& pair : profileEntries) {
        entries.push_back(pair.second);
    }
    
    // Sort by average duration (descending)
    std::sort(entries.begin(), entries.end(), [](const ProfileEntry& a, const ProfileEntry& b) {
        return a.averageDuration > b.averageDuration;
    });
    
    // Return top N entries
    if (entries.size() > count) {
        entries.resize(count);
    }
    
    return entries;
}

void PerformanceMonitor::enableRealTimeMonitoring(bool enable) {
    realTimeMonitoringEnabled = enable;
    LOGI("Real-time monitoring %s", enable ? "enabled" : "disabled");
}

bool PerformanceMonitor::isRealTimeMonitoringEnabled() const {
    return realTimeMonitoringEnabled;
}

void PerformanceMonitor::setMonitoringInterval(float intervalSeconds) {
    monitoringInterval = intervalSeconds;
    LOGI("Monitoring interval set to %.2f seconds", intervalSeconds);
}

float PerformanceMonitor::getMonitoringInterval() const {
    return monitoringInterval;
}

void PerformanceMonitor::setPerformanceAlert(PerformanceLevel level, std::function<void()> callback) {
    performanceAlerts[level] = callback;
    LOGI("Performance alert set for level %d", static_cast<int>(level));
}

void PerformanceMonitor::clearPerformanceAlert(PerformanceLevel level) {
    auto it = performanceAlerts.find(level);
    if (it != performanceAlerts.end()) {
        performanceAlerts.erase(it);
        LOGI("Performance alert cleared for level %d", static_cast<int>(level));
    }
}

void PerformanceMonitor::checkPerformanceAlerts() {
    PerformanceLevel currentLevel = getPerformanceLevel();
    
    // Check if we should trigger an alert
    if (currentLevel == PerformanceLevel::CRITICAL || currentLevel == PerformanceLevel::POOR) {
        auto it = performanceAlerts.find(currentLevel);
        if (it != performanceAlerts.end()) {
            it->second();
        }
    }
}

std::vector<std::string> PerformanceMonitor::getOptimizationSuggestions() const {
    std::vector<std::string> suggestions;
    
    // Analyze performance bottlenecks
    analyzePerformanceBottlenecks();
    
    // Add suggestions based on analysis
    if (overallMetrics.frameTime > fairThreshold) {
        suggestions.push_back("Consider reducing texture resolution");
        suggestions.push_back("Optimize shader complexity");
        suggestions.push_back("Reduce polygon count in 3D models");
    }
    
    if (overallMetrics.memoryUsage > 70.0f) {
        suggestions.push_back("Implement texture streaming");
        suggestions.push_back("Use object pooling for frequently created objects");
        suggestions.push_back("Reduce texture memory usage");
    }
    
    return suggestions;
}

void PerformanceMonitor::analyzePerformanceBottlenecks() const {
    // This is a simplified implementation
    // In a real implementation, you would analyze the profile data more thoroughly
    
    LOGI("Analyzing performance bottlenecks...");
    
    // Check for functions that take too long
    for (const auto& pair : profileEntries) {
        const ProfileEntry& entry = pair.second;
        if (entry.averageDuration > 16.0f) { // More than 16ms
            LOGI("Slow function detected: %s (%.2f ms average)", entry.name.c_str(), entry.averageDuration);
        }
    }
    
    // Check for high call count functions
    for (const auto& pair : profileEntries) {
        const ProfileEntry& entry = pair.second;
        if (entry.callCount > 1000) { // Called more than 1000 times
            LOGI("High call count function: %s (%d calls)", entry.name.c_str(), entry.callCount);
        }
    }
}

void PerformanceMonitor::resetStatistics() {
    frameTimings.clear();
    profileEntries.clear();
    activeProfiles.clear();
    profileHistory.clear();
    memoryHistory.clear();
    
    // Reset metrics
    overallMetrics = PerformanceMetrics();
    
    // Reset category metrics
    for (auto& pair : categoryMetrics) {
        pair.second = CategoryMetrics();
        pair.second.category = pair.first;
    }
    
    LOGI("Performance statistics reset");
}

void PerformanceMonitor::saveStatistics(const std::string& filename) const {
    std::ofstream file(filename);
    if (!file.is_open()) {
        LOGE("Failed to open file for writing: %s", filename.c_str());
        return;
    }
    
    file << "Performance Statistics Report\n";
    file << "============================\n\n";
    
    file << "Overall Metrics:\n";
    file << "  FPS: " << overallMetrics.fps << "\n";
    file << "  Frame Time: " << overallMetrics.frameTime << " ms\n";
    file << "  CPU Usage: " << overallMetrics.cpuUsage << "%\n";
    file << "  Memory Usage: " << overallMetrics.memoryUsage << "%\n";
    file << "  Draw Calls: " << overallMetrics.drawCalls << "\n";
    file << "  Triangles: " << overallMetrics.triangles << "\n\n";
    
    file << "Category Metrics:\n";
    for (const auto& pair : categoryMetrics) {
        const CategoryMetrics& metrics = pair.second;
        file << "  " << getCategoryString(metrics.category) << ":\n";
        file << "    Average Time: " << metrics.averageTime << " ms\n";
        file << "    Min Time: " << metrics.minTime << " ms\n";
        file << "    Max Time: " << metrics.maxTime << " ms\n";
        file << "    Sample Count: " << metrics.sampleCount << "\n\n";
    }
    
    file << "Top Slowest Functions:\n";
    std::vector<ProfileEntry> slowest = getTopSlowestFunctions(10);
    for (const auto& entry : slowest) {
        file << "  " << entry.name << ": " << entry.averageDuration << " ms (called " << entry.callCount << " times)\n";
    }
    
    file.close();
    LOGI("Performance statistics saved to: %s", filename.c_str());
}

void PerformanceMonitor::loadStatistics(const std::string& filename) {
    // This is a simplified implementation
    // In a real implementation, you would parse the saved statistics
    LOGI("Loading performance statistics from: %s", filename.c_str());
}

void PerformanceMonitor::drawPerformanceOverlay() const {
    // This is a simplified implementation
    // In a real implementation, you would draw the overlay using the rendering system
    LOGI("Drawing performance overlay");
}

void PerformanceMonitor::logPerformanceStats() const {
    LOGI("=== Performance Statistics ===");
    LOGI("FPS: %.2f", overallMetrics.fps);
    LOGI("Frame Time: %.2f ms", overallMetrics.frameTime);
    LOGI("CPU Usage: %.2f%%", overallMetrics.cpuUsage);
    LOGI("Memory Usage: %.2f%%", overallMetrics.memoryUsage);
    LOGI("Draw Calls: %d", overallMetrics.drawCalls);
    LOGI("Triangles: %d", overallMetrics.triangles);
    LOGI("Performance Level: %s", getPerformanceLevelString(getPerformanceLevel()).c_str());
}

void PerformanceMonitor::logDetailedProfile() const {
    LOGI("=== Detailed Profile ===");
    
    for (const auto& pair : profileEntries) {
        const ProfileEntry& entry = pair.second;
        LOGI("%s: %.2f ms average (called %d times)", 
             entry.name.c_str(), entry.averageDuration, entry.callCount);
    }
}

void PerformanceMonitor::setMaxProfileEntries(int maxEntries) {
    maxProfileEntries = maxEntries;
    LOGI("Max profile entries set to: %d", maxEntries);
}

int PerformanceMonitor::getMaxProfileEntries() const {
    return maxProfileEntries;
}

void PerformanceMonitor::setSampleWindowSize(int windowSize) {
    sampleWindowSize = windowSize;
    LOGI("Sample window size set to: %d", windowSize);
}

int PerformanceMonitor::getSampleWindowSize() const {
    return sampleWindowSize;
}

bool PerformanceMonitor::isPerformanceGood() const {
    PerformanceLevel level = getPerformanceLevel();
    return level == PerformanceLevel::EXCELLENT || level == PerformanceLevel::GOOD;
}

bool PerformanceMonitor::isPerformanceCritical() const {
    return getPerformanceLevel() == PerformanceLevel::CRITICAL;
}

float PerformanceMonitor::getPerformanceScore() const {
    PerformanceLevel level = getPerformanceLevel();
    switch (level) {
        case PerformanceLevel::EXCELLENT: return 1.0f;
        case PerformanceLevel::GOOD: return 0.8f;
        case PerformanceLevel::FAIR: return 0.6f;
        case PerformanceLevel::POOR: return 0.4f;
        case PerformanceLevel::CRITICAL: return 0.2f;
        default: return 0.0f;
    }
}

// Private methods

void PerformanceMonitor::updateFrameTiming() {
    // This method is called from endFrame()
    // Implementation is already in endFrame()
}

void PerformanceMonitor::updateCategoryMetrics(PerformanceCategory category, float duration) {
    auto it = categoryMetrics.find(category);
    if (it == categoryMetrics.end()) return;
    
    CategoryMetrics& metrics = it->second;
    metrics.sampleCount++;
    metrics.totalTime += duration;
    metrics.averageTime = metrics.totalTime / metrics.sampleCount;
    
    if (metrics.sampleCount == 1) {
        metrics.minTime = duration;
        metrics.maxTime = duration;
    } else {
        metrics.minTime = std::min(metrics.minTime, duration);
        metrics.maxTime = std::max(metrics.maxTime, duration);
    }
    
    metrics.level = calculatePerformanceLevel(metrics.averageTime);
}

void PerformanceMonitor::updateOverallMetrics() {
    if (frameTimings.empty()) return;
    
    // Calculate average FPS and frame time
    float totalFPS = 0.0f;
    float totalFrameTime = 0.0f;
    
    for (const auto& timing : frameTimings) {
        totalFPS += timing.fps;
        totalFrameTime += timing.frameTime;
    }
    
    overallMetrics.fps = totalFPS / frameTimings.size();
    overallMetrics.frameTime = totalFrameTime / frameTimings.size();
    
    // Update other metrics (simplified)
    overallMetrics.cpuUsage = std::min(100.0f, overallMetrics.frameTime / 16.67f * 100.0f); // Assuming 60 FPS target
    overallMetrics.memoryUsage = memoryInfo.memoryUsagePercent;
}

void PerformanceMonitor::checkPerformanceThresholds() {
    PerformanceLevel level = getPerformanceLevel();
    LOGI("Performance level: %s", getPerformanceLevelString(level).c_str());
}

void PerformanceMonitor::cleanupOldData() {
    // Clean up old frame timings
    if (frameTimings.size() > sampleWindowSize) {
        frameTimings.erase(frameTimings.begin(), frameTimings.begin() + (frameTimings.size() - sampleWindowSize));
    }
    
    // Clean up old profile history
    if (profileHistory.size() > maxProfileEntries) {
        profileHistory.erase(profileHistory.begin(), profileHistory.begin() + (profileHistory.size() - maxProfileEntries));
    }
    
    // Clean up old memory history
    if (memoryHistory.size() > sampleWindowSize) {
        memoryHistory.erase(memoryHistory.begin(), memoryHistory.begin() + (memoryHistory.size() - sampleWindowSize));
    }
}

PerformanceLevel PerformanceMonitor::calculatePerformanceLevel(float frameTime) const {
    if (frameTime <= excellentThreshold) {
        return PerformanceLevel::EXCELLENT;
    } else if (frameTime <= goodThreshold) {
        return PerformanceLevel::GOOD;
    } else if (frameTime <= fairThreshold) {
        return PerformanceLevel::FAIR;
    } else if (frameTime <= poorThreshold) {
        return PerformanceLevel::POOR;
    } else {
        return PerformanceLevel::CRITICAL;
    }
}

std::string PerformanceMonitor::getPerformanceLevelString(PerformanceLevel level) const {
    switch (level) {
        case PerformanceLevel::EXCELLENT: return "Excellent";
        case PerformanceLevel::GOOD: return "Good";
        case PerformanceLevel::FAIR: return "Fair";
        case PerformanceLevel::POOR: return "Poor";
        case PerformanceLevel::CRITICAL: return "Critical";
        default: return "Unknown";
    }
}

std::string PerformanceMonitor::getCategoryString(PerformanceCategory category) const {
    switch (category) {
        case PerformanceCategory::RENDERING: return "Rendering";
        case PerformanceCategory::PHYSICS: return "Physics";
        case PerformanceCategory::AUDIO: return "Audio";
        case PerformanceCategory::NETWORKING: return "Networking";
        case PerformanceCategory::AI: return "AI";
        case PerformanceCategory::INPUT: return "Input";
        case PerformanceCategory::MEMORY: return "Memory";
        case PerformanceCategory::FILE_IO: return "File I/O";
        case PerformanceCategory::GENERAL: return "General";
        default: return "Unknown";
    }
}

void PerformanceMonitor::lockPerformanceData() const {
    performanceMutex.lock();
}

void PerformanceMonitor::unlockPerformanceData() const {
    performanceMutex.unlock();
}
