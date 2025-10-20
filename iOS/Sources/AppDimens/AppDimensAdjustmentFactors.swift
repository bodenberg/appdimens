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
 * [EN] Utility class for calculating screen adjustment factors.
 * [PT] Classe utilitária para calcular fatores de ajuste de tela.
 */
public class AppDimensAdjustmentFactors {
    
    // MARK: - Constants
    
    /// [EN] Base width in points for reference calculations (iPhone 6/7/8 width).
    /// [PT] Largura base em pontos para cálculos de referência (largura do iPhone 6/7/8).
    public static let BASE_WIDTH_PT: CGFloat = 375.0
    
    /// [EN] Base height in points for reference calculations (iPhone 6/7/8 height).
    /// [PT] Altura base em pontos para cálculos de referência (altura do iPhone 6/7/8).
    public static let BASE_HEIGHT_PT: CGFloat = 667.0
    
    /// [EN] Base increment value for logarithmic calculations.
    /// [PT] Valor base de incremento para cálculos logarítmicos.
    public static let BASE_INCREMENT: CGFloat = 0.1
    
    /// [EN] Base factor for DP calculations.
    /// [PT] Fator base para cálculos de DP.
    public static let BASE_DP_FACTOR: CGFloat = 1.0
    
    /// [EN] Reference aspect ratio for calculations.
    /// [PT] Proporção de referência para cálculos.
    public static let REFERENCE_AR: CGFloat = BASE_WIDTH_PT / BASE_HEIGHT_PT
    
    /// [EN] Default sensitivity coefficient for logarithmic adjustment.
    /// [PT] Coeficiente de sensibilidade padrão para ajuste logarítmico.
    public static let DEFAULT_SENSITIVITY_K: CGFloat = 0.5
    
    // MARK: - Screen Information
    
    /**
     * [EN] Gets the current screen dimensions in points.
     * [PT] Obtém as dimensões atuais da tela em pontos.
     */
    public static func getCurrentScreenDimensions() -> (width: CGFloat, height: CGFloat) {
        let screen = UIScreen.main
        let bounds = screen.bounds
        return (width: bounds.width, height: bounds.height)
    }
    
    /**
     * [EN] Gets the current screen dimensions in pixels.
     * [PT] Obtém as dimensões atuais da tela em pixels.
     */
    public static func getCurrentScreenDimensionsInPixels() -> (width: CGFloat, height: CGFloat) {
        let screen = UIScreen.main
        let bounds = screen.bounds
        let scale = screen.scale
        return (width: bounds.width * scale, height: bounds.height * scale)
    }
    
    /**
     * [EN] Calculates the aspect ratio of the given dimensions.
     * [PT] Calcula a proporção das dimensões dadas.
     */
    public static func getReferenceAspectRatio(_ width: CGFloat, _ height: CGFloat) -> CGFloat {
        return width / height
    }
    
    // MARK: - Adjustment Factor Calculations
    
    /**
     * [EN] Calculates the adjustment factors for the current screen configuration.
     * [PT] Calcula os fatores de ajuste para a configuração atual da tela.
     */
    public static func calculateAdjustmentFactors() -> ScreenAdjustmentFactors {
        let (screenWidth, screenHeight) = getCurrentScreenDimensions()
        return calculateAdjustmentFactors(width: screenWidth, height: screenHeight)
    }
    
    /**
     * [EN] Calculates the adjustment factors for the given screen dimensions.
     * [PT] Calcula os fatores de ajuste para as dimensões de tela dadas.
     */
    public static func calculateAdjustmentFactors(width: CGFloat, height: CGFloat) -> ScreenAdjustmentFactors {
        let smallestDimension = min(width, height)
        let largestDimension = max(width, height)
        
        // Calculate base adjustment factors
        let adjustmentFactorLowest = smallestDimension / BASE_WIDTH_PT
        let adjustmentFactorHighest = largestDimension / BASE_WIDTH_PT
        
        // Calculate aspect ratio
        let currentAR = getReferenceAspectRatio(width, height)
        let arAdjustment = log(currentAR / REFERENCE_AR) * DEFAULT_SENSITIVITY_K
        
        // Calculate final factors with AR
        let withArFactorLowest = BASE_DP_FACTOR + adjustmentFactorLowest * (BASE_INCREMENT + arAdjustment)
        let withArFactorHighest = BASE_DP_FACTOR + adjustmentFactorHighest * (BASE_INCREMENT + arAdjustment)
        
        // Calculate factor without AR
        let withoutArFactor = BASE_DP_FACTOR + adjustmentFactorLowest * BASE_INCREMENT
        
        return ScreenAdjustmentFactors(
            withArFactorLowest: withArFactorLowest,
            withArFactorHighest: withArFactorHighest,
            withoutArFactor: withoutArFactor,
            adjustmentFactorLowest: adjustmentFactorLowest,
            adjustmentFactorHighest: adjustmentFactorHighest
        )
    }
    
