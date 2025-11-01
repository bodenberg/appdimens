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
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import kotlin.math.PI
import kotlin.math.ln

/**
 * [EN] A singleton object that provides functions for calculating and resolving
 * adjustment factors and screen qualifiers. Compatible with the traditional
 * Android View System (XML).
 *
 * [PT] Objeto singleton que fornece funções para o cálculo e resolução
 * de fatores de ajuste e qualificadores de tela. Compatível com o sistema
 * tradicional de Views XML do Android.
 */
object AppDimensAdjustmentFactors {

    /**
     * [EN] Base Dp scaling factor. Default is 1.0f (no adjustment).
     * [PT] Fator base de escala Dp. O valor padrão é 1.0f (sem ajuste).
     */
    const val BASE_DP_FACTOR = 1.00f

    /**
     * [EN] Base reference Dp width for adjustment calculation (e.g., 300dp).
     * [PT] Largura DP de referência base para o cálculo de ajuste (ex: 300dp).
     */
    const val BASE_WIDTH_DP = 300f

    /**
     * [EN] Dp increment step size for calculating the adjustment (e.g., every 1dp).
     * [PT] Tamanho do passo de incremento em Dp para calcular o ajuste (ex: a cada 1dp).
     */
    const val INCREMENT_DP_STEP = 1f

    /**
     * [EN] Factor for circumference calculation ($2\pi$). Uses kotlin.math.PI.
     * [PT] Fator para cálculo de circunferência ($2\pi$). Usando kotlin.math.PI.
     */
    const val CIRCUMFERENCE_FACTOR = PI * 2.0

    /**
     * [EN] Reference aspect ratio (e.g., 16:9), where the adjustment is neutral.
     * [PT] Proporção de tela de referência (Ex: 16:9), onde o ajuste é neutro.
     */
    const val REFERENCE_AR = 1.78f

    /**
     * [EN] DEFAULT sensitivity coefficient: Adjusts how aggressive the scaling is on extreme screens.
     * [PT] Coeficiente de sensibilidade PADRÃO: Ajusta o quão agressivo é o escalonamento em telas extremas.
     */
    const val DEFAULT_SENSITIVITY_K = 0.08f / 30f  // Adjusted for 1dp step granularity

    /**
     * [EN] Default increment factor (used in calculations WITHOUT Aspect Ratio).
     * [PT] Fator de incremento padrão (usado no cálculo SEM Aspect Ratio).
     */
    const val BASE_INCREMENT = 0.10f / 30f  // Adjusted for 1dp step granularity

    /**
     * [EN] Helper function that isolates the logic for searching and selecting the custom Dp value
     * through Qualifiers (SW, H, W).
     *
     * [PT] Função auxiliar que isola a lógica de busca e seleção do valor Dp
     * customizado através dos Qualificadores (SW, H, W).
     *
     * @param customDpMap Map of Dp qualifiers.
     * @param smallestWidthDp smallestScreenWidthDp from the current configuration.
     * @param currentScreenWidthDp Screen width in Dp.
     * @param currentScreenHeightDp Screen height in Dp.
     * @param initialBaseDp Initial Dp value (as Float).
     * @return The custom Dp value or the initial value (as Float).
     */
    fun resolveQualifierDp(
        customDpMap: Map<DpQualifierEntry, Float>,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float,
        initialBaseDp: Float
    ): Float {
        var dpToAdjust = initialBaseDp

        // Filter and sort qualifiers in descending order to ensure the LARGEST matching
        // qualifier is applied (e.g., 900dp should have priority over 600dp).
        val sortedQualifiers = customDpMap.entries.toList().sortedByDescending { it.key.value }

        var foundCustomDp: Float? = null

        // Priority 1: SMALL_WIDTH (smallestScreenWidthDp)
        foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
            key.type == DpQualifier.SMALL_WIDTH && smallestWidthDp >= key.value
        }?.value

        if (foundCustomDp != null) {
            dpToAdjust = foundCustomDp
        } else {
            // Priority 2: HEIGHT (screenHeightDp)
            foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
                key.type == DpQualifier.HEIGHT && currentScreenHeightDp >= key.value
            }?.value

            if (foundCustomDp != null) {
                dpToAdjust = foundCustomDp
            } else {
                // Priority 3: WIDTH (screenWidthDp)
                foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
                    key.type == DpQualifier.WIDTH && currentScreenWidthDp >= key.value
                }?.value

