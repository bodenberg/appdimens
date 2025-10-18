/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Game Math Implementation
 *
 * Description:
 * Implementation of mathematical utilities and functions optimized for game development.
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

#include "GameMath.h"
#include <algorithm>
#include <cmath>
#include <random>
#include <cstring>

// Static member initialization
bool GameMath::fastMathEnabled = false;
float GameMath::mathPrecision = 1e-6f;
float GameMath::sinTable[360];
float GameMath::cosTable[360];
bool GameMath::lookupTablesInitialized = false;
int GameMath::noiseSeed = 12345;

GameMath::GameMath() {
    LOGI("GameMath constructor called");
    
    if (!lookupTablesInitialized) {
        initializeLookupTables();
    }
}

GameMath::~GameMath() {
    LOGI("GameMath destructor called");
}

// Basic mathematical operations
float GameMath::clamp(float value, float min, float max) {
    return std::max(min, std::min(max, value));
}

int GameMath::clamp(int value, int min, int max) {
    return std::max(min, std::min(max, value));
}

float GameMath::lerp(float a, float b, float t) {
    return a + (b - a) * clamp(t, 0.0f, 1.0f);
}

float GameMath::inverseLerp(float a, float b, float value) {
    if (std::abs(b - a) < GameMathConstants::EPSILON) return 0.0f;
    return clamp((value - a) / (b - a), 0.0f, 1.0f);
}

float GameMath::remap(float value, float fromMin, float fromMax, float toMin, float toMax) {
    float t = inverseLerp(fromMin, fromMax, value);
    return lerp(toMin, toMax, t);
}

// Angle operations
float GameMath::normalizeAngle(float angle) {
    while (angle > GameMathConstants::TWO_PI) angle -= GameMathConstants::TWO_PI;
    while (angle < 0.0f) angle += GameMathConstants::TWO_PI;
    return angle;
}

float GameMath::angleDifference(float angle1, float angle2) {
    float diff = angle2 - angle1;
    while (diff > GameMathConstants::PI) diff -= GameMathConstants::TWO_PI;
    while (diff < -GameMathConstants::PI) diff += GameMathConstants::TWO_PI;
    return diff;
}

float GameMath::lerpAngle(float a, float b, float t) {
    float diff = angleDifference(a, b);
    return normalizeAngle(a + diff * t);
}

float GameMath::degreesToRadians(float degrees) {
    return degrees * GameMathConstants::DEG_TO_RAD;
}

float GameMath::radiansToDegrees(float radians) {
    return radians * GameMathConstants::RAD_TO_DEG;
}

// Vector2D operations
float GameMath::dot(const Vector2D& a, const Vector2D& b) {
    return a.x * b.x + a.y * b.y;
}

float GameMath::cross(const Vector2D& a, const Vector2D& b) {
    return a.x * b.y - a.y * b.x;
}

float GameMath::distance(const Vector2D& a, const Vector2D& b) {
    float dx = b.x - a.x;
    float dy = b.y - a.y;
    return sqrtf(dx * dx + dy * dy);
}

float GameMath::distanceSquared(const Vector2D& a, const Vector2D& b) {
    float dx = b.x - a.x;
    float dy = b.y - a.y;
    return dx * dx + dy * dy;
}

Vector2D GameMath::normalize(const Vector2D& vector) {
    return vector.normalized();
}

Vector2D GameMath::lerp(const Vector2D& a, const Vector2D& b, float t) {
    return Vector2D(lerp(a.x, b.x, t), lerp(a.y, b.y, t));
}

Vector2D GameMath::reflect(const Vector2D& vector, const Vector2D& normal) {
    float dotProduct = 2.0f * dot(vector, normal);
    return Vector2D(vector.x - dotProduct * normal.x, vector.y - dotProduct * normal.y);
}

Vector2D GameMath::rotate(const Vector2D& vector, float angle) {
    float cos = cosf(angle);
    float sin = sinf(angle);
    return Vector2D(
        vector.x * cos - vector.y * sin,
        vector.x * sin + vector.y * cos
    );
}

Vector2D GameMath::perpendicular(const Vector2D& vector) {
    return Vector2D(-vector.y, vector.x);
}

