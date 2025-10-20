<div align="center">
    <h1>🎮 AppDimens Games</h1>
    <p><strong>Адаптивная разработка игр для Android с поддержкой C++/NDK</strong></p>
    <p>Специализированный модуль с OpenGL ES, управлением вьюпортом и игровыми расчетами размеров.</p>

[![Версия](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Лицензия](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Платформа](https://img.shields.io/badge/platform-Android%2023+-orange.svg)](https://developer.android.com/)
[![NDK](https://img.shields.io/badge/NDK-r21+-green.svg)](https://developer.android.com/ndk)
</div>

> Языки: [English](../../../../Android/appdimens_games/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_games/README.md) | [Español](../../es/Android/appdimens_games/README.md) | [हिन्दी](../../hi/Android/appdimens_games/README.md) | [中文](../../zh/Android/appdimens_games/README.md) | [日本語](../../ja/Android/appdimens_games/README.md)

---

## 🎯 Обзор
- Расчеты размеров для UI, мира игры и оверлеев
- Интеграция C++/NDK и утилиты OpenGL ES
- Управление вьюпортом и мониторинг производительности

## 🚀 Установка
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-games:1.0.6") }
```

## 🎨 Использование (Kotlin)
```kotlin
val button = appDimensGames.calculateButtonSize(48f)
val player = appDimensGames.calculatePlayerSize(64f)
```

## 🧩 C++ (NDK)
```cpp
float size = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
```

## 📚 Справка
- Документация: `Android/DOCS/`
- Лицензия: `LICENSE`
