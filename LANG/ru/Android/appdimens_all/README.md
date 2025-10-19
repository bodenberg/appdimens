# 🚀 AppDimens: Краткое руководство и обзор библиотеки

> Языки: [English](../../../../Android/appdimens_all/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_all/README.md) | [Español](../../es/Android/appdimens_all/README.md) | [हिन्दी](../../hi/Android/appdimens_all/README.md) | [中文](../../zh/Android/appdimens_all/README.md) | [日本語](../../ja/Android/appdimens_all/README.md)

**AppDimens** — система управления размерами для Android (Views и Compose) с акцентом на математическую адаптивность.

- **Модели**: Fixed (FX) — тонкая корректировка; Dynamic (DY) — пропорциональная.
- **Compose**: `.fxdp`, `.dydp`, `.fxsp`, `.dysp`.
- **Views/XML**: `AppDimens.fixedPx`, `AppDimens.dynamicPx`, `AppDimens.dynamicPercentagePx`.
- **Физические единицы**: мм/см/дюйм → Dp/Sp/Px.
- **Утилита макета**: `calculateAvailableItemCount`.

```kotlin
// Примеры (Compose)
val fixedButton = 56.fxdp
val dynamicWidth = 100.dydp
```

- Документация: `Android/DOCS/`
- Лицензия: `LICENSE`
