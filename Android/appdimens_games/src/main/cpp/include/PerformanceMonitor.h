/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Performance Monitor
 *
 * Description:
 * Performance monitoring and profiling utilities for game development.
 * Provides real-time performance metrics, profiling, and optimization suggestions.
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

#ifndef PERFORMANCE_MONITOR_H
#define PERFORMANCE_MONITOR_H

#include "AppDimensGames.h"
#include <chrono>
#include <string>
#include <vector>
#include <unordered_map>
#include <memory>

// Performance categories
enum class PerformanceCategory {
    RENDERING,
    PHYSICS,
    AUDIO,
    NETWORKING,
    AI,
    INPUT,
    MEMORY,
    FILE_IO,
    GENERAL
};

// Performance levels
enum class PerformanceLevel {
    EXCELLENT,  // > 60 FPS, < 16ms frame time
    GOOD,       // 30-60 FPS, 16-33ms frame time
    FAIR,       // 15-30 FPS, 33-66ms frame time
    POOR,       // < 15 FPS, > 66ms frame time
    CRITICAL    // < 10 FPS, > 100ms frame time
};

// Performance metrics for a specific category
struct CategoryMetrics {
    PerformanceCategory category;
    float averageTime;
    float minTime;
    float maxTime;
    float totalTime;
    int sampleCount;
    PerformanceLevel level;
    
    CategoryMetrics() : category(PerformanceCategory::GENERAL), averageTime(0.0f),
                       minTime(0.0f), maxTime(0.0f), totalTime(0.0f),
                       sampleCount(0), level(PerformanceLevel::EXCELLENT) {}
};

// Frame timing data
struct FrameTiming {
    float frameTime;
    float deltaTime;
    float fps;
    std::chrono::high_resolution_clock::time_point timestamp;
    
    FrameTiming() : frameTime(0.0f), deltaTime(0.0f), fps(0.0f) {}
};

// Memory usage information
struct MemoryInfo {
    size_t totalMemory;
    size_t usedMemory;
    size_t freeMemory;
    size_t peakMemory;
    float memoryUsagePercent;
    
    MemoryInfo() : totalMemory(0), usedMemory(0), freeMemory(0),
                  peakMemory(0), memoryUsagePercent(0.0f) {}
};

// Performance profile entry
struct ProfileEntry {
    std::string name;
    PerformanceCategory category;
    float startTime;
    float endTime;
    float duration;
    int callCount;
    float averageDuration;
    
    ProfileEntry() : category(PerformanceCategory::GENERAL), startTime(0.0f),
                    endTime(0.0f), duration(0.0f), callCount(0), averageDuration(0.0f) {}
};

// Performance report
struct PerformanceReport {
    PerformanceMetrics overallMetrics;
    std::vector<CategoryMetrics> categoryMetrics;
    MemoryInfo memoryInfo;
    std::vector<ProfileEntry> topSlowestFunctions;
    std::vector<std::string> recommendations;
    PerformanceLevel overallLevel;
    
    PerformanceReport() : overallLevel(PerformanceLevel::EXCELLENT) {}
};

class PerformanceMonitor {
public:
    PerformanceMonitor();
    ~PerformanceMonitor();
    
    // Initialize performance monitoring
    void initialize();
    void shutdown();
    
    // Frame timing
    void beginFrame();
    void endFrame();
    FrameTiming getCurrentFrameTiming() const;
    float getAverageFPS() const;
    float getAverageFrameTime() const;
    
    // Profiling
    void beginProfile(const std::string& name, PerformanceCategory category = PerformanceCategory::GENERAL);
    void endProfile(const std::string& name);
    float getProfileTime(const std::string& name) const;
    ProfileEntry getProfileEntry(const std::string& name) const;
    
    // Performance metrics
    PerformanceMetrics getOverallMetrics() const;
    CategoryMetrics getCategoryMetrics(PerformanceCategory category) const;
    std::vector<CategoryMetrics> getAllCategoryMetrics() const;
    
