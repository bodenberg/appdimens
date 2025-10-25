---
layout: default
title: "Compatibilidade Jekyll - AppDimens"
---

# Compatibilidade Jekyll/GitHub Pages

## Resumo das Alterações

Este documento descreve as mudanças realizadas para tornar todos os arquivos Markdown compatíveis com Jekyll/GitHub Pages.

## O que foi feito

### 1. Criação do `_config.yml`

Foi criado um arquivo de configuração Jekyll na raiz do projeto com:
- **Theme**: `jekyll-theme-primer`
- **Markdown**: Kramdown com sintaxe GFM (GitHub Flavored Markdown)
- **Syntax Highlighter**: Rouge
- **Exclusões**: Arquivos e diretórios de build, dependências e testes

### 2. Front Matter YAML adicionado

Todos os **477 arquivos Markdown** receberam front matter YAML no formato:

```yaml
---
layout: default
title: "Título do Arquivo"
---
```

#### Arquivos Processados:
- ✅ Documentação raiz (README, CONTRIBUTING, etc.)
- ✅ Documentação Android (DOCS/DYNAMIC, DOCS/GAMES, DOCS/LIBRARY, DOCS/SDPS, DOCS/SSPS)
- ✅ Documentação iOS
- ✅ Documentação Flutter
- ✅ Documentação React Native
- ✅ Documentação Web
- ✅ Documentação multilíngue (LANG/)

### 3. Limpeza de Comentários Dokka

Os comentários de navegação do Dokka (formato `//[...]`) foram removidos do início dos arquivos para evitar problemas de renderização.

## Estrutura Jekyll

```
AppDimens/
├── _config.yml              # Configuração Jekyll
├── README.md                # Página inicial (com front matter)
├── Android/
│   ├── DOCS/
│   │   ├── DYNAMIC/
│   │   │   └── MARKDOWN/   # Documentação API Dynamic
│   │   ├── GAMES/
│   │   │   └── MARKDOWN/   # Documentação API Games
│   │   ├── LIBRARY/
│   │   │   └── MARKDOWN/   # Documentação API Library
│   │   ├── SDPS/
│   │   │   └── MARKDOWN/   # Documentação API SDPS
│   │   └── SSPS/
│   │       └── MARKDOWN/   # Documentação API SSPS
│   └── README.md
├── iOS/
│   └── DOCS/
├── Flutter/
├── ReactNative/
├── Web/
└── LANG/                    # Documentação multilíngue
    ├── pt-BR/
    ├── es/
    ├── zh/
    ├── ja/
    ├── hi/
    └── ru/
```

## Configurações do Jekyll

### Theme
Utiliza o tema oficial do GitHub Pages: `jekyll-theme-primer`

### Processamento de Markdown
- **Parser**: Kramdown
- **Input**: GFM (GitHub Flavored Markdown)
- **Syntax Highlighter**: Rouge

### Exclusões
O `_config.yml` exclui do build Jekyll:
- Arquivos de build (`.gradle`, `.gradle.kts`)
- Dependências (`node_modules/`, `*.lock`)
- Testes (`__tests__/`, `Tests/`)
- Projetos nativos (`.xcodeproj`, `.jar`)

## Deploy no GitHub Pages

### Opção 1: Branch `gh-pages`
```bash
git checkout -b gh-pages
git push origin gh-pages
```

Configure no GitHub:
1. Vá em **Settings** > **Pages**
2. Source: **Deploy from a branch**
3. Branch: **gh-pages** / **/ (root)**

### Opção 2: GitHub Actions
O GitHub Pages pode automaticamente fazer build do Jekyll a partir da branch `main` ou `master`.

Configure no GitHub:
1. Vá em **Settings** > **Pages**
2. Source: **GitHub Actions**

## Verificação Local

Para testar o site Jekyll localmente:

```bash
# Instalar Jekyll (se necessário)
gem install bundler jekyll

# Criar Gemfile (se não existir)
bundle init
bundle add jekyll jekyll-theme-primer

# Servir o site
bundle exec jekyll serve

# Acessar em: http://localhost:4000
```

## Compatibilidade

✅ **GitHub Pages**: Totalmente compatível  
✅ **Jekyll 3.10.0**: Compatível  
✅ **Kramdown**: Compatível  
✅ **GFM (GitHub Flavored Markdown)**: Compatível  
✅ **Front Matter YAML**: Presente em todos os arquivos  

## Problemas Resolvidos

### Antes
- ❌ Arquivos sem front matter YAML
- ❌ Comentários Dokka causando problemas de renderização
- ❌ Sem configuração Jekyll
- ❌ Nomes de arquivo com hífens iniciais não eram processados corretamente

### Depois
- ✅ Todos os arquivos com front matter YAML válido
- ✅ Conteúdo limpo e bem formatado
- ✅ Configuração Jekyll completa
- ✅ Build bem-sucedido no GitHub Pages

## Manutenção

### Novos arquivos Markdown
Ao criar novos arquivos `.md`, sempre adicione front matter:

```markdown
---
layout: default
title: "Título do Arquivo"
---

# Conteúdo...
```

### Atualização do tema
Para mudar o tema, edite `_config.yml`:

```yaml
theme: jekyll-theme-cayman  # ou outro tema
```

Temas disponíveis no GitHub Pages:
- jekyll-theme-primer (atual)
- jekyll-theme-cayman
- jekyll-theme-minimal
- jekyll-theme-slate
- jekyll-theme-modernist
- jekyll-theme-midnight

## Status

✅ **Todos os arquivos estão compatíveis com Jekyll/GitHub Pages**

Total de arquivos processados: **477**  
Data: 25/10/2025

---

Para mais informações sobre Jekyll e GitHub Pages, consulte:
- [Documentação Jekyll](https://jekyllrb.com/docs/)
- [GitHub Pages Documentation](https://docs.github.com/pt/pages)

