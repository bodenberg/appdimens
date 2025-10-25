---
layout: default
title: "Podfile"
---

<div align="center">
    <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens iOS - Design Responsivo" height="250"/>
    <h1>📐 AppDimens iOS</h1>
    <p><strong>Dimensionamento Inteligente e Responsivo para iOS</strong></p>
    <p>Escala responsiva matematicamente precisa que garante que seu design de UI se adapte perfeitamente a qualquer tamanho de tela ou proporção — de iPhones a iPads, Apple TV e Apple Watch.</p>

[![Versão](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licença](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-iOS%2013+-orange.svg)](https://developer.apple.com/ios/)
[![Swift](https://img.shields.io/badge/Swift-5.0+-blue.svg)](https://swift.org/)
</div>

> **Idiomas:** [English](../../../iOS/README.md) | Português (BR) | [Español](../../es/iOS/README.md)

---

## 🎯 O que é AppDimens iOS?

**AppDimens iOS** é um sistema abrangente de dimensionamento que substitui valores fixos de points por dimensões escaladas de forma inteligente com base nas características reais da tela. Enquanto os points padrão do iOS são constantes, o AppDimens os trata como valores base que escalam de forma previsível em diferentes tamanhos de tela, densidades e proporções.

A biblioteca está organizada em três módulos:
- **Principal**: Funcionalidade unificada de gerenciamento de dimensões com cache avançado e qualificadores
- **UI**: Extensões e integrações UIKit e SwiftUI  
- **Games**: Funcionalidade específica Metal para desenvolvimento de jogos

### 🎨 Benefícios Principais

- **🎯 Consistência Visual**: Mantenha proporções perfeitas em todos os tipos de dispositivos iOS
- **📱 Compatibilidade Universal**: Funciona perfeitamente em iPhones, iPads, Apple TV e Apple Watch
- **⚡ Performance Otimizada**: Overhead mínimo de runtime com cálculos em cache
- **🔧 Integração Fácil**: API simples que funciona com SwiftUI e UIKit
- **📐 Precisão Matemática**: Dois modelos de escala (Fixed & Dynamic) para diferentes necessidades de design
- **🍎 iOS Nativo**: Construído especificamente para iOS com Swift e APIs nativas
- **🎮 Desenvolvimento de Jogos**: Módulo Metal especializado para desenvolvimento de jogos de alta performance
- **🚀 Integração Metal**: Suporte nativo Metal e MetalKit com otimizações SIMD

---

## 🎮 Recursos de Desenvolvimento de Jogos

### Módulo AppDimens Games
O módulo **AppDimens Games** fornece funcionalidade especializada para desenvolvimento de jogos iOS com suporte Metal e MetalKit:

#### Recursos Principais:
- **Integração Metal**: Suporte nativo Metal e MetalKit para renderização de alta performance
- **Modos de Escala de Viewport**:
  - `Uniform`: Escala uniforme para proporções consistentes
  - `Horizontal`: Escala horizontal para jogos em paisagem
  - `Vertical`: Escala vertical para jogos em retrato
  - `AspectRatio`: Escala consciente da proporção
  - `Viewport`: Escala baseada em viewport para layouts complexos
- **Conversão de Coordenadas**: Transformações de coordenadas Tela ↔ NDC
- **Extensões SIMD**: Operações vetoriais otimizadas usando framework simd
- **Integração SwiftUI**: Extensões SwiftUI específicas de jogos e sistema de ambiente
- **Performance Otimizada**: Implementação Swift nativa com aceleração Metal

#### Exemplo de Uso:
```swift
// Dimensões específicas do jogo
let buttonSize = gameUniform(48)        // Escala uniforme
let playerSize = gameAspectRatio(64)    // Escala de proporção
let uiOverlaySize = gameViewport(24)    // Escala de viewport

// Integração SwiftUI
struct GameView: View {
    var body: some View {
        VStack {
            Text("Pontuação: 1000")
                .font(.system(size: gameUniform(24)))
            
            MetalGameView()
                .frame(
                    width: gameAspectRatio(320),
                    height: gameAspectRatio(240)
                )
        }
        .withAppDimens()  // Habilitar ambiente AppDimens
    }
}
```

---

## 🚀 Instalação

### CocoaPods (Recomendado)

```ruby
# Podfile
platform :ios, '13.0'
use_frameworks!

target 'SeuApp' do
  pod 'AppDimens'
end
```

```bash
pod install
```

### Swift Package Manager

1. **No Xcode:**
   - File → Add Package Dependencies
   - Digite: `https://github.com/bodenberg/appdimens.git`
   - Selecione a versão: `1.0.5` ou superior
   - Adicione ao seu target

2. **Ou adicione ao Package.swift:**
```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
]
```

### Instalação Manual

1. **Baixe o código-fonte:**
```bash
git clone https://github.com/bodenberg/appdimens.git
```

2. **Copie a pasta Sources:**
   - Copie `Sources/AppDimens/` para seu projeto
   - Adicione todos os arquivos Swift ao seu projeto Xcode

---

## 🧠 Modelos de Dimensão Core

| Modelo | Filosofia | Caso de Uso Ideal | Suportado Em |
|--------|-----------|-------------------|--------------|
| **Fixed (FX)** | Escala logarítmica (refinada) | Botões, paddings, margens, ícones | SwiftUI + UIKit |
| **Dynamic (DY)** | Escala proporcional (agressiva) | Containers, grids, fontes fluidas | SwiftUI + UIKit |
| **Unidades Físicas** | mm/cm/polegada → Points | Wearables, impressão, layouts de precisão | SwiftUI + UIKit |

---

## 🎨 Exemplos de Uso

### 🧩 SwiftUI

#### Design Responsivo Básico

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("Título Responsivo")
                .font(.fxSystem(size: 24, weight: .bold))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(12)
                .foregroundColor(.blue)
            
            Button("Ação") {
                // Ação do botão
            }
            .fxFrame(width: 120, height: 44)
            .fxCornerRadius(8)
        }
        .fxPadding(16)
    }
}
```

#### Escala Condicional Avançada

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Título do Card")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("Este card se adapta a qualquer tamanho de tela com escala inteligente.")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
            
            HStack {
                Spacer()
                Button("Ação") { }
                    .fxFrame(width: 80, height: 32)
                    .fxCornerRadius(6)
            }
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // Largura dinâmica
        .fxFrame(height: 200)          // Altura fixa
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

#### Integração de Ambiente (Recomendado)

```swift
@main
struct MyApp: App {
    var body: some Scene {
        WindowGroup {
            DimensProvider {  // Essencial para novos recursos
                ContentView()
            }
        }
    }
}

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("AppDimens Aprimorado")
                .font(.fxSystem(size: 24, weight: .bold))
            
            // API baseada em protocolo
            Rectangle()
                .frame(width: 100.fixed().dimension)
                .frame(height: 50.fxpt)
            
            // Unidades físicas
            Rectangle()
                .frame(width: 2.cm, height: 1.cm)
            
            // Calculadora de contagem de itens
            Rectangle()
                .calculateAvailableItemCount(
                    itemSize: 50.fxpt,
                    itemPadding: 8.fxpt,
                    count: $itemCount
                )
        }
    }
}
```

### 📱 UIKit

#### Integração UIKit Básica

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    private func setupUI() {
        // Container
        let containerView = UIView()
        containerView.backgroundColor = .systemBlue
        containerView.fxCornerRadius(16)
        view.addSubview(containerView)
        
        // Label
        let titleLabel = UILabel()
        titleLabel.text = "Título Responsivo"
        titleLabel.textAlignment = .center
        titleLabel.fxFontSize(20)
        containerView.addSubview(titleLabel)
        
        // Botão
        let button = UIButton(type: .system)
        button.setTitle("Ação", for: .normal)
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        containerView.addSubview(button)
        
        // Constraints
        containerView.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        button.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            // Container - largura dinâmica, altura fixa
            containerView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            containerView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            containerView.widthAnchor.constraint(equalToConstant: 300.dypt),
            containerView.heightAnchor.constraint(equalToConstant: 200.fxpt),
            
            // Label
            titleLabel.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 20.fxpt),
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16.fxpt),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16.fxpt),
            
            // Botão
            button.centerXAnchor.constraint(equalTo: containerView.centerXAnchor),
            button.centerYAnchor.constraint(equalTo: containerView.centerYAnchor),
            button.widthAnchor.constraint(equalToConstant: 120.dypt),
            button.heightAnchor.constraint(equalToConstant: 44.fxpt)
        ])
    }
}
```

