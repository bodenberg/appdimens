---
layout: default
title: "🚀 AppDimens: Quick Guide & Library Overview"
---

# 🚀 AppDimens: Quick Guide & Library Overview

> Languages: [Português (BR)](../../LANG/pt-BR/Android/appdimens_all/README.md) | [Español](../../LANG/es/Android/appdimens_all/README.md) | [हिन्दी](../../LANG/hi/Android/appdimens_all/README.md) | [Русский](../../LANG/ru/Android/appdimens_all/README.md) | [中文](../../LANG/zh/Android/appdimens_all/README.md) | [日本語](../../LANG/ja/Android/appdimens_all/README.md)

**AppDimens** is a **dimension management system** for Android (Views and Compose) focused on **mathematical responsiveness**.
It provides refined scaling to ensure that UI elements maintain the correct **proportions** and **visual comfort** on any screen size or **aspect ratio**, from phones to TVs.

---


## 📋 Minimum Requirements

### Required Versions

| Component | Minimum Version | Recommended |
|-----------|----------------|-------------|
| **Kotlin** | 2.2.20 | 2.2.20 |
| **Android Gradle Plugin** | 8.13.0 | 8.13.0 |
| **Gradle** | 8.5 | 8.5 |
| **compileSdk** | 36 | 36 |
| **minSdk** | 21 (Android 5.0) | 23 (Android 6.0) |
| **targetSdk** | 36 | 36 |
| **Java** | 17 | 17 |
| **Jetpack Compose BOM** | 2025.01.00 | 2025.01.00 |

### Build Configuration Example

```kotlin
// build.gradle.kts (Project level)
plugins {
    id("com.android.application") version "8.13.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.20" apply false
}
```

```kotlin
// build.gradle.kts (Module level)
android {
    namespace = "com.example.app"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        targetSdk = 36
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        dataBinding = true
    }
}

dependencies {
    // AppDimens All-in-One
    implementation("io.github.bodenberg:appdimens-all:1.0.8")
    
    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2025.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
}
```

### 16KB Page Size Compatibility

AppDimens All is **fully compatible** with Android's 16KB page size. No special configuration needed.

---

## 1. Core Scaling: Fixed (FX) vs. Dynamic (DY)

The library offers two scaling models that can be used via **Compose Extensions** (`.fxdp`, `.dydp`) or through the **Gateway Object** (`AppDimens.fixedPx`, `AppDimens.dynamicPx`) in Views/XML.

| Feature         | Fixed (FX) – Logarithmic Adjustment                                                                                    | Dynamic (DY) – Percentual Adjustment                                                                                          |
| :-------------- | :--------------------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------------------------------------------------------------------------- |
| **Philosophy**  | **Subtle and Refined**. Uses an **adjustment factor** with a **logarithmic function** (Aspect Ratio) to grow smoothly. | **Aggressive and Proportional**. Calculates the final value while preserving the same **percentage** of the screen dimension. |
| **Best Use**    | Button heights, paddings, icons — anything that should grow **smoothly**.                                              | Container widths, carousels, fonts that must **scale up significantly** on large screens.                                     |
| **Integration** | `.fixed()` / `.fxdp`, `.fxem`, `.fxsp`                                                                                          | `.dynamic()` / `.dydp`, `.dyem`, `.dysp`                                                                                               |
| **Key Feature** | Supports **Aspect Ratio (AR)** sensitivity and **custom sensitivity curves**.                                          | Can **skip scaling** in **Multi-Window** mode to avoid broken layouts.                                                        |

**Example (Compose):**

```kotlin
// Subtle adjustment, ideal for button height
val fixedButtonHeight = 56.fxdp 

// Proportional adjustment, ideal for container width
val dynamicContainerWidth = 100.dydp
```

---

## 2. Gateway & Utilities (Views/XML)

The **`AppDimens`** object acts as a static entry point for the View System and provides essential measurement utilities.

### Main Functions (`AppDimens` Object)

