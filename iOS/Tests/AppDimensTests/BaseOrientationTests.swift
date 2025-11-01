/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Library: AppDimens iOS - Base Orientation Tests
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
@testable import AppDimens

final class BaseOrientationTests: XCTestCase {
    
    func testResolveScreenType_Auto_NoInversion() {
        let portraitBounds = CGRect(x: 0, y: 0, width: 360, height: 800)
        
        // AUTO should never invert
        let result = AppDimensAdjustmentFactors.resolveScreenType(
            requestedType: .lowest,
            baseOrientation: .auto,
            bounds: portraitBounds
        )
        
        XCTAssertEqual(result, .lowest)
    }
    
    func testResolveScreenType_Portrait_InPortrait_NoInversion() {
        let portraitBounds = CGRect(x: 0, y: 0, width: 360, height: 800)
        
        // Design for portrait, currently in portrait - no inversion
        let result = AppDimensAdjustmentFactors.resolveScreenType(
            requestedType: .lowest,
            baseOrientation: .portrait,
            bounds: portraitBounds
        )
        
        XCTAssertEqual(result, .lowest)
    }
    
    func testResolveScreenType_Portrait_InLandscape_Inverts() {
        let landscapeBounds = CGRect(x: 0, y: 0, width: 800, height: 360)
        
        // Design for portrait, currently in landscape - should invert
        let resultLowest = AppDimensAdjustmentFactors.resolveScreenType(
            requestedType: .lowest,
            baseOrientation: .portrait,
            bounds: landscapeBounds
        )
        
        XCTAssertEqual(resultLowest, .highest) // Inverted!
        
        let resultHighest = AppDimensAdjustmentFactors.resolveScreenType(
            requestedType: .highest,
            baseOrientation: .portrait,
            bounds: landscapeBounds
        )
        
        XCTAssertEqual(resultHighest, .lowest) // Inverted!
    }
    
    func testResolveScreenType_Landscape_InLandscape_NoInversion() {
        let landscapeBounds = CGRect(x: 0, y: 0, width: 800, height: 360)
        
        // Design for landscape, currently in landscape - no inversion
        let result = AppDimensAdjustmentFactors.resolveScreenType(
            requestedType: .highest,
            baseOrientation: .landscape,
            bounds: landscapeBounds
        )
        
        XCTAssertEqual(result, .highest)
    }
    
    func testResolveScreenType_Landscape_InPortrait_Inverts() {
        let portraitBounds = CGRect(x: 0, y: 0, width: 360, height: 800)
        
        // Design for landscape, currently in portrait - should invert
        let resultLowest = AppDimensAdjustmentFactors.resolveScreenType(
            requestedType: .lowest,
            baseOrientation: .landscape,
            bounds: portraitBounds
        )
        
        XCTAssertEqual(resultLowest, .highest) // Inverted!
    }
    
    func testRealWorldScenario_CardInPortraitDesign() {
        // Scenario: Card designed for portrait at 300pt width
        // In portrait (360x800): should use width (360)
        // In landscape (800x360): should auto-invert to maintain proportion
        
        let portraitBounds = CGRect(x: 0, y: 0, width: 360, height: 800)
        let landscapeBounds = CGRect(x: 0, y: 0, width: 800, height: 360)
        
        // Portrait: LOWEST stays LOWEST (uses width = 360)
        let portraitType = AppDimensAdjustmentFactors.resolveScreenType(
            .lowest,
            .portrait,
            portraitBounds
        )
        XCTAssertEqual(portraitType, .lowest)
        
        // Landscape: LOWEST becomes HIGHEST (uses height = 360, not width = 800)
        let landscapeType = AppDimensAdjustmentFactors.resolveScreenType(
            .lowest,
            .portrait,
            landscapeBounds
        )
        XCTAssertEqual(landscapeType, .highest)
    }
    
    func testFixedCalculator_WithBaseOrientation() {
        // Test that AppDimensFixedCalculator properly uses base orientation
        let portraitBounds = CGRect(x: 0, y: 0, width: 360, height: 800)
        
        let fixedCalc = AppDimensFixedCalculator(48)
            .baseOrientation(.portrait)
            .screen(type: .lowest)
        
        // Should calculate without errors
        let result = fixedCalc.calculate(bounds: portraitBounds)
        XCTAssertGreaterThan(result, 0)
    }
    
    func testDynamicCalculator_WithBaseOrientation() {
        // Test that AppDimensDynamicCalculator properly uses base orientation
        let landscapeBounds = CGRect(x: 0, y: 0, width: 800, height: 360)
        
        let dynamicCalc = AppDimensDynamicCalculator(100)
            .baseOrientation(.landscape)
            .screen(type: .highest)
        
        // Should calculate without errors
        let result = dynamicCalc.calculate(bounds: landscapeBounds)
        XCTAssertGreaterThan(result, 0)
    }
}

