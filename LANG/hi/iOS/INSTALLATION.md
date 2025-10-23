# 📦 AppDimens iOS - इंस्टॉलेशन गाइड

> भाषाएँ: [English](../../../iOS/INSTALLATION.md) | [Português (BR)](../../pt-BR/iOS/INSTALLATION.md) | [Español](../../es/iOS/INSTALLATION.md) | [Русский](../../ru/iOS/INSTALLATION.md) | [中文](../../zh/iOS/INSTALLATION.md) | [日本語](../../ja/iOS/INSTALLATION.md)

यह व्यापक गाइड आपको स्टेप-बाय-स्टेप निर्देशों के साथ AppDimens iOS को अपने प्रोजेक्ट में इंस्टॉल और इंटीग्रेट करने में मदद करेगा।

## 📋 आवश्यकताएँ

- iOS 13.0+
- Swift 5.0+
- Xcode 12.0+

## 🚀 इंस्टॉलेशन के तरीके

### CocoaPods (अनुशंसित)

CocoaPods AppDimens iOS के लिए अनुशंसित इंस्टॉलेशन तरीका है।

#### 1. अपने Podfile में जोड़ें

```ruby
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens'
end
```

#### 2. Pod इंस्टॉल करें

```bash
pod install
```

#### 3. वर्कस्पेस खोलें

```bash
open YourApp.xcworkspace
```

#### 4. Swift फाइलों में इम्पोर्ट करें

```swift
import AppDimens
```

#### CocoaPods एडवांस्ड कॉन्फ़िगरेशन

```ruby
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens', '~> 1.0'
end

post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '13.0'
    end
  end
end
```

### Swift Package Manager

#### तरीका 1: Xcode इंटरफ़ेस

1. Xcode में:
   - File → Add Package Dependencies
   - URL: `https://github.com/bodenberg/appdimens.git`
   - Version: `1.0.8` या अधिक
   - अपने टार्गेट में जोड़ें

2. Swift फाइलों में इम्पोर्ट करें:
```swift
import AppDimens
```

#### तरीका 2: Package.swift

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.8")
]
```

#### तरीका 3: Xcode प्रोजेक्ट सेटिंग्स

1. प्रोजेक्ट चुनें → Package Dependencies
2. "+" बटन क्लिक करें
3. URL दर्ज करें और ऐड करें

### मैनुअल इंस्टॉलेशन

#### 1. सोर्स कोड डाउनलोड करें

```bash
git clone https://github.com/bodenberg/appdimens.git
```

#### 2. Sources फ़ोल्डर कॉपी करें

- `Sources/AppDimens/` को अपने प्रोजेक्ट में कॉपी करें
- सभी Swift फाइलें Xcode में जोड़ें

#### 3. Swift फाइलों में इम्पोर्ट करें

```swift
import AppDimens
```

## 🔧 कॉन्फ़िगरेशन

### Build Settings

- iOS Deployment Target: 13.0+
- Swift Language Version: Swift 5
- Enable Bitcode: No (CocoaPods के साथ)

### प्रोजेक्ट कॉन्फ़िगरेशन

- CocoaPods: Podfile फ्रेमवर्क लिंकिंग और बिल्ड सेटिंग्स संभालेगा
- SPM: Xcode अपने आप कॉन्फ़िगर करेगा
- Manual: टार्गेट में सभी फाइलें जोड़ें और इम्पोर्ट करें

## 🎯 क्विक स्टार्ट

```swift
import AppDimens

// फिक्स्ड स्केलिंग (UI एलिमेंट्स के लिए)
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// डायनामिक स्केलिंग (लेआउट के लिए)
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt
```

### SwiftUI

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack {
            Text("नमस्ते दुनिया")
                .font(.fxSystem(size: 16))
                .fxPadding(16)
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(8)
        }
    }
}
```

### UIKit

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        let button = UIButton()
        button.frame = CGRect(x: 0, y: 0, width: 200.dypt, height: 48.fxpt)
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        view.addSubview(button)
    }
}
```

## 🛠 समस्या निवारण

- मॉड्यूल नहीं मिला: `pod install` चलाएँ, क्लीन बिल्ड करें
- सिंबल नहीं मिला: `import AppDimens` जोड़ें, `16.fxpt` जैसी सिंटैक्स जाँचें
- SPM रिज़ॉल्व फेल: इंटरनेट/URL जाँचें, पैकेज कैश क्लियर करें

## 🔄 Android से माइग्रेशन

| Android | iOS |
|---------|-----|
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |

## 📄 लाइसेंस

यह प्रोजेक्ट Apache License 2.0 के अंतर्गत लाइसेंस प्राप्त है — विवरण के लिए [LICENSE](LICENSE) देखें।

---

**AppDimens iOS** — रिस्पॉन्सिव डिज़ाइन को सरल बनाता है! 🚀
