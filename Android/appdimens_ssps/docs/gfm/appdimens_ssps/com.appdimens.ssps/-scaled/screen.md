//[appdimens_ssps](../../../index.md)/[com.appdimens.ssps](../index.md)/[Scaled](index.md)/[screen](screen.md)

# screen

[androidJvm]\
fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.md)

Regra de prioridade 1: Combinação de Modo de UI e Qualificador de DP. Aplicável se o **modo de UI** corresponder E a tela for **maior ou igual** ao `qualifierValue`.

[androidJvm]\
fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

[androidJvm]\
fun [screen](screen.md)(type: UiModeType, customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.md)

Regra de prioridade 2: Apenas Modo de UI. Aplicável se o **modo de UI** corresponder.

[androidJvm]\
fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.md)

Regra de prioridade 3: Apenas Qualificador de DP. Aplicável se a tela for **maior ou igual** ao `value` do qualificador.