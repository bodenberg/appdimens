/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Physical Units Example
 *
 * Description:
 * Example showing how to use AppDimens with physical units (mm, cm, inches).
 */

import SwiftUI
import AppDimens

@available(iOS 13.0, *)
struct PhysicalUnitsExampleView: View {
    @State private var selectedUnit: PhysicalUnit = .millimeter
    @State private var unitValue: Double = 10.0
    
    enum PhysicalUnit: String, CaseIterable {
        case millimeter = "mm"
        case centimeter = "cm"
        case inch = "in"
        
        var displayName: String {
            switch self {
            case .millimeter: return "Millimeter"
            case .centimeter: return "Centimeter"
            case .inch: return "Inch"
            }
        }
    }
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 20.fxpt) {
                    
                    // Header section
                    VStack(spacing: 16.fxpt) {
                        Text("Physical Units Example")
                            .font(.fxSystem(size: 24, weight: .bold))
                            .multilineTextAlignment(.center)
                        
                        Text("Convert physical units to screen pixels")
                            .font(.fxSystem(size: 16))
                            .foregroundColor(.secondary)
                    }
                    .fxPadding(20)
                    
                    // Unit selector
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Select Physical Unit")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        Picker("Unit", selection: $selectedUnit) {
                            ForEach(PhysicalUnit.allCases, id: \.self) { unit in
                                Text(unit.displayName).tag(unit)
                            }
                        }
                        .pickerStyle(SegmentedPickerStyle())
                        
                        VStack(alignment: .leading, spacing: 8.fxpt) {
                            Text("Value: \(unitValue, specifier: "%.1f") \(selectedUnit.rawValue)")
                            Slider(value: $unitValue, in: 0.1...50, step: 0.1)
                        }
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Conversion display
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Conversion Results")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        let pixels = convertToPixels(value: unitValue, unit: selectedUnit)
                        let dp = convertToDp(value: unitValue, unit: selectedUnit)
                        
                        Text("\(unitValue, specifier: "%.1f") \(selectedUnit.rawValue) = \(Int(pixels)) pixels")
                            .font(.fxSystem(size: 14))
                        
                        Text("\(unitValue, specifier: "%.1f") \(selectedUnit.rawValue) = \(Int(dp)) dp")
                            .font(.fxSystem(size: 14))
                        
                        // Visual representation
                        Rectangle()
                            .fill(Color.blue.opacity(0.3))
                            .frame(width: CGFloat(pixels), height: 20.fxpt)
                            .overlay(
                                Text("\(Int(pixels))px")
                                    .font(.fxSystem(size: 12))
                                    .foregroundColor(.blue)
                            )
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Common measurements
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Common Measurements")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        VStack(alignment: .leading, spacing: 8.fxpt) {
                            MeasurementRow(
                                name: "Touch Target (Minimum)",
                                value: 7.0,
                                unit: .millimeter,
                                description: "Recommended minimum touch target size"
                            )
                            
                            MeasurementRow(
                                name: "Button Height",
                                value: 44.0,
                                unit: .millimeter,
                                description: "Standard iOS button height"
                            )
                            
                            MeasurementRow(
                                name: "Card Width",
                                value: 8.5,
                                unit: .inch,
                                description: "Standard business card width"
                            )
                            
                            MeasurementRow(
                                name: "Screen Diagonal",
                                value: 6.1,
                                unit: .inch,
                                description: "iPhone 14 screen diagonal"
                            )
                        }
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Unit comparison
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Unit Comparison")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        let comparisonValue: Double = 10.0
                        
                        VStack(alignment: .leading, spacing: 4.fxpt) {
                            ComparisonRow(
                                value: comparisonValue,
                                unit: .millimeter,
                                pixels: convertToPixels(value: comparisonValue, unit: .millimeter)
                            )
                            ComparisonRow(
                                value: comparisonValue,
                                unit: .centimeter,
                                pixels: convertToPixels(value: comparisonValue, unit: .centimeter)
                            )
                            ComparisonRow(
                                value: comparisonValue,
                                unit: .inch,
                                pixels: convertToPixels(value: comparisonValue, unit: .inch)
                            )
                        }
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Real-world applications
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Real-World Applications")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        VStack(alignment: .leading, spacing: 8.fxpt) {
                            Text("• Accessibility guidelines (minimum 44pt ≈ 7mm)")
                            Text("• Print layouts and measurements")
                            Text("• Cross-platform consistency")
                            Text("• Physical device specifications")
                            Text("• Design system standards")
                        }
                        .font(.fxSystem(size: 14))
                        .foregroundColor(.secondary)
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    Spacer.fxMinLength(20)
                }
            }
            .navigationTitle("Physical Units")
            .navigationBarTitleDisplayMode(.large)
        }
    }
    
    private func convertToPixels(value: Double, unit: PhysicalUnit) -> Double {
        switch unit {
        case .millimeter:
            return value * (UIScreen.main.bounds.width / (UIScreen.main.bounds.width / 25.4 * 10)) // Approximate mm to pixels
        case .centimeter:
            return value * (UIScreen.main.bounds.width / (UIScreen.main.bounds.width / 2.54 * 10)) // Approximate cm to pixels
        case .inch:
            return value * UIScreen.main.scale * 96 // 96 DPI standard
        }
    }
    
    private func convertToDp(value: Double, unit: PhysicalUnit) -> Double {
        let pixels = convertToPixels(value: value, unit: unit)
        return pixels / UIScreen.main.scale
    }
}

@available(iOS 13.0, *)
struct MeasurementRow: View {
    let name: String
    let value: Double
    let unit: PhysicalUnitsExampleView.PhysicalUnit
    let description: String
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 2.fxpt) {
                Text(name)
                    .font(.fxSystem(size: 14, weight: .medium))
                Text(description)
                    .font(.fxSystem(size: 12))
                    .foregroundColor(.secondary)
            }
            
            Spacer()
            
            Text("\(value, specifier: "%.1f") \(unit.rawValue)")
                .font(.fxSystem(size: 14, weight: .medium))
                .foregroundColor(.blue)
        }
        .fxPadding(.vertical, 4)
    }
}

@available(iOS 13.0, *)
struct ComparisonRow: View {
    let value: Double
    let unit: PhysicalUnitsExampleView.PhysicalUnit
    let pixels: Double
    
    var body: some View {
        HStack {
            Text("\(value, specifier: "%.1f") \(unit.rawValue)")
                .font(.fxSystem(size: 14))
            
            Spacer()
            
            Text("= \(Int(pixels)) pixels")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
        }
    }
}

@available(iOS 13.0, *)
struct PhysicalUnitsExampleView_Previews: PreviewProvider {
    static var previews: some View {
        PhysicalUnitsExampleView()
    }
}
