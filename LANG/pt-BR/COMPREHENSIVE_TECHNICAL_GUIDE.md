# 📚 AppDimens: Guia Técnico Completo

> **Idiomas:** [English](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md) | Português (BR) | [Español](../es/COMPREHENSIVE_TECHNICAL_GUIDE.md)

**Documentação Técnica Abrangente - Tudo Que Você Precisa Saber**  
*Autor: Jean Bodenberg*  
*Data: Fevereiro 2025*  
*Versão: 2.0.0*

> **🆕 Versão 2.0:** Expandido de 2 para **13 estratégias de escalonamento**, incluindo modelos perceptuais (BALANCED⭐, LOGARITHMIC, POWER), Inferência Inteligente, e melhorias de performance de 5x.

> **Android (`appdimens-dynamic` 3.x):** exemplos em Jetpack Compose usam tokens **`sdp` / `hdp` / `wdp` / `ssp`** e, para a curva híbrida no eixo, **`asdp` / `ahdp` / `awdp` / `assp`**. Nomes como **`balanced()`** continuam em **iOS, Web e React Native**. Resumo completo: [PLATFORM_API_MAP.md](../../DOCS/PLATFORM_API_MAP.md) (inglês). O documento canónico em inglês é [COMPREHENSIVE_TECHNICAL_GUIDE.md](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md).

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

### API por plataforma (resumo)

Os blocos abaixo espelham a secção **4.3 / 5.3** do guia em inglês.

#### BALANCED (conceito) — bindings típicos

**Android (Jetpack Compose):**
```kotlin
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.auto.asdp

// appdimens-dynamic 3.x — escalado + auto (curva híbrida no eixo)
16.sdp
16.ssp
Modifier.padding(16.asdp)
// Views/XML: ver README do submódulo (não usar cadeia JVM unificada antiga)
```

**iOS:**
```swift
AppDimens.shared.balanced(16).toPoints()
AppDimens.shared.balanced(16).toPt()
```

**Flutter:**
```dart
AppDimens.fixed(16).calculate(context)
16.0.fx.calculate(context)
AppDimens.dynamic(300).calculate(context)
```

**React Native:**
```typescript
const {balanced} = useAppDimens();
balanced(16)
```

**Web:**
```typescript
const {balanced} = useWebDimens();
balanced(16)
```

#### DEFAULT (conceito) — secundário / telefone

**Android:**
```kotlin
16.sdp   // escalado — layouts focados em telefone
16.ssp
16.asdp  // auto — curva híbrida no eixo
```

**iOS:**
```swift
AppDimens.shared.defaultScaling(16).toPoints()
```

**Flutter:**
```dart
AppDimens.fixed(16).calculate(context)
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

- [Teoria Matemática](../../DOCS/MATHEMATICAL_THEORY.md)
- [Guia Simplificado](../../DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md)
- [Comparação de Fórmulas](../../DOCS/FORMULA_COMPARISON.md)
- [Exemplos](../../DOCS/EXAMPLES.md)
- [Guia técnico completo (EN)](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

---

**Documento criado por:** Jean Bodenberg  
**Última atualização:** Fevereiro 2025  
**Versão:** 2.0.0  
**Repositório:** https://github.com/bodenberg/appdimens
