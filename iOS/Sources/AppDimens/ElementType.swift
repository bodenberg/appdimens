/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Element Types (iOS)
 *
 * Description:
 * Unified enum for UI element types to help auto-infer the best scaling strategy.
 * This is the single source of truth for element types across UIKit and SwiftUI.
 *
 * Licensed under the Apache License, Version 2.0
 */

import Foundation

/**
 * Enum representing different UI element types for auto-inference.
 */
public enum ElementType {
    /// Buttons, clickable elements
    case button
    
    /// Text, typography
    case text
    
    /// Icons, symbols
    case icon
    
    /// Containers, boxes, layouts
    case container
    
    /// Spacing, padding, margins
    case spacing
    
    /// Generic/unknown element
    case generic
    
    /// Cards, elevated surfaces
    case card
    
    /// Dialogs, sheets, popovers
    case dialog
    
    /// Toolbars, navigation bars
    case toolbar
    
    /// Floating action buttons
    case fab
    
    /// Chips, tags, pills
    case chip
    
    /// List items, table cells
    case listItem
    
    /// Images, avatars
    case image
    
    /// Badges, indicators
    case badge
    
    /// Dividers, separators
    case divider
    
    /// Navigation elements, tab bars
    case navigation
    
    /// Text inputs, form fields
    case input
    
    /// Headers, titles, section headers
    case header
    
    /// Returns the recommended scaling strategy for this element type
    public func recommendedStrategy() -> ScalingStrategy {
        switch self {
        case .button, .fab: return .balanced
        case .text: return .fluid
        case .icon, .badge: return .default
        case .container, .card, .listItem, .image: return .percentage
        case .spacing: return .balanced
        case .dialog: return .balanced
        case .toolbar, .navigation, .header: return .default
        case .chip, .input: return .fluid
        case .divider: return .none
        case .generic: return .balanced
        }
    }
    
    /// Returns a description of this element type
    public var description: String {
        switch self {
        case .button: return "Buttons, clickable elements"
        case .text: return "Text, typography"
        case .icon: return "Icons, symbols"
        case .container: return "Containers, boxes, layouts"
        case .spacing: return "Spacing, padding, margins"
        case .generic: return "Generic/unknown element"
        case .card: return "Cards, elevated surfaces"
        case .dialog: return "Dialogs, sheets, popovers"
        case .toolbar: return "Toolbars, navigation bars"
        case .fab: return "Floating action buttons"
        case .chip: return "Chips, tags, pills"
        case .listItem: return "List items, table cells"
        case .image: return "Images, avatars"
        case .badge: return "Badges, indicators"
        case .divider: return "Dividers, separators"
        case .navigation: return "Navigation elements, tab bars"
        case .input: return "Text inputs, form fields"
        case .header: return "Headers, titles, section headers"
        }
    }
}

