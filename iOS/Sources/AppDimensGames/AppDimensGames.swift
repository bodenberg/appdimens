/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Metal/Games Module
 *
 * Description:
 * Main entry point for the AppDimens Games module, providing convenient
 * access to game-specific dimension management functionality.
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

/**
 * [EN] Main entry point for AppDimens Games functionality.
 * Provides convenient access to game-specific dimension management.
 * [PT] Ponto de entrada principal para funcionalidade AppDimens Games.
 * Fornece acesso conveniente ao gerenciamento de dimensões específico para jogos.
 */
public class AppDimensGames {
    
    // MARK: - Singleton
    
    public static let shared = AppDimensGames()
    
    // MARK: - Lazy Properties for Performance
    
    private lazy var metalManager: AppDimensMetal? = {
        // Only initialize when actually needed
        guard let device = self.currentDevice, let viewport = self.currentViewport else {
            return nil
        }
        return AppDimensMetal(device: device, viewport: viewport)
    }()
    
    private lazy var autoCache: AppDimensAutoCache = {
        return AppDimensAutoCache()
    }()
    
    private lazy var performanceSettings: GamePerformanceSettings = {
        return GamePerformanceSettings.default
    }()
    
    // MARK: - Private Properties
    
    private var currentDevice: MTLDevice?
    private var currentViewport: MTLViewport?
    
    private init() {}
    
    // MARK: - Initialization
    
    /**
     * [EN] Initializes the games module with a Metal device and viewport.
     * @param device The Metal device to use.
     * @param viewport The initial viewport configuration.
     * [PT] Inicializa o módulo de jogos com um dispositivo Metal e viewport.
     * @param device O dispositivo Metal a ser usado.
     * @param viewport A configuração inicial do viewport.
     */
    public func initialize(device: MTLDevice, viewport: MTLViewport) {
        self.currentDevice = device
        self.currentViewport = viewport
        // metalManager will be lazily initialized when first accessed
    }
    
    /**
     * [EN] Initializes the games module with a Metal device and screen dimensions.
     * @param device The Metal device to use.
     * @param width The viewport width.
     * @param height The viewport height.
     * [PT] Inicializa o módulo de jogos com um dispositivo Metal e dimensões de tela.
     * @param device O dispositivo Metal a ser usado.
     * @param width A largura do viewport.
     * @param height A altura do viewport.
     */
    public func initialize(device: MTLDevice, width: Float, height: Float) {
        let viewport = MTLViewport(
            originX: 0, originY: 0,
            width: Double(width),
            height: Double(height),
            znear: 0.0, zfar: 1.0
        )
        initialize(device: device, viewport: viewport)
    }
    
    // MARK: - Convenience Methods
    
    /**
     * [EN] Gets the Metal dimension manager.
     * @return The Metal manager, or nil if not initialized.
     * [PT] Obtém o gerenciador de dimensões Metal.
     * @return O gerenciador Metal, ou nil se não inicializado.
     */
    public func getMetalManager() -> AppDimensMetal? {
        return metalManager
    }
    
    /**
     * [EN] Updates the viewport configuration.
     * @param viewport The new viewport configuration.
     * [PT] Atualiza a configuração do viewport.
     * @param viewport A nova configuração do viewport.
     */
    public func updateViewport(_ viewport: MTLViewport) {
        self.currentViewport = viewport
        // Clear cache when viewport changes
        autoCache.clearAll()
        metalManager?.updateViewport(viewport)
    }
    
    /**
     * [EN] Updates the viewport with new dimensions.
     * @param width The new viewport width.
     * @param height The new viewport height.
     * [PT] Atualiza o viewport com novas dimensões.
     * @param width A nova largura do viewport.
     * @param height A nova altura do viewport.
     */
    public func updateViewport(width: Float, height: Float) {
        let viewport = MTLViewport(
            originX: 0, originY: 0,
            width: Double(width),
            height: Double(height),
            znear: 0.0, zfar: 1.0
        )
        updateViewport(viewport)
    }
    
    // MARK: - Game Dimension Calculations
    
