/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-11-02
 *
 * Library: AppDimens iOS v2.0.0 - New Scaling Strategies Example
 *
 * Description:
 * Example demonstrating the new 13 scaling strategies introduced in v2.0.0
 */

import SwiftUI
import AppDimens

@available(iOS 13.0, *)
struct NewStrategiesExampleView: View {
    @State private var selectedStrategy: Strategy = .balanced
    
    enum Strategy: String, CaseIterable {
        case balanced = "BALANCED ⭐"
        case percentage = "PERCENTAGE"
        case defaultStrategy = "DEFAULT"
        case logarithmic = "LOGARITHMIC"
        case power = "POWER"
        case fluid = "FLUID"
        case interpolated = "INTERPOLATED"
        case diagonal = "DIAGONAL"
        case perimeter = "PERIMETER"
        case fit = "FIT"
        case fill = "FILL"
        case autosize = "AUTOSIZE"
        case none = "NONE"
        
        var description: String {
            switch self {
            case .balanced:
                return "⭐ RECOMMENDED: Linear on phones, logarithmic on tablets"
            case .percentage:
                return "100% proportional scaling (old Dynamic)"
            case .defaultStrategy:
                return "~97% linear + AR adjustment (old Fixed)"
            case .logarithmic:
                return "Pure logarithmic (Weber-Fechner Law)"
            case .power:
                return "Stevens' Power Law with configurable exponent"
            case .fluid:
                return "CSS clamp-like with min/max boundaries"
            case .interpolated:
                return "50% moderated linear growth"
            case .diagonal:
                return "Scale based on screen diagonal"
            case .perimeter:
                return "Scale based on perimeter (W+H)"
            case .fit:
                return "Letterbox scaling (min ratio) - Game fit"
            case .fill:
                return "Cover scaling (max ratio) - Game fill"
            case .autosize:
                return "Auto-adjust based on component size"
            case .none:
                return "No scaling (constant size)"
            }
        }
    }
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 20) {
                    
                    // Header
                    VStack(spacing: 12) {
                        Text("AppDimens v2.0.0")
                            .font(.system(size: AppDimens.shared.balanced(28).toPoints(), weight: .bold))
                        
                        Text("13 Scaling Strategies")
                            .font(.system(size: AppDimens.shared.balanced(16).toPoints()))
                            .foregroundColor(.secondary)
                    }
                    .padding(AppDimens.shared.balanced(20).toPoints())
                    
                    // Strategy Info Card
                    VStack(alignment: .leading, spacing: 12) {
                        Text("About Strategies")
                            .font(.system(size: AppDimens.shared.balanced(18).toPoints(), weight: .semibold))
                        
                        Text("Version 2.0.0 introduces 13 scaling strategies:")
                            .font(.system(size: AppDimens.shared.balanced(14).toPoints()))
                        
                        VStack(alignment: .leading, spacing: 6) {
                            BulletPoint(text: "BALANCED ⭐ (Recommended): Best for multi-device apps")
                            BulletPoint(text: "PERCENTAGE: Pure proportional (old Dynamic)")
                            BulletPoint(text: "DEFAULT: Legacy fixed (old Fixed)")
                            BulletPoint(text: "LOGARITHMIC: Maximum control on large screens")
                            BulletPoint(text: "POWER: Scientific Stevens' Law")
                            BulletPoint(text: "And 8 more specialized strategies...")
                        }
                        .font(.system(size: AppDimens.shared.balanced(12).toPoints()))
                        .foregroundColor(.secondary)
                    }
                    .padding(AppDimens.shared.balanced(16).toPoints())
                    .background(Color(.systemGray6))
                    .cornerRadius(AppDimens.shared.balanced(12).toPoints())
                    
                    // Strategy Selector
                    VStack(alignment: .leading, spacing: 12) {
                        Text("Select Strategy to Explore")
                            .font(.system(size: AppDimens.shared.balanced(16).toPoints(), weight: .semibold))
                        
                        ScrollView(.horizontal, showsIndicators: false) {
                            HStack(spacing: 8) {
                                ForEach(Strategy.allCases, id: \.self) { strategy in
                                    StrategyChip(
                                        strategy: strategy,
                                        isSelected: selectedStrategy == strategy,
                                        onTap: { selectedStrategy = strategy }
                                    )
                                }
                            }
                        }
                    }
                    .padding(.horizontal, AppDimens.shared.balanced(16).toPoints())
                    
                    // Strategy Demo
                    StrategyDemoCard(strategy: selectedStrategy)
                    
                    // Code Examples
                    CodeExamplesCard(strategy: selectedStrategy)
                    
                    // Smart API Demo
                    SmartAPIDemo()
                    
                    Spacer(minLength: 20)
                }
            }
            .navigationTitle("New Strategies v2.0")
            .navigationBarTitleDisplayMode(.inline)
        }
    }
}

