# Naming and migration — 1.x / 2.x → `appdimens-dynamic` 3.x (Android)

How **historic AppDimens** labels map onto **`appdimens-dynamic`** Gradle **strategy packages** and **Compose extensions**. Exact formulae: [`MATHEMATICS-AND-CALCULUS.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md). Verified Kotlin constants / package roster: **[PLATFORMS.md](PLATFORMS.md)**.

---

## 1 · The core triple (what most migrations touch)

Older docs talked about several familiar buckets. On **Compose 3.x** the code you ship daily collapses into **three** strategies:

| Concept in older samples & unified APIs | Mental model | **`appdimens-dynamic` strategy** | Compose hints |
|------------------------------------------|--------------|----------------------------------|---------------|
| **BALANCED** — hybrid (linear phones, restrained tail tablets/TVs) | Default narrative for mixed form factors | **`auto`** | `asdp`, `ahdp`, `awdp`, `assp`, … (`import …compose.auto.*`) |
| **FIXED** and **DEFAULT** — SDP‑style baseline (legacy `.fxdp`, **`defaultScaling`**) | Stretch anchored at ~300 dp logic | **`scaled`** | `sdp`, `ssp`, `hdp`, `wdp`; AR-aware **`sdpa`** / **`sspa`**, … |
| **DYNAMIC** — axis-heavy sizing (legacy `.dydp`, proportional grids) | Strong tracking on logical axes | **`percent`** | `psdp`, … (`import …compose.percent.*`) |

**Important**

- **BALANCED** is **`auto`**, **not** `scaled`. Prefer **`16.asdp` / `16.assp`** when you want the hybrid curve, not bare **`sdp` / `ssp`**.
- **`FIXED`** and **DEFAULT** (2.x rename of the Fixed storyline) map to the **same **`scaled`** package**—pick axis tokens instead of imagining a second Gradle module.

The 2.x doc set renamed *Fixed → **DEFAULT*** and *Dynamic → **PERCENTAGE*** purely for readability. Narrative hubs still mention those buckets; Gradle uses **`scaled`** and **`percent`**.

---

## 2 · Version timeline (abbreviated)

| Era | Android surface | What tutorials emphasized |
|-----|-----------------|----------------------------|
| **1.x** | Unified DSL: fixed DP vs dynamic DP (`.fxdp`, `.dydp` in archived posts) | “Fixed vs dynamic” split |
| **2.x** | Same engines; prose standardized DEFAULT / PERCENTAGE / BALANCED | Shared vocabulary across stacks |
| **3.x** | Explicit packages (`scaled`, `percent`, `auto`, …) + imports | Compose tokens `sdp`, `psdp`, `asdp`, … |

Breaking notes live inside **`appdimens-dynamic`**—not duplicated here.

---

## 3 · Cheatsheet: “I used to call things like…”

Assume the modern **`AppDimensProvider`** / Compose setup described in that submodule README.

| Legacy-ish intent | Land on 3.x |
|-------------------|-----------|
| “Balanced / hybrid curve” | **`auto`**: `16.asdp`, `16.assp`, optionally `ahdp` / `awdp` |
| “Default / fixed SDP stretch” | **`scaled`**: `16.sdp`, `100.wdp`, `48.hdp`, `16.ssp`; add `sdpa` / `sspa` when you need documented AR tails |
| “Dynamic proportional axis” | **`percent`**: `psdp`, other `compose.percent` helpers |
| Old unified `.balanced().dp` snippets | Fork by intent (`asdp` vs `sdp`); avoid pasting monolithic DSL chains verbatim |

Hooks like **`smart().forElement(…)`** remain **literature for platforms that expose Smart builders**. Kotlin **`appdimens-dynamic`** still prefers **explicit** extensions per callsite unless you dispatch yourself.

---

## 4 · Before / after — Compose diffs (the core triple)

The three migrations below are the ones that cover **the vast majority** of real-world Android Compose porting. Each block is a faithful before/after, with package imports and the matching kernel doc.

### 4.1 BALANCED — unified `.balanced().dp` → `auto` package

```diff
-import com.appdimens.dynamic.compose.*
-
-Box(
-    Modifier
-        .padding(16.balanced().dp)
-        .height(48.balanced().dp)
-) {
-    Text("Hybrid", fontSize = 16.balanced().sp)
-}
+import com.appdimens.dynamic.compose.auto.asdp
+import com.appdimens.dynamic.compose.auto.assp
+import com.appdimens.dynamic.compose.scaled.hdp // axis token stays under `scaled`
+
+Box(
+    Modifier
+        .padding(16.asdp)
+        .height(48.asdp)
+) {
+    Text("Hybrid", fontSize = 16.assp)
+}
```

Kernel doc: **[`auto.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/auto.md)**.

