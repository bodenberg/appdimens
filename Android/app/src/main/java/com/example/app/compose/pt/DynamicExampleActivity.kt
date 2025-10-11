/**
 * AppDimens Compose Demo Activity
 * Author & Developer: Jean Bodenberg
 * Purpose: Demonstrate complete usage of the AppDimens classes (Fixed, Dynamic,
 * PhysicalUnits, AdjustmentFactors utilities and CalculateAvailableItemCount).
 *
 * Place this file under your app module (e.g. `com.appdimens.demo`).
 * Build & runtime requirements:
 * - Jetpack Compose setup (activity-compose)
 * - Material3 is used for visuals (you can adapt to Material if desired)
 * - Add your `app-dimens` module / library to project so imports resolve
 */

package com.example.app.compose.pt // Mantive o nome do pacote original

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

@OptIn(ExperimentalMaterial3Api::class)
class DynamicExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Chama a tela de demonstração traduzida
                DynamicDimensDemoScreen()
            }
        }
    }
}

@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicDimensDemoScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val ctx = LocalContext.current
    // Estado para alternar entre tipos de tela (LOWEST/HIGHEST) para demonstração dinâmica
    var screenType by remember { mutableStateOf(ScreenType.LOWEST) }

    Scaffold(
        topBar = {
            TopAppBar(
                // Título da barra superior
                title = { Text("AppDimens — Exemplo Dinâmico", fontWeight = FontWeight.SemiBold) }
            )
        }
    ) { contentPadding ->
        // Conteúdo principal em uma lista de rolagem
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(12.dydp),
            verticalArrangement = Arrangement.spacedBy(12.dydp)
        ) {
            item {
                // Cartão de informação sobre a prévia de escala
                InfoCard("Prévia de Escala Dinâmica", "Observe como as dimensões se adaptam por tipo de tela") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        val baseDp = 24.dp // Dimensão base

                        // Aplica o escalonamento dinâmico baseado no 'screenType' atual
                        val dynDp = with(AppDimens) { baseDp.dynamicDp(screenType) }
                        val dynSp = with(AppDimens) { baseDp.dynamicSp(screenType) }
                        val dynPx = with(AppDimens) { baseDp.dynamicPx(screenType) }

                        // Exibe os valores
                        Text("Base: ${baseDp.value}dp")
                        Text("dynamicDp -> ${dynDp.value.toInt()}dp")
                        Text("dynamicSp -> ${dynSp.value}sp")
                        Text("dynamicPx -> ${dynPx.toInt()}px")

                        Column (verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                            // Exibe um bloco de demonstração com o tamanho dinâmico
                            DemoTile(size = dynDp, label = "dp Dinâmico")
                            // Botão para alternar entre os tipos de tela (LOWEST/HIGHEST)
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
                // Cartão de uso para demonstração de proporção responsiva (porcentagem)
                UsageCard("Exemplo de Proporção Responsiva (Largura %, Altura %)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        // Calcula 10% da largura da tela no ScreenType.LOWEST
                        val w10 = with(AppDimens) { 0.10f.dynamicPerDp(ScreenType.LOWEST) }
                        // Calcula 10% da altura da tela no ScreenType.HIGHEST
                        val h10 = with(AppDimens) { 0.10f.dynamicPerDp(ScreenType.HIGHEST) }

                        Text("10% da largura da tela -> ${w10.value.toInt()}dp")
                        Text("10% da altura da tela -> ${h10.value.toInt()}dp")

                        // Exibe um Box com as dimensões de porcentagem calculadas
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
                // Cartão de uso para ajuste dinâmico com slider (fator de porcentagem)
                UsageCard("Prévia ao vivo — ajuste o fator dinamicamente") {
                    // Estado mutável para o fator de ajuste
                    var factor by remember { mutableStateOf(1.0f) }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        Text("Fator de ajuste atual: ${"%.2f".format(factor)}")
                        // Aplica o fator de porcentagem dinâmico
                        val adjustedDp = with(AppDimens) { dynamicPercentageDp(factor) }
                        Box(
                            modifier = Modifier
                                .size(adjustedDp) // Usa a dimensão ajustada
                                .background(Color(0xFFFFCC80), RoundedCornerShape(6.dydp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${adjustedDp.value.toInt()}dp", fontWeight = FontWeight.Bold)
                        }
                        // Slider para alterar o fator
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
                // Cartão de uso para unidades físicas no contexto dinâmico
                UsageCard("Unidades físicas em contexto dinâmico") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        // Converte 2cm e 1 polegada (inch) para pixels (px)
                        val cmPx = with(AppDimensPhysicalUnits) { 2f.cm }
                        val inchPx = with(AppDimensPhysicalUnits) { 1f.inch }
                        Text("2 cm ≈ ${cmPx.toInt()} px")
                        Text("1 polegada ≈ ${inchPx.toInt()} px")

                        // Converte o valor em px (cmPx) para Dp dinâmico
                        val dynRadius = with(AppDimens) { cmPx.dynamicDp(screenType) }
                        Text("Ajustado Dinamicamente (2cm) ≈ ${dynRadius.value.toInt()}dp (após escala)")
                    }
                }
            }

            item {
                // Cartão de notas finais
                InfoCard("Notas", "Resumo Dinâmico vs Fixo") {
                    Text("Dimensões dinâmicas escalam automaticamente com base no tamanho da tela, densidade e ScreenType."
                            +"\n\nUse-as para layouts adaptáveis que se mantêm consistentes entre dispositivos.\n")
                }
            }
        }
    }
}

// --- Funções de Ajuda (Helpers) reutilizadas ---
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

// Prévia para visualização no Android Studio
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