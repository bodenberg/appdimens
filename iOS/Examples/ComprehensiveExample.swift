/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Comprehensive Example
 *
 * Description:
 * Comprehensive example showing all AppDimens features including Fixed, Dynamic, Games, Physical Units, and Environment System.
 */

import SwiftUI
import AppDimens

@available(iOS 13.0, *)
struct ComprehensiveExampleView: View {
    @State private var selectedFeature: AppDimensFeature = .fixed
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass
    @Environment(\.verticalSizeClass) private var verticalSizeClass
    
    enum AppDimensFeature: String, CaseIterable {
        case fixed = "Fixed"
        case dynamic = "Dynamic"
        case games = "Games"
        case physicalUnits = "Physical Units"
        case environmentSystem = "Environment System"
        case protocols = "Protocols"
        case advanced = "Advanced"
        
        var description: String {
            switch self {
            case .fixed:
                return "Logarithmic scaling for UI elements"
            case .dynamic:
                return "Proportional scaling for containers"
            case .games:
                return "Game-specific dimension calculations with Metal"
            case .physicalUnits:
                return "Convert mm, cm, inches to screen pixels"
            case .environmentSystem:
                return "Environment-based responsive design"
            case .protocols:
                return "Protocol-based API design"
            case .advanced:
                return "Advanced features and utilities"
            }
        }
        
