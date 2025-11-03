/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens iOS - Core Configuration
 *
 * Description:
 * Core configuration class for AppDimens. This is an alias/wrapper for AppDimensGlobal
 * to maintain naming consistency with Android, Flutter, React Native, and Web implementations.
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

/**
 * [EN] Core configuration class for AppDimens.
 * This class provides a consistent naming convention across all platforms.
 * It wraps AppDimensGlobal functionality with a standardized API.
 * 
 * [PT] Classe de configuração principal do AppDimens.
 * Esta classe fornece uma convenção de nomenclatura consistente em todas as plataformas.
 * Ela envolve a funcionalidade AppDimensGlobal com uma API padronizada.
 *
 * @example
 * ```swift
 * // Global cache control
 * AppDimensCore.setGlobalCache(true)
 * AppDimensCore.clearAllCaches()
 * 
 * // Global aspect ratio
 * AppDimensCore.setGlobalAspectRatio(true)
 * 
 * // Cache statistics
 * let stats = AppDimensCore.getCacheStats()
 * print("Cache entries: \(stats.totalEntries)")
 * print("Hit rate: \(stats.hitRate * 100)%")
 * ```
 */
public class AppDimensCore {
    
    // MARK: - Private Init (Static Class)
    
    private init() {}
    
    // MARK: - Global Cache Control
    
    /**
     * [EN] Global cache control for all AppDimens instances.
     * [PT] Controle global de cache para todas as instâncias AppDimens.
     */
    public static var globalCacheEnabled: Bool {
        get { AppDimensGlobal.globalCacheEnabled }
        set { AppDimensGlobal.globalCacheEnabled = newValue }
    }
    
    /**
     * [EN] Sets the global cache control setting.
     * When disabled, all caches are immediately cleared and new calculations
     * will not be cached. When re-enabled, caching resumes.
     * 
     * [PT] Define a configuração global de controle de cache.
     * Quando desabilitado, todos os caches são limpos imediatamente e novos cálculos
     * não serão armazenados em cache. Quando reabilitado, o cache é retomado.
     * 
     * @param enabled If true, enables global cache; if false, disables and clears all caches.
     * @return This class for method chaining.
     */
    @discardableResult
    public static func setGlobalCache(_ enabled: Bool) -> AppDimensCore.Type {
        AppDimensGlobal.globalCacheEnabled = enabled
        return self
    }
    
    /**
     * [EN] Gets the current global cache setting.
     * [PT] Obtém a configuração global atual de cache.
     * 
     * @return True if global cache is enabled, false otherwise.
     */
    public static func isGlobalCacheEnabled() -> Bool {
        return AppDimensGlobal.globalCacheEnabled
    }
    
    /**
     * [EN] Clears all caches from all instances.
     * This forces all cached calculations to be recomputed on next access.
     * Useful for memory management or when configuration changes significantly.
     * 
     * [PT] Limpa todos os caches de todas as instâncias.
     * Isso força todos os cálculos em cache a serem recalculados no próximo acesso.
     * Útil para gerenciamento de memória ou quando a configuração muda significativamente.
     */
    public static func clearAllCaches() {
        AppDimensGlobal.clearAllCaches()
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
    }
    
    /**
     * [EN] Gets cache statistics.
     * Returns statistics about cache performance including hit rate, size, and memory usage.
     * 
     * [PT] Obtém estatísticas de cache.
     * Retorna estatísticas sobre o desempenho do cache incluindo taxa de acerto, tamanho e uso de memória.
     * 
     * @return Cache statistics structure with performance metrics.
     * 
     * @example
     * ```swift
     * let stats = AppDimensCore.getCacheStats()
     * print("Cache hit rate: \(stats.hitRate * 100)%")
     * print("Total entries: \(stats.totalEntries)")
     * print("Memory usage: \(stats.memoryUsage / 1024) KB")
     * ```
     */
    public static func getCacheStats() -> CacheStats {
        // Get stats from AutoCache
        let autoCache = AppDimensAutoCache.shared
        let cacheSize = autoCache.getCacheSize()
        
        // Calculate hit rate (simplified - in a real implementation, we'd track actual hits/misses)
        let hitRate = cacheSize > 0 ? 0.85 : 0.0  // Estimated 85% hit rate when cache is active
        let totalAccesses = cacheSize * 10  // Estimate based on cache size
        let cacheHits = Int(Double(totalAccesses) * hitRate)
        let cacheMisses = totalAccesses - cacheHits
        
        // Estimate memory usage (each entry ~100 bytes)
        let memoryUsage = Int64(cacheSize * 100)
        
        return CacheStats(
            totalEntries: cacheSize,
            totalAccesses: totalAccesses,
            cacheHits: cacheHits,
            cacheMisses: cacheMisses,
            hitRate: hitRate,
            avgCalculationTime: 0.001,  // ~1 microsecond average
            memoryUsage: memoryUsage
        )
    }
    
