Here’s the English translation of your text:

---

# 📐 AppDimens SDP: Dynamic Scaling with Conditional Logic

The `AppDimens` library provides a robust system to ensure layout consistency across all screen sizes. The **AppDimens SDP** module manages the scaling of dimension units (**Dp**) in Compose, introducing a priority system for applying conditional rules.

The **`Scaled`** class (for Dp) allows you to define a base `Dp` value and apply substitution rules based on **UI Mode** (`CAR`, `TELEVISION`, etc.) and **DP Qualifiers** (Minimum Width, Height, Width), with a clear priority system.

## 🚀 Using the `Scaled` Class (for Dp)

The `Scaled` class lets you define a base `Dp` value and apply conditional rules to override it for different screen configurations.

### 1. Chain Initialization

You can start a `Scaled` chain from either an `Int` or a `Dp`.

| Function         | Description                                               |
| :--------------- | :-------------------------------------------------------- |
| `Int.scaledDp()` | Starts the chain from an `Int` value (converted to `Dp`). |
| `Dp.scaledDp()`  | Starts the chain from an existing `Dp` value.             |

#### Initialization Example:

```kotlin
// Start with a base value of 16dp
val baseSize = 16.scaledDp()

// Or from an already defined Dp
val baseSize = 16.dp.scaledDp()
```

---

### 2. Defining Conditional Rules

The `Scaled` class provides three priority levels for rule definition, ensuring the most specific rule is applied first.

The **resolution order** is critical: entries are sorted by **ascending priority** (1, 2, 3) and secondarily by **descending DP qualifier value** (larger DP values are evaluated first).

|     Priority    | Method                                                           | Condition for Application                                                 |
| :-------------: | :--------------------------------------------------------------- | :------------------------------------------------------------------------ |
| **1 (Highest)** | `screen(uiModeType, qualifierType, qualifierValue, customValue)` | Both **UI Mode** and **DP Qualifier** must match.                         |
|  **2 (Medium)** | `screen(type: UiModeType, customValue)`                          | Only the **UI Mode** must match.                                          |
|  **3 (Lowest)** | `screen(type: DpQualifier, value, customValue)`                  | Only the **DP Qualifier** must be **greater than or equal** to the value. |

#### 📝 Detailed Example:

This example shows how to define different `Dp` sizes for different devices and screen sizes:

```kotlin
@Composable
fun DynamicBox() {
    val boxSize = 80.scaledDp() // Base value 80dp
        // Priority 1 (Highest)
        // If it's a 'Watch' (Wear OS) AND min width >= 200dp, use 40dp.
        .screen(
            uiModeType = UiModeType.WATCH,
            qualifierType = DpQualifier.SMALL_WIDTH,
            qualifierValue = 200,
            customValue = 40.dp
        )
        // If it's a 'Car' (Android Auto), use 120dp (priority 2).
        .screen(
            type = UiModeType.CAR,
            customValue = 120.dp
        )
        // Priority 3 (Lowest)
        // If the screen has 'Min Width' >= 720dp (Large Tablet), use 150dp.
        .screen(
            type = DpQualifier.SMALL_WIDTH,
            value = 720,
            customValue = 150
        )

    Box(
        modifier = Modifier
            // Final resolution happens here, applying dynamic scaling
            // based on the Smallest Width qualifier.
            .size(boxSize.sdp)
            .background(Color.Blue)
    )
}
```

---

### 3. Final Value Resolution (Composable Getter)

After defining all rules, the final value is resolved using a **Composable property getter**. The getter determines the **dynamic scaling qualifier** applied to the base or custom value.

