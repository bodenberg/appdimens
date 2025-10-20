/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - LRU Cache
 *
 * Description:
 * LRU (Least Recently Used) cache implementation for optimized memory usage
 * in game dimension calculations.
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

#ifndef APPDIMENS_GAMES_LRU_CACHE_H
#define APPDIMENS_GAMES_LRU_CACHE_H

#include <unordered_map>
#include <list>
#include <mutex>

namespace AppDimensGamesCache {

/**
 * [EN] LRU Cache implementation with thread safety and size limits.
 * [PT] Implementação de cache LRU com segurança de thread e limites de tamanho.
 */
template<typename Key, typename Value>
class LRUCache {
public:
    /**
     * [EN] Constructor with maximum cache size.
     * @param maxSize Maximum number of entries in the cache.
     * [PT] Construtor com tamanho máximo do cache.
     * @param maxSize Número máximo de entradas no cache.
     */
    explicit LRUCache(size_t maxSize = 1000) : maxSize_(maxSize) {}
    
    /**
     * [EN] Gets a value from the cache.
     * @param key The cache key.
     * @return The cached value, or nullptr if not found.
     * [PT] Obtém um valor do cache.
     * @param key A chave do cache.
     * @return O valor em cache, ou nullptr se não encontrado.
     */
    Value* get(const Key& key) {
        std::lock_guard<std::mutex> lock(mutex_);
        
        auto it = cache_.find(key);
        if (it == cache_.end()) {
            return nullptr;
        }
        
        // Move to front (most recently used)
        items_.splice(items_.begin(), items_, it->second);
        return &it->second->second;
    }
    
    /**
     * [EN] Puts a value into the cache.
     * @param key The cache key.
     * @param value The value to cache.
     * [PT] Coloca um valor no cache.
     * @param key A chave do cache.
     * @param value O valor a ser armazenado em cache.
     */
    void put(const Key& key, const Value& value) {
        std::lock_guard<std::mutex> lock(mutex_);
        
        auto it = cache_.find(key);
        if (it != cache_.end()) {
            // Update existing entry
            it->second->second = value;
            items_.splice(items_.begin(), items_, it->second);
            return;
        }
        
        // Add new entry
        if (items_.size() >= maxSize_) {
            // Remove least recently used item
            auto last = items_.back();
            cache_.erase(last.first);
            items_.pop_back();
        }
        
        items_.emplace_front(key, value);
        cache_[key] = items_.begin();
    }
    
    /**
     * [EN] Clears all entries from the cache.
     * [PT] Limpa todas as entradas do cache.
     */
    void clear() {
        std::lock_guard<std::mutex> lock(mutex_);
        cache_.clear();
        items_.clear();
    }
    
    /**
     * [EN] Gets the current number of entries in the cache.
     * @return The number of cached entries.
     * [PT] Obtém o número atual de entradas no cache.
     * @return O número de entradas em cache.
     */
    size_t size() const {
        std::lock_guard<std::mutex> lock(mutex_);
        return cache_.size();
    }
    
    /**
     * [EN] Gets the maximum cache size.
     * @return The maximum number of entries.
     * [PT] Obtém o tamanho máximo do cache.
     * @return O número máximo de entradas.
     */
    size_t maxSize() const {
        return maxSize_;
    }
    
    /**
     * [EN] Sets the maximum cache size.
     * @param maxSize The new maximum size.
     * [PT] Define o tamanho máximo do cache.
     * @param maxSize O novo tamanho máximo.
     */
    void setMaxSize(size_t maxSize) {
        std::lock_guard<std::mutex> lock(mutex_);
        maxSize_ = maxSize;
        
        // Remove excess entries if necessary
        while (items_.size() > maxSize_) {
            auto last = items_.back();
            cache_.erase(last.first);
            items_.pop_back();
        }
    }

private:
    size_t maxSize_;
    std::list<std::pair<Key, Value>> items_;
    std::unordered_map<Key, typename std::list<std::pair<Key, Value>>::iterator> cache_;
    mutable std::mutex mutex_;
};

} // namespace AppDimensGamesCache

#endif // APPDIMENS_GAMES_LRU_CACHE_H
