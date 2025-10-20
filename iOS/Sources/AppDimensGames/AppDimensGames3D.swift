/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - 3D Optimizations (iOS)
 *
 * Description:
 * iOS implementation of 3D game optimizations with advanced performance management.
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

import Foundation
import Metal
import MetalKit
import simd

/**
 * [EN] Cache priority levels for 3D games.
 * [PT] Níveis de prioridade de cache para jogos 3D.
 */
public enum CachePriority {
    case criticalUI    // HUD, critical menus
    case normalUI      // Normal UI elements
    case gameObjects   // Game objects
    case background    // Background elements
}

/**
 * [EN] GPU synchronization modes.
 * [PT] Modos de sincronização GPU.
 */
public enum SyncMode {
    case immediate     // Immediate synchronization
    case deferred      // Deferred synchronization
    case batched       // Batched synchronization
}

/**
 * [EN] Quality levels for adaptive quality.
 * [PT] Níveis de qualidade para qualidade adaptativa.
 */
public enum QualityLevel {
    case ultra         // Maximum quality
    case high          // High quality
    case medium        // Medium quality
    case low           // Low quality
    case emergency     // Emergency mode
}

/**
 * [EN] UI element types for 3D games.
 * [PT] Tipos de elementos UI para jogos 3D.
 */
public enum UIElementType {
    case hudScore
    case hudHealth
    case hudAmmo
    case menuButton
    case menuTitle
    case tooltip
    case notification
    case loadingIndicator
}

/**
 * [EN] Performance metrics specific to 3D games.
 * [PT] Métricas de performance específicas para jogos 3D.
 */
public struct Game3DPerformanceMetrics {
    public let currentFPS: Float
    public let averageFPS: Float
    public let frameTime: Float
    public let gpuMemoryUsage: Float
    public let cpuMemoryUsage: Float
    public let cacheHitRate: Float
    public let asyncCalculationRatio: Float
    public let currentQualityLevel: QualityLevel
    public let emergencyModeActive: Bool
    public let activeCacheEntries: Int
    public let queuedAsyncCalculations: Int
    
    public init(
        currentFPS: Float = 0.0,
        averageFPS: Float = 0.0,
        frameTime: Float = 0.0,
        gpuMemoryUsage: Float = 0.0,
        cpuMemoryUsage: Float = 0.0,
        cacheHitRate: Float = 0.0,
        asyncCalculationRatio: Float = 0.0,
        currentQualityLevel: QualityLevel = .high,
        emergencyModeActive: Bool = false,
        activeCacheEntries: Int = 0,
        queuedAsyncCalculations: Int = 0
    ) {
        self.currentFPS = currentFPS
        self.averageFPS = averageFPS
        self.frameTime = frameTime
        self.gpuMemoryUsage = gpuMemoryUsage
        self.cpuMemoryUsage = cpuMemoryUsage
        self.cacheHitRate = cacheHitRate
        self.asyncCalculationRatio = asyncCalculationRatio
        self.currentQualityLevel = currentQualityLevel
        self.emergencyModeActive = emergencyModeActive
        self.activeCacheEntries = activeCacheEntries
        self.queuedAsyncCalculations = queuedAsyncCalculations
    }
}

/**
 * [EN] 3D-specific performance settings.
 * [PT] Configurações de performance específicas para 3D.
 */
public struct Game3DPerformanceSettings {
    // Hierarchical cache settings
    public let enableHierarchicalCache: Bool
    public let criticalUICacheSize: Int
    public let normalUICacheSize: Int
    public let gameObjectsCacheSize: Int
    public let backgroundCacheSize: Int
    
    // Async calculation settings
    public let enableAsyncCalculations: Bool
    public let maxAsyncThreads: Int
    public let asyncQueueSize: Int
    
    // GPU synchronization settings
    public let enableGPUSync: Bool
    public let syncMode: SyncMode
    public let enableFramePrediction: Bool
    
    // Memory monitoring settings
    public let enableMemoryMonitoring: Bool
    public let memoryPressureThreshold: Float
    public let enableAutoOptimization: Bool
    
