/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Element Types
 *
 * Description:
 * Unified enum for UI element types to help auto-infer the best scaling strategy.
 * This is the single source of truth for element types shared between
 * code and compose implementations.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.strategy

/**
 * [EN] Enum representing different UI element types for auto-inference.
 * 
 * [PT] Enum representando diferentes tipos de elementos UI para inferência automática.
 */
enum class ElementType {
    /** Buttons, clickable elements */
    BUTTON,
    
    /** Text, typography */
    TEXT,
    
    /** Icons, symbols */
    ICON,
    
    /** Containers, boxes, layouts */
    CONTAINER,
    
    /** Spacing, padding, margins */
    SPACING,
    
    /** Generic/unknown element */
    GENERIC,
    
    /** Cards, elevated surfaces */
    CARD,
    
    /** Dialogs, bottom sheets */
    DIALOG,
    
    /** Toolbars, app bars */
    TOOLBAR,
    
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
    HEADER
}

