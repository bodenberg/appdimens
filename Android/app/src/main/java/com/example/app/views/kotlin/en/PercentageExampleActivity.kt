/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.views.kotlin.en

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appdimens.dynamic.code.AppDimens
import com.example.app.R
import com.example.app.databinding.ActivityPercentageExampleBinding

/**
 * PERCENTAGE Strategy - Code (XML + DataBinding) Example
 * Formula: f(x) = x × (W / W₀)
 */
class PercentageExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPercentageExampleBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_percentage_example)
        
        val baseValue = 48f
        binding.baseValue = baseValue
        
        val percentageDp = AppDimens.from(baseValue).percentage().toDp(resources)
        val percentagePx = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP, percentageDp, resources.displayMetrics
        )
        
        binding.tvResult.text = "PERCENTAGE: ${baseValue}dp → ${percentageDp.toInt()}dp (${percentagePx.toInt()}px)"
    }
}

