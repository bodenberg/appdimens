# Migration — upgrading to AppDimens 3.2

**What is this page for?** If your project uses an older AppDimens version, this page tells you **what changed** and **what to type now**. If you are starting fresh, you don't need it — go to the [Guide](GUIDE.md).

> Today every library is **3.2.0** (the .NET packages are **3.6.1**) and all of them are **in production**.

---

## 1. Old names → new names (all platforms)

Older versions described sizes with concept names. In 3.2 the names are short tokens that are **identical on every platform**:

| Old name / old API | New (3.2) | Example |
|--------------------|-----------|---------|
| DEFAULT, FIXED, `.fxdp`, `fx`, `fixed(…)`, `defaultScaling(…)` | **scaled** | `16.sdp`, `16.ssp` |
| BALANCED, `balanced(…)`, `.balanced().dp` | **auto** | `16.asdp`, `16.assp` |
| DYNAMIC, PERCENTAGE, `.dydp`, `dy`, `dynamic(…)` | **percent** (or **scaled** if you just want proportional growth) | `16.psdp`, `10.spaceW` |
| `fluid(min, max)` / `AppDimensFluid(…)` | **fluid** | `16.fsdp`, `16.fssp` |
| NONE | plain `dp` / `sp` / `pt` / `px` | `1.dp` |

> **Careful:** BALANCED is **`auto`** (`asdp`), not `scaled` (`sdp`).

