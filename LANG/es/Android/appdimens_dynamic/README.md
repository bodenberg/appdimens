---
layout: default
---

<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Dimensionamiento Responsivo Core para Android</strong></p>
    <p>Módulo esencial con modelos Fixed y Dynamic para Jetpack Compose, Views/XML y Data Binding.</p>

[![Versión](https://img.shields.io/badge/version-1.1.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> **Idiomas:** [English](../../../../appdimens-dynamic/README.md) | [Português (BR)](../../../pt-BR/Android/appdimens_dynamic/README.md) | Español

---

## 🎯 Descripción General

Modelos:
- **Fixed (FX)**: ajuste logarítmico; ideal para botones, paddings, iconos y fuentes.
- **Dynamic (DY)**: proporcional; ideal para anchos/altos de contenedores y grids.

## 🚀 Instalación
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-dynamic:1.1.0") }
```

## 🎨 Ejemplos (Compose)
```kotlin
Text("Título", fontSize = 24.ssp)
Button(modifier = Modifier.height(48.sdp).width(120.sdp)) { Text("Acción") }
Card(modifier = Modifier.fillMaxWidth().height(100.wdp).padding(8.sdp)) { /* ... */ }
```

## 📄 XML/Data Binding
Atributos: `app:dynamicWidthDp`, `app:dynamicHeightDp`, `app:dynamicTextSizeDp`.

## 📏 Unidades Físicas
`5.0f.toDp(UnitType.MM)`, `2.0f.toSp(UnitType.CM)`, `1.0f.toPx(UnitType.INCH)`.

## 🧮 Utilidades de Layout
`AppDimens.CalculateAvailableItemCount(itemSize, itemPadding, direction, onResult)`.

## 📚 Referencia
- Documentación: `Android/DOCS/`
- Licencia: `LICENSE`
