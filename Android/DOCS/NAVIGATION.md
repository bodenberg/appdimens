# 📑 Guia de Navegação - Documentação AppDimens Android

## 🎯 Visão Geral

Este documento serve como um **guia completo de navegação** para os **356 arquivos markdown** convertidos para Jekyll, organizados por módulo e categoria.

---

## 📚 Documentação Principal

### 🏠 Página Inicial
- **[README.md](README.md)** - Documentação principal do Android DOCS

### 📖 Guias de Conversão
- **[CONVERSAO_JEKYLL.md](CONVERSAO_JEKYLL.md)** (8.0K)
  - Detalhes completos da conversão
  - Estatísticas e métricas
  - Processo técnico utilizado
  - Deploy e configuração

- **[QUICKSTART_JEKYLL.md](QUICKSTART_JEKYLL.md)** (6.5K)
  - Guia rápido para começar
  - 3 comandos para iniciar
  - Comandos úteis
  - Troubleshooting

- **[EXEMPLOS_CONVERSAO.md](EXEMPLOS_CONVERSAO.md)** (8.2K)
  - Exemplos visuais antes/depois
  - Padrões de permalink
  - Estrutura de categorias
  - Validação e testes

---

## ⚙️ Arquivos de Configuração

### Jekyll
- **[_config.yml](_config.yml)** (4.0K)
  - Configuração principal do Jekyll
  - Definições de coleções
  - Defaults por categoria
  - Plugins e SEO

### Ruby/Bundler
- **[Gemfile](Gemfile)** (4.0K)
  - Dependências do Jekyll
  - Plugins necessários
  - Versões específicas

### Git
- **[.gitignore](.gitignore)** (4.0K)
  - Exclusões para Jekyll
  - Arquivos temporários
  - Builds e caches

---

## 📁 Estrutura de Módulos

### 🔷 DYNAMIC (165 arquivos)
```
DYNAMIC/MARKDOWN/
├── index.md
└── appdimens_dynamic/
    ├── com.appdimens.dynamic.code/
    │   ├── -app-dimens/
    │   ├── -app-dimens-adjustment-factors/
    │   ├── -app-dimens-dynamic/
    │   ├── -app-dimens-fixed/
    │   ├── -app-dimens-physical-units/
    │   └── -screen-adjustment-factors/
    └── com.appdimens.dynamic.compose/
        ├── -app-dimens/
        ├── -app-dimens-adjustment-factors/
        ├── -app-dimens-dynamic/
        ├── -app-dimens-fixed/
        └── -app-dimens-physical-units/
```

**Principais Recursos:**
- Dimensionamento dinâmico proporcional
- Dimensionamento fixo logarítmico
- Unidades físicas (cm, mm, inch)
- Suporte Jetpack Compose
- Fatores de ajuste de tela

### 🎮 GAMES (81 arquivos)
```
GAMES/MARKDOWN/
├── index.md
├── OVERVIEW.md
└── appdimens_games/
    └── com.appdimens.games/
        ├── -app-dimens-games/
        ├── -game-activity/
        ├── -game-dimension-type/
        ├── -game-rectangle/
        ├── -game-renderer/
        ├── -game-screen-config/
        ├── -game-screen-orientation/
        ├── -game-vector2-d/
        └── -game-viewport-mode/
```

**Principais Recursos:**
- Integração C++/NDK
- Performance otimizada para jogos
- Gestão de viewport
- OpenGL ES utilities
- Vector math 2D
- Configuração de tela para jogos

### 📚 LIBRARY (55 arquivos)
```
LIBRARY/MARKDOWN/
├── index.md
└── appdimens_library/
    └── com.appdimens.library/
        ├── -dp-qualifier/
        ├── -dp-qualifier-entry/
        ├── -screen-adjustment-factors/
        ├── -screen-type/
        ├── -ui-mode-qualifier-entry/
        ├── -ui-mode-type/
        └── -unit-type/
```

**Principais Recursos:**
- Tipos core da biblioteca
- Qualificadores de dimensão
- Tipos de tela e UI mode
- Enums de unidades
- Interfaces base

