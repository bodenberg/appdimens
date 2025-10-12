# COMPOSE

## 📚 AppDimens Core Documentation (COMPOSE)

The **AppDimens** core provides the intelligence behind the scaling system, offering three main APIs:

1.  **`AppDimensDynamic`**: Advanced adaptive scaling (percentage-based, ignores multi-window).
2.  **`AppDimensFixed`**: Responsive scaling with Aspect Ratio (logarithmic).
3.  **`AppDimensPhysicalUnits`**: Conversion of physical units (inches, mm, cm) to Dp, Sp, and Px.

-----

## 1\. 🌐 AppDimensDynamic: Adaptive Percentage Sizing

`AppDimensDynamic` applies a type of "percentage" sizing, where the base DP value is adapted proportionally to the screen dimension (Width or Height), similar to a percentage.

### 📌 Core Concept

Instead of using incremental adjustment factors, `AppDimensDynamic` calculates:

$$\text{Final Value} = \text{Screen Dimension (W or H)} \times \left( \frac{\text{Base DP}}{\text{Ref. Width (360dp)}} \right)$$

This ensures that a $100\text{dp}$ element on a $360\text{dp}$ screen occupies the same proportion on a $720\text{dp}$ screen.

### Key Methods and Getters

The `Dynamic` class is initialized with a value and a screen type, determining whether the scaling will be based on the **highest** (`HIGHEST`) or **lowest** (`LOWEST`) dimension of the device.

| Initialization | Description |
| :--- | :--- |
| `Int.dynamic(ScreenType.LOWEST)` | Starts the chain, using the base DP and the **lowest** screen dimension (Android default) for the percentage calculation. |
| `Dp.dynamic(ScreenType.HIGHEST)` | Starts the chain, using the base DP and the **highest** screen dimension for the percentage calculation. |

#### Resolution Getters (`@Composable`)

After initialization, resolve the value within the Compose context:

| Getter | Final Unit Type |
| :--- | :--- |
| **`.dp`** | Returns the final value in **Dp**. |
| **`.sp`** | Returns the final value in **TextUnit (Sp)**. |
| **`.em`** | Returns the final value in **TextUnit (Em)**. |
| **`.px`** | Returns the final value in **Float (Pixels)**. |

### Exclusive Feature: Ignore Multi-Window Adjustment

The Dynamic Adjustment has a crucial parameter for stability:

| Function | Parameter | Description |
| :--- | :--- | :--- |
| `.dynamic(...)` | `ignoreMultiViewAdjustment: Boolean` | **Default is `true`**. If in Multi-Window mode (split-screen or Pop-up) and the `smallestWidthDp` is vastly different from the current width, the dynamic scaling is **ignored**, and the original value is used. This prevents elements from becoming oversized in small windows. |

#### Usage Example:

```kotlin
@Composable
fun DynamicSizing() {
    // Calculates a Box that will always occupy the same proportion of the screen width.
    val dynamicWidth = 100.dynamic(ScreenType.LOWEST).dp 

    // The padding is scaled, but it will IGNORE scaling if in Multi-Window mode.
    val dynamicPadding = 16.dynamic(ScreenType.LOWEST).ignoreMultiViewAdjustment(true).dp 

    Box(
        modifier = Modifier
            .width(dynamicWidth)
            .padding(dynamicPadding)
    )
}
```

-----

## 2\. 🎯 AppDimensFixed: Fixed Sizing with Aspect Ratio

`AppDimensFixed` applies responsive sizing based on the calculation of pre-calculated **Adjustment Factors**. This is the method that uses **Aspect Ratio (AR)** and logarithmic adjustment for a more subtle and mathematically refined sizing.

### 📌 Core Concept

The dimension adjustment is a function of the **Adjustment Factor** (which is based on `smallestWidthDp` or the highest dimension) plus a **Final Increment** adjusted by the Aspect Ratio.

$$\text{Final Value} = \text{Base DP} \times \text{Final Adjustment Factor}$$

### Key Methods and Getters

