---
title: AppDimensAutoCache
---
//[appdimens_dynamic](../../../index.html)/[com.appdimens.dynamic.code](../index.html)/[AppDimensAutoCache](index.html)



# AppDimensAutoCache



[androidJvm]\
object [AppDimensAutoCache](index.html)

EN Automatic cache management system that mimics Compose's remember behavior. Automatically detects dependency changes and invalidates cache entries.



PT Sistema de gerenciamento automático de cache que imita o comportamento do remember do Compose. Detecta automaticamente mudanças de dependências e invalida entradas do cache.



## Types


| Name | Summary |
|---|---|
| [CacheStats](-cache-stats/index.html) | [androidJvm]<br>data class [CacheStats](-cache-stats/index.html)(val totalEntries: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val totalAccesses: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), val averageAccesses: [Double](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-double/index.html), val memoryUsage: [Long](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-long/index.html))<br>EN Cache statistics data class. PT Classe de dados de estatísticas do cache. |


## Functions


| Name | Summary |
|---|---|
| [clearAll](clear-all.html) | [androidJvm]<br>fun [clearAll](clear-all.html)()<br>EN Clears all cache entries. PT Limpa todas as entradas do cache. |
| [clearByPattern](clear-by-pattern.html) | [androidJvm]<br>fun [clearByPattern](clear-by-pattern.html)(pattern: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html))<br>EN Clears cache entries that match a specific pattern. Useful for clearing instance-specific cache entries. PT Limpa entradas do cache que correspondem a um padrão específico. Útil para limpar entradas de cache específicas da instância. |
| [getCacheStats](get-cache-stats.html) | [androidJvm]<br>fun [getCacheStats](get-cache-stats.html)(): [AppDimensAutoCache.CacheStats](-cache-stats/index.html)<br>EN Gets cache statistics for debugging. PT Obtém estatísticas do cache para debug. |
| [invalidateOnConfigurationChange](invalidate-on-configuration-change.html) | [androidJvm]<br>fun [invalidateOnConfigurationChange](invalidate-on-configuration-change.html)(oldConfiguration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html)?, newConfiguration: [Configuration](https://developer.android.com/reference/kotlin/android/content/res/Configuration.html))<br>EN Invalidates cache entries based on configuration changes. Automatically called when screen configuration changes. |
| [invalidateOnDependencyChange](invalidate-on-dependency-change.html) | [androidJvm]<br>fun [invalidateOnDependencyChange](invalidate-on-dependency-change.html)(changedObject: [Any](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-any/index.html))<br>EN Invalidates cache entries that depend on a specific object. Called automatically when dependencies change. |
| [remember](remember.html) | [androidJvm]<br>fun &lt;[T](remember.html)&gt; [remember](remember.html)(key: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), dependencies: [Set](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-set/index.html)&lt;[Any](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-any/index.html)&gt;, compute: () -&gt; [T](remember.html)): [T](remember.html)<br>EN Gets or computes a value with automatic dependency tracking. Similar to Compose's remember function. |
