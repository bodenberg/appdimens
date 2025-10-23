/**
 * React Integration Examples
 */

import React from 'react';
import {
  useWebDimens,
  useViewport,
  useBreakpoint,
  useMediaQuery,
  useMediaFeatures,
  useResponsiveValue,
  useDimension,
  useOrientation
} from '../src/integrations/react';

// ============================================
// 1. BASIC COMPONENT WITH HOOKS
// ============================================

export function ResponsiveCard() {
  const dimens = useWebDimens();
  const viewport = useViewport();

  return (
    <div style={{
      padding: dimens.fixed(16).toPx(),
      width: dimens.dynamic(300).toPx(),
      fontSize: dimens.fluid(14, 18).toString(),
      backgroundColor: viewport.orientation === 'portrait' ? '#f0f0f0' : '#ffffff'
    }}>
      <h2 style={{ fontSize: dimens.fixed(24).toPx() }}>
        Responsive Card
      </h2>
      <p>Viewport: {viewport.width}x{viewport.height}</p>
      <p>Aspect Ratio: {viewport.aspectRatio.toFixed(2)}</p>
    </div>
  );
}

// ============================================
// 2. BREAKPOINT-AWARE COMPONENT
// ============================================

export function BreakpointDemo() {
  const breakpoint = useBreakpoint();

  return (
    <div>
      <h1>Current Breakpoint: {breakpoint.current}</h1>
      
      {breakpoint.below('md') && (
        <p>Mobile view - showing simplified layout</p>
      )}
      
      {breakpoint.between('md', 'lg') && (
        <p>Tablet view - showing medium layout</p>
      )}
      
      {breakpoint.above('lg') && (
        <p>Desktop view - showing full layout</p>
      )}
    </div>
  );
}

// ============================================
// 3. MEDIA QUERY HOOK
// ============================================

export function DarkModeComponent() {
  const isDark = useMediaQuery('(prefers-color-scheme: dark)');
  const prefersReducedMotion = useMediaQuery('(prefers-reduced-motion: reduce)');
  const hasHover = useMediaQuery('(hover: hover)');

  return (
    <div style={{
      backgroundColor: isDark ? '#1a1a1a' : '#ffffff',
      color: isDark ? '#ffffff' : '#000000',
      transition: prefersReducedMotion ? 'none' : 'all 0.3s ease',
      cursor: hasHover ? 'pointer' : 'default'
    }}>
      <p>Theme: {isDark ? 'Dark' : 'Light'}</p>
      <p>Hover: {hasHover ? 'Supported' : 'Not supported'}</p>
      <p>Motion: {prefersReducedMotion ? 'Reduced' : 'Full'}</p>
    </div>
  );
}

// ============================================
// 4. MEDIA FEATURES HOOK
// ============================================

export function AccessibilityAware() {
  const features = useMediaFeatures();
  const dimens = useWebDimens();

  // Adjust touch targets for coarse pointers
  const minTouchTarget = features.pointer === 'coarse' ? 44 : 32;

  return (
    <button style={{
      padding: dimens.fixed(16).toPx(),
      minWidth: dimens.fixed(minTouchTarget).toPx(),
      minHeight: dimens.fixed(minTouchTarget).toPx(),
      fontSize: dimens.fixed(16).toPx(),
      backgroundColor: features.prefers === 'high-contrast' ? '#000' : '#007bff',
      color: '#fff',
      border: 'none',
      borderRadius: dimens.fixed(8).toPx(),
      cursor: features.hover === 'hover' ? 'pointer' : 'default'
    }}>
      Accessible Button
    </button>
  );
}

// ============================================
// 5. RESPONSIVE VALUE HOOK
// ============================================

export function ResponsiveGrid() {
  const columns = useResponsiveValue({
    xs: 1,
    sm: 2,
    md: 3,
    lg: 4,
    xl: 6
  });

  const dimens = useWebDimens();

  return (
    <div style={{
      display: 'grid',
      gridTemplateColumns: `repeat(${columns}, 1fr)`,
      gap: dimens.fixed(16).toPx(),
      padding: dimens.fixed(24).toPx()
    }}>
      {Array.from({ length: 12 }).map((_, i) => (
        <div
          key={i}
          style={{
            padding: dimens.fixed(16).toPx(),
            backgroundColor: '#f0f0f0',
            borderRadius: dimens.fixed(8).toPx()
          }}
        >
          Item {i + 1}
        </div>
      ))}
    </div>
  );
}

// ============================================
// 6. REACTIVE DIMENSION HOOK
// ============================================

export function DynamicSizeComponent() {
  const padding = useDimension(16, 'fixed');
  const width = useDimension(300, 'dynamic');
  const fontSize = useDimension(14, 'fluid', { max: 18, minBp: 'sm', maxBp: 'lg' });

  return (
    <div style={{
      padding,
      width,
      fontSize,
      backgroundColor: '#f9f9f9'
    }}>
      <p>This component uses reactive dimensions that update automatically</p>
    </div>
  );
}

