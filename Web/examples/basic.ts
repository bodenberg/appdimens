/**
 * Basic WebDimens Usage Examples
 */

import { webdimens, fixed, dynamic, fluid } from '../src';

// ============================================
// 1. BASIC USAGE
// ============================================

// Fixed scaling (logarithmic) - for UI elements
const buttonPadding = fixed(16).toPx();           // "17.2px" (varies by screen)
const iconSize = fixed(24).toPx();                // "25.8px"
const fontSize = fixed(14).toRem();               // "0.875rem"

// Dynamic scaling (proportional) - for layouts
const containerWidth = dynamic(300).toPx();       // "22.5vw" equivalent
const cardHeight = dynamic(200).toVw();           // "15vw"

// Fluid scaling (clamp) - for smooth transitions
const fluidFont = fluid(16, 24).toString();       // "clamp(16px, ...calc..., 24px)"
const fluidSpacing = fluid(8, 32, 'sm', 'xl').toString();

// ============================================
// 2. ADVANCED USAGE WITH QUALIFIERS
// ============================================

// Priority 1: Device Mode + Size Qualifier (Intersection)
const adaptiveSize = fixed(16)
  .screen('mobile' as any, 'min-width' as any, 768, 20)   // Mobile at 768px+ = 20
  .screen('tablet' as any, 18)                     // Tablet = 18
  .screen('min-width' as any, 1024, 24)           // 1024px+ = 24
  .toPx();

// ============================================
// 3. BREAKPOINT-BASED SCALING
// ============================================

const responsivePadding = fixed(16)
  .screen('min-width' as any, 768, 24)    // md and up
  .screen('min-width' as any, 1024, 32)   // lg and up
  .toPx();

// ============================================
// 4. PHYSICAL UNITS
// ============================================

const physical = webdimens.physical;
const mmSize = physical.mm(10);                   // "37.79px" @ 96 DPI
const cmSize = physical.cm(2);                    // "75.59px"
const inchSize = physical.inch(1);                // "96px"

// ============================================
// 5. PERCENTAGE-BASED
// ============================================

const halfWidth = webdimens.percent(50, 'width');  // 50% of viewport width
const thirdHeight = webdimens.percent(33.33, 'height');

// ============================================
// 6. SAFE AREA (for mobile notches)
// ============================================

const safeTop = webdimens.safeArea('top', 16);
// Result: "max(16px, env(safe-area-inset-top))"

// ============================================
// 7. BREAKPOINT CHECKS
// ============================================

const bp = webdimens.breakpoints;
console.log('Current breakpoint:', bp.current);   // "lg"
console.log('Is desktop?', bp.above('md'));       // true
console.log('Is mobile?', bp.below('sm'));        // false
console.log('Is tablet?', bp.between('sm', 'lg')); // false

// ============================================
// 8. MEDIA QUERIES
// ============================================

const isDark = webdimens.matchesMedia('(prefers-color-scheme: dark)');
const hasHover = webdimens.matchesMedia('(hover: hover)');
const features = webdimens.getMediaFeatures();

console.log('Features:', features);
// { prefers: 'dark', orientation: 'landscape', hover: 'hover', ... }

// ============================================
// 9. CSS VARIABLES (Auto-update)
// ============================================

webdimens.enableCSSVars();
// Now you can use in CSS:
// padding: var(--wd-fx-16);
// width: var(--wd-dy-300);
// height: var(--wd-vmin);

// ============================================
// 10. CACHE MANAGEMENT
// ============================================

const stats = webdimens.getCacheStats();
console.log('Cache stats:', stats);
// { totalEntries: 42, cacheHits: 156, hitRate: 0.87, ... }

webdimens.clearCache(); // Clear all cached calculations

// ============================================
// 11. GENERATE DESIGN TOKENS
// ============================================

const spacingScale = webdimens.generateSpacingScale(4);
console.log(spacingScale);
// { 0: "0px", 1: "4.3px", 2: "8.6px", ... }

const fontScale = webdimens.generateFontScale();
console.log(fontScale);
// { xs: "12.9px", sm: "15px", base: "17.2px", ... }

// ============================================
// 12. COMPLEX EXAMPLE - RESPONSIVE CARD
// ============================================

const cardStyles = {
  // Fixed padding - consistent feel
  padding: fixed(16)
    .screen('min-width' as any, 768, 24)
    .toPx(),
  
  // Dynamic width - proportional to screen
  width: dynamic(300)
    .screen('min-width' as any, 1024, 400)
    .toPx(),
  
  // Fluid font size - smooth scaling
  fontSize: fluid(14, 18, 'sm', 'lg').toString(),
  
  // Fixed margin
  margin: fixed(8).toPx(),
  
  // Border radius with aspect ratio adjustment
  borderRadius: fixed(12)
    .aspectRatio(true, 0.1)
    .toPx()
};

console.log('Card styles:', cardStyles);

// ============================================
// 13. VIEWPORT SUBSCRIPTION
// ============================================

const unsubscribe = webdimens.viewport.subscribe((dimensions) => {
  console.log('Viewport changed:', dimensions);
  // { width: 1920, height: 1080, aspectRatio: 1.78, orientation: 'landscape' }
});

// Later: unsubscribe();

// ============================================
// 14. CUSTOM CONFIGURATION
// ============================================

import { WebDimens } from '../src';

const customDimens = new WebDimens({
  baseWidth: 1440,
  baseHeight: 900,
  referenceAR: 16/10,
  defaultSensitivity: 0.1,
  breakpoints: {
    mobile: 0,
    tablet: 768,
    desktop: 1024,
    wide: 1920
  },
  enableCache: true,
  debug: true,
  autoUpdateCSSVars: true
});

const customFixed = customDimens.fixed(16).toPx();

export {
  buttonPadding,
  iconSize,
  containerWidth,
  adaptiveSize,
  cardStyles,
  customDimens
};

