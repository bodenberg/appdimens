Here’s the English translation of your document:

---

# COMPOSE

## 📚 AppDimens Core Documentation (COMPOSE)

The core of **AppDimens** provides the intelligence behind the scaling system, offering three main APIs:

1. **`AppDimensDynamic`**: Advanced adaptive scaling (percentage-based, ignores multi-window mode).
2. **`AppDimensFixed`**: Responsive scaling with Aspect Ratio (logarithmic).
3. **`AppDimensPhysicalUnits`**: Conversion of physical units (inches, mm, cm) to Dp, Sp, and Px.

---

## 1. 🌐 AppDimensDynamic: Adaptive Percentage Scaling

`AppDimensDynamic` applies a "percentage-based" scaling, where the base DP value is proportionally adapted to the screen dimension (Width or Height), similar to a percentage.

### 📌 Core Concept

Instead of using incremental adjustment factors, `AppDimensDynamic` calculates:

$$\text{Final Value} = \text{Screen Dimension (W or H)} \times \left( \frac{\text{Base DP}}{\text{Reference Width (360dp)}} \right)$$

This ensures that a $100\text{dp}$ element on a $360\text{dp}$ screen occupies the same proportion on a $720\text{dp}$ screen.

### Key Methods and Getters

The `Dynamic` class is initialized with a value and a screen type, determining whether scaling will use the **highest** (`HIGHEST`) or **lowest** (`LOWEST`) device dimension.

| Initialization                   | Description                                                                                                            |
| :------------------------------- | :--------------------------------------------------------------------------------------------------------------------- |
| `Int.dynamic(ScreenType.LOWEST)` | Starts the chain using the base DP and the **smallest** screen dimension (Android default) for percentage calculation. |
| `Dp.dynamic(ScreenType.HIGHEST)` | Starts the chain using the base DP and the **largest** screen dimension for percentage calculation.                    |

#### Resolution Getters (`@Composable`)

After initialization, resolve the value in Compose context:

| Getter    | Final Unit Type                                |
| :-------- | :--------------------------------------------- |
| **`.dp`** | Returns the final value in **Dp**.             |
| **`.sp`** | Returns the final value in **TextUnit (Sp)**.  |
| **`.em`** | Returns the final value in **TextUnit (Em)**.  |
| **`.px`** | Returns the final value in **Float (Pixels)**. |

### Exclusive Feature: Ignore Multi-Window Adjustment

Dynamic Adjustment has a key parameter for stability:

| Function        | Parameter                            | Description                                                                                                                                                                                                                                                    |
| :-------------- | :----------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `.dynamic(...)` | `ignoreMultiViewAdjustment: Boolean` | **Default is `true`**. In Multi-Window mode (split-screen or pop-up) where `smallestWidthDp` differs significantly from current width, dynamic scaling is **ignored** and the original value is used. Prevents elements from being too large in small windows. |

#### Usage Example:

```kotlin
@Composable
fun DynamicSizing() {
    val dynamicWidth = 100.dynamic(ScreenType.LOWEST).dp
    val dynamicPadding = 16.dynamic(ScreenType.LOWEST).ignoreMultiViewAdjustment(true).dp

    Box(
        modifier = Modifier
            .width(dynamicWidth)
            .padding(dynamicPadding)
    )
}
```

---

## 2. 🎯 AppDimensFixed: Fixed Scaling with Aspect Ratio

`AppDimensFixed` applies responsive scaling based on **pre-calculated adjustment factors**, using **Aspect Ratio (AR)** and logarithmic scaling for subtle and mathematically refined scaling.

### 📌 Core Concept

Final scaling is a function of the **Adjustment Factor** (based on `smallestWidthDp` or largest dimension) plus a **Final Increment** adjusted by Aspect Ratio.

$$\text{Final Value} = \text{Base DP} \times \text{Final Adjustment Factor}$$

### Key Methods and Getters

`Fixed` is initialized with a value and a screen type, selecting which base adjustment factor to use (`LOWEST` or `HIGHEST`).

| Initialization                 | Description                                                                                          |
| :----------------------------- | :--------------------------------------------------------------------------------------------------- |
| `Int.fixed(ScreenType.LOWEST)` | Starts the chain using the base DP and adjustment factor calculated from the **smallest** dimension. |
| `Dp.fixed(ScreenType.HIGHEST)` | Starts the chain using the base DP and adjustment factor calculated from the **largest** dimension.  |

#### Fine-Tuning Parameters

| Method                     | Parameter              | Description                                                                          |
| :------------------------- | :--------------------- | :----------------------------------------------------------------------------------- |
| `.withAspectRatio()`       | `isEnabled: Boolean`   | **Default is `true`**. Enables logarithmic fine-tuning based on screen Aspect Ratio. |
| `.withCustomSensitivity()` | `sensitivityK: Double` | Allows setting a custom **$K$** factor for AR logarithmic adjustment.                |

#### Resolution Getters (`@Composable`)

