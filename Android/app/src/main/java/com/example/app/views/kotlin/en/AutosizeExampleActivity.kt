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
import com.example.app.databinding.ActivityAutosizeExampleBinding

class AutosizeExampleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAutosizeExampleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_autosize_example)
        binding.minValue = 32f
        binding.maxValue = 72f
        val dp = AppDimens.from(48f).autoSize(32f, 72f).toDp(resources)
        binding.tvResult.text = "AUTOSIZE: 48dp → ${dp.toInt()}dp (range: 32-72dp)"
    }
}

