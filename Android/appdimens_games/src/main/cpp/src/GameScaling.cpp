/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Game Scaling Implementation
 *
 * Description:
 * Implementation of advanced scaling algorithms and utilities for game development.
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

#include "GameScaling.h"
#include <algorithm>
#include <cmath>

GameScaling::GameScaling() {
    LOGI("GameScaling constructor called");
    
    // Initialize with default values
    scalingConfig.algorithm = ScalingAlgorithm::LOGARITHMIC;
    scalingConfig.preset = GameScalingPreset::MOBILE_2D;
    scalingConfig.baseScale = 1.0f;
    scalingConfig.minScale = 0.5f;
    scalingConfig.maxScale = 2.0f;
    scalingConfig.scaleFactor = 1.0f;
    scalingConfig.enableAdaptiveScaling = true;
    scalingConfig.enablePerformanceScaling = false;
    
    performanceScaling.targetFPS = 60.0f;
    performanceScaling.currentFPS = 60.0f;
    performanceScaling.scaleAdjustment = 1.0f;
    performanceScaling.isEnabled = false;
    
    referenceResolution = Vector2D(1920.0f, 1080.0f);
    
    // Initialize cache
    cacheValid = false;
    cachedScreenScale = 1.0f;
    cachedDensityScale = 1.0f;
    cachedAspectRatioScale = 1.0f;
}

GameScaling::~GameScaling() {
    LOGI("GameScaling destructor called");
}

void GameScaling::initialize(const GameScreenConfig& config) {
    LOGI("GameScaling initializing with screen: %dx%d, density: %.2f", 
         config.width, config.height, config.density);
    
    screenConfig = config;
    invalidateCache();
    updateCache();
}

void GameScaling::setScalingConfig(const ScalingConfig& config) {
    scalingConfig = config;
    invalidateCache();
}

ScalingConfig GameScaling::getScalingConfig() const {
    return scalingConfig;
}

void GameScaling::setScalingAlgorithm(ScalingAlgorithm algorithm) {
    scalingConfig.algorithm = algorithm;
    invalidateCache();
}

ScalingAlgorithm GameScaling::getScalingAlgorithm() const {
    return scalingConfig.algorithm;
}

void GameScaling::setGamePreset(GameScalingPreset preset) {
    scalingConfig.preset = preset;
    calculatePresetScaling(preset);
    invalidateCache();
}

GameScalingPreset GameScaling::getGamePreset() const {
    return scalingConfig.preset;
}

float GameScaling::scaleValue(float value, float referenceValue) {
    if (value <= 0.0f) return 0.0f;
    if (referenceValue <= 0.0f) referenceValue = 1.0f;
    
    float scale = value / referenceValue;
    
    switch (scalingConfig.algorithm) {
        case ScalingAlgorithm::LINEAR:
            return calculateLinearScale(scale, referenceValue);
        case ScalingAlgorithm::LOGARITHMIC:
            return calculateLogarithmicScale(scale, referenceValue);
        case ScalingAlgorithm::EXPONENTIAL:
            return calculateExponentialScale(scale, referenceValue);
        case ScalingAlgorithm::SMOOTHSTEP:
            return calculateSmoothStepScale(scale, referenceValue);
        case ScalingAlgorithm::BEZIER:
            return calculateBezierScale(scale, referenceValue);
        default:
            return value;
    }
}

Vector2D GameScaling::scaleVector2D(const Vector2D& vector, const Vector2D& referenceVector) {
    return Vector2D(
        scaleValue(vector.x, referenceVector.x),
        scaleValue(vector.y, referenceVector.y)
    );
}

Rectangle GameScaling::scaleRectangle(const Rectangle& rect, const Rectangle& referenceRect) {
    return Rectangle(
        scaleValue(rect.x, referenceRect.x),
        scaleValue(rect.y, referenceRect.y),
        scaleValue(rect.width, referenceRect.width),
        scaleValue(rect.height, referenceRect.height)
    );
}

