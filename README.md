<div align="center">
   <img src="IMAGES/image_sample_devices.png" alt="AppDimens - Responsive Design Across All Devices" height="300"/>
<h1>AppDimens</h1>
<p><strong>Central repository — documentation hub and multi-platform library family</strong></p>

[![Meta-repo](https://img.shields.io/badge/repo-meta--hub-blue.svg)](https://github.com/bodenberg/appdimens)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Platforms](https://img.shields.io/badge/platforms-Android%20%7C%20iOS%20%7C%20Flutter%20%7C%20RN%20%7C%20Web-orange.svg)](https://github.com/bodenberg/appdimens)

[Documentation index](DOCS/README.md) · [Quick reference](DOCS/DOCS_QUICK_REFERENCE.md) · [Technical guide](DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

**Languages:** English (this page) · [Português (BR)](LANG/pt-BR/README.md) · [Español](LANG/es/README.md)
</div>

---

## What this repository is

**This is the central _meta-repository_ for AppDimens.** It aggregates official documentation, theory, examples, and multilingual guides. **Source code and releases** for each platform live in **separate Git repositories** included here as **[Git submodules](.gitmodules)**.

- Clone with submodules: `git clone --recurse-submodules https://github.com/bodenberg/appdimens.git`
- Update submodules: `git submodule update --init --recursive`

If you only need one platform, you can clone that repository directly (see table below).

---

## Submodule map

Each row is a **standalone repo** (path in this clone = submodule directory). **Version** is the current published line documented in that repo’s README or build files at the submodule tip; always confirm in the submodule before pinning dependencies.

| Submodule | Platform | Version (see submodule) | Status | Upstream / folder |
|-----------|----------|-------------------------|--------|-------------------|
| **appdimens-dynamic** | Android (Jetpack Compose, Kotlin/Java) | 3.1.4 | Stable | [appdimens-dynamic/](appdimens-dynamic/) |
| **appdimens-dynamic-kmp** | Kotlin Multiplatform (Android, iOS, Desktop) | 1.0.0 | Work in progress | [appdimens-dynamic-kmp/](appdimens-dynamic-kmp/) |
| **appdimens-sdps** | Android XML scalable DP (`@dimen/_*sdp`) | 3.1.2 | Stable | [appdimens-sdps/](appdimens-sdps/) |
| **appdimens-ssps** | Android XML scalable SP (`@dimen/_*ssp`) | 3.1.2 | Stable | [appdimens-ssps/](appdimens-ssps/) |
| **appdimens-games** | Android games (C++/NDK, OpenGL) | 2.0.1 | Work in progress (see module README) | [appdimens-games/](appdimens-games/) |
| **appdimens-flutter** | Flutter / Dart | 2.0.0 | Work in progress | [appdimens-flutter/](appdimens-flutter/) |
| **appdimens-ios** | iOS / macOS / tvOS / watchOS (Swift, SPM, CocoaPods) | 2.0.0 | Work in progress (rich docs inside repo) | [appdimens-ios/](appdimens-ios/) |
| **appdimens-react-native** | React Native (TypeScript) | 2.0.0 | Work in progress | [appdimens-react-native/](appdimens-react-native/) |
| **appdimens-web** | Web / TypeScript (`webdimens`) | 2.0.0 | Work in progress | [appdimens-web/](appdimens-web/) |

GitHub mirrors: [dynamic](https://github.com/bodenberg/appdimens-dynamic) · [kmp](https://github.com/bodenberg/appdimens-dynamic-kmp) · [sdps](https://github.com/bodenberg/appdimens-sdps) · [ssps](https://github.com/bodenberg/appdimens-ssps) · [games](https://github.com/bodenberg/appdimens-games) · [flutter](https://github.com/bodenberg/appdimens-flutter) · [ios](https://github.com/bodenberg/appdimens-ios) · [react-native](https://github.com/bodenberg/appdimens-react-native) · [web](https://github.com/bodenberg/appdimens-web)

---

## Quick overview

AppDimens provides **responsive dimensions** across phones, tablets, TVs, watches, and browsers, using **perceptual scaling** (e.g. Weber–Fechner, Stevens’ power law) and **multiple strategies** so you can pick the right curve for buttons, typography, containers, and games.

**Central docs (this repo):** strategy comparison, math, validation, and cross-platform examples live under [`DOCS/`](DOCS/README.md). **Platform-specific installation, APIs, and changelogs** live in each **submodule** (links in the table above).

---

## Install snippets (confirm version in submodule README)

### Android — dynamic (Compose / code)

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:3.1.4")
}
```

→ Full setup, Compose, XML, performance: **[appdimens-dynamic/README.md](appdimens-dynamic/README.md)**

### Android — SDP / SSP (XML resources)

```kotlin
implementation("io.github.bodenberg:appdimens-sdps:3.1.2")
implementation("io.github.bodenberg:appdimens-ssps:3.1.2")
```

→ **[appdimens-sdps/README.md](appdimens-sdps/README.md)** · **[appdimens-ssps/README.md](appdimens-ssps/README.md)**

### Android — games (optional)

```kotlin
implementation("io.github.bodenberg:appdimens-games:2.0.1")
```

→ **[appdimens-games/appdimens_games/README.md](appdimens-games/appdimens_games/README.md)**

### iOS

```ruby
pod 'AppDimens', '~> 2.0.0'
```

→ **[appdimens-ios/](appdimens-ios/)** (see `INSTALLATION.md`, `USAGE_GUIDE.md`, `DOCUMENTATION.md` inside the submodule)

### Flutter

```yaml
dependencies:
  appdimens: ^2.0.0
```

→ **[appdimens-flutter/](appdimens-flutter/)**

### React Native

```bash
npm install appdimens-react-native@2.0.0
```

→ **[appdimens-react-native/](appdimens-react-native/)**

### Web (`webdimens`)

```bash
npm install webdimens@2.0.0
```

→ **[appdimens-web/](appdimens-web/)** · [Quick start](appdimens-web/QUICK_START.md)

### Kotlin Multiplatform

→ **[appdimens-dynamic-kmp/](appdimens-dynamic-kmp/)** (experimental; not production-ready per submodule notice)

---

## Scaling strategies (summary)

The family documents **13 strategies** (names may vary slightly by platform). For formulas, rankings, and when to use each:

| Strategy | Typical use |
|----------|-------------|
| **BALANCED (auto)** | Default recommendation for most UI |
| **DEFAULT (scaled)** | Phone-first / legacy “fixed” style curve |
| **PERCENTAGE** | Proportional / “dynamic” style growth |
| **LOGARITHMIC**, **POWER** | Perceptual / TV / fine control |
| **FLUID** | Bounded min–max (typography, spacing) |
| **INTERPOLATED**, **DIAGONAL**, **PERIMETER** | Specialized geometry |
| **FIT**, **FILL** | Game-style letterbox / cover |
| **AUTOSIZE** | Container-aware text sizing |
| **NONE** | No scaling |

Details: [Simplified theory](DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md) · [Full theory](DOCS/MATHEMATICAL_THEORY.md) · [Formula comparison](DOCS/FORMULA_COMPARISON.md)

---

## Documentation in this repo

| Resource | Description |
|----------|-------------|
| [DOCS/README.md](DOCS/README.md) | Master documentation index |
| [DOCS/EXAMPLES.md](DOCS/EXAMPLES.md) | Cross-platform examples |
| [DOCS/BASE_ORIENTATION_GUIDE.md](DOCS/BASE_ORIENTATION_GUIDE.md) | Base orientation / rotation |
| [DOCS/APPLICABILITY_OF_APPDIMENS.md](DOCS/APPLICABILITY_OF_APPDIMENS.md) | When to use or avoid AppDimens |
| [LANG/](LANG/README.md) | Portuguese and Spanish mirrors |
| [DOCS/html/](DOCS/html/) | Interactive scaling comparison HTML demos |

---

## Contributing, security, license

- [CONTRIBUTING.md](CONTRIBUTING.md) — includes submodule workflow  
- [SECURITY.md](SECURITY.md)  
- [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md)  
- [CHANGELOG.md](CHANGELOG.md) — high-level history; each submodule may have its own changelog  
- [LICENSE](LICENSE) — Apache License 2.0  

Issues for **this hub** (docs structure, links, translations): [appdimens issues](https://github.com/bodenberg/appdimens/issues). For **library bugs**, prefer the **issue tracker of the relevant submodule** on GitHub.

---

## Author

**Jean Bodenberg** — [@bodenberg](https://github.com/bodenberg) · [appdimens-project.web.app](https://appdimens-project.web.app/)

---

<div align="center">

**Made for developers worldwide**

[Documentation](DOCS/README.md) · [Examples](DOCS/EXAMPLES.md) · [Technical guide](DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

</div>
