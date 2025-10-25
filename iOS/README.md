---
layout: default
title: "Podfile"
---

<div align="center">
    <img src="../IMAGES/image_sample_devices.png" alt="AppDimens iOS - Responsive Design" height="250"/>
    <h1>📐 AppDimens iOS</h1>
    <p><strong>Smart and Responsive Dimensioning for iOS</strong></p>
    <p>Mathematically responsive scaling that ensures your UI design adapts perfectly to any screen size or aspect ratio — from iPhones to iPads, Apple TV, and Apple Watch.</p>

[![Version](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../LICENSE)
[![Platform](https://img.shields.io/badge/platform-iOS%2013+-orange.svg)](https://developer.apple.com/ios/)
[![Swift](https://img.shields.io/badge/Swift-5.0+-blue.svg)](https://swift.org/)
</div>

> Languages: [Português (BR)](../LANG/pt-BR/iOS/README.md) | [Español](../LANG/es/iOS/README.md) | [हिन्दी](../LANG/hi/iOS/README.md) | [Русский](../LANG/ru/iOS/README.md) | [中文](../LANG/zh/iOS/README.md) | [日本語](../LANG/ja/iOS/README.md)

---

## 🎯 What is AppDimens iOS?

**AppDimens iOS** is a comprehensive dimensioning system that replaces fixed point values with intelligently scaled dimensions based on actual screen characteristics. While iOS's default points are constant, AppDimens treats them as base values that scale predictably across different screen sizes, densities, and aspect ratios.

The library is organized into three modules:
- **Main**: Unified dimension management functionality with advanced caching and qualifiers
- **UI**: UIKit and SwiftUI extensions and integrations  
- **Games**: Metal-specific functionality for game development

### 🎨 Key Benefits

- **🎯 Visual Consistency**: Maintain perfect proportions across all iOS device types
- **📱 Universal Compatibility**: Works seamlessly on iPhones, iPads, Apple TV, and Apple Watch
- **⚡ Performance Optimized**: Minimal runtime overhead with cached calculations
- **🔧 Easy Integration**: Simple API that works with SwiftUI and UIKit
- **📐 Mathematical Precision**: Two scaling models - **Fixed (RECOMMENDED)** for most cases & Dynamic for specific needs
- **🍎 Native iOS**: Built specifically for iOS with Swift and native APIs
- **🎮 Game Development**: Specialized Metal module for high-performance game development
- **🚀 Metal Integration**: Native Metal and MetalKit support with SIMD optimizations

---

## 🎮 Game Development Features

### AppDimens Games Module
The **AppDimens Games** module provides specialized functionality for iOS game development with Metal and MetalKit support:

#### Key Features:
- **Metal Integration**: Native Metal and MetalKit support for high-performance rendering
- **Viewport Scaling Modes**:
  - `Uniform`: Uniform scaling for consistent proportions
  - `Horizontal`: Horizontal scaling for landscape games
  - `Vertical`: Vertical scaling for portrait games
  - `AspectRatio`: Aspect-ratio-aware scaling
  - `Viewport`: Viewport-based scaling for complex layouts
- **Coordinate Conversion**: Screen ↔ NDC coordinate transformations
- **SIMD Extensions**: Optimized vector operations using simd framework
- **SwiftUI Integration**: Game-specific SwiftUI extensions and environment system
- **Performance Optimized**: Native Swift implementation with Metal acceleration

#### Usage Example:
```swift
// Game-specific dimensions
let buttonSize = gameUniform(48)        // Uniform scaling
let playerSize = gameAspectRatio(64)    // Aspect-ratio scaling
let uiOverlaySize = gameViewport(24)    // Viewport scaling

// SwiftUI integration
struct GameView: View {
    var body: some View {
        VStack {
            Text("Score: 1000")
                .font(.system(size: gameUniform(24)))
            
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

---

## 📋 Requisitos Mínimos

| Requisito | Versão Mínima | Recomendado |
|-----------|---------------|-------------|
| **iOS** | 13.0 | 17.0+ |
| **macOS** | 10.15 | 14.0+ |
| **tvOS** | 13.0 | 17.0+ |
| **watchOS** | 6.0 | 10.0+ |
| **Swift** | 5.0 | 5.9+ |
| **Xcode** | 13.0 | 15.0+ |

### Plataformas Suportadas

| Plataforma | Min Version | SwiftUI | UIKit | Games (Metal) |
|------------|-------------|---------|-------|---------------|
| **iOS** | 13.0 | ✅ | ✅ | ✅ |
| **macOS** | 10.15 | ✅ | AppKit | ✅ |
| **tvOS** | 13.0 | ✅ | ✅ | ✅ |
| **watchOS** | 6.0 | ✅ | ❌ | ❌ |

---

## 🚀 Installation

### CocoaPods (Recommended)

```ruby
# Podfile
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens'
end
```

```bash
pod install
```

### Swift Package Manager

1. **In Xcode:**
   - File → Add Package Dependencies
   - Enter: `https://github.com/bodenberg/appdimens.git`
   - Select version: `1.0.8` or higher
   - Add to your target

2. **Or add to Package.swift:**
```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.8")
]
```

### Manual Installation

1. **Download the source code:**
```bash
git clone https://github.com/bodenberg/appdimens.git
```

2. **Copy the Sources folder:**
   - Copy `Sources/AppDimens/` to your project
   - Add all Swift files to your Xcode project

---

## 🧠 Core Dimension Models

| Model | Philosophy | Ideal Use Case | Supported In |
|-------|------------|----------------|--------------|
| **Fixed (FX)** ⭐ **RECOMMENDED** | Logarithmic scaling (refined & balanced) | **Most UI elements**: buttons, paddings, margins, icons, fonts, containers, cards | SwiftUI + UIKit |
| **Dynamic (DY)** | Proportional scaling (aggressive) | **Specific cases**: large containers, full-width grids, viewport-dependent elements | SwiftUI + UIKit |
| **Physical Units** | mm/cm/inch → Points | Wearables, printing, precision layouts | SwiftUI + UIKit |

---

## 🎨 Usage Examples

### 🧩 SwiftUI

#### Basic Responsive Design

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("Responsive Title")
                .font(.fxSystem(size: 24, weight: .bold))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(12)
                .foregroundColor(.blue)
            
            Button("Action") {
                // Button action
            }
            .fxFrame(width: 120, height: 44)
            .fxCornerRadius(8)
        }
        .fxPadding(16)
    }
}
```

#### Advanced Conditional Scaling

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Card Title")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("This card adapts to any screen size with intelligent scaling.")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
            
            HStack {
                Spacer()
                Button("Action") { }
                    .fxFrame(width: 80, height: 32)
                    .fxCornerRadius(6)
            }
        }
        .fxPadding(16)
        .fxFrame(width: 300)           // Fixed width (RECOMMENDED)
        .fxFrame(height: 200)          // Fixed height
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

#### Environment Integration (Recommended)

```swift
@main
struct MyApp: App {
    var body: some Scene {
        WindowGroup {
            DimensProvider {  // Essential for new features
                ContentView()
            }
        }
    }
}

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("Enhanced AppDimens")
                .font(.fxSystem(size: 24, weight: .bold))
            
            // Protocol-based API
            Rectangle()
                .frame(width: 100.fixed().dimension)
                .frame(height: 50.fxpt)
            
            // Physical units
            Rectangle()
                .frame(width: 2.cm, height: 1.cm)
            
            // Item count calculator
            Rectangle()
                .calculateAvailableItemCount(
                    itemSize: 50.fxpt,
                    itemPadding: 8.fxpt,
                    count: $itemCount
                )
        }
    }
}
```

### 📱 UIKit

#### Basic UIKit Integration

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    private func setupUI() {
        // Container
        let containerView = UIView()
        containerView.backgroundColor = .systemBlue
        containerView.fxCornerRadius(16)
        view.addSubview(containerView)
        
        // Label
        let titleLabel = UILabel()
        titleLabel.text = "Responsive Title"
        titleLabel.textAlignment = .center
        titleLabel.fxFontSize(20)
        containerView.addSubview(titleLabel)
        
        // Button
        let button = UIButton(type: .system)
        button.setTitle("Action", for: .normal)
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        containerView.addSubview(button)
        
        // Constraints
        containerView.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        button.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            // Container - dynamic width, fixed height
            containerView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            containerView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            containerView.widthAnchor.constraint(equalToConstant: 300.dypt),
            containerView.heightAnchor.constraint(equalToConstant: 200.fxpt),
            
            // Label
            titleLabel.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 20.fxpt),
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16.fxpt),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16.fxpt),
            
            // Button
            button.centerXAnchor.constraint(equalTo: containerView.centerXAnchor),
            button.centerYAnchor.constraint(equalTo: containerView.centerYAnchor),
            button.widthAnchor.constraint(equalToConstant: 120.dypt),
            button.heightAnchor.constraint(equalToConstant: 44.fxpt)
        ])
    }
}
```

