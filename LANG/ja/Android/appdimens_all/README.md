# 🚀 AppDimens: クイックガイドとライブラリ概要

> 言語: [English](../../../../Android/appdimens_all/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_all/README.md) | [Español](../../es/Android/appdimens_all/README.md) | [हिन्दी](../../hi/Android/appdimens_all/README.md) | [Русский](../../ru/Android/appdimens_all/README.md) | [中文](../../zh/Android/appdimens_all/README.md)

**AppDimens** は Android（Views/Compose）向けの**数学的にレスポンシブ**な寸法管理システムです。

- **モデル**: Fixed (FX) は穏やか/精緻、Dynamic (DY) は割合ベース
- **Compose**: `.fxdp`, `.dydp`, `.fxsp`, `.dysp`
- **Views/XML**: `AppDimens.fixedPx`, `AppDimens.dynamicPx`, `AppDimens.dynamicPercentagePx`
- **物理単位**: mm/cm/inch → Dp/Sp/Px
- **レイアウトユーティリティ**: `calculateAvailableItemCount`

```kotlin
// 例（Compose）
val fixedButton = 56.fxdp
val dynamicWidth = 100.dydp
```

- ドキュメント: `Android/DOCS/`
- ライセンス: `LICENSE`
