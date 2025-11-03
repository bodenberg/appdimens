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
import com.appdimens.library.ScreenType

/**
 * [EN] Activity demonstrating the BALANCED scaling strategy (Perceptual Hybrid).
 *
 * BALANCED Strategy ⭐ RECOMMENDED:
 * - Formula:
 *   • if W < 480: f(x) = x × (W / W₀) [Linear on phones]
 *   • if W ≥ 480: f(x) = x × (1.6 + sensitivity × ln(1 + (W-480)/W₀)) [Log on tablets]
 * - Linear scaling on phones (< 480dp)
 * - Logarithmic scaling on tablets/TVs (≥ 480dp)
 * - Smooth transition at 480dp breakpoint
 * - Prevents oversizing on large screens
 * - Best for: Multi-device apps, buttons, spacing
 *
 * [PT] Activity demonstrando a estratégia de escalonamento BALANCED (Híbrido Perceptual).
 *
 * Estratégia BALANCED ⭐ RECOMENDADA:
 * - Fórmula:
 *   • se W < 480: f(x) = x × (W / W₀) [Linear em telefones]
 *   • se W ≥ 480: f(x) = x × (1.6 + sensitivity × ln(1 + (W-480)/W₀)) [Log em tablets]
 * - Escalonamento linear em telefones (< 480dp)
 * - Escalonamento logarítmico em tablets/TVs (≥ 480dp)
 * - Transição suave no ponto de 480dp
 * - Previne super-dimensionamento em telas grandes
 * - Melhor para: Apps multi-dispositivo, botões, espaçamento
 */
@OptIn(ExperimentalMaterial3Api::class)
class BalancedExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                BalancedDemoScreen()
            }
        }
    }
}

