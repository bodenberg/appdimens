# AppDimens All Module - All-in-One Package

**Complete AppDimens for Android (Dynamic + SDP + SSP)**  
*Version: 2.0.1*

---

## 📦 Installation

```kotlin
dependencies {
    // All-in-one package
    implementation("io.github.bodenberg:appdimens-all:2.0.1")
}
```

**Includes:**
- ✅ appdimens-dynamic (13 strategies + Physical Units)
- ✅ appdimens-sdps (426 XML DP values)
- ✅ appdimens-ssps (269 XML SP values)

**Excludes:**
- ❌ appdimens-games (install separately if needed)

---

## 🎯 Features

**Everything from dynamic, sdps, and ssps modules:**

- 13 Scaling Strategies (BALANCED⭐, DEFAULT, PERCENTAGE, etc.)
- XML Support (@dimen/_16sdp, @dimen/_18ssp)
- Jetpack Compose extensions
- Physical Units (mm, cm, inch)
- Smart Inference system
- 5x Performance improvements

---

## ⚡ Usage

**Jetpack Compose:**
```kotlin
Text("Hello", fontSize = 16.balanced().sp)  // Dynamic
```

**XML:**
```xml
<TextView android:textSize="@dimen/_16ssp" />  // SDP/SSP
```

---

## 📚 Documentation

- [Dynamic Module](../appdimens_dynamic/README.md)
- [SDP Module](../appdimens_sdps/README.md)
- [SSP Module](../appdimens_ssps/README.md)
- [Main Android Guide](../README.md)

---

**License:** Apache 2.0
