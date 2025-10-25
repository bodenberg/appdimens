---
layout: default
---

<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Dimensionamiento Responsivo Core para Android</strong></p>
    <p>Módulo esencial con modelos Fixed y Dynamic para Jetpack Compose, Views/XML y Data Binding.</p>

[![Versión](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> Idiomas: [English](../../../../Android/appdimens_dynamic/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_dynamic/README.md) | [हिन्दी](../../hi/Android/appdimens_dynamic/README.md) | [Русский](../../ru/Android/appdimens_dynamic/README.md) | [中文](../../zh/Android/appdimens_dynamic/README.md) | [日本語](../../ja/Android/appdimens_dynamic/README.md)

---

## 🎯 Descripción General

Modelos:
- **Fixed (FX)**: ajuste logarítmico; ideal para botones, paddings, iconos y fuentes.
- **Dynamic (DY)**: proporcional; ideal para anchos/altos de contenedores y grids.

## 🚀 Instalación
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-dynamic:1.0.8") }
```

## 🎨 Ejemplos (Compose)
```kotlin
Text("Título", fontSize = 24.fxsp)
Button(modifier = Modifier.height(48.fxdp).width(120.fxdp)) { Text("Acción") }
Card(modifier = Modifier.fillMaxWidth().height(100.dydp).padding(8.fxdp)) { /* ... */ }
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
