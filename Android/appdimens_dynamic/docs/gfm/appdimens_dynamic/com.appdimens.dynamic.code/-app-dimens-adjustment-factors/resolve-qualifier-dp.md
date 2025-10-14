//[appdimens_dynamic](../../../index.md)/[com.appdimens.dynamic.code](../index.md)/[AppDimensAdjustmentFactors](index.md)/[resolveQualifierDp](resolve-qualifier-dp.md)

# resolveQualifierDp

[androidJvm]\
fun [resolveQualifierDp](resolve-qualifier-dp.md)(customDpMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;DpQualifierEntry, [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;, smallestWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

Função auxiliar que isola a lógica de busca e seleção do valor Dp customizado através dos Qualificadores (SW, H, W).

#### Return

O valor Dp customizado ou o valor inicial (em Float).

#### Parameters

androidJvm

| | |
|---|---|
| customDpMap | Mapa de qualificadores de Dp. |
| smallestWidthDp | smallestScreenWidthDp da configuração atual. |
| currentScreenWidthDp | Largura da tela em Dp. |
| currentScreenHeightDp | Altura da tela em Dp. |
| initialBaseDp | Valor Dp inicial (em Float). |