# AppDimens Flutter - Example App

**Version: 2.0.0**

---

## 🎯 Features Demonstrated

- ✅ All 13 scaling strategies
- ✅ BALANCED ⭐ primary usage
- ✅ Smart Inference examples
- ✅ Base Orientation support
- ✅ Physical Units
- ✅ Performance optimizations

---

## 🚀 Run Example

```bash
cd example
flutter pub get
flutter run
```

---

## 📱 What's Included

### Main Demo (`lib/main.dart`)
- Strategy comparison screen
- Visual demonstrations
- Interactive examples

### New Strategies (`lib/new_strategies_example.dart`)
- BALANCED, LOGARITHMIC, POWER examples
- Smart API demonstrations

### Fluid Scaling (`lib/fluid_example.dart`)
- FLUID strategy examples
- Typography demonstrations

---

## 🎨 Example Code

```dart
// See lib/main.dart for complete examples
class ExampleApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Center(
          child: Text(
            'Example',
            style: TextStyle(
              fontSize: AppDimens.balanced(16).calculate(context),
            ),
          ),
        ),
      ),
    );
  }
}
```

---

**Main Flutter Guide:** [../README.md](../README.md)
