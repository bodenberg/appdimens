---
title: UiModeType
---
//[appdimens_library](../../../index.html)/[com.appdimens.library](../index.html)/[UiModeType](index.html)



# UiModeType



[androidJvm]\
enum [UiModeType](index.html) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)&lt;[UiModeType](index.html)&gt; 

EN Defines the Android UI Mode Types for dimension customization, based on [Configuration.uiMode](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html#uimode).



PT Define os tipos de modo de interface do usuário (UI Mode Type) do Android para customização de dimensões, com base em [Configuration.uiMode](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html#uimode).



## Entries


| | |
|---|---|
| [NORMAL](-n-o-r-m-a-l/index.html) | [androidJvm]<br>[NORMAL](-n-o-r-m-a-l/index.html)<br>EN Default Phone/Tablet. |
| [TELEVISION](-t-e-l-e-v-i-s-i-o-n/index.html) | [androidJvm]<br>[TELEVISION](-t-e-l-e-v-i-s-i-o-n/index.html)<br>EN Television. |
| [CAR](-c-a-r/index.html) | [androidJvm]<br>[CAR](-c-a-r/index.html)<br>EN Car. |
| [WATCH](-w-a-t-c-h/index.html) | [androidJvm]<br>[WATCH](-w-a-t-c-h/index.html)<br>EN Watch (Wear OS). |
| [DESK](-d-e-s-k/index.html) | [androidJvm]<br>[DESK](-d-e-s-k/index.html)<br>EN Desk Device (Docked). |
| [APPLIANCE](-a-p-p-l-i-a-n-c-e/index.html) | [androidJvm]<br>[APPLIANCE](-a-p-p-l-i-a-n-c-e/index.html)<br>EN Projection Device (e.g., Android Auto, Cast). |
| [VR_HEADSET](-v-r_-h-e-a-d-s-e-t/index.html) | [androidJvm]<br>@[RequiresApi](https://developer.android.com/reference/kotlin/androidx/annotation/RequiresApi.html)(value = 26)<br>[VR_HEADSET](-v-r_-h-e-a-d-s-e-t/index.html)<br>EN Virtual Reality (VR) Device. |
| [UNDEFINED](-u-n-d-e-f-i-n-e-d/index.html) | [androidJvm]<br>[UNDEFINED](-u-n-d-e-f-i-n-e-d/index.html)<br>EN Any unspecified/other UI mode. |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [androidJvm]<br>object [Companion](-companion/index.html) |


## Properties


| Name | Summary |
|---|---|
| [configValue](config-value.html) | [androidJvm]<br>val [configValue](config-value.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |
| [entries](entries.html) | [androidJvm]<br>val [entries](entries.html): [EnumEntries](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.enums/-enum-entries/index.html)&lt;[UiModeType](index.html)&gt;<br>Returns a representation of an immutable list of all enum entries, in the order they're declared. |
| [name](../-unit-type/-p-x/index.html#-372974862%2FProperties%2F1376941149) | [androidJvm]<br>val [name](../-unit-type/-p-x/index.html#-372974862%2FProperties%2F1376941149): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) |
| [ordinal](../-unit-type/-p-x/index.html#-739389684%2FProperties%2F1376941149) | [androidJvm]<br>val [ordinal](../-unit-type/-p-x/index.html#-739389684%2FProperties%2F1376941149): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |


## Functions


| Name | Summary |
|---|---|
| [valueOf](value-of.html) | [androidJvm]<br>fun [valueOf](value-of.html)(value: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [UiModeType](index.html)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.html) | [androidJvm]<br>fun [values](values.html)(): [Array](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-array/index.html)&lt;[UiModeType](index.html)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |
