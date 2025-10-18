/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Improved SwiftUI Example
 *
 * Description:
 * Example showing the improved AppDimens with Environment system, protocols, and new features.
 */

import SwiftUI
import AppDimens

@available(iOS 13.0, *)
struct ImprovedSwiftUIExampleView: View {
    @State private var itemCount: Int = 0
    @State private var isExpanded = false
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 20.fxpt) { // Fixed spacing
                    
                    // Header section
                    VStack(spacing: 16.fxpt) { // Fixed spacing
                        Text("AppDimens iOS - Improved Version")
                            .font(.fxSystem(size: 24, weight: .bold)) // Fixed font size
                            .multilineTextAlignment(.center)
                        
                        Text("Enhanced with Environment system, protocols, and physical units")
                            .font(.fxSystem(size: 16)) // Fixed font size
                            .foregroundColor(.secondary)
                    }
                    .fxPadding(20) // Fixed padding
                    
                    // Physical Units Section
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("1. Physical Units")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        HStack(spacing: 16.fxpt) {
                            Rectangle()
                                .fill(Color.blue.opacity(0.3))
                                .frame(width: 2.cm, height: 2.cm) // 2 centimeters
                                .overlay(Text("2cm").font(.fxSystem(size: 10)))
                            
                            Rectangle()
                                .fill(Color.red.opacity(0.3))
                                .frame(width: 5.mm, height: 5.mm) // 5 millimeters
                                .overlay(Text("5mm").font(.fxSystem(size: 8)))
                            
                            Rectangle()
                                .fill(Color.green.opacity(0.3))
                                .frame(width: 1.inch, height: 1.inch) // 1 inch
                                .overlay(Text("1in").font(.fxSystem(size: 10)))
                        }
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Protocol-based API Section
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("2. Protocol-based API")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        // Fixed with qualifiers
                        Text("Fixed with Device Qualifiers")
                            .font(.fxSystem(size: 14))
                            .foregroundColor(.secondary)
                        
                        Rectangle()
                            .fill(Color.orange.opacity(0.3))
                            .frame(
                                width: 100.fixed()
                                    .add(deviceType: .phone, customValue: 80)
                                    .add(deviceType: .tablet, customValue: 120)
                                    .dimension,
                                height: 50.fxpt
                            )
                            .overlay(Text("100.fixed()").font(.fxSystem(size: 10)))
                        
                        // Dynamic with percentage
                        Text("Dynamic Percentage")
                            .font(.fxSystem(size: 14))
                            .foregroundColor(.secondary)
                        
                        Rectangle()
                            .fill(Color.purple.opacity(0.3))
                            .frame(
                                width: 0.5.dynamicPercentage().dimension, // 50% of screen
                                height: 50.fxpt
                            )
                            .overlay(Text("50%").font(.fxSystem(size: 10)))
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Item Count Calculator Section
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("3. Item Count Calculator")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        Text("Available items: \(itemCount)")
                            .font(.fxSystem(size: 14))
                            .foregroundColor(.secondary)
                        
                        Rectangle()
                            .fill(Color.gray.opacity(0.1))
                            .frame(height: 100)
                            .calculateAvailableItemCount(
                                itemSize: 50.fxpt,         // Fixed item size
                                itemPadding: 8.fxpt,       // Fixed padding
                                direction: .lowest,        // Use smallest dimension
                                count: $itemCount          // Binding to update count
                            )
                            .overlay(
                                HStack(spacing: 8.fxpt) {
                                    ForEach(0..<min(itemCount, 5), id: \.self) { _ in
                                        Rectangle()
                                            .fill(Color.blue.opacity(0.5))
                                            .frame(width: 50.fxpt, height: 50.fxpt)
                                    }
                                }
                            )
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Wrapper Functions Section
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("4. Wrapper Functions (Kotlin/Compose Style)")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        // Using wrapper functions
                        fixedDp(100) { dimension in
                            Rectangle()
                                .fill(Color.teal.opacity(0.3))
                                .frame(width: dimension, height: 50.fxpt)
                                .overlay(Text("fixedDp(100)").font(.fxSystem(size: 10)))
                        }
                        
                        dynamicDp(200) { dimension in
                            Rectangle()
                                .fill(Color.cyan.opacity(0.3))
                                .frame(width: dimension, height: 50.fxpt)
                                .overlay(Text("dynamicDp(200)").font(.fxSystem(size: 10)))
                        }
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Advanced Configuration Section
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("5. Advanced Configuration")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        Button(action: {
                            withAnimation {
                                isExpanded.toggle()
                            }
                        }) {
                            Text(isExpanded ? "Hide Details" : "Show Details")
                                .font(.fxSystem(size: 14, weight: .medium))
                                .foregroundColor(.blue)
                        }
                        
                        if isExpanded {
                            VStack(alignment: .leading, spacing: 8.fxpt) {
                                Text("• Environment-based dimension calculation")
                                Text("• Protocol-based API design")
                                Text("• Physical unit conversion (mm, cm, inches)")
                                Text("• Item count calculator for grids")
                                Text("• Wrapper functions for Kotlin/Compose compatibility")
                                Text("• Device-specific qualifiers")
                                Text("• Aspect ratio adjustment")
                            }
                            .font(.fxSystem(size: 12))
                            .foregroundColor(.secondary)
                        }
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    Spacer.fxMinLength(20) // Fixed minimum spacer length
                }
            }
            .navigationTitle("AppDimens Enhanced")
            .navigationBarTitleDisplayMode(.large)
        }
    }
}

@available(iOS 13.0, *)
struct ImprovedSwiftUIExampleView_Previews: PreviewProvider {
    static var previews: some View {
        ImprovedSwiftUIExampleView()
    }
}

// MARK: - App with DimensProvider

@available(iOS 13.0, *)
@main
struct ImprovedAppDimensApp: App {
    var body: some Scene {
        WindowGroup {
            // DimensProvider is ESSENTIAL for the new Environment-based system
            DimensProvider {
                ImprovedSwiftUIExampleView()
            }
        }
    }
}