float GameScaling::scaleValueWithFunction(float value, std::function<float(float)> scalingFunction) {
    if (value <= 0.0f) return 0.0f;
    return scalingFunction(value);
}

Vector2D GameScaling::scaleVector2DWithFunction(const Vector2D& vector, std::function<float(float)> scalingFunction) {
    return Vector2D(
        scalingFunction(vector.x),
        scalingFunction(vector.y)
    );
}

void GameScaling::applyPreset(GameScalingPreset preset) {
    setGamePreset(preset);
}

float GameScaling::getPresetScaleFactor(GameScalingPreset preset) const {
    return getPresetBaseScale(preset);
}

void GameScaling::enablePerformanceScaling(bool enable) {
    performanceScaling.isEnabled = enable;
    scalingConfig.enablePerformanceScaling = enable;
}

void GameScaling::updatePerformanceMetrics(const PerformanceMetrics& metrics) {
    performanceScaling.currentFPS = metrics.fps;
    
    if (performanceScaling.isEnabled) {
        updatePerformanceAdjustment();
    }
}

float GameScaling::getPerformanceAdjustedScale() const {
    return calculatePerformanceScale();
}

float GameScaling::getScreenBasedScale() const {
    if (!cacheValid) {
        updateCache();
    }
    return cachedScreenScale;
}

float GameScaling::getDensityBasedScale() const {
    if (!cacheValid) {
        updateCache();
    }
    return cachedDensityScale;
}

float GameScaling::getAspectRatioBasedScale() const {
    if (!cacheValid) {
        updateCache();
    }
    return cachedAspectRatioScale;
}

void GameScaling::setReferenceResolution(float width, float height) {
    referenceResolution = Vector2D(width, height);
    invalidateCache();
}

Vector2D GameScaling::getReferenceResolution() const {
    return referenceResolution;
}

float GameScaling::getResolutionScale() const {
    float screenDiagonal = sqrtf(screenConfig.width * screenConfig.width + screenConfig.height * screenConfig.height);
    float refDiagonal = sqrtf(referenceResolution.x * referenceResolution.x + referenceResolution.y * referenceResolution.y);
    
    return (refDiagonal > 0.0f) ? (screenDiagonal / refDiagonal) : 1.0f;
}

float GameScaling::scaleUIElement(float baseSize) {
    return scaleValue(baseSize, 48.0f); // Default UI element size
}

float GameScaling::scaleGameObject(float baseSize) {
    return scaleValue(baseSize, 64.0f); // Default game object size
}

float GameScaling::scaleTextSize(float baseSize) {
    return scaleValue(baseSize, 16.0f); // Default text size
}

float GameScaling::scaleParticleSize(float baseSize) {
    return scaleValue(baseSize, 8.0f); // Default particle size
}

float GameScaling::scaleEffectSize(float baseSize) {
    return scaleValue(baseSize, 32.0f); // Default effect size
}

float GameScaling::scaleAnimationDuration(float baseDuration) {
    // Animation duration typically doesn't scale with screen size
    return baseDuration;
}

float GameScaling::scaleAnimationSpeed(float baseSpeed) {
    // Animation speed might scale with performance
    if (performanceScaling.isEnabled) {
        return baseSpeed * performanceScaling.scaleAdjustment;
    }
    return baseSpeed;
}

float GameScaling::scalePhysicsForce(float baseForce) {
    // Physics forces might need scaling for different screen sizes
    return scaleValue(baseForce, 100.0f); // Default force
}

float GameScaling::scalePhysicsMass(float baseMass) {
    // Physics mass typically doesn't scale
    return baseMass;
}

float GameScaling::scalePhysicsVelocity(float baseVelocity) {
    // Physics velocity might scale with screen size
    return scaleValue(baseVelocity, 100.0f); // Default velocity
}

