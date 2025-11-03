/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Scaling Strategies (iOS)
 *
 * Description:
 * Unified enum defining all available scaling strategies for AppDimens Games iOS.
 * Extends the base UI element types with game-specific categories and recommendations.
 *
 * Version 2.0 introduces 13 different scaling strategies optimized for game development.
 *
 * Licensed under the Apache License, Version 2.0
 */

import Foundation

/**
 * [EN] Enum representing all available scaling strategies in AppDimens Games 2.0 for iOS.
 * 
 * [PT] Enum representando todas as estratégias de escalonamento disponíveis no AppDimens Games 2.0 para iOS.
 *
 * Available strategies:
 * - DEFAULT: Fixed legacy (~97% linear + AR adjustment) - Ideal for UI elements
 * - PERCENTAGE: Dynamic legacy (100% linear, proportional) - Ideal for containers
 * - BALANCED: Perceptual Hybrid (linear phones, log tablets) - RECOMMENDED for most cases
 * - LOGARITHMIC: Perceptual Weber-Fechner (pure log) - Maximum control on large screens
 * - POWER: Perceptual Stevens (power law) - Configurable scaling
 * - FLUID: CSS clamp-like interpolation with breakpoints - Typography and bounded spacing
 * - INTERPOLATED: Moderated linear interpolation (50%) - Balanced growth
 * - DIAGONAL: Scale based on diagonal (screen size) - True screen size scaling
 * - PERIMETER: Scale based on perimeter (W+H) - Balanced W+H scaling
 * - FIT: Letterbox scaling (min ratio) - Game fit content
 * - FILL: Cover scaling (max ratio) - Game fill/backgrounds
 * - AUTOSIZE: Auto-adjust based on container size - Dynamic content fitting
 * - NONE: No scaling (constant size) - Fixed elements
 */
public enum GameScalingStrategy {
    /// DEFAULT - Fixed legacy (~97% linear + aspect ratio adjustment)
    /// Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
    /// Best for: HUD elements, buttons, icons, fixed UI components
    case `default`
    
    /// PERCENTAGE - Dynamic legacy (100% proportional)
    /// Formula: f(x) = x × (W / W₀)
    /// Best for: Containers, layouts, full-screen elements
    case percentage
    
    /// BALANCED - Perceptual Hybrid (RECOMMENDED for games)
    /// Formula: Linear on phones (< 480pt), logarithmic on tablets/TVs
    /// Best for: Player characters, enemies, game objects, projectiles
    case balanced
    
    /// LOGARITHMIC - Perceptual Weber-Fechner (maximum control)
    /// Formula: f(x) = x × (1 + sensitivity × ln(W / W₀))
    /// Best for: TV games, large screen optimizations, subtle elements
    case logarithmic
    
    /// POWER - Perceptual Stevens (scientific, configurable)
    /// Formula: f(x) = x × (W / W₀)^exponent
    /// Best for: Configurable games, custom scaling needs
    case power
    
    /// FLUID - CSS clamp-like interpolation
    /// Smooth interpolation between min/max values
    /// Best for: Game text, dialogue, score displays, HUD text
    case fluid
    
    /// INTERPOLATED - Moderated linear interpolation
    /// Formula: f(x) = x + ((x × W/W₀) - x) × 0.5
    /// Best for: Secondary objects, background elements, decorative items
    case interpolated
    
    /// DIAGONAL - Scale based on diagonal (true screen size)
    /// Formula: f(x) = x × √(W² + H²) / √(W₀² + H₀²)
    /// Best for: Touch targets, gesture areas, physical interaction zones
    case diagonal
    
    /// PERIMETER - Scale based on perimeter
    /// Formula: f(x) = x × (W + H) / (W₀ + H₀)
    /// Best for: General game elements, balanced world objects
    case perimeter
    
    /// FIT - Letterbox scaling (game fit)
    /// Formula: f(x) = x × min(W/W₀, H/H₀)
    /// Best for: Full viewport (letterbox), puzzle games, strategy games
    case fit
    
    /// FILL - Cover scaling (game fill)
    /// Formula: f(x) = x × max(W/W₀, H/H₀)
    /// Best for: Backgrounds, parallax layers, immersive environments
    case fill
    
    /// AUTOSIZE - Auto-adjust based on container size
    /// Similar to auto-sizing text, adjusts proportionally to container
    /// Best for: Dynamic HUD text, variable dialogue, adaptive labels
    case autosize
    
    /// NONE - No scaling (constant size)
    /// Formula: f(x) = x
    /// Best for: 1pt dividers, pixel art, pixel-perfect rendering
    case none
    
    // MARK: - Description
    
    /// Returns a human-readable description of the strategy
    public var description: String {
        switch self {
        case .default: return "DEFAULT: Fixed legacy (~97% linear + AR)"
        case .percentage: return "PERCENTAGE: Dynamic legacy (100% linear)"
        case .balanced: return "BALANCED: Linear phones, log tablets (Recommended for Games)"
        case .logarithmic: return "LOGARITHMIC: Pure log (Maximum control)"
        case .power: return "POWER: Stevens power law (Scientific)"
        case .fluid: return "FLUID: CSS clamp-like with breakpoints"
        case .interpolated: return "INTERPOLATED: 50% moderated linear"
        case .diagonal: return "DIAGONAL: Scale by screen diagonal"
        case .perimeter: return "PERIMETER: Scale by width + height"
        case .fit: return "FIT: Letterbox (game fit)"
        case .fill: return "FILL: Cover (game fill)"
        case .autosize: return "AUTOSIZE: Auto-adjust to container size"
        case .none: return "NONE: No scaling (constant)"
        }
    }
    
