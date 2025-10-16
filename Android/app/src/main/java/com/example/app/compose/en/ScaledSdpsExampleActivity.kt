/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
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
// [EN] Imports custom dimension extensions
// [PT] Importa extensões de dimensão personalizadas
import com.appdimens.sdps.compose.hdp
import com.appdimens.sdps.compose.scaledDp
import com.appdimens.sdps.compose.sdp
import com.appdimens.sdps.compose.wdp

// [PT] Importa extensões sdp, hdp, wdp, scaledDp

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
 * [PT] Define o layout principal para o aplicativo de demonstração.
 * Usa `MaterialTheme` e `Surface` para configurar o tema e o plano de fundo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SdpDemoScreen() {
    // [EN] Applies a Material 3 theme (light color scheme by default).
    // [PT] Aplica um tema do Material 3 (esquema de cores claro por padrão).
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // [EN] Organizes components vertically.
            // [PT] Organiza os componentes verticalmente.
            Column(// [EN] Allows vertical scrolling.
                   // [PT] Permite a rolagem vertical.
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    // [EN] 16.sdp padding: adapts padding based on the smallest width.
                    // [PT] Preenchimento de 16.sdp: adapta o preenchimento com base na menor largura.
                    .padding(16.sdp),
                // [EN] Spacing between items of 20.sdp.
                // [PT] Espaçamento entre itens de 20.sdp.
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
                    "Demonstration of using the .sdp, .hdp, .wdp, and scaledDp() extensions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                // [EN] --- Adaptive Dimension Examples ---
                // [PT] --- Exemplos de Dimensão Adaptável ---

                // [EN] .sdp Example (Smallest Width)
                // [PT] Exemplo de .sdp (Menor Largura)
                ExampleCard(
                    title = ".sdp Example (Smallest Width)",
                    description = "16.sdp adjusts proportionally to the screen's smallest width. The box is 60.sdp.",
                    boxSize = 60.sdp // [EN] The box size adapts to the 'smallest width'.
                                     // [PT] O tamanho da caixa se adapta à 'menor largura'.
                )

                // [EN] .hdp Example (Height)
                // [PT] Exemplo de .hdp (Altura)
                ExampleCard(
                    title = ".hdp Example (Height)",
                    description = "80.hdp adapts according to the screen height. Change orientation to see the difference.",
                    boxSize = 80.hdp // [EN] The box size adapts to the total screen height.
                                     // [PT] O tamanho da caixa se adapta à altura total da tela.
                )

                // [EN] .wdp Example (Width)
                // [PT] Exemplo de .wdp (Largura)
                ExampleCard(
                    title = ".wdp Example (Width)",
                    description = "120.wdp depends on the device's total width. The box is 120.wdp.",
                    boxSize = 120.wdp // [EN] The box size adapts to the total screen width.
                                      // [PT] O tamanho da caixa se adapta à largura total da tela.
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
 * [PT] Componente `@Composable` genérico para exibir um exemplo de dimensão.
 * @param title [EN] The example title (e.g., ".sdp").
 *              [PT] O título do exemplo (por exemplo, ".sdp").
 * @param description [EN] The functionality description.
 *                    [PT] A descrição da funcionalidade.
 * @param boxSize [EN] The adaptive Dp value to be used for the Box size.
 *                [PT] O valor de Dp adaptável a ser usado para o tamanho da Caixa.
 */
@Composable
fun ExampleCard(title: String, description: String, boxSize: Dp) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)), // [EN] Card background color
                                                                              // [PT] Cor de fundo do cartão
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            // [EN] Internal card padding using 16.sdp for responsiveness.
            // [PT] Preenchimento interno do cartão usando 16.sdp para responsividade.
            modifier = Modifier.padding(16.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp) // [EN] Responsive internal spacing.
                                                               // [PT] Espaçamento interno responsivo.
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodyMedium)

            // [EN] Demonstration Box
            // [PT] Caixa de Demonstração
            Box(
                modifier = Modifier
                    // [EN] Sets the size (width and height) of the box using the adaptive value.
                    // [PT] Define o tamanho (largura e altura) da caixa usando o valor adaptável.
                    .size(boxSize)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // [EN] Displays the resolved Dp value (for debug/visualization purposes)
                // [PT] Exibe o valor de Dp resolvido (para fins de depuração/visualização)
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
 * Esta função permite definir um valor base e substituições específicas para diferentes
 * qualificadores de tela (por exemplo, TV, menor largura sw600dp).
 */
@Composable
fun ScaledExampleCard() {
    // [EN] 1. Defines the base value (100) and starts the configuration chain.
    // [PT] 1. Define o valor base (100) e inicia a cadeia de configuração.
    val dynamicDp = 100.scaledDp()
        // [EN] 2. Override for UI mode: If it's a TV, use 200dp.
        // [PT] 2. Substituição para o modo de IU: Se for uma TV, use 200dp.
        .screen(UiModeType.TELEVISION, 200)
        // [EN] 3. Override for Dp qualifier: If the smallest width is >= 600dp, use 150dp.
        // [PT] 3. Substituição para o qualificador de Dp: Se a menor largura for >= 600dp, use 150dp.
        .screen(DpQualifier.SMALL_WIDTH, 600, 150)
        // [EN] 4. Override for UI mode (NORMAL is the default, but good for clarity): uses 100dp.
        // [PT] 4. Substituição para o modo de IU (NORMAL é o padrão, mas bom para clareza): usa 100dp.
        .screen(UiModeType.NORMAL, 100)
        // [EN] 5. Finalizes the chain and resolves the Dp, adapting it based on the .sdp qualifier.
        // [PT] 5. Finaliza a cadeia e resolve o Dp, adaptando-o com base no qualificador .sdp.
        .sdp // [EN] Uses the 'smallest width' adaptation on the final value.
             // [PT] Usa a adaptação de 'menor largura' no valor final.

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // [EN] Card background color
                                                                              // [PT] Cor de fundo do cartão
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

            // [EN] Demonstration Box
            // [PT] Caixa de Demonstração
            Box(
                modifier = Modifier
                    // [EN] The Box uses the dynamically resolved value.
                    // [PT] A Caixa usa o valor resolvido dinamicamente.
                    .size(dynamicDp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ) {
                // [EN] Displays the resolved Dp value.
                // [PT] Exibe o valor de Dp resolvido.
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
 * [PT] Uma visualização para a tela de exemplo do AppDimens SDP.
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