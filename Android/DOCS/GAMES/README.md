---
layout: default
title: "🎮 AppDimens Games - Android Game Development"
---

# 🎮 AppDimens Games - Android Game Development

> **C++/NDK Integration for High-Performance Game Development**

The AppDimens Games module provides native C++ implementation for high-performance game development on Android, with specialized dimension scaling, viewport management, and OpenGL ES integration.

## 🚀 Quick Start

### 1. Add Dependency

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-games:1.0.8")
}
```

### 2. Initialize Games Manager

```kotlin
class GameActivity : AppCompatActivity() {
    private lateinit var gamesManager: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize games manager
        gamesManager = AppDimensGames.getInstance()
        gamesManager.initialize(this)
        
        // Configure performance settings
        gamesManager.configurePerformance(
            GamePerformanceSettings.HIGH_PERFORMANCE
        )
    }
}
```

### 3. Use Game Dimensions

```kotlin
// Calculate game-specific dimensions
val buttonSize = gamesManager.calculateButtonSize(48f)
val playerSize = gamesManager.calculatePlayerSize(64f)
val enemySize = gamesManager.calculateEnemySize(32f)
val uiSize = gamesManager.calculateUISize(24f)
```


## 🎯 Game Dimension Types

### Dimension Categories

| Type | Use Case | Scaling Behavior |
|------|----------|------------------|
| **DYNAMIC** | Containers, fluid layouts | Proportional scaling |
| **FIXED** | UI elements, buttons | Logarithmic scaling |
| **GAME_WORLD** | Player, enemies, objects | World coordinate scaling |
| **UI_OVERLAY** | HUD, menus, text | UI-specific scaling |

### Usage Examples

```kotlin
// Game world dimensions
val playerSize = gamesManager.calculateDimension(64f, GameDimensionType.GAME_WORLD)
val enemySize = gamesManager.calculateDimension(32f, GameDimensionType.GAME_WORLD)

// UI overlay dimensions
val buttonSize = gamesManager.calculateDimension(48f, GameDimensionType.UI_OVERLAY)
val textSize = gamesManager.calculateDimension(16f, GameDimensionType.UI_OVERLAY)

// Container dimensions
val containerWidth = gamesManager.calculateDimension(320f, GameDimensionType.DYNAMIC)
val containerHeight = gamesManager.calculateDimension(240f, GameDimensionType.DYNAMIC)
```

## 🎮 Game Development Features

### Vector Operations

```kotlin
// 2D Vector operations
val vector1 = GameVector2D(100f, 50f)
val vector2 = GameVector2D(25f, 75f)

val sum = vector1 + vector2           // Addition
val difference = vector1 - vector2    // Subtraction
val scaled = vector1 * 2f             // Scalar multiplication
val length = vector1.length()         // Vector length
val normalized = vector1.normalized() // Normalized vector
```

### Rectangle Operations

```kotlin
// Rectangle operations
val rect1 = GameRectangle(0f, 0f, 100f, 50f)
val rect2 = GameRectangle(50f, 25f, 100f, 50f)

val center = rect1.center()                    // Center point
val contains = rect1.contains(GameVector2D(25f, 25f))  // Point containment
val intersection = rect1.intersection(rect2)   // Rectangle intersection
```

### Viewport Management

```kotlin
// Viewport scaling modes
enum class GameViewportMode {
    FIT_WIDTH,      // Fit to screen width
    FIT_HEIGHT,     // Fit to screen height
    FIT_ALL,        // Fit entire content
    STRETCH,        // Stretch to fill screen
    CROP            // Crop to maintain aspect ratio
}

// Calculate viewport dimensions
val viewportWidth = gamesManager.calculateDimension(320f, GameViewportMode.FIT_WIDTH)
val viewportHeight = gamesManager.calculateDimension(240f, GameViewportMode.FIT_HEIGHT)
```

## ⚡ Performance Optimization

### Performance Settings

```kotlin
// Default settings (balanced)
GamePerformanceSettings.DEFAULT

// High performance (120 FPS)
GamePerformanceSettings.HIGH_PERFORMANCE

// Low performance (30 FPS, simple games)
GamePerformanceSettings.LOW_PERFORMANCE

// Custom settings
val customSettings = GamePerformanceSettings(
    enableCaching = true,
    cacheSize = 2000,
    enableAsyncCalculation = true,
    maxCalculationTime = 8L  // 8ms for 120 FPS
)
```

### Caching Strategy

```kotlin
// Enable/disable caching
gamesManager.configurePerformance(
    GamePerformanceSettings(enableCaching = true)
)

// Clear cache when needed
gamesManager.clearCache()

// Cache is automatically managed based on screen configuration changes
```

## 🎨 OpenGL ES Integration

### OpenGL Utilities

The Games module includes OpenGL ES utilities for graphics rendering:

```cpp
// C++ OpenGL utilities (native code)
class OpenGLUtils {
public:
    static void setupViewport(int width, int height);
    static void calculateProjectionMatrix(float* matrix, float fov, float aspect, float near, float far);
    static void convertScreenToNDC(float screenX, float screenY, float& ndcX, float& ndcY);
};
```

### Coordinate Conversion

```kotlin
// Screen to NDC conversion
val screenPoint = GameVector2D(100f, 50f)
val ndcPoint = gamesManager.screenToNDC(screenPoint)

