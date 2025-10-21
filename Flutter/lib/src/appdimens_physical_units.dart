/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Physical Units
 *
 * Description:
 * Physical units conversion and management for real-world measurements.
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
import 'appdimens_utils.dart';

/// [EN] Physical units manager for converting real-world measurements to screen pixels.
/// [PT] Gerenciador de unidades físicas para converter medidas do mundo real para pixels da tela.
class AppDimensPhysicalUnits {
  AppDimensPhysicalUnits._();

  // MARK: - Conversion Constants

  /// [EN] Millimeters per inch.
  /// [PT] Milímetros por polegada.
  static const double mmPerInch = 25.4;

  /// [EN] Centimeters per inch.
  /// [PT] Centímetros por polegada.
  static const double cmPerInch = 2.54;

  /// [EN] Points per inch (standard for print).
  /// [PT] Pontos por polegada (padrão para impressão).
  static const double pointsPerInch = 72.0;

  /// [EN] Pixels per inch (standard for screens).
  /// [PT] Pixels por polegada (padrão para telas).
  static const double pixelsPerInch = 96.0;

  // MARK: - Conversion Methods

  /// [EN] Converts millimeters to logical pixels.
  /// @param mm The value in millimeters.
  /// @param context The BuildContext.
  /// @return The value in logical pixels.
  /// [PT] Converte milímetros para pixels lógicos.
  /// @param mm O valor em milímetros.
  /// @param context O BuildContext.
  /// @return O valor em pixels lógicos.
  static double mmToPixels(double mm, BuildContext context) {
    return AppDimensUtils.convertUnit(mm, UnitType.mm, UnitType.pt, context);
  }

  /// [EN] Converts centimeters to logical pixels.
  /// @param cm The value in centimeters.
  /// @param context The BuildContext.
  /// @return The value in logical pixels.
  /// [PT] Converte centímetros para pixels lógicos.
  /// @param cm O valor em centímetros.
  /// @param context O BuildContext.
  /// @return O valor em pixels lógicos.
  static double cmToPixels(double cm, BuildContext context) {
    return AppDimensUtils.convertUnit(cm, UnitType.cm, UnitType.pt, context);
  }

  /// [EN] Converts inches to logical pixels.
  /// @param inches The value in inches.
  /// @param context The BuildContext.
  /// @return The value in logical pixels.
  /// [PT] Converte polegadas para pixels lógicos.
  /// @param inches O valor em polegadas.
  /// @param context O BuildContext.
  /// @return O valor em pixels lógicos.
  static double inchesToPixels(double inches, BuildContext context) {
    return AppDimensUtils.convertUnit(inches, UnitType.inch, UnitType.pt, context);
  }

  /// [EN] Converts logical pixels to millimeters.
  /// @param pixels The value in logical pixels.
  /// @param context The BuildContext.
  /// @return The value in millimeters.
  /// [PT] Converte pixels lógicos para milímetros.
  /// @param pixels O valor em pixels lógicos.
  /// @param context O BuildContext.
  /// @return O valor em milímetros.
  static double pixelsToMm(double pixels, BuildContext context) {
    return AppDimensUtils.convertUnit(pixels, UnitType.pt, UnitType.mm, context);
  }

  /// [EN] Converts logical pixels to centimeters.
  /// @param pixels The value in logical pixels.
  /// @param context The BuildContext.
  /// @return The value in centimeters.
  /// [PT] Converte pixels lógicos para centímetros.
  /// @param pixels O valor em pixels lógicos.
  /// @param context O BuildContext.
  /// @return O valor em centímetros.
  static double pixelsToCm(double pixels, BuildContext context) {
    return AppDimensUtils.convertUnit(pixels, UnitType.pt, UnitType.cm, context);
  }

  /// [EN] Converts logical pixels to inches.
  /// @param pixels The value in logical pixels.
  /// @param context The BuildContext.
  /// @return The value in inches.
  /// [PT] Converte pixels lógicos para polegadas.
  /// @param pixels O valor em pixels lógicos.
  /// @param context O BuildContext.
  /// @return O valor em polegadas.
  static double pixelsToInches(double pixels, BuildContext context) {
    return AppDimensUtils.convertUnit(pixels, UnitType.pt, UnitType.inch, context);
  }

  // MARK: - Convenience Methods

  /// [EN] Gets the screen density in dots per inch.
  /// @param context The BuildContext.
  /// @return The screen density in DPI.
  /// [PT] Obtém a densidade da tela em pontos por polegada.
  /// @param context O BuildContext.
  /// @return A densidade da tela em DPI.
  static double getScreenDensity(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    return mediaQuery.devicePixelRatio * pixelsPerInch;
  }

  /// [EN] Gets the screen size in physical units.
  /// @param context The BuildContext.
  /// @param unit The target unit type.
  /// @return The screen size in the specified unit.
  /// [PT] Obtém o tamanho da tela em unidades físicas.
  /// @param context O BuildContext.
  /// @param unit O tipo de unidade de destino.
  /// @return O tamanho da tela na unidade especificada.
  static Size getScreenSizeInUnit(BuildContext context, UnitType unit) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    
    final width = AppDimensUtils.convertUnit(size.width, UnitType.pt, unit, context);
    final height = AppDimensUtils.convertUnit(size.height, UnitType.pt, unit, context);
    
