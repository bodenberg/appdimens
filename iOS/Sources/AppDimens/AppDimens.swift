/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Main Export
 *
 * Description:
 * Main export file for the AppDimens library, providing access to all
 * functionality across all modules (Core, UI, and Games).
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

import Foundation
import UIKit

// MARK: - UI Module (if available)
#if canImport(SwiftUI)
@_exported import AppDimensUI
#endif

// MARK: - Games Module (if Metal is available)
#if canImport(Metal)
@_exported import AppDimensGames
#endif

// MARK: - Main AppDimens Class

/**
 * [EN] Main AppDimens class that provides access to fixed and dynamic dimension builders.
 * [PT] Classe principal AppDimens que fornece acesso aos construtores de dimensões fixas e dinâmicas.
 */
public class AppDimens {
    
    // MARK: - Singleton
    
    public static let shared = AppDimens()
    
    private init() {}
    
    // MARK: - Builder Methods
    
    /**
     * [EN] Creates a fixed dimension builder from a CGFloat value.
     * @param initialValue The initial base value.
     * @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
     * @return An AppDimensFixed instance for chaining.
     * [PT] Cria um construtor de dimensão fixa a partir de um valor CGFloat.
     * @param initialValue O valor base inicial.
     * @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
     * @return Uma instância AppDimensFixed para encadeamento.
     */
    public func fixed(_ initialValue: CGFloat, ignoreMultiWindowAdjustment: Bool? = nil) -> AppDimensFixed {
        let ignore = ignoreMultiWindowAdjustment ?? false
        return AppDimensFixed(initialValue, ignoreMultiWindowAdjustment: ignore)
    }
    
    /**
     * [EN] Creates a fixed dimension builder from an Int value.
     * @param initialValue The initial base value.
     * @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
     * @return An AppDimensFixed instance for chaining.
     * [PT] Cria um construtor de dimensão fixa a partir de um valor Int.
     * @param initialValue O valor base inicial.
     * @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
     * @return Uma instância AppDimensFixed para encadeamento.
     */
    public func fixed(_ initialValue: Int, ignoreMultiWindowAdjustment: Bool? = nil) -> AppDimensFixed {
        return fixed(CGFloat(initialValue), ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Creates a dynamic dimension builder from a CGFloat value.
     * @param initialValue The initial base value.
     * @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
     * @return An AppDimensDynamic instance for chaining.
     * [PT] Cria um construtor de dimensão dinâmica a partir de um valor CGFloat.
     * @param initialValue O valor base inicial.
     * @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
     * @return Uma instância AppDimensDynamic para encadeamento.
     */
    public func dynamic(_ initialValue: CGFloat, ignoreMultiWindowAdjustment: Bool? = nil) -> AppDimensDynamic {
        let ignore = ignoreMultiWindowAdjustment ?? false
        return AppDimensDynamic(initialValue, ignoreMultiWindowAdjustment: ignore)
    }
    
    /**
     * [EN] Creates a dynamic dimension builder from an Int value.
     * @param initialValue The initial base value.
     * @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
     * @return An AppDimensDynamic instance for chaining.
     * [PT] Cria um construtor de dimensão dinâmica a partir de um valor Int.
     * @param initialValue O valor base inicial.
     * @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
     * @return Uma instância AppDimensDynamic para encadeamento.
     */
    public func dynamic(_ initialValue: Int, ignoreMultiWindowAdjustment: Bool? = nil) -> AppDimensDynamic {
        return dynamic(CGFloat(initialValue), ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
}

// MARK: - Global Cache Control

/**
 * [EN] Global cache control for all AppDimens instances.
 * [PT] Controle global de cache para todas as instâncias AppDimens.
 */
public class AppDimensGlobal {
    
    /**
     * [EN] Global cache control for all AppDimensDynamic instances.
     * [PT] Controle global de cache para todas as instâncias AppDimensDynamic.
     */
    public static var globalCacheEnabled: Bool = true {
        didSet {
            if !globalCacheEnabled {
                // Clear all caches when globally disabled
                clearAllCaches()
            }
        }
    }
    
    /**
     * [EN] Clears all caches from all instances.
     * [PT] Limpa todos os caches de todas as instâncias.
     */
    public static func clearAllCaches() {
        // This would need to be implemented with a registry of instances
        // For now, individual instances will clear their own caches
    }
}

// MARK: - Library Information

/**
 * [EN] Information about the AppDimens library.
 * [PT] Informações sobre a biblioteca AppDimens.
 */
public struct AppDimensInfo {
    public static let version = "1.0.5"
    public static let libraryName = "AppDimens"
    public static let description = "A responsive dimension management system for iOS"
    
    /**
     * [EN] Gets the library information as a dictionary.
     * @return A dictionary containing library information.
     * [PT] Obtém as informações da biblioteca como um dicionário.
     * @return Um dicionário contendo informações da biblioteca.
     */
    public static func info() -> [String: String] {
        var info: [String: String] = [
            "version": version,
            "libraryName": libraryName,
            "description": description
        ]
        
        // Add module information
        info["coreModule"] = AppDimensCoreInfo.moduleName
        info["coreVersion"] = AppDimensCoreInfo.version
        
        #if canImport(SwiftUI)
        info["uiModule"] = AppDimensUIInfo.moduleName
        info["uiVersion"] = AppDimensUIInfo.version
        #endif
        
        #if canImport(Metal)
        info["gamesModule"] = AppDimensGamesInfo.moduleName
        info["gamesVersion"] = AppDimensGamesInfo.version
        #endif
        
        return info
    }
    
    /**
     * [EN] Gets the available modules.
     * @return An array of available module names.
     * [PT] Obtém os módulos disponíveis.
     * @return Um array com os nomes dos módulos disponíveis.
     */
    public static func availableModules() -> [String] {
        var modules = ["Core"]
        
        #if canImport(SwiftUI)
        modules.append("UI")
        #endif
        
        #if canImport(Metal)
        modules.append("Games")
        #endif
        
        return modules
    }
}