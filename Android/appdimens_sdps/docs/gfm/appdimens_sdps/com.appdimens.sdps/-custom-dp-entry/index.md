//[appdimens_sdps](../../../index.md)/[com.appdimens.sdps](../index.md)/[CustomDpEntry](index.md)

# CustomDpEntry

data class [CustomDpEntry](index.md)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

Representa uma entrada de dimensão customizada com qualificadores e prioridade. Usada pela classe [Scaled](../-scaled/index.md) para definir valores específicos para condições de tela.

#### Parameters

androidJvm

| | |
|---|---|
| uiModeType | O modo de UI (CAR, TELEVISION, WATCH, NORMAL). Nulo para qualquer modo. |
| dpQualifierEntry | A entrada do qualificador de Dp (tipo e valor, e.g., SMALL_WIDTH 600). Nulo se apenas o modo de UI for usado. |
| customValue | O valor de Dp que deve ser usado se a condição for atendida. |
| priority | A prioridade de resolução. 1 é mais específico (UI + Qualificador), 3 é menos específico (apenas Qualificador). |

## Constructors

| | |
|---|---|
| [CustomDpEntry](-custom-dp-entry.md) | [androidJvm]<br>constructor(uiModeType: UiModeType? = null, dpQualifierEntry: DpQualifierEntry? = null, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [customValue](custom-value.md) | [androidJvm]<br>val [customValue](custom-value.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) |
| [dpQualifierEntry](dp-qualifier-entry.md) | [androidJvm]<br>val [dpQualifierEntry](dp-qualifier-entry.md): DpQualifierEntry? = null |
| [priority](priority.md) | [androidJvm]<br>val [priority](priority.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |
| [uiModeType](ui-mode-type.md) | [androidJvm]<br>val [uiModeType](ui-mode-type.md): UiModeType? = null |