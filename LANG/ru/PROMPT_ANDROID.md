---
layout: default
title: "ИИ-промпт для библиотеки AppDimens (Android)"
---

# ИИ-промпт для библиотеки AppDimens (Android)

> Языки: [English](../../PROMPT_ANDROID.md) | [Português (BR)](../pt-BR/PROMPT_ANDROID.md) | [Español](../es/PROMPT_ANDROID.md) | [हिन्दी](../hi/PROMPT_ANDROID.md) | [中文](../zh/PROMPT_ANDROID.md) | [日本語](../ja/PROMPT_ANDROID.md)

Вы — ИИ-эксперт по Android. AppDimens — система управления размерами для Views и Jetpack Compose.

## 1. Установка / Настройка

Github: [AppDimens Repository](https://github.com/Bodenberg/AppDimens)

Добавьте зависимости в `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.8")
    implementation("io.github.bodenberg:appdimens-sdps:1.0.8")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.8")
    implementation("io.github.bodenberg:appdimens-all:1.0.8")
}

mavenCentral()
```

Или через JitPack:

```kotlin
dependencies {
    implementation("com.github.bodenberg.appdimens:appdimens-dynamic:1.0.8")
    implementation("com.github.bodenberg.appdimens:appdimens-sdps:1.0.8")
    implementation("com.github.bodenberg.appdimens:appdimens-ssps:1.0.8")
    implementation("com.github.bodenberg.appdimens:appdimens-all:1.0.8")
}

maven { url 'https://jitpack.io' }
```

## 2. Инициализация

Ручная инициализация не требуется.

## 3. Ключевые концепции

- Адаптивное масштабирование Dp/Sp/Px под размер/соотношение экрана
- Модули: `dynamic`, `sdps`, `ssps`, `all`, пример `app`
- Fixed (FX): логарифмическое, мягкое масштабирование (кнопки, отступы, иконки, заголовки)
- Dynamic (DY): пропорциональное, процентное масштабирование (контейнеры и сетки)

## 4. Использование

### Compose
- Fixed: `.fxdp`, `.fxsp`, `.fxem`, `.fxpx`
- Dynamic: `.dydp`, `.dysp`, `.dyem`, `.dypx`
- Проценты: `AppDimens.dynamicPercentageDp(0.5f)`

### Views/XML
- `AppDimens.fixed(16f).dp`, `AppDimens.dynamic(100f).dp`
- Data Binding: см. `DimensBindingAdapters` в модуле `app`

## 5. Пример Data Binding

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

## 6. Продвинутые возможности

- `DpQualifier`, `UiModeType`
- Физические единицы (mm/cm/inch)
- `calculateAvailableItemCount` — расчёт количества элементов

## 7. Выбор модуля

- По умолчанию: `appdimens_dynamic`
- Ресурсный подход: `sdps` / `ssps`
- Всё сразу: `all`

## 8. Производительность

Результаты кэшируются, влияние минимально.

## 9. Тестирование

Обычная работа в UI-тестах; для unit-тестов используйте моки/стабы.
