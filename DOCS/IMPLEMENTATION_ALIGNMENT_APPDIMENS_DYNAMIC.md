# Implementation alignment — `appdimens-dynamic` (Jetpack Compose / Kotlin)

> **Hub documentation.** This page keeps **conceptual Docs** aligned with **`appdimens-dynamic` as shipped**. It is not a substitute for submodule sources—confirm edge cases there.

Authoritative subtree: **[`appdimens-dynamic/DOCUMENTATION/`](../appdimens-dynamic/DOCUMENTATION/README.md)** — especially **`MATHEMATICS-AND-CALCULUS.md`**, **`auto.md`**, **`scaled.md`**, **`percent.md`**, **`PERFORMANCE.md`**.

Hardware / layout plumbing symbols ($$ sw, \rho, L_{AR} $$ etc.) match **`MATHEMATICS-AND-CALCULUS.md`** (§1–4).

---

## 1 · Concept names vs Android package naming

Older cross-stack narrative used **ENUM-like labels** (**BALANCED**, **DEFAULT**, **PERCENTAGE**, …). In **`appdimens-dynamic`** every runtime strategy lives under its **own Gradle source package**:

| Typical hub / cross‑platform wording | **`appdimens-dynamic` strategy** | Compose import sketch | Canonical narrative doc |
|-------------------------------------|----------------------------------|------------------------|-------------------------|
| **BALANCED** (hybrid phone-linear → restrained tablet growth) | **`auto`** | `com.appdimens.dynamic.compose.auto.*` (`asdp`, `ahdp`, `awdp`, `assp` …) | [`auto.md`](../appdimens-dynamic/DOCUMENTATION/auto.md) |
| **DEFAULT** “fixed-style” baseline / SDP-style linear on 300 dp axes | **`scaled`** (default recommendation) | `com.appdimens.dynamic.compose.scaled.*` (`sdp`, `hdp`, `wdp`, `ssp` …) | [`scaled.md`](../appdimens-dynamic/DOCUMENTATION/scaled.md) |
| **PERCENTAGE** / aggressive axis tracking | **`percent`** | `com.appdimens.dynamic.compose.percent.*` (`psdp` …) | [`percent.md`](../appdimens-dynamic/DOCUMENTATION/percent.md) |
| Stevens-style sublinear knob | **`power`** | `com.appdimens.dynamic.compose.power.*` | [`power.md`](../appdimens-dynamic/DOCUMENTATION/power.md) |
| Bounded band / breakpoint feel | **`fluid`** | `com.appdimens.dynamic.compose.fluid.*` | [`fluid.md`](../appdimens-dynamic/DOCUMENTATION/fluid.md) |
| Weber–Fechner style damping | **`logarithmic`** | `com.appdimens.dynamic.compose.logarithmic.*` (`logsdp` … demo) | [`logarithmic.md`](../appdimens-dynamic/DOCUMENTATION/logarithmic.md) |
| Midpoint blend vs pure linear | **`interpolated`** | `compose.interpolated.*` (`isdp` …) | [`interpolated.md`](../appdimens-dynamic/DOCUMENTATION/interpolated.md) |
| Hypotenuse / physical feel | **`diagonal`** | `compose.diagonal.*` (`dgsdp` …) | [`diagonal.md`](../appdimens-dynamic/DOCUMENTATION/diagonal.md) |
| Width + height average growth | **`perimeter`** | `compose.perimeter.*` (`prsdp` …) | [`perimeter.md`](../appdimens-dynamic/DOCUMENTATION/perimeter.md) |
| Letterbox (FIT semantics) | **`fit`** | `compose.fit.*` (`ftsdp` …) | [`fit.md`](../appdimens-dynamic/DOCUMENTATION/fit.md) |
| Cover (FILL semantics) | **`fill`** | `compose.fill.*` (`flsdp` …) | [`fill.md`](../appdimens-dynamic/DOCUMENTATION/fill.md) |
| Density override curve | **`density`** | `compose.density.*` (`dsdp` …) | [`density.md`](../appdimens-dynamic/DOCUMENTATION/density.md) |
| Container / step-based resize | **`resize`** | `compose.resize.*`, `ResizeBound` | [`resize.md`](../appdimens-dynamic/DOCUMENTATION/resize.md) |
| Constant physical size (**NONE**) | *No strategy*: raw `Dp`/`Sp` or guarded returns | — | — |

