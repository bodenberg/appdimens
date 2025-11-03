/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.1 - AutoSize Helper (Views XML)
 *
 * Description:
 * Helper class for AutoSize functionality in traditional Android Views (XML).
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.code.helpers

import android.view.View
import android.view.ViewTreeObserver
import com.appdimens.dynamic.code.AppDimens

/**
 * Helper for AutoSize in Views XML
 * 
 * Example usage:
 * ```kotlin
 * val helper = AppDimensAutoSizeHelper(16f, 12f, 24f)
 * helper.onSizeCalculated = { sizeDp ->
 *     textView.textSize = sizeDp
 * }
 * helper.attachToView(textView)
 * ```
 */
class AppDimensAutoSizeHelper(
    private val baseValue: Float,
    private val minValue: Float,
    private val maxValue: Float
) {
    
    /**
     * Callback invoked when size is calculated
     */
    var onSizeCalculated: (Float) -> Unit = {}
    
    /**
     * Attach to a View and auto-calculate size when measured
     */
    fun attachToView(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val widthDp = view.width / view.resources.displayMetrics.density
                    val heightDp = view.height / view.resources.displayMetrics.density
                    
                    if (widthDp > 0f && heightDp > 0f) {
                        val size = AppDimens.from(baseValue)
                            .autoSize(minValue, maxValue)
                            .calculateForSize(widthDp, heightDp, view.resources.configuration)
                        
                        onSizeCalculated(size)
                        
                        // Remove listener after first calculation
                        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            }
        )
    }
    
    /**
     * Calculate size for specific dimensions without attaching
     */
    fun calculateForDimensions(view: View): Float {
        val widthDp = view.width / view.resources.displayMetrics.density
        val heightDp = view.height / view.resources.displayMetrics.density
        
        return AppDimens.from(baseValue)
            .autoSize(minValue, maxValue)
            .calculateForSize(widthDp, heightDp, view.resources.configuration)
    }
}

/**
 * Helper for AutoSize with preset sizes
 */
class AppDimensAutoSizePresetHelper(
    private val baseValue: Float,
    private val presetSizes: FloatArray
) {
    
    /**
     * Callback invoked when size is calculated
     */
    var onSizeCalculated: (Float) -> Unit = {}
    
    /**
     * Attach to a View and auto-calculate size when measured
     */
    fun attachToView(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val widthDp = view.width / view.resources.displayMetrics.density
                    val heightDp = view.height / view.resources.displayMetrics.density
                    
                    if (widthDp > 0f && heightDp > 0f) {
                        val size = AppDimens.from(baseValue)
                            .autoSizePresets(presetSizes)
                            .calculateForSize(widthDp, heightDp, view.resources.configuration)
                        
                        onSizeCalculated(size)
                        
                        // Remove listener after first calculation
                        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            }
        )
    }
}

/**
 * Extension function to apply autoSize to a View
 * 
 * Example:
 * ```
 * textView.applyAutoSize(16f, 12f, 24f) { sizeDp ->
 *     textView.textSize = sizeDp
 * }
 * ```
 */
fun View.applyAutoSize(
    baseValue: Float,
    minValue: Float,
    maxValue: Float,
    onSizeCalculated: (Float) -> Unit
) {
    val helper = AppDimensAutoSizeHelper(baseValue, minValue, maxValue)
    helper.onSizeCalculated = onSizeCalculated
    helper.attachToView(this)
}

/**
 * Extension function to apply autoSize with presets to a View
 */
fun View.applyAutoSizePresets(
    baseValue: Float,
    presetSizes: FloatArray,
    onSizeCalculated: (Float) -> Unit
) {
    val helper = AppDimensAutoSizePresetHelper(baseValue, presetSizes)
    helper.onSizeCalculated = onSizeCalculated
    helper.attachToView(this)
}

