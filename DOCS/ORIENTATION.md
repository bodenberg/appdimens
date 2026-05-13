# Base orientation and auto inversion

Portrait-first designs should not feel narrow once the handset rotates—**base orientation** encodes where the PSD was authored so `LOWEST` / `HIGHEST` biases can invert across rotation.

Companion docs: **[PLATFORMS.md](PLATFORMS.md)** · **[GUIDE.md](GUIDE.md)** · **[EXAMPLES.md](EXAMPLES.md)**.

---

## Table of Contents

1. [Overview](#1-overview)
2. [The Problem](#2-the-problem)
3. [The Solution](#3-the-solution)
4. [How It Works](#4-how-it-works)
5. [API Reference](#5-api-reference)
6. [Examples by Strategy](#6-examples-by-strategy)
7. [Best Practices](#7-best-practices)
8. [Platform Notes](#8-platform-notes)

---

## 1. Overview

**Base orientation** declares the posture your layout was authored in. During rotation AppDimens can swap `LOWEST` and `HIGHEST` so proportions stay humane.

```
PORTRAIT design + LANDSCAPE current -> invert LOWEST<->HIGHEST
LANDSCAPE design + PORTRAIT current -> invert LOWEST<->HIGHEST
AUTO (library default)                -> skip inversion
```

Works with every conceptual strategy because it only selects which axis feeds the scaler.

---

## 2. The Problem

### Without base orientation

```kotlin
// Portrait card mock (360x800). On Android 3.x wire rotation via inverter docs in appdimens-dynamic.
val cardWidth = 300.wdp

// Portrait: width biased token tracks short edge
// Landscape (800x360): without inversion the card can stay visually too narrow
```

---

## 3. The Solution

```kotlin
// Prefer portraitLowest() / sdpRotate equivalents from the submodule README when you ship rotation.
val cardWidth = 300.wdp
```

Benefits:

- Automatic adaptation driven by authored orientation metadata
- Less bespoke `remember` juggling of width versus height swaps
- Cohesive with the broader kernel catalog

---

## 4. How It Works

### Orientation detection snapshot

```
if (height > width) -> CURRENT = PORTRAIT
else                -> CURRENT = LANDSCAPE
```

### Inversion logic

| Design base | Actual orientation | Invert? |
|-------------|-------------------|--------|
| PORTRAIT | PORTRAIT | NO |
| PORTRAIT | LANDSCAPE | YES |
| LANDSCAPE | LANDSCAPE | NO |
| LANDSCAPE | PORTRAIT | YES |
| AUTO | Any | NO |

---

## 5. API Reference

### BaseOrientation

```
PORTRAIT   -- authored portrait-first
LANDSCAPE  -- authored landscape-first
AUTO       -- default (no inversion)
```

### Android Compose 3.x — inverter token suffixes (`*Ph`, `*Lw`, `*Lh`)

Compose **does not** expose a `.portraitLowest()` chain on tokens. Instead, inversion is encoded as a **token suffix** on the existing axis tokens, so it composes naturally with the rest of the API. The four canonical inverters apply to every strategy package (here shown for `scaled`; the same `*Ph`/`*Lw`/`*Lh` pattern repeats on `auto`, `percent`, `power`, `logarithmic`, …):

| Token | Default axis | When orientation switches |
|-------|---------------|----------------------------|
| **`32.sdpPh`** | smallest-width (sw) | in **PORTRAIT** → uses **HEIGHT** |
| **`32.sdpLw`** | smallest-width (sw) | in **LANDSCAPE** → uses **WIDTH** |
| **`50.hdpLw`** | HEIGHT | in **LANDSCAPE** → uses **WIDTH** |
| **`50.wdpLh`** | WIDTH | in **LANDSCAPE** → uses **HEIGHT** |

The same suffix pattern is published on text tokens (`32.sspPh`, `32.sspLw`, `50.hspLw`, `50.wspLh`, …).

Full suffix catalogue (axis + `a` / `i` / `ia` AR/multi-window flags + inverters): **[`COMPOSE-API-CONVENTIONS.md`](../appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md)**.

### iOS / Flutter / RN / Web — builder-style chains

Other tracks expose the conceptual inversion via builder methods on the dimension object (consult the submodule README for the exact names):

```kotlin
.baseOrientation(BaseOrientation.PORTRAIT)
.type(ScreenType.LOWEST)
```

Shorthands illustrated in [Section 6](#6-examples-by-strategy): `.portraitLowest()`, `.portraitHighest()`, `.landscapeLowest()`, `.landscapeHighest()`.

---

## 6. Examples by Strategy

### 6.1 BALANCED (Android `auto` stack parity)

```kotlin
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.auto.asdp

@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .width(300.wdp)
            .padding(16.asdp)
    ) {
        Text("Auto adapting card")
    }
}
```

```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack {
            Text("Auto adapting card")
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
        .frame(width: AppDimens.shared.balanced(300).portraitLowest().toPoints())
    }
}
```

```dart
Container(
  width: AppDimens.fixed(300).portraitLowest().calculate(context),
  padding: EdgeInsets.all(AppDimens.fixed(16).calculate(context)),
  child: const Text('Auto adapting card'),
)
```

### 6.2 DEFAULT baseline (`scaled`) — with the modern inverter tokens

```kotlin
import com.appdimens.dynamic.compose.scaled.sdp
import com.appdimens.dynamic.compose.scaled.sdpPh
import com.appdimens.dynamic.compose.scaled.sdpLw
import com.appdimens.dynamic.compose.scaled.hdpLw
import com.appdimens.dynamic.compose.scaled.wdpLh

// Standard, no inversion — sw-driven sizing
Icon(
    imageVector = Icons.Default.Favorite,
    modifier = Modifier.size(24.sdp)
)

// Portrait-authored padding: 32.sdpPh stays sw in landscape, switches to height in portrait
Box(modifier = Modifier.padding(32.sdpPh))

// Landscape-aware width: 32.sdpLw stays sw in portrait, switches to width in landscape
Box(modifier = Modifier.width(32.sdpLw))

// Height-anchored row that becomes width-anchored on landscape (e.g. game HUD)
Box(modifier = Modifier.height(50.hdpLw))

// Width-anchored card that becomes height-anchored on landscape
Box(modifier = Modifier.width(50.wdpLh))
```

### 6.3 PERCENTAGE-oriented widths

```kotlin
Surface(modifier = Modifier.width(400.wdp))
```

---

## 7. Best Practices

**Do:** pick `portraitLowest()` when your design kit assumes portrait comps; torture-test hardware rotation **before** release.

**Do not:** mix conflicting base-orientation intents without QA; rely on AUTO if you genuinely do not care about authoring posture.

---

## 8. Platform Notes

| Platform | Notes |
|----------|-------|
| Android | Configuration changes propagate through Compose when wired per submodule guides; inverter tokens (`*Ph`, `*Lw`, `*Lh`) are the canonical surface — see [`COMPOSE-API-CONVENTIONS.md`](../appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md) |
| iOS | Trait collection churn triggers sizing refresh; orientation builders on the dimension chain |
| Flutter | `MediaQuery` rebuild semantics; builder-style `.portraitLowest()` / `.landscapeHighest()` chains |
| React Native / Web | Window resize APIs supply refreshed constraints; builder chains as on Flutter |

---

## Where to go next

- **Compose surface (suffixes, inverters, AR / multi-window flags):** [`COMPOSE-API-CONVENTIONS.md`](../appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md)
- **Concept ↔ API map across stacks:** [PLATFORMS.md](PLATFORMS.md)
- **Why these inverters exist (axis selection theory):** [THEORY.md §3](THEORY.md#3-effective-axis-selection-qualifier-inverter-multi-window)
- **Tactical patterns:** [GUIDE.md](GUIDE.md)
- **Long-form snippets:** [EXAMPLES.md](EXAMPLES.md)

---

**Jean Bodenberg** -- https://github.com/bodenberg/appdimens

[← Documentation index](README.md) · [Hub README](../README.md)
