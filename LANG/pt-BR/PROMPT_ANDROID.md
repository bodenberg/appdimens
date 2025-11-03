# AppDimens Android - Prompt de Desenvolvimento

**Referência Rápida para Assistentes de IA e Desenvolvedores**  
*Versão: 2.0.0*

---

## Princípios Fundamentais

1. **Use BALANCED ⭐ para 95% dos elementos** (primária)
2. **Use DEFAULT para apps de telefone** (secundária)
3. **Use PERCENTAGE para containers** (específico)
4. **13 estratégias disponíveis**
5. **5x de performance** vs v1.x

---

## API Rápida

```kotlin
// PRIMÁRIA: BALANCED ⭐
16.balanced().dp
16.balanced().sp

// SECUNDÁRIA: DEFAULT
16.defaultDp
16.defaultSp

// Containers: PERCENTAGE
300.percentageDp.dp

// Smart API
AppDimens.from(48).smart().forElement(ElementType.BUTTON).dp
```

---

## Seleção de Estratégia

- Multi-dispositivo → BALANCED ⭐
- Apenas telefone → DEFAULT
- Containers → PERCENTAGE
- TV → LOGARITHMIC

---

**Documentação Completa:** [../../../DOCS/README.md](../../../DOCS/README.md)
