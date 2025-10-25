---
layout: default
title: "toDynamicScaledDp"
---

# toDynamicScaledDp

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledDp](to-dynamic-scaled-dp.md)(qualifier: DpQualifier): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)

EN Converts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) (the base Dp value) into a dynamically scaled [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html). The logic tries to find a corresponding dimension resource in the 'res/values/' folder.

1. 
   Constructs the resource name based on the value (this) and the qualifier ([qualifier](to-dynamic-scaled-dp.md)).
2. 
   Tries to load the resource via [dimensionResource](https://developer.android.com/reference/kotlin/androidx/compose/ui/res/package-summary.html).
3. 
   If the resource is found (e.g., in `values-sw600dp/dimens.xml`), that value is used.
4. 
   If the resource is not found, the original value is used as a [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) (the default Compose [Int.dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/package-summary.html)).

PT Converte um [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) (o valor base de Dp) em um [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) dinamicamente escalado. A lógica tenta encontrar um recurso de dimensão correspondente na pasta 'res/values/'.

1. 
   Constrói o nome do recurso baseado no valor (this) e no qualificador ([qualifier](to-dynamic-scaled-dp.md)).
2. 
   Tenta carregar o recurso via [dimensionResource](https://developer.android.com/reference/kotlin/androidx/compose/ui/res/package-summary.html).
3. 
   Se o recurso for encontrado (e.g., em `values-sw600dp/dimens.xml`), esse valor é usado.
4. 
   Se o recurso não for encontrado, o valor original é usado como [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) (o [Int.dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/package-summary.html) padrão do Compose).

#### Return

The Dp value loaded from the resource or the base Dp value.

#### Parameters

androidJvm

| | |
|---|---|
| qualifier | The screen qualifier used to construct the resource name (s, h, w). |