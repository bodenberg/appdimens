/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Calculator (C++)
 *
 * Description:
 * C++ calculation engine for all 13 scaling strategies.
 * Optimized for maximum performance in game loops.
 *
 * Licensed under the Apache License, Version 2.0
 */

#ifndef GAME_CALCULATOR_H
#define GAME_CALCULATOR_H

#include "GameScalingStrategy.h"
#include "GameLookupTables.h"
#include <cmath>
#include <algorithm>

/**
 * Game screen configuration for calculations.
 */
struct GameScreenConfigNative {
    float screenWidthDp;
    float screenHeightDp;
    float smallestScreenWidthDp;
    int densityDpi;
    int uiMode;
    
    GameScreenConfigNative()
        : screenWidthDp(360.0f)
        , screenHeightDp(640.0f)
        , smallestScreenWidthDp(360.0f)
        , densityDpi(420)
        , uiMode(0) {}
    
    GameScreenConfigNative(float w, float h, float sw, int dpi, int mode)
        : screenWidthDp(w)
        , screenHeightDp(h)
        , smallestScreenWidthDp(sw)
        , densityDpi(dpi)
        , uiMode(mode) {}
};

/**
 * Main calculator class for game dimensions.
 * 
 * Implements all 13 scaling strategies with optimal performance.
 * Thread-safe, optimized for game loops (60+ FPS).
 */
class GameCalculator {
public:
    /**
     * Calculates dimension value based on strategy.
     * 
     * @param baseValue Base value in dp
     * @param strategy Scaling strategy to use
     * @param config Screen configuration
     * @param screenType Screen type (LOWEST or HIGHEST)
     * @param baseOrientation Base orientation for auto-inversion
     * @return Calculated value in dp
     */
    static float calculate(
        float baseValue,
        GameScalingStrategy strategy,
        const GameScreenConfigNative& config,
        ScreenType screenType = ScreenType::LOWEST,
        BaseOrientation baseOrientation = BaseOrientation::AUTO
    );
    
    /**
     * Infers best strategy based on element type.
     * 
     * @param elementType Element type hint
     * @param config Screen configuration
     * @return Recommended scaling strategy
     */
    static GameScalingStrategy inferStrategy(
        GameElementType elementType,
        const GameScreenConfigNative& config
    );
    
    /**
     * Binary search for best preset (AutoSize).
     * 
     * @param presets Sorted array of presets
     * @param count Number of presets
     * @param availableSize Available container size
     * @return Best fitting preset
     */
    static float findBestPreset(
        const float* presets,
        int count,
        float availableSize
    );
    
private:
    // Strategy calculations
    static float calculateDefault(
        float baseValue,
        const GameScreenConfigNative& config,
        ScreenType screenType,
        BaseOrientation baseOrientation,
        bool applyAspectRatio = true
    );
    
    static float calculatePercentage(
        float baseValue,
        const GameScreenConfigNative& config,
        ScreenType screenType,
        BaseOrientation baseOrientation
    );
    
    static float calculateBalanced(
        float baseValue,
        const GameScreenConfigNative& config,
        ScreenType screenType,
        BaseOrientation baseOrientation,
        float sensitivity = GameLookup::DEFAULT_SENSITIVITY,
        float transitionPoint = GameLookup::DEFAULT_TRANSITION_POINT
    );
    
    static float calculateLogarithmic(
        float baseValue,
        const GameScreenConfigNative& config,
        ScreenType screenType,
        BaseOrientation baseOrientation,
        float sensitivity = GameLookup::DEFAULT_SENSITIVITY
    );
    
    static float calculatePower(
        float baseValue,
        const GameScreenConfigNative& config,
        ScreenType screenType,
        BaseOrientation baseOrientation,
        float exponent = GameLookup::DEFAULT_POWER_EXPONENT
    );
    
    static float calculateFluid(
        float baseValue,
        const GameScreenConfigNative& config,
        ScreenType screenType,
        BaseOrientation baseOrientation,
        float minValue,
        float maxValue,
        float minWidth = 320.0f,
        float maxWidth = 768.0f
    );
    
    static float calculateInterpolated(
        float baseValue,
        const GameScreenConfigNative& config,
        ScreenType screenType,
        BaseOrientation baseOrientation
    );
    
    static float calculateDiagonal(
        float baseValue,
        const GameScreenConfigNative& config
    );
    
    static float calculatePerimeter(
        float baseValue,
        const GameScreenConfigNative& config
    );
    
    static float calculateFit(
        float baseValue,
        const GameScreenConfigNative& config
    );
    
    static float calculateFill(
        float baseValue,
        const GameScreenConfigNative& config
    );
    
    // Helper methods
    static float getDimensionForType(
        const GameScreenConfigNative& config,
        ScreenType type,
        BaseOrientation baseOrientation
    );
    
    static ScreenType resolveScreenType(
        ScreenType requestedType,
        BaseOrientation baseOrientation,
        const GameScreenConfigNative& config
    );
};

#endif // GAME_CALCULATOR_H

