# 🔄 Base Orientation Guide - Auto-Inversion Feature

> **Languages:** English | [Português (BR)](../LANG/pt-BR/BASE_ORIENTATION_GUIDE.md) | [Español](../LANG/es/BASE_ORIENTATION_GUIDE.md)

**Complete Guide to Orientation-Aware Dimension Scaling**  
*Author: Jean Bodenberg*  
*Date: January 2025*  
*Version: 1.2.0*

---

## 📋 Table of Contents

1. [Overview](#overview)
2. [The Problem](#the-problem)
3. [The Solution](#the-solution)
4. [How It Works](#how-it-works)
5. [API Reference](#api-reference)
6. [Examples](#examples)
7. [Best Practices](#best-practices)
8. [Platform-Specific Notes](#platform-specific-notes)

---

## Overview

**Base Orientation** is a feature that allows you to specify the orientation for which your design was originally created. When the device rotates to a different orientation, AppDimens automatically inverts the `LOWEST`/`HIGHEST` screen types to maintain visual proportions.

### Key Concept

```
PORTRAIT Design + LANDSCAPE Current = Auto-invert LOWEST↔HIGHEST
LANDSCAPE Design + PORTRAIT Current = Auto-invert LOWEST↔HIGHEST
AUTO (default) = No inversion (current behavior)
```

---

## The Problem

### Without Base Orientation

When you design a card for portrait orientation using `LOWEST` (width):

```kotlin
// Card designed for portrait (360x800)
val cardWidth = 300.fixedDp().type(ScreenType.LOWEST).dp

// Portrait (360x800):  Uses LOWEST = 360 → Card ~336dp ✅ (93% of width)
// Landscape (800x360): Uses LOWEST = 360 → Card ~336dp ❌ (42% of width - too small!)
```

**Problem:** The card looks small in landscape because it still uses width (360) instead of height (800).

### Manual Workaround (Before v1.2.0)

```kotlin
val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
val cardWidth = if (isLandscape) {
    300.fixedDp().type(ScreenType.HIGHEST).dp  // Use height in landscape
} else {
    300.fixedDp().type(ScreenType.LOWEST).dp   // Use width in portrait
}
```

**Problem:** Repetitive, error-prone, hard to maintain.

---

## The Solution

### With Base Orientation (v1.2.0+)

```kotlin
// Tell AppDimens this design was made for portrait
val cardWidth = 300.fixedDp().portraitLowest().dp

// Portrait (360x800):  Uses LOWEST (width) = 360 → ~336dp ✅
// Landscape (800x360): AUTO-INVERTS to HIGHEST (width) = 800 → ~896dp ✅
```

**Benefits:**
- ✅ Automatic adaptation
- ✅ Cleaner code
- ✅ Maintains visual proportion
- ✅ Works across all platforms

---

## How It Works

### Orientation Detection

AppDimens detects current orientation by comparing dimensions:

```
if (height > width) → Current orientation is PORTRAIT
if (width > height) → Current orientation is LANDSCAPE
```

### Inversion Logic

```
┌─────────────────────────────────────────────────────────┐
│  Design      Current      Invert?   LOWEST→   HIGHEST→ │
├─────────────────────────────────────────────────────────┤
│  PORTRAIT    PORTRAIT     NO        LOWEST    HIGHEST   │
│  PORTRAIT    LANDSCAPE    YES       HIGHEST   LOWEST    │
│  LANDSCAPE   LANDSCAPE    NO        LOWEST    HIGHEST   │
│  LANDSCAPE   PORTRAIT     YES       HIGHEST   LOWEST    │
│  AUTO        any          NO        LOWEST    HIGHEST   │
└─────────────────────────────────────────────────────────┘
```

### Visual Example

```
Design for PORTRAIT, use LOWEST (width):

Portrait 360x800:
┌──────────────────┐
│                  │
│  ┌────────────┐  │
│  │    Card    │  │  Card uses width (360) ✅
│  │   300dp    │  │
│  └────────────┘  │
│                  │
└──────────────────┘

Landscape 800x360 (AUTO-INVERTED to use width still):
┌─────────────────────────────────────────┐
│  ┌────────────────────────────┐        │
│  │          Card              │        │  Card uses width (800) ✅
│  │         300dp              │        │
│  └────────────────────────────┘        │
└─────────────────────────────────────────┘
```

---

## API Reference

### BaseOrientation Enum

```kotlin
enum class BaseOrientation {
    PORTRAIT,   // Design created for portrait (height > width)
    LANDSCAPE,  // Design created for landscape (width > height)
    AUTO        // No specific orientation (default - no inversion)
}
```

### Method: `.baseOrientation(orientation)`

**Explicit method to set base orientation:**

```kotlin
val size = 16.fixedDp()
    .baseOrientation(BaseOrientation.PORTRAIT)
    .type(ScreenType.LOWEST)
    .dp
```

### Shorthand Methods

**Portrait Shorthands:**

- `.portraitLowest()` - Portrait design, uses width (lowest in portrait), auto-inverts to height in landscape
- `.portraitHighest()` - Portrait design, uses height (highest in portrait), auto-inverts to width in landscape

**Landscape Shorthands:**

- `.landscapeLowest()` - Landscape design, uses height (lowest in landscape), auto-inverts to width in portrait
- `.landscapeHighest()` - Landscape design, uses width (highest in landscape), auto-inverts to height in portrait

---

## Examples

### Android Jetpack Compose

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.fxPortraitLowest)      // Extension shortcut
            .padding(16.fxdp)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 24.fixedDp().portraitLowest().dp,  // Method chain
                vertical = 16.fxdp
            )
        ) {
            Text("Auto-adapting Card")
        }
    }
}
```

### iOS SwiftUI

```swift
struct ResponsiveCard: View {
    let cardWidth = AppDimensFixedCalculator(300).portraitLowest()
    
    var body: some View {
        VStack {
            Text("Auto-adapting Card")
        }
        .fxFrame(width: cardWidth.pt)
        .fxPadding(16)
    }
}
```

### Flutter

```dart
class ResponsiveCard extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      width: AppDimensFixed(300).portraitLowest().calculate(context),
      padding: EdgeInsets.all(16.fx.calculate(context)),
      child: Text('Auto-adapting Card'),
    );
  }
}
```

### React Native

```tsx
function ResponsiveCard() {
  const {fx} = useAppDimens();
  
  return (
    <View style={{
      width: fixedDp(300).portraitLowest().calculate(),
      padding: fx(16),
    }}>
      <Text>Auto-adapting Card</Text>
    </View>
  );
}
```

### Web (React)

```jsx
function ResponsiveCard() {
  const {fx} = useWebDimens();
  
  return (
    <div style={{
      width: fixed(300).portraitLowest().toPx(),
      padding: fx(16),
    }}>
      <p>Auto-adapting Card</p>
    </div>
  );
}
```

---

## Best Practices

### When to Use Base Orientation

✅ **USE when:**
- Design was created primarily for one orientation
- You want elements to maintain visual proportion when rotated
- Building tablet apps that support both orientations
- Creating games that adapt to orientation changes

❌ **DON'T USE when:**
- Design works equally well in both orientations
- Using orientation-specific layouts (portrait.xml / landscape.xml)
- Elements should genuinely be smaller/larger in different orientations

### Choosing LOWEST vs HIGHEST

**For Portrait Designs:**
- Use `.portraitLowest()` for elements that scale with **width** (most cards, dialogs, forms)
- Use `.portraitHighest()` for elements that scale with **height** (vertical lists, scrolling content)

**For Landscape Designs:**
- Use `.landscapeLowest()` for elements that scale with **height** (toolbars, headers)
- Use `.landscapeHighest()` for elements that scale with **width** (wide panels, dashboards)

### Combining with Other Features

```kotlin
val adaptiveSize = 48.fixedDp()
    .portraitLowest()                         // Base orientation
    .screen(UiModeType.TV, 96.dp)            // Override for TV
    .screen(DpQualifier.SMALL_WIDTH, 600, 72.dp)  // Override for tablets
    .aspectRatio(true, sensitivityK = 0.12f) // Custom sensitivity
    .dp