#### Advanced UIKit Configuration

```swift
class AdvancedViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupAdvancedUI()
    }
    
    private func setupAdvancedUI() {
        // Custom dimensions with device-specific values
        let customDimension = AppDimens.fixed(16)
            .screen(.phone, 14)           // 14pt for phones
            .screen(.tablet, 18)          // 18pt for tablets
            .aspectRatio(enable: true)    // Enable aspect ratio adjustment
            .toPoints()
        
        // Dynamic with custom screen type
        let dynamicDimension = AppDimens.dynamic(100)
            .type(.highest)               // Use highest screen dimension
            .toPoints()
        
        // Apply to UI elements
        let label = UILabel()
        label.font = UIFont.systemFont(ofSize: customDimension)
        label.text = "Custom scaled text"
        
        let view = UIView()
        view.frame = CGRect(x: 0, y: 0, width: dynamicDimension, height: 50.fxpt)
        
        view.addSubview(label)
        self.view.addSubview(view)
    }
}
```

---

## 🔧 Advanced Features

### 🔄 Conditional Scaling

```swift
// Custom screen qualifiers
let customDimension = AppDimens.fixed(16)
    .screen(.phone, 14)           // 14pt for phones
    .screen(.tablet, 18)          // 18pt for tablets
    .screen(.watch, 12)           // 12pt for Apple Watch
    .aspectRatio(enable: true)    // Enable aspect ratio adjustment
    .toPoints()

// Dynamic with custom screen type
let dynamicDimension = AppDimens.dynamic(100)
    .type(.highest)               // Use highest screen dimension
    .toPoints()
```

