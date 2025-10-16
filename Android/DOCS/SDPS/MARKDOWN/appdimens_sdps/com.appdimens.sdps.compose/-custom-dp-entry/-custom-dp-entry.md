//[appdimens_sdps](../../../index.md)/[com.appdimens.sdps.compose](../index.md)/[CustomDpEntry](index.md)/[CustomDpEntry](-custom-dp-entry.md)

# CustomDpEntry

[androidJvm]\
constructor(uiModeType: UiModeType? = null, dpQualifierEntry: DpQualifierEntry? = null, customValue: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html), priority: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html))

#### Parameters

androidJvm

| | |
|---|---|
| uiModeType | The UI mode (CAR, TELEVISION, WATCH, NORMAL). Null for any mode. |
| dpQualifierEntry | The Dp qualifier entry (type and value, e.g., SMALL_WIDTH 600). Null if only UI mode is used. |
| customValue | The Dp value to be used if the condition is met. |
| priority | The resolution priority. 1 is more specific (UI + Qualifier), 3 is less specific (Qualifier only). |