float GameMath::angle(const Vector2D& vector) {
    return atan2f(vector.y, vector.x);
}

Vector2D GameMath::fromAngle(float angle, float length) {
    return Vector2D(cosf(angle) * length, sinf(angle) * length);
}

// Rectangle operations
bool GameMath::rectanglesIntersect(const Rectangle& a, const Rectangle& b) {
    return !(a.x + a.width < b.x || b.x + b.width < a.x ||
             a.y + a.height < b.y || b.y + b.height < a.y);
}

Rectangle GameMath::rectangleIntersection(const Rectangle& a, const Rectangle& b) {
    return a.intersection(b);
}

Rectangle GameMath::rectangleUnion(const Rectangle& a, const Rectangle& b) {
    float left = std::min(a.x, b.x);
    float top = std::min(a.y, b.y);
    float right = std::max(a.x + a.width, b.x + b.width);
    float bottom = std::max(a.y + a.height, b.y + b.height);
    
    return Rectangle(left, top, right - left, bottom - top);
}

bool GameMath::pointInRectangle(const Vector2D& point, const Rectangle& rect) {
    return rect.contains(point);
}

Vector2D GameMath::closestPointOnRectangle(const Vector2D& point, const Rectangle& rect) {
    float x = clamp(point.x, rect.x, rect.x + rect.width);
    float y = clamp(point.y, rect.y, rect.y + rect.height);
    return Vector2D(x, y);
}

float GameMath::rectangleArea(const Rectangle& rect) {
    return rect.width * rect.height;
}

float GameMath::rectanglePerimeter(const Rectangle& rect) {
    return 2.0f * (rect.width + rect.height);
}

// Circle operations
bool GameMath::pointInCircle(const Vector2D& point, const Vector2D& center, float radius) {
    return distanceSquared(point, center) <= radius * radius;
}

bool GameMath::circlesIntersect(const Vector2D& center1, float radius1, 
                               const Vector2D& center2, float radius2) {
    float dist = distance(center1, center2);
    return dist <= (radius1 + radius2);
}

bool GameMath::circleRectangleIntersect(const Vector2D& center, float radius, const Rectangle& rect) {
    Vector2D closest = closestPointOnRectangle(center, rect);
    return distanceSquared(center, closest) <= radius * radius;
}

Vector2D GameMath::closestPointOnCircle(const Vector2D& point, const Vector2D& center, float radius) {
    Vector2D direction = point - center;
    float distance = direction.length();
    
    if (distance <= radius) {
        return point; // Point is inside circle
    }
    
    return center + direction.normalized() * radius;
}

// Interpolation functions
float GameMath::interpolate(float t, InterpolationType type) {
    t = clamp(t, 0.0f, 1.0f);
    
    switch (type) {
        case InterpolationType::LINEAR:
            return t;
        case InterpolationType::SMOOTH_STEP:
            return t * t * (3.0f - 2.0f * t);
        case InterpolationType::SMOOTHER_STEP:
            return t * t * t * (t * (t * 6.0f - 15.0f) + 10.0f);
        case InterpolationType::BEZIER:
            return bezier1D(t, 0.0f, 0.5f, 0.5f, 1.0f);
        case InterpolationType::EASE_IN:
            return t * t;
        case InterpolationType::EASE_OUT:
            return 1.0f - (1.0f - t) * (1.0f - t);
        case InterpolationType::EASE_IN_OUT:
            return t < 0.5f ? 2.0f * t * t : 1.0f - powf(-2.0f * t + 2.0f, 2.0f) / 2.0f;
        case InterpolationType::BOUNCE:
            return GameMath::bounce(t);
        case InterpolationType::ELASTIC:
            return GameMath::elastic(t);
        default:
            return t;
    }
}

float GameMath::easeIn(float t, EasingType type) {
    t = clamp(t, 0.0f, 1.0f);
    
    switch (type) {
        case EasingType::LINEAR: return t;
        case EasingType::QUADRATIC: return t * t;
        case EasingType::CUBIC: return t * t * t;
        case EasingType::QUARTIC: return t * t * t * t;
        case EasingType::QUINTIC: return t * t * t * t * t;
        case EasingType::SINUSOIDAL: return 1.0f - cosf(t * GameMathConstants::PI / 2.0f);
        case EasingType::EXPONENTIAL: return t == 0.0f ? 0.0f : powf(2.0f, 10.0f * (t - 1.0f));
        case EasingType::CIRCULAR: return 1.0f - sqrtf(1.0f - t * t);
        default: return t;
    }
}

