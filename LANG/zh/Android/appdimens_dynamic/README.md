<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Android 核心响应式尺寸系统</strong></p>
    <p>为 Jetpack Compose、Views/XML 与 Data Binding 提供 Fixed 与 Dynamic 两种缩放模型。</p>

[![版本](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![许可证](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![平台](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> 语言: [English](../../../../Android/appdimens_dynamic/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_dynamic/README.md) | [Español](../../es/Android/appdimens_dynamic/README.md) | [हिन्दी](../../hi/Android/appdimens_dynamic/README.md) | [Русский](../../ru/Android/appdimens_dynamic/README.md) | [日本語](../../ja/Android/appdimens_dynamic/README.md)

---

## 🎯 概述
- **Fixed (FX)**：对数式精细调节；按钮、内边距、图标、字体
- **Dynamic (DY)**：按比例；容器宽高与网格

## 🚀 安装
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-dynamic:1.0.6") }
```

## 🎨 示例（Compose）
```kotlin
Text("标题", fontSize = 24.fxsp)
Button(modifier = Modifier.height(48.fxdp).width(120.fxdp)) { Text("操作") }
Card(modifier = Modifier.fillMaxWidth().height(100.dydp).padding(8.fxdp)) { }
```

## 📄 XML/Data Binding
`app:dynamicWidthDp`、`app:dynamicHeightDp`、`app:dynamicTextSizeDp`

## 📏 物理单位
`5.0f.toDp(UnitType.MM)`、`2.0f.toSp(UnitType.CM)`、`1.0f.toPx(UnitType.INCH)`

## 🧮 布局工具
`AppDimens.CalculateAvailableItemCount(...)`

## 📚 参考
- 文档：`Android/DOCS/`
- 许可证：`LICENSE`
