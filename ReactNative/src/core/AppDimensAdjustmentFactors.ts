/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Adjustment Factors
 *
 * Description:
 * Core adjustment factor calculations for responsive dimension scaling.
 * Provides mathematical models for Fixed and Dynamic scaling.
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

import {Dimensions, PixelRatio, Platform} from 'react-native';
import {
  AdjustmentFactors,
  DeviceInfo,
  ScreenDimensions,
  BASE_WIDTH_DP,
  BASE_HEIGHT_DP,
  BASE_DP_FACTOR,
  BASE_INCREMENT,
  INCREMENT_DP_STEP,
  DEFAULT_SENSITIVITY_K,
  REFERENCE_AR,
  DEVICE_TYPES,
  UI_MODE_TYPES,
  ScreenQualifierEntry,
  UiModeType,
  DeviceType,
} from '../types';

/**
 * Core adjustment factor calculations for AppDimens
 */
export class AppDimensAdjustmentFactors {
  private static cache = new Map<string, AdjustmentFactors>();
  private static lastScreenDimensions: ScreenDimensions | null = null;

  /**
   * Get current screen dimensions
   */
  public static getCurrentScreenDimensions(): ScreenDimensions {
    const {width, height, scale} = Dimensions.get('window');
    const pixelRatio = PixelRatio.get();
    const fontScale = PixelRatio.getFontScale();

    const screenDimensions: ScreenDimensions = {
      width,
      height,
      scale,
      fontScale,
      pixelRatio,
    };

    // Update cache if dimensions changed
    if (
      !this.lastScreenDimensions ||
      this.lastScreenDimensions.width !== width ||
      this.lastScreenDimensions.height !== height
    ) {
      this.lastScreenDimensions = screenDimensions;
      this.cache.clear();
    }

    return screenDimensions;
  }

  /**
   * Get current device information
   */
  public static getCurrentDeviceInfo(): DeviceInfo {
    const {width, height} = Dimensions.get('window');
    const platform = Platform.OS as 'ios' | 'android' | 'web';
    const orientation = width > height ? 'landscape' : 'portrait';

    // Determine device type based on screen dimensions and platform
    const isTablet = this.isTabletDevice(width, height, platform);
    const isPhone = !isTablet && platform !== 'web';
    const isDesktop = platform === 'web';
    const isWatch = this.isWatchDevice(width, height);
    const isTV = this.isTVDevice(width, height);
    const isCar = this.isCarDevice(width, height);

    let deviceType: DeviceType = DEVICE_TYPES.PHONE;
    if (isTablet) deviceType = DEVICE_TYPES.TABLET;
    else if (isDesktop) deviceType = DEVICE_TYPES.DESKTOP;
    else if (isWatch) deviceType = DEVICE_TYPES.WATCH;
    else if (isTV) deviceType = DEVICE_TYPES.TV;
    else if (isCar) deviceType = DEVICE_TYPES.CAR;

    return {
      type: deviceType,
      platform,
      orientation,
      isTablet,
      isPhone,
      isDesktop,
      isWatch,
      isTV,
      isCar,
    };
  }

  /**
   * Calculate adjustment factors for the current screen
   */
  public static calculateAdjustmentFactors(): AdjustmentFactors {
    const screenDimensions = this.getCurrentScreenDimensions();
    const cacheKey = `${screenDimensions.width}_${screenDimensions.height}`;

    // Check cache first
    if (this.cache.has(cacheKey)) {
      return this.cache.get(cacheKey)!;
    }

    const {width, height} = screenDimensions;
    const smallestWidth = Math.min(width, height);
    const largestWidth = Math.max(width, height);

    // Calculate base adjustment factors
    const adjustmentFactorLowest =
      this.calculateBaseAdjustmentFactor(smallestWidth);
    const adjustmentFactorHighest =
      this.calculateBaseAdjustmentFactor(largestWidth);

    // Calculate aspect ratio factors
    const aspectRatio = this.getReferenceAspectRatio(width, height);
    // Normalize AR to landscape (largest/smallest)
    const normalizedAR = aspectRatio >= 1.0 ? aspectRatio : 1.0 / aspectRatio;
    const arAdjustment =
      DEFAULT_SENSITIVITY_K * Math.log(normalizedAR / REFERENCE_AR);

    const withArFactorLowest =
      BASE_DP_FACTOR + adjustmentFactorLowest * (BASE_INCREMENT + arAdjustment);
    const withArFactorHighest =
      BASE_DP_FACTOR +
      adjustmentFactorHighest * (BASE_INCREMENT + arAdjustment);
    const withoutArFactor =
      BASE_DP_FACTOR + adjustmentFactorLowest * BASE_INCREMENT;

    const factors: AdjustmentFactors = {
      adjustmentFactorLowest,
      adjustmentFactorHighest,
      withArFactorLowest,
      withArFactorHighest,
      withoutArFactor,
    };

    // Cache the result
    this.cache.set(cacheKey, factors);

    return factors;
  }

