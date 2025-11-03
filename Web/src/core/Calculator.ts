/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Web 2.0 - Calculation Engine
 *
 * Description:
 * Unified calculation engine containing all 13 scaling strategy implementations.
 * Ported from Android AppDimens 2.0 Calculator.kt
 *
 * All calculation functions are pure and independent of platform-specific APIs.
 *
 * Licensed under the Apache License, Version 2.0
 */

import {ScalingStrategy, PerceptualModel, ElementType} from '../types/ScalingStrategy';
import type {BaseOrientation} from '../types/BaseOrientation';

// ============================================
// CONSTANTS
// ============================================

/**
 * Base reference width in dp for all calculations.
 */
export const BASE_WIDTH_DP = 300;

/**
 * Base reference height in dp for all calculations.
 */
export const BASE_HEIGHT_DP = 533;

/**
 * Reference aspect ratio (16:9 = 1.78).
 */
export const REFERENCE_AR = 1.78;

/**
 * Default sensitivity for perceptual models (Weber-Fechner, Balanced).
 */
export const DEFAULT_SENSITIVITY = 0.40;

/**
 * Default power exponent for Stevens Power Law.
 */
export const DEFAULT_POWER_EXPONENT = 0.75;

/**
 * Default transition point for Balanced model (where log starts).
 */
export const DEFAULT_TRANSITION_POINT = 480;

/**
 * Default aspect ratio sensitivity for Fixed/DEFAULT strategy.
 */
export const DEFAULT_AR_SENSITIVITY = 0.08 / 30;

/**
 * Base increment factor for Fixed/DEFAULT strategy.
 */
export const BASE_INCREMENT = 0.10 / 30;

// ============================================
// PRE-CALCULATED CONSTANTS (Performance Optimization)
// ============================================

/**
 * Pre-calculated base diagonal.
 * Formula: √(300² + 533²) ≈ 611.63
 */
const BASE_DIAGONAL = 611.6305;

/**
 * Pre-calculated base perimeter.
 * Formula: 300 + 533 = 833
 */
const BASE_PERIMETER = 833;

/**
 * Pre-calculated 1/BASE_WIDTH_DP for faster multiplication.
 */
const INV_BASE_WIDTH_DP = 1 / BASE_WIDTH_DP;

/**
 * Pre-calculated 1/REFERENCE_AR for faster aspect ratio calculations.
 */
const INV_REFERENCE_AR = 1 / REFERENCE_AR;

// ============================================
// LOOKUP TABLE FOR ln() (Performance Optimization)
// ============================================

/**
 * Fast ln() lookup table with binary search.
 * Pre-calculated values for common aspect ratios and screen ratios.
 */
class LnLookupTable {
  private static keys: number[] = [
    0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 1.1, 1.2, 1.25, 1.28, 1.3, 1.33, 1.333,
    1.367, 1.4, 1.414, 1.5, 1.6, 1.667, 1.7, 1.75, 1.777, 1.78, 1.8, 1.85,
    1.9, 2.0, 2.05, 2.1, 2.133, 2.16, 2.2, 2.223, 2.25, 2.3, 2.33, 2.4, 2.5,
    2.56, 2.6, 2.667, 2.7, 2.8, 2.9, 3.0, 3.2, 3.4, 3.5, 4.0, 4.5, 5.0, 6.0,
    7.0, 7.2,
  ];

  private static values: number[] = [
    -0.91629076, -0.6931472, -0.51082563, -0.35667494, -0.22314355,
    -0.10536052, 0.0, 0.09531018, 0.18232156, 0.22314355, 0.24686007,
    0.26236426, 0.28518318, 0.28728026, 0.31244648, 0.33647224, 0.34610766,
    0.4054651, 0.47000363, 0.51082563, 0.5306282, 0.5596158, 0.57472223,
    0.57660466, 0.58778667, 0.61518564, 0.64185388, 0.6931472, 0.7178398,
    0.74193734, 0.75750062, 0.77014756, 0.78845736, 0.79850769, 0.81093025,
    0.83290912, 0.84587455, 0.8754687, 0.91629076, 0.94002015, 0.95551145,
    0.98082924, 0.99325174, 1.02961942, 1.06471074, 1.09861229, 1.16315081,
    1.22377543, 1.25276297, 1.38629436, 1.50407739, 1.60943791, 1.79175947,
    1.94591015, 1.97408103,
  ];

  private static TOLERANCE = 0.005;

