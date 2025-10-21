/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts logical pixels, scalable pixels, and physical units in a responsive 
 * and mathematically refined way, ensuring layout consistency across any 
 * screen size or ratio.
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

library appdimens;

// Core exports
export 'src/appdimens.dart';
export 'src/appdimens_fixed.dart';
export 'src/appdimens_dynamic.dart';
export 'src/appdimens_types.dart';
export 'src/appdimens_extensions.dart';
export 'src/appdimens_provider.dart';
export 'src/appdimens_physical_units.dart';
export 'src/appdimens_utils.dart';

// Version information
const String appDimensVersion = '1.0.7';
const String appDimensDescription = 'Smart and Responsive Dimensioning for Flutter';
