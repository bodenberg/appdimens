/**
* Author & Developer: Jean Bodenberg
* GIT: https://github.com/bodenberg/appdimens.git
* Date: 2025-02-01
*
* Library: AppDimens 2.0 - Compose Extension Functions
*
* Description:
* Compose-specific extension functions for AppDimens library.
* These extensions use @Composable functions and Compose types (Dp, TextUnit).
*
* Licensed under the Apache License, Version 2.0
*/
package com.appdimens.dynamic.compose.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.annotation.SuppressLint
import com.appdimens.library.ScreenType
import com.appdimens.library.DpQualifier
import com.appdimens.dynamic.compose.AppDimens
import com.appdimens.dynamic.core.AppDimensCore
import kotlin.math.floor
import kotlin.math.min

// Helper functions to create AppDimens instances with legacy strategies
@Composable
private fun Dp.fixedCompat(): AppDimens = AppDimens.fixed(this.value)

@Composable
private fun TextUnit.fixedCompat(): AppDimens = AppDimens.fixed(this.value)

@Composable
private fun Float.fixedCompat(): AppDimens = AppDimens.fixed(this)

@Composable
private fun Int.fixedCompat(): AppDimens = AppDimens.fixed(this)

@Composable
private fun Dp.dynamicCompat(): AppDimens = AppDimens.dynamic(this.value)

@Composable
private fun TextUnit.dynamicCompat(): AppDimens = AppDimens.dynamic(this.value)

@Composable
private fun Float.dynamicCompat(): AppDimens = AppDimens.dynamic(this)

@Composable
private fun Int.dynamicCompat(): AppDimens = AppDimens.dynamic(this)

// ============================================
// COMPOSE EXTENSION FUNCTIONS
// ============================================

// MARK: - Convenience Extensions (Fixed)

/**
* [EN] Builds the adjusted Dp (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o Dp ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("dpFxdp")
val Dp.fxdp: Dp
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Em) (defaults: LOWEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("dpFxem")
val Dp.fxem: TextUnit
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("dpFxpx")
val Dp.fxpx: Float
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from a TextUnit (Sp) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um TextUnit (Sp) (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("spFxdp")
val TextUnit.fxdp: Dp
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("spFxsp")
val TextUnit.fxsp: TextUnit
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) (defaults: LOWEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("spFxem")
val TextUnit.fxem: TextUnit
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("spFxpx")
val TextUnit.fxpx: Float
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from a Float (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um Float (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("floatFxdp")
val Float.fxdp: Dp
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) from a Float (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado a partir de um Float (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("floatFxsp")
val Float.fxsp: TextUnit
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) from a Float (defaults: LOWEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado a partir de um Float (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("floatFxem")
val Float.fxem: TextUnit
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) from a Float (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Float (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("floatFxpx")
val Float.fxpx: Float
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from an Int (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um Int (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("intFxdp")
val Int.fxdp: Dp
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) from an Int (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado a partir de um Int (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("intFxsp")
val Int.fxsp: TextUnit
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) from an Int (defaults: LOWEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado a partir de um Int (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("intFxem")
val Int.fxem: TextUnit
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) from an Int (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Int (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("intFxpx")
val Int.fxpx: Float
    get() = this.fixedCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o Dp ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hdpFxdp")
val Dp.fxhdp: Dp
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hdpFxsp")
val Dp.fxhsp: TextUnit
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) (defaults: HIGHEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("hdpFxem")
val Dp.fxhem: TextUnit
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hdpFxpx")
val Dp.fxhpx: Float
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from a TextUnit (Sp) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um TextUnit (Sp) (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hspFxdp")
val TextUnit.fxhdp: Dp
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hspFxsp")
val TextUnit.fxhsp: TextUnit
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) (defaults: HIGHEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("hspFxem")
val TextUnit.fxhem: TextUnit
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hspFxpx")
val TextUnit.fxhpx: Float
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from a Float (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hfloatFxdp")
val Float.fxhdp: Dp
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) from a Float (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hfloatFxsp")
val Float.fxhsp: TextUnit
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) from a Float (defaults: HIGHEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado a partir de um Float (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("hfloatFxem")
val Float.fxhem: TextUnit
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) from a Float (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hfloatFxpx")
val Float.fxhpx: Float
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from an Int (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hintFxdp")
val Int.fxhdp: Dp
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) from an Int (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hintFxsp")
val Int.fxhsp: TextUnit
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) from an Int (defaults: HIGHEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado a partir de um Int (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("hintFxem")
val Int.fxhem: TextUnit
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) from an Int (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hintFxpx")
val Int.fxhpx: Float
    get() = this.fixedCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    // [EN] Functions for Dynamic Dimensions (Percentage-Based)
    // [PT] Funções para Dimensões Dinâmicas (Baseadas em Porcentagem)

/**
* [EN] Calculates a dynamic dimension value based on a percentage (0.0 to 1.0) of the screen dimension.
*
* [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem (0.0 a 1.0) da dimensão da tela.
*/
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
fun dynamicPercentage(
        percentage: Float,
        type: ScreenType = ScreenType.LOWEST,
    ): Float {
        require(percentage in 0.0f..1.0f) { "Percentage must be between 0.0f and 1.0f" }

val configuration = LocalConfiguration.current
val screenWidthDp = configuration.screenWidthDp.toFloat()
val screenHeightDp = configuration.screenHeightDp.toFloat()

val dimensionToUse = when (type) {
            ScreenType.HIGHEST -> maxOf(screenWidthDp, screenHeightDp)
            ScreenType.LOWEST -> minOf(screenWidthDp, screenHeightDp)
        }

        return dimensionToUse * percentage
    }

