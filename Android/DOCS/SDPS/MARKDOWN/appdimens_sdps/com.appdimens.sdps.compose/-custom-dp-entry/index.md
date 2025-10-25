---
title: CustomDpEntry
---
//[appdimens_sdps](../../../index.html)/[com.appdimens.sdps.compose](../index.html)/[CustomDpEntry](index.html)



# CustomDpEntry

data class [CustomDpEntry](index.html)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

EN Represents a custom dimension entry with qualifiers and priority. Used by the [Scaled](../-scaled/index.html) class to define specific values for screen conditions.



PT Representa uma entrada de dimensão customizada com qualificadores e prioridade. Usada pela classe [Scaled](../-scaled/index.html) para definir valores específicos para condições de tela.



#### Parameters


androidJvm

| | |
|---|---|
| uiModeType | The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode. |
| dpQualifierEntry | The Dp qualifier entry (type and value, e.g., SMALL_WIDTH 600). Null if only UI mode is used. |
| customValue | The Dp value to be used if the condition is met. |
| priority | The resolution priority. 1 is more specific (UI + Qualifier), 3 is less specific (Qualifier only). |



## Constructors


| | |
|---|---|
| [CustomDpEntry](-custom-dp-entry.html) | [androidJvm]<br>constructor(uiModeType: UiModeType? = null, dpQualifierEntry: DpQualifierEntry? = null, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [customValue](custom-value.html) | [androidJvm]<br>val [customValue](custom-value.html): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) |
| [dpQualifierEntry](dp-qualifier-entry.html) | [androidJvm]<br>val [dpQualifierEntry](dp-qualifier-entry.html): DpQualifierEntry? = null |
| [priority](priority.html) | [androidJvm]<br>val [priority](priority.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |
| [uiModeType](ui-mode-type.html) | [androidJvm]<br>val [uiModeType](ui-mode-type.html): UiModeType? = null |
