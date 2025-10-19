# 📦 AppDimens iOS - Guía de Instalación

> Idiomas: [English](../../../iOS/INSTALLATION.md) | [Português (BR)](../../pt-BR/iOS/INSTALLATION.md) | [हिन्दी](../../hi/iOS/INSTALLATION.md) | [Русский](../../ru/iOS/INSTALLATION.md) | [中文](../../zh/iOS/INSTALLATION.md) | [日本語](../../ja/iOS/INSTALLATION.md)

Esta guía completa le ayudará a instalar e integrar AppDimens iOS en su proyecto con instrucciones paso a paso.

## 📋 Requisitos

- iOS 13.0+
- Swift 5.0+
- Xcode 12.0+

## 🚀 Métodos de Instalación

### CocoaPods (Recomendado)

CocoaPods es el método de instalación recomendado para AppDimens iOS.

#### 1. Agregar a su Podfile

```ruby
platform :ios, '13.0'
use_frameworks!

target 'SuApp' do
  pod 'AppDimens'
end
```

#### 2. Instalar el pod

```bash
pod install
```

#### 3. Abrir el workspace

```bash
open SuApp.xcworkspace
```

#### 4. Importar en sus archivos Swift

```swift
import AppDimens
```

#### Configuración Avanzada de CocoaPods

Para configuración avanzada, puede especificar opciones adicionales en su Podfile:

```ruby
platform :ios, '13.0'
use_frameworks!

target 'SuApp' do
  pod 'AppDimens', '~> 1.0'
end

# Post-instalación para ajustes adicionales
post_install do |installer|
  installer.pods_project.targets.each do |target|
    target.build_configurations.each do |config|
      config.build_settings['IPHONEOS_DEPLOYMENT_TARGET'] = '13.0'
    end
  end
end
```

### Swift Package Manager

Swift Package Manager es la herramienta oficial de gestión de dependencias de Apple.

#### Método 1: Interfaz de Xcode

1. En Xcode:
   - File → Add Package Dependencies
   - Ingrese: `https://github.com/bodenberg/appdimens.git`
   - Seleccione la versión: `1.0.5` o superior
   - Agregar a su target

2. Importar en sus archivos Swift:
```swift
import AppDimens
```

#### Método 2: Package.swift

Agregue a su archivo `Package.swift`:

```swift
dependencies: [
    .package(url: "https://github.com/bodenberg/appdimens.git", from: "1.0.5")
]
```

#### Método 3: Configuración del Proyecto en Xcode

1. Seleccione su proyecto en Xcode
2. Vaya a Package Dependencies
3. Haga clic en el botón "+"
4. Ingrese: `https://github.com/bodenberg/appdimens.git`
5. Seleccione la versión y agregue al target

### Instalación Manual

Si prefiere incluir el código fuente directamente en su proyecto:

#### 1. Descargar el código fuente

```bash
git clone https://github.com/bodenberg/appdimens.git
```

#### 2. Copiar la carpeta Sources

- Copie `Sources/AppDimens/` a su proyecto
- Agregue todos los archivos Swift a su proyecto Xcode

#### 3. Importar en sus archivos Swift

```swift
import AppDimens
```

## 🔧 Configuración

### Build Settings

Asegúrese de que su proyecto tenga las siguientes configuraciones:

- iOS Deployment Target: 13.0 o superior
- Swift Language Version: Swift 5
- Enable Bitcode: No (si usa CocoaPods)

### Configuración del Proyecto

#### Para CocoaPods

Después de ejecutar `pod install`, su proyecto debe configurarse automáticamente. El Podfile manejará:

- Vinculación de frameworks
- Configuraciones de build
- Dependencias del target

#### Para Swift Package Manager

El paquete se configurará automáticamente cuando se agregue desde Xcode. Verifique:

- La membresía correcta del target
- Framework vinculado a su target
- Importaciones agregadas donde sea necesario

#### Para Instalación Manual

1. Agregue todos los archivos Swift a su target
2. Verifique la membresía correcta del target
3. Agregue importaciones donde sea necesario

## 🎯 Inicio Rápido

### Uso Básico

Después de la instalación, puede comenzar a usar AppDimens inmediatamente:

```swift
import AppDimens

// Escalado fijo (recomendado para elementos de UI)
let buttonHeight = AppDimens.fixed(48).toPoints()
let padding = 16.fxpt

// Escalado dinámico (para layouts proporcionales)
let cardWidth = AppDimens.dynamic(200).toPoints()
let containerWidth = 300.dypt
```

### Integración con SwiftUI

```swift
import SwiftUI
import AppDimens

struct ContentView: View {
    var body: some View {
        VStack {
            Text("Hola, mundo")
                .font(.fxSystem(size: 16))
                .fxPadding(16)
            
            Rectangle()
                .fxFrame(width: 200, height: 100)
                .fxCornerRadius(8)
        }
    }
}
```

### Integración con UIKit

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

## 🔧 Solución de Problemas

### Problemas Comunes

#### 1. Error de Importación
```
No such module 'AppDimens'
```

Soluciones:
- Asegúrese de haber ejecutado `pod install` después de agregar la dependencia
- Limpie y reconstruya el proyecto (Product → Clean Build Folder)
- Verifique que el target incluya el framework AppDimens
- Verifique que el framework esté correctamente vinculado en la configuración del target

#### 2. Errores de Build
```
Use of unresolved identifier 'fxpt'
```

Soluciones:
- Asegúrese de haber importado AppDimens: `import AppDimens`
- Verifique que esté usando la sintaxis correcta: `16.fxpt`
- Verifique que el framework esté instalado y vinculado correctamente

#### 3. Problemas con CocoaPods
```
[!] Unable to find a specification for 'AppDimens'
```

Soluciones:
- Actualice CocoaPods: `sudo gem install cocoapods`
- Actualice su repositorio de pods: `pod repo update`
- Intente instalar nuevamente: `pod install`
- Verifique su conexión a Internet

#### 4. Problemas con Swift Package Manager
```
Failed to resolve package dependencies
```

Soluciones:
- Verifique su conexión a Internet
- Verifique que la URL del repositorio sea correcta
- Intente limpiar la caché de paquetes en Xcode
- Reinicie Xcode e inténtelo de nuevo

#### 5. Conflictos de Versión
```
Multiple commands produce the same file
```

Soluciones:
- Limpie la carpeta de build
- Elimine derived data
- Reinicie Xcode
- Verifique frameworks duplicados

### Pasos de Depuración

Si aún tiene problemas, pruebe estos pasos:

1. Verificar Instalación:
   ```swift
   import AppDimens
   print("AppDimens version: \(AppDimens.version)")
   ```

2. Verificar Framework:
   - Verifique que AppDimens aparezca en el navegador del proyecto
   - Verifique que esté agregado a "Frameworks, Libraries, and Embedded Content" de su target

3. Limpiar y Reconstruir:
   - Product → Clean Build Folder
   - Elimine derived data
   - Reinicie Xcode
   - Compile de nuevo

4. Verificar Dependencias:
   - Asegúrese de que todos los frameworks necesarios estén instalados
   - Verifique que el iOS Deployment Target sea 13.0+

## 🔄 Migración desde Android

Si migra desde la versión de Android de AppDimens, vea la [Guía de Migración](MIGRATION.md) para instrucciones detalladas.

### Referencia Rápida de Migración

| Android | iOS |
|---------|-----|
| `16.fxdp` | `16.fxpt` |
| `100.dydp` | `100.dypt` |
| `AppDimens.fixed(16).toPx()` | `AppDimens.fixed(16).toPixels()` |
| `ScreenType.LOWEST` | `ScreenType.lowest` |

## 📚 Próximos Pasos

Después de una instalación exitosa:

1. Lea la documentación: [README.md](README.md)
2. Pruebe los ejemplos: [Examples/](Examples/)
3. Explore la API: [DOCUMENTATION.md](DOCUMENTATION.md)
4. Únase a la comunidad: [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)

## 🆘 Ayuda

Si encuentra problemas que no estén cubiertos aquí:

1. Revise la documentación: [README.md](README.md)
2. Revise los ejemplos: [Examples/](Examples/)
3. Cree un issue: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia Apache 2.0 - vea el archivo [LICENSE](LICENSE) para más detalles.

---

**AppDimens iOS** - ¡Diseño responsivo hecho simple! 🚀
