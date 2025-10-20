/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - Example Game Activity
 *
 * Description:
 * Example Android Activity demonstrating how to use AppDimens Games library
 * for responsive game development with C++ integration.
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

package com.example.app.games

import android.app.Activity
import android.content.res.Configuration
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.Keep
import com.appdimens.games.AppDimensGames
import com.appdimens.games.GameDimensionType
import com.appdimens.games.GameVector2D
import com.appdimens.games.GameRectangle

/**
 * [EN] Example game activity demonstrating AppDimens Games usage.
 * [PT] Atividade de jogo de exemplo demonstrando o uso do AppDimens Games.
 */
class GameActivity : Activity() {
    
    companion object {
        private const val TAG = "GameActivity"
    }
    
    private lateinit var appDimensGames: AppDimensGames
    private lateinit var glSurfaceView: GLSurfaceView
    private var isGameInitialized = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set fullscreen mode for games
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        
        // Initialize AppDimens Games
        initializeAppDimensGames()
        
        // Setup OpenGL surface view
        setupOpenGLSurfaceView()
        
        // Set content view
        setContentView(glSurfaceView)
        
        Log.i(TAG, "GameActivity created")
    }
    
    override fun onResume() {
        super.onResume()
        
        if (isGameInitialized) {
            // Resume game rendering
            glSurfaceView.onResume()
        }
    }
    
    override fun onPause() {
        super.onPause()
        
        if (isGameInitialized) {
            // Pause game rendering
            glSurfaceView.onPause()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        
        if (isGameInitialized) {
            // Shutdown AppDimens Games
            appDimensGames.shutdown()
            isGameInitialized = false
        }
        
        Log.i(TAG, "GameActivity destroyed")
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        
        if (isGameInitialized) {
            // Update screen configuration when orientation changes
            appDimensGames.updateScreenConfiguration()
            Log.i(TAG, "Screen configuration updated due to orientation change")
        }
    }
    
    /**
     * [EN] Initializes the AppDimens Games library.
     * [PT] Inicializa a biblioteca AppDimens Games.
     */
    private fun initializeAppDimensGames() {
        try {
            appDimensGames = AppDimensGames.getInstance()
            val success = appDimensGames.initialize(this)
            
            if (success) {
                isGameInitialized = true
                Log.i(TAG, "AppDimens Games initialized successfully")
                
                // Demonstrate dimension calculations
                demonstrateDimensionCalculations()
                
                Toast.makeText(this, "AppDimens Games initialized!", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "Failed to initialize AppDimens Games")
                Toast.makeText(this, "Failed to initialize AppDimens Games", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception initializing AppDimens Games", e)
            Toast.makeText(this, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    /**
     * [EN] Sets up the OpenGL surface view for game rendering.
     * [PT] Configura a view de superfície OpenGL para renderização do jogo.
     */
    private fun setupOpenGLSurfaceView() {
        glSurfaceView = GLSurfaceView(this)
        
        // Set OpenGL ES version
        glSurfaceView.setEGLContextClientVersion(2)
        
        // Set renderer
        glSurfaceView.setRenderer(GameRenderer(appDimensGames))
        
        // Set render mode to continuous
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
        
        Log.i(TAG, "OpenGL surface view setup complete")
    }
    
    /**
     * [EN] Demonstrates various dimension calculations.
     * [PT] Demonstra vários cálculos de dimensão.
     */
    private fun demonstrateDimensionCalculations() {
        if (!isGameInitialized) return
        
        Log.i(TAG, "=== AppDimens Games Dimension Calculations Demo ===")
        
        // Get screen configuration
        val screenConfig = appDimensGames.getScreenConfig()
        screenConfig?.let { config ->
            Log.i(TAG, "Screen: ${config.width}x${config.height}, Density: ${config.density}")
            Log.i(TAG, "Is Tablet: ${config.isTablet}, Is Landscape: ${config.isLandscape}")
        }
        
        // Demonstrate different dimension types
        val baseValue = 100.0f
        
        val dynamicDimension = appDimensGames.calculateDimension(baseValue, GameDimensionType.DYNAMIC)
        val fixedDimension = appDimensGames.calculateDimension(baseValue, GameDimensionType.FIXED)
        val gameWorldDimension = appDimensGames.calculateDimension(baseValue, GameDimensionType.GAME_WORLD)
        val uiOverlayDimension = appDimensGames.calculateDimension(baseValue, GameDimensionType.UI_OVERLAY)
        
        Log.i(TAG, "Base Value: $baseValue")
        Log.i(TAG, "Dynamic: $dynamicDimension")
        Log.i(TAG, "Fixed: $fixedDimension")
        Log.i(TAG, "Game World: $gameWorldDimension")
        Log.i(TAG, "UI Overlay: $uiOverlayDimension")
        
        // Demonstrate vector calculations
        val baseVector = GameVector2D(100.0f, 50.0f)
        val dynamicVector = appDimensGames.calculateVector2D(baseVector, GameDimensionType.DYNAMIC)
        val fixedVector = appDimensGames.calculateVector2D(baseVector, GameDimensionType.FIXED)
        
        Log.i(TAG, "Base Vector: (${baseVector.x}, ${baseVector.y})")
        Log.i(TAG, "Dynamic Vector: (${dynamicVector.x}, ${dynamicVector.y})")
        Log.i(TAG, "Fixed Vector: (${fixedVector.x}, ${fixedVector.y})")
        
        // Demonstrate rectangle calculations
        val baseRectangle = GameRectangle(0.0f, 0.0f, 200.0f, 100.0f)
        val dynamicRectangle = appDimensGames.calculateRectangle(baseRectangle, GameDimensionType.DYNAMIC)
        val fixedRectangle = appDimensGames.calculateRectangle(baseRectangle, GameDimensionType.FIXED)
        
        Log.i(TAG, "Base Rectangle: (${baseRectangle.x}, ${baseRectangle.y}, ${baseRectangle.width}, ${baseRectangle.height})")
        Log.i(TAG, "Dynamic Rectangle: (${dynamicRectangle.x}, ${dynamicRectangle.y}, ${dynamicRectangle.width}, ${dynamicRectangle.height})")
        Log.i(TAG, "Fixed Rectangle: (${fixedRectangle.x}, ${fixedRectangle.y}, ${fixedRectangle.width}, ${fixedRectangle.height})")
        
        // Demonstrate game-specific calculations
        val buttonSize = appDimensGames.calculateButtonSize(48.0f)
        val textSize = appDimensGames.calculateTextSize(16.0f)
        val playerSize = appDimensGames.calculatePlayerSize(64.0f)
        val enemySize = appDimensGames.calculateEnemySize(32.0f)
        val uiSize = appDimensGames.calculateUISize(24.0f)
        
        Log.i(TAG, "Game Elements:")
        Log.i(TAG, "Button Size: $buttonSize")
        Log.i(TAG, "Text Size: $textSize")
        Log.i(TAG, "Player Size: $playerSize")
        Log.i(TAG, "Enemy Size: $enemySize")
        Log.i(TAG, "UI Size: $uiSize")
        
        Log.i(TAG, "=== Demo Complete ===")
    }
}

/**
 * [EN] Simple OpenGL renderer for demonstration purposes.
 * [PT] Renderizador OpenGL simples para fins de demonstração.
 */
@Keep
class GameRenderer(private val appDimensGames: AppDimensGames) : GLSurfaceView.Renderer {
    
    companion object {
        private const val TAG = "GameRenderer"
    }
    
    override fun onSurfaceCreated(gl: javax.microedition.khronos.opengles.GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
        Log.i(TAG, "OpenGL surface created")
        
        // Set clear color to dark blue
        android.opengl.GLES20.glClearColor(0.0f, 0.0f, 0.2f, 1.0f)
    }
    
    override fun onSurfaceChanged(gl: javax.microedition.khronos.opengles.GL10?, width: Int, height: Int) {
        Log.i(TAG, "OpenGL surface changed: ${width}x${height}")
        
        // Set viewport
        android.opengl.GLES20.glViewport(0, 0, width, height)
        
        // Update screen configuration in AppDimens Games
        appDimensGames.updateScreenConfiguration()
    }
    
    override fun onDrawFrame(gl: javax.microedition.khronos.opengles.GL10?) {
        // Clear the screen
        android.opengl.GLES20.glClear(android.opengl.GLES20.GL_COLOR_BUFFER_BIT)
        
        // Here you would implement your game rendering logic
        // using the AppDimens Games library for responsive dimensions
        
        // Example: Draw a simple colored rectangle using calculated dimensions
        drawExampleGameElement()
    }
    
    /**
     * [EN] Draws an example game element using AppDimens Games calculations.
     * [PT] Desenha um elemento de jogo de exemplo usando cálculos do AppDimens Games.
     */
    private fun drawExampleGameElement() {
        // This is a simplified example - in a real game you would use proper OpenGL rendering
        
        // Calculate responsive dimensions for a game element
        val elementSize = appDimensGames.calculateDimension(100.0f, GameDimensionType.GAME_WORLD)
        val elementPosition = appDimensGames.calculateVector2D(
            GameVector2D(50.0f, 50.0f), 
            GameDimensionType.DYNAMIC
        )
        
        // In a real implementation, you would use these calculated values
        // to position and scale your game elements
        // For now, we just log the values
        Log.d(TAG, "Element size: $elementSize, position: (${elementPosition.x}, ${elementPosition.y})")
    }
}
