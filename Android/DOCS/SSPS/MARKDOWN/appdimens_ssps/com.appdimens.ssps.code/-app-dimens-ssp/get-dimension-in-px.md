//[appdimens_ssps](../../../index.md)/[com.appdimens.ssps.code](../index.md)/[AppDimensSsp](index.md)/[getDimensionInPx](get-dimension-in-px.md)

# getDimensionInPx

[androidJvm]\
fun [getDimensionInPx](get-dimension-in-px.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), dpQualifier: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)

EN Gets the dimension in pixels from an SSP value.

PT Obtém a dimensão em pixels a partir de um valor SSP.

#### Return

The dimension in pixels, or 0f if not found.

#### Parameters

androidJvm

| | |
|---|---|
| context | The application context. |
| dpQualifier | DpQualifier. |
| value | The SSP value (0 to 600). |