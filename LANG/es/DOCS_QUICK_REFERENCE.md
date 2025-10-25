# ⚡ AppDimens: Referencia Rápida de Documentación

**Guía de Navegación Rápida - Encuentra Lo Que Necesitas en Segundos**

> **Idiomas:** [English](../../DOCS/DOCS_QUICK_REFERENCE.md) | [Português (BR)](../pt-BR/DOCS_QUICK_REFERENCE.md) | Español

---

## 🎯 Elige Tu Camino

### Quiero...

**... aprender AppDimens desde cero** 🌱
→ Lee: [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) (15min)

**... ver ejemplos de código** 💻
→ Lee: [EXAMPLES.md](../../EXAMPLES.md) (20min)

**... entender la teoría matemática completa** 🧮
→ Lee: [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md) (45min)

**... comparar AppDimens con otras soluciones** ⚖️
→ Lee: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) (30min)

**... ver TODO en un documento** 📚
→ Lee: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md) (2h)

**... validar si la implementación es correcta** ✅
→ Lee: [VALIDATION_REPORT.md](../../VALIDATION_REPORT.md) (20min)

**... navegar por toda la documentación** 🗺️
→ Lee: [DOCS/README.md](../../DOCS/README.md) (5min para explorar)

---

## 📊 Documentos por Objetivo

### Para Decidir si Usas AppDimens

1. [README.md](../../README.md) - Visión general
2. [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) - Teoría simple
3. [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) - Ver ranking #1 con 91/100

**Decisión en: 50 minutos**

---

### Para Implementar en Tu Proyecto

1. [EXAMPLES.md](../../EXAMPLES.md) - Código listo para usar
2. [Android/README.md](../../Android/README.md) - Específico de plataforma
3. [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) - FAQ

**Listo para codificar en: 30 minutos**

---

### Para Investigación/Artículo Académico

1. [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md) - Teoría formal
2. [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) - Comparaciones científicas
3. [VALIDATION_REPORT.md](../../VALIDATION_REPORT.md) - Validación
4. [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md) - Todo

**Material completo para publicación**

---

## 🏆 Principales Logros Documentados

### Rankings y Certificaciones

- **#1 de 7 fórmulas** con **91/100 puntos** 🥇
- **3.5× más preciso** que lineal
- **65% menos sobretamaño** que competidores
- **Único con compensación AR**
- **Único con fundamento psicofísico**

📍 Ver en: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md#63-certificate-of-excellence)

---

### Innovaciones Técnicas

1. ✅ Primera biblioteca con escalado logarítmico
2. ✅ Primera con compensación automática de relación de aspecto
3. ✅ Primera con sistema jerárquico de 4 prioridades
4. ✅ Primera con fundamento en Ley de Weber-Fechner
5. ✅ Rendimiento superior con caché (0.002µs vs 0.005µs)

📍 Ver en: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md#12-innovation-and-originality)

---

## 📐 Fórmulas Principales

### Fórmula Logarítmica (Fixed)

```
f_FX(B, S, AR) = B × [1 + ((S - 300) / 30) × (0.10 + 0.08 × ln(AR / 1.78))]
```

### Sistema de Prioridades

```
1. Intersección (UiMode + DpQualifier) ← Más específico
2. UiMode (solo tipo de dispositivo)
3. DpQualifier (solo tamaño)
4. Logarítmica (escalado automático) ← Respaldo
```

📍 Detalles en: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md#4-priority-system-hierarchy-system)

---

## 🔬 Comparaciones Importantes

### vs. SDP/SSP (Lineal)

| Aspecto | SDP/SSP | AppDimens |
|---------|---------|-----------|
| Fórmula | Lineal | Logarítmica |
| AR | ❌ Ignora | ✅ Compensa |
| Tableta 800dp | 107dp (🔴 +123%) | 68dp (✅ +42%) |

### vs. CSS clamp()

| Aspecto | CSS clamp() | AppDimens |
|---------|-------------|-----------|
| Escalado | Lineal | Logarítmico |
| AR | ❌ Ignora | ✅ Compensa |
| Plataforma | Solo Web | Universal |

### vs. Flutter ScreenUtil

| Aspecto | ScreenUtil | AppDimens |
|---------|-----------|-----------|
| Fórmula | Cuadrática | Logarítmica |
| Base teórica | ❌ Ninguna | ✅ Weber-Fechner |
| Tableta 800dp | 89dp (🟡 +86%) | 68dp (✅ +42%) |

