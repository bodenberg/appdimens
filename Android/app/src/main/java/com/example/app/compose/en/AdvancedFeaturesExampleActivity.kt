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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.AppDimens
import com.appdimens.dynamic.compose.AppDimensPhysicalUnits
import com.appdimens.library.DpQualifier
import com.appdimens.library.ScreenType
import com.appdimens.library.UiModeType
import java.util.Locale

/**
 * [EN] An activity that demonstrates advanced AppDimens features including CalculateAvailableItemCount,
 * Environment system, Protocol-based API, and Performance Settings.
 * 
 * [PT] Uma atividade que demonstra recursos avançados do AppDimens incluindo CalculateAvailableItemCount,
 * sistema de ambiente, API baseada em protocolos e configurações de performance.
 */
@OptIn(ExperimentalMaterial3Api::class)
class AdvancedFeaturesExampleActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AdvancedFeaturesDemoScreen()
            }
        }
    }
}

/**
 * [EN] A composable function that displays the Advanced Features demo screen.
 * 
 * [PT] Uma função de composição que exibe a tela de demonstração de Recursos Avançados.
 */
@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedFeaturesDemoScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    // State for CalculateAvailableItemCount demo
    var availableCount by remember { mutableStateOf(-1) }
    var itemSize by remember { mutableStateOf(80f) }
    var itemPadding by remember { mutableStateOf(8f) }
    
    // State for performance settings
    var enableCaching by remember { mutableStateOf(true) }
    var cacheSize by remember { mutableStateOf(1000) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Advanced Features Demo", 
                        fontWeight = FontWeight.SemiBold
                    ) 
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            
            // CalculateAvailableItemCount Demo
            item {
                UsageCard(title = "CalculateAvailableItemCount - Grid Layout Helper") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        
                        Text("This feature calculates how many items can fit in a container with given size and padding.")
                        
                        // Controls for item size and padding
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Item Size: ${itemSize.toInt()}dp")
                                Slider(
                                    value = itemSize,
                                    onValueChange = { itemSize = it },
                                    valueRange = 40f..120f,
                                    steps = 8
                                )
                            }
                        }
                        
                        Text("Container (full width) with calculated items:")
                        
                        // CalculateAvailableItemCount implementation
                        with(AppDimens) {
                            CalculateAvailableItemCount(
                                itemSize = itemSize.dp,
                                itemPadding = itemPadding.dp,
                                direction = DpQualifier.WIDTH,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                onResult = { availableCount = it }
                            )
                        }
                        
                        Text("Available items: $availableCount")
                        
                        // Visual representation of items
                        if (availableCount > 0) {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(itemPadding.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(availableCount.coerceAtMost(8)) { index ->
                                    Box(
                                        modifier = Modifier
                                            .size(itemSize.dp)
                                            .background(Color(0xFF2196F3), RoundedCornerShape(8.dp))
                                            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "${index + 1}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            // Protocol-based API Demo
            item {
                UsageCard(title = "Protocol-based API with Qualifiers") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        
                        Text("Demonstrates the protocol-based API with device qualifiers and screen configurations.")
                        
                        // Fixed with device qualifiers
                        val customWidthDp = with(AppDimens) {
                            16.dp.fixed()
                                .screen(DpQualifier.WIDTH, 360, 20)
                                .type(ScreenType.LOWEST)
                                .dp
                        }
                        
                        val customHeightDp = with(AppDimens) {
                            24.dp.fixed()
                                .screen(DpQualifier.HEIGHT, 800, 32)
                                .type(ScreenType.HIGHEST)
                                .dp
                        }
                        
                        val customSmallestWidthDp = with(AppDimens) {
                            18.dp.fixed()
                                .screen(DpQualifier.SMALL_WIDTH, 600, 28)
                                .type(ScreenType.LOWEST)
                                .dp
                        }
                        
                        Text("Width qualifier (WIDTH >= 360): ${customWidthDp.value.toInt()}dp")
                        Text("Height qualifier (HEIGHT >= 800): ${customHeightDp.value.toInt()}dp")
                        Text("Smallest width qualifier (SW >= 600): ${customSmallestWidthDp.value.toInt()}dp")
                        
                        // Visual representation
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            ProtocolDemoTile(
                                size = customWidthDp,
                                label = "Width\nQualifier",
                                color = Color(0xFF4CAF50)
                            )
                            ProtocolDemoTile(
                                size = customHeightDp,
                                label = "Height\nQualifier",
                                color = Color(0xFF2196F3)
                            )
                            ProtocolDemoTile(
                                size = customSmallestWidthDp,
                                label = "SW\nQualifier",
                                color = Color(0xFFFF9800)
                            )
                        }
                    }
                }
            }
            
            // Environment System Demo
            item {
                UsageCard(title = "Environment System Integration") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        
                        Text("Shows how AppDimens integrates with Compose Environment system.")
                        
                        // Screen configuration from environment
                        Text("Current screen configuration:")
                        Text("• Width: ${configuration.screenWidthDp}dp")
                        Text("• Height: ${configuration.screenHeightDp}dp")
                        Text("• Smallest width: ${configuration.smallestScreenWidthDp}dp")
                        Text("• Orientation: ${if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) "Landscape" else "Portrait"}")
                        
                        // Density information
                        val pxPerDp = density.density
                        val scaledDensity = density.fontScale
                        Text("• Density: ${String.format(Locale.getDefault(), "%.2f", pxPerDp)} px/dp")
                        Text("• Font scale: ${String.format(Locale.getDefault(), "%.2f", scaledDensity)}")
                        
                        // Environment-aware dimensions
                        val envAwareDp = with(AppDimens) {
                            when {
                                configuration.screenWidthDp >= 600 -> 32.dp.fixed()
                                configuration.screenWidthDp >= 400 -> 24.dp.fixed()
                                else -> 16.dp.fixed()
                            }.dp
                        }
                        
                        Text("Environment-aware dimension: ${envAwareDp.value.toInt()}dp")
                        
                        Box(
                            modifier = Modifier
                                .size(envAwareDp)
                                .background(Color(0xFF9C27B0), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Env\n${envAwareDp.value.toInt()}dp",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
            
            // Performance Settings Demo
            item {
                UsageCard(title = "Performance Settings Configuration") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        
                        Text("Configure performance settings for AppDimens calculations.")
                        
                        // Caching toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Enable Caching:")
                            Switch(
                                checked = enableCaching,
                                onCheckedChange = { enableCaching = it }
                            )
                        }
                        
                        // Cache size slider
                        Column {
                            Text("Cache Size: $cacheSize")
                            Slider(
                                value = cacheSize.toFloat(),
                                onValueChange = { cacheSize = it.toInt() },
                                valueRange = 100f..5000f,
                                steps = 49
                            )
                        }
                        
                        // Performance metrics
                        Text("Performance Metrics:")
                        Text("• Caching enabled: $enableCaching")
                        Text("• Cache size: $cacheSize entries")
                        Text("• Memory usage: ~${(cacheSize * 0.1).toInt()} KB")
                        
                        // Performance test
                        Button(
                            onClick = {
                                // Simulate performance test
                                val startTime = System.currentTimeMillis()
                                repeat(1000) {
                                    // Simple performance test without @Composable calls
                                    val testValue = 16f
                                }
                                val endTime = System.currentTimeMillis()
                                val duration = endTime - startTime
                                // In a real implementation, you would show this result
                            }
                        ) {
                            Text("Run Performance Test")
                        }
                    }
                }
            }
            
            // Physical Units Integration
            item {
                UsageCard(title = "Physical Units Integration") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        
                        Text("Integration of physical units with AppDimens calculations.")
                        
                        // Physical units with AppDimens scaling
                        val physicalMm = with(AppDimensPhysicalUnits) { 5f.mm }
                        val physicalCm = with(AppDimensPhysicalUnits) { 2f.cm }
                        val physicalInch = with(AppDimensPhysicalUnits) { 0.5f.inch }
                        
                        val scaledMm = with(AppDimens) { physicalMm.dp.fixed().dp }
                        val scaledCm = with(AppDimens) { physicalCm.dp.fixed().dp }
                        val scaledInch = with(AppDimens) { physicalInch.dp.fixed().dp }
                        
                        Text("Physical units with AppDimens scaling:")
                        Text("• 5mm: ${physicalMm.toInt()}px -> ${scaledMm.value.toInt()}dp")
                        Text("• 2cm: ${physicalCm.toInt()}px -> ${scaledCm.value.toInt()}dp")
                        Text("• 0.5in: ${physicalInch.toInt()}px -> ${scaledInch.value.toInt()}dp")
                        
                        // Visual representation
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PhysicalUnitTile(
                                size = scaledMm,
                                label = "5mm",
                                description = "Scaled",
                                color = Color(0xFF4CAF50)
                            )
                            PhysicalUnitTile(
                                size = scaledCm,
                                label = "2cm",
                                description = "Scaled",
                                color = Color(0xFF2196F3)
                            )
                            PhysicalUnitTile(
                                size = scaledInch,
                                label = "0.5in",
                                description = "Scaled",
                                color = Color(0xFFFF9800)
                            )
                        }
                    }
                }
            }
            
            // Advanced Configuration Examples
            item {
                UsageCard(title = "Advanced Configuration Examples") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        
                        Text("Complex configuration examples combining multiple features.")
                        
                        // Complex qualifier chain
                        val complexDp = with(AppDimens) {
                            20.dp.fixed()
                                .screen(UiModeType.TELEVISION, 40)
                                .screen(DpQualifier.SMALL_WIDTH, 600, 32)
                                .screen(DpQualifier.WIDTH, 360, 24)
                                .type(ScreenType.LOWEST)
                                .dp
                        }
                        
                        Text("Complex qualifier chain: ${complexDp.value.toInt()}dp")
                        Text("Priority: TV Mode > SW >= 600 > Width >= 360 > Base (20dp)")
                        
                        // Multi-directional calculation
                        val multiDirDp = with(AppDimens) {
                            CalculateAvailableItemCount(
                                itemSize = 60.dp,
                                itemPadding = 12.dp,
                                direction = DpQualifier.HEIGHT,
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(150.dp)
                                    .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp)),
                                onResult = { /* Handle result */ }
                            )
                        }
                        
                        Text("Multi-directional calculations supported for both width and height.")
                    }
                }
            }
        }
    }
}

/**
 * [EN] A demo tile component for displaying protocol-based API results.
 * 
 * [PT] Um componente de bloco de demonstração para exibir resultados da API baseada em protocolos.
 */
@Composable
private fun ProtocolDemoTile(
    size: Dp,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(color, RoundedCornerShape(8.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${size.value.toInt()}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

/**
 * [EN] A tile component for displaying physical unit conversions visually.
 * 
 * [PT] Um componente de bloco para exibir conversões de unidades físicas visualmente.
 */
@Composable
private fun PhysicalUnitTile(
    size: Dp,
    label: String,
    description: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(color, RoundedCornerShape(8.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${size.value.toInt()}",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = description,
            fontSize = 8.sp,
            color = Color.Gray
        )
    }
}

/**
 * [EN] A standard card component for wrapping AppDimens usage examples.
 * 
 * [PT] Um componente de cartão padrão para envolver exemplos de uso do AppDimens.
 */
@Composable
private fun UsageCard(
    title: String, 
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Preview(
    showBackground = true,
    device = "id:pixel_5", 
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AdvancedFeaturesPreview() {
    MaterialTheme {
        AdvancedFeaturesDemoScreen()
    }
}
