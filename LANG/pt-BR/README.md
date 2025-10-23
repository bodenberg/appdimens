<div align="center">
    <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens - Design Responsivo em Todos os Dispositivos" height="300"/>
    <h1>📐 AppDimens</h1>
    <p><strong>Dimensionamento Inteligente e Responsivo para Android & iOS</strong></p>
    <p>Escala responsiva matematicamente precisa que garante que seu design de UI se adapte perfeitamente a qualquer tamanho de tela ou proporção — de telefones a TVs, carros e wearables.</p>

[![Versão](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licença](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%20%7C%20iOS-orange.svg)](https://github.com/bodenberg/appdimens)
[![Documentação](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

> Idiomas: [English](../../README.md) | [Español](../es/README.md) | [हिन्दी](../hi/README.md) | [Русский](../ru/README.md) | [中文](../zh/README.md) | [日本語](../ja/README.md)

---

## 🎯 O que é AppDimens?

**AppDimens** é um sistema abrangente de dimensionamento que substitui valores fixos de pixels por dimensões escaladas de forma inteligente com base nas características reais da tela. Enquanto DP/Points tradicionais são constantes, AppDimens os trata como valores base que escalam de forma previsível em diferentes tamanhos de tela, densidades e proporções.

### 🎨 Principais Benefícios

- **🎯 Consistência Visual**: Mantenha proporções perfeitas em todos os tipos de dispositivos
- **📱 Compatibilidade Universal**: Funciona perfeitamente em telefones, tablets, TVs, carros e wearables
- **⚡ Otimizado para Performance**: Sobrecarga mínima em tempo de execução com cálculos em cache
- **🔧 Integração Fácil**: API simples que funciona com Jetpack Compose, XML Views, SwiftUI e UIKit
- **📐 Precisão Matemática**: Dois modelos de escala (Fixed & Dynamic) para diferentes necessidades de design
- **🎮 Desenvolvimento de Jogos**: Módulo especializado C++/NDK para desenvolvimento de jogos de alta performance
- **🚀 Performance Nativa**: Implementação C++ para cálculos específicos de jogos e integração OpenGL

---

## 🎮 Funcionalidades de Desenvolvimento de Jogos

### Android Games (C++/NDK)
- **Performance Nativa**: Implementação C++ para cálculos de alta performance
- **Tipos de Dimensão de Jogo**: DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY
- **Operações Vetoriais**: GameVector2D com operações matemáticas
- **Gerenciamento de Viewport**: Múltiplos modos de escala para diferentes cenários de jogo
- **Integração OpenGL**: Utilitários para renderização OpenGL ES

### iOS Games (Metal)
- **Integração Metal**: Suporte nativo Metal e MetalKit
- **Escala de Viewport**: Modos uniform, horizontal, vertical, aspect-ratio, viewport
- **Conversão de Coordenadas**: Transformações Screen ↔ NDC
- **Otimizado para Performance**: Extensões SIMD para operações vetoriais
- **Integração SwiftUI**: Extensões SwiftUI específicas para jogos

---

## 🚀 Início Rápido

### Android

```kotlin
dependencies {
    // Biblioteca principal (Dynamic + Fixed scaling)
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.8")
    
    // Opcional: SDP & SSP scaling
    implementation("io.github.bodenberg:appdimens-sdps:1.0.8")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.8")
    
    // Pacote completo (não inclui módulo de games)
    implementation("io.github.bodenberg:appdimens-all:1.0.8")
    
    // Desenvolvimento de jogos com suporte C++/NDK (dependência separada)
    implementation("io.github.bodenberg:appdimens-games:1.0.8")
}
```

### iOS

```ruby
# Podfile
pod 'AppDimens'
```

```swift
// Swift Package Manager
.package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.8")
```

---

## 🧠 Modelos de Dimensão Principais

| Modelo | Filosofia | Caso de Uso Ideal | Plataformas Suportadas | Implementação |
|-------|------------|----------------|-------------------|----------------|
| **Fixed (FX)** | Escala logarítmica (refinada) | Botões, paddings, margens, ícones | Android + iOS | Ajuste matemático de proporção |
| **Dynamic (DY)** | Escala proporcional (agressiva) | Containers, grids, fontes fluidas | Android + iOS | Escala proporcional baseada na tela |
| **SDP / SSP** | Recursos pré-calculados | Uso direto de `@dimen` em XML | Android | 426+ arquivos de dimensão pré-gerados |
| **Unidades Físicas** | mm/cm/inch → Dp/Sp/Px/Points | Wearables, impressão, layouts de precisão | Android + iOS | Conversão de medidas do mundo real |
| **Dimensões de Jogo** | Escala especializada para jogos | UI de jogo, viewports, Metal/OpenGL | Android + iOS | Implementação nativa C++/NDK + Metal |

---

## 🏗️ Visão Geral da Arquitetura

### Estrutura das Bibliotecas Android

| Módulo | Propósito | Dependências | Principais Funcionalidades |
|--------|-----------|-------------|---------------------------|
| **appdimens_library** | Tipos e interfaces principais | Nenhuma | Enums base, qualificadores, fatores de ajuste |
| **appdimens_dynamic** | Escala Dynamic/Fixed | appdimens_library | Modelos DY/FX, extensões Compose, cache |
| **appdimens_sdps** | Escala SDP | appdimens_library | 426+ recursos @dimen pré-calculados |
| **appdimens_ssps** | Escala SSP | appdimens_library | 216+ recursos @dimen pré-calculados |
| **appdimens_games** | Desenvolvimento de jogos | appdimens_library, appdimens_dynamic | C++/NDK, utilitários OpenGL, monitoramento de performance |
| **appdimens_all** | Pacote completo | Todos os módulos | Funcionalidade completa em uma única dependência |

### Estrutura das Bibliotecas iOS

| Módulo | Propósito | Dependências | Principais Funcionalidades |
|--------|-----------|-------------|---------------------------|
| **AppDimens** | Funcionalidade principal | Foundation, UIKit | Modelos DY/FX, cache, qualificadores |
| **AppDimensUI** | Extensões de UI | AppDimens | Extensões SwiftUI, integração UIKit |
| **AppDimensGames** | Desenvolvimento de jogos | AppDimens, Metal | Integração Metal, gerenciamento de viewport, SIMD |

### Características de Performance

| Funcionalidade | Sobrecarga Runtime | Uso de Memória | Tempo de Cálculo | Estratégia de Cache |
|----------------|-------------------|----------------|------------------|-------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cache por configuração | Rastreamento automático de dependências |
| **SDP/SSP** | Zero | ~2MB (recursos) | Pré-calculado | Baseado em recursos |
| **Unidades Físicas** | ~0.002ms | ~10KB | Sob demanda | Inicialização lazy |
| **Jogos (Nativo)** | ~0.0005ms | ~100KB | Cache com LRU | Implementação C++ nativa |

---

## 📱 Exemplos de Plataforma

### 🤖 Android - Jetpack Compose

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.dydp)           // Largura dinâmica
            .height(200.fxdp)          // Altura fixa
            .padding(16.fxdp)          // Padding fixo
    ) {
        Column(
            modifier = Modifier.padding(16.fxdp)
        ) {
            Text(
                text = "Título Responsivo",
                fontSize = 18.fxsp     // Tamanho de fonte fixo
            )
            Text(
                text = "Este card se adapta a qualquer tamanho de tela",
                fontSize = 14.dysp     // Tamanho de fonte dinâmico
            )
        }
    }
}
```

### 🍎 iOS - SwiftUI

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Título Responsivo")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("Este card se adapta a qualquer tamanho de tela")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // Largura dinâmica
        .fxFrame(height: 200)          // Altura fixa
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

### 📄 Android - XML Views

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="@dimen/_16sdp">
    
    <TextView
        android:layout_width="@dimen/_300sdp"
        android:layout_height="wrap_content"
        android:textSize="@dimen/_18ssp"
        android:text="Texto Responsivo" />
        
    <Button
        android:layout_width="@dimen/_120sdp"
        android:layout_height="@dimen/_48sdp"
        android:text="Ação" />
</LinearLayout>
```

