<div align="center">
    <h1>📐 AppDimens SDP</h1>
    <p><strong>Динамическое масштабирование с условными правилами</strong></p>
    <p>SDP (Scaled DP) с приоритетными правилами для адаптивных макетов (Compose и XML).</p>

[![Версия](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Лицензия](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Платформа](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> Языки: [English](../../../../Android/appdimens_sdps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_sdps/README.md) | [Español](../../es/Android/appdimens_sdps/README.md) | [हिन्दी](../../hi/Android/appdimens_sdps/README.md) | [中文](../../zh/Android/appdimens_sdps/README.md) | [日本語](../../ja/Android/appdimens_sdps/README.md)

---

## 🎯 Обзор
- Прямые расширения: `.sdp`, `.hdp`, `.wdp`
- Условные правила по приоритету (UiMode + квалификаторы)
- Нулевые накладные расходы при использовании предрасчитанных ресурсов

## 🚀 Установка
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-sdps:1.0.6") }
```

## 🎨 Примеры (Compose)
```kotlin
Column(Modifier.padding(16.sdp)) {
    Text("Текст", fontSize = 18.ssp)
    Spacer(Modifier.height(18.sdp).width(100.wdp))
    Card(Modifier.size(120.sdp).padding(8.hdp)) { }
}
```

### Условные правила
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

## 📐 Квалификаторы
- SDP: минимальная ширина (sw)
- WDP: ширина (w)
- HDP: высота (h)

## ⚡ Производительность
- Расширения: нулевая нагрузка
- Правила: ~0.001ms (кэш на конфигурацию)

## 📚 Справка
- Документация: `Android/DOCS/`
- Лицензия: `LICENSE`
