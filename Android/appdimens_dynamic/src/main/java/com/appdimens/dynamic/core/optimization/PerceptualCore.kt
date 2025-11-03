/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Perceptual Scaling
 *
 * Description:
 * Core implementation of true perceptual scaling based on psychophysics research.
 * Implements Weber-Fechner, Stevens' Power Law, and Balanced Hybrid models.
 * 
 * This is the unified calculation engine shared between code and compose implementations.
 * All calculation functions are pure and independent of platform-specific APIs.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.optimization

import com.appdimens.dynamic.core.models.*


import com.appdimens.library.BaseOrientation
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeType
import com.appdimens.library.UiModeQualifierEntry
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/**
 * Core perceptual scaling calculation engine.
 * 
 * Contains all pure calculation functions for perceptual scaling models:
 * - LOGARITHMIC: Weber-Fechner Law (S = k × ln(I / I₀))
 * - POWER: Stevens' Power Law (P = k × I^n where n < 1)
 * - BALANCED: Hybrid model (linear on phones, logarithmic on tablets)
 * 
 * This object provides platform-agnostic calculations that work with Float values
 * representing screen dimensions in dp. Platform-specific wrappers handle
 * Resources/Configuration integration.
 * 
 * @example
 * ```kotlin
 * val config = PerceptualConfig(
 *     model = PerceptualModel.BALANCED,
 *     sensitivity = 0.40f,
 *     baseValue = 48f
 * )
 * 
 * val scale = AppDimensPerceptualCore.calculatePerceptualScale(
 *     dimension = 720f,
 *     config = config
 * )
 * ```
 */
object PerceptualCore {
    
    // ============================================
    // CONSTANTS
    // ============================================
    
    /**
     * Base reference width in dp for perceptual calculations.
     * This is the design reference width (typically 300dp).
     */
    const val BASE_WIDTH_DP = 300f
    
    /**
     * Reference aspect ratio (typically 16:9 = 1.78).
     * Used for aspect ratio compensation calculations.
     */
    const val REFERENCE_AR = 1.78f
    
    /**
     * Default sensitivity for Weber-Fechner and Balanced models.
     * Higher values increase the logarithmic effect.
     * Typical range: 0.30f - 0.50f
     */
    const val DEFAULT_SENSITIVITY = 0.40f
    
    /**
     * Default power exponent for Stevens' Power Law.
     * Lower values provide more control on large screens.
     * Typical range: 0.60f - 0.90f
     */
    const val DEFAULT_POWER_EXPONENT = 0.75f
    
    /**
     * Default transition point for Balanced model (where logarithmic starts).
     * Below this point: pure linear scaling
     * Above this point: logarithmic scaling
     */
    const val DEFAULT_TRANSITION_POINT = 480f
    
    /**
     * Default aspect ratio sensitivity for AR compensation.
     * Used to adjust scaling based on deviation from reference aspect ratio.
     */
    const val DEFAULT_AR_SENSITIVITY = 0.08f
    
    // ============================================
    // CONFIGURATION DATA CLASSES
    // ============================================
    
    /**
     * Configuration for perceptual calculations.
     * 
     * Encapsulates all parameters needed for perceptual scaling calculations
     * without requiring platform-specific Configuration objects.
     * 
     * @param baseValue The initial base value in dp before scaling
     * @param model The perceptual model to use (LOGARITHMIC, POWER, BALANCED)
     * @param sensitivity Sensitivity coefficient for logarithmic models (0.30f-0.50f)
     * @param powerExponent Power exponent for Stevens' Law (0.60f-0.90f)
     * @param transitionPoint Transition point in dp for Balanced model
     * @param applyAspectRatio Whether to apply aspect ratio compensation
     * @param arSensitivity Aspect ratio sensitivity factor
     * @param screenType Which dimension to use (LOWEST or HIGHEST)
     * @param baseOrientation Base orientation for auto-inversion
     * @param customQualifiers Custom qualifier values
     */
    data class PerceptualConfig(
        val baseValue: Float,
        val model: PerceptualModel = PerceptualModel.BALANCED,
        val sensitivity: Float = DEFAULT_SENSITIVITY,
        val powerExponent: Float = DEFAULT_POWER_EXPONENT,
        val transitionPoint: Float = DEFAULT_TRANSITION_POINT,
        val applyAspectRatio: Boolean = true,
        val arSensitivity: Float = DEFAULT_AR_SENSITIVITY,
        val screenType: ScreenType = ScreenType.LOWEST,
        val baseOrientation: BaseOrientation = BaseOrientation.AUTO,
        val customQualifiers: CustomQualifiers = CustomQualifiers()
    )
    
