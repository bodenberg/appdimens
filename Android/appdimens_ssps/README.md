# 📐 AppDimens SSP: Dynamic Text Scaling with Conditional Logic

The `AppDimens` library provides a refined system to ensure consistent layouts across different screens, and the **AppDimens SSP** module extends this capability to text unit (**Sp**) scaling in Compose, adding conditional logic and support for screen qualifiers.

This module allows you to define `TextUnit` (Sp) values based on **UI Mode** (Car, TV, Watch) and **DP Qualifiers** (Minimum Width, Height, Width), with a clear priority system.

## 🚀 Using the `Scaled` Class

The `Scaled` class lets you define a base `TextUnit` value and apply conditional rules to override it for different screen configurations.

### 1. Starting the Chain

You can start the `Scaled` chain from an `Int` or a `TextUnit`.

| Function              | Description                                                       |
| :-------------------- | :---------------------------------------------------------------- |
| `Int.scaledSp()`      | Starts the chain from an `Int` value (will be converted to `Sp`). |
| `TextUnit.scaledSp()` | Starts the chain from an existing `TextUnit` value.               |

#### Example:

```kotlin
// Start with a base value of 16sp
val baseText = 16.scaledSp()

// Or from an already defined TextUnit
val baseText = 16.sp.scaledSp()
```

---

### 2. Defining Conditional Rules

The `Scaled` class provides three priority levels for rule definitions, ensuring the most specific rule is applied first. Rules are evaluated from **lowest priority (1)** to **highest priority (3)**.

Resolution order is crucial: the entries list is sorted by **ascending priority** and, secondarily, by **descending DP qualifier value** (higher DP values are evaluated first).

|     Priority    | Method                                                           | Condition to Apply                                                        |
| :-------------: | :--------------------------------------------------------------- | :------------------------------------------------------------------------ |
| **1 (Highest)** | `screen(uiModeType, qualifierType, qualifierValue, customValue)` | Both **UI Mode** and **DP Qualifier** must match.                         |
|  **2 (Medium)** | `screen(type: UiModeType, customValue)`                          | Only the **UI Mode** must match.                                          |
|  **3 (Lowest)** | `screen(type: DpQualifier, value, customValue)`                  | Only the **DP Qualifier** must be **greater than or equal** to the value. |

#### 📝 Detailed Usage Example:

This example shows how to set different text sizes for different devices and screen sizes:

```kotlin
@Composable
fun TitleText() {
    val titleSize = 24.scaledSp() // Base value of 24sp
        // Priority 1 (Highest)
        // If 'Car' AND minimum width >= 720dp, use 48sp
        .screen(
            uiModeType = UiModeType.CAR,
            qualifierType = DpQualifier.SMALL_WIDTH,
            qualifierValue = 720,
            customValue = 48.sp
        )
        // If 'Watch' AND width >= 200dp, use 12sp
        .screen(
            uiModeType = UiModeType.WATCH,
            qualifierType = DpQualifier.WIDTH,
            qualifierValue = 200,
            customValue = 12 // Int (converted to Sp)
        )
        // Priority 2 (Medium)
        // If 'Television' (regardless of DP), use 40sp
        .screen(
            type = UiModeType.TELEVISION,
            customValue = 40.sp
        )
        // Priority 3 (Lowest)
        // If screen has 'Minimum Width' >= 600dp (Tablet), use 32sp
        .screen(
            type = DpQualifier.SMALL_WIDTH,
            value = 600,
            customValue = 32.sp
        )

    Text(
        text = "Dynamic Title",
        // Final resolution happens here, applying dynamic scaling
        // based on the Smallest Width qualifier.
        fontSize = titleSize.ssp 
    )
}
```

---

### 3. Final Value Resolution (Composable Getter)

After defining all rules, the final value is resolved using one of the *Composable property getters*. The getter determines the **dynamic scaling qualifier** applied at the end.

