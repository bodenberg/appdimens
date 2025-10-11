package com.appdimens.dynamic.code

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.TypedValue
import com.appdimens.library.ScreenType
import kotlin.math.floor

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
 * Objeto singleton que fornece funções para gerenciamento de dimensões responsivas
 * em Views Android/Kotlin, agindo como um gateway para os construtores Fixed e Dynamic.
 */
object AppDimens {

    // --- Funções Auxiliares para Dimensões "Fixas" Ajustáveis (Gateway) ---

    /** Inicia o construtor `AppDimensFixed` a partir de um valor Float em Dp. */
    fun fixed(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean = false): AppDimensFixed =
        AppDimensFixed(initialValueDp, ignoreMultiViewAdjustment)

    /** Inicia o construtor `AppDimensFixed` a partir de um Int em Dp. */
    fun fixed(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean = false): AppDimensFixed =
        AppDimensFixed(initialValueInt.toFloat(), ignoreMultiViewAdjustment)


    // --- Funções Auxiliares para Dimensões Dinâmicas (Gateway) ---

    /** Inicia o construtor `AppDimensDynamic` a partir de um valor Float em Dp. */
    fun dynamic(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean = false): AppDimensDynamic =
        AppDimensDynamic(initialValueDp, ignoreMultiViewAdjustment)

    /** Inicia o construtor `AppDimensDynamic` a partir de um Int em Dp. */
    fun dynamic(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean = false): AppDimensDynamic =
        AppDimensDynamic(initialValueInt.toFloat(), ignoreMultiViewAdjustment)

    // --- Funções para Dimensões Dinâmicas (Baseadas em Porcentagem) ---

    /**
     * Calcula um valor de dimensão dinâmico com base em uma porcentagem (0.0 a 1.0) da dimensão da tela.
     * Retorna o valor em Dp (Float).
     *
     * @param percentage A porcentagem (0.0f a 1.0f).
     * @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
     * @param resources Os Resources do Context.
     * @return O valor ajustado em Dp (Float).
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun dynamicPercentageDp(
        percentage: Float,
        type: ScreenType = ScreenType.LOWEST,
        resources: Resources
    ): Float {
        require(percentage in 0.0f..1.0f) { "Percentage must be between 0.0f and 1.0f" }

        val configuration = resources.configuration
        val screenWidthDp = configuration.screenWidthDp.toFloat()
        val screenHeightDp = configuration.screenHeightDp.toFloat()

        val dimensionToUse = when (type) {
            ScreenType.HIGHEST -> maxOf(screenWidthDp, screenHeightDp)
            ScreenType.LOWEST -> minOf(screenWidthDp, screenHeightDp)
        }

        return dimensionToUse * percentage
    }

    /**
     * Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para Pixels (PX).
     *
     * @param percentage A porcentagem (0.0f a 1.0f).
     * @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
     * @param resources Os Resources do Context.
     * @return O valor ajustado em Pixels (PX) como Float.
     */
    fun dynamicPercentagePx(
        percentage: Float,
        type: ScreenType = ScreenType.LOWEST,
        resources: Resources
    ): Float {
        val dpValue = dynamicPercentageDp(percentage, type, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.displayMetrics
        )
    }

    /**
     * Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para Scaleable Pixels (SP) em PX.
     *
     * @param percentage A porcentagem (0.0f a 1.0f).
     * @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
     * @param resources Os Resources do Context.
     * @return O valor ajustado em Scaleable Pixels (SP) em Pixels (PX) como Float.
     */
    fun dynamicPercentageSp(
        percentage: Float,
        type: ScreenType = ScreenType.LOWEST,
        resources: Resources
    ): Float {
        val dpValue = dynamicPercentageDp(percentage, type, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dpValue, resources.displayMetrics
        )
    }

    // --- Utilitários de Layout ---

    /**
     * Calcula o número máximo de itens que cabem em um contêiner, dado o tamanho do contêiner em PX.
     *
     * @param containerSizePx O tamanho (largura ou altura) do contêiner em Pixels (PX).
     * @param itemSizeDp O tamanho (largura ou altura) de um item em Dp.
     * @param itemMarginDp O padding total (em Dp) em torno de cada item.
     * @param resources Os Resources do Context, para conversão Dp -> Px.
     * @return A contagem de itens calculada (Int).
     */
    fun calculateAvailableItemCount(
        containerSizePx: Int,
        itemSizeDp: Float,
        itemMarginDp: Float,
        resources: Resources
    ): Int {
        // Converte o tamanho do item e o padding para Pixels (PX)
        val itemSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemSizeDp, resources.displayMetrics)
        val itemMarginPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemMarginDp, resources.displayMetrics)

        val totalItemSizePx = itemSizePx + (itemMarginPx * 2)

        return if (totalItemSizePx > 0f)
            floor(containerSizePx / totalItemSizePx).toInt()
        else 0
    }
}