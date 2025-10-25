---
layout: default
title: "screen"
---

# screen

[androidJvm]
fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)

EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w).

PT Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w).

[androidJvm]
fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

EN Priority 1: Most specific qualifier - Combines UiModeType AND Dp Qualifier (sw, h, w). This is an overload that accepts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) for `customValue`.

PT Prioridade 1: Qualificador mais específico - Combina UiModeType E Qualificador de Dp (sw, h, w). Esta é uma sobrecarga que aceita um [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) para `customValue`.

[androidJvm]
fun [screen](screen.md)(type: UiModeType, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)

EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH).

PT Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH).

[androidJvm]
fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

EN Priority 2: UiModeType qualifier (e.g., TELEVISION, WATCH). This is an overload that accepts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) for `customValue`.

PT Prioridade 2: Qualificador de UiModeType (e.g., TELEVISION, WATCH). Esta é uma sobrecarga que aceita um [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) para `customValue`.

[androidJvm]
fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Scaled](index.md)

EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction.

PT Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType.

[androidJvm]
fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Scaled](index.md)

EN Priority 3: Dp qualifier (sw, h, w) without UiModeType restriction. This is an overload that accepts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) for `customValue`.

PT Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de UiModeType. Esta é uma sobrecarga que aceita um [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) para `customValue`.