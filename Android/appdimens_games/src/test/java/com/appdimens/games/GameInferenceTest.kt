/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Inference Tests
 *
 * Description:
 * Unit tests for strategy inference system.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games

import com.appdimens.games.core.calculation.GamesCalculator
import com.appdimens.games.core.strategy.*
import com.appdimens.games.core.models.*
import org.junit.Test
import org.junit.Assert.*

class GameInferenceTest {
    
    @Test
    fun testPlayerInfersBalanced() {
        val config = GameScreenConfig(
            screenWidthDp = 720f,
            screenHeightDp = 1024f,
            smallestScreenWidthDp = 720f,
            densityDpi = 320,
            uiMode = GameUiModeType.NORMAL
        )
        
        val strategy = GamesCalculator.inferStrategy(
            elementType = GameElementType.PLAYER,
            config = config,
            hasFluidConfig = false,
            hasBounds = false
        )
        
        assertEquals("Player should infer BALANCED", GameScalingStrategy.BALANCED, strategy)
    }
    
    @Test
    fun testHudTextInfersFluid() {
        val config = GameScreenConfig(
            screenWidthDp = 360f,
            screenHeightDp = 640f,
            smallestScreenWidthDp = 360f,
            densityDpi = 420,
            uiMode = GameUiModeType.NORMAL
        )
        
        val recommended = GameElementType.HUD_TEXT.getRecommendedStrategy()
        
        assertEquals("HUD_TEXT should recommend FLUID", GameScalingStrategy.FLUID, recommended)
    }
    
    @Test
    fun testBackgroundInfersFill() {
        val recommended = GameElementType.BACKGROUND.getRecommendedStrategy()
        
        assertEquals("BACKGROUND should recommend FILL", GameScalingStrategy.FILL, recommended)
    }
    
    @Test
    fun testDividerInfersNone() {
        val recommended = GameElementType.DIVIDER.getRecommendedStrategy()
        
        assertEquals("DIVIDER should recommend NONE", GameScalingStrategy.NONE, recommended)
    }
    
    @Test
    fun testDeviceTypeClassification() {
        // Phone
        val phone = GameDeviceType.from(360f, GameUiModeType.NORMAL)
        assertEquals("360dp should be PHONE_NORMAL", GameDeviceType.PHONE_NORMAL, phone)
        assertTrue("Should be phone", phone.isPhone())
        
        // Tablet
        val tablet = GameDeviceType.from(720f, GameUiModeType.NORMAL)
        assertEquals("720dp should be TABLET_SMALL", GameDeviceType.TABLET_SMALL, tablet)
        assertTrue("Should be tablet", tablet.isTablet())
        
        // TV
        val tv = GameDeviceType.from(1920f, GameUiModeType.NORMAL)
        assertEquals("1920dp should be TV", GameDeviceType.TV, tv)
        assertTrue("Should be large screen", tv.isLargeScreen())
        
        // Watch
        val watch = GameDeviceType.from(280f, GameUiModeType.NORMAL)
        assertEquals("280dp should be WATCH", GameDeviceType.WATCH, watch)
        
        // TV by UI mode
        val tvByMode = GameDeviceType.from(800f, GameUiModeType.TELEVISION)
        assertEquals("TELEVISION mode should be TV", GameDeviceType.TV, tvByMode)
    }
    
    @Test
    fun testElementTypeCategories() {
        assertEquals("PLAYER should be Characters", "Characters", 
            GameElementType.PLAYER.getCategory())
        
        assertEquals("HUD_BUTTON should be UI & HUD", "UI & HUD", 
            GameElementType.HUD_BUTTON.getCategory())
        
        assertEquals("PARTICLE should be Effects & Particles", "Effects & Particles", 
            GameElementType.PARTICLE.getCategory())
        
        assertEquals("BACKGROUND should be World Elements", "World Elements", 
            GameElementType.BACKGROUND.getCategory())
    }
    
    @Test
    fun testElementTypeIsGameSpecific() {
        assertTrue("PLAYER should be game-specific", 
            GameElementType.PLAYER.isGameSpecific())
        
        assertTrue("ENEMY should be game-specific", 
            GameElementType.ENEMY.isGameSpecific())
        
        assertFalse("BUTTON should not be game-specific", 
            GameElementType.BUTTON.isGameSpecific())
        
        assertFalse("TEXT should not be game-specific", 
            GameElementType.TEXT.isGameSpecific())
    }
    
    @Test
    fun testStrategyRecommendations() {
        // Game elements should recommend BALANCED
        assertTrue("PLAYER should recommend game-friendly strategy",
            GameElementType.PLAYER.getRecommendedStrategy().isRecommendedForGames())
        
        assertTrue("ENEMY should recommend game-friendly strategy",
            GameElementType.ENEMY.getRecommendedStrategy().isRecommendedForGames())
        
        // UI elements should have appropriate strategies
        assertEquals("HUD_BUTTON should recommend DEFAULT",
            GameScalingStrategy.DEFAULT,
            GameElementType.HUD_BUTTON.getRecommendedStrategy())
    }
}

