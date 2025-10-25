---
title: AppDimensPhysicalUnits
---
//[appdimens_dynamic](../../../index.html)/[com.appdimens.dynamic.code](../index.html)/[AppDimensPhysicalUnits](index.html)



# AppDimensPhysicalUnits



[androidJvm]\
object [AppDimensPhysicalUnits](index.html)

EN Utility class for physical unit conversions. PT Classe utilitária para conversões de unidades físicas.



## Properties


| Name | Summary |
|---|---|
| [POINTS_PER_CM](-p-o-i-n-t-s_-p-e-r_-c-m.html) | [androidJvm]<br>const val [POINTS_PER_CM](-p-o-i-n-t-s_-p-e-r_-c-m.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Points per centimeter. PT Pontos por centímetro. |
| [POINTS_PER_INCH](-p-o-i-n-t-s_-p-e-r_-i-n-c-h.html) | [androidJvm]<br>const val [POINTS_PER_INCH](-p-o-i-n-t-s_-p-e-r_-i-n-c-h.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 72.0f<br>EN Points per inch (standard resolution). PT Pontos por polegada (resolução padrão). |
| [POINTS_PER_MM](-p-o-i-n-t-s_-p-e-r_-m-m.html) | [androidJvm]<br>const val [POINTS_PER_MM](-p-o-i-n-t-s_-p-e-r_-m-m.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Points per millimeter. PT Pontos por milímetro. |


## Functions


| Name | Summary |
|---|---|
| [cmToInch](cm-to-inch.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[cmToInch](cm-to-inch.html)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Float extension to convert CM to Inch. PT Extensão de Float para converter CM para Inch. |
| [cmToMm](cm-to-mm.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[cmToMm](cm-to-mm.html)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Float extension to convert CM to MM. PT Extensão de Float para converter CM para MM. |
| [inchToCm](inch-to-cm.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[inchToCm](inch-to-cm.html)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Float extension to convert Inch to CM. PT Extensão de Float para converter Inch para CM. |
| [inchToMm](inch-to-mm.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[inchToMm](inch-to-mm.html)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Float extension to convert Inch to MM. PT Extensão de Float para converter Inch para MM. |
| [mmToCm](mm-to-cm.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[mmToCm](mm-to-cm.html)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Float extension to convert MM to CM. PT Extensão de Float para converter MM para CM. |
| [mmToInch](mm-to-inch.html) | [androidJvm]<br>fun [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html).[mmToInch](mm-to-inch.html)(): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Float extension to convert MM to Inch. PT Extensão de Float para converter MM para Inch. |
| [radiusFromCircumference](radius-from-circumference.html) | [androidJvm]<br>fun [radiusFromCircumference](radius-from-circumference.html)(circumference: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), unitType: UnitType, resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts a circumference value in a specific physical unit to radius in Dp. |
| [radiusFromDiameter](radius-from-diameter.html) | [androidJvm]<br>fun [radiusFromDiameter](radius-from-diameter.html)(diameter: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), unitType: UnitType, resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts a diameter value in a specific physical unit to radius in Dp. |
| [toDpFromCm](to-dp-from-cm.html) | [androidJvm]<br>fun [toDpFromCm](to-dp-from-cm.html)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts centimeters to Dp. |
| [toDpFromInch](to-dp-from-inch.html) | [androidJvm]<br>fun [toDpFromInch](to-dp-from-inch.html)(inch: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts inches to Dp. |
| [toDpFromMm](to-dp-from-mm.html) | [androidJvm]<br>fun [toDpFromMm](to-dp-from-mm.html)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts millimeters to Dp. |
| [toPxFromCm](to-px-from-cm.html) | [androidJvm]<br>fun [toPxFromCm](to-px-from-cm.html)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts centimeters to Pixels. |
| [toPxFromInch](to-px-from-inch.html) | [androidJvm]<br>fun [toPxFromInch](to-px-from-inch.html)(inch: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts inches to Pixels. |
| [toPxFromMm](to-px-from-mm.html) | [androidJvm]<br>fun [toPxFromMm](to-px-from-mm.html)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts millimeters to Pixels. |
| [toSpFromCm](to-sp-from-cm.html) | [androidJvm]<br>fun [toSpFromCm](to-sp-from-cm.html)(cm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts centimeters to SP. |
| [toSpFromInch](to-sp-from-inch.html) | [androidJvm]<br>fun [toSpFromInch](to-sp-from-inch.html)(inch: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts inches to SP. |
| [toSpFromMm](to-sp-from-mm.html) | [androidJvm]<br>fun [toSpFromMm](to-sp-from-mm.html)(mm: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Converts millimeters to SP. |
