/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Metal/Games Module
 *
 * Description:
 * Game-specific extensions for Metal, MetalKit, and game development frameworks.
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

// MARK: - Float Extensions for Game Dimensions

public extension Float {
    
    /**
     * [EN] Creates a game dimension with uniform scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento uniforme.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameUniform(_ metalManager: AppDimensMetal) -> Float {
        return metalManager.gameDimension(self, scalingMode: .uniform)
    }
    
    /**
     * [EN] Creates a game dimension with horizontal scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento horizontal.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameHorizontal(_ metalManager: AppDimensMetal) -> Float {
        return metalManager.gameDimension(self, scalingMode: .horizontal)
    }
    
    /**
     * [EN] Creates a game dimension with vertical scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento vertical.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameVertical(_ metalManager: AppDimensMetal) -> Float {
        return metalManager.gameDimension(self, scalingMode: .vertical)
    }
    
    /**
     * [EN] Creates a game dimension with aspect ratio scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento de proporção.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameAspectRatio(_ metalManager: AppDimensMetal) -> Float {
        return metalManager.gameDimension(self, scalingMode: .aspectRatio)
    }
    
    /**
     * [EN] Creates a game dimension with viewport scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento de viewport.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameViewport(_ metalManager: AppDimensMetal) -> Float {
        return metalManager.gameDimension(self, scalingMode: .viewport)
    }
    
    /**
     * [EN] Creates a game font size with appropriate scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled font size.
     * [PT] Cria um tamanho de fonte de jogo com escalonamento apropriado.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O tamanho da fonte escalonado.
     */
    func gameFontSize(_ metalManager: AppDimensMetal) -> Float {
        return metalManager.gameFontSize(self)
    }
    
    /**
     * [EN] Creates a game spacing with appropriate scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled spacing.
     * [PT] Cria um espaçamento de jogo com escalonamento apropriado.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O espaçamento escalonado.
     */
    func gameSpacing(_ metalManager: AppDimensMetal) -> Float {
        return metalManager.gameSpacing(self)
    }
    
    /**
     * [EN] Creates a game size with appropriate scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled size.
     * [PT] Cria um tamanho de jogo com escalonamento apropriado.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O tamanho escalonado.
     */
    func gameSize(_ metalManager: AppDimensMetal) -> Float {
        return metalManager.gameSize(self)
    }
}

// MARK: - CGFloat Extensions for Game Dimensions

public extension CGFloat {
    
    /**
     * [EN] Creates a game dimension with uniform scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento uniforme.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameUniform(_ metalManager: AppDimensMetal) -> CGFloat {
        return CGFloat(metalManager.gameDimension(Float(self), scalingMode: .uniform))
    }
    
    /**
     * [EN] Creates a game dimension with horizontal scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento horizontal.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameHorizontal(_ metalManager: AppDimensMetal) -> CGFloat {
        return CGFloat(metalManager.gameDimension(Float(self), scalingMode: .horizontal))
    }
    
    /**
     * [EN] Creates a game dimension with vertical scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento vertical.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameVertical(_ metalManager: AppDimensMetal) -> CGFloat {
        return CGFloat(metalManager.gameDimension(Float(self), scalingMode: .vertical))
    }
    
    /**
     * [EN] Creates a game dimension with aspect ratio scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento de proporção.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameAspectRatio(_ metalManager: AppDimensMetal) -> CGFloat {
        return CGFloat(metalManager.gameDimension(Float(self), scalingMode: .aspectRatio))
    }
    
    /**
     * [EN] Creates a game dimension with viewport scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension.
     * [PT] Cria uma dimensão de jogo com escalonamento de viewport.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return A dimensão escalonada.
     */
    func gameViewport(_ metalManager: AppDimensMetal) -> CGFloat {
        return CGFloat(metalManager.gameDimension(Float(self), scalingMode: .viewport))
    }
}

// MARK: - simd_float2 Extensions for Game Dimensions

public extension simd_float2 {
    
    /**
     * [EN] Creates a game dimension vector with uniform scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension vector.
     * [PT] Cria um vetor de dimensão de jogo com escalonamento uniforme.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O vetor de dimensão escalonado.
     */
    func gameUniform(_ metalManager: AppDimensMetal) -> simd_float2 {
        return simd_float2(
            metalManager.gameDimension(x, scalingMode: .uniform),
            metalManager.gameDimension(y, scalingMode: .uniform)
        )
    }
    
    /**
     * [EN] Creates a game dimension vector with horizontal scaling for X and vertical for Y.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension vector.
     * [PT] Cria um vetor de dimensão de jogo com escalonamento horizontal para X e vertical para Y.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O vetor de dimensão escalonado.
     */
    func gameAxisAligned(_ metalManager: AppDimensMetal) -> simd_float2 {
        return simd_float2(
            metalManager.gameDimension(x, scalingMode: .horizontal),
            metalManager.gameDimension(y, scalingMode: .vertical)
        )
    }
    
