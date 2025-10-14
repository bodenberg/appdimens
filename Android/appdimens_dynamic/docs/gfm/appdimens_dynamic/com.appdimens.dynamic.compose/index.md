//[appdimens_dynamic](../../index.md)/[com.appdimens.dynamic.compose](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [AppDimens](-app-dimens/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>object [AppDimens](-app-dimens/index.md)<br>Objeto singleton que fornece funções para gerenciamento de dimensões responsivas em Jetpack Compose, agindo como um gateway para os construtores Fixed e Dynamic. |
| [AppDimensAdjustmentFactors](-app-dimens-adjustment-factors/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>object [AppDimensAdjustmentFactors](-app-dimens-adjustment-factors/index.md)<br>Objeto singleton que fornece funções para o cálculo e resolução de fatores de ajuste e qualificadores de tela. |
| [AppDimensDynamic](-app-dimens-dynamic/index.md) | [androidJvm]<br>class [AppDimensDynamic](-app-dimens-dynamic/index.md)(initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>Classe para construir dimensões dinâmicas que permitem customização do DP base via qualificadores de tela (`.screen()`). O valor final é escalado pela tela. |
| [AppDimensFixed](-app-dimens-fixed/index.md) | [androidJvm]<br>class [AppDimensFixed](-app-dimens-fixed/index.md)(initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)<br>Classe para construir dimensões &quot;fixas&quot; que são ajustadas automaticamente com base no menor `smallestScreenWidthDp` do dispositivo e na proporção da tela. |
| [AppDimensPhysicalUnits](-app-dimens-physical-units/index.md) | [androidJvm]<br>object [AppDimensPhysicalUnits](-app-dimens-physical-units/index.md)<br>Objeto singleton que fornece funções para conversão de unidades físicas (MM, CM, Inch) e utilitários de medição. |