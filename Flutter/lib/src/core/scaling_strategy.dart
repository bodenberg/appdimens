/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Scaling Strategies (Flutter)
 *
 * Description:
 * Unified enum defining all available scaling strategies for AppDimens Flutter.
 * This is the single source of truth for scaling strategies across the library.
 *
 * Version 2.0 introduces unified scaling with 13 different strategies.
 *
 * Licensed under the Apache License, Version 2.0
 */

/// Enum representing all available scaling strategies in AppDimens 2.0 for Flutter.
///
/// Available strategies:
/// - DEFAULT: Fixed legacy (~97% linear + AR adjustment)
/// - PERCENTAGE: Dynamic legacy (100% linear, proportional)
/// - BALANCED: Perceptual Hybrid (linear phones, log tablets)
/// - LOGARITHMIC: Perceptual Weber-Fechner (pure log)
/// - POWER: Perceptual Stevens (power law)
/// - FLUID: CSS clamp-like interpolation with breakpoints
/// - INTERPOLATED: Moderated linear interpolation
/// - DIAGONAL: Scale based on diagonal (screen size)
/// - PERIMETER: Scale based on perimeter (W+H)
/// - FIT: Letterbox scaling (min ratio) - Game fit
/// - FILL: Cover scaling (max ratio) - Game fill
/// - AUTOSIZE: Auto-adjust based on component size
/// - NONE: No scaling (constant size)
enum ScalingStrategy {
  /// DEFAULT - Fixed legacy (~97% linear + aspect ratio adjustment)
  /// Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
  /// Best for: Phone-only apps, icons, backward compatibility
  defaultStrategy,

  /// PERCENTAGE - Dynamic legacy (100% proportional)
  /// Formula: f(x) = x × (W / W₀)
  /// Best for: Containers, fluid layouts, images
  percentage,

  /// BALANCED - Perceptual Hybrid (recommended)
  /// Formula: Linear on phones (< 480dp), logarithmic on tablets/TVs
  /// Best for: Multi-device apps, buttons, spacing
  balanced,

  /// LOGARITHMIC - Perceptual Weber-Fechner (maximum control)
  /// Formula: f(x) = x × (1 + sensitivity × ln(W / W₀))
  /// Best for: TVs, very large tablets
  logarithmic,

  /// POWER - Perceptual Stevens (scientific)
  /// Formula: f(x) = x × (W / W₀)^exponent
  /// Best for: General purpose, configurable apps
  power,

  /// FLUID - CSS clamp-like interpolation
  /// Smooth interpolation between min/max values
  /// Best for: Typography, bounded spacing
  fluid,

  /// INTERPOLATED - Moderated linear interpolation
  /// Formula: f(x) = x + ((x × W/W₀) - x) × 0.5
  /// Best for: Moderate scaling needs
  interpolated,

  /// DIAGONAL - Scale based on diagonal (screen size)
  /// Formula: f(x) = x × √(W² + H²) / √(W₀² + H₀²)
  /// Best for: Elements that should match physical screen size
  diagonal,

  /// PERIMETER - Scale based on perimeter
  /// Formula: f(x) = x × (W + H) / (W₀ + H₀)
  /// Best for: General purpose, balanced scaling
  perimeter,

  /// FIT - Letterbox scaling (game fit)
  /// Formula: f(x) = x × min(W/W₀, H/H₀)
  /// Best for: Games, full-screen content that must fit
  fit,

  /// FILL - Cover scaling (game fill)
  /// Formula: f(x) = x × max(W/W₀, H/H₀)
  /// Best for: Games, backgrounds, full-screen content
  fill,

  /// AUTOSIZE - Auto-adjust based on component size
  /// Similar to AutoSizeText, adjusts value proportionally
  /// Best for: Dynamic text, auto-sizing typography
  autosize,

  /// NONE - No scaling (constant size)
  /// Formula: f(x) = x
  /// Best for: Fixed UI elements, absolute dimensions
  none;

