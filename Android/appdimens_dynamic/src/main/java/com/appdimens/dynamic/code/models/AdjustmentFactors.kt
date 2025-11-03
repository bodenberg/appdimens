/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Adjustment Factors (Code)
 *
 * Description:
 * Platform-specific wrapper for code package that delegates to core implementation.
 * Compatible with the traditional Android View System (XML).
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.code.models

import android.annotation.SuppressLint
import android.content.res.Configuration
import com.appdimens.dynamic.core.models.AdjustmentFactorsCore
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenAdjustmentFactors
import com.appdimens.library.ScreenType
import com.appdimens.library.BaseOrientation

/**
 * [EN] A singleton object that provides functions for calculating and resolving
 * adjustment factors and screen qualifiers. Compatible with the traditional
 * Android View System (XML).
 * 
 * Delegates to core implementation for unified logic.
 *
 * [PT] Objeto singleton que fornece funções para o cálculo e resolução
 * de fatores de ajuste e qualificadores de tela. Compatível com o sistema
 * tradicional de Views XML do Android.
 */
object AppDimensAdjustmentFactors {

    // Re-export constants from core
    const val BASE_DP_FACTOR = AdjustmentFactorsCore.BASE_DP_FACTOR
    const val BASE_WIDTH_DP = AdjustmentFactorsCore.BASE_WIDTH_DP
    const val INCREMENT_DP_STEP = AdjustmentFactorsCore.INCREMENT_DP_STEP
    const val CIRCUMFERENCE_FACTOR = AdjustmentFactorsCore.CIRCUMFERENCE_FACTOR
    const val REFERENCE_AR = AdjustmentFactorsCore.REFERENCE_AR
    const val DEFAULT_SENSITIVITY_K = AdjustmentFactorsCore.DEFAULT_SENSITIVITY_K
    const val BASE_INCREMENT = AdjustmentFactorsCore.BASE_INCREMENT

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
    ): Float = AdjustmentFactorsCore.resolveQualifierDp(
        customDpMap = customDpMap,
        smallestWidthDp = smallestWidthDp,
        currentScreenWidthDp = currentScreenWidthDp,
        currentScreenHeightDp = currentScreenHeightDp,
        initialBaseDp = initialBaseDp
    )

    /**
     * Helper function that checks if a [DpQualifierEntry] meets the current screen dimensions.
     */
    fun resolveIntersectionCondition(
        entry: DpQualifierEntry,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float
    ): Boolean = AdjustmentFactorsCore.resolveIntersectionCondition(
        entry = entry,
        smallestWidthDp = smallestWidthDp,
        currentScreenWidthDp = currentScreenWidthDp,
        currentScreenHeightDp = currentScreenHeightDp
    )

    /**
     * Helper function to get the aspect ratio from the configuration.
     * @return The screen aspect ratio (Largest dimension / Smallest dimension).
     */
    fun getReferenceAspectRatio(screenWidthDp: Float, screenHeightDp: Float): Float =
        AdjustmentFactorsCore.getReferenceAspectRatio(screenWidthDp, screenHeightDp)

    /**
     * Calculates the Basic Adjustment Factors.
     *
     * @param configuration The current screen configuration.
     * @return A [ScreenAdjustmentFactors] object containing the adjustment factors.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun calculateAdjustmentFactors(configuration: Configuration): ScreenAdjustmentFactors =
        AdjustmentFactorsCore.calculateAdjustmentFactors(configuration)

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
    ): ScreenType = AdjustmentFactorsCore.resolveScreenType(
        requestedType = requestedType,
        baseOrientation = baseOrientation,
        configuration = configuration
    )
}