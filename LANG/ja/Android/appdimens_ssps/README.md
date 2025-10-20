<div align="center">
    <h1>📐 AppDimens SSP</h1>
    <p><strong>条件付きルールによるダイナミックなテキストスケーリング</strong></p>
    <p>SSP（Scaled SP）。優先度ベースの条件ルールで、Compose と XML のレスポンシブなタイポグラフィを実現。</p>

[![バージョン](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![ライセンス](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![プラットフォーム](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> 言語: [English](../../../../Android/appdimens_ssps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_ssps/README.md) | [Español](../../es/Android/appdimens_ssps/README.md) | [हिन्दी](../../hi/Android/appdimens_ssps/README.md) | [Русский](../../ru/Android/appdimens_ssps/README.md) | [中文](../../zh/Android/appdimens_ssps/README.md)

---

## 🎯 概要
- テキスト拡張: `.ssp`, `.hsp`, `.wsp`（アクセシビリティ対応）、`.sem/.hem/.wem`（非対応）
- UiMode + Qualifier による優先度ベースの条件ルール

## 🚀 インストール
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-ssps:1.0.6") }
```

## 🎨 例（Compose）
```kotlin
Text("タイトル", fontSize = 24.ssp)
Text("UI ラベル", fontSize = 12.sem)
```

## 📄 XML
```xml
<TextView android:textSize="@dimen/_16ssp" />
```

## 📚 参考
- ドキュメント: `Android/DOCS/`
- ライセンス: `LICENSE`
