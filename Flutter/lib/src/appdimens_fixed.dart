/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Fixed Dimension Builder
 *
 * Description:
 * Fixed dimension builder that provides logarithmic scaling for UI elements like buttons,
 * paddings, margins, and icons, ensuring visual consistency across different screen sizes.
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
import 'appdimens_utils.dart';
import 'models/base_orientation.dart';
import 'utils/orientation_resolver.dart';

/// [EN] Fixed dimension builder that provides logarithmic scaling for UI elements.
/// [PT] Construtor de dimensão fixa que fornece escalonamento logarítmico para elementos de UI.
class AppDimensFixed {
  final double _initialValue;
  final bool _ignoreMultiWindowAdjustment;
  BaseOrientation _baseOrientation = BaseOrientation.auto;
  ScreenType _screenType = ScreenType.lowest;
  final Map<String, double> _uiModeValues = {};
  final Map<String, double> _dpQualifierValues = {};
  final Map<String, double> _deviceTypeValues = {};
  final Map<String, double> _screenQualifierValues = {};
  final Map<String, double> _intersectionValues = {};
  final Map<String, double> _cache = {};
  bool _cacheEnabled = true;

  /// [EN] Creates a new AppDimensFixed instance.
  /// @param initialValue The initial base value.
  /// @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
  /// [PT] Cria uma nova instância AppDimensFixed.
  /// @param initialValue O valor base inicial.
  /// @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
  AppDimensFixed(
    this._initialValue, {
    bool ignoreMultiWindowAdjustment = false,
  }) : _ignoreMultiWindowAdjustment = ignoreMultiWindowAdjustment;

  // MARK: - Custom Value Methods (Standardized API - Compatible with Android/iOS)

  /// [EN] Sets a custom dimension value for a specific UI mode.
  /// Compatible with Android/iOS API.
  /// @param uiModeType The UI mode type.
  /// @param customValue The custom dimension value.
  /// @return The AppDimensFixed instance for chaining.
  /// [PT] Define um valor de dimensão customizado para um modo de UI específico.
  /// Compatível com API Android/iOS.
  /// @param uiModeType O tipo de modo de UI.
  /// @param customValue O valor de dimensão customizado.
  /// @return A instância AppDimensFixed para encadeamento.
  AppDimensFixed screen(UiModeType uiModeType, double customValue) {
    _uiModeValues[uiModeType.name] = customValue;
    return this;
  }

  /// [EN] Sets a custom dimension value for a specific device type and screen size.
  /// Compatible with Android/iOS API.
  /// @param deviceType The device type.
  /// @param screenSize The minimum screen size (in dp).
  /// @param customValue The custom dimension value.
  /// @return The AppDimensFixed instance for chaining.
  /// [PT] Define um valor de dimensão customizado para um tipo de dispositivo e tamanho de tela.
  /// Compatível com API Android/iOS.
  /// @param deviceType O tipo de dispositivo.
  /// @param screenSize O tamanho mínimo de tela (em dp).
  /// @param customValue O valor de dimensão customizado.
  /// @return A instância AppDimensFixed para encadeamento.
  AppDimensFixed screenWithSize(
      DeviceType deviceType, double screenSize, double customValue) {
    final key = '${deviceType.name}_$screenSize';
    _screenQualifierValues[key] = customValue;
    return this;
  }

  /// [EN] Sets a custom dimension value for a specific DP qualifier.
  /// Compatible with Android/iOS API.
  /// @param dpQualifier The DP qualifier.
  /// @param qualifierValue The qualifier value (e.g., 600 for sw600dp).
  /// @param customValue The custom dimension value.
  /// @return The AppDimensFixed instance for chaining.
  /// [PT] Define um valor de dimensão customizado para um qualificador DP específico.
  /// Compatível com API Android/iOS.
  /// @param dpQualifier O qualificador DP.
  /// @param qualifierValue O valor do qualificador (ex: 600 para sw600dp).
  /// @param customValue O valor de dimensão customizado.
  /// @return A instância AppDimensFixed para encadeamento.
  AppDimensFixed screenWithQualifier(
      DpQualifier dpQualifier, int qualifierValue, double customValue) {
    final key = '${dpQualifier.name}_$qualifierValue';
    _dpQualifierValues[key] = customValue;
    return this;
  }

