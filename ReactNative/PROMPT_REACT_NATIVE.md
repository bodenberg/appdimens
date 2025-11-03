# AppDimens React Native - Development Prompt

**Quick Reference for AI Assistants and Developers**  
*Version: 2.0.0*

---

## Core Principles

1. **Use BALANCED ⭐ for 95% of UI** (primary)
2. **Use DEFAULT for phone-only** (secondary)
3. **Use PERCENTAGE for containers** (specific)
4. **13 strategies available**
5. **TypeScript first-class support**

---

## API Quick Reference

```typescript
import {useAppDimens} from 'appdimens-react-native';

const {
  balanced,        // Primary ⭐
  defaultScaling,  // Secondary
  percentage,      // Containers
  logarithmic,     // Tablets
  power,           // Configurable
  fluid,           // Typography
  smart            // Auto-inference
} = useAppDimens();

// Usage
<Text style={{fontSize: balanced(16)}}>Hello</Text>
<View style={{width: percentage(300)}} />
<TouchableOpacity style={{height: smart(48).forElement('button')}}>
```

---

## Installation

```bash
npm install appdimens-react-native@2.0.0
```

---

## Strategy Selection

- Multi-device (phone + tablet) → BALANCED ⭐
- Phone-only → DEFAULT
- Containers → PERCENTAGE
- Typography → FLUID
- Games → FIT/FILL

---

**Full Documentation:** [README.md](README.md)
