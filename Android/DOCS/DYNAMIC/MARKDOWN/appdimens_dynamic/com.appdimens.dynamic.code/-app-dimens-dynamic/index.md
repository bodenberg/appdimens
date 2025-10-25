---
layout: default
title: "AppDimensDynamic"
---

# AppDimensDynamic

[androidJvm]
class [AppDimensDynamic](index.md)(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)

EN Class for building dynamic dimensions that allow base Dp customization via screen qualifiers (`.screen()`). The final value is scaled by the screen. Compatible with the View System (XML layouts).

PT Classe para construir dimensões dinâmicas que permitem customização do DP base via qualificadores de tela (`.screen()`). O valor final é escalado pela tela. Compatível com View System (XML layouts).

## Constructors

| | |
|---|---|
| [AppDimensDynamic](-app-dimens-dynamic.md) | [androidJvm]<br>constructor(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false) |

## Functions

| Name | Summary |
|---|---|
| [calculateAdjustedValue](calculate-adjusted-value.md) | [androidJvm]<br>fun [calculateAdjustedValue](calculate-adjusted-value.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Performs the final dynamic dimension calculation. |
| [multiViewAdjustment](multi-view-adjustment.md) | [androidJvm]<br>fun [multiViewAdjustment](multi-view-adjustment.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [AppDimensDynamic](index.md)<br>EN Ignores adjustments when the app is in multi-window mode. |
| [screen](screen.md) | [androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](index.md)<br>EN Sets a custom dimension value for a specific UI mode.<br>[androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](index.md)<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](index.md)<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](index.md)<br>[androidJvm]<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](index.md)<br>EN Sets a custom dimension value for a specific screen qualifier.<br>[androidJvm]<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](index.md)<br>EN Sets a custom dimension for the intersection of a UI mode and a screen qualifier. |
| [toDp](to-dp.md) | [androidJvm]<br>fun [toDp](to-dp.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Returns the dynamically adjusted Dp value (in Dp, not converted to PX). |
| [toDpInt](to-dp-int.md) | [androidJvm]<br>fun [toDpInt](to-dp-int.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN Returns the dynamically adjusted Dp value (in Dp, not converted to PX) as an Int. |
| [toEm](to-em.md) | [androidJvm]<br>fun [toEm](to-em.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the dynamically adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float), ignoring the system's font scale ('em' unit). |
| [toEmInt](to-em-int.md) | [androidJvm]<br>fun [toEmInt](to-em-int.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN Builds the dynamically adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Int), ignoring the system's font scale ('em' unit). |
| [toPx](to-px.md) | [androidJvm]<br>fun [toPx](to-px.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the dynamically adjusted Dp value and converts it to Pixels (Float). |
| [toPxInt](to-px-int.md) | [androidJvm]<br>fun [toPxInt](to-px-int.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN Builds the dynamically adjusted Dp value and converts it to Pixels (Int). Useful for setters that only accept Int. |
| [toSp](to-sp.md) | [androidJvm]<br>fun [toSp](to-sp.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the dynamically adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Float). |
| [toSpInt](to-sp-int.md) | [androidJvm]<br>fun [toSpInt](to-sp-int.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN Builds the dynamically adjusted Dp value and converts it to Scalable Pixels (SP) in Pixels (Int). |
| [type](type.md) | [androidJvm]<br>fun [type](type.md)(type: ScreenType): [AppDimensDynamic](index.md)<br>EN Sets the screen dimension type (LOWEST or HIGHEST) to be used as the base for adjustments. |