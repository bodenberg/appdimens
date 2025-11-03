/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Web 2.0 - Scaling Strategies
 *
 * Description:
 * Unified enum defining all available scaling strategies for AppDimens Web.
 * Ported from Android AppDimens 2.0.
 *
 * Version 2.0 introduces unified scaling with 13 different strategies.
 *
 * Licensed under the Apache License, Version 2.0
 */

/**
 * Enum representing all available scaling strategies in AppDimens 2.0.
 */
export enum ScalingStrategy {
  DEFAULT = 'DEFAULT',
  PERCENTAGE = 'PERCENTAGE',
  BALANCED = 'BALANCED',
  LOGARITHMIC = 'LOGARITHMIC',
  POWER = 'POWER',
  FLUID = 'FLUID',
  INTERPOLATED = 'INTERPOLATED',
  DIAGONAL = 'DIAGONAL',
  PERIMETER = 'PERIMETER',
  FIT = 'FIT',
  FILL = 'FILL',
  AUTOSIZE = 'AUTOSIZE',
  NONE = 'NONE',
}

/**
 * Perceptual model types
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
 * Returns recommended use cases
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

