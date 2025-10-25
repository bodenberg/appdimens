---
title: CustomSpEntry
---
//[appdimens_ssps](../../../index.html)/[com.appdimens.ssps.compose](../index.html)/[CustomSpEntry](index.html)



# CustomSpEntry



[androidJvm]\
data class [CustomSpEntry](index.html)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

EN Represents a custom text dimension (Sp) configuration entry. Used to define specific text (Sp) values based on the UI mode (e.g., car, TV), DP qualifier (e.g., smallest width), and priority.



PT Representa uma entrada de configuração de dimensão de texto (Sp) personalizada. Usada para definir valores de texto (Sp) específicos com base no modo de UI (ex: carro, TV), no qualificador de DP (ex: largura mínima) e na prioridade.



## Constructors


| | |
|---|---|
| [CustomSpEntry](-custom-sp-entry.html) | [androidJvm]<br>constructor(uiModeType: UiModeType? = null, dpQualifierEntry: DpQualifierEntry? = null, customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html), priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [customValue](custom-value.html) | [androidJvm]<br>val [customValue](custom-value.html): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>The TextUnit (Sp) value to be used. |
| [dpQualifierEntry](dp-qualifier-entry.html) | [androidJvm]<br>val [dpQualifierEntry](dp-qualifier-entry.html): DpQualifierEntry? = null<br>The DP qualifier entry (type and minimum value) (optional). |
| [priority](priority.html) | [androidJvm]<br>val [priority](priority.html): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>The application priority of this rule. Lower priorities are evaluated first. |
| [uiModeType](ui-mode-type.html) | [androidJvm]<br>val [uiModeType](ui-mode-type.html): UiModeType? = null<br>The UI mode to which this entry applies (optional). |
