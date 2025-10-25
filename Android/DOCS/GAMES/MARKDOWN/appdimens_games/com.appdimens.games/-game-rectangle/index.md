---
title: GameRectangle
---
//[appdimens_games](../../../index.html)/[com.appdimens.games](../index.html)/[GameRectangle](index.html)



# GameRectangle



[androidJvm]\
data class [GameRectangle](index.html)(val x: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val y: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val width: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val height: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))

EN Rectangle for game bounds and viewports. PT Retângulo para limites e viewports de jogo.



## Constructors


| | |
|---|---|
| [GameRectangle](-game-rectangle.html) | [androidJvm]<br>constructor(x: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), y: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), width: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), height: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [height](height.html) | [androidJvm]<br>val [height](height.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [width](width.html) | [androidJvm]<br>val [width](width.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [x](x.html) | [androidJvm]<br>val [x](x.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [y](y.html) | [androidJvm]<br>val [y](y.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |


## Functions


| Name | Summary |
|---|---|
| [center](center.html) | [androidJvm]<br>fun [center](center.html)(): [GameVector2D](../-game-vector2-d/index.html) |
| [contains](contains.html) | [androidJvm]<br>fun [contains](contains.html)(point: [GameVector2D](../-game-vector2-d/index.html)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [intersection](intersection.html) | [androidJvm]<br>fun [intersection](intersection.html)(other: [GameRectangle](index.html)): [GameRectangle](index.html) |