@available(iOS 13.0, *)
struct StrategyChip: View {
    let strategy: NewStrategiesExampleView.Strategy
    let isSelected: Bool
    let onTap: () -> Void
    
    var body: some View {
        Button(action: onTap) {
            Text(strategy.rawValue)
                .font(.system(size: AppDimens.shared.balanced(12).toPoints(), weight: .medium))
                .padding(.horizontal, AppDimens.shared.balanced(12).toPoints())
                .padding(.vertical, AppDimens.shared.balanced(8).toPoints())
                .background(isSelected ? Color.blue : Color(.systemGray5))
                .foregroundColor(isSelected ? .white : .primary)
                .cornerRadius(AppDimens.shared.balanced(8).toPoints())
        }
    }
}

@available(iOS 13.0, *)
struct StrategyDemoCard: View {
    let strategy: NewStrategiesExampleView.Strategy
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text(strategy.rawValue)
                .font(.system(size: AppDimens.shared.balanced(18).toPoints(), weight: .semibold))
            
            Text(strategy.description)
                .font(.system(size: AppDimens.shared.balanced(14).toPoints()))
                .foregroundColor(.secondary)
            
            Divider()
            
            Text("Visual Comparison")
                .font(.system(size: AppDimens.shared.balanced(14).toPoints(), weight: .medium))
            
            HStack(spacing: 12) {
                DemoBox(size: getStrategySize(48, strategy: strategy), label: "48pt")
                DemoBox(size: getStrategySize(64, strategy: strategy), label: "64pt")
                DemoBox(size: getStrategySize(96, strategy: strategy), label: "96pt")
            }
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
        .background(Color(.systemBackground))
        .cornerRadius(AppDimens.shared.balanced(12).toPoints())
        .shadow(color: .black.opacity(0.1), radius: 4, x: 0, y: 2)
        .padding(.horizontal, AppDimens.shared.balanced(16).toPoints())
    }
    
    private func getStrategySize(_ base: CGFloat, strategy: NewStrategiesExampleView.Strategy) -> CGFloat {
        switch strategy {
        case .balanced:
            return AppDimens.shared.balanced(base).toPoints()
        case .percentage:
            return AppDimens.shared.percentage(base).toPoints()
        case .defaultStrategy:
            return AppDimens.shared.default(base).toPoints()
        case .logarithmic:
            return AppDimens.shared.logarithmic(base).toPoints()
        case .power:
            return AppDimens.shared.power(base, exponent: 0.75).toPoints()
        case .fluid:
            return AppDimens.shared.fluid(min: base * 0.8, max: base * 1.2).toPoints()
        case .interpolated:
            return AppDimens.shared.interpolated(base).toPoints()
        case .diagonal:
            return AppDimens.shared.diagonal(base).toPoints()
        case .perimeter:
            return AppDimens.shared.perimeter(base).toPoints()
        case .fit:
            return AppDimens.shared.fit(base).toPoints()
        case .fill:
            return AppDimens.shared.fill(base).toPoints()
        case .autosize:
            return AppDimens.shared.autosize(base).toPoints()
        case .none:
            return base
        }
    }
}