#### Configuração UIKit Avançada

```swift
class AdvancedViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupAdvancedUI()
    }
    
    private func setupAdvancedUI() {
        // Dimensões personalizadas com valores específicos do dispositivo
        let customDimension = AppDimens.fixed(16)
            .screen(.phone, 14)           // 14pt para telefones
            .screen(.tablet, 18)          // 18pt para tablets
            .aspectRatio(enable: true)    // Habilitar ajuste de proporção
            .toPoints()
        
        // Dinâmico com tipo de tela personalizado
        let dynamicDimension = AppDimens.dynamic(100)
            .type(.highest)               // Usar maior dimensão da tela
            .toPoints()
        
        // Aplicar aos elementos de UI
        let label = UILabel()
        label.font = UIFont.systemFont(ofSize: customDimension)
        label.text = "Texto escalado personalizado"
        
        let view = UIView()
        view.frame = CGRect(x: 0, y: 0, width: dynamicDimension, height: 50.fxpt)
        
        view.addSubview(label)
        self.view.addSubview(view)
    }
}
```

---

## 🔧 Recursos Avançados

### 🔄 Escala Condicional

```swift
// Qualificadores de tela personalizados
let customDimension = AppDimens.fixed(16)
    .screen(.phone, 14)           // 14pt para telefones
    .screen(.tablet, 18)          // 18pt para tablets
    .screen(.watch, 12)           // 12pt para Apple Watch
    .aspectRatio(enable: true)    // Habilitar ajuste de proporção
    .toPoints()

// Dinâmico com tipo de tela personalizado
let dynamicDimension = AppDimens.dynamic(100)
    .type(.highest)               // Usar maior dimensão da tela
    .toPoints()
```

