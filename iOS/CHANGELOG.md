# Changelog

> Languages: [Português (BR)](../LANG/pt-BR/iOS/CHANGELOG.md) | [Español](../LANG/es/iOS/CHANGELOG.md) | [हिन्दी](../LANG/hi/iOS/CHANGELOG.md) | [Русский](../LANG/ru/iOS/CHANGELOG.md) | [中文](../LANG/zh/iOS/CHANGELOG.md) | [日本語](../LANG/ja/iOS/CHANGELOG.md)

All notable changes to the AppDimens iOS library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.6] - 2025-01-16

### Changed
- Updated version to 1.0.6 across all modules
- Updated AppDimens.podspec version to 1.0.6
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
