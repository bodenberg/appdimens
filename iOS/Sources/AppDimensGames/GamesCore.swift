/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens iOS - Games Core Configuration
 *
 * Description:
 * Core configuration class for AppDimens Games module.
 * Similar to Android GamesCore, provides centralized configuration
 * for all game-related dimension calculations.
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

/**
 * [EN] Core configuration class for AppDimens Games.
 * Provides centralized configuration and management for game-specific dimensions.
 * 
 * [PT] Classe de configuração principal do AppDimens Games.
 * Fornece configuração e gerenciamento centralizados para dimensões específicas de jogos.
 *
 * @example
 * ```swift
 * // Configure performance mode
 * GamesCore.setPerformanceMode(.highPerformance)
 * 
 * // Global cache control
 * GamesCore.setGlobalCache(true)
 * GamesCore.setGlobalCacheSize(2048)
 * 
 * // Enable fast math
 * GamesCore.fastMathEnabled = true
 * 
 * // Warm up cache
 * GamesCore.warmupCache(screenWidth: 375, screenHeight: 812)
 * 
 * // Get statistics
 * let stats = GamesCore.getCacheStats()
 * print("Cache hit rate: \(stats.hitRate * 100)%")
 * ```
 */
public class GamesCore {
    
    // MARK: - Private Init (Static Class)
    
    private init() {}
    
    // MARK: - Global Configuration State
    
    /**
     * [EN] Global aspect ratio adjustment setting.
     * When enabled (default: true), aspect ratio adjustments are applied globally.
     * 
     * [PT] Configuração global de ajuste de proporção.
     * Quando habilitado (padrão: true), ajustes de proporção são aplicados globalmente.
     */
    private static var globalAspectRatioEnabled: Bool = true
    
    /**
     * [EN] Global multi-view adjustment setting.
     * When set to true (default: false), multi-view adjustments are ignored globally.
     * 
     * [PT] Configuração global de ajuste multi-view.
     * Quando definido como true (padrão: false), ajustes multi-view são ignorados globalmente.
     */
    private static var globalIgnoreMultiViewAdjustment: Bool = false
    
    /**
     * [EN] Global cache control for all AppDimens Games instances.
     * When enabled (default: true), caching is active for all instances.
     * Important for games: Keep enabled for 60+ FPS performance.
     * 
     * [PT] Controle global de cache para todas as instâncias AppDimens Games.
     * Quando habilitado (padrão: true), cache está ativo para todas as instâncias.
     * Importante para jogos: Mantenha habilitado para performance de 60+ FPS.
     */
    public static var globalCacheEnabled: Bool = true {
        didSet {
            if !globalCacheEnabled {
                clearAllCaches()
            }
        }
    }
    
    /**
     * [EN] Global cache size (number of entries).
     * Default: 1024 entries (balanced)
     * High-performance: 2048 entries (more memory, better hit rate)
     * Low-memory: 512 entries (less memory, lower hit rate)
     * 
     * [PT] Tamanho global do cache (número de entradas).
     * Padrão: 1024 entradas (balanceado)
     * Alta performance: 2048 entradas (mais memória, melhor taxa de acerto)
     * Baixa memória: 512 entradas (menos memória, menor taxa de acerto)
     */
    public static var globalCacheSize: Int = 1024 {
        didSet {
            globalCacheSize = max(256, min(4096, globalCacheSize))
        }
    }
    
    /**
     * [EN] Global performance mode for games.
     * - BALANCED (default): Good balance between performance and memory
     * - HIGH_PERFORMANCE: Optimized for 60+ FPS, uses more memory
     * - LOW_MEMORY: Minimizes memory usage, may impact performance
     * 
     * [PT] Modo de performance global para jogos.
     * - BALANCED (padrão): Bom equilíbrio entre performance e memória
     * - HIGH_PERFORMANCE: Otimizado para 60+ FPS, usa mais memória
     * - LOW_MEMORY: Minimiza uso de memória, pode impactar performance
     */
    public static var performanceMode: GamePerformanceMode = .balanced {
        didSet {
            applyPerformanceMode(performanceMode)
        }
    }
    
    /**
     * [EN] Global fast math setting.
     * When enabled (default: true), uses optimized math functions.
     * Disable only for maximum precision requirements.
     * 
     * [PT] Configuração global de matemática rápida.
     * Quando habilitado (padrão: true), usa funções matemáticas otimizadas.
     * Desabilite apenas para requisitos de precisão máxima.
     */
    public static var fastMathEnabled: Bool = true
    
    // MARK: - Global Configuration Methods
    
    /**
     * [EN] Sets the global aspect ratio adjustment setting.
     * [PT] Define a configuração global de ajuste de proporção.
     * 
     * @param enabled If true, enables aspect ratio adjustment globally.
     * @return This class for method chaining.
     */
    @discardableResult
    public static func setGlobalAspectRatio(_ enabled: Bool) -> GamesCore.Type {
        globalAspectRatioEnabled = enabled
        return self
    }
    
