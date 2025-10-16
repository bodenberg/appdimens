/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.compose.en

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.appdimens.dynamic.compose.AppDimens.fxdp
import com.appdimens.dynamic.compose.AppDimens.fxsp
import com.appdimens.dynamic.compose.AppDimensPhysicalUnits
import com.appdimens.library.DpQualifier
import com.appdimens.library.ScreenType
import com.appdimens.library.UnitType
import com.appdimens.library.UiModeType
import java.util.Locale

/**
 * [EN] An activity that demonstrates the use of fixed dimensions with AppDimens in Jetpack Compose.
 * 
 * [PT] Uma atividade que demonstra o uso de dimensões fixas com AppDimens no Jetpack Compose.
 */
@OptIn(ExperimentalMaterial3Api::class)
class FixedExampleActivity : ComponentActivity() {
    /**
     * [EN] Called when the activity is first created.
     * 
     * [PT] Chamado quando a atividade é criada pela primeira vez.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // [EN] Apply MaterialTheme for standard visuals.
            // [PT] Aplica o MaterialTheme para visuais padrão.
            MaterialTheme {
                AppDimensDemoScreen()
            }
        }
    }
}

/**
 * [EN] A composable function that displays the AppDimens demo screen.
 * 
 * [PT] Uma função de composição que exibe a tela de demonstração do AppDimens.
 */
// [EN] Suppress lint for accessing resources via LocalContext.current in a Composable.
// [PT] Suprime o lint para acessar recursos via LocalContext.current em um Composable.
@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDimensDemoScreen() {
    // [EN] Obtain local configuration, density, and context for runtime metrics.
    // [PT] Obtém a configuração local, densidade e contexto para métricas de tempo de execução.
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val ctx = LocalContext.current

    // [EN] Toggleable state used in the demo to show how changing ScreenType affects dimension results.
    // [PT] Estado alternável usado na demonstração para mostrar como a alteração do ScreenType afeta os resultados da dimensão.
    var currentScreenType by remember { mutableStateOf(ScreenType.LOWEST) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("AppDimens — Compose demo", fontWeight = FontWeight.SemiBold) })
        }
    ) { contentPadding ->
        // [EN] Main scrollable content column.
        // [PT] Coluna de conteúdo rolável principal.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                // [EN] Use a fixed dimension utility for padding from the AppDimens library.
                // [PT] Usa um utilitário de dimensão fixa para preenchimento da biblioteca AppDimens.
                .padding(12.fxdp),
            verticalArrangement = Arrangement.spacedBy(12.fxdp)
        ) {
            item {
                InfoCard(
                    title = "Device metrics",
                    subtitle = "Quick readout of screen metrics (dp & px)"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.fxdp)) {
                        Text("screenWidthDp = ${configuration.screenWidthDp} dp")
                        Text("screenHeightDp = ${configuration.screenHeightDp} dp")
                        Text("smallestScreenWidthDp = ${configuration.smallestScreenWidthDp} dp")
                        val pxPerDp = density.density
                        // [EN] Show the current density factor (pixels per dp unit).
                        // [PT] Mostra o fator de densidade atual (pixels por unidade de dp).
                        Text(String.format(Locale.getDefault(), "density = %.2f (px per dp)", pxPerDp))
                    }
                }
            }

            item {
                UsageCard(title = "Fixed vs Dynamic (basic)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        val baseDp = 16.dp

                        // [EN] Using the AppDimens extension helpers inside the AppDimens scope.
                        // [PT] Usando os auxiliares de extensão AppDimens dentro do escopo AppDimens.
                        // [EN] Fixed dimensions (dp, sp, px) scale based on the initial configuration/qualifier.
                        // [PT] As dimensões fixas (dp, sp, px) são dimensionadas com base na configuração/qualificador inicial.
                        val fixedDp = with(AppDimens) { baseDp.fixedDp(type = currentScreenType) }
                        val fixedSp = with(AppDimens) { baseDp.fixedSp(type = currentScreenType) }
                        val fixedPx = with(AppDimens) { baseDp.fixedPx(type = currentScreenType) }

                        // [EN] Dynamic dimensions scale proportionally to screen size changes.
                        // [PT] As dimensões dinâmicas são dimensionadas proporcionalmente às alterações no tamanho da tela.
                        val dynamicDp = with(AppDimens) { baseDp.dynamicDp(type = currentScreenType) }
                        val dynamicSp = with(AppDimens) { baseDp.dynamicSp(type = currentScreenType) }
                        val dynamicPx = with(AppDimens) { baseDp.dynamicPx(type = currentScreenType) }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.fxdp)) {
                            // [EN] Demo tiles use the calculated Dp values.
                            // [PT] Os blocos de demonstração usam os valores de Dp calculados.
                            DemoTile(size = fixedDp, label = "fixed dp = ${fixedDp.value.toInt()}dp")
                            DemoTile(size = dynamicDp, label = "dynamic dp = ${dynamicDp.value.toInt()}dp")
                        }

                        Text("fixed sp = ${fixedSp.value}sp, fixed px ≈ ${fixedPx.toInt()}px")
                        Text("dynamic sp = ${dynamicSp.value}sp, dynamic px ≈ ${dynamicPx.toInt()}px")

                        Column (verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                            // [EN] Button to toggle the ScreenType (LOWEST/HIGHEST) which affects the calculation baseline.
                            // [PT] Botão para alternar o ScreenType (LOWEST/HIGHEST) que afeta a linha de base do cálculo.
                            Button(onClick = {
                                currentScreenType = if (currentScreenType == ScreenType.LOWEST) ScreenType.HIGHEST else ScreenType.LOWEST
                            }) {
                                Text("Toggle ScreenType (now: $currentScreenType)")
                            }
                            Text("Tip: toggling screen type recomputes the underlying dimension calculation.")
                        }
                    }
                }
            }

            item {
                UsageCard(title = "Convenience properties (fxdp / fxsp / dydp / dypx)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        val sampleDp = 24.dp
                        // [EN] fxdp: convenience extension for fixed Dp calculation.
                        // [PT] fxdp: extensão de conveniência para cálculo de Dp fixo.
                        val convFxDp = with(AppDimens) { sampleDp.fxdp }
                        // [EN] fxsp: convenience extension for fixed Sp calculation.
                        // [PT] fxsp: extensão de conveniência para cálculo de Sp fixo.
                        val convFxSp = with(AppDimens) { 14.fxsp }
                        // [EN] dynamicPerDp: dynamic dimension based on a percentage of the relevant screen dimension.
                        // [PT] dynamicPerDp: dimensão dinâmica baseada em uma porcentagem da dimensão da tela relevante.
                        val convDyDp = with(AppDimens) { 0.10f.dynamicPerDp() } // 0.10f.dynamicPerDp(type = ScreenType.LOWEST or ScreenType.HIGHEST)

                        Text("sample base = ${sampleDp.value}dp")
                        Text("fxdp -> ${convFxDp.value}dp")
                        Text("fxsp(14.dp) -> ${convFxSp.value}sp")
                        Text("0.10f.dynamicPercentageDp() -> ${convDyDp.value}dp (10% of screen lowest/hight depending on type)")

                        // [EN] Demo: using fxsp directly for font size in a Text Composable.
                        // [PT] Demonstração: usando fxsp diretamente para o tamanho da fonte em um Text Composable.
                        Text(
                            "This text uses AppDimens.fxsp (14.dp.fxsp)",
                            fontSize = with(AppDimens) { 14.dp.fxsp }
                        )
                    }
                }
            }

            item {
                UsageCard(title = "Custom qualifiers (screen / ui mode / intersection)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {

                        // [EN] Example: custom value based on screen width qualifier (WIDTH >= 360).
                        // [PT] Exemplo: valor personalizado com base no qualificador de largura da tela (LARGURA >= 360).
                        val customWidthDp = with(AppDimens) {
                            16.dp.fixed()
                                // [EN] If screen width (WIDTH) is >= 360, use 20dp as the base value.
                                // [PT] Se a largura da tela (LARGURA) for >= 360, use 20dp como valor base.
                                .screen(DpQualifier.WIDTH, 360, 20)
                                .type(currentScreenType)
                                .dp
                        }

                        Text("customWidthDp (WIDTH >= 360) -> ${customWidthDp.value}dp")

                        // [EN] IMPORTANT: UiModeType describes the device/ui mode (car, tv, watch, appliance, etc.) — not the theme (night/day).
                        // [PT] IMPORTANTE: UiModeType descreve o modo do dispositivo/IU (carro, TV, relógio, eletrodoméstico, etc.) — não o tema (noite/dia).
                        // [EN] It demonstrates how to qualify a dimension based on the physical device mode.
                        // [PT] Demonstra como qualificar uma dimensão com base no modo do dispositivo físico.

                        // [EN] Dynamically try to get all UiModeType enum values for the demo buttons.
                        // [PT] Tenta obter dinamicamente todos os valores do enum UiModeType para os botões de demonstração.
                        val uiModeValues = runCatching { UiModeType::class.java.enumConstants?.toList() ?: emptyList() }.getOrNull() ?: emptyList()
                        var selectedUiMode by remember { mutableStateOf(uiModeValues.firstOrNull()) }

                        // [EN] Display the current device UI mode from the configuration.
                        // [PT] Exibe o modo de IU do dispositivo atual a partir da configuração.
                        Text("Detected current UiMode: ${UiModeType.fromConfiguration(configuration.uiMode)}")

                        if (uiModeValues.isNotEmpty()) {
                            // [EN] Display buttons for selecting a UiModeType.
                            // [PT] Exibe botões para selecionar um UiModeType.
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.fxdp)) {
                                items(uiModeValues.size) { index ->
                                    val mode = uiModeValues[index]
                                    Button(onClick = { selectedUiMode = mode }, modifier = Modifier.height(36.dp)) {
                                        Text(mode.name)
                                    }
                                }
                            }

                            Spacer(Modifier.height(6.fxdp))

                            // [EN] Use the selected UiModeType to build a custom intersection example.
                            // [PT] Usa o UiModeType selecionado para construir um exemplo de interseção personalizado.
                            val customUiModeDp = selectedUiMode?.let { sel ->
                                with(AppDimens) {
                                    18.fixed()
                                        // [EN] Intersection qualifier: (selected device mode) AND (small width >= 600) -> use 22dp.
                                        // [PT] Qualificador de interseção: (modo de dispositivo selecionado) E (largura pequena >= 600) -> use 22dp.
                                        .screen(sel, DpQualifier.SMALL_WIDTH, 600, 22)
                                        .type(currentScreenType)
                                        .dp
                                }
                            }

                            Text("Selected UiMode (for custom value): ${selectedUiMode?.name ?: "—"}")
                            Text("customUiModeDp (intersection if selected) -> ${customUiModeDp?.value?.toInt() ?: "n/a"}dp")

                        } else {
                            Text("UiModeType enum values not discoverable at runtime. Check your library's UiModeType for available constants (e.g. CAR, TELEVISION, WATCH, APPLIANCE, etc.).")
                        }

                        Text("These examples show the priority chain: INTERSECTION > UI MODE > QUALIFIER")
                    }
                }
            }

            item {
                UsageCard(title = "Physical units (mm / cm / inch) & radius") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        // [EN] Use the composable extension properties provided by AppDimensPhysicalUnits.
                        // [PT] Usa as propriedades de extensão de composição fornecidas por AppDimensPhysicalUnits.
                        // [EN] These convert physical units (mm, inch, cm) to screen pixels (Px).
                        // [PT] Eles convertem unidades físicas (mm, polegada, cm) em pixels da tela (Px).
                        val tenMmPx = with(AppDimensPhysicalUnits) { 10f.mm }
                        val twoInchPx = with(AppDimensPhysicalUnits) { 2f.inch }
                        val threeCmPx = with(AppDimensPhysicalUnits) { 3f.cm }

                        Text("10 mm ≈ ${tenMmPx.toInt()} px")
                        Text("2 in ≈ ${twoInchPx.toInt()} px")
                        Text("3 cm ≈ ${threeCmPx.toInt()} px")

                        // [EN] Radius helper: calculates the radius in Px from a diameter in a given UnitType.
                        // [PT] Auxiliar de raio: calcula o raio em Px a partir de um diâmetro em um determinado UnitType.
                        val radiusFromDiameterPx = with(AppDimensPhysicalUnits) { 30f.radius(UnitType.CM) }
                        Text("30 cm diameter -> radius ≈ ${radiusFromDiameterPx.toInt()} px")

                        // [EN] Unit size per pixel utility: retrieves the pixel size equivalent to 1 unit (e.g., 1 cm).
                        // [PT] Utilitário de tamanho de unidade por pixel: recupera o tamanho do pixel equivalente a 1 unidade (por exemplo, 1 cm).
                        val oneCmPx = AppDimensPhysicalUnits.unitSizePerPx(UnitType.CM, ctx.resources)
                        Text("1 cm on this device ≈ ${oneCmPx.toInt()} px")
                    }
                }
            }

            item {
                UsageCard(title = "CalculateAvailableItemCount (layout helper)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        var availableCount by remember { mutableStateOf(-1) }

                        Text("Container (full width) with fixed item size 80dp")

                        // [EN] CalculateAvailableItemCount measures the available space in the Box.
                        // [PT] CalculateAvailableItemCount mede o espaço disponível na Caixa.
                        // [EN] and calculates how many items of a given size + padding can fit.
                        // [PT] e calcula quantos itens de um determinado tamanho + preenchimento podem caber.
                        with(AppDimens) {
                            CalculateAvailableItemCount(
                                itemSize = 80.fxdp,
                                itemPadding = 4.fxdp,
                                direction = DpQualifier.WIDTH, // [EN] Check availability along the width.
                                                              // [PT] Verifica a disponibilidade ao longo da largura.
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.fxdp)
                                    .border(1.fxdp, Color.Gray, RoundedCornerShape(6.fxdp)),
                                onResult = { availableCount = it }
                            )
                        }

                        Text("availableCount = $availableCount")

                        if (availableCount > 0) {
                            // [EN] Display the calculated number of tiles (max 6 for visual clarity).
                            // [PT] Exibe o número calculado de blocos (máximo de 6 para clareza visual).
                            Row(horizontalArrangement = Arrangement.spacedBy(8.fxdp)) {
                                repeat(availableCount.coerceAtMost(6)) { i ->
                                    DemoTile(size = 80.fxdp, label = "item ${i + 1}")
                                }
                            }
                        }
                    }
                }
            }

            item {
                UsageCard(title = "Misc examples & utilities") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        // [EN] dynamicPercentageDp helper: calculates a dynamic Dp value based on a percentage of a screen dimension (LOWEST).
                        // [PT] Auxiliar dynamicPercentageDp: calcula um valor de Dp dinâmico com base em uma porcentagem de uma dimensão da tela (LOWEST).
                        val tenPercentDp = with(AppDimens) { dynamicPercentageDp(0.10f, ScreenType.LOWEST) }
                        Text("10% of (lowest) screen dimension -> ${tenPercentDp.value.toInt()}dp")

                        // [EN] dynamicPercentageDp helper: calculates a dynamic Dp value based on a percentage of a screen dimension (HIGHEST).
                        // [PT] Auxiliar dynamicPercentageDp: calcula um valor de Dp dinâmico com base em uma porcentagem de uma dimensão da tela (HIGHEST).
                        val tenPercentDp2 = with(AppDimens) { dynamicPercentageDp(0.10f, ScreenType.HIGHEST) }
                        Text("10% of (highest) screen dimension -> ${tenPercentDp2.value.toInt()}dp")

                        // [EN] Conversions: converts an Sp value to a fixed Px value.
                        // [PT] Conversões: converte um valor de Sp em um valor de Px fixo.
                        val pxFromSp = with(AppDimens) { 18.fixedPx() }
                        Text("18sp fixedPx -> ${pxFromSp.toInt()} px")

                        // [EN] Show how to return raw px/float when needed, and use utility calculations.
                        // [PT] Mostra como retornar px/float bruto quando necessário e usar cálculos de utilitário.
                        val diameterPx = with(AppDimensPhysicalUnits) { 5f.inch }
                        // [EN] Calculates circumference from diameter.
                        // [PT] Calcula a circunferência a partir do diâmetro.
                        val circumference = AppDimensPhysicalUnits.displayMeasureDiameter(diameterPx, true)
                        Text("5in -> diameter px = ${diameterPx.toInt()} px, circumference px ≈ ${circumference.toInt()} px")
                    }
                }
            }
        }
    }
}

