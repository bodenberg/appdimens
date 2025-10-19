# 🚀 AppDimens：快速指南与库概览

> 语言: [English](../../../../Android/appdimens_all/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_all/README.md) | [Español](../../es/Android/appdimens_all/README.md) | [हिन्दी](../../hi/Android/appdimens_all/README.md) | [Русский](../../ru/Android/appdimens_all/README.md) | [日本語](../../ja/Android/appdimens_all/README.md)

**AppDimens** 是面向 Android（Views/Compose）的**数学响应式**尺寸系统。

- **模型**：Fixed (FX) 细致平滑；Dynamic (DY) 按比例。
- **Compose**：`.fxdp`、`.dydp`、`.fxsp`、`.dysp`。
- **Views/XML**：`AppDimens.fixedPx`、`AppDimens.dynamicPx`、`AppDimens.dynamicPercentagePx`。
- **物理单位**：毫米/厘米/英寸 → Dp/Sp/Px。
- **布局工具**：`calculateAvailableItemCount`。

```kotlin
// 示例（Compose）
val fixedButton = 56.fxdp
val dynamicWidth = 100.dydp
```

- 文档：`Android/DOCS/`
- 许可证：`LICENSE`
