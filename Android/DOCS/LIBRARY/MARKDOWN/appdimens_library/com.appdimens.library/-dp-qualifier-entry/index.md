---
layout: default
title: "DpQualifierEntry"
---

# DpQualifierEntry

[androidJvm]
data class [DpQualifierEntry](index.md)(val type: [DpQualifier](../-dp-qualifier/index.md), val value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

EN Represents a custom qualifier entry, combining the type and the minimum DP value for the custom adjustment to be applied.

PT Representa uma entrada de qualificador customizado, combinando o tipo e o valor mínimo de DP para que o ajuste customizado seja aplicado.

## Constructors

| | |
|---|---|
| [DpQualifierEntry](-dp-qualifier-entry.md) | [androidJvm]<br>constructor(type: [DpQualifier](../-dp-qualifier/index.md), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [type](type.md) | [androidJvm]<br>val [type](type.md): [DpQualifier](../-dp-qualifier/index.md)<br>EN The dimension type (SMALL_WIDTH, HEIGHT, WIDTH).     PT O tipo de dimensão (SMALL_WIDTH, HEIGHT, WIDTH). |
| [value](value.md) | [androidJvm]<br>val [value](value.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN The minimum dimension in DP to activate this qualifier (e.g., 600).     PT A dimensão mínima em DP para ativar este qualificador (ex: 600). |