    // Adaptive quality settings
    public let enableAdaptiveQuality: Bool
    public let targetFPS: Int
    public let qualityReductionFactor: Float
    
    // 3D-specific settings
    public let prioritizeUIElements: Bool
    public let enableEmergencyCleanup: Bool
    public let enableBackgroundPrecalculation: Bool
    
    // Performance thresholds
    public let fpsWarningThreshold: Float
    public let fpsCriticalThreshold: Float
    public let memoryWarningThreshold: Float
    public let memoryCriticalThreshold: Float
    
    public init(
        enableHierarchicalCache: Bool = true,
        criticalUICacheSize: Int = 200,
        normalUICacheSize: Int = 100,
        gameObjectsCacheSize: Int = 50,
        backgroundCacheSize: Int = 25,
        enableAsyncCalculations: Bool = true,
        maxAsyncThreads: Int = 2,
        asyncQueueSize: Int = 100,
        enableGPUSync: Bool = true,
        syncMode: SyncMode = .batched,
        enableFramePrediction: Bool = true,
        enableMemoryMonitoring: Bool = true,
        memoryPressureThreshold: Float = 0.8,
        enableAutoOptimization: Bool = true,
        enableAdaptiveQuality: Bool = true,
        targetFPS: Int = 60,
        qualityReductionFactor: Float = 0.1,
        prioritizeUIElements: Bool = true,
        enableEmergencyCleanup: Bool = true,
        enableBackgroundPrecalculation: Bool = false,
        fpsWarningThreshold: Float = 45.0,
        fpsCriticalThreshold: Float = 30.0,
        memoryWarningThreshold: Float = 0.7,
        memoryCriticalThreshold: Float = 0.9
    ) {
        self.enableHierarchicalCache = enableHierarchicalCache
        self.criticalUICacheSize = criticalUICacheSize
        self.normalUICacheSize = normalUICacheSize
        self.gameObjectsCacheSize = gameObjectsCacheSize
        self.backgroundCacheSize = backgroundCacheSize
        self.enableAsyncCalculations = enableAsyncCalculations
        self.maxAsyncThreads = maxAsyncThreads
        self.asyncQueueSize = asyncQueueSize
        self.enableGPUSync = enableGPUSync
        self.syncMode = syncMode
        self.enableFramePrediction = enableFramePrediction
        self.enableMemoryMonitoring = enableMemoryMonitoring
        self.memoryPressureThreshold = memoryPressureThreshold
        self.enableAutoOptimization = enableAutoOptimization
        self.enableAdaptiveQuality = enableAdaptiveQuality
        self.targetFPS = targetFPS
        self.qualityReductionFactor = qualityReductionFactor
        self.prioritizeUIElements = prioritizeUIElements
        self.enableEmergencyCleanup = enableEmergencyCleanup
        self.enableBackgroundPrecalculation = enableBackgroundPrecalculation
        self.fpsWarningThreshold = fpsWarningThreshold
        self.fpsCriticalThreshold = fpsCriticalThreshold
        self.memoryWarningThreshold = memoryWarningThreshold
        self.memoryCriticalThreshold = memoryCriticalThreshold
    }
    
    /**
     * [EN] Default performance settings optimized for 3D games.
     * [PT] Configurações de performance padrão otimizadas para jogos 3D.
     */
    public static let default3D = Game3DPerformanceSettings()
    
    /**
     * [EN] High performance settings for demanding 3D games.
     * [PT] Configurações de alta performance para jogos 3D exigentes.
     */
    public static let highPerformance3D = Game3DPerformanceSettings(
        criticalUICacheSize: 300,
        normalUICacheSize: 150,
        gameObjectsCacheSize: 100,
        backgroundCacheSize: 50,
        maxAsyncThreads: 4,
        asyncQueueSize: 200,
        targetFPS: 120,
        fpsWarningThreshold: 90.0,
        fpsCriticalThreshold: 60.0
    )
    
