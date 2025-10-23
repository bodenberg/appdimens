/**
 * Author & Developer: Jean Bodenberg
 * 
 * Media Query Observer
 * Tracks media query changes for responsive behavior
 */

import type { MediaFeatures } from '../types';

type MediaQueryCallback = (matches: boolean, query: string) => void;

/**
 * Media Query Observer
 * Efficiently tracks media query changes
 */
export class MediaQueryObserver {
  private mediaQueries = new Map<string, MediaQueryList>();
  private listeners = new Map<string, Set<MediaQueryCallback>>();

  constructor() {
    if (typeof window !== 'undefined') {
      this.initializeCommonQueries();
    }
  }

  /**
   * Initialize common media queries
   */
  private initializeCommonQueries(): void {
    // Prefers color scheme
    this.observe('(prefers-color-scheme: dark)');
    this.observe('(prefers-color-scheme: light)');
    
    // Prefers reduced motion
    this.observe('(prefers-reduced-motion: reduce)');
    
    // Hover capability
    this.observe('(hover: hover)');
    this.observe('(hover: none)');
    
    // Pointer type
    this.observe('(pointer: fine)');
    this.observe('(pointer: coarse)');
    this.observe('(pointer: none)');
    
    // Orientation
    this.observe('(orientation: portrait)');
    this.observe('(orientation: landscape)');
    
    // Display mode (PWA)
    this.observe('(display-mode: standalone)');
    this.observe('(display-mode: fullscreen)');
  }

  /**
   * Observe a media query
   */
  observe(query: string, callback?: MediaQueryCallback): () => void {
    // Create MediaQueryList if it doesn't exist
    if (!this.mediaQueries.has(query)) {
      const mql = window.matchMedia(query);
      this.mediaQueries.set(query, mql);
      this.listeners.set(query, new Set());

      // Add listener to MediaQueryList
      const handleChange = (e: MediaQueryListEvent) => {
        this.notifyListeners(query, e.matches);
      };

      // Modern browsers
      if (mql.addEventListener) {
        mql.addEventListener('change', handleChange);
      } else {
        // Legacy browsers
        mql.addListener(handleChange);
      }
    }

    // Add callback if provided
    if (callback) {
      const listeners = this.listeners.get(query)!;
      listeners.add(callback);

      // Immediately call with current state
      const mql = this.mediaQueries.get(query)!;
      callback(mql.matches, query);

      // Return unsubscribe function
      return () => {
        listeners.delete(callback);
      };
    }

    return () => {};
  }

  /**
   * Check if a media query matches
   */
  matches(query: string): boolean {
    const mql = this.mediaQueries.get(query);
    return mql ? mql.matches : window.matchMedia(query).matches;
  }

  /**
   * Get media features
   */
  getMediaFeatures(): MediaFeatures {
    return {
      prefers: this.getPreference(),
      orientation: this.getOrientation(),
      hover: this.getHoverCapability(),
      pointer: this.getPointerType(),
      displayMode: this.getDisplayMode()
    };
  }

  /**
   * Get color scheme preference
   */
  private getPreference(): 'light' | 'dark' | 'reduce-motion' | 'high-contrast' {
    if (this.matches('(prefers-color-scheme: dark)')) return 'dark';
    if (this.matches('(prefers-reduced-motion: reduce)')) return 'reduce-motion';
    if (this.matches('(prefers-contrast: high)')) return 'high-contrast';
    return 'light';
  }

  /**
   * Get orientation
   */
  private getOrientation(): 'portrait' | 'landscape' {
    return this.matches('(orientation: portrait)') ? 'portrait' : 'landscape';
  }

  /**
   * Get hover capability
   */
  private getHoverCapability(): 'none' | 'hover' {
    return this.matches('(hover: hover)') ? 'hover' : 'none';
  }

  /**
   * Get pointer type
   */
  private getPointerType(): 'none' | 'coarse' | 'fine' {
    if (this.matches('(pointer: fine)')) return 'fine';
    if (this.matches('(pointer: coarse)')) return 'coarse';
    return 'none';
  }

  /**
   * Get display mode (PWA)
   */
  private getDisplayMode(): 'fullscreen' | 'standalone' | 'minimal-ui' | 'browser' {
    if (this.matches('(display-mode: fullscreen)')) return 'fullscreen';
    if (this.matches('(display-mode: standalone)')) return 'standalone';
    if (this.matches('(display-mode: minimal-ui)')) return 'minimal-ui';
    return 'browser';
  }

  /**
   * Notify listeners for a query
   */
  private notifyListeners(query: string, matches: boolean): void {
    const listeners = this.listeners.get(query);
    if (!listeners) return;

    listeners.forEach(callback => {
      try {
        callback(matches, query);
      } catch (error) {
        console.error('Error in media query callback:', error);
      }
    });
  }

  /**
   * Cleanup
   */
  destroy(): void {
    this.mediaQueries.clear();
    this.listeners.clear();
  }
}

/**
 * Global media query observer instance
 */
export const globalMediaQueryObserver = new MediaQueryObserver();

