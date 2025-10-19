<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Android 向けコアのレスポンシブ寸法システム</strong></p>
    <p>Jetpack Compose・Views/XML・Data Binding に対応した Fixed/Dynamic モデル。</p>

[![バージョン](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![ライセンス](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![プラットフォーム](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> 言語: [English](../../../../Android/appdimens_dynamic/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_dynamic/README.md) | [Español](../../es/Android/appdimens_dynamic/README.md) | [हिन्दी](../../hi/Android/appdimens_dynamic/README.md) | [Русский](../../ru/Android/appdimens_dynamic/README.md) | [中文](../../zh/Android/appdimens_dynamic/README.md)

---

## 🎯 概要
- **Fixed (FX)**: 対数的で穏やかな調整。ボタン/パディング/アイコン/フォント向け
- **Dynamic (DY)**: 画面に対する比率。コンテナの幅高さ・グリッド向け

## 🚀 インストール
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-dynamic:1.0.5") }
```

## 🎨 例（Compose）
```kotlin
Text("タイトル", fontSize = 24.fxsp)
Button(modifier = Modifier.height(48.fxdp).width(120.fxdp)) { Text("実行") }
Card(modifier = Modifier.fillMaxWidth().height(100.dydp).padding(8.fxdp)) { }
```

## 📄 XML/Data Binding
`app:dynamicWidthDp`, `app:dynamicHeightDp`, `app:dynamicTextSizeDp`。

## 📏 物理単位
`5.0f.toDp(UnitType.MM)`, `2.0f.toSp(UnitType.CM)`, `1.0f.toPx(UnitType.INCH)`

## 🧮 レイアウトユーティリティ
`AppDimens.CalculateAvailableItemCount(...)`

## 📚 参考
- ドキュメント: `Android/DOCS/`
- ライセンス: `LICENSE`
