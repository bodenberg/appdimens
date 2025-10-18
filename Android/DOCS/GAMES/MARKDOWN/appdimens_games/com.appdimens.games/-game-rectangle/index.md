//[appdimens_games](../../../index.md)/[com.appdimens.games](../index.md)/[GameRectangle](index.md)

# GameRectangle

[androidJvm]\
data class [GameRectangle](index.md)(val x: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val y: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val width: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), val height: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html))

EN Rectangle for game bounds and viewports. PT Retângulo para limites e viewports de jogo.

## Constructors

| | |
|---|---|
| [GameRectangle](-game-rectangle.md) | [androidJvm]<br>constructor(x: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), y: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), width: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), height: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [height](height.md) | [androidJvm]<br>val [height](height.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [width](width.md) | [androidJvm]<br>val [width](width.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [x](x.md) | [androidJvm]<br>val [x](x.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |
| [y](y.md) | [androidJvm]<br>val [y](y.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) |

## Functions

| Name | Summary |
|---|---|
| [center](center.md) | [androidJvm]<br>fun [center](center.md)(): [GameVector2D](../-game-vector2-d/index.md) |
| [contains](contains.md) | [androidJvm]<br>fun [contains](contains.md)(point: [GameVector2D](../-game-vector2-d/index.md)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) |
| [intersection](intersection.md) | [androidJvm]<br>fun [intersection](intersection.md)(other: [GameRectangle](index.md)): [GameRectangle](index.md) |