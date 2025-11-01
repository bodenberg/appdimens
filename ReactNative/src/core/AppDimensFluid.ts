/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens React Native - Fluid Scaling Model
 *
 * Description:
 * Implements fluid (clamp-like) scaling that smoothly interpolates
 * between minimum and maximum values based on screen width breakpoints.
 * Ideal for typography, spacing, and other elements that need smooth
 * transitions across different screen sizes.
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

import {Dimensions} from 'react-native';
import {DeviceType, ScreenQualifier} from '../types';

/**
 * Fluid scaling configuration
 */
export interface FluidConfig {
  minValue: number;
  maxValue: number;
  minWidth: number;
  maxWidth: number;
}

/**
 * AppDimensFluid - Fluid (Clamp-like) Scaling Model
 *
 * Provides smooth interpolation between minimum and maximum values
 * based on screen width. Similar to CSS clamp() but for React Native.
 *
 * Philosophy: Smooth transitions with bounded growth
 * Ideal for: Typography, fluid spacing, responsive sizes with limits
 *
 * @example
 * ```typescript
 * // Basic usage
 * const fluid = new AppDimensFluid(16, 24);
 * const fontSize = fluid.calculate(); // 16-24 based on screen width
 *
 * // Custom breakpoints
 * const fluid = new AppDimensFluid(12, 32, 320, 768);
 *
 * // With device type qualifiers
 * const fluid = new AppDimensFluid(16, 24)
 *   .device(DeviceType.Tablet, 20, 32)
 *   .calculate();
 * ```
 */
export class AppDimensFluid {
  private minValue: number;
  private maxValue: number;
  private minWidth: number;
  private maxWidth: number;
  private deviceQualifiers: Map<DeviceType, FluidConfig> = new Map();
  private screenQualifiers: Map<number, FluidConfig> = new Map();

  /**
   * Creates a new Fluid dimension builder
   *
   * @param minValue - Minimum value (at minWidth or below)
   * @param maxValue - Maximum value (at maxWidth or above)
   * @param minWidth - Minimum screen width breakpoint (default: 320)
   * @param maxWidth - Maximum screen width breakpoint (default: 768)
   */
  constructor(
    minValue: number,
    maxValue: number,
    minWidth: number = 320,
    maxWidth: number = 768,
  ) {
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.minWidth = minWidth;
    this.maxWidth = maxWidth;
  }

  /**
   * Set fluid values for specific device type
   *
   * @param type - Device type
   * @param minValue - Minimum value for this device
   * @param maxValue - Maximum value for this device
   * @param minWidth - Optional custom min width for this device
   * @param maxWidth - Optional custom max width for this device
   * @returns This instance for chaining
   *
   * @example
   * ```typescript
   * const fontSize = new AppDimensFluid(14, 18)
   *   .device(DeviceType.Tablet, 18, 24)
   *   .device(DeviceType.TV, 24, 32)
   *   .calculate();
   * ```
   */
  device(
    type: DeviceType,
    minValue: number,
    maxValue: number,
    minWidth?: number,
    maxWidth?: number,
  ): this {
    this.deviceQualifiers.set(type, {
      minValue,
      maxValue,
      minWidth: minWidth ?? this.minWidth,
      maxWidth: maxWidth ?? this.maxWidth,
    });
    return this;
  }

  /**
   * Set fluid values for specific screen width qualifier
   *
   * @param qualifier - Screen width qualifier in dp (e.g., 600 for SW600)
   * @param minValue - Minimum value for this qualifier
   * @param maxValue - Maximum value for this qualifier
   * @param minWidth - Optional custom min width
   * @param maxWidth - Optional custom max width
   * @returns This instance for chaining
   *
   * @example
   * ```typescript
   * const spacing = new AppDimensFluid(8, 16)
   *   .screen(600, 12, 20)
   *   .screen(840, 16, 24)
   *   .calculate();
   * ```
   */
  screen(
    qualifier: number,
    minValue: number,
    maxValue: number,
    minWidth?: number,
    maxWidth?: number,
  ): this {
    this.screenQualifiers.set(qualifier, {
      minValue,
      maxValue,
      minWidth: minWidth ?? this.minWidth,
      maxWidth: maxWidth ?? this.maxWidth,
    });
    return this;
  }

  /**
   * Calculate the fluid value based on current screen width
   *
   * @param screenWidth - Optional screen width (uses device width if not provided)
   * @param deviceType - Optional device type for qualifier resolution
   * @returns Interpolated value between min and max
   */
  calculate(screenWidth?: number, deviceType?: DeviceType): number {
    const width = screenWidth ?? Dimensions.get('window').width;

    // Resolve which config to use based on qualifiers
    const config = this.resolveConfig(width, deviceType);

    // Perform fluid interpolation
    return this.interpolate(width, config);
  }

  /**
   * Get the minimum value
   */
  getMin(): number {
    return this.minValue;
  }

  /**
   * Get the maximum value
   */
  getMax(): number {
    return this.maxValue;
  }

  /**
   * Get the preferred (middle) value
   */
  getPreferred(): number {
    return (this.minValue + this.maxValue) / 2;
  }

  /**
   * Linear interpolation at a specific progress point
   *
   * @param t - Progress value between 0 and 1
   * @returns Interpolated value
   */
  lerp(t: number): number {
    const clampedT = Math.max(0, Math.min(1, t));
    return this.minValue + (this.maxValue - this.minValue) * clampedT;
  }

  /**
   * Resolve which configuration to use based on qualifiers
   * Priority: Device Type > Screen Qualifier > Default
   */
  private resolveConfig(width: number, deviceType?: DeviceType): FluidConfig {
    // Priority 1: Device Type
    if (deviceType && this.deviceQualifiers.has(deviceType)) {
      return this.deviceQualifiers.get(deviceType)!;
    }

    // Priority 2: Screen Qualifier (find largest matching)
    let matchedConfig: FluidConfig | null = null;
    let largestQualifier = 0;

    for (const [qualifier, config] of this.screenQualifiers.entries()) {
      if (width >= qualifier && qualifier > largestQualifier) {
        matchedConfig = config;
        largestQualifier = qualifier;
      }
    }

    if (matchedConfig) {
      return matchedConfig;
    }

    // Priority 3: Default
    return {
      minValue: this.minValue,
      maxValue: this.maxValue,
      minWidth: this.minWidth,
      maxWidth: this.maxWidth,
    };
  }

  /**
   * Perform fluid interpolation (clamp-like behavior)
   */
  private interpolate(width: number, config: FluidConfig): number {
    // Below minimum width: return min value
    if (width <= config.minWidth) {
      return config.minValue;
    }

    // Above maximum width: return max value
    if (width >= config.maxWidth) {
      return config.maxValue;
    }

    // Between min and max: linear interpolation
    const progress =
      (width - config.minWidth) / (config.maxWidth - config.minWidth);

    return config.minValue + (config.maxValue - config.minValue) * progress;
  }
}

/**
 * Create a fluid dimension (shorthand function)
 *
 * @param minValue - Minimum value
 * @param maxValue - Maximum value
 * @param minWidth - Minimum screen width (default: 320)
 * @param maxWidth - Maximum screen width (default: 768)
 * @returns New AppDimensFluid instance
 *
 * @example
 * ```typescript
 * const fontSize = fluid(16, 24).calculate();
 * ```
 */
export function fluid(
  minValue: number,
  maxValue: number,
  minWidth?: number,
  maxWidth?: number,
): AppDimensFluid {
  return new AppDimensFluid(minValue, maxValue, minWidth, maxWidth);
}
