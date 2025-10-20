/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-19
 *
 * Library: AppDimens
 *
 * Description:
 * Automatic cache management system based on Compose's remember mechanism.
 * Automatically detects dependency changes and invalidates cache entries.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdimens.dynamic.code

import android.content.res.Configuration
import java.util.concurrent.ConcurrentHashMap

/**
 * [EN] Automatic cache management system that mimics Compose's remember behavior.
 * Automatically detects dependency changes and invalidates cache entries.
 *
 * [PT] Sistema de gerenciamento automático de cache que imita o comportamento do remember do Compose.
 * Detecta automaticamente mudanças de dependências e invalida entradas do cache.
 */
object AppDimensAutoCache {
    
    private val cache = ConcurrentHashMap<String, CacheEntry>()
    private val dependencyTracker = ConcurrentHashMap<String, Set<DependencyKey>>()
    
    /**
     * [EN] Cache entry that stores value, dependencies, and metadata.
     * [PT] Entrada do cache que armazena valor, dependências e metadados.
     */
    private data class CacheEntry(
        val value: Any,
        val dependencies: Set<DependencyKey>,
        val timestamp: Long,
        val accessCount: Int = 0
    )
    
    /**
     * [EN] Dependency key that can track different types of dependencies.
     * [PT] Chave de dependência que pode rastrear diferentes tipos de dependências.
     */
    private data class DependencyKey(
        val type: String,
        val value: Any,
        val hashCode: Int
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is DependencyKey) return false
            return type == other.type && hashCode == other.hashCode
        }
        
        override fun hashCode(): Int = hashCode
    }
    
    /**
     * [EN] Creates a dependency key from any object.
     * [PT] Cria uma chave de dependência a partir de qualquer objeto.
     */
    private fun createDependencyKey(obj: Any): DependencyKey {
        return DependencyKey(
            type = obj::class.java.simpleName,
            value = obj,
            hashCode = obj.hashCode()
        )
    }
    
    /**
     * [EN] Gets or computes a value with automatic dependency tracking.
     * Similar to Compose's remember function.
     *
     * @param key The cache key.
     * @param dependencies The dependencies that affect this value.
     * @param compute The computation function.
     * @return The cached or computed value.
     *
     * [PT] Obtém ou computa um valor com rastreamento automático de dependências.
     * Similar à função remember do Compose.
     *
     * @param key A chave do cache.
     * @param dependencies As dependências que afetam este valor.
     * @param compute A função de computação.
     * @return O valor em cache ou computado.
     */
    fun <T> remember(
        key: String,
        dependencies: Set<Any>,
        compute: () -> T
    ): T {
        val dependencyKeys = dependencies.map { createDependencyKey(it) }.toSet()
        val currentEntry = cache[key]
        
        // Check if dependencies have changed
        val dependenciesChanged = currentEntry?.dependencies != dependencyKeys
        
        if (dependenciesChanged) {
            // Dependencies changed, compute new value
            val newValue = compute()
            cache[key] = CacheEntry(
                value = newValue as Any,
                dependencies = dependencyKeys,
                timestamp = System.currentTimeMillis(),
                accessCount = 1
            )
            dependencyTracker[key] = dependencyKeys
            return newValue
        }
        
        // Dependencies haven't changed, return cached value
        val entry = currentEntry!!
        cache[key] = entry.copy(accessCount = entry.accessCount + 1)
        @Suppress("UNCHECKED_CAST")
        return entry.value as T
    }
    
    /**
     * [EN] Invalidates cache entries that depend on a specific object.
     * Called automatically when dependencies change.
     *
     * @param changedObject The object that changed.
     *
     * [PT] Invalida entradas do cache que dependem de um objeto específico.
     * Chamado automaticamente quando dependências mudam.
     *
     * @param changedObject O objeto que mudou.
     */
    fun invalidateOnDependencyChange(changedObject: Any) {
        val changedKey = createDependencyKey(changedObject)
        
        val keysToRemove = dependencyTracker.filter { (_, deps) ->
            deps.contains(changedKey)
        }.keys
        
        keysToRemove.forEach { key ->
            cache.remove(key)
            dependencyTracker.remove(key)
        }
    }
    
    /**
     * [EN] Invalidates cache entries based on configuration changes.
     * Automatically called when screen configuration changes.
     *
     * @param oldConfiguration The previous configuration.
     * @param newConfiguration The new configuration.
     *
     * [PT] Invalida entradas do cache baseadas em mudanças de configuração.
     * Chamado automaticamente quando a configuração da tela muda.
     *
     * @param oldConfiguration A configuração anterior.
     * @param newConfiguration A nova configuração.
     */
    fun invalidateOnConfigurationChange(
        oldConfiguration: Configuration?,
        newConfiguration: Configuration
    ) {
        if (oldConfiguration == null) return
        
        val oldConfigKey = createDependencyKey(oldConfiguration)
        val newConfigKey = createDependencyKey(newConfiguration)
        
        if (oldConfigKey != newConfigKey) {
            invalidateOnDependencyChange(oldConfiguration)
        }
    }
    
    /**
     * [EN] Clears all cache entries.
     * [PT] Limpa todas as entradas do cache.
     */
    fun clearAll() {
        cache.clear()
        dependencyTracker.clear()
    }
    
    /**
     * [EN] Gets cache statistics for debugging.
     * [PT] Obtém estatísticas do cache para debug.
     */
    fun getCacheStats(): CacheStats {
        val totalEntries = cache.size
        val totalAccesses = cache.values.sumOf { it.accessCount }
        val averageAccesses = if (totalEntries > 0) totalAccesses.toDouble() / totalEntries else 0.0
        
        return CacheStats(
            totalEntries = totalEntries,
            totalAccesses = totalAccesses,
            averageAccesses = averageAccesses,
            memoryUsage = estimateMemoryUsage()
        )
    }
    
    /**
     * [EN] Estimates memory usage of the cache.
     * [PT] Estima o uso de memória do cache.
     */
    private fun estimateMemoryUsage(): Long {
        var totalSize = 0L
        
        cache.forEach { (key, entry) ->
            totalSize += key.length * 2 // String characters
            totalSize += entry.dependencies.size * 16 // DependencyKey objects
            totalSize += 24 // CacheEntry object overhead
        }
        
        return totalSize
    }
    
    /**
     * [EN] Cache statistics data class.
     * [PT] Classe de dados de estatísticas do cache.
     */
    data class CacheStats(
        val totalEntries: Int,
        val totalAccesses: Int,
        val averageAccesses: Double,
        val memoryUsage: Long
    )
}
