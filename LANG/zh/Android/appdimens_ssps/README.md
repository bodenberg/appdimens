<div align="center">
    <h1>📐 AppDimens SSP</h1>
    <p><strong>带条件规则的动态文本缩放</strong></p>
    <p>SSP（Scaled SP），通过优先级条件规则为 Compose 与 XML 提供响应式排版。</p>

[![版本](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![许可证](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![平台](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> 语言: [English](../../../../Android/appdimens_ssps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_ssps/README.md) | [Español](../../es/Android/appdimens_ssps/README.md) | [हिन्दी](../../hi/Android/appdimens_ssps/README.md) | [Русский](../../ru/Android/appdimens_ssps/README.md) | [日本語](../../ja/Android/appdimens_ssps/README.md)

---

## 🎯 概述
- 文本扩展：`.ssp`、`.hsp`、`.wsp`（遵循无障碍），`.sem/.hem/.wem`（忽略无障碍）
- 基于优先级的条件规则（UiMode + 限定符）

## 🚀 安装
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-ssps:1.0.5") }
```

## 🎨 示例（Compose）
```kotlin
Text("标题", fontSize = 24.ssp)
Text("UI 标签", fontSize = 12.sem)
```

## 📄 XML
```xml
<TextView android:textSize="@dimen/_16ssp" />
```

## 📚 参考
- 文档：`Android/DOCS/`
- 许可证：`LICENSE`
