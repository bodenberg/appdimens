# 📐 AppDimens for Android

**Smart Responsive Dimensions for Android**  
*Version: 2.0.0 | Last Updated: February 2025*

> **Languages:** English | [Português (BR)](../LANG/pt-BR/Android/README.md) | [Español](../LANG/es/Android/README.md)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.bodenberg/appdimens-dynamic)](https://search.maven.org/artifact/io.github.bodenberg/appdimens-dynamic)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg)](https://android-arsenal.com/api?level=21)

---

## 🆕 What's New in Version 2.0

- 🎯 **13 Scaling Strategies** (up from 2)
- ⭐ **BALANCED** - New primary recommendation (hybrid linear-logarithmic)
- 🔬 **Perceptual Models** (Weber-Fechner, Stevens' Power Law)
- 🧠 **Smart Inference** - Automatic strategy selection
- ⚡ **5x Performance** - Lock-free cache, optimizations
- ♻️ **Full Backward Compatibility** - v1.x code still works

---

## 🚀 Installation

```kotlin
dependencies {
    // Core library (13 strategies + Physical Units)
    implementation("io.github.bodenberg:appdimens-dynamic:2.0.0")
    
    // SDP (Scalable DP for XML)
    implementation("io.github.bodenberg:appdimens-sdps:2.0.0")
    
    // SSP (Scalable SP for text)
    implementation("io.github.bodenberg:appdimens-ssps:2.0.0")
    
    // All-in-one (dynamic + sdps + ssps)
    implementation("io.github.bodenberg:appdimens-all:2.0.0")
    
    // Games module (C++/NDK)
    implementation("io.github.bodenberg:appdimens-games:2.0.0")
}
```

---

## ⚡ Quick Start

### Jetpack Compose (Recommended)

```kotlin
@Composable
fun MyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.balanced().dp)  // ⭐ BALANCED (Primary)
    ) {
        Text(
            text = "Hello World",
            fontSize = 18.balanced().sp
        )
        
        Button(
            onClick = { },
            modifier = Modifier
                .height(48.balanced().dp)
                .fillMaxWidth()
        ) {
            Text("Click Me", fontSize = 16.balanced().sp)
        }
    }
}
```

### XML Layouts

```xml
<LinearLayout
    android:padding="@dimen/_16sdp">
    
    <TextView
        android:textSize="@dimen/_18ssp"
        android:text="Hello World" />
        
    <Button
        android:layout_height="@dimen/_48sdp"
        android:text="Click Me" />
</LinearLayout>
```

---

## 🎯 13 Scaling Strategies

### Primary: BALANCED ⭐ (Multi-Device)

```kotlin
Text("Hello", fontSize = 16.balanced().sp)
Button(modifier = Modifier.height(48.balanced().dp))
```

**Use for:** 95% of apps (phones, tablets, TVs)

### Secondary: DEFAULT (Phone-Focused)

```kotlin
Icon(modifier = Modifier.size(24.defaultDp))
```

**Use for:** Phone-only apps, backward compatibility

### Others

```kotlin
// PERCENTAGE (Large containers)
Container(modifier = Modifier.width(300.percentageDp.dp))

// LOGARITHMIC (TV apps)
Text("TV Text", fontSize = 20.logarithmic().sp)

// POWER (Configurable)
Text("Power", fontSize = 16.power(0.75f).sp)

// FLUID (Typography)
Text("Title", fontSize = fluidSp(16f, 24f))

// Smart API (Auto-select)
Button(modifier = Modifier.height(
    AppDimens.from(48).smart().forElement(ElementType.BUTTON).dp
))
```

---

## 📚 Modules

### appdimens-dynamic
Core library with 13 strategies, Physical Units, Grid calculations.

**[📖 Dynamic Module Guide](appdimens_dynamic/README.md)**

### appdimens-sdps
Scalable DP for XML layouts (426 pre-calculated values).

**[📖 SDP Module Guide](appdimens_sdps/README.md)**

### appdimens-ssps
Scalable SP for text in XML (269 pre-calculated values).

**[📖 SSP Module Guide](appdimens_ssps/README.md)**

### appdimens-all
All-in-one package (dynamic + sdps + ssps).

**[📖 All Module Guide](appdimens_all/README.md)**

### appdimens-games
C++/NDK module for game development with OpenGL ES.

**[📖 Games Module Guide](appdimens_games/README.md)**

---

## 📖 Documentation

- [Main Documentation](../DOCS/README.md)
- [Mathematical Theory](../DOCS/MATHEMATICAL_THEORY.md)
- [Examples](../DOCS/EXAMPLES.md)
- [Quick Reference](../DOCS/DOCS_QUICK_REFERENCE.md)

---

**Author:** Jean Bodenberg  
**License:** Apache 2.0  
**Repository:** https://github.com/bodenberg/appdimens
