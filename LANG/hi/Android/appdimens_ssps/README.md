<div align="center">
    <h1>📐 AppDimens SSP</h1>
    <p><strong>शर्त-आधारित नियमों के साथ डायनामिक टेक्स्ट स्केलिंग</strong></p>
    <p>SSP (Scaled SP) जो प्राथमिकता-आधारित नियमों से Compose और XML में उत्तरदायी टाइपोग्राफी सक्षम करता है।</p>

[![संस्करण](https://img.shields.io/badge/version-1.0.5-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![लाइसेंस](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![प्लेटफ़ॉर्म](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> भाषाएँ: [English](../../../../Android/appdimens_ssps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_ssps/README.md) | [Español](../../es/Android/appdimens_ssps/README.md) | [Русский](../../ru/Android/appdimens_ssps/README.md) | [中文](../../zh/Android/appdimens_ssps/README.md) | [日本語](../../ja/Android/appdimens_ssps/README.md)

---

## 🎯 अवलोकन
- टेक्स्ट एक्सटेंशन: `.ssp`, `.hsp`, `.wsp` (एक्सेसिबिलिटी का सम्मान) और `.sem/.hem/.wem` (एक्सेसिबिलिटी को नज़रअंदाज़)
- UiMode + क्वालिफ़ायर के साथ प्राथमिकता-आधारित कंडीशनल नियम

## 🚀 इंस्टॉलेशन
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-ssps:1.0.5") }
```

## 🎨 उदाहरण (Compose)
```kotlin
Text("शीर्षक", fontSize = 24.ssp)
Text("UI लेबल", fontSize = 12.sem)
```

## 📄 XML
```xml
<TextView android:textSize="@dimen/_16ssp" />
```

## 📚 संदर्भ
- दस्तावेज़: `Android/DOCS/`
- लाइसेंस: `LICENSE`
