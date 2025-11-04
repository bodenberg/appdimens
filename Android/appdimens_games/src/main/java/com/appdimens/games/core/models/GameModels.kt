/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Core Models
 *
 * Description:
 * Core data models and configurations for game dimension calculations.
 * Contains all necessary data classes for strategy parameters and configurations.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.core.models

import com.appdimens.games.core.strategy.*

/**
 * Parameters for DEFAULT strategy calculation.
 * 
 * DEFAULT strategy uses a linear scaling approach with optional aspect ratio adjustment.
 * Formula: f(x) = x × (1 + (W-W₀)/1 × increment) × arAdjustment
 * 
 * @param applyAspectRatio Whether to apply aspect ratio adjustment
 * @param arSensitivity Aspect ratio sensitivity factor (null = use default)
 */
data class DefaultParams(
    val applyAspectRatio: Boolean = true,
    val arSensitivity: Float? = null
)

/**
 * Parameters for Perceptual strategies (BALANCED, LOGARITHMIC, POWER).
 * 
 * @param model Perceptual model to use
 * @param sensitivity Sensitivity factor for logarithmic models (0.0-1.0)
 * @param powerExponent Power exponent for Stevens Power Law (0.0-1.0)
 * @param transitionPoint Transition point for BALANCED model (in dp)
 * @param applyAspectRatio Whether to apply aspect ratio adjustment
 * @param arSensitivity Aspect ratio sensitivity factor
 */
data class PerceptualParams(
    val model: GamePerceptualModel = GamePerceptualModel.BALANCED,
    val sensitivity: Float = 0.40f,
    val powerExponent: Float = 0.75f,
    val transitionPoint: Float = 480f,
    val applyAspectRatio: Boolean = true,
    val arSensitivity: Float = 0.08f
)

/**
 * Parameters for FLUID strategy calculation.
 * 
 * FLUID strategy provides CSS clamp-like behavior with breakpoints.
 * Formula: clamp(minValue, interpolate(width), maxValue)
 * 
 * @param minValue Minimum value in dp
 * @param maxValue Maximum value in dp
 * @param minWidth Minimum width breakpoint in dp
 * @param maxWidth Maximum width breakpoint in dp
 * @param deviceQualifiers Device-specific qualifiers
 * @param screenQualifiers Screen width-specific qualifiers
 * @param applyAspectRatio Whether to apply aspect ratio adjustment (FLUID-specific, ignores global)
 * @param arSensitivity Aspect ratio sensitivity factor (null = use default)
 */
data class FluidParams(
    val minValue: Float,
    val maxValue: Float,
    val minWidth: Float = 320f,
    val maxWidth: Float = 768f,
    val deviceQualifiers: Map<FluidDeviceType, FluidConfig> = emptyMap(),
    val screenQualifiers: Map<Int, FluidConfig> = emptyMap(),
    val applyAspectRatio: Boolean = false,
    val arSensitivity: Float? = null
)

/**
 * Parameters for INTERPOLATED strategy calculation.
 * 
 * INTERPOLATED strategy provides 50% of linear scaling.
 * Formula: f(x) = x + ((x × W/W₀) - x) × 0.5
 * 
 * @param applyAspectRatio Whether to apply aspect ratio adjustment
 * @param arSensitivity Aspect ratio sensitivity factor (null = use default)
 */
data class InterpolatedParams(
    val applyAspectRatio: Boolean = true,
    val arSensitivity: Float? = null
)

/**
 * Fluid configuration for specific device or screen qualifier.
 * 
 * @param minValue Minimum value in dp
 * @param maxValue Maximum value in dp
 * @param minWidth Minimum width breakpoint in dp
 * @param maxWidth Maximum width breakpoint in dp
 * @param baseOrientation Base orientation
 * @param screenType Screen type to use
 */
data class FluidConfig(
    val minValue: Float,
    val maxValue: Float,
    val minWidth: Float = 320f,
    val maxWidth: Float = 768f,
    val baseOrientation: GameBaseOrientation = GameBaseOrientation.AUTO,
    val screenType: GameScreenType = GameScreenType.LOWEST
)

/**
 * Universal constraints to apply after calculation.
 * 
 * @param minValue Minimum value constraint in dp
 * @param maxValue Maximum value constraint in dp
 * @param maxPhysicalMm Maximum physical size constraint in millimeters
 */
data class Constraints(
    val minValue: Float? = null,
    val maxValue: Float? = null,
    val maxPhysicalMm: Float? = null
)

/**
 * Custom qualifiers for screen-specific overrides.
 * 
 * Uses a priority system:
 * 1. Intersection (UiMode + Screen size) - highest priority
 * 2. UiMode only - medium priority
 * 3. Screen size only - lowest priority
 * 
 * @param intersectionMap Intersection qualifiers (UiMode + Screen size)
 * @param uiModeMap UiMode qualifiers
 * @param screenSizeMap Screen size qualifiers
 */
data class CustomQualifiers(
    val intersectionMap: Map<UiModeQualifierEntry, Float> = emptyMap(),
    val uiModeMap: Map<GameUiModeType, Float> = emptyMap(),
    val screenSizeMap: Map<ScreenQualifierEntry, Float> = emptyMap()
)

/**
 * UiMode qualifier entry for intersection qualifiers.
 * 
 * @param uiModeType UI mode type
 * @param screenQualifierEntry Screen qualifier entry
 */
