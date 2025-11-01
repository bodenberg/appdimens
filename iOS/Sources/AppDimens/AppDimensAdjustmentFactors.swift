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
    
    /// [EN] Base width in points for reference calculations (unified: 300pt for exact Android compatibility).
    /// [PT] Largura base em pontos para cálculos de referência (unificado: 300pt para compatibilidade exata com Android).
    public static let BASE_WIDTH_PT: CGFloat = 300.0
    
    /// [EN] Base height in points for reference calculations (iPhone 6/7/8 height).
    /// [PT] Altura base em pontos para cálculos de referência (altura do iPhone 6/7/8).
    public static let BASE_HEIGHT_PT: CGFloat = 667.0
    
    /// [EN] Increment step size for calculating adjustment (unified: 1pt granularity).
    /// [PT] Tamanho do passo de incremento para calcular o ajuste (unificado: granularidade de 1pt).
    public static let INCREMENT_DP_STEP: CGFloat = 1.0
    
    /// [EN] Base increment value for logarithmic calculations (adjusted for 1dp step granularity).
    /// [PT] Valor base de incremento para cálculos logarítmicos (ajustado para granularidade de step 1dp).
    public static let BASE_INCREMENT: CGFloat = 0.1 / 30.0  // Adjusted for 1dp step granularity
    
    /// [EN] Base factor for DP calculations.
    /// [PT] Fator base para cálculos de DP.
    public static let BASE_DP_FACTOR: CGFloat = 1.0
    
    /// [EN] Reference aspect ratio for calculations (unified: 16:9 landscape).
    /// [PT] Proporção de referência para cálculos (unificado: 16:9 landscape).
    public static let REFERENCE_AR: CGFloat = 1.78  // Unified reference AR (16:9 landscape)
    
    /// [EN] Default sensitivity coefficient for logarithmic adjustment (adjusted for 1dp step granularity).
    /// [PT] Coeficiente de sensibilidade padrão para ajuste logarítmico (ajustado para granularidade de step 1dp).
    public static let DEFAULT_SENSITIVITY_K: CGFloat = 0.08 / 30.0  // Adjusted for 1dp step granularity
    
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
     * Unified: Returns largest/smallest (landscape normalization).
     * [PT] Calcula a proporção das dimensões dadas.
     * Unificado: Retorna maior/menor (normalização landscape).
     */
    public static func getReferenceAspectRatio(_ width: CGFloat, _ height: CGFloat) -> CGFloat {
        // Unified: normalize to landscape (largest/smallest)
        return width >= height ? width / height : height / width
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
     * Uses unified formula: 1.0 + ((dimension - BASE_WIDTH) / STEP) × (BASE_INCREMENT + K × ln(AR / AR₀))
     * [PT] Calcula os fatores de ajuste para as dimensões de tela dadas.
     * Usa fórmula unificada: 1.0 + ((dimension - BASE_WIDTH) / STEP) × (BASE_INCREMENT + K × ln(AR / AR₀))
     */
    public static func calculateAdjustmentFactors(width: CGFloat, height: CGFloat) -> ScreenAdjustmentFactors {
        let smallestDimension = min(width, height)
        let largestDimension = max(width, height)
        
        // Unified formula: subtraction + step
        let differenceLowest = smallestDimension - BASE_WIDTH_PT
        let differenceHighest = largestDimension - BASE_WIDTH_PT
        let adjustmentFactorLowest = differenceLowest / INCREMENT_DP_STEP
        let adjustmentFactorHighest = differenceHighest / INCREMENT_DP_STEP
        
        // Calculate aspect ratio (normalized to landscape: largest/smallest)
        let currentAR = getReferenceAspectRatio(width, height)
        
        // Unified logarithmic adjustment
        let arAdjustment = DEFAULT_SENSITIVITY_K * log(currentAR / REFERENCE_AR)
        let finalIncrementValueWithAr = BASE_INCREMENT + arAdjustment
        
        // Calculate final factors with AR using unified formula
        let withArFactorLowest = BASE_DP_FACTOR + adjustmentFactorLowest * finalIncrementValueWithAr
        let withArFactorHighest = BASE_DP_FACTOR + adjustmentFactorHighest * finalIncrementValueWithAr
        
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
    
    // MARK: - Base Orientation Resolution
    
    /**
     * [EN] Resolves the effective ScreenType based on the base orientation and current device orientation.
     * If the base orientation differs from the current orientation, LOWEST and HIGHEST are inverted.
     *
     * @param requestedType The originally requested screen type (.lowest or .highest)
     * @param baseOrientation The orientation for which the design was created (.portrait, .landscape, or .auto)
     * @param bounds The current screen bounds
     * @return The resolved ScreenType (may be inverted from requestedType)
     *
     * [PT] Resolve o ScreenType efetivo baseado na orientação base e na orientação atual do dispositivo.
     * Se a orientação base difere da orientação atual, .lowest e .highest são invertidos.
     *
     * @param requestedType O tipo de tela originalmente requisitado (.lowest ou .highest)
     * @param baseOrientation A orientação para a qual o design foi criado (.portrait, .landscape ou .auto)
     * @param bounds As dimensões atuais da tela
     * @return O ScreenType resolvido (pode ser invertido do requestedType)
     */
    public static func resolveScreenType(
        requestedType: ScreenType,
        baseOrientation: BaseOrientation,
        bounds: CGRect
    ) -> ScreenType {
        // If AUTO, no inversion - return as requested
        if baseOrientation == .auto {
            return requestedType
        }
        
        // Detect current orientation
        let currentIsPortrait = bounds.height > bounds.width
        let currentIsLandscape = !currentIsPortrait
        
        // Determine if inversion is needed
        let shouldInvert: Bool
        switch baseOrientation {
        case .portrait:
            shouldInvert = currentIsLandscape
        case .landscape:
            shouldInvert = currentIsPortrait
        case .auto:
            shouldInvert = false
        }
        
        // Invert if needed
        if shouldInvert {
            return requestedType == .lowest ? .highest : .lowest
        } else {
            return requestedType
        }
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
     * [EN] Gets the system font scale factor.
     * Compatible with Android API.
     * @return The font scale factor (typically 1.0, but varies based on accessibility settings).
     * [PT] Obtém o fator de escala de fonte do sistema.
     * Compatível com API Android.
     * @return O fator de escala de fonte (tipicamente 1.0, mas varia baseado nas configurações de acessibilidade).
     */
    public static func getFontScale() -> CGFloat {
        // In iOS, the font scale is controlled by Dynamic Type
        // We can approximate it using the preferred content size category
        let contentSizeCategory = UIApplication.shared.preferredContentSizeCategory
        
        // Map content size categories to scale factors
        switch contentSizeCategory {
        case .extraSmall:
            return 0.8
        case .small:
            return 0.9
        case .medium:
            return 1.0
        case .large:
            return 1.0 // Default size
        case .extraLarge:
            return 1.1
        case .extraExtraLarge:
            return 1.2
        case .extraExtraExtraLarge:
            return 1.3
        case .accessibilityMedium:
            return 1.4
        case .accessibilityLarge:
            return 1.6
        case .accessibilityExtraLarge:
            return 1.8
        case .accessibilityExtraExtraLarge:
            return 2.0
        case .accessibilityExtraExtraExtraLarge:
            return 2.3
        default:
            return 1.0
        }
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
