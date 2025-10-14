//[appdimens_library](../../../index.md)/[com.appdimens.library](../index.md)/[UiModeQualifierEntry](index.md)

# UiModeQualifierEntry

data class [UiModeQualifierEntry](index.md)(val uiModeType: [UiModeType](../-ui-mode-type/index.md), val dpQualifierEntry: [DpQualifierEntry](../-dp-qualifier-entry/index.md))

Representa uma entrada de qualificador que combina um tipo de UI Mode (dispositivo) E um qualificador de tela (SW, H, W). Esta combinação tem a PRIORIDADE MÁXIMA.

#### Parameters

androidJvm

| | |
|---|---|
| uiModeType | O tipo de UI Mode (CAR, TELEVISION, NORMAL, etc.). |
| dpQualifierEntry | O qualificador de tela (DpQualifier, valor DP mínimo). |

## Constructors

| | |
|---|---|
| [UiModeQualifierEntry](-ui-mode-qualifier-entry.md) | [androidJvm]<br>constructor(uiModeType: [UiModeType](../-ui-mode-type/index.md), dpQualifierEntry: [DpQualifierEntry](../-dp-qualifier-entry/index.md)) |

## Properties

| Name | Summary |
|---|---|
| [dpQualifierEntry](dp-qualifier-entry.md) | [androidJvm]<br>val [dpQualifierEntry](dp-qualifier-entry.md): [DpQualifierEntry](../-dp-qualifier-entry/index.md) |
| [uiModeType](ui-mode-type.md) | [androidJvm]<br>val [uiModeType](ui-mode-type.md): [UiModeType](../-ui-mode-type/index.md) |