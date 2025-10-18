/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Core Module
 *
 * Description:
 * Convenience extensions and utilities for the AppDimens Core module.
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

// MARK: - Protocol-based API Extensions

public extension CGFloat {
    
    /**
     * [EN] Creates a fixed dimension using the protocol-based API.
     * @return A protocol-based dimension builder.
     * [PT] Cria uma dimensão fixa usando a API baseada em protocolo.
     * @return Um construtor de dimensão baseado em protocolo.
     */
    func fixed() -> AppDimensProtocolBuilder {
        return AppDimensProtocolBuilder(baseValue: self, type: .fixed)
    }
    
    /**
     * [EN] Creates a dynamic dimension using the protocol-based API.
     * @return A protocol-based dimension builder.
     * [PT] Cria uma dimensão dinâmica usando a API baseada em protocolo.
     * @return Um construtor de dimensão baseado em protocolo.
     */
    func dynamic() -> AppDimensProtocolBuilder {
        return AppDimensProtocolBuilder(baseValue: self, type: .dynamic)
    }
    
    /**
     * [EN] Creates a percentage-based dimension using the protocol-based API.
     * @return A protocol-based dimension builder.
     * [PT] Cria uma dimensão baseada em porcentagem usando a API baseada em protocolo.
     * @return Um construtor de dimensão baseado em protocolo.
     */
    func dynamicPercentage() -> AppDimensProtocolBuilder {
        return AppDimensProtocolBuilder(baseValue: self, type: .percentage)
    }
}

public extension Int {
    
    /**
     * [EN] Creates a fixed dimension using the protocol-based API.
     * @return A protocol-based dimension builder.
     * [PT] Cria uma dimensão fixa usando a API baseada em protocolo.
     * @return Um construtor de dimensão baseado em protocolo.
     */
    func fixed() -> AppDimensProtocolBuilder {
        return AppDimensProtocolBuilder(baseValue: CGFloat(self), type: .fixed)
    }
    
    /**
     * [EN] Creates a dynamic dimension using the protocol-based API.
     * @return A protocol-based dimension builder.
     * [PT] Cria uma dimensão dinâmica usando a API baseada em protocolo.
     * @return Um construtor de dimensão baseado em protocolo.
     */
    func dynamic() -> AppDimensProtocolBuilder {
        return AppDimensProtocolBuilder(baseValue: CGFloat(self), type: .dynamic)
    }
    
    /**
     * [EN] Creates a percentage-based dimension using the protocol-based API.
     * @return A protocol-based dimension builder.
     * [PT] Cria uma dimensão baseada em porcentagem usando a API baseada em protocolo.
     * @return Um construtor de dimensão baseado em protocolo.
     */
    func dynamicPercentage() -> AppDimensProtocolBuilder {
        return AppDimensProtocolBuilder(baseValue: CGFloat(self), type: .percentage)
    }
}

// MARK: - Physical Units Extensions

public extension CGFloat {
    
    /**
     * [EN] Converts the value from millimeters to points.
     * @return The value in points.
     * [PT] Converte o valor de milímetros para pontos.
     * @return O valor em pontos.
     */
    var mm: CGFloat {
        return AppDimensPhysicalUnits.toPoints(mm: self)
    }
    
    /**
     * [EN] Converts the value from centimeters to points.
     * @return The value in points.
     * [PT] Converte o valor de centímetros para pontos.
     * @return O valor em pontos.
     */
    var cm: CGFloat {
        return AppDimensPhysicalUnits.toPoints(cm: self)
    }
    
    /**
     * [EN] Converts the value from inches to points.
     * @return The value in points.
     * [PT] Converte o valor de polegadas para pontos.
     * @return O valor em pontos.
     */
    var inch: CGFloat {
        return AppDimensPhysicalUnits.toPoints(inches: self)
    }
}

public extension Int {
    
    /**
     * [EN] Converts the value from millimeters to points.
     * @return The value in points.
     * [PT] Converte o valor de milímetros para pontos.
     * @return O valor em pontos.
     */
    var mm: CGFloat {
        return AppDimensPhysicalUnits.toPoints(mm: CGFloat(self))
    }
    
    /**
     * [EN] Converts the value from centimeters to points.
     * @return The value in points.
     * [PT] Converte o valor de centímetros para pontos.
     * @return O valor em pontos.
     */
    var cm: CGFloat {
        return AppDimensPhysicalUnits.toPoints(cm: CGFloat(self))
    }
    
    /**
     * [EN] Converts the value from inches to points.
     * @return The value in points.
     * [PT] Converte o valor de polegadas para pontos.
     * @return O valor em pontos.
     */
    var inch: CGFloat {
        return AppDimensPhysicalUnits.toPoints(inches: CGFloat(self))
    }
}

// MARK: - Protocol-based Dimension Builder

