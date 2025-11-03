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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens
import com.appdimens.library.ScreenType

/**
 * [EN] Activity demonstrating the DEFAULT scaling strategy (formerly "Fixed").
 *
 * DEFAULT Strategy:
 * - Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
 * - ~97% linear growth with aspect ratio compensation
 * - Familiar behavior from AppDimens v1.x
 * - Best for: Phone-only apps, icons, backward compatibility
 *
 * [PT] Activity demonstrando a estratégia de escalonamento DEFAULT (anteriormente "Fixed").
 *
 * Estratégia DEFAULT:
 * - Fórmula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × ajusteAR
 * - Crescimento ~97% linear com compensação de proporção de aspecto
 * - Comportamento familiar do AppDimens v1.x
 * - Melhor para: Apps apenas para telefones, ícones, compatibilidade retroativa
 */
@OptIn(ExperimentalMaterial3Api::class)
class DefaultExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                DefaultDemoScreen()
            }
        }
    }
}

@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultDemoScreen() {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val ctx = LocalContext.current

    var currentScreenType by remember { mutableStateOf(ScreenType.LOWEST) }
    var enableAR by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "DEFAULT Strategy Demo",
                        fontSize = AppDimens.from(18).default().sp
                    )
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(AppDimens.from(12).default().dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.from(12).default().dp)
        ) {
            // Device metrics
            item {
                InfoCard(
                    title = "Device Metrics",
                    subtitle = "Screen dimensions and density"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).default().dp
                        )
                    ) {
                        Text("Screen Width: ${configuration.screenWidthDp}dp")
                        Text("Screen Height: ${configuration.screenHeightDp}dp")
                        Text("Smallest Width: ${configuration.smallestScreenWidthDp}dp")
                        Text("Density: ${"%.2f".format(density.density)} (px per dp)")
                        Text("Aspect Ratio: ${"%.2f".format(configuration.screenHeightDp.toFloat() / configuration.screenWidthDp)}")
                    }
                }
            }

            // Strategy explanation
            item {
                InfoCard(
                    title = "📖 DEFAULT Strategy",
                    subtitle = "Legacy 'Fixed' behavior with ~97% linear scaling"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).default().dp
                        )
                    ) {
                        Text(
                            "DEFAULT is the legacy scaling strategy from AppDimens v1.x.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(AppDimens.from(4).default().dp))

                        Text(
                            "Formula:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).default().sp
                        )
                        Text(
                            "f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdj",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(12).default().sp
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(4).default().dp))
                        Text(
                            "Characteristics:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).default().sp
                        )
                        Text("• ~97% linear growth", fontSize = AppDimens.from(13).default().sp)
                        Text(
                            "• Aspect ratio compensation",
                            fontSize = AppDimens.from(13).default().sp
                        )
                        Text("• Familiar v1.x behavior", fontSize = AppDimens.from(13).default().sp)

                        Spacer(modifier = Modifier.height(AppDimens.from(4).default().dp))
                        Text(
                            "Best for:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).default().sp
                        )
                        Text("• Phone-only apps", fontSize = AppDimens.from(13).default().sp)
                        Text(
                            "• Icons and small elements",
                            fontSize = AppDimens.from(13).default().sp
                        )
                        Text("• Backward compatibility", fontSize = AppDimens.from(13).default().sp)
                    }
                }
            }

            // Basic usage examples
            item {
                UsageCard(title = "Basic Usage") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).default().dp
                        )
                    ) {
                        Text(
                            "Method 1: Using .default() (recommended)",
                            fontWeight = FontWeight.Medium,
                            fontSize = AppDimens.from(14).default().sp
                        )

                        val size1 = AppDimens.from(48).default().dp
                        DemoTile(size = size1, label = "48dp → ${size1.value.toInt()}dp")

                        Text(
                            "Code: AppDimens.from(48).default().dp",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).default().sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(AppDimens.from(8).default().dp))

                        Text(
                            "Method 2: Using .fixed() (legacy compatibility)",
                            fontWeight = FontWeight.Medium,
                            fontSize = AppDimens.from(14).default().sp
                        )

                        val size2 = AppDimens.fixed(48).dp
                        DemoTile(size = size2, label = "48dp → ${size2.value.toInt()}dp")

                        Text(
                            "Code: AppDimens.fixed(48).dp",
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            fontSize = AppDimens.from(11).default().sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Aspect Ratio control
            item {
                UsageCard(title = "Aspect Ratio Adjustment") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).default().dp
                        )
                    ) {
                        Text(
                            "DEFAULT strategy includes aspect ratio compensation to prevent distortion on non-standard screens.",
                            fontSize = AppDimens.from(13).default().sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Enable AR Adjustment:",
                                fontSize = AppDimens.from(14).default().sp
                            )
                            Switch(
                                checked = enableAR,
                                onCheckedChange = { enableAR = it }
                            )
                        }

                        val sizeWithAR = AppDimens.from(48).default(enableAR = enableAR).dp
                        val sizeNoAR = AppDimens.from(48).default(enableAR = false).dp

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                AppDimens.from(8).default().dp
                            )
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DemoTile(
                                    size = sizeWithAR,
                                    label = if (enableAR) "With AR" else "Current"
                                )
                                Text(
                                    "${sizeWithAR.value.toInt()}dp",
                                    fontSize = AppDimens.from(12).default().sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DemoTile(size = sizeNoAR, label = "Without AR")
                                Text(
                                    "${sizeNoAR.value.toInt()}dp",
                                    fontSize = AppDimens.from(12).default().sp
                                )
                            }
                        }

                        Text(
                            "Difference: ${(sizeWithAR.value - sizeNoAR.value).toInt()}dp",
                            fontSize = AppDimens.from(12).default().sp,
                            color = if (enableAR && sizeWithAR.value != sizeNoAR.value) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }
                }
            }

            // Screen type comparison
            item {
                UsageCard(title = "Screen Type Comparison") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(12).default().dp
                        )
                    ) {
                        Button(
                            onClick = {
                                currentScreenType = if (currentScreenType == ScreenType.LOWEST)
                                    ScreenType.HIGHEST else ScreenType.LOWEST
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Toggle ScreenType: $currentScreenType")
                        }

                        val defaultLowest = AppDimens.from(48)
                            .default()
                            .type(ScreenType.LOWEST)
                            .dp

                        val defaultHighest = AppDimens.from(48)
                            .default()
                            .type(ScreenType.HIGHEST)
                            .dp

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                AppDimens.from(12).default().dp
                            )
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DemoTile(size = defaultLowest, label = "LOWEST")
                                Text(
                                    "${defaultLowest.value.toInt()}dp",
                                    fontSize = AppDimens.from(12).default().sp
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                DemoTile(size = defaultHighest, label = "HIGHEST")
                                Text(
                                    "${defaultHighest.value.toInt()}dp",
                                    fontSize = AppDimens.from(12).default().sp
                                )
                            }
                        }
                    }
                }
            }

            // Visual comparison with other strategies
            item {
                UsageCard(title = "⚖️ Comparison with Other Strategies") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(8).default().dp
                        )
                    ) {
                        Text(
                            "Base value: 48dp",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(14).default().sp
                        )

                        val baseValue = 48
                        val defaultSize = AppDimens.from(baseValue).default().dp
                        val percentageSize = AppDimens.from(baseValue).percentage().dp
                        val balancedSize = AppDimens.from(baseValue).balanced().dp

                        ComparisonRow("DEFAULT (this)", defaultSize, defaultSize)
                        ComparisonRow("PERCENTAGE", percentageSize, defaultSize)
                        ComparisonRow("BALANCED", balancedSize, defaultSize)

                        Text(
                            "Note: DEFAULT provides ~97% linear scaling, making it conservative compared to PERCENTAGE.",
                            fontSize = AppDimens.from(11).default().sp,
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // Recommendations
            item {
                InfoCard(
                    title = "💡 Recommendations",
                    subtitle = "When to use DEFAULT strategy"
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(
                            AppDimens.from(6).default().dp
                        )
                    ) {
                        RecommendationItem("✅ Phone-only applications")
                        RecommendationItem("✅ Backward compatibility with v1.x")
                        RecommendationItem("✅ Icons and small UI elements")
                        RecommendationItem("✅ When familiar behavior is needed")

                        Spacer(modifier = Modifier.height(AppDimens.from(8).default().dp))

                        Text(
                            "Consider alternatives:",
                            fontWeight = FontWeight.Bold,
                            fontSize = AppDimens.from(13).default().sp
                        )
                        RecommendationItem("🔄 BALANCED for multi-device apps")
                        RecommendationItem("🔄 PERCENTAGE for fluid layouts")
                        RecommendationItem("🔄 LOGARITHMIC for tablets/TVs")
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.from(6).default().dp),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.from(10).default().dp)
    ) {
        Column(modifier = Modifier.padding(AppDimens.from(12).default().dp)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = AppDimens.from(16).default().sp)
            Spacer(Modifier.height(AppDimens.from(6).default().dp))
            Text(subtitle, fontSize = AppDimens.from(12).default().sp, color = Color.Gray)
            Spacer(Modifier.height(AppDimens.from(8).default().dp))
            content()
        }
    }
}