### 4.2 DEFAULT / FIXED — `.fxdp` / `.defaultDp` → `scaled` package

```diff
-import com.appdimens.dynamic.compose.*
-
-Icon(
-    imageVector = Icons.Default.Favorite,
-    modifier = Modifier.size(24.fxdp) // legacy 1.x — phone-first SDP stretch
-)
+import com.appdimens.dynamic.compose.scaled.sdp
+import com.appdimens.dynamic.compose.scaled.sdpa // when you want the documented AR tail
+
+Icon(
+    imageVector = Icons.Default.Favorite,
+    modifier = Modifier.size(24.sdp) // or 24.sdpa when AR-awareness matters
+)
```

Kernel doc: **[`scaled.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/scaled.md)**.

### 4.3 PERCENTAGE / DYNAMIC — `.dydp` → `percent` package

```diff
-import com.appdimens.dynamic.compose.*
-
-Surface(
-    modifier = Modifier
-        .width(300.dydp)  // legacy 1.x — axis-heavy proportional stretch
-)
+import com.appdimens.dynamic.compose.percent.psdp
+import com.appdimens.dynamic.compose.scaled.wdp // when you want the explicit width-axis token
+
+Surface(
+    modifier = Modifier
+        .width(300.psdp)  // or 300.wdp if you really want the `scaled` width axis
+)
```

Kernel doc: **[`percent.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/percent.md)**.

---

## 5 · Migrating from third-party SDP / SSP libraries

If your app currently depends on a third-party Intuit-style SDP/SSP package (`com.intuit.sdp:sdp-android:*` or similar), the path is short and **non-destructive**: the resource names (`@dimen/_16sdp`, `@dimen/_18ssp`, …) match.

**Step 1 — swap the dependency.** Replace the third-party SDP/SSP with [`io.github.bodenberg:appdimens-sdps`](https://github.com/bodenberg/appdimens-sdps/blob/main/README.md) and [`io.github.bodenberg:appdimens-ssps`](https://github.com/bodenberg/appdimens-ssps/blob/main/README.md). XML keeps working:

```xml
<!-- Unchanged -->
<TextView
    android:layout_width="@dimen/_300sdp"
    android:padding="@dimen/_16sdp"
    android:textSize="@dimen/_18ssp" />
```

**Step 2 — adopt runtime tokens gradually inside Compose.** Where you currently read an `@dimen` value, write the Kotlin extension instead:

```diff
-// Compose reading XML resources today
-Box(modifier = Modifier.padding(dimensionResource(R.dimen._16sdp)))
+
+import com.appdimens.dynamic.compose.scaled.sdp
+Box(modifier = Modifier.padding(16.sdp))
```

**Step 3 — switch hot UI surfaces to BALANCED (`auto`) where it helps.** Buttons, cards, spacing on multi-form-factor screens benefit most from the hybrid curve:

```diff
-import com.appdimens.dynamic.compose.scaled.sdp
-Box(modifier = Modifier.padding(16.sdp))
+import com.appdimens.dynamic.compose.auto.asdp
+Box(modifier = Modifier.padding(16.asdp))
```

You do **not** have to migrate everything at once; SDP/SSP XML resources and `appdimens-dynamic` runtime tokens are designed to coexist in the same app. The two tracks share the same 300 dp design reference, so values stay consistent.

---

## 6 · Related hub pages

| Page | Purpose |
|------|---------|
| [PLATFORMS.md](PLATFORMS.md) | Full strategy roster, constants vs `DesignScaleConstants.kt`, kernels |
| [THEORY.md](THEORY.md) | Derivations, axioms, kernel formulas |
| [GUIDE.md](GUIDE.md) | Tactical patterns, decision tree |
| [EXAMPLES.md](EXAMPLES.md) | Long-form snippets per stack |
| [`appdimens-dynamic/DOCUMENTATION/README.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/README.md) | Submodule kernel catalogue |
| [`appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md`](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md) | Full Compose token / suffix index |

 [← Documentation index](README.md) · [Hub README](../README.md)
