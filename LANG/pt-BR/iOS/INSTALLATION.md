# 📦 AppDimens iOS - Guia de Instalação

> Idiomas: [English](../../../iOS/INSTALLATION.md) | [Español](../../es/iOS/INSTALLATION.md) | [हिन्दी](../../hi/iOS/INSTALLATION.md) | [Русский](../../ru/iOS/INSTALLATION.md) | [中文](../../zh/iOS/INSTALLATION.md) | [日本語](../../ja/iOS/INSTALLATION.md)

Este guia abrangente ajuda você a instalar e integrar o AppDimens iOS no seu projeto com instruções passo a passo.

## 📋 Requisitos

- iOS 13.0+
- Swift 5.0+
- Xcode 12.0+

## 🚀 Métodos de Instalação

### CocoaPods (Recomendado)

O CocoaPods é o método recomendado de instalação para o AppDimens iOS.

#### 1. Adicionar ao seu Podfile

```ruby
platform :ios, '13.0'
use_frameworks!

target 'SeuApp' do
  pod 'AppDimens'
end
```

#### 2. Instalar o pod

```bash
pod install
```

#### 3. Abrir o workspace

```bash
open SeuApp.xcworkspace
```

#### 4. Importar nos seus arquivos Swift

```swift
import AppDimens
```

#### Configuração Avançada do CocoaPods

Para configuração avançada, você pode especificar opções adicionais no seu Podfile:

```ruby
platform :ios, '13.0'
use_frameworks!

target 'SeuApp' do
  pod 'AppDimens', '~> 1.0'
end

# Pós-instalação para ajustes adicionais
post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '13.0'
    end
  end
end
```

### Swift Package Manager

O Swift Package Manager é a ferramenta oficial de gerenciamento de dependências da Apple.

#### Método 1: Interface do Xcode

1. No Xcode:
   - File → Add Package Dependencies
   - Informe: `https://github.com/bodenberg/appdimens.git`
   - Selecione a versão: `1.0.5` ou superior
   - Adicione ao seu target

2. Importar nos seus arquivos Swift:
```swift
import AppDimens
```

#### Método 2: Package.swift

Adicione ao seu arquivo `Package.swift`:

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
]
```

#### Método 3: Configurações do Projeto no Xcode

1. Selecione seu projeto no Xcode
2. Vá em Package Dependencies
3. Clique no botão "+"
4. Informe: `https://github.com/bodenberg/appdimens.git`
5. Selecione a versão e adicione ao target

### Instalação Manual

Se preferir incluir o código-fonte diretamente no seu projeto:

#### 1. Baixar o código-fonte

```bash
git clone https://github.com/bodenberg/appdimens.git
```

#### 2. Copiar a pasta Sources

- Copie `Sources/AppDimens/` para o seu projeto
- Adicione todos os arquivos Swift ao seu projeto Xcode

#### 3. Importar nos seus arquivos Swift

```swift
import AppDimens
```

## 🔧 Configuração

### Build Settings

Garanta que seu projeto tenha as seguintes configurações:

- iOS Deployment Target: 13.0 ou superior
- Swift Language Version: Swift 5
- Enable Bitcode: No (se usar CocoaPods)

### Configuração do Projeto

#### Para CocoaPods

Após executar `pod install`, seu projeto deve estar configurado automaticamente. O Podfile gerencia:

- Vinculação de frameworks
- Configurações de build
- Dependências do target

#### Para Swift Package Manager

O pacote será configurado automaticamente quando adicionado pelo Xcode. Verifique:

- A associação correta ao target
- O framework vinculado ao seu target
- Importações nos arquivos necessários

#### Para Instalação Manual

1. Adicione todos os arquivos Swift ao seu target
2. Verifique a associação correta ao target
3. Adicione importações onde necessário

## 🎯 Início Rápido

### Uso Básico

Após instalar, você pode começar a usar o AppDimens imediatamente:

```swift
import AppDimens

// Escala fixa (recomendado para elementos de UI)
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// Escala dinâmica (para layouts proporcionais)
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt
```

### Integração SwiftUI

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack {
            Text("Olá, mundo!")
                .font(.fxSystem(size: 16))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(8)
        }
    }
}
```

### Integração UIKit

```swift
import UIKit
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let button = UIButton()
        button.frame = CGRect(
            x: 0, y: 0,
            width: 200.dypt,
            height: 48.fxpt
        )
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        view.addSubview(button)
    }
}
```

## 🔧 Solução de Problemas

### Problemas Comuns

#### 1. Erro de Importação
```
No such module 'AppDimens'
```

Soluções:
- Garanta que você executou `pod install` após adicionar a dependência
- Limpe e reconstrua o projeto (Product → Clean Build Folder)
- Verifique se o target inclui o framework AppDimens
- Confirme que o framework está devidamente vinculado nas configurações do target

#### 2. Erros de Build
```
Use of unresolved identifier 'fxpt'
```

Soluções:
- Certifique-se de ter importado AppDimens: `import AppDimens`
- Verifique se está usando a sintaxe correta: `16.fxpt`
- Confirme que o framework está instalado e vinculado corretamente

#### 3. Problemas com CocoaPods
```
[!] Unable to find a specification for 'AppDimens'
```

Soluções:
- Atualize o CocoaPods: `sudo gem install cocoapods`
- Atualize o repositório de pods: `pod repo update`
- Tente instalar novamente: `pod install`
- Verifique a conexão com a internet

#### 4. Problemas com Swift Package Manager
```
Failed to resolve package dependencies
```

Soluções:
- Verifique sua conexão com a internet
- Confirme se a URL do repositório está correta
- Limpe o cache de pacotes no Xcode
- Reinicie o Xcode e tente novamente

#### 5. Conflitos de Versão
```
Multiple commands produce the same file
```

Soluções:
- Limpe a pasta de build
- Apague derived data
- Reinicie o Xcode
- Verifique frameworks duplicados

### Passos de Debug

Se o problema persistir, experimente:

1. Verificar Instalação:
   ```swift
   import AppDimens
   print("AppDimens version: \(AppDimens.version)")
   ```

2. Verificar Framework:
   - Confirme que AppDimens aparece no navegador do projeto
   - Verifique se está adicionado em "Frameworks, Libraries, and Embedded Content" do seu target

3. Limpar e Recompilar:
   - Product → Clean Build Folder
   - Apague derived data
   - Reinicie o Xcode
   - Compile novamente

4. Verificar Dependências:
   - Garanta que todos os frameworks necessários estão instalados
   - Confirme que o iOS Deployment Target é 13.0+

## 🔄 Migração a partir do Android

Se você estiver migrando da versão Android do AppDimens, veja o [Guia de Migração](MIGRATION.md) para instruções detalhadas.

### Referência Rápida de Migração

| Android | iOS |
|---------|-----|
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |

## 📚 Próximos Passos

Após instalar com sucesso:

1. Leia a documentação: [README.md](README.md)
2. Teste os exemplos: [Examples/](Examples/)
3. Explore a API: [DOCUMENTATION.md](DOCUMENTATION.md)
4. Participe da comunidade: [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)

## 🆘 Ajuda

Se encontrar problemas não cobertos aqui:

1. Consulte a documentação: [README.md](README.md)
2. Revise os exemplos: [Examples/](Examples/)
3. Crie uma issue: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)

## 📄 Licença

Este projeto está licenciado sob a Licença Apache 2.0 - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

**AppDimens iOS** - Design responsivo feito simples! 🚀
