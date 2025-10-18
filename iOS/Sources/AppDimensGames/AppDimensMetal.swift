/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Metal/Games Module
 *
 * Description:
 * The AppDimens Metal/Games module provides dimension management specifically
 * optimized for Metal rendering and game development, with support for
 * viewport-based scaling, aspect ratio handling, and game-specific units.
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

/**
 * [EN] Metal-specific dimension management for game development.
 * Provides viewport-based scaling, aspect ratio handling, and game-specific units.
 * [PT] Gerenciamento de dimensões específico para Metal no desenvolvimento de jogos.
 * Fornece escalonamento baseado em viewport, manipulação de proporção e unidades específicas para jogos.
 */
public class AppDimensMetal {
    
    // MARK: - Properties
    
    private let metalDevice: MTLDevice
    private var viewport: MTLViewport
    private var aspectRatio: Float
    private var baseViewport: MTLViewport
    
    // MARK: - Game-Specific Constants
    
    /// [EN] Base viewport width for reference calculations (1920x1080).
    /// [PT] Largura base do viewport para cálculos de referência (1920x1080).
    public static let BASE_VIEWPORT_WIDTH: Float = 1920.0
    
    /// [EN] Base viewport height for reference calculations (1920x1080).
    /// [PT] Altura base do viewport para cálculos de referência (1920x1080).
    public static let BASE_VIEWPORT_HEIGHT: Float = 1080.0
    
    /// [EN] Base aspect ratio for reference calculations.
    /// [PT] Proporção base para cálculos de referência.
    public static let BASE_ASPECT_RATIO: Float = BASE_VIEWPORT_WIDTH / BASE_VIEWPORT_HEIGHT
    
    // MARK: - Initialization
    
    /**
     * [EN] Initializes the Metal dimension manager with a Metal device.
     * @param device The Metal device to use for calculations.
     * @param viewport The initial viewport configuration.
     * [PT] Inicializa o gerenciador de dimensões Metal com um dispositivo Metal.
     * @param device O dispositivo Metal a ser usado para cálculos.
     * @param viewport A configuração inicial do viewport.
     */
    public init(device: MTLDevice, viewport: MTLViewport) {
        self.metalDevice = device
        self.viewport = viewport
        self.aspectRatio = viewport.width / viewport.height
        self.baseViewport = MTLViewport(
            originX: 0, originY: 0,
            width: Double(AppDimensMetal.BASE_VIEWPORT_WIDTH),
            height: Double(AppDimensMetal.BASE_VIEWPORT_HEIGHT),
            znear: 0.0, zfar: 1.0
        )
    }
    
    // MARK: - Viewport Management
    
    /**
     * [EN] Updates the viewport configuration.
     * @param newViewport The new viewport configuration.
     * [PT] Atualiza a configuração do viewport.
     * @param newViewport A nova configuração do viewport.
     */
    public func updateViewport(_ newViewport: MTLViewport) {
        self.viewport = newViewport
        self.aspectRatio = Float(newViewport.width / newViewport.height)
    }
    
    /**
     * [EN] Gets the current viewport configuration.
     * @return The current viewport.
     * [PT] Obtém a configuração atual do viewport.
     * @return O viewport atual.
     */
    public func getCurrentViewport() -> MTLViewport {
        return viewport
    }
    
    /**
     * [EN] Gets the current aspect ratio.
     * @return The current aspect ratio.
     * [PT] Obtém a proporção atual.
     * @return A proporção atual.
     */
    public func getCurrentAspectRatio() -> Float {
        return aspectRatio
    }
    
    // MARK: - Game Dimension Calculations
    
    /**
     * [EN] Calculates a game dimension based on viewport scaling.
     * @param baseValue The base value in game units.
     * @param scalingMode The scaling mode to use.
     * @return The scaled dimension.
     * [PT] Calcula uma dimensão de jogo baseada no escalonamento do viewport.
     * @param baseValue O valor base em unidades de jogo.
     * @param scalingMode O modo de escalonamento a ser usado.
     * @return A dimensão escalonada.
     */
    public func gameDimension(_ baseValue: Float, scalingMode: GameScalingMode = .uniform) -> Float {
        switch scalingMode {
        case .uniform:
            return uniformScale(baseValue)
        case .horizontal:
            return horizontalScale(baseValue)
        case .vertical:
            return verticalScale(baseValue)
        case .aspectRatio:
            return aspectRatioScale(baseValue)
        case .viewport:
            return viewportScale(baseValue)
        }
    }
    
