//[appdimens_sdps](../../../index.md)/[com.appdimens.sdps.code](../index.md)/[AppDimensSdp](index.md)/[getResourceId](get-resource-id.md)

# getResourceId

[androidJvm]\
fun [getResourceId](get-resource-id.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), dpQualifier: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)

EN Gets the resource ID for an SDP value.

PT Obtém o ID do recurso para um valor SDP.

#### Return

The resource ID, or 0 if not found.

#### Parameters

androidJvm

| | |
|---|---|
| context | The application context. |
| value | The SDP value (-330 to 600). |
| dpQualifier | DpQualifier. |