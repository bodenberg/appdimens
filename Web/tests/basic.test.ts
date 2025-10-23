/**
 * Basic Tests for WebDimens
 */

import { describe, it, expect, beforeEach } from 'vitest';
import { WebDimens } from '../src/core/WebDimens';
import { Fixed } from '../src/core/Fixed';
import { Dynamic } from '../src/core/Dynamic';
import { Fluid } from '../src/core/Fluid';
import { WebDimensCache } from '../src/cache/Cache';
import { PhysicalUnits } from '../src/units/PhysicalUnits';

describe('WebDimens Core', () => {
  let dimens: WebDimens;

  beforeEach(() => {
    dimens = new WebDimens();
  });

  it('should create WebDimens instance', () => {
    expect(dimens).toBeInstanceOf(WebDimens);
  });

  it('should create Fixed instance', () => {
    const fixed = dimens.fixed(16);
    expect(fixed).toBeInstanceOf(Fixed);
  });

  it('should create Dynamic instance', () => {
    const dynamic = dimens.dynamic(300);
    expect(dynamic).toBeInstanceOf(Dynamic);
  });

  it('should create Fluid instance', () => {
    const fluid = dimens.fluid(16, 24);
    expect(fluid).toBeInstanceOf(Fluid);
  });

  it('should have shorthand methods', () => {
    expect(dimens.fx(16)).toBeInstanceOf(Fixed);
    expect(dimens.dy(300)).toBeInstanceOf(Dynamic);
    expect(dimens.fl(16, 24)).toBeInstanceOf(Fluid);
  });
});

describe('Fixed Scaling', () => {
  it('should calculate fixed values', () => {
    const fixed = new Fixed(16);
    const result = fixed.calculate();
    expect(result).toBeGreaterThan(0);
    expect(typeof result).toBe('number');
  });

  it('should return px string', () => {
    const fixed = new Fixed(16);
    const result = fixed.toPx();
    expect(result).toMatch(/^\d+(\.\d+)?px$/);
  });

  it('should return rem string', () => {
    const fixed = new Fixed(16);
    const result = fixed.toRem();
    expect(result).toMatch(/^\d+(\.\d+)?rem$/);
  });

  it('should support chaining', () => {
    const result = new Fixed(16)
      .type('lowest' as any)
      .aspectRatio(true)
      .cache(true)
      .toPx();
    expect(typeof result).toBe('string');
  });
});

describe('Dynamic Scaling', () => {
  it('should calculate dynamic values', () => {
    const dynamic = new Dynamic(300);
    const result = dynamic.calculate();
    expect(result).toBeGreaterThan(0);
    expect(typeof result).toBe('number');
  });

  it('should return px string', () => {
    const dynamic = new Dynamic(300);
    const result = dynamic.toPx();
    expect(result).toMatch(/^\d+(\.\d+)?px$/);
  });

  it('should return vw string', () => {
    const dynamic = new Dynamic(300);
    const result = dynamic.toVw();
    expect(result).toMatch(/^\d+(\.\d+)?vw$/);
  });

  it('should calculate percentage', () => {
    const dynamic = new Dynamic(300);
    const percentage = dynamic.percentage();
    expect(percentage).toBeGreaterThan(0);
    expect(percentage).toBeLessThanOrEqual(200); // Reasonable range
  });
});

describe('Fluid Scaling', () => {
  it('should generate clamp string', () => {
    const fluid = new Fluid(16, 24);
    const result = fluid.toString();
    expect(result).toContain('clamp');
    expect(result).toContain('16px');
    expect(result).toContain('24px');
  });

  it('should support custom breakpoints', () => {
    const fluid = new Fluid(16, 24, 'sm', 'lg');
    const result = fluid.calculate();
    expect(result).toContain('clamp');
  });

  it('should return rem clamp', () => {
    const fluid = new Fluid(16, 24);
    const result = fluid.toRem();
    expect(result).toContain('clamp');
    expect(result).toContain('rem');
  });

  it('should calculate min, max, preferred', () => {
    const fluid = new Fluid(16, 24);
    expect(fluid.getMin()).toBe(16);
    expect(fluid.getMax()).toBe(24);
    expect(fluid.getPreferred()).toBe(20);
  });

  it('should lerp correctly', () => {
    const fluid = new Fluid(16, 24);
    expect(fluid.lerp(0)).toBe(16);
    expect(fluid.lerp(1)).toBe(24);
    expect(fluid.lerp(0.5)).toBe(20);
  });
});

