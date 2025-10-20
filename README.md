<div align="center">
    <img src="IMAGES/image_sample_devices.png" alt="AppDimens - Responsive Design Across All Devices" height="300"/>
    <h1>📐 AppDimens</h1>
    <p><strong>Smart and Responsive Dimensioning for Android & iOS</strong></p>
    <p>Mathematically responsive scaling that ensures your UI design adapts perfectly to any screen size or aspect ratio — from phones to TVs, cars, and wearables.</p>

[![Version](https://img.shields.io/badge/version-1.0.7-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%20%7C%20iOS-orange.svg)](https://github.com/bodenberg/appdimens)
[![Documentation](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

> Languages: [Português (BR)](LANG/pt-BR/README.md) | [Español](LANG/es/README.md) | [हिन्दी](LANG/hi/README.md) | [Русский](LANG/ru/README.md) | [中文](LANG/zh/README.md) | [日本語](LANG/ja/README.md)

---

## 🎯 What is AppDimens?

**AppDimens** is a comprehensive dimensioning system that replaces fixed pixel values with intelligently scaled dimensions based on actual screen characteristics. While traditional DP/Points are constant, AppDimens treats them as base values that scale predictably across different screen sizes, densities, and aspect ratios.

### 🎨 Key Benefits

- **🎯 Visual Consistency**: Maintain perfect proportions across all device types
- **📱 Universal Compatibility**: Works seamlessly on phones, tablets, TVs, cars, and wearables
- **⚡ Performance Optimized**: Minimal runtime overhead with cached calculations
- **🔧 Easy Integration**: Simple API that works with Jetpack Compose, XML Views, SwiftUI, and UIKit
- **📐 Mathematical Precision**: Two scaling models (Fixed & Dynamic) for different design needs

---

## 🚀 Quick Start

### Android

```kotlin
dependencies {
    // Core library (Dynamic + Fixed scaling)
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.7")
    
    // Optional: SDP & SSP scaling
    implementation("io.github.bodenberg:appdimens-sdps:1.0.7")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.7")
    
    // All-in-one package (does not include games module)
    implementation("io.github.bodenberg:appdimens-all:1.0.7")
    
    // Game development with C++/NDK support (separate dependency)
    implementation("io.github.bodenberg:appdimens-games:1.0.7")
}
```

### New Features in v1.0.6

- **🎮 Games Module**: Complete C++/NDK support for game development with Metal (iOS) and OpenGL ES (Android)
- **📏 Physical Units**: Convert real-world measurements (mm, cm, inches) to screen pixels
- **🌐 Environment System**: Advanced environment-based responsive design with protocol-based APIs
- **📊 Item Count Calculator**: Calculate optimal grid layouts and item counts
- **⚡ Performance Settings**: Configurable caching and performance optimization
- **🔧 Advanced Protocols**: Protocol-based API design for better extensibility

### iOS

```ruby
# Podfile
pod 'AppDimens'
```

```swift
// Swift Package Manager
.package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.7")
```

---

## 🧠 Core Dimension Models

| Model | Philosophy | Ideal Use Case | Supported Platforms | Implementation |
|-------|------------|----------------|-------------------|----------------|
| **Fixed (FX)** | Logarithmic scaling (refined) | Buttons, paddings, margins, icons | Android + iOS | Mathematical aspect ratio adjustment |
| **Dynamic (DY)** | Proportional scaling (aggressive) | Containers, grids, fluid fonts | Android + iOS | Screen-based proportional scaling |
| **SDP / SSP** | Pre-calculated resources | Direct `@dimen` usage in XML | Android | 426+ pre-generated dimension files |
| **Physical Units** | mm/cm/inch → Dp/Sp/Px/Points | Wearables, printing, precision layouts | Android + iOS | Real-world measurement conversion |
| **Game Dimensions** | Specialized scaling for games | Game UI, viewports, Metal/OpenGL | Android + iOS | C++/NDK + Metal native implementation |

---

## 🎮 Game Development Features

### Android Games (C++/NDK)
- **Native Performance**: C++ implementation for high-performance calculations
- **Game Dimension Types**: DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY
- **Vector Operations**: GameVector2D with mathematical operations
- **Viewport Management**: Multiple scaling modes for different game scenarios
- **OpenGL Integration**: Utilities for OpenGL ES rendering

### iOS Games (Metal)
- **Metal Integration**: Native Metal and MetalKit support
- **Viewport Scaling**: Uniform, horizontal, vertical, aspect-ratio, viewport modes
- **Coordinate Conversion**: Screen ↔ NDC coordinate transformations
- **Performance Optimized**: SIMD extensions for vector operations
- **SwiftUI Integration**: Game-specific SwiftUI extensions

---

## 📱 Platform Examples

### 🤖 Android - Jetpack Compose

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.dydp)           // Dynamic width
            .height(200.fxdp)          // Fixed height
            .padding(16.fxdp)          // Fixed padding
    ) {
        Column(
            modifier = Modifier.padding(16.fxdp)
        ) {
            Text(
                text = "Responsive Title",
                fontSize = 18.fxsp     // Fixed font size
            )
            Text(
                text = "This card adapts to any screen size",
                fontSize = 14.dysp     // Dynamic font size
            )
        }
    }
}

// Game Development Example
@Composable
fun GameUI() {
    val appDimensGames = AppDimensGames.getInstance()
    
    // Initialize for game development
    LaunchedEffect(Unit) {
        appDimensGames.initialize(context)
    }
    
    // Game-specific dimensions
    val buttonSize = appDimensGames.calculateButtonSize(48f)
    val playerSize = appDimensGames.calculatePlayerSize(64f)
    val uiOverlaySize = appDimensGames.calculateUISize(24f)
}
```

### 🍎 iOS - SwiftUI

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Responsive Title")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("This card adapts to any screen size")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // Dynamic width
        .fxFrame(height: 200)          // Fixed height
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}

// Game Development Example
struct GameView: View {
    var body: some View {
        VStack {
            // Game-specific dimensions
            Text("Score: 1000")
                .font(.system(size: gameUniform(24)))  // Uniform scaling
            
            // Metal viewport dimensions
            MetalGameView()
                .frame(
                    width: gameAspectRatio(320),
                    height: gameAspectRatio(240)
                )
        }
        .withAppDimens()  // Enable AppDimens environment
    }
}
```

### 📄 Android - XML Views

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="@dimen/_16sdp">
    
    <TextView
        android:layout_width="@dimen/_300sdp"
        android:layout_height="wrap_content"
        android:textSize="@dimen/_18ssp"
        android:text="Responsive Text" />
        
    <Button
        android:layout_width="@dimen/_120sdp"
        android:layout_height="@dimen/_48sdp"
        android:text="Action" />
</LinearLayout>
```

---

## 🎨 Advanced Features

### 🔄 Conditional Scaling

```kotlin
// Android
val buttonSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

```swift
// iOS
let buttonSize = AppDimens.fixed(80)
    .screen(.watch, 40)           // 40pt for Apple Watch
    .screen(.tablet, 120)         // 120pt for iPad
    .aspectRatio(enable: true)    // Enable aspect ratio adjustment
    .toPoints()
```

### 📏 Physical Units

```kotlin
// Android
val marginPx = AppDimensPhysicalUnits.toMm(5f, resources)
view.setPadding(marginPx.toInt(), 0, 0, 0)
```

```swift
// iOS
Rectangle()
    .frame(width: 2.cm, height: 1.cm)  // Physical units
```

### 🧮 Layout Utilities

```kotlin
// Android - Calculate optimal grid columns
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

### 🔧 Advanced Configuration

#### Screen Qualifiers & Customization

```kotlin
// Android - Advanced screen qualifiers
val buttonSize = 80.dynamicDp() // or fixedDp
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)  // Priority 1: Intersection
    .screen(UiModeType.CAR, 120.dp)                                // Priority 2: UI Mode
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)                     // Priority 3: Dp Qualifier
    .type(ScreenType.HIGHEST)                                       // Use largest dimension
    .multiViewAdjustment(true)                                      // Ignore multi-window
    .cache(true)                                                    // Enable caching
