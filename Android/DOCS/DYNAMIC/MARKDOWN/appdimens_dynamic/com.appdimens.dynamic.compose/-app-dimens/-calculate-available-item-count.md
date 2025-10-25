---
layout: default
title: "CalculateAvailableItemCount"
---

# CalculateAvailableItemCount

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [CalculateAvailableItemCount](-calculate-available-item-count.md)(itemSize: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), itemPadding: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), direction: DpQualifier = DpQualifier.HEIGHT, modifier: [Modifier](https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier.html) = Modifier, onResult: (count: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) -&gt; [Unit](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-unit/index.html))

EN Calculates the maximum number of items that can fit in a Composable container.

PT Calcula o número máximo de itens que cabem em um contêiner Composável.

#### Parameters

androidJvm

| | |
|---|---|
| itemSize | The size (width or height) of an item. |
| itemPadding | The total padding (in Dp) around each item (e.g., if there is 2dp on the sides, the padding is 4dp). |
| direction | The container dimension to be used for the calculation. |
| onResult | Callback that returns the calculated item count. |