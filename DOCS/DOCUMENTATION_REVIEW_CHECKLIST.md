# Hub documentation review checklist

> **Hub documentation.** Use this before merging edits to **`DOCS/`** or hub-level Markdown at the repo root. It does **not** replace submodule release review.

---

## Scope rules

| Rule | Detail |
|------|--------|
| No semver in `DOCS/` | Do not add Maven/npm/pub/CocoaPods pins—point to **[`README.md`](../README.md#submodule-map)** or submodule files instead. |
| English only here | Localization lives in submodule repos if offered. Do not add `LANG/` links to non-existent folders. |
| Theory vs installs | Explain *why* a strategy behaves as it does; link out for exact API tokens. |

## Content quality

- [ ] Starts with hub banner (blockquote) when missing.
- [ ] Internal links resolve (relative paths to `README.md`, `PLATFORM_API_MAP.md`, submodules).
- [ ] Tables and formulas render (LaTeX `$$ … $$`, pipe tables escaped correctly).
- [ ] Mermaid blocks use quoted labels where labels contain parentheses; node IDs contain no bare spaces.

## Cross-platform wording

- [ ] Strategy names (**BALANCED**, **DEFAULT**, …) aligned with **`[PLATFORM_API_MAP.md](PLATFORM_API_MAP.md)`** + **`[IMPLEMENTATION_ALIGNMENT_APPDIMENS_DYNAMIC.md](IMPLEMENTATION_ALIGNMENT_APPDIMENS_DYNAMIC.md)`** (`sdp`≠BALANCED unless you are intentionally documenting **`scaled`** only).
- [ ] Android samples: **`BALANCED→asdp`**; **`FIXED`/`DEFAULT`→`sdp`/`scaled`**; **`DYNAMIC`/`PERCENTAGE`→`percent`/`psdp`** — **`[NAMING_AND_MIGRATION_1X_2X_TO_3X.md](NAMING_AND_MIGRATION_1X_2X_TO_3X.md)`**.

## Code fences in theory docs

- [ ] Samples are **illustrative**—add a reminder to confirm syntax in the submodule.
- [ ] No production coordinates inside fenced snippets.

## Final pass

- [ ] **Math / Android:** reconcile with **[IMPLEMENTATION_ALIGNMENT_APPDIMENS_DYNAMIC.md](IMPLEMENTATION_ALIGNMENT_APPDIMENS_DYNAMIC.md)** + **[`appdimens-dynamic/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md`](../appdimens-dynamic/DOCUMENTATION/MATHEMATICS-AND-CALCULUS.md)** (`auto` breakpoints = **BALANCED**; **`scaled`** tokens = **`sdp`** / **`ssp`**, …).
- [ ] Grep the changed file(s) for `LANG/`, `implementation("io.github`, `pod '`, `@2.0`, `-v20` leftovers.
- [ ] Update **`DOCS/README.md`** navigation if adding a brand-new theory page.
