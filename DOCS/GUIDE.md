# APPDIMENS practical guide

How to choose strategies, read cross-platform narratives, copy common patterns, and debug sizing without wading through the full theory document. Mathematical detail lives in **[THEORY.md](THEORY.md)**; longer samples in **[EXAMPLES.md](EXAMPLES.md)**.

> **Reading order inside this guide**
>
> 1. **[Fast path](#fast-path-about-30-seconds)** — pick a strategy in 30 seconds.
> 2. **[PART I — Introduction](#part-i-introduction)** — what problem is being solved and how AppDimens solves it.
> 3. **[PART II — Strategy reference at a glance](#part-ii-strategy-reference-at-a-glance)** — the 14-mode rollup table.
> 4. **[PART III — Platform implementation](#part-iii-platform-implementation)** — what each stack looks like.
> 5. **[PART IV — Advanced features](#part-iv-advanced-features)** — Smart inference, orientation, physical units, games.
> 6. **[PART V — Comparisons & analysis](#part-v-comparisons--analysis)** — honest categorical trade-offs and benchmarks.
> 7. **[PART VI — Reference](#part-vi-reference)** — API surface, troubleshooting, FAQ, references.
>
> Companion docs: **[THEORY.md](THEORY.md)** (formal math), **[PLATFORMS.md](PLATFORMS.md)** (concept ↔ API), **[MIGRATION.md](MIGRATION.md)** (legacy ↔ modern tokens), **[ORIENTATION.md](ORIENTATION.md)** (rotation / inverters), **[EXAMPLES.md](EXAMPLES.md)** (long-form snippets).

---

## Fast path (about 30 seconds)

```
What's your UI?
├── Phones + tablets + TVs → BALANCED narrative / Android `auto` (`asdp`, `assp`, …)
├── Phone-only SKU → DEFAULT narrative / scaled tokens (`sdp`, `ssp`, …)
├── Big containers → PERCENTAGE / `percent`
├── TVs → LOGARITHMIC
├── Typography bands → FLUID
└── Fullscreen games → FIT or FILL
```

### Related tooling choices

Some teams address the same sizing concerns with other stacks:

- Single-form-factor prototypes with rigid mockups often use raw `dp/pt` plus manual tweaks.
- Web-only marketing pages handled entirely in CSS often standardize on `clamp()` and media queries without a cross-platform sizing library.

---

## PART I: INTRODUCTION

## 1. The Responsive Sizing Problem

### 1.1 The Challenge

Modern apps must work across:
- 📱 Phones (320-480dp)
- 📱 Phablets (480-600dp)
- 📱 Small Tablets (600-720dp)
- 📺 Large Tablets (720-840dp)
- 📺 TVs (960-1920dp+)
- ⌚ Watches (< 300dp)
- 💻 Web (320-3840px+)

**Problem:** A fixed 48dp button:
- Looks good on phones (13% of screen)
- Looks tiny on tablets (6.7% of screen)
- Looks microscopic on TVs (2.5% of screen)

**Linear scaling (SDP):** Opposite problem:
- Looks good on phones (48dp)
- Looks too big on tablets (96dp)
- Looks enormous on TVs (144dp)

### 1.2 The Solution Requirements

An ideal solution must:
1. ✅ **Adapt** to screen size
2. ✅ **Control** excessive growth
3. ✅ **Consider** aspect ratio
4. ✅ **Maintain** visual consistency
5. ✅ **Perform** efficiently
6. ✅ **Work** across all platforms

---

## 2. The AppDimens Solution

### 2.1 Core Innovations

**Today's catalog exposes:**

1. **14 Scaling Modes** (vs 2 in the original fixed/dynamic split — cross-checked against the `appdimens-dynamic` documentation [summary table](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/README.md#summary))
   - Perceptual: **BALANCED** (`auto`), **LOGARITHMIC**, **POWER**
   - Linear / proportional: **DEFAULT / FIXED** (`scaled`), **PERCENTAGE / DYNAMIC** (`percent`)
   - Utility: **FLUID**, **INTERPOLATED**, **DIAGONAL**, **PERIMETER**
   - Game: **FIT** (letterbox), **FILL** (cover)
   - Bucket / geometry: **DENSITY**, **RESIZE** (constraint-based auto-fit)
   - Real-world: **PHYSICAL UNITS** (mm / cm / inch)
   - Plus conceptual **NONE** for surfaces that must stay fixed

2. **Recommended primary: BALANCED (`auto`)** ⭐
   - Hybrid linear-logarithmic
   - Linear on phones (axis ≤ 480 dp)
   - Logarithmic on tablets / TVs (axis > 480 dp)
   - Damps the SDP-style overshoot on large canvases

3. **Element-type heuristics** 🧠
   - Cross-platform `smart(_).forElement(_)` builders on iOS / Flutter / RN / Web
   - Android `appdimens-dynamic` keeps **explicit** tokens at the call site (no hidden dispatch)
   - Rule-of-thumb table in [§15.2](#152-element-type-heuristics-rule-of-thumb)

4. **Lock-free padded sharded cache** ⚡
   - ~5 ns cache hit, ~2 ns raw multiply on Snapdragon 888 hardware
   - 128-byte shard padding to avoid false sharing on ARM64
   - SIMD-friendly batching via `getBatch()`
   - Fast-bypass for simple no-AR kernels — full report in **[`appdimens-dynamic/PERFORMANCE.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/PERFORMANCE.md)**

### 2.2 Quick Comparison

| Approach | Phone (360 dp) | Tablet (720 dp) | TV (1080 dp) | Verdict |
|----------|----------------|------------------|---------------|---------|
| **Raw `dp` / `sp`** | 48 dp (13%) ✅ | 48 dp (6.7%) ❌ | 48 dp (4.4%) ❌ | Density-only |
| **Linear (SDP)** | 58 dp (16%) ✅ | 115 dp (16%) ❌ | 173 dp (16%) ❌ | Over-scales |
| **AppDimens `auto` (BALANCED)** | 58 dp (16%) ✅ | ~70 dp (10%) ✅ | ~85 dp (8%) ✅ | Calibrated everywhere |

---

## 3. Strategy catalog highlights

### 3.1 What changed since the legacy fixed/dynamic split

**Expanded surface area:**
- ✅ 12 additional kernels (14 total scaling modes today, cross-checked against the [`appdimens-dynamic/DOCUMENTATION/` summary table](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/README.md#summary))
- ✅ `auto` (BALANCED) as the recommended primary for multi-form-factor apps
- ✅ Element-type heuristics published on iOS / Flutter / RN / Web tracks (Android keeps explicit tokens — see [§15](#15-element-type-inference-cross-platform-note))
- ✅ Hardware-validated sub-microsecond hot path (see [§19](#19-cache-and-performance))
- ✅ Native TypeScript / JavaScript support across `appdimens-web` and `appdimens-react-native`

**Renamed (2.x catalog clarity):**
- `Fixed` → **DEFAULT** in long-form docs (phone-focused storyline)
- `Dynamic` → **PERCENTAGE** in long-form docs (proportional / axis-heavy storyline)

**Android `appdimens-dynamic` 3.x splits the old unified Kotlin DSL into packages** ([**full migration cheatsheet**](MIGRATION.md)):

| Familiar buckets (still used in hub theory) | 3.x Gradle strategy | Typical Compose hints |
|--------------------------------------------|---------------------|-----------------------|
| **BALANCED** | **`auto`** | `asdp`, `assp`, … |
| **FIXED** + **DEFAULT** | **`scaled`** | `sdp`, `ssp`, `wdp`, `hdp`, … |
| **DYNAMIC** + **PERCENTAGE** | **`percent`** | `psdp`, … |

**Backward Compatibility:**
- ✅ Older **platform packages** may still expose deprecated entry points—confirm in the submodule you depend on
- ✅ Meta-documentation now targets **`appdimens-dynamic` 3.x** tokens (`sdp`, `asdp`, …) for new Android Compose work
- ⚠️ Treat legacy `.fxdp` / `.dydp` **blog examples** as historical; follow the linked Android README instead

### 3.2 Migration from earlier revisions

**Old code (earlier revisions)** — illustrative; **`appdimens-dynamic`** uses explicit strategy tokens (`sdp`, `asdp`, …).

**Compose — distinguish strategies:**
```kotlin
Text("Hybrid (BALANCED → auto)", fontSize = 16.assp)
Text("Phone-first scaled (DEFAULT → scaled)", fontSize = 16.ssp)
```

---

## PART II: Strategy reference at a glance

Heavy math, derivation, and benchmarking live in **[THEORY.md](THEORY.md)**. Use this matrix while pairing cross-platform wording with Gradle packages on Android Compose.

### 14-mode rollup

Aligned with the canonical [`appdimens-dynamic/DOCUMENTATION/` summary table](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/README.md#summary). Conceptual buckets on the left, Gradle / Compose surface on the right.

| Hub bucket | Gradle / Compose hint (`appdimens-dynamic`) | When it shines |
|------------|---------------------------------------------|----------------|
| **BALANCED** | `auto` — `asdp`, `ahdp`, `awdp`, `assp`, … | Multi-form-factor hybrids (linear ≤480 dp, log above) |
| **DEFAULT / FIXED** | `scaled` — `sdp`, `ssp`, `wdp`, `hdp`, `sem`, … (+ `sdpa` / `sspa` for AR) | Phone-first SDP-style baseline |
| **PERCENTAGE / DYNAMIC** | `percent` — `psdp`, `pwdp`, `phdp`, plus `space*` literals | Width / axis-heavy proportional layouts |
| **LOGARITHMIC** | `logarithmic` — `logsdp`, `logssp` | Maximum overshoot control on TVs / huge tablets |
| **POWER** | `power` — `pwsdp`, `pwssp` (configurable exponent) | Stevens-style sublinear curve |
| **FLUID** | `fluid` — `fsdp`, `fssp` (min..max band) | Typography / spacing with explicit bounds |
| **INTERPOLATED** | `interpolated` — `isdp`, `issp` | Moderate scaling (~50% linear blend) |
| **DIAGONAL** | `diagonal` — `dsdp`, `dssp` | Scale by screen diagonal (true physical feel) |
| **PERIMETER** | `perimeter` — `psdp` (perimeter pkg), `pssp` | Scale by `W + H` perimeter |
| **FIT** | `fit` — `fitsdp`, `fitssp` | Game / canvas content that must not crop (letterbox) |
| **FILL** | `fill` — `fillsdp`, `fillssp` | Game / canvas content that must fill (cover) |
| **DENSITY** | `density` — `densdp`, `denssp` | Scale only on dpi-bucket change |
| **RESIZE** | `resize` — `autoResizeTextSp`, `autoResizeSquareSize`, `ResizeBound` | Constraint-driven auto-fit (titles, squares) |
| **PHYSICAL UNITS** | `mm`, `cm`, `inch` extensions / helpers | Real-world measurements (kiosks, AR, accessibility) |
| **NONE** *(conceptual)* | raw `Dp` / `Sp` / `.dp` / `.sp` | Sizes that truly must stay fixed |

Snippet pointers for balanced vs scaled baseline:

```kotlin
Text("Hybrid (BALANCED → auto)", fontSize = 16.assp)
Text("Phone-first scaled baseline", fontSize = 16.ssp)
```

Full migration cheat sheet → **[MIGRATION.md](MIGRATION.md)** · verified constants → **[PLATFORMS.md](PLATFORMS.md)**

---

## PART III: PLATFORM IMPLEMENTATION

## 10. Android Complete Guide

### 10.1 Installation

Coordinates are maintained per artifact in **submodule READMEs**. Start from [`README.md`](../README.md#submodule-map), then open `appdimens-dynamic`, `appdimens-sdps`, `appdimens-ssps`, or `appdimens-games` as needed—do **not** pin versions from this theory document.

```kotlin
dependencies {
    // See submodule README for exact maven coordinates (dynamic, sdps, ssps, games, …).
}
```

### 10.2 Jetpack Compose API

```kotlin
// Hybrid BALANCED (strategy `auto` — `compose.auto`)
Text("Hello", fontSize = 16.assp)
Box(modifier = Modifier.size(48.asdp))

// Phone-first scaled baseline (`scaled` — hub “DEFAULT-ish”; not the hybrid curve)
Icon(modifier = Modifier.size(24.sdp))

// Width-heavy proportional patterns (`percent` helpers / `*.wdp` axis)
Surface(modifier = Modifier.width(300.wdp))

// Smart-style fusion is **not** exposed as `smart(...)` on Android — choose tokens explicitly:
Button(modifier = Modifier.height(48.asdp))

// FLUID (Typography)
Text("Title", fontSize = 16.fluidSp().fssp)

// Other strategies expose their own entry points (`pwssp`, `logssp`, …) — peek `DOCUMENTATION/`.
```

### 10.3 View System API

```kotlin
// Prefer Jetpack Compose extensions in appdimens-dynamic; for classic Views use
// XML SDP/SSP (`appdimens-sdps` / `appdimens-ssps`) or the `code` packages from the submodule.
val widthPx = (300 * resources.displayMetrics.density).toInt() // placeholder — see submodule View integration
view.layoutParams.width = widthPx
```

### 10.4 XML with SDP/SSP

```xml
<TextView
    android:textSize="@dimen/_16ssp"
    android:padding="@dimen/_12sdp"
    android:layout_width="@dimen/_300sdp" />
```

**📖 [Complete Android Guide](https://github.com/bodenberg/appdimens-dynamic/blob/main/README.md)**

---

## 11. iOS Complete Guide

### 11.1 Installation

Use CocoaPods **or** Swift Package Manager exactly as documented in **[appdimens-ios/README.md](https://github.com/bodenberg/appdimens-ios/blob/main/README.md)** and [`INSTALLATION.md`](https://github.com/bodenberg/appdimens-ios/blob/main/INSTALLATION.md)—coordinates change independently of this hub.

### 11.2 SwiftUI API

```swift
// BALANCED (Primary) ⭐
Text("Hello")
    .font(.system(size: AppDimens.shared.balanced(16).toPoints()))

// DEFAULT (Secondary)
Image(systemName: "heart")
    .frame(width: AppDimens.shared.defaultScaling(24).toPoints())

// Smart API
Button("Click") {}
    .frame(height: AppDimens.shared.smart(48).forElement(.button).toPoints())

// FLUID
Text("Title")
    .font(.system(size: AppDimens.shared.fluid(min: 16, max: 24).toPoints()))
```

### 11.3 UIKit API

```swift
let size = AppDimens.shared.balanced(48).toPoints()
button.frame = CGRect(x: 0, y: 0, width: size, height: size)
```

**📖 [Complete iOS Guide](https://github.com/bodenberg/appdimens-ios/blob/main/README.md)**

---

## 12. Flutter Complete Guide

### 12.1 Installation

See **[appdimens-flutter/pubspec.yaml](https://github.com/bodenberg/appdimens-flutter/blob/main/pubspec.yaml)** for the published constraint line—do **not** copy versions from hub theory docs.

### 12.2 API

```dart
// BALANCED-style (Primary) ⭐ — fixed builder + extensions (.fx / .dy)
Text(
  'Hello',
  style: TextStyle(fontSize: AppDimens.fixed(16).calculate(context)),
)

// Extensions
Container(
  width: AppDimens.fixed(300).calculate(context),
  height: AppDimens.fixed(200).calculate(context),
)

// Button height — pick explicit builder; “smart” helpers are not on AppDimens in this package
ElevatedButton(
  style: ElevatedButton.styleFrom(
    minimumSize: Size(
      double.infinity,
      AppDimens.fixed(48).calculate(context),
    ),
  ),
)

// FLUID — extension helper on num (see appdimens_extensions.dart)
Text(
  'Title',
  style: TextStyle(
    fontSize: 16.0.fluidTo(24).calculate(context),
  ),
)
```

**📖 [Complete Flutter Guide](https://github.com/bodenberg/appdimens-flutter/blob/main/README.md)**

---

## 13. React Native Complete Guide

### 13.1 Installation

See **[appdimens-react-native/package.json](https://github.com/bodenberg/appdimens-react-native/blob/main/package.json)** and submodule README—install there, not here.

### 13.2 API

{% raw %}
```typescript
import {useAppDimens} from 'appdimens-react-native';

function MyComponent() {
  const {balanced, defaultScaling, smart} = useAppDimens();
  
  return (
    <View style={{padding: balanced(16)}}>
      <Text style={{fontSize: balanced(18)}}>Hello</Text>
      <TouchableOpacity style={{height: smart(48).forElement('button')}}>
        <Text>Click</Text>
      </TouchableOpacity>
    </View>
  );
}
```
{% endraw %}

**📖 [Complete React Native Guide](https://github.com/bodenberg/appdimens-react-native/blob/main/README.md)**

---

## 14. Web Complete Guide

### 14.1 Installation

Follow **[appdimens-web/package.json](https://github.com/bodenberg/appdimens-web/blob/main/package.json)** and [`QUICK_START.md`](https://github.com/bodenberg/appdimens-web/blob/main/QUICK_START.md).

### 14.2 React API

{% raw %}
```typescript
import {useWebDimens} from 'webdimens/react';

function MyComponent() {
  const {balanced, fluid, smart} = useWebDimens();
  
  return (
    <div style={{padding: balanced(16)}}>
      <h2 style={{fontSize: fluid(18, 24)}}>Title</h2>
      <button style={{height: smart(48).forElement('button')}}>
        Click
      </button>
    </div>
  );
}
```
{% endraw %}

### 14.3 Vue, Svelte, Angular

See platform-specific hooks and services.

**📖 [Complete Web Guide](https://github.com/bodenberg/appdimens-web/blob/main/README.md)**

---

## PART IV: ADVANCED FEATURES

## 15. Element-type inference (cross-platform note)

### 15.1 What "smart inference" means here

**Android `appdimens-dynamic` does *not* ship a `smart(...)` fused entry point** — Compose users pick **`sdp`**, **`asdp`**, **`wdp`**, … explicitly at each call site (it keeps the source obvious and avoids hidden state). The hooks below are **conceptual heuristics** for picking the right kernel by element type. They are exposed as `smart(_).forElement(_)` builders on the iOS / Flutter / RN / Web tracks where published — see those submodule READMEs.

```kotlin
// Conceptual mapping (you pick the token by hand on Android):
val buttonSize     = 48.asdp   // BUTTON   → auto (BALANCED)
val containerWidth = 300.wdp   // CONTAINER → scaled width axis (or percent if proportional)
val iconSize       = 24.sdp    // ICON    → scaled (DEFAULT)
val bodyText       = 16.fssp   // TEXT    → fluid (with min..max)

// ⚠️ `48.sdp` never silently means "tablet BALANCED" — it invokes the `scaled` kernel.
//    Use `48.asdp` when you want the hybrid (`auto`) curve.
```

### 15.2 Element-type heuristics (rule-of-thumb)

| Element | Suggested kernel (phones) | Suggested kernel (tablets / TVs) | Rationale |
|---------|----------------------------|------------------------------------|-----------|
| **Button** | `scaled` (DEFAULT) | `auto` (BALANCED) | Consistent tap-target size; damp growth on large canvases |
| **Body / paragraph text** | `fluid` (min..max) | `fluid` | Bounded typography that respects the design system |
| **Icon** | `scaled` (DEFAULT) | `scaled` | Visual weight should stay close to the design comp |
| **Container (large card / grid)** | `percent` (PERCENTAGE) | `percent` | Proportional width tracking |
| **Spacing / padding** | `scaled` | `auto` (BALANCED) | Calibrated spacing on phones, perceptual on tablets |
| **Title / hero** | `fluid` or `auto` | `auto` | Large surfaces benefit from log damping |

### 15.3 Device categories

Useful when you write your own dispatch logic on top of the explicit tokens:

- **PHONE_SMALL** (< 300 dp)
- **PHONE_NORMAL** (300–360 dp)
- **PHONE_LARGE** (360–480 dp) ← `auto` switches from linear to log just after this band
- **TABLET_SMALL** (480–600 dp)
- **TABLET_LARGE** (600–840 dp)
- **TV** (840+ dp)
- **WATCH** (< 240 dp)
- **AUTO** (context-dependent)

**Deep dive on the underlying math:** [THEORY.md §5 — Strategy catalogue](THEORY.md#5-strategy-catalogue).

---

## 16. Base Orientation Support

### 16.1 Auto-rotation adaptation

Design for one orientation, automatically adapt when rotated:

```kotlin
// Android Compose — inverter tokens swap the driving axis based on current orientation
val pad   = 32.sdpPh  // SW-based; in PORTRAIT switches to HEIGHT
val width = 32.sdpLw  // SW-based; in LANDSCAPE switches to WIDTH
val tall  = 50.hdpLw  // HEIGHT-based; in LANDSCAPE switches to WIDTH
val wide  = 50.wdpLh  // WIDTH-based; in LANDSCAPE switches to HEIGHT
```

```swift
// iOS / Flutter / RN / Web stacks expose builder-style helpers
//   `.portraitLowest()`, `.landscapeHighest()` etc. — confirm the names in the submodule README.
let width = AppDimens.shared.balanced(300).portraitLowest().toPoints()
```

```dart
final width = AppDimens.fixed(300).portraitLowest().calculate(context);
```

**Rotation semantics, base-orientation matrix, and the full inverter catalogue:** **[ORIENTATION.md](ORIENTATION.md)** · Compose surface in [`COMPOSE-API-CONVENTIONS.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md).

---

## 17. Physical Units

### 17.1 Real-World Measurements

```kotlin
// Android
val buttonWidth = 10.mm   // 10 millimeters
val cardWidth = 8.cm      // 8 centimeters
val screenSize = 5.inch   // 5 inches

// iOS
let width = AppDimensPhysicalUnits.mm(10)

// Flutter
final width = AppDimensPhysicalUnits.mmToPixels(10, context);
```

**Available:** All platforms

**Physical sizing:** Confirm millimetre helpers in **`appdimens-dynamic`** submodule docs—this hub avoids duplicating them.

---

## 18. Game Development Modules

### 18.1 Android (C++/NDK)

```kotlin
val games = AppDimensGames.getInstance()
games.initialize(context)

val buttonSize = games.calculateButtonSize(48f)
val playerSize = games.calculatePlayerSize(64f)
```

**Features:** C++/NDK, OpenGL ES, Vector2D, Physical units

**📖 [Android Games Guide](https://github.com/bodenberg/appdimens-games/blob/main/appdimens_games/README.md)**

### 18.2 iOS (Metal)

```swift
let buttonSize = gameUniform(48)
let playerSize = gameAspectRatio(64)
```

**Features:** Metal/MetalKit, SIMD, 5 viewport modes

**📖 [iOS Games Guide](https://github.com/bodenberg/appdimens-ios/blob/main/README.md#game-development-features)**

---

## 19. Cache and Performance

### 19.1 Architecture summary

`appdimens-dynamic` runs every kernel through a **lock-free padded sharded cache** with an intelligent **fast-bypass layer**:

- **Padded sharding** — each shard is isolated with 128-byte padding to eliminate **false sharing** between CPU cores on ARM64.
- **SIMD-friendly batching** — the `getBatch()` API exposes continuous loops the JIT/ART can vectorize.
- **Volatile isolation** — scale factors are grouped in a padded `ScreenFactors` object to prevent cache-line invalidations on configuration changes.
- **Fast bypass** — for simple no-AR kernels (`SCALED`, `AUTO`, `PERCENT`, `FLUID`), the cache is intentionally skipped because a 2 ns multiply is **2.5× faster** than the fastest 5 ns cache lookup.

### 19.2 Hot-path costs (Snapdragon 888 hardware)

| Operation | Cost | Notes |
|-----------|------|-------|
| Raw math, no AR | **~2 ns** | Fast-bypass path |
| Raw math with AR | ~45 ns | `ln()` evaluation on hardware |
| Cache hit, no AR | **~5 ns** | Lock-free padded shard |
| Cache hit, with AR | **~35 ns** | Zero-math AR path |
| Batch resolution (100 items) | ~169 ns | `getBatch()` API |
| JVM cache hit (Ubuntu, JVM 17) | **~1 ns** | Local development baseline |

Full methodology, R8 deltas, and per-device variability: **[`appdimens-dynamic/PERFORMANCE.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/PERFORMANCE.md)**.

### 19.3 Cache control

```kotlin
// Compose: cache lifecycle is driven by Configuration changes and managed by `DimenCache`.
// For Activities that survive rotation / split-screen / density changes without recreation:
override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    DimenCache.invalidateOnConfigChange(newConfig)
}
```

Per-instance cache toggles (`.cache(true/false)`) and warm-up helpers vary per submodule — confirm in the README you ship.

**Performance internals:** [`appdimens-dynamic/PERFORMANCE.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/PERFORMANCE.md) · [THEORY.md §9 — Engineering invariants](THEORY.md#9-engineering-invariants-no-curves-here)

---

## PART V: COMPARISONS & ANALYSIS

## 20. Comparison with External Libraries

### 20.1 Honest categorical trade-offs

The hub does **not** publish a single global "score / 100" ranking — comparisons depend heavily on form-factor mix, design philosophy, and what you measure. The categorical trade-offs below are reproducible from the kernels shipped in [`appdimens-dynamic/DOCUMENTATION/`](https://github.com/bodenberg/appdimens-dynamic/tree/main/DOCUMENTATION) and the formulas in [THEORY.md §8–10](THEORY.md#8-numerical-comparison-across-a-sweep-of-sw).

| Approach | What it does well | What AppDimens adds |
|----------|--------------------|---------------------|
| **Raw `dp` / `sp` / `pt` / `px`** | Zero deps, predictable on the calibrated device | Calibration across canvas extent and aspect ratio, not just density |
| **Linear SDP / SSP (third-party)** | Pre-computed `@dimen` resources, XML-native | Same baked resources (`appdimens-sdps`, `appdimens-ssps`) **plus** non-linear kernels (`auto`, `logarithmic`, `power`, `fluid`) — and you can mix runtime computation in Compose |
| **CSS `vw` / `vh` / `clamp()` / container queries** | First-class in modern browsers, no JS | A single vocabulary that travels off the Web (Android / iOS / KMP / Flutter / RN) — `appdimens-web` implements `clamp()`-style behaviour as the `fluid` kernel |
| **`ScreenUtil` / `size-matters` / similar libraries** | Single dependency, friendly API | Strategy-per-call-site instead of a single global curve; foldable + multi-window heuristics; AR compensation; reproducible math doc per kernel |

### 20.2 AppDimens vs raw SDP/SSP (illustrative)

For a **48 dp button** on a 720 dp wide canvas (reproducible from `DimenAutoDp` / `DimenSdp` — see [`auto.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/auto.md) and [`scaled.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/scaled.md)):

| Aspect | AppDimens `auto` (BALANCED) | Linear SDP/SSP |
|--------|------------------------------|----------------|
| **Formula** | Hybrid linear-then-logarithmic (hinge at 480 dp) | Linear |
| **Tablet (720 dp) result** | ~70 dp (10% of width) | ~115 dp (16% of width) |
| **TV (1080 dp) result** | ~85 dp (8% of width) | ~173 dp (16% of width) |
| **Aspect-ratio compensation** | Opt-in via `a` suffix | Not modeled |
| **Performance** | ~5 ns cache hit / ~2 ns no-AR multiply ([`PERFORMANCE.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/PERFORMANCE.md)) | ~0 ns (pre-computed) |
| **Flexibility** | 14 scaling modes per call-site | 1 curve per artifact |
| **XML resources** | Via `appdimens-sdps` / `appdimens-ssps` (same baked tables) | XML resource tables |

**Reading guidance:** if you ship XML view systems and a phone-first SDP curve covers your needs, keep using `appdimens-sdps` / `appdimens-ssps` resources directly. If you need restrained growth on tablets / TVs / foldables, prefer the `auto` (BALANCED) kernel in `appdimens-dynamic` or its cross-platform peers. The two tracks are designed to coexist in the same app.

---

## 21. Performance Benchmarks

### 21.1 Hot-path costs (recap)

Hardware-validated numbers on Snapdragon 888 / Android 14 — see `§19.2` above and **[`appdimens-dynamic/PERFORMANCE.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/PERFORMANCE.md)** for the full report.

| Operation | Result | Status |
|-----------|--------|--------|
| Raw math, no AR | **~2 ns** | Fast-bypass path |
| Cache hit, no AR | **~5 ns** | Lock-free padded shard |
| Cache hit, with AR | **~35 ns** | Zero-math AR path |
| Batch resolution (100 items) | ~169 ns | SIMD-friendly loop |
| Real-world Compose scroll (1 000 items) | ~996 ms total / ~996 µs per item | 0% jank @ 120 FPS |

With **R8 + minify** on release builds the dashboard-style harness drops further (~125–155 ns micro combined, ~367–380 ns per-item macro). See the **Build variants & R8** note at the top of `PERFORMANCE.md`.

### 21.2 Memory & footprint

- Padded shard layout: 128-byte guards to avoid false sharing (~2.5 KB extra memory).
- The library performs Configuration-driven caching only where it amortizes the lookup cost — the simplest no-AR kernels bypass storage entirely.
- See the architecture diagram at the bottom of [`PERFORMANCE.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/PERFORMANCE.md).

**Engineering invariants:** [THEORY.md §9](THEORY.md#9-engineering-invariants-no-curves-here)

---

## 22. Migration Guides

### 22.1 From AppDimens earlier revisions

**Step 1:** Pick the submodule for your stack (Compose → `appdimens-dynamic` 3.x).  
**Step 2:** Replace legacy unified Android chains (`.fxdp`, `.dydp`, `.balanced().dp`) so that **SDP-style FIXED/DEFAULT flows** land on **`sdp` / `wdp` / `hdp` / `ssp`**, **hybrid BALANCED** lands on **`asdp` / `ahdp` / `awdp` / `assp`**, and **DYNAMIC/PERCENTAGE** flows land on **`psdp`** / **`compose.percent`**. Cheat sheet **[`MIGRATION.md`](MIGRATION.md)**.  
**Step 3:** Keep **iOS / Web / RN** builder names (`balanced`, `defaultScaling`, …) as documented in those repos.

**Backward compatible:** Older **platform-specific** code may still compile inside older artifacts; the meta-docs no longer teach the retired Kotlin extension chain for `appdimens-dynamic` 3.x.

### 22.2 From third-party SDP / SSP libraries

**Step 1:** Add the AppDimens artifact for your stack — for Android XML, swap your existing third-party SDP/SSP library for [`appdimens-sdps`](https://github.com/bodenberg/appdimens-sdps/blob/main/README.md) / [`appdimens-ssps`](https://github.com/bodenberg/appdimens-ssps/blob/main/README.md). Resource names (`@dimen/_16sdp`, `@dimen/_18ssp`, …) match.
**Step 2:** For Compose / runtime code, replace `@dimen/_16sdp` → `16.sdp` (the `scaled` kernel, same baseline). For the hybrid BALANCED curve switch to `16.asdp` (`auto`).
**Step 3:** Remove the previous SDP dependency.

**Benefits:** access to the full 14-mode kernel set without throwing away the SDP-style baseline; you can adopt non-linear kernels (`auto`, `logarithmic`, `power`, `fluid`) gradually on a per-callsite basis.

### 22.3 From Other Libraries

- **CSS vw/vh** → WebDimens `balanced()` / `fluid()` (see `appdimens-web`)
- **ScreenUtil** → Prefer explicit **scaled** tokens on Android (`sdp` / `wdp` / `hdp`)
- **size-matters** → Map layouts to **strategy packages** in `appdimens-dynamic`, not a single extension

**External toolkit comparisons:** [THEORY.md §10 — Comparative discussion and limits](THEORY.md#10-comparative-discussion-and-limits)

---

## PART VI: REFERENCE

## 23. Complete API Reference

### 23.1 Strategy Selection Methods

**Android (Jetpack Compose — `appdimens-dynamic` 3.x):**
```kotlin
// Strategy = which package you import (scaled, auto, percent, logarithmic, power, fluid, …)
16.sdp           // scaled — smallest-width axis
16.asdp          // auto — hybrid “BALANCED-like” on smallest-width axis
300.wdp          // scaled — width axis (containers)
16.logarithmicSp().logssp
16.pwssp
14.fluidSp().fssp
```

**iOS:**
```swift
.balanced(_)
.defaultScaling(_)
.percentage(_)
.logarithmic(_)
.power(_, exponent:)
.fluid(min:max:)
.smart(_)
```

**Flutter (`appdimens` package):**
```dart
AppDimens.fixed(_)        // primary builder
AppDimens.dynamic(_)      // aggressive / width-like growth
_.fx / _.dy               // num extensions → builders
_.fluidTo(max)            // fluid helper (see appdimens_extensions.dart)
AppDimens.dynamicPercentageDp(percent, context)
```

### 23.2 Configuration Methods

Qualifier chains, orientation, cache, and AR flags are exposed on **builders inside each submodule** (e.g. `DimenLogarithmic`, `AppDimensFixed`, `WebDimensBuilder`). See [Platform API map](PLATFORMS.md) and the linked READMEs instead of a single shared Kotlin surface.

### 23.3 Output Methods

**Android:**
```kotlin
.dp          // Dp (Compose)
.sp          // TextUnit (Compose)
.toDp()      // Float (Views)
.toSp()      // Float (Views)
.toPx()      // Float (physical pixels)
```

**iOS:**
```swift
.toPoints()  // CGFloat
.toPt()      // CGFloat (shorthand)
.toPixels()  // CGFloat (physical)
```

**Flutter:**
```dart
.calculate(context)  // double
```

---

## 24. Troubleshooting

### 24.1 Common Issues

**Q: Sizes look wrong on my device**

A: Check:
1. Using correct strategy (BALANCED for multi-device)
2. Base value is appropriate (48dp for buttons, not 16dp)
3. Not mixing strategies randomly

**Q: Performance seems slow**

A: Solutions:
1. Ensure cache is enabled (default)
2. Call `warmupCache()` during init
3. Use appropriate strategy (PERCENTAGE is fastest)

**Q: Migration from earlier revisions - sizes changed**

A: On **Android Compose**, move to **`sdp` / `wdp` / `hdp` / `ssp`** plus **`asdp` / …** for the hybrid curve ([appdimens-dynamic README](https://github.com/bodenberg/appdimens-dynamic/blob/main/README.md)). Legacy extension names are **not** what this meta-repo documents for 3.x.

### 24.2 FAQ

**Q: Which strategy should I use?**

A: 
- Multi-device app → **BALANCED** ⭐
- Phone-only → **DEFAULT**
- Large containers → **PERCENTAGE**
- Typography → **FLUID**

**Q: Do newer Android scaling tokens replace every legacy extension?**

A: **Per artifact.** Deprecated chains may still compile on older pins, but modern work should follow the **`appdimens-dynamic` README** (token + explicit strategy packages). Confirm with the submodule changelog you ship.

A: Negligible (< 0.002µs per calculation with cache)

---

## 25. FAQ

### Strategy Selection

**Q: Why is BALANCED now primary instead of DEFAULT?**

A: BALANCED provides 40% better oversizing control on tablets while maintaining familiar linear behavior on phones. DEFAULT (formerly Fixed) is now optimized for phone-focused apps.

**Q: Can I still use the old Fixed/Dynamic?**

A: If your **published dependency** still exposes those symbols, consult that version’s notes. New **Compose** work should follow **`sdp` / `wdp` / `hdp`** and strategy imports from **`appdimens-dynamic`** instead of `.fxdp` / `.dydp` examples in old blog posts.

**Q: When should I use PERCENTAGE instead of BALANCED?**

A: Only for very large containers, images, and grids where proportional scaling is truly needed. For 95% of UI elements, use BALANCED.

### Performance

**Q: Is there overhead vs traditional DP?**

A: Minimal (< 0.002µs with cache). Negligible in practice.

**Q: Should I disable cache?**

A: No, keep it enabled (default). Only disable for debugging.

### Platform-Specific

**Q: Does AppDimens work with Jetpack Compose?**

A: Yes! Native Compose support with extension functions.

**Q: Can I use AppDimens in XML layouts?**

A: Yes! Use SDP/SSP modules for XML, or runtime calculation in code.

---

## 26. References

### Documentation

- [Mathematical Theory](THEORY.md) — formal kernel taxonomy, formulas, invariants, comparisons
- [Cross-platform map](PLATFORMS.md) — concept ↔ submodule API across all stacks
- [Migration](MIGRATION.md) — 1.x / 2.x / SDP ↔ Compose 3.x cheat sheet
- [Orientation](ORIENTATION.md) — base orientation and axis inverters
- [Examples](EXAMPLES.md) — long-form code samples per stack

### Platform Guides

- [Android](https://github.com/bodenberg/appdimens-dynamic/blob/main/README.md)
- [iOS](https://github.com/bodenberg/appdimens-ios/blob/main/README.md)
- [Flutter](https://github.com/bodenberg/appdimens-flutter/blob/main/README.md)
- [React Native](https://github.com/bodenberg/appdimens-react-native/blob/main/README.md)
- [Web](https://github.com/bodenberg/appdimens-web/blob/main/README.md)

### Academic References

- Weber, E. H. (1834). "De pulsu, resorptione, auditu et tactu"
- Fechner, G. T. (1860). "Elemente der Psychophysik"
- Stevens, S. S. (1957). "On the psychophysical law"
- Loomis et al. (1992). "Visual space perception"

---

**Document created by:** Jean Bodenberg  
**License:** Apache 2.0  
**Repository hub:** https://github.com/bodenberg/appdimens (theory-only in `DOCS/`; implementations in submodules)

---

**[⬆ Back to Top](#-appdimens-complete-technical-guide)**
