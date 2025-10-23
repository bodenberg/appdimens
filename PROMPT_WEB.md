# AI Prompt for Web Library

> Languages: [Português (BR)](LANG/pt-BR/PROMPT_WEB.md) | [Español](LANG/es/PROMPT_WEB.md) | [हिन्दी](LANG/hi/PROMPT_WEB.md) | [Русский](LANG/ru/PROMPT_WEB.md) | [中文](LANG/zh/PROMPT_WEB.md) | [日本語](LANG/ja/PROMPT_WEB.md)

You are an expert web developer AI. Your task is to understand and effectively use the Web library, a sophisticated dimension management system for web applications. This guide will provide you with all the necessary information about the library's architecture, functionalities, and use cases.

## 1. Installation / Setup

Github: [AppDimens Repository](https://github.com/Bodenberg/AppDimens)

```bash
npm install webdimens
# or
yarn add webdimens
# or
pnpm add webdimens
```

## 2. Minimum Requirements

| Requirement | Minimum Version | Recommended |
|------------|-----------------|-------------|
| **TypeScript** | 5.0.0 | 5.3.0+ |
| **Node.js** | 16.0.0 | 20.0.0+ |

### Supported Browsers

| Browser | Minimum Version | Notes |
|---------|-----------------|-------|
| **Chrome/Edge** | 90+ | Full support |
| **Firefox** | 88+ | Full support |
| **Safari** | 14+ | Full support |
| **Opera** | 76+ | Full support |

### Framework Integrations

| Framework | Minimum Version | Status |
|-----------|-----------------|--------|
| **React** | 16.8.0+ | ✅ Complete |
| **Vue** | 3.0.0+ | ✅ Complete |
| **Svelte** | 3.0.0+ | ⏳ Planned |
| **Angular** | 12.0.0+ | ⏳ Planned |

## 3. Core Concepts

### 3.1. What is Web?

Web is an advanced responsive dimensioning system for web applications that adapts automatically based on screen size, aspect ratio, device type, and user preferences. It brings the powerful concepts from AppDimens Android to the web with additional web-specific features.

### 3.2. Three Scaling Models

| Model | Philosophy | Ideal Use Case | Growth Pattern |
|-------|------------|----------------|----------------|
| **Fixed (FX)** | Logarithmic scaling | Buttons, paddings, margins, icons | Smooth, controlled growth |
| **Dynamic (DY)** | Proportional scaling | Containers, grids, fluid fonts | Linear, percentage-based |
| **Fluid (FL)** | Clamp-based transition | Typography, fluid spacing | Smooth min-max transitions |

## 4. How to Use Web

### 4.1. Pure JavaScript/TypeScript

```typescript
import { fixed, dynamic, fluid, webdimens } from 'webdimens';

// Fixed (logarithmic) - for UI elements
const buttonPadding = fixed(16).toPx();      // "17.2px"
const iconSize = fixed(24).toPx();           // "25.8px"

// Dynamic (proportional) - for layouts
const containerWidth = dynamic(300).toPx();  // Proportional to screen
const cardHeight = dynamic(200).toVw();      // "15vw"

// Fluid (clamp) - smooth transitions
const fluidFont = fluid(16, 24).toString();  // "clamp(16px, ...calc..., 24px)"
```

### 4.2. With Custom Qualifiers

```typescript
const adaptiveSize = fixed(16)
  .screen('min-width', 768, 24)    // md and above
  .screen('min-width', 1024, 32)   // lg and above
  .toPx();
```

### 4.3. React Integration

```tsx
import { 
  useWebDimens, 
  useViewport, 
  useBreakpoint,
  useMediaQuery 
} from 'webdimens/react';

function ResponsiveCard() {
  const dimens = useWebDimens();
  const breakpoint = useBreakpoint();
  const isDark = useMediaQuery('(prefers-color-scheme: dark)');

  return (
    <div style={{
      padding: dimens.fixed(16).toPx(),
      width: dimens.dynamic(300).toPx(),
      fontSize: dimens.fluid(14, 18).toString(),
      backgroundColor: isDark ? '#1a1a1a' : '#ffffff'
    }}>
      <h2>Current Breakpoint: {breakpoint.current}</h2>
      {breakpoint.above('md') && <p>Desktop Layout</p>}
      {breakpoint.below('md') && <p>Mobile Layout</p>}
    </div>
  );
}
```

### 4.4. Vue Integration

```vue
<script setup>
import { useWebDimens } from 'webdimens/vue';

const dimens = useWebDimens();
</script>

<template>
  <div :style="{
    padding: dimens.fixed(16).toPx(),
    width: dimens.dynamic(300).toPx(),
  }">
    Responsive Content
  </div>
</template>
```

## 5. Advanced Features

### 5.1. Priority System

Web uses a three-tier priority system similar to Android:

**Priority 1: Intersection** (Device Mode + Size Qualifier)
```typescript
const value = fixed(16)
  .screen('mobile', 'min-width', 768, 20)  // Mobile AND 768px+ = 20
  .toPx();
```

**Priority 2: Device Mode**
```typescript
const value = fixed(16)
  .screen('mobile', 12)
  .screen('tablet', 18)
  .screen('desktop', 24)
  .toPx();
```

**Priority 3: Size Qualifier**
```typescript
const value = fixed(16)
  .screen('min-width', 640, 20)
  .screen('min-width', 1024, 24)
  .toPx();
```

### 5.2. Breakpoints

```typescript
const bp = webdimens.breakpoints;

// Check current breakpoint
console.log(bp.current);           // "lg"
console.log(bp.is('lg'));          // true
console.log(bp.above('md'));       // true
console.log(bp.below('xl'));       // true

// Generate media query
const query = bp.mediaQuery('lg'); // "(min-width: 1024px)"

// Fluid between breakpoints
const fluidSize = bp.fluid(16, 32, 'sm', 'xl');
// "clamp(16px, 1rem + 0.5vw, 32px)"
```

### 5.3. Physical Units

```typescript
const physical = webdimens.physical;

const mmSize = physical.mm(10);      // "37.79px" @ 96 DPI
const cmSize = physical.cm(2);       // "75.59px"
const inchSize = physical.inch(1);   // "96px"
```

### 5.4. Media Queries

```typescript
// Check media queries
const isDark = webdimens.matchesMedia('(prefers-color-scheme: dark)');
const hasHover = webdimens.matchesMedia('(hover: hover)');

// Get all features
const features = webdimens.getMediaFeatures();
// {
//   prefers: 'dark',
//   orientation: 'landscape',
//   hover: 'hover',
//   pointer: 'fine',
// }

// Subscribe to changes
webdimens.mediaQuery.observe('(orientation: portrait)', (matches) => {
  console.log('Portrait mode:', matches);
});
```

### 5.5. CSS Variables (Auto-update)

```typescript
// Enable CSS variables that update automatically
webdimens.enableCSSVars();
```

```css
.element {
  /* Pre-calculated fixed values */
  padding: var(--wd-fx-16);
  margin: var(--wd-fx-24);
  
  /* Dynamic values */
  width: var(--wd-dy-300);
  
  /* Viewport dimensions */
  max-width: var(--wd-vw);
  height: var(--wd-vh);
  
  /* Current breakpoint */
  content: var(--wd-breakpoint);  /* "lg" */
}
```

### 5.6. Safe Area (Mobile Notches)

```typescript
const header = {
  // Respects notch/safe area
  paddingTop: webdimens.safeArea('top', 16),
  // Result: "max(16px, env(safe-area-inset-top))"
};
```

### 5.7. Generate Design Tokens

```typescript
// Spacing scale (Tailwind-like)
const spacing = webdimens.generateSpacingScale(4);
// { 0: "0px", 1: "4.3px", 2: "8.6px", 4: "17.2px", ... }

// Font scale
const fonts = webdimens.generateFontScale();
// { xs: "12.9px", sm: "15px", base: "17.2px", lg: "19.4px", ... }
```

## 6. Performance

### Optimizations

- ✅ **Smart Caching**: Calculations are cached with automatic invalidation
- ✅ **Efficient Observers**: Modern ResizeObserver instead of resize events
- ✅ **Debouncing**: Debounced updates to avoid excessive calculations
- ✅ **Lazy Evaluation**: Values calculated only when needed
- ✅ **Zero Reflows**: Does not cause unnecessary DOM reflows

### Benchmarks

| Operation | Time | Cache Hit |
|-----------|------|-----------|
| Fixed calculation | ~0.05ms | ~0.001ms |
| Dynamic calculation | ~0.03ms | ~0.001ms |
| Breakpoint check | ~0.01ms | Instant |
| Media query check | ~0.02ms | Instant |

## 7. Best Practices

1. **Use Fixed for UI Elements**: Buttons, paddings, fonts, icons
2. **Use Dynamic for Layout**: Container widths, spacers, grid items
3. **Use Fluid for Typography**: Font sizes, line heights
4. **Enable Caching**: For better performance
5. **Use CSS Variables**: For better performance and DX
6. **Leverage Breakpoints**: Use the breakpoint system for responsive design
7. **Monitor Performance**: Use built-in performance monitoring

## 8. Common Patterns

### Design System

```typescript
// tokens.ts
export const spacing = webdimens.generateSpacingScale(4);
export const typography = webdimens.generateFontScale();

export const components = {
  button: {
    padding: fixed(16).screen('min-width', 768, 20).toPx(),
    fontSize: fixed(14).toRem(),
    borderRadius: fixed(8).toPx()
  }
};
```

### Responsive Layout System

```typescript
const grid = {
  container: {
    width: dynamic(1200).screen('min-width', 1440, 1400).toPx(),
    padding: fixed(16).screen('min-width', 768, 24).toPx()
  },
  column: (span: number) => ({
    width: `${(span / 12) * 100}%`,
    padding: fixed(8).toPx()
  })
};
```

### Accessibility

```typescript
const features = webdimens.getMediaFeatures();

const button = {
  // Larger touch target for coarse pointer (mobile)
  minWidth: fixed(features.pointer === 'coarse' ? 44 : 32).toPx(),
  minHeight: fixed(features.pointer === 'coarse' ? 44 : 32).toPx(),
  
  // No transitions if reduced motion
  transition: features.prefers === 'reduce-motion' ? 'none' : 'all 0.3s',
};
```

## 9. Debugging

```typescript
// Cache statistics
const stats = webdimens.getCacheStats();
console.log(stats);
// {
//   totalEntries: 42,
//   cacheHits: 156,
//   cacheMisses: 18,
//   hitRate: 0.87
// }

// Clear cache
webdimens.clearCache();
```

By following this guide, you should be able to effectively use the Web library to create responsive and visually consistent web applications across all browsers and devices.

