/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.appdimens.ssps.compose

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.UiModeType

/**
 * [EN]
 * Represents a custom text dimension (Sp) configuration entry.
 * Used to define specific text (Sp) values based on the UI mode
 * (e.g., car, TV), DP qualifier (e.g., smallest width), and priority.
 *
 * [PT]
 * Representa uma entrada de configuração de dimensão de texto (Sp) personalizada.
 * Usada para definir valores de texto (Sp) específicos com base no modo de UI
 * (ex: carro, TV), no qualificador de DP (ex: largura mínima) e na prioridade.
 *
 * @property uiModeType The UI mode to which this entry applies (optional).
 * @property dpQualifierEntry The DP qualifier entry (type and minimum value) (optional).
 * @property customValue The TextUnit (Sp) value to be used.
 * @property priority The application priority of this rule. Lower priorities are evaluated first.
 */
data class CustomSpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val customValue: TextUnit,
    val priority: Int
)

/**
 * [EN]
 * Returns the DP configuration value (as a float) for a specific DpQualifier.
 *
 * [PT]
 * Retorna o valor de configuração de DP (em float) para um DpQualifier específico.
 *
 * @param qualifier The type of qualifier (SMALL_WIDTH, HEIGHT, WIDTH).
 * @param configuration The current resource configuration.
 * @return The corresponding DP value in the configuration.
 */
private fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {
    return when (qualifier) {
        // [EN] Returns the smallest screen width in DP. / [PT] Retorna a largura mínima da tela em DP.
        DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        // [EN] Returns the screen height in DP. / [PT] Retorna a altura da tela em DP.
        DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
        // [EN] Returns the screen width in DP. / [PT] Retorna a largura da tela em DP.
        DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
    }
}

/**
 * [EN]
 * Maps the UI mode mask from the Android Configuration to the UiModeType enum.
 *
 * [PT]
 * Mapeia a máscara de modo de UI da Configuração do Android para o enum UiModeType.
 *
 * @param uiMode The 'uiMode' field of the Configuration.
 * @return The corresponding UiModeType.
 */
fun fromConfiguration(uiMode: Int): UiModeType {
    // [EN] Applies the mask to get only the UI type. / [PT] Aplica a máscara para obter apenas o tipo de UI.
    return when (uiMode and Configuration.UI_MODE_TYPE_MASK) {
        Configuration.UI_MODE_TYPE_CAR -> UiModeType.CAR
        Configuration.UI_MODE_TYPE_TELEVISION -> UiModeType.TELEVISION
        Configuration.UI_MODE_TYPE_WATCH -> UiModeType.WATCH
        // [EN] Default value for mobile, tablet, etc. / [PT] Valor padrão para celular, tablet, etc.
        else -> UiModeType.NORMAL
    }
}

// [EN] Int extensions for dynamic text scaling (Sp).
// [PT] Extensões de Int para escalonamento dinâmico de texto (Sp).

/**
 * [EN]
 * Composable extension for Int that returns a dynamically scaled TextUnit (Sp)
 * using the **Smallest Width** qualifier.
 * Useful for text scaling based on the most limiting dimension (sw).
 *
 * [PT]
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente
 * usando o qualificador **Smallest Width (Largura Mínima)**.
 * Útil para escalonamento de texto baseado na dimensão mais limitante (sw).
 */
@get:Composable
val Int.ssp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, true)

/**
 * [EN]
 * Composable extension for Int that returns a dynamically scaled TextUnit (Sp)
 * using the **Height** qualifier.
 * Useful for text scaling based on the screen height (h).
 *
 * [PT]
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente
 * usando o qualificador **Height (Altura)**.
 * Útil para escalonamento de texto baseado na altura da tela (h).
 */
@get:Composable
val Int.hsp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, true)

/**
 * [EN]
 * Composable extension for Int that returns a dynamically scaled TextUnit (Sp)
 * using the **Width** qualifier.
 * Useful for text scaling based on the screen width (w).
 *
 * [PT]
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente
 * usando o qualificador **Width (Largura)**.
 * Útil para escalonamento de texto baseado na largura da tela (w).
 */
