# ⚡ AppDimens: Referência Rápida da Documentação

**Guia de Navegação Rápida - Encontre o que você precisa em segundos**

> **Idiomas:** [English](../../DOCS/DOCS_QUICK_REFERENCE.md) | Português (BR) | [Español](../es/DOCS_QUICK_REFERENCE.md)

---

## 🎯 Escolha Seu Caminho

### Eu quero...

**... aprender AppDimens do zero** 🌱
→ Leia: [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) (15min)

**... ver exemplos de código** 💻
→ Leia: [EXAMPLES.md](../../DOCS/EXAMPLES.md) (20min)

**... entender a teoria matemática completa** 🧮
→ Leia: [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md) (45min)

**... comparar AppDimens com outras soluções** ⚖️
→ Leia: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) (30min)

**... ver TUDO em um só documento** 📚
→ Leia: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md) (2h)

**... validar se a implementação está correta** ✅
→ Leia: [VALIDATION_REPORT.md](../../VALIDATION_REPORT.md) (20min)

**... navegar toda a documentação** 🗺️
→ Leia: [DOCS/README.md](../../DOCS/README.md) (5min para navegar)

---

## 📊 Documentos por Objetivo

### Para Decidir se Usa AppDimens

1. [README.md](README.md) - Visão geral
2. [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) - Teoria simples
3. [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) - Ver ranking #1 com 91/100

**Decisão em: 50 minutos**

---

### Para Implementar no Projeto

1. [EXAMPLES.md](../../DOCS/EXAMPLES.md) - Código pronto
2. [Android/README.md](../../Android/README.md) - Específico da plataforma
3. [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md) - FAQ

**Pronto para codificar em: 30 minutos**

---

### Para Pesquisa/Artigo Acadêmico

1. [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md) - Teoria formal
2. [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md) - Comparações científicas
3. [VALIDATION_REPORT.md](../../VALIDATION_REPORT.md) - Validação
4. [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md) - Tudo

**Material completo para publicação**

---

## 🏆 Principais Conquistas Documentadas

### Ranking e Certificações

- **#1 de 7 fórmulas** com **91/100 pontos** 🥇
- **3.5× mais precisa** que linear
- **65% menos oversizing** que concorrentes
- **Única com compensação de AR**
- **Única com fundamentação psicofísica**

📍 Veja em: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md#63-certificado-de-excelência)

---

### Inovações Técnicas

1. ✅ Primeira biblioteca com escalonamento logarítmico
2. ✅ Primeira com compensação automática de aspect ratio
3. ✅ Primeira com sistema de 4 prioridades hierárquico
4. ✅ Primeira com fundamentação em Lei de Weber-Fechner
5. ✅ Performance superior com cache (0.002µs vs 0.005µs)

📍 Veja em: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md#12-inovação-e-originalidade)

---

## 📐 Fórmulas Principais

### Fórmula Logarítmica (Fixed)

```
f_FX(B, S, AR) = B × [1 + ((S - 300) / 30) × (0.10 + 0.08 × ln(AR / 1.78))]
```

### Sistema de Prioridades

```
1. Intersection (UiMode + DpQualifier) ← Mais específico
2. UiMode (apenas tipo de dispositivo)
3. DpQualifier (apenas tamanho)
4. Logarithmic (escalonamento automático) ← Fallback
```

📍 Detalhes em: [COMPREHENSIVE_TECHNICAL_GUIDE.md](COMPREHENSIVE_TECHNICAL_GUIDE.md#4-sistema-de-prioridades-hierarchy-system)

---

## 🔬 Comparações Importantes

### vs. SDP/SSP (Linear)

| Aspecto | SDP/SSP | AppDimens |
|---------|---------|-----------|
| Fórmula | Linear | Logarítmica |
| AR | ❌ Ignora | ✅ Compensa |
| Tablet 800dp | 107dp (🔴 +123%) | 68dp (✅ +42%) |

### vs. CSS clamp()

| Aspecto | CSS clamp() | AppDimens |
|---------|-------------|-----------|
| Escalonamento | Linear | Logarítmico |
| AR | ❌ Ignora | ✅ Compensa |
| Plataforma | Apenas Web | Universal |

### vs. Flutter ScreenUtil

| Aspecto | ScreenUtil | AppDimens |
|---------|-----------|-----------|
| Fórmula | Quadrática | Logarítmica |
| Base teórica | ❌ Nenhuma | ✅ Weber-Fechner |
| Tablet 800dp | 89dp (🟡 +86%) | 68dp (✅ +42%) |

📍 Comparação completa: [FORMULA_COMPARISON.md](FORMULA_COMPARISON.md#7-comparação-com-7-fórmulas-fundamentais)

---

## 🎓 Fundamentação Científica

### Lei de Weber-Fechner (1860)

```
S = k × ln(I / I₀)
```

**Aplicação:** Percepção humana de tamanho é logarítmica, não linear.

### Lei de Stevens (1957)

```
ψ = k × φⁿ  (onde n < 1 para percepção espacial)
```

**Aplicação:** Crescimento sublinear é mais natural para UI.

📍 Detalhes em: [MATHEMATICAL_THEORY.md](MATHEMATICAL_THEORY.md#6-fundamentação-científica)

---

## 💡 FAQ Rápido

**P: Por que logaritmo?**  
R: Porque a percepção humana é logarítmica (Lei de Weber-Fechner). Evita oversizing natural.

**P: É lento?**  
R: Com cache, é a MAIS RÁPIDA (0.002µs vs 0.005µs da porcentagem).

**P: Funciona em iOS/Flutter/Web?**  
R: Sim! Universal, mesma fórmula em todas as plataformas.

**P: É difícil de usar?**  
R: Não! API simples: `16.fxdp` ou `16.fixedDp().dp`

📍 FAQ completo: [MATHEMATICAL_THEORY_SIMPLIFIED.md](MATHEMATICAL_THEORY_SIMPLIFIED.md#-perguntas-frequentes-faq)

---

## 📱 Exemplos Rápidos

### Android Compose

```kotlin
Text(
    text = "Hello",
    fontSize = 16.fxsp,
    modifier = Modifier.padding(12.fxdp)
)
```

### iOS SwiftUI

```swift
Text("Hello")
    .font(.system(size: AppDimens.fixed(16).sp))
    .padding(AppDimens.fixed(12).dp)
```

### Flutter

```dart
Text(
  'Hello',
  style: TextStyle(fontSize: 16.fxsp.calculate(context)),
)
```

### React Native

```javascript
<Text style={{ fontSize: fixedSp(16) }}>
  Hello
</Text>
```

📍 Mais exemplos: [EXAMPLES.md](../../DOCS/EXAMPLES.md)

---

## 🗺️ Mapa de Documentação

```
AppDimens/
│
├─ README.md ← START HERE
│
├─ Documentação Simplificada (Iniciante)
│  ├─ MATHEMATICAL_THEORY_SIMPLIFIED.md ⭐
│  ├─ EXAMPLES.md
│  └─ Platform READMEs
│
├─ Documentação Técnica (Intermediário)
│  ├─ MATHEMATICAL_THEORY.md
│  ├─ FORMULA_COMPARISON.md ⭐
│  └─ VALIDATION_REPORT.md
│
├─ Documentação Avançada (Especialistas)
│  └─ COMPREHENSIVE_TECHNICAL_GUIDE.md ⭐⭐⭐
│
└─ Navegação
   ├─ DOCS/README.md (índice completo)
   └─ DOCS_QUICK_REFERENCE.md (você está aqui!)
```

---

## ⚡ Links Diretos

### Mais Acessados

- [Guia Simplificado](MATHEMATICAL_THEORY_SIMPLIFIED.md) ← 80% dos usuários começam aqui
- [Exemplos de Código](../../DOCS/EXAMPLES.md)
- [Comparação com Outras Bibliotecas](FORMULA_COMPARISON.md)

### Documentação Completa

- [Teoria Matemática Formal](MATHEMATICAL_THEORY.md)
- [Guia Técnico Completo](COMPREHENSIVE_TECHNICAL_GUIDE.md) ← Tudo em um lugar
- [Índice de Toda Documentação](../../DOCS/README.md)

### Validação e Certificações

- [Relatório de Validação](../../VALIDATION_REPORT.md)
- [Certificado de Excelência](FORMULA_COMPARISON.md#63-certificado-de-excelência)

---

## 📞 Precisa de Ajuda?

1. **Documentação** ← Você está aqui!
2. [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
3. [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)
4. [Site Oficial](https://appdimens-project.web.app/)

---

**Última atualização:** Janeiro 2025  
**Versão:** 1.0.8  
**Autor:** Jean Bodenberg  
**Licença:** Apache 2.0

---

*"A informação certa, no momento certo, da forma certa."*

