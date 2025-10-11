package com.appdimens.library

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
 * Armazena os fatores de ajuste calculados a partir das dimensões da tela.
 * O cálculo do Aspect Ratio (AR) é feito apenas uma vez por configuração de tela.
 *
 * @param withArFactor Fator de escala final, incluindo o ajuste fino do Aspect Ratio (usando a sensibilidade padrão).
 * @param withoutArFactor Fator de escala final, usando o incremento base de 0.10f (SEM AR).
 * @param adjustmentFactor Fator base de ajuste (calculado a partir da diferença da smallestWidthDp / INCREMENT_DP_STEP).
 */
data class ScreenAdjustmentFactors(
    // Fator final COMPLETO, usando a base LOWEST (menor dimensão) + AR
    val withArFactorLowest: Float,
// Fator final COMPLETO, usando a base HIGHEST (maior dimensão) + AR
    val withArFactorHighest: Float,
// Fator final SEM AR (Usa a base LOWEST por segurança)
    val withoutArFactor: Float,
// Fator base (multiplicador do incremento), LOWEST: smallestWidthDp
    val adjustmentFactorLowest: Float,
// Fator base (multiplicador do incremento), HIGHEST: max(W, H)
    val adjustmentFactorHighest: Float
)