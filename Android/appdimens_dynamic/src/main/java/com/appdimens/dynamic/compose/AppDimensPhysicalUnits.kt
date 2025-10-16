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
package com.appdimens.dynamic.compose

import android.content.res.Resources
import android.util.TypedValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.AppDimensAdjustmentFactors.CIRCUMFERENCE_FACTOR
import com.appdimens.library.UnitType

/**
 * [EN] Singleton object providing functions for physical unit conversion (MM, CM, Inch)
 * and measurement utilities.
 *
 * [PT] Objeto singleton que fornece funções para conversão de unidades físicas (MM, CM, Inch)
 * e utilitários de medição.
 */
object AppDimensPhysicalUnits {

    /**
     * [EN] Constants for Physical Unit Conversion.
     *
     * [PT] Constantes para Conversão de Unidades Físicas.
     */
    private const val MM_TO_CM_FACTOR = 10.0f
    private const val MM_TO_INCH_FACTOR = 25.4f

    /**
     * [EN] Millimeters (MM)
     *
     * [PT] Milímetros (MM)
     */

    /**
     * [EN] Converts Millimeters (MM) to Pixels (PX).
     *
     * [PT] Converte Milímetros (MM) para Pixels (PX).
     */
    fun toMm(mm: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, resources.displayMetrics)

    /**
     * [EN] Converts Millimeters (MM) to Centimeters (CM).
     *
     * [PT] Converte Milímetros (MM) para Centímetros (CM).
     */
    fun convertMmToCm(mm: Float): Float = mm / MM_TO_CM_FACTOR

    /**
     * [EN] Converts Millimeters (MM) to Inches (Inch).
     *
     * [PT] Converte Milímetros (MM) para Polegadas (Inch).
     */
    fun convertMmToInch(mm: Float): Float = mm / MM_TO_INCH_FACTOR

    /**
     * [EN] Float extension to convert MM to CM.
     *
     * [PT] Extensão de Float para converter MM para CM.
     */
    fun Float.mmToCm(): Float = convertMmToCm(this)

    /**
     * [EN] Int extension to convert MM to CM.
     *
     * [PT] Extensão de Int para converter MM para CM.
     */
    fun Int.mmToCm(): Float = convertMmToCm(this.toFloat())

    /**
     * [EN] Float extension to convert MM to Inch.
     *
     * [PT] Extensão de Float para converter MM para Inch.
     */
    fun Float.mmToInch(): Float = convertMmToInch(this)

    /**
     * [EN] Int extension to convert MM to Inch.
     *
     * [PT] Extensão de Int para converter MM para Inch.
     */
    fun Int.mmToInch(): Float = convertMmToInch(this.toFloat())

    /**
     * [EN] Centimeters (CM)
     *
     * [PT] Centímetros (CM)
     */

