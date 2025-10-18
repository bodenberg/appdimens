/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Main Implementation
 *
 * Description:
 * Main implementation of the AppDimens Games library for Android C++ game development.
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

#include "AppDimensGames.h"
#include "GameDimensions.h"
#include "ViewportManager.h"
#include "GameScaling.h"
#include "OpenGLUtils.h"
#include "GameMath.h"
#include "PerformanceMonitor.h"

// Static instance
AppDimensGames* AppDimensGames::instance = nullptr;

AppDimensGames::AppDimensGames() : initialized(false) {
    LOGI("AppDimensGames constructor called");
}

AppDimensGames::~AppDimensGames() {
    LOGI("AppDimensGames destructor called");
    shutdown();
}

AppDimensGames& AppDimensGames::getInstance() {
    if (instance == nullptr) {
        instance = new AppDimensGames();
    }
    return *instance;
}

bool AppDimensGames::initialize(JNIEnv* env, jobject context) {
    if (initialized) {
        LOGI("AppDimensGames already initialized");
        return true;
    }
    
    LOGI("Initializing AppDimensGames...");
    
    try {
        // Initialize core components
        gameDimensions = std::make_unique<GameDimensions>();
        viewportManager = std::make_unique<ViewportManager>();
        gameScaling = std::make_unique<GameScaling>();
        openGLUtils = std::make_unique<OpenGLUtils>();
        gameMath = std::make_unique<GameMath>();
        performanceMonitor = std::make_unique<PerformanceMonitor>();
        
        // Initialize OpenGL utilities
        if (!openGLUtils->initialize()) {
            LOGE("Failed to initialize OpenGL utilities");
            return false;
        }
        
        // Initialize performance monitor
        performanceMonitor->initialize();
        
        initialized = true;
        LOGI("AppDimensGames initialized successfully");
        return true;
        
    } catch (const std::exception& e) {
        LOGE("Exception during AppDimensGames initialization: %s", e.what());
        return false;
    }
}

void AppDimensGames::shutdown() {
    if (!initialized) {
        return;
    }
    
    LOGI("Shutting down AppDimensGames...");
    
    // Shutdown components in reverse order
    if (performanceMonitor) {
        performanceMonitor->shutdown();
        performanceMonitor.reset();
    }
    
    if (openGLUtils) {
        openGLUtils->shutdown();
        openGLUtils.reset();
    }
    
    gameScaling.reset();
    viewportManager.reset();
    gameDimensions.reset();
    gameMath.reset();
    
    initialized = false;
    LOGI("AppDimensGames shutdown complete");
}

void AppDimensGames::updateScreenConfig(const GameScreenConfig& config) {
    if (!initialized) {
        LOGE("AppDimensGames not initialized");
        return;
    }
    
    screenConfig = config;
    
    // Update all components with new screen configuration
    if (gameDimensions) {
        gameDimensions->initialize(config);
    }
    
    if (viewportManager) {
        viewportManager->initialize(config);
    }
    
    if (gameScaling) {
        gameScaling->initialize(config);
    }
    
    LOGI("Screen config updated: %dx%d, density: %.2f", config.width, config.height, config.density);
}

GameScreenConfig AppDimensGames::getScreenConfig() const {
    return screenConfig;
}

float AppDimensGames::calculateDimension(float baseValue, GameDimensionType type) {
    if (!initialized || !gameDimensions) {
        LOGE("AppDimensGames not initialized or gameDimensions is null");
        return baseValue;
    }
    
    switch (type) {
        case GameDimensionType::DYNAMIC:
            return gameDimensions->calculateDynamicDimension(baseValue);
        case GameDimensionType::FIXED:
            return gameDimensions->calculateFixedDimension(baseValue);
        case GameDimensionType::GAME_WORLD:
            return gameDimensions->calculateGameWorldDimension(baseValue);
        case GameDimensionType::UI_OVERLAY:
            return gameDimensions->calculateUIOverlayDimension(baseValue);
        default:
            return baseValue;
    }
}

Vector2D AppDimensGames::calculateVector2D(const Vector2D& baseVector, GameDimensionType type) {
    if (!initialized || !gameDimensions) {
        LOGE("AppDimensGames not initialized or gameDimensions is null");
        return baseVector;
    }
    
    switch (type) {
        case GameDimensionType::DYNAMIC:
            return gameDimensions->calculateDynamicVector2D(baseVector);
        case GameDimensionType::FIXED:
            return gameDimensions->calculateFixedVector2D(baseVector);
        case GameDimensionType::GAME_WORLD:
            return gameDimensions->calculateGameWorldVector2D(baseVector);
        case GameDimensionType::UI_OVERLAY:
            return gameDimensions->calculateUIOverlayVector2D(baseVector);
        default:
            return baseVector;
    }
}

