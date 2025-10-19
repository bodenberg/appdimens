<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Базовая система адаптивных размеров для Android</strong></p>
    <p>Модуль с моделями Fixed и Dynamic для Jetpack Compose, Views/XML и Data Binding.</p>

[![Версия](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Лицензия](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Платформа](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> Языки: [English](../../../../Android/appdimens_dynamic/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_dynamic/README.md) | [Español](../../es/Android/appdimens_dynamic/README.md) | [हिन्दी](../../hi/Android/appdimens_dynamic/README.md) | [中文](../../zh/Android/appdimens_dynamic/README.md) | [日本語](../../ja/Android/appdimens_dynamic/README.md)

---

## 🎯 Обзор
- **Fixed (FX)**: логарифмическая корректировка; кнопки, отступы, иконки, шрифты
- **Dynamic (DY)**: пропорциональная; ширины/высоты контейнеров, сетки

## 🚀 Установка
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-dynamic:1.0.5") }
```

## 🎨 Примеры (Compose)
```kotlin
Text("Заголовок", fontSize = 24.fxsp)
Button(modifier = Modifier.height(48.fxdp).width(120.fxdp)) { Text("Действие") }
Card(modifier = Modifier.fillMaxWidth().height(100.dydp).padding(8.fxdp)) { }
```

## 📄 XML/Data Binding
Атрибуты: `app:dynamicWidthDp`, `app:dynamicHeightDp`, `app:dynamicTextSizeDp`.

## 📏 Физические единицы
`5.0f.toDp(UnitType.MM)`, `2.0f.toSp(UnitType.CM)`, `1.0f.toPx(UnitType.INCH)`

## 🧮 Утилиты компоновки
`AppDimens.CalculateAvailableItemCount(...)`

## 📚 Справка
- Документация: `Android/DOCS/`
- Лицензия: `LICENSE`