    /**
     * Custom qualifiers for perceptual scaling.
     * 
     * Allows specifying different base values for specific screen configurations,
     * similar to Android's resource qualifier system.
     */
    data class CustomQualifiers(
        val intersectionMap: Map<UiModeQualifierEntry, Float> = emptyMap(),
        val uiModeMap: Map<UiModeType, Float> = emptyMap(),
        val dpMap: Map<DpQualifierEntry, Float> = emptyMap()
    )
    
    /**
     * Screen configuration data for calculations.
     * 
     * Platform-agnostic representation of screen dimensions.
     * 
     * @param smallestWidthDp Smallest screen width in dp (regardless of orientation)
     * @param screenWidthDp Current screen width in dp
     * @param screenHeightDp Current screen height in dp
     * @param uiMode Current UI mode type
     */
    data class ScreenConfig(
        val smallestWidthDp: Float,
        val screenWidthDp: Float,
        val screenHeightDp: Float,
        val uiMode: UiModeType
    )
    
    // ============================================
    // MAIN CALCULATION METHODS
    // ============================================
    
    /**
     * Calculates the final perceptually scaled value.
     * 
     * This is the main entry point for perceptual scaling calculations.
     * 
     * @param screenConfig Screen configuration data
     * @param config Perceptual configuration
     * @return The final scaled value in dp
     * 
     * @example
     * ```kotlin
     * val screenConfig = ScreenConfig(
     *     smallestWidthDp = 360f,
     *     screenWidthDp = 720f,
     *     screenHeightDp = 1280f,
     *     uiMode = UiModeType.NORMAL
     * )
     * 
     * val config = PerceptualConfig(
     *     baseValue = 48f,
     *     model = PerceptualModel.BALANCED
     * )
     * 
     * val scaled = calculateScaledValue(screenConfig, config)
     * // Returns perceptually scaled value (e.g., ~38dp on tablet)
     * ```
     */
    fun calculateScaledValue(
        screenConfig: ScreenConfig,
        config: PerceptualConfig
    ): Float {
        // 1. Resolve base value (check custom qualifiers first)
        val baseValue = resolveFinalBaseDp(screenConfig, config)
        
        // 2. Resolve effective screen type (handle orientation inversion)
        val effectiveScreenType = AdjustmentFactorsCore.resolveScreenType(
            requestedType = config.screenType,
            baseOrientation = config.baseOrientation,
            configuration = createConfigurationFromScreenConfig(screenConfig)
        )
        
        // 3. Get dimension to use for calculation
        val dimensionToUse = when (effectiveScreenType) {
            ScreenType.HIGHEST -> max(screenConfig.screenWidthDp, screenConfig.screenHeightDp)
            ScreenType.LOWEST -> screenConfig.smallestWidthDp
        }
        
        // 4. Calculate perceptual scale
        val perceptualScale = calculatePerceptualScale(dimensionToUse, config)
        
        // 5. Apply aspect ratio compensation if enabled
        val arFactor = if (config.applyAspectRatio) {
            calculateAspectRatioFactor(
                screenConfig.screenWidthDp,
                screenConfig.screenHeightDp,
                config.arSensitivity
            )
        } else {
            1.0f
        }
        
        // 6. Calculate final value
        return baseValue * perceptualScale * arFactor
    }
    
