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
 * [EN] Represents a qualifier entry that combines a UI Mode type (device)
 * AND a screen qualifier (SW, H, W). This combination has the HIGHEST PRIORITY.
 *
 * [PT] Representa uma entrada de qualificador que combina um tipo de UI Mode (dispositivo)
 * E um qualificador de tela (SW, H, W). Esta combinação tem a PRIORIDADE MÁXIMA.
 *
 * @property uiModeType [EN] The UI Mode type (CAR, TELEVISION, NORMAL, etc.).
 *                      [PT] O tipo de UI Mode (CAR, TELEVISION, NORMAL, etc.).
 * @property dpQualifierEntry [EN] The screen qualifier (DpQualifier, minimum DP value).
 *                          [PT] O qualificador de tela (DpQualifier, valor DP mínimo).
 */
data class UiModeQualifierEntry(
    val uiModeType: UiModeType,
    val dpQualifierEntry: DpQualifierEntry
)