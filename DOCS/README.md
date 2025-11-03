# 📚 AppDimens Documentation Index

Welcome to the complete AppDimens v2.0 documentation. This page serves as a central index to all documentation resources.

> **Languages:** English | [Português (BR)](../LANG/pt-BR/DOCS_QUICK_REFERENCE.md) | [Español](../LANG/es/DOCS_QUICK_REFERENCE.md)

---

## 🆕 What's New in Version 2.0

**Major Updates:**
- 🎯 **13 Scaling Strategies** (up from 2)
- ⭐ **BALANCED** - New primary recommendation (hybrid linear-logarithmic)
- 🔬 **Perceptual Models** (Weber-Fechner, Stevens' Power Law)
- 🧠 **Smart Inference** - Automatic strategy selection
- ⚡ **5x Performance** - Lock-free cache, Ln() lookup table
- ♻️ **Full Backward Compatibility** - v1.x code still works

---

## 🚀 Quick Start

New to AppDimens? Start here:

1. **[Main README](../README.md)** - Project overview and installation
2. **[Quick Reference](DOCS_QUICK_REFERENCE.md)** ⚡ - Find anything in seconds
3. **[Simplified Guide](MATHEMATICAL_THEORY_SIMPLIFIED.md)** - Understand in 15 minutes
4. **[Examples](EXAMPLES.md)** - Ready-to-use code

---

## 📖 Core Documentation

### For Beginners

| Document | Description | Time |
|----------|-------------|------|
| [Simplified Theory](MATHEMATICAL_THEORY_SIMPLIFIED.md) | Easy-to-understand explanation | 15 min |
| [Examples](EXAMPLES.md) | Real-world code samples (5 platforms) | 20 min |
| [Quick Reference](DOCS_QUICK_REFERENCE.md) | Quick navigation guide | 5 min |

**Recommended Path:** Simplified Theory → Examples → Your Platform Guide

### For Developers

| Document | Description | Time |
|----------|-------------|------|
| [Complete Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) | Everything in one place | 2 hours |
| [Mathematical Theory](MATHEMATICAL_THEORY.md) | Formal mathematical foundation | 45 min |
| [Formula Comparison](FORMULA_COMPARISON.md) | Scientific analysis & rankings (13 strategies + 7 external) | 30 min |
| [Validation Report](VALIDATION_REPORT.md) | Implementation validation | 20 min |
| [Base Orientation Guide](BASE_ORIENTATION_GUIDE.md) | Auto-rotation adaptation | 15 min |
| [Applicability Guide](APPLICABILITY_OF_APPDIMENS.md) | When and where to use AppDimens | 20 min |

**Recommended Path:** Mathematical Theory → Formula Comparison → Technical Guide

---

## 🎯 Platform-Specific Guides

### Android

- **[Android README](../Android/README.md)** - Complete Android guide
  - Jetpack Compose, View System, Data Binding
  - 13 scaling strategies with Kotlin extensions
  - Physical Units (mm, cm, inch)
  
**Modules:**
- **[Dynamic Module](../Android/appdimens_dynamic/README.md)** - Core library (Fixed + Dynamic + Physical Units)
- **[Games Module](../Android/appdimens_games/README.md)** - C++/NDK game development
- **[SDP Module](../Android/appdimens_sdps/README.md)** - Scalable DP for XML
- **[SSP Module](../Android/appdimens_ssps/README.md)** - Scalable SP for text in XML
- **[All Module](../Android/appdimens_all/README.md)** - All-in-one (dynamic + sdps + ssps)

### iOS

- **[iOS README](../iOS/README.md)** - Complete iOS guide
  - SwiftUI and UIKit support
  - 13 scaling strategies
  - Metal game development
  
**Additional Docs:**
- [Installation Guide](../iOS/INSTALLATION.md) - iOS installation
- [Usage Guide](../iOS/USAGE_GUIDE.md) - iOS usage examples
- [Documentation](../iOS/DOCUMENTATION.md) - Detailed iOS documentation
- [Modular Guide](../iOS/README_MODULAR.md) - Modular architecture

### Flutter

- **[Flutter README](../Flutter/README.md)** - Complete Flutter guide
  - 13 scaling strategies with extensions
  - Base orientation support
  - Smart API with auto-inference
  
**Additional Docs:**
- [Implementation Summary](../Flutter/IMPLEMENTATION_SUMMARY.md) - Technical implementation details
- [Example App](../Flutter/example/README.md) - Example application

### React Native

- **[React Native README](../ReactNative/README.md)** - Complete React Native guide
  - TypeScript support
  - Custom hooks (useAppDimens)
  - 13 scaling strategies
  - Smart API

### Web

- **[Web README](../Web/README.md)** - Complete Web guide
  - Vanilla JS, React, Vue, Svelte, Angular
  - TypeScript typings
  - CDN and NPM support
  
**Additional Docs:**
- [Quick Start](../Web/QUICK_START.md) - Web quick start
- [Testing Guide](../Web/HOWTO_TEST.md) - How to test WebDimens

---

## 🔬 Technical Resources

### Theory & Research

| Resource | Description |
|----------|-------------|
| [Mathematical Theory](MATHEMATICAL_THEORY.md) | Formal mathematical foundation, 13 strategies detailed |
| [Simplified Theory](MATHEMATICAL_THEORY_SIMPLIFIED.md) | Easy-to-understand version (15min) |
| [Formula Comparison](FORMULA_COMPARISON.md) | Comparison of 13 AppDimens strategies + 7 external approaches |
| [Validation Report](VALIDATION_REPORT.md) | Test results and validation |

### Implementation

| Resource | Description |
|----------|-------------|
| [Comprehensive Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) | Complete technical documentation (2h read) |
| [Examples](EXAMPLES.md) | Code examples for all 5 platforms |
| [Quick Reference](DOCS_QUICK_REFERENCE.md) | Fast navigation and lookup |
| [Applicability Guide](APPLICABILITY_OF_APPDIMENS.md) | When and where to use AppDimens |

---

## 🌍 Multilingual Documentation

### Português (Brasil)
- [README em Português](../LANG/pt-BR/README.md)
- [Teoria Matemática](../LANG/pt-BR/MATHEMATICAL_THEORY.md)
- [Teoria Simplificada](../LANG/pt-BR/MATHEMATICAL_THEORY_SIMPLIFIED.md)
- [Comparação de Fórmulas](../LANG/pt-BR/FORMULA_COMPARISON.md)
- [Documentação Completa](../LANG/pt-BR/)

### Español
- [README en Español](../LANG/es/README.md)
- [Teoría Matemática](../LANG/es/MATHEMATICAL_THEORY.md)
- [Teoría Simplificada](../LANG/es/MATHEMATICAL_THEORY_SIMPLIFIED.md)
- [Comparación de Fórmulas](../LANG/es/FORMULA_COMPARISON.md)
- [Documentación Completa](../LANG/es/)

---

## 🎓 Learning Paths

### Path 1: Quick Implementation (30 minutes)

**Goal:** Get AppDimens working in your app ASAP

1. [Main README](../README.md) - Overview (5 min)
2. [Platform Guide](../Android/README.md) - Choose your platform (10 min)
3. [Examples](EXAMPLES.md) - Copy and adapt (15 min)

**Quick Start Code:**
```kotlin
// Android - Just use .balanced() everywhere! ⭐
Text("Hello", fontSize = 16.balanced().sp)
Button(modifier = Modifier.height(48.balanced().dp))
```

---

### Path 2: Understanding & Best Practices (2 hours)

**Goal:** Understand why and when to use each strategy

1. [Simplified Theory](MATHEMATICAL_THEORY_SIMPLIFIED.md) - Understand concepts (15 min)
2. [Examples](EXAMPLES.md) - See it in action (20 min)
3. [Formula Comparison](FORMULA_COMPARISON.md) - Why AppDimens is better (30 min)
4. [Complete Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) - Master everything (1 hour)

**Key Takeaways:**
- ✅ Use **BALANCED** for 95% of apps (primary)
- ✅ Use **DEFAULT** for phone-only apps (secondary)
- ✅ Use **PERCENTAGE** for large containers only
- ✅ 13 strategies cover all use cases

---

### Path 3: Research & Academic (4 hours)

**Goal:** Deep mathematical understanding and academic knowledge

1. [Mathematical Theory](MATHEMATICAL_THEORY.md) - Formal theory (45 min)
2. [Formula Comparison](FORMULA_COMPARISON.md) - Scientific comparison (30 min)
3. [Validation Report](VALIDATION_REPORT.md) - Test results (20 min)
4. [Complete Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) - Complete reference (2 hours)

**For:**
- Researchers and academics
- Contributors to the project
- Advanced customization needs
- Performance optimization

---

## 🔍 Find by Topic

### Scaling Strategies

#### Primary Recommendations
- [BALANCED Strategy](MATHEMATICAL_THEORY_SIMPLIFIED.md#3-primary-balanced-strategy-recommended) ⭐ (#1, 93/100)
- [DEFAULT Strategy](MATHEMATICAL_THEORY_SIMPLIFIED.md#4-secondary-default-strategy-phone-focused) (#4, 82/100)

#### Additional Strategies
- [LOGARITHMIC](MATHEMATICAL_THEORY.md#41-logarithmic-strategy-pure-weber-fechner) - TV/Large tablets (#2, 88/100)
- [POWER](MATHEMATICAL_THEORY.md#42-power-strategy-stevens-law) - Configurable (#3, 86/100)
- [PERCENTAGE](MATHEMATICAL_THEORY.md#51-percentage-strategy-formerly-dynamic) - Large containers (#16, 62/100)
- [FLUID](MATHEMATICAL_THEORY.md#52-fluid-strategy-css-clamp-like) - Typography (#5, 78/100)
- [AUTOSIZE 🆕](MATHEMATICAL_THEORY.md#581-autosize-strategy-container-aware) - Dynamic text (#6, 78/100)
- [FIT/FILL](MATHEMATICAL_THEORY.md#56-fit-strategy-game-letterbox) - Games (#7/#8, 75/73)
- [INTERPOLATED](MATHEMATICAL_THEORY.md#53-interpolated-strategy-moderate-linear) - Moderate (#11, 70/100)
- [DIAGONAL/PERIMETER](MATHEMATICAL_THEORY.md#54-diagonal-strategy-screen-size) - Physical (#9/#12, 72/70)
- [NONE](MATHEMATICAL_THEORY.md#59-none-strategy-no-scaling) - Fixed sizes

### Implementation

- [Android Examples](EXAMPLES.md#android-examples)
- [iOS Examples](EXAMPLES.md#ios-examples)
- [Flutter Examples](EXAMPLES.md#flutter-examples)
- [React Native Examples](EXAMPLES.md#react-native-examples)
- [Web Examples](EXAMPLES.md#web-examples)

### Theory & Science

- [Weber-Fechner Law](MATHEMATICAL_THEORY.md#41-logarithmic-strategy-pure-weber-fechner)
- [Stevens' Power Law](MATHEMATICAL_THEORY.md#42-power-strategy-stevens-law)
- [Logarithmic Scaling](MATHEMATICAL_THEORY.md#logarithmic-scaling)
- [Aspect Ratio Compensation](MATHEMATICAL_THEORY.md#32-complete-mathematical-formulation)

### Performance

- [v2.0 Optimizations](MATHEMATICAL_THEORY.md#7-mathematical-optimizations-v20)
- [Cache System](MATHEMATICAL_THEORY.md#73-unified-lock-free-cache)
- [Ln() Lookup Table](MATHEMATICAL_THEORY.md#71-ln-lookup-table-10-20x-faster)
- [Performance Benchmarks](FORMULA_COMPARISON.md#5-performance-analysis)

### Features

- [Smart Inference](MATHEMATICAL_THEORY.md#6-smart-inference-system-v20)
- [Base Orientation](BASE_ORIENTATION_GUIDE.md) - Auto-rotation support
- [Physical Units](COMPREHENSIVE_TECHNICAL_GUIDE.md#physical-units)
- [Game Development](../Android/appdimens_games/README.md) - C++/NDK and Metal

---

## 📊 Key Achievements (v2.0)

- 🥇 **#1 Strategy**: BALANCED (93/100) - Best for multi-device apps
- 🥈 **#2 Strategy**: LOGARITHMIC (88/100) - Maximum control on TVs
- 🥉 **#3 Strategy**: POWER (86/100) - General purpose
- 🎯 **40% oversizing reduction** on tablets (vs linear scaling)
- 🔬 **Only library** with perceptual models (Weber-Fechner, Stevens)
- 🎨 **Only library** with aspect ratio compensation (DEFAULT strategy)
- ⚡ **5x performance** improvement (v1.x → v2.0)
- 🧠 **Smart Inference** - Automatic strategy selection
- 🌍 **5 platforms** - Android, iOS, Flutter, React Native, Web

**[See Full Comparison](FORMULA_COMPARISON.md)**

---

## 📐 Version 2.0 Strategy Overview

### Primary Recommendation: BALANCED ⭐

**Use for:** 95% of applications, especially multi-device apps

```kotlin
// Android
Text("Hello", fontSize = 16.balanced().sp)
Button(modifier = Modifier.height(48.balanced().dp))
```

**Why BALANCED?**
- ✅ Linear on phones (< 480dp): Familiar behavior
- ✅ Logarithmic on tablets/TVs (≥ 480dp): Controls oversizing
- ✅ 40% reduction vs linear on tablets
- ✅ Smooth transition at 480dp
- ✅ Best-in-class (93/100 score)

**📖 [Learn More About BALANCED](MATHEMATICAL_THEORY_SIMPLIFIED.md#3-primary-balanced-strategy-recommended)**

### Secondary Recommendation: DEFAULT

**Use for:** Phone-focused apps, backward compatibility with v1.x

```kotlin
// Android
Icon(modifier = Modifier.size(24.defaultDp))
```

**Why DEFAULT?**
- ✅ ~97% linear + 3% AR compensation
- ✅ Optimized for phone range (320-480dp)
- ✅ Backward compatible with AppDimens v1.x
- ✅ Good score (82/100)

**📖 [Learn More About DEFAULT](MATHEMATICAL_THEORY_SIMPLIFIED.md#4-secondary-default-strategy-phone-focused)**

### Other Strategies (Specific Use Cases)

| Strategy | Score | Use Case |
|----------|-------|----------|
| **LOGARITHMIC** | 88/100 | TV apps, large tablets, maximum control |
| **POWER** | 86/100 | General purpose, configurable |
| **FLUID** | 78/100 | Typography with bounds |
| **AUTOSIZE** 🆕 | 78/100 | Dynamic text, auto-sizing |
| **PERCENTAGE** | 62/100 | Large containers, images |
| **FIT / FILL** | 75/73 | Game development |

**📖 [Complete Strategy Comparison](FORMULA_COMPARISON.md)**

---

## 🔬 Technical Documentation

### Mathematical Foundation

#### Core Theory
- **[Mathematical Theory](MATHEMATICAL_THEORY.md)** 📐 - Complete mathematical foundation
  - Formal definitions of all 13 strategies
  - Psychophysics foundation (Weber-Fechner, Stevens)
  - Smart Inference algorithm
  - Mathematical optimizations (5x performance)
  - Academic references
  - **Read time:** 45 minutes

- **[Simplified Theory](MATHEMATICAL_THEORY_SIMPLIFIED.md)** 📖 - Easy version
  - Quick introduction (15 minutes)
  - Visual comparisons
  - Practical examples
  - Decision guide
  - **Read time:** 15 minutes

#### Comparative Analysis
- **[Formula Comparison](FORMULA_COMPARISON.md)** 📊 - Scientific rankings
  - 13 AppDimens strategies compared
  - 7 external libraries analyzed
  - Complete numerical tables
  - Performance benchmarks
  - Rankings by use case
  - **Read time:** 30 minutes

#### Validation
- **[Validation Report](VALIDATION_REPORT.md)** ✅ - Test results
  - Implementation validation
  - Cross-platform consistency
  - Performance measurements
  - Test coverage
  - **Read time:** 20 minutes

### Implementation Guides

- **[Comprehensive Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md)** 🔬
  - Everything in one document
  - All 13 strategies detailed
  - API reference for all platforms
  - Advanced features
  - Performance optimization
  - Best practices
  - **Read time:** 2 hours (reference document)

- **[Examples](EXAMPLES.md)** 💻
  - Real-world code samples
  - All 5 platforms (Android, iOS, Flutter, RN, Web)
  - Common patterns
  - Advanced use cases
  - **Read time:** 20 minutes

- **[Quick Reference](DOCS_QUICK_REFERENCE.md)** ⚡
  - Fast lookup
  - API cheat sheet
  - Strategy selector
  - Common patterns
  - **Read time:** 5 minutes

### Special Topics

- **[Base Orientation Guide](BASE_ORIENTATION_GUIDE.md)** 🔄
  - Auto-rotation adaptation
  - Portrait/Landscape handling
  - Design once, auto-adapt
  - **Read time:** 15 minutes

- **[Applicability Guide](APPLICABILITY_OF_APPDIMENS.md)** 🎯
  - When to use AppDimens
  - When NOT to use AppDimens
  - Alternative approaches
  - Decision criteria
  - **Read time:** 20 minutes

---

## 📱 Platform Documentation Structure

### Android Documentation Tree

```
Android/
├── README.md (Main guide)
├── appdimens_dynamic/
│   └── README.md (Core library: Fixed, Dynamic, Physical Units)
├── appdimens_games/
│   └── README.md (C++/NDK game development)
├── appdimens_sdps/
│   └── README.md (Scalable DP for XML)
├── appdimens_ssps/
│   └── README.md (Scalable SP for XML)
├── appdimens_all/
│   └── README.md (All-in-one package)
└── DOCS/ (Auto-generated API docs)
```

### iOS Documentation Tree

```
iOS/
├── README.md (Main guide)
├── DOCUMENTATION.md (Detailed API reference)
├── INSTALLATION.md (Installation guide)
├── USAGE_GUIDE.md (Usage examples)
├── README_MODULAR.md (Modular architecture)
└── DOCS/
    └── README.md (Additional resources)
```

### Flutter Documentation Tree

```
Flutter/
├── README.md (Main guide)
├── IMPLEMENTATION_SUMMARY.md (Technical details)
└── example/
    └── README.md (Example app guide)
```

### React Native & Web Documentation

```
ReactNative/
├── README.md (Main guide)
└── PROMPT_REACT_NATIVE.md (Development prompts)

Web/
├── README.md (Main guide)
├── QUICK_START.md (Quick start)
└── HOWTO_TEST.md (Testing guide)
```

---

## 🧭 Navigation Guide

### By Experience Level

#### Beginner (New to AppDimens)
1. Start: [Simplified Theory](MATHEMATICAL_THEORY_SIMPLIFIED.md)
2. Practice: [Examples](EXAMPLES.md)
3. Implement: [Your Platform Guide](#platform-specific-guides)

#### Intermediate (Know basics, want depth)
1. [Formula Comparison](FORMULA_COMPARISON.md) - Understand all strategies
2. [Complete Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) - Master the API
3. [Platform Guide](#platform-specific-guides) - Platform-specific features

#### Advanced (Need customization)
1. [Mathematical Theory](MATHEMATICAL_THEORY.md) - Understand the math
2. [Complete Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) - All features
3. Source code - Dive into implementation

### By Task

#### "I want to install AppDimens"
→ [Main README](../README.md#-installation)

#### "I want to understand which strategy to use"
→ [Quick Decision Guide](MATHEMATICAL_THEORY_SIMPLIFIED.md#6-quick-decision-guide)

#### "I want code examples"
→ [Examples](EXAMPLES.md)

#### "I want to understand the math"
→ [Simplified Theory](MATHEMATICAL_THEORY_SIMPLIFIED.md) (15min) or
→ [Mathematical Theory](MATHEMATICAL_THEORY.md) (45min)

#### "I want to compare with SDP/SSP"
→ [Formula Comparison](FORMULA_COMPARISON.md#31-sdpssp-intuit---linear-proportional)

#### "I want to migrate from v1.x"
→ [Migration Guide](MATHEMATICAL_THEORY_SIMPLIFIED.md#9-migration-from-v1x)

#### "I want performance data"
→ [Performance Analysis](FORMULA_COMPARISON.md#5-performance-analysis)

#### "I want game development features"
→ [Android Games](../Android/appdimens_games/README.md) or
→ [iOS Games](../iOS/README.md#game-development-features)

---

## 📈 Documentation Statistics

### Version 2.0 Updates

- **Total Documents:** 81 markdown files
- **Languages:** 3 (English, Português, Español)
- **Platforms:** 5 (Android, iOS, Flutter, React Native, Web)
- **Strategies Documented:** 13
- **Code Examples:** 100+
- **Comparison Tables:** 50+

### Documentation Coverage

| Topic | Documents | Pages |
|-------|-----------|-------|
| **Core Theory** | 4 | ~150 |
| **Platform Guides** | 23 | ~300 |
| **Examples** | 1 | ~80 |
| **Multilingual** | 24 | ~400 |
| **Auto-generated** | 536 | ~2000 |
| **Total** | **81+** | **~2930** |

---

## 🤝 Contributing to Documentation

Want to improve the documentation?

**Easy Contributions:**
- 📝 Fix typos and grammar
- 🌍 Add translations
- 💡 Suggest clarifications
- ✨ Add examples

**Technical Contributions:**
- 📊 Add diagrams and visualizations
- 🔬 Expand mathematical analysis
- ⚡ Add performance benchmarks
- 🎨 Improve formatting

**📖 [Contributing Guidelines](../CONTRIBUTING.md)**

---

## 📞 Need Help?

### Quick Help Resources

1. **Check Documentation** - Start with [Quick Reference](DOCS_QUICK_REFERENCE.md)
2. **See Examples** - Browse [Examples](EXAMPLES.md)
3. **Read FAQ** - Common questions answered
4. **Search Issues** - Check [existing issues](https://github.com/bodenberg/appdimens/issues)

### Community Support

- 💬 [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions) - Ask questions
- 🐛 [Report Issues](https://github.com/bodenberg/appdimens/issues) - Bug reports
- 🌟 [Feature Requests](https://github.com/bodenberg/appdimens/discussions) - Suggest features

---

## 🗺️ Documentation Roadmap

### Version 2.1 (Planned)

- [ ] Video tutorials (YouTube)
- [ ] Interactive demos (Web)
- [ ] Figma plugin documentation
- [ ] Additional language translations

### Version 2.2 (Future)

- [ ] Advanced customization guides
- [ ] Migration tools documentation
- [ ] Performance profiling guides
- [ ] Visual design guidelines

---

## 📄 License

Apache License 2.0 - see [LICENSE](../LICENSE) file

---

## 🏆 Documentation Awards

- ✅ **Most comprehensive** scaling library documentation
- ✅ **Only library** with formal mathematical theory
- ✅ **Only library** documenting 13+ strategies
- ✅ **Only library** with psychophysics foundation
- ✅ **Only library** with multilingual support (3 languages)

---

**Last updated:** February 2025  
**Version:** 2.0.0  
**Author:** [Jean Bodenberg](https://github.com/bodenberg)

---

<div align="center">

**[⬆ Back to Top](#-appdimens-documentation-index)**

Made with ❤️ for developers worldwide

[Main README](../README.md) • [Quick Start](MATHEMATICAL_THEORY_SIMPLIFIED.md) • [Examples](EXAMPLES.md)

</div>
