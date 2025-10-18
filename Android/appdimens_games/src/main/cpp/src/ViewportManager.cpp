/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Viewport Manager Implementation
 *
 * Description:
 * Implementation of viewport management and camera systems for game development.
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

#include "ViewportManager.h"
#include <algorithm>
#include <cmath>

ViewportManager::ViewportManager() {
    LOGI("ViewportManager constructor called");
    activeViewportId = 0;
}

ViewportManager::~ViewportManager() {
    LOGI("ViewportManager destructor called");
}

void ViewportManager::initialize(const GameScreenConfig& config) {
    LOGI("ViewportManager initializing with screen: %dx%d", config.width, config.height);
    
    screenConfig = config;
    
    // Initialize default viewport
    viewportConfig.bounds = Rectangle(0.0f, 0.0f, static_cast<float>(config.width), static_cast<float>(config.height));
    viewportConfig.mode = ViewportMode::FIT_ALL;
    viewportConfig.cameraType = CameraType::ORTHOGRAPHIC;
    viewportConfig.zoom = 1.0f;
    viewportConfig.offset = Vector2D(0.0f, 0.0f);
    
    // Initialize camera state
    cameraState.position = Vector2D(0.0f, 0.0f);
    cameraState.rotation = 0.0f;
    cameraState.zoom = 1.0f;
    cameraState.target = Vector2D(0.0f, 0.0f);
    cameraState.isFollowing = false;
    
    // Initialize animation state
    cameraAnimation.isAnimating = false;
    cameraAnimation.duration = 0.0f;
    cameraAnimation.elapsed = 0.0f;
}

void ViewportManager::setViewport(const Rectangle& bounds, ViewportMode mode) {
    viewportConfig.bounds = bounds;
    viewportConfig.mode = mode;
    LOGI("Viewport set: (%.1f, %.1f, %.1f, %.1f), mode: %d", 
         bounds.x, bounds.y, bounds.width, bounds.height, static_cast<int>(mode));
}

void ViewportManager::setViewportConfig(const ViewportConfig& config) {
    viewportConfig = config;
}

ViewportConfig ViewportManager::getViewportConfig() const {
    return viewportConfig;
}

void ViewportManager::setCameraType(CameraType type) {
    viewportConfig.cameraType = type;
    LOGI("Camera type set to: %d", static_cast<int>(type));
}

CameraType ViewportManager::getCameraType() const {
    return viewportConfig.cameraType;
}

void ViewportManager::setCameraPosition(const Vector2D& position) {
    cameraState.position = position;
}

Vector2D ViewportManager::getCameraPosition() const {
    return cameraState.position;
}

void ViewportManager::setCameraRotation(float rotation) {
    cameraState.rotation = rotation;
}

float ViewportManager::getCameraRotation() const {
    return cameraState.rotation;
}

void ViewportManager::setCameraZoom(float zoom) {
    cameraState.zoom = std::max(0.1f, std::min(10.0f, zoom)); // Clamp zoom
    viewportConfig.zoom = cameraState.zoom;
}

float ViewportManager::getCameraZoom() const {
    return cameraState.zoom;
}

void ViewportManager::setCameraTarget(const Vector2D& target) {
    cameraState.target = target;
}

Vector2D ViewportManager::getCameraTarget() const {
    return cameraState.target;
}

void ViewportManager::setFollowingCamera(bool following) {
    cameraState.isFollowing = following;
}

bool ViewportManager::isFollowingCamera() const {
    return cameraState.isFollowing;
}

void ViewportManager::setViewportMode(ViewportMode mode) {
    viewportConfig.mode = mode;
}

ViewportMode ViewportManager::getViewportMode() const {
    return viewportConfig.mode;
}

Vector2D ViewportManager::screenToWorld(const Vector2D& screenPos) const {
    // Basic screen to world coordinate transformation
    Vector2D viewportCenter = calculateViewportCenter();
    Vector2D offset = screenPos - viewportCenter;
    
    // Apply camera transformations
    Vector2D worldPos = cameraState.position + (offset * (1.0f / cameraState.zoom));
    
    // Apply rotation if needed
    if (cameraState.rotation != 0.0f) {
        float cos = cosf(cameraState.rotation);
        float sin = sinf(cameraState.rotation);
        worldPos = Vector2D(
            worldPos.x * cos - worldPos.y * sin,
            worldPos.x * sin + worldPos.y * cos
        );
    }
    
    return worldPos;
}

