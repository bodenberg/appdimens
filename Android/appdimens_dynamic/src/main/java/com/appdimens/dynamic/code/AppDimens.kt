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
package com.appdimens.dynamic.code

import android.annotation.SuppressLint
import android.content.res.Resources
import android.util.TypedValue
import com.appdimens.library.ScreenType
import kotlin.math.floor

/**
 * [EN] A singleton object that provides functions for responsive dimension management,
 * acting as a gateway to the `AppDimensFixed` and `AppDimensDynamic` constructors.
 *
 * [PT] Objeto singleton que fornece funções para gerenciamento de dimensões responsivas,
 * agindo como um gateway para os construtores `AppDimensFixed` e `AppDimensDynamic`.
 */
object AppDimens {

    // MARK: - Global Configuration Properties
    
    /**
     * [EN] Global aspect ratio adjustment setting.
     * [PT] Configuração global de ajuste de proporção.
     */
    private var globalAspectRatioEnabled: Boolean = true
    
    /**
     * [EN] Global cache control for all AppDimensDynamic instances.
     * [PT] Controle global de cache para todas as instâncias AppDimensDynamic.
     */
    @JvmStatic
    var globalCacheEnabled: Boolean = true
        set(value) {
            field = value
            if (!value) {
                // Clear all caches when globally disabled
                clearAllCaches()
            }
        }
    
    /**
     * [EN] Registry of all AppDimensDynamic instances for global cache management.
     * [PT] Registro de todas as instâncias AppDimensDynamic para gerenciamento global de cache.
     */
    private val dynamicInstances = mutableSetOf<AppDimensDynamic>()
    
    /**
     * [EN] Registers an AppDimensDynamic instance for global cache management.
     * [PT] Registra uma instância AppDimensDynamic para gerenciamento global de cache.
     */
    @JvmStatic
    internal fun registerDynamicInstance(instance: AppDimensDynamic) {
        dynamicInstances.add(instance)
    }
    
    /**
     * [EN] Unregisters an AppDimensDynamic instance from global cache management.
     * [PT] Remove o registro de uma instância AppDimensDynamic do gerenciamento global de cache.
     */
    @JvmStatic
    internal fun unregisterDynamicInstance(instance: AppDimensDynamic) {
        dynamicInstances.remove(instance)
    }
    
    /**
     * [EN] Clears all caches from all instances.
     * [PT] Limpa todos os caches de todas as instâncias.
     */
    @JvmStatic
    fun clearAllCaches() {
        // Clear the global auto cache
        AppDimensAutoCache.clearAll()
        
        // Clear individual instance caches
        dynamicInstances.forEach { instance ->
            instance.clearInstanceCache()
        }
    }
    
    /**
     * [EN] Global multi-view adjustment setting.
     * [PT] Configuração global de ajuste multi-view.
     */
    private var globalIgnoreMultiViewAdjustment: Boolean = false

    // MARK: - Global Configuration Methods
    
    /**
     * [EN] Sets the global aspect ratio adjustment setting.
     * @param enabled If true, enables aspect ratio adjustment globally.
     * @return The AppDimens instance for chaining.
     * [PT] Define a configuração global de ajuste de proporção.
     * @param enabled Se verdadeiro, ativa o ajuste de proporção globalmente.
     * @return A instância AppDimens para encadeamento.
     */
    fun setGlobalAspectRatio(enabled: Boolean): AppDimens {
        globalAspectRatioEnabled = enabled
        return this
    }
    
    /**
     * [EN] Sets the global multi-view adjustment setting.
     * @param ignore If true, ignores multi-view adjustments globally.
     * @return The AppDimens instance for chaining.
     * [PT] Define a configuração global de ajuste multi-view.
     * @param ignore Se verdadeiro, ignora os ajustes multi-view globalmente.
     * @return A instância AppDimens para encadeamento.
     */
    fun setGlobalIgnoreMultiViewAdjustment(ignore: Boolean): AppDimens {
        globalIgnoreMultiViewAdjustment = ignore
        return this
    }
    
    /**
     * [EN] Gets the current global aspect ratio setting.
     * @return True if aspect ratio adjustment is enabled globally.
     * [PT] Obtém a configuração global atual de proporção.
     * @return True se o ajuste de proporção está ativado globalmente.
     */
    fun isGlobalAspectRatioEnabled(): Boolean = globalAspectRatioEnabled
    
    /**
     * [EN] Gets the current global multi-view adjustment setting.
     * @return True if multi-view adjustments are ignored globally.
     * [PT] Obtém a configuração global atual de ajuste multi-view.
     * @return True se os ajustes multi-view são ignorados globalmente.
     */
    fun isGlobalIgnoreMultiViewAdjustment(): Boolean = globalIgnoreMultiViewAdjustment
    
