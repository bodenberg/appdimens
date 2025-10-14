//[appdimens_library](../../../index.md)/[com.appdimens.library](../index.md)/[ScreenAdjustmentFactors](index.md)

# ScreenAdjustmentFactors

data class [ScreenAdjustmentFactors](index.md)(val withArFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withArFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val withoutArFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val adjustmentFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))

Armazena os fatores de ajuste calculados a partir das dimensões da tela. O cálculo do Aspect Ratio (AR) é feito apenas uma vez por configuração de tela.

#### Parameters

androidJvm

| | |
|---|---|
| withArFactor | Fator de escala final, incluindo o ajuste fino do Aspect Ratio (usando a sensibilidade padrão). |
| withoutArFactor | Fator de escala final, usando o incremento base de 0.10f (SEM AR). |
| adjustmentFactor | Fator base de ajuste (calculado a partir da diferença da smallestWidthDp / INCREMENT_DP_STEP). |

## Constructors

| | |
|---|---|
| [ScreenAdjustmentFactors](-screen-adjustment-factors.md) | [androidJvm]<br>constructor(withArFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), withArFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), withoutArFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), adjustmentFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), adjustmentFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [adjustmentFactorHighest](adjustment-factor-highest.md) | [androidJvm]<br>val [adjustmentFactorHighest](adjustment-factor-highest.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [adjustmentFactorLowest](adjustment-factor-lowest.md) | [androidJvm]<br>val [adjustmentFactorLowest](adjustment-factor-lowest.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [withArFactorHighest](with-ar-factor-highest.md) | [androidJvm]<br>val [withArFactorHighest](with-ar-factor-highest.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [withArFactorLowest](with-ar-factor-lowest.md) | [androidJvm]<br>val [withArFactorLowest](with-ar-factor-lowest.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [withoutArFactor](without-ar-factor.md) | [androidJvm]<br>val [withoutArFactor](without-ar-factor.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |