---
layout: default
---

<div align="center">
    <img src="../IMAGES/image_sample_devices.png" alt="AppDimens Android - Responsive Design" height="250"/>
    <h1>📐 AppDimens Android</h1>
    <p><strong>Smart and Responsive Dimensioning for Android</strong></p>
    <p>Mathematically responsive scaling that ensures your UI design adapts perfectly to any screen size or aspect ratio — from phones to TVs, cars, and wearables.</p>

[![Version](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
[![Documentation](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

> **Languages:** English | [Português (BR)](../LANG/pt-BR/Android/README.md) | [Español](../LANG/es/Android/README.md)

---

## 🎯 What is AppDimens Android?

**AppDimens Android** is a comprehensive dimensioning system that replaces fixed DP values with intelligently scaled dimensions based on actual screen characteristics. While Android's default DP (1 DP = 1/160 inch) is constant, AppDimens treats it as a base value that scales predictably across different screen sizes, densities, and aspect ratios.

### 🎨 Key Benefits

- **🎯 Visual Consistency**: Maintain perfect proportions across all Android device types
- **📱 Universal Compatibility**: Works seamlessly on phones, tablets, TVs, cars, and wearables
- **⚡ Performance Optimized**: Minimal runtime overhead with cached calculations
- **🔧 Easy Integration**: Simple API that works with Jetpack Compose, XML Views, and Data Binding
- **📐 Mathematical Precision**: Two scaling models - **Fixed (RECOMMENDED)** for most cases & Dynamic for specific needs
- **🎮 Game Development**: Specialized C++/NDK module for high-performance game development
- **🚀 Native Performance**: C++ implementation for game-specific calculations and OpenGL integration

---

## 🎮 Game Development Features

### AppDimens Games Module
The **AppDimens Games** module provides specialized functionality for Android game development with native C++/NDK support:

#### Key Features:
- **Native Performance**: C++ implementation for high-performance calculations
- **Game Dimension Types**: 
  - `DYNAMIC`: Proportional scaling for containers
  - `FIXED`: Logarithmic scaling for UI elements
  - `GAME_WORLD`: Consistent game world coordinates
  - `UI_OVERLAY`: UI overlay coordinates
- **Vector Operations**: `GameVector2D` with mathematical operations (addition, subtraction, multiplication, normalization)
- **Rectangle Utilities**: `GameRectangle` for bounds and viewport management
- **OpenGL Integration**: Utilities for OpenGL ES rendering
- **Performance Monitoring**: Real-time performance metrics and optimization

#### Usage Example:
```kotlin
val appDimensGames = AppDimensGames.getInstance()
appDimensGames.initialize(context)

// Game-specific dimensions
val buttonSize = appDimensGames.calculateButtonSize(48f)
val playerSize = appDimensGames.calculatePlayerSize(64f)
val uiOverlaySize = appDimensGames.calculateUISize(24f)

// Vector operations
val position = GameVector2D(100f, 200f)
val scaledPosition = appDimensGames.calculateVector2D(position, GameDimensionType.GAME_WORLD)
```

---

## 🚀 Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    // Core library (Dynamic + Fixed scaling)
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.8")
    
    // Optional: SDP & SSP scaling
    implementation("io.github.bodenberg:appdimens-sdps:1.0.8")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.8")
    
    // All-in-one package (does not include games module)
    implementation("io.github.bodenberg:appdimens-all:1.0.8")
    
    // Game development with C++/NDK support (separate dependency)
    implementation("io.github.bodenberg:appdimens-games:1.0.8")
}
```

### Gradle (Groovy)

```groovy
dependencies {
    implementation 'io.github.bodenberg:appdimens-dynamic:1.0.8'
    implementation 'io.github.bodenberg:appdimens-sdps:1.0.8'
    implementation 'io.github.bodenberg:appdimens-ssps:1.0.8'
    implementation 'io.github.bodenberg:appdimens-all:1.0.8'
    implementation 'io.github.bodenberg:appdimens-games:1.0.8'
}
```

### Repository Configuration

```kotlin
repositories {
    mavenCentral()
    // or
    maven { url = uri("https://jitpack.io") }
}
```

---

## 📋 Minimum Requirements

### Required Versions

| Component | Minimum Version | Recommended |
|-----------|----------------|-------------|
| **Kotlin** | 2.2.20 | 2.2.20 |
| **Android Gradle Plugin** | 8.13.0 | 8.13.0 |
| **Gradle** | 8.5 | 8.5 |
| **compileSdk** | 36 | 36 |
| **minSdk** | 21 (Android 5.0) | 23 (Android 6.0) |
| **targetSdk** | 36 | 36 |
| **Java** | 17 | 17 |
| **Jetpack Compose BOM** | 2025.01.00 | 2025.01.00 |

### Build Configuration Example

```kotlin
// build.gradle.kts (Project level)
plugins {
    id("com.android.application") version "8.13.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.20" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.20" apply false
}
```

```kotlin
// build.gradle.kts (Module level)
android {
    namespace = "com.example.app"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.app"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    // Jetpack Compose BOM
    implementation(platform("androidx.compose:compose-bom:2025.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    
    // AppDimens
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.8")
}
```

### Special Requirements

#### For Games Module (C++/NDK)

| Component | Minimum Version |
|-----------|----------------|
| **NDK** | 21.0.6113669 |
| **CMake** | 3.18.1 |
| **C++ Standard** | C++17 |

```kotlin
android {
    ndkVersion = "26.1.10909125"
    
    defaultConfig {
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++17"
            }
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

#### For 16KB Page Size Compatibility

AppDimens is **fully compatible** with Android's 16KB page size. No special configuration needed.

```kotlin
android {
    defaultConfig {
        // Optional: Enable 16KB page size testing
        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
}
```

---

## 🧠 Core Dimension Models

| Model | Philosophy | Ideal Use Case | Supported In |
|-------|------------|----------------|--------------|
| **Fixed (FX)** | Logarithmic scaling (refined) | Buttons, paddings, margins, icons | Compose + Views + Data Binding |
| **Dynamic (DY)** | Proportional scaling (aggressive) | Containers, grids, fluid fonts | Compose + Views + Data Binding |
| **SDP / SSP** | Pre-calculated resources | Direct `@dimen` usage in XML | Compose + Views (XML) |
| **Physical Units** | mm/cm/inch → Dp/Sp/Px | Wearables, printing, precision layouts | Compose + Views |

---

## 🎨 Usage Examples

### 🧩 Jetpack Compose

#### Fixed and Dynamic Scaling

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.dydp)           // Dynamic width - proportional to screen
            .height(200.fxdp)          // Fixed height - refined scaling
            .padding(16.fxdp)          // Fixed padding - consistent feel
    ) {
        Column(
            modifier = Modifier.padding(16.fxdp)
        ) {
            Text(
                text = "Responsive Title",
                fontSize = 18.fxsp     // Fixed font size - comfortable reading
            )
            Text(
                text = "This card adapts to any screen size",
                fontSize = 14.dysp     // Dynamic font size - proportional
            )
        }
    }
}
```

#### SDP and SSP in Compose

```kotlin
@Composable
fun SDPExample() {
    Box(
        modifier = Modifier
            .padding(16.sdp)           // SDP padding
            .size(100.sdp)             // SDP size
    ) {
        Text(
            text = "Responsive Text",
            fontSize = 18.ssp          // SSP font size
        )
    }
}
```

### 📄 XML Views and Data Binding

#### Dynamic Scaling with Data Binding

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    app:dynamicWidthDp="@{100f}"
    app:dynamicHeightDp="@{56f}"
    app:dynamicTextSizeDp="@{20f}">
    
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Dynamic Text Size"
        app:dynamicTextSizeDp="@{18f}" />
</LinearLayout>
```

#### SDP and SSP in XML

```xml
<TextView
    android:layout_width="@dimen/_100sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp"
    android:text="Responsive Text Example"/>
```

### 💻 Kotlin/Java Code

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Fixed scaling - subtle adjustment
        val fixedWidthPx = AppDimens.fixedPx(
            value = 200f,
            screenType = ScreenType.LOWEST,
            resources = resources
        ).toInt()
        
        // Dynamic scaling - proportional adjustment
        val dynamicTextSizeSp = AppDimens.dynamicSp(
            value = 18f,
            screenType = ScreenType.LOWEST,
            resources = resources
        )
        
        // Apply to views
        myView.layoutParams.width = fixedWidthPx
        myTextView.textSize = dynamicTextSizeSp
    }
}
```

---

## 🔧 Advanced Features

### 🔄 Conditional Scaling

```kotlin
@Composable
fun ConditionalScaling() {
    val boxSize = 80.scaledDp()
        .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
        .screen(UiModeType.CAR, 120.dp)
        .screen(DpQualifier.SMALL_WIDTH, 720, 150)
    
    Box(
        modifier = Modifier
            .size(boxSize.sdp)
            .background(Color.Blue)
    )
}
```

### 📏 Physical Units

```kotlin
@Composable
fun PhysicalUnits() {
    // 5 millimeters converted to Dp
    val marginDp = 5.0f.toDp(UnitType.MM)
    
    Box(
        modifier = Modifier
            .padding(marginDp)
            .size(1.0f.toDp(UnitType.INCH)) // 1 inch in Dp
    )
}
```

### 🧮 Layout Utilities

```kotlin
@Composable
fun ResponsiveGrid() {
    var spanCount by remember { mutableStateOf(3) }
    
    // Calculate optimal number of columns
    AppDimens.CalculateAvailableItemCount(
        itemSize = 100.dp,
        itemPadding = 4.dp,
        direction = DpQualifier.WIDTH,
        modifier = Modifier.fillMaxWidth(),
        onResult = { count ->
            if (count > 0) spanCount = count
        }
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(spanCount),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(20) { index ->
            // Your grid items
        }
    }
}
```

---

## 📊 Module Overview

### 🎯 Core Modules

| Module | Description | Use Case |
|--------|-------------|----------|
| **appdimens-dynamic** | Core library with Fixed & Dynamic scaling | Essential for all responsive apps |
| **appdimens-sdps** | SDP scaling with conditional logic | XML-based responsive design |
| **appdimens-ssps** | SSP scaling with conditional logic | Responsive text sizing |
| **appdimens-all** | Complete package with all modules (except games) | One-stop solution for standard apps |
| **appdimens-games** | Game development with C++/NDK | Android game development |

### 🎮 Games Module Features

- **C++/NDK Integration**: Native performance for game engines
- **OpenGL ES Support**: Utilities for OpenGL ES 2.0/3.0 rendering
- **Viewport Management**: Advanced camera and viewport systems
- **Performance Monitoring**: Real-time performance metrics
- **Game-Specific Scaling**: Preset calculations for game elements

#### 🎮 Games Module Usage

The Games module provides specialized functionality for Android game development with C++/NDK support. It's a separate dependency that's not included in the `appdimens-all` package.

**Basic Integration:**

```kotlin
class GameActivity : Activity() {
    private lateinit var appDimensGames: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize AppDimens Games
        appDimensGames = AppDimensGames.getInstance()
        appDimensGames.initialize(this)
        
        // Calculate responsive dimensions for game elements
        val buttonSize = appDimensGames.calculateButtonSize(48.0f)
        val textSize = appDimensGames.calculateTextSize(16.0f)
        val playerSize = appDimensGames.calculatePlayerSize(64.0f)
        val enemySize = appDimensGames.calculateEnemySize(32.0f)
    }
}
```

**C++ Integration:**

```cpp
#include "AppDimensGames.h"

