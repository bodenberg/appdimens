/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Scaling Strategies
 *
 * Description:
 * Unified enum defining all available scaling strategies for AppDimens Games.
 * Extends the dynamic library strategies with game-specific optimizations.
 *
 * Version 2.0 introduces 13 different scaling strategies optimized for game development.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.core.strategy

/**
 * [EN] Enum representing all available scaling strategies in AppDimens Games 2.0.
 * 
 * [PT] Enum representando todas as estratégias de escalonamento disponíveis no AppDimens Games 2.0.
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
enum class GameScalingStrategy {
    /**
     * DEFAULT - Fixed legacy (~97% linear + aspect ratio adjustment)
     * 
     * Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
     * 
     * Characteristics:
     * - ~97% linear growth
     * - Aspect ratio compensation
     * - Familiar behavior from v1.x
     * - Excellent for UI consistency
     * 
     * Best for: HUD elements, buttons, icons, fixed UI components
     * 
     * Game Use Cases:
     * - HUD buttons and controls
     * - Menu UI elements
     * - Overlay icons
     * - Fixed game UI
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
     * - Maintains screen percentage
     * 
     * Best for: Containers, layouts, full-screen elements
     * 
     * Game Use Cases:
     * - Game world bounds
     * - Full-screen backgrounds
     * - Container dimensions
     * - Proportional layouts
     * 
     * Example: 48dp @ 720dp screen = 115.2dp
     */
    PERCENTAGE,
    
    /**
     * BALANCED - Perceptual Hybrid (RECOMMENDED for games)
     * 
     * Formula: 
     * - if W < 480: f(x) = x × (W / W₀) × arAdjustment
     * - if W ≥ 480: f(x) = x × (1.6 + sensitivity × ln(1 + (W-480)/W₀)) × arAdjustment
     * 
     * Characteristics:
     * - Linear on phones (< 480dp)
     * - Logarithmic on tablets/TVs
     * - Smooth transition at breakpoint
     * - Prevents oversizing on large screens
     * - Best balance between familiarity and control
     * - Supports aspect ratio adjustment (enabled by default)
     * 
     * Best for: Multi-device games, player characters, enemies, most game elements
     * 
     * Game Use Cases:
     * - Player sprites/models
     * - Enemy characters
     * - Game objects (items, powerups)
     * - Projectiles
     * - Interactive elements
     * 
     * Example: 48dp @ 720dp screen = ~29.7dp (-22% vs linear)
     */
    BALANCED,
    
    /**
     * LOGARITHMIC - Perceptual Weber-Fechner (maximum control)
     * 
     * Formula: f(x) = x × (1 + sensitivity × ln(W / W₀)) × arAdjustment
     * 
     * Characteristics:
     * - Pure logarithmic growth on all screens
     * - Maximum control on large screens
     * - May reduce sizes noticeably on phones
     * - Based on human perception research
     * - Supports aspect ratio adjustment (enabled by default)
     * 
     * Best for: TV games, very large tablets, elements that should not dominate
     * 
     * Game Use Cases:
     * - TV game UI
     * - Large screen optimizations
     * - Elements that need tight control
     * - Subtle visual elements
     * 
     * Example: 48dp @ 720dp screen = ~21.6dp (-44% vs linear)
     */
    LOGARITHMIC,
    
    /**
     * POWER - Perceptual Stevens (scientific, configurable)
     * 
     * Formula: f(x) = x × (W / W₀)^exponent × arAdjustment
     * 
     * Characteristics:
     * - Power law scaling (exponent < 1)
     * - Scientifically grounded (Stevens' Law)
     * - Configurable exponent (0.6-0.9)
     * - Predictable behavior
     * - Supports aspect ratio adjustment (enabled by default)
     * 
     * Best for: Configurable games, custom scaling needs
     * 
     * Game Use Cases:
     * - Custom difficulty scaling
     * - Adaptive UI based on skill
     * - Dynamic difficulty adjustment
     * - Fine-tuned experiences
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
     * - Optional: × arAdjustment (disabled by default, individual control only)
     * 
     * Characteristics:
     * - Bounded growth (min/max limits)
     * - Smooth interpolation between breakpoints
     * - Device/screen-specific configs
     * - Excellent control over size ranges
     * - Aspect ratio adjustment (disabled by default, ignores global settings)
     * 
     * Best for: Typography, text elements, bounded UI components
     * 
     * Game Use Cases:
     * - Game text (dialogue, captions)
     * - Score displays
     * - HUD text elements
     * - Menu typography
     * 
     * Example: fluid(40, 72) @ 720dp = interpolated value
     */
    FLUID,
    
    /**
     * INTERPOLATED - Moderated linear interpolation
     * 
     * Formula: f(x) = x + ((x × W/W₀) - x) × 0.5 × arAdjustment
     * 
     * Characteristics:
     * - 50% of linear growth
     * - Softer than linear, stronger than log
     * - Good balance for medium screens
     * - Simple and predictable
     * - Supports aspect ratio adjustment (enabled by default)
     * 
     * Best for: Moderate scaling needs, balanced growth
     * 
     * Game Use Cases:
     * - Moderate UI elements
     * - Secondary game objects
     * - Background elements
     * - Decorative items
     * 
     * Example: 48dp @ 720dp screen = ~72.0dp (50% between base and linear)
     */
    INTERPOLATED,
    
    /**
     * DIAGONAL - Scale based on diagonal (true screen size)
     * 
     * Formula: f(x) = x × √(W² + H²) / √(W₀² + H₀²)
     * 
     * Characteristics:
     * - Considers both width and height
     * - True "screen size" scaling
     * - Orientation independent
     * - Physical screen size awareness
     * 
     * Best for: Elements that should match physical screen size
     * 
     * Game Use Cases:
     * - Touch targets (must be same physical size)
     * - Gesture areas
     * - Physical interaction zones
     * - Accessibility-critical elements
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
     * - Good general-purpose strategy
     * 
     * Best for: General purpose, balanced scaling
     * 
     * Game Use Cases:
     * - General game elements
     * - Balanced world objects
     * - Neutral scaling needs
     * - Generic game components
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
     * - Prevents cropping
     * 
     * Best for: Games where all content must be visible, no cropping allowed
     * 
     * Game Use Cases:
     * - Full game viewport (letterbox mode)
     * - Critical game area (must see all)
     * - Puzzle games (entire board visible)
     * - Strategy games (full map view)
     * 
     * Example: 48dp @ different AR = uses smaller scale factor
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
     * - Fills entire screen
     * 
     * Best for: Backgrounds, full-screen content, immersive experiences
     * 
     * Game Use Cases:
     * - Game backgrounds
     * - Parallax layers
     * - Full-screen effects
     * - Immersive environments
     * - Cover art
     * 
     * Example: 48dp @ different AR = uses larger scale factor
     */
    FILL,
    
    /**
     * AUTOSIZE - Auto-adjust based on container size
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
     * - Container-aware
     * 
     * Requires: Component measurement (runtime dimension detection)
     * 
     * Best for: Dynamic content, variable-size containers, responsive text
     * 
     * Game Use Cases:
     * - Dynamic HUD text (scores, names)
     * - Variable-length dialogue
     * - Adaptive UI labels
     * - Responsive menus
     * - Chat text
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
     * - Absolute dimensions
     * 
     * Best for: Fixed size requirements, absolute dimensions, 1px elements
     * 
     * Game Use Cases:
     * - 1dp dividers
     * - Fixed pixel art (pixel-perfect rendering)
     * - Constant size indicators
     * - Absolute dimension requirements
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
        BALANCED -> "BALANCED: Linear phones, log tablets + AR (Recommended for Games)"
        LOGARITHMIC -> "LOGARITHMIC: Pure log + AR (Maximum control)"
        POWER -> "POWER: Stevens power law + AR (Scientific)"
        FLUID -> "FLUID: CSS clamp-like with breakpoints (AR opt-in)"
        INTERPOLATED -> "INTERPOLATED: 50% moderated linear + AR"
        DIAGONAL -> "DIAGONAL: Scale by screen diagonal"
        PERIMETER -> "PERIMETER: Scale by width + height"
        FIT -> "FIT: Letterbox (game fit)"
        FILL -> "FILL: Cover (game fill)"
        AUTOSIZE -> "AUTOSIZE: Auto-adjust to container size"
        NONE -> "NONE: No scaling (constant)"
    }
    
    /**
     * [EN] Returns recommended use cases for games
     * [PT] Retorna casos de uso recomendados para jogos
     */
    fun getRecommendedForGames(): String = when (this) {
        DEFAULT -> "HUD elements, buttons, icons, fixed UI ⭐"
        PERCENTAGE -> "Game world bounds, backgrounds, proportional layouts"
        BALANCED -> "Player characters, enemies, game objects, projectiles ⭐⭐⭐"
        LOGARITHMIC -> "TV games, large screen optimizations, subtle elements"
        POWER -> "Custom difficulty scaling, adaptive UI, fine-tuned experiences"
        FLUID -> "Game text, dialogue, score displays, HUD text"
        INTERPOLATED -> "Secondary objects, background elements, decorative items"
        DIAGONAL -> "Touch targets, gesture areas, physical interaction zones"
        PERIMETER -> "General game elements, balanced world objects"
        FIT -> "Full viewport (letterbox), puzzle games, strategy games ⭐"
        FILL -> "Backgrounds, parallax layers, immersive environments ⭐"
        AUTOSIZE -> "Dynamic HUD text, variable dialogue, adaptive labels"
        NONE -> "1dp dividers, pixel art, pixel-perfect rendering"
    }
    
    /**
     * [EN] Returns formula representation
     * [PT] Retorna representação da fórmula
     */
    fun getFormula(): String = when (this) {
        DEFAULT -> "f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdj"
        PERCENTAGE -> "f(x) = x × (W / W₀)"
        BALANCED -> "f(x) = x × (W/W₀) × arAdj if W<480, else x × (1.6 + k×ln(...)) × arAdj"
        LOGARITHMIC -> "f(x) = x × (1 + k × ln(W / W₀)) × arAdj"
        POWER -> "f(x) = x × (W / W₀)^n × arAdj"
        FLUID -> "f(x) = interpolate(min, max, W, minW, maxW) × arAdj?"
        INTERPOLATED -> "f(x) = x + ((x × W/W₀) - x) × 0.5 × arAdj"
        DIAGONAL -> "f(x) = x × √(W² + H²) / √(W₀² + H₀²)"
        PERIMETER -> "f(x) = x × (W + H) / (W₀ + H₀)"
        FIT -> "f(x) = x × min(W/W₀, H/H₀)"
        FILL -> "f(x) = x × max(W/W₀, H/H₀)"
        AUTOSIZE -> "f(x) = fitToSize(x, min, max, containerSize)"
        NONE -> "f(x) = x"
    }
    
    /**
     * [EN] Returns whether this strategy is recommended for game development
     * [PT] Retorna se esta estratégia é recomendada para desenvolvimento de jogos
     */
    fun isRecommendedForGames(): Boolean = when (this) {
        BALANCED, FIT, FILL, DEFAULT -> true
        else -> false
    }
}

