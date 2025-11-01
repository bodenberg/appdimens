---
layout: default
---

<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Core Responsive Dimensioning for Android</strong></p>
    <p>The essential AppDimens module providing Fixed and Dynamic scaling models for Jetpack Compose, XML Views, and Data Binding.</p>
    
[![Version](https://img.shields.io/badge/version-1.1.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> **Languages:** English | [Português (BR)](../../LANG/pt-BR/Android/appdimens_dynamic/README.md) | [Español](../../LANG/es/Android/appdimens_dynamic/README.md)

---

## 🎯 Overview

**AppDimens Dynamic** is the core module of the AppDimens library, providing the fundamental responsive dimensioning system for Android applications. It offers two mathematical scaling models designed to handle different UI design requirements across all device types.

### 🧠 Core Models

| Model | Philosophy | Ideal Use Case | Growth Pattern |
|-------|------------|----------------|----------------|
| **Fixed (FX)** ⭐ **RECOMMENDED** | Logarithmic scaling (refined & balanced) | **Most UI elements**: buttons, paddings, margins, icons, fonts, containers, cards | Smooth, controlled growth |
| **Dynamic (DY)** | Proportional scaling (aggressive) | **Specific cases**: large containers, full-width grids, viewport-dependent elements | Linear, percentage-based |

---

## 📋 Requisitos Mínimos

| Requisito | Versão Mínima       | Recomendado |
|-----------|---------------------|-------------|
| **Kotlin** | 2.0.0               | 2.2.20      |
| **Android Gradle Plugin** | 8.0.0               | 8.13.0      |
| **compileSdk** | 34                  | 36          |
| **minSdk** | 25                  | 25          |
| **targetSdk** | 34                  | 36          |
| **Jetpack Compose BOM** | 2024.01.00          | 2025.10.00  |
| **Page Size** | Compatível com 16KB | ✅           |

### Configuração do Projeto

```kotlin
// build.gradle.kts (Project)
plugins {
    id("com.android.application") version "8.13.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.20" apply false
}

// build.gradle.kts (Module)
android {
    namespace = "com.example.app"
    compileSdk = 36
    
    defaultConfig {
        minSdk = 25
        targetSdk = 36
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
}
```

---

## 🚀 Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:1.1.0")
}
```

---

## 🎨 Usage Examples

### 🧩 Jetpack Compose

#### Fixed Scaling (FX) - RECOMMENDED for Most UI Elements

```kotlin
@Composable
fun FixedScalingExample() {
    Column(
        modifier = Modifier.padding(16.fxdp)  // Fixed padding (RECOMMENDED)
    ) {
        Text(
            text = "Title",
            fontSize = 24.fxsp                // Fixed font size (RECOMMENDED)
        )
        
        Button(
            onClick = { },
            modifier = Modifier
                .width(120.fxdp)              // Fixed width (RECOMMENDED)
                .height(48.fxdp)              // Fixed height (RECOMMENDED)
        ) {
            Text("Action")
        }
    }
}
```

#### Dynamic Scaling (DY) - For Specific Large Container Cases

```kotlin
@Composable
fun DynamicScalingExample() {
    // Note: This example shows Dynamic scaling in action
    // Use this approach only when you need aggressive proportional scaling
    // For most cases, Fixed (FX) is RECOMMENDED
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dydp)                 // Dynamic padding - scales proportionally
    ) {
        items(10) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dydp)         // Dynamic height - proportional to screen
                    .padding(8.fxdp)          // Fixed padding for consistency
            ) {
                Text(
                    text = "Item $index",
                    fontSize = 16.dysp        // Dynamic font - proportional scaling
                )
            }
        }
    }
}
```

#### Advanced Configuration

```kotlin
@Composable
fun AdvancedScalingExample() {
    // Fixed with custom aspect ratio sensitivity
    val customFixedSize = 16.fixed(ScreenType.LOWEST)
        .withAspectRatio(true)
        .withCustomSensitivity(0.5)
        .dp
    
    // Dynamic with multi-window adjustment
    val dynamicSize = 100.dynamic(ScreenType.LOWEST)
        .ignoreMultiViewAdjustment(true)
        .dp
    
    Box(
        modifier = Modifier
            .size(customFixedSize)
            .padding(dynamicSize)
    )
}
```

### 📄 XML Views and Data Binding

#### Data Binding Integration

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    
    <!-- Dynamic width and height -->
    <View
        android:id="@+id/dynamic_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:dynamicWidthDp="@{200f}"
        app:dynamicHeightDp="@{100f}" />
    
    <!-- Dynamic text size -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Dynamic Text"
        app:dynamicTextSizeDp="@{18f}" />
</LinearLayout>
```

