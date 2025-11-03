# 📚 AppDimens: Guia Técnico Completo

> **Idiomas:** [English](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md) | Português (BR) | [Español](../es/COMPREHENSIVE_TECHNICAL_GUIDE.md)

**Documentação Técnica Abrangente - Tudo Que Você Precisa Saber**  
*Autor: Jean Bodenberg*  
*Data: Fevereiro 2025*  
*Versão: 2.0.0*

> **🆕 Versão 2.0:** Expandido de 2 para **13 estratégias de escalonamento**, incluindo modelos perceptuais (BALANCED⭐, LOGARITHMIC, POWER), Inferência Inteligente, e melhorias de performance de 5x.

---

## Visão Geral

Este é o **documento DEFINITIVO** do AppDimens. Tudo sobre AppDimens 2.0 está aqui.

### Estratégias

1. **BALANCED** ⭐ - Recomendação primária (93/100)
2. **DEFAULT** - Recomendação secundária (82/100)
3. **LOGARITHMIC** - Apps de TV (88/100)
4. **POWER** - Uso geral (86/100)
5. **PERCENTAGE** - Containers grandes (62/100)
6. **FLUID** - Tipografia (78/100)
7. E mais 7 estratégias

### Plataformas

- Android (Jetpack Compose, Views, XML)
- iOS (SwiftUI, UIKit)
- Flutter
- React Native
- Web (React, Vue, Svelte, Angular, Vanilla JS)

---

## Guia Rápido

### Seleção de Estratégia

```
95% dos apps → BALANCED ⭐
Apps de telefone → DEFAULT
Containers → PERCENTAGE
TV → LOGARITHMIC
Tipografia → FLUID
Jogos → FIT/FILL
```

### API por Plataforma

**Android:**
```kotlin
16.balanced().dp    // Primária ⭐
16.defaultDp        // Secundária
300.percentageDp.dp // Containers
```

**iOS:**
```swift
AppDimens.shared.balanced(16).toPoints()
AppDimens.shared.defaultScaling(16).toPoints()
```

**Flutter:**
```dart
AppDimens.balanced(16).calculate(context)
AppDimens.defaultScaling(16).calculate(context)
```

---

## Comparações

### AppDimens vs Bibliotecas Externas

| Aspecto | AppDimens BALANCED ⭐ | SDP/SSP |
|---------|----------------------|---------|
| **Fórmula** | Híbrida linear-log | Linear |
| **Controle de Oversizing** | 40% de redução | 0% |
| **Tablet (720dp)** | 70dp ✅ | 115dp ❌ |
| **Performance** | 0.0012µs | 0.0000µs (pré-calc) |
| **Flexibilidade** | 13 estratégias | 1 estratégia |

**Recomendação:** Migre de SDP para BALANCED para 40% de melhoria

---

## Performance

**Benchmarks v2.0:**
- Cache: 0.001µs (5x mais rápido que v1.x)
- Ln() lookup: 10-20x mais rápido
- Memória: 56 bytes por entrada
- Multi-thread: 100% de paralelismo

---

## Referências

- [Teoria Matemática](MATHEMATICAL_THEORY.md)
- [Guia Simplificado](MATHEMATICAL_THEORY_SIMPLIFIED.md)
- [Comparação de Fórmulas](FORMULA_COMPARISON.md)
- [Exemplos](../../DOCS/EXAMPLES.md)

---

**Documento criado por:** Jean Bodenberg  
**Última atualização:** Fevereiro 2025  
**Versão:** 2.0.0  
**Repositório:** https://github.com/bodenberg/appdimens
