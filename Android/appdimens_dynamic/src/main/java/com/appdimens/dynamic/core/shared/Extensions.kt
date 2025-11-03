/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Shared Extension Functions
 *
 * Description:
 * Shared extension functions that can be used by both code and compose implementations.
 * These extensions delegate to core implementations and are platform-agnostic.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.shared

import com.appdimens.dynamic.core.models.PhysicalUnitsCore
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

// ============================================
// PHYSICAL UNIT CONVERSION EXTENSIONS
// ============================================

/**
 * [EN] Float extension to convert MM to CM.
 * [PT] Extensão de Float para converter MM para CM.
 */
fun Float.mmToCm(): Float = PhysicalUnitsCore.convertMmToCm(this)

/**
 * [EN] Float extension to convert MM to Inch.
 * [PT] Extensão de Float para converter MM para Inch.
 */
fun Float.mmToInch(): Float = PhysicalUnitsCore.convertMmToInch(this)

/**
 * [EN] Float extension to convert CM to MM.
 * [PT] Extensão de Float para converter CM para MM.
 */
fun Float.cmToMm(): Float = PhysicalUnitsCore.convertCmToMm(this)

/**
 * [EN] Float extension to convert CM to Inch.
 * [PT] Extensão de Float para converter CM para Inch.
 */
fun Float.cmToInch(): Float = PhysicalUnitsCore.convertCmToInch(this)

/**
 * [EN] Float extension to convert Inch to MM.
 * [PT] Extensão de Float para converter Inch para MM.
 */
fun Float.inchToMm(): Float = PhysicalUnitsCore.convertInchToMm(this)

/**
 * [EN] Float extension to convert Inch to CM.
 * [PT] Extensão de Float para converter Inch para CM.
 */
fun Float.inchToCm(): Float = PhysicalUnitsCore.convertInchToCm(this)

/**
 * [EN] Int extension to convert MM to CM.
 * [PT] Extensão de Int para converter MM para CM.
 */
fun Int.mmToCm(): Float = PhysicalUnitsCore.convertMmToCm(this.toFloat())

/**
 * [EN] Int extension to convert MM to Inch.
 * [PT] Extensão de Int para converter MM para Inch.
 */
fun Int.mmToInch(): Float = PhysicalUnitsCore.convertMmToInch(this.toFloat())

/**
 * [EN] Int extension to convert CM to MM.
 * [PT] Extensão de Int para converter CM para MM.
 */
fun Int.cmToMm(): Float = PhysicalUnitsCore.convertCmToMm(this.toFloat())

/**
 * [EN] Int extension to convert CM to Inch.
 * [PT] Extensão de Int para converter CM para Inch.
 */
fun Int.cmToInch(): Float = PhysicalUnitsCore.convertCmToInch(this.toFloat())

/**
 * [EN] Int extension to convert Inch to MM.
 * [PT] Extensão de Int para converter Inch para MM.
 */
fun Int.inchToMm(): Float = PhysicalUnitsCore.convertInchToMm(this.toFloat())

/**
 * [EN] Int extension to convert Inch to CM.
 * [PT] Extensão de Int para converter Inch para CM.
 */
fun Int.inchToCm(): Float = PhysicalUnitsCore.convertInchToCm(this.toFloat())

// ============================================
// MATHEMATICAL HELPER FUNCTIONS
// ============================================

/**
 * [EN] Linear interpolation between two values.
 * 
 * @param start Start value
 * @param end End value
 * @param fraction Interpolation fraction (0.0 to 1.0)
 * @return Interpolated value
 * 
 * @example
 * ```kotlin
 * val mid = lerp(10f, 20f, 0.5f)  // Returns 15.0
 * val quarter = lerp(0f, 100f, 0.25f)  // Returns 25.0
 * ```
 * 
 * [PT] Interpolação linear entre dois valores.
 */
fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}

/**
 * [EN] Linear interpolation between two Int values, returning Float.
 * [PT] Interpolação linear entre dois valores Int, retornando Float.
 */
fun lerp(start: Int, end: Int, fraction: Float): Float {
    return start + (end - start) * fraction
}

/**
 * [EN] Clamps a value between minimum and maximum bounds.
 * 
 * @param value Value to clamp
 * @param min Minimum bound
 * @param max Maximum bound
 * @return Clamped value
 * 
 * @example
 * ```kotlin
 * val clamped = clamp(150f, 0f, 100f)  // Returns 100.0
 * val inRange = clamp(50f, 0f, 100f)   // Returns 50.0
 * ```
 * 
 * [PT] Limita um valor entre limites mínimo e máximo.
 */