    // MARK: - Recommended Use Cases for Games
    
    /// Returns recommended use cases for games
    public var recommendedForGames: String {
        switch self {
        case .default: return "HUD elements, buttons, icons, fixed UI ⭐"
        case .percentage: return "Game world bounds, backgrounds, proportional layouts"
        case .balanced: return "Player characters, enemies, game objects, projectiles ⭐⭐⭐"
        case .logarithmic: return "TV games, large screen optimizations, subtle elements"
        case .power: return "Custom difficulty scaling, adaptive UI, fine-tuned experiences"
        case .fluid: return "Game text, dialogue, score displays, HUD text"
        case .interpolated: return "Secondary objects, background elements, decorative items"
        case .diagonal: return "Touch targets, gesture areas, physical interaction zones"
        case .perimeter: return "General game elements, balanced world objects"
        case .fit: return "Full viewport (letterbox), puzzle games, strategy games ⭐"
        case .fill: return "Backgrounds, parallax layers, immersive environments ⭐"
        case .autosize: return "Dynamic HUD text, variable dialogue, adaptive labels"
        case .none: return "1pt dividers, pixel art, pixel-perfect rendering"
        }
    }
    
    // MARK: - Formula
    
    /// Returns formula representation
    public var formula: String {
        switch self {
        case .default: return "f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdj"
        case .percentage: return "f(x) = x × (W / W₀)"
        case .balanced: return "f(x) = x × (W/W₀) if W<480, else x × (1.6 + k×ln(...))"
        case .logarithmic: return "f(x) = x × (1 + k × ln(W / W₀))"
        case .power: return "f(x) = x × (W / W₀)^n"
        case .fluid: return "f(x) = interpolate(min, max, W, minW, maxW)"
        case .interpolated: return "f(x) = x + ((x × W/W₀) - x) × 0.5"
        case .diagonal: return "f(x) = x × √(W² + H²) / √(W₀² + H₀²)"
        case .perimeter: return "f(x) = x × (W + H) / (W₀ + H₀)"
        case .fit: return "f(x) = x × min(W/W₀, H/H₀)"
        case .fill: return "f(x) = x × max(W/W₀, H/H₀)"
        case .autosize: return "f(x) = fitToSize(x, min, max, containerSize)"
        case .none: return "f(x) = x"
        }
    }
    
    // MARK: - Is Recommended for Games
    
    /// Returns whether this strategy is recommended for game development
    public var isRecommendedForGames: Bool {
        switch self {
        case .balanced, .fit, .fill, .default:
            return true
        default:
            return false
        }
    }
    
    // MARK: - Game Category
    
    /// Returns the category of this scaling strategy for games
    public var gameCategory: String {
        switch self {
        case .default:
            return "UI & HUD"
        case .percentage:
            return "Containers & Layout"
        case .balanced, .logarithmic, .power, .interpolated:
            return "Game Objects"
        case .fluid:
            return "Typography"
        case .diagonal, .perimeter:
            return "Physical Dimensions"
        case .fit, .fill:
            return "Viewport Modes"
        case .autosize:
            return "Dynamic Content"
        case .none:
            return "Fixed Elements"
        }
    }
    
    // MARK: - Conversion to Base ScalingStrategy
    
    /// Converts GameScalingStrategy to base ScalingStrategy
    /// (for compatibility with non-game code)
    public var toScalingStrategy: ScalingStrategy {
        switch self {
        case .default: return .default
        case .percentage: return .percentage
        case .balanced: return .balanced
        case .logarithmic: return .logarithmic
        case .power: return .power
        case .fluid: return .fluid
        case .interpolated: return .interpolated
        case .diagonal: return .diagonal
        case .perimeter: return .perimeter
        case .fit: return .fit
        case .fill: return .fill
        case .autosize: return .autosize
        case .none: return .none
        }
    }
    
    // MARK: - Creation from Base ScalingStrategy
    
    /// Creates GameScalingStrategy from base ScalingStrategy
    public init(from strategy: ScalingStrategy) {
        switch strategy {
        case .default: self = .default
        case .percentage: self = .percentage
        case .balanced: self = .balanced
        case .logarithmic: self = .logarithmic
        case .power: self = .power
        case .fluid: self = .fluid
        case .interpolated: self = .interpolated
        case .diagonal: self = .diagonal
        case .perimeter: self = .perimeter
        case .fit: self = .fit
        case .fill: self = .fill
        case .autosize: self = .autosize
        case .none: self = .none
        }
    }
}

// MARK: - Game-Specific Recommendations

/**
 * [EN] Helper to get recommended strategy for specific game element types
 * [PT] Helper para obter estratégia recomendada para tipos específicos de elementos de jogo
 */
public extension GameElementType {
    /// Returns the recommended GameScalingStrategy for this element type
    var recommendedGameStrategy: GameScalingStrategy {
        return GameScalingStrategy(from: self.recommendedStrategy)
    }
}