  /// [EN] Sets a custom dimension value for the intersection of UI mode and DP qualifier.
  /// Compatible with Android/iOS API.
  /// @param uiModeType The UI mode type.
  /// @param dpQualifier The DP qualifier.
  /// @param qualifierValue The qualifier value (e.g., 600 for sw600dp).
  /// @param customValue The custom dimension value.
  /// @return The AppDimensFixed instance for chaining.
  /// [PT] Define um valor de dimensão customizado para a interseção de modo UI e qualificador DP.
  /// Compatível com API Android/iOS.
  /// @param uiModeType O tipo de modo de UI.
  /// @param dpQualifier O qualificador DP.
  /// @param qualifierValue O valor do qualificador (ex: 600 para sw600dp).
  /// @param customValue O valor de dimensão customizado.
  /// @return A instância AppDimensFixed para encadeamento.
  AppDimensFixed screenWithIntersection(
    UiModeType uiModeType,
    DpQualifier dpQualifier,
    int qualifierValue,
    double customValue,
  ) {
    final key = '${uiModeType.name}_${dpQualifier.name}_$qualifierValue';
    _intersectionValues[key] = customValue;
    return this;
  }

  // MARK: - Base Orientation Methods

  /// [EN] Sets the base orientation for which the design was originally created.
  /// [PT] Define a orientação base para a qual o design foi originalmente criado.
  AppDimensFixed baseOrientation(BaseOrientation orientation) {
    _baseOrientation = orientation;
    return this;
  }

  /// [EN] Sets screen type (lowest or highest).
  /// [PT] Define o tipo de tela (lowest ou highest).
  AppDimensFixed screenType(ScreenType type) {
    _screenType = type;
    return this;
  }

  /// [EN] Shorthand for portrait design using lowest dimension (width in portrait).
  /// [PT] Atalho para design portrait usando menor dimensão (largura em portrait).
  AppDimensFixed portraitLowest() {
    _baseOrientation = BaseOrientation.portrait;
    _screenType = ScreenType.lowest;
    return this;
  }

  /// [EN] Shorthand for portrait design using highest dimension (height in portrait).
  /// [PT] Atalho para design portrait usando maior dimensão (altura em portrait).
  AppDimensFixed portraitHighest() {
    _baseOrientation = BaseOrientation.portrait;
    _screenType = ScreenType.highest;
    return this;
  }

  /// [EN] Shorthand for landscape design using lowest dimension (height in landscape).
  /// [PT] Atalho para design landscape usando menor dimensão (altura em landscape).
  AppDimensFixed landscapeLowest() {
    _baseOrientation = BaseOrientation.landscape;
    _screenType = ScreenType.lowest;
    return this;
  }

  /// [EN] Shorthand for landscape design using highest dimension (width in landscape).
  /// [PT] Atalho para design landscape usando maior dimensão (largura em landscape).
  AppDimensFixed landscapeHighest() {
    _baseOrientation = BaseOrientation.landscape;
    _screenType = ScreenType.highest;
    return this;
  }

  // MARK: - Deprecated Methods (Kept for backward compatibility)

  /// [DEPRECATED] Use screen() instead.
  /// [EN] Sets a custom dimension value for a specific UI mode.
  /// @deprecated Use screen(UiModeType, double) instead for consistency with Android/iOS.
  @deprecated
  AppDimensFixed uiMode(UiModeType uiMode, double value) {
    return screen(uiMode, value);
  }

  /// [DEPRECATED] Use screenWithQualifier() instead.
  /// [EN] Sets a custom dimension value for a specific DP qualifier.
  /// @deprecated Use screenWithQualifier(DpQualifier, int, double) instead for consistency with Android/iOS.
  @deprecated
  AppDimensFixed dpQualifier(DpQualifier qualifier, double value) {
    _dpQualifierValues[qualifier.name] = value;
    return this;
  }

