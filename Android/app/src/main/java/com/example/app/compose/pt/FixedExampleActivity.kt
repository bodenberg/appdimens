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
import com.appdimens.dynamic.compose.AppDimens.fixedDp
import com.appdimens.dynamic.compose.AppDimens.fxPortraitLowest
import com.appdimens.dynamic.compose.AppDimens.fxdp
import com.appdimens.dynamic.compose.AppDimens.fxsp
import com.appdimens.dynamic.compose.AppDimensPhysicalUnits
import com.appdimens.library.DpQualifier
import com.appdimens.library.ScreenType
import com.appdimens.library.UnitType
import com.appdimens.library.UiModeType
import com.appdimens.sdps.compose.hdp
import com.appdimens.sdps.compose.scaledDp
import com.appdimens.sdps.compose.sdp
import com.appdimens.sdps.compose.wdp
import com.appdimens.ssps.compose.ssp
import java.util.Locale

/**
 * [EN] Main demonstration activity.
 * 
 * [PT] Activity principal de demonstração.
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
            MaterialTheme {
                AppDimensDemoScreen()
            }
        }
    }
}

/**
 * [EN] Main screen that demonstrates the features of AppDimens.
 * 
 * [PT] Tela principal que demonstra os recursos do AppDimens.
 */
@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDimensDemoScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val ctx = LocalContext.current

    // [EN] Toggleable state used in the demo to show how changing ScreenType affects the results.
    // [PT] Estado alternável usado na demo para mostrar como a mudança de ScreenType afeta os resultados.
    var currentScreenType by remember { mutableStateOf(ScreenType.LOWEST) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("AppDimens — Compose demo", fontWeight = FontWeight.SemiBold) })
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                // [EN] Using the fxdp (fixed dp) extension from AppDimens.
                // [PT] Usando a extensão fxdp (fixed dp) do AppDimens.
                .padding(12.fxdp),
            verticalArrangement = Arrangement.spacedBy(12.fxdp)
        ) {
            item {
                InfoCard(
                    title = "Métricas do Dispositivo",
                    subtitle = "Leitura rápida das métricas da tela (dp e px)"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.fxdp)) {
                        Text("screenWidthDp = ${configuration.screenWidthDp} dp")
                        Text("screenHeightDp = ${configuration.screenHeightDp} dp")
                        Text("smallestScreenWidthDp = ${configuration.smallestScreenWidthDp} dp")
                        val pxPerDp = density.density
                        Text(String.format(Locale.getDefault(), "density = %.2f (px por dp)", pxPerDp))
                    }
                }
            }

            item {
                UsageCard(title = "Fixo (Fixed) vs Dinâmico (Dynamic) (básico)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        val baseDp = 16.dp

                        // [EN] Using the AppDimens extension helpers within the AppDimens scope.
                        // [PT] Usando os helpers de extensão do AppDimens dentro do escopo AppDimens.
                        val fixedDp = with(AppDimens) { baseDp.fixedDp(type = currentScreenType) }
                        val fixedSp = with(AppDimens) { baseDp.fixedSp(type = currentScreenType) }
                        val fixedPx = with(AppDimens) { baseDp.fixedPx(type = currentScreenType) }

                        val dynamicDp = with(AppDimens) { baseDp.dynamicDp(type = currentScreenType) }
                        val dynamicSp = with(AppDimens) { baseDp.dynamicSp(type = currentScreenType) }
                        val dynamicPx = with(AppDimens) { baseDp.dynamicPx(type = currentScreenType) }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.fxdp)) {
                            // [EN] Visual demonstration of fixed and dynamic size.
                            // [PT] Demonstração visual do tamanho fixo e dinâmico.
                            DemoTile(size = fixedDp, label = "fixo dp = ${fixedDp.value.toInt()}dp")
                            DemoTile(size = dynamicDp, label = "dinâmico dp = ${dynamicDp.value.toInt()}dp")
                        }

                        Text("fixo sp = ${fixedSp.value}sp, fixo px ≈ ${fixedPx.toInt()}px")
                        Text("dinâmico sp = ${dynamicSp.value}sp, dinâmico px ≈ ${dynamicPx.toInt()}px")

                        Column (verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                            Button(onClick = {
                                // [EN] Toggles between LOWEST (smaller screen) and HIGHEST (larger screen).
                                // [PT] Alterna entre LOWEST (menor tela) e HIGHEST (maior tela).
                                currentScreenType = if (currentScreenType == ScreenType.LOWEST) ScreenType.HIGHEST else ScreenType.LOWEST
                            }) {
                                Text("Alternar ScreenType (atual: $currentScreenType)")
                            }
                            Text("Dica: Alternar o tipo de tela recalcula a dimensão subjacente.")
                        }
                    }
                }
            }

            item {
                UsageCard(title = "🆕 Base Orientation - Auto-adapta à rotação (v1.2.0)") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.fxdp)) {
                        Text(
                            "Especifique a orientação original do design e AppDimens automaticamente ajusta quando rotacionar!",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        // Portrait design examples
                        val portraitCard = 280.fixedDp().fxPortraitLowest
                        val portraitPadding = 16.fxPortraitLowest
                        
                        Card(
                            modifier = Modifier
                                .width(portraitCard)
                                .padding(portraitPadding),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
                        ) {
                            Column(modifier = Modifier.padding(12.fxdp)) {
                                Text("Card Portrait Design", style = MaterialTheme.typography.titleSmall)
                                Text("Width: ${portraitCard.value.toInt()}dp", style = MaterialTheme.typography.bodySmall)
                                Text("Se rotacionar para landscape, largura auto-ajusta!", fontSize = 12.fxsp)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.fxdp))
                        
                        // Comparison
                        Text("Comparação:", style = MaterialTheme.typography.titleSmall)
                        Text("• .portraitLowest() - Design portrait, usa largura", fontSize = 12.fxsp)
                        Text("• .landscapeHighest() - Design landscape, usa largura", fontSize = 12.fxsp)
                        Text("• .baseOrientation(AUTO) - Sem inversão (padrão)", fontSize = 12.fxsp)
                    }
                }
            }

            item {
                UsageCard(title = "Propriedades de conveniência (fxdp / fxsp / dydp / dypx)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        val sampleDp = 24.dp
                        // [EN] Convenience extensions:
                        // [PT] Extensões de conveniência:
                        val convFxDp = with(AppDimens) { sampleDp.fxdp }
                        val convFxSp = with(AppDimens) { 14.fxsp }
                        // [EN] dynamicPerDp calculates a percentage of the screen dimension.
                        // [PT] dynamicPerDp calcula uma porcentagem da dimensão da tela.
                        val convDyDp = with(AppDimens) { 0.10f.dynamicPerDp() }


                        val convFxDp1 = with(AppDimens) { 16.fixed().type(ScreenType.HIGHEST).dp }
                        val convFxSp2 = with(AppDimens) { 16.wdp }
                        val convFxSp3 = with(AppDimens) { 16.hdp }
                        val convFxDp3 = with(AppDimens) { 16.dynamic().type(ScreenType.HIGHEST).dp}

                        Text("fx = ${convFxDp1.value}dp")
                        Text("dy -> ${convFxDp3.value}sp")
                        Text("dp -> ${convFxSp2.value}dp")
                        Text("dp -> ${convFxSp3.value}dp")

                        Text("base da amostra = ${sampleDp.value}dp")
                        Text("fxdp -> ${convFxDp.value}dp")
                        Text("fxsp(14.dp) -> ${convFxSp.value}sp")
                        Text("0.10f.dynamicPercentageDp() -> ${convDyDp.value}dp (10% da tela)")

                        // [EN] Demo: using fxsp for font size.
                        // [PT] Demo: usar fxsp para tamanho da fonte.
                        Text(
                            "Este texto usa AppDimens.fxsp (14.dp.fxsp)",
                            fontSize = with(AppDimens) { 14.fxsp }
                        )
                    }
                }
            }

            item {
                UsageCard(title = "Qualificadores personalizados (tela / modo UI / interseção)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {

                        // [EN] Example: custom value for width qualifier.
                        // [PT] Exemplo: valor personalizado para qualificador de largura.
                        val customWidthDp = with(AppDimens) {
                            16.dp.fixed()
                                // [EN] If screen width is >= 360, use 20dp as base.
                                // [PT] Se a largura da tela for >= 360, usa 20dp como base.
                                .screen(DpQualifier.WIDTH, 360, 20)
                                .type(currentScreenType)
                                .dp
                        }

                        Text("customWidthDp (LARGURA >= 360) -> ${customWidthDp.value}dp")

                        // [EN] UiModeType describes the device/UI mode (car, tv, watch, appliance, etc.).
                        // [PT] UiModeType descreve o modo do dispositivo/UI (carro, tv, relógio, eletrodoméstico, etc.).
                        // [EN] The purpose here is to show how to get the values to create a custom qualifier.
                        // [PT] O objetivo aqui é mostrar como obter os valores para criar um qualificador personalizado.
                        val uiModeValues = runCatching { UiModeType::class.java.enumConstants?.toList() ?: emptyList() }.getOrNull() ?: emptyList()
                        var selectedUiMode by remember { mutableStateOf(uiModeValues.firstOrNull()) }

                        Text("UiMode atual detectado: ${UiModeType.fromConfiguration(configuration.uiMode)}")

                        if (uiModeValues.isNotEmpty()) {
                            // [EN] List of buttons to select a UiModeType.
                            // [PT] Lista de botões para selecionar um UiModeType.
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.fxdp)) {
                                items(uiModeValues.size) { index ->
                                    val mode = uiModeValues[index]
                                    Button(
                                        onClick = { selectedUiMode = mode },
                                        modifier = Modifier.height(36.fxdp),
                                        // [EN] Highlights the selected mode.
                                        // [PT] Destaca o modo selecionado.
                                        enabled = selectedUiMode != mode
                                    ) {
                                        Text(mode.name)
                                    }
                                }
                            }

                            Spacer(Modifier.height(6.fxdp))

                            // [EN] Custom intersection example using the selected UiModeType.
                            // [PT] Exemplo de interseção personalizada usando o UiModeType selecionado.
                            val customUiModeDp = selectedUiMode?.let { sel ->
                                with(AppDimens) {
                                    18.fixed()
                                        // [EN] Intersection: (selected device mode) AND (small width >= 600) -> use 22dp.
                                        // [PT] Interseção: (modo do dispositivo selecionado) E (largura pequena >= 600) -> usa 22dp.
                                        .screen(sel, DpQualifier.SMALL_WIDTH, 600, 22)
                                        .type(currentScreenType)
                                        .dp
                                }
                            }

                            Text("UiMode selecionado (para valor customizado): ${selectedUiMode?.name ?: "—"}")
                            Text("customUiModeDp (interseção se selecionado) -> ${customUiModeDp?.value?.toInt() ?: "n/a"}dp")

                        } else {
                            Text("Os valores do enum UiModeType não são detectáveis em runtime. Verifique sua biblioteca AppDimens para os valores constantes disponíveis (ex: CAR, TELEVISION, WATCH, APPLIANCE, etc.).")
                        }

                        Text("Estes exemplos mostram a cadeia de prioridade: INTERSEÇÃO > MODO UI > QUALIFICADOR")
                    }
                }
            }

            item {
                UsageCard(title = "Unidades físicas (mm / cm / polegada) e raio") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        // [EN] Using the composable properties provided by AppDimensPhysicalUnits.
                        // [PT] Usando as propriedades composable fornecidas por AppDimensPhysicalUnits.
                        val tenMmPx = with(AppDimensPhysicalUnits) { 10f.mm }
                        val twoInchPx = with(AppDimensPhysicalUnits) { 2f.inch }
                        val threeCmPx = with(AppDimensPhysicalUnits) { 3f.cm }

                        Text("10 mm ≈ ${tenMmPx.toInt()} px")
                        Text("2 in ≈ ${twoInchPx.toInt()} px")
                        Text("3 cm ≈ ${threeCmPx.toInt()} px")

                        // [EN] Radius helper (composable extension) - if provided by the library.
                        // [PT] Helper de raio (extensão composable) - se fornecido pela biblioteca.
                        // [EN] Calculates the radius in pixels from a diameter value in a physical unit.
                        // [PT] Calcula o raio em pixels a partir de um valor de diâmetro em uma unidade física.
                        val radiusFromDiameterPx = with(AppDimensPhysicalUnits) { 30f.radius(UnitType.CM) }
                        Text("Diâmetro de 30 cm -> raio ≈ ${radiusFromDiameterPx.toInt()} px")

                        // [EN] Size in pixels for a physical unit.
                        // [PT] Tamanho em pixels para uma unidade física.
                        val oneCmPx = AppDimensPhysicalUnits.unitSizePerPx(UnitType.CM, ctx.resources)
                        Text("1 cm neste dispositivo ≈ ${oneCmPx.toInt()} px")
                    }
                }
            }

            item {
                UsageCard(title = "CalculateAvailableItemCount (helper de layout)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        var availableCount by remember { mutableStateOf(-1) }

                        Text("Contêiner (largura total) com tamanho de item fixo de 80dp")

                        // [EN] The helper measures the area of the provided Box and calls onResult.
                        // [PT] O helper mede a área do Box fornecido e chama onResult.
                        with(AppDimens) {
                            CalculateAvailableItemCount(
                                itemSize = 80.fxdp, // [EN] Size of each item.
                                                    // [PT] Tamanho de cada item.
                                itemPadding = 4.fxdp, // [EN] Spacing between items.
                                                      // [PT] Espaçamento entre os itens.
                                direction = DpQualifier.WIDTH, // [EN] Width (horizontal).
                                                             // [PT] Largura (horizontal).
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.fxdp)
                                    .border(1.fxdp, Color.Gray, RoundedCornerShape(6.fxdp)),
                                onResult = { availableCount = it } // [EN] Receives the number of items that fit.
                                                                   // [PT] Recebe o número de itens que cabem.
                            )
                        }

                        Text("availableCount = $availableCount")

                        if (availableCount > 0) {
                            // [EN] Renders the items that fit.
                            // [PT] Renderiza os itens que cabem.
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
                UsageCard(title = "Outros exemplos e utilities") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        // [EN] dynamicPercentage helper.
                        // [PT] Helper dynamicPercentage.
                        val tenPercentDp = with(AppDimens) { dynamicPercentageDp(0.10f, ScreenType.LOWEST) }
                        Text("10% da dimensão (mais baixa) da tela -> ${tenPercentDp.value.toInt()}dp")

                        val tenPercentDp2 = with(AppDimens) { dynamicPercentageDp(0.10f, ScreenType.HIGHEST) }
                        Text("10% da dimensão (mais alta) da tela -> ${tenPercentDp2.value.toInt()}dp")

                        // [EN] Conversions and px values.
                        // [PT] Conversões e valores de px.
                        val pxFromSp = with(AppDimens) { 18.fixedPx() }
                        Text("18sp fixedPx -> ${pxFromSp.toInt()} px")

                        // [EN] Shows how to return raw float/px when needed.
                        // [PT] Mostra como retornar float/px brutos quando necessário.
                        val diameterPx = with(AppDimensPhysicalUnits) { 5f.inch }
                        // [EN] Calculates the circumference based on the diameter in pixels.
                        // [PT] Calcula a circunferência com base no diâmetro em pixels.
                        val circumference = AppDimensPhysicalUnits.displayMeasureDiameter(diameterPx, true)
                        Text("5in -> diâmetro px = ${diameterPx.toInt()} px, circunferência px ≈ ${circumference.toInt()} px")
                    }
                }
            }
        }
    }
}

