---
title: ScreenAdjustmentFactors
---
//[appdimens_library](../../../index.html)/[com.appdimens.library](../index.html)/[ScreenAdjustmentFactors](index.html)



# ScreenAdjustmentFactors



[androidJvm]\
data class [ScreenAdjustmentFactors](index.html)(val withArFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withArFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withoutArFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))

EN Stores the adjustment factors calculated from the screen dimensions. The Aspect Ratio (AR) calculation is performed only once per screen configuration.



PT Armazena os fatores de ajuste calculados a partir das dimensões da tela. O cálculo do Aspect Ratio (AR) é feito apenas uma vez por configuração de tela.



## Constructors


| | |
|---|---|
| [ScreenAdjustmentFactors](-screen-adjustment-factors.html) | [androidJvm]<br>constructor(withArFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), withArFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), withoutArFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), adjustmentFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), adjustmentFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [adjustmentFactorHighest](adjustment-factor-highest.html) | [androidJvm]<br>val [adjustmentFactorHighest](adjustment-factor-highest.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN The base adjustment factor (increment multiplier), HIGHEST: max(W, H).     PT Fator base de ajuste (multiplicador do incremento), HIGHEST: max(W, H). |
| [adjustmentFactorLowest](adjustment-factor-lowest.html) | [androidJvm]<br>val [adjustmentFactorLowest](adjustment-factor-lowest.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN The base adjustment factor (increment multiplier), LOWEST: smallestWidthDp.     PT Fator base de ajuste (multiplicador do incremento), LOWEST: smallestWidthDp. |
| [withArFactorHighest](with-ar-factor-highest.html) | [androidJvm]<br>val [withArFactorHighest](with-ar-factor-highest.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN The final and COMPLETE scaling factor, using the HIGHEST base (largest dimension) + AR.     PT Fator de escala final e COMPLETO, usando a base HIGHEST (maior dimensão) + AR. |
| [withArFactorLowest](with-ar-factor-lowest.html) | [androidJvm]<br>val [withArFactorLowest](with-ar-factor-lowest.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN The final and COMPLETE scaling factor, using the LOWEST base (smallest dimension) + AR.     PT Fator de escala final e COMPLETO, usando a base LOWEST (menor dimensão) + AR. |
| [withoutArFactor](without-ar-factor.html) | [androidJvm]<br>val [withoutArFactor](without-ar-factor.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN The final scaling factor WITHOUT AR (uses the LOWEST base for safety).     PT Fator de escala final SEM AR (Usa a base LOWEST por segurança). |
