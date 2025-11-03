/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Calculator Tests
 *
 * Description:
 * Unit tests for GamesCalculator and all scaling strategies.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games

import com.appdimens.games.core.calculation.GamesCalculator
import com.appdimens.games.core.strategy.*
import com.appdimens.games.core.models.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class GamesCalculatorTest {
    
    private lateinit var phoneConfig: com.appdimens.games.core.models.GameScreenConfig
    private lateinit var tabletConfig: com.appdimens.games.core.models.GameScreenConfig
    private lateinit var tvConfig: com.appdimens.games.core.models.GameScreenConfig
    
    @Before
    fun setup() {
        // Phone configuration (360x640dp)
        phoneConfig = com.appdimens.games.core.models.GameScreenConfig(
            screenWidthDp = 360f,
            screenHeightDp = 640f,
            smallestScreenWidthDp = 360f,
            densityDpi = 420,
            uiMode = com.appdimens.games.core.strategy.GameUiModeType.NORMAL
        )
        
        // Tablet configuration (720x1024dp)
        tabletConfig = com.appdimens.games.core.models.GameScreenConfig(
            screenWidthDp = 720f,
            screenHeightDp = 1024f,
            smallestScreenWidthDp = 720f,
            densityDpi = 320,
            uiMode = com.appdimens.games.core.strategy.GameUiModeType.NORMAL
        )
        
        // TV configuration (1920x1080dp)
        tvConfig = com.appdimens.games.core.models.GameScreenConfig(
            screenWidthDp = 1920f,
            screenHeightDp = 1080f,
            smallestScreenWidthDp = 1080f,
            densityDpi = 213,
            uiMode = com.appdimens.games.core.strategy.GameUiModeType.TELEVISION
        )
    }
    
    @Test
    fun testDefaultStrategy() {
        val result = GamesCalculator.calculate(
            baseValue = 48f,
            strategy = GameScalingStrategy.DEFAULT,
            elementType = null,
            config = phoneConfig
        )
        
        // DEFAULT should provide subtle scaling
        assertTrue("DEFAULT should scale moderately", result in 40f..60f)
    }
    
    @Test
    fun testPercentageStrategy() {
        val result = GamesCalculator.calculate(
            baseValue = 100f,
            strategy = GameScalingStrategy.PERCENTAGE,
            elementType = null,
            config = tabletConfig
        )
        
        // PERCENTAGE should scale proportionally
        // 100dp at 300dp base -> 240dp at 720dp (2.4x)
        val expected = 100f * (720f / 300f)
        assertEquals("PERCENTAGE should scale proportionally", expected, result, 1f)
    }
    
    @Test
    fun testBalancedStrategy() {
        // Phone (< 480dp): should use linear
        val phoneResult = GamesCalculator.calculate(
            baseValue = 48f,
            strategy = GameScalingStrategy.BALANCED,
            elementType = null,
            config = phoneConfig
        )
        
        // Tablet (> 480dp): should use logarithmic
        val tabletResult = GamesCalculator.calculate(
            baseValue = 48f,
            strategy = GameScalingStrategy.BALANCED,
            elementType = null,
            config = tabletConfig
        )
        
        // Tablet should scale less aggressively than pure linear
        val linearTablet = 48f * (720f / 300f) // 115.2dp
        assertTrue("BALANCED should control scaling on tablets", tabletResult < linearTablet)
    }
    
    @Test
    fun testNoneStrategy() {
        val result = GamesCalculator.calculate(
            baseValue = 48f,
            strategy = GameScalingStrategy.NONE,
            elementType = null,
            config = tabletConfig
        )
        
        assertEquals("NONE should return base value", 48f, result, 0.001f)
    }
    
    @Test
    fun testFitStrategy() {
        val result = GamesCalculator.calculate(
            baseValue = 100f,
            strategy = GameScalingStrategy.FIT,
            elementType = null,
            config = phoneConfig
        )
        
        // FIT uses min ratio
        val ratioW = 360f / 300f  // 1.2
        val ratioH = 640f / 533f  // 1.2
        val minRatio = minOf(ratioW, ratioH)
        val expected = 100f * minRatio
        
        assertEquals("FIT should use minimum ratio", expected, result, 1f)
    }
    
    @Test
    fun testFillStrategy() {
        val result = GamesCalculator.calculate(
            baseValue = 100f,
            strategy = GameScalingStrategy.FILL,
            elementType = null,
            config = phoneConfig
        )
        
        // FILL uses max ratio
        val ratioW = 360f / 300f
        val ratioH = 640f / 533f
        val maxRatio = maxOf(ratioW, ratioH)
        val expected = 100f * maxRatio
        
        assertEquals("FILL should use maximum ratio", expected, result, 1f)
    }
    
    @Test
    fun testConstraints() {
        val result = GamesCalculator.calculate(
            baseValue = 48f,
            strategy = GameScalingStrategy.PERCENTAGE,
            elementType = null,
            config = tabletConfig,
            constraints = Constraints(
                minValue = 50f,
                maxValue = 80f
            )
        )
        
        assertTrue("Result should respect constraints", result in 50f..80f)
    }
    
    @Test
    fun testElementTypeInference() {
        // Player should auto-select BALANCED
        val playerResult = GamesCalculator.calculate(
            baseValue = 64f,
            strategy = null, // Auto-infer
            elementType = GameElementType.PLAYER,
            config = tabletConfig
        )
        
        // Should use BALANCED strategy (controlled scaling)
        assertTrue("Player should use controlled scaling", playerResult > 0f)
    }
    
    @Test
    fun testCachePerformance() {
        val startTime = System.nanoTime()
        
        // First call (cache miss)
        val result1 = GamesCalculator.calculate(
            baseValue = 48f,
            strategy = GameScalingStrategy.BALANCED,
            elementType = GameElementType.HUD_BUTTON,
            config = phoneConfig
        )
        
        val firstCallTime = System.nanoTime() - startTime
        
        // Second call (cache hit)
        val cacheStartTime = System.nanoTime()
        val result2 = GamesCalculator.calculate(
            baseValue = 48f,
            strategy = GameScalingStrategy.BALANCED,
            elementType = GameElementType.HUD_BUTTON,
            config = phoneConfig
        )
        val cacheCallTime = System.nanoTime() - cacheStartTime
        
        // Results should be identical
        assertEquals("Cached result should match", result1, result2, 0.001f)
        
        // Cache should be significantly faster
        assertTrue("Cache should be faster", cacheCallTime < firstCallTime)
        
        // Cache hit should be very fast (<1µs = 1000ns)
        assertTrue("Cache hit should be very fast", cacheCallTime < 1000)
    }
    
    @Test
    fun testBinarySearchPresets() {
        val presets = floatArrayOf(10f, 20f, 30f, 40f, 50f, 60f, 70f, 80f)
        
        // Test exact match
        assertEquals(30f, GamesCalculator.findBestPreset(presets, 30f), 0.001f)
        
        // Test between values (should return smaller)
        assertEquals(30f, GamesCalculator.findBestPreset(presets, 35f), 0.001f)
        
        // Test below minimum
        assertEquals(10f, GamesCalculator.findBestPreset(presets, 5f), 0.001f)
        
        // Test above maximum
        assertEquals(80f, GamesCalculator.findBestPreset(presets, 100f), 0.001f)
    }
    
    @Test
    fun testMultipleDeviceTypes() {
        val baseValue = 48f
        
        val phoneResult = GamesCalculator.calculate(
            baseValue, GameScalingStrategy.BALANCED, null, phoneConfig
        )
        
        val tabletResult = GamesCalculator.calculate(
            baseValue, GameScalingStrategy.BALANCED, null, tabletConfig
        )
        
        val tvResult = GamesCalculator.calculate(
            baseValue, GameScalingStrategy.BALANCED, null, tvConfig
        )
        
        // All should produce valid results
        assertTrue("Phone result valid", phoneResult > 0f)
        assertTrue("Tablet result valid", tabletResult > 0f)
        assertTrue("TV result valid", tvResult > 0f)
        
        // Tablet should not be too large (logarithmic control)
        val linearTablet = baseValue * (720f / 300f)
        assertTrue("Tablet should use logarithmic control", tabletResult < linearTablet)
    }
    
    @Test
    fun testFluidStrategy() {
        val result = GamesCalculator.calculate(
            baseValue = 16f,
            strategy = GameScalingStrategy.FLUID,
            elementType = null,
            config = phoneConfig,
            fluidParams = FluidParams(
                minValue = 12f,
                maxValue = 24f,
                minWidth = 300f,
                maxWidth = 800f
            )
        )
        
        // Should interpolate between min and max
        assertTrue("FLUID should be between min and max", result in 12f..24f)
    }
    
    @Test
    fun testScreenTypeOrientation() {
        // LOWEST on portrait should give width (360dp)
        val lowest = GamesCalculator.calculate(
            baseValue = 100f,
            strategy = GameScalingStrategy.PERCENTAGE,
            elementType = null,
            config = phoneConfig,
            screenType = com.appdimens.games.core.strategy.GameScreenType.LOWEST
        )
        
        // HIGHEST on portrait should give height (640dp)
        val highest = GamesCalculator.calculate(
            baseValue = 100f,
            strategy = GameScalingStrategy.PERCENTAGE,
            elementType = null,
            config = phoneConfig,
            screenType = com.appdimens.games.core.strategy.GameScreenType.HIGHEST
        )
        
        // HIGHEST should be larger (based on larger dimension)
        assertTrue("HIGHEST should use larger dimension", highest > lowest)
    }
}

