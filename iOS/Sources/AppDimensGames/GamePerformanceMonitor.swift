/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-02-01
 *
 * Library: AppDimens Games - Performance Monitor
 *
 * Description:
 * Performance monitoring system for game development.
 * Tracks FPS, frame times, calculation performance, and memory usage.
 * Similar to Android Games PerformanceMonitor.
 *
 * Licensed under the Apache License, Version 2.0
 */

import Foundation
import QuartzCore
import Metal

/**
 * [EN] Performance monitoring system specifically for game development.
 * Extends base PerformanceMonitor with game-specific metrics.
 * 
 * [PT] Sistema de monitoramento de performance específico para desenvolvimento de jogos.
 * Estende PerformanceMonitor base com métricas específicas para jogos.
 *
 * @example
 * ```swift
 * let monitor = GamePerformanceMonitor.shared
 * 
 * // Begin frame profiling
 * monitor.beginProfile("gameRender", category: .rendering)
 * // ... render game ...
 * monitor.endProfile("gameRender")
 * monitor.recordFrame()
 * 
 * // Get metrics
 * let metrics = monitor.getOverallMetrics()
 * print("FPS: \(metrics.currentFPS)")
 * 
 * // Generate report
 * let report = monitor.generateReport()
 * monitor.printReport()
 * ```
 */
public class GamePerformanceMonitor {
    
    // MARK: - Singleton
    
    public static let shared = GamePerformanceMonitor()
    
    private init() {}
    
    // MARK: - Private Properties
    
    private var profileSessions: [String: ProfileSession] = [:]
    private var completedProfiles: [ProfileResult] = []
    private let monitorQueue = DispatchQueue(label: "com.appdimens.games.performance", attributes: .concurrent)
    private var frameTimestamps: [CFTimeInterval] = []
    private let maxFrameHistory = 120  // Keep last 2 seconds @ 60 FPS
    
    // Game-specific tracking
    private var renderCalls: Int = 0
    private var physicsCalls: Int = 0
    private var aiCalls: Int = 0
    
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
     * [EN] Performance metrics structure for games.
     * [PT] Estrutura de métricas de performance para jogos.
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
        
        /// Render call count
        public let renderCalls: Int
        
        /// Physics call count
        public let physicsCalls: Int
        
