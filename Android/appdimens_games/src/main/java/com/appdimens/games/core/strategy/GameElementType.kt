/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Element Types
 *
 * Description:
 * Unified enum for game element types to help auto-infer the best scaling strategy.
 * Extends the base UI element types with game-specific categories.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.core.strategy

/**
 * [EN] Enum representing different game element types for auto-inference.
 * 
 * [PT] Enum representando diferentes tipos de elementos de jogo para inferência automática.
 * 
 * Categories:
 * - UI Elements: HUD components, menus, overlays
 * - Game Characters: Players, enemies, NPCs
 * - Game Objects: Items, projectiles, obstacles
 * - World Elements: Backgrounds, terrain, effects
 * - Text Elements: Dialogue, scores, captions
 */
enum class GameElementType {
    
    // ==========================================
    // UI ELEMENTS (HUD & Overlays)
    // ==========================================
    
    /** HUD buttons, control buttons */
    HUD_BUTTON,
    
    /** HUD icons (health, mana, abilities) */
    HUD_ICON,
    
    /** HUD text (scores, timers, stats) */
    HUD_TEXT,
    
    /** HUD containers (info panels, status bars) */
    HUD_CONTAINER,
    
    /** Health bars, mana bars, progress bars */
    HUD_BAR,
    
    /** Minimap, radar, navigation aids */
    HUD_MINIMAP,
    
    /** Crosshair, targeting reticle */
    HUD_CROSSHAIR,
    
    /** Pause menu, settings menu */
    MENU,
    
    /** Dialog boxes, confirmation popups */
    DIALOG,
    
    /** Tooltips, hints, tutorials */
    TOOLTIP,
    
    /** Inventory grid, item slots */
    INVENTORY,
    
    /** Skill tree, ability panel */
    ABILITY_PANEL,
    
    // ==========================================
    // GAME CHARACTERS
    // ==========================================
    
    /** Player character, avatar */
    PLAYER,
    
    /** Enemy character, monster */
    ENEMY,
    
    /** Boss character, elite enemy */
    BOSS,
    
    /** Non-playable character */
    NPC,
    
    /** Pet, companion, follower */
    COMPANION,
    
    /** Vehicle, mount */
    VEHICLE,
    
    // ==========================================
    // GAME OBJECTS
    // ==========================================
    
    /** Collectible item (coins, gems, powerups) */
    ITEM,
    
    /** Weapon, equipment */
    WEAPON,
    
    /** Projectile (bullets, arrows, spells) */
    PROJECTILE,
    
    /** Obstacle, barrier */
    OBSTACLE,
    
    /** Interactive object (doors, chests, switches) */
    INTERACTIVE_OBJECT,
    
    /** Destructible object */
    DESTRUCTIBLE,
    
    /** Pickup (health pack, ammo) */
    PICKUP,
    
    /** Trap, hazard */
    TRAP,
    
    // ==========================================
    // WORLD ELEMENTS
    // ==========================================
    
    /** Background layer */
    BACKGROUND,
    
    /** Parallax layer */
    PARALLAX_LAYER,
    
    /** Terrain, ground */
    TERRAIN,
    
    /** Platform (in platformer games) */
    PLATFORM,
    
    /** World object (trees, rocks, decorations) */
    WORLD_OBJECT,
    
    /** Building, structure */
    BUILDING,
    
    // ==========================================
    // EFFECTS & PARTICLES
    // ==========================================
    
    /** Particle effect (explosions, smoke, magic) */
    PARTICLE,
    
    /** Visual effect (glow, trail, aura) */
    VISUAL_EFFECT,
    
    /** Animation sprite */
    ANIMATION,
    
    /** Light source, lighting effect */
    LIGHT_EFFECT,
    
    // ==========================================
    // TEXT ELEMENTS
    // ==========================================
    
    /** Dialogue text, speech bubble */
    DIALOGUE,
    
    /** Caption, subtitle */
    CAPTION,
    
    /** Floating text (damage numbers, XP gain) */
    FLOATING_TEXT,
    
    /** Quest text, mission info */
    QUEST_TEXT,
    
    /** Lore text, story text */
    LORE_TEXT,
    
    // ==========================================
    // SPECIAL ELEMENTS
    // ==========================================
    
    /** Camera bounds, viewport */
    CAMERA_BOUNDS,
    
    /** Trigger zone, collision area */
    TRIGGER_ZONE,
    
    /** Debug element, debug visualization */
    DEBUG_ELEMENT,
    
    /** Generic/unknown element */
    GENERIC,
    
    // ==========================================
    // UI STANDARD (from appdimens_dynamic)
    // ==========================================
    
    /** Standard button */
    BUTTON,
    
    /** Standard text */
    TEXT,
    
    /** Standard icon */
    ICON,
    
    /** Standard container */
    CONTAINER,
    
