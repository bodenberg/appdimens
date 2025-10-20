/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-27
 *
 * Library: AppDimens Games - 3D Performance Tests (iOS)
 *
 * Description:
 * Comprehensive performance tests for 3D game optimizations on iOS.
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

import XCTest
import AppDimensGames
import Metal
import MetalKit

/**
 * [EN] Performance tests for 3D game optimizations on iOS.
 * [PT] Testes de performance para otimizações de jogos 3D no iOS.
 */
@available(iOS 13.0, *)
class Game3DPerformanceTests: XCTestCase {
    
    var appDimensGames3D: AppDimensGames3D!
    var testResults: [TestResult] = []
    
    override func setUpWithError() throws {
        appDimensGames3D = AppDimensGames3D.shared
        testResults = []
        
        // Initialize with default 3D settings
        let settings = Game3DPerformanceSettings.default3D
        appDimensGames3D.initializeFor3D(settings: settings)
    }
    
    override func tearDownWithError() throws {
        appDimensGames3D.shutdown()
        appDimensGames3D = nil
        testResults = []
    }
    
    /**
     * [EN] Tests cache performance with different priority levels.
     * [PT] Testa performance do cache com diferentes níveis de prioridade.
     */
    func testCachePerformance() throws {
        print("=== Cache Performance Test ===")
        
        let testCases: [(CachePriority, Int)] = [
            (.criticalUI, 1000),
            (.normalUI, 2000),
            (.gameObjects, 1500),
            (.background, 1000)
        ]
        
        for (priority, iterations) in testCases {
            let startTime = CFAbsoluteTimeGetCurrent()
            
            for i in 0..<iterations {
                let baseValue = Float(i % 100)
                let elementType = UIElementType.allCases[i % UIElementType.allCases.count]
                
                switch priority {
                case .criticalUI:
                    _ = appDimensGames3D.calculateHUDElement(baseValue, type: elementType)
                case .normalUI:
                    _ = appDimensGames3D.calculateMenuElement(baseValue, type: elementType)
                case .gameObjects, .background:
                    _ = appDimensGames3D.calculateUIElement(baseValue, type: elementType)
                }
            }
            
            let time = CFAbsoluteTimeGetCurrent() - startTime
            let metrics = appDimensGames3D.getPerformanceMetrics()
            
            let result = TestResult(
                testName: "Cache Performance - \(priority)",
                iterations: iterations,
                totalTime: time * 1000, // Convert to milliseconds
                averageTime: (time * 1000) / Double(iterations),
                cacheHitRate: metrics.cacheHitRate,
                memoryUsage: metrics.cpuMemoryUsage
            )
            
            testResults.append(result)
            print("Cache \(priority): \(time * 1000)ms for \(iterations) iterations (\((time * 1000) / Double(iterations))ms avg)")
        }
    }
    
    /**
     * [EN] Tests async calculation performance.
     * [PT] Testa performance de cálculos assíncronos.
     */
    func testAsyncCalculationPerformance() throws {
        print("=== Async Calculation Performance Test ===")
        
        let iterations = 1000
        let expectation = XCTestExpectation(description: "Async calculations completed")
        expectation.expectedFulfillmentCount = iterations
        
        let startTime = CFAbsoluteTimeGetCurrent()
        
        for i in 0..<iterations {
            let baseValue = Float(i % 100)
            let elementType = UIElementType.allCases[i % UIElementType.allCases.count]
            
            appDimensGames3D.calculateUIElementAsync(baseValue, type: elementType) { _ in
                expectation.fulfill()
            }
        }
        
        wait(for: [expectation], timeout: 10.0)
        
        let totalTime = CFAbsoluteTimeGetCurrent() - startTime
        let metrics = appDimensGames3D.getPerformanceMetrics()
        
        let result = TestResult(
            testName: "Async Calculation Performance",
            iterations: iterations,
            totalTime: totalTime * 1000,
            averageTime: (totalTime * 1000) / Double(iterations),
            asyncRatio: metrics.asyncCalculationRatio,
            queuedCalculations: metrics.queuedAsyncCalculations
        )
        
        testResults.append(result)
        print("Async calculations: \(totalTime * 1000)ms for \(iterations) iterations (\((totalTime * 1000) / Double(iterations))ms avg)")
    }
    
    /**
     * [EN] Tests memory pressure handling.
     * [PT] Testa tratamento de pressão de memória.
     */
    func testMemoryPressureHandling() throws {
        print("=== Memory Pressure Handling Test ===")
        
        let iterations = 5000
        let startTime = CFAbsoluteTimeGetCurrent()
        
        // Generate high memory pressure
        for i in 0..<iterations {
            let baseValue = Float(i % 200)
            let elementType = UIElementType.allCases[i % UIElementType.allCases.count]
            
            _ = appDimensGames3D.calculateUIElement(baseValue, type: elementType)
            _ = appDimensGames3D.calculateHUDElement(baseValue, type: elementType)
            _ = appDimensGames3D.calculateMenuElement(baseValue, type: elementType)
        }
        
        let totalTime = CFAbsoluteTimeGetCurrent() - startTime
        let metrics = appDimensGames3D.getPerformanceMetrics()
        
        let result = TestResult(
            testName: "Memory Pressure Handling",
            iterations: iterations * 3, // 3 calculations per iteration
            totalTime: totalTime * 1000,
            averageTime: (totalTime * 1000) / Double(iterations * 3),
            memoryUsage: metrics.cpuMemoryUsage,
            cacheHitRate: metrics.cacheHitRate
        )
        
        testResults.append(result)
        print("Memory pressure test: \(totalTime * 1000)ms for \(iterations * 3) calculations")
    }
    
    /**
     * [EN] Tests emergency mode performance.
     * [PT] Testa performance do modo de emergência.
     */
    func testEmergencyModePerformance() throws {
        print("=== Emergency Mode Performance Test ===")
        
        let iterations = 1000
        
        // Test normal mode
        let normalStartTime = CFAbsoluteTimeGetCurrent()
        for i in 0..<iterations {
            let baseValue = Float(i % 100)
            let elementType = UIElementType.allCases[i % UIElementType.allCases.count]
            _ = appDimensGames3D.calculateUIElement(baseValue, type: elementType)
        }
        let normalTime = CFAbsoluteTimeGetCurrent() - normalStartTime
        
        let normalMetrics = appDimensGames3D.getPerformanceMetrics()
        
        // Enable emergency mode
        appDimensGames3D.enableEmergencyMode()
        
        let emergencyStartTime = CFAbsoluteTimeGetCurrent()
        for i in 0..<iterations {
            let baseValue = Float(i % 100)
            let elementType = UIElementType.allCases[i % UIElementType.allCases.count]
            _ = appDimensGames3D.calculateUIElement(baseValue, type: elementType)
        }
        let emergencyTime = CFAbsoluteTimeGetCurrent() - emergencyStartTime
        
        let emergencyMetrics = appDimensGames3D.getPerformanceMetrics()
        
        // Disable emergency mode
        appDimensGames3D.disableEmergencyMode()
        
        let normalResult = TestResult(
            testName: "Normal Mode Performance",
            iterations: iterations,
            totalTime: normalTime * 1000,
            averageTime: (normalTime * 1000) / Double(iterations),
            memoryUsage: normalMetrics.cpuMemoryUsage,
            cacheHitRate: normalMetrics.cacheHitRate
        )
        
        let emergencyResult = TestResult(
            testName: "Emergency Mode Performance",
            iterations: iterations,
            totalTime: emergencyTime * 1000,
            averageTime: (emergencyTime * 1000) / Double(iterations),
            memoryUsage: emergencyMetrics.cpuMemoryUsage,
            cacheHitRate: emergencyMetrics.cacheHitRate,
            emergencyMode: true
        )
        
        testResults.append(normalResult)
        testResults.append(emergencyResult)
        
        print("Normal mode: \(normalTime * 1000)ms, Emergency mode: \(emergencyTime * 1000)ms")
    }
    
    /**
     * [EN] Tests different quality settings performance.
     * [PT] Testa performance de diferentes configurações de qualidade.
     */
    func testQualitySettingsPerformance() throws {
        print("=== Quality Settings Performance Test ===")
        
        let qualitySettings: [(String, Game3DPerformanceSettings)] = [
            ("Default 3D", .default3D),
            ("High Performance 3D", .highPerformance3D),
            ("Low Performance 3D", .lowPerformance3D)
        ]
        
        for (name, settings) in qualitySettings {
            appDimensGames3D.updateSettings(settings)
            
            let iterations = 1000
            let startTime = CFAbsoluteTimeGetCurrent()
            
            for i in 0..<iterations {
                let baseValue = Float(i % 100)
                let elementType = UIElementType.allCases[i % UIElementType.allCases.count]
                _ = appDimensGames3D.calculateUIElement(baseValue, type: elementType)
            }
            
            let time = CFAbsoluteTimeGetCurrent() - startTime
            let metrics = appDimensGames3D.getPerformanceMetrics()
            
            let result = TestResult(
                testName: "Quality Settings - \(name)",
                iterations: iterations,
                totalTime: time * 1000,
                averageTime: (time * 1000) / Double(iterations),
                memoryUsage: metrics.cpuMemoryUsage,
                cacheHitRate: metrics.cacheHitRate
            )
            
            testResults.append(result)
            print("\(name): \(time * 1000)ms for \(iterations) iterations")
        }
    }
    
