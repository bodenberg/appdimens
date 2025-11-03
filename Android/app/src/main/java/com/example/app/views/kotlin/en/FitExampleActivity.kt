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
import com.example.app.databinding.ActivityFitExampleBinding

class FitExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFitExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fit_example)
        binding.baseValue = 48f
        val dp = AppDimens.from(48f).fit().toDp(resources)
        binding.tvResult.text = "FIT: 48dp → ${dp.toInt()}dp"
    }
}

