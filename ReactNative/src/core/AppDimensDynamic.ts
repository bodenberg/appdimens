/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Dynamic Scaling
 *
 * Description:
 * Dynamic dimension scaling with proportional adjustment for aggressive scaling.
 * Ideal for layout containers and fluid elements.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {
  DimensionConfig,
  AppDimensBuilder,
  UiModeType,
  DeviceType,
  ScreenQualifier,
  ScreenQualifierEntry,
  UiModeQualifierEntry,
  BASE_WIDTH_DP,
} from '../types';
import {AppDimensAdjustmentFactors} from './AppDimensAdjustmentFactors';

/**
 * Dynamic dimension builder with proportional adjustment
 */
export class AppDimensDynamic implements AppDimensBuilder {
  private config: DimensionConfig;
  private baseValueCache = new Map<string, number>();
  private calculatedValueCache = new Map<string, number>();

  constructor(
    initialValue: number,
    ignoreMultiWindowAdjustment: boolean = false,
  ) {
    this.config = {
      initialValue,
      screenType: 'lowest',
      applyAspectRatioAdjustment: false, // Dynamic doesn't use aspect ratio
      ignoreMultiWindowAdjustment,
      customScreenQualifierMap: new Map(),
      customUiModeMap: new Map(),
      customIntersectionMap: new Map(),
    };
  }

  /**
   * Set custom value for screen qualifiers
   */
  screen(...args: any[]): this {
    if (args.length === 2) {
      const [type, customValue] = args;
      if (
        type === 'normal' ||
        type === 'car' ||
        type === 'tv' ||
        type === 'watch' ||
        type === 'desktop'
      ) {
        // UI Mode
        this.config.customUiModeMap.set(type, customValue);
      } else {
        // Device Type
        const screenQualifier: ScreenQualifierEntry = {
          type: 'smallWidth',
          value: 0,
          deviceType: type,
        };
        this.config.customScreenQualifierMap.set(screenQualifier, customValue);
      }
    } else if (args.length === 3) {
      const [deviceType, screenSize, customValue] = args;
      const screenQualifier: ScreenQualifierEntry = {
        type: 'smallWidth',
        value: screenSize,
        deviceType,
      };
      this.config.customScreenQualifierMap.set(screenQualifier, customValue);
    } else if (args.length === 4) {
      const [uiModeType, qualifierType, qualifierValue, customValue] = args;
      const screenQualifier: ScreenQualifierEntry = {
        type: qualifierType,
        value: qualifierValue,
        uiMode: uiModeType,
      };
      const intersectionEntry: UiModeQualifierEntry = {
        uiModeType,
        screenQualifierEntry: screenQualifier,
      };
      this.config.customIntersectionMap.set(intersectionEntry, customValue);
    }
    this.invalidateCache();
    return this;
  }

  /**
   * Set screen dimension type
   */
  type(type: 'lowest' | 'highest'): this {
    this.config.screenType = type;
    this.invalidateCache();
    return this;
  }

  /**
   * Set multi-window adjustment behavior
   */
  multiWindowAdjustment(ignore: boolean = true): this {
    this.config.ignoreMultiWindowAdjustment = ignore;
    this.invalidateCache();
    return this;
  }

  /**
   * Enable/disable aspect ratio adjustment (not used in dynamic)
   */
  aspectRatio(enable: boolean = true, sensitivity?: number): this {
    // Dynamic scaling doesn't use aspect ratio adjustment
    return this;
  }

  /**
   * Enable/disable cache
   */
  cache(enable: boolean = true): this {
    if (!enable) {
      this.invalidateCache();
    }
    return this;
  }

  /**
   * Calculate adjusted value in pixels
   */
  toPixels(): number {
    const adjustedValue = this.calculateAdjustedValue();
    return AppDimensAdjustmentFactors.pointsToPixels(adjustedValue);
  }

  /**
   * Calculate adjusted value in pixels (integer)
   */
  toPixelsInt(): number {
    return Math.round(this.toPixels());
  }

  /**
   * Calculate adjusted value in points
   */
  toPoints(): number {
    return this.calculateAdjustedValue();
  }

  /**
   * Calculate adjusted value in points (integer)
   */
  toPointsInt(): number {
    return Math.round(this.calculateAdjustedValue());
  }

