/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens iOS - Fluid Scaling Model (SwiftUI)
 *
 * Description:
 * Implements fluid (clamp-like) scaling that smoothly interpolates
 * between minimum and maximum values based on screen width breakpoints.
 * Ideal for typography, spacing, and other elements that need smooth
 * transitions across different screen sizes.
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
import SwiftUI

/// Configuration for fluid scaling
public struct FluidConfig {
    let minValue: CGFloat
    let maxValue: CGFloat
    let minWidth: CGFloat
    let maxWidth: CGFloat
    
    public init(minValue: CGFloat, maxValue: CGFloat, minWidth: CGFloat, maxWidth: CGFloat) {
        self.minValue = minValue
        self.maxValue = maxValue
        self.minWidth = minWidth
        self.maxWidth = maxWidth
    }
}

/// AppDimensFluid - Fluid (Clamp-like) Scaling Model for iOS
///
/// Provides smooth interpolation between minimum and maximum values
/// based on screen width. Similar to CSS clamp() but for iOS/SwiftUI.
///
/// Philosophy: Smooth transitions with bounded growth
/// Ideal for: Typography, fluid spacing, responsive sizes with limits
///
/// - Example:
/// ```swift
/// // Basic usage
/// let fluid = AppDimensFluid(minValue: 16, maxValue: 24)
/// let fontSize = fluid.calculate(screenWidth: UIScreen.main.bounds.width)
///
/// // With device type qualifiers
/// let fluid = AppDimensFluid(minValue: 16, maxValue: 24)
///     .device(.tablet, minValue: 20, maxValue: 32)
///     .device(.tv, minValue: 24, maxValue: 40)
/// ```
public class AppDimensFluid {
    private let minValue: CGFloat
    private let maxValue: CGFloat
    private let minWidth: CGFloat
    private let maxWidth: CGFloat
    private var deviceQualifiers: [DeviceType: FluidConfig] = [:]
    private var screenQualifiers: [CGFloat: FluidConfig] = [:]
    
    /// Creates a new Fluid dimension builder
    ///
    /// - Parameters:
    ///   - minValue: Minimum value (at minWidth or below)
    ///   - maxValue: Maximum value (at maxWidth or above)
    ///   - minWidth: Minimum screen width breakpoint (default: 320)
    ///   - maxWidth: Maximum screen width breakpoint (default: 768)
    public init(
        minValue: CGFloat,
        maxValue: CGFloat,
        minWidth: CGFloat = 320,
        maxWidth: CGFloat = 768
    ) {
        self.minValue = minValue
        self.maxValue = maxValue
        self.minWidth = minWidth
        self.maxWidth = maxWidth
    }
    
    /// Set fluid values for specific device type
    ///
    /// - Parameters:
    ///   - type: Device type
    ///   - minValue: Minimum value for this device
    ///   - maxValue: Maximum value for this device
    ///   - minWidth: Optional custom min width for this device
    ///   - maxWidth: Optional custom max width for this device
    /// - Returns: This instance for chaining
    ///
    /// - Example:
    /// ```swift
    /// let fontSize = AppDimensFluid(minValue: 14, maxValue: 18)
    ///     .device(.tablet, minValue: 18, maxValue: 24)
    ///     .device(.tv, minValue: 24, maxValue: 32)
    /// ```
    @discardableResult
    public func device(
        _ type: DeviceType,
        minValue: CGFloat,
        maxValue: CGFloat,
        minWidth: CGFloat? = nil,
        maxWidth: CGFloat? = nil
    ) -> AppDimensFluid {
        deviceQualifiers[type] = FluidConfig(
            minValue: minValue,
            maxValue: maxValue,
            minWidth: minWidth ?? self.minWidth,
            maxWidth: maxWidth ?? self.maxWidth
        )
        return self
    }
    
    /// Set fluid values for specific screen width qualifier
    ///
    /// - Parameters:
    ///   - qualifier: Screen width qualifier (e.g., 600 for sw600pt)
    ///   - minValue: Minimum value for this qualifier
    ///   - maxValue: Maximum value for this qualifier
    ///   - minWidth: Optional custom min width
    ///   - maxWidth: Optional custom max width
    /// - Returns: This instance for chaining
    ///
    /// - Example:
    /// ```swift
    /// let spacing = AppDimensFluid(minValue: 8, maxValue: 16)
    ///     .screen(600, minValue: 12, maxValue: 20)
    ///     .screen(840, minValue: 16, maxValue: 24)
    /// ```
    @discardableResult
    public func screen(
        _ qualifier: CGFloat,
        minValue: CGFloat,
        maxValue: CGFloat,
        minWidth: CGFloat? = nil,
        maxWidth: CGFloat? = nil
    ) -> AppDimensFluid {
        screenQualifiers[qualifier] = FluidConfig(
            minValue: minValue,
            maxValue: maxValue,
            minWidth: minWidth ?? self.minWidth,
            maxWidth: maxWidth ?? self.maxWidth
        )
        return self
    }
    
