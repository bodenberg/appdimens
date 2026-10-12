# Rotation, split screen and foldables

**What is this page for?** It explains what happens to AppDimens sizes when the device **rotates**, **splits the screen** or **folds**, and the few extra tools you have when you want different behaviour. Most apps never need anything on this page — the defaults already work.

---

## 1. The short version

- Values **update automatically** when the screen changes. You don't write any code for that.
- `sdp` follows the **smallest side** of the screen. When you rotate a phone, the smallest side stays the same → **`sdp` sizes don't jump** on rotation. 👍
- `wdp` follows the **width** and `hdp` the **height**, so those *do* change on rotation (that's usually what you want for widths and heights).
- In **split screen**, the window is smaller, so sizes become smaller. Add **`i`** (`16.sdpi`) to keep the full-screen size.

---

## 2. When would I want more control?

Imagine a design made for a phone held **upright** (portrait). Some sizes were meant to follow the **long side** of the screen. When the user rotates the phone, the long side becomes the width — and you want the size to follow it.

That's what **inverters** do: they change which side of the screen is measured, depending on the orientation.

---

## 3. Inverters — the four endings

| Token | Normally follows | In this orientation… | …it follows |
|-------|------------------|----------------------|-------------|
| `32.sdpPh` | smallest side | **P**ortrait | **h**eight |
| `32.sdpLw` | smallest side | **L**andscape | **w**idth |
| `32.sdpPw` / `32.sdpLh` | smallest side | Portrait / Landscape | width / height |
| `50.hdpLw` | height | **L**andscape | **w**idth |
| `50.wdpLh` | width | **L**andscape | **h**eight |

**How to read them:** first letter = orientation (**P**ortrait / **L**andscape), second letter = side to use (**h**eight / **w**idth).

The same endings exist for text (`32.sspPh`, `50.hspLw`, …) and for every strategy (`32.asdpPh`, `32.fsdpLw`, …).

### The same token on every platform

| Compose · KMP · iOS · Flutter | React Native | Web | .NET MAUI |
|-------------------------------|--------------|-----|-----------|
| `32.sdpPh` | `sdpPh(32)` | `32sdpPh` (e.g. `class="p:32sdpPh"`) | `{sdpPh:32}` |

---

## 4. A different value per orientation

Sometimes you simply want **another number** in landscape. Use the `Rotate` helper:

```kotlin
// Android Compose — 80 normally, 50 in landscape
80.sdpRotate(50, orientation = Orientation.LANDSCAPE)
```

```swift
// iOS
80.sdpRotate(50)
```

```tsx
// React Native
sdpRotate(80, 50, Orientation.LANDSCAPE)
```

The same family of helpers exists for TV/car/watch modes (`sdpMode`), screen-size breakpoints (`sdpQualifier`) and combinations (`sdpScreen`). Exact signatures are in each library's README.

---

## 5. Split screen and multi-window

| You want… | Use | Example |
|-----------|-----|---------|
| sizes to follow the real window (default) | normal tokens | `16.sdp` |
| sizes to stay as if the app were full-screen | the `i` ending | `16.sdpi` |
| both aspect-ratio correction and full-screen size | `ia` | `16.sdpia` |

Games often use `i` for the HUD so it doesn't shrink when the window is resized.

---

## 6. Foldables

- When a foldable **opens**, the screen gets bigger, so sizes grow — like moving from a phone to a small tablet. If that feels too large, use the **auto** strategy (`asdp`), which grows more calmly on big screens.
- Each library can detect the fold posture (open, half-open, closed) for the `Mode` helpers. On the Web you can even write classes like `p@fold-open:24sdp`.

---

## 7. Examples

**Android Compose**

```kotlin
import com.appdimens.dynamic.compose.*

Icon(Icons.Default.Favorite, null, Modifier.size(24.sdp))   // stable on rotation
Box(Modifier.padding(32.sdpPh))      // portrait → follows height
Box(Modifier.width(32.sdpLw))        // landscape → follows width
Box(Modifier.height(50.hdpLw))       // HUD bar: height in portrait, width in landscape
```

**Flutter**

```dart
Padding(padding: EdgeInsets.all(32.sdpPh), child: child);
```

**iOS (SwiftUI)**

```swift
Text("Card").padding(32.sdpPh).frame(height: 50.hdpLw)
```

**Web**

```html
<div class="p:32sdpPh h:50hdpLw">Card</div>
<h1 class="fs:20ssp fs@landscape:24ssp">Title</h1>
```

---

## 8. Good habits

- Start with plain `sdp`. Reach for inverters only when a real device shows a problem.
- Test by **rotating** and by **resizing** (split screen, desktop window, browser window).
- Don't mix many inverters on one screen — pick one rule per element type.

---

## Where to go next

- Full list of endings (all strategies): [`COMPOSE-API-CONVENTIONS.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md)
- How each platform writes them: [PLATFORMS.md](PLATFORMS.md)
- Why the axis is chosen this way (math): [THEORY.md §3](THEORY.md#3-effective-axis-selection-qualifier-inverter-multi-window)
- Beginner guide: [GUIDE.md](GUIDE.md) · Examples: [EXAMPLES.md](EXAMPLES.md)

[← Documentation index](README.md) · [Back to the hub](../README.md)
