import Foundation
import CoreGraphics
import SwiftUI

/// Classe utilitária para converter unidades físicas (MM, CM, INCH) em
/// pontos lógicos (`DimensPoint`/CGFloat).
/// (Kotlin: AppDimensPhysicalUnits)
public final class UnitConverter {

    private static let MM_TO_INCH_FACTOR = AppDimensConstants.MM_TO_INCH_FACTOR
    private static let POINTS_PER_INCH = AppDimensConstants.POINTS_PER_INCH
    private static let CIRCUMFERENCE_FACTOR = AppDimensConstants.CIRCUMFERENCE_FACTOR

    /// Converte Milímetros (MM) para Points/Pixels lógicos (PX).
    public static func toPoints(mm: DimensPoint) -> DimensPoint {
        // Conversão: mm -> inch (mm / 25.4) -> points ( * 72.0)
        return mm / MM_TO_INCH_FACTOR * POINTS_PER_INCH
    }

    /// Converte Centímetros (CM) para Points/Pixels lógicos (PX).
    public static func toPoints(cm: DimensPoint) -> DimensPoint {
        // Converte CM para MM e depois para Points
        return toPoints(mm: cm * 10.0)
    }

    /// Converte Polegadas (Inch) para Points/Pixels lógicos (PX).
    public static func toPoints(inches: DimensPoint) -> DimensPoint {
        return inches * POINTS_PER_INCH
    }

    /// Calcula o Raio (Radius) em Points a partir de um Diâmetro (Diameter) em uma Unidade.
    public static func radius(diameter: DimensPoint, type: UnitType) -> DimensPoint {
        let diameterInPoints: DimensPoint
        switch type {
        case .inch: diameterInPoints = toPoints(inches: diameter)
        case .cm: diameterInPoints = toPoints(cm: diameter)
        case .mm: diameterInPoints = toPoints(mm: diameter)
        case .sp, .dp, .px: diameterInPoints = diameter // DP/SP/PX já são pontos
        }
        return diameterInPoints / 2.0
    }
    
    /// Ajusta um valor de diâmetro para Circunferência se solicitado.
    public static func displayMeasureDiameter(diameter: DimensPoint, isCircumference: Bool) -> DimensPoint {
        return isCircumference ? (diameter * CIRCUMFERENCE_FACTOR) : diameter
    }
}

// MARK: - Extensões de Acesso Rápido

public extension Int {
    var mm: DimensPoint { UnitConverter.toPoints(mm: DimensPoint(self)) }
    var cm: DimensPoint { UnitConverter.toPoints(cm: DimensPoint(self)) }
    var inch: DimensPoint { UnitConverter.toPoints(inches: DimensPoint(self)) }
}

public extension DimensPoint {
    var mm: DimensPoint { UnitConverter.toPoints(mm: self) }
    var cm: DimensPoint { UnitConverter.toPoints(cm: self) }
    var inch: DimensPoint { UnitConverter.toPoints(inches: self) }
    
    func radius(type: UnitType = .dp) -> DimensPoint { UnitConverter.radius(diameter: self, type: type) }
}