Vector2D ViewportManager::worldToScreen(const Vector2D& worldPos) const {
    // Basic world to screen coordinate transformation
    Vector2D viewportCenter = calculateViewportCenter();
    
    // Apply inverse camera transformations
    Vector2D offset = worldPos - cameraState.position;
    
    // Apply inverse rotation if needed
    if (cameraState.rotation != 0.0f) {
        float cos = cosf(-cameraState.rotation);
        float sin = sinf(-cameraState.rotation);
        offset = Vector2D(
            offset.x * cos - offset.y * sin,
            offset.x * sin + offset.y * cos
        );
    }
    
    Vector2D screenPos = viewportCenter + (offset * cameraState.zoom);
    return screenPos;
}

Vector2D ViewportManager::screenToViewport(const Vector2D& screenPos) const {
    // Convert screen coordinates to viewport coordinates
    return Vector2D(
        screenPos.x - viewportConfig.bounds.x,
        screenPos.y - viewportConfig.bounds.y
    );
}

Vector2D ViewportManager::viewportToScreen(const Vector2D& viewportPos) const {
    // Convert viewport coordinates to screen coordinates
    return Vector2D(
        viewportPos.x + viewportConfig.bounds.x,
        viewportPos.y + viewportConfig.bounds.y
    );
}

Rectangle ViewportManager::screenToWorld(const Rectangle& screenRect) const {
    Vector2D topLeft = screenToWorld(Vector2D(screenRect.x, screenRect.y));
    Vector2D bottomRight = screenToWorld(Vector2D(screenRect.x + screenRect.width, screenRect.y + screenRect.height));
    
    return Rectangle(
        topLeft.x,
        topLeft.y,
        bottomRight.x - topLeft.x,
        bottomRight.y - topLeft.y
    );
}

Rectangle ViewportManager::worldToScreen(const Rectangle& worldRect) const {
    Vector2D topLeft = worldToScreen(Vector2D(worldRect.x, worldRect.y));
    Vector2D bottomRight = worldToScreen(Vector2D(worldRect.x + worldRect.width, worldRect.y + worldRect.height));
    
    return Rectangle(
        topLeft.x,
        topLeft.y,
        bottomRight.x - topLeft.x,
        bottomRight.y - topLeft.y
    );
}

Rectangle ViewportManager::screenToViewport(const Rectangle& screenRect) const {
    return Rectangle(
        screenRect.x - viewportConfig.bounds.x,
        screenRect.y - viewportConfig.bounds.y,
        screenRect.width,
        screenRect.height
    );
}

Rectangle ViewportManager::viewportToScreen(const Rectangle& viewportRect) const {
    return Rectangle(
        viewportRect.x + viewportConfig.bounds.x,
        viewportRect.y + viewportConfig.bounds.y,
        viewportRect.width,
        viewportRect.height
    );
}

float ViewportManager::getViewportScale() const {
    return calculateViewportScale(viewportConfig.bounds, viewportConfig.mode);
}

float ViewportManager::getAspectRatio() const {
    return (viewportConfig.bounds.height > 0.0f) ? 
           (viewportConfig.bounds.width / viewportConfig.bounds.height) : 1.0f;
}

Vector2D ViewportManager::getViewportSize() const {
    return Vector2D(viewportConfig.bounds.width, viewportConfig.bounds.height);
}

Vector2D ViewportManager::getWorldSize() const {
    // Return world size based on viewport and zoom
    return Vector2D(
        viewportConfig.bounds.width / cameraState.zoom,
        viewportConfig.bounds.height / cameraState.zoom
    );
}

int ViewportManager::createViewport(const ViewportConfig& config) {
    int id = static_cast<int>(viewports.size());
    viewports.push_back(config);
    LOGI("Created viewport with ID: %d", id);
    return id;
}

void ViewportManager::setActiveViewport(int viewportId) {
    if (viewportId >= 0 && viewportId < static_cast<int>(viewports.size())) {
        activeViewportId = viewportId;
        viewportConfig = viewports[viewportId];
        LOGI("Set active viewport to ID: %d", viewportId);
    }
}

int ViewportManager::getActiveViewport() const {
    return activeViewportId;
}

void ViewportManager::removeViewport(int viewportId) {
    if (viewportId >= 0 && viewportId < static_cast<int>(viewports.size())) {
        viewports.erase(viewports.begin() + viewportId);
        if (activeViewportId >= viewportId) {
            activeViewportId = std::max(0, activeViewportId - 1);
        }
        LOGI("Removed viewport with ID: %d", viewportId);
    }
}

ViewportConfig ViewportManager::getViewportConfig(int viewportId) const {
    if (viewportId >= 0 && viewportId < static_cast<int>(viewports.size())) {
        return viewports[viewportId];
    }
    return ViewportConfig();
}