The other strategies (`power`, `interpolated`, `logarithmic`, `diagonal`, `perimeter`, `fit`, `fill`, `density`, `resize`, `units`) are explained in the [Guide](GUIDE.md#all-14-strategies-in-plain-words). Their names on each platform: [PLATFORMS.md](PLATFORMS.md#3-strategies-on-each-platform).

---

## 2. Upgrade steps per platform

### Android — Compose / Kotlin / Java (`appdimens-dynamic`)

```kotlin
dependencies {
    implementation(platform("io.github.bodenberg:appdimens-dynamic-bom:3.2.0"))
    implementation("io.github.bodenberg:appdimens-dynamic")
    // add each strategy you import, e.g.:
    implementation("io.github.bodenberg:appdimens-dynamic-auto")
}
```

- Since 3.1.6 each strategy is a **separate module**. If you import `com.appdimens.dynamic.compose.auto.*` without adding `appdimens-dynamic-auto`, the build tells you exactly which line to add.
- Kotlin package names did not change.
- Details: [README](https://github.com/bodenberg/appdimens-dynamic/blob/main/README.md) · [MODULES.md](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/MODULES.md)

**Typical edit:**

```diff
-Modifier.padding(16.balanced().dp)
+import com.appdimens.dynamic.compose.auto.asdp
+Modifier.padding(16.asdp)
```

### Android — XML (`appdimens-sdps`, `appdimens-ssps`)

Only the version changes; resource names (`@dimen/_16sdp`, `@dimen/_14ssp`) are the same.

```kotlin
implementation("io.github.bodenberg:appdimens-sdps:3.2.0")
implementation("io.github.bodenberg:appdimens-ssps:3.2.0")
```

### Kotlin Multiplatform (`appdimens-kmp`) — 1.0.x → 3.2.0

The version number **jumped from 1.0.1 to 3.2.0** so that all AppDimens libraries share one version. The API is the same family API.

```kotlin
implementation(platform("io.github.bodenberg:appdimens-kmp-bom:3.2.0"))
implementation("io.github.bodenberg:appdimens-kmp")
```

Details: [CHANGELOG](https://github.com/bodenberg/appdimens-kmp/blob/main/CHANGELOG.md)

### Apple (`appdimens-ios`) — 3.1.x → 3.2.0

```swift
.package(url: "https://github.com/bodenberg/appdimens-ios", from: "3.2.0")
```

- New: zero-argument accessors (`16.sdp`, `16.ssp`) — no configuration object needed in most code.
- SwiftUI: add `.appDimens()` once per window. UIKit: call `AppDimens.startAutomaticUpdates()`.
- Details: [iOS migration guide](https://github.com/bodenberg/appdimens-ios/blob/main/Documentation/MIGRATION.md) · [CHANGELOG](https://github.com/bodenberg/appdimens-ios/blob/main/CHANGELOG.md)

### Flutter (`appdimens-flutter`) — 2.x → 3.2.0 ⚠️ breaking

3.2 is a **rewrite**. The package is now `appdimens_flutter` and the old `fx` / `dy` builders are gone.

```bash
flutter pub add appdimens_flutter
```

| Old (2.x) | New (3.2) |
|-----------|-----------|
| `100.0.fx` / `100.dy` | `100.sdp` |
| `AppDimens.fixed(100).calculate(ctx)` | `100.sdp` |
| `fxFontSize(18, ctx)` | `18.ssp` |
| `AppDimensFluid(16, 24)` | `16.fsdp` (package `appdimens_fluid`) |
| `AppDimensProvider` | `AppDimensApp(child: …)` |

Full table: [Flutter MIGRATION.md](https://github.com/bodenberg/appdimens-flutter/blob/main/MIGRATION.md)

### React Native (`appdimens-react-native`) — 2.x → 3.2.0 ⚠️ breaking

3.2 is a **rewrite** that mirrors the Android API. `balanced`, `fx`, `dy` and `AppDimensBuilder` were removed.

```diff
-import { balanced } from 'appdimens-react-native';
-padding: balanced(16)
+import { asdp } from 'appdimens-react-native/auto';
+padding: asdp(16)
```

Details: [CHANGELOG](https://github.com/bodenberg/appdimens-react-native/blob/main/CHANGELOG.md) · [Migration from Android names](https://github.com/bodenberg/appdimens-react-native/blob/main/docs/MIGRATION-FROM-ANDROID.md)

### Web (`appdimens-web`, npm `webdimens`) — 2.x → 3.2.0 ⚠️ breaking

3.2 is a **rewrite** that mirrors the Android API. The BALANCED builder and the `Fixed` / `Dynamic` / `Fluid` classes were removed.

```diff
-webdimens.balanced(16)
+import 'webdimens/auto';
+<div class="p:16asdp">…</div>      <!-- or asdp(16) in JS -->
```

Details: [CHANGELOG](https://github.com/bodenberg/appdimens-web/blob/main/CHANGELOG.md) · [README](https://github.com/bodenberg/appdimens-web/blob/main/README.md)

### .NET (`appdimens-net-binding`) → 3.6.1

```bash
dotnet add package Bodenberg.AppDimens.Maui.Sdps --version 3.6.1
```

- The Android bindings (`Bodenberg.AppDimens.Sdps`, `.Ssps`, `.Dynamic`) now wrap the native libraries **3.2.0**.
- New in 3.6.1: WPF, Windows Forms and WinUI 3 packages, and `AppDimens.Net.*` (C# port of `appdimens-kmp`).
- Details: [README](https://github.com/bodenberg/appdimens-net-binding/blob/main/README.md)

### Games (`appdimens-games`) — 2.0.1 / 3.0.0 → 3.2.0

```kotlin
implementation(platform("io.github.bodenberg:appdimens-games-bom:3.2.0"))
implementation("io.github.bodenberg:appdimens-games")
```

3.x replaced the deprecated 2.0.1 API with the same family names used on Android (`sdp`, `asdp`, `flsdp`…). Details: [README](https://github.com/bodenberg/appdimens-games/blob/main/README.md)

---

## 3. Checklist

1. Update the dependency to the version above.
2. Search your code for old names (`balanced`, `fx`, `dy`, `fixed`, `dynamic`, `defaultScaling`) and replace them using the table in section 1.
3. Add the module of every extra strategy you use (e.g. `…-auto`).
4. Do the one-time setup your platform needs (Flutter `AppDimensApp`, SwiftUI `.appDimens()`, Web `install()`, MAUI `builder.UseAppDimensSdps()`).
5. Run the app on a phone and a tablet and compare.

---

[← Documentation index](README.md) · [Guide](GUIDE.md) · [Platforms](PLATFORMS.md) · [Examples](EXAMPLES.md) · [Back to the hub](../README.md)