    /// Calculate the fluid value based on current screen width
    ///
    /// - Parameters:
    ///   - screenWidth: Current screen width in points
    ///   - deviceType: Optional device type for qualifier resolution
    /// - Returns: Interpolated value between min and max
    public func calculate(
        screenWidth: CGFloat,
        deviceType: DeviceType? = nil
    ) -> CGFloat {
        // Resolve which config to use based on qualifiers
        let config = resolveConfig(width: screenWidth, deviceType: deviceType)
        
        // Perform fluid interpolation
        return interpolate(width: screenWidth, config: config)
    }
    
    /// Get the minimum value
    public func getMin() -> CGFloat {
        return minValue
    }
    
    /// Get the maximum value
    public func getMax() -> CGFloat {
        return maxValue
    }
    
    /// Get the preferred (middle) value
    public func getPreferred() -> CGFloat {
        return (minValue + maxValue) / 2
    }
    
    /// Linear interpolation at a specific progress point
    ///
    /// - Parameter t: Progress value between 0 and 1
    /// - Returns: Interpolated value
    public func lerp(_ t: CGFloat) -> CGFloat {
        let clampedT = max(0, min(1, t))
        return minValue + (maxValue - minValue) * clampedT
    }
    
    /// Resolve which configuration to use based on qualifiers
    /// Priority: Device Type > Screen Qualifier > Default
    private func resolveConfig(
        width: CGFloat,
        deviceType: DeviceType?
    ) -> FluidConfig {
        // Priority 1: Device Type
        if let type = deviceType, let config = deviceQualifiers[type] {
            return config
        }
        
        // Priority 2: Screen Qualifier (find largest matching)
        var matchedConfig: FluidConfig? = nil
        var largestQualifier: CGFloat = 0
        
        for (qualifier, config) in screenQualifiers {
            if width >= qualifier && qualifier > largestQualifier {
                matchedConfig = config
                largestQualifier = qualifier
            }
        }
        
        if let config = matchedConfig {
            return config
        }
        
        // Priority 3: Default
        return FluidConfig(
            minValue: minValue,
            maxValue: maxValue,
            minWidth: minWidth,
            maxWidth: maxWidth
        )
    }
    
    /// Perform fluid interpolation (clamp-like behavior)
    private func interpolate(width: CGFloat, config: FluidConfig) -> CGFloat {
        // Below minimum width: return min value
        if width <= config.minWidth {
            return config.minValue
        }
        
        // Above maximum width: return max value
        if width >= config.maxWidth {
            return config.maxValue
        }
        
        // Between min and max: linear interpolation
        let progress = (width - config.minWidth) / (config.maxWidth - config.minWidth)
        return config.minValue + (config.maxValue - config.minValue) * progress
    }
}

// MARK: - SwiftUI Extensions

#if canImport(SwiftUI)
import SwiftUI

/// SwiftUI View extension for fluid dimensions
@available(iOS 13.0, macOS 10.15, tvOS 13.0, watchOS 6.0, *)
public extension View {
    /// Apply fluid padding to the view
    ///
    /// - Parameters:
    ///   - minPadding: Minimum padding value
    ///   - maxPadding: Maximum padding value
    ///   - minWidth: Minimum screen width (default: 320)
    ///   - maxWidth: Maximum screen width (default: 768)
    /// - Returns: View with fluid padding applied
    ///
    /// - Example:
    /// ```swift
    /// Text("Hello")
    ///     .fluidPadding(min: 8, max: 16)
    /// ```
    func fluidPadding(
        min minPadding: CGFloat,
        max maxPadding: CGFloat,
        minWidth: CGFloat = 320,
        maxWidth: CGFloat = 768
    ) -> some View {
        GeometryReader { geometry in
            let fluid = AppDimensFluid(
                minValue: minPadding,
                maxValue: maxPadding,
                minWidth: minWidth,
                maxWidth: maxWidth
            )
            let padding = fluid.calculate(screenWidth: geometry.size.width)
            
            self.padding(padding)
        }
    }
    
    /// Apply fluid frame to the view
    ///
    /// - Parameters:
    ///   - minWidth: Minimum width value
    ///   - maxWidth: Maximum width value
    ///   - minHeight: Minimum height value (optional)
    ///   - maxHeight: Maximum height value (optional)
    ///   - screenMinWidth: Minimum screen width breakpoint (default: 320)
    ///   - screenMaxWidth: Maximum screen width breakpoint (default: 768)
    /// - Returns: View with fluid frame applied
    func fluidFrame(
        minWidth: CGFloat,
        maxWidth: CGFloat,
        minHeight: CGFloat? = nil,
        maxHeight: CGFloat? = nil,
        screenMinWidth: CGFloat = 320,
        screenMaxWidth: CGFloat = 768
    ) -> some View {
        GeometryReader { geometry in
            let widthFluid = AppDimensFluid(
                minValue: minWidth,
                maxValue: maxWidth,
                minWidth: screenMinWidth,
                maxWidth: screenMaxWidth
            )
            let width = widthFluid.calculate(screenWidth: geometry.size.width)
            
            let height: CGFloat? = {
                guard let minH = minHeight, let maxH = maxHeight else { return nil }
                let heightFluid = AppDimensFluid(
                    minValue: minH,
                    maxValue: maxH,
                    minWidth: screenMinWidth,
                    maxWidth: screenMaxWidth
                )
                return heightFluid.calculate(screenWidth: geometry.size.width)
            }()
            
            self.frame(width: width, height: height)
        }
    }
}