| Getter    | Final Unit Type                                |
| :-------- | :--------------------------------------------- |
| **`.dp`** | Returns the final value in **Dp**.             |
| **`.sp`** | Returns the final value in **TextUnit (Sp)**.  |
| **`.em`** | Returns the final value in **TextUnit (Em)**.  |
| **`.px`** | Returns the final value in **Float (Pixels)**. |

#### Usage Example:

```kotlin
@Composable
fun FixedSizing() {
    val defaultHeight = 50.fixed(ScreenType.LOWEST).dp
    val noArPadding = 16.fixed(ScreenType.HIGHEST).withAspectRatio(false).dp
    val customArText = 20.fixed(ScreenType.LOWEST).withCustomSensitivity(0.5).sp

    Column(
        modifier = Modifier
            .height(defaultHeight)
            .padding(noArPadding)
    ) {
        Text(
            text = "Custom AR Text",
            fontSize = customArText
        )
    }
}
```

---

## 3. 📏 AppDimensPhysicalUnits: Physical Unit Conversion

`AppDimensPhysicalUnits` provides utilities to convert physical units (inch, cm, mm) to Android screen units (Dp, Sp, Px), along with geometry helpers.

### Conversion Methods

Extensions convert a `Float` or `Int` (physical measure) to the desired screen unit:

| Method                     | Source Unit  | Target Unit | Description                             |
| :------------------------- | :----------- | :---------- | :-------------------------------------- |
| `Int/Float.toDp(UnitType)` | INCH, CM, MM | **Dp**      | Converts to density-independent pixels. |
| `Int/Float.toSp(UnitType)` | INCH, CM, MM | **Sp**      | Converts to scale-independent pixels.   |
| `Int/Float.toPx(UnitType)` | INCH, CM, MM | **Px**      | Converts to real screen pixels.         |

**Supported Units (`UnitType`)**: `INCH`, `CM`, `MM`.

#### Conversion Example:

```kotlin
@Composable
fun PhysicalConversion() {
    val oneInchInDp: Dp = 1.0f.toDp(UnitType.INCH)
    val fiveMmInSp: TextUnit = 5.toSp(UnitType.MM)

    Box(
        modifier = Modifier.size(oneInchInDp)
    ) {
        Text(
            text = "5mm in SP",
            fontSize = fiveMmInSp
        )
    }
}
```

### Geometry Utilities

| Method                                                | Description                                                                                          |
| :---------------------------------------------------- | :--------------------------------------------------------------------------------------------------- |
| `Int/Float.radius(UnitType)`                          | Calculates the **radius** of a spherical device (e.g., watch) in **Px**, based on its physical size. |
| `Int/Float.measureDiameter(isCircumference: Boolean)` | Adjusts a physical measure to reflect **diameter** or **circumference**.                             |

#### Example (Wear OS):

```kotlin
@Composable
fun WearableRadius() {
    val watchDiameterMm = 42
    val radiusPx = watchDiameterMm.radius(UnitType.MM)
    val diameterMeasure = 42.measureDiameter(false)
}
```

---

# 🎯 AppDimens Scaling: Fixed (FX) vs. Dynamic (DY)

Extensions `fxdp` and `dydp` are shortcuts for `Int.fixed()` and `Int.dynamic()`.

|   Category  | Compose Base Method | Scaling Philosophy                                                                  |
| :---------: | :------------------ | :---------------------------------------------------------------------------------- |
|  **Fixed**  | `Int.fixed(...)`    | **Logarithmic/Incremental Adjustment** with AR. Ideal for subtle component scaling. |
| **Dynamic** |`Int.dynamic(...)`  | **Percentage/Proportional Scaling**. Maintains proportion on extreme screens.       |

### Fixed Dimensions (FX)

| Extension | Final Unit | Base Calculation                  |
| :-------- | :--------- | :-------------------------------- |
| `.fxdp`   | `Dp`       | `Int.fixed(ScreenType.LOWEST).dp` |
| `.fxsp`   | `Sp`       | `Int.fixed(ScreenType.LOWEST).sp` |
| `.fxem`   | `Em`       | `Int.fixed(ScreenType.LOWEST).em` |
| `.fxpx`   | `Px`       | `Int.fixed(ScreenType.LOWEST).px` |

### Dynamic Dimensions (DY)

| Extension | Final Unit | Base Calculation                    |
| :-------- | :--------- | :---------------------------------- |
| `.dydp`   | `Dp`       | `Int.dynamic(ScreenType.LOWEST).dp` |
| `.dysp`   | `Sp`       | `Int.dynamic(ScreenType.LOWEST).sp` |
| `.dyem`   | `Em`       | `Int.dynamic(ScreenType.LOWEST).em` |
| `.dypx`   | `Px`       | `Int.dynamic(ScreenType.LOWEST).px` |

**Additional Feature:** Dynamic ignores scaling in Multi-Window by default.

#### Dynamic Example:

