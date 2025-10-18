/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Game Math
 *
 * Description:
 * Mathematical utilities and functions optimized for game development.
 * Provides fast, accurate mathematical operations for 2D and 3D game calculations.
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

#ifndef GAME_MATH_H
#define GAME_MATH_H

#include "AppDimensGames.h"
#include <cmath>
#include <algorithm>

// Mathematical constants
namespace GameMathConstants {
    constexpr float PI = 3.14159265358979323846f;
    constexpr float TWO_PI = 6.28318530717958647692f;
    constexpr float HALF_PI = 1.57079632679489661923f;
    constexpr float DEG_TO_RAD = PI / 180.0f;
    constexpr float RAD_TO_DEG = 180.0f / PI;
    constexpr float EPSILON = 1e-6f;
    constexpr float INFINITY_F = std::numeric_limits<float>::infinity();
}

// Interpolation types
enum class InterpolationType {
    LINEAR,
    SMOOTH_STEP,
    SMOOTHER_STEP,
    BEZIER,
    EASE_IN,
    EASE_OUT,
    EASE_IN_OUT,
    BOUNCE,
    ELASTIC
};

// Easing functions
enum class EasingType {
    LINEAR,
    QUADRATIC,
    CUBIC,
    QUARTIC,
    QUINTIC,
    SINUSOIDAL,
    EXPONENTIAL,
    CIRCULAR
};

class GameMath {
public:
    GameMath();
    ~GameMath();
    
    // Basic mathematical operations
    static float clamp(float value, float min, float max);
    static int clamp(int value, int min, int max);
    static float lerp(float a, float b, float t);
    static float inverseLerp(float a, float b, float value);
    static float remap(float value, float fromMin, float fromMax, float toMin, float toMax);
    
    // Angle operations
    static float normalizeAngle(float angle);
    static float angleDifference(float angle1, float angle2);
    static float lerpAngle(float a, float b, float t);
    static float degreesToRadians(float degrees);
    static float radiansToDegrees(float radians);
    
    // Vector2D operations
    static float dot(const Vector2D& a, const Vector2D& b);
    static float cross(const Vector2D& a, const Vector2D& b);
    static float distance(const Vector2D& a, const Vector2D& b);
    static float distanceSquared(const Vector2D& a, const Vector2D& b);
    static Vector2D normalize(const Vector2D& vector);
    static Vector2D lerp(const Vector2D& a, const Vector2D& b, float t);
    static Vector2D reflect(const Vector2D& vector, const Vector2D& normal);
    static Vector2D rotate(const Vector2D& vector, float angle);
    static Vector2D perpendicular(const Vector2D& vector);
    static float angle(const Vector2D& vector);
    static Vector2D fromAngle(float angle, float length = 1.0f);
    
    // Rectangle operations
    static bool rectanglesIntersect(const Rectangle& a, const Rectangle& b);
    static Rectangle rectangleIntersection(const Rectangle& a, const Rectangle& b);
    static Rectangle rectangleUnion(const Rectangle& a, const Rectangle& b);
    static bool pointInRectangle(const Vector2D& point, const Rectangle& rect);
    static Vector2D closestPointOnRectangle(const Vector2D& point, const Rectangle& rect);
    static float rectangleArea(const Rectangle& rect);
    static float rectanglePerimeter(const Rectangle& rect);
    
    // Circle operations
    static bool pointInCircle(const Vector2D& point, const Vector2D& center, float radius);
    static bool circlesIntersect(const Vector2D& center1, float radius1, 
                                const Vector2D& center2, float radius2);
    static bool circleRectangleIntersect(const Vector2D& center, float radius, const Rectangle& rect);
    static Vector2D closestPointOnCircle(const Vector2D& point, const Vector2D& center, float radius);
    
    // Interpolation functions
    static float interpolate(float t, InterpolationType type);
    static float easeIn(float t, EasingType type);
    static float easeOut(float t, EasingType type);
    static float easeInOut(float t, EasingType type);
    static float smoothStep(float edge0, float edge1, float x);
    static float smootherStep(float edge0, float edge1, float x);
    
    // Bezier curves
    static float bezier1D(float t, float p0, float p1);
    static float bezier1D(float t, float p0, float p1, float p2);
    static float bezier1D(float t, float p0, float p1, float p2, float p3);
    static Vector2D bezier2D(float t, const Vector2D& p0, const Vector2D& p1);
    static Vector2D bezier2D(float t, const Vector2D& p0, const Vector2D& p1, const Vector2D& p2);
    static Vector2D bezier2D(float t, const Vector2D& p0, const Vector2D& p1, 
                            const Vector2D& p2, const Vector2D& p3);
    
