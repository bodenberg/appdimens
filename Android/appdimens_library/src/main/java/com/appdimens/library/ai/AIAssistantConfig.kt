package com.appdimens.library.ai

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Configuração do Assistente de IA para AppDimens Android
 * 
 * Permite habilitar/desabilitar funcionalidades de assistência da IA e
 * gerenciar configurações relacionadas ao aprendizado e sugestões.
 * 
 * @since 1.0.7
 * @author AppDimens Team
 */
object AIAssistantConfig {
    
    private const val PREFS_NAME = "appdimens_ai_config"
    private const val KEY_AI_ENABLED = "ai_enabled"
    private const val KEY_SUGGESTIONS_ENABLED = "suggestions_enabled"
    private const val KEY_VALIDATION_ENABLED = "validation_enabled"
    private const val KEY_CODE_GENERATION_ENABLED = "code_generation_enabled"
    private const val KEY_PLATFORM_GUIDANCE_ENABLED = "platform_guidance_enabled"
    
    private var preferences: SharedPreferences? = null
    private var configJson: JSONObject? = null
    
    /**
     * Inicializa a configuração do assistente de IA
     * 
     * @param context Contexto da aplicação
     */
    fun initialize(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadConfigFromAssets(context)
    }
    
    /**
     * Carrega a configuração do arquivo AI_CONFIG.json
     */
    private fun loadConfigFromAssets(context: Context) {
        try {
            val inputStream = context.assets.open("AI_CONFIG.json")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonString = reader.use { it.readText() }
            configJson = JSONObject(jsonString)
        } catch (e: Exception) {
            // Arquivo não encontrado ou erro ao ler, usar configurações padrão
            e.printStackTrace()
        }
    }
    
    /**
     * Verifica se o assistente de IA está habilitado
     * 
     * @return true se habilitado, false caso contrário
     */
    fun isAIEnabled(): Boolean {
        return preferences?.getBoolean(KEY_AI_ENABLED, getDefaultAIEnabled()) ?: true
    }
    
    /**
     * Habilita ou desabilita o assistente de IA
     * 
     * @param enabled true para habilitar, false para desabilitar
     */
    fun setAIEnabled(enabled: Boolean) {
        preferences?.edit()?.putBoolean(KEY_AI_ENABLED, enabled)?.apply()
    }
    
    /**
     * Verifica se sugestões de design responsivo estão habilitadas
     */
    fun areSuggestionsEnabled(): Boolean {
        return preferences?.getBoolean(KEY_SUGGESTIONS_ENABLED, true) ?: true
    }
    
    /**
     * Habilita ou desabilita sugestões de design responsivo
     */
    fun setSuggestionsEnabled(enabled: Boolean) {
        preferences?.edit()?.putBoolean(KEY_SUGGESTIONS_ENABLED, enabled)?.apply()
    }
    
    /**
     * Verifica se validação de melhores práticas está habilitada
     */
    fun isValidationEnabled(): Boolean {
        return preferences?.getBoolean(KEY_VALIDATION_ENABLED, true) ?: true
    }
    
    /**
     * Habilita ou desabilita validação de melhores práticas
     */
    fun setValidationEnabled(enabled: Boolean) {
        preferences?.edit()?.putBoolean(KEY_VALIDATION_ENABLED, enabled)?.apply()
    }
    
    /**
     * Verifica se geração de código está habilitada
     */
    fun isCodeGenerationEnabled(): Boolean {
        return preferences?.getBoolean(KEY_CODE_GENERATION_ENABLED, true) ?: true
    }
    
    /**
     * Habilita ou desabilita geração de código
     */
    fun setCodeGenerationEnabled(enabled: Boolean) {
        preferences?.edit()?.putBoolean(KEY_CODE_GENERATION_ENABLED, enabled)?.apply()
    }
    
    /**
     * Verifica se orientação específica de plataforma está habilitada
     */
    fun isPlatformGuidanceEnabled(): Boolean {
        return preferences?.getBoolean(KEY_PLATFORM_GUIDANCE_ENABLED, true) ?: true
    }
    
    /**
     * Habilita ou desabilita orientação específica de plataforma
     */
    fun setPlatformGuidanceEnabled(enabled: Boolean) {
        preferences?.edit()?.putBoolean(KEY_PLATFORM_GUIDANCE_ENABLED, enabled)?.apply()
    }
    
    /**
     * Obtém a configuração padrão de IA habilitada do JSON
     */
    private fun getDefaultAIEnabled(): Boolean {
        return try {
            configJson?.getJSONObject("ai_assistant")?.getBoolean("enabled") ?: true
        } catch (e: Exception) {
            true
        }
    }
    
    /**
     * Reseta todas as configurações para os valores padrão
     */
    fun resetToDefaults() {
        preferences?.edit()?.clear()?.apply()
    }
    
    /**
     * Obtém todas as configurações atuais como Map
     */
    fun getAllSettings(): Map<String, Boolean> {
        return mapOf(
            "ai_enabled" to isAIEnabled(),
            "suggestions_enabled" to areSuggestionsEnabled(),
            "validation_enabled" to isValidationEnabled(),
            "code_generation_enabled" to isCodeGenerationEnabled(),
            "platform_guidance_enabled" to isPlatformGuidanceEnabled()
        )
    }
    
    /**
     * Exporta as configurações como JSON string
     */
    fun exportSettings(): String {
        val settings = getAllSettings()
        return JSONObject(settings).toString(2)
    }
    
    /**
     * Importa configurações de uma JSON string
     * 
     * @param jsonString JSON contendo as configurações
     * @return true se importação bem-sucedida, false caso contrário
     */
    fun importSettings(jsonString: String): Boolean {
        return try {
            val json = JSONObject(jsonString)
            preferences?.edit()?.apply {
                if (json.has("ai_enabled")) {
                    putBoolean(KEY_AI_ENABLED, json.getBoolean("ai_enabled"))
                }
                if (json.has("suggestions_enabled")) {
                    putBoolean(KEY_SUGGESTIONS_ENABLED, json.getBoolean("suggestions_enabled"))
                }
                if (json.has("validation_enabled")) {
                    putBoolean(KEY_VALIDATION_ENABLED, json.getBoolean("validation_enabled"))
                }
                if (json.has("code_generation_enabled")) {
                    putBoolean(KEY_CODE_GENERATION_ENABLED, json.getBoolean("code_generation_enabled"))
                }
                if (json.has("platform_guidance_enabled")) {
                    putBoolean(KEY_PLATFORM_GUIDANCE_ENABLED, json.getBoolean("platform_guidance_enabled"))
                }
                apply()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}

