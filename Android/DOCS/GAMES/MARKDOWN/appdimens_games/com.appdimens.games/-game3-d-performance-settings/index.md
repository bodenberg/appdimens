---
title: Game3DPerformanceSettings
---
//[appdimens_games](../../../index.html)/[com.appdimens.games](../index.html)/[Game3DPerformanceSettings](index.html)



# Game3DPerformanceSettings



[androidJvm]\
data class [Game3DPerformanceSettings](index.html)(val enableHierarchicalCache: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val criticalUICacheSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 200, val normalUICacheSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 100, val gameObjectsCacheSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 50, val backgroundCacheSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 25, val enableAsyncCalculations: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val maxAsyncThreads: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 2, val asyncQueueSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 100, val enableGPUSync: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val syncMode: [SyncMode](../-sync-mode/index.html) = SyncMode.BATCHED, val enableFramePrediction: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val enableMemoryMonitoring: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val memoryPressureThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.8f, val enableAutoOptimization: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val enableAdaptiveQuality: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val targetFPS: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 60, val qualityReductionFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.1f, val prioritizeUIElements: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val enableEmergencyCleanup: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, val enableBackgroundPrecalculation: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, val fpsWarningThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 45.0f, val fpsCriticalThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 30.0f, val memoryWarningThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.7f, val memoryCriticalThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.9f)

EN 3D-specific performance settings. PT Configurações de performance específicas para 3D.



## Constructors


| | |
|---|---|
| [Game3DPerformanceSettings](-game3-d-performance-settings.html) | [androidJvm]<br>constructor(enableHierarchicalCache: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, criticalUICacheSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 200, normalUICacheSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 100, gameObjectsCacheSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 50, backgroundCacheSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 25, enableAsyncCalculations: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, maxAsyncThreads: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 2, asyncQueueSize: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 100, enableGPUSync: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, syncMode: [SyncMode](../-sync-mode/index.html) = SyncMode.BATCHED, enableFramePrediction: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, enableMemoryMonitoring: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, memoryPressureThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.8f, enableAutoOptimization: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, enableAdaptiveQuality: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, targetFPS: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 60, qualityReductionFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.1f, prioritizeUIElements: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, enableEmergencyCleanup: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, enableBackgroundPrecalculation: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false, fpsWarningThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 45.0f, fpsCriticalThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 30.0f, memoryWarningThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.7f, memoryCriticalThreshold: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.9f) |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [androidJvm]<br>object [Companion](-companion/index.html) |


## Properties


| Name | Summary |
|---|---|
| [asyncQueueSize](async-queue-size.html) | [androidJvm]<br>val [asyncQueueSize](async-queue-size.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 100 |
| [backgroundCacheSize](background-cache-size.html) | [androidJvm]<br>val [backgroundCacheSize](background-cache-size.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 25 |
| [criticalUICacheSize](critical-u-i-cache-size.html) | [androidJvm]<br>val [criticalUICacheSize](critical-u-i-cache-size.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 200 |
| [enableAdaptiveQuality](enable-adaptive-quality.html) | [androidJvm]<br>val [enableAdaptiveQuality](enable-adaptive-quality.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [enableAsyncCalculations](enable-async-calculations.html) | [androidJvm]<br>val [enableAsyncCalculations](enable-async-calculations.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [enableAutoOptimization](enable-auto-optimization.html) | [androidJvm]<br>val [enableAutoOptimization](enable-auto-optimization.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [enableBackgroundPrecalculation](enable-background-precalculation.html) | [androidJvm]<br>val [enableBackgroundPrecalculation](enable-background-precalculation.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false |
| [enableEmergencyCleanup](enable-emergency-cleanup.html) | [androidJvm]<br>val [enableEmergencyCleanup](enable-emergency-cleanup.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [enableFramePrediction](enable-frame-prediction.html) | [androidJvm]<br>val [enableFramePrediction](enable-frame-prediction.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [enableGPUSync](enable-g-p-u-sync.html) | [androidJvm]<br>val [enableGPUSync](enable-g-p-u-sync.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [enableHierarchicalCache](enable-hierarchical-cache.html) | [androidJvm]<br>val [enableHierarchicalCache](enable-hierarchical-cache.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [enableMemoryMonitoring](enable-memory-monitoring.html) | [androidJvm]<br>val [enableMemoryMonitoring](enable-memory-monitoring.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [fpsCriticalThreshold](fps-critical-threshold.html) | [androidJvm]<br>val [fpsCriticalThreshold](fps-critical-threshold.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 30.0f |
| [fpsWarningThreshold](fps-warning-threshold.html) | [androidJvm]<br>val [fpsWarningThreshold](fps-warning-threshold.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 45.0f |
| [gameObjectsCacheSize](game-objects-cache-size.html) | [androidJvm]<br>val [gameObjectsCacheSize](game-objects-cache-size.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 50 |
| [maxAsyncThreads](max-async-threads.html) | [androidJvm]<br>val [maxAsyncThreads](max-async-threads.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 2 |
| [memoryCriticalThreshold](memory-critical-threshold.html) | [androidJvm]<br>val [memoryCriticalThreshold](memory-critical-threshold.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.9f |
| [memoryPressureThreshold](memory-pressure-threshold.html) | [androidJvm]<br>val [memoryPressureThreshold](memory-pressure-threshold.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.8f |
| [memoryWarningThreshold](memory-warning-threshold.html) | [androidJvm]<br>val [memoryWarningThreshold](memory-warning-threshold.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.7f |
| [normalUICacheSize](normal-u-i-cache-size.html) | [androidJvm]<br>val [normalUICacheSize](normal-u-i-cache-size.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 100 |
| [prioritizeUIElements](prioritize-u-i-elements.html) | [androidJvm]<br>val [prioritizeUIElements](prioritize-u-i-elements.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true |
| [qualityReductionFactor](quality-reduction-factor.html) | [androidJvm]<br>val [qualityReductionFactor](quality-reduction-factor.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.1f |
| [syncMode](sync-mode.html) | [androidJvm]<br>val [syncMode](sync-mode.html): [SyncMode](../-sync-mode/index.html) |
| [targetFPS](target-f-p-s.html) | [androidJvm]<br>val [targetFPS](target-f-p-s.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) = 60 |
