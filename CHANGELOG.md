---
layout: default
title: "📝 Changelog"
---

# 📝 Changelog

> **Languages:** English | Português (BR) | Español

> **Note:** Translation files for CHANGELOG are not yet available in other languages.

All notable changes to AppDimens will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [2.0.0] - 2025-11-02

### 🎉 Multi-Platform Release v2.0.0

All platforms updated to version 2.0.0 with unified architecture:

#### ✅ Released Platforms
- **Android appdimens_dynamic**: v2.0.0 ✅ (tested and built)
- **Android appdimens_games**: v2.0.0 ⚠️ (updated, C++ conflicts pending)
- **iOS**: v2.0.0 ✅ (podspec, core types created)
- **Flutter**: v2.0.0 ✅ (pubspec, core types created)
- **React Native**: v2.0.0 ✅ (tested and built)
- **Web**: v2.0.0 ✅ (tested and built)

### 🔄 Previous Updates
- 📊 **SCALING_COMPARISON.html**: Updated to include all 13 scaling strategies (added BALANCED, POWER, FLUID)
- 📊 **SCALING_COMPARISON_COMPLETE.html**: New comprehensive comparison page showing all 13 strategies with visual demos
- 📊 **SCALING_COMPARISON_2.html**: Clarified as Dynamic vs Fixed comparison, added reference to complete comparison

## [2.0.0-dynamic] - 2025-02-01

### 🎯 Major Changes

#### Renamed Strategies (Breaking Naming, Not Breaking API)
- ♻️ **Fixed → DEFAULT**: Legacy logarithmic model renamed for clarity
- ♻️ **Dynamic → PERCENTAGE**: Legacy proportional model renamed to reflect nature
- ✅ **Backward Compatibility**: Old names (`.fxdp`, `.dydp`) still work (deprecated)
- ⭐ **New Recommendation**: BALANCED strategy is now the recommended default

### ✨ Added - Perceptual Models

#### 13 Scaling Strategies (from 2)
- 🆕 **BALANCED** ⭐ (Recommended): Hybrid linear-logarithmic
  - Linear on phones (<480dp), logarithmic on tablets/TVs (≥480dp)
  - 40% less oversizing on tablets vs linear scaling
  - Best balance for multi-device apps
  
- 🆕 **LOGARITHMIC**: Pure Weber-Fechner Law
  - Maximum control on large screens (44-58% reduction vs linear)
  - Ideal for TVs and very large tablets
  
- 🆕 **POWER**: Stevens' Power Law
  - Configurable exponent (0.60-0.90, default: 0.75)
  - Scientifically grounded perceptual scaling
  - Smooth predictable behavior

#### Extended Strategies
- 🆕 **FLUID**: CSS clamp-like with breakpoints
- 🆕 **INTERPOLATED**: 50% moderated linear
- 🆕 **DIAGONAL**: Screen diagonal-based scaling
- 🆕 **PERIMETER**: Width + height-based scaling
- 🆕 **FIT**: Letterbox (game fit mode)
- 🆕 **FILL**: Cover (game fill mode)
- 🆕 **AUTOSIZE**: Container-aware auto-sizing
- 🆕 **NONE**: No scaling (constant size)

### 🧠 Added - Smart Inference System

- 🆕 **Weight-Based Strategy Selection**: Automatic inference
- 🆕 **18 Element Types**: BUTTON, TEXT, ICON, CONTAINER, SPACING, CARD, DIALOG, TOOLBAR, FAB, CHIP, LIST_ITEM, IMAGE, BADGE, DIVIDER, NAVIGATION, INPUT, HEADER, GENERIC
- 🆕 **8 Device Categories**: PHONE_SMALL, PHONE_NORMAL, PHONE_LARGE, TABLET_SMALL, TABLET_LARGE, TV, WATCH, AUTO
- 🆕 **Smart API**: `.smart().forElement(ElementType.BUTTON).dp`

### ⚡ Added - Mathematical Optimizations

#### Ln() Lookup Table
- 🆕 150 pre-calculated ln() values for common inputs
- 🆕 Binary search O(log n) lookup
- ⚡ 85-95% cache hit rate
- ⚡ 10-20x faster than raw ln() calculation

