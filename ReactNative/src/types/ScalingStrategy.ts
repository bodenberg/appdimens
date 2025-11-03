/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens React Native 2.0 - Scaling Strategies
 *
 * Description:
 * Unified enum defining all available scaling strategies for AppDimens React Native.
 * Ported from Android AppDimens 2.0.
 *
 * Version 2.0 introduces unified scaling with 13 different strategies.
 *
 * Licensed under the Apache License, Version 2.0
 */

/**
 * Enum representing all available scaling strategies in AppDimens 2.0.
 * 
 * Available strategies:
 * - DEFAULT: Fixed legacy (~97% linear + AR adjustment)
 * - PERCENTAGE: Dynamic legacy (100% linear, proportional)
 * - BALANCED: Perceptual Hybrid (linear phones, log tablets) ⭐ RECOMMENDED
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
export enum ScalingStrategy {
  /**
   * DEFAULT - Fixed legacy (~97% linear + aspect ratio adjustment)
   * 
   * Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
   * 
   * Best for: Phone-only apps, icons, backward compatibility
   */
  DEFAULT = 'DEFAULT',

  /**
   * PERCENTAGE - Dynamic legacy (100% proportional)
   * 
   * Formula: f(x) = x × (W / W₀)
   * 
   * Best for: Containers, fluid layouts, images
   */
  PERCENTAGE = 'PERCENTAGE',

  /**
   * BALANCED - Perceptual Hybrid (recommended) ⭐
   * 
   * Formula: 
   * - if W < 480: f(x) = x × (W / W₀)
   * - if W ≥ 480: f(x) = x × (1.6 + sensitivity × ln(1 + (W-480)/W₀))
   * 
   * Best for: Multi-device apps, buttons, spacing
   */
  BALANCED = 'BALANCED',

  /**
   * LOGARITHMIC - Perceptual Weber-Fechner (maximum control)
   * 
   * Formula: f(x) = x × (1 + sensitivity × ln(W / W₀))
   * 
   * Best for: TVs, very large tablets
   */
  LOGARITHMIC = 'LOGARITHMIC',

  /**
   * POWER - Perceptual Stevens (scientific)
   * 
   * Formula: f(x) = x × (W / W₀)^exponent
   * 
   * Best for: General purpose, configurable apps
   */
  POWER = 'POWER',

  /**
   * FLUID - CSS clamp-like interpolation
   * 
   * Formula: clamp(minValue, interpolate(W), maxValue)
   * 
   * Best for: Typography, bounded spacing
   */
  FLUID = 'FLUID',

  /**
   * INTERPOLATED - Moderated linear interpolation
   * 
   * Formula: f(x) = x + ((x × W/W₀) - x) × 0.5
   * 
   * Best for: Moderate scaling needs
   */
  INTERPOLATED = 'INTERPOLATED',

  /**
   * DIAGONAL - Scale based on diagonal (screen size)
   * 
   * Formula: f(x) = x × √(W² + H²) / √(W₀² + H₀²)
   * 
   * Best for: Elements that should match physical screen size
   */
  DIAGONAL = 'DIAGONAL',

  /**
   * PERIMETER - Scale based on perimeter
   * 
   * Formula: f(x) = x × (W + H) / (W₀ + H₀)
   * 
   * Best for: General purpose, balanced scaling
   */
  PERIMETER = 'PERIMETER',

  /**
   * FIT - Letterbox scaling (game fit)
   * 
   * Formula: f(x) = x × min(W/W₀, H/H₀)
   * 
   * Best for: Games, full-screen content that must fit
   */
  FIT = 'FIT',

  /**
   * FILL - Cover scaling (game fill)
   * 
   * Formula: f(x) = x × max(W/W₀, H/H₀)
   * 
   * Best for: Games, backgrounds, full-screen content
   */
  FILL = 'FILL',

  /**
   * AUTOSIZE - Auto-adjust based on component size
   * 
   * Formula: f(x) = fitToSize(x, minSize, maxSize, containerSize)
   * 
   * Best for: Dynamic text, auto-sizing typography
   */
  AUTOSIZE = 'AUTOSIZE',

  /**
   * NONE - No scaling (constant size)
   * 
   * Formula: f(x) = x
   * 
   * Best for: Fixed size requirements, absolute dimensions
   */
  NONE = 'NONE',
}

/**
 * Perceptual model types for perceptual strategies
 */
export enum PerceptualModel {
  BALANCED = 'BALANCED',
  WEBER_FECHNER = 'WEBER_FECHNER',
  STEVENS = 'STEVENS',
}

/**
 * Element types for smart inference
 */
export enum ElementType {
  BUTTON = 'BUTTON',
  TEXT = 'TEXT',
  ICON = 'ICON',
  IMAGE = 'IMAGE',
  CONTAINER = 'CONTAINER',
  CARD = 'CARD',
  DIALOG = 'DIALOG',
  FAB = 'FAB',
  CHIP = 'CHIP',
  LIST_ITEM = 'LIST_ITEM',
  BADGE = 'BADGE',
  DIVIDER = 'DIVIDER',
  SPACING = 'SPACING',
  NAVIGATION = 'NAVIGATION',
  INPUT = 'INPUT',
  HEADER = 'HEADER',
  TOOLBAR = 'TOOLBAR',
  GAME_UI = 'GAME_UI',
}