    /**
     * [EN] Calculates uniform scaling based on the smallest viewport dimension.
     * @param baseValue The base value to scale.
     * @return The uniformly scaled value.
     * [PT] Calcula escalonamento uniforme baseado na menor dimensão do viewport.
     * @param baseValue O valor base a ser escalonado.
     * @return O valor escalonado uniformemente.
     */
    private func uniformScale(_ baseValue: Float) -> Float {
        let scaleFactor = min(viewport.width / baseViewport.width, viewport.height / baseViewport.height)
        return baseValue * scaleFactor
    }
    
    /**
     * [EN] Calculates horizontal scaling based on viewport width.
     * @param baseValue The base value to scale.
     * @return The horizontally scaled value.
     * [PT] Calcula escalonamento horizontal baseado na largura do viewport.
     * @param baseValue O valor base a ser escalonado.
     * @return O valor escalonado horizontalmente.
     */
    private func horizontalScale(_ baseValue: Float) -> Float {
        let scaleFactor = viewport.width / baseViewport.width
        return baseValue * scaleFactor
    }
    
    /**
     * [EN] Calculates vertical scaling based on viewport height.
     * @param baseValue The base value to scale.
     * @return The vertically scaled value.
     * [PT] Calcula escalonamento vertical baseado na altura do viewport.
     * @param baseValue O valor base a ser escalonado.
     * @return O valor escalonado verticalmente.
     */
    private func verticalScale(_ baseValue: Float) -> Float {
        let scaleFactor = viewport.height / baseViewport.height
        return baseValue * scaleFactor
    }
    
    /**
     * [EN] Calculates aspect ratio-aware scaling.
     * @param baseValue The base value to scale.
     * @return The aspect ratio-scaled value.
     * [PT] Calcula escalonamento consciente da proporção.
     * @param baseValue O valor base a ser escalonado.
     * @return O valor escalonado pela proporção.
     */
    private func aspectRatioScale(_ baseValue: Float) -> Float {
        let arAdjustment = log(aspectRatio / AppDimensMetal.BASE_ASPECT_RATIO) * 0.5
        let scaleFactor = min(viewport.width / baseViewport.width, viewport.height / baseViewport.height)
        return baseValue * scaleFactor * (1.0 + arAdjustment)
    }
    
    /**
     * [EN] Calculates viewport-based scaling using the diagonal.
     * @param baseValue The base value to scale.
     * @return The viewport-scaled value.
     * [PT] Calcula escalonamento baseado no viewport usando a diagonal.
     * @param baseValue O valor base a ser escalonado.
     * @return O valor escalonado pelo viewport.
     */
    private func viewportScale(_ baseValue: Float) -> Float {
        let currentDiagonal = sqrt(viewport.width * viewport.width + viewport.height * viewport.height)
        let baseDiagonal = sqrt(baseViewport.width * baseViewport.width + baseViewport.height * baseViewport.height)
        let scaleFactor = currentDiagonal / baseDiagonal
        return baseValue * scaleFactor
    }
    
    // MARK: - Game-Specific Utilities
    
    /**
     * [EN] Converts screen coordinates to normalized device coordinates (NDC).
     * @param screenPoint The point in screen coordinates.
     * @return The point in NDC coordinates.
     * [PT] Converte coordenadas de tela para coordenadas normalizadas do dispositivo (NDC).
     * @param screenPoint O ponto em coordenadas de tela.
     * @return O ponto em coordenadas NDC.
     */
    public func screenToNDC(_ screenPoint: simd_float2) -> simd_float2 {
        let ndcX = (screenPoint.x / Float(viewport.width)) * 2.0 - 1.0
        let ndcY = 1.0 - (screenPoint.y / Float(viewport.height)) * 2.0
        return simd_float2(ndcX, ndcY)
    }
    
    /**
     * [EN] Converts normalized device coordinates to screen coordinates.
     * @param ndcPoint The point in NDC coordinates.
     * @return The point in screen coordinates.
     * [PT] Converte coordenadas normalizadas do dispositivo para coordenadas de tela.
     * @param ndcPoint O ponto em coordenadas NDC.
     * @return O ponto em coordenadas de tela.
     */
    public func ndcToScreen(_ ndcPoint: simd_float2) -> simd_float2 {
        let screenX = (ndcPoint.x + 1.0) * 0.5 * Float(viewport.width)
        let screenY = (1.0 - ndcPoint.y) * 0.5 * Float(viewport.height)
        return simd_float2(screenX, screenY)
    }
    
