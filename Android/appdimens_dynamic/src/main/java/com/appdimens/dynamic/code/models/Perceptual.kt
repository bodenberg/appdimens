/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens 2.0 - Perceptual Scaling (Code)
 *
 * Description:
 * Platform-specific wrapper for code package that delegates to core implementation.
 * Provides true perceptual scaling based on psychophysics research.
 * Compatible with the traditional Android View System (XML).
 *
 * Licensed under the Apache License, Version 2.0
 */
package com.appdimens.dynamic.code.models

import android.content.res.Configuration
import android.content.res.Resources
import com.appdimens.dynamic.code.AppDimens
import com.appdimens.dynamic.core.cache.AutoCacheFast
import com.appdimens.dynamic.core.models.PerceptualModel
import com.appdimens.dynamic.core.optimization.PerceptualCore
import com.appdimens.library.BaseOrientation
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeType
import com.appdimens.library.UiModeQualifierEntry

/**
 * [EN] Builder class for true perceptual scaling with psychophysics-based models.
 * 
 * This is a platform-specific wrapper for the Android View System that delegates
 * all calculations to PerceptualCore. It provides a fluent builder API
 * for configuring perceptual scaling parameters.
 * 
 * Unlike the legacy Fixed model (97% linear), this implements genuine logarithmic
 * or power-law scaling that provides real control over oversizing on large screens.
 * 
 * Usage:
 * ```kotlin
 * // Balanced model (recommended) - linear on phones, log on tablets
 * val buttonHeight = 48.perceptualDp()
 *     .model(PerceptualModel.BALANCED)
 *     .toDp(resources)
 * 
 * // LOGARITHMIC: Pure log
 * val padding = 16.perceptualDp()
 *     .model(PerceptualModel.LOGARITHMIC, sensitivity = 0.40f)
 *     .toDp(resources)
 * 
 * // POWER: Configurable exponent
 * val textSize = 18.perceptualSp()
 *     .model(PerceptualModel.POWER)
 *     .powerExponent(0.75f)
 *     .toSp(resources)
 * ```
 * 
 * [PT] Classe builder para escalonamento perceptual verdadeiro com modelos baseados em psicofísica.
 * 
 * Este é um wrapper específico da plataforma para o Sistema de Views Android que delega
 * todos os cálculos para PerceptualCore.
 */