float GameMath::easeOut(float t, EasingType type) {
    t = clamp(t, 0.0f, 1.0f);
    
    switch (type) {
        case EasingType::LINEAR: return t;
        case EasingType::QUADRATIC: return 1.0f - (1.0f - t) * (1.0f - t);
        case EasingType::CUBIC: return 1.0f - powf(1.0f - t, 3.0f);
        case EasingType::QUARTIC: return 1.0f - powf(1.0f - t, 4.0f);
        case EasingType::QUINTIC: return 1.0f - powf(1.0f - t, 5.0f);
        case EasingType::SINUSOIDAL: return sinf(t * GameMathConstants::PI / 2.0f);
        case EasingType::EXPONENTIAL: return t == 1.0f ? 1.0f : 1.0f - powf(2.0f, -10.0f * t);
        case EasingType::CIRCULAR: return sqrtf(1.0f - powf(t - 1.0f, 2.0f));
        default: return t;
    }
}

float GameMath::easeInOut(float t, EasingType type) {
    t = clamp(t, 0.0f, 1.0f);
    
    if (t < 0.5f) {
        return easeIn(t * 2.0f, type) / 2.0f;
    } else {
        return easeOut(t * 2.0f - 1.0f, type) / 2.0f + 0.5f;
    }
}

float GameMath::smoothStep(float edge0, float edge1, float x) {
    float t = clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
    return t * t * (3.0f - 2.0f * t);
}

float GameMath::smootherStep(float edge0, float edge1, float x) {
    float t = clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
    return t * t * t * (t * (t * 6.0f - 15.0f) + 10.0f);
}

// Bezier curves
float GameMath::bezier1D(float t, float p0, float p1) {
    return lerp(p0, p1, t);
}

float GameMath::bezier1D(float t, float p0, float p1, float p2) {
    float u = 1.0f - t;
    return u * u * p0 + 2.0f * u * t * p1 + t * t * p2;
}

float GameMath::bezier1D(float t, float p0, float p1, float p2, float p3) {
    float u = 1.0f - t;
    float uu = u * u;
    float uuu = uu * u;
    float tt = t * t;
    float ttt = tt * t;
    
    return uuu * p0 + 3.0f * uu * t * p1 + 3.0f * u * tt * p2 + ttt * p3;
}

Vector2D GameMath::bezier2D(float t, const Vector2D& p0, const Vector2D& p1) {
    return Vector2D(bezier1D(t, p0.x, p1.x), bezier1D(t, p0.y, p1.y));
}

Vector2D GameMath::bezier2D(float t, const Vector2D& p0, const Vector2D& p1, const Vector2D& p2) {
    return Vector2D(bezier1D(t, p0.x, p1.x, p2.x), bezier1D(t, p0.y, p1.y, p2.y));
}

Vector2D GameMath::bezier2D(float t, const Vector2D& p0, const Vector2D& p1, 
                           const Vector2D& p2, const Vector2D& p3) {
    return Vector2D(bezier1D(t, p0.x, p1.x, p2.x, p3.x), bezier1D(t, p0.y, p1.y, p2.y, p3.y));
}

// Noise functions (simplified implementations)
float GameMath::noise1D(float x) {
    return noise1DInternal(x);
}

float GameMath::noise2D(float x, float y) {
    return noise2DInternal(x, y);
}

float GameMath::perlinNoise1D(float x, float frequency, int octaves) {
    float result = 0.0f;
    float amplitude = 1.0f;
    float maxValue = 0.0f;
    
    for (int i = 0; i < octaves; ++i) {
        result += noise1D(x * frequency) * amplitude;
        maxValue += amplitude;
        amplitude *= 0.5f;
        frequency *= 2.0f;
    }
    
    return result / maxValue;
}

