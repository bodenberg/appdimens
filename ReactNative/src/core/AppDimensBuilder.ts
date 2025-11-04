/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens React Native 2.0 - Unified Builder
 *
 * Description:
 * Unified builder supporting all 13 scaling strategies.
 * This is the main API for AppDimens 2.0.
 *
 * Licensed under the Apache License, Version 2.0
 */

import {Dimensions, PixelRatio} from 'react-native';
import {
  ScalingStrategy,
  PerceptualModel,
  ElementType,
} from '../types/ScalingStrategy';
import type {BaseOrientation} from '../types/BaseOrientation';
import * as Calculator from './Calculator';
import type {
  CalculationConfig,
  DefaultParams,
  PerceptualParams,
  FluidParams,
  InterpolatedParams,
  Constraints,
} from './Calculator';

/**
 * Unified AppDimens builder with all 13 strategies.
 * 
 * Usage:
 * ```tsx
 * // BALANCED strategy (recommended)
 * const size = new AppDimensBuilder(48).balanced().toPixels();
 * 
 * // LOGARITHMIC strategy
 * const size = new AppDimensBuilder(48).logarithmic().toPixels();
 * 
 * // POWER strategy with custom exponent
 * const size = new AppDimensBuilder(48).power(0.8).toPixels();
 * 
 * // Smart inference
 * const size = new AppDimensBuilder(48).forElement(ElementType.BUTTON).toPixels();
 * ```
 */
export class AppDimensBuilder {
  private baseValue: number;
  private strategy: ScalingStrategy = ScalingStrategy.BALANCED;
  private screenType: 'lowest' | 'highest' = 'lowest';
  private _baseOrientation: BaseOrientation = 'auto';

  // Strategy parameters
  private defaultParams: DefaultParams = {};
  private perceptualParams: PerceptualParams = {};
  private fluidParams?: FluidParams;
  private interpolatedParams: InterpolatedParams = {};
  private constraints: Constraints = {};

  constructor(value: number) {
    this.baseValue = value;
  }

  // ============================================
  // STRATEGY SELECTION METHODS
  // ============================================

  /**
   * Use BALANCED strategy (recommended).
   * Linear on phones (<480dp), logarithmic on tablets/TVs.
   */
  balanced(): this {
    this.strategy = ScalingStrategy.BALANCED;
    this.perceptualParams.model = PerceptualModel.BALANCED;
    return this;
  }

  /**
   * Use LOGARITHMIC strategy (pure Weber-Fechner).
   * Maximum control on large screens.
   */
  logarithmic(): this {
    this.strategy = ScalingStrategy.LOGARITHMIC;
    this.perceptualParams.model = PerceptualModel.WEBER_FECHNER;
    return this;
  }

  /**
   * Use POWER strategy (Stevens' Power Law).
   * @param exponent Power exponent (0.0-1.0), default 0.75
   */
  power(exponent?: number): this {
    this.strategy = ScalingStrategy.POWER;
    this.perceptualParams.model = PerceptualModel.STEVENS;
    if (exponent !== undefined) {
      this.perceptualParams.powerExponent = exponent;
    }
    return this;
  }

  /**
   * Use DEFAULT strategy (Fixed legacy).
   * ~97% linear + aspect ratio adjustment.
   */
  default(): this {
    this.strategy = ScalingStrategy.DEFAULT;
    return this;
  }

  /**
   * Use PERCENTAGE strategy (Dynamic legacy).
   * 100% proportional scaling.
   */
  percentage(): this {
    this.strategy = ScalingStrategy.PERCENTAGE;
    return this;
  }

  /**
   * Use FLUID strategy (CSS clamp-like).
   * @param minValue Minimum value
   * @param maxValue Maximum value
   * @param minWidth Minimum width breakpoint (default 320)
   * @param maxWidth Maximum width breakpoint (default 768)
   */
  fluid(
    minValue: number,
    maxValue: number,
    minWidth?: number,
    maxWidth?: number,
  ): this {
    this.strategy = ScalingStrategy.FLUID;
    this.fluidParams = {
      minValue,
      maxValue,
      minWidth,
      maxWidth,
    };
    return this;
  }