    return Size(width, height);
  }

  /// [EN] Gets the screen diagonal in physical units.
  /// @param context The BuildContext.
  /// @param unit The target unit type.
  /// @return The screen diagonal in the specified unit.
  /// [PT] Obtém a diagonal da tela em unidades físicas.
  /// @param context O BuildContext.
  /// @param unit O tipo de unidade de destino.
  /// @return A diagonal da tela na unidade especificada.
  static double getScreenDiagonalInUnit(BuildContext context, UnitType unit) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    
    final diagonalPixels = (size.width * size.width + size.height * size.height).sqrt();
    return AppDimensUtils.convertUnit(diagonalPixels, UnitType.pt, unit, context);
  }

  // MARK: - Utility Methods

  /// [EN] Calculates the optimal font size for a given physical height.
  /// @param physicalHeight The physical height in the specified unit.
  /// @param unit The unit type.
  /// @param context The BuildContext.
  /// @return The optimal font size in logical pixels.
  /// [PT] Calcula o tamanho ótimo da fonte para uma altura física dada.
  /// @param physicalHeight A altura física na unidade especificada.
  /// @param unit O tipo de unidade.
  /// @param context O BuildContext.
  /// @return O tamanho ótimo da fonte em pixels lógicos.
  static double calculateOptimalFontSize(
    double physicalHeight,
    UnitType unit,
    BuildContext context,
  ) {
    final heightInPixels = AppDimensUtils.convertUnit(physicalHeight, unit, UnitType.pt, context);
    
    // Base font size calculation (adjust based on your needs)
    final baseFontSize = heightInPixels * 0.1; // 10% of the height
    
    // Apply device type adjustments
    final deviceType = DeviceType.current(context);
    final deviceTypeFactors = {
      DeviceType.phone: 1.0,
      DeviceType.tablet: 1.2,
      DeviceType.watch: 0.8,
      DeviceType.tv: 1.5,
      DeviceType.carPlay: 1.3,
      DeviceType.desktop: 1.1,
      DeviceType.foldable: 1.1,
      DeviceType.unknown: 1.0,
    };
    
    final factor = deviceTypeFactors[deviceType] ?? 1.0;
    return baseFontSize * factor;
  }

  /// [EN] Calculates the optimal spacing for a given physical distance.
  /// @param physicalDistance The physical distance in the specified unit.
  /// @param unit The unit type.
  /// @param context The BuildContext.
  /// @return The optimal spacing in logical pixels.
  /// [PT] Calcula o espaçamento ótimo para uma distância física dada.
  /// @param physicalDistance A distância física na unidade especificada.
  /// @param unit O tipo de unidade.
  /// @param context O BuildContext.
  /// @return O espaçamento ótimo em pixels lógicos.
  static double calculateOptimalSpacing(
    double physicalDistance,
    UnitType unit,
    BuildContext context,
  ) {
    final distanceInPixels = AppDimensUtils.convertUnit(physicalDistance, unit, UnitType.pt, context);
    
    // Apply device type adjustments
    final deviceType = DeviceType.current(context);
    final deviceTypeFactors = {
      DeviceType.phone: 1.0,
      DeviceType.tablet: 1.1,
      DeviceType.watch: 0.9,
      DeviceType.tv: 1.3,
      DeviceType.carPlay: 1.2,
      DeviceType.desktop: 1.05,
      DeviceType.foldable: 1.05,
      DeviceType.unknown: 1.0,
    };
    
    final factor = deviceTypeFactors[deviceType] ?? 1.0;
    return distanceInPixels * factor;
  }

  // MARK: - Validation Methods

  /// [EN] Validates if a physical unit value is within reasonable bounds.
  /// @param value The value to validate.
  /// @param unit The unit type.
  /// @param context The BuildContext.
  /// @return True if the value is within reasonable bounds.
  /// [PT] Valida se um valor de unidade física está dentro de limites razoáveis.
  /// @param value O valor a ser validado.
  /// @param unit O tipo de unidade.
  /// @param context O BuildContext.
  /// @return True se o valor está dentro de limites razoáveis.
  static bool isValidPhysicalValue(double value, UnitType unit, BuildContext context) {
    if (value <= 0) return false;
    
    final mediaQuery = MediaQuery.of(context);
    final screenSize = mediaQuery.size;
    
    // Convert to pixels for validation
    final valueInPixels = AppDimensUtils.convertUnit(value, unit, UnitType.pt, context);
    
    // Check if the value is within reasonable bounds (0.1% to 100% of screen size)
    final minValue = (screenSize.width + screenSize.height) * 0.001;
    final maxValue = (screenSize.width + screenSize.height) * 1.0;
    
    return valueInPixels >= minValue && valueInPixels <= maxValue;
  }
}