/**
* [EN] Initializes the `AppDimens` builder from a Dp using PERCENTAGE strategy.
*
* [PT] Inicia o construtor `AppDimens` a partir de um Dp usando estratégia PERCENTAGE.
* 
* @deprecated Use .smart().percentage() instead for clarity. This is kept for backward compatibility.
*/
    @Deprecated(
        message = "Use .smart().percentage() instead",
        replaceWith = ReplaceWith("AppDimens.from(this).percentage()"),
        level = DeprecationLevel.WARNING
    )
    @Composable
fun Dp.dynamic(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimens {
val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: AppDimensCore.isGlobalIgnoreMultiViewAdjustment()
        return AppDimens.from(this)
            .percentage()
            .multiViewAdjustment(finalIgnoreMultiView)
    }

/**
* [EN] Initializes the `AppDimens` builder from a TextUnit (Sp), converting it to Dp first.
*
* [PT] Inicia o construtor `AppDimens` a partir de um TextUnit (Sp), convertendo-o primeiro para Dp.
* 
* @deprecated Use .smart().percentage() instead for clarity. This is kept for backward compatibility.
*/
    @Deprecated(
        message = "Use .smart().percentage() instead",
        replaceWith = ReplaceWith("AppDimens.from(this).percentage()"),
        level = DeprecationLevel.WARNING
    )
    @Composable
fun TextUnit.dynamic(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimens {
val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: AppDimensCore.isGlobalIgnoreMultiViewAdjustment()
        return AppDimens.from(this)
            .percentage()
            .multiViewAdjustment(finalIgnoreMultiView)
    }

/**
* [EN] Initializes the `AppDimens` builder from a Float, converting it to Dp first.
*
* [PT] Inicia o construtor `AppDimens` a partir de um Float, convertendo-o primeiro para Dp.
* 
* @deprecated Use .smart().percentage() instead for clarity. This is kept for backward compatibility.
*/
    @Deprecated(
        message = "Use .smart().percentage() instead",
        replaceWith = ReplaceWith("AppDimens.from(this).percentage()"),
        level = DeprecationLevel.WARNING
    )
    @Composable
fun Float.dynamic(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimens {
val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: AppDimensCore.isGlobalIgnoreMultiViewAdjustment()
        return AppDimens.from(this)
            .percentage()
            .multiViewAdjustment(finalIgnoreMultiView)
    }

/**
* [EN] Initializes the `AppDimens` builder from an Int, converting it to Dp first.
*
* [PT] Inicia o construtor `AppDimens` a partir de um Int, convertendo-o primeiro para Dp.
* 
* @deprecated Use .smart().percentage() instead for clarity. This is kept for backward compatibility.
*/
    @Deprecated(
        message = "Use .smart().percentage() instead",
        replaceWith = ReplaceWith("AppDimens.from(this).percentage()"),
        level = DeprecationLevel.WARNING
    )
    @Composable
fun Int.dynamic(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimens {
val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: AppDimensCore.isGlobalIgnoreMultiViewAdjustment()
        return AppDimens.from(this)
            .percentage()
            .multiViewAdjustment(finalIgnoreMultiView)
    }

/**
* [EN] Calculates a dynamic Dp value based on a percentage of the screen dimension.
*
* [PT] Calcula um valor Dp dinâmico com base em uma porcentagem da dimensão da tela.
*/
    @Composable
fun dynamicPercentageDp(
        percentage: Float, type: ScreenType = ScreenType.LOWEST
    ): Dp = dynamicPercentage(percentage, type).dp

/**
* [EN] Calculates a dynamic TextUnit (Sp) value based on a percentage of the screen dimension.
*
* [PT] Calcula um valor TextUnit (Sp) dinâmico com base em uma porcentagem da dimensão da tela.
*/
    @Composable
fun dynamicPerToSp(
        percentage: Float, type: ScreenType = ScreenType.LOWEST
    ): TextUnit = dynamicPercentage(percentage, type).sp

/**
* [EN] Calculates a dynamic TextUnit (Em) value based on a percentage of the screen dimension, ignoring font scaling.
*
* [PT] Calcula um valor TextUnit (Em) dinâmico com base em uma porcentagem da dimensão da tela, ignorando a escala da fonte.
*/
    @Composable
fun dynamicPerToEm(
        percentage: Float, type: ScreenType = ScreenType.LOWEST
    ): TextUnit = (dynamicPercentage(percentage, type) / LocalDensity.current.fontScale).sp

/**
* [EN] Calculates a dynamic Float value, treating the Float receiver as the percentage.
*
* [PT] Calcula um valor Float dinâmico, tratando o Float receiver como a porcentagem.
*/
    @Composable
fun Float.dynamicPer(type: ScreenType = ScreenType.LOWEST): Float =
        with(LocalDensity.current) { dynamicPercentage(this@dynamicPer, type) }

/**
* [EN] Calculates a dynamic Dp value, treating the Float receiver as the percentage.
*
* [PT] Calcula um valor Dp dinâmico, tratando o Float receiver como a porcentagem.
*/
    @Composable
fun Float.dynamicPerDp(type: ScreenType = ScreenType.LOWEST): Dp =
        with(LocalDensity.current) { dynamicPercentageDp(this@dynamicPerDp, type) }

/**
* [EN] Calculates a dynamic TextUnit (Sp) value, treating the Float receiver as the percentage.
*
* [PT] Calcula um valor TextUnit (Sp) dinâmico, tratando o Float receiver como a porcentagem.
*/
    @Composable
fun Float.dynamicPerSp(type: ScreenType = ScreenType.LOWEST): TextUnit =
        with(LocalDensity.current) { dynamicPerToSp(this@dynamicPerSp, type) }

/**
* Calculates a dynamic TextUnit (Em) value, treating the Float receiver as the percentage and ignoring font scaling.
*
* Calcula um valor TextUnit (Em) dinâmico, tratando o Float receiver como a porcentagem e ignorando a escala da fonte.
*/
    @Composable
fun Float.dynamicPerEm(type: ScreenType = ScreenType.LOWEST): TextUnit =
        with(LocalDensity.current) { dynamicPerToEm(this@dynamicPerEm, type) }

/**
* [EN] Builds the adjusted Dp.
*
* [PT] Constrói o Dp ajustado.
*/
    @Composable
fun Dp.dynamicDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).dp

/**
* [EN] Builds the adjusted TextUnit (Sp).
*
* [PT] Constrói o TextUnit (Sp) ajustado.
*/
    @Composable
fun Dp.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).sp

/**
* [EN] Builds the adjusted TextUnit (Em), ignoring font scaling.
*
* [PT] Constrói o TextUnit (Em) ajustado, ignorando a escala da fonte.
*/
    @Composable
fun Dp.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).em

/**
* [EN] Builds the adjusted Pixel value (Float).
*
* [PT] Constrói o valor em Pixels (Float) ajustado.
*/
    @Composable
fun Dp.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).px

