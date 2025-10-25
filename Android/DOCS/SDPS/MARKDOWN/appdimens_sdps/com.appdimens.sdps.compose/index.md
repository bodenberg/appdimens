---
title: com.appdimens.sdps.compose
---
//[appdimens_sdps](../../index.html)/[com.appdimens.sdps.compose](index.html)



# Package-level declarations



## Types


| Name | Summary |
|---|---|
| [CustomDpEntry](-custom-dp-entry/index.html) | [androidJvm]<br>data class [CustomDpEntry](-custom-dp-entry/index.html)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))<br>EN Represents a custom dimension entry with qualifiers and priority. Used by the [Scaled](-scaled/index.html) class to define specific values for screen conditions. |
| [Scaled](-scaled/index.html) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>class [Scaled](-scaled/index.html)<br>EN A [Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html) class that allows defining custom dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width). |


## Properties


| Name | Summary |
|---|---|
| [hdp](hdp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[hdp](hdp.html): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**. Usage example: `32.hdp`. |
| [sdp](sdp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[sdp](sdp.html): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Extension for Dp with dynamic scaling based on the **Smallest Width (swDP)**. Usage example: `16.sdp`. |
| [wdp](wdp.html) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[wdp](wdp.html): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**. Usage example: `100.wdp`. |


## Functions


| Name | Summary |
|---|---|
| [fromConfiguration](from-configuration.html) | [androidJvm]<br>fun [fromConfiguration](from-configuration.html)(uiMode: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): UiModeType<br>EN Maps the [uiMode](from-configuration.html) value from the configuration to the library's UiModeType enum. |
| [scaledDp](scaled-dp.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html).[scaledDp](scaled-dp.html)(): [Scaled](-scaled/index.html)<br>EN Starts the build chain for the custom dimension [Scaled](-scaled/index.html) from a base [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html). Usage example: `100.dp.scaled().screen(...)`.<br>[androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[scaledDp](scaled-dp.html)(): [Scaled](-scaled/index.html)<br>EN Starts the build chain for the custom dimension [Scaled](-scaled/index.html) from a base [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html). Usage example: `100.scaled().screen(...)`. |
| [toDynamicScaledDp](to-dynamic-scaled-dp.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledDp](to-dynamic-scaled-dp.html)(qualifier: DpQualifier): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Converts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) (the base Dp value) into a dynamically scaled [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html). The logic tries to find a corresponding dimension resource in the 'res/values/' folder. |