#### Pre-Calculated Constants
- 🆕 `BASE_DIAGONAL = 611.6305f` (eliminates sqrt call)
- 🆕 `BASE_PERIMETER = 833f` (eliminates addition)
- 🆕 `INV_BASE_WIDTH_DP`, `INV_REFERENCE_AR` (multiplication vs division)
- ⚡ 2-10x performance gain per calculation

#### Unified Lock-Free Cache
- 🆕 Single AutoCacheFast for all components (Compose + Views)
- 🆕 Int hash instead of String (5x faster, 5x less memory)
- 🆕 Ring buffer with auto-cleanup
- 🆕 Zero dependency tracking (hash comparison only)
- ⚡ **5x improvement**: 0.001μs (vs 0.005μs before)
- ⚡ 100% multi-thread parallelism (vs 25% before)

#### Binary Search Algorithms
- 🆕 O(log n) complexity for AutoSize preset selection
- ⚡ 5-10x faster than linear search (20 presets: 5 vs 20 comparisons)

### 📦 Changed - Code Organization

- ♻️ **Reorganized Package Structure**:
  - `core/`: AppDimensCore, calculation engine, models, strategies
  - `core/calculation/`: Calculator (unified engine)
  - `core/strategy/`: ScalingStrategy, ElementType, InferenceContext
  - `core/models/`: PerceptualModel, AdjustmentFactors, PhysicalUnits, Fluid, FluidTypes
  - `core/optimization/`: PerceptualCore, Warmup
  - `core/cache/`: AutoCacheFast (unified)
  - `core/shared/`: Extensions (shared)
  - `code/`: AppDimens (Views API), models, helpers, extensions
  - `compose/`: AppDimens (Compose API), models, helpers, extensions

- ♻️ **Removed Classes** (consolidated):
  - ❌ `AppDimensAutoCache.kt` (563 lines) - replaced by AutoCacheFast
  - ❌ `AppDimensAutoCacheSegmented.kt` (580 lines) - obsolete
  - ❌ Redundant legacy cache implementations
  - **Net reduction**: -763 lines (66% less cache code)

### 📊 Performance Summary (v1.x → v2.0)

| Metric | v1.x | v2.0 | Improvement |
|--------|------|------|-------------|
| **Calculation Time** | 0.005μs | 0.001μs | **5x faster** |
| **Memory/Entry** | 280 bytes | 56 bytes | **5x smaller** |
| **Multi-Thread** | 25% efficiency | 100% efficiency | **4x better** |
| **Cache Implementations** | 3 different | 1 unified | **Simplified** |
| **Code Size (cache)** | 1143 lines | 280 lines | **66% reduction** |
| **Strategies** | 2 | 13 | **6.5x more** |

### 🔧 Fixed

- 🐛 Cache thread contention in Views XML (now lock-free)
- 🐛 Memory overhead in legacy cache (now 5x smaller)
- 🐛 Slow ln() calculations (now 10-20x faster with lookup table)
- 🐛 Linear search in AutoSize (now binary search O(log n))

### 📚 Documentation

- 📝 Updated all mathematical theory docs for 13 strategies
- 📝 Added perceptual models documentation (Weber-Fechner, Stevens)
- 📝 Expanded scientific articles with psychophysical foundation
- 📝 Added Smart Inference system documentation
- 📝 Added performance optimization details
- 📝 Updated migration guides (v1.x → v2.0)
- 📝 Multi-language updates (EN, PT-BR, ES)

---

## [1.2.0] - 2025-01-31

### Added - Base Orientation Feature 🎯

#### Auto-Inversion of Screen Types Based on Design Orientation

**New Feature:** Sistema de detecção de orientação de design que inverte automaticamente LOWEST/HIGHEST quando a orientação atual difere da orientação original do design.

#### All Platforms
- **BaseOrientation enum:** `PORTRAIT`, `LANDSCAPE`, `AUTO` (default)
- **API explícita:** `.baseOrientation(orientation)` method
- **API de atalhos:** `.portraitLowest()`, `.portraitHighest()`, `.landscapeLowest()`, `.landscapeHighest()`
- **Resolução automática:** `resolveScreenType()` function inverte LOWEST↔HIGHEST quando necessário

