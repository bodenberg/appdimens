package com.example.app.views.java.pt;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import com.appdimens.dynamic.code.AppDimens;
import com.appdimens.dynamic.code.AppDimensFixed;
import com.appdimens.dynamic.code.AppDimensPhysicalUnits;
import com.appdimens.library.ScreenType;
import com.example.app.databinding.ActivityDynamicDataBindingBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityDynamicDataBindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Configuração do Data Binding
        binding = ActivityDynamicDataBindingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ====================================================================
        // 1. USO DINÂMICO COM DATA BINDING (Dando foco ao valor '48')
        // ====================================================================
        float dimenValue = 48f;
        // Passa o valor 48 para a variável 'dimenValue' no XML, ativando o Binding Adapter
        binding.setDimenValue(dimenValue);

        Log.d("AppDimensExample", "1. Dinâmico (DB) - Valor inicial: " + dimenValue + "dp");
        // O Binding Adapter (DimensBindingAdapters.java) fará a conversão dinâmica para PX

        // Garante que o Data Binding seja executado imediatamente (opcional)
        binding.executePendingBindings();

        // ====================================================================
        // --- Exemplos de Uso Direto em Java (Não-Data Binding) ---
        // ====================================================================

        // 2. Uso Fixo (Non-Dynamic)
        demonstrateFixedUsage(binding.fixedView);

        // 3. Uso Percentual Dinâmico
        demonstratePercentageUsage(binding.percentageView);

        // 4. Uso de Unidades Físicas (MM)
        demonstratePhysicalUnitUsage(binding.physicalUnitView);
    }

    /**
     * 2. Demonstra o uso de AppDimensFixed (DP Fixo) para manter a dimensão
     * SEM o ajuste matemático de escala.
     */
    private void demonstrateFixedUsage(View view) {
        float dpValue = 64f;

        // Converte o Dp Fixo (64.dp) para Pixel (PX)
        float fixedPx = new AppDimensFixed(dpValue, false).toPx(getResources());

        Log.d("AppDimensExample", "2. Fixo: " + dpValue + "dp -> " + fixedPx + "px");

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) fixedPx;
        layoutParams.height = (int) fixedPx;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 3. Demonstra o uso de cálculo percentual dinâmico (80% da tela).
     * Nota: A dimensão base (LOWEST ou HIGHEST) pode ser alterada.
     */
    private void demonstratePercentageUsage(View view) {
        float percentage = 0.80f; // 80%

        // Calcula 80% da dimensão LOWEST (menor dimensão da tela, W ou H) em PX
        float percentagePx = AppDimens.INSTANCE.dynamicPercentagePx(
                percentage,
                ScreenType.LOWEST,
                getResources()
        );

        Log.d("AppDimensExample", "3. Percentual: " + (percentage * 100) + "% de "
                + ScreenType.LOWEST + " -> " + percentagePx + "px");

        // Define a largura da View como 80% da tela em PX
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) percentagePx;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 4. Demonstra o uso de conversão de unidades físicas (Milímetros).
     */
    private void demonstratePhysicalUnitUsage(View view) {
        float mmValue = 5.0f; // 5 milímetros

        // Converte 5mm para Pixel (PX)
        float mmInPx = AppDimensPhysicalUnits.INSTANCE.toMm(mmValue, getResources());

        Log.d("AppDimensExample", "4. Física: " + mmValue + "mm -> " + mmInPx + "px");

        // Define a margem superior da View usando a conversão de MM para PX
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = (int) mmInPx;

        // Atualiza o TextView para mostrar o resultado da conversão
        binding.tvPhysicalUnit.setText(
                "4. Unidade Física (MM) - 5mm de margem (~" + (int) mmInPx + "px)"
        );

        view.setLayoutParams(layoutParams);
    }
}