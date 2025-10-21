/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - React Hook
 *
 * Description:
 * React hook for using AppDimens in React Native components.
 * Provides easy access to responsive dimension calculations.
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

import {useMemo, useCallback, useEffect, useState} from 'react';
import {Dimensions} from 'react-native';
import {appDimens} from '../core/AppDimens';
import {UseAppDimensReturn, DeviceInfo, ScreenDimensions} from '../types';

/**
 * Hook for using AppDimens in React Native components
 */
export function useAppDimens(): UseAppDimensReturn {
  const [screenDimensions, setScreenDimensions] = useState<ScreenDimensions>(
    () => appDimens.getScreenDimensions(),
  );
  const [deviceInfo, setDeviceInfo] = useState<DeviceInfo>(() =>
    appDimens.getDeviceInfo(),
  );

  // Update dimensions when screen changes
  useEffect(() => {
    const subscription = Dimensions.addEventListener('change', () => {
      setScreenDimensions(appDimens.getScreenDimensions());
      setDeviceInfo(appDimens.getDeviceInfo());
    });

    return () => subscription?.remove();
  }, []);

  // Memoized fixed dimension builder
  const fx = useCallback((value: number) => {
    return appDimens.fixed(value);
  }, []);

  // Memoized dynamic dimension builder
  const dy = useCallback((value: number) => {
    return appDimens.dynamic(value);
  }, []);

  // Memoized percentage calculator
  const percentage = useCallback((value: number) => {
    return appDimens.percentage(value);
  }, []);

  // Memoized item count calculator
  const calculateAvailableItemCount = useCallback(
    (containerSize: number, itemSize: number, itemMargin: number) => {
      return appDimens.calculateAvailableItemCount(
        containerSize,
        itemSize,
        itemMargin,
      );
    },
    [],
  );

  // Memoized physical unit converters
  const mm = useCallback((value: number) => appDimens.mm(value), []);
  const cm = useCallback((value: number) => appDimens.cm(value), []);
  const inch = useCallback((value: number) => appDimens.inch(value), []);

  // Memoized cache control
  const clearCache = useCallback(() => {
    appDimens.clearAllCaches();
  }, []);

  const isCacheEnabled = useMemo(() => {
    return appDimens.isGlobalCacheEnabled();
  }, []);

  return useMemo(
    () => ({
      fx,
      dy,
      percentage,
      deviceInfo,
      screenDimensions,
      calculateAvailableItemCount,
      mm,
      cm,
      inch,
      clearCache,
      isCacheEnabled,
    }),
    [
      fx,
      dy,
      percentage,
      deviceInfo,
      screenDimensions,
      calculateAvailableItemCount,
      mm,
      cm,
      inch,
      clearCache,
      isCacheEnabled,
    ],
  );
}
