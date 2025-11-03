# AppDimens iOS - Installation Guide

**Version: 2.0.0**

---

## CocoaPods

### 1. Add to Podfile

```ruby
# Full package (recommended)
pod 'AppDimens', '~> 2.0.0'

# Or specific modules
pod 'AppDimens/Main', '~> 2.0.0'
pod 'AppDimens/UI', '~> 2.0.0'
pod 'AppDimens/Games', '~> 2.0.0'
```

### 2. Install

```bash
pod install
```

---

## Swift Package Manager

### 1. Xcode

File → Add Packages → Enter URL:
```
https://github.com/bodenberg/appdimens.git
```

Version: From 2.0.0

### 2. Package.swift

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "2.0.0")
]
```

---

## Usage

```swift
import AppDimens

// SwiftUI
Text("Hello").font(.system(size: AppDimens.shared.balanced(16).toPoints()))

// UIKit
let size = AppDimens.shared.balanced(48).toPoints()
```

---

**Next:** [Usage Guide](USAGE_GUIDE.md)