data class UiModeQualifierEntry(
    val uiModeType: GameUiModeType,
    val screenQualifierEntry: ScreenQualifierEntry
)

/**
 * Screen qualifier entry for screen size qualifiers.
 * 
 * @param type Qualifier type (SMALL_WIDTH, WIDTH, HEIGHT)
 * @param value Threshold value in dp
 */
data class ScreenQualifierEntry(
    val type: ScreenQualifier,
    val value: Int
)

/**
 * Screen qualifier type.
 */
enum class ScreenQualifier {
    /** Smallest screen width (sw) - most restrictive */
    SMALL_WIDTH,
    
    /** Screen width (w) - current width */
    WIDTH,
    
    /** Screen height (h) - current height */
    HEIGHT
}

/**
 * Physical units type for conversion.
 */
enum class PhysicalUnitType {
    /** Millimeters */
    MM,
    
    /** Centimeters */
    CM,
    
    /** Inches */
    INCH,
    
    /** Points (1/72 inch) */
    PT
}

/**
 * Game screen configuration for calculations.
 * 
 * Platform-agnostic representation of screen dimensions.
 * 
 * @param screenWidthDp Screen width in dp
 * @param screenHeightDp Screen height in dp
 * @param smallestScreenWidthDp Smallest screen width in dp (regardless of orientation)
 * @param densityDpi Screen density in dots per inch
 * @param uiMode UI mode type
 */
data class GameScreenConfig(
    val screenWidthDp: Float,
    val screenHeightDp: Float,
    val smallestScreenWidthDp: Float,
    val densityDpi: Int,
    val uiMode: GameUiModeType
) {
    /**
     * Aspect ratio of the screen (largest / smallest).
     */
    val aspectRatio: Float
        get() {
            val smallest = minOf(screenWidthDp, screenHeightDp)
            val largest = maxOf(screenWidthDp, screenHeightDp)
            return if (smallest > 0) largest / smallest else 1.78f
        }
    
    /**
     * Screen density scale factor (1.0 = mdpi = 160dpi).
     */
    val densityScale: Float
        get() = densityDpi / 160f
    
    /**
     * Whether screen is in portrait orientation.
     */
    val isPortrait: Boolean
        get() = screenHeightDp > screenWidthDp
    
    /**
     * Whether screen is in landscape orientation.
     */
    val isLandscape: Boolean
        get() = screenWidthDp > screenHeightDp
    
    /**
     * Device type inferred from screen dimensions.
     */
    val deviceType: GameDeviceType
        get() = GameDeviceType.from(smallestScreenWidthDp, uiMode)
}

/**
 * AutoSize configuration for container-aware scaling.
 * 
 * @param baseValue Base value in dp
 * @param minValue Minimum value in dp
 * @param maxValue Maximum value in dp
 * @param containerWidthDp Container width in dp
 * @param containerHeightDp Container height in dp
 * @param granularity Granularity for preset generation (optional)
 * @param presets Pre-defined presets (optional, auto-generated if null)
 */
data class AutoSizeConfig(
    val baseValue: Float,
    val minValue: Float,
    val maxValue: Float,
    val containerWidthDp: Float,
    val containerHeightDp: Float,
    val granularity: Float = 1f,
    val presets: FloatArray? = null
) {
    /**
     * Generates presets if not provided.
     */
    fun getOrGeneratePresets(): FloatArray {
        return presets ?: generatePresets()
    }
    
    /**
     * Generates presets based on min, max, and granularity.
     */
    private fun generatePresets(): FloatArray {
        val count = ((maxValue - minValue) / granularity).toInt() + 1
        return FloatArray(count) { i ->
            minValue + i * granularity
        }
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AutoSizeConfig

        if (baseValue != other.baseValue) return false
        if (minValue != other.minValue) return false
        if (maxValue != other.maxValue) return false
        if (containerWidthDp != other.containerWidthDp) return false
        if (containerHeightDp != other.containerHeightDp) return false
        if (granularity != other.granularity) return false
        if (presets != null) {
            if (other.presets == null) return false
            if (!presets.contentEquals(other.presets)) return false
        } else if (other.presets != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = baseValue.hashCode()
        result = 31 * result + minValue.hashCode()
        result = 31 * result + maxValue.hashCode()
        result = 31 * result + containerWidthDp.hashCode()
        result = 31 * result + containerHeightDp.hashCode()
        result = 31 * result + granularity.hashCode()
        result = 31 * result + (presets?.contentHashCode() ?: 0)
        return result
    }
}

/**
 * Calculation result with metadata.
 * 
 * @param value Calculated value in dp
 * @param strategy Strategy used for calculation
 * @param wasCached Whether value was retrieved from cache
 * @param computationTimeNs Computation time in nanoseconds (0 if cached)
 */
data class CalculationResult(
    val value: Float,
    val strategy: GameScalingStrategy,
    val wasCached: Boolean,
    val computationTimeNs: Long = 0
) {
    /**
     * Computation time in microseconds.
     */
    val computationTimeMicros: Double
        get() = computationTimeNs / 1000.0
    
    /**
     * Computation time in milliseconds.
     */
    val computationTimeMillis: Double
        get() = computationTimeNs / 1_000_000.0
}

