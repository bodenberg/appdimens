/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-19
 *
 * Library: AppDimens iOS
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

import Foundation
import UIKit

/**
 * [EN] Automatic cache management system that mimics Compose's remember behavior.
 * Automatically detects dependency changes and invalidates cache entries.
 *
 * [PT] Sistema de gerenciamento automático de cache que imita o comportamento do remember do Compose.
 * Detecta automaticamente mudanças de dependências e invalida entradas do cache.
 */
public class AppDimensAutoCache {
    
    // MARK: - Singleton
    
    public static let shared = AppDimensAutoCache()
    
    private init() {}
    
    // MARK: - Private Properties
    
    private let cache = NSMutableDictionary()
    private let dependencyTracker = NSMutableDictionary()
    private let cacheQueue = DispatchQueue(label: "com.appdimens.cache", attributes: .concurrent)
    
    /**
     * [EN] Cache entry that stores value, dependencies, and metadata.
     * [PT] Entrada do cache que armazena valor, dependências e metadados.
     */
    private struct CacheEntry {
        let value: Any
        let dependencies: Set<DependencyKey>
        let timestamp: TimeInterval
        var accessCount: Int
        
        init(value: Any, dependencies: Set<DependencyKey>, timestamp: TimeInterval = Date().timeIntervalSince1970, accessCount: Int = 0) {
            self.value = value
            self.dependencies = dependencies
            self.timestamp = timestamp
            self.accessCount = accessCount
        }
    }
    
    /**
     * [EN] Dependency key that can track different types of dependencies.
     * [PT] Chave de dependência que pode rastrear diferentes tipos de dependências.
     */
    private struct DependencyKey: Hashable {
        let type: String
        let value: AnyHashable
        let hashCode: Int
        
        init<T: Hashable>(_ obj: T) {
            self.type = String(describing: type(of: obj))
            self.value = AnyHashable(obj)
            self.hashCode = obj.hashValue
        }
        
        func hash(into hasher: inout Hasher) {
            hasher.combine(type)
            hasher.combine(hashCode)
        }
        
        static func == (lhs: DependencyKey, rhs: DependencyKey) -> Bool {
            return lhs.type == rhs.type && lhs.hashCode == rhs.hashCode
        }
    }
    
    /**
     * [EN] Creates a dependency key from any object.
     * [PT] Cria uma chave de dependência a partir de qualquer objeto.
     */
    private func createDependencyKey<T: Hashable>(_ obj: T) -> DependencyKey {
        return DependencyKey(obj)
    }
    
