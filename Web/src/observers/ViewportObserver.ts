/**
 * Author & Developer: Jean Bodenberg
 * 
 * Viewport Observer
 * Uses ResizeObserver for efficient viewport change detection
 */

import type { ViewportDimensions } from '../types';

type ViewportCallback = (dimensions: ViewportDimensions) => void;

/**
 * Viewport Observer
 * Efficiently tracks viewport changes using ResizeObserver
 */
export class ViewportObserver {
  private observers = new Set<ViewportCallback>();
  private resizeObserver: ResizeObserver | null = null;
  private currentDimensions: ViewportDimensions | null = null;
  private debounceTimer: number | null = null;
  private debounceDelay = 100;

  constructor() {
    if (typeof window !== 'undefined') {
      this.init();
    }
  }

  /**
   * Initialize observer
   */
  private init(): void {
    this.currentDimensions = this.getCurrentDimensions();

    // Use ResizeObserver for modern browsers
    if (typeof ResizeObserver !== 'undefined') {
      this.resizeObserver = new ResizeObserver(() => {
        this.handleResize();
      });
      this.resizeObserver.observe(document.documentElement);
    } else if (typeof window !== 'undefined') {
      // Fallback to window resize event
      window.addEventListener('resize', () => this.handleResize());
    }
  }

  /**
   * Handle resize with debouncing
   */
  private handleResize(): void {
    if (this.debounceTimer !== null) {
      clearTimeout(this.debounceTimer);
    }

    this.debounceTimer = window.setTimeout(() => {
      const newDimensions = this.getCurrentDimensions();
      
      // Only notify if dimensions actually changed
      if (this.dimensionsChanged(this.currentDimensions!, newDimensions)) {
        this.currentDimensions = newDimensions;
        this.notify(newDimensions);
      }
    }, this.debounceDelay);
  }

  /**
   * Get current viewport dimensions
   */
  private getCurrentDimensions(): ViewportDimensions {
    const width = window.innerWidth;
    const height = window.innerHeight;
    const aspectRatio = width / height;
    const orientation = width >= height ? 'landscape' : 'portrait';
    const dpr = window.devicePixelRatio || 1;

    return {
      width,
      height,
      aspectRatio,
      orientation,
      dpr
    };
  }

  /**
   * Check if dimensions changed significantly
   */
  private dimensionsChanged(
    old: ViewportDimensions,
    newDims: ViewportDimensions
  ): boolean {
    return (
      old.width !== newDims.width ||
      old.height !== newDims.height ||
      old.orientation !== newDims.orientation
    );
  }

  /**
   * Subscribe to viewport changes
   */
  subscribe(callback: ViewportCallback): () => void {
    this.observers.add(callback);
    
    // Immediately call with current dimensions
    if (this.currentDimensions) {
      callback(this.currentDimensions);
    }

    // Return unsubscribe function
    return () => {
      this.observers.delete(callback);
    };
  }

  /**
   * Notify all observers
   */
  private notify(dimensions: ViewportDimensions): void {
    this.observers.forEach(callback => {
      try {
        callback(dimensions);
      } catch (error) {
        console.error('Error in viewport observer callback:', error);
      }
    });
  }

  /**
   * Get current dimensions
   */
  getDimensions(): ViewportDimensions {
    if (!this.currentDimensions) {
      this.currentDimensions = this.getCurrentDimensions();
    }
    return this.currentDimensions;
  }

  /**
   * Set debounce delay
   */
  setDebounceDelay(delay: number): void {
    this.debounceDelay = delay;
  }

  /**
   * Cleanup
   */
  destroy(): void {
    if (this.resizeObserver) {
      this.resizeObserver.disconnect();
      this.resizeObserver = null;
    }

    if (this.debounceTimer !== null) {
      clearTimeout(this.debounceTimer);
      this.debounceTimer = null;
    }

    this.observers.clear();
  }
}

/**
 * Global viewport observer instance
 */
export const globalViewportObserver = new ViewportObserver();

