/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Web 2.0 - Unified Builder
 *
 * Description:
 * Unified builder supporting all 13 scaling strategies for Web.
 * This is the main API for AppDimens Web 2.0.
 *
 * Licensed under the Apache License, Version 2.0
 */

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
  Constraints,
} from './Calculator';
import { globalViewportObserver } from '../observers/ViewportObserver';

/**
 * Unified WebDimens builder with all 13 strategies.
 * 
 * Usage:
 * ```typescript
 * // BALANCED strategy (recommended)
 * const size = new WebDimensBuilder(48).balanced().px();
 * 
 * // LOGARITHMIC strategy
 * const size = new WebDimensBuilder(48).logarithmic().px();
 * 
 * // POWER strategy with custom exponent
 * const size = new WebDimensBuilder(48).power(0.8).px();
 * 
 * // Smart inference
 * const size = new WebDimensBuilder(48).forElement(ElementType.BUTTON).px();
 * ```
 */
export class WebDimensBuilder {
  private baseValue: number;
  private strategy: ScalingStrategy = ScalingStrategy.BALANCED;
  private screenType: 'lowest' | 'highest' = 'lowest';
  private _baseOrientation: BaseOrientation = 'auto';

  // Strategy parameters
  private defaultParams: DefaultParams = {};
  private perceptualParams: PerceptualParams = {};
  private fluidParams?: FluidParams;
  private constraints: Constraints = {};

  constructor(value: number) {
    this.baseValue = value;
  }

  // ============================================
  // STRATEGY SELECTION METHODS
  // ============================================

  /**
   * Use BALANCED strategy (recommended).
   */
  balanced(): this {
    this.strategy = ScalingStrategy.BALANCED;
    this.perceptualParams.model = PerceptualModel.BALANCED;
    return this;
  }

  /**
   * Use LOGARITHMIC strategy.
   */
  logarithmic(): this {
    this.strategy = ScalingStrategy.LOGARITHMIC;
    this.perceptualParams.model = PerceptualModel.WEBER_FECHNER;
    return this;
  }

  /**
   * Use POWER strategy.
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
   */
  default(): this {
    this.strategy = ScalingStrategy.DEFAULT;
    return this;
  }

  /**
   * Use PERCENTAGE strategy (Dynamic legacy).
   */
  percentage(): this {
    this.strategy = ScalingStrategy.PERCENTAGE;
    return this;
  }

  /**
   * Use FLUID strategy.
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
   */
  interpolated(): this {
    this.strategy = ScalingStrategy.INTERPOLATED;
    return this;
  }

  /**
   * Use DIAGONAL strategy.
   */
  diagonal(): this {
    this.strategy = ScalingStrategy.DIAGONAL;
    return this;
  }

  /**
   * Use PERIMETER strategy.
   */
  perimeter(): this {
    this.strategy = ScalingStrategy.PERIMETER;
    return this;
  }

  /**
   * Use FIT strategy (game letterbox).
   */
  fit(): this {
    this.strategy = ScalingStrategy.FIT;
    return this;
  }

  /**
   * Use FILL strategy (game cover).
   */
  fill(): this {
    this.strategy = ScalingStrategy.FILL;
    return this;
  }

  /**
   * Use AUTOSIZE strategy.
   */
  autosize(): this {
    this.strategy = ScalingStrategy.AUTOSIZE;
    return this;
  }

  /**
   * Use NONE strategy.
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
   * Set base orientation.
   */
  baseOrientation(orientation: BaseOrientation): this {
    this._baseOrientation = orientation;
    return this;
  }

  portraitLowest(): this {
    this._baseOrientation = 'portrait';
    this.screenType = 'lowest';
    return this;
  }

  portraitHighest(): this {
    this._baseOrientation = 'portrait';
    this.screenType = 'highest';
    return this;
  }

  landscapeLowest(): this {
    this._baseOrientation = 'landscape';
    this.screenType = 'lowest';
    return this;
  }

  landscapeHighest(): this {
    this._baseOrientation = 'landscape';
    this.screenType = 'highest';
    return this;
  }

  /**
   * Set sensitivity for perceptual models.
   */
  withSensitivity(sensitivity: number): this {
    this.perceptualParams.sensitivity = sensitivity;
    return this;
  }

