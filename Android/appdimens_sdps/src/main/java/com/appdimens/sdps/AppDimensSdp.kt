package com.appdimens.sdps

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
 * Representa uma entrada de dimensão customizada com qualificadores e prioridade.
 * Usada pela classe [Scaled] para definir valores específicos para condições de tela.
 *
 * @param uiModeType O modo de UI (CAR, TELEVISION, WATCH, NORMAL). Nulo para qualquer modo.
 * @param dpQualifierEntry A entrada do qualificador de Dp (tipo e valor, e.g., SMALL_WIDTH > 600). Nulo se apenas o modo de UI for usado.
 * @param customValue O valor de Dp que deve ser usado se a condição for atendida.
 * @param priority A prioridade de resolução. 1 é mais específico (UI + Qualificador), 3 é menos específico (apenas Qualificador).
 */
data class CustomDpEntry(
    val uiModeType: UiModeType? = null,
    val dpQualifierEntry: DpQualifierEntry? = null,
    val customValue: Dp,
    val priority: Int
)

/**
 * Obtém o valor real da configuração (Configuration) para o [DpQualifier] dado.
 *
 * @param qualifier O tipo de qualificador (SMALL_WIDTH, HEIGHT, WIDTH).
 * @param configuration A configuração de recurso atual.
 * @return O valor numérico (em Dp) da métrica da tela.
 */
private fun getQualifierValue(qualifier: DpQualifier, configuration: Configuration): Float {
    return when (qualifier) {
        DpQualifier.SMALL_WIDTH -> configuration.smallestScreenWidthDp.toFloat()
        DpQualifier.HEIGHT -> configuration.screenHeightDp.toFloat()
        DpQualifier.WIDTH -> configuration.screenWidthDp.toFloat()
    }
}

/**
 * Mapeia o valor de [uiMode] da configuração para o enum [UiModeType] da biblioteca.
 */
fun fromConfiguration(uiMode: Int): UiModeType {
    // Usa uma máscara bitwise para isolar o tipo de modo de UI
    return when (uiMode and Configuration.UI_MODE_TYPE_MASK) {
        Configuration.UI_MODE_TYPE_CAR -> UiModeType.CAR
        Configuration.UI_MODE_TYPE_TELEVISION -> UiModeType.TELEVISION
        Configuration.UI_MODE_TYPE_WATCH -> UiModeType.WATCH
        else -> UiModeType.NORMAL
    }
}

// --- Extensões Composable para Dimensionamento Dinâmico Rápido ---

/**
 * Extensão para Dp com dimensionamento dinâmico baseado na **Smallest Width (swDP)**.
 * Exemplo de uso: `16.sdp`.
 */
@get:Composable
val Int.sdp: Dp get() = this.toDynamicScaledDp(DpQualifier.SMALL_WIDTH)

/**
 * Extensão para Dp com dimensionamento dinâmico baseado na **Altura da Tela (hDP)**.
 * Exemplo de uso: `32.hdp`.
 */
@get:Composable
val Int.hdp: Dp get() = this.toDynamicScaledDp(DpQualifier.HEIGHT)

/**
 * Extensão para Dp com dimensionamento dinâmico baseado na **Largura da Tela (wDP)**.
 * Exemplo de uso: `100.wdp`.
 */
@get:Composable
val Int.wdp: Dp get() = this.toDynamicScaledDp(DpQualifier.WIDTH)

// --- Métodos de Criação da Classe Scaled ---

/**
 * Inicia a cadeia de construção para a dimensão customizada [Scaled] a partir de um [Dp] base.
 * Exemplo de uso: `100.dp.scaled().screen(...)`
 */
@Composable
fun Dp.scaledDp(): Scaled = Scaled(this@scaledDp)

/**
 * Inicia a cadeia de construção para a dimensão customizada [Scaled] a partir de um [Int] base.
 * Exemplo de uso: `100.scaled().screen(...)`
 */
@Composable
fun Int.scaledDp(): Scaled = this.dp.scaledDp()

// --- Classe Principal de Dimensionamento Condicional ---

