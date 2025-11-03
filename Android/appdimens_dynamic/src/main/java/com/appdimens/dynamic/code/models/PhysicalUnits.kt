/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Physical Units (Code)
 *
 * Description:
 * Platform-specific wrapper for code package that delegates to core implementation.
 * Compatible with the traditional Android View System (XML).
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.code.models

import android.content.res.Resources
import android.util.TypedValue
import com.appdimens.dynamic.core.models.PhysicalUnitsCore
import com.appdimens.library.UnitType

/**
 * [EN] Utility class for physical unit conversions.
 * Delegates to core implementation for unified logic.
 * [PT] Classe utilitária para conversões de unidades físicas.
 */
object AppDimensPhysicalUnits {

    // Re-export constants from core
    const val POINTS_PER_INCH = PhysicalUnitsCore.POINTS_PER_INCH
    const val POINTS_PER_CM = PhysicalUnitsCore.POINTS_PER_CM
    const val POINTS_PER_MM = PhysicalUnitsCore.POINTS_PER_MM

    // MARK: - Conversion Methods

    /**
     * Converts millimeters to Dp.
     */
    fun toDpFromMm(mm: Float, resources: Resources): Float =
        PhysicalUnitsCore.toDpFromMm(mm, resources)

    /**
     * Converts centimeters to Dp.
     */
    fun toDpFromCm(cm: Float, resources: Resources): Float =
        PhysicalUnitsCore.toDpFromCm(cm, resources)

    /**
     * Converts inches to Dp.
     */
    fun toDpFromInch(inch: Float, resources: Resources): Float =
        PhysicalUnitsCore.toDpFromInch(inch, resources)

    /**
     * Converts millimeters to Pixels.
     */
    fun toPxFromMm(mm: Float, resources: Resources): Float =
        PhysicalUnitsCore.toPxFromMm(mm, resources)

    /**
     * Converts centimeters to Pixels.
     */
    fun toPxFromCm(cm: Float, resources: Resources): Float =
        PhysicalUnitsCore.toPxFromCm(cm, resources)

    /**
     * Converts inches to Pixels.
     */
    fun toPxFromInch(inch: Float, resources: Resources): Float =
        PhysicalUnitsCore.toPxFromInch(inch, resources)

    /**
     * Converts millimeters to SP.
     */
    fun toSpFromMm(mm: Float, resources: Resources): Float {
        val dp = toDpFromMm(mm, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dp, resources.displayMetrics
        )
    }

    /**
     * Converts centimeters to SP.
     */
    fun toSpFromCm(cm: Float, resources: Resources): Float {
        val dp = toDpFromCm(cm, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dp, resources.displayMetrics
        )
    }

    /**
     * Converts inches to SP.
     */
    fun toSpFromInch(inch: Float, resources: Resources): Float {
        val dp = toDpFromInch(inch, resources)
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, dp, resources.displayMetrics
        )
    }

    // MARK: - Utility Methods

    /**
     * Converts a diameter value in a specific physical unit to radius in Dp.
     */
    fun radiusFromDiameter(diameter: Float, unitType: UnitType, resources: Resources): Float =
        PhysicalUnitsCore.radiusFromDiameter(diameter, unitType, resources)

    /**
     * Converts a circumference value in a specific physical unit to radius in Dp.
     */
    fun radiusFromCircumference(circumference: Float, unitType: UnitType, resources: Resources): Float =
        PhysicalUnitsCore.radiusFromCircumference(circumference, unitType, resources)

    // MARK: - Conversion Extensions
    // Note: Physical unit conversion extensions moved to shared/AppDimensExtensions.kt
    // They are imported via package-level imports
}