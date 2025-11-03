/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens iOS - Performance Monitor
 *
 * Description:
 * Performance monitoring system for tracking calculation times, FPS,
 * and memory usage. Similar to Android Games PerformanceMonitor.
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

import Foundation
import QuartzCore

/**
 * [EN] Performance category for organizing profiling data.
 * [PT] Categoria de performance para organizar dados de profiling.
 */
public enum PerformanceCategory: String {
    case general = "GENERAL"
    case rendering = "RENDERING"
    case calculation = "CALCULATION"
    case caching = "CACHING"
    case layout = "LAYOUT"
    case physics = "PHYSICS"
    case ai = "AI"
}

/**
 * [EN] Performance monitoring system for AppDimens.
 * Tracks calculation times, memory usage, and provides performance reports.
 * 
 * [PT] Sistema de monitoramento de performance para AppDimens.
 * Rastreia tempos de cálculo, uso de memória e fornece relatórios de performance.
 *
 * @example
 * ```swift
 * let monitor = PerformanceMonitor.shared
 * 
 * // Begin profiling
 * monitor.beginProfile("dimensionCalc", category: .calculation)
 * // ... perform calculations ...
 * monitor.endProfile("dimensionCalc")
 * 
 * // Get metrics
 * let metrics = monitor.getOverallMetrics()
 * print("Average time: \(metrics.averageFrameTime)ms")
 * 
 * // Generate report
 * let report = monitor.generateReport()
 * print(report.summary)
 * ```
 */
public class PerformanceMonitor {
    
    // MARK: - Singleton
    
    public static let shared = PerformanceMonitor()
    
    private init() {}
    
    // MARK: - Private Properties
    
    private var profileSessions: [String: ProfileSession] = [:]
    private var completedProfiles: [ProfileResult] = []
    private let monitorQueue = DispatchQueue(label: "com.appdimens.performance", attributes: .concurrent)
    private var frameTimestamps: [CFTimeInterval] = []
    private let maxFrameHistory = 60  // Keep last 60 frames
    
    // MARK: - Profile Session
    
    private struct ProfileSession {
        let name: String
        let category: PerformanceCategory
        let startTime: CFTimeInterval
    }
    
    // MARK: - Profile Result
    
    private struct ProfileResult {
        let name: String
        let category: PerformanceCategory
        let duration: CFTimeInterval
        let timestamp: Date
    }
    
    // MARK: - Public Structures
    
    /**
     * [EN] Performance metrics structure.
     * [PT] Estrutura de métricas de performance.
     */
    public struct PerformanceMetrics {
        /// Average frame time in milliseconds
        public let averageFrameTime: Double
        
        /// Minimum frame time in milliseconds
        public let minFrameTime: Double
        
        /// Maximum frame time in milliseconds
        public let maxFrameTime: Double
        
        /// Current FPS (frames per second)
        public let currentFPS: Double
        
        /// Total profiled operations
        public let totalOperations: Int
        
        /// Average operation time in milliseconds
        public let averageOperationTime: Double
        
        /// Estimated memory usage in bytes
        public let memoryUsage: Int64
        
        /// Number of slow calculations (>1ms)
        public let slowCalculations: Int
    }
    
    /**
     * [EN] Performance report structure.
     * [PT] Estrutura de relatório de performance.
     */
    public struct PerformanceReport {
        /// Summary text
        public let summary: String
        
        /// Detailed metrics
        public let metrics: PerformanceMetrics
        
        /// Performance suggestions
        public let suggestions: [String]
        
        /// Breakdown by category
        public let categoryBreakdown: [PerformanceCategory: CategoryStats]
        
        /// Generated timestamp
        public let timestamp: Date
    }
    
    /**
     * [EN] Category statistics structure.
     * [PT] Estrutura de estatísticas por categoria.
     */
    public struct CategoryStats {
        /// Number of operations in this category
        public let operationCount: Int
        
        /// Average time for this category in milliseconds
        public let averageTime: Double
        
        /// Total time for this category in milliseconds
        public let totalTime: Double
        
        /// Percentage of total time
        public let percentageOfTotal: Double
    }
    
    // MARK: - Profiling Methods
    
    /**
     * [EN] Begins profiling a named operation.
     * [PT] Inicia o profiling de uma operação nomeada.
     * 
     * @param name The name of the operation to profile.
     * @param category The category of the operation.
     * 
     * @example
     * ```swift
     * monitor.beginProfile("dimensionCalc", category: .calculation)
     * ```
     */
    public func beginProfile(_ name: String, category: PerformanceCategory = .general) {
        let startTime = CACurrentMediaTime()
        
        monitorQueue.async(flags: .barrier) {
            let session = ProfileSession(name: name, category: category, startTime: startTime)
            self.profileSessions[name] = session
        }
    }
    
