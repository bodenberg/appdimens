//[appdimens_dynamic](../../../README.md)/[com.appdimens.dynamic.code](../README.md)/[AppDimensAutoCache](README.md)/[remember](remember.md)

# remember

[androidJvm]\
fun &lt;[T](remember.md)&gt; [remember](remember.md)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), dependencies: [Set](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-set/index.html)&lt;[Any](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-any/index.html)&gt;, compute: () -&gt; [T](remember.md)): [T](remember.md)

EN Gets or computes a value with automatic dependency tracking. Similar to Compose's remember function.

#### Return

The cached or computed value.

PT Obtém ou computa um valor com rastreamento automático de dependências. Similar à função remember do Compose.

O valor em cache ou computado.

#### Parameters

androidJvm

| | |
|---|---|
| key | A chave do cache. |
| dependencies | As dependências que afetam este valor. |
| compute | A função de computação. |