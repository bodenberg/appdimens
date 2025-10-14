//[appdimens_dynamic](../../../index.md)/[com.appdimens.dynamic.code](../index.md)/[AppDimensFixed](index.md)

# AppDimensFixed

[androidJvm]\
class [AppDimensFixed](index.md)(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false)

Classe para construir dimensões &quot;fixas&quot; que são ajustadas automaticamente com base no menor `smallestScreenWidthDp` do dispositivo e na proporção da tela. Compatível com View System (XML).

## Constructors

| | |
|---|---|
| [AppDimensFixed](-app-dimens-fixed.md) | [androidJvm]<br>constructor(initialBaseDp: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html), ignoreMultiViewAdjustment: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = false) |

## Functions

| Name | Summary |
|---|---|
| [aspectRatio](aspect-ratio.md) | [androidJvm]<br>fun [aspectRatio](aspect-ratio.md)(enable: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true, sensitivityK: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)? = null): [AppDimensFixed](index.md) |
| [calculateAdjustedValue](calculate-adjusted-value.md) | [androidJvm]<br>fun [calculateAdjustedValue](calculate-adjusted-value.md)(configuration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>Executa o cálculo final do ajuste da dimensão. |
| [multiViewAdjustment](multi-view-adjustment.md) | [androidJvm]<br>fun [multiViewAdjustment](multi-view-adjustment.md)(ignore: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html) = true): [AppDimensFixed](index.md) |
| [screen](screen.md) | [androidJvm]<br>fun [screen](screen.md)(type: UiModeType, customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.md)<br>fun [screen](screen.md)(type: UiModeType, customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.md)<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.md)<br>fun [screen](screen.md)(type: DpQualifier, value: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.md)<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)): [AppDimensFixed](index.md)<br>fun [screen](screen.md)(uiModeType: UiModeType, qualifierType: DpQualifier, qualifierValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), customValue: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)): [AppDimensFixed](index.md) |
| [toDp](to-dp.md) | [androidJvm]<br>fun [toDp](to-dp.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>Retorna o valor Dp ajustado (em Dp, não convertido para PX). |
| [toEm](to-em.md) | [androidJvm]<br>fun [toEm](to-em.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Float), ignorando a escala de fonte do sistema (unidade 'em'). |
| [toEmInt](to-em-int.md) | [androidJvm]<br>fun [toEmInt](to-em-int.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)<br>Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Int), ignorando a escala de fonte do sistema (unidade 'em'). |
| [toPx](to-px.md) | [androidJvm]<br>fun [toPx](to-px.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>Constrói o valor Dp ajustado e o converte para Pixels (Float). |
| [toSp](to-sp.md) | [androidJvm]<br>fun [toSp](to-sp.md)(resources: [Resources](https://developer.android.com/reference/kotlin/android/content/res/Resources.html)): [Float](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-float/index.html)<br>Constrói o valor Dp ajustado e o converte para Scaleable Pixels (SP) em Pixels (Float). |
| [type](type.md) | [androidJvm]<br>fun [type](type.md)(type: ScreenType): [AppDimensFixed](index.md) |