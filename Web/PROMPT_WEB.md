# WebDimens - Development Prompt

**Quick Reference for AI Assistants and Developers**  
*Version: 2.0.0*

---

## Core Principles

1. **Use BALANCED ⭐ for 95% of UI** (primary)
2. **Use FLUID for typography** (bounded)
3. **Use PERCENTAGE for containers** (specific)
4. **13 strategies available**
5. **Works with all frameworks**

---

## API Quick Reference

### React

{% raw %}
```typescript
import {useWebDimens} from 'webdimens/react';

const {balanced, fluid, smart} = useWebDimens();

<div style={{padding: balanced(16)}}>
  <h2 style={{fontSize: fluid(18, 24)}}>Title</h2>
  <button style={{height: smart(48).forElement('button')}}>Click</button>
</div>
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
    <h2 :style="{fontSize: balanced(18)}">Title</h2>
  </div>
</template>
```

---

## Installation

```bash
npm install webdimens@2.0.0
```

---

## Strategy Selection

- Web apps (responsive) → BALANCED ⭐
- Typography → FLUID
- Large containers → PERCENTAGE
- Desktop apps → LOGARITHMIC

---

**Full Documentation:** [README.md](README.md)
