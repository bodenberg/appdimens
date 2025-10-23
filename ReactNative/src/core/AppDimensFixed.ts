/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Fixed Scaling
 *
 * Description:
 * Fixed dimension scaling with logarithmic adjustment for refined scaling.
 * Ideal for UI elements that should maintain visual consistency.
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

import {
  DimensionConfig,
  AppDimensBuilder,
  UiModeType,
  DeviceType,
  ScreenQualifier,
  ScreenQualifierEntry,
  UiModeQualifierEntry,
  BASE_DP_FACTOR,
  BASE_INCREMENT,
  REFERENCE_AR,
} from '../types';
import {AppDimensAdjustmentFactors} from './AppDimensAdjustmentFactors';

/**
 * Fixed dimension builder with logarithmic adjustment
 */
export class AppDimensFixed implements AppDimensBuilder {
  private config: DimensionConfig;
  private baseValueCache = new Map<string, number>();
  private calculatedValueCache = new Map<string, number>();

  constructor(
    initialValue: number,
    ignoreMultiWindowAdjustment: boolean = false,
  ) {
    this.config = {
      initialValue,
      screenType: 'lowest',
      applyAspectRatioAdjustment: true,
      ignoreMultiWindowAdjustment,
      customScreenQualifierMap: new Map(),
      customUiModeMap: new Map(),
      customIntersectionMap: new Map(),
    };
  }

  /**
   * [EN] Set custom value for UI mode.
   * Compatible with Android/iOS API.
   * [PT] Define valor customizado para modo UI.
   * Compatível com API Android/iOS.
   */
  screen(uiModeType: UiModeType, customValue: number): this;
  
  /**
   * [EN] Set custom value for device type and screen size.
   * Compatible with Android/iOS API.
   * [PT] Define valor customizado para tipo de dispositivo e tamanho de tela.
   * Compatível com API Android/iOS.
   */
  screen(deviceType: DeviceType, screenSize: number, customValue: number): this;
  
  /**
   * [EN] Set custom value for screen qualifier with specific value.
   * Compatible with Android/iOS API.
   * [PT] Define valor customizado para qualificador de tela com valor específico.
   * Compatível com API Android/iOS.
   */
  screen(
    qualifierType: ScreenQualifier,
    qualifierValue: number,
    customValue: number
  ): this;
  
  /**
   * [EN] Set custom value for intersection of UI mode and screen qualifier.
   * Compatible with Android/iOS API.
   * [PT] Define valor customizado para interseção de modo UI e qualificador de tela.
   * Compatível com API Android/iOS.
   */
  screen(
    uiModeType: UiModeType,
    qualifierType: ScreenQualifier,
    qualifierValue: number,
    customValue: number
  ): this;

  // Implementation
  screen(
    arg1: UiModeType | DeviceType | ScreenQualifier,
    arg2: number | ScreenQualifier,
    arg3?: number,
    arg4?: number
  ): this {
    if (arg3 === undefined && arg4 === undefined) {
      // screen(uiModeType, customValue)
      const uiModeType = arg1 as UiModeType;
      const customValue = arg2 as number;
      this.config.customUiModeMap.set(uiModeType, customValue);
    } else if (arg4 === undefined && arg3 !== undefined) {
      // Could be: screen(deviceType, screenSize, customValue) or
      //           screen(qualifierType, qualifierValue, customValue)
      const isDeviceType = ['phone', 'tablet', 'desktop', 'watch', 'tv', 'car'].includes(arg1);
      
      if (isDeviceType) {
        // screen(deviceType, screenSize, customValue)
        const deviceType = arg1 as DeviceType;
        const screenSize = arg2 as number;
        const customValue = arg3;
        const screenQualifier: ScreenQualifierEntry = {
          type: 'smallWidth',
          value: screenSize,
          deviceType,
        };
        this.config.customScreenQualifierMap.set(screenQualifier, customValue);
      } else {
        // screen(qualifierType, qualifierValue, customValue)
        const qualifierType = arg1 as ScreenQualifier;
        const qualifierValue = arg2 as number;
        const customValue = arg3;
        const screenQualifier: ScreenQualifierEntry = {
          type: qualifierType,
          value: qualifierValue,
        };
        this.config.customScreenQualifierMap.set(screenQualifier, customValue);
      }
    } else if (arg4 !== undefined && arg3 !== undefined) {
      // screen(uiModeType, qualifierType, qualifierValue, customValue)
      const uiModeType = arg1 as UiModeType;
      const qualifierType = arg2 as ScreenQualifier;
      const qualifierValue = arg3;
      const customValue = arg4;
      const screenQualifier: ScreenQualifierEntry = {
        type: qualifierType,
        value: qualifierValue,
        uiMode: uiModeType,
      };
      const intersectionEntry: UiModeQualifierEntry = {
        uiModeType,
        screenQualifierEntry: screenQualifier,
      };
      this.config.customIntersectionMap.set(intersectionEntry, customValue);
    }
    this.invalidateCache();
    return this;
  }

