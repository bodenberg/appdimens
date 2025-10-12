# 📖 AppDimens Library Overview: The Core of Responsiveness

**AppDimens** is a unified dimension management system designed to solve a central challenge of modern UI development: **ensuring that layouts and user experiences remain consistent and visually comfortable across any screen size, ratio, or device type.**

It goes beyond the limitations of standard density-independent pixels (**Dp/Pt**), treating these values as mere starting points, which are then refined by sophisticated mathematical scaling algorithms.

---

```kotlin
dependencies {
    // Core (Dynamic + Fixed)
    implementation("com.github.bodenberg.appdimens:appdimens-dynamic:1.0.1")

    // SDP & SSP scaling (optional)
    implementation("com.github.bodenberg.appdimens:appdimens-sdps:1.0.1")
    implementation("com.github.bodenberg.appdimens:appdimens-ssps:1.0.1")

    // All in one
    implementation("com.github.bodenberg.appdimens:appdimens-all:1.0.1")
}

maven { url 'https://jitpack.io' } //or maven central
```

```kotlin
dependencies {
    // Core (Dynamic + Fixed)
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.1")

    // SDP & SSP scaling (optional)
    implementation("io.github.bodenberg:appdimens-sdps:1.0.1")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.1")

    // All in one
    implementation("io.github.bodenberg:appdimens-all:1.0.1")
}

mavenCentral()
```

### 🌐 Unified Principle and Cross-Platform Compatibility

**AppDimens** is built on a platform-agnostic architecture. Its core scaling logic is identical across platforms, ensuring consistent dimension behavior wherever your code runs.

| Platform              | Integration                                                                                | Supported Paradigms                                 |
| :-------------------- | :----------------------------------------------------------------------------------------- | :-------------------------------------------------- |
| **Android**           | Native. Extension libraries for **Jetpack Compose** and **View System (XML/Kotlin/Java)**. | Uses dynamically adjusted `Dp`, `Sp`, and `Px`.     |
| **iOS/Multiplatform** | Core logic can be used with **Swift/SwiftUI** or **Kotlin Multiplatform (KMP)**.           | Uses dynamically scaled `CGFloat` for `Pt` or `Px`. |

---

### 🧠 The Core of the Library: Two Scaling Models

The power of AppDimens lies in its two mathematical scaling models. Developers can choose the best fit for each component, achieving responsiveness that goes far beyond simple “screen size” rules.

#### 1. Fixed (FX): Subtle and Logarithmic Scaling

* **Philosophy:** **Smooth** and **controlled** growth. The adjustment is based on the screen’s **Smallest Width DP** and modulated by a **logarithmic function** that factors in the **Aspect Ratio**.
* **Highlight:** Prevents excessive growth of paddings and margins on wide tablets or 4K monitors. Ideal for preserving information density.
* **Best Use:** Paddings, margins, touch target heights (buttons, text fields), small icons.

#### 2. Dynamic (DY): Proportional and Aggressive Scaling

* **Philosophy:** **Aggressive**, **proportional** growth. The adjusted value maintains the **same percentage of the reference screen**. If an element takes up 25% of a phone screen’s width, it will also take up 25% on a tablet.
* **Highlight:** Includes logic to mitigate issues in **Multi-Window (Split Screen)** mode, disabling aggressive scaling when the UI is squeezed, avoiding layout breakage.
* **Best Use:** Container widths, large elements (e.g., hero images), or when text size should scale with viewport size.

---

### ⚡ Performance and Architecture

AppDimens is optimized for **maximum runtime efficiency**.

* **Cached Factors:** Mathematical adjustment factors (**FX** and **DY**) are calculated only once per screen configuration change (e.g., rotation). Results are cached (or remembered in Compose) and reused instantly.
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

* **Code Dependency:** Unlike resource file–based solutions, AppDimens requires developers to use code extensions (`.fxdp`, `.dydp`) or Gateway methods.
* **Physical Conversion Accuracy:** The accuracy of physical unit conversions (MM/INCH) depends on the reliability of the device’s reported density metadata (`xdpi`, `ydpi`).

---

In summary, **AppDimens** delivers a powerful and mathematically sound toolset for creating truly adaptive interfaces—regardless of device or platform.

