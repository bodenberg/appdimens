---
title: UiModeQualifierEntry
---
//[appdimens_library](../../../index.html)/[com.appdimens.library](../index.html)/[UiModeQualifierEntry](index.html)



# UiModeQualifierEntry



[androidJvm]\
data class [UiModeQualifierEntry](index.html)(val uiModeType: [UiModeType](../-ui-mode-type/index.html), val dpQualifierEntry: [DpQualifierEntry](../-dp-qualifier-entry/index.html))

EN Represents a qualifier entry that combines a UI Mode type (device) AND a screen qualifier (SW, H, W). This combination has the HIGHEST PRIORITY.



PT Representa uma entrada de qualificador que combina um tipo de UI Mode (dispositivo) E um qualificador de tela (SW, H, W). Esta combinação tem a PRIORIDADE MÁXIMA.



## Constructors


| | |
|---|---|
| [UiModeQualifierEntry](-ui-mode-qualifier-entry.html) | [androidJvm]<br>constructor(uiModeType: [UiModeType](../-ui-mode-type/index.html), dpQualifierEntry: [DpQualifierEntry](../-dp-qualifier-entry/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [dpQualifierEntry](dp-qualifier-entry.html) | [androidJvm]<br>val [dpQualifierEntry](dp-qualifier-entry.html): [DpQualifierEntry](../-dp-qualifier-entry/index.html)<br>EN The screen qualifier (DpQualifier, minimum DP value).     PT O qualificador de tela (DpQualifier, valor DP mínimo). |
| [uiModeType](ui-mode-type.html) | [androidJvm]<br>val [uiModeType](ui-mode-type.html): [UiModeType](../-ui-mode-type/index.html)<br>EN The UI Mode type (CAR, TELEVISION, NORMAL, etc.).     PT O tipo de UI Mode (CAR, TELEVISION, NORMAL, etc.). |
