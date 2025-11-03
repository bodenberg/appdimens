/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.compose.en

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

/**
 * DIAGONAL Strategy: Scale based on diagonal (true screen size)
 * Formula: f(x) = x × √(W² + H²) / √(W₀² + H₀²)
 * Best for: Physical screen size matching, orientation-independent scaling
 */
@OptIn(ExperimentalMaterial3Api::class)
class DiagonalExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { DiagonalDemoScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagonalDemoScreen() {
    val config = LocalConfiguration.current
    val diagonalDp =
        kotlin.math.sqrt((config.screenWidthDp * config.screenWidthDp + config.screenHeightDp * config.screenHeightDp).toDouble())
            .toFloat()

    Scaffold(topBar = { TopAppBar(title = { Text("DIAGONAL Strategy") }) }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(AppDimens.from(12).diagonal().dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.from(12).diagonal().dp)
        ) {
            item {
                InfoCard("📖 DIAGONAL Strategy", "True screen size scaling") {
                    Text("Formula: f(x) = x × √(W² + H²) / √(W₀² + H₀²)")
                    Text("Considers both width AND height")
                    Text("True \"screen size\" - orientation independent")
                    Text("Current diagonal: ${"%.1f".format(diagonalDp)}dp")
                    Text("Best for: Physical screen size matching")
                }
            }
            item {
                UsageCard("Basic Usage") {
                    val size = AppDimens.from(48).diagonal().dp
                    DemoTile(size, "48dp → ${size.value.toInt()}dp")
                    Text(
                        "Code: AppDimens.from(48).diagonal().dp",
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        color = Color.Gray
                    )
                }
            }
            item {
                UsageCard("⚖️ Comparison") {
                    ComparisonRow(
                        "DIAGONAL (this)",
                        AppDimens.from(48).diagonal().dp,
                        AppDimens.from(48).diagonal().dp
                    )
                    ComparisonRow(
                        "PERCENTAGE",
                        AppDimens.from(48).percentage().dp,
                        AppDimens.from(48).diagonal().dp
                    )
                    ComparisonRow(
                        "PERIMETER",
                        AppDimens.from(48).perimeter().dp,
                        AppDimens.from(48).diagonal().dp
                    )
                }
            }
            item {
                InfoCard("💡 Recommendations", "") {
                    RecommendationItem("✅ Physical screen size matching")
                    RecommendationItem("✅ Orientation-independent scaling")
                    RecommendationItem("✅ When W×H balance matters")
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(
                title,
                fontWeight = FontWeight.Bold
            ); if (subtitle.isNotEmpty()) Text(
            subtitle,
            color = Color.Gray
        ); Spacer(Modifier.height(8.dp)); content()
        }
    }

@Composable
private fun UsageCard(title: String, content: @Composable ColumnScope.() -> Unit) =
    Card(
        Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
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
            .background(Color(0xFFFFE082), RoundedCornerShape(6.dp)),
        Alignment.Center
    ) { Text(label, fontWeight = FontWeight.Medium) }

@Composable
private fun ComparisonRow(label: String, value: Dp, baseline: Dp) {
    val diff = ((value.value - baseline.value) / baseline.value * 100).toInt()
    Row(Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp), Arrangement.SpaceBetween) {
        Text(
            label,
            Modifier.weight(1f)
        ); Text("${value.value.toInt()}dp"); Text(
        if (diff != 0) "${if (diff > 0) "+" else ""}$diff%" else "0%",
        color = if (diff > 5) Color.Red else if (diff < -5) Color.Green else Color.Gray
    )
    }
}

@Composable
private fun RecommendationItem(text: String) = Text(text, Modifier.padding(vertical = 2.dp))

