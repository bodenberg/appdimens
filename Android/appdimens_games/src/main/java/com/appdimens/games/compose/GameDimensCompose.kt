/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games 2.0 - Jetpack Compose Support
 *
 * Description:
 * Jetpack Compose extensions and composables for AppDimens Games.
 * Provides remember-based caching and Compose-friendly APIs.
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.games.compose

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import com.appdimens.games.core.calculation.GamesCalculator
import com.appdimens.games.core.strategy.*
import com.appdimens.games.core.models.*
import com.appdimens.games.core.cache.GameCacheFast
import com.appdimens.games.builders.GameDimensBuilder

/**
 * Remembers a game screen configuration.
 * 
 * Automatically updates when configuration changes.
 * 
 * @return GameScreenConfig for current screen
 */
@Composable
fun rememberGameScreenConfig(): GameScreenConfig {
    val configuration = LocalConfiguration.current
    
    return remember(
        configuration.screenWidthDp,
        configuration.screenHeightDp,
        configuration.smallestScreenWidthDp,
        configuration.densityDpi,
        configuration.uiMode
    ) {
        GameScreenConfig(
            screenWidthDp = configuration.screenWidthDp.toFloat(),
            screenHeightDp = configuration.screenHeightDp.toFloat(),
            smallestScreenWidthDp = configuration.smallestScreenWidthDp.toFloat(),
            densityDpi = configuration.densityDpi,
            uiMode = GameUiModeType.fromAndroidUiMode(configuration.uiMode)
        )
    }
}

/**
 * Extension: Converts Float to Dp with smart scaling.
 * 
 * @receiver Base value in dp
 * @return State<Dp> with calculated scaled value
 * 
 * @example
 * ```kotlin
 * @Composable
 * fun PlayerSprite() {
 *     val size = 64f.smartDp(GameElementType.PLAYER)
 *     Box(modifier = Modifier.size(size.value))
 * }
 * ```
 */
@Composable
fun Float.smartDp(
    elementType: GameElementType? = null,
    strategy: GameScalingStrategy? = null
): State<Dp> {
    val config = rememberGameScreenConfig()
    
    return remember(this, elementType, strategy, config) {
        val value = GamesCalculator.calculate(
            baseValue = this,
            strategy = strategy,
            elementType = elementType,
            config = config
        )
        mutableStateOf(value.dp)
    }
}

/**
 * Extension: Converts Int to Dp with smart scaling.
 */
@Composable
fun Int.smartDp(
    elementType: GameElementType? = null,
    strategy: GameScalingStrategy? = null
): State<Dp> {
    return this.toFloat().smartDp(elementType, strategy)
}

/**
 * Extension: Quick smart Dp (no state).
 * 
 * Use when you need immediate value without state management.
 * 
 * @receiver Base value in dp
 * @return Dp with calculated scaled value
 */
@Composable
fun Float.adp(
    elementType: GameElementType? = null,
    strategy: GameScalingStrategy? = null
): Dp {
    val config = rememberGameScreenConfig()
    
    return remember(this, elementType, strategy, config) {
        val value = GamesCalculator.calculate(
            baseValue = this,
            strategy = strategy,
            elementType = elementType,
            config = config
        )
        value.dp
    }
}

/**
 * Extension: Quick smart Dp for Int.
 */
@Composable
fun Int.adp(
    elementType: GameElementType? = null,
    strategy: GameScalingStrategy? = null
): Dp {
    return this.toFloat().adp(elementType, strategy)
}

/**
 * Extension: Balanced strategy Dp.
 */
@Composable
fun Float.balancedDp(elementType: GameElementType? = null): Dp {
    return this.adp(elementType, GameScalingStrategy.BALANCED)
}

/**
 * Extension: Balanced strategy Dp for Int.
 */
@Composable
fun Int.balancedDp(elementType: GameElementType? = null): Dp {
    return this.toFloat().balancedDp(elementType)
}

/**
 * Extension: Percentage strategy Dp.
 */
@Composable
fun Float.percentageDp(elementType: GameElementType? = null): Dp {
    return this.adp(elementType, GameScalingStrategy.PERCENTAGE)
}

/**
 * Extension: Default strategy Dp.
 */
@Composable
fun Float.defaultDp(elementType: GameElementType? = null): Dp {
    return this.adp(elementType, GameScalingStrategy.DEFAULT)
}

