package com.appdimens.ssps

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.appdimens.library.DpQualifier
import com.appdimens.library.DpQualifierEntry
import com.appdimens.library.UiModeType

/**
 * Author & Developer: Jean Bodenberg
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

/**
 * Representa uma entrada de configuração de dimensão de texto (Sp) personalizada.
 * Usada para definir valores de texto (Sp) específicos com base no modo de UI
 * (ex: carro, TV), no qualificador de DP (ex: largura mínima) e na prioridade.
 *
 * @property uiModeType O modo de UI ao qual esta entrada se aplica (opcional).
 * @property dpQualifierEntry A entrada do qualificador de DP (tipo e valor mínimo) (opcional).
 * @property customValue O valor TextUnit (Sp) a ser usado.
 * @property priority A prioridade de aplicação desta regra. Prioridades mais baixas são avaliadas primeiro.
 */
data class CustomSpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val customValue: TextUnit,
    val priority: Int
)

/**
 * Retorna o valor de configuração de DP (em float) para um DpQualifier específico.
 *
 * @param qualifier O tipo de qualificador (SMALL_WIDTH, HEIGHT, WIDTH).
 * @param configuration A configuração atual do recurso.
 * @return O valor de DP correspondente na configuração.
 */
private fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {
    return when (qualifier) {
        // Retorna a largura mínima da tela em DP.
        DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        // Retorna a altura da tela em DP.
        DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
        // Retorna a largura da tela em DP.
        DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
    }
}

/**
 * Mapeia a máscara de modo de UI da Configuração do Android para o enum UiModeType.
 *
 * @param uiMode O campo 'uiMode' da Configuration.
 * @return O UiModeType correspondente.
 */
fun fromConfiguration(uiMode: Int): UiModeType {
    // Aplica a máscara para obter apenas o tipo de UI.
    return when (uiMode and Configuration.UI_MODE_TYPE_MASK) {
        Configuration.UI_MODE_TYPE_CAR -> UiModeType.CAR
        Configuration.UI_MODE_TYPE_TELEVISION -> UiModeType.TELEVISION
        Configuration.UI_MODE_TYPE_WATCH -> UiModeType.WATCH
        // Valor padrão para celular, tablet, etc.
        else -> UiModeType.NORMAL
    }
}

// --- Extensões de Int para Escalamento Dinâmico de Texto (Sp) ---

/**
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente
 * usando o qualificador **Smallest Width (Largura Mínima)**.
 * Útil para escalonamento de texto baseado na dimensão mais limitante (sw).
 */
@get:Composable
val Int.ssp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, true)

/**
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente
 * usando o qualificador **Height (Altura)**.
 * Útil para escalonamento de texto baseado na altura da tela (h).
 */
@get:Composable
val Int.hsp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, true)

/**
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente
 * usando o qualificador **Width (Largura)**.
 * Útil para escalonamento de texto baseado na largura da tela (w).
 */
@get:Composable
val Int.wsp: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, true)

/**
* Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente (SEM FONTE SCALE)
* usando o qualificador **Smallest Width (Largura Mínima)**.
* Útil para escalonamento de texto baseado na dimensão mais limitante (sw).
*/
@get:Composable
val Int.sem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.SMALL_WIDTH, false)

/**
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente (SEM FONTE SCALE)
 * usando o qualificador **Height (Altura)**.
 * Útil para escalonamento de texto baseado na altura da tela (h).
 */
@get:Composable
val Int.hem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.HEIGHT, false)

/**
 * Extensão Composable para Int que retorna um TextUnit (Sp) escalado dinamicamente (SEM FONTE SCALE)
 * usando o qualificador **Width (Largura)**.
 * Útil para escalonamento de texto baseado na largura da tela (w).
 */
@get:Composable
val Int.wem: TextUnit get() = this.toDynamicScaledSp(DpQualifier.WIDTH, false)

