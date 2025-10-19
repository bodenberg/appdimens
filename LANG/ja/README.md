<div align="center">
    <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens - すべてのデバイスでレスポンシブデザイン" height="300"/>
    <h1>📐 AppDimens</h1>
    <p><strong>AndroidとiOSのためのスマートでレスポンシブな寸法システム</strong></p>
    <p>数学的に正確なレスポンシブスケーリングにより、UIデザインがあらゆる画面サイズやアスペクト比に完璧に適応します — スマートフォンからテレビ、車、ウェアラブルまで。</p>

[![バージョン](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![ライセンス](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![プラットフォーム](https://img.shields.io/badge/platform-Android%20%7C%20iOS-orange.svg)](https://github.com/bodenberg/appdimens)
[![ドキュメント](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

> 言語: [English](../../README.md) | [Português (BR)](../pt-BR/README.md) | [Español](../es/README.md) | [हिन्दी](../hi/README.md) | [Русский](../ru/README.md) | [中文](../zh/README.md)

---

## 🎯 AppDimensとは？

**AppDimens**は、固定ピクセル値を実際の画面特性に基づいてインテリジェントにスケーリングされた寸法に置き換える包括的な寸法管理システムです。従来のDP/Pointsが定数であるのに対し、AppDimensはそれらを基準値として扱い、異なる画面サイズ、密度、アスペクト比で予測可能にスケーリングします。

### 🎨 主な利点

- **🎯 視覚的一貫性**：すべてのデバイスタイプで完璧な比率を維持
- **📱 ユニバーサル互換性**：スマートフォン、タブレット、テレビ、車、ウェアラブルでシームレスに動作
- **⚡ パフォーマンス最適化**：キャッシュされた計算による最小限のランタイムオーバーヘッド
- **🔧 簡単な統合**：Jetpack Compose、XML Views、SwiftUI、UIKitで動作するシンプルなAPI
- **📐 数学的精度**：異なるデザインニーズに対応する2つのスケーリングモデル（FixedとDynamic）

---

## 🚀 クイックスタート

### Android

```kotlin
dependencies {
    // コアライブラリ（Dynamic + Fixed scaling）
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.5")
    
    // オプション：SDPとSSP scaling
    implementation("io.github.bodenberg:appdimens-sdps:1.0.5")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.5")
    
    // オールインワンパッケージ（ゲームモジュールは含まれません）
    implementation("io.github.bodenberg:appdimens-all:1.0.5")
    
    // C++/NDKサポート付きゲーム開発（別の依存関係）
    implementation("io.github.bodenberg:appdimens-games:1.0.5")
}
```

### iOS

```ruby
# Podfile
pod 'AppDimens'
```

```swift
// Swift Package Manager
.package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
```

---

## 🧠 コア寸法モデル

| モデル | 哲学 | 理想的なユースケース | サポートされているプラットフォーム |
|-------|------------|----------------|-------------------|
| **Fixed (FX)** | 対数スケーリング（洗練された） | ボタン、パディング、マージン、アイコン | Android + iOS |
| **Dynamic (DY)** | 比例スケーリング（積極的） | コンテナ、グリッド、流動的なフォント | Android + iOS |
| **SDP / SSP** | 事前計算されたリソース | XMLでの`@dimen`の直接使用 | Android |
| **物理単位** | mm/cm/inch → Dp/Sp/Px/Points | ウェアラブル、印刷、精密レイアウト | Android + iOS |

---

## 📱 プラットフォーム例

### 🤖 Android - Jetpack Compose

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.dydp)           // 動的な幅
            .height(200.fxdp)          // 固定の高さ
            .padding(16.fxdp)          // 固定のパディング
    ) {
        Column(
            modifier = Modifier.padding(16.fxdp)
        ) {
            Text(
                text = "レスポンシブタイトル",
                fontSize = 18.fxsp     // 固定のフォントサイズ
            )
            Text(
                text = "このカードはあらゆる画面サイズに適応します",
                fontSize = 14.dysp     // 動的なフォントサイズ
            )
        }
    }
}
```

### 🍎 iOS - SwiftUI

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("レスポンシブタイトル")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("このカードはあらゆる画面サイズに適応します")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // 動的な幅
        .fxFrame(height: 200)          // 固定の高さ
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

### 📄 Android - XML Views

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="@dimen/_16sdp">
    
    <TextView
        android:layout_width="@dimen/_300sdp"
        android:layout_height="wrap_content"
        android:textSize="@dimen/_18ssp"
        android:text="レスポンシブテキスト" />
        
    <Button
        android:layout_width="@dimen/_120sdp"
        android:layout_height="@dimen/_48sdp"
        android:text="アクション" />
</LinearLayout>
```

---

## 🎨 高度な機能

### 🔄 条件付きスケーリング

```kotlin
// Android
val buttonSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

```swift
// iOS
let buttonSize = AppDimens.fixed(80)
    .screen(.watch, 40)           // Apple Watchの場合は40pt
    .screen(.tablet, 120)         // iPadの場合は120pt
    .aspectRatio(enable: true)    // アスペクト比調整を有効化
    .toPoints()
```

### 📏 物理単位

```kotlin
// Android
val marginPx = AppDimensPhysicalUnits.toMm(5f, resources)
view.setPadding(marginPx.toInt(), 0, 0, 0)
```

```swift
// iOS
Rectangle()
    .frame(width: 2.cm, height: 1.cm)  // 物理単位
```

### 🧮 レイアウトユーティリティ

```kotlin
// Android - 最適なグリッド列数を計算
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

---

## 📊 パフォーマンスと互換性

### ⚡ パフォーマンス特性

| 機能 | ランタイムオーバーヘッド | メモリ使用量 | 計算時間 |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | 構成ごとにキャッシュ |
| **SDP/SSP** | ゼロ | ~2MB（リソース） | 事前計算済み |
| **物理単位** | ~0.002ms | ~10KB | オンデマンド |

### 📱 プラットフォームサポート

| プラットフォーム | 最小バージョン | UIフレームワーク | 特別な機能 |
|----------|-------------|---------------|------------------|
| **Android** | API 21+ | Compose、Views、Data Binding | SDP/SSP、物理単位 |
| **iOS** | 13.0+ | SwiftUI、UIKit | ネイティブ拡張 |

---

## 📚 ドキュメントとリソース

### 📖 完全なドキュメント

- **[📘 完全なドキュメント](https://appdimens-project.web.app/)** - 包括的なガイドとAPIリファレンス
- **[🤖 Androidガイド](../../Android/README.md)** - Android固有のドキュメント
- **[🍎 iOSガイド](../../iOS/README.md)** - iOS固有のドキュメント
- **[🎮 ゲームモジュール](../../Android/appdimens_games/README.md)** - C++/NDKを使用したゲーム開発

### 🎯 クイックリンク

- **[🚀 インストールガイド](../../Android/README.md#installation)** - 数分で開始
- **[📱 例](../../Android/app/src/main/kotlin/)** - 実世界での使用例
- **[🔧 APIリファレンス](../../Android/DOCS/)** - 完全なAPIドキュメント
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - よくある質問と回答

---

## 🎯 ユースケース

### 📱 モバイルアプリ
異なるスマートフォンサイズと向きで動作する必要があるアプリに最適です。

### 📺 テレビと車のアプリ
さまざまな画面サイズを持つAndroid TVとAndroid Autoアプリケーションに理想的です。

### ⌚ ウェアラブルアプリ
異なる時計サイズに適応する必要があるWear OSアプリに不可欠です。

### 🎮 ゲーム開発
C++/NDKサポートとOpenGL統合を備えたゲーム開発用の専門モジュール。

### 🏢 エンタープライズアプリ
タブレット、スマートフォン、デスクトップで動作する必要があるビジネスアプリケーションに最適です。

---

## 🤝 貢献

貢献を歓迎します！詳細については、[貢献ガイドライン](CONTRIBUTING.md)をご覧ください。

### 🐛 バグを見つけましたか？
- [issueを作成](https://github.com/bodenberg/appdimens/issues)
- デバイス情報と再現手順を含める
- 該当する場合はスクリーンショットを添付

### 💡 アイデアがありますか？
- [ディスカッションを開始](https://github.com/bodenberg/appdimens/discussions)
- 新機能や改善を提案
- ユースケースを共有

---

## 📄 ライセンス

このプロジェクトはApache License 2.0の下でライセンスされています - 詳細については[LICENSE](../../LICENSE)ファイルをご覧ください。

---

## 👨‍💻 著者

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 サポートを示す

AppDimensがあなたのプロジェクトに役立った場合は、以下をご検討ください：

- ⭐ このリポジトリに**スターを付ける**
- 🐦 ソーシャルメディアで**共有する**
- 📝 レビューやブログ投稿を**書く**
- 🤝 コードやドキュメントで**貢献する**

---

<div align="center">
    <p><strong>モバイル開発コミュニティのために❤️を込めて作成</strong></p>
    <p>AppDimens - レスポンシブデザインと数学的精度が出会う場所</p>
</div>
