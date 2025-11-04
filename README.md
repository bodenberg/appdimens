<div align="center">
   <img src="IMAGES/image_sample_devices.png" alt="AppDimens - Responsive Design Across All Devices" height="300"/>
<h1>📐 AppDimens</h1>
<p><strong>Smart Responsive Dimensions for Any Screen</strong></p>
   
[![Version](https://img.shields.io/badge/version-2.0.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%20%7C%20iOS%20%7C%20Flutter%20%7C%20RN%20%7C%20Web-orange.svg)](https://github.com/bodenberg/appdimens)
[![Strategies](https://img.shields.io/badge/strategies-13-orange.svg)]()

[📚 Documentation](DOCS/README.md) | [⚡ Quick Reference](DOCS/DOCS_QUICK_REFERENCE.md) | [🔬 Technical Details](DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

> **Languages:** English | [Português (BR)](LANG/pt-BR/README.md) | [Español](LANG/es/README.md)
</div>

---

## 🆕 What's New in Version 2.0

**🎯 13 Scaling Strategies** (up from 2!)
- **BALANCED** ⭐ New recommended primary strategy - hybrid linear-logarithmic + AR
- **DEFAULT** (formerly Fixed) - logarithmic with aspect ratio (secondary recommendation)
- **PERCENTAGE** (formerly Dynamic) - proportional scaling
- **LOGARITHMIC** - pure Weber-Fechner psychophysics + AR
- **POWER** - Stevens' Power Law (configurable) + AR
- **FLUID** - CSS clamp-like with breakpoints (AR opt-in)
- **INTERPOLATED** - moderate linear interpolation + AR
- Plus 6 more: DIAGONAL, PERIMETER, FIT, FILL, AUTOSIZE 🆕, NONE

**🧠 Smart Inference System**
- Automatic strategy selection based on element type
- 18 element types (BUTTON, TEXT, ICON, CONTAINER, etc.)
- 8 device categories (PHONE_SMALL to TV)
- Weight-based decision system

**⚡ 5x Performance Improvement**
- Unified lock-free cache (0.001µs)
- Ln() lookup table (10-20x faster)
- Pre-calculated constants
- Binary search algorithms (O(log n))

**♻️ Full Backward Compatibility**
- Old `.fxdp`/`.dydp` extensions still work
- Smooth migration path to `.balanced()`, `.defaultDp`, `.percentageDp`

---

## ⚡ Quick Overview

**AppDimens** makes your UI elements scale perfectly across all devices - from phones to tablets, TVs, watches, and web browsers.

Instead of fixed sizes that look tiny on tablets or huge on watches, AppDimens uses **perceptual scaling** based on psychophysics research (Weber-Fechner, Stevens) that adapts intelligently to screen size, aspect ratio, and device type.

### Why AppDimens 2.0?

```
❌ Without AppDimens:
   Phone (360dp): Button = 48dp (13% of screen) ✅ Good
   Tablet (720dp): Button = 48dp (7% of screen)  ❌ Too small!

❌ With Linear Scaling (SDP):
   Phone (360dp): Button = 58dp (16% of screen) ✅ OK
   Tablet (720dp): Button = 115dp (16% of screen) ❌ Too big!

✅ With AppDimens BALANCED ⭐:
   Phone (360dp): Button = 58dp (16% of screen) ✅ Perfect
   Tablet (720dp): Button = 70dp (10% of screen) ✅ Perfect!
```

### Key Benefits

- ✅ **Perfect proportions** on any screen size
- ✅ **Works everywhere**: Android, iOS, Flutter, React Native, Web
- ✅ **Simple API**: `.balanced()`, `.defaultDp`, `.percentageDp`
- ✅ **Scientifically proven**: Based on psychophysics research (Weber-Fechner, Stevens)
- ✅ **Aspect ratio aware**: Compensates for ultra-wide, foldables, and non-standard aspect ratios
- ✅ **Best performance**: 5x faster with lock-free cache and optimizations
- ✅ **13 scaling strategies**: From simple to advanced, covering all use cases
- ✅ **Smart Inference**: Automatic strategy selection for 18 element types
- ✅ **Physical units**: Real-world measurements (mm, cm, inch) across all platforms
- ✅ **Game development**: Specialized modules for Android (C++/NDK) and iOS (Metal)
- ✅ **AutoSize** 🆕: Container-aware auto-sizing like TextView autoSizeText

---

## 🚀 Installation

### Android

```kotlin
dependencies {
    // Core library (Fixed + Dynamic scaling + Physical Units)
    // Includes: .fxdp, .dydp, Physical Units (mm/cm/inch), Grid calculations
    implementation("io.github.bodenberg:appdimens-dynamic:2.0.1")
    
    // SDP scaling (Scalable DP for XML)
    // Includes: @dimen/_16sdp, etc.
    implementation("io.github.bodenberg:appdimens-sdps:2.0.1")
    
    // SSP scaling (Scalable SP for text in XML)
    // Includes: @dimen/_18ssp, etc.
    implementation("io.github.bodenberg:appdimens-ssps:2.0.1")
    
    // All-in-one (includes dynamic, sdps, ssps)
    // ⚠️ Note: Does NOT include games module
    implementation("io.github.bodenberg:appdimens-all:2.0.1")
    
    // Game development (C++/NDK + OpenGL)
    // 🎮 Separate dependency - not included in "all"
    implementation("io.github.bodenberg:appdimens-games:2.0.1")
}
```

### iOS

**CocoaPods:**
```ruby
# Full package (Main + UI)
pod 'AppDimens', '~> 2.0.0'

# Only Main module
pod 'AppDimens/Main', '~> 2.0.0'

# Games module (separate)
pod 'AppDimens/Games', '~> 2.0.0'
```

**Swift Package Manager:**
```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "2.0.0")
]
```

### Flutter

```yaml
dependencies:
  appdimens: ^2.0.0
```

### React Native

```bash
# npm
npm install appdimens-react-native@2.0.0

# yarn
yarn add appdimens-react-native@2.0.0
```

### Web

```bash
# npm
npm install webdimens@2.0.0

# yarn
yarn add webdimens@2.0.0

# pnpm
pnpm add webdimens@2.0.0
```

**Vanilla JavaScript (CDN):**
```html
<script src="https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.js"></script>
<script>
  const { balanced, defaultScaling, fluid } = WebDimens;
  
  document.getElementById('myElement').style.width = balanced(300);
</script>
```

**📖 [Complete Installation Guide](DOCS/README.md#-quick-start)**

---

## 💡 Basic Usage

### Android (Jetpack Compose)

```kotlin
@Composable
fun MyCard() {
    Card(
        modifier = Modifier
            .width(300.balanced().dp)      // ✨ BALANCED (RECOMMENDED) ⭐
            .padding(16.balanced().dp)     // ✨ Adapts intelligently
    ) {
        Text(
            text = "Hello World",
            fontSize = 18.balanced().sp    // ✨ Readable everywhere
        )
    }
}
```

### Android (XML with SDP/SSP)

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="@dimen/_16sdp">
    
    <TextView
        android:layout_width="@dimen/_300sdp"
        android:layout_height="wrap_content"
        android:textSize="@dimen/_18ssp"
        android:text="Hello World" />
</LinearLayout>
```

### Android (View Binding)

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // BALANCED scaling (recommended) ⭐
        val width = 300.balanced().toPx(resources)
        binding.card.layoutParams.width = width.toInt()
        
        // Physical units
        val margin = AppDimensPhysicalUnits.toCm(2f, resources)
        binding.button.setPadding(margin.toInt(), 0, margin.toInt(), 0)
    }
}
```

### Android (Data Binding)

```xml
<!-- layout/activity_main.xml -->
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    
    <data>
        <import type="com.appdimens.dynamic.compose.AppDimensExtKt"/>
    </data>
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="@dimen/_16sdp">
        
        <TextView
            android:layout_width="@dimen/_300sdp"
            android:layout_height="wrap_content"
            android:textSize="@dimen/_18ssp"
            android:text="Hello World" />
            
        <!-- Dynamic dimensions in DataBinding -->
        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="@{AppDimensExtKt.balanced(48).dp}"
            android:text="Click Me" />
    </LinearLayout>
</layout>
```

```kotlin
// Activity with DataBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = 
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        
        // Set dimensions programmatically
        binding.button.apply {
            layoutParams.width = 200.balanced().toPx(resources).toInt()
            layoutParams.height = 56.balanced().toPx(resources).toInt()
        }
    }
}
```

### Android (Physical Units - included in appdimens-dynamic)

```kotlin
// Use real-world measurements
// Physical Units are part of appdimens-dynamic
val cardWidth = AppDimensPhysicalUnits.toCm(8f, resources)  // 8 cm
val buttonHeight = AppDimensPhysicalUnits.toInch(0.5f, resources)  // 0.5 inch
val padding = AppDimensPhysicalUnits.toMm(10f, resources)  // 10 mm

view.layoutParams.width = cardWidth.toInt()
button.layoutParams.height = buttonHeight.toInt()
view.setPadding(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())

// Grid calculations (also in appdimens-dynamic)
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

### Android (Games Module)

```kotlin
@Composable
fun GameScreen() {
    val gamesManager = remember { AppDimensGames.getInstance() }
    
    LaunchedEffect(Unit) {
        gamesManager.initialize(context)
    }
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Game-specific dimensions
        val buttonSize = gamesManager.calculateButtonSize(48f)
        val playerSize = gamesManager.calculatePlayerSize(64f)
        
        // Draw game elements with scaled dimensions
        drawCircle(
            color = Color.Blue,
            radius = playerSize / 2
        )
    }
}
```

### iOS (SwiftUI)

```swift
struct MyCard: View {
    var body: some View {
        VStack {
            Text("Hello World")
                .font(.system(size: AppDimens.shared.balanced(18).toPoints()))
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
        .frame(width: AppDimens.shared.balanced(300).toPoints())
    }
}
```

### iOS (UIKit)

```swift
class MyViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let containerView = UIView()
        containerView.backgroundColor = .systemBlue
        containerView.layer.cornerRadius = AppDimens.shared.balanced(16).toPoints()
        view.addSubview(containerView)
        
        let titleLabel = UILabel()
        titleLabel.text = "Hello World"
        titleLabel.font = .systemFont(ofSize: AppDimens.shared.balanced(18).toPoints())
        containerView.addSubview(titleLabel)
    }
}
```

### Flutter

```dart
Widget build(BuildContext context) {
  return Container(
    width: AppDimens.balanced(300).calculate(context),
    padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
    child: Text(
      'Hello World',
      style: TextStyle(fontSize: AppDimens.balanced(18).calculate(context)),
    ),
  );
}
```

### React Native

{% raw %}
```jsx
function MyCard() {
  const { balanced } = useAppDimens();
  
  return (
    <View style={{ width: balanced(300), padding: balanced(16) }}>
      <Text style={{ fontSize: balanced(18) }}>
        Hello World
      </Text>
    </View>
  );
}
```
{% endraw %}

### Web (Vanilla JavaScript)

```html
<!DOCTYPE html>
<html>
<head>
  <script src="https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.js"></script>
</head>
<body>
  <div id="container">
    <header id="header">
      <h1 id="title">Hello World</h1>
    </header>
  </div>
  
  <script type="module">
    import { webdimens } from 'https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.mjs';
    
    // Apply balanced dimensions (recommended) ⭐
    document.getElementById('header').style.height = webdimens.balanced(64);
    document.getElementById('title').style.fontSize = webdimens.balanced(24);
    document.getElementById('container').style.padding = webdimens.balanced(24);
  </script>
</body>
</html>
```

### Web (React)

{% raw %}
```jsx
import { useWebDimens } from 'webdimens/react';

function MyCard() {
  const { balanced, fluid } = useWebDimens();
  
  return (
    <div style={{ width: balanced(300), padding: balanced(16) }}>
      <h2 style={{ fontSize: fluid(18, 24) }}>Hello World</h2>
    </div>
  );
}
```
{% endraw %}

### Web (Vue)

```vue
<template>
  <div :style="{ width: balanced(300), padding: balanced(16) }">
    <h2 :style="{ fontSize: balanced(18) }">Hello World</h2>
  </div>
</template>

<script setup>
import { useWebDimens } from 'webdimens/vue';

const { balanced } = useWebDimens();
</script>
```

### Web (Svelte)

```svelte
<script>
  import { webDimensStore } from 'webdimens/svelte';
  
  $: wd = $webDimensStore;
  $: width = wd.balanced(300);
  $: padding = wd.balanced(16);
  $: fontSize = wd.balanced(18);
</script>

<div style="width: {width}; padding: {padding};">
  <h2 style="font-size: {fontSize};">Hello World</h2>
</div>
```

### Web (Angular)

```typescript
import { Component } from '@angular/core';
import { WebDimensService } from 'webdimens/angular';

@Component({
  selector: 'app-card',
  template: `
    <div [ngStyle]="{ width: width, padding: padding }">
      <h2 [ngStyle]="{ fontSize: fontSize }">Hello World</h2>
    </div>
  `
})
export class CardComponent {
  width = '';
  padding = '';
  fontSize = '';

  constructor(private wd: WebDimensService) {
    this.width = wd.balanced(300);
    this.padding = wd.balanced(16);
    this.fontSize = wd.balanced(18);
  }
}
```

**📖 [More Examples](DOCS/EXAMPLES.md)**

---

## 🎯 Scaling Strategies

AppDimens 2.0 offers 13 scaling strategies for different needs:

| Strategy | When to Use | Example | Availability |
|----------|-------------|---------|--------------|
| **BALANCED** ⭐ **RECOMMENDED** | 95% of cases - multi-device apps (phones, tablets, TVs) | `48.balanced().dp` | All platforms |
| **DEFAULT** (Secondary) | Phone-focused apps, icons, backward compatibility | `48.defaultDp` | All platforms |
| **PERCENTAGE** | Large containers, full-width grids, proportional elements | `100.percentageDp` | All platforms |
| **LOGARITHMIC** | TV apps, maximum control on large screens | `48.logarithmic()` | All platforms |
| **POWER** | General purpose, configurable with exponent | `48.power(0.75)` | All platforms |
| **FLUID** 🌊 | Typography, spacing with smooth min/max transitions | `fluid(16, 24)` | All platforms |
| **FIT / FILL** | Game UI (letterbox/cover) | `48.fit()` / `48.fill()` | All platforms |
| **AUTOSIZE** 🆕 | Container-aware auto-sizing (like TextView autoSizeText) | `smart().autosize()` | All platforms |
| **INTERPOLATED** | Moderate scaling (50% linear) | `48.interpolated()` | All platforms |
| **DIAGONAL** | Scale based on screen diagonal (true physical size) | `48.diagonal()` | All platforms |
| **PERIMETER** | Scale based on W+H perimeter | `48.perimeter()` | All platforms |
| **NONE** | No scaling (constant size) | `48.none()` | All platforms |

**📖 [Understanding Scaling Strategies](DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md)**

---

## 🏆 Why AppDimens is #1

AppDimens was scientifically compared against 7 other scaling approaches:

```
🥇 #1 AppDimens BALANCED: 93/100 ⭐⭐⭐⭐⭐ (Primary recommendation)
🥈 #2 AppDimens LOGARITHMIC: 88/100 ⭐⭐⭐⭐⭐ (TV/Large tablets)
🥉 #3 AppDimens POWER: 86/100 ⭐⭐⭐⭐
   #4 AppDimens DEFAULT: 82/100 ⭐⭐⭐⭐ (Phone-focused)
   #5 SDP/SSP: 65/100
   #6 CSS vw/vh: 58/100
```

### What Makes It Better?

- ✅ **BALANCED strategy**: Hybrid linear-logarithmic (40% oversizing reduction)
- ✅ **Perceptual models**: Based on psychophysics (Weber-Fechner, Stevens)
- ✅ **13 strategies**: Most comprehensive library
- ✅ **Smart Inference**: Automatic strategy selection
- ✅ **5x faster**: Lock-free cache and optimizations
- ✅ **Aspect ratio compensation**: Only library with AR adjustment (DEFAULT strategy)

**📊 [See Full Comparison](DOCS/FORMULA_COMPARISON.md)**

---

## 📚 Documentation

### Getting Started

1. **[Quick Reference](DOCS/DOCS_QUICK_REFERENCE.md)** ⚡ Find anything in seconds
2. **[Simplified Guide](DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md)** 📖 Understand in 15 minutes
3. **[Examples](DOCS/EXAMPLES.md)** 💻 Ready-to-use code

### Technical Documentation

4. **[Complete Technical Guide](DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)** 🔬 Everything in one place (2h read)
5. **[Formula Comparison](DOCS/FORMULA_COMPARISON.md)** 📊 Scientific analysis & rankings
6. **[Mathematical Theory](DOCS/MATHEMATICAL_THEORY.md)** 📐 Formal mathematical foundation

### Platform Guides

- 🤖 [Android Guide](Android/README.md)
- 🍎 [iOS Guide](iOS/README.md)
- 🎯 [Flutter Guide](Flutter/README.md)
- ⚛️ [React Native Guide](ReactNative/README.md)
- 🌐 [Web Guide](Web/README.md)

**📚 [Complete Documentation Index](DOCS/README.md)**

---

## 🎮 Advanced Features

### 🆕 Base Orientation (v1.2.0+)

Auto-adapt to screen rotation! Design for one orientation, automatically maintain proportions when rotated:

```kotlin
// Android - Design for portrait, auto-adapts to landscape
val cardWidth = 300.balanced().portraitLowest().dp
// Portrait (360x800):  Uses width (360) ✅
// Landscape (800x360): Auto-inverts to width (800) ✅

// Shorthand extensions
val padding = 16.balancedPortraitLowest    // Auto-inverts in landscape
val height = 200.balancedLandscapeHighest  // Auto-inverts in portrait
```

```swift
// iOS - Same concept
let cardWidth = AppDimens.shared.balanced(300).portraitLowest().toPoints()
```

```dart
// Flutter
final cardWidth = AppDimens.balanced(300).portraitLowest().calculate(context);
```

**📖 [Complete Base Orientation Guide](DOCS/BASE_ORIENTATION_GUIDE.md)**

### 🌊 Fluid Scaling

Smooth interpolation between min/max values - perfect for typography and controlled growth:

```kotlin
// Android Compose - Font size 16-24sp between 320-768dp width
@Composable
fun FluidTypography() {
    val fontSize = fluidSp(16f, 24f)
    Text("Fluid Text", fontSize = fontSize)
    
    // With custom breakpoints
    val padding = fluidDp(8f, 16f, minWidth = 280f, maxWidth = 600f)
}
```

```swift
// iOS SwiftUI - Same concept
struct FluidView: View {
    var body: some View {
        Text("Fluid Text")
            .font(.system(size: AppDimens.shared.fluid(min: 16, max: 24).toPoints()))
            .padding(AppDimens.shared.fluid(min: 8, max: 16).toPoints())
    }
}
```

```dart
// Flutter
final fontSize = AppDimens.fluid(16, maxValue: 24).calculate(context);
final padding = AppDimens.fluid(8, maxValue: 16).calculate(context);
```

**Availability:** All platforms

**When to use:**
- ✅ Typography with explicit bounds
- ✅ Line heights and letter spacing
- ✅ Smooth transitions across breakpoints
- ❌ Not for general UI elements (use BALANCED instead)

### Smart API with Auto-Inference 🧠

Automatic strategy selection based on element type:

```kotlin
// Android - Automatic strategy selection
val buttonSize = 48.smart().forElement(ElementType.BUTTON).dp
// → Automatically selects BALANCED for buttons on tablets

val containerWidth = 300.smart().forElement(ElementType.CONTAINER).dp
// → Automatically selects PERCENTAGE for containers
```

```swift
// iOS
let buttonSize = AppDimens.shared.smart(48).forElement(.button).toPoints()
```

```dart
// Flutter
final buttonSize = AppDimens.smart(48).forElement(ElementType.button).calculate(context);
```

### Custom Scaling Rules

```kotlin
// Android - Different sizes for different devices
val buttonSize = 56.balanced()
    .screen(UiModeType.TV, 96.dp)           // TVs: 96dp
    .screen(UiModeType.WATCH, 40.dp)        // Watches: 40dp
    .screen(DpQualifier.SMALL_WIDTH, 600, 72.dp)  // Tablets: 72dp
    .dp  // Others: auto-scaled from 56dp
```

### 📏 Physical Units

Real-world measurements across all platforms:

```kotlin
// Android - Physical units (mm, cm, inch)
val buttonWidth = 10.mm   // 10 millimeters
val cardWidth = 8.cm      // 8 centimeters
val screenSize = 5.inch   // 5 inches

// AppDimens Games also supports physical units
val playerSize = appDimensGames.mm(15f)  // 15mm player sprite
```

```swift
// iOS - Same API
let buttonWidth = AppDimensPhysicalUnits.mm(10)
let cardWidth = AppDimensPhysicalUnits.cm(8)
let screenSize = AppDimensPhysicalUnits.inch(5)
```

```dart
// Flutter
final buttonWidth = AppDimensPhysicalUnits.mmToPixels(10, context);
final cardWidth = AppDimensPhysicalUnits.cmToPixels(8, context);
```

**Available everywhere:** Android (Code + Compose), iOS (SwiftUI + UIKit), Flutter, React Native, Web

### 🎮 Game Development

Specialized high-performance modules for game development:

#### Android Games Module (C++/NDK)
```kotlin
val appDimensGames = AppDimensGames.getInstance()
appDimensGames.initialize(context)

// Game-specific dimension types
val buttonSize = appDimensGames.calculateButtonSize(48f)      // UI elements
val playerSize = appDimensGames.calculatePlayerSize(64f)      // Game world
val enemySize = appDimensGames.calculateEnemySize(32f)        // Game world
val uiOverlay = appDimensGames.calculateUISize(24f)           // HUD/Overlay

// Vector and Rectangle operations
val position = GameVector2D(100f, 200f)
val scaledPos = appDimensGames.calculateVector2D(position, GameDimensionType.GAME_WORLD)

val bounds = GameRectangle(0f, 0f, 800f, 600f)
val scaledBounds = appDimensGames.calculateRectangle(bounds, GameDimensionType.DYNAMIC)

// Physical units in games
val physicalSize = appDimensGames.cm(2f)  // 2cm for touch targets
```

**Features:**
- ✅ Native C++/NDK for maximum performance
- ✅ 4 dimension types: DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY
- ✅ Vector2D and Rectangle utilities
- ✅ OpenGL ES integration
- ✅ Physical units support (mm, cm, inch)
- ✅ Performance monitoring and optimization
- ✅ Separate dependency - not included in appdimens-all

#### iOS Games Module (Metal)
```swift
// Metal/MetalKit integration
let buttonSize = gameUniform(48)           // Uniform scaling
let playerSize = gameAspectRatio(64)       // Aspect-ratio aware
let uiOverlay = gameViewport(24)           // Viewport-based

// SwiftUI Integration
struct GameView: View {
    var body: some View {
        MetalGameView()
            .frame(
                width: gameAspectRatio(320),
                height: gameAspectRatio(240)
            )
            .withAppDimens()
    }
}
```

**Features:**
- ✅ Native Metal/MetalKit support
- ✅ 5 viewport modes: Uniform, Horizontal, Vertical, AspectRatio, Viewport
- ✅ SIMD extensions for performance
- ✅ SwiftUI and UIKit compatible
- ✅ Coordinate transformations (Screen ↔ NDC)

**📖 [Android Game Development Guide](Android/appdimens_games/README.md)**
**📖 [iOS Game Development Guide](iOS/README.md#game-development-features)**

### ⚡ Global Cache Control

Control caching behavior globally across all AppDimens instances:

```kotlin
// Android - Global cache control
AppDimens.setGlobalCache(true)   // Enable (default)
AppDimens.setGlobalCache(false)  // Disable all caches
AppDimens.clearAllCaches()       // Clear all cached values

// Per-instance cache control
val dimension = AppDimens.balanced(100)
    .cache(true)  // Enable cache for this instance
    .toDp(resources)

// Check cache status
val isEnabled = AppDimens.isGlobalCacheEnabled()
```

```swift
// iOS - Same concept
AppDimensGlobal.globalCacheEnabled = true
AppDimensGlobal.clearAllCaches()

// Per-instance
let dimension = AppDimens.shared.balanced(100)
    .cache(true)
    .toPoints()
```

```dart
// Flutter
AppDimens.setGlobalCache(true);
AppDimens.clearAllCaches();

// Per-instance
final dimension = AppDimens.balanced(100)
    .cache(true)
    .calculate(context);
```

**Features:**
- ✅ Global cache control affects all instances
- ✅ Per-instance cache control for fine-tuning
- ✅ Automatic cache invalidation on configuration changes
- ✅ Zero performance overhead when disabled
- ✅ Memory efficient with automatic cleanup

---

## 🤝 Contributing

We welcome contributions!

- 🐛 [Report bugs](https://github.com/bodenberg/appdimens/issues)
- 💡 [Suggest features](https://github.com/bodenberg/appdimens/discussions)
- 📝 Improve documentation
- ⭐ Star this repo!

**📖 [Contributing Guidelines](CONTRIBUTING.md)**

---

## 📄 License

Apache License 2.0 - see [LICENSE](LICENSE) file

---

## 👨‍💻 Author

**Jean Bodenberg**
- GitHub: [@bodenberg](https://github.com/bodenberg)
- Website: [appdimens-project.web.app](https://appdimens-project.web.app/)

---

## 🌟 Support

If AppDimens helps your project:

- ⭐ **Star** this repository
- 🐦 **Share** on social media
- 📝 **Write** a review
- 🤝 **Contribute** to the project

---

<div align="center">

**Made with ❤️ for developers worldwide**

[Documentation](DOCS/README.md) • [Examples](DOCS/EXAMPLES.md) • [Technical Guide](DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

</div>