// --- Funções de Criação da Classe Scaled ---

/**
 * Inicia a cadeia de construção `Scaled` a partir de um `TextUnit`.
 */
@Composable
fun TextUnit.scaledSp(): Scaled = Scaled(this@scaledSp)

/**
 * Inicia a cadeia de construção `Scaled` a partir de um `Int` (convertido para Sp).
 */
@Composable
fun Int.scaledSp(): Scaled = this.sp.scaledSp()

// --- Classe Scaled ---

/**
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
     * Construtor secundário para iniciar a cadeia de construção.
     * @param initialBaseSp O valor base do Sp a ser escalonado.
     */
    constructor(initialBaseSp: TextUnit) : this(initialBaseSp, emptyList())

    /**
     * Adiciona uma nova entrada personalizada e reordena a lista.
     * A ordenação é crucial para a lógica de resolução:
     * 1. Pela **prioridade** (ascendente: 1, 2, 3...) - Regras de prioridade mais baixa são avaliadas primeiro.
     * 2. Pelo **valor do qualificador de DP** (descendente) - Para qualificadores do mesmo tipo e prioridade,
     * a tela mais restritiva (maior valor de DP, ex: sw600dp antes de sw400dp) é avaliada primeiro.
     *
     * @param newEntry A nova entrada a ser adicionada.
     * @return A nova lista de entradas classificadas.
     */
    private fun reorderEntries(newEntry: CustomSpEntry): List<CustomSpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomSpEntry> { it.priority }
                .thenByDescending { it.dpQualifierEntry?.value ?: 0 }
        )
    }

    // --- Funções para Definir Regras de Escalamento ---

    /**
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

    fun screen(type: UiModeType, customValue: Int): Scaled {
        val entry = CustomSpEntry(
            uiModeType = type,
            customValue = customValue.sp,
            priority = 2
        )
        return Scaled(initialBaseSp, reorderEntries(entry))
    }

    /**
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

    fun screen(type: DpQualifier, value: Int, customValue: Int): Scaled {
        val entry = CustomSpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            customValue = customValue.sp,
            priority = 3
        )
        return Scaled(initialBaseSp, reorderEntries(entry))
    }

    // --- Lógica de Resolução ---

    /**
     * Resolve a dimensão final. Esta é a parte Composable que lê a configuração atual
     * e decide qual [Dp] usar.
     *
     * @param qualifier O qualificador de dimensionamento dinâmico que será aplicado no final
     * (SMALL_WIDTH, HEIGHT ou WIDTH), determinado pela propriedade de acesso (.ssp, .hsp, .wsp).
     */
    @SuppressLint("ConfigurationScreenWidthHeight") // A anotação é necessária, pois acessamos métricas da tela.
    @Composable
    private fun resolve(qualifier: DpQualifier, fontScale: Boolean): TextUnit {
        val configuration = LocalConfiguration.current
        val currentUiModeType = fromConfiguration(configuration.uiMode)

        // Tenta encontrar a primeira entrada customizada que se qualifica.
        // A lista é verificada na ordem de prioridade definida em [reorderEntries].
        val foundEntry = sortedCustomEntries.firstOrNull { entry ->
            val qualifierEntry = entry.dpQualifierEntry
            val uiModeMatch = entry.uiModeType == null || entry.uiModeType == currentUiModeType

            if (qualifierEntry != null) {
                // Verifica se o valor da tela é MAIOR OU IGUAL ao valor do qualificador.
                val qualifierMatch = getQualifierValue(
                    qualifierEntry.type,
                    configuration
                ) >= qualifierEntry.value

                // Prioridade 1: Deve casar [uiModeMatch] E [qualifierMatch]
                if (entry.priority == 1 && uiModeMatch && qualifierMatch) return@firstOrNull true

                // Prioridade 3: Deve casar apenas [qualifierMatch]
                if (entry.priority == 3 && qualifierMatch) return@firstOrNull true

                return@firstOrNull false // Não casou com P1 ou P3
            } else {
                // Prioridade 2: Deve casar apenas [uiModeMatch] (sem qualificador de Dp)
                return@firstOrNull entry.priority == 2 && uiModeMatch
            }
        }

        // Determina o valor de TextUnit a ser usado: customizado ou o base inicial.
        val spToUse: TextUnit = foundEntry?.customValue ?: initialBaseSp

        // Aplica o dimensionamento dinâmico ao valor base/customizado,
        // usando o 'qualifier' passado explicitamente pela propriedade de acesso.
        val baseIntSp = spToUse.value.toInt()
        return baseIntSp.toDynamicScaledSp(qualifier, fontScale)
    }

    /**
     * O valor final [Dp] que é resolvida no Compose.
     */
    @get:Composable
    val ssp: TextUnit get() = resolve(DpQualifier.SMALL_WIDTH, true)

    @get:Composable
    val hsp: TextUnit get() = resolve(DpQualifier.HEIGHT, true)

    @get:Composable
    val wsp: TextUnit get() = resolve(DpQualifier.WIDTH, true)

    @get:Composable
    val sem: TextUnit get() = resolve(DpQualifier.SMALL_WIDTH, false)

    @get:Composable
    val hem: TextUnit get() = resolve(DpQualifier.HEIGHT, false)

    @get:Composable
    val wem: TextUnit get() = resolve(DpQualifier.WIDTH, false)
}

