//[appdimens_sdps](../../index.md)/[com.appdimens.sdps](index.md)/[toDynamicScaledDp](to-dynamic-scaled-dp.md)

# toDynamicScaledDp

[androidJvm]\

@[Composable](https://developer.android.com/reference/kotlin/androidx/compose/runtime/Composable.html)

fun [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html).[toDynamicScaledDp](to-dynamic-scaled-dp.md)(qualifier: DpQualifier): [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html)

Converte um [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html) (o valor base de Dp) em um [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) dinamicamente escalado.

A lógica tenta encontrar um recurso de dimensão correspondente na pasta 'res/values/'.

1. 
   Constrói o nome do recurso baseado no valor (this) e no qualificador ([qualifier](to-dynamic-scaled-dp.md)).
2. 
   Tenta carregar o recurso via [dimensionResource](https://developer.android.com/reference/kotlin/androidx/compose/ui/res/package-summary.html).
3. 
   Se o recurso for encontrado (e.g., em `values-sw600dp/dimens.xml`), esse valor é usado.
4. 
   Se o recurso não for encontrado, o valor original é usado como [Dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/Dp.html) (o [Int.dp](https://developer.android.com/reference/kotlin/androidx/compose/ui/unit/package-summary.html) padrão do Compose).

#### Return

O valor de Dp carregado do recurso ou o valor de Dp base.

#### Parameters

androidJvm

| | |
|---|---|
| qualifier | O qualificador de tela usado para construir o nome do recurso (s, h, w). |