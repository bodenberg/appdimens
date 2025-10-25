---
title: com.appdimens.dynamic.code
---
//[appdimens_dynamic](../../index.html)/[com.appdimens.dynamic.code](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [AppDimens](-app-dimens/index.html) | [androidJvm]<br>object [AppDimens](-app-dimens/index.html)<br>EN A singleton object that provides functions for responsive dimension management, acting as a gateway to the `AppDimensFixed` and `AppDimensDynamic` constructors. |
| [AppDimensAdjustmentFactors](-app-dimens-adjustment-factors/index.html) | [androidJvm]<br>object [AppDimensAdjustmentFactors](-app-dimens-adjustment-factors/index.html)<br>EN A singleton object that provides functions for calculating and resolving adjustment factors and screen qualifiers. Compatible with the traditional Android View System (XML). |
| [AppDimensAutoCache](-app-dimens-auto-cache/index.html) | [androidJvm]<br>object [AppDimensAutoCache](-app-dimens-auto-cache/index.html)<br>EN Automatic cache management system that mimics Compose's remember behavior. Automatically detects dependency changes and invalidates cache entries. |
| [AppDimensDynamic](-app-dimens-dynamic/index.html) | [androidJvm]<br>class [AppDimensDynamic](-app-dimens-dynamic/index.html)(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>EN Class for building dynamic dimensions that allow base Dp customization via screen qualifiers (`.screen()`). The final value is scaled by the screen. Compatible with the View System (XML layouts). |
| [AppDimensFixed](-app-dimens-fixed/index.html) | [androidJvm]<br>class [AppDimensFixed](-app-dimens-fixed/index.html)(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>EN Class for building &quot;fixed&quot; dimensions that are automatically adjusted based on the device's smallestScreenWidthDp and screen aspect ratio. Compatible with the View System (XML). |
| [AppDimensPhysicalUnits](-app-dimens-physical-units/index.html) | [androidJvm]<br>object [AppDimensPhysicalUnits](-app-dimens-physical-units/index.html)<br>EN Utility class for physical unit conversions. PT Classe utilitária para conversões de unidades físicas. |
| [ScreenAdjustmentFactors](-screen-adjustment-factors/index.html) | [androidJvm]<br>data class [ScreenAdjustmentFactors](-screen-adjustment-factors/index.html)(val withArFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withArFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withoutArFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))<br>EN Data class to store screen adjustment factors. PT Data class para armazenar os fatores de ajuste de tela. |


## Properties


| Name | Summary |
|---|---|
| [cm](cm.html) | [androidJvm]<br>val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[cm](cm.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[cm](cm.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts centimeters to Dp. PT Converte centímetros para Dp. |
| [dydp](dydp.html) | [androidJvm]<br>val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[dydp](dydp.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[dydp](dydp.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted value using dynamic dimensions (defaults: LOWEST, multiWindow=true). PT Constrói o valor ajustado usando dimensões dinâmicas (padrões: LOWEST, multiWindow=true). |
| [dyhdp](dyhdp.html) | [androidJvm]<br>val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[dyhdp](dyhdp.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[dyhdp](dyhdp.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted value using dynamic dimensions (defaults: HIGHEST, multiWindow=true). PT Constrói o valor ajustado usando dimensões dinâmicas (padrões: HIGHEST, multiWindow=true). |
| [fxdp](fxdp.html) | [androidJvm]<br>val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[fxdp](fxdp.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[fxdp](fxdp.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted value using fixed dimensions (defaults: LOWEST, multiWindow=true). PT Constrói o valor ajustado usando dimensões fixas (padrões: LOWEST, multiWindow=true). |
| [fxhdp](fxhdp.html) | [androidJvm]<br>val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[fxhdp](fxhdp.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[fxhdp](fxhdp.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Builds the adjusted value using fixed dimensions (defaults: HIGHEST, multiWindow=true). PT Constrói o valor ajustado usando dimensões fixas (padrões: HIGHEST, multiWindow=true). |
| [inch](inch.html) | [androidJvm]<br>val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[inch](inch.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[inch](inch.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts inches to Dp. PT Converte polegadas para Dp. |
| [mm](mm.html) | [androidJvm]<br>val [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[mm](mm.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[mm](mm.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts millimeters to Dp. PT Converte milímetros para Dp. |


## Functions


| Name | Summary |
|---|---|
| [dynamic](dynamic.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[dynamic](dynamic.html)(): [AppDimensDynamic](-app-dimens-dynamic/index.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[dynamic](dynamic.html)(): [AppDimensDynamic](-app-dimens-dynamic/index.html)<br>EN Creates a dynamic dimension builder with fluent API. PT Cria um construtor de dimensão dinâmica com API fluida. |
| [dynamicPer](dynamic-per.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[dynamicPer](dynamic-per.html)(type: ScreenType = ScreenType.LOWEST): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates a dynamic dimension value based on a percentage of the screen dimension. PT Calcula um valor de dimensão dinâmico com base em uma porcentagem da dimensão da tela. |
| [dynamicPerPx](dynamic-per-px.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[dynamicPerPx](dynamic-per-px.html)(type: ScreenType = ScreenType.LOWEST): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates a dynamic dimension value based on a percentage and converts it to pixels. PT Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para pixels. |
| [dynamicPerSp](dynamic-per-sp.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[dynamicPerSp](dynamic-per-sp.html)(type: ScreenType = ScreenType.LOWEST): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates a dynamic dimension value based on a percentage and converts it to SP. PT Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para SP. |
| [fixed](fixed.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[fixed](fixed.html)(): [AppDimensFixed](-app-dimens-fixed/index.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[fixed](fixed.html)(): [AppDimensFixed](-app-dimens-fixed/index.html)<br>EN Creates a fixed dimension builder with fluent API. PT Cria um construtor de dimensão fixa com API fluida. |
