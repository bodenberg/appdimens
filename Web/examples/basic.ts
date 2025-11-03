/**
 * Basic WebDimens Usage Examples - v2.0.0
 * 
 * This file demonstrates all 13 scaling strategies and the Smart API
 * introduced in version 2.0.0
 */

import { webdimens, fixed, dynamic, fluid } from '../src';

console.log('='.repeat(60));
console.log('WebDimens v2.0.0 - Basic Usage Examples');
console.log('='.repeat(60));

// ============================================
// 1. NEW STRATEGIES (v2.0.0)
// ============================================

console.log('\n📊 NEW: 13 Scaling Strategies\n');

// BALANCED ⭐ - RECOMMENDED for most apps
// Linear on phones (<480), logarithmic on tablets (≥480)
const balancedSize = webdimens.balanced(48).toPx();
console.log(`✓ BALANCED (48):      ${balancedSize} ⭐ RECOMMENDED`);

// PERCENTAGE - 100% proportional (old Dynamic)
const percentageSize = webdimens.percentage(48).toPx();
console.log(`  PERCENTAGE (48):    ${percentageSize} (old: dy)`);

// DEFAULT - ~97% linear + AR adjustment (old Fixed)
const defaultSize = webdimens.default(48).toPx();
console.log(`  DEFAULT (48):       ${defaultSize} (old: fx)`);

// LOGARITHMIC - Pure Weber-Fechner Law
const logarithmicSize = webdimens.logarithmic(48).toPx();
console.log(`  LOGARITHMIC (48):   ${logarithmicSize}`);

// POWER - Stevens' Power Law
const powerSize = webdimens.power(48, 0.75).toPx();
console.log(`  POWER (48, 0.75):   ${powerSize}`);

// FLUID - CSS clamp-like with min/max boundaries
const fluidSize = webdimens.fluid(40, 56).toString();
console.log(`  FLUID (40, 56):     ${fluidSize}`);

// INTERPOLATED - 50% moderated linear growth
const interpolatedSize = webdimens.interpolated(48).toPx();
console.log(`  INTERPOLATED (48):  ${interpolatedSize}`);

// DIAGONAL - Scale based on screen diagonal
const diagonalSize = webdimens.diagonal(48).toPx();
console.log(`  DIAGONAL (48):      ${diagonalSize}`);

// PERIMETER - Scale based on perimeter (W+H)
const perimeterSize = webdimens.perimeter(48).toPx();
console.log(`  PERIMETER (48):     ${perimeterSize}`);

// FIT - Letterbox scaling (min ratio) - Game fit
const fitSize = webdimens.fit(48).toPx();
console.log(`  FIT (48):           ${fitSize} (game)`);

// FILL - Cover scaling (max ratio) - Game fill
const fillSize = webdimens.fill(48).toPx();
console.log(`  FILL (48):          ${fillSize} (game)`);

// AUTOSIZE - Auto-adjust based on component size
const autosizeSize = webdimens.autosize(48).toPx();
console.log(`  AUTOSIZE (48):      ${autosizeSize}`);

// NONE - No scaling (constant size)
const noneSize = webdimens.none(48).toPx();
console.log(`  NONE (48):          ${noneSize}`);

// ============================================
// 2. SMART API (Auto-Inference)
// ============================================

console.log('\n🧠 NEW: Smart API (Auto-Inference)\n');

// Smart API automatically selects the best strategy based on element type
const smartButton = webdimens.smart(48).forElement('button').toPx();
console.log(`✓ smart(48).forElement('button'):     ${smartButton} → BALANCED`);

const smartContainer = webdimens.smart(300).forElement('container').toPx();
console.log(`  smart(300).forElement('container'): ${smartContainer} → PERCENTAGE`);

const smartText = webdimens.smart(16).forElement('text').toPx();
console.log(`  smart(16).forElement('text'):       ${smartText} → BALANCED`);

const smartIcon = webdimens.smart(24).forElement('icon').toPx();
console.log(`  smart(24).forElement('icon'):       ${smartIcon} → DEFAULT`);

console.log('\nElement types: button, text, icon, container, spacing,');
console.log('  card, dialog, toolbar, fab, chip, list_item, image,');
console.log('  badge, divider, navigation, input, header, generic');

// ============================================
// 3. LEGACY API (still works, but deprecated)
// ============================================

console.log('\n📝 Legacy API (Deprecated)\n');

// OLD: fx() → NEW: default()
const oldFx = webdimens.fx(16).toPx();
console.log(`  OLD: fx(16)  = ${oldFx}`);
console.log(`  NEW: default(16) = ${webdimens.default(16).toPx()}`);

// OLD: dy() → NEW: percentage()
const oldDy = webdimens.dy(100).toPx();
console.log(`  OLD: dy(100) = ${oldDy}`);
console.log(`  NEW: percentage(100) = ${webdimens.percentage(100).toPx()}`);

console.log('\n⚠️  Recommendation: Use balanced() for most cases');

// ============================================
// 4. RESPONSIVE CARD EXAMPLE
// ============================================

