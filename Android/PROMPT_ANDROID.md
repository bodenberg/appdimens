# AppDimens Android - Development Prompt

**Quick Reference for AI Assistants and Developers**  
*Version: 2.0.0*

---

## Core Principles

1. **Use BALANCED ⭐ for 95% of UI elements** (primary recommendation)
2. **Use DEFAULT for phone-only apps** (secondary recommendation)
3. **Use PERCENTAGE for large containers only** (specific use)
4. **13 strategies available** for all use cases
5. **5x performance** vs v1.x

---

## API Quick Reference

### Jetpack Compose

```kotlin
// PRIMARY: BALANCED ⭐
16.balanced().dp
16.balanced().sp

// SECONDARY: DEFAULT
16.defaultDp
16.defaultSp

// Containers: PERCENTAGE
300.percentageDp.dp

// Others
16.logarithmic().sp
16.power(0.75f).sp
fluidSp(14f, 20f)

// Smart API
AppDimens.from(48).smart().forElement(ElementType.BUTTON).dp
```

### XML (SDP/SSP)

```xml
<TextView
    android:textSize="@dimen/_18ssp"
    android:padding="@dimen/_16sdp"
    android:layout_width="@dimen/_300sdp" />
```

---

## Dependencies

```kotlin
implementation("io.github.bodenberg:appdimens-dynamic:2.0.0")  // Core (13 strategies)
implementation("io.github.bodenberg:appdimens-sdps:2.0.0")     // SDP (XML)
implementation("io.github.bodenberg:appdimens-ssps:2.0.0")     // SSP (XML)
implementation("io.github.bodenberg:appdimens-all:2.0.0")      // All-in-one
implementation("io.github.bodenberg:appdimens-games:2.0.0")    // Games (C++/NDK)
```

---

## Strategy Selection

- Multi-device → BALANCED ⭐
- Phone-only → DEFAULT
- Containers → PERCENTAGE
- TV → LOGARITHMIC
- Typography → FLUID
- Games → FIT/FILL

---

**Full Documentation:** [../DOCS/README.md](../DOCS/README.md)
