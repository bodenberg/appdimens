/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Physical Units
 *
 * Description:
 * Physical units conversion utilities for AppDimens React Native library,
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

import {PixelRatio} from 'react-native';

/**
 * [EN] Unit types for physical conversions.
 * [PT] Tipos de unidade para conversões físicas.
 */
export enum UnitType {
  MM = 'mm',
  CM = 'cm',
  INCH = 'inch',
  DP = 'dp',
  SP = 'sp',
}

/**
 * [EN] Utility class for physical unit conversions.
 * Compatible with Android/iOS API.
 * [PT] Classe utilitária para conversões de unidades físicas.
 * Compatível com API Android/iOS.
 */
export class AppDimensPhysicalUnits {
  // MARK: - Constants

  /**
   * [EN] Points per inch (standard resolution).
   * [PT] Pontos por polegada (resolução padrão).
   */
  private static readonly POINTS_PER_INCH = 72.0;

  /**
   * [EN] Points per centimeter.
   * [PT] Pontos por centímetro.
   */
  private static readonly POINTS_PER_CM = AppDimensPhysicalUnits.POINTS_PER_INCH / 2.54;

  /**
   * [EN] Points per millimeter.
   * [PT] Pontos por milímetro.
   */
  private static readonly POINTS_PER_MM = AppDimensPhysicalUnits.POINTS_PER_CM / 10.0;

  /**
   * [EN] Millimeters per inch.
   * [PT] Milímetros por polegada.
   */
  public static readonly MM_PER_INCH = 25.4;

  /**
   * [EN] Centimeters per inch.
   * [PT] Centímetros por polegada.
   */
  public static readonly CM_PER_INCH = 2.54;

  /**
   * [EN] Default PPI (pixels per inch) for Android devices.
   * [PT] PPI padrão (pixels por polegada) para dispositivos Android.
   */
  private static readonly DEFAULT_PPI = 160.0;

  // MARK: - Conversion Methods (MM)

  /**
   * [EN] Converts millimeters to density-independent points (dp).
   * @param mm The value in millimeters.
   * @return The value in dp.
   * [PT] Converte milímetros para pontos independentes de densidade (dp).
   * @param mm O valor em milímetros.
   * @return O valor em dp.
   */
  public static toDpFromMm(mm: number): number {
    const pixelRatio = PixelRatio.get();
    const ppi = pixelRatio * AppDimensPhysicalUnits.DEFAULT_PPI;
    return (mm / AppDimensPhysicalUnits.MM_PER_INCH) * ppi / pixelRatio;
  }

  /**
   * [EN] Converts millimeters to physical pixels (px).
   * @param mm The value in millimeters.
   * @return The value in pixels.
   * [PT] Converte milímetros para pixels físicos (px).
   * @param mm O valor em milímetros.
   * @return O valor em pixels.
   */
  public static toPxFromMm(mm: number): number {
    const dpValue = AppDimensPhysicalUnits.toDpFromMm(mm);
    const pixelRatio = PixelRatio.get();
    return dpValue * pixelRatio;
  }

  /**
   * [EN] Converts millimeters to scalable pixels (sp).
   * @param mm The value in millimeters.
   * @return The value in sp.
   * [PT] Converte milímetros para pixels escaláveis (sp).
   * @param mm O valor em milímetros.
   * @return O valor em sp.
   */
  public static toSpFromMm(mm: number): number {
    const dpValue = AppDimensPhysicalUnits.toDpFromMm(mm);
    const fontScale = PixelRatio.getFontScale();
    return dpValue * fontScale;
  }

  // MARK: - Conversion Methods (CM)

  /**
   * [EN] Converts centimeters to density-independent points (dp).
   * @param cm The value in centimeters.
   * @return The value in dp.
   * [PT] Converte centímetros para pontos independentes de densidade (dp).
   * @param cm O valor em centímetros.
   * @return O valor em dp.
   */
  public static toDpFromCm(cm: number): number {
    return AppDimensPhysicalUnits.toDpFromMm(cm * 10.0);
  }

  /**
   * [EN] Converts centimeters to physical pixels (px).
   * @param cm The value in centimeters.
   * @return The value in pixels.
   * [PT] Converte centímetros para pixels físicos (px).
   * @param cm O valor em centímetros.
   * @return O valor em pixels.
   */
  public static toPxFromCm(cm: number): number {
    return AppDimensPhysicalUnits.toPxFromMm(cm * 10.0);
  }

