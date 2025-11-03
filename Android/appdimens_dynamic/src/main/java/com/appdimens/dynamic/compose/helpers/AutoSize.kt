/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.1 - AutoSize Helpers (Compose)
 *
 * Description:
 * Helper functions and modifiers for AutoSize functionality in Compose.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.compose.helpers

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens

/**
 * Modifier that measures container and calculates auto-sized dimension
 * 
 * Example:
 * ```
 * var textSize by remember { mutableStateOf(16.dp) }
 * Box(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .autoSizeDimens(16, 12f, 24f) { size ->
 *             textSize = size
 *         }
 * ) {
 *     Text("Auto-sized", fontSize = textSize.value.sp)
 * }
 * ```
 */
@Composable
fun Modifier.autoSizeDimens(
    baseValue: Int,
    minValue: Float,
    maxValue: Float,
    onSizeCalculated: (Dp) -> Unit
): Modifier {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    return this.onGloballyPositioned { coordinates ->
        val widthDp = with(density) { 
            coordinates.size.width.toDp().value 
        }
        val heightDp = with(density) { 
            coordinates.size.height.toDp().value 
        }
        
        if (widthDp > 0f && heightDp > 0f) {
            val sizeValue = AppDimens.from(baseValue)
                .autoSize(minValue, maxValue)
                .calculateForSizeWithConfig(widthDp, heightDp, configuration)
            
            onSizeCalculated(sizeValue.dp)
        }
    }
}

/**
 * Remember an auto-sized dimension based on container size
 * 
 * Example:
 * ```
 * @Composable
 * fun MyComponent() {
 *     BoxWithConstraints {
 *         val textSize = rememberAutoSizeDp(
 *             baseValue = 16,
 *             minValue = 12f,
 *             maxValue = 24f,
 *             containerWidth = maxWidth,
 *             containerHeight = maxHeight
 *         )
 *         Text("Auto-sized", fontSize = textSize.value.sp)
 *     }
 * }
 * ```
 */
@Composable
fun rememberAutoSizeDp(
    baseValue: Int,
    minValue: Float,
    maxValue: Float,
    containerWidth: Dp,
    containerHeight: Dp
): Dp {
    val configuration = LocalConfiguration.current
    
    return remember(baseValue, minValue, maxValue, containerWidth, containerHeight, configuration) {
        val sizeValue = AppDimens.from(baseValue)
            .autoSize(minValue, maxValue)
            .calculateForSizeWithConfig(containerWidth.value, containerHeight.value, configuration)
        sizeValue.dp
    }
}

/**
 * Remember an auto-sized dimension with preset sizes
 * 
 * Example:
 * ```
 * val textSize = rememberAutoSizePresetDp(
 *     baseValue = 16,
 *     presetSizes = floatArrayOf(10f, 12f, 16f, 20f, 24f),
 *     containerWidth = maxWidth,
 *     containerHeight = maxHeight
 * )
 * ```
 */
@Composable
fun rememberAutoSizePresetDp(
    baseValue: Int,
    presetSizes: FloatArray,
    containerWidth: Dp,
    containerHeight: Dp
): Dp {
    val configuration = LocalConfiguration.current
    
    return remember(baseValue, presetSizes, containerWidth, containerHeight, configuration) {
        val sizeValue = AppDimens.from(baseValue)
            .autoSizePresets(presetSizes)
            .calculateForSizeWithConfig(containerWidth.value, containerHeight.value, configuration)
        sizeValue.dp
    }
}

/**
 * State holder for auto-size calculations
 */
@Stable
class AutoSizeState internal constructor(
    private val baseValue: Int,
    private val minValue: Float,
    private val maxValue: Float
) {
    private var _size = mutableStateOf(baseValue.dp)
    val size: Dp by _size
    
    @Composable
    fun update(containerWidth: Dp, containerHeight: Dp) {
        val configuration = LocalConfiguration.current
        
        LaunchedEffect(containerWidth, containerHeight, configuration) {
            val sizeValue = AppDimens.from(baseValue)
                .autoSize(minValue, maxValue)
                .calculateForSizeWithConfig(containerWidth.value, containerHeight.value, configuration)
            _size.value = sizeValue.dp
        }
    }
}

/**
 * Remember an AutoSizeState
 * 
 * Example:
 * ```
 * val autoSizeState = rememberAutoSizeState(16, 12f, 24f)
 * BoxWithConstraints {
 *     autoSizeState.update(maxWidth, maxHeight)
 *     Text("Auto-sized", fontSize = autoSizeState.size.value.sp)
 * }
 * ```
 */
@Composable
fun rememberAutoSizeState(
    baseValue: Int,
    minValue: Float,
    maxValue: Float
): AutoSizeState {
    return remember(baseValue, minValue, maxValue) {
        AutoSizeState(baseValue, minValue, maxValue)
    }
}

