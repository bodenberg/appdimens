---
layout: default
title: "🎮 AppDimens Games - Desenvolvimento de Jogos Android"
---

# 🎮 AppDimens Games - Desenvolvimento de Jogos Android

> **Integração C++/NDK para Desenvolvimento de Jogos de Alta Performance**

O módulo AppDimens Games fornece implementação nativa C++ para desenvolvimento de jogos de alta performance no Android, com escala de dimensão especializada, gerenciamento de viewport e integração OpenGL ES.

## 🚀 Início Rápido

### 1. Adicionar Dependência

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-games:1.1.0")
}
```

### 2. Inicializar Gerenciador de Jogos

```kotlin
class GameActivity : AppCompatActivity() {
    private lateinit var gamesManager: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar gerenciador de jogos
        gamesManager = AppDimensGames.getInstance()
        gamesManager.initialize(this)
        
        // Configurar configurações de performance
        gamesManager.configurePerformance(
            GamePerformanceSettings.HIGH_PERFORMANCE
        )
    }
}
```

### 3. Usar Dimensões de Jogo

```kotlin
// Calcular dimensões específicas do jogo
val buttonSize = gamesManager.calculateButtonSize(48f)
val playerSize = gamesManager.calculatePlayerSize(64f)
val enemySize = gamesManager.calculateEnemySize(32f)
val uiSize = gamesManager.calculateUISize(24f)
```

## 🏗️ Visão Geral da Arquitetura

### Implementação C++ Nativa

O módulo Games inclui código C++ nativo para máxima performance:

- **Sistema de Build CMake**: Compilação de biblioteca nativa
- **Integração JNI**: Ponte Java-Kotlin para C++
- **Utilitários OpenGL ES**: Suporte para renderização gráfica
- **Monitoramento de Performance**: Rastreamento de performance nativo

### Estrutura do Módulo

```
appdimens_games/
├── src/main/
│   ├── cpp/                    # Implementação C++ nativa
│   │   ├── include/           # Arquivos de cabeçalho
│   │   │   ├── AppDimensGames.h
│   │   │   ├── GameDimensions.h
│   │   │   ├── GameMath.h
│   │   │   ├── GameScaling.h
│   │   │   ├── ViewportManager.h
│   │   │   ├── OpenGLUtils.h
│   │   │   └── PerformanceMonitor.h
│   │   └── src/               # Arquivos fonte
│   │       ├── AppDimensGames.cpp
│   │       ├── GameDimensions.cpp
│   │       ├── GameMath.cpp
│   │       ├── GameScaling.cpp
│   │       ├── ViewportManager.cpp
│   │       ├── OpenGLUtils.cpp
│   │       └── PerformanceMonitor.cpp
│   ├── java/                  # Interface Kotlin/Java
│   │   └── com/appdimens/games/
│   │       ├── AppDimensGames.kt
│   │       ├── AppDimensGames3D.kt
│   │       └── GameActivity.kt
│   └── CMakeLists.txt         # Configuração CMake
```

## 🎯 Tipos de Dimensão de Jogo

### Categorias de Dimensão

| Tipo | Caso de Uso | Comportamento de Escala | Performance |
|------|-------------|------------------------|-------------|
| **DYNAMIC** | Containers, layouts fluidos | Escala proporcional | ~0.0005ms |
| **FIXED** | Elementos de UI, botões | Escala logarítmica | ~0.0005ms |
| **GAME_WORLD** | Jogador, inimigos, objetos | Escala de coordenadas do mundo | ~0.0005ms |
| **UI_OVERLAY** | HUD, menus, texto | Escala específica da UI | ~0.0005ms |

### Exemplos de Uso

```kotlin
// Dimensões do mundo do jogo
val playerSize = gamesManager.calculateDimension(64f, GameDimensionType.GAME_WORLD)
val enemySize = gamesManager.calculateDimension(32f, GameDimensionType.GAME_WORLD)

// Dimensões de sobreposição da UI
val buttonSize = gamesManager.calculateDimension(48f, GameDimensionType.UI_OVERLAY)
val textSize = gamesManager.calculateDimension(16f, GameDimensionType.UI_OVERLAY)

