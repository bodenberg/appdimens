/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens Flutter - Base Orientation Tests
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

import 'package:flutter_test/flutter_test.dart';
import 'package:appdimens/appdimens.dart';

void main() {
  group('BaseOrientation Tests', () {
    test('AUTO orientation should not invert', () {
      final result = OrientationResolver.resolveWithDimensions(
        requestedType: ScreenType.lowest,
        baseOrientation: BaseOrientation.auto,
        width: 360,
        height: 800,
      );

      expect(result, ScreenType.lowest);
    });

    test('Portrait design in portrait should not invert', () {
      final result = OrientationResolver.resolveWithDimensions(
        requestedType: ScreenType.lowest,
        baseOrientation: BaseOrientation.portrait,
        width: 360,
        height: 800,
      );

      expect(result, ScreenType.lowest);
    });

    test('Portrait design in landscape should invert', () {
      final resultLowest = OrientationResolver.resolveWithDimensions(
        requestedType: ScreenType.lowest,
        baseOrientation: BaseOrientation.portrait,
        width: 800,
        height: 360,
      );

      expect(resultLowest, ScreenType.highest);

      final resultHighest = OrientationResolver.resolveWithDimensions(
        requestedType: ScreenType.highest,
        baseOrientation: BaseOrientation.portrait,
        width: 800,
        height: 360,
      );

      expect(resultHighest, ScreenType.lowest);
    });

    test('Landscape design in landscape should not invert', () {
      final result = OrientationResolver.resolveWithDimensions(
        requestedType: ScreenType.highest,
        baseOrientation: BaseOrientation.landscape,
        width: 800,
        height: 360,
      );

      expect(result, ScreenType.highest);
    });

    test('Landscape design in portrait should invert', () {
      final result = OrientationResolver.resolveWithDimensions(
        requestedType: ScreenType.lowest,
        baseOrientation: BaseOrientation.landscape,
        width: 360,
        height: 800,
      );

      expect(result, ScreenType.highest);
    });
  });
}
