---
layout: default
title: "📐 AppDimens React Native"
---

> **Idiomas:** [English](../../../appdimens-react-native/README.md) | [Português (BR)](../../pt-BR/ReactNative/README.md) | Español

# 📐 AppDimens React Native

> **Dimensionamiento Inteligente y Responsivo para React Native**

Escalado matemáticamente responsivo que garantiza que su diseño de UI se adapte perfectamente a cualquier tamaño o proporción de pantalla — desde teléfonos hasta tablets, TVs y wearables.

[![Version](https://img.shields.io/badge/version-1.1.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Platform](https://img.shields.io/badge/platform-React%20Native-orange.svg)](https://reactnative.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-4.8+-blue.svg)](https://www.typescriptlang.org/)

---

## 🎯 ¿Qué es AppDimens React Native?

**AppDimens React Native** es un sistema de dimensionamiento integral que reemplaza valores de píxeles fijos por dimensiones escaladas inteligentemente basadas en las características reales de la pantalla. Mientras que las dimensiones por defecto de React Native son constantes, AppDimens las trata como valores base que escalan predeciblemente en diferentes tamaños de pantalla, densidades y proporciones.

### 🎨 Principales Beneficios

- **🎯 Consistencia Visual**: Mantenga proporciones perfectas en todos los tipos de dispositivos
- **📱 Compatibilidad Universal**: Funciona perfectamente en teléfonos, tablets, TVs y wearables
- **⚡ Rendimiento Optimizado**: Overhead mínimo en runtime con cálculos en caché
- **🔧 Integración Fácil**: Hooks React simples y soporte TypeScript
- **📐 Precisión Matemática**: Dos modelos de escalado - **Fixed (RECOMENDADO)** para la mayoría de casos & Dynamic para necesidades específicas
- **🎮 Desarrollo de Juegos**: Escalado especializado para elementos de UI de juegos
- **🚀 TypeScript**: Soporte completo TypeScript con definiciones de tipo comprensivas

---

## 📋 Requisitos Mínimos

| Requisito | Versión Mínima | Recomendado |
|-----------|----------------|-------------|
| **React Native** | 0.68.0 | 0.73.0+ |
| **React** | 16.8.0 | 18.2.0+ |
| **TypeScript** | 4.8.0+ | 5.3.0+ |
| **Node.js** | 16.0.0 | 20.0.0+ |

### Plataformas Soportadas

| Plataforma | Versión Mín. | Recursos Especiales | Soporte Nativo |
|------------|--------------|---------------------|----------------|
| **iOS** | 11.0+ | Integración Metal | Módulos nativos |
| **Android** | API 21+ | Soporte OpenGL ES | Módulos nativos |
| **Web** | Navegadores modernos | Integración CSS | APIs DOM |

---

## 🚀 Inicio Rápido

### Instalación

```bash
npm install appdimens-react-native
# o
yarn add appdimens-react-native
```

### Uso Básico

{% raw %}
```tsx
import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useAppDimens, AppDimensProvider } from 'appdimens-react-native';

function MyComponent() {
  const { fx, dy, deviceInfo } = useAppDimens();
  
  return (
    <View style={styles.container}>
      <Text style={styles.title}>App Responsivo</Text>
      <View style={styles.card} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: fx(16).toPixels(), // Padding fijo (RECOMENDADO)
  },
  title: {
    fontSize: fx(24).toPixels(), // Tamaño de fuente fijo (RECOMENDADO)
    marginBottom: fx(20).toPixels(), // Margen fijo (RECOMENDADO)
  },
  card: {
    width: fx(300).toPixels(), // Ancho fijo (RECOMENDADO)
    height: fx(200).toPixels(), // Alto fijo (RECOMENDADO)
    backgroundColor: '#007AFF',
    borderRadius: fx(12).toPixels(), // Border radius fijo (RECOMENDADO)
  },
});

function App() {
  return (
    <AppDimensProvider
      config={{
        aspectRatioEnabled: true,
        cacheEnabled: true,
      }}
    >
      <MyComponent />
    </AppDimensProvider>
  );
}

export default App;
```
{% endraw %}

---

## 📖 Conceptos Principales

### 🎯 Fixed (FX) - RECOMENDADO

Escalado **logarítmico** que proporciona crecimiento suave y controlado. **Use para la mayoría de elementos de UI**.

{% raw %}
```tsx
const { fx } = useAppDimens();

// Elementos de UI (RECOMENDADO)
<View style={{
  width: fx(100).toPixels(),      // ✅ Fixed
  height: fx(100).toPixels(),     // ✅ Fixed
  padding: fx(16).toPixels(),     // ✅ Fixed
  borderRadius: fx(8).toPixels(), // ✅ Fixed
}} />

// Texto (RECOMENDADO)
<Text style={{
  fontSize: fx(16).toPixels(),     // ✅ Fixed
  lineHeight: fx(24).toPixels(),   // ✅ Fixed
}} />
```
{% endraw %}

### 🎯 Dynamic (DY) - Use Solo para Casos Específicos

Escalado **proporcional** que crece agresivamente con el tamaño de pantalla. **Use solo para contenedores grandes o elementos que deben ocupar porcentaje fijo de pantalla**.

{% raw %}
```tsx
const { dy } = useAppDimens();

// Contenedores grandes (casos específicos)
<View style={{
  width: dy(300).toPixels(),  // ⚠️ Dynamic - use solo cuando sea necesario
  height: dy(200).toPixels(), // ⚠️ Dynamic - use solo cuando sea necesario
}} />
```
{% endraw %}

### 🎯 Fluid (FL) - Transiciones Suaves

Escalado **clamp-based** que garantiza transiciones suaves entre breakpoints. **Ideal para tipografía y layouts adaptativos**.

{% raw %}
```tsx
const { fl } = useAppDimens();

// Texto con transiciones suaves
<Text style={{
  fontSize: fl(14, { min: 12, max: 18 }).toPixels(),
}} />
```
{% endraw %}

---

## 🎮 Desarrollo de Juegos

### Escalado para UI de Juegos

{% raw %}
```tsx
import { useGameDimens } from 'appdimens-react-native';

function GameUI() {
  const { scaleForGame, deviceInfo } = useGameDimens();
  
  const buttonSize = scaleForGame({
    baseWidth: 100,
    baseHeight: 50,
    aspectRatio: 16 / 9,
  });
  
  return (
    <View style={{
      width: buttonSize.width,
      height: buttonSize.height,
    }}>
      <Text>Jugar</Text>
    </View>
  );
}
```
{% endraw %}

---

## 🔧 Configuración Avanzada

### Provider Global

{% raw %}
```tsx
import { AppDimensProvider } from 'appdimens-react-native';

function App() {
  return (
    <AppDimensProvider
      config={{
        aspectRatioEnabled: true,
        cacheEnabled: true,
        ignoreMultiWindowAdjustment: false,
        referenceWidth: 300,
        referenceAspectRatio: 16 / 9,
      }}
    >
      <TuApp />
    </AppDimensProvider>
  );
}
```
{% endraw %}

### Valores Personalizados por Dispositivo

```tsx
const { fx } = useAppDimens();

const responsiveWidth = fx(100)
  .forDevice('tablet', 150)
  .forDevice('tv', 200)
  .toPixels();
```

---

## 📊 Rendimiento

- **⚡ Caché Inteligente**: Los cálculos se cachean automáticamente
- **🚀 Overhead Mínimo**: ~0.1ms por cálculo (cacheado: ~0.001ms)
- **💾 Uso de Memoria**: <1MB para caché completo
- **🔄 Recomposición Optimizada**: Evita recálculos innecesarios

---

## 📚 API Completa

### Hooks

- **useAppDimens()**: Hook principal para escalado
- **useGameDimens()**: Hook especializado para juegos
- **useDeviceInfo()**: Información sobre el dispositivo actual

### Métodos

- **fx(value)**: Escalado Fixed (RECOMENDADO)
- **dy(value)**: Escalado Dynamic (casos específicos)
- **fl(value, options)**: Escalado Fluid (tipografía)
- **toPixels()**: Convierte a píxeles
- **toDp()**: Convierte a DP
- **forDevice(type, value)**: Valor customizado por dispositivo

---

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Por favor, lea el [CONTRIBUTING.md](../../../CONTRIBUTING.md) para detalles.

## 📄 Licencia

Apache 2.0 - vea [LICENSE](../../../LICENSE) para detalles.

## 📞 Soporte

- **Issues**: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
- **Discussions**: [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)

---

**Desarrollado con ❤️ por [Jean Bodenberg](https://github.com/bodenberg)**