    /**
     * [EN] Low performance settings for simple 3D games.
     * [PT] Configurações de baixa performance para jogos 3D simples.
     */
    public static let lowPerformance3D = Game3DPerformanceSettings(
        criticalUICacheSize: 100,
        normalUICacheSize: 50,
        gameObjectsCacheSize: 25,
        backgroundCacheSize: 10,
        maxAsyncThreads: 1,
        asyncQueueSize: 50,
        targetFPS: 30,
        fpsWarningThreshold: 25.0,
        fpsCriticalThreshold: 20.0
    )
}

/**
 * [EN] Dimension request for async calculations.
 * [PT] Requisição de dimensão para cálculos assíncronos.
 */
public struct DimensionRequest {
    public let baseValue: Float
    public let elementType: UIElementType
    public let priority: CachePriority
    public let requestId: Int
    
    public init(baseValue: Float, elementType: UIElementType, priority: CachePriority, requestId: Int = 0) {
        self.baseValue = baseValue
        self.elementType = elementType
        self.priority = priority
        self.requestId = requestId
    }
}

/**
 * [EN] Main 3D games optimization manager for iOS.
 * [PT] Gerenciador principal de otimizações para jogos 3D no iOS.
 */
public class AppDimensGames3D {
    
    // MARK: - Singleton
    
    public static let shared = AppDimensGames3D()
    
    // MARK: - Private Properties
    
    private var isInitialized = false
    private var settings: Game3DPerformanceSettings = .default3D
    private var performanceCallback: ((Game3DPerformanceMetrics) -> Void)?
    
    // Cache management
    private var caches: [CachePriority: [String: Float]] = [:]
    private var cacheStats: [CachePriority: (hits: Int, misses: Int)] = [:]
    private let cacheQueue = DispatchQueue(label: "com.appdimens.games3d.cache", attributes: .concurrent)
    
    // Async processing
    private let asyncQueue = DispatchQueue(label: "com.appdimens.games3d.async", qos: .userInitiated)
    private var asyncTasks: [Int: DimensionRequest] = [:]
    private var completedTasks: Int = 0
    private var totalTasks: Int = 0
    private let asyncLock = NSLock()
    
    // Quality management
    private var currentQualityLevel: QualityLevel = .high
    private var emergencyModeActive: Bool = false
    private let qualityLock = NSLock()
    
    // Metal integration
    private var metalDevice: MTLDevice?
    private var metalCommandQueue: MTLCommandQueue?
    
    // Performance monitoring
    private var performanceTimer: Timer?
    private var lastFrameTime: CFTimeInterval = 0
    private var frameCount: Int = 0
    private var fpsHistory: [Float] = []
    
    private init() {
        initializeCaches()
    }
    
    // MARK: - Initialization
    
    /**
     * [EN] Initializes the 3D games optimization system.
     * @param settings The 3D performance settings.
     * [PT] Inicializa o sistema de otimização para jogos 3D.
     * @param settings As configurações de performance 3D.
     */
    public func initializeFor3D(settings: Game3DPerformanceSettings) {
        self.settings = settings
        
        // Initialize Metal if available
        if let device = MTLCreateSystemDefaultDevice() {
            metalDevice = device
            metalCommandQueue = device.makeCommandQueue()
        }
        
        // Initialize caches
        initializeCaches()
        
        // Start performance monitoring
        startPerformanceMonitoring()
        
        isInitialized = true
        print("AppDimens Games 3D initialized successfully")
    }
    
    /**
     * [EN] Shuts down the 3D optimization system.
     * [PT] Desliga o sistema de otimização 3D.
     */
    public func shutdown() {
        performanceTimer?.invalidate()
        performanceTimer = nil
        
        isInitialized = false
        print("AppDimens Games 3D shutdown")
    }
    
    // MARK: - Dimension Calculations
    
