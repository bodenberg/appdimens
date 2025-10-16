/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04
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

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * [EN] Defines the Android UI Mode Types for dimension customization,
 * based on [Configuration.uiMode].
 *
 * [PT] Define os tipos de modo de interface do usuário (UI Mode Type) do Android
 * para customização de dimensões, com base em [Configuration.uiMode].
 */
enum class UiModeType(val configValue: Int) {
    /**
     * [EN] Default Phone/Tablet.
     *
     * [PT] Telefone/Tablet Padrão.
     */
    NORMAL(Configuration.UI_MODE_TYPE_NORMAL),

    /**
     * [EN] Television.
     *
     * [PT] Televisão.
     */
    TELEVISION(Configuration.UI_MODE_TYPE_TELEVISION),

    /**
     * [EN] Car.
     *
     * [PT] Carro.
     */
    CAR(Configuration.UI_MODE_TYPE_CAR),

    /**
     * [EN] Watch (Wear OS).
     *
     * [PT] Relógio (Wear OS).
     */
    WATCH(Configuration.UI_MODE_TYPE_WATCH),

    /**
     * [EN] Desk Device (Docked).
     *
     * [PT] Dispositivo de Mesa (Docked).
     */
    DESK(Configuration.UI_MODE_TYPE_DESK),

    /**
     * [EN] Projection Device (e.g., Android Auto, Cast).
     *
     * [PT] Dispositivo de Projeção (e.g., Android Auto, Cast).
     */
    APPLIANCE(Configuration.UI_MODE_TYPE_APPLIANCE),

    /**
     * [EN] Virtual Reality (VR) Device.
     *
     * [PT] Dispositivo de Realidade Virtual (VR).
     */
    @RequiresApi(Build.VERSION_CODES.O)
    VR_HEADSET(Configuration.UI_MODE_TYPE_VR_HEADSET),

    /**
     * [EN] Any unspecified/other UI mode.
     *
     * [PT] Qualquer modo de UI não especificado/outros.
     */
    UNDEFINED(Configuration.UI_MODE_TYPE_UNDEFINED);

    companion object {
        /**
         * [EN] Returns the [UiModeType] corresponding to the [Configuration.uiMode] value.
         *
         * [PT] Retorna o [UiModeType] correspondente ao valor de [Configuration.uiMode].
         */
        fun fromConfiguration(uiMode: Int): UiModeType {
            // [EN] The mask is used to extract only the UI Mode TYPE, ignoring night/other flags.
            // [PT] A máscara é usada para extrair apenas o TIPO do UI Mode, ignorando flags noturnas/outras.
            val type = uiMode and Configuration.UI_MODE_TYPE_MASK
            return entries.firstOrNull { it.configValue == type } ?: NORMAL // Returns NORMAL as default
        }
    }
}