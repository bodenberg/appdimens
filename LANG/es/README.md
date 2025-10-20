<div align="center">
    <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens - Diseño Responsivo en Todos los Dispositivos" height="300"/>
    <h1>📐 AppDimens</h1>
    <p><strong>Dimensionamiento Inteligente y Responsivo para Android & iOS</strong></p>
    <p>Escalado responsivo matemáticamente preciso que garantiza que su diseño de UI se adapte perfectamente a cualquier tamaño de pantalla o relación de aspecto — desde teléfonos hasta TVs, autos y wearables.</p>

[![Versión](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%20%7C%20iOS-orange.svg)](https://github.com/bodenberg/appdimens)
[![Documentación](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

> Idiomas: [English](../../README.md) | [Português (BR)](../pt-BR/README.md) | [हिन्दी](../hi/README.md) | [Русский](../ru/README.md) | [中文](../zh/README.md) | [日本語](../ja/README.md)

---

## 🎯 ¿Qué es AppDimens?

**AppDimens** es un sistema integral de dimensionamiento que reemplaza valores fijos de píxeles con dimensiones escaladas inteligentemente basadas en las características reales de la pantalla. Mientras que los DP/Points tradicionales son constantes, AppDimens los trata como valores base que escalan de manera predecible en diferentes tamaños de pantalla, densidades y relaciones de aspecto.

### 🎨 Beneficios Principales

- **🎯 Consistencia Visual**: Mantenga proporciones perfectas en todos los tipos de dispositivos
- **📱 Compatibilidad Universal**: Funciona perfectamente en teléfonos, tablets, TVs, autos y wearables
- **⚡ Optimizado para Rendimiento**: Sobrecarga mínima en tiempo de ejecución con cálculos en caché
- **🔧 Integración Fácil**: API simple que funciona con Jetpack Compose, XML Views, SwiftUI y UIKit
- **📐 Precisión Matemática**: Dos modelos de escala (Fixed & Dynamic) para diferentes necesidades de diseño
- **🎮 Desarrollo de Juegos**: Módulo especializado C++/NDK para desarrollo de juegos de alto rendimiento
- **🚀 Rendimiento Nativo**: Implementación C++ para cálculos específicos de juegos e integración OpenGL

---

## 🎮 Funcionalidades de Desarrollo de Juegos

### Android Games (C++/NDK)
- **Rendimiento Nativo**: Implementación C++ para cálculos de alto rendimiento
- **Tipos de Dimensión de Juego**: DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY
- **Operaciones Vectoriales**: GameVector2D con operaciones matemáticas
- **Gestión de Viewport**: Múltiples modos de escala para diferentes escenarios de juego
- **Integración OpenGL**: Utilidades para renderizado OpenGL ES

### iOS Games (Metal)
- **Integración Metal**: Soporte nativo Metal y MetalKit
- **Escala de Viewport**: Modos uniform, horizontal, vertical, aspect-ratio, viewport
- **Conversión de Coordenadas**: Transformaciones Screen ↔ NDC
- **Optimizado para Rendimiento**: Extensiones SIMD para operaciones vectoriales
- **Integración SwiftUI**: Extensiones SwiftUI específicas para juegos

---

## 🚀 Inicio Rápido

### Android

```kotlin
dependencies {
    // Biblioteca principal (Dynamic + Fixed scaling)
    implementation("io.github.bodenberg:appdimens-dynamic:1.0.6")
    
    // Opcional: SDP & SSP scaling
    implementation("io.github.bodenberg:appdimens-sdps:1.0.6")
    implementation("io.github.bodenberg:appdimens-ssps:1.0.6")
    
    // Paquete completo (no incluye módulo de juegos)
    implementation("io.github.bodenberg:appdimens-all:1.0.6")
    
    // Desarrollo de juegos con soporte C++/NDK (dependencia separada)
    implementation("io.github.bodenberg:appdimens-games:1.0.6")
}
```

### iOS

```ruby
# Podfile
pod 'AppDimens'
```

```swift
// Swift Package Manager
.package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.6")
```

---

## 🧠 Modelos de Dimensión Principales

| Modelo | Filosofía | Caso de Uso Ideal | Plataformas Soportadas | Implementación |
|-------|------------|----------------|-------------------|----------------|
| **Fixed (FX)** | Escala logarítmica (refinada) | Botones, paddings, márgenes, íconos | Android + iOS | Ajuste matemático de proporción |
| **Dynamic (DY)** | Escala proporcional (agresiva) | Contenedores, grids, fuentes fluidas | Android + iOS | Escalado proporcional basado en pantalla |
| **SDP / SSP** | Recursos pre-calculados | Uso directo de `@dimen` en XML | Android | 426+ archivos de dimensión pre-generados |
| **Unidades Físicas** | mm/cm/inch → Dp/Sp/Px/Points | Wearables, impresión, layouts de precisión | Android + iOS | Conversión de medidas del mundo real |
| **Dimensiones de Juego** | Escalado especializado para juegos | UI de juego, viewports, Metal/OpenGL | Android + iOS | Implementación nativa C++/NDK + Metal |

---

## 📱 Ejemplos de Plataforma

### 🤖 Android - Jetpack Compose

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.dydp)           // Ancho dinámico
            .height(200.fxdp)          // Altura fija
            .padding(16.fxdp)          // Padding fijo
    ) {
        Column(
            modifier = Modifier.padding(16.fxdp)
        ) {
            Text(
                text = "Título Responsivo",
                fontSize = 18.fxsp     // Tamaño de fuente fijo
            )
            Text(
                text = "Esta tarjeta se adapta a cualquier tamaño de pantalla",
                fontSize = 14.dysp     // Tamaño de fuente dinámico
            )
        }
    }
}
```

### 🍎 iOS - SwiftUI

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Título Responsivo")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("Esta tarjeta se adapta a cualquier tamaño de pantalla")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // Ancho dinámico
        .fxFrame(height: 200)          // Altura fija
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

### 📄 Android - XML Views

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="@dimen/_16sdp">
    
    <TextView
        android:layout_width="@dimen/_300sdp"
        android:layout_height="wrap_content"
        android:textSize="@dimen/_18ssp"
        android:text="Texto Responsivo" />
        
    <Button
        android:layout_width="@dimen/_120sdp"
        android:layout_height="@dimen/_48sdp"
        android:text="Acción" />
</LinearLayout>
```