// Dimensões de container
val containerWidth = gamesManager.calculateDimension(320f, GameDimensionType.DYNAMIC)
val containerHeight = gamesManager.calculateDimension(240f, GameDimensionType.DYNAMIC)
```

## 🎮 Funcionalidades de Desenvolvimento de Jogos

### Operações Vetoriais

```kotlin
// Operações vetoriais 2D
val vector1 = GameVector2D(100f, 50f)
val vector2 = GameVector2D(25f, 75f)

val sum = vector1 + vector2           // Adição
val difference = vector1 - vector2    // Subtração
val scaled = vector1 * 2f             // Multiplicação escalar
val length = vector1.length()         // Comprimento do vetor
val normalized = vector1.normalized() // Vetor normalizado
```

### Operações de Retângulo

```kotlin
// Operações de retângulo
val rect1 = GameRectangle(0f, 0f, 100f, 50f)
val rect2 = GameRectangle(50f, 25f, 100f, 50f)

val center = rect1.center()                    // Ponto central
val contains = rect1.contains(GameVector2D(25f, 25f))  // Contenção de ponto
val intersection = rect1.intersection(rect2)   // Interseção de retângulos
```

### Gerenciamento de Viewport

```kotlin
// Modos de escala de viewport
enum class GameViewportMode {
    FIT_WIDTH,      // Ajustar à largura da tela
    FIT_HEIGHT,     // Ajustar à altura da tela
    FIT_ALL,        // Ajustar todo o conteúdo
    STRETCH,        // Esticar para preencher a tela
    CROP            // Cortar para manter proporção
}

// Calcular dimensões do viewport
val viewportWidth = gamesManager.calculateDimension(320f, GameViewportMode.FIT_WIDTH)
val viewportHeight = gamesManager.calculateDimension(240f, GameViewportMode.FIT_HEIGHT)
```

## ⚡ Otimização de Performance

### Configurações de Performance

```kotlin
// Configurações padrão (balanceadas)
GamePerformanceSettings.DEFAULT

// Alta performance (120 FPS)
GamePerformanceSettings.HIGH_PERFORMANCE

// Baixa performance (30 FPS, jogos simples)
GamePerformanceSettings.LOW_PERFORMANCE

// Configurações personalizadas
val customSettings = GamePerformanceSettings(
    enableCaching = true,
    cacheSize = 2000,
    enableAsyncCalculation = true,
    maxCalculationTime = 8L  // 8ms para 120 FPS
)
```

### Estratégia de Cache

```kotlin
// Ativar/desativar cache
gamesManager.configurePerformance(
    GamePerformanceSettings(enableCaching = true)
)

// Limpar cache quando necessário
gamesManager.clearCache()

// Cache é gerenciado automaticamente baseado em mudanças de configuração de tela
```

## 🎨 Integração OpenGL ES

### Utilitários OpenGL

O módulo Games inclui utilitários OpenGL ES para renderização gráfica:

```cpp
// Utilitários OpenGL C++ (código nativo)
class OpenGLUtils {
public:
    static void setupViewport(int width, int height);
    static void calculateProjectionMatrix(float* matrix, float fov, float aspect, float near, float far);
    static void convertScreenToNDC(float screenX, float screenY, float& ndcX, float& ndcY);
};
```

### Conversão de Coordenadas

```kotlin
// Conversão de tela para NDC
val screenPoint = GameVector2D(100f, 50f)
val ndcPoint = gamesManager.screenToNDC(screenPoint)

// Conversão de NDC para tela
val ndcPoint = GameVector2D(0.5f, 0.5f)
val screenPoint = gamesManager.ndcToScreen(ndcPoint)
```

## 📱 Configuração de Tela

### Detecção Automática de Tela

{% raw %}
```kotlin
// Configuração de tela é detectada automaticamente
gamesManager.updateScreenConfiguration()

// Obter configuração atual da tela
val screenConfig = gamesManager.getScreenConfig()
screenConfig?.let { config ->
    Log.i("Games", "Tela: ${config.width}x${config.height}")
    Log.i("Games", "Densidade: ${config.density}")
    Log.i("Games", "Orientação: ${config.orientation}")
    Log.i("Games", "É Tablet: ${config.isTablet}")
}
```
{% endraw %}

### Orientação da Tela

```kotlin
enum class GameScreenOrientation {
    PORTRAIT,
    LANDSCAPE,
    AUTO
}

