# 📐 AppDimens SSP: Escalabilidade de Texto Dinâmica com Lógica Condicional

A biblioteca `AppDimens` oferece um sistema refinado para garantir a consistência do layout em diferentes telas, e o módulo **AppDimens SSP** estende essa capacidade para o escalonamento de unidades de texto (**Sp**) no Compose, adicionando lógica condicional e suporte a qualificadores de tela.

O módulo permite que você defina valores de `TextUnit` (Sp) específicos com base no **Modo de UI** (Carro, TV, Relógio) e em **Qualificadores de DP** (Largura Mínima, Altura, Largura), com um sistema de prioridades claro.

## 🚀 Utilização da Classe `Scaled`

A classe `Scaled` permite que você defina um valor base de `TextUnit` e aplique regras condicionais para substituí-lo em diferentes configurações de tela.

### 1\. Iniciação da Cadeia

Você pode iniciar a cadeia de construção `Scaled` a partir de um `Int` ou um `TextUnit`.

| Função | Descrição |
| :--- | :--- |
| `Int.scaledSp()` | Inicia a cadeia a partir de um valor `Int` (será convertido para `Sp`). |
| `TextUnit.scaledSp()` | Inicia a cadeia a partir de um valor `TextUnit` existente. |

#### Exemplo de Iniciação:

```kotlin
// Inicia com o valor base de 16sp
val baseText = 16.scaledSp()

// Ou a partir de um TextUnit já definido
val baseText = 16.sp.scaledSp()
```

-----

### 2\. Definição de Regras Condicionais

A classe `Scaled` oferece três níveis de prioridade para a definição de regras, garantindo que a regra mais específica seja aplicada primeiro. As regras são avaliadas na ordem de **menor prioridade (1)** para a **maior prioridade (3)**.

A ordem de resolução é crucial: a lista de entradas é classificada por **prioridade ascendente** e, secundariamente, pelo **valor do qualificador de DP descendente** (o maior valor de DP é avaliado primeiro).

| Prioridade | Método | Condição para Aplicação |
| :---: | :--- | :--- |
| **1 (Mais Alta)** | `screen(uiModeType, qualifierType, qualifierValue, customValue)` | O **Modo de UI** e o **Qualificador de DP** devem **casar**. |
| **2 (Média)** | `screen(type: UiModeType, customValue)` | Apenas o **Modo de UI** deve **casar**. |
| **3 (Mais Baixa)** | `screen(type: DpQualifier, value, customValue)` | Apenas o **Qualificador de DP** deve ser **maior ou igual** ao valor. |

#### 📝 Exemplo Detalhado de Uso:

Este exemplo demonstra como definir diferentes tamanhos de texto para diferentes dispositivos e tamanhos de tela:

```kotlin
@Composable
fun TitleText() {
    val titleSize = 24.scaledSp() // Valor base em 24sp
        // Prioridade 1 (Máxima)
        // Se for um 'Carro' E a largura mínima for >= 720dp, use 48sp.
        .screen(
            uiModeType = UiModeType.CAR,
            qualifierType = DpQualifier.SMALL_WIDTH,
            qualifierValue = 720,
            customValue = 48.sp // Tipo TextUnit
        )
        // Se for um 'Relógio' E a largura for >= 200dp, use 12sp.
        .screen(
            uiModeType = UiModeType.WATCH,
            qualifierType = DpQualifier.WIDTH,
            qualifierValue = 200,
            customValue = 12 // Tipo Int (será convertido para Sp)
        )
        // Prioridade 2 (Média)
        // Se for um 'Televisão' (independente do DP), use 40sp.
        .screen(
            type = UiModeType.TELEVISION,
            customValue = 40.sp
        )
        // Prioridade 3 (Mais Baixa)
        // Se a tela tiver 'Largura Mínima' >= 600dp (Tablet), use 32sp.
        .screen(
            type = DpQualifier.SMALL_WIDTH,
            value = 600,
            customValue = 32.sp
        )

    Text(
        text = "Título Dinâmico",
        // A resolução final é feita aqui, aplicando o escalonamento dinâmico
        // com base no qualificador Smallest Width.
        fontSize = titleSize.ssp 
    )
}
```

-----

### 3\. Resolução Final do Valor (Getter Composable)

Após definir todas as regras, o valor final é resolvido usando um dos *getters* de propriedade `Composable`. O *getter* determina o **qualificador de dimensionamento dinâmico** a ser aplicado no final.

| Getter | Qualificador Base (Escalonamento Dinâmico) | Uso Ideal |
| :--- | :--- | :--- |
| **`.ssp`** | **Smallest Width (sw)**: `DpQualifier.SMALL_WIDTH` | Padrão, utiliza a dimensão mais restritiva (`smallestScreenWidthDp`) para o escalonamento base. |
| **`.hsp`** | **Height (h)**: `DpQualifier.HEIGHT` | Para elementos que devem escalar com base na altura da tela (`screenHeightDp`). |
| **`.wsp`** | **Width (w)**: `DpQualifier.WIDTH` | Para elementos que devem escalar com base na largura da tela (`screenWidthDp`). |
| **`.sem`** | **Smallest Width (sw)**: `DpQualifier.SMALL_WIDTH` | Padrão, utiliza a dimensão mais restritiva (`smallestScreenWidthDp`) para o escalonamento base, ignora escala da fonte da acessibilidade. |
| **`.hem`** | **Height (h)**: `DpQualifier.HEIGHT` | Para elementos que devem escalar com base na altura da tela (`screenHeightDp`) ignora escala da fonte da acessibilidade. |
| **`.wem`** | **Width (w)**: `DpQualifier.WIDTH` | Para elementos que devem escalar com base na largura da tela (`screenWidthDp`). ignora escala da fonte da acessibilidade. |

