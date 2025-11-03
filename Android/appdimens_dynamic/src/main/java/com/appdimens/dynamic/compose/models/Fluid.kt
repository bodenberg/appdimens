/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Fluid Scaling Model (Compose)
 *
 * Description:
 * Compose-specific wrappers for fluid scaling model.
 * Provides @Composable functions and extensions for Compose UI.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.compose.models

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import com.appdimens.dynamic.core.models.Fluid as CoreFluid

/**
 * Composable function to calculate fluid Dp value
 *
 * @param minValue Minimum value in dp
 * @param maxValue Maximum value in dp
 * @param minWidth Minimum screen width in dp (default: 320)
 * @param maxWidth Maximum screen width in dp (default: 768)
 * @return Calculated Dp value
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
        CoreFluid(minValue, maxValue, minWidth, maxWidth)
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
        CoreFluid(minValue, maxValue, minWidth, maxWidth)
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
 * @return Pair of calculated Float value and CoreFluid instance
 */
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberFluid(
    minValue: Float,
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): Pair<Float, CoreFluid> {
    val configuration = LocalConfiguration.current
    
    val fluid = remember(minValue, maxValue, minWidth, maxWidth) {
        CoreFluid(minValue, maxValue, minWidth, maxWidth)
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
            val fluid = CoreFluid(min, max, minWidth, maxWidth)
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
 * @return CoreFluid instance
 */
fun Float.fluidTo(
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): CoreFluid {
    return CoreFluid(this, maxValue, minWidth, maxWidth)
}

/**
 * Extension function to create fluid builder with this as max value
 *
 * @param minValue Minimum value
 * @param minWidth Minimum screen width (default: 320)
 * @param maxWidth Maximum screen width (default: 768)
 * @return CoreFluid instance
 */
fun Float.fluidFrom(
    minValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): CoreFluid {
    return CoreFluid(minValue, this, minWidth, maxWidth)
}

/**
 * Extension function to create fluid builder from Int
 */
fun Int.fluidTo(
    maxValue: Int,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): CoreFluid {
    return CoreFluid(this.toFloat(), maxValue.toFloat(), minWidth, maxWidth)
}

/**
 * Extension function to create fluid builder with Int as max value
 */
fun Int.fluidFrom(
    minValue: Int,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): CoreFluid {
    return CoreFluid(minValue.toFloat(), this.toFloat(), minWidth, maxWidth)
}
