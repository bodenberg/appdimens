---
layout: default
title: "Prompt de IA para la biblioteca AppDimens (Android)"
---

# Prompt de IA para la biblioteca AppDimens (Android)

> **Idiomas:** [English](../../Android/PROMPT_ANDROID.md) | [Português (BR)](../pt-BR/PROMPT_ANDROID.md) | Español

Eres una IA experta en Android. Tu tarea es comprender y usar AppDimens, un sistema de gestión de dimensiones para Views y Jetpack Compose.

## 1. Instalación
Incluye los módulos necesarios (`appdimens-dynamic`, `appdimens-sdps`, `appdimens-ssps`, `appdimens-all`) desde MavenCentral/JitPack (`io.github.bodenberg:*:1.0.8`).

## 2. Inicialización
No requiere inicialización manual.

## 3. Conceptos Clave
- Escalado responsivo de Dp/Sp/Px.
- Módulos: base, dinámico, SDP/SSP y app de ejemplo.

### Modelos
- Fixed (FX): ajuste logarítmico, sutil. UI (botones, paddings, iconos, fuentes).
- Dynamic (DY): proporcional. Contenedores y layouts fluidos.

## 4. Uso
### Compose
Extensiones: `.fxdp`, `.fxsp`, `.dydp`, `.dysp`, etc. Porcentajes: `AppDimens.dynamicPercentageDp(0.5f)`.

### Views/XML
Kotlin: `AppDimens.fixed(16f).dp`, `AppDimens.dynamic(100f).dp`. Data Binding: ver adaptadores en `app`.

## 5. Avanzado
`DpQualifier`, `UiModeType`, unidades físicas (mm/cm/inch), utilidades de layout (`calculateAvailableItemCount`).

## 6. Rendimiento
Cálculos en caché, impacto mínimo.

## 7. Pruebas
Funciona en UI tests; en unit tests, se pueden simular valores deterministas.