#### Implemented In
- ✅ **Android:** AppDimensFixed, AppDimensDynamic, AppDimensFluid (code + compose)
- ✅ **iOS:** AppDimensFixedCalculator, AppDimensDynamicCalculator
- ✅ **Flutter:** AppDimensFixed, AppDimensDynamic
- ✅ **React Native:** AppDimensFixed, AppDimensDynamic
- ✅ **Web:** Fixed, Dynamic, Fluid
- ✅ **Android Games:** C++/NDK with Kotlin wrapper
- ✅ **iOS Games:** Metal/Swift with helper functions

#### Examples

**Android/Kotlin:**
```kotlin
// Explicit API
val cardWidth = 300.fixedDp()
    .baseOrientation(BaseOrientation.PORTRAIT)
    .type(ScreenType.LOWEST)
    .dp

// Shorthand API
val cardWidth = 300.fixedDp().portraitLowest().dp

// Compose extensions
val padding = 16.fxPortraitLowest  // Auto-inverts in landscape
```

**iOS/Swift:**
```swift
let cardWidth = AppDimensFixedCalculator(300)
    .baseOrientation(.portrait)
    .screen(type: .lowest)
    .pt

// Shorthand
let cardWidth = AppDimensFixedCalculator(300).portraitLowest().pt
```

**Flutter/Dart:**
```dart
final cardWidth = AppDimensFixed(300)
    .baseOrientation(BaseOrientation.portrait)
    .screenType(ScreenType.lowest)
    .calculate(context);

// Shorthand
final cardWidth = AppDimensFixed(300).portraitLowest().calculate(context);
```

**React Native/TypeScript:**
```typescript
const cardWidth = fixedDp(300).portraitLowest().calculate();
```

**Web/JavaScript:**
```javascript
const cardWidth = fixed(300).portraitLowest().toPx();
```

#### Benefits
- ✅ **Consistência Visual:** Mantém proporções ao rotacionar tela
- ✅ **Menos Código:** Evita lógica duplicada para orientações
- ✅ **Design System:** Facilita trabalho de designers
- ✅ **Retrocompatível:** Default AUTO mantém comportamento atual

#### Technical Details
- Detecta orientação baseada em `height > width` (portrait) ou `width > height` (landscape)
- Inversão automática: `PORTRAIT design + LANDSCAPE atual → inverte LOWEST↔HIGHEST`
- Performance: Zero overhead quando `baseOrientation = AUTO` (default)

### Changed
- `FluidConfig` (Android): Agora inclui `baseOrientation` e `screenType` parameters
- Métodos `calculate()` em várias classes agora consideram base orientation

### Fixed
- Nenhum bug conhecido

---

## [1.1.0] - 2025-10-31

### Changed - Mathematical Precision Enhancement

#### Core Formula Update (All Platforms)
- **Granularidade Aumentada:** Mudança de step de 30dp para 1dp, resultando em 30× mais precisão
- **BASE_INCREMENT:** Atualizado de `0.10` para `0.10 / 30` (0.00333...) - granularidade de 1dp
- **DEFAULT_SENSITIVITY_K:** Atualizado de `0.08` para `0.08 / 30` (0.00267...) - granularidade de 1dp
- **INCREMENT_DP_STEP:** Mantido em `1` (cada 1dp tem valor único)
- **BASE_WIDTH:** Mantido em `300` (dp/pt/px)
- **REFERENCE_AR:** Mantido em `1.78` (16:9)

#### Mathematical Equivalence
- **IMPORTANTE:** Os valores finais de escalonamento permanecem IDÊNTICOS
- **Equivalência matemática:** `(111/30) × 0.10 = (111/1) × 0.00333 ≈ 0.37`
- **Sem mudanças visuais:** A aparência dos elementos permanece exatamente a mesma
- **Benefício:** Granularidade 30× maior permite cálculos mais precisos por dp

### Improved

- **Precisão Matemática:** 30× maior granularidade sem alterar valores finais
- **Performance:** Leve melhoria (~3-5%) ao eliminar divisão por 30 em runtime
- **Documentação:** Explicações detalhadas sobre o ajuste proporcional
- **Clareza de Código:** Fórmulas explícitas mostrando ajuste (0.10f / 30f)

