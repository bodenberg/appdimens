/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
 *
 * Description:
 * Environment system for SwiftUI integration with AppDimens.
 * Provides screen dimensions and adjustment factors through SwiftUI's environment.
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

import SwiftUI
import Foundation

// MARK: - Environment Keys

/// Environment key for storing UI Mode type (device type).
public struct UiModeTypeKey: EnvironmentKey {
    public static let defaultValue: DeviceType = .current()
}

public extension EnvironmentValues {
    var uiModeType: DeviceType {
        get { self[UiModeTypeKey.self] }
        set { self[UiModeTypeKey.self] = newValue }
    }
}

/// Environment key for storing screen dimensions (width, height).
public struct ScreenDimensionsKey: EnvironmentKey {
    // Default value to prevent crashes
    public static let defaultValue: (width: CGFloat, height: CGFloat) = (375, 667)
}

public extension EnvironmentValues {
    var screenDimensions: (width: CGFloat, height: CGFloat) {
        get { self[ScreenDimensionsKey.self] }
        set { self[ScreenDimensionsKey.self] = newValue }
    }
}

/// Environment key for storing adjustment factors (Fixed/Dynamic/AR).
public struct AdjustmentFactorsKey: EnvironmentKey {
    // Default neutral factor
    public static let defaultValue = ScreenAdjustmentFactors(
        withArFactorLowest: 1.0,
        withArFactorHighest: 1.0,
        withoutArFactor: 1.0,
        adjustmentFactorLowest: 1.0,
        adjustmentFactorHighest: 1.0
    )
}

public extension EnvironmentValues {
    var adjustmentFactors: ScreenAdjustmentFactors {
        get { self[AdjustmentFactorsKey.self] }
        set { self[AdjustmentFactorsKey.self] = newValue }
    }
}

// MARK: - DimensProvider

/// Essential view wrapper that captures screen dimensions (GeometryReader)
/// and injects dimensions and adjustment factors into the Environment.
public struct DimensProvider<Content: View>: View {
    @ViewBuilder public let content: Content

    public init(@ViewBuilder content: () -> Content) {
        self.content = content()
    }

    public var body: some View {
        GeometryReader { geometry in
            let screenWidth = geometry.size.width
            let screenHeight = geometry.size.height

            // 1. Calculate adjustment factors ONCE per dimension change
            let factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors(
                width: screenWidth, 
                height: screenHeight
            )

            content
                // 2. Inject UI Mode type
                .environment(\.uiModeType, DeviceType.current())
                // 3. Inject screen dimensions
                .environment(\.screenDimensions, (width: screenWidth, height: screenHeight))
                // 4. Inject adjustment factors
                .environment(\.adjustmentFactors, factors)
        }
    }
}

// MARK: - Environment Extensions

public extension View {
    /// Provides AppDimens environment to the view hierarchy.
    /// This is essential for AppDimens to work properly in SwiftUI.
    func appDimensEnvironment() -> some View {
        DimensProvider {
            self
        }
    }
}
