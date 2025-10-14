//[appdimens_sdps](../../index.md)/[com.appdimens.sdps](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [CustomDpEntry](-custom-dp-entry/index.md) | [androidJvm]<br>data class [CustomDpEntry](-custom-dp-entry/index.md)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))<br>Representa uma entrada de dimensão customizada com qualificadores e prioridade. Usada pela classe [Scaled](-scaled/index.md) para definir valores específicos para condições de tela. |
| [Scaled](-scaled/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>class [Scaled](-scaled/index.md)<br>Classe [Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html) que permite a definição de dimensões customizadas baseadas em qualificadores de tela (UiModeType, Largura, Altura, Smallest Width). |

## Properties

| Name | Summary |
|---|---|
| [hdp](hdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[hdp](hdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**. Exemplo de uso: `32.hdp`. |
| [sdp](sdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[sdp](sdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**. Exemplo de uso: `16.sdp`. |
| [wdp](wdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[wdp](wdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**. Exemplo de uso: `100.wdp`. |

## Functions

| Name | Summary |
|---|---|
| [fromConfiguration](from-configuration.md) | [androidJvm]<br>fun [fromConfiguration](from-configuration.md)(uiMode: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): UiModeType<br>Mapeia o valor de [uiMode](from-configuration.md) da configuração para o enum UiModeType da biblioteca. |
| [scaledDp](scaled-dp.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html).[scaledDp](scaled-dp.md)(): [Scaled](-scaled/index.md)<br>Inicia a cadeia de construção para a dimensão customizada [Scaled](-scaled/index.md) a partir de um [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) base. Exemplo de uso: `100.dp.scaled().screen(...)`<br>[androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[scaledDp](scaled-dp.md)(): [Scaled](-scaled/index.md)<br>Inicia a cadeia de construção para a dimensão customizada [Scaled](-scaled/index.md) a partir de um [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) base. Exemplo de uso: `100.scaled().screen(...)` |
| [toDynamicScaledDp](to-dynamic-scaled-dp.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledDp](to-dynamic-scaled-dp.md)(qualifier: DpQualifier): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>Converte um [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) (o valor base de Dp) em um [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) dinamicamente escalado. |