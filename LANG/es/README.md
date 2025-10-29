<div align="center"><img src="../../IMAGES/image_sample_devices.png" alt="AppDimens - Diseño Responsivo en Todos los Dispositivos" height="300"/>
<h1>📐 AppDimens</h1>
<p><strong>Dimensiones Responsivas Inteligentes para Cualquier Pantalla</strong></p>

[![Versión](https://img.shields.io/badge/version-1.0.9-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%20%7C%20iOS%20%7C%20Flutter%20%7C%20RN%20%7C%20Web-orange.svg)](https://github.com/bodenberg/appdimens)

[📚 Documentación](../../DOCS/README.md) | [⚡ Referencia Rápida](DOCS_QUICK_REFERENCE.md) | [🔬 Detalles Técnicos](COMPREHENSIVE_TECHNICAL_GUIDE.md)

</div>

> **Idiomas:** [English](../../README.md) | [Português (BR)](../pt-BR/README.md) | Español

---

## ⚡ Descripción General

**AppDimens** hace que tus elementos de UI se escalen perfectamente en todos los dispositivos - desde teléfonos hasta tabletas, TVs, relojes y navegadores web.

En lugar de tamaños fijos que se ven diminutos en tabletas o enormes en relojes, AppDimens usa **escalado matemático** que se adapta inteligentemente al tamaño de pantalla y la relación de aspecto.

### ¿Por qué AppDimens?

```
❌ Sin AppDimens:
   Teléfono (360dp): Botón = 48dp (13% de pantalla) ✅ Bien
   Tableta (800dp): Botón = 48dp (6% de pantalla)  ❌ ¡Muy pequeño!

✅ Con AppDimens:
   Teléfono (360dp): Botón = 48dp (13% de pantalla) ✅ Bien
   Tableta (800dp): Botón = 68dp (8.5% de pantalla) ✅ ¡Perfecto!
```

### Beneficios Clave

- ✅ **Proporciones perfectas** en cualquier tamaño de pantalla
- ✅ **Funciona en todas partes**: Android, iOS, Flutter, React Native, Web
- ✅ **API simple**: Solo agrega `.fxdp` o `.fxsp` a tus dimensiones
- ✅ **Matemáticamente probado**: Basado en investigación psicofísica (Ley de Weber-Fechner)
- ✅ **Mejor rendimiento**: Caché inteligente lo hace más rápido que las alternativas

---

## 🚀 Instalación

### Android

```kotlin
dependencies {
    // Biblioteca principal (Escalado Fixed + Dynamic + Unidades Físicas)
    // Incluye: .fxdp, .dydp, Unidades Físicas (mm/cm/inch), Cálculos de grid
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.8")
    
    // Escalado SDP (DP escalable para XML)
    // Incluye: @dimen/_16sdp, etc.
    implementation("io.github.bodenberg:appdimens-sdps:1.0.8")
    
    // Escalado SSP (SP escalable para texto en XML)
    // Incluye: @dimen/_18ssp, etc.
    implementation("io.github.bodenberg:appdimens-ssps:1.0.8")
    
    // Todo en uno (incluye dynamic, sdps, ssps)
    // ⚠️ Nota: NO incluye el módulo de juegos
    implementation("io.github.bodenberg:appdimens-all:1.0.8")
    
    // Desarrollo de juegos (C++/NDK + OpenGL)
    // 🎮 Dependencia separada - no incluida en "all"
    implementation("io.github.bodenberg:appdimens-games:1.0.8")
}
```

### iOS

**CocoaPods:**
```ruby
# Paquete completo (Main + UI)
pod 'AppDimens', '~> 1.0.8'

# Solo módulo Main
pod 'AppDimens/Main', '~> 1.0.8'

# Módulo de juegos (separado)
pod 'AppDimens/Games', '~> 1.0.8'
```

**Swift Package Manager:**
```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.8")
]
```

### Flutter

```yaml
dependencies:
  appdimens: ^1.0.8
```

### React Native

```bash
# npm
npm install appdimens-react-native@1.0.8

# yarn
yarn add appdimens-react-native@1.0.8
```

### Web

```bash
# npm
npm install webdimens@1.0.8

# yarn
yarn add webdimens@1.0.8

# pnpm
pnpm add webdimens@1.0.8
```

**Vanilla JavaScript (CDN):**
```html
<script src="https://cdn.jsdelivr.net/npm/webdimens@1.0.8/dist/index.js"></script>
<script>
  const { fixed, dynamic, fluid } = WebDimens;
  
  document.getElementById('myElement').style.width = fixed(300).toPx();
</script>
```

**📖 [Guía Completa de Instalación](../../DOCS/README.md#-quick-start)**

---

## 💡 Uso Básico

### Android (Jetpack Compose)

```kotlin
@Composable
fun MyCard() {
    Card(
        modifier = Modifier
            .width(300.fxdp)      // ✨ Escalado fijo (RECOMENDADO)
            .padding(16.fxdp)     // ✨ Se adapta a la pantalla
    ) {
        Text(
            text = "Hola Mundo",
            fontSize = 18.fxsp    // ✨ Legible en todas partes
        )
    }
}
```

### Android (XML con SDP/SSP)

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="@dimen/_16sdp">
    
    <TextView
        android:layout_width="@dimen/_300sdp"
        android:layout_height="wrap_content"
        android:textSize="@dimen/_18ssp"
        android:text="Hola Mundo" />
</LinearLayout>
```

### Android (View Binding)

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Escalado dinámico
        val width = 300.fixedDp().toPx(resources)
        binding.card.layoutParams.width = width.toInt()
        
        // Unidades físicas
        val margin = AppDimensPhysicalUnits.toCm(2f, resources)
        binding.button.setPadding(margin.toInt(), 0, margin.toInt(), 0)
    }
}
```

### Android (Data Binding)

```xml
<!-- layout/activity_main.xml -->
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    
    <data>
        <import type="com.appdimens.dynamic.compose.AppDimensExtKt"/>
    </data>
    
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="@dimen/_16sdp">
        
        <TextView
            android:layout_width="@dimen/_300sdp"
            android:layout_height="wrap_content"
            android:textSize="@dimen/_18ssp"
            android:text="Hola Mundo" />
            
        <!-- Dimensiones dinámicas en DataBinding -->
        <Button
            android:id="@+id/button"
            android:layout_width="wrap_content"
            android:layout_height="@{AppDimensExtKt.fixedDp(48).dp}"
            android:text="Haz clic" />
    </LinearLayout>
</layout>
```

```kotlin
// Activity con DataBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = 
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        
        // Establecer dimensiones programáticamente
        binding.button.apply {
            layoutParams.width = 200.fixedDp().toPx(resources).toInt()
            layoutParams.height = 56.fixedDp().toPx(resources).toInt()
        }
    }
}
```

### Android (Unidades Físicas - incluidas en appdimens-dynamic)

```kotlin
// Usar medidas del mundo real
// Las Unidades Físicas son parte de appdimens-dynamic
val cardWidth = AppDimensPhysicalUnits.toCm(8f, resources)  // 8 cm
val buttonHeight = AppDimensPhysicalUnits.toInch(0.5f, resources)  // 0.5 pulgadas
val padding = AppDimensPhysicalUnits.toMm(10f, resources)  // 10 mm

view.layoutParams.width = cardWidth.toInt()
button.layoutParams.height = buttonHeight.toInt()
view.setPadding(padding.toInt(), padding.toInt(), padding.toInt(), padding.toInt())

// Cálculos de grid (también en appdimens-dynamic)
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

### Android (Módulo de Juegos)

```kotlin
@Composable
fun GameScreen() {
    val gamesManager = remember { AppDimensGames.getInstance() }
    
    LaunchedEffect(Unit) {
        gamesManager.initialize(context)
    }
    
    Canvas(modifier = Modifier.fillMaxSize()) {
        // Dimensiones específicas para juegos
        val buttonSize = gamesManager.calculateButtonSize(48f)
        val playerSize = gamesManager.calculatePlayerSize(64f)
        
        // Dibujar elementos del juego con dimensiones escaladas
        drawCircle(
            color = Color.Blue,
            radius = playerSize / 2
        )
    }
}
```

### iOS (SwiftUI)

```swift
struct MyCard: View {
    var body: some View {
        VStack {
            Text("Hola Mundo")
                .font(.fxSystem(size: 18))
        }
        .fxPadding(16)
        .fxFrame(width: 300)
    }
}
```

### iOS (UIKit)

```swift
class MyViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let containerView = UIView()
        containerView.backgroundColor = .systemBlue
        containerView.layer.cornerRadius = 16.fxpt
        view.addSubview(containerView)
        
        let titleLabel = UILabel()
        titleLabel.text = "Hola Mundo"
        titleLabel.fxFontSize(18)
        containerView.addSubview(titleLabel)
    }
}
```

### Flutter

```dart
Widget build(BuildContext context) {
  return Container(
    width: 300.fxdp(),
    padding: EdgeInsets.all(16.fxdp()),
    child: Text(
      'Hola Mundo',
      style: TextStyle(fontSize: 18.fxsp()),
    ),
  );
}
```

### React Native

{% raw %}
```jsx
function MyCard() {
  const { fx } = useAppDimens();
  
  return (
    <View style={{ width: fx(300), padding: fx(16) }}>
      <Text style={{ fontSize: fx(18) }}>
        Hola Mundo
      </Text>
    </View>
  );
}
```
{% endraw %}

### Web (Vanilla JavaScript)

```html
<!DOCTYPE html>
<html>
<head>
  <script src="https://cdn.jsdelivr.net/npm/webdimens@1.0.8/dist/index.js"></script>
</head>
<body>
  <div id="container">
    <header id="header">
      <h1 id="title">Hola Mundo</h1>
    </header>
  </div>
  
  <script type="module">
    import { webdimens } from 'https://cdn.jsdelivr.net/npm/webdimens@1.0.8/dist/index.mjs';
    
    // Aplicar dimensiones fijas
    document.getElementById('header').style.height = webdimens.fx(64);
    document.getElementById('title').style.fontSize = webdimens.fl(24, 48);
    document.getElementById('container').style.padding = webdimens.dy(24);
  </script>
</body>
</html>
```

### Web (React)

{% raw %}
```jsx
import { useWebDimens } from 'webdimens/react';

function MyCard() {
  const { fx, dy, fl } = useWebDimens();
  
  return (
    <div style={{ width: dy(300), padding: fx(16) }}>
      <h2 style={{ fontSize: fl(18, 24) }}>Hola Mundo</h2>
    </div>
  );
}
```
{% endraw %}

### Web (Vue)

```vue
<template>
  <div :style="{ width: dy(300), padding: fx(16) }">
    <h2 :style="{ fontSize: fl(18, 24) }">Hola Mundo</h2>
  </div>
</template>

<script setup>
import { useWebDimens } from 'webdimens/vue';

const { fx, dy, fl } = useWebDimens();
</script>
```

### Web (Svelte)

```svelte
<script>
  import { webDimensStore } from 'webdimens/svelte';
  
  $: wd = $webDimensStore;
  $: width = wd.dy(300);
  $: padding = wd.fx(16);
  $: fontSize = wd.fl(18, 24);
</script>

<div style="width: {width}; padding: {padding};">
  <h2 style="font-size: {fontSize};">Hola Mundo</h2>
</div>
```

### Web (Angular)

```typescript
import { Component } from '@angular/core';
import { WebDimensService } from 'webdimens/angular';

@Component({
  selector: 'app-card',
  template: `
    <div [ngStyle]="{ width: width, padding: padding }">
      <h2 [ngStyle]="{ fontSize: fontSize }">Hola Mundo</h2>
    </div>
  `
})
export class CardComponent {
  width = '';
  padding = '';
  fontSize = '';

  constructor(private wd: WebDimensService) {
    this.width = wd.dy(300);
    this.padding = wd.fx(16);
    this.fontSize = wd.fl(18, 24);
  }
}
```

**📖 [Más Ejemplos](../../DOCS/EXAMPLES.md)**

---

## 🎯 Modelos de Escalado

AppDimens ofrece múltiples estrategias de escalado para diferentes necesidades:

| Modelo | Cuándo Usar | Ejemplo |
|-------|-------------|---------|
| **Fixed (FX)** ⭐ **RECOMENDADO** | 95% de los casos - botones, texto, padding, márgenes | `16.fxdp` |
| **Dynamic (DY)** | Contenedores grandes, grids de ancho completo | `100.dydp` |
| **Fluid (FL)** | Tipografía con límites mín/máx (Web) | `fl(16, 24)` |
| **SDP/SSP** | Proyectos Android XML heredados | `@dimen/_16sdp` |

**📖 [Entendiendo los Modelos de Escalado](MATHEMATICAL_THEORY_SIMPLIFIED.md)**

---

## 🏆 Por qué AppDimens es #1

AppDimens fue comparado científicamente contra 7 otros enfoques de escalado:

```
🥇 #1 AppDimens:        91/100 ⭐⭐⭐⭐⭐
🥈 #2 RN Moderate:      78/100
🥉 #3 Flutter ScreenUtil: 72/100
   #4 SDP/SSP:          65/100
   #5 CSS vw/vh:        58/100
```

### ¿Qué lo hace mejor?

- ✅ **Única biblioteca** con escalado logarítmico (controla el sobretamaño)
- ✅ **Única biblioteca** con compensación automática de relación de aspecto
- ✅ **3.5× más preciso** que el escalado lineal
- ✅ **65% menos sobretamaño** en tabletas vs competidores
- ✅ **Más rápido con caché** (0.002µs vs 0.005µs)

**📊 [Ver Comparación Completa](FORMULA_COMPARISON.md)**

---

## 📚 Documentación

### Primeros Pasos

1. **[Referencia Rápida](DOCS_QUICK_REFERENCE.md)** ⚡ Encuentra cualquier cosa en segundos
2. **[Guía Simplificada](MATHEMATICAL_THEORY_SIMPLIFIED.md)** 📖 Entiende en 15 minutos
3. **[Ejemplos](../../DOCS/EXAMPLES.md)** 💻 Código listo para usar

### Documentación Técnica

4. **[Guía Técnica Completa](COMPREHENSIVE_TECHNICAL_GUIDE.md)** 🔬 Todo en un solo lugar (lectura de 2h)
5. **[Comparación de Fórmulas](FORMULA_COMPARISON.md)** 📊 Análisis científico y rankings
6. **[Teoría Matemática](MATHEMATICAL_THEORY.md)** 📐 Fundamento matemático formal

### Guías por Plataforma

- 🤖 [Guía Android](../../Android/README.md)
- 🍎 [Guía iOS](../../iOS/README.md)
- 🎯 [Guía Flutter](../../Flutter/README.md)
- ⚛️ [Guía React Native](../../ReactNative/README.md)
- 🌐 [Guía Web](../../Web/README.md)

**📚 [Índice Completo de Documentación](../../DOCS/README.md)**

---

## 🎮 Características Avanzadas

### Reglas de Escalado Personalizadas

```kotlin
// Android - Diferentes tamaños para diferentes dispositivos
val buttonSize = 56.fixedDp()
    .screen(UiModeType.TV, 96.dp)           // TVs: 96dp
    .screen(UiModeType.WATCH, 40.dp)        // Relojes: 40dp
    .screen(DpQualifier.SMALL_WIDTH, 600, 72.dp)  // Tabletas: 72dp
    .dp  // Otros: auto-escalado desde 56dp
```

### Unidades Físicas

```kotlin
// Android - Medidas del mundo real
Rectangle()
    .width(2.cm)    // 2 centímetros
    .height(1.inch) // 1 pulgada
```

### Desarrollo de Juegos

AppDimens incluye módulos especializados para desarrollo de juegos:

- 🎮 **Android**: Soporte C++/NDK + OpenGL ES
- 🎮 **iOS**: Integración Metal + MetalKit
- 🎮 Cálculos nativos de alto rendimiento

**📖 [Guía de Desarrollo de Juegos](../../Android/appdimens_games/README.md)**

---

## 🤝 Contribuir

¡Damos la bienvenida a contribuciones!

- 🐛 [Reportar bugs](https://github.com/bodenberg/appdimens/issues)
- 💡 [Sugerir características](https://github.com/bodenberg/appdimens/discussions)
- 📝 Mejorar documentación
- ⭐ ¡Dale estrella a este repo!

**📖 [Guía de Contribución](../../CONTRIBUTING.md)**

---

## 📄 Licencia

Apache License 2.0 - ver archivo [LICENSE](../../LICENSE)

---

## 👨‍💻 Autor

**Jean Bodenberg**
- GitHub: [@bodenberg](https://github.com/bodenberg)
- Sitio web: [appdimens-project.web.app](https://appdimens-project.web.app/)

---

## 🌟 Apoyo

Si AppDimens ayuda a tu proyecto:

- ⭐ **Dale estrella** a este repositorio
- 🐦 **Comparte** en redes sociales
- 📝 **Escribe** una reseña
- 🤝 **Contribuye** al proyecto

---

<div align="center">

**Hecho con ❤️ para desarrolladores de todo el mundo**

[Documentación](../../DOCS/README.md) • [Ejemplos](../../DOCS/EXAMPLES.md) • [Guía Técnica](COMPREHENSIVE_TECHNICAL_GUIDE.md)

</div>