The `Fixed` class is initialized with a value and a screen type, determining which base adjustment factor should be used (`LOWEST` or `HIGHEST`).

| Initialization | Description |
| :--- | :--- |
| `Int.fixed(ScreenType.LOWEST)` | Starts the chain, using the base DP and the adjustment factor calculated with the **lowest** dimension. |
| `Dp.fixed(ScreenType.HIGHEST)` | Starts the chain, using the base DP and the adjustment factor calculated with the **highest** dimension. |

#### Fine-Tuning Parameters

You can customize the scaling behavior:

| Method | Parameter | Description |
| :--- | :--- | :--- |
| `.withAspectRatio()` | `isEnabled: Boolean` | **Default is `true`**. Activates the logarithmic fine-tuning based on the screen's Aspect Ratio. |
| `.withCustomSensitivity()` | `sensitivityK: Double` | Allows defining a custom **$K$** factor for the logarithmic Aspect Ratio adjustment, controlling the aggressiveness of the adjustment. |

#### Resolution Getters (`@Composable`)

| Getter | Final Unit Type |
| :--- | :--- |
| **`.dp`** | Returns the final value in **Dp**. |
| **`.sp`** | Returns the final value in **TextUnit (Sp)**. |
| **`.em`** | Returns the final value in **TextUnit (Em)**. |
| **`.px`** | Returns the final value in **Float (Pixels)**. |

#### Usage Example:

```kotlin
@Composable
fun FixedSizing() {
    // Default scaling (LOWEST + AR enabled)
    val defaultHeight = 50.fixed(ScreenType.LOWEST).dp

    // Scaling based on the highest dimension, without AR adjustment (AR disabled)
    val noArPadding = 16.fixed(ScreenType.HIGHEST).withAspectRatio(false).dp 

    // Scaling with CUSTOM AR sensitivity
    val customArText = 20.fixed(ScreenType.LOWEST).withCustomSensitivity(sensitivityK = 0.5).sp

    Column(
        modifier = Modifier
            .height(defaultHeight)
            .padding(noArPadding)
    ) {
        Text(
            text = "Text with custom AR",
            fontSize = customArText
        )
    }
}
```

-----

## 3\. 📏 AppDimensPhysicalUnits: Physical Units Conversion

`AppDimensPhysicalUnits` provides utilities to convert physical measurement units (Inch, Centimeter, Millimeter) to Android screen units (Dp, Sp, Px), in addition to geometry utilities.

### Conversion Methods

The extension functions allow converting a `Float` or `Int` (representing the physical measurement) to the desired screen unit:

| Method | Origin Unit | Destination Unit | Description |
| :--- | :--- | :--- | :--- |
| `Int/Float.toDp(UnitType)` | INCH, CM, MM | **Dp** | Converts to Logical Pixels (Density-independent Pixels). |
| `Int/Float.toSp(UnitType)` | INCH, CM, MM | **Sp** | Converts to Scale Pixels (Scale-independent Pixels). |
| `Int/Float.toPx(UnitType)` | INCH, CM, MM | **Px** | Converts to Real Screen Pixels. |

**Supported Units (`UnitType`)**: `INCH`, `CM`, `MM`.

#### Conversion Example:

```kotlin
@Composable
fun PhysicalConversion() {
    // 1 Inch converted to Dp
    val oneInchInDp: Dp = 1.0f.toDp(UnitType.INCH) 

    // 5 Millimeters converted to TextUnit (Sp)
    val fiveMmInSp: TextUnit = 5.toSp(UnitType.MM)

    Box(
        modifier = Modifier.size(oneInchInDp) // 1 inch in Dp
    ) {
        Text(
            text = "5mm in SP",
            fontSize = fiveMmInSp // 5 millimeters in Sp
        )
    }
}
```

### Geometry and Measurement Utilities

| Method | Description |
| :--- | :--- |
| `Int/Float.radius(UnitType)` | Calculates the **Radius** of a spherical device (like a watch) in **Pixels (Px)**, based on its physical measurement (e.g., $38\text{mm}$ diameter). |
| `Int/Float.measureDiameter(isCircumference: Boolean)` | Adjusts a physical measurement (e.g., of a watch) to reflect the **diameter** or **circumference**. |

