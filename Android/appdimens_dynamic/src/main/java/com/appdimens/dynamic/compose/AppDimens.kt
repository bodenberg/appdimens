package com.appdimens.dynamic.compose

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.appdimens.library.DpQualifier
import com.appdimens.library.ScreenType
import kotlin.math.floor

/**
 * Author & Developer: Jean Bodenberg
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
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

/**
 * Objeto singleton que fornece funções para gerenciamento de dimensões responsivas
 * em Jetpack Compose, agindo como um gateway para os construtores Fixed e Dynamic.
 */
@Stable
object AppDimens {

    // --- Funções Auxiliares para Dimensões "Fixas" Ajustáveis (Gateway) ---

    /** Inicia o construtor `AppDimensFixed` a partir de um Dp, permitindo customizações. */
    @Composable
    fun Dp.fixed(
        ignoreMultiViewAdjustment: Boolean = false
    ): AppDimensFixed = AppDimensFixed(this@fixed, ignoreMultiViewAdjustment)

    /** Inicia o construtor `AppDimensFixed` a partir de um TextUnit (Sp), convertendo-o primeiro para Dp. */
    @Composable
    fun TextUnit.fixed(
        ignoreMultiViewAdjustment: Boolean = false
    ): AppDimensFixed = with(LocalDensity.current) { AppDimensFixed(this@fixed.value.dp, ignoreMultiViewAdjustment) }

    /** Inicia o construtor `AppDimensFixed` a partir de um Float, convertendo-o primeiro para Dp. */
    @Composable
    fun Float.fixed(
        ignoreMultiViewAdjustment: Boolean = false
    ): AppDimensFixed = AppDimensFixed(this@fixed.dp, ignoreMultiViewAdjustment)

    /** Inicia o construtor `AppDimensFixed` a partir de um Int, convertendo-o primeiro para Dp. */
    @Composable
    fun Int.fixed(
        ignoreMultiViewAdjustment: Boolean = false
    ): AppDimensFixed = AppDimensFixed(this@fixed.dp, ignoreMultiViewAdjustment)