@get:Composable
val Int.wsp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, true)

/**
 * [EN]
 * Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE)
 * using the **Smallest Width** qualifier.
 * Useful for text scaling based on the most limiting dimension (sw).
 *
 * [PT]
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente (SEM FONTE SCALE)
 * usando o qualificador **Smallest Width (Largura Mínima)**.
 * Útil para escalonamento de texto baseado na dimensão mais limitante (sw).
 */
@get:Composable
val Int.sem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, false)

/**
 * [EN]
 * Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE)
 * using the **Height** qualifier.
 * Useful for text scaling based on the screen height (h).
 *
 * [PT]
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente (SEM FONTE SCALE)
 * usando o qualificador **Height (Altura)**.
 * Útil para escalonamento de texto baseado na altura da tela (h).
 */
@get:Composable
val Int.hem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, false)

/**
 * [EN]
 * Composable extension for Int that returns a dynamically scaled TextUnit (Sp) (WITHOUT FONT SCALE)
 * using the **Width** qualifier.
 * Useful for text scaling based on the screen width (w).
 *
 * [PT]
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente (SEM FONTE SCALE)
 * usando o qualificador **Width (Largura)**.
 * Útil para escalonamento de texto baseado na largura da tela (w).
 */
@get:Composable
val Int.wem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, false)

// [EN] Functions for creating the Scaled class.
// [PT] Funções de criação da classe Scaled.

/**
 * [EN]
 * Starts the `Scaled` build chain from a `TextUnit`.
 *
 * [PT]
 * Inicia a cadeia de construção `Scaled` a partir de um `TextUnit`.
 */
@Composable
fun TextUnit.scaledSp(): Scaled = Scaled(this@scaledSp)

/**
 * [EN]
 * Starts the `Scaled` build chain from an `Int` (converted to Sp).
 *
 * [PT]
 * Inicia a cadeia de construção `Scaled` a partir de um `Int` (convertido para Sp).
 */
@Composable
fun Int.scaledSp(): Scaled = this.sp.scaledSp()

// [EN] Scaled Class
// [PT] Classe Scaled

/**
 * [EN]
 * The main class for applying dynamic text scaling (Sp) with conditional logic.
 * Allows defining specific Sp values for different screen configurations
 * (UI mode, smallest width, height, width).
 *
 * [PT]
 * A classe principal para aplicar escalonamento dinâmico de texto (Sp) com lógica condicional.
 * Permite a definição de valores Sp específicos para diferentes configurações de tela
 * (modo de UI, largura mínima, altura, largura).
 */
