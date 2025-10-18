/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
 *
 * Description:
 * Protocol-based design for AppDimens calculators.
 * Provides a clean API similar to the Kotlin implementation.
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

// MARK: - Dimension Calculator Protocol

/// Protocol that defines the capability to calculate a final dimension.
public protocol DimensionCalculator {
    func withAspectRatio(_ apply: Bool) -> Self
    func ignoreMultiWindowAdjustment(_ ignore: Bool) -> Self
    func screen(type: ScreenType) -> Self
    func calculate(screenWidth: CGFloat, screenHeight: CGFloat, factors: ScreenAdjustmentFactors, deviceType: DeviceType) -> CGFloat
}

// MARK: - Dimension Result Wrapper

/// Stores the calculator (Fixed/Dynamic) and resolves the final value using the Environment.
public struct DimensionResult<T: DimensionCalculator> {
    @Environment(\.screenDimensions) private var screenDimensions
    @Environment(\.adjustmentFactors) private var adjustmentFactors
    @Environment(\.uiModeType) private var deviceType
    public let calculator: T

    /// Property that returns the calculated value in points (CGFloat).
    public var dimension: CGFloat {
        calculator.calculate(
            screenWidth: screenDimensions.width, 
            screenHeight: screenDimensions.height,
            factors: adjustmentFactors,
            deviceType: deviceType
        )
    }
    
    /// Property that returns the calculated value in points (CGFloat) for text/SP.
    public var sp: CGFloat {
        // In SwiftUI, Text is automatically scaled. We use the same value.
        dimension
    }

    /// Wrapper that injects the calculated value into a View content block.
    public func dimension<Content: View>(@ViewBuilder content: @escaping (CGFloat) -> Content) -> Content {
        content(dimension)
    }
}

// MARK: - Initialization Extensions

public extension Int {
    func fixed() -> DimensionResult<AppDimensFixedCalculator> { 
        return DimensionResult(calculator: AppDimensFixedCalculator(CGFloat(self))) 
    }
    func dynamic() -> DimensionResult<AppDimensDynamicCalculator> { 
        return DimensionResult(calculator: AppDimensDynamicCalculator(CGFloat(self))) 
    }
}

public extension CGFloat {
    func fixed() -> DimensionResult<AppDimensFixedCalculator> { 
        return DimensionResult(calculator: AppDimensFixedCalculator(self)) 
    }
    func dynamic() -> DimensionResult<AppDimensDynamicCalculator> { 
        return DimensionResult(calculator: AppDimensDynamicCalculator(self)) 
    }
    
    /// Converts percentage (0.0 to 1.0) to reference DP value (375.0).
    /// Ex: 0.5.dynamicPercentage() is (0.5 * 375) = 187.5.dynamic()
    func dynamicPercentage() -> DimensionResult<AppDimensDynamicCalculator> {
        let BASE_WIDTH_PT = AppDimensAdjustmentFactors.BASE_WIDTH_PT
        let baseValue = self * BASE_WIDTH_PT
        return DimensionResult(calculator: AppDimensDynamicCalculator(baseValue))
    }
}

// MARK: - Direct Syntax Extensions

public extension CGFloat {
    /// Fixed scaling (controlled growth) - equivalent to .fxdp
    var fxpt: CGFloat {
        return AppDimensFixedCalculator(self).calculate(
            screenWidth: AppDimensAdjustmentFactors.getCurrentScreenDimensions().width,
            screenHeight: AppDimensAdjustmentFactors.getCurrentScreenDimensions().height,
            factors: AppDimensAdjustmentFactors.calculateAdjustmentFactors(),
            deviceType: DeviceType.current()
        )
    }
    
    /// Dynamic scaling (proportional growth) - equivalent to .dydp
    var dypt: CGFloat {
        return AppDimensDynamicCalculator(self).calculate(
            screenWidth: AppDimensAdjustmentFactors.getCurrentScreenDimensions().width,
            screenHeight: AppDimensAdjustmentFactors.getCurrentScreenDimensions().height,
            factors: AppDimensAdjustmentFactors.calculateAdjustmentFactors(),
            deviceType: DeviceType.current()
        )
    }
    
    /// Fixed scaling for text/spaced points - equivalent to .sp
    var sp: CGFloat {
        return fxpt
    }
}

public extension Int {
    /// Fixed scaling (controlled growth) - equivalent to .fxdp
    var fxpt: CGFloat {
        return CGFloat(self).fxpt
    }
    
    /// Dynamic scaling (proportional growth) - equivalent to .dydp
    var dypt: CGFloat {
        return CGFloat(self).dypt
    }
    
    /// Fixed scaling for text/spaced points - equivalent to .sp
    var sp: CGFloat {
        return fxpt
    }
}
