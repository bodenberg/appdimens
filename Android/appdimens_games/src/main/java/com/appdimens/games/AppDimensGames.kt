/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games
 *
 * Description:
 * Kotlin/Java interface for the AppDimens Games C++ native library.
 * Provides responsive dimensioning and game development utilities for Android.
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

package com.appdimens.games

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.Log
import androidx.annotation.Keep
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeType
import com.appdimens.dynamic.code.AppDimensDynamic
import com.appdimens.dynamic.code.AppDimensFixed
import com.appdimens.dynamic.code.AppDimensPhysicalUnits

/**
 * [EN] Game dimension types for different scaling strategies.
 * [PT] Tipos de dimensão de jogo para diferentes estratégias de escalonamento.
 */
enum class GameDimensionType {
    DYNAMIC,    // Proportional scaling (ideal for containers)
    FIXED,      // Logarithmic scaling (ideal for UI elements)
    GAME_WORLD, // Game world coordinates
    UI_OVERLAY  // UI overlay coordinates
}

/**
 * [EN] Screen orientation types for game development.
 * [PT] Tipos de orientação de tela para desenvolvimento de jogos.
 */
enum class GameScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
    AUTO
}

/**
 * [EN] Viewport scaling modes for different game scenarios.
 * [PT] Modos de escalonamento de viewport para diferentes cenários de jogo.
 */
enum class GameViewportMode {
    FIT_WIDTH,      // Fit to screen width
    FIT_HEIGHT,     // Fit to screen height
    FIT_ALL,        // Fit entire content
    STRETCH,        // Stretch to fill screen
    CROP            // Crop to maintain aspect ratio
}

/**
 * [EN] Game screen configuration data.
 * [PT] Dados de configuração da tela do jogo.
 */
data class GameScreenConfig(
    val width: Int,
    val height: Int,
    val density: Float,
    val scaledDensity: Float,
    val orientation: Int,
    val isTablet: Boolean,
    val isLandscape: Boolean
)

/**
 * [EN] 2D Vector for game coordinates.
 * [PT] Vetor 2D para coordenadas de jogo.
 */
data class GameVector2D(
    val x: Float,
    val y: Float
) {
    operator fun plus(other: GameVector2D): GameVector2D {
        return GameVector2D(x + other.x, y + other.y)
    }
    
    operator fun minus(other: GameVector2D): GameVector2D {
        return GameVector2D(x - other.x, y - other.y)
    }
    
    operator fun times(scalar: Float): GameVector2D {
        return GameVector2D(x * scalar, y * scalar)
    }
    
    fun length(): Float {
        return kotlin.math.sqrt(x * x + y * y)
    }
    
    fun normalized(): GameVector2D {
        val len = length()
        return if (len > 0.0f) {
            GameVector2D(x / len, y / len)
        } else {
            GameVector2D(0.0f, 0.0f)
        }
    }
}

/**
 * [EN] Rectangle for game bounds and viewports.
 * [PT] Retângulo para limites e viewports de jogo.
 */
data class GameRectangle(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float
) {
    fun center(): GameVector2D {
        return GameVector2D(x + width * 0.5f, y + height * 0.5f)
    }
    
    fun contains(point: GameVector2D): Boolean {
        return point.x >= x && point.x <= x + width &&
               point.y >= y && point.y <= y + height
    }
    
    fun intersection(other: GameRectangle): GameRectangle {
        val left = kotlin.math.max(x, other.x)
        val top = kotlin.math.max(y, other.y)
        val right = kotlin.math.min(x + width, other.x + other.width)
        val bottom = kotlin.math.min(y + height, other.y + other.height)
        
        return if (left < right && top < bottom) {
            GameRectangle(left, top, right - left, bottom - top)
        } else {
            GameRectangle(0.0f, 0.0f, 0.0f, 0.0f)
        }
    }
}

/**
 * [EN] Main AppDimens Games class for Android game development.
 * [PT] Classe principal AppDimens Games para desenvolvimento de jogos Android.
 */
class AppDimensGames private constructor() {
    
    companion object {
        private const val TAG = "AppDimensGames"
        
        @Volatile
        private var INSTANCE: AppDimensGames? = null
        
        /**
         * [EN] Gets the singleton instance of AppDimensGames.
         * [PT] Obtém a instância singleton do AppDimensGames.
         */
        fun getInstance(): AppDimensGames {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppDimensGames().also { INSTANCE = it }
            }
        }
        
