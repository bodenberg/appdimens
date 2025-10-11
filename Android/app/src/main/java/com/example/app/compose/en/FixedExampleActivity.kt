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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.appdimens.dynamic.compose.AppDimens.fxdp
import com.appdimens.dynamic.compose.AppDimens.fxsp
import com.appdimens.dynamic.compose.AppDimensPhysicalUnits
import com.appdimens.library.DpQualifier
import com.appdimens.library.ScreenType
import com.appdimens.library.UnitType
import com.appdimens.library.UiModeType
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class FixedExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply MaterialTheme for standard visuals
            MaterialTheme {
                AppDimensDemoScreen()
            }
        }
    }
}

// Suppress lint for accessing resources via LocalContext.current in a Composable
@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDimensDemoScreen() {
    // Obtain local configuration, density, and context for runtime metrics
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val ctx = LocalContext.current

    // Toggleable state used in the demo to show how changing ScreenType affects dimension results
    var currentScreenType by remember { mutableStateOf(ScreenType.LOWEST) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("AppDimens — Compose demo", fontWeight = FontWeight.SemiBold) })
        }
    ) { contentPadding ->
        // Main scrollable content column
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                // Use a fixed dimension utility for padding from the AppDimens library
                .padding(12.fxdp),
            verticalArrangement = Arrangement.spacedBy(12.fxdp)
        ) {
            item {
                InfoCard(
                    title = "Device metrics",
                    subtitle = "Quick readout of screen metrics (dp & px)"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.fxdp)) {
                        Text("screenWidthDp = ${configuration.screenWidthDp} dp")
                        Text("screenHeightDp = ${configuration.screenHeightDp} dp")
                        Text("smallestScreenWidthDp = ${configuration.smallestScreenWidthDp} dp")
                        val pxPerDp = density.density
                        // Show the current density factor (pixels per dp unit)
                        Text(String.format(Locale.getDefault(), "density = %.2f (px per dp)", pxPerDp))
                    }
                }
            }

            item {
                UsageCard(title = "Fixed vs Dynamic (basic)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        val baseDp = 16.dp

                        // Using the AppDimens extension helpers inside the AppDimens scope
                        // Fixed dimensions (dp, sp, px) scale based on the initial configuration/qualifier
                        val fixedDp = with(AppDimens) { baseDp.fixedDp(type = currentScreenType) }
                        val fixedSp = with(AppDimens) { baseDp.fixedSp(type = currentScreenType) }
                        val fixedPx = with(AppDimens) { baseDp.fixedPx(type = currentScreenType) }

                        // Dynamic dimensions scale proportionally to screen size changes
                        val dynamicDp = with(AppDimens) { baseDp.dynamicDp(type = currentScreenType) }
                        val dynamicSp = with(AppDimens) { baseDp.dynamicSp(type = currentScreenType) }
                        val dynamicPx = with(AppDimens) { baseDp.dynamicPx(type = currentScreenType) }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.fxdp)) {
                            // Demo tiles use the calculated Dp values
                            DemoTile(size = fixedDp, label = "fixed dp = ${fixedDp.value.toInt()}dp")
                            DemoTile(size = dynamicDp, label = "dynamic dp = ${dynamicDp.value.toInt()}dp")
                        }

                        Text("fixed sp = ${fixedSp.value}sp, fixed px ≈ ${fixedPx.toInt()}px")
                        Text("dynamic sp = ${dynamicSp.value}sp, dynamic px ≈ ${dynamicPx.toInt()}px")

                        Column (verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                            // Button to toggle the ScreenType (LOWEST/HIGHEST) which affects the calculation baseline
                            Button(onClick = {
                                currentScreenType = if (currentScreenType == ScreenType.LOWEST) ScreenType.HIGHEST else ScreenType.LOWEST
                            }) {
                                Text("Toggle ScreenType (now: $currentScreenType)")
                            }
                            Text("Tip: toggling screen type recomputes the underlying dimension calculation.")
                        }
                    }
                }
            }

            item {
                UsageCard(title = "Convenience properties (fxdp / fxsp / dydp / dypx)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        val sampleDp = 24.dp
                        // fxdp: convenience extension for fixed Dp calculation
                        val convFxDp = with(AppDimens) { sampleDp.fxdp }
                        // fxsp: convenience extension for fixed Sp calculation
                        val convFxSp = with(AppDimens) { 14.fxsp }
                        // dynamicPerDp: dynamic dimension based on a percentage of the relevant screen dimension
                        val convDyDp = with(AppDimens) { 0.10f.dynamicPerDp() } // 0.10f.dynamicPerDp(type = ScreenType.LOWEST or ScreenType.HIGHEST)

                        Text("sample base = ${sampleDp.value}dp")
                        Text("fxdp -> ${convFxDp.value}dp")
                        Text("fxsp(14.dp) -> ${convFxSp.value}sp")
                        Text("0.10f.dynamicPercentageDp() -> ${convDyDp.value}dp (10% of screen lowest/hight depending on type)")

                        // Demo: using fxsp directly for font size in a Text Composable
                        Text(
                            "This text uses AppDimens.fxsp (14.dp.fxsp)",
                            fontSize = with(AppDimens) { 14.dp.fxsp }
                        )
                    }
                }
            }

            item {
                UsageCard(title = "Custom qualifiers (screen / ui mode / intersection)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {

                        // Example: custom value based on screen width qualifier (WIDTH >= 360)
                        val customWidthDp = with(AppDimens) {
                            16.dp.fixed()
                                // If screen width (WIDTH) is >= 360, use 20dp as the base value
                                .screen(DpQualifier.WIDTH, 360, 20)
                                .type(currentScreenType)
                                .dp
                        }

                        Text("customWidthDp (WIDTH >= 360) -> ${customWidthDp.value}dp")

                        // IMPORTANT: UiModeType describes the device/ui mode (car, tv, watch, appliance, etc.) — not the theme (night/day).
                        // It demonstrates how to qualify a dimension based on the physical device mode.

                        // Dynamically try to get all UiModeType enum values for the demo buttons
                        val uiModeValues = runCatching { UiModeType::class.java.enumConstants?.toList() ?: emptyList() }.getOrNull() ?: emptyList()
                        var selectedUiMode by remember { mutableStateOf(uiModeValues.firstOrNull()) }

                        // Display the current device UI mode from the configuration
                        Text("Detected current UiMode: ${UiModeType.fromConfiguration(configuration.uiMode)}")

                        if (uiModeValues.isNotEmpty()) {
                            // Display buttons for selecting a UiModeType
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.fxdp)) {
                                items(uiModeValues.size) { index ->
                                    val mode = uiModeValues[index]
                                    Button(onClick = { selectedUiMode = mode }, modifier = Modifier.height(36.dp)) {
                                        Text(mode.name)
                                    }
                                }
                            }

                            Spacer(Modifier.height(6.fxdp))

                            // Use the selected UiModeType to build a custom intersection example.
                            val customUiModeDp = selectedUiMode?.let { sel ->
                                with(AppDimens) {
                                    18.fixed()
                                        // Intersection qualifier: (selected device mode) AND (small width >= 600) -> use 22dp
                                        .screen(sel, DpQualifier.SMALL_WIDTH, 600, 22)
                                        .type(currentScreenType)
                                        .dp
                                }
                            }

                            Text("Selected UiMode (for custom value): ${selectedUiMode?.name ?: "—"}")
                            Text("customUiModeDp (intersection if selected) -> ${customUiModeDp?.value?.toInt() ?: "n/a"}dp")

                        } else {
                            Text("UiModeType enum values not discoverable at runtime. Check your library's UiModeType for available constants (e.g. CAR, TELEVISION, WATCH, APPLIANCE, etc.).")
                        }

                        Text("These examples show the priority chain: INTERSECTION > UI MODE > QUALIFIER")
                    }
                }
            }

            item {
                UsageCard(title = "Physical units (mm / cm / inch) & radius") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        // Use the composable extension properties provided by AppDimensPhysicalUnits
                        // These convert physical units (mm, inch, cm) to screen pixels (Px)
                        val tenMmPx = with(AppDimensPhysicalUnits) { 10f.mm }
                        val twoInchPx = with(AppDimensPhysicalUnits) { 2f.inch }
                        val threeCmPx = with(AppDimensPhysicalUnits) { 3f.cm }

                        Text("10 mm ≈ ${tenMmPx.toInt()} px")
                        Text("2 in ≈ ${twoInchPx.toInt()} px")
                        Text("3 cm ≈ ${threeCmPx.toInt()} px")

                        // Radius helper: calculates the radius in Px from a diameter in a given UnitType
                        val radiusFromDiameterPx = with(AppDimensPhysicalUnits) { 30f.radius(UnitType.CM) }
                        Text("30 cm diameter -> radius ≈ ${radiusFromDiameterPx.toInt()} px")

                        // Unit size per pixel utility: retrieves the pixel size equivalent to 1 unit (e.g., 1 cm)
                        val oneCmPx = AppDimensPhysicalUnits.unitSizePerPx(UnitType.CM, ctx.resources)
                        Text("1 cm on this device ≈ ${oneCmPx.toInt()} px")
                    }
                }
            }

            item {
                UsageCard(title = "CalculateAvailableItemCount (layout helper)") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        var availableCount by remember { mutableStateOf(-1) }

                        Text("Container (full width) with fixed item size 80dp")

                        // CalculateAvailableItemCount measures the available space in the Box
                        // and calculates how many items of a given size + padding can fit.
                        with(AppDimens) {
                            CalculateAvailableItemCount(
                                itemSize = 80.fxdp,
                                itemPadding = 4.fxdp,
                                direction = DpQualifier.WIDTH, // Check availability along the width
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.fxdp)
                                    .border(1.fxdp, Color.Gray, RoundedCornerShape(6.fxdp)),
                                onResult = { availableCount = it }
                            )
                        }

                        Text("availableCount = $availableCount")

                        if (availableCount > 0) {
                            // Display the calculated number of tiles (max 6 for visual clarity)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.fxdp)) {
                                repeat(availableCount.coerceAtMost(6)) { i ->
                                    DemoTile(size = 80.fxdp, label = "item ${i + 1}")
                                }
                            }
                        }
                    }
                }
            }

            item {
                UsageCard(title = "Misc examples & utilities") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.fxdp)) {
                        // dynamicPercentageDp helper: calculates a dynamic Dp value based on a percentage of a screen dimension (LOWEST)
                        val tenPercentDp = with(AppDimens) { dynamicPercentageDp(0.10f, ScreenType.LOWEST) }
                        Text("10% of (lowest) screen dimension -> ${tenPercentDp.value.toInt()}dp")

                        // dynamicPercentageDp helper: calculates a dynamic Dp value based on a percentage of a screen dimension (HIGHEST)
                        val tenPercentDp2 = with(AppDimens) { dynamicPercentageDp(0.10f, ScreenType.HIGHEST) }
                        Text("10% of (highest) screen dimension -> ${tenPercentDp2.value.toInt()}dp")

                        // Conversions: converts an Sp value to a fixed Px value
                        val pxFromSp = with(AppDimens) { 18.fixedPx() }
                        Text("18sp fixedPx -> ${pxFromSp.toInt()} px")

                        // Show how to return raw px/float when needed, and use utility calculations
                        val diameterPx = with(AppDimensPhysicalUnits) { 5f.inch }
                        // Calculates circumference from diameter
                        val circumference = AppDimensPhysicalUnits.displayMeasureDiameter(diameterPx, true)
                        Text("5in -> diameter px = ${diameterPx.toInt()} px, circumference px ≈ ${circumference.toInt()} px")
                    }
                }
            }
        }
    }
}