describe('Cache System', () => {
  let cache: WebDimensCache;

  beforeEach(() => {
    cache = new WebDimensCache();
  });

  it('should cache values', () => {
    let computeCount = 0;
    const compute = () => {
      computeCount++;
      return 42;
    };

    const result1 = cache.remember('test', [1, 2, 3], compute);
    const result2 = cache.remember('test', [1, 2, 3], compute);

    expect(result1).toBe(42);
    expect(result2).toBe(42);
    expect(computeCount).toBe(1); // Should only compute once
  });

  it('should invalidate on dependency change', () => {
    let computeCount = 0;
    const compute = () => {
      computeCount++;
      return computeCount;
    };

    const result1 = cache.remember('test', [1, 2, 3], compute);
    const result2 = cache.remember('test', [1, 2, 4], compute); // Different deps

    expect(result1).toBe(1);
    expect(result2).toBe(2);
    expect(computeCount).toBe(2);
  });

  it('should clear all cache', () => {
    cache.remember('test1', [1], () => 1);
    cache.remember('test2', [2], () => 2);

    const stats1 = cache.getStats();
    expect(stats1.totalEntries).toBe(2);

    cache.clearAll();

    const stats2 = cache.getStats();
    expect(stats2.totalEntries).toBe(0);
  });

  it('should track cache statistics', () => {
    cache.remember('test', [1], () => 1);
    cache.remember('test', [1], () => 1); // Cache hit

    const stats = cache.getStats();
    expect(stats.cacheHits).toBe(1);
    expect(stats.cacheMisses).toBe(1);
    expect(stats.hitRate).toBe(0.5);
  });
});

describe('Physical Units', () => {
  let physical: PhysicalUnits;

  beforeEach(() => {
    physical = new PhysicalUnits();
  });

  it('should detect DPI', () => {
    const dpi = physical.getDPI();
    expect(dpi).toBeGreaterThan(0);
    expect(typeof dpi).toBe('number');
  });

  it('should convert mm to px', () => {
    const result = physical.mmToPx(10);
    expect(result).toBeGreaterThan(0);
  });

  it('should convert cm to px', () => {
    const result = physical.cmToPx(2);
    expect(result).toBeGreaterThan(0);
  });

  it('should convert inch to px', () => {
    const result = physical.inchToPx(1);
    expect(result).toBe(physical.getDPI());
  });

  it('should return CSS strings', () => {
    expect(physical.mm(10)).toMatch(/^\d+(\.\d+)?px$/);
    expect(physical.cm(2)).toMatch(/^\d+(\.\d+)?px$/);
    expect(physical.inch(1)).toMatch(/^\d+(\.\d+)?px$/);
  });

  it('should calculate radius', () => {
    const radius = physical.radius(20, 'px');
    expect(radius).toBe(10);
  });

  it('should calculate circumference', () => {
    const circ = physical.circumference(20, 'px');
    expect(circ).toBeCloseTo(2 * Math.PI * 10, 1);
  });
});

describe('Configuration', () => {
  it('should accept custom configuration', () => {
    const dimens = new WebDimens({
      baseWidth: 1440,
      baseHeight: 900,
      referenceAR: 16 / 10,
      enableCache: false,
      debug: true
    });

    const config = dimens.getConfig();
    expect(config.baseWidth).toBe(1440);
    expect(config.baseHeight).toBe(900);
    expect(config.enableCache).toBe(false);
  });

  it('should update configuration', () => {
    const dimens = new WebDimens();
    dimens.updateConfig({
      baseWidth: 1600
    });

    const config = dimens.getConfig();
    expect(config.baseWidth).toBe(1600);
  });
});

describe('Utilities', () => {
  let dimens: WebDimens;

  beforeEach(() => {
    dimens = new WebDimens();
  });

  it('should generate spacing scale', () => {
    const scale = dimens.generateSpacingScale(4);
    expect(scale).toHaveProperty('0');
    expect(scale).toHaveProperty('1');
    expect(scale).toHaveProperty('4');
    expect(scale['0']).toBe('0px');
  });

  it('should generate font scale', () => {
    const scale = dimens.generateFontScale();
    expect(scale).toHaveProperty('xs');
    expect(scale).toHaveProperty('base');
    expect(scale).toHaveProperty('xl');
  });

  it('should generate safe area values', () => {
    const safeTop = dimens.safeArea('top', 16);
    expect(safeTop).toContain('max');
    expect(safeTop).toContain('env(safe-area-inset-top)');
  });
});