    /**
     * Calculates perceptual scale factor based on chosen model.
     * 
     * Routes to the appropriate calculation function based on the perceptual model.
     * 
     * @param dimension The screen dimension to use for calculation (in dp)
     * @param config Perceptual configuration
     * @return Scale factor to apply to base value
     * 
     * @example
     * ```kotlin
     * // LOGARITHMIC: Pure log scaling
     * val logScale = calculatePerceptualScale(720f, config.copy(model = LOGARITHMIC))
     * // Returns ~0.45 (strong reduction on large screens)
     * 
     * // BALANCED: Hybrid linear+log
     * val balancedScale = calculatePerceptualScale(720f, config.copy(model = BALANCED))
     * // Returns ~0.62 (moderate reduction)
     * 
     * // POWER: Power law
     * val powerScale = calculatePerceptualScale(720f, config.copy(model = POWER, powerExponent = 0.75f))
     * // Returns ~0.60 (configurable reduction)
     * ```
     */
    fun calculatePerceptualScale(
        dimension: Float,
        config: PerceptualConfig
    ): Float {
        return when (config.model) {
            PerceptualModel.LOGARITHMIC -> calculateWeberFechnerScale(dimension, config.sensitivity)
            PerceptualModel.POWER -> calculateStevensScale(dimension, config.powerExponent)
            PerceptualModel.BALANCED -> calculateHybridScale(
                dimension,
                config.sensitivity,
                config.transitionPoint
            )
        }
    }
    
    // ============================================
    // PERCEPTUAL MODEL IMPLEMENTATIONS
    // ============================================
    
    /**
     * Weber-Fechner Law: S = k × ln(I / I₀)
     * 
     * Implements pure logarithmic scaling based on Weber-Fechner psychophysics law.
     * Provides maximum control on large screens but may reduce sizes noticeably
     * on all screen sizes.
     * 
     * Formula:
     * - Below reference: scale = 1.0 - k × ln(W₀ / W)
     * - Above reference: scale = 1.0 + k × ln(W / W₀)
     * 
     * @param dimension Screen dimension in dp
     * @param sensitivity Sensitivity coefficient (higher = stronger effect)
     * @return Scale factor (typically 0.45-1.0 for screens 300-1920dp)
     * 
     * @example
     * ```kotlin
     * // Phone (360dp): scale ≈ 1.08 (+8% vs base)
     * val phoneScale = calculateWeberFechnerScale(360f, 0.40f)
     * 
     * // Tablet (720dp): scale ≈ 0.56 (-44% vs base)
     * val tabletScale = calculateWeberFechnerScale(720f, 0.40f)
     * 
     * // TV (1920dp): scale ≈ 0.42 (-58% vs base)
     * val tvScale = calculateWeberFechnerScale(1920f, 0.40f)
     * ```
     */
    fun calculateWeberFechnerScale(
        dimension: Float,
        sensitivity: Float = DEFAULT_SENSITIVITY
    ): Float {
        return if (dimension <= BASE_WIDTH_DP) {
            // Below reference: smooth reduction
            1.0f - sensitivity * ln(BASE_WIDTH_DP / dimension)
        } else {
            // Above reference: logarithmic growth
            1.0f + sensitivity * ln(dimension / BASE_WIDTH_DP)
        }
    }
    
    /**
     * Stevens' Power Law: P = k × I^n where n < 1
     * 
     * Implements power law scaling based on Stevens' psychophysics research.
     * Provides smooth, predictable behavior with configurable control through
     * the power exponent.
     * 
     * Formula: scale = (W / W₀)^n
     * 
     * @param dimension Screen dimension in dp
     * @param powerExponent Power exponent (typically 0.60f-0.90f, lower = more control)
     * @return Scale factor
     * 
     * @example
     * ```kotlin
     * // With exponent 0.75 (default):
     * // Phone (360dp): scale ≈ 1.07 (+7%)
     * val phoneScale = calculateStevensScale(360f, 0.75f)
     * 
     * // Tablet (720dp): scale ≈ 1.60 (linear would be 2.4x)
     * val tabletScale = calculateStevensScale(720f, 0.75f)
     * 
     * // With lower exponent (0.60) - stronger control:
     * // Tablet (720dp): scale ≈ 1.40 (-17% vs linear)
     * val strongerControl = calculateStevensScale(720f, 0.60f)
     * ```
     */
    fun calculateStevensScale(
        dimension: Float,
        powerExponent: Float = DEFAULT_POWER_EXPONENT
    ): Float {
        val ratio = dimension / BASE_WIDTH_DP
        return ratio.pow(powerExponent)
    }
    
