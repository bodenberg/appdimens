# 📐 AppDimens para Android

**Dimensões Responsivas Inteligentes para Android**  
*Versão: 2.0.1*

> **Idiomas:** [English](../../../appdimens-dynamic/README.md) | Português (BR) | [Español](../../es/Android/README.md)

---

## 🚀 Instalação

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:2.0.1")
    implementation("io.github.bodenberg:appdimens-all:2.0.1")
}
```

---

## ⚡ Início Rápido

```kotlin
@Composable
fun MeuCard() {
    Card(
        modifier = Modifier
            .width(300.wdp)      // ⭐ BALANCED (RECOMENDADO)
            .padding(16.sdp)
    ) {
        Text("Olá Mundo", fontSize = 18.ssp)
    }
}
```

---

## 🎯 Estratégias

- **BALANCED** ⭐ (primária) - Apps multi-dispositivo
- **DEFAULT** (secundária) - Apps de telefone
- **PERCENTAGE** - Containers grandes
- E mais 10 estratégias

---

## 📚 Documentação

- [Guia Principal](../../../appdimens-dynamic/README.md)
- [Documentação Completa](../../../DOCS/README.md)
- [Teoria Matemática](../MATHEMATICAL_THEORY.md)

---

**Licença:** Apache 2.0