@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalancedDemoScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    var sensitivity by remember { mutableStateOf(0.4f) }
    var transitionPoint by remember { mutableStateOf(480f) }

    val isPhone = configuration.smallestScreenWidthDp < transitionPoint
    val deviceCategory = when {
        configuration.smallestScreenWidthDp < 360 -> "Telefone Pequeno"
        configuration.smallestScreenWidthDp < 480 -> "Telefone"
        configuration.smallestScreenWidthDp < 720 -> "Telefone Grande / Tablet Pequeno"
        configuration.smallestScreenWidthDp < 960 -> "Tablet"
        else -> "Tablet Grande / TV"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Estratégia BALANCED ⭐",
                        fontSize = AppDimens.from(18).balanced().sp
                    )
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(AppDimens.from(12).balanced().dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.from(12).balanced().dp)
        ) {
            // Métricas do dispositivo
            item {
                InfoCard(
                    title = "Métricas do Dispositivo",
                    subtitle = "Características atuais da tela"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).balanced().dp
                        )
                    ) {
                        Text("Largura da Tela: ${configuration.screenWidthDp}dp")
                        Text("Altura da Tela: ${configuration.screenHeightDp}dp")
                        Text("Menor Largura: ${configuration.smallestScreenWidthDp}dp")
                        Text("Densidade: ${"%.2f".format(density.density)} (px por dp)")
                        Text(
                            "Categoria: $deviceCategory",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            "Modo de Escala: ${if (isPhone) "LINEAR (telefone)" else "LOGARÍTMICO (tablet/TV)"}",
                            fontWeight = FontWeight.Bold,
                            color = if (isPhone) Color(0xFF2196F3) else Color(0xFFFF9800)
                        )
                    }
                }
            }

            // Explicação da estratégia
            item {
                InfoCard(
                    title = "📖 Estratégia BALANCED ⭐",
                    subtitle = "Híbrido Perceptual: O melhor dos dois mundos"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).balanced().dp
                        )
                    ) {
                        Text(
                            "BALANCED é a estratégia RECOMENDADA para a maioria dos apps multi-dispositivo.",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(AppDimens.from(4).balanced().dp))

                        Text(
                            "Fórmula:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).balanced().sp
                        )
                        Text(
                            "• se W < 480dp: f(x) = x × (W / W₀)",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).balanced().sp
                        )
                        Text(
                            "• se W ≥ 480dp: f(x) = x × (1.6 + k×ln(...))",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).balanced().sp
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(4).balanced().dp))
                        Text(
                            "Características:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).balanced().sp
                        )
                        Text(
                            "• Linear em telefones (< 480dp)",
                            fontSize = AppDimens.from(13).balanced().sp
                        )
                        Text(
                            "• Logarítmico em tablets/TVs (≥ 480dp)",
                            fontSize = AppDimens.from(13).balanced().sp
                        )
                        Text(
                            "• Transição suave no ponto de quebra",
                            fontSize = AppDimens.from(13).balanced().sp
                        )
                        Text(
                            "• Previne super-dimensionamento",
                            fontSize = AppDimens.from(13).balanced().sp
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(4).balanced().dp))
                        Text(
                            "Melhor para:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).balanced().sp
                        )
                        Text(
                            "• Aplicações multi-dispositivo ⭐",
                            fontSize = AppDimens.from(13).balanced().sp
                        )
                        Text("• Botões e controles", fontSize = AppDimens.from(13).balanced().sp)
                        Text("• Espaçamento e margens", fontSize = AppDimens.from(13).balanced().sp)
                        Text("• UI de propósito geral", fontSize = AppDimens.from(13).balanced().sp)
                    }
                }
            }

            // Uso básico
            item {
                UsageCard(title = "Uso Básico") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).balanced().dp
                        )
                    ) {
                        Text(
                            "Usando .balanced() para escalonamento adaptativo:",
                            fontWeight = FontWeight.Medium,
                            fontSize = AppDimens.from(14).balanced().sp
                        )

                        val size = AppDimens.from(48).balanced().dp
                        DemoTile(size = size, label = "48dp → ${size.value.toInt()}dp")

                        Text(
                            "Código: AppDimens.from(48).balanced().dp",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).balanced().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Controle de sensibilidade
            item {
                UsageCard(title = "Controle de Sensibilidade") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).balanced().dp
                        )
                    ) {
                        Text(
                            "Ajustar sensibilidade logarítmica (apenas tablets/TVs):",
                            fontSize = AppDimens.from(14).balanced().sp,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            "Sensibilidade atual: ${"%.2f".format(sensitivity)}",
                            fontSize = AppDimens.from(13).balanced().sp
                        )

                        val sizeCustomSensitivity = AppDimens.from(48)
                            .balanced(sensitivity = sensitivity)
                            .dp

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                AppDimens.from(12).balanced().dp
                            )
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DemoTile(size = sizeCustomSensitivity, label = "Personalizado")
                                Text(
                                    "${sizeCustomSensitivity.value.toInt()}dp",
                                    fontSize = AppDimens.from(12).balanced().sp
                                )
                            }
                        }

                        Slider(
                            value = sensitivity,
                            onValueChange = { sensitivity = it },
                            valueRange = 0.1f..0.8f,
                            steps = 13
                        )

                        Text(
                            "Nota: Sensibilidade afeta apenas tablets/TVs (≥480dp)",
                            fontSize = AppDimens.from(11).balanced().sp,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Visualização do ponto de transição
            item {
                UsageCard(title = "Visualização do Ponto de Transição") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).balanced().dp
                        )
                    ) {
                        Text(
                            "O ponto de transição (padrão 480dp) determina onde a estratégia muda de linear para logarítmica:",
                            fontSize = AppDimens.from(13).balanced().sp
                        )

                        val currentWidth = configuration.smallestScreenWidthDp
                        val progress = (currentWidth.toFloat() / 1000f).coerceIn(0f, 1f)

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(AppDimens.from(60).balanced().dp)
                        ) {
                            // Gradiente de fundo
                            Row(modifier = Modifier.fillMaxSize()) {
                                Box(
                                    modifier = Modifier
                                        .weight(0.48f)
                                        .fillMaxHeight()
                                        .background(Color(0xFFBBDEFB))
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(0.52f)
                                        .fillMaxHeight()
                                        .background(Color(0xFFFFE0B2))
                                )
                            }

                            // Labels
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppDimens.from(8).balanced().dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("< 480dp\nLINEAR", fontSize = AppDimens.from(11).balanced().sp)
                                Text(
                                    "480dp\nTRANSIÇÃO",
                                    fontSize = AppDimens.from(10).balanced().sp,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                                Text("≥ 480dp\nLOG", fontSize = AppDimens.from(11).balanced().sp)
                            }

                            // Indicador de posição atual
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .fillMaxHeight()
                                    .border(
                                        width = AppDimens.from(2).balanced().dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(0.dp)
                                    )
                            )
                        }

                        Text(
                            "Seu dispositivo: ${currentWidth}dp (${if (isPhone) "Zona linear" else "Zona logarítmica"})",
                            fontSize = AppDimens.from(12).balanced().sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Comparação visual
            item {
                UsageCard(title = "⚖️ Comparação com Outras Estratégias") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).balanced().dp
                        )
                    ) {
                        Text(
                            "Valor base: 48dp",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).balanced().sp
                        )

                        val baseValue = 48
                        val balancedSize = AppDimens.from(baseValue).balanced().dp
                        val percentageSize = AppDimens.from(baseValue).percentage().dp
                        val defaultSize = AppDimens.from(baseValue).default().dp
                        val logarithmicSize = AppDimens.from(baseValue).logarithmic().dp

                        ComparisonRow("BALANCED ⭐ (esta)", balancedSize, balancedSize)
                        ComparisonRow("PERCENTAGE", percentageSize, balancedSize)
                        ComparisonRow("DEFAULT", defaultSize, balancedSize)
                        ComparisonRow("LOGARITHMIC", logarithmicSize, balancedSize)

                        Text(
                            if (isPhone)
                                "Em telefones: BALANCED se comporta similarmente ao PERCENTAGE (linear)"
                            else
                                "Em tablets: BALANCED previne super-dimensionamento vs PERCENTAGE (~${((percentageSize.value - balancedSize.value) / percentageSize.value * 100).toInt()}% redução)",
                            fontSize = AppDimens.from(11).balanced().sp,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Exemplos práticos
            item {
                UsageCard(title = "Exemplos Práticos") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).balanced().dp
                        )
                    ) {
                        Text(
                            "Elementos comuns de UI com BALANCED:",
                            fontWeight = FontWeight.Medium,
                            fontSize = AppDimens.from(14).balanced().sp
                        )

                        // Botão
                        Button(
                            onClick = { },
                            modifier = Modifier
                                .height(AppDimens.from(48).balanced().dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Botão (altura 48dp)",
                                fontSize = AppDimens.from(16).balanced().sp
                            )
                        }

                        // Card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(AppDimens.from(120).balanced().dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(AppDimens.from(16).balanced().dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Card com padding balanceado",
                                    fontSize = AppDimens.from(14).balanced().sp
                                )
                            }
                        }
                    }
                }
            }

            // Recomendações
            item {
                InfoCard(
                    title = "💡 Recomendações",
                    subtitle = "Por que BALANCED é a melhor escolha"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).balanced().dp
                        )
                    ) {
                        RecommendationItem("✅ Escolha padrão para novos apps")
                        RecommendationItem("✅ Previne inchaço de UI em tablets")
                        RecommendationItem("✅ Mantém boas proporções em telefones")
                        RecommendationItem("✅ Transições suaves entre dispositivos")
                        RecommendationItem("✅ Baseado em pesquisa perceptual")

                        Spacer(modifier = Modifier.height(AppDimens.from(8).balanced().dp))

                        Text(
                            "Considere alternativas apenas se:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(13).balanced().sp
                        )
                        RecommendationItem("• App apenas para telefone → DEFAULT")
                        RecommendationItem("• Contêineres fluidos → PERCENTAGE")
                        RecommendationItem("• Máximo controle em TVs → LOGARITHMIC")
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.from(6).balanced().dp),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.from(10).balanced().dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDE7))
    ) {
        Column(modifier = Modifier.padding(AppDimens.from(12).balanced().dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = AppDimens.from(16).balanced().sp)
            Spacer(Modifier.height(AppDimens.from(6).balanced().dp))
            Text(subtitle, fontSize = AppDimens.from(12).balanced().sp, color = Color.Gray)
            Spacer(Modifier.height(AppDimens.from(8).balanced().dp))
            content()
        }
    }
}

