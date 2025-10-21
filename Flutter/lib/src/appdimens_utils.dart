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

import 'package:flutter/material.dart';
import 'appdimens_types.dart';

/// [EN] Utility class for AppDimens calculations and operations.
/// [PT] Classe utilitária para cálculos e operações do AppDimens.
class AppDimensUtils {
  AppDimensUtils._();

  /// [EN] Calculates adjustment factors for the current screen configuration.
  /// @param context The BuildContext.
  /// @return ScreenAdjustmentFactors containing calculated factors.
  /// [PT] Calcula os fatores de ajuste para a configuração atual da tela.
  /// @param context O BuildContext.
  /// @return ScreenAdjustmentFactors contendo os fatores calculados.
  static ScreenAdjustmentFactors calculateAdjustmentFactors(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    final devicePixelRatio = mediaQuery.devicePixelRatio;
    
    final screenWidth = size.width;
    final screenHeight = size.height;
    final screenDiagonal = _calculateScreenDiagonal(screenWidth, screenHeight);
    
    final deviceType = DeviceType.current(context);
    final uiModeType = UiModeType.current(context);
    
    // Calculate base dimension (smallest dimension for scaling)
    final baseDimension = screenWidth < screenHeight ? screenWidth : screenHeight;
    
    // Calculate aspect ratio factor
    final aspectRatio = screenWidth / screenHeight;
    final aspectRatioFactor = _calculateAspectRatioFactor(aspectRatio, deviceType);
    
    // Calculate density factor
    final densityFactor = _calculateDensityFactor(devicePixelRatio, deviceType);
    
    // Calculate device type factor
    final deviceTypeFactor = _calculateDeviceTypeFactor(deviceType, uiModeType);
    
    return ScreenAdjustmentFactors(
      screenWidth: screenWidth,
      screenHeight: screenHeight,
      screenDiagonal: screenDiagonal,
      devicePixelRatio: devicePixelRatio,
      baseDimension: baseDimension,
      aspectRatio: aspectRatio,
      aspectRatioFactor: aspectRatioFactor,
      densityFactor: densityFactor,
      deviceTypeFactor: deviceTypeFactor,
      deviceType: deviceType,
      uiModeType: uiModeType,
    );
  }

  /// [EN] Calculates the screen diagonal in logical pixels.
  /// @param width Screen width in logical pixels.
  /// @param height Screen height in logical pixels.
  /// @return The diagonal length in logical pixels.
  /// [PT] Calcula a diagonal da tela em pixels lógicos.
  /// @param width Largura da tela em pixels lógicos.
  /// @param height Altura da tela em pixels lógicos.
  /// @return O comprimento da diagonal em pixels lógicos.
  static double _calculateScreenDiagonal(double width, double height) {
    return (width * width + height * height).sqrt();
  }

  /// [EN] Calculates the aspect ratio adjustment factor.
  /// @param aspectRatio The current aspect ratio.
  /// @param deviceType The current device type.
  /// @return The aspect ratio factor.
  /// [PT] Calcula o fator de ajuste de proporção.
  /// @param aspectRatio A proporção atual.
  /// @param deviceType O tipo de dispositivo atual.
  /// @return O fator de proporção.
  static double _calculateAspectRatioFactor(double aspectRatio, DeviceType deviceType) {
    // Standard aspect ratios for different device types
    final standardRatios = {
      DeviceType.phone: 16.0 / 9.0, // Standard phone ratio
      DeviceType.tablet: 4.0 / 3.0, // Standard tablet ratio
      DeviceType.watch: 1.0, // Square watch
      DeviceType.tv: 16.0 / 9.0, // Standard TV ratio
      DeviceType.carPlay: 16.0 / 9.0, // Car display ratio
      DeviceType.desktop: 16.0 / 9.0, // Desktop ratio
      DeviceType.foldable: 16.0 / 10.0, // Foldable ratio
      DeviceType.unknown: 16.0 / 9.0, // Default ratio
    };
    
    final standardRatio = standardRatios[deviceType] ?? 16.0 / 9.0;
    final ratioDifference = (aspectRatio - standardRatio).abs() / standardRatio;
    
    // Return a factor that adjusts based on how far the current ratio is from standard
    // Closer to standard ratio = factor closer to 1.0
    return 1.0 - (ratioDifference * 0.1); // Max 10% adjustment
  }

  /// [EN] Calculates the density adjustment factor.
  /// @param devicePixelRatio The device pixel ratio.
  /// @param deviceType The current device type.
  /// @return The density factor.
  /// [PT] Calcula o fator de ajuste de densidade.
  /// @param devicePixelRatio A proporção de pixels do dispositivo.
  /// @param deviceType O tipo de dispositivo atual.
  /// @return O fator de densidade.
  static double _calculateDensityFactor(double devicePixelRatio, DeviceType deviceType) {
    // Standard densities for different device types
    final standardDensities = {
      DeviceType.phone: 2.0, // Standard phone density
      DeviceType.tablet: 2.0, // Standard tablet density
      DeviceType.watch: 2.0, // Standard watch density
      DeviceType.tv: 1.0, // TV density (usually lower)
      DeviceType.carPlay: 2.0, // Car display density
      DeviceType.desktop: 1.0, // Desktop density (usually lower)
      DeviceType.foldable: 2.5, // Foldable density (usually higher)
      DeviceType.unknown: 2.0, // Default density
    };
    
    final standardDensity = standardDensities[deviceType] ?? 2.0;
    final densityDifference = (devicePixelRatio - standardDensity).abs() / standardDensity;
    
    // Return a factor that adjusts based on density difference
    return 1.0 - (densityDifference * 0.05); // Max 5% adjustment
  }

  /// [EN] Calculates the device type adjustment factor.
  /// @param deviceType The current device type.
  /// @param uiModeType The current UI mode type.
  /// @return The device type factor.
  /// [PT] Calcula o fator de ajuste do tipo de dispositivo.
  /// @param deviceType O tipo de dispositivo atual.
  /// @param uiModeType O tipo de modo de UI atual.
  /// @return O fator do tipo de dispositivo.
  static double _calculateDeviceTypeFactor(DeviceType deviceType, UiModeType uiModeType) {
    // Base factors for different device types
    final baseFactors = {
      DeviceType.phone: 1.0,
      DeviceType.tablet: 1.2, // Tablets get slightly larger scaling
      DeviceType.watch: 0.8, // Watches get smaller scaling
      DeviceType.tv: 1.5, // TVs get larger scaling
      DeviceType.carPlay: 1.3, // Car displays get larger scaling
      DeviceType.desktop: 1.1, // Desktop gets slightly larger scaling
      DeviceType.foldable: 1.1, // Foldables get slightly larger scaling
      DeviceType.unknown: 1.0, // Default factor
    };
    
    // UI mode adjustments
    final uiModeFactors = {
      UiModeType.normal: 1.0,
      UiModeType.carPlay: 1.2, // Car mode gets larger scaling
      UiModeType.tv: 1.3, // TV mode gets larger scaling
      UiModeType.watch: 0.9, // Watch mode gets smaller scaling
      UiModeType.mac: 1.1, // Mac mode gets slightly larger scaling
      UiModeType.unknown: 1.0, // Default factor
    };
    
    final baseFactor = baseFactors[deviceType] ?? 1.0;
    final uiModeFactor = uiModeFactors[uiModeType] ?? 1.0;
    
    return baseFactor * uiModeFactor;
  }

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