    /**
     * [EN] Calculates a game dimension with uniform scaling.
     * @param baseValue The base value to scale.
     * @return The scaled dimension, or the base value if not initialized.
     * [PT] Calcula uma dimensão de jogo com escalonamento uniforme.
     * @param baseValue O valor base a ser escalonado.
     * @return A dimensão escalonada, ou o valor base se não inicializado.
     */
    public func uniform(_ baseValue: Float) -> Float {
        guard let viewport = currentViewport else { return baseValue }
        
        let dependencies: Set<AnyHashable> = [
            baseValue, viewport.width, viewport.height, "uniform"
        ]
        
        return autoCache.remember(
            key: "uniform_\(baseValue)_\(viewport.width)_\(viewport.height)",
            dependencies: dependencies
        ) {
            metalManager?.gameDimension(baseValue, scalingMode: .uniform) ?? baseValue
        }
    }
    
    /**
     * [EN] Calculates a game dimension with horizontal scaling.
     * @param baseValue The base value to scale.
     * @return The scaled dimension, or the base value if not initialized.
     * [PT] Calcula uma dimensão de jogo com escalonamento horizontal.
     * @param baseValue O valor base a ser escalonado.
     * @return A dimensão escalonada, ou o valor base se não inicializado.
     */
    public func horizontal(_ baseValue: Float) -> Float {
        guard let viewport = currentViewport else { return baseValue }
        
        let dependencies: Set<AnyHashable> = [
            baseValue, viewport.width, viewport.height, "horizontal"
        ]
        
        return autoCache.remember(
            key: "horizontal_\(baseValue)_\(viewport.width)_\(viewport.height)",
            dependencies: dependencies
        ) {
            metalManager?.gameDimension(baseValue, scalingMode: .horizontal) ?? baseValue
        }
    }
    
    /**
     * [EN] Calculates a game dimension with vertical scaling.
     * @param baseValue The base value to scale.
     * @return The scaled dimension, or the base value if not initialized.
     * [PT] Calcula uma dimensão de jogo com escalonamento vertical.
     * @param baseValue O valor base a ser escalonado.
     * @return A dimensão escalonada, ou o valor base se não inicializado.
     */
    public func vertical(_ baseValue: Float) -> Float {
        guard let viewport = currentViewport else { return baseValue }
        
        let dependencies: Set<AnyHashable> = [
            baseValue, viewport.width, viewport.height, "vertical"
        ]
        
        return autoCache.remember(
            key: "vertical_\(baseValue)_\(viewport.width)_\(viewport.height)",
            dependencies: dependencies
        ) {
            metalManager?.gameDimension(baseValue, scalingMode: .vertical) ?? baseValue
        }
    }
    
    /**
     * [EN] Calculates a game dimension with aspect ratio scaling.
     * @param baseValue The base value to scale.
     * @return The scaled dimension, or the base value if not initialized.
     * [PT] Calcula uma dimensão de jogo com escalonamento de proporção.
     * @param baseValue O valor base a ser escalonado.
     * @return A dimensão escalonada, ou o valor base se não inicializado.
     */
    public func aspectRatio(_ baseValue: Float) -> Float {
        guard let viewport = currentViewport else { return baseValue }
        
        let dependencies: Set<AnyHashable> = [
            baseValue, viewport.width, viewport.height, "aspectRatio"
        ]
        
        return autoCache.remember(
            key: "aspectRatio_\(baseValue)_\(viewport.width)_\(viewport.height)",
            dependencies: dependencies
        ) {
            metalManager?.gameDimension(baseValue, scalingMode: .aspectRatio) ?? baseValue
        }
    }
    
    // MARK: - Game-Specific Calculations (Android API Compatibility)
    
    /**
     * [EN] Calculates responsive button size for game UI.
     * Uses BALANCED strategy by default (linear on phones, logarithmic on tablets).
     * 
     * [PT] Calcula tamanho de botão responsivo para UI de jogo.
     * Usa estratégia BALANCED por padrão (linear em phones, logarítmico em tablets).
     * 
     * @param baseSize The base button size.
     * @return The scaled button size.
     * 
     * @example
     * ```swift
     * let buttonSize = AppDimensGames.shared.calculateButtonSize(48)
     * // Returns appropriate size for current device (e.g., 48pt on phone, 56pt on tablet)
     * ```
     */
    public func calculateButtonSize(_ baseSize: Float) -> Float {
        // Buttons use BALANCED strategy for optimal UI consistency
        return uniform(baseSize)
    }
    
