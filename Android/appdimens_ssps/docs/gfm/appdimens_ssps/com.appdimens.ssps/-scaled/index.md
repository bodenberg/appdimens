//[appdimens_ssps](../../../index.md)/[com.appdimens.ssps](../index.md)/[Scaled](index.md)

# Scaled

[androidJvm]\
@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)

class [Scaled](index.md)

A classe principal para aplicar escalonamento dinâmico de texto (Sp) com lógica condicional. Permite a definição de valores Sp específicos para diferentes configurações de tela (modo de UI, largura mínima, altura, largura).

## Constructors

| | |
|---|---|
| [Scaled](-scaled.md) | [androidJvm]<br>constructor(initialBaseSp: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html))<br>Construtor secundário para iniciar a cadeia de construção. |

## Properties

| Name | Summary |
|---|---|
| [hem](hem.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [hem](hem.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) |
| [hsp](hsp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [hsp](hsp.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) |
| [sem](sem.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [sem](sem.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) |
| [ssp](ssp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [ssp](ssp.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>O valor final [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) que é resolvida no Compose. |
| [wem](wem.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [wem](wem.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) |
| [wsp](wsp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [wsp](wsp.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html) |

## Functions

| Name | Summary |
|---|---|
| [screen](screen.md) | [androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.md)<br>Regra de prioridade 2: Apenas Modo de UI. Aplicável se o **modo de UI** corresponder.<br>[androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>[androidJvm]<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.md)<br>Regra de prioridade 3: Apenas Qualificador de DP. Aplicável se a tela for **maior ou igual** ao `value` do qualificador.<br>[androidJvm]<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)): [Scaled](index.md)<br>Regra de prioridade 1: Combinação de Modo de UI e Qualificador de DP. Aplicável se o **modo de UI** corresponder E a tela for **maior ou igual** ao `qualifierValue`. |