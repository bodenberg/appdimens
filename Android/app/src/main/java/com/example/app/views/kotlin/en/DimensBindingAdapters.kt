/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * 
 * Complete DataBinding Adapters for all 13 AppDimens scaling strategies
 */
package com.example.app.views.kotlin.en

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.appdimens.dynamic.code.AppDimens

object DimensBindingAdapters {

    // ============================================
    // DEFAULT STRATEGY (formerly Fixed)
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:defaultWidthDp")
    fun setDefaultWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).default().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:defaultHeightDp")
    fun setDefaultHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).default().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:defaultTextSizeSp")
    fun setDefaultTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).default().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // PERCENTAGE STRATEGY (formerly Dynamic)
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:percentageWidthDp")
    fun setPercentageWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).percentage().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:percentageHeightDp")
    fun setPercentageHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).percentage().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:percentageTextSizeSp")
    fun setPercentageTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).percentage().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // BALANCED STRATEGY ⭐ (Recommended)
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:balancedWidthDp")
    fun setBalancedWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).balanced().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:balancedHeightDp")
    fun setBalancedHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).balanced().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:balancedTextSizeSp")
    fun setBalancedTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).balanced().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // LOGARITHMIC STRATEGY
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:logarithmicWidthDp")
    fun setLogarithmicWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).logarithmic().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:logarithmicHeightDp")
    fun setLogarithmicHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).logarithmic().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:logarithmicTextSizeSp")
    fun setLogarithmicTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).logarithmic().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // POWER STRATEGY
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:powerWidthDp")
    fun setPowerWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).power().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:powerHeightDp")
    fun setPowerHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).power().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:powerTextSizeSp")
    fun setPowerTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).power().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // FLUID STRATEGY
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:fluidWidthDpMin", "app:fluidWidthDpMax")
    fun setFluidWidth(view: View, minDp: Float, maxDp: Float) {
        val pxValue = AppDimens.from((minDp + maxDp) / 2f).fluid(minDp, maxDp).toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:fluidHeightDpMin", "app:fluidHeightDpMax")
    fun setFluidHeight(view: View, minDp: Float, maxDp: Float) {
        val pxValue = AppDimens.from((minDp + maxDp) / 2f).fluid(minDp, maxDp).toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    // ============================================
    // INTERPOLATED STRATEGY
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:interpolatedWidthDp")
    fun setInterpolatedWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).interpolated().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:interpolatedHeightDp")
    fun setInterpolatedHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).interpolated().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:interpolatedTextSizeSp")
    fun setInterpolatedTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).interpolated().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // DIAGONAL STRATEGY
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:diagonalWidthDp")
    fun setDiagonalWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).diagonal().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:diagonalHeightDp")
    fun setDiagonalHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).diagonal().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:diagonalTextSizeSp")
    fun setDiagonalTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).diagonal().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // PERIMETER STRATEGY
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:perimeterWidthDp")
    fun setPerimeterWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).perimeter().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:perimeterHeightDp")
    fun setPerimeterHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).perimeter().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:perimeterTextSizeSp")
    fun setPerimeterTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).perimeter().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // FIT STRATEGY (Letterbox)
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:fitWidthDp")
    fun setFitWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).fit().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:fitHeightDp")
    fun setFitHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).fit().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:fitTextSizeSp")
    fun setFitTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).fit().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // FILL STRATEGY (Cover)
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:fillWidthDp")
    fun setFillWidth(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).fill().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:fillHeightDp")
    fun setFillHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.from(dpValue).fill().toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:fillTextSizeSp")
    fun setFillTextSize(textView: TextView, spValue: Float) {
        val adjustedSp = AppDimens.from(spValue).fill().toSp(textView.resources)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, adjustedSp)
    }

    // ============================================
    // AUTOSIZE STRATEGY
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:autosizeWidthDpMin", "app:autosizeWidthDpMax")
    fun setAutosizeWidth(view: View, minDp: Float, maxDp: Float) {
        val pxValue = AppDimens.from((minDp + maxDp) / 2f).autoSize(minDp, maxDp).toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:autosizeHeightDpMin", "app:autosizeHeightDpMax")
    fun setAutosizeHeight(view: View, minDp: Float, maxDp: Float) {
        val pxValue = AppDimens.from((minDp + maxDp) / 2f).autoSize(minDp, maxDp).toDp(view.resources)
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, pxValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    // ============================================
    // NONE STRATEGY
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:noneWidthDp")
    fun setNoneWidth(view: View, dpValue: Float) {
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, dpValue, view.resources.displayMetrics)
        view.layoutParams.width = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:noneHeightDp")
    fun setNoneHeight(view: View, dpValue: Float) {
        val px = android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, dpValue, view.resources.displayMetrics)
        view.layoutParams.height = px.toInt()
        view.requestLayout()
    }

    @JvmStatic
    @BindingAdapter("app:noneTextSizeSp")
    fun setNoneTextSize(textView: TextView, spValue: Float) {
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_SP, spValue)
    }

    // ============================================
    // LEGACY COMPATIBILITY (keep existing code working)
    // ============================================
    
    @JvmStatic
    @BindingAdapter("app:dynamicWidthDp")
    fun setDynamicWidth(view: View, dpValue: Float) {
        setPercentageWidth(view, dpValue) // Redirect to PERCENTAGE
    }

    @JvmStatic
    @BindingAdapter("app:dynamicHeightDp")
    fun setDynamicHeight(view: View, dpValue: Float) {
        setPercentageHeight(view, dpValue) // Redirect to PERCENTAGE
    }

    @JvmStatic
    @BindingAdapter("app:dynamicTextSizeDp")
    fun setDynamicTextSize(textView: TextView, dpValue: Float) {
        setPercentageTextSize(textView, dpValue) // Redirect to PERCENTAGE
    }
}
