/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - AutoSize Helpers
 *
 * Description:
 * Container-aware auto-sizing for game HUD elements and text.
 * Similar to TextView autoSizeText but optimized for games.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.helpers

import android.content.res.Resources
import android.view.View
import android.widget.TextView
import com.appdimens.games.core.calculation.GamesCalculator
import com.appdimens.games.core.models.AutoSizeConfig

/**
 * AutoSize helper for game HUD elements.
 * 
 * Provides container-aware scaling similar to TextView's autoSizeText,
 * but optimized for game performance and HUD elements.
 */
object GameAutoSize {
    
    /**
     * Calculates auto-sized value based on container dimensions.
     * 
     * Uses binary search to find the largest preset that fits within
     * the available container space.
     * 
     * Performance: O(log n) where n is number of presets
     * 
     * @param config AutoSize configuration
     * @param resources Android Resources
     * @return Calculated size in dp
     * 
     * @example
     * ```kotlin
     * val config = AutoSizeConfig(
     *     baseValue = 16f,
     *     minValue = 12f,
     *     maxValue = 24f,
     *     containerWidthDp = hudWidth,
     *     containerHeightDp = hudHeight
     * )
     * val textSize = GameAutoSize.calculateAutoSize(config, resources)
     * ```
     */
    fun calculateAutoSize(
        config: AutoSizeConfig,
        resources: Resources
    ): Float {
        val presets = config.getOrGeneratePresets()
        
        // Calculate available space (use smallest dimension for conservative sizing)
        val availableSize = minOf(config.containerWidthDp, config.containerHeightDp)
        
        // Use binary search to find best fitting preset
        return GamesCalculator.findBestPreset(presets, availableSize)
    }
    
    /**
     * Applies auto-sizing to a TextView.
     * 
     * Adjusts text size to fit within the TextView's measured dimensions.
     * Call this after the view has been measured (e.g., in onGloballyPositioned).
     * 
     * @param textView TextView to auto-size
     * @param baseSize Base text size in sp
     * @param minSize Minimum text size in sp
     * @param maxSize Maximum text size in sp
     * @param granularity Size step granularity (default 1sp)
     * 
     * @example
     * ```kotlin
     * // After view measurement
     * GameAutoSize.applyAutoSizeToTextView(
     *     textView = scoreTextView,
     *     baseSize = 16f,
     *     minSize = 12f,
     *     maxSize = 24f
     * )
     * ```
     */
    fun applyAutoSizeToTextView(
        textView: TextView,
        baseSize: Float,
        minSize: Float,
        maxSize: Float,
        granularity: Float = 1f
    ) {
        // Get measured dimensions in dp
        val widthDp = textView.width / textView.resources.displayMetrics.density
        val heightDp = textView.height / textView.resources.displayMetrics.density
        
        if (widthDp <= 0 || heightDp <= 0) {
            // View not measured yet, use base size
            textView.textSize = baseSize
            return
        }
        
        val config = AutoSizeConfig(
            baseValue = baseSize,
            minValue = minSize,
            maxValue = maxSize,
            containerWidthDp = widthDp,
            containerHeightDp = heightDp,
            granularity = granularity
        )
        
        val calculatedSize = calculateAutoSize(config, textView.resources)
        textView.textSize = calculatedSize
    }
    
    /**
     * Generates uniform presets for auto-sizing.
     * 
     * Creates evenly-spaced size presets between min and max values.
     * 
     * @param minValue Minimum value
     * @param maxValue Maximum value
     * @param granularity Step size (default 1)
     * @return Array of preset values
     * 
     * @example
     * ```kotlin
     * val presets = GameAutoSize.generatePresets(12f, 24f, 2f)
     * // Returns: [12f, 14f, 16f, 18f, 20f, 22f, 24f]
     * ```
     */
    fun generatePresets(
        minValue: Float,
        maxValue: Float,
        granularity: Float = 1f
    ): FloatArray {
        if (minValue >= maxValue) {
            return floatArrayOf(minValue)
        }
        
        val count = ((maxValue - minValue) / granularity).toInt() + 1
        return FloatArray(count) { i ->
            minValue + i * granularity
        }
    }
    
    /**
     * Generates logarithmic presets for auto-sizing.
     * 
     * Creates presets with logarithmic spacing, providing more granularity
     * at smaller sizes and less at larger sizes.
     * 
     * @param minValue Minimum value
     * @param maxValue Maximum value
     * @param count Number of presets (default 10)
     * @return Array of preset values
     * 
     * @example
     * ```kotlin
     * val presets = GameAutoSize.generateLogPresets(12f, 24f, 8)
     * // Returns logarithmically spaced values
     * ```
     */
    fun generateLogPresets(
        minValue: Float,
        maxValue: Float,
        count: Int = 10
    ): FloatArray {
        if (minValue >= maxValue || count <= 0) {
            return floatArrayOf(minValue)
        }
        
        val logMin = kotlin.math.ln(minValue)
        val logMax = kotlin.math.ln(maxValue)
        val logStep = (logMax - logMin) / (count - 1)
        
        return FloatArray(count) { i ->
            kotlin.math.exp(logMin + i * logStep)
        }
    }
    
