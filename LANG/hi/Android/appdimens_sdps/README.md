<div align="center">
    <h1>📐 AppDimens SDP</h1>
    <p><strong>शर्त-आधारित नियमों के साथ डायनामिक स्केलिंग</strong></p>
    <p>SDP (Scaled DP) जो प्राथमिकता-आधारित नियमों के साथ Compose और XML लेआउट के लिए रिस्पॉन्सिव स्केलिंग देता है।</p>

[![संस्करण](https://img.shields.io/badge/version-1.0.6-blue.svg)](https://github.com/bodenberg/appdimens/releases)
[![लाइसेंस](https://img.shields.io/badge/license-Apache%202.0-green.svg)](../../../LICENSE)
[![प्लेटफ़ॉर्म](https://img.shields.io/badge/platform-Android%2021+-orange.svg)](https://developer.android.com/)
</div>

> भाषाएँ: [English](../../../../Android/appdimens_sdps/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_sdps/README.md) | [Español](../../es/Android/appdimens_sdps/README.md) | [Русский](../../ru/Android/appdimens_sdps/README.md) | [中文](../../zh/Android/appdimens_sdps/README.md) | [日本語](../../ja/Android/appdimens_sdps/README.md)

---

## 🎯 अवलोकन
- डायरेक्ट एक्सटेंशंस: `.sdp`, `.hdp`, `.wdp`
- प्राथमिकता-आधारित कंडीशनल नियम (UiMode + क्वालिफ़ायर्स)
- प्री-कैल्कुलेटेड संसाधनों के साथ शून्य रनटाइम ओवरहेड

## 🚀 इंस्टॉलेशन
```kotlin
dependencies { implementation("io.github.bodenberg:appdimens-sdps:1.0.6") }
```

## 🎨 उदाहरण (Compose)
```kotlin
Column(Modifier.padding(16.sdp)) {
    Text("Text", fontSize = 18.ssp)
    Spacer(Modifier.height(18.sdp).width(100.wdp))
    Card(Modifier.size(120.sdp).padding(8.hdp)) { }
}
```

### कंडीशनल नियम
```kotlin
val boxSize = 80.scaledDp()
    .screen(UiModeType.WATCH, DpQualifier.SMALL_WIDTH, 200, 40.dp)
    .screen(UiModeType.CAR, 120.dp)
    .screen(DpQualifier.SMALL_WIDTH, 720, 150)
```

## 📄 XML
```xml
<TextView
    android:layout_width="@dimen/_100sdp"
    android:layout_height="wrap_content"
    android:textSize="@dimen/_16ssp" />
```

## 📐 क्वालिफ़ायर्स
- SDP: सबसे छोटी चौड़ाई (sw)
- WDP: चौड़ाई (w)
- HDP: ऊँचाई (h)

## ⚡ परफ़ॉर्मेंस
- एक्सटेंशंस: शून्य ओवरहेड
- नियम: ~0.001ms (प्रति कॉन्फ़िगरेशन कैश)

## 📚 संदर्भ
- दस्तावेज़: `Android/DOCS/`
- लाइसेंस: `LICENSE`