    /**
     * [EN] Tests concurrent access performance.
     * [PT] Testa performance de acesso concorrente.
     */
    func testConcurrentAccessPerformance() throws {
        print("=== Concurrent Access Performance Test ===")
        
        let threadCount = 4
        let iterationsPerThread = 250
        let totalIterations = threadCount * iterationsPerThread
        
        let startTime = CFAbsoluteTimeGetCurrent()
        let group = DispatchGroup()
        
        for _ in 0..<threadCount {
            group.enter()
            DispatchQueue.global(qos: .userInitiated).async {
                for i in 0..<iterationsPerThread {
                    let baseValue = Float(i % 100)
                    let elementType = UIElementType.allCases[i % UIElementType.allCases.count]
                    _ = self.appDimensGames3D.calculateUIElement(baseValue, type: elementType)
                }
                group.leave()
            }
        }
        
        group.wait()
        
        let totalTime = CFAbsoluteTimeGetCurrent() - startTime
        let metrics = appDimensGames3D.getPerformanceMetrics()
        
        let result = TestResult(
            testName: "Concurrent Access Performance",
            iterations: totalIterations,
            totalTime: totalTime * 1000,
            averageTime: (totalTime * 1000) / Double(totalIterations),
            memoryUsage: metrics.cpuMemoryUsage,
            cacheHitRate: metrics.cacheHitRate,
            threadCount: threadCount
        )
        
        testResults.append(result)
        print("Concurrent access: \(totalTime * 1000)ms for \(totalIterations) iterations across \(threadCount) threads")
    }
    
    /**
     * [EN] Tests Metal integration performance.
     * [PT] Testa performance de integração Metal.
     */
    func testMetalIntegrationPerformance() throws {
        print("=== Metal Integration Performance Test ===")
        
        guard let device = MTLCreateSystemDefaultDevice() else {
            XCTSkip("Metal not available on this device")
            return
        }
        
        let iterations = 1000
        let startTime = CFAbsoluteTimeGetCurrent()
        
        for i in 0..<iterations {
            let baseValue = Float(i % 100)
            let elementType = UIElementType.allCases[i % UIElementType.allCases.count]
            
            // Test with Metal device available
            _ = appDimensGames3D.calculateUIElement(baseValue, type: elementType)
        }
        
        let totalTime = CFAbsoluteTimeGetCurrent() - startTime
        let metrics = appDimensGames3D.getPerformanceMetrics()
        
        let result = TestResult(
            testName: "Metal Integration Performance",
            iterations: iterations,
            totalTime: totalTime * 1000,
            averageTime: (totalTime * 1000) / Double(iterations),
            memoryUsage: metrics.cpuMemoryUsage,
            cacheHitRate: metrics.cacheHitRate,
            gpuMemoryUsage: metrics.gpuMemoryUsage
        )
        
        testResults.append(result)
        print("Metal integration: \(totalTime * 1000)ms for \(iterations) iterations")
    }
    
    /**
     * [EN] Generates comprehensive performance report.
     * [PT] Gera relatório abrangente de performance.
     */
    func testGeneratePerformanceReport() throws {
        print("=== Generating Performance Report ===")
        
        let report = generatePerformanceReport()
        print(report)
        
        // Also generate the library's own performance report
        let libraryReport = appDimensGames3D.generatePerformanceReport()
        print("Library Performance Report:\n\(libraryReport)")
        
        // Assert that we have test results
        XCTAssertFalse(testResults.isEmpty, "No test results available")
    }
    
    // MARK: - Helper Methods
    
