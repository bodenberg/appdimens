/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Example: Fluid Scaling in iOS SwiftUI
 *
 * Demonstrates the use of AppDimensFluid for smooth, clamp-like scaling
 * between minimum and maximum values based on screen width.
 */

import SwiftUI
import AppDimens

/// Main example view showing all Fluid scaling examples
@available(iOS 13.0, *)
struct FluidExamplesView: View {
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 16) {
                    BasicFluidExample()
                    FluidWithBreakpointsExample()
                    FluidWithQualifiersExample()
                    ResponsiveCardExample()
                    FluidBuilderInfoExample()
                    ExtensionMethodsExample()
                    FluidViewExtensionsExample()
                }
                .padding()
            }
            .navigationTitle("Fluid Examples")
        }
    }
}

// MARK: - Example 1: Basic Fluid Scaling

@available(iOS 13.0, *)
struct BasicFluidExample: View {
    var body: some View {
        GeometryReader { geometry in
            let fontSize = fluid(min: 16, max: 24)
                .calculate(screenWidth: geometry.size.width)
            
            VStack(alignment: .leading, spacing: 8) {
                Text("Fluid Typography: 16-24")
                    .font(.system(size: fontSize))
                    .fontWeight(.semibold)
                
                Text("Font size smoothly scales between 16 (320pt screen) and 24 (768pt screen)")
                    .font(.caption)
                    .foregroundColor(.gray)
            }
            .padding()
            .background(Color(.systemBackground))
            .cornerRadius(8)
            .shadow(radius: 2)
        }
        .frame(height: 100)
    }
}

// MARK: - Example 2: Fluid with Custom Breakpoints

@available(iOS 13.0, *)
struct FluidWithBreakpointsExample: View {
    var body: some View {
        GeometryReader { geometry in
            let fontSize = fluid(min: 12, max: 20, minWidth: 280, maxWidth: 600)
                .calculate(screenWidth: geometry.size.width)
            
            let padding = fluid(min: 6, max: 12, minWidth: 280, maxWidth: 600)
                .calculate(screenWidth: geometry.size.width)
            
            VStack(alignment: .leading, spacing: 8) {
                Text("Custom Breakpoints")
                    .font(.system(size: fontSize))
                    .fontWeight(.semibold)
                
                Text("Range: 280-600pt (narrower than default 320-768pt)")
                    .font(.caption)
                    .foregroundColor(.gray)
            }
            .padding(padding)
            .background(Color(.systemBackground))
            .cornerRadius(8)
            .shadow(radius: 2)
        }
        .frame(height: 100)
    }
}

// MARK: - Example 3: Fluid with Device Type Qualifiers

@available(iOS 13.0, *)
struct FluidWithQualifiersExample: View {
    var body: some View {
        GeometryReader { geometry in
            let fluidBuilder = AppDimensFluid(minValue: 16, maxValue: 24)
                .device(.tablet, minValue: 20, maxValue: 32)
                .device(.tv, minValue: 24, maxValue: 40)
            
            let fontSize = fluidBuilder.calculate(screenWidth: geometry.size.width)
            
            VStack(alignment: .leading, spacing: 8) {
                Text("Device-Aware Fluid")
                    .font(.system(size: fontSize))
                    .fontWeight(.semibold)
                
                Text("Phones: 16-24 | Tablets: 20-32 | TV: 24-40")
                    .font(.caption)
                    .foregroundColor(.gray)
            }
            .padding()
            .background(Color(.systemBackground))
            .cornerRadius(8)
            .shadow(radius: 2)
        }
        .frame(height: 100)
    }
}

// MARK: - Example 4: Responsive Card

@available(iOS 13.0, *)
struct ResponsiveCardExample: View {
    var body: some View {
        GeometryReader { geometry in
            let titleSize = CGFloat(18).fluidTo(28)
                .calculate(screenWidth: geometry.size.width)
            
            let bodySize = CGFloat(14).fluidTo(18)
                .calculate(screenWidth: geometry.size.width)
            
            let padding = CGFloat(12).fluidTo(20)
                .calculate(screenWidth: geometry.size.width)
            
            let borderRadius = CGFloat(8).fluidTo(16)
                .calculate(screenWidth: geometry.size.width)
            
            VStack(alignment: .leading, spacing: padding / 2) {
                Text("Fluid Card Design")
                    .font(.system(size: titleSize))
                    .fontWeight(.bold)
                
                Text("All dimensions (title, body, padding, radius) scale smoothly using fluid scaling model for optimal responsive behavior.")
                    .font(.system(size: bodySize))
                    .foregroundColor(.gray)
                    .lineSpacing(bodySize * 0.5)
            }
            .padding(padding)
            .background(Color(.systemBackground))
            .cornerRadius(borderRadius)
            .shadow(radius: 2)
        }
        .frame(height: 150)
    }
}

// MARK: - Example 5: Fluid Builder Info

