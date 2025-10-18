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
    
    /// [EN] Map to store custom values based on device type (Priority 2).
    /// [PT] Mapa para armazenar valores customizados com base no tipo de dispositivo (Prioridade 2).
    private var customDeviceTypeMap: [DeviceType: CGFloat] = [:]
    
    /// [EN] Map to store custom values based on screen qualifier (Priority 3).
    /// [PT] Mapa para armazenar valores customizados com base no qualificador de tela (Prioridade 3).
    private var customScreenQualifierMap: [ScreenQualifierEntry: CGFloat] = [:]
    
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
    
    // MARK: - Value Resolution
    
    /**
     * [EN] Resolves the base value to be adjusted by applying the customization logic
     * (Device Type > Screen Qualifier > Initial Value).
     * [PT] Resolve o valor base a ser ajustado, aplicando a lógica de customização
     * (Tipo de Dispositivo > Qualificador de Tela > Valor Inicial).
     */
    private func resolveFinalBaseValue() -> CGFloat {
        let currentDeviceType = DeviceType.current()
        let (screenWidth, screenHeight) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
        let currentScreenSize = min(screenWidth, screenHeight)
        
        // Priority 1: Device Type
        if let deviceTypeValue = customDeviceTypeMap[currentDeviceType] {
            return deviceTypeValue
        }
        
        // Priority 2: Screen Qualifier
        let qualifierValue = AppDimensAdjustmentFactors.resolveQualifierValue(
            customMap: customScreenQualifierMap,
            currentDeviceType: currentDeviceType,
            currentScreenSize: currentScreenSize,
            initialValue: initialBaseValue
        )
        
        return qualifierValue
    }
    
    /**
     * [EN] Performs the final dynamic dimension calculation.
     * @return The dynamically adjusted value as a CGFloat (not converted to pixels).
     * [PT] Executa o cálculo final da dimensão dinâmica.
     * @return O valor ajustado dinamicamente como CGFloat (não convertido para pixels).
     */
    public func calculateAdjustedValue() -> CGFloat {
        let valueToAdjust = resolveFinalBaseValue()
        
        let shouldIgnoreAdjustment = ignoreMultiWindowAdjustment && AppDimensAdjustmentFactors.isMultiWindowMode()
        
        if shouldIgnoreAdjustment {
            // Returns the base value without dynamic scaling
            return valueToAdjust
        }
        
        // The dynamic scaling percentage is: (Adjusted Base Value / Reference Value)
        let percentage = valueToAdjust / AppDimensAdjustmentFactors.BASE_WIDTH_PT
        
        // Screen dimension to use (LOWEST or HIGHEST)
        let (screenWidth, screenHeight) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
        let dimensionToUse = screenType == .highest ? 
            max(screenWidth, screenHeight) : 
            min(screenWidth, screenHeight)
        
        // The final value is the percentage applied to the screen dimension
        return dimensionToUse * percentage
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
}
