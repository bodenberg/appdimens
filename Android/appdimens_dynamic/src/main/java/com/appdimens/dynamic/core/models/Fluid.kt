/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Fluid Scaling Model
 *
 * Description:
 * Implementation of fluid (clamp-like) scaling that smoothly interpolates
 * between minimum and maximum values based on screen width breakpoints.
 * Ideal for typography, spacing, and other elements that need smooth
 * transitions across different screen sizes.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.models

import android.annotation.SuppressLint
import android.content.res.Configuration
import com.appdimens.library.BaseOrientation
import com.appdimens.library.ScreenType
import kotlin.math.max
import kotlin.math.min

/**
 * Fluid - Fluid (Clamp-like) Scaling Model
 *
 * Provides smooth interpolation between minimum and maximum values
 * based on screen width. Similar to CSS clamp() but for Android.
 *
 * Philosophy: Smooth transitions with bounded growth
 * Ideal for: Typography, fluid spacing, responsive sizes with limits
 *
 * @example
 * ```kotlin
 * val fluid = Fluid(16f, 24f)
 * val value = fluid.calculate(configuration)
 * ```
 */
class Fluid(
    private val minValue: Float,
    private val maxValue: Float,
    private val minWidth: Float = 320f,
    private val maxWidth: Float = 768f
) {
    private var baseOrientation: BaseOrientation = BaseOrientation.AUTO
    private var screenType: ScreenType = ScreenType.LOWEST
    private val deviceQualifiers = mutableMapOf<FluidDeviceType, FluidConfig>()
    private val screenQualifiers = mutableMapOf<Int, FluidConfig>()

    /**
     * Set fluid values for specific device type
     *
     * @param type Device type
     * @param minValue Minimum value for this device
     * @param maxValue Maximum value for this device
     * @param minWidth Optional custom min width for this device
     * @param maxWidth Optional custom max width for this device
     * @return This instance for chaining
     */
    fun device(
        type: FluidDeviceType,
        minValue: Float,
        maxValue: Float,
        minWidth: Float = this.minWidth,
        maxWidth: Float = this.maxWidth
    ): Fluid {
        deviceQualifiers[type] = FluidConfig(minValue, maxValue, minWidth, maxWidth)
        return this
    }

    /**
     * Set fluid values for specific screen width qualifier (in dp)
     *
     * @param qualifier Screen width qualifier (e.g., 600 for sw600dp)
     * @param minValue Minimum value for this qualifier
     * @param maxValue Maximum value for this qualifier
     * @param minWidth Optional custom min width
     * @param maxWidth Optional custom max width
     * @return This instance for chaining
     */
    fun screen(
        qualifier: Int,
        minValue: Float,
        maxValue: Float,
        minWidth: Float = this.minWidth,
        maxWidth: Float = this.maxWidth
    ): Fluid {
        screenQualifiers[qualifier] = FluidConfig(minValue, maxValue, minWidth, maxWidth)
        return this
    }

    /**
     * Set base orientation for which the design was originally created
     */
    fun baseOrientation(orientation: BaseOrientation): Fluid {
        baseOrientation = orientation
        return this
    }

    /**
     * Set screen dimension type (lowest or highest)
     */
    fun type(type: ScreenType): Fluid {
        screenType = type
        return this
    }

    /**
     * Shorthand for portrait design using lowest dimension (width)
     */
    fun portraitLowest(): Fluid {
        baseOrientation = BaseOrientation.PORTRAIT
        screenType = ScreenType.LOWEST
        return this
    }

    /**
     * Shorthand for portrait design using highest dimension (height)
     */
    fun portraitHighest(): Fluid {
        baseOrientation = BaseOrientation.PORTRAIT
        screenType = ScreenType.HIGHEST
        return this
    }

    /**
     * Shorthand for landscape design using lowest dimension (height)
     */
    fun landscapeLowest(): Fluid {
        baseOrientation = BaseOrientation.LANDSCAPE
        screenType = ScreenType.LOWEST
        return this
    }

    /**
     * Shorthand for landscape design using highest dimension (width)
     */
    fun landscapeHighest(): Fluid {
        baseOrientation = BaseOrientation.LANDSCAPE
        screenType = ScreenType.HIGHEST
        return this
    }

    /**
     * Calculate the fluid value based on current screen dimensions
     *
     * @param configuration Current screen configuration
     * @param deviceType Optional device type for qualifier resolution
     * @return Interpolated value between min and max
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun calculate(
        configuration: Configuration,
        deviceType: FluidDeviceType? = null
    ): Float {
        // Resolve effective screen type based on base orientation
        val effectiveScreenType = AdjustmentFactorsCore.resolveScreenType(
            requestedType = screenType,
            baseOrientation = baseOrientation,
            configuration = configuration
        )

        // Get the appropriate dimension to use
        val dimensionDp = when (effectiveScreenType) {
            ScreenType.HIGHEST -> max(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())
            ScreenType.LOWEST -> min(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())
        }

        // Resolve which config to use based on qualifiers
        val config = resolveConfig(dimensionDp, deviceType)

        // Perform fluid interpolation
        return interpolate(dimensionDp, config)
    }

    /**
     * Calculate the fluid value based on current screen width (backward compatibility)
     *
     * @param screenWidthDp Current screen width in dp
     * @param deviceType Optional device type for qualifier resolution
     * @return Interpolated value between min and max
     */
    @Deprecated("Use calculate(configuration, deviceType) instead")
    fun calculate(
        screenWidthDp: Float,
        deviceType: FluidDeviceType? = null
    ): Float {
        // Resolve which config to use based on qualifiers
        val config = resolveConfig(screenWidthDp, deviceType)

        // Perform fluid interpolation
        return interpolate(screenWidthDp, config)
    }

    /**
     * Get the minimum value
     */
    fun getMin(): Float = minValue

    /**
     * Get the maximum value
     */
    fun getMax(): Float = maxValue

    /**
     * Get the preferred (middle) value
     */
    fun getPreferred(): Float = (minValue + maxValue) / 2f

    /**
     * Linear interpolation at a specific progress point
     *
     * @param t Progress value between 0 and 1
     * @return Interpolated value
     */
    fun lerp(t: Float): Float {
        val clampedT = t.coerceIn(0f, 1f)
        return minValue + (maxValue - minValue) * clampedT
    }

    /**
     * Resolve which configuration to use based on qualifiers
     * Priority: Device Type > Screen Qualifier > Default
     */
    private fun resolveConfig(
        width: Float,
        deviceType: FluidDeviceType?
    ): FluidConfig {
        // Priority 1: Device Type
        if (deviceType != null && deviceQualifiers.containsKey(deviceType)) {
            return deviceQualifiers[deviceType]!!
        }

        // Priority 2: Screen Qualifier (find largest matching)
        var matchedConfig: FluidConfig? = null
        var largestQualifier = 0

        for ((qualifier, config) in screenQualifiers) {
            if (width >= qualifier && qualifier > largestQualifier) {
                matchedConfig = config
                largestQualifier = qualifier
            }
        }

        if (matchedConfig != null) {
            return matchedConfig
        }

        // Priority 3: Default
        return FluidConfig(minValue, maxValue, minWidth, maxWidth)
    }

    /**
     * Perform fluid interpolation (clamp-like behavior)
     */
    private fun interpolate(width: Float, config: FluidConfig): Float {
        // Below minimum width: return min value
        if (width <= config.minWidth) {
            return config.minValue
        }

        // Above maximum width: return max value
        if (width >= config.maxWidth) {
            return config.maxValue
        }

        // Between min and max: linear interpolation
        val progress = (width - config.minWidth) / (config.maxWidth - config.minWidth)
        return config.minValue + (config.maxValue - config.minValue) * progress
    }
}

