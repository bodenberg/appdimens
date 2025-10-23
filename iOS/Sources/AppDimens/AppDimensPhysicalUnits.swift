/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens iOS - Physical Units
 *
 * Description:
 * Physical units conversion utilities for AppDimens iOS library,
 * providing conversion between physical measurements (mm, cm, inch) and points/pixels.
 * Compatible with Android API.
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
import CoreGraphics
import UIKit

/// [EN] Utility class for physical unit conversions.
/// Compatible with Android API.
/// [PT] Classe utilitária para conversões de unidades físicas.
/// Compatível com API Android.
public final class AppDimensPhysicalUnits {
    
    // MARK: - Constants
    
    /// [EN] Points per inch (standard resolution).
    /// [PT] Pontos por polegada (resolução padrão).
    private static let POINTS_PER_INCH: CGFloat = 72.0
    
    /// [EN] Points per centimeter.
    /// [PT] Pontos por centímetro.
    private static let POINTS_PER_CM: CGFloat = POINTS_PER_INCH / 2.54
    
    /// [EN] Points per millimeter.
    /// [PT] Pontos por milímetro.
    private static let POINTS_PER_MM: CGFloat = POINTS_PER_CM / 10.0
    
    /// [EN] Millimeters per inch.
    /// [PT] Milímetros por polegada.
    public static let MM_PER_INCH: CGFloat = 25.4
    
    /// [EN] Centimeters per inch.
    /// [PT] Centímetros por polegada.
    public static let CM_PER_INCH: CGFloat = 2.54
    
    /// [EN] Circumference calculation constant.
    /// [PT] Constante para cálculo de circunferência.
    private static let CIRCUMFERENCE_FACTOR = 2.0 * CGFloat.pi

    // MARK: - Conversion Methods (MM)

    /// [EN] Converts millimeters to logical points (pt).
    /// @param mm The value in millimeters.
    /// @return The value in points.
    /// [PT] Converte milímetros para pontos lógicos (pt).
    /// @param mm O valor em milímetros.
    /// @return O valor em pontos.
    public static func toPointsFromMm(_ mm: CGFloat) -> CGFloat {
        return mm / MM_PER_INCH * POINTS_PER_INCH
    }

    /// [EN] Converts millimeters to physical pixels (px).
    /// @param mm The value in millimeters.
    /// @return The value in pixels.
    /// [PT] Converte milímetros para pixels físicos (px).
    /// @param mm O valor em milímetros.
    /// @return O valor em pixels.
    public static func toPixelsFromMm(_ mm: CGFloat) -> CGFloat {
        let ptValue = toPointsFromMm(mm)
        return AppDimensAdjustmentFactors.pointsToPixels(ptValue)
    }

    /// [EN] Converts millimeters to scalable points (sp).
    /// @param mm The value in millimeters.
    /// @return The value in sp.
    /// [PT] Converte milímetros para pontos escaláveis (sp).
    /// @param mm O valor em milímetros.
    /// @return O valor em sp.
    public static func toSpFromMm(_ mm: CGFloat) -> CGFloat {
        let ptValue = toPointsFromMm(mm)
        let fontScale = AppDimensAdjustmentFactors.getFontScale()
        return ptValue * fontScale
    }

    // MARK: - Conversion Methods (CM)

    /// [EN] Converts centimeters to logical points (pt).
    /// @param cm The value in centimeters.
    /// @return The value in points.
    /// [PT] Converte centímetros para pontos lógicos (pt).
    /// @param cm O valor em centímetros.
    /// @return O valor em pontos.
    public static func toPointsFromCm(_ cm: CGFloat) -> CGFloat {
        return toPointsFromMm(cm * 10.0)
    }

    /// [EN] Converts centimeters to physical pixels (px).
    /// @param cm The value in centimeters.
    /// @return The value in pixels.
    /// [PT] Converte centímetros para pixels físicos (px).
    /// @param cm O valor em centímetros.
    /// @return O valor em pixels.
    public static func toPixelsFromCm(_ cm: CGFloat) -> CGFloat {
        return toPixelsFromMm(cm * 10.0)
    }

