//[appdimens_dynamic](../../../README.md)/[com.appdimens.dynamic.code](../README.md)/[AppDimensDynamic](README.md)/[screen](screen.md)

# screen

[androidJvm]\
fun [screen](screen.md)(type: UiModeType, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](README.md)

EN Sets a custom dimension value for a specific UI mode.

#### Return

The `AppDimensDynamic` instance for chaining.

PT Define um valor de dimensão customizado para um modo de UI específico.

A instância `AppDimensDynamic` para encadeamento.

#### Parameters

androidJvm

| | |
|---|---|
| type | O modo de UI (`UiModeType`). |
| customValue | O valor de dimensão customizado em Dp. |

[androidJvm]\
fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](README.md)

fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](README.md)

fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](README.md)

[androidJvm]\
fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensDynamic](README.md)

EN Sets a custom dimension for the intersection of a UI mode and a screen qualifier.

#### Return

The `AppDimensDynamic` instance for chaining.

PT Define uma dimensão customizada para a interseção de um modo de UI e um qualificador de tela.

A instância `AppDimensDynamic` para encadeamento.

#### Parameters

androidJvm

| | |
|---|---|
| uiModeType | O modo de UI (`UiModeType`). |
| qualifierType | O tipo de qualificador (`DpQualifier`). |
| qualifierValue | O valor do qualificador (ex: 600 para sw600dp). |
| customValue | O valor de dimensão customizado em Dp. |

[androidJvm]\
fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensDynamic](README.md)

EN Sets a custom dimension value for a specific screen qualifier.

#### Return

The `AppDimensDynamic` instance for chaining.

PT Define um valor de dimensão customizado para um qualificador de tela específico.

A instância `AppDimensDynamic` para encadeamento.

#### Parameters

androidJvm

| | |
|---|---|
| type | O tipo de qualificador (`DpQualifier`). |
| value | O valor do qualificador (ex: 600 para sw600dp). |
| customValue | O valor da dimensão customizada em Dp. |