    /** Spacing, padding, margins */
    SPACING,
    
    /** Cards, elevated surfaces */
    CARD,
    
    /** Floating action buttons */
    FAB,
    
    /** Chips, tags */
    CHIP,
    
    /** List items */
    LIST_ITEM,
    
    /** Images, avatars */
    IMAGE,
    
    /** Badges, indicators */
    BADGE,
    
    /** Dividers, separators */
    DIVIDER,
    
    /** Navigation elements */
    NAVIGATION,
    
    /** Text inputs, form fields */
    INPUT,
    
    /** Headers, titles */
    HEADER,
    
    /** Toolbar, app bar */
    TOOLBAR;
    
    /**
     * [EN] Returns the recommended scaling strategy for this element type
     * [PT] Retorna a estratégia de escalonamento recomendada para este tipo de elemento
     */
    fun getRecommendedStrategy(): GameScalingStrategy = when (this) {
        // HUD Elements - Fixed/Default for consistency
        HUD_BUTTON, HUD_ICON, HUD_BAR, HUD_CROSSHAIR -> GameScalingStrategy.DEFAULT
        HUD_TEXT, HUD_MINIMAP -> GameScalingStrategy.FLUID
        HUD_CONTAINER, INVENTORY, ABILITY_PANEL -> GameScalingStrategy.PERCENTAGE
        
        // Menu & UI - Balanced for multi-device
        MENU, DIALOG, TOOLTIP -> GameScalingStrategy.BALANCED
        
        // Characters - Balanced for natural scaling
        PLAYER, ENEMY, BOSS, NPC, COMPANION, VEHICLE -> GameScalingStrategy.BALANCED
        
        // Game Objects - Balanced for consistency
        ITEM, WEAPON, PROJECTILE, OBSTACLE, INTERACTIVE_OBJECT, 
        DESTRUCTIBLE, PICKUP, TRAP -> GameScalingStrategy.BALANCED
        
        // World Elements - Fill/Percentage for coverage
        BACKGROUND, PARALLAX_LAYER -> GameScalingStrategy.FILL
        TERRAIN, PLATFORM, WORLD_OBJECT, BUILDING -> GameScalingStrategy.BALANCED
        
        // Effects & Particles - Balanced for visual consistency
        PARTICLE, VISUAL_EFFECT, ANIMATION, LIGHT_EFFECT -> GameScalingStrategy.BALANCED
        
        // Text Elements - Fluid for readability
        DIALOGUE, CAPTION, FLOATING_TEXT, QUEST_TEXT, LORE_TEXT -> GameScalingStrategy.FLUID
        
        // Special Elements
        CAMERA_BOUNDS, TRIGGER_ZONE -> GameScalingStrategy.PERCENTAGE
        DEBUG_ELEMENT -> GameScalingStrategy.NONE
        
        // Standard UI
        BUTTON, FAB -> GameScalingStrategy.BALANCED
        TEXT, HUD_TEXT -> GameScalingStrategy.FLUID
        ICON, BADGE -> GameScalingStrategy.DEFAULT
        CONTAINER, CARD, LIST_ITEM -> GameScalingStrategy.PERCENTAGE
        SPACING -> GameScalingStrategy.BALANCED
        CHIP, INPUT -> GameScalingStrategy.FLUID
        IMAGE -> GameScalingStrategy.PERCENTAGE
        DIVIDER -> GameScalingStrategy.NONE
        NAVIGATION, HEADER, TOOLBAR -> GameScalingStrategy.DEFAULT
        
        GENERIC -> GameScalingStrategy.BALANCED
    }
    
    /**
     * [EN] Returns whether this is a game-specific element (vs standard UI)
     * [PT] Retorna se este é um elemento específico de jogo (vs UI padrão)
     */
    fun isGameSpecific(): Boolean = when (this) {
        HUD_BUTTON, HUD_ICON, HUD_TEXT, HUD_CONTAINER, HUD_BAR, HUD_MINIMAP,
        HUD_CROSSHAIR, PLAYER, ENEMY, BOSS, NPC, COMPANION, VEHICLE,
        ITEM, WEAPON, PROJECTILE, OBSTACLE, INTERACTIVE_OBJECT, DESTRUCTIBLE,
        PICKUP, TRAP, BACKGROUND, PARALLAX_LAYER, TERRAIN, PLATFORM,
        WORLD_OBJECT, BUILDING, PARTICLE, VISUAL_EFFECT, ANIMATION,
        LIGHT_EFFECT, DIALOGUE, CAPTION, FLOATING_TEXT, QUEST_TEXT,
        LORE_TEXT, CAMERA_BOUNDS, TRIGGER_ZONE, DEBUG_ELEMENT -> true
        else -> false
    }
    
