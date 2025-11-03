/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Cache Tests
 *
 * Description:
 * Unit tests for GameCacheFast lock-free cache system.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games

import com.appdimens.games.core.cache.GameCacheFast
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.After
import kotlin.system.measureNanoTime

class GameCacheFastTest {
    
    @Before
    fun setup() {
        GameCacheFast.clearAll()
    }
    
    @After
    fun cleanup() {
        GameCacheFast.clearAll()
    }
    
    @Test
    fun testCacheHitMiss() {
        var computeCount = 0
        
        val hash = GameCacheFast.computeHash(
            baseValue = 48f,
            screenWidthDp = 360f,
            screenHeightDp = 640f,
            smallestWidthDp = 360f,
            strategyOrdinal = 0
        )
        
        // First call - should compute
        val result1 = GameCacheFast.rememberFast(hash) {
            computeCount++
            100f
        }
        
        assertEquals("First call should compute", 1, computeCount)
        assertEquals("First call should return computed value", 100f, result1, 0.001f)
        
        // Second call - should hit cache
        val result2 = GameCacheFast.rememberFast(hash) {
            computeCount++
            200f // Different value, should not be called
        }
        
        assertEquals("Second call should hit cache", 1, computeCount)
        assertEquals("Second call should return cached value", 100f, result2, 0.001f)
    }
    
    @Test
    fun testCachePerformance() {
        val hash = GameCacheFast.computeHash(48f, 360f, 640f, 360f, 0)
        
        // Populate cache
        GameCacheFast.rememberFast(hash) { 100f }
        
        // Measure cache hit performance
        val hitTime = measureNanoTime {
            repeat(1000) {
                GameCacheFast.rememberFast(hash) { 100f }
            }
        }
        
        val averageHitTime = hitTime / 1000.0
        
        // Cache hit should be very fast (<1µs = 1000ns)
        assertTrue("Cache hit should be very fast (<1µs)", averageHitTime < 1000)
        
        println("Average cache hit time: ${averageHitTime}ns")
    }
    
    @Test
    fun testHashComputation() {
        val hash1 = GameCacheFast.computeHash(48f, 360f, 640f, 360f, 0)
        val hash2 = GameCacheFast.computeHash(48f, 360f, 640f, 360f, 0)
        
        assertEquals("Same inputs should produce same hash", hash1, hash2)
        
        val hash3 = GameCacheFast.computeHash(48f, 720f, 640f, 360f, 0)
        
        assertNotEquals("Different inputs should produce different hash", hash1, hash3)
    }
    
    @Test
    fun testGameHashComputation() {
        val hash1 = GameCacheFast.computeGameHash(
            baseValue = 48f,
            screenWidthDp = 360f,
            screenHeightDp = 640f,
            smallestWidthDp = 360f,
            strategyOrdinal = 0,
            elementTypeOrdinal = 1
        )
        
        val hash2 = GameCacheFast.computeGameHash(
            baseValue = 48f,
            screenWidthDp = 360f,
            screenHeightDp = 640f,
            smallestWidthDp = 360f,
            strategyOrdinal = 0,
            elementTypeOrdinal = 2  // Different element type
        )
        
        assertNotEquals("Different element types should produce different hash", hash1, hash2)
    }
    
    @Test
    fun testCacheStatistics() {
        GameCacheFast.clearAll()
        
        val hash1 = GameCacheFast.computeHash(48f, 360f, 640f, 360f, 0)
        val hash2 = GameCacheFast.computeHash(64f, 360f, 640f, 360f, 0)
        
        // Populate cache
        GameCacheFast.rememberFast(hash1) { 100f }
        GameCacheFast.rememberFast(hash2) { 200f }
        
        // Hit cache multiple times
        repeat(10) {
            GameCacheFast.rememberFast(hash1) { 100f }
            GameCacheFast.rememberFast(hash2) { 200f }
        }
        
        val stats = GameCacheFast.getFastCacheStats()
        
        assertTrue("Cache should have entries", stats.totalEntries > 0)
        assertTrue("Cache should have hits", stats.totalHits > 0)
        assertTrue("Hit rate should be positive", stats.hitRate > 0f)
        
        println("Cache stats: $stats")
    }
    
    @Test
    fun testCacheClear() {
        val hash = GameCacheFast.computeHash(48f, 360f, 640f, 360f, 0)
        
        var computeCount = 0
        
        // Populate cache
        GameCacheFast.rememberFast(hash) {
            computeCount++
            100f
        }
        
        assertEquals("Should compute once", 1, computeCount)
        
        // Clear cache
        GameCacheFast.clearAll()
        
        // Should compute again after clear
        GameCacheFast.rememberFast(hash) {
            computeCount++
            100f
        }
        
        assertEquals("Should compute again after clear", 2, computeCount)
    }
    
    @Test
    fun testMultiThreadedCache() {
        val hash = GameCacheFast.computeHash(48f, 360f, 640f, 360f, 0)
        
        // Simulate multi-threaded access
        val threads = List(10) {
            Thread {
                repeat(100) {
                    GameCacheFast.rememberFast(hash) { 100f }
                }
            }
        }
        
        threads.forEach { it.start() }
        threads.forEach { it.join() }
        
        // Should not crash (lock-free is thread-safe)
        val result = GameCacheFast.rememberFast(hash) { 100f }
        assertEquals("Multi-threaded access should work", 100f, result, 0.001f)
    }
}

