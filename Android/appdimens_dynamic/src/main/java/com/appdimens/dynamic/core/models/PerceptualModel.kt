/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens - Perceptual Scaling Models
 *
 * Description:
 * Unified enum defining different perceptual scaling models based on psychophysics research.
 * This is the single source of truth for perceptual models shared between
 * code and compose implementations.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.core.models

/**
 * Enum representing different perceptual scaling models based on psychophysics.
 * 
 * Each model represents a different mathematical approach to UI scaling.
 * 
 * Available models:
 * - BALANCED: Smooth transition from linear (phones) to logarithmic (tablets/TVs) ⭐ RECOMMENDED
 * - LOGARITHMIC: Pure logarithmic scaling - maximum control on large screens
 * - POWER: Power law scaling - smooth and configurable behavior
 * 
 * Note: For legacy linear behavior (97% linear), use ScalingStrategy.DEFAULT instead.
 * 
 * @deprecated AppDimens 2.0: For new code, prefer using ScalingStrategy enum with AppDimens.
 * This enum is still used by AppDimensPerceptual for backward compatibility.
 * In AppDimens 2.0, use ScalingStrategy.BALANCED/LOGARITHMIC/POWER instead.
 */
enum class PerceptualModel {
    /**
     * BALANCED: Linear on phones, logarithmic on tablets/TVs (RECOMMENDED)
     * 
     * Formula: 
     * - Below 480dp: Linear scaling
     * - Above 480dp: Smooth transition to logarithmic
     * 
     * Characteristics:
     * - Best of both worlds
     * - Maintains familiar behavior on phones (< 3% difference)
     * - Controls oversizing on tablets/TVs (20-35% reduction)
     * - Smooth transition (no jumps)
     * 
     * Best for: Most apps, cross-device support ⭐ RECOMMENDED
     */
    BALANCED,
    
    /**
     * LOGARITHMIC: Pure logarithmic scaling (maximum control)
     * 
     * Formula: S = k × ln(I / I₀)
     * 
     * Characteristics:
     * - True logarithmic growth on ALL screens
     * - Maximum control on large screens (44-58% reduction vs linear)
     * - May reduce sizes noticeably on phones (-11%)
     * - Strongest reduction for TVs
     * 
     * Best for: TVs, very large tablets, apps that need maximum size control
     */
    LOGARITHMIC,
    
    /**
     * POWER: Power law scaling (smooth and configurable)
     * 
     * Formula: P = k × I^n where n < 1 (default: 0.75)
     * 
     * Characteristics:
     * - Scientifically grounded (Stevens' Power Law for spatial perception)
     * - Configurable exponent (0.6-0.9) for fine control
     * - Smooth, predictable behavior across all screen sizes
     * - Moderate reduction (20-29% on tablets/TVs)
     * 
     * Best for: General purpose, tablets, apps needing fine-tunable control
     */
    POWER;
    
    /**
     * Returns a human-readable description of the model
     */
    fun getDescription(): String = when (this) {
        BALANCED -> "Balanced: Linear phones, logarithmic tablets (Recommended)"
        LOGARITHMIC -> "Logarithmic: Pure log scaling (Maximum control)"
        POWER -> "Power: Smooth power law scaling (Configurable)"
    }
    
    /**
     * Returns recommended use cases
     */
    fun getRecommendedFor(): String = when (this) {
        BALANCED -> "Most apps, cross-device support ⭐"
        LOGARITHMIC -> "TVs, very large tablets, maximum control"
        POWER -> "General purpose, configurable apps, tablets"
    }
}

