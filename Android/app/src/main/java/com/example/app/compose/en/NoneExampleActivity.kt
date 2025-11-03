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

/** NONE Strategy: f(x) = x - No scaling, constant size on all screens */
@OptIn(ExperimentalMaterial3Api::class)
class NoneExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContent { MaterialTheme { NoneDemoScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoneDemoScreen() {
    val config = LocalConfiguration.current

    Scaffold(topBar = { TopAppBar(title = { Text("NONE Strategy") }) }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InfoCard(
                    "📖 NONE Strategy",
                    "No scaling - constant size"
                ) {
                    Text("Formula: f(x) = x"); Text("No scaling applied - absolute dimensions"); Text(
                    "Same size on all screens"
                ); Text(
                    "Screen: ${config.screenWidthDp} × ${config.screenHeightDp}dp"
                ); Text("Best for: Fixed size requirements, absolute dimensions")
                }
            }
            item {
                UsageCard("Basic Usage") {
                    val size = AppDimens.from(48).none().dp; DemoTile(
                    size,
                    "Always 48dp"
                ); Text(
                    "Code: AppDimens.from(48).none().dp",
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    color = Color.Gray
                )
                }
            }
            item {
                UsageCard("⚖️ Comparison") {
                    Text(
                        "Base value: 48dp",
                        fontWeight = FontWeight.Bold
                    ); ComparisonRow(
                    "NONE (this)",
                    AppDimens.from(48).none().dp,
                    AppDimens.from(48).none().dp
                ); ComparisonRow(
                    "DEFAULT",
                    AppDimens.from(48).default().dp,
                    AppDimens.from(48).none().dp
                ); ComparisonRow(
                    "PERCENTAGE",
                    AppDimens.from(48).percentage().dp,
                    AppDimens.from(48).none().dp
                ); Text(
                    "NONE always returns the base value unchanged",
                    fontSize = AppDimens.from(11).default().sp,
                    color = Color.Gray
                )
                }
            }
            item {
                InfoCard(
                    "💡 Use Cases",
                    "When to use NONE"
                ) {
                    Text("✅ Fixed size requirements (e.g., icons)"); Text("✅ Absolute dimensions needed"); Text(
                    "✅ Standard Android dp without scaling"
                ); Text("✅ When consistent absolute size is critical"); Text("⚠️ May not scale well across devices"); Text(
                    "⚠️ Use sparingly - other strategies usually better"
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
    colors = CardDefaults.cardColors(containerColor = Color(0xFFECEFF1))
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
        .background(Color(0xFFCFD8DC), RoundedCornerShape(6.dp)),
    Alignment.Center
) { Text(label) }

@Composable
private fun ComparisonRow(label: String, value: Dp, baseline: Dp) {
    val diff = ((value.value - baseline.value) / baseline.value * 100).toInt(); Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), Arrangement.SpaceBetween
    ) {
        Text(label, Modifier.weight(1f)); Text(
        "${value.value.toInt()}dp",
        fontWeight = FontWeight.Medium
    ); Text(
        if (diff != 0) "${if (diff > 0) "+" else ""}$diff%" else "0%",
        color = if (diff > 5) Color.Red else if (diff < -5) Color.Green else Color.Gray
    )
    }
}