// NDC to screen conversion
val ndcPoint = GameVector2D(0.5f, 0.5f)
val screenPoint = gamesManager.ndcToScreen(ndcPoint)
```

## 📱 Screen Configuration

### Automatic Screen Detection

```kotlin
// Screen configuration is automatically detected
gamesManager.updateScreenConfiguration()

// Get current screen configuration
val screenConfig = gamesManager.getScreenConfig()
screenConfig?.let { config ->
    Log.i("Games", "Screen: ${config.width}x${config.height}")
    Log.i("Games", "Density: ${config.density}")
    Log.i("Games", "Orientation: ${config.orientation}")
    Log.i("Games", "Is Tablet: ${config.isTablet}")
}
```

### Screen Orientation

```kotlin
enum class GameScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
    AUTO
}

// Handle orientation changes
override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    gamesManager.updateScreenConfiguration()
}
```

## 🔧 Advanced Usage

### Custom Game Activity

```kotlin
class MyGameActivity : GameActivity() {
    private lateinit var gamesManager: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize games manager
        gamesManager = AppDimensGames.getInstance()
        gamesManager.initialize(this)
        
        // Configure for your game
        gamesManager.configurePerformance(
            GamePerformanceSettings.HIGH_PERFORMANCE
        )
    }
    
    override fun onResume() {
        super.onResume()
        // Update screen configuration
        gamesManager.updateScreenConfiguration()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Clean up resources
        gamesManager.shutdown()
    }
}
```

### Integration with Game Engines

```kotlin
// Unity integration example
class UnityGameManager {
    private val gamesManager = AppDimensGames.getInstance()
    
    fun initialize(context: Context) {
        gamesManager.initialize(context)
        gamesManager.configurePerformance(
            GamePerformanceSettings.HIGH_PERFORMANCE
        )
    }
    
    fun calculateUIScale(baseSize: Float): Float {
        return gamesManager.calculateUISize(baseSize)
    }
    
    fun calculateWorldScale(baseSize: Float): Float {
        return gamesManager.calculatePlayerSize(baseSize)
    }
}
```

## 🐛 Troubleshooting

### Common Issues

1. **Native Library Loading Failed**
   ```
   Solution: Ensure your app has the correct architecture support (arm64-v8a, armeabi-v7a, x86, x86_64)
   ```

2. **Performance Issues**
   ```
   Solution: Configure appropriate GamePerformanceSettings for your game's requirements
   ```

3. **Screen Configuration Not Updating**
   ```
   Solution: Call updateScreenConfiguration() in onConfigurationChanged()
   ```

### Debug Information

```kotlin
// Enable debug logging
Log.i("AppDimensGames", "Games manager initialized: ${gamesManager.isInitialized()}")
Log.i("AppDimensGames", "Screen config: ${gamesManager.getScreenConfig()}")
Log.i("AppDimensGames", "Performance settings: ${gamesManager.getPerformanceSettings()}")
```

## 📚 API Reference

### Main Classes

- **[AppDimensGames](MARKDOWN/appdimens_games/com.appdimens.games/-app-dimens-games/)** - Main games manager
- **[GameDimensionType](MARKDOWN/appdimens_games/com.appdimens.games/-game-dimension-type/)** - Dimension types
- **[GameVector2D](MARKDOWN/appdimens_games/com.appdimens.games/-game-vector2-d/)** - 2D vector operations
- **[GameRectangle](MARKDOWN/appdimens_games/com.appdimens.games/-game-rectangle/)** - Rectangle operations
- **[GameScreenConfig](MARKDOWN/appdimens_games/com.appdimens.games/-game-screen-config/)** - Screen configuration
- **[GamePerformanceSettings](MARKDOWN/appdimens_games/com.appdimens.games/-game-performance-settings/)** - Performance settings

### Native C++ API

- **[AppDimensGames.h](../../appdimens_games/src/main/cpp/include/AppDimensGames.h)** - Main native interface
- **[GameDimensions.h](../../appdimens_games/src/main/cpp/include/GameDimensions.h)** - Dimension calculations
- **[OpenGLUtils.h](../../appdimens_games/src/main/cpp/include/OpenGLUtils.h)** - OpenGL utilities

## 🔗 Related Documentation

- **[Main Games Documentation](../README.md)** - Complete API reference
- **[Core Library](../LIBRARY/)** - Base types and interfaces
- **[Dynamic Module](../DYNAMIC/)** - Dynamic/Fixed scaling
- **[Examples](../../app/src/main/kotlin/)** - Usage examples

---

**Last Updated**: 2025-01-27  
**Version**: 1.0.8  
**Native Implementation**: C++/NDK with OpenGL ES support
