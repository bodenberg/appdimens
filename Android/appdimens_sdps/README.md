<div align="center">
    <h1>📐 AppDimens SDP</h1>
    <p><strong>Dynamic Scaling with Conditional Logic for Android</strong></p>
    <p>Advanced SDP (Scaled Density-independent Pixels) system with conditional rules and priority-based scaling for responsive layouts.</p>
    
[![Version](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

---

## 🎯 Overview

**AppDimens SDP** provides an advanced dimension scaling system that combines the convenience of pre-calculated resources with the flexibility of conditional logic. It offers both simple direct scaling and sophisticated conditional rules based on UI Mode and screen qualifiers.

### 🧠 Key Features

- **🎯 Conditional Scaling**: Priority-based rules for different device types and screen sizes
- **📱 Direct Extensions**: Simple `.sdp`, `.hdp`, `.wdp` extensions for immediate use
- **🔧 XML Support**: Full compatibility with XML layouts and dimension resources
- **⚡ Performance**: Zero runtime overhead with pre-calculated resources
- **🎨 Flexible**: Works with Jetpack Compose and traditional XML Views

---

## 🚀 Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-sdps:1.0.5")
}
```

---

## 🎨 Usage Examples

### 🧩 Jetpack Compose

#### Direct Scaling Extensions

```kotlin
@Composable
fun DirectScalingExample() {
    Column(
        modifier = Modifier.padding(16.sdp)  // SDP padding - scaled by smallest width
    ) {
        Text(
            text = "Responsive Text",
            fontSize = 18.ssp               // SSP font size
        )
        
        Spacer(
            modifier = Modifier
                .height(18.sdp)             // SDP height - scaled by smallest width
                .width(100.wdp)             // WDP width - scaled by screen width
        )
        
        Card(
            modifier = Modifier
                .size(120.sdp)              // SDP size - scaled by smallest width
                .padding(8.hdp)             // HDP padding - scaled by screen height
        ) {
            Text("Card Content")
        }
    }
}
```

#### Conditional Scaling with Priority System

```kotlin
@Composable
fun ConditionalScalingExample() {
    val boxSize = 80.scaledDp() // Base value 80dp
        // Priority 1 (Highest): Watch with specific width
        .screen(
            uiModeType = UiModeType.WATCH,
            qualifierType = DpQualifier.SMALL_WIDTH,
            qualifierValue = 200,
            customValue = 40.dp
        )
        // Priority 2 (Medium): Car mode
        .screen(
            type = UiModeType.CAR,
            customValue = 120.dp
        )
        // Priority 3 (Lowest): Large tablets
        .screen(
            type = DpQualifier.SMALL_WIDTH,
            value = 720,
            customValue = 150
        )
    
    Box(
        modifier = Modifier
            .size(boxSize.sdp)              // Final resolution with dynamic scaling
            .background(Color.Blue)
    ) {
        Text(
            text = "Adaptive Box",
            color = Color.White,
            modifier = Modifier.padding(8.sdp)
        )
    }
}
```

#### Advanced Conditional Rules

```kotlin
@Composable
fun AdvancedConditionalExample() {
    val titleSize = 24.scaledDp()
        // Car with large screen
        .screen(
            uiModeType = UiModeType.CAR,
            qualifierType = DpQualifier.SMALL_WIDTH,
            qualifierValue = 720,
            customValue = 48.dp
        )
        // Watch with specific width
        .screen(
            uiModeType = UiModeType.WATCH,
            qualifierType = DpQualifier.WIDTH,
            qualifierValue = 200,
            customValue = 12.dp
        )
        // Television mode
        .screen(
            type = UiModeType.TELEVISION,
            customValue = 40.dp
        )
        // Large tablets
        .screen(
            type = DpQualifier.SMALL_WIDTH,
            value = 600,
            customValue = 32.dp
        )
    
    Text(
        text = "Adaptive Title",
        fontSize = titleSize.ssp,           // SSP scaling for text
        modifier = Modifier.padding(16.sdp)
    )
}
```

### 📄 XML Views

#### Direct Dimension Resources

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="@dimen/_16sdp">
    
    <!-- Width scaled by smallest width -->
    <TextView
        android:layout_width="@dimen/_100sdp"
        android:layout_height="wrap_content"
        android:text="SDP Width"
        android:textSize="@dimen/_16ssp" />
    
    <!-- Height scaled by screen height -->
    <View
        android:layout_width="match_parent"
        android:layout_height="@dimen/_32hdp"
        android:background="#FF0000" />
    
    <!-- Width scaled by screen width -->
    <TextView
        android:layout_width="@dimen/_200wdp"
        android:layout_height="wrap_content"
        android:text="WDP Width"
        android:layout_marginStart="@dimen/_minus8wdp" />
</LinearLayout>
```

#### Complex Layout Example

```xml
<ScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="@dimen/_16sdp">
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="@dimen/_8sdp">
        
        <!-- Header with SDP dimensions -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="@dimen/_48sdp"
            android:text="Header"
            android:textSize="@dimen/_20ssp"
            android:gravity="center"
            android:background="@color/primary" />
        
        <!-- Content cards with mixed scaling -->
        <androidx.cardview.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="@dimen/_120hdp"
            android:layout_margin="@dimen/_8sdp"
            app:cardCornerRadius="@dimen/_8sdp"
            app:cardElevation="@dimen/_4sdp">
            
            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical"
                android:padding="@dimen/_12sdp">
                
                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="Card Title"
                    android:textSize="@dimen/_16ssp"
                    android:textStyle="bold" />
                
                <TextView
                    android:layout_width="match_parent"
                    android:layout_height="0dp"
                    android:layout_weight="1"
                    android:text="Card content that adapts to screen size"
                    android:textSize="@dimen/_14ssp" />
                
                <Button
                    android:layout_width="@dimen/_100sdp"
                    android:layout_height="@dimen/_36sdp"
                    android:text="Action"
                    android:textSize="@dimen/_12ssp" />
            </LinearLayout>
        </androidx.cardview.widget.CardView>
    </LinearLayout>
</ScrollView>
```

---

## 🔧 Advanced Features

### 🎯 Priority System

The conditional scaling system uses a three-tier priority system:

| Priority | Method | Condition |
|----------|--------|-----------|
| **1 (Highest)** | `screen(uiModeType, qualifierType, qualifierValue, customValue)` | Both UI Mode and DP Qualifier must match |
| **2 (Medium)** | `screen(type: UiModeType, customValue)` | Only UI Mode must match |
| **3 (Lowest)** | `screen(type: DpQualifier, value, customValue)` | Only DP Qualifier must be greater than or equal to value |

### 📐 Scaling Qualifiers

| Qualifier | Description | Use Case |
|-----------|-------------|----------|
| **SDP** | Smallest Width (sw) | Default, most restrictive dimension |
| **HDP** | Height (h) | Elements that should scale with screen height |
| **WDP** | Width (w) | Elements that should scale with screen width |

### 🔄 Resolution Process

1. **Read Configuration**: Current screen configuration is analyzed
2. **Evaluate Rules**: Custom rules are evaluated in priority order (1 to 3)
3. **Select Value**: If a rule matches, its custom value is selected; otherwise, base value is used
4. **Apply Scaling**: Selected value is converted to integer and dynamic scaling is applied
5. **Return Result**: Final scaled dimension is returned

---

## 📊 Dimension Resource Format

### 📝 Resource Naming Convention

SDP expects scaled dimension resources in the format:

```
@dimen/_<optional_negative_prefix><value><qualifier>dp
```

**Examples**:
- `@dimen/_16sdp` - 16dp scaled by smallest width
- `@dimen/_100wdp` - 100dp scaled by screen width
- `@dimen/_32hdp` - 32dp scaled by screen height
- `@dimen/_minus8wdp` - -8dp scaled by screen width (negative margin)

### 🎯 Qualifier Types

| Qualifier | Description | Resource Example |
|-----------|-------------|------------------|
| **s** | Smallest Width | `_16sdp` |
| **h** | Height | `_16hdp` |
| **w** | Width | `_16wdp` |

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
| **Direct Extensions** | Zero | ~2MB (resources) | Pre-calculated |
| **Conditional Logic** | ~0.001ms | ~50KB | Cached per configuration |

### 🚀 Optimization Tips

1. **Use Direct Extensions**: For simple scaling, use `.sdp`, `.hdp`, `.wdp` extensions
2. **Cache Conditional Results**: Store frequently used conditional dimensions
3. **Optimize Resource Files**: Keep dimension resources organized and minimal
4. **Profile Performance**: Monitor memory usage with large resource sets

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

- ✅ Direct scaling extensions
- ✅ Conditional logic evaluation
- ✅ Priority system resolution
- ✅ XML resource integration
- ✅ Edge cases and error handling
- ✅ Performance benchmarks

---

## 📚 API Reference

### 🎯 Core Classes

| Class | Description | Key Methods |
|-------|-------------|-------------|
| **Scaled** | Conditional scaling | `screen()`, `.sdp`, `.hdp`, `.wdp` |
| **AppDimens** | Main entry point | `calculateAvailableItemCount()` |

### 🔧 Extension Functions

| Extension | Description | Example |
|-----------|-------------|---------|
| `.sdp` | Smallest width scaling | `16.sdp` |
| `.hdp` | Height scaling | `32.hdp` |
| `.wdp` | Width scaling | `100.wdp` |

### 🎯 Conditional Methods

| Method | Description | Example |
|--------|-------------|---------|
| `screen(uiModeType, qualifierType, qualifierValue, customValue)` | Priority 1 rule | `.screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)` |
| `screen(type: UiModeType, customValue)` | Priority 2 rule | `.screen(UiModeType.CAR, 120.dp)` |
| `screen(type: DpQualifier, value, customValue)` | Priority 3 rule | `.screen(DpQualifier.SMALL_WIDTH, 720, 150)` |

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
    <p><strong>AppDimens SDP - Advanced conditional scaling for responsive layouts</strong></p>
</div>