    /**
     * [EN] Sets the global cache control setting.
     * @param enabled If true, enables global cache; if false, disables and clears all caches.
     * @return The AppDimens instance for chaining.
     * [PT] Define a configuração global de controle de cache.
     * @param enabled Se verdadeiro, ativa o cache global; se falso, desativa e limpa todos os caches.
     * @return A instância AppDimens para encadeamento.
     */
    @JvmStatic
    fun setGlobalCache(enabled: Boolean): AppDimens {
        globalCacheEnabled = enabled
        return this
    }
    
    /**
     * [EN] Gets the current global cache setting.
     * @return True if global cache is enabled.
     * [PT] Obtém a configuração global atual de cache.
     * @return True se o cache global está ativado.
     */
    @JvmStatic
    fun isGlobalCacheEnabled(): Boolean = globalCacheEnabled

    /**
     * [EN] Gateway for `AppDimensFixed`.
     *
     * [PT] Gateway para `AppDimensFixed`.
     */

    /**
     * [EN] Initializes the `AppDimensFixed` constructor from a Float value in Dp.
     * @param initialValueDp The initial value in Dp (Float).
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     *
     * [PT] Inicia o construtor `AppDimensFixed` a partir de um valor Float em Dp.
     * @param initialValueDp O valor inicial em Dp (Float).
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     */
    @JvmStatic
    fun fixed(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean? = null): AppDimensFixed {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return AppDimensFixed(initialValueDp, finalIgnoreMultiView)
            .apply { 
                if (!globalAspectRatioEnabled) {
                    aspectRatio(false)
                }
            }
    }

    /**
     * [EN] Initializes the `AppDimensFixed` constructor from an Int value in Dp.
     * @param initialValueInt The initial value in Dp (Int).
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     *
     * [PT] Inicia o construtor `AppDimensFixed` a partir de um valor Int em Dp.
     * @param initialValueInt O valor inicial em Dp (Int).
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     */
    @JvmStatic
    fun fixed(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean? = null): AppDimensFixed {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        return AppDimensFixed(initialValueInt.toFloat(), finalIgnoreMultiView)
            .apply { 
                if (!globalAspectRatioEnabled) {
                    aspectRatio(false)
                }
            }
    }


    /**
     * [EN] Gateway for `AppDimensDynamic`.
     *
     * [PT] Gateway para `AppDimensDynamic`.
     */

    /**
     * [EN] Initializes the `AppDimensDynamic` constructor from a Float value in Dp.
     * @param initialValueDp The initial value in Dp (Float).
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     *
     * [PT] Inicia o construtor `AppDimensDynamic` a partir de um valor Float em Dp.
     * @param initialValueDp O valor inicial em Dp (Float).
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     */
    @JvmStatic
    fun dynamic(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean? = null): AppDimensDynamic {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        val instance = AppDimensDynamic(initialValueDp, finalIgnoreMultiView)
        registerDynamicInstance(instance)
        return instance
    }

    /**
     * [EN] Initializes the `AppDimensDynamic` constructor from an Int value in Dp.
     * @param initialValueInt The initial value in Dp (Int).
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     *
     * [PT] Inicia o construtor `AppDimensDynamic` a partir de um valor Int em Dp.
     * @param initialValueInt O valor inicial em Dp (Int).
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     */
    @JvmStatic
    fun dynamic(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean? = null): AppDimensDynamic {
        val finalIgnoreMultiView = ignoreMultiViewAdjustment ?: globalIgnoreMultiViewAdjustment
        val instance = AppDimensDynamic(initialValueInt.toFloat(), finalIgnoreMultiView)
        registerDynamicInstance(instance)
        return instance
    }
    
    // MARK: - Concise Aliases (matching Web and Flutter API)
    
    /**
     * [EN] Alias for [fixed]. Concise syntax matching Web and Flutter API.
     *
     * @param initialValueDp The initial value in Dp (Float).
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     * @return A new [AppDimensFixed] instance.
     *
     * [PT] Alias para [fixed]. Sintaxe concisa compatível com API Web e Flutter.
     *
     * @param initialValueDp O valor inicial em Dp (Float).
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     * @return Uma nova instância de [AppDimensFixed].
     */
    @JvmStatic
    fun fx(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean? = null): AppDimensFixed =
        fixed(initialValueDp, ignoreMultiViewAdjustment)
    