  /**
   * Binary search with tolerance for closest match.
   */
  static lookup(value: number): number | null {
    let low = 0;
    let high = this.keys.length - 1;

    while (low <= high) {
      const mid = Math.floor((low + high) / 2);
      const midVal = this.keys[mid];

      const diff = Math.abs(value - midVal);
      if (diff <= this.TOLERANCE) {
        return this.values[mid];
      }

      if (midVal < value) {
        low = mid + 1;
      } else {
        high = mid - 1;
      }
    }

    // Check neighbors
    if (high >= 0 && Math.abs(value - this.keys[high]) <= this.TOLERANCE) {
      return this.values[high];
    }

    if (
      low < this.keys.length &&
      Math.abs(value - this.keys[low]) <= this.TOLERANCE
    ) {
      return this.values[low];
    }

    return null;
  }
}

/**
 * Fast ln() with lookup table fallback.
 */
function fastLn(value: number): number {
  const cached = LnLookupTable.lookup(value);
  return cached !== null ? cached : Math.log(value);
}

// ============================================
// CONFIGURATION INTERFACES
// ============================================

export interface CalculationConfig {
  screenWidthDp: number;
  screenHeightDp: number;
  smallestScreenWidthDp: number;
  densityDpi: number;
}

export interface DefaultParams {
  applyAspectRatio?: boolean;
  arSensitivity?: number;
}

export interface PerceptualParams {
  model?: PerceptualModel;
  sensitivity?: number;
  powerExponent?: number;
  transitionPoint?: number;
  applyAspectRatio?: boolean;
  arSensitivity?: number;
}

export interface FluidParams {
  minValue: number;
  maxValue: number;
  minWidth?: number;
  maxWidth?: number;
}

export interface Constraints {
  minValue?: number;
  maxValue?: number;
  maxPhysicalMm?: number;
}

interface CalculationCache {
  aspectRatio?: number;
  smallestDimension?: number;
  largestDimension?: number;
  effectiveScreenType?: 'lowest' | 'highest';
  dimensionForType?: number;
}

// ============================================
// MAIN CALCULATION ENTRY POINT
// ============================================

/**
 * Calculates the scaled dimension value based on strategy and configuration.
 */
export function calculate(
  baseValue: number,
  strategy: ScalingStrategy,
  config: CalculationConfig,
  screenType: 'lowest' | 'highest' = 'lowest',
  baseOrientation: BaseOrientation = 'auto',
  defaultParams?: DefaultParams,
  perceptualParams?: PerceptualParams,
  fluidParams?: FluidParams,
  constraints?: Constraints,
): number {
  const cache: CalculationCache = {};

  let result: number;

  switch (strategy) {
    case ScalingStrategy.DEFAULT:
      result = calculateDefault(
        baseValue,
        config,
        screenType,
        baseOrientation,
        defaultParams || {},
        cache,
      );
      break;

    case ScalingStrategy.PERCENTAGE:
      result = calculatePercentage(
        baseValue,
        config,
        screenType,
        baseOrientation,
        cache,
      );
      break;

    case ScalingStrategy.BALANCED:
      result = calculateBalanced(
        baseValue,
        config,
        screenType,
        baseOrientation,
        perceptualParams || {},
        cache,
      );
      break;

    case ScalingStrategy.LOGARITHMIC:
      result = calculateLogarithmic(
        baseValue,
        config,
        screenType,
        baseOrientation,
        perceptualParams || {},
        cache,
      );
      break;

    case ScalingStrategy.POWER:
      result = calculatePower(
        baseValue,
        config,
        screenType,
        baseOrientation,
        perceptualParams || {},
        cache,
      );
      break;

    case ScalingStrategy.FLUID:
      if (!fluidParams) {
        throw new Error('FLUID strategy requires fluidParams');
      }
      result = calculateFluid(
        baseValue,
        config,
        screenType,
        baseOrientation,
        fluidParams,
        cache,
      );
      break;

    case ScalingStrategy.INTERPOLATED:
      result = calculateInterpolated(
        baseValue,
        config,
        screenType,
        baseOrientation,
        cache,
      );
      break;

    case ScalingStrategy.DIAGONAL:
      result = calculateDiagonal(baseValue, config, cache);
      break;

    case ScalingStrategy.PERIMETER:
      result = calculatePerimeter(baseValue, config, cache);
      break;

    case ScalingStrategy.FIT:
      result = calculateFit(baseValue, config, cache);
      break;

    case ScalingStrategy.FILL:
      result = calculateFill(baseValue, config, cache);
      break;

    case ScalingStrategy.AUTOSIZE:
      // AUTOSIZE requires runtime measurement, return base value as fallback
      result = baseValue;
      break;

    case ScalingStrategy.NONE:
      result = baseValue;
      break;

    default:
      result = baseValue;
  }

  // Apply constraints
  if (constraints) {
    if (constraints.minValue !== undefined) {
      result = Math.max(result, constraints.minValue);
    }
    if (constraints.maxValue !== undefined) {
      result = Math.min(result, constraints.maxValue);
    }
    if (constraints.maxPhysicalMm !== undefined) {
      const maxDp = (constraints.maxPhysicalMm / 25.4) * (config.densityDpi / 160);
      result = Math.min(result, maxDp);
    }
  }

  return result;
}

