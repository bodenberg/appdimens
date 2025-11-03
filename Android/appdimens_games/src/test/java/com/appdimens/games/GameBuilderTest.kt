/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Builder Tests
 *
 * Description:
 * Unit tests for GameDimensBuilder fluent API.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games

import com.appdimens.games.builders.GameDimensBuilder
import com.appdimens.games.core.strategy.*
import com.appdimens.games.core.models.*
import org.junit.Test
import org.junit.Assert.*

class GameBuilderTest {
    
    private val testConfig = GameScreenConfig(
        screenWidthDp = 360f,
        screenHeightDp = 640f,
        smallestScreenWidthDp = 360f,
        densityDpi = 420,
        uiMode = GameUiModeType.NORMAL
    )
    
    @Test
    fun testBuilderBasic() {
        val builder = GameDimensBuilder(48f)
        val result = builder.build(testConfig)
        
        assertTrue("Builder should produce valid result", result > 0f)
    }
    
    @Test
    fun testBuilderWithStrategy() {
        val result = GameDimensBuilder(48f)
            .withStrategy(GameScalingStrategy.BALANCED)
            .build(testConfig)
        
        assertTrue("Builder with strategy should work", result > 0f)
    }
    
    @Test
    fun testBuilderWithElementType() {
        val result = GameDimensBuilder(64f)
            .withElementType(GameElementType.PLAYER)
            .build(testConfig)
        
        assertTrue("Builder with element type should work", result > 0f)
    }
    
    @Test
    fun testBuilderWithConstraints() {
        val result = GameDimensBuilder(100f)
            .withStrategy(GameScalingStrategy.PERCENTAGE)
            .withConstraints(
                minValue = 50f,
                maxValue = 80f
            )
            .build(testConfig)
        
        assertTrue("Result should respect min constraint", result >= 50f)
        assertTrue("Result should respect max constraint", result <= 80f)
    }
    
    @Test
    fun testBuilderWithMinMaxValue() {
        val result = GameDimensBuilder(48f)
            .withMinValue(40f)
            .withMaxValue(60f)
            .build(testConfig)
        
        assertTrue("Result should be within bounds", result in 40f..60f)
    }
    
    @Test
    fun testBuilderChaining() {
        val result = GameDimensBuilder(48f)
            .withStrategy(GameScalingStrategy.BALANCED)
            .withElementType(GameElementType.HUD_BUTTON)
            .withConstraints(minValue = 32f, maxValue = 64f)
            .withScreenType(com.appdimens.games.core.strategy.GameScreenType.LOWEST)
            .build(testConfig)
        
        assertTrue("Chained builder should work", result in 32f..64f)
    }
    
    @Test
    fun testBuilderWithFluid() {
        val result = GameDimensBuilder(16f)
            .withFluid(
                minValue = 12f,
                maxValue = 24f,
                minWidth = 300f,
                maxWidth = 800f
            )
            .build(testConfig)
        
        assertTrue("Fluid builder should work", result in 12f..24f)
    }
    
    @Test
    fun testBuilderWithPerceptual() {
        val result = GameDimensBuilder(48f)
            .withPerceptual(
                model = GamePerceptualModel.BALANCED,
                sensitivity = 0.40f
            )
            .build(testConfig)
        
        assertTrue("Perceptual builder should work", result > 0f)
    }
    
    @Test
    fun testBuilderForTV() {
        val tvConfig = GameScreenConfig(
            screenWidthDp = 1920f,
            screenHeightDp = 1080f,
            smallestScreenWidthDp = 1080f,
            densityDpi = 213,
            uiMode = GameUiModeType.TELEVISION
        )
        
        val result = GameDimensBuilder(48f)
            .forTV(64f)  // Should override to 64dp on TV
            .build(tvConfig)
        
        assertEquals("TV override should work", 64f, result, 0.001f)
    }
    
    @Test
    fun testBuilderForTablet() {
        val tabletConfig = GameScreenConfig(
            screenWidthDp = 720f,
            screenHeightDp = 1024f,
            smallestScreenWidthDp = 720f,
            densityDpi = 320,
            uiMode = GameUiModeType.NORMAL
        )
        
        val result = GameDimensBuilder(48f)
            .forTablet(56f)  // Should override to 56dp on tablets (sw600dp)
            .build(tabletConfig)
        
        assertEquals("Tablet override should work", 56f, result, 0.001f)
    }
}

