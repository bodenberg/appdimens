---
title: AppDimensDynamic
---
//[appdimens_dynamic](../../../index.html)/[com.appdimens.dynamic.compose](../index.html)/[AppDimensDynamic](index.html)



# AppDimensDynamic



[androidJvm]\
class [AppDimensDynamic](index.html)(initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)

EN A builder class for creating dynamic dimensions that allow base Dp customization via screen qualifiers (`.screen()`). The final value is scaled by the screen size.



PT Uma classe construtora para criar dimensões dinâmicas que permitem a customização do Dp base por meio de qualificadores de tela (`.screen()`). O valor final é escalado pelo tamanho da tela.



## Constructors


| | |
|---|---|
| [AppDimensDynamic](-app-dimens-dynamic.html) | [androidJvm]<br>constructor(initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false) |


## Properties


| Name | Summary |
|---|---|
| [dp](dp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [dp](dp.html): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Builds the adjusted Dp from the calculation. |
| [em](em.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [em](em.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Builds the adjusted TextUnit (Sp) from the calculation (NO FONT SCALE). |
| [px](px.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [px](px.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted Pixel value (Float) from the calculation. |
| [sp](sp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [sp](sp.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Builds the adjusted TextUnit (Sp) from the calculation. |


## Functions


| Name | Summary |
|---|---|
| [multiViewAdjustment](multi-view-adjustment.html) | [androidJvm]<br>fun [multiViewAdjustment](multi-view-adjustment.html)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [AppDimensDynamic](index.html)<br>EN Ignores multi-view adjustment if set to true. |
| [remember](remember.html) | [androidJvm]<br>fun [remember](remember.html)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [AppDimensDynamic](index.html)<br>EN Enables or disables remember for this instance. |
| [screen](screen.html) | [androidJvm]<br>fun [screen](screen.html)(type: UiModeType, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [AppDimensDynamic](index.html)<br>EN Sets a custom dimension for a specific UI mode.<br>[androidJvm]<br>fun [screen](screen.html)(type: UiModeType, customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [AppDimensDynamic](index.html)<br>EN Overload for `screen` that accepts a TextUnit value.<br>[androidJvm]<br>fun [screen](screen.html)(type: UiModeType, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](index.html)<br>EN Overload for `screen` that accepts a Float value.<br>[androidJvm]<br>fun [screen](screen.html)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](index.html)<br>EN Overload for `screen` that accepts an Int value.<br>[androidJvm]<br>fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [AppDimensDynamic](index.html)<br>EN Overload for `screen` that accepts a TextUnit value for a DpQualifier.<br>[androidJvm]<br>fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](index.html)<br>EN Overload for `screen` that accepts a Float value for a DpQualifier.<br>[androidJvm]<br>fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](index.html)<br>EN Sets a custom dimension for a specific screen qualifier.<br>[androidJvm]<br>fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [AppDimensDynamic](index.html)<br>EN Sets a custom dimension for a specific intersection of UI mode and screen qualifier.<br>[androidJvm]<br>fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [AppDimensDynamic](index.html)<br>EN Overload for `screen` intersection that accepts a TextUnit value.<br>[androidJvm]<br>fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](index.html)<br>EN Overload for `screen` intersection that accepts a Float value.<br>[androidJvm]<br>fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](index.html)<br>EN Overload for `screen` intersection that accepts an Int value. |
| [type](type.html) | [androidJvm]<br>fun [type](type.html)(type: ScreenType): [AppDimensDynamic](index.html)<br>EN Sets the screen dimension type to be used as a reference (HIGHEST or LOWEST). |
