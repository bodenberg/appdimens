/**
 * Author & Developer: Jean Bodenberg
 * 
 * Vue 3 Integration for Web
 * Composables and utilities for Vue 3 applications
 */

import { ref, computed, onMounted, onUnmounted, readonly, type Ref, type ComputedRef } from 'vue';
import type { ViewportDimensions, MediaFeatures } from '../types';
import { webdimens } from '../core/WebDimens';
import { globalViewportObserver } from '../observers/ViewportObserver';
import { globalMediaQueryObserver } from '../observers/MediaQueryObserver';
import { breakpointManager } from '../breakpoints/Breakpoints';

/**
 * Composable: useWebDimens
 * Main composable for accessing Web functions
 */
export function useWebDimens() {
  return {
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
  };
}

/**
 * Composable: useViewport
 * Reactive viewport dimensions
 */
export function useViewport(): Readonly<Ref<ViewportDimensions>> {
  const viewport = ref<ViewportDimensions>(
    globalViewportObserver.getDimensions()
  );

  onMounted(() => {
    const unsubscribe = globalViewportObserver.subscribe((dimensions) => {
      viewport.value = dimensions;
    });

    onUnmounted(() => {
      unsubscribe();
    });
  });

  return readonly(viewport);
}

/**
 * Composable: useBreakpoint
 * Get current breakpoint and check functions
 */
export function useBreakpoint() {
  const viewport = useViewport();
  
  const current = computed(() => breakpointManager.current);

  const is = (breakpoint: string) => {
    return breakpointManager.is(breakpoint);
  };

  const above = (breakpoint: string) => {
    return breakpointManager.above(breakpoint);
  };

  const below = (breakpoint: string) => {
    return breakpointManager.below(breakpoint);
  };

  const between = (min: string, max: string) => {
    return breakpointManager.between(min, max);
  };

  return {
    current,
    is,
    above,
    below,
    between,
    viewport
  };
}

/**
 * Composable: useMediaQuery
 * Reactive media query state
 */
export function useMediaQuery(query: string): Readonly<Ref<boolean>> {
  const matches = ref<boolean>(
    globalMediaQueryObserver.matches(query)
  );

  onMounted(() => {
    const unsubscribe = globalMediaQueryObserver.observe(query, (isMatching) => {
      matches.value = isMatching;
    });

    onUnmounted(() => {
      unsubscribe();
    });
  });

  return readonly(matches);
}

/**
 * Composable: useMediaFeatures
 * Get current media features reactively
 */
export function useMediaFeatures(): Readonly<Ref<MediaFeatures>> {
  const features = ref<MediaFeatures>(
    globalMediaQueryObserver.getMediaFeatures()
  );

  onMounted(() => {
    const unsubscribes = [
      globalMediaQueryObserver.observe('(prefers-color-scheme: dark)', () => {
        features.value = globalMediaQueryObserver.getMediaFeatures();
      }),
      globalMediaQueryObserver.observe('(prefers-reduced-motion: reduce)', () => {
        features.value = globalMediaQueryObserver.getMediaFeatures();
      }),
      globalMediaQueryObserver.observe('(orientation: portrait)', () => {
        features.value = globalMediaQueryObserver.getMediaFeatures();
      })
    ];

    onUnmounted(() => {
      unsubscribes.forEach(unsub => unsub());
    });
  });

  return readonly(features);
}

/**
 * Composable: useResponsiveValue
 * Get different values based on breakpoints
 */
export function useResponsiveValue<T>(values: Record<string, T>): ComputedRef<T> {
  const breakpoint = useBreakpoint();
  
  return computed(() => {
    const breakpoints = Object.keys(values).sort((a, b) => {
      return breakpointManager.getValue(b) - breakpointManager.getValue(a);
    });

    for (const bp of breakpoints) {
      if (breakpoint.is(bp) || breakpoint.above(bp)) {
        return values[bp];
      }
    }

    return values[breakpoints[breakpoints.length - 1]];
  });
}

/**
 * Composable: useDimension
 * Reactive dimension that updates on viewport change
 */
export function useDimension(
  value: number,
  type: 'fixed' | 'dynamic' | 'fluid' = 'fixed',
  options?: any
): ComputedRef<string> {
  const viewport = useViewport();

  return computed(() => {
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
 * Composable: useOrientation
 * Get current orientation
 */
export function useOrientation(): ComputedRef<'portrait' | 'landscape'> {
  const viewport = useViewport();
  return computed(() => viewport.value.orientation);
}

/**
 * Composable: useAspectRatio
 * Get current aspect ratio
 */
export function useAspectRatio(): ComputedRef<number> {
  const viewport = useViewport();
  return computed(() => viewport.value.aspectRatio);
}

/**
 * Plugin: WebDimensPlugin
 * Vue plugin for global installation
 */
export const WebDimensPlugin = {
  install(app: any) {
    // Make webdimens available globally
    app.config.globalProperties.$webdimens = webdimens;
    
    // Provide composables globally
    app.provide('webdimens', webdimens);
    
    // Enable CSS variables
    webdimens.enableCSSVars();
  }
};

// Export all
export { webdimens };

