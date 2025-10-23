/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
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

import Foundation
import UIKit

/**
 * [EN] Class for building dynamic dimensions that allow base value customization
 * via screen qualifiers. The final value is scaled by the screen.
 * Compatible with both UIKit and SwiftUI.
 * [PT] Classe para construir dimensões dinâmicas que permitem customização do valor base
 * via qualificadores de tela. O valor final é escalado pela tela.
 * Compatível com UIKit e SwiftUI.
 */
public class AppDimensDynamic {
    
    
    // MARK: - Properties
    
    private let initialBaseValue: CGFloat
    private var ignoreMultiWindowAdjustment: Bool = false
    private var screenType: ScreenType = .lowest
    
    /// [EN] Map to store custom values based on device type (Priority 3).
    /// [PT] Mapa para armazenar valores customizados com base no tipo de dispositivo (Prioridade 3).
    private var customDeviceTypeMap: [DeviceType: CGFloat] = [:]
    
    /// [EN] Map to store custom values based on screen qualifier (Priority 4).
    /// Lazy initialization for better performance.
    /// [PT] Mapa para armazenar valores customizados com base no qualificador de tela (Prioridade 4).
    /// Inicialização lazy para melhor performance.
    private lazy var customScreenQualifierMap: [ScreenQualifierEntry: CGFloat] = [:]
    
    /// [EN] Map for custom values by UiModeType (Priority 2).
    /// Lazy initialization for better performance.
    /// [PT] Mapa para valores customizados por UiModeType (Prioridade 2).
    /// Inicialização lazy para melhor performance.
    private lazy var customUiModeMap: [UiModeType: CGFloat] = [:]
    
    /// [EN] Map for custom values by INTERSECTION (UiMode + DpQualifier) (Priority 1).
    /// Lazy initialization for better performance.
    /// [PT] Mapa para valores customizados por INTERSEÇÃO (UiMode + DpQualifier) (Prioridade 1).
    /// Inicialização lazy para melhor performance.
    private lazy var customIntersectionMap: [UiModeQualifierEntry: CGFloat] = [:]
    
    // MARK: - Cache System
    
    /// [EN] Automatic cache system based on Compose's remember mechanism.
    /// [PT] Sistema de cache automático baseado no mecanismo remember do Compose.
    private let autoCache = AppDimensAutoCache()
    
    /// [EN] Cached sorted intersection qualifiers for performance.
    /// [PT] Qualificadores de interseção ordenados em cache para performance.
    private var cachedSortedIntersectionQualifiers: [(UiModeQualifierEntry, CGFloat)]? = nil
    
    /// [EN] Cached screen dimensions for performance.
    /// [PT] Dimensões da tela em cache para performance.
    private var cachedScreenDimensions: (width: CGFloat, height: CGFloat)? = nil
    private var lastConfigurationHash: Int = 0
    
    /// [EN] Individual cache control for this instance.
    /// [PT] Controle individual de cache para esta instância.
    private var enableCache: Bool = true
    
    // MARK: - Initialization
    
    /**
     * [EN] Initializes the AppDimensDynamic with a base value.
     * [PT] Inicializa o AppDimensDynamic com um valor base.
     */
    public init(_ initialValue: CGFloat, ignoreMultiWindowAdjustment: Bool = false) {
        self.initialBaseValue = initialValue
        self.ignoreMultiWindowAdjustment = ignoreMultiWindowAdjustment
    }
    
    // MARK: - Configuration Methods
    
