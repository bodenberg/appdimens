/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Calculator Implementation (C++)
 *
 * Description:
 * Implementation of all 13 scaling strategies in optimized C++.
 * Designed for maximum performance in game loops (60+ FPS).
 *
 * Licensed under the Apache License, Version 2.0
 */

#include "GameCalculator.h"
#include "GameLookupTables.h"
#include <algorithm>
#include <cmath>

using namespace GameLookup;

// ============================================
// MAIN CALCULATION ENTRY POINT
// ============================================

float GameCalculator::calculate(
    float baseValue,
    GameScalingStrategy strategy,
    const GameScreenConfigNative& config,
    ScreenType screenType,
    BaseOrientation baseOrientation
) {
    // Fast path: NONE strategy
    if (strategy == GameScalingStrategy::NONE) {
        return baseValue;
    }
    
    // Route to appropriate strategy
    switch (strategy) {
        case GameScalingStrategy::DEFAULT:
            return calculateDefault(baseValue, config, screenType, baseOrientation);
            
        case GameScalingStrategy::PERCENTAGE:
            return calculatePercentage(baseValue, config, screenType, baseOrientation);
            
        case GameScalingStrategy::BALANCED:
            return calculateBalanced(baseValue, config, screenType, baseOrientation);
            
        case GameScalingStrategy::LOGARITHMIC:
            return calculateLogarithmic(baseValue, config, screenType, baseOrientation);
            
        case GameScalingStrategy::POWER:
            return calculatePower(baseValue, config, screenType, baseOrientation);
            
        case GameScalingStrategy::INTERPOLATED:
            return calculateInterpolated(baseValue, config, screenType, baseOrientation);
            
        case GameScalingStrategy::DIAGONAL:
            return calculateDiagonal(baseValue, config);
            
        case GameScalingStrategy::PERIMETER:
            return calculatePerimeter(baseValue, config);
            
        case GameScalingStrategy::FIT:
            return calculateFit(baseValue, config);
            
        case GameScalingStrategy::FILL:
            return calculateFill(baseValue, config);
            
        case GameScalingStrategy::NONE:
            return baseValue;
            
        default:
            return baseValue;
    }
}

// ============================================
// STRATEGY IMPLEMENTATIONS
// ============================================

float GameCalculator::calculateDefault(
    float baseValue,
    const GameScreenConfigNative& config,
    ScreenType screenType,
    BaseOrientation baseOrientation,
    bool applyAspectRatio
) {
    float dimensionDp = getDimensionForType(config, screenType, baseOrientation);
    float difference = dimensionDp - BASE_WIDTH_DP;
    float adjustmentFactor = difference;
    float factor = 1.0f + adjustmentFactor * 0.00333f;
    
    if (applyAspectRatio) {
        float smallest = std::min(config.screenWidthDp, config.screenHeightDp);
        float largest = std::max(config.screenWidthDp, config.screenHeightDp);
        float ar = largest / smallest;
        
        float continuousAdjustment = DEFAULT_AR_SENSITIVITY * LnLookup::fastLn(ar * INV_REFERENCE_AR);
        float incrementValue = BASE_INCREMENT + continuousAdjustment;
        factor = 1.0f + adjustmentFactor * incrementValue;
    }
    
    return baseValue * factor;
}

float GameCalculator::calculatePercentage(
    float baseValue,
    const GameScreenConfigNative& config,
    ScreenType screenType,
    BaseOrientation baseOrientation
) {
    float dimensionDp = getDimensionForType(config, screenType, baseOrientation);
    return baseValue * (dimensionDp * INV_BASE_WIDTH_DP);
}

float GameCalculator::calculateBalanced(
    float baseValue,
    const GameScreenConfigNative& config,
    ScreenType screenType,
    BaseOrientation baseOrientation,
    float sensitivity,
    float transitionPoint
) {
    float screenDp = getDimensionForType(config, screenType, baseOrientation);
    
    if (screenDp <= transitionPoint) {
        // Linear region
        return baseValue * (screenDp * INV_BASE_WIDTH_DP);
    } else {
        // Logarithmic region
        float excess = screenDp - transitionPoint;
        float scale = (transitionPoint * INV_BASE_WIDTH_DP) +
                     sensitivity * LnLookup::fastLn(1.0f + excess * INV_BASE_WIDTH_DP);
        return baseValue * scale;
    }
}

