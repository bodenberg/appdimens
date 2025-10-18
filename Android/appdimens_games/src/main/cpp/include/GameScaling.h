/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Game Scaling
 *
 * Description:
 * Advanced scaling algorithms and utilities for game development.
 * Provides multiple scaling strategies optimized for different game types and scenarios.
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

#ifndef GAME_SCALING_H
#define GAME_SCALING_H

#include "AppDimensGames.h"
#include <functional>

// Scaling algorithms
enum class ScalingAlgorithm {
    LINEAR,         // Simple linear scaling
    LOGARITHMIC,    // Logarithmic scaling for better visual balance
    EXPONENTIAL,    // Exponential scaling for dramatic effects
    SMOOTHSTEP,     // Smooth interpolation
    BEZIER,         // Bezier curve scaling
    CUSTOM          // Custom scaling function
};

// Scaling presets for common game scenarios
enum class GameScalingPreset {
    MOBILE_2D,      // Mobile 2D games
    MOBILE_3D,      // Mobile 3D games
    TABLET_2D,      // Tablet 2D games
    TABLET_3D,      // Tablet 3D games
    CONSOLE,        // Console-style scaling
    PC,             // PC-style scaling
    VR,             // VR/AR scaling
    CUSTOM          // Custom preset
};

// Scaling configuration
struct ScalingConfig {
    ScalingAlgorithm algorithm;
    GameScalingPreset preset;
    float baseScale;
    float minScale;
    float maxScale;
    float scaleFactor;
    bool enableAdaptiveScaling;
    bool enablePerformanceScaling;
    
    ScalingConfig() : algorithm(ScalingAlgorithm::LOGARITHMIC),
                     preset(GameScalingPreset::MOBILE_2D),
                     baseScale(1.0f), minScale(0.5f), maxScale(2.0f),
                     scaleFactor(1.0f), enableAdaptiveScaling(true),
                     enablePerformanceScaling(false) {}
};

// Performance-based scaling
struct PerformanceScaling {
    float targetFPS;
    float currentFPS;
    float scaleAdjustment;
    bool isEnabled;
    
    PerformanceScaling() : targetFPS(60.0f), currentFPS(60.0f),
                          scaleAdjustment(1.0f), isEnabled(false) {}
};

class GameScaling {
public:
    GameScaling();
    ~GameScaling();
    
    // Initialize scaling system
    void initialize(const GameScreenConfig& config);
    
    // Configuration
    void setScalingConfig(const ScalingConfig& config);
    ScalingConfig getScalingConfig() const;
    
    void setScalingAlgorithm(ScalingAlgorithm algorithm);
    ScalingAlgorithm getScalingAlgorithm() const;
    
    void setGamePreset(GameScalingPreset preset);
    GameScalingPreset getGamePreset() const;
    
    // Basic scaling functions
    float scaleValue(float value, float referenceValue = 1.0f);
    Vector2D scaleVector2D(const Vector2D& vector, const Vector2D& referenceVector = Vector2D(1.0f, 1.0f));
    Rectangle scaleRectangle(const Rectangle& rect, const Rectangle& referenceRect = Rectangle(0, 0, 1, 1));
    
    // Advanced scaling with custom functions
    float scaleValueWithFunction(float value, std::function<float(float)> scalingFunction);
    Vector2D scaleVector2DWithFunction(const Vector2D& vector, std::function<float(float)> scalingFunction);
    
    // Preset-based scaling
    void applyPreset(GameScalingPreset preset);
    float getPresetScaleFactor(GameScalingPreset preset) const;
    
    // Adaptive scaling based on performance
    void enablePerformanceScaling(bool enable);
    void updatePerformanceMetrics(const PerformanceMetrics& metrics);
    float getPerformanceAdjustedScale() const;
    
    // Screen-based scaling
    float getScreenBasedScale() const;
    float getDensityBasedScale() const;
    float getAspectRatioBasedScale() const;
    
    // Multi-resolution scaling
    void setReferenceResolution(float width, float height);
    Vector2D getReferenceResolution() const;
    float getResolutionScale() const;
    
    // Scaling for different game elements
    float scaleUIElement(float baseSize);
    float scaleGameObject(float baseSize);
    float scaleTextSize(float baseSize);
    float scaleParticleSize(float baseSize);
    float scaleEffectSize(float baseSize);
    
    // Animation scaling
    float scaleAnimationDuration(float baseDuration);
    float scaleAnimationSpeed(float baseSpeed);
    
    // Physics scaling
    float scalePhysicsForce(float baseForce);
    float scalePhysicsMass(float baseMass);
    float scalePhysicsVelocity(float baseVelocity);
    
    // Audio scaling
    float scaleAudioVolume(float baseVolume);
    float scaleAudioPitch(float basePitch);
    
    // Utility functions
    float clampScale(float scale) const;
    bool isScaleValid(float scale) const;
    float interpolateScale(float from, float to, float t) const;
    
    // Batch scaling operations
    void scaleValues(float* values, int count, float referenceValue = 1.0f);
    void scaleVectors(Vector2D* vectors, int count, const Vector2D& referenceVector = Vector2D(1.0f, 1.0f));
    void scaleRectangles(Rectangle* rectangles, int count, const Rectangle& referenceRect = Rectangle(0, 0, 1, 1));
    
    // Scaling curves and interpolation
    float applyScalingCurve(float value, float curveStrength = 1.0f);
    float smoothStepScale(float value, float edge0 = 0.0f, float edge1 = 1.0f);
    float bezierScale(float value, float p1, float p2);
    
    // Debug and visualization
    void drawScalingInfo() const;
    void logScalingStats() const;
    
private:
    GameScreenConfig screenConfig;
    ScalingConfig scalingConfig;
    PerformanceScaling performanceScaling;
    Vector2D referenceResolution;
    
    // Cached calculations
    mutable float cachedScreenScale;
    mutable float cachedDensityScale;
    mutable float cachedAspectRatioScale;
    mutable bool cacheValid;
    
    // Internal calculation methods
    float calculateLinearScale(float value, float reference) const;
    float calculateLogarithmicScale(float value, float reference) const;
    float calculateExponentialScale(float value, float reference) const;
    float calculateSmoothStepScale(float value, float reference) const;
    float calculateBezierScale(float value, float reference) const;
    
    // Preset calculations
    void calculatePresetScaling(GameScalingPreset preset);
    float getPresetBaseScale(GameScalingPreset preset) const;
    float getPresetMinScale(GameScalingPreset preset) const;
    float getPresetMaxScale(GameScalingPreset preset) const;
    
    // Performance-based calculations
    float calculatePerformanceScale() const;
    void updatePerformanceAdjustment();
    
    // Cache management
    void invalidateCache();
    void updateCache() const;
    
    // Mathematical utilities
    float lerp(float a, float b, float t) const;
    float clamp(float value, float min, float max) const;
    float smoothStep(float edge0, float edge1, float x) const;
    float bezierInterpolation(float t, float p0, float p1, float p2, float p3) const;
};

#endif // GAME_SCALING_H
