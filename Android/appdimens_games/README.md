---
layout: default
title: "CMakeLists.txt"
---

<div align="center">
    <h1>🎮 AppDimens Games</h1>
    <p><strong>Responsive Game Development for Android with C++/NDK Support</strong></p>
    <p>Specialized AppDimens module designed for Android game development with native performance, OpenGL ES integration, and advanced viewport management.</p>
    
[![Version](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%2023+-orange.svg)](https://developer.android.com/)
[![NDK](https://img.shields.io/badge/NDK-r21+-green.svg)](https://developer.android.com/ndk)
</div>

> Languages: [Português (BR)](../../LANG/pt-BR/Android/appdimens_games/README.md) | [Español](../../LANG/es/Android/appdimens_games/README.md) | [हिन्दी](../../LANG/hi/Android/appdimens_games/README.md) | [Русский](../../LANG/ru/Android/appdimens_games/README.md) | [中文](../../LANG/zh/Android/appdimens_games/README.md) | [日本語](../../LANG/ja/Android/appdimens_games/README.md)

---

## 🎯 Overview

**AppDimens Games** is a specialized module of the AppDimens library designed specifically for Android game development. It combines responsive dimensioning with native C++/NDK performance, OpenGL ES utilities, and advanced game-specific features.

### 🧠 Key Features

- **🎮 Game-Specific Scaling**: Preset calculations for buttons, text, players, enemies, and UI elements
- **⚡ Native Performance**: C++/NDK integration for high-performance game engines
- **🎨 OpenGL ES Support**: Utilities for OpenGL ES 2.0/3.0 rendering with performance monitoring
- **📐 Viewport Management**: Advanced viewport and camera systems for different game scenarios
- **📊 Performance Monitoring**: Real-time performance metrics and optimization suggestions
- **🔄 Cross-Platform Consistency**: Ensures consistent game experience across different screen sizes

---

## 📋 Requisitos Mínimos

| Requisito | Versão Mínima | Recomendado |
|-----------|---------------|-------------|
| **Kotlin** | 2.0.0 | 2.2.20 |
| **Android Gradle Plugin** | 8.0.0 | 8.13.0 |
| **compileSdk** | 34 | 36 |
| **minSdk** | 23 | 23 |
| **targetSdk** | 34 | 36 |
| **Jetpack Compose BOM** | 2024.01.00 | 2025.10.00 |
| **NDK** | r21+ | r27+ |
| **CMake** | 3.18.1 | 3.22.1 |
| **C++ Standard** | C++17 | C++17 |
| **Page Size** | Compatível com 16KB | ✅ |

### Configuração do Projeto

```kotlin
// build.gradle.kts (Project)
plugins {
    id("com.android.application") version "8.13.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.20" apply false
}

// build.gradle.kts (Module)
android {
    namespace = "com.example.game"
    compileSdk = 36
    
    defaultConfig {
        minSdk = 23
        targetSdk = 36
        
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
        }
        
        externalNativeBuild {
            cmake {
                cppFlags += listOf("-std=c++17", "-frtti", "-fexceptions")
                arguments += listOf("-DANDROID_STL=c++_shared")
            }
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
    
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}
```

---

## 🚀 Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-games:1.0.8")
}
```

### NDK Configuration

```kotlin
android {
    compileSdk 34
    
    defaultConfig {
        minSdk 23
        targetSdk 34
        
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
        }
    }
    
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}
```

### CMake Configuration

```cmake
# CMakeLists.txt
cmake_minimum_required(VERSION 3.22.1)
project("AppDimensGames")

# Add AppDimens Games
add_subdirectory(${CMAKE_CURRENT_SOURCE_DIR}/../../../appdimens_games/src/main/cpp appdimens_games)

# Link to your target
target_link_libraries(${CMAKE_PROJECT_NAME} appdimens_games)
```

---

## 🎨 Usage Examples

### 🎮 Basic Game Integration

#### Kotlin/Java Initialization

```kotlin
class GameActivity : Activity() {
    private lateinit var appDimensGames: AppDimensGames
    private lateinit var gameRenderer: GameRenderer
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize AppDimens Games
        appDimensGames = AppDimensGames.getInstance()
        appDimensGames.initialize(this)
        
        // Set up game renderer
        gameRenderer = GameRenderer(this, appDimensGames)
        setContentView(gameRenderer)
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Update screen configuration
        appDimensGames.updateScreenConfiguration(newConfig)
    }
}
```

#### Game Dimension Calculations

```kotlin
class GameRenderer : GLSurfaceView.Renderer {
    private lateinit var appDimensGames: AppDimensGames
    
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        // Initialize OpenGL
        appDimensGames.getOpenGLUtils().initialize()
    }
    
    override fun onDrawFrame(gl: GL10?) {
        // Calculate responsive dimensions for game elements
        val buttonSize = appDimensGames.calculateButtonSize(48.0f)
        val textSize = appDimensGames.calculateTextSize(16.0f)
        val playerSize = appDimensGames.calculatePlayerSize(64.0f)
        val enemySize = appDimensGames.calculateEnemySize(32.0f)
        
        // Use different scaling types
        val dynamicDimension = appDimensGames.calculateDimension(100.0f, GameDimensionType.DYNAMIC)
        val fixedDimension = appDimensGames.calculateDimension(100.0f, GameDimensionType.FIXED)
        val gameWorldDimension = appDimensGames.calculateDimension(100.0f, GameDimensionType.GAME_WORLD)
        val uiOverlayDimension = appDimensGames.calculateDimension(100.0f, GameDimensionType.UI_OVERLAY)
        
        // Render game elements with calculated dimensions
        renderGameElements(buttonSize, textSize, playerSize, enemySize)
    }
}
```

### 🎯 Game Dimension Types

#### Dynamic Scaling
Proportional scaling ideal for containers and fluid layouts:

```kotlin
val dimension = appDimensGames.calculateDimension(baseValue, GameDimensionType.DYNAMIC)
```

#### Fixed Scaling
Logarithmic scaling ideal for UI elements, buttons, and margins:

```kotlin
val dimension = appDimensGames.calculateDimension(baseValue, GameDimensionType.FIXED)
```

#### Game World Scaling
Maintains consistent world coordinates for game objects:

```kotlin
val dimension = appDimensGames.calculateDimension(baseValue, GameDimensionType.GAME_WORLD)
```

#### UI Overlay Scaling
For HUD and overlay elements:

```kotlin
val dimension = appDimensGames.calculateDimension(baseValue, GameDimensionType.UI_OVERLAY)
```

### 🎮 Game-Specific Calculations

```kotlin
class GameUI {
    private lateinit var appDimensGames: AppDimensGames
    
