/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Extension Functions for Code
 *
 * Description:
 * Extension functions and convenience methods for AppDimens Android Code library,
 * providing a fluent API with facilitators for all scaling strategies.
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
package com.appdimens.dynamic.code.extensions

import android.content.res.Resources
import com.appdimens.dynamic.code.AppDimens
import com.appdimens.dynamic.core.models.PhysicalUnitsCore
import com.appdimens.dynamic.core.strategy.ElementType
import com.appdimens.library.ScreenType

// ============================================
// EXISTING FACILITATORS (Backward Compatibility)
// ============================================

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

// ============================================
// NEW FACILITATORS FOR ALL STRATEGIES
// ============================================

// MARK: - BALANCED Strategy Extensions

/**
 * [EN] Builds adjusted value using BALANCED strategy (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia BALANCED (padrões: LOWEST, multiWindow=true).
 */
val Float.balancedDp: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using BALANCED strategy (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia BALANCED (padrões: HIGHEST, multiWindow=true).
 */
val Float.balancedHdp: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using BALANCED strategy (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia BALANCED (padrões: LOWEST, multiWindow=true).
 */
val Int.balancedDp: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using BALANCED strategy (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia BALANCED (padrões: HIGHEST, multiWindow=true).
 */
val Int.balancedHdp: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - LOGARITHMIC Strategy Extensions

/**
 * [EN] Builds adjusted value using LOGARITHMIC strategy (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia LOGARITHMIC (padrões: LOWEST, multiWindow=true).
 */
val Float.logarithmicDp: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using LOGARITHMIC strategy (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia LOGARITHMIC (padrões: HIGHEST, multiWindow=true).
 */
val Float.logarithmicHdp: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using LOGARITHMIC strategy (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia LOGARITHMIC (padrões: LOWEST, multiWindow=true).
 */
val Int.logarithmicDp: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using LOGARITHMIC strategy (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia LOGARITHMIC (padrões: HIGHEST, multiWindow=true).
 */
val Int.logarithmicHdp: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - POWER Strategy Extensions

/**
 * [EN] Builds adjusted value using POWER strategy (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia POWER (padrões: LOWEST, multiWindow=true).
 */
val Float.powerDp: Float
    get() = AppDimens.from(this).power().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using POWER strategy (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia POWER (padrões: HIGHEST, multiWindow=true).
 */
val Float.powerHdp: Float
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using POWER strategy (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia POWER (padrões: LOWEST, multiWindow=true).
 */
val Int.powerDp: Float
    get() = AppDimens.from(this).power().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using POWER strategy (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia POWER (padrões: HIGHEST, multiWindow=true).
 */
val Int.powerHdp: Float
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - INTERPOLATED Strategy Extensions

/**
 * [EN] Builds adjusted value using INTERPOLATED strategy (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia INTERPOLATED (padrões: LOWEST, multiWindow=true).
 */
val Float.interpolatedDp: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using INTERPOLATED strategy (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia INTERPOLATED (padrões: HIGHEST, multiWindow=true).
 */
val Float.interpolatedHdp: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using INTERPOLATED strategy (defaults: LOWEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia INTERPOLATED (padrões: LOWEST, multiWindow=true).
 */
val Int.interpolatedDp: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using INTERPOLATED strategy (defaults: HIGHEST, multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia INTERPOLATED (padrões: HIGHEST, multiWindow=true).
 */
val Int.interpolatedHdp: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - DIAGONAL Strategy Extensions

/**
 * [EN] Builds adjusted value using DIAGONAL strategy (defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia DIAGONAL (padrões: multiWindow=true).
 */
val Float.diagonalDp: Float
    get() = AppDimens.from(this).diagonal().multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using DIAGONAL strategy (defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia DIAGONAL (padrões: multiWindow=true).
 */
val Int.diagonalDp: Float
    get() = AppDimens.from(this).diagonal().multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - PERIMETER Strategy Extensions

/**
 * [EN] Builds adjusted value using PERIMETER strategy (defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia PERIMETER (padrões: multiWindow=true).
 */
val Float.perimeterDp: Float
    get() = AppDimens.from(this).perimeter().multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using PERIMETER strategy (defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia PERIMETER (padrões: multiWindow=true).
 */
val Int.perimeterDp: Float
    get() = AppDimens.from(this).perimeter().multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - FIT Strategy Extensions

/**
 * [EN] Builds adjusted value using FIT strategy (defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia FIT (padrões: multiWindow=true).
 */
val Float.fitDp: Float
    get() = AppDimens.from(this).fit().multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using FIT strategy (defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia FIT (padrões: multiWindow=true).
 */
val Int.fitDp: Float
    get() = AppDimens.from(this).fit().multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - FILL Strategy Extensions

/**
 * [EN] Builds adjusted value using FILL strategy (defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia FILL (padrões: multiWindow=true).
 */
val Float.fillDp: Float
    get() = AppDimens.from(this).fill().multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using FILL strategy (defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia FILL (padrões: multiWindow=true).
 */
val Int.fillDp: Float
    get() = AppDimens.from(this).fill().multiViewAdjustment(true).toDp(Resources.getSystem())

// MARK: - NONE Strategy Extensions

/**
 * [EN] Builds adjusted value using NONE strategy (no scaling, defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia NONE (sem escalonamento, padrões: multiWindow=true).
 */
val Float.noneDp: Float
    get() = AppDimens.from(this).none().multiViewAdjustment(true).toDp(Resources.getSystem())

/**
 * [EN] Builds adjusted value using NONE strategy (no scaling, defaults: multiWindow=true).
 * [PT] Constrói valor ajustado usando estratégia NONE (sem escalonamento, padrões: multiWindow=true).
 */
val Int.noneDp: Float
    get() = AppDimens.from(this).none().multiViewAdjustment(true).toDp(Resources.getSystem())

// ============================================
// PHYSICAL UNITS EXTENSIONS
// ============================================

/**
 * [EN] Converts millimeters to Dp.
 * [PT] Converte milímetros para Dp.
 */
val Float.mm: Float
    get() = PhysicalUnitsCore.toDpFromMm(this, Resources.getSystem())

/**
 * [EN] Converts centimeters to Dp.
 * [PT] Converte centímetros para Dp.
 */
val Float.cm: Float
    get() = PhysicalUnitsCore.toDpFromCm(this, Resources.getSystem())

/**
 * [EN] Converts inches to Dp.
 * [PT] Converte polegadas para Dp.
 */
val Float.inch: Float
    get() = PhysicalUnitsCore.toDpFromInch(this, Resources.getSystem())

/**
 * [EN] Converts millimeters to Dp.
 * [PT] Converte milímetros para Dp.
 */
val Int.mm: Float
    get() = PhysicalUnitsCore.toDpFromMm(this.toFloat(), Resources.getSystem())

/**
 * [EN] Converts centimeters to Dp.
 * [PT] Converte centímetros para Dp.
 */
val Int.cm: Float
    get() = PhysicalUnitsCore.toDpFromCm(this.toFloat(), Resources.getSystem())

/**
 * [EN] Converts inches to Dp.
 * [PT] Converte polegadas para Dp.
 */
val Int.inch: Float
    get() = PhysicalUnitsCore.toDpFromInch(this.toFloat(), Resources.getSystem())

// ============================================
// PERCENTAGE-BASED EXTENSIONS
// ============================================

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

// ============================================
// FLUENT API EXTENSIONS
// ============================================

/**
 * [EN] Creates AppDimens builder with DEFAULT strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia DEFAULT (API fluida).
 */
fun Float.fixed(): AppDimens {
    return AppDimens.fixed(this)
}

/**
 * [EN] Creates AppDimens builder with PERCENTAGE strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia PERCENTAGE (API fluida).
 */
fun Float.dynamic(): AppDimens {
    return AppDimens.dynamic(this)
}

/**
 * [EN] Creates AppDimens builder with DEFAULT strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia DEFAULT (API fluida).
 */
fun Int.fixed(): AppDimens {
    return AppDimens.fixed(this)
}

/**
 * [EN] Creates AppDimens builder with PERCENTAGE strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia PERCENTAGE (API fluida).
 */
fun Int.dynamic(): AppDimens {
    return AppDimens.dynamic(this)
}

// ============================================
// STRATEGY-SPECIFIC FLUENT API EXTENSIONS
// ============================================

/**
 * [EN] Creates AppDimens builder with BALANCED strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia BALANCED (API fluida).
 */
fun Float.balanced(): AppDimens {
    return AppDimens.from(this).balanced()
}

/**
 * [EN] Creates AppDimens builder with LOGARITHMIC strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia LOGARITHMIC (API fluida).
 */
fun Float.logarithmic(): AppDimens {
    return AppDimens.from(this).logarithmic()
}

/**
 * [EN] Creates AppDimens builder with POWER strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia POWER (API fluida).
 */
fun Float.power(): AppDimens {
    return AppDimens.from(this).power()
}

/**
 * [EN] Creates AppDimens builder with INTERPOLATED strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia INTERPOLATED (API fluida).
 */
fun Float.interpolated(): AppDimens {
    return AppDimens.from(this).interpolated()
}

/**
 * [EN] Creates AppDimens builder with DIAGONAL strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia DIAGONAL (API fluida).
 */
fun Float.diagonal(): AppDimens {
    return AppDimens.from(this).diagonal()
}

/**
 * [EN] Creates AppDimens builder with PERIMETER strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia PERIMETER (API fluida).
 */
fun Float.perimeter(): AppDimens {
    return AppDimens.from(this).perimeter()
}

/**
 * [EN] Creates AppDimens builder with FIT strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia FIT (API fluida).
 */
fun Float.fit(): AppDimens {
    return AppDimens.from(this).fit()
}

/**
 * [EN] Creates AppDimens builder with FILL strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia FILL (API fluida).
 */
fun Float.fill(): AppDimens {
    return AppDimens.from(this).fill()
}

/**
 * [EN] Creates AppDimens builder with NONE strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia NONE (API fluida).
 */
fun Float.none(): AppDimens {
    return AppDimens.from(this).none()
}

/**
 * [EN] Creates AppDimens builder with BALANCED strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia BALANCED (API fluida).
 */
fun Int.balanced(): AppDimens {
    return AppDimens.from(this).balanced()
}

/**
 * [EN] Creates AppDimens builder with LOGARITHMIC strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia LOGARITHMIC (API fluida).
 */
fun Int.logarithmic(): AppDimens {
    return AppDimens.from(this).logarithmic()
}

/**
 * [EN] Creates AppDimens builder with POWER strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia POWER (API fluida).
 */
fun Int.power(): AppDimens {
    return AppDimens.from(this).power()
}

/**
 * [EN] Creates AppDimens builder with INTERPOLATED strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia INTERPOLATED (API fluida).
 */
fun Int.interpolated(): AppDimens {
    return AppDimens.from(this).interpolated()
}

/**
 * [EN] Creates AppDimens builder with DIAGONAL strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia DIAGONAL (API fluida).
 */
fun Int.diagonal(): AppDimens {
    return AppDimens.from(this).diagonal()
}

/**
 * [EN] Creates AppDimens builder with PERIMETER strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia PERIMETER (API fluida).
 */
fun Int.perimeter(): AppDimens {
    return AppDimens.from(this).perimeter()
}

/**
 * [EN] Creates AppDimens builder with FIT strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia FIT (API fluida).
 */
fun Int.fit(): AppDimens {
    return AppDimens.from(this).fit()
}

/**
 * [EN] Creates AppDimens builder with FILL strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia FILL (API fluida).
 */
fun Int.fill(): AppDimens {
    return AppDimens.from(this).fill()
}

/**
 * [EN] Creates AppDimens builder with NONE strategy (fluent API).
 * [PT] Cria construtor AppDimens com estratégia NONE (API fluida).
 */
fun Int.none(): AppDimens {
    return AppDimens.from(this).none()
}

// ============================================
// TEXT SIZE FACILITATORS (.sp variants)
// ============================================

// MARK: - BALANCED Strategy .sp Extensions

/**
 * [EN] BALANCED strategy for text sizing (sp) - LOWEST dimension.
 * [PT] Estratégia BALANCED para tamanho de texto (sp) - dimensão LOWEST.
 */
val Float.balancedSp: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] BALANCED strategy for text sizing (sp) - HIGHEST dimension.
 * [PT] Estratégia BALANCED para tamanho de texto (sp) - dimensão HIGHEST.
 */
val Float.balancedHsp: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] BALANCED strategy for text sizing (sp) - LOWEST dimension (Int).
 * [PT] Estratégia BALANCED para tamanho de texto (sp) - dimensão LOWEST (Int).
 */
val Int.balancedSp: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] BALANCED strategy for text sizing (sp) - HIGHEST dimension (Int).
 * [PT] Estratégia BALANCED para tamanho de texto (sp) - dimensão HIGHEST (Int).
 */
val Int.balancedHsp: Float
    get() = AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).toSp(Resources.getSystem())

// MARK: - LOGARITHMIC Strategy .sp Extensions

/**
 * [EN] LOGARITHMIC strategy for text sizing (sp) - LOWEST dimension.
 * [PT] Estratégia LOGARITHMIC para tamanho de texto (sp) - dimensão LOWEST.
 */
val Float.logarithmicSp: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.LOWEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] LOGARITHMIC strategy for text sizing (sp) - HIGHEST dimension.
 * [PT] Estratégia LOGARITHMIC para tamanho de texto (sp) - dimensão HIGHEST.
 */
val Float.logarithmicHsp: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] LOGARITHMIC strategy for text sizing (sp) - LOWEST dimension (Int).
 * [PT] Estratégia LOGARITHMIC para tamanho de texto (sp) - dimensão LOWEST (Int).
 */
val Int.logarithmicSp: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.LOWEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] LOGARITHMIC strategy for text sizing (sp) - HIGHEST dimension (Int).
 * [PT] Estratégia LOGARITHMIC para tamanho de texto (sp) - dimensão HIGHEST (Int).
 */
val Int.logarithmicHsp: Float
    get() = AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).toSp(Resources.getSystem())

// MARK: - POWER Strategy .sp Extensions

/**
 * [EN] POWER strategy for text sizing (sp) - LOWEST dimension.
 * [PT] Estratégia POWER para tamanho de texto (sp) - dimensão LOWEST.
 */
val Float.powerSp: Float
    get() = AppDimens.from(this).power().type(ScreenType.LOWEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] POWER strategy for text sizing (sp) - HIGHEST dimension.
 * [PT] Estratégia POWER para tamanho de texto (sp) - dimensão HIGHEST.
 */
val Float.powerHsp: Float
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] POWER strategy for text sizing (sp) - LOWEST dimension (Int).
 * [PT] Estratégia POWER para tamanho de texto (sp) - dimensão LOWEST (Int).
 */
val Int.powerSp: Float
    get() = AppDimens.from(this).power().type(ScreenType.LOWEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] POWER strategy for text sizing (sp) - HIGHEST dimension (Int).
 * [PT] Estratégia POWER para tamanho de texto (sp) - dimensão HIGHEST (Int).
 */
val Int.powerHsp: Float
    get() = AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).toSp(Resources.getSystem())

// MARK: - INTERPOLATED Strategy .sp Extensions

/**
 * [EN] INTERPOLATED strategy for text sizing (sp) - LOWEST dimension.
 * [PT] Estratégia INTERPOLATED para tamanho de texto (sp) - dimensão LOWEST.
 */
val Float.interpolatedSp: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] INTERPOLATED strategy for text sizing (sp) - HIGHEST dimension.
 * [PT] Estratégia INTERPOLATED para tamanho de texto (sp) - dimensão HIGHEST.
 */
val Float.interpolatedHsp: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] INTERPOLATED strategy for text sizing (sp) - LOWEST dimension (Int).
 * [PT] Estratégia INTERPOLATED para tamanho de texto (sp) - dimensão LOWEST (Int).
 */
val Int.interpolatedSp: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).toSp(Resources.getSystem())

/**
 * [EN] INTERPOLATED strategy for text sizing (sp) - HIGHEST dimension (Int).
 * [PT] Estratégia INTERPOLATED para tamanho de texto (sp) - dimensão HIGHEST (Int).
 */
val Int.interpolatedHsp: Float
    get() = AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).toSp(Resources.getSystem())

// ============================================
// PIXEL FACILITATORS (.px variants)
// ============================================

// MARK: - BALANCED Strategy .px Extensions

/**
 * [EN] BALANCED strategy for pixels (px) - LOWEST dimension.
 * [PT] Estratégia BALANCED para pixels (px) - dimensão LOWEST.
 */
val Float.balancedPx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] BALANCED strategy for pixels (px) - HIGHEST dimension.
 * [PT] Estratégia BALANCED para pixels (px) - dimensão HIGHEST.
 */
val Float.balancedHpx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] BALANCED strategy for pixels (px) - LOWEST dimension (Int).
 * [PT] Estratégia BALANCED para pixels (px) - dimensão LOWEST (Int).
 */
