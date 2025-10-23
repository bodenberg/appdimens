/**
 * Author & Developer: Jean Bodenberg
 * 
 * Angular Integration for Web
 * Services and directives for Angular applications
 */

import { Injectable, Directive, ElementRef, Input, OnInit, OnDestroy } from '@angular/core';
import { BehaviorSubject, Observable, fromEvent, merge } from 'rxjs';
import { distinctUntilChanged, debounceTime, map } from 'rxjs/operators';
import type { ViewportDimensions, MediaFeatures } from '../types';
import { webdimens } from '../core/WebDimens';
import { globalViewportObserver } from '../observers/ViewportObserver';
import { globalMediaQueryObserver } from '../observers/MediaQueryObserver';
import { breakpointManager } from '../breakpoints/Breakpoints';

/**
 * Service: WebDimensService
 * Main Angular service for Web
 */
@Injectable({
  providedIn: 'root'
})
export class WebDimensService {
  private viewportSubject = new BehaviorSubject<ViewportDimensions>(
    globalViewportObserver.getDimensions()
  );
  
  private mediaFeaturesSubject = new BehaviorSubject<MediaFeatures>(
    globalMediaQueryObserver.getMediaFeatures()
  );

  public viewport$ = this.viewportSubject.asObservable();
  public mediaFeatures$ = this.mediaFeaturesSubject.asObservable();
  
  public breakpoint$ = this.viewport$.pipe(
    map(() => ({
      current: breakpointManager.current,
      is: (bp: string) => breakpointManager.is(bp),
      above: (bp: string) => breakpointManager.above(bp),
      below: (bp: string) => breakpointManager.below(bp),
      between: (min: string, max: string) => breakpointManager.between(min, max)
    })),
    distinctUntilChanged((a, b) => a.current === b.current)
  );

  constructor() {
    // Subscribe to viewport changes
    globalViewportObserver.subscribe((dimensions) => {
      this.viewportSubject.next(dimensions);
    });

    // Subscribe to media features changes
    const queries = [
      '(prefers-color-scheme: dark)',
      '(prefers-reduced-motion: reduce)',
      '(orientation: portrait)'
    ];

    queries.forEach(query => {
      globalMediaQueryObserver.observe(query, () => {
        this.mediaFeaturesSubject.next(
          globalMediaQueryObserver.getMediaFeatures()
        );
      });
    });

    // Enable CSS variables
    webdimens.enableCSSVars();
  }

  /**
   * Get Web instance
   */
  get dimens() {
    return webdimens;
  }

  /**
   * Create fixed dimension
   */
  fixed(value: number) {
    return webdimens.fixed(value);
  }

  /**
   * Create dynamic dimension
   */
  dynamic(value: number) {
    return webdimens.dynamic(value);
  }

  /**
   * Create fluid dimension
   */
  fluid(min: number, max: number, minBp?: string, maxBp?: string) {
    return webdimens.fluid(min, max, minBp, maxBp);
  }

  /**
   * Get percentage
   */
  percent(percentage: number, type?: 'width' | 'height') {
    return webdimens.percent(percentage, type);
  }

  /**
   * Get safe area
   */
  safeArea(side: 'top' | 'right' | 'bottom' | 'left', fallback?: number) {
    return webdimens.safeArea(side, fallback);
  }

  /**
   * Create media query observable
   */
  mediaQuery(query: string): Observable<boolean> {
    return new Observable(subscriber => {
      subscriber.next(globalMediaQueryObserver.matches(query));
      
      const unsubscribe = globalMediaQueryObserver.observe(query, (matches) => {
        subscriber.next(matches);
      });

      return unsubscribe;
    });
  }

  /**
   * Get current viewport dimensions
   */
  getViewport(): ViewportDimensions {
    return this.viewportSubject.value;
  }

  /**
   * Get current media features
   */
  getMediaFeatures(): MediaFeatures {
    return this.mediaFeaturesSubject.value;
  }

  /**
   * Get current breakpoint
   */
  getCurrentBreakpoint(): string {
    return breakpointManager.current;
  }

  /**
   * Check if current breakpoint matches
   */
  isBreakpoint(bp: string): boolean {
    return breakpointManager.is(bp);
  }