// [EN] -- Small helper composables used above --
// [PT] -- Pequenos auxiliares de composição usados acima --

/**
 * [EN] A standard card component for displaying general information.
 * 
 * [PT] Um componente de cartão padrão para exibir informações gerais.
 */
@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.fxdp),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.fxdp)
    ) {
        Column(modifier = Modifier.padding(12.fxdp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.fxdp))
            Text(subtitle, fontSize = 12.fxsp, color = Color.Gray)
            Spacer(Modifier.height(8.fxdp))
            content()
        }
    }
}

/**
 * [EN] A standard card component for wrapping AppDimens usage examples.
 * 
 * [PT] Um componente de cartão padrão para envolver exemplos de uso do AppDimens.
 */
@Composable
private fun UsageCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.fxdp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.fxdp)
    ) {
        Column(modifier = Modifier.padding(14.fxdp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.fxdp))
            content()
        }
    }
}

/**
 * [EN] A simple box tile used to visually represent a calculated Dp size.
 * 
 * [PT] Um bloco de caixa simples usado para representar visualmente um tamanho de Dp calculado.
 */
@Composable
private fun DemoTile(size: Dp, label: String) {
    Box(
        modifier = Modifier
            .size(size) // [EN] Use the calculated Dp size.
                        // [PT] Usa o tamanho de Dp calculado.
            .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(6.fxdp))
            .border(1.fxdp, Color.LightGray, RoundedCornerShape(6.fxdp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 11.fxsp)
    }
}

// [EN] --- Preview for Visualization ---
// [PT] --- Visualização para Visualização ---
@Preview(
    showBackground = true,
    device = "id:pixel_5", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO // [EN] Ensure light theme for preview.
                                           // [PT] Garante o tema claro para a visualização.
)
@Composable
fun AppDimensPreviewEN() {
    MaterialTheme {
        AppDimensDemoScreen()
    }
}