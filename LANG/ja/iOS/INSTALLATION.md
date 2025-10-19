# 📦 AppDimens iOS - インストールガイド

> 言語: [English](../../../iOS/INSTALLATION.md) | [Português (BR)](../../pt-BR/iOS/INSTALLATION.md) | [Español](../../es/iOS/INSTALLATION.md) | [हिन्दी](../../hi/iOS/INSTALLATION.md) | [Русский](../../ru/iOS/INSTALLATION.md) | [中文](../../zh/iOS/INSTALLATION.md)

このガイドでは、ステップバイステップの手順で AppDimens iOS をプロジェクトにインストールし、統合する方法を説明します。

## 📋 必要条件

- iOS 13.0+
- Swift 5.0+
- Xcode 12.0+

## 🚀 インストール方法

### CocoaPods（推奨）

CocoaPods は AppDimens iOS の推奨インストール方法です。

#### 1. Podfile に追加

```ruby
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens'
end
```

#### 2. Pod をインストール

```bash
pod install
```

#### 3. ワークスペースを開く

```bash
open YourApp.xcworkspace
```

#### 4. Swift ファイルにインポート

```swift
import AppDimens
```

#### CocoaPods の高度な設定

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

#### 方法 1: Xcode インターフェース

1. Xcode にて：
   - File → Add Package Dependencies
   - 入力: `https://github.com/bodenberg/appdimens.git`
   - バージョン選択: `1.0.5` 以上
   - ターゲットに追加

2. Swift ファイルにインポート：
```swift
import AppDimens
```

#### 方法 2: Package.swift

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
]
```

#### 方法 3: Xcode プロジェクト設定

1. プロジェクトを選択 → Package Dependencies
2. "+" ボタンをクリック
3. リポジトリ URL を入力して追加

### 手動インストール

#### 1. ソースコードをダウンロード

```bash
git clone https://github.com/bodenberg/appdimens.git
```

#### 2. Sources フォルダをコピー

- `Sources/AppDimens/` をプロジェクトにコピー
- すべての Swift ファイルを Xcode プロジェクトに追加

#### 3. Swift ファイルにインポート

```swift
import AppDimens
```

## 🔧 設定

### Build 設定

- iOS Deployment Target: 13.0 以上
- Swift Language Version: Swift 5
- Enable Bitcode: No（CocoaPods 使用時）

### プロジェクト設定

- CocoaPods: Podfile がフレームワークリンクとビルド設定を処理
- SPM: Xcode が自動的に設定
- 手動: ターゲットにファイルを追加し、必要に応じてインポート

## 🎯 クイックスタート

```swift
import AppDimens

// 固定スケーリング（UI 要素向け）
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// 動的スケーリング（レイアウト向け）
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
            Text("こんにちは世界")
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

## 🛠 トラブルシューティング

- モジュールが見つからない: `pod install` を実行し、クリーンビルド
- シンボル未解決: `import AppDimens` を追加、`16.fxpt` の構文を確認
- SPM 解決失敗: ネットワーク/URL を確認、パッケージキャッシュをクリア

## 🔄 Android からの移行

| Android | iOS |
|---------|-----|
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |

## 📄 ライセンス

本プロジェクトは Apache License 2.0 の下でライセンスされています — 詳細は [LICENSE](LICENSE) をご覧ください。

---

**AppDimens iOS** — レスポンシブデザインをもっと簡単に！🚀
