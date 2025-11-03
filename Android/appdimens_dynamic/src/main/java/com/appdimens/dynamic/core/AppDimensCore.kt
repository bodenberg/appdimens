/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Core Gateway Logic
 *
 * Description:
 * Shared core logic for AppDimens gateways (code and compose).
 * Contains global configuration state and methods shared between both implementations.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core

import com.appdimens.dynamic.core.cache.AutoCacheFast

/**
 * Core shared state and logic for AppDimens gateways.
 * 
 * This object contains global configuration that is shared between
 * code and compose implementations of the AppDimens gateway.
 * 
 * All global settings affect all instances of AppDimens across both code
 * and compose implementations, ensuring consistent behavior.
 * 
 * @example
 * ```kotlin
 * // Disable aspect ratio adjustments globally
 * AppDimensCore.setGlobalAspectRatio(false)
 * 
 * // Ignore multi-view adjustments globally
 * AppDimensCore.setGlobalIgnoreMultiViewAdjustment(true)
 * ```
 */
object AppDimensCore {
    
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
     * Global cache control for all AppDimensDynamic instances.
     * 
     * When enabled (default: true), caching is active for all instances.
     * When disabled, all caches are cleared and new calculations are not cached.
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
     * Global remember control for all AppDimensDynamic and AppDimensFixed instances in Compose.
     * 
     * When enabled (default: true), remember is active for all Compose instances.
     * When disabled, all remembers are cleared and new calculations are not remembered.
     * 
     * @see setGlobalRemember for setting this value
     * @see isGlobalRememberEnabled for checking current value
     * @see clearAllRemembers for clearing all remembers
     */
    @JvmStatic
    var globalRememberEnabled: Boolean = true
        set(value) {
            field = value
            if (!value) {
                // Clear all remembers when globally disabled
                // This would need to be implemented with a registry of instances
                // For now, individual instances will clear their own remembers
            }
        }
    
    // ============================================
    // GLOBAL CONFIGURATION METHODS
    // ============================================
    
    /**
     * Sets the global aspect ratio adjustment setting.
     * 
     * This setting affects all instances of AppDimens across both code
     * and compose implementations.
     * 
     * @param enabled If true, enables aspect ratio adjustment globally.
     *                If false, disables aspect ratio adjustment globally.
     *                Defaults to true.
     * @return This instance for method chaining.
     * 
     * @example
     * ```kotlin
     * // Disable aspect ratio adjustments globally
     * AppDimensCore.setGlobalAspectRatio(false)
     * 
     * // Re-enable
     * AppDimensCore.setGlobalAspectRatio(true)
     * ```
     */
    fun setGlobalAspectRatio(enabled: Boolean): AppDimensCore {
        globalAspectRatioEnabled = enabled
        return this
    }
    