    // MARK: - Public Methods
    
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
    public func remember<T>(
        key: String,
        dependencies: Set<AnyHashable>,
        compute: () -> T
    ) -> T {
        return cacheQueue.sync(flags: .barrier) {
            let dependencyKeys = Set(dependencies.map { createDependencyKey($0) })
            let currentEntry = cache[key] as? CacheEntry
            
            // Check if dependencies have changed
            let dependenciesChanged = currentEntry?.dependencies != dependencyKeys
            
            if dependenciesChanged {
                // Dependencies changed, compute new value
                let newValue = compute()
                let newEntry = CacheEntry(
                    value: newValue,
                    dependencies: dependencyKeys,
                    timestamp: Date().timeIntervalSince1970,
                    accessCount: 1
                )
                cache[key] = newEntry
                dependencyTracker[key] = dependencyKeys
                return newValue
            }
            
            // Dependencies haven't changed, return cached value
            if var entry = currentEntry {
                entry.accessCount += 1
                cache[key] = entry
                return entry.value as! T
            }
            
            // Fallback (should not happen)
            return compute()
        }
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
    public func invalidateOnDependencyChange<T: Hashable>(_ changedObject: T) {
        cacheQueue.async(flags: .barrier) {
            let changedKey = self.createDependencyKey(changedObject)
            
            let keysToRemove = self.dependencyTracker.compactMap { (key, value) -> String? in
                guard let deps = value as? Set<DependencyKey>,
                      deps.contains(changedKey) else { return nil }
                return key as? String
            }
            
            keysToRemove.forEach { key in
                self.cache.removeObject(forKey: key)
                self.dependencyTracker.removeObject(forKey: key)
            }
        }
    }
    
    /**
     * [EN] Invalidates cache entries based on screen dimension changes.
     * Automatically called when screen dimensions change.
     *
     * @param oldDimensions The previous screen dimensions.
     * @param newDimensions The new screen dimensions.
     *
     * [PT] Invalida entradas do cache baseadas em mudanças de dimensões da tela.
     * Chamado automaticamente quando as dimensões da tela mudam.
     *
     * @param oldDimensions As dimensões anteriores da tela.
     * @param newDimensions As novas dimensões da tela.
     */
    public func invalidateOnScreenChange(
        oldDimensions: (width: CGFloat, height: CGFloat)?,
        newDimensions: (width: CGFloat, height: CGFloat)
    ) {
        guard let oldDims = oldDimensions else { return }
        
        if oldDims.width != newDimensions.width || oldDims.height != newDimensions.height {
            invalidateOnDependencyChange(oldDims)
        }
    }
    
    /**
     * [EN] Clears all cache entries.
     * [PT] Limpa todas as entradas do cache.
     */
    public func clearAll() {
        cacheQueue.async(flags: .barrier) {
            self.cache.removeAllObjects()
            self.dependencyTracker.removeAllObjects()
        }
    }
    
    /**
     * [EN] Gets the current cache size (number of entries).
     * [PT] Obtém o tamanho atual do cache (número de entradas).
     * 
     * @return Number of cached entries.
     */
    public func getCacheSize() -> Int {
        return cacheQueue.sync {
            return cache.count
        }
    }
    
    /**
     * [EN] Clears cache entries that match a specific pattern.
     * Useful for clearing instance-specific cache entries.
     * [PT] Limpa entradas do cache que correspondem a um padrão específico.
     * Útil para limpar entradas de cache específicas da instância.
     */
    public func clearByPattern(_ pattern: String) {
        cacheQueue.async(flags: .barrier) {
            let keys = self.cache.allKeys.compactMap { $0 as? String }
            let keysToRemove = keys.filter { $0.contains(pattern) }
            keysToRemove.forEach { key in
                self.cache.removeObject(forKey: key as NSString)
                self.dependencyTracker.removeObject(forKey: key as NSString)
            }
        }
    }
    
    /**
     * [EN] Gets cache statistics for debugging.
     * [PT] Obtém estatísticas do cache para debug.
     */
    public func getCacheStats() -> CacheStats {
        return cacheQueue.sync {
            let totalEntries = cache.count
            let totalAccesses = cache.allValues.compactMap { $0 as? CacheEntry }.reduce(0) { $0 + $1.accessCount }
            let averageAccesses = totalEntries > 0 ? Double(totalAccesses) / Double(totalEntries) : 0.0
            
            return CacheStats(
                totalEntries: totalEntries,
                totalAccesses: totalAccesses,
                averageAccesses: averageAccesses,
                memoryUsage: estimateMemoryUsage()
            )
        }
    }
    
    /**
     * [EN] Estimates memory usage of the cache.
     * [PT] Estima o uso de memória do cache.
     */
    private func estimateMemoryUsage() -> Int {
        var totalSize = 0
        
        cache.forEach { (key, value) in
            if let keyString = key as? String {
                totalSize += keyString.count * 2 // String characters
            }
            if let entry = value as? CacheEntry {
                totalSize += entry.dependencies.count * 16 // DependencyKey objects
                totalSize += 24 // CacheEntry object overhead
            }
        }
        
        return totalSize
    }
    
    /**
     * [EN] Cache statistics data class.
     * [PT] Classe de dados de estatísticas do cache.
     */
    public struct CacheStats {
        public let totalEntries: Int
        public let totalAccesses: Int
        public let averageAccesses: Double
        public let memoryUsage: Int
    }
}
