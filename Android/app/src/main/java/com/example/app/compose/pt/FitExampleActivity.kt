/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.compose.pt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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

/** Estratégia FIT (Letterbox): f(x) = x × min(W/W₀, H/H₀) - Ajuste de jogo, garante que o conteúdo caiba */
@OptIn(ExperimentalMaterial3Api::class)
class FitExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContent { MaterialTheme { FitDemoScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitDemoScreen() {
    Scaffold(topBar = { TopAppBar(title = { Text("Estratégia FIT (Letterbox)") }) }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InfoCard(
                    "📖 FIT (Letterbox)",
                    "Ajuste de jogo - garante que conteúdo caiba"
                ) {
                    Text("Fórmula: f(x) = x × min(W/W₀, H/H₀)"); Text("Usa proporção menor"); Text("Garante que tudo caiba (pode deixar espaço)"); Text(
                    "Melhor para: Jogos, conteúdo tela cheia"
                )
                }
            }
            item {
                UsageCard("Uso") {
                    val s = AppDimens.from(48).fit().dp; DemoTile(
                    s,
                    "${s.value.toInt()}dp"
                ); Text(
                    "Código: .fit().dp",
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    color = Color.Gray
                )
                }
            }
            item {
                UsageCard("Comparar") {
                    ComparisonRow(
                        "FIT (esta)",
                        AppDimens.from(48).fit().dp,
                        AppDimens.from(48).fit().dp
                    ); ComparisonRow(
                    "FILL",
                    AppDimens.from(48).fill().dp,
                    AppDimens.from(48).fit().dp
                ); ComparisonRow(
                    "PERCENTAGE",
                    AppDimens.from(48).percentage().dp,
                    AppDimens.from(48).fit().dp
                )
                }
            }
            item {
                InfoCard(
                    "💡 Casos de Uso",
                    ""
                ) {
                    Text("✅ Modo letterbox de jogo"); Text("✅ Conteúdo que deve caber"); Text("✅ Layouts tela cheia"); Text(
                    "⚠️ Pode deixar espaço vazio"
                )
                }
            }
        }
    }
}

@Composable
private fun InfoCard(t: String, s: String, c: @Composable ColumnScope.() -> Unit) = Card(
    elevation = CardDefaults.cardElevation(6.dp),
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color(0xFFE1BEE7))
) {
    Column(Modifier.padding(12.dp)) {
        Text(
            t,
            fontWeight = FontWeight.Bold
        ); if (s.isNotEmpty()) Text(s, color = Color.Gray); Spacer(Modifier.height(8.dp)); c()
    }
}

@Composable
private fun UsageCard(t: String, c: @Composable ColumnScope.() -> Unit) =
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(14.dp)) {
            Text(
                t,
                fontWeight = FontWeight.SemiBold
            ); Spacer(Modifier.height(8.dp)); c()
        }
    }

@Composable
private fun DemoTile(size: Dp, label: String) = Box(
    Modifier
        .size(size)
        .background(Color(0xFFCE93D8), RoundedCornerShape(6.dp)),
    Alignment.Center
) { Text(label) }

@Composable
private fun ComparisonRow(label: String, value: Dp, baseline: Dp) {
    val diff = ((value.value - baseline.value) / baseline.value * 100).toInt(); Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), Arrangement.SpaceBetween
    ) {
        Text(
            label,
            Modifier.weight(1f)
        ); Text("${value.value.toInt()}dp"); Text(
        if (diff != 0) "${if (diff > 0) "+" else ""}$diff%" else "0%",
        color = if (diff > 5) Color.Red else if (diff < -5) Color.Green else Color.Gray
    )
    }
}

