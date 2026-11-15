---
name: appdimens
description: Use this skill for any responsive sizing question in a project that uses (or should use) AppDimens on any platform — Android (Jetpack Compose, Kotlin, Java, XML @dimen), Kotlin Multiplatform, iOS/macOS (SwiftUI, UIKit), Flutter, React Native, Web (webdimens), .NET (MAUI, WPF, WinForms, WinUI, .NET for Android), Qt (Widgets/QML), GTK 3/4, LVGL (embedded), Roku (BrightScript/SceneGraph), Python (Tkinter, PySide, Kivy, pygame, Blender) or games on any engine/platform (Unity, Unreal, Godot, C/C++, Android; Windows, Linux, Steam Deck, Proton, macOS, iOS, Web, consoles). Trigger when the user mentions AppDimens, appdimens-*, webdimens, sdp, hdp, wdp, ssp, sem, asdp, "16sdp", @dimen/_16sdp, {sdp:16}, making UI look right on tablets/foldables/TVs, or choosing a scaling strategy. This hub skill picks the right library, install line and token names, then defers to that library's own skill.
---

# AppDimens — hub skill (routes to the right library)

AppDimens is one family of responsive-size libraries with **the same names and the same math on every platform**. All libraries are **in production**: version **3.2.0** (Web `webdimens` **3.2.1**, .NET **3.6.1**).

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
| Web | `appdimens-web` (npm `webdimens`) | `npm install webdimens` (3.2.1) then `install()`; strategies load automatically when a token is used (hand-written CSS/PostCSS: `install({ strategies: ['auto'] })`) | [appdimens-web/skills/SKILL.md](https://github.com/bodenberg/appdimens-web/blob/main/skills/SKILL.md) |
| .NET MAUI / WPF / WinForms / WinUI / .NET for Android | `appdimens-net-binding` | `dotnet add package Bodenberg.AppDimens.Maui.Sdps` (MAUI) · `Bodenberg.AppDimens.Sdps.Wpf` / `.WinForms` / `.WinUI` · `AppDimens.Net.Sdk` (all strategies) · Android: `Bodenberg.AppDimens.Sdps` / `.Ssps` / `.Dynamic` | [appdimens-net-binding/skills/](https://github.com/bodenberg/appdimens-net-binding/tree/main/skills) (`appdimens-sdps-net`, `appdimens-net`, `appdimens-android-sdps-ssps`, `appdimens-android-dynamic`) |
| Qt 5.12+ / Qt 6 (Widgets, QML) | `appdimens-qt` | CMake `add_subdirectory(appdimens-qt)` → `AppDimens::Qt` (or `appdimens-qt.pri`); `AppDimens::Scope::registerQmlTypes()` | [appdimens-qt/skills/appdimens-qt/SKILL.md](https://github.com/bodenberg/appdimens-qt/blob/main/skills/appdimens-qt/SKILL.md) |
| GTK 3 / GTK 4 (C, PyGObject, GJS, Vala) · any C/C++ toolkit | `appdimens-gtk` | Meson `-Dgtk=4` / CMake; pkg-config `appdimens-gtk4`; core only: `include/appdimens/appdimens.h` | [appdimens-gtk/skills/appdimens-gtk/SKILL.md](https://github.com/bodenberg/appdimens-gtk/blob/main/skills/appdimens-gtk/SKILL.md) |
| LVGL 8.3 / 9 (ESP32, STM32, RP2040, Zephyr, Arduino, embedded Linux) | `appdimens-lvgl` | ESP-IDF component, PlatformIO `bodenberg/appdimens-lvgl`, Arduino, Zephyr module, CMake | [appdimens-lvgl/skills/appdimens-lvgl/SKILL.md](https://github.com/bodenberg/appdimens-lvgl/blob/main/skills/appdimens-lvgl/SKILL.md) |
| Roku (BrightScript / SceneGraph channels) | `appdimens-roku` | copy `source/AppDimens.brs`, `AppDimensTokens.brs` (+ `AppDimensCustom.brs`) | [appdimens-roku/skills/appdimens-roku/SKILL.md](https://github.com/bodenberg/appdimens-roku/blob/main/skills/appdimens-roku/SKILL.md) |
| Python (Tkinter, PySide/PyQt, Kivy, pygame, pyglet, wx, Dear PyGui, Panda3D) · Blender | `appdimens-py` | `pip install appdimens`; Blender: add-on zip from `scripts/build_blender_addon.py` | [appdimens-py/skills/appdimens-py/SKILL.md](https://github.com/bodenberg/appdimens-py/blob/main/skills/appdimens-py/SKILL.md) |
| Games — Unity, Unreal Engine 5, Godot 4, C/C++ engines, .NET engines, Android (any platform/graphics API/upscaler) | `appdimens-games` | Unity UPM `…appdimens-games.git?path=/engines/unity/com.bodenberg.appdimens.games` · UE5 plugin `engines/unreal/AppDimens` · Godot `engines/godot/addons/appdimens` · CMake `native/` · Android `implementation(platform("io.github.bodenberg:appdimens-games-bom:3.3.0"))` | [appdimens-games/skills/SKILL.md](https://github.com/bodenberg/appdimens-games/blob/main/skills/SKILL.md) |

**Full skill pack:** [appdimens-skills](https://github.com/bodenberg/appdimens-skills) (`npx appdimens-skills`) installs, for every agent (Claude Code, Codex, Gemini CLI, Cursor, Copilot, Windsurf…), a core `layout-intent` skill (base screen + layout intent → strategy/token), workflow skills (`design-to-code`, `claude-design`, `code-edit`, `new-screen`), one platform skill per library, a `kit` CLI to compute base values (`npx appdimens-skills kit normalize …`) and a validator (`kit lint`). If it is installed in the project (`.claude/skills/`, `.agents/skills/`, `.appdimens/`), prefer it; in this hub it lives in `appdimens-skills/skills/`.

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
| Games (Android) | `GameScreen.updateFromContext(context)` on resize |
| Games (Unity / Unreal / Godot) | automatic (`AppDimensScreen` / engine subsystem / autoload); UE5: DPI Scale Rule = `AppDimensDPIScalingRule` |
| Games (C/C++) | `adg_metrics_from_surface()` on swapchain resize (or `adg_publish_px()` with the C ABI) |
| Qt | `AppDimens::Scope::registerQmlTypes()` before loading QML; Widgets: `new AppDimens::Scope(&window, &window)` |
| GTK | `appdimens_scope_new_for_widget(window)` |
| LVGL | `lv_appdimens_init(NULL)` after registering the display |
| Roku | include `source/AppDimens.brs` + `AppDimensTokens.brs`, then `sdp(720)` (global, like the Web) or `ad = AppDimens()` → `ad.sdp(720)` |
| Python | an adapter: `bind_tk(root, scope)`, `metrics_from_qt(widget)`, `bind_kivy(scope)`, `metrics_from_pygame(surface)` |

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
3. Every non-scaled strategy needs its module/package/import (e.g. `appdimens-dynamic-auto`, `appdimens_auto`, `appdimens-react-native/auto`). Add it when you use the token. Exception: Web 3.2.1+ registers strategies automatically.
4. Keep one strategy per screen; don't mix many.
5. Don't scale hairlines (`1.dp`) or platform-mandated sizes.
6. Values update automatically on rotation/split screen/fold; use the `i` flag for values that must keep full-screen size (game HUDs).
7. Reference canvas 300 × 533; `16.sdp` = 16 × (smallest side / 300).
8. No built-in passes QA, or the user wants their own formula, per-device rules or a shared strategies JSON → the **custom** module of the library (`CustomScaleStrategy { … }` factor, `scalingFormula { … }` value, text formulas for JSON; tokens `cdp` / `csp`). See [DOCS/CUSTOM.md](../DOCS/CUSTOM.md) and the `appdimens-custom-strategies` skill of [appdimens-skills](https://github.com/bodenberg/appdimens-skills).

## Hub docs

- Beginner guide: [DOCS/GUIDE.md](../DOCS/GUIDE.md)
- Copy-paste examples, all platforms: [DOCS/EXAMPLES.md](../DOCS/EXAMPLES.md)
- Per-platform token/module tables: [DOCS/PLATFORMS.md](../DOCS/PLATFORMS.md)
- Upgrading old code: [DOCS/MIGRATION.md](../DOCS/MIGRATION.md)
- Rotation / split screen / foldables: [DOCS/ORIENTATION.md](../DOCS/ORIENTATION.md)
- Math: [DOCS/THEORY.md](../DOCS/THEORY.md)
- Custom strategies (your own rule, all platforms): [DOCS/CUSTOM.md](../DOCS/CUSTOM.md)