/**
 * Extension: Fluid strategy Dp.
 * 
 * @receiver Base value in dp
 * @param minValue Minimum value
 * @param maxValue Maximum value
 * @param minWidth Minimum width breakpoint (default 320dp)
 * @param maxWidth Maximum width breakpoint (default 768dp)
 * @return Dp with fluid scaling
 */
@Composable
fun Float.fluidDp(
    minValue: Float,
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f,
    elementType: GameElementType? = null
): Dp {
    val config = rememberGameScreenConfig()
    
    return remember(this, minValue, maxValue, minWidth, maxWidth, config) {
        val fluidParams = FluidParams(minValue, maxValue, minWidth, maxWidth)
        val value = GamesCalculator.calculate(
            baseValue = this,
            strategy = GameScalingStrategy.FLUID,
            elementType = elementType,
            config = config,
            fluidParams = fluidParams
        )
        value.dp
    }
}

/**
 * Extension: Fill strategy Dp (for backgrounds).
 */
@Composable
fun Float.fillDp(elementType: GameElementType? = null): Dp {
    return this.adp(elementType, GameScalingStrategy.FILL)
}

/**
 * Extension: Fit strategy Dp (for viewports).
 */
@Composable
fun Float.fitDp(elementType: GameElementType? = null): Dp {
    return this.adp(elementType, GameScalingStrategy.FIT)
}

/**
 * Extension: Converts to Sp (for text).
 */
@Composable
fun Float.smartSp(elementType: GameElementType? = null): TextUnit {
    val dpValue = this.adp(elementType)
    return dpValue.value.sp
}

/**
 * Extension: Fluid Sp (for text).
 */
@Composable
fun Float.fluidSp(
    minValue: Float,
    maxValue: Float,
    minWidth: Float = 320f,
    maxWidth: Float = 768f
): TextUnit {
    val dpValue = this.fluidDp(minValue, maxValue, minWidth, maxWidth, GameElementType.TEXT)
    return dpValue.value.sp
}

/**
 * Remembers auto-sized Dp value.
 * 
 * Similar to TextView autoSizeText but for Compose.
 * Automatically adjusts size to fit container.
 * 
 * @param baseValue Base value in dp
 * @param minValue Minimum value in dp
 * @param maxValue Maximum value in dp
 * @param containerWidth Container width in Dp
 * @param containerHeight Container height in Dp
 * @return State<Dp> with auto-sized value
 * 
 * @example
 * ```kotlin
 * @Composable
 * fun AutoSizedText() {
 *     BoxWithConstraints {
 *         val fontSize = rememberGameAutoSizeDp(
 *             baseValue = 16f,
 *             minValue = 12f,
 *             maxValue = 24f,
 *             containerWidth = maxWidth,
 *             containerHeight = maxHeight
 *         )
 *         Text("Auto-sized!", fontSize = fontSize.value.sp)
 *     }
 * }
 * ```
 */
@Composable
fun rememberGameAutoSizeDp(
    baseValue: Float,
    minValue: Float,
    maxValue: Float,
    containerWidth: Dp,
    containerHeight: Dp,
    granularity: Float = 1f
): State<Dp> {
    val density = LocalDensity.current
    
    return remember(baseValue, minValue, maxValue, containerWidth, containerHeight, granularity) {
        val config = AutoSizeConfig(
            baseValue = baseValue,
            minValue = minValue,
            maxValue = maxValue,
            containerWidthDp = with(density) { containerWidth.toPx() / density.density },
            containerHeightDp = with(density) { containerHeight.toPx() / density.density },
            granularity = granularity
        )
        
        val presets = config.getOrGeneratePresets()
        val availableSize = minOf(
            with(density) { containerWidth.toPx() / density.density },
            with(density) { containerHeight.toPx() / density.density }
        )
        
        val calculated = GamesCalculator.findBestPreset(presets, availableSize)
        mutableStateOf(calculated.dp)
    }
}