  /**
   * Check if current breakpoint is above
   */
  isAbove(bp: string): boolean {
    return breakpointManager.above(bp);
  }

  /**
   * Check if current breakpoint is below
   */
  isBelow(bp: string): boolean {
    return breakpointManager.below(bp);
  }

  /**
   * Check if current breakpoint is between
   */
  isBetween(min: string, max: string): boolean {
    return breakpointManager.between(min, max);
  }
}

/**
 * Directive: wdFixed
 * Apply fixed dimension to element
 */
@Directive({
  selector: '[wdFixed]'
})
export class WdFixedDirective implements OnInit {
  @Input() wdFixed!: number;
  @Input() wdProperty: 'width' | 'height' | 'padding' | 'margin' = 'width';

  constructor(private el: ElementRef) {}

  ngOnInit() {
    const value = webdimens.fixed(this.wdFixed).toPx();
    this.el.nativeElement.style[this.wdProperty] = value;
  }
}

/**
 * Directive: wdDynamic
 * Apply dynamic dimension to element
 */
@Directive({
  selector: '[wdDynamic]'
})
export class WdDynamicDirective implements OnInit {
  @Input() wdDynamic!: number;
  @Input() wdProperty: 'width' | 'height' | 'padding' | 'margin' = 'width';

  constructor(private el: ElementRef) {}

  ngOnInit() {
    const value = webdimens.dynamic(this.wdDynamic).toPx();
    this.el.nativeElement.style[this.wdProperty] = value;
  }
}

/**
 * Directive: wdFluid
 * Apply fluid dimension to element
 */
@Directive({
  selector: '[wdFluid]'
})
export class WdFluidDirective implements OnInit {
  @Input() wdFluid!: { min: number; max: number; minBp?: string; maxBp?: string };
  @Input() wdProperty: 'width' | 'height' | 'fontSize' = 'fontSize';

  constructor(private el: ElementRef) {}

  ngOnInit() {
    const { min, max, minBp, maxBp } = this.wdFluid;
    const value = webdimens.fluid(min, max, minBp, maxBp).toString();
    this.el.nativeElement.style[this.wdProperty] = value;
  }
}

/**
 * Directive: wdBreakpoint
 * Show/hide element based on breakpoint
 */
@Directive({
  selector: '[wdBreakpoint]'
})
export class WdBreakpointDirective implements OnInit, OnDestroy {
  @Input() wdBreakpoint!: string;
  @Input() wdMode: 'above' | 'below' | 'is' | 'between' = 'is';
  @Input() wdMax?: string; // For 'between' mode

  private unsubscribe?: () => void;

  constructor(
    private el: ElementRef,
    private service: WebDimensService
  ) {}

  ngOnInit() {
    this.updateVisibility();
    
    this.unsubscribe = globalViewportObserver.subscribe(() => {
      this.updateVisibility();
    });
  }

  ngOnDestroy() {
    if (this.unsubscribe) {
      this.unsubscribe();
    }
  }

  private updateVisibility() {
    let shouldShow = false;

    switch (this.wdMode) {
      case 'is':
        shouldShow = this.service.isBreakpoint(this.wdBreakpoint);
        break;
      case 'above':
        shouldShow = this.service.isAbove(this.wdBreakpoint);
        break;
      case 'below':
        shouldShow = this.service.isBelow(this.wdBreakpoint);
        break;
      case 'between':
        if (this.wdMax) {
          shouldShow = this.service.isBetween(this.wdBreakpoint, this.wdMax);
        }
        break;
    }

    this.el.nativeElement.style.display = shouldShow ? '' : 'none';
  }
}

/**
 * Module: WebDimensModule
 * Angular module for Web
 */
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [CommonModule],
  declarations: [
    WdFixedDirective,
    WdDynamicDirective,
    WdFluidDirective,
    WdBreakpointDirective
  ],
  exports: [
    WdFixedDirective,
    WdDynamicDirective,
    WdFluidDirective,
    WdBreakpointDirective
  ],
  providers: [WebDimensService]
})
export class WebDimensModule {}

// Export all
export { webdimens };

