/**
 * Author & Developer: Jean Bodenberg
 * 
 * React Integration for WebDimens
 * Hooks and utilities for React applications
 */

import { useState, useEffect, useMemo, useCallback } from 'react';
import type { ViewportDimensions, MediaFeatures } from '../types';
import { webdimens } from '../core/WebDimens';
import { globalViewportObserver } from '../observers/ViewportObserver';
import { globalMediaQueryObserver } from '../observers/MediaQueryObserver';
import { breakpointManager } from '../breakpoints/Breakpoints';

/**
 * Hook: useWebDimens
 * Main hook for accessing WebDimens functions
 */
export function useWebDimens() {
  return useMemo(() => ({
    fixed: (value: number) => webdimens.fixed(value),
    fx: (value: number) => webdimens.fx(value),
    dynamic: (value: number) => webdimens.dynamic(value),
    dy: (value: number) => webdimens.dy(value),
    fluid: (min: number, max: number, minBp?: string, maxBp?: string) => 
      webdimens.fluid(min, max, minBp, maxBp),
    fl: (min: number, max: number, minBp?: string, maxBp?: string) => 
      webdimens.fl(min, max, minBp, maxBp),
    percent: (percentage: number, type?: 'width' | 'height') => 
      webdimens.percent(percentage, type),
    safeArea: (side: 'top' | 'right' | 'bottom' | 'left', fallback?: number) => 
      webdimens.safeArea(side, fallback)
  }), []);
}

/**
 * Hook: useViewport
 * Subscribe to viewport changes
 */
export function useViewport(): ViewportDimensions {
  const [viewport, setViewport] = useState<ViewportDimensions>(
    globalViewportObserver.getDimensions()
  );

  useEffect(() => {
    const unsubscribe = globalViewportObserver.subscribe((dimensions) => {
      setViewport(dimensions);
    });

    return unsubscribe;
  }, []);

  return viewport;
}

/**
 * Hook: useBreakpoint
 * Get current breakpoint and check functions
 */
export function useBreakpoint() {
  const viewport = useViewport();
  
  const current = useMemo(() => breakpointManager.current, [viewport]);

  const is = useCallback((breakpoint: string) => {
    return breakpointManager.is(breakpoint);
  }, [viewport]);

  const above = useCallback((breakpoint: string) => {
    return breakpointManager.above(breakpoint);
  }, [viewport]);

  const below = useCallback((breakpoint: string) => {
    return breakpointManager.below(breakpoint);
  }, [viewport]);

  const between = useCallback((min: string, max: string) => {
    return breakpointManager.between(min, max);
  }, [viewport]);

  return {
    current,
    is,
    above,
    below,
    between
  };
}

/**
 * Hook: useMediaQuery
 * Subscribe to media query changes
 */
export function useMediaQuery(query: string): boolean {
  const [matches, setMatches] = useState<boolean>(
    globalMediaQueryObserver.matches(query)
  );

  useEffect(() => {
    const unsubscribe = globalMediaQueryObserver.observe(query, (isMatching) => {
      setMatches(isMatching);
    });

    return unsubscribe;
  }, [query]);

  return matches;
}

/**
 * Hook: useMediaFeatures
 * Get current media features
 */
export function useMediaFeatures(): MediaFeatures {
  const [features, setFeatures] = useState<MediaFeatures>(
    globalMediaQueryObserver.getMediaFeatures()
  );

  useEffect(() => {
    // Subscribe to relevant media queries
    const unsubscribes = [
      globalMediaQueryObserver.observe('(prefers-color-scheme: dark)', () => {
        setFeatures(globalMediaQueryObserver.getMediaFeatures());
      }),
      globalMediaQueryObserver.observe('(prefers-reduced-motion: reduce)', () => {
        setFeatures(globalMediaQueryObserver.getMediaFeatures());
      }),
      globalMediaQueryObserver.observe('(orientation: portrait)', () => {
        setFeatures(globalMediaQueryObserver.getMediaFeatures());
      })
    ];

    return () => {
      unsubscribes.forEach(unsub => unsub());
    };
  }, []);

  return features;
}

/**
 * Hook: useResponsiveValue
 * Get different values based on breakpoints
 */
export function useResponsiveValue<T>(values: Record<string, T>): T {
  const breakpoint = useBreakpoint();
  
  return useMemo(() => {
    // Get value for current breakpoint or fallback to closest smaller
    const breakpoints = Object.keys(values).sort((a, b) => {
      return breakpointManager.getValue(b) - breakpointManager.getValue(a);
    });

    for (const bp of breakpoints) {
      if (breakpoint.is(bp) || breakpoint.above(bp)) {
        return values[bp];
      }
    }

    // Return first value as fallback
    return values[breakpoints[breakpoints.length - 1]];
  }, [breakpoint.current, values]);
}

/**
 * Hook: useDimension
 * Reactive dimension that updates on viewport change
 */
export function useDimension(
  value: number,
  type: 'fixed' | 'dynamic' | 'fluid' = 'fixed',
  options?: any
): string {
  const viewport = useViewport();

  return useMemo(() => {
    switch (type) {
      case 'fixed':
        return webdimens.fixed(value).toPx();
      case 'dynamic':
        return webdimens.dynamic(value).toPx();
      case 'fluid':
        if (options?.max) {
          return webdimens.fluid(value, options.max, options.minBp, options.maxBp).toString();
        }
        return webdimens.fixed(value).toPx();
      default:
        return webdimens.fixed(value).toPx();
    }
  }, [value, type, viewport.width, viewport.height, options]);
}

/**
 * Hook: useOrientation
 * Get current orientation
 */
export function useOrientation(): 'portrait' | 'landscape' {
  const viewport = useViewport();
  return viewport.orientation;
}

/**
 * Hook: useAspectRatio
 * Get current aspect ratio
 */
export function useAspectRatio(): number {
  const viewport = useViewport();
  return viewport.aspectRatio;
}

/**
 * HOC: withResponsive
 * Add responsive props to any component
 */
export function withResponsive<P extends object>(
  Component: React.ComponentType<P>
): React.FC<P> {
  return (props: P) => {
    const dimens = useWebDimens();
    const viewport = useViewport();
    const breakpoint = useBreakpoint();

    return (
      <Component
        {...props}
        dimens={dimens}
        viewport={viewport}
        breakpoint={breakpoint}
      />
    );
  };
}

