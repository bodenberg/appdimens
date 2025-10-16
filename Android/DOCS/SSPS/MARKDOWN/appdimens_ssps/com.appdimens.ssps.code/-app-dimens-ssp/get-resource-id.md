//[appdimens_ssps](../../../index.md)/[com.appdimens.ssps.code](../index.md)/[AppDimensSsp](index.md)/[getResourceId](get-resource-id.md)

# getResourceId

[androidJvm]\
fun [getResourceId](get-resource-id.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), dpQualifier: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

EN Gets the resource ID for an SSP value.

PT Obtém o ID do recurso para um valor SSP.

#### Return

The resource ID, or 0 if not found.

#### Parameters

androidJvm

| | |
|---|---|
| context | The application context. |
| dpQualifier | DpQualifier. |
| value | The SSP value (0 to 600). |