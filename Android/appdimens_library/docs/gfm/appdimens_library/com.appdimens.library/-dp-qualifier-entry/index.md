//[appdimens_library](../../../index.md)/[com.appdimens.library](../index.md)/[DpQualifierEntry](index.md)

# DpQualifierEntry

data class [DpQualifierEntry](index.md)(val type: [DpQualifier](../-dp-qualifier/index.md), val value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

Representa uma entrada de qualificador customizado, combinando o tipo e o valor mínimo de DP para que o ajuste customizado seja aplicado.

#### Parameters

androidJvm

| | |
|---|---|
| type | O tipo de dimensão (SMALL_WIDTH, HEIGHT, WIDTH). |
| value | A dimensão mínima em DP para ativar este qualificador (ex: 600). |

## Constructors

| | |
|---|---|
| [DpQualifierEntry](-dp-qualifier-entry.md) | [androidJvm]<br>constructor(type: [DpQualifier](../-dp-qualifier/index.md), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [type](type.md) | [androidJvm]<br>val [type](type.md): [DpQualifier](../-dp-qualifier/index.md) |
| [value](value.md) | [androidJvm]<br>val [value](value.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |