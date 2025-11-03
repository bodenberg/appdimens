# 🔄 Base Orientation Guide - Auto-Inversion Feature

> **Languages:** English | [Português (BR)](../LANG/pt-BR/BASE_ORIENTATION_GUIDE.md) | [Español](../LANG/es/BASE_ORIENTATION_GUIDE.md)

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
// Card designed for portrait (360x800)
val cardWidth = 300.balanced().type(ScreenType.LOWEST).dp

// Portrait (360x800):  Uses LOWEST = 360 → Card ~336dp ✅ (93% of width)
// Landscape (800x360): Uses LOWEST = 360 → Card ~336dp ❌ (42% of width - too small!)
```

**Problem:** Card looks small in landscape.

---

## 3. The Solution

### With Base Orientation (v1.2.0+)

```kotlin
// Tell AppDimens this design was made for portrait
val cardWidth = 300.balanced().portraitLowest().dp

// Portrait (360x800):  Uses LOWEST (width) = 360 → ~336dp ✅
// Landscape (800x360): AUTO-INVERTS to HIGHEST (width) = 800 → ~896dp ✅
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
// Android
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.balanced().portraitLowest().dp)  // ⭐ BALANCED + portrait
            .padding(16.balanced().dp)
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
  width: AppDimens.balanced(300).portraitLowest().calculate(context),
  padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
  child: Text('Auto-adapting Card'),
)
```

### 6.2 DEFAULT Strategy

```kotlin
// Android - Phone-focused with orientation
Icon(
    imageVector = Icons.Default.Favorite,
    modifier = Modifier.size(24.defaultDp.portraitLowest())
)
```

### 6.3 PERCENTAGE Strategy

```kotlin
// Large container with orientation
Container(
    modifier = Modifier.width(400.percentageDp.portraitLowest().dp)
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