    /**
     * [EN] Calculates responsive text size for game UI.
     * Uses FLUID strategy for smooth text scaling across devices.
     * 
     * [PT] Calcula tamanho de texto responsivo para UI de jogo.
     * Usa estratégia FLUID para escalonamento suave de texto entre dispositivos.
     * 
     * @param baseSize The base text size.
     * @return The scaled text size.
     * 
     * @example
     * ```swift
     * let textSize = AppDimensGames.shared.calculateTextSize(16)
     * // Returns appropriate text size for current device
     * ```
     */
    public func calculateTextSize(_ baseSize: Float) -> Float {
        // Text uses uniform scaling for readability
        return uniform(baseSize)
    }
    
    /**
     * [EN] Calculates responsive player size for game world.
     * Uses BALANCED strategy for natural player scaling.
     * 
     * [PT] Calcula tamanho de jogador responsivo para mundo do jogo.
     * Usa estratégia BALANCED para escalonamento natural do jogador.
     * 
     * @param baseSize The base player size.
     * @return The scaled player size.
     * 
     * @example
     * ```swift
     * let playerSize = AppDimensGames.shared.calculatePlayerSize(64)
     * // Returns appropriate player size for current device
     * ```
     */
    public func calculatePlayerSize(_ baseSize: Float) -> Float {
        // Player uses uniform scaling for consistent game feel
        return uniform(baseSize)
    }
    
    /**
     * [EN] Calculates responsive enemy size for game world.
     * Uses BALANCED strategy for consistent enemy scaling.
     * 
     * [PT] Calcula tamanho de inimigo responsivo para mundo do jogo.
     * Usa estratégia BALANCED para escalonamento consistente de inimigos.
     * 
     * @param baseSize The base enemy size.
     * @return The scaled enemy size.
     * 
     * @example
     * ```swift
     * let enemySize = AppDimensGames.shared.calculateEnemySize(32)
     * // Returns appropriate enemy size for current device
     * ```
     */
    public func calculateEnemySize(_ baseSize: Float) -> Float {
        // Enemies use uniform scaling for consistent game balance
        return uniform(baseSize)
    }
    
    /**
     * [EN] Calculates responsive UI overlay size (HUD elements).
     * Uses DEFAULT strategy for stable UI overlay elements.
     * 
     * [PT] Calcula tamanho de overlay de UI responsivo (elementos HUD).
     * Usa estratégia DEFAULT para elementos de overlay de UI estáveis.
     * 
     * @param baseSize The base UI overlay size.
     * @return The scaled UI overlay size.
     * 
     * @example
     * ```swift
     * let hudSize = AppDimensGames.shared.calculateUIOverlaySize(24)
     * // Returns appropriate HUD element size for current device
     * ```
     */
    public func calculateUIOverlaySize(_ baseSize: Float) -> Float {
        // UI overlays use uniform scaling for consistent HUD
        return uniform(baseSize)
    }
    
    /**
     * [EN] Calculates game dimension with specific scaling type.
     * Compatible with Android API for cross-platform consistency.
     * 
     * [PT] Calcula dimensão de jogo com tipo de escalonamento específico.
     * Compatível com API Android para consistência cross-platform.
     * 
     * @param baseValue The base value to scale.
     * @param type The game dimension type (DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY).
     * @return The scaled dimension.
     * 
     * @example
     * ```swift
     * // Different scaling types
     * let dynamicSize = AppDimensGames.shared.calculateDimension(100, type: .dynamic)
     * let fixedSize = AppDimensGames.shared.calculateDimension(100, type: .fixed)
     * let worldSize = AppDimensGames.shared.calculateDimension(100, type: .gameWorld)
     * let overlaySize = AppDimensGames.shared.calculateDimension(100, type: .uiOverlay)
     * ```
     */
    public func calculateDimension(_ baseValue: Float, type: GameDimensionType = .balanced) -> Float {
        switch type {
        case .dynamic:
            return horizontal(baseValue)  // Proportional scaling
        case .fixed:
            return uniform(baseValue)     // Logarithmic scaling
        case .gameWorld:
            return uniform(baseValue)     // Consistent game world scaling
        case .uiOverlay:
            return uniform(baseValue)     // Stable UI overlay scaling
        case .balanced:
            return uniform(baseValue)     // Balanced scaling (default)
        }
    }
    