val Int.balancedPx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).balanced().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] BALANCED strategy for pixels (px) - HIGHEST dimension (Int).
 * [PT] Estratégia BALANCED para pixels (px) - dimensão HIGHEST (Int).
 */
val Int.balancedHpx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).balanced().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

// MARK: - LOGARITHMIC Strategy .px Extensions

/**
 * [EN] LOGARITHMIC strategy for pixels (px) - LOWEST dimension.
 * [PT] Estratégia LOGARITHMIC para pixels (px) - dimensão LOWEST.
 */
val Float.logarithmicPx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).logarithmic().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] LOGARITHMIC strategy for pixels (px) - HIGHEST dimension.
 * [PT] Estratégia LOGARITHMIC para pixels (px) - dimensão HIGHEST.
 */
val Float.logarithmicHpx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] LOGARITHMIC strategy for pixels (px) - LOWEST dimension (Int).
 * [PT] Estratégia LOGARITHMIC para pixels (px) - dimensão LOWEST (Int).
 */
val Int.logarithmicPx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).logarithmic().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] LOGARITHMIC strategy for pixels (px) - HIGHEST dimension (Int).
 * [PT] Estratégia LOGARITHMIC para pixels (px) - dimensão HIGHEST (Int).
 */
val Int.logarithmicHpx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).logarithmic().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

