/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Adjustment Factors
 *
 * Description:
 * Shared logic for calculating and resolving adjustment factors and screen qualifiers.
 * This is the unified implementation used by both code and compose packages.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.models

import android.annotation.SuppressLint
import android.content.res.Configuration
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenAdjustmentFactors
import com.appdimens.library.ScreenType
import com.appdimens.library.BaseOrientation
import kotlin.math.PI
import kotlin.math.ln
import kotlin.math.max

/**
 * Shared logic for adjustment factors calculation.
 * 
 * This object contains the unified implementation of adjustment factor calculations
 * and qualifier resolution logic that is shared between code and compose packages.
 * 
 * Platform-specific wrappers delegate to this implementation.
 */
object AdjustmentFactorsCore {

    // ============================================
    // CONSTANTS
    // ============================================

    /**
     * Base Dp scaling factor. Default is 1.0f (no adjustment).
     */
    const val BASE_DP_FACTOR = 1.00f

    /**
     * Base reference Dp width for adjustment calculation (e.g., 300dp).
     */
    const val BASE_WIDTH_DP = 300f

    /**
     * Dp increment step size for calculating the adjustment (e.g., every 1dp).
     */
    const val INCREMENT_DP_STEP = 1f

    /**
     * Factor for circumference calculation (2π). Uses kotlin.math.PI.
     */
    const val CIRCUMFERENCE_FACTOR = PI * 2.0

    /**
     * Reference aspect ratio (e.g., 16:9), where the adjustment is neutral.
     */
    const val REFERENCE_AR = 1.78f

    /**
     * DEFAULT sensitivity coefficient: Adjusts how aggressive the scaling is on extreme screens.
     */
    const val DEFAULT_SENSITIVITY_K = 0.08f / 30f  // Adjusted for 1dp step granularity

    /**
     * Default increment factor (used in calculations WITHOUT Aspect Ratio).
     */
    const val BASE_INCREMENT = 0.10f / 30f  // Adjusted for 1dp step granularity

    // ============================================
    // QUALIFIER RESOLUTION (FLOAT)
    // ============================================

    /**
     * Helper function that isolates the logic for searching and selecting the custom Dp value
     * through Qualifiers (SW, H, W).
     *
     * @param customDpMap Map of Dp qualifiers.
     * @param smallestWidthDp smallestScreenWidthDp from the current configuration.
     * @param currentScreenWidthDp Screen width in Dp.
     * @param currentScreenHeightDp Screen height in Dp.
     * @param initialBaseDp Initial Dp value (as Float).
     * @return The custom Dp value or the initial value (as Float).
     */
    fun resolveQualifierDp(
        customDpMap: Map<DpQualifierEntry, Float>,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float,
        initialBaseDp: Float
    ): Float {
        var dpToAdjust = initialBaseDp

        // Filter and sort qualifiers in descending order to ensure the LARGEST matching
        // qualifier is applied (e.g., 900dp should have priority over 600dp).
        val sortedQualifiers = customDpMap.entries.toList().sortedByDescending { it.key.value }

        var foundCustomDp: Float? = null

        // Priority 1: SMALL_WIDTH (smallestScreenWidthDp)
        foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
            key.type == DpQualifier.SMALL_WIDTH && smallestWidthDp >= key.value
        }?.value

        if (foundCustomDp != null) {
            dpToAdjust = foundCustomDp
        } else {
            // Priority 2: HEIGHT (screenHeightDp)
            foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
                key.type == DpQualifier.HEIGHT && currentScreenHeightDp >= key.value
            }?.value

