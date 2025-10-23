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
 * [EN] Class for building "fixed" dimensions that are automatically adjusted
 * based on the device's screen dimensions and aspect ratio.
 * Compatible with both UIKit and SwiftUI.
 * [PT] Classe para construir dimensões "fixas" que são ajustadas automaticamente
 * com base nas dimensões da tela do dispositivo e na proporção da tela.
 * Compatível com UIKit e SwiftUI.
 */
public class AppDimensFixed {
    
    
    // MARK: - Properties
    
    private let initialBaseValue: CGFloat
    private var ignoreMultiWindowAdjustment: Bool = false
    private var applyAspectRatioAdjustment: Bool = true
    private var customSensitivityK: CGFloat? = nil
    private var screenType: ScreenType = .lowest
    
    /// [EN] Map to store custom values based on intersection (UiMode + DpQualifier) (Priority 1).
    /// Lazy initialization for better performance.
    /// [PT] Mapa para armazenar valores customizados com base na interseção (UiMode + DpQualifier) (Prioridade 1).
    /// Inicialização lazy para melhor performance.
    private lazy var customIntersectionMap: [UiModeQualifierEntry: CGFloat] = [:]
    
    /// [EN] Map to store custom values based on UI mode (Priority 2).
    /// Lazy initialization for better performance.
    /// [PT] Mapa para armazenar valores customizados com base no modo de UI (Prioridade 2).
    /// Inicialização lazy para melhor performance.
    private lazy var customUiModeMap: [UiModeType: CGFloat] = [:]
    
    /// [EN] Map to store custom values based on device type (Priority 3).
    /// Lazy initialization for better performance.
    /// [PT] Mapa para armazenar valores customizados com base no tipo de dispositivo (Prioridade 3).
    /// Inicialização lazy para melhor performance.
    private lazy var customDeviceTypeMap: [DeviceType: CGFloat] = [:]
    
    /// [EN] Map to store custom values based on screen qualifier (Priority 4).
    /// Lazy initialization for better performance.
    /// [PT] Mapa para armazenar valores customizados com base no qualificador de tela (Prioridade 4).
    /// Inicialização lazy para melhor performance.
    private lazy var customScreenQualifierMap: [ScreenQualifierEntry: CGFloat] = [:]
    
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
     * [EN] Initializes the AppDimensFixed with a base value.
     * [PT] Inicializa o AppDimensFixed com um valor base.
     */
    public init(_ initialValue: CGFloat, ignoreMultiWindowAdjustment: Bool = false) {
        self.initialBaseValue = initialValue
        self.ignoreMultiWindowAdjustment = ignoreMultiWindowAdjustment
    }
    
    // MARK: - Cache Management
    
    /**
     * [EN] Invalidates the cache when configuration changes.
     * [PT] Invalida o cache quando a configuração muda.
     */
    private func invalidateCache() {
        baseValueCache.removeAll()
        calculatedValueCache.removeAll()
    }
    
    /**
     * [EN] Checks if screen configuration changed and invalidates cache if needed.
     * [PT] Verifica se a configuração da tela mudou e invalida o cache se necessário.
     */
    private func checkAndInvalidateCacheIfNeeded() {
        let (screenWidth, screenHeight) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
        let currentUiMode = UiModeType.current()
        
        if let lastConfig = lastScreenConfig {
            if lastConfig.width != screenWidth || 
               lastConfig.height != screenHeight || 
               lastConfig.uiMode != currentUiMode {
                invalidateCachedData()
            }
        }
        
        lastScreenConfig = (screenWidth, screenHeight, currentUiMode)
    }
    
    // MARK: - Configuration Methods
    
