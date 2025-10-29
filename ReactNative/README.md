---
layout: default
title: "📐 AppDimens React Native"
---

# 📐 AppDimens React Native

> **Smart and Responsive Dimensioning for React Native**

Mathematically responsive scaling that ensures your UI design adapts perfectly to any screen size or aspect ratio — from phones to tablets, TVs, and wearables.

[![Version](https://img.shields.io/badge/version-1.0.9-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-React%20Native-orange.svg)](https://reactnative.dev/)
[![TypeScript](https://img.shields.io/badge/TypeScript-4.8+-blue.svg)](https://www.typescriptlang.org/)

---

## 🎯 What is AppDimens React Native?

**AppDimens React Native** is a comprehensive dimensioning system that replaces fixed pixel values with intelligently scaled dimensions based on actual screen characteristics. While React Native's default dimensions are constant, AppDimens treats them as base values that scale predictably across different screen sizes, densities, and aspect ratios.

### 🎨 Key Benefits

- **🎯 Visual Consistency**: Maintain perfect proportions across all device types
- **📱 Universal Compatibility**: Works seamlessly on phones, tablets, TVs, and wearables
- **⚡ Performance Optimized**: Minimal runtime overhead with cached calculations
- **🔧 Easy Integration**: Simple React hooks and TypeScript support
- **📐 Mathematical Precision**: Two scaling models - **Fixed (RECOMMENDED)** for most cases & Dynamic for specific needs
- **🎮 Game Development**: Specialized scaling for game UI elements
- **🚀 TypeScript**: Full TypeScript support with comprehensive type definitions

---

## 📋 Requisitos Mínimos

| Requisito | Versão Mínima | Recomendado |
|-----------|---------------|-------------|
| **React Native** | 0.68.0 | 0.73.0+ |
| **React** | 16.8.0 | 18.2.0+ |
| **TypeScript** | 4.8.0+ | 5.3.0+ |
| **Node.js** | 16.0.0 | 20.0.0+ |

### Plataformas Suportadas

| Plataforma | Min Version | Special Features | Native Support |
|------------|-------------|------------------|----------------|
| **iOS** | 11.0+ | Metal integration | Native modules |
| **Android** | API 21+ | OpenGL ES support | Native modules |
| **Web** | Navegadores modernos | CSS integration | DOM APIs |

---

## 🚀 Quick Start

### Installation

```bash
npm install appdimens-react-native
# or
yarn add appdimens-react-native
```

### Basic Usage

```tsx
import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useAppDimens, AppDimensProvider } from 'appdimens-react-native';

function MyComponent() {
  const { fx, dy, deviceInfo } = useAppDimens();
  
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Responsive App</Text>
      <View style={styles.card} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: fx(16).toPixels(), // Fixed padding (RECOMMENDED)
  },
  title: {
    fontSize: fx(24).toPixels(), // Fixed font size (RECOMMENDED)
    marginBottom: fx(20).toPixels(), // Fixed margin (RECOMMENDED)
  },
  card: {
    width: fx(300).toPixels(), // Fixed width (RECOMMENDED)
    height: fx(200).toPixels(), // Fixed height (RECOMMENDED)
    backgroundColor: '#007AFF',
    borderRadius: fx(12).toPixels(), // Fixed border radius (RECOMMENDED)
  },
});

function App() {
  return (
    <AppDimensProvider>
      <MyComponent />
    </AppDimensProvider>
  );
}
```

---

## 🧠 Core Dimension Models

| Model | Philosophy | Ideal Use Case | Implementation |
|-------|------------|----------------|----------------|
| **Fixed (FX)** ⭐ **RECOMMENDED** | Logarithmic scaling (refined & balanced) | **Most UI elements**: buttons, paddings, margins, icons, fonts, containers, cards | `fx(value).toPixels()` |
| **Dynamic (DY)** | Proportional scaling (aggressive) | **Specific cases**: large containers, full-width grids, viewport-dependent elements | `dy(value).toPixels()` |
| **Percentage** | Screen-based percentage | Layout containers (use sparingly) | `percentage(0.8)` |

---

## 🎨 Usage Examples

### 🧩 Basic Responsive Design

```tsx
import React from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { useAppDimens } from 'appdimens-react-native';

function ResponsiveCard() {
  const { fx, dy } = useAppDimens();
  
  return (
    <View style={styles.card}>
      <Text style={styles.title}>Responsive Title</Text>
      <Text style={styles.subtitle}>This card adapts to any screen size</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    width: fx(300).toPixels(),           // Fixed width (RECOMMENDED)
    height: fx(200).toPixels(),          // Fixed height (RECOMMENDED)
    padding: fx(16).toPixels(),          // Fixed padding (RECOMMENDED)
    backgroundColor: '#FFFFFF',
    borderRadius: fx(12).toPixels(),     // Fixed border radius (RECOMMENDED)
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: fx(2).toPixels(),          // Fixed shadow offset (RECOMMENDED)
    },
    shadowOpacity: 0.1,
    shadowRadius: fx(4).toPixels(),      // Fixed shadow radius
    elevation: fx(3).toPixels(),         // Fixed elevation (Android)
  },
  title: {
    fontSize: fx(18).toPixels(),         // Fixed font size
    fontWeight: 'bold',
    marginBottom: fx(8).toPixels(),      // Fixed margin
  },
  subtitle: {
    fontSize: fx(14).toPixels(),         // Fixed font size
    color: '#666666',
    lineHeight: fx(20).toPixels(),       // Fixed line height
  },
});
```

### 🔄 Conditional Scaling

```tsx
function ConditionalScaling() {
  const { fx, deviceInfo } = useAppDimens();
  
  const buttonSize = fx(80)
    .screen('tablet', 120)        // 120px for tablets
    .screen('phone', 60)          // 60px for phones
    .screen('watch', 40)          // 40px for watches
    .toPixels();
  
  return (
    <View style={[styles.button, { width: buttonSize, height: buttonSize }]}>
      <Text>Button</Text>
    </View>
  );
}
```

### 📏 Physical Units

```tsx
function PhysicalUnits() {
  const { fx, mm, cm, inch } = useAppDimens();
  
  return (
    <View style={styles.container}>
      {/* 5 millimeters */}
      <View style={[styles.bar, { width: mm(5) }]} />
      
      {/* 2 centimeters */}
      <View style={[styles.bar, { width: cm(2) }]} />
      
      {/* 1 inch */}
      <View style={[styles.bar, { width: inch(1) }]} />
    </View>
  );
}
```

### 🧮 Layout Utilities

```tsx
function ResponsiveGrid() {
  const { fx, dy, calculateAvailableItemCount, deviceInfo } = useAppDimens();
  const [spanCount, setSpanCount] = useState(3);
  
  useEffect(() => {
    const containerWidth = deviceInfo.screenDimensions.width;
    const itemSize = dy(100).toPixels();
    const itemMargin = fx(8).toPixels();
    
    const count = calculateAvailableItemCount(containerWidth, itemSize, itemMargin);
    if (count > 0) {
      setSpanCount(count);
    }
  }, [deviceInfo.screenDimensions.width]);
  
  return (
    <FlatList
      data={Array.from({ length: 20 }, (_, i) => ({ id: i }))}
      numColumns={spanCount}
      renderItem={({ item }) => (
        <View style={styles.gridItem} />
      )}
      contentContainerStyle={styles.grid}
    />
  );
}
```

### 🎮 Game Development

```tsx
function GameUI() {
  const { fx, dy, deviceInfo } = useAppDimens();
  
  // Game-specific dimensions
  const buttonSize = fx(48).screen('tablet', 64).toPixels();
  const playerSize = fx(64).screen('phone', 48).toPixels();
  const uiOverlaySize = fx(24).screen('watch', 16).toPixels();
  
  return (
    <View style={styles.gameContainer}>
      <View style={[styles.player, { width: playerSize, height: playerSize }]} />
      <View style={[styles.uiOverlay, { padding: uiOverlaySize }]}>
        <TouchableOpacity style={[styles.gameButton, { width: buttonSize, height: buttonSize }]}>
          <Text style={styles.buttonText}>Jump</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
}
```

---

## 🔧 Advanced Features

### 🎯 Design System Integration

```tsx
import { AppDimensProvider, DesignSystem } from 'appdimens-react-native';

const designSystem: DesignSystem = {
  name: 'MyApp Design System',
  version: '1.0.0',
  tokens: {
    spacing: {
      xs: { value: 4, type: 'fixed' },
      sm: { value: 8, type: 'fixed' },
      md: { value: 16, type: 'fixed' },
      lg: { value: 24, type: 'fixed' },
      xl: { value: 32, type: 'fixed' },
    },
    sizing: {
      button: { value: 48, type: 'fixed' },
      card: { value: 300, type: 'dynamic' },
      container: { value: 0.9, type: 'percentage' },
    },
  },
  rules: {
    fixed: ['spacing.*', 'button.*', 'border.*'],
    dynamic: ['card.*', 'container.*'],
    percentage: ['layout.*'],
    custom: [],
  },
};

function App() {
  return (
    <AppDimensProvider designSystem={designSystem}>
      <MyApp />
    </AppDimensProvider>
  );
}
```

### ⚡ Performance Configuration

```tsx
import { AppDimensProvider, PerformanceConfig } from 'appdimens-react-native';

const performanceConfig: PerformanceConfig = {
  enableCache: true,
  cacheSize: 1000,
  enablePerformanceMonitoring: true,
  logLevel: 'info',
};

function App() {
  return (
    <AppDimensProvider performanceConfig={performanceConfig}>
      <MyApp />
    </AppDimensProvider>
  );
}
```

### 🔍 Debugging and Monitoring

```tsx
function DebugPanel() {
  const { deviceInfo, screenDimensions, clearCache } = useAppDimens();
  const appDimens = useAppDimensContext();
  
  const performanceMetrics = appDimens.appDimens.getPerformanceMetrics();
  
  return (
    <View style={styles.debugPanel}>
      <Text>Device: {deviceInfo.type}</Text>
      <Text>Platform: {deviceInfo.platform}</Text>
      <Text>Screen: {screenDimensions.width} × {screenDimensions.height}</Text>
      <Text>Orientation: {deviceInfo.orientation}</Text>
      <Text>Cache Size: {performanceMetrics.cacheSize}</Text>
      <TouchableOpacity onPress={clearCache}>
        <Text>Clear Cache</Text>
      </TouchableOpacity>
    </View>
  );
}
```

---

## 📊 Performance & Compatibility

### ⚡ Performance Characteristics

| Feature | Runtime Overhead | Memory Usage | Calculation Time |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cached per configuration |
| **Physical Units** | ~0.002ms | ~10KB | On-demand |
| **Cache System** | ~0.0001ms | ~100KB | Automatic invalidation |

### 📱 Platform Support

| Platform | Min Version | Special Features | Native Support |
|----------|-------------|------------------|----------------|
| **iOS** | 11.0+ | Metal integration | Native modules |
| **Android** | API 21+ | OpenGL ES support | Native modules |
| **Web** | All modern browsers | CSS integration | DOM APIs |

---

## 📚 API Reference

### 🎯 Core Hooks

#### `useAppDimens()`

Main hook for using AppDimens in components.

```tsx
const {
  fx,                    // Fixed dimension builder
  dy,                    // Dynamic dimension builder
  percentage,            // Percentage calculator
  deviceInfo,            // Current device information
  screenDimensions,      // Current screen dimensions
  calculateAvailableItemCount, // Layout utility
  mm, cm, inch,         // Physical unit converters
  clearCache,            // Cache management
  isCacheEnabled,        // Cache status
} = useAppDimens();
```

#### `useAppDimensContext()`

Hook for accessing AppDimens context.

```tsx
const {
  designSystem,          // Current design system
  performanceConfig,     // Performance configuration
  appDimens,            // Core AppDimens instance
} = useAppDimensContext();
```

### 🔧 Core Classes

#### `AppDimens`

Main class for dimension management.

```tsx
import { appDimens } from 'appdimens-react-native';

// Create builders
const fixedBuilder = appDimens.fixed(16);
const dynamicBuilder = appDimens.dynamic(100);

// Calculate percentage
const width = appDimens.percentage(0.8); // 80% of screen width

// Physical units
const margin = appDimens.mm(5);    // 5 millimeters
const padding = appDimens.cm(2);   // 2 centimeters
const border = appDimens.inch(1);  // 1 inch

// Utilities
const itemCount = appDimens.calculateAvailableItemCount(containerWidth, itemSize, margin);

// Device info
const deviceInfo = appDimens.getDeviceInfo();
const screenDimensions = appDimens.getScreenDimensions();

// Cache management
appDimens.setGlobalCache(true);
appDimens.clearAllCaches();
```

#### `AppDimensFixed`

Fixed dimension builder with logarithmic scaling.

```tsx
const fixed = appDimens.fixed(16)
  .screen('tablet', 20)           // Custom value for tablets
  .screen('phone', 14)            // Custom value for phones
  .aspectRatio(true, 0.5)         // Enable aspect ratio adjustment
  .type('highest')                // Use highest screen dimension
  .cache(true);                   // Enable caching

const pixels = fixed.toPixels();      // Get value in pixels
const points = fixed.toPoints();      // Get value in points
```

#### `AppDimensDynamic`

Dynamic dimension builder with proportional scaling.

```tsx
const dynamic = appDimens.dynamic(100)
  .screen('tablet', 150)          // Custom value for tablets
  .screen('phone', 80)            // Custom value for phones
  .type('lowest')                 // Use lowest screen dimension
  .cache(true);                   // Enable caching

const pixels = dynamic.toPixels();    // Get value in pixels
const points = dynamic.toPoints();    // Get value in points
```

### 🛠️ Utilities

#### `AppDimensUtils`

Utility functions for common operations.

```tsx
import { AppDimensUtils } from 'appdimens-react-native';

// Scaling utilities
const scaled = AppDimensUtils.scale(16);                    // Scale dimension
const verticalScaled = AppDimensUtils.verticalScale(16);    // Vertical scale
const moderateScaled = AppDimensUtils.moderateScale(16, 0.5); // Moderate scale

// Responsive typography
const fontSize = AppDimensUtils.responsiveFontSize(16);     // Responsive font size
const lineHeight = AppDimensUtils.responsiveLineHeight(16); // Responsive line height

// Device utilities
const isLandscape = AppDimensUtils.isLandscape();           // Check orientation
const aspectRatio = AppDimensUtils.getAspectRatio();        // Get aspect ratio
const deviceWidth = AppDimensUtils.getDeviceWidth();        // Get device width

// Layout utilities
const itemSize = AppDimensUtils.calculateGridItemSize(containerWidth, 3, 8);
const columns = AppDimensUtils.calculateGridColumns(containerWidth, itemSize, 8);

// Conversion utilities
const percentage = AppDimensUtils.percentageToPixels(50, 'width'); // 50% of width
const pixels = AppDimensUtils.pixelsToPercentage(100, 'width');    // 100px as percentage

// Math utilities
const clamped = AppDimensUtils.clamp(value, 0, 100);        // Clamp value
const rounded = AppDimensUtils.roundToNearest(value, 5);    // Round to nearest 5
const interpolated = AppDimensUtils.interpolate(value, [0, 100], [0, 200]);
```

---

## 🧪 Testing

### 📋 Test Coverage

- ✅ Dimension calculations
- ✅ Device type detection
- ✅ Screen factor calculations
- ✅ React hooks
- ✅ Context providers
- ✅ Edge cases and error handling
- ✅ Performance benchmarks

### 🔧 Testing Tools

```tsx
import { renderHook } from '@testing-library/react-native';
import { useAppDimens, AppDimensProvider } from 'appdimens-react-native';

// Test hook
const { result } = renderHook(() => useAppDimens(), {
  wrapper: AppDimensProvider,
});

// Test dimension calculations
expect(result.current.fx(16).toPixels()).toBeGreaterThan(0);
expect(result.current.dy(100).toPixels()).toBeGreaterThan(0);

// Test device info
expect(result.current.deviceInfo.type).toBeDefined();
expect(result.current.screenDimensions.width).toBeGreaterThan(0);
```

---

## 🤝 Contributing

We welcome contributions! Please see our [Contributing Guidelines](../CONTRIBUTING.md) for details.

### 🐛 Found a Bug?
- [Create an issue](https://github.com/bodenberg/appdimens/issues)
- Include device information and reproduction steps
- Attach screenshots if applicable

### 💡 Have an Idea?
- [Start a discussion](https://github.com/bodenberg/appdimens/discussions)
- Propose new features or improvements
- Share your use cases

---

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](../LICENSE) file for details.

---

## 👨‍💻 Author

**Jean Bodenberg**
- 🌐 [GitHub](https://github.com/bodenberg)

---

## 🌟 Show Your Support

If AppDimens React Native has helped your project, please consider:

- ⭐ **Starring** this repository
- 🐦 **Sharing** on social media
- 📝 **Writing** a review or blog post
- 🤝 **Contributing** code or documentation

---

<div align="center">
    <p><strong>Made with ❤️ for the React Native development community</strong></p>
    <p>AppDimens React Native - Where responsive design meets mathematical precision</p>
</div>