  /**
   * Calculate base adjustment factor for a given screen dimension
   * Unified formula: (dimension - BASE_WIDTH) / INCREMENT_DP_STEP
   */
  private static calculateBaseAdjustmentFactor(
    screenDimension: number,
  ): number {
    // Convert to DP equivalent (assuming 1dp = 1px for simplicity in React Native)
    const screenDimensionDp = screenDimension;

    // Calculate adjustment based on the difference from base width
    // Unified formula: use subtraction + step (like Android/Web)
    const difference = screenDimensionDp - BASE_WIDTH_DP;
    const adjustmentFactor = difference / INCREMENT_DP_STEP;

    return Math.max(0, adjustmentFactor);
  }

  /**
   * Get reference aspect ratio for given dimensions
   * Unified: Returns largest/smallest (landscape normalization)
   */
  public static getReferenceAspectRatio(width: number, height: number): number {
    // Unified: normalize to landscape (largest/smallest)
    return width >= height ? width / height : height / width;
  }

  /**
   * Resolve intersection condition for screen qualifiers
   */
  public static resolveIntersectionCondition(
    entry: ScreenQualifierEntry,
    smallestWidth: number,
    currentScreenWidth: number,
    currentScreenHeight: number,
  ): boolean {
    switch (entry.type) {
      case 'smallWidth':
        return smallestWidth >= entry.value;
      case 'width':
        return currentScreenWidth >= entry.value;
      case 'height':
        return currentScreenHeight >= entry.value;
      default:
        return false;
    }
  }

  /**
   * Resolve qualifier value from custom map
   */
  public static resolveQualifierValue(
    customMap: Map<ScreenQualifierEntry, number>,
    currentDeviceType: DeviceType,
    currentScreenSize: number,
    initialValue: number,
  ): number {
    const sortedQualifiers = Array.from(customMap.keys()).sort(
      (a, b) => b.value - a.value,
    );

    for (const entry of sortedQualifiers) {
      if (
        this.resolveIntersectionCondition(
          entry,
          currentScreenSize,
          currentScreenSize,
          currentScreenSize,
        )
      ) {
        return customMap.get(entry) || initialValue;
      }
    }

    return initialValue;
  }

  /**
   * Check if device is in multi-window mode
   */
  public static isMultiWindowMode(): boolean {
    // In React Native, multi-window mode detection is limited
    // This is a placeholder for future implementation
    return false;
  }

  /**
   * Convert points to pixels
   */
  public static pointsToPixels(points: number): number {
    return points * PixelRatio.get();
  }

  /**
   * [EN] Get the system font scale factor.
   * Compatible with Android/iOS API.
   * [PT] Obtém o fator de escala de fonte do sistema.
   * Compatível com API Android/iOS.
   */
  public static getFontScale(): number {
    return PixelRatio.getFontScale();
  }

  /**
   * Convert pixels to points
   */
  public static pixelsToPoints(pixels: number): number {
    return pixels / PixelRatio.get();
  }

  /**
   * Check if device is a tablet
   */
  private static isTabletDevice(
    width: number,
    height: number,
    platform: string,
  ): boolean {
    const diagonal = Math.sqrt(width * width + height * height);
    const density = PixelRatio.get();

    // Convert to inches (approximate)
    const diagonalInches = diagonal / (density * 160);

    // Consider tablet if diagonal > 7 inches
    return diagonalInches > 7;
  }

  /**
   * Check if device is a watch
   */
  private static isWatchDevice(width: number, height: number): boolean {
    const diagonal = Math.sqrt(width * width + height * height);
    const density = PixelRatio.get();
    const diagonalInches = diagonal / (density * 160);

    // Consider watch if diagonal < 3 inches
    return diagonalInches < 3;
  }

  /**
   * Check if device is a TV
   */
  private static isTVDevice(width: number, height: number): boolean {
    const diagonal = Math.sqrt(width * width + height * height);
    const density = PixelRatio.get();
    const diagonalInches = diagonal / (density * 160);

    // Consider TV if diagonal > 24 inches
    return diagonalInches > 24;
  }

  /**
   * Check if device is a car display
   */
  private static isCarDevice(width: number, height: number): boolean {
    // Car displays are typically 7-12 inches
    const diagonal = Math.sqrt(width * width + height * height);
    const density = PixelRatio.get();
    const diagonalInches = diagonal / (density * 160);

    return diagonalInches >= 7 && diagonalInches <= 12;
  }

  /**
   * Clear adjustment factor cache
   */
  public static clearCache(): void {
    this.cache.clear();
    this.lastScreenDimensions = null;
  }

  /**
   * Get cache statistics
   */
  public static getCacheStats(): {size: number; keys: string[]} {
    return {
      size: this.cache.size,
      keys: Array.from(this.cache.keys()),
    };
  }
}
