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
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.auto.asdp
import com.appdimens.dynamic.compose.auto.assp

// PRIMÁRIA (curva híbrida no eixo) ≈ conceito BALANCED
Modifier.padding(16.asdp)
Text("", fontSize = 16.assp)

// SECUNDÁRIA (telefone primeiro) — tokens escalados
16.sdp
16.ssp

// Containers largos — eixo de largura
300.wdp
```

---

## Seleção de Estratégia

- Multi-dispositivo → BALANCED ⭐
- Apenas telefone → DEFAULT
- Containers → PERCENTAGE
- TV → LOGARITHMIC

---

**Documentação Completa:** [../../DOCS/README.md](../../DOCS/README.md)
