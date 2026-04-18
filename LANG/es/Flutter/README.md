---
layout: default
title: "AppDimens Flutter"
---

> **Idiomas:** [English](../../../appdimens-flutter/README.md) | [Português (BR)](../../pt-BR/Flutter/README.md) | Español

# AppDimens Flutter

[![pub package](https://img.shields.io/pub/v/appdimens.svg)](https://pub.dev/packages/appdimens)
[![pub points](https://img.shields.io/pub/points/appdimens?logo=dart)](https://pub.dev/packages/appdimens/score)
[![popularity](https://img.shields.io/pub/popularity/appdimens?logo=dart)](https://pub.dev/packages/appdimens/score)
[![likes](https://img.shields.io/pub/likes/appdimens?logo=dart)](https://pub.dev/packages/appdimens/score)

**AppDimens Flutter** es una biblioteca de dimensionamiento responsivo que ajusta automáticamente píxeles lógicos, píxeles escalables y unidades físicas de forma responsiva y matemáticamente refinada, garantizando consistencia de layout en cualquier tamaño o proporción de pantalla.

## 🚀 Características Principales

- **Dimensionamiento Fijo (FX)** ⭐ **RECOMENDADO**: Escalado logarítmico refinado y balanceado para la mayoría de elementos de UI - botones, paddings, márgenes, iconos, fuentes, contenedores, tarjetas
- **Dimensionamiento Dinámico (DY)**: Escalado proporcional agresivo solo para casos específicos - contenedores muy grandes, grids de ancho completo, elementos dependientes de viewport
- **Dimensionamiento Fluido (FL)** 🌊 **NUEVO v1.0.10**: Interpolación suave entre valores mín/máx basado en breakpoints de ancho - ideal para tipografía y espaciado controlado
- **Base Orientation** 🆕 **v1.2.0**: Auto-adaptación a rotación de pantalla - diseña para una orientación, mantiene proporciones al rotar automáticamente
- **Unidades Físicas**: Conversión de medidas reales (mm, cm, pulgadas) a píxeles de pantalla
- **Calificadores Condicionales**: Valores personalizados basados en modo de UI, tipo de dispositivo y calificadores de pantalla
- **Caché Inteligente**: Sistema de caché optimizado para rendimiento con control global y por instancia
- **Ajuste Multi-Window**: Opción para ignorar ajustes cuando la app está en modo multi-ventana
- **Extensiones Convenientes**: Extensiones para widgets Flutter para uso simplificado

## 📋 Requisitos Mínimos

| Requisito | Versión Mínima | Recomendado |
|-----------|---------------|-------------|
| **Dart SDK** | 3.0.0 | 3.5.0+ |
| **Flutter SDK** | 3.10.0 | 3.24.0+ |

### Plataformas Soportadas

| Plataforma | Soporte | Observaciones |
|------------|---------|---------------|
| **Android** | ✅ | API 21+ |
| **iOS** | ✅ | iOS 12.0+ |
| **Web** | ✅ | Todos los navegadores modernos |
| **Windows** | ✅ | Windows 10+ |
| **macOS** | ✅ | macOS 10.14+ |
| **Linux** | ✅ | |

---

## 📦 Instalación

Agregue a su `pubspec.yaml`:

```yaml
dependencies:
  appdimens: ^1.1.0
```

Ejecute:

```bash
flutter pub get
```

## 🎯 Uso Básico

### 1. Configuración Inicial

```dart
import 'package:appdimens/appdimens.dart';

void main() {
  runApp(
    AppDimensApp(
      config: const AppDimensConfig(
        aspectRatioEnabled: true,
        cacheEnabled: true,
        ignoreMultiWindowAdjustment: false,
      ),
      child: MyApp(),
    ),
  );
}
```

### 2. Dimensionamiento Fijo (FX) ⭐ RECOMENDADO

```dart
// Uso básico (RECOMENDADO para la mayoría de casos)
Container(
  width: 100.fx.calculate(context),
  height: 100.fx.calculate(context),
  decoration: BoxDecoration(
    borderRadius: BorderRadius.circular(16.fx.calculate(context)),
  ),
)

// Con valores personalizados
Container(
  width: AppDimens.fixed(150)
      .deviceType(DeviceType.tablet, 200)
      .deviceType(DeviceType.tv, 300)
      .calculate(context),
  height: AppDimens.fixed(80)
      .uiMode(UiModeType.carPlay, 120)
      .calculate(context),
)
```

### 3. Dimensionamiento Dinámico (DY) - Use Solo para Casos Específicos

```dart
// Nota: Este ejemplo muestra Dynamic en acción
// Use este enfoque solo cuando necesite escalado proporcional agresivo
// Para la mayoría de casos, Fixed (FX) es RECOMENDADO

// Uso básico de Dynamic (solo cuando sea necesario)
Container(
  width: 200.dy.calculate(context),  // Dynamic - proporcional a pantalla
  height: 100.dy.calculate(context), // Dynamic - proporcional a pantalla
)

// Con valores personalizados
Container(
  width: AppDimens.dynamic(300)      // Dynamic para contenedores grandes
      .deviceType(DeviceType.tablet, 400)
      .deviceType(DeviceType.tv, 500)
      .calculate(context),
)
```

### 4. Unidades Físicas

```dart
// Conversión de unidades físicas
Container(
  width: AppDimensPhysicalUnits.mmToPixels(50, context),
  height: AppDimensPhysicalUnits.mmToPixels(25, context),
)

// Tamaño de fuente optimizado
Text(
  'Hello World',
  style: TextStyle(
    fontSize: AppDimensPhysicalUnits.calculateOptimalFontSize(
      5, UnitType.mm, context
    ),
  ),
)
```

### 5. Extensiones para Widgets

```dart
// Padding responsivo
Text('Hello')
  .fxPadding(16, context)
  .dyMargin(8, context)
  .fxBorderRadius(12, context);

// Estilo de texto responsivo
Text(
  'Hello World',
  style: TextStyle()
    .fxFontSize(16, context)
    .dyFontSize(18, context),
);
```

## 🎨 Ejemplos Avanzados

### Grid Responsivo

{% raw %}
```dart
GridView.builder(
  gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
    crossAxisCount: 3,
    crossAxisSpacing: 8.fx.calculate(context),
    mainAxisSpacing: 8.fx.calculate(context),
  ),
  itemCount: 9,
  itemBuilder: (context, index) {
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(4.fx.calculate(context)),
      ),
      child: Center(
        child: Text(
          '${index + 1}',
          style: TextStyle(
            fontSize: 20.fx.calculate(context),
          ),
        ),
      ),
    );
  },
)
```
{% endraw %}

### Botón Responsivo

```dart
ElevatedButton(
  onPressed: () {},
  style: ElevatedButton.styleFrom(
    padding: EdgeInsets.all(16.fx.calculate(context)),
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(8.fx.calculate(context)),
    ),
  ),
  child: Text(
    'Button',
    style: TextStyle(
      fontSize: 16.fx.calculate(context),
    ),
  ),
)
```

## 🔧 Configuración Avanzada

### Configuración Global

```dart
// Configuración global
AppDimens.setGlobalAspectRatio(true);
AppDimens.setGlobalCache(true);
AppDimens.setGlobalIgnoreMultiWindowAdjustment(false);
```

### Caché Personalizado

```dart
// Control de caché por instancia
final dimension = AppDimens.fixed(100)
    .cache(true)  // Activar caché
    .calculate(context);

// Limpiar caché
AppDimens.fixed(100).clearCache();
```

## 📱 Tipos de Dispositivo Soportados

- **Phone**: Smartphones
- **Tablet**: Tablets
- **Watch**: Smartwatches
- **TV**: Smart TVs
- **CarPlay**: Displays automotrices
- **Desktop**: Computadoras de escritorio
- **Foldable**: Dispositivos plegables
- **Unknown**: Dispositivos no identificados

## 📚 API Reference

### Clases Principales

- **AppDimens**: Clase principal para crear dimensionamientos
- **AppDimensFixed**: Constructor para dimensionamiento fijo
- **AppDimensDynamic**: Constructor para dimensionamiento dinámico
- **AppDimensPhysicalUnits**: Utilidades para unidades físicas
- **AppDimensProvider**: Provider para configuración global

### Extensiones

- **AppDimensDoubleExtension**: Extensiones para valores double
- **AppDimensIntExtension**: Extensiones para valores int
- **AppDimensWidgetExtension**: Extensiones para widgets
- **AppDimensTextStyleExtension**: Extensiones para estilos de texto
- **AppDimensContainerExtension**: Extensiones para contenedores

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Por favor, lea el [CONTRIBUTING.md](../../../CONTRIBUTING.md) para detalles sobre cómo contribuir.

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia Apache 2.0 - vea el archivo [LICENSE](../../../LICENSE) para detalles.

## 📞 Soporte

- **Issues**: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
- **Discussions**: [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)

---

**Desarrollado con ❤️ por [Jean Bodenberg](https://github.com/bodenberg)**