@available(iOS 13.0, *)
struct DemoBox: View {
    let size: CGFloat
    let label: String
    
    var body: some View {
        VStack(spacing: 4) {
            RoundedRectangle(cornerRadius: 8)
                .fill(Color.blue.opacity(0.3))
                .frame(width: size, height: size)
            
            Text(label)
                .font(.system(size: 10))
                .foregroundColor(.secondary)
            
            Text("\(Int(size))pt")
                .font(.system(size: 10, weight: .bold))
                .foregroundColor(.primary)
        }
    }
}

@available(iOS 13.0, *)
struct CodeExamplesCard: View {
    let strategy: NewStrategiesExampleView.Strategy
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Code Examples")
                .font(.system(size: AppDimens.shared.balanced(16).toPoints(), weight: .semibold))
            
            VStack(alignment: .leading, spacing: 8) {
                Text("SwiftUI:")
                    .font(.system(size: AppDimens.shared.balanced(12).toPoints(), weight: .medium))
                
                CodeBlock(code: getSwiftUIExample(strategy))
                
                Text("UIKit:")
                    .font(.system(size: AppDimens.shared.balanced(12).toPoints(), weight: .medium))
                    .padding(.top, 8)
                
                CodeBlock(code: getUIKitExample(strategy))
            }
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
        .background(Color(.systemGray6))
        .cornerRadius(AppDimens.shared.balanced(12).toPoints())
        .padding(.horizontal, AppDimens.shared.balanced(16).toPoints())
    }
    
    private func getSwiftUIExample(_ strategy: NewStrategiesExampleView.Strategy) -> String {
        switch strategy {
        case .balanced:
            return ".frame(width: AppDimens.shared.balanced(300).toPoints())"
        case .percentage:
            return ".frame(width: AppDimens.shared.percentage(300).toPoints())"
        case .defaultStrategy:
            return ".frame(width: AppDimens.shared.default(300).toPoints())"
        case .logarithmic:
            return ".frame(width: AppDimens.shared.logarithmic(300).toPoints())"
        case .power:
            return ".frame(width: AppDimens.shared.power(300, exponent: 0.75).toPoints())"
        case .fluid:
            return ".frame(width: AppDimens.shared.fluid(min: 240, max: 360).toPoints())"
        case .interpolated:
            return ".frame(width: AppDimens.shared.interpolated(300).toPoints())"
        case .diagonal:
            return ".frame(width: AppDimens.shared.diagonal(300).toPoints())"
        case .perimeter:
            return ".frame(width: AppDimens.shared.perimeter(300).toPoints())"
        case .fit:
            return ".frame(width: AppDimens.shared.fit(300).toPoints())"
        case .fill:
            return ".frame(width: AppDimens.shared.fill(300).toPoints())"
        case .autosize:
            return ".frame(width: AppDimens.shared.autosize(300).toPoints())"
        case .none:
            return ".frame(width: 300)"
        }
    }
    
    private func getUIKitExample(_ strategy: NewStrategiesExampleView.Strategy) -> String {
        switch strategy {
        case .balanced:
            return "view.frame.size.width = AppDimens.shared.balanced(300).toPoints()"
        case .percentage:
            return "view.frame.size.width = AppDimens.shared.percentage(300).toPoints()"
        case .defaultStrategy:
            return "view.frame.size.width = AppDimens.shared.default(300).toPoints()"
        case .logarithmic:
            return "view.frame.size.width = AppDimens.shared.logarithmic(300).toPoints()"
        case .power:
            return "view.frame.size.width = AppDimens.shared.power(300, exponent: 0.75).toPoints()"
        case .fluid:
            return "view.frame.size.width = AppDimens.shared.fluid(min: 240, max: 360).toPoints()"
        case .interpolated:
            return "view.frame.size.width = AppDimens.shared.interpolated(300).toPoints()"
        case .diagonal:
            return "view.frame.size.width = AppDimens.shared.diagonal(300).toPoints()"
        case .perimeter:
            return "view.frame.size.width = AppDimens.shared.perimeter(300).toPoints()"
        case .fit:
            return "view.frame.size.width = AppDimens.shared.fit(300).toPoints()"
        case .fill:
            return "view.frame.size.width = AppDimens.shared.fill(300).toPoints()"
        case .autosize:
            return "view.frame.size.width = AppDimens.shared.autosize(300).toPoints()"
        case .none:
            return "view.frame.size.width = 300"
        }
    }
}

