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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens

/**
 * [EN] Activity demonstrating the LOGARITHMIC scaling strategy (Perceptual Weber-Fechner).
 * [PT] Activity demonstrando a estratégia de escalonamento LOGARITHMIC (Perceptual Weber-Fechner).
 *
 * Estratégia LOGARITHMIC:
 * - Fórmula: f(x) = x × (1 + sensitivity × ln(W / W₀))
 * - Crescimento logarítmico puro em todas as telas
 * - Máximo controle em telas grandes (previne super-dimensionamento)
 * - Baseado na Lei de Weber-Fechner (psicologia perceptual)
 * - Pode reduzir tamanhos notavelmente em telefones
 * - Melhor para: TVs, tablets muito grandes, máximo controle
 */
@OptIn(ExperimentalMaterial3Api::class)
class LogarithmicExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LogarithmicDemoScreen()
            }
        }
    }
}

@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogarithmicDemoScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    var sensitivity by remember { mutableStateOf(0.4f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Estratégia LOGARITHMIC",
                        fontSize = AppDimens.from(18).logarithmic().sp
                    )
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(AppDimens.from(12).logarithmic().dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.from(12).logarithmic().dp)
        ) {
            item {
                InfoCard(
                    title = "Métricas do Dispositivo",
                    subtitle = "Características atuais da tela"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).logarithmic().dp
                        )
                    ) {
                        Text("Menor Largura: ${configuration.smallestScreenWidthDp}dp")
                        Text("Densidade: ${"%.2f".format(density.density)} px/dp")
                        val scaleFactor = configuration.smallestScreenWidthDp / 360f
                        Text("Escala linear seria: ${"%.2f".format(scaleFactor)}x")
                        val logScale = 1f + (0.4f * kotlin.math.ln(scaleFactor.coerceAtLeast(1f)))
                        Text(
                            "Escala logarítmica: ${"%.2f".format(logScale)}x",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                InfoCard(
                    title = "📖 Estratégia LOGARITHMIC",
                    subtitle = "Escalonamento perceptual Weber-Fechner"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).logarithmic().dp
                        )
                    ) {
                        Text(
                            "LOGARITHMIC fornece controle máximo em telas grandes, prevenindo inchaço de UI.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(AppDimens.from(4).logarithmic().dp))

                        Text("Fórmula:", fontWeight = FontWeight.Bold)
                        Text(
                            "f(x) = x × (1 + k × ln(W / W₀))",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(12).logarithmic().sp
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(4).logarithmic().dp))
                        Text("Características:", fontWeight = FontWeight.Bold)
                        Text("• Crescimento logarítmico puro em todas as telas")
                        Text("• Controle máximo em tablets/TVs")
                        Text("• Baseado na Lei de Weber-Fechner")
                        Text("• Pode reduzir tamanhos em telefones")

                        Spacer(modifier = Modifier.height(AppDimens.from(4).logarithmic().dp))
                        Text("Melhor para:", fontWeight = FontWeight.Bold)
                        Text("• Aplicações para TV")
                        Text("• Tablets muito grandes (10\"+)")
                        Text("• Quando controle máximo é necessário")
                    }
                }
            }

            item {
                UsageCard(title = "Uso Básico") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).logarithmic().dp
                        )
                    ) {
                        val size = AppDimens.from(48).logarithmic().dp
                        DemoTile(size = size, label = "48dp → ${size.value.toInt()}dp")
                        Text(
                            "Código: AppDimens.from(48).logarithmic().dp",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).logarithmic().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                UsageCard(title = "Controle de Sensibilidade") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).logarithmic().dp
                        )
                    ) {
                        Text("Ajustar sensibilidade: ${"%.2f".format(sensitivity)}")

                        val sizeWithSensitivity = AppDimens.from(48)
                            .logarithmic(sensitivity = sensitivity)
                            .dp

                        DemoTile(
                            size = sizeWithSensitivity,
                            label = "${sizeWithSensitivity.value.toInt()}dp"
                        )

                        Slider(
                            value = sensitivity,
                            onValueChange = { sensitivity = it },
                            valueRange = 0.1f..0.8f,
                            steps = 13
                        )

                        Text(
                            "Menor sensibilidade = escalonamento mais conservador",
                            fontSize = AppDimens.from(11).logarithmic().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                UsageCard(title = "⚖️ Comparação com Outras Estratégias") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).logarithmic().dp
                        )
                    ) {
                        Text("Valor base: 48dp", fontWeight = FontWeight.Bold)

                        val logarithmicSize = AppDimens.from(48).logarithmic().dp
                        val balancedSize = AppDimens.from(48).balanced().dp
                        val percentageSize = AppDimens.from(48).percentage().dp

                        ComparisonRow("LOGARITHMIC (esta)", logarithmicSize, logarithmicSize)
                        ComparisonRow("BALANCED", balancedSize, logarithmicSize)
                        ComparisonRow("PERCENTAGE", percentageSize, logarithmicSize)

                        Text(
                            "LOGARITHMIC fornece o controle mais forte, prevenindo super-dimensionamento em tablets/TVs",
                            fontSize = AppDimens.from(11).logarithmic().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                InfoCard(
                    title = "💡 Recomendações",
                    subtitle = "Quando usar LOGARITHMIC"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).logarithmic().dp
                        )
                    ) {
                        RecommendationItem("✅ Aplicações para TV (Android TV)")
                        RecommendationItem("✅ Tablets grandes (telas 10\"+)")
                        RecommendationItem("✅ Quando inchaço de UI é uma preocupação")
                        RecommendationItem("✅ Interfaces com muito conteúdo")

                        Spacer(modifier = Modifier.height(AppDimens.from(8).logarithmic().dp))

                        Text("Considere alternativas:", fontWeight = FontWeight.Bold)
                        RecommendationItem("• Apps multi-dispositivo → BALANCED")
                        RecommendationItem("• Apps apenas para telefone → DEFAULT")
                        RecommendationItem("⚠️ Pode deixar UI muito pequena em telefones")
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
            defaultElevation = AppDimens.from(6).logarithmic().dp
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.from(10).logarithmic().dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EAF6))
    ) {
        Column(modifier = Modifier.padding(AppDimens.from(12).logarithmic().dp)) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = AppDimens.from(16).logarithmic().sp
            )
            Spacer(Modifier.height(AppDimens.from(6).logarithmic().dp))
            Text(subtitle, fontSize = AppDimens.from(12).logarithmic().sp, color = Color.Gray)
            Spacer(Modifier.height(AppDimens.from(8).logarithmic().dp))
            content()
        }
    }
}

