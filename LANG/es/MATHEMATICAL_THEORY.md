# 📐 AppDimens: Teoría Matemática y Fundación Científica

> **Idiomas:** [English](../../DOCS/MATHEMATICAL_THEORY.md) | [Português (BR)](../pt-BR/MATHEMATICAL_THEORY.md) | Español

**Documentación Técnica Detallada - Modelo Matemático Universal**  
*Autor: Jean Bodenberg*  
*Fecha: Febrero 2025*  
*Versión: 2.0.0*

> **🆕 Actualización Versión 2.0:** Esta versión introduce **13 estrategias de escalado** (eran 2), incluyendo modelos perceptuales basados en psicofísica (Weber-Fechner, Stevens), Sistema de Inferencia Inteligente, y optimizaciones matemáticas significativas (mejora de rendimiento de 5x). **BALANCED** es ahora la **estrategia primaria recomendada** para apps multi-dispositivo, mientras que **DEFAULT** (antigua Fixed) sirve como recomendación secundaria.

---

## Recomendaciones v2.0

### Recomendación Primaria (95% de apps)

**✅ BALANCED** ⭐ - Para aplicaciones multi-dispositivo
- Lineal en teléfonos (< 480dp)
- Logarítmica en tablets/TVs (≥ 480dp)
- 40% de reducción de oversizing
- Mejor en su clase (93/100)

### Recomendación Secundaria

**✅ DEFAULT** - Para apps enfocados en teléfonos
- ~97% crecimiento lineal + 3% compensación AR
- Optimizado para rango 320-480dp
- Compatible con v1.x (82/100)

### Casos de Uso Específicos

- **PERCENTAGE** - Containers grandes, imágenes
- **LOGARITHMIC** - Apps de TV, control máximo
- **POWER** - Uso general, configurable
- **FLUID** - Tipografía con límites

---

## Ejemplos por Plataforma

**Android:**
```kotlin
Text("Hola", fontSize = 16.ssp)
```

**iOS:**
```swift
Text("Hola").font(.system(size: AppDimens.shared.balanced(16).toPoints()))
```

**Flutter:**
```dart
Text('Hola', style: TextStyle(fontSize: AppDimens.fixed(16).calculate(context)))
```

---

## Comparación Numérica (48dp @ 720dp)

| Estrategia | Resultado | vs Lineal | Control |
|------------|-----------|-----------|---------|
| **BALANCED** ⭐ | 69.7dp | **-40%** | Excelente |
| **LOGARITHMIC** | 67.2dp | **-42%** | Excelente |
| **DEFAULT** | 79.2dp | **-31%** | Muy Bueno |
| SDP/Lineal | 115.2dp | 0% | Pobre ❌ |

---

**Documento creado por:** Jean Bodenberg  
**Última actualización:** Febrero 2025  
**Versión:** 2.0.0  
**Repositorio:** https://github.com/bodenberg/appdimens

---

*"El logaritmo natural nos enseña que el crecimiento verdaderamente sostenible no es el que acelera sin control, sino el que desacelera sabiamente a medida que se expande."*

— Jean Bodenberg