```

#### Performance Optimization

```kotlin
// Android - Performance settings
AppDimens.globalCacheEnabled = true  // Global cache control

val dimension = 100.dynamicDp()
    .cache(true)                     // Instance-level cache
    .toPx(resources)                 // Cached calculation
```

#### Game Development Integration

```kotlin
// Android - Game development setup
val gamesManager = AppDimensGames.getInstance()
gamesManager.initialize(context)

// Configure performance settings
gamesManager.configurePerformance(
    GamePerformanceSettings.HIGH_PERFORMANCE
)

// Calculate game-specific dimensions
val buttonSize = gamesManager.calculateButtonSize(48f)
val playerSize = gamesManager.calculatePlayerSize(64f)
val uiSize = gamesManager.calculateUISize(24f)
```

---

## 📊 Performance & Compatibility

### ⚡ Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cached per configuration |
| **SDP/SSP** | Zero | ~2MB (resources) | Pre-calculated |
| **Physical Units** | ~0.002ms | ~10KB | On-demand |

### 📱 Platform Support

| Platform | Min Version | UI Frameworks | Special Features | Native Support |
|----------|-------------|---------------|------------------|----------------|
| **Android** | API 23+ | Compose, Views, Data Binding | SDP/SSP, Physical Units, C++/NDK | OpenGL ES, CMake |
| **iOS** | 13.0+ | SwiftUI, UIKit | Metal Integration, SIMD | Metal, MetalKit |
| **macOS** | 10.15+ | SwiftUI, AppKit | Cross-platform consistency | Native extensions |
| **tvOS** | 13.0+ | SwiftUI, UIKit | TV-optimized scaling | Remote control UI |
| **watchOS** | 6.0+ | SwiftUI | Watch-specific dimensions | HealthKit integration |

---

## 📚 Documentation & Resources

### 📖 Complete Documentation

- **[📘 Full Documentation](https://appdimens-project.web.app/)** - Comprehensive guides and API reference
- **[🤖 Android Guide](Android/README.md)** - Android-specific documentation
- **[🍎 iOS Guide](iOS/README.md)** - iOS-specific documentation
- **[🎮 Games Module](Android/appdimens_games/README.md)** - Game development with C++/NDK

### 🎯 Quick Links

- **[🚀 Installation Guide](Android/README.md#installation)** - Get started in minutes
- **[📱 Examples](Android/app/src/main/kotlin/)** - Real-world usage examples
- **[🔧 API Reference](Android/DOCS/)** - Complete API documentation
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Common questions and answers

---

## 🎯 Use Cases

### 📱 Mobile Apps
Perfect for apps that need to work across different phone sizes and orientations.

### 📺 TV & Car Apps
Ideal for Android TV and Android Auto applications with varying screen sizes.

### ⌚ Wearable Apps
Essential for Wear OS apps that need to adapt to different watch sizes.

### 🎮 Game Development
Specialized module for game development with C++/NDK support and OpenGL integration.

### 🏢 Enterprise Apps
Great for business applications that need to work on tablets, phones, and desktop.

---

## 🏗️ Architecture Overview

### Android Library Structure

| Module | Purpose | Dependencies | Key Features |
|--------|---------|-------------|--------------|
| **appdimens_library** | Core types and interfaces | None | Base enums, qualifiers, adjustment factors |
| **appdimens_dynamic** | Dynamic/Fixed scaling | appdimens_library | DY/FX models, Compose extensions, caching |
| **appdimens_sdps** | SDP scaling | appdimens_library | 426+ pre-calculated @dimen resources |
| **appdimens_ssps** | SSP scaling | appdimens_library | 216+ pre-calculated @dimen resources |
| **appdimens_games** | Game development | appdimens_library, appdimens_dynamic | C++/NDK, OpenGL utilities, performance monitoring |
| **appdimens_all** | All-in-one package | All modules | Complete functionality in single dependency |

### iOS Library Structure

| Module | Purpose | Dependencies | Key Features |
|--------|---------|-------------|--------------|
| **AppDimens** | Core functionality | Foundation, UIKit | DY/FX models, caching, qualifiers |
| **AppDimensUI** | UI extensions | AppDimens | SwiftUI extensions, UIKit integration |
| **AppDimensGames** | Game development | AppDimens, Metal | Metal integration, viewport management, SIMD |

### Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time | Cache Strategy |
|---------|------------------|--------------|------------------|----------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cached per configuration | Automatic dependency tracking |
| **SDP/SSP** | Zero | ~2MB (resources) | Pre-calculated | Resource-based |
| **Physical Units** | ~0.002ms | ~10KB | On-demand | Lazy initialization |
| **Games (Native)** | ~0.0005ms | ~100KB | Cached with LRU | Native C++ implementation |

---

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details.

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

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 Show Your Support

If AppDimens has helped your project, please consider:

- ⭐ **Starring** this repository
- 🐦 **Sharing** on social media
- 📝 **Writing** a review or blog post
- 🤝 **Contributing** code or documentation

---

<div align="center">
    <p><strong>Made with ❤️ for the mobile development community</strong></p>
    <p>AppDimens - Where responsive design meets mathematical precision</p>
</div>