```kotlin
val contentPadding = 24.dydp
val proportionalText = 18.dysp
```

### Key Differences

| Feature                   | Fixed (FX)                                   | Dynamic (DY)                                          |
| :------------------------ | :------------------------------------------- | :---------------------------------------------------- |
| **Main Calculation**      | Logarithmic Adjustment + AR                  | Percentage Proportional                               |
| **Growth Aggressiveness** | **Subtle & Refined**                         | **Aggressive & Proportional**                         |
| **Ideal Use**             | Button heights, internal padding, icon sizes | Container width/height, viewport-dependent dimensions |
| **Multi-Window**          | Not implemented by default, can be enabled   | **Ignored by default** to prevent layout breakage     |

---

# 📐 Utility: `calculateAvailableItemCount`

Calculates how many items of a given size (`itemSizeDp`) and spacing (`itemPaddingDp`) fit side by side or stacked in a container of known Pixels (`containerSizePx`).

```kotlin
@Composable
fun CalculateAvailableItemCount(
    itemSize: Dp,
    itemPadding: Dp,
    direction: DpQualifier = DpQualifier.WIDTH,
    modifier: Modifier = Modifier.fillMaxSize(),
    onResult: (count: Int) -> Unit
)
```

**Compose Example:**

```kotlin
AppDimens.CalculateAvailableItemCount(
    itemSize = 100.dp,
    itemPadding = 4.dp,
    direction = DpQualifier.WIDTH,
    modifier = Modifier.fillMaxWidth(),
    onResult = { count -> spanCount = count }
)
```

---

# KOTLIN / JAVA - CODE

## AppDimens Core Object

Provides gateway utilities for XML Views and code-based dimension calculations (Fixed, Dynamic, Physical Units).

### Fixed Scaling Functions (Logarithmic/AR)

| Function                                | Return Unit | Description         |
| :-------------------------------------- | :---------- | :------------------ |
| `fixedDp(value, screenType, resources)` | DP          | Adjusted DP         |
| `fixedPx(value, screenType, resources)` | PX          | Converted to Pixels |
| `fixedSp(value, screenType, resources)` | PX          | Converted to SP     |
| `fixedEm(value, screenType, resources)` | PX          | Converted to EM     |

### Dynamic Scaling Functions (Percentage)

| Function                                  | Return Unit | Description         |
| :---------------------------------------- | :---------- | :------------------ |
| `dynamicDp(value, screenType, resources)` | DP          | Adjusted DP         |
| `dynamicPx(value, screenType, resources)` | PX          | Converted to Pixels |
| `dynamicSp(value, screenType, resources)` | PX          | Converted to SP     |
| `dynamicEm(value, screenType, resources)` | PX          | Converted to EM     |

### Physical Unit Conversion

| Function                       | Source Unit  | Return Unit | Description                              |
| :----------------------------- | :----------- | :---------- | :--------------------------------------- |
| `toDp(value, type, resources)` | INCH, CM, MM | DP          | Converts physical measure to DP          |
| `toSp(value, type, resources)` | INCH, CM, MM | PX          | Converts physical measure to SP (Pixels) |
| `toPx(value, type, resources)` | INCH, CM, MM | PX          | Converts physical measure to Pixels      |

### Layout Utility: `calculateAvailableItemCount`

Determines how many items fit in a container given item size and padding.

**RecyclerView/GridView Example:**

```kotlin
val containerWidthPx = resources.displayMetrics.widthPixels
val cardSizeDp = 120f
val cardPaddingDp = 8f

val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = containerWidthPx,
    itemSizeDp = cardSizeDp,
    itemMarginDp = cardPaddingDp * 2,
    resources = resources
)
```

### Data Binding Integration

`BindingAdapters` allow XML attributes (e.g., `app:dynamicWidthDp`) to apply dynamic scaling at runtime.

**XML Example:**

```xml
<View
    android:id="@+id/dynamic_db_view"
    app:dynamicWidthDp="@{dimenValue}"
    app:dynamicHeightDp="@{24f}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"/>
```

**Kotlin Example:**

```kotlin
val adjustedDynamicPx = AppDimens.dynamic(48f).toPx(resources).toInt()
binding.dynamicView.layoutParams.width = adjustedDynamicPx
```

**Dynamic Percentage Example:**

```kotlin
val percentagePx = AppDimens.dynamicPercentagePx(0.80f, ScreenType.LOWEST, resources)
binding.percentageView.layoutParams.width = percentagePx.toInt()
```

**Physical Units Example:**

```kotlin
val mmValue = 5.0f
val mmInPx = AppDimensPhysicalUnits.toMm(mmValue, resources)
layoutParams.topMargin = mmInPx.toInt()
```

---

This English version keeps all technical details, code samples, and Compose/XML integration notes intact.

---

If you want, I can also produce a **compact GitHub-ready README** in English using this translation, including quick-start examples for Compose and XML. Do you want me to do that?