@Composable
private fun UsageCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.from(12).balanced().dp),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.from(8).balanced().dp)
    ) {
        Column(modifier = Modifier.padding(AppDimens.from(14).balanced().dp)) {
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                fontSize = AppDimens.from(15).balanced().sp
            )
            Spacer(Modifier.height(AppDimens.from(8).balanced().dp))
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
                Color(0xFFFFF9C4),
                shape = RoundedCornerShape(AppDimens.from(6).balanced().dp)
            )
            .border(
                AppDimens.from(2).balanced().dp,
                Color(0xFFFDD835),
                RoundedCornerShape(AppDimens.from(6).balanced().dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = AppDimens.from(11).balanced().sp, fontWeight = FontWeight.Medium)
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
            .padding(vertical = AppDimens.from(4).balanced().dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontSize = AppDimens.from(13).balanced().sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            "${"%.1f".format(value.value)}dp",
            fontSize = AppDimens.from(13).balanced().sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(AppDimens.from(60).balanced().dp)
        )
        Text(
            diffText,
            fontSize = AppDimens.from(12).balanced().sp,
            color = diffColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(AppDimens.from(50).balanced().dp)
        )
    }
}

@Composable
private fun RecommendationItem(text: String) {
    Text(
        text,
        fontSize = AppDimens.from(13).balanced().sp,
        modifier = Modifier.padding(vertical = AppDimens.from(2).balanced().dp)
    )
}

@Preview(
    showBackground = true,
    device = "id:pixel_5",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun BalancedDemoPreview() {
    MaterialTheme {
        BalancedDemoScreen()
    }
}

