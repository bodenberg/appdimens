/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Inference Context
 *
 * Description:
 * Context and data structures for intelligent strategy inference system.
 * Provides device type detection and weight-based strategy selection.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.core.strategy

/**
 * Device type classification for game-specific optimizations.
 * 
 * Based on smallest screen width (sw):
 * - PHONE_SMALL: < 360dp (older phones, budget devices)
 * - PHONE_NORMAL: 360-480dp (standard phones)
 * - PHONE_LARGE: 480-600dp (phablets, large phones)
 * - TABLET_SMALL: 600-840dp (7-10 inch tablets)
 * - TABLET_LARGE: 840-1024dp (10+ inch tablets)
 * - TV: ≥ 1024dp (Android TV, large displays)
 * - WATCH: < 300dp (Wear OS devices)
 * - AUTO: Any size (Android Auto)
 */
enum class GameDeviceType {
    PHONE_SMALL,
    PHONE_NORMAL,
    PHONE_LARGE,
    TABLET_SMALL,
    TABLET_LARGE,
    TV,
    WATCH,
    AUTO;
    
    companion object {
        /**
         * Determines device type from smallest screen dimension and UI mode.
         * 
         * @param smallestDp Smallest screen dimension in dp
         * @param uiMode UI mode type (optional, for special cases)
         * @return Detected device type
         */
        fun from(smallestDp: Float, uiMode: GameUiModeType = GameUiModeType.NORMAL): GameDeviceType {
            return when {
                // Special UI modes override size detection
                uiMode == GameUiModeType.TELEVISION -> TV
                uiMode == GameUiModeType.WATCH -> WATCH
                uiMode == GameUiModeType.CAR -> AUTO
                
                // Size-based classification
                smallestDp < 300 -> WATCH
                smallestDp < 360 -> PHONE_SMALL
                smallestDp < 480 -> PHONE_NORMAL
                smallestDp < 600 -> PHONE_LARGE
                smallestDp < 840 -> TABLET_SMALL
                smallestDp < 1024 -> TABLET_LARGE
                else -> TV
            }
        }
    }
    
    /**
     * Returns whether this is a phone-sized device.
     */
    fun isPhone(): Boolean = this in arrayOf(PHONE_SMALL, PHONE_NORMAL, PHONE_LARGE)
    
    /**
     * Returns whether this is a tablet-sized device.
     */
    fun isTablet(): Boolean = this in arrayOf(TABLET_SMALL, TABLET_LARGE)
    
    /**
     * Returns whether this is a large screen device (tablet or TV).
     */
    fun isLargeScreen(): Boolean = this in arrayOf(TABLET_SMALL, TABLET_LARGE, TV)
    
    /**
     * Returns whether this device benefits from perceptual scaling.
     */
    fun benefitsFromPerceptualScaling(): Boolean = isLargeScreen()
}

/**
 * UI mode type for game environments.
 */
enum class GameUiModeType {
    NORMAL,
    DESK,
    CAR,
    TELEVISION,
    APPLIANCE,
    WATCH,
    VR_HEADSET;
    
    companion object {
        /**
         * Converts from Android Configuration.UI_MODE_TYPE_* constant.
         */
        fun fromAndroidUiMode(uiMode: Int): GameUiModeType {
            return when (uiMode and 0x0f) {
                0x01 -> NORMAL
                0x02 -> DESK
                0x03 -> CAR
                0x04 -> TELEVISION
                0x05 -> APPLIANCE
                0x06 -> WATCH
                0x07 -> VR_HEADSET
                else -> NORMAL
            }
        }
    }
}

/**
 * Inference context containing all information needed for strategy selection.
 * 
 * @param smallestDp Smallest screen dimension in dp
 * @param largestDp Largest screen dimension in dp
 * @param deviceType Detected device type
 * @param uiModeType UI mode type
 * @param densityDpi Screen density in DPI
 * @param hasFluidConfig Whether fluid configuration is provided
 * @param hasBounds Whether min/max bounds are set
 */
data class GameInferenceContext(
    val smallestDp: Float,
    val largestDp: Float,
    val deviceType: GameDeviceType,
    val uiModeType: GameUiModeType,
    val densityDpi: Int,
    val hasFluidConfig: Boolean,
    val hasBounds: Boolean
) {
    /**
     * Aspect ratio of the screen (largest / smallest).
     */
    val aspectRatio: Float
        get() = if (smallestDp > 0) largestDp / smallestDp else 1.78f
    
    /**
     * Screen density scale factor (1.0 = mdpi = 160dpi).
     */
    val densityScale: Float
        get() = densityDpi / 160f
    
    /**
     * Whether this is a high-density display (>= xhdpi = 320dpi).
     */
    val isHighDensity: Boolean
        get() = densityDpi >= 320
    
    /**
     * Whether this is a very high-density display (>= xxhdpi = 480dpi).
     */
    val isVeryHighDensity: Boolean
        get() = densityDpi >= 480
}

/**
 * Strategy weight for inference system.
 * 
 * Represents a candidate strategy with its confidence weight and reasoning.
 * Higher weights indicate stronger preference for that strategy.
 * 
 * @param strategy The scaling strategy
 * @param weight Confidence weight (0.0-1.0, higher = more confident)
 * @param reason Human-readable reason for this weight
 */
data class StrategyWeight(
    val strategy: GameScalingStrategy,
    val weight: Float,
    val reason: String
) {
    /**
     * Returns whether this weight is significant enough to consider (>= 0.1).
     */
    fun isSignificant(): Boolean = weight >= 0.1f
    
    /**
     * Returns whether this weight is strong (>= 0.5).
     */
    fun isStrong(): Boolean = weight >= 0.5f
    
    /**
     * Returns whether this weight is very strong (>= 0.7).
     */
    fun isVeryStrong(): Boolean = weight >= 0.7f
}

/**
 * Perceptual model types for game scaling.
 */
enum class GamePerceptualModel {
    /**
     * Weber-Fechner Law: S = k × ln(I / I₀)
     * Pure logarithmic scaling based on perception research.
     */
    LOGARITHMIC,
    
    /**
     * Stevens' Power Law: P = k × I^n where n < 1
     * Power law scaling with configurable exponent.
     */
    POWER,
    
    /**
     * Balanced Hybrid: Linear on phones, logarithmic on tablets
     * Best of both worlds for multi-device games.
     */
    BALANCED
}

/**
 * Fluid device type for fluid scaling configuration.
 */
enum class FluidDeviceType {
    WATCH,
    PHONE,
    TABLET,
    TV
}

/**
 * Screen type for dimension calculations.
 */
enum class GameScreenType {
    LOWEST,     // Use smallest dimension (width in portrait, height in landscape)
    HIGHEST     // Use largest dimension (height in portrait, width in landscape)
}

/**
 * Base orientation for automatic inversion.
 */
enum class GameBaseOrientation {
    PORTRAIT,   // Design created for portrait
    LANDSCAPE,  // Design created for landscape
    AUTO        // No specific orientation (default)
}

