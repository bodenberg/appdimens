# 🧪 Custom strategies — your own scaling, on every platform

> **Status:** new module in every code library (Android, KMP, iOS, Flutter, React Native, Web, .NET, games), on each repository's `main` branch after its pull request is merged; not in a published release yet. The Android XML resource libraries (`appdimens-sdps` / `appdimens-ssps`) offer only the scaled strategy and have no custom module.

The 14 built-in strategies (`sdp`, `asdp`, `psdp`…) cover most apps. When yours needs **its own growth law** — a different design frame, a formula of your own, different behavior on tablets, TVs or watches — the **custom** module lets you define it once and use it like any other token. The math and the JSON format are identical on every platform, and a shared 540-case fixture checks that every library gives the same numbers.

## 1. Four ways, from simplest to most complete

| You want to… | Write | Notes |
|---|---|---|
| Write the **factor** yourself, in code | `CustomScaleStrategy { (width / 360f).coerceIn(0.85f, 1.80f) }` | runs once per window, then one multiply per value |
| Write the **final value** yourself, in code | `scalingFormula { base * pow(sw / 360f, 0.8f) }` | `base` is the value you pass (the 16 in `16.cdp`) |
| Keep the formula in **JSON / remote config** | `scalingFormula("base * pow(sw / 360, 0.8)")` · `16.cdp("…")` | same names, same numbers on every platform |
| **Rules per device**, bounds, font-scale policy, shared design system | `scalingStrategy("brand") { … }` or a strategies JSON file | the full configuration |

Inside the braces of the code forms, your IDE autocompletes every name you can use, and a typo is a compile error. The names are the same everywhere:

| Names | Meaning |
|---|---|
| `base` | the value being scaled (formula only) |
| `sw` `width` `height` `shortSide` `longSide` `diagonal` `perimeter` `aspectRatio` | window measures (dp / pt) |
| `ratio` | `sw / 300` — the ratio `sdp` uses (`base * ratio` = `sdp`) |
| `phone` `tablet` `desktop` `tv` `watch` `car` `vr` · `landscape` `portrait` `multiWindow` | device and window flags |
| `pow` `sqrt` `ln` `log10` `exp` `abs` `round` `min` `max` `clamp` `lerp` `smoothstep` | math |

## 2. The same strategy on each platform

| Platform | Factor in code | Formula in code | Use it |
|---|---|---|---|
| Android Compose / Kotlin (`appdimens-dynamic-custom`) | `CustomScaleStrategy { (width / 360f).coerceIn(0.85f, 1.8f) }` | `scalingFormula { base * pow(sw / 360f, 0.8f) }` | `16.cdp(s)` (Dp), `14.csp(s)` · Views `16.cdp(context, s)` (px) |
| Kotlin Multiplatform (`appdimens-kmp-custom`) | same as Android | same as Android | Compose `16.cdp(s)` · code `16.cdp(appContext, s)` (px) |
| Apple (`AppDimensCustom` product) | `CustomScaleStrategy { s in s.clamp(s.width / 360, 0.85, 1.8) }` | `scalingFormula { $0.base * $0.pow($0.sw / 360, 0.8) }` | `16.cdp(s)` · text: `try 16.cdp("…")` · `@CustomDimension` |
| Flutter (`appdimens_custom`) | `customScaleStrategy((s) => s.clamp(s.width / 360, 0.85, 1.8))` | `scalingFormulaOf((s) => s.base * s.pow(s.sw / 360, 0.8))` | `16.cdpWith(s)` · text: `16.cdpFormula('…')` |
| React Native (`appdimens-react-native/custom`) | `customScaleStrategy(({width, clamp}) => clamp(width / 360, 0.85, 1.8))` | `scalingFormula(({base, sw, pow}) => base * pow(sw / 360, 0.8))` | `cdp(16, s)` · `useCustomStrategy` · `CustomStrategyProvider` |
| Web (`webdimens/custom`) | `customScaleStrategy(({ width, clamp }) => clamp(width / 360, 0.85, 1.8))` | `scalingFormula(({ base, sw, pow }) => base * pow(sw / 360, 0.8))` | `cdp(16, s)` · `cdp(16, 'base * …')` · CSS variables |
| .NET (`AppDimens.Net.Custom`) | `CustomScaleStrategy.Create(s => s.Clamp(s.Width / 360f, 0.85f, 1.8f))` | `ScalingStrategy.Formula(s => s.Base * s.Pow(s.Sw / 360f, 0.8f))` | `16f.Cdp(s)` · `16f.Cdp("…")` |
| Android games (`appdimens-games-custom`) | same as Android | same as Android | `64.cdp(s)` (px) · Compose `64.cdp(s)` |
| Unity / C# (games) | `CustomGames.CustomScaleStrategy(s => s.Clamp(s.Width / 360f, 0.85f, 1.8f))` | `CustomGames.Formula(s => s.Base * s.Pow(s.Sw / 360f, 0.8f))` | `64f.Cdp(s)` (dp) |

