/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Utility Functions
 *
 * Description:
 * Utility functions for dimension calculations, caching, and screen analysis.
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

import 'dart:math' as math;
import 'package:flutter/material.dart';
import 'appdimens_types.dart';
import 'appdimens_physical_units.dart';

/// [EN] Utility class for AppDimens calculations and operations.
/// [PT] Classe utilitária para cálculos e operações do AppDimens.
class AppDimensUtils {
  AppDimensUtils._();

  // Unified constants for dimension calculations
  static const double BASE_WIDTH_DP = 300.0; // Unified: 300dp for exact Android compatibility
  static const double INCREMENT_DP_STEP = 1.0; // Unified step size (1dp granularity)
  static const double BASE_INCREMENT = 0.1 / 30.0; // Adjusted for 1dp step granularity (0.003333...)
  static const double DEFAULT_SENSITIVITY_K = 0.08 / 30.0; // Adjusted for 1dp step granularity (0.002667...)
  static const double REFERENCE_AR = 1.78; // Unified reference AR (16:9 landscape)

  /// [EN] Calculates adjustment factors for the current screen configuration.
  /// Uses unified formula: 1.0 + ((dimension - BASE_WIDTH) / STEP) × (BASE_INCREMENT + K × ln(AR / AR₀))
  /// @param context The BuildContext.
  /// @return ScreenAdjustmentFactors containing calculated factors.
  /// [PT] Calcula os fatores de ajuste para a configuração atual da tela.
  /// Usa fórmula unificada: 1.0 + ((dimension - BASE_WIDTH) / STEP) × (BASE_INCREMENT + K × ln(AR / AR₀))
  /// @param context O BuildContext.
  /// @return ScreenAdjustmentFactors contendo os fatores calculados.
  static ScreenAdjustmentFactors calculateAdjustmentFactors(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    
    final screenWidth = size.width;
    final screenHeight = size.height;
    
    final baseDimensionLowest = screenWidth < screenHeight ? screenWidth : screenHeight;
    final baseDimensionHighest = screenWidth > screenHeight ? screenWidth : screenHeight;
    
    // Unified formula: subtraction + step
    final differenceLowest = baseDimensionLowest - BASE_WIDTH_DP;
    final differenceHighest = baseDimensionHighest - BASE_WIDTH_DP;
    final adjustmentFactorLowest = differenceLowest / INCREMENT_DP_STEP;
    final adjustmentFactorHighest = differenceHighest / INCREMENT_DP_STEP;
    
    // Calculate aspect ratio (normalized to landscape: largest/smallest)
    final aspectRatio = screenWidth / screenHeight;
    final normalizedAR = aspectRatio >= 1.0 ? aspectRatio : 1.0 / aspectRatio;
    
    // Unified logarithmic adjustment
    final arAdjustment = DEFAULT_SENSITIVITY_K * math.log(normalizedAR / REFERENCE_AR);
    final finalIncrement = BASE_INCREMENT + arAdjustment;
    
    // Calculate factors using unified formula
    const baseFactor = 1.0;
    final withArFactorLowest = baseFactor + adjustmentFactorLowest * finalIncrement;
    final withArFactorHighest = baseFactor + adjustmentFactorHighest * finalIncrement;
    
    // Factor without AR (uses lowest for safety)
    final withoutArFactor = baseFactor + adjustmentFactorLowest * BASE_INCREMENT;
    
    return ScreenAdjustmentFactors(
      withArFactorLowest: withArFactorLowest,
      withArFactorHighest: withArFactorHighest,
      withoutArFactor: withoutArFactor,
      adjustmentFactorLowest: adjustmentFactorLowest,
      adjustmentFactorHighest: adjustmentFactorHighest,
    );
  }

  // NOTE: Helper methods for advanced calculations (_calculateScreenDiagonal,
  // _calculateAspectRatioFactor, _calculateDensityFactor, _calculateDeviceTypeFactor)
  // were removed as they were not being used. They can be restored from git history
  // if needed in future versions.

  /// [EN] Determines if the current context is in multi-window mode.
  /// @param context The BuildContext.
  /// @return True if in multi-window mode.
  /// [PT] Determina se o contexto atual está em modo multi-window.
  /// @param context O BuildContext.
  /// @return True se estiver em modo multi-window.
  static bool isMultiWindowMode(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    // In Flutter, we can detect multi-window by checking if the screen size
    // is significantly smaller than expected for the device type
    final size = mediaQuery.size;
    final deviceType = DeviceType.current(context);
    
    // Define expected minimum sizes for different device types
    final expectedSizes = {
      DeviceType.phone: const Size(320, 568), // iPhone SE size
      DeviceType.tablet: const Size(768, 1024), // iPad size
      DeviceType.watch: const Size(162, 197), // Apple Watch size
      DeviceType.tv: const Size(1920, 1080), // TV size
      DeviceType.carPlay: const Size(800, 600), // Car display size
      DeviceType.desktop: const Size(1024, 768), // Desktop size
      DeviceType.foldable: const Size(360, 640), // Foldable size
      DeviceType.unknown: const Size(320, 568), // Default size
    };
    
    final expectedSize = expectedSizes[deviceType] ?? const Size(320, 568);
    
    // Check if current size is significantly smaller than expected
    final widthRatio = size.width / expectedSize.width;
    final heightRatio = size.height / expectedSize.height;
    
    // If either dimension is less than 70% of expected, consider it multi-window
    return widthRatio < 0.7 || heightRatio < 0.7;
  }

