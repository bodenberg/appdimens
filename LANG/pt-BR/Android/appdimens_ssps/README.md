---
layout: default
---

<div align="center">
    <h1>📐 AppDimens SSP</h1>
    <p><strong>Escalonamento Dinâmico de Texto com Regras Condicionais</strong></p>
    <p>SSP (Scaled SP) com regras por prioridade para tipografia responsiva em Compose e XML.</p>

[![Versão](https://img.shields.io/badge/version-1.1.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licença](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> **Idiomas:** [English](../../../../Android/appdimens_ssps/README.md) | Português (BR) | [Español](../../es/Android/appdimens_ssps/README.md)

---

## 🎯 Visão Geral
- Extensões de texto: `.ssp`, `.hsp`, `.wsp` (respeitam acessibilidade) e `.sem/.hem/.wem` (ignoram acessibilidade)
- Regras condicionais por prioridade (UiMode + Qualificadores)

## 🚀 Instalação
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-ssps:1.1.0") }
```

## 🎨 Exemplos (Compose)
```kotlin
Text("Título", fontSize = 24.ssp)
Text("Legenda UI", fontSize = 12.sem)
```

## 📄 XML
```xml
<TextView android:textSize="@dimen/_16ssp" />
```

## 📚 Referências
- Documentação: `Android/DOCS/`
- Licença: `LICENSE`
