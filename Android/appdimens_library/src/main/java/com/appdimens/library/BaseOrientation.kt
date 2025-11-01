/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens
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
package com.appdimens.library

/**
 * [EN] Defines the base orientation for which the design was originally created.
 * This allows the system to automatically invert LOWEST/HIGHEST screen types
 * when the current orientation differs from the design orientation.
 * 
 * [PT] Define a orientação base para a qual o design foi originalmente criado.
 * Isso permite que o sistema inverta automaticamente os tipos de tela LOWEST/HIGHEST
 * quando a orientação atual difere da orientação do design.
 * 
 * @property PORTRAIT The design was created for portrait orientation (width < height)
 * @property LANDSCAPE The design was created for landscape orientation (width > height)
 * @property AUTO No specific orientation (default behavior, no auto-inversion)
 */
enum class BaseOrientation {
    /**
     * [EN] Design created for portrait orientation (width < height).
     * When device is in landscape, LOWEST↔HIGHEST will be inverted.
     * 
     * [PT] Design criado para orientação portrait (largura < altura).
     * Quando o dispositivo está em landscape, LOWEST↔HIGHEST será invertido.
     */
    PORTRAIT,
    
    /**
     * [EN] Design created for landscape orientation (width > height).
     * When device is in portrait, LOWEST↔HIGHEST will be inverted.
     * 
     * [PT] Design criado para orientação landscape (largura > altura).
     * Quando o dispositivo está em portrait, LOWEST↔HIGHEST será invertido.
     */
    LANDSCAPE,
    
    /**
     * [EN] No specific orientation (default behavior).
     * Screen types are used as-is without auto-inversion.
     * 
     * [PT] Nenhuma orientação específica (comportamento padrão).
     * Tipos de tela são usados como estão, sem auto-inversão.
     */
    AUTO
}

