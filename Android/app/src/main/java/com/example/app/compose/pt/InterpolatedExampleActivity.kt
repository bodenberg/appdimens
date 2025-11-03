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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens

/**
 * Estratégia INTERPOLATED: Linear moderado (50% do crescimento linear)
 * Fórmula: f(x) = x + ((x × W/W₀) - x) × 0.5
 * Melhor para: Necessidades de escalonamento moderado, crescimento balanceado
 */
@OptIn(ExperimentalMaterial3Api::class)
class InterpolatedExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { InterpolatedDemoScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterpolatedDemoScreen() {
    Scaffold(topBar = { TopAppBar(title = { Text("Estratégia INTERPOLATED") }) }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(AppDimens.from(12).interpolated().dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.from(12).interpolated().dp)
        ) {
            item {
                InfoCard("📖 Estratégia INTERPOLATED", "Escalonamento linear 50% moderado") {
                    Text("Fórmula: f(x) = x + ((x × W/W₀) - x) × 0.5")
                    Text("50% do crescimento linear - mais suave que linear, mais forte que logarítmico")
                    Text("Melhor para: Escalonamento moderado, crescimento balanceado entre extremos")
                }
            }
            item {
                UsageCard("Uso Básico") {
                    val size = AppDimens.from(48).interpolated().dp
                    DemoTile(size, "48dp → ${size.value.toInt()}dp")
                    Text(
                        "Código: AppDimens.from(48).interpolated().dp",
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        fontSize = AppDimens.from(10).interpolated().sp,
                        color = Color.Gray
                    )
                }
            }
            item {
                UsageCard("⚖️ Comparação") {
                    val interpolatedSize = AppDimens.from(48).interpolated().dp
                    val percentageSize = AppDimens.from(48).percentage().dp
                    val defaultSize = AppDimens.from(48).default().dp
                    ComparisonRow("INTERPOLATED (esta)", interpolatedSize, interpolatedSize)
                    ComparisonRow("PERCENTAGE", percentageSize, interpolatedSize)
                    ComparisonRow("DEFAULT", defaultSize, interpolatedSize)
                }
            }
            item {
                InfoCard("💡 Casos de Uso", "Quando usar INTERPOLATED") {
                    RecommendationItem("✅ Necessidades de escalonamento moderado")
                    RecommendationItem("✅ Entre conservador e agressivo")
                    RecommendationItem("✅ Propósito geral com crescimento gentil")
                }
            }
        }
    }
}

@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable ColumnScope.() -> Unit) =
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F2F1))
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Bold); Text(
            subtitle,
            color = Color.Gray
        ); Spacer(Modifier.height(8.dp)); content()
        }
    }

@Composable
private fun UsageCard(title: String, content: @Composable ColumnScope.() -> Unit) =
    Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(8.dp)) {
        Column(Modifier.padding(14.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold); Spacer(
            Modifier.height(8.dp)
        ); content()
        }
    }

@Composable
private fun DemoTile(size: Dp, label: String) =
    Box(
        Modifier
            .size(size)
            .background(Color(0xFFB2DFDB), RoundedCornerShape(6.dp))
            .border(2.dp, Color(0xFF009688), RoundedCornerShape(6.dp)), Alignment.Center
    ) {
        Text(label, fontWeight = FontWeight.Medium)
    }

@Composable
private fun ComparisonRow(label: String, value: Dp, baseline: Dp) {
    val diff = ((value.value - baseline.value) / baseline.value * 100).toInt()
    Row(Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp), Arrangement.SpaceBetween) {
        Text(label, Modifier.weight(1f)); Text(
        "${value.value.toInt()}dp",
        fontWeight = FontWeight.Medium
    )
        Text(
            if (diff != 0) "${if (diff > 0) "+" else ""}$diff%" else "0%",
            color = if (diff > 5) Color.Red else if (diff < -5) Color.Green else Color.Gray
        )
    }
}

@Composable
private fun RecommendationItem(text: String) = Text(text, Modifier.padding(vertical = 2.dp))