class AppDimensPerceptual private constructor(
    private val initialBaseDp: Float,
    private val isTextSize: Boolean = false
) {
    
    // ============================================
    // CONFIGURATION STATE
    // ============================================
    
    private var model: PerceptualModel = PerceptualModel.BALANCED
    private var sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY
    private var powerExponent: Float = PerceptualCore.DEFAULT_POWER_EXPONENT
    private var transitionPoint: Float = PerceptualCore.DEFAULT_TRANSITION_POINT
    private var applyAspectRatioAdjustment: Boolean = true
    private var arSensitivity: Float = PerceptualCore.DEFAULT_AR_SENSITIVITY
    private var screenType: ScreenType = ScreenType.LOWEST
    private var baseOrientation: BaseOrientation = BaseOrientation.AUTO
    private var ignoreMultiViewAdjustment: Boolean = false
    private var enableCache: Boolean = true
    
    // Custom qualifiers (same as Fixed for compatibility)
    private val customIntersectionMap: MutableMap<UiModeQualifierEntry, Float> = mutableMapOf()
    private val customUiModeMap: MutableMap<UiModeType, Float> = mutableMapOf()
    private val customDpMap: MutableMap<DpQualifierEntry, Float> = mutableMapOf()
    
    // Cache: Now uses shared AutoCacheFast (lock-free)
    // Removed per-instance cache in favor of unified fast cache
    private var lastScreenConfig: Triple<Float, Float, Int>? = null
    
    companion object {
        /**
         * [EN] Factory method to create perceptual dimension from dp value
         * [PT] Método factory para criar dimensão perceptual de valor em dp
         */
        fun fromDp(value: Float): AppDimensPerceptual = 
            AppDimensPerceptual(value, isTextSize = false)
        
        /**
         * [EN] Factory method to create perceptual dimension from sp value (for text)
         * [PT] Método factory para criar dimensão perceptual de valor em sp (para texto)
         */
        fun fromSp(value: Float): AppDimensPerceptual = 
            AppDimensPerceptual(value, isTextSize = true)
    }
    
    // ============================================
    // CONFIGURATION METHODS (FLUENT API)
    // ============================================
    
    /**
     * [EN] Sets the perceptual model to use
     * 
     * @param model The scaling model (LOGARITHMIC, POWER, BALANCED)
     * @param sensitivity For logarithmic and balanced models (default 0.40)
     * @return This instance for method chaining
     * 
     * [PT] Define o modelo perceptual a usar
     */
    fun model(
        model: PerceptualModel, 
        sensitivity: Float = PerceptualCore.DEFAULT_SENSITIVITY
    ): AppDimensPerceptual {
        this.model = model
        this.sensitivity = sensitivity
        return this
    }
    
    /**
     * [EN] Sets the power exponent for Stevens' Power Law
     * 
     * @param exponent Value between 0.5-1.0 (lower = more control on large screens)
     *                 Typical: 0.7-0.9, Default: 0.75
     * @return This instance for method chaining
     * 
     * [PT] Define o expoente de potência para a Lei de Potência de Stevens
     */
    fun powerExponent(exponent: Float): AppDimensPerceptual {
        this.powerExponent = exponent.coerceIn(0.5f, 1.0f)
        return this
    }
    
    /**
     * [EN] Sets the transition point for Balanced model (where logarithmic starts)
     * 
     * @param point Screen dimension in dp (default 480dp)
     * @return This instance for method chaining
     * 
     * [PT] Define o ponto de transição para o modelo Híbrido
     */
    fun transitionPoint(point: Float): AppDimensPerceptual {
        this.transitionPoint = point
        return this
    }
    
    /**
     * [EN] Enables/disables aspect ratio compensation
     * 
     * @param apply Whether to apply aspect ratio adjustment
     * @param sensitivity Optional custom sensitivity (default 0.08)
     * @return This instance for method chaining
     * 
     * [PT] Habilita/desabilita compensação de aspect ratio
     */
    fun aspectRatio(apply: Boolean, sensitivity: Float? = null): AppDimensPerceptual {
        this.applyAspectRatioAdjustment = apply
        if (sensitivity != null) {
            this.arSensitivity = sensitivity
        }
        return this
    }
    
    /**
     * [EN] Sets the screen type to use (LOWEST or HIGHEST dimension)
     * 
     * @param type Screen type (LOWEST uses smallest dimension, HIGHEST uses largest)
     * @return This instance for method chaining
     * 
     * [PT] Define o tipo de tela a usar
     */
    fun type(type: ScreenType): AppDimensPerceptual {
        this.screenType = type
        return this
    }
    
    /**
     * [EN] Sets base orientation for auto-inversion
     * 
     * @param orientation Base orientation (PORTRAIT, LANDSCAPE, AUTO)
     * @return This instance for method chaining
     * 
     * [PT] Define orientação base para auto-inversão
     */
    fun baseOrientation(orientation: BaseOrientation): AppDimensPerceptual {
        this.baseOrientation = orientation
        return this
    }
    
    /**
     * [EN] Configures multi-window adjustment behavior
     * 
     * @param ignore Whether to ignore multi-view adjustments
     * @return This instance for method chaining
     * 
     * [PT] Configura comportamento de ajuste multi-janela
     */
    fun multiViewAdjustment(ignore: Boolean): AppDimensPerceptual {
        this.ignoreMultiViewAdjustment = ignore
        return this
    }
    
    /**
     * [EN] Enables/disables caching
     * 
     * @param enable Whether to enable caching
     * @return This instance for method chaining
     * 
     * [PT] Habilita/desabilita cache
     */
    fun cache(enable: Boolean): AppDimensPerceptual {
        this.enableCache = enable
        return this
    }
    
    // ============================================
    // CUSTOM QUALIFIERS (COMPATIBILITY WITH FIXED)
    // ============================================
    
    /**
     * [EN] Sets custom value for specific UI mode + dp qualifier intersection
     * [PT] Define valor customizado para interseção específica de modo UI + qualificador dp
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Float
    ): AppDimensPerceptual {
        val entry = UiModeQualifierEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(type = qualifierType, value = qualifierValue)
        )
        customIntersectionMap[entry] = customValue
        return this
    }
    
    /**
     * [EN] Sets custom value for specific UI mode
     * [PT] Define valor customizado para modo UI específico
     */
    fun screen(type: UiModeType, customValue: Float): AppDimensPerceptual {
        customUiModeMap[type] = customValue
        return this
    }
    
    /**
     * [EN] Sets custom value for specific dp qualifier
     * [PT] Define valor customizado para qualificador dp específico
     */
    fun screen(
        type: DpQualifier,
        value: Int,
        customValue: Float
    ): AppDimensPerceptual {
        val entry = DpQualifierEntry(type = type, value = value)
        customDpMap[entry] = customValue
        return this
    }
    
    // ============================================
    // CALCULATION METHODS (DELEGATE TO CORE)
    // ============================================
    
    /**
     * [EN] Calculates the final adjusted value by delegating to core implementation
     * 
     * @param configuration Current screen configuration
     * @return Scaled value in dp
     * 
     * [PT] Calcula o valor final ajustado delegando para implementação core
     */
    fun calculateAdjustedValue(configuration: Configuration): Float {
        val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentScreenHeightDp = configuration.screenHeightDp.toFloat()
        
        // Check and invalidate cache if needed
        checkAndInvalidateCacheIfNeeded(configuration)
        
        // Create screen config
        val screenConfig = PerceptualCore.ScreenConfig(
            smallestWidthDp = smallestWidthDp,
            screenWidthDp = currentScreenWidthDp,
            screenHeightDp = currentScreenHeightDp,
            uiMode = UiModeType.fromConfiguration(configuration.uiMode)
        )
        
        // Use AutoCacheFast for lock-free caching
        if (AppDimens.globalCacheEnabled && enableCache) {
            // Compute hash from configuration and parameters
            var hash = AutoCacheFast.computeHash(
                baseValue = initialBaseDp,
                screenWidthDp = currentScreenWidthDp,
                screenHeightDp = currentScreenHeightDp,
                smallestWidthDp = smallestWidthDp,
                strategyOrdinal = model.ordinal
            )
            
            // Mix in additional parameters
            hash = hash xor System.identityHashCode(this)
            hash = hash xor screenType.ordinal
            hash = hash xor baseOrientation.ordinal
            
            return AutoCacheFast.rememberFast(hash) {
                calculatePerceptualValue(configuration, screenConfig)
            }
        } else {
            // Cache disabled: direct calculation
            return calculatePerceptualValue(configuration, screenConfig)
        }
    }
    
    /**
     * Internal calculation method (separated for caching).
     */
    private fun calculatePerceptualValue(
        configuration: Configuration,
        screenConfig: PerceptualCore.ScreenConfig
    ): Float {
        val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        
        // Check multi-window if needed
        val shouldIgnoreAdjustment = if (ignoreMultiViewAdjustment) {
            val isLayoutSplit = configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK != 
                               Configuration.SCREENLAYOUT_SIZE_MASK
            val isSmallDifference = (smallestWidthDp - currentScreenWidthDp) < (smallestWidthDp * 0.1)
            isLayoutSplit && !isSmallDifference
        } else {
            false
        }
        
        // Calculate final value
        return if (shouldIgnoreAdjustment) {
            // If multi-window, return base value without scaling
            val config = createCoreConfig()
            PerceptualCore.resolveFinalBaseDp(screenConfig, config)
        } else {
            // Delegate to core for calculation
            PerceptualCore.calculateScaledValue(screenConfig, createCoreConfig())
        }
    }
    
    /**
     * [EN] Creates core configuration from current builder state
     * [PT] Cria configuração core do estado atual do builder
     */
    private fun createCoreConfig(): PerceptualCore.PerceptualConfig {
        val customQualifiers = PerceptualCore.CustomQualifiers(
            intersectionMap = customIntersectionMap,
            uiModeMap = customUiModeMap,
            dpMap = customDpMap
        )
        
        return PerceptualCore.PerceptualConfig(
            baseValue = initialBaseDp,
            model = model,
            sensitivity = sensitivity,
            powerExponent = powerExponent,
            transitionPoint = transitionPoint,
            applyAspectRatio = applyAspectRatioAdjustment,
            arSensitivity = arSensitivity,
            screenType = screenType,
            baseOrientation = baseOrientation,
            customQualifiers = customQualifiers
        )
    }
    
    /**
     * [EN] Checks if screen configuration changed
     * [PT] Verifica se a configuração da tela mudou
     * 
     * Note: Cache invalidation now handled by AutoCacheFast automatically
     */
    private fun checkAndInvalidateCacheIfNeeded(configuration: Configuration) {
        val smallestWidthDp = configuration.smallestScreenWidthDp.toFloat()
        val currentScreenWidthDp = configuration.screenWidthDp.toFloat()
        val currentUiMode = configuration.uiMode
        
        // Just track config changes (cache invalidation is automatic in AutoCacheFast)
        lastScreenConfig = Triple(currentScreenWidthDp, smallestWidthDp, currentUiMode)
    }
    
    // ============================================
    // OUTPUT METHODS
    // ============================================
    
    /**
     * [EN] Calculates adjusted value in DP
     * 
     * @param resources Android Resources instance
     * @return Scaled value in dp
     * 
     * [PT] Calcula valor ajustado em DP
     */
    fun toDp(resources: Resources): Float = 
        calculateAdjustedValue(resources.configuration)
    
    /**
     * [EN] Calculates adjusted value in SP (for text)
     * 
     * @param resources Android Resources instance
     * @return Scaled value in sp
     * 
     * [PT] Calcula valor ajustado em SP (para texto)
     */
    fun toSp(resources: Resources): Float = 
        calculateAdjustedValue(resources.configuration)
    
    /**
     * [EN] Calculates adjusted value in Pixels
     * 
     * @param resources Android Resources instance
     * @return Scaled value in pixels
     * 
     * [PT] Calcula valor ajustado em Pixels
     */
    fun toPx(resources: Resources): Float = 
        calculateAdjustedValue(resources.configuration) * resources.displayMetrics.density
}

