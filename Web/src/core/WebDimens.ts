/**
 * Author & Developer: Jean Bodenberg
 * Based on: AppDimens Android Library
 * 
 * WebDimens - Main Class
 * Advanced responsive dimension system for web
 */

import type { WebDimensConfig } from '../types';
import { Fixed } from './Fixed';
import { Dynamic } from './Dynamic';
import { Fluid } from './Fluid';
import { globalCache } from '../cache/Cache';
import { globalViewportObserver } from '../observers/ViewportObserver';
import { globalMediaQueryObserver } from '../observers/MediaQueryObserver';
import { breakpointManager } from '../breakpoints/Breakpoints';
import { physicalUnits } from '../units/PhysicalUnits';
import { 
  BASE_WIDTH_DP, 
  BASE_HEIGHT_DP, 
  REFERENCE_AR, 
  DEFAULT_SENSITIVITY_K, 
  BASE_INCREMENT 
} from '../constants';

/**
 * AppDimens Main Class
 * Entry point for the dimension system
 * (Renamed from WebDimens for consistency with other platforms)
 */
export class AppDimens {
  private config: Required<WebDimensConfig>;
  private cssVarsEnabled: boolean = false;
  private cssVarsUpdateInterval: number | null = null;

  constructor(config: WebDimensConfig = {}) {
    this.config = {
      baseWidth: config.baseWidth ?? BASE_WIDTH_DP,
      baseHeight: config.baseHeight ?? BASE_HEIGHT_DP,
      referenceAR: config.referenceAR ?? REFERENCE_AR,
      defaultSensitivity: config.defaultSensitivity ?? DEFAULT_SENSITIVITY_K,
      baseIncrement: config.baseIncrement ?? BASE_INCREMENT,
      breakpoints: config.breakpoints ?? {},
      enableCache: config.enableCache ?? true,
      debug: config.debug ?? false,
      autoUpdateCSSVars: config.autoUpdateCSSVars ?? false
    };

    // Set custom breakpoints if provided
    if (Object.keys(this.config.breakpoints).length > 0) {
      breakpointManager.setBreakpoints(this.config.breakpoints);
    }

    // Enable auto CSS variables update
    if (this.config.autoUpdateCSSVars) {
      this.enableCSSVars();
    }
  }

  /**
   * Create Fixed dimension
   */
  fixed(value: number): Fixed {
    return new Fixed(value);
  }

  /**
   * Create Fixed dimension (shorthand)
   */
  fx(value: number): Fixed {
    return this.fixed(value);
  }

  /**
   * Create Dynamic dimension
   */
  dynamic(value: number): Dynamic {
    return new Dynamic(value);
  }

  /**
   * Create Dynamic dimension (shorthand)
   */
  dy(value: number): Dynamic {
    return this.dynamic(value);
  }

  /**
   * Create Fluid dimension
   */
  fluid(minValue: number, maxValue: number, minBp?: string, maxBp?: string): Fluid {
    return new Fluid(minValue, maxValue, minBp, maxBp);
  }

  /**
   * Create Fluid dimension (shorthand)
   */
  fl(minValue: number, maxValue: number, minBp?: string, maxBp?: string): Fluid {
    return this.fluid(minValue, maxValue, minBp, maxBp);
  }

  /**
   * Calculate percentage of viewport
   */
  percent(percentage: number, type: 'width' | 'height' = 'width'): number {
    const viewport = globalViewportObserver.getDimensions();
    const dimension = type === 'width' ? viewport.width : viewport.height;
    return dimension * (percentage / 100);
  }

  /**
   * Physical units
   */
  get physical() {
    return physicalUnits;
  }

  /**
   * Breakpoints
   */
  get breakpoints() {
    return breakpointManager;
  }

  /**
   * Cache
   */
  get cache() {
    return globalCache;
  }

  /**
   * Viewport observer
   */
  get viewport() {
    return globalViewportObserver;
  }

  /**
   * Media query observer
   */
  get mediaQuery() {
    return globalMediaQueryObserver;
  }

  /**
   * Get current viewport dimensions
   */
  getDimensions() {
    return globalViewportObserver.getDimensions();
  }

  /**
   * Check media query
   */
  matchesMedia(query: string): boolean {
    return globalMediaQueryObserver.matches(query);
  }

  /**
   * Get media features
   */
  getMediaFeatures() {
    return globalMediaQueryObserver.getMediaFeatures();
  }

  /**
   * Enable CSS Variables
   * Auto-updates custom properties on resize
   */
  enableCSSVars(): void {
    if (typeof window === 'undefined' || this.cssVarsEnabled) return;

    this.cssVarsEnabled = true;
    this.updateCSSVars();

    // Subscribe to viewport changes
    globalViewportObserver.subscribe(() => {
      this.updateCSSVars();
    });
  }

