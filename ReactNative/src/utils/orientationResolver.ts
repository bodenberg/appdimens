/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens React Native
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

import {Dimensions} from 'react-native';
import type {BaseOrientation} from '../types/BaseOrientation';

/**
 * [EN] Screen type for dimension calculations.
 * [PT] Tipo de tela para cálculos de dimensões.
 */
export type ScreenType = 'lowest' | 'highest';

/**
 * [EN] Resolves the effective ScreenType based on the base orientation and current device orientation.
 * If the base orientation differs from the current orientation, LOWEST and HIGHEST are inverted.
 *
 * @param requestedType The originally requested screen type ('lowest' or 'highest')
 * @param baseOrientation The orientation for which the design was created ('portrait', 'landscape', or 'auto')
 * @param dimensions Optional dimensions object (defaults to current window dimensions)
 * @return The resolved ScreenType (may be inverted from requestedType)
 *
 * [PT] Resolve o ScreenType efetivo baseado na orientação base e na orientação atual do dispositivo.
 * Se a orientação base difere da orientação atual, 'lowest' e 'highest' são invertidos.
 *
 * @param requestedType O tipo de tela originalmente requisitado ('lowest' ou 'highest')
 * @param baseOrientation A orientação para a qual o design foi criado ('portrait', 'landscape' ou 'auto')
 * @param dimensions Objeto de dimensões opcional (padrão: dimensões atuais da janela)
 * @return O ScreenType resolvido (pode ser invertido do requestedType)
 *
 * @example
 * ```typescript
 * // Resolve based on current window dimensions
 * const screenType = resolveScreenType('lowest', 'portrait');
 *
 * // Resolve with custom dimensions
 * const screenType2 = resolveScreenType('lowest', 'portrait', { width: 800, height: 600 });
 * ```
 */
export function resolveScreenType(
  requestedType: ScreenType,
  baseOrientation: BaseOrientation,
  dimensions?: {width: number; height: number},
): ScreenType {
  // If AUTO, no inversion - return as requested
  if (baseOrientation === 'auto') {
    return requestedType;
  }

  // Get dimensions
  const dims = dimensions || Dimensions.get('window');
  const {width, height} = dims;

  // Detect current orientation
  const currentIsPortrait = height > width;
  const currentIsLandscape = !currentIsPortrait;

  // Determine if inversion is needed
  const shouldInvert =
    (baseOrientation === 'portrait' && currentIsLandscape) ||
    (baseOrientation === 'landscape' && currentIsPortrait);

  // Invert if needed
  if (shouldInvert) {
    return requestedType === 'lowest' ? 'highest' : 'lowest';
  } else {
    return requestedType;
  }
}

/**
 * [EN] Helper function to get current orientation as BaseOrientation.
 * [PT] Função auxiliar para obter a orientação atual como BaseOrientation.
 *
 * @returns The current device orientation ('portrait' or 'landscape')
 */
export function getCurrentOrientation(): 'portrait' | 'landscape' {
  const {width, height} = Dimensions.get('window');
  return height > width ? 'portrait' : 'landscape';
}

/**
 * [EN] Check if inversion would occur for given parameters.
 * Useful for debugging or UI indicators.
 *
 * [PT] Verifica se inversão ocorreria para os parâmetros dados.
 * Útil para debugging ou indicadores de UI.
 */
export function wouldInvert(
  baseOrientation: BaseOrientation,
  dimensions?: {width: number; height: number},
): boolean {
  if (baseOrientation === 'auto') {
    return false;
  }

  const dims = dimensions || Dimensions.get('window');
  const currentIsPortrait = dims.height > dims.width;

  return (
    (baseOrientation === 'portrait' && !currentIsPortrait) ||
    (baseOrientation === 'landscape' && currentIsPortrait)
  );
}
