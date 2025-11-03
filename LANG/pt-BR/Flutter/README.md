# 📐 AppDimens para Flutter

**Dimensões Responsivas Inteligentes para Flutter**  
*Versão: 2.0.0*

> **Idiomas:** [English](../../../Flutter/README.md) | Português (BR) | [Español](../../es/Flutter/README.md)

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
  width: AppDimens.balanced(300).calculate(context),
  padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
  child: Text(
    'Olá Mundo',
    style: TextStyle(fontSize: AppDimens.balanced(18).calculate(context)),
  ),
)
```

### Usando Extensions

```dart
Container(
  width: 300.0.balanced(),
  child: Text('Olá', style: TextStyle(fontSize: 18.0.balanced())),
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
