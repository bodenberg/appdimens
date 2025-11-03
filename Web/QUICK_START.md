# WebDimens - Quick Start

**Version: 2.0.0**

---

## Installation

```bash
npm install webdimens@2.0.0
```

---

## React

```typescript
import {useWebDimens} from 'webdimens/react';

function App() {
  const {balanced} = useWebDimens();
  
  return (
    <div style={{padding: balanced(16)}}>
      <h1 style={{fontSize: balanced(24)}}>Hello</h1>
    </div>
  );
}
```

---

## Vue

```vue
<template>
  <div :style="{padding: balanced(16)}">
    <h1 :style="{fontSize: balanced(24)}">Hello</h1>
  </div>
</template>

<script setup>
import {useWebDimens} from 'webdimens/vue';
const {balanced} = useWebDimens();
</script>
```

---

## Vanilla JS

```html
<script type="module">
  import {balanced} from 'https://cdn.jsdelivr.net/npm/webdimens@2.0.0/dist/index.mjs';
  
  document.body.style.padding = balanced(16);
</script>
```

---

**Full Guide:** [README.md](README.md)