float GameCalculator::calculateLogarithmic(
    float baseValue,
    const GameScreenConfigNative& config,
    ScreenType screenType,
    BaseOrientation baseOrientation,
    float sensitivity
) {
    float screenDp = getDimensionForType(config, screenType, baseOrientation);
    
    float scale;
    if (screenDp > BASE_WIDTH_DP) {
        scale = 1.0f + sensitivity * LnLookup::fastLn(screenDp * INV_BASE_WIDTH_DP);
    } else {
        scale = 1.0f - sensitivity * LnLookup::fastLn(BASE_WIDTH_DP / screenDp);
    }
    
    return baseValue * scale;
}

float GameCalculator::calculatePower(
    float baseValue,
    const GameScreenConfigNative& config,
    ScreenType screenType,
    BaseOrientation baseOrientation,
    float exponent
) {
    float screenDp = getDimensionForType(config, screenType, baseOrientation);
    float ratio = screenDp / BASE_WIDTH_DP;
    float scale = std::pow(ratio, exponent);
    return baseValue * scale;
}

float GameCalculator::calculateFluid(
    float baseValue,
    const GameScreenConfigNative& config,
    ScreenType screenType,
    BaseOrientation baseOrientation,
    float minValue,
    float maxValue,
    float minWidth,
    float maxWidth
) {
    float width = getDimensionForType(config, screenType, baseOrientation);
    
    if (width <= minWidth) {
        return minValue;
    } else if (width >= maxWidth) {
        return maxValue;
    } else {
        float progress = (width - minWidth) / (maxWidth - minWidth);
        return minValue + (maxValue - minValue) * progress;
    }
}

float GameCalculator::calculateInterpolated(
    float baseValue,
    const GameScreenConfigNative& config,
    ScreenType screenType,
    BaseOrientation baseOrientation
) {
    float W = getDimensionForType(config, screenType, baseOrientation);
    float linear = baseValue * (W / BASE_WIDTH_DP);
    return baseValue + (linear - baseValue) * 0.5f;
}

float GameCalculator::calculateDiagonal(
    float baseValue,
    const GameScreenConfigNative& config
) {
    float smallest = std::min(config.screenWidthDp, config.screenHeightDp);
    float largest = std::max(config.screenWidthDp, config.screenHeightDp);
    float currentDiag = std::sqrt(smallest * smallest + largest * largest);
    return baseValue * (currentDiag / BASE_DIAGONAL);
}

float GameCalculator::calculatePerimeter(
    float baseValue,
    const GameScreenConfigNative& config
) {
    float smallest = std::min(config.screenWidthDp, config.screenHeightDp);
    float largest = std::max(config.screenWidthDp, config.screenHeightDp);
    return baseValue * ((smallest + largest) / BASE_PERIMETER);
}

float GameCalculator::calculateFit(
    float baseValue,
    const GameScreenConfigNative& config
) {
    float smallest = std::min(config.screenWidthDp, config.screenHeightDp);
    float largest = std::max(config.screenWidthDp, config.screenHeightDp);
    float ratioW = smallest / BASE_WIDTH_DP;
    float ratioH = largest / BASE_HEIGHT_DP;
    return baseValue * std::min(ratioW, ratioH);
}

float GameCalculator::calculateFill(
    float baseValue,
    const GameScreenConfigNative& config
) {
    float smallest = std::min(config.screenWidthDp, config.screenHeightDp);
    float largest = std::max(config.screenWidthDp, config.screenHeightDp);
    float ratioW = smallest / BASE_WIDTH_DP;
    float ratioH = largest / BASE_HEIGHT_DP;
    return baseValue * std::max(ratioW, ratioH);
}

// ============================================
// HELPER METHODS
// ============================================

float GameCalculator::getDimensionForType(
    const GameScreenConfigNative& config,
    ScreenType type,
    BaseOrientation baseOrientation
) {
    ScreenType effectiveType = resolveScreenType(type, baseOrientation, config);
    
    switch (effectiveType) {
        case ScreenType::HIGHEST:
            return std::max(config.screenWidthDp, config.screenHeightDp);
        case ScreenType::LOWEST:
            return std::min(config.screenWidthDp, config.screenHeightDp);
        default:
            return std::min(config.screenWidthDp, config.screenHeightDp);
    }
}

