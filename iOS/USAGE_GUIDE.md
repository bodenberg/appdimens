# AppDimens iOS - Usage Guide

**Version: 2.0.0**

---

## Quick Start

### SwiftUI

```swift
struct MyView: View {
    var body: some View {
        VStack(spacing: AppDimens.shared.balanced(16).toPoints()) {
            Text("Hello World")
                .font(.system(size: AppDimens.shared.balanced(18).toPoints()))
            
            Button("Click Me") { }
                .frame(height: AppDimens.shared.balanced(48).toPoints())
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
    }
}
```

### UIKit

```swift
class MyViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let label = UILabel()
        label.font = .systemFont(
            ofSize: AppDimens.shared.balanced(18).toPoints()
        )
        view.addSubview(label)
    }
}
```

---

## Strategies

### BALANCED ⭐ (Primary)

```swift
AppDimens.shared.balanced(16).toPoints()
```

### DEFAULT (Secondary)

```swift
AppDimens.shared.defaultScaling(24).toPoints()
```

### Others

```swift
AppDimens.shared.percentage(300).toPoints()
AppDimens.shared.logarithmic(20).toPoints()
AppDimens.shared.power(16, exponent: 0.75).toPoints()
AppDimens.shared.fluid(min: 14, max: 20).toPoints()
```

---

**Full Documentation:** [../DOCS/README.md](../DOCS/README.md)