    /**
     * [EN] Sets a custom dimension value for a specific UI mode.
     * @param uiMode The UI mode type.
     * @param customValue The custom value for this UI mode.
     * [PT] Define um valor de dimensão customizado para um modo específico de UI.
     * @param uiMode O tipo de modo de UI.
     * @param customValue O valor customizado para este modo de UI.
     */
    @discardableResult
    public func screen(_ uiMode: UiModeType, _ customValue: CGFloat) -> AppDimensFixed {
        customUiModeMap[uiMode] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets a custom dimension value for a specific UI mode and DpQualifier intersection.
     * @param uiMode The UI mode type.
     * @param dpQualifier The DpQualifier entry.
     * @param customValue The custom value for this intersection.
     * [PT] Define um valor de dimensão customizado para a interseção de modo de UI e DpQualifier.
     * @param uiMode O tipo de modo de UI.
     * @param dpQualifier A entrada do DpQualifier.
     * @param customValue O valor customizado para esta interseção.
     */
    @discardableResult
    public func screen(_ uiMode: UiModeType, _ dpQualifier: DpQualifierEntry, _ customValue: CGFloat) -> AppDimensFixed {
        let entry = UiModeQualifierEntry(uiModeType: uiMode, dpQualifierEntry: dpQualifier)
        customIntersectionMap[entry] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets a custom dimension value for a specific device type.
     * @param type The device type.
     * @param customValue The custom dimension value in points.
     * @return The AppDimensFixed instance for chaining.
     * [PT] Define um valor de dimensão customizado para um tipo de dispositivo específico.
     * @param type O tipo de dispositivo.
     * @param customValue O valor de dimensão customizado em pontos.
     * @return A instância AppDimensFixed para encadeamento.
     */
    @discardableResult
    public func screen(_ type: DeviceType, _ customValue: CGFloat) -> AppDimensFixed {
        customDeviceTypeMap[type] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets a custom dimension value for a specific device type and screen size.
     * @param deviceType The device type.
     * @param screenSize The minimum screen size for this qualifier.
     * @param customValue The custom dimension value in points.
     * @return The AppDimensFixed instance for chaining.
     * [PT] Define um valor de dimensão customizado para um tipo de dispositivo e tamanho de tela específicos.
     * @param deviceType O tipo de dispositivo.
     * @param screenSize O tamanho mínimo de tela para este qualificador.
     * @param customValue O valor de dimensão customizado em pontos.
     * @return A instância AppDimensFixed para encadeamento.
     */
    @discardableResult
    public func screen(_ deviceType: DeviceType, _ screenSize: CGFloat, _ customValue: CGFloat) -> AppDimensFixed {
        let entry = ScreenQualifierEntry(deviceType: deviceType, screenSize: screenSize)
        customScreenQualifierMap[entry] = customValue
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Enables or disables the aspect ratio adjustment.
     * @param enable If true, enables the adjustment.
     * @param sensitivity Optional custom sensitivity for the adjustment.
     * @return The AppDimensFixed instance for chaining.
     * [PT] Ativa ou desativa o ajuste de proporção.
     * @param enable Se verdadeiro, ativa o ajuste.
     * @param sensitivity Sensibilidade customizada opcional para o ajuste.
     * @return A instância AppDimensFixed para encadeamento.
     */
    @discardableResult
    public func aspectRatio(enable: Bool = true, sensitivity: CGFloat? = nil) -> AppDimensFixed {
        applyAspectRatioAdjustment = enable
        customSensitivityK = sensitivity
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Sets the screen dimension type (LOWEST or HIGHEST) to be used as the base for adjustments.
     * @param type The screen dimension type.
     * @return The AppDimensFixed instance for chaining.
     * [PT] Define o tipo de dimensão da tela (LOWEST ou HIGHEST) a ser usado como base para os ajustes.
     * @param type O tipo de dimensão da tela.
     * @return A instância AppDimensFixed para encadeamento.
     */
    @discardableResult
    public func type(_ type: ScreenType) -> AppDimensFixed {
        screenType = type
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Ignores adjustments when the app is in multi-window mode.
     * @param ignore If true, adjustments are ignored in multi-window mode.
     * @return The AppDimensFixed instance for chaining.
     * [PT] Ignora os ajustes quando o aplicativo está em modo multi-janela.
     * @param ignore Se verdadeiro, os ajustes são ignorados no modo multi-janela.
     * @return A instância AppDimensFixed para encadeamento.
     */
    @discardableResult
    public func multiWindowAdjustment(ignore: Bool = true) -> AppDimensFixed {
        ignoreMultiWindowAdjustment = ignore
        invalidateCachedData()
        return self
    }
    
    /**
     * [EN] Enables or disables cache for this instance.
     * @param enable If true, enables cache; if false, disables cache.
     * @return The AppDimensFixed instance for chaining.
     * [PT] Ativa ou desativa o cache para esta instância.
     * @param enable Se verdadeiro, ativa o cache; se falso, desativa o cache.
     * @return A instância AppDimensFixed para encadeamento.
     */
    @discardableResult
    public func cache(enable: Bool = true) -> AppDimensFixed {
        enableCache = enable
        if !enable {
            autoCache.clearAll()
        }
        return self
    }
    
    // MARK: - Value Resolution
    
    /**
     * [EN] Resolves the base value to be adjusted by applying the customization logic
     * (Intersection > UI Mode > Device Type > Screen Qualifier > Initial Value).
     * [PT] Resolve o valor base a ser ajustado, aplicando a lógica de customização
     * (Interseção > Modo de UI > Tipo de Dispositivo > Qualificador de Tela > Valor Inicial).
     */
    private func resolveFinalBaseValue() -> CGFloat {
        let (screenWidth, screenHeight) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
        let currentUiMode = UiModeType.current()
        let currentDeviceType = DeviceType.current()
        let currentScreenSize = min(screenWidth, screenHeight)
        
        // Generate cache key including ALL options that can influence the result
        let cacheKey = "\(currentUiMode.rawValue)_\(currentDeviceType.rawValue)_\(screenWidth)_\(screenHeight)_\(customIntersectionMap.count)_\(customUiModeMap.count)_\(customDeviceTypeMap.count)_\(customScreenQualifierMap.count)_\(initialBaseValue)_\(screenType.rawValue)_\(ignoreMultiWindowAdjustment)_\(applyAspectRatioAdjustment)_\(customSensitivityK ?? 0)"
        
        // Check cache first (if enabled globally and individually)
        if AppDimensGlobal.globalCacheEnabled && enableCache {
            if let cachedValue = baseValueCache[cacheKey] {
                return cachedValue
            }
        }
        
        var valueToAdjust = initialBaseValue
        
        // Priority 1: Intersection (UiMode + DpQualifier)
        let sortedIntersectionQualifiers = customIntersectionMap.keys.sorted { 
            $0.dpQualifierEntry.value > $1.dpQualifierEntry.value 
        }
        
        for key in sortedIntersectionQualifiers {
            if key.uiModeType == currentUiMode && 
               AppDimensAdjustmentFactors.resolveIntersectionCondition(
                   entry: key.dpQualifierEntry,
                   smallestWidth: currentScreenSize,
                   currentScreenWidth: screenWidth,
                   currentScreenHeight: screenHeight
               ) {
                valueToAdjust = customIntersectionMap[key] ?? initialBaseValue
                break
            }
        }
        
        if valueToAdjust == initialBaseValue {
            // Priority 2: UI Mode (UiModeType only)
            if let uiModeValue = customUiModeMap[currentUiMode] {
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
        
        // Store in cache (if enabled globally and individually)
        if AppDimensGlobal.globalCacheEnabled && enableCache {
            baseValueCache[cacheKey] = valueToAdjust
        }
        
        return valueToAdjust
    }
    
    /**
     * [EN] Performs the final dimension adjustment calculation.
     * @return The adjusted value as a CGFloat (not converted to pixels).
     * [PT] Executa o cálculo final do ajuste da dimensão.
     * @return O valor ajustado como CGFloat (não convertido para pixels).
     */
    public func calculateAdjustedValue() -> CGFloat {
        // Check and invalidate cache if screen configuration changed
        checkAndInvalidateCacheIfNeeded()
        
        // Generate cache key for final calculated value including ALL options
        let (screenWidth, screenHeight) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
        let cacheKey = "\(screenType.rawValue)_\(ignoreMultiWindowAdjustment)_\(applyAspectRatioAdjustment)_\(customSensitivityK ?? 0)_\(screenWidth)_\(screenHeight)_\(initialBaseValue)_\(customIntersectionMap.count)_\(customUiModeMap.count)_\(customDeviceTypeMap.count)_\(customScreenQualifierMap.count)"
        
        // Check cache first (if enabled globally and individually)
        if AppDimensGlobal.globalCacheEnabled && enableCache {
            if let cachedValue = calculatedValueCache[cacheKey] {
                return cachedValue
            }
        }
        
        let valueToAdjust = resolveFinalBaseValue()
        let adjustmentFactors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
        
        let shouldIgnoreAdjustment = ignoreMultiWindowAdjustment && AppDimensAdjustmentFactors.isMultiWindowMode()
        
        let finalAdjustmentFactor: CGFloat
        
        if shouldIgnoreAdjustment {
            finalAdjustmentFactor = AppDimensAdjustmentFactors.BASE_DP_FACTOR
        } else if applyAspectRatioAdjustment {
            let selectedFactor = screenType == .highest ? 
                adjustmentFactors.withArFactorHighest : 
                adjustmentFactors.withArFactorLowest
            
            if let customSensitivity = customSensitivityK {
                let adjustmentFactorBase = screenType == .highest ? 
                    adjustmentFactors.adjustmentFactorHighest : 
                    adjustmentFactors.adjustmentFactorLowest
                
                let (screenWidth, screenHeight) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
                let ar = AppDimensAdjustmentFactors.getReferenceAspectRatio(screenWidth, screenHeight)
                let continuousAdjustment = customSensitivity * log(ar / AppDimensAdjustmentFactors.REFERENCE_AR)
                let finalIncrementValue = AppDimensAdjustmentFactors.BASE_INCREMENT + continuousAdjustment
                
                finalAdjustmentFactor = AppDimensAdjustmentFactors.BASE_DP_FACTOR + adjustmentFactorBase * finalIncrementValue
            } else {
                finalAdjustmentFactor = selectedFactor
            }
        } else {
            finalAdjustmentFactor = adjustmentFactors.withoutArFactor
        }
        
        let finalValue = valueToAdjust * finalAdjustmentFactor
        
        // Store in cache (if enabled globally and individually)
        if AppDimensGlobal.globalCacheEnabled && enableCache {
            calculatedValueCache[cacheKey] = finalValue
        }
        
        return finalValue
    }
    
    // MARK: - Conversion Methods
    
    /**
     * [EN] Returns the adjusted value in points.
     * @return The value in points as a CGFloat.
     * [PT] Retorna o valor ajustado em pontos.
     * @return O valor em pontos como CGFloat.
     */
    public func toPoints() -> CGFloat {
        return calculateAdjustedValue()
    }
    
    /**
     * [EN] Returns the adjusted value in pixels.
     * @return The value in pixels as a CGFloat.
     * [PT] Retorna o valor ajustado em pixels.
     * @return O valor em pixels como CGFloat.
     */
    public func toPixels() -> CGFloat {
        let adjustedValue = calculateAdjustedValue()
        return AppDimensAdjustmentFactors.pointsToPixels(adjustedValue)
    }
    
    /**
     * [EN] Returns the adjusted value as an integer in points.
     * @return The value in points as an Int.
     * [PT] Retorna o valor ajustado como inteiro em pontos.
     * @return O valor em pontos como Int.
     */
    public func toPointsInt() -> Int {
        return Int(calculateAdjustedValue())
    }
    
    /**
     * [EN] Returns the adjusted value as an integer in pixels.
     * @return The value in pixels as an Int.
     * [PT] Retorna o valor ajustado como inteiro em pixels.
     * @return O valor em pixels como Int.
     */
    public func toPixelsInt() -> Int {
        return Int(toPixels())
    }
    
    /**
     * [EN] Returns the adjusted value in scalable pixels (sp) for text.
     * This respects the system font scale setting.
     * Compatible with Android API.
     * @return The value in sp as a CGFloat.
     * [PT] Retorna o valor ajustado em pixels escaláveis (sp) para texto.
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
     * [EN] Returns the adjusted value as an integer in scalable pixels (sp).
     * Compatible with Android API.
     * @return The value in sp as an Int.
     * [PT] Retorna o valor ajustado como inteiro em pixels escaláveis (sp).
     * Compatível com API Android.
     * @return O valor em sp como Int.
     */
    public func toSpInt() -> Int {
        return Int(toSp())
    }
    
    /**
     * [EN] Returns the adjusted value in em units (ignoring font scale).
     * This is similar to sp but without the font scale factor applied.
     * Compatible with Android API.
     * @return The value in em as a CGFloat.
     * [PT] Retorna o valor ajustado em unidades em (ignorando escala de fonte).
     * Isso é similar ao sp mas sem o fator de escala de fonte aplicado.
     * Compatível com API Android.
     * @return O valor em em como CGFloat.
     */
    public func toEm() -> CGFloat {
        return calculateAdjustedValue()
    }
    
    /**
     * [EN] Returns the adjusted value as an integer in em units.
     * Compatible with Android API.
     * @return The value in em as an Int.
     * [PT] Retorna o valor ajustado como inteiro em unidades em.
     * Compatível com API Android.
     * @return O valor em em como Int.
     */
    public func toEmInt() -> Int {
        return Int(toEm())
    }
}