  /// [DEPRECATED] Use screen() or screenWithSize() instead.
  /// [EN] Sets a custom dimension value for a specific device type.
  /// @deprecated Use screen(DeviceType, double) or screenWithSize(DeviceType, double, double) instead.
  @deprecated
  AppDimensFixed deviceType(DeviceType deviceType, double value) {
    _deviceTypeValues[deviceType.name] = value;
    return this;
  }

  /// [DEPRECATED] Use screenWithQualifier() instead.
  /// [EN] Sets a custom dimension value for a specific screen qualifier.
  /// @deprecated Use screenWithQualifier() instead for consistency with Android/iOS.
  @deprecated
  AppDimensFixed screenQualifier(ScreenQualifier qualifier, double value) {
    _screenQualifierValues[qualifier.name] = value;
    return this;
  }

  /// [DEPRECATED] Use screenWithIntersection() instead.
  /// [EN] Sets a custom dimension value for a specific intersection of qualifiers.
  /// @deprecated Use screenWithIntersection() instead for consistency with Android/iOS.
  @deprecated
  AppDimensFixed intersection(
      UiModeType uiMode, DpQualifier dpQualifier, double value) {
    return screenWithIntersection(uiMode, dpQualifier, 0, value);
  }

  // MARK: - Cache Control Methods

  /// [EN] Enables or disables caching for this instance.
  /// @param enabled If true, enables caching; if false, disables and clears cache.
  /// @return The AppDimensFixed instance for chaining.
  /// [PT] Ativa ou desativa o cache para esta instância.
  /// @param enabled Se verdadeiro, ativa o cache; se falso, desativa e limpa o cache.
  /// @return A instância AppDimensFixed para encadeamento.
  AppDimensFixed cache(bool enabled) {
    _cacheEnabled = enabled;
    if (!enabled) {
      _cache.clear();
    }
    return this;
  }

  /// [EN] Clears the cache for this instance.
  /// @return The AppDimensFixed instance for chaining.
  /// [PT] Limpa o cache para esta instância.
  /// @return A instância AppDimensFixed para encadeamento.
  AppDimensFixed clearCache() {
    _cache.clear();
    return this;
  }

  // MARK: - Calculation Methods

  /// [EN] Calculates the final dimension value for the given context.
  /// @param context The BuildContext.
  /// @return The calculated dimension value.
  /// [PT] Calcula o valor final da dimensão para o contexto dado.
  /// @param context O BuildContext.
  /// @return O valor da dimensão calculado.
  double calculate(BuildContext context) {
    final cacheKey = _generateCacheKey(context);

    if (_cacheEnabled && _cache.containsKey(cacheKey)) {
      return _cache[cacheKey]!;
    }

    final value = _calculateValue(context);

    if (_cacheEnabled) {
      _cache[cacheKey] = value;
    }

    return value;
  }

  /// [EN] Calculates the final dimension value without caching.
  /// @param context The BuildContext.
  /// @return The calculated dimension value.
  /// [PT] Calcula o valor final da dimensão sem cache.
  /// @param context O BuildContext.
  /// @return O valor da dimensão calculado.
  double calculateWithoutCache(BuildContext context) {
    return _calculateValue(context);
  }

  // MARK: - Conversion Methods (Compatible with Android/iOS)

  /// [EN] Returns the adjusted value in density-independent pixels (dp).
  /// @param context The BuildContext.
  /// @return The value in dp as a double.
  /// [PT] Retorna o valor ajustado em pixels independentes de densidade (dp).
  /// @param context O BuildContext.
  /// @return O valor em dp como double.
  double toDp(BuildContext context) {
    return calculate(context);
  }

  /// [EN] Returns the adjusted value in density-independent pixels (dp) as an integer.
  /// @param context The BuildContext.
  /// @return The value in dp as an int.
  /// [PT] Retorna o valor ajustado em pixels independentes de densidade (dp) como inteiro.
  /// @param context O BuildContext.
  /// @return O valor em dp como int.
  int toDpInt(BuildContext context) {
    return calculate(context).round();
  }

  /// [EN] Returns the adjusted value in physical pixels (px).
  /// @param context The BuildContext.
  /// @return The value in pixels as a double.
  /// [PT] Retorna o valor ajustado em pixels físicos (px).
  /// @param context O BuildContext.
  /// @return O valor em pixels como double.
  double toPx(BuildContext context) {
    final dpValue = calculate(context);
    final devicePixelRatio = MediaQuery.of(context).devicePixelRatio;
    return dpValue * devicePixelRatio;
  }

