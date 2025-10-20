/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.views.kotlin.pt

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.appdimens.dynamic.code.AppDimens
import com.appdimens.dynamic.code.AppDimensFixed
import com.appdimens.dynamic.code.AppDimensPhysicalUnits
import com.appdimens.library.ScreenType
import com.example.app.databinding.ActivityDynamicDataBindingBinding

/**
 * [EN] Main activity demonstrating various features of the AppDimens library.
 *
 * [PT] Atividade principal que demonstra vários recursos da biblioteca AppDimens.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDynamicDataBindingBinding

    /**
     * [EN] Called when the activity is first created.
     *
     * [PT] Chamado quando a atividade é criada pela primeira vez.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [EN] Data Binding Setup
        // [PT] Configuração do Data Binding
        binding = ActivityDynamicDataBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ====================================================================
        // [EN] 1. DYNAMIC USAGE WITH DATA BINDING (Focusing on the value '48')
        // [PT] 1. USO DINÂMICO COM DATA BINDING (Focando no valor '48')
        // ====================================================================
        val dimenValue = 48f

        // [EN] Passes the value 48 to the 'dimenValue' variable in the XML, activating the Binding Adapter.
        // [PT] Passa o valor 48 para a variável 'dimenValue' no XML, ativando o Binding Adapter.
        binding.dimenValue = dimenValue

        Log.d("AppDimensExample", "1. Dinâmico (DB) - Valor inicial: ${dimenValue}dp")

        // [EN] The Binding Adapter (DimensBindingAdapters.kt) will perform the dynamic conversion to PX.
        // [PT] O Binding Adapter (DimensBindingAdapters.kt) realizará a conversão dinâmica para PX.

        // [EN] Ensures Data Binding is executed immediately (optional).
        // [PT] Garante que o Data Binding seja executado imediatamente (opcional).
        binding.executePendingBindings()


        // ====================================================================
        // [EN] --- Examples of Direct Kotlin Usage (Non-Data Binding) ---
        // [PT] --- Exemplos de Uso Direto em Kotlin (Sem Data Binding) ---
        // ====================================================================

        // [EN] 2. Fixed (Non-Dynamic) Usage
        // [PT] 2. Uso Fixo (Não Dinâmico)
        demonstrateFixedUsage(binding.fixedView)

        // [EN] 3. Dynamic Percentage Usage
        // [PT] 3. Uso de Porcentagem Dinâmica
        demonstratePercentageUsage(binding.percentageView)

        // [EN] 4. Physical Unit (MM) Usage
        // [PT] 4. Uso de Unidade Física (MM)
        demonstratePhysicalUnitUsage(binding.physicalUnitView)
    }

    /**
     * [EN] 2. Demonstrates the usage of AppDimensFixed (Fixed DP) to maintain the dimension
     * WITHOUT the mathematical scaling adjustment.
     *
     * [PT] 2. Demonstra o uso do AppDimensFixed (DP Fixo) para manter a dimensão
     * SEM o ajuste de escala matemático.
     */
    private fun demonstrateFixedUsage(view: View) {
        val dpValue = 64f

        // [EN] Converts Fixed Dp (64.dp) to Pixel (PX).
        // [PT] Converte Dp Fixo (64.dp) para Pixel (PX).
        val fixedPx = AppDimensFixed(dpValue).toPx(resources)

        Log.d("AppDimensExample", "2. Fixo: ${dpValue}dp -> ${fixedPx}px")

        val layoutParams = view.layoutParams
        layoutParams.width = fixedPx.toInt()
        layoutParams.height = fixedPx.toInt()
        view.layoutParams = layoutParams
    }

    /**
     * [EN] 3. Demonstrates the usage of dynamic percentage calculation (80% of the screen).
     * Note: The base dimension (LOWEST or HIGHEST) can be changed.
     *
     * [PT] 3. Demonstra o uso do cálculo de porcentagem dinâmica (80% da tela).
     * Nota: A dimensão base (LOWEST ou HIGHEST) pode ser alterada.
     */
    private fun demonstratePercentageUsage(view: View) {
        // [EN] 80%
        // [PT] 80%
        val percentage = 0.80f

        // [EN] Calculates 80% of the LOWEST dimension (smallest screen dimension, W or H) in PX.
        // [PT] Calcula 80% da menor dimensão (menor dimensão da tela, L ou A) em PX.
        val percentagePx = AppDimens.dynamicPercentagePx(
            percentage = percentage,
            type = ScreenType.LOWEST,
            resources = resources
        )

        Log.d("AppDimensExample", "3. Percentual: ${percentage * 100}% de ${ScreenType.LOWEST} -> ${percentagePx}px")

        // [EN] Sets the View's width to 80% of the screen in PX.
        // [PT] Define a largura da View como 80% da tela em PX.
        val layoutParams = view.layoutParams
        layoutParams.width = percentagePx.toInt()
        view.layoutParams = layoutParams
    }

    /**
     * [EN] 4. Demonstrates the usage of physical unit conversion (Millimeters).
     *
     * [PT] 4. Demonstra o uso da conversão de unidades físicas (Milímetros).
     */
    private fun demonstratePhysicalUnitUsage(view: View) {
        // [EN] 5 millimeters
        // [PT] 5 milímetros
        val mmValue = 5.0f

        // [EN] Converts 5mm to Pixel (PX).
        // [PT] Converte 5mm para Pixel (PX).
        val mmInPx = AppDimensPhysicalUnits.toPxFromMm(mmValue, resources)

        Log.d("AppDimensExample", "4. Física: ${mmValue}mm -> ${mmInPx}px")

        // [EN] Sets the View's top margin using the MM to PX conversion.
        // [PT] Define a margem superior da View usando a conversão de MM para PX.
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = mmInPx.toInt()

        // [EN] Updates the TextView to show the conversion result.
        // [PT] Atualiza o TextView para mostrar o resultado da conversão.
        binding.tvPhysicalUnit.text =
            "4. Unidade Física (MM) - 5mm de margem (~${mmInPx.toInt()}px)"

        view.layoutParams = layoutParams
    }
}