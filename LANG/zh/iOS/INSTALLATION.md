# 📦 AppDimens iOS - 安装指南

> 语言: [English](../../../iOS/INSTALLATION.md) | [Português (BR)](../../pt-BR/iOS/INSTALLATION.md) | [Español](../../es/iOS/INSTALLATION.md) | [हिन्दी](../../hi/iOS/INSTALLATION.md) | [Русский](../../ru/iOS/INSTALLATION.md) | [日本語](../../ja/iOS/INSTALLATION.md)

本指南将通过分步说明帮助您在项目中安装并集成 AppDimens iOS。

## 📋 要求

- iOS 13.0+
- Swift 5.0+
- Xcode 12.0+

## 🚀 安装方式

### CocoaPods（推荐）

CocoaPods 是安装 AppDimens iOS 的推荐方式。

#### 1. 在 Podfile 中添加

```ruby
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens'
end
```

#### 2. 安装 pod

```bash
pod install
```

#### 3. 打开 workspace

```bash
open YourApp.xcworkspace
```

#### 4. 在 Swift 文件中导入

```swift
import AppDimens
```

#### CocoaPods 高级配置

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

#### 方式一：Xcode 界面

1. 在 Xcode 中：
   - File → Add Package Dependencies
   - 输入：`https://github.com/bodenberg/appdimens.git`
   - 选择版本：`1.0.6` 或更高
   - 添加到目标 Target

2. 在 Swift 文件中导入：
```swift
import AppDimens
```

#### 方式二：Package.swift

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.6")
]
```

#### 方式三：Xcode 项目设置

1. 选择项目 → Package Dependencies
2. 点击“+”
3. 输入仓库 URL 并添加

### 手动安装

#### 1. 下载源代码

```bash
git clone https://github.com/bodenberg/appdimens.git
```

#### 2. 复制 Sources 文件夹

- 将 `Sources/AppDimens/` 复制到您的项目
- 将所有 Swift 文件添加到 Xcode 工程

#### 3. 在 Swift 文件中导入

```swift
import AppDimens
```

## 🔧 配置

### Build 设置

- iOS Deployment Target：13.0 或更高
- Swift Language Version：Swift 5
- Enable Bitcode：No（若使用 CocoaPods）

### 项目配置

- CocoaPods：Podfile 负责框架链接与构建设置
- SPM：Xcode 会自动配置
- 手动安装：将文件加入 Target 并按需导入

## 🎯 快速开始

```swift
import AppDimens

// 固定缩放（适用于 UI 元素）
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// 动态缩放（适用于布局）
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
            Text("你好，世界")
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

## 🛠 故障排除

- 找不到模块：运行 `pod install`，清理构建
- 未解析标识符：添加 `import AppDimens`，检查 `16.fxpt` 语法
- SPM 解析失败：检查网络/URL，清理包缓存

## 🔄 从 Android 迁移

| Android | iOS |
|---------|-----|
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |

## 📄 许可证

本项目采用 Apache License 2.0 许可 — 详见 [LICENSE](LICENSE) 文件。

---

**AppDimens iOS** — 让响应式设计更简单！🚀
