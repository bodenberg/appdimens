/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Flutter - Cache Statistics
 *
 * Description:
 * Cache statistics model for performance monitoring and optimization.
 *
 * Licensed under the Apache License, Version 2.0
 */

/// [EN] Cache statistics structure.
/// [PT] Estrutura de estatísticas de cache.
class CacheStats {
  /// [EN] Total number of cached entries
  /// [PT] Número total de entradas em cache
  final int totalEntries;

  /// [EN] Total number of cache accesses
  /// [PT] Número total de acessos ao cache
  final int totalAccesses;

  /// [EN] Number of cache hits
  /// [PT] Número de acertos de cache
  final int cacheHits;

  /// [EN] Number of cache misses
  /// [PT] Número de falhas de cache
  final int cacheMisses;

  /// [EN] Cache hit rate (0.0 to 1.0)
  /// [PT] Taxa de acerto de cache (0.0 a 1.0)
  final double hitRate;

  /// [EN] Average calculation time in milliseconds
  /// [PT] Tempo médio de cálculo em milissegundos
  final double avgCalculationTime;

  /// [EN] Estimated memory usage in bytes
  /// [PT] Uso de memória estimado em bytes
  final int memoryUsage;

  const CacheStats({
    required this.totalEntries,
    required this.totalAccesses,
    required this.cacheHits,
    required this.cacheMisses,
    required this.hitRate,
    required this.avgCalculationTime,
    required this.memoryUsage,
  });

  /// [EN] Creates a CacheStats instance with default values.
  /// [PT] Cria uma instância CacheStats com valores padrão.
  factory CacheStats.empty() {
    return const CacheStats(
      totalEntries: 0,
      totalAccesses: 0,
      cacheHits: 0,
      cacheMisses: 0,
      hitRate: 0.0,
      avgCalculationTime: 0.0,
      memoryUsage: 0,
    );
  }

  /// [EN] Returns a string representation of the cache stats.
  /// [PT] Retorna uma representação em string das estatísticas de cache.
  @override
  String toString() {
    return 'CacheStats('
        'totalEntries: $totalEntries, '
        'totalAccesses: $totalAccesses, '
        'cacheHits: $cacheHits, '
        'cacheMisses: $cacheMisses, '
        'hitRate: ${(hitRate * 100).toStringAsFixed(1)}%, '
        'avgCalculationTime: ${avgCalculationTime}ms, '
        'memoryUsage: ${(memoryUsage / 1024).toStringAsFixed(1)}KB'
        ')';
  }

  /// [EN] Returns a map representation of the cache stats.
  /// [PT] Retorna uma representação em mapa das estatísticas de cache.
  Map<String, dynamic> toMap() {
    return {
      'totalEntries': totalEntries,
      'totalAccesses': totalAccesses,
      'cacheHits': cacheHits,
      'cacheMisses': cacheMisses,
      'hitRate': hitRate,
      'avgCalculationTime': avgCalculationTime,
      'memoryUsage': memoryUsage,
    };
  }
}

