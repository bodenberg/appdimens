/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens Web - Base Orientation Tests
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

import { describe, it, expect } from 'vitest';
import { resolveScreenType, wouldInvert, getCurrentOrientation } from '../src/utils/orientationResolver';

describe('BaseOrientation', () => {
  describe('resolveScreenType', () => {
    it('should not invert when orientation is AUTO', () => {
      const result = resolveScreenType('lowest', 'auto', { width: 360, height: 800 });
      expect(result).toBe('lowest');
    });

    it('should not invert portrait design in portrait orientation', () => {
      const result = resolveScreenType('lowest', 'portrait', { width: 360, height: 800 });
      expect(result).toBe('lowest');
    });

    it('should invert portrait design in landscape orientation', () => {
      const resultLowest = resolveScreenType('lowest', 'portrait', { width: 800, height: 360 });
      expect(resultLowest).toBe('highest');

      const resultHighest = resolveScreenType('highest', 'portrait', { width: 800, height: 360 });
      expect(resultHighest).toBe('lowest');
    });

    it('should not invert landscape design in landscape orientation', () => {
      const result = resolveScreenType('highest', 'landscape', { width: 800, height: 360 });
      expect(result).toBe('highest');
    });

    it('should invert landscape design in portrait orientation', () => {
      const result = resolveScreenType('lowest', 'landscape', { width: 360, height: 800 });
      expect(result).toBe('highest');
    });
  });

  describe('wouldInvert', () => {
    it('should return false for AUTO orientation', () => {
      const result = wouldInvert('auto', { width: 360, height: 800 });
      expect(result).toBe(false);
    });

    it('should return true for portrait design in landscape', () => {
      const result = wouldInvert('portrait', { width: 800, height: 360 });
      expect(result).toBe(true);
    });

    it('should return false for portrait design in portrait', () => {
      const result = wouldInvert('portrait', { width: 360, height: 800 });
      expect(result).toBe(false);
    });
  });

  describe('Real-world scenarios', () => {
    it('should maintain card proportion when rotated', () => {
      // Card designed for portrait at 300px width
      const portraitType = resolveScreenType('lowest', 'portrait', { width: 360, height: 800 });
      const landscapeType = resolveScreenType('lowest', 'portrait', { width: 800, height: 360 });

      expect(portraitType).toBe('lowest');   // Uses width in portrait
      expect(landscapeType).toBe('highest'); // Auto-inverts to use height in landscape
    });

    it('should work with Fixed class', () => {
      // This would need the Fixed class to be imported and tested
      // Just validating the resolver function here
      const result = resolveScreenType('lowest', 'portrait', { width: 1024, height: 768 });
      expect(result).toBe('highest'); // Inverts because landscape (width > height)
    });
  });
});