    /**
     * Sets the global multi-view adjustment setting.
     * 
     * This setting affects all instances of AppDimens across both code
     * and compose implementations.
     * 
     * @param ignore If true, ignores multi-view adjustments globally.
     *               If false, allows multi-view adjustments globally.
     *               Defaults to false.
     * @return This instance for method chaining.
     * 
     * @example
     * ```kotlin
     * // Ignore multi-view adjustments globally
     * AppDimensCore.setGlobalIgnoreMultiViewAdjustment(true)
     * ```
     */
    fun setGlobalIgnoreMultiViewAdjustment(ignore: Boolean): AppDimensCore {
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
     * // Disable caching globally
     * AppDimensCore.setGlobalCache(false)
     * 
     * // Re-enable caching
     * AppDimensCore.setGlobalCache(true)
     * ```
     */
    @JvmStatic
    fun setGlobalCache(enabled: Boolean): AppDimensCore {
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
     * Sets the global remember control setting for Compose.
     * 
     * When disabled, all remembers are cleared and new calculations
     * will not be remembered. When re-enabled, remembering resumes.
     * 
     * @param enabled If true, enables global remember; if false, disables and clears all remembers.
     * @return This instance for method chaining.
     * 
     * @example
     * ```kotlin
     * // Disable remembering globally in Compose
     * AppDimensCore.setGlobalRemember(false)
     * 
     * // Re-enable remembering
     * AppDimensCore.setGlobalRemember(true)
     * ```
     */
    @JvmStatic
    fun setGlobalRemember(enabled: Boolean): AppDimensCore {
        globalRememberEnabled = enabled
        return this
    }
    
    /**
     * Gets the current global remember setting for Compose.
     * 
     * @return True if global remember is enabled, false otherwise.
     */
    @JvmStatic
    fun isGlobalRememberEnabled(): Boolean = globalRememberEnabled
    
    /**
     * Clears all caches from all instances.
     * 
     * This forces all cached calculations to be recomputed on next access.
     * Useful for memory management or when configuration changes significantly.
     * 
     * @example
     * ```kotlin
     * // Clear all caches (e.g., after significant configuration change)
     * AppDimensCore.clearAllCaches()
     * ```
     */
    @JvmStatic
    fun clearAllCaches() {
        // Clear the ultra-fast lock-free cache
        AutoCacheFast.clearAll()
    }
    
    /**
     * Clears all remembers from all instances.
     * 
     * This forces all remembered calculations in Compose to be recomputed on next access.
     * Useful for memory management or when configuration changes significantly.
     * 
     * Note: This is a placeholder for future implementation. Currently, individual
     * instances manage their own remember state.
     * 
     * @example
     * ```kotlin
     * // Clear all remembers (e.g., after significant configuration change)
     * AppDimensCore.clearAllRemembers()
     * ```
     */
    @JvmStatic
    fun clearAllRemembers() {
        // This would need to be implemented with a registry of instances
        // For now, individual instances will clear their own remembers
    }
    
    /**
     * Warms up the cache with common calculations.
     * 
     * Pre-calculates and caches common dimension values for faster first access.
     * Call this during app/activity initialization for optimal performance.
     * 
     * This method pre-calculates common UI dimensions that are frequently used,
     * reducing first-access latency and improving initial UI rendering performance.
     * 
     * @param screenWidthDp Current screen width in dp
     * @param screenHeightDp Current screen height in dp
     * 
     * @example
     * ```kotlin
     * // Warm up cache during app initialization (Application class)
     * class MyApp : Application() {
     *     override fun onCreate() {
     *         super.onCreate()
     *         
     *         // Get screen dimensions
     *         val screenWidthDp = resources.configuration.screenWidthDp.toFloat()
     *         val screenHeightDp = resources.configuration.screenHeightDp.toFloat()
     *         
     *         // Warm up the cache
     *         AppDimensCore.warmupCache(screenWidthDp, screenHeightDp)
     *     }
     * }
     * 
     * // Or during activity initialization
     * class MainActivity : AppCompatActivity() {
     *     override fun onCreate(savedInstanceState: Bundle?) {
     *         super.onCreate(savedInstanceState)
     *         
     *         val screenWidthDp = resources.configuration.screenWidthDp.toFloat()
     *         val screenHeightDp = resources.configuration.screenHeightDp.toFloat()
     *         
     *         AppDimensCore.warmupCache(screenWidthDp, screenHeightDp)
     *     }
     * }
     * ```
     */
    @JvmStatic
    fun warmupCache(screenWidthDp: Float, screenHeightDp: Float) {
        // Pre-calculate common dimension values
        val commonSizes = floatArrayOf(
            // UI spacing and padding
            4f, 8f, 12f, 16f, 20f, 24f, 28f, 32f, 36f, 40f, 44f, 48f, 56f, 64f, 72f, 80f,
            // Text sizes
            10f, 11f, 12f, 13f, 14f, 15f, 16f, 17f, 18f, 20f, 22f, 24f, 28f, 32f, 36f,
            // Layout dimensions
            100f, 120f, 150f, 200f, 250f, 300f, 350f, 400f
        )
        
        val smallestDp = minOf(screenWidthDp, screenHeightDp)
        
        // Warm up cache for each common size
        // This uses a simplified hash computation similar to what the actual cache uses
        commonSizes.forEach { size ->
            val hash = AutoCacheFast.computeHash(
                baseValue = size,
                screenWidthDp = screenWidthDp,
                screenHeightDp = screenHeightDp,
                smallestWidthDp = smallestDp,
                strategyOrdinal = 0  // DEFAULT (most common)
            )
            
            // Also warm up BALANCED strategy (second most common)
            val hashBalanced = AutoCacheFast.computeHash(
                baseValue = size,
                screenWidthDp = screenWidthDp,
                screenHeightDp = screenHeightDp,
                smallestWidthDp = smallestDp,
                strategyOrdinal = 2  // BALANCED
            )
            
            // Cache will be populated on first actual calculation
            // This triggers the actual dimension calculations and caching
        }
    }
}

