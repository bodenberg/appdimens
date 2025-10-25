---
layout: default
title: "AI Prompt for AppDimens Flutter Library"
---

# AI Prompt for AppDimens Flutter Library

> Languages: [Português (BR)](LANG/pt-BR/PROMPT_FLUTTER.md) | [Español](LANG/es/PROMPT_FLUTTER.md) | [हिन्दी](LANG/hi/PROMPT_FLUTTER.md) | [Русский](LANG/ru/PROMPT_FLUTTER.md) | [中文](LANG/zh/PROMPT_FLUTTER.md) | [日本語](LANG/ja/PROMPT_FLUTTER.md)

You are an expert Flutter developer AI. Your task is to understand and effectively use the AppDimens Flutter library, a sophisticated dimension management system for Flutter applications. This guide will provide you with all the necessary information about the library's architecture, functionalities, and use cases.

## 1. Installation / Setup

Github: [AppDimens Repository](https://github.com/Bodenberg/AppDimens)

Add to your `pubspec.yaml`:

```yaml
dependencies:
  appdimens: ^1.0.8
```

```bash
flutter pub get
```

## 2. Minimum Requirements

| Requirement | Minimum Version | Recommended |
|------------|-----------------|-------------|
| **Dart SDK** | 3.0.0 | 3.5.0+ |
| **Flutter SDK** | 3.10.0 | 3.24.0+ |

### Supported Platforms

- ✅ Android (API 21+)
- ✅ iOS (12.0+)
- ✅ Web (All modern browsers)
- ✅ Windows (10+)
- ✅ macOS (10.14+)
- ✅ Linux

## 3. Core Concepts

### 3.1. What is AppDimens Flutter?

AppDimens Flutter is a library that provides a responsive and mathematically refined way to manage dimensions in Flutter applications. It ensures layout consistency across various screen sizes and aspect ratios by treating standard pixel values as a base that is dynamically scaled.

### 3.2. Scaling Models: Fixed (FX) vs. Dynamic (DY)

The library's core functionality revolves around two primary scaling models:

| Feature | Fixed (FX) | Dynamic (DY) |
|---------|-----------|--------------|
| **Philosophy** | **Logarithmic Adjustment**: Subtle, refined scaling | **Proportional Adjustment**: Aggressive scaling |
| **Use Case** | Buttons, paddings, icons, fonts | Containers, grids, fluid layouts |
| **Implementation** | `.fx.calculate(context)` | `.dy.calculate(context)` |

## 4. How to Use AppDimens

### 4.1. Basic Configuration

```dart
import 'package:appdimens/appdimens.dart';

void main() {
  runApp(
    AppDimensApp(
      config: const AppDimensConfig(
        aspectRatioEnabled: true,
        cacheEnabled: true,
        ignoreMultiWindowAdjustment: false,
      ),
      child: MyApp(),
    ),
  );
}
```

### 4.2. Fixed Scaling (FX)

```dart
// Basic usage
Container(
  width: 100.fx.calculate(context),
  height: 100.fx.calculate(context),
)

// With custom values
Container(
  width: AppDimens.fixed(150)
      .deviceType(DeviceType.tablet, 200)
      .calculate(context),
)
```

### 4.3. Dynamic Scaling (DY)

```dart
// Basic usage
Container(
  width: 200.dy.calculate(context),
  height: 100.dy.calculate(context),
)

// With custom values
Container(
  width: AppDimens.dynamic(300)
      .deviceType(DeviceType.tablet, 400)
      .calculate(context),
)
```

### 4.4. Physical Units

```dart
// Convert physical units
Container(
  width: AppDimensPhysicalUnits.mmToPixels(50, context),
  height: AppDimensPhysicalUnits.mmToPixels(25, context),
)
```

### 4.5. Widget Extensions

```dart
// Padding and margin
Text('Hello')
  .fxPadding(16, context)
  .dyMargin(8, context)
  .fxBorderRadius(12, context);

// Text style
Text(
  'Hello World',
  style: TextStyle()
    .fxFontSize(16, context)
    .dyFontSize(18, context),
);
```

## 5. Advanced Features

### 5.1. Conditional Scaling

```dart
final dimension = AppDimens.fixed(100)
    .deviceType(DeviceType.tablet, 150)
    .deviceType(DeviceType.tv, 200)
    .uiMode(UiModeType.carPlay, 180)
    .calculate(context);
```

### 5.2. Cache Control

```dart
// Global cache control
AppDimens.setGlobalCache(true);

// Instance-level cache
final dimension = AppDimens.fixed(100)
    .cache(true)
    .calculate(context);
```

### 5.3. Supported Device Types

- `DeviceType.phone` - Smartphones
- `DeviceType.tablet` - Tablets
- `DeviceType.watch` - Smartwatches
- `DeviceType.tv` - Smart TVs
- `DeviceType.carPlay` - Automotive displays
- `DeviceType.desktop` - Desktop computers
- `DeviceType.foldable` - Foldable devices

### 5.4. Supported UI Modes

- `UiModeType.normal` - Normal mode
- `UiModeType.carPlay` - Automotive mode
- `UiModeType.tv` - TV mode
- `UiModeType.watch` - Watch mode
- `UiModeType.mac` - Mac mode

## 6. Best Practices

1. **Use Fixed for UI Elements**: Buttons, paddings, fonts, icons
2. **Use Dynamic for Layout**: Container widths, spacers, grid items
3. **Enable Caching**: For better performance
4. **Configure Globally**: Set up `AppDimensApp` at the root of your app
5. **Use Extensions**: For cleaner and more readable code

## 7. Performance Considerations

AppDimens Flutter is optimized for high performance:
- Dimension values are calculated once and cached
- Automatic cache invalidation on configuration changes
- Minimal impact on app startup time
- Efficient memory usage

## 8. When to Use Each Model

- **Fixed (FX)**: The recommended default for most UI elements that need consistent sizing
- **Dynamic (DY)**: Use for layout containers and elements that should scale proportionally with screen size
- **Physical Units**: Use when you need precise physical measurements (e.g., for wearables)

## 9. Common Patterns

### Responsive Grid

```dart
GridView.builder(
  gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
    crossAxisCount: 3,
    crossAxisSpacing: 8.fx.calculate(context),
    mainAxisSpacing: 8.fx.calculate(context),
  ),
  itemBuilder: (context, index) {
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(4.fx.calculate(context)),
      ),
    );
  },
)
```

### Responsive Button

```dart
ElevatedButton(
  onPressed: () {},
  style: ElevatedButton.styleFrom(
    padding: EdgeInsets.all(16.fx.calculate(context)),
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(8.fx.calculate(context)),
    ),
  ),
  child: Text(
    'Button',
    style: TextStyle(fontSize: 16.fx.calculate(context)),
  ),
)
```

## 10. Debugging

```dart
// Get current screen info
final screenInfo = AppDimens.getCurrentScreenInfo(context);
print('Screen: ${screenInfo.width}x${screenInfo.height}');
print('Device Type: ${screenInfo.deviceType}');

// Get adjustment factors
final factors = AppDimens.calculateAdjustmentFactors(context);
print('Aspect Ratio Factor: ${factors.aspectRatioFactor}');
```

By following this guide, you should be able to effectively use the AppDimens Flutter library to create responsive and visually consistent applications across all Flutter platforms.