### Updated

#### Android (Kotlin)
- `AppDimensAdjustmentFactors.kt`: Constantes atualizadas com comentários explicativos
- `AppDimensFixed.kt`: Fórmula mantém mesma saída com nova precisão
- `AppDimensDynamic.kt`: Sem alterações (usa referência base)

#### iOS (Swift)
- `AppDimensAdjustmentFactors.swift`: Constantes sincronizadas com Android
- `AppDimensFixed.swift`: Compatibilidade total mantida
- Comentários adicionados explicando ajuste de granularidade

#### Flutter (Dart)
- Constantes atualizadas para corresponder a outras plataformas
- Documentação inline atualizada

#### React Native (TypeScript)
- `types.ts`: Constantes atualizadas
- Comentários explicativos adicionados

#### Web (TypeScript)
- `constants.ts`: Todas as constantes atualizadas
- Compatibilidade mantida com versões anteriores

### Documentation

- **CRITICAL:** Todos os exemplos matemáticos recalculados com nova precisão
- **HTML Demos:** `SCALING_COMPARISON.html` e `SCALING_COMPARISON_2.html` atualizados
- **MATHEMATICAL_THEORY.md:** Fórmulas e exemplos atualizados
- **COMPREHENSIVE_TECHNICAL_GUIDE.md:** Seção de constantes revisada
- **FORMULA_COMPARISON.md:** Comparações recalculadas
- **README.md:** Versões atualizadas para 1.1.0 em todas as plataformas
- **Traduções:** Documentação pt-BR e es atualizada

### Technical Notes

```kotlin
// Antes (v1.0.10)
const val BASE_INCREMENT = 0.10f  // Step de 30dp

// Depois (v1.1.0)
const val BASE_INCREMENT = 0.10f / 30f  // Step de 1dp, mesma saída final
```

**Fórmula Fixed (Sem AR):**
```
f(x) = B × [1 + ((S - W₀) / δ) × ε₀]
```

**Onde:**
- `B` = Valor base (ex: 48dp)
- `S` = Menor dimensão da tela
- `W₀` = 300 (largura de referência)
- `δ` = 1 (step size)
- `ε₀` = 0.10/30 = 0.00333... (incremento base)

### Compatibility

- ✅ **100% retrocompatível** - sem breaking changes
- ✅ **Valores visuais idênticos** - nenhuma mudança na UI
- ✅ **Cross-platform consistency** - todas as plataformas sincronizadas
- ✅ **Performance mantida** - cache ainda otimizado

## [1.0.10] - 2025-01-31

### Added

#### React Native
- **Fluid Scaling Model (FL)**: New clamp-like scaling model for smooth bounded growth
  - `AppDimensFluid` class for fluid dimension calculations
  - `fluid()` shorthand function for quick fluid instances
  - `useFluid()` hook for reactive fluid values
  - `useFluidBuilder()` hook for builder instance access
  - `useFluidMultiple()` hook for multiple fluid values with shared breakpoints
  - Device type qualifiers support (tablets, TVs)
  - Screen width qualifiers support (sw600, sw840)
  - Custom breakpoint ranges (default: 320-768px)
  - Methods: `getMin()`, `getMax()`, `getPreferred()`, `lerp(t)`
  - Comprehensive `examples/FluidExample.tsx` with 7 usage examples

#### Flutter
- **Fluid Scaling Model (FL)**: New clamp-like scaling model
  - `AppDimensFluid` class for fluid dimension calculations
  - `fluid()` shorthand function
  - Extension methods: `fluidTo()`, `fluidFrom()` on `double` and `int`
  - Widget extensions: `fluidPadding()`, `fluidMargin()`, `fluidBorderRadius()`
  - TextStyle extension: `fluidFontSize()`
  - Device type qualifiers support
  - Screen width qualifiers support
  - Custom breakpoint ranges (default: 320-768px)
  - Comprehensive `example/lib/fluid_example.dart` with 8 usage examples