### 📏 SDPS (24 arquivos)
```
SDPS/MARKDOWN/
├── index.md
└── appdimens_sdps/
    ├── com.appdimens.sdps.code/
    │   └── -app-dimens-sdp/
    └── com.appdimens.sdps.compose/
        ├── -custom-dp-entry/
        └── -scaled/
```

**Principais Recursos:**
- Scalable Dimension Pixels
- Recursos pré-calculados
- Suporte Compose
- Customização por tela
- Performance zero overhead

### 📝 SSPS (30 arquivos)
```
SSPS/MARKDOWN/
├── index.md
└── appdimens_ssps/
    ├── com.appdimens.ssps.code/
    │   └── -app-dimens-ssp/
    └── com.appdimens.ssps.compose/
        ├── -custom-sp-entry/
        └── -scaled/
```

**Principais Recursos:**
- Scalable Text Pixels
- Tamanhos de texto responsivos
- Suporte Compose
- EM units
- Customização por dispositivo

---

## 🔍 Navegação Rápida

### Por Funcionalidade

#### Dimensionamento
- [Dynamic Scaling](DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.code/-app-dimens-dynamic/index.md)
- [Fixed Scaling](DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.code/-app-dimens-fixed/index.md)
- [SDP Resources](SDPS/MARKDOWN/appdimens_sdps/com.appdimens.sdps.compose/index.md)
- [SSP Resources](SSPS/MARKDOWN/appdimens_ssps/com.appdimens.ssps.compose/index.md)

#### Desenvolvimento de Jogos
- [AppDimensGames](GAMES/MARKDOWN/appdimens_games/com.appdimens.games/-app-dimens-games/index.md)
- [Game Vectors](GAMES/MARKDOWN/appdimens_games/com.appdimens.games/-game-vector2-d/index.md)
- [Game Renderer](GAMES/MARKDOWN/appdimens_games/com.appdimens.games/-game-renderer/index.md)
- [C++ Overview](GAMES/MARKDOWN/OVERVIEW.md)

#### Unidades Físicas
- [Physical Units](DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.code/-app-dimens-physical-units/index.md)
- [Conversions](DYNAMIC/MARKDOWN/appdimens_dynamic/com.appdimens.dynamic.compose/-app-dimens-physical-units/index.md)

#### Tipos e Interfaces
- [Screen Types](LIBRARY/MARKDOWN/appdimens_library/com.appdimens.library/-screen-type/index.md)
- [UI Mode Types](LIBRARY/MARKDOWN/appdimens_library/com.appdimens.library/-ui-mode-type/index.md)
- [Unit Types](LIBRARY/MARKDOWN/appdimens_library/com.appdimens.library/-unit-type/index.md)

---

## 🚀 Como Usar

### 1. Inicialização Rápida

```bash
# Instalar dependências
cd Android/DOCS
bundle install

# Iniciar servidor
bundle exec jekyll serve

# Acessar
open http://localhost:4000/Android/DOCS
```

### 2. Ler Documentação

Você pode:
- 📖 Ler diretamente no GitHub
- 🌐 Gerar site Jekyll localmente
- 🚀 Deploy em GitHub Pages
- 💻 Usar em IDE com preview Markdown

### 3. Buscar Conteúdo

```bash
# Buscar por termo
grep -r "termo" DYNAMIC/MARKDOWN/

# Buscar em arquivos específicos
find . -name "*dynamic*.md" -exec grep -l "toPx" {} \;

# Buscar por categoria
grep -l "category: games" **/*.md
```

---

## 📊 Estatísticas Completas

### Arquivos por Tipo

| Tipo | Quantidade | Descrição |
|------|------------|-----------|
| 📄 API Docs | 340 | Documentação de classes e funções |
| 📋 Index Files | 10 | Índices de pacotes |
| 📖 Overview | 5 | Visões gerais de módulos |
| 📚 README | 1 | Documentação principal |

### Distribuição de Conteúdo

| Categoria | Arquivos | % Total | Tamanho Aprox. |
|-----------|----------|---------|----------------|
| DYNAMIC | 165 | 46.3% | ~825 KB |
| GAMES | 81 | 22.8% | ~405 KB |
| LIBRARY | 55 | 15.4% | ~275 KB |
| SDPS | 24 | 6.7% | ~120 KB |
| SSPS | 30 | 8.4% | ~150 KB |
| ROOT | 1 | 0.3% | ~8 KB |
| **TOTAL** | **356** | **100%** | **~1.8 MB** |