    private func generatePerformanceReport() -> String {
        var report = "AppDimens Games 3D Performance Test Report\n"
        report += "==========================================\n\n"
        
        report += "Test Results Summary:\n\n"
        
        for result in testResults {
            report += "Test: \(result.testName)\n"
            report += "- Iterations: \(result.iterations)\n"
            report += "- Total Time: \(String(format: "%.2f", result.totalTime))ms\n"
            report += "- Average Time: \(String(format: "%.3f", result.averageTime))ms\n"
            report += "- Cache Hit Rate: \(String(format: "%.1f", result.cacheHitRate * 100))%\n"
            report += "- Memory Usage: \(String(format: "%.1f", result.memoryUsage * 100))%\n"
            
            if let asyncRatio = result.asyncRatio {
                report += "- Async Ratio: \(String(format: "%.1f", asyncRatio * 100))%\n"
            }
            if let queuedCalculations = result.queuedCalculations {
                report += "- Queued Calculations: \(queuedCalculations)\n"
            }
            if let threadCount = result.threadCount {
                report += "- Thread Count: \(threadCount)\n"
            }
            if let gpuMemoryUsage = result.gpuMemoryUsage {
                report += "- GPU Memory Usage: \(String(format: "%.1f", gpuMemoryUsage * 100))%\n"
            }
            if result.emergencyMode == true {
                report += "- Emergency Mode: Active\n"
            }
            report += "\n"
        }
        
        // Performance analysis
        report += "Performance Analysis:\n\n"
        
        let averageTime = testResults.map { $0.averageTime }.reduce(0, +) / Double(testResults.count)
        let averageCacheHitRate = testResults.map { $0.cacheHitRate }.reduce(0, +) / Float(testResults.count)
        let averageMemoryUsage = testResults.map { $0.memoryUsage }.reduce(0, +) / Float(testResults.count)
        
        report += "- Average Calculation Time: \(String(format: "%.3f", averageTime))ms\n"
        report += "- Average Cache Hit Rate: \(String(format: "%.1f", averageCacheHitRate * 100))%\n"
        report += "- Average Memory Usage: \(String(format: "%.1f", averageMemoryUsage * 100))%\n\n"
        
        // Recommendations
        report += "Recommendations:\n\n"
        
        if averageTime > 1.0 {
            report += "- Consider optimizing calculation algorithms (average time > 1ms)\n"
        }
        if averageCacheHitRate < 0.8 {
            report += "- Consider increasing cache sizes (hit rate < 80%)\n"
        }
        if averageMemoryUsage > 0.7 {
            report += "- Monitor memory usage closely (usage > 70%)\n"
        }
        
        let emergencyResult = testResults.first { $0.emergencyMode == true }
        let normalResult = testResults.first { $0.testName == "Normal Mode Performance" }
        
        if let emergency = emergencyResult, let normal = normalResult {
            let performanceGain = ((normal.averageTime - emergency.averageTime) / normal.averageTime) * 100
            report += "- Emergency mode provides \(String(format: "%.1f", performanceGain))% performance improvement\n"
        }
        
        return report
    }
}

// MARK: - Test Result Data Structure

/**
 * [EN] Data structure for test results.
 * [PT] Estrutura de dados para resultados de teste.
 */
struct TestResult {
    let testName: String
    let iterations: Int
    let totalTime: Double
    let averageTime: Double
    let cacheHitRate: Float
    let memoryUsage: Float
    let asyncRatio: Float?
    let queuedCalculations: Int?
    let threadCount: Int?
    let gpuMemoryUsage: Float?
    let emergencyMode: Bool?
    
    init(
        testName: String,
        iterations: Int,
        totalTime: Double,
        averageTime: Double,
        cacheHitRate: Float = 0.0,
        memoryUsage: Float = 0.0,
        asyncRatio: Float? = nil,
        queuedCalculations: Int? = nil,
        threadCount: Int? = nil,
        gpuMemoryUsage: Float? = nil,
        emergencyMode: Bool? = nil
    ) {
        self.testName = testName
        self.iterations = iterations
        self.totalTime = totalTime
        self.averageTime = averageTime
        self.cacheHitRate = cacheHitRate
        self.memoryUsage = memoryUsage
        self.asyncRatio = asyncRatio
        self.queuedCalculations = queuedCalculations
        self.threadCount = threadCount
        self.gpuMemoryUsage = gpuMemoryUsage
        self.emergencyMode = emergencyMode
    }
}

// MARK: - UIElementType Extension

extension UIElementType: CaseIterable {
    public static var allCases: [UIElementType] {
        return [.hudScore, .hudHealth, .hudAmmo, .menuButton, .menuTitle, .tooltip, .notification, .loadingIndicator]
    }
}
