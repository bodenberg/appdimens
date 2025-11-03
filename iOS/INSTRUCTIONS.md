# AppDimens iOS - Instructions

**Version: 2.0.0**

---

## Quick Instructions

1. **Install:** Add via CocoaPods or SPM
2. **Import:** `import AppDimens`
3. **Use:** `AppDimens.shared.balanced(16).toPoints()`

---

## Strategies

- **Primary:** `balanced()` ⭐
- **Secondary:** `defaultScaling()`
- **Containers:** `percentage()`
- **TV:** `logarithmic()`
- **Typography:** `fluid(min:max:)`

---

## Example

```swift
struct MyView: View {
    var body: some View {
        Text("Hello")
            .font(.system(size: AppDimens.shared.balanced(16).toPoints()))
    }
}
```

---

**Full Guide:** [README.md](README.md)
