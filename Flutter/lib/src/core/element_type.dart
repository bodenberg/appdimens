/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Element Types (Flutter)
 *
 * Description:
 * Unified enum for UI element types to help auto-infer the best scaling strategy.
 * This is the single source of truth for element types across the library.
 *
 * Licensed under the Apache License, Version 2.0
 */

import 'scaling_strategy.dart';

/// Enum representing different UI element types for auto-inference.
enum ElementType {
  /// Buttons, clickable elements
  button,

  /// Text, typography
  text,

  /// Icons, symbols
  icon,

  /// Containers, boxes, layouts
  container,

  /// Spacing, padding, margins
  spacing,

  /// Generic/unknown element
  generic,

  /// Cards, elevated surfaces
  card,

  /// Dialogs, bottom sheets
  dialog,

  /// Toolbars, app bars
  toolbar,

  /// Floating action buttons
  fab,

  /// Chips, tags
  chip,

  /// List items
  listItem,

  /// Images, avatars
  image,

  /// Badges, indicators
  badge,

  /// Dividers, separators
  divider,

  /// Navigation elements
  navigation,

  /// Text inputs, form fields
  input,

  /// Headers, titles
  header;

  /// Returns the recommended scaling strategy for this element type
  ScalingStrategy get recommendedStrategy {
    switch (this) {
      case ElementType.button:
      case ElementType.fab:
        return ScalingStrategy.balanced;
      case ElementType.text:
        return ScalingStrategy.fluid;
      case ElementType.icon:
      case ElementType.badge:
        return ScalingStrategy.defaultStrategy;
      case ElementType.container:
      case ElementType.card:
      case ElementType.listItem:
      case ElementType.image:
        return ScalingStrategy.percentage;
      case ElementType.spacing:
        return ScalingStrategy.balanced;
      case ElementType.dialog:
        return ScalingStrategy.balanced;
      case ElementType.toolbar:
      case ElementType.navigation:
      case ElementType.header:
        return ScalingStrategy.defaultStrategy;
      case ElementType.chip:
      case ElementType.input:
        return ScalingStrategy.fluid;
      case ElementType.divider:
        return ScalingStrategy.none;
      case ElementType.generic:
        return ScalingStrategy.balanced;
    }
  }

  /// Returns a description of this element type
  String get description {
    switch (this) {
      case ElementType.button:
        return 'Buttons, clickable elements';
      case ElementType.text:
        return 'Text, typography';
      case ElementType.icon:
        return 'Icons, symbols';
      case ElementType.container:
        return 'Containers, boxes, layouts';
      case ElementType.spacing:
        return 'Spacing, padding, margins';
      case ElementType.generic:
        return 'Generic/unknown element';
      case ElementType.card:
        return 'Cards, elevated surfaces';
      case ElementType.dialog:
        return 'Dialogs, bottom sheets';
      case ElementType.toolbar:
        return 'Toolbars, app bars';
      case ElementType.fab:
        return 'Floating action buttons';
      case ElementType.chip:
        return 'Chips, tags';
      case ElementType.listItem:
        return 'List items';
      case ElementType.image:
        return 'Images, avatars';
      case ElementType.badge:
        return 'Badges, indicators';
      case ElementType.divider:
        return 'Dividers, separators';
      case ElementType.navigation:
        return 'Navigation elements';
      case ElementType.input:
        return 'Text inputs, form fields';
      case ElementType.header:
        return 'Headers, titles';
    }
  }
}

