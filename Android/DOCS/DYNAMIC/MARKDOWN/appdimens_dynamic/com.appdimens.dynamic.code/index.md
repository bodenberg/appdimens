---
layout: default
title: "Package-level declarations"
---

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AppDimens](-app-dimens/index.md) | [androidJvm]<br>object [AppDimens](-app-dimens/index.md)<br>EN A singleton object that provides functions for responsive dimension management, acting as a gateway to the `AppDimensFixed` and `AppDimensDynamic` constructors. |
| [AppDimensAdjustmentFactors](-app-dimens-adjustment-factors/index.md) | [androidJvm]<br>object [AppDimensAdjustmentFactors](-app-dimens-adjustment-factors/index.md)<br>EN A singleton object that provides functions for calculating and resolving adjustment factors and screen qualifiers. Compatible with the traditional Android View System (XML). |
| [AppDimensDynamic](-app-dimens-dynamic/index.md) | [androidJvm]<br>class [AppDimensDynamic](-app-dimens-dynamic/index.md)(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>EN Class for building dynamic dimensions that allow base Dp customization via screen qualifiers (`.screen()`). The final value is scaled by the screen. Compatible with the View System (XML layouts). |
| [AppDimensFixed](-app-dimens-fixed/index.md) | [androidJvm]<br>class [AppDimensFixed](-app-dimens-fixed/index.md)(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>EN Class for building &quot;fixed&quot; dimensions that are automatically adjusted based on the device's smallestScreenWidthDp and screen aspect ratio. Compatible with the View System (XML). |
| [AppDimensPhysicalUnits](-app-dimens-physical-units/index.md) | [androidJvm]<br>object [AppDimensPhysicalUnits](-app-dimens-physical-units/index.md)<br>EN A singleton object that provides functions for physical unit conversions (MM, CM, Inch) and measurement utilities. |
| [ScreenAdjustmentFactors](-screen-adjustment-factors/index.md) | [androidJvm]<br>data class [ScreenAdjustmentFactors](-screen-adjustment-factors/index.md)(val withArFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withArFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withoutArFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))<br>EN Data class to store screen adjustment factors. PT Data class para armazenar os fatores de ajuste de tela. |