<div align="center">
    <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens iOS - Diseño Responsivo" height="250"/>
    <h1>📐 AppDimens iOS</h1>
    <p><strong>Dimensionamiento Inteligente y Responsivo para iOS</strong></p>
    <p>Escalado responsivo matemáticamente preciso que garantiza que su diseño de UI se adapte perfectamente a cualquier tamaño de pantalla o relación de aspecto — desde iPhones hasta iPads, Apple TV y Apple Watch.</p>

[![Versión](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-iOS%2013+-orange.svg)](https://developer.apple.com/ios/)
[![Swift](https://img.shields.io/badge/Swift-5.0+-blue.svg)](https://swift.org/)
</div>

> Idiomas: [English](../../../iOS/README.md) | [Português (BR)](../pt-BR/iOS/README.md) | [हिन्दी](../hi/iOS/README.md) | [Русский](../ru/iOS/README.md) | [中文](../zh/iOS/README.md) | [日本語](../ja/iOS/README.md)

---

## 🎯 ¿Qué es AppDimens iOS?

**AppDimens iOS** es un sistema integral de dimensionamiento que reemplaza valores fijos de points por dimensiones escaladas inteligentemente basadas en las características reales de la pantalla. Mientras que los points predeterminados de iOS son constantes, AppDimens los trata como valores base que escalan de manera predecible en diferentes tamaños de pantalla, densidades y proporciones.

La biblioteca está organizada en tres módulos:
- **Principal**: Funcionalidad unificada de gestión de dimensiones con caché avanzado y calificadores
- **UI**: Extensiones e integraciones UIKit y SwiftUI  
- **Games**: Funcionalidad específica Metal para desarrollo de juegos

### 🎨 Beneficios Clave

- **🎯 Consistencia Visual**: Mantenga proporciones perfectas en todos los tipos de dispositivos iOS
- **📱 Compatibilidad Universal**: Funciona perfectamente en iPhones, iPads, Apple TV y Apple Watch
- **⚡ Rendimiento Optimizado**: Overhead mínimo de runtime con cálculos en caché
- **🔧 Integración Fácil**: API simple que funciona con SwiftUI y UIKit
- **📐 Precisión Matemática**: Dos modelos de escalado (Fixed & Dynamic) para diferentes necesidades de diseño
- **🍎 iOS Nativo**: Construido específicamente para iOS con Swift y APIs nativas
- **🎮 Desarrollo de Juegos**: Módulo Metal especializado para desarrollo de juegos de alto rendimiento
- **🚀 Integración Metal**: Soporte nativo Metal y MetalKit con optimizaciones SIMD

---

## 🎮 Características de Desarrollo de Juegos

### Módulo AppDimens Games
El módulo **AppDimens Games** proporciona funcionalidad especializada para desarrollo de juegos iOS con soporte Metal y MetalKit:

#### Características Clave:
- **Integración Metal**: Soporte nativo Metal y MetalKit para renderizado de alto rendimiento
- **Modos de Escalado de Viewport**:
  - `Uniform`: Escalado uniforme para proporciones consistentes
  - `Horizontal`: Escalado horizontal para juegos en paisaje
  - `Vertical`: Escalado vertical para juegos en retrato
  - `AspectRatio`: Escalado consciente de la proporción
  - `Viewport`: Escalado basado en viewport para layouts complejos
- **Conversión de Coordenadas**: Transformaciones de coordenadas Pantalla ↔ NDC
- **Extensiones SIMD**: Operaciones vectoriales optimizadas usando framework simd
- **Integración SwiftUI**: Extensiones SwiftUI específicas de juegos y sistema de ambiente
- **Rendimiento Optimizado**: Implementación Swift nativa con aceleración Metal

#### Ejemplo de Uso:
```swift
// Dimensiones específicas del juego
let buttonSize = gameUniform(48)        // Escalado uniforme
let playerSize = gameAspectRatio(64)    // Escalado de proporción
let uiOverlaySize = gameViewport(24)    // Escalado de viewport

// Integración SwiftUI
struct GameView: View {
    var body: some View {
        VStack {
            Text("Puntuación: 1000")
                .font(.system(size: gameUniform(24)))
            
            MetalGameView()
                .frame(
                    width: gameAspectRatio(320),
                    height: gameAspectRatio(240)
                )
        }
        .withAppDimens()  // Habilitar ambiente AppDimens
    }
}
```

---

## 🚀 Instalación

### CocoaPods (Recomendado)

```ruby
# Podfile
platform :ios, '13.0'
use_frameworks!

target 'SuApp' do
  pod 'AppDimens'
end
```

```bash
pod install
```

### Swift Package Manager

1. **En Xcode:**
   - File → Add Package Dependencies
   - Ingrese: `https://github.com/bodenberg/appdimens.git`
   - Seleccione la versión: `1.0.5` o superior
   - Agregue a su target

2. **O agregue a Package.swift:**
```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
]
```

### Instalación Manual

1. **Descargue el código fuente:**
```bash
git clone https://github.com/bodenberg/appdimens.git
```

2. **Copie la carpeta Sources:**
   - Copie `Sources/AppDimens/` a su proyecto
   - Agregue todos los archivos Swift a su proyecto Xcode

---

## 🧠 Modelos de Dimensión Core

| Modelo | Filosofía | Caso de Uso Ideal | Soportado En |
|--------|-----------|-------------------|--------------|
| **Fixed (FX)** | Escalado logarítmico (refinado) | Botones, paddings, márgenes, iconos | SwiftUI + UIKit |
| **Dynamic (DY)** | Escalado proporcional (agresivo) | Contenedores, grids, fuentes fluidas | SwiftUI + UIKit |
| **Unidades Físicas** | mm/cm/pulgada → Points | Wearables, impresión, layouts de precisión | SwiftUI + UIKit |

---

## 🎨 Ejemplos de Uso

### 🧩 SwiftUI

#### Diseño Responsivo Básico

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("Título Responsivo")
                .font(.fxSystem(size: 24, weight: .bold))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(12)
                .foregroundColor(.blue)
            
            Button("Acción") {
                // Acción del botón
            }
            .fxFrame(width: 120, height: 44)
            .fxCornerRadius(8)
        }
        .fxPadding(16)
    }
}
```

#### Escalado Condicional Avanzado

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Título de la Tarjeta")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("Esta tarjeta se adapta a cualquier tamaño de pantalla con escalado inteligente.")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
            
            HStack {
                Spacer()
                Button("Acción") { }
                    .fxFrame(width: 80, height: 32)
                    .fxCornerRadius(6)
            }
        }
        .fxPadding(16)
        .dyFrame(width: 300)           // Ancho dinámico
        .fxFrame(height: 200)          // Alto fijo
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

#### Integración de Ambiente (Recomendado)

```swift
@main
struct MyApp: App {
    var body: some Scene {
        WindowGroup {
            DimensProvider {  // Esencial para nuevas características
                ContentView()
            }
        }
    }
}

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("AppDimens Mejorado")
                .font(.fxSystem(size: 24, weight: .bold))
            
            // API basada en protocolo
            Rectangle()
                .frame(width: 100.fixed().dimension)
                .frame(height: 50.fxpt)
            
            // Unidades físicas
            Rectangle()
                .frame(width: 2.cm, height: 1.cm)
            
            // Calculadora de conteo de elementos
            Rectangle()
                .calculateAvailableItemCount(
                    itemSize: 50.fxpt,
                    itemPadding: 8.fxpt,
                    count: $itemCount
                )
        }
    }
}
```

### 📱 UIKit

#### Integración UIKit Básica

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupUI()
    }
    
    private func setupUI() {
        // Contenedor
        let containerView = UIView()
        containerView.backgroundColor = .systemBlue
        containerView.fxCornerRadius(16)
        view.addSubview(containerView)
        
        // Etiqueta
        let titleLabel = UILabel()
        titleLabel.text = "Título Responsivo"
        titleLabel.textAlignment = .center
        titleLabel.fxFontSize(20)
        containerView.addSubview(titleLabel)
        
        // Botón
        let button = UIButton(type: .system)
        button.setTitle("Acción", for: .normal)
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        containerView.addSubview(button)
        
        // Constraints
        containerView.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        button.translatesAutoresizingMaskIntoConstraints = false
        
        NSLayoutConstraint.activate([
            // Contenedor - ancho dinámico, alto fijo
            containerView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            containerView.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            containerView.widthAnchor.constraint(equalToConstant: 300.dypt),
            containerView.heightAnchor.constraint(equalToConstant: 200.fxpt),
            
            // Etiqueta
            titleLabel.topAnchor.constraint(equalTo: containerView.topAnchor, constant: 20.fxpt),
            titleLabel.leadingAnchor.constraint(equalTo: containerView.leadingAnchor, constant: 16.fxpt),
            titleLabel.trailingAnchor.constraint(equalTo: containerView.trailingAnchor, constant: -16.fxpt),
            
            // Botón
            button.centerXAnchor.constraint(equalTo: containerView.centerXAnchor),
            button.centerYAnchor.constraint(equalTo: containerView.centerYAnchor),
            button.widthAnchor.constraint(equalToConstant: 120.dypt),
            button.heightAnchor.constraint(equalToConstant: 44.fxpt)
        ])
    }
}
```

