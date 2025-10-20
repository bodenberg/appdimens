import XCTest
@testable import AppDimens
@testable import AppDimensUI
@testable import AppDimensGames

final class AppDimensTests: XCTestCase {
    
    func testAppDimensFixed() throws {
        let fixed = AppDimens.shared.fixed(16.0)
        let result = fixed.toPoints()
        XCTAssertGreaterThan(result, 0, "Fixed dimension should be greater than 0")
    }
    
    func testAppDimensDynamic() throws {
        let dynamic = AppDimens.shared.dynamic(16.0)
        let result = dynamic.toPoints()
        XCTAssertGreaterThan(result, 0, "Dynamic dimension should be greater than 0")
    }
    
    func testAppDimensGlobal() throws {
        XCTAssertTrue(AppDimensGlobal.globalCacheEnabled, "Global cache should be enabled by default")
        
        AppDimensGlobal.globalCacheEnabled = false
        XCTAssertFalse(AppDimensGlobal.globalCacheEnabled, "Global cache should be disabled")
        
        AppDimensGlobal.globalCacheEnabled = true
        XCTAssertTrue(AppDimensGlobal.globalCacheEnabled, "Global cache should be enabled again")
    }
    
    func testCacheControl() throws {
        let dynamic = AppDimens.shared.dynamic(16.0)
        
        // Test individual cache control
        XCTAssertTrue(dynamic.enableCache, "Individual cache should be enabled by default")
        
        dynamic.cache(enable: false)
        XCTAssertFalse(dynamic.enableCache, "Individual cache should be disabled")
        
        dynamic.cache(enable: true)
        XCTAssertTrue(dynamic.enableCache, "Individual cache should be enabled again")
    }
    
    func testScreenQualifiers() throws {
        let dynamic = AppDimens.shared.dynamic(16.0)
        
        // Test screen qualifiers
        dynamic.screen(.smallWidth, 320, 16.0)
        dynamic.screen(.height, 568, 16.0)
        dynamic.screen(.width, 320, 16.0)
        
        let result = dynamic.toPoints()
        XCTAssertGreaterThan(result, 0, "Screen qualifier should work")
    }
    
    func testUiModeQualifiers() throws {
        let dynamic = AppDimens.shared.dynamic(16.0)
        
        // Test UI mode qualifiers
        dynamic.screen(.normal, 16.0)
        dynamic.screen(.carPlay, 16.0)
        dynamic.screen(.tv, 16.0)
        
        let result = dynamic.toPoints()
        XCTAssertGreaterThan(result, 0, "UI mode qualifier should work")
    }
    
    func testIntersectionQualifiers() throws {
        let dynamic = AppDimens.shared.dynamic(16.0)
        
        // Test intersection qualifiers
        let entry = UiModeQualifierEntry(uiModeType: .normal, dpQualifierEntry: DpQualifierEntry(qualifier: .smallWidth, value: 320))
        dynamic.screen(entry, 16.0)
        
        let result = dynamic.toPoints()
        XCTAssertGreaterThan(result, 0, "Intersection qualifier should work")
    }
}
