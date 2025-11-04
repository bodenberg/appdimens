# 📐 AppDimens for iOS

**Smart Responsive Dimensions for iOS, iPadOS, tvOS, watchOS**  
*Version: 2.0.0 | Last Updated: February 2025*

> **Languages:** English | [Português (BR)](../LANG/pt-BR/iOS/README.md) | [Español](../LANG/es/iOS/README.md)

[![Platform](https://img.shields.io/badge/platform-iOS%20%7C%20iPadOS%20%7C%20tvOS%20%7C%20watchOS-lightgrey.svg)](https://github.com/bodenberg/appdimens)
[![Swift](https://img.shields.io/badge/Swift-5.9%2B-orange.svg)](https://swift.org)

---

## 🆕 What's New in Version 2.0

- 🎯 **13 Scaling Strategies** (up from 2)
- ⭐ **BALANCED** - Primary recommendation (hybrid linear-logarithmic)
- 🔬 **Perceptual Models** (Weber-Fechner, Stevens' Power Law)
- 📐 **Aspect Ratio Adjustment** (5 strategies with AR support)
- 🧠 **Smart Inference** - Automatic strategy selection
- ⚡ **5x Performance** - Optimized Swift implementation
- 🎮 **Metal Integration** - Enhanced game development support

---

## 🚀 Installation

### CocoaPods

```ruby
# Full package (Main + UI + Games)
pod 'AppDimens', '~> 2.0.0'

# Only Main module
pod 'AppDimens/Main', '~> 2.0.0'

# Games module
pod 'AppDimens/Games', '~> 2.0.0'
```

### Swift Package Manager

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "2.0.0")
]
```

---

## ⚡ Quick Start

### SwiftUI (Recommended)

```swift
struct MyView: View {
    var body: some View {
        VStack(spacing: AppDimens.shared.balanced(16).toPoints()) {
            // BALANCED ⭐ (Primary recommendation)
            Text("Hello World")
                .font(.system(size: AppDimens.shared.balanced(18).toPoints()))
            
            Button("Click Me") { }
                .frame(height: AppDimens.shared.balanced(48).toPoints())
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
    }
}
```

### UIKit

```swift
class MyViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let label = UILabel()
        label.font = .systemFont(ofSize: AppDimens.shared.balanced(18).toPoints())
        
        let button = UIButton()
        button.frame.size.height = AppDimens.shared.balanced(48).toPoints()
        
        view.addSubview(label)
        view.addSubview(button)
    }
}
```

---

## 🎯 13 Scaling Strategies

### Primary: BALANCED ⭐

```swift
AppDimens.shared.balanced(16).toPoints()
```

**Use for:** 95% of apps (iPhone, iPad, Apple TV)

### Secondary: DEFAULT

```swift
AppDimens.shared.defaultScaling(24).toPoints()
```

**Use for:** iPhone-only apps, icons

### Others

```swift
// PERCENTAGE (Containers)
AppDimens.shared.percentage(300).toPoints()

// LOGARITHMIC (Apple TV)
AppDimens.shared.logarithmic(20).toPoints()

// POWER (Configurable)
AppDimens.shared.power(16, exponent: 0.75).toPoints()

// FLUID (Typography)
AppDimens.shared.fluid(min: 16, max: 24).toPoints()

// Smart API
AppDimens.shared.smart(48).forElement(.button).toPoints()
```

---

## 📱 Platform Support

- ✅ iOS 13.0+
- ✅ iPadOS 13.0+
- ✅ tvOS 13.0+
- ✅ watchOS 6.0+
- ✅ macOS 10.15+ (Catalyst)

---

## 🎮 Game Development

### Metal Integration

```swift
let buttonSize = gameUniform(48)
let playerSize = gameAspectRatio(64)
```

**[📖 Complete Games Guide](DOCUMENTATION.md#game-development)**

---

## 📚 Documentation

- [Documentation](DOCUMENTATION.md) - Complete API reference
- [Installation Guide](INSTALLATION.md) - Detailed installation
- [Usage Guide](USAGE_GUIDE.md) - Usage examples
- [Main Documentation](../DOCS/README.md) - Central docs

---

**Author:** Jean Bodenberg  
**License:** Apache 2.0  
**Repository:** https://github.com/bodenberg/appdimens