Rectangle AppDimensGames::calculateRectangle(const Rectangle& baseRect, GameDimensionType type) {
    if (!initialized || !gameDimensions) {
        LOGE("AppDimensGames not initialized or gameDimensions is null");
        return baseRect;
    }
    
    switch (type) {
        case GameDimensionType::DYNAMIC:
            return gameDimensions->calculateDynamicRectangle(baseRect);
        case GameDimensionType::FIXED:
            return gameDimensions->calculateFixedRectangle(baseRect);
        case GameDimensionType::GAME_WORLD:
            return gameDimensions->calculateGameWorldRectangle(baseRect);
        case GameDimensionType::UI_OVERLAY:
            return gameDimensions->calculateUIOverlayRectangle(baseRect);
        default:
            return baseRect;
    }
}

ViewportManager* AppDimensGames::getViewportManager() {
    return viewportManager.get();
}

PerformanceMonitor* AppDimensGames::getPerformanceMonitor() {
    return performanceMonitor.get();
}

GameMath* AppDimensGames::getGameMath() {
    return gameMath.get();
}

OpenGLUtils* AppDimensGames::getOpenGLUtils() {
    return openGLUtils.get();
}

// JNI function implementations
extern "C" {

JNIEXPORT jboolean JNICALL
Java_com_appdimens_games_AppDimensGames_nativeInitialize(JNIEnv *env, jobject thiz, jobject context) {
    LOGI("JNI: nativeInitialize called");
    
    AppDimensGames& instance = AppDimensGames::getInstance();
    return instance.initialize(env, context) ? JNI_TRUE : JNI_FALSE;
}

JNIEXPORT void JNICALL
Java_com_appdimens_games_AppDimensGames_nativeShutdown(JNIEnv *env, jobject thiz) {
    LOGI("JNI: nativeShutdown called");
    
    AppDimensGames& instance = AppDimensGames::getInstance();
    instance.shutdown();
}

JNIEXPORT void JNICALL
Java_com_appdimens_games_AppDimensGames_nativeUpdateScreenConfig(JNIEnv *env, jobject thiz,
    jint width, jint height, jfloat density, jfloat scaledDensity, jint orientation) {
    
    LOGI("JNI: nativeUpdateScreenConfig called - %dx%d, density: %.2f", width, height, density);
    
    AppDimensGames& instance = AppDimensGames::getInstance();
    
    GameScreenConfig config;
    config.width = width;
    config.height = height;
    config.density = density;
    config.scaledDensity = scaledDensity;
    config.orientation = orientation;
    config.isTablet = (width >= 600 || height >= 600);
    config.isLandscape = (width > height);
    
    instance.updateScreenConfig(config);
}

JNIEXPORT jfloat JNICALL
Java_com_appdimens_games_AppDimensGames_nativeCalculateDimension(JNIEnv *env, jobject thiz,
    jfloat baseValue, jint type) {
    
    AppDimensGames& instance = AppDimensGames::getInstance();
    return instance.calculateDimension(baseValue, static_cast<GameDimensionType>(type));
}

JNIEXPORT jfloatArray JNICALL
Java_com_appdimens_games_AppDimensGames_nativeCalculateVector2D(JNIEnv *env, jobject thiz,
    jfloat x, jfloat y, jint type) {
    
    AppDimensGames& instance = AppDimensGames::getInstance();
    Vector2D result = instance.calculateVector2D(Vector2D(x, y), static_cast<GameDimensionType>(type));
    
    jfloatArray array = env->NewFloatArray(2);
    jfloat values[2] = {result.x, result.y};
    env->SetFloatArrayRegion(array, 0, 2, values);
    
    return array;
}

JNIEXPORT jfloatArray JNICALL
Java_com_appdimens_games_AppDimensGames_nativeCalculateRectangle(JNIEnv *env, jobject thiz,
    jfloat x, jfloat y, jfloat width, jfloat height, jint type) {
    
    AppDimensGames& instance = AppDimensGames::getInstance();
    Rectangle result = instance.calculateRectangle(Rectangle(x, y, width, height), static_cast<GameDimensionType>(type));
    
    jfloatArray array = env->NewFloatArray(4);
    jfloat values[4] = {result.x, result.y, result.width, result.height};
    env->SetFloatArrayRegion(array, 0, 4, values);
    
    return array;
}

} // extern "C"
