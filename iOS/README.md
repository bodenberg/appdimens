# 📚 AppDimens: Responsive Sizing for SwiftUI

**AppDimens** is an intelligent sizing system for SwiftUI, inspired by Jetpack Compose. It allows you to build **layouts that dynamically adapt** to any screen size, aspect ratio, and device/screen qualifier.

The system uses a **reference point (DP) of $300\text{pt}$** for all calculations.

## 🚀 1. Core Concepts (Fixed vs. Dynamic)

| Mode        | Description                                                                                                                                                                            | Main Usage                                                              | Syntax (Example) |
| :---------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------------------- | :--------------- |
| **Fixed**   | **Weighted Scaling.** The base value is scaled using a factor that considers screen size and aspect ratio. **Best for subtle scaling and the preferred syntax.**                       | Fonts, spacing, padding, icons, base component sizes.                   | `20.fxdp`        |
| **Dynamic** | **Percentage-Based Scaling.** The base value is converted into a percentage of the `BASE_WIDTH_DP` ($300\text{pt}$) and applied to the current screen dimension (smallest or largest). | Main component frames, large visual blocks (e.g., $50%$ of the screen). | `0.5.dyPercent`  |

---

## 💡 2. Quick Syntax Guide

### 2.1. Direct Syntax (Recommended)

Use direct properties on `CGFloat`, `Int`, `Float`, and `Double` for the most common **Fixed** sizing.

| Syntax      | Description                                                                  | Swift Example                 | Return Type             |
| :---------- | :--------------------------------------------------------------------------- | :---------------------------- | :---------------------- |
| **`.fxdp`** | **Fixed** (Weighted Scaling) using aspect ratio (`.withAspectRation(true)`). | `.padding(16.fxdp)`           | `DimensPoint (CGFloat)` |
| **`.sp`**   | **Fixed** (Weighted Scaling) for text/spaced points.                         | `.font(.system(size: 16.sp))` | `DimensPoint (CGFloat)` |
| **`.dydp`** | **Dynamic** (Percentage Scaling) based on the smallest dimension.            | `.frame(height: 100.dydp)`    | `DimensPoint (CGFloat)` |

### 2.2. Chaining Syntax

Chaining syntax gives you access to advanced features like qualifiers, `ScreenType` (`.screen(.highest)`), and fine control over aspect ratio adjustments.

| Syntax                     | Description                                                                        | Swift Example                                                                          |
| :------------------------- | :--------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------- |
| **`.fixed()`**             | Starts a **Fixed** sizing chain.                                                   | `20.fixed().dimension`                                                                 |
| **`.dynamic()`**           | Starts a **Dynamic** sizing chain.                                                 | `100.dynamic().dimension`                                                              |
| **`.dynamicPercentage()`** | Starts a **Dynamic Percentage** sizing chain (0.0 to 1.0).                         | `0.5.dynamicPercentage().dimension`                                                    |
| **`.screen()`**            | Defines whether the calculation uses the screen’s `highest` or `lowest` dimension. | `100.dynamic().screen(.highest).dimension`                                             |
| **`.add()`**               | Adds a **screen qualifier** (`DpQualifier`).                                       | `20.fixed().add(type: .smallWidth, value: 600, customValue: 30).dimension`             |
| **`.add(uiMode:)`**        | Adds a **UI mode qualifier** (device type) with the highest priority.              | `20.fixed().add(uiMode: .macOS, type: .width, value: 1000, customValue: 40).dimension` |

### 2.3. Physical Units (MM, CM, INCH)

Physical units are converted directly to `Points` ($1\text{pt} = 1/72$ inch) and **do not** apply Fixed/Dynamic scaling logic.

| Syntax      | Description                                | Swift Example           |
| :---------- | :----------------------------------------- | :---------------------- |
| **`.mm`**   | Converts millimeters to logical Points/Px. | `.frame(width: 5.mm)`   |
| **`.cm`**   | Converts centimeters to logical Points/Px. | `.frame(height: 2.cm)`  |
| **`.inch`** | Converts inches to logical Points/Px.      | `.frame(width: 1.inch)` |

---

## ⚙️ 3. Utility Functions

### A. Item Count Calculation (`calculateAvailableItemCount`)

This modifier replicates the layout logic from Jetpack Compose to calculate how many items fit inside a container, based on `itemSize` and `itemPadding`.

```swift
// Variable to hold the result (itemCount)
@State private var itemCount: Int = 0

Rectangle()
    .frame(height: 100) 
    .calculateAvailableItemCount(
        itemSize: 50.fxdp,         // Scaled item size
        itemPadding: 8.fxdp,       // Scaled side padding
        direction: .width,         // Measure along the width
        count: $itemCount          // Target variable
    )

Text("Available items: \(itemCount)")
```

### B. Wrapper Functions

Simple helper functions to replicate direct syntax for some dimension systems:

```swift
public extension View {
    // Simulates 'dynamicDp' wrapper
    func dynamicDp(_ base: DimensPoint, @ViewBuilder content: @escaping (DimensPoint) -> some View) -> some View {
        return base.dynamic().dimension(content: content)
    }

    // Simulates 'fixedDp' wrapper
    func fixedDp(_ base: DimensPoint, @ViewBuilder content: @escaping (DimensPoint) -> some View) -> some View {
        return base.fixed().dimension(content: content)
    }
}
```

---

## ⚙️ 4. Essential Setup

For AppDimens to work properly, the **`DimensProvider`** must be injected into the environment to capture window dimensions and pre-calculate scaling factors.

**Golden Rule:** The `DimensProvider` **MUST** be placed at (or close to) the *root* of your view hierarchy.

```swift
@main
struct MainApp: App {
    var body: some Scene {
        WindowGroup {
            DimensProvider { 
                ContentView()
            }
        }
    }
}
```

