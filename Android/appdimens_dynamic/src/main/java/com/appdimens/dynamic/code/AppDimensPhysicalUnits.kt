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
package com.appdimens.dynamic.code

import android.content.res.Resources
import android.util.TypedValue
import com.appdimens.library.UnitType

/**
 * [EN] A singleton object that provides functions for physical unit conversions (MM, CM, Inch)
 * and measurement utilities.
 *
 * [PT] Objeto singleton que fornece funções para conversão de unidades físicas (MM, CM, Inch)
 * e utilitários de medição.
 */
object AppDimensPhysicalUnits {

    /**
     * [EN] Conversion factor from millimeters to centimeters.
     * [PT] Fator de conversão de milímetros para centímetros.
     */
    private const val MM_TO_CM_FACTOR = 10.0f

    /**
     * [EN] Conversion factor from millimeters to inches.
     * [PT] Fator de conversão de milímetros para polegadas.
     */
    private const val MM_TO_INCH_FACTOR = 25.4f


    // --- Millimeters (MM) ---

    /**
     * [EN] Converts Millimeters (MM) to Pixels (PX).
     * [PT] Converte Milímetros (MM) para Pixels (PX).
     */
    fun toMm(mm: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, resources.displayMetrics)

    /**
     * [EN] Converts Millimeters (MM) to Centimeters (CM).
     * [PT] Converte Milímetros (MM) para Centímetros (CM).
     */
    fun convertMmToCm(mm: Float): Float = mm / MM_TO_CM_FACTOR

    /**
     * [EN] Converts Millimeters (MM) to Inches.
     * [PT] Converte Milímetros (MM) para Polegadas (Inch).
     */
    fun convertMmToInch(mm: Float): Float = mm / MM_TO_INCH_FACTOR

    /**
     * [EN] Extension function for Float to convert MM to CM.
     * [PT] Função de extensão para Float para converter MM para CM.
     */
    fun Float.mmToCm(): Float = convertMmToCm(this)
    /**
     * [EN] Extension function for Int to convert MM to CM.
     * [PT] Função de extensão para Int para converter MM para CM.
     */
    fun Int.mmToCm(): Float = convertMmToCm(this.toFloat())

    /**
     * [EN] Extension function for Float to convert MM to Inches.
     * [PT] Função de extensão para Float para converter MM para Polegadas.
     */
    fun Float.mmToInch(): Float = convertMmToInch(this)

    /**
     * [EN] Extension function for Int to convert MM to Inches.
     * [PT] Função de extensão para Int para converter MM para Polegadas.
     */
    fun Int.mmToInch(): Float = convertMmToInch(this.toFloat())


    // --- Centimeters (CM) ---

