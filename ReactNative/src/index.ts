/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Main Export
 *
 * Description:
 * Main export file for AppDimens React Native library.
 * Provides all public APIs for responsive dimension management.
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

// Core classes
export {AppDimens, appDimens} from './core/AppDimens';
export {AppDimensFixed} from './core/AppDimensFixed';
export {AppDimensDynamic} from './core/AppDimensDynamic';
export {AppDimensAdjustmentFactors} from './core/AppDimensAdjustmentFactors';

// React hooks
export {useAppDimens} from './hooks/useAppDimens';

// Context providers
export {
  AppDimensProvider,
  useAppDimensContext,
} from './context/AppDimensProvider';

// Utilities
export {AppDimensUtils} from './utils/AppDimensUtils';

// Types
export * from './types';

// Version
export const VERSION = '1.0.7';
