---
layout: default
title: "calculateAvailableItemCount"
---

# calculateAvailableItemCount

[androidJvm]\
fun [calculateAvailableItemCount](calculate-available-item-count.md)(containerSizePx: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), itemSizeDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), itemMarginDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

EN Calculates the maximum number of items that can fit in a container, given the container size in PX.

#### Return

The calculated item count (Int).

PT Calcula o número máximo de itens que cabem em um contêiner, dado o tamanho do contêiner em PX.

A contagem de itens calculada (Int).

#### Parameters

androidJvm

| | |
|---|---|
| containerSizePx | O tamanho (largura ou altura) do contêiner em Pixels (PX). |
| itemSizeDp | O tamanho (largura ou altura) de um item em Dp. |
| itemMarginDp | O padding total (em Dp) em torno de cada item. |
| resources | Os Resources do Context, para conversão Dp -> Px. |