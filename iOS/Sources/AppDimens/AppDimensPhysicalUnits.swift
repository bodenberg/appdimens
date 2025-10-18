/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
 *
 * Description:
 * Physical units converter for AppDimens.
 * Converts millimeters, centimeters, and inches to logical points.
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

/// Utility class for converting physical units (MM, CM, INCH) to logical points.
public final class AppDimensPhysicalUnits {
    
    // MARK: - Constants
    
    private static let MM_TO_INCH_FACTOR: CGFloat = 25.4
    private static let POINTS_PER_INCH: CGFloat = 72.0
    private static let CIRCUMFERENCE_FACTOR = 2.0 * CGFloat.pi

    // MARK: - Conversion Methods

    /// Converts Millimeters (MM) to Points.
    public static func toPoints(mm: CGFloat) -> CGFloat {
        // Conversion: mm -> inch (mm / 25.4) -> points (* 72.0)
        return mm / MM_TO_INCH_FACTOR * POINTS_PER_INCH
    }

    /// Converts Centimeters (CM) to Points.
    public static func toPoints(cm: CGFloat) -> CGFloat {
        // Convert CM to MM and then to Points
        return toPoints(mm: cm * 10.0)
    }

    /// Converts Inches to Points.
    public static func toPoints(inches: CGFloat) -> CGFloat {
        return inches * POINTS_PER_INCH
    }

    /// Calculates Radius in Points from a Diameter in any unit.
    public static func radius(diameter: CGFloat, type: UnitType) -> CGFloat {
        let diameterInPoints: CGFloat
        switch type {
        case .inch: diameterInPoints = toPoints(inches: diameter)
        case .cm: diameterInPoints = toPoints(cm: diameter)
        case .mm: diameterInPoints = toPoints(mm: diameter)
        case .sp, .pt, .px: diameterInPoints = diameter // PT/SP/PX are already points
        }
        return diameterInPoints / 2.0
    }
    
    /// Adjusts a diameter value for Circumference if requested.
    public static func displayMeasureDiameter(diameter: CGFloat, isCircumference: Bool) -> CGFloat {
        return isCircumference ? (diameter * CIRCUMFERENCE_FACTOR) : diameter
    }
}

// MARK: - Quick Access Extensions

public extension Int {
    var mm: CGFloat { AppDimensPhysicalUnits.toPoints(mm: CGFloat(self)) }
    var cm: CGFloat { AppDimensPhysicalUnits.toPoints(cm: CGFloat(self)) }
    var inch: CGFloat { AppDimensPhysicalUnits.toPoints(inches: CGFloat(self)) }
}

public extension CGFloat {
    var mm: CGFloat { AppDimensPhysicalUnits.toPoints(mm: self) }
    var cm: CGFloat { AppDimensPhysicalUnits.toPoints(cm: self) }
    var inch: CGFloat { AppDimensPhysicalUnits.toPoints(inches: self) }
    
    func radius(type: UnitType = .pt) -> CGFloat { 
        AppDimensPhysicalUnits.radius(diameter: self, type: type) 
    }
}

// MARK: - AppDimens Integration

public extension AppDimens {
    /// Converts millimeters to points.
    func toPoints(fromMm mm: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPoints(mm: mm)
    }
    
    /// Converts centimeters to points.
    func toPoints(fromCm cm: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPoints(cm: cm)
    }
    
    /// Converts inches to points.
    func toPoints(fromInches inches: CGFloat) -> CGFloat {
        return AppDimensPhysicalUnits.toPoints(inches: inches)
    }
}
