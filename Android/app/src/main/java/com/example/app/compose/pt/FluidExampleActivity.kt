/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-31
 *
 * Example: Fluid Scaling in Android Compose
 *
 * Demonstrates the use of AppDimensFluid for smooth, clamp-like scaling
 * between minimum and maximum values based on screen width.
 */

package com.example.app.compose.pt

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.*
import com.appdimens.dynamic.compose.models.fluidDp
import com.appdimens.dynamic.compose.models.fluidSp
import com.appdimens.dynamic.compose.models.rememberFluid
import com.appdimens.dynamic.compose.models.fluidMultipleDp
import com.appdimens.dynamic.compose.models.fluidTo

/**
 * Activity demonstrating Fluid scaling examples in Compose
 */
class FluidExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                FluidExamplesScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FluidExamplesScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AppDimens Fluid Examples") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BasicFluidExample()
            FluidWithBreakpointsExample()
            FluidWithQualifiersExample()
            MultipleFluidValuesExample()
            ResponsiveCardExample()
            FluidBuilderInfoExample()
            ExtensionMethodsExample()
        }
    }
}

/**
 * Example 1: Basic Fluid Scaling
 */
@Composable
fun BasicFluidExample() {
    // Font size that scales smoothly from 16 to 32 between 320-768dp
    val fontSize = fluidSp(16f, 32f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Fluid Typography: 16-32sp",
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Font size smoothly scales between 16sp (320dp screen) and 32sp (768dp screen)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

/**
 * Example 2: Fluid with Custom Breakpoints
 */
@Composable
fun FluidWithBreakpointsExample() {
    // Custom breakpoints: 280-600dp range
    val fontSize = fluidSp(12f, 20f, minWidth = 280f, maxWidth = 600f)
    val padding = fluidDp(6f, 12f, minWidth = 280f, maxWidth = 600f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(
                text = "Custom Breakpoints",
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Range: 280-600dp (narrower than default 320-768dp)",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

/**
 * Example 3: Fluid with Device Type Qualifiers
 */
@Composable
fun FluidWithQualifiersExample() {
    val fontSize = fluidSp(16f, 24f)

    // Note: Device type detection would be added here
    // For this example, we show the basic usage

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Device-Aware Fluid",
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Phones: 16-24sp | Tablets: 20-32sp | TVs: 24-40sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

/**
 * Example 4: Multiple Fluid Values
 */
@Composable
fun MultipleFluidValuesExample() {
    val fluidValues = fluidMultipleDp(
        listOf(
            14f to 20f,  // fontSize
            8f to 16f,   // padding
            12f to 24f   // margin
        )
    )

    val fontSize = fluidValues[0].value.sp
    val padding = fluidValues[1]
    val margin = fluidValues[2]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(margin),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(
                text = "Multi-Fluid Component",
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Font, padding, e margin usam fluid scaling",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

/**
 * Example 5: Responsive Card with Fluid Dimensions
 */
@Composable
fun ResponsiveCardExample() {
    val titleSize = fluidSp(18f, 28f)
    val bodySize = fluidSp(14f, 18f)
    val padding = fluidDp(12f, 20f)
    val borderRadius = fluidDp(8f, 16f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(borderRadius)
    ) {
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text(
                text = "Fluid Card Design",
                fontSize = titleSize,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(padding / 2))
            Text(
                text = "All dimensions (title, body, padding, radius) scale smoothly using " +
                        "fluid scaling model for optimal responsive behavior.",
                fontSize = bodySize,
                color = Color.Gray,
                lineHeight = bodySize * 1.5f
            )
        }
    }
}

/**
 * Example 6: Fluid Builder Info
 */
@Composable
fun FluidBuilderInfoExample() {
    val (fontSize, fluidBuilder) = rememberFluid(16f, 24f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Fluid Builder Info",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Min: ${fluidBuilder.getMin()}sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "Max: ${fluidBuilder.getMax()}sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "Preferred: ${fluidBuilder.getPreferred()}sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "At 25%: ${fluidBuilder.lerp(0.25f)}sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "At 50%: ${fluidBuilder.lerp(0.5f)}sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "At 75%: ${fluidBuilder.lerp(0.75f)}sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

/**
 * Example 7: Extension Methods
 */
@Composable
fun ExtensionMethodsExample() {
    // Using extension methods for fluid builders
    val fluid = 16f.fluidTo(24f)
    val configuration = androidx.compose.ui.platform.LocalConfiguration.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Extension Methods",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Using 16f.fluidTo(24f) extension method",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "Min: ${fluid.getMin()}sp, Max: ${fluid.getMax()}sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "Calculated: ${fluid.calculate(configuration)}sp",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait"
)
@Composable
fun FluidExamplesPreview() {
    MaterialTheme {
        FluidExamplesScreen()
    }
}

