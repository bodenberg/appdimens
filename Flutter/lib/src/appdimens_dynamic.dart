/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Dynamic Dimension Builder
 *
 * Description:
 * Dynamic dimension builder that provides proportional scaling for containers, grids,
 * and fluid fonts, adapting aggressively to screen size changes.
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

/// [EN] Dynamic dimension builder that provides proportional scaling for containers and grids.
/// [PT] Construtor de dimensão dinâmica que fornece escalonamento proporcional para containers e grids.
class AppDimensDynamic {
  final double _initialValue;
  final bool _ignoreMultiWindowAdjustment;
  final Map<String, double> _customValues = {};
  final Map<String, double> _uiModeValues = {};
  final Map<String, double> _dpQualifierValues = {};
  final Map<String, double> _deviceTypeValues = {};
  final Map<String, double> _screenQualifierValues = {};
  final Map<String, double> _intersectionValues = {};
  final Map<String, double> _cache = {};
  bool _cacheEnabled = true;

  /// [EN] Creates a new AppDimensDynamic instance.
  /// @param initialValue The initial base value.
  /// @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
  /// [PT] Cria uma nova instância AppDimensDynamic.
  /// @param initialValue O valor base inicial.
  /// @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
  AppDimensDynamic(
    this._initialValue, {
    bool ignoreMultiWindowAdjustment = false,
  }) : _ignoreMultiWindowAdjustment = ignoreMultiWindowAdjustment;

  // MARK: - Custom Value Methods

  /// [EN] Sets a custom dimension value for a specific UI mode.
  /// @param uiMode The UI mode type.
  /// @param value The custom dimension value.
  /// @return The AppDimensDynamic instance for chaining.
  /// [PT] Define um valor de dimensão personalizado para um modo de UI específico.
  /// @param uiMode O tipo de modo de UI.
  /// @param value O valor de dimensão personalizado.
  /// @return A instância AppDimensDynamic para encadeamento.
  AppDimensDynamic uiMode(UiModeType uiMode, double value) {
    _uiModeValues[uiMode.name] = value;
    return this;
  }

  /// [EN] Sets a custom dimension value for a specific DP qualifier.
  /// @param qualifier The DP qualifier.
  /// @param value The custom dimension value.
  /// @return The AppDimensDynamic instance for chaining.
  /// [PT] Define um valor de dimensão personalizado para um qualificador DP específico.
  /// @param qualifier O qualificador DP.
  /// @param value O valor de dimensão personalizado.
  /// @return A instância AppDimensDynamic para encadeamento.
  AppDimensDynamic dpQualifier(DpQualifier qualifier, double value) {
    _dpQualifierValues[qualifier.name] = value;
    return this;
  }

  /// [EN] Sets a custom dimension value for a specific device type.
  /// @param deviceType The device type.
  /// @param value The custom dimension value.
  /// @return The AppDimensDynamic instance for chaining.
  /// [PT] Define um valor de dimensão personalizado para um tipo de dispositivo específico.
  /// @param deviceType O tipo de dispositivo.
  /// @param value O valor de dimensão personalizado.
  /// @return A instância AppDimensDynamic para encadeamento.
  AppDimensDynamic deviceType(DeviceType deviceType, double value) {
    _deviceTypeValues[deviceType.name] = value;
    return this;
  }

  /// [EN] Sets a custom dimension value for a specific screen qualifier.
  /// @param qualifier The screen qualifier.
  /// @param value The custom dimension value.
  /// @return The AppDimensDynamic instance for chaining.
  /// [PT] Define um valor de dimensão personalizado para um qualificador de tela específico.
  /// @param qualifier O qualificador de tela.
  /// @param value O valor de dimensão personalizado.
  /// @return A instância AppDimensDynamic para encadeamento.
  AppDimensDynamic screenQualifier(ScreenQualifier qualifier, double value) {
    _screenQualifierValues[qualifier.name] = value;
    return this;
  }

