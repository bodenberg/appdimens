# 🚀 AppDimens: त्वरित गाइड और लाइब्रेरी अवलोकन

> भाषाएँ: [English](../../../../Android/appdimens_all/README.md) | [Português (BR)](../../pt-BR/Android/appdimens_all/README.md) | [Español](../../es/Android/appdimens_all/README.md) | [Русский](../../ru/Android/appdimens_all/README.md) | [中文](../../zh/Android/appdimens_all/README.md) | [日本語](../../ja/Android/appdimens_all/README.md)

**AppDimens** Android (Views/Compose) के लिए **गणितीय रूप से रिस्पॉन्सिव** डाइमेंशन सिस्टम है।

- **मॉडल**: Fixed (FX) सूक्ष्म समायोजन, Dynamic (DY) अनुपात-आधारित समायोजन
- **Compose**: `.fxdp`, `.dydp`, `.fxsp`, `.dysp`
- **Views/XML**: `AppDimens.fixedPx`, `AppDimens.dynamicPx`, `AppDimens.dynamicPercentagePx`
- **भौतिक इकाइयाँ**: mm/cm/inch → Dp/Sp/Px
- **लेआउट यूटिलिटी**: `calculateAvailableItemCount`

```kotlin
// उदाहरण (Compose)
val fixedButton = 56.fxdp
val dynamicWidth = 100.dydp
```

- दस्तावेज़: `Android/DOCS/`
- लाइसेंस: `LICENSE`
