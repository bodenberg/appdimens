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
        let instance = AppDimensDynamic(initialValue, ignoreMultiWindowAdjustment: ignore)
        AppDimensGlobal.registerDynamicInstance(instance)
        return instance
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
    
    // MARK: - Concise Aliases (matching Web and Flutter API)
    
    /**
     * [EN] Alias for fixed(). Concise syntax matching Web and Flutter API.
     * @param initialValue The initial base value.
     * @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
     * @return An AppDimensFixed instance for chaining.
     * [PT] Alias para fixed(). Sintaxe concisa compatível com API Web e Flutter.
     * @param initialValue O valor base inicial.
     * @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
     * @return Uma instância AppDimensFixed para encadeamento.
     */
    public func fx(_ initialValue: CGFloat, ignoreMultiWindowAdjustment: Bool? = nil) -> AppDimensFixed {
        return fixed(initialValue, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Alias for fixed(). Concise syntax matching Web and Flutter API.
     * @param initialValue The initial base value.
     * @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
     * @return An AppDimensFixed instance for chaining.
     * [PT] Alias para fixed(). Sintaxe concisa compatível com API Web e Flutter.
     * @param initialValue O valor base inicial.
     * @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
     * @return Uma instância AppDimensFixed para encadeamento.
     */
    public func fx(_ initialValue: Int, ignoreMultiWindowAdjustment: Bool? = nil) -> AppDimensFixed {
        return fixed(initialValue, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Alias for dynamic(). Concise syntax matching Web and Flutter API.
     * @param initialValue The initial base value.
     * @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
     * @return An AppDimensDynamic instance for chaining.
     * [PT] Alias para dynamic(). Sintaxe concisa compatível com API Web e Flutter.
     * @param initialValue O valor base inicial.
     * @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
     * @return Uma instância AppDimensDynamic para encadeamento.
     */
    public func dy(_ initialValue: CGFloat, ignoreMultiWindowAdjustment: Bool? = nil) -> AppDimensDynamic {
        return dynamic(initialValue, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Alias for dynamic(). Concise syntax matching Web and Flutter API.
     * @param initialValue The initial base value.
     * @param ignoreMultiWindowAdjustment Whether to ignore multi-window adjustments.
     * @return An AppDimensDynamic instance for chaining.
     * [PT] Alias para dynamic(). Sintaxe concisa compatível com API Web e Flutter.
     * @param initialValue O valor base inicial.
     * @param ignoreMultiWindowAdjustment Se deve ignorar ajustes multi-window.
     * @return Uma instância AppDimensDynamic para encadeamento.
     */
    public func dy(_ initialValue: Int, ignoreMultiWindowAdjustment: Bool? = nil) -> AppDimensDynamic {
        return dynamic(initialValue, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    // MARK: - Percentage-Based Dimensions
    
    /**
     * [EN] Calculates a dynamic dimension value based on a percentage in points.
     * Compatible with Android API.
     * @param percentage The percentage (0.0 to 1.0).
     * @param type The screen dimension to use (lowest/highest).
     * @return The adjusted value in points.
     * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem em pontos.
     * Compatível com API Android.
     * @param percentage A porcentagem (0.0 a 1.0).
     * @param type A dimensão da tela a ser usada (lowest/highest).
     * @return O valor ajustado em pontos.
     */
    public func dynamicPercentageDp(_ percentage: CGFloat, type: ScreenType = .lowest) -> CGFloat {
        assert(percentage >= 0.0 && percentage <= 1.0, "Percentage must be between 0.0 and 1.0")
        
        let (screenWidth, screenHeight) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
        
        let dimensionToUse = type == .highest
            ? max(screenWidth, screenHeight)
            : min(screenWidth, screenHeight)
        
        return dimensionToUse * percentage
    }
    
    /**
     * [EN] Calculates a dynamic dimension value based on a percentage in physical pixels.
     * Compatible with Android API.
     * @param percentage The percentage (0.0 to 1.0).
     * @param type The screen dimension to use (lowest/highest).
     * @return The adjusted value in physical pixels.
     * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem em pixels físicos.
     * Compatível com API Android.
     * @param percentage A porcentagem (0.0 a 1.0).
     * @param type A dimensão da tela a ser usada (lowest/highest).
     * @return O valor ajustado em pixels físicos.
     */
    public func dynamicPercentagePx(_ percentage: CGFloat, type: ScreenType = .lowest) -> CGFloat {
        let dpValue = dynamicPercentageDp(percentage, type: type)
        return AppDimensAdjustmentFactors.pointsToPixels(dpValue)
    }
    
    /**
     * [EN] Calculates a dynamic dimension value based on a percentage in scalable pixels.
     * Compatible with Android API.
     * @param percentage The percentage (0.0 to 1.0).
     * @param type The screen dimension to use (lowest/highest).
     * @return The adjusted value in scalable pixels.
     * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem em pixels escaláveis.
     * Compatível com API Android.
     * @param percentage A porcentagem (0.0 a 1.0).
     * @param type A dimensão da tela a ser usada (lowest/highest).
     * @return O valor ajustado em pixels escaláveis.
     */
    public func dynamicPercentageSp(_ percentage: CGFloat, type: ScreenType = .lowest) -> CGFloat {
        let dpValue = dynamicPercentageDp(percentage, type: type)
        let fontScale = AppDimensAdjustmentFactors.getFontScale()
        return dpValue * fontScale
    }
    
    // MARK: - Layout Utilities
    
    /**
     * [EN] Calculates the maximum number of items that can fit in a container.
     * Compatible with Android API.
     * @param containerSizePx The size (width or height) of the container in pixels.
     * @param itemSizeDp The size of each item in points.
     * @param itemMarginDp The margin of each item in points.
     * @return The number of items that can fit.
     * [PT] Calcula o número máximo de itens que cabem em um container.
     * Compatível com API Android.
     * @param containerSizePx O tamanho (largura ou altura) do container em pixels.
     * @param itemSizeDp O tamanho de cada item em pontos.
     * @param itemMarginDp A margem de cada item em pontos.
     * @return O número de itens que cabem.
     */
    public func calculateAvailableItemCount(
        containerSizePx: CGFloat,
        itemSizeDp: CGFloat,
        itemMarginDp: CGFloat
    ) -> Int {
        let itemSizePx = AppDimensAdjustmentFactors.pointsToPixels(itemSizeDp)
        let itemMarginPx = AppDimensAdjustmentFactors.pointsToPixels(itemMarginDp)
        
        let totalItemSizePx = itemSizePx + (itemMarginPx * 2)
        
        return totalItemSizePx > 0 ? Int(floor(containerSizePx / totalItemSizePx)) : 0
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
    
    /// [EN] Registry of all AppDimensDynamic instances for global cache management.
    /// [PT] Registro de todas as instâncias AppDimensDynamic para gerenciamento global de cache.
    private static var dynamicInstances = NSHashTable<AppDimensDynamic>.weakObjects()
    private static let registryQueue = DispatchQueue(label: "com.appdimens.registry", attributes: .concurrent)
    
    /// [EN] Registers an AppDimensDynamic instance for global cache management.
    /// [PT] Registra uma instância AppDimensDynamic para gerenciamento global de cache.
    public static func registerDynamicInstance(_ instance: AppDimensDynamic) {
        registryQueue.async(flags: .barrier) {
            dynamicInstances.add(instance)
        }
    }
    
    /// [EN] Unregisters an AppDimensDynamic instance from global cache management.
    /// [PT] Remove o registro de uma instância AppDimensDynamic do gerenciamento global de cache.
    public static func unregisterDynamicInstance(_ instance: AppDimensDynamic) {
        registryQueue.async(flags: .barrier) {
            dynamicInstances.remove(instance)
        }
    }
    
    /**
     * [EN] Clears all caches from all instances.
     * [PT] Limpa todos os caches de todas as instâncias.
     */
    public static func clearAllCaches() {
        // Clear the global auto cache
        AppDimensAutoCache.shared.clearAll()
        // Clear individual instance caches
        registryQueue.sync {
            dynamicInstances.allObjects.forEach { instance in
                instance.clearInstanceCache()
            }
        }
    }
}

// MARK: - Library Information

/**
 * [EN] Information about the AppDimens library.
 * [PT] Informações sobre a biblioteca AppDimens.
 */
public struct AppDimensInfo {
    public static let version = "1.1.0"
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