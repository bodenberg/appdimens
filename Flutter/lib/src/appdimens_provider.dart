/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Provider
 *
 * Description:
 * Provider and InheritedWidget for AppDimens configuration and caching.
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

/// [EN] Configuration data for AppDimens provider.
/// [PT] Dados de configuração para o provider do AppDimens.
class AppDimensConfig {
  final bool aspectRatioEnabled;
  final bool cacheEnabled;
  final bool ignoreMultiWindowAdjustment;
  final DeviceType? forcedDeviceType;
  final UiModeType? forcedUiModeType;

  const AppDimensConfig({
    this.aspectRatioEnabled = true,
    this.cacheEnabled = true,
    this.ignoreMultiWindowAdjustment = false,
    this.forcedDeviceType,
    this.forcedUiModeType,
  });

  AppDimensConfig copyWith({
    bool? aspectRatioEnabled,
    bool? cacheEnabled,
    bool? ignoreMultiWindowAdjustment,
    DeviceType? forcedDeviceType,
    UiModeType? forcedUiModeType,
  }) {
    return AppDimensConfig(
      aspectRatioEnabled: aspectRatioEnabled ?? this.aspectRatioEnabled,
      cacheEnabled: cacheEnabled ?? this.cacheEnabled,
      ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment ?? this.ignoreMultiWindowAdjustment,
      forcedDeviceType: forcedDeviceType ?? this.forcedDeviceType,
      forcedUiModeType: forcedUiModeType ?? this.forcedUiModeType,
    );
  }
}

/// [EN] AppDimens provider that manages configuration and caching.
/// [PT] Provider do AppDimens que gerencia configuração e cache.
class AppDimensProvider extends InheritedWidget {
  final AppDimensConfig config;
  final Map<String, double> globalCache;

  const AppDimensProvider({
    Key? key,
    required this.config,
    required Widget child,
    Map<String, double>? globalCache,
  }) : globalCache = globalCache ?? const {},
        super(key: key, child: child);

  /// [EN] Gets the AppDimensProvider from the widget tree.
  /// @param context The BuildContext.
  /// @return The AppDimensProvider instance.
  /// [PT] Obtém o AppDimensProvider da árvore de widgets.
  /// @param context O BuildContext.
  /// @return A instância AppDimensProvider.
  static AppDimensProvider? of(BuildContext context) {
    return context.dependOnInheritedWidgetOfExactType<AppDimensProvider>();
  }

  /// [EN] Gets the AppDimensProvider from the widget tree or throws an error.
  /// @param context The BuildContext.
  /// @return The AppDimensProvider instance.
  /// [PT] Obtém o AppDimensProvider da árvore de widgets ou lança um erro.
  /// @param context O BuildContext.
  /// @return A instância AppDimensProvider.
  static AppDimensProvider require(BuildContext context) {
    final provider = of(context);
    if (provider == null) {
      throw FlutterError('AppDimensProvider not found in widget tree. Make sure to wrap your app with AppDimensProvider.');
    }
    return provider;
  }

  @override
  bool updateShouldNotify(AppDimensProvider oldWidget) {
    return config != oldWidget.config || globalCache != oldWidget.globalCache;
  }
}

/// [EN] AppDimens provider widget that wraps the app with configuration.
/// [PT] Widget provider do AppDimens que envolve o app com configuração.
class AppDimensApp extends StatelessWidget {
  final AppDimensConfig config;
  final Widget child;

  const AppDimensApp({
    Key? key,
    required this.child,
    this.config = const AppDimensConfig(),
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AppDimensProvider(
      config: config,
      child: child,
    );
  }
}

/// [EN] Hook for accessing AppDimens configuration in widgets.
/// [PT] Hook para acessar configuração do AppDimens em widgets.
class AppDimensHook extends StatelessWidget {
  final Widget Function(BuildContext context, AppDimensConfig config) builder;

  const AppDimensHook({
    Key? key,
    required this.builder,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final provider = AppDimensProvider.of(context);
    if (provider == null) {
      return builder(context, const AppDimensConfig());
    }
    return builder(context, provider.config);
  }
}

/// [EN] Widget that rebuilds when AppDimens configuration changes.
/// [PT] Widget que reconstrói quando a configuração do AppDimens muda.
class AppDimensConsumer extends StatelessWidget {
  final Widget Function(BuildContext context, AppDimensConfig config) builder;

  const AppDimensConsumer({
    Key? key,
    required this.builder,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final provider = AppDimensProvider.of(context);
    if (provider == null) {
      return builder(context, const AppDimensConfig());
    }
    return builder(context, provider.config);
  }
}

/// [EN] Widget that provides AppDimens configuration to its children.
/// [PT] Widget que fornece configuração do AppDimens para seus filhos.
class AppDimensScope extends StatelessWidget {
  final AppDimensConfig config;
  final Widget child;

  const AppDimensScope({
    Key? key,
    required this.config,
    required this.child,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return AppDimensProvider(
      config: config,
      child: child,
    );
  }
}

/// [EN] Mixin for widgets that need access to AppDimens configuration.
/// [PT] Mixin para widgets que precisam acessar configuração do AppDimens.
mixin AppDimensMixin<T extends StatefulWidget> on State<T> {
  AppDimensConfig get appDimensConfig {
    final provider = AppDimensProvider.of(context);
    return provider?.config ?? const AppDimensConfig();
  }

  bool get isAspectRatioEnabled => appDimensConfig.aspectRatioEnabled;
  bool get isCacheEnabled => appDimensConfig.cacheEnabled;
  bool get isMultiWindowAdjustmentIgnored => appDimensConfig.ignoreMultiWindowAdjustment;
  DeviceType? get forcedDeviceType => appDimensConfig.forcedDeviceType;
  UiModeType? get forcedUiModeType => appDimensConfig.forcedUiModeType;
}

/// [EN] Extension for BuildContext to provide convenient AppDimens access.
/// [PT] Extensão para BuildContext para fornecer acesso conveniente ao AppDimens.
extension AppDimensBuildContextExtension on BuildContext {
  /// [EN] Gets the AppDimens configuration from the provider.
  /// @return The AppDimensConfig instance.
  /// [PT] Obtém a configuração do AppDimens do provider.
  /// @return A instância AppDimensConfig.
  AppDimensConfig get appDimensConfig {
    final provider = AppDimensProvider.of(this);
    return provider?.config ?? const AppDimensConfig();
  }

  /// [EN] Gets the AppDimens provider instance.
  /// @return The AppDimensProvider instance or null.
  /// [PT] Obtém a instância do provider AppDimens.
  /// @return A instância AppDimensProvider ou null.
  AppDimensProvider? get appDimensProvider => AppDimensProvider.of(this);
}
