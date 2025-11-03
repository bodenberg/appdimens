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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens

/** FILL Strategy (Cover): f(x) = x × max(W/W₀, H/H₀) - Game cover, fills screen */
@OptIn(ExperimentalMaterial3Api::class)
class FillExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContent { MaterialTheme { FillDemoScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillDemoScreen() {
    Scaffold(topBar = { TopAppBar(title = { Text("FILL Strategy (Cover)") }) }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InfoCard(
                    "📖 FILL (Cover)",
                    "Game cover - fills screen"
                ) {
                    Text("Formula: f(x) = x × max(W/W₀, H/H₀)"); Text("Uses larger ratio"); Text("Fills screen (may crop content)"); Text(
                    "Best for: Games, backgrounds, full-screen"
                )
                }
            }
            item {
                UsageCard("Usage") {
                    val s = AppDimens.from(48).fill().dp; DemoTile(
                    s,
                    "${s.value.toInt()}dp"
                ); Text(
                    "Code: .fill().dp",
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    color = Color.Gray
                )
                }
            }
            item {
                UsageCard("Compare") {
                    ComparisonRow(
                        "FILL (this)",
                        AppDimens.from(48).fill().dp,
                        AppDimens.from(48).fill().dp
                    ); ComparisonRow(
                    "FIT",
                    AppDimens.from(48).fit().dp,
                    AppDimens.from(48).fill().dp
                ); ComparisonRow(
                    "PERCENTAGE",
                    AppDimens.from(48).percentage().dp,
                    AppDimens.from(48).fill().dp
                )
                }
            }
            item {
                InfoCard(
                    "💡 Use Cases",
                    ""
                ) {
                    Text("✅ Game cover mode"); Text("✅ Backgrounds"); Text("✅ Full-screen content"); Text(
                    "⚠️ May crop content"
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
    colors = CardDefaults.cardColors(containerColor = Color(0xFFD1C4E9))
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
        .background(Color(0xFFB39DDB), RoundedCornerShape(6.dp)),
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

