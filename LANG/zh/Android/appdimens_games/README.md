<div align="center">
    <h1>🎮 AppDimens Games</h1>
    <p><strong>面向 Android 的响应式游戏开发（支持 C++/NDK）</strong></p>
    <p>专用模块，包含 OpenGL ES 工具、视口管理与游戏维度计算。</p>

[![版本](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![许可证](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![平台](https://img.shields.io/badge/platform-Android%2023+-orange.svg)](https://developer.android.com/)
[![NDK](https://img.shields.io/badge/NDK-r21+-green.svg)](https://developer.android.com/ndk)
</div>

> 语言: [English](../../../../Android/appdimens_games/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_games/README.md) | [Español](../../es/Android/appdimens_games/README.md) | [हिन्दी](../../hi/Android/appdimens_games/README.md) | [Русский](../../ru/Android/appdimens_games/README.md) | [日本語](../../ja/Android/appdimens_games/README.md)

---

## 🎯 概述
- 针对游戏 UI、游戏世界与叠加层的尺寸计算
- C++/NDK 集成与 OpenGL ES 实用工具
- 视口管理与性能监控

## 🚀 安装
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-games:1.0.6") }
```

## 🎨 用法（Kotlin）
```kotlin
val button = appDimensGames.calculateButtonSize(48f)
val player = appDimensGames.calculatePlayerSize(64f)
```

## 🧩 C++（NDK）
```cpp
float size = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
```

## 📚 参考
- 文档：`Android/DOCS/`
- 许可证：`LICENSE`
