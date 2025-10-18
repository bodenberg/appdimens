//[appdimens_games](../../../index.md)/[com.appdimens.games](../index.md)/[AppDimensGames](index.md)

# AppDimensGames

[androidJvm]\
class [AppDimensGames](index.md)

EN Main AppDimens Games class for Android game development. PT Classe principal AppDimens Games para desenvolvimento de jogos Android.

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [calculateButtonSize](calculate-button-size.md) | [androidJvm]<br>fun [calculateButtonSize](calculate-button-size.md)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 48.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates button size for games. |
| [calculateDimension](calculate-dimension.md) | [androidJvm]<br>fun [calculateDimension](calculate-dimension.md)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: [GameDimensionType](../-game-dimension-type/index.md)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates a dimension value using the specified type. |
| [calculateEnemySize](calculate-enemy-size.md) | [androidJvm]<br>fun [calculateEnemySize](calculate-enemy-size.md)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 32.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates enemy size for games. |
| [calculatePlayerSize](calculate-player-size.md) | [androidJvm]<br>fun [calculatePlayerSize](calculate-player-size.md)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 64.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates player size for games. |
| [calculateRectangle](calculate-rectangle.md) | [androidJvm]<br>fun [calculateRectangle](calculate-rectangle.md)(rectangle: [GameRectangle](../-game-rectangle/index.md), type: [GameDimensionType](../-game-dimension-type/index.md)): [GameRectangle](../-game-rectangle/index.md)<br>EN Calculates a rectangle using the specified type. |
| [calculateTextSize](calculate-text-size.md) | [androidJvm]<br>fun [calculateTextSize](calculate-text-size.md)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 16.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates text size for games. |
| [calculateUISize](calculate-u-i-size.md) | [androidJvm]<br>fun [calculateUISize](calculate-u-i-size.md)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 24.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates UI overlay size for games. |
| [calculateVector2D](calculate-vector2-d.md) | [androidJvm]<br>fun [calculateVector2D](calculate-vector2-d.md)(vector: [GameVector2D](../-game-vector2-d/index.md), type: [GameDimensionType](../-game-dimension-type/index.md)): [GameVector2D](../-game-vector2-d/index.md)<br>EN Calculates a 2D vector using the specified type. |
| [getScreenConfig](get-screen-config.md) | [androidJvm]<br>fun [getScreenConfig](get-screen-config.md)(): [GameScreenConfig](../-game-screen-config/index.md)?<br>EN Gets the current screen configuration. |
| [initialize](initialize.md) | [androidJvm]<br>fun [initialize](initialize.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Initializes the AppDimens Games library. |
| [isInitialized](is-initialized.md) | [androidJvm]<br>fun [isInitialized](is-initialized.md)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Checks if the library is initialized. |
| [shutdown](shutdown.md) | [androidJvm]<br>fun [shutdown](shutdown.md)()<br>EN Shuts down the AppDimens Games library. PT Desliga a biblioteca AppDimens Games. |
| [updateScreenConfiguration](update-screen-configuration.md) | [androidJvm]<br>fun [updateScreenConfiguration](update-screen-configuration.md)()<br>EN Updates the screen configuration. PT Atualiza a configuração da tela. |