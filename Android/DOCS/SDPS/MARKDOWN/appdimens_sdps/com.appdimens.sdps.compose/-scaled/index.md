---
layout: default
title: "Scaled"
---

# Scaled

[androidJvm]\
@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)

class [Scaled](index.md)

EN A [Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html) class that allows defining custom dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width).

The [dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/package-summary.html) value is resolved at composition (Compose) and uses the base value or a custom value, applying dynamic scaling at the end.

PT Classe [Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html) que permite a definição de dimensões customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width).

O valor [dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/package-summary.html) é resolvido na composição (Compose) e usa o valor base ou um valor customizado, aplicando o dimensionamento dinâmico no final.

## Constructors

| | |
|---|---|
| [Scaled](-scaled.md) | [androidJvm]<br>constructor(initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)) |

## Properties

| Name | Summary |
|---|---|
| [hdp](hdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [hdp](hdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN The final [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) value that is resolved in Compose. |
| [sdp](sdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [sdp](sdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN The final [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) value that is resolved in Compose. |
| [wdp](wdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [wdp](wdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN The final [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) value that is resolved in Compose. |

## Functions

| Name | Summary |
|---|---|
| [screen](screen.md) | [androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)<br>EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).<br>[androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH). This is an overload that accepts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) for `customValue`.<br>[androidJvm]<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)<br>EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.<br>[androidJvm]<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction. This is an overload that accepts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) for `customValue`.<br>[androidJvm]<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)<br>EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).<br>[androidJvm]<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)<br>EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w). This is an overload that accepts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) for `customValue`. |