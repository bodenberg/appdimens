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
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.BASE_WIDTH_DP
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.resolveIntersectionCondition
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.resolveQualifierDp
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeQualifierEntry
import com.appdimens.library.UiModeType

/**
 * [EN] A builder class for creating dynamic dimensions that allow base Dp customization
 * via screen qualifiers (`.screen()`). The final value is scaled by the screen size.
 *
 * [PT] Uma classe construtora para criar dimensões dinâmicas que permitem a customização do Dp base
 * por meio de qualificadores de tela (`.screen()`). O valor final é escalado pelo tamanho da tela.
 */
class AppDimensDynamic(
    private val initialBaseDp: Dp,
    private var ignoreMultiViewAdjustment: Boolean = false
) {
    
    /**
     * [EN] Map to store custom Dp values (Priority 3).
     *
     * [PT] Mapa para armazenar valores Dp customizados (Prioridade 3).
     */
    private var customDpMap: MutableMap<DpQualifierEntry, Dp> = mutableMapOf()

    /**
     * [EN] Map for custom Dp values by UiModeType (Priority 2).
     *
     * [PT] Mapa para valores Dp customizados por UiModeType (Prioridade 2).
     */
    private var customUiModeMap: MutableMap<UiModeType, Dp> = mutableMapOf()

    /**
     * [EN] Map for custom Dp values by INTERSECTION (UiMode + DpQualifier) (Priority 1).
     *
     * [PT] Mapa para valores Dp customizados por INTERSEÇÃO (UiMode + DpQualifier) (Prioridade 1).
     */
    private var customIntersectionMap: MutableMap<UiModeQualifierEntry, Dp> = mutableMapOf()

    private var screenType: ScreenType = ScreenType.LOWEST
    
    /**
     * [EN] Individual remember control for this instance.
     * [PT] Controle individual de remember para esta instância.
     */
    private var enableRemember: Boolean = true

    /**
     * [EN] Sets a custom dimension for a specific UI mode.
     *
     * [PT] Define uma dimensão customizada para um modo de UI específico.
     */
    fun screen(type: UiModeType, customValue: Dp): AppDimensDynamic {
        customUiModeMap[type] = customValue
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts a TextUnit value.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor TextUnit.
     */
    fun screen(type: UiModeType, customValue: TextUnit): AppDimensDynamic {
        customUiModeMap[type] = customValue.value.dp
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts a Float value.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor Float.
     */
    fun screen(type: UiModeType, customValue: Float): AppDimensDynamic {
        customUiModeMap[type] = customValue.dp
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts an Int value.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor Int.
     */
    fun screen(type: UiModeType, customValue: Int): AppDimensDynamic {
        customUiModeMap[type] = customValue.dp
        return this
    }

    /**
     * [EN] Sets a custom dimension for a specific intersection of UI mode and screen qualifier.
     *
     * [PT] Define uma dimensão customizada para uma interseção específica de modo de UI e qualificador de tela.
     */
    fun screen(
        uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, customValue: Dp
    ): AppDimensDynamic {
        val key = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue)
        )
        customIntersectionMap[key] = customValue
        return this
    }

    /**
     * [EN] Overload for `screen` intersection that accepts a TextUnit value.
     *
     * [PT] Sobrecarga para a interseção `screen` que aceita um valor TextUnit.
     */
    fun screen(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, customValue: TextUnit): AppDimensDynamic {
        val key = UiModeQualifierEntry(uiModeType = uiModeType, dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue))
        customIntersectionMap[key] = customValue.value.dp
        return this
    }

    /**
     * [EN] Overload for `screen` intersection that accepts a Float value.
     *
     * [PT] Sobrecarga para a interseção `screen` que aceita um valor Float.
     */
    fun screen(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, customValue: Float): AppDimensDynamic {
        val key = UiModeQualifierEntry(uiModeType = uiModeType, dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue))
        customIntersectionMap[key] = customValue.dp
        return this
    }

    /**
     * [EN] Overload for `screen` intersection that accepts an Int value.
     *
     * [PT] Sobrecarga para a interseção `screen` que aceita um valor Int.
     */
    fun screen(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, customValue: Int): AppDimensDynamic {
        val key = UiModeQualifierEntry(uiModeType = uiModeType, dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue))
        customIntersectionMap[key] = customValue.dp
        return this
    }

    /**
     * [EN] Sets a custom dimension for a specific screen qualifier.
     *
     * [PT] Define uma dimensão customizada para um qualificador de tela específico.
     */
    fun screen(type: DpQualifier, value: Int, customValue: Int): AppDimensDynamic {
        customDpMap[DpQualifierEntry(type, value)] = customValue.dp
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts a Float value for a DpQualifier.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor Float para um DpQualifier.
     */
    fun screen(type: DpQualifier, value: Int, customValue: Float): AppDimensDynamic {
        customDpMap[DpQualifierEntry(type, value)] = customValue.dp
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts a TextUnit value for a DpQualifier.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor TextUnit para um DpQualifier.
     */
    fun screen(type: DpQualifier, value: Int, customValue: TextUnit): AppDimensDynamic {
        customDpMap[DpQualifierEntry(type, value)] = customValue.value.dp
        return this
    }

    /**
     * [EN] Sets the screen dimension type to be used as a reference (HIGHEST or LOWEST).
     *
     * [PT] Define o tipo de dimensão da tela a ser usado como referência (HIGHEST ou LOWEST).
     */
    fun type(type: ScreenType): AppDimensDynamic {
        screenType = type
        return this
    }

    /**
     * [EN] Ignores multi-view adjustment if set to true.
     *
     * [PT] Ignora o ajuste de multi-view se definido como verdadeiro.
     */
    fun multiViewAdjustment(ignore: Boolean = true): AppDimensDynamic {
        ignoreMultiViewAdjustment = ignore
        return this
    }
    
    /**
     * [EN] Enables or disables remember for this instance.
     * @param enable If true, enables remember; if false, disables remember.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Ativa ou desativa o remember para esta instância.
     * @param enable Se verdadeiro, ativa o remember; se falso, desativa o remember.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    fun remember(enable: Boolean = true): AppDimensDynamic {
        enableRemember = enable
        return this
    }

    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    private fun rememberFinalBaseDp(): Dp {
        val configuration = LocalConfiguration.current

        val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()

        val currentUiModeType = UiModeType.fromConfiguration(configuration.uiMode)

        return if (AppDimens.globalRememberEnabled && enableRemember) {
            remember(
                smallestWidthDp, currentScreenWidthDp, currentScreenHeightDp,
                customDpMap.hashCode(), customUiModeMap.hashCode(), customIntersectionMap.hashCode(),
                currentUiModeType
            ) {
                calculateBaseDp(
                    smallestWidthDp, currentScreenWidthDp, currentScreenHeightDp,
                    currentUiModeType
                )
            }
        } else {
            calculateBaseDp(
                smallestWidthDp, currentScreenWidthDp, currentScreenHeightDp,
                currentUiModeType
            )
        }
    }
    
    @SuppressLint("ConfigurationScreenWidthHeight")
    private fun calculateBaseDp(
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float,
        currentUiModeType: UiModeType
    ): Dp {
            var dpToAdjust = initialBaseDp
            var foundCustomDp: Dp?

            /**
             * [EN] PRIORITY 1: INTERSECTION (UiMode + DpQualifier)
             *
             * [PT] PRIORIDADE 1: INTERSEÇÃO (UiMode + DpQualifier)
             */
            val sortedIntersectionQualifiers = customIntersectionMap.entries.toList()
                .sortedByDescending { it.key.dpQualifierEntry.value }

            foundCustomDp = sortedIntersectionQualifiers.firstOrNull { (key, _) ->
                key.uiModeType == currentUiModeType && resolveIntersectionCondition(
                    entry = key.dpQualifierEntry,
                    smallestWidthDp = smallestWidthDp,
                    currentScreenWidthDp = currentScreenWidthDp,
                    currentScreenHeightDp = currentScreenHeightDp
                )
            }?.value

            if (foundCustomDp != null) {
                dpToAdjust = foundCustomDp
            } else {
                /**
                 * [EN] PRIORITY 2: UI MODE (UiModeType only)
                 *
                 * [PT] PRIORIDADE 2: UI MODE (Apenas UiModeType)
                 */
                foundCustomDp = customUiModeMap[currentUiModeType]

                if (foundCustomDp != null) {
                    dpToAdjust = foundCustomDp
                } else {
                    /**
                     * [EN] PRIORITY 3: DP QUALIFIER (SW, H, W only)
                     *
                     * [PT] PRIORIDADE 3: DP QUALIFIER (Apenas SW, H, W)
                     */
                    dpToAdjust = resolveQualifierDp(
                        customDpMap = customDpMap,
                        smallestWidthDp = smallestWidthDp,
                        currentScreenWidthDp = currentScreenWidthDp,
                        currentScreenHeightDp = currentScreenHeightDp,
                        initialBaseDp = initialBaseDp
                    )
                }
            }
            return dpToAdjust
    }


    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    private fun calculate(): Float {
        val dpToAdjust = rememberFinalBaseDp()
        val configuration = LocalConfiguration.current

        var isMultiWindow = ignoreMultiViewAdjustment

        if (ignoreMultiViewAdjustment) {
            val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
            val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
            val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
            val isSmallDifference = (smallestWidthDp - currentScreenWidthDp) < (smallestWidthDp * 0.1)
            isMultiWindow = isLayoutSplit && !isSmallDifference
        }

        val shouldIgnoreAdjustment = ignoreMultiViewAdjustment && isMultiWindow

        if (shouldIgnoreAdjustment) {
            /**
             * [EN] Returns the base Dp without dynamic scaling.
             *
             * [PT] Retorna o Dp base sem escalonamento dinâmico.
             */
            return dpToAdjust.value
        }

        /**
         * [EN] The dynamic scaling percentage is: (Adjusted Base DP / Reference DP).
         *
         * [PT] A porcentagem de escalonamento dinâmico é: (DP Base Ajustado / DP de Referência).
         */
        val percentage = dpToAdjust.value / BASE_WIDTH_DP

        /**
         * [EN] Screen dimension to use (LOWEST or HIGHEST).
         *
         * [PT] Dimensão da tela a ser usada (LOWEST ou HIGHEST).
         */
        val dimensionToUse = when (screenType) {
            ScreenType.HIGHEST -> maxOf(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())
            ScreenType.LOWEST -> minOf(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())
        }

        /**
         * [EN] The final value is the percentage applied to the screen dimension.
         *
         * [PT] O valor final é a porcentagem aplicada à dimensão da tela.
         */
        return dimensionToUse * percentage
    }

    /**
     * [EN] Builds the adjusted Dp from the calculation.
     *
     * [PT] Constrói o Dp ajustado a partir do cálculo.
     */
    @get:Composable
    val dp: Dp
        get() = calculate().dp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from the calculation.
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir do cálculo.
     */
    @get:Composable
    val sp: TextUnit
        get() = calculate().sp

    /**
     * [EN] Builds the adjusted TextUnit (Sp) from the calculation (NO FONT SCALE).
     *
     * [PT] Constrói o TextUnit (Sp) ajustado a partir do cálculo (SEM ESCALA DE FONTE).
     */
    @get:Composable
    val em: TextUnit
        get() = (calculate() / LocalDensity.current.fontScale).sp

    /**
     * [EN] Builds the adjusted Pixel value (Float) from the calculation.
     *
     * [PT] Constrói o valor em Pixels (Float) ajustado a partir do cálculo.
     */
    @get:Composable
    val px: Float
        get() = calculate()
}