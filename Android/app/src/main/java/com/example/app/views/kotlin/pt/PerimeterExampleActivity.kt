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
import com.example.app.databinding.ActivityPerimeterExampleBinding

class PerimeterExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPerimeterExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_perimeter_example)
        binding.baseValue = 48f
        val dp = AppDimens.from(48f).perimeter().toDp(resources)
        binding.tvResult.text = "PERIMETER: 48dp → ${dp.toInt()}dp"
    }
}

