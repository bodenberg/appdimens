/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Unified Smart Dimensioning System
 *
 * Description:
 * Unified dimension system supporting all 13 scaling strategies with complete
 * configuration options. Replaces legacy AppDimensFixed/AppDimensDynamic split
 * with a single, powerful AppDimens class.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.code

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import com.appdimens.dynamic.core.*
import com.appdimens.dynamic.core.cache.AutoCacheFast
import com.appdimens.dynamic.core.calculation.Calculator
import com.appdimens.dynamic.core.models.FluidConfig
import com.appdimens.dynamic.core.models.FluidDeviceType
import com.appdimens.dynamic.core.models.PerceptualModel
import com.appdimens.dynamic.core.strategy.AutoSizeMode
import com.appdimens.dynamic.core.strategy.ElementType
import com.appdimens.dynamic.core.strategy.ScalingStrategy
import com.appdimens.library.BaseOrientation
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeType
import com.appdimens.library.UiModeQualifierEntry

/**
 * AppDimens - Unified Smart Dimensioning System for AppDimens 2.0
 * 
 * Combines all 13 scaling strategies into a single, powerful API.
 * Replaces legacy AppDimensFixed/AppDimensDynamic split.
 * 
 * Usage:
 * ```kotlin
 * // Simple
 * val size = AppDimens.from(48f).toDp(resources)
 * 
 * // With strategy
 * val size = AppDimens.from(48f)
 *     .balanced()
 *     .toDp(resources)
 * 
 * // Legacy compatibility
 * val size = AppDimens.fixed(48f).toDp(resources)
 * val size = AppDimens.dynamic(48f).toDp(resources)
 * 
 * // Auto-infer
 * val size = AppDimens.from(48f)
 *     .forElement(ElementType.BUTTON)
 *     .toDp(resources)
 * 
 * // Full control
 * val size = AppDimens.from(48f)
 *     .balanced()
 *     .transitionPoint(500f)
 *     .bounds(40f, 72f)
 *     .maxPhysicalSize(15f)
 *     .screen(UiModeType.TELEVISION, 64f)
 *     .toDp(resources)
 * 
 * // Utilities
 * val itemCount = AppDimens.calculateAvailableItemCount(containerPx, itemDp, marginDp, resources)
 * val percent = AppDimens.dynamicPercentageDp(0.5f, ScreenType.LOWEST, resources)
 * ```
 */