    /// [EN] Converts centimeters to scalable points (sp).
    /// @param cm The value in centimeters.
    /// @return The value in sp.
    /// [PT] Converte centímetros para pontos escaláveis (sp).
    /// @param cm O valor em centímetros.
    /// @return O valor em sp.
    public static func toSpFromCm(_ cm: CGFloat) -> CGFloat {
        return toSpFromMm(cm * 10.0)
    }

    // MARK: - Conversion Methods (INCH)

    /// [EN] Converts inches to logical points (pt).
    /// @param inch The value in inches.
    /// @return The value in points.
    /// [PT] Converte polegadas para pontos lógicos (pt).
    /// @param inch O valor em polegadas.
    /// @return O valor em pontos.
    public static func toPointsFromInch(_ inch: CGFloat) -> CGFloat {
        return inch * POINTS_PER_INCH
    }

    /// [EN] Converts inches to physical pixels (px).
    /// @param inch The value in inches.
    /// @return The value in pixels.
    /// [PT] Converte polegadas para pixels físicos (px).
    /// @param inch O valor em polegadas.
    /// @return O valor em pixels.
    public static func toPixelsFromInch(_ inch: CGFloat) -> CGFloat {
        let ptValue = toPointsFromInch(inch)
        return AppDimensAdjustmentFactors.pointsToPixels(ptValue)
    }

    /// [EN] Converts inches to scalable points (sp).
    /// @param inch The value in inches.
    /// @return The value in sp.
    /// [PT] Converte polegadas para pontos escaláveis (sp).
    /// @param inch O valor em polegadas.
    /// @return O valor em sp.
    public static func toSpFromInch(_ inch: CGFloat) -> CGFloat {
        let ptValue = toPointsFromInch(inch)
        let fontScale = AppDimensAdjustmentFactors.getFontScale()
        return ptValue * fontScale
    }

    // MARK: - Legacy Method Names (for backward compatibility)

    /// [DEPRECATED] Use toPointsFromMm() instead.
    @available(*, deprecated, message: "Use toPointsFromMm() instead for consistency with Android API")
    public static func toPoints(mm: CGFloat) -> CGFloat {
        return toPointsFromMm(mm)
    }

    /// [DEPRECATED] Use toPointsFromCm() instead.
    @available(*, deprecated, message: "Use toPointsFromCm() instead for consistency with Android API")
    public static func toPoints(cm: CGFloat) -> CGFloat {
        return toPointsFromCm(cm)
    }

    /// [DEPRECATED] Use toPointsFromInch() instead.
    @available(*, deprecated, message: "Use toPointsFromInch() instead for consistency with Android API")
    public static func toPoints(inches: CGFloat) -> CGFloat {
        return toPointsFromInch(inches)
    }

    // MARK: - Utility Methods

    /// [EN] Converts a diameter value in a specific physical unit to radius in points.
    /// @param diameter The diameter value.
    /// @param type The unit type (mm, cm, inch).
    /// @return The radius in points.
    /// [PT] Converte um valor de diâmetro em uma unidade física específica para raio em pontos.
    /// @param diameter O valor do diâmetro.
    /// @param type O tipo de unidade (mm, cm, inch).
    /// @return O raio em pontos.
    public static func radiusFromDiameter(_ diameter: CGFloat, type: UnitType) -> CGFloat {
        let diameterInPoints: CGFloat
        
        switch type {
        case .mm:
            diameterInPoints = toPointsFromMm(diameter)
        case .cm:
            diameterInPoints = toPointsFromCm(diameter)
        case .inch:
            diameterInPoints = toPointsFromInch(diameter)
        case .pt, .sp, .px:
            diameterInPoints = diameter
        }
        
        return diameterInPoints / 2.0
    }
    
    /// [DEPRECATED] Use radiusFromDiameter() instead.
    @available(*, deprecated, renamed: "radiusFromDiameter")
    public static func radius(diameter: CGFloat, type: UnitType) -> CGFloat {
        return radiusFromDiameter(diameter, type: type)
    }
    
