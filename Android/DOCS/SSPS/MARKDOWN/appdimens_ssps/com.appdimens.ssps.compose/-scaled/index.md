---
title: Scaled
---
//[appdimens_ssps](../../../index.html)/[com.appdimens.ssps.compose](../index.html)/[Scaled](index.html)



# Scaled



[androidJvm]\
@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)



class [Scaled](index.html)

EN The main class for applying dynamic text scaling (Sp) with conditional logic. Allows defining specific Sp values for different screen configurations (UI mode, smallest width, height, width).



PT A classe principal para aplicar escalonamento dinâmico de texto (Sp) com lógica condicional. Permite a definição de valores Sp específicos para diferentes configurações de tela (modo de UI, largura mínima, altura, largura).



## Constructors


| | |
|---|---|
| [Scaled](-scaled.html) | [androidJvm]<br>constructor(initialBaseSp: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html))<br>EN Secondary constructor to start the build chain. |


## Properties


| Name | Summary |
|---|---|
| [hem](hem.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [hem](hem.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN The final [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) value that is resolved in Compose. |
| [hsp](hsp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [hsp](hsp.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN The final [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) value that is resolved in Compose. |
| [sem](sem.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [sem](sem.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN The final [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) value that is resolved in Compose. |
| [ssp](ssp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [ssp](ssp.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN The final [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) value that is resolved in Compose. |
| [wem](wem.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [wem](wem.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN The final [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) value that is resolved in Compose. |
| [wsp](wsp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [wsp](wsp.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN The final [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) value that is resolved in Compose. |


## Functions


| Name | Summary |
|---|---|
| [screen](screen.html) | [androidJvm]<br>fun [screen](screen.html)(type: UiModeType, customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.html)<br>EN Priority 2 rule: UI Mode only. Applicable if the **UI mode** matches.<br>[androidJvm]<br>fun [screen](screen.html)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.html)<br>fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.html)<br>fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.html)<br>EN Overload for `screen` that accepts an `Int` as `customValue`.<br>[androidJvm]<br>fun [screen](screen.html)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.html)<br>EN Priority 3 rule: DP Qualifier only. Applicable if the screen is **greater than or equal to** the qualifier `value`.<br>[androidJvm]<br>fun [screen](screen.html)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.html)<br>EN Priority 1 rule: Combination of UI Mode and DP Qualifier. Applicable if the **UI mode** matches AND the screen is **greater than or equal to** the `qualifierValue`. |