    /**
     * [EN] Sets a custom dimension value for a specific UI mode.
     * @param type The UI mode type.
     * @param customValue The custom dimension value in points.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Define um valor de dimensão customizado para um modo de UI específico.
     * @param type O tipo de modo de UI.
     * @param customValue O valor de dimensão customizado em pontos.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    @discardableResult
    public func screen(_ type: UiModeType, _ customValue: CGFloat) -> AppDimensDynamic {
        customUiModeMap[type] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets a custom dimension for the intersection of a UI mode and a screen qualifier.
     * @param uiModeType The UI mode type.
     * @param qualifierType The qualifier type.
     * @param qualifierValue The qualifier value (e.g., 600 for sw600dp).
     * @param customValue The custom dimension value in points.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Define uma dimensão customizada para a interseção de um modo de UI e um qualificador de tela.
     * @param uiModeType O tipo de modo de UI.
     * @param qualifierType O tipo de qualificador.
     * @param qualifierValue O valor do qualificador (ex: 600 para sw600dp).
     * @param customValue O valor de dimensão customizado em pontos.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    @discardableResult
    public func screen(_ uiModeType: UiModeType, _ qualifierType: DpQualifier, _ qualifierValue: Int, _ customValue: CGFloat) -> AppDimensDynamic {
        let key = UiModeQualifierEntry(
            uiModeType: uiModeType,
            dpQualifierEntry: DpQualifierEntry(type: qualifierType, value: qualifierValue)
        )
        customIntersectionMap[key] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets a custom dimension value for a specific screen qualifier.
     * @param type The qualifier type.
     * @param value The qualifier value (e.g., 600 for sw600dp).
     * @param customValue The custom dimension value in points.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Define um valor de dimensão customizado para um qualificador de tela específico.
     * @param type O tipo de qualificador.
     * @param value O valor do qualificador (ex: 600 para sw600dp).
     * @param customValue O valor da dimensão customizada em pontos.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    @discardableResult
    public func screen(_ type: DpQualifier, _ value: Int, _ customValue: CGFloat) -> AppDimensDynamic {
        let entry = DpQualifierEntry(type: type, value: value)
        customScreenQualifierMap[entry] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets a custom dimension value for a specific device type.
     * @param type The device type.
     * @param customValue The custom dimension value in points.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Define um valor de dimensão customizado para um tipo de dispositivo específico.
     * @param type O tipo de dispositivo.
     * @param customValue O valor de dimensão customizado em pontos.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    @discardableResult
    public func screen(_ type: DeviceType, _ customValue: CGFloat) -> AppDimensDynamic {
        customDeviceTypeMap[type] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets a custom dimension value for a specific device type and screen size.
     * @param deviceType The device type.
     * @param screenSize The minimum screen size for this qualifier.
     * @param customValue The custom dimension value in points.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Define um valor de dimensão customizado para um tipo de dispositivo e tamanho de tela específicos.
     * @param deviceType O tipo de dispositivo.
     * @param screenSize O tamanho mínimo de tela para este qualificador.
     * @param customValue O valor de dimensão customizado em pontos.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    @discardableResult
    public func screen(_ deviceType: DeviceType, _ screenSize: CGFloat, _ customValue: CGFloat) -> AppDimensDynamic {
        let entry = ScreenQualifierEntry(deviceType: deviceType, screenSize: screenSize)
        customScreenQualifierMap[entry] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets the screen dimension type (LOWEST or HIGHEST) to be used as the base for adjustments.
     * @param type The screen dimension type.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Define o tipo de dimensão da tela (LOWEST ou HIGHEST) a ser usado como base para os ajustes.
     * @param type O tipo de dimensão da tela.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    @discardableResult
    public func type(_ type: ScreenType) -> AppDimensDynamic {
        screenType = type
        return self
    }
    
    /**
     * [EN] Ignores adjustments when the app is in multi-window mode.
     * @param ignore If true, adjustments are ignored in multi-window mode.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Ignora os ajustes quando o aplicativo está em modo multi-janela.
     * @param ignore Se verdadeiro, os ajustes são ignorados no modo multi-janela.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    @discardableResult
    public func multiWindowAdjustment(ignore: Bool = true) -> AppDimensDynamic {
        ignoreMultiWindowAdjustment = ignore
        return self
    }
    
    /**
     * [EN] Enables or disables cache for this instance.
     * @param enable If true, enables cache; if false, disables cache.
     * @return The AppDimensDynamic instance for chaining.
     * [PT] Ativa ou desativa o cache para esta instância.
     * @param enable Se verdadeiro, ativa o cache; se falso, desativa o cache.
     * @return A instância AppDimensDynamic para encadeamento.
     */
    @discardableResult
    public func cache(enable: Bool = true) -> AppDimensDynamic {
        enableCache = enable
        if !enable {
            clearInstanceCache()
        }
        return self
    }
    
