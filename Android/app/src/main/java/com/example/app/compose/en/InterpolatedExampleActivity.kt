/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.compose.en

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
 * INTERPOLATED Strategy: Moderated linear (50% of linear growth)
 * Formula: f(x) = x + ((x × W/W₀) - x) × 0.5
 * Best for: Moderate scaling needs, balanced growth
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
    Scaffold(topBar = { TopAppBar(title = { Text("INTERPOLATED Strategy") }) }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(AppDimens.from(12).interpolated().dp),
            verticalArrangement = Arrangement.spacedBy(AppDimens.from(12).interpolated().dp)
        ) {
            item {
                InfoCard("📖 INTERPOLATED Strategy", "50% moderated linear scaling") {
                    Text("Formula: f(x) = x + ((x × W/W₀) - x) × 0.5")
                    Text("50% of linear growth - softer than linear, stronger than logarithmic")
                    Text("Best for: Moderate scaling, balanced growth between extremes")
                }
            }
            item {
                UsageCard("Basic Usage") {
                    val size = AppDimens.from(48).interpolated().dp
                    DemoTile(size, "48dp → ${size.value.toInt()}dp")
                    Text(
                        "Code: AppDimens.from(48).interpolated().dp",
                        fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                        fontSize = AppDimens.from(10).interpolated().sp,
                        color = Color.Gray
                    )
                }
            }
            item {
                UsageCard("⚖️ Comparison") {
                    val interpolatedSize = AppDimens.from(48).interpolated().dp
                    val percentageSize = AppDimens.from(48).percentage().dp
                    val defaultSize = AppDimens.from(48).default().dp
                    ComparisonRow("INTERPOLATED (this)", interpolatedSize, interpolatedSize)
                    ComparisonRow("PERCENTAGE", percentageSize, interpolatedSize)
                    ComparisonRow("DEFAULT", defaultSize, interpolatedSize)
                }
            }
            item {
                InfoCard("💡 Use Cases", "When to use INTERPOLATED") {
                    RecommendationItem("✅ Moderate scaling needs")
                    RecommendationItem("✅ Between conservative and aggressive")
                    RecommendationItem("✅ General purpose with gentle growth")
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