### 📏 Unidades Físicas

```swift
// Conversão de unidades físicas
Rectangle()
    .frame(width: 2.cm, height: 1.cm)    // 2cm × 1cm
    .frame(width: 5.mm, height: 3.mm)    // 5mm × 3mm
    .frame(width: 1.inch, height: 0.5.inch) // 1 polegada × 0.5 polegada
```

### 🧮 Utilitários de Layout

```swift
struct ResponsiveGrid: View {
    let items = Array(1...12)
    
    var body: some View {
        LazyVGrid(columns: [
            GridItem(.flexible(), spacing: 16.fxpt),
            GridItem(.flexible(), spacing: 16.fxpt)
        ], spacing: 16.fxpt) {
            ForEach(items, id: \.self) { item in
                VStack {
                    Image(systemName: "star.fill")
                        .font(.fxSystem(size: 24))
                        .foregroundColor(.yellow)
                    
                    Text("Item \(item)")
                        .font(.fxSystem(size: 12))
                }
                .fxFrame(width: 80, height: 80)
                .background(Color(.systemGray5))
                .fxCornerRadius(8)
            }
        }
        .fxPadding(16)
    }
}
```

### 📊 Layouts Baseados em Porcentagem

```swift
struct PercentageLayout: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            // 80% da largura da tela
            Rectangle()
                .fill(Color.blue.opacity(0.3))
                .dyFrame(width: AppDimens.percentage(0.8))
                .fxFrame(height: 100)
                .fxCornerRadius(8)
            
            // 60% da largura da tela
            Rectangle()
                .fill(Color.green.opacity(0.3))
                .dyFrame(width: AppDimens.percentage(0.6))
                .fxFrame(height: 80)
                .fxCornerRadius(8)
        }
    }
}
```

---

## 📊 Modelos Matemáticos

### 🎯 Modelo Fixed (FX)

**Filosofia**: Ajuste logarítmico para escala refinada

**Fórmula**: 
```
Valor Final = Base Points × (1 + Fator de Ajuste × (Incremento Base + Ajuste AR))
```

**Características**:
- Crescimento suave e controlado
- Desacelera em telas muito grandes
- Mantém consistência visual
- Ideal para elementos de UI

**Casos de Uso**:
- Alturas e larguras de botões
- Padding e margens
- Tamanhos de ícones
- Tamanhos de fonte para legibilidade

### 🚀 Modelo Dynamic (DY)

**Filosofia**: Ajuste proporcional baseado em porcentagem

**Fórmula**:
```
Valor Final = (Base Points / Largura de Referência) × Dimensão Atual da Tela
```