console.log('\n💳 Responsive Card Example\n');

const cardStyles = {
  // Use BALANCED for most dimensions ⭐
  padding: webdimens.balanced(16).toPx(),
  borderRadius: webdimens.balanced(12).toPx(),
  margin: webdimens.balanced(8).toPx(),
  
  // Use PERCENTAGE for containers
  width: webdimens.percentage(300).toPx(),
  
  // Use FLUID for typography
  fontSize: webdimens.fluid(14, 18).toString(),
  lineHeight: webdimens.fluid(20, 26).toString(),
};

console.log('Card Styles:', JSON.stringify(cardStyles, null, 2));

// ============================================
// 5. BREAKPOINT-AWARE DIMENSIONS
// ============================================

console.log('\n📱 Breakpoint-Aware Dimensions\n');

const responsivePadding = webdimens.balanced(16)
  .screen('min-width' as any, 768, 24)    // md and up
  .screen('min-width' as any, 1024, 32)   // lg and up
  .toPx();

console.log(`Responsive padding: ${responsivePadding}`);

// ============================================
// 6. PHYSICAL UNITS
// ============================================

console.log('\n📏 Physical Units\n');

const physical = webdimens.physical;
console.log(`10mm = ${physical.mm(10)}`);
console.log(`2cm  = ${physical.cm(2)}`);
console.log(`1in  = ${physical.inch(1)}`);

// ============================================
// 7. VIEWPORT & BREAKPOINTS
// ============================================

console.log('\n🖥️  Viewport & Breakpoints\n');

const viewport = webdimens.getViewportInfo();
console.log(`Viewport: ${viewport.width}x${viewport.height}`);
console.log(`Aspect Ratio: ${viewport.aspectRatio.toFixed(2)}`);
console.log(`Orientation: ${viewport.orientation}`);

const bp = webdimens.breakpoints;
console.log(`Current breakpoint: ${bp.current}`);
console.log(`Is desktop? ${bp.above('md')}`);
console.log(`Is mobile? ${bp.below('sm')}`);

// ============================================
// 8. MEDIA FEATURES
// ============================================

console.log('\n🎨 Media Features\n');

const features = webdimens.getMediaFeatures();
console.log(`Color scheme: ${features.prefers}`);
console.log(`Orientation: ${features.orientation}`);
console.log(`Hover: ${features.hover}`);
console.log(`Pointer: ${features.pointer}`);

// ============================================
// 9. DESIGN TOKENS
// ============================================

console.log('\n🎯 Generated Design Tokens\n');

const spacingScale = webdimens.generateSpacingScale(4);
console.log('Spacing scale:', spacingScale);

const fontScale = webdimens.generateFontScale();
console.log('Font scale:', fontScale);

// ============================================
// 10. CACHE STATS
// ============================================

console.log('\n📊 Performance Stats\n');

const stats = webdimens.getCacheStats();
console.log(`Total cache entries: ${stats.totalEntries}`);
console.log(`Cache hits: ${stats.cacheHits}`);
console.log(`Hit rate: ${(stats.hitRate * 100).toFixed(1)}%`);

// ============================================
// 11. COMPARISON TABLE
// ============================================

console.log('\n📊 Strategy Comparison (48px base)\n');
console.log('┌─────────────────┬──────────────┬────────────────────────┐');
console.log('│ Strategy        │ Result       │ Best For               │');
console.log('├─────────────────┼──────────────┼────────────────────────┤');
console.log(`│ BALANCED ⭐     │ ${balancedSize.padEnd(12)} │ Multi-device apps      │`);
console.log(`│ PERCENTAGE      │ ${percentageSize.padEnd(12)} │ Containers, layouts    │`);
console.log(`│ DEFAULT         │ ${defaultSize.padEnd(12)} │ Phone-only apps        │`);
console.log(`│ LOGARITHMIC     │ ${logarithmicSize.padEnd(12)} │ TVs, large tablets     │`);
console.log(`│ POWER           │ ${powerSize.padEnd(12)} │ General purpose        │`);
console.log('└─────────────────┴──────────────┴────────────────────────┘');

// ============================================
// 12. MIGRATION GUIDE
// ============================================

console.log('\n🔄 Migration Guide\n');
console.log('OLD API → NEW API');
console.log('─'.repeat(40));
console.log('.fx()  → .default() or .balanced() ⭐');
console.log('.dy()  → .percentage()');
console.log('');
console.log('RECOMMENDED for new code:');
console.log('• Use .balanced() for most elements ⭐');
console.log('• Use .smart() for auto-inference');
console.log('• Use .percentage() for containers');
console.log('• Use .fluid() for typography');

console.log('\n' + '='.repeat(60));
console.log('✨ All examples completed successfully!');
console.log('='.repeat(60) + '\n');

// Export for use in other files
export {
  balancedSize,
  percentageSize,
  defaultSize,
  logarithmicSize,
  powerSize,
  smartButton,
  smartContainer,
  cardStyles,
};
