/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Scaling Strategies (C++)
 *
 * Description:
 * C++ enum for scaling strategies, matching the Kotlin enum.
 *
 * Licensed under the Apache License, Version 2.0
 */

#ifndef GAME_SCALING_STRATEGY_H
#define GAME_SCALING_STRATEGY_H

/**
 * Scaling strategies for game dimensions.
 * Must match the order in GameScalingStrategy.kt enum!
 */
enum class GameScalingStrategy {
    DEFAULT = 0,
    PERCENTAGE = 1,
    BALANCED = 2,
    LOGARITHMIC = 3,
    POWER = 4,
    FLUID = 5,
    INTERPOLATED = 6,
    DIAGONAL = 7,
    PERIMETER = 8,
    FIT = 9,
    FILL = 10,
    AUTOSIZE = 11,
    NONE = 12
};

/**
 * Element types for auto-inference.
 * Must match GameElementType.kt enum!
 */
enum class GameElementType {
    // HUD Elements
    HUD_BUTTON = 0,
    HUD_ICON = 1,
    HUD_TEXT = 2,
    HUD_CONTAINER = 3,
    HUD_BAR = 4,
    HUD_MINIMAP = 5,
    HUD_CROSSHAIR = 6,
    MENU = 7,
    DIALOG = 8,
    TOOLTIP = 9,
    INVENTORY = 10,
    ABILITY_PANEL = 11,
    
    // Characters
    PLAYER = 12,
    ENEMY = 13,
    BOSS = 14,
    NPC = 15,
    COMPANION = 16,
    VEHICLE = 17,
    
    // Game Objects
    ITEM = 18,
    WEAPON = 19,
    PROJECTILE = 20,
    OBSTACLE = 21,
    INTERACTIVE_OBJECT = 22,
    DESTRUCTIBLE = 23,
    PICKUP = 24,
    TRAP = 25,
    
    // World Elements
    BACKGROUND = 26,
    PARALLAX_LAYER = 27,
    TERRAIN = 28,
    PLATFORM = 29,
    WORLD_OBJECT = 30,
    BUILDING = 31,
    
    // Effects
    PARTICLE = 32,
    VISUAL_EFFECT = 33,
    ANIMATION = 34,
    LIGHT_EFFECT = 35,
    
    // Text
    DIALOGUE = 36,
    CAPTION = 37,
    FLOATING_TEXT = 38,
    QUEST_TEXT = 39,
    LORE_TEXT = 40,
    
    // Special
    CAMERA_BOUNDS = 41,
    TRIGGER_ZONE = 42,
    DEBUG_ELEMENT = 43,
    GENERIC = 44,
    
    // Standard UI (from 45 onwards)
    BUTTON = 45,
    TEXT = 46,
    ICON = 47,
    CONTAINER = 48,
    SPACING = 49,
    CARD = 50,
    FAB = 51,
    CHIP = 52,
    LIST_ITEM = 53,
    IMAGE = 54,
    BADGE = 55,
    DIVIDER = 56,
    NAVIGATION = 57,
    INPUT = 58,
    HEADER = 59,
    TOOLBAR = 60
};

/**
 * Screen type for dimension calculations.
 */
enum class ScreenType {
    LOWEST = 0,
    HIGHEST = 1
};

/**
 * Base orientation for automatic inversion.
 */
enum class BaseOrientation {
    PORTRAIT = 0,
    LANDSCAPE = 1,
    AUTO = 2
};

#endif // GAME_SCALING_STRATEGY_H