/**
* [EN] Converts TextUnit (Sp) to Dp and applies dimension adjustment.
*
* [PT] Converte TextUnit (Sp) para Dp e aplica o ajuste de dimensão.
*/
    @Composable
fun TextUnit.dynamicDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).dp

/**
* [EN] Converts TextUnit (Sp) to Dp, applies dimension adjustment, and returns in Pixels (Float).
*
* [PT] Converte TextUnit (Sp) para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
*/
    @Composable
fun TextUnit.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).px

/**
* [EN] Applies dimension adjustment directly to the TextUnit (Sp).
*
* [PT] Aplica o ajuste de dimensão diretamente no TextUnit (Sp).
*/
    @Composable
fun TextUnit.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).sp

/**
* [EN] Applies dimension adjustment directly to the TextUnit (Em), ignoring font scaling.
*
* [PT] Aplica o ajuste de dimensão diretamente no TextUnit (Em), ignorando a escala da fonte.
*/
    @Composable
fun TextUnit.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).em

/**
* [EN] Converts Float to Dp and applies dimension adjustment.
*
* [PT] Converte Float para Dp e aplica o ajuste de dimensão.
*/
    @Composable
fun Float.dynamicDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).dp

/**
* [EN] Converts Float to Dp, applies dimension adjustment, and returns in Sp.
*
* [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Sp.
*/
    @Composable
fun Float.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).sp

/**
* [EN] Converts Float to Dp, applies dimension adjustment, and returns in Em (ignoring font scaling).
*
* [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Em (ignorando a escala da fonte).
*/
    @Composable
fun Float.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).em

/**
* [EN] Converts Float to Dp, applies dimension adjustment, and returns in Pixels (Float).
*
* [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
*/
    @Composable
fun Float.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamicCompat().type(type).multiViewAdjustment(ignoreMultiWindows).px

/**
* [EN] Converts Int to Dp and applies dimension adjustment.
*
* [PT] Converte Int para Dp e aplica o ajuste de dimensão.
*/
    @Composable
fun Int.dynamicDp(type: ScreenType = ScreenType.LOWEST): Dp = this.dynamicCompat().type(type).dp

/**
* [EN] Converts Int to Dp, applies dimension adjustment, and returns in Sp.
*
* [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Sp.
*/
    @Composable
fun Int.dynamicSp(type: ScreenType = ScreenType.LOWEST): TextUnit = this.dynamicCompat().type(type).sp

/**
* [EN] Converts Int to Dp, applies dimension adjustment, and returns in Em (ignoring font scaling).
*
* [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Em (ignorando a escala da fonte).
*/
    @Composable
fun Int.dynamicEm(type: ScreenType = ScreenType.LOWEST): TextUnit = this.dynamicCompat().type(type).em

/**
* [EN] Converts Int to Dp, applies dimension adjustment, and returns in Pixels (Float).
*
* [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
*/
    @Composable
fun Int.dynamicPx(type: ScreenType = ScreenType.LOWEST): Float = this.dynamicCompat().type(type).px

    // [EN] Convenience Extensions (Dynamic)
    // [PT] Extensões de Conveniência (Dinâmico)