  /**
   * Use INTERPOLATED strategy.
   * 50% moderated linear growth.
   */
  interpolated(): this {
    this.strategy = ScalingStrategy.INTERPOLATED;
    return this;
  }

  /**
   * Use DIAGONAL strategy.
   * Scale based on screen diagonal.
   */
  diagonal(): this {
    this.strategy = ScalingStrategy.DIAGONAL;
    return this;
  }

  /**
   * Use PERIMETER strategy.
   * Scale based on width + height.
   */
  perimeter(): this {
    this.strategy = ScalingStrategy.PERIMETER;
    return this;
  }

  /**
   * Use FIT strategy (game letterbox).
   * Uses min(W/W₀, H/H₀).
   */
  fit(): this {
    this.strategy = ScalingStrategy.FIT;
    return this;
  }

  /**
   * Use FILL strategy (game cover).
   * Uses max(W/W₀, H/H₀).
   */
  fill(): this {
    this.strategy = ScalingStrategy.FILL;
    return this;
  }

  /**
   * Use AUTOSIZE strategy.
   * Auto-adjust based on container size.
   */
  autosize(): this {
    this.strategy = ScalingStrategy.AUTOSIZE;
    return this;
  }

  /**
   * Use NONE strategy.
   * No scaling (constant size).
   */
  none(): this {
    this.strategy = ScalingStrategy.NONE;
    return this;
  }

  // ============================================
  // SMART INFERENCE
  // ============================================

  /**
   * Auto-select best strategy for element type.
   */
  forElement(elementType: ElementType): this {
    this.strategy = Calculator.inferStrategy(elementType);
    return this;
  }

  // ============================================
  // CONFIGURATION METHODS
  // ============================================

  /**
   * Set screen type (lowest or highest dimension).
   */
  screen(type: 'lowest' | 'highest'): this {
    this.screenType = type;
    return this;
  }

  /**
   * Set base orientation for automatic inversion.
   */
  baseOrientation(orientation: BaseOrientation): this {
    this._baseOrientation = orientation;
    return this;
  }

  /**
   * Portrait with lowest dimension.
   */
  portraitLowest(): this {
    this._baseOrientation = 'portrait';
    this.screenType = 'lowest';
    return this;
  }

  /**
   * Portrait with highest dimension.
   */
  portraitHighest(): this {
    this._baseOrientation = 'portrait';
    this.screenType = 'highest';
    return this;
  }

  /**
   * Landscape with lowest dimension.
   */
  landscapeLowest(): this {
    this._baseOrientation = 'landscape';
    this.screenType = 'lowest';
    return this;
  }

  /**
   * Landscape with highest dimension.
   */
  landscapeHighest(): this {
    this._baseOrientation = 'landscape';
    this.screenType = 'highest';
    return this;
  }

  /**
   * Set sensitivity for perceptual models.
   * @param sensitivity Value between 0.0-1.0 (default 0.40)
   */
  withSensitivity(sensitivity: number): this {
    this.perceptualParams.sensitivity = sensitivity;
    return this;
  }

  /**
   * Set transition point for BALANCED model.
   * @param point Transition point in dp (default 480)
   */
  withTransitionPoint(point: number): this {
    this.perceptualParams.transitionPoint = point;
    return this;
  }

  /**
   * Enable/disable aspect ratio adjustment for DEFAULT strategy.
   */
  withAspectRatio(enable: boolean): this {
    this.defaultParams.applyAspectRatio = enable;
    return this;
  }

  /**
   * Set aspect ratio sensitivity for DEFAULT strategy.
   */
  withArSensitivity(sensitivity: number): this {
    this.defaultParams.arSensitivity = sensitivity;
    return this;
  }

  /**
   * Set minimum value constraint.
   */
  withMin(minValue: number): this {
    this.constraints.minValue = minValue;
    return this;
  }

  /**
   * Set maximum value constraint.
   */
  withMax(maxValue: number): this {
    this.constraints.maxValue = maxValue;
    return this;
  }