**Características**:
- Crescimento linear e proporcional
- Mantém porcentagem do espaço da tela
- Escala agressiva em telas grandes
- Ideal para containers de layout

**Casos de Uso**:
- Larguras e alturas de containers
- Tamanhos de itens de grid
- Dimensões de espaçadores
- Elementos dependentes de viewport

---

## 📱 Suporte a Dispositivos

### 📱 Tipos de Dispositivos Suportados

| Tipo de Dispositivo | Descrição | Comportamento de Escala |
|---------------------|-----------|------------------------|
| **Phone** | Dispositivos iPhone | Escala balanceada |
| **Tablet** | Dispositivos iPad | Escala aprimorada para telas maiores |
| **Watch** | Dispositivos Apple Watch | Escala compacta |
| **TV** | Dispositivos Apple TV | Elementos de UI grandes para distância de visualização |
| **CarPlay** | Dispositivos CarPlay | Alvos de toque grandes |

### 📐 Tipos de Tela

| Tipo | Descrição | Caso de Uso |
|------|-----------|-------------|
| **Lowest** | Usar menor dimensão da tela | Padrão, mais restritivo |
| **Highest** | Usar maior dimensão da tela | Para elementos que devem escalar com a maior dimensão |

---

## ⚡ Performance e Otimização

### 📊 Características de Performance

| Recurso | Overhead de Runtime | Uso de Memória | Tempo de Cálculo |
|---------|-------------------|----------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cache por configuração |
| **Unidades Físicas** | ~0.002ms | ~10KB | Sob demanda |

### 🚀 Estratégias de Otimização

1. **Cálculos em Cache**: Fatores de ajuste são computados uma vez por mudança de configuração
2. **Avaliação Preguiçosa**: Valores são computados apenas quando necessários
3. **Overhead Mínimo**: Operações matemáticas simples com alocação mínima de memória

### 💡 Melhores Práticas

1. **Use Fixed para Elementos de UI**: Botões, paddings, fontes, ícones
2. **Use Dynamic para Layout**: Larguras de containers, espaçadores, itens de grid
3. **Cache de Dimensões**: Armazene dimensões frequentemente usadas em propriedades
4. **Evite Aninhamento Excessivo**: Mantenha cadeias de dimensão simples

---

## 🧪 Testes e Debug

### 🔧 Ferramentas de Debug

```swift
// Debug da configuração atual da tela
let (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
print("Tela: \(width) × \(height)")

// Debug do tipo de dispositivo
print("Dispositivo: \(DeviceType.current())")

// Debug dos fatores de ajuste
let factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
print("Fatores: \(factors)")
```

### 📋 Cobertura de Testes

- ✅ Cálculos de dimensão
- ✅ Detecção de tipo de dispositivo
- ✅ Cálculos de fator de tela
- ✅ Métodos de extensão
- ✅ Casos extremos e tratamento de erros
- ✅ Benchmarks de performance

---

## 📚 Referência da API

### 🎯 Classes Core

| Classe | Descrição | Métodos Principais |
|--------|-----------|-------------------|
| **AppDimens** | Ponto de entrada principal | `fixed()`, `dynamic()`, `percentage()` |
| **AppDimensFixed** | Escala fixa | `screen()`, `aspectRatio()`, `type()` |
| **AppDimensDynamic** | Escala dinâmica | `screen()`, `type()` |
| **AppDimensAdjustmentFactors** | Cálculos de tela | `getCurrentScreenDimensions()`, `calculateAdjustmentFactors()` |

### 🔧 Funções de Extensão

| Extensão | Descrição | Exemplo |
|----------|-----------|---------|
| `.fxpt` | Points fixos | `16.fxpt` |
| `.fxpx` | Pixels fixos | `16.fxpx` |
| `.dypt` | Points dinâmicos | `100.dypt` |
| `.dypx` | Pixels dinâmicos | `100.dypx` |
| `.cm` | Centímetros | `2.cm` |
| `.mm` | Milímetros | `5.mm` |
| `.inch` | Polegadas | `1.inch` |

### 🎨 Extensões SwiftUI

| Extensão | Descrição | Exemplo |
|----------|-----------|---------|
| `.fxPadding()` | Padding fixo | `.fxPadding(16)` |
| `.fxFrame()` | Frame fixo | `.fxFrame(width: 100, height: 50)` |
| `.fxCornerRadius()` | Raio de canto fixo | `.fxCornerRadius(8)` |
| `.dyFrame()` | Frame dinâmico | `.dyFrame(width: 200)` |
| `.font(.fxSystem())` | Fonte fixa | `.font(.fxSystem(size: 16))` |

