# Platforms — the same idea everywhere

**What is this page for?** AppDimens exists for many platforms. This page shows, side by side, **which library to use**, **how to install it**, and **how each feature is written** on each platform. The names are the same everywhere — only the syntax changes (`16.sdp`, `sdp(16)`, `16sdp`, `@dimen/_16sdp`, `{sdp:16}`).

> When this page and a library's own README disagree, **the library's README wins** — it is the source of truth.

---

## 1. All libraries at a glance

Every library is **in production**.

| Library | Platform | Version | Install | Docs | AI skill |
|---------|----------|:-------:|---------|------|----------|
| [appdimens-dynamic](https://github.com/bodenberg/appdimens-dynamic) | Android — Compose, Kotlin, Java | 3.2.0 | `implementation(platform("io.github.bodenberg:appdimens-dynamic-bom:3.2.0"))` + `implementation("io.github.bodenberg:appdimens-dynamic")` | [Beginner guide](https://github.com/bodenberg/appdimens-dynamic/blob/main/GUIDE-FOR-BEGINNERS.md) · [Strategies](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/README.md) | [skills/](https://github.com/bodenberg/appdimens-dynamic/tree/main/skills) |
| [appdimens-sdps](https://github.com/bodenberg/appdimens-sdps) | Android — XML sizes (+ Compose) | 3.2.0 | `implementation("io.github.bodenberg:appdimens-sdps:3.2.0")` | [README](https://github.com/bodenberg/appdimens-sdps/blob/main/README.md) | [hub skill](../skills/SKILL.md) |
| [appdimens-ssps](https://github.com/bodenberg/appdimens-ssps) | Android — XML text (+ Compose) | 3.2.0 | `implementation("io.github.bodenberg:appdimens-ssps:3.2.0")` | [README](https://github.com/bodenberg/appdimens-ssps/blob/main/README.md) | [hub skill](../skills/SKILL.md) |
| [appdimens-kmp](https://github.com/bodenberg/appdimens-kmp) | Kotlin Multiplatform — Android, iOS, JVM desktop, macOS, Linux, Windows, JS, Wasm | 3.2.0 | `implementation(platform("io.github.bodenberg:appdimens-kmp-bom:3.2.0"))` + `implementation("io.github.bodenberg:appdimens-kmp")` | [Beginner guide](https://github.com/bodenberg/appdimens-kmp/blob/main/GUIDE-FOR-BEGINNERS.md) · [Strategies](https://github.com/bodenberg/appdimens-kmp/blob/main/DOCUMENTATION/README.md) | [skills/](https://github.com/bodenberg/appdimens-kmp/tree/main/skills) |
| [appdimens-ios](https://github.com/bodenberg/appdimens-ios) | Apple — iOS, iPadOS, macOS, tvOS, watchOS, visionOS (SwiftUI, UIKit, AppKit, Metal) | 3.2.0 | SwiftPM `https://github.com/bodenberg/appdimens-ios` from `3.2.0`, product `AppDimensDynamic` | [Beginner guide](https://github.com/bodenberg/appdimens-ios/blob/main/GUIDE-FOR-BEGINNERS.md) · [Docs](https://github.com/bodenberg/appdimens-ios/blob/main/Documentation/README.md) | [skills/appdimens-ios](https://github.com/bodenberg/appdimens-ios/tree/main/skills/appdimens-ios) |
| [appdimens-flutter](https://github.com/bodenberg/appdimens-flutter) | Flutter — Android, iOS, web, desktop | 3.2.0 | `flutter pub add appdimens_flutter` | [Beginner guide](https://github.com/bodenberg/appdimens-flutter/blob/main/GUIDE-FOR-BEGINNERS.md) · [Docs](https://github.com/bodenberg/appdimens-flutter/blob/main/DOCUMENTATION/README.md) | [skills/](https://github.com/bodenberg/appdimens-flutter/tree/main/skills) |
| [appdimens-react-native](https://github.com/bodenberg/appdimens-react-native) | React Native ≥ 0.64 — iOS & Android | 3.2.0 | `npm install appdimens-react-native` | [Docs](https://github.com/bodenberg/appdimens-react-native/blob/main/docs/README.md) | [skills/](https://github.com/bodenberg/appdimens-react-native/tree/main/skills) |
| [appdimens-web](https://github.com/bodenberg/appdimens-web) | Web — HTML/CSS, PostCSS, Sass, React, Vue, Svelte, Angular | 3.2.1 | `npm install webdimens` | [Getting started](https://github.com/bodenberg/appdimens-web/blob/main/docs/getting-started.md) · [PT-BR](https://github.com/bodenberg/appdimens-web/blob/main/docs/pt-BR/getting-started.md) | [skills/](https://github.com/bodenberg/appdimens-web/tree/main/skills) |
| [appdimens-net-binding](https://github.com/bodenberg/appdimens-net-binding) | .NET — MAUI, WPF, WinForms, WinUI 3, .NET for Android | 3.6.1 | `dotnet add package Bodenberg.AppDimens.Maui.Sdps` (and others) | [Docs](https://github.com/bodenberg/appdimens-net-binding/blob/main/docs/README.md) | [skills/](https://github.com/bodenberg/appdimens-net-binding/tree/main/skills) |
| [appdimens-games](https://github.com/bodenberg/appdimens-games) | Android games — Compose, Kotlin, Java, C/C++/NDK, OpenGL ES, Vulkan, DirectX, Unity/C# | 3.2.0 | `implementation(platform("io.github.bodenberg:appdimens-games-bom:3.2.0"))` + `implementation("io.github.bodenberg:appdimens-games")` | [Beginner guide](https://github.com/bodenberg/appdimens-games/blob/main/DOCUMENTATION/GUIDE-FOR-BEGINNERS.md) · [Docs](https://github.com/bodenberg/appdimens-games/blob/main/DOCUMENTATION/README.md) | [skills/](https://github.com/bodenberg/appdimens-games/tree/main/skills) |

**Where they are published:** Maven Central (Android, KMP, Games) · Swift Package Manager (iOS) · pub.dev (Flutter) · npm (React Native, Web) · NuGet (.NET).

### .NET packages — which one?

| I'm building… | Package |
|---------------|---------|
| .NET MAUI app (`{sdp:16}` in XAML) | `Bodenberg.AppDimens.Maui.Sdps` |
| .NET MAUI with all strategies (auto, fluid, percent…) | `AppDimens.Net.Maui` + `AppDimens.Net.Sdk` |
| WPF / Windows Forms / WinUI 3 | `Bodenberg.AppDimens.Sdps.Wpf` / `.WinForms` / `.WinUI` (or `AppDimens.Net.Wpf` / `.WinForms` / `.WinUI`) |
| .NET for Android with XML `@dimen/_16sdp` | `Bodenberg.AppDimens.Sdps` (text only: `Bodenberg.AppDimens.Ssps`) |
| .NET for Android, code only, every strategy | `Bodenberg.AppDimens.Dynamic` |

The Android bindings wrap the native Android libraries **3.2.0**.

---

## 2. The basic names on each platform

| Meaning | Compose · KMP · iOS · Flutter | Kotlin/Java code (Android) | React Native | Web | Android XML | .NET MAUI |
|---------|-------------------------------|----------------------------|--------------|-----|-------------|-----------|
| follows smallest side | `16.sdp` | `DimenSdp.sdp(context, 16)` → px | `sdp(16)` | `16sdp` | `@dimen/_16sdp` | `{sdp:16}` |
| follows width | `100.wdp` | `DimenSdp.wdp(context, 100)` | `wdp(100)` | `100wdp` | `@dimen/_100wdp` | `{wdp:100}` |
| follows height | `48.hdp` | `DimenSdp.hdp(context, 48)` | `hdp(48)` | `48hdp` | `@dimen/_48hdp` | `{hdp:48}` |
| text (respects font size) | `16.ssp` | `DimenSsp.ssp(context, 16)` | `ssp(16)` | `16ssp` | `@dimen/_16ssp` | `{ssp:16}` |
| text (ignores font size) | `16.sem` | — | `sem(16)` | `16sem` | — | `{sem:16}` |
| + screen-shape correction | `16.sdpa` | `DimenSdp.sdpa(context, 16)` | `sdpa(16)` | `16sdpa` | — | `{sdpa:16}` |
| ignore split screen | `16.sdpi` | `DimenSdp.sdpi(context, 16)` | `scaledDp(16).ignoreMultiWindows().sdp` | `16sdpi` | — | `{sdpi:16}` |
| pixels | `16.sdpPx` | (already px) | `sdpPx(16)` | `16sdpPx` | — | `16.SdpPx()` |
| rotation inverters | `32.sdpPh`, `50.hdpLw` | — | `sdpPh(32)`, `hdpLw(50)` | `32sdpPh` | — | `{sdpPh:32}` |

Notes:
- **Flutter / iOS / KMP / Compose** all use the exact same `16.sdp` syntax.
- **Web:** `16sdp` is written inside classes (`class="p:16sdp fs:14ssp"`), in CSS through PostCSS/Sass, or in JS with `sdp(16)` / `dimen('16sdp')`.
- **Games** use the Android names; game code returns pixels: `48.sdp(context)`.
- `—` means "not offered by that library" or "use the library's own API" — check its README.

---

## 3. Strategies on each platform

The **scaled** strategy is always included. The rest are optional modules — install only what you use.

| Strategy | Dp / text name | Android & Games module | KMP module | iOS product | Flutter package | React Native import | Web import |
|----------|----------------|------------------------|------------|-------------|-----------------|---------------------|------------|
| scaled | `sdp` / `ssp` | `appdimens-dynamic` (core) | `appdimens-kmp` | `AppDimens` | `appdimens_flutter` | `appdimens-react-native` | `webdimens` |
| auto ⭐ | `asdp` / `assp` | `…-auto` | `appdimens-kmp-auto` | `AppDimensAuto` | `appdimens_auto` | `…/auto` | `webdimens/auto` |
| percent | `psdp` / `pssp`, `spaceW` | `…-percent` | `appdimens-kmp-percent` | `AppDimensPercent` | `appdimens_percent` | `…/percent` | `webdimens/percent` |
| power | `pwsdp` / `pwssp` | `…-power` | `appdimens-kmp-power` | `AppDimensPower` | `appdimens_power` | `…/power` | `webdimens/power` |
| fluid | `fsdp` / `fssp` | `…-fluid` | `appdimens-kmp-fluid` | `AppDimensFluid` | `appdimens_fluid` | `…/fluid` | `webdimens/fluid` |
| interpolated | `isdp` / `issp` | `…-interpolated` | `appdimens-kmp-interpolated` | `AppDimensInterpolated` | `appdimens_interpolated` | `…/interpolated` | `webdimens/interpolated` |
| logarithmic | `logsdp` / `logssp` | `…-logarithmic` | `appdimens-kmp-logarithmic` | `AppDimensLogarithmic` | `appdimens_logarithmic` | `…/logarithmic` | `webdimens/logarithmic` |
| diagonal | `dgsdp` / `dgssp` | `…-diagonal` | `appdimens-kmp-diagonal` | `AppDimensDiagonal` | `appdimens_diagonal` | `…/diagonal` | `webdimens/diagonal` |
| perimeter | `prsdp` / `prssp` | `…-perimeter` | `appdimens-kmp-perimeter` | `AppDimensPerimeter` | `appdimens_perimeter` | `…/perimeter` | `webdimens/perimeter` |
| fit | `ftsdp` / `ftssp` | `…-fit` | `appdimens-kmp-fit` | `AppDimensFit` | `appdimens_fit` | `…/fit` | `webdimens/fit` |
| fill | `flsdp` / `flssp` | `…-fill` | `appdimens-kmp-fill` | `AppDimensFill` | `appdimens_fill` | `…/fill` | `webdimens/fill` |
| density | `dsdp` / `dssp` | `…-density` | `appdimens-kmp-density` | `AppDimensDensity` | `appdimens_density` | `…/density` | `webdimens/density` |
| resize | `autoResizeTextSp(…)` / `AutoResizeText` | `…-resize` | `appdimens-kmp-resize` | `AppDimensResize` | `appdimens_resize` | `…/resize` | `webdimens/resize` |
| units | `10.mm`, `2.cm`, `1.inch` | `…-units` | `appdimens-kmp-units` | `AppDimensUnits` | `appdimens_units` | `…/units` | `webdimens/units` |

`…-auto` means `appdimens-dynamic-auto` (Android) or `appdimens-games-auto` (Games). `…/auto` means `appdimens-react-native/auto`. On the Web (3.2.1+) the `webdimens/<strategy>` sub-paths are optional: any token you use (`p:16asdp`, `dimen('16fsdp')`) registers its strategy automatically; hand-written CSS/PostCSS lists them with `install({ strategies: [...] })`. On iOS, the `AppDimensDynamic` product already bundles the core, all strategies, resize, units and Metal. On Flutter, `appdimens_bom` installs everything at once. On .NET, `AppDimens.Net.Sdk` brings all strategies.

The Android XML libraries (`appdimens-sdps` / `appdimens-ssps`) offer the **scaled** strategy only.

---

## 4. Constants shared by every platform

Every library uses the same numbers, so results match across platforms (checked against [`DesignScaleConstants.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/main/library/src/main/java/com/appdimens/dynamic/core/DesignScaleConstants.kt)):

| Constant | Value | Meaning |
|----------|-------|---------|
| Reference width | 300 | On a screen 300 wide, `16.sdp` = 16 |
| Reference height | 533 | Height of the reference screen |
| Reference aspect ratio | 1.78 (16:9) | Shape at which the `a` correction is neutral |
| Diagonal reference | √(300² + 533²) ≈ 611.63 | For the `diagonal` strategy |
| Perimeter reference | 833 | For the `perimeter` strategy |
| `auto` turning point | 480 | `auto` is proportional up to here, then slows down |
| `auto` slow-down factor | 0.4 | Strength of the slow-down |

The `auto` rule (from [`DimenAutoDp.kt`](https://github.com/bodenberg/appdimens-dynamic/blob/main/library-auto/src/main/java/com/appdimens/dynamic/compose/auto/DimenAutoDp.kt)): up to 480 wide, `scale = width / 300`; above it, `scale = 1.6 + 0.4 × ln(1 + (width − 480) / 300)`. Result = `base × scale`.

Full formulas for every strategy: [THEORY.md](THEORY.md) and [`MATHEMATICS-AND-CALCULUS.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md).

---

## 5. Old names you may still see

Older versions used concept names. They map like this:

| Old name | Today |
|----------|-------|
| DEFAULT / FIXED | **scaled** — `sdp`, `ssp` |
| BALANCED | **auto** — `asdp`, `assp` |
| DYNAMIC / PERCENTAGE | **percent** — `psdp`, `pssp` |
| NONE | plain `dp` / `sp` / `pt` / `px` |

Upgrade steps: [MIGRATION.md](MIGRATION.md).

---

[← Documentation index](README.md) · [Guide](GUIDE.md) · [Examples](EXAMPLES.md) · [Theory](THEORY.md) · [Migration](MIGRATION.md) · [Back to the hub](../README.md)