    /**
     * [EN] Ends profiling for a named operation.
     * [PT] Finaliza o profiling de uma operação nomeada.
     * 
     * @param name The name of the operation to end.
     * 
     * @example
     * ```swift
     * monitor.endProfile("dimensionCalc")
     * ```
     */
    public func endProfile(_ name: String) {
        let endTime = CACurrentMediaTime()
        
        monitorQueue.async(flags: .barrier) {
            guard let session = self.profileSessions[name] else { return }
            
            let duration = endTime - session.startTime
            let result = ProfileResult(
                name: name,
                category: session.category,
                duration: duration,
                timestamp: Date()
            )
            
            self.completedProfiles.append(result)
            self.profileSessions.removeValue(forKey: name)
            
            // Keep only last 1000 results
            if self.completedProfiles.count > 1000 {
                self.completedProfiles.removeFirst(self.completedProfiles.count - 1000)
            }
        }
    }
    
    /**
     * [EN] Records a frame timestamp for FPS calculation.
     * [PT] Registra um timestamp de frame para cálculo de FPS.
     * 
     * @example
     * ```swift
     * // Call this in your render loop
     * monitor.recordFrame()
     * ```
     */
    public func recordFrame() {
        let timestamp = CACurrentMediaTime()
        
        monitorQueue.async(flags: .barrier) {
            self.frameTimestamps.append(timestamp)
            
            if self.frameTimestamps.count > self.maxFrameHistory {
                self.frameTimestamps.removeFirst(self.frameTimestamps.count - self.maxFrameHistory)
            }
        }
    }
    
    // MARK: - Metrics Methods
    
    /**
     * [EN] Gets overall performance metrics.
     * [PT] Obtém métricas gerais de performance.
     * 
     * @return Performance metrics structure.
     */
    public func getOverallMetrics() -> PerformanceMetrics {
        return monitorQueue.sync {
            let totalOps = completedProfiles.count
            
            // Calculate average operation time
            let totalTime = completedProfiles.reduce(0.0) { $0 + $1.duration }
            let avgTime = totalOps > 0 ? (totalTime / Double(totalOps)) * 1000.0 : 0.0
            
            // Calculate frame times
            var avgFrameTime = 16.67  // Default ~60 FPS
            var minFrameTime = 16.67
            var maxFrameTime = 16.67
            var currentFPS = 60.0
            
            if frameTimestamps.count > 1 {
                var frameTimes: [Double] = []
                for i in 1..<frameTimestamps.count {
                    let frameTime = (frameTimestamps[i] - frameTimestamps[i-1]) * 1000.0
                    frameTimes.append(frameTime)
                }
                
                if !frameTimes.isEmpty {
                    avgFrameTime = frameTimes.reduce(0.0, +) / Double(frameTimes.count)
                    minFrameTime = frameTimes.min() ?? 16.67
                    maxFrameTime = frameTimes.max() ?? 16.67
                    currentFPS = avgFrameTime > 0 ? 1000.0 / avgFrameTime : 60.0
                }
            }
            
            // Count slow calculations (>1ms)
            let slowCalcs = completedProfiles.filter { $0.duration * 1000.0 > 1.0 }.count
            
            // Estimate memory usage
            let memoryUsage = estimateMemoryUsage()
            
            return PerformanceMetrics(
                averageFrameTime: avgFrameTime,
                minFrameTime: minFrameTime,
                maxFrameTime: maxFrameTime,
                currentFPS: currentFPS,
                totalOperations: totalOps,
                averageOperationTime: avgTime,
                memoryUsage: memoryUsage,
                slowCalculations: slowCalcs
            )
        }
    }
    