float GameMath::perlinNoise2D(float x, float y, float frequency, int octaves) {
    float result = 0.0f;
    float amplitude = 1.0f;
    float maxValue = 0.0f;
    
    for (int i = 0; i < octaves; ++i) {
        result += noise2D(x * frequency, y * frequency) * amplitude;
        maxValue += amplitude;
        amplitude *= 0.5f;
        frequency *= 2.0f;
    }
    
    return result / maxValue;
}

float GameMath::simplexNoise1D(float x) {
    // Simplified simplex noise implementation
    return noise1D(x);
}

float GameMath::simplexNoise2D(float x, float y) {
    // Simplified simplex noise implementation
    return noise2D(x, y);
}

// Random number generation
float GameMath::randomFloat(float min, float max) {
    static std::random_device rd;
    static std::mt19937 gen(rd());
    std::uniform_real_distribution<float> dis(min, max);
    return dis(gen);
}

int GameMath::randomInt(int min, int max) {
    static std::random_device rd;
    static std::mt19937 gen(rd());
    std::uniform_int_distribution<int> dis(min, max);
    return dis(gen);
}

Vector2D GameMath::randomVector2D(float minLength, float maxLength) {
    float angle = randomFloat(0.0f, GameMathConstants::TWO_PI);
    float length = randomFloat(minLength, maxLength);
    return fromAngle(angle, length);
}

Vector2D GameMath::randomVector2DInCircle(float radius) {
    float angle = randomFloat(0.0f, GameMathConstants::TWO_PI);
    float r = sqrtf(randomFloat(0.0f, 1.0f)) * radius;
    return Vector2D(cosf(angle) * r, sinf(angle) * r);
}

Vector2D GameMath::randomVector2DInRectangle(const Rectangle& rect) {
    return Vector2D(
        randomFloat(rect.x, rect.x + rect.width),
        randomFloat(rect.y, rect.y + rect.height)
    );
}

// Collision detection
bool GameMath::pointInPolygon(const Vector2D& point, const std::vector<Vector2D>& polygon) {
    if (polygon.size() < 3) return false;
    
    bool inside = false;
    int j = polygon.size() - 1;
    
    for (int i = 0; i < polygon.size(); ++i) {
        if (((polygon[i].y > point.y) != (polygon[j].y > point.y)) &&
            (point.x < (polygon[j].x - polygon[i].x) * (point.y - polygon[i].y) / (polygon[j].y - polygon[i].y) + polygon[i].x)) {
            inside = !inside;
        }
        j = i;
    }
    
    return inside;
}

bool GameMath::lineIntersectsLine(const Vector2D& p1, const Vector2D& p2,
                                 const Vector2D& p3, const Vector2D& p4, Vector2D* intersection) {
    float denom = (p1.x - p2.x) * (p3.y - p4.y) - (p1.y - p2.y) * (p3.x - p4.x);
    if (std::abs(denom) < GameMathConstants::EPSILON) return false;
    
    float t = ((p1.x - p3.x) * (p3.y - p4.y) - (p1.y - p3.y) * (p3.x - p4.x)) / denom;
    float u = -((p1.x - p2.x) * (p1.y - p3.y) - (p1.y - p2.y) * (p1.x - p3.x)) / denom;
    
    if (t >= 0.0f && t <= 1.0f && u >= 0.0f && u <= 1.0f) {
        if (intersection) {
            intersection->x = p1.x + t * (p2.x - p1.x);
            intersection->y = p1.y + t * (p2.y - p1.y);
        }
        return true;
    }
    
    return false;
}

bool GameMath::lineIntersectsRectangle(const Vector2D& p1, const Vector2D& p2, const Rectangle& rect) {
    // Check if line intersects any of the rectangle's edges
    Vector2D topLeft(rect.x, rect.y);
    Vector2D topRight(rect.x + rect.width, rect.y);
    Vector2D bottomLeft(rect.x, rect.y + rect.height);
    Vector2D bottomRight(rect.x + rect.width, rect.y + rect.height);
    
    return lineIntersectsLine(p1, p2, topLeft, topRight) ||
           lineIntersectsLine(p1, p2, topRight, bottomRight) ||
           lineIntersectsLine(p1, p2, bottomRight, bottomLeft) ||
           lineIntersectsLine(p1, p2, bottomLeft, topLeft);
}

