# 🚀 AppDimens: Guía Rápida y Resumen de la Biblioteca

> Idiomas: [English](../../../../Android/appdimens_all/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_all/README.md) | [हिन्दी](../../hi/Android/appdimens_all/README.md) | [Русский](../../ru/Android/appdimens_all/README.md) | [中文](../../zh/Android/appdimens_all/README.md) | [日本語](../../ja/Android/appdimens_all/README.md)

**AppDimens** es un sistema de gestión de dimensiones para Android (Views y Compose) orientado a la **responsividad matemática**.

- **Modelos**: Fixed (FX) para ajuste sutil y Dynamic (DY) para ajuste proporcional.
- **Compose**: `.fxdp`, `.dydp`, `.fxsp`, `.dysp`.
- **Views/XML**: `AppDimens.fixedPx`, `AppDimens.dynamicPx`, `AppDimens.dynamicPercentagePx`.
- **Unidades físicas**: mm/cm/pulg → Dp/Sp/Px.
- **Utilidad de layout**: `calculateAvailableItemCount`.

```kotlin
// Ejemplos (Compose)
val fixedButton = 56.fxdp
val dynamicWidth = 100.dydp
```

- Documentación: `Android/DOCS/`
- Licencia: `LICENSE`
