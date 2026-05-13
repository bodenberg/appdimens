# APPDIMENS — mathematical theory

A formal, library-faithful account of the scaling kernels published by
**`appdimens-dynamic`** (Jetpack Compose 3.x and parallel Kotlin View bindings).
Day‑to‑day decisions live in **[GUIDE.md](GUIDE.md)** and runnable recipes in
**[EXAMPLES.md](EXAMPLES.md)**. When prose here disagrees with the submodule
documents, the submodule is the source of truth:
**[`MATHEMATICS-AND-CALCULUS.md`](../appdimens-dynamic/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)**,
**[`COMPOSE-API-CONVENTIONS.md`](../appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md)**,
and the per‑strategy files under
[`appdimens-dynamic/DOCUMENTATION/`](../appdimens-dynamic/DOCUMENTATION/).

**Naming legend.** The hub uses the conceptual labels **BALANCED**, **DEFAULT**,
**PERCENTAGE**, **DYNAMIC** because they are stack‑neutral. The Compose 3.x
artifact publishes **Gradle/package names** (`auto`, `scaled`, `percent`, …).
The full mapping lives in [Appendix A](#appendix-a-hub-labels--gradle-strategies)
and in [MIGRATION.md](MIGRATION.md).

*Author: Jean Bodenberg — Apache 2.0.*

> **How to read this file**
>
> - **§1–§4** set up the problem, the notation, the effective-axis selection (qualifier, inverter, multi-window), and the AR multiplier folded in by the optional `a` suffix.
> - **§5 — Strategy catalogue** is the kernel-by-kernel reference, aligned with the 14 modes shipped by `appdimens-dynamic` (see the [submodule summary table](../appdimens-dynamic/DOCUMENTATION/README.md#summary)).
> - **§6–§7** cover the constraint-based `resize` subsystem and the physical-unit helpers (mm / cm / inch).
> - **§8–§10** are quantitative and comparative — numerical sweeps, engineering invariants, and honest categorical trade-offs vs other libraries.
> - **§11 + appendices** hold references, the Gradle ↔ hub-label table, and source pointers.
>
> Companion docs: **[GUIDE.md](GUIDE.md)** (tactical decisions), **[PLATFORMS.md](PLATFORMS.md)** (concept ↔ API map across stacks), **[MIGRATION.md](MIGRATION.md)** (legacy ↔ modern tokens), **[ORIENTATION.md](ORIENTATION.md)** (rotation + inverters), **[EXAMPLES.md](EXAMPLES.md)** (long-form snippets).
>
> **GitHub math:** Inline and display math use a [KaTeX-compatible subset](https://docs.github.com/en/get-started/writing-on-github/working-with-advanced-formatting/writing-mathematical-expressions). This file avoids macros that GitHub rejects (for example `\operatorname`), and uses plain symbols such as $(0,\infty)$ and $C$ instead of `\mathbb{R}_{>0}` / `\mathcal{C}` where those caused rendering issues.

---

## Table of contents

1. [Problem statement and design axioms](#1-problem-statement-and-design-axioms)
2. [Notation, units, and reference frame](#2-notation-units-and-reference-frame)
3. [Effective‑axis selection: qualifier, inverter, multi‑window](#3-effective-axis-selection-qualifier-inverter-multi-window)
4. [The aspect‑ratio mould (the optional `a` suffix)](#4-the-aspect-ratio-mould-the-optional-a-suffix)
5. [Strategy catalogue](#5-strategy-catalogue)
   - 5.1 [`scaled` — linear proportional kernel (DEFAULT)](#51-scaled--linear-proportional-kernel-default)
   - 5.2 [`auto` — piecewise linear/logarithmic kernel (BALANCED)](#52-auto--piecewise-linearlogarithmic-kernel-balanced)
   - 5.3 [`percent` — linear kernel + literal `space*` percentages (PERCENTAGE)](#53-percent--linear-kernel--literal-space-percentages-percentage)
   - 5.4 [`power` — Stevens‑style sublinear kernel](#54-power--stevens-style-sublinear-kernel)
   - 5.5 [`logarithmic` — Weber–Fechner damped kernel](#55-logarithmic--weberfechner-damped-kernel)
   - 5.6 [`fluid` — clamped affine band](#56-fluid--clamped-affine-band)
   - 5.7 [`interpolated` — fixed/linear midpoint](#57-interpolated--fixedlinear-midpoint)
   - 5.8 [`diagonal` — Euclidean kernel](#58-diagonal--euclidean-kernel)
   - 5.9 [`perimeter` — $L^{1}$ kernel](#59-perimeter--l1-kernel)
   - 5.10 [`fill` and `fit` — extremum ratios](#510-fill-and-fit--extremum-ratios)
   - 5.11 [`density` — dpi‑bucket kernel](#511-density--dpi-bucket-kernel)
6. [Constraint‑based geometry: the `resize` subsystem](#6-constraint-based-geometry-the-resize-subsystem)
7. [Physical units (mm, cm, in)](#7-physical-units-mm-cm-in)
8. [Numerical comparison across a sweep of $sw$](#8-numerical-comparison-across-a-sweep-of-sw)
9. [Engineering invariants (no curves here)](#9-engineering-invariants-no-curves-here)
10. [Comparative discussion and limits](#10-comparative-discussion-and-limits)
11. [References](#11-references)
12. [Appendix A — hub labels ↔ Gradle strategies](#appendix-a-hub-labels--gradle-strategies)
13. [Appendix B — source pointers](#appendix-b-source-pointers)

---

## 1. Problem statement and design axioms

### 1.1 The failure of density‑only sizing

The platform unit **`dp`** (density‑independent pixel) factors out **physical
density** but not **canvas extent**. A token of $48\,\text{dp}$ occupies roughly
$15\%$ of a $320\,\text{dp}$ phone but only ${\approx}\,4.4\%$ of a $1080\,\text{dp}$
TV: relative proportion is not preserved. Aspect ratio is similarly invisible
to plain `dp`: a $4:3$ tablet and a $21:9$ phone receive identical token sizes
even though their compositional canvases differ qualitatively.

The library therefore models token sizing as a **family of one‑dimensional
maps**

$$f_S\colon (0,\infty) \times C \longrightarrow (0,\infty),
\qquad (b, c) \mapsto f_S(b, c),$$

where $b$ is the **base token** (a design‑time `dp`/`sp`), $c \in C$
is a snapshot of `android.content.res.Configuration` (after orientation
plumbing), and $S$ selects a **kernel** — `scaled`, `auto`, `percent`, `power`,
`logarithmic`, `fluid`, `interpolated`, `diagonal`, `perimeter`, `fill`, `fit`,
`density`. The output stays in `dp` (or in `sp` via the parallel `*Sp*` paths).

### 1.2 Design axioms

The kernels in `appdimens-dynamic` are not generic. They obey four invariants
that surface in every public surface:

1. **Reference fixed point.** Every kernel satisfies $f_S(b,c_{0}) = b$ when the
   snapshot $c_0$ matches the reference frame
   $(W_0, H_0, \rho_0) = (300\,\text{dp},\,533\,\text{dp},\,1.78)$
   [`DesignScaleConstants.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DesignScaleConstants.kt).
2. **Monotone growth (in the relevant axis).** For the strategies that scale
   with device extent, $f_S$ is non‑decreasing in the chosen axis $d$. Two
   strategies break this on purpose — `fluid` saturates outside a band,
   `density` ignores extent altogether.
3. **Aspect ratio is opt‑in.** A kernel never folds aspect ratio into its
   output unless the caller chose an aspect‑aware façade (`*a`, `*ia` …).
   See [§4](#4-the-aspect-ratio-mould-the-optional-a-suffix).
4. **Strategies are modular.** Compose package
   `com.appdimens.dynamic.compose.<strategy>` does not import from another
   strategy package; all coupling goes through `core`
   [`DimenCache`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DimenCache.kt)
   and
   [`DimenCalculationPlumbing`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DimenCalculationPlumbing.kt).

### 1.3 Two pillars

A central abstraction is the **kernel vs. mould** decomposition. Each output is

$$f_S(b, c) \;=\; K_S(b, d) \cdot M_\rho(c),$$

where $K_S(b,d)$ is the **kernel** factor and $M_\rho(c)$ is the **optional aspect mould** (equals $1$ when aspect handling is off). The effective axis $d$ is `smallest‑width`, `width`, or `height` after inverters; $M_\rho$ collapses to $1$ unless the call site asked for the
mould. This separation is what lets phone‑first designs ignore aspect ratio
entirely and what lets aspect‑sensitive surfaces enable it precisely where it
matters, without rewriting kernels.

---

## 2. Notation, units, and reference frame

The constants below are **canonical** in
[`DesignScaleConstants.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DesignScaleConstants.kt)
and
[`DimenCache.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DimenCache.kt).

| Symbol | Definition | Value | Source |
|:---:|:---|:---|:---|
| $b$ | Design token (the receiver of `.sdp`, `.psdp`, `.asdp`, …) | user‑supplied $\text{dp}$ or $\text{sp}$ | API |
| $w, h$ | `screenWidthDp`, `screenHeightDp` of the active window | runtime | `Configuration` |
| $sw$ | `smallestScreenWidthDp` (the orientation‑invariant short side) | runtime | `Configuration` |
| $s_{\min}, s_{\max}$ | $\min(w,h)$, $\max(w,h)$ | runtime | derived |
| $W_0$ | Reference shorter side | $300\,\text{dp}$ | `BASE_WIDTH_DP` |
| $H_0$ | Reference longer side | $533\,\text{dp}$ | `BASE_HEIGHT_DP` |
| $D_0$ | Reference diagonal $\sqrt{W_0^2 + H_0^2}$ | $611.6305\,\text{dp}$ | `BASE_DIAGONAL_DP` |
| $P_0$ | Reference perimeter‑sum $W_0 + H_0$ | $833\,\text{dp}$ | `BASE_PERIMETER_DP` |
| $\rho_0$ | Reference aspect ratio | $1.78$ | `REFERENCE_ASPECT_RATIO` |
| $\rho$ | Live aspect ratio $s_{\max}/s_{\min}$ | runtime | derived |
| $\hat\rho$ | Normalised aspect $\rho/\rho_0$ | runtime | `factors.normalizedAr` |
| $L_\rho$ | $\ln \hat\rho$ | runtime | `factors.logNormalizedAr` |
| $\iota$ | $1/W_0 = 1/300$ | $0.0033\overline{3}$ | `INV_BASE_RATIO` |
| $\varepsilon$ | Default adjustment scale $0.10/30$ | $0.0033\overline{3}$ | `ADJUSTMENT_SCALE` |
| $\kappa$ | Default sensitivity $0.08/30$ | $0.002\overline{6}$ | `SENSITIVITY_DEFAULT` |

The reference triple $(W_0, H_0, \rho_0)$ corresponds, by construction, to a
$300 \times 533\,\text{dp}$ canvas at $\rho_0 = 1.78$ — a stereotypical "small
phone in portrait" baseline. Choosing these constants over, say, an iOS or web
viewport keeps the library compatible with Android's `smallest‑width`
resource‑qualifier conventions [\[1, §Configuration\]](#11-references).

The library publishes outputs in `Float` (IEEE 754 32‑bit) throughout. Test
suites validate curves to a deterministic delta of $< 0.05\,\text{dp}$, which
is one order of magnitude tighter than the visual resolution of a $1\,\text{dp}$
unit; cf. [`StrategyModuleFormulasTest`](../appdimens-dynamic/library/src/test/java/com/appdimens/dynamic/modules/StrategyModuleFormulasTest.kt).

---

## 3. Effective‑axis selection: qualifier, inverter, multi‑window

Before any kernel is evaluated, three short layers of plumbing decide *which
side of the window* the formula reads. They are implemented in
[`DimenCalculationPlumbing.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DimenCalculationPlumbing.kt).

### 3.1 Qualifier

A `DpQualifier` chooses the axis:

| Qualifier | Axis $d$ |
|:---|:---|
| `SMALL_WIDTH` | $sw$ |
| `WIDTH` | $w$ |
| `HEIGHT` | $h$ |

The default qualifier is fixed by the API surface — `sdp`/`ssp` read $sw$,
`hdp`/`hsp` read $h$, `wdp`/`wsp` read $w$. The qualifier is invariant of
orientation because Android already gives $sw$ as the **orientation‑invariant**
shorter side [\[1\]](#11-references).

### 3.2 Inverter

Inverters swap the axis when orientation flips — e.g. a `sdp` surface that
becomes "the height of a portrait phone" once rotated landscape. The valid
swaps are listed in `Inverter`:

$$\text{PH↔LW},\quad \text{PW↔LH},\quad \text{LH↔PW},\quad \text{LW↔PH},$$
$$\text{SW↔LH},\quad \text{SW↔LW},\quad \text{SW↔PH},\quad \text{SW↔PW}.$$

API surfaces such as `sdpLw`, `sdpPh`, `hdpLw`, … bake an inverter into the
property name; the resolver lives in
`DimenCalculationPlumbing.effectiveQualifier`.

### 3.3 Multi‑window bypass

In split‑screen and freeform windows the **canvas the formula expects** stops
matching the **canvas the system reports**. The plumbing first asks
`Activity.isInMultiWindowMode` (API 24+, matches `minSdk`); when no `Activity`
is reachable, a conservative heuristic compares $sw$ to $w$ and treats
$w \le 0.9\,sw$ as constrained
(see `isMultiWindowConstrained`). When this fires *and* the caller opted in
via `ignoreMultiWindows = true` (suffix `i`/`ia`), the strategy returns the
**raw base** $b$ without applying any kernel. This is the only branch that
breaks Axiom 2.

---

## 4. The aspect‑ratio mould (the optional `a` suffix)

The `*a` and `*ia` façades multiply the kernel output by a scalar mould
$M_\rho$ that depends on the canvas aspect. This is the single non‑trivial
non‑identity that is **not** strategy‑local — every kernel that exposes the
mould shares its definition.

### 4.1 Pre‑aggregated affine mould (default sensitivity)

For the default sensitivity $\kappa = 0.0026\overline{6}$
(`SENSITIVITY_DEFAULT`), the mould is the pre‑aggregated factor
`arMultiplier`:

$$M_\rho^{(\kappa)} \;=\; 1 + (sw - W_0)\bigl(\varepsilon + \kappa\,L_\rho\bigr),
\qquad L_\rho = \ln\!\bigl(\rho/\rho_0\bigr).$$

It is computed once per configuration change inside
`DimenCache.updateFactors` and stored as `factors.arMultiplier`. Three
observations:

- At reference scale ($sw = W_0$) the factor collapses to $1$ — the mould is
  identity on the baseline canvas.
- The mould is **affine** in $sw$ with slope $\varepsilon + \kappa L_\rho$. For
  the reference aspect $\rho = \rho_0$ we have $L_\rho = 0$ and the mould
  collapses to $1 + (sw - W_0)\varepsilon$ — i.e. it degenerates to a pure
  proportional factor whose slope is precisely $\iota = 1/W_0$.
- Larger‑than‑reference aspects ($\rho > \rho_0$ ⇒ $L_\rho > 0$) push the
  multiplier slightly *above* the proportional baseline; squarer panels
  ($L_\rho < 0$) push it slightly below.

### 4.2 User‑injected sensitivity

A call may inject a custom $k$ via `customSensitivityK`. The formula in
`DimenCache.calculateRawScaling` becomes

$$M_\rho^{(k)} \;=\; 1 + (sw - W_0)\bigl(\varepsilon + k\,L_\rho\bigr),$$

i.e. the same affine pattern with $\kappa$ replaced by $k$. A second
pre‑computed mould `aspectRatioMul = 1 + \kappa L_\rho` is published by
`ScreenFactors` for kernels that need a $sw$‑independent multiplier (used by
the `auto`, `power`, `logarithmic`, `interpolated`, `diagonal`, `perimeter`,
`fluid`, `fill`, `fit`, and `density` paths in their `*a` variants).

### 4.3 Why a lookup for $\ln(\rho/\rho_0)$

Evaluating $\ln$ on every configuration change is cheap on x86 but visibly
non‑trivial in tight Compose recompositions on weakly‑ordered ARM64. The
library replaces it with a $111$‑entry **sorted `FloatArray` plus binary
search** (`AspectRatioLookup`) keyed by $\hat\rho = \rho/\rho_0$ and valued
by $L_\rho$. The table covers ratios from $\rho \approx 0.56$ (extreme
portrait) up to $\approx 12$ (theoretical $12\text{K}$ panoramic). When the
incoming $\hat\rho$ falls within the per‑entry tolerance of $\pm 0.35\%$
the lookup returns directly; otherwise `fastLn` falls back to `kotlin.math.ln`
and memoises the result via `DimenCache.getOrPutAspectRatio`. This pattern is
the closest the library gets to caching an analytic function and is
**orthogonal to the curves themselves** — it does not change any equation,
only the cost of evaluating it.

### 4.4 What the mould is *not*

The mould is **not** an analytically motivated correction for human perception;
it is an empirical compensation calibrated against widely shipped
phone/tablet/foldable canvases. Calls that do not exhibit aspect‑ratio
sensitivity should leave the mould off — the kernels are already designed to
behave well on the reference canvas.

---

## 5. Strategy catalogue

Every kernel below is presented as a clean mathematical function $K_S(b,d)$ of
the base $b$ and the effective axis $d$, followed by its growth profile and
analytic properties. The `*a` variant multiplies the result by the mould of
[§4](#4-the-aspect-ratio-mould-the-optional-a-suffix); the `*i` variant gates
the call behind the multi‑window bypass. Implementation pointers are in
[Appendix B](#appendix-b-source-pointers).

The roster aligns one-for-one with the **14 scaling modes** listed in the
submodule
[`appdimens-dynamic/DOCUMENTATION/README.md` summary](../appdimens-dynamic/DOCUMENTATION/README.md#summary):

| # | Section | Kernel | Hub label | Submodule doc |
|---|---------|--------|-----------|----------------|
| 1 | [§5.1](#51-scaled--linear-proportional-kernel-default) | `scaled` | DEFAULT / FIXED | [`scaled.md`](../appdimens-dynamic/DOCUMENTATION/scaled.md) |
| 2 | [§5.2](#52-auto--piecewise-linearlogarithmic-kernel-balanced) | `auto` | BALANCED | [`auto.md`](../appdimens-dynamic/DOCUMENTATION/auto.md) |
| 3 | [§5.3](#53-percent--linear-kernel--literal-space-percentages-percentage) | `percent` | PERCENTAGE / DYNAMIC | [`percent.md`](../appdimens-dynamic/DOCUMENTATION/percent.md) |
| 4 | [§5.4](#54-power--stevens-style-sublinear-kernel) | `power` | Stevens-style | [`power.md`](../appdimens-dynamic/DOCUMENTATION/power.md) |
| 5 | [§5.5](#55-logarithmic--weberfechner-damped-kernel) | `logarithmic` | Weber–Fechner | [`logarithmic.md`](../appdimens-dynamic/DOCUMENTATION/logarithmic.md) |
| 6 | [§5.6](#56-fluid--clamped-affine-band) | `fluid` | Clamp band | [`fluid.md`](../appdimens-dynamic/DOCUMENTATION/fluid.md) |
| 7 | [§5.7](#57-interpolated--fixedlinear-midpoint) | `interpolated` | Fixed–linear blend | [`interpolated.md`](../appdimens-dynamic/DOCUMENTATION/interpolated.md) |
| 8 | [§5.8](#58-diagonal--euclidean-kernel) | `diagonal` | Euclidean | [`diagonal.md`](../appdimens-dynamic/DOCUMENTATION/diagonal.md) |
| 9 | [§5.9](#59-perimeter--l1-kernel) | `perimeter` | $L^{1}$ | [`perimeter.md`](../appdimens-dynamic/DOCUMENTATION/perimeter.md) |
| 10 | [§5.10](#510-fill-and-fit--extremum-ratios) | `fit` | Letterbox | [`fit.md`](../appdimens-dynamic/DOCUMENTATION/fit.md) |
| 11 | [§5.10](#510-fill-and-fit--extremum-ratios) | `fill` | Cover | [`fill.md`](../appdimens-dynamic/DOCUMENTATION/fill.md) |
| 12 | [§5.11](#511-density--dpi-bucket-kernel) | `density` | DPI-bucket | [`density.md`](../appdimens-dynamic/DOCUMENTATION/density.md) |
| 13 | [§6](#6-constraint-based-geometry-the-resize-subsystem) | `resize` | Constraint geometry | [`resize.md`](../appdimens-dynamic/DOCUMENTATION/resize.md) |
| 14 | [§7](#7-physical-units-mm-cm-in) | physical units | Real-world | [`physical-units.md`](../appdimens-dynamic/DOCUMENTATION/physical-units.md) |

Plus a conceptual **NONE** (raw `Dp` / `Sp`) for surfaces that must stay constant.

### 5.1 `scaled` — linear proportional kernel (DEFAULT)

The proportional kernel and the library's recommended starting point.

$$\boxed{\,K_{\text{scaled}}(b, d) \;=\; b\,\frac{d}{W_0}\,}$$

**Pre‑aggregation.** When the call resolves to $d = sw$ with the default
inverter, the factor $d/W_0$ is the cached scalar `factors.scale`, computed in
`updateFactors` and consumed in `calculateRawScaling`; one multiplication
suffices per call.

**Growth profile.** Linear in $d$ with slope $b/W_0$. Tablets and TVs grow at
the same rate as phones — sometimes desired, sometimes aggressive (see
[§10](#10-comparative-discussion-and-limits)).

**`*a` mould.** The `scaled` `*a` family does **not** multiply by $M_\rho$ on
top of a separate proportional term; it replaces the kernel by the
pre‑aggregated `arMultiplier` directly:
$f_{\text{scaled}^a}(b,c) = b \cdot M_\rho^{(\kappa)}$. At reference aspect
this collapses to $b\,\iota\,sw$, recovering the bare kernel exactly.

### 5.2 `auto` — piecewise linear/logarithmic kernel (BALANCED)

A hybrid kernel that preserves phone behaviour and curbs tablet growth. The
phone leg is the `scaled` kernel; the tablet leg switches to a logarithmic
suffix that compresses growth past the transition $T = 480\,\text{dp}$
(`transition`) at sensitivity $\alpha = 0.4$.

$$\boxed{\,K_{\text{auto}}(b, d) \;=\; b \cdot
\begin{cases}
d\,\iota & d \le T,\\[4pt]
T\,\iota \;+\; \alpha\,\ln\!\bigl(1 + (d - T)\,\iota\bigr) & d > T.
\end{cases}\,}$$

**Continuity.** $K_{\text{auto}}$ is $C^0$ at $d = T$ (both pieces hit
$T \iota = 1.6$). The first derivative drops from $\iota$ to
$\alpha\,\iota / (1 + (d-T)\iota)\big|_{d=T} = \alpha \iota$, i.e. the slope is
multiplied by $\alpha = 0.4$ across the transition. This intentional kink is
the visual signature of the BALANCED curve: phones grow linearly, tablets
breathe.

**Implementation.** `calculateAutoDpCompose` in
[`DimenAutoDp.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/compose/auto/DimenAutoDp.kt).
The phone leg reduces to the same pre‑aggregated `factors.scale` as `scaled`.

### 5.3 `percent` — linear kernel + literal `space*` percentages (PERCENTAGE)

A two‑headed API. The token family (`psdp`, `phdp`, `pwdp`, `pssp`, …) shares
the proportional kernel with `scaled`:

$$K_{\text{percent}}(b, d) \;=\; b\,d\,\iota \;=\; K_{\text{scaled}}(b, d).$$

What distinguishes `percent` is its second surface — the **literal**
`space*` family in
[`DimenPercentSpace.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/compose/percent/DimenPercentSpace.kt).
Here the receiver is read as a percentage in $[0, 100]$:

$$\text{spaceW}(p)\;=\;\frac{p}{100}\,\cdot\,\text{screenWidthDp},$$

with mirrored properties for $sw$ (`spaceSw`), $h$ (`spaceH`), and an explicit
reference length (`space(reference)`). The `*Px` variants return a `Float` in
layout pixels; the `*Sp` variants return a `TextUnit` whose value is the same
percentage of the chosen edge.

**`*a` mould.** `psdp`/`phdp`/`pwdp` accept the mould exactly as `scaled`
does. The `space*` family is, by construction, a literal percentage of a side
and therefore aspect‑independent.

### 5.4 `power` — Stevens‑style sublinear kernel

A sublinear power law in the spirit of Stevens [\[3\]](#11-references):

$$\boxed{\,K_{\text{power}}(b, d) \;=\; b \left(\frac{d}{W_0}\right)^{\!n},
\qquad n = 0.75.\,}$$

**Growth profile.** Concave in $d$: $K_{\text{power}}$ grows slower than the
linear kernel and reins in tablet/TV sizes without ever clamping. For the
default $n = 0.75$, doubling $d$ multiplies the output by $2^{0.75}\approx 1.68$
(vs. $2$ for `scaled`).

**Pre‑aggregation.** `factors.powerScale = (sw/W_0)^{0.75}` is computed once
per configuration change for the $sw$+default‑inverter path; the call cost is
a single multiplication. Other qualifiers evaluate `Math.pow` inline.

### 5.5 `logarithmic` — Weber–Fechner damped kernel

A symmetric log compression around $W_0$:

$$\boxed{\,K_{\text{log}}(b, d) \;=\; b \cdot
\begin{cases}
1 + \beta\,\ln(d/W_0) & d > W_0,\\[4pt]
1 - \beta\,\ln(W_0/d) & d \le W_0,
\end{cases}
\qquad \beta = 0.4.\,}$$

**Continuity.** $K_{\text{log}}$ is $C^{1}$ at $d = W_0$ (both pieces hit
$1$, both first derivatives equal $\beta/W_0$). It is the only kernel with
explicit symmetric behaviour for very small and very large canvases.

**Psychophysical motivation.** The Weber–Fechner law [\[2\]](#11-references)
states that perceived magnitude grows logarithmically with stimulus
intensity, $\psi = k \ln(I/I_0)$. The `logarithmic` kernel transposes this
idea to *device extent*, treating the canvas as the stimulus.

**Pre‑aggregation.** `factors.logScale` caches the result for $sw$+default
inverter. See
[`DimenLogarithmicDp.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/compose/logarithmic/DimenLogarithmicDp.kt).

### 5.6 `fluid` — clamped affine band

A "viewport units"‑style band, clamped outside it:

$$\boxed{\,K_{\text{fluid}}(b, d) \;=\;
\max\!\Bigl(\min\!\bigl((1-t(d))\cdot 0.8\,b + t(d)\cdot 1.2\,b,\;1.2\,b\bigr),\;0.8\,b\Bigr),\quad
t(d) = \frac{d - 320}{768 - 320}.\,}$$

(Linear blend between $0.8\,b$ and $1.2\,b$ in $t$, then clamped to $[0.8\,b,\,1.2\,b]$ — GitHub math does not support `\operatorname{lerp}` / `\operatorname{clamp}`.)

**Growth profile.** Linear in $d$ inside $[320, 768]$ (the modern phone band);
constant outside. The kernel never moves more than $\pm 20\%$ off the base —
useful for typography and dense interfaces where large excursions hurt.

**Edge behaviour.** Above $768\,\text{dp}$ the output saturates at $1.2\,b$;
below $320\,\text{dp}$ it pins at $0.8\,b$. The user must opt in to tablet
growth elsewhere if it is required.

### 5.7 `interpolated` — fixed/linear midpoint

An affine blend between the static base and the linear‑proportional value:

$$\boxed{\,K_{\text{interp}}(b, d) \;=\; b + \frac{1}{2}\bigl(b\,d\,\iota - b\bigr)
\;=\; b\bigl(\tfrac{1}{2} + \tfrac{1}{2}\,d\,\iota\bigr).\,}$$

**Properties.** Affine in $d$ with slope $b/(2 W_0)$ — half the rate of
`scaled`. Useful when the base token already looks acceptable on phones but
should not stay static on tablets.

The $1/2$ blend is fixed in the library — there is no per‑call exponent. For
custom blends the user can express the same shape through `scaledDp().screen(…)`
overloads or compose two strategies manually.

### 5.8 `diagonal` — Euclidean kernel

A canvas‑isotropic scalar driven by the diagonal:

$$\boxed{\,K_{\text{diag}}(b, c) \;=\; b\,\frac{\sqrt{s_{\min}^2 + s_{\max}^2}}{D_0}.\,}$$

The qualifier `SMALL_WIDTH`/`WIDTH`/`HEIGHT` does not change the diagonal — the
formula always reads both sides of the window. `factors.diagonalScale` caches
the scalar per configuration; cf.
[`DimenDiagonalDp.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/compose/diagonal/DimenDiagonalDp.kt).

**Geometric interpretation.** $K_{\text{diag}}$ is the $L^{2}$ norm of the
window normalised to the reference frame. For canvases within the same aspect
ratio it behaves like a uniform scaling, with the canvas itself substituting
the chosen axis.

### 5.9 `perimeter` — $L^{1}$ kernel

The $L^{1}$ companion of `diagonal`:

$$\boxed{\,K_{\text{perim}}(b, c) \;=\; b\,\frac{s_{\min} + s_{\max}}{P_0}.\,}$$

For the reference frame, $s_{\min} + s_{\max} = P_0 = 833$, so
$K_{\text{perim}}(b, c_0) = b$. Two canvases with the same diagonal but
different aspects can disagree on the perimeter ratio — `perimeter` weights
both sides linearly while `diagonal` weights them quadratically. Cached as
`factors.perimeterScale`.

### 5.10 `fill` and `fit` — extremum ratios

These two kernels reuse both reference sides $W_0, H_0$ instead of the single
axis $W_0$:

$$\boxed{\,K_{\text{fill}}(b, c) \;=\; b\,\max\!\Bigl(\tfrac{s_{\min}}{W_0},\,
\tfrac{s_{\max}}{H_0}\Bigr),\qquad
K_{\text{fit}}(b, c) \;=\; b\,\min\!\Bigl(\tfrac{s_{\min}}{W_0},\,
\tfrac{s_{\max}}{H_0}\Bigr).\,}$$

**Interpretation.** `fill` is CSS "cover": the canvas is scaled by whichever
side needs more growth, so content overflows or is clipped if needed. `fit`
is CSS "contain": the canvas is scaled by whichever side needs *less*
growth, so content never overflows. Pair them in the same screen for hero
sections (`fill`) and body content (`fit`).

### 5.11 `density` — dpi‑bucket kernel

The only kernel that does **not** read $sw$/$w$/$h$:

$$\boxed{\,K_{\text{density}}(b, c) \;=\; b\,\frac{\text{densityDpi}}{160}.\,}$$

It tracks the **resource bucket** (`mdpi`/`hdpi`/`xhdpi`/…) rather than the
canvas extent. Two devices with identical $sw$ but different `densityDpi`
diverge — typically the opposite of what `sw`‑based responsive layout wants.
Reserve for asset alignment, stroke widths, and legacy bitmaps; cache lives
in `factors.density`.

---

## 6. Constraint‑based geometry: the `resize` subsystem

`resize` is **not** a scaling curve. It is a constraint‑solving subsystem in
[`ResizeMath.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/ResizeMath.kt)
and the parallel Compose helpers in
[`compose.resize`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/compose/resize/).
Given a fitness predicate $\phi\colon (0,\infty) \to \{\mathrm{true},\,\mathrm{false}\}$ that is non‑increasing on the set where $\phi$ is true, it returns
the **largest** candidate from a discretised step table that still satisfies
$\phi$:

$$\boxed{\,f_{\text{resize}}(\phi,\,b_{\min},\,b_{\max},\,\Delta)
\;=\; \max\!\bigl\{\,x \in C_{\min,\max,\Delta} \;:\; \phi(x)\bigr\},\,}$$

with $C_{\min,\max,\Delta} = \{b_{\min} + i\Delta : 0 \le i,\;
b_{\min} + i\Delta \le b_{\max}\}$. The candidate set is materialised once as
a primitive `FloatArray` (`buildResizeStepsPx`), then explored by binary
search (`findLargestFittingResizePx`); when no candidate fits, the result is
$0$.

Typical predicates measure a `TextLayoutResult` against a constraint
(`autoResizeTextSp`) or compare a square side against
$\min(\text{inner width}, \text{inner height})$ (`autoResizeSquareSize`).
Bounds may be expressed as fixed $\text{dp}$/$\text{sp}$ or as fractions of
$sw$/$w$/$h$ via `ResizeBound`. All call sites live **inside**
`BoxWithConstraints` so the predicate has the real parent rectangle, not the
window rectangle.

**Complexity.** $O(\log N)$ predicate evaluations for $N$
candidates; the table is bounded to $\le 4096$ entries (`MAX_RESIZE_STEPS`).
Memory is $O(N)$ but allocated as a single primitive array — no
auto‑boxing in the hot path.

---

## 7. Physical units (mm, cm, in)

The `physical-units` module in
[`compose/units/DimenPhysicalUnits.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/compose/units/DimenPhysicalUnits.kt)
and `code.units.DimenPhysicalUnits` is **orthogonal** to the scaling kernels.
It maps real‑world lengths to `dp`/`sp`/`px` through Android's
`TypedValue` machinery — specifically `COMPLEX_UNIT_MM` and `COMPLEX_UNIT_IN`
— and exposes `radius` / `unitSizeInDp` helpers for shape geometry.

This subsystem is the right tool when the spec is **physical** (a tap target
in millimetres, a ruler‑calibrated marker, an accessibility minimum in
inches). It is the wrong tool for grid sizing because real vs. nominal
`densityDpi` varies in the wild.

---

## 8. Numerical comparison across a sweep of $sw$

The table below evaluates every $sw$‑driven kernel with $b = 48\,\text{dp}$
on three canonical canvases. Aspect ratio is held at $\rho = \rho_0$ to keep
the comparison kernel‑only; the mould is identity in that column by design
([§4.1](#41-pre-aggregated-affine-mould-default-sensitivity)).

| Kernel | $sw = 320$ | $sw = 360$ | $sw = 480$ | $sw = 720$ | $sw = 1080$ |
|:---|---:|---:|---:|---:|---:|
| `scaled` | $51.2$ | $57.6$ | $76.8$ | $115.2$ | $172.8$ |
| `auto` | $51.2$ | $57.6$ | $76.8$ | $88.1$ | $97.9$ |
| `percent` (token) | $51.2$ | $57.6$ | $76.8$ | $115.2$ | $172.8$ |
| `power`  ($n=0.75$) | $50.2$ | $55.1$ | $69.3$ | $92.6$ | $125.4$ |
| `logarithmic` | $47.0$ | $51.5$ | $57.0$ | $64.8$ | $72.6$ |
| `fluid` | $38.4$ | $40.1$ | $45.5$ | $55.5$ | $57.6$ |
| `interpolated` | $49.6$ | $52.8$ | $62.4$ | $81.6$ | $110.4$ |
| `density` ($160 \to 480\,\text{dpi}$) | $48.0$ | $48.0$ | $96.0$ | $144.0$ | $144.0$ |

Notes on the table:

- **`scaled`** and **`percent`** coincide on the token surface; they diverge
  only because `percent` also exposes the literal `space*` API.
- **`auto`** matches `scaled` up to $sw = T = 480\,\text{dp}$ and then bends
  with the logarithmic suffix.
- **`fluid`** saturates at $1.2\,b = 57.6\,\text{dp}$ above $768\,\text{dp}$.
- **`density`** is shown for completeness; it is not a function of $sw$ and
  values are illustrative (depending on the device's `densityDpi`).

For canvas‑isotropic kernels (`diagonal`, `perimeter`, `fill`, `fit`) the
table is two‑dimensional in $(s_{\min}, s_{\max})$. A representative slice
appears in the Compose tests under
[`StrategyModuleFormulasTest.kt`](../appdimens-dynamic/library/src/test/java/com/appdimens/dynamic/modules/StrategyModuleFormulasTest.kt).

---

## 9. Engineering invariants (no curves here)

The kernels above are stated as mathematical objects. Their implementation in
`DimenCache.kt` adds engineering invariants that affect *performance* but
**not** the equations. They are worth naming for context:

- **Configuration‑synchronous pre‑aggregation.** All transcendental
  evaluations (`ln`, `Math.pow`, `sqrt`) are lifted to a single batch inside
  `updateFactors` and stored in a padded `ScreenFactors` object. Strategies
  that resolve to `SMALL_WIDTH` + default inverter pay one primitive
  multiplication per call.
- **Lock‑free shared cache.** Token resolutions are memoised in a sharded
  `AtomicLongArray` / `AtomicIntegerArray` pair (`shards`, `CACHE_SIZE = 2048`,
  `SHARD_COUNT = 4`). Cache keys encode the full call signature in a single
  `Long` (`buildKey`), so two different qualifiers, inverters, AR flags, or
  sensitivities never collide. Hits are last‑write‑wins and always correct.
- **Selective invalidation.** `invalidateOnConfigChange` compares the old and
  new `Configuration` and only clears the cache when something physical
  changes (any of $s_{\min}, s_{\max}, sw$, `densityDpi`, `fontScale`).
  Orientation flips alone do not clear: the orientation bit is part of the
  key.
- **Aspect‑ratio fast path.** `AspectRatioLookup` plus `fastLn` replace a
  transcendental on the hot path of any `*a` token after the first call.
- **Multi‑window bypass.** When `ignoreMultiWindows = true` and the
  heuristic flags the window as constrained, the strategy returns the raw
  base, by design.

None of the above changes the analytical output for a given $(b, c, S)$
triple; they are pure performance scaffolding. Tests in
[`DimenCacheTest`](../appdimens-dynamic/library/src/test/java/com/appdimens/dynamic/core/DimenCacheTest.kt)
and `StrategyModuleFormulasTest` guarantee that the cached path agrees with
the analytic formula to the IEEE 754 tolerance noted in
[§2](#2-notation-units-and-reference-frame).

---

## 10. Comparative discussion and limits

### 10.1 Choosing a kernel

The kernels are organised by **how aggressively they grow on large canvases**.
For a fixed base $b$ and reference frame, the rate of growth in $sw$ near
$sw = W_0$ is

| Kernel | $K_S'(b, sw)\big|_{sw=W_0}$ |
|:---|:---:|
| `scaled`, `percent` (token) | $b/W_0$ |
| `auto` (phone leg) | $b/W_0$ |
| `power` ($n=0.75$) | $0.75\,b/W_0$ |
| `interpolated` | $b/(2W_0)$ |
| `logarithmic` | $\beta\,b/W_0 = 0.4\,b/W_0$ |
| `fluid` (inside band) | $0.4\,b/(768-320) \approx 0.00089\,b$ |

The classification follows naturally: `scaled` is the linear baseline;
`power`, `interpolated`, and `logarithmic` damp it; `auto` matches `scaled`
on phones and damps it on tablets; `fluid` damps it on phones and clamps it
entirely on tablets.

### 10.2 Where kernels disagree on purpose

- `scaled` vs. `auto`: identical on phones, divergent on tablets. Use `auto`
  when the same token must span a phone and a $720\,\text{dp}$ tablet without
  hand‑tuned breakpoints.
- `diagonal` vs. `perimeter`: same fixed point, different weighting of the
  two sides. `diagonal` punishes squareness ($s_{\min} \approx s_{\max}$);
  `perimeter` rewards it. Designers should agree on which "canvas size"
  matters before mixing them on the same hierarchy.
- `fill` vs. `fit`: opposite extremes of the same family. They are the
  building blocks of letterbox/cover semantics — never both on the same
  surface unless explicitly chained.

### 10.3 What the library deliberately does **not** do

- It does **not** auto‑select a strategy. There is no runtime dispatcher or
  "smart inference". Every call site picks its strategy explicitly through
  the package it imports from.
- It does **not** dynamically classify "element types" (icon, title, gutter,
  …). The API is geometry‑agnostic — the developer maps tokens to kernels
  in their design system.
- It does **not** weight `dp` by content semantics. Every token of the same
  base goes through the same kernel on a given call site.

These three negatives are what makes the library both predictable and
auditable — every output traces to a single formula and a single
`Configuration` snapshot.

### 10.4 Limits of the framework

- Kernels treat the canvas as a single rectangle. They have no opinion on
  *layout* — that is what `Modifier` and `BoxWithConstraints` are for. The
  `resize` subsystem (§6) is the bridge when a token must fit a *concrete*
  rectangle.
- The mould of §4 is calibrated for AR sweeps observed in shipping mobile
  hardware. Extreme aspect ratios past the `AspectRatioLookup` range fall
  back to a single `kotlin.math.ln` call and still behave smoothly, but the
  empirical constants $\varepsilon$ and $\kappa$ are not claimed to be
  optimal for every panel imaginable.
- All math is in 32‑bit floats. Tokens beyond the safe range
  ($|b\cdot d| \gtrsim 10^{6}\,\text{dp}^{2}$) are not exercised by the test
  suite and should be considered out of scope.

---

## 11. References

[1] Google. *Android Developers — `Configuration`* (`smallestScreenWidthDp`,
`screenWidthDp`, `screenHeightDp`, `densityDpi`, `fontScale`). Reference for
the resource‑qualifier semantics that every kernel reads.
<https://developer.android.com/reference/android/content/res/Configuration>

[2] Fechner, G. T. *Elemente der Psychophysik*. Breitkopf & Härtel, Leipzig,
1860. Establishes the Weber–Fechner law $\psi = k\,\ln(I/I_0)$ that motivates
the `logarithmic` kernel.

[3] Stevens, S. S. "On the psychophysical law." *Psychological Review*
**64** (1957), 153–181. The power‑law of psychophysics
$\psi = k\,I^{n}$ that motivates the `power` kernel with $n = 0.75$.

[4] Google. *Material Design — Layout and Foundations.* Design guidance for
density, touch targets, and breakpoints that informs the reference frame.
<https://m3.material.io/foundations/layout>

[5] Smashing Magazine. "Fluid responsive typography with CSS Poly Fluid
Sizing." Discussion of the clamped‑linear pattern adopted by the `fluid`
kernel.
<https://www.smashingmagazine.com/2016/05/fluid-typography/>

[6] Bodenberg, J. *AppDimens Dynamic — Mathematics and Calculus.* Companion
formal reference; consult for current implementation deltas.
[`MATHEMATICS-AND-CALCULUS.md`](../appdimens-dynamic/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md).

[7] Bodenberg, J. *AppDimens Dynamic — Compose API Conventions.* Complete
catalogue of the `Number` and `TextUnit` extensions, inverters, and
builders.
[`COMPOSE-API-CONVENTIONS.md`](../appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md).

### Suggested citation

```bibtex
@techreport{bodenberg2026appdimens_theory,
  title       = {AppDimens — Mathematical Theory of UI Scaling Kernels},
  author      = {Bodenberg, Jean},
  year        = {2026},
  institution = {Open source — AppDimens project},
  url         = {https://github.com/bodenberg/appdimens},
  note        = {Companion to the appdimens-dynamic library; see DOCS/THEORY.md}
}
```

---

## Appendix A — hub labels ↔ Gradle strategies

The hub uses platform‑neutral labels in narrative material; the
Compose 3.x artifact publishes Gradle/package names. The mapping is exact —
no semantic drift.

| Hub label | Gradle strategy | Compose package | Token stems |
|:---|:---|:---|:---|
| **BALANCED** | `auto` | `com.appdimens.dynamic.compose.auto` | `asdp`, `ahdp`, `awdp`, `assp`, … |
| **DEFAULT** | `scaled` | `com.appdimens.dynamic.compose.scaled` | `sdp`, `hdp`, `wdp`, `ssp`, `sem`, … |
| **PERCENTAGE** | `percent` | `com.appdimens.dynamic.compose.percent` | `psdp`, `pssp`, `spaceW`, `spaceSw`, … |
| **DYNAMIC** | `percent` (linear path) | — | same as `percent` token surface |
| — | `power` | `com.appdimens.dynamic.compose.power` | `pwsdp`, `pwhdp`, `pwwdp`, … |
| — | `logarithmic` | `com.appdimens.dynamic.compose.logarithmic` | `logsdp`, `loghdp`, `logwdp`, … |
| — | `fluid` | `com.appdimens.dynamic.compose.fluid` | `fsdp`, `fhdp`, `fwdp`, … |
| — | `interpolated` | `com.appdimens.dynamic.compose.interpolated` | `isdp`, `ihdp`, `iwdp`, … |
| — | `diagonal` | `com.appdimens.dynamic.compose.diagonal` | `dgsdp`, `dghdp`, `dgwdp`, … |
| — | `perimeter` | `com.appdimens.dynamic.compose.perimeter` | `prsdp`, `prhdp`, `prwdp`, … |
| — | `fill` | `com.appdimens.dynamic.compose.fill` | `flsdp`, `flhdp`, `flwdp`, … |
| — | `fit` | `com.appdimens.dynamic.compose.fit` | `ftsdp`, `fthdp`, `ftwdp`, … |
| — | `density` | `com.appdimens.dynamic.compose.density` | `dsdp`, `dhdp`, `dwdp`, … |
| — | `resize` (subsystem) | `com.appdimens.dynamic.compose.resize` | `autoResizeTextSp`, `autoResizeSquareSize`, … |
| — | `units` (physical) | `com.appdimens.dynamic.compose.units` | `.mm`, `.cm`, `.inch`, `radius`, … |

The legacy labels **Fixed (FX)** and **Dynamic (DY)** that appeared in early
versions of this hub map to **`scaled`** and **`percent`** respectively; no
strategy carries those names today.

---

## Appendix B — source pointers

When inspecting the library, the most informative files for each kernel are:

| Kernel | Compose entry point | Pre‑aggregation field |
|:---|:---|:---|
| `scaled` | `compose/scaled/DimenSdp.kt` | `factors.scale` |
| `auto` | `compose/auto/DimenAutoDp.kt` (`calculateAutoDpCompose`) | `factors.scale` (phone leg) |
| `percent` | `compose/percent/DimenPercentDp.kt`, `DimenPercentSpace.kt` | `factors.scale` (token leg) |
| `power` | `compose/power/DimenPowerDp.kt` (`calculatePowerDpCompose`) | `factors.powerScale` |
| `logarithmic` | `compose/logarithmic/DimenLogarithmicDp.kt` (`calculateLogarithmicDpCompose`) | `factors.logScale` |
| `fluid` | `compose/fluid/DimenFluidDp.kt` (`calculateFluidDpCompose`) | — |
| `interpolated` | `compose/interpolated/DimenInterpolatedDp.kt` (`calculateInterpolatedDpCompose`) | `factors.interpolatedScale` |
| `diagonal` | `compose/diagonal/DimenDiagonalDp.kt` (`calculateDiagonalDpCompose`) | `factors.diagonalScale` |
| `perimeter` | `compose/perimeter/DimenPerimeterDp.kt` (`calculatePerimeterDpCompose`) | `factors.perimeterScale` |
| `fill`, `fit` | `compose/fill/DimenFillDp.kt`, `compose/fit/DimenFitDp.kt` | — |
| `density` | `compose/density/DimenDensityDp.kt` (`calculateDensityDpCompose`) | `factors.density` |
| `resize` | `compose/resize/`, `core/ResizeMath.kt`, `core/ResizeBound.kt` | — |
| `units` | `compose/units/DimenPhysicalUnits.kt`, `code/units/DimenPhysicalUnits.kt` | — |

The aspect‑ratio mould of [§4](#4-the-aspect-ratio-mould-the-optional-a-suffix)
is shared across kernels via
[`DimenCache.calculateRawScaling`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DimenCache.kt)
and the lookup table in
[`core/AspectRatioLookup.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/AspectRatioLookup.kt).
The shared screen plumbing — inverters, multi‑window heuristics, and
`readScreenDp` — lives in
[`core/DimenCalculationPlumbing.kt`](../appdimens-dynamic/library/src/main/java/com/appdimens/dynamic/core/DimenCalculationPlumbing.kt).
For an exhaustive index of the `Number` / `TextUnit` surface (suffixes,
inverters, `*a`/`*i`/`*ia`, builders) consult
[`COMPOSE-API-CONVENTIONS.md`](../appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md).

---

## Where to go next

| Goal | Document |
|------|---------|
| Tactical decisions — which kernel for which use case | **[GUIDE.md](GUIDE.md)** |
| Concept ↔ API map across Android / iOS / KMP / Flutter / RN / Web | **[PLATFORMS.md](PLATFORMS.md)** |
| Migrate from 1.x / 2.x or from third-party SDP / SSP | **[MIGRATION.md](MIGRATION.md)** |
| Rotation & base orientation semantics | **[ORIENTATION.md](ORIENTATION.md)** |
| Long-form code recipes per stack | **[EXAMPLES.md](EXAMPLES.md)** |
| Canonical submodule math | **[`appdimens-dynamic/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md`](../appdimens-dynamic/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)** |
| Canonical Compose API surface | **[`appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md`](../appdimens-dynamic/DOCUMENTATION/COMPOSE-API-CONVENTIONS.md)** |
| Performance methodology | **[`appdimens-dynamic/PERFORMANCE.md`](../appdimens-dynamic/PERFORMANCE.md)** |

---

**Jean Bodenberg** — Apache 2.0 · Repository hub: <https://github.com/bodenberg/appdimens>

[← Documentation index](README.md) · [Back to top](#appdimens--mathematical-theory)
