/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
 *
 * Description:
 * Dynamic dimension calculator with proportional adjustment.
 * Provides percentage-based scaling for layout containers.
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

// MARK: - Dynamic Calculator

/// Calculates dimension as a percentage of the current screen dimension.
public struct AppDimensDynamicCalculator: DimensionCalculator {
    private let initialBaseValue: CGFloat
    private var ignoreMultiWindowAdjustment: Bool = false
    private var screenType: ScreenType = .lowest
    private var customMap: [ScreenQualifierEntry: CGFloat] = [:]
    private var customDeviceTypeMap: [DeviceType: CGFloat] = [:]

    public init(_ initialBaseValue: CGFloat) {
        self.initialBaseValue = initialBaseValue
    }

    // MARK: - Configuration Methods

    /// Dynamic ignores aspect ratio adjustment, but implements the protocol
    public func withAspectRatio(_ apply: Bool) -> AppDimensDynamicCalculator { 
        return self 
    }

    public func ignoreMultiWindowAdjustment(_ ignore: Bool) -> AppDimensDynamicCalculator {
        var copy = self
        copy.ignoreMultiWindowAdjustment = ignore
        return copy
    }

    public func screen(type: ScreenType) -> AppDimensDynamicCalculator {
        var copy = self
        copy.screenType = type
        return copy
    }

    /// Adds a custom screen qualifier.
    public func add(type: DeviceType, screenSize: CGFloat, customValue: CGFloat) -> AppDimensDynamicCalculator {
        var copy = self
        let entry = ScreenQualifierEntry(deviceType: type, screenSize: screenSize)
        copy.customMap[entry] = customValue
        return copy
    }
    
    /// Adds a custom device type value.
    public func add(deviceType: DeviceType, customValue: CGFloat) -> AppDimensDynamicCalculator {
        var copy = self
        copy.customDeviceTypeMap[deviceType] = customValue
        return copy
    }

    // MARK: - Calculation Logic

    public func calculate(screenWidth: CGFloat, screenHeight: CGFloat, factors: ScreenAdjustmentFactors, deviceType: DeviceType) -> CGFloat {
        let smallestWidth = min(screenWidth, screenHeight)
        let highestDimension = max(screenWidth, screenHeight)
        let BASE_WIDTH_PT = AppDimensAdjustmentFactors.BASE_WIDTH_PT

        let valueToAdjust = resolveQualifierValue(
            customMap: customMap,
            customDeviceTypeMap: customDeviceTypeMap,
            deviceType: deviceType,
            smallestWidth: smallestWidth,
            currentScreenWidth: screenWidth,
            currentScreenHeight: screenHeight,
            initialBaseValue: initialBaseValue
        )

        // Multi-Window detection
        let isSmallWindow = screenWidth < BASE_WIDTH_PT || screenHeight < BASE_WIDTH_PT
        let shouldIgnoreAdjustment = ignoreMultiWindowAdjustment && isSmallWindow
        
        if shouldIgnoreAdjustment {
             // If ignoring, return base value without scaling
             return valueToAdjust
        }

        // Percentage: (Adjusted Base Value / Reference Value)
        let percentage = valueToAdjust / BASE_WIDTH_PT

        // Screen dimension to use (LOWEST or HIGHEST)
        let dimensionToUse = (screenType == .highest) ? highestDimension : smallestWidth

        // Final value is percentage applied to screen dimension
        return dimensionToUse * percentage
    }
    
    // MARK: - Helper Methods
    
    private func resolveQualifierValue(
        customMap: [ScreenQualifierEntry: CGFloat],
        customDeviceTypeMap: [DeviceType: CGFloat],
        deviceType: DeviceType,
        smallestWidth: CGFloat,
        currentScreenWidth: CGFloat,
        currentScreenHeight: CGFloat,
        initialBaseValue: CGFloat
    ) -> CGFloat {
        var valueToAdjust = initialBaseValue
        
        // Priority 1: Device Type
        if let deviceTypeValue = customDeviceTypeMap[deviceType] {
            return deviceTypeValue
        }
        
        // Priority 2: Screen Qualifier
        let sortedQualifiers = customMap.keys.sorted { $0.screenSize > $1.screenSize }
        
        for entry in sortedQualifiers {
            if entry.deviceType == deviceType && smallestWidth >= entry.screenSize {
                return customMap[entry] ?? initialBaseValue
            }
        }
        
        return valueToAdjust
    }
}
