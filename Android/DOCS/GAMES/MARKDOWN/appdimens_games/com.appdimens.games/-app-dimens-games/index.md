---
title: AppDimensGames
---
//[appdimens_games](../../../index.html)/[com.appdimens.games](../index.html)/[AppDimensGames](index.html)



# AppDimensGames



[androidJvm]\
class [AppDimensGames](index.html)

EN Main AppDimens Games class for Android game development. PT Classe principal AppDimens Games para desenvolvimento de jogos Android.



## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [androidJvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [calculateButtonSize](calculate-button-size.html) | [androidJvm]<br>fun [calculateButtonSize](calculate-button-size.html)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 48.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates button size for games. |
| [calculateDimension](calculate-dimension.html) | [androidJvm]<br>fun [calculateDimension](calculate-dimension.html)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: [GameDimensionType](../-game-dimension-type/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates a dimension value using the specified type. |
| [calculateEnemySize](calculate-enemy-size.html) | [androidJvm]<br>fun [calculateEnemySize](calculate-enemy-size.html)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 32.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates enemy size for games. |
| [calculatePlayerSize](calculate-player-size.html) | [androidJvm]<br>fun [calculatePlayerSize](calculate-player-size.html)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 64.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates player size for games. |
| [calculateRectangle](calculate-rectangle.html) | [androidJvm]<br>fun [calculateRectangle](calculate-rectangle.html)(rectangle: [GameRectangle](../-game-rectangle/index.html), type: [GameDimensionType](../-game-dimension-type/index.html)): [GameRectangle](../-game-rectangle/index.html)<br>EN Calculates a rectangle using the specified type. |
| [calculateTextSize](calculate-text-size.html) | [androidJvm]<br>fun [calculateTextSize](calculate-text-size.html)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 16.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates text size for games. |
| [calculateUISize](calculate-u-i-size.html) | [androidJvm]<br>fun [calculateUISize](calculate-u-i-size.html)(baseSize: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 24.0f): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates UI overlay size for games. |
| [calculateVector2D](calculate-vector2-d.html) | [androidJvm]<br>fun [calculateVector2D](calculate-vector2-d.html)(vector: [GameVector2D](../-game-vector2-d/index.html), type: [GameDimensionType](../-game-dimension-type/index.html)): [GameVector2D](../-game-vector2-d/index.html)<br>EN Calculates a 2D vector using the specified type. |
| [clearCache](clear-cache.html) | [androidJvm]<br>fun [clearCache](clear-cache.html)()<br>EN Clears all cached calculations. |
| [configurePerformance](configure-performance.html) | [androidJvm]<br>fun [configurePerformance](configure-performance.html)(settings: [GamePerformanceSettings](../-game-performance-settings/index.html))<br>EN Configures performance settings for the games library. |
| [getPerformanceSettings](get-performance-settings.html) | [androidJvm]<br>fun [getPerformanceSettings](get-performance-settings.html)(): [GamePerformanceSettings](../-game-performance-settings/index.html)<br>EN Gets the current performance settings. |
| [getScreenConfig](get-screen-config.html) | [androidJvm]<br>fun [getScreenConfig](get-screen-config.html)(): [GameScreenConfig](../-game-screen-config/index.html)?<br>EN Gets the current screen configuration. |
| [initialize](initialize.html) | [androidJvm]<br>fun [initialize](initialize.html)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Initializes the AppDimens Games library. |
| [isInitialized](is-initialized.html) | [androidJvm]<br>fun [isInitialized](is-initialized.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Checks if the library is initialized. |
| [shutdown](shutdown.html) | [androidJvm]<br>fun [shutdown](shutdown.html)()<br>EN Shuts down the AppDimens Games library. PT Desliga a biblioteca AppDimens Games. |
| [updateScreenConfiguration](update-screen-configuration.html) | [androidJvm]<br>fun [updateScreenConfiguration](update-screen-configuration.html)()<br>EN Updates the screen configuration. PT Atualiza a configuração da tela. |
