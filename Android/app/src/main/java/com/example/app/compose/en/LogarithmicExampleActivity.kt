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
 *
 * LOGARITHMIC Strategy:
 * - Formula: f(x) = x × (1 + sensitivity × ln(W / W₀))
 * - Pure logarithmic growth on all screens
 * - Maximum control on large screens (prevents oversizing)
 * - Based on Weber-Fechner Law (perceptual psychology)
 * - May reduce sizes noticeably on phones
 * - Best for: TVs, very large tablets, maximum control
 *
 * [PT] Activity demonstrando a estratégia de escalonamento LOGARITHMIC (Perceptual Weber-Fechner).
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
                        "LOGARITHMIC Strategy",
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
                    title = "Device Metrics",
                    subtitle = "Current screen characteristics"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).logarithmic().dp
                        )
                    ) {
                        Text("Smallest Width: ${configuration.smallestScreenWidthDp}dp")
                        Text("Density: ${"%.2f".format(density.density)} px/dp")
                        val scaleFactor = configuration.smallestScreenWidthDp / 360f
                        Text("Linear scale would be: ${"%.2f".format(scaleFactor)}x")
                        val logScale = 1f + (0.4f * kotlin.math.ln(scaleFactor.coerceAtLeast(1f)))
                        Text(
                            "Logarithmic scale: ${"%.2f".format(logScale)}x",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                InfoCard(
                    title = "📖 LOGARITHMIC Strategy",
                    subtitle = "Weber-Fechner perceptual scaling"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).logarithmic().dp
                        )
                    ) {
                        Text(
                            "LOGARITHMIC provides maximum control on large screens, preventing UI bloat.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(AppDimens.from(4).logarithmic().dp))

                        Text("Formula:", fontWeight = FontWeight.Bold)
                        Text(
                            "f(x) = x × (1 + k × ln(W / W₀))",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(12).logarithmic().sp
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(4).logarithmic().dp))
                        Text("Characteristics:", fontWeight = FontWeight.Bold)
                        Text("• Pure logarithmic growth on all screens")
                        Text("• Maximum control on tablets/TVs")
                        Text("• Based on Weber-Fechner Law")
                        Text("• May reduce sizes on phones")

                        Spacer(modifier = Modifier.height(AppDimens.from(4).logarithmic().dp))
                        Text("Best for:", fontWeight = FontWeight.Bold)
                        Text("• TV applications")
                        Text("• Very large tablets (10\"+)")
                        Text("• When maximum control is needed")
                    }
                }
            }

            item {
                UsageCard(title = "Basic Usage") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).logarithmic().dp
                        )
                    ) {
                        val size = AppDimens.from(48).logarithmic().dp
                        DemoTile(size = size, label = "48dp → ${size.value.toInt()}dp")
                        Text(
                            "Code: AppDimens.from(48).logarithmic().dp",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).logarithmic().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                UsageCard(title = "Sensitivity Control") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).logarithmic().dp
                        )
                    ) {
                        Text("Adjust sensitivity: ${"%.2f".format(sensitivity)}")

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
                            "Lower sensitivity = more conservative scaling",
                            fontSize = AppDimens.from(11).logarithmic().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                UsageCard(title = "⚖️ Comparison with Other Strategies") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).logarithmic().dp
                        )
                    ) {
                        Text("Base value: 48dp", fontWeight = FontWeight.Bold)

                        val logarithmicSize = AppDimens.from(48).logarithmic().dp
                        val balancedSize = AppDimens.from(48).balanced().dp
                        val percentageSize = AppDimens.from(48).percentage().dp

                        ComparisonRow("LOGARITHMIC (this)", logarithmicSize, logarithmicSize)
                        ComparisonRow("BALANCED", balancedSize, logarithmicSize)
                        ComparisonRow("PERCENTAGE", percentageSize, logarithmicSize)

                        Text(
                            "LOGARITHMIC provides strongest control, preventing oversizing on tablets/TVs",
                            fontSize = AppDimens.from(11).logarithmic().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                InfoCard(
                    title = "💡 Recommendations",
                    subtitle = "When to use LOGARITHMIC"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).logarithmic().dp
                        )
                    ) {
                        RecommendationItem("✅ TV applications (Android TV)")
                        RecommendationItem("✅ Large tablets (10\"+ screens)")
                        RecommendationItem("✅ When UI bloat is a concern")
                        RecommendationItem("✅ Content-heavy interfaces")

                        Spacer(modifier = Modifier.height(AppDimens.from(8).logarithmic().dp))

                        Text("Consider alternatives:", fontWeight = FontWeight.Bold)
                        RecommendationItem("• Multi-device apps → BALANCED")
                        RecommendationItem("• Phone-only apps → DEFAULT")
                        RecommendationItem("⚠️ May make UI too small on phones")
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

