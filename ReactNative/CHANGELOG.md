---
layout: default
title: "Changelog"
---

# Changelog

All notable changes to the AppDimens React Native library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.10] - 2025-01-31

### Added
- **Fluid Scaling Model (FL)**: New clamp-like scaling model for smooth bounded growth
  - `AppDimensFluid` class for fluid dimension calculations
  - `fluid()` shorthand function for quick fluid instances
  - `useFluid()` hook for reactive fluid values that update on screen size changes
  - `useFluidBuilder()` hook for access to fluid instance and calculated value
  - `useFluidMultiple()` hook for multiple fluid values with shared breakpoints
  - Device type qualifiers support (e.g., different ranges for tablets/TVs)
  - Screen width qualifiers support (e.g., sw600, sw840)
  - Custom breakpoint ranges (default: 320-768px)
  - Linear interpolation between min and max values
  - Methods: `getMin()`, `getMax()`, `getPreferred()`, `lerp(t)`

### Features
- **Fluid Scaling**: Ideal for typography and spacing with controlled growth
  - Linear growth between defined min/max values
  - Smooth transitions based on screen width
  - Perfect for responsive fonts, line heights, and fluid spacing
  - Complementary to existing Fixed (logarithmic) and Dynamic (proportional) models

### Examples
- Added comprehensive `examples/FluidExample.tsx` with 7 practical examples
- Documented all fluid usage patterns in README.md

### Documentation
- Updated README with detailed Fluid model explanation
- Added comparison table: Fluid vs Fixed model
- Added examples for all fluid features
- Updated exports documentation

## [1.0.9] - 2025-10-31

### Changed
- Updated version to 1.0.9 for consistency across platforms
- Documentation updated with corrected precision values and proportional adjustment explanations
- Mathematical theory documentation now explains 1dp step granularity in detail (EN, PT, ES)

### Note
- React Native implementation already uses calculation based on BASE_WIDTH_DP = 375
- Uses BASE_INCREMENT = 0.1 with different formula approach
- This version aligns version numbers across all platforms
- **NO BREAKING CHANGES** for React Native users

## [1.0.8] - 2025-01-27

### Added
- Initial release of AppDimens React Native
- Core dimension scaling system with Fixed (FX) and Dynamic (DY) models
- React hooks for easy integration (`useAppDimens`)
- Context provider for configuration (`AppDimensProvider`)
- TypeScript support with comprehensive type definitions
- Physical unit converters (mm, cm, inch)
- Device type detection (phone, tablet, desktop, watch, TV, car)
- Screen dimension utilities
- Performance optimization with caching system
- Layout utilities for responsive grids
- Game development support with specialized scaling
- Comprehensive documentation and examples
- Basic and Game examples demonstrating usage

### Features
- **Fixed Scaling (FX)**: Logarithmic adjustment for refined scaling
- **Dynamic Scaling (DY)**: Proportional scaling for aggressive scaling
- **Percentage Scaling**: Screen-based percentage calculations
- **Physical Units**: Convert real-world measurements to screen pixels
- **Device Detection**: Automatic device type and orientation detection
- **Performance Caching**: Automatic cache management for optimal performance
- **TypeScript Support**: Full type safety and IntelliSense support
- **React Integration**: Hooks and context providers for seamless integration

### Technical Details
- Built with TypeScript 4.8+
- Compatible with React Native 0.72+
- Supports iOS 11.0+ and Android API 21+
- Zero runtime dependencies
- Optimized for performance with automatic caching
- Memory efficient with lazy initialization

### Examples
- **Basic Example**: Demonstrates basic responsive design patterns
- **Game Example**: Shows game-specific dimension scaling and UI elements

### Documentation
- Comprehensive README with usage examples
- API reference with detailed explanations
- Performance characteristics and optimization tips
- Migration guide from other dimension systems
- Contributing guidelines and code of conduct

## [Unreleased]

### Planned Features
- Design system integration
- Advanced animation support
- Performance monitoring tools
- Additional physical unit conversions
- Enhanced game development features
- Web platform support
- Advanced caching strategies
- Real-time dimension debugging tools
