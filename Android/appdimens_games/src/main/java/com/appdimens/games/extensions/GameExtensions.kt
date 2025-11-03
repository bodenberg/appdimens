/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Fluent Extensions
 *
 * Description:
 * Fluent extension functions for easy and intuitive dimension calculations.
 * Provides a clean, type-safe API for game developers.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.extensions

import android.content.Context
import android.content.res.Resources
import com.appdimens.games.core.calculation.GamesCalculator
import com.appdimens.games.core.strategy.*
import com.appdimens.games.core.models.*
import com.appdimens.games.builders.GameDimensBuilder

/**
 * Creates a smart dimension builder for fluent API.
 * 
 * @receiver Base value in dp
 * @return Builder for fluent configuration
 * 
 * @example
 * ```kotlin
 * // Auto-infer strategy
 * val playerSize = 64f.smart()
 *     .forElement(GameElementType.PLAYER)
 *     .dp
 * 
 * // Explicit strategy
 * val buttonSize = 48f.smart()
 *     .withStrategy(GameScalingStrategy.BALANCED)
 *     .forElement(GameElementType.HUD_BUTTON)
 *     .dp
 * ```
 */
fun Float.smart(): GameDimensBuilder {
    return GameDimensBuilder(this)
}

/**
 * Creates a smart dimension builder (Int version).
 */
fun Int.smart(): GameDimensBuilder {
    return GameDimensBuilder(this.toFloat())
}

/**
 * Quick dimension calculation with auto-inferred strategy.
 * 
 * @receiver Base value in dp
 * @param resources Android Resources for screen configuration
 * @return Scaled value in dp
 * 
 * @example
 * ```kotlin
 * val size = 48f.adp(resources)
 * ```
 */
fun Float.adp(resources: Resources): Float {
    return smart().build(resources)
}

/**
 * Quick dimension calculation (Int version).
 */
fun Int.adp(resources: Resources): Float {
    return this.toFloat().adp(resources)
}

/**
 * Quick dimension calculation with context.
 * 
 * @receiver Base value in dp
 * @param context Android Context
 * @return Scaled value in dp
 */
fun Float.adp(context: Context): Float {
    return adp(context.resources)
}

/**
 * Quick dimension calculation with context (Int version).
 */
fun Int.adp(context: Context): Float {
    return this.toFloat().adp(context)
}

/**
 * Creates a BALANCED strategy dimension.
 * 
 * @receiver Base value in dp
 * @return Builder configured with BALANCED strategy
 * 
 * @example
 * ```kotlin
 * val playerSize = 64f.balanced()
 *     .forElement(GameElementType.PLAYER)
 *     .dp
 * ```
 */
fun Float.balanced(): GameDimensBuilder {
    return smart().withStrategy(GameScalingStrategy.BALANCED)
}

/**
 * Creates a BALANCED strategy dimension (Int version).
 */
fun Int.balanced(): GameDimensBuilder {
    return this.toFloat().balanced()
}

/**
 * Creates a PERCENTAGE strategy dimension.
 * 
 * @receiver Base value in dp
 * @return Builder configured with PERCENTAGE strategy
 * 
 * @example
 * ```kotlin
 * val containerWidth = 200f.percentage()
 *     .forElement(GameElementType.CONTAINER)
 *     .dp
 * ```
 */
fun Float.percentage(): GameDimensBuilder {
    return smart().withStrategy(GameScalingStrategy.PERCENTAGE)
}

/**
 * Creates a PERCENTAGE strategy dimension (Int version).
 */
fun Int.percentage(): GameDimensBuilder {
    return this.toFloat().percentage()
}

/**
 * Creates a DEFAULT strategy dimension.
 * 
 * @receiver Base value in dp
 * @return Builder configured with DEFAULT strategy
 * 
 * @example
 * ```kotlin
 * val buttonSize = 48f.default()
 *     .forElement(GameElementType.HUD_BUTTON)
 *     .dp
 * ```
 */
