# ⚡ AppDimens: Documentation Quick Reference

**Quick Navigation Guide - Find What You Need in Seconds**

> **Languages:** English | [Português (BR)](../LANG/pt-BR/DOCS_QUICK_REFERENCE.md) | [Español](../LANG/es/DOCS_QUICK_REFERENCE.md)

---

## 🎯 Choose Your Path

### I want to...

**... learn AppDimens from scratch** 🌱
→ Read: [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) (15min)

**... see code examples** 💻
→ Read: [EXAMPLES.md](EXAMPLES.md) (20min)

**... understand the complete mathematical theory** 🧮
→ Read: [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md) (45min)

**... compare AppDimens with other solutions** ⚖️
→ Read: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) (30min)

**... see EVERYTHING in one document** 📚
→ Read: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md) (2h)

**... validate if the implementation is correct** ✅
→ Read: [VALIDATION_REPORT.md](VALIDATION_REPORT.md) (20min)

**... navigate all documentation** 🗺️
→ Read: [DOCS/README.md](README.md) (5min to browse)

---

## 📊 Documents by Objective

### To Decide if You Use AppDimens

1. [README.md](../README.md) - Overview
2. [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) - Simple theory
3. [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) - See #1 ranking with 91/100

**Decision in: 50 minutes**

---

### To Implement in Your Project

1. [EXAMPLES.md](EXAMPLES.md) - Ready-to-use code
2. [Android/README.md](../Android/README.md) - Platform-specific
3. [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) - FAQ

**Ready to code in: 30 minutes**

---

### For Research/Academic Paper

1. [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md) - Formal theory
2. [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) - Scientific comparisons
3. [VALIDATION_REPORT.md](VALIDATION_REPORT.md) - Validation
4. [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md) - Everything

**Complete material for publication**

---

## 🏆 Main Documented Achievements

### Rankings and Certifications

- **#1 out of 7 formulas** with **91/100 points** 🥇
- **3.5× more accurate** than linear
- **65% less oversizing** than competitors
- **Only one with AR compensation**
- **Only one with psychophysical foundation**

📍 See at: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md#63-certificate-of-excellence)

---

### Technical Innovations

1. ✅ First library with logarithmic scaling
2. ✅ First with automatic aspect ratio compensation
3. ✅ First with 4-priority hierarchical system
4. ✅ First with Weber-Fechner Law foundation
5. ✅ Superior performance with cache (0.002µs vs 0.005µs)
6. ✅ **NEW v1.2.0**: Base Orientation with auto-inversion of LOWEST/HIGHEST on rotation
7. ✅ **NEW v1.0.10**: Fluid model for smooth bounded growth (Compose/SwiftUI/Flutter/RN/Web)
8. ✅ Global cache control system with per-instance overrides
9. ✅ Physical units support across all platforms (mm, cm, inch)
10. ✅ Game development modules: Android (C++/NDK), iOS (Metal/SIMD)

📍 See at: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md#12-innovation-and-originality)

---

## 📐 Main Formulas

### Scaling Models Overview

| Model | Formula Type | When to Use | Platforms |
|-------|--------------|-------------|-----------|
| **Fixed (FX)** ⭐ | Logarithmic | 95% of cases - buttons, text, padding, icons | All |
| **Dynamic (DY)** | Proportional | Large containers, grids | All |
| **Fluid (FL)** 🌊 | Linear interpolation | Typography with bounds | Compose, SwiftUI, Flutter, RN, Web |
| **Physical Units** 📏 | Real-world measurements | Precision layouts | All |

### Logarithmic Formula (Fixed)

```
f_FX(B, S, AR) = B × [1 + ((S - 300) / 1) × (0.10/30 + 0.08/30 × ln(AR / 1.78))]
                = B × [1 + ((S - 300) / 1) × (0.00333 + 0.00267 × ln(AR / 1.78))]
```

### Priority System

```
1. Intersection (UiMode + DpQualifier) ← Most specific
2. UiMode (device type only)
3. DpQualifier (size only)
4. Logarithmic (automatic scaling) ← Fallback
```

📍 Details at: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md#4-priority-system-hierarchy-system)

---

## 🔬 Important Comparisons

### vs. SDP/SSP (Linear)

| Aspect | SDP/SSP | AppDimens |
|---------|---------|-----------|
| Formula | Linear | Logarithmic |
| AR | ❌ Ignores | ✅ Compensates |
| Tablet 800dp | 107dp (🔴 +123%) | 68dp (✅ +42%) |

### vs. CSS clamp()

| Aspect | CSS clamp() | AppDimens |
|---------|-------------|-----------|
| Scaling | Linear | Logarithmic |
| AR | ❌ Ignores | ✅ Compensates |
| Platform | Web Only | Universal |

