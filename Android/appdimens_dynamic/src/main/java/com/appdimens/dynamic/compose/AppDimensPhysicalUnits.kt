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
 * Objeto singleton que fornece funções para conversão de unidades físicas (MM, CM, Inch)
 * e utilitários de medição.
 */
object AppDimensPhysicalUnits {

    // --- Constantes para Conversão de Unidades Físicas ---
    private const val MM_TO_CM_FACTOR = 10.0f
    private const val MM_TO_INCH_FACTOR = 25.4f

    // --- Milímetros (MM) ---

    /** Converte Milímetros (MM) para Pixels (PX). */
    fun toMm(mm: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, mm, resources.displayMetrics)

    /** Converte Milímetros (MM) para Centímetros (CM). */
    fun convertMmToCm(mm: Float): Float = mm / MM_TO_CM_FACTOR

    /** Converte Milímetros (MM) para Polegadas (Inch). */
    fun convertMmToInch(mm: Float): Float = mm / MM_TO_INCH_FACTOR

    /** Extensão de Float para converter MM para CM. */
    fun Float.mmToCm(): Float = convertMmToCm(this)
    /** Extensão de Int para converter MM para CM. */
    fun Int.mmToCm(): Float = convertMmToCm(this.toFloat())

    /** Extensão de Float para converter MM para Inch. */
    fun Float.mmToInch(): Float = convertMmToInch(this)
    /** Extensão de Int para converter MM para Inch. */
    fun Int.mmToInch(): Float = convertMmToInch(this.toFloat())

    // --- Centímetros (CM) ---

    /** Converte Centímetros (CM) para Pixels (PX). */
    fun toCm(cm: Float, resources: Resources): Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_MM, cm * MM_TO_CM_FACTOR, resources.displayMetrics
    )

    /** Converte Centímetros (CM) para Milímetros (MM). */
    fun convertCmToMm(cm: Float): Float = cm * MM_TO_CM_FACTOR

    /** Converte Centímetros (CM) para Polegadas (Inch). */
    fun convertCmToInch(cm: Float): Float = cm / (MM_TO_INCH_FACTOR / MM_TO_CM_FACTOR)

    /** Extensão de Float para converter CM para MM. */
    fun Float.cmToMm(): Float = convertCmToMm(this)
    /** Extensão de Int para converter CM para MM. */
    fun Int.cmToMm(): Float = convertCmToMm(this.toFloat())

    /** Extensão de Float para converter CM para Inch. */
    fun Float.cmToInch(): Float = convertCmToInch(this)
    /** Extensão de Int para converter CM para Inch. */
    fun Int.cmToInch(): Float = convertCmToInch(this.toFloat())

    // --- Polegadas (INCH) ---

    /** Converte Polegadas (Inch) para Pixels (PX). */
    fun toInch(inches: Float, resources: Resources): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, inches, resources.displayMetrics)

    /** Converte Polegadas (Inch) para Centímetros (CM). */
    fun convertInchToCm(inch: Float): Float = inch * 2.54f

    /** Converte Polegadas (Inch) para Milímetros (MM). */
    fun convertInchToMm(inch: Float): Float = inch * MM_TO_INCH_FACTOR

    /** Extensão de Float para converter Inch para CM. */
    fun Float.inchToCm(): Float = convertInchToCm(this)
    /** Extensão de Int para converter Inch para CM. */
    fun Int.inchToCm(): Float = convertInchToCm(this.toFloat())

    /** Extensão de Float para converter Inch para MM. */
    fun Float.inchToMm(): Float = convertInchToMm(this)
    /** Extensão de Int para converter Inch para MM. */
    fun Int.inchToMm(): Float = convertInchToMm(this.toFloat())

    // --- Extensões Composable para Unidades Físicas em PX ---

    /** Extensão de Float para converter MM para PX. */
    @get:Composable
    val Float.mm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toMm(this@mm, resources) }
        }

    /** Extensão de Int para converter MM para PX. */
    @get:Composable
    val Int.mm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toMm(this@mm.toFloat(), resources) }
        }

    /** Extensão de Float para converter CM para PX. */
    @get:Composable
    val Float.cm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toCm(this@cm, resources) }
        }

    /** Extensão de Int para converter CM para PX. */
    @get:Composable
    val Int.cm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toCm(this@cm.toFloat(), resources) }
        }

    /** Extensão de Float para converter Inch para PX. */
    @get:Composable
    val Float.inch: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toInch(this@inch, resources) }
        }

    /** Extensão de Int para converter Inch para PX. */
    @get:Composable
    val Int.inch: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toInch(this@inch.toFloat(), resources) }
        }

    // --- Utilitários de Medição ---

    /** Converte um valor de diâmetro (Diameter) em uma unidade física específica para Raio (Radius) em Pixels (PX). */
    fun radius(diameter: Float, type: UnitType, resources: Resources): Float = when (type) {
        UnitType.INCH -> toInch(diameter, resources)
        UnitType.CM -> toCm(diameter, resources)
        UnitType.MM -> toMm(diameter, resources)
        UnitType.SP -> diameter.sp.value
        UnitType.DP -> diameter.dp.value
        else -> diameter
    } / 2.0f

    /** Extensão de Float para calcular o Raio em Pixels (PX). */
    @Composable
    fun Float.radius(type: UnitType): Float {
        val resources = LocalResources.current
        return with(LocalDensity.current) { radius(this@radius, type, resources) }
    }

    /** Extensão de Int para calcular o Raio em Pixels (PX). */
    @Composable
    fun Int.radius(type: UnitType): Float {
        val resources = LocalResources.current
        return with(LocalDensity.current) { radius(this@radius.toFloat(), type, resources) }
    }

    /** Ajusta um valor de diâmetro (Diameter) para Circunferência (Circumference) se solicitado. */
    fun displayMeasureDiameter(diameter: Float, isCircumference: Boolean): Float =
        if (isCircumference) (diameter * CIRCUMFERENCE_FACTOR).toFloat() else diameter

    /** Extensão de Float para ajustar a medida para Diâmetro ou Circunferência. */
    fun Float.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this, isCircumference)

    /** Extensão de Int para ajustar a medida para Diâmetro ou Circunferência. */
    fun Int.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this.toFloat(), isCircumference)


    /** Calcula o tamanho de 1 unidade (1.0f) em Pixels (PX) para uma unidade física específica. */
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