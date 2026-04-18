# Platform API map (concept → real bindings)

This meta-repository explains **scaling strategies** with shared names (BALANCED, DEFAULT, PERCENTAGE, …). **Public syntax differs by package**: Jetpack Compose code for Android lives in **`appdimens-dynamic` 3.x** and does **not** use the legacy unified chain `.balanced().dp` / `.defaultDp` from older central docs.

For the full editorial rule, see [DOCS/README.md](README.md) and the note at the top of [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md).

| Concept (theory / strategy name) | Android — `appdimens-dynamic` 3.x (Compose) | iOS — `AppDimens` | Web — `webdimens` | Flutter — `appdimens` | React Native — `appdimens-react-native` |
|----------------------------------|-----------------------------------------------|-------------------|-------------------|------------------------|-------------------------------------------|
| **Primary “balanced” hybrid (linear → log past ~480dp on axis)** | **Auto** axis tokens: `asdp` / `ahdp` / `awdp`, `assp`, … (`import com.appdimens.dynamic.compose.auto.*`). See submodule [auto.md](../appdimens-dynamic/DOCUMENTATION/auto.md), `DimenAuto.kt`, `DimenAutoDpExtensions.kt`. | `AppDimens.shared.balanced(_).toPoints()` (see submodule examples / USAGE). | `useWebDimens()` → `balanced(_)` on `WebDimensBuilder` ([WebDimensBuilder.ts](../appdimens-web/src/core/WebDimensBuilder.ts)). | `AppDimens.fixed(value)` or `value.fx` → `.calculate(context)` on builders from [appdimens.dart](../appdimens-flutter/lib/src/appdimens.dart) + [appdimens_extensions.dart](../appdimens-flutter/lib/src/appdimens_extensions.dart). | `useAppDimens()` → `balanced(_)` ([AppDimensBuilder.ts](../appdimens-react-native/src/core/AppDimensBuilder.ts)). |
| **Scaled default (smallest-width–style `sdp` / width `wdp` / height `hdp`)** | `16.sdp`, `100.wdp`, `48.hdp`, `16.ssp`, … (`import com.appdimens.dynamic.compose.*`). [README quick start](../appdimens-dynamic/README.md). | Use the iOS builder APIs from the submodule (naming differs from Android tokens). | Strategy-specific helpers on the web builder. | `AppDimens.fixed` / `.fx` expresses “fixed” scaling path; pick strategy on the builder as in Flutter README. | Same family as web: builder + `balanced` / `defaultScaling`, etc. |
| **DEFAULT / phone-first (legacy doc name)** | Choose **scaled** or **auto** per screen; there is **no** `defaultDp` token in dynamic 3.x. For **logarithmic** curves use `compose.logarithmic` (e.g. `Number.logarithmicDp()` / `logarithmicSp()` chains — see submodule `DOCUMENTATION/`). | `AppDimens.shared.defaultScaling(_).toPoints()` | `defaultScaling(_)` | `AppDimens.fixed` / `.fx` (see fixed builder docs in submodule). | `defaultScaling(_)` |
| **PERCENTAGE / width-heavy sizing** | Width-biased tokens such as `300.wdp` or literals in **`compose.percent`** (see submodule). Old `300.wdp` is **not** the 3.x API. | `AppDimens.shared.percentage(_).toPoints()` | `percentage(_)` | `AppDimens.dynamic` / `.dy` for aggressive / proportional paths ([appdimens_dynamic.dart](../appdimens-flutter/lib/src/appdimens_dynamic.dart)). | `percentage(_)` |
| **XML-only scalable DP/SP (no Compose extensions)** | Use **`appdimens-sdps` / `appdimens-ssps`** (`@dimen/_*sdp`, `@dimen/_*ssp`) — separate artifacts from `appdimens-dynamic`. | — | — | — | — |
| **Games / NDK** | **`appdimens-games`** C++/Kotlin APIs ([appdimens_games README](../appdimens-games/appdimens_games/README.md)) — do not mix with Compose `sdp` examples. | — | — | — | — |
| **“Smart” / element inference** | **Not** exposed as `48.smart().forElement(...)` in `appdimens-dynamic` 3.x Kotlin sources; keep that pattern only in platforms where the submodule still documents it (iOS / Web / RN / Flutter prompts). On Android, pick an explicit strategy module. | `AppDimens.shared.smart(_).forElement(...)` | `smart(_).forElement(...)` | Check current Flutter submodule API before copying “smart” snippets from old meta-docs. | `smart(_).forElement(...)` |

**Sources of truth (submodules):**  
Android Compose: [appdimens-dynamic/README.md](../appdimens-dynamic/README.md), [DOCUMENTATION/README.md](../appdimens-dynamic/DOCUMENTATION/README.md).  
Flutter: [lib/src/appdimens.dart](../appdimens-flutter/lib/src/appdimens.dart).  
Web: [WebDimensBuilder.ts](../appdimens-web/src/core/WebDimensBuilder.ts).  
React Native: [AppDimensBuilder.ts](../appdimens-react-native/src/core/AppDimensBuilder.ts).  
iOS: examples under `appdimens-ios/Examples` and submodule README / USAGE.

[Back to documentation index](README.md)
