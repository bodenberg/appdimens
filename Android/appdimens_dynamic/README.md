# AppDimens Dynamic Module - Core Library

**13 Scaling Strategies + Physical Units + Grid Calculations**  
*Version: 2.0.0*

---

## 📦 Installation

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:2.0.0")
}
```

---

## 🎯 Features

- ✅ **13 Scaling Strategies** (BALANCED⭐, DEFAULT, PERCENTAGE, LOGARITHMIC, POWER, FLUID, and 7 more)
- ✅ **Physical Units** (mm, cm, inch)
- ✅ **Grid Calculations** (item count, spacing)
- ✅ **Jetpack Compose** support
- ✅ **View System** support
- ✅ **Smart Inference** system
- ✅ **5x Performance** improvements

---

## ⚡ Quick Start

### Jetpack Compose

```kotlin
@Composable
fun MyCard() {
    Card(
        modifier = Modifier
            .width(300.balanced().dp)      // ⭐ BALANCED (Primary)
            .padding(16.balanced().dp)
    ) {
        Text(
            text = "Hello",
            fontSize = 18.balanced().sp
        )
    }
}
```

### View System

```kotlin
val width = 300.balanced().toPx(resources)
view.layoutParams.width = width.toInt()
```

---

## 🎯 Strategies

**Primary:** `BALANCED` ⭐  
**Secondary:** `DEFAULT`  
**Containers:** `PERCENTAGE`  
**TV:** `LOGARITHMIC`  
**Typography:** `FLUID`  
**Games:** `FIT`, `FILL`  
**Dynamic Text:** `AUTOSIZE` 🆕  
**And more:** POWER, INTERPOLATED, DIAGONAL, PERIMETER, NONE

---

## 📚 Documentation

- [Main Android Guide](../README.md)
- [Complete Documentation](../../DOCS/README.md)
- [Mathematical Theory](../../DOCS/MATHEMATICAL_THEORY.md)

---

**License:** Apache 2.0