float GameScaling::scaleAudioVolume(float baseVolume) {
    // Audio volume typically doesn't scale with screen size
    return baseVolume;
}

float GameScaling::scaleAudioPitch(float basePitch) {
    // Audio pitch typically doesn't scale with screen size
    return basePitch;
}

float GameScaling::clampScale(float scale) const {
    return std::max(scalingConfig.minScale, std::min(scalingConfig.maxScale, scale));
}

bool GameScaling::isScaleValid(float scale) const {
    return scale >= scalingConfig.minScale && scale <= scalingConfig.maxScale;
}

float GameScaling::interpolateScale(float from, float to, float t) const {
    return lerp(from, to, t);
}

void GameScaling::scaleValues(float* values, int count, float referenceValue) {
    for (int i = 0; i < count; ++i) {
        values[i] = scaleValue(values[i], referenceValue);
    }
}

void GameScaling::scaleVectors(Vector2D* vectors, int count, const Vector2D& referenceVector) {
    for (int i = 0; i < count; ++i) {
        vectors[i] = scaleVector2D(vectors[i], referenceVector);
    }
}

void GameScaling::scaleRectangles(Rectangle* rectangles, int count, const Rectangle& referenceRect) {
    for (int i = 0; i < count; ++i) {
        rectangles[i] = scaleRectangle(rectangles[i], referenceRect);
    }
}

float GameScaling::applyScalingCurve(float value, float curveStrength) {
    // Apply a scaling curve to the value
    float t = clamp(value / 100.0f, 0.0f, 1.0f); // Normalize to 0-1
    float curve = smoothStep(0.0f, 1.0f, t);
    return lerp(value, value * curve, curveStrength);
}

float GameScaling::smoothStepScale(float value, float edge0, float edge1) {
    return smoothStep(edge0, edge1, value);
}

float GameScaling::bezierScale(float value, float p1, float p2) {
    return bezierInterpolation(value, 0.0f, p1, p2, 1.0f);
}

void GameScaling::drawScalingInfo() const {
    // Draw scaling information for debugging
    // Implementation would depend on the rendering system
}

void GameScaling::logScalingStats() const {
    LOGI("GameScaling Stats:");
    LOGI("  Algorithm: %d", static_cast<int>(scalingConfig.algorithm));
    LOGI("  Preset: %d", static_cast<int>(scalingConfig.preset));
    LOGI("  Base Scale: %.3f", scalingConfig.baseScale);
    LOGI("  Scale Factor: %.3f", scalingConfig.scaleFactor);
    LOGI("  Screen Scale: %.3f", getScreenBasedScale());
    LOGI("  Density Scale: %.3f", getDensityBasedScale());
    LOGI("  Aspect Ratio Scale: %.3f", getAspectRatioBasedScale());
}

// Private methods

float GameScaling::calculateLinearScale(float value, float reference) const {
    return value * scalingConfig.scaleFactor;
}

float GameScaling::calculateLogarithmicScale(float value, float reference) const {
    if (value <= 0.0f) return 0.0f;
    return logf(value) * scalingConfig.scaleFactor;
}

float GameScaling::calculateExponentialScale(float value, float reference) const {
    return powf(value, scalingConfig.scaleFactor);
}

float GameScaling::calculateSmoothStepScale(float value, float reference) const {
    float t = clamp(value / reference, 0.0f, 1.0f);
    return smoothStep(0.0f, 1.0f, t) * reference;
}

float GameScaling::calculateBezierScale(float value, float reference) const {
    float t = clamp(value / reference, 0.0f, 1.0f);
    return bezierInterpolation(t, 0.0f, 0.5f, 0.5f, 1.0f) * reference;
}

void GameScaling::calculatePresetScaling(GameScalingPreset preset) {
    scalingConfig.baseScale = getPresetBaseScale(preset);
    scalingConfig.minScale = getPresetMinScale(preset);
    scalingConfig.maxScale = getPresetMaxScale(preset);
}

