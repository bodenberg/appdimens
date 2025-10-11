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
 * Objeto singleton que fornece funções para o cálculo e resolução
 * de fatores de ajuste e qualificadores de tela.
 */
@Stable
object AppDimensAdjustmentFactors {

    // --- Constantes para Cálculo de Ajuste de Dimensão ---
    /** Fator base de escala Dp. O valor padrão é 1.0f (sem ajuste). */
    const val BASE_DP_FACTOR = 1.00f

    /** Largura DP de referência base para o cálculo de ajuste (ex: 300dp). */
    const val BASE_WIDTH_DP = 300f

    /** Tamanho do passo de incremento em Dp para calcular o ajuste (ex: a cada 30dp). */
    const val INCREMENT_DP_STEP = 30f

    /** Fator para cálculo de circunferência ($2\pi$). Usando kotlin.math.PI. */
    const val CIRCUMFERENCE_FACTOR = PI * 2.0

    // --- Constantes para o CÁLCULO CONTÍNUO do Aspect Ratio ---
    /** Proporção de tela de referência (Ex: 16:9), onde o ajuste é neutro. */
    const val REFERENCE_AR = 1.78f

    /** Coeficiente de sensibilidade PADRÃO: Ajusta o quão agressivo é o escalonamento em telas extremas. */
    const val DEFAULT_SENSITIVITY_K = 0.08f

    /** Fator de incremento padrão (usado no cálculo SEM Aspect Ratio). */
    const val BASE_INCREMENT = 0.10f


    /**
     * Função auxiliar que isola a lógica de busca e seleção do valor Dp
     * customizado através dos Qualificadores (SW, H, W).
     * Esta lógica deve ser chamada dentro de um 'remember' para ser performática.
     */
    fun resolveQualifierDp(
        customDpMap: Map<DpQualifierEntry, Dp>,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float,
        initialBaseDp: Dp
    ): Dp {
        var dpToAdjust = initialBaseDp

        // 1. Lógica dos Qualificadores de Tela (SW, H, W) - Prioridade: SW > H > W
        // Filtramos e ordenamos em ordem decrescente para garantir que o MAIOR qualificador
        // que atende à condição seja aplicado (ex: 900dp deve ter prioridade sobre 600dp).
        val sortedQualifiers = customDpMap.entries.toList().sortedByDescending { it.key.value }

        var foundCustomDp: Dp? = null

        // Prioridade 1: SMALL_WIDTH (smallestScreenWidthDp)
        foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
            key.type == DpQualifier.SMALL_WIDTH && smallestWidthDp >= key.value
        }?.value

        if (foundCustomDp != null) {
            dpToAdjust = foundCustomDp
        } else {
            // Prioridade 2: HEIGHT (screenHeightDp)
            foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
                key.type == DpQualifier.HEIGHT && currentScreenHeightDp >= key.value
            }?.value

            if (foundCustomDp != null) {
                dpToAdjust = foundCustomDp
            } else {
                // Prioridade 3: WIDTH (screenWidthDp)
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
     * Função auxiliar que verifica se um [DpQualifierEntry] atende às dimensões atuais da tela.
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
     * Função auxiliar para obter a proporção de tela a partir da configuração.
     */
    fun getReferenceAspectRatio(screenWidthDp: Float, screenHeightDp: Float): Float {
        // Proporção: (Maior dimensão / Menor dimensão)
        return if (screenWidthDp > screenHeightDp)
            screenWidthDp / screenHeightDp
        else screenHeightDp / screenWidthDp
    }

    /**
     * Objeto Composável que calcula e memoriza Fatores Básicos de Ajuste.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    fun rememberAdjustmentFactors(): ScreenAdjustmentFactors {
        val configuration = LocalConfiguration.current

        // As keys no 'remember' garantem o recálculo apenas na mudança de tela
        return remember(
            configuration.screenWidthDp,
            configuration.screenHeightDp,
            configuration.smallestScreenWidthDp
        ) {
            val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
            val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
            val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
            val highestDimensionDp = maxOf(currentScreenWidthDp, currentScreenHeightDp)

            // 1A. Cálculo do Fator de Ajuste Base (LOWEST - smallestWidthDp)
            val differenceLowest = smallestWidthDp - BASE_WIDTH_DP
            val adjustmentFactorLowest = differenceLowest / INCREMENT_DP_STEP

            // 1B. Cálculo do Fator de Ajuste Base (HIGHEST - Max(W,H))
            val differenceHighest = highestDimensionDp - BASE_WIDTH_DP
            val adjustmentFactorHighest = differenceHighest / INCREMENT_DP_STEP

            // --- FATOR SEM ASPECT RATIO (Usa o fator LOWEST por segurança) ---
            val withoutArFactor = BASE_DP_FACTOR + adjustmentFactorLowest * BASE_INCREMENT

            // Obtém a proporção atual da tela
            val currentAr = getReferenceAspectRatio(currentScreenWidthDp, currentScreenHeightDp)

            // Cálculo contínuo (Função Logarítmica) para suavizar o ajuste de escala
            val continuousAdjustment =
                (DEFAULT_SENSITIVITY_K * ln(currentAr / REFERENCE_AR)).toFloat()
            val finalIncrementValueWithAr = BASE_INCREMENT + continuousAdjustment

            // 2A. Cálculo do Fator COMPLETO (LOWEST + AR)
            val withArFactorLowest = BASE_DP_FACTOR + adjustmentFactorLowest * finalIncrementValueWithAr

            // 2B. Cálculo do Fator COMPLETO (HIGHEST + AR)
            val withArFactorHighest = BASE_DP_FACTOR + adjustmentFactorHighest * finalIncrementValueWithAr

            // Retorna todos os fatores, memorizando os dois tipos de ajuste de base
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