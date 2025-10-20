/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - 3D Optimizations
 *
 * Description:
 * Advanced optimizations specifically designed for 3D games to address
 * performance warnings and improve GPU-CPU synchronization.
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

#ifndef GAME_3D_OPTIMIZATIONS_H
#define GAME_3D_OPTIMIZATIONS_H

#include "AppDimensGames.h"
#include "PerformanceMonitor.h"
#include "LRUCache.h"
#include <memory>
#include <thread>
#include <queue>
#include <atomic>
#include <functional>

namespace AppDimensGames3D {

// Cache priority levels for 3D games
enum class CachePriority {
    CRITICAL_UI,    // HUD, critical menus
    NORMAL_UI,      // Normal UI elements
    GAME_OBJECTS,   // Game objects
    BACKGROUND      // Background elements
};

// GPU synchronization modes
enum class SyncMode {
    IMMEDIATE,      // Immediate synchronization
    DEFERRED,       // Deferred synchronization
    BATCHED         // Batched synchronization
};

// Quality levels for adaptive quality
enum class QualityLevel {
    ULTRA,          // Maximum quality
    HIGH,           // High quality
    MEDIUM,         // Medium quality
    LOW,            // Low quality
    EMERGENCY       // Emergency mode
};

// UI element types for 3D games
enum class UIElementType {
    HUD_SCORE,
    HUD_HEALTH,
    HUD_AMMO,
    MENU_BUTTON,
    MENU_TITLE,
    TOOLTIP,
    NOTIFICATION,
    LOADING_INDICATOR
};

// Performance metrics specific to 3D games
struct Game3DPerformanceMetrics {
    float currentFPS;
    float averageFPS;
    float frameTime;
    float gpuMemoryUsage;
    float cpuMemoryUsage;
    float cacheHitRate;
    float asyncCalculationRatio;
    QualityLevel currentQualityLevel;
    bool emergencyModeActive;
    size_t activeCacheEntries;
    size_t queuedAsyncCalculations;
    
    Game3DPerformanceMetrics() : currentFPS(0.0f), averageFPS(0.0f), frameTime(0.0f),
                                gpuMemoryUsage(0.0f), cpuMemoryUsage(0.0f), cacheHitRate(0.0f),
                                asyncCalculationRatio(0.0f), currentQualityLevel(QualityLevel::HIGH),
                                emergencyModeActive(false), activeCacheEntries(0), queuedAsyncCalculations(0) {}
};

// 3D-specific performance settings
struct Game3DPerformanceSettings {
    // Hierarchical cache settings
    bool enableHierarchicalCache = true;
    size_t criticalUICacheSize = 200;
    size_t normalUICacheSize = 100;
    size_t gameObjectsCacheSize = 50;
    size_t backgroundCacheSize = 25;
    
    // Async calculation settings
    bool enableAsyncCalculations = true;
    int maxAsyncThreads = 2;
    int asyncQueueSize = 100;
    
    // GPU synchronization settings
    bool enableGPUSync = true;
    SyncMode syncMode = SyncMode::BATCHED;
    bool enableFramePrediction = true;
    
    // Memory monitoring settings
    bool enableMemoryMonitoring = true;
    float memoryPressureThreshold = 0.8f;  // 80%
    bool enableAutoOptimization = true;
    
    // Adaptive quality settings
    bool enableAdaptiveQuality = true;
    int targetFPS = 60;
    float qualityReductionFactor = 0.1f;
    
    // 3D-specific settings
    bool prioritizeUIElements = true;
    bool enableEmergencyCleanup = true;
    bool enableBackgroundPrecalculation = false;
    
    // Performance thresholds
    float fpsWarningThreshold = 45.0f;
    float fpsCriticalThreshold = 30.0f;
    float memoryWarningThreshold = 0.7f;
    float memoryCriticalThreshold = 0.9f;
};

// Dimension request for async calculations
struct DimensionRequest {
    float baseValue;
    GameDimensionType type;
    UIElementType elementType;
    CachePriority priority;
    std::function<void(float)> callback;
    int requestId;
    
    DimensionRequest() : baseValue(0.0f), type(GameDimensionType::FIXED),
                        elementType(UIElementType::MENU_BUTTON), priority(CachePriority::NORMAL_UI),
                        requestId(0) {}
};

// Hierarchical cache manager for 3D games
class Game3DCacheManager {
public:
    Game3DCacheManager();
    ~Game3DCacheManager();
    