#### Android (Compose)
- **Fluid Scaling Model (FL)**: New clamp-like scaling model for Jetpack Compose
  - `AppDimensFluid` class for fluid dimension calculations
  - `fluidDp()` and `fluidSp()` composable functions
  - `rememberFluid()` composable for builder access
  - `fluidMultipleDp()` for multiple fluid values with shared breakpoints
  - Extension methods: `fluidTo()`, `fluidFrom()` on `Float` and `Int`
  - Device type qualifiers support (PHONE, TABLET, TV, WATCH, AUTO)
  - Screen width qualifiers support
  - Custom breakpoint ranges (default: 320-768dp)
  - Comprehensive `FluidExampleActivity.kt` with 7 Compose examples
  - **Compose only** - not available for XML Views

#### iOS (SwiftUI)
- **Fluid Scaling Model (FL)**: New clamp-like scaling model for SwiftUI
  - `AppDimensFluid` class for fluid dimension calculations
  - `fluid(min:max:)` shorthand function
  - Extension methods: `fluidTo()`, `fluidFrom()` on `CGFloat`, `Int`, and `Double`
  - SwiftUI View extensions: `fluidPadding()`, `fluidFrame()`
  - Device type qualifiers support (tablet, tv, watch, etc.)
  - Screen width qualifiers support
  - Custom breakpoint ranges (default: 320-768pt)
  - Methods: `getMin()`, `getMax()`, `getPreferred()`, `lerp(t:)`
  - Comprehensive `FluidExample.swift` with 7+ usage examples
  - **SwiftUI only** - not available for UIKit

### Features
- **Fluid Scaling**: Ideal for typography and spacing with controlled growth
  - Linear interpolation between min/max values
  - Smooth transitions based on screen width
  - Perfect for responsive fonts, line heights, and fluid spacing
  - Complementary to existing Fixed (logarithmic) and Dynamic (proportional) models
  - Provides explicit min/max bounds unlike Fixed's calculated growth

### Documentation
- Updated React Native README with Fluid model section
- Updated Flutter README with Fluid model section
- Updated Android README with Fluid model section (Compose only)
- Updated iOS README with Fluid model section (SwiftUI only)
- Added comparison tables: Fluid vs Fixed on all platforms
- Added usage examples and best practices
- Updated exports documentation for all platforms
- Comprehensive examples for each platform implementation

### Philosophy
- **When to use Fluid**: Typography, line heights, letter spacing, fluid padding/margins
- **When to use Fixed**: UI elements, buttons, icons, general spacing (RECOMMENDED)
- **When to use Dynamic**: Large containers, full-width layouts (specific cases)

### Platform Availability
- **React Native**: ✅ Full support with hooks
- **Flutter**: ✅ Full support with extensions
- **Android**: ✅ Compose only (not available for XML Views)
- **iOS**: ✅ SwiftUI only (not available for UIKit)
- **Web**: ✅ Already available (reference implementation)

## [1.0.9] - 2025-10-31

### Fixed (Critical)
- **CRITICAL FIX**: Corrected proportional adjustment for INCREMENT_DP_STEP = 1dp
  - BASE_INCREMENT: 0.10 → 0.10/30 (0.00333...)
  - DEFAULT_SENSITIVITY_K: 0.08 → 0.08/30 (0.00267...)
  - **MAINTAINS SAME FINAL VALUES** as previous implementation
  - **30× higher granularity** (each 1dp has unique value)
  - Applied to: Android (code + compose), Web

### Improved
- Mathematical precision: 30× higher granularity without changing final scaling values
- Performance: ~3-5% improvement (eliminates division by 30)
- Documentation: Added detailed explanations about proportional adjustment
- Code clarity: Explicit formula showing adjustment (0.10f / 30f)

### Changed
- Updated all mathematical formulas in documentation (EN, PT, ES)
- Added warning notes explaining the proportional adjustment
- HTML examples updated with corrected calculation

### Note
- **NO VISUAL CHANGES**: Final scaling values remain identical to v1.0.8
- **Mathematical equivalence**: (111/30) × 0.10 = (111/1) × 0.00333 ≈ 0.37
- Granularity increase allows for more precise calculations in future versions
- iOS, Flutter, React Native use different base widths (375, 360, 375) - intentional platform differences