    /**
     * [EN] Returns the category of this element type
     * [PT] Retorna a categoria deste tipo de elemento
     */
    fun getCategory(): String = when (this) {
        HUD_BUTTON, HUD_ICON, HUD_TEXT, HUD_CONTAINER, HUD_BAR, HUD_MINIMAP,
        HUD_CROSSHAIR, MENU, DIALOG, TOOLTIP, INVENTORY, ABILITY_PANEL -> "UI & HUD"
        
        PLAYER, ENEMY, BOSS, NPC, COMPANION, VEHICLE -> "Characters"
        
        ITEM, WEAPON, PROJECTILE, OBSTACLE, INTERACTIVE_OBJECT, DESTRUCTIBLE,
        PICKUP, TRAP -> "Game Objects"
        
        BACKGROUND, PARALLAX_LAYER, TERRAIN, PLATFORM, WORLD_OBJECT, BUILDING -> "World Elements"
        
        PARTICLE, VISUAL_EFFECT, ANIMATION, LIGHT_EFFECT -> "Effects & Particles"
        
        DIALOGUE, CAPTION, FLOATING_TEXT, QUEST_TEXT, LORE_TEXT, TEXT, HUD_TEXT -> "Text Elements"
        
        CAMERA_BOUNDS, TRIGGER_ZONE, DEBUG_ELEMENT -> "Special Elements"
        
        else -> "Standard UI"
    }
    
    /**
     * [EN] Returns a description of this element type
     * [PT] Retorna uma descrição deste tipo de elemento
     */
    fun getDescription(): String = when (this) {
        HUD_BUTTON -> "HUD buttons and control buttons"
        HUD_ICON -> "HUD icons (health, mana, abilities)"
        HUD_TEXT -> "HUD text (scores, timers, stats)"
        HUD_CONTAINER -> "HUD containers (info panels, status bars)"
        HUD_BAR -> "Health bars, mana bars, progress bars"
        HUD_MINIMAP -> "Minimap, radar, navigation aids"
        HUD_CROSSHAIR -> "Crosshair, targeting reticle"
        MENU -> "Pause menu, settings menu"
        DIALOG -> "Dialog boxes, confirmation popups"
        TOOLTIP -> "Tooltips, hints, tutorials"
        INVENTORY -> "Inventory grid, item slots"
        ABILITY_PANEL -> "Skill tree, ability panel"
        PLAYER -> "Player character, avatar"
        ENEMY -> "Enemy character, monster"
        BOSS -> "Boss character, elite enemy"
        NPC -> "Non-playable character"
        COMPANION -> "Pet, companion, follower"
        VEHICLE -> "Vehicle, mount"
        ITEM -> "Collectible item (coins, gems, powerups)"
        WEAPON -> "Weapon, equipment"
        PROJECTILE -> "Projectile (bullets, arrows, spells)"
        OBSTACLE -> "Obstacle, barrier"
        INTERACTIVE_OBJECT -> "Interactive object (doors, chests, switches)"
        DESTRUCTIBLE -> "Destructible object"
        PICKUP -> "Pickup (health pack, ammo)"
        TRAP -> "Trap, hazard"
        BACKGROUND -> "Background layer"
        PARALLAX_LAYER -> "Parallax layer"
        TERRAIN -> "Terrain, ground"
        PLATFORM -> "Platform (in platformer games)"
        WORLD_OBJECT -> "World object (trees, rocks, decorations)"
        BUILDING -> "Building, structure"
        PARTICLE -> "Particle effect (explosions, smoke, magic)"
        VISUAL_EFFECT -> "Visual effect (glow, trail, aura)"
        ANIMATION -> "Animation sprite"
        LIGHT_EFFECT -> "Light source, lighting effect"
        DIALOGUE -> "Dialogue text, speech bubble"
        CAPTION -> "Caption, subtitle"
        FLOATING_TEXT -> "Floating text (damage numbers, XP gain)"
        QUEST_TEXT -> "Quest text, mission info"
        LORE_TEXT -> "Lore text, story text"
        CAMERA_BOUNDS -> "Camera bounds, viewport"
        TRIGGER_ZONE -> "Trigger zone, collision area"
        DEBUG_ELEMENT -> "Debug element, debug visualization"
        GENERIC -> "Generic/unknown element"
        BUTTON -> "Standard button"
        TEXT -> "Standard text"
        ICON -> "Standard icon"
        CONTAINER -> "Standard container"
        SPACING -> "Spacing, padding, margins"
        CARD -> "Cards, elevated surfaces"
        FAB -> "Floating action buttons"
        CHIP -> "Chips, tags"
        LIST_ITEM -> "List items"
        IMAGE -> "Images, avatars"
        BADGE -> "Badges, indicators"
        DIVIDER -> "Dividers, separators"
        NAVIGATION -> "Navigation elements"
        INPUT -> "Text inputs, form fields"
        HEADER -> "Headers, titles"
        TOOLBAR -> "Toolbar, app bar"
    }
}

