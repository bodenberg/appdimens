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
import com.appdimens.library.ScreenType

/**
 * [EN] Activity demonstrating the PERCENTAGE scaling strategy (formerly "Dynamic").
 *
 * PERCENTAGE Strategy:
 * - Formula: f(x) = x × (W / W₀)
 * - 100% proportional/linear growth
 * - No aspect ratio adjustment
 * - Best for: Containers, fluid layouts, proportional images
 *
 * [PT] Activity demonstrando a estratégia de escalonamento PERCENTAGE (anteriormente "Dynamic").
 *
 * Estratégia PERCENTAGE:
 * - Fórmula: f(x) = x × (W / W₀)
 * - Crescimento 100% proporcional/linear
 * - Sem ajuste de proporção de aspecto
 * - Melhor para: Contêineres, layouts fluidos, imagens proporcionais
 */
@OptIn(ExperimentalMaterial3Api::class)
class PercentageExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PercentageDemoScreen()
            }
        }
    }
}

@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PercentageDemoScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val ctx = LocalContext.current

    var currentScreenType by remember { mutableStateOf(ScreenType.LOWEST) }
    var percentageFactor by remember { mutableStateOf(1.0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Demonstração Estratégia PERCENTAGE",
                        fontSize = AppDimens.from(18).percentage().sp
                    )
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(AppDimens.from(12).percentage().dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.from(12).percentage().dp)
        ) {
            // Métricas do dispositivo
            item {
                InfoCard(
                    title = "Métricas do Dispositivo",
                    subtitle = "Dimensões da tela e densidade"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).percentage().dp
                        )
                    ) {
                        Text("Largura da Tela: ${configuration.screenWidthDp}dp")
                        Text("Altura da Tela: ${configuration.screenHeightDp}dp")
                        Text("Menor Largura: ${configuration.smallestScreenWidthDp}dp")
                        Text("Densidade: ${"%.2f".format(density.density)} (px por dp)")
                        Text("Taxa de Escala: ${"%.2f".format(configuration.smallestScreenWidthDp / 360f)}")
                    }
                }
            }

            // Explicação da estratégia
            item {
                InfoCard(
                    title = "📖 Estratégia PERCENTAGE",
                    subtitle = "Comportamento 'Dynamic' legado com escalonamento 100% linear"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).percentage().dp
                        )
                    ) {
                        Text(
                            "PERCENTAGE é a estratégia de escalonamento dinâmico legada (100% proporcional).",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(AppDimens.from(4).percentage().dp))

                        Text(
                            "Fórmula:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).percentage().sp
                        )
                        Text(
                            "f(x) = x × (W / W₀)",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(12).percentage().sp
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(4).percentage().dp))
                        Text(
                            "Características:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).percentage().sp
                        )
                        Text(
                            "• Crescimento 100% linear/proporcional",
                            fontSize = AppDimens.from(13).percentage().sp
                        )
                        Text(
                            "• Escalonamento puro de tela",
                            fontSize = AppDimens.from(13).percentage().sp
                        )
                        Text(
                            "• Sem compensação de proporção de aspecto",
                            fontSize = AppDimens.from(13).percentage().sp
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(4).percentage().dp))
                        Text(
                            "Melhor para:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).percentage().sp
                        )
                        Text(
                            "• Contêineres e layouts fluidos",
                            fontSize = AppDimens.from(13).percentage().sp
                        )
                        Text(
                            "• Imagens proporcionais",
                            fontSize = AppDimens.from(13).percentage().sp
                        )
                        Text(
                            "• Elementos que devem escalar totalmente",
                            fontSize = AppDimens.from(13).percentage().sp
                        )
                    }
                }
            }

            // Exemplos básicos de uso
            item {
                UsageCard(title = "Uso Básico") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).percentage().dp
                        )
                    ) {
                        Text(
                            "Método 1: Usando .percentage() (recomendado)",
                            fontWeight = FontWeight.Medium,
                            fontSize = AppDimens.from(14).percentage().sp
                        )

                        val size1 = AppDimens.from(48).percentage().dp
                        DemoTile(size = size1, label = "48dp → ${size1.value.toInt()}dp")

                        Text(
                            "Código: AppDimens.from(48).percentage().dp",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).percentage().sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(8).percentage().dp))

                        Text(
                            "Método 2: Usando .dynamic() (compatibilidade legada)",
                            fontWeight = FontWeight.Medium,
                            fontSize = AppDimens.from(14).percentage().sp
                        )

                        val size2 = AppDimens.dynamic(48).dp
                        DemoTile(size = size2, label = "48dp → ${size2.value.toInt()}dp")

                        Text(
                            "Código: AppDimens.dynamic(48).dp",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).percentage().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Dimensões baseadas em porcentagem
            item {
                UsageCard(title = "Dimensões Baseadas em Porcentagem") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).percentage().dp
                        )
                    ) {
                        Text(
                            "A estratégia PERCENTAGE é ideal para calcular porcentagens da tela:",
                            fontSize = AppDimens.from(13).percentage().sp
                        )

                        // 10% da largura da tela
                        val width10 = AppDimens.dynamicPercentageDp(0.10f, ScreenType.LOWEST)
                        // 20% da altura da tela
                        val height20 = AppDimens.dynamicPercentageDp(0.20f, ScreenType.HIGHEST)

                        Text(
                            "10% da largura da tela: ${width10.value.toInt()}dp",
                            fontSize = AppDimens.from(13).percentage().sp
                        )
                        Text(
                            "20% da altura da tela: ${height20.value.toInt()}dp",
                            fontSize = AppDimens.from(13).percentage().sp
                        )

                        Box(
                            modifier = Modifier
                                .width(width10)
                                .height(height20)
                                .background(
                                    Color(0xFF81C784),
                                    shape = RoundedCornerShape(AppDimens.from(8).percentage().dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("10% × 20%", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Text(
                            "Código: AppDimens.dynamicPercentageDp(0.10f, ScreenType.LOWEST)",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(10).percentage().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Ajuste ao vivo
            item {
                UsageCard(title = "Fator de Escala ao Vivo") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).percentage().dp
                        )
                    ) {
                        Text(
                            "Ajustar fator de porcentagem personalizado (0.5x a 2.0x):",
                            fontSize = AppDimens.from(14).percentage().sp,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            "Fator atual: ${"%.2f".format(percentageFactor)}x",
                            fontSize = AppDimens.from(13).percentage().sp
                        )

                        val adjustedSize = AppDimens.dynamicPercentageDp(percentageFactor)

                        Box(
                            modifier = Modifier
                                .size(adjustedSize)
                                .background(
                                    Color(0xFFFFB74D),
                                    shape = RoundedCornerShape(AppDimens.from(8).percentage().dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "${adjustedSize.value.toInt()}dp",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Slider(
                            value = percentageFactor,
                            onValueChange = { percentageFactor = it },
                            valueRange = 0.5f..2.0f,
                            steps = 14
                        )
                    }
                }
            }

            // Comparação de tipo de tela
            item {
                UsageCard(title = "Comparação de Tipo de Tela") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).percentage().dp
                        )
                    ) {
                        Button(
                            onClick = {
                                currentScreenType = if (currentScreenType == ScreenType.LOWEST)
                                    ScreenType.HIGHEST else ScreenType.LOWEST
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Alternar ScreenType: $currentScreenType")
                        }

                        val percentageLowest = AppDimens.from(48)
                            .percentage()
                            .type(ScreenType.LOWEST)
                            .dp

                        val percentageHighest = AppDimens.from(48)
                            .percentage()
                            .type(ScreenType.HIGHEST)
                            .dp

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                AppDimens.from(12).percentage().dp
                            )
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DemoTile(size = percentageLowest, label = "LOWEST")
                                Text(
                                    "${percentageLowest.value.toInt()}dp",
                                    fontSize = AppDimens.from(12).percentage().sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DemoTile(size = percentageHighest, label = "HIGHEST")
                                Text(
                                    "${percentageHighest.value.toInt()}dp",
                                    fontSize = AppDimens.from(12).percentage().sp
                                )
                            }
                        }
                    }
                }
            }

            // Comparação visual com outras estratégias
            item {
                UsageCard(title = "⚖️ Comparação com Outras Estratégias") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).percentage().dp
                        )
                    ) {
                        Text(
                            "Valor base: 48dp",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).percentage().sp
                        )

                        val baseValue = 48
                        val percentageSize = AppDimens.from(baseValue).percentage().dp
                        val defaultSize = AppDimens.from(baseValue).default().dp
                        val balancedSize = AppDimens.from(baseValue).balanced().dp

                        ComparisonRow("PERCENTAGE (esta)", percentageSize, percentageSize)
                        ComparisonRow("DEFAULT", defaultSize, percentageSize)
                        ComparisonRow("BALANCED", balancedSize, percentageSize)

                        Text(
                            "Nota: PERCENTAGE fornece escalonamento 100% linear, tornando-o mais agressivo que DEFAULT.",
                            fontSize = AppDimens.from(11).percentage().sp,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Recomendações
            item {
                InfoCard(
                    title = "💡 Recomendações",
                    subtitle = "Quando usar a estratégia PERCENTAGE"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).percentage().dp
                        )
                    ) {
                        RecommendationItem("✅ Layouts fluidos/responsivos")
                        RecommendationItem("✅ Contêineres proporcionais")
                        RecommendationItem("✅ Imagens que escalam totalmente")
                        RecommendationItem("✅ Elementos correspondentes ao tamanho da tela")

                        Spacer(modifier = Modifier.height(AppDimens.from(8).percentage().dp))

                        Text(
                            "Considere alternativas:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(13).percentage().sp
                        )
                        RecommendationItem("🔄 DEFAULT para escalonamento controlado")
                        RecommendationItem("🔄 BALANCED para apps multi-dispositivo")
                        RecommendationItem("🔄 LOGARITHMIC para evitar super-dimensionamento")

                        Spacer(modifier = Modifier.height(AppDimens.from(8).percentage().dp))

                        Text(
                            "⚠️ Aviso: Pode causar super-dimensionamento em tablets/TVs",
                            fontSize = AppDimens.from(12).percentage().sp,
                            color = Color(0xFFE64A19),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.from(6).percentage().dp
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.from(10).percentage().dp)
    ) {
        Column(modifier = Modifier.padding(AppDimens.from(12).percentage().dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = AppDimens.from(16).percentage().sp)
            Spacer(Modifier.height(AppDimens.from(6).percentage().dp))
            Text(subtitle, fontSize = AppDimens.from(12).percentage().sp, color = Color.Gray)
            Spacer(Modifier.height(AppDimens.from(8).percentage().dp))
            content()
        }
    }
}

