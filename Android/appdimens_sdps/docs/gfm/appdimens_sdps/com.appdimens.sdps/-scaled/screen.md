//[appdimens_sdps](../../../index.md)/[com.appdimens.sdps](../index.md)/[Scaled](index.md)/[screen](screen.md)

# screen

[androidJvm]\
fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)

Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).

[androidJvm]\
fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

[androidJvm]\
fun [screen](screen.md)(type: UiModeType, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)

Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).

[androidJvm]\
fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)

Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.