    /**
     * [EN] Alias for [fixed]. Concise syntax matching Web and Flutter API.
     *
     * @param initialValueInt The initial value in Dp (Int).
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     * @return A new [AppDimensFixed] instance.
     *
     * [PT] Alias para [fixed]. Sintaxe concisa compatível com API Web e Flutter.
     *
     * @param initialValueInt O valor inicial em Dp (Int).
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     * @return Uma nova instância de [AppDimensFixed].
     */
    @JvmStatic
    fun fx(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean? = null): AppDimensFixed =
        fixed(initialValueInt, ignoreMultiViewAdjustment)
    
    /**
     * [EN] Alias for [dynamic]. Concise syntax matching Web and Flutter API.
     *
     * @param initialValueDp The initial value in Dp (Float).
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     * @return A new [AppDimensDynamic] instance.
     *
     * [PT] Alias para [dynamic]. Sintaxe concisa compatível com API Web e Flutter.
     *
     * @param initialValueDp O valor inicial em Dp (Float).
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     * @return Uma nova instância de [AppDimensDynamic].
     */
    @JvmStatic
    fun dy(initialValueDp: Float, ignoreMultiViewAdjustment: Boolean? = null): AppDimensDynamic =
        dynamic(initialValueDp, ignoreMultiViewAdjustment)
    
    /**
     * [EN] Alias for [dynamic]. Concise syntax matching Web and Flutter API.
     *
     * @param initialValueInt The initial value in Dp (Int).
     * @param ignoreMultiViewAdjustment If true, ignores multi-view adjustments (overrides global setting).
     * @return A new [AppDimensDynamic] instance.
     *
     * [PT] Alias para [dynamic]. Sintaxe concisa compatível com API Web e Flutter.
     *
     * @param initialValueInt O valor inicial em Dp (Int).
     * @param ignoreMultiViewAdjustment Se verdadeiro, ignora os ajustes de multi-view (sobrescreve configuração global).
     * @return Uma nova instância de [AppDimensDynamic].
     */
    @JvmStatic
    fun dy(initialValueInt: Int, ignoreMultiViewAdjustment: Boolean? = null): AppDimensDynamic =
        dynamic(initialValueInt, ignoreMultiViewAdjustment)

    /**
     * [EN] Dynamic Dimension Functions (Percentage-Based).
     *
     * [PT] Funções para Dimensões Dinâmicas (Baseadas em Porcentagem).
     */

    /**
     * [EN] Calculates a dynamic dimension value based on a percentage (0.0 to 1.0) of the screen dimension.
     * Returns the value in Dp (Float).
     *
     * @param percentage The percentage (0.0f to 1.0f).
     * @param type The screen dimension to use (LOWEST/HIGHEST).
     * @param resources The Context's Resources.
     * @return The adjusted value in Dp (Float).
     *
     * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem (0.0 a 1.0) da dimensão da tela.
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
     * [EN] Calculates a dynamic dimension value based on a percentage and converts it to Pixels (PX).
     *
     * @param percentage The percentage (0.0f to 1.0f).
     * @param type The screen dimension to use (LOWEST/HIGHEST).
     * @param resources The Context's Resources.
     * @return The adjusted value in Pixels (PX) as a Float.
     *
     * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para Pixels (PX).
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
     * [EN] Calculates a dynamic dimension value based on a percentage and converts it to Scalable Pixels (SP) in PX.
     *
     * @param percentage The percentage (0.0f to 1.0f).
     * @param type The screen dimension to use (LOWEST/HIGHEST).
     * @param resources The Context's Resources.
     * @return The adjusted value in Scalable Pixels (SP) in Pixels (PX) as a Float.
     *
     * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para Scaleable Pixels (SP) em PX.
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

    /**
     * [EN] Layout Utilities.
     *
     * [PT] Utilitários de Layout.
     */

    /**
     * [EN] Calculates the maximum number of items that can fit in a container, given the container size in PX.
     *
     * @param containerSizePx The size (width or height) of the container in Pixels (PX).
     * @param itemSizeDp The size (width or height) of an item in Dp.
     * @param itemMarginDp The total margin (in Dp) around each item.
     * @param resources The Context's Resources, for Dp -> Px conversion.
     * @return The calculated item count (Int).
     *
     * [PT] Calcula o número máximo de itens que cabem em um contêiner, dado o tamanho do contêiner em PX.
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
        val itemSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemSizeDp, resources.displayMetrics)
        val itemMarginPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, itemMarginDp, resources.displayMetrics)

        val totalItemSizePx = itemSizePx + (itemMarginPx * 2)

        return if (totalItemSizePx > 0f)
            floor(containerSizePx / totalItemSizePx).toInt()
        else 0
    }
}