  /**
   * Update CSS variables
   */
  private updateCSSVars(): void {
    if (typeof window === 'undefined' || !document.documentElement) return;

    const root = document.documentElement.style;
    const viewport = globalViewportObserver.getDimensions();

    // Viewport dimensions
    root.setProperty('--wd-vw', `${viewport.width}px`);
    root.setProperty('--wd-vh', `${viewport.height}px`);
    root.setProperty('--wd-vmin', `${Math.min(viewport.width, viewport.height)}px`);
    root.setProperty('--wd-vmax', `${Math.max(viewport.width, viewport.height)}px`);
    root.setProperty('--wd-aspect-ratio', `${viewport.aspectRatio}`);
    root.setProperty('--wd-orientation', viewport.orientation);
    root.setProperty('--wd-dpr', `${viewport.dpr}`);

    // Breakpoint
    root.setProperty('--wd-breakpoint', breakpointManager.current);

    // Common fixed values
    [8, 12, 16, 20, 24, 32, 48, 64].forEach(size => {
      const value = this.fixed(size).px();
      root.setProperty(`--wd-fx-${size}`, `${value}px`);
    });

    // Common dynamic values
    [100, 200, 300, 400, 500].forEach(size => {
      const value = this.dynamic(size).px();
      root.setProperty(`--wd-dy-${size}`, `${value}px`);
    });
  }

  /**
   * Disable CSS Variables
   */
  disableCSSVars(): void {
    this.cssVarsEnabled = false;
    
    if (this.cssVarsUpdateInterval !== null) {
      clearInterval(this.cssVarsUpdateInterval);
      this.cssVarsUpdateInterval = null;
    }
  }

  /**
   * Generate spacing scale
   * Useful for Tailwind or design systems
   */
  generateSpacingScale(base: number = 4): Record<string, string> {
    const scale: Record<string, string> = {};
    const steps = [0, 1, 2, 3, 4, 5, 6, 8, 10, 12, 16, 20, 24, 32, 40, 48, 56, 64];

    steps.forEach(step => {
      const value = step * base;
      scale[step] = this.fixed(value).toPx();
    });

    return scale;
  }

  /**
   * Generate font scale
   */
  generateFontScale(): Record<string, string> {
    const sizes = {
      xs: 12,
      sm: 14,
      base: 16,
      lg: 18,
      xl: 20,
      '2xl': 24,
      '3xl': 30,
      '4xl': 36,
      '5xl': 48,
      '6xl': 60,
      '7xl': 72,
      '8xl': 96,
      '9xl': 128
    };

    const scale: Record<string, string> = {};
    Object.entries(sizes).forEach(([key, value]) => {
      scale[key] = this.fixed(value).toPx();
    });

    return scale;
  }

  /**
   * Safe area insets (for mobile notches, etc)
   */
  safeArea(side: 'top' | 'right' | 'bottom' | 'left', fallback: number = 16): string {
    return `max(${this.fixed(fallback).toPx()}, env(safe-area-inset-${side}))`;
  }

  /**
   * Get configuration
   */
  getConfig(): Required<WebDimensConfig> {
    return { ...this.config };
  }

  /**
   * Update configuration
   */
  updateConfig(config: Partial<WebDimensConfig>): void {
    this.config = {
      ...this.config,
      ...config
    };

    if (config.breakpoints) {
      breakpointManager.setBreakpoints(config.breakpoints);
    }
  }

  /**
   * Clear all caches
   */
  clearCache(): void {
    globalCache.clearAll();
  }

  /**
   * Get cache statistics
   */
  getCacheStats() {
    return globalCache.getStats();
  }

  /**
   * Cleanup
   */
  destroy(): void {
    this.disableCSSVars();
    globalCache.destroy();
    globalViewportObserver.destroy();
    globalMediaQueryObserver.destroy();
  }
}

/**
 * Create default instance
 */
export const webdimens = new AppDimens();

// Backward compatibility alias
export { AppDimens as WebDimens };

// Convenience exports
export const fixed = (value: number) => webdimens.fixed(value);
export const fx = (value: number) => webdimens.fx(value);
export const dynamic = (value: number) => webdimens.dynamic(value);
export const dy = (value: number) => webdimens.dy(value);
export const fluid = (min: number, max: number, minBp?: string, maxBp?: string) => 
  webdimens.fluid(min, max, minBp, maxBp);
export const fl = (min: number, max: number, minBp?: string, maxBp?: string) => 
  webdimens.fl(min, max, minBp, maxBp);