| Function                          | Purpose                                                                                                           | Example                                                              |
| :-------------------------------- | :---------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------- |
| `AppDimens.fixedPx()`             | Returns the adjusted **Fixed** base value converted to **Pixels (PX)**, ready for `layout_width`/`layout_height`. | `AppDimens.fixedPx(16f, ScreenType.LOWEST, resources).toInt()`       |
| `AppDimens.dynamicPx()`           | Returns the adjusted **Dynamic** base value converted to **Pixels (PX)**.                                         | `AppDimens.dynamicPx(100f, ScreenType.LOWEST, resources).toInt()`    |
| `AppDimens.dynamicPercentagePx()` | Returns a **percentage of the screen** (e.g., 80%) in **Pixels (PX)**.                                            | `AppDimens.dynamicPercentagePx(0.80f, ScreenType.LOWEST, resources)` |

### Data Binding Adapters

The library allows you to use custom attributes in XML with Data Binding to automatically apply **Dynamic** scaling:

| XML Attribute           | Applied Conversion                                                |
| :---------------------- | :---------------------------------------------------------------- |
| `app:dynamicWidthDp`    | `DP base` $\xrightarrow{\text{Dynamic}}$ `PX` (width)             |
| `app:dynamicHeightDp`   | `DP base` $\xrightarrow{\text{Dynamic}}$ `PX` (height)            |
| `app:dynamicTextSizeDp` | `DP base` $\xrightarrow{\text{Dynamic}}$ `SP` in `PX` (text size) |

**Example (XML):**

```xml
<View app:dynamicWidthDp="@{48f}" /> 
```

---

## 3. Physical Units (`AppDimensPhysicalUnits`)

The **`AppDimensPhysicalUnits`** object provides tools to convert **physical measurements** (inches, mm, cm) to Android screen units (Dp, Sp, Px).

| Function                        | Source Unit  | Target Unit | Typical Use                                                   |
| :------------------------------ | :----------- | :---------- | :------------------------------------------------------------ |
| `AppDimensPhysicalUnits.toDp()` | INCH, CM, MM | **Dp**      | Define an absolute physical size (e.g., $1\text{cm}$ height). |
| `AppDimensPhysicalUnits.toMm()` | MM           | **Px**      | Convert millimeters to real Pixels.                           |
| `Int.radius()`                  | INCH, CM, MM | **Px**      | Calculate the pixel radius for round devices (e.g., Wear OS). |

**Conversion Example (Views/Kotlin):**

```kotlin
// Set top margin of a View based on 5 millimeters
val marginPx = AppDimensPhysicalUnits.toMm(5.0f, resources).toInt()
myView.layoutParams.topMargin = marginPx 
```

---

## 4. Layout Utility: `calculateAvailableItemCount`

This function is essential for layouts like **RecyclerViews** and **LazyGrids**.
It calculates how many items of a given **Dp size** fit inside a container (View or Composable).

| Environment   | Usage                                        | Key Parameters                                               |
| :------------ | :------------------------------------------- | :----------------------------------------------------------- |
| **Views/XML** | `AppDimens.calculateAvailableItemCount(...)` | `containerSizePx` (real width), `itemSizeDp`, `itemMarginDp` |
| **Compose**   | `AppDimens.CalculateAvailableItemCount(...)` | `itemSize: Dp`, `itemPadding: Dp`, `onResult: (Int) -> Unit` |

**Example (Views/Kotlin):**

```kotlin
// Determine the number of columns for a GridLayoutManager
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width, // Width in PX
    itemSizeDp = 100f,
    itemMarginDp = 8f, // 8dp total padding
    resources = resources
)
// layoutManager = GridLayoutManager(context, spanCount)
```

---

✅ **Summary:**

* **Fixed (FX)** for subtle, controlled scaling.
* **Dynamic (DY)** for proportional scaling on larger displays.
* Works in **Compose**, **Views**, and **Data Binding**.
* Includes **physical unit conversion** and **layout calculation** utilities.
* Ideal for truly **responsive and adaptive UIs** on Android.

