/**
 * AppDimens Web v2.0 - Practical Usage Examples
 * Demonstra todas as 13 estratégias em casos de uso reais
 */

import {
  WebDimensBuilder,
  balanced,
  logarithmic,
  power,
  defaultStrategy,
  percentage,
  smart,
  ElementType,
} from '../src/index';

/**
 * Exemplo 1: App Multi-Dispositivo (BALANCED - Recomendado)
 */
export function createMultiDeviceStyles() {
  return {
    container: {
      padding: balanced(16).toPx(),           // '16px'
      maxWidth: balanced(1200).toPx(),
    },
    
    title: {
      fontSize: balanced(32).toRem(),         // '2rem'
      marginBottom: balanced(24).toPx(),
      lineHeight: '1.2',
    },
    
    button: {
      padding: `${balanced(12).px()}px ${balanced(24).px()}px`,
      fontSize: balanced(16).toRem(),
      borderRadius: balanced(8).toPx(),
      backgroundColor: '#007AFF',
    },
    
    card: {
      width: balanced(300).toPx(),
      padding: balanced(16).toPx(),
      borderRadius: balanced(12).toPx(),
      boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
    },
  };
}

/**
 * Exemplo 2: Smart Inference
 */
export function createSmartStyles() {
  return {
    // Auto-seleciona BALANCED para botão
    button: {
      padding: smart(12, ElementType.BUTTON).toPx(),
      fontSize: smart(16, ElementType.BUTTON).toRem(),
    },
    
    // Auto-seleciona DEFAULT para ícone
    icon: {
      width: smart(24, ElementType.ICON).toPx(),
      height: smart(24, ElementType.ICON).toPx(),
    },
    
    // Auto-seleciona PERCENTAGE para container
    card: {
      width: smart(300, ElementType.CONTAINER).toPx(),
      maxWidth: smart(600, ElementType.CONTAINER).toPx(),
    },
    
    // Auto-seleciona NONE para divisória
    divider: {
      height: smart(1, ElementType.DIVIDER).toPx(), // Sempre '1px'
      backgroundColor: '#E0E0E0',
    },
  };
}

/**
 * Exemplo 3: App para TV (LOGARITHMIC)
 */
export function createTVStyles() {
  return {
    container: {
      padding: logarithmic(48).toPx(),
    },
    
    hero: {
      fontSize: logarithmic(72).toRem(),      // Controle em telas grandes
      marginBottom: logarithmic(48).toPx(),
    },
    
    subtitle: {
      fontSize: logarithmic(32).toRem(),
      marginBottom: logarithmic(32).toPx(),
    },
    
    card: {
      padding: logarithmic(32).toPx(),
      borderRadius: logarithmic(16).toPx(),
    },
  };
}

/**
 * Exemplo 4: Jogo Web (FIT/FILL)
 */
export function createGameStyles() {
  return {
    gameCanvas: {
      // FIT - garante que caiba (letterbox)
      width: new WebDimensBuilder(1920).fit().toPx(),
      height: new WebDimensBuilder(1080).fit().toPx(),
      backgroundColor: '#1a1a1a',
    },
    
    background: {
      // FILL - cobre a tela (cover)
      position: 'absolute' as const,
      width: new WebDimensBuilder(1920).fill().toPx(),
      height: new WebDimensBuilder(1080).fill().toPx(),
      backgroundImage: 'url(/bg.jpg)',
      backgroundSize: 'cover',
      zIndex: -1,
    },
    
    hudButton: {
      padding: new WebDimensBuilder(16).fit().toPx(),
      fontSize: new WebDimensBuilder(14).fit().toRem(),
    },
  };
}

/**
 * Exemplo 5: Tipografia Fluida (FLUID)
 */
export function createFluidTypography() {
  return {
    // Título fluido: 24px em mobile, 48px em desktop
    h1: {
      fontSize: new WebDimensBuilder(48)
        .fluid(24, 48, 320, 1200)
        .toRem(),
    },
    
    // Parágrafo fluido: 14px em mobile, 18px em desktop
    paragraph: {
      fontSize: new WebDimensBuilder(16)
        .fluid(14, 18, 320, 768)
        .toRem(),
      lineHeight: '1.6',
    },
    
    // Spacing fluido
    section: {
      padding: new WebDimensBuilder(32)
        .fluid(16, 64, 320, 1440)
        .toPx(),
    },
  };
}

/**
 * Exemplo 6: Configurações Avançadas
 */
export function createAdvancedStyles() {
  return {
    // BALANCED com transição customizada
    customTransition: {
      padding: balanced(16)
        .withTransitionPoint(600) // Transição em 600dp ao invés de 480dp
        .toPx(),
    },
    
    // POWER com expoente customizado
    conservativeScale: {
      fontSize: power(24, 0.60) // Mais conservador
        .toRem(),
    },
    
    // Com constraints de tamanho
    bounded: {
      width: balanced(300)
        .withMin(200)
        .withMax(400)
        .toPx(),
    },
    
    // Com tamanho físico máximo
    physicalBound: {
      fontSize: balanced(48)
        .withMaxPhysicalMm(8) // Máximo 8mm
        .toRem(),
    },
  };
}

/**
 * Exemplo 7: Design System Completo
 */
export function createDesignSystem() {
  return {
    // Spacing system (BALANCED)
    spacing: {
      xs: balanced(4).px(),
      sm: balanced(8).px(),
      md: balanced(16).px(),
      lg: balanced(24).px(),
      xl: balanced(32).px(),
      xxl: balanced(48).px(),
    },
    
    // Typography system (BALANCED para texto)
    typography: {
      h1: balanced(48).px(),
      h2: balanced(36).px(),
      h3: balanced(24).px(),
      body: balanced(16).px(),
      caption: balanced(12).px(),
    },
    
    // Border radius system (DEFAULT para detalhes)
    borderRadius: {
      sm: defaultStrategy(4).px(),
      md: defaultStrategy(8).px(),
      lg: defaultStrategy(12).px(),
      xl: defaultStrategy(16).px(),
      full: '9999px',
    },
    
    // Icon sizes (DEFAULT)
    icons: {
      sm: smart(16, ElementType.ICON).px(),
      md: smart(24, ElementType.ICON).px(),
      lg: smart(32, ElementType.ICON).px(),
    },
    
    // Container widths (PERCENTAGE para containers)
    containers: {
      sm: percentage(640).px(),
      md: percentage(768).px(),
      lg: percentage(1024).px(),
      xl: percentage(1280).px(),
    },
  };
}

/**
 * Exemplo 8: Responsive Grid
 */
export function createResponsiveGrid() {
  const designSystem = createDesignSystem();
  
  return {
    gridContainer: {
      display: 'grid',
      gap: designSystem.spacing.md + 'px',
      gridTemplateColumns: `repeat(auto-fit, minmax(${balanced(250).px()}px, 1fr))`,
      padding: designSystem.spacing.lg + 'px',
    },
    
    gridItem: {
      padding: designSystem.spacing.md + 'px',
      borderRadius: designSystem.borderRadius.md + 'px',
      backgroundColor: '#F5F5F5',
    },
  };
}

/**
 * Exemplo 9: Comparação Visual de Estratégias
 */
export function demonstrateStrategies() {
  console.log('='.repeat(60));
  console.log('AppDimens v2.0 - Strategy Demonstration');
  console.log('='.repeat(60));
  
  const baseValue = 48;
  
  const results = [
    { name: 'DEFAULT', value: defaultStrategy(baseValue).px() },
    { name: 'PERCENTAGE', value: percentage(baseValue).px() },
    { name: 'BALANCED ⭐', value: balanced(baseValue).px() },
    { name: 'LOGARITHMIC', value: logarithmic(baseValue).px() },
    { name: 'POWER (0.75)', value: power(baseValue, 0.75).px() },
    { name: 'INTERPOLATED', value: new WebDimensBuilder(baseValue).interpolated().px() },
    { name: 'DIAGONAL', value: new WebDimensBuilder(baseValue).diagonal().px() },
    { name: 'PERIMETER', value: new WebDimensBuilder(baseValue).perimeter().px() },
    { name: 'FIT', value: new WebDimensBuilder(baseValue).fit().px() },
    { name: 'FILL', value: new WebDimensBuilder(baseValue).fill().px() },
    { name: 'NONE', value: new WebDimensBuilder(baseValue).none().px() },
  ];
  
  results.forEach(({ name, value }) => {
    console.log(`  ${name.padEnd(18)} ${value.toFixed(2)}px`);
  });
  
  console.log('='.repeat(60));
}

// Export all examples
export default {
  createMultiDeviceStyles,
  createSmartStyles,
  createTVStyles,
  createGameStyles,
  createFluidTypography,
  createAdvancedStyles,
  createDesignSystem,
  createResponsiveGrid,
  demonstrateStrategies,
};

