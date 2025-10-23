/**
 * Author & Developer: Jean Bodenberg
 * Based on: AppDimens Android Library
 * Date: 2025
 * 
 * WebDimens - Advanced Responsive Dimension System for Web
 * 
 * Licensed under the Apache License, Version 2.0
 */

/**
 * Screen dimension types
 */
export enum ScreenType {
  HIGHEST = 'highest',
  LOWEST = 'lowest',
  WIDTH = 'width',
  HEIGHT = 'height'
}

/**
 * Size qualifier types based on screen dimensions
 */
export enum SizeQualifier {
  MIN_WIDTH = 'min-width',
  MAX_WIDTH = 'max-width',
  MIN_HEIGHT = 'min-height',
  MAX_HEIGHT = 'max-height'
}

/**
 * Device mode types
 */
export enum DeviceMode {
  MOBILE = 'mobile',
  TABLET = 'tablet',
  DESKTOP = 'desktop',
  TV = 'tv',
  WATCH = 'watch',
  AUTO = 'auto'
}

/**
 * Unit types for conversions
 */
export enum UnitType {
  PX = 'px',
  REM = 'rem',
  EM = 'em',
  VW = 'vw',
  VH = 'vh',
  VMIN = 'vmin',
  VMAX = 'vmax',
  PERCENT = '%',
  MM = 'mm',
  CM = 'cm',
  INCH = 'in'
}

/**
 * Viewport dimensions
 */
export interface ViewportDimensions {
  width: number;
  height: number;
  aspectRatio: number;
  orientation: 'portrait' | 'landscape';
  dpr: number;
}

/**
 * Size qualifier entry
 */
export interface SizeQualifierEntry {
  type: SizeQualifier;
  value: number;
}

/**
 * Device mode qualifier entry
 */
export interface DeviceModeQualifierEntry {
  deviceMode: DeviceMode;
  sizeQualifier: SizeQualifierEntry;
}

/**
 * Breakpoint configuration
 */
export interface Breakpoints {
  [key: string]: number;
}

/**
 * WebDimens configuration
 */
export interface WebDimensConfig {
  baseWidth?: number;
  baseHeight?: number;
  referenceAR?: number;
  defaultSensitivity?: number;
  baseIncrement?: number;
  breakpoints?: Breakpoints;
  enableCache?: boolean;
  debug?: boolean;
  autoUpdateCSSVars?: boolean;
}

/**
 * Cache entry structure
 */
export interface CacheEntry<T = any> {
  value: T;
  dependencies: Set<any>;
  timestamp: number;
  accessCount: number;
}

/**
 * Cache statistics
 */
export interface CacheStats {
  totalEntries: number;
  totalAccesses: number;
  cacheHits: number;
  cacheMisses: number;
  hitRate: number;
  avgCalculationTime: number;
  memoryUsage: number;
}

/**
 * Performance metrics
 */
export interface PerformanceMetrics {
  calculations: number;
  cacheHits: number;
  cacheMisses: number;
  avgCalculationTime: number;
  slowCalculations: number;
}

/**
 * Dimension value with unit
 */
export interface DimensValue<U extends UnitType = UnitType.PX> {
  value: number;
  unit: U;
  toString(): string;
}

/**
 * Media feature preferences
 */
export interface MediaFeatures {
  prefers?: 'light' | 'dark' | 'reduce-motion' | 'high-contrast';
  orientation?: 'portrait' | 'landscape';
  hover?: 'none' | 'hover';
  pointer?: 'none' | 'coarse' | 'fine';
  displayMode?: 'fullscreen' | 'standalone' | 'minimal-ui' | 'browser';
}

/**
 * Screen adjustment factors (matching Android implementation)
 */
export interface ScreenAdjustmentFactors {
  withArFactorLowest: number;
  withArFactorHighest: number;
  withoutArFactor: number;
  adjustmentFactorLowest: number;
  adjustmentFactorHighest: number;
}

