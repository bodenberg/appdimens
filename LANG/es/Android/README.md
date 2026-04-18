---
layout: default
---

<div align="center">
    <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens Android - Diseño Responsivo" height="250"/>
    <h1>📐 AppDimens Android</h1>
    <p><strong>Dimensionamiento Inteligente y Responsivo para Android</strong></p>
    <p>Escalado responsivo matemáticamente preciso que garantiza que su diseño de UI se adapte perfectamente a cualquier tamaño de pantalla o relación de aspecto — desde teléfonos hasta TVs, autos y wearables.</p>

[![Versión](https://img.shields.io/badge/version-1.1.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
[![Documentación](https://img.shields.io/badge/docs-complete-brightgreen.svg)](https://appdimens-project.web.app/)
</div>

> **Idiomas:** [English](../../../appdimens-dynamic/README.md) | [Português (BR)](../../pt-BR/Android/README.md) | Español

---

## 🎯 ¿Qué es AppDimens Android?

**AppDimens Android** es un sistema integral de dimensionamiento que reemplaza valores DP fijos por dimensiones escaladas inteligentemente basadas en las características reales de la pantalla. Mientras que el DP predeterminado de Android (1 DP = 1/160 pulgada) es constante, AppDimens lo trata como un valor base que escala de manera predecible en diferentes tamaños de pantalla, densidades y proporciones.

### 🎨 Beneficios Clave

- **🎯 Consistencia Visual**: Mantenga proporciones perfectas en todos los tipos de dispositivos Android
- **📱 Compatibilidad Universal**: Funciona perfectamente en teléfonos, tablets, TVs, autos y wearables
- **⚡ Rendimiento Optimizado**: Overhead mínimo de runtime con cálculos en caché
- **🔧 Integración Fácil**: API simple que funciona con Jetpack Compose, XML Views y Data Binding
- **📐 Precisión Matemática**: Dos modelos de escalado (Fixed & Dynamic) para diferentes necesidades de diseño
- **🎮 Desarrollo de Juegos**: Módulo C++/NDK especializado para desarrollo de juegos de alto rendimiento
- **🚀 Rendimiento Nativo**: Implementación C++ para cálculos específicos de juegos e integración OpenGL

---

## 🎮 Características de Desarrollo de Juegos

### Módulo AppDimens Games
El módulo **AppDimens Games** proporciona funcionalidad especializada para desarrollo de juegos Android con soporte nativo C++/NDK:

#### Características Clave:
- **Rendimiento Nativo**: Implementación C++ para cálculos de alto rendimiento
- **Tipos de Dimensión de Juego**: 
  - `DYNAMIC`: Escalado proporcional para contenedores
  - `FIXED`: Escalado logarítmico para elementos de UI
  - `GAME_WORLD`: Coordenadas consistentes del mundo del juego
  - `UI_OVERLAY`: Coordenadas de superposición de UI
- **Operaciones Vectoriales**: `GameVector2D` con operaciones matemáticas (suma, resta, multiplicación, normalización)
- **Utilidades de Rectángulo**: `GameRectangle` para gestión de límites y viewport
- **Integración OpenGL**: Utilidades para renderizado OpenGL ES
- **Monitoreo de Rendimiento**: Métricas de rendimiento en tiempo real y optimización

#### Ejemplo de Uso:
```kotlin
val appDimensGames = AppDimensGames.getInstance()
appDimensGames.initialize(context)

// Dimensiones específicas del juego
val buttonSize = appDimensGames.calculateButtonSize(48f)
val playerSize = appDimensGames.calculatePlayerSize(64f)
val uiOverlaySize = appDimensGames.calculateUISize(24f)

// Operaciones vectoriales
val position = GameVector2D(100f, 200f)
val scaledPosition = appDimensGames.calculateVector2D(position, GameDimensionType.GAME_WORLD)
```

---

## 🚀 Instalación

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    // Biblioteca principal (escalado Dynamic + Fixed)
    implementation("io.github.bodenberg:appdimens-dynamic:1.1.0")
    
    // Opcional: Escalado SDP & SSP
    implementation("io.github.bodenberg:appdimens-sdps:1.1.0")
    implementation("io.github.bodenberg:appdimens-ssps:1.1.0")
    
    // Paquete completo (no incluye módulo de juegos)
    implementation("io.github.bodenberg:appdimens-all:1.1.0")
    
    // Desarrollo de juegos con soporte C++/NDK (dependencia separada)
    implementation("io.github.bodenberg:appdimens-games:1.1.0")
}
```

### Gradle (Groovy)

```groovy
dependencies {
    implementation 'io.github.bodenberg:appdimens-dynamic:1.1.0'
    implementation 'io.github.bodenberg:appdimens-sdps:1.1.0'
    implementation 'io.github.bodenberg:appdimens-ssps:1.1.0'
    implementation 'io.github.bodenberg:appdimens-all:1.1.0'
    implementation 'io.github.bodenberg:appdimens-games:1.1.0'
}
```

### Configuración del Repositorio

```kotlin
repositories {
    mavenCentral()
    // o
    maven { url = uri("https://jitpack.io") }
}
```

---

## 🧠 Modelos de Dimensión Core

| Modelo | Filosofía | Caso de Uso Ideal | Soportado En |
|--------|-----------|-------------------|--------------|
| **Fixed (FX)** | Escalado logarítmico (refinado) | Botones, paddings, márgenes, iconos | Compose + Views + Data Binding |
| **Dynamic (DY)** | Escalado proporcional (agresivo) | Contenedores, grids, fuentes fluidas | Compose + Views + Data Binding |
| **SDP / SSP** | Recursos pre-calculados | Uso directo de `@dimen` en XML | Compose + Views (XML) |
| **Unidades Físicas** | mm/cm/pulgada → Dp/Sp/Px | Wearables, impresión, layouts de precisión | Compose + Views |

---

## 🎨 Ejemplos de Uso

### 🧩 Jetpack Compose

#### Escalado Fixed y Dynamic

```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.wdp)           // Ancho dinámico - proporcional a la pantalla
            .height(200.sdp)          // Alto fijo - escalado refinado
            .padding(16.sdp)          // Padding fijo - sensación consistente
    ) {
        Column(
            modifier = Modifier.padding(16.sdp)
        ) {
            Text(
                text = "Título Responsivo",
                fontSize = 18.ssp     // Tamaño de fuente fijo - lectura cómoda
            )
            Text(
                text = "Esta tarjeta se adapta a cualquier tamaño de pantalla",
                fontSize = 14.ssp     // Tamaño de fuente dinámico - proporcional
            )
        }
    }
}
```

#### SDP y SSP en Compose

```kotlin
@Composable
fun SDPExample() {
    Box(
        modifier = Modifier
            .padding(16.sdp)           // Padding SDP
            .size(100.sdp)             // Tamaño SDP
    ) {
        Text(
            text = "Texto Responsivo",
            fontSize = 18.ssp          // Tamaño de fuente SSP
        )
    }
}
```

### 📄 XML Views y Data Binding

#### Escalado Dynamic con Data Binding

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    app:dynamicWidthDp="@{100f}"
    app:dynamicHeightDp="@{56f}"
    app:dynamicTextSizeDp="@{20f}">
    
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Tamaño de Texto Dinámico"
        app:dynamicTextSizeDp="@{18f}" />
</LinearLayout>
```

