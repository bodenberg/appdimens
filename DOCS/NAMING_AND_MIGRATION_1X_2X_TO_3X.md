# Naming & migration — 1.x / 2.x catalogs → **`appdimens-dynamic` 3.x** (Android)

> **Hub documentation.** English only. Describes how **historic AppDimens names** relate to **`appdimens-dynamic` 3.x** strategy **packages** and **Compose extensions**. For exact formulae use **[`MATHEMATICS-AND-CALCULUS.md`](../appdimens-dynamic/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)** and **[`IMPLEMENTATION_ALIGNMENT_APPDIMENS_DYNAMIC.md`](IMPLEMENTATION_ALIGNMENT_APPDIMENS_DYNAMIC.md)**.

---

## 1 · The core triple (what most migrations touch)

Older docs and unified APIs talked about **four** familiar buckets. On **Compose 3.x** the implementations you reach for daily collapse into **three Gradle strategies**:

| Concept in older hub samples & unified APIs | Typical mental model | **`appdimens-dynamic` 3.x** strategy | Compose entry hints |
|--------------------------------------------|----------------------|--------------------------------------|---------------------|
| **BALANCED** — hybrid (“linear on phones, damped tail on tablets/TVs”) | “Primary recommendation” for multi‑form-factor UI | **`auto`** | `asdp`, `ahdp`, `awdp`, `assp`, … (`import …compose.auto.*`) |
| **FIXED** *and* **DEFAULT** — phone‑first SDP / design‑width baseline (legacy `.fxdp`, “fixed‑style DP”, **`defaultScaling`**) | One clear proportional curve anchored at ~300 dp width thinking | **`scaled`** | `sdp`, `ssp`, `hdp`, `wdp`; AR-aware **`sdpa`** / **`sspa`**, … (`import …compose.scaled.*`) |
| **DYNAMIC** — strong axis tracking (“dynamic DP”, proportional containers, legacy `.dydp`) | Sizes that track a **fraction** of logical width/height | **`percent`** | `psdp`, … (`import …compose.percent.*`) |

**Important**

- **`BALANCED`** is **`auto`** — not **`scaled`**. Prefer **`asdp` / assp`** when you intentionally want that hybrid curve, not bare **`sdp` / `ssp`**.
- **`FIXED`** and **DEFAULT** (2.x wording for the old “Fixed” line) both map operationally to the **`scaled`** package in Kotlin—there is **no** separate Gradle module named “fixed”; pick axis tokens (`sdp`, `wdp`, `hdp`, …) instead of renaming the strategy twice in code.

The **2.x documentation catalog** rebranded wording only: *Fixed → **DEFAULT***, *Dynamic → **PERCENTAGE***. Those **marketing** names remain in long-form **`DOCS/MATHEMATICAL_THEORY*.md`** for cross-platform readability. **Android Compose 3.x** uses package names **`scaled`** and **`percent`**.

---

## 2 · Version timeline (abbreviated)

| Era | Android surface (simplified) | Dominant naming in tutorials |
|-----|------------------------------|------------------------------|
| **1.x** | Unified chains / DSL around **fixed DP** vs **dynamic DP** (examples: `.fxdp`, `.dydp` in archived posts) | “Fixed vs Dynamic” split |
| **2.x** | Same family; catalog prose standardized on **DEFAULT** (formerly Fixed narrative) vs **PERCENTAGE** (formerly Dynamic narrative) vs **BALANCED** emphasis | ENUM-like buckets in hub theory |
| **3.x (`appdimens-dynamic`)** | **Per-strategy packages** (`scaled`, `percent`, `auto`, …) + explicit imports | Compose tokens: `sdp`/`psdp`/`asdp` … |

Breaking changes live in **`appdimens-dynamic`** changelog and README—not in this hub’s semver prose.

---

## 3 · Cheatsheet: “I have this legacy call…”

Assume modern **`AppDimensProvider`** / Compose runtime setup (see submodule README).

| Legacy-ish intent | Aim in 3.x |
|-------------------|-----------|
| “Use **balanced / hybrid** scaling” | **`auto`**: **`16.asdp`**, **`16.assp`**, axis-specific **`ahdp`** / **`awdp`** |
| “Use **default / fixed** SDP‑style scaling (single curve)” | **`scaled`**: **`16.sdp`**, **`100.wdp`**, **`48.hdp`**, **`16.ssp`**; AR suffix **`sdpa`** / **`sspa`** when needed |
| “Use **dynamic / percentage‑of‑axis** sizing” | **`percent`**: **`psdp`** and related **`compose.percent`** APIs |
| “Old unified `.balanced().dp` blog sample” | Split by intent: **`asdp`** chain for hybrid, **`sdp`** chain for SDP baseline—do not paste legacy unified chains verbatim |

Smart / element inference examples in hub theory (**`smart().forElement(…)`**) remain **conceptual for non-Android stacks**; Kotlin still picks **explicit** extensions per callsite unless you wrap your own dispatcher.

---

## 4 · Related hub pages

| Page | Role |
|------|------|
| [`IMPLEMENTATION_ALIGNMENT_APPDIMENS_DYNAMIC.md`](IMPLEMENTATION_ALIGNMENT_APPDIMENS_DYNAMIC.md) | Verified constants (`auto` breakpoint 480 dp, sensitivities), full strategy↔package table |
| [`PLATFORM_API_MAP.md`](PLATFORM_API_MAP.md) | Concepts → bindings on iOS, Web, Flutter, RN |
| [`MATHEMATICAL_THEORY_SIMPLIFIED.md`](MATHEMATICAL_THEORY_SIMPLIFIED.md) | High-level formulas with Android footnotes |
| [`appdimens-dynamic/DOCUMENTATION/README.md`](../appdimens-dynamic/DOCUMENTATION/README.md) | Submodule index |

---

 [← Theory index](README.md)
