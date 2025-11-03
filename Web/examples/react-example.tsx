/**
 * React Integration Examples - Updated for v2.0.0
 */

import React, {useState} from 'react';
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
// 1. NEW STRATEGIES EXAMPLE (v2.0.0)
// ============================================

export function NewStrategiesExample() {
  const dimens = useWebDimens();
  const [selectedStrategy, setSelectedStrategy] = useState<string>('balanced');

  const strategies = {
    balanced: { name: 'BALANCED ⭐', description: 'Linear on phones, log on tablets (RECOMMENDED)' },
    percentage: { name: 'PERCENTAGE', description: 'Pure proportional (old Dynamic)' },
    default: { name: 'DEFAULT', description: '~97% linear + AR adjustment (old Fixed)' },
    logarithmic: { name: 'LOGARITHMIC', description: 'Pure logarithmic (Weber-Fechner)' },
    power: { name: 'POWER', description: 'Stevens\' Power Law' },
    fluid: { name: 'FLUID', description: 'CSS clamp-like with bounds' },
  };

  const getSize = (base: number): string => {
    switch (selectedStrategy) {
      case 'balanced':
        return dimens.balanced(base).toPx();
      case 'percentage':
        return dimens.percentage(base).toPx();
      case 'default':
        return dimens.default(base).toPx();
      case 'logarithmic':
        return dimens.logarithmic(base).toPx();
      case 'power':
        return dimens.power(base, 0.75).toPx();
      case 'fluid':
        return dimens.fluid(base * 0.8, base * 1.2).toPx();
      default:
        return `${base}px`;
    }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
      <h1 style={{ fontSize: getSize(32), textAlign: 'center' }}>
        AppDimens v2.0.0 - New Strategies
      </h1>

      <div style={{ 
        backgroundColor: '#E3F2FD', 
        padding: getSize(16), 
        borderRadius: getSize(12),
        marginBottom: getSize(20)
      }}>
        <h3 style={{ fontSize: getSize(18) }}>About v2.0.0</h3>
        <p style={{ fontSize: getSize(14) }}>
          Version 2.0.0 introduces 13 scaling strategies. Select one below to see it in action:
        </p>
      </div>

      {/* Strategy Selector */}
      <div style={{ marginBottom: getSize(20) }}>
        {Object.entries(strategies).map(([key, info]) => (
          <button
            key={key}
            onClick={() => setSelectedStrategy(key)}
            style={{
              padding: `${getSize(8)} ${getSize(16)}`,
              margin: getSize(4),
              backgroundColor: selectedStrategy === key ? '#007AFF' : '#E0E0E0',
              color: selectedStrategy === key ? 'white' : 'black',
              border: 'none',
              borderRadius: getSize(8),
              cursor: 'pointer',
              fontSize: getSize(12),
            }}
          >
            {info.name}
          </button>
        ))}
      </div>

      {/* Strategy Info */}
      <div style={{
        backgroundColor: '#FFF3E0',
        padding: getSize(16),
        borderRadius: getSize(12),
        marginBottom: getSize(20)
      }}>
        <h3 style={{ fontSize: getSize(18) }}>
          {strategies[selectedStrategy as keyof typeof strategies].name}
        </h3>
        <p style={{ fontSize: getSize(14) }}>
          {strategies[selectedStrategy as keyof typeof strategies].description}
        </p>
      </div>

      {/* Visual Demo */}
      <div style={{
        display: 'flex',
        justifyContent: 'space-around',
        marginBottom: getSize(20)
      }}>
        {[48, 64, 96].map(size => (
          <div key={size} style={{ textAlign: 'center' }}>
            <div style={{
              width: getSize(size),
              height: getSize(size),
              backgroundColor: '#BBDEFB',
              borderRadius: getSize(8),
              marginBottom: getSize(8)
            }} />
            <div style={{ fontSize: getSize(12) }}>{size}px</div>
            <div style={{ fontSize: getSize(11), fontWeight: 'bold' }}>
              {parseInt(getSize(size))}px
            </div>
          </div>
        ))}
      </div>

      {/* Code Example */}
      <div style={{
        backgroundColor: '#F5F5F5',
        padding: getSize(16),
        borderRadius: getSize(12),
        fontFamily: 'monospace',
        fontSize: getSize(12)
      }}>
        <code>
          dimens.{selectedStrategy}({selectedStrategy === 'power' ? '48, 0.75' : '48'}).toPx()
        </code>
      </div>
    </div>
  );
}

// ============================================
// 2. SMART API EXAMPLE (v2.0.0)
// ============================================

export function SmartAPIExample() {
  const dimens = useWebDimens();

  return (
    <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
      <h1 style={{ fontSize: dimens.balanced(32).toPx() }}>
        🧠 Smart API - Auto-Inference
      </h1>

      <div style={{
        backgroundColor: '#FFF3E0',
        padding: dimens.balanced(16).toPx(),
        borderRadius: dimens.balanced(12).toPx(),
        marginBottom: dimens.balanced(20).toPx()
      }}>
        <p style={{ fontSize: dimens.balanced(14).toPx() }}>
          The Smart API automatically selects the best strategy based on the element type.
        </p>
      </div>

      {/* Smart API Examples */}
      {[
        { type: 'button', code: 'smart(48).forElement("button")', strategy: 'BALANCED' },
        { type: 'container', code: 'smart(300).forElement("container")', strategy: 'PERCENTAGE' },
        { type: 'text', code: 'smart(16).forElement("text")', strategy: 'BALANCED' },
        { type: 'icon', code: 'smart(24).forElement("icon")', strategy: 'DEFAULT' },
      ].map(item => (
        <div key={item.type} style={{
          marginBottom: dimens.balanced(12).toPx(),
          padding: dimens.balanced(12).toPx(),
          backgroundColor: '#FFFFFF',
          borderRadius: dimens.balanced(8).toPx(),
          boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
        }}>
          <div style={{
            display: 'flex',
            justifyContent: 'space-between',
            marginBottom: dimens.balanced(8).toPx()
          }}>
            <strong style={{ fontSize: dimens.balanced(14).toPx() }}>
              {item.type.charAt(0).toUpperCase() + item.type.slice(1)}
            </strong>
            <span style={{ 
              fontSize: dimens.balanced(12).toPx(),
              color: '#FF6F00'
            }}>
              → {item.strategy}
            </span>
          </div>
          <code style={{
            display: 'block',
            padding: dimens.balanced(8).toPx(),
            backgroundColor: '#F5F5F5',
            borderRadius: dimens.balanced(4).toPx(),
            fontFamily: 'monospace',
            fontSize: dimens.balanced(11).toPx()
          }}>
            {item.code}
          </code>
        </div>
      ))}
    </div>
  );
}

// ============================================
// 3. BASIC COMPONENT WITH HOOKS
// ============================================

export function ResponsiveCard() {
  const dimens = useWebDimens();
  const viewport = useViewport();

  return (
    <div style={{
      padding: dimens.balanced(16).toPx(),
      width: dimens.percentage(300).toPx(),
      fontSize: dimens.fluid(14, 18).toString(),
      backgroundColor: viewport.orientation === 'portrait' ? '#f0f0f0' : '#ffffff'
    }}>
      <h2 style={{ fontSize: dimens.balanced(24).toPx() }}>
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