    /**
     * [EN] Converts Centimeters (CM) to Pixels (PX).
     * [PT] Converte Centímetros (CM) para Pixels (PX).
     */
    fun toCm(cm: Float, resources: Resources): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_MM, cm * MM_TO_CM_FACTOR, resources.displayMetrics
    )

    /**
     * [EN] Converts Centimeters (CM) to Millimeters (MM).
     * [PT] Converte Centímetros (CM) para Milímetros (MM).
     */
    fun convertCmToMm(cm: Float): Float = cm * MM_TO_CM_FACTOR

    /**
     * [EN] Converts Centimeters (CM) to Inches.
     * [PT] Converte Centímetros (CM) para Polegadas (Inch).
     */
    fun convertCmToInch(cm: Float): Float = cm / (MM_TO_INCH_FACTOR / MM_TO_CM_FACTOR)

    /**
     * [EN] Extension function for Float to convert CM to MM.
     * [PT] Função de extensão para Float para converter CM para MM.
     */
    fun Float.cmToMm(): Float = convertCmToMm(this)

    /**
     * [EN] Extension function for Int to convert CM to MM.
     * [PT] Função de extensão para Int para converter CM para MM.
     */
    fun Int.cmToMm(): Float = convertCmToMm(this.toFloat())

    /**
     * [EN] Extension function for Float to convert CM to Inches.
     * [PT] Função de extensão para Float para converter CM para Polegadas.
     */
    fun Float.cmToInch(): Float = convertCmToInch(this)

    /**
     * [EN] Extension function for Int to convert CM to Inches.
     * [PT] Função de extensão para Int para converter CM para Polegadas.
     */
    fun Int.cmToInch(): Float = convertCmToInch(this.toFloat())


    // --- Inches (INCH) ---

    /**
     * [EN] Converts Inches to Pixels (PX).
     * [PT] Converte Polegadas (Inch) para Pixels (PX).
     */
    fun toInch(inches: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inches, resources.displayMetrics)

    /**
     * [EN] Converts Inches to Centimeters (CM).
     * [PT] Converte Polegadas (Inch) para Centímetros (CM).
     */
    fun convertInchToCm(inch: Float): Float = inch * 2.54f

    /**
     * [EN] Converts Inches to Millimeters (MM).
     * [PT] Converte Polegadas (Inch) para Milímetros (MM).
     */
    fun convertInchToMm(inch: Float): Float = inch * MM_TO_INCH_FACTOR

    /**
     * [EN] Extension function for Float to convert Inches to CM.
     * [PT] Função de extensão para Float para converter Polegadas para CM.
     */
    fun Float.inchToCm(): Float = convertInchToCm(this)

    /**
     * [EN] Extension function for Int to convert Inches to CM.
     * [PT] Função de extensão para Int para converter Polegadas para CM.
     */
    fun Int.inchToCm(): Float = convertInchToCm(this.toFloat())

    /**
     * [EN] Extension function for Float to convert Inches to MM.
     * [PT] Função de extensão para Float para converter Polegadas para MM.
     */
    fun Float.inchToMm(): Float = convertInchToMm(this)

    /**
     * [EN] Extension function for Int to convert Inches to MM.
     * [PT] Função de extensão para Int para converter Polegadas para MM.
     */
    fun Int.inchToMm(): Float = convertInchToMm(this.toFloat())


    // --- Measurement Utilities ---

    /**
     * [EN] Converts a diameter value in a specific physical unit to a radius in Pixels (PX).
     *
     * Note: The use of sp/dp in this context should be replaced by TypedValue.applyDimension
     * for the View System.
     *
     * [PT] Converte um valor de diâmetro (Diameter) em uma unidade física específica para Raio (Radius) em Pixels (PX).
     *
     * OBS: O uso de sp/dp neste contexto deve ser substituído por TypedValue.applyDimension
     * para View System.
     */
    fun radius(diameter: Float, type: UnitType, resources: Resources): Float {
        val valueInPx = when (type) {
            UnitType.INCH -> toInch(diameter, resources)
            UnitType.CM -> toCm(diameter, resources)
            UnitType.MM -> toMm(diameter, resources)
            UnitType.SP -> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, diameter, resources.displayMetrics)
            UnitType.DP -> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, diameter, resources.displayMetrics)
            else -> diameter
        }
        return valueInPx / 2.0f
    }

    /**
     * [EN] Calculates the size of 1 unit (1.0f) in Pixels (PX) for a specific physical unit.
     *
     * Note: The use of sp/dp in this context should be replaced by TypedValue.applyDimension
     * for the View System.
     *
     * [PT] Calcula o tamanho de 1 unidade (1.0f) em Pixels (PX) para uma unidade física específica.
     *
     * OBS: O uso de sp/dp neste contexto deve ser substituído por TypedValue.applyDimension
     * para View System.
     */
    fun unitSizePerPx(type: UnitType, resources: Resources): Float =
        when (type) {
            UnitType.INCH -> toInch(1.0f, resources)
            UnitType.CM -> toCm(1.0f, resources)
            UnitType.MM  -> toMm(1.0f, resources)
            UnitType.SP -> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1.0f, resources.displayMetrics)
            UnitType.DP -> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1.0f, resources.displayMetrics)
            else-> 1f
        }

    /**
     * [EN] Adjusts a diameter value to circumference if requested.
     * [PT] Ajusta um valor de diâmetro (Diameter) para Circunferência (Circumference) se solicitado.
     */
    fun displayMeasureDiameter(diameter: Float, isCircumference: Boolean): Float =
        if (isCircumference) (diameter * AppDimensAdjustmentFactors.CIRCUMFERENCE_FACTOR).toFloat() else diameter

    /**
     * [EN] Extension for Float to adjust the measurement to Diameter or Circumference.
     * [PT] Extensão de Float para ajustar a medida para Diâmetro ou Circunferência.
     */
    fun Float.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this, isCircumference)

    /**
     * [EN] Extension for Int to adjust the measurement to Diameter or Circumference.
     * [PT] Extensão de Int para ajustar a medida para Diâmetro ou Circunferência.
     */
    fun Int.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this.toFloat(), isCircumference)
}