/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games
 *
 * Description:
 * The AppDimens Games library is a specialized dimension management system for Android game development
 * with C++/NDK support. It provides responsive scaling, viewport management, and performance optimizations
 * for game engines like Cocos2d-x, OpenGL ES, and Vulkan.
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

#ifndef APPDIMENS_GAMES_H
#define APPDIMENS_GAMES_H

#include <jni.h>
#include <android/log.h>
#include <GLES2/gl2.h>
#include <GLES3/gl3.h>
#include <EGL/egl.h>
#include <math.h>
#include <vector>
#include <memory>

// Logging macros
#define LOG_TAG "AppDimensGames"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)

// Game dimension types
enum class GameDimensionType {
    DYNAMIC,    // Proportional scaling (ideal for containers)
    FIXED,      // Logarithmic scaling (ideal for UI elements)
    GAME_WORLD, // Game world coordinates
    UI_OVERLAY  // UI overlay coordinates
};

// Screen orientation types
enum class ScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
    AUTO
};

// Viewport scaling modes
enum class ViewportMode {
    FIT_WIDTH,      // Fit to screen width
    FIT_HEIGHT,     // Fit to screen height
    FIT_ALL,        // Fit entire content
    STRETCH,        // Stretch to fill screen
    CROP            // Crop to maintain aspect ratio
};

// Vector2D structure for 2D coordinates
struct Vector2D {
    float x, y;
    
    Vector2D() : x(0.0f), y(0.0f) {}
    Vector2D(float x, float y) : x(x), y(y) {}
    
    Vector2D operator+(const Vector2D& other) const {
        return Vector2D(x + other.x, y + other.y);
    }
    
    Vector2D operator-(const Vector2D& other) const {
        return Vector2D(x - other.x, y - other.y);
    }
    
    Vector2D operator*(float scalar) const {
        return Vector2D(x * scalar, y * scalar);
    }
    
    Vector2D operator/(float scalar) const {
        return Vector2D(x / scalar, y / scalar);
    }
    
    float length() const {
        return sqrtf(x * x + y * y);
    }
    
    Vector2D normalized() const {
        float len = length();
        if (len > 0.0f) {
            return Vector2D(x / len, y / len);
        }
        return Vector2D(0.0f, 0.0f);
    }
};

// Rectangle structure for bounds and viewports
struct Rectangle {
    float x, y, width, height;
    
    Rectangle() : x(0.0f), y(0.0f), width(0.0f), height(0.0f) {}
    Rectangle(float x, float y, float w, float h) : x(x), y(y), width(w), height(h) {}
    
    Vector2D center() const {
        return Vector2D(x + width * 0.5f, y + height * 0.5f);
    }
    
    bool contains(const Vector2D& point) const {
        return point.x >= x && point.x <= x + width &&
               point.y >= y && point.y <= y + height;
    }
    
    Rectangle intersection(const Rectangle& other) const {
        float left = fmaxf(x, other.x);
        float top = fmaxf(y, other.y);
        float right = fminf(x + width, other.x + other.width);
        float bottom = fminf(y + height, other.y + other.height);
        
        if (left < right && top < bottom) {
            return Rectangle(left, top, right - left, bottom - top);
        }
        return Rectangle(0.0f, 0.0f, 0.0f, 0.0f);
    }
};

// Game screen configuration
struct GameScreenConfig {
    int width;
    int height;
    float density;
    float scaledDensity;
    int orientation;
    bool isTablet;
    bool isLandscape;
    
    GameScreenConfig() : width(0), height(0), density(1.0f), scaledDensity(1.0f),
                        orientation(0), isTablet(false), isLandscape(false) {}
};

// Performance metrics
struct PerformanceMetrics {
    float fps;
    float frameTime;
    float cpuUsage;
    float memoryUsage;
    int drawCalls;
    int triangles;
    
    PerformanceMetrics() : fps(0.0f), frameTime(0.0f), cpuUsage(0.0f),
                          memoryUsage(0.0f), drawCalls(0), triangles(0) {}
};

// Forward declarations
class GameDimensions;
class ViewportManager;
class GameScaling;
class OpenGLUtils;
class GameMath;
class PerformanceMonitor;

// Main AppDimens Games class
class AppDimensGames {
public:
    static AppDimensGames& getInstance();
    
    // Initialization
    bool initialize(JNIEnv* env, jobject context);
    void shutdown();
    
    // Screen configuration
    void updateScreenConfig(const GameScreenConfig& config);
    GameScreenConfig getScreenConfig() const;
    
    // Dimension calculations
    float calculateDimension(float baseValue, GameDimensionType type);
    Vector2D calculateVector2D(const Vector2D& baseVector, GameDimensionType type);
    Rectangle calculateRectangle(const Rectangle& baseRect, GameDimensionType type);
    
    // Viewport management
    ViewportManager* getViewportManager();
    
    // Performance monitoring
    PerformanceMonitor* getPerformanceMonitor();
    
    // Utility functions
    GameMath* getGameMath();
    OpenGLUtils* getOpenGLUtils();
    
private:
    AppDimensGames();
    ~AppDimensGames();
    
    static AppDimensGames* instance;
    bool initialized;
    GameScreenConfig screenConfig;
    
    std::unique_ptr<GameDimensions> gameDimensions;
    std::unique_ptr<ViewportManager> viewportManager;
    std::unique_ptr<GameScaling> gameScaling;
    std::unique_ptr<OpenGLUtils> openGLUtils;
    std::unique_ptr<GameMath> gameMath;
    std::unique_ptr<PerformanceMonitor> performanceMonitor;
};

// JNI function declarations
extern "C" {
    JNIEXPORT jboolean JNICALL
    Java_com_appdimens_games_AppDimensGames_nativeInitialize(JNIEnv *env, jobject thiz, jobject context);
    
    JNIEXPORT void JNICALL
    Java_com_appdimens_games_AppDimensGames_nativeShutdown(JNIEnv *env, jobject thiz);
    
    JNIEXPORT void JNICALL
    Java_com_appdimens_games_AppDimensGames_nativeUpdateScreenConfig(JNIEnv *env, jobject thiz,
        jint width, jint height, jfloat density, jfloat scaledDensity, jint orientation);
    
    JNIEXPORT jfloat JNICALL
    Java_com_appdimens_games_AppDimensGames_nativeCalculateDimension(JNIEnv *env, jobject thiz,
        jfloat baseValue, jint type);
    
    JNIEXPORT jfloatArray JNICALL
    Java_com_appdimens_games_AppDimensGames_nativeCalculateVector2D(JNIEnv *env, jobject thiz,
        jfloat x, jfloat y, jint type);
    
    JNIEXPORT jfloatArray JNICALL
    Java_com_appdimens_games_AppDimensGames_nativeCalculateRectangle(JNIEnv *env, jobject thiz,
        jfloat x, jfloat y, jfloat width, jfloat height, jint type);
}

#endif // APPDIMENS_GAMES_H
