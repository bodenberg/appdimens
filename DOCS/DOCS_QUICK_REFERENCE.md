# ⚡ AppDimens Quick Reference

> **Languages:** English | [Português (BR)](../LANG/pt-BR/DOCS_QUICK_REFERENCE.md) | [Español](../LANG/es/DOCS_QUICK_REFERENCE.md)

**Fast Lookup for AppDimens 2.0**  
*Version: 2.0.0 | Last Updated: February 2025*

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

| # | Strategy | Formula | Use Case | Score |
|---|----------|---------|----------|-------|
| 1 | **BALANCED** ⭐ | Hybrid linear-log | Multi-device (PRIMARY) | 93/100 |
| 2 | LOGARITHMIC | `1 + k×ln(W/300)` | TV, max control | 88/100 |
| 3 | POWER | `(W/300)^0.75` | General, configurable | 86/100 |
| 4 | **DEFAULT** | `~97% linear + AR` | Phone-focused (SECONDARY) | 82/100 |
| 5 | FLUID | `clamp(min, max)` | Typography | 78/100 |
| 6 | AUTOSIZE 🆕 | Container-aware | Dynamic text | 78/100 |
| 7 | FIT | `min(W/300, H/533)` | Games (letterbox) | 75/100 |
| 8 | FILL | `max(W/300, H/533)` | Games (cover) | 73/100 |
| 9 | DIAGONAL | `√(W²+H²)/611.63` | Physical size | 72/100 |
| 10 | INTERPOLATED | `50% linear` | Moderate | 70/100 |
| 11 | PERIMETER | `(W+H)/833` | W+H balance | 70/100 |
| 12 | PERCENTAGE | `W/300` | Containers ONLY | 62/100 |
| 13 | NONE | `x` | No scaling | 55/100 |

---

## 💻 API Quick Reference

### Android (Kotlin)

```kotlin
// BALANCED ⭐ (Primary)
16.balanced().dp
16.balanced().sp

// DEFAULT (Secondary)
16.defaultDp
16.defaultSp

// PERCENTAGE (Containers)
300.percentageDp.dp

// Others
16.logarithmic().sp
16.power(0.75f).sp
fluidSp(14f, 20f)
16.smart().forElement(ElementType.BUTTON).dp
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
// BALANCED ⭐
AppDimens.balanced(16).calculate(context)
16.0.balanced()

// DEFAULT
AppDimens.defaultScaling(16).calculate(context)

// PERCENTAGE
AppDimens.percentage(300).calculate(context)

// Others
AppDimens.logarithmic(16).calculate(context)
AppDimens.power(16, exponent: 0.75).calculate(context)
AppDimens.fluid(14, maxValue: 20).calculate(context)
AppDimens.smart(48).forElement(ElementType.button).calculate(context)
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

---

## 🚀 Common Patterns

### Button (All Platforms)

**Android:**
```kotlin
Button(
    modifier = Modifier.height(48.balanced().dp)
) {
    Text("Click", fontSize = 16.balanced().sp)
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
    minimumSize: Size(double.infinity, AppDimens.balanced(48).calculate(context))
  ),
)
```

### Card Padding

**Android:**
```kotlin
Card(modifier = Modifier.padding(16.balanced().dp))
```

**iOS:**
```swift
VStack {}.padding(AppDimens.shared.balanced(16).toPoints())
```

**Flutter:**
```dart
Container(padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)))
```

### Icon Size

**Android:**
```kotlin
Icon(modifier = Modifier.size(24.defaultDp))  // DEFAULT for icons
```

**iOS:**
```swift
Image(systemName: "heart")
    .frame(width: AppDimens.shared.defaultScaling(24).toPoints())
```

**Flutter:**
```dart
Icon(Icons.favorite, size: AppDimens.defaultScaling(24).calculate(context))
```

---

## 📚 Documentation Links

| Need | Document | Time |
|------|----------|------|
| **Quick start** | [Simplified Guide](MATHEMATICAL_THEORY_SIMPLIFIED.md) | 15min |
| **Examples** | [Examples](EXAMPLES.md) | 20min |
| **Math details** | [Mathematical Theory](MATHEMATICAL_THEORY.md) | 45min |
| **Comparisons** | [Formula Comparison](FORMULA_COMPARISON.md) | 30min |
| **Everything** | [Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) | 2h |

---

## 🔧 Common Tasks

### Install AppDimens

**Android:** `implementation("io.github.bodenberg:appdimens-dynamic:2.0.0")`  
**iOS:** `pod 'AppDimens', '~> 2.0.0'`  
**Flutter:** `appdimens: ^2.0.0`  
**RN:** `npm install appdimens-react-native@2.0.0`  
**Web:** `npm install webdimens@2.0.0`

### Choose Strategy

95% of apps → **BALANCED** ⭐  
Phone-only → **DEFAULT**  
Containers → **PERCENTAGE**

### Migrate from v1.x

`.fxdp` → `.balanced().dp` (or `.defaultDp`)  
`.dydp` → `.percentageDp.dp`

---

**[⬆ Back to Top](#-appdimens-quick-reference)**