    /**
     * Creates a callback-based auto-sizer for custom views.
     * 
     * Useful for views that need custom sizing logic.
     * 
     * @param view View to auto-size
     * @param baseSize Base size in dp
     * @param minSize Minimum size in dp
     * @param maxSize Maximum size in dp
     * @param onSizeCalculated Callback with calculated size
     * 
     * @example
     * ```kotlin
     * GameAutoSize.createAutoSizer(
     *     view = customHudElement,
     *     baseSize = 48f,
     *     minSize = 32f,
     *     maxSize = 64f
     * ) { sizeDp ->
     *     customHudElement.setCustomSize(sizeDp)
     * }
     * ```
     */
    fun createAutoSizer(
        view: View,
        baseSize: Float,
        minSize: Float,
        maxSize: Float,
        onSizeCalculated: (Float) -> Unit
    ) {
        view.post {
            val widthDp = view.width / view.resources.displayMetrics.density
            val heightDp = view.height / view.resources.displayMetrics.density
            
            if (widthDp > 0 && heightDp > 0) {
                val config = AutoSizeConfig(
                    baseValue = baseSize,
                    minValue = minSize,
                    maxValue = maxSize,
                    containerWidthDp = widthDp,
                    containerHeightDp = heightDp
                )
                
                val calculatedSize = calculateAutoSize(config, view.resources)
                onSizeCalculated(calculatedSize)
            } else {
                onSizeCalculated(baseSize)
            }
        }
    }
    
    /**
     * Calculates optimal HUD text size based on available space.
     * 
     * Specialized method for HUD text elements that need to fit
     * within a specific area while maintaining readability.
     * 
     * @param text Text content
     * @param availableWidthDp Available width in dp
     * @param availableHeightDp Available height in dp
     * @param baseSize Base text size in sp
     * @param minSize Minimum readable size in sp (default 12sp)
     * @param maxSize Maximum size in sp (default 24sp)
     * @return Calculated text size in sp
     * 
     * @example
     * ```kotlin
     * val textSize = GameAutoSize.calculateHudTextSize(
     *     text = "Score: 12345",
     *     availableWidthDp = 120f,
     *     availableHeightDp = 32f,
     *     baseSize = 16f
     * )
     * ```
     */
    fun calculateHudTextSize(
        text: String,
        availableWidthDp: Float,
        availableHeightDp: Float,
        baseSize: Float,
        minSize: Float = 12f,
        maxSize: Float = 24f
    ): Float {
        // Estimate text width based on character count
        // Rough approximation: each character takes ~0.6em width
        val estimatedCharWidth = baseSize * 0.6f
        val estimatedTextWidth = text.length * estimatedCharWidth
        
        // Calculate scale factor to fit width
        val widthScale = if (estimatedTextWidth > 0) {
            availableWidthDp / estimatedTextWidth
        } else {
            1f
        }
        
        // Calculate scale factor to fit height
        val heightScale = availableHeightDp / (baseSize * 1.2f) // 1.2 for line height
        
        // Use the smaller scale factor (most restrictive)
        val scale = minOf(widthScale, heightScale, 1f)
        
        // Apply scale and clamp to min/max
        val scaledSize = baseSize * scale
        return scaledSize.coerceIn(minSize, maxSize)
    }
    
    /**
     * Pre-defined preset sets for common use cases.
     */
    object Presets {
        /** Small text sizes (8-16sp) */
        val SMALL_TEXT = floatArrayOf(8f, 10f, 12f, 14f, 16f)
        
        /** Medium text sizes (12-24sp) */
        val MEDIUM_TEXT = floatArrayOf(12f, 14f, 16f, 18f, 20f, 22f, 24f)
        
        /** Large text sizes (18-36sp) */
        val LARGE_TEXT = floatArrayOf(18f, 20f, 24f, 28f, 32f, 36f)
        
        /** Button sizes (32-64dp) */
        val BUTTONS = floatArrayOf(32f, 40f, 48f, 56f, 64f)
        
        /** Icon sizes (16-48dp) */
        val ICONS = floatArrayOf(16f, 20f, 24f, 32f, 40f, 48f)
        
        /** HUD element sizes (24-72dp) */
        val HUD_ELEMENTS = floatArrayOf(24f, 32f, 40f, 48f, 56f, 64f, 72f)
    }
}

/**
 * Extension function for TextView auto-sizing.
 * 
 * @receiver TextView to auto-size
 * @param baseSize Base text size in sp
 * @param minSize Minimum text size in sp
 * @param maxSize Maximum text size in sp
 * 
 * @example
 * ```kotlin
 * scoreTextView.autoSize(
 *     baseSize = 16f,
 *     minSize = 12f,
 *     maxSize = 24f
 * )
 * ```
 */
fun TextView.autoSize(
    baseSize: Float,
    minSize: Float,
    maxSize: Float
) {
    GameAutoSize.applyAutoSizeToTextView(this, baseSize, minSize, maxSize)
}

/**
 * Extension function for View auto-sizing with callback.
 * 
 * @receiver View to auto-size
 * @param baseSize Base size in dp
 * @param minSize Minimum size in dp
 * @param maxSize Maximum size in dp
 * @param onSizeCalculated Callback with calculated size
 */
fun View.autoSize(
    baseSize: Float,
    minSize: Float,
    maxSize: Float,
    onSizeCalculated: (Float) -> Unit
) {
    GameAutoSize.createAutoSizer(this, baseSize, minSize, maxSize, onSizeCalculated)
}