```

---

## Platform-Specific Notes

### Android

- Works with both View System and Jetpack Compose
- Uses `Configuration.screenWidthDp` and `Configuration.screenHeightDp` for detection
- Compose extensions: `.fxPortraitLowest`, `.dyLandscapeHighest`, etc.

### iOS

- Works with both UIKit and SwiftUI
- Uses `CGRect bounds` for dimension detection
- View modifiers available for SwiftUI

### Flutter

- Uses `MediaQuery.of(context).size` for detection
- Works seamlessly with widget rebuilds on rotation

### React Native

- Uses `Dimensions.get('window')` for detection
- Automatically reacts to dimension changes

### Web

- Uses `window.innerWidth` and `window.innerHeight` for detection
- Responsive to window resize events

### Games (Android/iOS)

- C++/Metal implementations for high performance
- Useful for adaptive UI overlays in games
- Helper functions for game world coordinates

---

## Migration Guide

### From v1.1.0 to v1.2.0

**No breaking changes!** Base orientation is opt-in:

```kotlin
// Old code still works (uses AUTO by default)
val size = 16.fixedDp().type(ScreenType.LOWEST).dp  // Still works!

// New code with base orientation
val size = 16.fixedDp().portraitLowest().dp  // New feature!
```

### Migrating Manual Orientation Logic

**Before:**
```kotlin
val width = if (isLandscape) {
    300.fixedDp().type(ScreenType.HIGHEST).dp
} else {
    300.fixedDp().type(ScreenType.LOWEST).dp
}
```

**After:**
```kotlin
val width = 300.fixedDp().portraitLowest().dp  // That's it!
```

---

## Troubleshooting

### Q: My dimensions don't change on rotation

**A:** Make sure you're NOT using `AUTO` orientation:
```kotlin
// Wrong - uses AUTO (no inversion)
val size = 16.fixedDp().type(ScreenType.LOWEST).dp

