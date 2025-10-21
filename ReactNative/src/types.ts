/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Types
 *
 * Description:
 * Core types and interfaces for AppDimens React Native library.
 * Provides type definitions for responsive dimension management.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// React Native imports removed as they're not used in type definitions

// ============================================================================
// Core AppDimens Types
// ============================================================================

export type DimensionType = 'fixed' | 'dynamic' | 'percentage';

export type DeviceType =
  | 'phone'
  | 'tablet'
  | 'desktop'
  | 'watch'
  | 'tv'
  | 'car';

export type Orientation = 'portrait' | 'landscape';

export type ScreenQualifier = 'smallWidth' | 'width' | 'height';

export type UiModeType = 'normal' | 'car' | 'tv' | 'watch' | 'desktop';

export type PlatformType = 'ios' | 'android' | 'web';

// ============================================================================
// Screen Configuration Types
// ============================================================================

export interface ScreenDimensions {
  width: number;
  height: number;
  scale: number;
  fontScale: number;
  pixelRatio: number;
}

export interface DeviceInfo {
  type: DeviceType;
  platform: PlatformType;
  orientation: Orientation;
  isTablet: boolean;
  isPhone: boolean;
  isDesktop: boolean;
  isWatch: boolean;
  isTV: boolean;
  isCar: boolean;
}

export interface ScreenQualifierEntry {
  type: ScreenQualifier;
  value: number;
  deviceType?: DeviceType;
  uiMode?: UiModeType;
}

export interface UiModeQualifierEntry {
  uiModeType: UiModeType;
  screenQualifierEntry: ScreenQualifierEntry;
}

// ============================================================================
// Dimension Calculation Types
// ============================================================================

export interface AdjustmentFactors {
  adjustmentFactorLowest: number;
  adjustmentFactorHighest: number;
  withArFactorLowest: number;
  withArFactorHighest: number;
  withoutArFactor: number;
}

export interface DimensionConfig {
  initialValue: number;
  screenType: 'lowest' | 'highest';
  applyAspectRatioAdjustment: boolean;
  customSensitivityK?: number;
  ignoreMultiWindowAdjustment: boolean;
  customScreenQualifierMap: Map<ScreenQualifierEntry, number>;
  customUiModeMap: Map<UiModeType, number>;
  customIntersectionMap: Map<UiModeQualifierEntry, number>;
}

export interface CalculatedDimension {
  value: number;
  type: DimensionType;
  originalValue: number;
  scale: number;
  deviceInfo: DeviceInfo;
  screenDimensions: ScreenDimensions;
}

// ============================================================================
// AppDimens Builder Types
// ============================================================================

export interface AppDimensBuilder {
  screen(...args: any[]): this;
  type(type: 'lowest' | 'highest'): this;
  multiWindowAdjustment(ignore?: boolean): this;
  aspectRatio(enable?: boolean, sensitivity?: number): this;
  cache(enable?: boolean): this;
  toPixels(): number;
  toPixelsInt(): number;
  toPoints(): number;
  toPointsInt(): number;
}

// ============================================================================
// Hook Types
// ============================================================================

export interface UseAppDimensReturn {
  // Core methods
  fx: (value: number) => AppDimensBuilder;
  dy: (value: number) => AppDimensBuilder;
  percentage: (value: number) => number;

  // Device info
  deviceInfo: DeviceInfo;
  screenDimensions: ScreenDimensions;

  // Utility methods
  calculateAvailableItemCount: (
    containerSize: number,
    itemSize: number,
    itemMargin: number,
  ) => number;

  // Physical units
  mm: (value: number) => number;
  cm: (value: number) => number;
  inch: (value: number) => number;

  // Performance
  clearCache: () => void;
  isCacheEnabled: boolean;
}

export interface AppDimensProviderProps {
  children: React.ReactNode;
  designSystem?: DesignSystem;
  performanceConfig?: PerformanceConfig;
}

// ============================================================================
// Design System Types
// ============================================================================

export interface DesignSystem {
  name: string;
  version: string;
  description?: string;
  tokens: DesignTokens;
  components: ComponentLibrary;
  rules: ScalingRules;
  themes?: Theme[];
}

export interface DesignTokens {
  spacing: TokenGroup;
  sizing: TokenGroup;
  typography: TypographyTokens;
  colors: ColorTokens;
  borders: BorderTokens;
  shadows: ShadowTokens;
}