**Prefix catalogue & mirrored `code.*` parity** → [`COMPOSE-API-CONVENTIONS.md`](../appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md).

---

## 2 · Verified constants (`DesignScaleConstants` & docs)

Matched against **[`DesignScaleConstants.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DesignScaleConstants.kt)** and **`MATHEMATICS-AND-CALCULUS.md`**:

| Constant | Approx. value | Role |
|-----------|---------------|------|
| `BASE_WIDTH_DP` | **300 f** | Global ℓ∞ design width reference (`INV_BASE_RATIO = 1/300`) |
| `BASE_HEIGHT_DP` | **533 f** pairs with width for perimeter / hypotenuse baselines |
| `REFERENCE_ASPECT_RATIO` | **1.78 f** | Normalises $$\rho$$ into $$r_{AR}$$, feeding log compensations |
| Base diagonal | $$\sqrt{300^2 + 533^2} \approx 611.63$$ dp |
| Base perimeter | **833 dp** |
| **`auto` transition** | **480 dp** on measured axis (`DimenAutoDp.kt`) |
| **`auto` sensitivity** | **0.4 f** (`ln` term multiplier) |

`ScreenFactors.updateFactors()` precomputes shared multipliers consumed by **`scaled`**, **`power`**, **`logarithmic`**, **`interpolated`**, **`perimeter`**, **`diagonal`**, **`fill`/`fit`** paths—exact tensor layout is documented with formulas in **`MATHEMATICS-AND-CALCULUS.md` §§3–4** and echoed in **`library/PERFORMANCE.md`**.

---

## 3 · `auto` (**BALANCED**) kernel verified in Kotlin

Implementation: `calculateAutoDpCompose` (**`DimenAutoDp.kt`**) — mirrored in `code.auto`.

Given effective axis **`dim`** in dp, **`inv = 1/300`**, **`transition = 480`**, **`sensitivity = 0.4`**:

1. **`dim ≤ 480`:** \(\text{scale} = dim \times inv\) (pure linear scaled segment)  
2. **`dim > 480`:** \(\text{scale} = transition \cdot inv + sensitivity \cdot \ln\bigl(1 + (dim - transition)\cdot inv\bigr)\)

Returned dp (before suffix `a` / custom `k`): **`baseValue * scale * (optional aspect ratio multiplier)`**.

This matches **`DOCUMENTATION/auto.md`** line-for-line.

---

## 4 · `scaled` kernel (conceptual **DEFAULT** linear segment)

Aspect ratio OFF: exact linear translation  
$$Output \approx base \cdot (sw\cdot \frac{1}{300})$$

Aspect ratio ON uses precomputed **`f.arMultiplier`** (see **`MATHEMATICS-AND-CALCULUS.md` §4.1**) — **not** identical to naive “fixed logarithmic naming” older hub prose; always reference **`scaled.md`** for suffix patterns (`sdp`, `sdpa`, inverters …).

---

## 5 · `percent` (**PERCENTAGE**)

Formal reference: **`percent.md`** + §4 table in **`MATHEMATICS-AND-CALCULUS.md`**: output uses axis dp with same global inverse ratio.

---

## 6 · “Smart Inference” vs Android

Kotlin sources **do not** expose builders like **`smart(..).forElement(..)`**. Automatic strategy weighing remains documented for other stacks ; Android guidance is explicit **strategy-per-package**. See **`README.md`** strategy decision order (**scaled → percent → auto**).

---

## 7 · Automated regression

Formula consistency is pinned by **`library/src/test/java/.../StrategyModuleFormulasTest.kt`** (see **`MATHEMATICS-AND-CALCULUS.md` §6**). Run tests inside **`appdimens-dynamic`** submodule, not here.

---

[← Theory index](README.md) · [`appdimens-dynamic` README](../appdimens-dynamic/README.md)
