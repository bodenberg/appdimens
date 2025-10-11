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
 * Representa uma entrada de qualificador customizado, combinando o tipo e o valor mínimo de DP
 * para que o ajuste customizado seja aplicado.
 *
 * @param type O tipo de dimensão (SMALL_WIDTH, HEIGHT, WIDTH).
 * @param value A dimensão mínima em DP para ativar este qualificador (ex: 600).
 */
data class DpQualifierEntry(
    val type: DpQualifier,
    val value: Int
)