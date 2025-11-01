/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Game Dimensions Implementation
 *
 * Description:
 * Implementation of game-specific dimension calculations and scaling utilities.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "GameDimensions.h"
#include <algorithm>
#include <cmath>
#include <sstream>

GameDimensions::GameDimensions() : dimensionCache(1000) {
    LOGI("GameDimensions constructor called");
    
    // Initialize with default values
    referenceResolution = Vector2D(1920.0f, 1080.0f); // Full HD as default reference
    dynamicScaleFactor = 1.0f;
    fixedScaleFactor = 1.0f;
    gameWorldScaleFactor = 1.0f;
    uiOverlayScaleFactor = 1.0f;
}

GameDimensions::~GameDimensions() {
    LOGI("GameDimensions destructor called");
    clearCache();
}

void GameDimensions::initialize(const GameScreenConfig& config) {
    LOGI("GameDimensions initializing with screen: %dx%d, density: %.2f", 
         config.width, config.height, config.density);
    
    screenConfig = config;
    
    // Calculate scaling factors
    dynamicScaleFactor = calculateDynamicScaleFactor();
    fixedScaleFactor = calculateFixedScaleFactor();
    gameWorldScaleFactor = calculateGameWorldScaleFactor();
    uiOverlayScaleFactor = calculateUIOverlayScaleFactor();
    
    // Clear cache when configuration changes
    clearCache();
    
    LOGI("Scaling factors - Dynamic: %.3f, Fixed: %.3f, GameWorld: %.3f, UIOverlay: %.3f",
         dynamicScaleFactor, fixedScaleFactor, gameWorldScaleFactor, uiOverlayScaleFactor);
}

float GameDimensions::calculateDynamicDimension(float baseValue) {
    if (baseValue <= 0.0f) return 0.0f;
    
    // Use cached value if available
    size_t cacheKey = getCacheKey("dynamic", baseValue);
    float cachedValue = getCachedDimension(cacheKey);
    if (cachedValue > 0.0f) {
        return cachedValue;
    }
    
    // Calculate dynamic scaling (proportional)
    float result = baseValue * dynamicScaleFactor;
    
    // Cache the result
    setCachedDimension(cacheKey, result);
    
    return result;
}

Vector2D GameDimensions::calculateDynamicVector2D(const Vector2D& baseVector) {
    return Vector2D(
        calculateDynamicDimension(baseVector.x),
        calculateDynamicDimension(baseVector.y)
    );
}

Rectangle GameDimensions::calculateDynamicRectangle(const Rectangle& baseRect) {
    return Rectangle(
        calculateDynamicDimension(baseRect.x),
        calculateDynamicDimension(baseRect.y),
        calculateDynamicDimension(baseRect.width),
        calculateDynamicDimension(baseRect.height)
    );
}

float GameDimensions::calculateFixedDimension(float baseValue) {
    if (baseValue <= 0.0f) return 0.0f;
    
    // Use cached value if available
    size_t cacheKey = getCacheKey("fixed", baseValue);
    float cachedValue = getCachedDimension(cacheKey);
    if (cachedValue > 0.0f) {
        return cachedValue;
    }
    
    // Calculate fixed scaling (logarithmic)
    float result = baseValue * fixedScaleFactor;
    
    // Cache the result
    setCachedDimension(cacheKey, result);
    
    return result;
}

Vector2D GameDimensions::calculateFixedVector2D(const Vector2D& baseVector) {
    return Vector2D(
        calculateFixedDimension(baseVector.x),
        calculateFixedDimension(baseVector.y)
    );
}

Rectangle GameDimensions::calculateFixedRectangle(const Rectangle& baseRect) {
    return Rectangle(
        calculateFixedDimension(baseRect.x),
        calculateFixedDimension(baseRect.y),
        calculateFixedDimension(baseRect.width),
        calculateFixedDimension(baseRect.height)
    );
}

float GameDimensions::calculateGameWorldDimension(float baseValue) {
    if (baseValue <= 0.0f) return 0.0f;
    
    // Use cached value if available
    size_t cacheKey = getCacheKey("gameworld", baseValue);
    float cachedValue = getCachedDimension(cacheKey);
    if (cachedValue > 0.0f) {
        return cachedValue;
    }
    
    // Calculate game world scaling (maintains consistent world coordinates)
    float result = baseValue * gameWorldScaleFactor;
    
    // Cache the result
    setCachedDimension(cacheKey, result);
    
    return result;
}

Vector2D GameDimensions::calculateGameWorldVector2D(const Vector2D& baseVector) {
    return Vector2D(
        calculateGameWorldDimension(baseVector.x),
        calculateGameWorldDimension(baseVector.y)
    );
}

Rectangle GameDimensions::calculateGameWorldRectangle(const Rectangle& baseRect) {
    return Rectangle(
        calculateGameWorldDimension(baseRect.x),
        calculateGameWorldDimension(baseRect.y),
        calculateGameWorldDimension(baseRect.width),
        calculateGameWorldDimension(baseRect.height)
    );
}