@Composable
private fun UsageCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimens.from(12).default().dp),
        elevation = CardDefaults.cardElevation(defaultElevation = AppDimens.from(8).default().dp)
    ) {
        Column(modifier = Modifier.padding(AppDimens.from(14).default().dp)) {
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                fontSize = AppDimens.from(15).default().sp
            )
            Spacer(Modifier.height(AppDimens.from(8).default().dp))
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
                Color(0xFFE3F2FD),
                shape = RoundedCornerShape(AppDimens.from(6).default().dp)
            )
            .border(
                AppDimens.from(2).default().dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(AppDimens.from(6).default().dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = AppDimens.from(11).default().sp, fontWeight = FontWeight.Medium)
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
            .padding(vertical = AppDimens.from(4).default().dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontSize = AppDimens.from(13).default().sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            "${"%.1f".format(value.value)}dp",
            fontSize = AppDimens.from(13).default().sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.width(AppDimens.from(60).default().dp)
        )
        Text(
            diffText,
            fontSize = AppDimens.from(12).default().sp,
            color = diffColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(AppDimens.from(50).default().dp)
        )
    }
}

@Composable
private fun RecommendationItem(text: String) {
    Text(
        text,
        fontSize = AppDimens.from(13).default().sp,
        modifier = Modifier.padding(vertical = AppDimens.from(2).default().dp)
    )
}

@Preview(
    showBackground = true,
    device = "id:pixel_5",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun DefaultDemoPreview() {
    MaterialTheme {
        DefaultDemoScreen()
    }
}