| Getter     | Base Qualifier (Dynamic Scaling)                   | Ideal Use                                                                           |
| :--------- | :------------------------------------------------- | :---------------------------------------------------------------------------------- |
| **`.ssp`** | **Smallest Width (sw)**: `DpQualifier.SMALL_WIDTH` | Default, uses the most restrictive dimension (`smallestScreenWidthDp`) as the base. |
| **`.hsp`** | **Height (h)**: `DpQualifier.HEIGHT`               | For elements that should scale based on screen height (`screenHeightDp`).           |
| **`.wsp`** | **Width (w)**: `DpQualifier.WIDTH`                 | For elements that should scale based on screen width (`screenWidthDp`).             |
| **`.sem`** | **Smallest Width (sw)**: `DpQualifier.SMALL_WIDTH` | Default, ignores accessibility font scaling.                                        |
| **`.hem`** | **Height (h)**: `DpQualifier.HEIGHT`               | Ignores accessibility font scaling.                                                 |
| **`.wem`** | **Width (w)**: `DpQualifier.WIDTH`                 | Ignores accessibility font scaling.                                                 |

#### Resolution Process (`resolve`):

1. Reads the current screen configuration (`LocalConfiguration.current`).
2. Iterates over custom entries from **priority 1** to **3**.
3. If a rule matches (e.g., `uiModeType == CAR` **AND** `smallestWidthDp >= 720`), that rule’s `customValue` is selected.
4. If no custom rule matches, `initialBaseSp` is used.
5. The selected `TextUnit` value (`customValue` or `initialBaseSp`) is converted to integer (`.value.toInt()`).
6. Dynamic scaling is applied using `toDynamicScaledSp` and the qualifier (`s`, `h`, or `w`) defined by the getter (`.ssp`, `.hsp`, `.wsp`).

---

## ⚙️ Direct Scaling Extensions

For simple text scaling without conditional logic (like `Scaled`), you can use direct `Int` extensions. They apply dynamic scaling immediately by referencing pre-calculated dimension resources (e.g., `_16sdp`).

| Extension     | Base Qualifier (Dynamic Scaling) | Resource Sought (Example for 16) |
| :------------ | :------------------------------- | :------------------------------- |
| **`Int.ssp`** | `DpQualifier.SMALL_WIDTH`        | `_16ssp`                         |
| **`Int.hsp`** | `DpQualifier.HEIGHT`             | `_16hsp`                         |
| **`Int.wsp`** | `DpQualifier.WIDTH`              | `_16wsp`                         |
| **`Int.sem`** | `DpQualifier.SMALL_WIDTH`        | `_16sem`                         |
| **`Int.hem`** | `DpQualifier.HEIGHT`             | `_16hem`                         |
| **`Int.wem`** | `DpQualifier.WIDTH`              | `_16wem`                         |

#### Example Using Extensions:

```kotlin
@Composable
fun SimpleText() {
    Text(
        // Apply dynamic scaling based on SW (Smallest Width)
        fontSize = 18.ssp 
    )
    Text(
        // Apply dynamic scaling based on H (Height)
        fontSize = 18.hsp 
    )
}
```

---

## 💻 XML Views and Dimension Resource Support

Dynamic scaling (**ssp**, **hsp**, **wsp**) relies on **pre-calculated dimension resources** in your Android project. To use them in XML, your project must have generated `dimens.xml` files with the required entries.

### Dimension Resource Format

`AppDimens SSP` expects scaled text resources in the format:

`@dimen/_<value><qualifier>sp`

Where:

* **`<value>`**: Base unit value (e.g., `10`, `16`, `24`).
* **`<qualifier>`**: Type of scaling:

  * **`s`** for **Smallest Width**
  * **`h`** for **Height**
  * **`w`** for **Width**

### Usage in XML

You can use these resources directly in text size attributes (`android:textSize`) in your XML layouts:

#### XML Examples:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Text scaled by SW (ssp)"
        android:textSize="@dimen/_16ssp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Text scaled by Height (hsp)"
        android:textSize="@dimen/_18hsp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Text scaled by Width (wsp)"
        android:textSize="@dimen/_14wsp" />

</LinearLayout>
```

### Important: `Scaled` Logic vs. XML

Note that the **conditional logic** of the `Scaled` class (with `UiModeType` and `DpQualifier` priorities) **does not apply** when using direct `@dimen/` resources in XML.

* **XML (`@dimen/_16ssp`)**: Gets the **pre-scaled dynamic value** from the resource.
* **Compose (`16.scaledSp().screen(...).ssp`)**: Gets a **conditionally adjusted base value** (e.g., 16sp changes to 24sp on tablet) and then applies dynamic scaling (`ssp`, `hsp`, `wsp`, `sem`, `hem`, `wem`) to that base.

For **full conditional customization**, use the `Scaled` class in **Compose**. XML usage is ideal for applying only **pure dynamic scaling**.


