# AppDimens SDP Module - Scalable DP for XML

**Pre-Calculated Responsive DP Values for XML Layouts**  
*Version: 2.0.1*

---

## 📦 Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-sdps:2.0.1")
}
```

---

## 🎯 Features

- ✅ **426 Pre-Calculated Values** (@dimen/_1sdp to @dimen/_600sdp)
- ✅ **XML Native** support
- ✅ **Jetpack Compose** extensions
- ✅ **Linear Proportional** scaling
- ✅ **Zero Runtime** calculation

---

## ⚡ Quick Start

### XML Layouts

```xml
<TextView
    android:layout_width="@dimen/_300sdp"
    android:layout_height="wrap_content"
    android:padding="@dimen/_16sdp"
    android:text="Hello World" />
```

### Jetpack Compose

```kotlin
Text(
    text = "Hello",
    modifier = Modifier.padding(16.sdp)
)
```

---

## 📐 Formula

```
SDP Value = Base × (Current Screen Width / 300)
```

**Example:** 16sdp on 720dp = 32dp

---

## ⚠️ Recommendation

**Consider upgrading to BALANCED strategy:**
- 40% better control on tablets
- Runtime flexibility
- Perceptual scaling

```kotlin
// Instead of
android:padding="@dimen/_16sdp"

// Use (in Compose)
.padding(16.balanced().dp)  // Better for tablets!
```

---

## 📚 Documentation

- [Main Android Guide](../README.md)
- [Migration from SDP](../../DOCS/FORMULA_COMPARISON.md#101-from-sdpssp-to-appdimens)

---

**License:** Apache 2.0
