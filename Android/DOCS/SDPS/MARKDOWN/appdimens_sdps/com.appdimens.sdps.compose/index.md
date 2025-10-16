//[appdimens_sdps](../../index.md)/[com.appdimens.sdps.compose](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [CustomDpEntry](-custom-dp-entry/index.md) | [androidJvm]<br>data class [CustomDpEntry](-custom-dp-entry/index.md)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))<br>EN Represents a custom dimension entry with qualifiers and priority. Used by the [Scaled](-scaled/index.md) class to define specific values for screen conditions. |
| [Scaled](-scaled/index.md) | [androidJvm]<br>@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)<br>class [Scaled](-scaled/index.md)<br>EN A [Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html) class that allows defining custom dimensions based on screen qualifiers (UiModeType, Width, Height, Smallest Width). |

## Properties

| Name | Summary |
|---|---|
| [hdp](hdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[hdp](hdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Extension for Dp with dynamic scaling based on the **Screen Height (hDP)**. Usage example: `32.hdp`. |
| [sdp](sdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[sdp](sdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Extension for Dp with dynamic scaling based on the **Smallest Width (swDP)**. Usage example: `16.sdp`. |
| [wdp](wdp.md) | [androidJvm]<br>@get:[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>val [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[wdp](wdp.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Extension for Dp with dynamic scaling based on the **Screen Width (wDP)**. Usage example: `100.wdp`. |

## Functions

| Name | Summary |
|---|---|
| [fromConfiguration](from-configuration.md) | [androidJvm]<br>fun [fromConfiguration](from-configuration.md)(uiMode: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): UiModeType<br>EN Maps the [uiMode](from-configuration.md) value from the configuration to the library's UiModeType enum. |
| [scaledDp](scaled-dp.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html).[scaledDp](scaled-dp.md)(): [Scaled](-scaled/index.md)<br>EN Starts the build chain for the custom dimension [Scaled](-scaled/index.md) from a base [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html). Usage example: `100.dp.scaled().screen(...)`.<br>[androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[scaledDp](scaled-dp.md)(): [Scaled](-scaled/index.md)<br>EN Starts the build chain for the custom dimension [Scaled](-scaled/index.md) from a base [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html). Usage example: `100.scaled().screen(...)`. |
| [toDynamicScaledDp](to-dynamic-scaled-dp.md) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledDp](to-dynamic-scaled-dp.md)(qualifier: DpQualifier): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Converts an [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) (the base Dp value) into a dynamically scaled [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html). The logic tries to find a corresponding dimension resource in the 'res/values/' folder. |