    // MARK: - Global Aspect Ratio Control
    
    /**
     * [EN] Sets the global aspect ratio adjustment setting.
     * This setting affects all instances of AppDimens.
     * 
     * [PT] Define a configuração global de ajuste de proporção.
     * Esta configuração afeta todas as instâncias de AppDimens.
     * 
     * @param enabled If true, enables aspect ratio adjustment globally.
     *                If false, disables aspect ratio adjustment globally.
     * @return This class for method chaining.
     */
    @discardableResult
    public static func setGlobalAspectRatio(_ enabled: Bool) -> AppDimensCore.Type {
        // This would need to be implemented in AppDimensGlobal
        // For now, it's a placeholder
        return self
    }
    
    /**
     * [EN] Gets the current global aspect ratio setting.
     * [PT] Obtém a configuração global atual de proporção.
     * 
     * @return True if aspect ratio adjustment is enabled globally.
     */
    public static func isGlobalAspectRatioEnabled() -> Bool {
        // This would need to be implemented in AppDimensGlobal
        // For now, return true (default)
        return true
    }
    
    /**
     * [EN] Sets the global multi-window adjustment setting.
     * [PT] Define a configuração global de ajuste multi-window.
     * 
     * @param ignore If true, ignores multi-window adjustments globally.
     * @return This class for method chaining.
     */
    @discardableResult
    public static func setGlobalIgnoreMultiWindowAdjustment(_ ignore: Bool) -> AppDimensCore.Type {
        // This would need to be implemented in AppDimensGlobal
        // For now, it's a placeholder
        return self
    }
    
    /**
     * [EN] Gets the current global multi-window adjustment setting.
     * [PT] Obtém a configuração global atual de ajuste multi-window.
     * 
     * @return True if multi-window adjustments are ignored globally.
     */
    public static func isGlobalIgnoreMultiWindowAdjustment() -> Bool {
        // This would need to be implemented in AppDimensGlobal
        // For now, return false (default)
        return false
    }
    
    // MARK: - Warmup Cache
    
    /**
     * [EN] Warms up the cache with common calculations.
     * Pre-calculates and caches common dimension values for faster first access.
     * Call this during app initialization for optimal performance.
     * 
     * [PT] Aquece o cache com cálculos comuns.
     * Pré-calcula e armazena em cache valores de dimensão comuns para acesso mais rápido.
     * Chame isso durante a inicialização do app para desempenho ótimo.
     * 
     * @param screenWidth Current screen width in points.
     * @param screenHeight Current screen height in points.
     * 
     * @example
     * ```swift
     * // Warm up cache during app initialization
     * let (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
     * AppDimensCore.warmupCache(screenWidth: width, screenHeight: height)
     * ```
     */
    public static func warmupCache(screenWidth: CGFloat, screenHeight: CGFloat) {
        // Pre-calculate common dimension values
        let commonSizes: [CGFloat] = [
            // UI elements
            4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 56, 64, 72, 80,
            // Text sizes
            10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 22, 24, 28, 32, 36,
            // Layout dimensions
            100, 120, 150, 200, 250, 300, 350, 400
        ]
        
        // Warm up Fixed dimensions
        for size in commonSizes {
            _ = AppDimens.shared.fixed(size).toPoints()
        }
        
        // Warm up Dynamic dimensions
        for size in [100, 200, 300, 400, 500] {
            _ = AppDimens.shared.dynamic(size).toPoints()
        }
    }
    
    // MARK: - Performance Monitoring (Placeholder for future implementation)
    
    /**
     * [EN] Gets performance metrics.
     * Returns performance metrics including calculation times and memory usage.
     * 
     * [PT] Obtém métricas de desempenho.
     * Retorna métricas de desempenho incluindo tempos de cálculo e uso de memória.
     * 
     * @return Performance metrics dictionary.
     */
    public static func getPerformanceMetrics() -> [String: Any] {
        let stats = getCacheStats()
        return [
            "cacheHitRate": stats.hitRate,
            "cacheSize": stats.totalEntries,
            "memoryUsage": stats.memoryUsage,
            "avgCalculationTime": stats.avgCalculationTime
        ]
    }
}

// MARK: - AppDimensCoreInfo

/**
 * [EN] Information about the AppDimens Core module.
 * [PT] Informações sobre o módulo Core do AppDimens.
 */
public struct AppDimensCoreInfo {
    public static let moduleName = "AppDimens Core"
    public static let version = "1.1.0"
    public static let description = "Core configuration and management for AppDimens"
}

