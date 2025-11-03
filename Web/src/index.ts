/**
 * Author & Developer: Jean Bodenberg
 * Based on: AppDimens Android Library
 * 
 * WebDimens - Advanced Responsive Dimension System for Web
 * 
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

// Main class
import { webdimens as webdimensInstance } from './core/WebDimens';
export { AppDimens, webdimens, fixed, fx, dynamic, dy, fluid, fl } from './core/WebDimens';
export { AppDimens as WebDimens } from './core/WebDimens';  // Alias for backward compatibility

// Core classes
export { Fixed } from './core/Fixed';
export { Dynamic } from './core/Dynamic';
export { Fluid } from './core/Fluid';

// v2.0 Unified Builder with 13 strategies
export {
  WebDimensBuilder,
  balanced,
  logarithmic,
  power,
  defaultStrategy,
  percentage,
  fluidStrategy,
  smart,
} from './core/WebDimensBuilder';

// v2.0 Calculator
export * as Calculator from './core/Calculator';

// v2.0 Strategies and Types
export {
  ScalingStrategy,
  PerceptualModel,
  ElementType,
  getStrategyDescription,
  getStrategyRecommendation,
  getStrategyFormula,
} from './types/ScalingStrategy';

// Cache
export { WebDimensCache, globalCache } from './cache/Cache';

// Observers
export { ViewportObserver, globalViewportObserver } from './observers/ViewportObserver';
export { MediaQueryObserver, globalMediaQueryObserver } from './observers/MediaQueryObserver';

// Units
export { PhysicalUnits, physicalUnits } from './units/PhysicalUnits';

// Breakpoints
export { BreakpointManager, breakpointManager } from './breakpoints/Breakpoints';

// Constants
export * from './constants';

// Types
export type {
  ScreenType,
  SizeQualifier,
  DeviceMode,
  UnitType,
  ViewportDimensions,
  SizeQualifierEntry,
  DeviceModeQualifierEntry,
  Breakpoints,
  WebDimensConfig,
  CacheEntry,
  CacheStats,
  PerformanceMetrics,
  DimensValue,
  MediaFeatures,
  ScreenAdjustmentFactors
} from './types';

/**
 * Version
 */
export const VERSION = '2.0.0';

/**
 * Default export
 */
export default webdimensInstance;