/**
 * [EN] Protocol-based dimension builder for fluent API usage.
 * [PT] Construtor de dimensão baseado em protocolo para uso de API fluente.
 */
public class AppDimensProtocolBuilder {
    
    private let baseValue: CGFloat
    private let type: AppDimensProtocolType
    private var screenType: ScreenType = .lowest
    private var deviceType: DeviceType?
    private var customValue: CGFloat?
    
    internal init(baseValue: CGFloat, type: AppDimensProtocolType) {
        self.baseValue = baseValue
        self.type = type
    }
    
    /**
     * [EN] Sets the screen type for the dimension calculation.
     * @param type The screen type to use.
     * @return The builder instance for chaining.
     * [PT] Define o tipo de tela para o cálculo da dimensão.
     * @param type O tipo de tela a ser usado.
     * @return A instância do construtor para encadeamento.
     */
    @discardableResult
    public func screen(_ type: ScreenType) -> AppDimensProtocolBuilder {
        self.screenType = type
        return self
    }
    
    /**
     * [EN] Sets a custom value for a specific device type.
     * @param deviceType The device type.
     * @param value The custom value.
     * @return The builder instance for chaining.
     * [PT] Define um valor customizado para um tipo de dispositivo específico.
     * @param deviceType O tipo de dispositivo.
     * @param value O valor customizado.
     * @return A instância do construtor para encadeamento.
     */
    @discardableResult
    public func device(_ deviceType: DeviceType, _ value: CGFloat) -> AppDimensProtocolBuilder {
        self.deviceType = deviceType
        self.customValue = value
        return self
    }
    
    /**
     * [EN] Gets the calculated dimension value.
     * @return The calculated dimension.
     * [PT] Obtém o valor da dimensão calculada.
     * @return A dimensão calculada.
     */
    public var dimension: CGFloat {
        return calculateDimension()
    }
    
    /**
     * [EN] Calculates the dimension using the configured parameters.
     * @return The calculated dimension.
     * [PT] Calcula a dimensão usando os parâmetros configurados.
     * @return A dimensão calculada.
     */
    private func calculateDimension() -> CGFloat {
        let valueToUse = customValue ?? baseValue
        
        switch type {
        case .fixed:
            return AppDimens.fixed(valueToUse).type(screenType).toPoints()
        case .dynamic:
            return AppDimens.dynamic(valueToUse).type(screenType).toPoints()
        case .percentage:
            return AppDimens.percentage(valueToUse, type: screenType)
        }
    }
    
    /**
     * [EN] Creates a view with the calculated dimension.
     * @param content The view builder closure.
     * @return The view with the calculated dimension.
     * [PT] Cria uma view com a dimensão calculada.
     * @param content O closure construtor da view.
     * @return A view com a dimensão calculada.
     */
    public func dimension<Content: View>(@ViewBuilder content: @escaping (CGFloat) -> Content) -> some View {
        return content(dimension)
    }
}

// MARK: - Protocol Types

/**
 * [EN] Defines the types of protocol-based dimensions.
 * [PT] Define os tipos de dimensões baseadas em protocolo.
 */
public enum AppDimensProtocolType {
    case fixed
    case dynamic
    case percentage
}

// MARK: - SwiftUI Integration

#if canImport(SwiftUI)
import SwiftUI

@available(iOS 13.0, *)
public extension AppDimensProtocolBuilder {
    
    /**
     * [EN] Creates a SwiftUI view with the calculated dimension.
     * @param content The view builder closure.
     * @return The SwiftUI view with the calculated dimension.
     * [PT] Cria uma view SwiftUI com a dimensão calculada.
     * @param content O closure construtor da view.
     * @return A view SwiftUI com a dimensão calculada.
     */
    func dimension<Content: View>(@ViewBuilder content: @escaping (CGFloat) -> Content) -> some View {
        return content(dimension)
    }
}

@available(iOS 13.0, *)
public extension View {
    
    /**
     * [EN] Calculates the available item count for a container.
     * @param itemSize The size of each item.
     * @param itemPadding The padding around each item.
     * @param count A binding to store the calculated count.
     * @return The view with the item count calculation.
     * [PT] Calcula a contagem de itens disponíveis para um contêiner.
     * @param itemSize O tamanho de cada item.
     * @param itemPadding O padding ao redor de cada item.
     * @param count Um binding para armazenar a contagem calculada.
     * @return A view com o cálculo da contagem de itens.
     */
    func calculateAvailableItemCount(
        itemSize: CGFloat,
        itemPadding: CGFloat,
        count: Binding<Int>
    ) -> some View {
        self.onAppear {
            let containerSize = UIScreen.main.bounds.width
            let calculatedCount = AppDimens.shared.calculateAvailableItemCount(
                containerSize: containerSize,
                itemSize: itemSize,
                itemMargin: itemPadding
            )
            count.wrappedValue = calculatedCount
        }
    }
}
#endif