void ViewportManager::setViewportConfig(int viewportId, const ViewportConfig& config) {
    if (viewportId >= 0 && viewportId < static_cast<int>(viewports.size())) {
        viewports[viewportId] = config;
    }
}

Rectangle ViewportManager::calculateViewportBounds(const Rectangle& contentBounds, ViewportMode mode) const {
    switch (mode) {
        case ViewportMode::FIT_WIDTH:
            return Rectangle(0.0f, 0.0f, viewportConfig.bounds.width, 
                           viewportConfig.bounds.width * (contentBounds.height / contentBounds.width));
        case ViewportMode::FIT_HEIGHT:
            return Rectangle(0.0f, 0.0f, 
                           viewportConfig.bounds.height * (contentBounds.width / contentBounds.height),
                           viewportConfig.bounds.height);
        case ViewportMode::FIT_ALL:
            {
                float scaleX = viewportConfig.bounds.width / contentBounds.width;
                float scaleY = viewportConfig.bounds.height / contentBounds.height;
                float scale = std::min(scaleX, scaleY);
                return Rectangle(0.0f, 0.0f, contentBounds.width * scale, contentBounds.height * scale);
            }
        case ViewportMode::STRETCH:
            return viewportConfig.bounds;
        case ViewportMode::CROP:
            {
                float scaleX = viewportConfig.bounds.width / contentBounds.width;
                float scaleY = viewportConfig.bounds.height / contentBounds.height;
                float scale = std::max(scaleX, scaleY);
                return Rectangle(0.0f, 0.0f, contentBounds.width * scale, contentBounds.height * scale);
            }
        default:
            return viewportConfig.bounds;
    }
}

Vector2D ViewportManager::calculateViewportOffset(const Rectangle& contentBounds, ViewportMode mode) const {
    Rectangle viewportBounds = calculateViewportBounds(contentBounds, mode);
    return Vector2D(
        (viewportConfig.bounds.width - viewportBounds.width) * 0.5f,
        (viewportConfig.bounds.height - viewportBounds.height) * 0.5f
    );
}

float ViewportManager::calculateViewportScale(const Rectangle& contentBounds, ViewportMode mode) const {
    switch (mode) {
        case ViewportMode::FIT_WIDTH:
            return viewportConfig.bounds.width / contentBounds.width;
        case ViewportMode::FIT_HEIGHT:
            return viewportConfig.bounds.height / contentBounds.height;
        case ViewportMode::FIT_ALL:
            {
                float scaleX = viewportConfig.bounds.width / contentBounds.width;
                float scaleY = viewportConfig.bounds.height / contentBounds.height;
                return std::min(scaleX, scaleY);
            }
        case ViewportMode::STRETCH:
            return 1.0f;
        case ViewportMode::CROP:
            {
                float scaleX = viewportConfig.bounds.width / contentBounds.width;
                float scaleY = viewportConfig.bounds.height / contentBounds.height;
                return std::max(scaleX, scaleY);
            }
        default:
            return 1.0f;
    }
}

void ViewportManager::handleOrientationChange(int newOrientation) {
    LOGI("Handling orientation change to: %d", newOrientation);
    
    // Update screen configuration
    screenConfig.orientation = newOrientation;
    screenConfig.isLandscape = (newOrientation == 1); // 1 = landscape
    
    // Adjust viewport bounds
    viewportConfig.bounds = Rectangle(0.0f, 0.0f, 
                                    static_cast<float>(screenConfig.width), 
                                    static_cast<float>(screenConfig.height));
}

void ViewportManager::updateForScreenConfig(const GameScreenConfig& config) {
    screenConfig = config;
    viewportConfig.bounds = Rectangle(0.0f, 0.0f, 
                                    static_cast<float>(config.width), 
                                    static_cast<float>(config.height));
}

bool ViewportManager::isPointInViewport(const Vector2D& point) const {
    return viewportConfig.bounds.contains(point);
}

bool ViewportManager::isRectangleInViewport(const Rectangle& rect) const {
    return viewportConfig.bounds.intersection(rect).width > 0.0f && 
           viewportConfig.bounds.intersection(rect).height > 0.0f;
}

Rectangle ViewportManager::getVisibleWorldBounds() const {
    Vector2D topLeft = screenToWorld(Vector2D(viewportConfig.bounds.x, viewportConfig.bounds.y));
    Vector2D bottomRight = screenToWorld(Vector2D(
        viewportConfig.bounds.x + viewportConfig.bounds.width,
        viewportConfig.bounds.y + viewportConfig.bounds.height
    ));
    
    return Rectangle(
        topLeft.x,
        topLeft.y,
        bottomRight.x - topLeft.x,
        bottomRight.y - topLeft.y
    );
}

