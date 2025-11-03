# 📐 AppDimens: Teoría Matemática Simplificada

> **Idiomas:** [English](../../DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md) | [Português (BR)](../pt-BR/MATHEMATICAL_THEORY_SIMPLIFIED.md) | Español

**Guía Rápida y Fácil - Entiende en 15 Minutos**  
*Autor: Jean Bodenberg*  
*Fecha: Febrero 2025*  
*Versión: 2.0.0*

> **🆕 Versión 2.0:** Esta guía ahora cubre **13 estrategias de escalado** (eran 2), con **BALANCED** como **recomendación primaria** para la mayoría de las apps, y **DEFAULT** como elección secundaria para apps enfocados en teléfonos.

---

## 1. El Problema Que Estamos Resolviendo

```
📱 Teléfono (360dp):  Botón 48dp = 13.3% de pantalla  ✅ ¡Bien!
📱 Tablet (720dp):    Botón 48dp = 6.7% de pantalla   ❌ ¡Muy pequeño!
📺 TV (1080dp):       Botón 48dp = 4.4% de pantalla   ❌ ¡Minúsculo!
```

### La Solución AppDimens

```
📱 Teléfono (360dp):  BALANCED → 57.6dp  ✅ Perfecto
📱 Tablet (720dp):    BALANCED → 69.7dp  ✅ ¡Perfecto! (no 96dp)
📺 TV (1080dp):       BALANCED → 100.9dp ✅ ¡Perfecto! (no 144dp)
```

---

## 2. PRIMARIA: Estrategia BALANCED (Recomendada)

### Cómo Funciona

**BALANCED** combina dos comportamientos:

1. **Lineal en teléfonos** (< 480dp): Escalado familiar
2. **Logarítmica en tablets/TVs** (≥ 480dp): Controla oversizing

### Comparación Visual

| Pantalla | LINEAL (malo) | BALANCED ⭐ | Diferencia |
|----------|---------------|-------------|------------|
| 360dp    | 57.6dp        | 57.6dp      | Igual      |
| 720dp    | 115.2dp       | 69.7dp      | **-45dp** ⭐|
| 1080dp   | 172.8dp       | 100.9dp     | **-72dp**  |

**Beneficios:**
- ✅ **Teléfonos:** Comportamiento lineal familiar
- ✅ **Tablets:** Reducción de 11-45dp (previene oversizing)
- ✅ **TVs:** Reducción de 72dp

---

## 3. SECUNDARIA: Estrategia DEFAULT

**Uso:** Apps enfocados en teléfonos, compatibilidad con v1.x

```kotlin
Icon(modifier = Modifier.size(24.defaultDp))
```

---

## 4. Guía de Decisión Rápida

```
¿Qué tipo de app?
├─ Multi-dispositivo → BALANCED ⭐
├─ Solo teléfono → DEFAULT
├─ Containers grandes → PERCENTAGE
├─ App de TV → LOGARITHMIC
├─ Tipografía → FLUID
└─ Juegos → FIT o FILL
```

---

## 5. Ejemplos

**Android:**
```kotlin
Text("Hola", fontSize = 16.balanced().sp)
```

**iOS:**
```swift
Text("Hola").font(.system(size: AppDimens.shared.balanced(16).toPoints()))
```

**Flutter:**
```dart
Text('Hola', style: TextStyle(fontSize: AppDimens.balanced(16).calculate(context)))
```

---

**Documento creado por:** Jean Bodenberg  
**Versión:** 2.0.0  
**Repositorio:** https://github.com/bodenberg/appdimens
