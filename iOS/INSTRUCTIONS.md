# 📋 Instruções de Uso do Projeto AppDimens iOS

## 🎯 Objetivo

Este projeto converte a biblioteca Android AppDimens para iOS, criando uma biblioteca Swift/SwiftUI que pode ser usada com CocoaPods.

## 📁 Estrutura Criada

```
PROJETO_IOS/
├── AppDimens.podspec              # Configuração para CocoaPods
├── LICENSE                        # Licença Apache 2.0
├── README.md                      # Documentação principal
├── CHANGELOG.md                   # Histórico de versões
├── DOCUMENTATION.md               # Documentação técnica detalhada
├── INSTALLATION.md                # Guia de instalação
├── USAGE_GUIDE.md                 # Guia prático de uso
├── PROJECT_SUMMARY.md             # Resumo completo do projeto
├── INSTRUCTIONS.md                # Este arquivo
├── AppDimens.xcodeproj/           # Projeto Xcode configurado
│   └── project.pbxproj
├── AppDimens/
│   └── Info.plist                 # Configurações do framework
├── Sources/AppDimens/             # Código fonte Swift
│   ├── AppDimens.swift            # Classe principal singleton
│   ├── AppDimensTypes.swift       # Tipos, enums e estruturas
│   ├── AppDimensAdjustmentFactors.swift # Cálculos de fatores de tela
│   ├── AppDimensFixed.swift       # Dimensionamento fixo (FX)
│   ├── AppDimensDynamic.swift     # Dimensionamento dinâmico (DY)
│   └── AppDimensExtensions.swift  # Extensões para SwiftUI e UIKit
└── Examples/                      # Exemplos práticos
    ├── UIKitExample.swift         # Exemplo completo com UIKit
    └── SwiftUIExample.swift       # Exemplo completo com SwiftUI
```

## 🚀 Como Usar

### 1. Instalação via CocoaPods

```ruby
# No seu Podfile
platform :ios, '13.0'
use_frameworks!

target 'SeuApp' do
  pod 'AppDimens'
end
```

```bash
pod install
```

### 2. Uso Básico

```swift
import AppDimens

// Fixed scaling - para elementos de UI
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// Dynamic scaling - para layouts
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt
```

### 3. SwiftUI

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack(spacing: 20.fxpt) {
            Text("Título")
                .font(.fxSystem(size: 24, weight: .bold))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(12)
        }
    }
}
```

### 4. UIKit

```swift
import UIKit
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

## 🔧 Funcionalidades Implementadas

### ✅ Core Features

- **AppDimens**: Classe singleton principal
- **AppDimensFixed**: Dimensionamento fixo com ajuste logarítmico
- **AppDimensDynamic**: Dimensionamento dinâmico com ajuste proporcional
- **AppDimensAdjustmentFactors**: Cálculos de fatores de tela
- **AppDimensTypes**: Tipos e enums (DeviceType, ScreenType, etc.)

### ✅ Extensões

- **CGFloat/Int Extensions**: `16.fxpt`, `100.dypt`, etc.
- **SwiftUI Extensions**: `.fxPadding()`, `.fxFrame()`, `.fxCornerRadius()`
- **UIKit Extensions**: `.fxFontSize()`, `.fxCornerRadius()`, etc.

### ✅ Configurações Avançadas

- **Device-Specific Values**: Valores customizados por tipo de dispositivo
- **Screen Qualifiers**: Qualificadores de tela para controle fino
- **Aspect Ratio Adjustment**: Ajuste de proporção para telas extremas
- **Multi-Window Support**: Suporte para modo multi-janela
- **Physical Units**: Conversão de unidades físicas (mm, cm, inches)

## 📊 Comparação com Android

| Android | iOS |
|---------|-----|
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `AppDimens.dynamic(100).toDp()` | `AppDimens.dynamic(100).toPoints()` |
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |
| `UiModeType.PHONE` | `DeviceType.phone` |

## 🎯 Modelos Matemáticos

