# 📝 Changelog

> Languages: [Português (BR)](LANG/pt-BR/CHANGELOG.md) | [Español](LANG/es/CHANGELOG.md) | [हिन्दी](LANG/hi/CHANGELOG.md) | [Русский](LANG/ru/CHANGELOG.md) | [中文](LANG/zh/CHANGELOG.md) | [日本語](LANG/ja/CHANGELOG.md)

All notable changes to AppDimens will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- **Comprehensive Documentation Update**: Complete analysis and documentation of all Android and iOS libraries
- **Architecture Overview**: Detailed documentation of module structure and dependencies
- **Performance Analysis**: Detailed performance characteristics and optimization guidelines
- **Advanced Configuration Examples**: Screen qualifiers, caching, and performance settings
- **Game Development Integration**: Complete C++/NDK and Metal integration examples
- **Platform Support Matrix**: Detailed compatibility information for all supported platforms
- **Multilingual Documentation**: Enhanced Portuguese, Spanish, Hindi, Russian, Chinese, and Japanese documentation

### Changed
- **Documentation Standards**: Updated all documentation to professional standards with comprehensive examples
- **API Documentation**: Enhanced API reference with detailed examples and use cases
- **Installation Guides**: Improved installation instructions for all modules and platforms
- **Code Examples**: Updated and expanded code examples with real-world scenarios

### Fixed
- **Documentation Consistency**: Resolved inconsistencies across all documentation files
- **Missing Information**: Added missing examples and explanations in all modules
- **Formatting Issues**: Standardized formatting across all README and documentation files
- **Version Information**: Updated version information across all modules to 1.0.6

## [1.0.6] - 2025-01-16

### Added
- **Game Development Support**: New AppDimens Games module for Android with C++/NDK support
- **iOS Metal Integration**: Game-specific functionality with Metal and MetalKit support
- **Game Dimension Types**: DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY for specialized game scaling
- **Vector Operations**: GameVector2D and GameRectangle classes for game mathematics
- **Viewport Management**: Multiple scaling modes for different game scenarios
- **SwiftUI Game Extensions**: Game-specific SwiftUI extensions and environment integration
- **Performance Monitoring**: Native performance monitoring for game development
- **OpenGL Utilities**: OpenGL ES utilities for Android game rendering

### Changed
- Updated version to 1.0.6 across all Android and iOS modules
- Enhanced documentation with game development examples
- Improved API consistency across platforms
- Updated installation instructions for game modules

### Technical Improvements
- **Android**: Native C++ implementation for high-performance game calculations
- **iOS**: SIMD extensions for optimized vector operations
- **Cross-Platform**: Consistent API design between Android and iOS game modules
- Updated dependency examples in all documentation files
- Updated version badges in all README files

### Android
- Updated all build.gradle.kts files with new version coordinates
- Updated Maven publication coordinates for all modules
- Updated README files with new dependency versions

### iOS
- Updated AppDimens.podspec version to 1.0.6
- Updated Package.swift dependency references
- Updated README files with new version references

### Documentation
- Updated main README.md with version 1.0.6
- Updated Android/README.md with new dependency versions
- Updated iOS/README.md with new version references
- Updated all language-specific README files

## [1.0.5] - 2025-01-15

### Changed
- Updated version to 1.0.5 across all Android and iOS modules
- Updated documentation to include games module dependency information
- Clarified that games module is not included in appdimens-all package

## [1.0.4] - 2024-01-15

### Added
- **Android Games Module**: Complete C++/NDK integration for game development
- **iOS Environment System**: Enhanced SwiftUI integration with DimensProvider
- **Physical Units**: Support for mm, cm, inch conversions on both platforms
- **Performance Monitoring**: Built-in performance monitoring for games module
- **Advanced Conditional Scaling**: Priority-based scaling rules for different device types

### Enhanced
- **Android SDP/SSP**: Improved conditional logic with priority system
- **iOS Extensions**: Enhanced SwiftUI and UIKit extensions
- **Documentation**: Comprehensive documentation for all modules
- **Examples**: Extensive examples for all use cases

### Fixed
- Memory leaks in dimension calculations
- Performance issues with large dimension sets
- Compatibility issues with older Android versions
- iOS deployment target warnings

## [1.0.3] - 2024-01-10

### Added
- **iOS Support**: Complete iOS implementation with SwiftUI and UIKit
- **Cross-Platform Examples**: Examples for both Android and iOS
- **Migration Guide**: Guide for migrating from Android to iOS
- **Enhanced Documentation**: Comprehensive documentation for both platforms

### Enhanced
- **Android Performance**: Improved calculation performance
- **iOS Integration**: Better SwiftUI and UIKit integration
- **Documentation**: Updated documentation with new features

