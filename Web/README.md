---
layout: default
title: "📐 Web"
---

# 📐 Web

<div align="center">

**Sistema Avançado de Dimensionamento Responsivo para Web**

[![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](./LICENSE)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.3-blue.svg)](https://www.typescriptlang.org/)

Baseado na biblioteca [AppDimens Android](../Android/), traz os mesmos conceitos poderosos de responsividade para a web.

[English](#) | [Português](#)

</div>

---

## 🎯 Visão Geral

**Web** é um sistema completo de dimensionamento responsivo para web que adapta automaticamente valores de dimensões baseado no tamanho da tela, aspect ratio, tipo de dispositivo e preferências do usuário.

### ✨ Principais Recursos

- 🎨 **Três Modelos de Escalonamento**: **Fixed (RECOMENDADO)** - logarítmico refinado e balanceado, Dynamic - proporcional agressivo para casos específicos, Fluid - clamp suave
- 📱 **Sistema de Breakpoints Inteligente**: Baseado em viewport com suporte a container queries
- 🔄 **Cache Automático**: Sistema de cache com invalidação inteligente baseado em dependências
- 🎭 **Suporte a Media Queries**: Dark mode, reduced motion, hover, pointer type, etc
- 📏 **Unidades Físicas**: Conversões precisas de MM, CM, Inch para pixels
- ⚛️ **Integrações**: React, Vue, Svelte, Angular com hooks/composables/services nativos
- 🎯 **Sistema de Prioridades**: Intersection > Device Mode > Size Qualifier
- 🚀 **Performance**: Cálculos em cache, observers otimizados, zero reflows desnecessários
- 🔧 **TypeScript First**: Tipagem completa com autocomplete
- 📦 **Zero Dependencies**: Core sem dependências externas

---

## 📋 Requisitos Mínimos

| Requisito | Versão Mínima | Recomendado |
|-----------|---------------|-------------|
| **TypeScript** | 5.0.0 | 5.3.0+ |
| **Node.js** | 16.0.0 | 20.0.0+ |

### Navegadores Suportados

| Navegador | Versão Mínima | Observações |
|-----------|---------------|-------------|
| **Chrome/Edge** | 90+ | Suporte completo |
| **Firefox** | 88+ | Suporte completo |
| **Safari** | 14+ | Suporte completo |
| **Opera** | 76+ | Suporte completo |

### Integrações Disponíveis

| Framework | Versão Mínima | Status |
|-----------|---------------|--------|
| **React** | 16.8.0+ | ✅ Completo |
| **Vue** | 3.0.0+ | ✅ Completo |
| **Svelte** | 3.0.0+ | ✅ Completo |
| **Angular** | 12.0.0+ | ✅ Completo |
| **Vanilla JS** | ES6+ | ✅ Completo |

---

## 🚀 Instalação

```bash
npm install webdimens
# ou
yarn add webdimens
# ou
pnpm add webdimens
```

---

## 📖 Uso Básico

### JavaScript/TypeScript Puro

```typescript
import { fixed, dynamic, fluid, webdimens } from 'webdimens';

// Fixed (logarítmico) - RECOMENDADO para a maioria dos elementos
const buttonPadding = fixed(16).toPx();        // "17.2px" (RECOMENDADO)
const iconSize = fixed(24).toPx();             // "25.8px" (RECOMENDADO)
const containerWidth = fixed(300).toPx();      // Crescimento balanceado (RECOMENDADO)

// Dynamic (proporcional) - Use apenas para casos específicos
const largeContainer = dynamic(800).toPx();    // Apenas quando necessário
const fullWidthGrid = dynamic(1200).toVw();    // Para grids de largura completa

// Fluid (clamp) - transições suaves
const fluidFont = fluid(16, 24).toString();    // "clamp(16px, ...calc..., 24px)"

// Com qualificadores personalizados
const adaptiveSize = fixed(16)
  .screen('min-width', 768, 24)                // md e acima
  .screen('min-width', 1024, 32)               // lg e acima
  .toPx();
```

### React

{% raw %}
```tsx
import { 
  useWeb, 
  useViewport, 
  useBreakpoint,
  useMediaQuery 
} from 'webdimens/react';

function ResponsiveCard() {
  const dimens = useWeb();
  const breakpoint = useBreakpoint();
  const isDark = useMediaQuery('(prefers-color-scheme: dark)');

  return (
    <div style={{
      padding: dimens.fixed(16).toPx(),      // Fixed (RECOMENDADO)
      width: dimens.fixed(300).toPx(),       // Fixed (RECOMENDADO)
      fontSize: dimens.fixed(16).toPx(),     // Fixed (RECOMENDADO)
      backgroundColor: isDark ? '#1a1a1a' : '#ffffff'
    }}>
      <h2>Current Breakpoint: {breakpoint.current}</h2>
      {breakpoint.above('md') && <p>Desktop Layout</p>}
      {breakpoint.below('md') && <p>Mobile Layout</p>}
    </div>
  );
}
```
{% endraw %}

---

## 🎨 Modelos de Escalonamento

### 1. Fixed (FX) - Logarítmico ⭐ RECOMENDADO

**Filosofia**: Crescimento suave, controlado e balanceado  
**Ideal para**: A maioria dos elementos - botões, paddings, margens, ícones, fontes, containers, cards

```typescript
const button = {
  padding: fixed(16).toPx(),               // Crescimento suave
  fontSize: fixed(14).toRem(),             // Em rem
  borderRadius: fixed(8).toPx(),
  minWidth: fixed(120).toPx()
};
```

**Fórmula**:
```
Valor Final = Base × (1 + Fator de Ajuste × (Incremento Base + Ajuste AR))
```

### 2. Dynamic (DY) - Proporcional (Use Apenas para Casos Específicos)

**Filosofia**: Crescimento linear agressivo baseado em porcentagem  
**Ideal para**: Apenas casos específicos - containers muito grandes, grids de largura completa, elementos dependentes de viewport

```typescript
const layout = {
  width: dynamic(300).toPx(),              // Proporcional à tela
  height: dynamic(200).toVw(),             // Em vw
  gap: dynamic(16).toPx()
};
```

**Fórmula**:
```
Valor Final = (Base / Largura Referência) × Dimensão Atual da Tela
```

### 3. Fluid (FL) - Clamp

**Filosofia**: Transição suave entre valores min e max  
**Ideal para**: Typography, spacing fluido

```typescript
const typography = {
  fontSize: fluid(16, 24).toString(),      // clamp(16px, calc, 24px)
  lineHeight: fluid(1.5, 1.8).toString(),
  letterSpacing: fluid(0, 0.05).toString()
};

// Com breakpoints customizados
const customFluid = fluid(12, 32, 'sm', 'xl').toString();
```

**Fórmula CSS**:
```css
clamp(min, preferred, max)
```

---

## 🎯 Sistema de Prioridades

Web usa um sistema de prioridades similar ao Android para resolver valores customizados:

### Prioridade 1: Intersection (Device Mode + Size Qualifier)

```typescript
const value = fixed(16)
  .screen('mobile', 'min-width', 768, 20)  // Mobile E 768px+ = 20
  .toPx();
```

### Prioridade 2: Device Mode

```typescript
const value = fixed(16)
  .screen('mobile', 12)                    // Mobile = 12
  .screen('tablet', 18)                    // Tablet = 18
  .screen('desktop', 24)                   // Desktop = 24
  .toPx();
```

### Prioridade 3: Size Qualifier

```typescript
const value = fixed(16)
  .screen('min-width', 640, 20)            // 640px+ = 20
  .screen('min-width', 1024, 24)           // 1024px+ = 24
  .toPx();
```

---

## 📱 Breakpoints

### Breakpoints Padrão

```typescript
{
  xs: 0,
  sm: 640,
  md: 768,
  lg: 1024,
  xl: 1280,
  '2xl': 1536,
  '3xl': 1920,
  '4xl': 2560
}
```

### Uso

```typescript
const bp = webdimens.breakpoints;

// Verificações
console.log(bp.current);           // "lg"
console.log(bp.is('lg'));           // true
console.log(bp.above('md'));        // true
console.log(bp.below('xl'));        // true
console.log(bp.between('md', 'xl'));// true

// Gerar media query
const query = bp.mediaQuery('lg');  // "(min-width: 1024px)"

// Fluid entre breakpoints
const fluidSize = bp.fluid(16, 32, 'sm', 'xl');
// "clamp(16px, 1rem + 0.5vw, 32px)"
```

### Breakpoints Customizados

```typescript
import { Web } from 'webdimens';

const dimens = new Web({
  breakpoints: {
    mobile: 0,
    tablet: 768,
    desktop: 1024,
    wide: 1920
  }
});
```

---

## 🔧 Recursos Avançados

### 📏 Unidades Físicas

```typescript
const physical = webdimens.physical;

// Conversões
const mmSize = physical.mm(10);        // "37.79px" @ 96 DPI
const cmSize = physical.cm(2);         // "75.59px"
const inchSize = physical.inch(1);     // "96px"

// DPI automático
const dpi = physical.getDPI();         // 96, 144, etc

// Radius e circumferência
const radius = physical.radius(20, 'mm');
const circ = physical.circumference(20, 'mm');
```

### 🎭 Media Queries

```typescript
// Verificar media queries
const isDark = webdimens.matchesMedia('(prefers-color-scheme: dark)');
const hasHover = webdimens.matchesMedia('(hover: hover)');

// Obter todas as features
const features = webdimens.getMediaFeatures();
// {
//   prefers: 'dark',
//   orientation: 'landscape',
//   hover: 'hover',
//   pointer: 'fine',
//   displayMode: 'browser'
// }

// Subscribe para mudanças
webdimens.mediaQuery.observe('(orientation: portrait)', (matches) => {
  console.log('Portrait mode:', matches);
});
```

### 📦 Cache

```typescript
// Estatísticas do cache
const stats = webdimens.getCacheStats();
console.log(stats);
// {
//   totalEntries: 42,
//   cacheHits: 156,
//   cacheMisses: 18,
//   hitRate: 0.87,
//   avgCalculationTime: 0.3
// }

// Limpar cache
webdimens.clearCache();

// Cache individual por instância
const value = fixed(16)
  .cache(false)  // Desabilita cache para esta instância
  .toPx();
```

### 🎨 CSS Variables (Auto-update)

```typescript
// Habilitar CSS variables que atualizam automaticamente
webdimens.enableCSSVars();

// Agora você pode usar no CSS:
```

```css
.element {
  /* Valores fixed pré-calculados */
  padding: var(--wd-fx-16);
  margin: var(--wd-fx-24);
  
  /* Valores dynamic */
  width: var(--wd-dy-300);
  
  /* Dimensões do viewport */
  max-width: var(--wd-vw);
  height: var(--wd-vh);
  
  /* Info do breakpoint */
  content: var(--wd-breakpoint);  /* "lg" */
}
```

### 🛡️ Safe Area (Notches Mobile)

```typescript
const header = {
  // Respeita notch/safe area
  paddingTop: webdimens.safeArea('top', 16),
  // Result: "max(16px, env(safe-area-inset-top))"
  
  paddingBottom: webdimens.safeArea('bottom', 16),
  paddingLeft: webdimens.safeArea('left', 16),
  paddingRight: webdimens.safeArea('right', 16)
};
```

### 🎯 Porcentagens

```typescript
// Porcentagem do viewport
const halfWidth = webdimens.percent(50, 'width');   // 50% da largura
const thirdHeight = webdimens.percent(33.33, 'height'); // 33.33% da altura
```

### 🎨 Gerar Design Tokens

```typescript
// Escala de espaçamento (Tailwind-like)
const spacing = webdimens.generateSpacingScale(4);
// { 0: "0px", 1: "4.3px", 2: "8.6px", 4: "17.2px", ... }

// Escala de fontes
const fonts = webdimens.generateFontScale();
// { xs: "12.9px", sm: "15px", base: "17.2px", lg: "19.4px", ... }

// Usar com Tailwind CSS
module.exports = {
  theme: {
    extend: {
      spacing: webdimens.generateSpacingScale(4),
      fontSize: webdimens.generateFontScale()
    }
  }
}
```

---

## ⚛️ Integração React

### Hooks Disponíveis

```typescript
import {
  useWeb,           // Acesso principal
  useViewport,            // Dimensões do viewport
  useBreakpoint,          // Breakpoint atual
  useMediaQuery,          // Media query específica
  useMediaFeatures,       // Todas as features
  useResponsiveValue,     // Valor baseado em breakpoint
  useDimension,           // Dimensão reativa
  useOrientation,         // Orientação
  useAspectRatio          // Aspect ratio
} from 'webdimens/react';
```

{% raw %}
### Exemplo Completo

```tsx
import { useWeb, useBreakpoint, useMediaQuery } from 'webdimens/react';

function ResponsiveComponent() {
  const dimens = useWeb();
  const breakpoint = useBreakpoint();
  const isDark = useMediaQuery('(prefers-color-scheme: dark)');

  const columns = useResponsiveValue({
    xs: 1,
    sm: 2,
    md: 3,
    lg: 4
  });

  return (
    <div style={{
      display: 'grid',
      gridTemplateColumns: 'repeat(' + columns + ', 1fr)',
      gap: dimens.fixed(16).toPx(),
      padding: dimens.safeArea('top', 16),
      backgroundColor: isDark ? '#1a1a1a' : '#ffffff'
    }}>
      {/* Conteúdo */}
    </div>
  );
}
```
{% endraw %}

---

## 🔬 Performance

### Otimizações

- ✅ **Cache Inteligente**: Cálculos são cacheados com invalidação automática
- ✅ **Observers Eficientes**: ResizeObserver moderno em vez de eventos resize
- ✅ **Debouncing**: Atualizações debounced para evitar cálculos excessivos
- ✅ **Lazy Evaluation**: Valores calculados apenas quando necessário
- ✅ **Zero Reflows**: Não causa reflows desnecessários no DOM

### Benchmarks

| Operação | Tempo | Cache Hit |
|----------|-------|-----------|
| Fixed calculation | ~0.05ms | ~0.001ms |
| Dynamic calculation | ~0.03ms | ~0.001ms |
| Breakpoint check | ~0.01ms | Instant |
| Media query check | ~0.02ms | Instant |

---

## 📚 Comparação com Alternativas

| Feature | Web | CSS puro | Tailwind | styled-system |
|---------|-----------|----------|----------|---------------|
| Três modelos de escala | ✅ | ❌ | ❌ | ❌ |
| Aspect ratio aware | ✅ | ⚠️ Manual | ❌ | ❌ |
| Device detection | ✅ | ❌ | ❌ | ❌ |
| Sistema de prioridades | ✅ | ❌ | ⚠️ Limitado | ⚠️ Limitado |
| Cache automático | ✅ | N/A | ❌ | ❌ |
| Unidades físicas | ✅ | ❌ | ❌ | ❌ |
| TypeScript | ✅ | N/A | ✅ | ✅ |
| Zero runtime | ❌ | ✅ | ⚠️ JIT | ❌ |
| Framework agnostic | ✅ | ✅ | ✅ | ⚠️ Limitado |

---

## 🎓 Casos de Uso

### 1. Design System

```typescript
// tokens.ts
export const spacing = webdimens.generateSpacingScale(4);
export const typography = webdimens.generateFontScale();

export const colors = {
  // ...
};

export const components = {
  button: {
    padding: fixed(16).screen('min-width', 768, 20).toPx(),
    fontSize: fixed(14).toRem(),
    borderRadius: fixed(8).toPx()
  }
};
```

### 2. Responsive Layout System

```typescript
const grid = {
  container: {
    width: dynamic(1200).screen('min-width', 1440, 1400).toPx(),
    padding: fixed(16).screen('min-width', 768, 24).toPx()
  },
  column: (span: number) => ({
    width: ((span / 12) * 100) + '%',
    padding: fixed(8).toPx()
  })
};
```

### 3. Acessibilidade

```typescript
const features = webdimens.getMediaFeatures();

const button = {
  // Maior touch target para coarse pointer (mobile)
  minWidth: fixed(features.pointer === 'coarse' ? 44 : 32).toPx(),
  minHeight: fixed(features.pointer === 'coarse' ? 44 : 32).toPx(),
  
  // Sem transições se reduced motion
  transition: features.prefers === 'reduce-motion' ? 'none' : 'all 0.3s',
  
  // Contrast ajustado
  backgroundColor: features.prefers === 'high-contrast' ? '#000' : '#007bff'
};
```

---

## 📖 API Reference

### Web Class

```typescript
class Web {
  constructor(config?: WebConfig);
  
  // Builders
  fixed(value: number): Fixed;
  fx(value: number): Fixed;
  dynamic(value: number): Dynamic;
  dy(value: number): Dynamic;
  fluid(min: number, max: number, minBp?: string, maxBp?: string): Fluid;
  fl(min: number, max: number, minBp?: string, maxBp?: string): Fluid;
  
  // Utilities
  percent(percentage: number, type?: 'width' | 'height'): number;
  safeArea(side: 'top' | 'right' | 'bottom' | 'left', fallback?: number): string;
  
  // Accessors
  get physical(): PhysicalUnits;
  get breakpoints(): BreakpointManager;
  get cache(): WebCache;
  get viewport(): ViewportObserver;
  get mediaQuery(): MediaQueryObserver;
  
  // Methods
  enableCSSVars(): void;
  disableCSSVars(): void;
  generateSpacingScale(base?: number): Record<string, string>;
  generateFontScale(): Record<string, string>;
  clearCache(): void;
  getCacheStats(): CacheStats;
  destroy(): void;
}
```

### Fixed Builder

```typescript
class Fixed {
  type(type: ScreenType): this;
  aspectRatio(enable?: boolean, sensitivity?: number): this;
  screen(...args): this;
  multiView(ignore?: boolean): this;
  cache(enable?: boolean): this;
  
  // Output
  px(): number;
  toPx(): string;
  rem(): number;
  toRem(): string;
  em(): number;
  toEm(): string;
}
```

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor, leia [CONTRIBUTING.md](../CONTRIBUTING.md) para detalhes.

---

## 📄 Licença

Apache License 2.0 - veja [LICENSE](../LICENSE) para detalhes.

---

## 👨‍💻 Autor

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🙏 Agradecimentos

Baseado nos conceitos do [AppDimens Android](../Android/README.md), adaptado e expandido para a web com recursos adicionais.

---

<div align="center">
  <strong>Web - Dimensionamento Responsivo Inteligente para Web</strong>
</div>

