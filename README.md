<div align="center">
   <img src="/IMAGES/image_sample_devices.png" alt="AppDimens - Responsive Design Across All Devices" height="300"/>
<h1>📐 AppDimens</h1>
<p><strong>Smart Responsive Dimensions for Any Screen</strong></p>

[![Version](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Android%20%7C%20iOS%20%7C%20Flutter%20%7C%20RN%20%7C%20Web-orange.svg)](https://github.com/bodenberg/appdimens)

[📚 Documentation](DOCS/README.md) | [⚡ Quick Reference](DOCS/DOCS_QUICK_REFERENCE.md) | [🔬 Technical Details](DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

> **Languages:** English | [Português (BR)](LANG/pt-BR/README.md) | [Español](LANG/es/README.md)
</div>

---

## ⚡ Quick Overview

**AppDimens** makes your UI elements scale perfectly across all devices - from phones to tablets, TVs, watches, and web browsers.

Instead of fixed sizes that look tiny on tablets or huge on watches, AppDimens uses **mathematical scaling** that adapts intelligently to screen size and aspect ratio.

### Why AppDimens?

```
❌ Without AppDimens:
   Phone (360dp): Button = 48dp (13% of screen) ✅ Good
   Tablet (800dp): Button = 48dp (6% of screen)  ❌ Too small!

✅ With AppDimens:
   Phone (360dp): Button = 48dp (13% of screen) ✅ Good
   Tablet (800dp): Button = 68dp (8.5% of screen) ✅ Perfect!
```

### Key Benefits

- ✅ **Perfect proportions** on any screen size
- ✅ **Works everywhere**: Android, iOS, Flutter, React Native, Web
- ✅ **Simple API**: Just add `.fxdp` or `.fxsp` to your dimensions
- ✅ **Mathematically proven**: Based on psychophysical research (Weber-Fechner Law)
- ✅ **Best performance**: Intelligent caching makes it faster than alternatives

---

## 🚀 Installation

### Android

```kotlin
dependencies {
    // Core library (Fixed + Dynamic scaling + Physical Units)
    // Includes: .fxdp, .dydp, Physical Units (mm/cm/inch), Grid calculations
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.8")
    
    // SDP scaling (Scalable DP for XML)
    // Includes: @dimen/_16sdp, etc.
    implementation("io.github.bodenberg:appdimens-sdps:1.0.8")
    
    // SSP scaling (Scalable SP for text in XML)
    // Includes: @dimen/_18ssp, etc.
    implementation("io.github.bodenberg:appdimens-ssps:1.0.8")
    
    // All-in-one (includes dynamic, sdps, ssps)
    // ⚠️ Note: Does NOT include games module
    implementation("io.github.bodenberg:appdimens-all:1.0.8")
    
    // Game development (C++/NDK + OpenGL)
    // 🎮 Separate dependency - not included in "all"
    implementation("io.github.bodenberg:appdimens-games:1.0.8")
}
```

### iOS

**CocoaPods:**
```ruby
# Full package (Main + UI)
pod 'AppDimens', '~> 1.0.8'

# Only Main module
pod 'AppDimens/Main', '~> 1.0.8'

# Games module (separate)
pod 'AppDimens/Games', '~> 1.0.8'
```

**Swift Package Manager:**
```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.8")
]
```

### Flutter

```yaml
dependencies:
  appdimens: ^1.0.8
```

### React Native

```bash
# npm
npm install appdimens-react-native@1.0.8

# yarn
yarn add appdimens-react-native@1.0.8
```

### Web

```bash
# npm
npm install webdimens@1.0.8

# yarn
yarn add webdimens@1.0.8

# pnpm
pnpm add webdimens@1.0.8
```

**Vanilla JavaScript (CDN):**
```html
<script src="https://cdn.jsdelivr.net/npm/webdimens@1.0.8/dist/index.js"></script>
<script>
  const { fixed, dynamic, fluid } = WebDimens;
  
  document.getElementById('myElement').style.width = fixed(300).toPx();
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
            .width(300.fxdp)      // ✨ Fixed scaling (RECOMMENDED)
            .padding(16.fxdp)     // ✨ Adapts to screen
    ) {
        Text(
            text = "Hello World",
            fontSize = 18.fxsp    // ✨ Readable everywhere
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
        
        // Dynamic scaling
        val width = 300.fixedDp().toPx(resources)
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
            android:layout_height="@{AppDimensExtKt.fixedDp(48).dp}"
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
            layoutParams.width = 200.fixedDp().toPx(resources).toInt()
            layoutParams.height = 56.fixedDp().toPx(resources).toInt()
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
                .font(.fxSystem(size: 18))
        }
        .fxPadding(16)
        .fxFrame(width: 300)
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
        containerView.layer.cornerRadius = 16.fxpt
        view.addSubview(containerView)
        
        let titleLabel = UILabel()
        titleLabel.text = "Hello World"
        titleLabel.fxFontSize(18)
        containerView.addSubview(titleLabel)
    }
}
```

### Flutter

```dart
Widget build(BuildContext context) {
  return Container(
    width: 300.fxdp(),
    padding: EdgeInsets.all(16.fxdp()),
    child: Text(
      'Hello World',
      style: TextStyle(fontSize: 18.fxsp()),
    ),
  );
}
```

### React Native

```jsx
function MyCard() {
  const { fx } = useAppDimens();
  
  return (
    <View style={{ width: fx(300), padding: fx(16) }}>
      <Text style={{ fontSize: fx(18) }}>
        Hello World
      </Text>
    </View>
  );
}
```

### Web (Vanilla JavaScript)

```html
<!DOCTYPE html>
<html>
<head>
  <script src="https://cdn.jsdelivr.net/npm/webdimens@1.0.8/dist/index.js"></script>
</head>
<body>
  <div id="container">
    <header id="header">
      <h1 id="title">Hello World</h1>
    </header>
  </div>
  
  <script type="module">
    import { webdimens } from 'https://cdn.jsdelivr.net/npm/webdimens@1.0.8/dist/index.mjs';
    
    // Apply fixed dimensions
    document.getElementById('header').style.height = webdimens.fx(64);
    document.getElementById('title').style.fontSize = webdimens.fl(24, 48);
    document.getElementById('container').style.padding = webdimens.dy(24);
  </script>
</body>
</html>
```

### Web (React)

```jsx
import { useWebDimens } from 'webdimens/react';

function MyCard() {
  const { fx, dy, fl } = useWebDimens();
  
  return (
    <div style={{ width: dy(300), padding: fx(16) }}>
      <h2 style={{ fontSize: fl(18, 24) }}>Hello World</h2>
    </div>
  );
}
```

### Web (Vue)

```vue
<template>
  <div :style="{ width: dy(300), padding: fx(16) }">
    <h2 :style="{ fontSize: fl(18, 24) }">Hello World</h2>
  </div>
</template>

<script setup>
import { useWebDimens } from 'webdimens/vue';

const { fx, dy, fl } = useWebDimens();
</script>
```

### Web (Svelte)

```svelte
<script>
  import { webDimensStore } from 'webdimens/svelte';
  
  $: wd = $webDimensStore;
  $: width = wd.dy(300);
  $: padding = wd.fx(16);
  $: fontSize = wd.fl(18, 24);
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
    this.width = wd.dy(300);
    this.padding = wd.fx(16);
    this.fontSize = wd.fl(18, 24);
  }
}
```

**📖 [More Examples](DOCS/EXAMPLES.md)**

---

## 🎯 Scaling Models

AppDimens offers multiple scaling strategies for different needs:

| Model | When to Use | Example |
|-------|-------------|---------|
| **Fixed (FX)** ⭐ **RECOMMENDED** | 95% of cases - buttons, text, padding, margins | `16.fxdp` |
| **Dynamic (DY)** | Large containers, full-width grids | `100.dydp` |
| **Fluid (FL)** | Typography with min/max bounds (Web) | `fl(16, 24)` |
| **SDP/SSP** | Legacy XML Android projects | `@dimen/_16sdp` |

**📖 [Understanding Scaling Models](DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md)**

---

## 🏆 Why AppDimens is #1

AppDimens was scientifically compared against 7 other scaling approaches:

```
🥇 #1 AppDimens:        91/100 ⭐⭐⭐⭐⭐
🥈 #2 RN Moderate:      78/100
🥉 #3 Flutter ScreenUtil: 72/100
   #4 SDP/SSP:          65/100
   #5 CSS vw/vh:        58/100
```

### What Makes It Better?

- ✅ **Only library** with logarithmic scaling (controls oversizing)
- ✅ **Only library** with automatic aspect ratio compensation
- ✅ **3.5× more accurate** than linear scaling
- ✅ **65% less oversizing** on tablets vs competitors
- ✅ **Faster with cache** (0.002µs vs 0.005µs)

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

### Custom Scaling Rules

```kotlin
// Android - Different sizes for different devices
val buttonSize = 56.fixedDp()
    .screen(UiModeType.TV, 96.dp)           // TVs: 96dp
    .screen(UiModeType.WATCH, 40.dp)        // Watches: 40dp
    .screen(DpQualifier.SMALL_WIDTH, 600, 72.dp)  // Tablets: 72dp
    .dp  // Others: auto-scaled from 56dp
```

### Physical Units

```kotlin
// Android - Real-world measurements
Rectangle()
    .width(2.cm)    // 2 centimeters
    .height(1.inch) // 1 inch
```

### Game Development

AppDimens includes specialized modules for game development:

- 🎮 **Android**: C++/NDK + OpenGL ES support
- 🎮 **iOS**: Metal + MetalKit integration
- 🎮 High-performance native calculations

**📖 [Game Development Guide](Android/appdimens_games/README.md)**

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
