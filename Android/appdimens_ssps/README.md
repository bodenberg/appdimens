<div align="center">
    <h1>📐 AppDimens SSP</h1>
    <p><strong>Dynamic Text Scaling with Conditional Logic for Android</strong></p>
    <p>Advanced SSP (Scaled Scale-independent Pixels) system with conditional rules and priority-based scaling for responsive text sizing.</p>
    
[![Version](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

---

## 🎯 Overview

**AppDimens SSP** provides an advanced text scaling system that combines the convenience of pre-calculated resources with the flexibility of conditional logic. It offers both simple direct scaling and sophisticated conditional rules based on UI Mode and screen qualifiers, specifically designed for text and font sizing.

### 🧠 Key Features

- **🎯 Conditional Text Scaling**: Priority-based rules for different device types and screen sizes
- **📱 Direct Extensions**: Simple `.ssp`, `.hsp`, `.wsp`, `.sem`, `.hem`, `.wem` extensions
- **🔧 XML Support**: Full compatibility with XML layouts and dimension resources
- **⚡ Performance**: Zero runtime overhead with pre-calculated resources
- **🎨 Flexible**: Works with Jetpack Compose and traditional XML Views
- **📖 Text-Focused**: Specialized for typography and text sizing

---

## 🚀 Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-ssps:1.0.5")
}
```

---

## 🎨 Usage Examples

### 🧩 Jetpack Compose

#### Direct Text Scaling Extensions

```kotlin
@Composable
fun DirectTextScalingExample() {
    Column(
        modifier = Modifier.padding(16.sdp)
    ) {
        // Standard SSP scaling (respects accessibility)
        Text(
            text = "Main Title",
            fontSize = 24.ssp,              // SSP - scaled by smallest width
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Subtitle",
            fontSize = 18.hsp,              // HSP - scaled by screen height
            color = Color.Gray
        )
        
        Text(
            text = "Body text that adapts to screen width",
            fontSize = 16.wsp,              // WSP - scaled by screen width
            lineHeight = 20.ssp
        )
        
        // EM scaling (ignores accessibility font scaling)
        Text(
            text = "Fixed EM Text",
            fontSize = 14.sem,              // SEM - scaled by smallest width, ignores accessibility
            color = Color.Blue
        )
    }
}
```

#### Conditional Text Scaling with Priority System

```kotlin
@Composable
fun ConditionalTextScalingExample() {
    val titleSize = 24.scaledSp() // Base value 24sp
        // Priority 1 (Highest): Car with large screen
        .screen(
            uiModeType = UiModeType.CAR,
            qualifierType = DpQualifier.SMALL_WIDTH,
            qualifierValue = 720,
            customValue = 48.sp
        )
        // Priority 2 (Medium): Watch mode
        .screen(
            type = UiModeType.WATCH,
            customValue = 12.sp
        )
        // Priority 3 (Lowest): Large tablets
        .screen(
            type = DpQualifier.SMALL_WIDTH,
            value = 600,
            customValue = 32.sp
        )
    
    Text(
        text = "Adaptive Title",
        fontSize = titleSize.ssp,           // Final resolution with dynamic scaling
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.sdp)
    )
}
```

#### Advanced Typography System

```kotlin
@Composable
fun TypographySystemExample() {
    // Define responsive typography scale
    val typography = ResponsiveTypography()
    
    Column(
        modifier = Modifier.padding(16.sdp)
    ) {
        Text(
            text = "Display Title",
            style = typography.displayLarge
        )
        
        Text(
            text = "Headline",
            style = typography.headlineMedium
        )
        
        Text(
            text = "Body Text",
            style = typography.bodyLarge
        )
        
        Text(
            text = "Caption",
            style = typography.labelSmall
        )
    }
}