    fun setupGameUI() {
        // Calculate responsive dimensions for different game elements
        val buttonSize = appDimensGames.calculateButtonSize(48.0f)
        val textSize = appDimensGames.calculateTextSize(16.0f)
        val playerSize = appDimensGames.calculatePlayerSize(64.0f)
        val enemySize = appDimensGames.calculateEnemySize(32.0f)
        
        // Work with vectors and rectangles
        val baseVector = GameVector2D(100.0f, 50.0f)
        val scaledVector = appDimensGames.calculateVector2D(baseVector, GameDimensionType.DYNAMIC)
        
        val baseRectangle = GameRectangle(0.0f, 0.0f, 200.0f, 100.0f)
        val scaledRectangle = appDimensGames.calculateRectangle(baseRectangle, GameDimensionType.FIXED)
        
        // Apply to game elements
        setupButtons(buttonSize)
        setupText(textSize)
        setupPlayer(playerSize)
        setupEnemies(enemySize)
    }
}
```

---

## 🔧 C++ Integration

### 🎯 Native API Usage

```cpp
#include "AppDimensGames.h"

class GameEngine {
private:
    AppDimensGames& appDimensGames;
    
public:
    GameEngine(JNIEnv* env, jobject context) {
        // Initialize the library
        appDimensGames = AppDimensGames::getInstance();
        appDimensGames.initialize(env, context);
    }
    
