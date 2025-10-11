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
 * Classe para construir dimensões dinâmicas que permitem customização do DP base
 * via qualificadores de tela (`.screen()`). O valor final é escalado pela tela.
 */
class AppDimensDynamic(
    private val initialBaseDp: Dp,
    private var ignoreMultiViewAdjustment: Boolean = false
) {
    // Mapa para armazenar valores Dp customizados (Prioridade 3)
    private var customDpMap: MutableMap<DpQualifierEntry, Dp> = mutableMapOf()
    // Mapa para valores Dp customizados por UiModeType (Prioridade 2)
    private var customUiModeMap: MutableMap<UiModeType, Dp> = mutableMapOf()
    // Mapa para valores Dp customizados por INTERSEÇÃO (UiMode + DpQualifier) (Prioridade 1)
    private var customIntersectionMap: MutableMap<UiModeQualifierEntry, Dp> = mutableMapOf()
    private var screenType: ScreenType = ScreenType.LOWEST

    fun screen(type: UiModeType, customValue: Dp): AppDimensDynamic {
        customUiModeMap[type] = customValue
        return this
    }
    // Sobrecargas para TextUnit, Float e Int
    fun screen(type: UiModeType, customValue: TextUnit): AppDimensDynamic {
        customUiModeMap[type] = customValue.value.dp
        return this
    }
    fun screen(type: UiModeType, customValue: Float): AppDimensDynamic {
        customUiModeMap[type] = customValue.dp
        return this
    }
    fun screen(type: UiModeType, customValue: Int): AppDimensDynamic {
        customUiModeMap[type] = customValue.dp
        return this
    }

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
    // Sobrecargas para TextUnit, Float e Int na Interseção
    fun screen(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, customValue: TextUnit): AppDimensDynamic {
        val key = UiModeQualifierEntry(uiModeType = uiModeType, dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue))
        customIntersectionMap[key] = customValue.value.dp
        return this
    }
    fun screen(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, customValue: Float): AppDimensDynamic {
        val key = UiModeQualifierEntry(uiModeType = uiModeType, dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue))
        customIntersectionMap[key] = customValue.dp
        return this
    }
    fun screen(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: Int, customValue: Int): AppDimensDynamic {
        val key = UiModeQualifierEntry(uiModeType = uiModeType, dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue))
        customIntersectionMap[key] = customValue.dp
        return this
    }

    fun screen(type: DpQualifier, value: Int, customValue: Int): AppDimensDynamic {
        customDpMap[DpQualifierEntry(type, value)] = customValue.dp
        return this
    }

    // Sobrecargas para TextUnit, Float e Int no DpQualifier
    fun screen(type: DpQualifier, value: Int, customValue: Float): AppDimensDynamic {
        customDpMap[DpQualifierEntry(type, value)] = customValue.dp
        return this
    }
    fun screen(type: DpQualifier, value: Int, customValue: TextUnit): AppDimensDynamic {
        customDpMap[DpQualifierEntry(type, value)] = customValue.value.dp
        return this
    }
    fun type(type: ScreenType): AppDimensDynamic {
        screenType = type
        return this
    }

    fun multiViewAdjustment(ignore: Boolean = true): AppDimensDynamic {
        ignoreMultiViewAdjustment = ignore
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

        return remember(
            smallestWidthDp, currentScreenWidthDp, currentScreenHeightDp,
            customDpMap.hashCode(), customUiModeMap.hashCode(), customIntersectionMap.hashCode(),
            currentUiModeType
        ) {
            var dpToAdjust = initialBaseDp
            var foundCustomDp: Dp?

            // --- PRIORIDADE 1: INTERSEÇÃO (UiMode + DpQualifier) ---
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
                // --- PRIORIDADE 2: UI MODE (Apenas UiModeType) ---
                foundCustomDp = customUiModeMap[currentUiModeType]

                if (foundCustomDp != null) {
                    dpToAdjust = foundCustomDp
                } else {
                    // --- PRIORIDADE 3: DP QUALIFIER (Apenas SW, H, W) ---
                    dpToAdjust = resolveQualifierDp(
                        customDpMap = customDpMap,
                        smallestWidthDp = smallestWidthDp,
                        currentScreenWidthDp = currentScreenWidthDp,
                        currentScreenHeightDp = currentScreenHeightDp,
                        initialBaseDp = initialBaseDp
                    )
                }
            }
            dpToAdjust
        }
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
            // Retorna o Dp base sem escalonamento dinâmico
            return dpToAdjust.value
        }

        // A porcentagem de escalonamento dinâmico é: (DP Base Ajustado / DP de Referência)
        val percentage = dpToAdjust.value / BASE_WIDTH_DP

        // Dimensão da tela a ser usada (LOWEST ou HIGHEST)
        val dimensionToUse = when (screenType) {
            ScreenType.HIGHEST -> maxOf(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())
            ScreenType.LOWEST -> minOf(configuration.screenWidthDp.toFloat(), configuration.screenHeightDp.toFloat())
        }

        // O valor final é a porcentagem aplicada à dimensão da tela
        return dimensionToUse * percentage
    }

    /** Constrói o Dp ajustado a partir do cálculo. */
    @get:Composable
    val dp: Dp
        get() = calculate().dp

    /** Constrói o TextUnit (Sp) ajustado a partir do cálculo. */
    @get:Composable
    val sp: TextUnit
        get() = calculate().sp

    /** Constrói o TextUnit (Sp) ajustado a partir do cálculo. (NO FONT SCALE) */
    @get:Composable
    val em: TextUnit
        get() = (calculate() / LocalDensity.current.fontScale).sp

    /** Constrói o valor em Pixels (Float) ajustado a partir do cálculo. */
    @get:Composable
    val px: Float
        get() = calculate()
}