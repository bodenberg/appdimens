<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Core Responsive Dimensioning for Android</strong></p>
    <p>The essential AppDimens module providing Fixed and Dynamic scaling models for Jetpack Compose, XML Views, and Data Binding.</p>
    
[![Version](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> Languages: [Português (BR)](../../LANG/pt-BR/Android/appdimens_dynamic/README.md) | [Español](../../LANG/es/Android/appdimens_dynamic/README.md) | [हिन्दी](../../LANG/hi/Android/appdimens_dynamic/README.md) | [Русский](../../LANG/ru/Android/appdimens_dynamic/README.md) | [中文](../../LANG/zh/Android/appdimens_dynamic/README.md) | [日本語](../../LANG/ja/Android/appdimens_dynamic/README.md)

---

## 🎯 Overview

**AppDimens Dynamic** is the core module of the AppDimens library, providing the fundamental responsive dimensioning system for Android applications. It offers two mathematical scaling models designed to handle different UI design requirements across all device types.

### 🧠 Core Models

| Model | Philosophy | Ideal Use Case | Growth Pattern |
|-------|------------|----------------|----------------|
| **Fixed (FX)** | Logarithmic scaling (refined) | Buttons, paddings, margins, icons | Smooth, controlled growth |
| **Dynamic (DY)** | Proportional scaling (aggressive) | Containers, grids, fluid fonts | Linear, percentage-based |

---

## 🚀 Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.6")
}
```

---

## 🎨 Usage Examples

### 🧩 Jetpack Compose

#### Fixed Scaling (FX) - Refined UI Elements

```kotlin
@Composable
fun FixedScalingExample() {
    Column(
        modifier = Modifier.padding(16.fxdp)  // Fixed padding - consistent feel
    ) {
        Text(
            text = "Title",
            fontSize = 24.fxsp                // Fixed font size - comfortable reading
        )
        
        Button(
            onClick = { },
            modifier = Modifier
                .width(120.fxdp)              // Fixed width - consistent button size
                .height(48.fxdp)              // Fixed height - standard touch target
        ) {
            Text("Action")
        }
    }
}
```

#### Dynamic Scaling (DY) - Proportional Layouts

```kotlin
@Composable
fun DynamicScalingExample() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dydp)                 // Dynamic padding - proportional to screen
    ) {
        items(10) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dydp)         // Dynamic height - proportional
                    .padding(8.fxdp)          // Fixed padding - consistent spacing
            ) {
                Text(
                    text = "Item $index",
                    fontSize = 16.dysp        // Dynamic font - proportional to screen
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
    val buttonSize = 80.dynamicDp() //or fixedDP
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

### 🎯 Fixed (FX) Model

**Philosophy**: Logarithmic adjustment for refined scaling

**Formula**: 
```
Final Value = Base DP × (1 + Adjustment Factor × (Base Increment + AR Adjustment))
```

**Characteristics**:
- Smooth, controlled growth
- Slows down on very large screens
- Maintains visual consistency
- Ideal for UI elements

**Use Cases**:
- Button heights and widths
- Padding and margins
- Icon sizes
- Font sizes for readability

### 🚀 Dynamic (DY) Model

**Philosophy**: Percentage-based proportional adjustment

**Formula**:
```
Final Value = (Base DP / Reference Width) × Current Screen Dimension
```

**Characteristics**:
- Linear, proportional growth
- Maintains percentage of screen space
- Aggressive scaling on large screens
- Ideal for layout containers

**Use Cases**:
- Container widths and heights
- Grid item sizes
- Spacer dimensions
- Viewport-dependent elements

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

1. **Use Fixed for UI Elements**: Buttons, paddings, fonts, icons
2. **Use Dynamic for Layout**: Container widths, spacers, grid items
3. **Cache Dimensions**: Store frequently used dimensions in properties
4. **Avoid Excessive Nesting**: Keep dimension chains simple

---

## 🧪 Testing & Debugging

### 🔧 Debug Tools

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
