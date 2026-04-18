# 🎯 When to Use AppDimens - Applicability Guide

> **Languages:** English | [Português (BR)](../LANG/pt-BR/README.md) | [Español](../LANG/es/README.md) — there is no separate translated file for this topic under `LANG/`; use this document as canonical.

**Complete Guide on When and Where to Use AppDimens**  
*Author: Jean Bodenberg*  
*Date: February 2025*  
*Version: 2.0.0*

> **🆕 Version 2.0:** Updated for 13 scaling strategies with BALANCED as primary recommendation.

---

## 📋 Table of Contents

1. [When to Use AppDimens](#1-when-to-use-appdimens)
2. [When NOT to Use AppDimens](#2-when-not-to-use-appdimens)
3. [Decision Framework](#3-decision-framework)
4. [Alternative Approaches](#4-alternative-approaches)

---

## 1. When to Use AppDimens

### ✅ HIGHLY RECOMMENDED For:

#### 1.1 Multi-Device Applications

**Scenario:** App targeting phones, tablets, and TVs

**Why AppDimens?**
- ✅ BALANCED strategy provides 40% oversizing reduction on tablets
- ✅ Elements maintain visual consistency
- ✅ Single codebase for all form factors

**Recommended Strategy:** **BALANCED** ⭐ (primary)

**Example:**
```kotlin
// Same code works perfectly on phone, tablet, and TV
Text("Hello", fontSize = 16.ssp)
Button(modifier = Modifier.height(48.sdp))
```

---

#### 1.2 Apps with Variable Aspect Ratios

**Scenario:** Supporting 16:9, 18:9, 19:9, 20:9, 21:9, 4:3

**Why AppDimens?**
- ✅ DEFAULT strategy includes AR compensation
- ✅ Automatic adjustment for elongated screens

**Recommended Strategy:** **DEFAULT** (secondary)

**Example:**
```kotlin
// Automatically adjusts for different aspect ratios
Icon(modifier = Modifier.size(24.sdp))
```

---

#### 1.3 TV and Large Screen Apps

**Scenario:** Android TV, tvOS, web on large monitors

**Why AppDimens?**
- ✅ LOGARITHMIC provides maximum oversizing control
- ✅ 42-56% reduction vs linear scaling

**Recommended Strategy:** **LOGARITHMIC**

**Example:**
```swift
// iOS tvOS
Text("TV App").font(.system(size: AppDimens.shared.logarithmic(20).toPoints()))
```

---

#### 1.4 Typography-Heavy Apps

**Scenario:** News readers, blogs, documentation

**Why AppDimens?**
- ✅ FLUID strategy provides bounded growth
- ✅ Perfect for responsive typography

**Recommended Strategy:** **FLUID**

**Example:**
```dart
// Flutter
Text(
  'Article',
  style: TextStyle(fontSize: 16.0.fluidTo(24).calculate(context)),
)
```

---

#### 1.5 Game Development

**Scenario:** Mobile games with UI elements

**Why AppDimens?**
- ✅ FIT/FILL strategies for letterbox/cover
- ✅ Specialized game modules (C++/Metal)
- ✅ Vector and Rectangle scaling

**Recommended Strategy:** **FIT** or **FILL**

**Example:**
```kotlin
// Android game
val buttonSize = games.calculateButtonSize(48f)
```

---

## 2. When NOT to Use AppDimens

### ❌ NOT RECOMMENDED For:

#### 2.1 Single Form Factor Apps (Phone-Only, No Tablets)

**Scenario:** App exclusively for phones, never tablets/TVs

**Why NOT?**
- ⚠️ Traditional DP may be simpler
- ⚠️ AppDimens overhead not justified

**Alternative:** Traditional DP or WindowSizeClass

**Exception:** Use DEFAULT if app may expand to tablets later

---

#### 2.2 Fixed Layout Requirements

**Scenario:** Pixel-perfect designs, print layouts

**Why NOT?**
- ❌ AppDimens adapts dynamically
- ❌ Fixed sizes are violated

**Alternative:** Traditional DP or NONE strategy

**Exception:** Use NONE strategy for fixed elements

---

#### 2.3 Simple Web Pages (Marketing Sites)

**Scenario:** Static marketing pages, landing pages

**Why NOT?**
- ⚠️ CSS media queries may be simpler
- ⚠️ Less JavaScript overhead

**Alternative:** CSS media queries, CSS clamp()

**Exception:** Use for web apps (not pages)

---

## 3. Decision Framework

### 3.1 Decision Tree

```
START: Is your app multi-device?
│
├─ YES → Multi-device (phones, tablets, TVs)?
│  ├─ Main use: General apps
│  │  └─ Use AppDimens BALANCED ⭐
│  ├─ Main use: TV apps
│  │  └─ Use AppDimens LOGARITHMIC
│  └─ Main use: Typography
│     └─ Use AppDimens FLUID
│
├─ NO → Single device type?
│  ├─ Phone-only, may expand later?
│  │  └─ Use AppDimens DEFAULT
│  ├─ Phone-only, never expanding?
│  │  └─ Consider Traditional DP
│  └─ TV-only?
│     └─ Use AppDimens LOGARITHMIC
│
└─ Complex layout?
   ├─ Grid-based → Use AppDimens PERCENTAGE (containers)
   ├─ Game UI → Use AppDimens FIT/FILL
   └─ Fixed sizes → Use Traditional DP or NONE
```

### 3.2 Quick Selector

| Scenario | AppDimens? | Strategy |
|----------|------------|----------|
| Multi-device app | ✅ **YES** | BALANCED ⭐ |
| Phone-only (may expand) | ✅ **YES** | DEFAULT |
| Phone-only (never expand) | ⚠️ **MAYBE** | DEFAULT or Traditional DP |
| TV app | ✅ **YES** | LOGARITHMIC |
| Typography-heavy | ✅ **YES** | FLUID |
| Game development | ✅ **YES** | FIT/FILL |
| Fixed layouts | ❌ **NO** | Traditional DP |
| Simple marketing page | ❌ **NO** | CSS media queries |

---

## 4. Alternative Approaches

### 4.1 When Alternatives Make Sense

**Traditional DP:**
- ✅ Phone-only apps (uniform devices)
- ✅ When simplicity is paramount
- ❌ Multi-device apps

**SDP/SSP:**
- ✅ Legacy XML projects
- ⚠️ Linear oversizing problems
- ➡️ **Migrate to AppDimens BALANCED for 40% improvement**

**CSS Media Queries:**
- ✅ Static web pages
- ✅ Discrete breakpoints acceptable
- ❌ Dynamic web apps → Use WebDimens

**WindowSizeClass:**
- ✅ Layout structure (not dimensions)
- ✅ Combine with AppDimens for best results

---

## Conclusion

### Use AppDimens When:
1. ✅ Multi-device support needed
2. ✅ Visual consistency is important
3. ✅ Perceptual scaling valued
4. ✅ 40% better than linear scaling

### Consider Alternatives When:
1. ⚠️ Single device, simple app
2. ⚠️ Fixed layouts required
3. ⚠️ Ultra-simple projects

**📊 Bottom Line:** AppDimens benefits 90% of modern applications.

---

**Document created by:** Jean Bodenberg  
**Last updated:** February 2025  
**Version:** 2.0.0  
**Repository:** https://github.com/bodenberg/appdimens
