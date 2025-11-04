<div align="center">
   <img src="../../IMAGES/image_sample_devices.png" alt="AppDimens - Design Responsivo em Todos os Dispositivos" height="300"/>
<h1>📐 AppDimens</h1>
<p><strong>Dimensões Responsivas Inteligentes para Qualquer Tela</strong></p>
   
[![Versão](https://img.shields.io/badge/versão-2.0.0-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![Licença](https://img.shields.io/badge/licença-Apache%202.0-green.svg)](../../LICENSE)
[![Plataforma](https://img.shields.io/badge/plataforma-Android%20%7C%20iOS%20%7C%20Flutter%20%7C%20RN%20%7C%20Web-orange.svg)](https://github.com/bodenberg/appdimens)
[![Estratégias](https://img.shields.io/badge/estratégias-13-orange.svg)]()

[📚 Documentação](../../DOCS/README.md) | [⚡ Referência Rápida](../../DOCS/DOCS_QUICK_REFERENCE.md) | [🔬 Detalhes Técnicos](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

> **Idiomas:** [English](../../README.md) | Português (BR) | [Español](../es/README.md)
</div>

---

## 🆕 Novidades na Versão 2.0

**🎯 13 Estratégias de Escalonamento** (eram apenas 2!)
- **BALANCED** ⭐ Nova estratégia primária recomendada - híbrida linear-logarítmica
- **DEFAULT** (antiga Fixed) - logarítmica com compensação de AR (recomendação secundária)
- **PERCENTAGE** (antiga Dynamic) - escalonamento proporcional
- **LOGARITHMIC** - psicofísica pura de Weber-Fechner
- **POWER** - Lei de Potência de Stevens (configurável)
- **FLUID** - Similar ao CSS clamp com breakpoints
- Mais 7: INTERPOLATED, DIAGONAL, PERIMETER, FIT, FILL, AUTOSIZE 🆕, NONE

**🧠 Sistema de Inferência Inteligente**
- Seleção automática de estratégia baseada no tipo de elemento
- 18 tipos de elemento (BUTTON, TEXT, ICON, CONTAINER, etc.)
- 8 categorias de dispositivo (PHONE_SMALL até TV)
- Sistema de decisão baseado em pesos

**⚡ Melhoria de Performance de 5x**
- Cache unificado lock-free (0.001µs)
- Tabela de lookup para ln() (10-20x mais rápido)
- Constantes pré-calculadas
- Algoritmos de busca binária (O(log n))

**♻️ Compatibilidade Total com Versões Anteriores**
- Extensões antigas `.fxdp`/`.dydp` ainda funcionam
- Caminho suave de migração para `.balanced()`, `.defaultDp`, `.percentageDp`

---

## ⚡ Visão Geral Rápida

**AppDimens** faz seus elementos de UI escalonarem perfeitamente em todos os dispositivos - de telefones a tablets, TVs, relógios e navegadores web.

Em vez de tamanhos fixos que parecem minúsculos em tablets ou enormes em relógios, AppDimens usa **escalonamento perceptual** baseado em pesquisa psicofísica (Weber-Fechner, Stevens) que adapta inteligentemente ao tamanho da tela, proporção e tipo de dispositivo.

### Por que AppDimens 2.0?

```
❌ Sem AppDimens:
   Telefone (360dp): Botão = 48dp (13% da tela) ✅ Bom
   Tablet (720dp): Botão = 48dp (7% da tela)  ❌ Muito pequeno!

❌ Com Escalonamento Linear (SDP):
   Telefone (360dp): Botão = 58dp (16% da tela) ✅ OK
   Tablet (720dp): Botão = 115dp (16% da tela) ❌ Muito grande!

✅ Com AppDimens BALANCED ⭐:
   Telefone (360dp): Botão = 58dp (16% da tela) ✅ Perfeito
   Tablet (720dp): Botão = 70dp (10% da tela) ✅ Perfeito!
```

### Benefícios Principais

- ✅ **Proporções perfeitas** em qualquer tamanho de tela
- ✅ **Funciona em todo lugar**: Android, iOS, Flutter, React Native, Web
- ✅ **API simples**: `.balanced()`, `.defaultDp`, `.percentageDp`
- ✅ **Cientificamente comprovado**: Baseado em pesquisa psicofísica (Weber-Fechner, Stevens)
- ✅ **Melhor performance**: 5x mais rápido com cache lock-free e otimizações
- ✅ **13 estratégias**: Do simples ao avançado, cobrindo todos os casos de uso
- ✅ **Inferência Inteligente**: Seleção automática de estratégia para 18 tipos de elemento
- ✅ **Unidades físicas**: Medidas do mundo real (mm, cm, polegadas) em todas as plataformas
- ✅ **Desenvolvimento de jogos**: Módulos especializados para Android (C++/NDK) e iOS (Metal)
- ✅ **AutoSize** 🆕: Auto-dimensionamento com consciência de container como TextView autoSizeText

---

## 🚀 Instalação

### Android

```kotlin
dependencies {
    implementation("io.github.bodenberg:appdimens-dynamic:2.0.1")
    implementation("io.github.bodenberg:appdimens-sdps:2.0.1")
    implementation("io.github.bodenberg:appdimens-ssps:2.0.1")
    implementation("io.github.bodenberg:appdimens-all:2.0.1")
    implementation("io.github.bodenberg:appdimens-games:2.0.1")
}
```

### iOS

```ruby
pod 'AppDimens', '~> 2.0.0'
```

### Flutter

```yaml
dependencies:
  appdimens: ^2.0.0
```

### React Native

```bash
npm install appdimens-react-native@2.0.0
```

### Web

```bash
npm install webdimens@2.0.0
```

**📖 [Guia Completo de Instalação](../../DOCS/README.md#-quick-start)**

---

## 💡 Uso Básico

### Android (Jetpack Compose)

```kotlin
@Composable
fun MeuCard() {
    Card(
        modifier = Modifier
            .width(300.balanced().dp)      // ✨ BALANCED (RECOMENDADO) ⭐
            .padding(16.balanced().dp)
    ) {
        Text(
            text = "Olá Mundo",
            fontSize = 18.balanced().sp
        )
    }
}
```

### iOS (SwiftUI)

```swift
struct MeuCard: View {
    var body: some View {
        VStack {
            Text("Olá Mundo")
                .font(.system(size: AppDimens.shared.balanced(18).toPoints()))
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
        .frame(width: AppDimens.shared.balanced(300).toPoints())
    }
}
```

### Flutter

```dart
Widget build(BuildContext context) {
  return Container(
    width: AppDimens.balanced(300).calculate(context),
    padding: EdgeInsets.all(AppDimens.balanced(16).calculate(context)),
    child: Text(
      'Olá Mundo',
      style: TextStyle(fontSize: AppDimens.balanced(18).calculate(context)),
    ),
  );
}
```

### React Native

{% raw %}
```jsx
function MeuCard() {
  const { balanced } = useAppDimens();
  
  return (
    <View style={{ width: balanced(300), padding: balanced(16) }}>
      <Text style={{ fontSize: balanced(18) }}>
        Olá Mundo
      </Text>
    </View>
  );
}
```
{% endraw %}

### Web (React)

{% raw %}
```jsx
import { useWebDimens } from 'webdimens/react';

function MeuCard() {
  const { balanced } = useWebDimens();
  
  return (
    <div style={{ width: balanced(300), padding: balanced(16) }}>
      <h2 style={{ fontSize: balanced(18) }}>Olá Mundo</h2>
    </div>
  );
}
```
{% endraw %}

---

## 🎯 Estratégias de Escalonamento

| Estratégia | Quando Usar | Exemplo | Disponibilidade |
|------------|-------------|---------|-----------------|
| **BALANCED** ⭐ **RECOMENDADO** | 95% dos casos - apps multi-dispositivo (telefones, tablets, TVs) | `48.balanced().dp` | Todas as plataformas |
| **DEFAULT** (Secundário) | Apps focados em telefones, ícones, compatibilidade | `48.defaultDp` | Todas as plataformas |
| **PERCENTAGE** | Containers grandes, grids de largura total, elementos proporcionais | `100.percentageDp` | Todas as plataformas |
| **LOGARITHMIC** | Apps de TV, controle máximo em telas grandes | `48.logarithmic()` | Todas as plataformas |
| **POWER** | Uso geral, configurável com expoente | `48.power(0.75)` | Todas as plataformas |
| **FLUID** 🌊 | Tipografia, espaçamento com transições suaves min/max | `fluid(16, 24)` | Todas as plataformas |

**📖 [Entendendo as Estratégias de Escalonamento](../../DOCS/MATHEMATICAL_THEORY_SIMPLIFIED.md)**

---

## 🏆 Por que AppDimens é #1

AppDimens foi cientificamente comparado contra 7 outras abordagens de escalonamento:

```
🥇 #1 AppDimens BALANCED: 93/100 ⭐⭐⭐⭐⭐ (Recomendação primária)
🥈 #2 AppDimens LOGARITHMIC: 88/100 ⭐⭐⭐⭐⭐ (TV/Tablets grandes)
🥉 #3 AppDimens POWER: 86/100 ⭐⭐⭐⭐
   #4 AppDimens DEFAULT: 82/100 ⭐⭐⭐⭐ (Focado em telefones)
   #5 SDP/SSP: 65/100
   #6 CSS vw/vh: 58/100
```

### O Que Torna Melhor?

- ✅ **Estratégia BALANCED**: Híbrida linear-logarítmica (40% de redução de oversizing)
- ✅ **Modelos perceptuais**: Baseado em psicofísica (Weber-Fechner, Stevens)
- ✅ **13 estratégias**: Biblioteca mais abrangente
- ✅ **Inferência Inteligente**: Seleção automática de estratégia
- ✅ **5x mais rápido**: Cache lock-free e otimizações
- ✅ **Compensação de aspect ratio**: Única biblioteca com ajuste de AR (estratégia DEFAULT)

**📊 [Ver Comparação Completa](FORMULA_COMPARISON.md)**

---

## 📚 Documentação

### Primeiros Passos

1. **[Referência Rápida](DOCS_QUICK_REFERENCE.md)** ⚡ Encontre qualquer coisa em segundos
2. **[Guia Simplificado](MATHEMATICAL_THEORY_SIMPLIFIED.md)** 📖 Entenda em 15 minutos
3. **[Exemplos](../../DOCS/EXAMPLES.md)** 💻 Código pronto para usar

### Documentação Técnica

4. **[Guia Técnico Completo](COMPREHENSIVE_TECHNICAL_GUIDE.md)** 🔬 Tudo em um lugar (2h de leitura)
5. **[Comparação de Fórmulas](FORMULA_COMPARISON.md)** 📊 Análise científica & rankings
6. **[Teoria Matemática](MATHEMATICAL_THEORY.md)** 📐 Fundação matemática formal

### Guias por Plataforma

- 🤖 [Guia Android](Android/README.md)
- 🍎 [Guia iOS](iOS/README.md)
- 🎯 [Guia Flutter](Flutter/README.md)
- ⚛️ [Guia React Native](../../ReactNative/README.md)
- 🌐 [Guia Web](Web/README.md)

**📚 [Índice Completo da Documentação](../../DOCS/README.md)**

---

## 🤝 Contribuindo

Contribuições são bem-vindas!

- 🐛 [Reportar bugs](https://github.com/bodenberg/appdimens/issues)
- 💡 [Sugerir funcionalidades](https://github.com/bodenberg/appdimens/discussions)
- 📝 Melhorar documentação
- ⭐ Dar estrela neste repositório!

**📖 [Guia de Contribuição](CONTRIBUTING.md)**

---

## 📄 Licença

Apache License 2.0 - veja o arquivo [LICENSE](../../LICENSE)

---

## 👨‍💻 Autor

**Jean Bodenberg**
- GitHub: [@bodenberg](https://github.com/bodenberg)
- Website: [appdimens-project.web.app](https://appdimens-project.web.app/)

---

<div align="center">

**Feito com ❤️ para desenvolvedores no mundo todo**

[Documentação](../../DOCS/README.md) • [Exemplos](../../DOCS/EXAMPLES.md) • [Guia Técnico](../../DOCS/COMPREHENSIVE_TECHNICAL_GUIDE.md)

</div>
