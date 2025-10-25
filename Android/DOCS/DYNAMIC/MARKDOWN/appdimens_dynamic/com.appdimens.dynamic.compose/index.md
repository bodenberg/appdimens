---
layout: default
title: "Package-level declarations"
category: dynamic
permalink: /DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.compose/index.html
---

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AppDimens](-app-dimens/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>object [AppDimens](-app-dimens/index.md)<br>EN Singleton object that provides functions for responsive dimension management in Jetpack Compose, acting as a gateway to the Fixed and Dynamic builders. |
| [AppDimensAdjustmentFactors](-app-dimens-adjustment-factors/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>object [AppDimensAdjustmentFactors](-app-dimens-adjustment-factors/index.md)<br>EN Singleton object that provides functions for calculating and resolving adjustment factors and screen qualifiers. |
| [AppDimensDynamic](-app-dimens-dynamic/index.md) | [androidJvm]<br>class [AppDimensDynamic](-app-dimens-dynamic/index.md)(initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>EN A builder class for creating dynamic dimensions that allow base Dp customization via screen qualifiers (`.screen()`). The final value is scaled by the screen size. |
| [AppDimensFixed](-app-dimens-fixed/index.md) | [androidJvm]<br>class [AppDimensFixed](-app-dimens-fixed/index.md)(initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>EN A builder class for creating "fixed" dimensions that are automatically adjusted based on the device's `smallestScreenWidthDp` and screen aspect ratio. |
| [AppDimensPhysicalUnits](-app-dimens-physical-units/index.md) | [androidJvm]<br>object [AppDimensPhysicalUnits](-app-dimens-physical-units/index.md)<br>EN Singleton object providing functions for physical unit conversion (MM, CM, Inch) and measurement utilities. |