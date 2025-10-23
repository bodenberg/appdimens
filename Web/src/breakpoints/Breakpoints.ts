/**
 * Author & Developer: Jean Bodenberg
 * 
 * Breakpoint System
 * Intelligent breakpoint management with fluid scaling
 */

import type { Breakpoints as BreakpointsConfig } from '../types';
import { DEFAULT_BREAKPOINTS } from '../constants';
import { globalViewportObserver } from '../observers/ViewportObserver';

/**
 * Breakpoint Manager
 * Manages responsive breakpoints and fluid scaling
 */
export class BreakpointManager {
  private breakpoints: BreakpointsConfig;
  private currentBreakpoint: string = 'xs';

  constructor(breakpoints: BreakpointsConfig = DEFAULT_BREAKPOINTS) {
    this.breakpoints = breakpoints;
    
    if (typeof window !== 'undefined') {
      this.updateCurrentBreakpoint();
      
      // Subscribe to viewport changes
      globalViewportObserver.subscribe(() => {
        this.updateCurrentBreakpoint();
      });
    }
  }

  /**
   * Update current breakpoint
   */
  private updateCurrentBreakpoint(): void {
    const width = window.innerWidth;
    const entries = Object.entries(this.breakpoints);
    
    // Find the largest breakpoint that the current width exceeds
    for (let i = entries.length - 1; i >= 0; i--) {
      if (width >= entries[i][1]) {
        this.currentBreakpoint = entries[i][0];
        return;
      }
    }
    
    this.currentBreakpoint = entries[0][0]; // Default to smallest
  }

  /**
   * Get current breakpoint
   */
  get current(): string {
    return this.currentBreakpoint;
  }

  /**
   * Check if current viewport matches a breakpoint
   */
  is(breakpoint: string): boolean {
    return this.currentBreakpoint === breakpoint;
  }

  /**
   * Check if current viewport is above a breakpoint
   */
  above(breakpoint: string): boolean {
    const currentValue = this.breakpoints[this.currentBreakpoint];
    const targetValue = this.breakpoints[breakpoint];
    return currentValue > targetValue;
  }

  /**
   * Check if current viewport is below a breakpoint
   */
  below(breakpoint: string): boolean {
    const currentValue = this.breakpoints[this.currentBreakpoint];
    const targetValue = this.breakpoints[breakpoint];
    return currentValue < targetValue;
  }

  /**
   * Check if current viewport is between two breakpoints
   */
  between(min: string, max: string): boolean {
    const currentValue = this.breakpoints[this.currentBreakpoint];
    const minValue = this.breakpoints[min];
    const maxValue = this.breakpoints[max];
    return currentValue >= minValue && currentValue <= maxValue;
  }

  /**
   * Get breakpoint value
   */
  getValue(breakpoint: string): number {
    return this.breakpoints[breakpoint] || 0;
  }

  /**
   * Generate CSS media query
   */
  mediaQuery(breakpoint: string, type: 'min' | 'max' = 'min'): string {
    const value = this.getValue(breakpoint);
    return `(${type}-width: ${value}px)`;
  }

  /**
   * Generate fluid value between two breakpoints
   * Uses CSS clamp() for smooth scaling
   */
  fluid(
    minValue: number,
    maxValue: number,
    minBreakpoint: string = 'xs',
    maxBreakpoint: string = '4xl'
  ): string {
    const minVw = this.getValue(minBreakpoint);
    const maxVw = this.getValue(maxBreakpoint);

    // Calculate slope and y-intercept for fluid formula
    const slope = (maxValue - minValue) / (maxVw - minVw);
    const yAxisIntersection = -minVw * slope + minValue;

    // Return clamp formula: clamp(min, preferred, max)
    return `clamp(${minValue}px, ${yAxisIntersection.toFixed(4)}px + ${(slope * 100).toFixed(4)}vw, ${maxValue}px)`;
  }

  /**
   * Get all breakpoints
   */
  getAll(): BreakpointsConfig {
    return { ...this.breakpoints };
  }

  /**
   * Set custom breakpoints
   */
  setBreakpoints(breakpoints: BreakpointsConfig): void {
    this.breakpoints = breakpoints;
    this.updateCurrentBreakpoint();
  }

  /**
   * Add a breakpoint
   */
  addBreakpoint(name: string, value: number): void {
    this.breakpoints[name] = value;
    this.updateCurrentBreakpoint();
  }

  /**
   * Remove a breakpoint
   */
  removeBreakpoint(name: string): void {
    delete this.breakpoints[name];
    this.updateCurrentBreakpoint();
  }
}

/**
 * Global breakpoint manager instance
 */
export const breakpointManager = new BreakpointManager();

