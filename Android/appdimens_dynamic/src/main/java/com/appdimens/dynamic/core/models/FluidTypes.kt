/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Fluid Device Types
 *
 * Description:
 * Unified enum and data class for fluid device types used in Fluid scaling strategy.
 * This is the single source of truth for fluid types shared between
 * code and compose implementations.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.models

import com.appdimens.library.BaseOrientation
import com.appdimens.library.ScreenType

/**
 * Device type classification for Fluid strategy qualifiers.
 * 
 * Used to specify different min/max values for different device categories
 * when using the FLUID scaling strategy.
 */
enum class FluidDeviceType {
    /** Phone devices */
    PHONE,
    
    /** Tablet devices */
    TABLET,
    
    /** Television devices */
    TV,
    
    /** Watch devices */
    WATCH,
    
    /** Auto/Car devices */
    AUTO
}

/**
 * Configuration for Fluid strategy scaling.
 * 
 * Defines the min/max values and breakpoints for fluid interpolation.
 */
data class FluidConfig(
    val minValue: Float,
    val maxValue: Float,
    val minWidth: Float,
    val maxWidth: Float,
    val baseOrientation: BaseOrientation = BaseOrientation.AUTO,
    val screenType: ScreenType = ScreenType.LOWEST
)