class GameEngine {
private:
    AppDimensGames& appDimensGames;
    
public:
    GameEngine(JNIEnv* env, jobject context) {
        appDimensGames = AppDimensGames::getInstance();
        appDimensGames.initialize(env, context);
    }
    
    void updateGame() {
        // Calculate dimensions for different game elements
        float buttonSize = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
        float playerSize = appDimensGames.calculateDimension(64.0f, GameDimensionType::GAME_WORLD);
        
        // Work with vectors and rectangles
        Vector2D baseVector(100.0f, 50.0f);
        Vector2D scaledVector = appDimensGames.calculateVector2D(baseVector, GameDimensionType::DYNAMIC);
    }
};
```

**Game Dimension Types:**
- **DYNAMIC**: Proportional scaling for containers and fluid layouts
- **FIXED**: Logarithmic scaling for UI elements and buttons
- **GAME_WORLD**: Maintains consistent world coordinates for game objects
- **UI_OVERLAY**: For HUD and overlay elements

For complete documentation, see [AppDimens Games Module](appdimens_games/README.md).

---

## 📱 Device Support

### 📱 Supported Device Types

| Device Type | Description | Scaling Behavior |
|-------------|-------------|------------------|
| **Phone** | Standard Android phones | Balanced scaling |
| **Tablet** | Android tablets | Enhanced scaling for larger screens |
| **TV** | Android TV devices | Optimized for viewing distance |
| **Car** | Android Auto | Large touch targets |
| **Watch** | Wear OS devices | Compact scaling |
| **VR** | VR headsets | Immersive scaling |

### 📐 Screen Qualifiers

| Qualifier | Description | Use Case |
|-----------|-------------|----------|
| **SMALL_WIDTH** | Smallest screen dimension | Default, most restrictive |
| **WIDTH** | Screen width | Horizontal layouts |
| **HEIGHT** | Screen height | Vertical layouts |

---

## ⚡ Performance & Optimization

### 📊 Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cached per configuration |
| **SDP/SSP** | Zero | ~2MB (resources) | Pre-calculated |
| **Physical Units** | ~0.002ms | ~10KB | On-demand |

### 🚀 Optimization Tips

1. **Cache Dimensions**: Store frequently used dimensions in properties
2. **Use Appropriate Model**: Fixed for UI elements, Dynamic for layouts
3. **Avoid Excessive Nesting**: Keep dimension chains simple
4. **Profile Performance**: Use built-in performance monitoring

---

## 🧪 Testing

### 📋 Test Coverage

- ✅ Dimension calculations
- ✅ Device type detection
- ✅ Screen factor calculations
- ✅ Extension methods
- ✅ Edge cases and error handling
- ✅ Performance benchmarks

### 🔧 Testing Tools

{% raw %}
```kotlin
// Debug current screen configuration
val (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
println("Screen: ${width} × ${height}")

// Debug device type
println("Device: ${DeviceType.current()}")

// Debug adjustment factors
val factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
println("Factors: ${factors}")
```
{% endraw %}

---

## 📚 Documentation & Resources

### 📖 Complete Documentation

- **[📘 Full Documentation](https://appdimens-project.web.app/)** - Comprehensive guides and API reference
- **[🎯 Core Documentation](DOCS/)** - Detailed technical documentation
- **[🎮 Games Module](appdimens_games/README.md)** - Game development guide
- **[📱 Examples](app/src/main/kotlin/)** - Real-world usage examples

### 🔗 Quick Links

- **[🚀 Installation Guide](#installation)** - Get started in minutes
- **[📱 Examples](#usage-examples)** - Real-world usage examples
- **[🔧 API Reference](DOCS/)** - Complete API documentation
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Common questions and answers

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

### Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time | Cache Strategy |
|---------|------------------|--------------|------------------|----------------|
| **Dynamic/Fixed** | ~0.001ms | ~50KB | Cached per configuration | Automatic dependency tracking |
| **SDP/SSP** | Zero | ~2MB (resources) | Pre-calculated | Resource-based |
| **Physical Units** | ~0.002ms | ~10KB | On-demand | Lazy initialization |
| **Games (Native)** | ~0.0005ms | ~100KB | Cached with LRU | Native C++ implementation |

---

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](../CONTRIBUTING.md) for details.

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

This project is licensed under the Apache License 2.0 - see the [LICENSE](../LICENSE) file for details.

---

## 👨‍💻 Author

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 Show Your Support

If AppDimens Android has helped your project, please consider:

- ⭐ **Starring** this repository
- 🐦 **Sharing** on social media
- 📝 **Writing** a review or blog post
- 🤝 **Contributing** code or documentation

---

<div align="center">
    <p><strong>Made with ❤️ for the Android development community</strong></p>
    <p>AppDimens Android - Where responsive design meets mathematical precision</p>
</div>
