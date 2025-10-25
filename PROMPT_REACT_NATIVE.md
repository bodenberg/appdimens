---
layout: default
title: "AI Prompt for AppDimens React Native Library"
---

# AI Prompt for AppDimens React Native Library

> Languages: [Português (BR)](LANG/pt-BR/PROMPT_REACT_NATIVE.md) | [Español](LANG/es/PROMPT_REACT_NATIVE.md) | [हिन्दी](LANG/hi/PROMPT_REACT_NATIVE.md) | [Русский](LANG/ru/PROMPT_REACT_NATIVE.md) | [中文](LANG/zh/PROMPT_REACT_NATIVE.md) | [日本語](LANG/ja/PROMPT_REACT_NATIVE.md)

You are an expert React Native developer AI. Your task is to understand and effectively use the AppDimens React Native library, a sophisticated dimension management system for React Native applications. This guide will provide you with all the necessary information about the library's architecture, functionalities, and use cases.

## 1. Installation / Setup

Github: [AppDimens Repository](https://github.com/Bodenberg/AppDimens)

```bash
npm install appdimens-react-native
# or
yarn add appdimens-react-native
```

## 2. Minimum Requirements

| Requirement | Minimum Version | Recommended |
|------------|-----------------|-------------|
| **React Native** | 0.68.0 | 0.73.0+ |
| **React** | 16.8.0 | 18.2.0+ |
| **TypeScript** | 4.8.0+ | 5.3.0+ |
| **Node.js** | 16.0.0 | 20.0.0+ |

### Supported Platforms

| Platform | Min Version | Special Features | Native Support |
|----------|-------------|------------------|----------------|
| **iOS** | 11.0+ | Metal integration | Native modules |
| **Android** | API 21+ | OpenGL ES support | Native modules |
| **Web** | Modern browsers | CSS integration | DOM APIs |

## 3. Core Concepts

### 3.1. What is AppDimens React Native?

AppDimens React Native is a comprehensive dimensioning system that replaces fixed pixel values with intelligently scaled dimensions based on actual screen characteristics. It provides two main scaling models: Fixed (FX) for subtle logarithmic adjustment and Dynamic (DY) for proportional adjustment.

### 3.2. Scaling Models: Fixed (FX) vs. Dynamic (DY)

| Feature | Fixed (FX) | Dynamic (DY) |
|---------|-----------|--------------|
| **Philosophy** | Logarithmic scaling (refined) | Proportional scaling (aggressive) |
| **Use Case** | Buttons, paddings, margins, icons | Containers, grids, fluid fonts |
| **Implementation** | `fx(value).toPixels()` | `dy(value).toPixels()` |

## 4. How to Use AppDimens

### 4.1. Basic Setup

```tsx
import React from 'react';
import { AppDimensProvider } from 'appdimens-react-native';

function App() {
  return (
    <AppDimensProvider>
      <MyApp />
    </AppDimensProvider>
  );
}
```

### 4.2. Using the Hook

```tsx
import { useAppDimens } from 'appdimens-react-native';

function MyComponent() {
  const { fx, dy, deviceInfo } = useAppDimens();
  
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Responsive App</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    padding: fx(16).toPixels(),
  },
  title: {
    fontSize: fx(24).toPixels(),
    marginBottom: dy(20).toPixels(),
  },
});
```

### 4.3. Fixed Scaling (FX)

```tsx
const { fx } = useAppDimens();

const styles = StyleSheet.create({
  button: {
    width: fx(120).toPixels(),
    height: fx(48).toPixels(),
    borderRadius: fx(8).toPixels(),
    padding: fx(16).toPixels(),
  },
  text: {
    fontSize: fx(16).toPixels(),
    lineHeight: fx(20).toPixels(),
  },
});
```

### 4.4. Dynamic Scaling (DY)

```tsx
const { dy } = useAppDimens();

const styles = StyleSheet.create({
  card: {
    width: dy(300).toPixels(),
    height: dy(200).toPixels(),
  },
  spacing: {
    marginBottom: dy(20).toPixels(),
  },
});
```

### 4.5. Conditional Scaling

```tsx
const { fx } = useAppDimens();

const buttonSize = fx(80)
  .screen('tablet', 120)
  .screen('phone', 60)
  .screen('watch', 40)
  .toPixels();

<View style={[styles.button, { width: buttonSize, height: buttonSize }]} />
```

### 4.6. Physical Units

```tsx
const { mm, cm, inch } = useAppDimens();

const styles = StyleSheet.create({
  bar1: { width: mm(5) },    // 5 millimeters
  bar2: { width: cm(2) },    // 2 centimeters
  bar3: { width: inch(1) },  // 1 inch
});
```

### 4.7. Layout Utilities

```tsx
const { calculateAvailableItemCount, deviceInfo } = useAppDimens();
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
```

## 5. Advanced Features

### 5.1. Design System Integration

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
    },
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

### 5.2. Performance Configuration

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

### 5.3. Debugging

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
      <Text>Cache Size: {performanceMetrics.cacheSize}</Text>
      <TouchableOpacity onPress={clearCache}>
        <Text>Clear Cache</Text>
      </TouchableOpacity>
    </View>
  );
}
```

## 6. Best Practices

1. **Wrap your app with AppDimensProvider**: Always wrap your root component
2. **Use Fixed for UI Elements**: Buttons, paddings, fonts, icons
3. **Use Dynamic for Layout**: Container widths, spacers, grid items
4. **Enable Caching**: For better performance
5. **Use TypeScript**: Full type safety and autocomplete
6. **Cache Styles**: Create styles outside of render when possible

## 7. Performance Considerations

| Feature | Runtime Overhead | Memory Usage | Calculation Time |
|---------|------------------|--------------|------------------|
| **Fixed/Dynamic** | ~0.001ms | ~50KB | Cached per configuration |
| **Physical Units** | ~0.002ms | ~10KB | On-demand |
| **Cache System** | ~0.0001ms | ~100KB | Automatic invalidation |

## 8. Common Patterns

### Responsive Card

```tsx
function ResponsiveCard() {
  const { fx, dy } = useAppDimens();
  
  return (
    <View style={styles.card}>
      <Text style={styles.title}>Responsive Title</Text>
      <Text style={styles.subtitle}>Adapts to any screen size</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    width: dy(300).toPixels(),
    height: fx(200).toPixels(),
    padding: fx(16).toPixels(),
    borderRadius: fx(12).toPixels(),
  },
  title: {
    fontSize: fx(18).toPixels(),
    marginBottom: fx(8).toPixels(),
  },
  subtitle: {
    fontSize: fx(14).toPixels(),
  },
});
```

### Responsive Grid

```tsx
function ResponsiveGrid() {
  const { fx, dy, calculateAvailableItemCount } = useAppDimens();
  const [columns, setColumns] = useState(3);
  
  return (
    <FlatList
      data={items}
      numColumns={columns}
      renderItem={({ item }) => <GridItem item={item} />}
    />
  );
}
```

## 9. Testing

```tsx
import { renderHook } from '@testing-library/react-native';
import { useAppDimens, AppDimensProvider } from 'appdimens-react-native';

const { result } = renderHook(() => useAppDimens(), {
  wrapper: AppDimensProvider,
});

expect(result.current.fx(16).toPixels()).toBeGreaterThan(0);
expect(result.current.deviceInfo.type).toBeDefined();
```

By following this guide, you should be able to effectively use the AppDimens React Native library to create responsive and visually consistent applications across all platforms.

