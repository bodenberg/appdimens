# Resumo da Atualização para Versão 1.0.5

> Languages: [Português (BR)](LANG/pt-BR/VERSION_UPDATE_SUMMARY.md) | [Español](LANG/es/VERSION_UPDATE_SUMMARY.md) | [हिन्दी](LANG/hi/VERSION_UPDATE_SUMMARY.md) | [Русский](LANG/ru/VERSION_UPDATE_SUMMARY.md) | [中文](LANG/zh/VERSION_UPDATE_SUMMARY.md) | [日本語](LANG/ja/VERSION_UPDATE_SUMMARY.md)

## 📋 Alterações Realizadas

### 🔄 Atualização de Versões
- **Android**: Todas as bibliotecas atualizadas de 1.0.4 para 1.0.5
- **iOS**: Todas as bibliotecas atualizadas de 1.0.0 para 1.0.5

### 📚 Documentação Atualizada

#### Android
- **README.md**: Adicionada seção específica sobre o módulo Games
- **README.md**: Clarificado que o módulo `appdimens-all` não inclui o módulo Games
- **README.md**: Adicionados exemplos de uso do módulo Games
- **README.md**: Atualizada tabela de módulos para indicar que Games é separado

#### iOS
- **AppDimens.podspec**: Versão atualizada para 1.0.5
- **README.md**: Versão atualizada nos badges e exemplos
- **Arquivos de código Swift**: Versões internas atualizadas para 1.0.5

### 🎮 Módulo Games - Documentação Adicionada

#### Informações Adicionadas
- **Dependência separada**: `implementation("io.github.bodenberg:appdimens-games:1.0.5")`
- **Explicação clara**: O módulo Games não está incluído no `appdimens-all`
- **Exemplos de uso**: Código Kotlin e C++ para integração
- **Tipos de dimensão**: DYNAMIC, FIXED, GAME_WORLD, UI_OVERLAY

#### Exemplos de Código Adicionados
```kotlin
// Exemplo básico de integração
class GameActivity : Activity() {
    private lateinit var appDimensGames: AppDimensGames
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        appDimensGames = AppDimensGames.getInstance()
        appDimensGames.initialize(this)
        
        val buttonSize = appDimensGames.calculateButtonSize(48.0f)
        val textSize = appDimensGames.calculateTextSize(16.0f)
        val playerSize = appDimensGames.calculatePlayerSize(64.0f)
        val enemySize = appDimensGames.calculateEnemySize(32.0f)
    }
}
```

```cpp
// Exemplo de integração C++
#include "AppDimensGames.h"

class GameEngine {
private:
    AppDimensGames& appDimensGames;
    
public:
    GameEngine(JNIEnv* env, jobject context) {
        appDimensGames = AppDimensGames::getInstance();
        appDimensGames.initialize(env, context);
    }
    
    void updateGame() {
        float buttonSize = appDimensGames.calculateDimension(48.0f, GameDimensionType::FIXED);
        float playerSize = appDimensGames.calculateDimension(64.0f, GameDimensionType::GAME_WORLD);
    }
};
```

### 📁 Arquivos Atualizados

#### Android
- `Android/README.md` - Documentação principal atualizada
- `Android/appdimens_all/build.gradle.kts` - Versão 1.0.5
- `Android/appdimens_dynamic/build.gradle.kts` - Versão 1.0.5
- `Android/appdimens_sdps/build.gradle.kts` - Versão 1.0.5
- `Android/appdimens_ssps/build.gradle.kts` - Versão 1.0.5
- `Android/appdimens_library/build.gradle.kts` - Versão 1.0.5
- `Android/appdimens_games/build.gradle.kts` - Versão 1.0.5
- `Android/appdimens_games/README.md` - Versão 1.0.5
- `Android/appdimens_games/pom.xml` - Versão 1.0.5
- `Android/BENCHMARK.md` - Versões atualizadas
- `Android/BENCHMARK-pt-BR.md` - Versões atualizadas
- `Android/PROMPT_FOR_IA.md` - Versões atualizadas

#### iOS
- `iOS/AppDimens.podspec` - Versão 1.0.5
- `iOS/README.md` - Versão 1.0.5
- `iOS/INSTALLATION.md` - Versão 1.0.5
- `iOS/USAGE_GUIDE.md` - Versão 1.0.5
- `iOS/INSTALLATION_GUIDE.md` - Versão 1.0.5
- `iOS/README_MODULAR.md` - Versão 1.0.5
- `iOS/MODULAR_IMPLEMENTATION_SUMMARY.md` - Versão 1.0.5
- `iOS/PROJECT_SUMMARY.md` - Versão 1.0.5
- `iOS/CHANGELOG.md` - Nova entrada para 1.0.5
- `iOS/Sources/AppDimens/AppDimens.swift` - Versão 1.0.5
- `iOS/Sources/AppDimensGames/AppDimensGamesMain.swift` - Versão 1.0.5
- `iOS/Sources/AppDimensCore/AppDimensCore.swift` - Versão 1.0.5
- `iOS/Sources/AppDimensUI/AppDimensUI.swift` - Versão 1.0.5
- `iOS/DOCUMENTATION.md` - Versão 1.0.5

#### Projeto Principal
- `README.md` - Versão 1.0.5 e dependência Games
- `README.pt-BR.md` - Versão 1.0.5
- `CHANGELOG.md` - Nova entrada para 1.0.5
- `PRESENTATION.md` - Versão 1.0.5
- `PRESENTATION.pt-BR.md` - Versão 1.0.5
- `PROMPT_ANDROID.md` - Versão 1.0.5

### ✅ Verificações Realizadas
- ✅ Todas as versões 1.0.4 foram atualizadas para 1.0.5
- ✅ Todas as versões 1.0.0 do iOS foram atualizadas para 1.0.5
- ✅ Documentação do módulo Games foi adicionada
- ✅ Clarificação de que Games não está incluído no módulo all
- ✅ Exemplos de uso do módulo Games foram adicionados
- ✅ Changelog foi atualizado com as mudanças

### 🎯 Próximos Passos
1. Testar a compilação de todos os módulos
2. Verificar se as dependências estão corretas
3. Publicar as novas versões no Maven Central
4. Atualizar a documentação online se necessário