### 📏 Physical Units

```swift
// Physical units conversion
Rectangle()
    .frame(width: 2.cm, height: 1.cm)    // 2cm × 1cm
    .frame(width: 5.mm, height: 3.mm)    // 5mm × 3mm
    .frame(width: 1.inch, height: 0.5.inch) // 1 inch × 0.5 inch
```

### 🧮 Layout Utilities

```swift
struct ResponsiveGrid: View {
    let items = Array(1...12)
    
    var body: some View {
        LazyVGrid(columns: [
            GridItem(.flexible(), spacing: 16.fxpt),
            GridItem(.flexible(), spacing: 16.fxpt)
        ], spacing: 16.fxpt) {
            ForEach(items, id: \.self) { item in
                VStack {
                    Image(systemName: "star.fill")
                        .font(.fxSystem(size: 24))
                        .foregroundColor(.yellow)
                    
                    Text("Item \(item)")
                        .font(.fxSystem(size: 12))
                }
                .fxFrame(width: 80, height: 80)
                .background(Color(.systemGray5))
                .fxCornerRadius(8)
            }
        }
        .fxPadding(16)
    }
}
```

### 📊 Percentage-Based Layouts

```swift
struct PercentageLayout: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            // 80% of screen width
            Rectangle()
                .fill(Color.blue.opacity(0.3))
                .dyFrame(width: AppDimens.percentage(0.8))
                .fxFrame(height: 100)
                .fxCornerRadius(8)
            
            // 60% of screen width
            Rectangle()
                .fill(Color.green.opacity(0.3))
                .dyFrame(width: AppDimens.percentage(0.6))
                .fxFrame(height: 80)
                .fxCornerRadius(8)
        }
    }
}
```

---

## 📊 Mathematical Models

### 🎯 Fixed (FX) Model ⭐ **RECOMMENDED**

**Philosophy**: Logarithmic adjustment for refined and balanced scaling

**Formula**: 
```
Final Value = Base Points × (1 + Adjustment Factor × (Base Increment + AR Adjustment))
```