/**
* [EN] Builds the adjusted Dp (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o Dp ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("dpDydp")
val Dp.dydp: Dp
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("dpDysp")
val Dp.dysp: TextUnit
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) (defaults: LOWEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("dpDyem")
val Dp.dyem: TextUnit
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("dpDypx")
val Dp.dypx: Float
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from a TextUnit (Sp) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um TextUnit (Sp) (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("spDydp")
val TextUnit.dydp: Dp
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("spDysp")
val TextUnit.dysp: TextUnit
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) (defaults: LOWEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("spDyem")
val TextUnit.dyem: TextUnit
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("spDypx")
val TextUnit.dypx: Float
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from a Float (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um Float (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("floatDydp")
val Float.dydp: Dp
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) from a Float (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado a partir de um Float (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("floatDysp")
val Float.dysp: TextUnit
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) from a Float (defaults: LOWEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado a partir de um Float (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("floatDyem")
val Float.dyem: TextUnit
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) from a Float (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Float (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("floatDypx")
val Float.dypx: Float
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from an Int (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um Int (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("intDydp")
val Int.dydp: Dp
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) from an Int (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado a partir de um Int (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("intDysp")
val Int.dysp: TextUnit
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) from an Int (defaults: LOWEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado a partir de um Int (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("intDyem")
val Int.dyem: TextUnit
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) from an Int (defaults: LOWEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Int (padrões: LOWEST, multiView=true).
*/
@get:Composable
@get:JvmName("intDypx")
val Int.dypx: Float
    get() = this.dynamicCompat().type(ScreenType.LOWEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o Dp ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hdpDydp")
val Dp.dyhdp: Dp
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hdpDysp")
val Dp.dyhsp: TextUnit
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) (defaults: HIGHEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("hdpDyem")
val Dp.dyhem: TextUnit
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hdpDypx")
val Dp.dyhpx: Float
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from a TextUnit (Sp) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um TextUnit (Sp) (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hspDydp")
val TextUnit.dyhdp: Dp
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hspDysp")
val TextUnit.dyhsp: TextUnit
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) (defaults: HIGHEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("hspDyem")
val TextUnit.dyhem: TextUnit
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hspDypx")
val TextUnit.dyhpx: Float
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from a Float (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hfloatDydp")
val Float.dyhdp: Dp
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) from a Float (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hfloatDysp")
val Float.dyhsp: TextUnit
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) from a Float (defaults: HIGHEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado a partir de um Float (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("hfloatDyem")
val Float.dyhem: TextUnit
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) from a Float (defaults: HIGHEST, multiView=true).
*
* PT] Constrói o valor em Pixels (Float) ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hfloatDypx")
val Float.dyhpx: Float
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

/**
* [EN] Builds the adjusted Dp from an Int (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o Dp ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hintDydp")
val Int.dyhdp: Dp
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

/**
* [EN] Builds the adjusted TextUnit (Sp) from an Int (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o TextUnit (Sp) ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hintDysp")
val Int.dyhsp: TextUnit
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

/**
* [EN] Builds the adjusted TextUnit (Em) from an Int (defaults: HIGHEST, multiView=true, ignoring font scaling).
*
* [PT] Constrói o TextUnit (Em) ajustado a partir de um Int (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
*/
@get:Composable
@get:JvmName("hintDyem")
val Int.dyhem: TextUnit
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

/**
* [EN] Builds the adjusted Pixel value (Float) from an Int (defaults: HIGHEST, multiView=true).
*
* [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
*/
@get:Composable
@get:JvmName("hintDypx")
val Int.dyhpx: Float
    get() = this.dynamicCompat().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    // [EN] Layout Utilities
    // [PT] Utilitários de Layout

/**
* [EN] Calculates the maximum number of items that can fit in a Composable container.
*
* [PT] Calcula o número máximo de itens que cabem em um contêiner Composável.
*
* @param itemSize The size (width or height) of an item.
* @param itemPadding The total padding (in Dp) around each item (e.g., if there is 2dp on the sides, the padding is 4dp).
* @param direction The container dimension to be used for the calculation.
* @param onResult Callback that returns the calculated item count.
*/
    @Composable
fun CalculateAvailableItemCount(
        itemSize: Dp,
        itemPadding: Dp,
        direction: DpQualifier = DpQualifier.HEIGHT,
        @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
        onResult: (count: Int) -> Unit
    ) {
val density = LocalDensity.current
        Box(
            modifier = modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
val availableSizeDp = with(density) {
                        when (direction) {
                            DpQualifier.HEIGHT -> coordinates.size.height.toDp()
                            DpQualifier.WIDTH -> coordinates.size.width.toDp()
                            DpQualifier.SMALL_WIDTH -> {
                                val heightDp = coordinates.size.height.toDp()
                                val widthDp = coordinates.size.width.toDp()
                                if (heightDp.value < widthDp.value) heightDp else widthDp
                            }
                        }
                    }

val totalItemSize = itemSize + (itemPadding * 2)

val count = if (totalItemSize > 0.dp)
                        floor(availableSizeDp.value / totalItemSize.value).toInt()
                    else 0

                    onResult(count)
                }
        )
    }

    // MARK: - Base Orientation Shorthand Extensions
    // [EN] Convenient extensions for orientation-aware dimensions
    // [PT] Extensões convenientes para dimensões com orientação

/**
* [EN] Creates a fixed dimension for portrait design using lowest dimension (width).
* [PT] Cria uma dimensão fixa para design portrait usando menor dimensão (largura).
*/
@get:Composable
@get:JvmName("fxPortraitLowestDp")
val Dp.fxPortraitLowest: Dp
    get() = this.fixedCompat().portraitLowest().dp

@get:Composable
@get:JvmName("fxPortraitLowestFloat")
val Float.fxPortraitLowest: Dp
    get() = this.fixedCompat().portraitLowest().dp

@get:Composable
@get:JvmName("fxPortraitLowestInt")
val Int.fxPortraitLowest: Dp
    get() = this.fixedCompat().portraitLowest().dp

/**
* [EN] Creates a fixed dimension for portrait design using highest dimension (height).
* [PT] Cria uma dimensão fixa para design portrait usando maior dimensão (altura).
*/
@get:Composable
@get:JvmName("fxPortraitHighestDp")
val Dp.fxPortraitHighest: Dp
    get() = this.fixedCompat().portraitHighest().dp

@get:Composable
@get:JvmName("fxPortraitHighestFloat")
val Float.fxPortraitHighest: Dp
    get() = this.fixedCompat().portraitHighest().dp

@get:Composable
@get:JvmName("fxPortraitHighestInt")
val Int.fxPortraitHighest: Dp
    get() = this.fixedCompat().portraitHighest().dp

/**
* [EN] Creates a fixed dimension for landscape design using lowest dimension (height).
* [PT] Cria uma dimensão fixa para design landscape usando menor dimensão (altura).
*/
@get:Composable
@get:JvmName("fxLandscapeLowestDp")
val Dp.fxLandscapeLowest: Dp
    get() = this.fixedCompat().landscapeLowest().dp

@get:Composable
@get:JvmName("fxLandscapeLowestFloat")
val Float.fxLandscapeLowest: Dp
    get() = this.fixedCompat().landscapeLowest().dp

@get:Composable
@get:JvmName("fxLandscapeLowestInt")
val Int.fxLandscapeLowest: Dp
    get() = this.fixedCompat().landscapeLowest().dp

/**
* [EN] Creates a dynamic dimension for landscape design using highest dimension (width).
* [PT] Cria uma dimensão dinamica para design landscape usando maior dimensão (largura).
*/
@get:Composable
@get:JvmName("fxLandscapeHighestDp")
val Dp.fxLandscapeHighest: Dp
    get() = this.fixedCompat().landscapeHighest().dp

@get:Composable
@get:JvmName("fxLandscapeHighestFloat")
val Float.fxLandscapeHighest: Dp
    get() = this.fixedCompat().landscapeHighest().dp

@get:Composable
@get:JvmName("fxLandscapeHighestInt")
val Int.fxLandscapeHighest: Dp
    get() = this.fixedCompat().landscapeHighest().dp

// Dynamic orientation shortcuts
@get:Composable
@get:JvmName("dyPortraitLowestDp")
val Dp.dyPortraitLowest: Dp
    get() = this.dynamicCompat().portraitLowest().dp

@get:Composable
@get:JvmName("dyPortraitLowestFloat")
val Float.dyPortraitLowest: Dp
    get() = this.dynamicCompat().portraitLowest().dp

@get:Composable
@get:JvmName("dyPortraitLowestInt")
val Int.dyPortraitLowest: Dp
    get() = this.dynamicCompat().portraitLowest().dp

@get:Composable
@get:JvmName("dyPortraitHighestDp")
val Dp.dyPortraitHighest: Dp
    get() = this.dynamicCompat().portraitHighest().dp

@get:Composable
@get:JvmName("dyPortraitHighestFloat")
val Float.dyPortraitHighest: Dp
    get() = this.dynamicCompat().portraitHighest().dp

@get:Composable
@get:JvmName("dyPortraitHighestInt")
val Int.dyPortraitHighest: Dp
    get() = this.dynamicCompat().portraitHighest().dp

@get:Composable
@get:JvmName("dyLandscapeLowestDp")
val Dp.dyLandscapeLowest: Dp
    get() = this.dynamicCompat().landscapeLowest().dp

@get:Composable
@get:JvmName("dyLandscapeLowestFloat")
val Float.dyLandscapeLowest: Dp
    get() = this.dynamicCompat().landscapeLowest().dp

@get:Composable
@get:JvmName("dyLandscapeLowestInt")
val Int.dyLandscapeLowest: Dp
    get() = this.dynamicCompat().landscapeLowest().dp

/**
* [EN] Creates a dynamic dimension for landscape design using highest dimension (width).
* [PT] Cria uma dimensão dinamica para design landscape usando maior dimensão (largura).
*/
@get:Composable
@get:JvmName("dyLandscapeHighestDp")
val Dp.dyLandscapeHighest: Dp
    get() = this.dynamicCompat().landscapeHighest().dp

@get:Composable
@get:JvmName("dyLandscapeHighestFloat")
val Float.dyLandscapeHighest: Dp
    get() = this.dynamicCompat().landscapeHighest().dp

@get:Composable
@get:JvmName("dyLandscapeHighestInt")
val Int.dyLandscapeHighest: Dp
    get() = this.dynamicCompat().landscapeHighest().dp

    // ============================================
    // SMART (ADAPTIVE) EXTENSIONS - AppDimens 2.0
    // ============================================
    
/**
* [EN] Initializes AppDimens builder from Dp
* [PT] Inicia o construtor AppDimens a partir de Dp
*/
    @Composable
fun Dp.smart(): AppDimens = AppDimens.from(this)
    
/**
* [EN] Initializes AppDimens builder from Int
* [PT] Inicia o construtor AppDimens a partir de Int
*/
    @Composable
fun Int.smart(): AppDimens = AppDimens.from(this)
    
/**
* [EN] Initializes AppDimens builder from Float
* [PT] Inicia o construtor AppDimens a partir de Float
*/
    @Composable
fun Float.smart(): AppDimens = AppDimens.from(this)
    
/**
* [EN] Initializes AppDimens builder from TextUnit
* [PT] Inicia o construtor AppDimens a partir de TextUnit
*/
    @Composable
fun TextUnit.smart(): AppDimens = AppDimens.from(this)
    
    // ============================================
    // SMART SHORTHAND PROPERTIES
    // ============================================
    
/**
* [EN] Smart auto dimension (Dp) - Auto strategy inference
* [PT] Dimensão smart automática (Dp) - Inferência automática de estratégia
*/
@get:Composable
@get:JvmName("intSmartDp")
val Int.smartDp: Dp
    get() = AppDimens.from(this).dp
    
/**
* [EN] Smart auto dimension (Sp)
* [PT] Dimensão smart automática (Sp)
*/
@get:Composable
@get:JvmName("intSmartSp")
val Int.smartSp: TextUnit
    get() = AppDimens.from(this).sp
    
/**
* [EN] Adaptive dimension (Dp) - Alias for smartDp
* [PT] Dimensão adaptativa (Dp) - Alias para smartDp
*/
@get:Composable
@get:JvmName("intAdp")
val Int.adp: Dp
    get() = this.smartDp
    
/**
* [EN] Adaptive dimension (Sp) - Alias for smartSp
* [PT] Dimensão adaptativa (Sp) - Alias para smartSp
*/
@get:Composable
@get:JvmName("intAsp")
val Int.asp: TextUnit
    get() = this.smartSp
    
@get:Composable
@get:JvmName("floatSmartDp")
val Float.smartDp: Dp
    get() = AppDimens.from(this).dp
    
@get:Composable
@get:JvmName("floatSmartSp")
val Float.smartSp: TextUnit
    get() = AppDimens.from(this).sp
    
@get:Composable
@get:JvmName("floatAdp")
val Float.adp: Dp
    get() = this.smartDp
    
@get:Composable
@get:JvmName("floatAsp")
val Float.asp: TextUnit
    get() = this.smartSp
    
@get:Composable
@get:JvmName("dpSmartDp")
val Dp.smartDp: Dp
    get() = AppDimens.from(this).dp
    
@get:Composable
@get:JvmName("textUnitSmartDp")
val TextUnit.smartDp: Dp
    get() = AppDimens.from(this).dp
    
@get:Composable
@get:JvmName("textUnitSmartSp")
val TextUnit.smartSp: TextUnit
    get() = AppDimens.from(this).sp
    
    // ============================================
    // STRATEGY-SPECIFIC SHORTCUTS
    // ============================================
    
/**
* [EN] DEFAULT strategy (Fixed legacy)
* [PT] Estratégia DEFAULT (Fixed legado)
*/
@get:Composable
@get:JvmName("intDefaultDp")
val Int.defaultDp: Dp
    get() = AppDimens.from(this).default().dp
    
/**
* [EN] PERCENTAGE strategy (Dynamic legacy)
* [PT] Estratégia PERCENTAGE (Dynamic legado)
*/
@get:Composable
@get:JvmName("intPercentageDp")
val Int.percentageDp: Dp
    get() = AppDimens.from(this).percentage().dp
    
/**
* [EN] INTERPOLATED strategy
* [PT] Estratégia INTERPOLATED
*/
@get:Composable
@get:JvmName("intInterpolatedDp")
val Int.interpolatedDp: Dp
    get() = AppDimens.from(this).interpolated().dp
    
/**
* [EN] DIAGONAL strategy
* [PT] Estratégia DIAGONAL
*/
@get:Composable
@get:JvmName("intDiagonalDp")
val Int.diagonalDp: Dp
    get() = AppDimens.from(this).diagonal().dp
    
/**
* [EN] PERIMETER strategy
* [PT] Estratégia PERIMETER
*/
@get:Composable
@get:JvmName("intPerimeterDp")
val Int.perimeterDp: Dp
    get() = AppDimens.from(this).perimeter().dp
    
/**
* [EN] FIT strategy (game letterbox)
* [PT] Estratégia FIT (letterbox de game)
*/
@get:Composable
@get:JvmName("intFitDp")
val Int.fitDp: Dp
    get() = AppDimens.from(this).fit().dp
    
/**
* [EN] FILL strategy (game cover)
* [PT] Estratégia FILL (cover de game)
*/
@get:Composable
@get:JvmName("intFillDp")
val Int.fillDp: Dp
    get() = AppDimens.from(this).fill().dp
    
/**
* [EN] NONE strategy (no scaling)
* [PT] Estratégia NONE (sem escalonamento)
*/
@get:Composable
@get:JvmName("intNoneDp")
val Int.noneDp: Dp
    get() = AppDimens.from(this).none().dp
    
// Float versions
@get:Composable
@get:JvmName("floatDefaultDp")
val Float.defaultDp: Dp
    get() = AppDimens.from(this).default().dp
    
@get:Composable
@get:JvmName("floatPercentageDp")
val Float.percentageDp: Dp
    get() = AppDimens.from(this).percentage().dp
    
@get:Composable
@get:JvmName("floatInterpolatedDp")
val Float.interpolatedDp: Dp
    get() = AppDimens.from(this).interpolated().dp
    
@get:Composable
@get:JvmName("floatDiagonalDp")
val Float.diagonalDp: Dp
    get() = AppDimens.from(this).diagonal().dp
    
@get:Composable
@get:JvmName("floatPerimeterDp")
val Float.perimeterDp: Dp
    get() = AppDimens.from(this).perimeter().dp
    
@get:Composable
@get:JvmName("floatFitDp")
val Float.fitDp: Dp
    get() = AppDimens.from(this).fit().dp
    
@get:Composable
@get:JvmName("floatFillDp")
val Float.fillDp: Dp
    get() = AppDimens.from(this).fill().dp
    
@get:Composable
@get:JvmName("floatNoneDp")
val Float.noneDp: Dp
    get() = AppDimens.from(this).none().dp
    
    // ============================================
    // COMPLETE STRATEGY EXTENSIONS WITH ALL VARIANTS
    // ============================================
    
    // MARK: - BALANCED Strategy Extensions (Complete Set)
    
    // Note: balancedDp, balancedSp are defined in AppDimensPerceptual.kt
    // Only HIGHEST variants and Em/Px variants are defined here
    
// Float extensions - Em/Px variants (LOWEST)
@get:Composable
@get:JvmName("floatBalancedEm")
val Float.balancedEm: TextUnit
    get() = AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatBalancedPx")
val Float.balancedPx: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).px
    