  /// [EN] Returns the adjusted value in physical pixels (px) as an integer.
  /// @param context The BuildContext.
  /// @return The value in pixels as an int.
  /// [PT] Retorna o valor ajustado em pixels físicos (px) como inteiro.
  /// @param context O BuildContext.
  /// @return O valor em pixels como int.
  int toPxInt(BuildContext context) {
    return toPx(context).round();
  }

  /// [EN] Returns the adjusted value in scalable pixels (sp) for text.
  /// This respects the system font scale setting.
  /// @param context The BuildContext.
  /// @return The value in sp as a double.
  /// [PT] Retorna o valor ajustado em pixels escaláveis (sp) para texto.
  /// Isso respeita a configuração de escala de fonte do sistema.
  /// @param context O BuildContext.
  /// @return O valor em sp como double.
  double toSp(BuildContext context) {
    final dpValue = calculate(context);
    final textScaleFactor = MediaQuery.of(context).textScaleFactor;
    return dpValue * textScaleFactor;
  }

  /// [EN] Returns the adjusted value in scalable pixels (sp) as an integer.
  /// @param context The BuildContext.
  /// @return The value in sp as an int.
  /// [PT] Retorna o valor ajustado em pixels escaláveis (sp) como inteiro.
  /// @param context O BuildContext.
  /// @return O valor em sp como int.
  int toSpInt(BuildContext context) {
    return toSp(context).round();
  }

  /// [EN] Returns the adjusted value in em units (ignoring font scale).
  /// This is similar to sp but without the font scale factor applied.
  /// @param context The BuildContext.
  /// @return The value in em as a double.
  /// [PT] Retorna o valor ajustado em unidades em (ignorando escala de fonte).
  /// Isso é similar ao sp mas sem o fator de escala de fonte aplicado.
  /// @param context O BuildContext.
  /// @return O valor em em como double.
  double toEm(BuildContext context) {
    return calculate(context);
  }

  /// [EN] Returns the adjusted value in em units as an integer.
  /// @param context The BuildContext.
  /// @return The value in em as an int.
  /// [PT] Retorna o valor ajustado em unidades em como inteiro.
  /// @param context O BuildContext.
  /// @return O valor em em como int.
  int toEmInt(BuildContext context) {
    return toEm(context).round();
  }

  // MARK: - Private Methods

  /// [EN] Generates a cache key for the current context.
  /// @param context The BuildContext.
  /// @return The cache key.
  /// [PT] Gera uma chave de cache para o contexto atual.
  /// @param context O BuildContext.
  /// @return A chave de cache.
  String _generateCacheKey(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    final devicePixelRatio = mediaQuery.devicePixelRatio;
    final orientation = mediaQuery.orientation;

    final deviceType = DeviceType.current(context);
    final uiModeType = UiModeType.current(context);

    return '${size.width}_${size.height}_${devicePixelRatio}_${orientation.name}_${deviceType.name}_${uiModeType.name}_${_ignoreMultiWindowAdjustment}';
  }

  /// [EN] Calculates the actual dimension value.
  /// @param context The BuildContext.
  /// @return The calculated value.
  /// [PT] Calcula o valor real da dimensão.
  /// @param context O BuildContext.
  /// @return O valor calculado.
  double _calculateValue(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;

    final deviceType = DeviceType.current(context);
    final uiModeType = UiModeType.current(context);

    // Check for custom values in order of precedence
    final intersectionKey = '${uiModeType.name}_${_getDpQualifier(size)}';
    if (_intersectionValues.containsKey(intersectionKey)) {
      return _intersectionValues[intersectionKey]!;
    }

    if (_uiModeValues.containsKey(uiModeType.name)) {
      return _uiModeValues[uiModeType.name]!;
    }

    if (_deviceTypeValues.containsKey(deviceType.name)) {
      return _deviceTypeValues[deviceType.name]!;
    }

    final dpQualifier = _getDpQualifier(size);
    if (_dpQualifierValues.containsKey(dpQualifier.name)) {
      return _dpQualifierValues[dpQualifier.name]!;
    }

    final screenQualifier = _getScreenQualifier(size);
    if (_screenQualifierValues.containsKey(screenQualifier.name)) {
      return _screenQualifierValues[screenQualifier.name]!;
    }

    // Apply fixed scaling calculation
    return _applyFixedScaling(context, _initialValue);
  }

