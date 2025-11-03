/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Fluent Builder
 *
 * Description:
 * Fluent builder for dimension calculations with type-safe configuration.
 * Provides an intuitive API for setting up complex dimension calculations.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.builders

import android.content.res.Resources
import com.appdimens.games.core.calculation.GamesCalculator
import com.appdimens.games.core.strategy.*
import com.appdimens.games.core.models.*

/**
 * Fluent builder for game dimension calculations.
 * 
 * Provides a type-safe, intuitive API for configuring dimension calculations.
 * All methods return the builder for chaining.
 * 
 * @param baseValue Base dimension value in dp
 * 
 * @example
 * ```kotlin
 * val playerSize = GameDimensBuilder(64f)
 *     .withStrategy(GameScalingStrategy.BALANCED)
 *     .forElement(GameElementType.PLAYER)
 *     .withConstraints(minValue = 32f, maxValue = 128f)
 *     .build(resources)
 * ```
 */
class GameDimensBuilder(
    private val baseValue: Float
) {
    // Configuration properties
    private var strategy: GameScalingStrategy? = null
    private var elementType: GameElementType? = null
    private var screenType: GameScreenType = GameScreenType.LOWEST
    private var baseOrientation: GameBaseOrientation = GameBaseOrientation.AUTO
    
    // Strategy-specific parameters
    private var defaultParams: DefaultParams = DefaultParams()
    private var perceptualParams: PerceptualParams = PerceptualParams()
    private var fluidParams: FluidParams? = null
    
    // Constraints and qualifiers
    private var constraints: Constraints = Constraints()
    private var customQualifiers: CustomQualifiers = CustomQualifiers()
    
    // ============================================
    // STRATEGY CONFIGURATION
    // ============================================
    
    /**
     * Sets the scaling strategy explicitly.
     * 
     * If not set, strategy will be auto-inferred based on element type.
     * 
     * @param strategy Scaling strategy to use
     * @return This builder for chaining
     */
    fun withStrategy(strategy: GameScalingStrategy): GameDimensBuilder {
        this.strategy = strategy
        return this
    }
    
    /**
     * Sets the element type for auto-inference.
     * 
     * If strategy is not explicitly set, it will be inferred from element type.
     * 
     * @param type Element type
     * @return This builder for chaining
     */
    fun withElementType(type: GameElementType): GameDimensBuilder {
        this.elementType = type
        return this
    }
    
    /**
     * Sets the screen type to use for calculations.
     * 
     * @param type Screen type (LOWEST or HIGHEST)
     * @return This builder for chaining
     */
    fun withScreenType(type: GameScreenType): GameDimensBuilder {
        this.screenType = type
        return this
    }
    
    /**
     * Sets the base orientation for automatic inversion.
     * 
     * @param orientation Base orientation (PORTRAIT, LANDSCAPE, or AUTO)
     * @return This builder for chaining
     */
    fun withBaseOrientation(orientation: GameBaseOrientation): GameDimensBuilder {
        this.baseOrientation = orientation
        return this
    }
    
    // ============================================
    // STRATEGY-SPECIFIC PARAMETERS
    // ============================================
    
    /**
     * Configures DEFAULT strategy parameters.
     * 
     * @param applyAspectRatio Whether to apply aspect ratio adjustment
     * @param arSensitivity Aspect ratio sensitivity (null = use default)
     * @return This builder for chaining
     */
    fun withDefault(
        applyAspectRatio: Boolean = true,
        arSensitivity: Float? = null
    ): GameDimensBuilder {
        this.defaultParams = DefaultParams(applyAspectRatio, arSensitivity)
        return this
    }
    
    /**
     * Configures perceptual strategy parameters.
     * 
     * @param model Perceptual model (BALANCED, LOGARITHMIC, or POWER)
     * @param sensitivity Sensitivity factor (0.0-1.0)
     * @param powerExponent Power exponent (0.0-1.0)
     * @param transitionPoint Transition point for BALANCED model (in dp)
     * @return This builder for chaining
     */
    fun withPerceptual(
        model: GamePerceptualModel = GamePerceptualModel.BALANCED,
        sensitivity: Float = 0.40f,
        powerExponent: Float = 0.75f,
        transitionPoint: Float = 480f
    ): GameDimensBuilder {
        this.perceptualParams = PerceptualParams(
            model, sensitivity, powerExponent, transitionPoint
        )
        return this
    }
    
    /**
     * Configures perceptual strategy parameters (overload with params object).
     * 
     * @param params Perceptual parameters
     * @return This builder for chaining
     */
    fun withPerceptual(params: PerceptualParams): GameDimensBuilder {
        this.perceptualParams = params
        return this
    }
    
    /**
     * Configures FLUID strategy parameters.
     * 
     * @param minValue Minimum value in dp
     * @param maxValue Maximum value in dp
     * @param minWidth Minimum width breakpoint (default 320dp)
     * @param maxWidth Maximum width breakpoint (default 768dp)
     * @return This builder for chaining
     */
    fun withFluid(
        minValue: Float,
        maxValue: Float,
        minWidth: Float = 320f,
        maxWidth: Float = 768f
    ): GameDimensBuilder {
        this.fluidParams = FluidParams(minValue, maxValue, minWidth, maxWidth)
        return this
    }
    
    /**
     * Configures FLUID strategy parameters (overload with params object).
     * 
     * @param params Fluid parameters
     * @return This builder for chaining
     */
    fun withFluid(params: FluidParams): GameDimensBuilder {
        this.fluidParams = params
        return this
    }
    
    // ============================================
    // CONSTRAINTS
    // ============================================
    
    /**
     * Sets universal constraints.
     * 
     * @param minValue Minimum value in dp (null = no minimum)
     * @param maxValue Maximum value in dp (null = no maximum)
     * @param maxPhysicalMm Maximum physical size in mm (null = no limit)
     * @return This builder for chaining
     */
    fun withConstraints(
        minValue: Float? = null,
        maxValue: Float? = null,
        maxPhysicalMm: Float? = null
    ): GameDimensBuilder {
        this.constraints = Constraints(minValue, maxValue, maxPhysicalMm)
        return this
    }
    
    /**
     * Sets minimum value constraint.
     * 
     * @param minValue Minimum value in dp
     * @return This builder for chaining
     */
    fun withMinValue(minValue: Float): GameDimensBuilder {
        this.constraints = constraints.copy(minValue = minValue)
        return this
    }
    
    /**
     * Sets maximum value constraint.
     * 
     * @param maxValue Maximum value in dp
     * @return This builder for chaining
     */
    fun withMaxValue(maxValue: Float): GameDimensBuilder {
        this.constraints = constraints.copy(maxValue = maxValue)
        return this
    }
    
    /**
     * Sets maximum physical size constraint.
     * 
     * @param maxPhysicalMm Maximum physical size in mm
     * @return This builder for chaining
     */
    fun withMaxPhysicalSize(maxPhysicalMm: Float): GameDimensBuilder {
        this.constraints = constraints.copy(maxPhysicalMm = maxPhysicalMm)
        return this
    }
    
    // ============================================
    // QUALIFIERS
    // ============================================
    
    /**
     * Adds a UiMode qualifier override.
     * 
     * @param uiMode UI mode type
     * @param value Override value in dp
     * @return This builder for chaining
     */
    fun forUiMode(uiMode: GameUiModeType, value: Float): GameDimensBuilder {
        val newMap = customQualifiers.uiModeMap.toMutableMap()
        newMap[uiMode] = value
        this.customQualifiers = customQualifiers.copy(uiModeMap = newMap)
        return this
    }
    
    /**
     * Adds a screen size qualifier override.
     * 
     * @param qualifier Screen qualifier type
     * @param threshold Threshold value in dp
     * @param value Override value in dp
     * @return This builder for chaining
     */
    fun forScreenSize(
        qualifier: ScreenQualifier,
        threshold: Int,
        value: Float
    ): GameDimensBuilder {
        val newMap = customQualifiers.screenSizeMap.toMutableMap()
        newMap[ScreenQualifierEntry(qualifier, threshold)] = value
        this.customQualifiers = customQualifiers.copy(screenSizeMap = newMap)
        return this
    }
    
    /**
     * Shortcut for TV-specific value.
     * 
     * @param value Override value for TV in dp
     * @return This builder for chaining
     */
    fun forTV(value: Float): GameDimensBuilder {
        return forUiMode(GameUiModeType.TELEVISION, value)
    }
    
    /**
     * Shortcut for tablet-specific value (sw600dp).
     * 
     * @param value Override value for tablets in dp
     * @return This builder for chaining
     */
    fun forTablet(value: Float): GameDimensBuilder {
        return forScreenSize(ScreenQualifier.SMALL_WIDTH, 600, value)
    }
    
    /**
     * Shortcut for large tablet-specific value (sw720dp).
     * 
     * @param value Override value for large tablets in dp
     * @return This builder for chaining
     */
    fun forLargeTablet(value: Float): GameDimensBuilder {
        return forScreenSize(ScreenQualifier.SMALL_WIDTH, 720, value)
    }
    
    // ============================================
    // BUILD
    // ============================================
    
    /**
     * Builds and calculates the final dimension value.
     * 
     * @param resources Android Resources for screen configuration
     * @return Calculated dimension value in dp
     */
    fun build(resources: Resources): Float {
        // Create screen configuration from resources
        val config = GameScreenConfig(
            screenWidthDp = resources.configuration.screenWidthDp.toFloat(),
            screenHeightDp = resources.configuration.screenHeightDp.toFloat(),
            smallestScreenWidthDp = resources.configuration.smallestScreenWidthDp.toFloat(),
            densityDpi = resources.configuration.densityDpi,
            uiMode = GameUiModeType.fromAndroidUiMode(resources.configuration.uiMode)
        )
        
        // Perform calculation
        return GamesCalculator.calculate(
            baseValue = baseValue,
            strategy = strategy,
            elementType = elementType,
            config = config,
            screenType = screenType,
            baseOrientation = baseOrientation,
            defaultParams = defaultParams,
            perceptualParams = perceptualParams,
            fluidParams = fluidParams,
            constraints = constraints,
            customQualifiers = customQualifiers
        )
    }
    
    /**
     * Builds and calculates the final dimension value with custom config.
     * 
     * @param config Custom screen configuration
     * @return Calculated dimension value in dp
     */
    fun build(config: GameScreenConfig): Float {
        return GamesCalculator.calculate(
            baseValue = baseValue,
            strategy = strategy,
            elementType = elementType,
            config = config,
            screenType = screenType,
            baseOrientation = baseOrientation,
            defaultParams = defaultParams,
            perceptualParams = perceptualParams,
            fluidParams = fluidParams,
            constraints = constraints,
            customQualifiers = customQualifiers
        )
    }
}

