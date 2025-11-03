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
import com.example.app.databinding.ActivityFluidExampleBinding

class FluidExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFluidExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fluid_example)
        binding.minValue = 40f
        binding.maxValue = 72f
        val dp = AppDimens.from(48f).fluid(40f, 72f).toDp(resources)
        binding.tvResult.text = "FLUID: 48dp → ${dp.toInt()}dp (range: 40-72dp)"
    }
}

