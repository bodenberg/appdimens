/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens Web
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

/**
 * [EN] Defines the base orientation for which the design was originally created.
 * This allows the system to automatically invert LOWEST/HIGHEST screen types
 * when the current orientation differs from the design orientation.
 * 
 * [PT] Define a orientação base para a qual o design foi originalmente criado.
 * Isso permite que o sistema inverta automaticamente os tipos de tela LOWEST/HIGHEST
 * quando a orientação atual difere da orientação do design.
 * 
 * @example
 * ```typescript
 * // Design created for portrait
 * const size = fixed(16).baseOrientation('portrait').type('lowest').toPx();
 * 
 * // Using shorthand
 * const size2 = fixed(16).portraitLowest().toPx();
 * ```
 */
export type BaseOrientation = 'portrait' | 'landscape' | 'auto';

/**
 * [EN] Type guard to check if a value is a valid BaseOrientation.
 * [PT] Type guard para verificar se um valor é um BaseOrientation válido.
 */
export function isBaseOrientation(value: unknown): value is BaseOrientation {
  return (
    value === 'portrait' ||
    value === 'landscape' ||
    value === 'auto'
  );
}

/**
 * [EN] Constants for BaseOrientation values.
 * [PT] Constantes para valores de BaseOrientation.
 */
export const BASE_ORIENTATION = {
  PORTRAIT: 'portrait' as BaseOrientation,
  LANDSCAPE: 'landscape' as BaseOrientation,
  AUTO: 'auto' as BaseOrientation,
} as const;

