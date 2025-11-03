# AppDimens iOS - Prompt de Desenvolvimento

**Referência Rápida para Assistentes de IA e Desenvolvedores**  
*Versão: 2.0.0*

---

## Princípios Fundamentais

1. **Use BALANCED ⭐ para 95% dos elementos** (primária)
2. **Use DEFAULT para apps de iPhone** (secundária)
3. **13 estratégias disponíveis**

---

## API Rápida

```swift
// PRIMÁRIA: BALANCED ⭐
AppDimens.shared.balanced(16).toPoints()

// SECUNDÁRIA: DEFAULT
AppDimens.shared.defaultScaling(24).toPoints()

// Containers
AppDimens.shared.percentage(300).toPoints()

// Smart API
AppDimens.shared.smart(48).forElement(.button).toPoints()
```

---

## Instalação

**CocoaPods:** `pod 'AppDimens', '~> 2.0.0'`

---

**Documentação Completa:** [../../../iOS/README.md](../../../iOS/README.md)