        var icon: String {
            switch self {
            case .fixed:
                return "square.grid.3x3"
            case .dynamic:
                return "rectangle.3.offgrid"
            case .games:
                return "gamecontroller"
            case .physicalUnits:
                return "ruler"
            case .environmentSystem:
                return "iphone"
            case .protocols:
                return "link"
            case .advanced:
                return "gearshape"
            }
        }
    }
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 20.fxpt) {
                    
                    // Header Section
                    VStack(spacing: 16.fxpt) {
                        Text("AppDimens iOS")
                            .font(.fxSystem(size: 32, weight: .bold))
                            .multilineTextAlignment(.center)
                        
                        Text("Complete responsive dimension management system")
                            .font(.fxSystem(size: 16))
                            .foregroundColor(.secondary)
                            .multilineTextAlignment(.center)
                    }
                    .fxPadding(20)
                    
                    // Library Info
                    VStack(spacing: 12.fxpt) {
                        HStack(spacing: 20.fxpt) {
                            InfoChip(title: "Version", value: "1.0.5")
                            InfoChip(title: "Modules", value: "3")
                            InfoChip(title: "Features", value: "15+")
                        }
                        
                        Text("Available on iOS 13.0+ with SwiftUI and UIKit support")
                            .font(.fxSystem(size: 12))
                            .foregroundColor(.secondary)
                    }
                    .fxPadding(16)
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12)
                    
                    // Feature Selection
                    VStack(alignment: .leading, spacing: 16.fxpt) {
                        Text("Select Feature to Explore")
                            .font(.fxSystem(size: 20, weight: .bold))
                        
                        LazyVGrid(columns: [
                            GridItem(.flexible()),
                            GridItem(.flexible())
                        ], spacing: 12.fxpt) {
                            ForEach(AppDimensFeature.allCases, id: \.self) { feature in
                                FeatureCard(
                                    feature: feature,
                                    isSelected: selectedFeature == feature,
                                    onTap: { selectedFeature = feature }
                                )
                            }
                        }
                    }
                    
                    // Feature Demo
                    VStack(alignment: .leading, spacing: 16.fxpt) {
                        Text("Feature Demo: \(selectedFeature.rawValue)")
                            .font(.fxSystem(size: 20, weight: .bold))
                        
                        FeatureDemoView(feature: selectedFeature)
                    }
                    
                    // Quick Examples
                    VStack(alignment: .leading, spacing: 16.fxpt) {
                        Text("Quick Examples")
                            .font(.fxSystem(size: 20, weight: .bold))
                        
                        VStack(spacing: 12.fxpt) {
                            QuickExampleCard(
                                title: "Fixed Dimensions",
                                description: "Logarithmic scaling for consistent UI elements",
                                example: "16.fxpt"
                            )
                            
                            QuickExampleCard(
                                title: "Dynamic Dimensions",
                                description: "Proportional scaling for responsive layouts",
                                example: "0.8.dypt"
                            )
                            
                            QuickExampleCard(
                                title: "Physical Units",
                                description: "Real-world measurements",
                                example: "2.cm"
                            )
                        }
                    }
                    
                    // Environment Information
                    VStack(alignment: .leading, spacing: 12.fxpt) {
                        Text("Current Environment")
                            .font(.fxSystem(size: 18, weight: .semibold))
                        
                        VStack(alignment: .leading, spacing: 8.fxpt) {
                            Text("Device: \(UIDevice.current.model)")
                            Text("Size Class: \(horizontalSizeClass == .compact ? "Compact" : "Regular") × \(verticalSizeClass == .compact ? "Compact" : "Regular")")
                            Text("Screen: \(Int(UIScreen.main.bounds.width))×\(Int(UIScreen.main.bounds.height))")
                            Text("Scale: \(UIScreen.main.scale)")
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
            .navigationTitle("AppDimens")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

@available(iOS 13.0, *)
struct FeatureCard: View {
    let feature: ComprehensiveExampleView.AppDimensFeature
    let isSelected: Bool
    let onTap: () -> Void
    
    var body: some View {
        Button(action: onTap) {
            VStack(spacing: 8.fxpt) {
                Image(systemName: feature.icon)
                    .font(.fxSystem(size: 24))
                    .foregroundColor(isSelected ? .white : .blue)
                
                Text(feature.rawValue)
                    .font(.fxSystem(size: 14, weight: .semibold))
                    .foregroundColor(isSelected ? .white : .primary)
                
                Text(feature.description)
                    .font(.fxSystem(size: 10))
                    .foregroundColor(isSelected ? .white.opacity(0.8) : .secondary)
                    .multilineTextAlignment(.center)
            }
            .fxPadding(12)
            .frame(maxWidth: .infinity)
            .background(
                RoundedRectangle(cornerRadius: 12.fxpt)
                    .fill(isSelected ? Color.blue : Color(.systemGray6))
            )
        }
        .buttonStyle(PlainButtonStyle())
    }
}

@available(iOS 13.0, *)
struct FeatureDemoView: View {
    let feature: ComprehensiveExampleView.AppDimensFeature
    
    var body: some View {
        VStack(spacing: 12.fxpt) {
            switch feature {
            case .fixed:
                FixedDemoView()
            case .dynamic:
                DynamicDemoView()
            case .games:
                GamesDemoView()
            case .physicalUnits:
                PhysicalUnitsDemoView()
            case .environmentSystem:
                EnvironmentDemoView()
            case .protocols:
                ProtocolsDemoView()
            case .advanced:
                AdvancedDemoView()
            }
        }
        .fxPadding(16)
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}

@available(iOS 13.0, *)
struct FixedDemoView: View {
    var body: some View {
        VStack(spacing: 12.fxpt) {
            Text("Fixed Dimensions Demo")
                .font(.fxSystem(size: 16, weight: .semibold))
            
            HStack(spacing: 16.fxpt) {
                Rectangle()
                    .fill(Color.blue.opacity(0.3))
                    .frame(width: 50.fxpt, height: 50.fxpt)
                    .overlay(Text("50.fxpt").font(.fxSystem(size: 10)))
                
                Rectangle()
                    .fill(Color.green.opacity(0.3))
                    .frame(width: 80.fxpt, height: 80.fxpt)
                    .overlay(Text("80.fxpt").font(.fxSystem(size: 10)))
                
                Rectangle()
                    .fill(Color.orange.opacity(0.3))
                    .frame(width: 120.fxpt, height: 120.fxpt)
                    .overlay(Text("120.fxpt").font(.fxSystem(size: 10)))
            }
        }
    }
}

@available(iOS 13.0, *)
struct DynamicDemoView: View {
    var body: some View {
        VStack(spacing: 12.fxpt) {
            Text("Dynamic Dimensions Demo")
                .font(.fxSystem(size: 16, weight: .semibold))
            
            Rectangle()
                .fill(Color.purple.opacity(0.3))
                .frame(width: 0.8.dypt, height: 60.fxpt)
                .overlay(Text("80% width").font(.fxSystem(size: 12)))
        }
    }
}

@available(iOS 13.0, *)
struct GamesDemoView: View {
    var body: some View {
        VStack(spacing: 12.fxpt) {
            Text("Games Module Demo")
                .font(.fxSystem(size: 16, weight: .semibold))
            
            HStack(spacing: 16.fxpt) {
                Circle()
                    .fill(Color.red.opacity(0.5))
                    .frame(width: 40.fxpt, height: 40.fxpt)
                    .overlay(Text("Player").font(.fxSystem(size: 8)))
                
                Circle()
                    .fill(Color.yellow.opacity(0.5))
                    .frame(width: 30.fxpt, height: 30.fxpt)
                    .overlay(Text("Enemy").font(.fxSystem(size: 8)))
                
                Circle()
                    .fill(Color.green.opacity(0.5))
                    .frame(width: 20.fxpt, height: 20.fxpt)
                    .overlay(Text("Power").font(.fxSystem(size: 8)))
            }
        }
    }
}

@available(iOS 13.0, *)
struct PhysicalUnitsDemoView: View {
    var body: some View {
        VStack(spacing: 12.fxpt) {
            Text("Physical Units Demo")
                .font(.fxSystem(size: 16, weight: .semibold))
            
            HStack(spacing: 16.fxpt) {
                Rectangle()
                    .fill(Color.cyan.opacity(0.3))
                    .frame(width: 2.cm, height: 2.cm)
                    .overlay(Text("2cm").font(.fxSystem(size: 10)))
                
                Rectangle()
                    .fill(Color.mint.opacity(0.3))
                    .frame(width: 5.mm, height: 5.mm)
                    .overlay(Text("5mm").font(.fxSystem(size: 8)))
                
                Rectangle()
                    .fill(Color.teal.opacity(0.3))
                    .frame(width: 1.inch, height: 1.inch)
                    .overlay(Text("1in").font(.fxSystem(size: 10)))
            }
        }
    }
}

@available(iOS 13.0, *)
struct EnvironmentDemoView: View {
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass
    
    var body: some View {
        VStack(spacing: 12.fxpt) {
            Text("Environment System Demo")
                .font(.fxSystem(size: 16, weight: .semibold))
            
            Text("Size Class: \(horizontalSizeClass == .compact ? "Compact" : "Regular")")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
            
            Rectangle()
                .fill(Color.indigo.opacity(0.3))
                .frame(
                    width: horizontalSizeClass == .compact ? 100.fxpt : 150.fxpt,
                    height: 60.fxpt
                )
                .overlay(Text("Responsive").font(.fxSystem(size: 12)))
        }
    }
}

@available(iOS 13.0, *)
struct ProtocolsDemoView: View {
    var body: some View {
        VStack(spacing: 12.fxpt) {
            Text("Protocol-based API Demo")
                .font(.fxSystem(size: 16, weight: .semibold))
            
            Rectangle()
                .fill(Color.pink.opacity(0.3))
                .frame(
                    width: 100.fixed()
                        .add(deviceType: .phone, customValue: 80)
                        .add(deviceType: .tablet, customValue: 120)
                        .dimension,
                    height: 50.fxpt
                )
                .overlay(Text("Protocol").font(.fxSystem(size: 10)))
        }
    }
}

@available(iOS 13.0, *)
struct AdvancedDemoView: View {
    var body: some View {
        VStack(spacing: 12.fxpt) {
            Text("Advanced Features Demo")
                .font(.fxSystem(size: 16, weight: .semibold))
            
            Rectangle()
                .fill(Color.gray.opacity(0.3))
                .frame(width: 120.fxpt, height: 80.fxpt)
                .overlay(Text("Advanced\nFeatures").font(.fxSystem(size: 10)))
        }
    }
}

@available(iOS 13.0, *)
struct QuickExampleCard: View {
    let title: String
    let description: String
    let example: String
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4.fxpt) {
                Text(title)
                    .font(.fxSystem(size: 14, weight: .semibold))
                Text(description)
                    .font(.fxSystem(size: 12))
                    .foregroundColor(.secondary)
            }
            
            Spacer()
            
            Text(example)
                .font(.fxSystem(size: 12, weight: .medium))
                .foregroundColor(.blue)
                .fxPadding(.horizontal, 8)
                .fxPadding(.vertical, 4)
                .background(Color.blue.opacity(0.1))
                .fxCornerRadius(4)
        }
        .fxPadding(12)
        .background(Color(.systemGray6))
        .fxCornerRadius(8)
    }
}

@available(iOS 13.0, *)
struct InfoChip: View {
    let title: String
    let value: String
    
    var body: some View {
        VStack(spacing: 2.fxpt) {
            Text(value)
                .font(.fxSystem(size: 16, weight: .bold))
            Text(title)
                .font(.fxSystem(size: 10))
                .foregroundColor(.secondary)
        }
        .fxPadding(.horizontal, 12)
        .fxPadding(.vertical, 6)
        .background(Color(.systemGray5))
        .fxCornerRadius(8)
    }
}

@available(iOS 13.0, *)
@main
struct ComprehensiveExampleApp: App {
    var body: some Scene {
        WindowGroup {
            DimensProvider {
                ComprehensiveExampleView()
            }
        }
    }
}

@available(iOS 13.0, *)
struct ComprehensiveExampleView_Previews: PreviewProvider {
    static var previews: some View {
        ComprehensiveExampleView()
    }
}
