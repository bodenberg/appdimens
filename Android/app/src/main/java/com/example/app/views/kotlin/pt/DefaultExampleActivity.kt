/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.views.kotlin.pt

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appdimens.dynamic.code.AppDimens
import com.example.app.R
import com.example.app.databinding.ActivityDefaultExampleBinding

/**
 * Estratégia DEFAULT - Exemplo Code (XML + DataBinding)
 * Fórmula: f(x) = x × (1 + (W-W₀)/1 × 0.00333) × ajusteAR
 */
class DefaultExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDefaultExampleBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_default_example)
        
        // Define valor base para DataBinding
        val baseValue = 48f
        binding.baseValue = baseValue
        
        // Calcula dimensão DEFAULT
        val defaultDp = AppDimens.from(baseValue).default().toDp(resources)
        val defaultPx = android.util.TypedValue.applyDimension(
            android.util.TypedValue.COMPLEX_UNIT_DIP,
            defaultDp,
            resources.displayMetrics
        )
        
        // Atualiza texto de resultado
        binding.tvResult.text = "DEFAULT: ${baseValue}dp → ${defaultDp.toInt()}dp (${defaultPx.toInt()}px)"
        
        // Demonstra uso programático
        binding.demoViewDefault.post {
            Toast.makeText(
                this,
                "Tamanho da View: ${binding.demoViewDefault.width}px × ${binding.demoViewDefault.height}px",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

