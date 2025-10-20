<div align="center">
    <h1>📐 AppDimens SSP</h1>
    <p><strong>Динамическое масштабирование текста с условными правилами</strong></p>
    <p>SSP (Scaled SP) с приоритетными правилами для адаптивной типографики в Compose и XML.</p>

[![Версия](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Лицензия](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Платформа](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> Языки: [English](../../../../Android/appdimens_ssps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_ssps/README.md) | [Español](../../es/Android/appdimens_ssps/README.md) | [हिन्दी](../../hi/Android/appdimens_ssps/README.md) | [中文](../../zh/Android/appdimens_ssps/README.md) | [日本語](../../ja/Android/appdimens_ssps/README.md)

---

## 🎯 Обзор
- Текстовые расширения: `.ssp`, `.hsp`, `.wsp` (учитывают доступность) и `.sem/.hem/.wem` (игнорируют доступность)
- Условные правила по приоритету (UiMode + квалификаторы)

## 🚀 Установка
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-ssps:1.0.6") }
```

## 🎨 Примеры (Compose)
```kotlin
Text("Заголовок", fontSize = 24.ssp)
Text("UI метка", fontSize = 12.sem)
```

## 📄 XML
```xml
<TextView android:textSize="@dimen/_16ssp" />
```

## 📚 Справка
- Документация: `Android/DOCS/`
- Лицензия: `LICENSE`
