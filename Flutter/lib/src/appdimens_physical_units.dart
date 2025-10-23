/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-23
 *
 * Library: AppDimens Flutter - Physical Units
 *
 * Description:
 * Physical units conversion utilities for AppDimens Flutter library,
 * providing conversion between physical measurements (mm, cm, inch) and dp/px.
 * Compatible with Android/iOS API.
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

/// [EN] Defines the supported physical measurement units for conversion
/// into logical pixels.
/// [PT] Define as unidades de medida física suportadas para conversão
/// em pixels lógicos.
enum UnitType {
  mm,
  cm,
  inch,
  dp,
  sp,
  pt,
  px,
}

/// [EN] Utility class for physical unit conversions.
/// Compatible with Android/iOS API.
/// [PT] Classe utilitária para conversões de unidades físicas.
/// Compatível com API Android/iOS.
class AppDimensPhysicalUnits {
  // MARK: - Constants

  /// [EN] Points per inch (standard resolution).
  /// [PT] Pontos por polegada (resolução padrão).
  static const double pointsPerInch = 72.0;

  /// [EN] Points per centimeter.
  /// [PT] Pontos por centímetro.
  static const double pointsPerCm = pointsPerInch / 2.54;

  /// [EN] Points per millimeter.
  /// [PT] Pontos por milímetro.
  static const double pointsPerMm = pointsPerCm / 10.0;

  /// [EN] Millimeters per inch.
  /// [PT] Milímetros por polegada.
  static const double mmPerInch = 25.4;

  /// [EN] Centimeters per inch.
  /// [PT] Centímetros por polegada.
  static const double cmPerInch = 2.54;

  // MARK: - Conversion Methods (MM)

  /// [EN] Converts millimeters to density-independent pixels (dp).
  /// @param mm The value in millimeters.
  /// @param context The BuildContext.
  /// @return The value in dp.
  /// [PT] Converte milímetros para pixels independentes de densidade (dp).
  /// @param mm O valor em milímetros.
  /// @param context O BuildContext.
  /// @return O valor em dp.
  static double toDpFromMm(double mm, BuildContext context) {
    final devicePixelRatio = MediaQuery.of(context).devicePixelRatio;
    final ppi = devicePixelRatio * 160.0; // Android default PPI
    return (mm / mmPerInch) * ppi / devicePixelRatio;
  }

  /// [EN] Converts millimeters to physical pixels (px).
  /// @param mm The value in millimeters.
  /// @param context The BuildContext.
  /// @return The value in pixels.
  /// [PT] Converte milímetros para pixels físicos (px).
  /// @param mm O valor em milímetros.
  /// @param context O BuildContext.
  /// @return O valor em pixels.
  static double toPxFromMm(double mm, BuildContext context) {
    final dpValue = toDpFromMm(mm, context);
    final devicePixelRatio = MediaQuery.of(context).devicePixelRatio;
    return dpValue * devicePixelRatio;
  }

  /// [EN] Converts millimeters to scalable pixels (sp).
  /// @param mm The value in millimeters.
  /// @param context The BuildContext.
  /// @return The value in sp.
  /// [PT] Converte milímetros para pixels escaláveis (sp).
  /// @param mm O valor em milímetros.
  /// @param context O BuildContext.
  /// @return O valor em sp.
  static double toSpFromMm(double mm, BuildContext context) {
    final dpValue = toDpFromMm(mm, context);
    final textScaleFactor = MediaQuery.of(context).textScaleFactor;
    return dpValue * textScaleFactor;
  }

  // MARK: - Conversion Methods (CM)

  /// [EN] Converts centimeters to density-independent pixels (dp).
  /// @param cm The value in centimeters.
  /// @param context The BuildContext.
  /// @return The value in dp.
  /// [PT] Converte centímetros para pixels independentes de densidade (dp).
  /// @param cm O valor em centímetros.
  /// @param context O BuildContext.
  /// @return O valor em dp.
  static double toDpFromCm(double cm, BuildContext context) {
    return toDpFromMm(cm * 10.0, context);
  }

