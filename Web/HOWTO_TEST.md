# 🧪 Como Testar a Biblioteca WebDimens

## ⚠️ Problema com CORS e Arquivos Locais

Ao tentar abrir arquivos HTML localmente usando `file://`, o navegador bloqueia o carregamento de módulos ES6 por motivos de segurança (política CORS).

**Erros comuns:**
```
❌ Access to script at 'file:///.../index.mjs' from origin 'null' has been blocked by CORS policy
❌ Failed to load resource: net::ERR_FAILED
❌ Uncaught ReferenceError: module is not defined
```

## ✅ Soluções

### Opção 1: Demo Standalone (RECOMENDADO - Mais Fácil)

Abra diretamente no navegador - **funciona sem servidor HTTP**:

```
file:///home/bodenberg/Documentos/GitHub/AppDimens/Web/examples/standalone-demo.html
```

Este arquivo contém uma versão simplificada do WebDimens inline e testa todas as funcionalidades principais.

**Características:**
- ✅ Funciona sem servidor HTTP
- ✅ Testes automatizados
- ✅ Interface visual interativa
- ✅ Demonstra FX, DY e FL
- ✅ Mostra viewport e breakpoints

---

### Opção 2: Usar Servidor HTTP Local

Para testar os arquivos compilados completos, você precisa de um servidor HTTP.

#### Método 1: Python (mais simples)

```bash
cd /home/bodenberg/Documentos/GitHub/AppDimens/Web/examples

# Python 3
python3 -m http.server 8000

# Python 2
python -m SimpleHTTPServer 8000
```

Depois abra: `http://localhost:8000/test-basic.html`

#### Método 2: Node.js (http-server)

```bash
# Instalar globalmente (uma vez)
npm install -g http-server

# Na pasta examples
cd /home/bodenberg/Documentos/GitHub/AppDimens/Web/examples
http-server -p 8000
```

Depois abra: `http://localhost:8000/test-basic.html`

#### Método 3: PHP

```bash
cd /home/bodenberg/Documentos/GitHub/AppDimens/Web/examples
php -S localhost:8000
```

Depois abra: `http://localhost:8000/test-basic.html`

#### Método 4: npm serve

```bash
# Instalar globalmente
npm install -g serve

# Executar
cd /home/bodenberg/Documentos/GitHub/AppDimens/Web/examples
serve -p 8000
```

Depois abra: `http://localhost:8000/test-basic.html`

---

### Opção 3: Usar Extensão do VS Code

Se estiver usando VS Code:

1. Instale a extensão **Live Server**
2. Clique com botão direito em qualquer arquivo HTML
3. Selecione "Open with Live Server"

---

## 📝 Arquivos de Teste Disponíveis

### 1. **standalone-demo.html** ⭐ RECOMENDADO
- **Funciona sem servidor:** ✅ SIM
- **Características:** Demo completa inline
- **Testes:** Automatizados com UI visual
- **Caminho:** `Web/examples/standalone-demo.html`

### 2. **test-basic.html**
- **Funciona sem servidor:** ❌ NÃO (precisa de servidor HTTP)
- **Características:** Testa biblioteca compilada completa
- **Testes:** 10 testes automatizados
- **Caminho:** `Web/examples/test-basic.html`

### 3. **vanilla-example.html**
- **Funciona sem servidor:** ❌ NÃO (precisa de servidor HTTP)
- **Características:** Exemplo completo de uso
- **Interface:** UI profissional com cards
- **Caminho:** `Web/examples/vanilla-example.html`

### 4. **index.html**
- **Funciona sem servidor:** ❌ NÃO (precisa de servidor HTTP)
- **Características:** Demo interativa completa
- **Caminho:** `Web/examples/index.html`

---

## 🎯 Testes Incluídos

Todos os arquivos de teste validam:

1. ✅ **Import de Módulos** - Biblioteca carrega corretamente
2. ✅ **Fixed Dimensions (FX)** - Escalonamento logarítmico
3. ✅ **Dynamic Dimensions (DY)** - Escalonamento proporcional
4. ✅ **Fluid Dimensions (FL)** - Clamp CSS
5. ✅ **Viewport Observer** - Detecta dimensões da janela
6. ✅ **Breakpoint Manager** - Identifica breakpoints
7. ✅ **Orientation** - Portrait/Landscape
8. ✅ **Aspect Ratio** - Cálculo de proporção
9. ✅ **Aplicação de Estilos** - CSS dinâmico
10. ✅ **Observer Callbacks** - Listeners funcionam