// --- Funções de Suporte para Resolução de Recursos ---

/**
 * Encontra o ID de recurso de uma dimensão pelo seu nome no pacote atual.
 *
 * @param resourceName O nome do recurso (ex: "_16sdp").
 * @return O ID do recurso de dimensão, ou 0/-1 se não for encontrado.
 */
@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
private fun findResourceIdByName(resourceName: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(
        resourceName,
        "dimen", // Tipo de recurso que estamos procurando (dimensão)
        context.packageName
    )
}

/**
 * A lógica principal para aplicar o escalonamento dinâmico.
 * Tenta encontrar um recurso de dimensão pré-calculado (ex: `_16sdp`)
 * e o usa para obter um valor Sp escalado.
 *
 * @receiver O valor de Sp base (ex: 16 para 16sp).
 * @param qualifier O qualificador usado para determinar o nome do recurso (s, h, w).
 * @return O TextUnit (Sp) escalado dinamicamente, ou o valor base se o recurso não for encontrado.
 */
@Composable
fun Int.toDynamicScaledSp(qualifier: DpQualifier, fontScale: Boolean): TextUnit {
    // Garante que o valor está dentro de uma faixa razoável para a geração de dimensões.
    require(this in 1..600)
    "Value must be between 1 and 600 to use the dynamic scaling dimension logic. Current value:: $this"

    val attrName = when (qualifier) {
        DpQualifier.HEIGHT -> "h" // Recurso com base na altura: ex: _16hdp
        DpQualifier.WIDTH -> "w"  // Recurso com base na largura: ex: _16wdp
        else -> "s"               // Padrão (Smallest Width): ex: _16sdp
    }
    // Constrói o nome do recurso esperado, ex: "_16sdp"
    val resourceName = "_${this}${attrName}sp"
    val dimenResourceId = findResourceIdByName(resourceName)

    // Se o recurso for encontrado, carrega-o e converte para Sp.
    return if (dimenResourceId != 0 && dimenResourceId != -1)
    // dimensionResource retorna o valor em Dp, que é convertido para Sp.
        if (fontScale) dimensionResource(id = dimenResourceId).value.sp
        else (dimensionResource(id = dimenResourceId).value / LocalDensity.current.fontScale).sp
    // Caso contrário, retorna o valor Sp não escalado (padrão do Compose).
    else
        if (fontScale) this.sp
        else (this.sp.value / LocalDensity.current.fontScale).sp
}