  /// [EN] Converts centimeters to physical pixels (px).
  /// @param cm The value in centimeters.
  /// @param context The BuildContext.
  /// @return The value in pixels.
  /// [PT] Converte centímetros para pixels físicos (px).
  /// @param cm O valor em centímetros.
  /// @param context O BuildContext.
  /// @return O valor em pixels.
  static double toPxFromCm(double cm, BuildContext context) {
    return toPxFromMm(cm * 10.0, context);
  }

  /// [EN] Converts centimeters to scalable pixels (sp).
  /// @param cm The value in centimeters.
  /// @param context The BuildContext.
  /// @return The value in sp.
  /// [PT] Converte centímetros para pixels escaláveis (sp).
  /// @param cm O valor em centímetros.
  /// @param context O BuildContext.
  /// @return O valor em sp.
  static double toSpFromCm(double cm, BuildContext context) {
    return toSpFromMm(cm * 10.0, context);
  }

  // MARK: - Conversion Methods (INCH)

  /// [EN] Converts inches to density-independent pixels (dp).
  /// @param inch The value in inches.
  /// @param context The BuildContext.
  /// @return The value in dp.
  /// [PT] Converte polegadas para pixels independentes de densidade (dp).
  /// @param inch O valor em polegadas.
  /// @param context O BuildContext.
  /// @return O valor em dp.
  static double toDpFromInch(double inch, BuildContext context) {
    return toDpFromMm(inch * mmPerInch, context);
  }

  /// [EN] Converts inches to physical pixels (px).
  /// @param inch The value in inches.
  /// @param context The BuildContext.
  /// @return The value in pixels.
  /// [PT] Converte polegadas para pixels físicos (px).
  /// @param inch O valor em polegadas.
  /// @param context O BuildContext.
  /// @return O valor em pixels.
  static double toPxFromInch(double inch, BuildContext context) {
    return toPxFromMm(inch * mmPerInch, context);
  }

  /// [EN] Converts inches to scalable pixels (sp).
  /// @param inch The value in inches.
  /// @param context The BuildContext.
  /// @return The value in sp.
  /// [PT] Converte polegadas para pixels escaláveis (sp).
  /// @param inch O valor em polegadas.
  /// @param context O BuildContext.
  /// @return O valor em sp.
  static double toSpFromInch(double inch, BuildContext context) {
    return toSpFromMm(inch * mmPerInch, context);
  }

  // MARK: - Utility Methods

  /// [EN] Converts a diameter value in a specific physical unit to radius in dp.
  /// @param diameter The diameter value.
  /// @param unitType The unit type (mm, cm, inch).
  /// @param context The BuildContext.
  /// @return The radius in dp.
  /// [PT] Converte um valor de diâmetro em uma unidade física específica para raio em dp.
  /// @param diameter O valor do diâmetro.
  /// @param unitType O tipo de unidade (mm, cm, inch).
  /// @param context O BuildContext.
  /// @return O raio em dp.
  static double radiusFromDiameter(double diameter, UnitType unitType, BuildContext context) {
    double diameterInDp;
    
    switch (unitType) {
      case UnitType.mm:
        diameterInDp = toDpFromMm(diameter, context);
        break;
      case UnitType.cm:
        diameterInDp = toDpFromCm(diameter, context);
        break;
      case UnitType.inch:
        diameterInDp = toDpFromInch(diameter, context);
        break;
      case UnitType.dp:
        diameterInDp = diameter;
        break;
      case UnitType.sp:
        diameterInDp = diameter;
        break;
      case UnitType.pt:
        diameterInDp = diameter;
        break;
      case UnitType.px:
        final devicePixelRatio = MediaQuery.of(context).devicePixelRatio;
        diameterInDp = diameter / devicePixelRatio;
        break;
    }
    
    return diameterInDp / 2.0;
  }

