---
layout: default
title: "UiModeType"
category: library
permalink: /LIBRARY/MARKDOWN/appdimens_library/com.appdimens.library/-ui-mode-type/index.html
---

# UiModeType

[androidJvm]
enum [UiModeType](README.md) : [Enum](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-enum/index.html)<[UiModeType](README.md)> 

EN Defines the Android UI Mode Types for dimension customization, based on [Configuration.uiMode](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html#uimode).

PT Define os tipos de modo de interface do usuário (UI Mode Type) do Android para customização de dimensões, com base em [Configuration.uiMode](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html#uimode).

## Entries

| | |
|---|---|
| [NORMAL](-n-o-r-m-a-l/README.md) | [androidJvm]<br>[NORMAL](-n-o-r-m-a-l/README.md)<br>EN Default Phone/Tablet. |
| [TELEVISION](-t-e-l-e-v-i-s-i-o-n/README.md) | [androidJvm]<br>[TELEVISION](-t-e-l-e-v-i-s-i-o-n/README.md)<br>EN Television. |
| [CAR](-c-a-r/README.md) | [androidJvm]<br>[CAR](-c-a-r/README.md)<br>EN Car. |
| [WATCH](-w-a-t-c-h/README.md) | [androidJvm]<br>[WATCH](-w-a-t-c-h/README.md)<br>EN Watch (Wear OS). |
| [DESK](-d-e-s-k/README.md) | [androidJvm]<br>[DESK](-d-e-s-k/README.md)<br>EN Desk Device (Docked). |
| [APPLIANCE](-a-p-p-l-i-a-n-c-e/README.md) | [androidJvm]<br>[APPLIANCE](-a-p-p-l-i-a-n-c-e/README.md)<br>EN Projection Device (e.g., Android Auto, Cast). |
| [VR_HEADSET](-v-r_-h-e-a-d-s-e-t/README.md) | [androidJvm]<br>@[RequiresApi](https://developer.android.com/reference/kotlin/androidx/annotation/RequiresApi.html)(value = 26)<br>[VR_HEADSET](-v-r_-h-e-a-d-s-e-t/README.md)<br>EN Virtual Reality (VR) Device. |
| [UNDEFINED](-u-n-d-e-f-i-n-e-d/README.md) | [androidJvm]<br>[UNDEFINED](-u-n-d-e-f-i-n-e-d/README.md)<br>EN Any unspecified/other UI mode. |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/README.md) | [androidJvm]<br>object [Companion](-companion/README.md) |

## Properties

| Name | Summary |
|---|---|
| [configValue](config-value.md) | [androidJvm]<br>val [configValue](config-value.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |
| [entries](entries.md) | [androidJvm]<br>val [entries](entries.md): [EnumEntries](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.enums/-enum-entries/index.html)<[UiModeType](README.md)><br>Returns a representation of an immutable list of all enum entries, in the order they're declared. |
| [name](../-unit-type/-p-x/README.md#-372974862%2FProperties%2F373173406) | [androidJvm]<br>val [name](../-unit-type/-p-x/README.md#-372974862%2FProperties%2F373173406): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html) |
| [ordinal](../-unit-type/-p-x/README.md#-739389684%2FProperties%2F373173406) | [androidJvm]<br>val [ordinal](../-unit-type/-p-x/README.md#-739389684%2FProperties%2F373173406): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [androidJvm]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [UiModeType](README.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [androidJvm]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-array/index.html)<[UiModeType](README.md)><br>Returns an array containing the constants of this enum type, in the order they're declared. |