// ============================================
// STRATEGY CALCULATIONS
// ============================================

/**
 * Calculates DEFAULT strategy (Fixed legacy).
 * Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
 */
function calculateDefault(
  baseValue: number,
  config: CalculationConfig,
  screenType: 'lowest' | 'highest',
  baseOrientation: BaseOrientation,
  params: DefaultParams,
  cache: CalculationCache,
): number {
  const effectiveScreenType = resolveScreenType(
    screenType,
    baseOrientation,
    config,
    cache,
  );
  const screenDp = getDimensionForType(config, effectiveScreenType, cache);

  // Linear component: ~97% linear growth
  const linearScale = 1 + ((screenDp - BASE_WIDTH_DP) / 1) * BASE_INCREMENT;

  // Aspect ratio adjustment
  let arAdjustment = 1.0;
  if (params.applyAspectRatio !== false) {
    const ar = getAspectRatio(config, cache);
    const arSensitivity = params.arSensitivity || DEFAULT_AR_SENSITIVITY;
    arAdjustment = 1 + arSensitivity * fastLn(ar * INV_REFERENCE_AR);
  }

  return baseValue * linearScale * arAdjustment;
}

/**
 * Calculates PERCENTAGE strategy (Dynamic legacy).
 * Formula: f(x) = x × (W / W₀)
 */
function calculatePercentage(
  baseValue: number,
  config: CalculationConfig,
  screenType: 'lowest' | 'highest',
  baseOrientation: BaseOrientation,
  cache: CalculationCache,
): number {
  const effectiveScreenType = resolveScreenType(
    screenType,
    baseOrientation,
    config,
    cache,
  );
  const screenDp = getDimensionForType(config, effectiveScreenType, cache);

  return baseValue * (screenDp / BASE_WIDTH_DP);
}

/**
 * Calculates BALANCED strategy (Perceptual Hybrid).
 * 
 * Formula:
 * - if W < 480: f(x) = x × (W / W₀)
 * - if W ≥ 480: f(x) = x × (1.6 + sensitivity × ln(1 + (W-480)/W₀))
 */
function calculateBalanced(
  baseValue: number,
  config: CalculationConfig,
  screenType: 'lowest' | 'highest',
  baseOrientation: BaseOrientation,
  params: PerceptualParams,
  cache: CalculationCache,
): number {
  const effectiveScreenType = resolveScreenType(
    screenType,
    baseOrientation,
    config,
    cache,
  );
  const screenDp = getDimensionForType(config, effectiveScreenType, cache);

  const transitionPoint = params.transitionPoint || DEFAULT_TRANSITION_POINT;
  const sensitivity = params.sensitivity || DEFAULT_SENSITIVITY;

  if (screenDp < transitionPoint) {
    // Linear scaling on smaller screens (phones)
    return baseValue * (screenDp / BASE_WIDTH_DP);
  } else {
    // Logarithmic scaling on larger screens (tablets, TVs)
    const excess = screenDp - transitionPoint;
    const scale =
      transitionPoint * INV_BASE_WIDTH_DP +
      sensitivity * fastLn(1 + excess * INV_BASE_WIDTH_DP);
    return baseValue * scale;
  }
}

/**
 * Calculates LOGARITHMIC strategy (Perceptual Weber-Fechner).
 * Formula: f(x) = x × (1 + sensitivity × ln(W / W₀))
 */
function calculateLogarithmic(
  baseValue: number,
  config: CalculationConfig,
  screenType: 'lowest' | 'highest',
  baseOrientation: BaseOrientation,
  params: PerceptualParams,
  cache: CalculationCache,
): number {
  const effectiveScreenType = resolveScreenType(
    screenType,
    baseOrientation,
    config,
    cache,
  );
  const screenDp = getDimensionForType(config, effectiveScreenType, cache);

  const sensitivity = params.sensitivity || DEFAULT_SENSITIVITY;

  const scale =
    screenDp > BASE_WIDTH_DP
      ? 1.0 + sensitivity * fastLn(screenDp * INV_BASE_WIDTH_DP)
      : 1.0 - sensitivity * fastLn(BASE_WIDTH_DP / screenDp);

  return baseValue * scale;
}

/**
 * Calculates POWER strategy (Perceptual Stevens).
 * Formula: f(x) = x × (W / W₀)^exponent
 */
function calculatePower(
  baseValue: number,
  config: CalculationConfig,
  screenType: 'lowest' | 'highest',
  baseOrientation: BaseOrientation,
  params: PerceptualParams,
  cache: CalculationCache,
): number {
  const effectiveScreenType = resolveScreenType(
    screenType,
    baseOrientation,
    config,
    cache,
  );
  const screenDp = getDimensionForType(config, effectiveScreenType, cache);

  const exponent = params.powerExponent || DEFAULT_POWER_EXPONENT;
  const ratio = screenDp / BASE_WIDTH_DP;
  const scale = Math.pow(ratio, exponent);

  return baseValue * scale;
}

/**
 * Calculates FLUID strategy (CSS clamp-like).
 * Formula: clamp(minValue, interpolate(W), maxValue)
 */
function calculateFluid(
  baseValue: number,
  config: CalculationConfig,
  screenType: 'lowest' | 'highest',
  baseOrientation: BaseOrientation,
  params: FluidParams,
  cache: CalculationCache,
): number {
  const effectiveScreenType = resolveScreenType(
    screenType,
    baseOrientation,
    config,
    cache,
  );
  const width = getDimensionForType(config, effectiveScreenType, cache);

  const minWidth = params.minWidth || 320;
  const maxWidth = params.maxWidth || 768;

  if (width <= minWidth) {
    return params.minValue;
  } else if (width >= maxWidth) {
    return params.maxValue;
  } else {
    // Linear interpolation
    const progress = (width - minWidth) / (maxWidth - minWidth);
    return params.minValue + (params.maxValue - params.minValue) * progress;
  }
}

/**
 * Calculates INTERPOLATED strategy.
 * Formula: f(x) = x + ((x × W/W₀) - x) × 0.5
 */
function calculateInterpolated(
  baseValue: number,
  config: CalculationConfig,
  screenType: 'lowest' | 'highest',
  baseOrientation: BaseOrientation,
  cache: CalculationCache,
): number {
  const effectiveScreenType = resolveScreenType(
    screenType,
    baseOrientation,
    config,
    cache,
  );
  const W = getDimensionForType(config, effectiveScreenType, cache);

  const linear = baseValue * (W / BASE_WIDTH_DP);
  return baseValue + (linear - baseValue) * 0.5;
}

/**
 * Calculates DIAGONAL strategy.
 * Formula: f(x) = x × √(W² + H²) / BASE_DIAGONAL
 */
function calculateDiagonal(
  baseValue: number,
  config: CalculationConfig,
  cache: CalculationCache,
): number {
  const smallest =
    cache.smallestDimension ||
    Math.min(config.screenWidthDp, config.screenHeightDp);
  const largest =
    cache.largestDimension ||
    Math.max(config.screenWidthDp, config.screenHeightDp);

  const currentDiag = Math.sqrt(smallest * smallest + largest * largest);
  return baseValue * (currentDiag / BASE_DIAGONAL);
}

/**
 * Calculates PERIMETER strategy.
 * Formula: f(x) = x × (W + H) / BASE_PERIMETER
 */
function calculatePerimeter(
  baseValue: number,
  config: CalculationConfig,
  cache: CalculationCache,
): number {
  const smallest =
    cache.smallestDimension ||
    Math.min(config.screenWidthDp, config.screenHeightDp);
  const largest =
    cache.largestDimension ||
    Math.max(config.screenWidthDp, config.screenHeightDp);

  return baseValue * ((smallest + largest) / BASE_PERIMETER);
}

/**
 * Calculates FIT strategy (game letterbox).
 * Formula: f(x) = x × min(W/W₀, H/H₀)
 */
function calculateFit(
  baseValue: number,
  config: CalculationConfig,
  cache: CalculationCache,
): number {
  const smallest =
    cache.smallestDimension ||
    Math.min(config.screenWidthDp, config.screenHeightDp);
  const largest =
    cache.largestDimension ||
    Math.max(config.screenWidthDp, config.screenHeightDp);

  const ratioW = smallest / BASE_WIDTH_DP;
  const ratioH = largest / BASE_HEIGHT_DP;

  return baseValue * Math.min(ratioW, ratioH);
}