---

## 🎨 Características Avanzadas

### 🔄 Escala Condicional

```kotlin
// Android
val buttonSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

```swift
// iOS
let buttonSize = AppDimens.fixed(80)
    .screen(.watch, 40)           // 40pt para Apple Watch
    .screen(.tablet, 120)         // 120pt para iPad
    .aspectRatio(enable: true)    // Activar ajuste de proporción
    .toPoints()
```

### 📏 Unidades Físicas

```kotlin
// Android
val marginPx = AppDimensPhysicalUnits.toMm(5f, resources)
view.setPadding(marginPx.toInt(), 0, 0, 0)
```

```swift
// iOS
Rectangle()
    .frame(width: 2.cm, height: 1.cm)  // Unidades físicas
```

### 🧮 Utilidades de Layout

```kotlin
// Android - Calcular número óptimo de columnas de grid
val spanCount = AppDimens.calculateAvailableItemCount(
    containerSizePx = recyclerView.width,
    itemSizeDp = 100f,
    itemMarginDp = 8f,
    resources = resources
)
```

---

## 📊 Rendimiento y Compatibilidad

### ⚡ Características de Rendimiento

| Característica | Sobrecarga en Runtime | Uso de Memoria | Tiempo de Cálculo |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Caché por configuración |
| **SDP/SSP** | Cero | ~2MB (recursos) | Pre-calculado |
| **Unidades Físicas** | ~0.002ms | ~10KB | Bajo demanda |

### 📱 Soporte de Plataforma

| Plataforma | Versión Mínima | Frameworks de UI | Características Especiales |
|----------|-------------|---------------|------------------|
| **Android** | API 21+ | Compose, Views, Data Binding | SDP/SSP, Unidades Físicas |
| **iOS** | 13.0+ | SwiftUI, UIKit | Extensiones nativas |

---

## 📚 Documentación y Recursos

### 📖 Documentación Completa

- **[📘 Documentación Completa](https://appdimens-project.web.app/)** - Guías completas y referencia de API
- **[🤖 Guía Android](../../Android/README.md)** - Documentación específica para Android
- **[🍎 Guía iOS](../../iOS/README.md)** - Documentación específica para iOS
- **[🎮 Módulo de Juegos](../../Android/appdimens_games/README.md)** - Desarrollo de juegos con C++/NDK

### 🎯 Enlaces Rápidos

- **[🚀 Guía de Instalación](../../Android/README.md#installation)** - Comience en minutos
- **[📱 Ejemplos](../../Android/app/src/main/kotlin/)** - Ejemplos de uso en el mundo real
- **[🔧 Referencia de API](../../Android/DOCS/)** - Documentación completa de la API
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Preguntas y respuestas comunes

---

## 🎯 Casos de Uso

### 📱 Aplicaciones Móviles
Perfecto para apps que necesitan funcionar en diferentes tamaños de teléfono y orientaciones.

### 📺 Apps de TV y Auto
Ideal para aplicaciones Android TV y Android Auto con tamaños de pantalla variados.

### ⌚ Apps Wearable
Esencial para apps Wear OS que necesitan adaptarse a diferentes tamaños de reloj.

### 🎮 Desarrollo de Juegos
Módulo especializado para desarrollo de juegos con soporte C++/NDK e integración OpenGL.

### 🏢 Apps Empresariales
Excelente para aplicaciones de negocios que necesitan funcionar en tablets, teléfonos y escritorio.

---

## 🤝 Contribuyendo

¡Aceptamos contribuciones! Por favor, vea nuestras [Directrices de Contribución](CONTRIBUTING.md) para más detalles.

### 🐛 ¿Encontró un Error?
- [Crear un issue](https://github.com/bodenberg/appdimens/issues)
- Incluya información del dispositivo y pasos de reproducción
- Adjunte capturas de pantalla si es aplicable

### 💡 ¿Tiene una Idea?
- [Iniciar una discusión](https://github.com/bodenberg/appdimens/discussions)
- Proponga nuevas características o mejoras
- Comparta sus casos de uso

---

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia Apache 2.0 - vea el archivo [LICENSE](../../LICENSE) para más detalles.

---

## 👨‍💻 Autor

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 Muestre su Apoyo

Si AppDimens ha ayudado a su proyecto, por favor considere:

- ⭐ **Dar una estrella** a este repositorio
- 🐦 **Compartir** en redes sociales
- 📝 **Escribir** una reseña o publicación de blog
- 🤝 **Contribuir** con código o documentación

---

<div align="center">
    <p><strong>Hecho con ❤️ para la comunidad de desarrollo móvil</strong></p>
    <p>AppDimens - Donde el diseño responsivo se encuentra con la precisión matemática</p>
</div>
