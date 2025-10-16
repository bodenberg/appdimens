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
package com.appdimens.library

/**
 * [EN] Stores the adjustment factors calculated from the screen dimensions.
 * The Aspect Ratio (AR) calculation is performed only once per screen configuration.
 *
 * [PT] Armazena os fatores de ajuste calculados a partir das dimensões da tela.
 * O cálculo do Aspect Ratio (AR) é feito apenas uma vez por configuração de tela.
 *
 * @property withArFactorLowest [EN] The final and COMPLETE scaling factor, using the LOWEST base (smallest dimension) + AR.
 *                              [PT] Fator de escala final e COMPLETO, usando a base LOWEST (menor dimensão) + AR.
 * @property withArFactorHighest [EN] The final and COMPLETE scaling factor, using the HIGHEST base (largest dimension) + AR.
 *                               [PT] Fator de escala final e COMPLETO, usando a base HIGHEST (maior dimensão) + AR.
 * @property withoutArFactor [EN] The final scaling factor WITHOUT AR (uses the LOWEST base for safety).
 *                           [PT] Fator de escala final SEM AR (Usa a base LOWEST por segurança).
 * @property adjustmentFactorLowest [EN] The base adjustment factor (increment multiplier), LOWEST: smallestWidthDp.
 *                                  [PT] Fator base de ajuste (multiplicador do incremento), LOWEST: smallestWidthDp.
 * @property adjustmentFactorHighest [EN] The base adjustment factor (increment multiplier), HIGHEST: max(W, H).
 *                                   [PT] Fator base de ajuste (multiplicador do incremento), HIGHEST: max(W, H).
 */
data class ScreenAdjustmentFactors(
    val withArFactorLowest: Float,
    val withArFactorHighest: Float,
    val withoutArFactor: Float,
    val adjustmentFactorLowest: Float,
    val adjustmentFactorHighest: Float
)