  /// [EN] Sets a custom dimension value for a specific intersection of qualifiers.
  /// @param uiMode The UI mode type.
  /// @param dpQualifier The DP qualifier.
  /// @param value The custom dimension value.
  /// @return The AppDimensDynamic instance for chaining.
  /// [PT] Define um valor de dimensão personalizado para uma interseção específica de qualificadores.
  /// @param uiMode O tipo de modo de UI.
  /// @param dpQualifier O qualificador DP.
  /// @param value O valor de dimensão personalizado.
  /// @return A instância AppDimensDynamic para encadeamento.
  AppDimensDynamic intersection(UiModeType uiMode, DpQualifier dpQualifier, double value) {
    final key = '${uiMode.name}_${dpQualifier.name}';
    _intersectionValues[key] = value;
    return this;
  }

  // MARK: - Cache Control Methods

  /// [EN] Enables or disables caching for this instance.
  /// @param enabled If true, enables caching; if false, disables and clears cache.
  /// @return The AppDimensDynamic instance for chaining.
  /// [PT] Ativa ou desativa o cache para esta instância.
  /// @param enabled Se verdadeiro, ativa o cache; se falso, desativa e limpa o cache.
  /// @return A instância AppDimensDynamic para encadeamento.
  AppDimensDynamic cache(bool enabled) {
    _cacheEnabled = enabled;
    if (!enabled) {
      _cache.clear();
    }
    return this;
  }

  /// [EN] Clears the cache for this instance.
  /// @return The AppDimensDynamic instance for chaining.
  /// [PT] Limpa o cache para esta instância.
  /// @return A instância AppDimensDynamic para encadeamento.
  AppDimensDynamic clearCache() {
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
    final devicePixelRatio = mediaQuery.devicePixelRatio;
    
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
    
    // Apply dynamic scaling calculation
    return _applyDynamicScaling(context, _initialValue);
  }

  /// [EN] Applies dynamic scaling calculation to the base value.
  /// @param context The BuildContext.
  /// @param baseValue The base value to scale.
  /// @return The scaled value.
  /// [PT] Aplica o cálculo de escalonamento dinâmico ao valor base.
  /// @param context O BuildContext.
  /// @param baseValue O valor base para escalonar.
  /// @return O valor escalonado.
  double _applyDynamicScaling(BuildContext context, double baseValue) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    final devicePixelRatio = mediaQuery.devicePixelRatio;
    
    final deviceType = DeviceType.current(context);
    final uiModeType = UiModeType.current(context);
    
    // Calculate base dimension (smallest dimension for scaling)
    final baseDimension = size.width < size.height ? size.width : size.height;
    
    // Calculate aspect ratio factor
    final aspectRatio = size.width / size.height;
    final aspectRatioFactor = _calculateAspectRatioFactor(aspectRatio, deviceType);
    
    // Calculate density factor
    final densityFactor = _calculateDensityFactor(devicePixelRatio, deviceType);
    
    // Calculate device type factor
    final deviceTypeFactor = _calculateDeviceTypeFactor(deviceType, uiModeType);
    
    // Apply multi-window adjustment if not ignored
    double multiWindowFactor = 1.0;
    if (!_ignoreMultiWindowAdjustment && AppDimensUtils.isMultiWindowMode(context)) {
      multiWindowFactor = 0.8; // Reduce by 20% in multi-window mode
    }
    
    // Test for multi-window mode
    if (!_ignoreMultiWindowAdjustment && AppDimensUtils.isMultiWindowMode(context)) {
      multiWindowFactor = 0.8; // Reduce by 20% in multi-window mode
    }
    
    // Apply proportional scaling for dynamic dimensions
    final scalingFactor = baseDimension / 320.0; // Direct proportional scaling
    final scaledValue = baseValue * scalingFactor;
    
    // Apply all factors
    return scaledValue * aspectRatioFactor * densityFactor * deviceTypeFactor * multiWindowFactor;
  }

  /// [EN] Calculates the aspect ratio adjustment factor.
  /// @param aspectRatio The current aspect ratio.
  /// @param deviceType The current device type.
  /// @return The aspect ratio factor.
  /// [PT] Calcula o fator de ajuste de proporção.
  /// @param aspectRatio A proporção atual.
  /// @param deviceType O tipo de dispositivo atual.
  /// @return O fator de proporção.
  double _calculateAspectRatioFactor(double aspectRatio, DeviceType deviceType) {
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
  double _calculateDensityFactor(double devicePixelRatio, DeviceType deviceType) {
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
    final densityDifference = (devicePixelRatio - standardDensity).abs() / standardDensity;
    
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
  double _calculateDeviceTypeFactor(DeviceType deviceType, UiModeType uiModeType) {
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