/**
 * Classe [Stable] que permite a definição de dimensões customizadas
 * baseadas em qualificadores de tela ([UiModeType], Largura, Altura, Smallest Width).
 *
 * O valor [dp] é resolvido na composição (Compose) e usa o valor base ou um
 * valor customizado, aplicando o dimensionamento dinâmico no final.
 */
@Stable
class Scaled private constructor(
    private val initialBaseDp: Dp,
    // As entradas customizadas são sempre mantidas ordenadas por prioridade.
    private val sortedCustomEntries: List<CustomDpEntry> = emptyList()
) {

    // Construtor principal para iniciar a cadeia
    constructor(initialBaseDp: Dp) : this(initialBaseDp, emptyList())

    /**
     * Adiciona uma nova entrada e reordena a lista.
     * A ordenação é crucial: primeiro por [priority] (crescente),
     * e depois por [dpQualifierEntry.value] (decrescente) para que qualificadores
     * maiores (e.g., sw600dp) sejam verificados antes de qualificadores menores (e.g., sw320dp).
     */
    private fun reorderEntries(newEntry: CustomDpEntry): List<CustomDpEntry> {
        return (sortedCustomEntries + newEntry).sortedWith(
            compareBy<CustomDpEntry> { it.priority } // Prioridade 1 (mais específico) primeiro
                .thenByDescending { it.dpQualifierEntry?.value ?: 0 } // Qualificador de valor maior primeiro
        )
    }

    // --- Métodos Fluent para Construção (Chain of Responsibility) ---

    /**
     * Prioridade 1: Qualificador mais específico - Combina [UiModeType] E Qualificador de Dp (sw, h, w).
     */
    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Dp
    ): Scaled {
        val entry = CustomDpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            customValue = customValue,
            priority = 1
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    fun screen(
        uiModeType: UiModeType,
        qualifierType: DpQualifier,
        qualifierValue: Int,
        customValue: Int
    ): Scaled {
        val entry = CustomDpEntry(
            uiModeType = uiModeType,
            dpQualifierEntry = DpQualifierEntry(qualifierType, qualifierValue),
            customValue = customValue.dp,
            priority = 1
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    /**
     * Prioridade 2: Qualificador de [UiModeType] (e.g., TELEVISION, WATCH).
     */
    fun screen(type: UiModeType, customValue: Dp): Scaled {
        val entry = CustomDpEntry(
            uiModeType = type,
            customValue = customValue,
            priority = 2
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    fun screen(type: UiModeType, customValue: Int): Scaled {
        val entry = CustomDpEntry(
            uiModeType = type,
            customValue = customValue.dp,
            priority = 2
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    /**
     * Prioridade 3: Qualificador de Dp (sw, h, w) sem restrição de [UiModeType].
     */
    fun screen(type: DpQualifier, value: Int, customValue: Dp): Scaled {
        val entry = CustomDpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            customValue = customValue,
            priority = 3
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    fun screen(type: DpQualifier, value: Int, customValue: Int): Scaled {
        val entry = CustomDpEntry(
            dpQualifierEntry = DpQualifierEntry(type, value),
            customValue = customValue.dp,
            priority = 3
        )
        return Scaled(initialBaseDp, reorderEntries(entry))
    }

    // --- Lógica de Resolução Composable ---

    /**
     * Resolve a dimensão final. Esta é a parte Composable que lê a configuração atual
     * e decide qual [Dp] usar.
     *
     * @param qualifier O qualificador de dimensionamento dinâmico que será aplicado no final
     * (SMALL_WIDTH, HEIGHT ou WIDTH), determinado pela propriedade de acesso (.sdp, .hdp, .wdp).
     */
    @SuppressLint("ConfigurationScreenWidthHeight") // A anotação é necessária, pois acessamos métricas da tela.
    @Composable
    private fun resolve(qualifier: DpQualifier): Dp {
        val configuration = LocalConfiguration.current
        val currentUiModeType = fromConfiguration(configuration.uiMode)

        // Tenta encontrar a primeira entrada customizada que se qualifica.
        // A lista é verificada na ordem de prioridade definida em [reorderEntries].
        val foundEntry = sortedCustomEntries.firstOrNull { entry ->
            val qualifierEntry = entry.dpQualifierEntry
            val uiModeMatch = entry.uiModeType == null || entry.uiModeType == currentUiModeType

            if (qualifierEntry != null) {
                // Verifica se o valor da tela é MAIOR OU IGUAL ao valor do qualificador.
                val qualifierMatch = getQualifierValue(qualifierEntry.type, configuration) >= qualifierEntry.value

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

        // Determina o valor de Dp a ser usado: customizado ou o base inicial.
        val dpToUse: Dp = foundEntry?.customValue ?: initialBaseDp

        // Aplica o dimensionamento dinâmico ao valor base/customizado,
        // usando o 'qualifier' passado explicitamente pela propriedade de acesso.
        val baseIntDp = dpToUse.value.toInt()
        return baseIntDp.toDynamicScaledDp(qualifier)
    }

    /**
     * O valor final [Dp] que é resolvida no Compose.
     */
    @get:Composable
    val sdp: Dp get() = resolve(DpQualifier.SMALL_WIDTH)
    @get:Composable
    val hdp: Dp get() = resolve(DpQualifier.HEIGHT)
    @get:Composable
    val wdp: Dp get() = resolve(DpQualifier.WIDTH)
}

// --- Funções de Dimensionamento Dinâmico (Resource-based) ---

/**
 * Encontra o ID de recurso de dimensão (`dimen`) pelo nome construído.
 *
 * A anotação [SuppressLint] é usada porque [getIdentifier] é desencorajada,
 * mas é necessária para este tipo de lógica dinâmica baseada em convenção de nomenclatura.
 *
 * @param resourceName O nome esperado do recurso, e.g., `_s16dp`.
 * @return O ID do recurso ou 0 (ou -1) se não for encontrado.
 */
@SuppressLint("LocalContextResourcesRead", "DiscouragedApi")
@Composable
private fun findResourceIdByName(resourceName: String): Int {
    val context = LocalContext.current
    return context.resources.getIdentifier(
        resourceName,
        "dimen", // O tipo de recurso é 'dimen'
        context.packageName
    )
}

/**
 * Converte um [Int] (o valor base de Dp) em um [Dp] dinamicamente escalado.
 *
 * A lógica tenta encontrar um recurso de dimensão correspondente na pasta 'res/values/'.
 *
 * 1. Constrói o nome do recurso baseado no valor ([this]) e no qualificador ([qualifier]).
 * 2. Tenta carregar o recurso via [dimensionResource].
 * 3. Se o recurso for encontrado (e.g., em `values-sw600dp/dimens.xml`), esse valor é usado.
 * 4. Se o recurso não for encontrado, o valor original é usado como [Dp] (o [Int.dp] padrão do Compose).
 *
 * @param qualifier O qualificador de tela usado para construir o nome do recurso (s, h, w).
 * @return O valor de Dp carregado do recurso ou o valor de Dp base.
 */
@Composable
fun Int.toDynamicScaledDp(qualifier: DpQualifier): Dp {
    // Requisito de validação (limita o uso para evitar a criação de milhares de arquivos dimens)
    require(this in -300..600)
    "Value must be between -300 and 600 to use the dynamic scaling dimension logic. Current value:: $this"

    // Determina o prefixo do qualificador: s (Smallest Width), h (Height), w (Width)
    val attrName = when (qualifier) {
        DpQualifier.HEIGHT -> "h"
        DpQualifier.WIDTH -> "w"
        else -> "s"
    }

    // Lida com valores negativos, usando o prefixo "minus" na convenção de nome.
    val prefix = if (this < 0) "minus" else ""
    // Constrói o nome do recurso, e.g., "_s16dp", "_minuss16dp", "_w100dp"
    val resourceName = "_${prefix}${kotlin.math.abs(this)}${attrName}dp"

    val dimenResourceId = findResourceIdByName(resourceName)

    // Se o ID do recurso for válido (diferente de 0 ou -1), carrega a dimensão
    return if (dimenResourceId != 0 && dimenResourceId != -1)
        dimensionResource(id = dimenResourceId)
    else this.dp // Caso contrário, retorna o valor de Dp padrão do Compose
}