/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Main Class
 *
 * Description:
 * Main AppDimens class providing access to fixed and dynamic dimension builders.
 * Acts as the primary gateway for responsive dimension management.
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

import {Dimensions, PixelRatio} from 'react-native';
import {AppDimensFixed} from './AppDimensFixed';
import {AppDimensDynamic} from './AppDimensDynamic';
import {AppDimensAdjustmentFactors} from './AppDimensAdjustmentFactors';
import {
  DeviceInfo,
  ScreenDimensions,
  BASE_WIDTH_DP,
  BASE_HEIGHT_DP,
} from '../types';

/**
 * Main AppDimens class providing access to dimension builders
 */
export class AppDimens {
  private static instance: AppDimens;
  private globalCacheEnabled: boolean = true;
  private globalAspectRatioEnabled: boolean = true;
  private globalIgnoreMultiWindowAdjustment: boolean = false;

  private constructor() {}

  /**
   * Get singleton instance
   */
  public static getInstance(): AppDimens {
    if (!AppDimens.instance) {
      AppDimens.instance = new AppDimens();
    }
    return AppDimens.instance;
  }

  /**
   * Create fixed dimension builder
   */
  public fixed(
    initialValue: number,
    ignoreMultiWindowAdjustment?: boolean,
  ): AppDimensFixed {
    const finalIgnoreMultiWindow =
      ignoreMultiWindowAdjustment ?? this.globalIgnoreMultiWindowAdjustment;
    const builder = new AppDimensFixed(initialValue, finalIgnoreMultiWindow);

    if (!this.globalAspectRatioEnabled) {
      builder.aspectRatio(false);
    }

    return builder;
  }

  /**
   * Create dynamic dimension builder
   */
  public dynamic(
    initialValue: number,
    ignoreMultiWindowAdjustment?: boolean,
  ): AppDimensDynamic {
    const finalIgnoreMultiWindow =
      ignoreMultiWindowAdjustment ?? this.globalIgnoreMultiWindowAdjustment;
    return new AppDimensDynamic(initialValue, finalIgnoreMultiWindow);
  }

  /**
   * Calculate percentage-based dimension
   */
  public percentage(
    percentage: number,
    type: 'lowest' | 'highest' = 'lowest',
  ): number {
    if (percentage < 0 || percentage > 1) {
      throw new Error('Percentage must be between 0.0 and 1.0');
    }

    const screenDimensions =
      AppDimensAdjustmentFactors.getCurrentScreenDimensions();
    const {width, height} = screenDimensions;

    const dimensionToUse =
      type === 'highest' ? Math.max(width, height) : Math.min(width, height);

    return dimensionToUse * percentage;
  }

  /**
   * [EN] Calculate percentage-based dimension in Dp.
   * Compatible with Android API.
   * [PT] Calcula dimensão baseada em porcentagem em Dp.
   * Compatível com API Android.
   */
  public dynamicPercentageDp(
    percentage: number,
    type: 'lowest' | 'highest' = 'lowest',
  ): number {
    return this.percentage(percentage, type);
  }

  /**
   * [EN] Calculate percentage-based dimension in physical pixels.
   * Compatible with Android API.
   * [PT] Calcula dimensão baseada em porcentagem em pixels físicos.
   * Compatível com API Android.
   */
  public dynamicPercentagePx(
    percentage: number,
    type: 'lowest' | 'highest' = 'lowest',
  ): number {
    const dpValue = this.dynamicPercentageDp(percentage, type);
    return AppDimensAdjustmentFactors.pointsToPixels(dpValue);
  }

  /**
   * [EN] Calculate percentage-based dimension in scalable pixels (Sp).
   * Compatible with Android API.
   * [PT] Calcula dimensão baseada em porcentagem em pixels escaláveis (Sp).
   * Compatível com API Android.
   */
  public dynamicPercentageSp(
    percentage: number,
    type: 'lowest' | 'highest' = 'lowest',
  ): number {
    const dpValue = this.dynamicPercentageDp(percentage, type);
    const fontScale = AppDimensAdjustmentFactors.getFontScale();
    return dpValue * fontScale;
  }

  /**
   * Calculate available item count in a container
   */
  public calculateAvailableItemCount(
    containerSize: number,
    itemSize: number,
    itemMargin: number,
  ): number {
    const totalItemSize = itemSize + itemMargin * 2;
    return totalItemSize > 0 ? Math.floor(containerSize / totalItemSize) : 0;
  }

  /**
   * Convert millimeters to pixels
   */
  public mm(value: number): number {
    // 1 inch = 25.4 mm
    // 1 inch = 160 dp (Android standard)
    // 1 dp = 1 pixel in React Native (approximately)
    return (value / 25.4) * 160;
  }

  /**
   * Convert centimeters to pixels
   */
  public cm(value: number): number {
    return this.mm(value * 10);
  }

  /**
   * Convert inches to pixels
   */
  public inch(value: number): number {
    return value * 160; // 160 dp per inch
  }

  /**
   * Get current device information
   */
  public getDeviceInfo(): DeviceInfo {
    return AppDimensAdjustmentFactors.getCurrentDeviceInfo();
  }

  /**
   * Get current screen dimensions
   */
  public getScreenDimensions(): ScreenDimensions {
    return AppDimensAdjustmentFactors.getCurrentScreenDimensions();
  }

  /**
   * Set global cache control
   */
  public setGlobalCache(enabled: boolean): AppDimens {
    this.globalCacheEnabled = enabled;
    if (!enabled) {
      AppDimensAdjustmentFactors.clearCache();
    }
    return this;
  }

  /**
   * Get global cache setting
   */
  public isGlobalCacheEnabled(): boolean {
    return this.globalCacheEnabled;
  }

  /**
   * Set global aspect ratio adjustment
   */
  public setGlobalAspectRatio(enabled: boolean): AppDimens {
    this.globalAspectRatioEnabled = enabled;
    return this;
  }

  /**
   * Get global aspect ratio setting
   */
  public isGlobalAspectRatioEnabled(): boolean {
    return this.globalAspectRatioEnabled;
  }

  /**
   * Set global multi-window adjustment
   */
  public setGlobalIgnoreMultiWindowAdjustment(ignore: boolean): AppDimens {
    this.globalIgnoreMultiWindowAdjustment = ignore;
    return this;
  }

  /**
   * Get global multi-window adjustment setting
   */
  public isGlobalIgnoreMultiWindowAdjustment(): boolean {
    return this.globalIgnoreMultiWindowAdjustment;
  }

  /**
   * Clear all caches
   */
  public clearAllCaches(): void {
    AppDimensAdjustmentFactors.clearCache();
  }

  /**
   * Get library information
   */
  public getInfo(): Record<string, string> {
    return {
      version: '1.0.7',
      libraryName: 'AppDimens React Native',
      description: 'Smart and responsive dimensioning for React Native',
      platform: 'React Native',
      author: 'Jean Bodenberg',
    };
  }

  /**
   * Get performance metrics
   */
  public getPerformanceMetrics(): {
    cacheSize: number;
    deviceInfo: DeviceInfo;
    screenDimensions: ScreenDimensions;
  } {
    const cacheStats = AppDimensAdjustmentFactors.getCacheStats();
    return {
      cacheSize: cacheStats.size,
      deviceInfo: this.getDeviceInfo(),
      screenDimensions: this.getScreenDimensions(),
    };
  }
}

// Export singleton instance
export const appDimens = AppDimens.getInstance();
