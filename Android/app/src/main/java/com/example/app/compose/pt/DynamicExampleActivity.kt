/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.compose.pt

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens
import com.appdimens.dynamic.compose.AppDimens.dydp
import com.appdimens.dynamic.compose.AppDimens.dysp
import com.appdimens.dynamic.compose.AppDimensPhysicalUnits
import com.appdimens.library.ScreenType

/**
 * [EN] An activity that demonstrates the use of dynamic dimensions with AppDimens in Jetpack Compose.
 * 
 * [PT] Uma atividade que demonstra o uso de dimensões dinâmicas com AppDimens no Jetpack Compose.
 */
@OptIn(ExperimentalMaterial3Api::class)
class DynamicExampleActivity : ComponentActivity() {
    /**
     * [EN] Called when the activity is first created.
     * 
     * [PT] Chamado quando a atividade é criada pela primeira vez.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // [EN] Calls the translated demo screen.
                // [PT] Chama a tela de demonstração traduzida.
                DynamicDimensDemoScreen()
            }
        }
    }
}

/**
 * [EN] A composable function that displays the dynamic dimensions demo screen.
 * 
 * [PT] Uma função de composição que exibe a tela de demonstração de dimensões dinâmicas.
 */
@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicDimensDemoScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val ctx = LocalContext.current
    // [EN] State to toggle between screen types (LOWEST/HIGHEST) for dynamic demonstration.
    // [PT] Estado para alternar entre tipos de tela (LOWEST/HIGHEST) para demonstração dinâmica.
    var screenType by remember { mutableStateOf(ScreenType.LOWEST) }

    Scaffold(
        topBar = {
            TopAppBar(
                // [EN] Top app bar title.
                // [PT] Título da barra de aplicativos superior.
                title = { Text("AppDimens — Exemplo Dinâmico", fontWeight = FontWeight.SemiBold) }
            )
        }
    ) { contentPadding ->
        // [EN] Main content in a scrollable list.
        // [PT] Conteúdo principal em uma lista rolável.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(12.dydp),
            verticalArrangement = Arrangement.spacedBy(12.dydp)
        ) {
            item {
                // [EN] Info card about the dynamic scaling preview.
                // [PT] Cartão de informações sobre a prévia de escala dinâmica.
                InfoCard("Prévia de Escala Dinâmica", "Observe como as dimensões se adaptam por tipo de tela") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        // [EN] Base dimension.
                        // [PT] Dimensão base.
                        val baseDp = 24.dp

                        // [EN] Applies dynamic scaling based on the current 'screenType'.
                        // [PT] Aplica o escalonamento dinâmico baseado no 'screenType' atual.
                        val dynDp = with(AppDimens) { baseDp.dynamicDp(screenType) }
                        val dynSp = with(AppDimens) { baseDp.dynamicSp(screenType) }
                        val dynPx = with(AppDimens) { baseDp.dynamicPx(screenType) }

                        // [EN] Displays the values.
                        // [PT] Exibe os valores.
                        Text("Base: ${baseDp.value}dp")
                        Text("dynamicDp -> ${dynDp.value.toInt()}dp")
                        Text("dynamicSp -> ${dynSp.value}sp")
                        Text("dynamicPx -> ${dynPx.toInt()}px")

                        Column (verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                            // [EN] Displays a demo block with the dynamic size.
                            // [PT] Exibe um bloco de demonstração com o tamanho dinâmico.
                            DemoTile(size = dynDp, label = "dp Dinâmico")
                            // [EN] Button to toggle between screen types (LOWEST/HIGHEST).
                            // [PT] Botão para alternar entre os tipos de tela (LOWEST/HIGHEST).
                            Button(onClick = {
                                screenType = if (screenType == ScreenType.LOWEST) ScreenType.HIGHEST else ScreenType.LOWEST
                            }) {
                                Text("Alternar Tipo de Tela ($screenType)")
                            }
                        }
                    }
                }
            }

            item {
                // [EN] Usage card for demonstrating responsive ratio (percentage).
                // [PT] Cartão de uso para demonstração de proporção responsiva (porcentagem).
                UsageCard("Exemplo de Proporção Responsiva (Largura %, Altura %)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        // [EN] Calculates 10% of the screen width in ScreenType.LOWEST.
                        // [PT] Calcula 10% da largura da tela no ScreenType.LOWEST.
                        val w10 = with(AppDimens) { 0.10f.dynamicPerDp(ScreenType.LOWEST) }
                        // [EN] Calculates 10% of the screen height in ScreenType.HIGHEST.
                        // [PT] Calcula 10% da altura da tela no ScreenType.HIGHEST.
                        val h10 = with(AppDimens) { 0.10f.dynamicPerDp(ScreenType.HIGHEST) }

                        Text("10% da largura da tela -> ${w10.value.toInt()}dp")
                        Text("10% da altura da tela -> ${h10.value.toInt()}dp")

                        // [EN] Displays a Box with the calculated percentage dimensions.
                        // [PT] Exibe um Box com as dimensões de porcentagem calculadas.
                        Box(
                            modifier = Modifier
                                .width(w10)
                                .height(h10)
                                .background(Color(0xFF80CBC4), RoundedCornerShape(8.dydp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("10% x 10%", color = Color.Black)
                        }
                    }
                }
            }

            item {
                // [EN] Usage card for dynamic adjustment with a slider (percentage factor).
                // [PT] Cartão de uso para ajuste dinâmico com slider (fator de porcentagem).
                UsageCard("Prévia ao vivo — ajuste o fator dinamicamente") {
                    // [EN] Mutable state for the adjustment factor.
                    // [PT] Estado mutável para o fator de ajuste.
                    var factor by remember { mutableStateOf(1.0f) }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        Text("Fator de ajuste atual: ${"%.2f".format(factor)}")
                        // [EN] Applies the dynamic percentage factor.
                        // [PT] Aplica o fator de porcentagem dinâmico.
                        val adjustedDp = with(AppDimens) { dynamicPercentageDp(factor) }
                        Box(
                            modifier = Modifier
                                .size(adjustedDp) // [EN] Uses the adjusted dimension.
                                                 // [PT] Usa a dimensão ajustada.
                                .background(Color(0xFFFFCC80), RoundedCornerShape(6.dydp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${adjustedDp.value.toInt()}dp", fontWeight = FontWeight.Bold)
                        }
                        // [EN] Slider to change the factor.
                        // [PT] Slider para alterar o fator.
                        Slider(
                            value = factor,
                            onValueChange = { factor = it },
                            valueRange = 0.5f..2.0f,
                            steps = 5
                        )
                    }
                }
            }

            item {
                // [EN] Usage card for physical units in a dynamic context.
                // [PT] Cartão de uso para unidades físicas no contexto dinâmico.
                UsageCard("Unidades físicas em contexto dinâmico") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        // [EN] Converts 2cm and 1 inch to pixels (px).
                        // [PT] Converte 2cm e 1 polegada (inch) para pixels (px).
                        val cmPx = with(AppDimensPhysicalUnits) { 2f.cm }
                        val inchPx = with(AppDimensPhysicalUnits) { 1f.inch }
                        Text("2 cm ≈ ${cmPx.toInt()} px")
                        Text("1 polegada ≈ ${inchPx.toInt()} px")

                        // [EN] Converts the px value (cmPx) to dynamic Dp.
                        // [PT] Converte o valor em px (cmPx) para Dp dinâmico.
                        val dynRadius = with(AppDimens) { cmPx.dynamicDp(screenType) }
                        Text("Ajustado Dinamicamente (2cm) ≈ ${dynRadius.value.toInt()}dp (após escala)")
                    }
                }
            }

            item {
                // [EN] Final notes card.
                // [PT] Cartão de notas finais.
                InfoCard("Notas", "Resumo Dinâmico vs Fixo") {
                    Text("Dimensões dinâmicas escalam automaticamente com base no tamanho da tela, densidade e ScreenType."
                            +"\n\nUse-as para layouts adaptáveis que se mantêm consistentes entre dispositivos.\n")
                }
            }
        }
    }
}