  /// [EN] Applies fixed scaling calculation to the base value.
  /// @param context The BuildContext.
  /// @param baseValue The base value to scale.
  /// @return The scaled value.
  /// [PT] Aplica o cálculo de escalonamento fixo ao valor base.
  /// @param context O BuildContext.
  /// @param baseValue O valor base para escalonar.
  /// @return O valor escalonado.
  double _applyFixedScaling(BuildContext context, double baseValue) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    final devicePixelRatio = mediaQuery.devicePixelRatio;

    final deviceType = DeviceType.current(context);
    final uiModeType = UiModeType.current(context);

    // Resolve effective screen type based on base orientation
    final effectiveScreenType = OrientationResolver.resolve(
      requestedType: _screenType,
      baseOrientation: _baseOrientation,
      context: context,
    );

    // Calculate base dimension based on effective screen type
    final baseDimension = effectiveScreenType == ScreenType.lowest
        ? (size.width < size.height ? size.width : size.height)
        : (size.width > size.height ? size.width : size.height);

    // Calculate aspect ratio factor
    final aspectRatio = size.width / size.height;
    final aspectRatioFactor =
        _calculateAspectRatioFactor(aspectRatio, deviceType);

    // Calculate density factor
    final densityFactor = _calculateDensityFactor(devicePixelRatio, deviceType);

    // Calculate device type factor
    final deviceTypeFactor = _calculateDeviceTypeFactor(deviceType, uiModeType);

    // Apply multi-window adjustment if not ignored
    double multiWindowFactor = 1.0;
    if (!_ignoreMultiWindowAdjustment &&
        AppDimensUtils.isMultiWindowMode(context)) {
      multiWindowFactor = 0.8; // Reduce by 20% in multi-window mode
    }

    // Apply logarithmic scaling for fixed dimensions
    final scalingFactor =
        math.log(baseDimension / 320.0) / math.log(1080.0 / 320.0);
    final scaledValue = baseValue * scalingFactor;

