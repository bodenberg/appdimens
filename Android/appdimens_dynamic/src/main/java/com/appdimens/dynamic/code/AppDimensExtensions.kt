/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * Extensions and convenience methods for AppDimens Android Code library,
 * providing a fluent API similar to Compose implementation.
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
package com.appdimens.dynamic.code

import android.content.res.Resources
import com.appdimens.library.ScreenType

// MARK: - Convenience Extensions for Fixed Dimensions

/**
 * [EN] Builds the adjusted value using fixed dimensions (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói o valor ajustado usando dimensões fixas (padrões: LOWEST, multiWindow=true).
 */
val Float.fxdp: Float
    get() = AppDimens.fixed(this).type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds the adjusted value using fixed dimensions (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói o valor ajustado usando dimensões fixas (padrões: HIGHEST, multiWindow=true).
 */
val Float.fxhdp: Float
    get() = AppDimens.fixed(this).type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds the adjusted value using dynamic dimensions (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói o valor ajustado usando dimensões dinâmicas (padrões: LOWEST, multiWindow=true).
 */
val Float.dydp: Float
    get() = AppDimens.dynamic(this).type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds the adjusted value using dynamic dimensions (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói o valor ajustado usando dimensões dinâmicas (padrões: HIGHEST, multiWindow=true).
 */
val Float.dyhdp: Float
    get() = AppDimens.dynamic(this).type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - Int Extensions

/**
 * [EN] Builds the adjusted value using fixed dimensions (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói o valor ajustado usando dimensões fixas (padrões: LOWEST, multiWindow=true).
 */
val Int.fxdp: Float
    get() = AppDimens.fixed(this).type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds the adjusted value using fixed dimensions (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói o valor ajustado usando dimensões fixas (padrões: HIGHEST, multiWindow=true).
 */
val Int.fxhdp: Float
    get() = AppDimens.fixed(this).type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds the adjusted value using dynamic dimensions (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói o valor ajustado usando dimensões dinâmicas (padrões: LOWEST, multiWindow=true).
 */
val Int.dydp: Float
    get() = AppDimens.dynamic(this).type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds the adjusted value using dynamic dimensions (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói o valor ajustado usando dimensões dinâmicas (padrões: HIGHEST, multiWindow=true).
 */
val Int.dyhdp: Float
    get() = AppDimens.dynamic(this).type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - Physical Units Extensions

/**
 * [EN] Converts millimeters to Dp.
 * [PT] Converte milímetros para Dp.
 */
val Float.mm: Float
    get() = AppDimensPhysicalUnits.toDpFromMm(this, Resources.getSystem())

/**
 * [EN] Converts centimeters to Dp.
 * [PT] Converte centímetros para Dp.
 */
val Float.cm: Float
    get() = AppDimensPhysicalUnits.toDpFromCm(this, Resources.getSystem())

/**
 * [EN] Converts inches to Dp.
 * [PT] Converte polegadas para Dp.
 */
val Float.inch: Float
    get() = AppDimensPhysicalUnits.toDpFromInch(this, Resources.getSystem())

/**
 * [EN] Converts millimeters to Dp.
 * [PT] Converte milímetros para Dp.
 */
val Int.mm: Float
    get() = AppDimensPhysicalUnits.toDpFromMm(this.toFloat(), Resources.getSystem())

/**
 * [EN] Converts centimeters to Dp.
 * [PT] Converte centímetros para Dp.
 */
val Int.cm: Float
    get() = AppDimensPhysicalUnits.toDpFromCm(this.toFloat(), Resources.getSystem())

/**
 * [EN] Converts inches to Dp.
 * [PT] Converte polegadas para Dp.
 */
val Int.inch: Float
    get() = AppDimensPhysicalUnits.toDpFromInch(this.toFloat(), Resources.getSystem())

// MARK: - Percentage-Based Extensions

/**
 * [EN] Calculates a dynamic dimension value based on a percentage of the screen dimension.
 * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem da dimensão da tela.
 */
fun Float.dynamicPer(type: ScreenType = ScreenType.LOWEST): Float {
    return AppDimens.dynamicPercentageDp(this, type, Resources.getSystem())
}

/**
 * [EN] Calculates a dynamic dimension value based on a percentage and converts it to pixels.
 * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para pixels.
 */
fun Float.dynamicPerPx(type: ScreenType = ScreenType.LOWEST): Float {
    return AppDimens.dynamicPercentagePx(this, type, Resources.getSystem())
}

/**
 * [EN] Calculates a dynamic dimension value based on a percentage and converts it to SP.
 * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para SP.
 */
fun Float.dynamicPerSp(type: ScreenType = ScreenType.LOWEST): Float {
    return AppDimens.dynamicPercentageSp(this, type, Resources.getSystem())
}

// MARK: - API Fluida

/**
 * [EN] Creates a fixed dimension builder with fluent API.
 * [PT] Cria um construtor de dimensão fixa com API fluida.
 */
fun Float.fixed(): AppDimensFixed {
    return AppDimens.fixed(this)
}

/**
 * [EN] Creates a dynamic dimension builder with fluent API.
 * [PT] Cria um construtor de dimensão dinâmica com API fluida.
 */
fun Float.dynamic(): AppDimensDynamic {
    return AppDimens.dynamic(this)
}

/**
 * [EN] Creates a fixed dimension builder with fluent API.
 * [PT] Cria um construtor de dimensão fixa com API fluida.
 */
fun Int.fixed(): AppDimensFixed {
    return AppDimens.fixed(this)
}

/**
 * [EN] Creates a dynamic dimension builder with fluent API.
 * [PT] Cria um construtor de dimensão dinâmica com API fluida.
 */
fun Int.dynamic(): AppDimensDynamic {
    return AppDimens.dynamic(this)
}