#### Configuración UIKit Avanzada

```swift
class AdvancedViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        setupAdvancedUI()
    }
    
    private func setupAdvancedUI() {
        // Dimensiones personalizadas con valores específicos del dispositivo
        let customDimension = AppDimens.fixed(16)
            .screen(.phone, 14)           // 14pt para teléfonos
            .screen(.tablet, 18)          // 18pt para tablets
            .aspectRatio(enable: true)    // Habilitar ajuste de proporción
            .toPoints()
        
        // Dinámico con tipo de pantalla personalizado
        let dynamicDimension = AppDimens.dynamic(100)
            .type(.highest)               // Usar mayor dimensión de pantalla
            .toPoints()
        
        // Aplicar a elementos de UI
        let label = UILabel()
        label.font = UIFont.systemFont(ofSize: customDimension)
        label.text = "Texto escalado personalizado"
        
        let view = UIView()
        view.frame = CGRect(x: 0, y: 0, width: dynamicDimension, height: 50.fxpt)
        
        view.addSubview(label)
        self.view.addSubview(view)
    }
}
```

---

## 🔧 Características Avanzadas

### 🔄 Escalado Condicional

```swift
// Calificadores de pantalla personalizados
let customDimension = AppDimens.fixed(16)
    .screen(.phone, 14)           // 14pt para teléfonos
    .screen(.tablet, 18)          // 18pt para tablets
    .screen(.watch, 12)           // 12pt para Apple Watch
    .aspectRatio(enable: true)    // Habilitar ajuste de proporción
    .toPoints()

// Dinámico con tipo de pantalla personalizado
let dynamicDimension = AppDimens.dynamic(100)
    .type(.highest)               // Usar mayor dimensión de pantalla
    .toPoints()
```