  /**
   * Set maximum physical size constraint in millimeters.
   */
  withMaxPhysicalMm(maxMm: number): this {
    this.constraints.maxPhysicalMm = maxMm;
    return this;
  }

  // ============================================
  // CALCULATION METHODS
  // ============================================

  /**
   * Calculate and return the value in pixels.
   */
  toPixels(): number {
    const config = this.getCurrentConfig();
    const result = Calculator.calculate(
      this.baseValue,
      this.strategy,
      config,
      this.screenType,
      this._baseOrientation,
      this.defaultParams,
      this.perceptualParams,
      this.fluidParams,
      this.interpolatedParams,
      this.constraints,
    );
    return result;
  }

  /**
   * Calculate and return the value in pixels as integer.
   */
  toPixelsInt(): number {
    return Math.round(this.toPixels());
  }

  /**
   * Calculate and return the value in points (dp).
   */
  toPoints(): number {
    return this.toPixels() / PixelRatio.get();
  }

  /**
   * Calculate and return the value in points as integer.
   */
  toPointsInt(): number {
    return Math.round(this.toPoints());
  }

  /**
   * Calculate and return the value in sp (scalable pixels for text).
   */
  toSp(): number {
    const fontScale = PixelRatio.getFontScale();
    return this.toPoints() * fontScale;
  }

  /**
   * Calculate and return the value in sp as integer.
   */
  toSpInt(): number {
    return Math.round(this.toSp());
  }

  /**
   * Calculate and return the value in em units.
   */
  toEm(): number {
    return this.toPoints();
  }

  /**
   * Calculate and return the value in em as integer.
   */
  toEmInt(): number {
    return Math.round(this.toEm());
  }

  // ============================================
  // HELPER METHODS
  // ============================================

  /**
   * Get current screen configuration.
   */
  private getCurrentConfig(): CalculationConfig {
    const {width, height} = Dimensions.get('window');
    const pixelRatio = PixelRatio.get();

    // Convert pixels to dp
    const screenWidthDp = width;
    const screenHeightDp = height;

    return {
      screenWidthDp,
      screenHeightDp,
      smallestScreenWidthDp: Math.min(screenWidthDp, screenHeightDp),
      densityDpi: Math.round(160 * pixelRatio),
    };
  }
}

// ============================================
// CONVENIENCE FUNCTIONS
// ============================================

/**
 * Create a new AppDimensBuilder with BALANCED strategy (recommended).
 */
export function balanced(value: number): AppDimensBuilder {
  return new AppDimensBuilder(value).balanced();
}

/**
 * Create a new AppDimensBuilder with LOGARITHMIC strategy.
 */
export function logarithmic(value: number): AppDimensBuilder {
  return new AppDimensBuilder(value).logarithmic();
}

/**
 * Create a new AppDimensBuilder with POWER strategy.
 */
export function power(value: number, exponent?: number): AppDimensBuilder {
  return new AppDimensBuilder(value).power(exponent);
}

/**
 * Create a new AppDimensBuilder with DEFAULT strategy.
 */
export function defaultStrategy(value: number): AppDimensBuilder {
  return new AppDimensBuilder(value).default();
}

/**
 * Create a new AppDimensBuilder with PERCENTAGE strategy.
 */
export function percentage(value: number): AppDimensBuilder {
  return new AppDimensBuilder(value).percentage();
}

/**
 * Create a new AppDimensBuilder with FLUID strategy.
 */
export function fluidStrategy(
  value: number,
  minValue: number,
  maxValue: number,
): AppDimensBuilder {
  return new AppDimensBuilder(value).fluid(minValue, maxValue);
}

/**
 * Create a new AppDimensBuilder with smart inference.
 */
export function smart(value: number, elementType: ElementType): AppDimensBuilder {
  return new AppDimensBuilder(value).forElement(elementType);
}

/**
 * Legacy compatibility: create builder with DEFAULT strategy.
 */
export function fx(value: number): AppDimensBuilder {
  return new AppDimensBuilder(value).default();
}

/**
 * Legacy compatibility: create builder with PERCENTAGE strategy.
 */
export function dy(value: number): AppDimensBuilder {
  return new AppDimensBuilder(value).percentage();
}

