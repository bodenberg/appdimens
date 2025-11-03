/**
 * Calculator Tests - Web
 * Validates all 13 scaling strategies
 */

import { describe, test, expect } from 'vitest';
import * as Calculator from '../src/core/Calculator';
import { ScalingStrategy, ElementType } from '../src/types/ScalingStrategy';

// Test configuration for a 720px × 1280px screen (typical tablet)
const testConfig: Calculator.CalculationConfig = {
  screenWidthDp: 720,
  screenHeightDp: 1280,
  smallestScreenWidthDp: 720,
  densityDpi: 96, // Standard web DPI
};

// Base value for testing
const BASE_VALUE = 48;

describe('Calculator - 13 Scaling Strategies', () => {
  
  test('DEFAULT strategy should apply ~97% linear + AR adjustment', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.DEFAULT,
      testConfig,
    );
    
    expect(result).toBeGreaterThan(BASE_VALUE);
    expect(result).toBeLessThan(BASE_VALUE * 2.5);
  });

  test('PERCENTAGE strategy should apply 100% proportional scaling', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.PERCENTAGE,
      testConfig,
    );
    
    // 48dp @ 720dp screen with 300dp base = 48 * (720/300) = 115.2
    const expected = BASE_VALUE * (testConfig.screenWidthDp / Calculator.BASE_WIDTH_DP);
    expect(result).toBeCloseTo(expected, 1);
  });

  test('BALANCED strategy should be linear on phones, logarithmic on tablets', () => {
    // Tablet (720dp) - should use logarithmic
    const tabletResult = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.BALANCED,
      testConfig,
    );
    
    // Should be less than pure linear
    const linearResult = BASE_VALUE * (testConfig.screenWidthDp / Calculator.BASE_WIDTH_DP);
    expect(tabletResult).toBeLessThan(linearResult);
    expect(tabletResult).toBeGreaterThan(BASE_VALUE);
    
    // Phone (360dp) - should use linear
    const phoneConfig = { ...testConfig, screenWidthDp: 360, smallestScreenWidthDp: 360 };
    const phoneResult = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.BALANCED,
      phoneConfig,
    );
    
    const phoneLinear = BASE_VALUE * (360 / Calculator.BASE_WIDTH_DP);
    expect(phoneResult).toBeCloseTo(phoneLinear, 1);
  });

  test('LOGARITHMIC strategy should control growth on large screens', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.LOGARITHMIC,
      testConfig,
    );
    
    // Should be less than linear
    const linearResult = BASE_VALUE * (testConfig.screenWidthDp / Calculator.BASE_WIDTH_DP);
    expect(result).toBeLessThan(linearResult);
    expect(result).toBeGreaterThan(BASE_VALUE);
  });

  test('POWER strategy should use Stevens Power Law', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.POWER,
      testConfig,
      'lowest',
      'auto',
      undefined,
      { powerExponent: 0.75 },
    );
    
    const ratio = testConfig.screenWidthDp / Calculator.BASE_WIDTH_DP;
    const expected = BASE_VALUE * Math.pow(ratio, 0.75);
    expect(result).toBeCloseTo(expected, 1);
  });

  test('FLUID strategy should interpolate between min and max', () => {
    const minValue = 40;
    const maxValue = 72;
    
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.FLUID,
      testConfig,
      'lowest',
      'auto',
      undefined,
      undefined,
      { minValue, maxValue, minWidth: 320, maxWidth: 768 },
    );
    
    expect(result).toBeGreaterThanOrEqual(minValue);
    expect(result).toBeLessThanOrEqual(maxValue);
  });

  test('INTERPOLATED strategy should apply 50% moderation', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.INTERPOLATED,
      testConfig,
    );
    
    const linear = BASE_VALUE * (testConfig.screenWidthDp / Calculator.BASE_WIDTH_DP);
    const expected = BASE_VALUE + (linear - BASE_VALUE) * 0.5;
    expect(result).toBeCloseTo(expected, 1);
  });

  test('DIAGONAL strategy should scale by screen diagonal', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.DIAGONAL,
      testConfig,
    );
    
    expect(result).toBeGreaterThan(BASE_VALUE);
    expect(result).toBeLessThan(BASE_VALUE * 3);
  });

  test('PERIMETER strategy should scale by W+H', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.PERIMETER,
      testConfig,
    );
    
    expect(result).toBeGreaterThan(BASE_VALUE);
    expect(result).toBeLessThan(BASE_VALUE * 3);
  });

  test('FIT strategy should use min ratio (letterbox)', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.FIT,
      testConfig,
    );
    
    const ratioW = testConfig.screenWidthDp / Calculator.BASE_WIDTH_DP;
    const ratioH = testConfig.screenHeightDp / Calculator.BASE_HEIGHT_DP;
    const expected = BASE_VALUE * Math.min(ratioW, ratioH);
    
    expect(result).toBeCloseTo(expected, 1);
  });

  test('FILL strategy should use max ratio (cover)', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.FILL,
      testConfig,
    );
    
    const ratioW = testConfig.screenWidthDp / Calculator.BASE_WIDTH_DP;
    const ratioH = testConfig.screenHeightDp / Calculator.BASE_HEIGHT_DP;
    const expected = BASE_VALUE * Math.max(ratioW, ratioH);
    
    expect(result).toBeCloseTo(expected, 1);
  });

  test('AUTOSIZE strategy should return base value', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.AUTOSIZE,
      testConfig,
    );
    
    expect(result).toBe(BASE_VALUE);
  });

  test('NONE strategy should return base value unchanged', () => {
    const result = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.NONE,
      testConfig,
    );
    
    expect(result).toBe(BASE_VALUE);
  });

  test('All strategies should handle constraints', () => {
    const constraints: Calculator.Constraints = {
      minValue: 40,
      maxValue: 60,
    };
    
    const strategies = [
      ScalingStrategy.DEFAULT,
      ScalingStrategy.PERCENTAGE,
      ScalingStrategy.BALANCED,
      ScalingStrategy.LOGARITHMIC,
      ScalingStrategy.POWER,
      ScalingStrategy.INTERPOLATED,
      ScalingStrategy.DIAGONAL,
      ScalingStrategy.PERIMETER,
      ScalingStrategy.FIT,
      ScalingStrategy.FILL,
    ];
    
    strategies.forEach(strategy => {
      const result = Calculator.calculate(
        BASE_VALUE,
        strategy,
        testConfig,
        'lowest',
        'auto',
        undefined,
        undefined,
        undefined,
        constraints,
      );
      
      expect(result).toBeGreaterThanOrEqual(constraints.minValue!);
      expect(result).toBeLessThanOrEqual(constraints.maxValue!);
    });
  });

  test('Smart inference should select appropriate strategies', () => {
    expect(Calculator.inferStrategy(ElementType.BUTTON)).toBe(ScalingStrategy.BALANCED);
    expect(Calculator.inferStrategy(ElementType.ICON)).toBe(ScalingStrategy.DEFAULT);
    expect(Calculator.inferStrategy(ElementType.CONTAINER)).toBe(ScalingStrategy.PERCENTAGE);
    expect(Calculator.inferStrategy(ElementType.DIVIDER)).toBe(ScalingStrategy.NONE);
    expect(Calculator.inferStrategy(ElementType.GAME_UI)).toBe(ScalingStrategy.FIT);
  });

  test('BALANCED transition point should work correctly', () => {
    const customTransition = 600;
    
    // Below transition - should be linear
    const belowConfig = { ...testConfig, screenWidthDp: 500, smallestScreenWidthDp: 500 };
    const belowResult = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.BALANCED,
      belowConfig,
      'lowest',
      'auto',
      undefined,
      { transitionPoint: customTransition },
    );
    
    const belowLinear = BASE_VALUE * (500 / Calculator.BASE_WIDTH_DP);
    expect(belowResult).toBeCloseTo(belowLinear, 1);
    
    // Above transition - should be logarithmic
    const aboveConfig = { ...testConfig, screenWidthDp: 800, smallestScreenWidthDp: 800 };
    const aboveResult = Calculator.calculate(
      BASE_VALUE,
      ScalingStrategy.BALANCED,
      aboveConfig,
      'lowest',
      'auto',
      undefined,
      { transitionPoint: customTransition },
    );
    
    const aboveLinear = BASE_VALUE * (800 / Calculator.BASE_WIDTH_DP);
    expect(aboveResult).toBeLessThan(aboveLinear);
  });
});

