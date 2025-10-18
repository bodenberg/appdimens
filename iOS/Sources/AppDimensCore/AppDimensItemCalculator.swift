/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
 *
 * Description:
 * Item count calculator for AppDimens.
 * Calculates how many items fit in a container based on item size and padding.
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

// MARK: - Item Count Calculator Modifier

/// ViewModifier for calculating available item count in a container.
public struct ItemCountCalculatorModifier: ViewModifier {
    let itemSize: CGFloat
    let itemPadding: CGFloat
    let direction: ScreenType
    @Binding var itemCount: Int
    
    public func body(content: Content) -> some View {
        content
            .background(
                GeometryReader { geometry in
                    Color.clear
                        .onAppear {
                            calculate(geometry: geometry)
                        }
                        .onChange(of: geometry.size) { _ in
                            calculate(geometry: geometry)
                        }
                }
            )
    }

    private func calculate(geometry: GeometryProxy) {
        let availableSize: CGFloat
        let width = geometry.size.width
        let height = geometry.size.height

        switch direction {
        case .highest: availableSize = max(width, height)
        case .lowest: availableSize = min(width, height)
        }

        // Logic: totalItemSize = itemSize + (itemPadding * 2)
        let totalItemSize = itemSize + (itemPadding * 2)
        
        let count: Int = if totalItemSize > 0 {
            Int(floor(availableSize / totalItemSize))
        } else {
            0
        }
        
        // Update Binding only if value changed
        if count != itemCount {
            itemCount = count
        }
    }
}

// MARK: - View Extensions

public extension View {
    /// Applies the item count calculator modifier.
    func calculateAvailableItemCount(
        itemSize: CGFloat,
        itemPadding: CGFloat,
        direction: ScreenType = .lowest,
        count: Binding<Int>
    ) -> some View {
        self.modifier(ItemCountCalculatorModifier(
            itemSize: itemSize,
            itemPadding: itemPadding,
            direction: direction,
            itemCount: count
        ))
    }
    
    /// Applies the item count calculator with AppDimens values.
    func calculateAvailableItemCount(
        itemSize: CGFloat,
        itemPadding: CGFloat,
        direction: ScreenType = .lowest,
        count: Binding<Int>
    ) -> some View {
        self.modifier(ItemCountCalculatorModifier(
            itemSize: itemSize,
            itemPadding: itemPadding,
            direction: direction,
            itemCount: count
        ))
    }
}

// MARK: - AppDimens Integration

public extension AppDimens {
    /// Calculates the maximum number of items that can fit in a container.
    func calculateAvailableItemCount(
        containerSize: CGFloat,
        itemSize: CGFloat,
        itemMargin: CGFloat
    ) -> Int {
        return AppDimensAdjustmentFactors.calculateAvailableItemCount(
            containerSize: containerSize,
            itemSize: itemSize,
            itemMargin: itemMargin
        )
    }
}
