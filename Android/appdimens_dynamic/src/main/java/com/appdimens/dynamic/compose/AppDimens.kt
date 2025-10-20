/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
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
package com.appdimens.dynamic.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import com.appdimens.library.DpQualifier
import com.appdimens.library.ScreenType
import kotlin.math.floor

/**
 * [EN] Singleton object that provides functions for responsive dimension management
 * in Jetpack Compose, acting as a gateway to the Fixed and Dynamic builders.
 *
 * [PT] Objeto singleton que fornece funções para gerenciamento de dimensões responsivas
 * em Jetpack Compose, agindo como um gateway para os construtores Fixed e Dynamic.
 */
@Stable
object AppDimens {

    // MARK: - Global Configuration Properties
    
    /**
     * [EN] Global remember control for all AppDimensDynamic and AppDimensFixed instances.
     * [PT] Controle global de remember para todas as instâncias AppDimensDynamic e AppDimensFixed.
     */
    @JvmStatic
    var globalRememberEnabled: Boolean = true
        set(value) {
            field = value
            if (!value) {
                // Clear all remembers when globally disabled
                clearAllRemembers()
            }
        }
    
    /**
     * [EN] Clears all remembers from all instances.
     * [PT] Limpa todos os remembers de todas as instâncias.
     */
    @JvmStatic
    fun clearAllRemembers() {
        // This would need to be implemented with a registry of instances
        // For now, individual instances will clear their own remembers
    }
    
    /**
     * [EN] Global aspect ratio adjustment setting.
     * [PT] Configuração global de ajuste de proporção.
     */
    private var globalAspectRatioEnabled: Boolean = true
    
    /**
     * [EN] Global multi-view adjustment setting.
     * [PT] Configuração global de ajuste multi-view.
     */
    private var globalIgnoreMultiViewAdjustment: Boolean = false

    // MARK: - Global Configuration Methods
    
    /**
     * [EN] Sets the global aspect ratio adjustment setting.
     * @param enabled If true, enables aspect ratio adjustment globally.
     * @return The AppDimens instance for chaining.
     * [PT] Define a configuração global de ajuste de proporção.
     * @param enabled Se verdadeiro, ativa o ajuste de proporção globalmente.
     * @return A instância AppDimens para encadeamento.
     */
    fun setGlobalAspectRatio(enabled: Boolean): AppDimens {
        globalAspectRatioEnabled = enabled
        return this
    }
    
    /**
     * [EN] Sets the global multi-view adjustment setting.
     * @param ignore If true, ignores multi-view adjustments globally.
     * @return The AppDimens instance for chaining.
     * [PT] Define a configuração global de ajuste multi-view.
     * @param ignore Se verdadeiro, ignora os ajustes multi-view globalmente.
     * @return A instância AppDimens para encadeamento.
     */
    fun setGlobalIgnoreMultiViewAdjustment(ignore: Boolean): AppDimens {
        globalIgnoreMultiViewAdjustment = ignore
        return this
    }
    
    /**
     * [EN] Gets the current global aspect ratio setting.
     * @return True if aspect ratio adjustment is enabled globally.
     * [PT] Obtém a configuração global atual de proporção.
     * @return True se o ajuste de proporção está ativado globalmente.
     */
    fun isGlobalAspectRatioEnabled(): Boolean = globalAspectRatioEnabled
    
    /**
     * [EN] Gets the current global multi-view adjustment setting.
     * @return True if multi-view adjustments are ignored globally.
     * [PT] Obtém a configuração global atual de ajuste multi-view.
     * @return True se os ajustes multi-view são ignorados globalmente.
     */
    fun isGlobalIgnoreMultiViewAdjustment(): Boolean = globalIgnoreMultiViewAdjustment

    // [EN] Helper Functions for Adjustable "Fixed" Dimensions (Gateway)
    // [PT] Funções Auxiliares para Dimensões "Fixas" Ajustáveis (Gateway)