export interface TokenGroup {
  [key: string]: TokenValue;
}

export interface TokenValue {
  value: number | string;
  type: DimensionType;
  description?: string;
  category?: string;
}

export interface TypographyTokens {
  fontSizes: TokenGroup;
  fontWeights: TokenGroup;
  lineHeights: TokenGroup;
  letterSpacing: TokenGroup;
  fontFamilies: TokenGroup;
}

export interface ColorTokens {
  primary: TokenGroup;
  secondary: TokenGroup;
  neutral: TokenGroup;
  semantic: TokenGroup;
}

export interface BorderTokens {
  radius: TokenGroup;
  width: TokenGroup;
  style: TokenGroup;
}

export interface ShadowTokens {
  elevation: TokenGroup;
  blur: TokenGroup;
  spread: TokenGroup;
}

export interface ComponentLibrary {
  [componentName: string]: ComponentDefinition;
}

export interface ComponentDefinition {
  name: string;
  description?: string;
  variants: ComponentVariant[];
  properties: ComponentProperty[];
  dimensions: ComponentDimensions;
}

export interface ComponentVariant {
  name: string;
  properties: Record<string, any>;
  dimensions: ComponentDimensions;
}

export interface ComponentProperty {
  name: string;
  type: 'boolean' | 'string' | 'number' | 'color' | 'enum';
  defaultValue: any;
  options?: any[];
}

export interface ComponentDimensions {
  width?: DimensionDefinition;
  height?: DimensionDefinition;
  padding?: DimensionDefinition;
  margin?: DimensionDefinition;
  borderRadius?: DimensionDefinition;
  fontSize?: DimensionDefinition;
}

export interface DimensionDefinition {
  value: number;
  type: DimensionType;
  qualifiers?: ScreenQualifierEntry[];
}

export interface ScalingRules {
  fixed: string[];
  dynamic: string[];
  percentage: string[];
  custom: CustomScalingRule[];
}

export interface CustomScalingRule {
  pattern: string;
  type: DimensionType;
  conditions?: ScalingCondition[];
}

export interface ScalingCondition {
  property: string;
  operator: 'equals' | 'contains' | 'startsWith' | 'endsWith' | 'regex';
  value: string;
}

export interface Theme {
  name: string;
  type: 'light' | 'dark' | 'custom';
  colors: Record<string, Color>;
  overrides?: Record<string, any>;
}

export interface Color {
  r: number;
  g: number;
  b: number;
  a: number;
}

// ============================================================================
// Performance and Cache Types
// ============================================================================

export interface PerformanceConfig {
  enableCache: boolean;
  cacheSize: number;
  enablePerformanceMonitoring: boolean;
  logLevel: 'error' | 'warn' | 'info' | 'debug';
}

export interface CacheEntry<T> {
  key: string;
  data: T;
  timestamp: number;
  ttl: number;
}

export interface PerformanceMetrics {
  calculationTime: number;
  cacheHits: number;
  cacheMisses: number;
  memoryUsage: number;
}

// ============================================================================
// Constants
// ============================================================================

export const BASE_WIDTH_DP = 375; // iPhone 6/7/8 width
export const BASE_HEIGHT_DP = 667; // iPhone 6/7/8 height
export const BASE_DP_FACTOR = 1.0;
export const BASE_INCREMENT = 0.1;
export const REFERENCE_AR = BASE_WIDTH_DP / BASE_HEIGHT_DP; // ~0.56

export const DEVICE_TYPES = {
  PHONE: 'phone' as DeviceType,
  TABLET: 'tablet' as DeviceType,
  DESKTOP: 'desktop' as DeviceType,
  WATCH: 'watch' as DeviceType,
  TV: 'tv' as DeviceType,
  CAR: 'car' as DeviceType,
};

export const UI_MODE_TYPES = {
  NORMAL: 'normal' as UiModeType,
  CAR: 'car' as UiModeType,
  TV: 'tv' as UiModeType,
  WATCH: 'watch' as UiModeType,
  DESKTOP: 'desktop' as UiModeType,
};

export const SCREEN_QUALIFIERS = {
  SMALL_WIDTH: 'smallWidth' as ScreenQualifier,
  WIDTH: 'width' as ScreenQualifier,
  HEIGHT: 'height' as ScreenQualifier,
};
