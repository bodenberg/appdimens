/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Game Dimensions
 *
 * Description:
 * Game-specific dimension calculations and scaling utilities for responsive game development.
 * Provides dynamic and fixed scaling models optimized for game UI and world coordinates.
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

#ifndef GAME_DIMENSIONS_H
#define GAME_DIMENSIONS_H

#include "AppDimensGames.h"
#include "LRUCache.h"
#include <unordered_map>
#include <string>

class GameDimensions {
public:
    GameDimensions();
    ~GameDimensions();
    
    // Initialize with screen configuration
    void initialize(const GameScreenConfig& config);
    
    // Dynamic scaling (proportional) - ideal for containers and fluid layouts
    float calculateDynamicDimension(float baseValue);
    Vector2D calculateDynamicVector2D(const Vector2D& baseVector);
    Rectangle calculateDynamicRectangle(const Rectangle& baseRect);
    
    // Fixed scaling (logarithmic) - ideal for UI elements, buttons, margins
    float calculateFixedDimension(float baseValue);
    Vector2D calculateFixedVector2D(const Vector2D& baseVector);
    Rectangle calculateFixedRectangle(const Rectangle& baseRect);
    
    // Game world scaling - maintains consistent world coordinates
    float calculateGameWorldDimension(float baseValue);
    Vector2D calculateGameWorldVector2D(const Vector2D& baseVector);
    Rectangle calculateGameWorldRectangle(const Rectangle& baseRect);
    
    // UI overlay scaling - for HUD and overlay elements
    float calculateUIOverlayDimension(float baseValue);
    Vector2D calculateUIOverlayVector2D(const Vector2D& baseVector);
    Rectangle calculateUIOverlayRectangle(const Rectangle& baseRect);
    
    // Screen-based calculations
    float getScreenWidth() const;
    float getScreenHeight() const;
    float getScreenAspectRatio() const;
    float getScreenDensity() const;
    
    // Reference dimensions (base design resolution)
    void setReferenceResolution(float width, float height);
    Vector2D getReferenceResolution() const;
    
    // Scaling factors
    float getDynamicScaleFactor() const;
    float getFixedScaleFactor() const;
    float getGameWorldScaleFactor() const;
    float getUIOverlayScaleFactor() const;
    
    // Utility functions
    bool isTablet() const;
    bool isLandscape() const;
    ScreenOrientation getOrientation() const;
    
    // Preset dimension calculations
    float calculateButtonSize(float baseSize = 48.0f);
    float calculateTextSize(float baseSize = 16.0f);
    float calculateMarginSize(float baseSize = 16.0f);
    float calculatePaddingSize(float baseSize = 8.0f);
    
    // Game-specific presets
    float calculatePlayerSize(float baseSize = 64.0f);
    float calculateEnemySize(float baseSize = 32.0f);
    float calculatePowerUpSize(float baseSize = 24.0f);
    float calculateProjectileSize(float baseSize = 8.0f);
    
    // Viewport calculations
    Rectangle calculateSafeArea();
    Rectangle calculateGameArea();
    Rectangle calculateUIArea();
    
    // Coordinate conversion
    Vector2D screenToWorld(const Vector2D& screenPos);
    Vector2D worldToScreen(const Vector2D& worldPos);
    Vector2D screenToUI(const Vector2D& screenPos);
    Vector2D uiToScreen(const Vector2D& uiPos);
    
private:
    GameScreenConfig screenConfig;
    Vector2D referenceResolution;
    
    // Scaling factors
    float dynamicScaleFactor;
    float fixedScaleFactor;
    float gameWorldScaleFactor;
    float uiOverlayScaleFactor;
    
    // Cached calculations with LRU eviction
    mutable AppDimensGamesCache::LRUCache<size_t, float> dimensionCache;
    
    // Internal calculation methods
    float calculateDynamicScaleFactor() const;
    float calculateFixedScaleFactor() const;
    float calculateGameWorldScaleFactor() const;
    float calculateUIOverlayScaleFactor() const;
    
    // Cache management
    size_t getCacheKey(const std::string& prefix, float value) const;
    float getCachedDimension(size_t key) const;
    void setCachedDimension(size_t key, float value) const;
    void clearCache();
    void setCacheMaxSize(size_t maxSize);
    size_t getCacheSize() const;
    size_t getCacheMaxSize() const;
    
    // Mathematical utilities
    float logScale(float value, float base = 2.0f) const;
    float clamp(float value, float min, float max) const;
    float lerp(float a, float b, float t) const;
};

#endif // GAME_DIMENSIONS_H
