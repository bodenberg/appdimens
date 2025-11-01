/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
 *
 * Description:
 * Fixed dimension calculator with logarithmic adjustment.
 * Provides refined scaling for UI elements.
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

// MARK: - Fixed Calculator

/// Calculates dimension by applying a scale factor (AR, without AR) to the base value.
public struct AppDimensFixedCalculator: DimensionCalculator {
    private let initialBaseValue: CGFloat
    private var applyAspectRatio: Bool = true
    private var ignoreMultiWindowAdjustment: Bool = false
    private var screenType: ScreenType = .lowest
    private var baseOrientation: BaseOrientation = .auto
    private var customMap: [ScreenQualifierEntry: CGFloat] = [:]
    private var customDeviceTypeMap: [DeviceType: CGFloat] = [:]
    private var customSensitivityK: CGFloat? = nil

    public init(_ initialBaseValue: CGFloat) {
        self.initialBaseValue = initialBaseValue
    }

    // MARK: - Configuration Methods

    /**
     * Primary method - matches Android, Web, React Native, Flutter API
     */
    public func aspectRatio(_ apply: Bool, sensitivity: CGFloat? = nil) -> AppDimensFixedCalculator {
        var copy = self
        copy.applyAspectRatio = apply
        if let sens = sensitivity {
            copy.customSensitivity = sens
        }
        return copy
    }
    
    /**
     * Deprecated - kept for backward compatibility
     * @deprecated Use aspectRatio() instead for consistency with other platforms
     */
    public func withAspectRatio(_ apply: Bool) -> AppDimensFixedCalculator {
        return aspectRatio(apply, sensitivity: nil)
    }

    public func ignoreMultiWindowAdjustment(_ ignore: Bool) -> AppDimensFixedCalculator {
        var copy = self
        copy.ignoreMultiWindowAdjustment = ignore
        return copy
    }
    
    public func screen(type: ScreenType) -> AppDimensFixedCalculator {
        var copy = self
        copy.screenType = type
        return copy
    }
    
    public func baseOrientation(_ orientation: BaseOrientation) -> AppDimensFixedCalculator {
        var copy = self
        copy.baseOrientation = orientation
        return copy
    }
    
    public func portraitLowest() -> AppDimensFixedCalculator {
        var copy = self
        copy.baseOrientation = .portrait
        copy.screenType = .lowest
        return copy
    }
    
    public func portraitHighest() -> AppDimensFixedCalculator {
        var copy = self
        copy.baseOrientation = .portrait
        copy.screenType = .highest
        return copy
    }
    
    public func landscapeLowest() -> AppDimensFixedCalculator {
        var copy = self
        copy.baseOrientation = .landscape
        copy.screenType = .lowest
        return copy
    }
    
    public func landscapeHighest() -> AppDimensFixedCalculator {
        var copy = self
        copy.baseOrientation = .landscape
        copy.screenType = .highest
        return copy
    }
    
    /// Adds a custom screen qualifier.
    public func add(type: DeviceType, screenSize: CGFloat, customValue: CGFloat) -> AppDimensFixedCalculator {
        var copy = self
        let entry = ScreenQualifierEntry(deviceType: type, screenSize: screenSize)
        copy.customMap[entry] = customValue
        return copy
    }
    
    /// Adds a custom device type value.
    public func add(deviceType: DeviceType, customValue: CGFloat) -> AppDimensFixedCalculator {
        var copy = self
        copy.customDeviceTypeMap[deviceType] = customValue
        return copy
    }
    
    /// Recalculates AR factor with custom sensitivity (k).
    public func customSensitivity(k: CGFloat) -> AppDimensFixedCalculator {
        var copy = self
        copy.customSensitivityK = k
        return copy
    }

    // MARK: - Calculation Logic

    public func calculate(screenWidth: CGFloat, screenHeight: CGFloat, factors: ScreenAdjustmentFactors, deviceType: DeviceType) -> CGFloat {
        let smallestWidth = min(screenWidth, screenHeight)
        let BASE_WIDTH_PT = AppDimensAdjustmentFactors.BASE_WIDTH_PT
        let BASE_DP_FACTOR = AppDimensAdjustmentFactors.BASE_DP_FACTOR
        let BASE_INCREMENT = AppDimensAdjustmentFactors.BASE_INCREMENT
        let REFERENCE_AR = AppDimensAdjustmentFactors.REFERENCE_AR

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

        let finalAdjustmentFactor: CGFloat
        
        if applyAspectRatio {
            // Resolve effective screen type based on base orientation
            let effectiveScreenType = AppDimensAdjustmentFactors.resolveScreenType(
                requestedType: screenType,
                baseOrientation: baseOrientation,
                bounds: bounds
            )
            
            // 1. Select pre-calculated AR factor
            var selectedFactor: CGFloat = (effectiveScreenType == .highest) ? factors.withArFactorHighest : factors.withArFactorLowest
            
            if let customSensitivityK = customSensitivityK {
                // 2. If custom sensitivity, recalculate AR factor
                let adjustmentFactorBase = (effectiveScreenType == .highest) ? factors.adjustmentFactorHighest : factors.adjustmentFactorLowest
                let currentAr = AppDimensAdjustmentFactors.getReferenceAspectRatio(screenWidth, screenHeight)
                
                // Logarithmic calculation for sensitivity recalculation
                let continuousAdjustment = (customSensitivityK * log(currentAr / REFERENCE_AR))
                let finalIncrementValue = BASE_INCREMENT + continuousAdjustment
                
                selectedFactor = BASE_DP_FACTOR + adjustmentFactorBase * finalIncrementValue
            }
            
            finalAdjustmentFactor = selectedFactor
            
        } else {
            // Without AR, use pre-calculated without AR factor
            finalAdjustmentFactor = factors.withoutArFactor
        }

        return valueToAdjust * finalAdjustmentFactor
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
