# 📦 AppDimens iOS - Руководство по установке

> Языки: [English](../../../iOS/INSTALLATION.md) | [Português (BR)](../../pt-BR/iOS/INSTALLATION.md) | [Español](../../es/iOS/INSTALLATION.md) | [हिन्दी](../../hi/iOS/INSTALLATION.md) | [中文](../../zh/iOS/INSTALLATION.md) | [日本語](../../ja/iOS/INSTALLATION.md)

Это подробное руководство поможет установить и интегрировать AppDimens iOS в ваш проект с пошаговыми инструкциями.

## 📋 Требования

- iOS 13.0+
- Swift 5.0+
- Xcode 12.0+

## 🚀 Способы установки

### CocoaPods (рекомендуется)

CocoaPods — рекомендуемый способ установки для AppDimens iOS.

#### 1. Добавьте в Podfile

```ruby
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens'
end
```

#### 2. Установите pod

```bash
pod install
```

#### 3. Откройте workspace

```bash
open YourApp.xcworkspace
```

#### 4. Импортируйте в Swift-файлах

```swift
import AppDimens
```

#### Расширенная конфигурация CocoaPods

```ruby
platform :ios, '13.0'
use_frameworks!

target 'YourApp' do
  pod 'AppDimens', '~> 1.0'
end

post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '13.0'
    end
  end
end
```

### Swift Package Manager

#### Способ 1: Интерфейс Xcode

1. В Xcode:
   - File → Add Package Dependencies
   - Введите: `https://github.com/bodenberg/appdimens.git`
   - Выберите версию: `1.0.6` или выше
   - Добавьте в целевой проект (target)

2. Импортируйте в Swift-файлах:
```swift
import AppDimens
```

#### Способ 2: Package.swift

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.6")
]
```

#### Способ 3: Настройки проекта Xcode

1. Выберите проект → Package Dependencies
2. Нажмите "+"
3. Введите URL и добавьте пакет

### Ручная установка

#### 1. Скачайте исходный код

```bash
git clone https://github.com/bodenberg/appdimens.git
```

#### 2. Скопируйте папку Sources

- Скопируйте `Sources/AppDimens/` в ваш проект
- Добавьте все Swift-файлы в проект Xcode

#### 3. Импортируйте в Swift-файлах

```swift
import AppDimens
```

## 🔧 Конфигурация

### Build Settings

- iOS Deployment Target: 13.0+
- Swift Language Version: Swift 5
- Enable Bitcode: No (при использовании CocoaPods)

### Конфигурация проекта

- CocoaPods: Podfile настроит линковку фреймворков и параметры сборки
- SPM: Xcode настроит пакет автоматически
- Manual: добавьте файлы в target и импортируйте

## 🎯 Быстрый старт

```swift
import AppDimens

// Фиксированное масштабирование (для UI-элементов)
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// Динамическое масштабирование (для верстки)
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt
```

### SwiftUI

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack {
            Text("Привет, мир")
                .font(.fxSystem(size: 16))
                .fxPadding(16)
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(8)
        }
    }
}
```

### UIKit

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        let button = UIButton()
        button.frame = CGRect(x: 0, y: 0, width: 200.dypt, height: 48.fxpt)
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        view.addSubview(button)
    }
}
```

## 🛠 Устранение неполадок

- Модуль не найден: выполните `pod install`, очистите сборку
- Неопределенный символ: добавьте `import AppDimens`, проверьте синтаксис `16.fxpt`
- SPM: проверьте интернет/URL, очистите кэш пакетов

## 🔄 Миграция с Android

| Android | iOS |
|---------|-----|
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |

## 📄 Лицензия

Проект лицензирован по Apache License 2.0 — см. файл [LICENSE](LICENSE) для подробностей.

---

**AppDimens iOS** — адаптивный дизайн проще простого! 🚀
