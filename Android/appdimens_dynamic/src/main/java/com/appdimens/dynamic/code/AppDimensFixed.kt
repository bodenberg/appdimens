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
package com.appdimens.dynamic.code

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeQualifierEntry
import com.appdimens.library.UiModeType
import kotlin.math.ln

/**
 * [EN] Class for building "fixed" dimensions that are automatically adjusted
 * based on the device's smallestScreenWidthDp and screen aspect ratio.
 * Compatible with the View System (XML).
 *
 * [PT] Classe para construir dimensões "fixas" que são ajustadas automaticamente
 * com base no menor `smallestScreenWidthDp` do dispositivo e na proporção da tela.
 * Compatível com View System (XML).
 */
class AppDimensFixed(
    private val initialBaseDp: Float,
    private var ignoreMultiViewAdjustment: Boolean = false
) {
    /**
     * [EN] Map to store custom Dp values based on `DpQualifier` (Priority 3).
     * [PT] Mapa para armazenar valores Dp customizados com base no `DpQualifier` (Prioridade 3).
     */
    private var customDpMap: MutableMap<DpQualifierEntry, Float> = mutableMapOf()

    /**
     * [EN] Map for custom Dp values based on `UiModeType` (Priority 2).
     * [PT] Mapa para valores Dp customizados com base no `UiModeType` (Prioridade 2).
     */
    private var customUiModeMap: MutableMap<UiModeType, Float> = mutableMapOf()

    /**
     * [EN] Map for custom Dp values based on the intersection of `UiModeType` and `DpQualifier` (Priority 1).
     * [PT] Mapa para valores Dp customizados com base na interseção de `UiModeType` e `DpQualifier` (Prioridade 1).
     */
    private var customIntersectionMap: MutableMap<UiModeQualifierEntry, Float> = mutableMapOf()

    /**
     * [EN] Indicates whether the aspect ratio-based adjustment should be applied.
     * [PT] Indica se o ajuste baseado na proporção (aspect ratio) deve ser aplicado.
     */
    private var applyAspectRatioAdjustment: Boolean = true

    /**
     * [EN] Custom sensitivity for the aspect ratio adjustment. Uses the default value if null.
     * [PT] Sensibilidade customizada para o ajuste de proporção. Usa o valor padrão se for nulo.
     */
    private var customSensitivityK: Float? = null

    /**
     * [EN] Defines the screen dimension to be used as a base (HIGHEST or LOWEST).
     * [PT] Define a dimensão da tela a ser usada como base (HIGHEST ou LOWEST).
     */
    private var screenType: ScreenType = ScreenType.LOWEST

    /**
     * [EN] Sets a custom dimension value for a specific UI mode.
     * @param type The UI mode (`UiModeType`).
     * @param customValue The custom dimension value in Dp.
     * @return The `AppDimensFixed` instance for chaining.
     *
     * [PT] Define um valor de dimensão customizado para um modo de UI específico.
     * @param type O modo de UI (`UiModeType`).
     * @param customValue O valor de dimensão customizado em Dp.
     * @return A instância `AppDimensFixed` para encadeamento.
     */
    fun screen(type: UiModeType, customValue: Float): AppDimensFixed {
        customUiModeMap[type] = customValue
        return this
    }

    fun screen(type: UiModeType, customValue: Int): AppDimensFixed {
        customUiModeMap[type] = customValue.toFloat()
        return this
    }

    /**
     * [EN] Sets a custom dimension for the intersection of a UI mode and a screen qualifier.
     * @param uiModeType The UI mode (`UiModeType`).
     * @param qualifierType The qualifier type (`DpQualifier`).
     * @param qualifierValue The qualifier value (e.g., 600 for sw600dp).
     * @param customValue The custom dimension value in Dp.
     * @return The `AppDimensFixed` instance for chaining.
     *
     * [PT] Define uma dimensão customizada para a interseção de um modo de UI e um qualificador de tela.
     * @param uiModeType O modo de UI (`UiModeType`).
     * @param qualifierType O tipo de qualificador (`DpQualifier`).
     * @param qualifierValue O valor do qualificador (ex: 600 para sw600dp).
     * @param customValue O valor de dimensão customizado em Dp.
     * @return A instância `AppDimensFixed` para encadeamento.
     */
    fun screen(
        uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, customValue: Float
    ): AppDimensFixed {
        val key = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue)
        )
        customIntersectionMap[key] = customValue
        return this
    }

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
        customIntersectionMap[key] = customValue.toFloat()
        return this
    }

    /**
     * [EN] Sets a custom dimension value for a specific screen qualifier.
     * @param type The qualifier type (`DpQualifier`).
     * @param value The qualifier value (e.g., 600 for sw600dp).
     * @param customValue The custom dimension value in Dp.
     * @return The `AppDimensFixed` instance for chaining.
     *
     * [PT] Define um valor de dimensão customizado para um qualificador de tela específico.
     * @param type O tipo de qualificador (`DpQualifier`).
     * @param value O valor do qualificador (ex: 600 para sw600dp).
     * @param customValue O valor da dimensão customizada em Dp.
     * @return A instância `AppDimensFixed` para encadeamento.
     */
    fun screen(type: DpQualifier, value: Int, customValue: Float): AppDimensFixed {
        customDpMap[DpQualifierEntry(type, value)] = customValue
        return this
    }

    fun screen(type: DpQualifier, value: Int, customValue: Int): AppDimensFixed {
        customDpMap[DpQualifierEntry(type, value)] = customValue.toFloat()
        return this
    }

    /**
     * [EN] Enables or disables the aspect ratio adjustment.
     * @param enable If true, enables the adjustment.
     * @param sensitivityK Optional custom sensitivity for the adjustment.
     * @return The `AppDimensFixed` instance for chaining.
     *
     * [PT] Ativa ou desativa o ajuste de proporção.
     * @param enable Se verdadeiro, ativa o ajuste.
     * @param sensitivityK Sensibilidade customizada opcional para o ajuste.
     * @return A instância `AppDimensFixed` para encadeamento.
     */
    fun aspectRatio(enable: Boolean = true, sensitivityK: Float? = null): AppDimensFixed {
        applyAspectRatioAdjustment = enable
        customSensitivityK = sensitivityK
        return this
    }

    /**
     * [EN] Sets the screen dimension type (LOWEST or HIGHEST) to be used as the base for adjustments.
     * @param type The screen dimension type.
     * @return The `AppDimensFixed` instance for chaining.
     *
     * [PT] Define o tipo de dimensão da tela (LOWEST ou HIGHEST) a ser usado como base para os ajustes.
     * @param type O tipo de dimensão da tela.
     * @return A instância `AppDimensFixed` para encadeamento.
     */
    fun type(type: ScreenType): AppDimensFixed {
        screenType = type
        return this
    }

    /**
     * [EN] Ignores adjustments when the app is in multi-window mode.
     * @param ignore If true, adjustments are ignored in multi-window mode.
     * @return The `AppDimensFixed` instance for chaining.
     *
     * [PT] Ignora os ajustes quando o aplicativo está em modo multi-janela.
     * @param ignore Se verdadeiro, os ajustes são ignorados no modo multi-janela.
     * @return A instância `AppDimensFixed` para encadeamento.
     */
    fun multiViewAdjustment(ignore: Boolean = true): AppDimensFixed {
        ignoreMultiViewAdjustment = ignore
        return this
    }

    /**
     * [EN] Resolves the base Dp value to be adjusted by applying the customization logic
     * (Intersection > UiMode > DpQualifier).
     *
     * @param configuration The current screen configuration.
     * @return The base Dp value (unadjusted for the screen) to be used in the final calculation.
     *
     * [PT] Resolve o valor Dp base a ser ajustado, aplicando a lógica de customização
     * (Interseção > UiMode > DpQualifier).
     *
     * @param configuration A configuração da tela atual.
     * @return O valor Dp base (não ajustado pela tela) a ser usado no cálculo final.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    private fun resolveFinalBaseDp(configuration: Configuration): Float {
        val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
        val currentUiModeType = UiModeType.fromConfiguration(configuration.uiMode)

        var dpToAdjust = initialBaseDp
        var foundCustomDp: Float?

        // Priority 1: Intersection (UiMode + DpQualifier)
        val sortedIntersectionQualifiers = customIntersectionMap.entries.toList()
            .sortedByDescending { it.key.dpQualifierEntry.value }

        foundCustomDp = sortedIntersectionQualifiers.firstOrNull { (key, _) ->
            key.uiModeType == currentUiModeType && AppDimensAdjustmentFactors.resolveIntersectionCondition(
                entry = key.dpQualifierEntry,
                smallestWidthDp = smallestWidthDp,
                currentScreenWidthDp = currentScreenWidthDp,
                currentScreenHeightDp = currentScreenHeightDp
            )
        }?.value

        if (foundCustomDp != null) {
            dpToAdjust = foundCustomDp
        } else {
            // Priority 2: UI Mode (UiModeType only)
            foundCustomDp = customUiModeMap[currentUiModeType]

            if (foundCustomDp != null) {
                dpToAdjust = foundCustomDp
            } else {
                // Priority 3: Dp Qualifier (SW, H, W only)
                dpToAdjust = AppDimensAdjustmentFactors.resolveQualifierDp(
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

    /**
     * [EN] Performs the final dimension adjustment calculation.
     *
     * @param configuration The current screen configuration.
     * @return The adjusted Dp value as a **Float** (not converted to PX/SP).
     *
     * [PT] Executa o cálculo final do ajuste da dimensão.
     *
     * @param configuration A Configuration da tela atual.
     * @return O valor **Float** em Dp ajustado (não convertido para PX/SP).
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun calculateAdjustedValue(configuration: Configuration): Float {
        val dpToAdjust = resolveFinalBaseDp(configuration)
        val adjustmentFactors = AppDimensAdjustmentFactors.calculateAdjustmentFactors(configuration)
        var isMultiWindow = false

        if (ignoreMultiViewAdjustment) {
            val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
            val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
            val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
            val isSmallDifference = (smallestWidthDp - currentScreenWidthDp) < (smallestWidthDp * 0.1)
            isMultiWindow = isLayoutSplit && !isSmallDifference
        }

        val shouldIgnoreAdjustment = ignoreMultiViewAdjustment && isMultiWindow

        val finalAdjustmentFactor = if (shouldIgnoreAdjustment) {
            AppDimensAdjustmentFactors.BASE_DP_FACTOR
        } else if (applyAspectRatioAdjustment) {

            val selectedFactor = when (screenType) {
                ScreenType.HIGHEST -> adjustmentFactors.withArFactorHighest
                ScreenType.LOWEST -> adjustmentFactors.withArFactorLowest
            }

            if (customSensitivityK != null) {
                val adjustmentFactorBase = when (screenType) {
                    ScreenType.HIGHEST -> adjustmentFactors.adjustmentFactorHighest
                    ScreenType.LOWEST -> adjustmentFactors.adjustmentFactorLowest
                }

                val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
                val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
                val ar = AppDimensAdjustmentFactors.getReferenceAspectRatio(currentScreenWidthDp, currentScreenHeightDp)
                val continuousAdjustment = (customSensitivityK!! * ln(ar / AppDimensAdjustmentFactors.REFERENCE_AR)).toFloat()
                val finalIncrementValue = AppDimensAdjustmentFactors.BASE_INCREMENT + continuousAdjustment

                AppDimensAdjustmentFactors.BASE_DP_FACTOR + adjustmentFactorBase * finalIncrementValue

            } else {
                selectedFactor
            }

        } else {
            adjustmentFactors.withoutArFactor
        }

        return dpToAdjust * finalAdjustmentFactor
    }

    /**
     * [EN] Builds the adjusted Dp value and converts it to Pixels (Float).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) as a Float.
     *
     * [PT] Constrói o valor Dp ajustado e o converte para Pixels (Float).
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) como Float.
     */
    fun toPx(resources: Resources): Float {
        val adjustedDpValue = calculateAdjustedValue(resources.configuration)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, adjustedDpValue, resources.displayMetrics
        )
    }

    /**
     * [EN] Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float),
     * ignoring the system's font scale ('em' unit).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) corresponding to 'em', as a Float.
     *
     * [PT] Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Float),
     * ignorando a escala de fonte do sistema (unidade 'em').
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) correspondente ao 'em', como Float.
     */
    fun toEm(resources: Resources): Float {
        val adjustedDpValue = calculateAdjustedValue(resources.configuration)
        val fontScale = resources.configuration.fontScale
        val dpValueWithoutFontScale = adjustedDpValue / fontScale
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dpValueWithoutFontScale, resources.displayMetrics
        )
    }

    /**
     * [EN] Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Int),
     * ignoring the system's font scale ('em' unit).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) corresponding to 'em', as an Int.
     *
     * [PT] Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Int),
     * ignorando a escala de fonte do sistema (unidade 'em').
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) correspondente ao 'em', como Int.
     */
    fun toEmInt(resources: Resources): Int {
        return toEm(resources).toInt()
    }

    /**
     * [EN] Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) corresponding to SP, as a Float.
     *
     * [PT] Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Float).
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) correspondente ao SP, como Float.
     */
    fun toSp(resources: Resources): Float {
        val adjustedDpValue = calculateAdjustedValue(resources.configuration)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, adjustedDpValue, resources.displayMetrics
        )
    }

    /**
     * [EN] Returns the adjusted Dp value (in Dp, not converted to PX).
     *
     * @param resources The Context's Resources.
     * @return The value in Dp as a Float.
     *
     * [PT] Retorna o valor Dp ajustado (em Dp, não convertido para PX).
     *
     * @param resources Os Resources do Context.
     * @return O valor em Dp como Float.
     */
    fun toDp(resources: Resources): Float {
        return calculateAdjustedValue(resources.configuration)
    }
}