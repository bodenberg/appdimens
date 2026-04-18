# 📐 AppDimens: Teoria Matemática Simplificada

> **Idiomas:** [English](../../DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md) | Português (BR) | [Español](../es/MATHEMATICAL_THEORY_SIMPLIFIED.md)

**Guia Rápido & Fácil - Entenda em 15 Minutos**  
*Autor: Jean Bodenberg*  
*Data: Fevereiro 2025*  
*Versão: 2.0.0*

> **Nota de API:** exemplos **Android / Jetpack Compose** referem-se ao **`appdimens-dynamic` 3.x** (`sdp`, `hdp`, `wdp`, `ssp`, `asdp` / `ahdp` / `awdp`, …). Nomes como **`balanced()`** podem aparecer em **iOS / Web / React Native** ou na **teoria** multiplataforma. Veja o [mapa de APIs](../../DOCS/PLATFORM_API_MAP.md) (inglês).

> **🆕 Versão 2.0:** Este guia agora cobre **13 estratégias de escalonamento** (eram 2), com **BALANCED** como **recomendação primária** para a maioria dos apps, e **DEFAULT** como escolha secundária para apps focados em telefones.

---

## 1. O Problema Que Estamos Resolvendo

### O Problema de Dimensionamento Tradicional

Imagine que você projeta um botão como **48dp** em um telefone:

```
📱 Telefone (360dp):  Botão 48dp = 13.3% da tela  ✅ Bom!
📱 Tablet (720dp):    Botão 48dp = 6.7% da tela   ❌ Muito pequeno!
📺 TV (1080dp):       Botão 48dp = 4.4% da tela   ❌ Minúsculo!
```

**Abordagem tradicional:** Botão permanece 48dp em todo lugar → **parece minúsculo em telas grandes**

### O Problema do Escalonamento Linear

Solução simples: escalar proporcionalmente (como SDP/SSP):

```
📱 Telefone (360dp):  48dp × (360/360) = 48dp   ✅ Bom
📱 Tablet (720dp):    48dp × (720/360) = 96dp   ❌ Muito grande!
📺 TV (1080dp):       48dp × (1080/360) = 144dp ❌ Enorme!
```

**Escalonamento linear:** Cresce muito agressivamente → **elementos ficam grandes demais**

### A Solução AppDimens

AppDimens oferece **13 estratégias inteligentes** que escalonam de forma inteligente baseadas em pesquisa psicofísica:

```
📱 Telefone (360dp):  BALANCED → 57.6dp  ✅ Perfeito
📱 Tablet (720dp):    BALANCED → 69.7dp  ✅ Perfeito! (não 96dp)
📺 TV (1080dp):       BALANCED → 100.9dp ✅ Perfeito! (não 144dp)
```

**Resultado:** Elementos crescem o suficiente para serem visíveis, mas não excessivamente grandes!

---

## 2. Versão 2.0: O Que Há de Novo

### Evolução da v1.x

**AppDimens v1.x:**
- ✅ 2 estratégias: Fixed, Dynamic

**AppDimens v2.0:** ⭐ Atualização Importante
- ✅ **13 estratégias** (eram 2)
- ✅ **BALANCED** - Nova recomendação primária
- ✅ **Modelos perceptuais** (Weber-Fechner, Stevens)
- ✅ **Inferência Inteligente** (seleção automática)
- ✅ **5x de performance**
- ✅ **Compatibilidade total** com v1.x

---

## 3. PRIMÁRIA: Estratégia BALANCED (Recomendada)

> **🆕 Recomendação Primária v2.0:** Use **BALANCED** para 95% das aplicações, especialmente aquelas que visam múltiplos formatos de dispositivo.

### Como Funciona

**BALANCED** combina dois comportamentos:

1. **Linear em telefones** (< 480dp): Escalonamento familiar e proporcional
2. **Logarítmica em tablets/TVs** (≥ 480dp): Controla oversizing

### A Matemática (Versão Simples)

**Para telefones (< 480dp):**
```
Resultado = Base × (LarguraTela / 300)
Exemplo: 48dp em telefone 360dp = 48 × (360/300) = 57.6dp
```

**Para tablets/TVs (≥ 480dp):**
```
Resultado = Base × [1.6 + 0.40 × ln(1 + (Largura-480)/300)]
Exemplo: 48dp em tablet 720dp ≈ 69.7dp
```

### Comparação Visual

| Tela  | LINEAR (ruim) | BALANCED ⭐ | Diferença |
|-------|---------------|------------|-----------|
| 300dp | 48dp          | 48dp       | Referência |
| 360dp | 57.6dp        | 57.6dp     | Igual      |
| 720dp | 115.2dp       | 69.7dp     | **-45dp** ⭐|
| 1080dp| 172.8dp       | 100.9dp    | **-72dp**  |

**Benefícios:**
- ✅ **Telefones:** Comportamento linear familiar
- ✅ **Tablets:** Redução de 11-45dp (previne oversizing)
- ✅ **TVs:** Redução de 72dp (botões permanecem em tamanho tocável)

