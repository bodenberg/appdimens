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
package com.appdimens.sdps.code

import android.content.Context

/**
 * [EN]
 * Utility object for handling SDP (Scalable Dp) dimensions.
 *
 * [PT]
 * Objeto utilitário para manipulação de dimensões SDP (Scalable Dp).
 */
object AppDimensSdp {
    private const val MIN_VALUE = -330 // [EN] Minimum allowed SDP value. / [PT] Valor mínimo permitido para SDP.
    private const val MAX_VALUE = 600 // [EN] Maximum allowed SDP value. / [PT] Valor máximo permitido para SDP.
    private const val DIMEN_TYPE = "dimen" // [EN] The resource type for dimensions. / [PT] O tipo de recurso para dimensões.
    private const val SDP_SUFFIX = "sdp" // [EN] The suffix for SDP resource names. / [PT] O sufixo para nomes de recursos SDP.

    /**
     * [EN]
     * Gets the dimension in pixels from an SDP value.
     *
     * [PT]
     * Obtém a dimensão em pixels a partir de um valor SDP.
     *
     * @param context The application context.
     * @param value The SDP value (-330 to 600).
     * @return The dimension in pixels, or 0f if not found.
     */
    fun getDimensionInPx(context: Context, value: Int): Float {
        if (value == 0) return 0f
        val resourceId = getResourceId(context, value)
        return if (resourceId != 0) {
            context.resources.getDimension(resourceId)
        } else 0f
    }

    /**
     * [EN]
     * Gets the resource ID for an SDP value.
     *
     * [PT]
     * Obtém o ID do recurso para um valor SDP.
     *
     * @param context The application context.
     * @param value The SDP value (-330 to 600).
     * @return The resource ID, or 0 if not found.
     */
    fun getResourceId(context: Context, value: Int): Int {
        if (value == 0) return 0

        val safeValue = value.coerceIn(MIN_VALUE, MAX_VALUE)
        val dimenName = buildResourceName(safeValue)

        return context.resources.getIdentifier(dimenName, DIMEN_TYPE, context.packageName)
    }

    /**
     * [EN]
     * Builds the resource name for a given SDP value.
     * For negative values, it uses the "_minus" prefix (e.g., _minus10sdp).
     * For positive values, it uses a "_" prefix (e.g., _10sdp).
     *
     * [PT]
     * Constrói o nome do recurso para um determinado valor SDP.
     * Para valores negativos, usa o prefixo "_minus" (ex: _minus10sdp).
     * Para valores positivos, usa o prefixo "_" (ex: _10sdp).
     *
     * @param value The integer value.
     * @return The formatted resource name.
     */
    private fun buildResourceName(value: Int): String = when {
        value < 0 -> "_minus${kotlin.math.abs(value)}$SDP_SUFFIX"
        else -> "_${value}$SDP_SUFFIX"
    }
}