float GameDimensions::calculateUIOverlayDimension(float baseValue) {
    if (baseValue <= 0.0f) return 0.0f;
    
    // Use cached value if available
    size_t cacheKey = getCacheKey("uioverlay", baseValue);
    float cachedValue = getCachedDimension(cacheKey);
    if (cachedValue > 0.0f) {
        return cachedValue;
    }
    
    // Calculate UI overlay scaling (for HUD and overlay elements)
    float result = baseValue * uiOverlayScaleFactor;
    
    // Cache the result
    setCachedDimension(cacheKey, result);
    
    return result;
}

Vector2D GameDimensions::calculateUIOverlayVector2D(const Vector2D& baseVector) {
    return Vector2D(
        calculateUIOverlayDimension(baseVector.x),
        calculateUIOverlayDimension(baseVector.y)
    );
}

Rectangle GameDimensions::calculateUIOverlayRectangle(const Rectangle& baseRect) {
    return Rectangle(
        calculateUIOverlayDimension(baseRect.x),
        calculateUIOverlayDimension(baseRect.y),
        calculateUIOverlayDimension(baseRect.width),
        calculateUIOverlayDimension(baseRect.height)
    );
}

float GameDimensions::getScreenWidth() const {
    return static_cast<float>(screenConfig.width);
}

float GameDimensions::getScreenHeight() const {
    return static_cast<float>(screenConfig.height);
}

float GameDimensions::getScreenAspectRatio() const {
    float width = getScreenWidth();
    float height = getScreenHeight();
    return (height > 0.0f) ? (width / height) : 1.0f;
}

float GameDimensions::getScreenDensity() const {
    return screenConfig.density;
}

void GameDimensions::setReferenceResolution(float width, float height) {
    referenceResolution = Vector2D(width, height);
    
    // Recalculate scaling factors
    dynamicScaleFactor = calculateDynamicScaleFactor();
    fixedScaleFactor = calculateFixedScaleFactor();
    gameWorldScaleFactor = calculateGameWorldScaleFactor();
    uiOverlayScaleFactor = calculateUIOverlayScaleFactor();
    
    // Clear cache when reference resolution changes
    clearCache();
    
    LOGI("Reference resolution set to: %.0fx%.0f", width, height);
}

Vector2D GameDimensions::getReferenceResolution() const {
    return referenceResolution;
}

float GameDimensions::getDynamicScaleFactor() const {
    return dynamicScaleFactor;
}

float GameDimensions::getFixedScaleFactor() const {
    return fixedScaleFactor;
}

float GameDimensions::getGameWorldScaleFactor() const {
    return gameWorldScaleFactor;
}

float GameDimensions::getUIOverlayScaleFactor() const {
    return uiOverlayScaleFactor;
}

bool GameDimensions::isTablet() const {
    return screenConfig.isTablet;
}

bool GameDimensions::isLandscape() const {
    return screenConfig.isLandscape;
}

ScreenOrientation GameDimensions::getOrientation() const {
    return screenConfig.isLandscape ? ScreenOrientation::LANDSCAPE : ScreenOrientation::PORTRAIT;
}

float GameDimensions::calculateButtonSize(float baseSize) {
    return calculateFixedDimension(baseSize);
}

float GameDimensions::calculateTextSize(float baseSize) {
    return calculateFixedDimension(baseSize);
}

float GameDimensions::calculateMarginSize(float baseSize) {
    return calculateFixedDimension(baseSize);
}

float GameDimensions::calculatePaddingSize(float baseSize) {
    return calculateFixedDimension(baseSize);
}

float GameDimensions::calculatePlayerSize(float baseSize) {
    return calculateGameWorldDimension(baseSize);
}

float GameDimensions::calculateEnemySize(float baseSize) {
    return calculateGameWorldDimension(baseSize);
}

float GameDimensions::calculatePowerUpSize(float baseSize) {
    return calculateGameWorldDimension(baseSize);
}

float GameDimensions::calculateProjectileSize(float baseSize) {
    return calculateGameWorldDimension(baseSize);
}

Rectangle GameDimensions::calculateSafeArea() {
    // Calculate safe area based on screen dimensions and device type
    float margin = calculateFixedDimension(16.0f);
    return Rectangle(
        margin,
        margin,
        getScreenWidth() - (margin * 2.0f),
        getScreenHeight() - (margin * 2.0f)
    );
}

Rectangle GameDimensions::calculateGameArea() {
    // Calculate game area (usually the full screen for games)
    return Rectangle(0.0f, 0.0f, getScreenWidth(), getScreenHeight());
}

Rectangle GameDimensions::calculateUIArea() {
    // Calculate UI area (overlay area for HUD elements)
    return Rectangle(0.0f, 0.0f, getScreenWidth(), getScreenHeight());
}

Vector2D GameDimensions::screenToWorld(const Vector2D& screenPos) {
    // Convert screen coordinates to world coordinates
    // This is a basic implementation - can be enhanced based on game needs
    return Vector2D(
        screenPos.x * gameWorldScaleFactor,
        screenPos.y * gameWorldScaleFactor
    );
}

