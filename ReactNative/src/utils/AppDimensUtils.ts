/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Utilities
 *
 * Description:
 * Utility functions for AppDimens React Native library.
 * Provides helper functions for common dimension operations.
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

import {PixelRatio, Dimensions} from 'react-native';
import {DeviceType, ScreenDimensions, CalculatedDimension} from '../types';

/**
 * Utility functions for AppDimens
 */
export class AppDimensUtils {
  /**
   * Normalize dimension value for different screen densities
   */
  public static normalize(value: number): number {
    return PixelRatio.roundToNearestPixel(value);
  }

  /**
   * Scale dimension based on screen size
   */
  public static scale(size: number): number {
    const {width, height} = Dimensions.get('window');
    const scale = Math.min(width, height) / 375; // Base on iPhone 6/7/8
    return this.normalize(size * scale);
  }

  /**
   * Vertical scale dimension
   */
  public static verticalScale(size: number): number {
    const {height} = Dimensions.get('window');
    const scale = height / 667; // Base on iPhone 6/7/8
    return this.normalize(size * scale);
  }

  /**
   * Moderate scale dimension with factor
   */
  public static moderateScale(size: number, factor: number = 0.5): number {
    return this.normalize(size + (this.scale(size) - size) * factor);
  }

  /**
   * Moderate vertical scale dimension with factor
   */
  public static moderateVerticalScale(
    size: number,
    factor: number = 0.5,
  ): number {
    return this.normalize(size + (this.verticalScale(size) - size) * factor);
  }

  /**
   * Get responsive font size
   */
  public static responsiveFontSize(size: number): number {
    const {width} = Dimensions.get('window');
    const scale = width / 375; // Base on iPhone 6/7/8
    return this.normalize(size * scale);
  }

  /**
   * Get responsive line height
   */
  public static responsiveLineHeight(
    fontSize: number,
    multiplier: number = 1.2,
  ): number {
    return this.normalize(fontSize * multiplier);
  }

  /**
   * Calculate aspect ratio
   */
  public static getAspectRatio(): number {
    const {width, height} = Dimensions.get('window');
    return width / height;
  }

  /**
   * Check if device is in landscape mode
   */
  public static isLandscape(): boolean {
    const {width, height} = Dimensions.get('window');
    return width > height;
  }

  /**
   * Check if device is in portrait mode
   */
  public static isPortrait(): boolean {
    return !this.isLandscape();
  }

  /**
   * Get device width
   */
  public static getDeviceWidth(): number {
    return Dimensions.get('window').width;
  }

  /**
   * Get device height
   */
  public static getDeviceHeight(): number {
    return Dimensions.get('window').height;
  }

  /**
   * Get device dimensions
   */
  public static getDeviceDimensions(): ScreenDimensions {
    const {width, height, scale} = Dimensions.get('window');
    const pixelRatio = PixelRatio.get();
    const fontScale = PixelRatio.getFontScale();

    return {
      width,
      height,
      scale,
      fontScale,
      pixelRatio,
    };
  }

  /**
   * Convert percentage to pixels
   */
  public static percentageToPixels(
    percentage: number,
    dimension: 'width' | 'height' = 'width',
  ): number {
    const {width, height} = Dimensions.get('window');
    const value = dimension === 'width' ? width : height;
    return (value * percentage) / 100;
  }

  /**
   * Convert pixels to percentage
   */
  public static pixelsToPercentage(
    pixels: number,
    dimension: 'width' | 'height' = 'width',
  ): number {
    const {width, height} = Dimensions.get('window');
    const value = dimension === 'width' ? width : height;
    return (pixels / value) * 100;
  }

  /**
   * Get safe area insets (placeholder for react-native-safe-area-context)
   */
  public static getSafeAreaInsets(): {
    top: number;
    bottom: number;
    left: number;
    right: number;
  } {
    // This is a placeholder implementation
    // In real usage, you would use react-native-safe-area-context
    return {
      top: 0,
      bottom: 0,
      left: 0,
      right: 0,
    };
  }

  /**
   * Calculate grid item size
   */
  public static calculateGridItemSize(
    containerWidth: number,
    itemCount: number,
    spacing: number = 0,
  ): number {
    const totalSpacing = spacing * (itemCount - 1);
    return (containerWidth - totalSpacing) / itemCount;
  }

  /**
   * Calculate grid columns based on item size
   */
  public static calculateGridColumns(
    containerWidth: number,
    itemSize: number,
    spacing: number = 0,
  ): number {
    return Math.floor((containerWidth + spacing) / (itemSize + spacing));
  }

  /**
   * Interpolate value between two points
   */
  public static interpolate(
    value: number,
    inputRange: number[],
    outputRange: number[],
  ): number {
    if (inputRange.length !== outputRange.length) {
      throw new Error('Input range and output range must have the same length');
    }

    // Find the range where the value falls
    for (let i = 0; i < inputRange.length - 1; i++) {
      if (value >= inputRange[i] && value <= inputRange[i + 1]) {
        const range = inputRange[i + 1] - inputRange[i];
        const progress = (value - inputRange[i]) / range;
        return (
          outputRange[i] + (outputRange[i + 1] - outputRange[i]) * progress
        );
      }
    }

    // Clamp to the nearest range
    if (value < inputRange[0]) {
      return outputRange[0];
    }
    return outputRange[outputRange.length - 1];
  }

  /**
   * Clamp value between min and max
   */
  public static clamp(value: number, min: number, max: number): number {
    return Math.min(Math.max(value, min), max);
  }

  /**
   * Round to nearest multiple
   */
  public static roundToNearest(value: number, multiple: number): number {
    return Math.round(value / multiple) * multiple;
  }

  /**
   * Format dimension for display
   */
  public static formatDimension(
    value: number,
    unit: 'px' | 'dp' | 'pt' = 'px',
  ): string {
    return `${Math.round(value)}${unit}`;
  }

  /**
   * Debug dimension calculation
   */
  public static debugDimension(
    originalValue: number,
    calculatedValue: number,
    type: string,
  ): CalculatedDimension {
    const deviceDimensions = this.getDeviceDimensions();

    return {
      value: calculatedValue,
      type: type as any,
      originalValue,
      scale: calculatedValue / originalValue,
      deviceInfo: {
        type: 'phone' as DeviceType,
        platform: 'ios' as any,
        orientation: this.isLandscape() ? 'landscape' : 'portrait',
        isTablet: false,
        isPhone: true,
        isDesktop: false,
        isWatch: false,
        isTV: false,
        isCar: false,
      },
      screenDimensions: deviceDimensions,
    };
  }
}