fun clamp(value: Float, min: Float, max: Float): Float {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

/**
 * [EN] Clamps an Int value between minimum and maximum bounds.
 * [PT] Limita um valor Int entre limites mínimo e máximo.
 */
fun clamp(value: Int, min: Int, max: Int): Int {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

/**
 * [EN] Float extension to clamp within a range.
 * 
 * @receiver Float value to clamp
 * @param min Minimum bound
 * @param max Maximum bound
 * @return Clamped value
 * 
 * [PT] Extensão de Float para limitar dentro de um intervalo.
 */
fun Float.coerceInRange(min: Float, max: Float): Float = clamp(this, min, max)

/**
 * [EN] Int extension to clamp within a range.
 * [PT] Extensão de Int para limitar dentro de um intervalo.
 */
fun Int.coerceInRange(min: Int, max: Int): Int = clamp(this, min, max)

/**
 * [EN] Maps a value from one range to another.
 * 
 * @param value Value to map
 * @param fromMin Source range minimum
 * @param fromMax Source range maximum
 * @param toMin Target range minimum
 * @param toMax Target range maximum
 * @return Mapped value
 * 
 * @example
 * ```kotlin
 * // Map 50 from range [0, 100] to range [0, 10]
 * val mapped = mapRange(50f, 0f, 100f, 0f, 10f)  // Returns 5.0
 * 
 * // Map screen width (360-1920) to scale factor (1.0-2.4)
 * val scale = mapRange(720f, 360f, 1920f, 1.0f, 2.4f)
 * ```
 * 
 * [PT] Mapeia um valor de um intervalo para outro.
 */
fun mapRange(
    value: Float,
    fromMin: Float,
    fromMax: Float,
    toMin: Float,
    toMax: Float
): Float {
    val normalized = (value - fromMin) / (fromMax - fromMin)
    return lerp(toMin, toMax, normalized.coerceIn(0f, 1f))
}

/**
 * [EN] Float extension to check if within range (inclusive).
 * 
 * @receiver Float value to check
 * @param min Minimum bound (inclusive)
 * @param max Maximum bound (inclusive)
 * @return True if value is within range
 * 
 * @example
 * ```kotlin
 * val inRange = 50f.isInRange(0f, 100f)  // Returns true
 * val outOfRange = 150f.isInRange(0f, 100f)  // Returns false
 * ```
 * 
 * [PT] Extensão de Float para verificar se está dentro do intervalo (inclusivo).
 */
fun Float.isInRange(min: Float, max: Float): Boolean {
    return this >= min && this <= max
}

/**
 * [EN] Int extension to check if within range (inclusive).
 * 
 * @receiver Int value to check
 * @param min Minimum bound (inclusive)
 * @param max Maximum bound (inclusive)
 * @return True if value is within range
 * 
 * [PT] Extensão de Int para verificar se está dentro do intervalo (inclusivo).
 */
fun Int.isInRange(min: Int, max: Int): Boolean {
    return this >= min && this <= max
}

/**
 * [EN] Rounds a Float to a specified number of decimal places.
 * 
 * @receiver Float value to round
 * @param decimals Number of decimal places
 * @return Rounded value
 * 
 * @example
 * ```kotlin
 * val rounded = 3.14159f.roundTo(2)  // Returns 3.14
 * val noDecimals = 3.7f.roundTo(0)   // Returns 4.0
 * ```
 * 
 * [PT] Arredonda um Float para um número específico de casas decimais.
 */
fun Float.roundTo(decimals: Int): Float {
    val multiplier = 10.0.pow(decimals.toDouble()).toFloat()
    return round(this * multiplier) / multiplier
}

/**
 * [EN] Calculates percentage of a value.
 * 
 * @receiver Float base value
 * @param percentage Percentage (0-100)
 * @return Calculated percentage
 * 
 * @example
 * ```kotlin
 * val half = 100f.percent(50f)  // Returns 50.0
 * val tax = 150f.percent(15f)    // Returns 22.5
 * ```
 * 
 * [PT] Calcula porcentagem de um valor.
 */
fun Float.percent(percentage: Float): Float = this * (percentage / 100f)

/**
 * [EN] Calculates percentage of an Int value.
 * [PT] Calcula porcentagem de um valor Int.
 */
fun Int.percent(percentage: Float): Float = this * (percentage / 100f)

// ============================================
// VALIDATION FUNCTIONS
// ============================================

/**
 * [EN] Validates that a Float is positive (> 0).
 * 
 * @receiver Float value to validate
 * @param name Parameter name for error message
 * @return The value if positive
 * @throws IllegalArgumentException if value <= 0
 * 
 * [PT] Valida que um Float é positivo (> 0).
 */
fun Float.requirePositive(name: String = "value"): Float {
    require(this > 0f) { "$name must be positive, got: $this" }
    return this
}

/**
 * [EN] Validates that an Int is positive (> 0).
 * [PT] Valida que um Int é positivo (> 0).
 */
fun Int.requirePositive(name: String = "value"): Int {
    require(this > 0) { "$name must be positive, got: $this" }
    return this
}

/**
 * [EN] Validates that a Float is non-negative (>= 0).
 * 
 * @receiver Float value to validate
 * @param name Parameter name for error message
 * @return The value if non-negative
 * @throws IllegalArgumentException if value < 0
 * 
 * [PT] Valida que um Float é não-negativo (>= 0).
 */
fun Float.requireNonNegative(name: String = "value"): Float {
    require(this >= 0f) { "$name must be non-negative, got: $this" }
    return this
}

/**
 * [EN] Validates that an Int is non-negative (>= 0).
 * [PT] Valida que um Int é não-negativo (>= 0).
 */
fun Int.requireNonNegative(name: String = "value"): Int {
    require(this >= 0) { "$name must be non-negative, got: $this" }
    return this
}

/**
 * [EN] Validates that a value is within a specific range.
 * 
 * @receiver Float value to validate
 * @param min Minimum bound (inclusive)
 * @param max Maximum bound (inclusive)
 * @param name Parameter name for error message
 * @return The value if within range
 * @throws IllegalArgumentException if value out of range
 * 
 * [PT] Valida que um valor está dentro de um intervalo específico.
 */
fun Float.requireInRange(min: Float, max: Float, name: String = "value"): Float {
    require(this in min..max) { "$name must be in range [$min, $max], got: $this" }
    return this
}

/**
 * [EN] Validates that an Int value is within a specific range.
 * [PT] Valida que um valor Int está dentro de um intervalo específico.
 */
fun Int.requireInRange(min: Int, max: Int, name: String = "value"): Int {
    require(this in min..max) { "$name must be in range [$min, $max], got: $this" }
    return this
}

/**
 * [EN] Validates that a sensitivity value is in acceptable range (0.1-1.0).
 * 
 * @receiver Float sensitivity value
 * @return The value if valid
 * @throws IllegalArgumentException if invalid
 * 
 * [PT] Valida que um valor de sensibilidade está no intervalo aceitável (0.1-1.0).
 */
fun Float.requireValidSensitivity(): Float {
    return this.requireInRange(0.1f, 1.0f, "sensitivity")
}

/**
 * [EN] Validates that a power exponent is in acceptable range (0.5-1.0).
 * 
 * @receiver Float exponent value
 * @return The value if valid
 * @throws IllegalArgumentException if invalid
 * 
 * [PT] Valida que um expoente de potência está no intervalo aceitável (0.5-1.0).
 */
fun Float.requireValidPowerExponent(): Float {
    return this.requireInRange(0.5f, 1.0f, "powerExponent")
}

// ============================================
// UTILITY FUNCTIONS
// ============================================

/**
 * [EN] Safely divides two Floats, returning default value if divisor is zero.
 * 
 * @param divisor The divisor
 * @param default Default value if divisor is zero (default: 0f)
 * @return Division result or default
 * 
 * @example
 * ```kotlin
 * val result = 10f.safeDivide(2f)     // Returns 5.0
 * val safe = 10f.safeDivide(0f, 1f)   // Returns 1.0 (default)
 * ```
 * 
 * [PT] Divide dois Floats com segurança, retornando valor padrão se divisor for zero.
 */
fun Float.safeDivide(divisor: Float, default: Float = 0f): Float {
    return if (divisor != 0f) this / divisor else default
}

/**
 * [EN] Safely divides two Ints, returning default value if divisor is zero.
 * [PT] Divide dois Ints com segurança, retornando valor padrão se divisor for zero.
 */
fun Int.safeDivide(divisor: Int, default: Int = 0): Int {
    return if (divisor != 0) this / divisor else default
}

/**
 * [EN] Checks if a Float is approximately equal to another within tolerance.
 * 
 * @param other Value to compare
 * @param epsilon Tolerance (default: 0.001f)
 * @return True if approximately equal
 * 
 * @example
 * ```kotlin
 * val equal = 1.0001f.approxEquals(1.0f)  // Returns true (default tolerance)
 * val notEqual = 1.1f.approxEquals(1.0f, epsilon = 0.01f)  // Returns false
 * ```
 * 
 * [PT] Verifica se um Float é aproximadamente igual a outro dentro de uma tolerância.
 */
fun Float.approxEquals(other: Float, epsilon: Float = 0.001f): Boolean {
    return abs(this - other) < epsilon
}

