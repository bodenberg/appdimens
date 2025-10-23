<div align="center">
    <h1>🎮 AppDimens Games</h1>
    <p><strong>Android 向け C++/NDK 対応のレスポンシブなゲーム開発</strong></p>
    <p>OpenGL ES ユーティリティ、ビューポート管理、ゲーム用寸法計算を備えた専用モジュール。</p>

[![バージョン](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![ライセンス](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![プラットフォーム](https://img.shields.io/badge/platform-Android%2023+-orange.svg)](https://developer.android.com/)
[![NDK](https://img.shields.io/badge/NDK-r21+-green.svg)](https://developer.android.com/ndk)
</div>

> 言語: [English](../../../../Android/appdimens_games/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_games/README.md) | [Español](../../es/Android/appdimens_games/README.md) | [हिन्दी](../../hi/Android/appdimens_games/README.md) | [Русский](../../ru/Android/appdimens_games/README.md) | [中文](../../zh/Android/appdimens_games/README.md)

---

## 🎯 概要
- ゲーム UI／ゲームワールド／オーバーレイ向けの寸法計算
- C++/NDK 連携と OpenGL ES ユーティリティ
- ビューポート管理とパフォーマンスモニタリング

## 🚀 インストール
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-games:1.0.8") }
```

## 🎨 使い方（Kotlin）
```kotlin
val button = appDimensGames.calculateButtonSize(48f)
val player = appDimensGames.calculatePlayerSize(64f)
```

## 🧩 C++（NDK）
```cpp
float size = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
```

## 📚 参考
- ドキュメント: `Android/DOCS/`
- ライセンス: `LICENSE`