bool GameMath::lineIntersectsCircle(const Vector2D& p1, const Vector2D& p2,
                                   const Vector2D& center, float radius) {
    Vector2D closest = GameMath::closestPointOnLine(p1, p2, center);
    return distanceSquared(closest, center) <= radius * radius;
}

// Physics calculations
Vector2D GameMath::calculateGravity(float mass1, float mass2, const Vector2D& pos1, const Vector2D& pos2) {
    const float G = 6.674e-11f; // Gravitational constant (simplified)
    float dist = distance(pos1, pos2);
    if (dist < GameMathConstants::EPSILON) return Vector2D(0.0f, 0.0f);
    
    float force = G * mass1 * mass2 / (dist * dist);
    Vector2D direction = (pos2 - pos1).normalized();
    
    return direction * force;
}

Vector2D GameMath::calculateCollisionResponse(const Vector2D& velocity1, const Vector2D& velocity2,
                                             float mass1, float mass2, const Vector2D& normal) {
    float relativeVelocity = dot(velocity1 - velocity2, normal);
    float restitution = 0.8f; // Coefficient of restitution
    
    if (relativeVelocity > 0.0f) return velocity1; // Objects are separating
    
    float impulse = -(1.0f + restitution) * relativeVelocity / (1.0f / mass1 + 1.0f / mass2);
    
    return velocity1 + normal * (impulse / mass1);
}

float GameMath::calculateKineticEnergy(float mass, float velocity) {
    return 0.5f * mass * velocity * velocity;
}

float GameMath::calculatePotentialEnergy(float mass, float height, float gravity) {
    return mass * gravity * height;
}

// Matrix operations (2D)
void GameMath::createTranslationMatrix(float* matrix, float x, float y) {
    memset(matrix, 0, 16 * sizeof(float));
    matrix[0] = 1.0f;
    matrix[5] = 1.0f;
    matrix[10] = 1.0f;
    matrix[12] = x;
    matrix[13] = y;
    matrix[15] = 1.0f;
}

void GameMath::createRotationMatrix(float* matrix, float angle) {
    float cos = cosf(angle);
    float sin = sinf(angle);
    
    memset(matrix, 0, 16 * sizeof(float));
    matrix[0] = cos;
    matrix[1] = -sin;
    matrix[4] = sin;
    matrix[5] = cos;
    matrix[10] = 1.0f;
    matrix[15] = 1.0f;
}

void GameMath::createScaleMatrix(float* matrix, float x, float y) {
    memset(matrix, 0, 16 * sizeof(float));
    matrix[0] = x;
    matrix[5] = y;
    matrix[10] = 1.0f;
    matrix[15] = 1.0f;
}

void GameMath::createTransformMatrix(float* matrix, float x, float y, float angle, float scaleX, float scaleY) {
    float cos = cosf(angle);
    float sin = sinf(angle);
    
    memset(matrix, 0, 16 * sizeof(float));
    matrix[0] = cos * scaleX;
    matrix[1] = -sin * scaleY;
    matrix[4] = sin * scaleX;
    matrix[5] = cos * scaleY;
    matrix[10] = 1.0f;
    matrix[12] = x;
    matrix[13] = y;
    matrix[15] = 1.0f;
}

void GameMath::multiplyMatrices(float* result, const float* a, const float* b) {
    for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 4; ++j) {
            result[i * 4 + j] = 0.0f;
            for (int k = 0; k < 4; ++k) {
                result[i * 4 + j] += a[i * 4 + k] * b[k * 4 + j];
            }
        }
    }
}

Vector2D GameMath::transformVector2D(const Vector2D& vector, const float* matrix) {
    return Vector2D(
        vector.x * matrix[0] + vector.y * matrix[4] + matrix[12],
        vector.x * matrix[1] + vector.y * matrix[5] + matrix[13]
    );
}

// Utility functions
bool GameMath::isNearlyEqual(float a, float b, float epsilon) {
    return std::abs(a - b) < epsilon;
}

bool GameMath::isNearlyZero(float value, float epsilon) {
    return std::abs(value) < epsilon;
}

