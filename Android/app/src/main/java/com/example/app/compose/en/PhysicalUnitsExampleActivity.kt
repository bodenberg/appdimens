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
import androidx.compose.ui.unit.sp
import com.appdimens.dynamic.compose.AppDimensPhysicalUnits
import com.appdimens.library.UnitType
import java.util.Locale

/**
 * [EN] An activity that demonstrates the use of physical units with AppDimens in Jetpack Compose.
 * Shows conversions between physical units (mm, cm, inches) and screen pixels.
 * 
 * [PT] Uma atividade que demonstra o uso de unidades físicas com AppDimens no Jetpack Compose.
 * Mostra conversões entre unidades físicas (mm, cm, polegadas) e pixels da tela.
 */
@OptIn(ExperimentalMaterial3Api::class)
class PhysicalUnitsExampleActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PhysicalUnitsDemoScreen()
            }
        }
    }
}

/**
 * [EN] A composable function that displays the Physical Units demo screen.
 * 
 * [PT] Uma função de composição que exibe a tela de demonstração de Unidades Físicas.
 */
@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhysicalUnitsDemoScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "Physical Units Demo", 
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
            
            // Basic Physical Units Conversion
            item {
                InfoCard(
                    title = "Basic Physical Units Conversion",
                    subtitle = "Converting physical units to screen pixels"
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        
                        // Millimeters
                        val tenMmPx = with(AppDimensPhysicalUnits) { 10f.mm }
                        val fiveMmPx = with(AppDimensPhysicalUnits) { 5f.mm }
                        val oneMmPx = with(AppDimensPhysicalUnits) { 1f.mm }
                        
                        Text("10 mm ≈ ${tenMmPx.toInt()} px")
                        Text("5 mm ≈ ${fiveMmPx.toInt()} px")
                        Text("1 mm ≈ ${oneMmPx.toInt()} px")
                        
                        // Visual representation of millimeters
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PhysicalUnitTile(
                                size = tenMmPx.dp,
                                label = "10mm",
                                description = "10 millimeters",
                                color = Color(0xFF4CAF50)
                            )
                            PhysicalUnitTile(
                                size = fiveMmPx.dp,
                                label = "5mm",
                                description = "5 millimeters",
                                color = Color(0xFF8BC34A)
                            )
                            PhysicalUnitTile(
                                size = oneMmPx.dp,
                                label = "1mm",
                                description = "1 millimeter",
                                color = Color(0xFFCDDC39)
                            )
                        }
                    }
                }
            }
            
            // Centimeters
            item {
                UsageCard(title = "Centimeters Conversion") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        
                        val fiveCmPx = with(AppDimensPhysicalUnits) { 5f.cm }
                        val threeCmPx = with(AppDimensPhysicalUnits) { 3f.cm }
                        val oneCmPx = with(AppDimensPhysicalUnits) { 1f.cm }
                        
                        Text("5 cm ≈ ${fiveCmPx.toInt()} px")
                        Text("3 cm ≈ ${threeCmPx.toInt()} px")
                        Text("1 cm ≈ ${oneCmPx.toInt()} px")
                        
                        // Visual representation of centimeters
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PhysicalUnitTile(
                                size = fiveCmPx.dp,
                                label = "5cm",
                                description = "5 centimeters",
                                color = Color(0xFF2196F3)
                            )
                            PhysicalUnitTile(
                                size = threeCmPx.dp,
                                label = "3cm",
                                description = "3 centimeters",
                                color = Color(0xFF03A9F4)
                            )
                            PhysicalUnitTile(
                                size = oneCmPx.dp,
                                label = "1cm",
                                description = "1 centimeter",
                                color = Color(0xFF00BCD4)
                            )
                        }
                    }
                }
            }
            
            // Inches
            item {
                UsageCard(title = "Inches Conversion") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        
                        val twoInchPx = with(AppDimensPhysicalUnits) { 2f.inch }
                        val oneInchPx = with(AppDimensPhysicalUnits) { 1f.inch }
                        val halfInchPx = with(AppDimensPhysicalUnits) { 0.5f.inch }
                        
                        Text("2 in ≈ ${twoInchPx.toInt()} px")
                        Text("1 in ≈ ${oneInchPx.toInt()} px")
                        Text("0.5 in ≈ ${halfInchPx.toInt()} px")
                        
                        // Visual representation of inches
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            PhysicalUnitTile(
                                size = twoInchPx.dp,
                                label = "2in",
                                description = "2 inches",
                                color = Color(0xFFFF9800)
                            )
                            PhysicalUnitTile(
                                size = oneInchPx.dp,
                                label = "1in",
                                description = "1 inch",
                                color = Color(0xFFFFC107)
                            )
                            PhysicalUnitTile(
                                size = halfInchPx.dp,
                                label = "0.5in",
                                description = "0.5 inch",
                                color = Color(0xFFFFEB3B)
                            )
                        }
                    }
                }
            }
            
            // Radius and Diameter Calculations
            item {
                UsageCard(title = "Radius and Diameter Calculations") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        
                        // Radius from diameter
                        val radiusFromDiameterPx = with(AppDimensPhysicalUnits) { 30f.radius(UnitType.CM) }
                        Text("30 cm diameter -> radius ≈ ${radiusFromDiameterPx.toInt()} px")
                        
                        // Circumference calculation
                        val diameterPx = with(AppDimensPhysicalUnits) { 5f.inch }
                        val circumference = AppDimensPhysicalUnits.displayMeasureDiameter(diameterPx, true)
                        Text("5 inch diameter -> circumference ≈ ${circumference.toInt()} px")
                        
                        // Visual representation
                        Box(
                            modifier = Modifier
                                .size(radiusFromDiameterPx.dp * 2)
                                .background(Color(0xFF9C27B0), RoundedCornerShape(radiusFromDiameterPx.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Circle\n${radiusFromDiameterPx.toInt()}px radius",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }
            
            // Unit Size Per Pixel
            item {
                UsageCard(title = "Unit Size Per Pixel") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        
                        val oneMmPx = AppDimensPhysicalUnits.unitSizePerPx(UnitType.MM, context.resources)
                        val oneCmPx = AppDimensPhysicalUnits.unitSizePerPx(UnitType.CM, context.resources)
                        val oneInchPx = AppDimensPhysicalUnits.unitSizePerPx(UnitType.INCH, context.resources)
                        
                        Text("1 mm on this device ≈ ${oneMmPx.toInt()} px")
                        Text("1 cm on this device ≈ ${oneCmPx.toInt()} px")
                        Text("1 inch on this device ≈ ${oneInchPx.toInt()} px")
                        
                        // Show device density information
                        val density = LocalDensity.current.density
                        val scaledDensity = LocalDensity.current.fontScale
                        Text("Device density: ${String.format(Locale.getDefault(), "%.2f", density)}")
                        Text("Scaled density: ${String.format(Locale.getDefault(), "%.2f", scaledDensity)}")
                    }
                }
            }
            
            // Real-World Applications
            item {
                UsageCard(title = "Real-World Applications") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        
                        Text("Physical units are useful for:")
                        Text("• Touch targets (minimum 44dp ≈ 7mm)")
                        Text("• Print layouts and measurements")
                        Text("• Accessibility guidelines")
                        Text("• Cross-platform consistency")
                        
                        // Touch target example
                        val touchTargetPx = with(AppDimensPhysicalUnits) { 7f.mm } // Minimum touch target
                        Box(
                            modifier = Modifier
                                .size(touchTargetPx.dp)
                                .background(Color(0xFF607D8B), RoundedCornerShape(8.dp))
                                .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Touch\nTarget\n${touchTargetPx.toInt()}px",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 8.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }
            }
            
            // Comparison Table
            item {
                UsageCard(title = "Unit Comparison Table") {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        
                        val units = listOf(
                            "1 mm" to with(AppDimensPhysicalUnits) { 1f.mm },
                            "1 cm" to with(AppDimensPhysicalUnits) { 1f.cm },
                            "1 inch" to with(AppDimensPhysicalUnits) { 1f.inch },
                            "10 mm" to with(AppDimensPhysicalUnits) { 10f.mm },
                            "2.54 cm" to with(AppDimensPhysicalUnits) { 2.54f.cm },
                            "1 inch" to with(AppDimensPhysicalUnits) { 1f.inch }
                        )
                        
                        units.forEach { (unit, pixels) ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("$unit:")
                                Text("${pixels.toInt()} px")
                            }
                        }
                    }
                }
            }
        }
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
 * [EN] A standard card component for displaying general information.
 * 
 * [PT] Um componente de cartão padrão para exibir informações gerais.
 */
@Composable
private fun InfoCard(
    title: String, 
    subtitle: String, 
    content: @Composable () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            content()
        }
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
fun PhysicalUnitsPreview() {
    MaterialTheme {
        PhysicalUnitsDemoScreen()
    }
}