// -- Small helper composables used above --

/**
 * A standard card component for displaying general information.
 */
@Composable
private fun InfoCard(title: String, subtitle: String, content: @Composable () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.fxdp),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.fxdp)
    ) {
        Column(modifier = Modifier.padding(12.fxdp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.fxdp))
            Text(subtitle, fontSize = 12.fxsp, color = Color.Gray)
            Spacer(Modifier.height(8.fxdp))
            content()
        }
    }
}

/**
 * A standard card component for wrapping AppDimens usage examples.
 */
@Composable
private fun UsageCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.fxdp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.fxdp)
    ) {
        Column(modifier = Modifier.padding(14.fxdp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.fxdp))
            content()
        }
    }
}

/**
 * A simple box tile used to visually represent a calculated Dp size.
 */
@Composable
private fun DemoTile(size: Dp, label: String) {
    Box(
        modifier = Modifier
            .size(size) // Use the calculated Dp size
            .background(Color(0xFFEEEEEE), shape = RoundedCornerShape(6.fxdp))
            .border(1.fxdp, Color.LightGray, RoundedCornerShape(6.fxdp)),
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 11.fxsp)
    }
}

// --- Preview for Visualization ---
@Preview(
    showBackground = true,
    device = "id:pixel_5", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO // Ensure light theme for preview
)
@Composable
fun AppDimensPreviewEN() {
    MaterialTheme {
        AppDimensDemoScreen()
    }
}