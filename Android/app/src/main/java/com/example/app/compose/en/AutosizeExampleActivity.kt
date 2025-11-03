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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.dynamic.compose.AppDimens
import com.appdimens.dynamic.core.strategy.AutoSizeMode

/** AUTOSIZE Strategy: Auto-adjust based on container size - Similar to TextView autoSizeText */
@OptIn(ExperimentalMaterial3Api::class)
class AutosizeExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState); setContent { MaterialTheme { AutosizeDemoScreen() } }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutosizeDemoScreen() {
    var minValue by remember { mutableStateOf(32f) }
    var maxValue by remember { mutableStateOf(72f) }

    Scaffold(topBar = { TopAppBar(title = { Text("AUTOSIZE Strategy") }) }) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                InfoCard(
                    "📖 AUTOSIZE",
                    "Auto-adjust to container size"
                ) {
                    Text("Similar to TextView autoSizeText"); Text("Adjusts to fit content in container"); Text(
                    "Supports UNIFORM and PRESET modes"
                ); Text("Best for: Dynamic text, auto-sizing typography, variable containers")
                }
            }
            item {
                UsageCard("Basic Usage - UNIFORM Mode") {
                    val size = AppDimens.from(48).autoSize(minValue, maxValue).dp; DemoTile(
                    size,
                    "${size.value.toInt()}dp"
                ); Text(
                    "Code: .autoSize(${minValue.toInt()}f, ${maxValue.toInt()}f).dp",
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    color = Color.Gray,
                    fontSize = AppDimens.from(10).default().sp
                )
                }
            }
            item {
                UsageCard("Range Control") {
                    Text("Min: ${minValue.toInt()}dp"); Slider(
                    value = minValue,
                    onValueChange = { minValue = it },
                    valueRange = 16f..48f
                ); Text("Max: ${maxValue.toInt()}dp"); Slider(
                    value = maxValue,
                    onValueChange = { maxValue = it },
                    valueRange = 48f..96f
                )
                }
            }
            item {
                UsageCard("PRESET Mode") {
                    val presets = floatArrayOf(24f, 32f, 40f, 48f, 56f, 64f);
                    val size = AppDimens.from(48).autoSizePresets(presets).dp; DemoTile(
                    size,
                    "${size.value.toInt()}dp"
                ); Text(
                    "Presets: ${presets.joinToString(", ")}dp",
                    fontSize = AppDimens.from(11).default().sp,
                    color = Color.Gray
                )
                }
            }
            item {
                InfoCard(
                    "💡 Use Cases",
                    ""
                ) {
                    Text("✅ Auto-sizing text (TextView compatibility)"); Text("✅ Dynamic font sizes"); Text(
                    "✅ Content that must fit containers"
                ); Text("✅ Responsive typography")
                }
            }
        }
    }
}

@Composable
private fun InfoCard(t: String, s: String, c: @Composable ColumnScope.() -> Unit) = Card(
    elevation = CardDefaults.cardElevation(6.dp),
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
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
        .background(Color(0xFFE1BEE7), RoundedCornerShape(6.dp)),
    Alignment.Center
) { Text(label) }

