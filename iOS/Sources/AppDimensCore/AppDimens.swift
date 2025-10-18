/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
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
import SwiftUI

/**
 * [EN] A singleton object that provides functions for responsive dimension management,
 * acting as a gateway to the AppDimensFixed and AppDimensDynamic constructors.
 * [PT] Objeto singleton que fornece funções para gerenciamento de dimensões responsivas,
 * agindo como um gateway para os construtores AppDimensFixed e AppDimensDynamic.
 */
public class AppDimens {
    
    // MARK: - Singleton
    
    public static let shared = AppDimens()
    
    private init() {}
    
    // MARK: - Gateway for AppDimensFixed (Legacy API)
    
    /**
     * [EN] Initializes the AppDimensFixed constructor from a CGFloat value.
     * @param initialValue The initial value in points.
     * @param ignoreMultiWindowAdjustment If true, ignores multi-window adjustments.
     * @return The AppDimensFixed instance.
     * [PT] Inicia o construtor AppDimensFixed a partir de um valor CGFloat.
     * @param initialValue O valor inicial em pontos.
     * @param ignoreMultiWindowAdjustment Se verdadeiro, ignora os ajustes de multi-janela.
     * @return A instância AppDimensFixed.
     */
    public func fixed(_ initialValue: CGFloat, ignoreMultiWindowAdjustment: Bool = false) -> AppDimensFixed {
        return AppDimensFixed(initialValue, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Initializes the AppDimensFixed constructor from an Int value.
     * @param initialValue The initial value in points.
     * @param ignoreMultiWindowAdjustment If true, ignores multi-window adjustments.
     * @return The AppDimensFixed instance.
     * [PT] Inicia o construtor AppDimensFixed a partir de um valor Int.
     * @param initialValue O valor inicial em pontos.
     * @param ignoreMultiWindowAdjustment Se verdadeiro, ignora os ajustes de multi-janela.
     * @return A instância AppDimensFixed.
     */
    public func fixed(_ initialValue: Int, ignoreMultiWindowAdjustment: Bool = false) -> AppDimensFixed {
        return AppDimensFixed(CGFloat(initialValue), ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    // MARK: - Gateway for AppDimensDynamic (Legacy API)
    
    /**
     * [EN] Initializes the AppDimensDynamic constructor from a CGFloat value.
     * @param initialValue The initial value in points.
     * @param ignoreMultiWindowAdjustment If true, ignores multi-window adjustments.
     * @return The AppDimensDynamic instance.
     * [PT] Inicia o construtor AppDimensDynamic a partir de um valor CGFloat.
     * @param initialValue O valor inicial em pontos.
     * @param ignoreMultiWindowAdjustment Se verdadeiro, ignora os ajustes de multi-janela.
     * @return A instância AppDimensDynamic.
     */
    public func dynamic(_ initialValue: CGFloat, ignoreMultiWindowAdjustment: Bool = false) -> AppDimensDynamic {
        return AppDimensDynamic(initialValue, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Initializes the AppDimensDynamic constructor from an Int value.
     * @param initialValue The initial value in points.
     * @param ignoreMultiWindowAdjustment If true, ignores multi-window adjustments.
     * @return The AppDimensDynamic instance.
     * [PT] Inicia o construtor AppDimensDynamic a partir de um valor Int.
     * @param initialValue O valor inicial em pontos.
     * @param ignoreMultiWindowAdjustment Se verdadeiro, ignora os ajustes de multi-janela.
     * @return A instância AppDimensDynamic.
     */
    public func dynamic(_ initialValue: Int, ignoreMultiWindowAdjustment: Bool = false) -> AppDimensDynamic {
        return AppDimensDynamic(CGFloat(initialValue), ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    // MARK: - Dynamic Dimension Functions (Percentage-Based)
    
    /**
     * [EN] Calculates a dynamic dimension value based on a percentage (0.0 to 1.0) of the screen dimension.
     * Returns the value in points.
     * @param percentage The percentage (0.0 to 1.0).
     * @param type The screen dimension to use (LOWEST/HIGHEST).
     * @return The adjusted value in points.
     * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem (0.0 a 1.0) da dimensão da tela.
     * Retorna o valor em pontos.
     * @param percentage A porcentagem (0.0 a 1.0).
     * @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
     * @return O valor ajustado em pontos.
     */
    public func percentage(_ percentage: CGFloat, type: ScreenType = .lowest) -> CGFloat {
        guard percentage >= 0.0 && percentage <= 1.0 else {
            fatalError("Percentage must be between 0.0 and 1.0")
        }
        
        let (screenWidth, screenHeight) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
        
        let dimensionToUse = type == .highest ? 
            max(screenWidth, screenHeight) : 
            min(screenWidth, screenHeight)
        
        return dimensionToUse * percentage
    }
    
    /**
     * [EN] Calculates a dynamic dimension value based on a percentage and converts it to pixels.
     * @param percentage The percentage (0.0 to 1.0).
     * @param type The screen dimension to use (LOWEST/HIGHEST).
     * @return The adjusted value in pixels.
     * [PT] Calcula um valor de dimensão dinâmico com base em uma porcentagem e o converte para pixels.
     * @param percentage A porcentagem (0.0 a 1.0).
     * @param type A dimensão da tela a ser usada (LOWEST/HIGHEST).
     * @return O valor ajustado em pixels.
     */
    public func percentagePixels(_ percentage: CGFloat, type: ScreenType = .lowest) -> CGFloat {
        let pointsValue = percentage(percentage, type: type)
        return AppDimensAdjustmentFactors.pointsToPixels(pointsValue)
    }
    
    // MARK: - Layout Utilities
    
    /**
     * [EN] Calculates the maximum number of items that can fit in a container.
     * @param containerSize The size (width or height) of the container in points.
     * @param itemSize The size (width or height) of an item in points.
     * @param itemMargin The total margin (in points) around each item.
     * @return The calculated item count.
     * [PT] Calcula o número máximo de itens que cabem em um contêiner.
     * @param containerSize O tamanho (largura ou altura) do contêiner em pontos.
     * @param itemSize O tamanho (largura ou altura) de um item em pontos.
     * @param itemMargin O margin total (em pontos) em torno de cada item.
     * @return A contagem de itens calculada.
     */
    public func calculateAvailableItemCount(
        containerSize: CGFloat,
        itemSize: CGFloat,
        itemMargin: CGFloat
    ) -> Int {
        return AppDimensAdjustmentFactors.calculateAvailableItemCount(
            containerSize: containerSize,
            itemSize: itemSize,
            itemMargin: itemMargin
        )
    }
    
    // MARK: - Physical Units
    
    /**
     * [EN] Converts millimeters to points.
     * @param mm The value in millimeters.
     * @return The value in points.
     * [PT] Converte milímetros para pontos.
     * @param mm O valor em milímetros.
     * @return O valor em pontos.
     */
    public func toPoints(fromMm mm: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPoints(mm: mm)
    }
    
    /**
     * [EN] Converts centimeters to points.
     * @param cm The value in centimeters.
     * @return The value in points.
     * [PT] Converte centímetros para pontos.
     * @param cm O valor em centímetros.
     * @return O valor em pontos.
     */
    public func toPoints(fromCm cm: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPoints(cm: cm)
    }
    
    /**
     * [EN] Converts inches to points.
     * @param inches The value in inches.
     * @return The value in points.
     * [PT] Converte polegadas para pontos.
     * @param inches O valor em polegadas.
     * @return O valor em pontos.
     */
    public func toPoints(fromInches inches: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPoints(inches: inches)
    }
}

// MARK: - Convenience Static Methods

public extension AppDimens {
    
    /**
     * [EN] Static convenience method for creating fixed dimensions.
     * [PT] Método estático de conveniência para criar dimensões fixas.
     */
    static func fixed(_ value: CGFloat, ignoreMultiWindowAdjustment: Bool = false) -> AppDimensFixed {
        return AppDimens.shared.fixed(value, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Static convenience method for creating fixed dimensions from Int.
     * [PT] Método estático de conveniência para criar dimensões fixas a partir de Int.
     */
    static func fixed(_ value: Int, ignoreMultiWindowAdjustment: Bool = false) -> AppDimensFixed {
        return AppDimens.shared.fixed(value, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Static convenience method for creating dynamic dimensions.
     * [PT] Método estático de conveniência para criar dimensões dinâmicas.
     */
    static func dynamic(_ value: CGFloat, ignoreMultiWindowAdjustment: Bool = false) -> AppDimensDynamic {
        return AppDimens.shared.dynamic(value, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Static convenience method for creating dynamic dimensions from Int.
     * [PT] Método estático de conveniência para criar dimensões dinâmicas a partir de Int.
     */
    static func dynamic(_ value: Int, ignoreMultiWindowAdjustment: Bool = false) -> AppDimensDynamic {
        return AppDimens.shared.dynamic(value, ignoreMultiWindowAdjustment: ignoreMultiWindowAdjustment)
    }
    
    /**
     * [EN] Static convenience method for percentage-based dimensions.
     * [PT] Método estático de conveniência para dimensões baseadas em porcentagem.
     */
    static func percentage(_ percentage: CGFloat, type: ScreenType = .lowest) -> CGFloat {
        return AppDimens.shared.percentage(percentage, type: type)
    }
}
