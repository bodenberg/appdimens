/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.views.kotlin.pt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.appdimens.dynamic.code.AppDimens
import com.example.app.R
import com.example.app.databinding.ActivityBalancedExampleBinding

class BalancedExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBalancedExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_balanced_example)
        binding.baseValue = 48f
        val dp = AppDimens.from(48f).balanced().toDp(resources)
        binding.tvResult.text = "BALANCED: 48dp → ${dp.toInt()}dp"
    }
}