    /**
     * [EN] Creates a game dimension vector with viewport scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension vector.
     * [PT] Cria um vetor de dimensão de jogo com escalonamento de viewport.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O vetor de dimensão escalonado.
     */
    func gameViewport(_ metalManager: AppDimensMetal) -> simd_float2 {
        return simd_float2(
            metalManager.gameDimension(x, scalingMode: .viewport),
            metalManager.gameDimension(y, scalingMode: .viewport)
        )
    }
    
    /**
     * [EN] Converts this vector to normalized device coordinates.
     * @param metalManager The Metal dimension manager.
     * @return The NDC coordinates.
     * [PT] Converte este vetor para coordenadas normalizadas do dispositivo.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return As coordenadas NDC.
     */
    func toNDC(_ metalManager: AppDimensMetal) -> simd_float2 {
        return metalManager.screenToNDC(self)
    }
    
    /**
     * [EN] Converts this vector from normalized device coordinates to screen coordinates.
     * @param metalManager The Metal dimension manager.
     * @return The screen coordinates.
     * [PT] Converte este vetor de coordenadas normalizadas do dispositivo para coordenadas de tela.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return As coordenadas de tela.
     */
    func fromNDC(_ metalManager: AppDimensMetal) -> simd_float2 {
        return metalManager.ndcToScreen(self)
    }
}

// MARK: - simd_float3 Extensions for Game Dimensions

public extension simd_float3 {
    
    /**
     * [EN] Creates a game dimension vector with uniform scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension vector.
     * [PT] Cria um vetor de dimensão de jogo com escalonamento uniforme.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O vetor de dimensão escalonado.
     */
    func gameUniform(_ metalManager: AppDimensMetal) -> simd_float3 {
        return simd_float3(
            metalManager.gameDimension(x, scalingMode: .uniform),
            metalManager.gameDimension(y, scalingMode: .uniform),
            metalManager.gameDimension(z, scalingMode: .uniform)
        )
    }
    
    /**
     * [EN] Creates a game dimension vector with viewport scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension vector.
     * [PT] Cria um vetor de dimensão de jogo com escalonamento de viewport.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O vetor de dimensão escalonado.
     */
    func gameViewport(_ metalManager: AppDimensMetal) -> simd_float3 {
        return simd_float3(
            metalManager.gameDimension(x, scalingMode: .viewport),
            metalManager.gameDimension(y, scalingMode: .viewport),
            metalManager.gameDimension(z, scalingMode: .viewport)
        )
    }
}

// MARK: - simd_float4 Extensions for Game Dimensions

public extension simd_float4 {
    
    /**
     * [EN] Creates a game dimension vector with uniform scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension vector.
     * [PT] Cria um vetor de dimensão de jogo com escalonamento uniforme.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O vetor de dimensão escalonado.
     */
    func gameUniform(_ metalManager: AppDimensMetal) -> simd_float4 {
        return simd_float4(
            metalManager.gameDimension(x, scalingMode: .uniform),
            metalManager.gameDimension(y, scalingMode: .uniform),
            metalManager.gameDimension(z, scalingMode: .uniform),
            metalManager.gameDimension(w, scalingMode: .uniform)
        )
    }
    
    /**
     * [EN] Creates a game dimension vector with viewport scaling.
     * @param metalManager The Metal dimension manager.
     * @return The scaled dimension vector.
     * [PT] Cria um vetor de dimensão de jogo com escalonamento de viewport.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return O vetor de dimensão escalonado.
     */
    func gameViewport(_ metalManager: AppDimensMetal) -> simd_float4 {
        return simd_float4(
            metalManager.gameDimension(x, scalingMode: .viewport),
            metalManager.gameDimension(y, scalingMode: .viewport),
            metalManager.gameDimension(z, scalingMode: .viewport),
            metalManager.gameDimension(w, scalingMode: .viewport)
        )
    }
}

// MARK: - MTLViewport Extensions

public extension MTLViewport {
    
    /**
     * [EN] Creates a scaled viewport based on the Metal dimension manager.
     * @param metalManager The Metal dimension manager.
     * @return A scaled Metal viewport.
     * [PT] Cria um viewport escalonado baseado no gerenciador de dimensões Metal.
     * @param metalManager O gerenciador de dimensões Metal.
     * @return Um viewport Metal escalonado.
     */
    func gameScaled(_ metalManager: AppDimensMetal) -> MTLViewport {
        return metalManager.createScaledViewport(from: self)
    }
    
    /**
     * [EN] Gets the aspect ratio of this viewport.
     * @return The aspect ratio.
     * [PT] Obtém a proporção deste viewport.
     * @return A proporção.
     */
    var aspectRatio: Float {
        return Float(width / height)
    }
    
    /**
     * [EN] Gets the center point of this viewport.
     * @return The center point.
     * [PT] Obtém o ponto central deste viewport.
     * @return O ponto central.
     */
    var center: simd_float2 {
        return simd_float2(
            Float(originX + width / 2),
            Float(originY + height / 2)
        )
    }
    
    /**
     * [EN] Gets the size of this viewport as a vector.
     * @return The viewport size.
     * [PT] Obtém o tamanho deste viewport como um vetor.
     * @return O tamanho do viewport.
     */
    var size: simd_float2 {
        return simd_float2(Float(width), Float(height))
    }
}
