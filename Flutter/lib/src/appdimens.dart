/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Main Class
 *
 * Description:
 * Main AppDimens class that provides access to fixed and dynamic dimension builders.
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
import 'appdimens_fixed.dart';
import 'appdimens_dynamic.dart';
import 'appdimens_utils.dart';
import 'models/cache_stats.dart';

/// [EN] Main AppDimens class that provides access to fixed and dynamic dimension builders.
/// [PT] Classe principal AppDimens que fornece acesso aos construtores de dimensões fixas e dinâmicas.
class AppDimens {
  AppDimens._();

  // MARK: - Global Configuration Properties

  /// [EN] Global aspect ratio adjustment setting.
  /// [PT] Configuração global de ajuste de proporção.
  static bool _globalAspectRatioEnabled = true;

  /// [EN] Global cache control for all AppDimens instances.
  /// [PT] Controle global de cache para todas as instâncias AppDimens.
  static bool _globalCacheEnabled = true;

  /// [EN] Global multi-window adjustment setting.
  /// [PT] Configuração global de ajuste multi-window.
  static bool _globalIgnoreMultiWindowAdjustment = false;

  // MARK: - Global Configuration Methods

  /// [EN] Sets the global aspect ratio adjustment setting.
  /// @param enabled If true, enables aspect ratio adjustment globally.
  /// @return The AppDimens instance for chaining.
  /// [PT] Define a configuração global de ajuste de proporção.
  /// @param enabled Se verdadeiro, ativa o ajuste de proporção globalmente.
  /// @return A instância AppDimens para encadeamento.
  static AppDimens setGlobalAspectRatio(bool enabled) {
    _globalAspectRatioEnabled = enabled;
    return AppDimens._();
  }

  /// [EN] Sets the global cache control setting.
  /// @param enabled If true, enables global cache; if false, disables and clears all caches.
  /// @return The AppDimens instance for chaining.
  /// [PT] Define a configuração global de controle de cache.
  /// @param enabled Se verdadeiro, ativa o cache global; se falso, desativa e limpa todos os caches.
  /// @return A instância AppDimens para encadeamento.
  static AppDimens setGlobalCache(bool enabled) {
    _globalCacheEnabled = enabled;
    return AppDimens._();
  }

  /// [EN] Sets the global multi-view adjustment setting.
  /// @param ignore If true, ignores multi-view adjustments globally.
  /// @return The AppDimens instance for chaining.
  /// [PT] Define a configuração global de ajuste multi-view.
  /// @param ignore Se verdadeiro, ignora os ajustes multi-view globalmente.
  /// @return A instância AppDimens para encadeamento.
  static AppDimens setGlobalIgnoreMultiWindowAdjustment(bool ignore) {
    _globalIgnoreMultiWindowAdjustment = ignore;
    return AppDimens._();
  }

  /// [EN] Gets the current global aspect ratio setting.
  /// @return True if aspect ratio adjustment is enabled globally.
  /// [PT] Obtém a configuração global atual de proporção.
  /// @return True se o ajuste de proporção está ativado globalmente.
  static bool get isGlobalAspectRatioEnabled => _globalAspectRatioEnabled;

  /// [EN] Gets the current global cache setting.
  /// @return True if global cache is enabled.
  /// [PT] Obtém a configuração global atual de cache.
  /// @return True se o cache global está ativado.
  static bool get isGlobalCacheEnabled => _globalCacheEnabled;

  /// [EN] Gets the current global multi-view adjustment setting.
  /// @return True if multi-view adjustments are ignored globally.
  /// [PT] Obtém a configuração global atual de ajuste multi-view.
  /// @return True se os ajustes multi-view são ignorados globalmente.
  static bool get isGlobalIgnoreMultiWindowAdjustment => _globalIgnoreMultiWindowAdjustment;

  // MARK: - Builder Methods

  /// [EN] Creates a fixed dimension builder from a double value.
  /// @param initialValue The initial base value.
  /// @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
  /// @return An AppDimensFixed instance for chaining.
  /// [PT] Cria um construtor de dimensão fixa a partir de um valor double.
  /// @param initialValue O valor base inicial.
  /// @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
  /// @return Uma instância AppDimensFixed para encadeamento.
  static AppDimensFixed fixed(
    double initialValue, {
    bool? ignoreMultiWindowAdjustment,
  }) {
    final ignore = ignoreMultiWindowAdjustment ?? _globalIgnoreMultiWindowAdjustment;
    return AppDimensFixed(
      initialValue,
      ignoreMultiWindowAdjustment: ignore,
    );
  }

  /// [EN] Creates a dynamic dimension builder from a double value.
  /// @param initialValue The initial base value.
  /// @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
  /// @return An AppDimensDynamic instance for chaining.
  /// [PT] Cria um construtor de dimensão dinâmica a partir de um valor double.
  /// @param initialValue O valor base inicial.
  /// @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
  /// @return Uma instância AppDimensDynamic para encadeamento.
  static AppDimensDynamic dynamic(
    double initialValue, {
    bool? ignoreMultiWindowAdjustment,
  }) {
    final ignore = ignoreMultiWindowAdjustment ?? _globalIgnoreMultiWindowAdjustment;
    return AppDimensDynamic(
      initialValue,
      ignoreMultiWindowAdjustment: ignore,
    );
  }

