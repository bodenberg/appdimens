<div align="center">
    <h1>📐 AppDimens SDP</h1>
    <p><strong>Escalonamento Dinâmico com Regras Condicionais</strong></p>
    <p>SDP (Scaled DP) com regras condicionais por prioridade para layouts responsivos (Compose e XML).</p>

[![Versão](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licença](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![Plataforma](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> Idiomas: [English](../../../../Android/appdimens_sdps/README.md) | [Español](../../es/Android/appdimens_sdps/README.md) | [हिन्दी](../../hi/Android/appdimens_sdps/README.md) | [Русский](../../ru/Android/appdimens_sdps/README.md) | [中文](../../zh/Android/appdimens_sdps/README.md) | [日本語](../../ja/Android/appdimens_sdps/README.md)

---

## 🎯 Visão Geral

- Escalonamento direto: `.sdp`, `.hdp`, `.wdp` (Compose e XML)
- Texto: ver módulo SSP; aqui foco em dimensões
- Regras condicionais por prioridade (UiMode + Qualificadores)
- Zero overhead quando via recursos pré-calculados

## 🚀 Instalação
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-sdps:1.0.5") }
```

## 🎨 Exemplos (Compose)
```kotlin
Column(Modifier.padding(16.sdp)) {
    Text("Texto", fontSize = 18.ssp)
    Spacer(Modifier.height(18.sdp).width(100.wdp))
    Card(Modifier.size(120.sdp).padding(8.hdp)) { /* ... */ }
}
```

### Regras Condicionais
```kotlin
val boxSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp) // P1
    .screen(UiModeType.CAR, 120.dp)                                // P2
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)                     // P3
```

## 📄 XML
```xml
<TextView
    android:layout_width="@dimen/_100sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp" />
```

## 📐 Qualificadores
- SDP: menor largura (sw)
- WDP: largura (w)
- HDP: altura (h)

## ⚡ Performance
- Extensões diretas: zero overhead (recursos)
- Regras: ~0.001ms, cache por configuração

## 📚 Referências
- Documentação: `Android/DOCS/`
- Licença: `LICENSE`
