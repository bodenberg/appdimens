/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Perceptual Scaling (Compose)
 *
 * Description:
 * Jetpack Compose extensions for true perceptual scaling based on psychophysics research.
 * Provides @Composable functions with automatic remember support for optimal performance.
 * 
 * All calculations delegate to code/AppDimensPerceptual which delegates to
 * core/PerceptualCore for unified calculation logic.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.compose.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.code.models.AppDimensPerceptual
import com.appdimens.dynamic.core.models.PerceptualModel
import com.appdimens.dynamic.core.optimization.PerceptualCore
import com.appdimens.library.BaseOrientation
import com.appdimens.library.DpQualifier
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeType

// ============================================
// COMPOSABLE EXTENSIONS FOR DP
// ============================================

/**
 * [EN] Extension to create perceptual Dp from Int in Compose.
 * 
 * Uses the default BALANCED model which provides linear scaling on phones
 * and logarithmic scaling on tablets/TVs for optimal visual balance.
 * 
 * @receiver Int value in dp
 * @return Perceptually scaled Dp value
 * 
 * Usage:
 * ```kotlin
 * val padding = 16.perceptualDp  // Uses default Balanced model
 * ```
 * 
 * [PT] Extensão para criar Dp perceptual a partir de Int no Compose.
 */
@get:Composable
val Int.perceptualDp: Dp
    get() {
        val configuration = LocalConfiguration.current
        return remember(this, configuration.screenWidthDp, configuration.screenHeightDp) {
            val value = AppDimensPerceptual.fromDp(this.toFloat())
                .calculateAdjustedValue(configuration)
            value.dp
        }
    }

/**
 * [EN] Extension to create perceptual Dp with specific model.
 * 
 * Allows specifying a custom perceptual model and parameters.
 * 
 * @receiver Int value in dp
 * @param model The perceptual model (LOGARITHMIC, POWER, BALANCED)
 * @param sensitivity Sensitivity for logarithmic models (default 0.40)
 * @param powerExponent Exponent for POWER model (default 0.75)
 * @return Perceptually scaled Dp value
 * 
 * @example
 * ```kotlin
 * // Pure logarithmic with custom sensitivity
 * val size = 48.perceptualDp(PerceptualModel.LOGARITHMIC, sensitivity = 0.50f)
 * 
 * // Power law with custom exponent
 * val size = 48.perceptualDp(PerceptualModel.POWER, powerExponent = 0.70f)
 * ```
 * 
 * [PT] Extensão para criar Dp perceptual com modelo específico.
 */
@Composable
fun Int.perceptualDp(
    model: PerceptualModel,
    sensitivity: Float? = null,
    powerExponent: Float? = null
): Dp {
    val configuration = LocalConfiguration.current
    return remember(this, model, sensitivity, powerExponent, 
                    configuration.screenWidthDp, configuration.screenHeightDp) {
        var builder = AppDimensPerceptual.fromDp(this.toFloat())
            .model(model, sensitivity ?: PerceptualCore.DEFAULT_SENSITIVITY)
        
        if (powerExponent != null && model == PerceptualModel.POWER) {
            builder = builder.powerExponent(powerExponent)
        }
        
        builder.calculateAdjustedValue(configuration).dp
    }
}

/**
 * [EN] Extension to create perceptual Dp from Float in Compose.
 * 
 * @receiver Float value in dp
 * @return Perceptually scaled Dp value
 * 
 * [PT] Extensão para criar Dp perceptual a partir de Float no Compose.
 */
@get:Composable
val Float.perceptualDp: Dp
    get() {
        val configuration = LocalConfiguration.current
        return remember(this, configuration.screenWidthDp, configuration.screenHeightDp) {
            val value = AppDimensPerceptual.fromDp(this)
                .calculateAdjustedValue(configuration)
            value.dp
        }
    }

/**
 * [EN] Extension to create perceptual Dp from Float with specific model.
 * 
 * @receiver Float value in dp
 * @param model The perceptual model
 * @param sensitivity Sensitivity for logarithmic models
 * @param powerExponent Exponent for POWER model
 * @return Perceptually scaled Dp value
 * 
 * [PT] Extensão para criar Dp perceptual a partir de Float com modelo específico.
 */
