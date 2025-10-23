# AppDimens Flutter

[![pub package](https://img.shields.io/pub/v/appdimens.svg)](https://pub.dev/packages/appdimens)
[![pub points](https://img.shields.io/pub/points/appdimens?logo=dart)](https://pub.dev/packages/appdimens/score)
[![popularity](https://img.shields.io/pub/popularity/appdimens?logo=dart)](https://pub.dev/packages/appdimens/score)
[![likes](https://img.shields.io/pub/likes/appdimens?logo=dart)](https://pub.dev/packages/appdimens/score)

**AppDimens Flutter** é uma biblioteca de dimensionamento responsivo que ajusta automaticamente pixels lógicos, pixels escaláveis e unidades físicas de forma responsiva e matematicamente refinada, garantindo consistência de layout em qualquer tamanho ou proporção de tela.

## 🚀 Características Principais

- **Dimensionamento Fixo (FX)**: Escalonamento logarítmico para elementos de UI como botões, paddings, margens e ícones
- **Dimensionamento Dinâmico (DY)**: Escalonamento proporcional para containers, grids e fontes fluidas
- **Unidades Físicas**: Conversão de medidas reais (mm, cm, polegadas) para pixels da tela
- **Qualificadores Condicionais**: Valores personalizados baseados em modo de UI, tipo de dispositivo e qualificadores de tela
- **Cache Inteligente**: Sistema de cache otimizado para performance
- **Ajuste Multi-Window**: Opção para ignorar ajustes quando o app está em modo multi-window
- **Extensões Convenientes**: Extensões para widgets Flutter para uso simplificado

## 📋 Requisitos Mínimos

| Requisito | Versão Mínima | Recomendado |
|-----------|---------------|-------------|
| **Dart SDK** | 3.0.0 | 3.5.0+ |
| **Flutter SDK** | 3.10.0 | 3.24.0+ |

### Plataformas Suportadas

| Plataforma | Suporte | Observações |
|------------|---------|-------------|
| **Android** | ✅ | API 21+ |
| **iOS** | ✅ | iOS 12.0+ |
| **Web** | ✅ | Todos os navegadores modernos |
| **Windows** | ✅ | Windows 10+ |
| **macOS** | ✅ | macOS 10.14+ |
| **Linux** | ✅ | |

---

## 📦 Instalação

Adicione ao seu `pubspec.yaml`:

```yaml
dependencies:
  appdimens: ^1.0.8
```

Execute:

```bash
flutter pub get
```

## 🎯 Uso Básico

### 1. Configuração Inicial

```dart
import 'package:appdimens/appdimens.dart';

void main() {
  runApp(
    AppDimensApp(
      config: const AppDimensConfig(
        aspectRatioEnabled: true,
        cacheEnabled: true,
        ignoreMultiWindowAdjustment: false,
      ),
      child: MyApp(),
    ),
  );
}
```

### 2. Dimensionamento Fixo (FX)

```dart
// Uso básico
Container(
  width: 100.fx.calculate(context),
  height: 100.fx.calculate(context),
  decoration: BoxDecoration(
    borderRadius: BorderRadius.circular(16.fx.calculate(context)),
  ),
)

// Com valores personalizados
Container(
  width: AppDimens.fixed(150)
      .deviceType(DeviceType.tablet, 200)
      .deviceType(DeviceType.tv, 300)
      .calculate(context),
  height: AppDimens.fixed(80)
      .uiMode(UiModeType.carPlay, 120)
      .calculate(context),
)
```

### 3. Dimensionamento Dinâmico (DY)

```dart
// Uso básico
Container(
  width: 200.dy.calculate(context),
  height: 100.dy.calculate(context),
)

// Com valores personalizados
Container(
  width: AppDimens.dynamic(300)
      .deviceType(DeviceType.tablet, 400)
      .deviceType(DeviceType.tv, 500)
      .calculate(context),
)
```

### 4. Unidades Físicas

```dart
// Conversão de unidades físicas
Container(
  width: AppDimensPhysicalUnits.mmToPixels(50, context),
  height: AppDimensPhysicalUnits.mmToPixels(25, context),
)

// Tamanho de fonte otimizado
Text(
  'Hello World',
  style: TextStyle(
    fontSize: AppDimensPhysicalUnits.calculateOptimalFontSize(
      5, UnitType.mm, context
    ),
  ),
)
```

### 5. Extensões para Widgets

```dart
// Padding responsivo
Text('Hello')
  .fxPadding(16, context)
  .dyMargin(8, context)
  .fxBorderRadius(12, context);

// Estilo de texto responsivo
Text(
  'Hello World',
  style: TextStyle()
    .fxFontSize(16, context)
    .dyFontSize(18, context),
);
```

## 🎨 Exemplos Avançados

### Grid Responsivo

```dart
GridView.builder(
  gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
    crossAxisCount: 3,
    crossAxisSpacing: 8.fx.calculate(context),
    mainAxisSpacing: 8.fx.calculate(context),
  ),
  itemCount: 9,
  itemBuilder: (context, index) {
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(4.fx.calculate(context)),
      ),
      child: Center(
        child: Text(
          '${index + 1}',
          style: TextStyle(
            fontSize: 20.fx.calculate(context),
          ),
        ),
      ),
    );
  },
)
```

### Botão Responsivo

```dart
ElevatedButton(
  onPressed: () {},
  style: ElevatedButton.styleFrom(
    padding: EdgeInsets.all(16.fx.calculate(context)),
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(8.fx.calculate(context)),
    ),
  ),
  child: Text(
    'Button',
    style: TextStyle(
      fontSize: 16.fx.calculate(context),
    ),
  ),
)
```

### Card Responsivo

```dart
Card(
  margin: EdgeInsets.all(8.fx.calculate(context)),
  shape: RoundedRectangleBorder(
    borderRadius: BorderRadius.circular(12.fx.calculate(context)),
  ),
  child: Padding(
    padding: EdgeInsets.all(16.fx.calculate(context)),
    child: Column(
      children: [
        Text(
          'Title',
          style: TextStyle(
            fontSize: 18.fx.calculate(context),
            fontWeight: FontWeight.bold,
          ),
        ),
        SizedBox(height: 8.fx.calculate(context)),
        Text(
          'Description',
          style: TextStyle(
            fontSize: 14.fx.calculate(context),
          ),
        ),
      ],
    ),
  ),
)
```

## 🔧 Configuração Avançada

### Configuração Global

```dart
// Configuração global
AppDimens.setGlobalAspectRatio(true);
AppDimens.setGlobalCache(true);
AppDimens.setGlobalIgnoreMultiWindowAdjustment(false);
```

### Cache Personalizado

```dart
// Controle de cache por instância
final dimension = AppDimens.fixed(100)
    .cache(true)  // Ativar cache
    .calculate(context);

// Limpar cache
AppDimens.fixed(100).clearCache();
```

### Qualificadores Personalizados

```dart
// Valores baseados em interseção de qualificadores
final dimension = AppDimens.fixed(100)
    .intersection(UiModeType.carPlay, DpQualifier.smallestWidth600, 150)
    .calculate(context);
```

## 📱 Tipos de Dispositivo Suportados

- **Phone**: Smartphones
- **Tablet**: Tablets
- **Watch**: Smartwatches
- **TV**: Smart TVs
- **CarPlay**: Displays automotivos
- **Desktop**: Computadores desktop
- **Foldable**: Dispositivos dobráveis
- **Unknown**: Dispositivos não identificados

## 🎯 Modos de UI Suportados

- **Normal**: Modo normal
- **CarPlay**: Modo automotivo
- **TV**: Modo TV
- **Watch**: Modo relógio
- **Mac**: Modo Mac
- **Unknown**: Modo não identificado

## 📊 Performance

- **Cache Inteligente**: Sistema de cache otimizado para evitar recálculos desnecessários
- **Cálculos Eficientes**: Algoritmos otimizados para performance
- **Memória Otimizada**: Uso eficiente de memória com limpeza automática de cache

## 🔍 Debugging

### Informações da Tela

```dart
// Obter informações atuais da tela
final screenInfo = AppDimens.getCurrentScreenInfo(context);
print('Screen: ${screenInfo.width}x${screenInfo.height}');
print('Device Type: ${screenInfo.deviceType}');
print('UI Mode: ${screenInfo.uiModeType}');
```

### Fatores de Ajuste

```dart
// Calcular fatores de ajuste
final factors = AppDimens.calculateAdjustmentFactors(context);
print('Aspect Ratio Factor: ${factors.aspectRatioFactor}');
print('Density Factor: ${factors.densityFactor}');
print('Device Type Factor: ${factors.deviceTypeFactor}');
```

## 📚 API Reference

### Classes Principais

- **AppDimens**: Classe principal para criar dimensionamentos
- **AppDimensFixed**: Construtor para dimensionamento fixo
- **AppDimensDynamic**: Construtor para dimensionamento dinâmico
- **AppDimensPhysicalUnits**: Utilitários para unidades físicas
- **AppDimensProvider**: Provider para configuração global

### Extensões

- **AppDimensDoubleExtension**: Extensões para valores double
- **AppDimensIntExtension**: Extensões para valores int
- **AppDimensWidgetExtension**: Extensões para widgets
- **AppDimensTextStyleExtension**: Extensões para estilos de texto
- **AppDimensContainerExtension**: Extensões para containers

## 🤝 Contribuição

Contribuições são bem-vindas! Por favor, leia o [CONTRIBUTING.md](../CONTRIBUTING.md) para detalhes sobre como contribuir.

## 📄 Licença

Este projeto está licenciado sob a Licença Apache 2.0 - veja o arquivo [LICENSE](../LICENSE) para detalhes.

## 🙏 Agradecimentos

- Flutter Team pelo framework incrível
- Comunidade Flutter por feedback e contribuições
- Desenvolvedores que testaram e reportaram bugs

## 📞 Suporte

- **Issues**: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
- **Discussions**: [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)
- **Email**: [contato@bodenberg.dev](mailto:contato@bodenberg.dev)

---

**Desenvolvido com ❤️ por [Jean Bodenberg](https://github.com/bodenberg)**