    /**
     * [EN] Calculates UI element dimensions optimized for 3D games.
     * @param baseValue The base dimension value.
     * @param type The UI element type.
     * @return The calculated dimension.
     * [PT] Calcula dimensões de elementos UI otimizadas para jogos 3D.
     * @param baseValue O valor base da dimensão.
     * @param type O tipo do elemento UI.
     * @return A dimensão calculada.
     */
    public func calculateUIElement(_ baseValue: Float, type: UIElementType) -> Float {
        guard isInitialized else { return baseValue }
        
        let cacheKey = "\(baseValue)_\(type)"
        let priority = getCachePriority(for: type)
        
        // Check cache first
        if let cached = getFromCache(priority: priority, key: cacheKey) {
            updateCacheStats(priority: priority, hit: true)
            return cached
        }
        
        // Calculate result
        let result = calculateDimensionInternal(baseValue: baseValue, type: type)
        
        // Cache result
        putInCache(priority: priority, key: cacheKey, value: result)
        updateCacheStats(priority: priority, hit: false)
        
        return result
    }
    
    /**
     * [EN] Calculates HUD element dimensions optimized for 3D games.
     * @param baseValue The base dimension value.
     * @param type The HUD element type.
     * @return The calculated dimension.
     * [PT] Calcula dimensões de elementos HUD otimizadas para jogos 3D.
     * @param baseValue O valor base da dimensão.
     * @param type O tipo do elemento HUD.
     * @return A dimensão calculada.
     */
    public func calculateHUDElement(_ baseValue: Float, type: UIElementType) -> Float {
        guard isInitialized else { return baseValue }
        
        let cacheKey = "hud_\(baseValue)_\(type)"
        let priority = CachePriority.criticalUI
        
        if let cached = getFromCache(priority: priority, key: cacheKey) {
            updateCacheStats(priority: priority, hit: true)
            return cached
        }
        
        let result = calculateDimensionInternal(baseValue: baseValue, type: type, multiplier: 1.2)
        putInCache(priority: priority, key: cacheKey, value: result)
        updateCacheStats(priority: priority, hit: false)
        
        return result
    }
    
    /**
     * [EN] Calculates menu element dimensions optimized for 3D games.
     * @param baseValue The base dimension value.
     * @param type The menu element type.
     * @return The calculated dimension.
     * [PT] Calcula dimensões de elementos de menu otimizadas para jogos 3D.
     * @param baseValue O valor base da dimensão.
     * @param type O tipo do elemento de menu.
     * @return A dimensão calculada.
     */
    public func calculateMenuElement(_ baseValue: Float, type: UIElementType) -> Float {
        guard isInitialized else { return baseValue }
        
        let cacheKey = "menu_\(baseValue)_\(type)"
        let priority = CachePriority.normalUI
        
        if let cached = getFromCache(priority: priority, key: cacheKey) {
            updateCacheStats(priority: priority, hit: true)
            return cached
        }
        
        let result = calculateDimensionInternal(baseValue: baseValue, type: type)
        putInCache(priority: priority, key: cacheKey, value: result)
        updateCacheStats(priority: priority, hit: false)
        
        return result
    }
    
    /**
     * [EN] Calculates UI element dimensions asynchronously.
     * @param baseValue The base dimension value.
     * @param type The UI element type.
     * @param completion The completion handler.
     * [PT] Calcula dimensões de elementos UI de forma assíncrona.
     * @param baseValue O valor base da dimensão.
     * @param type O tipo do elemento UI.
     * @param completion O handler de conclusão.
     */
    public func calculateUIElementAsync(_ baseValue: Float, type: UIElementType, completion: @escaping (Float) -> Void) {
        guard isInitialized && settings.enableAsyncCalculations else {
            completion(calculateUIElement(baseValue, type: type))
            return
        }
        
        asyncQueue.async { [weak self] in
            guard let self = self else {
                completion(baseValue)
                return
            }
            
            let result = self.calculateUIElement(baseValue, type: type)
            
            DispatchQueue.main.async {
                completion(result)
            }
        }
    }
    
    // MARK: - Emergency Mode
    