@available(iOS 13.0, *)
struct FluidBuilderInfoExample: View {
    var body: some View {
        GeometryReader { geometry in
            let fluidBuilder = fluid(min: 16, max: 24)
            let fontSize = fluidBuilder.calculate(screenWidth: geometry.size.width)
            
            VStack(alignment: .leading, spacing: 8) {
                Text("Fluid Builder Info")
                    .font(.headline)
                
                Group {
                    Text("Min: \(String(format: "%.0f", fluidBuilder.getMin()))")
                    Text("Max: \(String(format: "%.0f", fluidBuilder.getMax()))")
                    Text("Preferred: \(String(format: "%.0f", fluidBuilder.getPreferred()))")
                    Text("At 25%: \(String(format: "%.1f", fluidBuilder.lerp(0.25)))")
                    Text("At 50%: \(String(format: "%.1f", fluidBuilder.lerp(0.5)))")
                    Text("At 75%: \(String(format: "%.1f", fluidBuilder.lerp(0.75)))")
                }
                .font(.caption)
                .foregroundColor(.gray)
            }
            .padding()
            .background(Color(.systemBackground))
            .cornerRadius(8)
            .shadow(radius: 2)
        }
        .frame(height: 200)
    }
}

// MARK: - Example 6: Extension Methods

@available(iOS 13.0, *)
struct ExtensionMethodsExample: View {
    var body: some View {
        GeometryReader { geometry in
            // Using extension methods
            let fluid = CGFloat(16).fluidTo(24)
            let fontSize = fluid.calculate(screenWidth: geometry.size.width)
            
            VStack(alignment: .leading, spacing: 8) {
                Text("Extension Methods")
                    .font(.system(size: fontSize))
                    .fontWeight(.semibold)
                
                Text("Using CGFloat(16).fluidTo(24) extension")
                    .font(.caption)
                    .foregroundColor(.gray)
                
                Text("Min: \(String(format: "%.0f", fluid.getMin())), Max: \(String(format: "%.0f", fluid.getMax()))")
                    .font(.caption)
                    .foregroundColor(.gray)
            }
            .padding()
            .background(Color(.systemBackground))
            .cornerRadius(8)
            .shadow(radius: 2)
        }
        .frame(height: 120)
    }
}

// MARK: - Example 7: Fluid View Extensions

@available(iOS 13.0, *)
struct FluidViewExtensionsExample: View {
    var body: some View {
        Text("Fluid View Extensions")
            .fontWeight(.semibold)
            .fluidPadding(min: 12, max: 20)
            .background(Color(.systemBackground))
            .cornerRadius(8)
            .shadow(radius: 2)
            .frame(height: 80)
    }
}

// MARK: - Preview

@available(iOS 13.0, *)
struct FluidExamplesView_Previews: PreviewProvider {
    static var previews: some View {
        FluidExamplesView()
    }
}

// MARK: - Usage Examples for Different Scenarios

@available(iOS 13.0, *)
struct FluidUsageExamples {
    
    // Example: Typography Scale
    static func typographyExample(width: CGFloat) -> some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Heading 1")
                .font(.system(size: fluid(min: 28, max: 36).calculate(screenWidth: width)))
                .fontWeight(.bold)
            
            Text("Heading 2")
                .font(.system(size: fluid(min: 24, max: 32).calculate(screenWidth: width)))
                .fontWeight(.semibold)
            
            Text("Body Text")
                .font(.system(size: fluid(min: 16, max: 20).calculate(screenWidth: width)))
            
            Text("Caption")
                .font(.system(size: fluid(min: 12, max: 14).calculate(screenWidth: width)))
                .foregroundColor(.gray)
        }
    }
    
    // Example: Responsive Spacing
    static func spacingExample(width: CGFloat) -> some View {
        let spacing = fluid(min: 8, max: 16).calculate(screenWidth: width)
        let padding = fluid(min: 12, max: 24).calculate(screenWidth: width)
        
        return VStack(spacing: spacing) {
            ForEach(0..<5) { index in
                Text("Item \(index + 1)")
                    .padding(padding)
                    .background(Color.blue.opacity(0.1))
                    .cornerRadius(8)
            }
        }
    }
    
    // Example: Adaptive Grid
    static func gridExample(width: CGFloat) -> some View {
        let itemSize = fluid(min: 80, max: 120).calculate(screenWidth: width)
        let spacing = fluid(min: 8, max: 16).calculate(screenWidth: width)
        
        return LazyVGrid(
            columns: Array(repeating: GridItem(.flexible(), spacing: spacing), count: 3),
            spacing: spacing
        ) {
            ForEach(0..<9) { index in
                RoundedRectangle(cornerRadius: 8)
                    .fill(Color.blue.opacity(0.3))
                    .frame(height: itemSize)
                    .overlay(Text("\(index + 1)"))
            }
        }
    }
}

