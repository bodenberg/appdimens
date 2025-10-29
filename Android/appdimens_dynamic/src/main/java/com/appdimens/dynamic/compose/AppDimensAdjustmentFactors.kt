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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenAdjustmentFactors
import kotlin.math.PI
import kotlin.math.ln

/**
 * [EN] Singleton object that provides functions for calculating and resolving
 * adjustment factors and screen qualifiers.
 *
 * [PT] Objeto singleton que fornece funções para o cálculo e resolução
 * de fatores de ajuste e qualificadores de tela.
 */
@Stable
object AppDimensAdjustmentFactors {

    /**
     * [EN] Constants for Dimension Adjustment Calculation.
     *
     * [PT] Constantes para Cálculo de Ajuste de Dimensão.
     */

    /**
     * [EN] Base Dp scaling factor. The default value is 1.0f (no adjustment).
     *
     * [PT] Fator base de escala Dp. O valor padrão é 1.0f (sem ajuste).
     */
    const val BASE_DP_FACTOR = 1.00f

    /**
     * [EN] Base reference width DP for adjustment calculation (e.g., 300dp).
     *
     * [PT] Largura DP de referência base para o cálculo de ajuste (ex: 300dp).
     */
    const val BASE_WIDTH_DP = 300f

    /**
     * [EN] Increment step size in Dp to calculate the adjustment (e.g., every 1dp).
     *
     * [PT] Tamanho do passo de incremento em Dp para calcular o ajuste (ex: a cada 1dp).
     */
    const val INCREMENT_DP_STEP = 1f

    /**
     * [EN] Factor for circumference calculation (2π). Using kotlin.math.PI.
     *
     * [PT] Fator para cálculo de circunferência (2π). Usando kotlin.math.PI.
     */
    const val CIRCUMFERENCE_FACTOR = PI * 2.0

    /**
     * [EN] Constants for CONTINUOUS Aspect Ratio CALCULATION.
     *
     * [PT] Constantes para o CÁLCULO CONTÍNUO do Aspect Ratio.
     */

    /**
     * [EN] Reference screen aspect ratio (e.g., 16:9), where the adjustment is neutral.
     *
     * [PT] Proporção de tela de referência (Ex: 16:9), onde o ajuste é neutro.
     */
    const val REFERENCE_AR = 1.78f

    /**
     * [EN] DEFAULT sensitivity coefficient: Adjusts how aggressive the scaling is on extreme screens.
     *
     * [PT] Coeficiente de sensibilidade PADRÃO: Ajusta o quão agressivo é o escalonamento em telas extremas.
     */
    const val DEFAULT_SENSITIVITY_K = 0.08f

    /**
     * [EN] Default increment factor (used in calculation WITHOUT Aspect Ratio).
     *
     * [PT] Fator de incremento padrão (usado no cálculo SEM Aspect Ratio).
     */
    const val BASE_INCREMENT = 0.10f

    /**
     * [EN] Helper function that isolates the logic for fetching and selecting the custom Dp value
     * through Qualifiers (SW, H, W). This logic should be called inside a 'remember' block for performance.
     *
     * [PT] Função auxiliar que isola a lógica de busca e seleção do valor Dp
     * customizado através dos Qualificadores (SW, H, W). Esta lógica deve ser chamada dentro de um 'remember' para ser performática.
     */
    fun resolveQualifierDp(
        customDpMap: Map<DpQualifierEntry, Dp>,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float,
        initialBaseDp: Dp
    ): Dp {
        var dpToAdjust = initialBaseDp

        /**
         * [EN] Screen Qualifier Logic (SW, H, W) - Priority: SW > H > W.
         * We filter and sort in descending order to ensure that the LARGEST qualifier
         * that meets the condition is applied (e.g., 900dp should have priority over 600dp).
         *
         * [PT] Lógica dos Qualificadores de Tela (SW, H, W) - Prioridade: SW > H > W.
         * Filtramos e ordenamos em ordem decrescente para garantir que o MAIOR qualificador
         * que atende à condição seja aplicado (ex: 900dp deve ter prioridade sobre 600dp).
         */
        val sortedQualifiers = customDpMap.entries.toList().sortedByDescending { it.key.value }

        var foundCustomDp: Dp? = null

        /**
         * [EN] Priority 1: SMALL_WIDTH (smallestScreenWidthDp)
         *
         * [PT] Prioridade 1: SMALL_WIDTH (smallestScreenWidthDp)
         */
        foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
            key.type == DpQualifier.SMALL_WIDTH && smallestWidthDp >= key.value
        }?.value

        if (foundCustomDp != null) {
            dpToAdjust = foundCustomDp
        } else {
            /**
             * [EN] Priority 2: HEIGHT (screenHeightDp)
             *
             * [PT] Prioridade 2: HEIGHT (screenHeightDp)
             */
            foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
                key.type == DpQualifier.HEIGHT && currentScreenHeightDp >= key.value
            }?.value

