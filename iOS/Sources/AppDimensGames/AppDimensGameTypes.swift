/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Metal/Games Module
 *
 * Description:
 * Game-specific types and enums for the AppDimens Metal/Games module.
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
import Metal
import simd

// MARK: - Base Orientation

/**
 * [EN] Defines the base orientation for which the design was originally created.
 * When set, LOWEST/HIGHEST will be auto-inverted based on current orientation.
 * [PT] Define a orientação base para a qual o design foi originalmente criado.
 * Quando configurado, LOWEST/HIGHEST será auto-invertido baseado na orientação atual.
 */
public enum GameBaseOrientation {
    case portrait   // Design created for portrait
    case landscape  // Design created for landscape
    case auto       // No specific orientation (default)
}

/**
 * [EN] Screen type for dimension calculations in games.
 * [PT] Tipo de tela para cálculos de dimensões em jogos.
 */
public enum GameScreenType {
    case lowest     // Use smallest dimension
    case highest    // Use largest dimension
}

/**
 * [EN] Resolves the effective ScreenType based on base orientation and current dimensions.
 * [PT] Resolve o ScreenType efetivo baseado na orientação base e dimensões atuais.
 */
public func resolveGameScreenType(
    requestedType: GameScreenType,
    baseOrientation: GameBaseOrientation,
    width: Float,
    height: Float
) -> GameScreenType {
    // If AUTO, no inversion - return as requested
    if baseOrientation == .auto {
        return requestedType
    }
    
    // Detect current orientation
    let currentIsPortrait = height > width
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

// MARK: - Game Device Types

/**
 * [EN] Defines game-specific device types for dimension calculations.
 * [PT] Define tipos de dispositivos específicos para jogos para cálculos de dimensão.
 */
public enum GameDeviceType: String, CaseIterable {
    case phone = "phone"
    case tablet = "tablet"
    case tv = "tv"
    case mac = "mac"
    case simulator = "simulator"
    
    /**
     * [EN] Determines the game device type based on the current device.
     * [PT] Determina o tipo de dispositivo de jogo baseado no dispositivo atual.
     */
    public static func current() -> GameDeviceType {
        #if targetEnvironment(simulator)
        return .simulator
        #elseif os(macOS)
        return .mac
        #elseif os(tvOS)
        return .tv
        #else
        switch UIDevice.current.userInterfaceIdiom {
        case .phone:
            return .phone
        case .pad:
            return .tablet
        case .tv:
            return .tv
        default:
            return .phone
        }
        #endif
    }
}

// MARK: - Game Resolution Types

/**
 * [EN] Defines common game resolution types for reference calculations.
 * [PT] Define tipos comuns de resolução de jogo para cálculos de referência.
 */
public enum GameResolutionType: String, CaseIterable {
    case hd = "720p"           // 1280x720
    case fullHD = "1080p"      // 1920x1080
    case qhd = "1440p"         // 2560x1440
    case uhd4k = "4K"          // 3840x2160
    case uhd8k = "8K"          // 7680x4320
    case mobile = "mobile"     // 375x667 (iPhone base)
    case tablet = "tablet"     // 1024x768 (iPad base)
    
    /**
     * [EN] Gets the resolution dimensions for this type.
     * @return A tuple with (width, height).
     * [PT] Obtém as dimensões de resolução para este tipo.
     * @return Uma tupla com (largura, altura).
     */
    public func dimensions() -> (width: Float, height: Float) {
        switch self {
        case .hd:
            return (1280, 720)
        case .fullHD:
            return (1920, 1080)
        case .qhd:
            return (2560, 1440)
        case .uhd4k:
            return (3840, 2160)
        case .uhd8k:
            return (7680, 4320)
        case .mobile:
            return (375, 667)
        case .tablet:
            return (1024, 768)
        }
    }
    
    /**
     * [EN] Gets the aspect ratio for this resolution type.
     * @return The aspect ratio.
     * [PT] Obtém a proporção para este tipo de resolução.
     * @return A proporção.
     */
    public func aspectRatio() -> Float {
        let (width, height) = dimensions()
        return width / height
    }
}

// MARK: - Game UI Element Types

/**
 * [EN] Defines types of game UI elements for specialized scaling.
 * [PT] Define tipos de elementos de UI de jogo para escalonamento especializado.
 */
public enum GameUIElementType: String, CaseIterable {
    case button = "button"
    case text = "text"
    case icon = "icon"
    case background = "background"
    case border = "border"
    case spacing = "spacing"
    case menu = "menu"
    case hud = "hud"
    case dialog = "dialog"
    case notification = "notification"
    
    /**
     * [EN] Gets the default scaling mode for this UI element type.
     * @return The recommended scaling mode.
     * [PT] Obtém o modo de escalonamento padrão para este tipo de elemento de UI.
     * @return O modo de escalonamento recomendado.
     */
    public func defaultScalingMode() -> GameScalingMode {
        switch self {
        case .button, .icon, .border:
            return .uniform
        case .text, .notification:
            return .aspectRatio
        case .background, .menu, .dialog:
            return .viewport
        case .hud:
            return .horizontal
        case .spacing:
            return .vertical
        }
    }
}

// MARK: - Game Viewport Configuration

/**
 * [EN] Configuration for game viewport settings.
 * [PT] Configuração para configurações de viewport de jogo.
 */
public struct GameViewportConfig {
    public let width: Float
    public let height: Float
    public let aspectRatio: Float
    public let scalingMode: GameScalingMode
    public let resolutionType: GameResolutionType
    
    public init(
        width: Float,
        height: Float,
        scalingMode: GameScalingMode = .uniform,
        resolutionType: GameResolutionType = .fullHD
    ) {
        self.width = width
        self.height = height
        self.aspectRatio = width / height
        self.scalingMode = scalingMode
        self.resolutionType = resolutionType
    }
    
    /**
     * [EN] Creates a viewport config from a Metal viewport.
     * @param viewport The Metal viewport.
     * @param scalingMode The scaling mode to use.
     * @return A game viewport configuration.
     * [PT] Cria uma configuração de viewport a partir de um viewport Metal.
     * @param viewport O viewport Metal.
     * @param scalingMode O modo de escalonamento a ser usado.
     * @return Uma configuração de viewport de jogo.
     */
    public static func fromMetalViewport(
        _ viewport: MTLViewport,
        scalingMode: GameScalingMode = .uniform
    ) -> GameViewportConfig {
        return GameViewportConfig(
            width: Float(viewport.width),
            height: Float(viewport.height),
            scalingMode: scalingMode
        )
    }
}

// MARK: - Game Dimension Entry

/**
 * [EN] Represents a game dimension entry with device-specific values.
 * [PT] Representa uma entrada de dimensão de jogo com valores específicos do dispositivo.
 */
public struct GameDimensionEntry: Hashable {
    public let deviceType: GameDeviceType
    public let resolutionType: GameResolutionType
    public let value: Float
    
    public init(deviceType: GameDeviceType, resolutionType: GameResolutionType, value: Float) {
        self.deviceType = deviceType
        self.resolutionType = resolutionType
        self.value = value
    }
}

// MARK: - Game Performance Settings

/**
 * [EN] Performance settings for game dimension calculations.
 * [PT] Configurações de performance para cálculos de dimensão de jogo.
 */
public struct GamePerformanceSettings {
    public let enableCaching: Bool
    public let cacheSize: Int
    public let enableAsyncCalculation: Bool
    public let maxCalculationTime: TimeInterval
    
    public init(
        enableCaching: Bool = true,
        cacheSize: Int = 1000,
        enableAsyncCalculation: Bool = false,
        maxCalculationTime: TimeInterval = 0.016 // 60 FPS
    ) {
        self.enableCaching = enableCaching
        self.cacheSize = cacheSize
        self.enableAsyncCalculation = enableAsyncCalculation
        self.maxCalculationTime = maxCalculationTime
    }
    
    /**
     * [EN] Default performance settings optimized for games.
     * [PT] Configurações de performance padrão otimizadas para jogos.
     */
    public static let `default` = GamePerformanceSettings()
    
    /**
     * [EN] High performance settings for demanding games.
     * [PT] Configurações de alta performance para jogos exigentes.
     */
    public static let highPerformance = GamePerformanceSettings(
        enableCaching: true,
        cacheSize: 2000,
        enableAsyncCalculation: true,
        maxCalculationTime: 0.008 // 120 FPS
    )
    
    /**
     * [EN] Low performance settings for simple games.
     * [PT] Configurações de baixa performance para jogos simples.
     */
    public static let lowPerformance = GamePerformanceSettings(
        enableCaching: false,
        cacheSize: 100,
        enableAsyncCalculation: false,
        maxCalculationTime: 0.033 // 30 FPS
    )
}
