---
title: screen
---
//[appdimens_ssps](../../../index.html)/[com.appdimens.ssps.compose](../index.html)/[Scaled](index.html)/[screen](screen.html)



# screen



[androidJvm]\
fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.html)



EN Priority 1 rule: Combination of UI Mode and DP Qualifier. Applicable if the **UI mode** matches AND the screen is **greater than or equal to** the `qualifierValue`.



PT Regra de prioridade 1: Combinação de Modo de UI e Qualificador de DP. Aplicável se o **modo de UI** corresponder E a tela for **maior ou igual** ao `qualifierValue`.





[androidJvm]\
fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.html)

fun [screen](screen.html)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.html)

fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.html)



EN Overload for `screen` that accepts an `Int` as `customValue`.



PT Sobrecarga de `screen` que aceita um `Int` como `customValue`.





[androidJvm]\
fun [screen](screen.html)(type: UiModeType, customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.html)



EN Priority 2 rule: UI Mode only. Applicable if the **UI mode** matches.



PT Regra de prioridade 2: Apenas Modo de UI. Aplicável se o **modo de UI** corresponder.





[androidJvm]\
fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.html)



EN Priority 3 rule: DP Qualifier only. Applicable if the screen is **greater than or equal to** the qualifier `value`.



PT Regra de prioridade 3: Apenas Qualificador de DP. Aplicável se a tela for **maior ou igual** ao `value` do qualificador.