    // MARK: - Vector and Rectangle Calculations (Android API Compatibility)
    
    /**
     * [EN] Calculates a scaled 2D vector for game world coordinates.
     * [PT] Calcula um vetor 2D escalonado para coordenadas do mundo do jogo.
     * 
     * @param baseVector The base vector to scale.
     * @param type The scaling type to use.
     * @return The scaled vector.
     */
    public func calculateVector2D(_ baseVector: simd_float2, type: GameDimensionType = .gameWorld) -> simd_float2 {
        let scaledX = calculateDimension(baseVector.x, type: type)
        let scaledY = calculateDimension(baseVector.y, type: type)
        return simd_float2(scaledX, scaledY)
    }
    
    /**
     * [EN] Calculates a scaled rectangle for game UI or world elements.
     * [PT] Calcula um retângulo escalonado para elementos de UI ou mundo do jogo.
     * 
     * @param baseRect The base rectangle to scale (x, y, width, height).
     * @param type The scaling type to use.
     * @return The scaled rectangle.
     */
    public func calculateRectangle(_ baseRect: (x: Float, y: Float, width: Float, height: Float), type: GameDimensionType = .gameWorld) -> (x: Float, y: Float, width: Float, height: Float) {
        return (
            x: calculateDimension(baseRect.x, type: type),
            y: calculateDimension(baseRect.y, type: type),
            width: calculateDimension(baseRect.width, type: type),
            height: calculateDimension(baseRect.height, type: type)
        )
    }
    
    /**
     * [EN] Calculates a game dimension with viewport scaling.
     * @param baseValue The base value to scale.
     * @return The scaled dimension, or the base value if not initialized.
     * [PT] Calcula uma dimensão de jogo com escalonamento de viewport.
     * @param baseValue O valor base a ser escalonado.
     * @return A dimensão escalonada, ou o valor base se não inicializado.
     */
    public func viewport(_ baseValue: Float) -> Float {
        guard let viewport = currentViewport else { return baseValue }
        
        let dependencies: Set<AnyHashable> = [
            baseValue, viewport.width, viewport.height, "viewport"
        ]
        
        return autoCache.remember(
            key: "viewport_\(baseValue)_\(viewport.width)_\(viewport.height)",
            dependencies: dependencies
        ) {
            metalManager?.gameDimension(baseValue, scalingMode: .viewport) ?? baseValue
        }
    }
    
    // MARK: - Performance Configuration
    
    /**
     * [EN] Configures performance settings for the games module.
     * @param settings The performance settings to apply.
     * [PT] Configura as configurações de performance para o módulo de jogos.
     * @param settings As configurações de performance a serem aplicadas.
     */
    public func configurePerformance(_ settings: GamePerformanceSettings) {
        performanceSettings = settings
        if !settings.enableCaching {
            autoCache.clearAll()
        }
    }
    
    /**
     * [EN] Gets the current performance settings.
     * @return The current performance settings.
     * [PT] Obtém as configurações de performance atuais.
     * @return As configurações de performance atuais.
     */
    public func getPerformanceSettings() -> GamePerformanceSettings {
        return performanceSettings
    }
    
    /**
     * [EN] Clears all cached calculations.
     * [PT] Limpa todos os cálculos em cache.
     */
    public func clearCache() {
        autoCache.clearAll()
    }
    
    // MARK: - Game-Specific Utilities
    
    /**
     * [EN] Calculates the appropriate font size for game UI elements.
     * @param baseFontSize The base font size.
     * @return The scaled font size, or the base value if not initialized.
     * [PT] Calcula o tamanho de fonte apropriado para elementos de UI do jogo.
     * @param baseFontSize O tamanho base da fonte.
     * @return O tamanho da fonte escalonado, ou o valor base se não inicializado.
     */
    public func fontSize(_ baseFontSize: Float) -> Float {
        return metalManager?.gameFontSize(baseFontSize) ?? baseFontSize
    }
    
