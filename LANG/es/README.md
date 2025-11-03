<div align="center">
   <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens - Diseño Responsivo en Todos los Dispositivos" height="300"/>
<h1>📐 AppDimens</h1>
<p><strong>Dimensiones Responsivas Inteligentes para Cualquier Pantalla</strong></p>
   
[![Versión](https://img.shields.io/badge/versión-2.0.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/licencia-Apache%202.0-green.svg)](../../LICENSE)
[![Plataforma](https://img.shields.io/badge/plataforma-Android%20%7C%20iOS%20%7C%20Flutter%20%7C%20RN%20%7C%20Web-orange.svg)](https://github.com/bodenberg/appdimens)
[![Estrategias](https://img.shields.io/badge/estrategias-13-orange.svg)]()

[📚 Documentación](../../DOCS/README.md) | [⚡ Referencia Rápida](../../DOCS/DOCS_QUICK_REFERENCE.md) | [🔬 Detalles Técnicos](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

> **Idiomas:** [English](../../README.md) | [Português (BR)](../pt-BR/README.md) | Español
</div>

---

## 🆕 Novedades en la Versión 2.0

**🎯 13 Estrategias de Escalado** (¡eran solo 2!)
- **BALANCED** ⭐ Nueva estrategia primaria recomendada - híbrida lineal-logarítmica
- **DEFAULT** (antigua Fixed) - logarítmica con compensación de AR (recomendación secundaria)
- **PERCENTAGE** (antigua Dynamic) - escalado proporcional
- **LOGARITHMIC** - psicofísica pura de Weber-Fechner
- **POWER** - Ley de Potencia de Stevens (configurable)
- **FLUID** - Similar a CSS clamp con breakpoints
- Más 7: INTERPOLATED, DIAGONAL, PERIMETER, FIT, FILL, AUTOSIZE 🆕, NONE

**🧠 Sistema de Inferencia Inteligente**
- Selección automática de estrategia basada en el tipo de elemento
- 18 tipos de elemento (BUTTON, TEXT, ICON, CONTAINER, etc.)
- 8 categorías de dispositivo (PHONE_SMALL hasta TV)
- Sistema de decisión basado en pesos

**⚡ Mejora de Rendimiento de 5x**
- Caché unificado lock-free (0.001µs)
- Tabla de lookup para ln() (10-20x más rápido)
- Constantes pre-calculadas
- Algoritmos de búsqueda binaria (O(log n))

**♻️ Compatibilidad Total con Versiones Anteriores**
- Las extensiones antiguas `.fxdp`/`.dydp` todavía funcionan
- Camino suave de migración a `.balanced()`, `.defaultDp`, `.percentageDp`

---

## ⚡ Visión General Rápida

**AppDimens** hace que tus elementos de UI escalen perfectamente en todos los dispositivos - desde teléfonos a tablets, TVs, relojes y navegadores web.

### ¿Por qué AppDimens 2.0?

```
❌ Sin AppDimens:
   Teléfono (360dp): Botón = 48dp (13% de la pantalla) ✅ Bien
   Tablet (720dp): Botón = 48dp (7% de la pantalla)  ❌ ¡Muy pequeño!

❌ Con Escalado Lineal (SDP):
   Teléfono (360dp): Botón = 58dp (16% de la pantalla) ✅ OK
   Tablet (720dp): Botón = 115dp (16% de la pantalla) ❌ ¡Muy grande!

✅ Con AppDimens BALANCED ⭐:
   Teléfono (360dp): Botón = 58dp (16% de la pantalla) ✅ ¡Perfecto!
   Tablet (720dp): Botón = 70dp (10% de la pantalla) ✅ ¡Perfecto!
```

---

## 🚀 Instalación

### Android

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:2.0.0")
}
```

### iOS

```ruby
pod 'AppDimens', '~> 2.0.0'
```

### Flutter

```yaml
dependencies:
  appdimens: ^2.0.0
```

### React Native

```bash
npm install appdimens-react-native@2.0.0
```

### Web

```bash
npm install webdimens@2.0.0
```

---

## 💡 Uso Básico

### Android

```kotlin
@Composable
fun MiCard() {
    Card(
        modifier = Modifier
            .width(300.balanced().dp)      // ✨ BALANCED (RECOMENDADO) ⭐
            .padding(16.balanced().dp)
    ) {
        Text("Hola Mundo", fontSize = 18.balanced().sp)
    }
}
```

### iOS

```swift
struct MiCard: View {
    var body: some View {
        VStack {
            Text("Hola Mundo")
                .font(.system(size: AppDimens.shared.balanced(18).toPoints()))
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
    }
}
```

### Flutter

```dart
Container(
  width: AppDimens.balanced(300).calculate(context),
  padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
  child: Text(
    'Hola Mundo',
    style: TextStyle(fontSize: AppDimens.balanced(18).calculate(context)),
  ),
)
```

---

## 🏆 Por qué AppDimens es #1

```
🥇 #1 AppDimens BALANCED: 93/100 ⭐⭐⭐⭐⭐ (Recomendación primaria)
🥈 #2 AppDimens LOGARITHMIC: 88/100 ⭐⭐⭐⭐⭐
🥉 #3 AppDimens POWER: 86/100 ⭐⭐⭐⭐
   #4 AppDimens DEFAULT: 82/100 ⭐⭐⭐⭐ (Recomendación secundaria)
```

---

## 📚 Documentación

- [Guía Simplificada](MATHEMATICAL_THEORY_SIMPLIFIED.md) - 15 minutos
- [Teoría Matemática](MATHEMATICAL_THEORY.md) - Completa
- [Comparación de Fórmulas](FORMULA_COMPARISON.md) - Rankings
- [Ejemplos](../../DOCS/EXAMPLES.md) - Código listo

---

<div align="center">

**Hecho con ❤️ para desarrolladores en todo el mundo**

[Documentación](../../DOCS/README.md) • [Ejemplos](../../DOCS/EXAMPLES.md)

</div>