class AppDimens private constructor(
    private val baseValue: Float
) {
    
    // ============================================
    // CONFIGURATION STATE
    // ============================================
    
    // Strategy control
    private var elementType: ElementType? = null
    private var forcedStrategy: ScalingStrategy? = null
    
    // Universal constraints
    private var minValue: Float? = null
    private var maxValue: Float? = null
    private var maxPhysicalMm: Float? = null
    
    // Fixed/DEFAULT-specific
    private var applyAspectRatioAdjustment: Boolean = true
    private var fixedArSensitivity: Float? = null
    
    // Perceptual-specific
    private var perceptualModel: PerceptualModel = PerceptualModel.BALANCED
    private var perceptualSensitivity: Float = Calculator.DEFAULT_SENSITIVITY
    private var perceptualPowerExponent: Float = Calculator.DEFAULT_POWER_EXPONENT
    private var perceptualTransitionPoint: Float = Calculator.DEFAULT_TRANSITION_POINT
    private var perceptualApplyAR: Boolean = true
    private var perceptualArSensitivity: Float = Calculator.DEFAULT_AR_SENSITIVITY
    
    // Fluid-specific
    private var fluidMinValue: Float? = null
    private var fluidMaxValue: Float? = null
    private var fluidMinWidth: Float = 320f
    private var fluidMaxWidth: Float = 768f
    private val fluidDeviceQualifiers = mutableMapOf<FluidDeviceType, FluidConfig>()
    private val fluidScreenQualifiers = mutableMapOf<Int, FluidConfig>()
    
    // AutoSize-specific (v2.1)
    private var autoSizeEnabled: Boolean = false
    private var autoSizeMinValue: Float? = null
    private var autoSizeMaxValue: Float? = null
    private var autoSizeMode: AutoSizeMode = AutoSizeMode.UNIFORM
    private var autoSizePresetSizes: FloatArray? = null
    
    // Screen qualifiers (3 priorities)
    private val customIntersectionMap: MutableMap<UiModeQualifierEntry, Float> = mutableMapOf()
    private val customUiModeMap: MutableMap<UiModeType, Float> = mutableMapOf()
    private val customDpMap: MutableMap<DpQualifierEntry, Float> = mutableMapOf()
    
    // Orientation & screen type
    private var screenType: ScreenType = ScreenType.LOWEST
    private var baseOrientation: BaseOrientation = BaseOrientation.AUTO
    
    // Other
    private var ignoreMultiViewAdjustment: Boolean = false
    private var enableCache: Boolean = true
    
    // ============================================
    // COMPANION & CONSTANTS
    // ============================================
    
    companion object {
        /**
         * Factory method to create AppDimens instance
         */
        @JvmStatic
        fun from(baseValue: Float): AppDimens {
            return AppDimens(baseValue)
        }
        
        /**
         * Factory method from Int
         */
        @JvmStatic
        fun from(baseValue: Int): AppDimens {
            return AppDimens(baseValue.toFloat())
        }
        
        // ============================================
        // LEGACY COMPATIBILITY METHODS
        // ============================================
        
        /**
         * [EN] Creates instance with DEFAULT strategy (legacy "fixed" behavior).
         * @param initialValueDp Base value in dp
         * @param ignoreMultiViewAdjustment Whether to ignore multi-view adjustments
         * @return AppDimens instance configured with DEFAULT strategy
         * [PT] Cria instância com estratégia DEFAULT (comportamento "fixed" legado).
         */
        @JvmStatic
        fun fixed(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean? = null): AppDimens {
            val instance = from(initialValueDp).default()
            if (ignoreMultiViewAdjustment != null) {
                instance.multiViewAdjustment(ignoreMultiViewAdjustment)
            }
            return instance
        }
        
        /**
         * [EN] Creates instance with DEFAULT strategy (legacy "fixed" behavior) from Int.
         * [PT] Cria instância com estratégia DEFAULT (comportamento "fixed" legado) a partir de Int.
         */
        @JvmStatic
        fun fixed(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean? = null): AppDimens {
            return fixed(initialValueInt.toFloat(), ignoreMultiViewAdjustment)
        }
        
        /**
         * [EN] Creates instance with PERCENTAGE strategy (legacy "dynamic" behavior).
         * @param initialValueDp Base value in dp
         * @param ignoreMultiViewAdjustment Whether to ignore multi-view adjustments
         * @return AppDimens instance configured with PERCENTAGE strategy
         * [PT] Cria instância com estratégia PERCENTAGE (comportamento "dynamic" legado).
         */
        @JvmStatic
        fun dynamic(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean? = null): AppDimens {
            val instance = from(initialValueDp).percentage()
            if (ignoreMultiViewAdjustment != null) {
                instance.multiViewAdjustment(ignoreMultiViewAdjustment)
            }
            return instance
        }
        
        /**
         * [EN] Creates instance with PERCENTAGE strategy (legacy "dynamic" behavior) from Int.
         * [PT] Cria instância com estratégia PERCENTAGE (comportamento "dynamic" legado) a partir de Int.
         */
        @JvmStatic
        fun dynamic(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean? = null): AppDimens {
            return dynamic(initialValueInt.toFloat(), ignoreMultiViewAdjustment)
        }
        
        /**
         * [EN] Alias for fixed() - shorter name.
         * [PT] Alias para fixed() - nome mais curto.
         */
        @JvmStatic
        fun fx(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean? = null): AppDimens =
            fixed(initialValueDp, ignoreMultiViewAdjustment)
        
        /**
         * [EN] Alias for fixed() - shorter name (Int).
         * [PT] Alias para fixed() - nome mais curto (Int).
         */
        @JvmStatic
        fun fx(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean? = null): AppDimens =
            fixed(initialValueInt, ignoreMultiViewAdjustment)
        
        /**
         * [EN] Alias for dynamic() - shorter name.
         * [PT] Alias para dynamic() - nome mais curto.
         */
        @JvmStatic
        fun dy(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean? = null): AppDimens =
            dynamic(initialValueDp, ignoreMultiViewAdjustment)
        
        /**
         * [EN] Alias for dynamic() - shorter name (Int).
         * [PT] Alias para dynamic() - nome mais curto (Int).
         */
        @JvmStatic
        fun dy(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean? = null): AppDimens =
            dynamic(initialValueInt, ignoreMultiViewAdjustment)
        
        // ============================================
        // UTILITY METHODS (STATIC)
        // ============================================
        
        /**
         * [EN] Calculates a dynamic dimension value based on a percentage.
         * 
         * @param percentage The percentage (0.0f to 1.0f)
         * @param type The screen dimension to use (LOWEST/HIGHEST)
         * @param resources The Context's Resources
         * @return The adjusted value in Dp as Float
         * 
         * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem.
         */
        @JvmStatic
        @SuppressLint("ConfigurationScreenWidthHeight")
        fun dynamicPercentageDp(
            percentage: Float,
            type: ScreenType = ScreenType.LOWEST,
            resources: Resources
        ): Float {
            require(percentage in 0.0f..1.0f) { "Percentage must be between 0.0f and 1.0f" }

            val configuration = resources.configuration
            val screenWidthDp = configuration.screenWidthDp.toFloat()
            val screenHeightDp = configuration.screenHeightDp.toFloat()

            val dimensionToUse = when (type) {
                ScreenType.HIGHEST -> kotlin.math.max(screenWidthDp, screenHeightDp)
                ScreenType.LOWEST -> kotlin.math.min(screenWidthDp, screenHeightDp)
            }

            return dimensionToUse * percentage
        }
        
        /**
         * [EN] Calculates a dynamic dimension value based on a percentage and converts to Pixels.
         * 
         * @param percentage The percentage (0.0f to 1.0f)
         * @param type The screen dimension to use (LOWEST/HIGHEST)
         * @param resources The Context's Resources
         * @return The adjusted value in Pixels (PX) as Float
         * 
         * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem e converte para Pixels.
         */
        @JvmStatic
        fun dynamicPercentagePx(
            percentage: Float,
            type: ScreenType = ScreenType.LOWEST,
            resources: Resources
        ): Float {
            val dpValue = dynamicPercentageDp(percentage, type, resources)
            return android.util.TypedValue.applyDimension(
                android.util.TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.displayMetrics
            )
        }
        
        /**
         * [EN] Calculates a dynamic dimension value based on a percentage and converts to SP.
         * 
         * @param percentage The percentage (0.0f to 1.0f)
         * @param type The screen dimension to use (LOWEST/HIGHEST)
         * @param resources The Context's Resources
         * @return The adjusted value in Scalable Pixels (SP) as Float
         * 
         * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem e converte para SP.
         */
        @JvmStatic
        fun dynamicPercentageSp(
            percentage: Float,
            type: ScreenType = ScreenType.LOWEST,
            resources: Resources
        ): Float {
            val dpValue = dynamicPercentageDp(percentage, type, resources)
            return android.util.TypedValue.applyDimension(
                android.util.TypedValue.COMPLEX_UNIT_SP, dpValue, resources.displayMetrics
            )
        }
        
        /**
         * [EN] Calculates the maximum number of items that can fit in a container.
         * 
         * @param containerSizePx The size (width or height) of the container in Pixels
         * @param itemSizeDp The size (width or height) of an item in Dp
         * @param itemMarginDp The total margin (in Dp) around each item
         * @param resources The Context's Resources, for Dp -> Px conversion
         * @return The calculated item count (Int)
         * 
         * [PT] Calcula o número máximo de itens que cabem em um contêiner.
         */
        @JvmStatic
        fun calculateAvailableItemCount(
            containerSizePx: Int,
            itemSizeDp: Float,
            itemMarginDp: Float,
            resources: Resources
        ): Int {
            val itemSizePx = android.util.TypedValue.applyDimension(
                android.util.TypedValue.COMPLEX_UNIT_DIP, itemSizeDp, resources.displayMetrics
            )
            val itemMarginPx = android.util.TypedValue.applyDimension(
                android.util.TypedValue.COMPLEX_UNIT_DIP, itemMarginDp, resources.displayMetrics
            )

            val totalItemSizePx = itemSizePx + (itemMarginPx * 2)

            return if (totalItemSizePx > 0f)
                kotlin.math.floor(containerSizePx / totalItemSizePx).toInt()
            else 0
        }
        
        // ============================================
        // GLOBAL CONFIGURATION METHODS
        // ============================================
        
        /**
         * [EN] Global cache control for all instances.
         * [PT] Controle global de cache para todas as instâncias.
         */
        @JvmStatic
        var globalCacheEnabled: Boolean
            get() = AppDimensCore.globalCacheEnabled
            set(value) { AppDimensCore.globalCacheEnabled = value }
        
        /**
         * [EN] Clears all caches from all instances.
         * [PT] Limpa todos os caches de todas as instâncias.
         */
        @JvmStatic
        fun clearAllCaches() {
            AutoCacheFast.clearAll()
        }
        
        /**
         * [EN] Sets the global aspect ratio adjustment setting.
         * [PT] Define a configuração global de ajuste de proporção.
         */
        @JvmStatic
        fun setGlobalAspectRatio(enabled: Boolean) {
            AppDimensCore.setGlobalAspectRatio(enabled)
        }
        
        /**
         * [EN] Sets the global multi-view adjustment setting.
         * [PT] Define a configuração global de ajuste multi-view.
         */
        @JvmStatic
        fun setGlobalIgnoreMultiViewAdjustment(ignore: Boolean) {
            AppDimensCore.setGlobalIgnoreMultiViewAdjustment(ignore)
        }
        
        /**
         * [EN] Gets the current global aspect ratio setting.
         * [PT] Obtém a configuração global atual de proporção.
         */
        @JvmStatic
        fun isGlobalAspectRatioEnabled(): Boolean = AppDimensCore.isGlobalAspectRatioEnabled()
        
        /**
         * [EN] Gets the current global multi-view adjustment setting.
         * [PT] Obtém a configuração global atual de ajuste multi-view.
         */
        @JvmStatic
        fun isGlobalIgnoreMultiViewAdjustment(): Boolean = AppDimensCore.isGlobalIgnoreMultiViewAdjustment()
        
        /**
         * [EN] Sets the global cache setting.
         * [PT] Define a configuração global de cache.
         */
        @JvmStatic
        fun setGlobalCache(enabled: Boolean) {
            globalCacheEnabled = enabled
        }
        
        /**
         * [EN] Gets the current global cache setting.
         * [PT] Obtém a configuração global atual de cache.
         */
        @JvmStatic
        fun isGlobalCacheEnabled(): Boolean = globalCacheEnabled
        
        // ============================================
        // CACHE PERSISTENCE CONTROL
        // ============================================
        
        /**
         * [EN] Sets the cache entry persistence time in milliseconds.
         * 
         * Controls how long calculated values remain in cache before expiring.
         * 
         * Special values:
         * - **Positive value**: Cache expires after N milliseconds (default: 30 minutes)
         * - **0 or CACHE_UNLIMITED**: Unlimited - cache never expires by age, only by LRU
         * - **-1 or CACHE_NO_PERSISTENCE**: No persistence - entries expire immediately (for testing)
         * 
         * Examples:
         * ```kotlin
         * // 1 hour
         * AppDimens.setCachePersistence(60 * 60 * 1000L)
         * 
         * // Unlimited (recommended for production apps with stable layouts)
         * AppDimens.setCachePersistence(AppDimens.CACHE_UNLIMITED)
         * 
         * // No persistence (for debugging/testing)
         * AppDimens.setCachePersistence(AppDimens.CACHE_NO_PERSISTENCE)
         * ```
         * 
         * [PT] Define o tempo de persistência das entradas do cache em milissegundos.
         * 
         * @param timeMs Time in milliseconds (positive), 0 for unlimited, or -1 for no persistence
         */
        @JvmStatic
        fun setCachePersistence(timeMs: Long) {
            AutoCacheFast.maxEntryAgeMs = timeMs
        }
        
        /**
         * [EN] Sets cache to unlimited persistence (never expire by age).
         * Entries only removed by LRU when cache is full.
         * Recommended for production apps with stable layouts.
         * 
         * [PT] Define cache com persistência ilimitada (nunca expira por tempo).
         */
        @JvmStatic
        fun setCacheUnlimited() {
            AutoCacheFast.maxEntryAgeMs = 0L // CACHE_UNLIMITED
        }
        
        /**
         * [EN] Sets cache to no persistence mode (entries expire immediately).
         * Useful for debugging and testing when you want fresh calculations.
         * 
         * [PT] Define cache em modo sem persistência (entradas expiram imediatamente).
         */
        @JvmStatic
        fun setCacheNoPersistence() {
            AutoCacheFast.maxEntryAgeMs = -1L // CACHE_NO_PERSISTENCE
        }
        
        /**
         * [EN] Resets cache persistence to default (30 minutes).
         * 
         * [PT] Redefine persistência do cache para padrão (30 minutos).
         */
        @JvmStatic
        fun resetCachePersistence() {
            AutoCacheFast.maxEntryAgeMs = 30 * 60 * 1000L // 30 minutes
        }
        
        /**
         * [EN] Constants for cache persistence control.
         * 
         * [PT] Constantes para controle de persistência do cache.
         */
        const val CACHE_UNLIMITED = 0L
        const val CACHE_NO_PERSISTENCE = -1L
        
        // ============================================
        // AUTOSIZE TEXT HELPERS (STATIC)
        // ============================================
        
        /**
         * [EN] Helper to create auto-sized text dimensions quickly.
         * 
         * Creates an AppDimens instance configured for text auto-sizing.
         * Useful for creating TextView with auto-sizing text.
         * 
         * @param baseSize Base text size in sp (used as starting point)
         * @param minSize Minimum text size in sp
         * @param maxSize Maximum text size in sp
         * @return AppDimens instance configured for text auto-sizing
         * 
         * @example
         * ```kotlin
         * // Create auto-sized text
         * val textView = TextView(context)
         * 
         * // Method 1: Using helper
         * val fontSize = AppDimens.autoSizedText(
         *     baseSize = 16f,
         *     minSize = 12f,
         *     maxSize = 24f
         * ).toSp(resources)
         * 
         * textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
         * 
         * // Method 2: With container measurement
         * textView.viewTreeObserver.addOnGlobalLayoutListener {
         *     val widthDp = textView.width / resources.displayMetrics.density
         *     val heightDp = textView.height / resources.displayMetrics.density
         *     
         *     val adaptiveSize = AppDimens.autoSizedText(16f, 12f, 24f)
         *         .calculateForSize(widthDp, heightDp, resources.configuration)
         *     
         *     textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, adaptiveSize)
         * }
         * ```
         */
        @JvmStatic
        fun autoSizedText(
            baseSize: Float,
            minSize: Float,
            maxSize: Float
        ): AppDimens {
            return from(baseSize).autoSizeText(minSize, maxSize, AutoSizeMode.UNIFORM)
        }
        
        /**
         * [EN] Helper to create auto-sized text with preset sizes.
         * 
         * Creates an AppDimens instance that selects from preset text sizes.
         * Similar to TextView.setAutoSizeTextTypeUniformWithPresets().
         * 
         * @param baseSize Base text size in sp
         * @param presetSizes Array of preset text sizes in sp
         * @return AppDimens instance configured for preset text auto-sizing
         * 
         * @example
         * ```kotlin
         * val presets = floatArrayOf(10f, 12f, 14f, 16f, 18f, 20f, 24f)
         * val fontSize = AppDimens.autoSizedTextPresets(16f, presets)
         *     .toSp(resources)
         * ```
         */
        @JvmStatic
        fun autoSizedTextPresets(
            baseSize: Float,
            presetSizes: FloatArray
        ): AppDimens {
            return from(baseSize).autoSizeTextPresets(presetSizes)
        }
    }
    
    // ============================================
    // STRATEGY SELECTION
    // ============================================
    
    /**
     * Set explicit scaling strategy
     */
    fun strategy(strategy: ScalingStrategy): AppDimens {
        forcedStrategy = strategy
        return this
    }
    
    /**
     * Provide element type hint for auto-inference
     */
    fun forElement(type: ElementType): AppDimens {
        elementType = type
        return this
    }
    
    // ============================================
    // STRATEGY SHORTCUTS
    // ============================================
    
    /**
     * Use DEFAULT strategy (Fixed legacy)
     */
    @JvmName("useDefault")
    fun default(enableAR: Boolean = true, arSensitivity: Float? = null): AppDimens {
        forcedStrategy = ScalingStrategy.DEFAULT
        applyAspectRatioAdjustment = enableAR
        fixedArSensitivity = arSensitivity
        return this
    }
    
    /**
     * Use PERCENTAGE strategy (Dynamic legacy)
     */
    fun percentage(): AppDimens {
        forcedStrategy = ScalingStrategy.PERCENTAGE
        return this
    }
    
    /**
     * Use BALANCED strategy (Perceptual Hybrid)
     */
    fun balanced(sensitivity: Float? = null): AppDimens {
        forcedStrategy = ScalingStrategy.BALANCED
        perceptualModel = PerceptualModel.BALANCED
        if (sensitivity != null) perceptualSensitivity = sensitivity
        return this
    }
    
    /**
     * Use LOGARITHMIC strategy (Perceptual Weber-Fechner)
     */
    fun logarithmic(sensitivity: Float? = null): AppDimens {
        forcedStrategy = ScalingStrategy.LOGARITHMIC
        perceptualModel = PerceptualModel.LOGARITHMIC
        if (sensitivity != null) perceptualSensitivity = sensitivity
        return this
    }
    
    /**
     * Use POWER strategy (Perceptual Stevens)
     */
    fun power(exponent: Float? = null): AppDimens {
        forcedStrategy = ScalingStrategy.POWER
        perceptualModel = PerceptualModel.POWER
        if (exponent != null) perceptualPowerExponent = exponent
        return this
    }
    
    /**
     * Sets FLUID strategy (CSS clamp-like).
     * 
     * FLUID strategy provides responsive scaling with min/max bounds and breakpoints.
     * Formula: clamp(minValue, interpolate(width), maxValue)
     * 
     * Best for: Elements that need responsive design with breakpoints, fluid typography.
     * 
     * @param minValue Minimum value in dp. Returned when screen width ≤ minWidth.
     *                Must be positive and less than maxValue.
     * @param maxValue Maximum value in dp. Returned when screen width ≥ maxWidth.
     *                Must be positive and greater than minValue.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if minValue >= maxValue or if either value is negative.
     * 
     * @see ScalingStrategy.FLUID
     * @see fluidBreakpoints for setting breakpoints
     * @see fluidDevice for device-specific qualifiers
     * 
     * @example
     * ```kotlin
     * val size = AppDimens.from(48f)
     *     .fluid(minValue = 40f, maxValue = 80f)
     *     .toDp(configuration)
     * ```
     */
    fun fluid(minValue: Float, maxValue: Float): AppDimens {
        require(minValue >= 0f) { 
            "FLUID minValue must be non-negative, but was $minValue. " +
            "Negative dimensions are not allowed."
        }
        require(maxValue >= 0f) { 
            "FLUID maxValue must be non-negative, but was $maxValue. " +
            "Negative dimensions are not allowed."
        }
        require(minValue < maxValue) { 
            "FLUID minValue ($minValue) must be less than maxValue ($maxValue). " +
            "Otherwise, the fluid range would be invalid."
        }
        
        forcedStrategy = ScalingStrategy.FLUID
        this.fluidMinValue = minValue
        this.fluidMaxValue = maxValue
        return this
    }
    
    /**
     * Use INTERPOLATED strategy
     */
    fun interpolated(): AppDimens {
        forcedStrategy = ScalingStrategy.INTERPOLATED
        return this
    }
    
    /**
     * Use DIAGONAL strategy
     */
    fun diagonal(): AppDimens {
        forcedStrategy = ScalingStrategy.DIAGONAL
        return this
    }
    
    /**
     * Use PERIMETER strategy
     */
    fun perimeter(): AppDimens {
        forcedStrategy = ScalingStrategy.PERIMETER
        return this
    }
    
    /**
     * Use FIT strategy (game letterbox)
     */
    fun fit(): AppDimens {
        forcedStrategy = ScalingStrategy.FIT
        return this
    }
    
    /**
     * Use FILL strategy (game cover)
     */
    fun fill(): AppDimens {
        forcedStrategy = ScalingStrategy.FILL
        return this
    }
    
    /**
     * Use NONE strategy (no scaling)
     */
    fun none(): AppDimens {
        forcedStrategy = ScalingStrategy.NONE
        return this
    }
    
    /**
     * Sets AUTOSIZE strategy with uniform mode.
     * 
     * AUTOSIZE strategy automatically adjusts dimensions based on container size.
     * Uniform mode scales uniformly between min and max values.
     * 
     * @param minValue Minimum value in dp. Must be positive and less than maxValue.
     * @param maxValue Maximum value in dp. Must be positive and greater than minValue.
     * @param mode AutoSize mode (UNIFORM or PRESET). Defaults to UNIFORM.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if minValue >= maxValue or if either value is negative.
     * 
     * @see AutoSizeMode
     * @see ScalingStrategy.AUTOSIZE
     */
    fun autoSize(minValue: Float, maxValue: Float, mode: AutoSizeMode = AutoSizeMode.UNIFORM): AppDimens {
        require(minValue >= 0f) { 
            "AUTOSIZE minValue must be non-negative, but was $minValue. " +
            "Negative dimensions are not allowed."
        }
        require(maxValue >= 0f) { 
            "AUTOSIZE maxValue must be non-negative, but was $maxValue. " +
            "Negative dimensions are not allowed."
        }
        require(minValue < maxValue) { 
            "AUTOSIZE minValue ($minValue) must be less than maxValue ($maxValue). " +
            "Otherwise, the auto-size range would be invalid."
        }
        
        forcedStrategy = ScalingStrategy.AUTOSIZE
        autoSizeEnabled = true
        autoSizeMinValue = minValue
        autoSizeMaxValue = maxValue
        autoSizeMode = mode
        return this
    }
    
    /**
     * Sets AUTOSIZE strategy with preset sizes.
     * 
     * AUTOSIZE strategy automatically adjusts dimensions based on container size.
     * Preset mode selects from predefined size values.
     * 
     * @param sizes Array of preset sizes in dp. Must be non-empty, sorted ascending, and all values positive.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if sizes array is empty or contains negative values.
     * 
     * @see AutoSizeMode.PRESET
     * @see ScalingStrategy.AUTOSIZE
     */
    fun autoSizePresets(sizes: FloatArray): AppDimens {
        require(sizes.isNotEmpty()) { 
            "AUTOSIZE preset sizes array cannot be empty. " +
            "Provide at least one preset size value."
        }
        require(sizes.all { it >= 0f }) { 
            "AUTOSIZE preset sizes must be non-negative. " +
            "Found negative value(s): ${sizes.filter { it < 0f }.joinToString()}"
        }
        
        forcedStrategy = ScalingStrategy.AUTOSIZE
        autoSizeEnabled = true
        autoSizeMode = AutoSizeMode.PRESET
        autoSizePresetSizes = sizes
        return this
    }
    
    /**
     * Enable autoSize for FLUID strategy
     */
    fun fluidAutoSize(enable: Boolean = true): AppDimens {
        if (forcedStrategy == ScalingStrategy.FLUID || forcedStrategy == null) {
            autoSizeEnabled = enable
        }
        return this
    }
    
    /**
     * Helper method: fit content to container
     */
    fun fitToContainer(minValue: Float, maxValue: Float): AppDimens {
        return autoSize(minValue, maxValue, AutoSizeMode.UNIFORM)
    }
    
    /**
     * [EN] AutoSize specifically for text/font sizing.
     * 
     * Similar to TextView's setAutoSizeTextTypeWithDefaults(), this method automatically
     * adjusts text size to fit within available width and/or height constraints.
     * 
     * The text size will scale uniformly between minTextSize and maxTextSize to ensure
     * the text fits within the container bounds. This is ideal for:
     * - Labels that must fit in variable-width containers
     * - Responsive typography that adapts to available space
     * - Text that should never overflow its bounds
     * 
     * @param minTextSize Minimum text size in sp. The text will never be smaller than this.
     *                    Typical values: 8sp-12sp for body text, 12sp-16sp for titles.
     *                    Must be positive and less than maxTextSize.
     * @param maxTextSize Maximum text size in sp. The text will never be larger than this.
     *                    Typical values: 14sp-18sp for body text, 20sp-32sp for titles.
     *                    Must be positive and greater than minTextSize.
     * @param mode AutoSize mode: UNIFORM (proportional scaling) or PRESET (discrete sizes).
     *             Defaults to UNIFORM for smooth scaling.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if minTextSize >= maxTextSize or either value is negative.
     * 
     * @see AutoSizeMode
     * @see ScalingStrategy.AUTOSIZE
     * 
     * @example
     * ```kotlin
     * // Auto-size text between 12sp and 24sp
     * val textSize = AppDimens.from(16f)
     *     .autoSizeText(minTextSize = 12f, maxTextSize = 24f)
     *     .toSp(resources)
     * 
     * // Usage with TextView
     * textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
     * 
     * // Compose usage
     * @Composable
     * fun AutoSizedText(text: String) {
     *     BoxWithConstraints {
     *         val textSize = AppDimens.from(16)
     *             .autoSizeText(12f, 24f)
     *             .calculateForSizeWithConfig(
     *                 maxWidth.value, 
     *                 maxHeight.value, 
     *                 LocalConfiguration.current
     *             )
     *         Text(text, fontSize = textSize.sp)
     *     }
     * }
     * ```
     */
    fun autoSizeText(
        minTextSize: Float,
        maxTextSize: Float,
        mode: AutoSizeMode = AutoSizeMode.UNIFORM
    ): AppDimens {
        require(minTextSize >= 0f) { 
            "AUTOSIZE TEXT minTextSize must be non-negative, but was $minTextSize. " +
            "Negative text sizes are not allowed."
        }
        require(maxTextSize >= 0f) { 
            "AUTOSIZE TEXT maxTextSize must be non-negative, but was $maxTextSize. " +
            "Negative text sizes are not allowed."
        }
        require(minTextSize < maxTextSize) { 
            "AUTOSIZE TEXT minTextSize ($minTextSize sp) must be less than maxTextSize ($maxTextSize sp). " +
            "Otherwise, the auto-size range would be invalid."
        }
        
        forcedStrategy = ScalingStrategy.AUTOSIZE
        autoSizeEnabled = true
        autoSizeMinValue = minTextSize
        autoSizeMaxValue = maxTextSize
        autoSizeMode = mode
        return this
    }
    
    /**
     * [EN] AutoSize text with preset font sizes.
     * 
     * Similar to TextView's setAutoSizeTextTypeUniformWithPresets(), this method
     * automatically selects the largest preset text size that fits within the
     * available space.
     * 
     * Preset mode is useful when you want discrete text size steps (e.g., 12sp, 14sp, 16sp)
     * rather than continuous scaling. The system will select the largest size that fits.
     * 
     * @param presetTextSizes Array of preset text sizes in sp. Must be non-empty and sorted
     *                       ascending. All values must be positive.
     *                       Example: floatArrayOf(10f, 12f, 14f, 16f, 18f, 20f, 24f)
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if array is empty or contains negative values.
     * 
     * @see AutoSizeMode.PRESET
     * @see ScalingStrategy.AUTOSIZE
     * 
     * @example
     * ```kotlin
     * // Auto-size with preset sizes
     * val presets = floatArrayOf(10f, 12f, 14f, 16f, 18f, 20f, 24f)
     * val textSize = AppDimens.from(16f)
     *     .autoSizeTextPresets(presets)
     *     .calculateForSize(containerWidth, containerHeight, configuration)
     * 
     * // The system will select the largest size that fits
     * // If container is small: might select 12sp
     * // If container is large: might select 20sp or 24sp
     * ```
     */
    fun autoSizeTextPresets(presetTextSizes: FloatArray): AppDimens {
        require(presetTextSizes.isNotEmpty()) { 
            "AUTOSIZE TEXT preset sizes array cannot be empty. " +
            "Provide at least one preset text size value."
        }
        require(presetTextSizes.all { it >= 0f }) { 
            "AUTOSIZE TEXT preset sizes must be non-negative. " +
            "Found negative value(s): ${presetTextSizes.filter { it < 0f }.joinToString()}"
        }
        
        forcedStrategy = ScalingStrategy.AUTOSIZE
        autoSizeEnabled = true
        autoSizeMode = AutoSizeMode.PRESET
        autoSizePresetSizes = presetTextSizes.sortedArray() // Ensure sorted
        return this
    }
    
    /**
     * [EN] AutoSize text with granularity control.
     * 
     * Similar to TextView's setAutoSizeTextTypeUniformWithConfiguration(), this provides
     * fine-grained control over text size steps.
     * 
     * Instead of continuous scaling or fixed presets, this generates a range of sizes
     * with a specific step granularity (e.g., every 1sp or 2sp).
     * 
     * @param minTextSize Minimum text size in sp
     * @param maxTextSize Maximum text size in sp
     * @param stepGranularitySp Step size in sp between auto-size values.
     *                         Example: stepGranularity = 2f generates [12, 14, 16, 18, 20...]
     *                         Typical values: 1f (fine control) or 2f (coarse control)
     *                         Must be positive and less than (maxTextSize - minTextSize)
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if parameters are invalid
     * 
     * @example
     * ```kotlin
     * // Generate sizes: 12sp, 14sp, 16sp, 18sp, 20sp, 22sp, 24sp
     * val textSize = AppDimens.from(16f)
     *     .autoSizeTextGranular(
     *         minTextSize = 12f,
     *         maxTextSize = 24f,
     *         stepGranularitySp = 2f
     *     )
     *     .toSp(resources)
     * ```
     */
    fun autoSizeTextGranular(
        minTextSize: Float,
        maxTextSize: Float,
        stepGranularitySp: Float
    ): AppDimens {
        require(minTextSize >= 0f) { 
            "AUTOSIZE TEXT minTextSize must be non-negative, but was $minTextSize."
        }
        require(maxTextSize >= 0f) { 
            "AUTOSIZE TEXT maxTextSize must be non-negative, but was $maxTextSize."
        }
        require(minTextSize < maxTextSize) { 
            "AUTOSIZE TEXT minTextSize ($minTextSize sp) must be less than maxTextSize ($maxTextSize sp)."
        }
        require(stepGranularitySp > 0f) { 
            "AUTOSIZE TEXT stepGranularity must be positive, but was $stepGranularitySp."
        }
        require(stepGranularitySp < (maxTextSize - minTextSize)) { 
            "AUTOSIZE TEXT stepGranularity ($stepGranularitySp sp) must be less than range " +
            "(${maxTextSize - minTextSize} sp)."
        }
        
        // Generate preset array from granularity
        val presets = mutableListOf<Float>()
        var current = minTextSize
        while (current <= maxTextSize) {
            presets.add(current)
            current += stepGranularitySp
        }
        
        forcedStrategy = ScalingStrategy.AUTOSIZE
        autoSizeEnabled = true
        autoSizeMode = AutoSizeMode.PRESET
        autoSizePresetSizes = presets.toFloatArray()
        return this
    }
    
    // ============================================
    // PERCEPTUAL-SPECIFIC CONFIGURATION
    // ============================================
    
    /**
     * Configure aspect ratio for Fixed/DEFAULT strategy.
     * 
     * @param apply Whether to apply aspect ratio adjustment.
     * @param sensitivity Aspect ratio sensitivity factor. If null, uses default.
     * @return This builder instance for method chaining.
     */
    fun aspectRatio(apply: Boolean, sensitivity: Float? = null): AppDimens {
        applyAspectRatioAdjustment = apply
        if (sensitivity != null) fixedArSensitivity = sensitivity
        return this
    }
    
    /**
     * Configure aspect ratio for Perceptual strategies.
     * 
     * @param apply Whether to apply aspect ratio adjustment.
     * @param sensitivity Aspect ratio sensitivity factor. Defaults to library default.
     * @return This builder instance for method chaining.
     */
    fun perceptualAspectRatio(apply: Boolean, sensitivity: Float = Calculator.DEFAULT_AR_SENSITIVITY): AppDimens {
        perceptualApplyAR = apply
        perceptualArSensitivity = sensitivity
        return this
    }
    
    // ============================================
    // FLUID-SPECIFIC CONFIGURATION
    // ============================================
    
    /**
     * Sets fluid breakpoints for FLUID strategy.
     * 
     * Defines the width range where fluid interpolation occurs.
     * 
     * @param minWidth Minimum width breakpoint in dp. Must be positive and less than maxWidth.
     *                Defaults to 320dp (typical phone width).
     * @param maxWidth Maximum width breakpoint in dp. Must be positive and greater than minWidth.
     *                Defaults to 768dp (typical tablet width).
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if minWidth >= maxWidth or if either value is negative.
     */
    fun fluidBreakpoints(minWidth: Float, maxWidth: Float): AppDimens {
        require(minWidth >= 0f) { 
            "FLUID minWidth must be non-negative, but was $minWidth. " +
            "Breakpoint widths must be positive."
        }
        require(maxWidth >= 0f) { 
            "FLUID maxWidth must be non-negative, but was $maxWidth. " +
            "Breakpoint widths must be positive."
        }
        require(minWidth < maxWidth) { 
            "FLUID minWidth ($minWidth) must be less than maxWidth ($maxWidth). " +
            "Otherwise, the breakpoint range would be invalid."
        }
        
        fluidMinWidth = minWidth
        fluidMaxWidth = maxWidth
        return this
    }
    
    /**
     * Sets fluid values for specific device type.
     * 
     * Allows different min/max values for different device types (phone, tablet, TV, watch).
     * Device qualifiers override default fluid values when the device type matches.
     * 
     * @param deviceType Device type to configure.
     * @param minValue Minimum value in dp for this device type.
     * @param maxValue Maximum value in dp for this device type.
     * @param minWidth Minimum width breakpoint in dp. Defaults to current fluidMinWidth.
     * @param maxWidth Maximum width breakpoint in dp. Defaults to current fluidMaxWidth.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if minValue >= maxValue or if any value is negative.
     */
    fun fluidDevice(
        deviceType: FluidDeviceType,
        minValue: Float,
        maxValue: Float,
        minWidth: Float = fluidMinWidth,
        maxWidth: Float = fluidMaxWidth
    ): AppDimens {
        require(minValue >= 0f) { 
            "FLUID device qualifier minValue must be non-negative, but was $minValue for $deviceType."
        }
        require(maxValue >= 0f) { 
            "FLUID device qualifier maxValue must be non-negative, but was $maxValue for $deviceType."
        }
        require(minValue < maxValue) { 
            "FLUID device qualifier minValue ($minValue) must be less than maxValue ($maxValue) for $deviceType."
        }
        require(minWidth >= 0f) { 
            "FLUID device qualifier minWidth must be non-negative, but was $minWidth for $deviceType."
        }
        require(maxWidth >= 0f) { 
            "FLUID device qualifier maxWidth must be non-negative, but was $maxWidth for $deviceType."
        }
        require(minWidth < maxWidth) { 
            "FLUID device qualifier minWidth ($minWidth) must be less than maxWidth ($maxWidth) for $deviceType."
        }
        
        fluidDeviceQualifiers[deviceType] = FluidConfig(
            minValue, maxValue, minWidth, maxWidth,
            baseOrientation, screenType
        )
        return this
    }
    
    /**
     * Set fluid values for screen qualifier
     */
    fun fluidScreen(
        qualifier: Int,
        minValue: Float,
        maxValue: Float,
        minWidth: Float = fluidMinWidth,
        maxWidth: Float = fluidMaxWidth
    ): AppDimens {
        fluidScreenQualifiers[qualifier] = FluidConfig(
            minValue, maxValue, minWidth, maxWidth,
            baseOrientation, screenType
        )
        return this
    }
    
    // ============================================
    // UNIVERSAL CONSTRAINTS
    // ============================================
    
    /**
     * Sets minimum and maximum value bounds.
     * 
     * Constrains the calculated value to be within the specified bounds.
     * These constraints are applied after strategy-specific calculation.
     * 
     * @param min Minimum value in dp. The calculated value will never be less than this.
     *           Must be non-negative and less than max.
     * @param max Maximum value in dp. The calculated value will never be greater than this.
     *           Must be positive and greater than min.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if min >= max or if either value is negative.
     * 
     * @example
     * ```kotlin
     * val size = AppDimens.from(48f)
     *     .balanced()
     *     .bounds(min = 40f, max = 80f)
     *     .toDp(configuration)
     * // Result will be clamped between 40dp and 80dp
     * ```
     */
    fun bounds(min: Float, max: Float): AppDimens {
        require(min >= 0f) { 
            "Bounds min value must be non-negative, but was $min. " +
            "Negative dimensions are not allowed."
        }
        require(max >= 0f) { 
            "Bounds max value must be non-negative, but was $max. " +
            "Negative dimensions are not allowed."
        }
        require(min < max) { 
            "Bounds min value ($min) must be less than max value ($max). " +
            "Otherwise, the bounds range would be invalid."
        }
        
        this.minValue = min
        this.maxValue = max
        return this
    }
    
    /**
     * Sets maximum physical size constraint.
     * 
     * Ensures the element doesn't exceed a physical size limit regardless of screen density.
     * Useful for accessibility and ensuring elements are usable on all devices.
     * 
     * @param mm Maximum physical size in millimeters. Must be positive.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if mm is negative or zero.
     * 
     * @example
     * ```kotlin
     * val size = AppDimens.from(48f)
     *     .balanced()
     *     .maxPhysicalSize(15f) // Maximum 15mm physical size
     *     .toDp(configuration)
     * ```
     */
    fun maxPhysicalSize(mm: Float): AppDimens {
        require(mm > 0f) { 
            "Maximum physical size must be positive, but was $mm. " +
            "Physical size constraints must be greater than zero."
        }
        
        this.maxPhysicalMm = mm
        return this
    }
    
    /**
     * Sets POWER strategy exponent.
     * 
     * Controls the scaling curve for POWER strategy:
     * - exponent = 1.0: Linear scaling (same as PERCENTAGE)
     * - exponent < 1.0: Conservative scaling (recommended: 0.5-0.7)
     * - exponent → 0: Approaches constant scaling
     * 
     * @param exponent Power exponent. Must be between 0.0 and 1.0 (inclusive).
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if exponent is outside valid range [0.0, 1.0].
     */
    fun powerExponent(exponent: Float): AppDimens {
        require(exponent in 0.0f..1.0f) { 
            "POWER exponent must be between 0.0 and 1.0 (inclusive), but was $exponent. " +
            "Recommended range: 0.5-0.7 for conservative scaling."
        }
        
        perceptualPowerExponent = exponent
        return this
    }
    
    /**
     * Sets transition point for BALANCED model.
     * 
     * The transition point determines where BALANCED strategy switches from
     * linear to logarithmic scaling. Screens smaller than this use linear scaling,
     * larger screens use logarithmic scaling.
     * 
     * @param point Transition point in dp. Must be positive.
     *             Recommended: 480dp (default) for typical transition between phones and tablets.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if point is negative or zero.
     */
    fun transitionPoint(point: Float): AppDimens {
        require(point > 0f) { 
            "Transition point must be positive, but was $point. " +
            "It represents the screen width (in dp) where BALANCED strategy switches from linear to logarithmic scaling."
        }
        
        perceptualTransitionPoint = point
        return this
    }
    
    /**
     * Sets sensitivity for perceptual strategies.
     * 
     * Controls the aggressiveness of logarithmic scaling for BALANCED and LOGARITHMIC strategies.
     * Higher values increase scaling aggressiveness.
     * 
     * @param sensitivity Sensitivity factor. Must be between 0.0 and 1.0 (inclusive).
     *                  Recommended: 0.3-0.5 for balanced scaling.
     * @return This builder instance for method chaining.
     * @throws IllegalArgumentException if sensitivity is outside valid range [0.0, 1.0].
     */
    fun sensitivity(sensitivity: Float): AppDimens {
        require(sensitivity in 0.0f..1.0f) { 
            "Perceptual sensitivity must be between 0.0 and 1.0 (inclusive), but was $sensitivity. " +
            "Recommended range: 0.3-0.5 for balanced scaling."
        }
        
        perceptualSensitivity = sensitivity
        return this
    }
    
    // ============================================
    // SCREEN QUALIFIERS
    // ============================================
    
    /**
     * Priority 2: UI Mode only
     */
    fun screen(type: UiModeType, customValue: Float): AppDimens {
        customUiModeMap[type] = customValue
        return this
    }
    
    /**
     * Priority 3: DpQualifier only
     */
    fun screen(type: DpQualifier, value: Int, customValue: Float): AppDimens {
        customDpMap[DpQualifierEntry(type, value)] = customValue
        return this
    }
    
    /**
     * Priority 1: Intersection (UiMode + DpQualifier)
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Float
    ): AppDimens {
        val key = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue)
        )
        customIntersectionMap[key] = customValue
        return this
    }
    
    // ============================================
    // ORIENTATION & SCREEN TYPE
    // ============================================
    
    fun type(type: ScreenType): AppDimens {
        screenType = type
        return this
    }
    
    fun baseOrientation(orientation: BaseOrientation): AppDimens {
        baseOrientation = orientation
        return this
    }
    
    fun portraitLowest(): AppDimens {
        baseOrientation = BaseOrientation.PORTRAIT
        screenType = ScreenType.LOWEST
        return this
    }
    
    fun portraitHighest(): AppDimens {
        baseOrientation = BaseOrientation.PORTRAIT
        screenType = ScreenType.HIGHEST
        return this
    }
    
    fun landscapeLowest(): AppDimens {
        baseOrientation = BaseOrientation.LANDSCAPE
        screenType = ScreenType.LOWEST
        return this
    }
    
    fun landscapeHighest(): AppDimens {
        baseOrientation = BaseOrientation.LANDSCAPE
        screenType = ScreenType.HIGHEST
        return this
    }
    
    // ============================================
    // OTHER
    // ============================================
    
    fun multiViewAdjustment(ignore: Boolean): AppDimens {
        ignoreMultiViewAdjustment = ignore
        return this
    }
    
    fun cache(enable: Boolean): AppDimens {
        enableCache = enable
        return this
    }
    
    // ============================================
    // OUTPUT METHODS
    // ============================================
    
    /**
     * Calculate dimension for specific container size (AutoSize)
     * 
     * @param containerWidth Container width in dp
     * @param containerHeight Container height in dp
     * @param configuration Android configuration
     * @return Calculated size in dp
     */
    fun calculateForSize(
        containerWidth: Float,
        containerHeight: Float,
        configuration: Configuration
    ): Float {
        if (!autoSizeEnabled || forcedStrategy != ScalingStrategy.AUTOSIZE) {
            return calculate(configuration)
        }
        
        // AutoSize logic
        val availableSize = minOf(containerWidth, containerHeight)
        val targetSize = baseValue
        val min = autoSizeMinValue ?: (targetSize * 0.5f)
        val max = autoSizeMaxValue ?: (targetSize * 2.0f)
        
        return when (autoSizeMode) {
            AutoSizeMode.UNIFORM -> {
                // Proportional adjustment based on available space
                // Scale factor based on how much space we have relative to a reference
                val referenceSizeDp = 100f
                val scaleFactor = availableSize / referenceSizeDp
                val adjustedSize = targetSize * scaleFactor
                adjustedSize.coerceIn(min, max)
            }
            AutoSizeMode.PRESET -> {
                // Find best matching preset size
                val presets = autoSizePresetSizes
                if (presets != null && presets.isNotEmpty()) {
                    // Find largest preset that fits
                    presets.filter { it <= availableSize }.maxOrNull() ?: presets.first()
                } else {
                    targetSize.coerceIn(min, max)
                }
            }
        }
    }
    
    /**
     * Calculate and return dimension in dp
     */
    fun toDp(configuration: Configuration): Float {
        return calculate(configuration)
    }
    
    /**
     * Calculate and return dimension in sp
     */
    fun toSp(configuration: Configuration): Float {
        return calculate(configuration)
    }
    
    /**
     * Calculate from Resources
     */
    fun toDp(resources: Resources): Float {
        return calculate(resources.configuration)
    }
    
    /**
     * Calculate from Resources
     */
    fun toSp(resources: Resources): Float {
        return calculate(resources.configuration)
    }
    
    // ============================================
    // CALCULATION ENGINE
    // ============================================
    
    /**
     * Calculates the scaled dimension value using the unified calculation engine.
     * 
     * OPTIMIZED: Integrated with AutoCacheFast for ultra-fast lock-free caching.
     * Respects both global (AppDimensCore.globalCacheEnabled) and instance-level 
     * (enableCache) cache settings.
     * 
     * Cache behavior:
     * - Enabled: Results are cached based on all calculation parameters
     * - Disabled: Direct calculation without caching
     * - Global override: AppDimensCore.globalCacheEnabled=false disables all caching
     * 
     * Delegates to AppDimensCalculator for all strategy calculations.
     */
    private fun calculate(configuration: Configuration): Float {
        // Convert Configuration to CalculationConfig
        val calcConfig = Calculator.CalculationConfig(
            screenWidthDp = configuration.screenWidthDp.toFloat(),
            screenHeightDp = configuration.screenHeightDp.toFloat(),
            smallestScreenWidthDp = configuration.smallestScreenWidthDp.toFloat(),
            densityDpi = configuration.densityDpi,
            uiMode = UiModeType.fromConfiguration(configuration.uiMode)
        )
        
        // Build calculation parameters
        val defaultParams = Calculator.DefaultParams(
            applyAspectRatio = applyAspectRatioAdjustment,
            arSensitivity = fixedArSensitivity
        )
        
        val perceptualParams = Calculator.PerceptualParams(
            model = perceptualModel,
            sensitivity = perceptualSensitivity,
            powerExponent = perceptualPowerExponent,
            transitionPoint = perceptualTransitionPoint,
            applyAspectRatio = perceptualApplyAR,
            arSensitivity = perceptualArSensitivity
        )
        
        val fluidParams = if (forcedStrategy == ScalingStrategy.FLUID || fluidDeviceQualifiers.isNotEmpty() || fluidScreenQualifiers.isNotEmpty()) {
            Calculator.FluidParams(
                minValue = fluidMinValue ?: baseValue * 0.8f,
                maxValue = fluidMaxValue ?: baseValue * 1.2f,
                minWidth = fluidMinWidth,
                maxWidth = fluidMaxWidth,
                deviceQualifiers = fluidDeviceQualifiers,
                screenQualifiers = fluidScreenQualifiers
            )
        } else {
            null
        }
        
        val constraints = Calculator.Constraints(
            minValue = minValue,
            maxValue = maxValue,
            maxPhysicalMm = maxPhysicalMm
        )
        
        val customQualifiers = Calculator.CustomQualifiers(
            intersectionMap = customIntersectionMap,
            uiModeMap = customUiModeMap,
            dpMap = customDpMap
        )
        
        // Check if caching is enabled (global AND instance-level)
        val isCacheEnabled = AppDimensCore.isGlobalCacheEnabled() && enableCache
        
        if (isCacheEnabled) {
            // CACHE ENABLED: Use AutoCacheFast for ultra-fast lock-free caching
            
            // Compute stable hash from all calculation parameters
            var hash = AutoCacheFast.computeHash(
                baseValue = baseValue,
                screenWidthDp = calcConfig.screenWidthDp,
                screenHeightDp = calcConfig.screenHeightDp,
                smallestWidthDp = calcConfig.smallestScreenWidthDp,
                strategyOrdinal = forcedStrategy?.ordinal ?: -1
            )
            
            // Mix in additional parameters to ensure unique hash
            hash = hash xor System.identityHashCode(this)
            hash = hash xor (elementType?.ordinal ?: 0)
            hash = hash xor screenType.ordinal
            hash = hash xor baseOrientation.ordinal
            
            // Use ultra-fast lock-free cache
            return AutoCacheFast.rememberFast(hash) {
                // Compute value (only called on cache miss or hash change)
                Calculator.calculate(
                    baseValue = baseValue,
                    strategy = forcedStrategy,
                    elementType = elementType,
                    config = calcConfig,
                    screenType = screenType,
                    baseOrientation = baseOrientation,
                    defaultParams = defaultParams,
                    perceptualParams = perceptualParams,
                    fluidParams = fluidParams,
                    constraints = constraints,
                    customQualifiers = customQualifiers
                )
            }
        } else {
            // CACHE DISABLED: Direct calculation without caching
            return Calculator.calculate(
                baseValue = baseValue,
                strategy = forcedStrategy,
                elementType = elementType,
                config = calcConfig,
                screenType = screenType,
                baseOrientation = baseOrientation,
                defaultParams = defaultParams,
                perceptualParams = perceptualParams,
                fluidParams = fluidParams,
                constraints = constraints,
                customQualifiers = customQualifiers
            )
        }
    }
    
    // All calculation logic is now in Calculator.
    // Helper methods removed as they're handled by the unified calculator.
}

// FluidConfig and FluidDeviceType are now in core package
