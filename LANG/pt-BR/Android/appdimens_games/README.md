<div align="center">
    <h1>🎮 AppDimens Games</h1>
    <p><strong>Desenvolvimento de jogos responsivo para Android com C++/NDK</strong></p>
    <p>Módulo especializado com OpenGL ES, gerenciamento de viewport e cálculos de dimensões para jogos.</p>

[![Versão](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licença](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2023+-orange.svg)](https://developer.android.com/)
[![NDK](https://img.shields.io/badge/NDK-r21+-green.svg)](https://developer.android.com/ndk)
</div>

> Idiomas: [English](../../../../Android/appdimens_games/README.md) | [Español](../../es/Android/appdimens_games/README.md) | [हिन्दी](../../hi/Android/appdimens_games/README.md) | [Русский](../../ru/Android/appdimens_games/README.md) | [中文](../../zh/Android/appdimens_games/README.md) | [日本語](../../ja/Android/appdimens_games/README.md)

---

## 🎯 Visão Geral
- Cálculos para UI de jogo (botões, texto), mundo do jogo e overlays
- Integração C++/NDK e utilitários OpenGL ES
- Gerenciamento de viewport e monitor de performance

## 🚀 Instalação
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-games:1.0.5") }
```

## 🎨 Uso (Kotlin)
```kotlin
val button = appDimensGames.calculateButtonSize(48f)
val player = appDimensGames.calculatePlayerSize(64f)
```

## 🧩 C++ (NDK)
```cpp
float size = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
```

## 📚 Referências
- Documentação: `Android/DOCS/`
- Licença: `LICENSE`
