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

// MARK: - Device Types

/**
 * [EN] Defines the device types for custom dimension values.
 * [PT] Define os tipos de dispositivos para valores de dimensão customizados.
 */
public enum DeviceType: String, CaseIterable {
    case phone = "phone"
    case tablet = "tablet"
    case watch = "watch"
    case tv = "tv"
    case carPlay = "carPlay"
    
    /**
     * [EN] Determines the device type based on the current device.
     * [PT] Determina o tipo de dispositivo baseado no dispositivo atual.
     */
    public static func current() -> DeviceType {
        switch UIDevice.current.userInterfaceIdiom {
        case .phone:
            return .phone
        case .pad:
            return .tablet
        case .tv:
            return .tv
        case .carPlay:
            return .carPlay
        case .watch:
            return .watch
        default:
            return .phone
        }
    }
}

// MARK: - Screen Types

/**
 * [EN] Defines which screen dimension (width or height) should be used
 * as the basis for dynamic and percentage-based sizing calculations.
 * [PT] Define qual dimensão da tela (largura ou altura) deve ser usada
 * como base para cálculos de dimensionamento dinâmico e percentual.
 */
public enum ScreenType {
    case highest, lowest
}

// MARK: - Unit Types

/**
 * [EN] Defines the supported physical measurement units for conversion
 * into points or pixels.
 * [PT] Define as unidades de medida física suportadas para conversão
 * em pontos ou pixels.
 */
public enum UnitType {
    case inch, cm, mm, sp, pt, px
}

// MARK: - Screen Qualifier Entry

/**
 * [EN] Represents a screen qualifier entry for custom dimension values.
 * [PT] Representa uma entrada de qualificador de tela para valores de dimensão customizados.
 */
public struct ScreenQualifierEntry: Hashable {
    public let deviceType: DeviceType
    public let screenSize: CGFloat
    
    public init(deviceType: DeviceType, screenSize: CGFloat) {
        self.deviceType = deviceType
        self.screenSize = screenSize
    }
}

// MARK: - Screen Adjustment Factors

/**
 * [EN] Stores the adjustment factors calculated from the screen dimensions.
 * The Aspect Ratio (AR) calculation is performed only once per screen configuration.
 * [PT] Armazena os fatores de ajuste calculados a partir das dimensões da tela.
 * O cálculo do Aspect Ratio (AR) é feito apenas uma vez por configuração de tela.
 */
public struct ScreenAdjustmentFactors {
    /// [EN] The final and COMPLETE scaling factor, using the LOWEST base (smallest dimension) + AR.
    /// [PT] Fator de escala final e COMPLETO, usando a base LOWEST (menor dimensão) + AR.
    public let withArFactorLowest: CGFloat
    
    /// [EN] The final and COMPLETE scaling factor, using the HIGHEST base (largest dimension) + AR.
    /// [PT] Fator de escala final e COMPLETO, usando a base HIGHEST (maior dimensão) + AR.
    public let withArFactorHighest: CGFloat
    
    /// [EN] The final scaling factor WITHOUT AR (uses the LOWEST base for safety).
    /// [PT] Fator de escala final SEM AR (Usa a base LOWEST por segurança).
    public let withoutArFactor: CGFloat
    
    /// [EN] The base adjustment factor (increment multiplier), LOWEST: smallest dimension.
    /// [PT] Fator base de ajuste (multiplicador do incremento), LOWEST: menor dimensão.
    public let adjustmentFactorLowest: CGFloat
    
    /// [EN] The base adjustment factor (increment multiplier), HIGHEST: max(W, H).
    /// [PT] Fator base de ajuste (multiplicador do incremento), HIGHEST: max(W, H).
    public let adjustmentFactorHighest: CGFloat
    
    public init(
        withArFactorLowest: CGFloat,
        withArFactorHighest: CGFloat,
        withoutArFactor: CGFloat,
        adjustmentFactorLowest: CGFloat,
        adjustmentFactorHighest: CGFloat
    ) {
        self.withArFactorLowest = withArFactorLowest
        self.withArFactorHighest = withArFactorHighest
        self.withoutArFactor = withoutArFactor
        self.adjustmentFactorLowest = adjustmentFactorLowest
        self.adjustmentFactorHighest = adjustmentFactorHighest
    }
}