// [EN] -----------------------------------------------------------------------------
// [PT] -----------------------------------------------------------------------------
// [EN] -- Small helper composables used above --
// [PT] -- Pequenos composables auxiliares usados acima --
// [EN] -----------------------------------------------------------------------------
// [PT] -----------------------------------------------------------------------------

/**
 * [EN] A card for displaying general information.
 * 
 * [PT] Um card para exibir informações gerais.
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
 * [EN] A card for wrapping usage examples.
 * 
 * [PT] Um card para envolver exemplos de uso.
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
 * [EN] A simple tile to visually represent a size.
 * 
 * [PT] Um tile simples para representar visualmente um tamanho.
 */
@Composable
private fun DemoTile(size: Dp, label: String) {
    Box(
        modifier = Modifier
            .size(size)
            .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(6.fxdp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(6.fxdp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 11.fxsp)
    }
}

// [EN] -----------------------------------------------------------------------------
// [PT] -----------------------------------------------------------------------------
// [EN] --- Preview for Visualization ---
// [PT] --- Preview para Visualização ---
// [EN] -----------------------------------------------------------------------------
// [PT] -----------------------------------------------------------------------------
/**
 * [EN] A preview for the AppDimens demo screen.
 * 
 * [PT] Uma pré-visualização para a tela de demonstração do AppDimens.
 */
@Preview(
    showBackground = true,
    device = "spec:parent=desktop_large,orientation=portrait", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AppDimensPreview() {
    MaterialTheme {
        AppDimensDemoScreen()
    }
}