  /// [EN] Converts a circumference value in a specific physical unit to radius in dp.
  /// @param circumference The circumference value.
  /// @param unitType The unit type (mm, cm, inch).
  /// @param context The BuildContext.
  /// @return The radius in dp.
  /// [PT] Converte um valor de circunferência em uma unidade física específica para raio em dp.
  /// @param circumference O valor da circunferência.
  /// @param unitType O tipo de unidade (mm, cm, inch).
  /// @param context O BuildContext.
  /// @return O raio em dp.
  static double radiusFromCircumference(double circumference, UnitType unitType, BuildContext context) {
    double circumferenceInDp;
    
    switch (unitType) {
      case UnitType.mm:
        circumferenceInDp = toDpFromMm(circumference, context);
        break;
      case UnitType.cm:
        circumferenceInDp = toDpFromCm(circumference, context);
        break;
      case UnitType.inch:
        circumferenceInDp = toDpFromInch(circumference, context);
        break;
      case UnitType.dp:
        circumferenceInDp = circumference;
        break;
      case UnitType.sp:
        circumferenceInDp = circumference;
        break;
      case UnitType.pt:
        circumferenceInDp = circumference;
        break;
      case UnitType.px:
        final devicePixelRatio = MediaQuery.of(context).devicePixelRatio;
        circumferenceInDp = circumference / devicePixelRatio;
        break;
    }
    
    return circumferenceInDp / (2.0 * math.pi);
  }
}

// MARK: - Conversion Extensions

/// [EN] Extensions for double to convert between physical units.
/// [PT] Extensões para double para converter entre unidades físicas.
extension PhysicalUnitConversionsDouble on double {
  /// [EN] Converts MM to CM.
  /// [PT] Converte MM para CM.
  double mmToCm() => this / 10.0;

  /// [EN] Converts MM to Inch.
  /// [PT] Converte MM para Inch.
  double mmToInch() => this / AppDimensPhysicalUnits.mmPerInch;

  /// [EN] Converts CM to MM.
  /// [PT] Converte CM para MM.
  double cmToMm() => this * 10.0;

  /// [EN] Converts CM to Inch.
  /// [PT] Converte CM para Inch.
  double cmToInch() => this / AppDimensPhysicalUnits.cmPerInch;

  /// [EN] Converts Inch to MM.
  /// [PT] Converte Inch para MM.
  double inchToMm() => this * AppDimensPhysicalUnits.mmPerInch;

  /// [EN] Converts Inch to CM.
  /// [PT] Converte Inch para CM.
  double inchToCm() => this * AppDimensPhysicalUnits.cmPerInch;
}

/// [EN] Extensions for int to convert between physical units.
/// [PT] Extensões para int para converter entre unidades físicas.
extension PhysicalUnitConversionsInt on int {
  /// [EN] Converts MM to CM.
  /// [PT] Converte MM para CM.
  double mmToCm() => this.toDouble() / 10.0;

  /// [EN] Converts MM to Inch.
  /// [PT] Converte MM para Inch.
  double mmToInch() => this.toDouble() / AppDimensPhysicalUnits.mmPerInch;

  /// [EN] Converts CM to MM.
  /// [PT] Converte CM para MM.
  double cmToMm() => this.toDouble() * 10.0;

  /// [EN] Converts CM to Inch.
  /// [PT] Converte CM para Inch.
  double cmToInch() => this.toDouble() / AppDimensPhysicalUnits.cmPerInch;

  /// [EN] Converts Inch to MM.
  /// [PT] Converte Inch para MM.
  double inchToMm() => this.toDouble() * AppDimensPhysicalUnits.mmPerInch;

  /// [EN] Converts Inch to CM.
  /// [PT] Converte Inch para CM.
  double inchToCm() => this.toDouble() * AppDimensPhysicalUnits.cmPerInch;
}

