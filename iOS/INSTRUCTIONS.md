---
layout: default
title: "📋 AppDimens iOS Project Usage Instructions"
---

# 📋 AppDimens iOS Project Usage Instructions

> **Languages:** English | [Português (BR)](../LANG/pt-BR/iOS/INSTRUCTIONS.md) | Español

> **Note:** Spanish translation is not yet available.

## 🎯 Goal

This project converts the Android AppDimens library to iOS, creating a Swift/SwiftUI library that can be used with CocoaPods.

## 📁 Created Structure

```
PROJETO_IOS/
├── AppDimens.podspec              # CocoaPods configuration
├── LICENSE                        # Apache 2.0 license
├── README.md                      # Main documentation
├── CHANGELOG.md                   # Version history
├── DOCUMENTATION.md               # Detailed technical documentation
├── INSTALLATION.md                # Installation guide
├── USAGE_GUIDE.md                 # Practical usage guide
├── PROJECT_SUMMARY.md             # Full project summary
├── INSTRUCTIONS.md                # This file
├── AppDimens.xcodeproj/           # Configured Xcode project
│   └── project.pbxproj
├── AppDimens/
│   └── Info.plist                 # Framework settings
├── Sources/AppDimens/             # Swift source code
│   ├── AppDimens.swift            # Main singleton class
│   ├── AppDimensTypes.swift       # Types, enums, and structs
│   ├── AppDimensAdjustmentFactors.swift # Screen factor calculations
│   ├── AppDimensFixed.swift       # Fixed scaling (FX)
│   ├── AppDimensDynamic.swift     # Dynamic scaling (DY)
│   └── AppDimensExtensions.swift  # Extensions for SwiftUI and UIKit
└── Examples/                      # Practical examples
    ├── UIKitExample.swift         # Full UIKit example
    └── SwiftUIExample.swift       # Full SwiftUI example
```

## 🚀 How to Use

### 1. Installation via CocoaPods

```ruby
# In your Podfile
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens'
end
```

```bash
pod install
```

### 2. Basic Usage

```swift
import AppDimens

// Fixed scaling - for UI elements
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// Dynamic scaling - for layouts
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt
```

### 3. SwiftUI

```swift
import SwiftUI
import AppDimens

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
```

### 4. UIKit

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let button = UIButton()
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        button.frame = CGRect(x: 0, y: 0, width: 200.dypt, height: 44.fxpt)
        view.addSubview(button)
    }
}
```

## 🔧 Implemented Features

### ✅ Core Features

- **AppDimens**: Main singleton class
- **AppDimensFixed**: Fixed scaling with logarithmic adjustment
- **AppDimensDynamic**: Dynamic scaling with proportional adjustment
- **AppDimensAdjustmentFactors**: Screen factor calculations
- **AppDimensTypes**: Types and enums (DeviceType, ScreenType, etc.)

### ✅ Extensions

- **CGFloat/Int Extensions**: `16.fxpt`, `100.dypt`, etc.
- **SwiftUI Extensions**: `.fxPadding()`, `.fxFrame()`, `.fxCornerRadius()`
- **UIKit Extensions**: `.fxFontSize()`, `.fxCornerRadius()`, etc.

### ✅ Advanced Settings

- **Device-Specific Values**: Custom values per device type
- **Screen Qualifiers**: Screen qualifiers for fine control
- **Aspect Ratio Adjustment**: Aspect ratio adjustment for extreme screens
- **Multi-Window Support**: Multi-window mode support
- **Physical Units**: Conversion of physical units (mm, cm, inches)

## 📊 Comparison with Android

| Android | iOS |
|---------|-----|
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `AppDimens.dynamic(100).toDp()` | `AppDimens.dynamic(100).toPoints()` |
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |
| `UiModeType.PHONE` | `DeviceType.phone` |

## 🎯 Mathematical Models

### Fixed (FX) - Refined Adjustment
- **Philosophy**: Logarithmic adjustment for refined scaling
- **Use**: Buttons, paddings, fonts, icons
- **Growth**: Smooth and controlled

### Dynamic (DY) - Proportional Adjustment
- **Philosophy**: Percentage-based adjustment
- **Use**: Containers, spacers, grids
- **Growth**: Linear while keeping screen percentage

## 📱 Device Support

- **Phone**: iPhones
- **Tablet**: iPads
- **Watch**: Apple Watch
- **TV**: Apple TV
- **CarPlay**: CarPlay

## 🔍 Practical Examples

### Responsive Card
```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Title")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("Description that adapts to the screen size.")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
        }
        .fxPadding(16)
        .dyFrame(width: 300)  // Dynamic width
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

### Percentage Layout
```swift
struct PercentageLayout: View {
    var body: some View {
        Rectangle()
            .fill(Color.blue.opacity(0.3))
            .dyFrame(width: AppDimens.percentage(0.8))  // 80% of the screen
            .fxFrame(height: 100)
            .fxCornerRadius(8)
    }
}
```

## 🛠️ Project Configuration

### Build Settings
- **iOS Deployment Target**: 13.0+
- **Swift Language Version**: Swift 5
- **Enable Bitcode**: No

### Dependencies
- **UIKit**: For UIKit extensions
- **SwiftUI**: For SwiftUI extensions (iOS 13.0+)

## 📚 Documentation

### Documentation Files
1. **README.md** - Main documentation
2. **DOCUMENTATION.md** - Technical reference
3. **INSTALLATION.md** - Installation guide
4. **USAGE_GUIDE.md** - Practical guide
5. **CHANGELOG.md** - Version history
6. **PROJECT_SUMMARY.md** - Complete summary

### Examples
1. **UIKitExample.swift** - Complete UIKit example
2. **SwiftUIExample.swift** - Complete SwiftUI example

## 🚀 Next Steps

### For Developers
1. **Test the library** in your projects
2. **Report bugs** via GitHub Issues
3. **Suggest improvements** via GitHub Discussions
4. **Contribute** with Pull Requests

### For Distribution
1. **Test on different devices**
2. **Validate performance**
3. **Update documentation**
4. **Publish on CocoaPods**

## 🔧 Troubleshooting

### Common Issues

1. **Import does not work**
   ```bash
   pod install
   # Clean and rebuild the project
   ```

2. **Dimensions not applying**
   ```swift
   import AppDimens  // Check you imported
   16.fxpt          // Confirm the syntax
   ```

3. **Slow performance**
   ```swift
   // Cache frequently used dimensions
   private let buttonHeight = AppDimens.fixed(44).toPoints()
   ```

## 📞 Support

- **GitHub Issues**: [Create issue](https://github.com/bodenberg/appdimens/issues)
- **Email**: private channel
- **Documentation**: [GitHub Wiki](https://github.com/bodenberg/appdimens/wiki)

## 📄 License

This project is licensed under the Apache 2.0 License - see the [LICENSE](LICENSE) file for details.

---

**AppDimens iOS** - Responsive dimensioning made simple! 🚀

*Successfully converted from the Android project to iOS, keeping all functionality and adding native support for SwiftUI and UIKit.*
