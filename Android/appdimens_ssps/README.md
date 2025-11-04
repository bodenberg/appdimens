# AppDimens SSP Module - Scalable SP for Text

**Pre-Calculated Responsive SP Values for XML Text**  
*Version: 2.0.1*

---

## 📦 Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-ssps:2.0.1")
}
```

---

## 🎯 Features

- ✅ **269 Pre-Calculated Values** (@dimen/_1ssp to @dimen/_600ssp)
- ✅ **XML Native** support for text
- ✅ **Jetpack Compose** extensions
- ✅ **Linear Proportional** scaling
- ✅ **Zero Runtime** calculation

---

## ⚡ Quick Start

### XML Layouts

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_18ssp"
    android:text="Hello World" />
```

### Jetpack Compose

```kotlin
Text(
    text = "Hello",
    fontSize = 18.ssp
)
```

---

## 📐 Formula

```
SSP Value = Base × (Current Screen Width / 300)
```

**Example:** 18ssp on 720dp = 36sp

---

## ⚠️ Recommendation

**Consider upgrading to BALANCED strategy:**
- Better readability on tablets
- Prevents excessive text sizes
- Perceptual scaling

```kotlin
// Instead of
android:textSize="@dimen/_18ssp"

// Use (in Compose)
fontSize = 18.balanced().sp  // Better for tablets!
```

---

## 📚 Documentation

- [Main Android Guide](../README.md)
- [Main Documentation](../../DOCS/README.md)

---

**License:** Apache 2.0
