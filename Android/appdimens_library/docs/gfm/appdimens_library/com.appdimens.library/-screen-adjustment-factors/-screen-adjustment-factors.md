//[appdimens_library](../../../index.md)/[com.appdimens.library](../index.md)/[ScreenAdjustmentFactors](index.md)/[ScreenAdjustmentFactors](-screen-adjustment-factors.md)

# ScreenAdjustmentFactors

[androidJvm]\
constructor(withArFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), withArFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), withoutArFactor: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), adjustmentFactorLowest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), adjustmentFactorHighest: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))

#### Parameters

androidJvm

| | |
|---|---|
| withArFactor | Fator de escala final, incluindo o ajuste fino do Aspect Ratio (usando a sensibilidade padrão). |
| withoutArFactor | Fator de escala final, usando o incremento base de 0.10f (SEM AR). |
| adjustmentFactor | Fator base de ajuste (calculado a partir da diferença da smallestWidthDp / INCREMENT_DP_STEP). |