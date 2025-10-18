# 📦 AppDimens iOS - Installation Guide

This comprehensive guide will help you install and integrate AppDimens iOS into your project with step-by-step instructions.

## 📋 Requirements

- iOS 13.0+
- Swift 5.0+
- Xcode 12.0+

## 🚀 Installation Methods

### CocoaPods (Recommended)

CocoaPods is the recommended installation method for AppDimens iOS.

#### 1. Add to your Podfile

```ruby
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens'
end
```

#### 2. Install the pod

```bash
pod install
```

#### 3. Open your workspace

```bash
open YourApp.xcworkspace
```

#### 4. Import in your Swift files

```swift
import AppDimens
```

#### Advanced CocoaPods Configuration

For advanced configuration, you can specify additional options in your Podfile:

```ruby
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens', '~> 1.0'
end

# Post-install hook for additional configuration
post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '13.0'
    end
  end
end
```

### Swift Package Manager

Swift Package Manager is Apple's official dependency management tool.

#### Method 1: Xcode Interface

1. **In Xcode:**
   - File → Add Package Dependencies
   - Enter the repository URL: `https://github.com/bodenberg/appdimens.git`
   - Select the version and add to your target

2. **Import in your Swift files:**
```swift
import AppDimens
```

#### Method 2: Package.swift

Add to your `Package.swift` file:

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
]
```

#### Method 3: Xcode Project Settings

1. Select your project in Xcode
2. Go to Package Dependencies tab
3. Click the "+" button
4. Enter: `https://github.com/bodenberg/appdimens.git`
5. Select version and add to target

### Manual Installation

If you prefer to include the source code directly in your project:

#### 1. Download the source code

```bash
git clone https://github.com/bodenberg/appdimens.git
```

#### 2. Copy the Sources folder

- Copy `Sources/AppDimens/` to your project
- Add all Swift files to your Xcode project

#### 3. Import in your Swift files

```swift
import AppDimens
```

## 🔧 Configuration

### Build Settings

Ensure your project has the following build settings:

- **iOS Deployment Target**: 13.0 or higher
- **Swift Language Version**: Swift 5
- **Enable Bitcode**: No (if using CocoaPods)

### Project Configuration

#### For CocoaPods

After running `pod install`, your project should be configured automatically. The Podfile will handle:

- Framework linking
- Build settings
- Target dependencies

#### For Swift Package Manager

The package will be automatically configured when added through Xcode. Ensure:

- Target membership is correct
- Framework is linked to your target
- Import statements are added where needed

#### For Manual Installation

1. Add all Swift files to your target
2. Ensure proper target membership
3. Add import statements where needed

## 🎯 Quick Start

### Basic Usage

After installation, you can start using AppDimens immediately:

```swift
import AppDimens

// Fixed scaling (recommended for UI elements)
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// Dynamic scaling (for proportional layouts)
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt
```

### SwiftUI Integration

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack {
            Text("Hello, World!")
                .font(.fxSystem(size: 16))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(8)
        }
    }
}
```

### UIKit Integration

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let button = UIButton()
        button.frame = CGRect(
            x: 0, y: 0,
            width: 200.dypt,
            height: 48.fxpt
        )
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        view.addSubview(button)
    }
}
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Import Error
```
No such module 'AppDimens'
```

**Solutions:**
- Ensure you've run `pod install` after adding the dependency
- Clean and rebuild your project (Product → Clean Build Folder)
- Check that the target includes the AppDimens framework
- Verify the framework is properly linked in your target settings

#### 2. Build Errors
```
Use of unresolved identifier 'fxpt'
```

**Solutions:**
- Make sure you've imported AppDimens: `import AppDimens`
- Check that you're using the correct syntax: `16.fxpt`
- Verify the framework is properly installed and linked

#### 3. CocoaPods Issues
```
[!] Unable to find a specification for 'AppDimens'
```

**Solutions:**
- Update your CocoaPods: `sudo gem install cocoapods`
- Update your pod repo: `pod repo update`
- Try installing again: `pod install`
- Check your internet connection

#### 4. Swift Package Manager Issues
```
Failed to resolve package dependencies
```

**Solutions:**
- Check your internet connection
- Verify the repository URL is correct
- Try clearing the package cache in Xcode
- Restart Xcode and try again

#### 5. Version Conflicts
```
Multiple commands produce the same file
```

**Solutions:**
- Clean your build folder
- Delete derived data
- Restart Xcode
- Check for duplicate frameworks

### Debug Steps

If you're still having issues, try these debug steps:

1. **Check Installation:**
   ```swift
   import AppDimens
   print("AppDimens version: \(AppDimens.version)")
   ```

2. **Verify Framework:**
   - Check that AppDimens appears in your project navigator
   - Verify it's added to your target's "Frameworks, Libraries, and Embedded Content"

3. **Clean and Rebuild:**
   - Product → Clean Build Folder
   - Delete derived data
   - Restart Xcode
   - Build again

4. **Check Dependencies:**
   - Ensure all required frameworks are installed
   - Verify iOS deployment target is 13.0+

## 🔄 Migration from Android

If you're migrating from the Android version of AppDimens, see the [Migration Guide](MIGRATION.md) for detailed instructions.

### Quick Migration Reference

| Android | iOS |
|---------|-----|
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |

## 📚 Next Steps

After successful installation:

1. **Read the documentation**: [README.md](README.md)
2. **Try the examples**: [Examples/](Examples/)
3. **Explore the API**: [DOCUMENTATION.md](DOCUMENTATION.md)
4. **Join the community**: [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)

## 🆘 Getting Help

If you encounter issues not covered here:

1. **Check the documentation**: [README.md](README.md)
2. **Review examples**: [Examples/](Examples/)
3. **Create an issue**: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

---

**AppDimens iOS** - Responsive design made simple! 🚀