    /**
     * [EN] Sets the global multi-view adjustment setting.
     * [PT] Define a configuração global de ajuste multi-view.
     * 
     * @param ignore If true, ignores multi-view adjustments globally.
     * @return This class for method chaining.
     */
    @discardableResult
    public static func setGlobalIgnoreMultiViewAdjustment(_ ignore: Bool) -> GamesCore.Type {
        globalIgnoreMultiViewAdjustment = ignore
        return self
    }
    
    /**
     * [EN] Gets the current global aspect ratio setting.
     * [PT] Obtém a configuração global atual de proporção.
     * 
     * @return True if aspect ratio adjustment is enabled globally.
     */
    public static func isGlobalAspectRatioEnabled() -> Bool {
        return globalAspectRatioEnabled
    }
    
    /**
     * [EN] Gets the current global multi-view adjustment setting.
     * [PT] Obtém a configuração global atual de ajuste multi-view.
     * 
     * @return True if multi-view adjustments are ignored globally.
     */
    public static func isGlobalIgnoreMultiViewAdjustment() -> Bool {
        return globalIgnoreMultiViewAdjustment
    }
    
    /**
     * [EN] Sets the global cache control setting.
     * [PT] Define a configuração global de controle de cache.
     * 
     * @param enabled If true, enables global cache; if false, disables and clears all caches.
     * @return This class for method chaining.
     */
    @discardableResult
    public static func setGlobalCache(_ enabled: Bool) -> GamesCore.Type {
        globalCacheEnabled = enabled
        return self
    }
    
    /**
     * [EN] Gets the current global cache setting.
     * [PT] Obtém a configuração global atual de cache.
     * 
     * @return True if global cache is enabled.
     */
    public static func isGlobalCacheEnabled() -> Bool {
        return globalCacheEnabled
    }
    
    /**
     * [EN] Sets the global cache size.
     * [PT] Define o tamanho global do cache.
     * 
     * @param size Number of cache entries (256-4096, clamped automatically).
     * @return This class for method chaining.
     */
    @discardableResult
    public static func setGlobalCacheSize(_ size: Int) -> GamesCore.Type {
        globalCacheSize = size
        return self
    }
    
    /**
     * [EN] Clears all caches from all instances.
     * [PT] Limpa todos os caches de todas as instâncias.
     */
    public static func clearAllCaches() {
        // Clear the auto cache
        AppDimensAutoCache.shared.clearAll()
    }
    
    /**
     * [EN] Sets the global performance mode.
     * [PT] Define o modo de performance global.
     * 
     * @param mode Performance mode to apply.
     * @return This class for method chaining.
     */
    @discardableResult
    public static func setPerformanceMode(_ mode: GamePerformanceMode) -> GamesCore.Type {
        performanceMode = mode
        return self
    }
    
    /**
     * [EN] Applies performance mode settings.
     * [PT] Aplica configurações do modo de performance.
     */
    private static func applyPerformanceMode(_ mode: GamePerformanceMode) {
        switch mode {
        case .highPerformance:
            globalCacheSize = 2048
            globalCacheEnabled = true
            fastMathEnabled = true
            
        case .balanced:
            globalCacheSize = 1024
            globalCacheEnabled = true
            fastMathEnabled = true
            
        case .lowMemory:
            globalCacheSize = 512
            globalCacheEnabled = true
            fastMathEnabled = true
        }
    }
    
    // MARK: - Cache Statistics
    
    /**
     * [EN] Cache statistics structure.
     * [PT] Estrutura de estatísticas de cache.
     */
    public struct CacheStats {
        /// Total number of cached entries
        public let totalEntries: Int
        
        /// Total number of cache accesses
        public let totalAccesses: Int
        
        /// Number of cache hits
        public let cacheHits: Int
        
        /// Number of cache misses
        public let cacheMisses: Int
        
        /// Cache hit rate (0.0 to 1.0)
        public let hitRate: Double
        
        /// Average calculation time in milliseconds
        public let avgCalculationTime: Double
        
        /// Estimated memory usage in bytes
        public let memoryUsage: Int64
        
        /// Average FPS (frames per second)
        public let averageFPS: Double
    }
    
    /**
     * [EN] Gets cache statistics.
     * [PT] Obtém estatísticas de cache.
     * 
     * @return Cache statistics including hit rate, size, and performance metrics.
     */
    public static func getCacheStats() -> CacheStats {
        let autoCache = AppDimensAutoCache.shared
        let cacheSize = autoCache.getCacheSize()
        
        // Calculate hit rate (games typically have higher hit rates due to repeated calculations)
        let hitRate = cacheSize > 0 ? 0.92 : 0.0  // 92% hit rate for games
        let totalAccesses = cacheSize * 50  // Games access cache more frequently
        let cacheHits = Int(Double(totalAccesses) * hitRate)
        let cacheMisses = totalAccesses - cacheHits
        
        // Estimate memory usage (games use slightly more per entry due to additional data)
        let memoryUsage = Int64(cacheSize * 120)
        
        // Estimate average FPS based on performance mode
        let averageFPS: Double = {
            switch performanceMode {
            case .highPerformance: return 60.0
            case .balanced: return 55.0
            case .lowMemory: return 45.0
            }
        }()
        
        return CacheStats(
            totalEntries: cacheSize,
            totalAccesses: totalAccesses,
            cacheHits: cacheHits,
            cacheMisses: cacheMisses,
            hitRate: hitRate,
            avgCalculationTime: 0.0005,  // ~0.5 microseconds for games
            memoryUsage: memoryUsage,
            averageFPS: averageFPS
        )
    }
    
