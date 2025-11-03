/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Lock-Free Cache Implementation (C++)
 *
 * Description:
 * Implementation of ultra-fast lock-free cache using std::atomic.
 *
 * Licensed under the Apache License, Version 2.0
 */

#include "GameCacheFast.h"
#include <cstring>
#include <algorithm>

// Initialize static atomic array
std::atomic<GameCacheEntry*> GameCacheFast::cache[CACHE_SIZE];

int32_t GameCacheFast::computeHash(
    float baseValue,
    float screenWidthDp,
    float screenHeightDp,
    float smallestWidthDp,
    int strategyOrdinal
) {
    // FNV-1a hash algorithm
    int32_t hash = static_cast<int32_t>(0x811c9dc5);
    const int32_t FNV_PRIME = 0x01000193;
    
    // Mix in each value
    union { float f; int32_t i; } converter;
    
    converter.f = baseValue;
    hash ^= converter.i;
    hash *= FNV_PRIME;
    
    hash ^= static_cast<int32_t>(screenWidthDp);
    hash *= FNV_PRIME;
    
    hash ^= static_cast<int32_t>(screenHeightDp);
    hash *= FNV_PRIME;
    
    hash ^= static_cast<int32_t>(smallestWidthDp);
    hash *= FNV_PRIME;
    
    hash ^= strategyOrdinal;
    hash *= FNV_PRIME;
    
    return hash;
}

float* GameCacheFast::lookup(int32_t hash) {
    int index = hash & CACHE_MASK;
    
    // Atomic load (lock-free read)
    GameCacheEntry* entry = cache[index].load(std::memory_order_acquire);
    
    if (entry != nullptr && entry->hash == hash) {
        // Cache hit!
        entry->hitCount.fetch_add(1, std::memory_order_relaxed);
        return &entry->value;
    }
    
    return nullptr;
}

void GameCacheFast::store(int32_t hash, float value) {
    int index = hash & CACHE_MASK;
    
    // Create new entry
    GameCacheEntry* newEntry = new GameCacheEntry(
        hash,
        value,
        getCurrentTimeMs()
    );
    
    // Get old entry
    GameCacheEntry* oldEntry = cache[index].load(std::memory_order_acquire);
    
    // Atomic store (lock-free write)
    cache[index].store(newEntry, std::memory_order_release);
    
    // Delete old entry if it exists
    if (oldEntry != nullptr) {
        delete oldEntry;
    }
}

void GameCacheFast::clearAll() {
    for (int i = 0; i < CACHE_SIZE; i++) {
        GameCacheEntry* entry = cache[i].load(std::memory_order_acquire);
        cache[i].store(nullptr, std::memory_order_release);
        
        if (entry != nullptr) {
            delete entry;
        }
    }
}

GameCacheFast::CacheStats GameCacheFast::getStats() {
    CacheStats stats;
    stats.totalEntries = 0;
    stats.totalHits = 0;
    stats.oldestEntryAgeMs = 0;
    
    int64_t currentTime = getCurrentTimeMs();
    
    for (int i = 0; i < CACHE_SIZE; i++) {
        GameCacheEntry* entry = cache[i].load(std::memory_order_acquire);
        if (entry != nullptr) {
            stats.totalEntries++;
            stats.totalHits += entry->hitCount.load(std::memory_order_relaxed);
            
            int64_t age = currentTime - entry->timestamp;
            if (age > stats.oldestEntryAgeMs) {
                stats.oldestEntryAgeMs = age;
            }
        }
    }
    
    stats.hitRate = (stats.totalHits > 0) ? 
        static_cast<float>(stats.totalHits) / std::max(stats.totalEntries, 1) : 0.0f;
    
    return stats;
}

int64_t GameCacheFast::getCurrentTimeMs() {
    auto now = std::chrono::system_clock::now();
    auto duration = now.time_since_epoch();
    return std::chrono::duration_cast<std::chrono::milliseconds>(duration).count();
}

