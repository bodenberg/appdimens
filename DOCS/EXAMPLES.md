# Examples — copy, paste, done

Every example on this page does the **same thing**: 16 of spacing, a 48-tall box and 16-size text. AppDimens then makes them look right on phones, tablets, foldables and TVs — automatically.

> **Versions:** every library is **3.2.0**, except Web (**3.2.1**) and .NET (**3.6.1**). All of them are in production.
> **Not sure which strategy to use?** Start with `sdp` / `ssp`. If your app also runs on tablets or TVs, try the `auto` versions (`asdp` / `assp`). See the [Guide](GUIDE.md).

**Jump to:** [Android Compose](#android--jetpack-compose-appdimens-dynamic) · [Android Views](#android--kotlin--java-views-appdimens-dynamic) · [Android XML](#android--xml-appdimens-sdps--appdimens-ssps) · [KMP](#kotlin-multiplatform-appdimens-kmp) · [iOS](#apple--ios--macos-appdimens-ios) · [Flutter](#flutter-appdimens-flutter) · [React Native](#react-native-appdimens-react-native) · [Web](#web-appdimens-web--npm-webdimens) · [.NET](#net-appdimens-net-binding) · [Games](#games-appdimens-games) · [Comparison](#the-same-token-on-every-platform)

---

## Android — Jetpack Compose (`appdimens-dynamic`)

**1. Install** (`build.gradle.kts`)

```kotlin
dependencies {
    implementation(platform("io.github.bodenberg:appdimens-dynamic-bom:3.2.0"))
    implementation("io.github.bodenberg:appdimens-dynamic")        // sdp, hdp, wdp, ssp…
    implementation("io.github.bodenberg:appdimens-dynamic-auto")   // optional: asdp, assp…
}
```

**2. Use**

```kotlin
import com.appdimens.dynamic.compose.*

Box(
    Modifier
        .padding(16.sdp)    // spacing
        .width(100.wdp)     // follows screen width
        .height(48.hdp)     // follows screen height
) {
    Text("Hello", fontSize = 16.ssp)   // text, respects the user's font size
}
```

**3. Phones + tablets + TVs → `auto`**

```kotlin
import com.appdimens.dynamic.compose.auto.asdp
import com.appdimens.dynamic.compose.auto.assp

Text("Hello", fontSize = 16.assp, modifier = Modifier.padding(16.asdp))
```

📘 [Beginner guide](https://github.com/bodenberg/appdimens-dynamic/blob/main/GUIDE-FOR-BEGINNERS.md) · [README](https://github.com/bodenberg/appdimens-dynamic/blob/main/README.md) · [All strategies](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/README.md)

---

## Android — Kotlin / Java Views (`appdimens-dynamic`)

Same install as above. Outside Compose, values come back in **pixels**:

```kotlin
import com.appdimens.dynamic.code.DimenSdp
import com.appdimens.dynamic.code.DimenSsp

val paddingPx = DimenSdp.sdp(context, 16)
val heightPx  = DimenSdp.hdp(context, 48)
val fontPx    = DimenSsp.ssp(context, 16)

view.setPadding(paddingPx.toInt(), paddingPx.toInt(), paddingPx.toInt(), paddingPx.toInt())
textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontPx)
```

---

## Android — XML (`appdimens-sdps` + `appdimens-ssps`)

Ready-made `@dimen` resources. Nothing is calculated at runtime.

**1. Install**

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-sdps:3.2.0")   // sizes: _16sdp, _48hdp, _100wdp
    implementation("io.github.bodenberg:appdimens-ssps:3.2.0")   // text:  _16ssp
}
```

**2. Use**

```xml
<TextView
    android:layout_width="@dimen/_100wdp"
    android:layout_height="@dimen/_48hdp"
    android:padding="@dimen/_16sdp"
    android:textSize="@dimen/_16ssp"
    android:text="Hello" />
```

The same libraries also work in Compose (`com.appdimens.sdps.compose.sdp`, `com.appdimens.ssps.compose.ssp`).

📘 [sdps README](https://github.com/bodenberg/appdimens-sdps/blob/main/README.md) · [ssps README](https://github.com/bodenberg/appdimens-ssps/blob/main/README.md)

---

## Kotlin Multiplatform (`appdimens-kmp`)

Runs unchanged on Android, iOS, desktop (JVM), macOS, Linux, Windows and the browser (JS + Wasm).

**1. Install** (`commonMain` dependencies)

```kotlin
implementation(platform("io.github.bodenberg:appdimens-kmp-bom:3.2.0"))
implementation("io.github.bodenberg:appdimens-kmp")
implementation("io.github.bodenberg:appdimens-kmp-auto")   // optional
```

**2. Use** (Compose Multiplatform)

```kotlin
import com.appdimens.kmp.compose.*

Box(Modifier.padding(16.sdp).width(100.wdp).height(48.hdp)) {
    Text("Hello", fontSize = 16.ssp)
}
```

**3. `auto`**

```kotlin
import com.appdimens.kmp.compose.auto.asdp

Spacer(Modifier.height(16.asdp))
```

📘 [Beginner guide](https://github.com/bodenberg/appdimens-kmp/blob/main/GUIDE-FOR-BEGINNERS.md) · [README](https://github.com/bodenberg/appdimens-kmp/blob/main/README.md)

---

## Apple — iOS / macOS (`appdimens-ios`)

iOS 13+, iPadOS, macOS 10.15+, tvOS 13+, watchOS 7+, visionOS 1+.

**1. Install** — Xcode › *File › Add Package Dependencies…* → `https://github.com/bodenberg/appdimens-ios`, or in `Package.swift`:

```swift
.package(url: "https://github.com/bodenberg/appdimens-ios", from: "3.2.0")
// target dependency:
.product(name: "AppDimensDynamic", package: "appdimens-ios")   // core + all common strategies
```

**2. SwiftUI** — install once per window, then use anywhere:

```swift
import SwiftUI
import AppDimensDynamic

@main
struct ExampleApp: App {
    var body: some Scene {
        WindowGroup { ContentView().appDimens() }
    }
}

struct ContentView: View {
    var body: some View {
        Text("Hello")
            .font(.system(size: 16.ssp))
            .padding(16.sdp)
            .frame(width: 100.wdp, height: 48.hdp)
    }
}
```

**3. UIKit**

```swift
import UIKit
import AppDimensDynamic

// SceneDelegate: follow rotation, Split View, Stage Manager, Dynamic Type
AppDimens.startAutomaticUpdates()

layer.cornerRadius = 12.sdp
titleLabel.font = .ssp(17, weight: .semibold)
heightAnchor.constraint(equalToConstant: 48.hdp).isActive = true
```

**4. `auto`** — `16.asdp`, `16.assp` (included in `AppDimensDynamic`).

📘 [Beginner guide](https://github.com/bodenberg/appdimens-ios/blob/main/GUIDE-FOR-BEGINNERS.md) · [README](https://github.com/bodenberg/appdimens-ios/blob/main/README.md)

---

## Flutter (`appdimens-flutter`)

Android, iOS, web and desktop.

**1. Install**

```bash
flutter pub add appdimens_flutter
flutter pub add appdimens_auto      # optional
# or everything at once: flutter pub add appdimens_bom
```

**2. Wrap your app once**

```dart
import 'package:appdimens_flutter/appdimens.dart';

void main() => runApp(
      AppDimensApp(child: MaterialApp(home: const MyPage())),
    );
```

**3. Use**

```dart
Container(
  width: 100.wdp,
  height: 48.hdp,
  padding: EdgeInsets.all(16.sdp),
  child: Text('Hello', style: TextStyle(fontSize: 16.ssp)),
)
```

**4. `auto`**

```dart
import 'package:appdimens_auto/appdimens_auto.dart';

SizedBox(height: 16.asdp);
```

📘 [Beginner guide](https://github.com/bodenberg/appdimens-flutter/blob/main/GUIDE-FOR-BEGINNERS.md) · [README](https://github.com/bodenberg/appdimens-flutter/blob/main/README.md)

---

## React Native (`appdimens-react-native`)

React Native 0.64+. Nothing to link, no pods.

**1. Install**

```bash
npm install appdimens-react-native
# or: yarn add appdimens-react-native
```

**2. Use**

```tsx
import { sdp, hdp, wdp, ssp } from 'appdimens-react-native';

<View style={{ padding: sdp(16), width: wdp(100), height: hdp(48) }}>
  <Text style={{ fontSize: ssp(16) }}>Hello</Text>
</View>
```

**3. `auto`** — strategies are imported from sub-paths, so you only bundle what you use:

```tsx
import { asdp, assp } from 'appdimens-react-native/auto';

<Text style={{ padding: asdp(16), fontSize: assp(16) }}>Hello</Text>
```

📘 [Docs](https://github.com/bodenberg/appdimens-react-native/blob/main/docs/README.md) · [README](https://github.com/bodenberg/appdimens-react-native/blob/main/README.md)

---

## Web (`appdimens-web` — npm `webdimens`)

Plain HTML/CSS, React, Vue, Svelte and Angular. You write `16sdp` instead of `16px`.

**1. Install**

```bash
npm install webdimens
```

or, without any build step:

```html
<script src="https://cdn.jsdelivr.net/npm/webdimens@3.2.1/dist/webdimens.global.js"></script>
```

**2. Start it once** (not needed with the CDN script)

```js
import { install } from 'webdimens';
install();
```

**3. Use in HTML classes — no CSS file needed**

```html
<div class="p:16sdp w:100wdp h:48hdp fs:16ssp">Hello</div>
<h1 class="fs:20ssp fs@sw600:28ssp fs@tv:40ssp">Title</h1>   <!-- bigger on tablets and TVs -->
```

**4. Use in JavaScript / React**

```js
import { sdp, ssp } from 'webdimens';
element.style.padding = `${sdp(16)}px`;
```

**5. `auto`** — just write the token; since 3.2.1 every strategy loads automatically the first time it is used

```html
<div class="p:16asdp fs:16assp">Hello</div>
```

Only CSS you write by hand (`calc(16 * var(--asdp))`) or PostCSS output needs it listed: `install({ strategies: ['auto'] })`.

📘 [Getting started](https://github.com/bodenberg/appdimens-web/blob/main/docs/getting-started.md) · [Guia em português](https://github.com/bodenberg/appdimens-web/blob/main/docs/pt-BR/getting-started.md) · [README](https://github.com/bodenberg/appdimens-web/blob/main/README.md)

---

## .NET (`appdimens-net-binding`)

.NET MAUI, WPF, Windows Forms, WinUI 3 and .NET for Android.

**1. Install** — pick your framework:

```bash
dotnet add package Bodenberg.AppDimens.Maui.Sdps      # .NET MAUI
dotnet add package Bodenberg.AppDimens.Sdps.Wpf       # WPF
dotnet add package Bodenberg.AppDimens.Sdps.WinForms  # Windows Forms
dotnet add package Bodenberg.AppDimens.Sdps.WinUI     # WinUI 3
dotnet add package Bodenberg.AppDimens.Sdps           # .NET for Android (XML @dimen)
dotnet add package AppDimens.Net.Sdk                  # every strategy (C# port of appdimens-kmp)
```

**2. Use (.NET MAUI)**

```csharp
// MauiProgram.cs
builder.UseAppDimensSdps();
```
```xml
<Label Text="Hello" Padding="{sdp:16}" FontSize="{ssp:16}" />
```
```csharp
var padding = 16.Sdp();   // in C#
```

**Other frameworks**

```csharp
// WPF:      AppDimensWpf.Initialize(this);       XAML: Margin="{sdps:Sdp 16}" FontSize="{sdps:Ssp 16}"
// WinForms: AppDimensWinForms.Initialize(this);  AppDimensWinForms.Apply(this, () => button1.Width = 120.SdpPx());
// WinUI 3:  AppDimensWinUI.Initialize(m_window); XAML: Margin="{sdps:Sdp Value=16}"
// Android:  XML android:padding="@dimen/_16sdp"  C#: DimenSdp.Sdp(this, 16) → pixels
```

📘 [Docs for every package](https://github.com/bodenberg/appdimens-net-binding/blob/main/docs/README.md) · [README](https://github.com/bodenberg/appdimens-net-binding/blob/main/README.md)

---

## Games (`appdimens-games`)

Android games — Compose, Kotlin, Java, C/C++/NDK, OpenGL ES, Vulkan, DirectX and Unity/C#.

**1. Install**

```kotlin
implementation(platform("io.github.bodenberg:appdimens-games-bom:3.2.0"))
implementation("io.github.bodenberg:appdimens-games")          // core
implementation("io.github.bodenberg:appdimens-games-native")   // optional: C/C++/JNI
```

**2. Use** (game code returns **pixels**)

```kotlin
val hud    = 48.sdp(context)    // HUD element
val locked = 48.sdpi(context)   // never changes when the window is resized
val text   = 16.ssp(context)
```

**Which strategy for which element?** HUD → `sdp` · gameplay ⭐ → `asdp` · background → `flsdp` (fill) · board that must stay fully visible → `ftsdp` (fit) · text → `fsdp` · TV → `logsdp`.

📘 [Beginner guide](https://github.com/bodenberg/appdimens-games/blob/main/DOCUMENTATION/GUIDE-FOR-BEGINNERS.md) · [Native engines](https://github.com/bodenberg/appdimens-games/blob/main/DOCUMENTATION/NATIVE-GAME-ENGINES.md)

---

## The same token on every platform

| What you want | Compose · KMP · iOS · Flutter | React Native | Web | Android XML | .NET MAUI |
|---------------|-------------------------------|--------------|-----|-------------|-----------|
| Spacing 16 | `16.sdp` | `sdp(16)` | `p:16sdp` | `@dimen/_16sdp` | `{sdp:16}` |
| Width 100 | `100.wdp` | `wdp(100)` | `w:100wdp` | `@dimen/_100wdp` | `{wdp:100}` |
| Height 48 | `48.hdp` | `hdp(48)` | `h:48hdp` | `@dimen/_48hdp` | `{hdp:48}` |
| Text 16 | `16.ssp` | `ssp(16)` | `fs:16ssp` | `@dimen/_16ssp` | `{ssp:16}` |
| Text 16, ignores user font size | `16.sem` | `sem(16)` | `fs:16sem` | — | `{sem:16}` |
| `auto` spacing | `16.asdp` | `asdp(16)` (from `/auto`) | `p:16asdp` | — | `AppDimens.Net` |
| With aspect-ratio correction | `16.sdpa` | `sdpa(16)` | `p:16sdpa` | — | `{sdpa:16}` |

`—` = not offered by that library; see its README for alternatives.

---

**Next:** [Guide](GUIDE.md) · [Platforms](PLATFORMS.md) · [Orientation](ORIENTATION.md) · [Back to the hub](../README.md)