            if (foundCustomDp != null) {
                dpToAdjust = foundCustomDp
            } else {
                /**
                 * [EN] Priority 3: WIDTH (screenWidthDp)
                 *
                 * [PT] Prioridade 3: WIDTH (screenWidthDp)
                 */
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
     *
     * [PT] Função auxiliar que verifica se um [DpQualifierEntry] atende às dimensões atuais da tela.
     */
    fun resolveIntersectionCondition(
        entry: DpQualifierEntry,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float
    ): Boolean = when (entry.type) {
        DpQualifier.SMALL_WIDTH -> smallestWidthDp >= entry.value
        DpQualifier.HEIGHT -> currentScreenHeightDp >= entry.value
        DpQualifier.WIDTH -> currentScreenWidthDp >= entry.value
    }

    /**
     * [EN] Helper function to get the screen aspect ratio from the configuration.
     *
     * [PT] Função auxiliar para obter a proporção de tela a partir da configuração.
     */
    fun getReferenceAspectRatio(screenWidthDp: Float, screenHeightDp: Float): Float {
        /**
         * [EN] Aspect Ratio: (Largest dimension / Smallest dimension)
         *
         * [PT] Proporção: (Maior dimensão / Menor dimensão)
         */
        return if (screenWidthDp > screenHeightDp)
            screenWidthDp / screenHeightDp
        else screenHeightDp / screenWidthDp
    }

    /**
     * [EN] Composable function that calculates and remembers Basic Adjustment Factors.
     *
     * [PT] Função Composable que calcula e memoriza Fatores Básicos de Ajuste.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    fun rememberAdjustmentFactors(): ScreenAdjustmentFactors {
        val configuration = LocalConfiguration.current

        /**
         * [EN] The keys in 'remember' ensure recalculation only when the screen changes.
         *
         * [PT] As chaves no 'remember' garantem o recálculo apenas na mudança de tela.
         */
        return remember(
            configuration.screenWidthDp,
            configuration.screenHeightDp,
            configuration.smallestScreenWidthDp
        ) {
            val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
            val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
            val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
            val highestDimensionDp = maxOf(currentScreenWidthDp, currentScreenHeightDp)

            /**
             * [EN] 1A. Calculation of the Base Adjustment Factor (LOWEST - smallestWidthDp)
             *
             * [PT] 1A. Cálculo do Fator de Ajuste Base (LOWEST - smallestWidthDp)
             */
            val differenceLowest = smallestWidthDp - BASE_WIDTH_DP
            val adjustmentFactorLowest = differenceLowest / INCREMENT_DP_STEP

            /**
             * [EN] 1B. Calculation of the Base Adjustment Factor (HIGHEST - Max(W,H))
             *
             * [PT] 1B. Cálculo do Fator de Ajuste Base (HIGHEST - Max(W,H))
             */
            val differenceHighest = highestDimensionDp - BASE_WIDTH_DP
            val adjustmentFactorHighest = differenceHighest / INCREMENT_DP_STEP

            /**
             * [EN] FACTOR WITHOUT ASPECT RATIO (Uses the LOWEST factor for safety).
             *
             * [PT] FATOR SEM ASPECT RATIO (Usa o fator LOWEST por segurança).
             */
            val withoutArFactor = BASE_DP_FACTOR + adjustmentFactorLowest * BASE_INCREMENT

            /**
             * [EN] Gets the current screen aspect ratio.
             *
             * [PT] Obtém a proporção atual da tela.
             */
            val currentAr = getReferenceAspectRatio(currentScreenWidthDp, currentScreenHeightDp)

            /**
             * [EN] Continuous calculation (Logarithmic Function) to smooth the scale adjustment.
             *
             * [PT] Cálculo contínuo (Função Logarítmica) para suavizar o ajuste de escala.
             */
            val continuousAdjustment =
                (DEFAULT_SENSITIVITY_K * ln(currentAr / REFERENCE_AR)).toFloat()
            val finalIncrementValueWithAr = BASE_INCREMENT + continuousAdjustment

            /**
             * [EN] 2A. Calculation of the COMPLETE Factor (LOWEST + AR)
             *
             * [PT] 2A. Cálculo do Fator COMPLETO (LOWEST + AR)
             */
            val withArFactorLowest = BASE_DP_FACTOR + adjustmentFactorLowest * finalIncrementValueWithAr

            /**
             * [EN] 2B. Calculation of the COMPLETE Factor (HIGHEST + AR)
             *
             * [PT] 2B. Cálculo do Fator COMPLETO (HIGHEST + AR)
             */
            val withArFactorHighest = BASE_DP_FACTOR + adjustmentFactorHighest * finalIncrementValueWithAr

            /**
             * [EN] Returns all factors, memorizing both types of base adjustment.
             *
             * [PT] Retorna todos os fatores, memorizando os dois tipos de ajuste de base.
             */
            ScreenAdjustmentFactors(
                withArFactorLowest = withArFactorLowest,
                withArFactorHighest = withArFactorHighest,
                withoutArFactor = withoutArFactor,
                adjustmentFactorLowest = adjustmentFactorLowest,
                adjustmentFactorHighest = adjustmentFactorHighest
            )
        }
    }
}