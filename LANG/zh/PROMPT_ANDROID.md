# 面向 Android 的 AppDimens 库 AI 提示

> 语言: [English](../../PROMPT_ANDROID.md) | [Português (BR)](../pt-BR/PROMPT_ANDROID.md) | [Español](../es/PROMPT_ANDROID.md) | [हिन्दी](../hi/PROMPT_ANDROID.md) | [Русский](../ru/PROMPT_ANDROID.md) | [日本語](../ja/PROMPT_ANDROID.md)

你是一名资深 Android 开发 AI。AppDimens 是用于 Views 与 Jetpack Compose 的高级尺寸管理系统。

## 1. 安装 / 设置

Github: [AppDimens Repository](https://github.com/Bodenberg/AppDimens)

在 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.6")
    implementation("io.github.bodenberg:appdimens-sdps:1.0.6")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.6")
    implementation("io.github.bodenberg:appdimens-all:1.0.6")
}

mavenCentral()
```

或 JitPack：

```kotlin
dependencies {
    implementation("com.github.bodenberg.appdimens:appdimens-dynamic:1.0.6")
    implementation("com.github.bodenberg.appdimens:appdimens-sdps:1.0.6")
    implementation("com.github.bodenberg.appdimens:appdimens-ssps:1.0.6")
    implementation("com.github.bodenberg.appdimens:appdimens-all:1.0.6")
}

maven { url 'https://jitpack.io' }
```

## 2. 初始化

无需手动初始化。

## 3. 核心概念

- Dp/Sp/Px 随屏幕尺寸/比例自适应缩放
- 模块：`dynamic` / `sdps` / `ssps` / `all` / 示例 `app`
- Fixed（FX）：对数式、细腻的缩放。按钮/内边距/图标/标题
- Dynamic（DY）：按比例缩放。容器与布局

## 4. 用法

### Compose
- Fixed: `.fxdp`, `.fxsp`, `.fxem`, `.fxpx`
- Dynamic: `.dydp`, `.dysp`, `.dyem`, `.dypx`
- 百分比: `AppDimens.dynamicPercentageDp(0.5f)`

### Views/XML
- `AppDimens.fixed(16f).dp`, `AppDimens.dynamic(100f).dp`
- Data Binding：参见 `app` 模块的 `DimensBindingAdapters`

## 5. Data Binding 示例

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <data>
        <import type="com.appdimens.dynamic.code.AppDimens"/>
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        app:dynamicHeightDp="@{100f}"
        app:dynamicWidthDp="@{100f}">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Title with Dynamic Font"
            app:dynamicTextSizeDp="@{20f}"/>
    </LinearLayout>
</layout>
```

## 6. 高级特性

- `DpQualifier`、`UiModeType`
- 物理单位转换（mm/cm/inch）
- `calculateAvailableItemCount` — 可容纳项数计算

## 7. 模块选择

- 默认：`appdimens_dynamic`
- 资源式：`sdps` / `ssps`
- 打包：`all`

## 8. 性能

计算结果缓存，开销极小。

## 9. 测试

UI 测试正常工作；单元测试可使用 mock 获得确定性值。
