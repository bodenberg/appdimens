---
layout: default
title: "📐 AppDimens React Native"
---

> **Idiomas:** [English](../../ReactNative/README.md) | Português (BR) | [Español](../es/ReactNative/README.md)

# 📐 AppDimens React Native

> **Dimensionamento Inteligente e Responsivo para React Native**

Escalado matematicamente responsivo que garante que seu design de UI se adapte perfeitamente a qualquer tamanho ou proporção de tela — de telefones a tablets, TVs e wearables.

[![Version](https://img.shields.io/badge/version-1.0.8-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../LICENSE)
[![Platform](https://img.shields.io/badge/platform-React%20Native-orange.svg)](https://reactnative.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-4.8+-blue.svg)](https://www.typescriptlang.org/)

---

## 🎯 O que é AppDimens React Native?

**AppDimens React Native** é um sistema de dimensionamento abrangente que substitui valores de pixel fixos por dimensões escaladas inteligentemente com base nas características reais da tela. Enquanto as dimensões padrão do React Native são constantes, AppDimens as trata como valores base que escalam previsivelmente em diferentes tamanhos de tela, densidades e proporções.

### 🎨 Principais Benefícios

- **🎯 Consistência Visual**: Mantenha proporções perfeitas em todos os tipos de dispositivos
- **📱 Compatibilidade Universal**: Funciona perfeitamente em telefones, tablets, TVs e wearables
- **⚡ Desempenho Otimizado**: Overhead mínimo de runtime com cálculos em cache
- **🔧 Integração Fácil**: Hooks React simples e suporte TypeScript
- **📐 Precisão Matemática**: Dois modelos de escalado - **Fixed (RECOMENDADO)** para a maioria dos casos & Dynamic para necessidades específicas
- **🎮 Desenvolvimento de Jogos**: Escalado especializado para elementos de UI de jogos
- **🚀 TypeScript**: Suporte completo TypeScript com definições de tipo abrangentes

---

## 📋 Requisitos Mínimos

| Requisito | Versão Mínima | Recomendado |
|-----------|---------------|-------------|
| **React Native** | 0.68.0 | 0.73.0+ |
| **React** | 16.8.0 | 18.2.0+ |
| **TypeScript** | 4.8.0+ | 5.3.0+ |
| **Node.js** | 16.0.0 | 20.0.0+ |

### Plataformas Suportadas

| Plataforma | Versão Mín. | Recursos Especiais | Suporte Nativo |
|------------|-------------|-------------------|----------------|
| **iOS** | 11.0+ | Integração Metal | Módulos nativos |
| **Android** | API 21+ | Suporte OpenGL ES | Módulos nativos |
| **Web** | Navegadores modernos | Integração CSS | APIs DOM |

---

## 🚀 Início Rápido

### Instalação

```bash
npm install appdimens-react-native
# ou
yarn add appdimens-react-native
```

### Uso Básico

```tsx
import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useAppDimens, AppDimensProvider } from 'appdimens-react-native';

function MyComponent() {
  const { fx, dy, deviceInfo } = useAppDimens();
  
  return (
    <View style={styles.container}>
      <Text style={styles.title}>App Responsivo</Text>
      <View style={styles.card} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: fx(16).toPixels(), // Padding fixo (RECOMENDADO)
  },
  title: {
    fontSize: fx(24).toPixels(), // Tamanho de fonte fixo (RECOMENDADO)
    marginBottom: fx(20).toPixels(), // Margem fixa (RECOMENDADA)
  },
  card: {
    width: fx(300).toPixels(), // Largura fixa (RECOMENDADO)
    height: fx(200).toPixels(), // Altura fixa (RECOMENDADO)
    backgroundColor: '#007AFF',
    borderRadius: fx(12).toPixels(), // Border radius fixo (RECOMENDADO)
  },
});

function App() {
  return (
    <AppDimensProvider
      config={{
        aspectRatioEnabled: true,
        cacheEnabled: true,
      }}
    >
      <MyComponent />
    </AppDimensProvider>
  );
}

export default App;
```

---

## 📖 Conceitos Principais

### 🎯 Fixed (FX) - RECOMENDADO

Escalado **logarítmico** que fornece crescimento suave e controlado. **Use para a maioria dos elementos de UI**.

```tsx
const { fx } = useAppDimens();

// Elementos de UI (RECOMENDADO)
<View style={{
  width: fx(100).toPixels(),      // ✅ Fixed
  height: fx(100).toPixels(),     // ✅ Fixed
  padding: fx(16).toPixels(),     // ✅ Fixed
  borderRadius: fx(8).toPixels(), // ✅ Fixed
}} />

// Texto (RECOMENDADO)
<Text style={{
  fontSize: fx(16).toPixels(),     // ✅ Fixed
  lineHeight: fx(24).toPixels(),   // ✅ Fixed
}} />
```

### 🎯 Dynamic (DY) - Use Apenas para Casos Específicos

Escalado **proporcional** que cresce agressivamente com o tamanho da tela. **Use apenas para containers grandes ou elementos que devem ocupar porcentagem fixa da tela**.

```tsx
const { dy } = useAppDimens();

// Containers grandes (casos específicos)
<View style={{
  width: dy(300).toPixels(),  // ⚠️ Dynamic - use apenas quando necessário
  height: dy(200).toPixels(), // ⚠️ Dynamic - use apenas quando necessário
}} />
```

### 🎯 Fluid (FL) - Transições Suaves

Escalado **clamp-based** que garante transições suaves entre breakpoints. **Ideal para tipografia e layouts adaptativos**.

```tsx
const { fl } = useAppDimens();

// Texto com transições suaves
<Text style={{
  fontSize: fl(14, { min: 12, max: 18 }).toPixels(),
}} />
```

---

## 🎮 Desenvolvimento de Jogos

### Escalado para UI de Jogos

```tsx
import { useGameDimens } from 'appdimens-react-native';

function GameUI() {
  const { scaleForGame, deviceInfo } = useGameDimens();
  
  const buttonSize = scaleForGame({
    baseWidth: 100,
    baseHeight: 50,
    aspectRatio: 16 / 9,
  });
  
  return (
    <View style={{
      width: buttonSize.width,
      height: buttonSize.height,
    }}>
      <Text>Play</Text>
    </View>
  );
}
```

---

## 🔧 Configuração Avançada

### Provider Global

```tsx
import { AppDimensProvider } from 'appdimens-react-native';

function App() {
  return (
    <AppDimensProvider
      config={{
        aspectRatioEnabled: true,
        cacheEnabled: true,
        ignoreMultiWindowAdjustment: false,
        referenceWidth: 300,
        referenceAspectRatio: 16 / 9,
      }}
    >
      <YourApp />
    </AppDimensProvider>
  );
}
```

### Valores Personalizados por Dispositivo

```tsx
const { fx } = useAppDimens();

const responsiveWidth = fx(100)
  .forDevice('tablet', 150)
  .forDevice('tv', 200)
  .toPixels();
```

---

## 📊 Performance

- **⚡ Cache Inteligente**: Cálculos são cacheados automaticamente
- **🚀 Overhead Mínimo**: ~0.1ms por cálculo (cacheado: ~0.001ms)
- **💾 Uso de Memória**: <1MB para cache completo
- **🔄 Recomposição Otimizada**: Evita recálculos desnecessários

---

## 📚 API Completa

### Hooks

- **useAppDimens()**: Hook principal para escalado
- **useGameDimens()**: Hook especializado para jogos
- **useDeviceInfo()**: Informações sobre o dispositivo atual

### Métodos

- **fx(value)**: Escalado Fixed (RECOMENDADO)
- **dy(value)**: Escalado Dynamic (casos específicos)
- **fl(value, options)**: Escalado Fluid (tipografia)
- **toPixels()**: Converte para pixels
- **toDp()**: Converte para DP
- **forDevice(type, value)**: Valor customizado por dispositivo

---

## 🤝 Contribuição

Contribuições são bem-vindas! Por favor, leia o [CONTRIBUTING.md](../../CONTRIBUTING.md) para detalhes.

## 📄 Licença

Apache 2.0 - veja [LICENSE](../../LICENSE) para detalhes.

## 📞 Suporte

- **Issues**: [GitHub Issues](https://github.com/bodenberg/appdimens/issues)
- **Discussions**: [GitHub Discussions](https://github.com/bodenberg/appdimens/discussions)
- **Email**: [contato@bodenberg.dev](mailto:contato@bodenberg.dev)

---

**Desenvolvido com ❤️ por [Jean Bodenberg](https://github.com/bodenberg)**