  // MARK: - Utility Methods

  /// [EN] Calculates a dynamic dimension value based on a percentage (0.0 to 1.0) of the screen dimension.
  /// @param percentage The percentage (0.0 to 1.0).
  /// @param type The screen dimension to use (LOWEST/HIGHEST).
  /// @param context The BuildContext.
  /// @return The adjusted value in logical pixels.
  /// [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem (0.0 a 1.0) da dimensão da tela.
  /// @param percentage A porcentagem (0.0 a 1.0).
  /// @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
  /// @param context O BuildContext.
  /// @return O valor ajustado em pixels lógicos.
  static double dynamicPercentage(
    double percentage,
    BuildContext context, {
    ScreenType type = ScreenType.lowest,
  }) {
    assert(percentage >= 0.0 && percentage <= 1.0, 'Percentage must be between 0.0 and 1.0');
    
    final mediaQuery = MediaQuery.of(context);
    final screenWidth = mediaQuery.size.width;
    final screenHeight = mediaQuery.size.height;

    final dimensionToUse = type == ScreenType.highest
        ? (screenWidth > screenHeight ? screenWidth : screenHeight)
        : (screenWidth < screenHeight ? screenWidth : screenHeight);

    return dimensionToUse * percentage;
  }

  /// [EN] Calculates a dynamic dimension value based on a percentage in Dp.
  /// Compatible with Android API.
  /// @param percentage The percentage (0.0 to 1.0).
  /// @param context The BuildContext.
  /// @param type The screen dimension to use (LOWEST/HIGHEST).
  /// @return The adjusted value in Dp.
  /// [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem em Dp.
  /// Compatível com API Android.
  /// @param percentage A porcentagem (0.0 a 1.0).
  /// @param context O BuildContext.
  /// @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
  /// @return O valor ajustado em Dp.
  static double dynamicPercentageDp(
    double percentage,
    BuildContext context, {
    ScreenType type = ScreenType.lowest,
  }) {
    return dynamicPercentage(percentage, context, type: type);
  }

  /// [EN] Calculates a dynamic dimension value based on a percentage in physical pixels.
  /// Compatible with Android API.
  /// @param percentage The percentage (0.0 to 1.0).
  /// @param context The BuildContext.
  /// @param type The screen dimension to use (LOWEST/HIGHEST).
  /// @return The adjusted value in physical pixels.
  /// [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem em pixels físicos.
  /// Compatível com API Android.
  /// @param percentage A porcentagem (0.0 a 1.0).
  /// @param context O BuildContext.
  /// @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
  /// @return O valor ajustado em pixels físicos.
  static double dynamicPercentagePx(
    double percentage,
    BuildContext context, {
    ScreenType type = ScreenType.lowest,
  }) {
    final dpValue = dynamicPercentageDp(percentage, context, type: type);
    final devicePixelRatio = MediaQuery.of(context).devicePixelRatio;
    return dpValue * devicePixelRatio;
  }

  /// [EN] Calculates a dynamic dimension value based on a percentage in scalable pixels (Sp).
  /// Compatible with Android API.
  /// @param percentage The percentage (0.0 to 1.0).
  /// @param context The BuildContext.
  /// @param type The screen dimension to use (LOWEST/HIGHEST).
  /// @return The adjusted value in Sp.
  /// [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem em pixels escaláveis (Sp).
  /// Compatível com API Android.
  /// @param percentage A porcentagem (0.0 a 1.0).
  /// @param context O BuildContext.
  /// @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
  /// @return O valor ajustado em Sp.
  static double dynamicPercentageSp(
    double percentage,
    BuildContext context, {
    ScreenType type = ScreenType.lowest,
  }) {
    final dpValue = dynamicPercentageDp(percentage, context, type: type);
    final textScaleFactor = MediaQuery.of(context).textScaleFactor;
    return dpValue * textScaleFactor;
  }

  /// [EN] Calculates the maximum number of items that can fit in a container.
  /// Compatible with Android API.
  /// @param containerSizePx The size (width or height) of the container in pixels.
  /// @param itemSizeDp The size of each item in Dp.
  /// @param itemMarginDp The margin of each item in Dp.
  /// @param context The BuildContext.
  /// @return The number of items that can fit.
  /// [PT] Calcula o número máximo de itens que cabem em um container.
  /// Compatível com API Android.
  /// @param containerSizePx O tamanho (largura ou altura) do container em pixels.
  /// @param itemSizeDp O tamanho de cada item em Dp.
  /// @param itemMarginDp A margem de cada item em Dp.
  /// @param context O BuildContext.
  /// @return O número de itens que cabem.
  static int calculateAvailableItemCount(
    double containerSizePx,
    double itemSizeDp,
    double itemMarginDp,
    BuildContext context,
  ) {
    final devicePixelRatio = MediaQuery.of(context).devicePixelRatio;
    final itemSizePx = itemSizeDp * devicePixelRatio;
    final itemMarginPx = itemMarginDp * devicePixelRatio;

    final totalItemSizePx = itemSizePx + (itemMarginPx * 2);

    return totalItemSizePx > 0 ? (containerSizePx / totalItemSizePx).floor() : 0;
  }