void ViewportManager::moveCameraTo(const Vector2D& target, float duration) {
    cameraAnimation.startPosition = cameraState.position;
    cameraAnimation.targetPosition = target;
    cameraAnimation.duration = duration;
    cameraAnimation.elapsed = 0.0f;
    cameraAnimation.isAnimating = true;
}

void ViewportManager::zoomCameraTo(float targetZoom, float duration) {
    cameraAnimation.startZoom = cameraState.zoom;
    cameraAnimation.targetZoom = targetZoom;
    cameraAnimation.duration = duration;
    cameraAnimation.elapsed = 0.0f;
    cameraAnimation.isAnimating = true;
}

void ViewportManager::rotateCameraTo(float targetRotation, float duration) {
    cameraAnimation.startRotation = cameraState.rotation;
    cameraAnimation.targetRotation = targetRotation;
    cameraAnimation.duration = duration;
    cameraAnimation.elapsed = 0.0f;
    cameraAnimation.isAnimating = true;
}

void ViewportManager::updateCamera(float deltaTime) {
    if (cameraAnimation.isAnimating) {
        updateCameraAnimation(deltaTime);
    }
    
    if (cameraState.isFollowing) {
        // Simple following camera logic
        Vector2D direction = cameraState.target - cameraState.position;
        float distance = direction.length();
        if (distance > 0.1f) {
            Vector2D move = direction.normalized() * (distance * 0.1f); // 10% of distance per frame
            cameraState.position = cameraState.position + move;
        }
    }
}

void ViewportManager::applyViewportToOpenGL() const {
    // Apply viewport to OpenGL
    glViewport(
        static_cast<GLint>(viewportConfig.bounds.x),
        static_cast<GLint>(viewportConfig.bounds.y),
        static_cast<GLsizei>(viewportConfig.bounds.width),
        static_cast<GLsizei>(viewportConfig.bounds.height)
    );
}

void ViewportManager::applyCameraToOpenGL() const {
    // Apply camera transformations to OpenGL
    // This would typically involve setting up projection and modelview matrices
    // Implementation depends on the specific OpenGL setup
}

void ViewportManager::drawViewportBounds() const {
    // Draw viewport bounds for debugging
    // Implementation would depend on the rendering system
}

void ViewportManager::drawCameraInfo() const {
    // Draw camera information for debugging
    // Implementation would depend on the rendering system
}

// Private methods

Vector2D ViewportManager::calculateViewportCenter() const {
    return Vector2D(
        viewportConfig.bounds.x + viewportConfig.bounds.width * 0.5f,
        viewportConfig.bounds.y + viewportConfig.bounds.height * 0.5f
    );
}

float ViewportManager::calculateOptimalZoom() const {
    // Calculate optimal zoom based on content and viewport
    return 1.0f; // Simplified implementation
}

Rectangle ViewportManager::calculateContentBounds() const {
    // Calculate content bounds based on current setup
    return Rectangle(0.0f, 0.0f, 1920.0f, 1080.0f); // Default content size
}

float ViewportManager::easeInOutCubic(float t) const {
    t = std::max(0.0f, std::min(1.0f, t));
    return t < 0.5f ? 4.0f * t * t * t : 1.0f - powf(-2.0f * t + 2.0f, 3.0f) / 2.0f;
}

void ViewportManager::updateCameraAnimation(float deltaTime) {
    cameraAnimation.elapsed += deltaTime;
    
    if (cameraAnimation.elapsed >= cameraAnimation.duration) {
        // Animation complete
        cameraState.position = cameraAnimation.targetPosition;
        cameraState.zoom = cameraAnimation.targetZoom;
        cameraState.rotation = cameraAnimation.targetRotation;
        cameraAnimation.isAnimating = false;
    } else {
        // Interpolate values
        float t = cameraAnimation.elapsed / cameraAnimation.duration;
        t = easeInOutCubic(t);
        
        cameraState.position = Vector2D(
            cameraAnimation.startPosition.x + (cameraAnimation.targetPosition.x - cameraAnimation.startPosition.x) * t,
            cameraAnimation.startPosition.y + (cameraAnimation.targetPosition.y - cameraAnimation.startPosition.y) * t
        );
        
        cameraState.zoom = cameraAnimation.startZoom + (cameraAnimation.targetZoom - cameraAnimation.startZoom) * t;
        cameraState.rotation = cameraAnimation.startRotation + (cameraAnimation.targetRotation - cameraAnimation.startRotation) * t;
    }
}
