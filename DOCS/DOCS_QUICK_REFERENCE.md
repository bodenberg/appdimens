# ⚡ AppDimens Quick Reference

> **Languages:** English | [Português (BR)](../LANG/pt-BR/DOCS_QUICK_REFERENCE.md) | [Español](../LANG/es/DOCS_QUICK_REFERENCE.md)

**Fast Lookup for AppDimens 2.0**  
*Version: 2.0.0 | Last Updated: April 2026 (docs synced with submodule APIs)*

> **Find anything in seconds!** This is your go-to reference for quick lookups.

---

## 🎯 Quick Decision (30 Seconds)

```
What's your app type?
├─ Multi-device (phones + tablets + TVs) → BALANCED ⭐
├─ Phone-only → DEFAULT
├─ Large containers/images → PERCENTAGE  
├─ TV app → LOGARITHMIC
├─ Typography → FLUID
└─ Games → FIT or FILL
```

---

## 📊 13 Strategies at a Glance

| # | Strategy | Formula | AR Support | Use Case | Score |
|---|----------|---------|------------|----------|-------|
| 1 | **BALANCED** ⭐ | Hybrid linear-log | ✅ Yes | Multi-device (PRIMARY) | 93/100 |
| 2 | LOGARITHMIC | `1 + k×ln(W/300)` | ✅ Yes | TV, max control | 88/100 |
| 3 | POWER | `(W/300)^0.75` | ✅ Yes | General, configurable | 86/100 |
| 4 | **DEFAULT** | `~97% linear + AR` | ✅ Yes | Phone-focused (SECONDARY) | 82/100 |
| 5 | FLUID | `clamp(min, max)` | ⚙️ Opt-in | Typography | 78/100 |
| 6 | AUTOSIZE 🆕 | Container-aware | ❌ No | Dynamic text | 78/100 |
| 7 | FIT | `min(W/300, H/533)` | ❌ No | Games (letterbox) | 75/100 |
| 8 | FILL | `max(W/300, H/533)` | ❌ No | Games (cover) | 73/100 |
| 9 | DIAGONAL | `√(W²+H²)/611.63` | ❌ No | Physical size | 72/100 |
| 10 | INTERPOLATED | `50% linear` | ✅ Yes | Moderate | 70/100 |
| 11 | PERIMETER | `(W+H)/833` | ❌ No | W+H balance | 70/100 |
| 12 | PERCENTAGE | `W/300` | ❌ No | Containers ONLY | 62/100 |
| 13 | NONE | `x` | ❌ No | No scaling | 55/100 |

---

## 💻 API Quick Reference

### Android (Kotlin)

```kotlin
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.auto.asdp
import com.appdimens.dynamic.compose.auto.assp

// Scaled (README default): smallest-width / width / height axis
16.sdp
100.wdp
48.hdp
16.ssp

// Auto (“BALANCED-like” hybrid on that axis) — see appdimens-dynamic DOCUMENTATION/auto.md
Modifier.padding(16.asdp)
Text("", fontSize = 16.assp)

// Other strategies live in their own packages (logarithmic, power, fluid, percent, …)
```

### iOS (Swift)

```swift
// BALANCED ⭐
AppDimens.shared.balanced(16).toPoints()

// DEFAULT
AppDimens.shared.defaultScaling(16).toPoints()

// PERCENTAGE
AppDimens.shared.percentage(300).toPoints()

// Others
AppDimens.shared.logarithmic(16).toPoints()
AppDimens.shared.power(16, exponent: 0.75).toPoints()
AppDimens.shared.fluid(min: 14, max: 20).toPoints()
AppDimens.shared.smart(48).forElement(.button).toPoints()
```

### Flutter (Dart)

```dart
// import appdimens + extensions (.fx / .dy) from the package

// Fixed builder (log-style / general UI — tune via builder chain in submodule)
AppDimens.fixed(16).calculate(context)
16.0.fx.calculate(context)

// Dynamic builder (stronger growth / containers)
AppDimens.dynamic(300).calculate(context)
300.0.dy.calculate(context)

// Literal % of screen (utility on AppDimens)
AppDimens.dynamicPercentageDp(0.5, context)
```

### React Native (TypeScript)

```typescript
const {balanced, defaultScaling, percentage, smart} = useAppDimens();

balanced(16)
defaultScaling(16)
percentage(300)
smart(48).forElement('button')
```

### Web (TypeScript)

```typescript
const {balanced, defaultScaling, percentage, fluid, smart} = useWebDimens();

balanced(16)
defaultScaling(16)
percentage(300)
fluid(14, 20)
smart(48).forElement('button')
```

---

## 📐 Formula Quick Reference

```
BALANCED:    x × (W/300) if W<480, else x × (1.6 + 0.4×ln(1+(W-480)/300))
DEFAULT:     x × [1 + (W-300) × (0.00333 + 0.00267×ln(AR/1.78))]
PERCENTAGE:  x × (W/300)
LOGARITHMIC: x × (1 + 0.40×ln(W/300))
POWER:       x × (W/300)^0.75
FLUID:       clamp(min, interpolate(W), max)
```

---

## 🎯 Results Table (48dp base)