    // MARK: - Value Resolution
    
    /**
     * [EN] Resolves the base value to be adjusted by applying the customization logic
     * (Intersection > UiMode > Device Type > Screen Qualifier > Initial Value).
     * [PT] Resolve o valor base a ser ajustado, aplicando a lógica de customização
     * (Interseção > UiMode > Tipo de Dispositivo > Qualificador de Tela > Valor Inicial).
     */
    private func resolveFinalBaseValue() -> CGFloat {
        // Get cached screen dimensions for performance
        let (screenWidth, screenHeight) = getCachedScreenDimensions()
        let currentScreenSize = min(screenWidth, screenHeight)
        let currentUiModeType = UiModeType.current()
        let currentDeviceType = DeviceType.current()

        // Use automatic cache system with dependency tracking
        let dependencies: Set<AnyHashable> = [
            screenWidth, screenHeight, currentUiModeType, currentDeviceType,
            customIntersectionMap.hashValue, customUiModeMap.hashValue, 
            customDeviceTypeMap.hashValue, customScreenQualifierMap.hashValue,
            initialBaseValue, screenType, ignoreMultiWindowAdjustment
        ]
        
        return if AppDimensGlobal.globalCacheEnabled && enableCache {
            autoCache.remember(
                key: "resolveFinalBaseValue_\(self.hashValue)",
                dependencies: dependencies
            ) {
                performBaseValueCalculation(
                    screenWidth: screenWidth,
                    screenHeight: screenHeight,
                    currentScreenSize: currentScreenSize,
                    currentUiModeType: currentUiModeType,
                    currentDeviceType: currentDeviceType
                )
            }
        } else {
            performBaseValueCalculation(
                screenWidth: screenWidth,
                screenHeight: screenHeight,
                currentScreenSize: currentScreenSize,
                currentUiModeType: currentUiModeType,
                currentDeviceType: currentDeviceType
            )
        }
    }
    
    /**
     * [EN] Gets cached screen dimensions for performance optimization.
     * [PT] Obtém dimensões da tela em cache para otimização de performance.
     */
    private func getCachedScreenDimensions() -> (width: CGFloat, height: CGFloat) {
        let configHash = UIScreen.main.bounds.hashValue
        if cachedScreenDimensions == nil || lastConfigurationHash != configHash {
            cachedScreenDimensions = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
            lastConfigurationHash = configHash
        }
        return cachedScreenDimensions!
    }
    
    /**
     * [EN] Performs the actual base value calculation with cached sorting.
     * [PT] Executa o cálculo real do valor base com ordenação em cache.
     */
    private func performBaseValueCalculation(
        screenWidth: CGFloat,
        screenHeight: CGFloat,
        currentScreenSize: CGFloat,
        currentUiModeType: UiModeType,
        currentDeviceType: DeviceType
    ) -> CGFloat {
        
        var valueToAdjust = initialBaseValue
        
        // Priority 1: Intersection (UiMode + DpQualifier) - Use cached sorting
        let sortedIntersectionQualifiers = getCachedSortedIntersectionQualifiers()
        
        for (key, value) in sortedIntersectionQualifiers {
            if key.uiModeType == currentUiModeType && 
               AppDimensAdjustmentFactors.resolveIntersectionCondition(
                   entry: key.dpQualifierEntry,
                   smallestWidth: currentScreenSize,
                   currentScreenWidth: screenWidth,
                   currentScreenHeight: screenHeight
               ) {
                valueToAdjust = value
                break
            }
        }
        
        if valueToAdjust == initialBaseValue {
            // Priority 2: UI Mode (UiModeType only)
            if let uiModeValue = customUiModeMap[currentUiModeType] {
                valueToAdjust = uiModeValue
            } else {
                // Priority 3: Device Type
                if let deviceTypeValue = customDeviceTypeMap[currentDeviceType] {
                    valueToAdjust = deviceTypeValue
                } else {
                    // Priority 4: Screen Qualifier
                    valueToAdjust = AppDimensAdjustmentFactors.resolveQualifierValue(
                        customMap: customScreenQualifierMap,
                        currentDeviceType: currentDeviceType,
                        currentScreenSize: currentScreenSize,
                        initialValue: initialBaseValue
                    )
                }
            }
        }
        
        return valueToAdjust
    }
    