            if (foundCustomDp != null) {
                dpToAdjust = foundCustomDp
            } else {
                // Priority 3: WIDTH (screenWidthDp)
                foundCustomDp = sortedQualifiers.firstOrNull { (key, _) ->
                    key.type == DpQualifier.WIDTH && currentScreenWidthDp >= key.value
                }?.value

                if (foundCustomDp != null) {
                    dpToAdjust = foundCustomDp
                }
            }
        }
        return dpToAdjust
    }

    /**
     * Helper function that checks if a [DpQualifierEntry] meets the current screen dimensions.
     */
    fun resolveIntersectionCondition(
        entry: DpQualifierEntry,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float
    ): Boolean = when (entry.type) {
        DpQualifier.HEIGHT -> currentScreenHeightDp >= entry.value
        DpQualifier.WIDTH -> currentScreenWidthDp >= entry.value
        DpQualifier.SMALL_WIDTH -> smallestWidthDp >= entry.value
    }

    /**
     * Helper function to get the aspect ratio from screen dimensions.
     * @return The screen aspect ratio (Largest dimension / Smallest dimension).
     */
    fun getReferenceAspectRatio(screenWidthDp: Float, screenHeightDp: Float): Float {
        // Aspect Ratio: (Largest dimension / Smallest dimension)
        return if (screenWidthDp > screenHeightDp)
            screenWidthDp / screenHeightDp
        else screenHeightDp / screenWidthDp
    }

    // ============================================
    // ADJUSTMENT FACTORS CALCULATION
    // ============================================

    /**
     * Calculates the Basic Adjustment Factors.
     *
     * @param configuration The current screen configuration.
     * @return A [ScreenAdjustmentFactors] object containing the adjustment factors.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun calculateAdjustmentFactors(configuration: Configuration): ScreenAdjustmentFactors {
        val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
        val highestDimensionDp = max(currentScreenWidthDp, currentScreenHeightDp)

        // Base Adjustment Factor (LOWEST - smallestWidthDp)
        val differenceLowest = smallestWidthDp - BASE_WIDTH_DP
        val adjustmentFactorLowest = differenceLowest / INCREMENT_DP_STEP

        // Base Adjustment Factor (HIGHEST - Max(W,H))
        val differenceHighest = highestDimensionDp - BASE_WIDTH_DP
        val adjustmentFactorHighest = differenceHighest / INCREMENT_DP_STEP

        // Factor WITHOUT Aspect Ratio (uses LOWEST factor for safety)
        val withoutArFactor = BASE_DP_FACTOR + adjustmentFactorLowest * BASE_INCREMENT

        // Get the current screen aspect ratio
        val currentAr = getReferenceAspectRatio(currentScreenWidthDp, currentScreenHeightDp)

        // Continuous calculation (Logarithmic function) to smooth the scaling adjustment
        val continuousAdjustment =
            (DEFAULT_SENSITIVITY_K * ln(currentAr / REFERENCE_AR)).toFloat()
        val finalIncrementValueWithAr = BASE_INCREMENT + continuousAdjustment

        // COMPLETE Factor (LOWEST + AR)
        val withArFactorLowest = BASE_DP_FACTOR + adjustmentFactorLowest * finalIncrementValueWithAr

        // COMPLETE Factor (HIGHEST + AR)
        val withArFactorHighest = BASE_DP_FACTOR + adjustmentFactorHighest * finalIncrementValueWithAr

        // Return all factors, storing both base adjustment types
        return ScreenAdjustmentFactors(
            withArFactorLowest = withArFactorLowest,
            withArFactorHighest = withArFactorHighest,
            withoutArFactor = withoutArFactor,
            adjustmentFactorLowest = adjustmentFactorLowest,
            adjustmentFactorHighest = adjustmentFactorHighest
        )
    }

    /**
     * Resolves the effective ScreenType based on the base orientation and current device orientation.
     * If the base orientation differs from the current orientation, LOWEST and HIGHEST are inverted.
     *
     * @param requestedType The originally requested screen type (LOWEST or HIGHEST)
     * @param baseOrientation The orientation for which the design was created (PORTRAIT, LANDSCAPE, or AUTO)
     * @param configuration The current screen configuration
     * @return The resolved ScreenType (may be inverted from requestedType)
     */
    fun resolveScreenType(
        requestedType: ScreenType,
        baseOrientation: BaseOrientation,
        configuration: Configuration
    ): ScreenType {
        // If AUTO, no inversion - return as requested
        if (baseOrientation == BaseOrientation.AUTO) {
            return requestedType
        }

        // Detect current orientation
        val currentIsPortrait = configuration.screenHeightDp > configuration.screenWidthDp
        val currentIsLandscape = !currentIsPortrait

        // Determine if inversion is needed
        val shouldInvert = when (baseOrientation) {
            BaseOrientation.PORTRAIT -> currentIsLandscape
            BaseOrientation.LANDSCAPE -> currentIsPortrait
            BaseOrientation.AUTO -> false
        }

        // Invert if needed
        return if (shouldInvert) {
            when (requestedType) {
                ScreenType.LOWEST -> ScreenType.HIGHEST
                ScreenType.HIGHEST -> ScreenType.LOWEST
            }
        } else {
            requestedType
        }
    }
}