Vector2D GameDimensions::worldToScreen(const Vector2D& worldPos) {
    // Convert world coordinates to screen coordinates
    return Vector2D(
        worldPos.x / gameWorldScaleFactor,
        worldPos.y / gameWorldScaleFactor
    );
}

Vector2D GameDimensions::screenToUI(const Vector2D& screenPos) {
    // Convert screen coordinates to UI coordinates
    return Vector2D(
        screenPos.x * uiOverlayScaleFactor,
        screenPos.y * uiOverlayScaleFactor
    );
}

Vector2D GameDimensions::uiToScreen(const Vector2D& uiPos) {
    // Convert UI coordinates to screen coordinates
    return Vector2D(
        uiPos.x / uiOverlayScaleFactor,
        uiPos.y / uiOverlayScaleFactor
    );
}

// Private methods

float GameDimensions::calculateDynamicScaleFactor() const {
    // Dynamic scaling based on screen size relative to reference resolution
    float screenWidth = getScreenWidth();
    float screenHeight = getScreenHeight();
    float screenDiagonal = sqrtf(screenWidth * screenWidth + screenHeight * screenHeight);
    
    float refWidth = referenceResolution.x;
    float refHeight = referenceResolution.y;
    float refDiagonal = sqrtf(refWidth * refWidth + refHeight * refHeight);
    
    return (refDiagonal > 0.0f) ? (screenDiagonal / refDiagonal) : 1.0f;
}

float GameDimensions::calculateFixedScaleFactor() const {
    // Unified formula: 1.0 + ((dimension - BASE_WIDTH) / STEP) × (BASE_INCREMENT + K × ln(AR / AR₀))
    float smallestDimension = std::min(static_cast<float>(screenConfig.width), 
                                      static_cast<float>(screenConfig.height));
    
    // Calculate difference from base width
    float difference = smallestDimension - BASE_WIDTH_DP;
    float adjustmentFactor = difference / INCREMENT_DP_STEP;
    
    // Calculate aspect ratio (normalized to landscape: largest/smallest)
    float width = static_cast<float>(screenConfig.width);
    float height = static_cast<float>(screenConfig.height);
    float aspectRatio = (width >= height) ? (width / height) : (height / width);
    
    // Unified logarithmic adjustment
    float arAdjustment = DEFAULT_SENSITIVITY_K * logf(aspectRatio / REFERENCE_AR);
    float finalIncrement = BASE_INCREMENT + arAdjustment;
    
    // Calculate final factor using unified formula
    return BASE_DP_FACTOR + adjustmentFactor * finalIncrement;
}

float GameDimensions::calculateGameWorldScaleFactor() const {
    // Game world scaling maintains consistent world coordinates
    // Use the smaller dimension to ensure content fits
    float screenWidth = getScreenWidth();
    float screenHeight = getScreenHeight();
    float refWidth = referenceResolution.x;
    float refHeight = referenceResolution.y;
    
    float scaleX = (refWidth > 0.0f) ? (screenWidth / refWidth) : 1.0f;
    float scaleY = (refHeight > 0.0f) ? (screenHeight / refHeight) : 1.0f;
    
    return std::min(scaleX, scaleY);
}

float GameDimensions::calculateUIOverlayScaleFactor() const {
    // UI overlay scaling for HUD elements
    return calculateFixedScaleFactor();
}

size_t GameDimensions::getCacheKey(const std::string& prefix, float value) const {
    // Optimized hash-based cache key generation
    size_t hash = std::hash<std::string>{}(prefix);
    hash ^= std::hash<float>{}(value) + 0x9e3779b9 + (hash << 6) + (hash >> 2);
    hash ^= std::hash<int>{}(screenConfig.width) + 0x9e3779b9 + (hash << 6) + (hash >> 2);
    hash ^= std::hash<int>{}(screenConfig.height) + 0x9e3779b9 + (hash << 6) + (hash >> 2);
    return hash;
}

float GameDimensions::getCachedDimension(size_t key) const {
    float* cached = dimensionCache.get(key);
    return cached ? *cached : 0.0f;
}

void GameDimensions::setCachedDimension(size_t key, float value) const {
    dimensionCache.put(key, value);
}

void GameDimensions::clearCache() {
    dimensionCache.clear();
}

void GameDimensions::setCacheMaxSize(size_t maxSize) {
    dimensionCache.setMaxSize(maxSize);
}

size_t GameDimensions::getCacheSize() const {
    return dimensionCache.size();
}

size_t GameDimensions::getCacheMaxSize() const {
    return dimensionCache.maxSize();
}

float GameDimensions::logScale(float value, float base) const {
    if (value <= 0.0f || base <= 0.0f) return 1.0f;
    return powf(base, logf(value) / logf(base));
}

float GameDimensions::clamp(float value, float min, float max) const {
    return std::max(min, std::min(max, value));
}

float GameDimensions::lerp(float a, float b, float t) const {
    return a + (b - a) * clamp(t, 0.0f, 1.0f);
}
