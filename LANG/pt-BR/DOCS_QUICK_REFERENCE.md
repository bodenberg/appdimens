# ⚡ AppDimens Referência Rápida

> **Idiomas:** [English](../../DOCS/DOCS_QUICK_REFERENCE.md) | Português (BR) | [Español](../es/DOCS_QUICK_REFERENCE.md)

**Consulta Rápida para AppDimens 2.0**  
*Versão: 2.0.0*

---

## 🎯 Decisão Rápida (30 Segundos)

```
Qual o tipo do seu app?
├─ Multi-dispositivo (telefones + tablets + TVs) → BALANCED ⭐
├─ Apenas telefone → DEFAULT
├─ Containers grandes/imagens → PERCENTAGE  
├─ App de TV → LOGARITHMIC
├─ Tipografia → FLUID
└─ Jogos → FIT ou FILL
```

---

## 📊 13 Estratégias Resumidas

| # | Estratégia | Caso de Uso | Pontuação |
|---|------------|-------------|-----------|
| 1 | **BALANCED** ⭐ | Multi-dispositivo (PRIMÁRIA) | 93/100 |
| 2 | LOGARITHMIC | TV, controle máximo | 88/100 |
| 3 | POWER | Geral, configurável | 86/100 |
| 4 | **DEFAULT** | Focado em telefones (SECUNDÁRIA) | 82/100 |
| 5 | FLUID | Tipografia | 78/100 |
| 6 | AUTOSIZE 🆕 | Texto dinâmico | 78/100 |
| 12 | PERCENTAGE | Apenas containers | 62/100 |

---

## 💻 API Rápida

### Android

```kotlin
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.auto.asdp
import com.appdimens.dynamic.compose.auto.assp

// Escalado (README do submódulo)
16.sdp
100.wdp
48.hdp
16.ssp

// Auto (curva híbrida “BALANCED” no eixo)
Modifier.padding(16.asdp)
Text("", fontSize = 16.assp)
```

### iOS

```swift
AppDimens.shared.balanced(16).toPoints()
AppDimens.shared.defaultScaling(16).toPoints()
```

### Flutter

```dart
AppDimens.fixed(16).calculate(context)
16.0.fx.calculate(context)
AppDimens.dynamic(300).calculate(context)
```

---

## 📐 Resultados (48dp base)

| Tela | BALANCED | DEFAULT | PERCENTAGE |
|------|----------|---------|------------|
| 360dp | 58dp | 54dp | 58dp |
| 720dp | 70dp ⭐ | 79dp | 115dp ❌ |
| 1080dp | 101dp | 94dp | 173dp ❌ |

---

**[⬆ Voltar ao Topo](#-appdimens-referência-rápida)**