#### O Processo de Resolução (`resolve`):

1.  Lê a configuração de tela atual (`LocalConfiguration.current`).
2.  Itera sobre as entradas customizadas, da prioridade **1** para a **3**.
3.  Se uma regra for qualificada (ex: `uiModeType == CAR` **E** `smallestWidthDp >= 720`), o valor `customValue` dessa regra é selecionado.
4.  Se nenhuma regra customizada casar, o `initialBaseSp` é usado.
5.  O valor de `TextUnit` selecionado (`customValue` ou `initialBaseSp`) tem seu valor inteiro extraído (`.value.toInt()`).
6.  O escalonamento dinâmico é aplicado a este valor inteiro usando a lógica `toDynamicScaledSp` e o qualificador (`s`, `h` ou `w`) definido pelo *getter* (`.ssp`, `.hsp`, `.wsp`).

-----

## ⚙️ Extensões de Escalamento Direto

Para escalonamento de texto simples, sem lógica condicional (como a classe `Scaled`), você pode usar as extensões diretas em `Int`. Elas aplicam o escalonamento dinâmico imediatamente, buscando um recurso de dimensão pré-calculado (ex: `_16sdp`).

| Extensão | Qualificador Base (Escalonamento Dinâmico) | Recurso Buscado (Ex: para 16) |
| :--- | :--- | :--- |
| **`Int.ssp`** | `DpQualifier.SMALL_WIDTH` | `_16ssp` |
| **`Int.hsp`** | `DpQualifier.HEIGHT` | `_16hsp` |
| **`Int.wsp`** | `DpQualifier.WIDTH` | `_16wsp` |
| **`Int.sem`** | `DpQualifier.SMALL_WIDTH` | `_16sem` |
| **`Int.hem`** | `DpQualifier.HEIGHT` | `_16hem` |
| **`Int.wem`** | `DpQualifier.WIDTH` | `_16wem` |

#### Exemplo de Uso das Extensões:

```kotlin
@Composable
fun SimpleText() {
    Text(
        // Aplica o escalonamento dinâmico SW (Smallest Width)
        fontSize = 18.ssp 
    )
    Text(
        // Aplica o escalonamento dinâmico H (Height)
        fontSize = 18.hsp 
    )
}
```

-----

## 💻 Suporte a XML Views e Recursos de Dimensão

A funcionalidade principal do escalonamento dinâmico (**ssp**, **hsp**, **wsp**) baseia-se na busca por **recursos de dimensão pré-calculados** no seu projeto Android. Para usar esses valores em XML, seu projeto deve ter gerado os arquivos `dimens.xml` contendo as entradas necessárias.

### Formato dos Recursos de Dimensão

O `AppDimens SSP` espera que os recursos de texto escalados sigam o padrão:

`@dimen/_<valor><qualificador>sp`

Onde:

  * **`<valor>`**: O valor base da unidade (ex: `10`, `16`, `24`).
  * **`<qualificador>`**: O tipo de escalonamento:
      * **`s`** para **Smallest Width** (Largura Mínima).
      * **`h`** para **Height** (Altura).
      * **`w`** para **Width** (Largura).

### Como Usar em XML

Você pode usar esses recursos diretamente em atributos de tamanho de texto (`android:textSize`) em seus arquivos de layout XML:

#### Exemplos de Uso em XML:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Texto Escalonado por SW (ssp)"
        android:textSize="@dimen/_16ssp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Texto Escalonado por Altura (hsp)"
        android:textSize="@dimen/_18hsp" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Texto Escalonado por Largura (wsp)"
        android:textSize="@dimen/_14wsp" />

</LinearLayout>
```

### Importante: Lógica do `Scaled` vs. XML

É fundamental notar que a **lógica condicional** da classe `Scaled` (com prioridades de `UiModeType` e `DpQualifier`) **não se aplica** ao uso direto dos recursos `@dimen/` em XML.

  * **Uso em XML (`@dimen/_16ssp`)**: Você obtém o valor **escalado dinamicamente** do recurso `dimen` pré-calculado.
  * **Uso no Compose (`16.scaledSp().screen(...).ssp`)**: Você obtém o valor base **condicionalmente ajustado** (ex: trocando 16sp para 24sp em tablet), e *depois* o escalonamento dinâmico (`ssp`, `hsp`, `wsp`, `wem`, `wem` ou `wem`) é aplicado a esse novo valor base.

Para a **customização condicional completa**, você deve usar a classe `Scaled` no **Compose** e não os recursos diretos em XML. O uso em XML é ideal para aplicar apenas o **escalonamento dinâmico** puro.