/**
 * Returns human-readable description of the strategy
 */
export function getStrategyDescription(strategy: ScalingStrategy): string {
  switch (strategy) {
    case ScalingStrategy.DEFAULT:
      return 'DEFAULT: Fixed legacy (~97% linear + AR)';
    case ScalingStrategy.PERCENTAGE:
      return 'PERCENTAGE: Dynamic legacy (100% linear)';
    case ScalingStrategy.BALANCED:
      return 'BALANCED: Linear phones, log tablets (Recommended)';
    case ScalingStrategy.LOGARITHMIC:
      return 'LOGARITHMIC: Pure log (Maximum control)';
    case ScalingStrategy.POWER:
      return 'POWER: Stevens power law (Scientific)';
    case ScalingStrategy.FLUID:
      return 'FLUID: CSS clamp-like with breakpoints';
    case ScalingStrategy.INTERPOLATED:
      return 'INTERPOLATED: 50% moderated linear';
    case ScalingStrategy.DIAGONAL:
      return 'DIAGONAL: Scale by screen diagonal';
    case ScalingStrategy.PERIMETER:
      return 'PERIMETER: Scale by width + height';
    case ScalingStrategy.FIT:
      return 'FIT: Letterbox (game fit)';
    case ScalingStrategy.FILL:
      return 'FILL: Cover (game fill)';
    case ScalingStrategy.AUTOSIZE:
      return 'AUTOSIZE: Auto-adjust to container size';
    case ScalingStrategy.NONE:
      return 'NONE: No scaling (constant)';
  }
}

/**
 * Returns recommended use cases for the strategy
 */
export function getStrategyRecommendation(strategy: ScalingStrategy): string {
  switch (strategy) {
    case ScalingStrategy.DEFAULT:
      return 'Phone-only apps, icons, backward compatibility';
    case ScalingStrategy.PERCENTAGE:
      return 'Containers, fluid layouts, proportional images';
    case ScalingStrategy.BALANCED:
      return 'Multi-device apps, buttons, spacing ⭐';
    case ScalingStrategy.LOGARITHMIC:
      return 'TVs, very large tablets, maximum control';
    case ScalingStrategy.POWER:
      return 'General purpose, configurable apps';
    case ScalingStrategy.FLUID:
      return 'Typography, bounded spacing, smooth transitions';
    case ScalingStrategy.INTERPOLATED:
      return 'Moderate scaling, balanced growth';
    case ScalingStrategy.DIAGONAL:
      return 'True screen size scaling, physical dimensions';
    case ScalingStrategy.PERIMETER:
      return 'Balanced W+H scaling, general purpose';
    case ScalingStrategy.FIT:
      return 'Games (letterbox), content that must fit';
    case ScalingStrategy.FILL:
      return 'Games (cover), backgrounds, full-screen';
    case ScalingStrategy.AUTOSIZE:
      return 'Dynamic text, auto-sizing typography, variable containers';
    case ScalingStrategy.NONE:
      return 'Fixed UI elements, absolute sizes';
  }
}

/**
 * Returns formula representation
 */
export function getStrategyFormula(strategy: ScalingStrategy): string {
  switch (strategy) {
    case ScalingStrategy.DEFAULT:
      return 'f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdj';
    case ScalingStrategy.PERCENTAGE:
      return 'f(x) = x × (W / W₀)';
    case ScalingStrategy.BALANCED:
      return 'f(x) = x × (W/W₀) if W<480, else x × (1.6 + k×ln(...))';
    case ScalingStrategy.LOGARITHMIC:
      return 'f(x) = x × (1 + k × ln(W / W₀))';
    case ScalingStrategy.POWER:
      return 'f(x) = x × (W / W₀)^n';
    case ScalingStrategy.FLUID:
      return 'f(x) = interpolate(min, max, W, minW, maxW)';
    case ScalingStrategy.INTERPOLATED:
      return 'f(x) = x + ((x × W/W₀) - x) × 0.5';
    case ScalingStrategy.DIAGONAL:
      return 'f(x) = x × √(W² + H²) / √(W₀² + H₀²)';
    case ScalingStrategy.PERIMETER:
      return 'f(x) = x × (W + H) / (W₀ + H₀)';
    case ScalingStrategy.FIT:
      return 'f(x) = x × min(W/W₀, H/H₀)';
    case ScalingStrategy.FILL:
      return 'f(x) = x × max(W/W₀, H/H₀)';
    case ScalingStrategy.AUTOSIZE:
      return 'f(x) = fitToSize(x, min, max, containerSize)';
    case ScalingStrategy.NONE:
      return 'f(x) = x';
  }
}