float GameMath::sign(float value) {
    return (value > 0.0f) ? 1.0f : ((value < 0.0f) ? -1.0f : 0.0f);
}

int GameMath::sign(int value) {
    return (value > 0) ? 1 : ((value < 0) ? -1 : 0);
}

float GameMath::abs(float value) {
    return std::abs(value);
}

int GameMath::abs(int value) {
    return std::abs(value);
}

float GameMath::min(float a, float b) {
    return std::min(a, b);
}

float GameMath::max(float a, float b) {
    return std::max(a, b);
}

int GameMath::min(int a, int b) {
    return std::min(a, b);
}

int GameMath::max(int a, int b) {
    return std::max(a, b);
}

// Fast approximations
float GameMath::fastSin(float angle) {
    if (fastMathEnabled) {
        return fastSinLookup(angle);
    }
    return sinf(angle);
}

float GameMath::fastCos(float angle) {
    if (fastMathEnabled) {
        return fastCosLookup(angle);
    }
    return cosf(angle);
}

float GameMath::fastTan(float angle) {
    return fastSin(angle) / fastCos(angle);
}

float GameMath::fastSqrt(float value) {
    if (fastMathEnabled) {
        return fastSqrtNewton(value);
    }
    return sqrtf(value);
}

float GameMath::fastInvSqrt(float value) {
    if (fastMathEnabled) {
        return fastInvSqrtNewton(value);
    }
    return 1.0f / sqrtf(value);
}

float GameMath::fastPow(float base, float exponent) {
    // Fast power approximation using log and exp
    return expf(exponent * logf(base));
}

float GameMath::fastExp(float value) {
    // Fast exponential approximation
    return powf(2.0f, value * 1.442695f); // 1/ln(2)
}

float GameMath::fastLog(float value) {
    // Fast logarithm approximation
    return logf(value) * 0.693147f; // ln(2)
}

// Color operations
Vector2D GameMath::rgbToHsv(float r, float g, float b) {
    float max = std::max(r, std::max(g, b));
    float min = std::min(r, std::min(g, b));
    float delta = max - min;
    
    float h = 0.0f;
    if (delta != 0.0f) {
        if (max == r) {
            h = 60.0f * fmodf((g - b) / delta, 6.0f);
        } else if (max == g) {
            h = 60.0f * ((b - r) / delta + 2.0f);
        } else {
            h = 60.0f * ((r - g) / delta + 4.0f);
        }
    }
    
    float s = (max == 0.0f) ? 0.0f : delta / max;
    float v = max;
    
    return Vector2D(h, s); // Note: This should be Vector3D for full HSV
}

Vector2D GameMath::hsvToRgb(float h, float s, float v) {
    // This is a simplified implementation
    // In a real implementation, you would return Vector3D for RGB
    float c = v * s;
    float x = c * (1.0f - std::abs(fmodf(h / 60.0f, 2.0f) - 1.0f));
    float m = v - c;
    
    float r, g, b;
    if (h < 60.0f) {
        r = c; g = x; b = 0.0f;
    } else if (h < 120.0f) {
        r = x; g = c; b = 0.0f;
    } else if (h < 180.0f) {
        r = 0.0f; g = c; b = x;
    } else if (h < 240.0f) {
        r = 0.0f; g = x; b = c;
    } else if (h < 300.0f) {
        r = x; g = 0.0f; b = c;
    } else {
        r = c; g = 0.0f; b = x;
    }
    
    return Vector2D(r + m, g + m); // Note: This should be Vector3D for full RGB
}

float GameMath::lerpColor(float a, float b, float t) {
    return lerp(a, b, t);
}

// Performance utilities
void GameMath::enableFastMath(bool enable) {
    fastMathEnabled = enable;
}

bool GameMath::isFastMathEnabled() {
    return fastMathEnabled;
}

// Debug utilities
void GameMath::logMathStats() {
    LOGI("GameMath Stats:");
    LOGI("  Fast Math Enabled: %s", fastMathEnabled ? "Yes" : "No");
    LOGI("  Math Precision: %.6f", mathPrecision);
    LOGI("  Lookup Tables Initialized: %s", lookupTablesInitialized ? "Yes" : "No");
}

float GameMath::getMathPrecision() {
    return mathPrecision;
}