---

## 🎨 Recursos Avançados

### 🔄 Escala Condicional

```kotlin
// Android
val buttonSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

```swift
// iOS
let buttonSize = AppDimens.fixed(80)
    .screen(.watch, 40)           // 40pt para Apple Watch
    .screen(.tablet, 120)         // 120pt para iPad
    .aspectRatio(enable: true)    // Ativar ajuste de proporção
    .toPoints()
```

### 📏 Unidades Físicas

```kotlin
// Android
val marginPx = AppDimensPhysicalUnits.toMm(5f, resources)
view.setPadding(marginPx.toInt(), 0, 0, 0)
```

```swift
// iOS
Rectangle()
    .frame(width: 2.cm, height: 1.cm)  // Unidades físicas
```

### 🧮 Utilitários de Layout

```kotlin
// Android - Calcular número ótimo de colunas de grid
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

---

## 📊 Performance & Compatibilidade

### ⚡ Características de Performance

| Recurso | Sobrecarga em Runtime | Uso de Memória | Tempo de Cálculo |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cache por configuração |
| **SDP/SSP** | Zero | ~2MB (recursos) | Pré-calculado |
| **Unidades Físicas** | ~0.002ms | ~10KB | Sob demanda |

### 📱 Suporte de Plataforma

| Plataforma | Versão Mínima | Frameworks de UI | Recursos Especiais |
|----------|-------------|---------------|------------------|
| **Android** | API 21+ | Compose, Views, Data Binding | SDP/SSP, Unidades Físicas |
| **iOS** | 13.0+ | SwiftUI, UIKit | Extensões nativas |

