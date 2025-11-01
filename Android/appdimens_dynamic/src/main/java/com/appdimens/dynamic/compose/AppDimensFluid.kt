/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens Android - Fluid Scaling Model (Compose)
 *
 * Description:
 * Implements fluid (clamp-like) scaling that smoothly interpolates
 * between minimum and maximum values based on screen width breakpoints.
 * Ideal for typography, spacing, and other elements that need smooth
 * transitions across different screen sizes.
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

package com.appdimens.dynamic.compose

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import com.appdimens.library.ScreenType
import kotlin.math.max
import kotlin.math.min

/**
 * Configuration for fluid scaling
 */
data class FluidConfig(
    val minValue: Float,
    val maxValue: Float,
    val minWidth: Float,
    val maxWidth: Float,
    val baseOrientation: com.appdimens.library.BaseOrientation = com.appdimens.library.BaseOrientation.AUTO,
    val screenType: ScreenType = ScreenType.LOWEST
)

/**
 * Device type for qualifiers
 */
enum class FluidDeviceType {
    PHONE,
    TABLET,
    TV,
    WATCH,
    AUTO
}

/**
 * AppDimensFluid - Fluid (Clamp-like) Scaling Model for Compose
 *
 * Provides smooth interpolation between minimum and maximum values
 * based on screen width. Similar to CSS clamp() but for Android Compose.
 *
 * Philosophy: Smooth transitions with bounded growth
 * Ideal for: Typography, fluid spacing, responsive sizes with limits
 *
 * @example
 * ```kotlin
 * @Composable
 * fun MyComponent() {
 *     // Basic usage
 *     val fontSize = fluidDp(16f, 24f)
 *     val padding = fluidDp(8f, 16f)
 *     
 *     Text(
 *         text = "Fluid Typography",
 *         fontSize = fontSize.value.sp,
 *         modifier = Modifier.padding(padding)
 *     )
 * }
 * ```
 */