    /// [EN] Converts a circumference value in a specific physical unit to radius in points.
    /// @param circumference The circumference value.
    /// @param type The unit type (mm, cm, inch).
    /// @return The radius in points.
    /// [PT] Converte um valor de circunferência em uma unidade física específica para raio em pontos.
    /// @param circumference O valor da circunferência.
    /// @param type O tipo de unidade (mm, cm, inch).
    /// @return O raio em pontos.
    public static func radiusFromCircumference(_ circumference: CGFloat, type: UnitType) -> CGFloat {
        let circumferenceInPoints: CGFloat
        
        switch type {
        case .mm:
            circumferenceInPoints = toPointsFromMm(circumference)
        case .cm:
            circumferenceInPoints = toPointsFromCm(circumference)
        case .inch:
            circumferenceInPoints = toPointsFromInch(circumference)
        case .pt, .sp, .px:
            circumferenceInPoints = circumference
        }
        
        return circumferenceInPoints / CIRCUMFERENCE_FACTOR
    }
    
    /// [DEPRECATED] Use radiusFromCircumference() or radiusFromDiameter() instead.
    @available(*, deprecated, message: "Use radiusFromCircumference() or radiusFromDiameter() instead")
    public static func displayMeasureDiameter(diameter: CGFloat, isCircumference: Bool) -> CGFloat {
        return isCircumference ? (diameter * CIRCUMFERENCE_FACTOR) : diameter
    }

    // MARK: - Conversion Between Physical Units

    /// [EN] Converts MM to CM.
    /// [PT] Converte MM para CM.
    public static func mmToCm(_ mm: CGFloat) -> CGFloat {
        return mm / 10.0
    }

    /// [EN] Converts MM to Inch.
    /// [PT] Converte MM para Inch.
    public static func mmToInch(_ mm: CGFloat) -> CGFloat {
        return mm / MM_PER_INCH
    }

    /// [EN] Converts CM to MM.
    /// [PT] Converte CM para MM.
    public static func cmToMm(_ cm: CGFloat) -> CGFloat {
        return cm * 10.0
    }

    /// [EN] Converts CM to Inch.
    /// [PT] Converte CM para Inch.
    public static func cmToInch(_ cm: CGFloat) -> CGFloat {
        return cm / CM_PER_INCH
    }

    /// [EN] Converts Inch to MM.
    /// [PT] Converte Inch para MM.
    public static func inchToMm(_ inch: CGFloat) -> CGFloat {
        return inch * MM_PER_INCH
    }

    /// [EN] Converts Inch to CM.
    /// [PT] Converte Inch para CM.
    public static func inchToCm(_ inch: CGFloat) -> CGFloat {
        return inch * CM_PER_INCH
    }
}

// MARK: - Quick Access Extensions

public extension Int {
    /// [EN] Converts Int to points from millimeters.
    /// [PT] Converte Int para pontos de milímetros.
    var mm: CGFloat { AppDimensPhysicalUnits.toPointsFromMm(CGFloat(self)) }
    
    /// [EN] Converts Int to points from centimeters.
    /// [PT] Converte Int para pontos de centímetros.
    var cm: CGFloat { AppDimensPhysicalUnits.toPointsFromCm(CGFloat(self)) }
    
    /// [EN] Converts Int to points from inches.
    /// [PT] Converte Int para pontos de polegadas.
    var inch: CGFloat { AppDimensPhysicalUnits.toPointsFromInch(CGFloat(self)) }
    
    /// [EN] Converts Int to pixels from millimeters.
    /// [PT] Converte Int para pixels de milímetros.
    var mmToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromMm(CGFloat(self)) }
    
    /// [EN] Converts Int to pixels from centimeters.
    /// [PT] Converte Int para pixels de centímetros.
    var cmToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromCm(CGFloat(self)) }
    
    /// [EN] Converts Int to pixels from inches.
    /// [PT] Converte Int para pixels de polegadas.
    var inchToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromInch(CGFloat(self)) }
}

public extension CGFloat {
    /// [EN] Converts CGFloat to points from millimeters.
    /// [PT] Converte CGFloat para pontos de milímetros.
    var mm: CGFloat { AppDimensPhysicalUnits.toPointsFromMm(self) }
    
    /// [EN] Converts CGFloat to points from centimeters.
    /// [PT] Converte CGFloat para pontos de centímetros.
    var cm: CGFloat { AppDimensPhysicalUnits.toPointsFromCm(self) }
    