float GameScaling::getPresetBaseScale(GameScalingPreset preset) const {
    switch (preset) {
        case GameScalingPreset::MOBILE_2D: return 1.0f;
        case GameScalingPreset::MOBILE_3D: return 0.8f;
        case GameScalingPreset::TABLET_2D: return 1.2f;
        case GameScalingPreset::TABLET_3D: return 1.0f;
        case GameScalingPreset::CONSOLE: return 1.5f;
        case GameScalingPreset::PC: return 2.0f;
        case GameScalingPreset::VR: return 0.6f;
        default: return 1.0f;
    }
}

float GameScaling::getPresetMinScale(GameScalingPreset preset) const {
    switch (preset) {
        case GameScalingPreset::MOBILE_2D: return 0.5f;
        case GameScalingPreset::MOBILE_3D: return 0.3f;
        case GameScalingPreset::TABLET_2D: return 0.7f;
        case GameScalingPreset::TABLET_3D: return 0.5f;
        case GameScalingPreset::CONSOLE: return 1.0f;
        case GameScalingPreset::PC: return 1.0f;
        case GameScalingPreset::VR: return 0.3f;
        default: return 0.5f;
    }
}

float GameScaling::getPresetMaxScale(GameScalingPreset preset) const {
    switch (preset) {
        case GameScalingPreset::MOBILE_2D: return 2.0f;
        case GameScalingPreset::MOBILE_3D: return 1.5f;
        case GameScalingPreset::TABLET_2D: return 2.5f;
        case GameScalingPreset::TABLET_3D: return 2.0f;
        case GameScalingPreset::CONSOLE: return 3.0f;
        case GameScalingPreset::PC: return 4.0f;
        case GameScalingPreset::VR: return 1.0f;
        default: return 2.0f;
    }
}

float GameScaling::calculatePerformanceScale() const {
    if (!performanceScaling.isEnabled) {
        return 1.0f;
    }
    
    float fpsRatio = performanceScaling.currentFPS / performanceScaling.targetFPS;
    return clampScale(performanceScaling.scaleAdjustment * fpsRatio);
}

void GameScaling::updatePerformanceAdjustment() {
    float fpsRatio = performanceScaling.currentFPS / performanceScaling.targetFPS;
    
    if (fpsRatio < 0.8f) {
        // Performance is poor, reduce quality
        performanceScaling.scaleAdjustment = std::max(0.5f, performanceScaling.scaleAdjustment * 0.95f);
    } else if (fpsRatio > 1.2f) {
        // Performance is good, can increase quality
        performanceScaling.scaleAdjustment = std::min(1.5f, performanceScaling.scaleAdjustment * 1.05f);
    }
}

void GameScaling::invalidateCache() {
    cacheValid = false;
}

void GameScaling::updateCache() const {
    cachedScreenScale = getResolutionScale();
    cachedDensityScale = screenConfig.density;
    cachedAspectRatioScale = (screenConfig.height > 0.0f) ? 
                            (static_cast<float>(screenConfig.width) / static_cast<float>(screenConfig.height)) : 1.0f;
    cacheValid = true;
}

float GameScaling::lerp(float a, float b, float t) const {
    return a + (b - a) * clamp(t, 0.0f, 1.0f);
}

float GameScaling::clamp(float value, float min, float max) const {
    return std::max(min, std::min(max, value));
}

float GameScaling::smoothStep(float edge0, float edge1, float x) const {
    float t = clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
    return t * t * (3.0f - 2.0f * t);
}

float GameScaling::bezierInterpolation(float t, float p0, float p1, float p2, float p3) const {
    float u = 1.0f - t;
    float tt = t * t;
    float uu = u * u;
    float uuu = uu * u;
    float ttt = tt * t;
    
    return uuu * p0 + 3.0f * uu * t * p1 + 3.0f * u * tt * p2 + ttt * p3;
}
