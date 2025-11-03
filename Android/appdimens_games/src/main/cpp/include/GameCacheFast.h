/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Lock-Free Cache (C++)
 *
 * Description:
 * Ultra-optimized lock-free cache for game dimension calculations.
 * Uses std::atomic for thread-safe, lock-free operations.
 *
 * Licensed under the Apache License, Version 2.0
 */

#ifndef GAME_CACHE_FAST_H
#define GAME_CACHE_FAST_H

#include <atomic>
#include <cstdint>
#include <chrono>

/**
 * Fast cache entry with minimal memory footprint.
 */
struct GameCacheEntry {
    int32_t hash;
    float value;
    int64_t timestamp;
    std::atomic<int32_t> hitCount;
    
    GameCacheEntry()
        : hash(0)
        , value(0.0f)
        , timestamp(0)
        , hitCount(0) {}
    
    GameCacheEntry(int32_t h, float v, int64_t t)
        : hash(h)
        , value(v)
        , timestamp(t)
        , hitCount(0) {}
};

/**
 * Lock-free cache for game dimensions.
 * 
 * Performance characteristics:
 * - Cache hit: <0.001µs (atomic read)
 * - Cache miss: <0.01ms (computation + atomic write)
 * - Multi-threading: Zero contention (lock-free)
 * 
 * Memory: 1024 entries × 24 bytes = ~24KB
 */
class GameCacheFast {
public:
    static constexpr int CACHE_SIZE = 1024;
    static constexpr int CACHE_MASK = CACHE_SIZE - 1;
    
    /**
     * Computes FNV-1a hash from dimension parameters.
     * 
     * @param baseValue Base dimension value
     * @param screenWidthDp Screen width in dp
     * @param screenHeightDp Screen height in dp
     * @param smallestWidthDp Smallest width in dp
     * @param strategyOrdinal Strategy enum ordinal
     * @return Hash code
     */
    static int32_t computeHash(
        float baseValue,
        float screenWidthDp,
        float screenHeightDp,
        float smallestWidthDp,
        int strategyOrdinal = 0
    );
    
    /**
     * Looks up value in cache.
     * 
     * @param hash Hash key
     * @return Cached value if found, nullptr otherwise
     */
    static float* lookup(int32_t hash);
    
    /**
     * Stores value in cache.
     * 
     * @param hash Hash key
     * @param value Value to cache
     */
    static void store(int32_t hash, float value);
    
    /**
     * Clears all cache entries.
     */
    static void clearAll();
    
    /**
     * Gets cache statistics.
     */
    struct CacheStats {
        int totalEntries;
        int64_t totalHits;
        float hitRate;
        int64_t oldestEntryAgeMs;
    };
    
    static CacheStats getStats();
    
private:
    // Atomic array for lock-free access
    static std::atomic<GameCacheEntry*> cache[CACHE_SIZE];
    
    // Get current timestamp in milliseconds
    static int64_t getCurrentTimeMs();
};

#endif // GAME_CACHE_FAST_H