    /**
     * [EN] Gets cached sorted intersection qualifiers for performance.
     * [PT] Obtém qualificadores de interseção ordenados em cache para performance.
     */
    private func getCachedSortedIntersectionQualifiers() -> [(UiModeQualifierEntry, CGFloat)] {
        if cachedSortedIntersectionQualifiers == nil {
            cachedSortedIntersectionQualifiers = customIntersectionMap.sorted { 
                $0.key.dpQualifierEntry.value > $1.key.dpQualifierEntry.value 
            }
        }
        return cachedSortedIntersectionQualifiers!
    }
    
    // MARK: - Cache Management
    
    /**
     * [EN] Invalidates cached data when maps change.
     * Called automatically when dependencies change.
     * [PT] Invalida dados em cache quando os maps mudam.
     * Chamado automaticamente quando dependências mudam.
     */
    private func invalidateCachedData() {
        cachedSortedIntersectionQualifiers = nil
        cachedScreenDimensions = nil
        lastConfigurationHash = 0
    }
    
    /// [EN] Clears the cache for this specific instance.
    /// Called by the global cache management system.
    /// [PT] Limpa o cache para esta instância específica.
    /// Chamado pelo sistema de gerenciamento global de cache.
    internal func clearInstanceCache() {
        invalidateCachedData()
        // Clear any instance-specific cache entries from the global auto cache
        AppDimensAutoCache.shared.clearByPattern("_\(self.hashValue)")
    }
    
    /**
     * [EN] Performs the final dynamic dimension calculation.
     * @return The dynamically adjusted value as a CGFloat (not converted to pixels).
     * [PT] Executa o cálculo final da dimensão dinâmica.
     * @return O valor ajustado dinamicamente como CGFloat (não convertido para pixels).
     */
    public func calculateAdjustedValue() -> CGFloat {
        let (screenWidth, screenHeight) = getCachedScreenDimensions()
        let currentUiModeType = UiModeType.current()
        let currentDeviceType = DeviceType.current()
        
        // Use automatic cache system with dependency tracking
        let dependencies: Set<AnyHashable> = [
            screenWidth, screenHeight, currentUiModeType, currentDeviceType,
            customIntersectionMap.hashValue, customUiModeMap.hashValue, 
            customDeviceTypeMap.hashValue, customScreenQualifierMap.hashValue,
            initialBaseValue, screenType, ignoreMultiWindowAdjustment
        ]
        
        return if AppDimensGlobal.globalCacheEnabled && enableCache {
            autoCache.remember(
                key: "calculateAdjustedValue_\(self.hashValue)",
                dependencies: dependencies
            ) {
                performFinalCalculation(
                    screenWidth: screenWidth,
                    screenHeight: screenHeight
                )
            }
        } else {
            performFinalCalculation(
                screenWidth: screenWidth,
                screenHeight: screenHeight
            )
        }
    }
    
    /**
     * [EN] Performs the final calculation with all optimizations.
     * [PT] Executa o cálculo final com todas as otimizações.
     */
    private func performFinalCalculation(
        screenWidth: CGFloat,
        screenHeight: CGFloat
    ) -> CGFloat {
        let valueToAdjust = resolveFinalBaseValue()
        
        let shouldIgnoreAdjustment = ignoreMultiWindowAdjustment && AppDimensAdjustmentFactors.isMultiWindowMode()
        
        let finalValue: CGFloat
        if shouldIgnoreAdjustment {
            // Returns the base value without dynamic scaling
            finalValue = valueToAdjust
        } else {
            // The dynamic scaling percentage is: (Adjusted Base Value / Reference Value)
            let percentage = valueToAdjust / AppDimensAdjustmentFactors.BASE_WIDTH_PT
            
            // Screen dimension to use (LOWEST or HIGHEST)
            let dimensionToUse = screenType == .highest ? 
                max(screenWidth, screenHeight) : 
                min(screenWidth, screenHeight)
            
            // The final value is the percentage applied to the screen dimension
            finalValue = dimensionToUse * percentage
        }
        
        return finalValue
    }
    
