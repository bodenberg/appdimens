/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.compose.pt

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
import com.appdimens.ssps.compose.hsp
import com.appdimens.ssps.compose.scaledSp
import com.appdimens.ssps.compose.ssp
import com.appdimens.ssps.compose.wsp

/**
 * [EN] Visual and functional example activity to demonstrate
 * the full usage of the AppDimensSsp library.
 *
 * Shows direct and conditional usage examples of the functions:
 * - .ssp, .hsp, .wsp
 * - .scaledSp() with different rule combinations
 * 
 * [PT] Activity de exemplo visual e funcional para demonstrar
 * o uso completo da biblioteca AppDimensSsp.
 *
 * Mostra exemplos diretos e condicionais de uso das funções:
 * - .ssp, .hsp, .wsp
 * - .scaledSp() com diferentes combinações de regras
 */
class ScaledSspsExampleActivity : ComponentActivity() {
    /**
     * [EN] Called when the activity is first created.
     * 
     * [PT] Chamado quando a atividade é criada pela primeira vez.
     */
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

/**
 * [EN] A composable function that displays the AppDimens SSP example screen.
 * 
 * [PT] Uma função de composição que exibe a tela de exemplo do AppDimens SSP.
 */
@Composable
fun AppDimensSspExampleScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // [EN] Main header of the demonstration
        // [PT] Cabeçalho principal da demonstração
        item {
            Text(
                text = "Demonstração - AppDimensSsp",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Escalonamento dinâmico de texto (Sp) para Compose",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // [EN] ---------------------------------------------------------------
        // [PT] ---------------------------------------------------------------
        // [EN] SECTION 1 — Direct Scaling (ssp, hsp, wsp)
        // [PT] SEÇÃO 1 — Escalonamento Direto (ssp, hsp, wsp)
        // [EN] ---------------------------------------------------------------
        // [PT] ---------------------------------------------------------------
        item {
            DemoCard(
                title = "Escalonamento Direto",
                icon = Icons.Default.FormatSize,
                description = "Uso direto das extensões .ssp, .hsp e .wsp " +
                        "para ajustar automaticamente o tamanho do texto de acordo " +
                        "com as dimensões reais da tela."
            ) {
                Text("16.ssp → baseado na menor largura (sw)", fontSize = 10.ssp)
                Text("18.hsp → baseado na altura", fontSize = 10.hsp)
                Text("18.wsp → baseado na largura", fontSize = 10.wsp)
            }
        }

        // [EN] ---------------------------------------------------------------
        // [PT] ---------------------------------------------------------------
        // [EN] SECTION 2 — Conditional Scaling (Scaled)
        // [PT] SEÇÃO 2 — Escalonamento Condicional (Escalado)
        // [EN] ---------------------------------------------------------------
        // [PT] ---------------------------------------------------------------
        item {
            val scaledExample = 10.scaledSp()
                .screen(UiModeType.TELEVISION, DpQualifier.SMALL_WIDTH, 720, 24)
                .screen(UiModeType.CAR, 11)
                .screen(DpQualifier.SMALL_WIDTH, 600, 12)
                .screen(DpQualifier.HEIGHT, 800, 13)
                .screen(DpQualifier.WIDTH, 400, 14)

            DemoCard(
                title = "Escalonamento Condicional (Scaled)",
                icon = Icons.Default.DisplaySettings,
                description = "Define regras personalizadas com base no modo de UI (TV, carro, etc.) " +
                        "e qualificadores de tela (largura, altura ou smallest width)."
            ) {
                Text("Texto escalonado dinamicamente:", fontSize = scaledExample.ssp)
                Text("Baseado na altura →", fontSize = scaledExample.hsp)
                Text("Baseado na largura →", fontSize = scaledExample.wsp)
            }
        }

        // [EN] ---------------------------------------------------------------
        // [PT] ---------------------------------------------------------------
        // [EN] SECTION 3 — Visual Comparison
        // [PT] SEÇÃO 3 — Comparativo Visual
        // [EN] ---------------------------------------------------------------
        // [PT] ---------------------------------------------------------------
        item {
            DemoCard(
                title = "Comparação de Escalas",
                icon = Icons.Default.Devices,
                description = "Mostra a diferença entre o valor base e os valores " +
                        "escalonados automaticamente conforme regras e dimensões."
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
 * [EN] Demo card with title, icon, description, and content.
 * 
 * [PT] Card de demonstração com título, ícone, descrição e conteúdo.
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

/**
 * [EN] A preview for the AppDimens SSP example screen.
 * 
 * [PT] Uma pré-visualização para a tela de exemplo do AppDimens SSP.
 */
@Preview(showBackground = true)
@Composable
fun PreviewAppDimensSspExample() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface {
            AppDimensSspExampleScreen()
        }
    }
}
