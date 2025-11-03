/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Performance Tests
 *
 * Description:
 * Performance benchmarks and stress tests for game performance.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games

import com.appdimens.games.core.calculation.GamesCalculator
import com.appdimens.games.core.strategy.*
import com.appdimens.games.core.models.*
import com.appdimens.games.core.cache.GameCacheFast
import com.appdimens.games.core.GamesCore
import com.appdimens.games.core.GamePerformanceMode
import org.junit.Test
import org.junit.Assert.*
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class GamePerformanceTest {
    
    private val testConfig = GameScreenConfig(
        screenWidthDp = 360f,
        screenHeightDp = 640f,
        smallestScreenWidthDp = 360f,
        densityDpi = 420,
        uiMode = GameUiModeType.NORMAL
    )
    
    @Test
    fun testSingleCalculationPerformance() {
        // Warm up
        repeat(100) {
            GamesCalculator.calculate(
                baseValue = 48f,
                strategy = GameScalingStrategy.BALANCED,
                elementType = GameElementType.HUD_BUTTON,
                config = testConfig
            )
        }
        
        // Measure
        val time = measureNanoTime {
            GamesCalculator.calculate(
                baseValue = 48f,
                strategy = GameScalingStrategy.BALANCED,
                elementType = GameElementType.HUD_BUTTON,
                config = testConfig
            )
        }
        
        val timeMicros = time / 1000.0
        
        // Should be very fast (<10µs with cache)
        assertTrue("Single calculation should be fast (<10µs)", timeMicros < 10.0)
        
        println("Single calculation time: ${timeMicros}µs")
    }
    
    @Test
    fun test1000CalculationsPerformance() {
        GameCacheFast.clearAll()
        
        val iterations = 1000
        
        val time = measureTimeMillis {
            repeat(iterations) {
                GamesCalculator.calculate(
                    baseValue = 48f,
                    strategy = GameScalingStrategy.BALANCED,
                    elementType = GameElementType.HUD_BUTTON,
                    config = testConfig
                )
            }
        }
        
        val avgTime = time.toDouble() / iterations
        
        // With cache, should average <0.01ms per calculation
        assertTrue("1000 calculations should average <0.01ms", avgTime < 0.01)
        
        println("1000 calculations: ${time}ms total, ${avgTime}ms average")
        
        val stats = GameCacheFast.getFastCacheStats()
        println("Cache stats: $stats")
        assertTrue("Cache hit rate should be high", stats.hitRate > 0.8f)
    }
    
    @Test
    fun testMixedStrategiesPerformance() {
        GameCacheFast.clearAll()
        
        val strategies = arrayOf(
            GameScalingStrategy.DEFAULT,
            GameScalingStrategy.BALANCED,
            GameScalingStrategy.PERCENTAGE,
            GameScalingStrategy.FLUID,
            GameScalingStrategy.FIT,
            GameScalingStrategy.FILL
        )
        
        val time = measureTimeMillis {
            repeat(100) { i ->
                strategies.forEach { strategy ->
                    GamesCalculator.calculate(
                        baseValue = (i % 10 + 1) * 8f,
                        strategy = strategy,
                        elementType = null,
                        config = testConfig
                    )
                }
            }
        }
        
        // 600 calculations (100 iterations × 6 strategies)
        val avgTime = time.toDouble() / 600
        
        println("Mixed strategies (600 calcs): ${time}ms total, ${avgTime}ms average")
        
        // Should still be fast
        assertTrue("Mixed strategies should be fast", avgTime < 0.05)
    }
    
    @Test
    fun testFrameBudgetSimulation() {
        // Simulate a game frame at 60 FPS (16.67ms budget)
        GameCacheFast.clearAll()
        GamesCore.setPerformanceMode(GamePerformanceMode.HIGH_PERFORMANCE)
        
        val frameBudgetMs = 16.67
        val calculationsPerFrame = 500 // Typical for a game
        
        // Warm up cache
        repeat(10) {
            repeat(calculationsPerFrame) { i ->
                val baseValue = (i % 20 + 1) * 4f
                GamesCalculator.calculate(
                    baseValue = baseValue,
                    strategy = GameScalingStrategy.BALANCED,
                    elementType = GameElementType.PLAYER,
                    config = testConfig
                )
            }
        }
        
        // Measure actual frame time
        val frameTime = measureTimeMillis {
            repeat(calculationsPerFrame) { i ->
                val baseValue = (i % 20 + 1) * 4f
                GamesCalculator.calculate(
                    baseValue = baseValue,
                    strategy = GameScalingStrategy.BALANCED,
                    elementType = GameElementType.PLAYER,
                    config = testConfig
                )
            }
        }.toDouble()
        
        val percentOfBudget = (frameTime / frameBudgetMs) * 100
        
        println("Frame simulation: ${calculationsPerFrame} calculations in ${frameTime}ms")
        println("Frame budget usage: ${percentOfBudget.toInt()}% of 16.67ms")
        
        // Should use less than 10% of frame budget
        assertTrue("Should use less than 10% of frame budget", percentOfBudget < 10.0)
        
        val stats = GameCacheFast.getFastCacheStats()
        println("Cache hit rate: ${(stats.hitRate * 100).toInt()}%")
        assertTrue("Cache hit rate should be excellent", stats.hitRate > 0.85f)
    }
    
    @Test
    fun testCacheVsNoCachePerformance() {
        val iterations = 100
        
        // Test WITH cache
        GameCacheFast.clearAll()
        GamesCore.setGlobalCache(true)
        
        // Warm up
        repeat(10) {
            GamesCalculator.calculate(
                48f, GameScalingStrategy.BALANCED, GameElementType.PLAYER, testConfig
            )
        }
        
        val withCacheTime = measureTimeMillis {
            repeat(iterations) {
                GamesCalculator.calculate(
                    48f, GameScalingStrategy.BALANCED, GameElementType.PLAYER, testConfig
                )
            }
        }
        
        // Test WITHOUT cache (force miss each time with different values)
        GameCacheFast.clearAll()
        
        val withoutCacheTime = measureTimeMillis {
            repeat(iterations) { i ->
                GamesCalculator.calculate(
                    48f + i * 0.01f, // Slightly different each time to force miss
                    GameScalingStrategy.BALANCED,
                    GameElementType.PLAYER,
                    testConfig
                )
            }
        }
        
        println("With cache: ${withCacheTime}ms")
        println("Without cache: ${withoutCacheTime}ms")
        println("Speedup: ${withoutCacheTime.toDouble() / withCacheTime}x")
        
        // Cache should be significantly faster
        assertTrue("Cache should provide speedup", withCacheTime < withoutCacheTime)
    }
    
    @Test
    fun testMemoryUsage() {
        GameCacheFast.clearAll()
        
        val runtime = Runtime.getRuntime()
        runtime.gc()
        Thread.sleep(100)
        
        val memBefore = runtime.totalMemory() - runtime.freeMemory()
        
        // Populate cache with diverse values
        repeat(500) { i ->
            val baseValue = (i % 50 + 1) * 2f
            GamesCalculator.calculate(
                baseValue,
                GameScalingStrategy.BALANCED,
                GameElementType.PLAYER,
                testConfig
            )
        }
        
        runtime.gc()
        Thread.sleep(100)
        
        val memAfter = runtime.totalMemory() - runtime.freeMemory()
        val memUsedKb = (memAfter - memBefore) / 1024.0
        
        println("Memory used by cache: ${memUsedKb}KB")
        
        // Cache should use minimal memory (<100KB for 500 entries)
        assertTrue("Cache should use minimal memory", memUsedKb < 100.0)
    }
}

