/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens Flutter
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
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

import 'package:flutter/widgets.dart';
import '../models/base_orientation.dart';
import '../appdimens_types.dart';

/// [EN] Utility class for resolving screen types based on base orientation.
/// [PT] Classe utilitária para resolver tipos de tela baseado na orientação base.
class OrientationResolver {
  /// [EN] Resolves the effective ScreenType based on the base orientation and current device orientation.
  /// If the base orientation differs from the current orientation, LOWEST and HIGHEST are inverted.
  ///
  /// @param requestedType The originally requested screen type (LOWEST or HIGHEST)
  /// @param baseOrientation The orientation for which the design was created (PORTRAIT, LANDSCAPE, or AUTO)
  /// @param context The build context to get current screen dimensions
  /// @return The resolved ScreenType (may be inverted from requestedType)
  ///
  /// [PT] Resolve o ScreenType efetivo baseado na orientação base e na orientação atual do dispositivo.
  /// Se a orientação base difere da orientação atual, LOWEST e HIGHEST são invertidos.
  ///
  /// @param requestedType O tipo de tela originalmente requisitado (LOWEST ou HIGHEST)
  /// @param baseOrientation A orientação para a qual o design foi criado (PORTRAIT, LANDSCAPE ou AUTO)
  /// @param context O contexto de build para obter as dimensões atuais da tela
  /// @return O ScreenType resolvido (pode ser invertido do requestedType)
  static ScreenType resolve({
    required ScreenType requestedType,
    required BaseOrientation baseOrientation,
    required BuildContext context,
  }) {
    // If AUTO, no inversion - return as requested
    if (baseOrientation == BaseOrientation.auto) {
      return requestedType;
    }

    // Get current screen dimensions
    final size = MediaQuery.of(context).size;
    final currentIsPortrait = size.height > size.width;
    final currentIsLandscape = !currentIsPortrait;

    // Determine if inversion is needed
    bool shouldInvert = false;
    switch (baseOrientation) {
      case BaseOrientation.portrait:
        shouldInvert = currentIsLandscape;
        break;
      case BaseOrientation.landscape:
        shouldInvert = currentIsPortrait;
        break;
      case BaseOrientation.auto:
        shouldInvert = false;
        break;
    }

    // Invert if needed
    if (shouldInvert) {
      return requestedType == ScreenType.lowest
          ? ScreenType.highest
          : ScreenType.lowest;
    } else {
      return requestedType;
    }
  }

  /// [EN] Alternative resolve method that takes explicit dimensions instead of context.
  /// Useful for testing or when context is not available.
  ///
  /// [PT] Método alternativo de resolução que recebe dimensões explícitas ao invés de contexto.
  /// Útil para testes ou quando contexto não está disponível.
  static ScreenType resolveWithDimensions({
    required ScreenType requestedType,
    required BaseOrientation baseOrientation,
    required double width,
    required double height,
  }) {
    // If AUTO, no inversion - return as requested
    if (baseOrientation == BaseOrientation.auto) {
      return requestedType;
    }

    // Detect current orientation
    final currentIsPortrait = height > width;
    final currentIsLandscape = !currentIsPortrait;

    // Determine if inversion is needed
    bool shouldInvert = false;
    switch (baseOrientation) {
      case BaseOrientation.portrait:
        shouldInvert = currentIsLandscape;
        break;
      case BaseOrientation.landscape:
        shouldInvert = currentIsPortrait;
        break;
      case BaseOrientation.auto:
        shouldInvert = false;
        break;
    }

    // Invert if needed
    if (shouldInvert) {
      return requestedType == ScreenType.lowest
          ? ScreenType.highest
          : ScreenType.lowest;
    } else {
      return requestedType;
    }
  }
}