    // Memory monitoring
    MemoryInfo getMemoryInfo() const;
    void updateMemoryInfo();
    size_t getCurrentMemoryUsage() const;
    size_t getPeakMemoryUsage() const;
    
    // Performance levels and thresholds
    PerformanceLevel getPerformanceLevel() const;
    void setPerformanceThresholds(float excellentThreshold = 16.0f, 
                                 float goodThreshold = 33.0f,
                                 float fairThreshold = 66.0f,
                                 float poorThreshold = 100.0f);
    
    // Performance analysis
    PerformanceReport generateReport() const;
    std::vector<std::string> getPerformanceRecommendations() const;
    std::vector<ProfileEntry> getTopSlowestFunctions(int count = 10) const;
    
    // Real-time monitoring
    void enableRealTimeMonitoring(bool enable);
    bool isRealTimeMonitoringEnabled() const;
    void setMonitoringInterval(float intervalSeconds);
    float getMonitoringInterval() const;
    
    // Performance alerts
    void setPerformanceAlert(PerformanceLevel level, std::function<void()> callback);
    void clearPerformanceAlert(PerformanceLevel level);
    void checkPerformanceAlerts();
    
    // Optimization suggestions
    std::vector<std::string> getOptimizationSuggestions() const;
    void analyzePerformanceBottlenecks() const;
    
    // Statistics and history
    void resetStatistics();
    void saveStatistics(const std::string& filename) const;
    void loadStatistics(const std::string& filename);
    
    // Debug and visualization
    void drawPerformanceOverlay() const;
    void logPerformanceStats() const;
    void logDetailedProfile() const;
    
    // Configuration
    void setMaxProfileEntries(int maxEntries);
    int getMaxProfileEntries() const;
    void setSampleWindowSize(int windowSize);
    int getSampleWindowSize() const;
    
    // Utility functions
    bool isPerformanceGood() const;
    bool isPerformanceCritical() const;
    float getPerformanceScore() const; // 0.0 to 1.0
    
private:
    bool initialized;
    bool realTimeMonitoringEnabled;
    float monitoringInterval;
    int maxProfileEntries;
    int sampleWindowSize;
    
    // Frame timing
    std::chrono::high_resolution_clock::time_point frameStartTime;
    std::chrono::high_resolution_clock::time_point lastFrameTime;
    std::vector<FrameTiming> frameTimings;
    FrameTiming currentFrameTiming;
    
    // Profiling
    std::unordered_map<std::string, ProfileEntry> profileEntries;
    std::unordered_map<std::string, std::chrono::high_resolution_clock::time_point> activeProfiles;
    std::vector<ProfileEntry> profileHistory;
    
    // Performance metrics
    PerformanceMetrics overallMetrics;
    std::unordered_map<PerformanceCategory, CategoryMetrics> categoryMetrics;
    
    // Memory monitoring
    MemoryInfo memoryInfo;
    std::vector<MemoryInfo> memoryHistory;
    
    // Performance thresholds
    float excellentThreshold;
    float goodThreshold;
    float fairThreshold;
    float poorThreshold;
    
    // Performance alerts
    std::unordered_map<PerformanceLevel, std::function<void()>> performanceAlerts;
    
    // Internal methods
    void updateFrameTiming();
    void updateCategoryMetrics(PerformanceCategory category, float duration);
    void updateOverallMetrics();
    void checkPerformanceThresholds();
    void cleanupOldData();
    
    // Utility functions
    PerformanceLevel calculatePerformanceLevel(float frameTime) const;
    std::string getPerformanceLevelString(PerformanceLevel level) const;
    std::string getCategoryString(PerformanceCategory category) const;
    
    // Analysis methods
    std::vector<std::string> analyzeRenderingPerformance() const;
    std::vector<std::string> analyzeMemoryPerformance() const;
    std::vector<std::string> analyzeGeneralPerformance() const;
    
    // Thread safety
    mutable std::mutex performanceMutex;
    void lockPerformanceData() const;
    void unlockPerformanceData() const;
};

#endif // PERFORMANCE_MONITOR_H