    /**
     * [EN] Enables emergency mode for performance optimization.
     * [PT] Habilita modo de emergência para otimização de performance.
     */
    public func enableEmergencyMode() {
        qualityLock.lock()
        defer { qualityLock.unlock() }
        
        emergencyModeActive = true
        currentQualityLevel = .emergency
        
        // Clear low priority caches
        clearLowPriorityCache()
        
        print("Emergency mode enabled")
    }
    
    /**
     * [EN] Disables emergency mode.
     * [PT] Desabilita modo de emergência.
     */
    public func disableEmergencyMode() {
        qualityLock.lock()
        defer { qualityLock.unlock() }
        
        emergencyModeActive = false
        currentQualityLevel = .high
        
        print("Emergency mode disabled")
    }
    
    /**
     * [EN] Checks if emergency mode is active.
     * @return True if emergency mode is active.
     * [PT] Verifica se o modo de emergência está ativo.
     * @return True se o modo de emergência estiver ativo.
     */
    public var isEmergencyModeActive: Bool {
        qualityLock.lock()
        defer { qualityLock.unlock() }
        return emergencyModeActive
    }
    
    // MARK: - Performance Monitoring
    
    /**
     * [EN] Gets current performance metrics.
     * @return The current performance metrics.
     * [PT] Obtém as métricas de performance atuais.
     * @return As métricas de performance atuais.
     */
    public func getPerformanceMetrics() -> Game3DPerformanceMetrics {
        let cacheHitRate = calculateCacheHitRate()
        let asyncRatio = calculateAsyncRatio()
        let gpuMemoryUsage = getGPUMemoryUsage()
        let cpuMemoryUsage = getCPUMemoryUsage()
        
        return Game3DPerformanceMetrics(
            currentFPS: calculateCurrentFPS(),
            averageFPS: calculateAverageFPS(),
            frameTime: calculateFrameTime(),
            gpuMemoryUsage: gpuMemoryUsage,
            cpuMemoryUsage: cpuMemoryUsage,
            cacheHitRate: cacheHitRate,
            asyncCalculationRatio: asyncRatio,
            currentQualityLevel: currentQualityLevel,
            emergencyModeActive: emergencyModeActive,
            activeCacheEntries: getActiveCacheEntries(),
            queuedAsyncCalculations: getQueuedAsyncCalculations()
        )
    }
    
    /**
     * [EN] Sets a performance callback.
     * @param callback The callback function.
     * [PT] Define um callback de performance.
     * @param callback A função de callback.
     */
    public func setPerformanceCallback(_ callback: @escaping (Game3DPerformanceMetrics) -> Void) {
        performanceCallback = callback
    }
    
    // MARK: - Configuration
    
    /**
     * [EN] Updates performance settings.
     * @param settings The new settings.
     * [PT] Atualiza as configurações de performance.
     * @param settings As novas configurações.
     */
    public func updateSettings(_ settings: Game3DPerformanceSettings) {
        self.settings = settings
        print("Performance settings updated")
    }
    
    /**
     * [EN] Gets current performance settings.
     * @return The current settings.
     * [PT] Obtém as configurações de performance atuais.
     * @return As configurações atuais.
     */
    public func getCurrentSettings() -> Game3DPerformanceSettings {
        return settings
    }
    
    // MARK: - Statistics and Debugging
    
    /**
     * [EN] Logs performance statistics.
     * [PT] Registra estatísticas de performance.
     */
    public func logPerformanceStats() {
        let metrics = getPerformanceMetrics()
        
        print("=== AppDimens Games 3D Performance Stats ===")
        print("FPS: \(metrics.currentFPS)")
        print("Frame Time: \(metrics.frameTime)ms")
        print("Cache Hit Rate: \(metrics.cacheHitRate * 100)%")
        print("Active Cache Entries: \(metrics.activeCacheEntries)")
        print("Queued Async Calculations: \(metrics.queuedAsyncCalculations)")
        print("GPU Memory Usage: \(metrics.gpuMemoryUsage * 100)%")
        print("CPU Memory Usage: \(metrics.cpuMemoryUsage * 100)%")
        print("Current Quality Level: \(metrics.currentQualityLevel)")
        print("Emergency Mode Active: \(metrics.emergencyModeActive)")
        print("===========================================")
    }
    
