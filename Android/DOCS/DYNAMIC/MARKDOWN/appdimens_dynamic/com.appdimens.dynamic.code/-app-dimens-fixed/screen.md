---
title: screen
---
//[appdimens_dynamic](../../../index.html)/[com.appdimens.dynamic.code](../index.html)/[AppDimensFixed](index.html)/[screen](screen.html)



# screen



[androidJvm]\
fun [screen](screen.html)(type: UiModeType, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.html)



EN Sets a custom dimension value for a specific UI mode.



#### Return



The `AppDimensFixed` instance for chaining.



PT Define um valor de dimensão customizado para um modo de UI específico.



A instância `AppDimensFixed` para encadeamento.



#### Parameters


androidJvm

| | |
|---|---|
| type | O modo de UI (`UiModeType`). |
| customValue | O valor de dimensão customizado em Dp. |





[androidJvm]\
fun [screen](screen.html)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.html)

fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.html)

fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.html)





[androidJvm]\
fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.html)



EN Sets a custom dimension for the intersection of a UI mode and a screen qualifier.



#### Return



The `AppDimensFixed` instance for chaining.



PT Define uma dimensão customizada para a interseção de um modo de UI e um qualificador de tela.



A instância `AppDimensFixed` para encadeamento.



#### Parameters


androidJvm

| | |
|---|---|
| uiModeType | O modo de UI (`UiModeType`). |
| qualifierType | O tipo de qualificador (`DpQualifier`). |
| qualifierValue | O valor do qualificador (ex: 600 para sw600dp). |
| customValue | O valor de dimensão customizado em Dp. |





[androidJvm]\
fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.html)



EN Sets a custom dimension value for a specific screen qualifier.



#### Return



The `AppDimensFixed` instance for chaining.



PT Define um valor de dimensão customizado para um qualificador de tela específico.



A instância `AppDimensFixed` para encadeamento.



#### Parameters


androidJvm

| | |
|---|---|
| type | O tipo de qualificador (`DpQualifier`). |
| value | O valor do qualificador (ex: 600 para sw600dp). |
| customValue | O valor da dimensão customizada em Dp. |



