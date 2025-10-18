/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Viewport Manager
 *
 * Description:
 * Manages game viewports, camera systems, and screen-to-world coordinate transformations.
 * Provides support for different viewport modes and aspect ratio handling.
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

#ifndef VIEWPORT_MANAGER_H
#define VIEWPORT_MANAGER_H

#include "AppDimensGames.h"
#include <vector>

// Camera types for different game scenarios
enum class CameraType {
    ORTHOGRAPHIC,   // 2D games
    PERSPECTIVE,    // 3D games
    FOLLOW,         // Following camera
    FIXED           // Fixed position camera
};

// Viewport configuration
struct ViewportConfig {
    Rectangle bounds;
    ViewportMode mode;
    CameraType cameraType;
    float zoom;
    Vector2D offset;
    float nearPlane;
    float farPlane;
    float fieldOfView;
    
    ViewportConfig() : bounds(0, 0, 0, 0), mode(ViewportMode::FIT_ALL),
                      cameraType(CameraType::ORTHOGRAPHIC), zoom(1.0f),
                      offset(0, 0), nearPlane(0.1f), farPlane(1000.0f),
                      fieldOfView(45.0f) {}
};

// Camera state
struct CameraState {
    Vector2D position;
    float rotation;
    float zoom;
    Vector2D target;
    bool isFollowing;
    
    CameraState() : position(0, 0), rotation(0.0f), zoom(1.0f),
                   target(0, 0), isFollowing(false) {}
};

class ViewportManager {
public:
    ViewportManager();
    ~ViewportManager();
    
    // Initialize viewport system
    void initialize(const GameScreenConfig& config);
    
    // Viewport management
    void setViewport(const Rectangle& bounds, ViewportMode mode = ViewportMode::FIT_ALL);
    void setViewportConfig(const ViewportConfig& config);
    ViewportConfig getViewportConfig() const;
    
    // Camera management
    void setCameraType(CameraType type);
    CameraType getCameraType() const;
    
    void setCameraPosition(const Vector2D& position);
    Vector2D getCameraPosition() const;
    
    void setCameraRotation(float rotation);
    float getCameraRotation() const;
    
    void setCameraZoom(float zoom);
    float getCameraZoom() const;
    
    void setCameraTarget(const Vector2D& target);
    Vector2D getCameraTarget() const;
    
    void setFollowingCamera(bool following);
    bool isFollowingCamera() const;
    
    // Viewport modes
    void setViewportMode(ViewportMode mode);
    ViewportMode getViewportMode() const;
    
    // Coordinate transformations
    Vector2D screenToWorld(const Vector2D& screenPos) const;
    Vector2D worldToScreen(const Vector2D& worldPos) const;
    Vector2D screenToViewport(const Vector2D& screenPos) const;
    Vector2D viewportToScreen(const Vector2D& viewportPos) const;
    
    // Rectangle transformations
    Rectangle screenToWorld(const Rectangle& screenRect) const;
    Rectangle worldToScreen(const Rectangle& worldRect) const;
    Rectangle screenToViewport(const Rectangle& screenRect) const;
    Rectangle viewportToScreen(const Rectangle& viewportRect) const;
    
    // Scaling and aspect ratio
    float getViewportScale() const;
    float getAspectRatio() const;
    Vector2D getViewportSize() const;
    Vector2D getWorldSize() const;
    
    // Multiple viewport support
    int createViewport(const ViewportConfig& config);
    void setActiveViewport(int viewportId);
    int getActiveViewport() const;
    void removeViewport(int viewportId);
    
    ViewportConfig getViewportConfig(int viewportId) const;
    void setViewportConfig(int viewportId, const ViewportConfig& config);
    
    // Viewport calculations
    Rectangle calculateViewportBounds(const Rectangle& contentBounds, ViewportMode mode) const;
    Vector2D calculateViewportOffset(const Rectangle& contentBounds, ViewportMode mode) const;
    float calculateViewportScale(const Rectangle& contentBounds, ViewportMode mode) const;
    
    // Screen orientation handling
    void handleOrientationChange(int newOrientation);
    void updateForScreenConfig(const GameScreenConfig& config);
    
    // Utility functions
    bool isPointInViewport(const Vector2D& point) const;
    bool isRectangleInViewport(const Rectangle& rect) const;
    Rectangle getVisibleWorldBounds() const;
    
    // Camera movement and animation
    void moveCameraTo(const Vector2D& target, float duration = 1.0f);
    void zoomCameraTo(float targetZoom, float duration = 1.0f);
    void rotateCameraTo(float targetRotation, float duration = 1.0f);
    
    void updateCamera(float deltaTime);
    
    // OpenGL integration
    void applyViewportToOpenGL() const;
    void applyCameraToOpenGL() const;
    
    // Debug and visualization
    void drawViewportBounds() const;
    void drawCameraInfo() const;
    
private:
    GameScreenConfig screenConfig;
    ViewportConfig viewportConfig;
    CameraState cameraState;
    
    // Multiple viewport support
    std::vector<ViewportConfig> viewports;
    int activeViewportId;
    
    // Animation state
    struct CameraAnimation {
        Vector2D startPosition;
        Vector2D targetPosition;
        float startZoom;
        float targetZoom;
        float startRotation;
        float targetRotation;
        float duration;
        float elapsed;
        bool isAnimating;
    } cameraAnimation;
    
    // Internal calculations
    Vector2D calculateViewportCenter() const;
    float calculateOptimalZoom() const;
    Rectangle calculateContentBounds() const;
    
    // Animation helpers
    float easeInOutCubic(float t) const;
    void updateCameraAnimation(float deltaTime);
    
    // OpenGL helpers
    void setupOrthographicProjection() const;
    void setupPerspectiveProjection() const;
    void applyCameraTransform() const;
};

#endif // VIEWPORT_MANAGER_H