    // Apply all factors
    return scaledValue *
        aspectRatioFactor *
        densityFactor *
        deviceTypeFactor *
        multiWindowFactor;
  }

  /// [EN] Calculates the aspect ratio adjustment factor.
  /// @param aspectRatio The current aspect ratio.
  /// @param deviceType The current device type.
  /// @return The aspect ratio factor.
  /// [PT] Calcula o fator de ajuste de proporção.
  /// @param aspectRatio A proporção atual.
  /// @param deviceType O tipo de dispositivo atual.
  /// @return O fator de proporção.
  double _calculateAspectRatioFactor(
      double aspectRatio, DeviceType deviceType) {
    // Standard aspect ratios for different device types
    final standardRatios = {
      DeviceType.phone: 16.0 / 9.0,
      DeviceType.tablet: 4.0 / 3.0,
      DeviceType.watch: 1.0,
      DeviceType.tv: 16.0 / 9.0,
      DeviceType.carPlay: 16.0 / 9.0,
      DeviceType.desktop: 16.0 / 9.0,
      DeviceType.foldable: 16.0 / 10.0,
      DeviceType.unknown: 16.0 / 9.0,
    };

    final standardRatio = standardRatios[deviceType] ?? 16.0 / 9.0;
    final ratioDifference = (aspectRatio - standardRatio).abs() / standardRatio;

    return 1.0 - (ratioDifference * 0.1);
  }

  /// [EN] Calculates the density adjustment factor.
  /// @param devicePixelRatio The device pixel ratio.
  /// @param deviceType The current device type.
  /// @return The density factor.
  /// [PT] Calcula o fator de ajuste de densidade.
  /// @param devicePixelRatio A proporção de pixels do dispositivo.
  /// @param deviceType O tipo de dispositivo atual.
  /// @return O fator de densidade.
  double _calculateDensityFactor(
      double devicePixelRatio, DeviceType deviceType) {
    final standardDensities = {
      DeviceType.phone: 2.0,
      DeviceType.tablet: 2.0,
      DeviceType.watch: 2.0,
      DeviceType.tv: 1.0,
      DeviceType.carPlay: 2.0,
      DeviceType.desktop: 1.0,
      DeviceType.foldable: 2.5,
      DeviceType.unknown: 2.0,
    };

    final standardDensity = standardDensities[deviceType] ?? 2.0;
    final densityDifference =
        (devicePixelRatio - standardDensity).abs() / standardDensity;

    return 1.0 - (densityDifference * 0.05);
  }

  /// [EN] Calculates the device type adjustment factor.
  /// @param deviceType The current device type.
  /// @param uiModeType The current UI mode type.
  /// @return The device type factor.
  /// [PT] Calcula o fator de ajuste do tipo de dispositivo.
  /// @param deviceType O tipo de dispositivo atual.
  /// @param uiModeType O tipo de modo de UI atual.
  /// @return O fator do tipo de dispositivo.
  double _calculateDeviceTypeFactor(
      DeviceType deviceType, UiModeType uiModeType) {
    final baseFactors = {
      DeviceType.phone: 1.0,
      DeviceType.tablet: 1.2,
      DeviceType.watch: 0.8,
      DeviceType.tv: 1.5,
      DeviceType.carPlay: 1.3,
      DeviceType.desktop: 1.1,
      DeviceType.foldable: 1.1,
      DeviceType.unknown: 1.0,
    };

    final uiModeFactors = {
      UiModeType.normal: 1.0,
      UiModeType.carPlay: 1.2,
      UiModeType.tv: 1.3,
      UiModeType.watch: 0.9,
      UiModeType.mac: 1.1,
      UiModeType.unknown: 1.0,
    };

    final baseFactor = baseFactors[deviceType] ?? 1.0;
    final uiModeFactor = uiModeFactors[uiModeType] ?? 1.0;

    return baseFactor * uiModeFactor;
  }

  /// [EN] Gets the DP qualifier for the given screen size.
  /// @param size The screen size.
  /// @return The DP qualifier.
  /// [PT] Obtém o qualificador DP para o tamanho de tela dado.
  /// @param size O tamanho da tela.
  /// @return O qualificador DP.
  DpQualifier _getDpQualifier(Size size) {
    final smallestWidth = size.width < size.height ? size.width : size.height;

    if (smallestWidth >= 960) return DpQualifier.smallestWidth960;
    if (smallestWidth >= 840) return DpQualifier.smallestWidth840;
    if (smallestWidth >= 720) return DpQualifier.smallestWidth720;
    if (smallestWidth >= 600) return DpQualifier.smallestWidth600;
    if (smallestWidth >= 480) return DpQualifier.smallestWidth480;
    if (smallestWidth >= 360) return DpQualifier.smallestWidth360;
    if (smallestWidth >= 320) return DpQualifier.smallestWidth320;
    if (smallestWidth >= 240) return DpQualifier.smallestWidth240;
    return DpQualifier.smallestWidth160;
  }

  /// [EN] Gets the screen qualifier for the given screen size.
  /// @param size The screen size.
  /// @return The screen qualifier.
  /// [PT] Obtém o qualificador de tela para o tamanho de tela dado.
  /// @param size O tamanho da tela.
  /// @return O qualificador de tela.
  ScreenQualifier _getScreenQualifier(Size size) {
    final width = size.width;
    final height = size.height;

    if (width >= 1920 && height >= 1080) return ScreenQualifier.w1920h1080;
    if (width >= 1600 && height >= 900) return ScreenQualifier.w1600h900;
    if (width >= 1440 && height >= 900) return ScreenQualifier.w1440h900;
    if (width >= 1280 && height >= 800) return ScreenQualifier.w1280h800;
    if (width >= 1024 && height >= 768) return ScreenQualifier.w1024h768;
    if (width >= 800 && height >= 600) return ScreenQualifier.w800h600;
    if (width >= 640 && height >= 480) return ScreenQualifier.w640h480;
    return ScreenQualifier.w320h240;
  }
}
