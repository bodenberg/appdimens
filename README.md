# 📐 AppDimens — Smart and Responsive Dimensioning for Android

**AppDimens** is a library that provides **mathematically responsive scaling**, ensuring your UI design adapts perfectly to any screen size or aspect ratio — from **phones** to **TVs**, **cars**, and **wearables**.
It works with **Jetpack Compose**, **XML Views**, and **Data Binding**.

---

## 🚀 Installation

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
<div align="center">
    <img src="IMAGES/image_sample_devices.png" alt="sample" height="250"/>
</div>

---

## 🧠 Core Dimension Models

| Model              | Philosophy                        | Ideal Use Case                        | Supported In                   |
| ------------------ | --------------------------------- | ------------------------------------- | ------------------------------ |
| **Fixed (FX)**     | Logarithmic scaling (refined)     | Buttons, paddings, margins, icons     | Compose + Views + Data Binding |
| **Dynamic (DY)**   | Proportional scaling (aggressive) | Containers, grids, fluid fonts        | Compose + Views + Data Binding |
| **SDP / SSP**      | Pre-calculated resources          | Compose + XML (direct `@dimen`)       | Compose + Views (XML)          |
| **Physical Units** | mm/cm/inch → Dp/Sp/Px             | Wearables, printing, physical layouts | Compose + Views                |

👉 [Learn more about FX and DY](#)
👉 [Learn more about SDP and SSP](#)

---

## 🧩 Jetpack Compose — Examples

### 📏 Fixed and Dynamic (FX / DY)

```kotlin
val fixedPadding = 16.fxdp               // subtle and refined
val dynamicWidth = 100.dydp              // proportional to screen
val dynamicText = 18.dysp                // proportional text
val fixedEm = 1.2.fxem                   // 'em' scaling (fixed)
val dynamicEm = 1.0.dyem                 // 'em' scaling (dynamic)
```

📝 `.fxdp`, `.fxsp`, `.fxem` use logarithmic scaling
📝 `.dydp`, `.dysp`, `.dyem` use proportional scaling

👉 [More Compose examples](#)

---

### 🧮 SDP and SSP in Compose

```kotlin
val padding = 16.sdp
val textSize = 18.ssp

Box(
    modifier = Modifier
        .padding(padding)
        .size(100.sdp)
) {
    Text(
        text = "Responsive Text",
        fontSize = textSize
    )
}
```

✅ `sdp` and `ssp` can be used directly inside Composables.
👉 [More SDP/SSP Compose examples](#)

---

## 🖼️ XML Views and Data Binding

### 🌐 Dynamic (FX / DY)

Dynamic works both in **Compose** and in **Java/Kotlin**, including **Data Binding**.

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    app:dynamicWidthDp="@{100f}"
    app:dynamicHeightDp="@{56f}"
    app:dynamicTextSizeDp="@{20f}">
</LinearLayout>
```

```kotlin
val widthPx = AppDimens.dynamicPx(100f, ScreenType.LOWEST, resources)
myView.layoutParams.width = widthPx.toInt()
```

👉 [More Dynamic + Views examples](#)

---

### 🧭 SDP and SSP in XML

`SDP` and `SSP` can be used directly in XML layouts, since they rely on pre-generated `dimens.xml` resources.

```xml
<TextView
    android:layout_width="@dimen/_49sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp"
    android:text="Responsive Text Example"/>
```

✅ Perfect for projects that want responsiveness without changing business logic.
👉 [More XML usage examples](#)

---

## 📏 Physical Units (mm, cm, inch)

```kotlin
val marginPx = AppDimensPhysicalUnits.toMm(5f, resources)
view.setPadding(marginPx.toInt(), 0, 0, 0)
```

* `toMm()` / `toCm()` / `toInch()` → Px
* Useful for Wear OS, precision layouts, and print-like designs.

👉 [More about Physical Units](#)

---

## 🧮 Dynamic Layout Utility

```kotlin
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

👉 Automatically calculates the **optimal column count** for grids or RecyclerViews.

👉 [More on layout utilities](#)

---

## 🧭 Conditional Rules (Advanced FIXED/DYNAMIC/SDP/SSP)

```kotlin
val boxSize = 80.dynamicDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)

val titleSize = 24.scaledSp()
    .screen(UiModeType.CAR, DpQualifier.SMALL_WIDTH, 720, 48.sp)
    .screen(DpQualifier.SMALL_WIDTH, 600, 32.sp)
```

✅ `scaledDp()` and `scaledSp()` let you adapt values based on UI Mode and screen qualifiers.
👉 [More about conditional rules](#)

---

## 📚 Compatibility

| Feature               | Compose |     XML Views    | Data Binding | Notes                                |
| --------------------- | :-----: | :--------------: | :----------: | ------------------------------------ |
| **Dynamic (FX/DY)**   |    ✅    | ✅ (via resource) |       ✅      | `.fxdp` / `.dydp` / AppDimens object |
| **SDP/SSP**           |    ✅    |         ✅        |       ❌      | Based on pre-generated `@dimen`      |
| **Physical Units**    |    ✅    |         ✅        |       ✅      | Real unit conversions                |
| **Conditional Rules** |    ✅    | ✅ (via resource) |       ✅      | Advanced responsive logic            |

---

## 📎 Useful Links

* 📘 [Full Documentation](#)
* 🧮 [Compose Examples](#)
* 🧰 [View System Examples](#)
* 🧭 [Advanced SDP/SSP](#)
* 📏 [Physical Units Conversion](#)

---

