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
// Importa as extensões de dimensões customizadas
import com.appdimens.sdps.* // Importa as extensões sdp, hdp, wdp, scaledDp

class ScaledSdpsExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SdpDemoScreen()
        }
    }
}

/**
 * Define o layout principal da aplicação de demonstração.
 * Ela usa `MaterialTheme` e `Surface` para configurar o tema e o plano de fundo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SdpDemoScreen() {
    // Aplica um tema Material 3 (esquema de cores claro por padrão).
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Organiza os componentes verticalmente.
            Column(// Permite rolagem vertical.
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    // Preenchimento de 16.sdp: adapta o padding com base na menor largura.
                    .padding(16.sdp),
                // Espaçamento entre os itens de 20.sdp.
                verticalArrangement = Arrangement.spacedBy(20.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título principal
                Text(
                    "AppDimens SDP Demo",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )

                // Subtítulo descritivo
                Text(
                    "Demonstração de uso das extensões .sdp, .hdp, .wdp e scaledDp()",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                // --- Exemplos de Dimensões Adaptativas ---

                // Exemplo de .sdp (Smallest Width)
                ExampleCard(
                    title = "Exemplo de .sdp (Smallest Width)",
                    description = "16.sdp ajusta-se proporcionalmente à menor largura da tela. O box tem 60.sdp.",
                    boxSize = 60.sdp // O tamanho do box se adapta à 'smallest width'.
                )

                // Exemplo de .hdp (Height)
                ExampleCard(
                    title = "Exemplo de .hdp (Height)",
                    description = "80.hdp adapta-se conforme a altura da tela. Mude a orientação para ver a diferença.",
                    boxSize = 80.hdp // O tamanho do box se adapta à altura total da tela.
                )

                // Exemplo de .wdp (Width)
                ExampleCard(
                    title = "Exemplo de .wdp (Width)",
                    description = "120.wdp depende da largura total do dispositivo. O box tem 120.wdp.",
                    boxSize = 120.wdp // O tamanho do box se adapta à largura total da tela.
                )

                // Exemplo de Dimensão Escalada (Scaled Dp)
                ScaledExampleCard()
            }
        }
    }
}

/**
 * Componente `@Composable` genérico para exibir o exemplo de uma dimensão.
 *
 * @param title O título do exemplo (ex: ".sdp").
 * @param description A descrição da funcionalidade.
 * @param boxSize O valor Dp (adaptativo) a ser usado para o tamanho do Box.
 */
@Composable
fun ExampleCard(title: String, description: String, boxSize: Dp) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)), // Cor de fundo do Card
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            // Padding interno do card usando 16.sdp para ser responsivo.
            modifier = Modifier.padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp) // Espaçamento interno responsivo.
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodyMedium)

            // Box de Demonstração
            Box(
                modifier = Modifier
                    // Define o tamanho (width e height) do box usando o valor adaptativo.
                    .size(boxSize)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // Exibe o valor do Dp resolvido (para fins de debug/visualização)
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
 * Componente `@Composable` para demonstrar o uso da função `scaledDp()`.
 * Esta função permite definir um valor base e overrides específicos para diferentes
 * qualificadores de tela (ex: TV, largura mínima sw600dp).
 */
@Composable
fun ScaledExampleCard() {
    // 1. Define o valor base (100) e inicia a cadeia de configuração.
    val dynamicDp = 100.scaledDp()
        // 2. Override para o modo de UI: Se for uma TV, use 200dp.
        .screen(UiModeType.TELEVISION, 200)
        // 3. Override para o qualificador de Dp: Se a largura mínima for >= 600dp, use 150dp.
        .screen(DpQualifier.SMALL_WIDTH, 600, 150)
        // 4. Override para o modo de UI (NORMAL é o padrão, mas é bom para clareza): usa 100dp.
        .screen(UiModeType.NORMAL, 100)
        // 5. Finaliza a cadeia e resolve o Dp, adaptando-o com base no qualificador .sdp.
        .sdp // Usa a adaptação 'smallest width' no valor final.

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // Cor de fundo do Card
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp)
        ) {
            Text(
                "Exemplo de uso de scaledDp()",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                "O tamanho muda dinamicamente conforme o tipo de dispositivo e largura mínima. O valor atual é: ${dynamicDp.value.toInt()}dp",
                style = MaterialTheme.typography.bodyMedium
            )

            // Box de Demonstração
            Box(
                modifier = Modifier
                    // O Box usa o valor dinamicamente resolvido.
                    .size(dynamicDp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // Exibe o valor do Dp resolvido.
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