// ============================================
// EXTENSION FUNCTIONS
// ============================================

/**
 * [EN] Extension to create perceptual dimension from Float (dp)
 * 
 * @receiver Float value in dp
 * @return AppDimensPerceptual builder instance
 * 
 * [PT] Extensão para criar dimensão perceptual a partir de Float (dp)
 */
fun Float.perceptualDp(): AppDimensPerceptual = 
    AppDimensPerceptual.fromDp(this)

/**
 * [EN] Extension to create perceptual dimension from Int (dp)
 * 
 * @receiver Int value in dp
 * @return AppDimensPerceptual builder instance
 * 
 * [PT] Extensão para criar dimensão perceptual a partir de Int (dp)
 */
fun Int.perceptualDp(): AppDimensPerceptual = 
    AppDimensPerceptual.fromDp(this.toFloat())

/**
 * [EN] Extension to create perceptual dimension from Float (sp for text)
 * 
 * @receiver Float value in sp
 * @return AppDimensPerceptual builder instance
 * 
 * [PT] Extensão para criar dimensão perceptual a partir de Float (sp para texto)
 */
fun Float.perceptualSp(): AppDimensPerceptual = 
    AppDimensPerceptual.fromSp(this)

/**
 * [EN] Extension to create perceptual dimension from Int (sp for text)
 * 
 * @receiver Int value in sp
 * @return AppDimensPerceptual builder instance
 * 
 * [PT] Extensão para criar dimensão perceptual a partir de Int (sp para texto)
 */
fun Int.perceptualSp(): AppDimensPerceptual = 
    AppDimensPerceptual.fromSp(this.toFloat())