// Right - uses PORTRAIT (auto-inverts)
val size = 16.fixedDp().portraitLowest().dp
```

### Q: Dimensions invert when they shouldn't

**A:** Check if your base orientation matches your design:
```kotlin
// If your design is for landscape, use landscape methods
val size = 16.fixedDp().landscapeLowest().dp  // Not portraitLowest!
```

### Q: How to debug orientation inversion?

**A:** Use the `wouldInvert()` utility function (RN/Web):
```typescript
import {wouldInvert} from 'appdimens/utils/orientationResolver';

const willInvert = wouldInvert('portrait', {width: 800, height: 600});
console.log(`Inversion will occur: ${willInvert}`);  // true (landscape)
```

---

## Technical Implementation

### Resolution Algorithm

```typescript
function resolveScreenType(
  requestedType: 'lowest' | 'highest',
  baseOrientation: 'portrait' | 'landscape' | 'auto',
  dimensions: {width, height}
): 'lowest' | 'highest' {
  // 1. If AUTO, return as-is
  if (baseOrientation === 'auto') return requestedType;
  
  // 2. Detect current orientation
  const currentIsPortrait = height > width;
  
  // 3. Check if inversion needed
  const shouldInvert = 
    (baseOrientation === 'portrait' && !currentIsPortrait) ||
    (baseOrientation === 'landscape' && currentIsPortrait);
  
  // 4. Invert if needed
  if (shouldInvert) {
    return requestedType === 'lowest' ? 'highest' : 'lowest';
  }
  
  return requestedType;
}
```

### Performance

- **Zero overhead when `baseOrientation = AUTO`** (default)
- Simple boolean comparisons (< 1µs)
- No additional allocations
- Cache-friendly (orientation rarely changes)

---

## Changelog

- **v1.2.0 (2025-01-31):** Initial release of Base Orientation feature
- Available in all platforms: Android, iOS, Flutter, React Native, Web, Games

---

## See Also

- [Complete Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md)
- [Mathematical Theory](MATHEMATICAL_THEORY.md)
- [Examples](EXAMPLES.md)
- [Quick Reference](DOCS_QUICK_REFERENCE.md)

---

**Document created by:** Jean Bodenberg  
**Last updated:** January 2025  
**Version:** 1.2.0  
**License:** Apache 2.0  
**Repository:** https://github.com/bodenberg/appdimens