    void updateGame() {
        // Calculate dimensions
        float buttonSize = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
        float playerSize = appDimensGames.calculateDimension(64.0f, GameDimensionType::GAME_WORLD);
        
        // Work with vectors
        Vector2D baseVector(100.0f, 50.0f);
        Vector2D scaledVector = appDimensGames.calculateVector2D(baseVector, GameDimensionType::DYNAMIC);
        
        // Work with rectangles
        Rectangle baseRect(0.0f, 0.0f, 200.0f, 100.0f);
        Rectangle scaledRect = appDimensGames.calculateRectangle(baseRect, GameDimensionType::UI_OVERLAY);
        
        // Access specialized components
        ViewportManager* viewport = appDimensGames.getViewportManager();
        PerformanceMonitor* monitor = appDimensGames.getPerformanceMonitor();
        OpenGLUtils* glUtils = appDimensGames.getOpenGLUtils();
        
        // Update game logic
        updateGameLogic(buttonSize, playerSize, scaledVector, scaledRect);
    }
};
```

### 🎨 OpenGL ES Integration

```cpp
class GameRenderer {
private:
    OpenGLUtils* glUtils;
    AppDimensGames& appDimensGames;
    
public:
    void initialize() {
        // Initialize OpenGL utilities
        glUtils = appDimensGames.getOpenGLUtils();
        
        // Create shader program
        ShaderProgram* program = glUtils->createShaderProgram("basic", vertexSource, fragmentSource);
        
        // Set viewport with responsive dimensions
        Rectangle viewport = calculateResponsiveViewport();
        glUtils->setViewport(viewport);
    }
    
    void render() {
        // Begin profiling
        PerformanceMonitor* monitor = appDimensGames.getPerformanceMonitor();
        monitor->beginProfile("gameRender", PerformanceCategory::RENDERING);
        
        // Render with calculated dimensions
        float elementSize = appDimensGames.calculateDimension(100.0f, GameDimensionType::GAME_WORLD);
        
        // Render game elements
        renderGameElements(elementSize);
        
        // End profiling
        monitor->endProfile("gameRender");
    }
    
private:
    Rectangle calculateResponsiveViewport() {
        // Calculate viewport based on screen dimensions
        float width = appDimensGames.calculateDimension(800.0f, GameDimensionType::DYNAMIC);
        float height = appDimensGames.calculateDimension(600.0f, GameDimensionType::DYNAMIC);
        
        return Rectangle(0.0f, 0.0f, width, height);
    }
};
```

### 📊 Performance Monitoring

```cpp
class GamePerformance {
private:
    PerformanceMonitor* monitor;
    
public:
    void updateGame() {
        // Begin profiling
        monitor->beginProfile("gameUpdate", PerformanceCategory::GENERAL);
        
        // Game update logic
        updateGameLogic();
        updatePhysics();
        updateAI();
        
        // End profiling
        monitor->endProfile("gameUpdate");
        
        // Get performance metrics
        PerformanceMetrics metrics = monitor->getOverallMetrics();
        PerformanceReport report = monitor->generateReport();
        
        // Log performance if needed
        if (metrics.averageFrameTime > 16.67f) { // 60 FPS threshold
            logPerformanceWarning(metrics);
        }
    }
    
private:
    void logPerformanceWarning(const PerformanceMetrics& metrics) {
        // Log performance issues
        std::cout << "Performance Warning: Average frame time: " 
                  << metrics.averageFrameTime << "ms" << std::endl;
    }
};
```

---

## 🎮 Game Engine Integration

### 🎯 Cocos2d-x Integration

```cpp
#include "cocos2d.h"
#include "AppDimensGames.h"

