# 📱 AppDimens iOS - Modular Structure

> Languages: [Português (BR)](../LANG/pt-BR/iOS/README_MODULAR.md) | [Español](../LANG/es/iOS/README_MODULAR.md) | [हिन्दी](../LANG/hi/iOS/README_MODULAR.md) | [Русский](../LANG/ru/iOS/README_MODULAR.md) | [中文](../LANG/zh/iOS/README_MODULAR.md) | [日本語](../LANG/ja/iOS/README_MODULAR.md)

[![Version](https://img.shields.io/cocoapods/v/AppDimens.svg?style=flat)](https://cocoapods.org/pods/AppDimens)
[![License](https://img.shields.io/cocoapods/l/AppDimens.svg?style=flat)](https://cocoapods.org/pods/AppDimens)
[![Platform](https://img.shields.io/cocoapods/p/AppDimens.svg?style=flat)](https://cocoapods.org/pods/AppDimens)

**AppDimens** is a responsive dimension management system for iOS that automatically adjusts values based on screen dimensions, ensuring layout consistency on any screen size or aspect ratio.

## 🏗️ Modular Architecture

The AppDimens library has been reorganized into a modular structure to allow flexible selection of required components:

### 📦 Available Modules

| Module | Description | Dependencies |
|--------|-------------|--------------|
| **Core** | Basic dimension management functionality | Foundation, UIKit |
| **UI** | Extensions for UIKit and SwiftUI | Core + SwiftUI |
| **Games** | Game-specific functionality for Metal | Core + Metal + MetalKit |

## 🚀 Installation

### CocoaPods

#### Full Installation (Recommended)
```ruby
pod 'AppDimens'
```
This automatically includes the Core and UI modules.

#### Modular Installation
```ruby
# Core only
pod 'AppDimens/Core'

# Core + UI (default)
pod 'AppDimens/UI'

# Core + Games (for game development)
pod 'AppDimens/Games'

# All modules
pod 'AppDimens/Core'
pod 'AppDimens/UI'
pod 'AppDimens/Games'
```

### Swift Package Manager

```swift
.package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
```

## 📖 Usage by Module

### 🔧 Core Module

The Core module provides the basic dimension management functionality:

```swift
import AppDimensCore

// Basic usage
let buttonHeight = AppDimens.fixed(48).toPoints()
let cardWidth = AppDimens.dynamic(100).toPoints()

// Simplified syntax
let padding = 16.fxpt
let margin = 100.dypt

// Physical units
let width = 2.cm
let height = 1.inch
```

### 🎨 UI Module

The UI module adds integration with UIKit and SwiftUI:

```swift
import AppDimensUI

// SwiftUI
struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("Title")
                .font(.fxSystem(size: 24, weight: .bold))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(12)
        }
    }
}

// UIKit
let button = UIButton()
button.fxTitleFontSize(16)
button.fxCornerRadius(8)
```

### 🎮 Games Module

The Games module provides functionality specific to game development with Metal:

```swift
import AppDimensGames
import Metal

// Initialization
let device = MTLCreateSystemDefaultDevice()!
let viewport = MTLViewport(originX: 0, originY: 0, width: 1920, height: 1080, znear: 0, zfar: 1)
AppDimensGames.shared.initialize(device: device, viewport: viewport)

// Game usage
let buttonSize = AppDimensGames.uniform(64.0)
let fontSize = AppDimensGames.aspectRatio(24.0)
let spacing = AppDimensGames.viewport(16.0)

// Extensions for simd
let position = simd_float2(100, 200)
let scaledPosition = position.gameUniform(AppDimensGames.shared.getMetalManager()!)

// Coordinate conversion
let ndcPoint = AppDimensGames.shared.screenToNDC(simd_float2(960, 540))
```

## 🎯 Module Selection

### For Standard iOS Apps
```ruby
pod 'AppDimens'  # Inclui Core + UI automaticamente
```

### For Game Development
```ruby
pod 'AppDimens/Core'
pod 'AppDimens/Games'
```

### For Libraries That Only Need Calculations
```ruby
pod 'AppDimens/Core'
```

### For Apps That Use Only UIKit (no SwiftUI)
```ruby
pod 'AppDimens/Core'
# Use only the UIKit extensions from the Core module
```

## 🔧 Advanced Configuration

### Core Module
```swift
// Custom configuration
let customDimension = AppDimens.fixed(16)
    .screen(.phone, 14)           // 14pt for iPhones
    .screen(.tablet, 18)          // 18pt for iPads
    .aspectRatio(enable: true)    // Enable aspect ratio adjustment
    .toPoints()
```

### Games Module
```swift
// Performance configuration
let performanceSettings = GamePerformanceSettings.highPerformance

// Different scaling modes
let uniformSize = AppDimensGames.uniform(100.0)      // Uniform scaling
let horizontalSize = AppDimensGames.horizontal(100.0) // Horizontal scaling
let viewportSize = AppDimensGames.viewport(100.0)     // Viewport scaling
```

## 📊 Module Comparison

| Functionality | Core | UI | Games |
|---------------|------|----|-------|
| Basic calculations | ✅ | ✅ | ✅ |
| UIKit extensions | ✅ | ✅ | ✅ |
| SwiftUI extensions | ❌ | ✅ | ❌ |
| Metal integration | ❌ | ❌ | ✅ |
| NDC coordinates | ❌ | ❌ | ✅ |
| Game scaling | ❌ | ❌ | ✅ |
| Library size | ~50KB | ~80KB | ~120KB |

## 🎨 Usage Examples

### Standard iOS App
```swift
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("AppDimens")
                .font(.fxSystem(size: 24, weight: .bold))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(12)
        }
    }
}
```

### Game with Metal
```swift
import AppDimensGames
import Metal

class GameRenderer {
    private let metalManager: AppDimensMetal
    
    init(device: MTLDevice, viewport: MTLViewport) {
        self.metalManager = AppDimensMetal(device: device, viewport: viewport)
        AppDimensGames.shared.initialize(device: device, viewport: viewport)
    }
    
    func renderUI() {
        let buttonSize = 64.0.gameUniform(metalManager)
        let fontSize = 24.0.gameFontSize(metalManager)
        let spacing = 16.0.gameSpacing(metalManager)
        
        // Render UI elements with scaled dimensions
    }
}
```

## 📚 Additional Documentation

- [Core Documentation](Sources/AppDimensCore/README.md)
- [UI Documentation](Sources/AppDimensUI/README.md)
- [Games Documentation](Sources/AppDimensGames/README.md)
- [Migration Guide](MIGRATION_GUIDE.md)

## 🤝 Contributing

Contributions are welcome! Please feel free to open a Pull Request.

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Jean Bodenberg**
- GitHub: [@bodenberg](https://github.com/bodenberg)
