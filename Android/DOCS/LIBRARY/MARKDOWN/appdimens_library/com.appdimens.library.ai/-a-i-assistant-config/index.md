---
title: AIAssistantConfig
---
//[appdimens_library](../../../index.html)/[com.appdimens.library.ai](../index.html)/[AIAssistantConfig](index.html)



# AIAssistantConfig



[androidJvm]\
object [AIAssistantConfig](index.html)

Configuração do Assistente de IA para AppDimens Android



Permite habilitar/desabilitar funcionalidades de assistência da IA e gerenciar configurações relacionadas ao aprendizado e sugestões.



#### Since



1.0.7



#### Author



AppDimens Team



## Functions


| Name | Summary |
|---|---|
| [areSuggestionsEnabled](are-suggestions-enabled.html) | [androidJvm]<br>fun [areSuggestionsEnabled](are-suggestions-enabled.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>Verifica se sugestões de design responsivo estão habilitadas |
| [exportSettings](export-settings.html) | [androidJvm]<br>fun [exportSettings](export-settings.html)(): [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)<br>Exporta as configurações como JSON string |
| [getAllSettings](get-all-settings.html) | [androidJvm]<br>fun [getAllSettings](get-all-settings.html)(): [Map](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html), [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>Obtém todas as configurações atuais como Map |
| [importSettings](import-settings.html) | [androidJvm]<br>fun [importSettings](import-settings.html)(jsonString: [String](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>Importa configurações de uma JSON string |
| [initialize](initialize.html) | [androidJvm]<br>fun [initialize](initialize.html)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))<br>Inicializa a configuração do assistente de IA |
| [isAIEnabled](is-a-i-enabled.html) | [androidJvm]<br>fun [isAIEnabled](is-a-i-enabled.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>Verifica se o assistente de IA está habilitado |
| [isCodeGenerationEnabled](is-code-generation-enabled.html) | [androidJvm]<br>fun [isCodeGenerationEnabled](is-code-generation-enabled.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>Verifica se geração de código está habilitada |
| [isPlatformGuidanceEnabled](is-platform-guidance-enabled.html) | [androidJvm]<br>fun [isPlatformGuidanceEnabled](is-platform-guidance-enabled.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>Verifica se orientação específica de plataforma está habilitada |
| [isValidationEnabled](is-validation-enabled.html) | [androidJvm]<br>fun [isValidationEnabled](is-validation-enabled.html)(): [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html)<br>Verifica se validação de melhores práticas está habilitada |
| [resetToDefaults](reset-to-defaults.html) | [androidJvm]<br>fun [resetToDefaults](reset-to-defaults.html)()<br>Reseta todas as configurações para os valores padrão |
| [setAIEnabled](set-a-i-enabled.html) | [androidJvm]<br>fun [setAIEnabled](set-a-i-enabled.html)(enabled: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html))<br>Habilita ou desabilita o assistente de IA |
| [setCodeGenerationEnabled](set-code-generation-enabled.html) | [androidJvm]<br>fun [setCodeGenerationEnabled](set-code-generation-enabled.html)(enabled: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html))<br>Habilita ou desabilita geração de código |
| [setPlatformGuidanceEnabled](set-platform-guidance-enabled.html) | [androidJvm]<br>fun [setPlatformGuidanceEnabled](set-platform-guidance-enabled.html)(enabled: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html))<br>Habilita ou desabilita orientação específica de plataforma |
| [setSuggestionsEnabled](set-suggestions-enabled.html) | [androidJvm]<br>fun [setSuggestionsEnabled](set-suggestions-enabled.html)(enabled: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html))<br>Habilita ou desabilita sugestões de design responsivo |
| [setValidationEnabled](set-validation-enabled.html) | [androidJvm]<br>fun [setValidationEnabled](set-validation-enabled.html)(enabled: [Boolean](https://kotlinlang.org/api/core/kotlin-stdlib/kotlin/-boolean/index.html))<br>Habilita ou desabilita validação de melhores práticas |
