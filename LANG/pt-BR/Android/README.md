# 📐 AppDimens para Android

**Dimensões Responsivas Inteligentes para Android**  
*Versão: 2.0.0*

> **Idiomas:** [English](../../../Android/README.md) | Português (BR) | [Español](../../es/Android/README.md)

---

## 🚀 Instalação

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:2.0.0")
    implementation("io.github.bodenberg:appdimens-all:2.0.0")
}
```

---

## ⚡ Início Rápido

```kotlin
@Composable
fun MeuCard() {
    Card(
        modifier = Modifier
            .width(300.balanced().dp)      // ⭐ BALANCED (RECOMENDADO)
            .padding(16.balanced().dp)
    ) {
        Text("Olá Mundo", fontSize = 18.balanced().sp)
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

- [Guia Principal](../../../Android/README.md)
- [Documentação Completa](../../../DOCS/README.md)
- [Teoria Matemática](../MATHEMATICAL_THEORY.md)

---

**Licença:** Apache 2.0
