/**
 * AppDimens Compose Demo Activity
 * Author & Developer: Jean Bodenberg
 * Purpose: Demonstrate complete usage of the AppDimens classes (Fixed, Dynamic,
 * PhysicalUnits, AdjustmentFactors utilities and CalculateAvailableItemCount).
 *
 * Place this file under your app module (e.g. `com.appdimens.demo`).
 * Build & runtime requirements:
 * - Jetpack Compose setup (activity-compose)
 * - Material3 is used for visuals (you can adapt to Material if desired)
 * - Add your `app-dimens` module / library to project so imports resolve
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
import com.appdimens.dynamic.compose.AppDimens.dydp
import com.appdimens.dynamic.compose.AppDimens.dysp
import com.appdimens.dynamic.compose.AppDimensPhysicalUnits
import com.appdimens.library.ScreenType

@OptIn(ExperimentalMaterial3Api::class)
class DynamicExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the Composable content for the activity
        setContent {
            MaterialTheme {
                DynamicDimensDemoScreen()
            }
        }
    }
}

@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicDimensDemoScreen() {
    // LocalConfiguration, LocalDensity, and LocalContext are available but not
    // strictly needed for AppDimens as it handles density/scaling internally.
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val ctx = LocalContext.current

    // State to toggle the screen type used for dynamic scaling demonstration
    var screenType by remember { mutableStateOf(ScreenType.LOWEST) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AppDimens — Dynamic demo", fontWeight = FontWeight.SemiBold) }
            )
        }
    ) { contentPadding ->
        // Use LazyColumn for a scrollable demonstration of the different concepts
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(12.dydp),
            verticalArrangement = Arrangement.spacedBy(12.dydp)
        ) {

            // --- Dynamic Scaling Preview Section ---
            item {
                InfoCard("Dynamic scaling preview", "Observe how dimensions adapt per screen type") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        val baseDp = 24.dp

                        // Apply dynamic scaling to Dp, Sp, and raw Px values based on the current screenType state.
                        val dynDp = with(AppDimens) { baseDp.dynamicDp(screenType) }
                        val dynSp = with(AppDimens) { baseDp.dynamicSp(screenType) }
                        // dynamicPx returns an Int (pixel value)
                        val dynPx = with(AppDimens) { baseDp.dynamicPx(screenType) }

                        Text("Base: ${baseDp.value}dp")
                        Text("dynamicDp -> ${dynDp.value.toInt()}dp")
                        Text("dynamicSp -> ${dynSp.value}sp")
                        Text("dynamicPx -> ${dynPx.toInt()}px")

                        Column (verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Show the dynamically scaled Dp size on a Composable
                            DemoTile(size = dynDp, label = "Dynamic dp")

                            // Button to toggle the scaling factor between ScreenType.LOWEST and ScreenType.HIGHEST
                            // This visually demonstrates the dynamic adjustment in real-time.
                            Button(onClick = {
                                screenType = if (screenType == ScreenType.LOWEST) ScreenType.HIGHEST else ScreenType.LOWEST
                            }) {
                                Text("Toggle ScreenType ($screenType)")
                            }
                        }
                    }
                }
            }

            // --- Responsive Ratio Demo Section ---
            item {
                UsageCard("Responsive ratio demo (width %, height %)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        // dynamicPerDp scales a float (representing a ratio/percentage) into a Dp value.
                        // Here, it calculates 10% of the screen's width (using LOWEST factor)
                        val w10 = with(AppDimens) { 0.10f.dynamicPerDp(ScreenType.LOWEST) }
                        // And 10% of the screen's height (using HIGHEST factor)
                        val h10 = with(AppDimens) { 0.10f.dynamicPerDp(ScreenType.HIGHEST) }

                        Text("10% of screen width -> ${w10.value.toInt()}dp")
                        Text("10% of screen height -> ${h10.value.toInt()}dp")

                        // Visualize the calculated dynamic dimensions
                        Box(
                            modifier = Modifier
                                .width(w10)
                                .height(h10)
                                .background(Color(0xFF80CBC4), RoundedCornerShape(8.dydp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("10% x 10%", color = Color.Black)
                        }
                    }
                }
            }

            // --- Live Adjustment Factor Demo Section ---
            item {
                UsageCard("Live preview — adjust factor dynamically") {
                    // State for a custom adjustment factor (0.5x to 2.0x)
                    var factor by remember { mutableStateOf(1.0f) }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dydp)) {
                        Text("Current adjustment factor: ${"%.2f".format(factor)}")

                        // dynamicPercentageDp applies a multiplier factor to the globally defined
                        // default dimension (e.g., in AppDimensConfiguration).
                        val adjustedDp = with(AppDimens) { dynamicPercentageDp(factor) }
                        Box(
                            modifier = Modifier
                                .size(adjustedDp)
                                .background(Color(0xFFFFCC80), RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${adjustedDp.value.toInt()}dp", fontWeight = FontWeight.Bold)
                        }

                        // Slider to change the custom factor in real-time
                        Slider(
                            value = factor,
                            onValueChange = { factor = it },
                            valueRange = 0.5f..2.0f,
                            steps = 5
                        )
                    }
                }
            }

            // --- Physical Units Demo Section ---
            item {
                UsageCard("Physical units in dynamic context") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // AppDimensPhysicalUnits converts real-world units (cm, inch) to raw pixels (Int).
                        val cmPx = with(AppDimensPhysicalUnits) { 2f.cm }
                        val inchPx = with(AppDimensPhysicalUnits) { 1f.inch }
                        Text("2 cm ≈ ${cmPx.toInt()} px")
                        Text("1 inch ≈ ${inchPx.toInt()} px")

                        // The resulting raw pixel value (cmPx) can then be dynamically scaled and converted to Dp
                        // using the standard AppDimens dynamic extensions.
                        val dynRadius = with(AppDimens) { cmPx.dynamicDp(screenType) }
                        Text("Dynamic adjusted (2cm) ≈ ${dynRadius.value.toInt()}dp (after scaling)")
                    }
                }
            }

            // --- Summary Notes ---
            item {
                InfoCard("Notes", "Dynamic vs Fixed summary") {
                    Text("Dynamic dimensions automatically scale based on screen size, density, and ScreenType."
                            +"\n\nUse them for adaptive layouts that remain consistent across devices.\n")
                }
            }
        }
    }
}

// -----------------------------------------------------------------------------
// --- Helper Composables (General UI) ---
// -----------------------------------------------------------------------------

@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable () -> Unit) {
    // Standard Card for informational content
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dydp),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dydp)
    ) {
        Column(modifier = Modifier.padding(12.dydp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dydp))
            Text(subtitle, fontSize = 12.dysp, color = Color.Gray)
            Spacer(Modifier.height(8.dydp))
            content()
        }
    }
}

@Composable
private fun UsageCard(title: String, content: @Composable () -> Unit) {
    // Standard Card for demonstration sections
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dydp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dydp)
    ) {
        Column(modifier = Modifier.padding(14.dydp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dydp))
            content()
        }
    }
}

@Composable
private fun DemoTile(size: Dp, label: String) {
    // A simple Box Composable to visually represent a scaled dimension (Dp)
    Box(
        modifier = Modifier
            .size(size) // The key modifier using the dynamically scaled Dp
            .background(Color(0xFFEEEEEE), RoundedCornerShape(6.dydp))
            .border(1.dydp, Color.LightGray, RoundedCornerShape(6.dydp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 11.dysp)
    }
}

// --- Preview Composable ---
@Preview(
    showBackground = true,
    // Use a specific device and UI mode for consistent preview rendering
    device = "id:pixel_9_pro", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AppDimensPreview() {
    MaterialTheme {
        DynamicDimensDemoScreen()
    }
}