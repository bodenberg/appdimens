//[appdimens_sdps](../../../index.md)/[com.appdimens.sdps.compose](../index.md)/[CustomDpEntry](index.md)

# CustomDpEntry

data class [CustomDpEntry](index.md)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

EN Represents a custom dimension entry with qualifiers and priority. Used by the [Scaled](../-scaled/index.md) class to define specific values for screen conditions.

PT Representa uma entrada de dimensão customizada com qualificadores e prioridade. Usada pela classe [Scaled](../-scaled/index.md) para definir valores específicos para condições de tela.

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
| [CustomDpEntry](-custom-dp-entry.md) | [androidJvm]<br>constructor(uiModeType: UiModeType? = null, dpQualifierEntry: DpQualifierEntry? = null, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [customValue](custom-value.md) | [androidJvm]<br>val [customValue](custom-value.md): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) |
| [dpQualifierEntry](dp-qualifier-entry.md) | [androidJvm]<br>val [dpQualifierEntry](dp-qualifier-entry.md): DpQualifierEntry? = null |
| [priority](priority.md) | [androidJvm]<br>val [priority](priority.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) |
| [uiModeType](ui-mode-type.md) | [androidJvm]<br>val [uiModeType](ui-mode-type.md): UiModeType? = null |