/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Context Provider
 *
 * Description:
 * React context provider for AppDimens configuration and design system integration.
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

import React, {createContext, useContext, useEffect, ReactNode} from 'react';
import {appDimens} from '../core/AppDimens';
import {
  AppDimensProviderProps,
  DesignSystem,
  PerformanceConfig,
} from '../types';

interface AppDimensContextValue {
  designSystem?: DesignSystem;
  performanceConfig?: PerformanceConfig;
  appDimens: typeof appDimens;
}

const AppDimensContext = createContext<AppDimensContextValue | undefined>(
  undefined,
);

/**
 * AppDimens provider component
 */
export function AppDimensProvider({
  children,
  designSystem,
  performanceConfig,
}: AppDimensProviderProps) {
  // Configure performance settings
  useEffect(() => {
    if (performanceConfig) {
      appDimens.setGlobalCache(performanceConfig.enableCache);

      if (performanceConfig.enablePerformanceMonitoring) {
        // Enable performance monitoring
        console.log('AppDimens: Performance monitoring enabled');
      }
    }
  }, [performanceConfig]);

  // Configure design system
  useEffect(() => {
    if (designSystem) {
      // Apply design system configuration
      console.log(`AppDimens: Design system "${designSystem.name}" loaded`);
    }
  }, [designSystem]);

  const contextValue: AppDimensContextValue = {
    designSystem,
    performanceConfig,
    appDimens,
  };

  return (
    <AppDimensContext.Provider value={contextValue}>
      {children}
    </AppDimensContext.Provider>
  );
}

/**
 * Hook to use AppDimens context
 */
export function useAppDimensContext(): AppDimensContextValue {
  const context = useContext(AppDimensContext);
  if (context === undefined) {
    throw new Error(
      'useAppDimensContext must be used within an AppDimensProvider',
    );
  }
  return context;
}
