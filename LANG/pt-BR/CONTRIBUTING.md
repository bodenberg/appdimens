# 🤝 Contribuindo para o AppDimens

Obrigado pelo seu interesse em contribuir para o AppDimens! Este documento fornece diretrizes e informações para contribuidores.

> Idiomas: [English](../../CONTRIBUTING.md) | [Español](../es/CONTRIBUTING.md) | [हिन्दी](../hi/CONTRIBUTING.md) | [Русский](../ru/CONTRIBUTING.md) | [中文](../zh/CONTRIBUTING.md) | [日本語](../ja/CONTRIBUTING.md)

## 📋 Índice

1. [Código de Conduta](#código-de-conduta)
2. [Começando](#começando)
3. [Configuração de Desenvolvimento](#configuração-de-desenvolvimento)
4. [Diretrizes de Contribuição](#diretrizes-de-contribuição)
5. [Processo de Pull Request](#processo-de-pull-request)
6. [Diretrizes de Issues](#diretrizes-de-issues)
7. [Documentação](#documentação)
8. [Testes](#testes)
9. [Processo de Release](#processo-de-release)

## 📜 Código de Conduta

Este projeto adere a um código de conduta. Ao participar, espera-se que você mantenha este código. Por favor, reporte comportamentos inaceitáveis através do nosso canal de contato privado.

## 🚀 Começando

### Pré-requisitos

- **Desenvolvimento Android**: Android Studio, JDK 11+, Android SDK
- **Desenvolvimento iOS**: Xcode 12+, Swift 5.0+, iOS 13.0+
- **Geral**: Git, conta GitHub

### Fork e Clone

1. Faça fork do repositório no GitHub
2. Clone seu fork localmente:
   ```bash
   git clone https://github.com/seu-usuario/appdimens.git
   cd appdimens
   ```
3. Adicione o repositório upstream:
   ```bash
   git remote add upstream https://github.com/bodenberg/appdimens.git
   ```

## 🛠 Configuração de Desenvolvimento

### Configuração Android

1. **Abrir no Android Studio**:
   ```bash
   cd Android
   # Abra o Android Studio e importe o projeto
   ```

2. **Instalar Dependências**:
   ```bash
   ./gradlew build
   ```

3. **Executar Testes**:
   ```bash
   ./gradlew test
   ```

### Configuração iOS

1. **Abrir no Xcode**:
   ```bash
   cd iOS
   open AppDimens.xcodeproj
   ```

2. **Instalar Dependências**:
   - CocoaPods: `pod install`
   - Swift Package Manager: Automático

3. **Executar Testes**:
   - No Xcode: Product → Test (⌘+U)

## 📝 Diretrizes de Contribuição

### Tipos de Contribuições

Aceitamos vários tipos de contribuições:

- 🐛 **Correções de Bugs**: Corrigir issues existentes
- ✨ **Novos Recursos**: Adicionar novas funcionalidades
- 📚 **Documentação**: Melhorar documentação
- 🧪 **Testes**: Adicionar ou melhorar testes
- 🎨 **Exemplos**: Adicionar exemplos de uso
- 🔧 **Performance**: Otimizar performance
- 🌐 **Traduções**: Adicionar suporte a idiomas

### Fluxo de Trabalho de Desenvolvimento

1. **Criar um Branch**:
   ```bash
   git checkout -b feature/nome-do-seu-recurso
   # ou
   git checkout -b fix/numero-da-issue
   ```

2. **Fazer Alterações**:
   - Siga os padrões de código
   - Adicione testes para novas funcionalidades
   - Atualize a documentação conforme necessário

3. **Testar Suas Alterações**:
   ```bash
   # Android
   ./gradlew test
   ./gradlew lint
   
   # iOS
   # Execute testes no Xcode
   ```

4. **Commit Suas Alterações**:
   ```bash
   git add .
   git commit -m "feat: adicionar novo tipo de dimensão responsiva"
   ```

### Padrões de Código

#### Android (Kotlin)

- Siga as [Convenções de Código Kotlin](https://kotlinlang.org/docs/coding-conventions.html)
- Use nomes significativos para variáveis e funções
- Adicione comentários KDoc para APIs públicas
- Siga o estilo de código existente

```kotlin
/**
 * Calcula dimensões responsivas baseadas nas características da tela.
 * 
 * @param baseValue O valor de dimensão base
 * @param screenType O tipo de tela para usar nos cálculos
 * @return A dimensão responsiva calculada
 */
fun calculateDimension(baseValue: Float, screenType: ScreenType): Float {
    // Implementação
}
```

#### iOS (Swift)

- Siga as [Diretrizes de Design de API Swift](https://swift.org/documentation/api-design-guidelines/)
- Use nomes significativos para variáveis e funções
- Adicione comentários de documentação para APIs públicas
- Siga o estilo de código existente

```swift
/// Calcula dimensões responsivas baseadas nas características da tela.
/// 
/// - Parameters:
///   - baseValue: O valor de dimensão base
///   - screenType: O tipo de tela para usar nos cálculos
/// - Returns: A dimensão responsiva calculada
func calculateDimension(baseValue: CGFloat, screenType: ScreenType) -> CGFloat {
    // Implementação
}
```

### Formato de Mensagem de Commit

Use o formato de commits convencionais:

```
tipo(escopo): descrição

[corpo opcional]

[rodapé opcional]
```

**Tipos**:
- `feat`: Novo recurso
- `fix`: Correção de bug
- `docs`: Alterações na documentação
- `style`: Alterações de estilo de código
- `refactor`: Refatoração de código
- `test`: Adicionar ou atualizar testes
- `chore`: Tarefas de manutenção

**Exemplos**:
```
feat(android): adicionar novo algoritmo de escala para tablets
fix(ios): resolver vazamento de memória em cálculos de dimensão
docs: atualizar guia de instalação com novos exemplos
test(android): adicionar testes unitários para escala condicional
```

## 🔄 Processo de Pull Request

### Antes de Submeter

1. **Atualizar Documentação**: Atualize a documentação relevante
2. **Adicionar Testes**: Adicione testes para novas funcionalidades
3. **Atualizar Exemplos**: Adicione exemplos se aplicável
4. **Verificar Compatibilidade**: Garanta que as alterações funcionem nas plataformas suportadas
5. **Performance**: Considere as implicações de performance

### Template de Pull Request

```markdown
## Descrição
Breve descrição das alterações

## Tipo de Alteração
- [ ] Correção de bug
- [ ] Novo recurso
- [ ] Atualização de documentação
- [ ] Melhoria de performance
- [ ] Alteração que quebra compatibilidade

## Testes
- [ ] Testes passam localmente
- [ ] Novos testes adicionados para novas funcionalidades
- [ ] Testes manuais completados

## Checklist
- [ ] Código segue as diretrizes de estilo do projeto
- [ ] Auto-revisão completada
- [ ] Documentação atualizada
- [ ] Sem alterações que quebram compatibilidade (ou documentadas)
```

### Processo de Revisão

1. **Verificações Automatizadas**: Pipeline CI/CD executa automaticamente
2. **Revisão de Código**: Mantenedores revisam o código
3. **Testes**: Alterações são testadas em múltiplos dispositivos
4. **Aprovação**: Pelo menos uma aprovação de mantenedor é necessária

## 🐛 Diretrizes de Issues

### Antes de Criar uma Issue

1. **Pesquisar Issues Existentes**: Verifique se a issue já existe
2. **Verificar Documentação**: Revise a documentação relevante
3. **Testar Última Versão**: Certifique-se de estar usando a versão mais recente

### Relatórios de Bug

Use o template de relatório de bug:

```markdown
## Descrição do Bug
Descrição clara do bug

## Passos para Reproduzir
1. Passo um
2. Passo dois
3. Passo três

## Comportamento Esperado
O que deveria acontecer

## Comportamento Atual
O que realmente acontece

## Ambiente
- Plataforma: Android/iOS
- Versão: X.X.X
- Dispositivo: [Modelo do dispositivo]
- Versão do SO: [Versão do SO]

## Contexto Adicional
Qualquer informação adicional
```

### Solicitações de Recursos

Use o template de solicitação de recurso:

```markdown
## Descrição do Recurso
Descrição clara do recurso

## Caso de Uso
Por que este recurso é necessário?

## Solução Proposta
Como este recurso deveria funcionar?

## Alternativas Consideradas
Outras soluções que você considerou

## Contexto Adicional
Qualquer informação adicional
```

## 📚 Documentação

### Padrões de Documentação

- **Clara e Concisa**: Escreva documentação clara e fácil de entender
- **Exemplos**: Inclua exemplos práticos
- **Atualizada**: Mantenha a documentação atualizada com as alterações de código
- **Consistente**: Siga o estilo de documentação estabelecido

### Tipos de Documentação

1. **Documentação de API**: Comentários de código e documentação inline
2. **Guias de Usuário**: Guias de instalação e uso
3. **Exemplos**: Exemplos de uso prático
4. **Arquitetura**: Documentação técnica de arquitetura

## 🧪 Testes

### Requisitos de Teste

- **Testes Unitários**: Adicione testes unitários para novas funcionalidades
- **Testes de Integração**: Teste a integração com código existente
- **Testes de Plataforma**: Teste em Android e iOS
- **Testes de Dispositivo**: Teste em diferentes tipos de dispositivos e tamanhos de tela

### Executando Testes

#### Android
```bash
# Testes unitários
./gradlew test

# Testes instrumentados
./gradlew connectedAndroidTest

# Verificações de lint
./gradlew lint
```

#### iOS
```bash
# No Xcode: Product → Test (⌘+U)
# Ou linha de comando:
xcodebuild test -scheme AppDimens -destination 'platform=iOS Simulator,name=iPhone 14'
```

### Cobertura de Testes

- **Cobertura Mínima**: 80% para novo código
- **Caminhos Críticos**: 100% de cobertura para funcionalidades críticas
- **Casos Extremos**: Teste casos extremos e condições de erro

## 🚀 Processo de Release

### Numeração de Versão

Seguimos o [Versionamento Semântico](https://semver.org/):

- **MAJOR**: Alterações que quebram compatibilidade
- **MINOR**: Novos recursos (compatível com versões anteriores)
- **PATCH**: Correções de bugs (compatível com versões anteriores)

## 📞 Obtendo Ajuda

### Canais de Comunicação

- **GitHub Issues**: Para relatórios de bugs e solicitações de recursos
- **GitHub Discussions**: Para perguntas e discussão geral
- **Email**: para assuntos privados

### Tempos de Resposta

- **Issues**: Dentro de 48 horas
- **Pull Requests**: Dentro de 72 horas
- **Perguntas**: Dentro de 24 horas

## 🎉 Reconhecimento

Contribuidores serão reconhecidos em:

- **README**: Listados como contribuidores
- **Notas de Release**: Mencionados nas notas de release
- **GitHub**: Listados na seção de contribuidores

## 📄 Licença

Ao contribuir para o AppDimens, você concorda que suas contribuições serão licenciadas sob a Licença Apache 2.0.

## 🙏 Obrigado

Obrigado por contribuir para o AppDimens! Suas contribuições ajudam a tornar o design responsivo acessível para desenvolvedores em todo o mundo.

---

**Feliz Contribuição!** 🚀
