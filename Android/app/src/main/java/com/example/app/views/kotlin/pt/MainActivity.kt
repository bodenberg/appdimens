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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDynamicDataBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Configuração do Data Binding
        binding = ActivityDynamicDataBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ====================================================================
        // 1. USO DINÂMICO COM DATA BINDING (Dando foco ao valor '48')
        // ====================================================================
        val dimenValue = 48f
        // Passa o valor 48 para a variável 'dimenValue' no XML, ativando o Binding Adapter
        binding.dimenValue = dimenValue

        Log.d("AppDimensExample", "1. Dinâmico (DB) - Valor inicial: ${dimenValue}dp")
        // O Binding Adapter (DimensBindingAdapters.kt) fará a conversão dinâmica para PX

        // Garante que o Data Binding seja executado imediatamente (opcional)
        binding.executePendingBindings()


        // ====================================================================
        // --- Exemplos de Uso Direto em Kotlin (Não-Data Binding) ---
        // ====================================================================

        // 2. Uso Fixo (Non-Dynamic)
        demonstrateFixedUsage(binding.fixedView)

        // 3. Uso Percentual Dinâmico
        demonstratePercentageUsage(binding.percentageView)

        // 4. Uso de Unidades Físicas (MM)
        demonstratePhysicalUnitUsage(binding.physicalUnitView)
    }

    /**
     * 2. Demonstra o uso de AppDimensFixed (DP Fixo) para manter a dimensão
     * SEM o ajuste matemático de escala.
     */
    private fun demonstrateFixedUsage(view: View) {
        val dpValue = 64f

        // Converte o Dp Fixo (64.dp) para Pixel (PX)
        val fixedPx = AppDimensFixed(dpValue).toPx(resources)

        Log.d("AppDimensExample", "2. Fixo: ${dpValue}dp -> ${fixedPx}px")

        val layoutParams = view.layoutParams
        layoutParams.width = fixedPx.toInt()
        layoutParams.height = fixedPx.toInt()
        view.layoutParams = layoutParams
    }

    /**
     * 3. Demonstra o uso de cálculo percentual dinâmico (80% da tela).
     * Nota: A dimensão base (LOWEST ou HIGHEST) pode ser alterada.
     */
    private fun demonstratePercentageUsage(view: View) {
        val percentage = 0.80f // 80%

        // Calcula 80% da dimensão LOWEST (menor dimensão da tela, W ou H) em PX
        val percentagePx = AppDimens.dynamicPercentagePx(
            percentage = percentage,
            type = ScreenType.LOWEST,
            resources = resources
        )

        Log.d("AppDimensExample", "3. Percentual: ${percentage * 100}% de ${ScreenType.LOWEST} -> ${percentagePx}px")

        // Define a largura da View como 80% da tela em PX
        val layoutParams = view.layoutParams
        layoutParams.width = percentagePx.toInt()
        view.layoutParams = layoutParams
    }

    /**
     * 4. Demonstra o uso de conversão de unidades físicas (Milímetros).
     */
    private fun demonstratePhysicalUnitUsage(view: View) {
        val mmValue = 5.0f // 5 milímetros

        // Converte 5mm para Pixel (PX)
        val mmInPx = AppDimensPhysicalUnits.toMm(mmValue, resources)

        Log.d("AppDimensExample", "4. Física: ${mmValue}mm -> ${mmInPx}px")

        // Define a margem superior da View usando a conversão de MM para PX
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = mmInPx.toInt()

        // Atualiza o TextView para mostrar o resultado da conversão
        binding.tvPhysicalUnit.text =
            "4. Unidade Física (MM) - 5mm de margem (~${mmInPx.toInt()}px)"

        view.layoutParams = layoutParams
    }
}