#### Geometry Example (Wear OS):

```kotlin
@Composable
fun WearableRadius() {
    // For a watch with 42 millimeters in diameter, calculate the radius in Px
    val watchDiameterMm = 42
    
    // Get the Radius in Px.
    val radiusPx = watchDiameterMm.radius(UnitType.MM)
    
    // The 42mm measurement represents the diameter (isCircumference = false)
    val diameterMeasure = 42.measureDiameter(isCircumference = false) 
    
    // ... use radiusPx to draw a circle on the Canvas
    // ... use diameterMeasure for subsequent calculations
}
```

# 🎯 AppDimens Sizing: Fixed (FX) vs. Dynamic (DY)

The `fxdp` and `dydp` extensions are simplified shortcuts to initialize the `Int.fixed()` and `Int.dynamic()` methods, respectively, in dimension files, but with a specific naming convention for **XML Views**.

| Category | Base Method (Compose) | Scaling Philosophy |
| :---: | :--- | :--- |
| **Fixed** | `Int.fixed(...)` | **Logarithmic/Incremental Adjustment** with *Aspect Ratio*. Ideal for subtle component adjustment. |
| **Dynamic** | `Int.dynamic(...)` | **Percentage/Proportional Adjustment**. Ideal for maintaining proportion on extreme screens. |

-----

## 1\. 📏 Fixed Dimensions (FX): `fxdp`, `fxsp`, `fxpx`

Fixed sizing (`fx`) is the main system of **AppDimens** for **responsive yet subtle** scale adjustment. It relies on pre-calculated adjustment factors that take into account the difference between the screen dimension and a reference width (usually $360\text{dp}$).

### Philosophy (Logarithmic Adjustment)

Instead of a simple multiplication factor, Fixed uses:

1.  **Base Adjustment Factor:** Calculated from the screen's $\text{smallestWidthDp}$.
2.  **Aspect Ratio (AR) Adjustment:** Applies a **logarithmic** function to the increment to smooth the adjustment on screens with extreme proportions (e.g., very wide TVs).

This model is ideal for maintaining a consistent **design feel**, allowing dimensions to grow or shrink in a controlled and refined manner.

### Compose Extensions

| Extension (Int) | Final Unit | Base Calculation Method |
| :--- | :--- | :--- |
| **`.fxdp`** | `Dp` | $\text{Int.fixed(ScreenType.LOWEST).dp}$ |
| **`.fxsp`** | `TextUnit (Sp)` | $\text{Int.fixed(ScreenType.LOWEST).sp}$ |
| **`.fxem`** | `TextUnit (Em)` | $\text{Int.fixed(ScreenType.LOWEST).em}$ |
| **`.fxpx`** | `Float (Px)` | $\text{Int.fixed(ScreenType.LOWEST).px}$ |

**Example:**

```kotlin
// Uses the logarithmic adjustment of AppDimens for size and font.
val buttonHeight = 56.fxdp
val textSize = 18.fxsp
```

-----

## 2\. 🚀 Dynamic Dimensions (DY): `dydp`, `dysp`, `dypx`

Dynamic sizing (`dy`) applies a **purely proportional** (or percentage) scaling based on the screen dimension (Width or Height).

### Philosophy (Percentage/Proportional Adjustment)

Dynamic calculates the final value by maintaining the same **proportion of the reference dimension**.

$$\text{Final Value} = \text{Screen Dimension (W or H)} \times \left( \frac{\text{Base DP}}{\text{Ref. Width (360dp)}} \right)$$

This model is ideal for components that need to fill a **constant proportion** of the screen, such as a carousel that should be $1/3$ of the total width, or to ensure that on very large screens, the element **grows aggressively** to fill the space.

### Compose Extensions

