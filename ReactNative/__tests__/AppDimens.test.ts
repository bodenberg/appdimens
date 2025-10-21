/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens React Native - Tests
 *
 * Description:
 * Basic tests for AppDimens React Native library functionality.
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

import {
  AppDimens,
  AppDimensFixed,
  AppDimensDynamic,
  AppDimensAdjustmentFactors,
} from '../src';

describe('AppDimens', () => {
  describe('AppDimensFixed', () => {
    it('should create fixed dimension builder', () => {
      const fx = AppDimens.fx(16);
      expect(fx).toBeInstanceOf(AppDimensFixed);
    });

    it('should calculate fixed dimension value', () => {
      const fx = AppDimens.fx(16);
      const result = fx.toPixels();
      expect(typeof result).toBe('number');
      expect(result).toBeGreaterThan(0);
    });

    it('should support chaining methods', () => {
      const fx = AppDimens.fx(16).type('highest').aspectRatio(true).cache(true);

      expect(fx).toBeInstanceOf(AppDimensFixed);
    });

    it('should support screen qualifiers', () => {
      const fx = AppDimens.fx(16).screen('tablet', 24).screen('phone', 14);

      expect(fx).toBeInstanceOf(AppDimensFixed);
    });
  });

  describe('AppDimensDynamic', () => {
    it('should create dynamic dimension builder', () => {
      const dy = AppDimens.dy(50);
      expect(dy).toBeInstanceOf(AppDimensDynamic);
    });

    it('should calculate dynamic dimension value', () => {
      const dy = AppDimens.dy(50);
      const result = dy.toPixels();
      expect(typeof result).toBe('number');
      expect(result).toBeGreaterThan(0);
    });

    it('should support chaining methods', () => {
      const dy = AppDimens.dy(50).type('lowest').cache(true);

      expect(dy).toBeInstanceOf(AppDimensDynamic);
    });

    it('should support screen qualifiers', () => {
      const dy = AppDimens.dy(50).screen('tablet', 75).screen('phone', 40);

      expect(dy).toBeInstanceOf(AppDimensDynamic);
    });
  });

  describe('AppDimensAdjustmentFactors', () => {
    it('should get current screen dimensions', () => {
      const dimensions =
        AppDimensAdjustmentFactors.getCurrentScreenDimensions();
      expect(dimensions).toHaveProperty('width');
      expect(dimensions).toHaveProperty('height');
      expect(dimensions).toHaveProperty('scale');
      expect(dimensions).toHaveProperty('fontScale');
      expect(dimensions).toHaveProperty('pixelRatio');
      expect(typeof dimensions.width).toBe('number');
      expect(typeof dimensions.height).toBe('number');
    });

    it('should get current device info', () => {
      const deviceInfo = AppDimensAdjustmentFactors.getCurrentDeviceInfo();
      expect(deviceInfo).toHaveProperty('type');
      expect(deviceInfo).toHaveProperty('platform');
      expect(deviceInfo).toHaveProperty('orientation');
      expect(typeof deviceInfo.type).toBe('string');
      expect(typeof deviceInfo.platform).toBe('string');
    });

    it('should convert points to pixels', () => {
      const pixels = AppDimensAdjustmentFactors.pointsToPixels(16);
      expect(typeof pixels).toBe('number');
      expect(pixels).toBeGreaterThan(0);
    });

    it('should convert pixels to points', () => {
      const points = AppDimensAdjustmentFactors.pixelsToPoints(32);
      expect(typeof points).toBe('number');
      expect(points).toBeGreaterThan(0);
    });
  });

  describe('Global AppDimens', () => {
    it('should provide global configuration', () => {
      expect(AppDimens).toHaveProperty('fx');
      expect(AppDimens).toHaveProperty('dy');
      expect(typeof AppDimens.fx).toBe('function');
      expect(typeof AppDimens.dy).toBe('function');
    });

    it('should support cache control', () => {
      AppDimens.setGlobalCacheEnabled(true);
      expect(AppDimens.isGlobalCacheEnabled()).toBe(true);

      AppDimens.setGlobalCacheEnabled(false);
      expect(AppDimens.isGlobalCacheEnabled()).toBe(false);
    });

    it('should support multi-window adjustment', () => {
      AppDimens.setGlobalMultiWindowAdjustment(true);
      expect(AppDimens.isGlobalMultiWindowAdjustmentEnabled()).toBe(true);

      AppDimens.setGlobalMultiWindowAdjustment(false);
      expect(AppDimens.isGlobalMultiWindowAdjustmentEnabled()).toBe(false);
    });
  });
});
