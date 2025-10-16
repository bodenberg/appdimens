/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
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

/**
 * [EN] Main activity demonstrating various features of the AppDimens library.
 *
 * [PT] Atividade principal que demonstra vários recursos da biblioteca AppDimens.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityDynamicDataBindingBinding binding;

    /**
     * [EN] Called when the activity is first created.
     *
     * [PT] Chamado quando a atividade é criada pela primeira vez.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // [EN] Data Binding Configuration
        // [PT] Configuração do Data Binding
        binding = ActivityDynamicDataBindingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ====================================================================
        // [EN] 1. DYNAMIC USAGE WITH DATA BINDING (Focusing on the value '48')
        // [PT] 1. USO DINÂMICO COM DATA BINDING (Dando foco ao valor '48')
        // ====================================================================
        float dimenValue = 48f;
        
        // [EN] Passes the value 48 to the 'dimenValue' variable in the XML, activating the Binding Adapter.
        // [PT] Passa o valor 48 para a variável 'dimenValue' no XML, ativando o Binding Adapter.
        binding.setDimenValue(dimenValue);

        Log.d("AppDimensExample", "1. Dinâmico (DB) - Valor inicial: " + dimenValue + "dp");

        // [EN] The Binding Adapter (DimensBindingAdapters.java) will perform the dynamic conversion to PX.
        // [PT] O Binding Adapter (DimensBindingAdapters.java) fará a conversão dinâmica para PX.

        // [EN] Ensures that the Data Binding is executed immediately (optional).
        // [PT] Garante que o Data Binding seja executado imediatamente (opcional).
        binding.executePendingBindings();

        // ====================================================================
        // [EN] --- Direct Java Usage Examples (Non-Data Binding) ---
        // [PT] --- Exemplos de Uso Direto em Java (Não-Data Binding) ---
        // ====================================================================

        // [EN] 2. Fixed (Non-Dynamic) Usage
        // [PT] 2. Uso Fixo (Não Dinâmico)
        demonstrateFixedUsage(binding.fixedView);

        // [EN] 3. Dynamic Percentage Usage
        // [PT] 3. Uso Percentual Dinâmico
        demonstratePercentageUsage(binding.percentageView);

        // [EN] 4. Physical Units Usage (MM)
        // [PT] 4. Uso de Unidades Físicas (MM)
        demonstratePhysicalUnitUsage(binding.physicalUnitView);
    }

    /**
     * [EN] 2. Demonstrates the use of AppDimensFixed (Fixed DP) to maintain the dimension
     * WITHOUT the mathematical scale adjustment.
     *
     * [PT] 2. Demonstra o uso de AppDimensFixed (DP Fixo) para manter a dimensão
     * SEM o ajuste matemático de escala.
     */
    private void demonstrateFixedUsage(View view) {
        float dpValue = 64f;

        // [EN] Converts Fixed Dp (64.dp) to Pixel (PX).
        // [PT] Converte o Dp Fixo (64.dp) para Pixel (PX).
        float fixedPx = new AppDimensFixed(dpValue, false).toPx(getResources());

        Log.d("AppDimensExample", "2. Fixo: " + dpValue + "dp -> " + fixedPx + "px");

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) fixedPx;
        layoutParams.height = (int) fixedPx;
        view.setLayoutParams(layoutParams);
    }

    /**
     * [EN] 3. Demonstrates the use of dynamic percentage calculation (80% of the screen).
     * Note: The base dimension (LOWEST or HIGHEST) can be changed.
     *
     * [PT] 3. Demonstra o uso de cálculo percentual dinâmico (80% da tela).
     * Nota: A dimensão base (LOWEST ou HIGHEST) pode ser alterada.
     */
    private void demonstratePercentageUsage(View view) {
        // [EN] 80%
        // [PT] 80%
        float percentage = 0.80f; 

        // [EN] Calculates 80% of the LOWEST dimension (smaller screen dimension, W or H) in PX.
        // [PT] Calcula 80% da dimensão LOWEST (menor dimensão da tela, L ou A) em PX.
        float percentagePx = AppDimens.INSTANCE.dynamicPercentagePx(
                percentage,
                ScreenType.LOWEST,
                getResources()
        );

        Log.d("AppDimensExample", "3. Percentual: " + (percentage * 100) + "% de "
                + ScreenType.LOWEST + " -> " + percentagePx + "px");

        // [EN] Sets the View width to 80% of the screen in PX.
        // [PT] Define a largura da View como 80% da tela em PX.
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) percentagePx;
        view.setLayoutParams(layoutParams);
    }

    /**
     * [EN] 4. Demonstrates the use of physical unit conversion (Millimeters).
     *
     * [PT] 4. Demonstra o uso de conversão de unidades físicas (Milímetros).
     */
    private void demonstratePhysicalUnitUsage(View view) {
        // [EN] 5 millimeters
        // [PT] 5 milímetros
        float mmValue = 5.0f; 

        // [EN] Converts 5mm to Pixel (PX).
        // [PT] Converte 5mm para Pixel (PX).
        float mmInPx = AppDimensPhysicalUnits.INSTANCE.toMm(mmValue, getResources());

        Log.d("AppDimensExample", "4. Física: " + mmValue + "mm -> " + mmInPx + "px");

        // [EN] Sets the View top margin using the MM to PX conversion.
        // [PT] Define a margem superior da View usando a conversão de MM para PX.
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = (int) mmInPx;

        // [EN] Updates the TextView to show the conversion result.
        // [PT] Atualiza o TextView para mostrar o resultado da conversão.
        binding.tvPhysicalUnit.setText(
                "4. Unidade Física (MM) - 5mm de margem (~" + (int) mmInPx + "px)"
        );

        view.setLayoutParams(layoutParams);
    }
}