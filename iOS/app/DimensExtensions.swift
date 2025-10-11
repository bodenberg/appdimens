import SwiftUI
import Foundation

// MARK: - Result Wrapper e Extensões de Inicialização

/// Armazena o calculador (Fixed/Dynamic) e resolve o valor final usando o Environment.
/// (Kotlin: AppDimensFixed / AppDimensDynamic - acesso final)
public struct DimensionResult<T: DimensionCalculator> {
    @Environment(\.screenDimensions) private var screenDimensions
    @Environment(\.adjustmentFactors) private var adjustmentFactors
    @Environment(\.uiModeType) private var uiModeType
    public let calculator: T

    /// Propriedade que retorna o valor calculado em pontos (CGFloat). (Kotlin: .dp)
    public var dimension: DimensPoint {
        calculator.calculate(
            screenWidth: screenDimensions.width, 
            screenHeight: screenDimensions.height,
            factors: adjustmentFactors,
            uiMode: uiModeType
        )
    }
    
    /// Propriedade que retorna o valor calculado em pontos (CGFloat) para texto/SP. (Kotlin: .sp)
    public var sp: DimensPoint {
        // Em SwiftUI, Text é escalado automaticamente. Usamos o mesmo valor.
        dimension
    }

    /// Wrapper que injeta o valor calculado em um bloco de conteúdo View.
    public func dimension<Content: View>(@ViewBuilder content: @escaping (DimensPoint) -> Content) -> Content {
        content(dimension)
    }
}

// Extensões para iniciar a cadeia de dimensionamento (Ex: 30.fixed() ou 30.0.dynamic())
public extension Int {
    func fixed() -> DimensionResult<Fixed> { 
        return DimensionResult(calculator: Fixed(DimensPoint(self))) 
    }
    func dynamic() -> DimensionResult<Dynamic> { 
        return DimensionResult(calculator: Dynamic(DimensPoint(self))) 
    }
}

public extension DimensPoint {
    func fixed() -> DimensionResult<Fixed> { 
        return DimensionResult(calculator: Fixed(self)) 
    }
    func dynamic() -> DimensionResult<Dynamic> { 
        return DimensionResult(calculator: Dynamic(self)) 
    }
    
    // Converte a porcentagem (0.0 a 1.0) para o valor DP de referência (300.0).
    // Ex: 0.5.dynamicPercentage() é (0.5 * 300) = 150.dynamic()
    func dynamicPercentage() -> DimensionResult<Dynamic> {
        let BASE_WIDTH_DP = AppDimensConstants.BASE_WIDTH_DP
        let baseValue = self * BASE_WIDTH_DP
        return DimensionResult(calculator: Dynamic(baseValue))
    }
}

// MARK: - Modificador de Contagem de Itens

/// ViewModifier para replicar a lógica de cálculo de itens disponíveis no Compose/Kotlin.
/// (Kotlin: CalculateAvailableItemCount)
public struct ItemCountCalculatorModifier: ViewModifier {
    let itemSize: DimensPoint
    let itemPadding: DimensPoint
    let direction: DpQualifier
    @Binding var itemCount: Int
    
    public func body(content: Content) -> some View {
        content
            .background(
                GeometryReader { geometry in
                    Color.clear
                        .onAppear {
                            calculate(geometry: geometry)
                        }
                        .onChange(of: geometry.size) { _ in
                            calculate(geometry: geometry)
                        }
                }
            )
    }

    private func calculate(geometry: GeometryProxy) {
        let availableSize: DimensPoint
        let width = geometry.size.width
        let height = geometry.size.height

        switch direction {
        case .height: availableSize = height
        case .width: availableSize = width
        case .smallWidth: availableSize = min(width, height)
        }

        // Lógica do Kotlin: totalItemSize = itemSize + (itemPadding * 2)
        let totalItemSize = itemSize + (itemPadding * 2)
        
        let count: Int = if totalItemSize > 0 {
            Int(floor(availableSize / totalItemSize))
        } else {
            0
        }
        
        // Atualiza o Binding apenas se o valor mudar
        if count != itemCount {
            itemCount = count
        }
    }
}

public extension View {
    /// Aplica o modificador de cálculo de contagem de itens, replicando a função Composable do Kotlin.
    public func calculateAvailableItemCount(
        itemSize: DimensPoint,
        itemPadding: DimensPoint,
        direction: DpQualifier = .width,
        count: Binding<Int>
    ) -> some View {
        self.modifier(ItemCountCalculatorModifier(
            itemSize: itemSize,
            itemPadding: itemPadding,
            direction: direction,
            itemCount: count
        ))
    }
}