@Composable
fun Float.perceptualDp(
    model: PerceptualModel,
    sensitivity: Float? = null,
    powerExponent: Float? = null
): Dp {
    val configuration = LocalConfiguration.current
    return remember(this, model, sensitivity, powerExponent,
                    configuration.screenWidthDp, configuration.screenHeightDp) {
        var builder = AppDimensPerceptual.fromDp(this)
            .model(model, sensitivity ?: PerceptualCore.DEFAULT_SENSITIVITY)
        
        if (powerExponent != null && model == PerceptualModel.POWER) {
            builder = builder.powerExponent(powerExponent)
        }
        
        builder.calculateAdjustedValue(configuration).dp
    }
}

// ============================================
// COMPOSABLE EXTENSIONS FOR SP (TEXT)
// ============================================

/**
 * [EN] Extension to create perceptual TextUnit from Int for text sizing.
 * 
 * Uses the default BALANCED model optimized for text readability across devices.
 * 
 * @receiver Int value in sp
 * @return Perceptually scaled TextUnit value
 * 
 * @example
 * ```kotlin
 * Text(
 *     text = "Hello",
 *     fontSize = 16.perceptualSp
 * )
 * ```
 * 
 * [PT] Extensão para criar TextUnit perceptual a partir de Int para dimensionamento de texto.
 */
@get:Composable
val Int.perceptualSp: TextUnit
    get() {
        val configuration = LocalConfiguration.current
        return remember(this, configuration.screenWidthDp, configuration.screenHeightDp) {
            val value = AppDimensPerceptual.fromSp(this.toFloat())
                .calculateAdjustedValue(configuration)
            value.sp
        }
    }

/**
 * [EN] Extension to create perceptual TextUnit with specific model.
 * 
 * @receiver Int value in sp
 * @param model The perceptual model
 * @param sensitivity Sensitivity for logarithmic models
 * @param powerExponent Exponent for POWER model
 * @return Perceptually scaled TextUnit value
 * 
 * [PT] Extensão para criar TextUnit perceptual com modelo específico.
 */
@Composable
fun Int.perceptualSp(
    model: PerceptualModel,
    sensitivity: Float? = null,
    powerExponent: Float? = null
): TextUnit {
    val configuration = LocalConfiguration.current
    return remember(this, model, sensitivity, powerExponent,
                    configuration.screenWidthDp, configuration.screenHeightDp) {
        var builder = AppDimensPerceptual.fromSp(this.toFloat())
            .model(model, sensitivity ?: PerceptualCore.DEFAULT_SENSITIVITY)
        
        if (powerExponent != null && model == PerceptualModel.POWER) {
            builder = builder.powerExponent(powerExponent)
        }
        
        builder.calculateAdjustedValue(configuration).sp
    }
}

/**
 * [EN] Extension to create perceptual TextUnit from Float.
 * 
 * @receiver Float value in sp
 * @return Perceptually scaled TextUnit value
 * 
 * [PT] Extensão para criar TextUnit perceptual a partir de Float.
 */
@get:Composable
val Float.perceptualSp: TextUnit
    get() {
        val configuration = LocalConfiguration.current
        return remember(this, configuration.screenWidthDp, configuration.screenHeightDp) {
            val value = AppDimensPerceptual.fromSp(this)
                .calculateAdjustedValue(configuration)
            value.sp
        }
    }

/**
 * [EN] Extension to create perceptual TextUnit from Float with specific model.
 * 
 * @receiver Float value in sp
 * @param model The perceptual model
 * @param sensitivity Sensitivity for logarithmic models
 * @param powerExponent Exponent for POWER model
 * @return Perceptually scaled TextUnit value
 * 
 * [PT] Extensão para criar TextUnit perceptual a partir de Float com modelo específico.
 */
@Composable
fun Float.perceptualSp(
    model: PerceptualModel,
    sensitivity: Float? = null,
    powerExponent: Float? = null
): TextUnit {
    val configuration = LocalConfiguration.current
    return remember(this, model, sensitivity, powerExponent,
                    configuration.screenWidthDp, configuration.screenHeightDp) {
        var builder = AppDimensPerceptual.fromSp(this)
            .model(model, sensitivity ?: PerceptualCore.DEFAULT_SENSITIVITY)
        
        if (powerExponent != null && model == PerceptualModel.POWER) {
            builder = builder.powerExponent(powerExponent)
        }
        
        builder.calculateAdjustedValue(configuration).sp
    }
}

