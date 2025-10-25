---
layout: default
title: "dynamicPercentageDp"
---

# dynamicPercentageDp

[androidJvm]
fun [dynamicPercentageDp](dynamic-percentage-dp.md)(percentage: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: ScreenType = ScreenType.LOWEST, resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Calculates a dynamic dimension value based on a percentage (0.0 to 1.0) of the screen dimension. Returns the value in Dp (Float).

#### Return

The adjusted value in Dp (Float).

PT Calcula um valor de dimensão dinâmico com base em uma porcentagem (0.0 a 1.0) da dimensão da tela. Retorna o valor em Dp (Float).

O valor ajustado em Dp (Float).

#### Parameters

androidJvm

| | |
|---|---|
| percentage | A porcentagem (0.0f a 1.0f). |
| type | A dimensão da tela a ser usada (LOWEST/HIGHEST). |
| resources | Os Resources do Context. |