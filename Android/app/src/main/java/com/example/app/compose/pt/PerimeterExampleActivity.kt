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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens

/** Estratégia PERIMETER: f(x) = x × (W + H) / (W₀ + H₀) */
@OptIn(ExperimentalMaterial3Api::class)
class PerimeterExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContent { MaterialTheme { PerimeterDemoScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerimeterDemoScreen() {
    val config = LocalConfiguration.current
    val perimeter = config.screenWidthDp + config.screenHeightDp
    Scaffold(topBar = { TopAppBar(title = { Text("Estratégia PERIMETER") }) }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InfoCard(
                    "📖 PERIMETER",
                    "Escalonamento W+H"
                ) {
                    Text("Fórmula: f(x) = x × (W + H) / (W₀ + H₀)"); Text("Perímetro: ${perimeter}dp"); Text(
                    "Melhor para: Escalonamento balanceado W+H"
                )
                }
            }
            item {
                UsageCard("Uso") {
                    val s = AppDimens.from(48).perimeter().dp; DemoTile(
                    s,
                    "${s.value.toInt()}dp"
                ); Text(
                    "Código: .perimeter().dp",
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    color = Color.Gray
                )
                }
            }
            item {
                UsageCard("Comparar") {
                    ComparisonRow(
                        "PERIMETER",
                        AppDimens.from(48).perimeter().dp,
                        AppDimens.from(48).perimeter().dp
                    ); ComparisonRow(
                    "DIAGONAL",
                    AppDimens.from(48).diagonal().dp,
                    AppDimens.from(48).perimeter().dp
                )
                }
            }
        }
    }
}

@Composable
private fun InfoCard(t: String, s: String, c: @Composable ColumnScope.() -> Unit) =
    Card(elevation = CardDefaults.cardElevation(6.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(t, fontWeight = FontWeight.Bold); Text(
            s,
            color = Color.Gray
        ); Spacer(Modifier.height(8.dp)); c()
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
        .background(Color(0xFFFFCDD2), RoundedCornerShape(6.dp)),
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

