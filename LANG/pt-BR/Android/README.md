---
layout: default
---

<div align="center">
    <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens Android - Design Responsivo" height="250"/>
    <h1>📐 AppDimens Android</h1>
    <p><strong>Dimensionamento Inteligente e Responsivo para Android</strong></p>
    <p>Escala responsiva matematicamente precisa que garante que seu design de UI se adapte perfeitamente a qualquer tamanho de tela ou proporção — de telefones a TVs, carros e wearables.</p>

[![Versão](https://img.shields.io/badge/version-1.1.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licença](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
[![Documentação](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

> **Idiomas:** [English](../../../Android/README.md) | Português (BR) | [Español](../../es/Android/README.md)

---

## 🎯 O que é AppDimens Android?

**AppDimens Android** é um sistema abrangente de dimensionamento que substitui valores fixos de DP por dimensões escaladas de forma inteligente com base nas características reais da tela. Enquanto o DP padrão do Android (1 DP = 1/160 polegada) é constante, o AppDimens o trata como um valor base que escala de forma previsível em diferentes tamanhos de tela, densidades e proporções.

### 🎨 Benefícios Principais

- **🎯 Consistência Visual**: Mantenha proporções perfeitas em todos os tipos de dispositivos Android
- **📱 Compatibilidade Universal**: Funciona perfeitamente em telefones, tablets, TVs, carros e wearables
- **⚡ Performance Otimizada**: Overhead mínimo de runtime com cálculos em cache
- **🔧 Integração Fácil**: API simples que funciona com Jetpack Compose, XML Views e Data Binding
- **📐 Precisão Matemática**: Três modelos de escala - Fixed (RECOMENDADO), Dynamic e Fluid (Compose apenas)
- **🎮 Desenvolvimento de Jogos**: Módulo C++/NDK especializado para desenvolvimento de jogos de alta performance
- **🚀 Performance Nativa**: Implementação C++ para cálculos específicos de jogos e integração OpenGL
- **🆕 v1.2.0**: Suporte a Base Orientation para auto-adaptação à rotação
- **🌊 v1.0.10**: Modelo Fluid para escalamento suave com limites definidos (Compose apenas)
- **⚡ Cache Global**: Controle de cache global e por instância
- **📏 Unidades Físicas**: mm, cm, polegadas em code, compose e games

---

## 🎮 Recursos de Desenvolvimento de Jogos

### Módulo AppDimens Games
O módulo **AppDimens Games** fornece funcionalidade especializada para desenvolvimento de jogos Android com suporte nativo C++/NDK:

#### Recursos Principais:
- **Performance Nativa**: Implementação C++ para cálculos de alta performance
- **Tipos de Dimensão de Jogo**: 
  - `DYNAMIC`: Escala proporcional para containers
  - `FIXED`: Escala logarítmica para elementos de UI
  - `GAME_WORLD`: Coordenadas consistentes do mundo do jogo
  - `UI_OVERLAY`: Coordenadas de sobreposição da UI
- **Operações Vetoriais**: `GameVector2D` com operações matemáticas (adição, subtração, multiplicação, normalização)
- **Utilitários de Retângulo**: `GameRectangle` para gerenciamento de limites e viewport
- **Integração OpenGL**: Utilitários para renderização OpenGL ES
- **Monitoramento de Performance**: Métricas de performance em tempo real e otimização

#### Exemplo de Uso:
```kotlin
val appDimensGames = AppDimensGames.getInstance()
appDimensGames.initialize(context)

// Dimensões específicas do jogo
val buttonSize = appDimensGames.calculateButtonSize(48f)
val playerSize = appDimensGames.calculatePlayerSize(64f)
val uiOverlaySize = appDimensGames.calculateUISize(24f)

// Operações vetoriais
val position = GameVector2D(100f, 200f)
val scaledPosition = appDimensGames.calculateVector2D(position, GameDimensionType.GAME_WORLD)
```

---

## 🚀 Instalação

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    // Biblioteca principal (escala Dynamic + Fixed)
    implementation("io.github.bodenberg:appdimens-dynamic:1.1.0")
    
    // Opcional: Escala SDP & SSP
    implementation("io.github.bodenberg:appdimens-sdps:1.1.0")
    implementation("io.github.bodenberg:appdimens-ssps:1.1.0")
    
    // Pacote completo (não inclui módulo de jogos)
    implementation("io.github.bodenberg:appdimens-all:1.1.0")
    
    // Desenvolvimento de jogos com suporte C++/NDK (dependência separada)
    implementation("io.github.bodenberg:appdimens-games:1.1.0")
}
```

### Gradle (Groovy)

```groovy
dependencies {
    implementation 'io.github.bodenberg:appdimens-dynamic:1.1.0'
    implementation 'io.github.bodenberg:appdimens-sdps:1.1.0'
    implementation 'io.github.bodenberg:appdimens-ssps:1.1.0'
    implementation 'io.github.bodenberg:appdimens-all:1.1.0'
    implementation 'io.github.bodenberg:appdimens-games:1.1.0'
}
```

### Configuração do Repositório

```kotlin
repositories {
    mavenCentral()
    // ou
    maven { url = uri("https://jitpack.io") }
}
```

---

## 🧠 Modelos de Dimensão Core

| Modelo | Filosofia | Caso de Uso Ideal | Suportado Em |
|--------|-----------|-------------------|--------------|
| **Fixed (FX)** | Escala logarítmica (refinada) | Botões, paddings, margens, ícones | Compose + Views + Data Binding |
| **Dynamic (DY)** | Escala proporcional (agressiva) | Containers, grids, fontes fluidas | Compose + Views + Data Binding |
| **SDP / SSP** | Recursos pré-calculados | Uso direto de `@dimen` em XML | Compose + Views (XML) |
| **Unidades Físicas** | mm/cm/polegada → Dp/Sp/Px | Wearables, impressão, layouts de precisão | Compose + Views |

---

## 🎨 Exemplos de Uso

### 🧩 Jetpack Compose

#### Escala Fixed e Dynamic

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.dydp)           // Largura dinâmica - proporcional à tela
            .height(200.fxdp)          // Altura fixa - escala refinada
            .padding(16.fxdp)          // Padding fixo - sensação consistente
    ) {
        Column(
            modifier = Modifier.padding(16.fxdp)
        ) {
            Text(
                text = "Título Responsivo",
                fontSize = 18.fxsp     // Tamanho de fonte fixo - leitura confortável
            )
            Text(
                text = "Este card se adapta a qualquer tamanho de tela",
                fontSize = 14.dysp     // Tamanho de fonte dinâmico - proporcional
            )
        }
    }
}
```

#### SDP e SSP em Compose

```kotlin
@Composable
fun SDPExample() {
    Box(
        modifier = Modifier
            .padding(16.sdp)           // Padding SDP
            .size(100.sdp)             // Tamanho SDP
    ) {
        Text(
            text = "Texto Responsivo",
            fontSize = 18.ssp          // Tamanho de fonte SSP
        )
    }
}
```

### 📄 XML Views e Data Binding

#### Escala Dynamic com Data Binding

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    app:dynamicWidthDp="@{100f}"
    app:dynamicHeightDp="@{56f}"
    app:dynamicTextSizeDp="@{20f}">
    
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Tamanho de Texto Dinâmico"
        app:dynamicTextSizeDp="@{18f}" />
</LinearLayout>
```

#### SDP e SSP em XML

```xml
<TextView
    android:layout_width="@dimen/_100sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp"
    android:text="Exemplo de Texto Responsivo"/>
```

### 💻 Código Kotlin/Java

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Escala fixa - ajuste sutil
        val fixedWidthPx = AppDimens.fixedPx(
            value = 200f,
            screenType = ScreenType.LOWEST,
            resources = resources
        ).toInt()
        
        // Escala dinâmica - ajuste proporcional
        val dynamicTextSizeSp = AppDimens.dynamicSp(
            value = 18f,
            screenType = ScreenType.LOWEST,
            resources = resources
        )
        
        // Aplicar às views
        myView.layoutParams.width = fixedWidthPx
        myTextView.textSize = dynamicTextSizeSp
    }
}
```

---

## 🔧 Recursos Avançados

### 🔄 Escala Condicional

```kotlin
@Composable
fun ConditionalScaling() {
    val boxSize = 80.scaledDp()
        .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
        .screen(UiModeType.CAR, 120.dp)
        .screen(DpQualifier.SMALL_WIDTH, 720, 150)
    
    Box(
        modifier = Modifier
            .size(boxSize.sdp)
            .background(Color.Blue)
    )
}
```

### 📏 Unidades Físicas

```kotlin
@Composable
fun PhysicalUnits() {
    // 5 milímetros convertidos para Dp
    val marginDp = 5.0f.toDp(UnitType.MM)
    
    Box(
        modifier = Modifier
            .padding(marginDp)
            .size(1.0f.toDp(UnitType.INCH)) // 1 polegada em Dp
    )
}
```

### 🧮 Utilitários de Layout

```kotlin
@Composable
fun ResponsiveGrid() {
    var spanCount by remember { mutableStateOf(3) }
    
    // Calcular número ótimo de colunas
    AppDimens.CalculateAvailableItemCount(
        itemSize = 100.dp,
        itemPadding = 4.dp,
        direction = DpQualifier.WIDTH,
        modifier = Modifier.fillMaxWidth(),
        onResult = { count ->
            if (count > 0) spanCount = count
        }
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(spanCount),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(20) { index ->
            // Seus itens de grid
        }
    }
}
```

---

## 📊 Visão Geral dos Módulos

### 🎯 Módulos Core

| Módulo | Descrição | Caso de Uso |
|--------|-----------|-------------|
| **appdimens-dynamic** | Biblioteca principal com escala Fixed & Dynamic | Essencial para todos os apps responsivos |
| **appdimens-sdps** | Escala SDP com lógica condicional | Design responsivo baseado em XML |
| **appdimens-ssps** | Escala SSP com lógica condicional | Dimensionamento de texto responsivo |
| **appdimens-all** | Pacote completo com todos os módulos (exceto jogos) | Solução completa para apps padrão |
| **appdimens-games** | Desenvolvimento de jogos com C++/NDK | Desenvolvimento de jogos Android |

### 🎮 Recursos do Módulo Games

- **Integração C++/NDK**: Performance nativa para engines de jogos
- **Suporte OpenGL ES**: Utilitários para renderização OpenGL ES 2.0/3.0
- **Gerenciamento de Viewport**: Sistemas avançados de câmera e viewport
- **Monitoramento de Performance**: Métricas de performance em tempo real
- **Escala Específica de Jogos**: Cálculos pré-definidos para elementos de jogo

#### 🎮 Uso do Módulo Games

O módulo Games fornece funcionalidade especializada para desenvolvimento de jogos Android com suporte C++/NDK. É uma dependência separada que não está incluída no pacote `appdimens-all`.

**Integração Básica:**

```kotlin
class GameActivity : Activity() {
    private lateinit var appDimensGames: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar AppDimens Games
        appDimensGames = AppDimensGames.getInstance()
        appDimensGames.initialize(this)
        
        // Calcular dimensões responsivas para elementos do jogo
        val buttonSize = appDimensGames.calculateButtonSize(48.0f)
        val textSize = appDimensGames.calculateTextSize(16.0f)
        val playerSize = appDimensGames.calculatePlayerSize(64.0f)
        val enemySize = appDimensGames.calculateEnemySize(32.0f)
    }
}
```

**Integração C++:**

```cpp
#include "AppDimensGames.h"

class GameEngine {
private:
    AppDimensGames& appDimensGames;
    
public:
    GameEngine(JNIEnv* env, jobject context) {
        appDimensGames = AppDimensGames::getInstance();
        appDimensGames.initialize(env, context);
    }
    
    void updateGame() {
        // Calcular dimensões para diferentes elementos do jogo
        float buttonSize = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
        float playerSize = appDimensGames.calculateDimension(64.0f, GameDimensionType::GAME_WORLD);
        
        // Trabalhar com vetores e retângulos
        Vector2D baseVector(100.0f, 50.0f);
        Vector2D scaledVector = appDimensGames.calculateVector2D(baseVector, GameDimensionType::DYNAMIC);
    }
};
```

**Tipos de Dimensão de Jogo:**
- **DYNAMIC**: Escala proporcional para containers e layouts fluidos
- **FIXED**: Escala logarítmica para elementos de UI e botões
- **GAME_WORLD**: Mantém coordenadas consistentes do mundo para objetos do jogo
- **UI_OVERLAY**: Para elementos HUD e sobreposição

Para documentação completa, veja [Módulo AppDimens Games](../../../Android/appdimens_games/README.md).

---

## 📱 Suporte a Dispositivos

### 📱 Tipos de Dispositivos Suportados

| Tipo de Dispositivo | Descrição | Comportamento de Escala |
|---------------------|-----------|------------------------|
| **Phone** | Telefones Android padrão | Escala balanceada |
| **Tablet** | Tablets Android | Escala aprimorada para telas maiores |
| **TV** | Dispositivos Android TV | Otimizado para distância de visualização |
| **Car** | Android Auto | Alvos de toque grandes |
| **Watch** | Dispositivos Wear OS | Escala compacta |
| **VR** | Headsets VR | Escala imersiva |

### 📐 Qualificadores de Tela

| Qualificador | Descrição | Caso de Uso |
|--------------|-----------|-------------|
| **SMALL_WIDTH** | Menor dimensão da tela | Padrão, mais restritivo |
| **WIDTH** | Largura da tela | Layouts horizontais |
| **HEIGHT** | Altura da tela | Layouts verticais |

---

## ⚡ Performance e Otimização

### 📊 Características de Performance

| Recurso | Overhead de Runtime | Uso de Memória | Tempo de Cálculo |
|---------|-------------------|----------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cache por configuração |
| **SDP/SSP** | Zero | ~2MB (recursos) | Pré-calculado |
| **Unidades Físicas** | ~0.002ms | ~10KB | Sob demanda |

### 🚀 Dicas de Otimização

1. **Cache de Dimensões**: Armazene dimensões frequentemente usadas em propriedades
2. **Use o Modelo Apropriado**: Fixed para elementos de UI, Dynamic para layouts
3. **Evite Aninhamento Excessivo**: Mantenha cadeias de dimensão simples
4. **Perfil de Performance**: Use monitoramento de performance integrado

---

## 🧪 Testes

### 📋 Cobertura de Testes

- ✅ Cálculos de dimensão
- ✅ Detecção de tipo de dispositivo
- ✅ Cálculos de fator de tela
- ✅ Métodos de extensão
- ✅ Casos extremos e tratamento de erros
- ✅ Benchmarks de performance

### 🔧 Ferramentas de Teste

{% raw %}
```kotlin
// Debug da configuração atual da tela
val (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
println("Tela: ${width} × ${height}")

// Debug do tipo de dispositivo
println("Dispositivo: ${DeviceType.current()}")

// Debug dos fatores de ajuste
val factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
println("Fatores: ${factors}")
```
{% endraw %}

---

## 📚 Documentação e Recursos

### 📖 Documentação Completa

- **[📘 Documentação Completa](https://appdimens-project.web.app/)** - Guias abrangentes e referência de API
- **[🎯 Documentação Core](../../../Android/DOCS/)** - Documentação técnica detalhada
- **[🎮 Módulo Games](../../../Android/appdimens_games/README.md)** - Guia de desenvolvimento de jogos
- **[📱 Exemplos](../../../Android/app/src/main/kotlin/)** - Exemplos de uso do mundo real

### 🔗 Links Rápidos

- **[🚀 Guia de Instalação](#instalação)** - Comece em minutos
- **[📱 Exemplos](#exemplos-de-uso)** - Exemplos de uso do mundo real
- **[🔧 Referência de API](../../../Android/DOCS/)** - Documentação completa da API
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Perguntas e respostas comuns

---

## 🏗️ Visão Geral da Arquitetura

### Estrutura da Biblioteca Android

| Módulo | Propósito | Dependências | Recursos Principais |
|--------|-----------|--------------|-------------------|
| **appdimens_library** | Tipos e interfaces core | Nenhuma | Enums base, qualificadores, fatores de ajuste |
| **appdimens_dynamic** | Escala Dynamic/Fixed | appdimens_library | Modelos DY/FX, extensões Compose, cache |
| **appdimens_sdps** | Escala SDP | appdimens_library | 426+ recursos @dimen pré-calculados |
| **appdimens_ssps** | Escala SSP | appdimens_library | 216+ recursos @dimen pré-calculados |
| **appdimens_games** | Desenvolvimento de jogos | appdimens_library, appdimens_dynamic | C++/NDK, utilitários OpenGL, monitoramento de performance |
| **appdimens_all** | Pacote completo | Todos os módulos | Funcionalidade completa em dependência única |

### Características de Performance

| Recurso | Overhead de Runtime | Uso de Memória | Tempo de Cálculo | Estratégia de Cache |
|---------|-------------------|----------------|------------------|-------------------|
| **Dynamic/Fixed** | ~0.001ms | ~50KB | Cache por configuração | Rastreamento automático de dependências |
| **SDP/SSP** | Zero | ~2MB (recursos) | Pré-calculado | Baseado em recursos |
| **Unidades Físicas** | ~0.002ms | ~10KB | Sob demanda | Inicialização preguiçosa |
| **Games (Nativo)** | ~0.0005ms | ~100KB | Cache com LRU | Implementação C++ nativa |

---

## 🤝 Contribuindo

Aceitamos contribuições! Veja nossas [Diretrizes de Contribuição](../../CONTRIBUTING.md) para detalhes.

### 🐛 Encontrou um Bug?
- [Crie uma issue](https://github.com/bodenberg/appdimens/issues)
- Inclua informações do dispositivo e passos para reproduzir
- Anexe screenshots se aplicável

### 💡 Tem uma Ideia?
- [Inicie uma discussão](https://github.com/bodenberg/appdimens/discussions)
- Proponha novos recursos ou melhorias
- Compartilhe seus casos de uso

---

## 📄 Licença

Este projeto está licenciado sob a Licença Apache 2.0 - veja o arquivo [LICENSE](../../LICENSE) para detalhes.

---

## 👨‍💻 Autor

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 Mostre Seu Apoio

Se o AppDimens Android ajudou seu projeto, considere:

- ⭐ **Fazer star** neste repositório
- 🐦 **Compartilhar** nas redes sociais
- 📝 **Escrever** uma resenha ou post de blog
- 🤝 **Contribuir** com código ou documentação

---

<div align="center">
    <p><strong>Feito com ❤️ para a comunidade de desenvolvimento Android</strong></p>
    <p>AppDimens Android - Onde design responsivo encontra precisão matemática</p>
</div>