---
layout: default
---

<div align="center">
    <h1>📐 AppDimens SDP</h1>
    <p><strong>Escalado Dinámico con Reglas Condicionales</strong></p>
    <p>SDP (Scaled DP) con reglas por prioridad para layouts responsivos (Compose y XML).</p>

[![Versión](https://img.shields.io/badge/version-1.1.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> **Idiomas:** [English](../../../../appdimens-sdps/README.md) | [Português (BR)](../../../pt-BR/Android/appdimens_sdps/README.md) | Español

---

## 🎯 Descripción General
- Extensiones directas: `.sdp`, `.hdp`, `.wdp`
- Reglas condicionales por prioridad (UiMode + Qualificadores)
- Cero overhead cuando se usan recursos pre-calculados

## 🚀 Instalación
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-sdps:1.1.0") }
```

## 🎨 Ejemplos (Compose)
```kotlin
Column(Modifier.padding(16.sdp)) {
    Text("Texto", fontSize = 18.ssp)
    Spacer(Modifier.height(18.sdp).width(100.wdp))
    Card(Modifier.size(120.sdp).padding(8.hdp)) { }
}
```

### Reglas Condicionales
```kotlin
val boxSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

## 📄 XML
```xml
<TextView
    android:layout_width="@dimen/_100sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp" />
```

## 📐 Calificadores
- SDP: menor ancho (sw)
- WDP: ancho (w)
- HDP: alto (h)

## ⚡ Rendimiento
- Extensiones: cero overhead
- Reglas: ~0.001ms (cache por configuración)

## 📚 Referencias
- Documentación: `Android/DOCS/`
- Licencia: `LICENSE`
