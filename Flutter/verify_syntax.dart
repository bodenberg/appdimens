/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Syntax Verification
 *
 * Description:
 * Simple syntax verification script to test if all imports and classes work correctly.
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

// Test all imports
import 'lib/appdimens.dart';

void main() {
  print('AppDimens Flutter - Syntax Verification');
  print('=====================================');

  // Test basic classes
  print('✓ AppDimens class available: ${AppDimens}');
  print('✓ AppDimensFixed class available: ${AppDimensFixed}');
  print('✓ AppDimensDynamic class available: ${AppDimensDynamic}');
  print('✓ AppDimensPhysicalUnits class available: ${AppDimensPhysicalUnits}');

  // Test enums
  print('✓ DeviceType enum available: ${DeviceType.values.isNotEmpty}');
  print('✓ UiModeType enum available: ${UiModeType.values.isNotEmpty}');
  print('✓ DpQualifier enum available: ${DpQualifier.values.isNotEmpty}');
  print(
      '✓ ScreenQualifier enum available: ${ScreenQualifier.values.isNotEmpty}');
  print('✓ ScreenType enum available: ${ScreenType.values.isNotEmpty}');
  print('✓ UnitType enum available: ${UnitType.values.isNotEmpty}');

  // Test extensions
  print('✓ Double extensions available: ${100.0.fx}');
  print('✓ Int extensions available: ${100.fx}');

  print('\n🎉 All syntax checks passed!');
  print('The AppDimens Flutter plugin is ready to use.');
}
