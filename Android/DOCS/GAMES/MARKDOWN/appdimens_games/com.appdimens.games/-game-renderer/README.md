---
layout: default
title: "GameRenderer"
category: games
permalink: /GAMES/MARKDOWN/appdimens_games/com.appdimens.games/-game-renderer/index.html
---

# GameRenderer

[androidJvm]
class [GameRenderer](README.md)(appDimensGames: [AppDimensGames](../-app-dimens-games/README.md)) : [GLSurfaceView.Renderer](https://developer.android.com/reference/kotlin/android/opengl/GLSurfaceView.Renderer.html)

EN Simple OpenGL renderer for demonstration purposes. PT Renderizador OpenGL simples para fins de demonstração.

## Constructors

| | |
|---|---|
| [GameRenderer](-game-renderer.md) | [androidJvm]<br>constructor(appDimensGames: [AppDimensGames](../-app-dimens-games/README.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/README.md) | [androidJvm]<br>object [Companion](-companion/README.md) |

## Functions

| Name | Summary |
|---|---|
| [onDrawFrame](on-draw-frame.md) | [androidJvm]<br>open override fun [onDrawFrame](on-draw-frame.md)(gl: [GL10](https://developer.android.com/reference/kotlin/javax/microedition/khronos/opengles/GL10.html)?) |
| [onSurfaceChanged](on-surface-changed.md) | [androidJvm]<br>open override fun [onSurfaceChanged](on-surface-changed.md)(gl: [GL10](https://developer.android.com/reference/kotlin/javax/microedition/khronos/opengles/GL10.html)?, width: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html), height: [Int](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-int/index.html)) |
| [onSurfaceCreated](on-surface-created.md) | [androidJvm]<br>open override fun [onSurfaceCreated](on-surface-created.md)(gl: [GL10](https://developer.android.com/reference/kotlin/javax/microedition/khronos/opengles/GL10.html)?, config: [EGLConfig](https://developer.android.com/reference/kotlin/javax/microedition/khronos/egl/EGLConfig.html)?) |