#### SDP y SSP en XML

```xml
<TextView
    android:layout_width="@dimen/_100sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp"
    android:text="Ejemplo de Texto Responsivo"/>
```

### 💻 Código Kotlin/Java

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Escalado fijo - ajuste sutil
        val fixedWidthPx = AppDimens.fixedPx(
            value = 200f,
            screenType = ScreenType.LOWEST,
            resources = resources
        ).toInt()
        
        // Escalado dinámico - ajuste proporcional
        val dynamicTextSizeSp = AppDimens.dynamicSp(
            value = 18f,
            screenType = ScreenType.LOWEST,
            resources = resources
        )
        
        // Aplicar a las vistas
        myView.layoutParams.width = fixedWidthPx
        myTextView.textSize = dynamicTextSizeSp
    }
}
```

---

## 🔧 Características Avanzadas

### 🔄 Escalado Condicional

```kotlin
@Composable
fun ConditionalScaling() {
    val boxSize = 80.scaledDp()
        .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
        .screen(UiModeType.CAR, 120.dp)
        .screen(DpQualifier.SMALL_WIDTH, 720, 150)
    
    Box(
        modifier = Modifier
            .size(boxSize.sdp)
            .background(Color.Blue)
    )
}
```

### 📏 Unidades Físicas

```kotlin
@Composable
fun PhysicalUnits() {
    // 5 milímetros convertidos a Dp
    val marginDp = 5.0f.toDp(UnitType.MM)
    
    Box(
        modifier = Modifier
            .padding(marginDp)
            .size(1.0f.toDp(UnitType.INCH)) // 1 pulgada en Dp
    )
}
```

### 🧮 Utilidades de Layout

```kotlin
@Composable
fun ResponsiveGrid() {
    var spanCount by remember { mutableStateOf(3) }
    
    // Calcular número óptimo de columnas
    AppDimens.CalculateAvailableItemCount(
        itemSize = 100.dp,
        itemPadding = 4.dp,
        direction = DpQualifier.WIDTH,
        modifier = Modifier.fillMaxWidth(),
        onResult = { count ->
            if (count > 0) spanCount = count
        }
    )
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(spanCount),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(20) { index ->
            // Tus elementos de grid
        }
    }
}
```

---

## 📊 Resumen de Módulos

### 🎯 Módulos Core

| Módulo | Descripción | Caso de Uso |
|--------|-------------|-------------|
| **appdimens-dynamic** | Biblioteca principal con escalado Fixed & Dynamic | Esencial para todas las apps responsivas |
| **appdimens-sdps** | Escalado SDP con lógica condicional | Diseño responsivo basado en XML |
| **appdimens-ssps** | Escalado SSP con lógica condicional | Dimensionamiento de texto responsivo |
| **appdimens-all** | Paquete completo con todos los módulos (excepto juegos) | Solución completa para apps estándar |
| **appdimens-games** | Desarrollo de juegos con C++/NDK | Desarrollo de juegos Android |

### 🎮 Características del Módulo Games

- **Integración C++/NDK**: Rendimiento nativo para motores de juegos
- **Soporte OpenGL ES**: Utilidades para renderizado OpenGL ES 2.0/3.0
- **Gestión de Viewport**: Sistemas avanzados de cámara y viewport
- **Monitoreo de Rendimiento**: Métricas de rendimiento en tiempo real
- **Escalado Específico de Juegos**: Cálculos predefinidos para elementos de juego

#### 🎮 Uso del Módulo Games

El módulo Games proporciona funcionalidad especializada para desarrollo de juegos Android con soporte C++/NDK. Es una dependencia separada que no está incluida en el paquete `appdimens-all`.

**Integración Básica:**

```kotlin
class GameActivity : Activity() {
    private lateinit var appDimensGames: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar AppDimens Games
        appDimensGames = AppDimensGames.getInstance()
        appDimensGames.initialize(this)
        
