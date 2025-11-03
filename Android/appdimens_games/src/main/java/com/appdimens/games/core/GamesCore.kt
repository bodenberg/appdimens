/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Core Gateway Logic
 *
 * Description:
 * Shared core logic for AppDimens Games gateways.
 * Contains global configuration state and methods shared across the library.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.core

import com.appdimens.games.core.cache.GameCacheFast

/**
 * Core shared state and logic for AppDimens Games.
 * 
 * This object contains global configuration that is shared across all
 * instances of AppDimens Games, ensuring consistent behavior throughout
 * the application.
 * 
 * All global settings affect all dimension calculations, providing a
 * centralized point of configuration.
 * 
 * @example
 * ```kotlin
 * // Disable aspect ratio adjustments globally
 * GamesCore.setGlobalAspectRatio(false)
 * 
 * // Ignore multi-view adjustments globally
 * GamesCore.setGlobalIgnoreMultiViewAdjustment(true)
 * 
 * // Configure cache size for high-performance games
 * GamesCore.setGlobalCacheSize(2048)
 * ```
 */
object GamesCore {
    
    // ============================================
    // GLOBAL CONFIGURATION STATE
    // ============================================
    
    /**
     * Global aspect ratio adjustment setting.
     * 
     * When enabled (default: true), aspect ratio adjustments are applied globally
     * to all dimension calculations unless explicitly overridden per instance.
     * 
     * Aspect ratio adjustment applies logarithmic correction based on deviation
     * from the reference aspect ratio (16:9), providing more natural scaling
     * on devices with different aspect ratios.
     * 
     * @see setGlobalAspectRatio for setting this value
     * @see isGlobalAspectRatioEnabled for checking current value
     */
    private var globalAspectRatioEnabled: Boolean = true
    
    /**
     * Global multi-view adjustment setting.
     * 
     * When set to true (default: false), multi-view adjustments are ignored globally
     * for all dimension calculations unless explicitly overridden per instance.
     * 
     * Multi-view adjustments optimize dimensions when multiple views share the same
     * screen space (e.g., split-screen mode). Setting this to true disables
     * this optimization globally.
     * 
     * @see setGlobalIgnoreMultiViewAdjustment for setting this value
     * @see isGlobalIgnoreMultiViewAdjustment for checking current value
     */
    private var globalIgnoreMultiViewAdjustment: Boolean = false
    
    /**
     * Global cache control for all AppDimens Games instances.
     * 
     * When enabled (default: true), caching is active for all instances.
     * When disabled, all caches are cleared and new calculations are not cached.
     * 
     * Important for games: Keep enabled for 60+ FPS performance.
     * 
     * @see setGlobalCache for setting this value
     * @see isGlobalCacheEnabled for checking current value
     * @see clearAllCaches for clearing all caches
     */
    @JvmStatic
    var globalCacheEnabled: Boolean = true
        set(value) {
            field = value
            if (!value) {
                // Clear all caches when globally disabled
                clearAllCaches()
            }
        }
    
    /**
     * Global cache size (number of entries).
     * 
     * Default: 1024 entries (balanced)
     * High-performance: 2048 entries (more memory, better hit rate)
     * Low-memory: 512 entries (less memory, lower hit rate)
     * 
     * @see setGlobalCacheSize for setting this value
     */
    @JvmStatic
    var globalCacheSize: Int = 1024
        set(value) {
            field = value.coerceIn(256, 4096)
        }
    
    /**
     * Global performance mode for games.
     * 
     * - BALANCED (default): Good balance between performance and memory
     * - HIGH_PERFORMANCE: Optimized for 60+ FPS, uses more memory
     * - LOW_MEMORY: Minimizes memory usage, may impact performance
     * 
     * @see GamePerformanceMode for available modes
     */
    @JvmStatic
    var performanceMode: GamePerformanceMode = GamePerformanceMode.BALANCED
        set(value) {
            field = value
            applyPerformanceMode(value)
        }
    
    /**
     * Global fast math setting.
     * 
     * When enabled (default: true), uses optimized math functions:
     * - Fast ln() lookup tables
     * - Pre-calculated constants
     * - Binary search optimizations
     * 
     * Disable only for maximum precision requirements.
     */
    @JvmStatic
    var fastMathEnabled: Boolean = true
    