  /// Returns a human-readable description of the strategy
  String get description {
    switch (this) {
      case ScalingStrategy.defaultStrategy:
        return 'DEFAULT: Fixed legacy (~97% linear + AR)';
      case ScalingStrategy.percentage:
        return 'PERCENTAGE: Dynamic legacy (100% linear)';
      case ScalingStrategy.balanced:
        return 'BALANCED: Linear phones, log tablets (Recommended)';
      case ScalingStrategy.logarithmic:
        return 'LOGARITHMIC: Pure log (Maximum control)';
      case ScalingStrategy.power:
        return 'POWER: Stevens power law (Scientific)';
      case ScalingStrategy.fluid:
        return 'FLUID: CSS clamp-like with breakpoints';
      case ScalingStrategy.interpolated:
        return 'INTERPOLATED: 50% moderated linear';
      case ScalingStrategy.diagonal:
        return 'DIAGONAL: Scale by screen diagonal';
      case ScalingStrategy.perimeter:
        return 'PERIMETER: Scale by width + height';
      case ScalingStrategy.fit:
        return 'FIT: Letterbox (game fit)';
      case ScalingStrategy.fill:
        return 'FILL: Cover (game fill)';
      case ScalingStrategy.autosize:
        return 'AUTOSIZE: Auto-adjust to container size';
      case ScalingStrategy.none:
        return 'NONE: No scaling (constant)';
    }
  }

  /// Returns recommended use cases
  String get recommendedFor {
    switch (this) {
      case ScalingStrategy.defaultStrategy:
        return 'Phone-only apps, icons, backward compatibility';
      case ScalingStrategy.percentage:
        return 'Containers, fluid layouts, proportional images';
      case ScalingStrategy.balanced:
        return 'Multi-device apps, buttons, spacing ⭐';
      case ScalingStrategy.logarithmic:
        return 'TVs, very large tablets, maximum control';
      case ScalingStrategy.power:
        return 'General purpose, configurable apps';
      case ScalingStrategy.fluid:
        return 'Typography, bounded spacing, smooth transitions';
      case ScalingStrategy.interpolated:
        return 'Moderate scaling, balanced growth';
      case ScalingStrategy.diagonal:
        return 'True screen size scaling, physical dimensions';
      case ScalingStrategy.perimeter:
        return 'Balanced W+H scaling, general purpose';
      case ScalingStrategy.fit:
        return 'Games (letterbox), content that must fit';
      case ScalingStrategy.fill:
        return 'Games (cover), backgrounds, full-screen';
      case ScalingStrategy.autosize:
        return 'Dynamic text, auto-sizing typography, variable containers';
      case ScalingStrategy.none:
        return 'Fixed UI elements, absolute sizes';
    }
  }

  /// Returns formula representation
  String get formula {
    switch (this) {
      case ScalingStrategy.defaultStrategy:
        return 'f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdj';
      case ScalingStrategy.percentage:
        return 'f(x) = x × (W / W₀)';
      case ScalingStrategy.balanced:
        return 'f(x) = x × (W/W₀) if W<480, else x × (1.6 + k×ln(...))';
      case ScalingStrategy.logarithmic:
        return 'f(x) = x × (1 + k × ln(W / W₀))';
      case ScalingStrategy.power:
        return 'f(x) = x × (W / W₀)^n';
      case ScalingStrategy.fluid:
        return 'f(x) = interpolate(min, max, W, minW, maxW)';
      case ScalingStrategy.interpolated:
        return 'f(x) = x + ((x × W/W₀) - x) × 0.5';
      case ScalingStrategy.diagonal:
        return 'f(x) = x × √(W² + H²) / √(W₀² + H₀²)';
      case ScalingStrategy.perimeter:
        return 'f(x) = x × (W + H) / (W₀ + H₀)';
      case ScalingStrategy.fit:
        return 'f(x) = x × min(W/W₀, H/H₀)';
      case ScalingStrategy.fill:
        return 'f(x) = x × max(W/W₀, H/H₀)';
      case ScalingStrategy.autosize:
        return 'f(x) = fitToSize(x, min, max, containerSize)';
      case ScalingStrategy.none:
        return 'f(x) = x';
    }
  }
}

