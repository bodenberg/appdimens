# AppDimens — Beginner's Guide

**What is this page for?** It explains AppDimens in plain words: what problem it solves, which library to install, which "strategy" to choose, and the few rules you need to know. No math required. (If you like math, see [THEORY.md](THEORY.md).)

> All AppDimens libraries are **in production**. Current version: **3.2.0** (Web **3.2.1**, .NET **3.6.1**).

---

## 1. The problem in 30 seconds

Apps describe sizes with units like `dp` (Android), `pt` (Apple) or `px` (web). These units make a button the **same physical size** on every screen. That sounds good — until you open the app on a tablet or a TV:

- On a **phone**, a 48 button fills a comfortable part of the screen.
- On a **tablet**, the same 48 looks small and the screen feels empty.
- On a **TV**, it is almost lost.

**AppDimens** adjusts each size to the screen it is on. You write the size you designed (`16`), add a short suffix (`16.sdp`), and the library does the rest — also when the device rotates, splits the screen or folds.

---

## 2. Your first 5 minutes

1. **Find your platform** in the [table in section 3](#3-which-library-do-i-install) and install the library.
2. **Replace sizes:**
   - spacing, widths, heights, corners: `16.dp` → **`16.sdp`**
   - text: `16.sp` → **`16.ssp`**
3. **Run the app** on a phone and on a tablet (or an emulator). That's it.

The design is based on a reference phone **300 units wide**. On that phone `16.sdp` is exactly 16; on a 360-wide phone it is 19.2; on a 720-wide tablet it is 38.4.

Copy-paste code for every platform: **[EXAMPLES.md](EXAMPLES.md)**.

---

## 3. Which library do I install?

| I'm building with… | Library | Install |
|--------------------|---------|---------|
| Android — Jetpack Compose, Kotlin, Java | [appdimens-dynamic](https://github.com/bodenberg/appdimens-dynamic) | `implementation("io.github.bodenberg:appdimens-dynamic:3.2.0")` |
| Android — XML layouts | [appdimens-sdps](https://github.com/bodenberg/appdimens-sdps) | `implementation("io.github.bodenberg:appdimens-sdps:3.2.0")` |
| Android — XML text sizes | [appdimens-ssps](https://github.com/bodenberg/appdimens-ssps) | `implementation("io.github.bodenberg:appdimens-ssps:3.2.0")` |
| Kotlin Multiplatform | [appdimens-kmp](https://github.com/bodenberg/appdimens-kmp) | `implementation("io.github.bodenberg:appdimens-kmp:3.2.0")` |
| iOS, iPadOS, macOS, tvOS, watchOS, visionOS | [appdimens-ios](https://github.com/bodenberg/appdimens-ios) | Swift Package Manager → `https://github.com/bodenberg/appdimens-ios` from `3.2.0` |
| Flutter | [appdimens-flutter](https://github.com/bodenberg/appdimens-flutter) | `flutter pub add appdimens_flutter` |
| React Native | [appdimens-react-native](https://github.com/bodenberg/appdimens-react-native) | `npm install appdimens-react-native` |
| Web (HTML/CSS, React, Vue, Svelte, Angular) | [appdimens-web](https://github.com/bodenberg/appdimens-web) | `npm install webdimens` |
| .NET MAUI, WPF, WinForms, WinUI 3, .NET for Android | [appdimens-net-binding](https://github.com/bodenberg/appdimens-net-binding) | `dotnet add package Bodenberg.AppDimens.Maui.Sdps` (MAUI — see its docs for others) |
| Android games (Kotlin, C/C++, OpenGL, Vulkan, Unity) | [appdimens-games](https://github.com/bodenberg/appdimens-games) | `implementation("io.github.bodenberg:appdimens-games:3.2.0")` |

**Android: XML or Compose?** If your screens are XML layouts, use `appdimens-sdps` + `appdimens-ssps` (ready-made `@dimen/_16sdp` resources). If you use Jetpack Compose or set sizes in code, use `appdimens-dynamic`. Using both in the same app is fine.

Each library has its own beginner guide: [Android](https://github.com/bodenberg/appdimens-dynamic/blob/main/GUIDE-FOR-BEGINNERS.md) · [KMP](https://github.com/bodenberg/appdimens-kmp/blob/main/GUIDE-FOR-BEGINNERS.md) · [iOS](https://github.com/bodenberg/appdimens-ios/blob/main/GUIDE-FOR-BEGINNERS.md) · [Flutter](https://github.com/bodenberg/appdimens-flutter/blob/main/GUIDE-FOR-BEGINNERS.md) · [Games](https://github.com/bodenberg/appdimens-games/blob/main/DOCUMENTATION/GUIDE-FOR-BEGINNERS.md) · [React Native docs](https://github.com/bodenberg/appdimens-react-native/blob/main/docs/README.md) · [Web getting started](https://github.com/bodenberg/appdimens-web/blob/main/docs/getting-started.md) · [.NET docs](https://github.com/bodenberg/appdimens-net-binding/blob/main/docs/README.md)

---

## 4. The names, explained

Every name is built from small pieces. Once you know them, you can read any AppDimens token.

### Which side of the screen to follow

| Name | Follows | Use it for |
|------|---------|-----------|
| `sdp` | the **smallest side** of the screen | almost everything: padding, margins, icons, corners |
| `wdp` | the screen **width** | things that should grow with the width (columns, cards) |
| `hdp` | the screen **height** | things that should grow with the height (rows, headers) |

### Text

| Name | Meaning |
|------|---------|
| `ssp` | text that **respects** the user's font-size setting (accessibility). Use this by default. |
| `sem` | text that **ignores** the user's font-size setting. Only for text that must fit a fixed space (badges, tiny labels). |
| `hsp`, `wsp` / `hem`, `wem` | same, but following height or width |

### Optional endings

| Ending | Meaning | Example |
|--------|---------|---------|
| `a` | also correct for the screen **shape** (very long or very wide screens) | `16.sdpa` |
| `i` | **ignore split-screen** / multi-window — keep the full-screen size | `16.sdpi` |
| `ia` | both of the above | `16.sdpia` |
| `Px` | return **pixels** instead of dp/points | `16.sdpPx` |
| `Ph`, `Pw`, `Lh`, `Lw` | change which side is measured when the device **rotates** (see [ORIENTATION.md](ORIENTATION.md)) | `32.sdpPh` |

### How it looks on each platform

| Compose · KMP · iOS · Flutter | React Native | Web | Android XML | .NET MAUI |
|-------------------------------|--------------|-----|-------------|-----------|
| `16.sdp` | `sdp(16)` | `16sdp` (e.g. `class="p:16sdp"`) | `@dimen/_16sdp` | `{sdp:16}` |

---

## 5. Which strategy should I choose?

A **strategy** is the rule used to grow a size on bigger screens. You choose it with a short **prefix** before the name (`a` + `sdp` = `asdp`).

### The quick answer

```text
Is your app only for phones?            → scaled   (sdp, ssp)        ← start here
Does it also run on tablets or TVs?     → auto     (asdp, assp)  ⭐
Still too big on huge TVs?              → logarithmic (logsdp, logssp)
Text that must stay between a min/max?  → fluid    (fsdp, fssp)
A game or a canvas?                     → fit / fill (ftsdp / flsdp)
```

### See the difference (a size of 16)

| Screen width | scaled `16.sdp` | auto `16.asdp` |
|--------------|-----------------|----------------|
| 300 (reference phone) | 16 | 16 |
| 360 (common phone) | 19.2 | 19.2 |
| 720 (tablet) | 38.4 | 29.4 |
| 1080 (TV) | 57.6 | 32.6 |

`auto` grows exactly like `scaled` up to 480 wide, then slows down — so tablets and TVs don't get giant buttons.

### All 14 strategies in plain words

| Strategy | Prefix (example) | In plain words | Good for |
|----------|------------------|----------------|----------|
| **scaled** | `sdp`, `ssp` | Grows in proportion to the screen. | Phone apps, most UIs |
| **auto** ⭐ | `asdp`, `assp` | Proportional up to 480, then grows slowly. | Phone + tablet + TV in one app |
| **percent** | `psdp`, `spaceW` | A true percentage of the screen. | Grids, full-width layouts |
| **power** | `pwsdp`, `pwssp` | Grows, but a bit less than proportional. | Softer growth on big screens |
| **fluid** | `fsdp`, `fssp` | Grows only between a minimum and a maximum (like CSS `clamp()`). | Typography |
| **interpolated** | `isdp`, `issp` | Halfway between "fixed" and "proportional". | Moderate growth |
| **logarithmic** | `logsdp`, `logssp` | Grows very slowly. | Very large TVs |
| **diagonal** | `dgsdp`, `dgssp` | Follows the screen diagonal. | Sizes that should "feel" physical |
| **perimeter** | `prsdp`, `prssp` | Follows width + height together. | Balanced growth on odd shapes |
| **fit** | `ftsdp`, `ftssp` | Like a letterbox: content always fits, never crops. | Game boards, canvases |
| **fill** | `flsdp`, `flssp` | Like "cover": content always fills the screen. | Backgrounds |
| **density** | `dsdp`, `dssp` | Follows the pixel density of the screen. | Matching platform DPI buckets |
| **resize** | `autoResizeTextSp(…)`, `AutoResizeText` | Picks the biggest size that still fits a box. | Titles that must fit one line |
| **units** | `10.mm`, `2.cm`, `1.inch` | Real-world sizes. | Kiosks, printing, touch targets |

The **scaled** strategy is always included. The others are **optional modules** — install only the ones you use (e.g. Android `appdimens-dynamic-auto`, Flutter `appdimens_auto`, React Native `appdimens-react-native/auto`). On the Web you don't install anything extra: strategies load automatically when you use their tokens. The per-platform names of every strategy are listed in [PLATFORMS.md](PLATFORMS.md).

> **Tip:** don't mix many strategies on one screen. Pick one for layout (usually `scaled` or `auto`) and keep it consistent.

---

## 6. Good habits

- **Use `ssp` for text** so users who need bigger fonts still get them.
- **Keep the same strategy** across a screen; switch only when you have a reason.
- **Test on at least two sizes**: a phone and a tablet (or a resizable emulator/browser window).
- **Fixed things stay fixed:** hairline dividers (`1.dp`) or system-mandated sizes don't need AppDimens.
- **Split screen:** values update automatically. If something must keep its full-screen size (a game HUD, for example), add `i`: `48.sdpi`.
- **Rotation:** the smallest side does not change when you rotate, so `sdp` stays stable. If you want a value to follow the long side in landscape, use the inverters in [ORIENTATION.md](ORIENTATION.md).
- **Need a different value on TV or large tablets?** Every platform has *facilitators* such as `sdpMode(…)`, `sdpQualifier(…)` and `sdpScreen(…)`, and a builder `scaledDp(16).screen(…)`. See your library's README.

---

## 7. Performance

You don't need to worry about it. Values are cached; one lookup costs a few **nanoseconds** (a nanosecond is a billionth of a second). Android numbers measured on a real device: [PERFORMANCE.md](https://github.com/bodenberg/appdimens-dynamic/blob/main/PERFORMANCE.md).

On Android, the libraries already include their R8/ProGuard rules — nothing to add ([R8-PROGUARD.md](https://github.com/bodenberg/appdimens-dynamic/blob/main/R8-PROGUARD.md)).

---

## 8. Using an AI coding assistant?

A **skill** is a set of instructions for AI assistants (Claude Code, Codex, Gemini CLI, Cursor, Copilot, Windsurf…). The easiest way to get them all is one command:

```bash
npx appdimens-skills
```

It installs [appdimens-skills](https://github.com/bodenberg/appdimens-skills) for the AI tools it finds on your computer. Your assistant will then ask (or detect) **which screen your design was made for**, understand **what each element should do**, pick the right library and values, and **check its own edits**. It works from Figma, Claude Design, existing screens or brand-new screens.

You can also point your assistant to:

- the hub skill — [`skills/SKILL.md`](../skills/SKILL.md) — which routes to the right library, or
- the skill inside the library you use (listed in the [README](../README.md#-ai-coding-assistants-skills)).

---

## 9. Common questions

**My app looks too big on tablets.** Switch from `sdp`/`ssp` to `asdp`/`assp` (auto), or try `pwsdp` (power).

**My text is too small on TVs.** Use `ssp`/`assp` instead of plain `sp`, or set a TV-specific value with `sspMode(…)`.

**My layout breaks in split screen.** That's usually expected (the window is smaller). Use the `i` ending on values that must not change.

**Can designers use these names?** Yes. "16 sdp" means the same on every platform, so it works as a design-system token.

**I'm upgrading from an old version.** See [MIGRATION.md](MIGRATION.md).

---

**Next:** [Examples](EXAMPLES.md) · [Platforms](PLATFORMS.md) · [Orientation](ORIENTATION.md) · [Migration](MIGRATION.md) · [Theory](THEORY.md) · [Back to the hub](../README.md)