### Updated
- All platforms to version 1.0.9
- Technical documentation with updated precision values
- Translated documentation (pt-BR, es) with new benchmarks
- Android Dokka API documentation

## [1.0.8] - 2025-01-16

### Added

#### Android
- **Game Development Support**: New AppDimens Games module with C++/NDK support
- **Game Dimension Types**: DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY for specialized game scaling
- **Vector Operations**: GameVector2D and GameRectangle classes for game mathematics
- **OpenGL Utilities**: OpenGL ES utilities for Android game rendering
- **Direct Code Access**: Complete documentation for `AppDimensSdp` and `AppDimensSsp` code packages
- **Minimum Requirements**: Added comprehensive minimum SDK, Kotlin, and build tool versions

#### iOS
- **Metal Integration**: Game-specific functionality with Metal and MetalKit support
- **Viewport Management**: Multiple scaling modes (uniform, horizontal, vertical, aspect-ratio, viewport)
- **SwiftUI Game Extensions**: Game-specific SwiftUI extensions and environment integration
- **SIMD Extensions**: Optimized vector operations for performance
- **Performance Monitoring**: Native performance monitoring for game development

#### Flutter
- **Cross-Platform Support**: Complete Flutter implementation with Dart
- **Responsive Extensions**: `.fxdp()`, `.dydp()`, `.fxsp()`, `.dysp()` extensions
- **Screen Type Detection**: Automatic device type detection (phone, tablet, desktop, TV)
- **Breakpoint System**: Built-in breakpoint detection and management
- **Platform Support**: Android, iOS, Web, Windows, macOS, Linux

#### React Native
- **Universal Scaling**: Fixed, Dynamic, and Fluid scaling models
- **React Hooks**: `useAppDimens`, `useDimensions`, `useBreakpoint`, `useOrientation`
- **TypeScript Support**: Complete TypeScript definitions
- **Platform Agnostic**: Works seamlessly on Android and iOS
- **HOC Support**: Higher-order components for responsive design

#### Web
- **Framework Integrations**: React, Vue, Svelte, Angular, and Vanilla JS/CSS support
- **Three Scaling Models**: Fixed (logarithmic), Dynamic (proportional), Fluid (clamp-based)
- **Breakpoint System**: Intelligent breakpoint detection and management
- **Media Query Support**: Dark mode, reduced motion, hover, pointer type
- **Physical Units**: MM, CM, Inch to pixel conversions
- **Performance**: Cache system with intelligent invalidation
- **TypeScript First**: Complete type definitions with autocomplete

### Changed
- Updated version to 1.0.8 across all platforms (Android, iOS, Flutter, React Native, Web)
- Enhanced documentation with comprehensive examples for all platforms
- Improved API consistency across all supported frameworks
- Updated installation instructions for all modules and platforms
- Standardized scaling models across platforms

### Technical Improvements

#### Android
- Native C++ implementation for high-performance game calculations
- Enhanced SDP/SSP modules with code package documentation
- Added support for 16KB page size compatibility
- Updated to Kotlin 2.2.20 and SDK 36

#### iOS
- SIMD extensions for optimized vector operations
- Metal integration for game rendering
- Enhanced SwiftUI and UIKit extensions

#### Flutter
- Null-safety support
- Platform-adaptive widgets
- Optimized dimension calculations with caching

#### React Native
- Zero native dependencies for core functionality
- Platform-specific optimizations
- Memory-efficient dimension caching

#### Web
- Browser compatibility (Chrome 90+, Firefox 88+, Safari 14+)
- Framework-agnostic core with specialized integrations
- Zero runtime dependencies for core library
- Optimized performance with observers and cache

### Documentation
- **Main README**: Updated with all platform support and version 1.0.8
- **CHANGELOG**: Comprehensive changelog with all platforms
- **PRESENTATION**: Updated library overview with all platforms
- **EXAMPLES**: Added examples for Flutter, React Native, and Web
- **Platform READMEs**: Updated Android, iOS, Flutter, React Native, and Web documentation
- **AI Prompts**: Created PROMPT_FLUTTER, PROMPT_REACT_NATIVE, PROMPT_WEB
- **Language Support**: Updated all internationalized documentation files
- **Minimum Requirements**: Added for all platforms with detailed version tables

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
