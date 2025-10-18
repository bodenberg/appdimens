# 🚀 AppDimens iOS - Usage Guide

This comprehensive guide shows you how to use AppDimens iOS in your projects with practical examples and best practices.

## 📋 Table of Contents

1. [Installation](#installation)
2. [Basic Concepts](#basic-concepts)
3. [SwiftUI Usage](#swiftui-usage)
4. [UIKit Usage](#uikit-usage)
5. [Advanced Configuration](#advanced-configuration)
6. [Practical Examples](#practical-examples)
7. [Tips and Tricks](#tips-and-tricks)
8. [Troubleshooting](#troubleshooting)

## 🛠 Installation

### CocoaPods

```ruby
# Podfile
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens'
end
```

```bash
pod install
```

### Swift Package Manager

1. File → Add Package Dependencies
2. URL: `https://github.com/bodenberg/appdimens.git`
3. Version: 1.0.5+

## 🎯 Basic Concepts

### Fixed vs Dynamic

**Fixed (FX)** - For UI elements:
- Buttons, paddings, fonts, icons
- Logarithmic controlled growth
- Maintains visual consistency

**Dynamic (DY)** - For layouts:
- Containers, spacers, grids
- Proportional growth
- Maintains screen percentage

### Basic Syntax

```swift
import AppDimens

// Fixed - UI elements
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// Dynamic - layouts
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt
```

## 🎨 SwiftUI Usage

### Basic Example

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("Title")
                .font(.fxSystem(size: 24, weight: .bold))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(12)
                .foregroundColor(.blue)
            
            Button("Action") {
                // Action
            }
            .fxFrame(width: 120, height: 44)
            .fxCornerRadius(8)
        }
    }
}
```

### SwiftUI Extensions

```swift
// Padding
.fxPadding(16)                    // All sides
.fxPadding(.horizontal, 20)       // Horizontal
.fxPadding(.top, 10)              // Top

// Frame
.fxFrame(width: 100)              // Fixed width
.fxFrame(height: 50)              // Fixed height
.fxFrame(width: 100, height: 50)  // Both

// Dynamic
.dyFrame(width: 200)              // Dynamic width
.dyFrame(height: 100)             // Dynamic height

// Corner Radius
.fxCornerRadius(8)                // Fixed corner radius
.dyCornerRadius(12)               // Dynamic corner radius
```

### Fonts

```swift
// System fonts
.font(.fxSystem(size: 16))                    // Fixed size
.font(.fxSystem(size: 18, weight: .bold))     // With weight
.font(.fxSystem(size: 14, design: .rounded))  // With design

// Custom fonts
.font(.fxCustom("Helvetica", size: 16))       // Custom font

// Dynamic
.font(.dySystem(size: 16))                    // Dynamic size
```

### Environment Integration (Recommended)

```swift
@main
struct MyApp: App {
    var body: some Scene {
        WindowGroup {
            DimensProvider {  // Essential for new features
                ContentView()
            }
        }
    }
}

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("Enhanced AppDimens")
                .font(.fxSystem(size: 24, weight: .bold))
            
            // Protocol-based API
            Rectangle()
                .frame(width: 100.fixed().dimension)
                .frame(height: 50.fxpt)
            
            // Physical units
            Rectangle()
                .frame(width: 2.cm, height: 1.cm)
            
            // Item count calculator
            Rectangle()
                .calculateAvailableItemCount(
                    itemSize: 50.fxpt,
                    itemPadding: 8.fxpt,
                    count: $itemCount
                )
        }
    }
}
```

## 📱 UIKit Usage

### Basic Example

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    private func setupUI() {
        // Container
        let containerView = UIView()
        containerView.backgroundColor = .systemBlue
        containerView.fxCornerRadius(16)
        view.addSubview(containerView)
        
        // Label
        let titleLabel = UILabel()
        titleLabel.text = "Title"
        titleLabel.textAlignment = .center
        titleLabel.fxFontSize(20)
        containerView.addSubview(titleLabel)
        
        // Button
        let button = UIButton(type: .system)
        button.setTitle("Button", for: .normal)
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        containerView.addSubview(button)
        
        // Constraints
        containerView.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        button.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            // Container - dynamic width, fixed height
            containerView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            containerView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            containerView.widthAnchor.constraint(equalToConstant: 300.dypt),
            containerView.heightAnchor.constraint(equalToConstant: 200.fxpt),
            
            // Label
            titleLabel.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 20.fxpt),
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16.fxpt),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16.fxpt),
            
            // Button
            button.centerXAnchor.constraint(equalTo: containerView.centerXAnchor),
            button.centerYAnchor.constraint(equalTo: containerView.centerYAnchor),
            button.widthAnchor.constraint(equalToConstant: 120.dypt),
            button.heightAnchor.constraint(equalToConstant: 44.fxpt)
        ])
    }
}
```

### UIKit Extensions

```swift
// UIView
view.fxCornerRadius(8)            // Fixed corner radius
view.dyCornerRadius(12)           // Dynamic corner radius
view.fxBorderWidth(1)             // Border width

// UILabel
label.fxFontSize(16)              // Fixed font size
label.dyFontSize(18)              // Dynamic font size

// UIButton
button.fxTitleFontSize(14)        // Title font size
button.dyTitleFontSize(16)        // Dynamic title font size

// UITextField
textField.fxFontSize(16)          // Font size
textField.dyFontSize(18)          // Dynamic font size
```

## ⚙️ Advanced Configuration

### Custom Device Values

```swift
let dimension = AppDimens.fixed(16)
    .screen(.phone, 14)           // 14pt for phones
    .screen(.tablet, 18)          // 18pt for tablets
    .screen(.watch, 12)           // 12pt for Apple Watch
    .toPoints()
```

### Screen Qualifiers

```swift
let dimension = AppDimens.fixed(16)
    .screen(.phone, 320, 14)      // 14pt for phones with width >= 320pt
    .screen(.tablet, 768, 18)     // 18pt for tablets with width >= 768pt
    .toPoints()
```

### Aspect Ratio Adjustment

```swift
let dimension = AppDimens.fixed(16)
    .aspectRatio(enable: true)    // Enable aspect ratio adjustment
    .aspectRatio(enable: true, sensitivity: 0.3)  // Custom sensitivity
    .toPoints()
```

### Screen Type

```swift
let dimension = AppDimens.dynamic(100)
    .type(.lowest)                // Use smallest dimension (default)
    .type(.highest)               // Use largest dimension
    .toPoints()
```

### Multi-Window

```swift
let dimension = AppDimens.fixed(16)
    .multiWindowAdjustment(ignore: true)  // Ignore multi-window adjustments
    .toPoints()
```

## 📊 Practical Examples

### Responsive Card

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Card Title")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("This card adapts to any screen size.")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
            
            HStack {
                Spacer()
                Button("Action") { }
                    .fxFrame(width: 80, height: 32)
                    .fxCornerRadius(6)
            }
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // Dynamic width
        .fxFrame(height: 200)          // Fixed height
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

### Responsive Grid

```swift
struct ResponsiveGrid: View {
    let items = Array(1...12)
    
    var body: some View {
        LazyVGrid(columns: [
            GridItem(.flexible(), spacing: 16.fxpt),
            GridItem(.flexible(), spacing: 16.fxpt)
        ], spacing: 16.fxpt) {
            ForEach(items, id: \.self) { item in
                VStack {
                    Image(systemName: "star.fill")
                        .font(.fxSystem(size: 24))
                        .foregroundColor(.yellow)
                    
                    Text("Item \(item)")
                        .font(.fxSystem(size: 12))
                }
                .fxFrame(width: 80, height: 80)
                .background(Color(.systemGray5))
                .fxCornerRadius(8)
            }
        }
        .fxPadding(16)
    }
}
```

### Percentage Layout

```swift
struct PercentageLayout: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            // 80% of screen width
            Rectangle()
                .fill(Color.blue.opacity(0.3))
                .dyFrame(width: AppDimens.percentage(0.8))
                .fxFrame(height: 100)
                .fxCornerRadius(8)
            
            // 60% of screen width
            Rectangle()
                .fill(Color.green.opacity(0.3))
                .dyFrame(width: AppDimens.percentage(0.6))
                .fxFrame(height: 80)
                .fxCornerRadius(8)
        }
    }
}
```

### Complex Layout with Mixed Scaling

```swift
struct ComplexLayout: View {
    var body: some View {
        ScrollView {
            VStack(spacing: 20.fxpt) {
                // Header with fixed dimensions
                VStack(spacing: 8.fxpt) {
                    Text("App Title")
                        .font(.fxSystem(size: 28, weight: .bold))
                    
                    Text("Subtitle")
                        .font(.fxSystem(size: 16))
                        .foregroundColor(.secondary)
                }
                .fxPadding(20)
                .frame(maxWidth: .infinity)
                .background(Color(.systemGray6))
                .fxCornerRadius(16)
                
                // Content with dynamic dimensions
                LazyVStack(spacing: 16.fxpt) {
                    ForEach(0..<10) { index in
                        HStack(spacing: 12.fxpt) {
                            // Fixed size icon
                            Image(systemName: "star.fill")
                                .font(.fxSystem(size: 20))
                                .foregroundColor(.yellow)
                                .fxFrame(width: 24, height: 24)
                            
                            // Dynamic content
                            VStack(alignment: .leading, spacing: 4.fxpt) {
                                Text("Item \(index + 1)")
                                    .font(.fxSystem(size: 16, weight: .medium))
                                
                                Text("Description for item \(index + 1)")
                                    .font(.fxSystem(size: 14))
                                    .foregroundColor(.secondary)
                            }
                            
                            Spacer()
                            
                            // Fixed size button
                            Button("Action") { }
                                .fxFrame(width: 60, height: 32)
                                .fxCornerRadius(6)
                        }
                        .fxPadding(16)
                        .background(Color(.systemBackground))
                        .fxCornerRadius(12)
                        .shadow(color: .black.opacity(0.1), radius: 2, x: 0, y: 1)
                    }
                }
                .dyFrame(width: AppDimens.percentage(0.9))
            }
            .fxPadding(16)
        }
    }
}
```

## 💡 Tips and Tricks

### 1. Cache Dimensions

```swift
class ViewController: UIViewController {
    // Cache frequently used dimensions
    private let buttonHeight = AppDimens.fixed(44).toPoints()
    private let cardWidth = AppDimens.dynamic(300).toPoints()
    private let padding = 16.fxpt
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Use cached dimensions
    }
}
```

### 2. Global Constants

```swift
struct AppDimensConstants {
    static let buttonHeight = AppDimens.fixed(44)
    static let cardWidth = AppDimens.dynamic(300)
    static let padding = AppDimens.fixed(16)
    static let cornerRadius = AppDimens.fixed(8)
}

// Usage
button.heightAnchor.constraint(equalToConstant: AppDimensConstants.buttonHeight.toPoints())
```

### 3. Custom Extensions

```swift
extension AppDimensFixed {
    static let standardButton = AppDimens.fixed(44)
    static let standardPadding = AppDimens.fixed(16)
    static let standardCornerRadius = AppDimens.fixed(8)
}

extension AppDimensDynamic {
    static let cardWidth = AppDimens.dynamic(300)
    static let containerWidth = AppDimens.dynamic(400)
}
```

### 4. Debug Dimensions

```swift
// Add in development
#if DEBUG
func debugDimensions() {
    let (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
    print("📱 Screen: \(width) × \(height)")
    print("📱 Device: \(DeviceType.current())")
    
    let factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
    print("📊 Factors: \(factors)")
}
#endif
```

### 5. Testing on Different Devices

```swift
// Use SwiftUI Preview for testing
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            ContentView()
                .previewDevice("iPhone SE (3rd generation)")
            
            ContentView()
                .previewDevice("iPad Pro (12.9-inch) (6th generation)")
        }
    }
}
```

### 6. Performance Optimization

```swift
// Avoid excessive calculations in tight loops
class OptimizedViewController: UIViewController {
    private let cachedDimensions = CachedDimensions()
    
    private struct CachedDimensions {
        let buttonHeight = AppDimens.fixed(44).toPoints()
        let cardWidth = AppDimens.dynamic(300).toPoints()
        let padding = 16.fxpt
        let cornerRadius = 8.fxpt
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Use cached dimensions instead of recalculating
    }
}
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Import Not Working
```
No such module 'AppDimens'
```

**Solution:**
- Ensure you've run `pod install`
- Clean and rebuild your project
- Check that the target includes the AppDimens framework

#### 2. Dimensions Not Applying
```
Use of unresolved identifier 'fxpt'
```

**Solution:**
- Make sure you've imported AppDimens: `import AppDimens`
- Check that you're using the correct syntax: `16.fxpt`

#### 3. Performance Issues
- Cache frequently used dimensions
- Avoid calculations in tight loops
- Use appropriate scaling types

### Debug

```swift
// Check current dimensions
let (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
print("Screen: \(width) × \(height)")

// Check device type
print("Device: \(DeviceType.current())")

// Check adjustment factors
let factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
print("Factors: \(factors)")
```

## 📚 Additional Resources

- [Complete Documentation](DOCUMENTATION.md)
- [Installation Guide](INSTALLATION.md)
- [Code Examples](Examples/)
- [Changelog](CHANGELOG.md)

## 🤝 Support

- **GitHub Issues**: [Create issue](https://github.com/bodenberg/appdimens/issues)
- **Documentation**: [GitHub Wiki](https://github.com/bodenberg/appdimens/wiki)
