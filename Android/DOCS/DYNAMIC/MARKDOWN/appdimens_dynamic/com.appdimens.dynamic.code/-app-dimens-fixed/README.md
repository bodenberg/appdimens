//[appdimens_dynamic](../../../README.md)/[com.appdimens.dynamic.code](../README.md)/[AppDimensFixed](README.md)

# AppDimensFixed

[androidJvm]\
class [AppDimensFixed](README.md)(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)

EN Class for building &quot;fixed&quot; dimensions that are automatically adjusted based on the device's smallestScreenWidthDp and screen aspect ratio. Compatible with the View System (XML).

PT Classe para construir dimensões &quot;fixas&quot; que são ajustadas automaticamente com base no menor `smallestScreenWidthDp` do dispositivo e na proporção da tela. Compatível com View System (XML).

## Constructors

| | |
|---|---|
| [AppDimensFixed](-app-dimens-fixed.md) | [androidJvm]<br>constructor(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false) |

## Functions

| Name | Summary |
|---|---|
| [aspectRatio](aspect-ratio.md) | [androidJvm]<br>fun [aspectRatio](aspect-ratio.md)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [AppDimensFixed](README.md)<br>EN Enables or disables the aspect ratio adjustment. |
| [cache](cache.md) | [androidJvm]<br>fun [cache](cache.md)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [AppDimensFixed](README.md)<br>EN Enables or disables cache for this instance. |
| [calculateAdjustedValue](calculate-adjusted-value.md) | [androidJvm]<br>fun [calculateAdjustedValue](calculate-adjusted-value.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Performs the final dimension adjustment calculation. |
| [multiViewAdjustment](multi-view-adjustment.md) | [androidJvm]<br>fun [multiViewAdjustment](multi-view-adjustment.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [AppDimensFixed](README.md)<br>EN Ignores adjustments when the app is in multi-window mode. |
| [screen](screen.md) | [androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](README.md)<br>EN Sets a custom dimension value for a specific UI mode.<br>[androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](README.md)<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](README.md)<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](README.md)<br>[androidJvm]<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](README.md)<br>EN Sets a custom dimension value for a specific screen qualifier.<br>[androidJvm]<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](README.md)<br>EN Sets a custom dimension for the intersection of a UI mode and a screen qualifier. |
| [toDp](to-dp.md) | [androidJvm]<br>fun [toDp](to-dp.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Returns the adjusted Dp value (in Dp, not converted to PX). |
| [toEm](to-em.md) | [androidJvm]<br>fun [toEm](to-em.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float), ignoring the system's font scale ('em' unit). |
| [toEmInt](to-em-int.md) | [androidJvm]<br>fun [toEmInt](to-em-int.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Int), ignoring the system's font scale ('em' unit). |
| [toPx](to-px.md) | [androidJvm]<br>fun [toPx](to-px.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted Dp value and converts it to Pixels (Float). |
| [toSp](to-sp.md) | [androidJvm]<br>fun [toSp](to-sp.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float). |
| [type](type.md) | [androidJvm]<br>fun [type](type.md)(type: ScreenType): [AppDimensFixed](README.md)<br>EN Sets the screen dimension type (LOWEST or HIGHEST) to be used as the base for adjustments. |