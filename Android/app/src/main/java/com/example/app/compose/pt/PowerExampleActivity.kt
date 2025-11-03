/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.compose.pt

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens

/**
 * Estratégia POWER (Lei de Potência de Stevens):
 * - Fórmula: f(x) = x × (W / W₀)^expoente
 * - Escalonamento perceptual científico
 * - Expoente configurável (0.5-0.9 típico)
 * - Melhor para: Propósito geral, apps baseados científicamente
 */
@OptIn(ExperimentalMaterial3Api::class)
class PowerExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { PowerDemoScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PowerDemoScreen() {
    val configuration = LocalConfiguration.current
    var exponent by remember { mutableStateOf(0.75f) }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "Estratégia POWER",
                    fontSize = AppDimens.from(18).power().sp
                )
            })
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(AppDimens.from(12).power().dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.from(12).power().dp)
        ) {
            item {
                InfoCard("📖 Estratégia POWER", "Escalonamento Lei de Potência de Stevens") {
                    Text("Fórmula: f(x) = x × (W / W₀)^n")
                    Text("Expoente controla agressividade do escalonamento")
                    Text("• n = 1.0: Linear (igual a PERCENTAGE)")
                    Text("• n = 0.75: Moderado (recomendado)")
                    Text("• n = 0.5: Conservador")
                    Text("Melhor para: Propósito geral, escalonamento baseado científicamente")
                }
            }

            item {
                UsageCard("Uso Básico & Controle de Expoente") {
                    val size = AppDimens.from(48).power(exponent = exponent).dp
                    DemoTile(size, "48dp → ${size.value.toInt()}dp")
                    Text("Expoente: ${"%.2f".format(exponent)}")
                    Slider(
                        value = exponent,
                        onValueChange = { exponent = it },
                        valueRange = 0.5f..1.0f,
                        steps = 9
                    )
                    Text(
                        "Código: AppDimens.from(48).power(exponent = ${"%.1f".format(exponent)}).dp",
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        fontSize = AppDimens.from(10).power().sp,
                        color = Color.Gray
                    )
                }
            }

            item {
                UsageCard("⚖️ Comparação") {
                    Text("Base: 48dp", fontWeight = FontWeight.Bold)
                    val powerSize = AppDimens.from(48).power(exponent).dp
                    val percentageSize = AppDimens.from(48).percentage().dp
                    val balancedSize = AppDimens.from(48).balanced().dp
                    ComparisonRow("POWER (esta)", powerSize, powerSize)
                    ComparisonRow("PERCENTAGE", percentageSize, powerSize)
                    ComparisonRow("BALANCED", balancedSize, powerSize)
                }
            }

            item {
                InfoCard("💡 Recomendações", "Quando usar POWER") {
                    RecommendationItem("✅ Aplicações de propósito geral")
                    RecommendationItem("✅ Quando base científica é importante")
                    RecommendationItem("✅ Necessidades de escalonamento configurável")
                    RecommendationItem("• Expoente menor = mais conservador")
                    RecommendationItem("• Expoente maior = mais agressivo")
                }
            }
        }
    }
}

@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFCE4EC))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = AppDimens.from(12).power().sp, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun UsageCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun DemoTile(size: Dp, label: String) {
    Box(
        Modifier
            .size(size)
            .background(Color(0xFFF8BBD0), RoundedCornerShape(6.dp))
            .border(2.dp, Color(0xFFE91E63), RoundedCornerShape(6.dp)), Alignment.Center
    ) {
        Text(label, fontSize = AppDimens.from(11).power().sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ComparisonRow(label: String, value: Dp, baseline: Dp) {
    val diff = ((value.value - baseline.value) / baseline.value * 100).toInt()
    Row(Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp), Arrangement.SpaceBetween) {
        Text(label, modifier = Modifier.weight(1f))
        Text("${"%.1f".format(value.value)}dp", fontWeight = FontWeight.Medium)
        Text(
            if (diff > 0) "+$diff%" else if (diff < 0) "$diff%" else "0%",
            color = if (diff > 5) Color.Red else if (diff < -5) Color.Green else Color.Gray,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RecommendationItem(text: String) {
    Text(text, modifier = Modifier.padding(vertical = 2.dp))
}