### Fixed
- iOS compilation issues
- Android memory management
- Documentation inconsistencies

## [1.0.2] - 2024-01-05

### Added
- **SDP Module**: Advanced SDP scaling with conditional logic
- **SSP Module**: Advanced SSP scaling with conditional logic
- **Physical Units**: Support for physical unit conversions
- **Layout Utilities**: Advanced layout calculation utilities

### Enhanced
- **Conditional Scaling**: Improved conditional scaling system
- **Performance**: Better performance for large dimension sets
- **Documentation**: Enhanced documentation with examples

### Fixed
- SDP/SSP resource generation issues
- Performance bottlenecks in dimension calculations
- Memory leaks in conditional scaling

## [1.0.1] - 2024-01-01

### Added
- **All-in-One Module**: Combined module with all features
- **Enhanced Examples**: More comprehensive examples
- **Performance Optimizations**: Improved calculation performance
- **Better Documentation**: Enhanced documentation and guides

### Enhanced
- **Core Performance**: Faster dimension calculations
- **Memory Usage**: Reduced memory footprint
- **API Consistency**: More consistent API across modules

### Fixed
- Memory leaks in dimension calculations
- Performance issues with large dimension sets
- API inconsistencies between modules

## [1.0.0] - 2023-12-25

### Added
- **Core Library**: AppDimens Dynamic module with Fixed and Dynamic scaling
- **Android Support**: Complete Android implementation
- **Jetpack Compose**: Full Compose integration
- **XML Views**: XML and Data Binding support
- **Mathematical Models**: Fixed (logarithmic) and Dynamic (proportional) scaling
- **Device Support**: Support for phones, tablets, TVs, cars, and wearables
- **Performance**: Optimized calculations with cached factors
- **Documentation**: Comprehensive documentation and examples

### Features
- **Fixed Scaling**: Logarithmic adjustment for refined UI elements
- **Dynamic Scaling**: Proportional adjustment for layout containers
- **Screen Qualifiers**: Support for different screen dimensions
- **Device Types**: Custom scaling for different device types
- **Aspect Ratio**: Aspect ratio adjustment for extreme screen proportions
- **Multi-Window**: Multi-window mode detection and handling
- **Physical Units**: Conversion of physical units (mm, cm, inch)
- **Layout Utilities**: Advanced layout calculation utilities

### Supported Platforms
- **Android**: API 21+ (Android 5.0+)
- **Jetpack Compose**: Full support
- **XML Views**: Complete support
- **Data Binding**: Full integration

### Performance
- **Calculation Time**: ~0.001ms per dimension
- **Memory Usage**: ~50KB additional memory
- **Cached Factors**: Factors calculated once per configuration change
- **Optimized**: Minimal runtime overhead

## [0.9.0] - 2023-12-20

### Added
- **Beta Release**: Initial beta release
- **Core Functionality**: Basic Fixed and Dynamic scaling
- **Android Support**: Android implementation
- **Basic Documentation**: Initial documentation

### Known Issues
- Performance issues with large dimension sets
- Memory leaks in some scenarios
- Limited device support
- Basic documentation

## [0.8.0] - 2023-12-15

### Added
- **Alpha Release**: Initial alpha release
- **Core Algorithms**: Basic scaling algorithms
- **Android Prototype**: Android prototype implementation
- **Initial Testing**: Basic testing framework

### Known Issues
- Unstable performance
- Limited functionality
- No documentation
- Experimental features

---

## Legend

- **Added**: New features
- **Changed**: Changes in existing functionality
- **Deprecated**: Soon-to-be removed features
- **Removed**: Removed features
- **Fixed**: Bug fixes
- **Security**: Security improvements

## Version Format

We use [Semantic Versioning](https://semver.org/) for version numbers:

- **MAJOR**: Breaking changes
- **MINOR**: New features (backward compatible)
- **PATCH**: Bug fixes (backward compatible)

## Release Schedule

- **Major Releases**: Every 6-12 months
- **Minor Releases**: Every 1-3 months
- **Patch Releases**: As needed for bug fixes
- **Security Releases**: As needed for security fixes

## Support Policy

- **Current Version**: Full support
- **Previous Major Version**: Bug fixes only
- **Older Versions**: No support

## Migration Guide

For migration between major versions, see:
- [Android Migration Guide](Android/MIGRATION.md)
- [iOS Migration Guide](iOS/MIGRATION.md)

## Contributing

To contribute to AppDimens, see our [Contributing Guide](CONTRIBUTING.md).

## License

AppDimens is licensed under the Apache License 2.0. See [LICENSE](LICENSE) for details.

---

**AppDimens** - Responsive design made simple! 🚀