| Extension (Int) | Final Unit | Base Calculation Method |
| :--- | :--- | :--- |
| **`.dydp`** | `Dp` | $\text{Int.dynamic(ScreenType.LOWEST).dp}$ |
| **`.dysp`** | `TextUnit (Sp)` | $\text{Int.dynamic(ScreenType.LOWEST).sp}$ |
| **`.dyem`** | `TextUnit (Em)` | $\text{Int.dynamic(ScreenType.LOWEST).em}$ |
| **`.dypx`** | `Float (Px)` | $\text{Int.dynamic(ScreenType.LOWEST).px}$ |

**Additional Feature:** By default, Dynamic **ignores** scaling in Multi-Window mode to prevent over-sizing in small windows.

**Example:**

```kotlin
// The padding will be proportional to the screen width.
val contentPadding = 24.dydp

// The text will be proportional to the screen width.
val proportionalText = 18.dysp
```

-----

## 🔑 Summary and Key Difference

| Feature | Fixed (FX) | Dynamic (DY) |
| :--- | :--- | :--- |
| **Primary Calculation** | Logarithmic Adjustment Factor + AR | Percentage Proportion |
| **Growth Aggressiveness** | **Subtle and Refined** | **Aggressive and Proportional** |
| **Ideal Use** | Button heights, internal paddings, icon sizes. | Container Width/Height, dimensions dependent on viewport proportion. |
| **Multi-Window** | Not implemented in the shortcut, but can be activated in `fixed()`. | **Ignores scaling** by default to prevent the UI from breaking in small windows. |

-----

Certainly\! The `calculateAvailableItemCount` function is an essential utility for creating responsive layouts based on the number of columns/rows that fit on the screen, especially in **Views (XML)**.

I will detail the function's operation and usage, including its Compose version, as found in the `AppDimens.kt` file.

-----

# 📐 Utility: `calculateAvailableItemCount`

The `calculateAvailableItemCount` function calculates how many items of a given size (`itemSizeDp`) and spacing (`itemPaddingDp`) can be laid out side-by-side or on top of each other within a container with a known dimension in Pixels (`containerSizePx`).

### Composable Signature

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

### Parameter Details (Compose)