/**
 * Composable: Game dimension with builder pattern.
 * 
 * Allows using the full builder API in Compose.
 * 
 * @param baseValue Base value in dp
 * @param builder Builder configuration
 * @return Dp with calculated value
 * 
 * @example
 * ```kotlin
 * @Composable
 * fun ComplexElement() {
 *     val size = GameDimens(48f) {
 *         forElement(GameElementType.HUD_BUTTON)
 *         withStrategy(GameScalingStrategy.BALANCED)
 *         withConstraints(minValue = 32f, maxValue = 64f)
 *         forTV(64f)
 *         forTablet(56f)
 *     }
 *     Box(modifier = Modifier.size(size))
 * }
 * ```
 */
@Composable
fun GameDimens(
    baseValue: Float,
    builder: GameDimensBuilder.() -> Unit
): Dp {
    val config = rememberGameScreenConfig()
    
    return remember(baseValue, config) {
        val dimensBuilder = GameDimensBuilder(baseValue)
        dimensBuilder.builder()
        dimensBuilder.build(config).dp
    }
}

/**
 * Composable: Performance monitor.
 * 
 * Displays cache statistics in debug builds.
 * 
 * @param show Whether to show the monitor (default: true in debug)
 * 
 * @example
 * ```kotlin
 * @Composable
 * fun App() {
 *     Box {
 *         // Your game content
 *         GamePerformanceMonitor()
 *     }
 * }
 * ```
 */
@Composable
fun GamePerformanceMonitor(show: Boolean = true) {
    if (!show) return
    
    var stats by remember { mutableStateOf(GameCacheFast.getFastCacheStats()) }
    
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000) // Update every second
        stats = GameCacheFast.getFastCacheStats()
    }
    
    androidx.compose.material3.Text(
        text = "Cache: ${stats.totalEntries}/${stats.capacity} (${(stats.hitRate * 100).toInt()}%)",
        style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
        color = if (stats.isPerformingWell()) 
            androidx.compose.ui.graphics.Color.Green 
        else 
            androidx.compose.ui.graphics.Color.Red
    )
}

/**
 * Provides game screen configuration to composition.
 * 
 * Useful for accessing config in multiple composables.
 * 
 * @param content Composable content
 * 
 * @example
 * ```kotlin
 * @Composable
 * fun App() {
 *     ProvideGameScreenConfig {
 *         // Child composables can access config
 *         val config = LocalGameScreenConfig.current
 *     }
 * }
 * ```
 */
@Composable
fun ProvideGameScreenConfig(content: @Composable () -> Unit) {
    val config = rememberGameScreenConfig()
    CompositionLocalProvider(LocalGameScreenConfig provides config) {
        content()
    }
}

/**
 * CompositionLocal for game screen configuration.
 */
val LocalGameScreenConfig = compositionLocalOf<GameScreenConfig> {
    error("No GameScreenConfig provided")
}

/**
 * Pre-defined dimension sets for common game elements.
 */
object GameDimens {
    /**
     * Standard HUD button sizes.
     */
    object HudButton {
        @Composable
        fun small() = 40f.balancedDp(GameElementType.HUD_BUTTON)
        
        @Composable
        fun medium() = 48f.balancedDp(GameElementType.HUD_BUTTON)
        
        @Composable
        fun large() = 56f.balancedDp(GameElementType.HUD_BUTTON)
    }
    
    /**
     * Standard text sizes.
     */
    object Text {
        @Composable
        fun small() = 12f.fluidSp(10f, 14f)
        
        @Composable
        fun body() = 16f.fluidSp(14f, 18f)
        
        @Composable
        fun title() = 20f.fluidSp(18f, 24f)
        
        @Composable
        fun headline() = 28f.fluidSp(24f, 32f)
    }
    
    /**
     * Standard spacing values.
     */
    object Spacing {
        @Composable
        fun tiny() = 4f.balancedDp(GameElementType.SPACING)
        
        @Composable
        fun small() = 8f.balancedDp(GameElementType.SPACING)
        
        @Composable
        fun medium() = 16f.balancedDp(GameElementType.SPACING)
        
        @Composable
        fun large() = 24f.balancedDp(GameElementType.SPACING)
        
        @Composable
        fun xlarge() = 32f.balancedDp(GameElementType.SPACING)
    }
    
    /**
     * Standard player sizes.
     */
    object Player {
        @Composable
        fun small() = 48f.balancedDp(GameElementType.PLAYER)
        
        @Composable
        fun medium() = 64f.balancedDp(GameElementType.PLAYER)
        
        @Composable
        fun large() = 80f.balancedDp(GameElementType.PLAYER)
    }
}

