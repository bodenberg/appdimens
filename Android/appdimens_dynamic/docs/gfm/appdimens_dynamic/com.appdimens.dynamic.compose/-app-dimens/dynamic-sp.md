//[appdimens_dynamic](../../../index.md)/[com.appdimens.dynamic.compose](../index.md)/[AppDimens](index.md)/[dynamicSp](dynamic-sp.md)

# dynamicSp

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html).[dynamicSp](dynamic-sp.md)(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)

Constrói o TextUnit (Sp) ajustado.

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html).[dynamicSp](dynamic-sp.md)(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)

Aplica o ajuste de dimensão diretamente no TextUnit (Sp).

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[dynamicSp](dynamic-sp.md)(type: ScreenType = ScreenType.LOWEST, ignoreMultiWindows: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)

Converte Float para Dp, aplica o ajuste de dimensão e retorna em Sp.

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[dynamicSp](dynamic-sp.md)(type: ScreenType = ScreenType.LOWEST): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)

Converte Int para Dp, aplica o ajuste de dimensão e retorna em Sp.