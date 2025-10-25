---
layout: default
title: "🚀 AppDimens iOS - Guia de Uso"
---

# 🚀 AppDimens iOS - Guia de Uso

> **Idiomas:** [English](../../../iOS/USAGE_GUIDE.md) | Português (BR) | [Español](../../es/iOS/USAGE_GUIDE.md)

Este guia mostra como usar AppDimens iOS com exemplos práticos e boas práticas.

## Conceitos Básicos
- Modelos: Fixed (FX) e Dynamic (DY)
- Unidades físicas: `.cm`, `.mm`, `.inch`
- Integração SwiftUI e UIKit

## Exemplos Rápidos
```swift
Text("Título").font(.fxSystem(size: 24)).fxPadding(16)
Rectangle().fxFrame(width: 200, height: 100).fxCornerRadius(12)
```

## Dicas
- Use Fixed para elementos de UI. Use Dynamic para containers.
- Cacheie dimensões frequentes.
- Evite cadeias de cálculo muito longas.
