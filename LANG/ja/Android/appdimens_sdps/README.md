---
layout: default
---

<div align="center">
    <h1>📐 AppDimens SDP</h1>
    <p><strong>条件付きルールによるダイナミックなスケーリング</strong></p>
    <p>優先度ベースの条件ルールで、Compose と XML レイアウトのレスポンシブなスケーリングを実現する SDP（Scaled DP）。</p>

[![バージョン](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![ライセンス](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![プラットフォーム](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> 言語: [English](../../../../Android/appdimens_sdps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_sdps/README.md) | [Español](../../es/Android/appdimens_sdps/README.md) | [हिन्दी](../../hi/Android/appdimens_sdps/README.md) | [Русский](../../ru/Android/appdimens_sdps/README.md) | [中文](../../zh/Android/appdimens_sdps/README.md)

---

## 🎯 概要
- 直接拡張: `.sdp`, `.hdp`, `.wdp`
- 優先度に基づく条件ルール（UiMode + Qualifier）
- 事前計算リソース使用時は実行時オーバーヘッドゼロ

## 🚀 インストール
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-sdps:1.0.8") }
```

## 🎨 例（Compose）
```kotlin
Column(Modifier.padding(16.sdp)) {
    Text("テキスト", fontSize = 18.ssp)
    Spacer(Modifier.height(18.sdp).width(100.wdp))
    Card(Modifier.size(120.sdp).padding(8.hdp)) { }
}
```

### 条件ルール
```kotlin
val boxSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

## 📄 XML
```xml
<TextView
    android:layout_width="@dimen/_100sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp" />
```

## 📐 修飾子（Qualifier）
- SDP: 最小幅（sw）
- WDP: 幅（w）
- HDP: 高さ（h）

## ⚡ パフォーマンス
- 拡張: オーバーヘッドなし
- ルール: ~0.001ms（構成ごとにキャッシュ）

## 📚 参考
- ドキュメント: `Android/DOCS/`
- ライセンス: `LICENSE`
