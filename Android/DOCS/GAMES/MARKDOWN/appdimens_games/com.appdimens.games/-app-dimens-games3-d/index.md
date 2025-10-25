---
title: AppDimensGames3D
---
//[appdimens_games](../../../index.html)/[com.appdimens.games](../index.html)/[AppDimensGames3D](index.html)



# AppDimensGames3D



[androidJvm]\
class [AppDimensGames3D](index.html)

EN Main 3D games optimization manager for Android. PT Gerenciador principal de otimizações para jogos 3D no Android.



## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [androidJvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [calculateHUDElement](calculate-h-u-d-element.html) | [androidJvm]<br>fun [calculateHUDElement](calculate-h-u-d-element.html)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: [UIElementType](../-u-i-element-type/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates HUD element dimensions optimized for 3D games. |
| [calculateMenuElement](calculate-menu-element.html) | [androidJvm]<br>fun [calculateMenuElement](calculate-menu-element.html)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: [UIElementType](../-u-i-element-type/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates menu element dimensions optimized for 3D games. |
| [calculateUIElement](calculate-u-i-element.html) | [androidJvm]<br>fun [calculateUIElement](calculate-u-i-element.html)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: [UIElementType](../-u-i-element-type/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Calculates UI element dimensions optimized for 3D games. |
| [calculateUIElementAsync](calculate-u-i-element-async.html) | [androidJvm]<br>fun [calculateUIElementAsync](calculate-u-i-element-async.html)(baseValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), type: [UIElementType](../-u-i-element-type/index.html), callback: ([Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>EN Calculates UI element dimensions asynchronously. |
| [disableEmergencyMode](disable-emergency-mode.html) | [androidJvm]<br>fun [disableEmergencyMode](disable-emergency-mode.html)()<br>EN Disables emergency mode. PT Desabilita modo de emergência. |
| [enableEmergencyMode](enable-emergency-mode.html) | [androidJvm]<br>fun [enableEmergencyMode](enable-emergency-mode.html)()<br>EN Enables emergency mode for performance optimization. PT Habilita modo de emergência para otimização de performance. |
| [generatePerformanceReport](generate-performance-report.html) | [androidJvm]<br>fun [generatePerformanceReport](generate-performance-report.html)(): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>EN Generates a performance report. PT Gera um relatório de performance. |
| [getCurrentSettings](get-current-settings.html) | [androidJvm]<br>fun [getCurrentSettings](get-current-settings.html)(): [Game3DPerformanceSettings](../-game3-d-performance-settings/index.html)<br>EN Gets current performance settings. |
| [getPerformanceMetrics](get-performance-metrics.html) | [androidJvm]<br>fun [getPerformanceMetrics](get-performance-metrics.html)(): [Game3DPerformanceMetrics](../-game3-d-performance-metrics/index.html)<br>EN Gets current performance metrics. |
| [initializeFor3D](initialize-for3-d.html) | [androidJvm]<br>fun [initializeFor3D](initialize-for3-d.html)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), settings: [Game3DPerformanceSettings](../-game3-d-performance-settings/index.html))<br>EN Initializes the 3D games optimization system. |
| [isEmergencyModeActive](is-emergency-mode-active.html) | [androidJvm]<br>fun [isEmergencyModeActive](is-emergency-mode-active.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Checks if emergency mode is active. |
| [logPerformanceStats](log-performance-stats.html) | [androidJvm]<br>fun [logPerformanceStats](log-performance-stats.html)()<br>EN Logs performance statistics. PT Registra estatísticas de performance. |
| [setPerformanceCallback](set-performance-callback.html) | [androidJvm]<br>fun [setPerformanceCallback](set-performance-callback.html)(callback: ([Game3DPerformanceMetrics](../-game3-d-performance-metrics/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))<br>EN Sets a performance callback. |
| [shutdown](shutdown.html) | [androidJvm]<br>fun [shutdown](shutdown.html)()<br>EN Shuts down the 3D optimization system. PT Desliga o sistema de otimização 3D. |
| [updateSettings](update-settings.html) | [androidJvm]<br>fun [updateSettings](update-settings.html)(settings: [Game3DPerformanceSettings](../-game3-d-performance-settings/index.html))<br>EN Updates performance settings. |
