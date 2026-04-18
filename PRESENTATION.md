# 📖 AppDimens Library Overview: The Core of Responsiveness

This document is maintained in the **[appdimens](https://github.com/bodenberg/appdimens) meta-repository** (documentation hub). **Released libraries** are versioned and published from **platform-specific repositories** linked as Git submodules (e.g. `appdimens-dynamic`, `appdimens-ios`). Always confirm coordinates in each submodule’s README before pinning dependencies.

**AppDimens** is a unified dimension management system designed to solve a central challenge of modern UI development: **ensuring that layouts and user experiences remain consistent and visually comfortable across any screen size, ratio, or device type.**

It goes beyond the limitations of standard density-independent pixels (**Dp/Pt**), treating these values as mere starting points, which are then refined by sophisticated mathematical scaling algorithms.

> **Languages:** English | [Português (BR)](LANG/pt-BR/README.md) | [Español](LANG/es/README.md)

---

```kotlin
dependencies {
    // Core (Compose / Kotlin) — see appdimens-dynamic for current API
    implementation("io.github.bodenberg:appdimens-dynamic:3.1.4")

    // SDP & SSP (optional, XML @dimen resources)
    implementation("io.github.bodenberg:appdimens-sdps:3.1.2")
    implementation("io.github.bodenberg:appdimens-ssps:3.1.2")

    // Aggregate “all” artifact (if published for your stack) — confirm in submodule / Maven
    implementation("io.github.bodenberg:appdimens-all:2.0.1")

    // Game development (separate dependency)
    implementation("io.github.bodenberg:appdimens-games:2.0.1")
}
```

**🆕 Version 2.x / 3.x family (high level):**
- **13 Scaling Strategies**: BALANCED⭐, DEFAULT, PERCENTAGE, LOGARITHMIC, POWER, FLUID, INTERPOLATED, DIAGONAL, PERIMETER, FIT, FILL, AUTOSIZE🆕, NONE
- **Smart Inference**: Auto-selects best strategy for 18 element types
- **5x Performance**: Unified lock-free cache, ln() lookup table, optimizations
- **Artifact-specific compatibility**: Older tutorials may still mention `.fxdp`/`.dydp`; **Android Compose** work should follow **`appdimens-dynamic` 3.x** (`sdp`, `asdp`, …)

### 🌐 Unified Principle and Cross-Platform Compatibility

**AppDimens** is built on a platform-agnostic architecture. Its core scaling logic is identical across platforms, ensuring consistent dimension behavior wherever your code runs.

| Platform | Integration | Supported Paradigms |
|:---------|:-----------|:-------------------|
| **Android** | Native. Extension libraries for **Jetpack Compose**, **XML Views**, **Data Binding**, and **Games (C++/NDK)**. | Uses dynamically adjusted `Dp`, `Sp`, and `Px`. Pre-generated SDP/SSP resources. |
| **iOS** | Native **Swift** with **SwiftUI**, **UIKit**, and **Metal** for games. CocoaPods and Swift Package Manager support. | Uses dynamically scaled `CGFloat` for `Pt` or `Px`. Game-specific viewport scaling. |
| **Flutter** | **Dart** package with builders (`AppDimens.fixed` / `AppDimens.dynamic`) and `num` extensions (`.fx`, `.dy`, fluid helpers). | Cross-platform widgets; see `appdimens-flutter` README for the supported API surface. |
| **React Native** | **TypeScript/JavaScript** with React hooks. Zero native dependencies, pure JS implementation. | Platform-agnostic Fixed, Dynamic, and Fluid scaling for Android and iOS. |
| **Web** | **Framework-agnostic** core with integrations for **React**, **Vue**, **Svelte**, **Angular**, and **Vanilla JS**. | CSS-compatible output (`px`, `rem`, `clamp()`). Media query and breakpoint support. |

---

### 🧠 The Core of the Library: 13 Scaling Strategies (v2.0)

The power of AppDimens 2.0 lies in its **13 mathematical scaling strategies** based on psychophysics research. Developers can choose the best fit for each component, or use Smart Inference for automatic selection.

#### ⭐ Recommended Strategies (Cover 95% of Use Cases)

**1. BALANCED ⭐ (New Recommended Default)**
* **Philosophy:** **Hybrid** linear-logarithmic. Linear on phones (<480dp), logarithmic on tablets/TVs (≥480dp).
* **Highlight:** Best of both worlds - familiar on phones, controlled on large screens. 40% less oversizing on tablets vs linear.
* **Best Use:** Multi-device apps, buttons, spacing, most UI elements
* **Formula:** `f(x) = x×(W/W₀)` if W<480dp, else `x×(T/W₀ + k×ln(1+(W-T)/W₀))`
* **Platforms:** Android, iOS, Flutter, React Native, Web

**2. DEFAULT (formerly Fixed/FX)**
* **Philosophy:** **Logarithmic** with aspect ratio compensation. Smooth controlled growth.
* **Highlight:** ~97% linear with logarithmic AR adjustment. Backward compatible with v1.x.
* **Best Use:** Phone-focused apps, icons, backward compatibility
* **Formula:** `f(x) = x × [1 + ((S-W₀)/δ) × (ε₀ + K×ln(AR/AR₀))]`
* **Platforms:** Android, iOS, Flutter, React Native, Web

**3. PERCENTAGE (formerly Dynamic/DY)**
* **Philosophy:** **Proportional** linear scaling. Maintains constant screen percentage.
* **Highlight:** Simple, aggressive growth. Use sparingly for large containers.
* **Best Use:** Large containers, full-width grids, layouts
* **Formula:** `f(x) = x × (W / W₀)`
* **Platforms:** Android, iOS, Flutter, React Native, Web

#### Perceptual Models (Psychophysics-Based)

**4. LOGARITHMIC**: Pure Weber-Fechner Law (maximum control on TVs)
**5. POWER**: Stevens' Power Law (configurable exponent 0.60-0.90)

#### Utility Strategies

**6. FLUID**: CSS clamp-like with breakpoints (typography)
**7. INTERPOLATED**: 50% moderated linear (balanced growth)
**8. DIAGONAL**: Screen diagonal-based (true physical size)
**9. PERIMETER**: Width + height-based (balanced W+H)

#### Game Strategies

**10. FIT**: Letterbox scaling (content must fit)
**11. FILL**: Cover scaling (backgrounds, full-screen)

#### Special Strategies

**12. AUTOSIZE** 🆕: Container-aware auto-sizing (like TextView autoSizeText)
**13. NONE**: No scaling (constant size for dividers, 1dp elements)

#### Legacy Support

**SDP/SSP**: Pre-calculated resource scaling for Android XML
* Pre-generated 536+ dimension files
* Direct XML usage: `@dimen/_16sdp`
* Platforms: Android only

---

### ⚡ Performance and Architecture

AppDimens is optimized for **maximum runtime efficiency**.

* **Cached factors:** Adjustment factors for each strategy are computed per **screen configuration** (e.g., rotation) and reused from the library caches / Compose `remember` paths.
* **Low Overhead:** The final dimension calculation is simply the base Dp multiplied by the precomputed adjustment factor. This results in **negligible** performance overhead during UI rendering.
* **Advantage over Static Solutions:** Eliminates the need to generate hundreds or thousands of XML resource files (`values-sw600dp/dimens.xml`), offering full flexibility for any Dp/Pt value.

---

### ✨ Highlights and Unique Features

1. **Physical Units (MM, CM, INCH):** A built-in conversion system allows you to define dimensions based on real-world measurements (e.g., $1\text{cm}$ margin), essential for precision applications (wearables, print).
2. **Advanced Qualifiers:** Supports scaling based on custom qualifiers like `WIDTH` (total width), `HEIGHT` (total height), or `SMALL_WIDTH` (smallest screen side), and can be conditioned by device type (**UI Mode Type**: TV, Car, Watch).
3. **Smart Layout Utility:** The `calculateAvailableItemCount` function calculates how many items of a defined Dp/Pt size and spacing can fit into the available container space, optimizing grid layouts dynamically.
4. **View Integration (Android):** The **`AppDimens`** object acts as a *Gateway* to the traditional View system, enabling direct conversion to Pixels (`fixedPx`, `dynamicPx`) and seamless integration through **Data Binding Adapters** in XML.

---

### 🛑 Limitations and Considerations

* **Code-first integration:** Compose/XML/Web each have a different binding; you wire dimensions in code (or generated XML resources for SDP/SSP), not only static `values-swNNNdp` trees.
* **Physical Conversion Accuracy:** The accuracy of physical unit conversions (MM/INCH) depends on the reliability of the device’s reported density metadata (`xdpi`, `ydpi`).

---

In summary, **AppDimens** delivers a powerful and mathematically sound toolset for creating truly adaptive interfaces—regardless of device or platform.

