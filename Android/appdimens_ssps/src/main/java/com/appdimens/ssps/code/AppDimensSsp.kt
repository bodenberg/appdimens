/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Sp, Sp, and Px values in a responsive and mathematically refined way,
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
import com.appdimens.library.DpQualifier

/**
* [EN]
* Utility object for handling SSP (Scalable Sp) dimensions.
*
* [PT]
* Objeto utilitário para manipulação de dimensões SSP (Scalable Sp).
*/
object AppDimensSsp {
    private const val MIN_VALUE =
        0 // [EN] Minimum allowed SSP value. / [PT] Valor mínimo permitido para SSP.
    private const val MAX_VALUE =
        600 // [EN] Maximum allowed SSP value. / [PT] Valor máximo permitido para SSP.
    private const val DIMEN_TYPE =
        "dimen" // [EN] The resource type for dimensions. / [PT] O tipo de recurso para dimensões.

    /**
     * [EN]
     * Gets the dimension in pixels from an SSP value.
     *
     * [PT]
     * Obtém a dimensão em pixels a partir de um valor SSP.
     *
     * @param context The application context.
     * @param dpQualifier DpQualifier.
     * @param value The SSP value (0 to 600).
     * @return The dimension in pixels, or 0f if not found.
     */
    fun getDimensionInPx(context: Context, dpQualifier: DpQualifier, value: Int): Float {
        if (value == 0) return 0f
        val resourceId = getResourceId(context, dpQualifier, value)
        return if (resourceId != 0) {
            context.resources.getDimension(resourceId)
        } else 0f
    }

    /**
     * [EN]
     * Gets the resource ID for an SSP value.
     *
     * [PT]
     * Obtém o ID do recurso para um valor SSP.
     *
     * @param context The application context.
     * @param dpQualifier DpQualifier.
     * @param value The SSP value (0 to 600).
     * @return The resource ID, or 0 if not found.
     */
    fun getResourceId(context: Context, dpQualifier: DpQualifier, value: Int): Int {
        if (value == 0) return 0

        val safeValue = value.coerceIn(MIN_VALUE, MAX_VALUE)
        val sspSuffix = when (dpQualifier) {
            DpQualifier.SMALL_WIDTH -> "ssp"
            DpQualifier.HEIGHT -> "hsp"
            DpQualifier.WIDTH -> "wsp"
        }
        val dimenName = buildResourceName(safeValue, sspSuffix)

        return context.resources.getIdentifier(dimenName, DIMEN_TYPE, context.packageName)
    }

    /**
     * [EN]
     * Builds the resource name for a given SSP value.
     * For positive values, it uses a "_" prefix (e.g., _10ssp).
     *
     * [PT]
     * Constrói o nome do recurso para um determinado valor SSP.
     * Para valores positivos, usa o prefixo "_" (ex: _10ssp).
     *
     * @param value The integer value.
     * @return The formatted resource name.
     */
    private fun buildResourceName(value: Int, sspSuffix: String): String =
        "_${value}$sspSuffix"
}