    // MARK: - Screen Qualifier Resolution
    
    /**
     * [EN] Resolves the appropriate dimension value based on screen qualifiers.
     * [PT] Resolve o valor de dimensão apropriado baseado nos qualificadores de tela.
     */
    public static func resolveQualifierValue(
        customMap: [ScreenQualifierEntry: CGFloat],
        currentDeviceType: DeviceType,
        currentScreenSize: CGFloat,
        initialValue: CGFloat
    ) -> CGFloat {
        let sortedQualifiers = customMap.keys.sorted { $0.screenSize > $1.screenSize }
        
        for entry in sortedQualifiers {
            if entry.deviceType == currentDeviceType && currentScreenSize >= entry.screenSize {
                return customMap[entry] ?? initialValue
            }
        }
        
        return initialValue
    }
    
    /**
     * [EN] Resolves the appropriate dimension value based on device type only.
     * [PT] Resolve o valor de dimensão apropriado baseado apenas no tipo de dispositivo.
     */
    public static func resolveDeviceTypeValue(
        customMap: [DeviceType: CGFloat],
        currentDeviceType: DeviceType,
        initialValue: CGFloat
    ) -> CGFloat {
        return customMap[currentDeviceType] ?? initialValue
    }
    
    /**
     * [EN] Helper function that checks if a DpQualifierEntry meets the current screen dimensions.
     * [PT] Função auxiliar que verifica se um DpQualifierEntry atende às dimensões atuais da tela.
     */
    public static func resolveIntersectionCondition(
        entry: DpQualifierEntry,
        smallestWidth: CGFloat,
        currentScreenWidth: CGFloat,
        currentScreenHeight: CGFloat
    ) -> Bool {
        switch entry.type {
        case .smallWidth:
            return smallestWidth >= CGFloat(entry.value)
        case .height:
            return currentScreenHeight >= CGFloat(entry.value)
        case .width:
            return currentScreenWidth >= CGFloat(entry.value)
        }
    }
    
    // MARK: - Multi-Window Detection
    
    /**
     * [EN] Detects if the app is running in multi-window mode (iPad).
     * [PT] Detecta se o app está rodando em modo multi-janela (iPad).
     */
    public static func isMultiWindowMode() -> Bool {
        guard UIDevice.current.userInterfaceIdiom == .pad else { return false }
        
        let (screenWidth, screenHeight) = getCurrentScreenDimensions()
        let smallestWidth = min(screenWidth, screenHeight)
        
        // Check if the current width is significantly smaller than the smallest possible width
        // This indicates the app is running in split-screen mode
        return screenWidth < smallestWidth * 0.9
    }
    
    // MARK: - Utility Functions
    
    /**
     * [EN] Converts points to pixels based on the current screen scale.
     * [PT] Converte pontos para pixels baseado na escala atual da tela.
     */
    public static func pointsToPixels(_ points: CGFloat) -> CGFloat {
        return points * UIScreen.main.scale
    }
    
    /**
     * [EN] Converts pixels to points based on the current screen scale.
     * [PT] Converte pixels para pontos baseado na escala atual da tela.
     */
    public static func pixelsToPoints(_ pixels: CGFloat) -> CGFloat {
        return pixels / UIScreen.main.scale
    }
    
    /**
     * [EN] Calculates the maximum number of items that can fit in a container.
     * [PT] Calcula o número máximo de itens que cabem em um contêiner.
     */
    public static func calculateAvailableItemCount(
        containerSize: CGFloat,
        itemSize: CGFloat,
        itemMargin: CGFloat
    ) -> Int {
        let totalItemSize = itemSize + (itemMargin * 2)
        return totalItemSize > 0 ? Int(containerSize / totalItemSize) : 0
    }
}