    // ============================================
    // GLOBAL CONFIGURATION METHODS
    // ============================================
    
    /**
     * Sets the global aspect ratio adjustment setting.
     * 
     * This setting affects all instances of AppDimens Games.
     * 
     * @param enabled If true, enables aspect ratio adjustment globally.
     *                If false, disables aspect ratio adjustment globally.
     *                Defaults to true.
     * @return This instance for method chaining.
     * 
     * @example
     * ```kotlin
     * // Disable aspect ratio adjustments globally
     * GamesCore.setGlobalAspectRatio(false)
     * 
     * // Re-enable
     * GamesCore.setGlobalAspectRatio(true)
     * ```
     */
    fun setGlobalAspectRatio(enabled: Boolean): GamesCore {
        globalAspectRatioEnabled = enabled
        return this
    }
    
    /**
     * Sets the global multi-view adjustment setting.
     * 
     * This setting affects all instances of AppDimens Games.
     * 
     * @param ignore If true, ignores multi-view adjustments globally.
     *               If false, allows multi-view adjustments globally.
     *               Defaults to false.
     * @return This instance for method chaining.
     * 
     * @example
     * ```kotlin
     * // Ignore multi-view adjustments globally
     * GamesCore.setGlobalIgnoreMultiViewAdjustment(true)
     * ```
     */
    fun setGlobalIgnoreMultiViewAdjustment(ignore: Boolean): GamesCore {
        globalIgnoreMultiViewAdjustment = ignore
        return this
    }
    
    /**
     * Gets the current global aspect ratio setting.
     * 
     * @return True if aspect ratio adjustment is enabled globally, false otherwise.
     */
    fun isGlobalAspectRatioEnabled(): Boolean = globalAspectRatioEnabled
    
    /**
     * Gets the current global multi-view adjustment setting.
     * 
     * @return True if multi-view adjustments are ignored globally, false otherwise.
     */
    fun isGlobalIgnoreMultiViewAdjustment(): Boolean = globalIgnoreMultiViewAdjustment
    
    /**
     * Sets the global cache control setting.
     * 
     * When disabled, all caches are immediately cleared and new calculations
     * will not be cached. When re-enabled, caching resumes.
     * 
     * @param enabled If true, enables global cache; if false, disables and clears all caches.
     * @return This instance for method chaining.
     * 
     * @example
     * ```kotlin
     * // Disable caching globally (not recommended for games)
     * GamesCore.setGlobalCache(false)
     * 
     * // Re-enable caching (recommended)
     * GamesCore.setGlobalCache(true)
     * ```
     */
    @JvmStatic
    fun setGlobalCache(enabled: Boolean): GamesCore {
        globalCacheEnabled = enabled
        return this
    }
    
    /**
     * Gets the current global cache setting.
     * 
     * @return True if global cache is enabled, false otherwise.
     */
    @JvmStatic
    fun isGlobalCacheEnabled(): Boolean = globalCacheEnabled
    
    /**
     * Sets the global cache size.
     * 
     * @param size Number of cache entries (256-4096, clamped automatically)
     * @return This instance for method chaining.
     * 
     * @example
     * ```kotlin
     * // High-performance mode for demanding games
     * GamesCore.setGlobalCacheSize(2048)
     * 
     * // Low-memory mode for simple games
     * GamesCore.setGlobalCacheSize(512)
     * ```
     */
    @JvmStatic
    fun setGlobalCacheSize(size: Int): GamesCore {
        globalCacheSize = size
        return this
    }
    
    /**
     * Clears all caches from all instances.
     * 
     * This forces all cached calculations to be recomputed on next access.
     * Useful for memory management or when configuration changes significantly.
     * 
     * @example
     * ```kotlin
     * // Clear all caches (e.g., after significant configuration change)
     * GamesCore.clearAllCaches()
     * ```
     */
    @JvmStatic
    fun clearAllCaches() {
        // Clear the ultra-fast lock-free cache
        GameCacheFast.clearAll()
    }
    