  /**
   * [EN] Converts centimeters to scalable pixels (sp).
   * @param cm The value in centimeters.
   * @return The value in sp.
   * [PT] Converte centímetros para pixels escaláveis (sp).
   * @param cm O valor em centímetros.
   * @return O valor em sp.
   */
  public static toSpFromCm(cm: number): number {
    return AppDimensPhysicalUnits.toSpFromMm(cm * 10.0);
  }

  // MARK: - Conversion Methods (INCH)

  /**
   * [EN] Converts inches to density-independent points (dp).
   * @param inch The value in inches.
   * @return The value in dp.
   * [PT] Converte polegadas para pontos independentes de densidade (dp).
   * @param inch O valor em polegadas.
   * @return O valor em dp.
   */
  public static toDpFromInch(inch: number): number {
    return AppDimensPhysicalUnits.toDpFromMm(inch * AppDimensPhysicalUnits.MM_PER_INCH);
  }

  /**
   * [EN] Converts inches to physical pixels (px).
   * @param inch The value in inches.
   * @return The value in pixels.
   * [PT] Converte polegadas para pixels físicos (px).
   * @param inch O valor em polegadas.
   * @return O valor em pixels.
   */
  public static toPxFromInch(inch: number): number {
    return AppDimensPhysicalUnits.toPxFromMm(inch * AppDimensPhysicalUnits.MM_PER_INCH);
  }

  /**
   * [EN] Converts inches to scalable pixels (sp).
   * @param inch The value in inches.
   * @return The value in sp.
   * [PT] Converte polegadas para pixels escaláveis (sp).
   * @param inch O valor em polegadas.
   * @return O valor em sp.
   */
  public static toSpFromInch(inch: number): number {
    return AppDimensPhysicalUnits.toSpFromMm(inch * AppDimensPhysicalUnits.MM_PER_INCH);
  }

  // MARK: - Utility Methods

  /**
   * [EN] Converts a diameter value in a specific physical unit to radius in dp.
   * @param diameter The diameter value.
   * @param unitType The unit type (mm, cm, inch).
   * @return The radius in dp.
   * [PT] Converte um valor de diâmetro em uma unidade física específica para raio em dp.
   * @param diameter O valor do diâmetro.
   * @param unitType O tipo de unidade (mm, cm, inch).
   * @return O raio em dp.
   */
  public static radiusFromDiameter(diameter: number, unitType: UnitType): number {
    let diameterInDp: number;

    switch (unitType) {
      case UnitType.MM:
        diameterInDp = AppDimensPhysicalUnits.toDpFromMm(diameter);
        break;
      case UnitType.CM:
        diameterInDp = AppDimensPhysicalUnits.toDpFromCm(diameter);
        break;
      case UnitType.INCH:
        diameterInDp = AppDimensPhysicalUnits.toDpFromInch(diameter);
        break;
      case UnitType.DP:
      case UnitType.SP:
        diameterInDp = diameter;
        break;
    }

    return diameterInDp / 2.0;
  }

  /**
   * [EN] Converts a circumference value in a specific physical unit to radius in dp.
   * @param circumference The circumference value.
   * @param unitType The unit type (mm, cm, inch).
   * @return The radius in dp.
   * [PT] Converte um valor de circunferência em uma unidade física específica para raio em dp.
   * @param circumference O valor da circunferência.
   * @param unitType O tipo de unidade (mm, cm, inch).
   * @return O raio em dp.
   */
  public static radiusFromCircumference(circumference: number, unitType: UnitType): number {
    let circumferenceInDp: number;

    switch (unitType) {
      case UnitType.MM:
        circumferenceInDp = AppDimensPhysicalUnits.toDpFromMm(circumference);
        break;
      case UnitType.CM:
        circumferenceInDp = AppDimensPhysicalUnits.toDpFromCm(circumference);
        break;
      case UnitType.INCH:
        circumferenceInDp = AppDimensPhysicalUnits.toDpFromInch(circumference);
        break;
      case UnitType.DP:
      case UnitType.SP:
        circumferenceInDp = circumference;
        break;
    }

    return circumferenceInDp / (2.0 * Math.PI);
  }

  // MARK: - Conversion Between Physical Units

  /**
   * [EN] Converts MM to CM.
   * [PT] Converte MM para CM.
   */
  public static mmToCm(mm: number): number {
    return mm / 10.0;
  }

