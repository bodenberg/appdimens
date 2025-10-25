---
title: com.appdimens.ssps.compose
---
//[appdimens_ssps](../../index.html)/[com.appdimens.ssps.compose](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [CustomSpEntry](-custom-sp-entry/index.html) | [androidJvm]<br>data class [CustomSpEntry](-custom-sp-entry/index.html)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))<br>EN Represents a custom text dimension (Sp) configuration entry. Used to define specific text (Sp) values based on the UI mode (e.g., car, TV), DP qualifier (e.g., smallest width), and priority. |
| [Scaled](-scaled/index.html) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>class [Scaled](-scaled/index.html)<br>EN The main class for applying dynamic text scaling (Sp) with conditional logic. Allows defining specific Sp values for different screen configurations (UI mode, smallest width, height, width). |


## Properties


| Name | Summary |
|---|---|
| [hem](hem.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[hem](hem.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE) using the **Height** qualifier. Useful for text scaling based on the screen height (h). |
| [hsp](hsp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[hsp](hsp.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) using the **Height** qualifier. Useful for text scaling based on the screen height (h). |
| [sem](sem.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[sem](sem.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE) using the **Smallest Width** qualifier. Useful for text scaling based on the most limiting dimension (sw). |
| [ssp](ssp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[ssp](ssp.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) using the **Smallest Width** qualifier. Useful for text scaling based on the most limiting dimension (sw). |
| [wem](wem.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[wem](wem.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE) using the **Width** qualifier. Useful for text scaling based on the screen width (w). |
| [wsp](wsp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[wsp](wsp.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN Composable extension for Int that returns a dynamically scaled TextUnit (Sp) using the **Width** qualifier. Useful for text scaling based on the screen width (w). |


## Functions


| Name | Summary |
|---|---|
| [fromConfiguration](from-configuration.html) | [androidJvm]<br>fun [fromConfiguration](from-configuration.html)(uiMode: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): UiModeType<br>EN Maps the UI mode mask from the Android Configuration to the UiModeType enum. |
| [scaledSp](scaled-sp.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html).[scaledSp](scaled-sp.html)(): [Scaled](-scaled/index.html)<br>EN Starts the `Scaled` build chain from a `TextUnit`.<br>[androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[scaledSp](scaled-sp.html)(): [Scaled](-scaled/index.html)<br>EN Starts the `Scaled` build chain from an `Int` (converted to Sp). |
| [toDynamicScaledSp](to-dynamic-scaled-sp.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledSp](to-dynamic-scaled-sp.html)(qualifier: DpQualifier, fontScale: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>EN The main logic for applying dynamic scaling. Tries to find a pre-calculated dimension resource (e.g., `_16sdp`) and uses it to get a scaled Sp value. |
