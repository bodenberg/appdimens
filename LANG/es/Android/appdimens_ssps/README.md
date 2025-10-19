<div align="center">
    <h1>📐 AppDimens SSP</h1>
    <p><strong>Escalado Dinámico de Texto con Reglas Condicionales</strong></p>
    <p>SSP (Scaled SP) con reglas por prioridad para tipografía responsiva en Compose y XML.</p>

[![Versión](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licencia](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> Idiomas: [English](../../../../Android/appdimens_ssps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_ssps/README.md) | [हिन्दी](../../hi/Android/appdimens_ssps/README.md) | [Русский](../../ru/Android/appdimens_ssps/README.md) | [中文](../../zh/Android/appdimens_ssps/README.md) | [日本語](../../ja/Android/appdimens_ssps/README.md)

---

## 🎯 Descripción General
- Extensiones de texto: `.ssp`, `.hsp`, `.wsp` (respetan accesibilidad) y `.sem/.hem/.wem` (ignoran accesibilidad)
- Reglas condicionales por prioridad (UiMode + Calificadores)

## 🚀 Instalación
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-ssps:1.0.5") }
```

## 🎨 Ejemplos (Compose)
```kotlin
Text("Título", fontSize = 24.ssp)
Text("Etiqueta UI", fontSize = 12.sem)
```

## 📄 XML
```xml
<TextView android:textSize="@dimen/_16ssp" />
```

## 📚 Referencias
- Documentación: `Android/DOCS/`
- Licencia: `LICENSE`
