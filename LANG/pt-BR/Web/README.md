# 📐 WebDimens - AppDimens para Web

**Dimensões Responsivas Inteligentes para Aplicações Web**  
*Versão: 2.0.0*

> **Idiomas:** [English](../../../Web/README.md) | Português (BR) | [Español](../../es/Web/README.md)

---

## 🚀 Instalação

```bash
npm install webdimens@2.0.0
```

---

## ⚡ Início Rápido

### React

{% raw %}
```typescript
import {useWebDimens} from 'webdimens/react';

function MeuComponente() {
  const {balanced} = useWebDimens();
  
  return (
    <div style={{padding: balanced(16)}}>
      <h2 style={{fontSize: balanced(18)}}>Olá Mundo</h2>
    </div>
  );
}
```
{% endraw %}

### Vue

```vue
<script setup>
import {useWebDimens} from 'webdimens/vue';
const {balanced} = useWebDimens();
</script>

<template>
  <div :style="{padding: balanced(16)}">
    <h2 :style="{fontSize: balanced(18)}">Olá Mundo</h2>
  </div>
</template>
```

---

## 🎯 Estratégias

- **BALANCED** ⭐ (primária)
- **FLUID** (tipografia)
- **PERCENTAGE** (containers)
- E mais 10 estratégias

---

**NPM:** https://www.npmjs.com/package/webdimens