### vs. Flutter ScreenUtil

| Aspect | ScreenUtil | AppDimens |
|---------|-----------|-----------|
| Formula | Quadratic | Logarithmic |
| Theoretical basis | ❌ None | ✅ Weber-Fechner |
| Tablet 800dp | 89dp (🟡 +86%) | 68dp (✅ +42%) |

📍 Complete comparison: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md#7-comparison-with-7-fundamental-formulas)

---

## 🎓 Scientific Foundation

### Weber-Fechner Law (1860)

```
S = k × ln(I / I₀)
```

**Application:** Human perception of size is logarithmic, not linear.

### Stevens' Power Law (1957)

```
ψ = k × φⁿ  (where n < 1 for spatial perception)
```

**Application:** Sublinear growth is more natural for UI.

📍 Details at: [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md#6-scientific-foundation)

---

## 💡 Quick FAQ

**Q: Why logarithm?**  
A: Because human perception is logarithmic (Weber-Fechner Law). Prevents natural oversizing.

**Q: Is it slow?**  
A: With cache, it's the FASTEST (0.002µs vs 0.005µs for percentage).

**Q: Does it work on iOS/Flutter/Web?**  
A: Yes! Universal, same formula on all platforms.

**Q: When should I use Fluid instead of Fixed?**  
A: Use Fluid for typography and spacing where you want explicit min/max bounds with smooth transitions. Use Fixed (RECOMMENDED) for general UI elements like buttons, icons, and most containers.

**Q: What's the difference between Fixed and Fluid?**  
A: Fixed uses logarithmic growth (slows on large screens), Fluid uses linear interpolation between explicit min/max values. Fixed adapts automatically, Fluid requires you to define bounds.

**Q: How does Base Orientation work?**  
A: Design for one orientation (portrait/landscape), and AppDimens automatically inverts LOWEST↔HIGHEST when the device rotates. No need to handle rotation manually!

**Q: Is it difficult to use?**  
A: No! Simple API: `16.fxdp` or `16.fixedDp().dp`

📍 Complete FAQ: [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md#-frequently-asked-questions-faq)

---

## 📱 Quick Examples

### Android Compose

```kotlin
Text(
    text = "Hello",
    fontSize = 16.fxsp,
    modifier = Modifier.padding(12.fxdp)
)
```

### iOS SwiftUI

```swift
Text("Hello")
    .font(.system(size: AppDimens.fixed(16).sp))
    .padding(AppDimens.fixed(12).dp)
```

### Flutter

```dart
Text(
  'Hello',
  style: TextStyle(fontSize: 16.fxsp.calculate(context)),
)
```

### React Native

{% raw %}
```javascript
<Text style={{ fontSize: fixedSp(16) }}>
  Hello
</Text>
```
{% endraw %}

📍 More examples: [EXAMPLES.md](EXAMPLES.md)

---

## 🗺️ Documentation Map

```
AppDimens/
│
├─ README.md ← START HERE
│
├─ Simplified Documentation (Beginner)
│  ├─ MATHEMATICAL_THEORY_SIMPLIFIED.md ⭐
│  ├─ EXAMPLES.md
│  └─ Platform READMEs
│
├─ Technical Documentation (Intermediate)
│  ├─ MATHEMATICAL_THEORY.md
│  ├─ FORMULA_COMPARISON.md ⭐
│  └─ VALIDATION_REPORT.md
│
├─ Advanced Documentation (Experts)
│  └─ COMPREHENSIVE_TECHNICAL_GUIDE.md ⭐⭐⭐
│
└─ Navigation
   ├─ DOCS/README.md (complete index)
   └─ DOCS/DOCS_QUICK_REFERENCE.md (you are here!)
```

---

## ⚡ Direct Links

### Most Accessed

- [Simplified Guide](MATHEMATICAL_THEORY_SIMPLIFIED.md) ← 80% of users start here
- [Code Examples](EXAMPLES.md)
- [Comparison with Other Libraries](FORMULA_COMPARISON.md)

### Complete Documentation

- [Formal Mathematical Theory](MATHEMATICAL_THEORY.md)
- [Complete Technical Guide](COMPREHENSIVE_TECHNICAL_GUIDE.md) ← Everything in one place
- [Full Documentation Index](README.md)

### Validation and Certifications

- [Validation Report](VALIDATION_REPORT.md)
- [Certificate of Excellence](FORMULA_COMPARISON.md#63-certificate-of-excellence)

---

## 📞 Need Help?

1. **Documentation** ← You are here!
2. [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
3. [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)
4. [Official Website](https://appdimens-project.web.app/)

---

**Last updated:** October 2025  
**Version:** 1.1.0  
**Author:** Jean Bodenberg  
**License:** Apache 2.0

---

*"The right information, at the right time, in the right way."*