@Stable
class Scaled private constructor(
    private val initialBaseSp: TextUnit,
    private val sortedCustomEntries: List<CustomSpEntry> = emptyList()
) {

    /**
     * [EN]
     * Secondary constructor to start the build chain.
     *
     * [PT]
     * Construtor secundário para iniciar a cadeia de construção.
     * @param initialBaseSp The base Sp value to be scaled.
     */
    constructor(initialBaseSp: TextUnit) : this(initialBaseSp, emptyList())

    /**
     * [EN]
     * Adds a new custom entry and re-sorts the list.
     * Sorting is crucial for the resolution logic:
     * 1. By **priority** (ascending: 1, 2, 3...) - Lower priority rules are evaluated first.
     * 2. By **DP qualifier value** (descending) - For qualifiers of the same type and priority,
     * the more restrictive screen (larger DP value, e.g., sw600dp before sw400dp) is evaluated first.
     *
     * [PT]
     * Adiciona uma nova entrada personalizada e reordena a lista.
     * A ordenação é crucial para a lógica de resolução:
     * 1. Pela **prioridade** (ascendente: 1, 2, 3...) - Regras de prioridade mais baixa são avaliadas primeiro.
     * 2. Pelo **valor do qualificador de DP** (descendente) - Para qualificadores do mesmo tipo e prioridade,
     * a tela mais restritiva (maior valor de DP, ex: sw600dp antes de sw400dp) é avaliada primeiro.
     *
     * @param newEntry The new entry to be added.
     * @return The new list of sorted entries.
     */
    private fun reorderEntries(newEntry: CustomSpEntry): List<CustomSpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomSpEntry> { it.priority }
                .thenByDescending { it.dpQualifierEntry?.value ?: 0 }
        )
    }

    // [EN] Functions for defining scaling rules.
    // [PT] Funções para definir regras de escalonamento.

    /**
     * [EN]
     * Priority 1 rule: Combination of UI Mode and DP Qualifier.
     * Applicable if the **UI mode** matches AND the screen is **greater than or equal to** the `qualifierValue`.
     *
     * [PT]
     * Regra de prioridade 1: Combinação de Modo de UI e Qualificador de DP.
     * Aplicável se o **modo de UI** corresponder E a tela for **maior ou igual** ao `qualifierValue`.
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: TextUnit
    ): Scaled {
        val entry = CustomSpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            customValue = customValue,
            priority = 1
        )
        return Scaled(initialBaseSp, reorderEntries(entry))
    }

    /**
     * [EN]
     * Overload for `screen` that accepts an `Int` as `customValue`.
     *
     * [PT]
     * Sobrecarga de `screen` que aceita um `Int` como `customValue`.
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Int
    ): Scaled {
        val entry = CustomSpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            customValue = customValue.sp,
            priority = 1
        )
        return Scaled(initialBaseSp, reorderEntries(entry))
    }

    /**
     * [EN]
     * Priority 2 rule: UI Mode only.
     * Applicable if the **UI mode** matches.
     *
     * [PT]
     * Regra de prioridade 2: Apenas Modo de UI.
     * Aplicável se o **modo de UI** corresponder.
     */
    fun screen(type: UiModeType, customValue: TextUnit): Scaled {
        val entry = CustomSpEntry(
            uiModeType = type,
            customValue = customValue,
            priority = 2
        )
        return Scaled(initialBaseSp, reorderEntries(entry))
    }

    /**
     * [EN]
     * Overload for `screen` that accepts an `Int` as `customValue`.
     *
     * [PT]
     * Sobrecarga de `screen` que aceita um `Int` como `customValue`.
     */
    fun screen(type: UiModeType, customValue: Int): Scaled {
        val entry = CustomSpEntry(
            uiModeType = type,
            customValue = customValue.sp,
            priority = 2
        )
        return Scaled(initialBaseSp, reorderEntries(entry))
    }

    /**
     * [EN]
     * Priority 3 rule: DP Qualifier only.
     * Applicable if the screen is **greater than or equal to** the qualifier `value`.
     *
     * [PT]
     * Regra de prioridade 3: Apenas Qualificador de DP.
     * Aplicável se a tela for **maior ou igual** ao `value` do qualificador.
     */
    fun screen(type: DpQualifier, value: Int, customValue: TextUnit): Scaled {
        val entry = CustomSpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            customValue = customValue,
            priority = 3
        )
        return Scaled(initialBaseSp, reorderEntries(entry))
    }

    /**
     * [EN]
     * Overload for `screen` that accepts an `Int` as `customValue`.
     *
     * [PT]
     * Sobrecarga de `screen` que aceita um `Int` como `customValue`.
     */
    fun screen(type: DpQualifier, value: Int, customValue: Int): Scaled {
        val entry = CustomSpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            customValue = customValue.sp,
            priority = 3
        )
        return Scaled(initialBaseSp, reorderEntries(entry))
    }

    // [EN] Resolution logic.
    // [PT] Lógica de resolução.

    /**
     * [EN]
     * Resolves the final dimension. This is the Composable part that reads the current configuration
     * and decides which [TextUnit] to use.
     *
     * [PT]
     * Resolve a dimensão final. Esta é a parte Composable que lê a configuração atual
     * e decide qual [TextUnit] usar.
     *
     * @param qualifier The dynamic scaling qualifier to be applied at the end
     * (SMALL_WIDTH, HEIGHT, or WIDTH), determined by the accessor property (.ssp, .hsp, .wsp).
     * @param fontScale Whether to respect the user's font scaling settings.
     */
    @SuppressLint("ConfigurationScreenWidthHeight") // [EN] Annotation is necessary as we access screen metrics. / [PT] A anotação é necessária, pois acessamos métricas da tela.
    @Composable
    private fun resolve(qualifier: DpQualifier, fontScale: Boolean): TextUnit {
        val configuration = LocalConfiguration.current
        val currentUiModeType = fromConfiguration(configuration.uiMode)

        // [EN] Tries to find the first custom entry that qualifies.
        // The list is checked in the priority order defined in [reorderEntries].
        // [PT] Tenta encontrar a primeira entrada customizada que se qualifica.
        // A lista é verificada na ordem de prioridade definida em [reorderEntries].
        val foundEntry = sortedCustomEntries.firstOrNull { entry ->
            val qualifierEntry = entry.dpQualifierEntry
            val uiModeMatch = entry.uiModeType == null || entry.uiModeType == currentUiModeType

            if (qualifierEntry != null) {
                // [EN] Checks if the screen value is GREATER THAN OR EQUAL to the qualifier value.
                // [PT] Verifica se o valor da tela é MAIOR OU IGUAL ao valor do qualificador.
                val qualifierMatch = getQualifierValue(
                    qualifierEntry.type,
                    configuration
                ) >= qualifierEntry.value

                // [EN] Priority 1: Must match [uiModeMatch] AND [qualifierMatch].
                // [PT] Prioridade 1: Deve casar [uiModeMatch] E [qualifierMatch].
                if (entry.priority == 1 && uiModeMatch && qualifierMatch) return@firstOrNull true

                // [EN] Priority 3: Must match only [qualifierMatch].
                // [PT] Prioridade 3: Deve casar apenas [qualifierMatch].
                if (entry.priority == 3 && qualifierMatch) return@firstOrNull true

                return@firstOrNull false // [EN] Did not match P1 or P3. / [PT] Não casou com P1 ou P3.
            } else {
                // [EN] Priority 2: Must match only [uiModeMatch] (without DP qualifier).
                // [PT] Prioridade 2: Deve casar apenas [uiModeMatch] (sem qualificador de Dp).
                return@firstOrNull entry.priority == 2 && uiModeMatch
            }
        }

        // [EN] Determines the TextUnit value to use: custom or the initial base.
        // [PT] Determina o valor de TextUnit a ser usado: customizado ou o base inicial.
        val spToUse: TextUnit = foundEntry?.customValue ?: initialBaseSp

        // [EN] Applies dynamic scaling to the base/custom value,
        // using the 'qualifier' passed explicitly by the accessor property.
        // [PT] Aplica o dimensionamento dinâmico ao valor base/customizado,
        // usando o 'qualifier' passado explicitamente pela propriedade de acesso.
        val baseIntSp = spToUse.value.toInt()
        return baseIntSp.toDynamicScaledSp(qualifier, fontScale)
    }

    /**
     * [EN]
     * The final [TextUnit] value that is resolved in Compose.
     *
     * [PT]
     * O valor final [TextUnit] que é resolvido no Compose.
     */
    @get:Composable
    val ssp: TextUnit get() = resolve(DpQualifier.SMALL_WIDTH, true)

    /**
     * [EN]
     * The final [TextUnit] value that is resolved in Compose.
     *
     * [PT]
     * O valor final [TextUnit] que é resolvido no Compose.
     */
    @get:Composable
    val hsp: TextUnit get() = resolve(DpQualifier.HEIGHT, true)

    /**
     * [EN]
     * The final [TextUnit] value that is resolved in Compose.
     *
     * [PT]
     * O valor final [TextUnit] que é resolvido no Compose.
     */
    @get:Composable
    val wsp: TextUnit get() = resolve(DpQualifier.WIDTH, true)

    /**
     * [EN]
     * The final [TextUnit] value that is resolved in Compose.
     *
     * [PT]
     * O valor final [TextUnit] que é resolvido no Compose.
     */
    @get:Composable
    val sem: TextUnit get() = resolve(DpQualifier.SMALL_WIDTH, false)

    /**
     * [EN]
     * The final [TextUnit] value that is resolved in Compose.
     *
     * [PT]
     * O valor final [TextUnit] que é resolvido no Compose.
     */
    @get:Composable
    val hem: TextUnit get() = resolve(DpQualifier.HEIGHT, false)

    /**
     * [EN]
     * The final [TextUnit] value that is resolved in Compose.
     *
     * [PT]
     * O valor final [TextUnit] que é resolvido no Compose.
     */
    @get:Composable
    val wem: TextUnit get() = resolve(DpQualifier.WIDTH, false)
}

// [EN] Support functions for resource resolution.
// [PT] Funções de suporte para resolução de recursos.

/**
 * [EN]
 * Finds the resource ID of a dimension by its name in the current package.
 *
 * [PT]
 * Encontra o ID de recurso de uma dimensão pelo seu nome no pacote atual.
 *
 * @param resourceName The name of the resource (e.g., "_16sdp").
 * @return The dimension resource ID, or 0/-1 if not found.
 */
@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
private fun findResourceIdByName(resourceName: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(
        resourceName,
        "dimen", // [EN] Type of resource we are looking for (dimension). / [PT] Tipo de recurso que estamos procurando (dimensão).
        context.packageName
    )
}

/**
 * [EN]
 * The main logic for applying dynamic scaling.
 * Tries to find a pre-calculated dimension resource (e.g., `_16sdp`)
 * and uses it to get a scaled Sp value.
 *
 * [PT]
 * A lógica principal para aplicar o escalonamento dinâmico.
 * Tenta encontrar um recurso de dimensão pré-calculado (ex: `_16sdp`)
 * e o usa para obter um valor Sp escalado.
 *
 * @receiver The base Sp value (e.g., 16 for 16sp).
 * @param qualifier The qualifier used to determine the resource name (s, h, w).
 * @param fontScale A boolean that indicates if the font scale is enabled or not
 * @return The dynamically scaled TextUnit (Sp), or the base value if the resource is not found.
 */
