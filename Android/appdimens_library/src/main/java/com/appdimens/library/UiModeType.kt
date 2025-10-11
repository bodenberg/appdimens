package com.appdimens.library

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * Author & Developer: Jean Bodenberg
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

/**
 * Define os tipos de modo de interface do usuário (UI Mode Type) do Android
 * para customização de dimensões, com base em [Configuration.uiMode].
 */
enum class UiModeType(val configValue: Int) {
    /** Telefone/Tablet Padrão. */
    NORMAL(Configuration.UI_MODE_TYPE_NORMAL),
    /** Televisão. */
    TELEVISION(Configuration.UI_MODE_TYPE_TELEVISION),
    /** Carro. */
    CAR(Configuration.UI_MODE_TYPE_CAR),
    /** Relógio (Wear OS). */
    WATCH(Configuration.UI_MODE_TYPE_WATCH),
    /** Dispositivo de Mesa (Docked). */
    DESK(Configuration.UI_MODE_TYPE_DESK),
    /** Dispositivo de Projeção (e.g., Android Auto, Cast). */
    APPLIANCE(Configuration.UI_MODE_TYPE_APPLIANCE),
    /** Dispositivo de Realidade Virtual (VR). */
    @RequiresApi(Build.VERSION_CODES.O)
    VR_HEADSET(Configuration.UI_MODE_TYPE_VR_HEADSET),
    /** Qualquer modo de UI não especificado/outros. */
    UNDEFINED(Configuration.UI_MODE_TYPE_UNDEFINED);

    companion object {
        /**
         * Retorna o [UiModeType] correspondente ao valor de [Configuration.uiMode].
         */
        fun fromConfiguration(uiMode: Int): UiModeType {
            // A máscara é usada para extrair apenas o TIPO do UI Mode, ignorando flags noturnas/outras.
            val type = uiMode and Configuration.UI_MODE_TYPE_MASK
            return entries.firstOrNull { it.configValue == type } ?: NORMAL // Retorna NORMAL como padrão
        }
    }
}