# 📚 AppDimens: Complete Technical Guide

> **Languages:** English | [Português (BR)](../LANG/pt-BR/COMPREHENSIVE_TECHNICAL_GUIDE.md) | [Español](../LANG/es/COMPREHENSIVE_TECHNICAL_GUIDE.md)

**Comprehensive Technical Documentation - Everything You Need to Know**  
*Author: Jean Bodenberg*  
*Date: February 2025*  
*Version: 2.0.0*

> **🆕 Version 2.0:** Expanded from 2 to **13 scaling strategies**, including perceptual models (BALANCED⭐, LOGARITHMIC, POWER), Smart Inference, and 5x performance improvements.

> **The most comprehensive responsive sizing library, combining mathematical rigor with practical simplicity.**

> **📚 This is the DEFINITIVE document.** Everything about AppDimens 2.0 is here. For quick starts, see [Simplified Guide](MATHEMATICAL_THEORY_SIMPLIFIED.md).

---

## 📋 Complete Table of Contents

### Part I: Introduction
1. [The Responsive Sizing Problem](#1-the-responsive-sizing-problem)
2. [The AppDimens 2.0 Solution](#2-the-appdimens-20-solution)
3. [Version 2.0 Major Changes](#3-version-20-major-changes)

### Part II: Strategy Reference (All 13)
4. [PRIMARY: BALANCED Strategy](#4-primary-balanced-strategy)
5. [SECONDARY: DEFAULT Strategy](#5-secondary-default-strategy)
6. [Perceptual Models: LOGARITHMIC & POWER](#6-perceptual-models-logarithmic--power)
7. [Utility Strategies: FLUID, INTERPOLATED, etc](#7-utility-strategies)
8. [Game Strategies: FIT & FILL](#8-game-strategies-fit--fill)
9. [Special: AUTOSIZE & NONE](#9-special-strategies-autosize--none)

### Part III: Platform Implementation
10. [Android Complete Guide](#10-android-complete-guide)
11. [iOS Complete Guide](#11-ios-complete-guide)
12. [Flutter Complete Guide](#12-flutter-complete-guide)
13. [React Native Complete Guide](#13-react-native-complete-guide)
14. [Web Complete Guide](#14-web-complete-guide)

### Part IV: Advanced Features
15. [Smart Inference System](#15-smart-inference-system)
16. [Base Orientation Support](#16-base-orientation-support)
17. [Physical Units](#17-physical-units)
18. [Game Development Modules](#18-game-development-modules)
19. [Cache and Performance](#19-cache-and-performance)

### Part V: Comparisons & Analysis
20. [Comparison with External Libraries](#20-comparison-with-external-libraries)
21. [Performance Benchmarks](#21-performance-benchmarks)
22. [Migration Guides](#22-migration-guides)

### Part VI: Reference
23. [Complete API Reference](#23-complete-api-reference)
24. [Troubleshooting](#24-troubleshooting)
25. [FAQ](#25-faq)
26. [References](#26-references)

---

## PART I: INTRODUCTION

## 1. The Responsive Sizing Problem

### 1.1 The Challenge

Modern apps must work across:
- 📱 Phones (320-480dp)
- 📱 Phablets (480-600dp)
- 📱 Small Tablets (600-720dp)
- 📺 Large Tablets (720-840dp)
- 📺 TVs (960-1920dp+)
- ⌚ Watches (< 300dp)
- 💻 Web (320-3840px+)

**Problem:** A fixed 48dp button:
- Looks good on phones (13% of screen)
- Looks tiny on tablets (6.7% of screen)
- Looks microscopic on TVs (2.5% of screen)

**Linear scaling (SDP):** Opposite problem:
- Looks good on phones (48dp)
- Looks too big on tablets (96dp)
- Looks enormous on TVs (144dp)

### 1.2 The Solution Requirements

An ideal solution must:
1. ✅ **Adapt** to screen size
2. ✅ **Control** excessive growth
3. ✅ **Consider** aspect ratio
4. ✅ **Maintain** visual consistency
5. ✅ **Perform** efficiently
6. ✅ **Work** across all platforms

---

## 2. The AppDimens 2.0 Solution

### 2.1 Core Innovations

**Version 2.0 introduces:**

1. **13 Scaling Strategies** (vs 2 in v1.x)
   - Perceptual: BALANCED ⭐, LOGARITHMIC, POWER
   - Legacy: DEFAULT, PERCENTAGE
   - Utility: FLUID, INTERPOLATED, DIAGONAL, PERIMETER
   - Game: FIT, FILL
   - Special: AUTOSIZE 🆕, NONE

2. **Primary Recommendation: BALANCED** ⭐
   - Hybrid linear-logarithmic
   - Linear on phones (< 480dp)
   - Logarithmic on tablets/TVs (≥ 480dp)
   - 40% oversizing reduction

3. **Smart Inference System** 🧠
   - Automatic strategy selection
   - 18 element types
   - 8 device categories
   - Weight-based algorithm

4. **5x Performance Improvement** ⚡
   - Lock-free cache (0.001µs)
   - Ln() lookup table (10-20x faster)
   - Pre-calculated constants
   - Binary search O(log n)

### 2.2 Quick Comparison

| Approach | Phone (360dp) | Tablet (720dp) | Problem |
|----------|---------------|----------------|---------|
| **Traditional DP** | 48dp (13%) | 48dp (6.7%) ❌ | Too small on tablets |
| **Linear (SDP)** | 58dp (16%) | 115dp (16%) ❌ | Too big on tablets |
| **AppDimens BALANCED** ⭐ | 58dp (16%) ✅ | 70dp (10%) ✅ | Perfect! |

---

## 3. Version 2.0 Major Changes

### 3.1 What Changed

**New Features:**
- ✅ 11 new scaling strategies
- ✅ BALANCED as primary recommendation
- ✅ Smart Inference with auto-selection
- ✅ 5x performance improvements
- ✅ Enhanced TypeScript/JavaScript support

**Renamed (Clarity):**
- `Fixed` → `DEFAULT` (phone-focused, secondary recommendation)
- `Dynamic` → `PERCENTAGE` (proportional, specific use)

**Backward Compatibility:**
- ✅ Older **platform packages** may still expose deprecated entry points—confirm in the submodule you depend on
- ✅ Meta-documentation now targets **`appdimens-dynamic` 3.x** tokens (`sdp`, `asdp`, …) for new Android Compose work
- ⚠️ Treat legacy `.fxdp` / `.dydp` **blog examples** as historical; follow the linked Android README instead

### 3.2 Migration from v1.x

**Old code (v1.x)** — consult the artifact you still compile against; **`appdimens-dynamic` 3.x** examples use:
```kotlin
Text("Hello", fontSize = 16.ssp)
```

**New code (v2.0) - Recommended:**
```kotlin
// Primary recommendation
Text("Hello", fontSize = 16.ssp)  // ⭐ BALANCED

// Secondary (equivalent to old Fixed)
Text("Hello", fontSize = 16.ssp)  // scaled typography

// Large containers (equivalent to old Dynamic)
Container(modifier = Modifier.width(300.wdp))
```

---

## PART II: STRATEGY REFERENCE

## 4. PRIMARY: BALANCED Strategy

> **🏆 Primary Recommendation:** Use for 95% of applications, especially multi-device apps.

### 4.1 Mathematical Formula

```
f_BALANCED(x, W) = {
  x × (W / 300)                           if W < 480dp
  x × (1.6 + 0.40 × ln(1 + (W-480)/300)) if W ≥ 480dp
}
```

**Components:**
- Linear region: W < 480dp (phones)
- Logarithmic region: W ≥ 480dp (tablets/TVs)
- Transition point: 480dp
- Sensitivity: k = 0.40

### 4.2 Results Table (48dp base)

| Screen | BALANCED | LINEAR | Reduction |
|--------|----------|--------|-----------|
| 240dp  | 38.4dp   | 38.4dp | 0%        |
| 300dp  | 48.0dp   | 48.0dp | Reference |
| 360dp  | 57.6dp   | 57.6dp | 0% (linear) |
| 480dp  | 76.8dp   | 76.8dp | 0% (transition) |
| 600dp  | 85.0dp   | 96.0dp | **-11.5%** |
| 720dp  | 69.7dp   | 115.2dp| **-39.5%** ⭐ |
| 960dp  | 88.3dp   | 153.6dp| **-42.5%** |
| 1080dp | 100.9dp  | 172.8dp| **-41.6%** |

### 4.3 Platform APIs

**Android:**
```kotlin
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.auto.asdp

// Jetpack Compose extensions (appdimens-dynamic 3.x)
16.sdp
16.ssp
Modifier.padding(16.asdp)

// View / XML / `code` layer: see submodule README (not the legacy AppDimens JVM chain)
```

**iOS:**
```swift
AppDimens.shared.balanced(16).toPoints()
AppDimens.shared.balanced(16).toPt()
```

**Flutter:**
```dart
AppDimens.fixed(16).calculate(context)
16.0.fx.calculate(context)
```

**React Native:**
```typescript
const {balanced} = useAppDimens();
balanced(16)
```

**Web:**
```typescript
const {balanced} = useWebDimens();
balanced(16)
```

### 4.4 When to Use BALANCED

**✅ Ideal For:**
- Multi-device apps (phones + tablets + TVs)
- Social media, productivity, e-commerce
- Buttons, spacing, padding
- General UI elements
- 95% of modern applications

**❌ Consider Alternatives:**
- Phone-only apps → Use DEFAULT
- Large containers → Use PERCENTAGE
- Maximum control → Use LOGARITHMIC

**📖 [Complete BALANCED Documentation](MATHEMATICAL_THEORY.md#2-new-primary-recommendation---balanced-strategy)**

---

## 5. SECONDARY: DEFAULT Strategy

> **Secondary Recommendation:** Use for phone-focused apps and backward compatibility.

### 5.1 Mathematical Formula

```
f_DEFAULT(x, W, AR) = x × [1 + ((W-300)/1) × (0.00333 + 0.00267×ln(AR/1.78))]
```

**Components:**
- ~97% linear: `((W-300) × 0.00333)`
- ~3% AR adjustment: `((W-300) × 0.00267×ln(AR/1.78))`

### 5.2 Results Table (48dp base)

| Screen | DEFAULT | BALANCED | LINEAR |
|--------|---------|----------|--------|
| 360dp  | 53.8dp  | 57.6dp   | 57.6dp |
| 480dp  | 64.5dp  | 76.8dp   | 76.8dp |
| 720dp  | 79.2dp  | 69.7dp   | 115.2dp|
| 1080dp | 94.0dp  | 100.9dp  | 172.8dp|

### 5.3 Platform APIs

**Android:**
```kotlin
16.sdp  // scaled — phone-first layouts
16.ssp
16.asdp // auto — hybrid curve on axis
```

**iOS:**
```swift
AppDimens.shared.defaultScaling(16).toPoints()
```

**Flutter:**
```dart
AppDimens.fixed(16).calculate(context)
```

### 5.4 When to Use DEFAULT

**✅ Ideal For:**
- Phone-focused apps (320-480dp)
- Icons and small elements
- Apps with elongated screens (20:9, 21:9)
- Backward compatibility with v1.x

**📖 [Complete DEFAULT Documentation](MATHEMATICAL_THEORY.md#3-secondary-recommendation---default-strategy-formerly-fixed)**

---

## 6. Perceptual Models: LOGARITHMIC & POWER

### 6.1 LOGARITHMIC (Pure Weber-Fechner)

**Formula:**
```
f_LOG(x, W) = x × (1 + 0.40 × ln(W/300))
```

**Results:** 48dp @ 720dp = 67.2dp (-42% vs linear)

**Use for:** TV apps, maximum control

**Score:** 88/100 🥈

**📖 [Logarithmic Details](MATHEMATICAL_THEORY.md#41-logarithmic-strategy-pure-weber-fechner)**

### 6.2 POWER (Stevens' Law)

**Formula:**
```
f_POWER(x, W) = x × (W/300)^0.75
```

**Results:** 48dp @ 720dp = 76.8dp (-33% vs linear)

**Use for:** General purpose, configurable

**Score:** 86/100 🥉

**📖 [Power Details](MATHEMATICAL_THEORY.md#42-power-strategy-stevens-law)**

---

## 7. Utility Strategies

### Quick Reference

| Strategy | Formula | Use Case | Score |
|----------|---------|----------|-------|
| **PERCENTAGE** | `x × (W/300)` | Large containers | 62/100 |
| **FLUID** | `clamp(min, interp, max)` | Typography | 78/100 |
| **INTERPOLATED** | `x × (0.5 + 0.5×W/300)` | Moderate | 70/100 |
| **DIAGONAL** | `x × √(W²+H²)/611.63` | Physical size | 72/100 |
| **PERIMETER** | `x × (W+H)/833` | W+H balance | 70/100 |

**📖 [All Utility Strategies](MATHEMATICAL_THEORY.md#5-extended-strategy-catalog-v20)**

---

## 8. Game Strategies: FIT & FILL

**FIT (Letterbox):** `x × min(W/300, H/533)`  
**FILL (Cover):** `x × max(W/300, H/533)`

**Scores:** 75/100, 73/100

**📖 [Game Strategies Details](MATHEMATICAL_THEORY.md#56-fit-strategy-game-letterbox)**

---

## 9. Special Strategies: AUTOSIZE & NONE

**AUTOSIZE 🆕:** Container-aware auto-sizing (O(log n) binary search)  
**NONE:** No scaling (identity function)

**📖 [Special Strategies](MATHEMATICAL_THEORY.md#58-autosize-strategy-container-aware)**

---

## PART III: PLATFORM IMPLEMENTATION

## 10. Android Complete Guide

### 10.1 Installation

```kotlin
dependencies {
    // Core library (13 strategies + Physical Units)
    implementation("io.github.bodenberg:appdimens-dynamic:3.1.4")
    
    // SDP/SSP (XML support)
    implementation("io.github.bodenberg:appdimens-sdps:3.1.2")
    implementation("io.github.bodenberg:appdimens-ssps:3.1.2")
    
    // All-in-one
    implementation("io.github.bodenberg:appdimens-all:2.0.1")
    
    // Games (C++/NDK)
    implementation("io.github.bodenberg:appdimens-games:2.0.1")
}
```

### 10.2 Jetpack Compose API

```kotlin
// BALANCED (Primary) ⭐
Text("Hello", fontSize = 16.ssp)
Box(modifier = Modifier.size(48.sdp))

// DEFAULT (Secondary)
Icon(modifier = Modifier.size(24.sdp))

// PERCENTAGE (Containers)
Container(modifier = Modifier.width(300.wdp))

// Smart API
Button(modifier = Modifier.height(
    48.sdp
))

// FLUID (Typography)
Text("Title", fontSize = 16.fluidSp().fssp)

// All other strategies
Text("Powerful", fontSize = 16.pwssp)
Text("Logarithmic", fontSize = 16.logarithmicSp().logssp)
```

### 10.3 View System API

```kotlin
// Prefer Jetpack Compose extensions in appdimens-dynamic; for classic Views use
// XML SDP/SSP (`appdimens-sdps` / `appdimens-ssps`) or the `code` packages from the submodule.
val widthPx = (300 * resources.displayMetrics.density).toInt() // placeholder — see submodule View integration
view.layoutParams.width = widthPx
```

### 10.4 XML with SDP/SSP

```xml
<TextView
    android:textSize="@dimen/_16ssp"
    android:padding="@dimen/_12sdp"
    android:layout_width="@dimen/_300sdp" />
```

**📖 [Complete Android Guide](../appdimens-dynamic/README.md)**

---

## 11. iOS Complete Guide

### 11.1 Installation

**CocoaPods:**
```ruby
pod 'AppDimens', '~> 2.0.0'
```

**SPM:**
```swift
.package(url: "https://github.com/bodenberg/appdimens.git", from: "2.0.0")
```

### 11.2 SwiftUI API

```swift
// BALANCED (Primary) ⭐
Text("Hello")
    .font(.system(size: AppDimens.shared.balanced(16).toPoints()))

// DEFAULT (Secondary)
Image(systemName: "heart")
    .frame(width: AppDimens.shared.defaultScaling(24).toPoints())

// Smart API
Button("Click") {}
    .frame(height: AppDimens.shared.smart(48).forElement(.button).toPoints())

// FLUID
Text("Title")
    .font(.system(size: AppDimens.shared.fluid(min: 16, max: 24).toPoints()))
```

### 11.3 UIKit API

```swift
let size = AppDimens.shared.balanced(48).toPoints()
button.frame = CGRect(x: 0, y: 0, width: size, height: size)
```

**📖 [Complete iOS Guide](../appdimens-ios/README.md)**

---

## 12. Flutter Complete Guide

### 12.1 Installation

```yaml
dependencies:
  appdimens: ^2.0.0
```

### 12.2 API

```dart
// BALANCED-style (Primary) ⭐ — fixed builder + extensions (.fx / .dy)
Text(
  'Hello',
  style: TextStyle(fontSize: AppDimens.fixed(16).calculate(context)),
)

// Extensions
Container(
  width: AppDimens.fixed(300).calculate(context),
  height: AppDimens.fixed(200).calculate(context),
)

// Button height — pick explicit builder; “smart” helpers are not on AppDimens in this package
ElevatedButton(
  style: ElevatedButton.styleFrom(
    minimumSize: Size(
      double.infinity,
      AppDimens.fixed(48).calculate(context),
    ),
  ),
)

// FLUID — extension helper on num (see appdimens_extensions.dart)
Text(
  'Title',
  style: TextStyle(
    fontSize: 16.0.fluidTo(24).calculate(context),
  ),
)
```

**📖 [Complete Flutter Guide](../appdimens-flutter/README.md)**

---

## 13. React Native Complete Guide

### 13.1 Installation

```bash
npm install appdimens-react-native@2.0.0
```

### 13.2 API

{% raw %}
```typescript
import {useAppDimens} from 'appdimens-react-native';

function MyComponent() {
  const {balanced, defaultScaling, smart} = useAppDimens();
  
  return (
    <View style={{padding: balanced(16)}}>
      <Text style={{fontSize: balanced(18)}}>Hello</Text>
      <TouchableOpacity style={{height: smart(48).forElement('button')}}>
        <Text>Click</Text>
      </TouchableOpacity>
    </View>
  );
}
```
{% endraw %}

**📖 [Complete React Native Guide](../appdimens-react-native/README.md)**

---

## 14. Web Complete Guide

### 14.1 Installation

```bash
npm install webdimens@2.0.0
```

### 14.2 React API

{% raw %}
```typescript
import {useWebDimens} from 'webdimens/react';

function MyComponent() {
  const {balanced, fluid, smart} = useWebDimens();
  
  return (
    <div style={{padding: balanced(16)}}>
      <h2 style={{fontSize: fluid(18, 24)}}>Title</h2>
      <button style={{height: smart(48).forElement('button')}}>
        Click
      </button>
    </div>
  );
}
```
{% endraw %}

### 14.3 Vue, Svelte, Angular

See platform-specific hooks and services.

**📖 [Complete Web Guide](../appdimens-web/README.md)**

---

## PART IV: ADVANCED FEATURES

## 15. Smart Inference System

### 15.1 Automatic Strategy Selection

```kotlin
// Automatically selects best strategy based on element type
val buttonSize = 48.sdp
// → Selects BALANCED for buttons on tablets

val containerWidth = 300.wdp
// → Selects PERCENTAGE for containers
```

### 15.2 Element Types (18)

| Element | Phone Strategy | Tablet Strategy | Rationale |
|---------|----------------|-----------------|-----------|
| BUTTON | DEFAULT (0.4) | BALANCED (0.7) | Consistent sizing |
| TEXT | FLUID (0.6) | FLUID (0.8) | Readable |
| ICON | DEFAULT (0.7) | DEFAULT (0.6) | Visual weight |
| CONTAINER | PERCENTAGE (0.8) | PERCENTAGE (0.8) | Proportional |
| SPACING | DEFAULT (0.5) | BALANCED (0.6) | Perceptual |
| ... | ... | ... | ... |

### 15.3 Device Categories (8)

- PHONE_SMALL (< 300dp)
- PHONE_NORMAL (300-360dp)
- PHONE_LARGE (360-480dp)
- TABLET_SMALL (480-600dp)
- TABLET_LARGE (600-840dp)
- TV (840+dp)
- WATCH (< 240dp)
- AUTO (context-dependent)

**📖 [Complete Smart Inference](MATHEMATICAL_THEORY.md#6-smart-inference-system-v20)**

---

## 16. Base Orientation Support

### 16.1 Auto-Rotation Adaptation

Design for one orientation, automatically adapt when rotated:

```kotlin
// Android
val width = 300.wdp // orientation: see appdimens-dynamic rotation / inverter docs
// Portrait (360x800): Uses width (360)
// Landscape (800x360): Auto-inverts to width (800)

// iOS
let width = AppDimens.shared.balanced(300).portraitLowest().toPoints()

// Flutter
final width = AppDimens.fixed(300).portraitLowest().calculate(context);
```

**📖 [Base Orientation Guide](BASE_ORIENTATION_GUIDE.md)**

---

## 17. Physical Units

### 17.1 Real-World Measurements

```kotlin
// Android
val buttonWidth = 10.mm   // 10 millimeters
val cardWidth = 8.cm      // 8 centimeters
val screenSize = 5.inch   // 5 inches

// iOS
let width = AppDimensPhysicalUnits.mm(10)

// Flutter
final width = AppDimensPhysicalUnits.mmToPixels(10, context);
```

**Available:** All platforms

**📖 [Physical Units Documentation](MATHEMATICAL_THEORY.md#complementary-models)**

---

## 18. Game Development Modules

### 18.1 Android (C++/NDK)

```kotlin
val games = AppDimensGames.getInstance()
games.initialize(context)

val buttonSize = games.calculateButtonSize(48f)
val playerSize = games.calculatePlayerSize(64f)
```

**Features:** C++/NDK, OpenGL ES, Vector2D, Physical units

**📖 [Android Games Guide](../appdimens-games/appdimens_games/README.md)**

### 18.2 iOS (Metal)

```swift
let buttonSize = gameUniform(48)
let playerSize = gameAspectRatio(64)
```

**Features:** Metal/MetalKit, SIMD, 5 viewport modes

**📖 [iOS Games Guide](../appdimens-ios/README.md#game-development-features)**

---

## 19. Cache and Performance

### 19.1 Version 2.0 Optimizations

**5x Overall Improvement:**

| Optimization | v1.x | v2.0 | Speedup |
|--------------|------|------|---------|
| **Views cache** | 0.005µs | 0.001µs | **5x** |
| **Ln() calculation** | 0.012µs | 0.001µs | **10-20x** |
| **Memory/entry** | 280B | 56B | **5x** |
| **Multi-thread** | 25% | 100% | **4x** |

### 19.2 Cache Control

```kotlin
// Compose: configuration-driven caching is internal to appdimens-dynamic.
// Flutter builders expose `.cache(bool)` — see submodule README / performance notes.
val height = 48.hdp
```

### 19.3 Warmup Cache

```kotlin
// Call during app initialization
AppDimens.warmupCache(context)
```

**📖 [Performance Details](MATHEMATICAL_THEORY.md#7-mathematical-optimizations-v20)**

---

## PART V: COMPARISONS & ANALYSIS

## 20. Comparison with External Libraries

### 20.1 Rankings (Score/100)

| Rank | Library/Strategy | Score | Category |
|------|------------------|-------|----------|
| 🏆 #1 | **AppDimens BALANCED** | 93/100 | Platinum |
| 🥈 #2 | **AppDimens LOGARITHMIC** | 88/100 | Platinum |
| 🥉 #3 | **AppDimens POWER** | 86/100 | Gold |
| #4 | **AppDimens DEFAULT** | 82/100 | Gold |
| #5 | **AppDimens FLUID** | 78/100 | Silver |
| ... | ... | ... | ... |
| #15 | **SDP/SSP** | 65/100 | Bronze |
| #18 | **CSS vw/vh** | 58/100 | Not Recommended |
| #20 | **Traditional DP** | 50/100 | Baseline |

**📊 [Complete Rankings](FORMULA_COMPARISON.md#8-final-ranking-and-certification)**

### 20.2 AppDimens vs SDP/SSP

| Aspect | AppDimens BALANCED ⭐ | SDP/SSP |
|--------|---------------------|---------|
| **Formula** | Hybrid linear-log | Linear |
| **Oversizing Control** | 40% reduction | 0% |
| **AR Compensation** | No (BALANCED), Yes (DEFAULT) | No |
| **Tablet (720dp)** | 70dp ✅ | 115dp ❌ |
| **Performance** | 0.0012µs | 0.0000µs (pre-calc) |
| **Flexibility** | 13 strategies | 1 strategy |
| **Files** | 0 (runtime) | 426 XML files |

**Recommendation:** Migrate from SDP to BALANCED for 40% improvement

**📖 [Migration from SDP](FORMULA_COMPARISON.md#101-from-sdpssp-to-appdimens)**

---

## 21. Performance Benchmarks

### 21.1 Calculation Speed

**10,000 calculations benchmark:**

| Strategy | Time (µs) | Relative |
|----------|-----------|----------|
| PERCENTAGE | 0.0003 | 1.0x (fastest) |
| POWER | 0.0008 | 2.7x |
| LOGARITHMIC | 0.0010 | 3.3x |
| BALANCED | 0.0012 | 4.0x |
| DEFAULT | 0.0015 | 5.0x |

**All sub-microsecond! Negligible performance difference.**

### 21.2 Memory Usage

- Per cache entry: 56 bytes
- Total cache: ~60 KB
- Ln() lookup table: ~1 KB
- **Total:** ~61 KB (entire library)

**📊 [Complete Benchmarks](FORMULA_COMPARISON.md#5-performance-analysis)**

---

## 22. Migration Guides

### 22.1 From AppDimens v1.x

**Step 1:** Pick the submodule for your stack (Compose → `appdimens-dynamic` 3.x).  
**Step 2:** Replace legacy unified Android chains (`.fxdp`, `.dydp`, `.balanced().dp`) with **`sdp` / `wdp` / `hdp` / `ssp`** and, for the hybrid curve, **`asdp` / `ahdp` / `awdp` / `assp`**.  
**Step 3:** Keep **iOS / Web / RN** builder names (`balanced`, `defaultScaling`, …) as documented in those repos.

**Backward compatible:** Older **platform-specific** code may still compile inside older artifacts; the meta-docs no longer teach the retired Kotlin extension chain for `appdimens-dynamic` 3.x.

### 22.2 From SDP/SSP

**Step 1:** Add AppDimens dependency  
**Step 2:** Replace `@dimen/_16sdp` → `16.sdp`  
**Step 3:** Remove SDP dependency and XML files

**Benefits:** 40% better control, runtime flexibility

### 22.3 From Other Libraries

- **CSS vw/vh** → WebDimens `balanced()` / `fluid()` (see `appdimens-web`)
- **ScreenUtil** → Prefer explicit **scaled** tokens on Android (`sdp` / `wdp` / `hdp`)
- **size-matters** → Map layouts to **strategy packages** in `appdimens-dynamic`, not a single extension

**📖 [Complete Migration Guides](FORMULA_COMPARISON.md#10-migration-guide-from-external-libraries)**

---

## PART VI: REFERENCE

## 23. Complete API Reference

### 23.1 Strategy Selection Methods

**Android (Jetpack Compose — `appdimens-dynamic` 3.x):**
```kotlin
// Strategy = which package you import (scaled, auto, percent, logarithmic, power, fluid, …)
16.sdp           // scaled — smallest-width axis
16.asdp          // auto — hybrid “BALANCED-like” on smallest-width axis
300.wdp          // scaled — width axis (containers)
16.logarithmicSp().logssp
16.pwssp
14.fluidSp().fssp
```

**iOS:**
```swift
.balanced(_)
.defaultScaling(_)
.percentage(_)
.logarithmic(_)
.power(_, exponent:)
.fluid(min:max:)
.smart(_)
```

**Flutter (`appdimens` package):**
```dart
AppDimens.fixed(_)        // primary builder
AppDimens.dynamic(_)      // aggressive / width-like growth
_.fx / _.dy               // num extensions → builders
_.fluidTo(max)            // fluid helper (see appdimens_extensions.dart)
AppDimens.dynamicPercentageDp(percent, context)
```

### 23.2 Configuration Methods

Qualifier chains, orientation, cache, and AR flags are exposed on **builders inside each submodule** (e.g. `DimenLogarithmic`, `AppDimensFixed`, `WebDimensBuilder`). See [Platform API map](PLATFORM_API_MAP.md) and the linked READMEs instead of a single shared Kotlin surface.

### 23.3 Output Methods

**Android:**
```kotlin
.dp          // Dp (Compose)
.sp          // TextUnit (Compose)
.toDp()      // Float (Views)
.toSp()      // Float (Views)
.toPx()      // Float (physical pixels)
```

**iOS:**
```swift
.toPoints()  // CGFloat
.toPt()      // CGFloat (shorthand)
.toPixels()  // CGFloat (physical)
```

**Flutter:**
```dart
.calculate(context)  // double
```

---

## 24. Troubleshooting

### 24.1 Common Issues

**Q: Sizes look wrong on my device**

A: Check:
1. Using correct strategy (BALANCED for multi-device)
2. Base value is appropriate (48dp for buttons, not 16dp)
3. Not mixing strategies randomly

**Q: Performance seems slow**

A: Solutions:
1. Ensure cache is enabled (default)
2. Call `warmupCache()` during init
3. Use appropriate strategy (PERCENTAGE is fastest)

**Q: Migration from v1.x - sizes changed**

A: On **Android Compose**, move to **`sdp` / `wdp` / `hdp` / `ssp`** plus **`asdp` / …** for the hybrid curve ([appdimens-dynamic README](../appdimens-dynamic/README.md)). Legacy extension names are **not** what this meta-repo documents for 3.x.

### 24.2 FAQ

**Q: Which strategy should I use?**

A: 
- Multi-device app → **BALANCED** ⭐
- Phone-only → **DEFAULT**
- Large containers → **PERCENTAGE**
- Typography → **FLUID**

**Q: Is v2.0 compatible with v1.x?**

A: **Per artifact.** Some older binaries still ship deprecated entry points, but **`appdimens-dynamic` 3.x** teaches the **token + package-per-strategy** model—verify against the submodule changelog you actually depend on.

**Q: What's the performance impact?**

A: Negligible (< 0.002µs per calculation with cache)

---

## 25. FAQ

### Strategy Selection

**Q: Why is BALANCED now primary instead of DEFAULT?**

A: BALANCED provides 40% better oversizing control on tablets while maintaining familiar linear behavior on phones. DEFAULT (formerly Fixed) is now optimized for phone-focused apps.

**Q: Can I still use the old Fixed/Dynamic?**

A: If your **published dependency** still exposes those symbols, consult that version’s notes. New **Compose** work should follow **`sdp` / `wdp` / `hdp`** and strategy imports from **`appdimens-dynamic`** instead of `.fxdp` / `.dydp` examples in old blog posts.

**Q: When should I use PERCENTAGE instead of BALANCED?**

A: Only for very large containers, images, and grids where proportional scaling is truly needed. For 95% of UI elements, use BALANCED.

### Performance

**Q: Is there overhead vs traditional DP?**

A: Minimal (< 0.002µs with cache). Negligible in practice.

**Q: Should I disable cache?**

A: No, keep it enabled (default). Only disable for debugging.

### Platform-Specific

**Q: Does AppDimens work with Jetpack Compose?**

A: Yes! Native Compose support with extension functions.

**Q: Can I use AppDimens in XML layouts?**

A: Yes! Use SDP/SSP modules for XML, or runtime calculation in code.

---

## 26. References

### Documentation

- [Mathematical Theory](MATHEMATICAL_THEORY.md) - Complete math foundation
- [Simplified Guide](MATHEMATICAL_THEORY_SIMPLIFIED.md) - 15min intro
- [Formula Comparison](FORMULA_COMPARISON.md) - Rankings & analysis
- [Examples](EXAMPLES.md) - Code samples

### Platform Guides

- [Android](../appdimens-dynamic/README.md)
- [iOS](../appdimens-ios/README.md)
- [Flutter](../appdimens-flutter/README.md)
- [React Native](../appdimens-react-native/README.md)
- [Web](../appdimens-web/README.md)

### Academic References

- Weber, E. H. (1834). "De pulsu, resorptione, auditu et tactu"
- Fechner, G. T. (1860). "Elemente der Psychophysik"
- Stevens, S. S. (1957). "On the psychophysical law"
- Loomis et al. (1992). "Visual space perception"

---

**Document created by:** Jean Bodenberg  
**Last updated:** February 2025  
**Version:** 2.0.0  
**License:** Apache 2.0  
**Repository:** https://github.com/bodenberg/appdimens

---

**[⬆ Back to Top](#-appdimens-complete-technical-guide)**