ScreenType GameCalculator::resolveScreenType(
    ScreenType requestedType,
    BaseOrientation baseOrientation,
    const GameScreenConfigNative& config
) {
    if (baseOrientation == BaseOrientation::AUTO) {
        return requestedType;
    }
    
    bool currentIsPortrait = config.screenHeightDp > config.screenWidthDp;
    bool shouldInvert = false;
    
    switch (baseOrientation) {
        case BaseOrientation::PORTRAIT:
            shouldInvert = !currentIsPortrait;
            break;
        case BaseOrientation::LANDSCAPE:
            shouldInvert = currentIsPortrait;
            break;
        case BaseOrientation::AUTO:
            shouldInvert = false;
            break;
    }
    
    if (shouldInvert) {
        return (requestedType == ScreenType::LOWEST) ? 
               ScreenType::HIGHEST : ScreenType::LOWEST;
    } else {
        return requestedType;
    }
}

// ============================================
// STRATEGY INFERENCE
// ============================================

GameScalingStrategy GameCalculator::inferStrategy(
    GameElementType elementType,
    const GameScreenConfigNative& config
) {
    // Map element types to recommended strategies
    switch (elementType) {
        // HUD Elements - DEFAULT for consistency
        case GameElementType::HUD_BUTTON:
        case GameElementType::HUD_ICON:
        case GameElementType::HUD_BAR:
        case GameElementType::HUD_CROSSHAIR:
            return GameScalingStrategy::DEFAULT;
        
        // Text - FLUID for readability
        case GameElementType::HUD_TEXT:
        case GameElementType::TEXT:
        case GameElementType::DIALOGUE:
        case GameElementType::CAPTION:
        case GameElementType::FLOATING_TEXT:
        case GameElementType::QUEST_TEXT:
        case GameElementType::LORE_TEXT:
            return GameScalingStrategy::FLUID;
        
        // Containers - PERCENTAGE
        case GameElementType::HUD_CONTAINER:
        case GameElementType::CONTAINER:
        case GameElementType::INVENTORY:
        case GameElementType::ABILITY_PANEL:
        case GameElementType::CARD:
        case GameElementType::LIST_ITEM:
            return GameScalingStrategy::PERCENTAGE;
        
        // Characters - BALANCED
        case GameElementType::PLAYER:
        case GameElementType::ENEMY:
        case GameElementType::BOSS:
        case GameElementType::NPC:
        case GameElementType::COMPANION:
        case GameElementType::VEHICLE:
            return GameScalingStrategy::BALANCED;
        
        // Game Objects - BALANCED
        case GameElementType::ITEM:
        case GameElementType::WEAPON:
        case GameElementType::PROJECTILE:
        case GameElementType::OBSTACLE:
        case GameElementType::INTERACTIVE_OBJECT:
        case GameElementType::DESTRUCTIBLE:
        case GameElementType::PICKUP:
        case GameElementType::TRAP:
        case GameElementType::WORLD_OBJECT:
        case GameElementType::BUILDING:
            return GameScalingStrategy::BALANCED;
        
        // Backgrounds - FILL
        case GameElementType::BACKGROUND:
        case GameElementType::PARALLAX_LAYER:
            return GameScalingStrategy::FILL;
        
        // Effects - BALANCED
        case GameElementType::PARTICLE:
        case GameElementType::VISUAL_EFFECT:
        case GameElementType::ANIMATION:
        case GameElementType::LIGHT_EFFECT:
            return GameScalingStrategy::BALANCED;
        
        // Divider - NONE
        case GameElementType::DIVIDER:
            return GameScalingStrategy::NONE;
        
        // Default - BALANCED
        default:
            return GameScalingStrategy::BALANCED;
    }
}

// ============================================
// BINARY SEARCH FOR PRESETS
// ============================================

float GameCalculator::findBestPreset(
    const float* presets,
    int count,
    float availableSize
) {
    if (count == 0) return availableSize;
    if (count == 1) return presets[0];
    
    // Fast paths
    if (availableSize >= presets[count - 1]) return presets[count - 1];
    if (availableSize < presets[0]) return presets[0];
    
    // Binary search for largest value <= availableSize
    int left = 0;
    int right = count - 1;
    float result = presets[0];
    
    while (left <= right) {
        int mid = (left + right) >> 1;  // Fast division by 2
        float midValue = presets[mid];
        
        if (midValue <= availableSize) {
            result = midValue;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    return result;
}

