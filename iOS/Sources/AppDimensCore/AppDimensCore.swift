/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Core Module
 *
 * Description:
 * Main export file for the AppDimens Core module, providing access to all
 * core dimension management functionality.
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

// MARK: - Core Module Exports

// Re-export all core functionality
// Note: These are defined in the same module, so no import needed

// MARK: - Convenience Extensions

public extension AppDimens {
    
    /**
     * [EN] Creates a fixed dimension with the specified value.
     * @param value The base value for the dimension.
     * @return A fixed dimension instance.
     * [PT] Cria uma dimensão fixa com o valor especificado.
     * @param value O valor base para a dimensão.
     * @return Uma instância de dimensão fixa.
     */
    static func fx(_ value: CGFloat) -> AppDimensFixed {
        return AppDimens.fixed(value)
    }
    
    /**
     * [EN] Creates a fixed dimension with the specified integer value.
     * @param value The base value for the dimension.
     * @return A fixed dimension instance.
     * [PT] Cria uma dimensão fixa com o valor inteiro especificado.
     * @param value O valor base para a dimensão.
     * @return Uma instância de dimensão fixa.
     */
    static func fx(_ value: Int) -> AppDimensFixed {
        return AppDimens.fixed(value)
    }
    
    /**
     * [EN] Creates a dynamic dimension with the specified value.
     * @param value The base value for the dimension.
     * @return A dynamic dimension instance.
     * [PT] Cria uma dimensão dinâmica com o valor especificado.
     * @param value O valor base para a dimensão.
     * @return Uma instância de dimensão dinâmica.
     */
    static func dy(_ value: CGFloat) -> AppDimensDynamic {
        return AppDimens.dynamic(value)
    }
    
    /**
     * [EN] Creates a dynamic dimension with the specified integer value.
     * @param value The base value for the dimension.
     * @return A dynamic dimension instance.
     * [PT] Cria uma dimensão dinâmica com o valor inteiro especificado.
     * @param value O valor base para a dimensão.
     * @return Uma instância de dimensão dinâmica.
     */
    static func dy(_ value: Int) -> AppDimensDynamic {
        return AppDimens.dynamic(value)
    }
}

// MARK: - Global Convenience Functions

/**
 * [EN] Creates a fixed dimension with the specified value.
 * @param value The base value for the dimension.
 * @return A fixed dimension instance.
 * [PT] Cria uma dimensão fixa com o valor especificado.
 * @param value O valor base para a dimensão.
 * @return Uma instância de dimensão fixa.
 */
public func fx(_ value: CGFloat) -> AppDimensFixed {
    return AppDimens.fixed(value)
}

/**
 * [EN] Creates a fixed dimension with the specified integer value.
 * @param value The base value for the dimension.
 * @return A fixed dimension instance.
 * [PT] Cria uma dimensão fixa com o valor inteiro especificado.
 * @param value O valor base para a dimensão.
 * @return Uma instância de dimensão fixa.
 */
public func fx(_ value: Int) -> AppDimensFixed {
    return AppDimens.fixed(value)
}

/**
 * [EN] Creates a dynamic dimension with the specified value.
 * @param value The base value for the dimension.
 * @return A dynamic dimension instance.
 * [PT] Cria uma dimensão dinâmica com o valor especificado.
 * @param value O valor base para a dimensão.
 * @return Uma instância de dimensão dinâmica.
 */
public func dy(_ value: CGFloat) -> AppDimensDynamic {
    return AppDimens.dynamic(value)
}

/**
 * [EN] Creates a dynamic dimension with the specified integer value.
 * @param value The base value for the dimension.
 * @return A dynamic dimension instance.
 * [PT] Cria uma dimensão dinâmica com o valor inteiro especificado.
 * @param value O valor base para a dimensão.
 * @return Uma instância de dimensão dinâmica.
 */
public func dy(_ value: Int) -> AppDimensDynamic {
    return AppDimens.dynamic(value)
}

// MARK: - Module Information

/**
 * [EN] Information about the AppDimens Core module.
 * [PT] Informações sobre o módulo AppDimens Core.
 */
public struct AppDimensCoreInfo {
    public static let version = "1.0.5"
    public static let moduleName = "AppDimensCore"
    public static let description = "Core dimension management functionality for AppDimens"
    
    /**
     * [EN] Gets the module information as a dictionary.
     * @return A dictionary containing module information.
     * [PT] Obtém as informações do módulo como um dicionário.
     * @return Um dicionário contendo informações do módulo.
     */
    public static func info() -> [String: String] {
        return [
            "version": version,
            "moduleName": moduleName,
            "description": description
        ]
    }
}
