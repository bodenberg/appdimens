/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Environment System Example
 *
 * Description:
 * Example showing how to use AppDimens with the Environment system, protocols, and new features.
 */

import SwiftUI
import AppDimens

@available(iOS 13.0, *)
struct EnvironmentSystemExampleView: View {
    @State private var itemCount: Int = 0
    @State private var isExpanded = false
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass
    @Environment(\.verticalSizeClass) private var verticalSizeClass
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 20.fxpt) {
                    
                    // Header section
                    VStack(spacing: 16.fxpt) {
                        Text("Environment System Example")
                            .font(.fxSystem(size: 24, weight: .bold))
                            .multilineTextAlignment(.center)
                        
                        Text("Environment-based dimension calculation with protocols and new features")
                            .font(.fxSystem(size: 16))
                            .foregroundColor(.secondary)
                    }
                    .fxPadding(20)
                    
                    // Environment Information
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Environment Information")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        VStack(alignment: .leading, spacing: 8.fxpt) {
                            Text("Horizontal Size Class: \(horizontalSizeClass == .compact ? "Compact" : "Regular")")
                            Text("Vertical Size Class: \(verticalSizeClass == .compact ? "Compact" : "Regular")")
                            Text("Screen Width: \(Int(UIScreen.main.bounds.width)) points")
                            Text("Screen Height: \(Int(UIScreen.main.bounds.height)) points")
                            Text("Scale Factor: \(UIScreen.main.scale)")
                        }
                        .font(.fxSystem(size: 14))
                        .foregroundColor(.secondary)
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Protocol-based API Section
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Protocol-based API")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        Text("Device-specific qualifiers with protocol-based design")
                            .font(.fxSystem(size: 14))
                            .foregroundColor(.secondary)
                        
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
                        Text("Item Count Calculator")
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
                        Text("Wrapper Functions (Kotlin/Compose Style)")
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
                    
                    // Environment-aware Dimensions
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Environment-aware Dimensions")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        let envAwareDp = environmentAwareDimension()
                        
                        Text("Current environment dimension: \(Int(envAwareDp))dp")
                            .font(.fxSystem(size: 14))
                            .foregroundColor(.secondary)
                        
                        Rectangle()
                            .fill(Color.green.opacity(0.3))
                            .frame(width: envAwareDp, height: 50.fxpt)
                            .overlay(Text("Env-aware").font(.fxSystem(size: 10)))
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Advanced Configuration Section
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Advanced Configuration")
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
                                Text("• Device-specific qualifiers")
                                Text("• Item count calculator for grids")
                                Text("• Wrapper functions for compatibility")
                                Text("• Aspect ratio adjustment")
                                Text("• Dynamic percentage calculations")
                            }
                            .font(.fxSystem(size: 12))
                            .foregroundColor(.secondary)
                        }
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    Spacer.fxMinLength(20)
                }
            }
            .navigationTitle("Environment System")
            .navigationBarTitleDisplayMode(.large)
        }
    }
    
    private func environmentAwareDimension() -> CGFloat {
        switch horizontalSizeClass {
        case .compact:
            return 60.fxpt
        case .regular:
            return 80.fxpt
        default:
            return 70.fxpt
        }
    }
}

// MARK: - App with DimensProvider

@available(iOS 13.0, *)
@main
struct EnvironmentSystemApp: App {
    var body: some Scene {
        WindowGroup {
            // DimensProvider is ESSENTIAL for the new Environment-based system
            DimensProvider {
                EnvironmentSystemExampleView()
            }
        }
    }
}

@available(iOS 13.0, *)
struct EnvironmentSystemExampleView_Previews: PreviewProvider {
    static var previews: some View {
        EnvironmentSystemExampleView()
    }
}
