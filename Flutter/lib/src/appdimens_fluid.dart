/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens Flutter - Fluid Scaling Model
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

import 'package:flutter/widgets.dart';
import 'appdimens_types.dart';

/// Configuration for fluid scaling
class FluidConfig {
  final double minValue;
  final double maxValue;
  final double minWidth;
  final double maxWidth;

  const FluidConfig({
    required this.minValue,
    required this.maxValue,
    required this.minWidth,
    required this.maxWidth,
  });
}

/// AppDimensFluid - Fluid (Clamp-like) Scaling Model
///
/// Provides smooth interpolation between minimum and maximum values
/// based on screen width. Similar to CSS clamp() but for Flutter.
///
/// Philosophy: Smooth transitions with bounded growth
/// Ideal for: Typography, fluid spacing, responsive sizes with limits
///
/// Example:
/// ```dart
/// // Basic usage
/// final fluid = AppDimensFluid(16, 24);
/// final fontSize = fluid.calculate(context);
///
/// // Custom breakpoints
/// final fluid = AppDimensFluid(12, 32, minWidth: 320, maxWidth: 768);
///
/// // With device type qualifiers
/// final fluid = AppDimensFluid(16, 24)
///   ..device(DeviceType.tablet, 20, 32);
/// final size = fluid.calculate(context);
/// ```
class AppDimensFluid {
  final double _minValue;
  final double _maxValue;
  final double _minWidth;
  final double _maxWidth;
  final Map<DeviceType, FluidConfig> _deviceQualifiers = {};
  final Map<int, FluidConfig> _screenQualifiers = {};

  /// Creates a new Fluid dimension builder
  ///
  /// [minValue] - Minimum value (at minWidth or below)
  /// [maxValue] - Maximum value (at maxWidth or above)
  /// [minWidth] - Minimum screen width breakpoint (default: 320)
  /// [maxWidth] - Maximum screen width breakpoint (default: 768)
  AppDimensFluid(
    this._minValue,
    this._maxValue, {
    double minWidth = 320,
    double maxWidth = 768,
  })  : _minWidth = minWidth,
        _maxWidth = maxWidth;

  /// Set fluid values for specific device type
  ///
  /// [type] - Device type
  /// [minValue] - Minimum value for this device
  /// [maxValue] - Maximum value for this device
  /// [minWidth] - Optional custom min width for this device
  /// [maxWidth] - Optional custom max width for this device
  ///
  /// Example:
  /// ```dart
  /// final fontSize = AppDimensFluid(14, 18)
  ///   ..device(DeviceType.tablet, 18, 24)
  ///   ..device(DeviceType.desktop, 24, 32);
  /// ```
  AppDimensFluid device(
    DeviceType type,
    double minValue,
    double maxValue, {
    double? minWidth,
    double? maxWidth,
  }) {
    _deviceQualifiers[type] = FluidConfig(
      minValue: minValue,
      maxValue: maxValue,
      minWidth: minWidth ?? _minWidth,
      maxWidth: maxWidth ?? _maxWidth,
    );
    return this;
  }

  /// Set fluid values for specific screen width qualifier
  ///
  /// [qualifier] - Screen width qualifier (e.g., 600 for sw600dp)
  /// [minValue] - Minimum value for this qualifier
  /// [maxValue] - Maximum value for this qualifier
  /// [minWidth] - Optional custom min width
  /// [maxWidth] - Optional custom max width
  ///
  /// Example:
  /// ```dart
  /// final spacing = AppDimensFluid(8, 16)
  ///   ..screen(600, 12, 20)
  ///   ..screen(840, 16, 24);
  /// ```
  AppDimensFluid screen(
    int qualifier,
    double minValue,
    double maxValue, {
    double? minWidth,
    double? maxWidth,
  }) {
    _screenQualifiers[qualifier] = FluidConfig(
      minValue: minValue,
      maxValue: maxValue,
      minWidth: minWidth ?? _minWidth,
      maxWidth: maxWidth ?? _maxWidth,
    );
    return this;
  }

  /// Calculate the fluid value based on current screen width
  ///
  /// [context] - Build context to get screen size
  /// [deviceType] - Optional device type for qualifier resolution
  /// Returns interpolated value between min and max
  double calculate(BuildContext context, {DeviceType? deviceType}) {
    final width = MediaQuery.of(context).size.width;
    return calculateForWidth(width, deviceType: deviceType);
  }

  /// Calculate the fluid value for a specific screen width
  ///
  /// [width] - Screen width
  /// [deviceType] - Optional device type for qualifier resolution
  /// Returns interpolated value between min and max
  double calculateForWidth(double width, {DeviceType? deviceType}) {
    // Resolve which config to use based on qualifiers
    final config = _resolveConfig(width, deviceType);

    // Perform fluid interpolation
    return _interpolate(width, config);
  }

  /// Get the minimum value
  double get min => _minValue;

  /// Get the maximum value
  double get max => _maxValue;

  /// Get the preferred (middle) value
  double get preferred => (_minValue + _maxValue) / 2;

  /// Linear interpolation at a specific progress point
  ///
  /// [t] - Progress value between 0 and 1
  /// Returns interpolated value
  double lerp(double t) {
    final clampedT = t.clamp(0.0, 1.0);
    return _minValue + (_maxValue - _minValue) * clampedT;
  }

  /// Resolve which configuration to use based on qualifiers
  /// Priority: Device Type > Screen Qualifier > Default
  FluidConfig _resolveConfig(double width, DeviceType? deviceType) {
    // Priority 1: Device Type
    if (deviceType != null && _deviceQualifiers.containsKey(deviceType)) {
      return _deviceQualifiers[deviceType]!;
    }

    // Priority 2: Screen Qualifier (find largest matching)
    FluidConfig? matchedConfig;
    int largestQualifier = 0;

    _screenQualifiers.forEach((qualifier, config) {
      if (width >= qualifier && qualifier > largestQualifier) {
        matchedConfig = config;
        largestQualifier = qualifier;
      }
    });

    if (matchedConfig != null) {
      return matchedConfig!;
    }

    // Priority 3: Default
    return FluidConfig(
      minValue: _minValue,
      maxValue: _maxValue,
      minWidth: _minWidth,
      maxWidth: _maxWidth,
    );
  }

  /// Perform fluid interpolation (clamp-like behavior)
  double _interpolate(double width, FluidConfig config) {
    // Below minimum width: return min value
    if (width <= config.minWidth) {
      return config.minValue;
    }

    // Above maximum width: return max value
    if (width >= config.maxWidth) {
      return config.maxValue;
    }

    // Between min and max: linear interpolation
    final progress =
        (width - config.minWidth) / (config.maxWidth - config.minWidth);

    return config.minValue + (config.maxValue - config.minValue) * progress;
  }
}

/// Create a fluid dimension (shorthand function)
///
/// [minValue] - Minimum value
/// [maxValue] - Maximum value
/// [minWidth] - Minimum screen width (default: 320)
/// [maxWidth] - Maximum screen width (default: 768)
/// Returns new AppDimensFluid instance
///
/// Example:
/// ```dart
/// final fontSize = fluid(16, 24).calculate(context);
/// ```
AppDimensFluid fluid(
  double minValue,
  double maxValue, {
  double minWidth = 320,
  double maxWidth = 768,
}) {
  return AppDimensFluid(minValue, maxValue, minWidth: minWidth, maxWidth: maxWidth);
}

