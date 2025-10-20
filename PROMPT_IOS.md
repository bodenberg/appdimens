# AppDimens iOS - System Knowledge Documentation

> Languages: [Português (BR)](LANG/pt-BR/PROMPT_IOS.md) | [Español](LANG/es/PROMPT_IOS.md) | [हिन्दी](LANG/hi/PROMPT_IOS.md) | [Русский](LANG/ru/PROMPT_IOS.md) | [中文](LANG/zh/PROMPT_IOS.md) | [日本語](LANG/ja/PROMPT_IOS.md)

This document provides comprehensive information about the **AppDimens iOS** dimension management system. AppDimens is a responsive dimension management library that provides density-independent sizing and physical unit support for iOS development with SwiftUI and UIKit.

### 1\. Core Concept and Purpose

**Objective:** To enable developers to define UI element sizes (like `width`, `height`, `padding`, and `spacing`) using a base value (similar to density-independent pixels or "DP") that is automatically scaled and adjusted based on screen size, aspect ratio, and custom qualifiers, ensuring a consistent visual experience across various device sizes (phones, tablets, and multi-window modes).

**Key Components:**

  * **`AppDimens.shared`:** Main singleton class providing access to dimension builders
  * **`AppDimensFixed`:** Fixed scaling with logarithmic adjustment
  * **`AppDimensDynamic`:** Dynamic scaling with proportional adjustment
  * **Extensions:** `.fxpt`, `.dypt`, `.fxpx`, `.dypx` for convenient access

### 2\. Dimension Calculation Types (Fixed vs. Dynamic)

The system offers two primary calculation strategies:

#### A. Fixed Adjustment - Logarithmic Scaling

**Purpose:** Provides refined, logarithmic scaling for UI elements that should maintain visual consistency across different screen sizes.

**Access Methods:**
- `AppDimens.shared.fixed(value)` - Creates a fixed dimension builder
- `value.fxpt` - Extension for direct fixed scaling in points
- `value.fxpx` - Extension for direct fixed scaling in pixels

**Usage Examples:**
```swift
// Using the builder pattern
let buttonHeight = AppDimens.shared.fixed(48).toPoints()

// Using extensions
let padding = 16.fxpt
let borderWidth = 1.fxpx
```

#### B. Dynamic Adjustment - Proportional Scaling

**Purpose:** Provides aggressive, proportional scaling for containers and layout elements that should scale with screen size.

**Access Methods:**
- `AppDimens.shared.dynamic(value)` - Creates a dynamic dimension builder
- `value.dypt` - Extension for direct dynamic scaling in points
- `value.dypx` - Extension for direct dynamic scaling in pixels

**Usage Examples:**
```swift
// Using the builder pattern
let containerWidth = AppDimens.shared.dynamic(300).toPoints()

// Using extensions
let cardWidth = 200.dypt
let heroImageHeight = 180.dypt
```

### 3\. SwiftUI Integration

#### A. SwiftUI Extensions

**Purpose:** Provides convenient extensions for SwiftUI views to use AppDimens scaling directly.

**Available Extensions:**
- `.fxPadding(value)` - Fixed padding
- `.dyPadding(value)` - Dynamic padding
- `.fxFrame(width:, height:)` - Fixed frame sizing
- `.dyFrame(width:, height:)` - Dynamic frame sizing
- `.fxCornerRadius(value)` - Fixed corner radius
- `.dyCornerRadius(value)` - Dynamic corner radius

**Usage Examples:**
```swift
VStack {
    Text("Hello")
        .fxPadding(16)
        .fxCornerRadius(8)
    
    Rectangle()
        .fxFrame(width: 200, height: 100)
        .dyCornerRadius(12)
}
```

#### B. UIKit Integration

**Purpose:** Provides extensions for UIKit components to use AppDimens scaling.

**Available Extensions:**
- `.fxCornerRadius(value)` - Fixed corner radius for UIView
- `.dyCornerRadius(value)` - Dynamic corner radius for UIView
- `.fxFontSize(value)` - Fixed font size for UILabel, UIButton, UITextField, UITextView
- `.dyFontSize(value)` - Dynamic font size for UILabel, UIButton, UITextField, UITextView

**Usage Examples:**
```swift
let button = UIButton()
button.fxTitleFontSize(16)
button.fxCornerRadius(8)

let label = UILabel()
label.dyFontSize(14)
```

### 4\. Installation

#### Swift Package Manager
```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
]
```

#### CocoaPods
```ruby
pod 'AppDimens'
```

### 5\. Game Development Support

AppDimens includes specialized support for game development with Metal integration:

```swift
import AppDimensGames
import Metal

// Initialize for game development
AppDimensGames.shared.initialize(device: metalDevice, viewport: viewport)

// Use game-specific scaling
let buttonSize = gameUniform(48.0)
let playerSize = gameAspectRatio(64.0)
```

### **Summary**

AppDimens iOS provides a comprehensive dimension management system with:
- Fixed and Dynamic scaling models
- SwiftUI and UIKit integration
- Game development support with Metal
- Simple extension-based API (`.fxpt`, `.dypt`, etc.)
- Cross-platform consistency with Android version
