/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Physical Units
 *
 * Description:
 * Shared logic for physical unit conversions (MM, CM, Inch).
 * This is the unified implementation used by both code and compose packages.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.models

import android.content.res.Resources
import android.util.TypedValue
import com.appdimens.library.UnitType
import kotlin.math.PI

/**
 * Shared logic for physical unit conversions.
 * 
 * This object contains the unified implementation of physical unit conversion
 * logic that is shared between code and compose packages.
 * 
 * Platform-specific wrappers delegate to this implementation.
 */
object PhysicalUnitsCore {

    // ============================================
    // CONSTANTS
    // ============================================

    /**
     * Points per inch (standard resolution).
     */
    const val POINTS_PER_INCH = 72.0f

    /**
     * Points per centimeter.
     */
    const val POINTS_PER_CM = POINTS_PER_INCH / 2.54f

    /**
     * Points per millimeter.
     */
    const val POINTS_PER_MM = POINTS_PER_CM / 10.0f

    /**
     * Conversion factors for unit conversions.
     */
    const val MM_TO_CM_FACTOR = 10.0f
    const val MM_TO_INCH_FACTOR = 25.4f
    const val CM_TO_INCH_FACTOR = 2.54f

    /**
     * Circumference factor (2π).
     */
    const val CIRCUMFERENCE_FACTOR = PI * 2.0

    // ============================================
    // CONVERSION TO DP (via Points)
    // ============================================

    /**
     * Converts millimeters to Dp using points as intermediate unit.
     */
    fun toDpFromMm(mm: Float, resources: Resources): Float {
        val points = mm * POINTS_PER_MM
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PT, points, resources.displayMetrics
        )
    }

    /**
     * Converts centimeters to Dp using points as intermediate unit.
     */
    fun toDpFromCm(cm: Float, resources: Resources): Float {
        val points = cm * POINTS_PER_CM
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PT, points, resources.displayMetrics
        )
    }

    /**
     * Converts inches to Dp using points as intermediate unit.
     */
    fun toDpFromInch(inch: Float, resources: Resources): Float {
        val points = inch * POINTS_PER_INCH
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_PT, points, resources.displayMetrics
        )
    }

    // ============================================
    // CONVERSION TO PX (Direct)
    // ============================================

    /**
     * Converts millimeters to Pixels directly.
     */
    fun toPxFromMm(mm: Float, resources: Resources): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_MM, mm, resources.displayMetrics
        )
    }

    /**
     * Converts centimeters to Pixels directly.
     */
    fun toPxFromCm(cm: Float, resources: Resources): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_MM, cm * MM_TO_CM_FACTOR, resources.displayMetrics
        )
    }

    /**
     * Converts inches to Pixels directly.
     */
    fun toPxFromInch(inch: Float, resources: Resources): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_IN, inch, resources.displayMetrics
        )
    }

    // ============================================
    // UNIT CONVERSIONS (between physical units)
    // ============================================

    /**
     * Converts millimeters to centimeters.
     */
    fun convertMmToCm(mm: Float): Float = mm / MM_TO_CM_FACTOR

    /**
     * Converts millimeters to inches.
     */
    fun convertMmToInch(mm: Float): Float = mm / MM_TO_INCH_FACTOR

    /**
     * Converts centimeters to millimeters.
     */
    fun convertCmToMm(cm: Float): Float = cm * MM_TO_CM_FACTOR

    /**
     * Converts centimeters to inches.
     */
    fun convertCmToInch(cm: Float): Float = cm / CM_TO_INCH_FACTOR

    /**
     * Converts inches to millimeters.
     */
    fun convertInchToMm(inch: Float): Float = inch * MM_TO_INCH_FACTOR

    /**
     * Converts inches to centimeters.
     */
    fun convertInchToCm(inch: Float): Float = inch * CM_TO_INCH_FACTOR

    // ============================================
    // UTILITY METHODS
    // ============================================

    /**
     * Converts a diameter value in a specific physical unit to radius in Dp.
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
     * Converts a circumference value in a specific physical unit to radius in Dp.
     */
    fun radiusFromCircumference(circumference: Float, unitType: UnitType, resources: Resources): Float {
        val circumferenceInDp = when (unitType) {
            UnitType.MM -> toDpFromMm(circumference, resources)
            UnitType.CM -> toDpFromCm(circumference, resources)
            UnitType.INCH -> toDpFromInch(circumference, resources)
            UnitType.DP -> circumference
            else -> circumference
        }
        
        return circumferenceInDp / (2.0f * PI.toFloat())
    }

    /**
     * Calculates the size of 1 unit (1.0f) in Pixels (PX) for a specific physical unit.
     */
    fun unitSizePerPx(type: UnitType, resources: Resources): Float =
        when (type) {
            UnitType.INCH -> toPxFromInch(1.0f, resources)
            UnitType.CM -> toPxFromCm(1.0f, resources)
            UnitType.MM -> toPxFromMm(1.0f, resources)
            else -> 1f
        }

    /**
     * Adjusts a diameter value to Circumference if requested.
     */
    fun displayMeasureDiameter(diameter: Float, isCircumference: Boolean): Float =
        if (isCircumference) (diameter * CIRCUMFERENCE_FACTOR).toFloat() else diameter
}

