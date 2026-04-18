# 📐 AppDimens para Flutter

**Dimensões Responsivas Inteligentes para Flutter**  
*Versão: 2.0.0*

> **Idiomas:** [English](../../../appdimens-flutter/README.md) | Português (BR) | [Español](../../es/Flutter/README.md)

---

## 🚀 Instalação

```yaml
dependencies:
  appdimens: ^2.0.0
```

---

## ⚡ Início Rápido

```dart
Container(
  width: AppDimens.fixed(300).calculate(context),
  padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
  child: Text(
    'Olá Mundo',
    style: TextStyle(fontSize: AppDimens.fixed(18).calculate(context)),
  ),
)
```

### Usando Extensions

```dart
Container(
  width: AppDimens.fixed(300).calculate(context),
  child: Text('Olá', style: TextStyle(fontSize: AppDimens.fixed(18).calculate(context))),
)
```

---

## 🎯 Estratégias

- **BALANCED** ⭐ (primária)
- **DEFAULT** (secundária)
- **PERCENTAGE**, **LOGARITHMIC**, **POWER**, **FLUID**
- E mais 7 estratégias

---

**Pub:** https://pub.dev/packages/appdimens
