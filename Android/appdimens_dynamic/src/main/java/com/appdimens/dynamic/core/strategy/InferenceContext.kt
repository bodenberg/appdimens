/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.1 - Inference Context
 *
 * Description:
 * Classes for smart strategy inference with context analysis and weights.
 * This is the single source of truth for inference context shared between
 * code and compose implementations.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.strategy

import com.appdimens.library.UiModeType

/**
 * Context for strategy inference.
 * 
 * Contains all relevant information about the current device and screen
 * configuration to help determine the best scaling strategy.
 */
data class InferenceContext(
    val smallestDp: Float,
    val largestDp: Float,
    val deviceType: DeviceType,
    val uiModeType: UiModeType,
    val densityDpi: Int,
    val hasFluidConfig: Boolean,
    val hasBounds: Boolean
)

/**
 * Device type classification for inference.
 * 
 * Helps determine appropriate scaling strategies based on device category.
 */
enum class DeviceType {
    /** < 360dp */
    PHONE_SMALL,
    
    /** 360-599dp */
    PHONE_NORMAL,
    
    /** 600-719dp (phablet) */
    PHONE_LARGE,
    
    /** 720-959dp */
    TABLET_SMALL,
    
    /** >= 960dp */
    TABLET_LARGE,
    
    /** Television */
    TV,
    
    /** Watch */
    WATCH,
    
    /** Auto/Car */
    AUTO;
    
    companion object {
        /**
         * Classify device based on smallest dimension and UI mode.
         * 
         * @param smallestDp The smallest screen dimension in dp
         * @param uiModeType The UI mode type (normal, television, watch, car)
         * @return The classified device type
         */
        fun from(smallestDp: Float, uiModeType: UiModeType): DeviceType {
            return when (uiModeType) {
                UiModeType.TELEVISION -> TV
                UiModeType.WATCH -> WATCH
                UiModeType.CAR -> AUTO
                else -> when {
                    smallestDp < 360f -> PHONE_SMALL
                    smallestDp < 600f -> PHONE_NORMAL
                    smallestDp < 720f -> PHONE_LARGE
                    smallestDp < 960f -> TABLET_SMALL
                    else -> TABLET_LARGE
                }
            }
        }
    }
}

/**
 * Strategy weight for inference.
 * 
 * Represents how strongly a particular strategy should be considered
 * based on the current context and element type.
 */
data class StrategyWeight(
    val strategy: ScalingStrategy,
    val weight: Float,
    val reason: String
)

/**
 * AutoSize mode for container-aware scaling.
 */
enum class AutoSizeMode {
    /** Adjusts proportionally within bounds */
    UNIFORM,
    
    /** Uses predefined size values */
    PRESET
}