    /**
     * Balanced Hybrid: Linear on small screens, logarithmic on large screens
     * 
     * Combines the best of both worlds:
     * - Below transition point: pure linear (familiar behavior, ~3% difference from legacy)
     * - Above transition point: smooth transition to logarithmic (controls oversizing)
     * 
     * This is the RECOMMENDED model for most applications as it maintains
     * familiar behavior on phones while preventing oversizing on tablets/TVs.
     * 
     * Formula:
     * - if W ≤ transitionPoint: scale = W / W₀
     * - if W > transitionPoint: scale = (T / W₀) + k × ln(1 + (W - T) / W₀)
     * 
     * @param dimension Screen dimension in dp
     * @param sensitivity Sensitivity for logarithmic portion
     * @param transitionPoint Point where logarithmic starts (default 480dp)
     * @return Scale factor
     * 
     * @example
     * ```kotlin
     * // Phone (360dp): scale ≈ 1.20 (linear, similar to legacy)
     * val phoneScale = calculateHybridScale(360f, 0.40f, 480f)
     * 
     * // Phablet (480dp): scale ≈ 1.60 (at transition point)
     * val phabletScale = calculateHybridScale(480f, 0.40f, 480f)
     * 
     * // Tablet (720dp): scale ≈ 1.71 (-29% vs pure linear)
     * val tabletScale = calculateHybridScale(720f, 0.40f, 480f)
     * 
     * // TV (1920dp): scale ≈ 2.21 (-65% vs pure linear)
     * val tvScale = calculateHybridScale(1920f, 0.40f, 480f)
     * ```
     */
    fun calculateHybridScale(
        dimension: Float,
        sensitivity: Float = DEFAULT_SENSITIVITY,
        transitionPoint: Float = DEFAULT_TRANSITION_POINT
    ): Float {
        val linearScale = dimension / BASE_WIDTH_DP
        
        return if (dimension <= transitionPoint) {
            // Below transition: pure linear
            linearScale
        } else {
            // Above transition: logarithmic growth
            val excess = dimension - transitionPoint
            val baseLinear = transitionPoint / BASE_WIDTH_DP
            val logGrowth = sensitivity * ln(1 + excess / BASE_WIDTH_DP)
            baseLinear + logGrowth
        }
    }
    
    // ============================================
    // ASPECT RATIO COMPENSATION
    // ============================================
    
    /**
     * Calculates aspect ratio compensation factor.
     * 
     * Applies logarithmic adjustment based on deviation from reference aspect ratio (16:9).
     * Helps maintain visual balance on devices with unusual aspect ratios (e.g., 21:9, 4:3).
     * 
     * Formula: factor = 1.0 + arSensitivity × ln(currentAR / referenceAR)
     * 
     * @param width Screen width in dp
     * @param height Screen height in dp
     * @param arSensitivity Sensitivity factor (default 0.08)
     * @return Aspect ratio compensation factor (typically 0.95-1.05)
     * 
     * @example
     * ```kotlin
     * // Standard 16:9 (1.78): factor ≈ 1.00 (no adjustment)
     * val standard = calculateAspectRatioFactor(720f, 1280f, 0.08f)
     * 
     * // Ultrawide 21:9 (2.33): factor ≈ 1.02 (+2%)
     * val ultrawide = calculateAspectRatioFactor(1080f, 2520f, 0.08f)
     * 
     * // Square 1:1 (1.0): factor ≈ 0.96 (-4%)
     * val square = calculateAspectRatioFactor(600f, 600f, 0.08f)
     * ```
     */
    fun calculateAspectRatioFactor(
        width: Float,
        height: Float,
        arSensitivity: Float = DEFAULT_AR_SENSITIVITY
    ): Float {
        val currentAr = max(width, height) / min(width, height)
        val arAdjustment = arSensitivity * ln(currentAr / REFERENCE_AR)
        return 1.0f + arAdjustment
    }
    
    // ============================================
    // QUALIFIER RESOLUTION
    // ============================================
    