  /**
   * Set screen dimension type
   */
  type(type: 'lowest' | 'highest'): this {
    this.config.screenType = type;
    this.invalidateCache();
    return this;
  }

  /**
   * Set multi-window adjustment behavior
   */
  multiWindowAdjustment(ignore: boolean = true): this {
    this.config.ignoreMultiWindowAdjustment = ignore;
    this.invalidateCache();
    return this;
  }

  /**
   * Enable/disable aspect ratio adjustment
   */
  aspectRatio(enable: boolean = true, sensitivity?: number): this {
    this.config.applyAspectRatioAdjustment = enable;
    this.config.customSensitivityK = sensitivity;
    this.invalidateCache();
    return this;
  }

  /**
   * Enable/disable cache
   */
  cache(enable: boolean = true): this {
    if (!enable) {
      this.invalidateCache();
    }
    return this;
  }

  /**
   * Calculate adjusted value in pixels
   */
  toPixels(): number {
    const adjustedValue = this.calculateAdjustedValue();
    return AppDimensAdjustmentFactors.pointsToPixels(adjustedValue);
  }

  /**
   * Calculate adjusted value in pixels (integer)
   */
  toPixelsInt(): number {
    return Math.round(this.toPixels());
  }

  /**
   * Calculate adjusted value in points
   */
  toPoints(): number {
    return this.calculateAdjustedValue();
  }

  /**
   * Calculate adjusted value in points (integer)
   */
  toPointsInt(): number {
    return Math.round(this.calculateAdjustedValue());
  }

  /**
   * [EN] Calculate adjusted value in scalable pixels (sp) for text.
   * This respects the system font scale setting.
   * Compatible with Android/iOS API.
   * [PT] Calcula o valor ajustado em pixels escaláveis (sp) para texto.
   * Isso respeita a configuração de escala de fonte do sistema.
   * Compatível com API Android/iOS.
   */
  toSp(): number {
    const adjustedValue = this.calculateAdjustedValue();
    const fontScale = AppDimensAdjustmentFactors.getFontScale();
    return adjustedValue * fontScale;
  }

  /**
   * [EN] Calculate adjusted value in scalable pixels (sp) as integer.
   * Compatible with Android/iOS API.
   * [PT] Calcula o valor ajustado em pixels escaláveis (sp) como inteiro.
   * Compatível com API Android/iOS.
   */
  toSpInt(): number {
    return Math.round(this.toSp());
  }

  /**
   * [EN] Calculate adjusted value in em units (ignoring font scale).
   * This is similar to sp but without the font scale factor applied.
   * Compatible with Android/iOS API.
   * [PT] Calcula o valor ajustado em unidades em (ignorando escala de fonte).
   * Isso é similar ao sp mas sem o fator de escala de fonte aplicado.
   * Compatível com API Android/iOS.
   */
  toEm(): number {
    return this.calculateAdjustedValue();
  }

  /**
   * [EN] Calculate adjusted value in em units as integer.
   * Compatible with Android/iOS API.
   * [PT] Calcula o valor ajustado em unidades em como inteiro.
   * Compatível com API Android/iOS.
   */
  toEmInt(): number {
    return Math.round(this.toEm());
  }

