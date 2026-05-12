# AppDimens at a glance

This brief lives in the **[appdimens](https://github.com/bodenberg/appdimens) documentation hub**—a meta-repository aggregating theory & links, **not** a single installable package.

## Problem

Plain **dp / sp / pt / px** handle density classes but rarely encode *how* components should grow across phones, tablets, TVs, watches, and fluid web viewports. Teams patch gaps with huge `values-sw*` trees or aggressive percentage layouts, which often **overshoot** on large canvases or **under-serve** accessibility on tiny ones.

## Proposal

AppDimens is a **family of libraries** that share a mathematical vocabulary of **scaling strategies** (hybrid **BALANCED** curves, logarithmic / power perceptual models, **FLUID** bounds, **FIT/FILL** game modes, **Smart** element inference on some stacks, etc.). Pick the curve that matches your UX risk profile instead of defaulting to linear stretch everywhere.

## Where things live

| Need | Location |
|------|----------|
| **Install coordinates** & per-release notes | Submodule READMEs linked from [`README.md`](README.md#submodule-map) |
| **Strategy math & comparisons** | [`DOCS/README.md`](DOCS/README.md) |
| **Visual PDFs / HTML ladders / raster gallery** | [`README.md#visual-resources`](README.md#visual-resources) |

## Platform surface (conceptual)

- **Android (production today):** `appdimens-dynamic` (Compose/code), plus XML **SDP / SSP**.
- **Android games, iOS, KMP test line, Flutter, React Native, Web:** follow hub status table (**Production vs Work in progress**) in [`README.md`](README.md).
- **KMP:** pre-production **test** artifacts on Maven coordinate **`appdimens-dynamic` 4.x** today; planned first **stable GA** semver **1.0.0** (see hub README narrative).

Nothing in this file pins dependencies—always copy coordinates from the **exact submodule README** you integrate.
