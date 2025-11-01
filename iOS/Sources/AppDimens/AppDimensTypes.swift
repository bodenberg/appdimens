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

// MARK: - UI Mode Types

/**
 * [EN] Defines UI mode types for custom dimension values (similar to Android UiModeType).
 * [PT] Define tipos de modo de UI para valores de dimensão customizados (similar ao Android UiModeType).
 */
public enum UiModeType: String, CaseIterable {
    case normal = "normal"
    case carPlay = "carPlay"
    case tv = "tv"
    case watch = "watch"
    case mac = "mac"
    
    /**
     * [EN] Determines the UI mode type based on the current device and context.
     * [PT] Determina o tipo de modo de UI baseado no dispositivo atual e contexto.
     */
    public static func current() -> UiModeType {
        switch UIDevice.current.userInterfaceIdiom {
        case .phone, .pad:
            return .normal
        case .tv:
            return .tv
        case .carPlay:
            return .carPlay
        case .watch:
            return .watch
        case .mac:
            return .mac
        @unknown default:
            return .normal
        }
    }
}

// MARK: - DP Qualifier Types

/**
 * [EN] Defines the screen qualifier types based on device dimensions (similar to Android DpQualifier).
 * [PT] Define os tipos de qualificador de tela baseados nas dimensões do dispositivo (similar ao Android DpQualifier).
 */
public enum DpQualifier {
    case smallWidth, height, width
}

/**
 * [EN] Represents a custom qualifier entry, combining the type and the minimum value
 * for the custom adjustment to be applied (similar to Android DpQualifierEntry).
 * [PT] Representa uma entrada de qualificador customizado, combinando o tipo e o valor mínimo
 * para que o ajuste customizado seja aplicado (similar ao Android DpQualifierEntry).
 */
public struct DpQualifierEntry: Hashable {
    public let type: DpQualifier
    public let value: Int
    
    public init(type: DpQualifier, value: Int) {
        self.type = type
        self.value = value
    }
}

/**
 * [EN] Represents a qualifier entry that combines a UI Mode type AND a screen qualifier.
 * This combination has the HIGHEST PRIORITY (similar to Android UiModeQualifierEntry).
 * [PT] Representa uma entrada de qualificador que combina um tipo de UI Mode E um qualificador de tela.
 * Esta combinação tem a PRIORIDADE MÁXIMA (similar ao Android UiModeQualifierEntry).
 */
public struct UiModeQualifierEntry: Hashable {
    public let uiModeType: UiModeType
    public let dpQualifierEntry: DpQualifierEntry
    
    public init(uiModeType: UiModeType, dpQualifierEntry: DpQualifierEntry) {
        self.uiModeType = uiModeType
        self.dpQualifierEntry = dpQualifierEntry
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

// MARK: - Base Orientation

/**
 * [EN] Defines the base orientation for which the design was originally created.
 * This allows the system to automatically invert LOWEST/HIGHEST screen types
 * when the current orientation differs from the design orientation.
 * 
 * [PT] Define a orientação base para a qual o design foi originalmente criado.
 * Isso permite que o sistema inverta automaticamente os tipos de tela LOWEST/HIGHEST
 * quando a orientação atual difere da orientação do design.
 */
public enum BaseOrientation {
    /**
     * [EN] Design created for portrait orientation (width < height).
     * When device is in landscape, LOWEST↔HIGHEST will be inverted.
     * 
     * [PT] Design criado para orientação portrait (largura < altura).
     * Quando o dispositivo está em landscape, LOWEST↔HIGHEST será invertido.
     */
    case portrait
    
    /**
     * [EN] Design created for landscape orientation (width > height).
     * When device is in portrait, LOWEST↔HIGHEST will be inverted.
     * 
     * [PT] Design criado para orientação landscape (largura > altura).
     * Quando o dispositivo está em portrait, LOWEST↔HIGHEST será invertido.
     */
    case landscape
    
    /**
     * [EN] No specific orientation (default behavior).
     * Screen types are used as-is without auto-inversion.
     * 
     * [PT] Nenhuma orientação específica (comportamento padrão).
     * Tipos de tela são usados como estão, sem auto-inversão.
     */
    case auto
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
