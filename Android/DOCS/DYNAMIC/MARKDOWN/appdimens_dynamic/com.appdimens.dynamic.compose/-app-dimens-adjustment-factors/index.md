---
title: AppDimensAdjustmentFactors
---
//[appdimens_dynamic](../../../index.html)/[com.appdimens.dynamic.compose](../index.html)/[AppDimensAdjustmentFactors](index.html)



# AppDimensAdjustmentFactors



[androidJvm]\
@[Stable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Stable.html)



object [AppDimensAdjustmentFactors](index.html)

EN Singleton object that provides functions for calculating and resolving adjustment factors and screen qualifiers.



PT Objeto singleton que fornece funções para o cálculo e resolução de fatores de ajuste e qualificadores de tela.



## Properties


| Name | Summary |
|---|---|
| [BASE_DP_FACTOR](-b-a-s-e_-d-p_-f-a-c-t-o-r.html) | [androidJvm]<br>const val [BASE_DP_FACTOR](-b-a-s-e_-d-p_-f-a-c-t-o-r.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 1.0f<br>EN Base Dp scaling factor. The default value is 1.0f (no adjustment). |
| [BASE_INCREMENT](-b-a-s-e_-i-n-c-r-e-m-e-n-t.html) | [androidJvm]<br>const val [BASE_INCREMENT](-b-a-s-e_-i-n-c-r-e-m-e-n-t.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.1f<br>EN Default increment factor (used in calculation WITHOUT Aspect Ratio). |
| [BASE_WIDTH_DP](-b-a-s-e_-w-i-d-t-h_-d-p.html) | [androidJvm]<br>const val [BASE_WIDTH_DP](-b-a-s-e_-w-i-d-t-h_-d-p.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 300.0f<br>EN Base reference width DP for adjustment calculation (e.g., 300dp). |
| [CIRCUMFERENCE_FACTOR](-c-i-r-c-u-m-f-e-r-e-n-c-e_-f-a-c-t-o-r.html) | [androidJvm]<br>const val [CIRCUMFERENCE_FACTOR](-c-i-r-c-u-m-f-e-r-e-n-c-e_-f-a-c-t-o-r.html): &lt;Error class: unknown class&gt;<br>EN Factor for circumference calculation (2π). Using kotlin.math.PI. |
| [DEFAULT_SENSITIVITY_K](-d-e-f-a-u-l-t_-s-e-n-s-i-t-i-v-i-t-y_-k.html) | [androidJvm]<br>const val [DEFAULT_SENSITIVITY_K](-d-e-f-a-u-l-t_-s-e-n-s-i-t-i-v-i-t-y_-k.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.08f<br>EN DEFAULT sensitivity coefficient: Adjusts how aggressive the scaling is on extreme screens. |
| [INCREMENT_DP_STEP](-i-n-c-r-e-m-e-n-t_-d-p_-s-t-e-p.html) | [androidJvm]<br>const val [INCREMENT_DP_STEP](-i-n-c-r-e-m-e-n-t_-d-p_-s-t-e-p.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 1.0f<br>EN Increment step size in Dp to calculate the adjustment (e.g., every 1dp). |
| [REFERENCE_AR](-r-e-f-e-r-e-n-c-e_-a-r.html) | [androidJvm]<br>const val [REFERENCE_AR](-r-e-f-e-r-e-n-c-e_-a-r.html): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 1.78f<br>EN Reference screen aspect ratio (e.g., 16:9), where the adjustment is neutral. |


## Functions


| Name | Summary |
|---|---|
| [getReferenceAspectRatio](get-reference-aspect-ratio.html) | [androidJvm]<br>fun [getReferenceAspectRatio](get-reference-aspect-ratio.html)(screenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), screenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>EN Helper function to get the screen aspect ratio from the configuration. |
| [rememberAdjustmentFactors](remember-adjustment-factors.html) | [androidJvm]<br>@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)<br>fun [rememberAdjustmentFactors](remember-adjustment-factors.html)(): ScreenAdjustmentFactors<br>EN Composable function that calculates and remembers Basic Adjustment Factors. |
| [resolveIntersectionCondition](resolve-intersection-condition.html) | [androidJvm]<br>fun [resolveIntersectionCondition](resolve-intersection-condition.html)(entry: DpQualifierEntry, smallestWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>EN Helper function that checks if a DpQualifierEntry meets the current screen dimensions. |
| [resolveQualifierDp](resolve-qualifier-dp.html) | [androidJvm]<br>fun [resolveQualifierDp](resolve-qualifier-dp.html)(customDpMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;DpQualifierEntry, [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)&gt;, smallestWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), initialBaseDp: [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)<br>EN Helper function that isolates the logic for fetching and selecting the custom Dp value through Qualifiers (SW, H, W). This logic should be called inside a 'remember' block for performance. |
