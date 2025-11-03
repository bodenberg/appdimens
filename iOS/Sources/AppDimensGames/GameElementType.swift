/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Element Types (iOS)
 *
 * Description:
 * Unified enum for game element types to help auto-infer the best scaling strategy.
 * Extends the base UI element types with game-specific categories.
 *
 * Licensed under the Apache License, Version 2.0
 */

import Foundation

/**
 * Enum representing different game element types for auto-inference.
 *
 * Categories:
 * - UI Elements: HUD components, menus, overlays
 * - Game Characters: Players, enemies, NPCs
 * - Game Objects: Items, projectiles, obstacles
 * - World Elements: Backgrounds, terrain, effects
 * - Text Elements: Dialogue, scores, captions
 */
public enum GameElementType {
    
    // MARK: - UI ELEMENTS (HUD & Overlays)
    
    case hudButton
    case hudIcon
    case hudText
    case hudContainer
    case hudBar
    case hudMinimap
    case hudCrosshair
    case menu
    case dialog
    case tooltip
    case inventory
    case abilityPanel
    
    // MARK: - GAME CHARACTERS
    
    case player
    case enemy
    case boss
    case npc
    case companion
    case vehicle
    
    // MARK: - GAME OBJECTS
    
    case item
    case weapon
    case projectile
    case obstacle
    case interactiveObject
    case destructible
    case pickup
    case trap
    
    // MARK: - WORLD ELEMENTS
    
    case background
    case parallaxLayer
    case terrain
    case platform
    case worldObject
    case building
    
    // MARK: - EFFECTS & PARTICLES
    
    case particle
    case visualEffect
    case animation
    case lightEffect
    
    // MARK: - TEXT ELEMENTS
    
    case dialogue
    case caption
    case floatingText
    case questText
    case loreText
    
    // MARK: - SPECIAL ELEMENTS
    
    case cameraBounds
    case triggerZone
    case debugElement
    case generic
    
    // MARK: - UI STANDARD
    
    case button
    case text
    case icon
    case container
    case spacing
    case card
    case fab
    case chip
    case listItem
    case image
    case badge
    case divider
    case navigation
    case input
    case header
    case toolbar
    
    /// Returns the recommended scaling strategy for this element type
    public var recommendedStrategy: ScalingStrategy {
        switch self {
        // HUD Elements - Default/Fixed for consistency
        case .hudButton, .hudIcon, .hudBar, .hudCrosshair:
            return .default
        case .hudText, .hudMinimap:
            return .fluid
        case .hudContainer, .inventory, .abilityPanel:
            return .percentage
            
        // Menu & UI - Balanced for multi-device
        case .menu, .dialog, .tooltip:
            return .balanced
            
        // Characters - Balanced for natural scaling
        case .player, .enemy, .boss, .npc, .companion, .vehicle:
            return .balanced
            
        // Game Objects - Balanced for consistency
        case .item, .weapon, .projectile, .obstacle, .interactiveObject,
             .destructible, .pickup, .trap:
            return .balanced
            
        // World Elements - Fill/Percentage for coverage
        case .background, .parallaxLayer:
            return .fill
        case .terrain, .platform, .worldObject, .building:
            return .balanced
            
        // Effects & Particles - Balanced for visual consistency
        case .particle, .visualEffect, .animation, .lightEffect:
            return .balanced
            
        // Text Elements - Fluid for readability
        case .dialogue, .caption, .floatingText, .questText, .loreText:
            return .fluid
            
        // Special Elements
        case .cameraBounds, .triggerZone:
            return .percentage
        case .debugElement:
            return .none
            
        // Standard UI
        case .button, .fab:
            return .balanced
        case .text, .hudText:
            return .fluid
        case .icon, .badge:
            return .default
        case .container, .card, .listItem, .image:
            return .percentage
        case .spacing:
            return .balanced
        case .chip, .input:
            return .fluid
        case .divider:
            return .none
        case .navigation, .header, .toolbar:
            return .default
            
        case .generic:
            return .balanced
        }
    }
    
    /// Returns whether this is a game-specific element (vs standard UI)
    public var isGameSpecific: Bool {
        switch self {
        case .hudButton, .hudIcon, .hudText, .hudContainer, .hudBar, .hudMinimap,
             .hudCrosshair, .player, .enemy, .boss, .npc, .companion, .vehicle,
             .item, .weapon, .projectile, .obstacle, .interactiveObject, .destructible,
             .pickup, .trap, .background, .parallaxLayer, .terrain, .platform,
             .worldObject, .building, .particle, .visualEffect, .animation,
             .lightEffect, .dialogue, .caption, .floatingText, .questText,
             .loreText, .cameraBounds, .triggerZone, .debugElement:
            return true
        default:
            return false
        }
    }
    
    /// Returns the category of this element type
    public var category: String {
        switch self {
        case .hudButton, .hudIcon, .hudText, .hudContainer, .hudBar, .hudMinimap,
             .hudCrosshair, .menu, .dialog, .tooltip, .inventory, .abilityPanel:
            return "UI & HUD"
            
        case .player, .enemy, .boss, .npc, .companion, .vehicle:
            return "Characters"
            
        case .item, .weapon, .projectile, .obstacle, .interactiveObject, .destructible,
             .pickup, .trap:
            return "Game Objects"
            
        case .background, .parallaxLayer, .terrain, .platform, .worldObject, .building:
            return "World Elements"
            
        case .particle, .visualEffect, .animation, .lightEffect:
            return "Effects & Particles"
            
        case .dialogue, .caption, .floatingText, .questText, .loreText, .text, .hudText:
            return "Text Elements"
            
        case .cameraBounds, .triggerZone, .debugElement:
            return "Special Elements"
            
        default:
            return "Standard UI"
        }
    }
}

