# AppDimens iOS - Development Prompt

**Quick Reference for AI Assistants and Developers**  
*Version: 2.0.0*

---

## Core Principles

1. **Use BALANCED ⭐ for 95% of UI** (primary)
2. **Use DEFAULT for iPhone-only** (secondary)
3. **Use PERCENTAGE for containers** (specific)
4. **13 strategies available**
5. **5x performance** vs v1.x

---

## API Quick Reference

### SwiftUI

```swift
// PRIMARY: BALANCED ⭐
AppDimens.shared.balanced(16).toPoints()

// SECONDARY: DEFAULT
AppDimens.shared.defaultScaling(24).toPoints()

// Containers: PERCENTAGE
AppDimens.shared.percentage(300).toPoints()

// Others
AppDimens.shared.logarithmic(20).toPoints()
AppDimens.shared.power(16, exponent: 0.75).toPoints()
AppDimens.shared.fluid(min: 14, max: 20).toPoints()

// Smart API
AppDimens.shared.smart(48).forElement(.button).toPoints()
```

---

## Installation

**CocoaPods:** `pod 'AppDimens', '~> 2.0.0'`  
**SPM:** `https://github.com/bodenberg/appdimens.git`

---

## Strategy Selection

- Multi-device (iPhone + iPad + TV) → BALANCED ⭐
- iPhone-only → DEFAULT
- Apple TV → LOGARITHMIC
- Typography → FLUID
- Games → FIT/FILL

---

**Full Documentation:** [README.md](README.md)
