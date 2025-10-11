# AppDimens para iOS - Documentação Completa

## 📋 Índice

1. [Configuração Inicial](#configuração-inicial)
2. [SwiftUI](#swiftui)
3. [UIKit](#uikit)
4. [Exemplos Práticos](#exemplos-práticos)
5. [API Completa](#api-completa)
6. [Migração de Código Existente](#migração-de-código-existente)
7. [Integração com Kotlin Multiplatform](#integração-com-kotlin-multiplatform)
8. [Troubleshooting](#troubleshooting)

## 🚀 Configuração Inicial

### 1. Instalação via Swift Package Manager

```swift
// Package.swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens-ios.git", from: "1.0.0")
]
```

Ou via Xcode:
1. File → Add Package Dependencies
2. Digite: `https://github.com/bodenberg/appdimens-ios.git`
3. Selecione a versão: `1.0.0` ou superior
4. Adicione ao seu target

### 2. Instalação via CocoaPods

```ruby
# Podfile
pod 'AppDimens', '~> 1.0.0'
```

```bash
pod install
```

### 3. Inicialização no AppDelegate

```swift
// AppDelegate.swift
import UIKit
import AppDimens

@main
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    func application(_ application: UIApplication, 
                     didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        // Inicializar AppDimens
        AppDimens.shared.configure { config in
            config.referenceWidth = 375  // iPhone padrão
            config.referenceHeight = 667
            config.debugMode = DEBUG_MODE
            config.defaultQualifier = .smallestWidth
            config.tabletThreshold = 768
        }
        
        return true
    }
}
```

### 4. Configuração para SwiftUI App

```swift
// MyApp.swift
import SwiftUI
import AppDimens

@main
struct MyApp: App {
    
    init() {
        // Configurar AppDimens
        AppDimens.shared.configure { config in
            config.referenceWidth = 375
            config.debugMode = true
        }
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environmentObject(AppDimens.shared)
        }
    }
}
```

## 📱 SwiftUI

### Extensões e Modificadores

```swift
import SwiftUI
import AppDimens

// Extensões para valores responsivos
extension CGFloat {
    /// Fixed scaling (crescimento controlado)
    var fxpt: CGFloat {
        return AppDimens.shared.fxValue(self)
    }
    
    /// Dynamic scaling (crescimento proporcional)
    var dypt: CGFloat {
        return AppDimens.shared.dyValue(self)
    }
    
    /// Conversão para pixels
    var px: CGFloat {
        return self * UIScreen.main.scale
    }
}

// Extensões para Font
extension Font {
    static func fxFont(_ size: CGFloat, weight: Font.Weight = .regular) -> Font {
        return .system(size: size.fxpt, weight: weight)
    }
    
    static func dyFont(_ size: CGFloat, weight: Font.Weight = .regular) -> Font {
        return .system(size: size.dypt, weight: weight)
    }
}

// View Modifiers responsivos
struct ResponsivePadding: ViewModifier {
    let edges: Edge.Set
    let length: CGFloat
    let scaling: ScalingType
    
    enum ScalingType {
        case fixed, dynamic
    }
    
    func body(content: Content) -> some View {
        content.padding(edges, scaling == .fixed ? length.fxpt : length.dypt)
    }
}

extension View {
    func responsivePadding(_ edges: Edge.Set = .all, _ length: CGFloat, scaling: ResponsivePadding.ScalingType = .fixed) -> some View {
        modifier(ResponsivePadding(edges: edges, length: length, scaling: scaling))
    }
}
```

### Componentes Responsivos

```swift
// CartaoResponsivo.swift
struct CartaoResponsivo: View {
    let titulo: String
    let descricao: String
    let imagem: String
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            // Imagem responsiva
            Image(imagem)
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(height: 200.dypt)
                .clipped()
            
            // Conteúdo
            VStack(alignment: .leading, spacing: 8.fxpt) {
                Text(titulo)
                    .font(.fxFont(18, weight: .bold))
                    .foregroundColor(.primary)
                
                Text(descricao)
                    .font(.fxFont(14))
                    .foregroundColor(.secondary)
                    .lineLimit(3)
            }
            .padding(16.fxpt)
        }
        .background(Color(.systemBackground))
        .cornerRadius(12.fxpt)
        .shadow(radius: 4.fxpt)
    }
}
```

### Grid Adaptativo

```swift
struct GridAdaptativo: View {
    let items: [GridItem]
    @EnvironmentObject var appDimens: AppDimens
    
    private var columns: [GridItem] {
        let screenWidth = UIScreen.main.bounds.width
        let itemWidth: CGFloat = 100.fxpt
        let spacing: CGFloat = 16.fxpt
        
        let itemCount = appDimens.calculateAvailableItemCount(
            containerWidth: screenWidth,
            itemWidth: itemWidth,
            spacing: spacing,
            startPadding: 16,
            endPadding: 16
        )
        
        return Array(repeating: GridItem(.flexible(), spacing: spacing), count: itemCount)
    }
    
    var body: some View {
        ScrollView {
            LazyVGrid(columns: columns, spacing: 16.fxpt) {
                ForEach(items) { item in
                    GridItemView(item: item)
                }
            }
            .padding(16.fxpt)
        }
    }
}

struct GridItemView: View {
    let item: GridItem
    
    var body: some View {
        VStack(spacing: 8.fxpt) {
            Image(systemName: item.icon)
                .font(.system(size: 40.dypt))
                .foregroundColor(.blue)
            
            Text(item.title)
                .font(.fxFont(12))
                .multilineTextAlignment(.center)
        }
        .frame(maxWidth: .infinity)
        .frame(height: 100.fxpt)
        .background(Color(.secondarySystemBackground))
        .cornerRadius(8.fxpt)
    }
}
```

### Tela de Login Responsiva

```swift
struct TelaLogin: View {
    @State private var email = ""
    @State private var senha = ""
    @State private var lembrarMe = false
    
    var body: some View {
        VStack(spacing: 0) {
            // Logo
            Image("logo")
                .resizable()
                .scaledToFit()
                .frame(width: 120.dypt, height: 120.dypt)
                .padding(.bottom, 40.fxpt)
            
            // Formulário
            VStack(spacing: 16.fxpt) {
                // Campo Email
                VStack(alignment: .leading, spacing: 8.fxpt) {
                    Text("Email")
                        .font(.fxFont(12, weight: .medium))