    /**
     * [EN] Generates a performance report.
     * @return The performance report string.
     * [PT] Gera um relatório de performance.
     * @return A string do relatório de performance.
     */
    public func generatePerformanceReport() -> String {
        let metrics = getPerformanceMetrics()
        
        var report = "AppDimens Games 3D Performance Report\n"
        report += "=====================================\n\n"
        
        report += "Performance Metrics:\n"
        report += "- Current FPS: \(metrics.currentFPS)\n"
        report += "- Average FPS: \(metrics.averageFPS)\n"
        report += "- Frame Time: \(metrics.frameTime)ms\n"
        report += "- Target FPS: \(settings.targetFPS)\n\n"
        
        report += "Memory Usage:\n"
        report += "- GPU Memory: \(metrics.gpuMemoryUsage * 100)%\n"
        report += "- CPU Memory: \(metrics.cpuMemoryUsage * 100)%\n"
        report += "- Memory Threshold: \(settings.memoryPressureThreshold * 100)%\n\n"
        
        report += "Cache Performance:\n"
        report += "- Hit Rate: \(metrics.cacheHitRate * 100)%\n"
        report += "- Active Entries: \(metrics.activeCacheEntries)\n"
        report += "- Critical UI Cache: \(settings.criticalUICacheSize)\n"
        report += "- Normal UI Cache: \(settings.normalUICacheSize)\n"
        report += "- Game Objects Cache: \(settings.gameObjectsCacheSize)\n"
        report += "- Background Cache: \(settings.backgroundCacheSize)\n\n"
        
        report += "Async Processing:\n"
        report += "- Queued Calculations: \(metrics.queuedAsyncCalculations)\n"
        report += "- Completion Ratio: \(metrics.asyncCalculationRatio * 100)%\n"
        report += "- Max Threads: \(settings.maxAsyncThreads)\n"
        report += "- Queue Size: \(settings.asyncQueueSize)\n\n"
        
        report += "Quality Management:\n"
        report += "- Current Level: \(metrics.currentQualityLevel)\n"
        report += "- Emergency Mode: \(metrics.emergencyModeActive ? "Active" : "Inactive")\n"
        report += "- Adaptive Quality: \(settings.enableAdaptiveQuality ? "Enabled" : "Disabled")\n"
        report += "- Quality Recovery: \(settings.enableAutoOptimization ? "Enabled" : "Disabled")\n\n"
        
        report += "Recommendations:\n"
        if metrics.currentFPS < settings.fpsWarningThreshold {
            report += "- Consider reducing quality level\n"
        }
        if metrics.gpuMemoryUsage > settings.memoryWarningThreshold {
            report += "- Monitor GPU memory usage closely\n"
        }
        if metrics.cacheHitRate < 0.8 {
            report += "- Consider increasing cache sizes\n"
        }
        if metrics.queuedAsyncCalculations > settings.asyncQueueSize / 2 {
            report += "- Consider increasing async queue size\n"
        }
        
        return report
    }
    
    // MARK: - Private Helper Methods
    
    private func initializeCaches() {
        caches[.criticalUI] = [:]
        caches[.normalUI] = [:]
        caches[.gameObjects] = [:]
        caches[.background] = [:]
        
        cacheStats[.criticalUI] = (hits: 0, misses: 0)
        cacheStats[.normalUI] = (hits: 0, misses: 0)
        cacheStats[.gameObjects] = (hits: 0, misses: 0)
        cacheStats[.background] = (hits: 0, misses: 0)
    }
    
    private func getCachePriority(for type: UIElementType) -> CachePriority {
        switch type {
        case .hudScore, .hudHealth, .hudAmmo:
            return .criticalUI
        case .menuButton, .menuTitle:
            return .normalUI
        case .tooltip, .notification, .loadingIndicator:
            return .background
        }
    }
    