        /// AI call count
        public let aiCalls: Int
    }
    
    /**
     * [EN] Performance report structure for games.
     * [PT] Estrutura de relatório de performance para jogos.
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
     */
    public func beginProfile(_ name: String, category: PerformanceCategory = .general) {
        let startTime = CACurrentMediaTime()
        
        monitorQueue.async(flags: .barrier) {
            let session = ProfileSession(name: name, category: category, startTime: startTime)
            self.profileSessions[name] = session
            
            // Track category-specific calls
            switch category {
            case .rendering:
                self.renderCalls += 1
            case .physics:
                self.physicsCalls += 1
            case .ai:
                self.aiCalls += 1
            default:
                break
            }
        }
    }
    
    /**
     * [EN] Ends profiling for a named operation.
     * [PT] Finaliza o profiling de uma operação nomeada.
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
            
            // Keep only last 2000 results for games (more data)
            if self.completedProfiles.count > 2000 {
                self.completedProfiles.removeFirst(self.completedProfiles.count - 2000)
            }
        }
    }
    
    /**
     * [EN] Records a frame timestamp for FPS calculation.
     * [PT] Registra um timestamp de frame para cálculo de FPS.
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
            
            // Count slow calculations (>1ms for games)
            let slowCalcs = completedProfiles.filter { $0.duration * 1000.0 > 1.0 }.count
            
            // Estimate memory usage
            let cacheStats = GamesCore.getCacheStats()
            let memoryUsage = cacheStats.memoryUsage + Int64(completedProfiles.count * 72)
            
            return PerformanceMetrics(
                averageFrameTime: avgFrameTime,
                minFrameTime: minFrameTime,
                maxFrameTime: maxFrameTime,
                currentFPS: currentFPS,
                totalOperations: totalOps,
                averageOperationTime: avgTime,
                memoryUsage: memoryUsage,
                slowCalculations: slowCalcs,
                renderCalls: renderCalls,
                physicsCalls: physicsCalls,
                aiCalls: aiCalls
            )
        }
    }
    
    /**
     * [EN] Generates a detailed performance report for games.
     * [PT] Gera um relatório detalhado de performance para jogos.
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
            
            // Generate game-specific suggestions
            var suggestions: [String] = []
            
            if metrics.currentFPS < 60 {
                suggestions.append("FPS below 60. Consider HIGH_PERFORMANCE mode: GamesCore.setPerformanceMode(.highPerformance)")
            }
            
            if metrics.currentFPS < 30 {
                suggestions.append("Critical FPS issue. Enable fast math: GamesCore.fastMathEnabled = true")
            }
            
            if metrics.averageFrameTime > 16.67 {
                suggestions.append("Frame time above 60 FPS target (\(String(format: "%.2f", metrics.averageFrameTime))ms). Optimize render loop.")
            }
            
            if metrics.slowCalculations > metrics.totalOperations / 5 {
                suggestions.append("Many slow calculations (\(metrics.slowCalculations)/\(metrics.totalOperations)). Increase cache size: GamesCore.setGlobalCacheSize(2048)")
            }
            
            if metrics.memoryUsage > 50 * 1024 * 1024 {  // > 50MB
                suggestions.append("High memory usage (\(String(format: "%.1f", Double(metrics.memoryUsage) / 1024.0 / 1024.0))MB). Consider LOW_MEMORY mode.")
            }
            
            // Rendering-specific suggestions
            if let renderStats = categoryStats[.rendering], renderStats.averageTime > 10.0 {
                suggestions.append("Rendering is slow (\(String(format: "%.2f", renderStats.averageTime))ms). Optimize draw calls and shaders.")
            }
            
            // Physics-specific suggestions
            if let physicsStats = categoryStats[.physics], physicsStats.averageTime > 5.0 {
                suggestions.append("Physics calculations are slow. Consider fixed timestep or reduce physics objects.")
            }
            
            // Generate summary
            let summary = generateGameSummary(metrics: metrics, categoryStats: categoryStats)
            
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
            self.renderCalls = 0
            self.physicsCalls = 0
            self.aiCalls = 0
        }
    }
    
    // MARK: - Private Helper Methods
    
    private func generateGameSummary(metrics: PerformanceMetrics, categoryStats: [PerformanceCategory: CategoryStats]) -> String {
        var summary = "=== AppDimens Games Performance Report ===\n\n"
        
        // Frame performance
        summary += "🎮 Frame Performance:\n"
        summary += "  FPS: \(String(format: "%.1f", metrics.currentFPS)) "
        summary += metrics.currentFPS >= 60 ? "✅\n" : (metrics.currentFPS >= 30 ? "⚠️\n" : "❌\n")
        summary += "  Average Frame Time: \(String(format: "%.2f", metrics.averageFrameTime))ms\n"
        summary += "  Min/Max Frame Time: \(String(format: "%.2f", metrics.minFrameTime))/\(String(format: "%.2f", metrics.maxFrameTime))ms\n\n"
        
        // Game loop performance
        summary += "🔄 Game Loop:\n"
        summary += "  Render Calls: \(metrics.renderCalls)\n"
        summary += "  Physics Calls: \(metrics.physicsCalls)\n"
        summary += "  AI Calls: \(metrics.aiCalls)\n\n"
        
        // Operation performance
        summary += "⚡ Operation Performance:\n"
        summary += "  Total Operations: \(metrics.totalOperations)\n"
        summary += "  Average Time: \(String(format: "%.3f", metrics.averageOperationTime))ms\n"
        summary += "  Slow Calculations (>1ms): \(metrics.slowCalculations)\n\n"
        
        // Memory usage
        summary += "💾 Memory Usage:\n"
        summary += "  Estimated: \(String(format: "%.2f", Double(metrics.memoryUsage) / 1024.0 / 1024.0))MB\n\n"
        
        // Category breakdown
        if !categoryStats.isEmpty {
            summary += "📊 Category Breakdown:\n"
            let sortedCategories = categoryStats.sorted { $0.value.totalTime > $1.value.totalTime }
            for (category, stats) in sortedCategories {
                if stats.operationCount > 0 {
                    let emoji = getCategoryEmoji(category)
                    summary += "  \(emoji) \(category.rawValue): \(stats.operationCount) ops, "
                    summary += "\(String(format: "%.3f", stats.averageTime))ms avg, "
                    summary += "\(String(format: "%.1f", stats.percentageOfTotal))%\n"
                }
            }
        }
        
        return summary
    }
    
    private func getCategoryEmoji(_ category: PerformanceCategory) -> String {
        switch category {
        case .rendering: return "🎨"
        case .physics: return "⚛️"
        case .ai: return "🤖"
        case .calculation: return "🧮"
        case .caching: return "💾"
        case .layout: return "📐"
        case .general: return "⚙️"
        }
    }
}

// MARK: - Convenience Extensions

public extension GamePerformanceMonitor {
    
    /**
     * [EN] Profiles a closure and returns its result.
     * [PT] Faz profiling de uma closure e retorna seu resultado.
     */
    func profile<T>(_ name: String, category: PerformanceCategory = .general, operation: () -> T) -> T {
        beginProfile(name, category: category)
        let result = operation()
        endProfile(name)
        return result
    }
    
    /**
     * [EN] Profiles a game frame (render loop).
     * [PT] Faz profiling de um frame do jogo (loop de renderização).
     */
    func profileFrame(_ operation: () -> Void) {
        beginProfile("gameFrame", category: .rendering)
        operation()
        endProfile("gameFrame")
        recordFrame()
    }
    
    /**
     * [EN] Gets a simple performance summary string.
     * [PT] Obtém uma string resumida de performance.
     */
    func getSummary() -> String {
        let metrics = getOverallMetrics()
        let fpsIndicator = metrics.currentFPS >= 60 ? "✅" : (metrics.currentFPS >= 30 ? "⚠️" : "❌")
        
        return "FPS: \(String(format: "%.1f", metrics.currentFPS)) \(fpsIndicator) | " +
               "Frame: \(String(format: "%.2f", metrics.averageFrameTime))ms | " +
               "Ops: \(metrics.totalOperations) | " +
               "Mem: \(String(format: "%.1f", Double(metrics.memoryUsage) / 1024.0 / 1024.0))MB"
    }
    
    /**
     * [EN] Prints current performance metrics to console.
     * [PT] Imprime métricas de performance atuais no console.
     */
    func printMetrics() {
        let metrics = getOverallMetrics()
        let fpsIndicator = metrics.currentFPS >= 60 ? "✅" : (metrics.currentFPS >= 30 ? "⚠️" : "❌")
        
        print("\n=== AppDimens Games Performance ===")
        print("🎮 FPS: \(String(format: "%.1f", metrics.currentFPS)) \(fpsIndicator)")
        print("⏱️  Average Frame Time: \(String(format: "%.2f", metrics.averageFrameTime))ms")
        print("🔄 Total Operations: \(metrics.totalOperations)")
        print("⚡ Average Operation: \(String(format: "%.3f", metrics.averageOperationTime))ms")
        print("🐌 Slow Calculations: \(metrics.slowCalculations)")
        print("💾 Memory Usage: \(String(format: "%.2f", Double(metrics.memoryUsage) / 1024.0 / 1024.0))MB")
        print("===================================\n")
    }
    
    /**
     * [EN] Prints full performance report to console.
     * [PT] Imprime relatório completo de performance no console.
     */
    func printReport() {
        let report = generateReport()
        print("\n\(report.summary)")
        
        if !report.suggestions.isEmpty {
            print("💡 Performance Suggestions:")
            for (index, suggestion) in report.suggestions.enumerated() {
                print("  \(index + 1). \(suggestion)")
            }
        }
        
        print("")
    }
    
    /**
     * [EN] Checks if performance is acceptable (>= 30 FPS).
     * [PT] Verifica se a performance é aceitável (>= 30 FPS).
     */
    func isPerformanceAcceptable() -> Bool {
        let metrics = getOverallMetrics()
        return metrics.currentFPS >= 30.0
    }
    
    /**
     * [EN] Checks if performance is optimal (>= 60 FPS).
     * [PT] Verifica se a performance é ótima (>= 60 FPS).
     */
    func isPerformanceOptimal() -> Bool {
        let metrics = getOverallMetrics()
        return metrics.currentFPS >= 60.0
    }
}