### Quando Usar BALANCED

**✅ Perfeito Para:**
- Apps multi-dispositivo (telefones + tablets + TVs)
- Apps de redes sociais
- Apps de produtividade
- Apps de notícias e conteúdo
- Apps de e-commerce
- Maioria das aplicações de propósito geral

---

## 4. SECUNDÁRIA: Estratégia DEFAULT (Focada em Telefones)

> **Recomendação Secundária:** Use **DEFAULT** para apps focados em telefones onde você quer controle ligeiramente maior que o linear.

### Como Funciona

**DEFAULT** (antiga "Fixed" na v1.x) fornece:
- **~97% de componente linear**
- **~3% de ajuste logarítmico** baseado em aspect ratio
- Compensação automática para telas alongadas (20:9, 21:9)

### Quando Usar DEFAULT

**✅ Perfeito Para:**
- Aplicações apenas para telefones (320-480dp)
- Apps com telas alongadas (20:9, 21:9)
- Ícones e elementos pequenos de UI
- Compatibilidade com AppDimens v1.x

---

## 5. Outras Estratégias Úteis

### PERCENTAGE (Proporcional)
**Uso:** Containers muito grandes, imagens proporcionais

### LOGARITHMIC (Controle Máximo)
**Uso:** Apps de TV, tablets muito grandes

### POWER (Configurável)
**Uso:** Apps de propósito geral, científicos

### FLUID (CSS Clamp-Like)
**Uso:** Tipografia com limites de tamanho

### FIT & FILL (Jogos)
**Uso:** Elementos de UI de jogos

### AUTOSIZE 🆕 (Container-Aware)
**Uso:** Texto dinâmico que deve caber

### NONE (Sem Escalonamento)
**Uso:** Divisores (sempre 1dp), tamanhos fixos

---

## 6. Guia de Decisão Rápida

```
┌─ Que tipo de app? ──────────────────────────────────┐
│                                                      │
├─ Multi-dispositivo (telefones + tablets + TVs)?     │
│  └─ SIM → BALANCED ⭐ (Recomendação Primária)        │
│                                                      │
├─ App apenas para telefone?                          │
│  └─ SIM → DEFAULT (Recomendação Secundária)         │
│                                                      │
├─ Containers grandes, imagens, grids?                │
│  └─ SIM → PERCENTAGE                                │
│                                                      │
├─ Foco em TV/tablet grande?                          │
│  └─ SIM → LOGARITHMIC                               │
│                                                      │
├─ Tipografia com limites?                            │
│  └─ SIM → FLUID                                     │
│                                                      │
├─ Desenvolvimento de jogos?                          │
│  └─ SIM → FIT ou FILL                               │
│                                                      │
└─ Não tem certeza? → Use BALANCED ⭐ (funciona para 95%)│
```

---

## 7. Exemplos por Plataforma

### Android

```kotlin
Text("Olá", fontSize = 16.ssp)
Button(modifier = Modifier.height(48.sdp))
```

### iOS

```swift
Text("Olá").font(.system(size: AppDimens.shared.balanced(16).toPoints()))
```

### Flutter

```dart
Text('Olá', style: TextStyle(fontSize: AppDimens.fixed(16).calculate(context)))
```

---

## 8. Performance & Otimização

**Melhoria de 5x na v2.0:**
- Cache unificado: 0.001µs (5x mais rápido)
- Lookup de ln(): 10-20x mais rápido
- Memória por entrada: 56B (5x menor)
- Multi-thread: 100% de paralelismo (era 25%)

---

## 9. Migração da v1.x

**Código antigo (v1.x) - Ainda funciona:**
```kotlin
Text("Olá", fontSize = 16.ssp)  // Deprecated mas funcional
```

**Código novo (v2.0) - Recomendado:**
```kotlin
// Recomendação primária
Text("Olá", fontSize = 16.ssp)  // ⭐ BALANCED

// Secundária (telefone primeiro — tokens escalados)
Text("Olá", fontSize = 16.ssp)
```

---

## Resumo & Recomendações

### Seleção de Estratégia

**Para 95% dos apps (Android Compose):**
```kotlin
// Curva híbrida no eixo ≈ BALANCED
16.asdp / 16.assp
```

**Para layouts focados em telefone (Android Compose):**
```kotlin
// Tokens escalados ≈ DEFAULT
16.sdp / 16.ssp
```

**Para containers grandes (Android Compose):**
```kotlin
// Largura ≈ PERCENTAGE
300.wdp
```

---

**Documento criado por:** Jean Bodenberg  
**Última atualização:** Fevereiro 2025  
**Versão:** 2.0.0  
**Licença:** Apache 2.0  
**Repositório:** https://github.com/bodenberg/appdimens

---

*"Simplicidade é a sofisticação máxima. Matemática complexa, API simples."*  
— Filosofia do AppDimens v2.0
