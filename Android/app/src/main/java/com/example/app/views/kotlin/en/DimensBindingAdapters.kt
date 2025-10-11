package com.example.app.views.kotlin.en

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.appdimens.dynamic.code.AppDimens

/**
 * Custom Data Binding Adapters to apply dynamic dimensions from the AppDimens library.
 */
object DimensBindingAdapters {

    // --- Adapters for Layout Dimensions (Dp -> Px) ---

    /**
     * Sets the width of a View, converting the Float Dp value (e.g., 48f) to PX
     * using the dynamic adjustment from AppDimensDynamic.
     * XML usage: app:dynamicWidthDp="@{48f}" or app:dynamicWidthDp="@{myFloatVariable}"
     */
    @JvmStatic
    @BindingAdapter("app:dynamicWidthDp")
    fun setDynamicWidth(view: View, dpValue: Float) {
        // 1. Creates the adjustable Dp object (48.dp)
        // 2. Calls toPx(resources) to get the dynamically adjusted value in Pixels
        val pxValue = AppDimens.dynamic(dpValue).toPx(view.resources)

        view.layoutParams.width = pxValue.toInt()
        view.requestLayout()
    }

    /**
     * Sets the height of a View, converting Float Dp to dynamic PX.
     */
    @JvmStatic
    @BindingAdapter("app:dynamicHeightDp")
    fun setDynamicHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.dynamic(dpValue).toPx(view.resources)

        view.layoutParams.height = pxValue.toInt()
        view.requestLayout()
    }

    // --- Adapter for Text Size (Dp -> Sp/Px) ---

    /**
     * Sets the text size (TextView), converting Float Dp to dynamic SP/PX.
     * AppDimensDynamic.toSp() ensures that the scaling adjustment is applied to the text.
     * XML usage: app:dynamicTextSizeDp="@{20f}"
     */
    @JvmStatic
    @BindingAdapter("app:dynamicTextSizeDp")
    fun setDynamicTextSize(textView: TextView, dpValue: Float) {
        // Converts Dynamic Dp to Scaleable Pixels (SP) in Pixels (Float)
        val spValueInPx = AppDimens.dynamic(dpValue).toSp(textView.resources)

        // Sets the text size using TypedValue.COMPLEX_UNIT_PX (raw pixels)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, spValueInPx)
    }
}