    // Cache management
    void initialize(const Game3DPerformanceSettings& settings);
    void shutdown();
    
    // Cache operations
    float* get(CachePriority priority, const std::string& key);
    void put(CachePriority priority, const std::string& key, float value);
    void clear(CachePriority priority);
    void clearAll();
    
    // Cache configuration
    void setCacheSize(CachePriority priority, size_t maxSize);
    void setMemoryBudget(size_t totalMemoryMB);
    void enableAdaptiveCache(bool enable);
    
    // Memory management
    void clearLowPriorityCache();
    void emergencyMemoryCleanup();
    size_t getTotalMemoryUsage() const;
    float getMemoryUsagePercent() const;
    
    // Statistics
    float getCacheHitRate(CachePriority priority) const;
    size_t getCacheSize(CachePriority priority) const;
    Game3DPerformanceMetrics getCacheMetrics() const;

private:
    struct CacheInfo {
        std::unique_ptr<AppDimensGamesCache::LRUCache<std::string, float>> cache;
        size_t maxSize;
        size_t hits;
        size_t misses;
        size_t memoryUsage;
    };
    
    Game3DPerformanceSettings settings_;
    std::unordered_map<CachePriority, CacheInfo> caches_;
    size_t totalMemoryBudget_;
    bool adaptiveCacheEnabled_;
    mutable std::mutex cacheMutex_;
    
    void updateCacheStatistics(CachePriority priority, bool hit);
    void performAdaptiveCacheManagement();
};

// Async dimension calculator for 3D games
class AsyncDimensionCalculator {
public:
    AsyncDimensionCalculator();
    ~AsyncDimensionCalculator();
    
    // Initialization
    void initialize(const Game3DPerformanceSettings& settings);
    void shutdown();
    
    // Async calculations
    void calculateAsync(float baseValue, GameDimensionType type, 
                       UIElementType elementType, CachePriority priority,
                       std::function<void(float)> callback);
    
    // Batch processing
    void calculateBatch(const std::vector<DimensionRequest>& requests,
                       std::function<void(const std::vector<float>&)> callback);
    
    // Priority management
    void setCalculationPriority(const std::string& elementId, int priority);
    void clearLowPriorityQueue();
    
    // Statistics
    size_t getQueueSize() const;
    float getAsyncCalculationRatio() const;
    Game3DPerformanceMetrics getAsyncMetrics() const;

private:
    struct AsyncTask {
        DimensionRequest request;
        std::chrono::high_resolution_clock::time_point submitTime;
        int priority;
    };
    
    Game3DPerformanceSettings settings_;
    std::vector<std::thread> workerThreads_;
    std::queue<AsyncTask> taskQueue_;
    std::atomic<bool> running_;
    std::atomic<size_t> completedTasks_;
    std::atomic<size_t> totalTasks_;
    mutable std::mutex queueMutex_;
    std::condition_variable queueCondition_;
    
    void workerThreadFunction();
    void processTask(const AsyncTask& task);
    float calculateDimension(float baseValue, GameDimensionType type, UIElementType elementType);
};

// GPU resource monitor for 3D games
class GPUResourceMonitor {
public:
    GPUResourceMonitor();
    ~GPUResourceMonitor();
    
    // Initialization
    void initialize();
    void shutdown();
    
    // Memory monitoring
    size_t getGPUMemoryUsage() const;
    size_t getGPUMemoryAvailable() const;
    float getGPUMemoryUsagePercent() const;
    
    // Memory pressure handling
    void setMemoryPressureCallback(std::function<void(float)> callback);
    void enableAutoOptimization(bool enable);
    void setMemoryThreshold(float thresholdPercent);
    
    // Emergency cleanup
    void emergencyGPUCleanup();
    
    // Statistics
    Game3DPerformanceMetrics getGPUMetrics() const;

private:
    std::function<void(float)> memoryPressureCallback_;
    bool autoOptimizationEnabled_;
    float memoryThreshold_;
    mutable std::mutex monitorMutex_;
    
    void checkMemoryPressure();
    void performAutoOptimization();
};

// Render pipeline optimizer for 3D games
class RenderPipelineOptimizer {
public:
    RenderPipelineOptimizer();
    ~RenderPipelineOptimizer();
    
    // Initialization
    void initialize(const Game3DPerformanceSettings& settings);
    void shutdown();
    
    // Synchronization
    void enableGPUSync(bool enable);
    void setSyncMode(SyncMode mode);
    
