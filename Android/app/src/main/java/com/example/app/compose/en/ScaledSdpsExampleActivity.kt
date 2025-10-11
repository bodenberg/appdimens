package com.example.app.compose.en

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.appdimens.library.DpQualifier
import com.appdimens.library.UiModeType
// Imports custom dimension extensions
import com.appdimens.sdps.* // Imports sdp, hdp, wdp, scaledDp extensions

class ScaledSdpsExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SdpDemoScreen()
        }
    }
}

/**
 * Defines the main layout for the demo application.
 * It uses `MaterialTheme` and `Surface` to set up the theme and background.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SdpDemoScreen() {
    // Applies a Material 3 theme (light color scheme by default).
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Organizes components vertically.
            Column(// Allows vertical scrolling.
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    // 16.sdp padding: adapts padding based on the smallest width.
                    .padding(16.sdp),
                // Spacing between items of 20.sdp.
                verticalArrangement = Arrangement.spacedBy(20.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Main title
                Text(
                    "AppDimens SDP Demo",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )

                // Descriptive subtitle
                Text(
                    "Demonstration of using the .sdp, .hdp, .wdp, and scaledDp() extensions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                // --- Adaptive Dimension Examples ---

                // .sdp Example (Smallest Width)
                ExampleCard(
                    title = ".sdp Example (Smallest Width)",
                    description = "16.sdp adjusts proportionally to the screen's smallest width. The box is 60.sdp.",
                    boxSize = 60.sdp // The box size adapts to the 'smallest width'.
                )

                // .hdp Example (Height)
                ExampleCard(
                    title = ".hdp Example (Height)",
                    description = "80.hdp adapts according to the screen height. Change orientation to see the difference.",
                    boxSize = 80.hdp // The box size adapts to the total screen height.
                )

                // .wdp Example (Width)
                ExampleCard(
                    title = ".wdp Example (Width)",
                    description = "120.wdp depends on the device's total width. The box is 120.wdp.",
                    boxSize = 120.wdp // The box size adapts to the total screen width.
                )

                // Scaled Dimension Example (Scaled Dp)
                ScaledExampleCard()
            }
        }
    }
}

/**
 * Generic `@Composable` component to display a dimension example.
 *
 * @param title The example title (e.g., ".sdp").
 * @param description The functionality description.
 * @param boxSize The adaptive Dp value to be used for the Box size.
 */
@Composable
fun ExampleCard(title: String, description: String, boxSize: Dp) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)), // Card background color
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            // Internal card padding using 16.sdp for responsiveness.
            modifier = Modifier.padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp) // Responsive internal spacing.
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodyMedium)

            // Demonstration Box
            Box(
                modifier = Modifier
                    // Sets the size (width and height) of the box using the adaptive value.
                    .size(boxSize)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // Displays the resolved Dp value (for debug/visualization purposes)
                Text(
                    text = "${boxSize.value.toInt()}dp",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * `@Composable` component to demonstrate the use of the `scaledDp()` function.
 * This function allows defining a base value and specific overrides for different
 * screen qualifiers (e.g., TV, smallest width sw600dp).
 */
@Composable
fun ScaledExampleCard() {
    // 1. Defines the base value (100) and starts the configuration chain.
    val dynamicDp = 100.scaledDp()
        // 2. Override for UI mode: If it's a TV, use 200dp.
        .screen(UiModeType.TELEVISION, 200)
        // 3. Override for Dp qualifier: If the smallest width is >= 600dp, use 150dp.
        .screen(DpQualifier.SMALL_WIDTH, 600, 150)
        // 4. Override for UI mode (NORMAL is the default, but good for clarity): uses 100dp.
        .screen(UiModeType.NORMAL, 100)
        // 5. Finalizes the chain and resolves the Dp, adapting it based on the .sdp qualifier.
        .sdp // Uses the 'smallest width' adaptation on the final value.

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // Card background color
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp)
        ) {
            Text(
                "scaledDp() Usage Example",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "The size changes dynamically based on the device type and smallest width. Current value: ${dynamicDp.value.toInt()}dp",
                style = MaterialTheme.typography.bodyMedium
            )

            // Demonstration Box
            Box(
                modifier = Modifier
                    // The Box uses the dynamically resolved value.
                    .size(dynamicDp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // Displays the resolved Dp value.
                Text(
                    text = "${dynamicDp.value.toInt()}dp",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppDimensSdpExample() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface {
            SdpDemoScreen()
        }
    }
}