/**
 * Calculates FILL strategy (game cover).
 * Formula: f(x) = x × max(W/W₀, H/H₀)
 */
function calculateFill(
  baseValue: number,
  config: CalculationConfig,
  cache: CalculationCache,
): number {
  const smallest =
    cache.smallestDimension ||
    Math.min(config.screenWidthDp, config.screenHeightDp);
  const largest =
    cache.largestDimension ||
    Math.max(config.screenWidthDp, config.screenHeightDp);

  const ratioW = smallest / BASE_WIDTH_DP;
  const ratioH = largest / BASE_HEIGHT_DP;

  return baseValue * Math.max(ratioW, ratioH);
}

// ============================================
// HELPER METHODS
// ============================================

/**
 * Resolves effective screen type based on base orientation.
 */
function resolveScreenType(
  requestedType: 'lowest' | 'highest',
  baseOrientation: BaseOrientation,
  config: CalculationConfig,
  cache: CalculationCache,
): 'lowest' | 'highest' {
  if (cache.effectiveScreenType) {
    return cache.effectiveScreenType;
  }

  const currentIsPortrait = config.screenHeightDp > config.screenWidthDp;

  let effectiveType: 'lowest' | 'highest';

  switch (baseOrientation) {
    case 'portrait':
      effectiveType = currentIsPortrait
        ? requestedType
        : requestedType === 'lowest'
          ? 'highest'
          : 'lowest';
      break;
    case 'landscape':
      effectiveType = !currentIsPortrait
        ? requestedType
        : requestedType === 'lowest'
          ? 'highest'
          : 'lowest';
      break;
    case 'auto':
    default:
      effectiveType = requestedType;
  }

  cache.effectiveScreenType = effectiveType;
  return effectiveType;
}

/**
 * Gets dimension for the specified screen type.
 */
function getDimensionForType(
  config: CalculationConfig,
  screenType: 'lowest' | 'highest',
  cache: CalculationCache,
): number {
  if (cache.dimensionForType) {
    return cache.dimensionForType;
  }

  const smallest = getSmallestDimension(config, cache);
  const largest = getLargestDimension(config, cache);

  const dimension = screenType === 'lowest' ? smallest : largest;
  cache.dimensionForType = dimension;
  return dimension;
}

/**
 * Gets smallest screen dimension.
 */
function getSmallestDimension(
  config: CalculationConfig,
  cache: CalculationCache,
): number {
  if (cache.smallestDimension) {
    return cache.smallestDimension;
  }

  const smallest = Math.min(config.screenWidthDp, config.screenHeightDp);
  cache.smallestDimension = smallest;
  return smallest;
}

/**
 * Gets largest screen dimension.
 */
function getLargestDimension(
  config: CalculationConfig,
  cache: CalculationCache,
): number {
  if (cache.largestDimension) {
    return cache.largestDimension;
  }

  const largest = Math.max(config.screenWidthDp, config.screenHeightDp);
  cache.largestDimension = largest;
  return largest;
}

/**
 * Gets aspect ratio with caching.
 */
function getAspectRatio(
  config: CalculationConfig,
  cache: CalculationCache,
): number {
  if (cache.aspectRatio) {
    return cache.aspectRatio;
  }

  const smallest = getSmallestDimension(config, cache);
  const largest = getLargestDimension(config, cache);

  const ar = largest / smallest;
  cache.aspectRatio = ar;
  return ar;
}

// ============================================
// SMART INFERENCE
// ============================================

/**
 * Infers best strategy for element type.
 */
export function inferStrategy(elementType: ElementType): ScalingStrategy {
  switch (elementType) {
    case ElementType.BUTTON:
    case ElementType.CHIP:
    case ElementType.FAB:
      return ScalingStrategy.BALANCED;

    case ElementType.TEXT:
    case ElementType.HEADER:
      return ScalingStrategy.BALANCED;

    case ElementType.ICON:
    case ElementType.BADGE:
      return ScalingStrategy.DEFAULT;

    case ElementType.IMAGE:
    case ElementType.CONTAINER:
    case ElementType.CARD:
      return ScalingStrategy.PERCENTAGE;

    case ElementType.SPACING:
      return ScalingStrategy.BALANCED;

    case ElementType.DIVIDER:
      return ScalingStrategy.NONE;

    case ElementType.GAME_UI:
      return ScalingStrategy.FIT;

    default:
      return ScalingStrategy.BALANCED;
  }
}