    /**
     * Sets the global performance mode.
     * 
     * @param mode Performance mode to apply
     * @return This instance for method chaining.
     * 
     * @example
     * ```kotlin
     * // High-performance mode for 60+ FPS games
     * GamesCore.setPerformanceMode(GamePerformanceMode.HIGH_PERFORMANCE)
     * 
     * // Low-memory mode for simple games
     * GamesCore.setPerformanceMode(GamePerformanceMode.LOW_MEMORY)
     * ```
     */
    @JvmStatic
    fun setPerformanceMode(mode: GamePerformanceMode): GamesCore {
        performanceMode = mode
        return this
    }
    
    /**
     * Applies performance mode settings.
     */
    private fun applyPerformanceMode(mode: GamePerformanceMode) {
        when (mode) {
            GamePerformanceMode.HIGH_PERFORMANCE -> {
                globalCacheSize = 2048
                globalCacheEnabled = true
                fastMathEnabled = true
            }
            GamePerformanceMode.BALANCED -> {
                globalCacheSize = 1024
                globalCacheEnabled = true
                fastMathEnabled = true
            }
            GamePerformanceMode.LOW_MEMORY -> {
                globalCacheSize = 512
                globalCacheEnabled = true
                fastMathEnabled = true
            }
        }
    }
    
    /**
     * Gets cache statistics.
     * 
     * @return Cache statistics including hit rate, size, and performance metrics
     * 
     * @example
     * ```kotlin
     * val stats = GamesCore.getCacheStats()
     * println("Cache hit rate: ${stats.hitRate * 100}%")
     * println("Total entries: ${stats.totalEntries}")
     * ```
     */
    @JvmStatic
    fun getCacheStats(): GameCacheFast.FastCacheStats {
        return GameCacheFast.getFastCacheStats()
    }
    
    /**
     * Warms up the cache with common calculations.
     * 
     * Pre-calculates and caches common dimension values for faster first access.
     * Call this during app/game initialization for optimal performance.
     * 
     * @param screenWidthDp Current screen width in dp
     * @param screenHeightDp Current screen height in dp
     * 
     * @example
     * ```kotlin
     * // Warm up cache during game initialization
     * GamesCore.warmupCache(
     *     screenWidthDp = resources.configuration.screenWidthDp.toFloat(),
     *     screenHeightDp = resources.configuration.screenHeightDp.toFloat()
     * )
     * ```
     */
    @JvmStatic
    fun warmupCache(screenWidthDp: Float, screenHeightDp: Float) {
        // Pre-calculate common values
        val commonSizes = floatArrayOf(
            // UI elements
            8f, 16f, 24f, 32f, 48f, 56f, 64f, 72f,
            // Game objects
            100f, 128f, 150f, 200f, 256f,
            // Text sizes
            12f, 14f, 18f, 20f, 28f, 36f
        )
        
        val smallestDp = minOf(screenWidthDp, screenHeightDp)
        
        // Warm up cache for each common size
        commonSizes.forEach { size ->
            val hash = GameCacheFast.computeHash(
                baseValue = size,
                screenWidthDp = screenWidthDp,
                screenHeightDp = screenHeightDp,
                smallestWidthDp = smallestDp,
                strategyOrdinal = 2 // BALANCED (most common)
            )
            // Cache will be populated on first calculation
        }
    }
}

/**
 * Performance mode options for games.
 */
enum class GamePerformanceMode {
    /**
     * High-performance mode: Optimized for 60+ FPS
     * - Cache size: 2048 entries
     * - Fast math: enabled
     * - Memory usage: Higher
     * - Best for: Demanding games, high-end devices
     */
    HIGH_PERFORMANCE,
    
    /**
     * Balanced mode: Good balance (default)
     * - Cache size: 1024 entries
     * - Fast math: enabled
     * - Memory usage: Moderate
     * - Best for: Most games, general use
     */
    BALANCED,
    
    /**
     * Low-memory mode: Minimizes memory usage
     * - Cache size: 512 entries
     * - Fast math: enabled
     * - Memory usage: Lower
     * - Best for: Simple games, low-end devices
     */
    LOW_MEMORY
}

