---
layout: default
---

<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Dimensionamento Responsivo Core para Android</strong></p>
    <p>Módulo essencial com modelos Fixed e Dynamic para Jetpack Compose, Views/XML e Data Binding.</p>

[![Versão](https://img.shields.io/badge/version-1.0.9-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licença](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> **Idiomas:** [English](../../../../Android/appdimens_dynamic/README.md) | Português (BR) | [Español](../../es/Android/appdimens_dynamic/README.md)

---

## 🎯 Visão Geral

Modelos:
- **Fixed (FX)**: ajuste logarítmico; ideal para botões, paddings, ícones, fontes.
- **Dynamic (DY)**: proporcional; ideal para larguras/alturas de containers e grids.

## 🚀 Instalação
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-dynamic:1.0.8") }
```

## 🎨 Exemplos (Compose)
```kotlin
Text("Título", fontSize = 24.fxsp)
Button(modifier = Modifier.height(48.fxdp).width(120.fxdp)) { Text("Ação") }
Card(modifier = Modifier.fillMaxWidth().height(100.dydp).padding(8.fxdp)) { /* ... */ }
```

## 📄 XML/Data Binding
Use atributos como `app:dynamicWidthDp`, `app:dynamicHeightDp`, `app:dynamicTextSizeDp`.

## 📏 Unidades Físicas
`5.0f.toDp(UnitType.MM)`, `2.0f.toSp(UnitType.CM)`, `1.0f.toPx(UnitType.INCH)`.

## 🧮 Utilitários de Layout
`AppDimens.CalculateAvailableItemCount(itemSize, itemPadding, direction, onResult)`.

## 📚 Referência
- Documentação: `Android/DOCS/`
- Licença: `LICENSE`