### Front Matter Completo

```yaml
✅ 356/356 (100%) arquivos com layout
✅ 356/356 (100%) arquivos com title
✅ 355/356 (99.7%) arquivos com category
✅ 356/356 (100%) arquivos com permalink
```

---

## 🎨 Personalização

### Temas Recomendados

1. **[Just the Docs](https://github.com/just-the-docs/just-the-docs)**
   - Perfeito para documentação técnica
   - Busca integrada
   - Navegação em árvore

2. **[Minimal Mistakes](https://github.com/mmistakes/minimal-mistakes)**
   - Design limpo e profissional
   - Altamente customizável
   - SEO otimizado

3. **[Docsy](https://www.docsy.dev/)**
   - Focado em documentação
   - Multi-idioma
   - Versioning

### Customizar Aparência

Crie `_layouts/default.html`:

```html
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>{{ page.title }} | {{ site.title }}</title>
  <link rel="stylesheet" href="/assets/css/style.css">
</head>
<body>
  <nav>
    {% for item in site.navigation %}
      <a href="{{ item.url }}">{{ item.title }}</a>
    {% endfor %}
  </nav>
  
  <main>
    <article>
      {{ content }}
    </article>
  </main>
  
  <footer>
    © 2025 AppDimens
  </footer>
</body>
</html>
```

---

## 🔗 Links Úteis

### Documentação Externa
- [Jekyll Official Docs](https://jekyllrb.com/docs/)
- [Liquid Templating](https://shopify.github.io/liquid/)
- [Kramdown Syntax](https://kramdown.gettalong.org/)
- [GitHub Pages](https://pages.github.com/)

### Recursos AppDimens
- [Repositório Principal](https://github.com/bodenberg/AppDimens)
- [Android README](../README.md)
- [Exemplos Android](../app/src/main/kotlin/)
- [Documentação Multilíngue](../../LANG/)

### Ferramentas
- [Jekyll Themes](https://jekyllthemes.io/)
- [Jekyll Plugins](https://jekyllrb.com/docs/plugins/)
- [Rouge Highlighter](http://rouge.jneen.net/)

---

## 📝 Checklist de Deploy

- [ ] Testar localmente com `jekyll serve`
- [ ] Validar todos os links
- [ ] Verificar responsividade mobile
- [ ] Configurar SEO (meta tags)
- [ ] Adicionar analytics (opcional)
- [ ] Configurar domínio customizado (opcional)
- [ ] Habilitar HTTPS
- [ ] Testar performance (Lighthouse)
- [ ] Criar sitemap.xml (automático)
- [ ] Configurar robots.txt

---

## 🆘 Suporte

### Problemas Comuns

Consulte:
- [QUICKSTART_JEKYLL.md](QUICKSTART_JEKYLL.md) - Seção Troubleshooting
- [Issues do Jekyll](https://github.com/jekyll/jekyll/issues)
- [Stack Overflow - Jekyll](https://stackoverflow.com/questions/tagged/jekyll)

### Contato

- 🐛 **Bugs**: [GitHub Issues](https://github.com/bodenberg/AppDimens/issues)
- 💬 **Discussões**: [GitHub Discussions](https://github.com/bodenberg/AppDimens/discussions)
- 📧 **Email**: [Veja CONTRIBUTING.md](../../CONTRIBUTING.md)

---

## 📈 Roadmap

### ✅ Concluído
- [x] Converter 356 arquivos para Jekyll
- [x] Adicionar front matter completo
- [x] Criar configuração Jekyll
- [x] Documentar processo
- [x] Criar guias de uso

### 🔄 Próximo
- [ ] Deploy em GitHub Pages
- [ ] Adicionar tema customizado
- [ ] Implementar busca (Algolia/Lunr)
- [ ] Adicionar navegação hierárquica
- [ ] Criar versioning de docs
- [ ] Implementar i18n

---

**Última Atualização**: 25/10/2025  
**Versão da Documentação**: 1.0  
**Total de Páginas**: 356  
**Status**: ✅ Completo e Pronto para Uso