class ResponsiveGameScene : public cocos2d::Scene {
private:
    AppDimensGames& appDimensGames;
    
public:
    static cocos2d::Scene* createScene() {
        return ResponsiveGameScene::create();
    }
    
    bool init() override {
        if (!Scene::init()) {
            return false;
        }
        
        // Initialize AppDimens Games
        appDimensGames = AppDimensGames::getInstance();
        
        // Set up responsive UI
        setupResponsiveUI();
        
        return true;
    }
    
private:
    void setupResponsiveUI() {
        // Calculate responsive dimensions
        float buttonSize = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
        float textSize = appDimensGames.calculateDimension(16.0f, GameDimensionType::FIXED);
        
        // Create responsive button
        auto button = cocos2d::ui::Button::create("button.png");
        button->setScale(buttonSize / 48.0f); // Scale based on calculated size
        button->setPosition(cocos2d::Vec2(400, 300));
        addChild(button);
        
        // Create responsive label
        auto label = cocos2d::Label::createWithTTF("Game Text", "fonts/arial.ttf", textSize);
        label->setPosition(cocos2d::Vec2(400, 400));
        addChild(label);
    }
};
```

### 🎮 Unity Integration (via JNI)

```csharp
public class AppDimensGamesUnity : MonoBehaviour {
    [DllImport("appdimens_games")]
    private static extern float nativeCalculateDimension(float baseValue, int type);
    
    [DllImport("appdimens_games")]
    private static extern void nativeInitialize(IntPtr env, IntPtr context);
    
    void Start() {
        // Initialize AppDimens Games
        IntPtr env = AndroidJNI.GetEnv();
        IntPtr context = AndroidJNI.FindClass("android/content/Context");
        nativeInitialize(env, context);
        
        // Calculate responsive dimensions
        float buttonSize = nativeCalculateDimension(48.0f, (int)GameDimensionType.FIXED);
        float playerSize = nativeCalculateDimension(64.0f, (int)GameDimensionType.GAME_WORLD);
        
        // Apply to Unity objects
        ApplyResponsiveDimensions(buttonSize, playerSize);
    }
    
    private void ApplyResponsiveDimensions(float buttonSize, float playerSize) {
        // Apply calculated dimensions to Unity UI elements
        Button[] buttons = FindObjectsOfType<Button>();
        foreach (Button button in buttons) {
            button.transform.localScale = Vector3.one * (buttonSize / 48.0f);
        }
        
        // Apply to game objects
        GameObject[] players = GameObject.FindGameObjectsWithTag("Player");
        foreach (GameObject player in players) {
            player.transform.localScale = Vector3.one * (playerSize / 64.0f);
        }
    }
}

public enum GameDimensionType {
    DYNAMIC = 0,
    FIXED = 1,
    GAME_WORLD = 2,
    UI_OVERLAY = 3
}
```

---

## 📊 Advanced Features

### 🎯 Viewport Management

```cpp
class GameViewport {
private:
    ViewportManager* viewportManager;
    
public:
    void setupViewport() {
        // Create different viewport modes
        ViewportMode mode = ViewportMode::FIT_ALL;
        CameraType cameraType = CameraType::ORTHOGRAPHIC;
        
        // Set up viewport
        viewportManager->setViewportMode(mode);
        viewportManager->setCameraType(cameraType);
        
        // Calculate responsive viewport
        Rectangle viewport = viewportManager->calculateViewport();
        viewportManager->setViewport(viewport);
    }
    
    void updateViewport() {
        // Update viewport based on screen changes
        if (viewportManager->needsUpdate()) {
            Rectangle newViewport = viewportManager->calculateViewport();
            viewportManager->setViewport(newViewport);
        }
    }
};
```

### 🎨 OpenGL ES Utilities

```cpp
class GameRenderer {
private:
    OpenGLUtils* glUtils;
    
public:
    void initializeShaders() {
        // Create shader program
        const char* vertexSource = R"(
            #version 300 es
            in vec2 position;
            in vec2 texCoord;
            out vec2 vTexCoord;
            uniform mat4 mvp;
            void main() {
                gl_Position = mvp * vec4(position, 0.0, 1.0);
                vTexCoord = texCoord;
            }
        )";
        