    private func getFromCache(priority: CachePriority, key: String) -> Float? {
        return cacheQueue.sync {
            return caches[priority]?[key]
        }
    }
    
    private func putInCache(priority: CachePriority, key: String, value: Float) {
        cacheQueue.async(flags: .barrier) { [weak self] in
            guard let self = self else { return }
            
            let maxSize = self.getMaxCacheSize(for: priority)
            var cache = self.caches[priority] ?? [:]
            
            if cache.count >= maxSize {
                // Remove oldest entry (simple LRU simulation)
                if let firstKey = cache.keys.first {
                    cache.removeValue(forKey: firstKey)
                }
            }
            
            cache[key] = value
            self.caches[priority] = cache
        }
    }
    
    private func getMaxCacheSize(for priority: CachePriority) -> Int {
        switch priority {
        case .criticalUI:
            return settings.criticalUICacheSize
        case .normalUI:
            return settings.normalUICacheSize
        case .gameObjects:
            return settings.gameObjectsCacheSize
        case .background:
            return settings.backgroundCacheSize
        }
    }
    
    private func updateCacheStats(priority: CachePriority, hit: Bool) {
        cacheQueue.async(flags: .barrier) { [weak self] in
            guard let self = self else { return }
            
            let current = self.cacheStats[priority] ?? (hits: 0, misses: 0)
            self.cacheStats[priority] = hit ? 
                (hits: current.hits + 1, misses: current.misses) :
                (hits: current.hits, misses: current.misses + 1)
        }
    }
    
    private func calculateDimensionInternal(baseValue: Float, type: UIElementType, multiplier: Float = 1.0) -> Float {
        // Simplified calculation - in real implementation, this would use the actual AppDimens algorithms
        let elementMultiplier: Float
        
        switch type {
        case .hudScore, .hudHealth, .hudAmmo:
            elementMultiplier = 1.2 // HUD elements slightly larger
        case .menuButton, .menuTitle:
            elementMultiplier = 1.0 // Menu elements standard size
        case .tooltip, .notification, .loadingIndicator:
            elementMultiplier = 0.9 // Smaller elements
        }
        
        return baseValue * elementMultiplier * multiplier
    }
    
    private func clearLowPriorityCache() {
        cacheQueue.async(flags: .barrier) { [weak self] in
            self?.caches[.background]?.removeAll()
            self?.caches[.gameObjects]?.removeAll()
        }
    }
    
    private func calculateCacheHitRate() -> Float {
        return cacheQueue.sync {
            var totalHits = 0
            var totalMisses = 0
            
            for (_, stats) in cacheStats {
                totalHits += stats.hits
                totalMisses += stats.misses
            }
            
            let total = totalHits + totalMisses
            return total > 0 ? Float(totalHits) / Float(total) : 0.0
        }
    }
    
    private func calculateAsyncRatio() -> Float {
        asyncLock.lock()
        defer { asyncLock.unlock() }
        
        return totalTasks > 0 ? Float(completedTasks) / Float(totalTasks) : 0.0
    }
    
    private func getGPUMemoryUsage() -> Float {
        // Simplified implementation - in real scenario, this would query actual GPU memory
        return 0.0 // Placeholder
    }
    
    private func getCPUMemoryUsage() -> Float {
        let info = mach_task_basic_info()
        var count = mach_msg_type_number_t(MemoryLayout<mach_task_basic_info>.size)/4
        
        let kerr: kern_return_t = withUnsafeMutablePointer(to: &info) {
            $0.withMemoryRebound(to: integer_t.self, capacity: 1) {
                task_info(mach_task_self_,
                         task_flavor_t(MACH_TASK_BASIC_INFO),
                         $0,
                         &count)
            }
        }
        
        if kerr == KERN_SUCCESS {
            let usedMemory = Float(info.resident_size) / (1024 * 1024) // Convert to MB
            let totalMemory = Float(ProcessInfo.processInfo.physicalMemory) / (1024 * 1024) // Convert to MB
            return usedMemory / totalMemory
        }
        
        return 0.0
    }
    
