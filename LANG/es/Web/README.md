---
layout: default
title: "📐 Web"
---

> **Idiomas:** [English](../../Web/README.md) | [Português (BR)](../pt-BR/Web/README.md) | Español

# 📐 Web

<div align="center">

**Sistema Avanzado de Dimensionamiento Responsivo para Web**

[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.3-blue.svg)](https://www.typescriptlang.org/)

Basado en la biblioteca [AppDimens Android](../../Android/), trae los mismos conceptos poderosos de responsividad a la web.

</div>

---

## 🎯 Visión General

**Web** es un sistema completo de dimensionamiento responsivo para web que adapta automáticamente valores de dimensiones basándose en el tamaño de pantalla, aspect ratio, tipo de dispositivo y preferencias del usuario.

### ✨ Principales Recursos

- 🎨 **Tres Modelos de Escalado**: **Fixed (RECOMENDADO)** - logarítmico refinado y balanceado, Dynamic - proporcional agresivo para casos específicos, Fluid - clamp suave
- 📱 **Sistema de Breakpoints Inteligente**: Basado en viewport con soporte a container queries
- 🔄 **Caché Automático**: Sistema de caché con invalidación inteligente basada en dependencias
- 🎭 **Soporte a Media Queries**: Dark mode, reduced motion, hover, pointer type, etc
- 📏 **Unidades Físicas**: Conversiones precisas de MM, CM, Inch a píxeles
- ⚛️ **Integraciones**: React, Vue, Svelte, Angular con hooks/composables/services nativos
- 🎯 **Sistema de Prioridades**: Intersection > Device Mode > Size Qualifier
- 🚀 **Rendimiento**: Cálculos en caché, observers optimizados, zero reflows innecesarios
- 🔧 **TypeScript First**: Tipado completo con autocomplete
- 📦 **Zero Dependencies**: Core sin dependencias externas

---

## 📋 Requisitos Mínimos

| Requisito | Versión Mínima | Recomendado |
|-----------|----------------|-------------|
| **TypeScript** | 5.0.0 | 5.3.0+ |
| **Node.js** | 16.0.0 | 20.0.0+ |

### Navegadores Soportados

| Navegador | Versión Mínima | Observaciones |
|-----------|----------------|---------------|
| **Chrome/Edge** | 90+ | Soporte completo |
| **Firefox** | 88+ | Soporte completo |
| **Safari** | 14+ | Soporte completo |
| **Opera** | 76+ | Soporte completo |

### Integraciones Disponibles

| Framework | Versión Mínima | Estado |
|-----------|----------------|--------|
| **React** | 16.8.0+ | ✅ Completo |
| **Vue** | 3.0.0+ | ✅ Completo |
| **Svelte** | 3.0.0+ | ✅ Completo |
| **Angular** | 12.0.0+ | ✅ Completo |
| **Vanilla JS** | ES6+ | ✅ Completo |

---

## 🚀 Instalación

```bash
npm install webdimens
# o
yarn add webdimens
# o
pnpm add webdimens
```

### Importación via CDN (Vanilla JS)

```html
<script type="module">
  import { fixed, dynamic, fluid } from 'https://cdn.jsdelivr.net/npm/webdimens@1.0.8/dist/index.js';
  
  // Uso
  document.getElementById('myElement').style.padding = fixed(16).toPx();
</script>
```

---

## 📖 Uso Básico

### JavaScript/TypeScript Puro

```typescript
import { fixed, dynamic, fluid, webdimens } from 'webdimens';

// Fixed (logarítmico) - RECOMENDADO para la mayoría de elementos
const buttonPadding = fixed(16).toPx();        // "17.2px" (RECOMENDADO)
const iconSize = fixed(24).toPx();             // "25.8px" (RECOMENDADO)
const containerWidth = fixed(300).toPx();      // Crecimiento balanceado (RECOMENDADO)

// Dynamic (proporcional) - Use solo para casos específicos
const largeContainer = dynamic(800).toPx();    // Solo cuando sea necesario
const fullWidthGrid = dynamic(1200).toVw();    // Para grids de ancho completo

// Fluid (clamp) - transiciones suaves
const fluidFont = fluid(16, 24).toString();    // "clamp(16px, ...calc..., 24px)"
```

### React

{% raw %}
```tsx
import React from 'react';
import { useAppDimens } from 'webdimens/react';

function MyComponent() {
  const { fx, dy, fl, deviceInfo } = useAppDimens();
  
  return (
    <div style={{
      padding: fx(16).toPx(),           // Fixed (RECOMENDADO)
      width: fx(300).toPx(),            // Fixed (RECOMENDADO)
      fontSize: fl(14, 18).toString(),  // Fluid para tipografía
    }}>
      <h1 style={{ fontSize: fx(24).toPx() }}>
        Título Responsivo
      </h1>
    </div>
  );
}
```
{% endraw %}

### Vue

```vue
<template>
  <div :style="containerStyle">
    <h1 :style="titleStyle">Título Responsivo</h1>
  </div>
</template>

<script setup lang="ts">
import { useAppDimens } from 'webdimens/vue';

const { fx, dy, fl } = useAppDimens();

const containerStyle = {
  padding: fx(16).toPx(),
  width: fx(300).toPx(),
};

const titleStyle = {
  fontSize: fx(24).toPx(),
};
</script>
```

### Svelte

```svelte
<script lang="ts">
  import { useAppDimens } from 'webdimens/svelte';
  
  const { fx, dy, fl } = useAppDimens();
  
  $: containerPadding = fx(16).toPx();
  $: titleFontSize = fx(24).toPx();
</script>

<div style="padding: {containerPadding}">
  <h1 style="font-size: {titleFontSize}">
    Título Responsivo
  </h1>
</div>
```

### Angular

```typescript
import { Component } from '@angular/core';
import { AppDimensService } from 'webdimens/angular';

@Component({
  selector: 'app-my-component',
  template: `
    <div [style.padding]="containerPadding">
      <h1 [style.font-size]="titleFontSize">
        Título Responsivo
      </h1>
    </div>
  `
})
export class MyComponent {
  containerPadding: string;
  titleFontSize: string;
  
  constructor(private appDimens: AppDimensService) {
    this.containerPadding = this.appDimens.fx(16).toPx();
    this.titleFontSize = this.appDimens.fx(24).toPx();
  }
}
```

---

## 📖 Conceptos Principales

### 🎯 Fixed (FX) - RECOMENDADO

Escalado **logarítmico** que proporciona crecimiento suave y controlado. **Use para la mayoría de elementos de UI**.

```typescript
// Elementos de UI (RECOMENDADO)
const button = {
  padding: fixed(16).toPx(),      // ✅ Fixed
  fontSize: fixed(14).toPx(),     // ✅ Fixed
  borderRadius: fixed(8).toPx(),  // ✅ Fixed
};
```

### 🎯 Dynamic (DY) - Use Solo para Casos Específicos

Escalado **proporcional** que crece agresivamente con el tamaño de pantalla.

```typescript
// Contenedores grandes (casos específicos)
const container = {
  width: dynamic(800).toPx(),     // ⚠️ Dynamic
  maxWidth: dynamic(1200).toPx(), // ⚠️ Dynamic
};
```

### 🎯 Fluid (FL) - Transiciones Suaves

Escalado **clamp-based** ideal para tipografía.

```typescript
// Tipografía responsiva
const typography = {
  fontSize: fluid(14, 18).toString(),  // clamp(14px, calc(...), 18px)
  lineHeight: fluid(20, 28).toString(),
};
```

---

## 🔧 Configuración Avanzada

### Valores Personalizados por Dispositivo

```typescript
const responsiveWidth = fixed(100)
  .forDevice('tablet', 150)
  .forDevice('desktop', 200)
  .toPx();
```

### Breakpoints Personalizados

```typescript
import { webdimens } from 'webdimens';

webdimens.config({
  breakpoints: {
    mobile: { min: 0, max: 768 },
    tablet: { min: 769, max: 1024 },
    desktop: { min: 1025, max: Infinity },
  },
  cacheEnabled: true,
  aspectRatioEnabled: true,
});
```

---

## 📊 Rendimiento

- **⚡ Caché Inteligente**: Cálculos cacheados automáticamente
- **🚀 Overhead Mínimo**: <0.1ms por cálculo
- **💾 Uso de Memoria**: <500KB para caché completo
- **🔄 Zero Reflows**: Observadores optimizados

---

## 📚 API Completa

### Funciones Principales

- **fixed(value)**: Escalado Fixed (RECOMENDADO)
- **dynamic(value)**: Escalado Dynamic (casos específicos)
- **fluid(min, max)**: Escalado Fluid (tipografía)

### Métodos

- **.toPx()**: Convierte a píxeles (string)
- **.toRem()**: Convierte a REM
- **.toVw()**: Convierte a VW
- **.toVh()**: Convierte a VH
- **.forDevice(type, value)**: Valor customizado por dispositivo
- **.toString()**: Convierte a string CSS

---

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Por favor, lea el [CONTRIBUTING.md](../../CONTRIBUTING.md) para detalles.

## 📄 Licencia

Apache 2.0 - vea [LICENSE](../../LICENSE) para detalles.

## 📞 Soporte

- **Issues**: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
- **Discussions**: [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)

---

**Desarrollado con ❤️ por [Jean Bodenberg](https://github.com/bodenberg)**
