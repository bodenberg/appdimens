/**
 * Author & Developer: Jean Bodenberg
 * Based on: AppDimens Android - AppDimensDynamic
 * 
 * Dynamic Scaling Model
 * Proportional scaling (percentage-based)
 * Ideal for: containers, grids, fluid layouts
 */

import { ScreenType } from '../types';
import type { DeviceMode, SizeQualifier } from '../types';
import type { BaseOrientation } from '../types/BaseOrientation';
import { BASE_WIDTH_DP } from '../constants';
import { globalCache } from '../cache/Cache';
import { globalViewportObserver } from '../observers/ViewportObserver';
import { resolveScreenType } from '../utils/orientationResolver';

interface QualifierEntry {
  qualifier: SizeQualifier;
  value: number;
  customValue: number;
}

interface DeviceModeEntry {
  mode: DeviceMode;
  customValue: number;
}

interface IntersectionEntry {
  mode: DeviceMode;
  qualifier: SizeQualifier;
  value: number;
  customValue: number;
}

/**
 * Dynamic Dimension Builder
 * Proportional scaling for layouts
 */
export class Dynamic {
  private baseValue: number;
  private screenType: ScreenType = ScreenType.LOWEST;
  private baseOrientation: BaseOrientation = 'auto';
  private ignoreMultiView: boolean = true;
  private enableCache: boolean = true;

  // Custom values (Priority system)
  private qualifierEntries: QualifierEntry[] = [];
  private deviceModeEntries: DeviceModeEntry[] = [];
  private intersectionEntries: IntersectionEntry[] = [];

  constructor(value: number) {
    this.baseValue = value;
  }

  /**
   * Set screen type (lowest/highest/width/height)
   * Primary method - matches Android, iOS, Flutter API
   */
  screen(type: ScreenType): this;
  
  /**
   * Set custom value for device mode or qualifier
   */
  screen(mode: DeviceMode, value: number): this;
  screen(qualifier: SizeQualifier, value: number, customValue: number): this;
  screen(
    mode: DeviceMode,
    qualifier: SizeQualifier,
    value: number,
    customValue: number
  ): this;
  
  screen(...args: any[]): this {
    // Check if it's screen(type: ScreenType)
    if (args.length === 1 && typeof args[0] === 'string' && 
        ['lowest', 'highest', 'width', 'height'].includes(args[0])) {
      this.screenType = args[0] as ScreenType;
      return this;
    }
    
    if (args.length === 2) {
      // Device mode only
      this.deviceModeEntries.push({
        mode: args[0] as DeviceMode,
        customValue: args[1]
      });
    } else if (args.length === 3) {
      // Size qualifier
      this.qualifierEntries.push({
        qualifier: args[0] as SizeQualifier,
        value: args[1],
        customValue: args[2]
      });
    } else if (args.length === 4) {
      // Intersection (Priority 1)
      this.intersectionEntries.push({
        mode: args[0] as DeviceMode,
        qualifier: args[1] as SizeQualifier,
        value: args[2],
        customValue: args[3]
      });
    }
    return this;
  }
  
  /**
   * Alias for screen() - kept for backward compatibility
   * @deprecated Use screen() instead for consistency with other platforms
   */
  type(type: ScreenType): this {
    return this.screen(type);
  }

  baseOrientation(orientation: BaseOrientation): this {
    this.baseOrientation = orientation;
    return this;
  }

  portraitLowest(): this {
    this.baseOrientation = 'portrait';
    this.screenType = ScreenType.LOWEST;
    return this;
  }

  portraitHighest(): this {
    this.baseOrientation = 'portrait';
    this.screenType = ScreenType.HIGHEST;
    return this;
  }

  landscapeLowest(): this {
    this.baseOrientation = 'landscape';
    this.screenType = ScreenType.LOWEST;
    return this;
  }

  landscapeHighest(): this {
    this.baseOrientation = 'landscape';
    this.screenType = ScreenType.HIGHEST;
    return this;
  }

  /**
   * Enable/disable multi-view adjustment
   */
  multiView(ignore: boolean = true): this {
    this.ignoreMultiView = ignore;
    return this;
  }

  /**
   * Enable/disable cache
   */
  cache(enable: boolean = true): this {
    this.enableCache = enable;
    return this;
  }

  /**
   * Resolve base value with custom qualifiers
   */
  private resolveBaseValue(): number {
    const viewport = globalViewportObserver.getDimensions();
    
    // Priority 1: Intersection
    const intersection = this.intersectionEntries
      .sort((a, b) => b.value - a.value)
      .find(entry => this.matchesQualifier(entry.qualifier, entry.value, viewport));
    
    if (intersection) {
      return intersection.customValue;
    }

    // Priority 2: Device mode
    const deviceMode = this.detectDeviceMode(viewport);
    const modeEntry = this.deviceModeEntries.find(e => e.mode === deviceMode);
    
    if (modeEntry) {
      return modeEntry.customValue;
    }

    // Priority 3: Size qualifier
    const qualifier = this.qualifierEntries
      .sort((a, b) => b.value - a.value)
      .find(entry => this.matchesQualifier(entry.qualifier, entry.value, viewport));
    
    if (qualifier) {
      return qualifier.customValue;
    }

    return this.baseValue;
  }

  /**
   * Check if qualifier matches
   */
  private matchesQualifier(
    qualifier: SizeQualifier,
    value: number,
    viewport: ReturnType<typeof globalViewportObserver.getDimensions>
  ): boolean {
    switch (qualifier) {
      case 'min-width':
        return viewport.width >= value;
      case 'max-width':
        return viewport.width <= value;
      case 'min-height':
        return viewport.height >= value;
      case 'max-height':
        return viewport.height <= value;
      default:
        return false;
    }
  }

  /**
   * Detect device mode
   */
  private detectDeviceMode(viewport: ReturnType<typeof globalViewportObserver.getDimensions>): DeviceMode {
    const smallest = Math.min(viewport.width, viewport.height);
    
    if (smallest < 640) return 'mobile' as DeviceMode;
    if (smallest < 1024) return 'tablet' as DeviceMode;
    if (smallest < 1920) return 'desktop' as DeviceMode;
    return 'tv' as DeviceMode;
  }

  /**
   * Get dimension based on screen type
   */
  private getDimensionByType(viewport: ReturnType<typeof globalViewportObserver.getDimensions>): number {
    // Resolve effective screen type based on base orientation
    const effectiveScreenType = resolveScreenType(
      this.screenType,
      this.baseOrientation,
      {width: viewport.width, height: viewport.height}
    );
    
    switch (effectiveScreenType) {
      case 'highest':
        return Math.max(viewport.width, viewport.height);
      case 'width':
        return viewport.width;
      case 'height':
        return viewport.height;
      case 'lowest':
      default:
        return Math.min(viewport.width, viewport.height);
    }
  }

  /**
   * Calculate final value
   */
  calculate(): number {
    const cacheKey = `dynamic_${this.baseValue}_${this.screenType}`;
    
    if (this.enableCache) {
      return globalCache.remember(
        cacheKey,
        [this.baseValue, this.screenType],
        () => this.performCalculation()
      );
    }

    return this.performCalculation();
  }

  /**
   * Perform the actual calculation
   */
  private performCalculation(): number {
    const baseValue = this.resolveBaseValue();
    const viewport = globalViewportObserver.getDimensions();

    // Calculate percentage
    const percentage = baseValue / BASE_WIDTH_DP;

    // Get dimension to use
    const dimension = this.getDimensionByType(viewport);

    // Return proportional value
    return dimension * percentage;
  }

  /**
   * Calculate as percentage
   */
  percentage(): number {
    return (this.baseValue / BASE_WIDTH_DP) * 100;
  }

  /**
   * Build as pixel value
   */
  px(): number {
    return this.calculate();
  }

  /**
   * Build as CSS string with px unit
   */
  toPx(): string {
    return `${this.px()}px`;
  }

  /**
   * Build as vw value
   */
  vw(): number {
    return this.percentage();
  }

  /**
   * Build as CSS string with vw unit
   */
  toVw(): string {
    return `${this.vw()}vw`;
  }

  /**
   * Build as vh value
   */
  vh(): number {
    return this.percentage();
  }

  /**
   * Build as CSS string with vh unit
   */
  toVh(): string {
    return `${this.vh()}vh`;
  }

  /**
   * Build as rem value
   */
  rem(): number {
    return this.px() / 16;
  }

  /**
   * Build as CSS string with rem unit
   */
  toRem(): string {
    return `${this.rem()}rem`;
  }
}

