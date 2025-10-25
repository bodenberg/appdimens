---
layout: default
title: "UiModeQualifierEntry"
category: library
permalink: /LIBRARY/MARKDOWN/appdimens_library/com.appdimens.library/-ui-mode-qualifier-entry/index.html
---

# UiModeQualifierEntry

[androidJvm]
data class [UiModeQualifierEntry](README.md)(val uiModeType: [UiModeType](../-ui-mode-type/README.md), val dpQualifierEntry: [DpQualifierEntry](../-dp-qualifier-entry/README.md))

EN Represents a qualifier entry that combines a UI Mode type (device) AND a screen qualifier (SW, H, W). This combination has the HIGHEST PRIORITY.

PT Representa uma entrada de qualificador que combina um tipo de UI Mode (dispositivo) E um qualificador de tela (SW, H, W). Esta combinação tem a PRIORIDADE MÁXIMA.

## Constructors

| | |
|---|---|
| [UiModeQualifierEntry](-ui-mode-qualifier-entry.md) | [androidJvm]<br>constructor(uiModeType: [UiModeType](../-ui-mode-type/README.md), dpQualifierEntry: [DpQualifierEntry](../-dp-qualifier-entry/README.md)) |

## Properties

| Name | Summary |
|---|---|
| [dpQualifierEntry](dp-qualifier-entry.md) | [androidJvm]<br>val [dpQualifierEntry](dp-qualifier-entry.md): [DpQualifierEntry](../-dp-qualifier-entry/README.md)<br>EN The screen qualifier (DpQualifier, minimum DP value).     PT O qualificador de tela (DpQualifier, valor DP mínimo). |
| [uiModeType](ui-mode-type.md) | [androidJvm]<br>val [uiModeType](ui-mode-type.md): [UiModeType](../-ui-mode-type/README.md)<br>EN The UI Mode type (CAR, TELEVISION, NORMAL, etc.).     PT O tipo de UI Mode (CAR, TELEVISION, NORMAL, etc.). |