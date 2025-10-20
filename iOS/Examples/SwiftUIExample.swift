/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - SwiftUI Example
 *
 * Description:
 * Example showing how to use AppDimens with SwiftUI.
 */

import SwiftUI
import AppDimens

@available(iOS 13.0, *)
struct SwiftUIExampleView: View {
    @State private var isExpanded = false
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(spacing: 20.fxpt) { // Fixed spacing
                    
                    // Header section
                    VStack(spacing: 16.fxpt) { // Fixed spacing
                        Text("AppDimens SwiftUI Example")
                            .font(.fxSystem(size: 24, weight: .bold)) // Fixed font size
                            .multilineTextAlignment(.center)
                        
                        Text("Responsive layouts made easy")
                            .font(.fxSystem(size: 16)) // Fixed font size
                            .foregroundColor(.secondary)
                    }
                    .fxPadding(20) // Fixed padding
                    
                    // Card with dynamic width
                    VStack(spacing: 16.fxpt) { // Fixed spacing
                        HStack {
                            Image(systemName: "star.fill")
                                .foregroundColor(.yellow)
                                .font(.fxSystem(size: 20)) // Fixed font size
                            
                            Text("Featured Content")
                                .font(.fxSystem(size: 18, weight: .semibold)) // Fixed font size
                            
                            Spacer()
                        }
                        
                        Text("This card demonstrates how AppDimens automatically adjusts dimensions based on screen size while maintaining visual consistency.")
                            .font(.fxSystem(size: 14)) // Fixed font size
                            .foregroundColor(.secondary)
                            .multilineTextAlignment(.leading)
                        
                        Button(action: {
                            withAnimation {
                                isExpanded.toggle()
                            }
                        }) {
                            Text(isExpanded ? "Show Less" : "Show More")
                                .font(.fxSystem(size: 14, weight: .medium)) // Fixed font size
                                .foregroundColor(.blue)
                        }
                        
                        if isExpanded {
                            VStack(alignment: .leading, spacing: 8.fxpt) { // Fixed spacing
                                Text("Additional Information:")
                                    .font(.fxSystem(size: 16, weight: .semibold)) // Fixed font size
                                
                                Text("• Fixed dimensions scale logarithmically")
                                Text("• Dynamic dimensions scale proportionally")
                                Text("• Perfect for both UIKit and SwiftUI")
                                Text("• Supports all iOS device types")
                            }
                            .font(.fxSystem(size: 14)) // Fixed font size
                            .foregroundColor(.secondary)
                        }
                    }
                    .fxPadding(20) // Fixed padding
                    .background(Color(.systemGray6))
                    .fxCornerRadius(12) // Fixed corner radius
                    .dyFrame(width: 350) // Dynamic width
                    
                    // Grid of items
                    LazyVGrid(columns: [
                        GridItem(.flexible(), spacing: 16.fxpt), // Fixed spacing
                        GridItem(.flexible(), spacing: 16.fxpt)  // Fixed spacing
                    ], spacing: 16.fxpt) { // Fixed spacing
                        
                        ForEach(0..<6) { index in
                            VStack(spacing: 8.fxpt) { // Fixed spacing
                                Image(systemName: "heart.fill")
                                    .font(.fxSystem(size: 24)) // Fixed font size
                                    .foregroundColor(.red)
                                
                                Text("Item \(index + 1)")
                                    .font(.fxSystem(size: 12)) // Fixed font size
                                    .foregroundColor(.secondary)
                            }
                            .fxFrame(width: 80, height: 80) // Fixed frame
                            .background(Color(.systemGray5))
                            .fxCornerRadius(8) // Fixed corner radius
                        }
                    }
                    .fxPadding(.horizontal, 20) // Fixed horizontal padding
                    
                    // Percentage-based section
                    VStack(spacing: 16.fxpt) { // Fixed spacing
                        Text("Percentage-Based Layout")
                            .font(.fxSystem(size: 18, weight: .semibold)) // Fixed font size
                        
                        Rectangle()
                            .fill(Color.blue.opacity(0.3))
                            .dyFrame(width: 300) // Dynamic width
                            .fxFrame(height: 100) // Fixed height
                            .fxCornerRadius(8) // Fixed corner radius
                        
                        Text("This rectangle uses dynamic width scaling")
                            .font(.fxSystem(size: 14)) // Fixed font size
                            .foregroundColor(.secondary)
                            .multilineTextAlignment(.center)
                    }
                    .fxPadding(20) // Fixed padding
                    
                    Spacer.fxMinLength(20) // Fixed minimum spacer length
                }
            }
            .navigationTitle("AppDimens")
            .navigationBarTitleDisplayMode(.large)
        }
    }
}

@available(iOS 13.0, *)
struct SwiftUIExampleView_Previews: PreviewProvider {
    static var previews: some View {
        SwiftUIExampleView()
    }
}