#### Kotlin/Java Integration

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
        
        // Percentage-based dimensions
        val percentageWidth = AppDimens.dynamicPercentagePx(
            percentage = 0.8f,  // 80% of screen width
            type = ScreenType.LOWEST,
            resources = resources
        )
        
        percentageView.layoutParams.width = percentageWidth.toInt()
    }
}
```

---

## 🔧 Advanced Features

### 📏 Physical Units Conversion

```kotlin
@Composable
fun PhysicalUnitsExample() {
    // Convert physical measurements to screen units
    val marginDp = 5.0f.toDp(UnitType.MM)      // 5mm to Dp
    val textSizeSp = 2.0f.toSp(UnitType.CM)    // 2cm to Sp
    val borderPx = 1.0f.toPx(UnitType.INCH)    // 1 inch to Px
    
    Box(
        modifier = Modifier
            .padding(marginDp)
            .size(1.0f.toDp(UnitType.INCH))    // 1 inch square
    ) {
        Text(
            text = "Physical Units",
            fontSize = textSizeSp
        )
    }
}
```

### 🧮 Layout Utilities

```kotlin
@Composable
fun LayoutUtilitiesExample() {
    var spanCount by remember { mutableStateOf(3) }
    
    // Calculate optimal number of columns for grid
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
            Card(
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
            ) {
                Text("Item $index")
            }
        }
    }
}
```

### 🔄 Conditional Scaling

```kotlin
@Composable
fun ConditionalScalingExample() {
    val buttonSize = 80.fixedDp()  // Fixed (RECOMMENDED)
        // Priority 1: Watch with specific width
        .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
        // Priority 2: Car mode
        .screen(UiModeType.CAR, 120.dp)
        // Priority 3: Large tablets
        .screen(DpQualifier.SMALL_WIDTH, 720, 150)
    
    Button(
        onClick = { },
        modifier = Modifier.size(buttonSize.sdp)
    ) {
        Text("Adaptive Button")
    }
}
```

---

## 📊 Mathematical Models

### 🎯 Fixed (FX) Model ⭐ **RECOMMENDED**

**Philosophy**: Logarithmic adjustment for refined and balanced scaling

**Formula**: 
```
Final Value = Base DP × (1 + Adjustment Factor × (Base Increment + AR Adjustment))
```

**Characteristics**:
- Smooth, controlled growth
- Slows down on very large screens
- Maintains visual consistency
- Perfect balance for most UI scenarios

**Use Cases** (RECOMMENDED):
- Button heights and widths
- Padding and margins
- Icon sizes
- Font sizes for readability
- Container dimensions
- Card sizes
- Most UI elements

### 🚀 Dynamic (DY) Model

**Philosophy**: Percentage-based proportional adjustment (use sparingly)

**Formula**:
```
Final Value = (Base DP / Reference Width) × Current Screen Dimension
```

**Characteristics**:
- Linear, proportional growth
- Maintains percentage of screen space
- Aggressive scaling on large screens
- Use only when specifically needed

**Use Cases** (specific scenarios only):
- Very large container widths
- Full-width grid layouts
- Spacer dimensions for full-screen layouts
- Viewport-dependent elements that need to scale aggressively

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
| **Physical Units** | ~0.002ms | ~10KB | On-demand |

### 🚀 Optimization Strategies

1. **Cached Calculations**: Adjustment factors are computed once per configuration change
2. **Lazy Evaluation**: Values are computed only when needed
3. **Minimal Overhead**: Simple mathematical operations with minimal memory allocation

### 💡 Best Practices

1. **Use Fixed (FX) for Most Cases** ⭐ **RECOMMENDED**: Buttons, paddings, fonts, icons, containers, cards
2. **Use Dynamic (DY) Sparingly**: Only for specific large containers, full-width grids, viewport-dependent elements
3. **Cache Dimensions**: Store frequently used dimensions in properties
4. **Avoid Excessive Nesting**: Keep dimension chains simple

---

## 🧪 Testing & Debugging

### 🔧 Debug Tools

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

### 📋 Test Coverage

- ✅ Dimension calculations
- ✅ Device type detection
- ✅ Screen factor calculations
- ✅ Extension methods
- ✅ Edge cases and error handling
- ✅ Performance benchmarks

---

## 📚 API Reference

### 🎯 Core Classes

| Class | Description | Key Methods |
|-------|-------------|-------------|
| **AppDimens** | Main entry point | `fixed()`, `dynamic()`, `calculateAvailableItemCount()` |
| **AppDimensFixed** | Fixed scaling | `withAspectRatio()`, `withCustomSensitivity()` |
| **AppDimensDynamic** | Dynamic scaling | `ignoreMultiViewAdjustment()` |
| **AppDimensPhysicalUnits** | Physical units | `toDp()`, `toSp()`, `toPx()` |

### 🔧 Extension Functions

| Extension | Description | Example |
|-----------|-------------|---------|
| `.fxdp` | Fixed Dp scaling | `16.fxdp` |
| `.fxsp` | Fixed Sp scaling | `18.fxsp` |
| `.dydp` | Dynamic Dp scaling | `100.dydp` |
| `.dysp` | Dynamic Sp scaling | `16.dysp` |

---

## 📚 Documentation & Resources

### 📖 Complete Documentation

- **[📘 Full Documentation](https://appdimens-project.web.app/)** - Comprehensive guides and API reference
- **[🎯 Core Documentation](../../DOCS/)** - Detailed technical documentation
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
    <p><strong>AppDimens Dynamic - The foundation of responsive Android design</strong></p>
</div>
