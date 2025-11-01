/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens iOS - Base Orientation Example
 *
 * Description:
 * Example demonstrating the Base Orientation feature which automatically
 * adapts dimensions when the device rotates between portrait and landscape.
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
import AppDimens

/// Example view demonstrating Base Orientation feature
struct BaseOrientationExampleView: View {
    var body: some View {
        ScrollView {
            VStack(spacing: AppDimens.fixed(16).pt) {
                // Header
                Text("🔄 Base Orientation Examples")
                    .font(.system(size: AppDimens.fixed(24).pt))
                    .fontWeight(.bold)
                    .padding(.bottom, AppDimens.fixed(8).pt)
                
                // Example 1: Portrait Design Card
                GroupBox(label: Text("Portrait Design Card")) {
                    VStack(alignment: .leading, spacing: AppDimens.fixed(8).pt) {
                        Text("Design created for portrait orientation")
                            .font(.system(size: AppDimens.fixed(14).pt))
                            .foregroundColor(.secondary)
                        
                        // Card that adapts to rotation
                        let cardWidth = AppDimens.fixed(280).portraitLowest().pt
                        
                        RoundedRectangle(cornerRadius: AppDimens.fixed(12).pt)
                            .fill(Color.blue.opacity(0.2))
                            .frame(width: cardWidth, height: AppDimens.fixed(120).pt)
                            .overlay(
                                VStack {
                                    Text("Adaptive Card")
                                        .font(.system(size: AppDimens.fixed(16).pt))
                                    Text("Width: \(Int(cardWidth))pt")
                                        .font(.system(size: AppDimens.fixed(12).pt))
                                        .foregroundColor(.secondary)
                                    Text("Rotates automatically! 🔄")
                                        .font(.system(size: AppDimens.fixed(10).pt))
                                }
                            )
                    }
                    .padding(AppDimens.fixed(12).pt)
                }
                .fxPadding(8)
                
                // Example 2: Landscape Design Panel
                GroupBox(label: Text("Landscape Design Panel")) {
                    VStack(alignment: .leading, spacing: AppDimens.fixed(8).pt) {
                        Text("Design created for landscape orientation")
                            .font(.system(size: AppDimens.fixed(14).pt))
                            .foregroundColor(.secondary)
                        
                        let panelHeight = AppDimens.fixed(80).landscapeLowest().pt
                        
                        RoundedRectangle(cornerRadius: AppDimens.fixed(12).pt)
                            .fill(Color.green.opacity(0.2))
                            .frame(height: panelHeight)
                            .frame(maxWidth: .infinity)
                            .overlay(
                                VStack {
                                    Text("Landscape Panel")
                                        .font(.system(size: AppDimens.fixed(14).pt))
                                    Text("Height: \(Int(panelHeight))pt")
                                        .font(.system(size: AppDimens.fixed(10).pt))
                                        .foregroundColor(.secondary)
                                }
                            )
                    }
                    .padding(AppDimens.fixed(12).pt)
                }
                .fxPadding(8)
                
                // Example 3: Comparison
                GroupBox(label: Text("Comparison: With vs Without")) {
                    VStack(alignment: .leading, spacing: AppDimens.fixed(12).pt) {
                        // Without base orientation
                        Text("Without Base Orientation:")
                            .font(.system(size: AppDimens.fixed(14).pt))
                            .fontWeight(.semibold)
                        
                        let normalWidth = AppDimens.fixed(200).screen(type: .lowest).pt
                        
                        RoundedRectangle(cornerRadius: 8)
                            .fill(Color.red.opacity(0.2))
                            .frame(width: normalWidth, height: AppDimens.fixed(60).pt)
                            .overlay(
                                Text("Width: \(Int(normalWidth))pt")
                                    .font(.system(size: AppDimens.fixed(12).pt))
                            )
                        
                        // With base orientation
                        Text("With Base Orientation (Portrait):")
                            .font(.system(size: AppDimens.fixed(14).pt))
                            .fontWeight(.semibold)
                            .padding(.top, AppDimens.fixed(8).pt)
                        
                        let adaptiveWidth = AppDimens.fixed(200).portraitLowest().pt
                        
                        RoundedRectangle(cornerRadius: 8)
                            .fill(Color.blue.opacity(0.2))
                            .frame(width: adaptiveWidth, height: AppDimens.fixed(60).pt)
                            .overlay(
                                Text("Width: \(Int(adaptiveWidth))pt (auto-adapts!)")
                                    .font(.system(size: AppDimens.fixed(12).pt))
                            )
                        
                        Text("💡 Rotate device to see the difference!")
                            .font(.system(size: AppDimens.fixed(12).pt))
                            .foregroundColor(.orange)
                            .padding(.top, AppDimens.fixed(8).pt)
                    }
                    .padding(AppDimens.fixed(12).pt)
                }
                .fxPadding(8)
                
                // API Reference
                GroupBox(label: Text("API Reference")) {
                    VStack(alignment: .leading, spacing: AppDimens.fixed(8).pt) {
                        Text("Métodos Disponíveis:")
                            .font(.system(size: AppDimens.fixed(14).pt))
                            .fontWeight(.semibold)
                        
                        VStack(alignment: .leading, spacing: AppDimens.fixed(4).pt) {
                            APIRow(method: ".baseOrientation(.portrait)", description: "Explicit API")
                            APIRow(method: ".portraitLowest()", description: "Portrait design, width")
                            APIRow(method: ".portraitHighest()", description: "Portrait design, height")
                            APIRow(method: ".landscapeLowest()", description: "Landscape design, height")
                            APIRow(method: ".landscapeHighest()", description: "Landscape design, width")
                        }
                    }
                    .padding(AppDimens.fixed(12).pt)
                }
                .fxPadding(8)
            }
            .padding(AppDimens.fixed(16).pt)
        }
        .navigationTitle("Base Orientation")
    }
}

/// Helper view for API rows
private struct APIRow: View {
    let method: String
    let description: String
    
    var body: some View {
        HStack {
            Text(method)
                .font(.system(size: AppDimens.fixed(12).pt, design: .monospaced))
                .foregroundColor(.blue)
            Spacer()
            Text(description)
                .font(.system(size: AppDimens.fixed(11).pt))
                .foregroundColor(.secondary)
        }
        .padding(.vertical, AppDimens.fixed(2).pt)
    }
}

#Preview {
    NavigationView {
        BaseOrientationExampleView()
    }
}