// MARK: - POWER Strategy .px Extensions

/**
 * [EN] POWER strategy for pixels (px) - LOWEST dimension.
 * [PT] Estratégia POWER para pixels (px) - dimensão LOWEST.
 */
val Float.powerPx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).power().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] POWER strategy for pixels (px) - HIGHEST dimension.
 * [PT] Estratégia POWER para pixels (px) - dimensão HIGHEST.
 */
val Float.powerHpx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] POWER strategy for pixels (px) - LOWEST dimension (Int).
 * [PT] Estratégia POWER para pixels (px) - dimensão LOWEST (Int).
 */
val Int.powerPx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).power().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] POWER strategy for pixels (px) - HIGHEST dimension (Int).
 * [PT] Estratégia POWER para pixels (px) - dimensão HIGHEST (Int).
 */
val Int.powerHpx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).power().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

// MARK: - INTERPOLATED Strategy .px Extensions

/**
 * [EN] INTERPOLATED strategy for pixels (px) - LOWEST dimension.
 * [PT] Estratégia INTERPOLATED para pixels (px) - dimensão LOWEST.
 */
val Float.interpolatedPx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] INTERPOLATED strategy for pixels (px) - HIGHEST dimension.
 * [PT] Estratégia INTERPOLATED para pixels (px) - dimensão HIGHEST.
 */
val Float.interpolatedHpx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] INTERPOLATED strategy for pixels (px) - LOWEST dimension (Int).
 * [PT] Estratégia INTERPOLATED para pixels (px) - dimensão LOWEST (Int).
 */
val Int.interpolatedPx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).interpolated().type(ScreenType.LOWEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }

/**
 * [EN] INTERPOLATED strategy for pixels (px) - HIGHEST dimension (Int).
 * [PT] Estratégia INTERPOLATED para pixels (px) - dimensão HIGHEST (Int).
 */
val Int.interpolatedHpx: Float
    get() {
        val resources = Resources.getSystem()
        return AppDimens.from(this).interpolated().type(ScreenType.HIGHEST).multiViewAdjustment(true).toDp(resources) * resources.displayMetrics.density
    }
