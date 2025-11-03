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
import com.example.app.databinding.ActivityFillExampleBinding

class FillExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFillExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fill_example)
        binding.baseValue = 48f
        val dp = AppDimens.from(48f).fill().toDp(resources)
        binding.tvResult.text = "FILL: 48dp → ${dp.toInt()}dp"
    }
}

