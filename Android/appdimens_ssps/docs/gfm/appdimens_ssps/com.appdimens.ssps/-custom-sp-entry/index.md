//[appdimens_ssps](../../../index.md)/[com.appdimens.ssps](../index.md)/[CustomSpEntry](index.md)

# CustomSpEntry

[androidJvm]\
data class [CustomSpEntry](index.md)(val uiModeType: UiModeType? = null, val dpQualifierEntry: DpQualifierEntry? = null, val customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html), val priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

Representa uma entrada de configuração de dimensão de texto (Sp) personalizada. Usada para definir valores de texto (Sp) específicos com base no modo de UI (ex: carro, TV), no qualificador de DP (ex: largura mínima) e na prioridade.

## Constructors

| | |
|---|---|
| [CustomSpEntry](-custom-sp-entry.md) | [androidJvm]<br>constructor(uiModeType: UiModeType? = null, dpQualifierEntry: DpQualifierEntry? = null, customValue: [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html), priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [customValue](custom-value.md) | [androidJvm]<br>val [customValue](custom-value.md): [TextUnit](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/TextUnit.html)<br>O valor TextUnit (Sp) a ser usado. |
| [dpQualifierEntry](dp-qualifier-entry.md) | [androidJvm]<br>val [dpQualifierEntry](dp-qualifier-entry.md): DpQualifierEntry? = null<br>A entrada do qualificador de DP (tipo e valor mínimo) (opcional). |
| [priority](priority.md) | [androidJvm]<br>val [priority](priority.md): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>A prioridade de aplicação desta regra. Prioridades mais baixas são avaliadas primeiro. |
| [uiModeType](ui-mode-type.md) | [androidJvm]<br>val [uiModeType](ui-mode-type.md): UiModeType? = null<br>O modo de UI ao qual esta entrada se aplica (opcional). |