    /** Constrói o Dp ajustado. */
    @Composable
    fun Dp.fixedDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /** Constrói o TextUnit (Sp) ajustado. */
    @Composable
    fun Dp.fixedSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /** Constrói o TextUnit (Sp) ajustado. (SEM FONTE SCALE)*/
    @Composable
    fun Dp.fixedEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /** Constrói o valor em Pixels (Float) ajustado. */
    @Composable
    fun Dp.fixedPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /** Converte TextUnit (Sp) para Dp e aplica o ajuste de dimensão. */
    @Composable
    fun TextUnit.fixedDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /** Converte TextUnit (Sp) para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float). */
    @Composable
    fun TextUnit.fixedPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /** Aplica o ajuste de dimensão diretamente no TextUnit (Sp). */
    @Composable
    fun TextUnit.fixedSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /** Aplica o ajuste de dimensão diretamente no TextUnit (Sp). (SEM FONTE SCALE)*/
    @Composable
    fun TextUnit.fixedEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /** Converte Float para Dp e aplica o ajuste de dimensão. */
    @Composable
    fun Float.fixedDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /** Converte Float para Dp, aplica o ajuste de dimensão e retorna em Sp. */
    @Composable
    fun Float.fixedSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /** Converte Float para Dp, aplica o ajuste de dimensão e retorna em Sp. (SEM FONTE SCALE)*/
    @Composable
    fun Float.fixedEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /** Converte Float para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float). */
    @Composable
    fun Float.fixedPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /** Converte Int para Dp e aplica o ajuste de dimensão. */
    @Composable
    fun Int.fixedDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /** Converte Int para Dp, aplica o ajuste de dimensão e retorna em Sp. */
    @Composable
    fun Int.fixedSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /** Converte Int para Dp, aplica o ajuste de dimensão e retorna em Sp. (SEM FONTE SCALE)*/
    @Composable
    fun Int.fixedEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /** Converte Int para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float). */
    @Composable
    fun Int.fixedPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).px

    // --- Extensões de Conveniência (Fixed) ---

    /** Constrói o Dp ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("dpFxdp")
    val Dp.fxdp: Dp
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("dpFxsp")
    val Dp.fxsp: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: LOWEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("dpFxem")
    val Dp.fxem: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("dpFxpx")
    val Dp.fxpx: Float
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    @get:Composable
    @get:JvmName("spFxdp")
    val TextUnit.fxdp: Dp
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("spFxsp")
    val TextUnit.fxsp: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: LOWEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("spFxem")
    val TextUnit.fxem: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("spFxpx")
    val TextUnit.fxpx: Float
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado a partir de Float (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("floatFxdp")
    val Float.fxdp: Dp
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado a partir de Float (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("floatFxsp")
    val Float.fxsp: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado a partir de Float (com padrões: LOWEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("floatFxem")
    val Float.fxem: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado a partir de Float (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("floatFxpx")
    val Float.fxpx: Float
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado a partir de Int (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("intFxdp")
    val Int.fxdp: Dp
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado a partir de Int (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("intFxsp")
    val Int.fxsp: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado a partir de Int (com padrões: LOWEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("intFxem")
    val Int.fxem: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado a partir de Int (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("intFxpx")
    val Int.fxpx: Float
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    @get:Composable
    @get:JvmName("hdpFxdp")
    val Dp.fxhdp: Dp
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hdpFxsp")
    val Dp.fxhsp: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: HIGHEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("hdpFxem")
    val Dp.fxhem: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hdpFxpx")
    val Dp.fxhpx: Float
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    @get:Composable
    @get:JvmName("hspFxdp")
    val TextUnit.fxhdp: Dp
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hspFxsp")
    val TextUnit.fxhsp: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: HIGHEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("hspFxem")
    val TextUnit.fxhem: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hspFxpx")
    val TextUnit.fxhpx: Float
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado a partir de Float (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hfloatFxdp")
    val Float.fxhdp: Dp
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado a partir de Float (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hfloatFxsp")
    val Float.fxhsp: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado a partir de Float (com padrões: HIGHEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("hfloatFxem")
    val Float.fxhem: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado a partir de Float (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hfloatFxpx")
    val Float.fxhpx: Float
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado a partir de Int (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hintFxdp")
    val Int.fxhdp: Dp
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado a partir de Int (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hintFxsp")
    val Int.fxhsp: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado a partir de Int (com padrões: HIGHEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("hintFxem")
    val Int.fxhem: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado a partir de Int (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hintFxpx")
    val Int.fxhpx: Float
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    // --- Funções para Dimensões Dinâmicas (Baseadas em Porcentagem) ---

    /**
     * Calcula um valor de dimensão dinâmico com base em uma porcentagem (0.0 a 1.0) da dimensão da tela.
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

    /** Inicia o construtor `AppDimensDynamic` a partir de um Dp, permitindo customizações. */
    @Composable
    fun Dp.dynamic(
        ignoreMultiViewAdjustment: Boolean = false
    ): AppDimensDynamic = AppDimensDynamic(this@dynamic, ignoreMultiViewAdjustment)

    /** Inicia o construtor `AppDimensDynamic` a partir de um TextUnit (Sp), convertendo-o primeiro para Dp. */
    @Composable
    fun TextUnit.dynamic(
        ignoreMultiViewAdjustment: Boolean = false
    ): AppDimensDynamic = with(LocalDensity.current) { AppDimensDynamic(this@dynamic.value.dp, ignoreMultiViewAdjustment) }

    /** Inicia o construtor `AppDimensDynamic` a partir de um Float, convertendo-o primeiro para Dp. */
    @Composable
    fun Float.dynamic(
        ignoreMultiViewAdjustment: Boolean = false
    ): AppDimensDynamic = AppDimensDynamic(this@dynamic.dp, ignoreMultiViewAdjustment)

    /** Inicia o construtor `AppDimensDynamic` a partir de um Int, convertendo-o primeiro para Dp. */
    @Composable
    fun Int.dynamic(
        ignoreMultiViewAdjustment: Boolean = false
    ): AppDimensDynamic = AppDimensDynamic(this@dynamic.dp, ignoreMultiViewAdjustment)

    /** Calcula um valor Dp dinâmico com base em uma porcentagem da dimensão da tela. */
    @Composable
    fun dynamicPercentageDp(
        percentage: Float, type: ScreenType = ScreenType.LOWEST
    ): Dp = dynamicPercentage(percentage, type).dp

    /** Calcula um valor TextUnit (Sp) dinâmico com base em uma porcentagem da dimensão da tela. */
    @Composable
    fun dynamicPerToSp(
        percentage: Float, type: ScreenType = ScreenType.LOWEST
    ): TextUnit = dynamicPercentage(percentage, type).sp

    /** Calcula um valor TextUnit (Sp) dinâmico com base em uma porcentagem da dimensão da tela. SEM FONTE SCALE */
    @Composable
    fun dynamicPerToEm(
        percentage: Float, type: ScreenType = ScreenType.LOWEST
    ): TextUnit = (dynamicPercentage(percentage, type) / LocalDensity.current.fontScale).sp

    /** Calcula um valor Float dinâmico, tratando o Float receiver como a porcentagem. */
    @Composable
    fun Float.dynamicPer(type: ScreenType = ScreenType.LOWEST): Float =
        with(LocalDensity.current) { dynamicPercentage(this@dynamicPer, type) }

    /** Calcula um valor Dp dinâmico, tratando o Float receiver como a porcentagem. */
    @Composable
    fun Float.dynamicPerDp(type: ScreenType = ScreenType.LOWEST): Dp =
        with(LocalDensity.current) { dynamicPercentageDp(this@dynamicPerDp, type) }

    /** Calcula um valor TextUnit (Sp) dinâmico, tratando o Float receiver como a porcentagem. */
    @Composable
    fun Float.dynamicPerSp(type: ScreenType = ScreenType.LOWEST): TextUnit =
        with(LocalDensity.current) { dynamicPerToSp(this@dynamicPerSp, type) }

    /** Calcula um valor TextUnit (Sp) dinâmico, tratando o Float receiver como a porcentagem. SEM FONTE SCALE*/
    @Composable
    fun Float.dynamicPerEm(type: ScreenType = ScreenType.LOWEST): TextUnit =
        with(LocalDensity.current) { dynamicPerToEm(this@dynamicPerEm, type) }

    /** Constrói o Dp ajustado. */
    @Composable
    fun Dp.dynamicDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /** Constrói o TextUnit (Sp) ajustado. */
    @Composable
    fun Dp.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /** Constrói o TextUnit (Sp) ajustado. SEM FONTE SCALE */
    @Composable
    fun Dp.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /** Constrói o valor em Pixels (Float) ajustado. */
    @Composable
    fun Dp.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /** Converte TextUnit (Sp) para Dp e aplica o ajuste de dimensão. */
    @Composable
    fun TextUnit.dynamicDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /** Converte TextUnit (Sp) para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float). */
    @Composable
    fun TextUnit.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /** Aplica o ajuste de dimensão diretamente no TextUnit (Sp). */
    @Composable
    fun TextUnit.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /** Aplica o ajuste de dimensão diretamente no TextUnit (Sp). SEM FONTE SCALE*/
    @Composable
    fun TextUnit.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /** Converte Float para Dp e aplica o ajuste de dimensão. */
    @Composable
    fun Float.dynamicDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /** Converte Float para Dp, aplica o ajuste de dimensão e retorna em Sp. */
    @Composable
    fun Float.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /** Converte Float para Dp, aplica o ajuste de dimensão e retorna em Sp. SEM FONTE SCALE*/
    @Composable
    fun Float.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /** Converte Float para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float). */
    @Composable
    fun Float.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /** Converte Int para Dp e aplica o ajuste de dimensão. */
    @Composable
    fun Int.dynamicDp(type: ScreenType = ScreenType.LOWEST): Dp = this.dynamic().type(type).dp

    /** Converte Int para Dp, aplica o ajuste de dimensão e retorna em Sp. */
    @Composable
    fun Int.dynamicSp(type: ScreenType = ScreenType.LOWEST): TextUnit = this.dynamic().type(type).sp

    /** Converte Int para Dp, aplica o ajuste de dimensão e retorna em Sp. SEM FONTE SCALE*/
    @Composable
    fun Int.dynamicEm(type: ScreenType = ScreenType.LOWEST): TextUnit = this.dynamic().type(type).em

    /** Converte Int para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float). */
    @Composable
    fun Int.dynamicPx(type: ScreenType = ScreenType.LOWEST): Float = this.dynamic().type(type).px

    // --- Extensões de Conveniência (Dynamic) ---

    /** Constrói o Dp ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("dpDydp")
    val Dp.dydp: Dp
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("dpDysp")
    val Dp.dysp: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: LOWEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("dpDyem")
    val Dp.dyem: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("dpDypx")
    val Dp.dypx: Float
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    @get:Composable
    @get:JvmName("spDydp")
    val TextUnit.dydp: Dp
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("spDysp")
    val TextUnit.dysp: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: LOWEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("spDyem")
    val TextUnit.dyem: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("spDypx")
    val TextUnit.dypx: Float
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado a partir de Float (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("floatDydp")
    val Float.dydp: Dp
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado a partir de Float (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("floatDysp")
    val Float.dysp: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado a partir de Float (com padrões: LOWEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("floatDyem")
    val Float.dyem: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado a partir de Float (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("floatDypx")
    val Float.dypx: Float
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado a partir de Int (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("intDydp")
    val Int.dydp: Dp
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado a partir de Int (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("intDysp")
    val Int.dysp: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado a partir de Int (com padrões: LOWEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("intDyem")
    val Int.dyem: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado a partir de Int (com padrões: LOWEST, multiView=true). */
    @get:Composable
    @get:JvmName("intDypx")
    val Int.dypx: Float
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hdpDydp")
    val Dp.dyhdp: Dp
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hdpDysp")
    val Dp.dyhsp: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: HIGHEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("hdpDyem")
    val Dp.dyhem: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hdpDypx")
    val Dp.dyhpx: Float
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    @get:Composable
    @get:JvmName("hspDydp")
    val TextUnit.dyhdp: Dp
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hspDysp")
    val TextUnit.dyhsp: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado (com padrões: HIGHEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("hspDyem")
    val TextUnit.dyhem: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hspDypx")
    val TextUnit.dyhpx: Float
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado a partir de Float (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hfloatDydp")
    val Float.dyhdp: Dp
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado a partir de Float (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hfloatDysp")
    val Float.dyhsp: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado a partir de Float (com padrões: HIGHEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("hfloatDyem")
    val Float.dyhem: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado a partir de Float (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hfloatDypx")
    val Float.dyhpx: Float
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /** Constrói o Dp ajustado a partir de Int (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hintDydp")
    val Int.dyhdp: Dp
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /** Constrói o TextUnit (Sp) ajustado a partir de Int (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hintDysp")
    val Int.dyhsp: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /** Constrói o TextUnit (Sp) ajustado a partir de Int (com padrões: HIGHEST, multiView=true, SEM FONTE SCALE). */
    @get:Composable
    @get:JvmName("hintDyem")
    val Int.dyhem: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /** Constrói o valor em Pixels (Float) ajustado a partir de Int (com padrões: HIGHEST, multiView=true). */
    @get:Composable
    @get:JvmName("hintDypx")
    val Int.dyhpx: Float
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    // --- Utilitários de Layout ---

    /**
     * Calcula o número máximo de itens que cabem em um contêiner Composável.
     *
     * @param itemSize O tamanho (largura ou altura) de um item.
     * @param itemPadding O padding total (em Dp) em torno de cada item (ex: se for 2dp nas laterais, o padding é 4dp).
     * @param direction A dimensão do contêiner a ser usada para o cálculo.
     * @param onResult Callback que retorna a contagem de itens calculada.
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
                .onGloballyPositioned{ coordinates ->
                    val availableSizeDp = with(density) {
                        when (direction) {
                            DpQualifier.HEIGHT -> coordinates.size.height.toDp()
                            DpQualifier.WIDTH -> coordinates.size.width.toDp()
                            DpQualifier.SMALL_WIDTH -> {
                                val heightDp = coordinates.size.height.toDp()
                                val widthDp = coordinates.size.width.toDp()
                                min(heightDp, widthDp)
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
}