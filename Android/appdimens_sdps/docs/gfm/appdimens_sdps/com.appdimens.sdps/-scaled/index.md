//[appdimens_sdps](../../../index.md)/[com.appdimens.sdps](../index.md)/[Scaled](index.md)

# Scaled

[androidJvm]\
@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)

class [Scaled](index.md)

Classe [Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html) que permite a definição de dimensões customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).

O valor [dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/package-summary.html) é resolvido na composição (Compose) e usa o valor base ou um valor customizado, aplicando o dimensionamento dinâmico no final.

## Constructors

| | |
|---|---|
| [Scaled](-scaled.md) | [androidJvm]<br>constructor(initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)) |

## Properties

| Name | Summary |
|---|---|
| [hdp](hdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [hdp](hdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) |
| [sdp](sdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [sdp](sdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>O valor final [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) que é resolvida no Compose. |
| [wdp](wdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [wdp](wdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) |

## Functions

| Name | Summary |
|---|---|
| [screen](screen.md) | [androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)<br>Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).<br>[androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>[androidJvm]<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)<br>Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.<br>[androidJvm]<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)<br>Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w). |