        const char* fragmentSource = R"(
            #version 300 es
            precision mediump float;
            in vec2 vTexCoord;
            out vec4 fragColor;
            uniform sampler2D texture;
            void main() {
                fragColor = texture2D(texture, vTexCoord);
            }
        )";
        
        ShaderProgram* program = glUtils->createShaderProgram("basic", vertexSource, fragmentSource);
        glUtils->useProgram(program);
    }
    
    void renderTexture() {
        // Create texture
        Texture* texture = glUtils->createTexture("texture.png");
        
        // Set up rendering state
        glUtils->setBlendMode(BlendMode::ALPHA);
        glUtils->setDepthTest(true);
        
        // Render texture
        glUtils->drawTexture(texture, 0, 0, 100, 100);
    }
};
```

### 📊 Performance Monitoring

```cpp
class GamePerformance {
private:
    PerformanceMonitor* monitor;
    
public:
    void beginFrame() {
        monitor->beginProfile("frame", PerformanceCategory::GENERAL);
    }
    
    void endFrame() {
        monitor->endProfile("frame");
        
        // Get performance metrics
        PerformanceMetrics metrics = monitor->getOverallMetrics();
        
        // Check for performance issues
        if (metrics.averageFrameTime > 16.67f) { // 60 FPS threshold
            handlePerformanceIssue(metrics);
        }
    }
    
    void handlePerformanceIssue(const PerformanceMetrics& metrics) {
        // Generate performance report
        PerformanceReport report = monitor->generateReport();
        
        // Log performance issues
        std::cout << "Performance Report:" << std::endl;
        std::cout << "Average FPS: " << 1000.0f / metrics.averageFrameTime << std::endl;
        std::cout << "Average Frame Time: " << metrics.averageFrameTime << "ms" << std::endl;
        std::cout << "Memory Usage: " << metrics.memoryUsage << "MB" << std::endl;
        
        // Apply performance optimizations
        applyPerformanceOptimizations(report);
    }
    
private:
    void applyPerformanceOptimizations(const PerformanceReport& report) {
        // Reduce quality if needed
        if (report.suggestions.contains("reduce_texture_quality")) {
            reduceTextureQuality();
        }
        
        // Reduce particle count
        if (report.suggestions.contains("reduce_particles")) {
            reduceParticleCount();
        }
        
        // Optimize shaders
        if (report.suggestions.contains("optimize_shaders")) {
            optimizeShaders();
        }
    }
};
```

---

## 📱 Device Support

### 📱 Supported Device Types

| Device Type | Description | Game Scaling Behavior |
|-------------|-------------|----------------------|
| **Phone** | Standard Android phones | Balanced game scaling |
| **Tablet** | Android tablets | Enhanced scaling for larger screens |
| **TV** | Android TV devices | Large UI elements for viewing distance |
| **Car** | Android Auto | Large touch targets and clear text |
| **Watch** | Wear OS devices | Compact game scaling |
| **VR** | VR headsets | Immersive game scaling |

### 📐 Screen Qualifiers

| Qualifier | Description | Use Case |
|-----------|-------------|----------|
| **SMALL_WIDTH** | Smallest screen dimension | Default, most restrictive |
| **WIDTH** | Screen width | Horizontal game layouts |
| **HEIGHT** | Screen height | Vertical game layouts |

---

## ⚡ Performance & Optimization

### 📊 Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time |
|---------|------------------|--------------|------------------|
| **Game Dimensions** | ~0.001ms | ~50KB | Cached per configuration |
| **OpenGL Utils** | ~0.002ms | ~100KB | On-demand |
| **Performance Monitor** | ~0.001ms | ~20KB | Real-time |

### 🚀 Optimization Tips

1. **Cache Dimensions**: Store frequently used game dimensions
2. **Use Appropriate Types**: Choose the right scaling type for each game element
3. **Monitor Performance**: Use built-in performance monitoring
4. **Optimize Shaders**: Use efficient shader programs
5. **Manage Memory**: Properly dispose of OpenGL resources

---

## 🧪 Testing & Debugging

### 🔧 Debug Tools

```cpp
// Debug current screen configuration
void debugScreenConfiguration() {
    AppDimensGames& appDimens = AppDimensGames::getInstance();
    ScreenConfig config = appDimens.getScreenConfig();
    
    std::cout << "Screen: " << config.width << " × " << config.height << std::endl;
    std::cout << "Device: " << config.deviceType << std::endl;
    std::cout << "Density: " << config.density << std::endl;
}