// [EN] --- Reusable Helper Functions ---
// [PT] --- Funções de Ajuda Reutilizáveis ---
/**
 * [EN] An information card composable.
 * 
 * [PT] Um composable de cartão de informações.
 */
@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dydp),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dydp)
    ) {
        Column(modifier = Modifier.padding(12.dydp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dydp))
            Text(subtitle, fontSize = 12.dysp, color = Color.Gray)
            Spacer(Modifier.height(8.dydp))
            content()
        }
    }
}

/**
 * [EN] A usage card composable.
 * 
 * [PT] Um composable de cartão de uso.
 */
@Composable
private fun UsageCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dydp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dydp)
    ) {
        Column(modifier = Modifier.padding(14.dydp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dydp))
            content()
        }
    }
}

/**
 * [EN] A demo tile composable.
 * 
 * [PT] Um composable de bloco de demonstração.
 */
@Composable
private fun DemoTile(size: Dp, label: String) {
    Box(
        modifier = Modifier
            .size(size)
            .background(Color(0xFFEEEEEE), RoundedCornerShape(6.dydp))
            .border(1.dydp, Color.LightGray, RoundedCornerShape(6.dydp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 11.dysp)
    }
}

/**
 * [EN] A preview for the dynamic dimensions demo screen.
 * 
 * [PT] Uma pré-visualização para a tela de demonstração de dimensões dinâmicas.
 */
// [EN] Preview for visualization in Android Studio.
// [PT] Pré-visualização para visualização no Android Studio.
@Preview(
    showBackground = true,
    device = "id:Nexus One", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AppDimensPreview2() {
    MaterialTheme {
        DynamicDimensDemoScreen()
    }
}