    /**
     * [EN] Calculates the appropriate font size for game UI elements.
     * @param baseFontSize The base font size in game units.
     * @param scalingMode The scaling mode to use.
     * @return The scaled font size.
     * [PT] Calcula o tamanho de fonte apropriado para elementos de UI do jogo.
     * @param baseFontSize O tamanho base da fonte em unidades de jogo.
     * @param scalingMode O modo de escalonamento a ser usado.
     * @return O tamanho da fonte escalonado.
     */
    public func gameFontSize(_ baseFontSize: Float, scalingMode: GameScalingMode = .uniform) -> Float {
        return gameDimension(baseFontSize, scalingMode: scalingMode)
    }
    
    /**
     * [EN] Calculates the appropriate spacing for game UI elements.
     * @param baseSpacing The base spacing in game units.
     * @param scalingMode The scaling mode to use.
     * @return The scaled spacing.
     * [PT] Calcula o espaçamento apropriado para elementos de UI do jogo.
     * @param baseSpacing O espaçamento base em unidades de jogo.
     * @param scalingMode O modo de escalonamento a ser usado.
     * @return O espaçamento escalonado.
     */
    public func gameSpacing(_ baseSpacing: Float, scalingMode: GameScalingMode = .uniform) -> Float {
        return gameDimension(baseSpacing, scalingMode: scalingMode)
    }
    
    /**
     * [EN] Calculates the appropriate size for game UI elements.
     * @param baseSize The base size in game units.
     * @param scalingMode The scaling mode to use.
     * @return The scaled size.
     * [PT] Calcula o tamanho apropriado para elementos de UI do jogo.
     * @param baseSize O tamanho base em unidades de jogo.
     * @param scalingMode O modo de escalonamento a ser usado.
     * @return O tamanho escalonado.
     */
    public func gameSize(_ baseSize: Float, scalingMode: GameScalingMode = .uniform) -> Float {
        return gameDimension(baseSize, scalingMode: scalingMode)
    }
}

// MARK: - Game Scaling Modes

/**
 * [EN] Defines the different scaling modes available for game dimensions.
 * [PT] Define os diferentes modos de escalonamento disponíveis para dimensões de jogo.
 */
public enum GameScalingMode {
    /// [EN] Uniform scaling based on the smallest viewport dimension.
    /// [PT] Escalonamento uniforme baseado na menor dimensão do viewport.
    case uniform
    
    /// [EN] Horizontal scaling based on viewport width.
    /// [PT] Escalonamento horizontal baseado na largura do viewport.
    case horizontal
    
    /// [EN] Vertical scaling based on viewport height.
    /// [PT] Escalonamento vertical baseado na altura do viewport.
    case vertical
    
    /// [EN] Aspect ratio-aware scaling with logarithmic adjustment.
    /// [PT] Escalonamento consciente da proporção com ajuste logarítmico.
    case aspectRatio
    
    /// [EN] Viewport-based scaling using the diagonal.
    /// [PT] Escalonamento baseado no viewport usando a diagonal.
    case viewport
}

// MARK: - Metal Extensions

public extension AppDimensMetal {
    
    /**
     * [EN] Creates a Metal viewport with proper scaling for the current screen.
     * @param baseViewport The base viewport configuration.
     * @return A scaled Metal viewport.
     * [PT] Cria um viewport Metal com escalonamento apropriado para a tela atual.
     * @param baseViewport A configuração base do viewport.
     * @return Um viewport Metal escalonado.
     */
    func createScaledViewport(from baseViewport: MTLViewport) -> MTLViewport {
        let scaleFactor = min(viewport.width / Float(baseViewport.width), viewport.height / Float(baseViewport.height))
        
        return MTLViewport(
            originX: Double(viewport.originX),
            originY: Double(viewport.originY),
            width: Double(Float(baseViewport.width) * scaleFactor),
            height: Double(Float(baseViewport.height) * scaleFactor),
            znear: baseViewport.znear,
            zfar: baseViewport.zfar
        )
    }
    
    /**
     * [EN] Calculates the appropriate render target size for the current viewport.
     * @param baseSize The base render target size.
     * @return The scaled render target size.
     * [PT] Calcula o tamanho apropriado do render target para o viewport atual.
     * @param baseSize O tamanho base do render target.
     * @return O tamanho escalonado do render target.
     */
    func calculateRenderTargetSize(_ baseSize: simd_float2) -> simd_float2 {
        let scaleFactor = min(viewport.width / baseSize.x, viewport.height / baseSize.y)
        return baseSize * scaleFactor
    }
}
