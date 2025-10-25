//[appdimens_dynamic](../../../README.md)/[com.appdimens.dynamic.code](../README.md)/[AppDimensAdjustmentFactors](README.md)/[resolveQualifierDp](resolve-qualifier-dp.md)

# resolveQualifierDp

[androidJvm]\
fun [resolveQualifierDp](resolve-qualifier-dp.md)(customDpMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;DpQualifierEntry, [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;, smallestWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Helper function that isolates the logic for searching and selecting the custom Dp value through Qualifiers (SW, H, W).

PT Função auxiliar que isola a lógica de busca e seleção do valor Dp customizado através dos Qualificadores (SW, H, W).

#### Return

The custom Dp value or the initial value (as Float).

#### Parameters

androidJvm

| | |
|---|---|
| customDpMap | Map of Dp qualifiers. |
| smallestWidthDp | smallestScreenWidthDp from the current configuration. |
| currentScreenWidthDp | Screen width in Dp. |
| currentScreenHeightDp | Screen height in Dp. |
| initialBaseDp | Initial Dp value (as Float). |