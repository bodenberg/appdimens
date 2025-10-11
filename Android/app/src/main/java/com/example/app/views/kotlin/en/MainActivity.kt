package com.example.app.views.kotlin.en

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

        // 1. Data Binding Setup
        binding = ActivityDynamicDataBindingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ====================================================================
        // 1. DYNAMIC USAGE WITH DATA BINDING (Focusing on the value '48')
        // ====================================================================
        val dimenValue = 48f
        // Passes the value 48 to the 'dimenValue' variable in the XML, activating the Binding Adapter
        binding.dimenValue = dimenValue

        Log.d("AppDimensExample", "1. Dynamic (DB) - Initial Value: ${dimenValue}dp")
        // The Binding Adapter (DimensBindingAdapters.kt) will perform the dynamic conversion to PX

        // Ensures Data Binding is executed immediately (optional)
        binding.executePendingBindings()


        // ====================================================================
        // --- Examples of Direct Kotlin Usage (Non-Data Binding) ---
        // ====================================================================

        // 2. Fixed (Non-Dynamic) Usage
        demonstrateFixedUsage(binding.fixedView)

        // 3. Dynamic Percentage Usage
        demonstratePercentageUsage(binding.percentageView)

        // 4. Physical Unit (MM) Usage
        demonstratePhysicalUnitUsage(binding.physicalUnitView)
    }

    /**
     * 2. Demonstrates the usage of AppDimensFixed (Fixed DP) to maintain the dimension
     * WITHOUT the mathematical scaling adjustment.
     */
    private fun demonstrateFixedUsage(view: View) {
        val dpValue = 64f

        // Converts Fixed Dp (64.dp) to Pixel (PX)
        val fixedPx = AppDimensFixed(dpValue).toPx(resources)

        Log.d("AppDimensExample", "2. Fixed: ${dpValue}dp -> ${fixedPx}px")

        val layoutParams = view.layoutParams
        layoutParams.width = fixedPx.toInt()
        layoutParams.height = fixedPx.toInt()
        view.layoutParams = layoutParams
    }

    /**
     * 3. Demonstrates the usage of dynamic percentage calculation (80% of the screen).
     * Note: The base dimension (LOWEST or HIGHEST) can be changed.
     */
    private fun demonstratePercentageUsage(view: View) {
        val percentage = 0.80f // 80%

        // Calculates 80% of the LOWEST dimension (smallest screen dimension, W or H) in PX
        val percentagePx = AppDimens.dynamicPercentagePx(
            percentage = percentage,
            type = ScreenType.LOWEST,
            resources = resources
        )

        Log.d("AppDimensExample", "3. Percentage: ${percentage * 100}% of ${ScreenType.LOWEST} -> ${percentagePx}px")

        // Sets the View's width to 80% of the screen in PX
        val layoutParams = view.layoutParams
        layoutParams.width = percentagePx.toInt()
        view.layoutParams = layoutParams
    }

    /**
     * 4. Demonstrates the usage of physical unit conversion (Millimeters).
     */
    private fun demonstratePhysicalUnitUsage(view: View) {
        val mmValue = 5.0f // 5 millimeters

        // Converts 5mm to Pixel (PX)
        val mmInPx = AppDimensPhysicalUnits.toMm(mmValue, resources)

        Log.d("AppDimensExample", "4. Physical: ${mmValue}mm -> ${mmInPx}px")

        // Sets the View's top margin using the MM to PX conversion
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.topMargin = mmInPx.toInt()

        // Updates the TextView to show the conversion result
        binding.tvPhysicalUnit.text =
            "4. Physical Unit (MM) - 5mm top margin (~${mmInPx.toInt()}px)"

        view.layoutParams = layoutParams
    }
}