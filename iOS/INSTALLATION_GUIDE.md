---
layout: default
title: "📱 AppDimens iOS - Guia de Instalação"
---

# 📱 AppDimens iOS - Guia de Instalação

> **Languages:** Português (BR) | Español

> **Note:** This file is in Portuguese (BR). English and Spanish translations are not yet available.

Este guia detalha como instalar e configurar a biblioteca AppDimens iOS em diferentes cenários.

## 🎯 Escolha da Instalação

### Para Apps iOS Padrão (Recomendado)
```ruby
pod 'AppDimens'
```
Inclui automaticamente os módulos Core e UI.

### Para Desenvolvimento de Jogos
```ruby
pod 'AppDimens/Core'
pod 'AppDimens/Games'
```

### Para Bibliotecas que Precisam Apenas de Cálculos
```ruby
pod 'AppDimens/Core'
```

## 📦 Instalação com CocoaPods

### 1. Adicionar ao Podfile

```ruby
# Podfile
platform :ios, '13.0'
use_frameworks!

target 'MyApp' do
  # Para apps iOS padrão
  pod 'AppDimens'
  
  # OU para desenvolvimento de jogos
  pod 'AppDimens/Core'
  pod 'AppDimens/Games'
  
  # OU apenas o módulo Core
  pod 'AppDimens/Core'
end
```

### 2. Instalar Dependências

```bash
pod install
```

### 3. Abrir o Workspace

```bash
open MyApp.xcworkspace
```

## 📦 Instalação com Swift Package Manager

### 1. Adicionar Dependência

No Xcode:
1. File → Add Package Dependencies
2. URL: `https://github.com/bodenberg/appdimens.git`
3. Version: `1.0.8` ou superior
4. Adicionar ao target

### 2. Importar nos Arquivos

```swift
import AppDimens        // Para instalação completa
import AppDimensUI      // Para módulo UI (opcional)
import AppDimensGames   // Para módulo Games (opcional)
```

## 🔧 Configuração Inicial

### Para Apps iOS Padrão

```swift
import AppDimens

@main
struct MyApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
```

### Para Jogos com Metal

```swift
import AppDimensGames
import Metal

class GameViewController: UIViewController {
    private var metalDevice: MTLDevice!
    private var metalManager: AppDimensMetal!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Configurar Metal
        metalDevice = MTLCreateSystemDefaultDevice()
        
        // Configurar viewport
        let viewport = MTLViewport(
            originX: 0, originY: 0,
            width: Double(view.bounds.width),
            height: Double(view.bounds.height),
            znear: 0.0, zfar: 1.0
        )
        
        // Inicializar AppDimens Games
        AppDimensGames.shared.initialize(device: metalDevice, viewport: viewport)
    }
}
```

## 🎨 Exemplos de Uso

### SwiftUI

```swift
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("AppDimens")
                .font(.fxSystem(size: 24, weight: .bold))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(12)
        }
    }
}
```

### UIKit

```swift
import AppDimens

class ViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let button = UIButton()
        button.fxTitleFontSize(16)
        button.fxCornerRadius(8)
        button.frame = CGRect(x: 0, y: 0, width: 200.dypt, height: 44.fxpt)
        view.addSubview(button)
    }
}
```

### Metal/Games

```swift
import AppDimensGames
import Metal

class GameRenderer {
    func renderUI() {
        let buttonSize = AppDimensGames.uniform(64.0)
        let fontSize = AppDimensGames.aspectRatio(24.0)
        let spacing = AppDimensGames.viewport(16.0)
        
        // Renderizar elementos de UI
    }
}
```

## 🔍 Verificação da Instalação

### Verificar Módulos Disponíveis

```swift
import AppDimens

let availableModules = AppDimensInfo.availableModules()
print("Módulos disponíveis: \(availableModules)")

let libraryInfo = AppDimensInfo.info()
print("Informações da biblioteca: \(libraryInfo)")
```

### Teste Básico

```swift
import AppDimens

// Teste de dimensão fixa
let fixedDimension = AppDimens.fixed(16).toPoints()
print("Dimensão fixa: \(fixedDimension)")

// Teste de dimensão dinâmica
let dynamicDimension = AppDimens.dynamic(100).toPoints()
print("Dimensão dinâmica: \(dynamicDimension)")

// Teste de sintaxe simplificada
let simpleFixed = 16.fxpt
let simpleDynamic = 100.dypt
print("Sintaxe simplificada - Fixo: \(simpleFixed), Dinâmico: \(simpleDynamic)")
```

## 🚨 Solução de Problemas

### Erro: "No such module 'AppDimens'"

1. Verifique se o pod foi instalado corretamente:
   ```bash
   pod install
   ```

2. Limpe o build:
   ```bash
   Product → Clean Build Folder
   ```

3. Rebuild o projeto

### Erro: "AppDimensGames not found"

1. Verifique se o módulo Games foi incluído:
   ```ruby
   pod 'AppDimens/Games'
   ```

2. Verifique se Metal está disponível no dispositivo

### Erro: "SwiftUI not available"

1. Verifique se o iOS deployment target é 13.0 ou superior
2. Use apenas o módulo Core se SwiftUI não estiver disponível

## 📱 Requisitos do Sistema

### iOS
- iOS 13.0 ou superior
- Xcode 12.0 ou superior
- Swift 5.0 ou superior

### Módulo Games
- Metal disponível (iOS 8.0+)
- MetalKit disponível (iOS 8.0+)

### Módulo UI
- SwiftUI disponível (iOS 13.0+)

## 🔄 Atualização

### CocoaPods

```bash
pod update AppDimens
```

### Swift Package Manager

No Xcode:
1. File → Package Dependencies
2. Selecionar AppDimens
3. Update to Latest Package Versions

## 📚 Próximos Passos

Após a instalação, consulte:

- [Guia de Uso](USAGE_GUIDE.md)
- [Documentação da API](API_REFERENCE.md)
- [Exemplos](Examples/)
- [FAQ](FAQ.md)

## 🤝 Suporte

Para dúvidas ou problemas:

- [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
- [Documentação](https://github.com/bodenberg/appdimens/wiki)
