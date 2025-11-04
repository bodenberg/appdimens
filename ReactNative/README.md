# 📐 AppDimens for React Native

**Smart Responsive Dimensions for React Native**  
*Version: 2.0.0 | Last Updated: February 2025*

> **Languages:** English | [Português (BR)](../LANG/pt-BR/ReactNative/README.md) | [Español](../LANG/es/ReactNative/README.md)

[![npm version](https://img.shields.io/npm/v/appdimens-react-native.svg)](https://www.npmjs.com/package/appdimens-react-native)
[![Platform](https://img.shields.io/badge/platform-iOS%20%7C%20Android-blue.svg)](https://reactnative.dev)

---

## 🆕 What's New in Version 2.0

- 🎯 **13 Scaling Strategies** (up from 2)
- ⭐ **BALANCED** - Primary recommendation (hybrid linear-logarithmic)
- 🔬 **Perceptual Models** (Weber-Fechner, Stevens' Power Law)
- 📐 **Aspect Ratio Adjustment** (5 strategies with AR support)
- 🧠 **Smart Inference** - Automatic strategy selection
- ⚡ **5x Performance** - Optimized TypeScript implementation
- 📘 **Full TypeScript** support

---

## 🚀 Installation

```bash
# npm
npm install appdimens-react-native@2.0.0

# yarn
yarn add appdimens-react-native@2.0.0
```

---

## ⚡ Quick Start

{% raw %}
```typescript
import React from 'react';
import {View, Text} from 'react-native';
import {useAppDimens} from 'appdimens-react-native';

function MyComponent() {
  const {balanced} = useAppDimens();
  
  return (
    <View style={{padding: balanced(16)}}>
      <Text style={{fontSize: balanced(18)}}>Hello World</Text>
    </View>
  );
}
```
{% endraw %}

---

## 🎯 13 Scaling Strategies

### Primary: BALANCED ⭐

{% raw %}
```typescript
const {balanced} = useAppDimens();
<Text style={{fontSize: balanced(16)}}>Text</Text>
```
{% endraw %}

**Use for:** 95% of apps (phones, tablets)

### Secondary: DEFAULT

{% raw %}
```typescript
const {defaultScaling} = useAppDimens();
<Image style={{width: defaultScaling(24), height: defaultScaling(24)}} />
```
{% endraw %}

**Use for:** Phone-focused apps, icons

### Others

{% raw %}
```typescript
const {
  percentage,      // Containers
  logarithmic,     // Tablets
  power,           // Configurable
  fluid,           // Typography
  smart            // Auto-inference
} = useAppDimens();

<View style={{width: percentage(300)}} />
<Text style={{fontSize: fluid(16, 24)}}>Title</Text>
<TouchableOpacity style={{height: smart(48).forElement('button')}}>
```
{% endraw %}

---

## 📚 Documentation

- [Main Documentation](../DOCS/README.md)
- [Mathematical Theory](../DOCS/MATHEMATICAL_THEORY.md)
- [Examples](../DOCS/EXAMPLES.md)
- [Quick Reference](../DOCS/DOCS_QUICK_REFERENCE.md)

---

## 🎯 Complete Example

{% raw %}
```typescript
import React from 'react';
import {View, Text, TouchableOpacity, ScrollView} from 'react-native';
import {useAppDimens} from 'appdimens-react-native';

function FeedScreen() {
  const {balanced, smart} = useAppDimens();
  
  return (
    <ScrollView style={{padding: balanced(16)}}>
      <Text style={{
        fontSize: balanced(24),
        fontWeight: 'bold',
        marginBottom: balanced(16)
      }}>
        Feed
      </Text>
      
      {posts.map(post => (
        <View
          key={post.id}
          style={{
            backgroundColor: 'white',
            borderRadius: balanced(12),
            padding: balanced(16),
            marginBottom: balanced(12),
            shadowColor: '#000',
            shadowOpacity: 0.1,
            shadowRadius: balanced(4),
            elevation: 2,
          }}
        >
          <Text style={{fontSize: balanced(16), fontWeight: 'bold'}}>
            {post.title}
          </Text>
          <Text style={{fontSize: balanced(14), color: '#666', marginTop: balanced(8)}}>
            {post.content}
          </Text>
          
          <TouchableOpacity
            style={{
              height: smart(40).forElement('button'),
              backgroundColor: '#007AFF',
              borderRadius: balanced(8),
              justifyContent: 'center',
              alignItems: 'center',
              marginTop: balanced(12),
            }}
          >
            <Text style={{fontSize: balanced(14), color: 'white'}}>
              Read More
            </Text>
          </TouchableOpacity>
        </View>
      ))}
    </ScrollView>
  );
}
```
{% endraw %}

---

**Author:** Jean Bodenberg  
**License:** Apache 2.0  
**Repository:** https://github.com/bodenberg/appdimens  
**NPM:** https://www.npmjs.com/package/appdimens-react-native
