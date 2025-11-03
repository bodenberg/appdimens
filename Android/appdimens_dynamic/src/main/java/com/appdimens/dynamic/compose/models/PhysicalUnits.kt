/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Physical Units (Compose)
 *
 * Description:
 * Platform-specific wrapper for compose package that delegates to core implementation.
 * Provides Compose-specific @Composable functions with remember support.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.compose.models

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.core.models.PhysicalUnitsCore
import com.appdimens.library.UnitType

/**
 * [EN] Singleton object providing functions for physical unit conversion (MM, CM, Inch)
 * and measurement utilities.
 * 
 * Delegates to core implementation for unified logic. Provides Compose-specific
 * wrappers with @Composable and remember support.
 *
 * [PT] Objeto singleton que fornece funções para conversão de unidades físicas (MM, CM, Inch)
 * e utilitários de medição.
 */
object AppDimensPhysicalUnits {

    // Re-export constants from core
    const val CIRCUMFERENCE_FACTOR = PhysicalUnitsCore.CIRCUMFERENCE_FACTOR

    /**
     * Converts Millimeters (MM) to Pixels (PX).
     */
    fun toMm(mm: Float, resources: Resources): Float =
        PhysicalUnitsCore.toPxFromMm(mm, resources)

    /**
     * Converts Millimeters (MM) to Centimeters (CM).
     */
    fun convertMmToCm(mm: Float): Float = PhysicalUnitsCore.convertMmToCm(mm)

    /**
     * Converts Millimeters (MM) to Inches (Inch).
     */
    fun convertMmToInch(mm: Float): Float = PhysicalUnitsCore.convertMmToInch(mm)

    /**
     * Float extension to convert MM to CM.
     */
    fun Float.mmToCm(): Float = convertMmToCm(this)

    /**
     * Int extension to convert MM to CM.
     */
    fun Int.mmToCm(): Float = convertMmToCm(this.toFloat())

    /**
     * Float extension to convert MM to Inch.
     */
    fun Float.mmToInch(): Float = convertMmToInch(this)

    /**
     * Int extension to convert MM to Inch.
     */
    fun Int.mmToInch(): Float = convertMmToInch(this.toFloat())

    /**
     * Converts Centimeters (CM) to Pixels (PX).
     */
    fun toCm(cm: Float, resources: Resources): Float =
        PhysicalUnitsCore.toPxFromCm(cm, resources)

    /**
     * Converts Centimeters (CM) to Millimeters (MM).
     */
    fun convertCmToMm(cm: Float): Float = PhysicalUnitsCore.convertCmToMm(cm)

    /**
     * Converts Centimeters (CM) to Inches (Inch).
     */
    fun convertCmToInch(cm: Float): Float = PhysicalUnitsCore.convertCmToInch(cm)

    /**
     * Float extension to convert CM to MM.
     */
    fun Float.cmToMm(): Float = convertCmToMm(this)

    /**
     * Int extension to convert CM to MM.
     */
    fun Int.cmToMm(): Float = convertCmToMm(this.toFloat())

    /**
     * Float extension to convert CM to Inch.
     */
    fun Float.cmToInch(): Float = convertCmToInch(this)

    /**
     * Int extension to convert CM to Inch.
     */
    fun Int.cmToInch(): Float = convertCmToInch(this.toFloat())

    /**
     * Converts Inches (Inch) to Pixels (PX).
     */
    fun toInch(inches: Float, resources: Resources): Float =
        PhysicalUnitsCore.toPxFromInch(inches, resources)

    /**
     * Converts Inches (Inch) to Centimeters (CM).
     */
    fun convertInchToCm(inch: Float): Float = PhysicalUnitsCore.convertInchToCm(inch)

    /**
     * Converts Inches (Inch) to Millimeters (MM).
     */
    fun convertInchToMm(inch: Float): Float = PhysicalUnitsCore.convertInchToMm(inch)

    /**
     * Float extension to convert Inch to CM.
     */
    fun Float.inchToCm(): Float = convertInchToCm(this)

    /**
     * Int extension to convert Inch to CM.
     */
    fun Int.inchToCm(): Float = convertInchToCm(this.toFloat())

    /**
     * Float extension to convert Inch to MM.
     */
    fun Float.inchToMm(): Float = convertInchToMm(this)

    /**
     * Int extension to convert Inch to MM.
     */
    fun Int.inchToMm(): Float = convertInchToMm(this.toFloat())

    /**
     * [EN] Composable Extensions for Physical Units in PX.
     *
     * [PT] Extensões Composable para Unidades Físicas em PX.
     */

    /**
     * Float extension to convert MM to PX.
     */
    @get:Composable
    val Float.mm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toMm(this@mm, resources) }
        }

    /**
     * Int extension to convert MM to PX.
     */
    @get:Composable
    val Int.mm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toMm(this@mm.toFloat(), resources) }
        }

    /**
     * Float extension to convert CM to PX.
     */
    @get:Composable
    val Float.cm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toCm(this@cm, resources) }
        }

    /**
     * Int extension to convert CM to PX.
     */
    @get:Composable
    val Int.cm: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toCm(this@cm.toFloat(), resources) }
        }

    /**
     * Float extension to convert Inch to PX.
     */
    @get:Composable
    val Float.inch: Float
        get() {
            val resources = LocalResources.current
            return with(LocalDensity.current) { toInch(this@inch, resources) }
        }

    /**
     * Int extension to convert Inch to PX.
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
     * Converts a diameter value in a specific physical unit to Radius in Pixels (PX).
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
     * Float extension to calculate the Radius in Pixels (PX).
     */
    @Composable
    fun Float.radius(type: UnitType): Float {
        val resources = LocalResources.current
        return with(LocalDensity.current) { radius(this@radius, type, resources) }
    }

    /**
     * Int extension to calculate the Radius in Pixels (PX).
     */
    @Composable
    fun Int.radius(type: UnitType): Float {
        val resources = LocalResources.current
        return with(LocalDensity.current) { radius(this@radius.toFloat(), type, resources) }
    }

    /**
     * Adjusts a diameter value to Circumference if requested.
     */
    fun displayMeasureDiameter(diameter: Float, isCircumference: Boolean): Float =
        PhysicalUnitsCore.displayMeasureDiameter(diameter, isCircumference)

    /**
     * Float extension to adjust the measurement for Diameter or Circumference.
     */
    fun Float.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this, isCircumference)

    /**
     * Int extension to adjust the measurement for Diameter or Circumference.
     */
    fun Int.measureDiameter(isCircumference: Boolean): Float =
        displayMeasureDiameter(this.toFloat(), isCircumference)

    /**
     * Calculates the size of 1 unit (1.0f) in Pixels (PX) for a specific physical unit.
     */
    fun unitSizePerPx(type: UnitType, resources: Resources): Float =
        PhysicalUnitsCore.unitSizePerPx(type, resources)
}