### 📱 Extensões UIKit

| Extensão | Descrição | Exemplo |
|----------|-----------|---------|
| `.fxFontSize()` | Tamanho de fonte fixo | `label.fxFontSize(16)` |
| `.fxCornerRadius()` | Raio de canto fixo | `view.fxCornerRadius(8)` |
| `.fxBorderWidth()` | Largura de borda fixa | `view.fxBorderWidth(1)` |
| `.fxTitleFontSize()` | Tamanho de fonte do título fixo | `button.fxTitleFontSize(14)` |

---

## 🔄 Migração do Android

Se você está familiarizado com a versão Android do AppDimens, aqui está o mapeamento:

| Android | iOS |
|---------|-----|
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `AppDimens.dynamic(100).toDp()` | `AppDimens.dynamic(100).toPoints()` |
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |
| `UiModeType.PHONE` | `DeviceType.phone` |

---

## 📚 Documentação e Recursos

### 📖 Documentação Completa

- **[📘 Documentação Completa](https://appdimens-project.web.app/)** - Guias abrangentes e referência de API
- **[🎯 Documentação Técnica](../../../iOS/DOCUMENTATION.md)** - Documentação técnica detalhada
- **[📱 Guia de Uso](../../../iOS/USAGE_GUIDE.md)** - Guia prático de uso
- **[🔧 Guia de Instalação](../../../iOS/INSTALLATION.md)** - Instruções de instalação
- **[📱 Exemplos](../../../iOS/Examples/)** - Exemplos de uso do mundo real

### 🔗 Links Rápidos

- **[🚀 Guia de Instalação](#instalação)** - Comece em minutos
- **[📱 Exemplos](#exemplos-de-uso)** - Exemplos de uso do mundo real
- **[🔧 Referência de API](#referência-da-api)** - Documentação completa da API
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Perguntas e respostas comuns

---

## 🏗️ Visão Geral da Arquitetura

### Estrutura da Biblioteca iOS

| Módulo | Propósito | Dependências | Recursos Principais |
|--------|-----------|--------------|-------------------|
| **AppDimens** | Funcionalidade core | Foundation, UIKit | Modelos DY/FX, cache, qualificadores |
| **AppDimensUI** | Extensões de UI | AppDimens | Extensões SwiftUI, integração UIKit |
| **AppDimensGames** | Desenvolvimento de jogos | AppDimens, Metal | Integração Metal, gerenciamento de viewport, SIMD |

### Características de Performance

| Recurso | Overhead de Runtime | Uso de Memória | Tempo de Cálculo | Estratégia de Cache |
|---------|-------------------|----------------|------------------|-------------------|
| **Dynamic/Fixed** | ~0.001ms | ~50KB | Cache por configuração | Rastreamento automático de dependências |
| **Unidades Físicas** | ~0.002ms | ~10KB | Sob demanda | Inicialização preguiçosa |
| **Games (Metal)** | ~0.0005ms | ~100KB | Cache com SIMD | Implementação Metal nativa |
| **Extensões SwiftUI** | Zero | ~5KB | Tempo de compilação | Extensões estáticas |

### Suporte de Plataforma

| Plataforma | Versão Mín | SwiftUI | UIKit | Games |
|------------|------------|---------|-------|-------|
| **iOS** | 13.0 | ✅ | ✅ | ✅ (Metal) |
| **macOS** | 10.15 | ✅ | AppKit | ✅ (Metal) |
| **tvOS** | 13.0 | ✅ | ✅ | ✅ (Metal) |
| **watchOS** | 6.0 | ✅ | ❌ | ❌ |

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

Se o AppDimens iOS ajudou seu projeto, considere:

- ⭐ **Fazer star** neste repositório
- 🐦 **Compartilhar** nas redes sociais
- 📝 **Escrever** uma resenha ou post de blog
- 🤝 **Contribuir** com código ou documentação

---

<div align="center">
    <p><strong>Feito com ❤️ para a comunidade de desenvolvimento iOS</strong></p>
    <p>AppDimens iOS - Onde design responsivo encontra precisão matemática</p>
</div>