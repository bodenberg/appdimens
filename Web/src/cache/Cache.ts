/**
 * Author & Developer: Jean Bodenberg
 * Based on: AppDimens Android - AppDimensAutoCache
 * 
 * Automatic cache management system
 * Mimics Compose's remember behavior with automatic dependency tracking
 */

import type { CacheEntry, CacheStats } from '../types';
import { MAX_CACHE_SIZE, CACHE_CLEANUP_INTERVAL } from '../constants';

/**
 * Dependency key for tracking changes
 */
interface DependencyKey {
  type: string;
  hashCode: number;
}

/**
 * WebDimens Cache System
 * Automatic cache with dependency tracking and smart invalidation
 */
export class WebDimensCache {
  private cache = new Map<string, CacheEntry>();
  private dependencyTracker = new Map<string, Set<DependencyKey>>();
  private cleanupInterval: number | null = null;
  private stats = {
    hits: 0,
    misses: 0,
    totalCalculationTime: 0
  };

  constructor() {
    this.startCleanupInterval();
  }

  /**
   * Remember a value with automatic dependency tracking
   * Similar to Compose's remember
   */
  remember<T>(key: string, dependencies: any[], compute: () => T): T {
    const dependencyKeys = this.createDependencyKeys(dependencies);
    const cached = this.cache.get(key);

    // Check if dependencies changed
    const dependenciesChanged = !cached || !this.areDependenciesEqual(
      cached.dependencies,
      new Set(dependencyKeys)
    );

    if (dependenciesChanged) {
      this.stats.misses++;
      
      // Compute new value
      const startTime = performance.now();
      const newValue = compute();
      const calculationTime = performance.now() - startTime;
      
      this.stats.totalCalculationTime += calculationTime;

      // Store in cache
      this.cache.set(key, {
        value: newValue,
        dependencies: new Set(dependencyKeys),
        timestamp: Date.now(),
        accessCount: 1
      });

      this.dependencyTracker.set(key, new Set(dependencyKeys));

      // Cleanup if cache is too large
      if (this.cache.size > MAX_CACHE_SIZE) {
        this.cleanup();
      }

      return newValue;
    }

    // Return cached value
    this.stats.hits++;
    const entry = cached!;
    this.cache.set(key, {
      ...entry,
      accessCount: entry.accessCount + 1
    });

    return entry.value as T;
  }

  /**
   * Create dependency keys from any objects
   */
  private createDependencyKeys(dependencies: any[]): DependencyKey[] {
    return dependencies.map(dep => ({
      type: typeof dep === 'object' && dep !== null 
        ? dep.constructor.name 
        : typeof dep,
      hashCode: this.hashCode(dep)
    }));
  }

  /**
   * Generate hash code for any value
   */
  private hashCode(value: any): number {
    if (value === null || value === undefined) return 0;
    
    const str = typeof value === 'object' 
      ? JSON.stringify(value)
      : String(value);
    
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
      const char = str.charCodeAt(i);
      hash = ((hash << 5) - hash) + char;
      hash = hash & hash; // Convert to 32bit integer
    }
    return hash;
  }

  /**
   * Check if dependencies are equal
   */
  private areDependenciesEqual(
    deps1: Set<any>,
    deps2: Set<DependencyKey>
  ): boolean {
    if (deps1.size !== deps2.size) return false;

    const deps1Array = Array.from(deps1);
    const deps2Array = Array.from(deps2);

    return deps1Array.every((dep1, index) => {
      const dep2 = deps2Array[index];
      return dep1.type === dep2.type && dep1.hashCode === dep2.hashCode;
    });
  }

  /**
   * Invalidate cache entries that depend on a specific object
   */
  invalidateOnDependencyChange(changedObject: any): void {
    const changedKey = this.createDependencyKeys([changedObject])[0];

    const keysToRemove: string[] = [];

    this.dependencyTracker.forEach((deps, key) => {
      const depsArray = Array.from(deps);
      if (depsArray.some(dep => 
        dep.type === changedKey.type && dep.hashCode === changedKey.hashCode
      )) {
        keysToRemove.push(key);
      }
    });

    keysToRemove.forEach(key => {
      this.cache.delete(key);
      this.dependencyTracker.delete(key);
    });
  }

  /**
   * Clear all cache entries
   */
  clearAll(): void {
    this.cache.clear();
    this.dependencyTracker.clear();
  }

  /**
   * Clear cache entries by pattern
   */
  clearByPattern(pattern: string): void {
    const keysToRemove: string[] = [];

    this.cache.forEach((_, key) => {
      if (key.includes(pattern)) {
        keysToRemove.push(key);
      }
    });

    keysToRemove.forEach(key => {
      this.cache.delete(key);
      this.dependencyTracker.delete(key);
    });
  }

  /**
   * Get cache statistics
   */
  getStats(): CacheStats {
    const totalAccesses = this.stats.hits + this.stats.misses;
    const hitRate = totalAccesses > 0 
      ? this.stats.hits / totalAccesses 
      : 0;

    return {
      totalEntries: this.cache.size,
      totalAccesses,
      cacheHits: this.stats.hits,
      cacheMisses: this.stats.misses,
      hitRate,
      avgCalculationTime: this.stats.misses > 0
        ? this.stats.totalCalculationTime / this.stats.misses
        : 0,
      memoryUsage: this.estimateMemoryUsage()
    };
  }

  /**
   * Estimate memory usage
   */
  private estimateMemoryUsage(): number {
    let totalSize = 0;

    this.cache.forEach((entry, key) => {
      totalSize += key.length * 2; // String characters
      totalSize += entry.dependencies.size * 16; // Dependency keys
      totalSize += 24; // Entry object overhead
    });

    return totalSize;
  }

  /**
   * Cleanup old entries based on LRU
   */
  private cleanup(): void {
    // Convert to array and sort by access count and timestamp
    const entries = Array.from(this.cache.entries())
      .sort((a, b) => {
        const scoreA = a[1].accessCount / (Date.now() - a[1].timestamp);
        const scoreB = b[1].accessCount / (Date.now() - b[1].timestamp);
        return scoreA - scoreB;
      });

    // Remove bottom 20%
    const removeCount = Math.floor(entries.length * 0.2);
    for (let i = 0; i < removeCount; i++) {
      const key = entries[i][0];
      this.cache.delete(key);
      this.dependencyTracker.delete(key);
    }
  }

  /**
   * Start periodic cleanup
   */
  private startCleanupInterval(): void {
    if (typeof window !== 'undefined') {
      this.cleanupInterval = window.setInterval(() => {
        if (this.cache.size > MAX_CACHE_SIZE * 0.8) {
          this.cleanup();
        }
      }, CACHE_CLEANUP_INTERVAL);
    }
  }

  /**
   * Stop cleanup interval
   */
  destroy(): void {
    if (this.cleanupInterval !== null) {
      clearInterval(this.cleanupInterval);
      this.cleanupInterval = null;
    }
    this.clearAll();
  }
}

/**
 * Global cache instance
 */
export const globalCache = new WebDimensCache();