// ============================================
// SHORTHAND EXTENSIONS - BALANCED MODEL
// ============================================

/**
 * [EN] Shorthand for BALANCED model (RECOMMENDED).
 * 
 * Provides linear scaling on phones and logarithmic scaling on tablets/TVs.
 * This is the recommended model for most applications as it maintains familiar
 * behavior on phones while controlling oversizing on large screens.
 * 
 * @receiver Int value in dp
 * @param sensitivity Optional sensitivity (default 0.40)
 * @return Perceptually scaled Dp value
 * 
 * @example
 * ```kotlin
 * val buttonHeight = 48.balancedDp()
 * val customPadding = 16.balancedDp(sensitivity = 0.50f)
 * ```
 * 
 * [PT] Atalho para modelo BALANCED (RECOMENDADO).
 */
@Composable
fun Int.balancedDp(sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY): Dp =
    this.perceptualDp(PerceptualModel.BALANCED, sensitivity = sensitivity)

/**
 * [EN] Shorthand for BALANCED model for Float.
 * [PT] Atalho para modelo BALANCED para Float.
 */
@Composable
fun Float.balancedDp(sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY): Dp =
    this.perceptualDp(PerceptualModel.BALANCED, sensitivity = sensitivity)

/**
 * [EN] Shorthand for BALANCED model for text (sp).
 * [PT] Atalho para modelo BALANCED para texto (sp).
 */
@Composable
fun Int.balancedSp(sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY): TextUnit =
    this.perceptualSp(PerceptualModel.BALANCED, sensitivity = sensitivity)

/**
 * [EN] Shorthand for BALANCED model for text (sp) with Float.
 * [PT] Atalho para modelo BALANCED para texto (sp) com Float.
 */
@Composable
fun Float.balancedSp(sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY): TextUnit =
    this.perceptualSp(PerceptualModel.BALANCED, sensitivity = sensitivity)

// ============================================
// SHORTHAND EXTENSIONS - LOGARITHMIC MODEL
// ============================================

/**
 * [EN] Shorthand for pure LOGARITHMIC model (maximum control).
 * 
 * Provides pure logarithmic scaling on all screen sizes using Weber-Fechner Law.
 * Best for TVs and very large tablets where maximum size control is needed.
 * 
 * @receiver Int value in dp
 * @param sensitivity Sensitivity coefficient (default 0.40)
 * @return Perceptually scaled Dp value
 * 
 * @example
 * ```kotlin
 * val tvButton = 64.logarithmicDp()
 * val strongControl = 48.logarithmicDp(sensitivity = 0.50f)
 * ```
 * 
 * [PT] Atalho para modelo LOGARITHMIC puro (controle máximo).
 */
@Composable
fun Int.logarithmicDp(sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY): Dp =
    this.perceptualDp(PerceptualModel.LOGARITHMIC, sensitivity = sensitivity)

/**
 * [EN] Shorthand for LOGARITHMIC model for Float.
 * [PT] Atalho para modelo LOGARITHMIC para Float.
 */
@Composable
fun Float.logarithmicDp(sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY): Dp =
    this.perceptualDp(PerceptualModel.LOGARITHMIC, sensitivity = sensitivity)

/**
 * [EN] Shorthand for LOGARITHMIC model for text (sp).
 * [PT] Atalho para modelo LOGARITHMIC para texto (sp).
 */
@Composable
fun Int.logarithmicSp(sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY): TextUnit =
    this.perceptualSp(PerceptualModel.LOGARITHMIC, sensitivity = sensitivity)

/**
 * [EN] Shorthand for LOGARITHMIC model for text (sp) with Float.
 * [PT] Atalho para modelo LOGARITHMIC para texto (sp) com Float.
 */
@Composable
fun Float.logarithmicSp(sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY): TextUnit =
    this.perceptualSp(PerceptualModel.LOGARITHMIC, sensitivity = sensitivity)

