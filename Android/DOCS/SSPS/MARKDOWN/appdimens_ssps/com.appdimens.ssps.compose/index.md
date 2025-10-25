---
layout: default
title: "Package-level declarations"
category: ssps
permalink: /SSPS/MARKDOWN/appdimens_ssps/com.appdimens.ssps.compose/index.html
---

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [CustomSpEntry](-custom-sp-entry/index.md) | [androidJvm]<br>data class [CustomSpEntry](-custom-sp-entry/index.md)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))<br>EN Represents a custom text dimension (Sp) configuration entry. Used to define specific text (Sp) values based on the UI mode (e.g., car, TV), DP qualifier (e.g., smallest width), and priority. |
| [Scaled](-scaled/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>class [Scaled](-scaled/index.md)<br>EN The main class for applying dynamic text scaling (Sp) with conditional logic. Allows defining specific Sp values for different screen configurations (UI mode, smallest width, height, width). |

## Properties

| Name | Summary |
|---|---|
| [hem](hem.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[hem](hem.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE) using the **Height** qualifier. Useful for text scaling based on the screen height (h). |
| [hsp](hsp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[hsp](hsp.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) using the **Height** qualifier. Useful for text scaling based on the screen height (h). |
| [sem](sem.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[sem](sem.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE) using the **Smallest Width** qualifier. Useful for text scaling based on the most limiting dimension (sw). |
| [ssp](ssp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[ssp](ssp.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) using the **Smallest Width** qualifier. Useful for text scaling based on the most limiting dimension (sw). |
| [wem](wem.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[wem](wem.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE) using the **Width** qualifier. Useful for text scaling based on the screen width (w). |
| [wsp](wsp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[wsp](wsp.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) using the **Width** qualifier. Useful for text scaling based on the screen width (w). |

## Functions

| Name | Summary |
|---|---|
| [fromConfiguration](from-configuration.md) | [androidJvm]<br>fun [fromConfiguration](from-configuration.md)(uiMode: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): UiModeType<br>EN Maps the UI mode mask from the Android Configuration to the UiModeType enum. |
| [scaledSp](scaled-sp.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html).[scaledSp](scaled-sp.md)(): [Scaled](-scaled/index.md)<br>EN Starts the `Scaled` build chain from a `TextUnit`.<br>[androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[scaledSp](scaled-sp.md)(): [Scaled](-scaled/index.md)<br>EN Starts the `Scaled` build chain from an `Int` (converted to Sp). |
| [toDynamicScaledSp](to-dynamic-scaled-sp.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledSp](to-dynamic-scaled-sp.md)(qualifier: DpQualifier, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN The main logic for applying dynamic scaling. Tries to find a pre-calculated dimension resource (e.g., `_16sdp`) and uses it to get a scaled Sp value. |