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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * [EN] A comprehensive example activity that demonstrates all AppDimens features
 * including Fixed, Dynamic, SDPs, SSPs, Games, Physical Units, and Advanced Features.
 * 
 * [PT] Uma atividade de exemplo abrangente que demonstra todos os recursos do AppDimens
 * incluindo Fixed, Dynamic, SDPs, SSPs, Games, Physical Units e Recursos Avançados.
 */
@OptIn(ExperimentalMaterial3Api::class)
class ComprehensiveExampleActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ComprehensiveDemoScreen()
            }
        }
    }
}

/**
 * [EN] A composable function that displays the comprehensive demo screen with navigation to all features.
 * 
 * [PT] Uma função de composição que exibe a tela de demonstração abrangente com navegação para todos os recursos.
 */
@SuppressLint("LocalContextResourcesRead")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprehensiveDemoScreen() {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    var selectedFeature by remember { mutableStateOf(0) }
    
    val features = listOf(
        FeatureItem(
            title = "Fixed Dimensions",
            description = "Logarithmic scaling for UI elements",
            icon = Icons.Default.Tune,
            activity = FixedExampleActivity::class.java
        ),
        FeatureItem(
            title = "Dynamic Dimensions",
            description = "Proportional scaling for containers",
            icon = Icons.Default.AspectRatio,
            activity = DynamicExampleActivity::class.java
        ),
        FeatureItem(
            title = "SDPs (Scaled DP)",
            description = "Width, height, and smallest width scaling",
            icon = Icons.Default.Straighten,
            activity = ScaledSdpsExampleActivity::class.java
        ),
        FeatureItem(
            title = "SSPs (Scaled SP)",
            description = "Dynamic text scaling for different screen sizes",
            icon = Icons.Default.FormatSize,
            activity = ScaledSspsExampleActivity::class.java
        ),
        // FeatureItem(
        //     title = "Games Module",
        //     description = "Game-specific dimension calculations with C++/NDK",
        //     icon = Icons.Default.Games,
        //     activity = GamesExampleActivity::class.java
        // ),
        FeatureItem(
            title = "Physical Units",
            description = "Convert mm, cm, inches to screen pixels",
            icon = Icons.Default.Straighten,
            activity = PhysicalUnitsExampleActivity::class.java
        ),
        FeatureItem(
            title = "Advanced Features",
            description = "CalculateAvailableItemCount, Environment, Protocols",
            icon = Icons.Default.Settings,
            activity = AdvancedFeaturesExampleActivity::class.java
        )
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "AppDimens - Complete Demo", 
                        fontWeight = FontWeight.SemiBold
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
            
            // Header Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "AppDimens Library",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Complete responsive dimension management system for Android",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // Library info
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            InfoChip("Version", "1.0.6")
                            InfoChip("Modules", "7")
                            InfoChip("Features", "20+")
                        }
                    }
                }
            }
            
            // Features Grid
            item {
                Text(
                    text = "Available Features",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Feature Cards
            items(features.size) { index ->
                val feature = features[index]
                FeatureCard(
                    feature = feature,
                    isSelected = selectedFeature == index,
                    onClick = { selectedFeature = index }
                )
            }
            
            // Quick Demo Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Quick Demo",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "Screen Configuration:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text("• Width: ${configuration.screenWidthDp}dp")
                        Text("• Height: ${configuration.screenHeightDp}dp")
                        Text("• Smallest width: ${configuration.smallestScreenWidthDp}dp")
                        Text("• Orientation: ${if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) "Landscape" else "Portrait"}")
                        
                        val density = LocalDensity.current.density
                        Text("• Density: ${String.format("%.2f", density)} px/dp")
                    }
                }
            }
            
            // Usage Instructions
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "How to Use",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Text(
                            text = "1. Tap on any feature card above to explore specific functionality",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "2. Each demo shows practical examples with visual representations",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "3. Try rotating your device to see responsive behavior",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "4. Use different device configurations to test scaling",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

/**
 * [EN] Data class representing a feature item.
 * 
 * [PT] Classe de dados representando um item de recurso.
 */
data class FeatureItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val activity: Class<*>
)

/**
 * [EN] A card component for displaying feature information.
 * 
 * [PT] Um componente de cartão para exibir informações de recursos.
 */
@Composable
private fun FeatureCard(
    feature: FeatureItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isSelected) {
                    Modifier.border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.size(32.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = feature.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Navigate to ${feature.title}",
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        }
    }
}

/**
 * [EN] A small info chip component.
 * 
 * [PT] Um pequeno componente de chip de informação.
 */
@Composable
private fun InfoChip(
    label: String,
    value: String
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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
fun ComprehensiveDemoPreview() {
    MaterialTheme {
        ComprehensiveDemoScreen()
    }
}
