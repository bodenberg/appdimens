# 📚 AppDimens Android Documentation

> **Complete API Documentation and Implementation Guide**

This directory contains comprehensive documentation for all AppDimens Android modules, generated automatically from source code comments and enhanced with detailed examples.

## 📖 Documentation Structure

### 🏗️ Module Documentation

| Module | Description | Documentation |
|--------|-------------|---------------|
| **[ALL](ALL/)** | All-in-one package documentation | Complete API reference for the unified library |
| **[DYNAMIC](DYNAMIC/)** | Dynamic and Fixed scaling models | Core dimension calculation and scaling logic |
| **[GAMES](GAMES/)** | Game development module | C++/NDK integration and game-specific features |
| **[LIBRARY](LIBRARY/)** | Core library types and interfaces | Base enums, qualifiers, and adjustment factors |
| **[SDPS](SDPS/)** | SDP scaling implementation | Pre-calculated dimension resources |
| **[SSPS](SSPS/)** | SSP scaling implementation | Pre-calculated text size resources |

### 📋 Documentation Formats

Each module includes documentation in multiple formats:

- **📘 HTML**: Interactive web documentation with search and navigation
- **📝 Markdown**: GitHub-compatible documentation with examples
- **📖 JavaDoc**: Traditional JavaDoc format for IDE integration

### 🔍 Quick Navigation

#### Core Functionality
- **[Dynamic Scaling](DYNAMIC/MARKDOWN/appdimens_dynamic/)** - Proportional dimension scaling
- **[Fixed Scaling](DYNAMIC/MARKDOWN/appdimens_dynamic/)** - Logarithmic dimension adjustment
- **[Physical Units](DYNAMIC/MARKDOWN/appdimens_dynamic/)** - Real-world measurement conversion

#### Game Development
- **[AppDimensGames](GAMES/MARKDOWN/appdimens_games/)** - Main game development API
- **[Game Types](GAMES/MARKDOWN/appdimens_games/)** - Game dimension types and utilities
- **[Performance](GAMES/MARKDOWN/appdimens_games/)** - Performance monitoring and optimization

#### Pre-calculated Resources
- **[SDP Resources](SDPS/MARKDOWN/appdimens_sdps/)** - Scalable dimension pixels
- **[SSP Resources](SSPS/MARKDOWN/appdimens_ssps/)** - Scalable text pixels

## 🚀 Getting Started

### 1. Choose Your Module

```kotlin
dependencies {
    // Core functionality (Dynamic + Fixed scaling)
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.8")
    
    // Pre-calculated resources
    implementation("io.github.bodenberg:appdimens-sdps:1.0.8")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.8")
    
    // Game development
    implementation("io.github.bodenberg:appdimens-games:1.0.8")
    
    // All-in-one package
    implementation("io.github.bodenberg:appdimens-all:1.0.8")
}
```

### 2. Basic Usage

```kotlin
// Dynamic scaling
val dynamicWidth = 300.dydp.toPx(resources)

// Fixed scaling with aspect ratio
val fixedHeight = 200.fxdp
    .aspectRatio(enable = true)
    .toPx(resources)

// Game development
val gamesManager = AppDimensGames.getInstance()
gamesManager.initialize(context)
val buttonSize = gamesManager.calculateButtonSize(48f)
```

### 3. Advanced Configuration

```kotlin
// Screen qualifiers
val buttonSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .type(ScreenType.HIGHEST)
    .cache(true)
```

## 📊 Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time |
|---------|------------------|--------------|------------------|
| **Dynamic/Fixed** | ~0.001ms | ~50KB | Cached per configuration |
| **SDP/SSP** | Zero | ~2MB (resources) | Pre-calculated |
| **Games (Native)** | ~0.0005ms | ~100KB | Native C++ implementation |

## 🔧 API Reference

### Core Classes

- **[AppDimensDynamic](DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.code/-app-dimens-dynamic/)** - Dynamic dimension scaling
- **[AppDimensFixed](DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.code/-app-dimens-fixed/)** - Fixed dimension scaling
- **[AppDimensGames](GAMES/MARKDOWN/appdimens_games/com.appdimens.games/-app-dimens-games/)** - Game development utilities
- **[ScreenAdjustmentFactors](LIBRARY/MARKDOWN/appdimens_library/com.appdimens.library/-screen-adjustment-factors/)** - Screen calculation factors

### Extension Functions

- **[Compose Extensions](DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.compose/)** - Jetpack Compose integration
- **[SDP Extensions](SDPS/MARKDOWN/appdimens_sdps/com.appdimens.sdps.compose/)** - SDP scaling functions
- **[SSP Extensions](SSPS/MARKDOWN/appdimens_ssps/com.appdimens.ssps.compose/)** - SSP scaling functions

## 🎮 Game Development

### Android Games (C++/NDK)

The Games module provides native performance for game development:

- **Native Implementation**: C++ code for high-performance calculations
- **Game Dimension Types**: DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY
- **Vector Operations**: GameVector2D with mathematical operations
- **Viewport Management**: Multiple scaling modes for different scenarios
- **OpenGL Integration**: Utilities for OpenGL ES rendering

### Performance Settings

```kotlin
// Configure performance for games
gamesManager.configurePerformance(
    GamePerformanceSettings.HIGH_PERFORMANCE
)

// Available settings
GamePerformanceSettings.DEFAULT        // Balanced performance
GamePerformanceSettings.HIGH_PERFORMANCE  // 120 FPS optimization
GamePerformanceSettings.LOW_PERFORMANCE   // 30 FPS for simple games
```

## 📱 Platform Support

| Feature | Android API | Compose | Views | Games |
|---------|-------------|---------|-------|-------|
| **Dynamic/Fixed** | 23+ | ✅ | ✅ | ✅ |
| **SDP/SSP** | 23+ | ✅ | ✅ | ❌ |
| **Physical Units** | 23+ | ✅ | ✅ | ✅ |
| **Game Development** | 23+ | ✅ | ✅ | ✅ (C++/NDK) |

## 🔗 Related Documentation

- **[Main README](../../README.md)** - Project overview and quick start
- **[iOS Documentation](../../iOS/README.md)** - iOS implementation guide
- **[Examples](../../Android/app/src/main/kotlin/)** - Real-world usage examples
- **[Multilingual Docs](../../LANG/)** - Documentation in multiple languages

## 🤝 Contributing

Found an issue with the documentation? Please:

1. Check the [source code documentation](../../Android/) for the latest API
2. [Create an issue](https://github.com/bodenberg/appdimens/issues) with documentation improvements
3. [Submit a pull request](https://github.com/bodenberg/appdimens/pulls) with fixes

---

**Last Updated**: 2025-01-27  
**Version**: 1.0.8  
**Generated**: Automatic from source code comments
