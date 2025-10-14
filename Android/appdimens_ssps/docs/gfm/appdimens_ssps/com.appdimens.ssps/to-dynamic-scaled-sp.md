//[appdimens_ssps](../../index.md)/[com.appdimens.ssps](index.md)/[toDynamicScaledSp](to-dynamic-scaled-sp.md)

# toDynamicScaledSp

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledSp](to-dynamic-scaled-sp.md)(qualifier: DpQualifier, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)

A lógica principal para aplicar o escalonamento dinâmico. Tenta encontrar um recurso de dimensão pré-calculado (ex: `_16sdp`) e o usa para obter um valor Sp escalado.

#### Receiver

O valor de Sp base (ex: 16 para 16sp).

#### Return

O TextUnit (Sp) escalado dinamicamente, ou o valor base se o recurso não for encontrado.

#### Parameters

androidJvm

| | |
|---|---|
| qualifier | O qualificador usado para determinar o nome do recurso (s, h, w). |