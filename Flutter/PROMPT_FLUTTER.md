# AppDimens Flutter - Development Prompt

**Quick Reference for AI Assistants and Developers**  
*Version: 2.0.0*

---

## Core Principles

1. **Use BALANCED ⭐ for 95% of UI** (primary)
2. **Use DEFAULT for phone-only** (secondary)
3. **Use PERCENTAGE for containers** (specific)
4. **13 strategies available**
5. **5x performance** vs v1.x

---

## API Quick Reference

```dart
// PRIMARY: BALANCED ⭐
AppDimens.balanced(16).calculate(context)
16.0.balanced()  // Extension

// SECONDARY: DEFAULT
AppDimens.defaultScaling(24).calculate(context)
24.0.defaultScaling()

// Containers: PERCENTAGE
AppDimens.percentage(300).calculate(context)
300.0.percentage()

// Others
AppDimens.logarithmic(20).calculate(context)
AppDimens.power(16, exponent: 0.75).calculate(context)
AppDimens.fluid(16, maxValue: 24).calculate(context)

// Smart API
AppDimens.smart(48).forElement(ElementType.button).calculate(context)
```

---

## Installation

```yaml
dependencies:
  appdimens: ^2.0.0
```

---

## Strategy Selection

- Multi-device (phone + tablet + web) → BALANCED ⭐
- Phone-only → DEFAULT
- Large screens → LOGARITHMIC
- Typography → FLUID
- Containers → PERCENTAGE
- Games → FIT/FILL

---

**Full Documentation:** [../DOCS/README.md](../DOCS/README.md)