> Differences worth knowing: Dart has no overloading, so text formulas use `cdpFormula`; Swift text helpers `throw`; Apple falls back to a factor of 1 when a factor is not finite (the others throw); in Games the Kotlin short names return px and the C# ones dp.

## 3. One JSON for the whole design system

```json
{
  "version": 1,
  "default": "brand",
  "strategies": [
    { "id": "brand", "reference": { "width": 360, "height": 640 },
      "curve": { "type": "power", "exponent": 0.8 }, "clamp": { "min": 0.85, "max": 1.6 },
      "rules": [ { "when": { "device": ["tablet", "desktop"] },
                   "set": { "curve": { "type": "logarithmic", "k": 0.4 } } } ] },
    { "id": "hud", "formula": "base * pow(sw / 360, 0.8)" }
  ]
}
```

Load it with `ScalingStrategies.loadJson(…)` (`loadAsset` on Android, `LoadJson` on .NET, `loadScalingStrategies` on the Web…) and use the ids. Loading is strict and atomic: a malformed field fails with its JSON path and nothing is registered. Check a file before shipping it:

```bash
npx appdimens-skills kit custom check design/appdimens-strategies.json
npx appdimens-skills kit custom explain --formula "base * pow(sw / 360, 0.8)" --devices medium-phone,medium-tablet
```

Schema: [`custom-strategy.schema.json`](https://github.com/bodenberg/appdimens-skills/blob/main/schemas/custom-strategy.schema.json).

## 4. How it computes

```
ratio  = axis(window) / axis(reference)                       // e.g. sw / 300
factor = clamp( curve(ratio) × multiplier × AR , min, max )   // once per window
scaled = clampValue( base × factor )                          // the configured result
value  = formula ? formula(base, scaled, factor) : scaled     // per value, only if a formula is set
```

`CustomScaleStrategy` replaces the `factor` line. A formula (`scalingFormula { … }` or text) **replaces** the result: it receives the original `base` (the 16 in `16.cdp`) and returns the final value. It does not compound with the default scaling. `scaled` and `factor` are only there if you want to post-process the configured strategy, for example `max(scaled, base)`; a formula that does not read them ignores them. Screen-only strategies cost one multiply per value, like the built-ins; per-value formulas are cached per window where the platform has a cache. Details: [THEORY.md §5.12](THEORY.md#512-custom--user-defined-kernel).

## 5. Full documentation per library

| Library | Guides |
|---|---|
| Android (`appdimens-dynamic`) | [DOCUMENTATION/custom/](https://github.com/bodenberg/appdimens-dynamic/blob/main/DOCUMENTATION/custom/README.md) |
| Kotlin Multiplatform (`appdimens-kmp`) | [DOCUMENTATION/custom/](https://github.com/bodenberg/appdimens-kmp/blob/main/DOCUMENTATION/custom/README.md) |
| Apple (`appdimens-ios`) | [Documentation/strategies/custom/](https://github.com/bodenberg/appdimens-ios/blob/main/Documentation/strategies/custom/README.md) |
| Flutter (`appdimens-flutter`) | [DOCUMENTATION/custom/](https://github.com/bodenberg/appdimens-flutter/blob/main/DOCUMENTATION/custom/README.md) |
| React Native (`appdimens-react-native`) | [docs/custom/](https://github.com/bodenberg/appdimens-react-native/blob/main/docs/custom/README.md) |
| Web (`appdimens-web`) | [docs/custom/](https://github.com/bodenberg/appdimens-web/blob/main/docs/custom/README.md) · [pt-BR](https://github.com/bodenberg/appdimens-web/blob/main/docs/pt-BR/custom/README.md) |
| .NET (`appdimens-net-binding`) | [docs/AppDimens.Net.Custom/](https://github.com/bodenberg/appdimens-net-binding/blob/main/docs/AppDimens.Net.Custom/README.md) |
| Games — Kotlin and Unity C# (`appdimens-games`) | [DOCUMENTATION/strategies/custom/](https://github.com/bodenberg/appdimens-games/blob/main/DOCUMENTATION/strategies/custom/README.md) |
| AI agents (`appdimens-skills`) | [skills/appdimens-custom-strategies](https://github.com/bodenberg/appdimens-skills/tree/main/skills/appdimens-custom-strategies) |

Each set has the same guides: quick start, code formulas, text formulas, configuration, JSON format, API reference, performance, cookbook and troubleshooting.

## 6. Good habits

1. **Try the built-ins first.** Custom strategies are for when none passes visual QA.
2. **Declare each strategy once** (a top-level value, an object, or the JSON file), never inside a render or composable body.
3. **Keep code formulas pure** — no state, settings or time inside; results are memoized per window.
4. **Prefer a factor** when your math is "base × something about the screen": it costs one multiply.
5. **Use value bounds only for real limits** (tap targets, maximum text size).

[← Back to the documentation index](README.md)