    /**
     * [EN] Converts Centimeters (CM) to Pixels (PX).
     *
     * [PT] Converte Centímetros (CM) para Pixels (PX).
     */
    fun toCm(cm: Float, resources: Resources): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_MM, cm * MM_TO_CM_FACTOR, resources.displayMetrics
    )

    /**
     * [EN] Converts Centimeters (CM) to Millimeters (MM).
     *
     * [PT] Converte Centímetros (CM) para Milímetros (MM).
     */
    fun convertCmToMm(cm: Float): Float = cm * MM_TO_CM_FACTOR

    /**
     * [EN] Converts Centimeters (CM) to Inches (Inch).
     *
     * [PT] Converte Centímetros (CM) para Polegadas (Inch).
     */
    fun convertCmToInch(cm: Float): Float = cm / (MM_TO_INCH_FACTOR / MM_TO_CM_FACTOR)

    /**
     * [EN] Float extension to convert CM to MM.
     *
     * [PT] Extensão de Float para converter CM para MM.
     */
    fun Float.cmToMm(): Float = convertCmToMm(this)

    /**
     * [EN] Int extension to convert CM to MM.
     *
     * [PT] Extensão de Int para converter CM para MM.
     */
    fun Int.cmToMm(): Float = convertCmToMm(this.toFloat())

    /**
     * [EN] Float extension to convert CM to Inch.
     *
     * [PT] Extensão de Float para converter CM para Inch.
     */
    fun Float.cmToInch(): Float = convertCmToInch(this)

    /**
     * [EN] Int extension to convert CM to Inch.
     *
     * [PT] Extensão de Int para converter CM para Inch.
     */
    fun Int.cmToInch(): Float = convertCmToInch(this.toFloat())

    /**
     * [EN] Inches (INCH)
     *
     * [PT] Polegadas (INCH)
     */

    /**
     * [EN] Converts Inches (Inch) to Pixels (PX).
     *
     * [PT] Converte Polegadas (Inch) para Pixels (PX).
     */
    fun toInch(inches: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inches, resources.displayMetrics)

    /**
     * [EN] Converts Inches (Inch) to Centimeters (CM).
     *
     * [PT] Converte Polegadas (Inch) para Centímetros (CM).
     */
    fun convertInchToCm(inch: Float): Float = inch * 2.54f

    /**
     * [EN] Converts Inches (Inch) to Millimeters (MM).
     *
     * [PT] Converte Polegadas (Inch) para Milímetros (MM).
     */
    fun convertInchToMm(inch: Float): Float = inch * MM_TO_INCH_FACTOR

    /**
     * [EN] Float extension to convert Inch to CM.
     *
     * [PT] Extensão de Float para converter Inch para CM.
     */
    fun Float.inchToCm(): Float = convertInchToCm(this)

    /**
     * [EN] Int extension to convert Inch to CM.
     *
     * [PT] Extensão de Int para converter Inch para CM.
     */
    fun Int.inchToCm(): Float = convertInchToCm(this.toFloat())

    /**
     * [EN] Float extension to convert Inch to MM.
     *
     * [PT] Extensão de Float para converter Inch para MM.
     */
    fun Float.inchToMm(): Float = convertInchToMm(this)

    /**
     * [EN] Int extension to convert Inch to MM.
     *
     * [PT] Extensão de Int para converter Inch para MM.
     */
    fun Int.inchToMm(): Float = convertInchToMm(this.toFloat())

    /**
     * [EN] Composable Extensions for Physical Units in PX.
     *
     * [PT] Extensões Composable para Unidades Físicas em PX.
     */

    /**
     * [EN] Float extension to convert MM to PX.
     *
     * [PT] Extensão de Float para converter MM para PX.
     */
    @get:Composable
    val Float.mm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toMm(this@mm, resources) }
        }

    /**
     * [EN] Int extension to convert MM to PX.
     *
     * [PT] Extensão de Int para converter MM para PX.
     */
    @get:Composable
    val Int.mm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toMm(this@mm.toFloat(), resources) }
        }

    /**
     * [EN] Float extension to convert CM to PX.
     *
     * [PT] Extensão de Float para converter CM para PX.
     */
    @get:Composable
    val Float.cm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toCm(this@cm, resources) }
        }

    /**
     * [EN] Int extension to convert CM to PX.
     *
     * [PT] Extensão de Int para converter CM para PX.
     */
    @get:Composable
    val Int.cm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toCm(this@cm.toFloat(), resources) }
        }

    /**
     * [EN] Float extension to convert Inch to PX.
     *
     * [PT] Extensão de Float para converter Inch para PX.
     */
    @get:Composable
    val Float.inch: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toInch(this@inch, resources) }
        }

    /**
     * [EN] Int extension to convert Inch to PX.
     *
     * [PT] Extensão de Int para converter Inch para PX.
     */
    @get:Composable
    val Int.inch: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toInch(this@inch.toFloat(), resources) }
        }

    /**
     * [EN] Measurement Utilities.
     *
     * [PT] Utilitários de Medição.
     */

    /**
     * [EN] Converts a diameter value in a specific physical unit to Radius in Pixels (PX).
     *
     * [PT] Converte um valor de diâmetro em uma unidade física específica para Raio em Pixels (PX).
     */
    fun radius(diameter: Float, type: UnitType, resources: Resources): Float = when (type) {
        UnitType.INCH -> toInch(diameter, resources)
        UnitType.CM -> toCm(diameter, resources)
        UnitType.MM -> toMm(diameter, resources)
        UnitType.SP -> diameter.sp.value
        UnitType.DP -> diameter.dp.value
        else -> diameter
    } / 2.0f

    /**
     * [EN] Float extension to calculate the Radius in Pixels (PX).
     *
     * [PT] Extensão de Float para calcular o Raio em Pixels (PX).
     */
    @Composable
    fun Float.radius(type: UnitType): Float {
        val resources = LocalResources.current
        return with(LocalDensity.current) { radius(this@radius, type, resources) }
    }

    /**
     * [EN] Int extension to calculate the Radius in Pixels (PX).
     *
     * [PT] Extensão de Int para calcular o Raio em Pixels (PX).
     */
    @Composable
    fun Int.radius(type: UnitType): Float {
        val resources = LocalResources.current
        return with(LocalDensity.current) { radius(this@radius.toFloat(), type, resources) }
    }

    /**
     * [EN] Adjusts a diameter value to Circumference if requested.
     *
     * [PT] Ajusta um valor de diâmetro para Circunferência se solicitado.
     */
    fun displayMeasureDiameter(diameter: Float, isCircumference: Boolean): Float =
        if (isCircumference) (diameter * CIRCUMFERENCE_FACTOR).toFloat() else diameter

    /**
     * [EN] Float extension to adjust the measurement for Diameter or Circumference.
     *
     * [PT] Extensão de Float para ajustar a medida para Diâmetro ou Circunferência.
     */
    fun Float.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this, isCircumference)

    /**
     * [EN] Int extension to adjust the measurement for Diameter or Circumference.
     *
     * [PT] Extensão de Int para ajustar a medida para Diâmetro ou Circunferência.
     */
    fun Int.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this.toFloat(), isCircumference)

    /**
     * [EN] Calculates the size of 1 unit (1.0f) in Pixels (PX) for a specific physical unit.
     *
     * [PT] Calcula o tamanho de 1 unidade (1.0f) em Pixels (PX) para uma unidade física específica.
     */
    fun unitSizePerPx(type: UnitType, resources: Resources): Float =
        when (type) {
            UnitType.INCH -> toInch(1.0f, resources)
            UnitType.CM -> toCm(1.0f, resources)
            UnitType.MM  -> toMm(1.0f, resources)
            UnitType.SP -> 1.sp.value
            UnitType.DP -> 1.dp.value
            else-> 1f
        }
}