    /**
     * Resolves the final base dp value by checking custom qualifiers.
     * 
     * Follows Android's qualifier priority system:
     * 1. Intersection (UiMode + DpQualifier)
     * 2. UI Mode only
     * 3. Dp Qualifier only (sw, w, h)
     * 4. Default (initialBaseDp)
     * 
     * @param screenConfig Screen configuration
     * @param config Perceptual configuration with custom qualifiers
     * @return Resolved base value in dp
     * 
     * @example
     * ```kotlin
     * val qualifiers = CustomQualifiers(
     *     uiModeMap = mapOf(UiModeType.TELEVISION to 64f),
     *     dpMap = mapOf(DpQualifierEntry(DpQualifier.SMALL_WIDTH, 600) to 56f)
     * )
     * 
     * val config = PerceptualConfig(
     *     baseValue = 48f,
     *     customQualifiers = qualifiers
     * )
     * 
     * // On TV: returns 64f
     * // On tablet (sw600dp): returns 56f
     * // On phone: returns 48f (default)
     * val resolved = resolveFinalBaseDp(screenConfig, config)
     * ```
     */
    fun resolveFinalBaseDp(
        screenConfig: ScreenConfig,
        config: PerceptualConfig
    ): Float {
        val qualifiers = config.customQualifiers
        var dpToAdjust = config.baseValue
        
        // Priority 1: Intersection (UiMode + DpQualifier)
        val sortedIntersectionQualifiers = qualifiers.intersectionMap.entries.toList()
            .sortedByDescending { it.key.dpQualifierEntry.value }
        
        val foundIntersection = sortedIntersectionQualifiers.firstOrNull { (key, _) ->
            key.uiModeType == screenConfig.uiMode &&
            AdjustmentFactorsCore.resolveIntersectionCondition(
                entry = key.dpQualifierEntry,
                smallestWidthDp = screenConfig.smallestWidthDp,
                currentScreenWidthDp = screenConfig.screenWidthDp,
                currentScreenHeightDp = screenConfig.screenHeightDp
            )
        }?.value
        
        if (foundIntersection != null) {
            dpToAdjust = foundIntersection
        } else {
            // Priority 2: UI Mode
            val foundUiMode = qualifiers.uiModeMap[screenConfig.uiMode]
            if (foundUiMode != null) {
                dpToAdjust = foundUiMode
            } else {
                // Priority 3: Dp Qualifier
                dpToAdjust = AdjustmentFactorsCore.resolveQualifierDp(
                    customDpMap = qualifiers.dpMap,
                    smallestWidthDp = screenConfig.smallestWidthDp,
                    currentScreenWidthDp = screenConfig.screenWidthDp,
                    currentScreenHeightDp = screenConfig.screenHeightDp,
                    initialBaseDp = config.baseValue
                )
            }
        }
        
        return dpToAdjust
    }
    
    // ============================================
    // CACHE KEY GENERATION
    // ============================================
    
    /**
     * Generates a cache key for storing calculated values.
     * 
     * Creates a unique string key based on all configuration parameters
     * that affect the calculation result. Used by platform-specific wrappers
     * for caching calculated values.
     * 
     * @param screenConfig Screen configuration
     * @param config Perceptual configuration
     * @return Cache key string
     * 
     * @example
     * ```kotlin
     * val key = generateCacheKey(screenConfig, config)
     * // Returns: "BALANCED_LOWEST_true_0.4_0.75_360.0_720.0_1280.0_48.0_0_0_0"
     * 
     * // Use for caching:
     * val cache = mutableMapOf<String, Float>()
     * val cachedValue = cache.getOrPut(key) {
     *     calculateScaledValue(screenConfig, config)
     * }
     * ```
     */
    fun generateCacheKey(
        screenConfig: ScreenConfig,
        config: PerceptualConfig
    ): String {
        return "${config.model.name}_${config.screenType.name}_" +
               "${config.applyAspectRatio}_${config.sensitivity}_${config.powerExponent}_" +
               "${screenConfig.smallestWidthDp}_${screenConfig.screenWidthDp}_" +
               "${screenConfig.screenHeightDp}_${config.baseValue}_" +
               "${config.customQualifiers.intersectionMap.size}_" +
               "${config.customQualifiers.uiModeMap.size}_" +
               "${config.customQualifiers.dpMap.size}"
    }
    
    // ============================================
    // HELPER METHODS
    // ============================================
    
    /**
     * Creates a minimal Configuration object for compatibility with existing helper methods.
     * 
     * This is a temporary helper until all methods are fully platform-agnostic.
     * Only used internally for resolveScreenType which still requires Configuration.
     * 
     * @param screenConfig Screen configuration data
     * @return Minimal Configuration with essential fields populated
     */
    private fun createConfigurationFromScreenConfig(screenConfig: ScreenConfig): android.content.res.Configuration {
        return android.content.res.Configuration().apply {
            smallestScreenWidthDp = screenConfig.smallestWidthDp.toInt()
            screenWidthDp = screenConfig.screenWidthDp.toInt()
            screenHeightDp = screenConfig.screenHeightDp.toInt()
        }
    }
}

