/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens React Native - useFluid Hook
 *
 * Description:
 * React hook for fluid (clamp-like) scaling that automatically updates
 * when screen dimensions change.
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

import {useEffect, useState} from 'react';
import {Dimensions, ScaledSize} from 'react-native';
import {AppDimensFluid} from '../core/AppDimensFluid';
import {DeviceType} from '../types';

/**
 * Hook for fluid dimension that automatically updates on screen size changes
 *
 * @param minValue - Minimum value
 * @param maxValue - Maximum value
 * @param minWidth - Minimum screen width breakpoint (default: 320)
 * @param maxWidth - Maximum screen width breakpoint (default: 768)
 * @param deviceType - Optional device type for qualifier resolution
 * @returns Current fluid value
 *
 * @example
 * ```tsx
 * function MyComponent() {
 *   const fontSize = useFluid(16, 24);
 *   const padding = useFluid(8, 16, 320, 768);
 *
 *   return (
 *     <Text style={{ fontSize, padding }}>
 *       Responsive Text
 *     </Text>
 *   );
 * }
 * ```
 */
export function useFluid(
  minValue: number,
  maxValue: number,
  minWidth: number = 320,
  maxWidth: number = 768,
  deviceType?: DeviceType,
): number {
  const calculateValue = () => {
    const fluid = new AppDimensFluid(minValue, maxValue, minWidth, maxWidth);
    return fluid.calculate(undefined, deviceType);
  };

  const [value, setValue] = useState(calculateValue);

  useEffect(() => {
    const subscription = Dimensions.addEventListener(
      'change',
      ({window}: {window: ScaledSize}) => {
        const fluid = new AppDimensFluid(
          minValue,
          maxValue,
          minWidth,
          maxWidth,
        );
        setValue(fluid.calculate(window.width, deviceType));
      },
    );

    return () => {
      subscription?.remove();
    };
  }, [minValue, maxValue, minWidth, maxWidth, deviceType]);

  return value;
}

/**
 * Hook for creating a fluid dimension builder with qualifiers
 *
 * @param minValue - Minimum value
 * @param maxValue - Maximum value
 * @param minWidth - Minimum screen width breakpoint (default: 320)
 * @param maxWidth - Maximum screen width breakpoint (default: 768)
 * @returns Tuple of [current value, AppDimensFluid instance]
 *
 * @example
 * ```tsx
 * function MyComponent() {
 *   const [fontSize, fluidBuilder] = useFluidBuilder(16, 24);
 *
 *   // Access builder methods
 *   const min = fluidBuilder.getMin();
 *   const max = fluidBuilder.getMax();
 *
 *   return (
 *     <Text style={{ fontSize }}>
 *       Font size between {min} and {max}
 *     </Text>
 *   );
 * }
 * ```
 */
export function useFluidBuilder(
  minValue: number,
  maxValue: number,
  minWidth: number = 320,
  maxWidth: number = 768,
): [number, AppDimensFluid] {
  const [fluid] = useState(
    () => new AppDimensFluid(minValue, maxValue, minWidth, maxWidth),
  );

  const [value, setValue] = useState(() => fluid.calculate());

  useEffect(() => {
    const subscription = Dimensions.addEventListener(
      'change',
      ({window}: {window: ScaledSize}) => {
        setValue(fluid.calculate(window.width));
      },
    );

    return () => {
      subscription?.remove();
    };
  }, [fluid]);

  return [value, fluid];
}

/**
 * Hook for multiple fluid values with shared breakpoints
 *
 * @param values - Array of [minValue, maxValue] pairs
 * @param minWidth - Minimum screen width breakpoint (default: 320)
 * @param maxWidth - Maximum screen width breakpoint (default: 768)
 * @returns Array of calculated fluid values
 *
 * @example
 * ```tsx
 * function MyComponent() {
 *   const [fontSize, padding, margin] = useFluidMultiple([
 *     [14, 20],  // fontSize: 14-20
 *     [8, 16],   // padding: 8-16
 *     [12, 24],  // margin: 12-24
 *   ]);
 *
 *   return (
 *     <View style={{ padding, margin }}>
 *       <Text style={{ fontSize }}>Responsive Component</Text>
 *     </View>
 *   );
 * }
 * ```
 */
export function useFluidMultiple(
  values: [number, number][],
  minWidth: number = 320,
  maxWidth: number = 768,
): number[] {
  const calculateValues = () => {
    const width = Dimensions.get('window').width;
    return values.map(([min, max]) => {
      const fluid = new AppDimensFluid(min, max, minWidth, maxWidth);
      return fluid.calculate(width);
    });
  };

  const [calculatedValues, setCalculatedValues] = useState(calculateValues);

  useEffect(() => {
    const subscription = Dimensions.addEventListener(
      'change',
      ({window}: {window: ScaledSize}) => {
        const newValues = values.map(([min, max]) => {
          const fluid = new AppDimensFluid(min, max, minWidth, maxWidth);
          return fluid.calculate(window.width);
        });
        setCalculatedValues(newValues);
      },
    );

    return () => {
      subscription?.remove();
    };
  }, [values, minWidth, maxWidth]);

  return calculatedValues;
}
