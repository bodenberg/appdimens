---
layout: default
title: "Changelog"
---

# Changelog

> **Languages:** English | [Português (BR)](../LANG/pt-BR/iOS/CHANGELOG.md) | Español

> **Note:** Spanish translation is not yet available.

All notable changes to the AppDimens iOS library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.10] - 2025-01-31

### Added
- **Fluid Scaling Model (FL)**: New clamp-like scaling model for SwiftUI
  - `AppDimensFluid` class for fluid dimension calculations
  - `fluid(min:max:)` shorthand function for quick instances
  - Extension methods: `fluidTo()`, `fluidFrom()` on `CGFloat`, `Int`, and `Double`
  - SwiftUI View extensions: `fluidPadding()`, `fluidFrame()`
  - Device type qualifiers support (tablets, TV, etc.)
  - Screen width qualifiers support
  - Custom breakpoint ranges (default: 320-768pt)
  - Methods: `getMin()`, `getMax()`, `getPreferred()`, `lerp(t:)`
  - Comprehensive `FluidExample.swift` with 7 usage examples plus utility examples

### Features
- **Fluid Scaling**: Ideal for typography and spacing with controlled growth
  - Linear interpolation between min/max values
  - Smooth transitions based on screen width
  - Perfect for responsive fonts, line heights, and fluid spacing
  - Complementary to existing Fixed (logarithmic) and Dynamic (proportional) models
  - Provides explicit min/max bounds unlike Fixed's calculated growth
  - **SwiftUI only** - optimized for SwiftUI's declarative syntax

### Documentation
- Updated README with Fluid model section including formulas and comparisons
- Added comparison table: Fluid vs Fixed
- Added usage examples for all fluid features
- SwiftUI-specific examples and best practices

### Platform Support
- iOS 13.0+
- macOS 10.15+
- tvOS 13.0+
- watchOS 6.0+

### Philosophy
- **When to use Fluid**: Typography, line heights, letter spacing, fluid padding/margins
- **When to use Fixed**: UI elements, buttons, icons, general spacing (RECOMMENDED)
- **When to use Dynamic**: Large containers, full-width layouts (specific cases)

## [1.0.9] - 2025-10-31

### Changed (Breaking)
- **BREAKING**: Unified formula structure with all platforms
  - Changed from direct division (`dimension / BASE_WIDTH`) to subtraction + step (`(dimension - BASE_WIDTH) / STEP`)
  - Adjusted `BASE_INCREMENT`: 0.1 → 0.003333 (0.1/30)
  - Adjusted `DEFAULT_SENSITIVITY_K`: 0.5 → 0.002667 (0.08/30)
  - Changed `REFERENCE_AR`: 0.56 (portrait) → 1.78 (landscape)
  - Added `INCREMENT_DP_STEP`: 1.0 (unified step size)
  - Normalized aspect ratio to landscape (largest/smallest)
- **BREAKING**: Unified `BASE_WIDTH_PT`: 375 → 300
  - Changed to match Android/Web for exact value compatibility
  - All platforms now produce **100% identical values**
  - CV (coefficient of variation) between platforms: **0.00%**

### Impact
- **Breaking changes:** -9% to -51% depending on device size (vs original iOS)
- **Small devices (300-360dp):** Changes of -27% to -35%
- **iPhone reference (375dp):** Changes of -25% to +14%
- **Large devices (600-960dp):** Changes of -4% to +17%

### Warning
- ⚠️ **EXTENSIVE TESTING REQUIRED** for iOS applications
- ⚠️ Layouts may need manual adjustments
- ⚠️ Consider testing on multiple device sizes before deployment

### Improved
- Mathematical structure now consistent with Android, Web, React Native, and Flutter
- Unified formula: `1.0 + ((dimension - BASE_WIDTH) / 1) × (0.003333 + 0.002667 × ln(AR / 1.78))`
- Better predictability with logarithmic smoothing
- Maximum granularity (1pt step)
- **100% identical values** with Android/Web/React Native/Flutter (CV = 0.00%)

### Updated
- Formula structure unified across all platforms
- Aspect ratio normalization improved
- BASE_WIDTH unified to 300pt for exact compatibility
- Documentation updated with unified formula

## [1.0.8] - 2025-01-16

### Changed
- Updated version to 1.0.8 across all modules
- Updated AppDimens.podspec version to 1.0.8
- Updated Package.swift dependency references
- Updated documentation to reflect new version

## [1.0.5] - 2025-01-15

### Changed
- Updated version to 1.0.5 across all modules
- Updated documentation to reflect new version

## [1.0.0] - 2025-01-15

### Added
- Initial release of AppDimens iOS library
- Core dimension management system with Fixed (FX) and Dynamic (DY) models
- Support for both UIKit and SwiftUI
- Device type detection (Phone, Tablet, Watch, TV, CarPlay)
- Screen qualifier system for custom dimension values
- Aspect ratio adjustment for refined scaling
- Multi-window mode detection and handling
- Comprehensive extension methods for easy integration
- Physical unit conversion (mm, cm, inches to points)
- Percentage-based dimension calculations
- Layout utility functions
- Complete documentation and examples
- CocoaPods integration
- Unit tests and integration tests

### Features
- **Fixed Scaling**: Logarithmic adjustment for UI elements (buttons, paddings, fonts)
- **Dynamic Scaling**: Proportional adjustment for layout containers
- **Device-Specific Values**: Custom dimensions for different device types
- **Screen Qualifiers**: Fine-grained control over dimension values
- **Aspect Ratio Adjustment**: Smooth scaling on extreme aspect ratios
- **Multi-Window Support**: Proper handling of split-screen mode
- **SwiftUI Integration**: Native SwiftUI extensions and modifiers
- **UIKit Integration**: UIView and control extensions
- **Performance Optimized**: Cached calculations and minimal overhead

### API Highlights
```swift
// Fixed scaling for UI elements
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// Dynamic scaling for layouts
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt

// SwiftUI integration
Text("Hello")
    .font(.fxSystem(size: 16))
    .fxPadding(16)
    .fxCornerRadius(8)

// UIKit integration
button.fxTitleFontSize(16)
view.fxCornerRadius(8)
```

### Compatibility
- iOS 13.0+
- Swift 5.0+
- Xcode 12.0+
- CocoaPods 1.10.0+

### Documentation
- Complete README with usage examples
- Technical documentation with API reference
- UIKit and SwiftUI example projects
- Migration guide from Android version
- Performance optimization guidelines
