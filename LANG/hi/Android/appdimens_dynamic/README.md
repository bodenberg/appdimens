<div align="center">
    <h1>📐 AppDimens Dynamic</h1>
    <p><strong>Android के लिए कोर रिस्पॉन्सिव डाइमेंशनिंग</strong></p>
    <p>Jetpack Compose, Views/XML और Data Binding के लिए Fixed और Dynamic मॉडल।</p>

[![संस्करण](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![लाइसेंस](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![प्लेटफ़ॉर्म](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> भाषाएँ: [English](../../../../Android/appdimens_dynamic/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_dynamic/README.md) | [Español](../../es/Android/appdimens_dynamic/README.md) | [Русский](../../ru/Android/appdimens_dynamic/README.md) | [中文](../../zh/Android/appdimens_dynamic/README.md) | [日本語](../../ja/Android/appdimens_dynamic/README.md)

---

## 🎯 अवलोकन
- **Fixed (FX)**: लॉगरिदमिक समायोजन; बटन/पैडिंग/आइकन/फ़ॉन्ट के लिए
- **Dynamic (DY)**: आनुपातिक; कंटेनर की चौड़ाई/ऊँचाई, ग्रिड के लिए

## 🚀 इंस्टॉलेशन
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-dynamic:1.0.5") }
```

## 🎨 उदाहरण (Compose)
```kotlin
Text("शीर्षक", fontSize = 24.fxsp)
Button(modifier = Modifier.height(48.fxdp).width(120.fxdp)) { Text("क्रिया") }
Card(modifier = Modifier.fillMaxWidth().height(100.dydp).padding(8.fxdp)) { }
```

## 📄 XML/Data Binding
`app:dynamicWidthDp`, `app:dynamicHeightDp`, `app:dynamicTextSizeDp`।

## 📏 भौतिक इकाइयाँ
`5.0f.toDp(UnitType.MM)`, `2.0f.toSp(UnitType.CM)`, `1.0f.toPx(UnitType.INCH)`

## 🧮 लेआउट यूटिलिटीज़
`AppDimens.CalculateAvailableItemCount(...)`

## 📚 संदर्भ
- दस्तावेज़: `Android/DOCS/`
- लाइसेंस: `LICENSE`
