/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Dimension Types (iOS)
 *
 * Description:
 * Enum defining game dimension types for cross-platform compatibility.
 * Similar to Android GameDimensionType.
 *
 * Licensed under the Apache License, Version 2.0
 */

import Foundation

/**
 * [EN] Enum representing different game dimension types.
 * Compatible with Android API for cross-platform consistency.
 * 
 * [PT] Enum representando diferentes tipos de dimensão de jogo.
 * Compatível com API Android para consistência cross-platform.
 */
public enum GameDimensionType {
    /**
     * DYNAMIC - Proportional scaling
     * Ideal for containers and fluid layouts
     * Formula: f(x) = x × (W / W₀)
     */
    case dynamic
    
    /**
     * FIXED - Logarithmic scaling
     * Ideal for UI elements, buttons, and margins
     * Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdj
     */
    case fixed
    
    /**
     * GAME_WORLD - Consistent world coordinates
     * Maintains consistent scaling for game objects
     * Formula: Similar to BALANCED strategy
     */
    case gameWorld
    
    /**
     * UI_OVERLAY - Stable UI overlay elements
     * For HUD and overlay elements that need stable scaling
     * Formula: Similar to DEFAULT strategy
     */
    case uiOverlay
    
    /**
     * BALANCED - Balanced scaling (default)
     * Linear on phones, logarithmic on tablets
     * Formula: Hybrid approach based on screen size
     */
    case balanced
    
    /// Returns a human-readable description of the dimension type
    public var description: String {
        switch self {
        case .dynamic: return "DYNAMIC: Proportional scaling"
        case .fixed: return "FIXED: Logarithmic scaling"
        case .gameWorld: return "GAME_WORLD: Consistent world coordinates"
        case .uiOverlay: return "UI_OVERLAY: Stable UI overlay"
        case .balanced: return "BALANCED: Balanced scaling (default)"
        }
    }
    
    /// Returns recommended use cases
    public var recommendedFor: String {
        switch self {
        case .dynamic: return "Containers, fluid layouts, proportional elements"
        case .fixed: return "UI elements, buttons, margins, icons"
        case .gameWorld: return "Game objects, players, enemies, physics entities"
        case .uiOverlay: return "HUD elements, menus, overlay UI"
        case .balanced: return "General purpose, multi-device games"
        }
    }
}

