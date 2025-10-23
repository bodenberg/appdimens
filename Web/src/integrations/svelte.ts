/**
 * Author & Developer: Jean Bodenberg
 * 
 * Svelte Integration for Web
 * Stores and utilities for Svelte applications
 */

import { writable, derived, readable, type Readable, type Writable } from 'svelte/store';
import type { ViewportDimensions, MediaFeatures } from '../types';
import { webdimens } from '../core/WebDimens';
import { globalViewportObserver } from '../observers/ViewportObserver';
import { globalMediaQueryObserver } from '../observers/MediaQueryObserver';
import { breakpointManager } from '../breakpoints/Breakpoints';

/**
 * Store: viewport
 * Reactive viewport dimensions
 */
export const viewport: Readable<ViewportDimensions> = readable(
  globalViewportObserver.getDimensions(),
  (set) => {
    const unsubscribe = globalViewportObserver.subscribe((dimensions) => {
      set(dimensions);
    });
    
    return unsubscribe;
  }
);

/**
 * Store: breakpoint
 * Current breakpoint with utility functions
 */
export const breakpoint = derived(viewport, ($viewport) => {
  return {
    current: breakpointManager.current,
    is: (bp: string) => breakpointManager.is(bp),
    above: (bp: string) => breakpointManager.above(bp),
    below: (bp: string) => breakpointManager.below(bp),
    between: (min: string, max: string) => breakpointManager.between(min, max),
    value: (bp: string) => breakpointManager.getValue(bp)
  };
});

/**
 * Function: createMediaQuery
 * Create a reactive media query store
 */
export function createMediaQuery(query: string): Readable<boolean> {
  return readable(
    globalMediaQueryObserver.matches(query),
    (set) => {
      const unsubscribe = globalMediaQueryObserver.observe(query, (matches) => {
        set(matches);
      });
      
      return unsubscribe;
    }
  );
}

/**
 * Store: mediaFeatures
 * Reactive media features
 */
export const mediaFeatures: Readable<MediaFeatures> = readable(
  globalMediaQueryObserver.getMediaFeatures(),
  (set) => {
    const unsubscribes = [
      globalMediaQueryObserver.observe('(prefers-color-scheme: dark)', () => {
        set(globalMediaQueryObserver.getMediaFeatures());
      }),
      globalMediaQueryObserver.observe('(prefers-reduced-motion: reduce)', () => {
        set(globalMediaQueryObserver.getMediaFeatures());
      }),
      globalMediaQueryObserver.observe('(orientation: portrait)', () => {
        set(globalMediaQueryObserver.getMediaFeatures());
      })
    ];

    return () => {
      unsubscribes.forEach(unsub => unsub());
    };
  }
);

/**
 * Function: createResponsiveValue
 * Create a store that returns different values based on breakpoints
 */
export function createResponsiveValue<T>(values: Record<string, T>): Readable<T> {
  return derived(breakpoint, ($breakpoint) => {
    const breakpoints = Object.keys(values).sort((a, b) => {
      return breakpointManager.getValue(b) - breakpointManager.getValue(a);
    });

    for (const bp of breakpoints) {
      if ($breakpoint.is(bp) || $breakpoint.above(bp)) {
        return values[bp];
      }
    }

    return values[breakpoints[breakpoints.length - 1]];
  });
}

/**
 * Function: createDimension
 * Create a reactive dimension store
 */
export function createDimension(
  value: number,
  type: 'fixed' | 'dynamic' | 'fluid' = 'fixed',
  options?: any
): Readable<string> {
  return derived(viewport, ($viewport) => {
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
  });
}

/**
 * Store: orientation
 * Current screen orientation
 */
export const orientation = derived(
  viewport,
  ($viewport) => $viewport.orientation
);

/**
 * Store: aspectRatio
 * Current aspect ratio
 */
export const aspectRatio = derived(
  viewport,
  ($viewport) => $viewport.aspectRatio
);

/**
 * Function: initializeWebDimens
 * Initialize Web for Svelte app
 */
export function initializeWebDimens() {
  // Enable CSS variables
  webdimens.enableCSSVars();
  
  return webdimens;
}

// Export webdimens instance
export { webdimens };

// Common media queries as stores
export const isDark = createMediaQuery('(prefers-color-scheme: dark)');
export const isReducedMotion = createMediaQuery('(prefers-reduced-motion: reduce)');
export const isPortrait = createMediaQuery('(orientation: portrait)');
export const isLandscape = createMediaQuery('(orientation: landscape)');
export const hasHover = createMediaQuery('(hover: hover)');
export const hasTouch = createMediaQuery('(pointer: coarse)');