// Lidar com mudanças de orientação
override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    gamesManager.updateScreenConfiguration()
}
```

## 🔧 Uso Avançado

### Atividade de Jogo Personalizada

```kotlin
class MyGameActivity : GameActivity() {
    private lateinit var gamesManager: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar gerenciador de jogos
        gamesManager = AppDimensGames.getInstance()
        gamesManager.initialize(this)
        
        // Configurar para seu jogo
        gamesManager.configurePerformance(
            GamePerformanceSettings.HIGH_PERFORMANCE
        )
    }
    
    override fun onResume() {
        super.onResume()
        // Atualizar configuração da tela
        gamesManager.updateScreenConfiguration()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Limpar recursos
        gamesManager.shutdown()
    }
}
```

### Integração com Engines de Jogo

```kotlin
// Exemplo de integração Unity
class UnityGameManager {
    private val gamesManager = AppDimensGames.getInstance()
    
    fun initialize(context: Context) {
        gamesManager.initialize(context)
        gamesManager.configurePerformance(
            GamePerformanceSettings.HIGH_PERFORMANCE
        )
    }
    
    fun calculateUIScale(baseSize: Float): Float {
        return gamesManager.calculateUISize(baseSize)
    }
    
    fun calculateWorldScale(baseSize: Float): Float {
        return gamesManager.calculatePlayerSize(baseSize)
    }
}
```

## 🐛 Solução de Problemas

### Problemas Comuns

1. **Falha no Carregamento da Biblioteca Nativa**
   ```
   Solução: Certifique-se de que seu app tem suporte à arquitetura correta (arm64-v8a, armeabi-v7a, x86, x86_64)
   ```

2. **Problemas de Performance**
   ```
   Solução: Configure GamePerformanceSettings apropriadas para os requisitos do seu jogo
   ```

3. **Configuração de Tela Não Atualizando**
   ```
   Solução: Chame updateScreenConfiguration() em onConfigurationChanged()
   ```

### Informações de Debug

{% raw %}
```kotlin
// Habilitar log de debug
Log.i("AppDimensGames", "Gerenciador de jogos inicializado: ${gamesManager.isInitialized()}")
Log.i("AppDimensGames", "Config da tela: ${gamesManager.getScreenConfig()}")
Log.i("AppDimensGames", "Configurações de performance: ${gamesManager.getPerformanceSettings()}")
```
{% endraw %}

## 📚 Referência da API

### Classes Principais

- **[AppDimensGames](MARKDOWN/appdimens_games/com.appdimens.games/-app-dimens-games/)** - Gerenciador principal de jogos
- **[GameDimensionType](MARKDOWN/appdimens_games/com.appdimens.games/-game-dimension-type/)** - Tipos de dimensão
- **[GameVector2D](MARKDOWN/appdimens_games/com.appdimens.games/-game-vector2-d/)** - Operações vetoriais 2D
- **[GameRectangle](MARKDOWN/appdimens_games/com.appdimens.games/-game-rectangle/)** - Operações de retângulo
- **[GameScreenConfig](MARKDOWN/appdimens_games/com.appdimens.gizations/-game-screen-config/)** - Configuração da tela
- **[GamePerformanceSettings](MARKDOWN/appdimens_games/com.appdimens.games/-game-performance-settings/)** - Configurações de performance

### API C++ Nativa

- **[AppDimensGames.h](../../appdimens_games/src/main/cpp/include/AppDimensGames.h)** - Interface nativa principal
- **[GameDimensions.h](../../appdimens_games/src/main/cpp/include/GameDimensions.h)** - Cálculos de dimensão
- **[OpenGLUtils.h](../../appdimens_games/src/main/cpp/include/OpenGLUtils.h)** - Utilitários OpenGL

## 🔗 Documentação Relacionada

- **[Documentação Principal de Jogos](../README.md)** - Referência completa da API
- **[Biblioteca Principal](../LIBRARY/)** - Tipos e interfaces base
- **[Módulo Dynamic](../DYNAMIC/)** - Escala Dynamic/Fixed
- **[Exemplos](../../app/src/main/kotlin/)** - Exemplos de uso

---

**Última Atualização**: 2025-01-27  
**Versão**: 1.1.0  
**Implementação Nativa**: C++/NDK com suporte OpenGL ES