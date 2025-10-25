---
title: AppDimensFixed
---
//[appdimens_dynamic](../../../index.html)/[com.appdimens.dynamic.code](../index.html)/[AppDimensFixed](index.html)



# AppDimensFixed



[androidJvm]\
class [AppDimensFixed](index.html)(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)

EN Class for building &quot;fixed&quot; dimensions that are automatically adjusted based on the device's smallestScreenWidthDp and screen aspect ratio. Compatible with the View System (XML).



PT Classe para construir dimensões &quot;fixas&quot; que são ajustadas automaticamente com base no menor `smallestScreenWidthDp` do dispositivo e na proporção da tela. Compatível com View System (XML).



## Constructors


| | |
|---|---|
| [AppDimensFixed](-app-dimens-fixed.html) | [androidJvm]<br>constructor(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false) |


## Functions


| Name | Summary |
|---|---|
| [aspectRatio](aspect-ratio.html) | [androidJvm]<br>fun [aspectRatio](aspect-ratio.html)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [AppDimensFixed](index.html)<br>EN Enables or disables the aspect ratio adjustment. |
| [cache](cache.html) | [androidJvm]<br>fun [cache](cache.html)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [AppDimensFixed](index.html)<br>EN Enables or disables cache for this instance. |
| [calculateAdjustedValue](calculate-adjusted-value.html) | [androidJvm]<br>fun [calculateAdjustedValue](calculate-adjusted-value.html)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Performs the final dimension adjustment calculation. |
| [multiViewAdjustment](multi-view-adjustment.html) | [androidJvm]<br>fun [multiViewAdjustment](multi-view-adjustment.html)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [AppDimensFixed](index.html)<br>EN Ignores adjustments when the app is in multi-window mode. |
| [screen](screen.html) | [androidJvm]<br>fun [screen](screen.html)(type: UiModeType, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.html)<br>EN Sets a custom dimension value for a specific UI mode.<br>[androidJvm]<br>fun [screen](screen.html)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.html)<br>fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.html)<br>fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.html)<br>[androidJvm]<br>fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.html)<br>EN Sets a custom dimension value for a specific screen qualifier.<br>[androidJvm]<br>fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.html)<br>EN Sets a custom dimension for the intersection of a UI mode and a screen qualifier. |
| [toDp](to-dp.html) | [androidJvm]<br>fun [toDp](to-dp.html)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Returns the adjusted Dp value (in Dp, not converted to PX). |
| [toEm](to-em.html) | [androidJvm]<br>fun [toEm](to-em.html)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float), ignoring the system's font scale ('em' unit). |
| [toEmInt](to-em-int.html) | [androidJvm]<br>fun [toEmInt](to-em-int.html)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Int), ignoring the system's font scale ('em' unit). |
| [toPx](to-px.html) | [androidJvm]<br>fun [toPx](to-px.html)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted Dp value and converts it to Pixels (Float). |
| [toSp](to-sp.html) | [androidJvm]<br>fun [toSp](to-sp.html)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float). |
| [type](type.html) | [androidJvm]<br>fun [type](type.html)(type: ScreenType): [AppDimensFixed](index.html)<br>EN Sets the screen dimension type (LOWEST or HIGHEST) to be used as the base for adjustments. |
