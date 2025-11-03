# 📐 AppDimens for Flutter

**Smart Responsive Dimensions for Flutter**  
*Version: 2.0.0 | Last Updated: February 2025*

> **Languages:** English | [Português (BR)](../LANG/pt-BR/Flutter/README.md) | [Español](../LANG/es/Flutter/README.md)

[![pub package](https://img.shields.io/pub/v/appdimens.svg)](https://pub.dev/packages/appdimens)
[![Platform](https://img.shields.io/badge/platform-Android%20%7C%20iOS%20%7C%20Web%20%7C%20Desktop-blue.svg)](https://pub.dev/packages/appdimens)

---

## 🆕 What's New in Version 2.0

- 🎯 **13 Scaling Strategies** (up from 2)
- ⭐ **BALANCED** - Primary recommendation (hybrid linear-logarithmic)
- 🔬 **Perceptual Models** (Weber-Fechner, Stevens' Power Law)
- 🧠 **Smart Inference** - Automatic strategy selection
- ⚡ **5x Performance** - Optimized Dart implementation
- ♻️ **Full Backward Compatibility** - v1.x code still works

---

## 🚀 Installation

```yaml
dependencies:
  appdimens: ^2.0.0
```

Then run:
```bash
flutter pub get
```

---

## ⚡ Quick Start

```dart
import 'package:appdimens/appdimens.dart';

class MyWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      width: AppDimens.balanced(300).calculate(context),  // ⭐ BALANCED
      padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
      child: Text(
        'Hello World',
        style: TextStyle(
          fontSize: AppDimens.balanced(18).calculate(context),
        ),
      ),
    );
  }
}
```

### Using Extensions

```dart
Container(
  width: 300.0.balanced(),
  padding: EdgeInsets.all(16.0.balanced()),
  child: Text(
    'Hello World',
    style: TextStyle(fontSize: 18.0.balanced()),
  ),
)
```

---

## 🎯 13 Scaling Strategies

### Primary: BALANCED ⭐

```dart
AppDimens.balanced(16).calculate(context)
16.0.balanced()  // Extension
```

**Use for:** 95% of apps (phones, tablets, web, desktop)

### Secondary: DEFAULT

```dart
AppDimens.defaultScaling(24).calculate(context)
24.0.defaultScaling()
```

**Use for:** Phone-focused apps, icons

### Others

```dart
// PERCENTAGE (Containers)
AppDimens.percentage(300).calculate(context)

// LOGARITHMIC (Large screens)
AppDimens.logarithmic(20).calculate(context)

// POWER (Configurable)
AppDimens.power(16, exponent: 0.75).calculate(context)

// FLUID (Typography)
AppDimens.fluid(16, maxValue: 24).calculate(context)

// Smart API
AppDimens.smart(48).forElement(ElementType.button).calculate(context)

// FIT/FILL (Games)
AppDimens.fit(48).calculate(context)
AppDimens.fill(48).calculate(context)

// DIAGONAL, PERIMETER, INTERPOLATED, AUTOSIZE, NONE
// All available!
```

---

## 📱 Platform Support

- ✅ Android
- ✅ iOS
- ✅ Web
- ✅ Windows
- ✅ macOS
- ✅ Linux

---

## 📚 Documentation

- [Implementation Summary](IMPLEMENTATION_SUMMARY.md) - Technical details
- [Example App](example/README.md) - Sample application
- [Main Documentation](../DOCS/README.md) - Central docs
- [Mathematical Theory](../DOCS/MATHEMATICAL_THEORY.md) - Complete theory
- [Examples](../DOCS/EXAMPLES.md) - Code samples

---

## 🎯 Examples

### Complete Screen

```dart
class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Home',
          style: TextStyle(
            fontSize: AppDimens.balanced(20).calculate(context),
          ),
        ),
        toolbarHeight: AppDimens.balanced(56).calculate(context),
      ),
      body: Padding(
        padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
        child: Column(
          children: [
            ElevatedButton(
              onPressed: () {},
              style: ElevatedButton.styleFrom(
                minimumSize: Size(
                  double.infinity,
                  AppDimens.balanced(48).calculate(context),
                ),
              ),
              child: Text(
                'Click Me',
                style: TextStyle(
                  fontSize: AppDimens.balanced(16).calculate(context),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
```

---

**Author:** Jean Bodenberg  
**License:** Apache 2.0  
**Repository:** https://github.com/bodenberg/appdimens  
**Pub:** https://pub.dev/packages/appdimens