@Composable
private fun UsageCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.from(12).percentage().dp),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.from(8).percentage().dp)
    ) {
        Column(modifier = Modifier.padding(AppDimens.from(14).percentage().dp)) {
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                fontSize = AppDimens.from(15).percentage().sp
            )
            Spacer(Modifier.height(AppDimens.from(8).percentage().dp))
            content()
        }
    }
}

@Composable
private fun DemoTile(size: Dp, label: String) {
    Box(
        modifier = Modifier
            .size(size)
            .background(
                Color(0xFFE8F5E9),
                shape = RoundedCornerShape(AppDimens.from(6).percentage().dp)
            )
            .border(
                AppDimens.from(2).percentage().dp,
                Color(0xFF4CAF50),
                RoundedCornerShape(AppDimens.from(6).percentage().dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = AppDimens.from(11).percentage().sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ComparisonRow(label: String, value: Dp, baseline: Dp) {
    val diff = ((value.value - baseline.value) / baseline.value * 100).toInt()
    val diffText = when {
        diff > 0 -> "+$diff%"
        diff < 0 -> "$diff%"
        else -> "0%"
    }
    val diffColor = when {
        diff > 5 -> Color(0xFFD32F2F)
        diff < -5 -> Color(0xFF388E3C)
        else -> Color.Gray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = AppDimens.from(4).percentage().dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontSize = AppDimens.from(13).percentage().sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            "${"%.1f".format(value.value)}dp",
            fontSize = AppDimens.from(13).percentage().sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(AppDimens.from(60).percentage().dp)
        )
        Text(
            diffText,
            fontSize = AppDimens.from(12).percentage().sp,
            color = diffColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(AppDimens.from(50).percentage().dp)
        )
    }
}

@Composable
private fun RecommendationItem(text: String) {
    Text(
        text,
        fontSize = AppDimens.from(13).percentage().sp,
        modifier = Modifier.padding(vertical = AppDimens.from(2).percentage().dp)
    )
}

@Preview(
    showBackground = true,
    device = "id:pixel_5",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun PercentageDemoPreview() {
    MaterialTheme {
        PercentageDemoScreen()
    }
}