        init {
            try {
                System.loadLibrary("appdimens_games")
                Log.i(TAG, "Native library loaded successfully")
            } catch (e: UnsatisfiedLinkError) {
                Log.e(TAG, "Failed to load native library", e)
            }
        }
    }
    
    private var isInitialized = false
    private var context: Context? = null
    private var screenConfig: GameScreenConfig? = null
    
    // Native method declarations
    @Keep
    private external fun nativeInitialize(context: Context): Boolean
    
    @Keep
    private external fun nativeShutdown()
    
    @Keep
    private external fun nativeUpdateScreenConfig(
        width: Int, height: Int, density: Float, 
        scaledDensity: Float, orientation: Int
    )
    
    @Keep
    private external fun nativeCalculateDimension(baseValue: Float, type: Int): Float
    
    @Keep
    private external fun nativeCalculateVector2D(x: Float, y: Float, type: Int): FloatArray
    
    @Keep
    private external fun nativeCalculateRectangle(
        x: Float, y: Float, width: Float, height: Float, type: Int
    ): FloatArray
    
    /**
     * [EN] Initializes the AppDimens Games library.
     * @param context The Android context.
     * @return True if initialization was successful.
     * 
     * [PT] Inicializa a biblioteca AppDimens Games.
     * @param context O contexto Android.
     * @return True se a inicialização foi bem-sucedida.
     */
    fun initialize(context: Context): Boolean {
        if (isInitialized) {
            Log.i(TAG, "AppDimensGames already initialized")
            return true
        }
        
        this.context = context.applicationContext
        
        try {
            val success = nativeInitialize(this.context!!)
            if (success) {
                isInitialized = true
                updateScreenConfiguration()
                Log.i(TAG, "AppDimensGames initialized successfully")
            } else {
                Log.e(TAG, "Failed to initialize AppDimensGames native library")
            }
            return success
        } catch (e: Exception) {
            Log.e(TAG, "Exception during initialization", e)
            return false
        }
    }
    
    /**
     * [EN] Shuts down the AppDimens Games library.
     * [PT] Desliga a biblioteca AppDimens Games.
     */
    fun shutdown() {
        if (!isInitialized) {
            return
        }
        
        try {
            nativeShutdown()
            isInitialized = false
            context = null
            screenConfig = null
            Log.i(TAG, "AppDimensGames shutdown complete")
        } catch (e: Exception) {
            Log.e(TAG, "Exception during shutdown", e)
        }
    }
    
    /**
     * [EN] Updates the screen configuration.
     * [PT] Atualiza a configuração da tela.
     */
    fun updateScreenConfiguration() {
        val context = this.context ?: return
        
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        val density = displayMetrics.density
        val scaledDensity = displayMetrics.scaledDensity
        val orientation = configuration.orientation
        val isTablet = resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        val isLandscape = width > height
        
        screenConfig = GameScreenConfig(width, height, density, scaledDensity, orientation, isTablet, isLandscape)
        
        if (isInitialized) {
            nativeUpdateScreenConfig(width, height, density, scaledDensity, orientation)
        }
        
        Log.i(TAG, "Screen config updated: ${width}x${height}, density: $density")
    }
    
    /**
     * [EN] Calculates a dimension value using the specified type.
     * @param baseValue The base dimension value.
     * @param type The dimension type.
     * @return The calculated dimension value.
     * 
     * [PT] Calcula um valor de dimensão usando o tipo especificado.
     * @param baseValue O valor de dimensão base.
     * @param type O tipo de dimensão.
     * @return O valor de dimensão calculado.
     */
    fun calculateDimension(baseValue: Float, type: GameDimensionType): Float {
        if (!isInitialized) {
            Log.w(TAG, "AppDimensGames not initialized")
            return baseValue
        }
        
        return try {
            nativeCalculateDimension(baseValue, type.ordinal)
        } catch (e: Exception) {
            Log.e(TAG, "Exception calculating dimension", e)
            baseValue
        }
    }
    
    /**
     * [EN] Calculates a 2D vector using the specified type.
     * @param vector The base vector.
     * @param type The dimension type.
     * @return The calculated vector.
     * 
     * [PT] Calcula um vetor 2D usando o tipo especificado.
     * @param vector O vetor base.
     * @param type O tipo de dimensão.
     * @return O vetor calculado.
     */
    fun calculateVector2D(vector: GameVector2D, type: GameDimensionType): GameVector2D {
        if (!isInitialized) {
            Log.w(TAG, "AppDimensGames not initialized")
            return vector
        }
        
        return try {
            val result = nativeCalculateVector2D(vector.x, vector.y, type.ordinal)
            GameVector2D(result[0], result[1])
        } catch (e: Exception) {
            Log.e(TAG, "Exception calculating vector2D", e)
            vector
        }
    }
    
    /**
     * [EN] Calculates a rectangle using the specified type.
     * @param rectangle The base rectangle.
     * @param type The dimension type.
     * @return The calculated rectangle.
     * 
     * [PT] Calcula um retângulo usando o tipo especificado.
     * @param rectangle O retângulo base.
     * @param type O tipo de dimensão.
     * @return O retângulo calculado.
     */
    fun calculateRectangle(rectangle: GameRectangle, type: GameDimensionType): GameRectangle {
        if (!isInitialized) {
            Log.w(TAG, "AppDimensGames not initialized")
            return rectangle
        }
        
        return try {
            val result = nativeCalculateRectangle(rectangle.x, rectangle.y, rectangle.width, rectangle.height, type.ordinal)
            GameRectangle(result[0], result[1], result[2], result[3])
        } catch (e: Exception) {
            Log.e(TAG, "Exception calculating rectangle", e)
            rectangle
        }
    }
    
    /**
     * [EN] Gets the current screen configuration.
     * @return The screen configuration.
     * 
     * [PT] Obtém a configuração atual da tela.
     * @return A configuração da tela.
     */
    fun getScreenConfig(): GameScreenConfig? {
        return screenConfig
    }
    
    /**
     * [EN] Checks if the library is initialized.
     * @return True if initialized.
     * 
     * [PT] Verifica se a biblioteca está inicializada.
     * @return True se inicializada.
     */
    fun isInitialized(): Boolean {
        return isInitialized
    }
    
    // Game-specific utility functions
    
    /**
     * [EN] Calculates button size for games.
     * @param baseSize The base button size.
     * @return The calculated button size.
     * 
     * [PT] Calcula o tamanho do botão para jogos.
     * @param baseSize O tamanho base do botão.
     * @return O tamanho do botão calculado.
     */
    fun calculateButtonSize(baseSize: Float = 48.0f): Float {
        return calculateDimension(baseSize, GameDimensionType.FIXED)
    }
    
    /**
     * [EN] Calculates text size for games.
     * @param baseSize The base text size.
     * @return The calculated text size.
     * 
     * [PT] Calcula o tamanho do texto para jogos.
     * @param baseSize O tamanho base do texto.
     * @return O tamanho do texto calculado.
     */
    fun calculateTextSize(baseSize: Float = 16.0f): Float {
        return calculateDimension(baseSize, GameDimensionType.FIXED)
    }
    
    /**
     * [EN] Calculates player size for games.
     * @param baseSize The base player size.
     * @return The calculated player size.
     * 
     * [PT] Calcula o tamanho do jogador para jogos.
     * @param baseSize O tamanho base do jogador.
     * @return O tamanho do jogador calculado.
     */
    fun calculatePlayerSize(baseSize: Float = 64.0f): Float {
        return calculateDimension(baseSize, GameDimensionType.GAME_WORLD)
    }
    
    /**
     * [EN] Calculates enemy size for games.
     * @param baseSize The base enemy size.
     * @return The calculated enemy size.
     * 
     * [PT] Calcula o tamanho do inimigo para jogos.
     * @param baseSize O tamanho base do inimigo.
     * @return O tamanho do inimigo calculado.
     */
    fun calculateEnemySize(baseSize: Float = 32.0f): Float {
        return calculateDimension(baseSize, GameDimensionType.GAME_WORLD)
    }
    
    /**
     * [EN] Calculates UI overlay size for games.
     * @param baseSize The base UI size.
     * @return The calculated UI size.
     * 
     * [PT] Calcula o tamanho da sobreposição de UI para jogos.
     * @param baseSize O tamanho base da UI.
     * @return O tamanho da UI calculado.
     */
    fun calculateUISize(baseSize: Float = 24.0f): Float {
        return calculateDimension(baseSize, GameDimensionType.UI_OVERLAY)
    }
}