@available(iOS 13.0, *)
struct SmartAPIDemo: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("🧠 Smart API (Auto-Inference)")
                .font(.system(size: AppDimens.shared.balanced(18).toPoints(), weight: .bold))
            
            Text("The Smart API automatically selects the best strategy based on the element type:")
                .font(.system(size: AppDimens.shared.balanced(14).toPoints()))
                .foregroundColor(.secondary)
            
            Divider()
            
            SmartAPIExample(
                elementType: "Button",
                code: ".smart().forElement(.button).toPoints()",
                recommendedStrategy: "BALANCED"
            )
            
            SmartAPIExample(
                elementType: "Container",
                code: ".smart().forElement(.container).toPoints()",
                recommendedStrategy: "PERCENTAGE"
            )
            
            SmartAPIExample(
                elementType: "Text",
                code: ".smart().forElement(.text).toPoints()",
                recommendedStrategy: "BALANCED"
            )
            
            SmartAPIExample(
                elementType: "Icon",
                code: ".smart().forElement(.icon).toPoints()",
                recommendedStrategy: "DEFAULT"
            )
            
            Text("Element Types: BUTTON, TEXT, ICON, CONTAINER, SPACING, CARD, DIALOG, TOOLBAR, FAB, CHIP, LIST_ITEM, IMAGE, BADGE, DIVIDER, NAVIGATION, INPUT, HEADER")
                .font(.system(size: AppDimens.shared.balanced(10).toPoints()))
                .foregroundColor(.secondary)
                .padding(.top, 8)
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
        .background(Color(.systemBackground))
        .cornerRadius(AppDimens.shared.balanced(12).toPoints())
        .shadow(color: .black.opacity(0.1), radius: 4, x: 0, y: 2)
        .padding(.horizontal, AppDimens.shared.balanced(16).toPoints())
    }
}

@available(iOS 13.0, *)
struct SmartAPIExample: View {
    let elementType: String
    let code: String
    let recommendedStrategy: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            HStack {
                Text(elementType)
                    .font(.system(size: AppDimens.shared.balanced(14).toPoints(), weight: .medium))
                Spacer()
                Text("→ \(recommendedStrategy)")
                    .font(.system(size: AppDimens.shared.balanced(12).toPoints()))
                    .foregroundColor(.blue)
            }
            CodeBlock(code: code, fontSize: 10)
        }
    }
}

@available(iOS 13.0, *)
struct CodeBlock: View {
    let code: String
    var fontSize: CGFloat = 11
    
    var body: some View {
        Text(code)
            .font(.system(size: fontSize, design: .monospaced))
            .padding(8)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(Color(.systemGray5))
            .cornerRadius(6)
    }
}

@available(iOS 13.0, *)
struct BulletPoint: View {
    let text: String
    
    var body: some View {
        HStack(alignment: .top, spacing: 6) {
            Text("•")
            Text(text)
            Spacer()
        }
    }
}

@available(iOS 13.0, *)
struct NewStrategiesExampleView_Previews: PreviewProvider {
    static var previews: some View {
        NewStrategiesExampleView()
    }
}