/// CGFloat extension for creating fluid builders
public extension CGFloat {
    /// Creates a fluid dimension builder from this CGFloat value to a maximum value
    ///
    /// - Parameters:
    ///   - maxValue: The maximum value
    ///   - minWidth: Minimum screen width (default: 320)
    ///   - maxWidth: Maximum screen width (default: 768)
    /// - Returns: An AppDimensFluid instance
    ///
    /// - Example:
    /// ```swift
    /// let fluid = CGFloat(16).fluidTo(24)
    /// ```
    func fluidTo(
        _ maxValue: CGFloat,
        minWidth: CGFloat = 320,
        maxWidth: CGFloat = 768
    ) -> AppDimensFluid {
        return AppDimensFluid(
            minValue: self,
            maxValue: maxValue,
            minWidth: minWidth,
            maxWidth: maxWidth
        )
    }
    
    /// Creates a fluid dimension builder with this CGFloat as maximum value
    ///
    /// - Parameters:
    ///   - minValue: The minimum value
    ///   - minWidth: Minimum screen width (default: 320)
    ///   - maxWidth: Maximum screen width (default: 768)
    /// - Returns: An AppDimensFluid instance
    func fluidFrom(
        _ minValue: CGFloat,
        minWidth: CGFloat = 320,
        maxWidth: CGFloat = 768
    ) -> AppDimensFluid {
        return AppDimensFluid(
            minValue: minValue,
            maxValue: self,
            minWidth: minWidth,
            maxWidth: maxWidth
        )
    }
}

/// Int extension for creating fluid builders
public extension Int {
    /// Creates a fluid dimension builder from this Int value to a maximum value
    func fluidTo(
        _ maxValue: Int,
        minWidth: CGFloat = 320,
        maxWidth: CGFloat = 768
    ) -> AppDimensFluid {
        return AppDimensFluid(
            minValue: CGFloat(self),
            maxValue: CGFloat(maxValue),
            minWidth: minWidth,
            maxWidth: maxWidth
        )
    }
    
    /// Creates a fluid dimension builder with this Int as maximum value
    func fluidFrom(
        _ minValue: Int,
        minWidth: CGFloat = 320,
        maxWidth: CGFloat = 768
    ) -> AppDimensFluid {
        return AppDimensFluid(
            minValue: CGFloat(minValue),
            maxValue: CGFloat(self),
            minWidth: minWidth,
            maxWidth: maxWidth
        )
    }
}

/// Double extension for creating fluid builders
public extension Double {
    /// Creates a fluid dimension builder from this Double value to a maximum value
    func fluidTo(
        _ maxValue: Double,
        minWidth: CGFloat = 320,
        maxWidth: CGFloat = 768
    ) -> AppDimensFluid {
        return AppDimensFluid(
            minValue: CGFloat(self),
            maxValue: CGFloat(maxValue),
            minWidth: minWidth,
            maxWidth: maxWidth
        )
    }
    
    /// Creates a fluid dimension builder with this Double as maximum value
    func fluidFrom(
        _ minValue: Double,
        minWidth: CGFloat = 320,
        maxWidth: CGFloat = 768
    ) -> AppDimensFluid {
        return AppDimensFluid(
            minValue: CGFloat(minValue),
            maxValue: CGFloat(self),
            minWidth: minWidth,
            maxWidth: maxWidth
        )
    }
}

#endif

// MARK: - Utility Functions

/// Create a fluid dimension (shorthand function)
///
/// - Parameters:
///   - minValue: Minimum value
///   - maxValue: Maximum value
///   - minWidth: Minimum screen width (default: 320)
///   - maxWidth: Maximum screen width (default: 768)
/// - Returns: New AppDimensFluid instance
///
/// - Example:
/// ```swift
/// let fontSize = fluid(min: 16, max: 24)
/// ```
public func fluid(
    min minValue: CGFloat,
    max maxValue: CGFloat,
    minWidth: CGFloat = 320,
    maxWidth: CGFloat = 768
) -> AppDimensFluid {
    return AppDimensFluid(
        minValue: minValue,
        maxValue: maxValue,
        minWidth: minWidth,
        maxWidth: maxWidth
    )
}

