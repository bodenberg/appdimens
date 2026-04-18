# ⚡ AppDimens Referencia Rápida

> **Idiomas:** [English](../../DOCS/DOCS_QUICK_REFERENCE.md) | [Português (BR)](../pt-BR/DOCS_QUICK_REFERENCE.md) | Español

**Consulta Rápida para AppDimens 2.0**  
*Versión: 2.0.0*

---

## 🎯 Decisión Rápida

```
¿Tipo de app?
├─ Multi-dispositivo → BALANCED ⭐
├─ Solo teléfono → DEFAULT
├─ Containers grandes → PERCENTAGE
├─ App TV → LOGARITHMIC
└─ Tipografía → FLUID
```

---

## 📊 13 Estrategias

| # | Estrategia | Puntuación | Uso |
|---|------------|------------|-----|
| 1 | **BALANCED** ⭐ | 93/100 | Multi-dispositivo |
| 4 | **DEFAULT** | 82/100 | Teléfonos |
| 12 | PERCENTAGE | 62/100 | Containers |

---

## 💻 API

**Android:** `16.sdp`  
**iOS:** `AppDimens.shared.balanced(16).toPoints()`  
**Flutter:** `AppDimens.fixed(16).calculate(context)`

---

**[⬆ Volver arriba](#-appdimens-referencia-rápida)**
