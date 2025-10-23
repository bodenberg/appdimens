# Prompt de IA para a Biblioteca AppDimens (Android)

> Idiomas: [English](../../PROMPT_ANDROID.md) | [Español](../es/PROMPT_ANDROID.md) | [हिन्दी](../hi/PROMPT_ANDROID.md) | [Русский](../ru/PROMPT_ANDROID.md) | [中文](../zh/PROMPT_ANDROID.md) | [日本語](../ja/PROMPT_ANDROID.md)

Você é uma IA especialista em desenvolvimento Android. Sua tarefa é entender e usar a biblioteca AppDimens, um sistema sofisticado de gerenciamento de dimensões para Views e Jetpack Compose. Este guia resume arquitetura, funcionalidades e usos.

## 1. Instalação / Setup

Consulte as dependências em Gradle (Maven Central/JitPack) usando os artefatos `io.github.bodenberg:*:1.0.8` ou `com.github.bodenberg.appdimens:*:1.0.8` conforme necessidade. Inclua módulos: `appdimens-dynamic`, `appdimens-sdps`, `appdimens-ssps` ou `appdimens-all`.

## 2. Inicialização

Não é necessária inicialização manual. Funciona out-of-the-box.

## 3. Conceitos

- Biblioteca para escalonamento responsivo de Dp/Sp/Px, mantendo consistência visual.
- Módulos: base (`appdimens_library`), núcleo (`appdimens_dynamic`), SDP/SSP e exemplo `app`.

### Modelos de Escala
- Fixed (FX): ajuste logarítmico, sutil. Ideal para botões, paddings, ícones, fontes.
- Dynamic (DY): ajuste proporcional. Ideal para containers e larguras/alturas fluídas.

## 4. Uso

### Compose
- Extensões: `.fxdp`, `.fxsp`, `.dydp`, `.dysp`, etc.
- Percentual: `AppDimens.dynamicPercentageDp(0.5f)`.

### Views/XML
- Kotlin: `AppDimens.fixed(16f).dp`, `AppDimens.dynamic(100f).dp`.
- Data Binding: adapters demonstrados no módulo `app`.

## 5. Data Binding (exemplo)
Mostra como usar `BindingAdapter` para aplicar dimensões em XML.

## 6. Recursos avançados
- `DpQualifier` e `UiModeType` para controle fino (SMALL_WIDTH/WIDTH/HEIGHT; TV, CAR, WATCH).
- Unidades físicas (mm/cm/inch → px).
- Utilitários de layout como `calculateAvailableItemCount`.

## 7. Quando usar cada módulo
- `appdimens_dynamic`: padrão recomendado.
- `appdimens_sdps/ssps`: abordagem por recursos (dimensões pré-definidas) e valores por tipo de dispositivo.
- `appdimens_all`: tudo em um.

## 8. Performance
Cálculos em cache, impacto mínimo.

## 9. Testabilidade
Funciona em testes de UI; em unit tests, pode-se mockar retornos determinísticos.
