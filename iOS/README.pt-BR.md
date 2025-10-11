# 📚 AppDimens: Dimensionamento Responsivo para SwiftUI

O **AppDimens** é um sistema de dimensionamento inteligente para SwiftUI, inspirado no Jetpack Compose. Ele permite que você crie *layouts* que se ajustam dinamicamente a qualquer tamanho de tela, Proporção (*Aspect Ratio*) e qualificador de dispositivo/tela.

O sistema utiliza um **DP (Ponto) de referência de $300\text{pt}$** para seus cálculos.

## 🚀 1. Conceitos Fundamentais (Fixed vs. Dynamic)

| Modo | Descrição | Uso Principal | Sintaxe (Ex.) |
| :--- | :--- | :--- | :--- |
| **Fixed** | **Ajuste Ponderado (Escalonamento).** O valor base é escalado por um fator que considera o tamanho da tela e o *Aspect Ratio*. **Ideal para escala sutil e é a sintaxe preferencial.** | Fontes, espaçamentos, *padding*, ícones, tamanhos base de componentes. | `20.fxdp` |
| **Dynamic** | **Dimensionamento Percentual.** O valor base é convertido em uma porcentagem da `BASE_WIDTH_DP` ($300\text{pt}$) e aplicado à dimensão da tela atual (menor ou maior). | *Frames* de componentes principais, gráficos que ocupam grande área (Ex: $50\%$ da tela). | `0.5.dyPercent` |

-----

## 💡 2. Guia Rápido de Sintaxe

### 2.1. Sintaxe Direta (Recomendado)

Utilize as propriedades diretas de `CGFloat`, `Int`, `Float` e `Double` para o dimensionamento mais comum (**Fixed**).

| Sintaxe | Descrição | Exemplo em Swift | Tipo de Retorno |
| :--- | :--- | :--- | :--- |
| **`.fxdp`** | **Fixed** (Ajuste Ponderado) usando *Aspect Ratio* (`.withAspectRation(true)`). | `.padding(16.fxdp)` | `DimensPoint (CGFloat)` |
| **`.sp`** | **Fixed** (Ajuste Ponderado) para *Text*/*Spaced Points*. | `.font(.system(size: 16.sp))` | `DimensPoint (CGFloat)` |
| **`.dydp`** | **Dynamic** (Ajuste Percentual) da menor dimensão. | `.frame(height: 100.dydp)` | `DimensPoint (CGFloat)` |

### 2.2. Sintaxe de Cadeia (*Chaining*)

A sintaxe de cadeia permite acesso a funções avançadas, como qualificadores, `ScreenType` (`.screen(.highest)`) e ajuste fino de *Aspect Ratio*.

| Sintaxe | Descrição | Exemplo em Swift |
| :--- | :--- | :--- |
| **`.fixed()`** | Inicia a cadeia **Fixed**. | `20.fixed().dimension` |
| **`.dynamic()`** | Inicia a cadeia **Dynamic**. | `100.dynamic().dimension` |
| **`.dynamicPercentage()`** | Inicia a cadeia Dynamic como **porcentagem** (0.0 a 1.0). | `0.5.dynamicPercentage().dimension` |
| **`.screen()`** | Define se o cálculo Dynamic/Fixed deve usar a dimensão `highest` ou `lowest` da tela. | `100.dynamic().screen(.highest).dimension` |
| **`.add()`** | Adiciona um **qualificador** de tela (`DpQualifier`). | `20.fixed().add(type: .smallWidth, value: 600, customValue: 30).dimension` |
| **`.add(uiMode:)`** | Adiciona um **qualificador de modo de UI** (dispositivo) com prioridade máxima. | `20.fixed().add(uiMode: .macOS, type: .width, value: 1000, customValue: 40).dimension` |

### 2.3. Unidades Físicas (MM, CM, INCH)

As unidades físicas são convertidas diretamente para `Points` ($1\text{pt} = 1/72$ polegada), ignorando a lógica de escalonamento Fixed/Dynamic.

| Sintaxe | Descrição | Exemplo em Swift |
| :--- | :--- | :--- |
| **`.mm`** | Converte Milímetros para Points/Px lógicos. | `.frame(width: 5.mm)` |
| **`.cm`** | Converte Centímetros para Points/Px lógicos. | `.frame(height: 2.cm)` |
| **`.inch`** | Converte Polegadas para Points/Px lógicos. | `.frame(width: 1.inch)` |

-----

## ⚙️ 3. Funções Utilitárias

### A. Cálculo de Contagem de Itens (`calculateAvailableItemCount`)

Este modificador replica a lógica de *layout* do Compose para calcular quantos itens cabem em um contêiner, considerando o `itemSize` e o `itemPadding`.

```swift
// Variável que receberá o resultado (itemCount)
@State private var itemCount: Int = 0

Rectangle()
    .frame(height: 100) 
    .calculateAvailableItemCount(
        itemSize: 50.fxdp,         // Tamanho do item ajustado
        itemPadding: 8.fxdp,       // Padding lateral ajustado
        direction: .width,         // Medir na direção da largura
        count: $itemCount          // Variável de destino
    )

Text("Itens disponíveis: \(itemCount)")
```

### B. Funções Wrapper

Funções simples para replicar a sintaxe direta de alguns sistemas de dimensões:

```swift
public extension View {
    // Simula a função wrapper 'dynamicDp'
    func dynamicDp(_ base: DimensPoint, @ViewBuilder content: @escaping (DimensPoint) -> some View) -> some View {
        return base.dynamic().dimension(content: content)
    }

    // Simula a função wrapper 'fixedDp'
    func fixedDp(_ base: DimensPoint, @ViewBuilder content: @escaping (DimensPoint) -> some View) -> some View {
        return base.fixed().dimension(content: content)
    }
}
```

-----

## ⚙️ 4. Configuração Essencial

Para que o AppDimens funcione, o **`DimensProvider`** deve ser injetado no ambiente para capturar as dimensões da janela e pré-calcular os fatores de ajuste.

**Regra de Ouro:** O `DimensProvider` **DEVE** ser o *root* (ou próximo ao *root*) da sua hierarquia de *Views*.

```swift
@main
struct MainApp: App {
    var body: some Scene {
        WindowGroup {
            DimensProvider { 
                ContentView()
            }
        }
    }
}
```
