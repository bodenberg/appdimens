/**
 * Author & Developer: Jean Bodenberg
 * 
 * Fluid Scaling Model
 * CSS clamp() based fluid typography and spacing
 * Smoothly scales between min and max values
 */

import { breakpointManager } from '../breakpoints/Breakpoints';
import type { BaseOrientation } from '../types/BaseOrientation';

/**
 * Fluid Dimension Builder
 * Uses CSS clamp() for smooth responsive scaling
 */
export class Fluid {
  private minValue: number;
  private maxValue: number;
  private minBreakpoint: string;
  private maxBreakpoint: string;
  private _baseOrientation: BaseOrientation = 'auto';

  constructor(
    minValue: number,
    maxValue: number,
    minBreakpoint: string = 'xs',
    maxBreakpoint: string = '4xl'
  ) {
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.minBreakpoint = minBreakpoint;
    this.maxBreakpoint = maxBreakpoint;
  }

  /**
   * Set breakpoint range
   */
  between(minBreakpoint: string, maxBreakpoint: string): this {
    this.minBreakpoint = minBreakpoint;
    this.maxBreakpoint = maxBreakpoint;
    return this;
  }

  /**
   * Set base orientation for design
   */
  baseOrientation(orientation: BaseOrientation): this {
    this._baseOrientation = orientation;
    return this;
  }

  /**
   * Shorthand for portrait design
   */
  portrait(): this {
    this._baseOrientation = 'portrait';
    return this;
  }

  /**
   * Shorthand for landscape design
   */
  landscape(): this {
    this._baseOrientation = 'landscape';
    return this;
  }

  /**
   * Calculate fluid value using clamp()
   */
  calculate(): string {
    return breakpointManager.fluid(
      this.minValue,
      this.maxValue,
      this.minBreakpoint,
      this.maxBreakpoint
    );
  }

  /**
   * Build as CSS clamp() string
   */
  toString(): string {
    return this.calculate();
  }

  /**
   * Build as px clamp
   */
  toPx(): string {
    return this.calculate();
  }

  /**
   * Build as rem clamp
   */
  toRem(): string {
    const minRem = this.minValue / 16;
    const maxRem = this.maxValue / 16;
    
    const minVw = breakpointManager.getValue(this.minBreakpoint);
    const maxVw = breakpointManager.getValue(this.maxBreakpoint);
    
    const slope = (maxRem - minRem) / (maxVw - minVw);
    const yAxisIntersection = -minVw * slope + minRem;
    
    return `clamp(${minRem}rem, ${yAxisIntersection.toFixed(4)}rem + ${(slope * 100).toFixed(4)}vw, ${maxRem}rem)`;
  }

  /**
   * Build as em clamp
   */
  toEm(): string {
    // Same as rem for base calculation
    return this.toRem().replace(/rem/g, 'em');
  }

  /**
   * Get min value
   */
  getMin(): number {
    return this.minValue;
  }

  /**
   * Get max value
   */
  getMax(): number {
    return this.maxValue;
  }

  /**
   * Get preferred value (middle of range)
   */
  getPreferred(): number {
    return (this.minValue + this.maxValue) / 2;
  }

  /**
   * Linear interpolation between min and max
   */
  lerp(t: number): number {
    return this.minValue + (this.maxValue - this.minValue) * t;
  }
}

