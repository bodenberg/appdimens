/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Scaling Strategies
 *
 * Description:
 * Unified enum defining all available scaling strategies for AppDimens.
 * This is the single source of truth for scaling strategies shared between
 * code and compose implementations.
 *
 * Version 2.0 introduces unified scaling with 13 different strategies.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.strategy

/**
 * [EN] Enum representing all available scaling strategies in AppDimens 2.0.
 * 
 * [PT] Enum representando todas as estratégias de escalonamento disponíveis no AppDimens 2.0.
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
enum class ScalingStrategy {
    /**
     * DEFAULT - Fixed legacy (~97% linear + aspect ratio adjustment)
     * 
     * Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
     * 
     * Characteristics:
     * - ~97% linear growth
     * - Aspect ratio compensation
     * - Familiar behavior from v1.x
     * 
     * Best for: Phone-only apps, icons, backward compatibility
     * 
     * Example: 48dp @ 720dp screen = ~38.4dp
     */
    DEFAULT,
    
    /**
     * PERCENTAGE - Dynamic legacy (100% proportional)
     * 
     * Formula: f(x) = x × (W / W₀)
     * 
     * Characteristics:
     * - 100% linear growth
     * - Pure proportional scaling
     * - No aspect ratio adjustment
     * 
     * Best for: Containers, fluid layouts, images
     * 
     * Example: 48dp @ 720dp screen = 115.2dp
     */
    PERCENTAGE,
    
    /**
     * BALANCED - Perceptual Hybrid (recommended)
     * 
     * Formula: 
     * - if W < 480: f(x) = x × (W / W₀)
     * - if W ≥ 480: f(x) = x × (1.6 + sensitivity × ln(1 + (W-480)/W₀))
     * 
     * Characteristics:
     * - Linear on phones (< 480dp)
     * - Logarithmic on tablets/TVs
     * - Smooth transition at breakpoint
     * - Prevents oversizing on large screens
     * 
     * Best for: Multi-device apps, buttons, spacing
     * 
     * Example: 48dp @ 720dp screen = ~29.7dp (-22% vs linear)
     */
    BALANCED,
    
    /**
     * LOGARITHMIC - Perceptual Weber-Fechner (maximum control)
     * 
     * Formula: f(x) = x × (1 + sensitivity × ln(W / W₀))
     * 
     * Characteristics:
     * - Pure logarithmic growth on all screens
     * - Maximum control on large screens
     * - May reduce sizes noticeably on phones
     * 
     * Best for: TVs, very large tablets
     * 
     * Example: 48dp @ 720dp screen = ~21.6dp (-44% vs linear)
     */
    LOGARITHMIC,
    
    /**
     * POWER - Perceptual Stevens (scientific)
     * 
     * Formula: f(x) = x × (W / W₀)^exponent
     * 
     * Characteristics:
     * - Power law scaling (exponent < 1)
     * - Scientifically grounded (Stevens' Law)
     * - Configurable exponent (0.6-0.9)
     * 
     * Best for: General purpose, configurable apps
     * 
     * Example: 48dp @ 720dp screen = ~29.0dp (exponent=0.75)
     */
    POWER,
    
    /**
     * FLUID - CSS clamp-like interpolation
     * 
     * Formula: 
     * - if W ≤ minW: return minValue
     * - if W ≥ maxW: return maxValue
     * - else: linear interpolation between min/max
     * 
     * Characteristics:
     * - Bounded growth (min/max limits)
     * - Smooth interpolation between breakpoints
     * - Device/screen-specific configs
     * 
     * Best for: Typography, bounded spacing
     * 
     * Example: fluid(40, 72) @ 720dp = interpolated value
     */
    FLUID,
    
    /**
     * INTERPOLATED - Moderated linear interpolation
     * 
     * Formula: f(x) = x + ((x × W/W₀) - x) × 0.5
     * 
     * Characteristics:
     * - 50% of linear growth
     * - Softer than linear, stronger than log
     * - Good balance for medium screens
     * 
     * Best for: Moderate scaling needs
     * 
     * Example: 48dp @ 720dp screen = ~72.0dp (50% between base and linear)
     */
    INTERPOLATED,
    
    /**
     * DIAGONAL - Scale based on diagonal (screen size)
     * 
     * Formula: f(x) = x × √(W² + H²) / √(W₀² + H₀²)
     * 
     * Characteristics:
     * - Considers both width and height
     * - True "screen size" scaling
     * - Orientation independent
     * 
     * Best for: Elements that should match physical screen size
     * 
     * Example: 48dp @ 720×1280 screen = scaled by diagonal ratio
     */
    DIAGONAL,
    
    /**
     * PERIMETER - Scale based on perimeter
     * 
     * Formula: f(x) = x × (W + H) / (W₀ + H₀)
     * 
     * Characteristics:
     * - Linear combination of width and height
     * - Balanced between W and H
     * - Similar to diagonal but simpler calculation
     * 
     * Best for: General purpose, balanced scaling
     * 
     * Example: 48dp @ 720×1280 screen = scaled by perimeter ratio
     */
    PERIMETER,
    
    /**
     * FIT - Letterbox scaling (game fit)
     * 
     * Formula: f(x) = x × min(W/W₀, H/H₀)
     * 
     * Characteristics:
     * - Uses smaller ratio (width or height)
     * - Ensures element fits entirely
     * - Game letterbox behavior
     * - May leave empty space
     * 
     * Best for: Games, full-screen content that must fit
     * 
     * Example: 48dp @ different AR = uses smaller scale
     */
    FIT,
    
    /**
     * FILL - Cover scaling (game fill)
     * 
     * Formula: f(x) = x × max(W/W₀, H/H₀)
     * 
     * Characteristics:
     * - Uses larger ratio (width or height)
     * - Ensures element covers screen
     * - Game cover behavior
     * - May crop content
     * 
     * Best for: Games, backgrounds, full-screen content
     * 
     * Example: 48dp @ different AR = uses larger scale
     */
    FILL,
    
    /**
     * AUTOSIZE - Auto-adjust based on component size
     * 
     * Similar to TextView autoSizeText, adjusts value proportionally
     * to actual component dimensions measured at runtime.
     * 
     * Formula: f(x) = fitToSize(x, minSize, maxSize, containerSize)
     * 
     * Characteristics:
     * - Measures component at runtime
     * - Adjusts to fit content in container
     * - Supports uniform and preset modes
     * 
     * Requires: Component measurement (Compose: onGloballyPositioned)
     * 
     * Best for: Dynamic text, content that must fit container, auto-sizing typography
     * 
     * Example: Text that must fit in variable-width container
     */
    AUTOSIZE,
    
    /**
     * NONE - No scaling (constant size)
     * 
     * Formula: f(x) = x
     * 
     * Characteristics:
     * - No scaling applied
     * - Constant size on all screens
     * - Useful for fixed UI elements
     * 
     * Best for: Fixed size requirements, absolute dimensions
     * 
     * Example: 48dp @ any screen = 48dp
     */
    NONE;
    
    /**
     * [EN] Returns a human-readable description of the strategy
     * [PT] Retorna uma descrição legível da estratégia
     */
    fun getDescription(): String = when (this) {
        DEFAULT -> "DEFAULT: Fixed legacy (~97% linear + AR)"
        PERCENTAGE -> "PERCENTAGE: Dynamic legacy (100% linear)"
        BALANCED -> "BALANCED: Linear phones, log tablets (Recommended)"
        LOGARITHMIC -> "LOGARITHMIC: Pure log (Maximum control)"
        POWER -> "POWER: Stevens power law (Scientific)"
        FLUID -> "FLUID: CSS clamp-like with breakpoints"
        INTERPOLATED -> "INTERPOLATED: 50% moderated linear"
        DIAGONAL -> "DIAGONAL: Scale by screen diagonal"
        PERIMETER -> "PERIMETER: Scale by width + height"
        FIT -> "FIT: Letterbox (game fit)"
        FILL -> "FILL: Cover (game fill)"
        AUTOSIZE -> "AUTOSIZE: Auto-adjust to container size"
        NONE -> "NONE: No scaling (constant)"
    }
    
    /**
     * [EN] Returns recommended use cases
     * [PT] Retorna casos de uso recomendados
     */
    fun getRecommendedFor(): String = when (this) {
        DEFAULT -> "Phone-only apps, icons, backward compatibility"
        PERCENTAGE -> "Containers, fluid layouts, proportional images"
        BALANCED -> "Multi-device apps, buttons, spacing ⭐"
        LOGARITHMIC -> "TVs, very large tablets, maximum control"
        POWER -> "General purpose, configurable apps"
        FLUID -> "Typography, bounded spacing, smooth transitions"
        INTERPOLATED -> "Moderate scaling, balanced growth"
        DIAGONAL -> "True screen size scaling, physical dimensions"
        PERIMETER -> "Balanced W+H scaling, general purpose"
        FIT -> "Games (letterbox), content that must fit"
        FILL -> "Games (cover), backgrounds, full-screen"
        AUTOSIZE -> "Dynamic text, auto-sizing typography, variable containers"
        NONE -> "Fixed UI elements, absolute sizes"
    }
    
    /**
     * [EN] Returns formula representation
     * [PT] Retorna representação da fórmula
     */
    fun getFormula(): String = when (this) {
        DEFAULT -> "f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdj"
        PERCENTAGE -> "f(x) = x × (W / W₀)"
        BALANCED -> "f(x) = x × (W/W₀) if W<480, else x × (1.6 + k×ln(...))"
        LOGARITHMIC -> "f(x) = x × (1 + k × ln(W / W₀))"
        POWER -> "f(x) = x × (W / W₀)^n"
        FLUID -> "f(x) = interpolate(min, max, W, minW, maxW)"
        INTERPOLATED -> "f(x) = x + ((x × W/W₀) - x) × 0.5"
        DIAGONAL -> "f(x) = x × √(W² + H²) / √(W₀² + H₀²)"
        PERIMETER -> "f(x) = x × (W + H) / (W₀ + H₀)"
        FIT -> "f(x) = x × min(W/W₀, H/H₀)"
        FILL -> "f(x) = x × max(W/W₀, H/H₀)"
        AUTOSIZE -> "f(x) = fitToSize(x, min, max, containerSize)"
        NONE -> "f(x) = x"
    }
}

