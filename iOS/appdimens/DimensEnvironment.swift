import SwiftUI

// MARK: - Chaves de Ambiente

/// Chave para armazenar o tipo de UI Mode (dispositivo).
public struct UiModeTypeKey: EnvironmentKey {
    public static let defaultValue: UiModeType = .current()
}

public extension EnvironmentValues {
    var uiModeType: UiModeType {
        get { self[UiModeTypeKey.self] }
        set { self[UiModeTypeKey.self] = newValue }
    }
}

/// Chave para armazenar as dimensões da tela (largura, altura).
public struct ScreenDimensionsKey: EnvironmentKey {
    // Valor Default para evitar crashes
    public static let defaultValue: (width: DimensPoint, height: DimensPoint) = (300, 500)
}

public extension EnvironmentValues {
    var screenDimensions: (width: DimensPoint, height: DimensPoint) {
        get { self[ScreenDimensionsKey.self] }
        set { self[ScreenDimensionsKey.self] = newValue }
    }
}

/// Chave para armazenar os fatores de ajuste (Fixed/Dynamic/AR).
public struct AdjustmentFactorsKey: EnvironmentKey {
    // Fator padrão neutro
    public static let defaultValue = ScreenAdjustmentFactors(screenWidth: 300.0, screenHeight: 500.0)
}

public extension EnvironmentValues {
    var adjustmentFactors: ScreenAdjustmentFactors {
        get { self[AdjustmentFactorsKey.self] }
        set { self[AdjustmentFactorsKey.self] = newValue }
    }
}

// MARK: - Provedor de Dimensões (DimensProvider)

/// View Wrapper ESSENCIAL que captura as dimensões da tela (GeometryReader)
/// e injeta as dimensões e os fatores de ajuste no Environment.
public struct DimensProvider<Content: View>: View {
    @ViewBuilder public let content: Content

    public init(@ViewBuilder content: () -> Content) {
        self.content = content()
    }

    public var body: some View {
        GeometryReader { geometry in
            let screenWidth = geometry.size.width
            let screenHeight = geometry.size.height

            // 1. Calcula os fatores de ajuste UMA VEZ por mudança de dimensão
            let factors = ScreenAdjustmentFactors(screenWidth: screenWidth, screenHeight: screenHeight)

            content
                // 2. Injeta o tipo de UI Mode
                .environment(\.uiModeType, UiModeType.current())
                // 3. Injeta as dimensões da tela
                .environment(\.screenDimensions, (width: screenWidth, height: screenHeight))
                // 4. Injeta os fatores de ajuste
                .environment(\.adjustmentFactors, factors)
        }
    }
}