        // Calcular dimensiones responsivas para elementos del juego
        val buttonSize = appDimensGames.calculateButtonSize(48.0f)
        val textSize = appDimensGames.calculateTextSize(16.0f)
        val playerSize = appDimensGames.calculatePlayerSize(64.0f)
        val enemySize = appDimensGames.calculateEnemySize(32.0f)
    }
}
```

**Integración C++:**

```cpp
#include "AppDimensGames.h"

class GameEngine {
private:
    AppDimensGames& appDimensGames;
    
public:
    GameEngine(JNIEnv* env, jobject context) {
        appDimensGames = AppDimensGames::getInstance();
        appDimensGames.initialize(env, context);
    }
    
    void updateGame() {
        // Calcular dimensiones para diferentes elementos del juego
        float buttonSize = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
        float playerSize = appDimensGames.calculateDimension(64.0f, GameDimensionType::GAME_WORLD);
        
        // Trabajar con vectores y rectángulos
        Vector2D baseVector(100.0f, 50.0f);
        Vector2D scaledVector = appDimensGames.calculateVector2D(baseVector, GameDimensionType::DYNAMIC);
    }
};
```

**Tipos de Dimensión de Juego:**
- **DYNAMIC**: Escalado proporcional para contenedores y layouts fluidos
- **FIXED**: Escalado logarítmico para elementos de UI y botones
- **GAME_WORLD**: Mantiene coordenadas consistentes del mundo para objetos del juego
- **UI_OVERLAY**: Para elementos HUD y superposición

Para documentación completa, vea [Módulo AppDimens Games](../../../appdimens-games/appdimens_games/README.md).

---

## 📱 Soporte de Dispositivos

### 📱 Tipos de Dispositivos Soportados

| Tipo de Dispositivo | Descripción | Comportamiento de Escalado |
|---------------------|-------------|---------------------------|
| **Phone** | Teléfonos Android estándar | Escalado balanceado |
| **Tablet** | Tablets Android | Escalado mejorado para pantallas más grandes |
| **TV** | Dispositivos Android TV | Optimizado para distancia de visualización |
| **Car** | Android Auto | Objetivos de toque grandes |
| **Watch** | Dispositivos Wear OS | Escalado compacto |
| **VR** | Cascos VR | Escalado inmersivo |

### 📐 Calificadores de Pantalla

| Calificador | Descripción | Caso de Uso |
|-------------|-------------|-------------|
| **SMALL_WIDTH** | Menor dimensión de pantalla | Predeterminado, más restrictivo |
| **WIDTH** | Ancho de pantalla | Layouts horizontales |
| **HEIGHT** | Alto de pantalla | Layouts verticales |

---

## ⚡ Rendimiento y Optimización

### 📊 Características de Rendimiento

| Característica | Overhead de Runtime | Uso de Memoria | Tiempo de Cálculo |
|----------------|-------------------|----------------|-------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Caché por configuración |
| **SDP/SSP** | Cero | ~2MB (recursos) | Pre-calculado |
| **Unidades Físicas** | ~0.002ms | ~10KB | Bajo demanda |

### 🚀 Consejos de Optimización

1. **Caché de Dimensiones**: Almacene dimensiones frecuentemente usadas en propiedades
2. **Use el Modelo Apropiado**: Fixed para elementos de UI, Dynamic para layouts
3. **Evite Anidamiento Excesivo**: Mantenga cadenas de dimensión simples
4. **Perfil de Rendimiento**: Use monitoreo de rendimiento integrado

---

## 🧪 Pruebas

### 📋 Cobertura de Pruebas

- ✅ Cálculos de dimensión
- ✅ Detección de tipo de dispositivo
- ✅ Cálculos de factor de pantalla
- ✅ Métodos de extensión
- ✅ Casos extremos y manejo de errores
- ✅ Benchmarks de rendimiento

### 🔧 Herramientas de Prueba

{% raw %}
```kotlin
// Debug de la configuración actual de pantalla
val (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
println("Pantalla: ${width} × ${height}")

// Debug del tipo de dispositivo
println("Dispositivo: ${DeviceType.current()}")

// Debug de los factores de ajuste
val factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
println("Factores: ${factors}")
```
{% endraw %}

---

## 📚 Documentación y Recursos

### 📖 Documentación Completa

- **[📘 Documentación Completa](https://appdimens-project.web.app/)** - Guías comprensivas y referencia de API
- **[🎯 Documentación Core](../../../DOCS/)** - Documentación técnica detallada
- **[🎮 Módulo Games](../../../appdimens-games/appdimens_games/README.md)** - Guía de desarrollo de juegos
- **[📱 Ejemplos](../../../appdimens-dynamic/app/src/main/java/)** - Ejemplos de uso del mundo real

### 🔗 Enlaces Rápidos

- **[🚀 Guía de Instalación](#instalación)** - Comience en minutos
- **[📱 Ejemplos](#ejemplos-de-uso)** - Ejemplos de uso del mundo real
- **[🔧 Referencia de API](../../../DOCS/)** - Documentación completa de API
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Preguntas y respuestas comunes

---

## 🏗️ Resumen de Arquitectura

### Estructura de la Biblioteca Android

| Módulo | Propósito | Dependencias | Características Clave |
|--------|-----------|--------------|---------------------|
| **appdimens_library** | Tipos e interfaces core | Ninguna | Enums base, calificadores, factores de ajuste |
| **appdimens_dynamic** | Escalado Dynamic/Fixed | appdimens_library | Modelos DY/FX, extensiones Compose, caché |
| **appdimens_sdps** | Escalado SDP | appdimens_library | 426+ recursos @dimen pre-calculados |
| **appdimens_ssps** | Escalado SSP | appdimens_library | 216+ recursos @dimen pre-calculados |
| **appdimens_games** | Desarrollo de juegos | appdimens_library, appdimens_dynamic | C++/NDK, utilidades OpenGL, monitoreo de rendimiento |
| **appdimens_all** | Paquete completo | Todos los módulos | Funcionalidad completa en dependencia única |

### Características de Rendimiento

| Característica | Overhead de Runtime | Uso de Memoria | Tiempo de Cálculo | Estrategia de Caché |
|----------------|-------------------|----------------|-------------------|-------------------|
| **Dynamic/Fixed** | ~0.001ms | ~50KB | Caché por configuración | Seguimiento automático de dependencias |
| **SDP/SSP** | Cero | ~2MB (recursos) | Pre-calculado | Basado en recursos |
| **Unidades Físicas** | ~0.002ms | ~10KB | Bajo demanda | Inicialización perezosa |
| **Games (Nativo)** | ~0.0005ms | ~100KB | Caché con LRU | Implementación C++ nativa |

---

## 🤝 Contribuyendo

¡Aceptamos contribuciones! Vea nuestras [Pautas de Contribución](../../../CONTRIBUTING.md) para detalles.

### 🐛 ¿Encontró un Bug?
- [Crear un issue](https://github.com/bodenberg/appdimens/issues)
- Incluya información del dispositivo y pasos para reproducir
- Adjunte capturas de pantalla si es aplicable

### 💡 ¿Tiene una Idea?
- [Iniciar una discusión](https://github.com/bodenberg/appdimens/discussions)
- Proponga nuevas características o mejoras
- Comparta sus casos de uso

---

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia Apache 2.0 - vea el archivo [LICENSE](../../../LICENSE) para detalles.

---

## 👨‍💻 Autor

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 Muestre Su Apoyo

Si AppDimens Android ha ayudado a su proyecto, considere:

- ⭐ **Hacer star** a este repositorio
- 🐦 **Compartir** en redes sociales
- 📝 **Escribir** una reseña o post de blog
- 🤝 **Contribuir** con código o documentación

---

<div align="center">
    <p><strong>Hecho con ❤️ para la comunidad de desarrollo Android</strong></p>
    <p>AppDimens Android - Donde el diseño responsivo se encuentra con la precisión matemática</p>
</div>