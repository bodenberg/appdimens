/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
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
// [EN] Imports custom dimension extensions
// [PT] Importa as extensões de dimensões customizadas
import com.appdimens.sdps.compose.hdp
import com.appdimens.sdps.compose.scaledDp
import com.appdimens.sdps.compose.sdp
import com.appdimens.sdps.compose.wdp

// [PT] Importa as extensões sdp, hdp, wdp, scaledDp

/**
 * [EN] An activity that demonstrates the use of scaled SDPs with AppDimens in Jetpack Compose.
 * 
 * [PT] Uma atividade que demonstra o uso de SDPs escalados com AppDimens no Jetpack Compose.
 */
class ScaledSdpsExampleActivity : ComponentActivity() {
    /**
     * [EN] Called when the activity is first created.
     * 
     * [PT] Chamado quando a atividade é criada pela primeira vez.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SdpDemoScreen()
        }
    }
}

/**
 * [EN] Defines the main layout for the demo application.
 * It uses `MaterialTheme` and `Surface` to set up the theme and background.
 * 
 * [PT] Define o layout principal da aplicação de demonstração.
 * Ela usa `MaterialTheme` e `Surface` para configurar o tema e o plano de fundo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SdpDemoScreen() {
    // [EN] Applies a Material 3 theme (light color scheme by default).
    // [PT] Aplica um tema Material 3 (esquema de cores claro por padrão).
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // [EN] Organizes the components vertically.
            // [PT] Organiza os componentes verticalmente.
            Column(// [EN] Allows vertical scrolling.
                   // [PT] Permite rolagem vertical.
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    // [EN] 16.sdp padding: adapts the padding based on the smallest width.
                    // [PT] Preenchimento de 16.sdp: adapta o padding com base na menor largura.
                    .padding(16.sdp),
                // [EN] Spacing between items of 20.sdp.
                // [PT] Espaçamento entre os itens de 20.sdp.
                verticalArrangement = Arrangement.spacedBy(20.sdp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // [EN] Main title
                // [PT] Título principal
                Text(
                    "AppDimens SDP Demo",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center
                )

                // [EN] Descriptive subtitle
                // [PT] Subtítulo descritivo
                Text(
                    "Demonstração de uso das extensões .sdp, .hdp, .wdp e scaledDp()",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                // [EN] --- Adaptive Dimension Examples ---
                // [PT] --- Exemplos de Dimensões Adaptativas ---

                // [EN] .sdp Example (Smallest Width)
                // [PT] Exemplo de .sdp (Menor Largura)
                ExampleCard(
                    title = "Exemplo de .sdp (Smallest Width)",
                    description = "16.sdp ajusta-se proporcionalmente à menor largura da tela. O box tem 60.sdp.",
                    boxSize = 60.sdp // [EN] The box size adapts to the 'smallest width'.
                                     // [PT] O tamanho do box se adapta à 'menor largura'.
                )

                // [EN] .hdp Example (Height)
                // [PT] Exemplo de .hdp (Altura)
                ExampleCard(
                    title = "Exemplo de .hdp (Height)",
                    description = "80.hdp adapta-se conforme a altura da tela. Mude a orientação para ver a diferença.",
                    boxSize = 80.hdp // [EN] The box size adapts to the total screen height.
                                     // [PT] O tamanho do box se adapta à altura total da tela.
                )

                // [EN] .wdp Example (Width)
                // [PT] Exemplo de .wdp (Largura)
                ExampleCard(
                    title = "Exemplo de .wdp (Width)",
                    description = "120.wdp depende da largura total do dispositivo. O box tem 120.wdp.",
                    boxSize = 120.wdp // [EN] The box size adapts to the total screen width.
                                      // [PT] O tamanho do box se adapta à largura total da tela.
                )

                // [EN] Scaled Dimension Example (Scaled Dp)
                // [PT] Exemplo de Dimensão Escalada (Dp Escalado)
                ScaledExampleCard()
            }
        }
    }
}

/**
 * [EN] Generic `@Composable` component to display a dimension example.
 *
 * [PT] Componente `@Composable` genérico para exibir o exemplo de uma dimensão.
 * @param title [EN] The example title (e.g., ".sdp").
 *              [PT] O título do exemplo (ex: ".sdp").
 * @param description [EN] The functionality description.
 *                    [PT] A descrição da funcionalidade.
 * @param boxSize [EN] The adaptive Dp value to be used for the Box size.
 *                [PT] O valor Dp (adaptativo) a ser usado para o tamanho do Box.
 */
@Composable
fun ExampleCard(title: String, description: String, boxSize: Dp) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)), // [EN] Card background color
                                                                              // [PT] Cor de fundo do Card
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            // [EN] Internal card padding using 16.sdp for responsiveness.
            // [PT] Padding interno do card usando 16.sdp para ser responsivo.
            modifier = Modifier.padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp) // [EN] Responsive internal spacing.
                                                               // [PT] Espaçamento interno responsivo.
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodyMedium)

            // [EN] Demonstration Box
            // [PT] Box de Demonstração
            Box(
                modifier = Modifier
                    // [EN] Sets the size (width and height) of the box using the adaptive value.
                    // [PT] Define o tamanho (width e height) do box usando o valor adaptativo.
                    .size(boxSize)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // [EN] Displays the resolved Dp value (for debug/visualization purposes)
                // [PT] Exibe o valor do Dp resolvido (para fins de debug/visualização)
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
 * [EN] `@Composable` component to demonstrate the use of the `scaledDp()` function.
 * This function allows defining a base value and specific overrides for different
 * screen qualifiers (e.g., TV, smallest width sw600dp).
 * 
 * [PT] Componente `@Composable` para demonstrar o uso da função `scaledDp()`.
 * Esta função permite definir um valor base e overrides específicos para diferentes
 * qualificadores de tela (ex: TV, largura mínima sw600dp).
 */
@Composable
fun ScaledExampleCard() {
    // [EN] 1. Defines the base value (100) and starts the configuration chain.
    // [PT] 1. Define o valor base (100) e inicia a cadeia de configuração.
    val dynamicDp = 100.scaledDp()
        // [EN] 2. Override for UI mode: If it's a TV, use 200dp.
        // [PT] 2. Override para o modo de UI: Se for uma TV, use 200dp.
        .screen(UiModeType.TELEVISION, 200)
        // [EN] 3. Override for Dp qualifier: If the smallest width is >= 600dp, use 150dp.
        // [PT] 3. Override para o qualificador de Dp: Se a largura mínima for >= 600dp, use 150dp.
        .screen(DpQualifier.SMALL_WIDTH, 600, 150)
        // [EN] 4. Override for UI mode (NORMAL is the default, but good for clarity): uses 100dp.
        // [PT] 4. Override para o modo de UI (NORMAL é o padrão, mas é bom para clareza): usa 100dp.
        .screen(UiModeType.NORMAL, 100)
        // [EN] 5. Finalizes the chain and resolves the Dp, adapting it based on the .sdp qualifier.
        // [PT] 5. Finaliza a cadeia e resolve o Dp, adaptando-o com base no qualificador .sdp.
        .sdp // [EN] Uses the 'smallest width' adaptation on the final value.
             // [PT] Usa a adaptação 'smallest width' no valor final.

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // [EN] Card background color
                                                                              // [PT] Cor de fundo do Card
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

            // [EN] Demonstration Box
            // [PT] Box de Demonstração
            Box(
                modifier = Modifier
                    // [EN] The Box uses the dynamically resolved value.
                    // [PT] O Box usa o valor dinamicamente resolvido.
                    .size(dynamicDp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // [EN] Displays the resolved Dp value.
                // [PT] Exibe o valor do Dp resolvido.
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

/**
 * [EN] A preview for the AppDimens SDP example screen.
 * 
 * [PT] Uma pré-visualização para a tela de exemplo do AppDimens SDP.
 */
@Preview(showBackground = true)
@Composable
fun PreviewAppDimensSdpExample() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface {
            SdpDemoScreen()
        }
    }
}
