# 📐 AppDimens para React Native

**Dimensões Responsivas Inteligentes para React Native**  
*Versão: 2.0.0*

> **Idiomas:** [English](../../../ReactNative/README.md) | Português (BR) | [Español](../../es/ReactNative/README.md)

---

## 🚀 Instalação

```bash
npm install appdimens-react-native@2.0.0
```

---

## ⚡ Início Rápido

{% raw %}
```typescript
import {useAppDimens} from 'appdimens-react-native';

function MeuComponente() {
  const {balanced} = useAppDimens();
  
  return (
    <View style={{padding: balanced(16)}}>
      <Text style={{fontSize: balanced(18)}}>Olá Mundo</Text>
    </View>
  );
}
```
{% endraw %}

---

## 🎯 Estratégias

- **BALANCED** ⭐ (primária)
- **DEFAULT** (secundária)
- **PERCENTAGE**, **LOGARITHMIC**, **POWER**, **FLUID**
- E mais 7 estratégias

---

**NPM:** https://www.npmjs.com/package/appdimens-react-native
