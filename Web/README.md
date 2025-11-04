# 📐 WebDimens - AppDimens for Web

**Smart Responsive Dimensions for Web Applications**  
*Version: 2.0.0 | Last Updated: February 2025*

> **Languages:** English | [Português (BR)](../LANG/pt-BR/Web/README.md) | [Español](../LANG/es/Web/README.md)

[![npm version](https://img.shields.io/npm/v/webdimens.svg)](https://www.npmjs.com/package/webdimens)
[![Platform](https://img.shields.io/badge/platform-Vanilla%20%7C%20React%20%7C%20Vue%20%7C%20Svelte%20%7C%20Angular-blue.svg)](https://github.com/bodenberg/appdimens)

---

## 🆕 What's New in Version 2.0

- 🎯 **13 Scaling Strategies** (up from 2)
- ⭐ **BALANCED** - Primary recommendation (hybrid linear-logarithmic)
- 🔬 **Perceptual Models** (Weber-Fechner, Stevens' Power Law)
- 📐 **Aspect Ratio Adjustment** (5 strategies with AR support)
- 🧠 **Smart Inference** - Automatic strategy selection
- ⚡ **5x Performance** - Optimized calculations
- 🌐 **Framework Adapters** - React, Vue, Svelte, Angular

---

## 🚀 Installation

```bash
# npm
npm install webdimens@2.0.0

# yarn
yarn add webdimens@2.0.0

# pnpm
pnpm add webdimens@2.0.0
```

### CDN (Vanilla JS)

```html
<script src="https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.js"></script>
```

---

## ⚡ Quick Start

### React

{% raw %}
```typescript
import {useWebDimens} from 'webdimens/react';

function MyComponent() {
  const {balanced, fluid} = useWebDimens();
  
  return (
    <div style={{padding: balanced(16)}}>
      <h2 style={{fontSize: fluid(18, 24)}}>Hello World</h2>
      <button style={{height: balanced(48)}}>Click Me</button>
    </div>
  );
}
```
{% endraw %}

### Vue

```vue
<template>
  <div :style="{padding: balanced(16)}">
    <h2 :style="{fontSize: balanced(18)}">Hello World</h2>
    <button :style="{height: balanced(48)}">Click Me</button>
  </div>
</template>

<script setup>
import {useWebDimens} from 'webdimens/vue';
const {balanced} = useWebDimens();
</script>
```

### Svelte

```svelte
<script>
  import {webDimensStore} from 'webdimens/svelte';
  $: wd = $webDimensStore;
</script>

<div style="padding: {wd.balanced(16)};">
  <h2 style="font-size: {wd.balanced(18)};">Hello World</h2>
</div>
```

### Angular

```typescript
import {WebDimensService} from 'webdimens/angular';

@Component({
  template: `
    <div [ngStyle]="{padding: padding}">
      <h2 [ngStyle]="{fontSize: fontSize}">Hello World</h2>
    </div>
  `
})
export class MyComponent {
  padding = '';
  fontSize = '';
  
  constructor(private wd: WebDimensService) {
    this.padding = wd.balanced(16);
    this.fontSize = wd.balanced(18);
  }
}
```

### Vanilla JavaScript

```html
<!DOCTYPE html>
<html>
<head>
  <script src="https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.js"></script>
</head>
<body>
  <div id="app"></div>
  
  <script type="module">
    import {balanced, fluid} from 'https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.mjs';
    
    document.getElementById('app').style.padding = balanced(16);
  </script>
</body>
</html>
```

---

## 🎯 13 Scaling Strategies

All AppDimens strategies available:
- **BALANCED** ⭐ (primary)
- **DEFAULT** (secondary)
- **PERCENTAGE**, **LOGARITHMIC**, **POWER**
- **FLUID**, **INTERPOLATED**, **DIAGONAL**, **PERIMETER**
- **FIT**, **FILL**, **AUTOSIZE**, **NONE**

---

## 📚 Documentation

- [Quick Start Guide](QUICK_START.md)
- [Testing Guide](HOWTO_TEST.md)
- [Main Documentation](../DOCS/README.md)
- [Examples](../DOCS/EXAMPLES.md)

---

**Author:** Jean Bodenberg  
**License:** Apache 2.0  
**Repository:** https://github.com/bodenberg/appdimens  
**NPM:** https://www.npmjs.com/package/webdimens