@Composable
fun ResponsiveTypography(): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontSize = 32.scaledSp()
                .screen(UiModeType.TELEVISION, 48.sp)
                .screen(UiModeType.WATCH, 16.sp)
                .ssp,
            fontWeight = FontWeight.Bold
        ),
        headlineMedium = TextStyle(
            fontSize = 24.scaledSp()
                .screen(UiModeType.TELEVISION, 36.sp)
                .screen(UiModeType.WATCH, 14.sp)
                .ssp,
            fontWeight = FontWeight.SemiBold
        ),
        bodyLarge = TextStyle(
            fontSize = 16.scaledSp()
                .screen(UiModeType.TELEVISION, 24.sp)
                .screen(UiModeType.WATCH, 12.sp)
                .ssp
        ),
        labelSmall = TextStyle(
            fontSize = 12.scaledSp()
                .screen(UiModeType.TELEVISION, 18.sp)
                .screen(UiModeType.WATCH, 10.sp)
                .ssp
        )
    )
}
```

#### Accessibility-Aware Text Scaling

```kotlin
@Composable
fun AccessibilityTextExample() {
    Column(
        modifier = Modifier.padding(16.sdp)
    ) {
        // Respects user's accessibility font scaling
        Text(
            text = "Accessible Text",
            fontSize = 16.ssp,              // SSP respects accessibility
            modifier = Modifier.padding(8.sdp)
        )
        
        // Ignores accessibility font scaling (for UI elements)
        Text(
            text = "Fixed UI Text",
            fontSize = 16.sem,              // SEM ignores accessibility
            color = Color.Gray
        )
        
        // Mixed approach for complex layouts
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Label:",
                fontSize = 14.sem,          // Fixed size for labels
                modifier = Modifier.weight(0.3f)
            )
            Text(
                text = "Dynamic content that should scale with accessibility",
                fontSize = 14.ssp,          // Scalable content
                modifier = Modifier.weight(0.7f)
            )
        }
    }
}
```

### 📄 XML Views

#### Direct Text Size Resources

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="@dimen/_16sdp">
    
    <!-- Title with SSP scaling -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Main Title"
        android:textSize="@dimen/_24ssp"
        android:textStyle="bold"
        android:gravity="center" />
    
    <!-- Subtitle with HSP scaling -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Subtitle"
        android:textSize="@dimen/_18hsp"
        android:textColor="@android:color/darker_gray"
        android:gravity="center" />
    
    <!-- Body text with WSP scaling -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="This is body text that scales with screen width for optimal readability."
        android:textSize="@dimen/_16wsp"
        android:lineSpacingExtra="@dimen/_4ssp" />
    
    <!-- Fixed UI text with SEM scaling -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="UI Element"
        android:textSize="@dimen/_12sem"
        android:textColor="@android:color/darker_gray" />
</LinearLayout>
```

#### Complex Typography Layout

```xml
<ScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="@dimen/_16sdp">
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">
        
        <!-- Article Header -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Article Title"
            android:textSize="@dimen/_28ssp"
            android:textStyle="bold"
            android:gravity="center"
            android:layout_marginBottom="@dimen/_8sdp" />
        
        <!-- Article Meta -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:layout_marginBottom="@dimen/_16sdp">
            
            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:text="By Author Name"
                android:textSize="@dimen/_14ssp"
                android:textColor="@android:color/darker_gray" />
            
            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="2 hours ago"
                android:textSize="@dimen/_12sem"
                android:textColor="@android:color/darker_gray" />
        </LinearLayout>
        
        <!-- Article Content -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="This is the article content that should be easily readable on all devices. The text size adapts to the screen dimensions while maintaining readability and following typography best practices."
            android:textSize="@dimen/_16ssp"
            android:lineSpacingExtra="@dimen/_4ssp"
            android:layout_marginBottom="@dimen/_16sdp" />
        
        <!-- Call to Action -->
        <Button
            android:layout_width="match_parent"
            android:layout_height="@dimen/_48sdp"
            android:text="Read More"
            android:textSize="@dimen/_16ssp"
            android:layout_marginTop="@dimen/_8sdp" />
    </LinearLayout>
</ScrollView>
```

---

## 🔧 Advanced Features

### 🎯 Priority System

The conditional text scaling system uses a three-tier priority system:

| Priority | Method | Condition |
|----------|--------|-----------|
| **1 (Highest)** | `screen(uiModeType, qualifierType, qualifierValue, customValue)` | Both UI Mode and DP Qualifier must match |
| **2 (Medium)** | `screen(type: UiModeType, customValue)` | Only UI Mode must match |
| **3 (Lowest)** | `screen(type: DpQualifier, value, customValue)` | Only DP Qualifier must be greater than or equal to value |

### 📐 Text Scaling Qualifiers

| Qualifier | Description | Accessibility | Use Case |
|-----------|-------------|---------------|----------|
| **SSP** | Smallest Width (sw) | ✅ Respects | Default text scaling |
| **HSP** | Height (h) | ✅ Respects | Text that scales with height |
| **WSP** | Width (w) | ✅ Respects | Text that scales with width |
| **SEM** | Smallest Width (sw) | ❌ Ignores | UI elements, labels |
| **HEM** | Height (h) | ❌ Ignores | UI elements that scale with height |
| **WEM** | Width (w) | ❌ Ignores | UI elements that scale with width |

### 🔄 Resolution Process