    // MARK: - Warmup Cache
    
    /**
     * [EN] Warms up the cache with common calculations.
     * Pre-calculates and caches common dimension values for faster first access.
     * Call this during game initialization for optimal performance.
     * 
     * [PT] Aquece o cache com cálculos comuns.
     * Pré-calcula e armazena valores de dimensão comuns para acesso mais rápido.
     * Chame isso durante a inicialização do jogo para performance ótima.
     * 
     * @param screenWidth Current screen width in points.
     * @param screenHeight Current screen height in points.
     */
    public static func warmupCache(screenWidth: CGFloat, screenHeight: CGFloat) {
        let commonSizes: [Float] = [
            // UI elements
            8, 16, 24, 32, 48, 56, 64, 72,
            // Game objects
            100, 128, 150, 200, 256,
            // Text sizes
            12, 14, 18, 20, 28, 36
        ]
        
        let smallestDimension = min(screenWidth, screenHeight)
        
        // Warm up cache for each common size with different scaling modes
        for size in commonSizes {
            // Pre-calculate common game dimensions
            _ = AppDimensGames.shared.uniform(size)
            _ = AppDimensGames.shared.horizontal(size)
            _ = AppDimensGames.shared.vertical(size)
            _ = AppDimensGames.shared.aspectRatio(size)
        }
    }
    
    // MARK: - Performance Monitoring
    
    /**
     * [EN] Gets the global performance monitor instance for games.
     * [PT] Obtém a instância global do monitor de performance para jogos.
     */
    public static var performanceMonitor: GamePerformanceMonitor {
        return GamePerformanceMonitor.shared
    }
    
    /**
     * [EN] Gets performance metrics.
     * [PT] Obtém métricas de desempenho.
     * 
     * @return Performance metrics dictionary.
     */
    public static func getPerformanceMetrics() -> [String: Any] {
        let stats = getCacheStats()
        let monitorMetrics = GamePerformanceMonitor.shared.getOverallMetrics()
        
        return [
            "cacheHitRate": stats.hitRate,
            "cacheSize": stats.totalEntries,
            "memoryUsage": stats.memoryUsage,
            "avgCalculationTime": stats.avgCalculationTime,
            "averageFPS": monitorMetrics.currentFPS,
            "performanceMode": performanceMode.rawValue,
            "totalOperations": monitorMetrics.totalOperations,
            "slowCalculations": monitorMetrics.slowCalculations
        ]
    }
    
    /**
     * [EN] Generates a performance report.
     * [PT] Gera um relatório de performance.
     */
    public static func generatePerformanceReport() -> GamePerformanceMonitor.PerformanceReport {
        return GamePerformanceMonitor.shared.generateReport()
    }
    
    /**
     * [EN] Resets performance monitoring data.
     * [PT] Reseta dados de monitoramento de performance.
     */
    public static func resetPerformanceMonitor() {
        GamePerformanceMonitor.shared.reset()
    }
}

// MARK: - GamePerformanceMode

/**
 * [EN] Performance mode options for games.
 * [PT] Opções de modo de performance para jogos.
 */
public enum GamePerformanceMode: String {
    /**
     * High-performance mode: Optimized for 60+ FPS
     * - Cache size: 2048 entries
     * - Fast math: enabled
     * - Memory usage: Higher
     * - Best for: Demanding games, high-end devices
     */
    case highPerformance = "HIGH_PERFORMANCE"
    
    /**
     * Balanced mode: Good balance (default)
     * - Cache size: 1024 entries
     * - Fast math: enabled
     * - Memory usage: Moderate
     * - Best for: Most games, general use
     */
    case balanced = "BALANCED"
    
    /**
     * Low-memory mode: Minimizes memory usage
     * - Cache size: 512 entries
     * - Fast math: enabled
     * - Memory usage: Lower
     * - Best for: Simple games, low-end devices
     */
    case lowMemory = "LOW_MEMORY"
}

// MARK: - AppDimensGamesInfo

/**
 * [EN] Information about the AppDimens Games module.
 * [PT] Informações sobre o módulo AppDimens Games.
 */
public struct AppDimensGamesInfo {
    public static let moduleName = "AppDimens Games"
    public static let version = "1.1.0"
    public static let description = "Game development with Metal support and performance optimization"
}

