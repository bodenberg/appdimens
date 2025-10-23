/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Tests
 *
 * Description:
 * Unit tests for AppDimens Flutter plugin.
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

import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:appdimens/appdimens.dart';

void main() {
  group('AppDimens Tests', () {
    testWidgets('AppDimensFixed should calculate dimensions correctly', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              final fixedDimension = AppDimens.fixed(100).calculate(context);
              expect(fixedDimension, isA<double>());
              expect(fixedDimension, greaterThan(0));
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('AppDimensDynamic should calculate dimensions correctly', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              final dynamicDimension = AppDimens.dynamic(100).calculate(context);
              expect(dynamicDimension, isA<double>());
              expect(dynamicDimension, greaterThan(0));
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('Extensions should work correctly', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              // Test double extensions
              expect(100.0.fx, isA<AppDimensFixed>());
              expect(100.0.dy, isA<AppDimensDynamic>());
              
              // Test int extensions
              expect(100.fx, isA<AppDimensFixed>());
              expect(100.dy, isA<AppDimensDynamic>());
              
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('Custom values should work correctly', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              final dimension = AppDimens.fixed(100)
                  .deviceType(DeviceType.tablet, 200)
                  .uiMode(UiModeType.carPlay, 150)
                  .calculate(context);
              
              expect(dimension, isA<double>());
              expect(dimension, greaterThan(0));
              
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('Physical units should work correctly', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              final mmToPixels = AppDimensPhysicalUnits.toPxFromMm(25.4, context);
              final cmToPixels = AppDimensPhysicalUnits.toPxFromCm(2.54, context);
              final inchesToPixels = AppDimensPhysicalUnits.toPxFromInch(1.0, context);
              
              expect(mmToPixels, isA<double>());
              expect(cmToPixels, isA<double>());
              expect(inchesToPixels, isA<double>());
              
              expect(mmToPixels, greaterThan(0));
              expect(cmToPixels, greaterThan(0));
              expect(inchesToPixels, greaterThan(0));
              
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('Cache should work correctly', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              final dimension1 = AppDimens.fixed(100).calculate(context);
              final dimension2 = AppDimens.fixed(100).calculate(context);
              
              expect(dimension1, equals(dimension2));
              
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('Screen info should be available', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              final screenInfo = AppDimens.getCurrentScreenInfo(context);
              
              expect(screenInfo, isA<ScreenInfo>());
              expect(screenInfo.width, greaterThan(0));
              expect(screenInfo.height, greaterThan(0));
              expect(screenInfo.deviceType, isA<DeviceType>());
              expect(screenInfo.uiModeType, isA<UiModeType>());
              
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('Adjustment factors should be calculated', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              final factors = AppDimens.calculateAdjustmentFactors(context);
              
              expect(factors, isA<ScreenAdjustmentFactors>());
              expect(factors.adjustmentFactorLowest, greaterThan(0));
              expect(factors.adjustmentFactorHighest, greaterThan(0));
              expect(factors.withArFactorLowest, greaterThan(0));
              expect(factors.withArFactorHighest, greaterThan(0));
              expect(factors.withoutArFactor, greaterThan(0));
              
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('Dynamic percentage should work correctly', (WidgetTester tester) async {
      await tester.pumpWidget(
        MaterialApp(
          home: Builder(
            builder: (context) {
              final percentage = AppDimens.dynamicPercentage(0.5, context);
              
              expect(percentage, isA<double>());
              expect(percentage, greaterThan(0));
              
              return Container();
            },
          ),
        ),
      );
    });

    testWidgets('Provider should work correctly', (WidgetTester tester) async {
      await tester.pumpWidget(
        AppDimensApp(
          config: const AppDimensConfig(
            aspectRatioEnabled: true,
            cacheEnabled: true,
            ignoreMultiWindowAdjustment: false,
          ),
          child: MaterialApp(
            home: Builder(
              builder: (context) {
                final config = context.appDimensConfig;
                
                expect(config.aspectRatioEnabled, isTrue);
                expect(config.cacheEnabled, isTrue);
                expect(config.ignoreMultiWindowAdjustment, isFalse);
                
                return Container();
              },
            ),
          ),
        ),
      );
    });
  });
}