    // Batching
    void beginBatch();
    void endBatch();
    void addToBatch(const DimensionRequest& request);
    
    // Frame prediction
    void preCalculateNextFrame();
    void enableFramePrediction(bool enable);
    
    // Quality management
    void setTargetFPS(int fps);
    void enableAdaptiveQuality(bool enable);
    
    // Statistics
    Game3DPerformanceMetrics getPipelineMetrics() const;

private:
    Game3DPerformanceSettings settings_;
    bool gpuSyncEnabled_;
    SyncMode syncMode_;
    bool framePredictionEnabled_;
    std::vector<DimensionRequest> currentBatch_;
    mutable std::mutex pipelineMutex_;
    
    void processBatch();
    void performFramePrediction();
};

// Adaptive quality manager for 3D games
class AdaptiveQualityManager {
public:
    AdaptiveQualityManager();
    ~AdaptiveQualityManager();
    
    // Initialization
    void initialize(const Game3DPerformanceSettings& settings);
    void shutdown();
    
    // Performance monitoring
    void updatePerformanceMetrics(float currentFPS, float frameTime, float memoryUsage);
    
    // Quality management
    void enableQualityReduction(bool enable);
    void setQualityLevels(const std::vector<QualityLevel>& levels);
    
    // Optimizations
    void reduceCacheSize(float factor);
    void disableNonCriticalFeatures();
    void enableEmergencyMode();
    
    // Recovery
    void restoreQuality();
    void enableQualityRecovery(bool enable);
    
    // Current state
    QualityLevel getCurrentQualityLevel() const;
    bool isEmergencyModeActive() const;
    
    // Statistics
    Game3DPerformanceMetrics getQualityMetrics() const;

private:
    Game3DPerformanceSettings settings_;
    QualityLevel currentQualityLevel_;
    bool emergencyModeActive_;
    bool qualityReductionEnabled_;
    bool qualityRecoveryEnabled_;
    std::vector<QualityLevel> qualityLevels_;
    float lastFPS_;
    float lastFrameTime_;
    float lastMemoryUsage_;
    std::chrono::high_resolution_clock::time_point lastQualityChange_;
    mutable std::mutex qualityMutex_;
    
    void evaluateQualityChange();
    void applyQualityLevel(QualityLevel level);
    QualityLevel determineOptimalQualityLevel();
};

// Main 3D games optimization manager
class AppDimensGames3D {
public:
    AppDimensGames3D();
    ~AppDimensGames3D();
    
    // Initialization
    void initializeFor3D(Context* context, const Game3DPerformanceSettings& settings);
    void shutdown();
    
    // Optimized calculations for 3D
    float calculateUIElement(float baseValue, UIElementType type);
    float calculateHUDElement(float baseValue, UIElementType type);
    float calculateMenuElement(float baseValue, UIElementType type);
    
    // Async calculations
    void calculateUIElementAsync(float baseValue, UIElementType type, 
                                std::function<void(float)> callback);
    
    // Emergency mode
    void enableEmergencyMode();
    void disableEmergencyMode();
    bool isEmergencyModeActive() const;
    
    // Performance monitoring
    Game3DPerformanceMetrics getPerformanceMetrics() const;
    void setPerformanceCallback(std::function<void(const Game3DPerformanceMetrics&)> callback);
    
    // Configuration
    void updateSettings(const Game3DPerformanceSettings& settings);
    Game3DPerformanceSettings getCurrentSettings() const;
    
    // Statistics and debugging
    void logPerformanceStats() const;
    void generatePerformanceReport() const;

private:
    Game3DPerformanceSettings settings_;
    std::unique_ptr<Game3DCacheManager> cacheManager_;
    std::unique_ptr<AsyncDimensionCalculator> asyncCalculator_;
    std::unique_ptr<GPUResourceMonitor> gpuMonitor_;
    std::unique_ptr<RenderPipelineOptimizer> pipelineOptimizer_;
    std::unique_ptr<AdaptiveQualityManager> qualityManager_;
    std::function<void(const Game3DPerformanceMetrics&)> performanceCallback_;
    bool initialized_;
    mutable std::mutex instanceMutex_;
    
    float calculateDimensionInternal(float baseValue, GameDimensionType type, UIElementType elementType);
    void updatePerformanceMetrics();
    void handlePerformanceCallback();
};

} // namespace AppDimensGames3D

#endif // GAME_3D_OPTIMIZATIONS_H