fun Float.default(): GameDimensBuilder {
    return smart().withStrategy(GameScalingStrategy.DEFAULT)
}

/**
 * Creates a DEFAULT strategy dimension (Int version).
 */
fun Int.default(): GameDimensBuilder {
    return this.toFloat().default()
}

/**
 * Creates a FLUID strategy dimension.
 * 
 * @receiver Base value in dp
 * @param minValue Minimum value in dp
 * @param maxValue Maximum value in dp
 * @param minWidth Minimum width breakpoint (default 320dp)
 * @param maxWidth Maximum width breakpoint (default 768dp)
 * @return Builder configured with FLUID strategy
 * 
 * @example
 * ```kotlin
 * val textSize = 16f.fluid(
 *     minValue = 12f,
 *     maxValue = 24f
 * ).dp
 * ```
 */
fun Float.fluid(
    minValue: Float,
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): GameDimensBuilder {
    return smart()
        .withStrategy(GameScalingStrategy.FLUID)
        .withFluid(
            FluidParams(
                minValue = minValue,
                maxValue = maxValue,
                minWidth = minWidth,
                maxWidth = maxWidth
            )
        )
}

/**
 * Creates a FLUID strategy dimension (Int version).
 */
fun Int.fluid(
    minValue: Float,
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): GameDimensBuilder {
    return this.toFloat().fluid(minValue, maxValue, minWidth, maxWidth)
}

/**
 * Creates a LOGARITHMIC strategy dimension.
 * 
 * @receiver Base value in dp
 * @param sensitivity Sensitivity factor (0.0-1.0, default 0.40)
 * @return Builder configured with LOGARITHMIC strategy
 * 
 * @example
 * ```kotlin
 * val tvButton = 48f.logarithmic(sensitivity = 0.5f).dp
 * ```
 */
fun Float.logarithmic(sensitivity: Float = 0.40f): GameDimensBuilder {
    return smart()
        .withStrategy(GameScalingStrategy.LOGARITHMIC)
        .withPerceptual(
            PerceptualParams(
                model = GamePerceptualModel.LOGARITHMIC,
                sensitivity = sensitivity
            )
        )
}

/**
 * Creates a LOGARITHMIC strategy dimension (Int version).
 */
fun Int.logarithmic(sensitivity: Float = 0.40f): GameDimensBuilder {
    return this.toFloat().logarithmic(sensitivity)
}

/**
 * Creates a POWER strategy dimension.
 * 
 * @receiver Base value in dp
 * @param exponent Power exponent (0.0-1.0, default 0.75)
 * @return Builder configured with POWER strategy
 * 
 * @example
 * ```kotlin
 * val scaledElement = 64f.power(exponent = 0.7f).dp
 * ```
 */
fun Float.power(exponent: Float = 0.75f): GameDimensBuilder {
    return smart()
        .withStrategy(GameScalingStrategy.POWER)
        .withPerceptual(
            PerceptualParams(
                model = GamePerceptualModel.POWER,
                powerExponent = exponent
            )
        )
}

/**
 * Creates a POWER strategy dimension (Int version).
 */
fun Int.power(exponent: Float = 0.75f): GameDimensBuilder {
    return this.toFloat().power(exponent)
}

/**
 * Creates a FIT strategy dimension (letterbox).
 * 
 * @receiver Base value in dp
 * @return Builder configured with FIT strategy
 * 
 * @example
 * ```kotlin
 * val gameViewport = 800f.fit().dp
 * ```
 */
fun Float.fit(): GameDimensBuilder {
    return smart().withStrategy(GameScalingStrategy.FIT)
}

/**
 * Creates a FIT strategy dimension (Int version).
 */
fun Int.fit(): GameDimensBuilder {
    return this.toFloat().fit()
}

/**
 * Creates a FILL strategy dimension (cover).
 * 
 * @receiver Base value in dp
 * @return Builder configured with FILL strategy
 * 
 * @example
 * ```kotlin
 * val background = 1920f.fill().dp
 * ```
 */