    // Noise functions
    static float noise1D(float x);
    static float noise2D(float x, float y);
    static float perlinNoise1D(float x, float frequency = 1.0f, int octaves = 4);
    static float perlinNoise2D(float x, float y, float frequency = 1.0f, int octaves = 4);
    static float simplexNoise1D(float x);
    static float simplexNoise2D(float x, float y);
    
    // Random number generation
    static float randomFloat(float min = 0.0f, float max = 1.0f);
    static int randomInt(int min, int max);
    static Vector2D randomVector2D(float minLength = 0.0f, float maxLength = 1.0f);
    static Vector2D randomVector2DInCircle(float radius);
    static Vector2D randomVector2DInRectangle(const Rectangle& rect);
    
    // Collision detection
    static bool pointInPolygon(const Vector2D& point, const std::vector<Vector2D>& polygon);
    static bool lineIntersectsLine(const Vector2D& p1, const Vector2D& p2,
                                  const Vector2D& p3, const Vector2D& p4, Vector2D* intersection = nullptr);
    static bool lineIntersectsRectangle(const Vector2D& p1, const Vector2D& p2, const Rectangle& rect);
    static bool lineIntersectsCircle(const Vector2D& p1, const Vector2D& p2,
                                    const Vector2D& center, float radius);
    
    // Physics calculations
    static Vector2D calculateGravity(float mass1, float mass2, const Vector2D& pos1, const Vector2D& pos2);
    static Vector2D calculateCollisionResponse(const Vector2D& velocity1, const Vector2D& velocity2,
                                              float mass1, float mass2, const Vector2D& normal);
    static float calculateKineticEnergy(float mass, float velocity);
    static float calculatePotentialEnergy(float mass, float height, float gravity = 9.81f);
    
    // Matrix operations (2D)
    static void createTranslationMatrix(float* matrix, float x, float y);
    static void createRotationMatrix(float* matrix, float angle);
    static void createScaleMatrix(float* matrix, float x, float y);
    static void createTransformMatrix(float* matrix, float x, float y, float angle, float scaleX, float scaleY);
    static void multiplyMatrices(float* result, const float* a, const float* b);
    static Vector2D transformVector2D(const Vector2D& vector, const float* matrix);
    
    // Utility functions
    static bool isNearlyEqual(float a, float b, float epsilon = GameMathConstants::EPSILON);
    static bool isNearlyZero(float value, float epsilon = GameMathConstants::EPSILON);
    static float sign(float value);
    static int sign(int value);
    static float abs(float value);
    static int abs(int value);
    static float min(float a, float b);
    static float max(float a, float b);
    static int min(int a, int b);
    static int max(int a, int b);
    
    // Additional utility functions
    static Vector2D closestPointOnLine(const Vector2D& p1, const Vector2D& p2, const Vector2D& point);
    static float bounce(float t);
    static float elastic(float t);
    
    // Fast approximations
    static float fastSin(float angle);
    static float fastCos(float angle);
    static float fastTan(float angle);
    static float fastSqrt(float value);
    static float fastInvSqrt(float value);
    static float fastPow(float base, float exponent);
    static float fastExp(float value);
    static float fastLog(float value);
    
    // Color operations
    static Vector2D rgbToHsv(float r, float g, float b);
    static Vector2D hsvToRgb(float h, float s, float v);
    static float lerpColor(float a, float b, float t);
    
    // Performance utilities
    static void enableFastMath(bool enable);
    static bool isFastMathEnabled();
    
    // Debug utilities
    static void logMathStats();
    static float getMathPrecision();
    
private:
    static bool fastMathEnabled;
    static float mathPrecision;
    
    // Fast math lookup tables
    static float sinTable[360];
    static float cosTable[360];
    static bool lookupTablesInitialized;
    
    // Internal utilities
    static void initializeLookupTables();
    static float fastSinLookup(float angle);
    static float fastCosLookup(float angle);
    static float fastSqrtNewton(float value);
    static float fastInvSqrtNewton(float value);
    
    // Noise generation
    static int noiseSeed;
    static void setNoiseSeed(int seed);
    static float noise1DInternal(float x);
    static float noise2DInternal(float x, float y);
    static float fade(float t);
    static float lerpNoise(float a, float b, float t);
    static float grad(int hash, float x);
    static float grad(int hash, float x, float y);
};

#endif // GAME_MATH_H