// Float extensions - HIGHEST variants
@get:Composable
@get:JvmName("floatBalancedHdp")
val Float.balancedHdp: Dp
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp
    
@get:Composable
@get:JvmName("floatBalancedHsp")
val Float.balancedHsp: TextUnit
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatBalancedHem")
val Float.balancedHem: TextUnit
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatBalancedHpx")
val Float.balancedHpx: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).px
    
// Int extensions - Em/Px variants (LOWEST)
@get:Composable
@get:JvmName("intBalancedEm")
val Int.balancedEm: TextUnit
    get() = AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intBalancedPx")
val Int.balancedPx: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).px
    
// Int extensions - HIGHEST variants
@get:Composable
@get:JvmName("intBalancedHdp")
val Int.balancedHdp: Dp
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp
    
@get:Composable
@get:JvmName("intBalancedHsp")
val Int.balancedHsp: TextUnit
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intBalancedHem")
val Int.balancedHem: TextUnit
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intBalancedHpx")
val Int.balancedHpx: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).px
    
    // MARK: - LOGARITHMIC Strategy Extensions (Complete Set)
    
    // Note: logarithmicDp, logarithmicSp are defined in AppDimensPerceptual.kt
    // Only HIGHEST variants and Em/Px variants are defined here
    
// Float extensions - Em/Px variants (LOWEST)
@get:Composable
@get:JvmName("floatLogarithmicEm")
val Float.logarithmicEm: TextUnit
    get() = AppDimens.from(this).logarithmic().type(ScreenType.LOWEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatLogarithmicPx")
val Float.logarithmicPx: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.LOWEST).multiViewAdjustment(true).px
    