// ============================================
// 7. ORIENTATION-AWARE LAYOUT
// ============================================

export function OrientationLayout() {
  const orientation = useOrientation();
  const dimens = useWebDimens();

  return (
    <div style={{
      display: 'flex',
      flexDirection: orientation === 'portrait' ? 'column' : 'row',
      gap: dimens.fixed(16).toPx(),
      padding: dimens.fixed(24).toPx()
    }}>
      <div style={{
        flex: 1,
        padding: dimens.fixed(16).toPx(),
        backgroundColor: '#e3f2fd'
      }}>
        Sidebar
      </div>
      <div style={{
        flex: 3,
        padding: dimens.fixed(16).toPx(),
        backgroundColor: '#f3e5f5'
      }}>
        Main Content
        <p>Orientation: {orientation}</p>
      </div>
    </div>
  );
}

// ============================================
// 8. COMPREHENSIVE EXAMPLE
// ============================================

export function ComprehensiveExample() {
  const dimens = useWebDimens();
  const viewport = useViewport();
  const breakpoint = useBreakpoint();
  const isDark = useMediaQuery('(prefers-color-scheme: dark)');
  const features = useMediaFeatures();

  const containerWidth = useResponsiveValue({
    xs: '100%',
    sm: '90%',
    md: '80%',
    lg: '70%',
    xl: '60%'
  });

  return (
    <div style={{
      width: containerWidth,
      margin: '0 auto',
      padding: dimens.safeArea('top', 16),
      backgroundColor: isDark ? '#1a1a1a' : '#ffffff',
      color: isDark ? '#ffffff' : '#000000',
      minHeight: '100vh'
    }}>
      <header style={{
        padding: dimens.fixed(24).toPx(),
        marginBottom: dimens.fixed(32).toPx(),
        borderBottom: '1px solid currentColor'
      }}>
        <h1 style={{
          fontSize: dimens.fluid(24, 48, 'sm', 'xl').toString(),
          margin: 0
        }}>
          WebDimens Demo
        </h1>
      </header>

      <main>
        <section style={{
          padding: dimens.fixed(16).toPx(),
          marginBottom: dimens.fixed(24).toPx()
        }}>
          <h2 style={{ fontSize: dimens.fixed(20).toPx() }}>
            Viewport Information
          </h2>
          <ul>
            <li>Width: {viewport.width}px</li>
            <li>Height: {viewport.height}px</li>
            <li>Aspect Ratio: {viewport.aspectRatio.toFixed(2)}</li>
            <li>Orientation: {viewport.orientation}</li>
            <li>DPR: {viewport.dpr}</li>
            <li>Breakpoint: {breakpoint.current}</li>
          </ul>
        </section>

        <section style={{
          padding: dimens.fixed(16).toPx(),
          marginBottom: dimens.fixed(24).toPx()
        }}>
          <h2 style={{ fontSize: dimens.fixed(20).toPx() }}>
            Media Features
          </h2>
          <ul>
            <li>Color Scheme: {features.prefers}</li>
            <li>Hover: {features.hover}</li>
            <li>Pointer: {features.pointer}</li>
            <li>Display Mode: {features.displayMode}</li>
          </ul>
        </section>

        <section>
          <div style={{
            display: 'grid',
            gridTemplateColumns: breakpoint.above('md') 
              ? 'repeat(3, 1fr)' 
              : breakpoint.above('sm') 
                ? 'repeat(2, 1fr)' 
                : '1fr',
            gap: dimens.fixed(16).toPx()
          }}>
            {Array.from({ length: 6 }).map((_, i) => (
              <div
                key={i}
                style={{
                  padding: dimens.fixed(24).toPx(),
                  backgroundColor: isDark ? '#2a2a2a' : '#f0f0f0',
                  borderRadius: dimens.fixed(12).toPx(),
                  transition: features.prefers === 'reduce-motion' 
                    ? 'none' 
                    : 'transform 0.2s ease'
                }}
              >
                <h3 style={{ 
                  fontSize: dimens.fixed(18).toPx(),
                  marginBottom: dimens.fixed(8).toPx()
                }}>
                  Card {i + 1}
                </h3>
                <p style={{ fontSize: dimens.fixed(14).toPx() }}>
                  This is a responsive card that adapts to screen size
                </p>
              </div>
            ))}
          </div>
        </section>
      </main>

      <footer style={{
        padding: dimens.fixed(24).toPx(),
        marginTop: dimens.fixed(48).toPx(),
        borderTop: '1px solid currentColor',
        textAlign: 'center',
        fontSize: dimens.fixed(14).toPx()
      }}>
        <p>WebDimens - Advanced Responsive Dimensions for Web</p>
      </footer>
    </div>
  );
}

export default ComprehensiveExample;