// ============================================
// SHORTHAND EXTENSIONS - POWER MODEL
// ============================================

/**
 * [EN] Shorthand for POWER model (Stevens' Power Law).
 * 
 * Provides smooth, configurable scaling based on Stevens' psychophysics research.
 * The power exponent controls the strength of scaling (lower = more control).
 * 
 * @receiver Int value in dp
 * @param exponent Power exponent (default 0.75, range 0.5-1.0)
 * @return Perceptually scaled Dp value
 * 
 * @example
 * ```kotlin
 * val default = 48.powerDp()  // Uses 0.75 exponent
 * val stronger = 48.powerDp(exponent = 0.60f)  // More control
 * val weaker = 48.powerDp(exponent = 0.90f)  // Less control
 * ```
 * 
 * [PT] Atalho para modelo POWER (Lei de Potência de Stevens).
 */
@Composable
fun Int.powerDp(exponent: Float = PerceptualCore.DEFAULT_POWER_EXPONENT): Dp =
    this.perceptualDp(PerceptualModel.POWER, powerExponent = exponent)

/**
 * [EN] Shorthand for POWER model for Float.
 * [PT] Atalho para modelo POWER para Float.
 */
@Composable
fun Float.powerDp(exponent: Float = PerceptualCore.DEFAULT_POWER_EXPONENT): Dp =
    this.perceptualDp(PerceptualModel.POWER, powerExponent = exponent)

/**
 * [EN] Shorthand for POWER model for text (sp).
 * [PT] Atalho para modelo POWER para texto (sp).
 */
@Composable
fun Int.powerSp(exponent: Float = PerceptualCore.DEFAULT_POWER_EXPONENT): TextUnit =
    this.perceptualSp(PerceptualModel.POWER, powerExponent = exponent)

/**
 * [EN] Shorthand for POWER model for text (sp) with Float.
 * [PT] Atalho para modelo POWER para texto (sp) com Float.
 */
@Composable
fun Float.powerSp(exponent: Float = PerceptualCore.DEFAULT_POWER_EXPONENT): TextUnit =
    this.perceptualSp(PerceptualModel.POWER, powerExponent = exponent)

// ============================================
// ADVANCED BUILDER FUNCTIONS
// ============================================

/**
 * [EN] Creates a perceptual builder with full configuration control.
 * 
 * Provides access to the full AppDimensPerceptual builder API for advanced
 * configuration including custom qualifiers, aspect ratio control, etc.
 * 
 * @receiver Int value in dp
 * @return AppDimensPerceptual builder instance
 * 
 * @example
 * ```kotlin
 * @Composable
 * fun MyComponent() {
 *     val configuration = LocalConfiguration.current
 *     
 *     val advancedSize = remember(configuration.screenWidthDp) {
 *         48.perceptualBuilder()
 *             .model(PerceptualModel.BALANCED)
 *             .transitionPoint(500f)
 *             .aspectRatio(true, sensitivity = 0.10f)
 *             .type(ScreenType.LOWEST)
 *             .screen(UiModeType.TELEVISION, 64f)
 *             .calculateAdjustedValue(configuration)
 *             .dp
 *     }
 * }
 * ```
 * 
 * [PT] Cria um builder perceptual com controle total de configuração.
 */
fun Int.perceptualBuilder(): AppDimensPerceptual =
    AppDimensPerceptual.fromDp(this.toFloat())

/**
 * [EN] Creates a perceptual builder from Float.
 * [PT] Cria um builder perceptual a partir de Float.
 */
fun Float.perceptualBuilder(): AppDimensPerceptual =
    AppDimensPerceptual.fromDp(this)

/**
 * [EN] Creates a perceptual builder for text sizing.
 * [PT] Cria um builder perceptual para dimensionamento de texto.
 */
fun Int.perceptualSpBuilder(): AppDimensPerceptual =
    AppDimensPerceptual.fromSp(this.toFloat())

/**
 * [EN] Creates a perceptual builder for text sizing from Float.
 * [PT] Cria um builder perceptual para dimensionamento de texto a partir de Float.
 */
fun Float.perceptualSpBuilder(): AppDimensPerceptual =
    AppDimensPerceptual.fromSp(this)
