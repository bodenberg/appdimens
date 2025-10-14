<div align="center">
    <img src="IMAGES/image_sample_devices.png" alt="sample" height="250"/>
</div>

---


# 📐 AppDimens — Dimensionamento Inteligente e Responsivo para Android

**AppDimens** é uma biblioteca que fornece **dimensionamento matematicamente responsivo**, garantindo que o design da sua UI se adapte perfeitamente a qualquer tamanho ou proporção de tela — de **celulares** a **TVs**, **carros** e **wearables**.
Funciona com **Jetpack Compose**, **Views (XML)** e **Data Binding**.

---

## 🚀 Instalação

```kotlin
dependencies {
    // Core (Dynamic + Fixed)
    implementation("com.github.bodenberg.appdimens:appdimens-dynamic:1.0.2")

    // SDP & SSP scaling (optional)
    implementation("com.github.bodenberg.appdimens:appdimens-sdps:1.0.2")
    implementation("com.github.bodenberg.appdimens:appdimens-ssps:1.0.2")

    // All in one
    implementation("com.github.bodenberg.appdimens:appdimens-all:1.0.2")
}

maven { url 'https://jitpack.io' } //or maven central
```

```kotlin
dependencies {
    // Core (Dynamic + Fixed)
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.2")

    // SDP & SSP scaling (optional)
    implementation("io.github.bodenberg:appdimens-sdps:1.0.2")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.2")

    // All in one
    implementation("io.github.bodenberg:appdimens-all:1.0.2")
}

mavenCentral()
```

---

<<<<<<< HEAD

=======
>>>>>>> 0ed509487a713c1468a65e705fafd5cfc836ac3b
## 🧠 Modelos de Dimensionamento

| Modelo             | Filosofia                  | Uso Ideal                                      | Suporte                        |
| ------------------ | -------------------------- | ---------------------------------------------- | ------------------------------ |
| **Fixed (FX)**     | Logarítmico (refinado)     | Botões, paddings, margens, ícones              | Compose + Views + Data Binding |
| **Dynamic (DY)**   | Percentual (proporcional)  | Containers, grids, fontes fluidas              | Compose + Views + Data Binding |
| **SDP / SSP**      | Escalonamento via recursos | Compose + XML (direto em @dimen)               | Compose + Views (XML)          |
| **Physical Units** | mm, cm, inch → Dp/Sp/Px    | Wearables, impressão, layouts físicos precisos | Compose + Views                |

👉 [Ver mais sobre FX e DY](#)
👉 [Ver mais sobre SDP e SSP](#)

---

## 🧩 Jetpack Compose — Exemplos Práticos

### 📏 Fixed e Dynamic (FX / DY)

```kotlin
val fixedPadding = 16.fxdp               // sutil e refinado
val dynamicWidth = 100.dydp              // proporcional à tela
val dynamicText = 18.dysp                // texto proporcional
val fixedEm = 1.2.fxem                   // escalonamento em 'em' (fonte base)
val dynamicEm = 1.0.dyem                 // proporcional em em
```

📝 `.fxdp`, `.fxsp`, `.fxem` usam ajuste logarítmico
📝 `.dydp`, `.dysp`, `.dyem` usam ajuste proporcional

👉 [Ver mais exemplos Compose](#)

---

### 🧮 SDP e SSP no Compose

```kotlin
// Tamanho escalonado via smallest width
val padding = 16.sdp
val textSize = 18.ssp

Box(
    modifier = Modifier
        .padding(padding)
        .size(100.sdp)
) {
    Text(
        text = "Texto Responsivo",
        fontSize = textSize
    )
}
```

✅ `sdp` e `ssp` podem ser usados diretamente em Composables.
👉 [Ver mais sobre SDP/SSP no Compose](#)

---

## 🖼️ XML Views e Data Binding

### 🌐 Dynamic (FX / DY)

Dynamic funciona tanto **em Compose** quanto em **código Java/Kotlin** — inclusive com **Data Binding**.

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
</LinearLayout>
```

```kotlin
val widthPx = AppDimens.dynamicPx(100f, ScreenType.LOWEST, resources)
myView.layoutParams.width = widthPx.toInt()
```

👉 [Ver mais sobre Dynamic com Views](#)

---

### 🧭 SDP e SSP no XML

O `sdp` e `ssp` também funcionam diretamente em XML, pois se baseiam em recursos pré-gerados em `dimens.xml`.

```xml
<TextView
    android:layout_width="@dimen/_49sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp"
    android:text="Exemplo de Texto Responsivo"/>
```

✅ Ideal para quem quer usar a biblioteca sem mudar código existente.
👉 [Ver mais sobre uso XML](#)

---

## 📏 Unidades Físicas (mm, cm, inch)

```kotlin
val marginPx = AppDimensPhysicalUnits.toMm(5f, resources)
view.setPadding(marginPx.toInt(), 0, 0, 0)
```

* `toMm()` / `toCm()` / `toInch()` → Px
* Útil em wearables, layouts precisos e impressão.

👉 [Ver mais sobre Physical Units](#)

---

## 🧮 Utilitário de Layout Dinâmico

```kotlin
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

👉 Garante que `RecyclerView` ou `LazyGrid` tenham **número ideal de colunas** de acordo com a tela.

👉 [Ver mais sobre Layout Utils](#)

---

## 🧭 Regras Condicionais (FIXED/DYNAMIC/SDP/SSP)

```kotlin
val boxSize = 80.dynamicDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)

val titleSize = 24.scaledSp()
    .screen(UiModeType.CAR, DpQualifier.SMALL_WIDTH, 720, 48.sp)
    .screen(DpQualifier.SMALL_WIDTH, 600, 32.sp)
```

✅ `scaledDp()` e `scaledSp()` permitem adaptar dimensões a modo de UI e qualificadores.
👉 [Ver mais sobre lógica condicional](#)

---

## 📚 Compatibilidade

| Recurso                 | Compose |    XML Views    | Data Binding | Notas                                |
| ----------------------- | :-----: | :-------------: | :----------: | ------------------------------------ |
| **Dynamic (FX/DY)**     |    ✅    | ✅ (via recurso) |       ✅      | `.fxdp` / `.dydp` / AppDimens Object |
| **SDP/SSP**             |    ✅    |        ✅        |       ❌      | Baseado em `@dimen` (pré-gerado)     |
| **Physical Units**      |    ✅    |        ✅        |       ✅      | Conversão real                       |
| **Regras Condicionais** |    ✅    | ✅ (via recurso) |       ✅      | Avançado para responsividade         |

---

## 📎 Recursos e Links

* 📘 [Documentação completa](#)
* 🧮 [Exemplos Compose](#)
* 🧰 [Exemplos com Views](#)
* 🧭 [SDP/SSP Avançado](#)
* 🧭 [Conversão de Unidades Físicas](#)

---