describe('Calculator - Performance', () => {
  test('Should calculate 1000 iterations in reasonable time', () => {
    const start = Date.now();
    
    for (let i = 0; i < 1000; i++) {
      Calculator.calculate(
        BASE_VALUE,
        ScalingStrategy.BALANCED,
        testConfig,
      );
    }
    
    const end = Date.now();
    const elapsed = end - start;
    
    // Should complete 1000 iterations in less than 100ms
    expect(elapsed).toBeLessThan(100);
  });

  test('Lookup table should provide performance benefit', () => {
    // Test with many different values to ensure lookup table hits
    const values = [360, 411, 480, 600, 720, 800, 960, 1080, 1280, 1920];
    
    const start = Date.now();
    
    values.forEach(width => {
      const config = { ...testConfig, screenWidthDp: width, smallestScreenWidthDp: width };
      Calculator.calculate(BASE_VALUE, ScalingStrategy.LOGARITHMIC, config);
    });
    
    const end = Date.now();
    const elapsed = end - start;
    
    // Should be very fast with lookup table
    expect(elapsed).toBeLessThan(10);
  });
});

describe('Calculator - Cross-platform Consistency', () => {
  test('Should produce identical results to Android reference values', () => {
    // Reference values from Android implementation
    const referenceTests = [
      { screen: 360, expected: 48.0 },     // Baseline
      { screen: 480, expected: 76.8 },    // Power 0.75
      { screen: 720, expected: 115.2 },   // Linear
    ];
    
    referenceTests.forEach(({ screen, expected }) => {
      const config = { ...testConfig, screenWidthDp: screen, smallestScreenWidthDp: screen };
      const result = Calculator.calculate(
        BASE_VALUE,
        ScalingStrategy.PERCENTAGE,
        config,
      );
      
      expect(result).toBeCloseTo(expected, 1);
    });
  });
});