// Float extensions - HIGHEST variants
@get:Composable
@get:JvmName("floatLogarithmicHdp")
val Float.logarithmicHdp: Dp
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp
    
@get:Composable
@get:JvmName("floatLogarithmicHsp")
val Float.logarithmicHsp: TextUnit
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatLogarithmicHem")
val Float.logarithmicHem: TextUnit
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatLogarithmicHpx")
val Float.logarithmicHpx: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px
    
// Int extensions - HIGHEST variants
@get:Composable
@get:JvmName("intLogarithmicHdp")
val Int.logarithmicHdp: Dp
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp
    
@get:Composable
@get:JvmName("intLogarithmicHsp")
val Int.logarithmicHsp: TextUnit
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intLogarithmicHem")
val Int.logarithmicHem: TextUnit
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intLogarithmicHpx")
val Int.logarithmicHpx: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px
    
    // MARK: - POWER Strategy Extensions (Complete Set)
    
    // Note: powerDp, powerSp are defined in AppDimensPerceptual.kt
    // Only HIGHEST variants and Em/Px variants are defined here
    
// Float extensions - Em/Px variants (LOWEST)
@get:Composable
@get:JvmName("floatPowerEm")
val Float.powerEm: TextUnit
    get() = AppDimens.from(this).power().type(ScreenType.LOWEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatPowerPx")
val Float.powerPx: Float
    get() = AppDimens.from(this).power().type(ScreenType.LOWEST).multiViewAdjustment(true).px
    
// Float extensions - HIGHEST variants
@get:Composable
@get:JvmName("floatPowerHdp")
val Float.powerHdp: Dp
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp
    
@get:Composable
@get:JvmName("floatPowerHsp")
val Float.powerHsp: TextUnit
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatPowerHem")
val Float.powerHem: TextUnit
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatPowerHpx")
val Float.powerHpx: Float
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).px
    
