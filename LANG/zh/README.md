<div align="center">
    <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens - 所有设备的响应式设计" height="300"/>
    <h1>📐 AppDimens</h1>
    <p><strong>Android 和 iOS 的智能响应式尺寸系统</strong></p>
    <p>数学精确的响应式缩放，确保您的 UI 设计完美适配任何屏幕尺寸或宽高比 — 从手机到电视、汽车和可穿戴设备。</p>

[![版本](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![许可证](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![平台](https://img.shields.io/badge/platform-Android%20%7C%20iOS-orange.svg)](https://github.com/bodenberg/appdimens)
[![文档](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

> 语言: [English](../../README.md) | [Português (BR)](../pt-BR/README.md) | [Español](../es/README.md) | [हिन्दी](../hi/README.md) | [Русский](../ru/README.md) | [日本語](../ja/README.md)

---

## 🎯 什么是 AppDimens？

**AppDimens** 是一个综合的尺寸管理系统，它用基于实际屏幕特性智能缩放的尺寸替代固定像素值。虽然传统的 DP/Points 是常量，AppDimens 将它们视为基础值，在不同的屏幕尺寸、密度和宽高比下可预测地缩放。

### 🎨 主要优势

- **🎯 视觉一致性**：在所有设备类型上保持完美比例
- **📱 通用兼容性**：在手机、平板电脑、电视、汽车和可穿戴设备上无缝工作
- **⚡ 性能优化**：通过缓存计算实现最小运行时开销
- **🔧 轻松集成**：简单的 API，适用于 Jetpack Compose、XML Views、SwiftUI 和 UIKit
- **📐 数学精度**：两种缩放模型（Fixed 和 Dynamic）满足不同的设计需求

---

## 🚀 快速开始

### Android

```kotlin
dependencies {
    // 核心库（Dynamic + Fixed scaling）
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.8")
    
    // 可选：SDP 和 SSP scaling
    implementation("io.github.bodenberg:appdimens-sdps:1.0.8")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.8")
    
    // 一体化包（不包括游戏模块）
    implementation("io.github.bodenberg:appdimens-all:1.0.8")
    
    // 支持 C++/NDK 的游戏开发（单独依赖）
    implementation("io.github.bodenberg:appdimens-games:1.0.8")
}
```

### iOS

```ruby
# Podfile
pod 'AppDimens'
```

```swift
// Swift Package Manager
.package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.8")
```

---

## 🧠 核心尺寸模型

| 模型 | 理念 | 理想用例 | 支持的平台 | 实现 |
|-------|------------|----------------|-------------------|----------------|
| **Fixed (FX)** | 对数缩放（精细） | 按钮、内边距、外边距、图标 | Android + iOS | 数学比例调整 |
| **Dynamic (DY)** | 比例缩放（激进） | 容器、网格、流动字体 | Android + iOS | 基于屏幕的比例缩放 |
| **SDP / SSP** | 预计算资源 | XML 中直接使用 `@dimen` | Android | 426+ 预生成的尺寸文件 |
| **物理单位** | mm/cm/inch → Dp/Sp/Px/Points | 可穿戴设备、打印、精确布局 | Android + iOS | 真实世界测量的转换 |
| **游戏尺寸** | 游戏专用缩放 | 游戏UI、视口、Metal/OpenGL | Android + iOS | 原生C++/NDK + Metal实现 |

---

## 📱 平台示例

### 🤖 Android - Jetpack Compose

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.dydp)           // 动态宽度
            .height(200.fxdp)          // 固定高度
            .padding(16.fxdp)          // 固定内边距
    ) {
        Column(
            modifier = Modifier.padding(16.fxdp)
        ) {
            Text(
                text = "响应式标题",
                fontSize = 18.fxsp     // 固定字体大小
            )
            Text(
                text = "此卡片适配任何屏幕尺寸",
                fontSize = 14.dysp     // 动态字体大小
            )
        }
    }
}
```

### 🍎 iOS - SwiftUI

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("响应式标题")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("此卡片适配任何屏幕尺寸")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // 动态宽度
        .fxFrame(height: 200)          // 固定高度
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

### 📄 Android - XML Views

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="@dimen/_16sdp">
    
    <TextView
        android:layout_width="@dimen/_300sdp"
        android:layout_height="wrap_content"
        android:textSize="@dimen/_18ssp"
        android:text="响应式文本" />
        
    <Button
        android:layout_width="@dimen/_120sdp"
        android:layout_height="@dimen/_48sdp"
        android:text="操作" />
</LinearLayout>
```

---

## 🎨 高级功能

### 🔄 条件缩放

```kotlin
// Android
val buttonSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

```swift
// iOS
let buttonSize = AppDimens.fixed(80)
    .screen(.watch, 40)           // Apple Watch 为 40pt
    .screen(.tablet, 120)         // iPad 为 120pt
    .aspectRatio(enable: true)    // 启用宽高比调整
    .toPoints()
```

### 📏 物理单位

```kotlin
// Android
val marginPx = AppDimensPhysicalUnits.toMm(5f, resources)
view.setPadding(marginPx.toInt(), 0, 0, 0)
```

```swift
// iOS
Rectangle()
    .frame(width: 2.cm, height: 1.cm)  // 物理单位
```

### 🧮 布局工具

```kotlin
// Android - 计算最佳网格列数
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

---

## 📊 性能和兼容性

### ⚡ 性能特性

| 功能 | 运行时开销 | 内存使用 | 计算时间 |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | 按配置缓存 |
| **SDP/SSP** | 零 | ~2MB（资源） | 预计算 |
| **物理单位** | ~0.002ms | ~10KB | 按需 |

### 📱 平台支持

| 平台 | 最低版本 | UI 框架 | 特殊功能 |
|----------|-------------|---------------|------------------|
| **Android** | API 21+ | Compose、Views、Data Binding | SDP/SSP、物理单位 |
| **iOS** | 13.0+ | SwiftUI、UIKit | 原生扩展 |

---

## 📚 文档和资源

### 📖 完整文档

- **[📘 完整文档](https://appdimens-project.web.app/)** - 全面的指南和 API 参考
- **[🤖 Android 指南](../../Android/README.md)** - Android 特定文档
- **[🍎 iOS 指南](../../iOS/README.md)** - iOS 特定文档
- **[🎮 游戏模块](../../Android/appdimens_games/README.md)** - 使用 C++/NDK 进行游戏开发

### 🎯 快速链接

- **[🚀 安装指南](../../Android/README.md#installation)** - 几分钟内开始
- **[📱 示例](../../Android/app/src/main/kotlin/)** - 真实世界的使用示例
- **[🔧 API 参考](../../Android/DOCS/)** - 完整的 API 文档
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - 常见问题和答案

---

## 🎯 用例

### 📱 移动应用
非常适合需要在不同手机尺寸和方向上工作的应用。

### 📺 电视和汽车应用
非常适合具有不同屏幕尺寸的 Android TV 和 Android Auto 应用程序。

### ⌚ 可穿戴应用
对于需要适配不同手表尺寸的 Wear OS 应用至关重要。

### 🎮 游戏开发
专门的游戏开发模块，支持 C++/NDK 和 OpenGL 集成。

### 🏢 企业应用
非常适合需要在平板电脑、手机和桌面上工作的商业应用程序。

---

## 🤝 贡献

我们欢迎贡献！请查看我们的[贡献指南](CONTRIBUTING.md)了解详情。

### 🐛 发现错误？
- [创建 issue](https://github.com/bodenberg/appdimens/issues)
- 包括设备信息和重现步骤
- 如果适用，请附上截图

### 💡 有想法？
- [开始讨论](https://github.com/bodenberg/appdimens/discussions)
- 提出新功能或改进建议
- 分享您的用例

---

## 📄 许可证

本项目采用 Apache License 2.0 许可 - 详见 [LICENSE](../../LICENSE) 文件。

---

## 👨‍💻 作者

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 表达您的支持

如果 AppDimens 帮助了您的项目，请考虑：

- ⭐ **给这个仓库加星**
- 🐦 **在社交媒体上分享**
- 📝 **撰写**评论或博客文章
- 🤝 **贡献**代码或文档

---

<div align="center">
    <p><strong>为移动开发社区用 ❤️ 制作</strong></p>
    <p>AppDimens - 响应式设计与数学精度的完美结合</p>
</div>