  /**
   * Resolve final base value using customization logic
   */
  private resolveFinalBaseValue(): number {
    const screenDimensions =
      AppDimensAdjustmentFactors.getCurrentScreenDimensions();
    const deviceInfo = AppDimensAdjustmentFactors.getCurrentDeviceInfo();
    const {width, height} = screenDimensions;
    const currentScreenSize = Math.min(width, height);

    // Generate cache key
    const cacheKey = `${deviceInfo.type}_${deviceInfo.platform}_${width}_${height}_${this.config.initialValue}_${this.config.customIntersectionMap.size}_${this.config.customUiModeMap.size}_${this.config.customScreenQualifierMap.size}`;

    // Check cache first
    if (this.baseValueCache.has(cacheKey)) {
      return this.baseValueCache.get(cacheKey)!;
    }

    let valueToAdjust = this.config.initialValue;

    // Priority 1: Intersection (UiMode + ScreenQualifier)
    const sortedIntersectionQualifiers = Array.from(
      this.config.customIntersectionMap.keys(),
    ).sort(
      (a, b) => b.screenQualifierEntry.value - a.screenQualifierEntry.value,
    );

    for (const key of sortedIntersectionQualifiers) {
      if (
        key.uiModeType === this.getCurrentUiMode() &&
        AppDimensAdjustmentFactors.resolveIntersectionCondition(
          key.screenQualifierEntry,
          currentScreenSize,
          width,
          height,
        )
      ) {
        valueToAdjust =
          this.config.customIntersectionMap.get(key) ||
          this.config.initialValue;
        break;
      }
    }

    // Priority 2: UI Mode
    if (valueToAdjust === this.config.initialValue) {
      const uiModeValue = this.config.customUiModeMap.get(
        this.getCurrentUiMode(),
      );
      if (uiModeValue !== undefined) {
        valueToAdjust = uiModeValue;
      }
    }

    // Priority 3: Device Type / Screen Qualifier
    if (valueToAdjust === this.config.initialValue) {
      valueToAdjust = AppDimensAdjustmentFactors.resolveQualifierValue(
        this.config.customScreenQualifierMap,
        deviceInfo.type,
        currentScreenSize,
        this.config.initialValue,
      );
    }

    // Cache the result
    this.baseValueCache.set(cacheKey, valueToAdjust);

    return valueToAdjust;
  }

  /**
   * Calculate final adjusted value
   */
  private calculateAdjustedValue(): number {
    const screenDimensions =
      AppDimensAdjustmentFactors.getCurrentScreenDimensions();
    const {width, height} = screenDimensions;

    // Generate cache key for final calculation
    const cacheKey = `${this.config.screenType}_${
      this.config.ignoreMultiWindowAdjustment
    }_${this.config.applyAspectRatioAdjustment}_${
      this.config.customSensitivityK || 0
    }_${width}_${height}_${this.config.initialValue}_${
      this.config.customIntersectionMap.size
    }_${this.config.customUiModeMap.size}_${
      this.config.customScreenQualifierMap.size
    }`;

    // Check cache first
    if (this.calculatedValueCache.has(cacheKey)) {
      return this.calculatedValueCache.get(cacheKey)!;
    }

    const valueToAdjust = this.resolveFinalBaseValue();
    const adjustmentFactors =
      AppDimensAdjustmentFactors.calculateAdjustmentFactors();

    const shouldIgnoreAdjustment =
      this.config.ignoreMultiWindowAdjustment &&
      AppDimensAdjustmentFactors.isMultiWindowMode();

    let finalAdjustmentFactor: number;

    if (shouldIgnoreAdjustment) {
      finalAdjustmentFactor = BASE_DP_FACTOR;
    } else if (this.config.applyAspectRatioAdjustment) {
      const selectedFactor =
        this.config.screenType === 'highest'
          ? adjustmentFactors.withArFactorHighest
          : adjustmentFactors.withArFactorLowest;

      if (this.config.customSensitivityK !== undefined) {
        const adjustmentFactorBase =
          this.config.screenType === 'highest'
            ? adjustmentFactors.adjustmentFactorHighest
            : adjustmentFactors.adjustmentFactorLowest;

        const ar = AppDimensAdjustmentFactors.getReferenceAspectRatio(
          width,
          height,
        );
        const continuousAdjustment =
          this.config.customSensitivityK * Math.log(ar / REFERENCE_AR);
        const finalIncrementValue = BASE_INCREMENT + continuousAdjustment;

        finalAdjustmentFactor =
          BASE_DP_FACTOR + adjustmentFactorBase * finalIncrementValue;
      } else {
        finalAdjustmentFactor = selectedFactor;
      }
    } else {
      finalAdjustmentFactor = adjustmentFactors.withoutArFactor;
    }

    const finalValue = valueToAdjust * finalAdjustmentFactor;

    // Cache the result
    this.calculatedValueCache.set(cacheKey, finalValue);

    return finalValue;
  }

  /**
   * Get current UI mode
   */
  private getCurrentUiMode(): UiModeType {
    // In React Native, we can't easily detect UI mode
    // This is a placeholder for future implementation
    return 'normal';
  }

  /**
   * Invalidate cache
   */
  private invalidateCache(): void {
    this.baseValueCache.clear();
    this.calculatedValueCache.clear();
  }
}