// Private methods

void GameMath::initializeLookupTables() {
    for (int i = 0; i < 360; ++i) {
        float angle = static_cast<float>(i) * GameMathConstants::DEG_TO_RAD;
        sinTable[i] = sinf(angle);
        cosTable[i] = cosf(angle);
    }
    lookupTablesInitialized = true;
    LOGI("GameMath lookup tables initialized");
}

float GameMath::fastSinLookup(float angle) {
    // Normalize angle to 0-360 degrees
    while (angle < 0.0f) angle += GameMathConstants::TWO_PI;
    while (angle >= GameMathConstants::TWO_PI) angle -= GameMathConstants::TWO_PI;
    
    int index = static_cast<int>(angle * GameMathConstants::RAD_TO_DEG);
    return sinTable[index];
}

float GameMath::fastCosLookup(float angle) {
    // Normalize angle to 0-360 degrees
    while (angle < 0.0f) angle += GameMathConstants::TWO_PI;
    while (angle >= GameMathConstants::TWO_PI) angle -= GameMathConstants::TWO_PI;
    
    int index = static_cast<int>(angle * GameMathConstants::RAD_TO_DEG);
    return cosTable[index];
}

float GameMath::fastSqrtNewton(float value) {
    if (value < 0.0f) return 0.0f;
    if (value == 0.0f) return 0.0f;
    
    float x = value;
    for (int i = 0; i < 4; ++i) {
        x = 0.5f * (x + value / x);
    }
    return x;
}

float GameMath::fastInvSqrtNewton(float value) {
    if (value <= 0.0f) return 0.0f;
    
    float x = value;
    for (int i = 0; i < 4; ++i) {
        x = x * (1.5f - 0.5f * value * x * x);
    }
    return x;
}

float GameMath::noise1DInternal(float x) {
    // Simplified 1D noise implementation
    int n = static_cast<int>(x) + noiseSeed;
    n = (n << 13) ^ n;
    return (1.0f - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0f);
}

float GameMath::noise2DInternal(float x, float y) {
    // Simplified 2D noise implementation
    int n = static_cast<int>(x) + static_cast<int>(y) * 57 + noiseSeed;
    n = (n << 13) ^ n;
    return (1.0f - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0f);
}

float GameMath::fade(float t) {
    return t * t * t * (t * (t * 6.0f - 15.0f) + 10.0f);
}

float GameMath::lerpNoise(float a, float b, float t) {
    return a + t * (b - a);
}

float GameMath::grad(int hash, float x) {
    return (hash & 1) == 0 ? x : -x;
}

float GameMath::grad(int hash, float x, float y) {
    int h = hash & 3;
    return ((h & 1) == 0 ? x : -x) + ((h & 2) == 0 ? y : -y);
}

Vector2D GameMath::closestPointOnLine(const Vector2D& p1, const Vector2D& p2, const Vector2D& point) {
    Vector2D line = p2 - p1;
    float lineLength = line.length();
    if (lineLength < GameMathConstants::EPSILON) return p1;
    
    Vector2D lineDir = line / lineLength;
    Vector2D pointToP1 = point - p1;
    
    float t = dot(pointToP1, lineDir);
    t = clamp(t, 0.0f, lineLength);
    
    return p1 + lineDir * t;
}

float GameMath::bounce(float t) {
    if (t < 1.0f / 2.75f) {
        return 7.5625f * t * t;
    } else if (t < 2.0f / 2.75f) {
        t -= 1.5f / 2.75f;
        return 7.5625f * t * t + 0.75f;
    } else if (t < 2.5f / 2.75f) {
        t -= 2.25f / 2.75f;
        return 7.5625f * t * t + 0.9375f;
    } else {
        t -= 2.625f / 2.75f;
        return 7.5625f * t * t + 0.984375f;
    }
}

float GameMath::elastic(float t) {
    if (t == 0.0f) return 0.0f;
    if (t == 1.0f) return 1.0f;
    
    float p = 0.3f;
    float s = p / 4.0f;
    
    return powf(2.0f, -10.0f * t) * sinf((t - s) * (2.0f * GameMathConstants::PI) / p) + 1.0f;
}