// Int extensions - HIGHEST variants
@get:Composable
@get:JvmName("intPowerHdp")
val Int.powerHdp: Dp
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp
    
@get:Composable
@get:JvmName("intPowerHsp")
val Int.powerHsp: TextUnit
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intPowerHem")
val Int.powerHem: TextUnit
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intPowerHpx")
val Int.powerHpx: Float
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).px
    
    // MARK: - INTERPOLATED Strategy Extensions (Complete Set)
    
// Float extensions - LOWEST variants (Dp already exists, adding Sp/Em/Px)
@get:Composable
@get:JvmName("floatInterpolatedSp")
val Float.interpolatedSp: TextUnit
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatInterpolatedEm")
val Float.interpolatedEm: TextUnit
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatInterpolatedPx")
val Float.interpolatedPx: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).px
    
// Float extensions - HIGHEST variants
@get:Composable
@get:JvmName("floatInterpolatedHdp")
val Float.interpolatedHdp: Dp
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp
    
@get:Composable
@get:JvmName("floatInterpolatedHsp")
val Float.interpolatedHsp: TextUnit
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatInterpolatedHem")
val Float.interpolatedHem: TextUnit
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatInterpolatedHpx")
val Float.interpolatedHpx: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).px
    
// Int extensions - LOWEST variants (Dp already exists, adding Sp/Em/Px)
@get:Composable
@get:JvmName("intInterpolatedSp")
val Int.interpolatedSp: TextUnit
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intInterpolatedEm")
val Int.interpolatedEm: TextUnit
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intInterpolatedPx")
val Int.interpolatedPx: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).px
    