  /**
   * Set transition point for BALANCED model.
   */
  withTransitionPoint(point: number): this {
    this.perceptualParams.transitionPoint = point;
    return this;
  }

  /**
   * Enable/disable aspect ratio adjustment.
   */
  withAspectRatio(enable: boolean): this {
    this.defaultParams.applyAspectRatio = enable;
    return this;
  }

  /**
   * Set aspect ratio sensitivity.
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
   * Calculate and return value (same as px()).
   */
  calculate(): number {
    return this.px();
  }

  /**
   * Calculate and return the value in pixels.
   */
  px(): number {
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
      this.constraints,
    );
    return result;
  }

  /**
   * Calculate and return as CSS pixel string.
   */
  toPx(): string {
    return `${this.px()}px`;
  }

  /**
   * Calculate and return as vw value (viewport width percentage).
   */
  vw(): number {
    return (this.baseValue / Calculator.BASE_WIDTH_DP) * 100;
  }

  /**
   * Calculate and return as CSS vw string.
   */
  toVw(): string {
    return `${this.vw()}vw`;
  }

  /**
   * Calculate and return as vh value (viewport height percentage).
   */
  vh(): number {
    return (this.baseValue / Calculator.BASE_WIDTH_DP) * 100;
  }

  /**
   * Calculate and return as CSS vh string.
   */
  toVh(): string {
    return `${this.vh()}vh`;
  }

  /**
   * Calculate and return as rem value.
   */
  rem(): number {
    return this.px() / 16;
  }

  /**
   * Calculate and return as CSS rem string.
   */
  toRem(): string {
    return `${this.rem()}rem`;
  }

  /**
   * Calculate and return as em value.
   */
  em(): number {
    return this.px() / 16;
  }

  /**
   * Calculate and return as CSS em string.
   */
  toEm(): string {
    return `${this.em()}em`;
  }

  // ============================================
  // HELPER METHODS
  // ============================================

  /**
   * Get current screen configuration.
   */
  private getCurrentConfig(): CalculationConfig {
    const viewport = globalViewportObserver.getDimensions();
    
    // Assume 96 DPI (standard web DPI)
    const densityDpi = 96;

    return {
      screenWidthDp: viewport.width,
      screenHeightDp: viewport.height,
      smallestScreenWidthDp: Math.min(viewport.width, viewport.height),
      densityDpi,
    };
  }
}

// ============================================
// CONVENIENCE FUNCTIONS
// ============================================

/**
 * Create a new builder with BALANCED strategy (recommended).
 */
export function balanced(value: number): WebDimensBuilder {
  return new WebDimensBuilder(value).balanced();
}

/**
 * Create a new builder with LOGARITHMIC strategy.
 */
export function logarithmic(value: number): WebDimensBuilder {
  return new WebDimensBuilder(value).logarithmic();
}

/**
 * Create a new builder with POWER strategy.
 */
export function power(value: number, exponent?: number): WebDimensBuilder {
  return new WebDimensBuilder(value).power(exponent);
}

/**
 * Create a new builder with DEFAULT strategy.
 */
export function defaultStrategy(value: number): WebDimensBuilder {
  return new WebDimensBuilder(value).default();
}

/**
 * Create a new builder with PERCENTAGE strategy.
 */
export function percentage(value: number): WebDimensBuilder {
  return new WebDimensBuilder(value).percentage();
}

/**
 * Create a new builder with FLUID strategy.
 */
export function fluidStrategy(
  value: number,
  minValue: number,
  maxValue: number,
): WebDimensBuilder {
  return new WebDimensBuilder(value).fluid(minValue, maxValue);
}

/**
 * Create a new builder with smart inference.
 */
export function smart(value: number, elementType: ElementType): WebDimensBuilder {
  return new WebDimensBuilder(value).forElement(elementType);
}

/**
 * Legacy compatibility: create builder with DEFAULT strategy.
 */
export function fixed(value: number): WebDimensBuilder {
  return new WebDimensBuilder(value).default();
}

/**
 * Legacy compatibility: create builder with PERCENTAGE strategy.
 */
export function dynamic(value: number): WebDimensBuilder {
  return new WebDimensBuilder(value).percentage();
}

// Aliases
export const fx = fixed;
export const dy = dynamic;
export const fl = fluidStrategy;