1. **Read Configuration**: Current screen configuration is analyzed
2. **Evaluate Rules**: Custom rules are evaluated in priority order (1 to 3)
3. **Select Value**: If a rule matches, its custom value is selected; otherwise, base value is used
4. **Apply Scaling**: Selected value is converted to integer and dynamic scaling is applied
5. **Return Result**: Final scaled text size is returned

---

## 📊 Dimension Resource Format

### 📝 Resource Naming Convention

SSP expects scaled text resources in the format:

```
@dimen/_<value><qualifier>sp
```

**Examples**:
- `@dimen/_16ssp` - 16sp scaled by smallest width
- `@dimen/_18hsp` - 18sp scaled by screen height
- `@dimen/_14wsp` - 14sp scaled by screen width
- `@dimen/_12sem` - 12sp scaled by smallest width, ignores accessibility

### 🎯 Qualifier Types

| Qualifier | Description | Resource Example | Accessibility |
|-----------|-------------|------------------|---------------|
| **s** | Smallest Width | `_16ssp` | ✅ Respects |
| **h** | Height | `_16hsp` | ✅ Respects |
| **w** | Width | `_16wsp` | ✅ Respects |
| **se** | Smallest Width (EM) | `_16sem` | ❌ Ignores |
| **he** | Height (EM) | `_16hem` | ❌ Ignores |
| **we** | Width (EM) | `_16wem` | ❌ Ignores |

---

## 📱 Device Support

### 📱 Supported Device Types

| Device Type | Description | Text Scaling Behavior |
|-------------|-------------|----------------------|
| **Phone** | Standard Android phones | Balanced text scaling |
| **Tablet** | Android tablets | Enhanced text scaling for larger screens |
| **TV** | Android TV devices | Large text for viewing distance |
| **Car** | Android Auto | Large, clear text for quick reading |
| **Watch** | Wear OS devices | Compact text scaling |
| **VR** | VR headsets | Immersive text scaling |

### 📐 Screen Qualifiers

| Qualifier | Description | Use Case |
|-----------|-------------|----------|
| **SMALL_WIDTH** | Smallest screen dimension | Default, most restrictive |
| **WIDTH** | Screen width | Horizontal text layouts |
| **HEIGHT** | Screen height | Vertical text layouts |

---

## ⚡ Performance & Optimization

### 📊 Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time |
|---------|------------------|--------------|------------------|
| **Direct Extensions** | Zero | ~2MB (resources) | Pre-calculated |
| **Conditional Logic** | ~0.001ms | ~50KB | Cached per configuration |

### 🚀 Optimization Tips

1. **Use Direct Extensions**: For simple text scaling, use `.ssp`, `.hsp`, `.wsp` extensions
2. **Cache Typography**: Store frequently used typography scales
3. **Optimize Resource Files**: Keep text dimension resources organized
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

- ✅ Direct text scaling extensions
- ✅ Conditional logic evaluation
- ✅ Priority system resolution
- ✅ XML resource integration
- ✅ Accessibility compliance
- ✅ Edge cases and error handling
- ✅ Performance benchmarks

---

## 📚 API Reference

### 🎯 Core Classes

| Class | Description | Key Methods |
|-------|-------------|-------------|
| **Scaled** | Conditional text scaling | `screen()`, `.ssp`, `.hsp`, `.wsp`, `.sem`, `.hem`, `.wem` |
| **AppDimens** | Main entry point | `calculateAvailableItemCount()` |

### 🔧 Extension Functions

| Extension | Description | Accessibility | Example |
|-----------|-------------|---------------|---------|
| `.ssp` | Smallest width text scaling | ✅ Respects | `16.ssp` |
| `.hsp` | Height text scaling | ✅ Respects | `18.hsp` |
| `.wsp` | Width text scaling | ✅ Respects | `14.wsp` |
| `.sem` | Smallest width EM scaling | ❌ Ignores | `12.sem` |
| `.hem` | Height EM scaling | ❌ Ignores | `14.hem` |
| `.wem` | Width EM scaling | ❌ Ignores | `16.wem` |

### 🎯 Conditional Methods

| Method | Description | Example |
|--------|-------------|---------|
| `screen(uiModeType, qualifierType, qualifierValue, customValue)` | Priority 1 rule | `.screen(UiModeType.CAR, DpQualifier.SMALL_WIDTH, 720, 48.sp)` |
| `screen(type: UiModeType, customValue)` | Priority 2 rule | `.screen(UiModeType.WATCH, 12.sp)` |
| `screen(type: DpQualifier, value, customValue)` | Priority 3 rule | `.screen(DpQualifier.SMALL_WIDTH, 600, 32.sp)` |

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
    <p><strong>AppDimens SSP - Advanced conditional text scaling for responsive typography</strong></p>
</div>
