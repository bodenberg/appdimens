<div align="center">
    <h1>🎮 AppDimens Games</h1>
    <p><strong>Android के लिए C++/NDK सपोर्ट के साथ रिस्पॉन्सिव गेम डेवलपमेंट</strong></p>
    <p>OpenGL ES, व्यूपोर्ट प्रबंधन और गेम-विशिष्ट डाइमेंशन गणनाओं वाला विशेष मॉड्यूल।</p>

[![संस्करण](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![लाइसेंस](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![प्लेटफ़ॉर्म](https://img.shields.io/badge/platform-Android%2023+-orange.svg)](https://developer.android.com/)
[![NDK](https://img.shields.io/badge/NDK-r21+-green.svg)](https://developer.android.com/ndk)
</div>

> भाषाएँ: [English](../../../../Android/appdimens_games/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_games/README.md) | [Español](../../es/Android/appdimens_games/README.md) | [Русский](../../ru/Android/appdimens_games/README.md) | [中文](../../zh/Android/appdimens_games/README.md) | [日本語](../../ja/Android/appdimens_games/README.md)

---

## 🎯 अवलोकन
- गेम UI, गेम वर्ल्ड और ओवरले के लिए स्केलिंग
- C++/NDK इंटीग्रेशन और OpenGL ES यूटिलिटीज़
- व्यूपोर्ट मैनेजमेंट और परफ़ॉर्मेंस मॉनिटरिंग

## 🚀 इंस्टॉलेशन
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-games:1.0.6") }
```

## 🎨 उपयोग (Kotlin)
```kotlin
val button = appDimensGames.calculateButtonSize(48f)
val player = appDimensGames.calculatePlayerSize(64f)
```

## 🧩 C++ (NDK)
```cpp
float size = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
```

## 📚 संदर्भ
- डॉक्यूमेंटेशन: `Android/DOCS/`
- लाइसेंस: `LICENSE`