### 📏 Unidades Físicas

```swift
// Conversión de unidades físicas
Rectangle()
    .frame(width: 2.cm, height: 1.cm)    // 2cm × 1cm
    .frame(width: 5.mm, height: 3.mm)    // 5mm × 3mm
    .frame(width: 1.inch, height: 0.5.inch) // 1 pulgada × 0.5 pulgada
```

### 🧮 Utilidades de Layout

```swift
struct ResponsiveGrid: View {
    let items = Array(1...12)
    
    var body: some View {
        LazyVGrid(columns: [
            GridItem(.flexible(), spacing: 16.fxpt),
            GridItem(.flexible(), spacing: 16.fxpt)
        ], spacing: 16.fxpt) {
            ForEach(items, id: \.self) { item in
                VStack {
                    Image(systemName: "star.fill")
                        .font(.fxSystem(size: 24))
                        .foregroundColor(.yellow)
                    
                    Text("Elemento \(item)")
                        .font(.fxSystem(size: 12))
                }
                .fxFrame(width: 80, height: 80)
                .background(Color(.systemGray5))
                .fxCornerRadius(8)
            }
        }
        .fxPadding(16)
    }
}
```

### 📊 Layouts Basados en Porcentaje

```swift
struct PercentageLayout: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            // 80% del ancho de pantalla
            Rectangle()
                .fill(Color.blue.opacity(0.3))
                .dyFrame(width: AppDimens.percentage(0.8))
                .fxFrame(height: 100)
                .fxCornerRadius(8)
            
            // 60% del ancho de pantalla
            Rectangle()
                .fill(Color.green.opacity(0.3))
                .dyFrame(width: AppDimens.percentage(0.6))
                .fxFrame(height: 80)
                .fxCornerRadius(8)
        }
    }
}
```