  /**
   * [EN] Converts MM to Inch.
   * [PT] Converte MM para Inch.
   */
  public static mmToInch(mm: number): number {
    return mm / AppDimensPhysicalUnits.MM_PER_INCH;
  }

  /**
   * [EN] Converts CM to MM.
   * [PT] Converte CM para MM.
   */
  public static cmToMm(cm: number): number {
    return cm * 10.0;
  }

  /**
   * [EN] Converts CM to Inch.
   * [PT] Converte CM para Inch.
   */
  public static cmToInch(cm: number): number {
    return cm / AppDimensPhysicalUnits.CM_PER_INCH;
  }

  /**
   * [EN] Converts Inch to MM.
   * [PT] Converte Inch para MM.
   */
  public static inchToMm(inch: number): number {
    return inch * AppDimensPhysicalUnits.MM_PER_INCH;
  }

  /**
   * [EN] Converts Inch to CM.
   * [PT] Converte Inch para CM.
   */
  public static inchToCm(inch: number): number {
    return inch * AppDimensPhysicalUnits.CM_PER_INCH;
  }
}

// MARK: - Extensions/Helper Functions

/**
 * [EN] Helper functions for physical unit conversions.
 * Can be used as extensions-style helpers.
 * [PT] Funções auxiliares para conversões de unidades físicas.
 * Podem ser usadas como helpers estilo extensões.
 */
export const PhysicalUnitConversions = {
  /**
   * [EN] Converts MM to CM.
   * [PT] Converte MM para CM.
   */
  mmToCm: (mm: number) => AppDimensPhysicalUnits.mmToCm(mm),

  /**
   * [EN] Converts MM to Inch.
   * [PT] Converte MM para Inch.
   */
  mmToInch: (mm: number) => AppDimensPhysicalUnits.mmToInch(mm),

  /**
   * [EN] Converts CM to MM.
   * [PT] Converte CM para MM.
   */
  cmToMm: (cm: number) => AppDimensPhysicalUnits.cmToMm(cm),

  /**
   * [EN] Converts CM to Inch.
   * [PT] Converte CM para Inch.
   */
  cmToInch: (cm: number) => AppDimensPhysicalUnits.cmToInch(cm),

  /**
   * [EN] Converts Inch to MM.
   * [PT] Converte Inch para MM.
   */
  inchToMm: (inch: number) => AppDimensPhysicalUnits.inchToMm(inch),

  /**
   * [EN] Converts Inch to CM.
   * [PT] Converte Inch para CM.
   */
  inchToCm: (inch: number) => AppDimensPhysicalUnits.inchToCm(inch),

  /**
   * [EN] Converts MM to dp.
   * [PT] Converte MM para dp.
   */
  mmToDp: (mm: number) => AppDimensPhysicalUnits.toDpFromMm(mm),

  /**
   * [EN] Converts MM to px.
   * [PT] Converte MM para px.
   */
  mmToPx: (mm: number) => AppDimensPhysicalUnits.toPxFromMm(mm),

  /**
   * [EN] Converts MM to sp.
   * [PT] Converte MM para sp.
   */
  mmToSp: (mm: number) => AppDimensPhysicalUnits.toSpFromMm(mm),

  /**
   * [EN] Converts CM to dp.
   * [PT] Converte CM para dp.
   */
  cmToDp: (cm: number) => AppDimensPhysicalUnits.toDpFromCm(cm),

  /**
   * [EN] Converts CM to px.
   * [PT] Converte CM para px.
   */
  cmToPx: (cm: number) => AppDimensPhysicalUnits.toPxFromCm(cm),

  /**
   * [EN] Converts CM to sp.
   * [PT] Converte CM para sp.
   */
  cmToSp: (cm: number) => AppDimensPhysicalUnits.toSpFromCm(cm),

  /**
   * [EN] Converts Inch to dp.
   * [PT] Converte Inch para dp.
   */
  inchToDp: (inch: number) => AppDimensPhysicalUnits.toDpFromInch(inch),

  /**
   * [EN] Converts Inch to px.
   * [PT] Converte Inch para px.
   */
  inchToPx: (inch: number) => AppDimensPhysicalUnits.toPxFromInch(inch),

  /**
   * [EN] Converts Inch to sp.
   * [PT] Converte Inch para sp.
   */
  inchToSp: (inch: number) => AppDimensPhysicalUnits.toSpFromInch(inch),
};

