<div align="center">
    <h1>📐 AppDimens SDP</h1>
    <p><strong>带条件规则的动态缩放</strong></p>
    <p>SDP（Scaled DP），通过优先级条件规则为 Compose 与 XML 提供响应式缩放。</p>

[![版本](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![许可证](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![平台](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> 语言: [English](../../../../Android/appdimens_sdps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_sdps/README.md) | [Español](../../es/Android/appdimens_sdps/README.md) | [हिन्दी](../../hi/Android/appdimens_sdps/README.md) | [Русский](../../ru/Android/appdimens_sdps/README.md) | [日本語](../../ja/Android/appdimens_sdps/README.md)

---

## 🎯 概述
- 直接扩展：`.sdp`、`.hdp`、`.wdp`
- 基于优先级的条件规则（UiMode + 限定符）
- 使用预计算资源时零运行时开销

## 🚀 安装
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-sdps:1.0.8") }
```

## 🎨 示例（Compose）
```kotlin
Column(Modifier.padding(16.sdp)) {
    Text("文本", fontSize = 18.ssp)
    Spacer(Modifier.height(18.sdp).width(100.wdp))
    Card(Modifier.size(120.sdp).padding(8.hdp)) { }
}
```

### 条件规则
```kotlin
val boxSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

## 📄 XML
```xml
<TextView
    android:layout_width="@dimen/_100sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp" />
```

## 📐 限定符
- SDP：最小宽度（sw）
- WDP：宽度（w）
- HDP：高度（h）

## ⚡ 性能
- 扩展：零开销
- 规则：~0.001ms（按配置缓存）

## 📚 参考
- 文档：`Android/DOCS/`
- 许可证：`LICENSE`
