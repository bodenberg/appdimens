# 🚀 AppDimens: Guia Rápido e Visão Geral da Biblioteca

O **AppDimens** é um sistema de gerenciamento de dimensões para Android (Views e Compose) focado em **responsividade matemática**. Ele oferece escalonamento refinado para garantir que os elementos de UI mantenham a proporção correta e o conforto visual em qualquer tamanho ou Aspect Ratio de tela, desde telefones até TVs.

## 1\. Core de Dimensionamento: Fixed (FX) vs. Dynamic (DY)

A biblioteca oferece dois modelos de escalonamento que podem ser usados via **Extensions no Compose** (`.fxdp`, `.dydp`) ou via **Gateway Object** (`AppDimens.fixedPx`, `AppDimens.dynamicPx`) em Views/XML.

| Característica | Fixed (FX) - Ajuste Logarítmico | Dynamic (DY) - Ajuste Percentual |
| :--- | :--- | :--- |
| **Filosofia** | **Sutil e Refinado**. Usa um **Fator de Ajuste** com função **logarítmica** (Aspect Ratio) para crescer suavemente. | **Agressivo e Proporcional**. Calcula o valor final mantendo a mesma **proporção percentual** da dimensão da tela. |
| **Uso Ideal** | Alturas de botões, paddings, ícones. Tudo que precisa de um crescimento **controlado**. | Largura de contêineres, carrosséis, fontes que devem **crescer muito** em telas grandes. |
| **Integração** | `.fixed()` / `.fxdp`, `.fxsp`, `.fxem` | `.dynamic()` / `.dydp`, `.dysp`, `.dyem` |
| **Recurso Chave** | Suporte a ajuste de **Aspect Ratio (AR)** e **sensibilidade customizada**. | Pode **ignorar o escalonamento** em modo **Multi-Window** para evitar UI quebrada. |

**Exemplo (Compose):**

```kotlin
// Ajuste sutil, ideal para a altura do botão
val fixedButtonHeight = 56.fxdp 

// Ajuste proporcional, ideal para a largura do contêiner
val dynamicContainerWidth = 100.dydp
```

-----

## 2\. Gateway e Utilitários (Views/XML)

O objeto **`AppDimens`** atua como o ponto de entrada estático para o View System e fornece utilitários de medição cruciais.

### Funções Principais (`AppDimens` Object)

| Função | Finalidade | Exemplo de Uso |
| :--- | :--- | :--- |
| `AppDimens.fixedPx()` | Retorna o valor base ajustado (Fixed) convertido para **Pixels (PX)**, pronto para `layout_width`/`layout_height`. | `AppDimens.fixedPx(16f, ScreenType.LOWEST, resources).toInt()` |
| `AppDimens.dynamicPx()` | Retorna o valor base ajustado (Dynamic) convertido para **Pixels (PX)**. | `AppDimens.dynamicPx(100f, ScreenType.LOWEST, resources).toInt()` |
| `AppDimens.dynamicPercentagePx()` | Retorna uma porcentagem da tela (ex: 80%) em **Pixels (PX)**. | `AppDimens.dynamicPercentagePx(0.80f, ScreenType.LOWEST, resources)` |

### Data Binding Adapters

A biblioteca permite usar atributos customizados no XML com Data Binding para aplicar o escalonamento Dynamic automaticamente:

| Atributo XML | Conversão Aplicada |
| :--- | :--- |
| `app:dynamicWidthDp` | `DP base` $\xrightarrow{\text{Dynamic}}$ `PX` (largura) |
| `app:dynamicHeightDp` | `DP base` $\xrightarrow{\text{Dynamic}}$ `PX` (altura) |
| `app:dynamicTextSizeDp` | `DP base` $\xrightarrow{\text{Dynamic}}$ `SP` em `PX` (tamanho do texto) |

**Exemplo (XML):**

```xml
<View app:dynamicWidthDp="@{48f}" /> 
```

-----

## 3\. Unidades Físicas (`AppDimensPhysicalUnits`)

O objeto **`AppDimensPhysicalUnits`** fornece ferramentas para converter medidas físicas (polegadas, mm, cm) para as unidades de tela do Android (Dp, Sp, Px).

| Função | Unidade de Origem | Unidade de Destino | Uso Típico |
| :--- | :--- | :--- | :--- |
| `AppDimensPhysicalUnits.toDp()` | INCH, CM, MM | **Dp** | Definir um tamanho absoluto de um objeto (ex: $1\text{cm}$ de altura). |
| `AppDimensPhysicalUnits.toMm()` | MM | **Px** | Obter a conversão direta de milímetros para Pixels reais. |
| `Int.radius()` | INCH, CM, MM | **Px** | Calcular o raio em Pixels de um dispositivo esférico (Wear OS). |

**Exemplo de Conversão (Views/Kotlin):**

```kotlin
// Define a margem superior da View com base em 5 milímetros.
val marginPx = AppDimensPhysicalUnits.toMm(5.0f, resources).toInt()
myView.layoutParams.topMargin = marginPx 
```

-----

## 4\. Utilitário de Layout: `calculateAvailableItemCount`

Esta função é essencial para layouts como `RecyclerViews` e `LazyGrids`, pois calcula quantos itens de um tamanho Dp específico cabem em um contêiner (View ou Composable).

| Ambiente | Uso | Parâmetros Chave |
| :--- | :--- | :--- |
| **Views/XML** | `AppDimens.calculateAvailableItemCount(...)` | `containerSizePx` (largura real), `itemSizeDp`, `itemMarginDp` |
| **Compose** | `AppDimens.CalculateAvailableItemCount(...)` | `itemSize: Dp`, `itemPadding: Dp`, `onResult: (Int) -> Unit` |

**Exemplo (Views/Kotlin):**

```kotlin
// Determina o número de colunas para um GridLayoutManager
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width, // Largura em PX
    itemSizeDp = 100f,
    itemMarginDp = 8f, // 8dp total de padding
    resources = resources
)
// layoutManager = GridLayoutManager(context, spanCount)
```