    /**
     * [EN] Initializes the `AppDimensFixed` builder from a Dp, allowing for customizations.
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     *
     * [PT] Inicia o construtor `AppDimensFixed` a partir de um Dp, permitindo customizações.
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     */
    @Composable
    fun Dp.fixed(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimensFixed {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return AppDimensFixed(this@fixed, finalIgnoreMultiView)
            .apply { 
                if (!globalAspectRatioEnabled) {
                    aspectRatio(false)
                }
            }
    }

    /**
     * [EN] Initializes the `AppDimensFixed` builder from a TextUnit (Sp), converting it to Dp first.
     *
     * [PT] Inicia o construtor `AppDimensFixed` a partir de um TextUnit (Sp), convertendo-o primeiro para Dp.
     */
    @Composable
    fun TextUnit.fixed(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimensFixed {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return with(LocalDensity.current) { 
            AppDimensFixed(this@fixed.value.dp, finalIgnoreMultiView)
                .apply { 
                    if (!globalAspectRatioEnabled) {
                        aspectRatio(false)
                    }
                }
        }
    }

    /**
     * [EN] Initializes the `AppDimensFixed` builder from a Float, converting it to Dp first.
     *
     * [PT] Inicia o construtor `AppDimensFixed` a partir de um Float, convertendo-o primeiro para Dp.
     */
    @Composable
    fun Float.fixed(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimensFixed {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return AppDimensFixed(this@fixed.dp, finalIgnoreMultiView)
            .apply { 
                if (!globalAspectRatioEnabled) {
                    aspectRatio(false)
                }
            }
    }

    /**
     * [EN] Initializes the `AppDimensFixed` builder from an Int, converting it to Dp first.
     *
     * [PT] Inicia o construtor `AppDimensFixed` a partir de um Int, convertendo-o primeiro para Dp.
     */
    @Composable
    fun Int.fixed(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimensFixed {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return AppDimensFixed(this@fixed.dp, finalIgnoreMultiView)
            .apply { 
                if (!globalAspectRatioEnabled) {
                    aspectRatio(false)
                }
            }
    }

    /**
     * [EN] Builds the adjusted Dp.
     *
     * [PT] Constrói o Dp ajustado.
     */
    @Composable
    fun Dp.fixedDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado.
     */
    @Composable
    fun Dp.fixedSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em), ignoring font scaling.
     *
     * [PT] Constrói o TextUnit (Em) ajustado, ignorando a escala da fonte.
     */
    @Composable
    fun Dp.fixedEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /**
     * Builds the adjusted Pixel value (Float).
     *
     * Constrói o valor em Pixels (Float) ajustado.
     */
    @Composable
    fun Dp.fixedPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /**
     * [EN] Converts TextUnit (Sp) to Dp and applies dimension adjustment.
     *
     * [PT] Converte TextUnit (Sp) para Dp e aplica o ajuste de dimensão.
     */
    @Composable
    fun TextUnit.fixedDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /**
     * [EN] Converts TextUnit (Sp) to Dp, applies dimension adjustment, and returns in Pixels (Float).
     *
     * [PT] Converte TextUnit (Sp) para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
     */
    @Composable
    fun TextUnit.fixedPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /**
     * [EN] Applies dimension adjustment directly to the TextUnit (Sp).
     *
     * [PT] Aplica o ajuste de dimensão diretamente no TextUnit (Sp).
     */
    @Composable
    fun TextUnit.fixedSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /**
     * [EN] Applies dimension adjustment directly to the TextUnit (Em), ignoring font scaling.
     *
     * [PT] Aplica o ajuste de dimensão diretamente no TextUnit (Em), ignorando a escala da fonte.
     */
    @Composable
    fun TextUnit.fixedEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /**
     * [EN] Converts Float to Dp and applies dimension adjustment.
     *
     * [PT] Converte Float para Dp e aplica o ajuste de dimensão.
     */
    @Composable
    fun Float.fixedDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /**
     * [EN] Converts Float to Dp, applies dimension adjustment, and returns in Sp.
     *
     * [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Sp.
     */
    @Composable
    fun Float.fixedSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /**
     * [EN] Converts Float to Dp, applies dimension adjustment, and returns in Em (ignoring font scaling).
     *
     * [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Em (ignorando a escala da fonte).
     */
    @Composable
    fun Float.fixedEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /**
     * [EN] Converts Float to Dp, applies dimension adjustment, and returns in Pixels (Float).
     *
     * [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
     */
    @Composable
    fun Float.fixedPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /**
     * [EN] Converts Int to Dp and applies dimension adjustment.
     *
     * [PT] Converte Int para Dp e aplica o ajuste de dimensão.
     */
    @Composable
    fun Int.fixedDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /**
     * [EN] Converts Int to Dp, applies dimension adjustment, and returns in Sp.
     *
     * [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Sp.
     */
    @Composable
    fun Int.fixedSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /**
     * [EN] Converts Int to Dp, applies dimension adjustment, and returns in Em (ignoring font scaling).
     *
     * [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Em (ignorando a escala da fonte).
     */
    @Composable
    fun Int.fixedEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /**
     * [EN] Converts Int to Dp, applies dimension adjustment, and returns in Pixels (Float).
     *
     * [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
     */
    @Composable
    fun Int.fixedPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.fixed().type(type).multiViewAdjustment(ignoreMultiWindows).px

    // [EN] Convenience Extensions (Fixed)
    // [PT] Extensões de Conveniência (Fixed)

    /**
     * [EN] Builds the adjusted Dp (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("dpFxdp")
    val Dp.fxdp: Dp
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("dpFxsp")
    val Dp.fxsp: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) (defaults: LOWEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("dpFxem")
    val Dp.fxem: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("dpFxpx")
    val Dp.fxpx: Float
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from a TextUnit (Sp) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um TextUnit (Sp) (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("spFxdp")
    val TextUnit.fxdp: Dp
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("spFxsp")
    val TextUnit.fxsp: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) (defaults: LOWEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("spFxem")
    val TextUnit.fxem: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("spFxpx")
    val TextUnit.fxpx: Float
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from a Float (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um Float (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("floatFxdp")
    val Float.fxdp: Dp
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from a Float (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir de um Float (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("floatFxsp")
    val Float.fxsp: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) from a Float (defaults: LOWEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado a partir de um Float (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("floatFxem")
    val Float.fxem: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) from a Float (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Float (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("floatFxpx")
    val Float.fxpx: Float
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from an Int (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um Int (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("intFxdp")
    val Int.fxdp: Dp
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from an Int (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir de um Int (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("intFxsp")
    val Int.fxsp: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) from an Int (defaults: LOWEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado a partir de um Int (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("intFxem")
    val Int.fxem: TextUnit
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) from an Int (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Int (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("intFxpx")
    val Int.fxpx: Float
        get() = this.fixed().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hdpFxdp")
    val Dp.fxhdp: Dp
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hdpFxsp")
    val Dp.fxhsp: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) (defaults: HIGHEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("hdpFxem")
    val Dp.fxhem: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hdpFxpx")
    val Dp.fxhpx: Float
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from a TextUnit (Sp) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um TextUnit (Sp) (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hspFxdp")
    val TextUnit.fxhdp: Dp
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hspFxsp")
    val TextUnit.fxhsp: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) (defaults: HIGHEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("hspFxem")
    val TextUnit.fxhem: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hspFxpx")
    val TextUnit.fxhpx: Float
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from a Float (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hfloatFxdp")
    val Float.fxhdp: Dp
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from a Float (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hfloatFxsp")
    val Float.fxhsp: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) from a Float (defaults: HIGHEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado a partir de um Float (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("hfloatFxem")
    val Float.fxhem: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) from a Float (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hfloatFxpx")
    val Float.fxhpx: Float
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from an Int (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hintFxdp")
    val Int.fxhdp: Dp
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from an Int (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hintFxsp")
    val Int.fxhsp: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) from an Int (defaults: HIGHEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado a partir de um Int (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("hintFxem")
    val Int.fxhem: TextUnit
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) from an Int (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hintFxpx")
    val Int.fxhpx: Float
        get() = this.fixed().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

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
     * [EN] Initializes the `AppDimensDynamic` builder from a Dp, allowing for customizations.
     *
     * [PT] Inicia o construtor `AppDimensDynamic` a partir de um Dp, permitindo customizações.
     */
    @Composable
    fun Dp.dynamic(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimensDynamic {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return AppDimensDynamic(this@dynamic, finalIgnoreMultiView)
    }

    /**
     * [EN] Initializes the `AppDimensDynamic` builder from a TextUnit (Sp), converting it to Dp first.
     *
     * [PT] Inicia o construtor `AppDimensDynamic` a partir de um TextUnit (Sp), convertendo-o primeiro para Dp.
     */
    @Composable
    fun TextUnit.dynamic(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimensDynamic {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return with(LocalDensity.current) { AppDimensDynamic(this@dynamic.value.dp, finalIgnoreMultiView) }
    }

    /**
     * [EN] Initializes the `AppDimensDynamic` builder from a Float, converting it to Dp first.
     *
     * [PT] Inicia o construtor `AppDimensDynamic` a partir de um Float, convertendo-o primeiro para Dp.
     */
    @Composable
    fun Float.dynamic(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimensDynamic {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return AppDimensDynamic(this@dynamic.dp, finalIgnoreMultiView)
    }

    /**
     * [EN] Initializes the `AppDimensDynamic` builder from an Int, converting it to Dp first.
     *
     * [PT] Inicia o construtor `AppDimensDynamic` a partir de um Int, convertendo-o primeiro para Dp.
     */
    @Composable
    fun Int.dynamic(
        ignoreMultiViewAdjustment: Boolean? = null
    ): AppDimensDynamic {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return AppDimensDynamic(this@dynamic.dp, finalIgnoreMultiView)
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
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado.
     */
    @Composable
    fun Dp.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em), ignoring font scaling.
     *
     * [PT] Constrói o TextUnit (Em) ajustado, ignorando a escala da fonte.
     */
    @Composable
    fun Dp.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /**
     * [EN] Builds the adjusted Pixel value (Float).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado.
     */
    @Composable
    fun Dp.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /**
     * [EN] Converts TextUnit (Sp) to Dp and applies dimension adjustment.
     *
     * [PT] Converte TextUnit (Sp) para Dp e aplica o ajuste de dimensão.
     */
    @Composable
    fun TextUnit.dynamicDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /**
     * [EN] Converts TextUnit (Sp) to Dp, applies dimension adjustment, and returns in Pixels (Float).
     *
     * [PT] Converte TextUnit (Sp) para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
     */
    @Composable
    fun TextUnit.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /**
     * [EN] Applies dimension adjustment directly to the TextUnit (Sp).
     *
     * [PT] Aplica o ajuste de dimensão diretamente no TextUnit (Sp).
     */
    @Composable
    fun TextUnit.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /**
     * [EN] Applies dimension adjustment directly to the TextUnit (Em), ignoring font scaling.
     *
     * [PT] Aplica o ajuste de dimensão diretamente no TextUnit (Em), ignorando a escala da fonte.
     */
    @Composable
    fun TextUnit.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /**
     * [EN] Converts Float to Dp and applies dimension adjustment.
     *
     * [PT] Converte Float para Dp e aplica o ajuste de dimensão.
     */
    @Composable
    fun Float.dynamicDp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Dp =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).dp

    /**
     * [EN] Converts Float to Dp, applies dimension adjustment, and returns in Sp.
     *
     * [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Sp.
     */
    @Composable
    fun Float.dynamicSp(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).sp

    /**
     * [EN] Converts Float to Dp, applies dimension adjustment, and returns in Em (ignoring font scaling).
     *
     * [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Em (ignorando a escala da fonte).
     */
    @Composable
    fun Float.dynamicEm(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): TextUnit =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).em

    /**
     * [EN] Converts Float to Dp, applies dimension adjustment, and returns in Pixels (Float).
     *
     * [PT] Converte Float para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
     */
    @Composable
    fun Float.dynamicPx(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: Boolean = true): Float =
        this.dynamic().type(type).multiViewAdjustment(ignoreMultiWindows).px

    /**
     * [EN] Converts Int to Dp and applies dimension adjustment.
     *
     * [PT] Converte Int para Dp e aplica o ajuste de dimensão.
     */
    @Composable
    fun Int.dynamicDp(type: ScreenType = ScreenType.LOWEST): Dp = this.dynamic().type(type).dp

    /**
     * [EN] Converts Int to Dp, applies dimension adjustment, and returns in Sp.
     *
     * [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Sp.
     */
    @Composable
    fun Int.dynamicSp(type: ScreenType = ScreenType.LOWEST): TextUnit = this.dynamic().type(type).sp

    /**
     * [EN] Converts Int to Dp, applies dimension adjustment, and returns in Em (ignoring font scaling).
     *
     * [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Em (ignorando a escala da fonte).
     */
    @Composable
    fun Int.dynamicEm(type: ScreenType = ScreenType.LOWEST): TextUnit = this.dynamic().type(type).em

    /**
     * [EN] Converts Int to Dp, applies dimension adjustment, and returns in Pixels (Float).
     *
     * [PT] Converte Int para Dp, aplica o ajuste de dimensão e retorna em Pixels (Float).
     */
    @Composable
    fun Int.dynamicPx(type: ScreenType = ScreenType.LOWEST): Float = this.dynamic().type(type).px

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
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("dpDysp")
    val Dp.dysp: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) (defaults: LOWEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("dpDyem")
    val Dp.dyem: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("dpDypx")
    val Dp.dypx: Float
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from a TextUnit (Sp) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um TextUnit (Sp) (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("spDydp")
    val TextUnit.dydp: Dp
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("spDysp")
    val TextUnit.dysp: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) (defaults: LOWEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("spDyem")
    val TextUnit.dyem: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("spDypx")
    val TextUnit.dypx: Float
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from a Float (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um Float (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("floatDydp")
    val Float.dydp: Dp
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from a Float (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir de um Float (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("floatDysp")
    val Float.dysp: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) from a Float (defaults: LOWEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado a partir de um Float (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("floatDyem")
    val Float.dyem: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) from a Float (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Float (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("floatDypx")
    val Float.dypx: Float
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from an Int (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um Int (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("intDydp")
    val Int.dydp: Dp
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from an Int (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir de um Int (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("intDysp")
    val Int.dysp: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) from an Int (defaults: LOWEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado a partir de um Int (padrões: LOWEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("intDyem")
    val Int.dyem: TextUnit
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) from an Int (defaults: LOWEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Int (padrões: LOWEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("intDypx")
    val Int.dypx: Float
        get() = this.dynamic().type(ScreenType.LOWEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hdpDydp")
    val Dp.dyhdp: Dp
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hdpDysp")
    val Dp.dyhsp: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) (defaults: HIGHEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("hdpDyem")
    val Dp.dyhem: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hdpDypx")
    val Dp.dyhpx: Float
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from a TextUnit (Sp) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um TextUnit (Sp) (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hspDydp")
    val TextUnit.dyhdp: Dp
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hspDysp")
    val TextUnit.dyhsp: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) (defaults: HIGHEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("hspDyem")
    val TextUnit.dyhem: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hspDypx")
    val TextUnit.dyhpx: Float
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from a Float (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hfloatDydp")
    val Float.dyhdp: Dp
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from a Float (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hfloatDysp")
    val Float.dyhsp: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) from a Float (defaults: HIGHEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado a partir de um Float (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("hfloatDyem")
    val Float.dyhem: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) from a Float (defaults: HIGHEST, multiView=true).
     *
     * PT] Constrói o valor em Pixels (Float) ajustado a partir de um Float (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hfloatDypx")
    val Float.dyhpx: Float
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

    /**
     * [EN] Builds the adjusted Dp from an Int (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o Dp ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hintDydp")
    val Int.dyhdp: Dp
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from an Int (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hintDysp")
    val Int.dyhsp: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).sp

    /**
     * [EN] Builds the adjusted TextUnit (Em) from an Int (defaults: HIGHEST, multiView=true, ignoring font scaling).
     *
     * [PT] Constrói o TextUnit (Em) ajustado a partir de um Int (padrões: HIGHEST, multiView=true, ignorando a escala da fonte).
     */
    @get:Composable
    @get:JvmName("hintDyem")
    val Int.dyhem: TextUnit
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).em

    /**
     * [EN] Builds the adjusted Pixel value (Float) from an Int (defaults: HIGHEST, multiView=true).
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado a partir de um Int (padrões: HIGHEST, multiView=true).
     */
    @get:Composable
    @get:JvmName("hintDypx")
    val Int.dyhpx: Float
        get() = this.dynamic().type(ScreenType.HIGHEST).multiViewAdjustment(true).px

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
