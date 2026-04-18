# 📚 AppDimens: Guía Técnica Completa

> **Idiomas:** [English](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md) | [Português (BR)](../pt-BR/COMPREHENSIVE_TECHNICAL_GUIDE.md) | Español

**Documentación Técnica Integral**  
*Versión: 2.0.0*

> **Android (`appdimens-dynamic` 3.x):** Los ejemplos de Jetpack Compose usan **`sdp` / `hdp` / `wdp` / `ssp`** y, para la curva híbrida en el eje, **`asdp` / `ahdp` / `awdp` / `assp`**. Los nombres como **`balanced()`** siguen en **iOS, Web y React Native**. Tabla resumida: [PLATFORM_API_MAP.md](../../DOCS/PLATFORM_API_MAP.md) (inglés). Documento canónico: [COMPREHENSIVE_TECHNICAL_GUIDE.md](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md).

---

## Estrategias

1. **BALANCED** ⭐ - Primaria (93/100)
2. **DEFAULT** - Secundaria (82/100)
3. **LOGARITHMIC** - TV (88/100)
4. **POWER** - General (86/100)
5. **PERCENTAGE** - Contenedores grandes (62/100)
6. **FLUID** - Tipografía (78/100)
7. Y 7 más

---

## API por plataforma (resumen)

Equivalente a las secciones **4.3 / 5.3** del guía en inglés.

### BALANCED (concepto) — enlaces típicos

**Android (Jetpack Compose):**
```kotlin
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.auto.asdp

16.sdp
16.ssp
Modifier.padding(16.asdp)
// Views/XML: ver README del submódulo
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

### DEFAULT (concepto) — secundario / móvil

**Android:**
```kotlin
16.sdp
16.ssp
16.asdp
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

## Referencias

- [Teoría matemática](../../DOCS/MATHEMATICAL_THEORY.md)
- [Guía simplificada](../../DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md)
- [Comparación de fórmulas](../../DOCS/FORMULA_COMPARISON.md)
- [Ejemplos](../../DOCS/EXAMPLES.md)

---

**Repositorio:** https://github.com/bodenberg/appdimens