// Debug performance
void debugPerformance() {
    PerformanceMonitor* monitor = AppDimensGames::getInstance().getPerformanceMonitor();
    PerformanceMetrics metrics = monitor->getOverallMetrics();
    
    std::cout << "FPS: " << 1000.0f / metrics.averageFrameTime << std::endl;
    std::cout << "Frame Time: " << metrics.averageFrameTime << "ms" << std::endl;
    std::cout << "Memory: " << metrics.memoryUsage << "MB" << std::endl;
}
```

### 📋 Test Coverage

- ✅ Game dimension calculations
- ✅ C++/NDK integration
- ✅ OpenGL ES utilities
- ✅ Viewport management
- ✅ Performance monitoring
- ✅ Game engine integration
- ✅ Edge cases and error handling

---

## 📚 API Reference

### 🎯 Core Classes

| Class | Description | Key Methods |
|-------|-------------|-------------|
| **AppDimensGames** | Main entry point | `initialize()`, `calculateDimension()`, `calculateVector2D()` |
| **ViewportManager** | Viewport management | `setViewportMode()`, `calculateViewport()` |
| **OpenGLUtils** | OpenGL utilities | `createShaderProgram()`, `createTexture()` |
| **PerformanceMonitor** | Performance monitoring | `beginProfile()`, `endProfile()`, `getOverallMetrics()` |

### 🎮 Game Dimension Types

| Type | Description | Use Case |
|------|-------------|----------|
| **DYNAMIC** | Proportional scaling | Containers, fluid layouts |
| **FIXED** | Logarithmic scaling | UI elements, buttons |
| **GAME_WORLD** | World coordinates | Game objects, physics |
| **UI_OVERLAY** | Overlay elements | HUD, menus |

---

## 📚 Documentation & Resources

### 📖 Complete Documentation

- **[📘 Full Documentation](https://appdimens-project.web.app/)** - Comprehensive guides and API reference
- **[🎯 Core Documentation](../../DOCS/)** - Detailed technical documentation
- **[🎮 Games Module](IMPLEMENTATION_SUMMARY.md)** - Implementation details
- **[📱 Examples](../../app/src/main/kotlin/)** - Real-world usage examples

### 🔗 Quick Links

- **[🚀 Installation Guide](#installation)** - Get started in minutes
- **[📱 Examples](#usage-examples)** - Real-world usage examples
- **[🔧 API Reference](#api-reference)** - Complete API documentation
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Common questions and answers

---

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](../../CONTRIBUTING.md) for details.

### 🐛 Found a Bug?
- [Create an issue](https://github.com/bodenberg/appdimens/issues)
- Include device information and reproduction steps
- Attach screenshots if applicable

### 💡 Have an Idea?
- [Start a discussion](https://github.com/bodenberg/appdimens/discussions)
- Propose new features or improvements
- Share your use cases

---

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](../../LICENSE) file for details.

---

## 👨‍💻 Author

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

<div align="center">
    <p><strong>AppDimens Games - Responsive game development with native performance</strong></p>
</div>