---

## 📚 Documentação & Recursos

### 📖 Documentação Completa

- **[📘 Documentação Completa](https://appdimens-project.web.app/)** - Guias abrangentes e referência de API
- **[🤖 Guia Android](../../Android/README.md)** - Documentação específica para Android
- **[🍎 Guia iOS](../../iOS/README.md)** - Documentação específica para iOS
- **[🎮 Módulo de Games](../../Android/appdimens_games/README.md)** - Desenvolvimento de jogos com C++/NDK

### 🎯 Links Rápidos

- **[🚀 Guia de Instalação](../../Android/README.md#installation)** - Comece em minutos
- **[📱 Exemplos](../../Android/app/src/main/kotlin/)** - Exemplos de uso no mundo real
- **[🔧 Referência de API](../../Android/DOCS/)** - Documentação completa da API
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Perguntas e respostas comuns

---

## 🎯 Casos de Uso

### 📱 Aplicativos Mobile
Perfeito para apps que precisam funcionar em diferentes tamanhos de telefone e orientações.

### 📺 Apps de TV & Carro
Ideal para aplicações Android TV e Android Auto com tamanhos de tela variados.

### ⌚ Apps Wearable
Essencial para apps Wear OS que precisam se adaptar a diferentes tamanhos de relógio.

### 🎮 Desenvolvimento de Jogos
Módulo especializado para desenvolvimento de jogos com suporte C++/NDK e integração OpenGL.

### 🏢 Apps Empresariais
Ótimo para aplicações de negócios que precisam funcionar em tablets, telefones e desktop.

---

## 🤝 Contribuindo

Aceitamos contribuições! Por favor, veja nossas [Diretrizes de Contribuição](CONTRIBUTING.md) para detalhes.

### 🐛 Encontrou um Bug?
- [Crie uma issue](https://github.com/bodenberg/appdimens/issues)
- Inclua informações do dispositivo e passos de reprodução
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

## 🌟 Mostre seu Apoio

Se AppDimens ajudou seu projeto, por favor considere:

- ⭐ **Dar uma estrela** neste repositório
- 🐦 **Compartilhar** nas redes sociais
- 📝 **Escrever** uma avaliação ou post no blog
- 🤝 **Contribuir** com código ou documentação

---

<div align="center">
    <p><strong>Feito com ❤️ para a comunidade de desenvolvimento mobile</strong></p>
    <p>AppDimens - Onde design responsivo encontra precisão matemática</p>
</div>
