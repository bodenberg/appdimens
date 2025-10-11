# 📚 AppDimens: Comprehensive Documentation

## 1. What Is the AppDimens Library?

**AppDimens** is a dimensioning system that replaces the direct use of fixed **Dp** (Density-independent Pixels) values with dynamically adjusted values based on the **actual screen dimensions**.

While Android’s default Dp (1 Dp = 1/160 inch) is constant, AppDimens treats it as a **base value** that is scaled intelligently (and predictably) across different screen sizes and aspect ratios.

### 🎯 Target Audience

Developers who want to:

1. **Maintain Visual Consistency:** Ensure that a `48dp` button looks balanced on a $360\text{dp}$ phone and a $1024\text{dp}$ tablet.
2. **Achieve Refined Responsiveness:** Avoid excessive growth on ultra-large screens (e.g., $4K$ TVs) or text shrinking on very small screens.
3. **Enable Unified Support:** Easily integrate advanced dimensioning systems in **Views/XML** (via Gateway and Data Binding) and **Jetpack Compose** (via Extensions).

---

## 2. The Mathematical Core: FXDP (Fixed) vs. DYDP (Dynamic)

The library provides two main scaling models — each with a different mathematical philosophy — to meet different design needs.

| Feature           | Fixed (FX) – Subtle Adjustment (`fxdp`, `fxsp`, `fxpx`, `fxem`)                                                                                | Dynamic (DY) – Proportional Adjustment (`dydp`, `dysp`, `dypx`, `dyem`)                                                                            |
| :---------------- | :------------------------------------------------------------------------------------------------------------------------------------- | :----------------------------------------------------------------------------------------------------------------------------------------- |
| **Philosophy**    | **Logarithmic Adjustment (Refined)**. Growth is smooth and slows down on very large screens. Recommended default for most UI elements. | **Percentage-Based Adjustment (Aggressive)**. Keeps the same percentage of the reference screen size. Grows aggressively on large screens. |
| **Key Formula**   | Base Adjustment Factor × **Logarithmic Aspect Ratio (AR)** Adjustment.                                                                 | $\text{Adjusted Value} = \frac{\text{Base DP}}{\text{Reference DP}} \times \text{Screen Dimension}$                                        |
| **Highlight**     | The **Aspect Ratio factor** smooths scaling on very wide (TV) or tall screens, preventing oversized elements.                          | Useful for elements that must **fill a constant fraction** of the available space (e.g., card widths in `LazyGrid`).                       |
| **Extra Control** | Customizable **sensitivity coefficient (K)** for logarithmic adjustment.                                                               | Includes built-in logic to **ignore scaling** in **Multi-Window** mode, preventing UI breakage in small windows.                           |
| **Typical Use**   | Button heights, paddings, icons, title fonts (comfortable visual scale).                                                               | Container widths, spacers, viewport-dependent dimensions.                                                                                  |

### When to Use Each Option?

* **Use FX (Fixed):** For critical components that should keep their “feel.” A $16\text{dp}$ margin should scale to $20\text{dp}$ or $24\text{dp}$ on a tablet, but not to $40\text{dp}$. FX ensures controlled, subtle growth.
* **Use DY (Dynamic):** For the main layout. If a card is $100\text{dp}$ on a $360\text{dp}$ screen (27.7% of the width), Dynamic will keep it at approximately $27.7%$ on any screen (`ScreenType.LOWEST` recommended).

---

## 3. Architecture and Performance

The library is designed to minimize rendering performance impact.

### Performance Strategy

1. **Single Factor Calculation:** Adjustment factors (`ScreenAdjustmentFactors`) are computed **only once** per configuration change (e.g., rotation, window resize). They are cached (`remember` in Compose or static in the View System).
2. **Optimized Extensions/Gateway:** Using `Int` or `Float` extensions (e.g., `56.fxdp`) or Gateway methods (e.g., `AppDimens.fixedPx()`) ensures fast calculations by applying cached factors.

### Comparison with Qualifier-Based Solutions (e.g., sdp/ssp)

| Solution                | Approach                                                  | Advantages                                                                       | Disadvantages                                                                                                            |
| :---------------------- | :-------------------------------------------------------- | :------------------------------------------------------------------------------- | :----------------------------------------------------------------------------------------------------------------------- |
| **AppDimens FX/DY**     | **Runtime Calculation**.                                  | Full flexibility, **custom sizes** (e.g., `17dp`, `49dp`), fewer resource files. | Small runtime calculation overhead (factor is fixed).                                                                    |
| **Traditional SDP/SSP** | **Static Qualified Values** (`dimens.xml` per `sw600dp`). | Zero runtime overhead.                                                           | Requires generating thousands of XML dimension files (e.g., $1\text{dp}$ to $600\text{dp}$), making custom sizes harder. |

**Performance Conclusion:** AppDimens trades the static complexity of XML (SDP) for **optimized, cached runtime calculations**, offering more flexibility and custom dimension support.

---

## 4. Highlights and Advanced Features

### A. Dynamic Qualifiers and UiMode

The library allows customizing scaling based on screen dimensions (`DpQualifier`) and UI mode (`UiModeType`).

| Class/Enum        | Purpose                                                                       | Available Types                                            |
| :---------------- | :---------------------------------------------------------------------------- | :--------------------------------------------------------- |
| **`DpQualifier`** | Defines which screen dimension is used as the custom scaling base.            | `SMALL_WIDTH` (`smallestWidthDp`), `WIDTH`, `HEIGHT`       |
| **`UiModeType`**  | Allows different scaling factors for specific device types (higher priority). | `TELEVISION`, `CAR`, `WATCH`, `NORMAL`, `VR_HEADSET`, etc. |

### B. Physical Units (`AppDimensPhysicalUnits`)

The Physical Units subsystem (MM, CM, INCH) allows defining dimensions based on **real-world measurements**. This is crucial for wearables (Wear OS) or applications requiring absolute precision (e.g., printing, measurement systems).

* **Usage:** `AppDimensPhysicalUnits.toMm(5.0f, resources)` converts $5\text{mm}$ to **Pixels (PX)**.
* **Applications:** Accurately calculating the radius of round devices (Wear OS) using `radius()`.

### C. Smart Layout Utility (`calculateAvailableItemCount`)

This utility calculates how many items of a given size fit into a container, solving the common problem of defining dynamic `spanCount` in `GridLayoutManager` or `LazyVerticalGrid`.

* **Views/XML:** `AppDimens.calculateAvailableItemCount(containerSizePx, itemSizeDp, itemMarginDp, resources)`.

---

## 5. Limitations and Requirements

| Area               | Limitation/Requirement                                                                                                                         | Impact                                                                                              |
| :----------------- | :--------------------------------------------------------------------------------------------------------------------------------------------- | :-------------------------------------------------------------------------------------------------- |
| **Performance**    | Adjustment factor is computed at runtime.                                                                                                      | Although optimized, if runtime overhead is a major concern, an XML-only solution may be preferable. |
| **Physical Units** | Accuracy depends on the device’s provided `xdpi`/`ydpi` values in `DisplayMetrics`.                                                            | On emulators or devices with incorrect density metadata, physical conversions may be inaccurate.    |
| **Usage in Views** | Requires using the `AppDimens` object or custom `BindingAdapters`.                                                                             | Not a “plug-and-play” XML generator. Your code or XML must call the library’s functions.            |
| **Value Range**    | Some extension-based solutions (Sdp-like) may have a practical value limit (e.g., $\pm 600$) to optimize resources and avoid startup overhead. | Developers should keep base DP values within a reasonable range.                                    |