/// [EN] Extensions with BuildContext for physical unit conversions to dp/px/sp.
/// [PT] Extensões com BuildContext para conversões de unidades físicas para dp/px/sp.
extension PhysicalUnitContextConversionsDouble on double {
  /// [EN] Converts MM to dp.
  /// [PT] Converte MM para dp.
  double mmToDp(BuildContext context) => AppDimensPhysicalUnits.toDpFromMm(this, context);

  /// [EN] Converts MM to px.
  /// [PT] Converte MM para px.
  double mmToPx(BuildContext context) => AppDimensPhysicalUnits.toPxFromMm(this, context);

  /// [EN] Converts MM to sp.
  /// [PT] Converte MM para sp.
  double mmToSp(BuildContext context) => AppDimensPhysicalUnits.toSpFromMm(this, context);

  /// [EN] Converts CM to dp.
  /// [PT] Converte CM para dp.
  double cmToDp(BuildContext context) => AppDimensPhysicalUnits.toDpFromCm(this, context);

  /// [EN] Converts CM to px.
  /// [PT] Converte CM para px.
  double cmToPx(BuildContext context) => AppDimensPhysicalUnits.toPxFromCm(this, context);

  /// [EN] Converts CM to sp.
  /// [PT] Converte CM para sp.
  double cmToSp(BuildContext context) => AppDimensPhysicalUnits.toSpFromCm(this, context);

  /// [EN] Converts Inch to dp.
  /// [PT] Converte Inch para dp.
  double inchToDp(BuildContext context) => AppDimensPhysicalUnits.toDpFromInch(this, context);

  /// [EN] Converts Inch to px.
  /// [PT] Converte Inch para px.
  double inchToPx(BuildContext context) => AppDimensPhysicalUnits.toPxFromInch(this, context);

  /// [EN] Converts Inch to sp.
  /// [PT] Converte Inch para sp.
  double inchToSp(BuildContext context) => AppDimensPhysicalUnits.toSpFromInch(this, context);
}

/// [EN] Extensions with BuildContext for physical unit conversions to dp/px/sp (Int).
/// [PT] Extensões com BuildContext para conversões de unidades físicas para dp/px/sp (Int).
extension PhysicalUnitContextConversionsInt on int {
  /// [EN] Converts MM to dp.
  /// [PT] Converte MM para dp.
  double mmToDp(BuildContext context) => AppDimensPhysicalUnits.toDpFromMm(this.toDouble(), context);

  /// [EN] Converts MM to px.
  /// [PT] Converte MM para px.
  double mmToPx(BuildContext context) => AppDimensPhysicalUnits.toPxFromMm(this.toDouble(), context);

  /// [EN] Converts MM to sp.
  /// [PT] Converte MM para sp.
  double mmToSp(BuildContext context) => AppDimensPhysicalUnits.toSpFromMm(this.toDouble(), context);

  /// [EN] Converts CM to dp.
  /// [PT] Converte CM para dp.
  double cmToDp(BuildContext context) => AppDimensPhysicalUnits.toDpFromCm(this.toDouble(), context);

  /// [EN] Converts CM to px.
  /// [PT] Converte CM para px.
  double cmToPx(BuildContext context) => AppDimensPhysicalUnits.toPxFromCm(this.toDouble(), context);

  /// [EN] Converts CM to sp.
  /// [PT] Converte CM para sp.
  double cmToSp(BuildContext context) => AppDimensPhysicalUnits.toSpFromCm(this.toDouble(), context);

  /// [EN] Converts Inch to dp.
  /// [PT] Converte Inch para dp.
  double inchToDp(BuildContext context) => AppDimensPhysicalUnits.toDpFromInch(this.toDouble(), context);

  /// [EN] Converts Inch to px.
  /// [PT] Converte Inch para px.
  double inchToPx(BuildContext context) => AppDimensPhysicalUnits.toPxFromInch(this.toDouble(), context);

  /// [EN] Converts Inch to sp.
  /// [PT] Converte Inch para sp.
  double inchToSp(BuildContext context) => AppDimensPhysicalUnits.toSpFromInch(this.toDouble(), context);
}
