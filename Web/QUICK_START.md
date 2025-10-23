# 🚀 Web - Início Rápido

## 📦 Instalação

```bash
npm install webdimens
# ou
yarn add webdimens
# ou
pnpm add webdimens
```

## 💡 Uso Básico (5 minutos)

### 1. JavaScript/TypeScript Puro

```typescript
import { fixed, dynamic, fluid } from 'webdimens';

// Fixed - para elementos de UI
const buttonPadding = fixed(16).toPx();        // "17.2px"
const iconSize = fixed(24).toPx();             // "25.8px"

// Dynamic - para layouts
const containerWidth = dynamic(300).toPx();    // Proporcional

// Fluid - transições suaves
const fluidFont = fluid(16, 24).toString();    // "clamp(...)"
```

### 2. HTML Puro

```html
<!DOCTYPE html>
<html>
<head>
    <style>
        .button {
            padding: var(--wd-fx-16);
            font-size: var(--wd-fx-14);
        }
    </style>
</head>
<body>
    <button class="button">Click me</button>

    <script type="module">
        import { webdimens } from 'webdimens';
        
        // Ativar CSS Variables
        webdimens.enableCSSVars();
        
        // Ou usar diretamente
        const button = document.querySelector('.button');
        button.style.padding = webdimens.fixed(16).toPx();
    </script>
</body>
</html>
```

### 3. React

```tsx
import { useWeb, useBreakpoint } from 'webdimens/react';

function MyComponent() {
  const dimens = useWeb();
  const breakpoint = useBreakpoint();

  return (
    <div style={{
      padding: dimens.fixed(16).toPx(),
      width: breakpoint.above('md') ? '80%' : '100%'
    }}>
      Content
    </div>
  );
}
```

## 🎯 Exemplos Rápidos

### Botão Responsivo
```typescript
const button = {
  padding: fixed(16)
    .screen('min-width', 768, 24)  // md e acima
    .toPx(),
  fontSize: fixed(14).toRem(),
  borderRadius: fixed(8).toPx()
};
```

### Container Adaptativo
```typescript
const container = {
  width: dynamic(1200)
    .screen('min-width', 1440, 1400)
    .toPx(),
  padding: fixed(24).toPx()
};
```

### Typography Fluida
```typescript
const h1 = {
  fontSize: fluid(24, 48, 'sm', 'xl').toString(),
  // "clamp(24px, calc(...), 48px)"
};
```

### Dark Mode
```typescript
const isDark = webdimens.matchesMedia('(prefers-color-scheme: dark)');
const bg = isDark ? '#1a1a1a' : '#ffffff';
```

### Safe Area (PWA)
```typescript
const header = {
  paddingTop: webdimens.safeArea('top', 16)
  // "max(16px, env(safe-area-inset-top))"
};
```

## 📱 Testar Agora

### Opção 1: Demo HTML
```bash
# Clone o repositório
git clone https://github.com/bodenberg/appdimens.git
cd appdimens/Web/examples

# Abra index.html no navegador
```

### Opção 2: Criar Projeto React
```bash
npx create-react-app my-app
cd my-app
npm install webdimens
```

Edite `src/App.js`:
```jsx
import { useWeb, useBreakpoint } from 'webdimens/react';

function App() {
  const dimens = useWeb();
  const breakpoint = useBreakpoint();

  return (
    <div style={{
      padding: dimens.fixed(24).toPx(),
      maxWidth: dimens.dynamic(1200).toPx(),
      margin: '0 auto'
    }}>
      <h1 style={{ fontSize: dimens.fluid(24, 48).toString() }}>
        Web Demo
      </h1>
      <p>Breakpoint atual: {breakpoint.current}</p>
      {breakpoint.above('md') && <p>Desktop layout</p>}
      {breakpoint.below('md') && <p>Mobile layout</p>}
    </div>
  );
}

export default App;
```

## 🎨 Design System Rápido

```typescript
// tokens.ts
import { webdimens } from 'webdimens';

export const spacing = webdimens.generateSpacingScale(4);
// { 0: "0px", 1: "4.3px", 2: "8.6px", ... }

export const typography = webdimens.generateFontScale();
// { xs: "12.9px", sm: "15px", base: "17.2px", ... }

export const colors = {
  primary: '#667eea',
  secondary: '#764ba2'
};

export const components = {
  button: {
    padding: `${spacing[4]} ${spacing[8]}`,
    fontSize: typography.base,
    borderRadius: spacing[2]
  },
  card: {
    padding: spacing[6],
    borderRadius: spacing[3]
  }
};
```

## 🔧 Configuração Customizada

```typescript
import { Web } from 'webdimens';

const dimens = new Web({
  baseWidth: 1440,
  referenceAR: 16/10,
  breakpoints: {
    mobile: 0,
    tablet: 768,
    desktop: 1024,
    wide: 1920
  },
  enableCache: true,
  autoUpdateCSSVars: true
});

// Usar instância customizada
const padding = dimens.fixed(16).toPx();
```

## 📚 Próximos Passos

1. **Leia a documentação completa**: [README.md](./README.md)
2. **Veja exemplos detalhados**: [examples/](./examples/)
3. **Explore a API**: [README.md#api-reference](./README.md#api-reference)
4. **Teste no browser**: [examples/index.html](./examples/index.html)

## 💡 Dicas

1. **Use Fixed para UI**: Botões, paddings, ícones, fontes
2. **Use Dynamic para Layouts**: Containers, widths, grids
3. **Use Fluid para Typography**: Smooth scaling entre breakpoints
4. **Ative CSS Vars**: Para performance e facilidade
5. **Use React Hooks**: Se estiver em React

## 🆘 Suporte

- **Documentação**: [README.md](./README.md)
- **Exemplos**: [examples/](./examples/)
- **Issues**: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)

---

**Pronto para começar!** 🚀

