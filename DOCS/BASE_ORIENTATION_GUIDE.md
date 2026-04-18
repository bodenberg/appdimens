# 🔄 Base Orientation Guide - Auto-Inversion Feature

> **Languages:** English | [Português (BR)](../LANG/pt-BR/README.md) | [Español](../LANG/es/README.md) — there is no separate translated file for this topic under `LANG/`; use this document as canonical.

**Complete Guide to Orientation-Aware Dimension Scaling**  
*Author: Jean Bodenberg*  
*Date: February 2025*  
*Version: 2.0.0*

> **🆕 Version 2.0:** Base Orientation now works with all 13 scaling strategies, including BALANCED, DEFAULT, LOGARITHMIC, POWER, and others.

---

## 📋 Table of Contents

1. [Overview](#1-overview)
2. [The Problem](#2-the-problem)
3. [The Solution](#3-the-solution)
4. [How It Works](#4-how-it-works)
5. [API Reference](#5-api-reference)
6. [Examples by Strategy](#6-examples-by-strategy)
7. [Best Practices](#7-best-practices)
8. [Platform-Specific Notes](#8-platform-specific-notes)

---

## 1. Overview

**Base Orientation** allows you to specify the orientation for which your design was originally created. When the device rotates, AppDimens automatically inverts the `LOWEST`/`HIGHEST` screen types to maintain visual proportions.

### Key Concept

```
PORTRAIT Design + LANDSCAPE Current = Auto-invert LOWEST↔HIGHEST
LANDSCAPE Design + PORTRAIT Current = Auto-invert LOWEST↔HIGHEST
AUTO (default) = No inversion
```

**Works with ALL strategies:** BALANCED, DEFAULT, PERCENTAGE, LOGARITHMIC, POWER, etc.

---

## 2. The Problem

### Without Base Orientation

Design a card for portrait using `LOWEST` (width):

```kotlin
// Card designed for portrait (360x800) — illustrative sizes only.
// In appdimens-dynamic 3.x, orientation + LOWEST/HIGHEST are handled via
// axis tokens and inverter / rotation helpers (see submodule DOCUMENTATION).
val cardWidth = 300.wdp

// Portrait (360x800):  width basis → Card tracks width ✅
// Landscape (800x360): re-check axis / inverters so the card does not stay “phone narrow” ❌
```

**Problem:** Card looks small in landscape.

---

## 3. The Solution

### With Base Orientation (v1.2.0+)

```kotlin
// Portrait-first designs: on Android 3.x use builder / inverter APIs from
// appdimens-dynamic (e.g. portraitLowest-style flows on AppDimensFixed in KMP / code layer),
// or model width explicitly with wdp + qualifier tables.
val cardWidth = 300.wdp

// Portrait (360x800):  width-biased token tracks short edge as expected ✅
// Landscape (800x360): validate against submodule rotation examples ✅
```

**Benefits:**
- ✅ Automatic adaptation
- ✅ Cleaner code
- ✅ Maintains visual proportion
- ✅ Works with all 13 strategies

---

## 4. How It Works

### Orientation Detection

```
if (height > width) → Current = PORTRAIT
if (width > height) → Current = LANDSCAPE
```

### Inversion Logic

| Design | Current | Invert? | LOWEST→ | HIGHEST→ |
|--------|---------|---------|---------|----------|
| PORTRAIT | PORTRAIT | NO | LOWEST | HIGHEST |
| PORTRAIT | LANDSCAPE | YES | HIGHEST | LOWEST |
| LANDSCAPE | LANDSCAPE | NO | LOWEST | HIGHEST |
| LANDSCAPE | PORTRAIT | YES | HIGHEST | LOWEST |
| AUTO | any | NO | LOWEST | HIGHEST |

---

## 5. API Reference

### BaseOrientation Enum

```
PORTRAIT   - Design created for portrait
LANDSCAPE  - Design created for landscape
AUTO       - No specific orientation (default)
```

### Methods

**Explicit:**
```kotlin
.baseOrientation(BaseOrientation.PORTRAIT)
.type(ScreenType.LOWEST)
```

**Shorthands:**
- `.portraitLowest()` - Portrait design, width → auto-inverts to height in landscape
- `.portraitHighest()` - Portrait design, height → auto-inverts to width in landscape
- `.landscapeLowest()` - Landscape design, height → auto-inverts to width in portrait
- `.landscapeHighest()` - Landscape design, width → auto-inverts to height in portrait

---

## 6. Examples by Strategy

### 6.1 BALANCED Strategy ⭐

```kotlin
// Android — Jetpack Compose (appdimens-dynamic 3.x)
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.auto.asdp

@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.wdp)
            .padding(16.asdp)
    ) {
        Text("Auto-adapting Card")
    }
}
```

```swift
// iOS
struct ResponsiveCard: View {
    var body: some View {
        VStack {
            Text("Auto-adapting Card")
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
        .frame(width: AppDimens.shared.balanced(300).portraitLowest().toPoints())
    }
}
```

```dart
// Flutter
Container(
  width: AppDimens.fixed(300).portraitLowest().calculate(context),
  padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
  child: Text('Auto-adapting Card'),
)
```

### 6.2 DEFAULT Strategy

```kotlin
// Android — scaled icon; for rotation-specific overrides see sdpRotate / wdpRotate in submodule
Icon(
    imageVector = Icons.Default.Favorite,
    modifier = Modifier.size(24.sdp)
)
```

### 6.3 PERCENTAGE Strategy

```kotlin
// Large container with orientation — width-biased token; pair with submodule percent APIs if needed
Container(
    modifier = Modifier.width(400.wdp)
)
```

---

## 7. Best Practices

**✅ Do:**
- Use `portraitLowest()` for portrait-first designs
- Use `landscapeHighest()` for landscape-first designs
- Test in both orientations
- Use with BALANCED for best results

**❌ Don't:**
- Mix different base orientations randomly
- Use without testing rotation
- Forget AUTO is the default (no inversion)

---

## 8. Platform-Specific Notes

### Android
- Works with Compose and Views
- Automatic configuration change detection
- Cache invalidation on rotation

### iOS
- Works with SwiftUI and UIKit
- Trait collection observation
- Automatic updates

### Flutter
- Works with MediaQuery
- Rebuild on orientation change
- Context-aware

### React Native & Web
- Works with Dimensions API
- Event listeners for resize
- Responsive hooks

---

**Document created by:** Jean Bodenberg  
**Last updated:** February 2025  
**Version:** 2.0.0  
**Repository:** https://github.com/bodenberg/appdimens

---

**[⬆ Back to Top](#-base-orientation-guide---auto-inversion-feature)**
