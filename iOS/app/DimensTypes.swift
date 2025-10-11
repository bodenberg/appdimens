import Foundation
import CoreGraphics
import SwiftUI

// MARK: - Tipos de Dimensão
public typealias DimensPoint = CGFloat

// MARK: - Constantes Centralizadas
private struct AppDimensConstants {
    // Valores de referência do sistema Kotlin
    static let BASE_WIDTH_DP: DimensPoint = 300.0
    static let INCREMENT_DP_STEP: DimensPoint = 30.0
    static let BASE_DP_FACTOR: DimensPoint = 1.0
    static let REFERENCE_AR: DimensPoint = 1.78 // Aspect Ratio de referência (ex: 16:9)
    static let DEFAULT_SENSITIVITY_K: DimensPoint = 0.08
    static let BASE_INCREMENT: DimensPoint = 0.10
    static let CIRCUMFERENCE_FACTOR = 2.0 * DimensPoint.pi

    // Fatores de Conversão de Unidades Físicas (1 point = 1/72 inch)
    static let MM_TO_INCH_FACTOR: DimensPoint = 25.4
    static let POINTS_PER_INCH: DimensPoint = 72.0
}

// MARK: - Enums e Structs Base

/// Define os tipos de qualificador de tela (Kotlin: DpQualifier).
public enum DpQualifier {
    case smallWidth, height, width
}

/// Define qual dimensão da tela deve ser usada como base (Kotlin: ScreenType).
public enum ScreenType {
    case highest, lowest
}

/// Define as unidades de medida física (Kotlin: UnitType).
public enum UnitType {
    case inch, cm, mm, sp, dp, px
}

// MARK: - Qualificadores Customizados

/// Representa uma entrada de qualificador customizado, combinando o tipo e o valor mínimo de DP.
/// (Kotlin: DpQualifierEntry)
public struct DpQualifierEntry: Hashable {
    public let type: DpQualifier
    public let value: DimensPoint
}

/// Define os tipos de modo de interface do usuário (UI Mode Type)
/// (Kotlin: UiModeType) - Adaptado para o sistema operacional
public enum UiModeType: Hashable {
    case iOS, macOS, tvOS, watchOS, visionOS, undefined
    
    public static func current() -> UiModeType {
        #if os(iOS)
        return .iOS
        #elseif os(watchOS)
        return .watchOS
        #elseif os(tvOS)
        return .tvOS
        #elseif os(macOS)
        return .macOS
        #elseif os(visionOS)
        return .visionOS
        #else
        return .undefined
        #endif
    }
}

/// Representa uma entrada de qualificador que combina um tipo de UI Mode (dispositivo)
/// E um qualificador de tela (SW, H, W). (Kotlin: UiModeQualifierEntry)
public struct UiModeQualifierEntry: Hashable {
    public let uiModeType: UiModeType
    public let dpQualifierEntry: DpQualifierEntry
}


// MARK: - Fatores de Ajuste

/// Armazena os fatores de ajuste calculados a partir das dimensões da tela.
/// (Kotlin: ScreenAdjustmentFactors)
public struct ScreenAdjustmentFactors {
    public let withArFactorLowest: DimensPoint
    public let withArFactorHighest: DimensPoint
    public let withoutArFactor: DimensPoint
    public let adjustmentFactorLowest: DimensPoint
    public let adjustmentFactorHighest: DimensPoint
    
    // Constantes
    private let BASE_WIDTH_DP = AppDimensConstants.BASE_WIDTH_DP
    private let INCREMENT_DP_STEP = AppDimensConstants.INCREMENT_DP_STEP
    private let BASE_DP_FACTOR = AppDimensConstants.BASE_DP_FACTOR
    private let BASE_INCREMENT = AppDimensConstants.BASE_INCREMENT
    private let REFERENCE_AR = AppDimensConstants.REFERENCE_AR
    private let DEFAULT_SENSITIVITY_K = AppDimensConstants.DEFAULT_SENSITIVITY_K
    
    /// Calcula a proporção da tela (largura / altura ou altura / largura)
    private func getReferenceAspectRatio(screenWidth: DimensPoint, screenHeight: DimensPoint) -> DimensPoint {
        return max(screenWidth, screenHeight) / min(screenWidth, screenHeight)
    }

    public init(screenWidth: DimensPoint, screenHeight: DimensPoint) {
        let smallestWidthDp = min(screenWidth, screenHeight)
        let highestDimensionDp = max(screenWidth, screenHeight)

        // 1. Fatores Base (diferença em relação à largura base)
        let differenceLowest = smallestWidthDp - BASE_WIDTH_DP
        let differenceHighest = highestDimensionDp - BASE_WIDTH_DP
        
        let adjustmentFactorLowest = differenceLowest / INCREMENT_DP_STEP
        let adjustmentFactorHighest = differenceHighest / INCREMENT_DP_STEP

        // 2. Fator Sem AR
        let withoutArFactor = BASE_DP_FACTOR + adjustmentFactorLowest * BASE_INCREMENT

        // 3. Fator Com AR (cálculo logarítmico para suavizar o ajuste)
        let currentAr = getReferenceAspectRatio(screenWidth: screenWidth, screenHeight: screenHeight)
        // O log() em Swift é o log natural (ln), replicando a função Kotlin
        let continuousAdjustment = (DEFAULT_SENSITIVITY_K * log(currentAr / REFERENCE_AR))
        let finalIncrementValueWithAr = BASE_INCREMENT + continuousAdjustment

        // 4. Cálculo do Fator COMPLETO (LOWEST + AR e HIGHEST + AR)
        let withArFactorLowest = BASE_DP_FACTOR + adjustmentFactorLowest * finalIncrementValueWithAr
        let withArFactorHighest = BASE_DP_FACTOR + adjustmentFactorHighest * finalIncrementValueWithAr

        self.withArFactorLowest = withArFactorLowest
        self.withArFactorHighest = withArFactorHighest
        self.withoutArFactor = withoutArFactor
        self.adjustmentFactorLowest = adjustmentFactorLowest
        self.adjustmentFactorHighest = adjustmentFactorHighest
    }
}