    /**
     * [EN] Calculates the appropriate spacing for game UI elements.
     * @param baseSpacing The base spacing.
     * @return The scaled spacing, or the base value if not initialized.
     * [PT] Calcula o espaçamento apropriado para elementos de UI do jogo.
     * @param baseSpacing O espaçamento base.
     * @return O espaçamento escalonado, ou o valor base se não inicializado.
     */
    public func spacing(_ baseSpacing: Float) -> Float {
        return metalManager?.gameSpacing(baseSpacing) ?? baseSpacing
    }
    
    /**
     * [EN] Calculates the appropriate size for game UI elements.
     * @param baseSize The base size.
     * @return The scaled size, or the base value if not initialized.
     * [PT] Calcula o tamanho apropriado para elementos de UI do jogo.
     * @param baseSize O tamanho base.
     * @return O tamanho escalonado, ou o valor base se não inicializado.
     */
    public func size(_ baseSize: Float) -> Float {
        return metalManager?.gameSize(baseSize) ?? baseSize
    }
    
    // MARK: - Coordinate Conversion
    
    /**
     * [EN] Converts screen coordinates to normalized device coordinates.
     * @param screenPoint The point in screen coordinates.
     * @return The point in NDC coordinates, or the original point if not initialized.
     * [PT] Converte coordenadas de tela para coordenadas normalizadas do dispositivo.
     * @param screenPoint O ponto em coordenadas de tela.
     * @return O ponto em coordenadas NDC, ou o ponto original se não inicializado.
     */
    public func screenToNDC(_ screenPoint: simd_float2) -> simd_float2 {
        return metalManager?.screenToNDC(screenPoint) ?? screenPoint
    }
    
    /**
     * [EN] Converts normalized device coordinates to screen coordinates.
     * @param ndcPoint The point in NDC coordinates.
     * @return The point in screen coordinates, or the original point if not initialized.
     * [PT] Converte coordenadas normalizadas do dispositivo para coordenadas de tela.
     * @param ndcPoint O ponto em coordenadas NDC.
     * @return O ponto em coordenadas de tela, ou o ponto original se não inicializado.
     */
    public func ndcToScreen(_ ndcPoint: simd_float2) -> simd_float2 {
        return metalManager?.ndcToScreen(ndcPoint) ?? ndcPoint
    }
}

// MARK: - Static Convenience Methods

public extension AppDimensGames {
    
    /**
     * [EN] Static convenience method for uniform scaling.
     * @param value The value to scale.
     * @return The scaled value.
     * [PT] Método estático de conveniência para escalonamento uniforme.
     * @param value O valor a ser escalonado.
     * @return O valor escalonado.
     */
    static func uniform(_ value: Float) -> Float {
        return AppDimensGames.shared.uniform(value)
    }
    
    /**
     * [EN] Static convenience method for horizontal scaling.
     * @param value The value to scale.
     * @return The scaled value.
     * [PT] Método estático de conveniência para escalonamento horizontal.
     * @param value O valor a ser escalonado.
     * @return O valor escalonado.
     */
    static func horizontal(_ value: Float) -> Float {
        return AppDimensGames.shared.horizontal(value)
    }
    
    /**
     * [EN] Static convenience method for vertical scaling.
     * @param value The value to scale.
     * @return The scaled value.
     * [PT] Método estático de conveniência para escalonamento vertical.
     * @param value O valor a ser escalonado.
     * @return O valor escalonado.
     */
    static func vertical(_ value: Float) -> Float {
        return AppDimensGames.shared.vertical(value)
    }
    
    /**
     * [EN] Static convenience method for aspect ratio scaling.
     * @param value The value to scale.
     * @return The scaled value.
     * [PT] Método estático de conveniência para escalonamento de proporção.
     * @param value O valor a ser escalonado.
     * @return O valor escalonado.
     */
    static func aspectRatio(_ value: Float) -> Float {
        return AppDimensGames.shared.aspectRatio(value)
    }
    
    /**
     * [EN] Static convenience method for viewport scaling.
     * @param value The value to scale.
     * @return The scaled value.
     * [PT] Método estático de conveniência para escalonamento de viewport.
     * @param value O valor a ser escalonado.
     * @return O valor escalonado.
     */
    static func viewport(_ value: Float) -> Float {
        return AppDimensGames.shared.viewport(value)
    }
}
