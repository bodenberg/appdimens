/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * Physical units conversion utilities for AppDimens Android Code library,
 * providing conversion between physical measurements and Dp/Px.
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
import android.util.TypedValue
import com.appdimens.library.UnitType

/**
 * [EN] Utility class for physical unit conversions.
 * [PT] Classe utilitária para conversões de unidades físicas.
 */
object AppDimensPhysicalUnits {

    // MARK: - Constants

    /**
     * [EN] Points per inch (standard resolution).
     * [PT] Pontos por polegada (resolução padrão).
     */
    const val POINTS_PER_INCH = 72.0f

    /**
     * [EN] Points per centimeter.
     * [PT] Pontos por centímetro.
     */
    const val POINTS_PER_CM = POINTS_PER_INCH / 2.54f

    /**
     * [EN] Points per millimeter.
     * [PT] Pontos por milímetro.
     */
    const val POINTS_PER_MM = POINTS_PER_CM / 10.0f

    // MARK: - Conversion Methods

    /**
     * [EN] Converts millimeters to Dp.
     * @param mm The value in millimeters.
     * @param resources The Context's Resources.
     * @return The value in Dp.
     * [PT] Converte milímetros para Dp.
     * @param mm O valor em milímetros.
     * @param resources Os Resources do Context.
     * @return O valor em Dp.
     */
    fun toDpFromMm(mm: Float, resources: Resources): Float {
        val points = mm * POINTS_PER_MM
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PT, points, resources.displayMetrics
        )
    }

    /**
     * [EN] Converts centimeters to Dp.
     * @param cm The value in centimeters.
     * @param resources The Context's Resources.
     * @return The value in Dp.
     * [PT] Converte centímetros para Dp.
     * @param cm O valor em centímetros.
     * @param resources Os Resources do Context.
     * @return O valor em Dp.
     */
    fun toDpFromCm(cm: Float, resources: Resources): Float {
        val points = cm * POINTS_PER_CM
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PT, points, resources.displayMetrics
        )
    }

    /**
     * [EN] Converts inches to Dp.
     * @param inch The value in inches.
     * @param resources The Context's Resources.
     * @return The value in Dp.
     * [PT] Converte polegadas para Dp.
     * @param inch O valor em polegadas.
     * @param resources Os Resources do Context.
     * @return O valor em Dp.
     */
    fun toDpFromInch(inch: Float, resources: Resources): Float {
        val points = inch * POINTS_PER_INCH
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PT, points, resources.displayMetrics
        )
    }

    /**
     * [EN] Converts millimeters to Pixels.
     * @param mm The value in millimeters.
     * @param resources The Context's Resources.
     * @return The value in Pixels.
     * [PT] Converte milímetros para Pixels.
     * @param mm O valor em milímetros.
     * @param resources Os Resources do Context.
     * @return O valor em Pixels.
     */
    fun toPxFromMm(mm: Float, resources: Resources): Float {
        val dp = toDpFromMm(mm, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
        )
    }

    /**
     * [EN] Converts centimeters to Pixels.
     * @param cm The value in centimeters.
     * @param resources The Context's Resources.
     * @return The value in Pixels.
     * [PT] Converte centímetros para Pixels.
     * @param cm O valor em centímetros.
     * @param resources Os Resources do Context.
     * @return O valor em Pixels.
     */
    fun toPxFromCm(cm: Float, resources: Resources): Float {
        val dp = toDpFromCm(cm, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
        )
    }

    /**
     * [EN] Converts inches to Pixels.
     * @param inch The value in inches.
     * @param resources The Context's Resources.
     * @return The value in Pixels.
     * [PT] Converte polegadas para Pixels.
     * @param inch O valor em polegadas.
     * @param resources Os Resources do Context.
     * @return O valor em Pixels.
     */
    fun toPxFromInch(inch: Float, resources: Resources): Float {
        val dp = toDpFromInch(inch, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics
        )
    }

    /**
     * [EN] Converts millimeters to SP.
     * @param mm The value in millimeters.
     * @param resources The Context's Resources.
     * @return The value in SP.
     * [PT] Converte milímetros para SP.
     * @param mm O valor em milímetros.
     * @param resources Os Resources do Context.
     * @return O valor em SP.
     */
    fun toSpFromMm(mm: Float, resources: Resources): Float {
        val dp = toDpFromMm(mm, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dp, resources.displayMetrics
        )
    }

    /**
     * [EN] Converts centimeters to SP.
     * @param cm The value in centimeters.
     * @param resources The Context's Resources.
     * @return The value in SP.
     * [PT] Converte centímetros para SP.
     * @param cm O valor em centímetros.
     * @param resources Os Resources do Context.
     * @return O valor em SP.
     */
    fun toSpFromCm(cm: Float, resources: Resources): Float {
        val dp = toDpFromCm(cm, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dp, resources.displayMetrics
        )
    }

    /**
     * [EN] Converts inches to SP.
     * @param inch The value in inches.
     * @param resources The Context's Resources.
     * @return The value in SP.
     * [PT] Converte polegadas para SP.
     * @param inch O valor em polegadas.
     * @param resources Os Resources do Context.
     * @return O valor em SP.
     */
    fun toSpFromInch(inch: Float, resources: Resources): Float {
        val dp = toDpFromInch(inch, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dp, resources.displayMetrics
        )
    }

    // MARK: - Utility Methods

    /**
     * [EN] Converts a diameter value in a specific physical unit to radius in Dp.
     * @param diameter The diameter value.
     * @param unitType The unit type (mm, cm, inch).
     * @param resources The Context's Resources.
     * @return The radius in Dp.
     * [PT] Converte um valor de diâmetro em uma unidade física específica para raio em Dp.
     * @param diameter O valor do diâmetro.
     * @param unitType O tipo de unidade (mm, cm, inch).
     * @param resources Os Resources do Context.
     * @return O raio em Dp.
     */
    fun radiusFromDiameter(diameter: Float, unitType: UnitType, resources: Resources): Float {
        val diameterInDp = when (unitType) {
            UnitType.MM -> toDpFromMm(diameter, resources)
            UnitType.CM -> toDpFromCm(diameter, resources)
            UnitType.INCH -> toDpFromInch(diameter, resources)
            UnitType.DP -> diameter
            else -> diameter
        }
        
        return diameterInDp / 2.0f
    }

    /**
     * [EN] Converts a circumference value in a specific physical unit to radius in Dp.
     * @param circumference The circumference value.
     * @param unitType The unit type (mm, cm, inch).
     * @param resources The Context's Resources.
     * @return The radius in Dp.
     * [PT] Converte um valor de circunferência em uma unidade física específica para raio em Dp.
     * @param circumference O valor da circunferência.
     * @param unitType O tipo de unidade (mm, cm, inch).
     * @param resources Os Resources do Context.
     * @return O raio em Dp.
     */
    fun radiusFromCircumference(circumference: Float, unitType: UnitType, resources: Resources): Float {
        val circumferenceInDp = when (unitType) {
            UnitType.MM -> toDpFromMm(circumference, resources)
            UnitType.CM -> toDpFromCm(circumference, resources)
            UnitType.INCH -> toDpFromInch(circumference, resources)
            UnitType.DP -> circumference
            else -> circumference
        }
        
        return circumferenceInDp / (2.0f * kotlin.math.PI.toFloat())
    }

    // MARK: - Conversion Extensions

    /**
     * [EN] Float extension to convert MM to CM.
     * [PT] Extensão de Float para converter MM para CM.
     */
    fun Float.mmToCm(): Float = this / 10.0f

    /**
     * [EN] Float extension to convert MM to Inch.
     * [PT] Extensão de Float para converter MM para Inch.
     */
    fun Float.mmToInch(): Float = this / 25.4f

    /**
     * [EN] Float extension to convert CM to MM.
     * [PT] Extensão de Float para converter CM para MM.
     */
    fun Float.cmToMm(): Float = this * 10.0f

    /**
     * [EN] Float extension to convert CM to Inch.
     * [PT] Extensão de Float para converter CM para Inch.
     */
    fun Float.cmToInch(): Float = this / 2.54f

    /**
     * [EN] Float extension to convert Inch to MM.
     * [PT] Extensão de Float para converter Inch para MM.
     */
    fun Float.inchToMm(): Float = this * 25.4f

    /**
     * [EN] Float extension to convert Inch to CM.
     * [PT] Extensão de Float para converter Inch para CM.
     */
    fun Float.inchToCm(): Float = this * 2.54f
}