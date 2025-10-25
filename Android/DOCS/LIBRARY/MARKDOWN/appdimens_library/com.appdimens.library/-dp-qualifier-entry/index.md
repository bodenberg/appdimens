---
title: DpQualifierEntry
---
//[appdimens_library](../../../index.html)/[com.appdimens.library](../index.html)/[DpQualifierEntry](index.html)



# DpQualifierEntry



[androidJvm]\
data class [DpQualifierEntry](index.html)(val type: [DpQualifier](../-dp-qualifier/index.html), val value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

EN Represents a custom qualifier entry, combining the type and the minimum DP value for the custom adjustment to be applied.



PT Representa uma entrada de qualificador customizado, combinando o tipo e o valor mínimo de DP para que o ajuste customizado seja aplicado.



## Constructors


| | |
|---|---|
| [DpQualifierEntry](-dp-qualifier-entry.html) | [androidJvm]<br>constructor(type: [DpQualifier](../-dp-qualifier/index.html), value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [type](type.html) | [androidJvm]<br>val [type](type.html): [DpQualifier](../-dp-qualifier/index.html)<br>EN The dimension type (SMALL_WIDTH, HEIGHT, WIDTH).     PT O tipo de dimensão (SMALL_WIDTH, HEIGHT, WIDTH). |
| [value](value.html) | [androidJvm]<br>val [value](value.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>EN The minimum dimension in DP to activate this qualifier (e.g., 600).     PT A dimensão mínima em DP para ativar este qualificador (ex: 600). |
