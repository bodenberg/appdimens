/**
 * Author & Developer: Jean Bodenberg
 * Based on: AppDimens Android - AppDimensFixed
 * 
 * Fixed Scaling Model
 * Logarithmic adjustment for refined scaling
 * Ideal for: buttons, paddings, margins, icons, fonts
 */

import type { ScreenType, DeviceMode, SizeQualifier, ScreenAdjustmentFactors } from '../types';
import { 
  BASE_DP_FACTOR, 
  BASE_WIDTH_DP, 
  INCREMENT_DP_STEP, 
  REFERENCE_AR, 
  DEFAULT_SENSITIVITY_K, 
  BASE_INCREMENT 
} from '../constants';
import { globalCache } from '../cache/Cache';
import { globalViewportObserver } from '../observers/ViewportObserver';

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
 * Fixed Dimension Builder
 * Logarithmic scaling for UI elements
 */
export class Fixed {
  private baseValue: number;
  private screenType: ScreenType = 'lowest' as ScreenType;
  private applyAspectRatio: boolean = true;
  private customSensitivity: number | null = null;
  private _ignoreMultiView: boolean = true;
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
   */
  type(type: ScreenType): this {
    this.screenType = type;
    return this;
  }

  /**
   * Enable/disable aspect ratio adjustment
   */
  aspectRatio(enable: boolean = true, sensitivity?: number): this {
    this.applyAspectRatio = enable;
    if (sensitivity !== undefined) {
      this.customSensitivity = sensitivity;
    }
    return this;
  }

  /**
   * Set custom value for device mode (Priority 2)
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
   * Enable/disable multi-view adjustment
   */
  multiView(ignore: boolean = true): this {
    this._ignoreMultiView = ignore;
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
   * Calculate adjustment factors
   */
  private calculateAdjustmentFactors(): ScreenAdjustmentFactors {
    const viewport = globalViewportObserver.getDimensions();
    const smallestDimension = Math.min(viewport.width, viewport.height);
    const largestDimension = Math.max(viewport.width, viewport.height);

    // Base adjustment factor (LOWEST)
    const differenceLowest = smallestDimension - BASE_WIDTH_DP;
    const adjustmentFactorLowest = differenceLowest / INCREMENT_DP_STEP;

    // Base adjustment factor (HIGHEST)
    const differenceHighest = largestDimension - BASE_WIDTH_DP;
    const adjustmentFactorHighest = differenceHighest / INCREMENT_DP_STEP;

    // Without AR
    const withoutArFactor = BASE_DP_FACTOR + adjustmentFactorLowest * BASE_INCREMENT;

    // With AR
    const ar = viewport.aspectRatio;
    const sensitivity = this.customSensitivity ?? DEFAULT_SENSITIVITY_K;
    const continuousAdjustment = sensitivity * Math.log(ar / REFERENCE_AR);
    const finalIncrementValue = BASE_INCREMENT + continuousAdjustment;

    const withArFactorLowest = BASE_DP_FACTOR + adjustmentFactorLowest * finalIncrementValue;
    const withArFactorHighest = BASE_DP_FACTOR + adjustmentFactorHighest * finalIncrementValue;

    return {
      withArFactorLowest,
      withArFactorHighest,
      withoutArFactor,
      adjustmentFactorLowest,
      adjustmentFactorHighest
    };
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
  private _getDimensionByType(viewport: ReturnType<typeof globalViewportObserver.getDimensions>): number {
    switch (this.screenType) {
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
    const cacheKey = `fixed_${this.baseValue}_${this.screenType}_${this.applyAspectRatio}`;
    
    if (this.enableCache) {
      return globalCache.remember(
        cacheKey,
        [this.baseValue, this.screenType, this.applyAspectRatio, this.customSensitivity],
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
    const factors = this.calculateAdjustmentFactors();

    let factor: number;

    if (this.applyAspectRatio) {
      factor = this.screenType === 'highest' 
        ? factors.withArFactorHighest 
        : factors.withArFactorLowest;
    } else {
      factor = factors.withoutArFactor;
    }

    return baseValue * factor;
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
   * Build as rem value
   */
  rem(): number {
    return this.px() / 16; // Assuming 16px base
  }

  /**
   * Build as CSS string with rem unit
   */
  toRem(): string {
    return `${this.rem()}rem`;
  }

  /**
   * Build as em value
   */
  em(): number {
    return this.rem(); // Same as rem for base calculation
  }

  /**
   * Build as CSS string with em unit
   */
  toEm(): string {
    return `${this.em()}em`;
  }
}

