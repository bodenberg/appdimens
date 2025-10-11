package com.example.app.views.java.en;

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

        // 1. Data Binding Configuration
        binding = ActivityDynamicDataBindingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ====================================================================
        // 1. DYNAMIC USAGE WITH DATA BINDING (Focusing on the value '48')
        // ====================================================================
        float dimenValue = 48f;
        // Passes the value 48 to the 'dimenValue' variable in the XML, activating the Binding Adapter
        binding.setDimenValue(dimenValue);

        Log.d("AppDimensExample", "1. Dynamic (DB) - Initial value: " + dimenValue + "dp");
        // The Binding Adapter (DimensBindingAdapters.java) will perform the dynamic conversion to PX

        // Ensures that the Data Binding is executed immediately (optional)
        binding.executePendingBindings();

        // ====================================================================
        // --- Direct Java Usage Examples (Non-Data Binding) ---
        // ====================================================================

        // 2. Fixed (Non-Dynamic) Usage
        demonstrateFixedUsage(binding.fixedView);

        // 3. Dynamic Percentage Usage
        demonstratePercentageUsage(binding.percentageView);

        // 4. Physical Units Usage (MM)
        demonstratePhysicalUnitUsage(binding.physicalUnitView);
    }

    /**
     * 2. Demonstrates the use of AppDimensFixed (Fixed DP) to maintain the dimension
     * WITHOUT the mathematical scale adjustment.
     */
    private void demonstrateFixedUsage(View view) {
        float dpValue = 64f;

        // Converts Fixed Dp (64.dp) to Pixel (PX)
        float fixedPx = new AppDimensFixed(dpValue, false).toPx(getResources());

        Log.d("AppDimensExample", "2. Fixed: " + dpValue + "dp -> " + fixedPx + "px");

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) fixedPx;
        layoutParams.height = (int) fixedPx;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 3. Demonstrates the use of dynamic percentage calculation (80% of the screen).
     * Note: The base dimension (LOWEST or HIGHEST) can be changed.
     */
    private void demonstratePercentageUsage(View view) {
        float percentage = 0.80f; // 80%

        // Calculates 80% of the LOWEST dimension (smaller screen dimension, W or H) in PX
        float percentagePx = AppDimens.INSTANCE.dynamicPercentagePx(
                percentage,
                ScreenType.LOWEST,
                getResources()
        );

        Log.d("AppDimensExample", "3. Percentage: " + (percentage * 100) + "% of "
                + ScreenType.LOWEST + " -> " + percentagePx + "px");

        // Sets the View width to 80% of the screen in PX
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) percentagePx;
        view.setLayoutParams(layoutParams);
    }

    /**
     * 4. Demonstrates the use of physical unit conversion (Millimeters).
     */
    private void demonstratePhysicalUnitUsage(View view) {
        float mmValue = 5.0f; // 5 millimeters

        // Converts 5mm to Pixel (PX)
        float mmInPx = AppDimensPhysicalUnits.INSTANCE.toMm(mmValue, getResources());

        Log.d("AppDimensExample", "4. Physical: " + mmValue + "mm -> " + mmInPx + "px");

        // Sets the View top margin using the MM to PX conversion
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.topMargin = (int) mmInPx;

        // Updates the TextView to show the conversion result
        binding.tvPhysicalUnit.setText(
                "4. Physical Unit (MM) - 5mm margin (~" + (int) mmInPx + "px)"
        );

        view.setLayoutParams(layoutParams);
    }
}