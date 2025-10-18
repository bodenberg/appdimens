<div align="center">
    <img src="IMAGES/image_sample_devices.png" alt="AppDimens - Responsive Design Across All Devices" height="300"/>
    <h1>📐 AppDimens</h1>
    <p><strong>Smart and Responsive Dimensioning for Android & iOS</strong></p>
    <p>Mathematically responsive scaling that ensures your UI design adapts perfectly to any screen size or aspect ratio — from phones to TVs, cars, and wearables.</p>
    
[![Version](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%20%7C%20iOS-orange.svg)](https://github.com/bodenberg/appdimens)
[![Documentation](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

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
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.5")
    
    // Optional: SDP & SSP scaling
    implementation("io.github.bodenberg:appdimens-sdps:1.0.5")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.5")
    
    // All-in-one package (does not include games module)
    implementation("io.github.bodenberg:appdimens-all:1.0.5")
    
    // Game development with C++/NDK support (separate dependency)
    implementation("io.github.bodenberg:appdimens-games:1.0.5")
}
```

### iOS

```ruby
# Podfile
pod 'AppDimens'
```

```swift
// Swift Package Manager
.package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
```

---

## 🧠 Core Dimension Models

| Model | Philosophy | Ideal Use Case | Supported Platforms |
|-------|------------|----------------|-------------------|
| **Fixed (FX)** | Logarithmic scaling (refined) | Buttons, paddings, margins, icons | Android + iOS |
| **Dynamic (DY)** | Proportional scaling (aggressive) | Containers, grids, fluid fonts | Android + iOS |
| **SDP / SSP** | Pre-calculated resources | Direct `@dimen` usage in XML | Android |
| **Physical Units** | mm/cm/inch → Dp/Sp/Px/Points | Wearables, printing, precision layouts | Android + iOS |

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

---

## 📊 Performance & Compatibility

### ⚡ Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cached per configuration |
| **SDP/SSP** | Zero | ~2MB (resources) | Pre-calculated |
| **Physical Units** | ~0.002ms | ~10KB | On-demand |

### 📱 Platform Support

| Platform | Min Version | UI Frameworks | Special Features |
|----------|-------------|---------------|------------------|
| **Android** | API 21+ | Compose, Views, Data Binding | SDP/SSP, Physical Units |
| **iOS** | 13.0+ | SwiftUI, UIKit | Native extensions |

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
- 📧 [Email](mailto:jean.bodenberg@gmail.com)
- 💼 [LinkedIn](https://linkedin.com/in/jean-bodenberg)

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
