---
name: appdimens
description: Use this skill for any responsive sizing question in a project that uses (or should use) AppDimens on any platform — Android (Jetpack Compose, Kotlin, Java, XML @dimen), Kotlin Multiplatform, iOS/macOS (SwiftUI, UIKit), Flutter, React Native, Web (webdimens), .NET (MAUI, WPF, WinForms, WinUI, .NET for Android) or Android games. Trigger when the user mentions AppDimens, appdimens-*, webdimens, sdp, hdp, wdp, ssp, sem, asdp, "16sdp", @dimen/_16sdp, {sdp:16}, making UI look right on tablets/foldables/TVs, or choosing a scaling strategy. This hub skill picks the right library, install line and token names, then defers to that library's own skill.
---

# AppDimens — hub skill (routes to the right library)

AppDimens is one family of responsive-size libraries with **the same names and the same math on every platform**. All libraries are **in production**: version **3.2.0** (the .NET packages are **3.6.1**).

## Step 1 — pick the library

| Project uses… | Library | Install | Library skill |
|---------------|---------|---------|---------------|
| Android Compose / Kotlin / Java | `appdimens-dynamic` | `implementation(platform("io.github.bodenberg:appdimens-dynamic-bom:3.2.0"))` + `implementation("io.github.bodenberg:appdimens-dynamic")` (+ `appdimens-dynamic-<strategy>`) | [appdimens-dynamic/skills/SKILL.md](https://github.com/bodenberg/appdimens-dynamic/blob/main/skills/SKILL.md) |
| Android XML layouts | `appdimens-sdps` | `implementation("io.github.bodenberg:appdimens-sdps:3.2.0")` → `@dimen/_16sdp`, `_48hdp`, `_100wdp` | this skill |
| Android XML text | `appdimens-ssps` | `implementation("io.github.bodenberg:appdimens-ssps:3.2.0")` → `@dimen/_14ssp`, `_14hsp`, `_14wsp` | this skill |
| Kotlin Multiplatform | `appdimens-kmp` | `implementation(platform("io.github.bodenberg:appdimens-kmp-bom:3.2.0"))` + `implementation("io.github.bodenberg:appdimens-kmp")` (+ `appdimens-kmp-<strategy>`) | [appdimens-kmp/skills/SKILL.md](https://github.com/bodenberg/appdimens-kmp/blob/main/skills/SKILL.md) |
| iOS / macOS / tvOS / watchOS / visionOS | `appdimens-ios` | SwiftPM `.package(url: "https://github.com/bodenberg/appdimens-ios", from: "3.2.0")`, product `AppDimensDynamic` | [appdimens-ios/skills/appdimens-ios/SKILL.md](https://github.com/bodenberg/appdimens-ios/blob/main/skills/appdimens-ios/SKILL.md) |
| Flutter | `appdimens-flutter` | `flutter pub add appdimens_flutter` (+ `appdimens_<strategy>` or `appdimens_bom`) | [appdimens-flutter/skills/SKILL.md](https://github.com/bodenberg/appdimens-flutter/blob/main/skills/SKILL.md) |
| React Native | `appdimens-react-native` | `npm install appdimens-react-native` (strategies: `appdimens-react-native/<strategy>`) | [appdimens-react-native/skills/SKILL.md](https://github.com/bodenberg/appdimens-react-native/blob/main/skills/SKILL.md) |
| Web | `appdimens-web` (npm `webdimens`) | `npm install webdimens` then `install()`; strategies: `import 'webdimens/<strategy>'` | [appdimens-web/skills/SKILL.md](https://github.com/bodenberg/appdimens-web/blob/main/skills/SKILL.md) |
| .NET MAUI / WPF / WinForms / WinUI / .NET for Android | `appdimens-net-binding` | `dotnet add package Bodenberg.AppDimens.Maui.Sdps` (MAUI) · `Bodenberg.AppDimens.Sdps.Wpf` / `.WinForms` / `.WinUI` · `AppDimens.Net.Sdk` (all strategies) · Android: `Bodenberg.AppDimens.Sdps` / `.Ssps` / `.Dynamic` | [appdimens-net-binding/skill/SKILL.md](https://github.com/bodenberg/appdimens-net-binding/blob/main/skill/SKILL.md) |
| Android games (Kotlin, C/C++/NDK, GL/Vulkan, Unity) | `appdimens-games` | `implementation(platform("io.github.bodenberg:appdimens-games-bom:3.2.0"))` + `implementation("io.github.bodenberg:appdimens-games")` (+ `appdimens-games-native`) | [appdimens-games/skills/SKILL.md](https://github.com/bodenberg/appdimens-games/blob/main/skills/SKILL.md) |

If the library's own skill is available (in this hub it lives in the submodule folder, e.g. `appdimens-flutter/skills/SKILL.md`), **read it and follow it** — it is more detailed than this file. The library README is the final word on APIs.

## Step 2 — one-time setup

| Platform | Setup |
|----------|-------|
| Android Compose / KMP | none for `sdp`/`ssp`; wrap in `AppDimensProvider { }` only for UI-mode/fold facilitators (`sdpMode`, `sdpScreen`) |
| iOS SwiftUI | `ContentView().appDimens()` once per window |
| iOS UIKit / AppKit | `AppDimens.startAutomaticUpdates()` |
| Flutter | `AppDimensApp(child: MaterialApp(...))` |
| Web | `import { install } from 'webdimens'; install();` (the CDN script installs itself) |
| .NET MAUI | `builder.UseAppDimensSdps();` in `MauiProgram.cs` |
| Games | `GameScreen.updateFromContext(context)` on resize |

## Step 3 — write the tokens

Syntax per platform:

| Compose · KMP · iOS · Flutter | Kotlin/Java code, Games | React Native | Web | Android XML | .NET MAUI XAML |
|-------------------------------|-------------------------|--------------|-----|-------------|----------------|
| `16.sdp`, `16.ssp` | `16.sdp(context)` / `DimenSdp.sdp(context, 16)` → **px** | `sdp(16)`, `ssp(16)` | `class="p:16sdp fs:16ssp"`, JS `sdp(16)` | `@dimen/_16sdp`, `@dimen/_16ssp` | `{sdp:16}`, `{ssp:16}` |

Grammar: `<prefix><axis><kind><inverter><flags>[Px]`
- axis: `s` smallest side (default choice), `h` height, `w` width
- kind: `dp` layout · `sp` text that respects user font scale · `em` text that ignores it
- inverters: `Ph` `Lh` `Pw` `Lw` (portrait/landscape → height/width)
- flags: `a` aspect-ratio correction · `i` ignore multi-window · `ia` both
- `Px`: return pixels

Strategy prefixes (exact — do not invent others):

| Strategy | Token | When |
|----------|-------|------|
| scaled (default, always installed) | `sdp` / `ssp` | phone-first apps, most UIs |
| auto ⭐ | `asdp` / `assp` | phone + tablet + TV in one app (linear to 480, then log) |
| percent | `psdp` / `pssp`, `spaceW(10)` | literal % of the screen |
| power | `pwsdp` | softer sub-linear growth |
| fluid | `fsdp` / `fssp` | bounded 0.8×–1.2× between 320–768 (typography) |
| interpolated | `isdp` | halfway fixed/linear |
| logarithmic | `logsdp` | huge screens / TVs |
| diagonal | `dgsdp` | screen diagonal |
| perimeter | `prsdp` | width + height |
| fit | `ftsdp` | letterbox (never crop) |
| fill | `flsdp` | cover (always fill) |
| density | `dsdp` | pixel density |
| resize | `autoResizeTextSp(…)`, `AutoResizeText` | largest size that fits a box |
| units | `10.mm`, `2.cm`, `1.inch` | physical sizes |

Obsolete names — never generate them: `balanced(…)`, `.balanced().dp`, `webdimens.balanced`, `defaultScaling`, `.fxdp`, `.dydp`, `fx`, `dy`, `AppDimens.fixed(…)`, `AppDimens.shared.…toPoints()`, `densdp`, `fillsdp`, `fitsdp`. Map: BALANCED → `auto` (`asdp`), DEFAULT/FIXED → `scaled` (`sdp`), PERCENTAGE/DYNAMIC → `percent` (`psdp`).

## Step 4 — rules of thumb

1. Default to `sdp` for sizes and `ssp` for text. Recommend `asdp`/`assp` when the app targets tablets/TVs too.
2. Text: `ssp` (accessible). Use `sem` only for text that must not follow the user's font scale.
3. Every non-scaled strategy needs its module/package/import (e.g. `appdimens-dynamic-auto`, `appdimens_auto`, `appdimens-react-native/auto`, `webdimens/auto`). Add it when you use the token.
4. Keep one strategy per screen; don't mix many.
5. Don't scale hairlines (`1.dp`) or platform-mandated sizes.
6. Values update automatically on rotation/split screen/fold; use the `i` flag for values that must keep full-screen size (game HUDs).
7. Reference canvas 300 × 533; `16.sdp` = 16 × (smallest side / 300).

## Hub docs

- Beginner guide: [DOCS/GUIDE.md](../DOCS/GUIDE.md)
- Copy-paste examples, all platforms: [DOCS/EXAMPLES.md](../DOCS/EXAMPLES.md)
- Per-platform token/module tables: [DOCS/PLATFORMS.md](../DOCS/PLATFORMS.md)
- Upgrading old code: [DOCS/MIGRATION.md](../DOCS/MIGRATION.md)
- Rotation / split screen / foldables: [DOCS/ORIENTATION.md](../DOCS/ORIENTATION.md)
- Math: [DOCS/THEORY.md](../DOCS/THEORY.md)
