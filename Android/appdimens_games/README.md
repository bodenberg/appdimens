# AppDimens Games Module - C++/NDK Game Development

**High-Performance Scaling for Android Games**  
*Version: 2.0.1*

---

## 📦 Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-games:2.0.1")
}
```

**Note:** Separate from appdimens-all (not included)

---

## 🎮 Features

- ✅ **Native C++/NDK** for maximum performance
- ✅ **13 Scaling Strategies** with aspect ratio support
- ✅ **Aspect Ratio Adjustment** (5 strategies: BALANCED, LOGARITHMIC, POWER, INTERPOLATED, FLUID)
- ✅ **4 Dimension Types** (DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY)
- ✅ **Vector2D & Rectangle** utilities
- ✅ **OpenGL ES** integration
- ✅ **Physical Units** (mm, cm, inch)
- ✅ **Performance Monitoring** (60+ FPS optimized)

---

## ⚡ Quick Start

```kotlin
val games = AppDimensGames.getInstance()
games.initialize(context)

// UI elements
val buttonSize = games.calculateButtonSize(48f)

// Game world
val playerSize = games.calculatePlayerSize(64f)
val enemySize = games.calculateEnemySize(32f)

// Vectors
val position = GameVector2D(100f, 200f)
val scaled = games.calculateVector2D(position, GameDimensionType.GAME_WORLD)

// Physical units
val touchTarget = games.cm(2f)  // 2cm for touch
```

---

## 📚 Documentation

- [Complete Games Guide](IMPLEMENTATION_SUMMARY.md)
- [Main Android Guide](../README.md)
- [Main Documentation](../../DOCS/README.md)

---

**License:** Apache 2.0
