# AppDimens Games - Módulo Android

**Desenvolvimento de Jogos de Alta Performance com C++/NDK**  
*Versão: 2.0.1*

---

## 📦 Instalação

```kotlin
implementation("io.github.bodenberg:appdimens-games:2.0.1")
```

---

## 🎮 Uso

```kotlin
val games = AppDimensGames.getInstance()
games.initialize(context)

val buttonSize = games.calculateButtonSize(48f)
val playerSize = games.calculatePlayerSize(64f)
```

---

**Guia:** [../../../../appdimens-games/appdimens_games/README.md](../../../../appdimens-games/appdimens_games/README.md)
