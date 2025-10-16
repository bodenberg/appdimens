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
package com.appdimens.ssps.code

import android.content.Context

/**
 * [EN]
 * Object responsible for handling SSP (Scalable Screen Pixels) dimensions.
 *
 * [PT]
 * Objeto responsável por manipular dimensões SSP (Scalable Screen Pixels).
 */
object AppDimensSsp {
    private const val MIN_VALUE = 0 // [EN] The minimum allowed value for SSP dimensions. / [PT] O valor mínimo permitido para dimensões SSP.
    private const val MAX_VALUE = 600 // [EN] The maximum allowed value for SSP dimensions. / [PT] O valor máximo permitido para dimensões SSP.
    private const val DIMEN_PREFIX = "_" // [EN] The prefix for the dimension resource name. / [PT] O prefixo para o nome do recurso de dimensão.
    private const val DIMEN_SUFFIX = "ssp" // [EN] The suffix for the SSP dimension resource name. / [PT] O sufixo para o nome do recurso de dimensão SSP.
    private const val DIMEN_TYPE = "dimen" // [EN] The resource type for dimensions. / [PT] O tipo de recurso para dimensões.

    /**
     * [EN]
     * Normalizes the input value within the allowed limits.
     *
     * [PT]
     * Normaliza o valor de entrada dentro dos limites permitidos.
     *
     * @param value The original integer value.
     * @return The value clamped within [MIN_VALUE] and [MAX_VALUE].
     */
    private fun getNormalizedValue(value: Int): Int = when {
        value <= MIN_VALUE -> MIN_VALUE
        value > MAX_VALUE -> MAX_VALUE
        else -> value
    }

    /**
     * [EN]
     * Creates the dimension resource name based on the integer value.
     *
     * [PT]
     * Cria o nome do recurso de dimensão com base no valor inteiro.
     *
     * @param value The integer value of the dimension.
     * @return The formatted resource name (e.g., "_16ssp").
     */
    private fun createDimenName(value: Int): String =
        "$DIMEN_PREFIX${value}$DIMEN_SUFFIX"

    /**
     * [EN]
     * Gets the resource ID for a given dimension name.
     *
     * [PT]
     * Obtém o ID do recurso para um determinado nome de dimensão.
     *
     * @param context The application context.
     * @param dimenName The name of the dimension resource.
     * @return The resource ID, or 0 if not found.
     */
    private fun getResourceId(context: Context, dimenName: String): Int =
        context.resources.getIdentifier(dimenName, DIMEN_TYPE, context.packageName)

    /**
     * [EN]
     * Returns the dimension in pixels.
     *
     * [PT]
     * Retorna a dimensão em pixels.
     *
     * @param context The application context.
     * @param value The desired dimension value.
     * @return The value in pixels, or 0f if not found.
     */
    fun getDimensionInPx(context: Context, value: Int): Float {
        val safeValue = getNormalizedValue(value)
        val dimenName = createDimenName(safeValue)
        val resId = getResourceId(context, dimenName)

        return if (resId > 0) {
            context.resources.getDimension(resId)
        } else {
            0f
        }
    }

    /**
     * [EN]
     * Returns the dimension resource ID.
     *
     * [PT]
     * Retorna o ID do recurso de dimensão.
     *
     * @param context The application context.
     * @param value The desired dimension value.
     * @return The resource ID, or 0 if not found.
     */
    fun getResourceId(context: Context, value: Int): Int {
        val safeValue = getNormalizedValue(value)
        val dimenName = createDimenName(safeValue)
        return getResourceId(context, dimenName)
    }
}