**Characteristics**:
- Smooth, controlled growth
- Slows down on very large screens
- Maintains visual consistency
- Ideal for UI elements

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
Final Value = (Base Points / Reference Width) × Current Screen Dimension
```

**Characteristics**:
- Linear, proportional growth
- Maintains percentage of screen space
- Aggressive scaling on large screens
- Ideal for layout containers

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
| **Phone** | iPhone devices | Balanced scaling |
| **Tablet** | iPad devices | Enhanced scaling for larger screens |
| **Watch** | Apple Watch devices | Compact scaling |
| **TV** | Apple TV devices | Large UI elements for viewing distance |
| **CarPlay** | CarPlay devices | Large touch targets |

### 📐 Screen Types

| Type | Description | Use Case |
|------|-------------|----------|
| **Lowest** | Use smallest screen dimension | Default, most restrictive |
| **Highest** | Use largest screen dimension | For elements that should scale with largest dimension |

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

```swift
// Debug current screen configuration
let (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
print("Screen: \(width) × \(height)")

// Debug device type
print("Device: \(DeviceType.current())")

// Debug adjustment factors
let factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
print("Factors: \(factors)")
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
| **AppDimens** | Main entry point | `fixed()`, `dynamic()`, `percentage()` |
| **AppDimensFixed** | Fixed scaling | `screen()`, `aspectRatio()`, `type()` |
| **AppDimensDynamic** | Dynamic scaling | `screen()`, `type()` |
| **AppDimensAdjustmentFactors** | Screen calculations | `getCurrentScreenDimensions()`, `calculateAdjustmentFactors()` |

### 🔧 Extension Functions

| Extension | Description | Example |
|-----------|-------------|---------|
| `.fxpt` | Fixed points | `16.fxpt` |
| `.fxpx` | Fixed pixels | `16.fxpx` |
| `.dypt` | Dynamic points | `100.dypt` |
| `.dypx` | Dynamic pixels | `100.dypx` |
| `.cm` | Centimeters | `2.cm` |
| `.mm` | Millimeters | `5.mm` |
| `.inch` | Inches | `1.inch` |

### 🎨 SwiftUI Extensions

| Extension | Description | Example |
|-----------|-------------|---------|
| `.fxPadding()` | Fixed padding | `.fxPadding(16)` |
| `.fxFrame()` | Fixed frame | `.fxFrame(width: 100, height: 50)` |
| `.fxCornerRadius()` | Fixed corner radius | `.fxCornerRadius(8)` |
| `.dyFrame()` | Dynamic frame | `.dyFrame(width: 200)` |
| `.font(.fxSystem())` | Fixed font | `.font(.fxSystem(size: 16))` |

### 📱 UIKit Extensions

| Extension | Description | Example |
|-----------|-------------|---------|
| `.fxFontSize()` | Fixed font size | `label.fxFontSize(16)` |
| `.fxCornerRadius()` | Fixed corner radius | `view.fxCornerRadius(8)` |
| `.fxBorderWidth()` | Fixed border width | `view.fxBorderWidth(1)` |
| `.fxTitleFontSize()` | Fixed title font size | `button.fxTitleFontSize(14)` |

---

## 🔄 Migration from Android

If you're familiar with the Android version of AppDimens, here's the mapping:

| Android | iOS |
|---------|-----|
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `AppDimens.dynamic(100).toDp()` | `AppDimens.dynamic(100).toPoints()` |
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |
| `UiModeType.PHONE` | `DeviceType.phone` |

---

## 📚 Documentation & Resources

### 📖 Complete Documentation

- **[📘 Full Documentation](https://appdimens-project.web.app/)** - Comprehensive guides and API reference
- **[🎯 Technical Documentation](DOCUMENTATION.md)** - Detailed technical documentation
- **[📱 Usage Guide](USAGE_GUIDE.md)** - Practical usage guide
- **[🔧 Installation Guide](INSTALLATION.md)** - Installation instructions
- **[📱 Examples](Examples/)** - Real-world usage examples

### 🔗 Quick Links

- **[🚀 Installation Guide](#installation)** - Get started in minutes
- **[📱 Examples](#usage-examples)** - Real-world usage examples
- **[🔧 API Reference](#api-reference)** - Complete API documentation
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Common questions and answers

---

## 🏗️ Architecture Overview

### iOS Library Structure

| Module | Purpose | Dependencies | Key Features |
|--------|---------|-------------|--------------|
| **AppDimens** | Core functionality | Foundation, UIKit | DY/FX models, caching, qualifiers |
| **AppDimensUI** | UI extensions | AppDimens | SwiftUI extensions, UIKit integration |
| **AppDimensGames** | Game development | AppDimens, Metal | Metal integration, viewport management, SIMD |

### Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time | Cache Strategy |
|---------|------------------|--------------|------------------|----------------|
| **Dynamic/Fixed** | ~0.001ms | ~50KB | Cached per configuration | Automatic dependency tracking |
| **Physical Units** | ~0.002ms | ~10KB | On-demand | Lazy initialization |
| **Games (Metal)** | ~0.0005ms | ~100KB | Cached with SIMD | Native Metal implementation |
| **SwiftUI Extensions** | Zero | ~5KB | Compile-time | Static extensions |

### Platform Support

| Platform | Min Version | SwiftUI | UIKit | Games |
|----------|-------------|---------|-------|-------|
| **iOS** | 13.0 | ✅ | ✅ | ✅ (Metal) |
| **macOS** | 10.15 | ✅ | AppKit | ✅ (Metal) |
| **tvOS** | 13.0 | ✅ | ✅ | ✅ (Metal) |
| **watchOS** | 6.0 | ✅ | ❌ | ❌ |

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

If AppDimens iOS has helped your project, please consider:

- ⭐ **Starring** this repository
- 🐦 **Sharing** on social media
- 📝 **Writing** a review or blog post
- 🤝 **Contributing** code or documentation

---

<div align="center">
    <p><strong>Made with ❤️ for the iOS development community</strong></p>
    <p>AppDimens iOS - Where responsive design meets mathematical precision</p>
</div>