  /// [EN] Gets the current screen information.
  /// @param context The BuildContext.
  /// @return ScreenInfo containing screen dimensions and properties.
  /// [PT] Obtém as informações atuais da tela.
  /// @param context O BuildContext.
  /// @return ScreenInfo contendo dimensões e propriedades da tela.
  static ScreenInfo getCurrentScreenInfo(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    final devicePixelRatio = mediaQuery.devicePixelRatio;
    final orientation = mediaQuery.orientation;
    
    final deviceType = DeviceType.current(context);
    final uiModeType = UiModeType.current(context);

    return ScreenInfo(
      width: size.width,
      height: size.height,
      devicePixelRatio: devicePixelRatio,
      deviceType: deviceType,
      uiModeType: uiModeType,
      orientation: orientation,
    );
  }

  /// [EN] Calculates adjustment factors for the current screen configuration.
  /// @param context The BuildContext.
  /// @return ScreenAdjustmentFactors containing calculated factors.
  /// [PT] Calcula os fatores de ajuste para a configuração atual da tela.
  /// @param context O BuildContext.
  /// @return ScreenAdjustmentFactors contendo os fatores calculados.
  static ScreenAdjustmentFactors calculateAdjustmentFactors(BuildContext context) {
    return AppDimensUtils.calculateAdjustmentFactors(context);
  }

  /// [EN] Clears all caches from all instances.
  /// [PT] Limpa todos os caches de todas as instâncias.
  static void clearAllCaches() {
    // This would be implemented with a registry of instances
    // For now, individual instances will clear their own caches
  }

  // MARK: - Cache Statistics

  /// [EN] Cache statistics structure.
  /// [PT] Estrutura de estatísticas de cache.
  static CacheStats getCacheStats() {
    // Simplified cache stats implementation
    // In a real implementation, this would track actual cache usage
    return CacheStats(
      totalEntries: 0,
      totalAccesses: 0,
      cacheHits: 0,
      cacheMisses: 0,
      hitRate: 0.0,
      avgCalculationTime: 0.001,
      memoryUsage: 0,
    );
  }

  // MARK: - Warmup Cache

  /// [EN] Warms up the cache with common calculations.
  /// Pre-calculates and caches common dimension values for faster first access.
  /// Call this during app initialization for optimal performance.
  /// 
  /// [PT] Aquece o cache com cálculos comuns.
  /// Pré-calcula e armazena valores de dimensão comuns para acesso mais rápido.
  /// Chame isso durante a inicialização do app para performance ótima.
  /// 
  /// @param context The BuildContext to use for calculations.
  /// 
  /// @example
  /// ```dart
  /// // Warm up cache during app initialization
  /// class MyApp extends StatelessWidget {
  ///   @override
  ///   Widget build(BuildContext context) {
  ///     // Warm up cache on first build
  ///     WidgetsBinding.instance.addPostFrameCallback((_) {
  ///       AppDimens.warmupCache(context);
  ///     });
  ///     
  ///     return MaterialApp(
  ///       home: HomeScreen(),
  ///     );
  ///   }
  /// }
  /// ```
  static void warmupCache(BuildContext context) {
    // Pre-calculate common dimension values
    final commonSizes = <double>[
      // UI spacing and padding
      4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 56, 64, 72, 80,
      // Text sizes
      10, 11, 12, 13, 14, 15, 16, 17, 18, 20, 22, 24, 28, 32, 36,
      // Layout dimensions
      100, 120, 150, 200, 250, 300, 350, 400
    ];

    // Warm up Fixed dimensions (most common)
    for (final size in commonSizes) {
      try {
        AppDimens.fixed(size).calculate(context);
      } catch (e) {
        // Ignore errors during warmup
      }
    }

    // Warm up Dynamic dimensions (common for large containers)
    for (final size in [100.0, 200.0, 300.0, 400.0, 500.0]) {
      try {
        AppDimens.dynamic(size).calculate(context);
      } catch (e) {
        // Ignore errors during warmup
      }
    }
  }

  // MARK: - Legacy Methods for Compatibility

  /// [EN] Legacy method for creating fixed dimensions.
  /// @deprecated Use AppDimens.fixed() instead.
  /// [PT] Método legado para criar dimensões fixas.
  /// @deprecated Use AppDimens.fixed() em vez disso.
  @Deprecated('Use AppDimens.fixed() instead')
  static AppDimensFixed fixedDp(double value) {
    return fixed(value);
  }

  /// [EN] Legacy method for creating dynamic dimensions.
  /// @deprecated Use AppDimens.dynamic() instead.
  /// [PT] Método legado para criar dimensões dinâmicas.
  /// @deprecated Use AppDimens.dynamic() em vez disso.
  @Deprecated('Use AppDimens.dynamic() instead')
  static AppDimensDynamic dynamicDp(double value) {
    return dynamic(value);
  }
}
