/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Metal/Games Module
 *
 * Description:
 * Main export file for the AppDimens Games module, providing access to all
 * Metal-specific functionality for game development.
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
import MetalKit
import simd

// MARK: - Games Module Exports

// Re-export all games functionality
// Note: These are defined in the same module, so no import needed

// MARK: - Convenience Extensions

public extension AppDimensGames {
    
    /**
     * [EN] Creates a game dimension with uniform scaling.
     * @param value The base value to scale.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento uniforme.
     * @param value O valor base a ser escalonado.
     * @return A dimensão escalonada.
     */
    static func uniform(_ value: Float) -> Float {
        return AppDimensGames.shared.uniform(value)
    }
    
    /**
     * [EN] Creates a game dimension with horizontal scaling.
     * @param value The base value to scale.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento horizontal.
     * @param value O valor base a ser escalonado.
     * @return A dimensão escalonada.
     */
    static func horizontal(_ value: Float) -> Float {
        return AppDimensGames.shared.horizontal(value)
    }
    
    /**
     * [EN] Creates a game dimension with vertical scaling.
     * @param value The base value to scale.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento vertical.
     * @param value O valor base a ser escalonado.
     * @return A dimensão escalonada.
     */
    static func vertical(_ value: Float) -> Float {
        return AppDimensGames.shared.vertical(value)
    }
    
    /**
     * [EN] Creates a game dimension with aspect ratio scaling.
     * @param value The base value to scale.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento de proporção.
     * @param value O valor base a ser escalonado.
     * @return A dimensão escalonada.
     */
    static func aspectRatio(_ value: Float) -> Float {
        return AppDimensGames.shared.aspectRatio(value)
    }
    
    /**
     * [EN] Creates a game dimension with viewport scaling.
     * @param value The base value to scale.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento de viewport.
     * @param value O valor base a ser escalonado.
     * @return A dimensão escalonada.
     */
    static func viewport(_ value: Float) -> Float {
        return AppDimensGames.shared.viewport(value)
    }
}

// MARK: - Global Convenience Functions

/**
 * [EN] Creates a game dimension with uniform scaling.
 * @param value The base value to scale.
 * @return The scaled dimension.
 * [PT] Cria uma dimensão de jogo com escalonamento uniforme.
 * @param value O valor base a ser escalonado.
 * @return A dimensão escalonada.
 */
public func gameUniform(_ value: Float) -> Float {
    return AppDimensGames.uniform(value)
}

/**
 * [EN] Creates a game dimension with horizontal scaling.
 * @param value The base value to scale.
 * @return The scaled dimension.
 * [PT] Cria uma dimensão de jogo com escalonamento horizontal.
 * @param value O valor base a ser escalonado.
 * @return A dimensão escalonada.
 */
public func gameHorizontal(_ value: Float) -> Float {
    return AppDimensGames.horizontal(value)
}

/**
 * [EN] Creates a game dimension with vertical scaling.
 * @param value The base value to scale.
 * @return The scaled dimension.
 * [PT] Cria uma dimensão de jogo com escalonamento vertical.
 * @param value O valor base a ser escalonado.
 * @return A dimensão escalonada.
 */
public func gameVertical(_ value: Float) -> Float {
    return AppDimensGames.vertical(value)
}

/**
 * [EN] Creates a game dimension with aspect ratio scaling.
 * @param value The base value to scale.
 * @return The scaled dimension.
 * [PT] Cria uma dimensão de jogo com escalonamento de proporção.
 * @param value O valor base a ser escalonado.
 * @return A dimensão escalonada.
 */
public func gameAspectRatio(_ value: Float) -> Float {
    return AppDimensGames.aspectRatio(value)
}

/**
 * [EN] Creates a game dimension with viewport scaling.
 * @param value The base value to scale.
 * @return The scaled dimension.
 * [PT] Cria uma dimensão de jogo com escalonamento de viewport.
 * @param value O valor base a ser escalonado.
 * @return A dimensão escalonada.
 */
public func gameViewport(_ value: Float) -> Float {
    return AppDimensGames.viewport(value)
}

// MARK: - Metal Integration Helpers

/**
 * [EN] Helper function to initialize AppDimens Games with a Metal device.
 * @param device The Metal device.
 * @param viewport The viewport configuration.
 * [PT] Função auxiliar para inicializar AppDimens Games com um dispositivo Metal.
 * @param device O dispositivo Metal.
 * @param viewport A configuração do viewport.
 */
public func initializeAppDimensGames(device: MTLDevice, viewport: MTLViewport) {
    AppDimensGames.shared.initialize(device: device, viewport: viewport)
}

/**
 * [EN] Helper function to initialize AppDimens Games with screen dimensions.
 * @param device The Metal device.
 * @param width The viewport width.
 * @param height The viewport height.
 * [PT] Função auxiliar para inicializar AppDimens Games com dimensões de tela.
 * @param device O dispositivo Metal.
 * @param width A largura do viewport.
 * @param height A altura do viewport.
 */
public func initializeAppDimensGames(device: MTLDevice, width: Float, height: Float) {
    AppDimensGames.shared.initialize(device: device, width: width, height: height)
}

// MARK: - Module Information

/**
 * [EN] Information about the AppDimens Games module.
 * [PT] Informações sobre o módulo AppDimens Games.
 */
public struct AppDimensGamesInfo {
    public static let version = "1.0.5"
    public static let moduleName = "AppDimensGames"
    public static let description = "Metal-specific functionality for game development with AppDimens"
    
    /**
     * [EN] Gets the module information as a dictionary.
     * @return A dictionary containing module information.
     * [PT] Obtém as informações do módulo como um dicionário.
     * @return Um dicionário contendo informações do módulo.
     */
    public static func info() -> [String: String] {
        return [
            "version": version,
            "moduleName": moduleName,
            "description": description
        ]
    }
}