class AppDimensFluid(
    private val minValue: Float,
    private val maxValue: Float,
    private val minWidth: Float = 320f,
    private val maxWidth: Float = 768f
) {
    private var baseOrientation: com.appdimens.library.BaseOrientation = com.appdimens.library.BaseOrientation.AUTO
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
     *
     * @example
     * ```kotlin
     * val fontSize = AppDimensFluid(14f, 18f)
     *     .device(FluidDeviceType.TABLET, 18f, 24f)
     *     .device(FluidDeviceType.TV, 24f, 32f)
     * ```
     */
    fun device(
        type: FluidDeviceType,
        minValue: Float,
        maxValue: Float,
        minWidth: Float = this.minWidth,
        maxWidth: Float = this.maxWidth
    ): AppDimensFluid {
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
     *
     * @example
     * ```kotlin
     * val spacing = AppDimensFluid(8f, 16f)
     *     .screen(600, 12f, 20f)
     *     .screen(840, 16f, 24f)
     * ```
     */
    fun screen(
        qualifier: Int,
        minValue: Float,
        maxValue: Float,
        minWidth: Float = this.minWidth,
        maxWidth: Float = this.maxWidth
    ): AppDimensFluid {
        screenQualifiers[qualifier] = FluidConfig(minValue, maxValue, minWidth, maxWidth)
        return this
    }

    /**
     * Set base orientation for which the design was originally created
     */
    fun baseOrientation(orientation: com.appdimens.library.BaseOrientation): AppDimensFluid {
        baseOrientation = orientation
        return this
    }

    /**
     * Set screen dimension type (lowest or highest)
     */
    fun type(type: ScreenType): AppDimensFluid {
        screenType = type
        return this
    }

    /**
     * Shorthand for portrait design using lowest dimension (width)
     */
    fun portraitLowest(): AppDimensFluid {
        baseOrientation = com.appdimens.library.BaseOrientation.PORTRAIT
        screenType = ScreenType.LOWEST
        return this
    }

    /**
     * Shorthand for portrait design using highest dimension (height)
     */
    fun portraitHighest(): AppDimensFluid {
        baseOrientation = com.appdimens.library.BaseOrientation.PORTRAIT
        screenType = ScreenType.HIGHEST
        return this
    }

    /**
     * Shorthand for landscape design using lowest dimension (height)
     */
    fun landscapeLowest(): AppDimensFluid {
        baseOrientation = com.appdimens.library.BaseOrientation.LANDSCAPE
        screenType = ScreenType.LOWEST
        return this
    }

    /**
     * Shorthand for landscape design using highest dimension (width)
     */
    fun landscapeHighest(): AppDimensFluid {
        baseOrientation = com.appdimens.library.BaseOrientation.LANDSCAPE
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
        val effectiveScreenType = AppDimensAdjustmentFactors.resolveScreenType(
            requestedType = screenType,
            baseOrientation = baseOrientation,
            configuration = configuration
        )

        // Get the appropriate dimension to use
        val dimensionDp = when (effectiveScreenType) {
            ScreenType.HIGHEST -> maxOf(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())
            ScreenType.LOWEST -> minOf(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())
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

/**
 * Composable function to calculate fluid Dp value
 *
 * @param minValue Minimum value in dp
 * @param maxValue Maximum value in dp
 * @param minWidth Minimum screen width in dp (default: 320)
 * @param maxWidth Maximum screen width in dp (default: 768)
 * @return Calculated Dp value
 *
 * @example
 * ```kotlin
 * @Composable
 * fun MyComponent() {
 *     val padding = fluidDp(8f, 16f)
 *     Box(modifier = Modifier.padding(padding)) {
 *         Text("Fluid Padding")
 *     }
 * }
 * ```
 */
@Composable
fun fluidDp(
    minValue: Float,
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): Dp {
    val configuration = LocalConfiguration.current
    
    val fluid = remember(minValue, maxValue, minWidth, maxWidth) {
        AppDimensFluid(minValue, maxValue, minWidth, maxWidth)
    }
    
    return fluid.calculate(configuration).dp
}

/**
 * Composable function to calculate fluid TextUnit (sp) value
 *
 * @param minValue Minimum value in sp
 * @param maxValue Maximum value in sp
 * @param minWidth Minimum screen width in dp (default: 320)
 * @param maxWidth Maximum screen width in dp (default: 768)
 * @return Calculated TextUnit value
 *
 * @example
 * ```kotlin
 * @Composable
 * fun MyComponent() {
 *     val fontSize = fluidSp(14f, 20f)
 *     Text(
 *         text = "Fluid Typography",
 *         fontSize = fontSize
 *     )
 * }
 * ```
 */
@Composable
fun fluidSp(
    minValue: Float,
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): TextUnit {
    val configuration = LocalConfiguration.current
    
    val fluid = remember(minValue, maxValue, minWidth, maxWidth) {
        AppDimensFluid(minValue, maxValue, minWidth, maxWidth)
    }
    
    return fluid.calculate(configuration).sp
}

/**
 * Composable function to create a fluid dimension builder
 *
 * @param minValue Minimum value
 * @param maxValue Maximum value
 * @param minWidth Minimum screen width in dp (default: 320)
 * @param maxWidth Maximum screen width in dp (default: 768)
 * @return Pair of calculated Float value and AppDimensFluid instance
 *
 * @example
 * ```kotlin
 * @Composable
 * fun MyComponent() {
 *     val (fontSize, fluidBuilder) = rememberFluid(16f, 24f)
 *     
 *     Text(
 *         text = "Min: ${fluidBuilder.getMin()}, Max: ${fluidBuilder.getMax()}",
 *         fontSize = fontSize.sp
 *     )
 * }
 * ```
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberFluid(
    minValue: Float,
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): Pair<Float, AppDimensFluid> {
    val configuration = LocalConfiguration.current
    
    val fluid = remember(minValue, maxValue, minWidth, maxWidth) {
        AppDimensFluid(minValue, maxValue, minWidth, maxWidth)
    }
    
    val calculatedValue = remember(configuration.screenWidthDp, configuration.screenHeightDp, fluid) {
        fluid.calculate(configuration)
    }
    
    return Pair(calculatedValue, fluid)
}

/**
 * Composable function to calculate multiple fluid values with shared breakpoints
 *
 * @param values List of min/max pairs
 * @param minWidth Minimum screen width in dp (default: 320)
 * @param maxWidth Maximum screen width in dp (default: 768)
 * @return List of calculated Dp values
 *
 * @example
 * ```kotlin
 * @Composable
 * fun MyComponent() {
 *     val (fontSize, padding, margin) = fluidMultipleDp(
 *         listOf(14f to 20f, 8f to 16f, 12f to 24f)
 *     )
 *     
 *     Box(
 *         modifier = Modifier
 *             .padding(padding)
 *             .padding(margin)
 *     ) {
 *         Text("Multi-Fluid", fontSize = fontSize.value.sp)
 *     }
 * }
 * ```
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun fluidMultipleDp(
    values: List<Pair<Float, Float>>,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): List<Dp> {
    val configuration = LocalConfiguration.current
    
    return remember(values, configuration.screenWidthDp, configuration.screenHeightDp, minWidth, maxWidth) {
        values.map { (min, max) ->
            val fluid = AppDimensFluid(min, max, minWidth, maxWidth)
            fluid.calculate(configuration).dp
        }
    }
}

/**
 * Extension function to create fluid builder from Float
 *
 * @param maxValue Maximum value
 * @param minWidth Minimum screen width (default: 320)
 * @param maxWidth Maximum screen width (default: 768)
 * @return AppDimensFluid instance
 *
 * @example
 * ```kotlin
 * val fluid = 16f.fluidTo(24f)
 * ```
 */
fun Float.fluidTo(
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): AppDimensFluid {
    return AppDimensFluid(this, maxValue, minWidth, maxWidth)
}

/**
 * Extension function to create fluid builder with this as max value
 *
 * @param minValue Minimum value
 * @param minWidth Minimum screen width (default: 320)
 * @param maxWidth Maximum screen width (default: 768)
 * @return AppDimensFluid instance
 *
 * @example
 * ```kotlin
 * val fluid = 24f.fluidFrom(16f)
 * ```
 */
fun Float.fluidFrom(
    minValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): AppDimensFluid {
    return AppDimensFluid(minValue, this, minWidth, maxWidth)
}

/**
 * Extension function to create fluid builder from Int
 */
fun Int.fluidTo(
    maxValue: Int,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): AppDimensFluid {
    return AppDimensFluid(this.toFloat(), maxValue.toFloat(), minWidth, maxWidth)
}

/**
 * Extension function to create fluid builder with Int as max value
 */
fun Int.fluidFrom(
    minValue: Int,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): AppDimensFluid {
    return AppDimensFluid(minValue.toFloat(), this.toFloat(), minWidth, maxWidth)
}