    /**
     * [EN] Generates a detailed performance report.
     * [PT] Gera um relatório detalhado de performance.
     * 
     * @return Performance report with metrics and suggestions.
     */
    public func generateReport() -> PerformanceReport {
        return monitorQueue.sync {
            let metrics = getOverallMetrics()
            
            // Calculate category breakdown
            var categoryStats: [PerformanceCategory: CategoryStats] = [:]
            let totalTime = completedProfiles.reduce(0.0) { $0 + $1.duration }
            
            for category in [PerformanceCategory.general, .rendering, .calculation, .caching, .layout, .physics, .ai] {
                let categoryProfiles = completedProfiles.filter { $0.category == category }
                let count = categoryProfiles.count
                let catTotalTime = categoryProfiles.reduce(0.0) { $0 + $1.duration } * 1000.0
                let catAvgTime = count > 0 ? catTotalTime / Double(count) : 0.0
                let percentage = totalTime > 0 ? (catTotalTime / (totalTime * 1000.0)) * 100.0 : 0.0
                
                categoryStats[category] = CategoryStats(
                    operationCount: count,
                    averageTime: catAvgTime,
                    totalTime: catTotalTime,
                    percentageOfTotal: percentage
                )
            }
            
            // Generate suggestions
            var suggestions: [String] = []
            
            if metrics.averageFrameTime > 16.67 {
                suggestions.append("Frame time above 60 FPS target. Consider optimizations.")
            }
            
            if metrics.averageOperationTime > 1.0 {
                suggestions.append("Average operation time is high. Enable caching if not already enabled.")
            }
            
            if metrics.slowCalculations > metrics.totalOperations / 10 {
                suggestions.append("Many slow calculations detected. Review calculation complexity.")
            }
            
            if metrics.memoryUsage > 10 * 1024 * 1024 {  // > 10MB
                suggestions.append("High memory usage detected. Consider clearing caches periodically.")
            }
            
            if metrics.currentFPS < 30 {
                suggestions.append("Low FPS detected. Enable fast math and high-performance mode.")
            }
            
            // Generate summary
            let summary = generateSummaryText(metrics: metrics, categoryStats: categoryStats)
            
            return PerformanceReport(
                summary: summary,
                metrics: metrics,
                suggestions: suggestions,
                categoryBreakdown: categoryStats,
                timestamp: Date()
            )
        }
    }
    
    /**
     * [EN] Resets all performance data.
     * [PT] Reseta todos os dados de performance.
     */
    public func reset() {
        monitorQueue.async(flags: .barrier) {
            self.completedProfiles.removeAll()
            self.frameTimestamps.removeAll()
            self.profileSessions.removeAll()
        }
    }
    
    /**
     * [EN] Gets statistics for a specific category.
     * [PT] Obtém estatísticas para uma categoria específica.
     * 
     * @param category The performance category.
     * @return Category statistics or nil if no data.
     */
    public func getCategoryStats(for category: PerformanceCategory) -> CategoryStats? {
        return monitorQueue.sync {
            let categoryProfiles = completedProfiles.filter { $0.category == category }
            guard !categoryProfiles.isEmpty else { return nil }
            
            let count = categoryProfiles.count
            let totalTime = categoryProfiles.reduce(0.0) { $0 + $1.duration } * 1000.0
            let avgTime = totalTime / Double(count)
            
            let allTotalTime = completedProfiles.reduce(0.0) { $0 + $1.duration } * 1000.0
            let percentage = allTotalTime > 0 ? (totalTime / allTotalTime) * 100.0 : 0.0
            
            return CategoryStats(
                operationCount: count,
                averageTime: avgTime,
                totalTime: totalTime,
                percentageOfTotal: percentage
            )
        }
    }
    
    // MARK: - Private Helper Methods
    
    private func generateSummaryText(metrics: PerformanceMetrics, categoryStats: [PerformanceCategory: CategoryStats]) -> String {
        var summary = "=== AppDimens Performance Report ===\n\n"
        
        // Frame performance
        summary += "Frame Performance:\n"
        summary += "  Average FPS: \(String(format: "%.1f", metrics.currentFPS))\n"
        summary += "  Average Frame Time: \(String(format: "%.2f", metrics.averageFrameTime))ms\n"
        summary += "  Min/Max Frame Time: \(String(format: "%.2f", metrics.minFrameTime))/\(String(format: "%.2f", metrics.maxFrameTime))ms\n\n"
        
        // Operation performance
        summary += "Operation Performance:\n"
        summary += "  Total Operations: \(metrics.totalOperations)\n"
        summary += "  Average Operation Time: \(String(format: "%.3f", metrics.averageOperationTime))ms\n"
        summary += "  Slow Calculations (>1ms): \(metrics.slowCalculations)\n\n"
        
        // Memory usage
        summary += "Memory Usage:\n"
        summary += "  Estimated: \(String(format: "%.2f", Double(metrics.memoryUsage) / 1024.0 / 1024.0))MB\n\n"
        
        // Category breakdown
        if !categoryStats.isEmpty {
            summary += "Category Breakdown:\n"
            for (category, stats) in categoryStats.sorted(by: { $0.value.totalTime > $1.value.totalTime }) {
                if stats.operationCount > 0 {
                    summary += "  \(category.rawValue): \(stats.operationCount) ops, "
                    summary += "\(String(format: "%.3f", stats.averageTime))ms avg, "
                    summary += "\(String(format: "%.1f", stats.percentageOfTotal))%\n"
                }
            }
        }
        
        return summary
    }
    