@Composable
fun Int.toDynamicScaledSp(qualifier: DpQualifier, fontScale: Boolean): TextUnit {
    // [EN] Ensures that the value is within a reasonable range for dimension generation.
    // [PT] Garante que o valor está dentro de uma faixa razoável para a geração de dimensões.
    require(this in 1..600)
    "Value must be between 1 and 600 to use the dynamic scaling dimension logic. Current value:: $this"

    val attrName = when (qualifier) {
        DpQualifier.HEIGHT -> "h" // [EN] Resource based on height: e.g., _16hdp / [PT] Recurso com base na altura: ex: _16hdp
        DpQualifier.WIDTH -> "w"  // [EN] Resource based on width: e.g., _16wdp / [PT] Recurso com base na largura: ex: _16wdp
        else -> "s"               // [EN] Default (Smallest Width): e.g., _16sdp / [PT] Padrão (Smallest Width): ex: _16sdp
    }
    // [EN] Constructs the expected resource name, e.g., "_16sdp".
    // [PT] Constrói o nome do recurso esperado, ex: "_16sdp".
    val resourceName = "_${this}${attrName}sp"
    val dimenResourceId = findResourceIdByName(resourceName)

    // [EN] If the resource is found, loads it and converts to Sp.
    // [PT] Se o recurso for encontrado, carrega-o e converte para Sp.
    return if (dimenResourceId != 0 && dimenResourceId != -1)
    // [EN] dimensionResource returns the value in Dp, which is converted to Sp. / [PT] dimensionResource retorna o valor em Dp, que é convertido para Sp.
        if (fontScale) dimensionResource(id = dimenResourceId).value.sp
        else (dimensionResource(id = dimenResourceId).value / LocalDensity.current.fontScale).sp
    // [EN] Otherwise, returns the unscaled Sp value (Compose default).
    // [PT] Caso contrário, retorna o valor Sp não escalado (padrão do Compose).
    else
        if (fontScale) this.sp
        else (this.sp.value / LocalDensity.current.fontScale).sp
}