                if (foundCustomDp != null) {
                    dpToAdjust = foundCustomDp
                }
            }
        }
        return dpToAdjust
    }

    /**
     * [EN] Helper function that checks if a [DpQualifierEntry] meets the current screen dimensions.
     * [PT] Função auxiliar que verifica se um [DpQualifierEntry] atende às dimensões atuais da tela.
     */
    fun resolveIntersectionCondition(
        entry: DpQualifierEntry,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float
    ): Boolean = when (entry.type) {
        DpQualifier.HEIGHT -> currentScreenHeightDp >= entry.value
        DpQualifier.WIDTH -> currentScreenWidthDp >= entry.value
        else -> smallestWidthDp >= entry.value
    }

    /**
     * [EN] Helper function to get the aspect ratio from the configuration.
     * @return The screen aspect ratio (Largest dimension / Smallest dimension).
     *
     * [PT] Função auxiliar para obter a proporção de tela a partir da configuração.
     * @return A proporção da tela (Maior dimensão / Menor dimensão).
     */
    fun getReferenceAspectRatio(screenWidthDp: Float, screenHeightDp: Float): Float {
        // Aspect Ratio: (Largest dimension / Smallest dimension)
        return if (screenWidthDp > screenHeightDp)
            screenWidthDp / screenHeightDp
        else screenHeightDp / screenWidthDp
    }

    /**
     * [EN] Calculates the Basic Adjustment Factors.
     *
     * @param configuration The current screen configuration.
     * @return A [ScreenAdjustmentFactors] object containing the adjustment factors.
     *
     * [PT] Calcula os Fatores Básicos de Ajuste.
     *
     * @param configuration A Configuration da tela atual.
     * @return Um objeto [ScreenAdjustmentFactors] contendo os fatores de ajuste.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun calculateAdjustmentFactors(configuration: Configuration): ScreenAdjustmentFactors {
        val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
        val highestDimensionDp = maxOf(currentScreenWidthDp, currentScreenHeightDp)

        // Base Adjustment Factor (LOWEST - smallestWidthDp)
        val differenceLowest = smallestWidthDp - BASE_WIDTH_DP
        val adjustmentFactorLowest = differenceLowest / INCREMENT_DP_STEP

        // Base Adjustment Factor (HIGHEST - Max(W,H))
        val differenceHighest = highestDimensionDp - BASE_WIDTH_DP
        val adjustmentFactorHighest = differenceHighest / INCREMENT_DP_STEP

        // Factor WITHOUT Aspect Ratio (uses LOWEST factor for safety)
        val withoutArFactor = BASE_DP_FACTOR + adjustmentFactorLowest * BASE_INCREMENT

        // Get the current screen aspect ratio
        val currentAr = getReferenceAspectRatio(currentScreenWidthDp, currentScreenHeightDp)

        // Continuous calculation (Logarithmic function) to smooth the scaling adjustment
        val continuousAdjustment =
            (DEFAULT_SENSITIVITY_K * ln(currentAr / REFERENCE_AR)).toFloat()
        val finalIncrementValueWithAr = BASE_INCREMENT + continuousAdjustment

        // COMPLETE Factor (LOWEST + AR)
        val withArFactorLowest = BASE_DP_FACTOR + adjustmentFactorLowest * finalIncrementValueWithAr

        // COMPLETE Factor (HIGHEST + AR)
        val withArFactorHighest = BASE_DP_FACTOR + adjustmentFactorHighest * finalIncrementValueWithAr

        // Return all factors, storing both base adjustment types
        return ScreenAdjustmentFactors(
            withArFactorLowest = withArFactorLowest,
            withArFactorHighest = withArFactorHighest,
            withoutArFactor = withoutArFactor,
            adjustmentFactorLowest = adjustmentFactorLowest,
            adjustmentFactorHighest = adjustmentFactorHighest
        )
    }

    /**
     * [EN] Resolves the effective ScreenType based on the base orientation and current device orientation.
     * If the base orientation differs from the current orientation, LOWEST and HIGHEST are inverted.
     *
     * @param requestedType The originally requested screen type (LOWEST or HIGHEST)
     * @param baseOrientation The orientation for which the design was created (PORTRAIT, LANDSCAPE, or AUTO)
     * @param configuration The current screen configuration
     * @return The resolved ScreenType (may be inverted from requestedType)
     *
     * [PT] Resolve o ScreenType efetivo baseado na orientação base e na orientação atual do dispositivo.
     * Se a orientação base difere da orientação atual, LOWEST e HIGHEST são invertidos.
     *
     * @param requestedType O tipo de tela originalmente requisitado (LOWEST ou HIGHEST)
     * @param baseOrientation A orientação para a qual o design foi criado (PORTRAIT, LANDSCAPE ou AUTO)
     * @param configuration A Configuration da tela atual
     * @return O ScreenType resolvido (pode ser invertido do requestedType)
     */
    fun resolveScreenType(
        requestedType: com.appdimens.library.ScreenType,
        baseOrientation: com.appdimens.library.BaseOrientation,
        configuration: Configuration
    ): com.appdimens.library.ScreenType {
        // If AUTO, no inversion - return as requested
        if (baseOrientation == com.appdimens.library.BaseOrientation.AUTO) {
            return requestedType
        }

        // Detect current orientation
        val currentIsPortrait = configuration.screenHeightDp > configuration.screenWidthDp
        val currentIsLandscape = !currentIsPortrait

        // Determine if inversion is needed
        val shouldInvert = when (baseOrientation) {
            com.appdimens.library.BaseOrientation.PORTRAIT -> currentIsLandscape
            com.appdimens.library.BaseOrientation.LANDSCAPE -> currentIsPortrait
            com.appdimens.library.BaseOrientation.AUTO -> false
        }

        // Invert if needed
        return if (shouldInvert) {
            when (requestedType) {
                com.appdimens.library.ScreenType.LOWEST -> com.appdimens.library.ScreenType.HIGHEST
                com.appdimens.library.ScreenType.HIGHEST -> com.appdimens.library.ScreenType.LOWEST
            }
        } else {
            requestedType
        }
    }
}

/**
 * [EN] Data class to store screen adjustment factors.
 * [PT] Data class para armazenar os fatores de ajuste de tela.
 */
data class ScreenAdjustmentFactors(
    val withArFactorLowest: Float,
    val withArFactorHighest: Float,
    val withoutArFactor: Float,
    val adjustmentFactorLowest: Float,
    val adjustmentFactorHighest: Float
)