    /// [EN] Converts CGFloat to points from inches.
    /// [PT] Converte CGFloat para pontos de polegadas.
    var inch: CGFloat { AppDimensPhysicalUnits.toPointsFromInch(self) }
    
    /// [EN] Converts CGFloat to pixels from millimeters.
    /// [PT] Converte CGFloat para pixels de milímetros.
    var mmToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromMm(self) }
    
    /// [EN] Converts CGFloat to pixels from centimeters.
    /// [PT] Converte CGFloat para pixels de centímetros.
    var cmToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromCm(self) }
    
    /// [EN] Converts CGFloat to pixels from inches.
    /// [PT] Converte CGFloat para pixels de polegadas.
    var inchToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromInch(self) }
    
    /// [EN] Calculates radius from diameter.
    /// [PT] Calcula raio do diâmetro.
    func radius(type: UnitType = .pt) -> CGFloat { 
        AppDimensPhysicalUnits.radiusFromDiameter(self, type: type) 
    }
    
    /// [EN] Converts between physical units.
    /// [PT] Converte entre unidades físicas.
    func mmToCm() -> CGFloat { AppDimensPhysicalUnits.mmToCm(self) }
    func mmToInch() -> CGFloat { AppDimensPhysicalUnits.mmToInch(self) }
    func cmToMm() -> CGFloat { AppDimensPhysicalUnits.cmToMm(self) }
    func cmToInch() -> CGFloat { AppDimensPhysicalUnits.cmToInch(self) }
    func inchToMm() -> CGFloat { AppDimensPhysicalUnits.inchToMm(self) }
    func inchToCm() -> CGFloat { AppDimensPhysicalUnits.inchToCm(self) }
}

public extension Double {
    /// [EN] Converts Double to points from millimeters.
    /// [PT] Converte Double para pontos de milímetros.
    var mm: CGFloat { AppDimensPhysicalUnits.toPointsFromMm(CGFloat(self)) }
    
    /// [EN] Converts Double to points from centimeters.
    /// [PT] Converte Double para pontos de centímetros.
    var cm: CGFloat { AppDimensPhysicalUnits.toPointsFromCm(CGFloat(self)) }
    
    /// [EN] Converts Double to points from inches.
    /// [PT] Converte Double para pontos de polegadas.
    var inch: CGFloat { AppDimensPhysicalUnits.toPointsFromInch(CGFloat(self)) }
    
    /// [EN] Converts Double to pixels from millimeters.
    /// [PT] Converte Double para pixels de milímetros.
    var mmToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromMm(CGFloat(self)) }
    
    /// [EN] Converts Double to pixels from centimeters.
    /// [PT] Converte Double para pixels de centímetros.
    var cmToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromCm(CGFloat(self)) }
    
    /// [EN] Converts Double to pixels from inches.
    /// [PT] Converte Double para pixels de polegadas.
    var inchToPx: CGFloat { AppDimensPhysicalUnits.toPixelsFromInch(CGFloat(self)) }
}

// MARK: - AppDimens Integration

public extension AppDimens {
    /// [EN] Converts millimeters to points.
    /// [PT] Converte milímetros para pontos.
    func toPoints(fromMm mm: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPointsFromMm(mm)
    }
    
    /// [EN] Converts centimeters to points.
    /// [PT] Converte centímetros para pontos.
    func toPoints(fromCm cm: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPointsFromCm(cm)
    }
    
    /// [EN] Converts inches to points.
    /// [PT] Converte polegadas para pontos.
    func toPoints(fromInches inches: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPointsFromInch(inches)
    }
    
    /// [EN] Converts millimeters to pixels.
    /// [PT] Converte milímetros para pixels.
    func toPixels(fromMm mm: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPixelsFromMm(mm)
    }
    
    /// [EN] Converts centimeters to pixels.
    /// [PT] Converte centímetros para pixels.
    func toPixels(fromCm cm: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPixelsFromCm(cm)
    }
    
    /// [EN] Converts inches to pixels.
    /// [PT] Converte polegadas para pixels.
    func toPixels(fromInches inches: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPixelsFromInch(inches)
    }
}
