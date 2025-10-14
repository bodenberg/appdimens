//[appdimens_dynamic](../../../index.md)/[com.appdimens.dynamic.code](../index.md)/[AppDimensAdjustmentFactors](index.md)

# AppDimensAdjustmentFactors

[androidJvm]\
object [AppDimensAdjustmentFactors](index.md)

Objeto singleton que fornece funções para o cálculo e resolução de fatores de ajuste e qualificadores de tela. Compatível com o sistema tradicional de Views XML do Android.

## Properties

| Name | Summary |
|---|---|
| [BASE_DP_FACTOR](-b-a-s-e_-d-p_-f-a-c-t-o-r.md) | [androidJvm]<br>const val [BASE_DP_FACTOR](-b-a-s-e_-d-p_-f-a-c-t-o-r.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 1.0f<br>Fator base de escala Dp. O valor padrão é 1.0f (sem ajuste). |
| [BASE_INCREMENT](-b-a-s-e_-i-n-c-r-e-m-e-n-t.md) | [androidJvm]<br>const val [BASE_INCREMENT](-b-a-s-e_-i-n-c-r-e-m-e-n-t.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.1f<br>Fator de incremento padrão (usado no cálculo SEM Aspect Ratio). |
| [BASE_WIDTH_DP](-b-a-s-e_-w-i-d-t-h_-d-p.md) | [androidJvm]<br>const val [BASE_WIDTH_DP](-b-a-s-e_-w-i-d-t-h_-d-p.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 300.0f<br>Largura DP de referência base para o cálculo de ajuste (ex: 300dp). |
| [CIRCUMFERENCE_FACTOR](-c-i-r-c-u-m-f-e-r-e-n-c-e_-f-a-c-t-o-r.md) | [androidJvm]<br>const val [CIRCUMFERENCE_FACTOR](-c-i-r-c-u-m-f-e-r-e-n-c-e_-f-a-c-t-o-r.md): &lt;Error class: unknown class&gt;<br>Fator para cálculo de circunferência (<br>$2\pi$<br>). Usando kotlin.math.PI. |
| [DEFAULT_SENSITIVITY_K](-d-e-f-a-u-l-t_-s-e-n-s-i-t-i-v-i-t-y_-k.md) | [androidJvm]<br>const val [DEFAULT_SENSITIVITY_K](-d-e-f-a-u-l-t_-s-e-n-s-i-t-i-v-i-t-y_-k.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 0.08f<br>Coeficiente de sensibilidade PADRÃO: Ajusta o quão agressivo é o escalonamento em telas extremas. |
| [INCREMENT_DP_STEP](-i-n-c-r-e-m-e-n-t_-d-p_-s-t-e-p.md) | [androidJvm]<br>const val [INCREMENT_DP_STEP](-i-n-c-r-e-m-e-n-t_-d-p_-s-t-e-p.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 30.0f<br>Tamanho do passo de incremento em Dp para calcular o ajuste (ex: a cada 30dp). |
| [REFERENCE_AR](-r-e-f-e-r-e-n-c-e_-a-r.md) | [androidJvm]<br>const val [REFERENCE_AR](-r-e-f-e-r-e-n-c-e_-a-r.md): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html) = 1.78f<br>Proporção de tela de referência (Ex: 16:9), onde o ajuste é neutro. |

## Functions

| Name | Summary |
|---|---|
| [calculateAdjustmentFactors](calculate-adjustment-factors.md) | [androidJvm]<br>fun [calculateAdjustmentFactors](calculate-adjustment-factors.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [ScreenAdjustmentFactors](../-screen-adjustment-factors/index.md)<br>Calcula os Fatores Básicos de Ajuste. |
| [getReferenceAspectRatio](get-reference-aspect-ratio.md) | [androidJvm]<br>fun [getReferenceAspectRatio](get-reference-aspect-ratio.md)(screenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), screenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>Função auxiliar para obter a proporção de tela a partir da configuração. |
| [resolveIntersectionCondition](resolve-intersection-condition.md) | [androidJvm]<br>fun [resolveIntersectionCondition](resolve-intersection-condition.md)(entry: DpQualifierEntry, smallestWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>Função auxiliar que verifica se um DpQualifierEntry atende às dimensões atuais da tela. |
| [resolveQualifierDp](resolve-qualifier-dp.md) | [androidJvm]<br>fun [resolveQualifierDp](resolve-qualifier-dp.md)(customDpMap: [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;DpQualifierEntry, [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)&gt;, smallestWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenWidthDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), currentScreenHeightDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>Função auxiliar que isola a lógica de busca e seleção do valor Dp customizado através dos Qualificadores (SW, H, W). |