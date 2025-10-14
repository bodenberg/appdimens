//[appdimens_sdps](../../../index.md)/[com.appdimens.sdps](../index.md)/[CustomDpEntry](index.md)/[CustomDpEntry](-custom-dp-entry.md)

# CustomDpEntry

[androidJvm]\
constructor(uiModeType: UiModeType? = null, dpQualifierEntry: DpQualifierEntry? = null, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

#### Parameters

androidJvm

| | |
|---|---|
| uiModeType | O modo de UI (CAR, TELEVISION, WATCH, NORMAL). Nulo para qualquer modo. |
| dpQualifierEntry | A entrada do qualificador de Dp (tipo e valor, e.g., SMALL_WIDTH 600). Nulo se apenas o modo de UI for usado. |
| customValue | O valor de Dp que deve ser usado se a condição for atendida. |
| priority | A prioridade de resolução. 1 é mais específico (UI + Qualificador), 3 é menos específico (apenas Qualificador). |