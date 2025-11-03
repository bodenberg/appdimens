/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Adjustment Factors (Compose)
 *
 * Description:
 * Platform-specific wrapper for compose package that delegates to core implementation.
 * Provides Compose-specific @Composable functions with remember support.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.compose.models

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.core.models.AdjustmentFactorsCore
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenAdjustmentFactors
import com.appdimens.library.ScreenType
import com.appdimens.library.BaseOrientation

/**
 * [EN] Singleton object that provides functions for calculating and resolving
 * adjustment factors and screen qualifiers.
 * 
 * Delegates to core implementation for unified logic. Provides Compose-specific
 * wrappers with @Composable and remember support.
 *
 * [PT] Objeto singleton que fornece funções para o cálculo e resolução
 * de fatores de ajuste e qualificadores de tela.
 */
@Stable
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
     * Helper function that isolates the logic for fetching and selecting the custom Dp value
     * through Qualifiers (SW, H, W). This logic should be called inside a 'remember' block for performance.
     * 
     * Compose-specific version that accepts and returns Dp instead of Float.
     */
    fun resolveQualifierDp(
        customDpMap: Map<DpQualifierEntry, Dp>,
        smallestWidthDp: Float,
        currentScreenWidthDp: Float,
        currentScreenHeightDp: Float,
        initialBaseDp: Dp
    ): Dp {
        // Convert Dp map to Float map for core processing
        val floatMap = customDpMap.mapValues { (_, value) -> value.value }
        
        val result = AdjustmentFactorsCore.resolveQualifierDp(
            customDpMap = floatMap,
            smallestWidthDp = smallestWidthDp,
            currentScreenWidthDp = currentScreenWidthDp,
            currentScreenHeightDp = currentScreenHeightDp,
            initialBaseDp = initialBaseDp.value
        )
        
        return result.dp
    }

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
     * Helper function to get the screen aspect ratio from the configuration.
     */
    fun getReferenceAspectRatio(screenWidthDp: Float, screenHeightDp: Float): Float =
        AdjustmentFactorsCore.getReferenceAspectRatio(screenWidthDp, screenHeightDp)

    /**
     * Composable function that calculates and remembers Basic Adjustment Factors.
     * 
     * This is a Compose-specific wrapper that uses remember for performance optimization.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    @Composable
    fun rememberAdjustmentFactors(): ScreenAdjustmentFactors {
        val configuration = LocalConfiguration.current

        // The keys in 'remember' ensure recalculation only when the screen changes.
        return remember(
            configuration.screenWidthDp,
            configuration.screenHeightDp,
            configuration.smallestScreenWidthDp
        ) {
            AdjustmentFactorsCore.calculateAdjustmentFactors(configuration)
        }
    }

    /**
     * Calculates the Basic Adjustment Factors without remember (for non-Compose contexts).
     * Delegates to core implementation.
     */
    @SuppressLint("ConfigurationScreenWidthHeight")
    fun calculateAdjustmentFactors(configuration: Configuration): ScreenAdjustmentFactors =
        AdjustmentFactorsCore.calculateAdjustmentFactors(configuration)

    /**
     * Resolves the effective ScreenType based on the base orientation and current device orientation.
     * If the base orientation differs from the current orientation, LOWEST and HIGHEST are inverted.
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