| Parameter | Type | Description |
| :--- | :--- | :--- |
| **`itemSize`** | `Dp` | The size (Width or Height) of an item in **Dp**. |
| **`itemPadding`** | `Dp` | The **total padding (in Dp)** on one side (e.g., if it's `4.dp`, the function considers `8.dp` of total space for the item). |
| **`direction`** | `DpQualifier` | The direction to be measured: `WIDTH`, `HEIGHT`, or `SMALL_WIDTH` (smallest dimension). **Default is `WIDTH`**. |
| **`modifier`** | `Modifier` | The `Modifier` that defines the container to be measured. |
| **`onResult`** | `(count: Int) -> Unit` | **Callback** that returns the calculated item count. |

### Practical Usage Example (Compose)

This example uses the function inside a `LazyRow` to dynamically define the number of items that fit.

```kotlin
@Composable
fun ItemGridScreen() {
    var spanCount by remember { mutableStateOf(3) }
    
    // 1. Use the Composable to measure the space and calculate the number of columns
    AppDimens.CalculateAvailableItemCount(
        itemSize = 100.dp, // Base width of the item
        itemPadding = 4.dp, // Padding of 4.dp (total of 8.dp horizontal)
        direction = DpQualifier.WIDTH,
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        onResult = { count ->
            // 2. Update the item count (spanCount) with the result
            if (count > 0) {
                spanCount = count
            }
        }
    )

    // 3. Display the items in the Layout
    LazyVerticalGrid(
        columns = GridCells.Fixed(spanCount), // Uses the calculated value
        contentPadding = PaddingValues(16.dp)
    ) {
        items(20) { index ->
            // Your item
        }
    }
}
```

-----

# KOTLIN, JAVA - CODE

## 🚪 `AppDimens` Core Object: Gateway and Layout Utilities

The **`AppDimens`** object is the static entry point for sizing in the **View System** (XML) and provides practical utility methods that are not tied to Compose. It exposes the ability to calculate *Fixed* and *Dynamic* dimensions and convert *Physical Units* directly in Kotlin/Java code using the `Resources` object.

## 1\. ⚙️ Sizing Gateway Functions (Views/XML)

These functions receive a **base DP** value and the `Resources` object from your `Context` and return the final adjusted value in **Float** or **Int** (already converted to Pixels, Px).

### **FIXED** Sizing (Logarithmic/AR)

`Fixed` is the best choice for **subtle and refined adjustment** that considers the screen's Aspect Ratio.

| Function | Return Unit | Description |
| :--- | :--- | :--- |
| `fixedDp(value, screenType, resources)` | **Float (DP)** | Returns the adjusted value in DP (not converted to Px). |
| `fixedPx(value, screenType, resources)` | **Float (PX)** | Returns the adjusted value in DP, *converted to real Pixels* (best for `layout_width`/`layout_height`). |
| `fixedSp(value, screenType, resources)` | **Float (PX)** | Returns the adjusted value in DP, *converted to Scale Pixels (SP)*. |
| `fixedEm(value, screenType, resources)` | **Float (PX)** | Returns the adjusted value in DP, *converted to Scale Pixels (EM)*. |

**Usage Example in Kotlin Code (Views/XML):**

```kotlin
// In an Activity/Fragment:
val resources = context.resources
val adjustedWidthPx = AppDimens.fixedPx(
    value = 200f,
    screenType = ScreenType.LOWEST,
    resources = resources
).toInt()

myView.layoutParams.width = adjustedWidthPx
```

### **DYNAMIC** Sizing (Percentage/Proportional)

`Dynamic` is ideal for **aggressive and proportional adjustment** that attempts to maintain the same percentage of the total screen width/height.

| Function | Return Unit | Description |
| :--- | :--- | :--- |
| `dynamicDp(value, screenType, resources)` | **Float (DP)** | Returns the adjusted value in DP (not converted to Px). |
| `dynamicPx(value, screenType, resources)` | **Float (PX)** | Returns the adjusted value in DP, *converted to real Pixels*. |
| `dynamicSp(value, screenType, resources)` | **Float (PX)** | Returns the adjusted value in DP, *converted to Scale Pixels (SP)*. |
| `dynamicEm(value, screenType, resources)` | **Float (PX)** | Returns the adjusted value in DP, *converted to Scale Pixels (EM)*. |

**Usage Example in Kotlin Code (Views/XML):**

```kotlin
// In an Activity/Fragment:
val resources = context.resources
val dynamicTextSizeSp = AppDimens.dynamicSp(
    value = 18f, // 18dp converted to SP
    screenType = ScreenType.LOWEST,
    resources = resources
)

myTextView.textSize = dynamicTextSizeSp // Already in PX, ready to be used as SP
```

-----

## 2\. 📐 Physical Unit Conversion Utilities (MM, CM, INCH)

The `AppDimens` object also exposes the physical unit conversion system (provided by `AppDimensPhysicalUnits`) for direct use in the View System.

| Function | Origin Unit | Return Unit | Description |
| :--- | :--- | :--- | :--- |
| `toDp(value, type, resources)` | INCH, CM, MM | **Float (DP)** | Converts the physical measurement to Logical Pixels (DP). |
| `toSp(value, type, resources)` | INCH, CM, MM | **Float (PX)** | Converts the physical measurement to Scale Pixels (SP), already in PX. |
| `toPx(value, type, resources)` | INCH, CM, MM | **Float (PX)** | Converts the physical measurement to Real Pixels (PX). |

**Conversion Example:**

```kotlin
// 15 Millimeters converted to Dp
val fifteenMmInDp = AppDimens.toDp(15f, UnitType.MM, resources)

// 1 Inch converted to Px (ideal for screens with known density)
val oneInchInPx = AppDimens.toPx(1.0f, UnitType.INCH, resources) 
```

-----

## 3\. 📦 Layout Utilities (Available Item Count Calculation)

One of the most powerful utilities provided by the `AppDimens` object is the ability to calculate how many items fit within a container, which is essential for layouts like `RecyclerViews` and `GridViews`.

### Function: `calculateAvailableItemCount`

This function determines how many items with a given size and padding can be laid out within a container, given the container's dimension in **Pixels (Px)**.

| Parameter | Type | Description |
| :--- | :--- | :--- |
| `containerSizePx` | `Int` | The size (Width or Height) of the container **in Pixels (PX)**. |
| `itemSizeDp` | `Float` | The base size of a single item **in DP**. |
| `itemMarginDp` | `Float` | The total padding around the item **in DP**. |
| `resources` | `Resources` | The Resources object for DP $\rightarrow$ Px conversion. |
| **Return** | `Int` | The calculated item count. |

**Usage Example in Kotlin Code (RecyclerView/GridView):**

```kotlin
// 1. Get the width of the RecyclerView in Pixels (after the layout is resolved)
val recyclerViewWidthPx: Int = myRecyclerView.width 

// 2. Calculate how many cards fit in the width, considering a 100dp Card with 8dp of padding
val itemCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerViewWidthPx,
    itemSizeDp = 100f, // 100dp
    itemMarginDp = 8f,  // 8dp (total padding, e.g., 4dp left + 4dp right)
    resources = resources
)

// 3. Use itemCount to define the spanCount in GridLayoutManager
// val layoutManager = GridLayoutManager(context, itemCount)
```

-----

# 🔎 Detailed Analysis: Integration with Views and Data Binding

## 1\. Data Binding Adapters (`DimensBindingAdapters.kt/.java`)

`BindingAdapters` are the essential bridge that allows using base DP values directly in XML and having the dynamic adjustment of **AppDimens** applied at runtime.

### How It Works

1.  **XML:** In the layout (`activity_dynamic_data_binding.xml`), you declare custom attributes, such as `app:dynamicWidthDp`.
2.  **`BindingAdapter`:** Android finds the corresponding function annotated with `@BindingAdapter` (e.g., `setDynamicWidth`).
3.  **Calculation:** The function receives the `View` and the base value in `Float` (e.g., `48f`). It uses the **AppDimens** *gateway* method to perform the dynamic calculation:
        ` kotlin     val pxValue = AppDimens.dynamic(dpValue).toPx(view.resources)      `
4.  **Application:** The adjusted value in Pixels (`pxValue`) is then applied directly to the `View`'s `layoutParams`.

### Examples of Custom Adapters

| XML Attribute | Purpose | Internal Conversion | Files |
| :--- | :--- | :--- | :--- |
| `app:dynamicWidthDp` | `View` Width | Dynamic DP $\rightarrow$ **PX** | `View.layoutParams.width` |
| `app:dynamicHeightDp` | `View` Height | Dynamic DP $\rightarrow$ **PX** | `View.layoutParams.height` |
| `app:dynamicTextSizeDp` | Font Size | Dynamic DP $\rightarrow$ **SP** (in PX) | `TextView.textSize` |
| `app:dynamicTextSizeEm` | Font Size | Dynamic DP $\rightarrow$ **EM** (in PX) | `TextView.textSize` |

### Usage in XML (`activity_dynamic_data_binding.xml`)

The layout uses a `dimenValue` variable (defined as `48f` in `MainActivity`) to demonstrate the application of dynamic adjustment:

```xml
<View
    android:id="@+id/dynamic_db_view"
    app:dynamicWidthDp="@{dimenValue}"  app:dynamicHeightDp="@{24f}"        android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    ... />

<TextView
    app:dynamicTextSizeDp="@{20f}"      android:text="Adjusted Dynamic Text"
    ... />
```

-----

## 2\. Direct Usage in Code (`MainActivity.kt/.java`)

`MainActivity` demonstrates how to use the `AppDimens` object to manually calculate dimensions in code, which is necessary for more complex layouts, margin manipulation (`MarginLayoutParams`), and percentage calculations.

### Example 1: Fixed vs. Dynamic (Manual)

The code demonstrates the difference between the two sizing systems by calculating a base value of `16dp`.

| Method | Philosophy | Result (in PX) |
| :--- | :--- | :--- |
| **`AppDimens.fixed(16f).toPx(...)`** | Subtle Adjustment (Logarithmic/AR) | `adjustedFixedPx` |
| **`AppDimens.dynamic(16f).toPx(...)`** | Proportional Adjustment (Percentage) | `adjustedDynamicPx` |

**Example (Kotlin):**

```kotlin
// 2. Fixed/Subtle Use: Calculates the left margin with the Fixed adjustment factor
val adjustedFixedPx = AppDimens.fixed(16f).toPx(resources).toInt()
val fixedLayoutParams = binding.fixedView.layoutParams as ViewGroup.MarginLayoutParams
fixedLayoutParams.marginStart = adjustedFixedPx

// 2. Dynamic/Aggressive Use: Calculates the width
val adjustedDynamicPx = AppDimens.dynamic(48f).toPx(resources).toInt()
binding.dynamicView.layoutParams.width = adjustedDynamicPx
```

### Example 2: Dynamic Percentage (`dynamicPercentagePx`)

**AppDimens** provides a dedicated function to calculate a specific percentage of the screen width or height in Pixels.

| Function | Parameters | Purpose |
| :--- | :--- | :--- |
| **`dynamicPercentagePx`** | `percentage: 0.80f`, `type: ScreenType.LOWEST` | Calculates 80% of the screen's minimum dimension (width or height). |

**Example (Kotlin/Java):**

```kotlin
// Calculates 80% of the LOWEST dimension (smallest screen dimension, W or H) in PX
val percentagePx = AppDimens.dynamicPercentagePx(
    percentage = 0.80f,
    type = ScreenType.LOWEST,
    resources = resources
)

// Set the width of a View to 80%
binding.percentageView.layoutParams.width = percentagePx.toInt()
```

### Example 3: Physical Units (`AppDimensPhysicalUnits`)

`MainActivity` demonstrates the conversion of physical units (millimeters) to Pixels (PX).

| Base Unit | Function | Application |
| :--- | :--- | :--- |
| 5.0 Millimeters | `AppDimensPhysicalUnits.toMm(mmValue, resources)` | Conversion to `mmInPx` |

**Example (Kotlin/Java):**

```kotlin
val mmValue = 5.0f // 5 millimeters
val mmInPx = AppDimensPhysicalUnits.toMm(mmValue, resources)

// Set the top margin of the View using the MM to PX conversion
val layoutParams = binding.physicalUnitView.layoutParams as ViewGroup.MarginLayoutParams
layoutParams.topMargin = mmInPx.toInt()
```

-----

## 3\. Additional Examples: Practical Use Cases

### A. Dynamic Layout Calculation (RecyclerView)

It is common to need a `RecyclerView` that displays the maximum number of items based on the available space. The `calculateAvailableItemCount` utility is ideal for this:

```kotlin
// Example in an Activity/Fragment:

// Screen width in Pixels
val containerWidthPx = resources.displayMetrics.widthPixels 

// Base size of the cards (in DP)
val cardSizeDp = 120f
val cardPaddingDp = 8f 

// Determines how many 120dp cards fit in the current screen width
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = containerWidthPx,
    itemSizeDp = cardSizeDp,
    itemMarginDp = cardPaddingDp * 2, // 8dp total margin (4dp left + 4dp right)
    resources = resources
)

// Apply to LayoutManager
// myRecyclerView.layoutManager = GridLayoutManager(context, spanCount)
```

### B. Use of Fixed Dimensions with Custom Qualifier

You can apply the `Fixed` adjustment (smoother) with customizations:

```kotlin
// Calculates an adjusted text size, but with lower sensitivity (K=0.5)
// This smooths the influence of the Aspect Ratio.
val customSensitivityTextSize = AppDimensFixed(
    dpValue = 18f, 
    screenType = ScreenType.LOWEST
)
.withCustomSensitivity(sensitivityK = 0.5)
.toSp(resources)

myTextView.textSize = customSensitivityTextSize
```