// Int extensions - HIGHEST variants
@get:Composable
@get:JvmName("intInterpolatedHdp")
val Int.interpolatedHdp: Dp
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp
    
@get:Composable
@get:JvmName("intInterpolatedHsp")
val Int.interpolatedHsp: TextUnit
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intInterpolatedHem")
val Int.interpolatedHem: TextUnit
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intInterpolatedHpx")
val Int.interpolatedHpx: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).px
    
    // MARK: - DIAGONAL Strategy Extensions (Complete Set)
    // Note: Dp variants already exist, adding Sp/Em/Px
    
// Float extensions - all variants
@get:Composable
@get:JvmName("floatDiagonalSp")
val Float.diagonalSp: TextUnit
    get() = AppDimens.from(this).diagonal().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatDiagonalEm")
val Float.diagonalEm: TextUnit
    get() = AppDimens.from(this).diagonal().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatDiagonalPx")
val Float.diagonalPx: Float
    get() = AppDimens.from(this).diagonal().multiViewAdjustment(true).px
    
// Int extensions - all variants
@get:Composable
@get:JvmName("intDiagonalSp")
val Int.diagonalSp: TextUnit
    get() = AppDimens.from(this).diagonal().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intDiagonalEm")
val Int.diagonalEm: TextUnit
    get() = AppDimens.from(this).diagonal().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intDiagonalPx")
val Int.diagonalPx: Float
    get() = AppDimens.from(this).diagonal().multiViewAdjustment(true).px
    
    // MARK: - PERIMETER Strategy Extensions (Complete Set)
    // Note: Dp variants already exist, adding Sp/Em/Px
    
// Float extensions - all variants
@get:Composable
@get:JvmName("floatPerimeterSp")
val Float.perimeterSp: TextUnit
    get() = AppDimens.from(this).perimeter().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatPerimeterEm")
val Float.perimeterEm: TextUnit
    get() = AppDimens.from(this).perimeter().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatPerimeterPx")
val Float.perimeterPx: Float
    get() = AppDimens.from(this).perimeter().multiViewAdjustment(true).px
    
// Int extensions - all variants
@get:Composable
@get:JvmName("intPerimeterSp")
val Int.perimeterSp: TextUnit
    get() = AppDimens.from(this).perimeter().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intPerimeterEm")
val Int.perimeterEm: TextUnit
    get() = AppDimens.from(this).perimeter().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intPerimeterPx")
val Int.perimeterPx: Float
    get() = AppDimens.from(this).perimeter().multiViewAdjustment(true).px
    
    // MARK: - FIT Strategy Extensions (Complete Set)
    // Note: Dp variants already exist, adding Sp/Em/Px
    
// Float extensions - all variants
@get:Composable
@get:JvmName("floatFitSp")
val Float.fitSp: TextUnit
    get() = AppDimens.from(this).fit().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatFitEm")
val Float.fitEm: TextUnit
    get() = AppDimens.from(this).fit().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatFitPx")
val Float.fitPx: Float
    get() = AppDimens.from(this).fit().multiViewAdjustment(true).px
    
// Int extensions - all variants
@get:Composable
@get:JvmName("intFitSp")
val Int.fitSp: TextUnit
    get() = AppDimens.from(this).fit().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intFitEm")
val Int.fitEm: TextUnit
    get() = AppDimens.from(this).fit().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intFitPx")
val Int.fitPx: Float
    get() = AppDimens.from(this).fit().multiViewAdjustment(true).px
    
    // MARK: - FILL Strategy Extensions (Complete Set)
    // Note: Dp variants already exist, adding Sp/Em/Px
    
// Float extensions - all variants
@get:Composable
@get:JvmName("floatFillSp")
val Float.fillSp: TextUnit
    get() = AppDimens.from(this).fill().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatFillEm")
val Float.fillEm: TextUnit
    get() = AppDimens.from(this).fill().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatFillPx")
val Float.fillPx: Float
    get() = AppDimens.from(this).fill().multiViewAdjustment(true).px
    
// Int extensions - all variants
@get:Composable
@get:JvmName("intFillSp")
val Int.fillSp: TextUnit
    get() = AppDimens.from(this).fill().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intFillEm")
val Int.fillEm: TextUnit
    get() = AppDimens.from(this).fill().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intFillPx")
val Int.fillPx: Float
    get() = AppDimens.from(this).fill().multiViewAdjustment(true).px
    
    // MARK: - NONE Strategy Extensions (Complete Set)
    // Note: Dp variants already exist, adding Sp/Em/Px
    
// Float extensions - all variants
@get:Composable
@get:JvmName("floatNoneSp")
val Float.noneSp: TextUnit
    get() = AppDimens.from(this).none().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("floatNoneEm")
val Float.noneEm: TextUnit
    get() = AppDimens.from(this).none().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("floatNonePx")
val Float.nonePx: Float
    get() = AppDimens.from(this).none().multiViewAdjustment(true).px
    
// Int extensions - all variants
@get:Composable
@get:JvmName("intNoneSp")
val Int.noneSp: TextUnit
    get() = AppDimens.from(this).none().multiViewAdjustment(true).sp
    
@get:Composable
@get:JvmName("intNoneEm")
val Int.noneEm: TextUnit
    get() = AppDimens.from(this).none().multiViewAdjustment(true).em
    
@get:Composable
@get:JvmName("intNonePx")
val Int.nonePx: Float
    get() = AppDimens.from(this).none().multiViewAdjustment(true).px