@Composable
private fun UsageCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.from(12).logarithmic().dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = AppDimens.from(8).logarithmic().dp
        )
    ) {
        Column(modifier = Modifier.padding(AppDimens.from(14).logarithmic().dp)) {
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                fontSize = AppDimens.from(15).logarithmic().sp
            )
            Spacer(Modifier.height(AppDimens.from(8).logarithmic().dp))
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
                Color(0xFFC5CAE9),
                shape = RoundedCornerShape(AppDimens.from(6).logarithmic().dp)
            )
            .border(
                AppDimens.from(2).logarithmic().dp,
                Color(0xFF3F51B5),
                RoundedCornerShape(AppDimens.from(6).logarithmic().dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = AppDimens.from(11).logarithmic().sp, fontWeight = FontWeight.Medium)
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
            .padding(vertical = AppDimens.from(4).logarithmic().dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = AppDimens.from(13).logarithmic().sp, modifier = Modifier.weight(1f))
        Text(
            "${"%.1f".format(value.value)}dp",
            fontSize = AppDimens.from(13).logarithmic().sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            diffText,
            fontSize = AppDimens.from(12).logarithmic().sp,
            color = diffColor,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RecommendationItem(text: String) {
    Text(
        text,
        fontSize = AppDimens.from(13).logarithmic().sp,
        modifier = Modifier.padding(vertical = AppDimens.from(2).logarithmic().dp)
    )
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun LogarithmicDemoPreview() {
    MaterialTheme { LogarithmicDemoScreen() }
}