  /**
   * Resolve final base value using customization logic
   */
  private resolveFinalBaseValue(): number {
    const screenDimensions =
      AppDimensAdjustmentFactors.getCurrentScreenDimensions();
    const deviceInfo = AppDimensAdjustmentFactors.getCurrentDeviceInfo();
    const {width, height} = screenDimensions;
    const currentScreenSize = Math.min(width, height);

    // Generate cache key
    const cacheKey = `${deviceInfo.type}_${deviceInfo.platform}_${width}_${height}_${this.config.initialValue}_${this.config.customIntersectionMap.size}_${this.config.customUiModeMap.size}_${this.config.customScreenQualifierMap.size}`;

    // Check cache first
    if (this.baseValueCache.has(cacheKey)) {
      return this.baseValueCache.get(cacheKey)!;
    }

    let valueToAdjust = this.config.initialValue;

    // Priority 1: Intersection (UiMode + ScreenQualifier)
    const sortedIntersectionQualifiers = Array.from(
      this.config.customIntersectionMap.keys(),
    ).sort(
      (a, b) => b.screenQualifierEntry.value - a.screenQualifierEntry.value,
    );

    for (const key of sortedIntersectionQualifiers) {
      if (
        key.uiModeType === this.getCurrentUiMode() &&
        AppDimensAdjustmentFactors.resolveIntersectionCondition(
          key.screenQualifierEntry,
          currentScreenSize,
          width,
          height,
        )
      ) {
        valueToAdjust =
          this.config.customIntersectionMap.get(key) ||
          this.config.initialValue;
        break;
      }
    }

    // Priority 2: UI Mode
    if (valueToAdjust === this.config.initialValue) {
      const uiModeValue = this.config.customUiModeMap.get(
        this.getCurrentUiMode(),
      );
      if (uiModeValue !== undefined) {
        valueToAdjust = uiModeValue;
      }
    }

    // Priority 3: Device Type / Screen Qualifier
    if (valueToAdjust === this.config.initialValue) {
      valueToAdjust = AppDimensAdjustmentFactors.resolveQualifierValue(
        this.config.customScreenQualifierMap,
        deviceInfo.type,
        currentScreenSize,
        this.config.initialValue,
      );
    }

    // Cache the result
    this.baseValueCache.set(cacheKey, valueToAdjust);

    return valueToAdjust;
  }

  /**
   * Calculate final adjusted value using dynamic scaling
   */
  private calculateAdjustedValue(): number {
    const screenDimensions =
      AppDimensAdjustmentFactors.getCurrentScreenDimensions();
    const {width, height} = screenDimensions;

    // Generate cache key for final calculation
    const cacheKey = `${this.config.screenType}_${this.config.ignoreMultiWindowAdjustment}_${width}_${height}_${this.config.initialValue}_${this.config.customIntersectionMap.size}_${this.config.customUiModeMap.size}_${this.config.customScreenQualifierMap.size}`;

    // Check cache first
    if (this.calculatedValueCache.has(cacheKey)) {
      return this.calculatedValueCache.get(cacheKey)!;
    }

    const valueToAdjust = this.resolveFinalBaseValue();

    const shouldIgnoreAdjustment =
      this.config.ignoreMultiWindowAdjustment &&
      AppDimensAdjustmentFactors.isMultiWindowMode();

    let finalValue: number;

    if (shouldIgnoreAdjustment) {
      // Return base value without dynamic scaling
      finalValue = valueToAdjust;
    } else {
      // Dynamic scaling: percentage of screen dimension
      const percentage = valueToAdjust / BASE_WIDTH_DP;

      // Screen dimension to use (LOWEST or HIGHEST)
      const dimensionToUse =
        this.config.screenType === 'highest'
          ? Math.max(width, height)
          : Math.min(width, height);

      // Final value is percentage applied to screen dimension
      finalValue = dimensionToUse * percentage;
    }

    // Cache the result
    this.calculatedValueCache.set(cacheKey, finalValue);

    return finalValue;
  }

  /**
   * Get current UI mode
   */
  private getCurrentUiMode(): UiModeType {
    // In React Native, we can't easily detect UI mode
    // This is a placeholder for future implementation
    return 'normal';
  }

  /**
   * Invalidate cache
   */
  private invalidateCache(): void {
    this.baseValueCache.clear();
    this.calculatedValueCache.clear();
  }
}
