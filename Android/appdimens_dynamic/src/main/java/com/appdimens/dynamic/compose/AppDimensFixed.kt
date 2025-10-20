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
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.BASE_DP_FACTOR
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.BASE_INCREMENT
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.REFERENCE_AR
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.getReferenceAspectRatio
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.rememberAdjustmentFactors
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.resolveIntersectionCondition
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.resolveQualifierDp
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeQualifierEntry
import com.appdimens.library.UiModeType
import kotlin.math.ln

/**
 * [EN] A builder class for creating "fixed" dimensions that are automatically adjusted
 * based on the device's `smallestScreenWidthDp` and screen aspect ratio.
 *
 * [PT] Uma classe construtora para criar dimensões "fixas" que são ajustadas
 * automaticamente com base no `smallestScreenWidthDp` do dispositivo e na proporção da tela.
 */
class AppDimensFixed(
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

    /**
     * [EN] Indicates whether aspect ratio-based adjustment should be applied.
     *
     * [PT] Indica se o ajuste baseado na proporção (aspect ratio) deve ser aplicado.
     */
    private var applyAspectRatioAdjustment: Boolean = true

    /**
     * [EN] Custom sensitivity; uses the default value if null.
     *
     * [PT] Sensibilidade customizada; usa o valor padrão se for nulo.
     */
    private var customSensitivityK: Float? = null

    /**
     * [EN] Defines the screen dimension to be used as the base (HIGHEST or LOWEST).
     *
     * [PT] Define a dimensão da tela a ser usada como base (HIGHEST ou LOWEST).
     */
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
    fun screen(type: UiModeType, customValue: Dp): AppDimensFixed {
        customUiModeMap[type] = customValue
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts a TextUnit value.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor TextUnit.
     */
    fun screen(type: UiModeType, customValue: TextUnit): AppDimensFixed {
        customUiModeMap[type] = customValue.value.dp
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts a Float value.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor Float.
     */
    fun screen(type: UiModeType, customValue: Float): AppDimensFixed {
        customUiModeMap[type] = customValue.dp
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts an Int value.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor Int.
     */
    fun screen(type: UiModeType, customValue: Int): AppDimensFixed {
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
    ): AppDimensFixed {
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
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: TextUnit
    ): AppDimensFixed {
        val key = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue)
        )
        customIntersectionMap[key] = customValue.value.dp
        return this
    }

    /**
     * [EN] Overload for `screen` intersection that accepts a Float value.
     *
     * [PT] Sobrecarga para a interseção `screen` que aceita um valor Float.
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Float
    ): AppDimensFixed {
        val key = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue)
        )
        customIntersectionMap[key] = customValue.dp
        return this
    }

    /**
     * [EN] Overload for `screen` intersection that accepts an Int value.
     *
     * [PT] Sobrecarga para a interseção `screen` que aceita um valor Int.
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Int
    ): AppDimensFixed {
        val key = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue)
        )
        customIntersectionMap[key] = customValue.dp
        return this
    }

    /**
     * [EN] Sets a custom dimension for a specific screen qualifier.
     *
     * [PT] Define uma dimensão customizada para um qualificador de tela específico.
     */
    fun screen(type: DpQualifier, value: Int, customValue: Dp): AppDimensFixed {
        customDpMap[DpQualifierEntry(type, value)] = customValue
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts a TextUnit value for a DpQualifier.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor TextUnit para um DpQualifier.
     */
    fun screen(type: DpQualifier, value: Int, customValue: TextUnit): AppDimensFixed {
        customDpMap[DpQualifierEntry(type, value)] = customValue.value.dp
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts a Float value for a DpQualifier.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor Float para um DpQualifier.
     */
    fun screen(type: DpQualifier, value: Int, customValue: Float): AppDimensFixed {
        customDpMap[DpQualifierEntry(type, value)] = customValue.dp
        return this
    }

    /**
     * [EN] Overload for `screen` that accepts an Int value for a DpQualifier.
     *
     * [PT] Sobrecarga para `screen` que aceita um valor Int para um DpQualifier.
     */
    fun screen(type: DpQualifier, value: Int, customValue: Int): AppDimensFixed {
        customDpMap[DpQualifierEntry(type, value)] = customValue.dp
        return this
    }

    /**
     * [EN] Enables or disables aspect ratio adjustment and sets a custom sensitivity.
     *
     * [PT] Ativa ou desativa o ajuste de proporção da tela e define uma sensibilidade customizada.
     */
    fun aspectRatio(enable: Boolean = true, sensitivityK: Float? = null): AppDimensFixed {
        applyAspectRatioAdjustment = enable
        customSensitivityK = sensitivityK
        return this
    }

    /**
     * [EN] Sets the screen dimension type to be used as a reference (HIGHEST or LOWEST).
     *
     * [PT] Define o tipo de dimensão da tela a ser usado como referência (HIGHEST ou LOWEST).
     */
    fun type(type: ScreenType): AppDimensFixed {
        screenType = type
        return this
    }

    /**
     * [EN] Ignores multi-view adjustment if set to true.
     *
     * [PT] Ignora o ajuste de multi-view se definido como verdadeiro.
     */
    fun multiViewAdjustment(ignore: Boolean = true): AppDimensFixed {
        ignoreMultiViewAdjustment = ignore
        return this
    }
    
    /**
     * [EN] Enables or disables remember for this instance.
     * @param enable If true, enables remember; if false, disables remember.
     * @return The AppDimensFixed instance for chaining.
     * [PT] Ativa ou desativa o remember para esta instância.
     * @param enable Se verdadeiro, ativa o remember; se falso, desativa o remember.
     * @return A instância AppDimensFixed para encadeamento.
     */
    fun remember(enable: Boolean = true): AppDimensFixed {
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
        val adjustmentFactors = rememberAdjustmentFactors()
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

        val finalAdjustmentFactor = if (shouldIgnoreAdjustment) {
            BASE_DP_FACTOR
        } else if (applyAspectRatioAdjustment) {

            val selectedFactor = when (screenType) {
                ScreenType.HIGHEST -> adjustmentFactors.withArFactorHighest
                ScreenType.LOWEST -> adjustmentFactors.withArFactorLowest
            }

            if (customSensitivityK != null) {
                /**
                 * [EN] Recalculation of the increment factor with custom sensitivity.
                 *
                 * [PT] Recálculo do fator de incremento com sensibilidade customizada.
                 */
                val adjustmentFactorBase = when (screenType) {
                    ScreenType.HIGHEST -> adjustmentFactors.adjustmentFactorHighest
                    ScreenType.LOWEST -> adjustmentFactors.adjustmentFactorLowest
                }

                val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
                val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
                val ar = getReferenceAspectRatio(currentScreenWidthDp, currentScreenHeightDp)
                val continuousAdjustment = (customSensitivityK!! * ln(ar / REFERENCE_AR))
                val finalIncrementValue = BASE_INCREMENT + continuousAdjustment

                BASE_DP_FACTOR + adjustmentFactorBase * finalIncrementValue

            } else {
                selectedFactor /** [EN] Use the memoized factor. [PT] Usa o fator memorizado. */
            }

        } else {
            adjustmentFactors.withoutArFactor /** [EN] No AR. [PT] Sem AR. */
        }

        return dpToAdjust.value * finalAdjustmentFactor
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