| Screen | BALANCED | DEFAULT | PERCENTAGE | Use |
|--------|----------|---------|------------|-----|
| 360dp  | 58dp     | 54dp    | 58dp       | Phone |
| 480dp  | 77dp     | 65dp    | 77dp       | Phablet |
| 720dp  | 70dp ⭐  | 79dp    | 115dp ❌   | Tablet |
| 1080dp | 101dp    | 94dp    | 173dp ❌   | TV |

### 📐 Aspect Ratio (AR) Impact Comparison (48dp base)

**Devices with different aspect ratios:**

| Device | Resolution | AR | Strategy | Without AR | With AR | Impact |
|--------|------------|-----|----------|------------|---------|---------|
| **Phone (Standard)** | 360×640 | 1.78 | BALANCED | 57.6dp | 57.6dp | 0% (ref) |
| | | | DEFAULT | 53.6dp | 53.6dp | 0% (ref) |
| | | | LOGARITHMIC | 42.0dp | 42.0dp | 0% (ref) |
| **Phone (Elongated)** | 360×800 | 2.22 | BALANCED | 57.6dp | 57.9dp | +0.5% |
| | | | DEFAULT | 53.6dp | 54.2dp | +1.1% |
| | | | LOGARITHMIC | 42.0dp | 42.2dp | +0.5% |
| **Tablet (16:9)** | 720×1280 | 1.78 | BALANCED | 69.7dp | 69.7dp | 0% (ref) |
| | | | DEFAULT | 78.7dp | 78.7dp | 0% (ref) |
| | | | LOGARITHMIC | 67.2dp | 67.2dp | 0% (ref) |
| **Tablet (Elongated)** | 720×1600 | 2.22 | BALANCED | 69.7dp | 70.1dp | +0.6% |
| | | | DEFAULT | 78.7dp | 79.6dp | +1.1% |
| | | | LOGARITHMIC | 67.2dp | 67.5dp | +0.4% |
| **TV (16:9)** | 1080×1920 | 1.78 | BALANCED | 101.4dp | 101.4dp | 0% (ref) |
| | | | DEFAULT | 93.6dp | 93.6dp | 0% (ref) |
| | | | LOGARITHMIC | 85.3dp | 85.3dp | 0% (ref) |

> **Note:** AR=1.78 (16:9) is the reference, so AR adjustment is neutral (0%). Elongated screens (AR>1.78) get slight size increases (+0.5% to +1.1%), while wider screens (AR<1.78) get slight decreases.

---

## 🚀 Common Patterns

### Button (All Platforms)

**Android:**
```kotlin
Button(
    modifier = Modifier.height(48.sdp)
) {
    Text("Click", fontSize = 16.ssp)
}
```

**iOS:**
```swift
Button("Click") {}
    .frame(height: AppDimens.shared.balanced(48).toPoints())
```

**Flutter:**
```dart
ElevatedButton(
  style: ElevatedButton.styleFrom(
    minimumSize: Size(double.infinity, AppDimens.fixed(48).calculate(context))
  ),
)
```

### Card Padding

**Android:**
```kotlin
Card(modifier = Modifier.padding(16.sdp))
```

**iOS:**
```swift
VStack {}.padding(AppDimens.shared.balanced(16).toPoints())
```

**Flutter:**
```dart
Container(padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)))
```

### Icon Size

**Android:**
```kotlin
Icon(modifier = Modifier.size(24.sdp))  // scaled token; pick axis (sdp / wdp / hdp) per layout
```

**iOS:**
```swift
Image(systemName: "heart")
    .frame(width: AppDimens.shared.defaultScaling(24).toPoints())
```

**Flutter:**
```dart
Icon(Icons.favorite, size: AppDimens.fixed(24).calculate(context))
```

---

## 📚 Documentation Links

| Need | Document | Time |
|------|----------|------|
| **Quick start** | [Simplified Guide](MATHEMATICAL_THEORY_SIMPLIFIED.md) | 15min |
| **Examples** | [Examples](EXAMPLES.md) | 20min |
| **Math details** | [Mathematical Theory](MATHEMATICAL_THEORY.md) | 45min |
| **Comparisons** | [Formula Comparison](FORMULA_COMPARISON.md) | 30min |
| **Per-platform APIs** | [Platform API map](PLATFORM_API_MAP.md) | 10min |
| **Everything** | [Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) | 2h |

---

## 🔧 Common Tasks

### Install AppDimens

**Android:** `implementation("io.github.bodenberg:appdimens-dynamic:3.1.4")`  
**iOS:** `pod 'AppDimens', '~> 2.0.0'`  
**Flutter:** `appdimens: ^2.0.0`  
**RN:** `npm install appdimens-react-native@2.0.0`  
**Web:** `npm install webdimens@2.0.0`

### Choose Strategy

95% of apps → **BALANCED** ⭐  
Phone-only → **DEFAULT**  
Containers → **PERCENTAGE**

### Migrate from older unified Android docs

Legacy meta-docs used `.fxdp` / `.dydp` / `.balanced().dp`. On **`appdimens-dynamic` 3.x** use **`sdp` / `wdp` / `hdp` / `ssp`** (scaled) and **`asdp` / `ahdp` / `awdp` / `assp`** (auto). See the [appdimens-dynamic README](../appdimens-dynamic/README.md) and [Platform API map](PLATFORM_API_MAP.md).

---

**[⬆ Back to Top](#-appdimens-quick-reference)**