  /// [EN] Calculates the optimal item count for a given container size and item size.
  /// @param containerSize The size of the container.
  /// @param itemSize The size of each item.
  /// @param spacing The spacing between items.
  /// @return The optimal number of items that can fit.
  /// [PT] Calcula a contagem ótima de itens para um tamanho de container e tamanho de item dados.
  /// @param containerSize O tamanho do container.
  /// @param itemSize O tamanho de cada item.
  /// @param spacing O espaçamento entre os itens.
  /// @return O número ótimo de itens que cabem.
  static int calculateOptimalItemCount(
    Size containerSize,
    Size itemSize, {
    double spacing = 0.0,
  }) {
    if (itemSize.width <= 0 || itemSize.height <= 0) return 0;
    
    final itemsPerRow = ((containerSize.width + spacing) / (itemSize.width + spacing)).floor();
    final itemsPerColumn = ((containerSize.height + spacing) / (itemSize.height + spacing)).floor();
    
    return itemsPerRow * itemsPerColumn;
  }

  /// [EN] Converts a value from one unit to another.
  /// @param value The value to convert.
  /// @param fromUnit The source unit.
  /// @param toUnit The target unit.
  /// @param context The BuildContext for screen information.
  /// @return The converted value.
  /// [PT] Converte um valor de uma unidade para outra.
  /// @param value O valor a ser convertido.
  /// @param fromUnit A unidade de origem.
  /// @param toUnit A unidade de destino.
  /// @param context O BuildContext para informações da tela.
  /// @return O valor convertido.
  static double convertUnit(
    double value,
    UnitType fromUnit,
    UnitType toUnit,
    BuildContext context,
  ) {
    if (fromUnit == toUnit) return value;
    
    final mediaQuery = MediaQuery.of(context);
    final devicePixelRatio = mediaQuery.devicePixelRatio;
    final size = mediaQuery.size;
    
    // Convert to logical pixels first
    double logicalPixels = _convertToLogicalPixels(value, fromUnit, devicePixelRatio, size);
    
    // Then convert to target unit
    return _convertFromLogicalPixels(logicalPixels, toUnit, devicePixelRatio, size);
  }

  /// [EN] Converts a value to logical pixels.
  /// @param value The value to convert.
  /// @param unit The source unit.
  /// @param devicePixelRatio The device pixel ratio.
  /// @param screenSize The screen size.
  /// @return The value in logical pixels.
  /// [PT] Converte um valor para pixels lógicos.
  /// @param value O valor a ser convertido.
  /// @param unit A unidade de origem.
  /// @param devicePixelRatio A proporção de pixels do dispositivo.
  /// @param screenSize O tamanho da tela.
  /// @return O valor em pixels lógicos.
  static double _convertToLogicalPixels(
    double value,
    UnitType unit,
    double devicePixelRatio,
    Size screenSize,
  ) {
    switch (unit) {
      case UnitType.px:
        return value / devicePixelRatio;
      case UnitType.dp:
        return value; // dp is already in logical pixels
      case UnitType.pt:
        return value;
      case UnitType.sp:
        return value;
      case UnitType.mm:
        return value * (screenSize.width / (screenSize.width / 25.4)); // Approximate conversion
      case UnitType.cm:
        return value * (screenSize.width / (screenSize.width / 2.54)); // Approximate conversion
      case UnitType.inch:
        return value * (screenSize.width / (screenSize.width / 1.0)); // Approximate conversion
    }
  }

  /// [EN] Converts a value from logical pixels to the target unit.
  /// @param value The value in logical pixels.
  /// @param unit The target unit.
  /// @param devicePixelRatio The device pixel ratio.
  /// @param screenSize The screen size.
  /// @return The converted value.
  /// [PT] Converte um valor de pixels lógicos para a unidade de destino.
  /// @param value O valor em pixels lógicos.
  /// @param unit A unidade de destino.
  /// @param devicePixelRatio A proporção de pixels do dispositivo.
  /// @param screenSize O tamanho da tela.
  /// @return O valor convertido.
  static double _convertFromLogicalPixels(
    double value,
    UnitType unit,
    double devicePixelRatio,
    Size screenSize,
  ) {
    switch (unit) {
      case UnitType.px:
        return value * devicePixelRatio;
      case UnitType.dp:
        return value; // dp is already in logical pixels
      case UnitType.pt:
        return value;
      case UnitType.sp:
        return value;
      case UnitType.mm:
        return value / (screenSize.width / 25.4); // Approximate conversion
      case UnitType.cm:
        return value / (screenSize.width / 2.54); // Approximate conversion
      case UnitType.inch:
        return value / (screenSize.width / 1.0); // Approximate conversion
    }
  }
}