📍 Comparación completa: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md#7-comparison-with-7-fundamental-formulas)

---

## 🎓 Fundamento Científico

### Ley de Weber-Fechner (1860)

```
S = k × ln(I / I₀)
```

**Aplicación:** La percepción humana del tamaño es logarítmica, no lineal.

### Ley de Potencia de Stevens (1957)

```
ψ = k × φⁿ  (donde n < 1 para percepción espacial)
```

**Aplicación:** El crecimiento sublineal es más natural para UI.

📍 Detalles en: [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md#6-scientific-foundation)

---

## 💡 FAQ Rápido

**P: ¿Por qué logaritmo?**  
R: Porque la percepción humana es logarítmica (Ley de Weber-Fechner). Previene sobretamaño natural.

**P: ¿Es lento?**  
R: Con caché, es el MÁS RÁPIDO (0.002µs vs 0.005µs para porcentaje).

**P: ¿Funciona en iOS/Flutter/Web?**  
R: ¡Sí! Universal, misma fórmula en todas las plataformas.

**P: ¿Es difícil de usar?**  
R: ¡No! API simple: `16.fxdp` o `16.fixedDp().dp`

📍 FAQ completo: [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md#-frequently-asked-questions-faq)

---

## 📱 Ejemplos Rápidos

### Android Compose

```kotlin
Text(
    text = "Hola",
    fontSize = 16.fxsp,
    modifier = Modifier.padding(12.fxdp)
)
```

### iOS SwiftUI

```swift
Text("Hola")
    .font(.system(size: AppDimens.fixed(16).sp))
    .padding(AppDimens.fixed(12).dp)
```

### Flutter

```dart
Text(
  'Hola',
  style: TextStyle(fontSize: 16.fxsp.calculate(context)),
)
```

### React Native

```javascript
<Text style={{ fontSize: fixedSp(16) }}>
  Hola
</Text>
```

📍 Más ejemplos: [EXAMPLES.md](../../EXAMPLES.md)

---

## 🗺️ Mapa de Documentación

```
AppDimens/
│
├─ README.md ← COMIENZA AQUÍ
│
├─ Documentación Simplificada (Principiante)
│  ├─ MATHEMATICAL_THEORY_SIMPLIFIED.md ⭐
│  ├─ EXAMPLES.md
│  └─ READMEs de Plataforma
│
├─ Documentación Técnica (Intermedio)
│  ├─ MATHEMATICAL_THEORY.md
│  ├─ FORMULA_COMPARISON.md ⭐
│  └─ VALIDATION_REPORT.md
│
├─ Documentación Avanzada (Expertos)
│  └─ COMPREHENSIVE_TECHNICAL_GUIDE.md ⭐⭐⭐
│
└─ Navegación
   ├─ DOCS/README.md (índice completo)
   └─ DOCS_QUICK_REFERENCE.md (¡estás aquí!)
```

---

## ⚡ Enlaces Directos

### Más Accedidos

- [Guía Simplificada](MATHEMATICAL_THEORY_SIMPLIFIED.md) ← 80% de usuarios comienzan aquí
- [Ejemplos de Código](../../EXAMPLES.md)
- [Comparación con Otras Bibliotecas](FORMULA_COMPARISON.md)

### Documentación Completa

- [Teoría Matemática Formal](MATHEMATICAL_THEORY.md)
- [Guía Técnica Completa](COMPREHENSIVE_TECHNICAL_GUIDE.md) ← Todo en un solo lugar
- [Índice Completo de Documentación](../../DOCS/README.md)

### Validación y Certificaciones

- [Reporte de Validación](../../VALIDATION_REPORT.md)
- [Certificado de Excelencia](FORMULA_COMPARISON.md#63-certificate-of-excellence)

---

## 📞 ¿Necesitas Ayuda?

1. **Documentación** ← ¡Estás aquí!
2. [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
3. [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)
4. [Sitio Web Oficial](https://appdimens-project.web.app/)

---

**Última actualización:** Enero 2025  
**Versión:** 1.0.8  
**Autor:** Jean Bodenberg  
**Licencia:** Apache 2.0

---

*"La información correcta, en el momento correcto, de la manera correcta."*

