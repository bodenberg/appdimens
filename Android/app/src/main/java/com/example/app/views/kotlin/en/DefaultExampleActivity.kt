/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.views.kotlin.en

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appdimens.dynamic.code.AppDimens
import com.example.app.R
import com.example.app.databinding.ActivityDefaultExampleBinding

/**
 * DEFAULT Strategy - Code (XML + DataBinding) Example
 * Formula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × arAdjustment
 */
class DefaultExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDefaultExampleBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_default_example)
        
        // Set base value for DataBinding
        val baseValue = 48f
        binding.baseValue = baseValue
        
        // Calculate DEFAULT dimension
        val defaultDp = AppDimens.from(baseValue).default().toDp(resources)
        val defaultPx = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP,
            defaultDp,
            resources.displayMetrics
        )
        
        // Update result text
        binding.tvResult.text = "DEFAULT: ${baseValue}dp → ${defaultDp.toInt()}dp (${defaultPx.toInt()}px)"
        
        // Demonstrate programmatic usage
        binding.demoViewDefault.post {
            Toast.makeText(
                this,
                "View size: ${binding.demoViewDefault.width}px × ${binding.demoViewDefault.height}px",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

