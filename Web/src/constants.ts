/**
 * Author & Developer: Jean Bodenberg
 * Based on: AppDimens Android Library
 * 
 * Constants for dimension calculations
 * Matching the Android implementation
 */

/**
 * Base Dp scaling factor (no adjustment)
 */
export const BASE_DP_FACTOR = 1.0;

/**
 * Base reference width for adjustment calculation (300dp)
 */
export const BASE_WIDTH_DP = 300;

/**
 * Base reference height
 */
export const BASE_HEIGHT_DP = 300;

/**
 * Increment step size for calculating adjustment (30dp)
 */
export const INCREMENT_DP_STEP = 30;

/**
 * Circumference factor (2π)
 */
export const CIRCUMFERENCE_FACTOR = Math.PI * 2.0;

/**
 * Reference aspect ratio (16:9 = 1.78)
 */
export const REFERENCE_AR = 1.78;

/**
 * Default sensitivity coefficient
 * Controls how aggressive scaling is on extreme screens
 */
export const DEFAULT_SENSITIVITY_K = 0.08;

/**
 * Default increment factor (without aspect ratio)
 */
export const BASE_INCREMENT = 0.10;

/**
 * Default breakpoints (matching common device sizes)
 */
export const DEFAULT_BREAKPOINTS = {
  xs: 0,
  sm: 640,
  md: 768,
  lg: 1024,
  xl: 1280,
  '2xl': 1536,
  '3xl': 1920,
  '4xl': 2560
};

/**
 * Physical unit conversion factors
 */
export const MM_TO_CM_FACTOR = 10.0;
export const MM_TO_INCH_FACTOR = 25.4;
export const CM_TO_INCH_FACTOR = 2.54;

/**
 * Default DPI for calculations
 */
export const DEFAULT_DPI = 96;

/**
 * Performance thresholds
 */
export const SLOW_CALCULATION_THRESHOLD = 16; // 1 frame @ 60fps
export const CACHE_CLEANUP_INTERVAL = 60000; // 1 minute
export const MAX_CACHE_SIZE = 1000;

/**
 * Debug overlay z-index
 */
export const DEBUG_OVERLAY_Z_INDEX = 999999;

