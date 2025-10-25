---
layout: default
title: "resolveQualifierDp"
category: dynamic
permalink: /DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.compose/-app-dimens-adjustment-factors/resolve-qualifier-dp.html
---

# resolveQualifierDp

[androidJvm]
fun [resolveQualifierDp](resolve-qualifier-dp.md)(customDpMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)<DpQualifierEntry, [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)>, smallestWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)

EN Helper function that isolates the logic for fetching and selecting the custom Dp value through Qualifiers (SW, H, W). This logic should be called inside a 'remember' block for performance.

PT Função auxiliar que isola a lógica de busca e seleção do valor Dp customizado através dos Qualificadores (SW, H, W). Esta lógica deve ser chamada dentro de um 'remember' para ser performática.