/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - 3D Optimizations Implementation
 *
 * Description:
 * Implementation of advanced optimizations for 3D games.
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

#include "Game3DOptimizations.h"
#include <algorithm>
#include <chrono>
#include <iostream>
#include <sstream>

namespace AppDimensGames3D {

// Game3DCacheManager Implementation
Game3DCacheManager::Game3DCacheManager() 
    : totalMemoryBudget_(100 * 1024 * 1024), // 100MB default
      adaptiveCacheEnabled_(false) {
}

Game3DCacheManager::~Game3DCacheManager() {
    shutdown();
}

void Game3DCacheManager::initialize(const Game3DPerformanceSettings& settings) {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    settings_ = settings;
    
    // Initialize caches for each priority level
    caches_[CachePriority::CRITICAL_UI] = {
        std::make_unique<AppDimensGamesCache::LRUCache<std::string, float>>(settings.criticalUICacheSize),
        settings.criticalUICacheSize, 0, 0, 0
    };
    
    caches_[CachePriority::NORMAL_UI] = {
        std::make_unique<AppDimensGamesCache::LRUCache<std::string, float>>(settings.normalUICacheSize),
        settings.normalUICacheSize, 0, 0, 0
    };
    
    caches_[CachePriority::GAME_OBJECTS] = {
        std::make_unique<AppDimensGamesCache::LRUCache<std::string, float>>(settings.gameObjectsCacheSize),
        settings.gameObjectsCacheSize, 0, 0, 0
    };
    
    caches_[CachePriority::BACKGROUND] = {
        std::make_unique<AppDimensGamesCache::LRUCache<std::string, float>>(settings.backgroundCacheSize),
        settings.backgroundCacheSize, 0, 0, 0
    };
    
    adaptiveCacheEnabled_ = settings.enableAutoOptimization;
}

void Game3DCacheManager::shutdown() {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    caches_.clear();
}

float* Game3DCacheManager::get(CachePriority priority, const std::string& key) {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    auto it = caches_.find(priority);
    if (it == caches_.end()) {
        return nullptr;
    }
    
    float* result = it->second.cache->get(key);
    updateCacheStatistics(priority, result != nullptr);
    
    return result;
}

void Game3DCacheManager::put(CachePriority priority, const std::string& key, float value) {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    auto it = caches_.find(priority);
    if (it == caches_.end()) {
        return;
    }
    
    it->second.cache->put(key, value);
    it->second.memoryUsage += sizeof(float);
    
    if (adaptiveCacheEnabled_) {
        performAdaptiveCacheManagement();
    }
}

void Game3DCacheManager::clear(CachePriority priority) {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    auto it = caches_.find(priority);
    if (it != caches_.end()) {
        it->second.cache->clear();
        it->second.hits = 0;
        it->second.misses = 0;
        it->second.memoryUsage = 0;
    }
}

void Game3DCacheManager::clearAll() {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    for (auto& pair : caches_) {
        pair.second.cache->clear();
        pair.second.hits = 0;
        pair.second.misses = 0;
        pair.second.memoryUsage = 0;
    }
}

void Game3DCacheManager::setCacheSize(CachePriority priority, size_t maxSize) {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    auto it = caches_.find(priority);
    if (it != caches_.end()) {
        it->second.cache->setMaxSize(maxSize);
        it->second.maxSize = maxSize;
    }
}

void Game3DCacheManager::setMemoryBudget(size_t totalMemoryMB) {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    totalMemoryBudget_ = totalMemoryMB * 1024 * 1024;
}

void Game3DCacheManager::enableAdaptiveCache(bool enable) {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    adaptiveCacheEnabled_ = enable;
}

void Game3DCacheManager::clearLowPriorityCache() {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    // Clear background and game objects cache first
    clear(CachePriority::BACKGROUND);
    clear(CachePriority::GAME_OBJECTS);
}

void Game3DCacheManager::emergencyMemoryCleanup() {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    // Clear all caches except critical UI
    clear(CachePriority::BACKGROUND);
    clear(CachePriority::GAME_OBJECTS);
    clear(CachePriority::NORMAL_UI);
}

size_t Game3DCacheManager::getTotalMemoryUsage() const {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    size_t total = 0;
    for (const auto& pair : caches_) {
        total += pair.second.memoryUsage;
    }
    return total;
}

float Game3DCacheManager::getMemoryUsagePercent() const {
    if (totalMemoryBudget_ == 0) return 0.0f;
    return static_cast<float>(getTotalMemoryUsage()) / totalMemoryBudget_;
}

float Game3DCacheManager::getCacheHitRate(CachePriority priority) const {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    auto it = caches_.find(priority);
    if (it == caches_.end()) return 0.0f;
    
    size_t total = it->second.hits + it->second.misses;
    if (total == 0) return 0.0f;
    
    return static_cast<float>(it->second.hits) / total;
}

size_t Game3DCacheManager::getCacheSize(CachePriority priority) const {
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    auto it = caches_.find(priority);
    if (it == caches_.end()) return 0;
    
    return it->second.cache->size();
}

Game3DPerformanceMetrics Game3DCacheManager::getCacheMetrics() const {
    Game3DPerformanceMetrics metrics;
    
    std::lock_guard<std::mutex> lock(cacheMutex_);
    
    size_t totalHits = 0, totalMisses = 0;
    for (const auto& pair : caches_) {
        totalHits += pair.second.hits;
        totalMisses += pair.second.misses;
        metrics.activeCacheEntries += pair.second.cache->size();
    }
    
    size_t total = totalHits + totalMisses;
    if (total > 0) {
        metrics.cacheHitRate = static_cast<float>(totalHits) / total;
    }
    
    metrics.cpuMemoryUsage = getMemoryUsagePercent();
    
    return metrics;
}

void Game3DCacheManager::updateCacheStatistics(CachePriority priority, bool hit) {
    auto it = caches_.find(priority);
    if (it != caches_.end()) {
        if (hit) {
            it->second.hits++;
        } else {
            it->second.misses++;
        }
    }
}

void Game3DCacheManager::performAdaptiveCacheManagement() {
    float memoryUsage = getMemoryUsagePercent();
    
    if (memoryUsage > settings_.memoryPressureThreshold) {
        // Reduce cache sizes progressively
        for (auto& pair : caches_) {
            if (pair.first != CachePriority::CRITICAL_UI) {
                size_t newSize = static_cast<size_t>(pair.second.maxSize * 0.8f);
                pair.second.cache->setMaxSize(newSize);
                pair.second.maxSize = newSize;
            }
        }
    }
}

// AsyncDimensionCalculator Implementation
AsyncDimensionCalculator::AsyncDimensionCalculator() 
    : running_(false), completedTasks_(0), totalTasks_(0) {
}

AsyncDimensionCalculator::~AsyncDimensionCalculator() {
    shutdown();
}

void AsyncDimensionCalculator::initialize(const Game3DPerformanceSettings& settings) {
    settings_ = settings;
    running_ = true;
    
    // Start worker threads
    for (int i = 0; i < settings.maxAsyncThreads; ++i) {
        workerThreads_.emplace_back(&AsyncDimensionCalculator::workerThreadFunction, this);
    }
}

void AsyncDimensionCalculator::shutdown() {
    running_ = false;
    queueCondition_.notify_all();
    
    for (auto& thread : workerThreads_) {
        if (thread.joinable()) {
            thread.join();
        }
    }
    workerThreads_.clear();
}

void AsyncDimensionCalculator::calculateAsync(float baseValue, GameDimensionType type, 
                                             UIElementType elementType, CachePriority priority,
                                             std::function<void(float)> callback) {
    if (!running_) return;
    
    DimensionRequest request;
    request.baseValue = baseValue;
    request.type = type;
    request.elementType = elementType;
    request.priority = priority;
    request.callback = callback;
    request.requestId = static_cast<int>(totalTasks_.fetch_add(1));
    
    AsyncTask task;
    task.request = request;
    task.submitTime = std::chrono::high_resolution_clock::now();
    task.priority = static_cast<int>(priority);
    
    {
        std::lock_guard<std::mutex> lock(queueMutex_);
        if (taskQueue_.size() < static_cast<size_t>(settings_.asyncQueueSize)) {
            taskQueue_.push(task);
        }
    }
    
    queueCondition_.notify_one();
}

void AsyncDimensionCalculator::calculateBatch(const std::vector<DimensionRequest>& requests,
                                             std::function<void(const std::vector<float>&)> callback) {
    if (!running_) return;
    
    std::vector<float> results;
    results.reserve(requests.size());
    
    for (const auto& request : requests) {
        float result = calculateDimension(request.baseValue, request.type, request.elementType);
        results.push_back(result);
    }
    
    if (callback) {
        callback(results);
    }
}

void AsyncDimensionCalculator::setCalculationPriority(const std::string& elementId, int priority) {
    // Implementation would track element priorities
    // For now, this is a placeholder
}

void AsyncDimensionCalculator::clearLowPriorityQueue() {
    std::lock_guard<std::mutex> lock(queueMutex_);
    
    std::queue<AsyncTask> newQueue;
    while (!taskQueue_.empty()) {
        AsyncTask task = taskQueue_.front();
        taskQueue_.pop();
        
        // Keep only high priority tasks
        if (task.priority <= static_cast<int>(CachePriority::NORMAL_UI)) {
            newQueue.push(task);
        }
    }
    
    taskQueue_ = std::move(newQueue);
}

size_t AsyncDimensionCalculator::getQueueSize() const {
    std::lock_guard<std::mutex> lock(queueMutex_);
    return taskQueue_.size();
}

float AsyncDimensionCalculator::getAsyncCalculationRatio() const {
    size_t completed = completedTasks_.load();
    size_t total = totalTasks_.load();
    
    if (total == 0) return 0.0f;
    return static_cast<float>(completed) / total;
}

Game3DPerformanceMetrics AsyncDimensionCalculator::getAsyncMetrics() const {
    Game3DPerformanceMetrics metrics;
    metrics.queuedAsyncCalculations = getQueueSize();
    metrics.asyncCalculationRatio = getAsyncCalculationRatio();
    return metrics;
}

void AsyncDimensionCalculator::workerThreadFunction() {
    while (running_) {
        AsyncTask task;
        
        {
            std::unique_lock<std::mutex> lock(queueMutex_);
            queueCondition_.wait(lock, [this] { return !taskQueue_.empty() || !running_; });
            
            if (!running_) break;
            
            task = taskQueue_.front();
            taskQueue_.pop();
        }
        
        processTask(task);
        completedTasks_.fetch_add(1);
    }
}

void AsyncDimensionCalculator::processTask(const AsyncTask& task) {
    float result = calculateDimension(task.request.baseValue, task.request.type, task.request.elementType);
    
    if (task.request.callback) {
        task.request.callback(result);
    }
}

float AsyncDimensionCalculator::calculateDimension(float baseValue, GameDimensionType type, UIElementType elementType) {
    // Simplified calculation - in real implementation, this would use the actual AppDimens algorithms
    float multiplier = 1.0f;
    
    switch (elementType) {
        case UIElementType::HUD_SCORE:
        case UIElementType::HUD_HEALTH:
        case UIElementType::HUD_AMMO:
            multiplier = 1.2f; // HUD elements slightly larger
            break;
        case UIElementType::MENU_BUTTON:
        case UIElementType::MENU_TITLE:
            multiplier = 1.0f; // Menu elements standard size
            break;
        case UIElementType::TOOLTIP:
        case UIElementType::NOTIFICATION:
            multiplier = 0.9f; // Smaller elements
            break;
        default:
            multiplier = 1.0f;
            break;
    }
    
    return baseValue * multiplier;
}

// GPUResourceMonitor Implementation
GPUResourceMonitor::GPUResourceMonitor() 
    : autoOptimizationEnabled_(false), memoryThreshold_(0.8f) {
}

GPUResourceMonitor::~GPUResourceMonitor() {
    shutdown();
}

void GPUResourceMonitor::initialize() {
    // Initialize GPU monitoring
}

void GPUResourceMonitor::shutdown() {
    // Cleanup GPU monitoring
}

size_t GPUResourceMonitor::getGPUMemoryUsage() const {
    // Simplified implementation - in real scenario, this would query actual GPU memory
    return 0; // Placeholder
}

size_t GPUResourceMonitor::getGPUMemoryAvailable() const {
    // Simplified implementation
    return 0; // Placeholder
}

float GPUResourceMonitor::getGPUMemoryUsagePercent() const {
    size_t used = getGPUMemoryUsage();
    size_t available = getGPUMemoryAvailable();
    
    if (available == 0) return 0.0f;
    return static_cast<float>(used) / (used + available);
}

void GPUResourceMonitor::setMemoryPressureCallback(std::function<void(float)> callback) {
    std::lock_guard<std::mutex> lock(monitorMutex_);
    memoryPressureCallback_ = callback;
}

void GPUResourceMonitor::enableAutoOptimization(bool enable) {
    std::lock_guard<std::mutex> lock(monitorMutex_);
    autoOptimizationEnabled_ = enable;
}

void GPUResourceMonitor::setMemoryThreshold(float thresholdPercent) {
    std::lock_guard<std::mutex> lock(monitorMutex_);
    memoryThreshold_ = thresholdPercent;
}

void GPUResourceMonitor::emergencyGPUCleanup() {
    // Implementation would perform emergency GPU cleanup
}

Game3DPerformanceMetrics GPUResourceMonitor::getGPUMetrics() const {
    Game3DPerformanceMetrics metrics;
    metrics.gpuMemoryUsage = getGPUMemoryUsagePercent();
    return metrics;
}

void GPUResourceMonitor::checkMemoryPressure() {
    float usage = getGPUMemoryUsagePercent();
    
    if (usage > memoryThreshold_ && memoryPressureCallback_) {
        memoryPressureCallback_(usage);
    }
    
    if (autoOptimizationEnabled_ && usage > memoryThreshold_) {
        performAutoOptimization();
    }
}

void GPUResourceMonitor::performAutoOptimization() {
    // Implementation would perform automatic optimizations
}

// RenderPipelineOptimizer Implementation
RenderPipelineOptimizer::RenderPipelineOptimizer() 
    : gpuSyncEnabled_(false), syncMode_(SyncMode::BATCHED), framePredictionEnabled_(false) {
}

RenderPipelineOptimizer::~RenderPipelineOptimizer() {
    shutdown();
}

void RenderPipelineOptimizer::initialize(const Game3DPerformanceSettings& settings) {
    settings_ = settings;
    gpuSyncEnabled_ = settings.enableGPUSync;
    syncMode_ = settings.syncMode;
    framePredictionEnabled_ = settings.enableFramePrediction;
}

void RenderPipelineOptimizer::shutdown() {
    // Cleanup pipeline optimizer
}

void RenderPipelineOptimizer::enableGPUSync(bool enable) {
    std::lock_guard<std::mutex> lock(pipelineMutex_);
    gpuSyncEnabled_ = enable;
}

void RenderPipelineOptimizer::setSyncMode(SyncMode mode) {
    std::lock_guard<std::mutex> lock(pipelineMutex_);
    syncMode_ = mode;
}

void RenderPipelineOptimizer::beginBatch() {
    std::lock_guard<std::mutex> lock(pipelineMutex_);
    currentBatch_.clear();
}

void RenderPipelineOptimizer::endBatch() {
    std::lock_guard<std::mutex> lock(pipelineMutex_);
    processBatch();
}

void RenderPipelineOptimizer::addToBatch(const DimensionRequest& request) {
    std::lock_guard<std::mutex> lock(pipelineMutex_);
    currentBatch_.push_back(request);
}

void RenderPipelineOptimizer::preCalculateNextFrame() {
    if (framePredictionEnabled_) {
        performFramePrediction();
    }
}

void RenderPipelineOptimizer::enableFramePrediction(bool enable) {
    std::lock_guard<std::mutex> lock(pipelineMutex_);
    framePredictionEnabled_ = enable;
}

void RenderPipelineOptimizer::setTargetFPS(int fps) {
    // Implementation would adjust timing based on target FPS
}

void RenderPipelineOptimizer::enableAdaptiveQuality(bool enable) {
    // Implementation would enable/disable adaptive quality
}

Game3DPerformanceMetrics RenderPipelineOptimizer::getPipelineMetrics() const {
    Game3DPerformanceMetrics metrics;
    return metrics;
}

void RenderPipelineOptimizer::processBatch() {
    // Process all batched requests
    for (const auto& request : currentBatch_) {
        // Process request
    }
}

void RenderPipelineOptimizer::performFramePrediction() {
    // Implementation would predict next frame requirements
}

// AdaptiveQualityManager Implementation
AdaptiveQualityManager::AdaptiveQualityManager() 
    : currentQualityLevel_(QualityLevel::HIGH), emergencyModeActive_(false),
      qualityReductionEnabled_(false), qualityRecoveryEnabled_(false),
      lastFPS_(60.0f), lastFrameTime_(16.67f), lastMemoryUsage_(0.0f) {
}

AdaptiveQualityManager::~AdaptiveQualityManager() {
    shutdown();
}

void AdaptiveQualityManager::initialize(const Game3DPerformanceSettings& settings) {
    settings_ = settings;
    qualityReductionEnabled_ = settings.enableAdaptiveQuality;
    qualityRecoveryEnabled_ = true;
    
    // Initialize quality levels
    qualityLevels_ = {
        QualityLevel::ULTRA,
        QualityLevel::HIGH,
        QualityLevel::MEDIUM,
        QualityLevel::LOW,
        QualityLevel::EMERGENCY
    };
}

void AdaptiveQualityManager::shutdown() {
    // Cleanup quality manager
}

void AdaptiveQualityManager::updatePerformanceMetrics(float currentFPS, float frameTime, float memoryUsage) {
    std::lock_guard<std::mutex> lock(qualityMutex_);
    
    lastFPS_ = currentFPS;
    lastFrameTime_ = frameTime;
    lastMemoryUsage_ = memoryUsage;
    
    if (qualityReductionEnabled_) {
        evaluateQualityChange();
    }
}

void AdaptiveQualityManager::enableQualityReduction(bool enable) {
    std::lock_guard<std::mutex> lock(qualityMutex_);
    qualityReductionEnabled_ = enable;
}

void AdaptiveQualityManager::setQualityLevels(const std::vector<QualityLevel>& levels) {
    std::lock_guard<std::mutex> lock(qualityMutex_);
    qualityLevels_ = levels;
}

void AdaptiveQualityManager::reduceCacheSize(float factor) {
    // Implementation would reduce cache sizes
}

void AdaptiveQualityManager::disableNonCriticalFeatures() {
    // Implementation would disable non-critical features
}

void AdaptiveQualityManager::enableEmergencyMode() {
    std::lock_guard<std::mutex> lock(qualityMutex_);
    emergencyModeActive_ = true;
    currentQualityLevel_ = QualityLevel::EMERGENCY;
    applyQualityLevel(QualityLevel::EMERGENCY);
}

void AdaptiveQualityManager::restoreQuality() {
    std::lock_guard<std::mutex> lock(qualityMutex_);
    emergencyModeActive_ = false;
    currentQualityLevel_ = QualityLevel::HIGH;
    applyQualityLevel(QualityLevel::HIGH);
}

void AdaptiveQualityManager::enableQualityRecovery(bool enable) {
    std::lock_guard<std::mutex> lock(qualityMutex_);
    qualityRecoveryEnabled_ = enable;
}

QualityLevel AdaptiveQualityManager::getCurrentQualityLevel() const {
    std::lock_guard<std::mutex> lock(qualityMutex_);
    return currentQualityLevel_;
}

bool AdaptiveQualityManager::isEmergencyModeActive() const {
    std::lock_guard<std::mutex> lock(qualityMutex_);
    return emergencyModeActive_;
}

Game3DPerformanceMetrics AdaptiveQualityManager::getQualityMetrics() const {
    Game3DPerformanceMetrics metrics;
    
    std::lock_guard<std::mutex> lock(qualityMutex_);
    metrics.currentQualityLevel = currentQualityLevel_;
    metrics.emergencyModeActive = emergencyModeActive_;
    metrics.currentFPS = lastFPS_;
    metrics.frameTime = lastFrameTime_;
    
    return metrics;
}

void AdaptiveQualityManager::evaluateQualityChange() {
    QualityLevel optimalLevel = determineOptimalQualityLevel();
    
    if (optimalLevel != currentQualityLevel_) {
        applyQualityLevel(optimalLevel);
        currentQualityLevel_ = optimalLevel;
        lastQualityChange_ = std::chrono::high_resolution_clock::now();
    }
}

void AdaptiveQualityManager::applyQualityLevel(QualityLevel level) {
    // Implementation would apply quality level changes
    switch (level) {
        case QualityLevel::ULTRA:
            // Apply ultra quality settings
            break;
        case QualityLevel::HIGH:
            // Apply high quality settings
            break;
        case QualityLevel::MEDIUM:
            // Apply medium quality settings
            break;
        case QualityLevel::LOW:
            // Apply low quality settings
            break;
        case QualityLevel::EMERGENCY:
            // Apply emergency settings
            break;
    }
}

QualityLevel AdaptiveQualityManager::determineOptimalQualityLevel() {
    // Determine optimal quality based on performance metrics
    if (lastFPS_ < settings_.fpsCriticalThreshold || lastMemoryUsage_ > settings_.memoryCriticalThreshold) {
        return QualityLevel::EMERGENCY;
    } else if (lastFPS_ < settings_.fpsWarningThreshold || lastMemoryUsage_ > settings_.memoryWarningThreshold) {
        return QualityLevel::LOW;
    } else if (lastFPS_ < 50.0f) {
        return QualityLevel::MEDIUM;
    } else {
        return QualityLevel::HIGH;
    }
}

// AppDimensGames3D Implementation
AppDimensGames3D::AppDimensGames3D() : initialized_(false) {
}

AppDimensGames3D::~AppDimensGames3D() {
    shutdown();
}

void AppDimensGames3D::initializeFor3D(Context* context, const Game3DPerformanceSettings& settings) {
    std::lock_guard<std::mutex> lock(instanceMutex_);
    
    settings_ = settings;
    
    // Initialize components
    cacheManager_ = std::make_unique<Game3DCacheManager>();
    cacheManager_->initialize(settings);
    
    asyncCalculator_ = std::make_unique<AsyncDimensionCalculator>();
    asyncCalculator_->initialize(settings);
    
    gpuMonitor_ = std::make_unique<GPUResourceMonitor>();
    gpuMonitor_->initialize();
    
    pipelineOptimizer_ = std::make_unique<RenderPipelineOptimizer>();
    pipelineOptimizer_->initialize(settings);
    
    qualityManager_ = std::make_unique<AdaptiveQualityManager>();
    qualityManager_->initialize(settings);
    
    initialized_ = true;
}

void AppDimensGames3D::shutdown() {
    std::lock_guard<std::mutex> lock(instanceMutex_);
    
    if (cacheManager_) {
        cacheManager_->shutdown();
        cacheManager_.reset();
    }
    
    if (asyncCalculator_) {
        asyncCalculator_->shutdown();
        asyncCalculator_.reset();
    }
    
    if (gpuMonitor_) {
        gpuMonitor_->shutdown();
        gpuMonitor_.reset();
    }
    
    if (pipelineOptimizer_) {
        pipelineOptimizer_->shutdown();
        pipelineOptimizer_.reset();
    }
    
    if (qualityManager_) {
        qualityManager_->shutdown();
        qualityManager_.reset();
    }
    
    initialized_ = false;
}

float AppDimensGames3D::calculateUIElement(float baseValue, UIElementType type) {
    return calculateDimensionInternal(baseValue, GameDimensionType::FIXED, type);
}

float AppDimensGames3D::calculateHUDElement(float baseValue, UIElementType type) {
    return calculateDimensionInternal(baseValue, GameDimensionType::FIXED, type);
}

float AppDimensGames3D::calculateMenuElement(float baseValue, UIElementType type) {
    return calculateDimensionInternal(baseValue, GameDimensionType::FIXED, type);
}

void AppDimensGames3D::calculateUIElementAsync(float baseValue, UIElementType type, 
                                              std::function<void(float)> callback) {
    if (!initialized_ || !asyncCalculator_) return;
    
    CachePriority priority = CachePriority::NORMAL_UI;
    if (type == UIElementType::HUD_SCORE || type == UIElementType::HUD_HEALTH || type == UIElementType::HUD_AMMO) {
        priority = CachePriority::CRITICAL_UI;
    }
    
    asyncCalculator_->calculateAsync(baseValue, GameDimensionType::FIXED, type, priority, callback);
}

void AppDimensGames3D::enableEmergencyMode() {
    if (qualityManager_) {
        qualityManager_->enableEmergencyMode();
    }
}

void AppDimensGames3D::disableEmergencyMode() {
    if (qualityManager_) {
        qualityManager_->restoreQuality();
    }
}

bool AppDimensGames3D::isEmergencyModeActive() const {
    if (qualityManager_) {
        return qualityManager_->isEmergencyModeActive();
    }
    return false;
}

Game3DPerformanceMetrics AppDimensGames3D::getPerformanceMetrics() const {
    Game3DPerformanceMetrics metrics;
    
    if (cacheManager_) {
        auto cacheMetrics = cacheManager_->getCacheMetrics();
        metrics.cacheHitRate = cacheMetrics.cacheHitRate;
        metrics.activeCacheEntries = cacheMetrics.activeCacheEntries;
        metrics.cpuMemoryUsage = cacheMetrics.cpuMemoryUsage;
    }
    
    if (asyncCalculator_) {
        auto asyncMetrics = asyncCalculator_->getAsyncMetrics();
        metrics.queuedAsyncCalculations = asyncMetrics.queuedAsyncCalculations;
        metrics.asyncCalculationRatio = asyncMetrics.asyncCalculationRatio;
    }
    
    if (gpuMonitor_) {
        auto gpuMetrics = gpuMonitor_->getGPUMetrics();
        metrics.gpuMemoryUsage = gpuMetrics.gpuMemoryUsage;
    }
    
    if (qualityManager_) {
        auto qualityMetrics = qualityManager_->getQualityMetrics();
        metrics.currentQualityLevel = qualityMetrics.currentQualityLevel;
        metrics.emergencyModeActive = qualityMetrics.emergencyModeActive;
        metrics.currentFPS = qualityMetrics.currentFPS;
        metrics.frameTime = qualityMetrics.frameTime;
    }
    
    return metrics;
}

void AppDimensGames3D::setPerformanceCallback(std::function<void(const Game3DPerformanceMetrics&)> callback) {
    std::lock_guard<std::mutex> lock(instanceMutex_);
    performanceCallback_ = callback;
}

void AppDimensGames3D::updateSettings(const Game3DPerformanceSettings& settings) {
    std::lock_guard<std::mutex> lock(instanceMutex_);
    settings_ = settings;
    
    // Update all components with new settings
    if (cacheManager_) {
        cacheManager_->initialize(settings);
    }
    
    if (asyncCalculator_) {
        asyncCalculator_->initialize(settings);
    }
    
    if (pipelineOptimizer_) {
        pipelineOptimizer_->initialize(settings);
    }
    
    if (qualityManager_) {
        qualityManager_->initialize(settings);
    }
}

Game3DPerformanceSettings AppDimensGames3D::getCurrentSettings() const {
    std::lock_guard<std::mutex> lock(instanceMutex_);
    return settings_;
}

void AppDimensGames3D::logPerformanceStats() const {
    auto metrics = getPerformanceMetrics();
    
    std::cout << "=== AppDimens Games 3D Performance Stats ===" << std::endl;
    std::cout << "FPS: " << metrics.currentFPS << std::endl;
    std::cout << "Frame Time: " << metrics.frameTime << "ms" << std::endl;
    std::cout << "Cache Hit Rate: " << (metrics.cacheHitRate * 100.0f) << "%" << std::endl;
    std::cout << "Active Cache Entries: " << metrics.activeCacheEntries << std::endl;
    std::cout << "Queued Async Calculations: " << metrics.queuedAsyncCalculations << std::endl;
    std::cout << "GPU Memory Usage: " << (metrics.gpuMemoryUsage * 100.0f) << "%" << std::endl;
    std::cout << "CPU Memory Usage: " << (metrics.cpuMemoryUsage * 100.0f) << "%" << std::endl;
    std::cout << "Current Quality Level: " << static_cast<int>(metrics.currentQualityLevel) << std::endl;
    std::cout << "Emergency Mode Active: " << (metrics.emergencyModeActive ? "Yes" : "No") << std::endl;
    std::cout << "===========================================" << std::endl;
}

void AppDimensGames3D::generatePerformanceReport() const {
    auto metrics = getPerformanceMetrics();
    
    std::stringstream report;
    report << "AppDimens Games 3D Performance Report\n";
    report << "=====================================\n\n";
    
    report << "Performance Metrics:\n";
    report << "- Current FPS: " << metrics.currentFPS << "\n";
    report << "- Average FPS: " << metrics.averageFPS << "\n";
    report << "- Frame Time: " << metrics.frameTime << "ms\n";
    report << "- Target FPS: " << settings_.targetFPS << "\n\n";
    
    report << "Memory Usage:\n";
    report << "- GPU Memory: " << (metrics.gpuMemoryUsage * 100.0f) << "%\n";
    report << "- CPU Memory: " << (metrics.cpuMemoryUsage * 100.0f) << "%\n";
    report << "- Memory Threshold: " << (settings_.memoryPressureThreshold * 100.0f) << "%\n\n";
    
    report << "Cache Performance:\n";
    report << "- Hit Rate: " << (metrics.cacheHitRate * 100.0f) << "%\n";
    report << "- Active Entries: " << metrics.activeCacheEntries << "\n";
    report << "- Critical UI Cache: " << settings_.criticalUICacheSize << "\n";
    report << "- Normal UI Cache: " << settings_.normalUICacheSize << "\n";
    report << "- Game Objects Cache: " << settings_.gameObjectsCacheSize << "\n";
    report << "- Background Cache: " << settings_.backgroundCacheSize << "\n\n";
    
    report << "Async Processing:\n";
    report << "- Queued Calculations: " << metrics.queuedAsyncCalculations << "\n";
    report << "- Completion Ratio: " << (metrics.asyncCalculationRatio * 100.0f) << "%\n";
    report << "- Max Threads: " << settings_.maxAsyncThreads << "\n";
    report << "- Queue Size: " << settings_.asyncQueueSize << "\n\n";
    
    report << "Quality Management:\n";
    report << "- Current Level: " << static_cast<int>(metrics.currentQualityLevel) << "\n";
    report << "- Emergency Mode: " << (metrics.emergencyModeActive ? "Active" : "Inactive") << "\n";
    report << "- Adaptive Quality: " << (settings_.enableAdaptiveQuality ? "Enabled" : "Disabled") << "\n";
    report << "- Quality Recovery: " << (settings_.enableAutoOptimization ? "Enabled" : "Disabled") << "\n\n";
    
    report << "Recommendations:\n";
    if (metrics.currentFPS < settings_.fpsWarningThreshold) {
        report << "- Consider reducing quality level\n";
    }
    if (metrics.gpuMemoryUsage > settings_.memoryWarningThreshold) {
        report << "- Monitor GPU memory usage closely\n";
    }
    if (metrics.cacheHitRate < 0.8f) {
        report << "- Consider increasing cache sizes\n";
    }
    if (metrics.queuedAsyncCalculations > settings_.asyncQueueSize * 0.8f) {
        report << "- Consider increasing async queue size\n";
    }
    
    std::cout << report.str() << std::endl;
}

float AppDimensGames3D::calculateDimensionInternal(float baseValue, GameDimensionType type, UIElementType elementType) {
    // Check cache first
    if (cacheManager_) {
        CachePriority priority = CachePriority::NORMAL_UI;
        if (elementType == UIElementType::HUD_SCORE || elementType == UIElementType::HUD_HEALTH || elementType == UIElementType::HUD_AMMO) {
            priority = CachePriority::CRITICAL_UI;
        }
        
        std::string cacheKey = std::to_string(baseValue) + "_" + std::to_string(static_cast<int>(type)) + "_" + std::to_string(static_cast<int>(elementType));
        
        float* cached = cacheManager_->get(priority, cacheKey);
        if (cached) {
            return *cached;
        }
        
        // Calculate and cache result
        float result = baseValue * 1.0f; // Simplified calculation
        cacheManager_->put(priority, cacheKey, result);
        return result;
    }
    
    // Fallback calculation
    return baseValue * 1.0f;
}

void AppDimensGames3D::updatePerformanceMetrics() {
    // Update performance metrics across all components
    if (performanceCallback_) {
        auto metrics = getPerformanceMetrics();
        performanceCallback_(metrics);
    }
}

void AppDimensGames3D::handlePerformanceCallback() {
    updatePerformanceMetrics();
}

} // namespace AppDimensGames3D
