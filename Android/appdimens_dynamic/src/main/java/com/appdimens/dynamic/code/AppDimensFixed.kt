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
 * Classe para construir dimensões "fixas" que são ajustadas automaticamente
 * com base no menor `smallestScreenWidthDp` do dispositivo e na proporção da tela.
 * Compatível com View System (XML).
 */
class AppDimensFixed(
    private val initialBaseDp: Float,
    private var ignoreMultiViewAdjustment: Boolean = false
) {
    // Mapa para armazenar valores Dp customizados (Prioridade 3)
    private var customDpMap: MutableMap<DpQualifierEntry, Float> = mutableMapOf()

    // Mapa para valores Dp customizados por UiModeType (Prioridade 2)
    private var customUiModeMap: MutableMap<UiModeType, Float> = mutableMapOf()

    // Mapa para valores Dp customizados por INTERSEÇÃO (UiMode + DpQualifier) (Prioridade 1)
    private var customIntersectionMap: MutableMap<UiModeQualifierEntry, Float> = mutableMapOf()

    // Indica se o ajuste baseado na proporção (aspect ratio) deve ser aplicado.
    private var applyAspectRatioAdjustment: Boolean = true

    // Sensibilidade customizada, usa o valor padrão se for nulo.
    private var customSensitivityK: Float? = null

    // Define a dimensão da tela a ser usada como base (HIGHEST ou LOWEST).
    private var screenType: ScreenType = ScreenType.LOWEST

    // Métodos para construir as customizações (Builders)

    fun screen(type: UiModeType, customValue: Float): AppDimensFixed {
        customUiModeMap[type] = customValue
        return this
    }

    fun screen(type: UiModeType, customValue: Int): AppDimensFixed {
        customUiModeMap[type] = customValue.toFloat()
        return this
    }

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

    fun screen(type: DpQualifier, value: Int, customValue: Float): AppDimensFixed {
        customDpMap[DpQualifierEntry(type, value)] = customValue
        return this
    }

    fun screen(type: DpQualifier, value: Int, customValue: Int): AppDimensFixed {
        customDpMap[DpQualifierEntry(type, value)] = customValue.toFloat()
        return this
    }

    fun aspectRatio(enable: Boolean = true, sensitivityK: Float? = null): AppDimensFixed {
        applyAspectRatioAdjustment = enable
        customSensitivityK = sensitivityK
        return this
    }

    fun type(type: ScreenType): AppDimensFixed {
        screenType = type
        return this
    }

    fun multiViewAdjustment(ignore: Boolean = true): AppDimensFixed {
        ignoreMultiViewAdjustment = ignore
        return this
    }

    /**
     * Resolve o valor Dp base a ser ajustado, aplicando a lógica de customização
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

        // --- PRIORIDADE 1: INTERSEÇÃO (UiMode + DpQualifier) ---
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
            // --- PRIORIDADE 2: UI MODE (Apenas UiModeType) ---
            foundCustomDp = customUiModeMap[currentUiModeType]

            if (foundCustomDp != null) {
                dpToAdjust = foundCustomDp
            } else {
                // --- PRIORIDADE 3: DP QUALIFIER (Apenas SW, H, W) ---
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
     * Executa o cálculo final do ajuste da dimensão.
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

    // --- Métodos Finais para Uso em Views/XML ---

    /**
     * Constrói o valor Dp ajustado e o converte para Pixels (Float).
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
     * Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Float),
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
     * Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Int),
     * ignorando a escala de fonte do sistema (unidade 'em').
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) correspondente ao 'em', como Int.
     */
    fun toEmInt(resources: Resources): Int {
        return toEm(resources).toInt()
    }

    /**
     * Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Float).
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
     * Retorna o valor Dp ajustado (em Dp, não convertido para PX).
     *
     * @param resources Os Resources do Context.
     * @return O valor em Dp como Float.
     */
    fun toDp(resources: Resources): Float {
        return calculateAdjustedValue(resources.configuration)
    }
}