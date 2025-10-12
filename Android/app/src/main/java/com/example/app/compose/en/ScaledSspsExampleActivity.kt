/**
 * Author & Developer: Jean Bodenberg
 * Date: 2025-10-04
 *
 * Library: AppDimens
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.app.compose.en

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.DisplaySettings
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.appdimens.library.DpQualifier
import com.appdimens.library.UiModeType
import com.appdimens.ssps.*

/**
 * Example Activity to demonstrate the full usage
 * of the AppDimensSsp library.
 *
 * Shows both direct and conditional usage of the functions:
 * - .ssp, .hsp, .wsp
 * - .scaledSp() with various rule combinations
 */
class ScaledSspsExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppDimensSspExampleScreen()
                }
            }
        }
    }
}

@Composable
fun AppDimensSspExampleScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ---------------------------------------------------------------
        // HEADER
        // ---------------------------------------------------------------
        item {
            Text(
                text = "Demo - AppDimensSsp",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Dynamic text scaling (Sp) for Compose",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // ---------------------------------------------------------------
        // SECTION 1 — Direct Scaling (ssp, hsp, wsp)
        // ---------------------------------------------------------------
        item {
            DemoCard(
                title = "Direct Scaling",
                icon = Icons.Default.FormatSize,
                description = "Direct usage of .ssp, .hsp, and .wsp extensions " +
                        "to automatically adjust text size according to actual screen dimensions."
            ) {
                Text("16.ssp → based on smallest width (sw)", fontSize = 10.ssp)
                Text("18.hsp → based on height", fontSize = 10.hsp)
                Text("18.wsp → based on width", fontSize = 10.wsp)
            }
        }

        // ---------------------------------------------------------------
        // SECTION 2 — Conditional Scaling (Scaled)
        // ---------------------------------------------------------------
        item {
            val scaledExample = 10.scaledSp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 720, 24)
                .screen(UiModeType.CAR, 11)
                .screen(DpQualifier.SMALL_WIDTH, 600, 12)
                .screen(DpQualifier.HEIGHT, 800, 13)
                .screen(DpQualifier.WIDTH, 400, 14)

            DemoCard(
                title = "Conditional Scaling (Scaled)",
                icon = Icons.Default.DisplaySettings,
                description = "Define custom rules based on UI mode (TV, Car, etc.) " +
                        "and screen qualifiers (width, height, or smallest width)."
            ) {
                Text("Dynamically scaled text:", fontSize = scaledExample.ssp)
                Text("Based on height →", fontSize = scaledExample.hsp)
                Text("Based on width →", fontSize = scaledExample.wsp)
            }
        }

        // ---------------------------------------------------------------
        // SECTION 3 — Visual Comparison
        // ---------------------------------------------------------------
        item {
            DemoCard(
                title = "Scale Comparison",
                icon = Icons.Default.Devices,
                description = "Shows the difference between base value and automatically " +
                        "scaled values according to rules and screen dimensions."
            ) {
                Text(
                    text = "Base 14sp → ${14.ssp.value}sp",
                    fontSize = 14.ssp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                val dynamicScaledText = 14.scaledSp()
                    .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 720, 24)
                Text(
                    text = "TV Mode (sw ≥ 720dp) → dynamicScaledText.ssp",
                    fontSize = dynamicScaledText.ssp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Powered by AppDimens Library",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Demo card with title, icon, description, and content.
 */
@Composable
fun DemoCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    description: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )

            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppDimensSspExample() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface {
            AppDimensSspExampleScreen()
        }
    }
}
