# AppDimens iOS - Complete API Documentation

**Version: 2.0.0**

---

## API Reference

### Main Class

```swift
AppDimens.shared
```

### Strategies

```swift
.balanced(_)          // Primary ⭐
.defaultScaling(_)    // Secondary
.percentage(_)        // Containers
.logarithmic(_)       // TV
.power(_, exponent:)  // Configurable
.fluid(min:max:)      // Typography
.smart(_)             // Auto-inference
```

### Output Methods

```swift
.toPoints()  // CGFloat
.toPt()      // Shorthand
.toPixels()  // Physical pixels
```

### Configuration

```swift
.screen(_:customValue:)
.baseOrientation(_)
.cache(_)
```

---

## Examples

### SwiftUI

```swift
Text("Hello")
    .font(.system(size: AppDimens.shared.balanced(16).toPoints()))
```

### UIKit

```swift
let size = AppDimens.shared.balanced(48).toPoints()
button.frame.size.height = size
```

---

**Full Documentation:** [../DOCS/README.md](../DOCS/README.md)
