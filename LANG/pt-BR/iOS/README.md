# 📐 AppDimens para iOS

**Dimensões Responsivas Inteligentes para iOS, iPadOS, tvOS, watchOS**  
*Versão: 2.0.0*

> **Idiomas:** [English](../../../appdimens-ios/README.md) | Português (BR) | [Español](../../es/iOS/README.md)

---

## 🚀 Instalação

### CocoaPods

```ruby
pod 'AppDimens', '~> 2.0.0'
```

### SPM

```
https://github.com/bodenberg/appdimens.git
```

---

## ⚡ Início Rápido

### SwiftUI

```swift
struct MinhaView: View {
    var body: some View {
        VStack(spacing: AppDimens.shared.balanced(16).toPoints()) {
            Text("Olá Mundo")
                .font(.system(size: AppDimens.shared.balanced(18).toPoints()))
            
            Button("Clique") { }
                .frame(height: AppDimens.shared.balanced(48).toPoints())
        }
        .padding(AppDimens.shared.balanced(16).toPoints())
    }
}
```

---

## 🎯 Estratégias

- **BALANCED** ⭐ (primária)
- **DEFAULT** (secundária)
- **LOGARITHMIC**, **POWER**, **FLUID**
- E mais 8 estratégias

---

**Repositório:** https://github.com/bodenberg/appdimens