---

## 📊 Modelos Matemáticos

### 🎯 Modelo Fixed (FX)

**Filosofía**: Ajuste logarítmico para escalado refinado

**Fórmula**: 
```
Valor Final = Base Points × (1 + Factor de Ajuste × (Incremento Base + Ajuste AR))
```

**Características**:
- Crecimiento suave y controlado
- Se ralentiza en pantallas muy grandes
- Mantiene consistencia visual
- Ideal para elementos de UI

**Casos de Uso**:
- Alturas y anchos de botones
- Padding y márgenes
- Tamaños de iconos
- Tamaños de fuente para legibilidad

### 🚀 Modelo Dynamic (DY)

**Filosofía**: Ajuste proporcional basado en porcentaje

**Fórmula**:
```
Valor Final = (Base Points / Ancho de Referencia) × Dimensión Actual de Pantalla
```

**Características**:
- Crecimiento lineal y proporcional
- Mantiene porcentaje del espacio de pantalla
- Escalado agresivo en pantallas grandes
- Ideal para contenedores de layout

**Casos de Uso**:
- Anchos y alturas de contenedores
- Tamaños de elementos de grid
- Dimensiones de espaciadores
- Elementos dependientes de viewport

---

## 📱 Soporte de Dispositivos

### 📱 Tipos de Dispositivos Soportados

| Tipo de Dispositivo | Descripción | Comportamiento de Escalado |
|---------------------|-------------|---------------------------|
| **Phone** | Dispositivos iPhone | Escalado balanceado |
| **Tablet** | Dispositivos iPad | Escalado mejorado para pantallas más grandes |
| **Watch** | Dispositivos Apple Watch | Escalado compacto |
| **TV** | Dispositivos Apple TV | Elementos de UI grandes para distancia de visualización |
| **CarPlay** | Dispositivos CarPlay | Objetivos de toque grandes |

### 📐 Tipos de Pantalla

| Tipo | Descripción | Caso de Uso |
|------|-------------|-------------|
| **Lowest** | Usar menor dimensión de pantalla | Predeterminado, más restrictivo |
| **Highest** | Usar mayor dimensión de pantalla | Para elementos que deben escalar con la mayor dimensión |

---

## ⚡ Rendimiento y Optimización

### 📊 Características de Rendimiento

| Característica | Overhead de Runtime | Uso de Memoria | Tiempo de Cálculo |
|----------------|-------------------|----------------|-------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Caché por configuración |
| **Unidades Físicas** | ~0.002ms | ~10KB | Bajo demanda |

### 🚀 Estrategias de Optimización