fun Float.fill(): GameDimensBuilder {
    return smart().withStrategy(GameScalingStrategy.FILL)
}

/**
 * Creates a FILL strategy dimension (Int version).
 */
fun Int.fill(): GameDimensBuilder {
    return this.toFloat().fill()
}

/**
 * Creates a DIAGONAL strategy dimension.
 * 
 * @receiver Base value in dp
 * @return Builder configured with DIAGONAL strategy
 * 
 * @example
 * ```kotlin
 * val touchTarget = 48f.diagonal().dp
 * ```
 */
fun Float.diagonal(): GameDimensBuilder {
    return smart().withStrategy(GameScalingStrategy.DIAGONAL)
}

/**
 * Creates a DIAGONAL strategy dimension (Int version).
 */
fun Int.diagonal(): GameDimensBuilder {
    return this.toFloat().diagonal()
}

/**
 * Creates a PERIMETER strategy dimension.
 * 
 * @receiver Base value in dp
 * @return Builder configured with PERIMETER strategy
 * 
 * @example
 * ```kotlin
 * val element = 100f.perimeter().dp
 * ```
 */
fun Float.perimeter(): GameDimensBuilder {
    return smart().withStrategy(GameScalingStrategy.PERIMETER)
}

/**
 * Creates a PERIMETER strategy dimension (Int version).
 */
fun Int.perimeter(): GameDimensBuilder {
    return this.toFloat().perimeter()
}

/**
 * Creates a NONE strategy dimension (constant).
 * 
 * @receiver Base value in dp
 * @return Builder configured with NONE strategy
 * 
 * @example
 * ```kotlin
 * val divider = 1f.none().dp // Always 1dp
 * ```
 */
fun Float.none(): GameDimensBuilder {
    return smart().withStrategy(GameScalingStrategy.NONE)
}

/**
 * Creates a NONE strategy dimension (Int version).
 */
fun Int.none(): GameDimensBuilder {
    return this.toFloat().none()
}

/**
 * Extension for element type shortcuts.
 * 
 * @receiver Builder
 * @param type Element type
 * @return Builder for chaining
 */
fun GameDimensBuilder.forElement(type: GameElementType): GameDimensBuilder {
    return this.withElementType(type)
}

/**
 * Extension to get dp value from builder.
 * 
 * @receiver Builder
 * @param resources Android Resources
 * @return Calculated value in dp
 */
val GameDimensBuilder.dp: Float
    get() = this.build(android.content.res.Resources.getSystem())

/**
 * Extension to get dp value from builder with resources.
 * 
 * @receiver Builder
 * @param resources Android Resources
 * @return Calculated value in dp
 */
fun GameDimensBuilder.dp(resources: Resources): Float {
    return this.build(resources)
}

/**
 * Extension to get dp value from builder with context.
 * 
 * @receiver Builder
 * @param context Android Context
 * @return Calculated value in dp
 */
fun GameDimensBuilder.dp(context: Context): Float {
    return this.build(context.resources)
}

/**
 * Extension to get px value from dp.
 * 
 * @receiver Value in dp
 * @param resources Android Resources
 * @return Value in px
 */
fun Float.dpToPx(resources: Resources): Float {
    return this * resources.displayMetrics.density
}

/**
 * Extension to get px value from dp (Int version).
 */
fun Int.dpToPx(resources: Resources): Int {
    return (this * resources.displayMetrics.density).toInt()
}

/**
 * Extension to get dp value from px.
 * 
 * @receiver Value in px
 * @param resources Android Resources
 * @return Value in dp
 */
fun Float.pxToDp(resources: Resources): Float {
    return this / resources.displayMetrics.density
}

/**
 * Extension to get dp value from px (Int version).
 */
fun Int.pxToDp(resources: Resources): Int {
    return (this / resources.displayMetrics.density).toInt()
}

