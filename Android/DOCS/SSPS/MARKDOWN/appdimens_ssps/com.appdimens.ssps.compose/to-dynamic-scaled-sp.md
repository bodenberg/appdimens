---
layout: default
title: "toDynamicScaledSp"
---

# toDynamicScaledSp

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledSp](to-dynamic-scaled-sp.md)(qualifier: DpQualifier, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)

EN The main logic for applying dynamic scaling. Tries to find a pre-calculated dimension resource (e.g., `_16sdp`) and uses it to get a scaled Sp value.

PT A lógica principal para aplicar o escalonamento dinâmico. Tenta encontrar um recurso de dimensão pré-calculado (ex: `_16sdp`) e o usa para obter um valor Sp escalado.

#### Receiver

The base Sp value (e.g., 16 for 16sp).

#### Return

The dynamically scaled TextUnit (Sp), or the base value if the resource is not found.

#### Parameters

androidJvm

| | |
|---|---|
| qualifier | The qualifier used to determine the resource name (s, h, w). |
| fontScale | A boolean that indicates if the font scale is enabled or not |