    // MARK: - Conversion Methods
    
    /**
     * [EN] Returns the dynamically adjusted value in points.
     * @return The value in points as a CGFloat.
     * [PT] Retorna o valor ajustado dinamicamente em pontos.
     * @return O valor em pontos como CGFloat.
     */
    public func toPoints() -> CGFloat {
        return calculateAdjustedValue()
    }
    
    /**
     * [EN] Returns the dynamically adjusted value in pixels.
     * @return The value in pixels as a CGFloat.
     * [PT] Retorna o valor ajustado dinamicamente em pixels.
     * @return O valor em pixels como CGFloat.
     */
    public func toPixels() -> CGFloat {
        let adjustedValue = calculateAdjustedValue()
        return AppDimensAdjustmentFactors.pointsToPixels(adjustedValue)
    }
    
    /**
     * [EN] Returns the dynamically adjusted value as an integer in points.
     * @return The value in points as an Int.
     * [PT] Retorna o valor ajustado dinamicamente como inteiro em pontos.
     * @return O valor em pontos como Int.
     */
    public func toPointsInt() -> Int {
        return Int(calculateAdjustedValue())
    }
    
    /**
     * [EN] Returns the dynamically adjusted value as an integer in pixels.
     * @return The value in pixels as an Int.
     * [PT] Retorna o valor ajustado dinamicamente como inteiro em pixels.
     * @return O valor em pixels como Int.
     */
    public func toPixelsInt() -> Int {
        return Int(toPixels())
    }
    
    /**
     * [EN] Returns the dynamically adjusted value in scalable pixels (sp) for text.
     * This respects the system font scale setting.
     * Compatible with Android API.
     * @return The value in sp as a CGFloat.
     * [PT] Retorna o valor ajustado dinamicamente em pixels escaláveis (sp) para texto.
     * Isso respeita a configuração de escala de fonte do sistema.
     * Compatível com API Android.
     * @return O valor em sp como CGFloat.
     */
    public func toSp() -> CGFloat {
        let adjustedValue = calculateAdjustedValue()
        let fontScale = AppDimensAdjustmentFactors.getFontScale()
        return adjustedValue * fontScale
    }
    
    /**
     * [EN] Returns the dynamically adjusted value as an integer in scalable pixels (sp).
     * Compatible with Android API.
     * @return The value in sp as an Int.
     * [PT] Retorna o valor ajustado dinamicamente como inteiro em pixels escaláveis (sp).
     * Compatível com API Android.
     * @return O valor em sp como Int.
     */
    public func toSpInt() -> Int {
        return Int(toSp())
    }
    
    /**
     * [EN] Returns the dynamically adjusted value in em units (ignoring font scale).
     * This is similar to sp but without the font scale factor applied.
     * Compatible with Android API.
     * @return The value in em as a CGFloat.
     * [PT] Retorna o valor ajustado dinamicamente em unidades em (ignorando escala de fonte).
     * Isso é similar ao sp mas sem o fator de escala de fonte aplicado.
     * Compatível com API Android.
     * @return O valor em em como CGFloat.
     */
    public func toEm() -> CGFloat {
        return calculateAdjustedValue()
    }
    
    /**
     * [EN] Returns the dynamically adjusted value as an integer in em units.
     * Compatible with Android API.
     * @return The value in em as an Int.
     * [PT] Retorna o valor ajustado dinamicamente como inteiro em unidades em.
     * Compatível com API Android.
     * @return O valor em em como Int.
     */
    public func toEmInt() -> Int {
        return Int(toEm())
    }
}
