//[appdimens_dynamic](../../../index.md)/[com.appdimens.dynamic.compose](../index.md)/[AppDimens](index.md)/[CalculateAvailableItemCount](-calculate-available-item-count.md)

# CalculateAvailableItemCount

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [CalculateAvailableItemCount](-calculate-available-item-count.md)(itemSize: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), itemPadding: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), direction: DpQualifier = DpQualifier.HEIGHT, modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, onResult: (count: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

Calcula o número máximo de itens que cabem em um contêiner Composável.

#### Parameters

androidJvm

| | |
|---|---|
| itemSize | O tamanho (largura ou altura) de um item. |
| itemPadding | O padding total (em Dp) em torno de cada item (ex: se for 2dp nas laterais, o padding é 4dp). |
| direction | A dimensão do contêiner a ser usada para o cálculo. |
| onResult | Callback que retorna a contagem de itens calculada. |