    private func estimateMemoryUsage() -> Int64 {
        var totalSize: Int64 = 0
        
        // Estimate from cache
        let cacheStats = AppDimensCore.getCacheStats()
        totalSize += cacheStats.memoryUsage
        
        // Estimate from profile data
        totalSize += Int64(completedProfiles.count * 64)  // ~64 bytes per profile result
        totalSize += Int64(frameTimestamps.count * 8)     // ~8 bytes per timestamp
        
        return totalSize
    }
}

// MARK: - Convenience Extensions

public extension PerformanceMonitor {
    
    /**
     * [EN] Profiles a closure and returns its result.
     * [PT] Faz profiling de uma closure e retorna seu resultado.
     * 
     * @param name The name of the operation.
     * @param category The performance category.
     * @param operation The operation to profile.
     * @return The result of the operation.
     * 
     * @example
     * ```swift
     * let result = monitor.profile("calculateDimension", category: .calculation) {
     *     return someDimensionCalculation()
     * }
     * ```
     */
    func profile<T>(_ name: String, category: PerformanceCategory = .general, operation: () -> T) -> T {
        beginProfile(name, category: category)
        let result = operation()
        endProfile(name)
        return result
    }
    
    /**
     * [EN] Profiles an async operation.
     * [PT] Faz profiling de uma operação assíncrona.
     * 
     * @param name The name of the operation.
     * @param category The performance category.
     * @param operation The async operation to profile.
     * @return The result of the operation.
     */
    @available(iOS 13.0, *)
    func profileAsync<T>(_ name: String, category: PerformanceCategory = .general, operation: () async -> T) async -> T {
        beginProfile(name, category: category)
        let result = await operation()
        endProfile(name)
        return result
    }
    
    /**
     * [EN] Gets a simple performance summary string.
     * [PT] Obtém uma string resumida de performance.
     * 
     * @return Summary string.
     */
    func getSummary() -> String {
        let metrics = getOverallMetrics()
        return "FPS: \(String(format: "%.1f", metrics.currentFPS)) | " +
               "Avg Frame: \(String(format: "%.2f", metrics.averageFrameTime))ms | " +
               "Operations: \(metrics.totalOperations) | " +
               "Memory: \(String(format: "%.1f", Double(metrics.memoryUsage) / 1024.0 / 1024.0))MB"
    }
    
    /**
     * [EN] Prints current performance metrics to console.
     * [PT] Imprime métricas de performance atuais no console.
     */
    func printMetrics() {
        let metrics = getOverallMetrics()
        print("\n=== AppDimens Performance Metrics ===")
        print("FPS: \(String(format: "%.1f", metrics.currentFPS))")
        print("Average Frame Time: \(String(format: "%.2f", metrics.averageFrameTime))ms")
        print("Total Operations: \(metrics.totalOperations)")
        print("Average Operation Time: \(String(format: "%.3f", metrics.averageOperationTime))ms")
        print("Slow Calculations: \(metrics.slowCalculations)")
        print("Memory Usage: \(String(format: "%.2f", Double(metrics.memoryUsage) / 1024.0 / 1024.0))MB")
        print("=====================================\n")
    }
    
    /**
     * [EN] Prints full performance report to console.
     * [PT] Imprime relatório completo de performance no console.
     */
    func printReport() {
        let report = generateReport()
        print("\n\(report.summary)")
        
        if !report.suggestions.isEmpty {
            print("Suggestions:")
            for suggestion in report.suggestions {
                print("  - \(suggestion)")
            }
        }
        
        print("")
    }
}

// MARK: - Global Access (AppDimensCore Integration)

public extension AppDimensCore {
    
    /**
     * [EN] Gets the global performance monitor instance.
     * [PT] Obtém a instância global do monitor de performance.
     */
    static var performanceMonitor: PerformanceMonitor {
        return PerformanceMonitor.shared
    }
    
    /**
     * [EN] Gets performance metrics from the global monitor.
     * [PT] Obtém métricas de performance do monitor global.
     */
    static func getPerformanceMonitorMetrics() -> PerformanceMonitor.PerformanceMetrics {
        return PerformanceMonitor.shared.getOverallMetrics()
    }
    
    /**
     * [EN] Generates a performance report from the global monitor.
     * [PT] Gera um relatório de performance do monitor global.
     */
    static func generatePerformanceReport() -> PerformanceMonitor.PerformanceReport {
        return PerformanceMonitor.shared.generateReport()
    }
    
    /**
     * [EN] Resets the global performance monitor.
     * [PT] Reseta o monitor de performance global.
     */
    static func resetPerformanceMonitor() {
        PerformanceMonitor.shared.reset()
    }
}