---

## 🔍 Validação Visual

### O que esperar ao testar:

1. **Status Verde** - Todos os testes passaram
2. **Métricas de Viewport** - Largura, altura, breakpoint, orientação
3. **Cards Interativos** - Clique nos botões para testar escalas
4. **Layout Responsivo** - Redimensione a janela para ver mudanças

### Exemplo de saída esperada:

```
✅ Todos os testes passaram! WebDimens está 100% funcional!

Viewport: 1920x1080
Breakpoint: 2xl
Orientação: landscape
Aspect Ratio: 1.78

✓ WebDimens Inicializado
✓ Fixed Scaling (FX): fx(16) = 18px
✓ Dynamic Scaling (DY): dy(100) = 512px
✓ Fluid Scaling (FL): Clamp gerado
✓ Viewport Width: 1920px
✓ Viewport Height: 1080px
✓ Breakpoint Detection: 2xl
✓ Orientation: landscape
✓ Aspect Ratio: 1.78
✓ Aplicar ao DOM
```

---

## 🐛 Troubleshooting

### Problema: "module is not defined"
**Solução:** Use `standalone-demo.html` ou inicie um servidor HTTP

### Problema: "CORS policy blocking"
**Solução:** Use `standalone-demo.html` ou inicie um servidor HTTP

### Problema: "Failed to load resource"
**Solução:** Verifique se o arquivo `dist/index.mjs` existe e use servidor HTTP

### Problema: Testes não aparecem
**Solução:** Abra o console do navegador (F12) para ver erros

### Problema: Servidor HTTP não inicia
**Solução:** 
- Verifique se a porta 8000 está livre
- Tente outra porta: `python3 -m http.server 8080`
- Ou use `standalone-demo.html`

---

## 📦 Estrutura de Arquivos

```
Web/
├── dist/                       # Biblioteca compilada
│   ├── index.js               # CommonJS
│   ├── index.mjs              # ES Modules
│   ├── index.d.ts             # TypeScript definitions
│   └── index.d.mts            # TypeScript definitions (ESM)
│
├── examples/
│   ├── standalone-demo.html   ⭐ Use este! (sem servidor)
│   ├── test-basic.html        # Testes completos (precisa servidor)
│   ├── vanilla-example.html   # Demo UI (precisa servidor)
│   ├── index.html             # Demo interativa (precisa servidor)
│   ├── svelte-example.svelte  # Exemplo Svelte
│   ├── angular-example.ts     # Exemplo Angular
│   └── react-example.tsx      # Exemplo React
│
├── src/                       # Código fonte
│   ├── index.ts              # Entry point
│   └── integrations/         # Framework integrations
│       ├── react.tsx
│       ├── vue.ts
│       ├── svelte.ts
│       └── angular.ts
│
└── package.json              # Configuração npm
```

---

## ✅ Checklist de Validação

- [ ] Abrir `standalone-demo.html` diretamente no navegador
- [ ] Verificar se todos os 9-10 testes aparecem em verde
- [ ] Clicar nos botões "Testar FX/DY/FL" e ver animações
- [ ] Redimensionar a janela e ver métricas atualizarem
- [ ] Verificar console para mensagem de sucesso
- [ ] (Opcional) Iniciar servidor HTTP e testar `test-basic.html`
- [ ] (Opcional) Testar em diferentes navegadores

---

## 🎉 Resultado Esperado

Se tudo estiver funcionando:

```
✅ BIBLIOTECA WEB 100% FUNCIONAL!

- Build compilado com sucesso
- Testes automatizados passando
- Exemplos interativos funcionando
- Todas as integrações implementadas
- Pronto para publicação no npm
```

---

## 📞 Suporte

Se encontrar problemas:

1. Verifique o console do navegador (F12)
2. Confirme que está usando `standalone-demo.html` para teste rápido
3. Para testes completos, use servidor HTTP
4. Veja `VALIDATION_REPORT.md` para detalhes técnicos

---

**WebDimens v1.0.8** - Sistema Avançado de Dimensionamento Responsivo  
Desenvolvido por: Jean Bodenberg  
Licença: Apache 2.0