### Fixed (FX) - Ajuste Refinado
- **Filosofia**: Ajuste logarítmico para escalonamento refinado
- **Uso**: Botões, paddings, fontes, ícones
- **Crescimento**: Suave e controlado

### Dynamic (DY) - Ajuste Proporcional
- **Filosofia**: Ajuste baseado em porcentagem
- **Uso**: Containers, espaçadores, grids
- **Crescimento**: Linear mantendo porcentagem da tela

## 📱 Suporte a Dispositivos

- **Phone**: iPhones
- **Tablet**: iPads
- **Watch**: Apple Watch
- **TV**: Apple TV
- **CarPlay**: CarPlay

## 🔍 Exemplos Práticos

### Card Responsivo
```swift
struct ResponsiveCard: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 12.fxpt) {
            Text("Título")
                .font(.fxSystem(size: 18, weight: .semibold))
            
            Text("Descrição que se adapta ao tamanho da tela.")
                .font(.fxSystem(size: 14))
                .foregroundColor(.secondary)
        }
        .fxPadding(16)
        .dyFrame(width: 300)  // Largura dinâmica
        .background(Color(.systemGray6))
        .fxCornerRadius(12)
    }
}
```

### Layout Percentual
```swift
struct PercentageLayout: View {
    var body: some View {
        Rectangle()
            .fill(Color.blue.opacity(0.3))
            .dyFrame(width: AppDimens.percentage(0.8))  // 80% da tela
            .fxFrame(height: 100)
            .fxCornerRadius(8)
    }
}
```

## 🛠️ Configuração do Projeto

### Build Settings
- **iOS Deployment Target**: 13.0+
- **Swift Language Version**: Swift 5
- **Enable Bitcode**: No

### Dependências
- **UIKit**: Para extensões UIKit
- **SwiftUI**: Para extensões SwiftUI (iOS 13.0+)

## 📚 Documentação

### Arquivos de Documentação
1. **README.md** - Documentação principal
2. **DOCUMENTATION.md** - Referência técnica
3. **INSTALLATION.md** - Guia de instalação
4. **USAGE_GUIDE.md** - Guia prático
5. **CHANGELOG.md** - Histórico de versões
6. **PROJECT_SUMMARY.md** - Resumo completo

### Exemplos
1. **UIKitExample.swift** - Exemplo UIKit completo
2. **SwiftUIExample.swift** - Exemplo SwiftUI completo

## 🚀 Próximos Passos

### Para Desenvolvedores
1. **Teste a biblioteca** nos seus projetos
2. **Reporte bugs** via GitHub Issues
3. **Sugira melhorias** via GitHub Discussions
4. **Contribua** com Pull Requests

### Para Distribuição
1. **Teste em diferentes dispositivos**
2. **Valide performance**
3. **Atualize documentação**
4. **Publique no CocoaPods**

## 🔧 Troubleshooting

### Problemas Comuns

1. **Import não funciona**
   ```bash
   pod install
   # Limpe e reconstrua o projeto
   ```

2. **Dimensões não aplicam**
   ```swift
   import AppDimens  // Verifique se importou
   16.fxpt          // Confirme a sintaxe
   ```

3. **Performance lenta**
   ```swift
   // Cache dimensões frequentemente usadas
   private let buttonHeight = AppDimens.fixed(44).toPoints()
   ```

## 📞 Suporte

- **GitHub Issues**: [Criar issue](https://github.com/bodenberg/appdimens/issues)
- **Email**: jean.bodenberg@gmail.com
- **Documentação**: [GitHub Wiki](https://github.com/bodenberg/appdimens/wiki)

## 📄 Licença

Este projeto está licenciado sob a Licença Apache 2.0 - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

**AppDimens iOS** - Dimensionamento responsivo feito simples! 🚀

*Convertido com sucesso do projeto Android para iOS, mantendo toda a funcionalidade e adicionando suporte nativo para SwiftUI e UIKit.*