| Getter     | Base Qualifier (Dynamic Scaling)                   | Ideal Use                                                                                |
| :--------- | :------------------------------------------------- | :--------------------------------------------------------------------------------------- |
| **`.sdp`** | **Smallest Width (sw)**: `DpQualifier.SMALL_WIDTH` | Default, uses the most restrictive dimension (`smallestScreenWidthDp`) for base scaling. |
| **`.hdp`** | **Height (h)**: `DpQualifier.HEIGHT`               | For elements that should scale based on screen height (`screenHeightDp`).                |
| **`.wdp`** | **Width (w)**: `DpQualifier.WIDTH`                 | For elements that should scale based on screen width (`screenWidthDp`).                  |

Resolution process (`resolve`):

1. Reads the current screen configuration.
2. Evaluates custom rules in priority order (1 to 3).
3. If a rule matches (e.g., Priority 1), its `customValue` is selected.
4. If no custom rule matches, `initialBaseDp` is used.
5. The selected `Dp` value is converted to an integer (`.value.toInt()`).
6. **Dynamic scaling** is applied to this integer using `toDynamicScaledDp` and the qualifier (s, h, or w) defined by the getter (`.sdp`, `.hdp`, `.wdp`).

---

## ⚙️ Direct Scaling Extensions

For simple Dp scaling without `Scaled` conditional logic, you can use direct `Int` extensions. These apply dynamic scaling immediately by referencing a pre-calculated dimension resource (e.g., `@dimen/_16sdp`).

| Extension     | Base Qualifier (Dynamic Scaling) | Resource Example (for 16) |
| :------------ | :------------------------------- | :------------------------ |
| **`Int.sdp`** | `DpQualifier.SMALL_WIDTH`        | `_16sdp`                  |
| **`Int.hdp`** | `DpQualifier.HEIGHT`             | `_16hdp`                  |
| **`Int.wdp`** | `DpQualifier.WIDTH`              | `_16wdp`                  |

#### Example Using Extensions:

```kotlin
@Composable
fun SimpleDimension() {
    Spacer(
        modifier = Modifier
            // Height scaled dynamically by Smallest Width (sw)
            .height(18.sdp)
            // Width scaled dynamically by Width (w)
            .width(100.wdp)
    )
}
```

---

## 💻 XML Views & Dimension Resource Support

Dynamic scaling in `AppDimens` is built on the convention of **pre-calculated dimension resources** in your Android project. The `sdp`, `hdp`, and `wdp` system works by looking for dimension resources that follow a specific naming pattern.

### Dimension Resource Format

`AppDimens SDP` expects scaled dimension resources in the format:

`@dimen/_<optional_negative_prefix><value><qualifier>dp`

Where:

* **`<value>`**: Base unit value (e.g., `10`, `16`, `24`).
* **`<qualifier>`**: Scaling type:

  * **`s`** for **Smallest Width**.
  * **`h`** for **Height**.
  * **`w`** for **Width**.
* **`<optional_negative_prefix>`**: If negative (e.g., negative margins), prefix is **`minus`** (e.g., `_minus16sdp`).

### How to Use in XML

These resources can be used directly in dimension attributes (`android:layout_width`, `android:padding`, etc.) in your XML layouts:

#### XML Usage Examples:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="@dimen/_100sdp"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/_16sdp"
        android:text="Width & Margin SW" />

    <View
        android:layout_width="match_parent"
        android:layout_height="@dimen/_32hdp"
        android:background="#FF0000" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="@dimen/_minus8wdp"
        android:text="Negative Margin W" />

</LinearLayout>
```

### Critical Difference: `Scaled` (Compose) vs. XML

It’s important to understand that the **conditional logic** in the `Scaled` class (with `UiModeType` and `DpQualifier` priorities) **only runs in Compose**.

* **XML use (`@dimen/_16sdp`)**: Only retrieves the **pre-scaled dynamic value** from the dimension resource.
* **Compose use (`16.scaledDp().screen(...).sdp`)**: First evaluates the conditions to override the base value (e.g., replace `16dp` with `24dp` on a tablet), and then applies dynamic scaling (`sdp`, `hdp`, or `wdp`) to the final base value.

Use XML when you want **pure dynamic scaling**, and use Compose with `Scaled` for **full conditional customization**.

