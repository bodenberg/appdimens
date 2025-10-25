---
layout: default
title: "Package-level declarations"
---

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [DpQualifier](-dp-qualifier/index.md) | [androidJvm]<br>enum [DpQualifier](-dp-qualifier/index.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)&lt;[DpQualifier](-dp-qualifier/index.md)&gt; <br>EN Defines the screen qualifier types based on the device's smallest width (smallestWidthDp), height (screenHeightDp), or width (screenWidthDp). |
| [DpQualifierEntry](-dp-qualifier-entry/index.md) | [androidJvm]<br>data class [DpQualifierEntry](-dp-qualifier-entry/index.md)(val type: [DpQualifier](-dp-qualifier/index.md), val value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))<br>EN Represents a custom qualifier entry, combining the type and the minimum DP value for the custom adjustment to be applied. |
| [ScreenAdjustmentFactors](-screen-adjustment-factors/index.md) | [androidJvm]<br>data class [ScreenAdjustmentFactors](-screen-adjustment-factors/index.md)(val withArFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withArFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withoutArFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))<br>EN Stores the adjustment factors calculated from the screen dimensions. The Aspect Ratio (AR) calculation is performed only once per screen configuration. |
| [ScreenType](-screen-type/index.md) | [androidJvm]<br>enum [ScreenType](-screen-type/index.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)&lt;[ScreenType](-screen-type/index.md)&gt; <br>EN Defines which screen dimension (width or height) should be used as the basis for dynamic and percentage-based sizing calculations. |
| [UiModeQualifierEntry](-ui-mode-qualifier-entry/index.md) | [androidJvm]<br>data class [UiModeQualifierEntry](-ui-mode-qualifier-entry/index.md)(val uiModeType: [UiModeType](-ui-mode-type/index.md), val dpQualifierEntry: [DpQualifierEntry](-dp-qualifier-entry/index.md))<br>EN Represents a qualifier entry that combines a UI Mode type (device) AND a screen qualifier (SW, H, W). This combination has the HIGHEST PRIORITY. |
| [UiModeType](-ui-mode-type/index.md) | [androidJvm]<br>enum [UiModeType](-ui-mode-type/index.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)&lt;[UiModeType](-ui-mode-type/index.md)&gt; <br>EN Defines the Android UI Mode Types for dimension customization, based on [Configuration.uiMode](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html#uimode). |
| [UnitType](-unit-type/index.md) | [androidJvm]<br>enum [UnitType](-unit-type/index.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)&lt;[UnitType](-unit-type/index.md)&gt; <br>EN Defines the supported physical measurement units for conversion into density-independent pixels (Dp) or screen pixels (Px). |