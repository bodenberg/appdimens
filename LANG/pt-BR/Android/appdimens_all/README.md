---
layout: default
title: "🚀 AppDimens: Guia Rápido e Visão Geral da Biblioteca"
---

# 🚀 AppDimens: Guia Rápido e Visão Geral da Biblioteca

> **Idiomas:** [English](../../../../Android/appdimens_all/README.md) | Português (BR) | [Español](../../es/Android/appdimens_all/README.md)

**AppDimens** é um sistema de gerenciamento de dimensões para Android (Views e Compose) com foco em responsividade matemática.

- **Modelos**: Fixed (FX) para ajustes sutis e Dynamic (DY) para ajustes proporcionais.
- **Compose**: extensões `.fxdp`, `.dydp`, `.fxsp`, `.dysp`.
- **Views/XML**: `AppDimens.fixedPx`, `AppDimens.dynamicPx`, `AppDimens.dynamicPercentagePx`.
- **Unidades físicas**: conversões mm/cm/pol para Dp/Sp/Px.
- **Utilitário de layout**: `calculateAvailableItemCount`.

```kotlin
// Exemplos (Compose)
val fixedButton = 56.fxdp
val dynamicWidth = 100.dydp
```

- Documentação: `Android/DOCS/`
- Licença: `LICENSE`