1. **Cálculos en Caché**: Los factores de ajuste se computan una vez por cambio de configuración
2. **Evaluación Perezosa**: Los valores se computan solo cuando son necesarios
3. **Overhead Mínimo**: Operaciones matemáticas simples con asignación mínima de memoria

### 💡 Mejores Prácticas

1. **Use Fixed para Elementos de UI**: Botones, paddings, fuentes, iconos
2. **Use Dynamic para Layout**: Anchos de contenedores, espaciadores, elementos de grid
3. **Caché de Dimensiones**: Almacene dimensiones frecuentemente usadas en propiedades
4. **Evite Anidamiento Excesivo**: Mantenga cadenas de dimensión simples

---

## 🧪 Pruebas y Debug

### 🔧 Herramientas de Debug

```swift
// Debug de la configuración actual de pantalla
let (width, height) = AppDimensAdjustmentFactors.getCurrentScreenDimensions()
print("Pantalla: \(width) × \(height)")

// Debug del tipo de dispositivo
print("Dispositivo: \(DeviceType.current())")

// Debug de los factores de ajuste
let factors = AppDimensAdjustmentFactors.calculateAdjustmentFactors()
print("Factores: \(factors)")
```

### 📋 Cobertura de Pruebas

- ✅ Cálculos de dimensión
- ✅ Detección de tipo de dispositivo
- ✅ Cálculos de factor de pantalla
- ✅ Métodos de extensión
- ✅ Casos extremos y manejo de errores
- ✅ Benchmarks de rendimiento

---

## 📚 Referencia de API

### 🎯 Clases Core

| Clase | Descripción | Métodos Clave |
|-------|-------------|---------------|
| **AppDimens** | Punto de entrada principal | `fixed()`, `dynamic()`, `percentage()` |
| **AppDimensFixed** | Escalado fijo | `screen()`, `aspectRatio()`, `type()` |
| **AppDimensDynamic** | Escalado dinámico | `screen()`, `type()` |
| **AppDimensAdjustmentFactors** | Cálculos de pantalla | `getCurrentScreenDimensions()`, `calculateAdjustmentFactors()` |

### 🔧 Funciones de Extensión

| Extensión | Descripción | Ejemplo |
|-----------|-------------|---------|
| `.fxpt` | Points fijos | `16.fxpt` |
| `.fxpx` | Pixels fijos | `16.fxpx` |
| `.dypt` | Points dinámicos | `100.dypt` |
| `.dypx` | Pixels dinámicos | `100.dypx` |
| `.cm` | Centímetros | `2.cm` |
| `.mm` | Milímetros | `5.mm` |
| `.inch` | Pulgadas | `1.inch` |

### 🎨 Extensiones SwiftUI

| Extensión | Descripción | Ejemplo |
|-----------|-------------|---------|
| `.fxPadding()` | Padding fijo | `.fxPadding(16)` |
| `.fxFrame()` | Frame fijo | `.fxFrame(width: 100, height: 50)` |
| `.fxCornerRadius()` | Radio de esquina fijo | `.fxCornerRadius(8)` |
| `.dyFrame()` | Frame dinámico | `.dyFrame(width: 200)` |
| `.font(.fxSystem())` | Fuente fija | `.font(.fxSystem(size: 16))` |

### 📱 Extensiones UIKit

| Extensión | Descripción | Ejemplo |
|-----------|-------------|---------|
| `.fxFontSize()` | Tamaño de fuente fijo | `label.fxFontSize(16)` |
| `.fxCornerRadius()` | Radio de esquina fijo | `view.fxCornerRadius(8)` |
| `.fxBorderWidth()` | Ancho de borde fijo | `view.fxBorderWidth(1)` |
| `.fxTitleFontSize()` | Tamaño de fuente del título fijo | `button.fxTitleFontSize(14)` |

---

## 🔄 Migración desde Android

Si está familiarizado con la versión Android de AppDimens, aquí está el mapeo:

| Android | iOS |
|---------|-----|
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `AppDimens.dynamic(100).toDp()` | `AppDimens.dynamic(100).toPoints()` |
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |
| `UiModeType.PHONE` | `DeviceType.phone` |

---

## 📚 Documentación y Recursos

### 📖 Documentación Completa

- **[📘 Documentación Completa](https://appdimens-project.web.app/)** - Guías comprensivas y referencia de API
- **[🎯 Documentación Técnica](../../../iOS/DOCUMENTATION.md)** - Documentación técnica detallada
- **[📱 Guía de Uso](../../../iOS/USAGE_GUIDE.md)** - Guía práctica de uso
- **[🔧 Guía de Instalación](../../../iOS/INSTALLATION.md)** - Instrucciones de instalación
- **[📱 Ejemplos](../../../iOS/Examples/)** - Ejemplos de uso del mundo real

### 🔗 Enlaces Rápidos

- **[🚀 Guía de Instalación](#instalación)** - Comience en minutos
- **[📱 Ejemplos](#ejemplos-de-uso)** - Ejemplos de uso del mundo real
- **[🔧 Referencia de API](#referencia-de-api)** - Documentación completa de API
- **[❓ FAQ](https://appdimens-project.web.app/faq)** - Preguntas y respuestas comunes

---

## 🏗️ Resumen de Arquitectura

### Estructura de la Biblioteca iOS

| Módulo | Propósito | Dependencias | Características Clave |
|--------|-----------|--------------|---------------------|
| **AppDimens** | Funcionalidad core | Foundation, UIKit | Modelos DY/FX, caché, calificadores |
| **AppDimensUI** | Extensiones de UI | AppDimens | Extensiones SwiftUI, integración UIKit |
| **AppDimensGames** | Desarrollo de juegos | AppDimens, Metal | Integración Metal, gestión de viewport, SIMD |

### Características de Rendimiento

| Característica | Overhead de Runtime | Uso de Memoria | Tiempo de Cálculo | Estrategia de Caché |
|----------------|-------------------|----------------|-------------------|-------------------|
| **Dynamic/Fixed** | ~0.001ms | ~50KB | Caché por configuración | Seguimiento automático de dependencias |
| **Unidades Físicas** | ~0.002ms | ~10KB | Bajo demanda | Inicialización perezosa |
| **Games (Metal)** | ~0.0005ms | ~100KB | Caché con SIMD | Implementación Metal nativa |
| **Extensiones SwiftUI** | Cero | ~5KB | Tiempo de compilación | Extensiones estáticas |

### Soporte de Plataforma

| Plataforma | Versión Mín | SwiftUI | UIKit | Games |
|------------|-------------|---------|-------|-------|
| **iOS** | 13.0 | ✅ | ✅ | ✅ (Metal) |
| **macOS** | 10.15 | ✅ | AppKit | ✅ (Metal) |
| **tvOS** | 13.0 | ✅ | ✅ | ✅ (Metal) |
| **watchOS** | 6.0 | ✅ | ❌ | ❌ |

---

## 🤝 Contribuyendo

¡Aceptamos contribuciones! Vea nuestras [Pautas de Contribución](../../CONTRIBUTING.md) para detalles.

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

Este proyecto está licenciado bajo la Licencia Apache 2.0 - vea el archivo [LICENSE](../../LICENSE) para detalles.

---

## 👨‍💻 Autor

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 Muestre Su Apoyo

Si AppDimens iOS ha ayudado a su proyecto, considere:

- ⭐ **Hacer star** a este repositorio
- 🐦 **Compartir** en redes sociales
- 📝 **Escribir** una reseña o post de blog
- 🤝 **Contribuir** con código o documentación

---

<div align="center">
    <p><strong>Hecho con ❤️ para la comunidad de desarrollo iOS</strong></p>
    <p>AppDimens iOS - Donde el diseño responsivo se encuentra con la precisión matemática</p>
</div>