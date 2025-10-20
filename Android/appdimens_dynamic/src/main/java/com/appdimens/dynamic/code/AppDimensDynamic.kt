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
import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeQualifierEntry
import com.appdimens.library.UiModeType

/**
 * [EN] Class for building dynamic dimensions that allow base Dp customization
 * via screen qualifiers (`.screen()`). The final value is scaled by the screen.
 * Compatible with the View System (XML layouts).
 *
 * [PT] Classe para construir dimensões dinâmicas que permitem customização do DP base
 * via qualificadores de tela (`.screen()`). O valor final é escalado pela tela.
 * Compatível com View System (XML layouts).
 */
class AppDimensDynamic(
    private val initialBaseDp: Float,
    private var ignoreMultiViewAdjustment: Boolean = false
) {
    
    /**
     * [EN] Map to store custom Dp values (Priority 3).
     * Lazy initialization for better performance.
     * [PT] Mapa para armazenar valores Dp customizados (Prioridade 3).
     * Inicialização lazy para melhor performance.
     */
    private val customDpMap: MutableMap<DpQualifierEntry, Float> by lazy { mutableMapOf() }

    /**
     * [EN] Map for custom Dp values by UiModeType (Priority 2).
     * Lazy initialization for better performance.
     * [PT] Mapa para valores Dp customizados por UiModeType (Prioridade 2).
     * Inicialização lazy para melhor performance.
     */
    private val customUiModeMap: MutableMap<UiModeType, Float> by lazy { mutableMapOf() }

    /**
     * [EN] Map for custom Dp values by INTERSECTION (UiMode + DpQualifier) (Priority 1).
     * Lazy initialization for better performance.
     * [PT] Mapa para valores Dp customizados por INTERSEÇÃO (UiMode + DpQualifier) (Prioridade 1).
     * Inicialização lazy para melhor performance.
     */
    private val customIntersectionMap: MutableMap<UiModeQualifierEntry, Float> by lazy { mutableMapOf() }

    /**
     * [EN] Defines the screen dimension to be used as a base (HIGHEST or LOWEST).
     * [PT] Define a dimensão da tela a ser usada como base (HIGHEST ou LOWEST).
     */
    private var screenType: ScreenType = ScreenType.LOWEST
    
    // MARK: - Cache System
    
    /**
     * [EN] Automatic cache system based on Compose's remember mechanism.
     * [PT] Sistema de cache automático baseado no mecanismo remember do Compose.
     */
    private val autoCache = AppDimensAutoCache
    
    /**
     * [EN] Cached sorted intersection qualifiers for performance.
     * [PT] Qualificadores de interseção ordenados em cache para performance.
     */
    private var cachedSortedIntersectionQualifiers: List<Map.Entry<UiModeQualifierEntry, Float>>? = null
    
    /**
     * [EN] Cached screen dimensions for performance.
     * [PT] Dimensões da tela em cache para performance.
     */
    private var cachedScreenDimensions: Triple<Float, Float, Float>? = null
    private var lastConfigurationHash: Int = 0
    
    /**
     * [EN] Individual cache control for this instance.
     * [PT] Controle individual de cache para esta instância.
     */
    private var enableCache: Boolean = true

    /**
     * [EN] Sets a custom dimension value for a specific UI mode.
     * @param type The UI mode (`UiModeType`).
     * @param customValue The custom dimension value in Dp.
     * @return The `AppDimensDynamic` instance for chaining.
     *
     * [PT] Define um valor de dimensão customizado para um modo de UI específico.
     * @param type O modo de UI (`UiModeType`).
     * @param customValue O valor de dimensão customizado em Dp.
     * @return A instância `AppDimensDynamic` para encadeamento.
     */
    fun screen(type: UiModeType, customValue: Float): AppDimensDynamic {
        customUiModeMap[type] = customValue
        // Invalidate cached data when maps change
        invalidateCachedData()
        return this
    }

    fun screen(type: UiModeType, customValue: Int): AppDimensDynamic {
        customUiModeMap[type] = customValue.toFloat()
        // Invalidate cached data when maps change
        invalidateCachedData()
        return this
    }

    /**
     * [EN] Sets a custom dimension for the intersection of a UI mode and a screen qualifier.
     * @param uiModeType The UI mode (`UiModeType`).
     * @param qualifierType The qualifier type (`DpQualifier`).
     * @param qualifierValue The qualifier value (e.g., 600 for sw600dp).
     * @param customValue The custom dimension value in Dp.
     * @return The `AppDimensDynamic` instance for chaining.
     *
     * [PT] Define uma dimensão customizada para a interseção de um modo de UI e um qualificador de tela.
     * @param uiModeType O modo de UI (`UiModeType`).
     * @param qualifierType O tipo de qualificador (`DpQualifier`).
     * @param qualifierValue O valor do qualificador (ex: 600 para sw600dp).
     * @param customValue O valor de dimensão customizado em Dp.
     * @return A instância `AppDimensDynamic` para encadeamento.
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Float
    ): AppDimensDynamic {
        val key = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue)
        )
        customIntersectionMap[key] = customValue
        // Invalidate cached data when maps change
        invalidateCachedData()
        return this
    }

    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Int
    ): AppDimensDynamic {
        val key = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue)
        )
        customIntersectionMap[key] = customValue.toFloat()
        // Invalidate cached data when maps change
        invalidateCachedData()
        return this
    }

    /**
     * [EN] Sets a custom dimension value for a specific screen qualifier.
     * @param type The qualifier type (`DpQualifier`).
     * @param value The qualifier value (e.g., 600 for sw600dp).
     * @param customValue The custom dimension value in Dp.
     * @return The `AppDimensDynamic` instance for chaining.
     *
     * [PT] Define um valor de dimensão customizado para um qualificador de tela específico.
     * @param type O tipo de qualificador (`DpQualifier`).
     * @param value O valor do qualificador (ex: 600 para sw600dp).
     * @param customValue O valor da dimensão customizada em Dp.
     * @return A instância `AppDimensDynamic` para encadeamento.
     */
    fun screen(type: DpQualifier, value: Int, customValue: Int): AppDimensDynamic {
        customDpMap[DpQualifierEntry(type, value)] = customValue.toFloat()
        // Invalidate cached data when maps change
        invalidateCachedData()
        return this
    }

    fun screen(type: DpQualifier, value: Int, customValue: Float): AppDimensDynamic {
        customDpMap[DpQualifierEntry(type, value)] = customValue
        // Invalidate cached data when maps change
        invalidateCachedData()
        return this
    }

    /**
     * [EN] Sets the screen dimension type (LOWEST or HIGHEST) to be used as the base for adjustments.
     * @param type The screen dimension type.
     * @return The `AppDimensDynamic` instance for chaining.
     *
     * [PT] Define o tipo de dimensão da tela (LOWEST ou HIGHEST) a ser usado como base para os ajustes.
     * @param type O tipo de dimensão da tela.
     * @return A instância `AppDimensDynamic` para encadeamento.
     */
    fun type(type: ScreenType): AppDimensDynamic {
        screenType = type
        return this
    }

    /**
     * [EN] Ignores adjustments when the app is in multi-window mode.
     * @param ignore If true, adjustments are ignored in multi-window mode.
     * @return The `AppDimensDynamic` instance for chaining.
     *
     * [PT] Ignora os ajustes quando o aplicativo está em modo multi-janela.
     * @param ignore Se verdadeiro, os ajustes são ignorados no modo multi-janela.
     * @return A instância `AppDimensDynamic` para encadeamento.
     */
    fun multiViewAdjustment(ignore: Boolean = true): AppDimensDynamic {
        ignoreMultiViewAdjustment = ignore
        return this
    }
    
    /**
     * [EN] Enables or disables cache for this instance.
     * @param enable If true, enables cache; if false, disables cache.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Ativa ou desativa o cache para esta instância.
     * @param enable Se verdadeiro, ativa o cache; se falso, desativa o cache.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    fun cache(enable: Boolean = true): AppDimensDynamic {
        enableCache = enable
        if (!enable) {
            clearInstanceCache()
        }
        return this
    }

    /**
     * [EN] Resolves the base Dp value to be adjusted by applying the customization logic
     * (Intersection > UiMode > DpQualifier).
     *
     * @param configuration The current screen configuration.
     * @return The base Dp value (unadjusted for the screen) to be used in the calculation.
     *
     * [PT] Resolve o valor Dp base a ser ajustado, aplicando a lógica de customização
     * (Interseção > UiMode > DpQualifier).
     *
     * @param configuration A configuração da tela atual.
     * @return O valor Dp base (não ajustado pela tela) a ser usado no cálculo.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    private fun resolveFinalBaseDp(configuration: Configuration): Float {
        // Get cached screen dimensions for performance
        val (smallestWidthDp, currentScreenWidthDp, currentScreenHeightDp) = getCachedScreenDimensions(configuration)
        val currentUiModeType = UiModeType.fromConfiguration(configuration.uiMode)

        // Use automatic cache system with dependency tracking
        val dependencies = setOf(
            configuration,
            customIntersectionMap,
            customUiModeMap,
            customDpMap,
            initialBaseDp,
            screenType,
            ignoreMultiViewAdjustment
        )
        
        return if (AppDimens.globalCacheEnabled && enableCache) {
            autoCache.remember("resolveFinalBaseDp_${this.hashCode()}", dependencies) {
                performBaseDpCalculation(smallestWidthDp, currentScreenWidthDp, currentScreenHeightDp, currentUiModeType)
            }
        } else {
            performBaseDpCalculation(smallestWidthDp, currentScreenWidthDp, currentScreenHeightDp, currentUiModeType)
        }
    }
    
    /**
     * [EN] Gets cached screen dimensions for performance optimization.
     * [PT] Obtém dimensões da tela em cache para otimização de performance.
     */
    private fun getCachedScreenDimensions(configuration: Configuration): Triple<Float, Float, Float> {
        val configHash = configuration.hashCode()
        if (cachedScreenDimensions == null || lastConfigurationHash != configHash) {
            cachedScreenDimensions = Triple(
                configuration.smallestScreenWidthDp.toFloat(),
                configuration.screenWidthDp.toFloat(),
                configuration.screenHeightDp.toFloat()
            )
            lastConfigurationHash = configHash
        }
        return cachedScreenDimensions!!
    }
    
    /**
     * [EN] Performs the actual base Dp calculation with cached sorting.
     * [PT] Executa o cálculo real do Dp base com ordenação em cache.
     */
    private fun performBaseDpCalculation(
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float,
        currentUiModeType: UiModeType
    ): Float {

        var dpToAdjust = initialBaseDp
        var foundCustomDp: Float?

        // Priority 1: Intersection (UiMode + DpQualifier) - Use cached sorting
        val sortedIntersectionQualifiers = getCachedSortedIntersectionQualifiers()

        foundCustomDp = sortedIntersectionQualifiers.firstOrNull { (key, _) ->
            key.uiModeType == currentUiModeType && AppDimensAdjustmentFactors.resolveIntersectionCondition(
                entry = key.dpQualifierEntry,
                smallestWidthDp = smallestWidthDp,
                currentScreenWidthDp = currentScreenWidthDp,
                currentScreenHeightDp = currentScreenHeightDp
            )
        }?.value

        if (foundCustomDp != null) {
            dpToAdjust = foundCustomDp
        } else {
            // Priority 2: UI Mode (UiModeType only)
            foundCustomDp = customUiModeMap[currentUiModeType]

            if (foundCustomDp != null) {
                dpToAdjust = foundCustomDp
            } else {
                // Priority 3: Dp Qualifier (SW, H, W only)
                dpToAdjust = resolveQualifierDpFloat(
                    customDpMap = customDpMap,
                    smallestWidthDp = smallestWidthDp,
                    currentScreenWidthDp = currentScreenWidthDp,
                    currentScreenHeightDp = currentScreenHeightDp,
                    initialBaseDp = initialBaseDp
                )
            }
        }
        
        return dpToAdjust
    }
    
    /**
     * [EN] Gets cached sorted intersection qualifiers for performance.
     * [PT] Obtém qualificadores de interseção ordenados em cache para performance.
     */
    private fun getCachedSortedIntersectionQualifiers(): List<Map.Entry<UiModeQualifierEntry, Float>> {
        if (cachedSortedIntersectionQualifiers == null) {
            cachedSortedIntersectionQualifiers = customIntersectionMap.entries.toList()
                .sortedByDescending { it.key.dpQualifierEntry.value }
        }
        return cachedSortedIntersectionQualifiers!!
    }
    
    // MARK: - Cache Management
    
    /**
     * [EN] Invalidates cached data when maps change.
     * Called automatically when dependencies change.
     * [PT] Invalida dados em cache quando os maps mudam.
     * Chamado automaticamente quando dependências mudam.
     */
    private fun invalidateCachedData() {
        cachedSortedIntersectionQualifiers = null
        cachedScreenDimensions = null
        lastConfigurationHash = 0
    }
    
    /**
     * [EN] Clears the cache for this specific instance.
     * Called by the global cache management system.
     * [PT] Limpa o cache para esta instância específica.
     * Chamado pelo sistema de gerenciamento global de cache.
     */
    internal fun clearInstanceCache() {
        invalidateCachedData()
        // Clear any instance-specific cache entries from the global auto cache
        val instanceHash = this.hashCode().toString()
        autoCache.clearByPattern(instanceHash)
    }

    /**
     * [EN] Auxiliary version of `resolveQualifierDp` that works with Float instead of Dp.
     * [PT] Versão auxiliar do `resolveQualifierDp` que trabalha com Float ao invés de Dp.
     */
    private fun resolveQualifierDpFloat(
        customDpMap: Map<DpQualifierEntry, Float>,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float,
        initialBaseDp: Float
    ): Float {
        val sortedQualifiers = customDpMap.entries.toList()
            .sortedByDescending { it.key.value }

        val foundCustomDp = sortedQualifiers.firstOrNull { (entry, _) ->
            AppDimensAdjustmentFactors.resolveIntersectionCondition(
                entry = entry,
                smallestWidthDp = smallestWidthDp,
                currentScreenWidthDp = currentScreenWidthDp,
                currentScreenHeightDp = currentScreenHeightDp
            )
        }?.value

        return foundCustomDp ?: initialBaseDp
    }

    /**
     * [EN] Performs the final dynamic dimension calculation.
     *
     * @param configuration The current screen configuration.
     * @return The dynamically adjusted Dp value as a **Float** (not converted to PX/SP).
     *
     * [PT] Executa o cálculo final da dimensão dinâmica.
     *
     * @param configuration A Configuration da tela atual.
     * @return O valor **Float** em Dp ajustado dinamicamente (não convertido para PX/SP).
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun calculateAdjustedValue(configuration: Configuration): Float {
        // Use automatic cache system with dependency tracking
        val dependencies = setOf(
            configuration,
            customIntersectionMap,
            customUiModeMap,
            customDpMap,
            initialBaseDp,
            screenType,
            ignoreMultiViewAdjustment
        )
        
        return if (AppDimens.globalCacheEnabled && enableCache) {
            autoCache.remember("calculateAdjustedValue_${this.hashCode()}", dependencies) {
                performFinalCalculation(configuration)
            }
        } else {
            performFinalCalculation(configuration)
        }
    }
    
    /**
     * [EN] Performs the final calculation with all optimizations.
     * [PT] Executa o cálculo final com todas as otimizações.
     */
    private fun performFinalCalculation(configuration: Configuration): Float {
        val dpToAdjust = resolveFinalBaseDp(configuration)

        var isMultiWindow = false

        if (ignoreMultiViewAdjustment) {
            val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
            val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
            val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != Configuration.SCREENLAYOUT_SIZE_MASK
            val isSmallDifference = (smallestWidthDp - currentScreenWidthDp) < (smallestWidthDp * 0.1)
            isMultiWindow = isLayoutSplit && !isSmallDifference
        }

        val shouldIgnoreAdjustment = ignoreMultiViewAdjustment && isMultiWindow

        val finalValue = if (shouldIgnoreAdjustment) {
            // Returns the base Dp without dynamic scaling
            dpToAdjust
        } else {
            // The dynamic scaling percentage is: (Adjusted Base DP / Reference DP)
            val percentage = dpToAdjust / AppDimensAdjustmentFactors.BASE_WIDTH_DP

            // Screen dimension to use (LOWEST or HIGHEST)
            val dimensionToUse = when (screenType) {
                ScreenType.HIGHEST -> maxOf(
                    configuration.screenWidthDp.toFloat(),
                    configuration.screenHeightDp.toFloat()
                )
                ScreenType.LOWEST -> minOf(
                    configuration.screenWidthDp.toFloat(),
                    configuration.screenHeightDp.toFloat()
                )
            }

            // The final value is the percentage applied to the screen dimension
            dimensionToUse * percentage
        }
        
        return finalValue
    }

    /**
     * [EN] Builds the dynamically adjusted Dp value and converts it to Pixels (Float).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) as a Float.
     *
     * [PT] Constrói o valor Dp ajustado dinamicamente e o converte para Pixels (Float).
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) como Float.
     */
    fun toPx(resources: Resources): Float {
        val adjustedDpValue = calculateAdjustedValue(resources.configuration)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, adjustedDpValue, resources.displayMetrics
        )
    }

    /**
     * [EN] Builds the dynamically adjusted Dp value and converts it to Pixels (Int).
     * Useful for setters that only accept Int.
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) as an Int.
     *
     * [PT] Constrói o valor Dp ajustado dinamicamente e o converte para Pixels (Int).
     * Útil para setters que aceitam apenas Int.
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) como Int.
     */
    fun toPxInt(resources: Resources): Int {
        return toPx(resources).toInt()
    }

    /**
     * [EN] Builds the dynamically adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float),
     * ignoring the system's font scale ('em' unit).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) corresponding to 'em', as a Float.
     *
     * [PT] Constrói o valor Dp ajustado dinamicamente e o converte para Scaleable Pixels (SP) em Pixels (Float),
     * ignorando a escala de fonte do sistema (unidade 'em').
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) correspondente ao 'em', como Float.
     */
    fun toEm(resources: Resources): Float {
        val adjustedDpValue = calculateAdjustedValue(resources.configuration)
        val fontScale = resources.configuration.fontScale
        val dpValueWithoutFontScale = adjustedDpValue / fontScale
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dpValueWithoutFontScale, resources.displayMetrics
        )
    }

    /**
     * [EN] Builds the dynamically adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Int),
     * ignoring the system's font scale ('em' unit).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) corresponding to 'em', as an Int.
     *
     * [PT] Constrói o valor Dp ajustado dinamicamente e o converte para Scaleable Pixels (SP) em Pixels (Int),
     * ignorando a escala de fonte do sistema (unidade 'em').
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) correspondente ao 'em', como Int.
     */
    fun toEmInt(resources: Resources): Int {
        return toEm(resources).toInt()
    }

    /**
     * [EN] Builds the dynamically adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) corresponding to SP, as a Float.
     *
     * [PT] Constrói o valor Dp ajustado dinamicamente e o converte para Scaleable Pixels (SP) em Pixels (Float).
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) correspondente ao SP, como Float.
     */
    fun toSp(resources: Resources): Float {
        val adjustedDpValue = calculateAdjustedValue(resources.configuration)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, adjustedDpValue, resources.displayMetrics
        )
    }

    /**
     * [EN] Builds the dynamically adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Int).
     *
     * @param resources The Context's Resources.
     * @return The value in Pixels (PX) corresponding to SP, as an Int.
     *
     * [PT] Constrói o valor Dp ajustado dinamicamente e o converte para Scaleable Pixels (SP) em Pixels (Int).
     *
     * @param resources Os Resources do Context.
     * @return O valor em Pixels (PX) correspondente ao SP, como Int.
     */
    fun toSpInt(resources: Resources): Int {
        return toSp(resources).toInt()
    }

    /**
     * [EN] Returns the dynamically adjusted Dp value (in Dp, not converted to PX).
     *
     * @param resources The Context's Resources.
     * @return The value in Dp as a Float.
     *
     * [PT] Retorna o valor Dp ajustado dinamicamente (em Dp, não convertido para PX).
     *
     * @param resources Os Resources do Context.
     * @return O valor em Dp como Float.
     */
    fun toDp(resources: Resources): Float {
        return calculateAdjustedValue(resources.configuration)
    }

    /**
     * [EN] Returns the dynamically adjusted Dp value (in Dp, not converted to PX) as an Int.
     *
     * @param resources The Context's Resources.
     * @return The value in Dp as an Int.
     *
     * [PT] Retorna o valor Dp ajustado dinamicamente (em Dp, não convertido para PX) como Int.
     *
     * @param resources Os Resources do Context.
     * @return O valor em Dp como Int.
     */
    fun toDpInt(resources: Resources): Int {
        return calculateAdjustedValue(resources.configuration).toInt()
    }
}