    private func calculateCurrentFPS() -> Float {
        return fpsHistory.isEmpty ? 0.0 : fpsHistory.last ?? 0.0
    }
    
    private func calculateAverageFPS() -> Float {
        guard !fpsHistory.isEmpty else { return 0.0 }
        return fpsHistory.reduce(0, +) / Float(fpsHistory.count)
    }
    
    private func calculateFrameTime() -> Float {
        return fpsHistory.isEmpty ? 0.0 : 1000.0 / (fpsHistory.last ?? 1.0)
    }
    
    private func getActiveCacheEntries() -> Int {
        return cacheQueue.sync {
            return caches.values.reduce(0) { $0 + $1.count }
        }
    }
    
    private func getQueuedAsyncCalculations() -> Int {
        asyncLock.lock()
        defer { asyncLock.unlock() }
        return asyncTasks.count
    }
    
    private func startPerformanceMonitoring() {
        performanceTimer = Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { [weak self] _ in
            self?.updatePerformanceMetrics()
        }
    }
    
    private func updatePerformanceMetrics() {
        let currentTime = CACurrentMediaTime()
        
        if lastFrameTime > 0 {
            let deltaTime = currentTime - lastFrameTime
            let fps = 1.0 / deltaTime
            
            fpsHistory.append(Float(fps))
            if fpsHistory.count > 60 { // Keep last 60 frames
                fpsHistory.removeFirst()
            }
        }
        
        lastFrameTime = currentTime
        frameCount += 1
        
        // Call performance callback if set
        if let callback = performanceCallback {
            let metrics = getPerformanceMetrics()
            DispatchQueue.main.async {
                callback(metrics)
            }
        }
    }
}

// MARK: - SwiftUI Extensions

#if canImport(SwiftUI)
import SwiftUI

@available(iOS 13.0, *)
public extension View {
    
    /**
     * [EN] Applies 3D game-optimized dimensions to the view.
     * @param baseValue The base dimension value.
     * @param type The UI element type.
     * @return The view with 3D-optimized dimensions.
     * [PT] Aplica dimensões otimizadas para jogos 3D à view.
     * @param baseValue O valor base da dimensão.
     * @param type O tipo do elemento UI.
     * @return A view com dimensões otimizadas para 3D.
     */
    func game3DSize(_ baseValue: CGFloat, type: UIElementType) -> some View {
        let dimension = AppDimensGames3D.shared.calculateUIElement(Float(baseValue), type: type)
        return self.frame(width: CGFloat(dimension), height: CGFloat(dimension))
    }
    
    /**
     * [EN] Applies 3D game-optimized HUD dimensions to the view.
     * @param baseValue The base dimension value.
     * @param type The HUD element type.
     * @return The view with 3D-optimized HUD dimensions.
     * [PT] Aplica dimensões HUD otimizadas para jogos 3D à view.
     * @param baseValue O valor base da dimensão.
     * @param type O tipo do elemento HUD.
     * @return A view com dimensões HUD otimizadas para 3D.
     */
    func game3DHUDSize(_ baseValue: CGFloat, type: UIElementType) -> some View {
        let dimension = AppDimensGames3D.shared.calculateHUDElement(Float(baseValue), type: type)
        return self.frame(width: CGFloat(dimension), height: CGFloat(dimension))
    }
    
    /**
     * [EN] Applies 3D game-optimized menu dimensions to the view.
     * @param baseValue The base dimension value.
     * @param type The menu element type.
     * @return The view with 3D-optimized menu dimensions.
     * [PT] Aplica dimensões de menu otimizadas para jogos 3D à view.
     * @param baseValue O valor base da dimensão.
     * @param type O tipo do elemento de menu.
     * @return A view com dimensões de menu otimizadas para 3D.
     */
    func game3DMenuSize(_ baseValue: CGFloat, type: UIElementType) -> some View {
        let dimension = AppDimensGames3D.shared.calculateMenuElement(Float(baseValue), type: type)
        return self.frame(width: CGFloat(dimension), height: CGFloat(dimension))
    }
}
#endif
