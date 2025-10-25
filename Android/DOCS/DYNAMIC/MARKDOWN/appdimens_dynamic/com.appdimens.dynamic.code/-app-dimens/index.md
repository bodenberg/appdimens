---
title: AppDimens
---
//[appdimens_dynamic](../../../index.html)/[com.appdimens.dynamic.code](../index.html)/[AppDimens](index.html)



# AppDimens



[androidJvm]\
object [AppDimens](index.html)

EN A singleton object that provides functions for responsive dimension management, acting as a gateway to the `AppDimensFixed` and `AppDimensDynamic` constructors.



PT Objeto singleton que fornece funções para gerenciamento de dimensões responsivas, agindo como um gateway para os construtores `AppDimensFixed` e `AppDimensDynamic`.



## Properties


| Name | Summary |
|---|---|
| [globalCacheEnabled](global-cache-enabled.html) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)<br>var [globalCacheEnabled](global-cache-enabled.html): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Global cache control for all AppDimensDynamic instances. PT Controle global de cache para todas as instâncias AppDimensDynamic. |


## Functions


| Name | Summary |
|---|---|
| [calculateAvailableItemCount](calculate-available-item-count.html) | [androidJvm]<br>fun [calculateAvailableItemCount](calculate-available-item-count.html)(containerSizePx: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), itemSizeDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), itemMarginDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN Calculates the maximum number of items that can fit in a container, given the container size in PX. |
| [clearAllCaches](clear-all-caches.html) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [clearAllCaches](clear-all-caches.html)()<br>EN Clears all caches from all instances. PT Limpa todos os caches de todas as instâncias. |
| [dynamic](dynamic.html) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [dynamic](dynamic.html)(initialValueDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)? = null): [AppDimensDynamic](../-app-dimens-dynamic/index.html)<br>EN Initializes the `AppDimensDynamic` constructor from a Float value in Dp.<br>[androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [dynamic](dynamic.html)(initialValueInt: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)? = null): [AppDimensDynamic](../-app-dimens-dynamic/index.html)<br>EN Initializes the `AppDimensDynamic` constructor from an Int value in Dp. |
| [dynamicPercentageDp](dynamic-percentage-dp.html) | [androidJvm]<br>fun [dynamicPercentageDp](dynamic-percentage-dp.html)(percentage: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: ScreenType = ScreenType.LOWEST, resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates a dynamic dimension value based on a percentage (0.0 to 1.0) of the screen dimension. Returns the value in Dp (Float). |
| [dynamicPercentagePx](dynamic-percentage-px.html) | [androidJvm]<br>fun [dynamicPercentagePx](dynamic-percentage-px.html)(percentage: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: ScreenType = ScreenType.LOWEST, resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates a dynamic dimension value based on a percentage and converts it to Pixels (PX). |
| [dynamicPercentageSp](dynamic-percentage-sp.html) | [androidJvm]<br>fun [dynamicPercentageSp](dynamic-percentage-sp.html)(percentage: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: ScreenType = ScreenType.LOWEST, resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates a dynamic dimension value based on a percentage and converts it to Scalable Pixels (SP) in PX. |
| [fixed](fixed.html) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [fixed](fixed.html)(initialValueDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)? = null): [AppDimensFixed](../-app-dimens-fixed/index.html)<br>EN Initializes the `AppDimensFixed` constructor from a Float value in Dp.<br>[androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [fixed](fixed.html)(initialValueInt: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)? = null): [AppDimensFixed](../-app-dimens-fixed/index.html)<br>EN Initializes the `AppDimensFixed` constructor from an Int value in Dp. |
| [isGlobalAspectRatioEnabled](is-global-aspect-ratio-enabled.html) | [androidJvm]<br>fun [isGlobalAspectRatioEnabled](is-global-aspect-ratio-enabled.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Gets the current global aspect ratio setting. |
| [isGlobalCacheEnabled](is-global-cache-enabled.html) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [isGlobalCacheEnabled](is-global-cache-enabled.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Gets the current global cache setting. |
| [isGlobalIgnoreMultiViewAdjustment](is-global-ignore-multi-view-adjustment.html) | [androidJvm]<br>fun [isGlobalIgnoreMultiViewAdjustment](is-global-ignore-multi-view-adjustment.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Gets the current global multi-view adjustment setting. |
| [setGlobalAspectRatio](set-global-aspect-ratio.html) | [androidJvm]<br>fun [setGlobalAspectRatio](set-global-aspect-ratio.html)(enabled: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [AppDimens](index.html)<br>EN Sets the global aspect ratio adjustment setting. |
| [setGlobalCache](set-global-cache.html) | [androidJvm]<br>@[JvmStatic](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-jvm-static/index.html)<br>fun [setGlobalCache](set-global-cache.html)(enabled: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [AppDimens](index.html)<br>EN Sets the global cache control setting. |
| [setGlobalIgnoreMultiViewAdjustment](set-global-ignore-multi-view-adjustment.html) | [androidJvm]<br>fun [setGlobalIgnoreMultiViewAdjustment](set-global-ignore-multi-view-adjustment.html)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [AppDimens](index.html)<br>EN Sets the global multi-view adjustment setting. |
