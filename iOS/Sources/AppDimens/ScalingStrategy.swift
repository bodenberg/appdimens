/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Scaling Strategies (iOS)
 *
 * Description:
 * Unified enum defining all available scaling strategies for AppDimens iOS.
 * This is the single source of truth for scaling strategies across UIKit and SwiftUI.
 *
 * Version 2.0 introduces unified scaling with 13 different strategies.
 *
 * Licensed under the Apache License, Version 2.0
 */

import Foundation

/**
 * Enum representing all available scaling strategies in AppDimens 2.0 for iOS.
 *
 * Available strategies:
 * - DEFAULT: Fixed legacy (~97% linear + AR adjustment)
 * - PERCENTAGE: Dynamic legacy (100% linear, proportional)
 * - BALANCED: Perceptual Hybrid (linear phones, log tablets)
 * - LOGARITHMIC: Perceptual Weber-Fechner (pure log)
 * - POWER: Perceptual Stevens (power law)
 * - FLUID: CSS clamp-like interpolation with breakpoints
 * - INTERPOLATED: Moderated linear interpolation
 * - DIAGONAL: Scale based on diagonal (screen size)
 * - PERIMETER: Scale based on perimeter (W+H)
 * - FIT: Letterbox scaling (min ratio) - Game fit
 * - FILL: Cover scaling (max ratio) - Game fill
 * - AUTOSIZE: Auto-adjust based on component size
 * - NONE: No scaling (constant size)
 */
public enum ScalingStrategy {
    /// DEFAULT - Fixed legacy (~97% linear + aspect ratio adjustment)
    /// Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
    /// Best for: Phone-only apps, icons, backward compatibility
    case `default`
    
    /// PERCENTAGE - Dynamic legacy (100% proportional)
    /// Formula: f(x) = x × (W / W₀)
    /// Best for: Containers, fluid layouts, images
    case percentage
    
    /// BALANCED - Perceptual Hybrid (recommended)
    /// Formula: Linear on phones (< 480pt), logarithmic on tablets/TVs
    /// Best for: Multi-device apps, buttons, spacing
    case balanced
    
    /// LOGARITHMIC - Perceptual Weber-Fechner (maximum control)
    /// Formula: f(x) = x × (1 + sensitivity × ln(W / W₀))
    /// Best for: TVs, very large tablets
    case logarithmic
    
    /// POWER - Perceptual Stevens (scientific)
    /// Formula: f(x) = x × (W / W₀)^exponent
    /// Best for: General purpose, configurable apps
    case power
    
    /// FLUID - CSS clamp-like interpolation
    /// Smooth interpolation between min/max values
    /// Best for: Typography, bounded spacing
    case fluid
    
    /// INTERPOLATED - Moderated linear interpolation
    /// Formula: f(x) = x + ((x × W/W₀) - x) × 0.5
    /// Best for: Moderate scaling needs
    case interpolated
    
    /// DIAGONAL - Scale based on diagonal (screen size)
    /// Formula: f(x) = x × √(W² + H²) / √(W₀² + H₀²)
    /// Best for: Elements that should match physical screen size
    case diagonal
    
    /// PERIMETER - Scale based on perimeter
    /// Formula: f(x) = x × (W + H) / (W₀ + H₀)
    /// Best for: General purpose, balanced scaling
    case perimeter
    
    /// FIT - Letterbox scaling (game fit)
    /// Formula: f(x) = x × min(W/W₀, H/H₀)
    /// Best for: Games, full-screen content that must fit
    case fit
    
    /// FILL - Cover scaling (game fill)
    /// Formula: f(x) = x × max(W/W₀, H/H₀)
    /// Best for: Games, backgrounds, full-screen content
    case fill
    
    /// AUTOSIZE - Auto-adjust based on component size
    /// Similar to UILabel adjustsFontSizeToFitWidth
    /// Best for: Dynamic text, auto-sizing typography
    case autosize
    
    /// NONE - No scaling (constant size)
    /// Formula: f(x) = x
    /// Best for: Fixed UI elements, absolute dimensions
    case none
    
    /// Returns a human-readable description of the strategy
    public var description: String {
        switch self {
        case .default: return "DEFAULT: Fixed legacy (~97% linear + AR)"
        case .percentage: return "PERCENTAGE: Dynamic legacy (100% linear)"
        case .balanced: return "BALANCED: Linear phones, log tablets (Recommended)"
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
    
    /// Returns recommended use cases
    public var recommendedFor: String {
        switch self {
        case .default: return "Phone-only apps, icons, backward compatibility"
        case .percentage: return "Containers, fluid layouts, proportional images"
        case .balanced: return "Multi-device apps, buttons, spacing ⭐"
        case .logarithmic: return "TVs, very large tablets, maximum control"
        case .power: return "General purpose, configurable apps"
        case .fluid: return "Typography, bounded spacing, smooth transitions"
        case .interpolated: return "Moderate scaling, balanced growth"
        case .diagonal: return "True screen size scaling, physical dimensions"
        case .perimeter: return "Balanced W+H scaling, general purpose"
        case .fit: return "Games (letterbox), content that must fit"
        case .fill: return "Games (cover), backgrounds, full-screen"
        case .autosize: return "Dynamic text, auto-sizing typography, variable containers"
        case .none: return "Fixed UI elements, absolute sizes"
        }
    }
    
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
}

