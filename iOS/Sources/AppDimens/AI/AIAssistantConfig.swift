import Foundation

/**
 Configuração do Assistente de IA para AppDimens iOS
 
 Permite habilitar/desabilitar funcionalidades de assistência da IA e
 gerenciar configurações relacionadas ao aprendizado e sugestões.
 
 - Since: 1.0.7
 - Author: AppDimens Team
 */
@available(iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0, *)
public class AIAssistantConfig {
    
    // MARK: - Singleton
    
    /// Instância compartilhada do AIAssistantConfig
    public static let shared = AIAssistantConfig()
    
    // MARK: - Keys
    
    private struct Keys {
        static let aiEnabled = "appdimens_ai_enabled"
        static let suggestionsEnabled = "appdimens_suggestions_enabled"
        static let validationEnabled = "appdimens_validation_enabled"
        static let codeGenerationEnabled = "appdimens_code_generation_enabled"
        static let platformGuidanceEnabled = "appdimens_platform_guidance_enabled"
    }
    
    // MARK: - Properties
    
    private let userDefaults: UserDefaults
    private var configDict: [String: Any]?
    
    // MARK: - Initialization
    
    private init() {
        self.userDefaults = UserDefaults.standard
        loadConfigFromBundle()
    }
    
    // MARK: - Configuration Loading
    
    /// Carrega a configuração do arquivo AI_CONFIG.json
    private func loadConfigFromBundle() {
        guard let url = Bundle.main.url(forResource: "AI_CONFIG", withExtension: "json"),
              let data = try? Data(contentsOf: url),
              let json = try? JSONSerialization.jsonObject(with: data) as? [String: Any] else {
            return
        }
        configDict = json
    }
    
    // MARK: - AI Enabled
    
    /// Verifica se o assistente de IA está habilitado
    public var isAIEnabled: Bool {
        get {
            if userDefaults.object(forKey: Keys.aiEnabled) == nil {
                return defaultAIEnabled
            }
            return userDefaults.bool(forKey: Keys.aiEnabled)
        }
        set {
            userDefaults.set(newValue, forKey: Keys.aiEnabled)
        }
    }
    
    private var defaultAIEnabled: Bool {
        guard let aiAssistant = configDict?["ai_assistant"] as? [String: Any],
              let enabled = aiAssistant["enabled"] as? Bool else {
            return true
        }
        return enabled
    }
    
    // MARK: - Suggestions
    
    /// Verifica se sugestões de design responsivo estão habilitadas
    public var areSuggestionsEnabled: Bool {
        get {
            if userDefaults.object(forKey: Keys.suggestionsEnabled) == nil {
                return true
            }
            return userDefaults.bool(forKey: Keys.suggestionsEnabled)
        }
        set {
            userDefaults.set(newValue, forKey: Keys.suggestionsEnabled)
        }
    }
    
    // MARK: - Validation
    
    /// Verifica se validação de melhores práticas está habilitada
    public var isValidationEnabled: Bool {
        get {
            if userDefaults.object(forKey: Keys.validationEnabled) == nil {
                return true
            }
            return userDefaults.bool(forKey: Keys.validationEnabled)
        }
        set {
            userDefaults.set(newValue, forKey: Keys.validationEnabled)
        }
    }
    
    // MARK: - Code Generation
    
    /// Verifica se geração de código está habilitada
    public var isCodeGenerationEnabled: Bool {
        get {
            if userDefaults.object(forKey: Keys.codeGenerationEnabled) == nil {
                return true
            }
            return userDefaults.bool(forKey: Keys.codeGenerationEnabled)
        }
        set {
            userDefaults.set(newValue, forKey: Keys.codeGenerationEnabled)
        }
    }
    
    // MARK: - Platform Guidance
    
    /// Verifica se orientação específica de plataforma está habilitada
    public var isPlatformGuidanceEnabled: Bool {
        get {
            if userDefaults.object(forKey: Keys.platformGuidanceEnabled) == nil {
                return true
            }
            return userDefaults.bool(forKey: Keys.platformGuidanceEnabled)
        }
        set {
            userDefaults.set(newValue, forKey: Keys.platformGuidanceEnabled)
        }
    }
    
    // MARK: - Reset
    
    /// Reseta todas as configurações para os valores padrão
    public func resetToDefaults() {
        userDefaults.removeObject(forKey: Keys.aiEnabled)
        userDefaults.removeObject(forKey: Keys.suggestionsEnabled)
        userDefaults.removeObject(forKey: Keys.validationEnabled)
        userDefaults.removeObject(forKey: Keys.codeGenerationEnabled)
        userDefaults.removeObject(forKey: Keys.platformGuidanceEnabled)
    }
    
    // MARK: - Export/Import
    
    /// Obtém todas as configurações atuais como dicionário
    public var allSettings: [String: Bool] {
        return [
            "ai_enabled": isAIEnabled,
            "suggestions_enabled": areSuggestionsEnabled,
            "validation_enabled": isValidationEnabled,
            "code_generation_enabled": isCodeGenerationEnabled,
            "platform_guidance_enabled": isPlatformGuidanceEnabled
        ]
    }
    
    /// Exporta as configurações como JSON string
    public func exportSettings() -> String? {
        guard let data = try? JSONSerialization.data(withJSONObject: allSettings, options: .prettyPrinted),
              let jsonString = String(data: data, encoding: .utf8) else {
            return nil
        }
        return jsonString
    }
    
    /// Importa configurações de uma JSON string
    /// - Parameter jsonString: JSON contendo as configurações
    /// - Returns: true se importação bem-sucedida, false caso contrário
    public func importSettings(from jsonString: String) -> Bool {
        guard let data = jsonString.data(using: .utf8),
              let json = try? JSONSerialization.jsonObject(with: data) as? [String: Bool] else {
            return false
        }
        
        if let aiEnabled = json["ai_enabled"] {
            isAIEnabled = aiEnabled
        }
        if let suggestionsEnabled = json["suggestions_enabled"] {
            areSuggestionsEnabled = suggestionsEnabled
        }
        if let validationEnabled = json["validation_enabled"] {
            isValidationEnabled = validationEnabled
        }
        if let codeGenerationEnabled = json["code_generation_enabled"] {
            isCodeGenerationEnabled = codeGenerationEnabled
        }
        if let platformGuidanceEnabled = json["platform_guidance_enabled"] {
            isPlatformGuidanceEnabled = platformGuidanceEnabled
        }
        
        return true
    }
}

// MARK: - SwiftUI Integration

#if canImport(SwiftUI)
import SwiftUI

@available(iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0, *)
public extension View {
    /// Aplica configurações de IA ao ambiente do view
    func withAIAssistant() -> some View {
        self.environment(\.aiAssistantEnabled, AIAssistantConfig.shared.isAIEnabled)
    }
}

@available(iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0, *)
private struct AIAssistantEnabledKey: EnvironmentKey {
    static let defaultValue: Bool = true
}

@available(iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0, *)
public extension EnvironmentValues {
    var aiAssistantEnabled: Bool {
        get { self[AIAssistantEnabledKey.self] }
        set { self[AIAssistantEnabledKey.self] = newValue }
    }
}
#endif

