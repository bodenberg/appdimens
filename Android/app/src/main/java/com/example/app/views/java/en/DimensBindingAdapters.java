package com.example.app.views.java.en;

import android.view.View;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

import com.appdimens.dynamic.code.AppDimens;

/**
 * Custom Data Binding Adapters to apply dynamic dimensions from the AppDimens library.
 */
public class DimensBindingAdapters {

    // --- Adapters for Layout Dimensions (Dp -> Px) ---

    /**
     * Sets the width of a View, converting the Dp Float value (e.g., 48f) to PX
     * using the dynamic adjustment from AppDimensDynamic.
     * Usage in XML: app:dynamicWidthDp="@{48f}" or app:dynamicWidthDp="@{myFloatVariable}"
     */
    @BindingAdapter("app:dynamicWidthDp")
    public static void setDynamicWidth(View view, float dpValue) {
        // 1. Creates the adjustable Dp object (48.dp)
        // 2. Calls toPx(resources) to get the dynamically adjusted value in Pixels
        float pxValue = AppDimens.INSTANCE.dynamic(dpValue, false).toPx(view.getResources());

        view.getLayoutParams().width = (int) pxValue;
        view.requestLayout();
    }

    /**
     * Sets the height of a View, converting Dp Float to dynamic PX.
     */
    @BindingAdapter("app:dynamicHeightDp")
    public static void setDynamicHeight(View view, float dpValue) {
        float pxValue = AppDimens.INSTANCE.dynamic(dpValue, false).toPx(view.getResources());

        view.getLayoutParams().height = (int) pxValue;
        view.requestLayout();
    }

    // --- Adapter for Text Size (Dp -> Sp/Px) ---

    /**
     * Sets the text size (TextView), converting Dp Float to dynamic SP/PX.
     * AppDimensDynamic.toSp() ensures that the scale adjustment is applied to the text.
     * Usage in XML: app:dynamicTextSizeDp="@{20f}"
     */
    @BindingAdapter("app:dynamicTextSizeDp")
    public static void setDynamicTextSize(TextView textView, float dpValue) {
        // Converts Dynamic Dp to Scaleable Pixels (SP) in Pixels (Float)
        float spValueInPx = AppDimens.INSTANCE.dynamic(dpValue, false).toSp(textView.getResources());

        // Sets the text size using TypedValue.COMPLEX_UNIT_PX (raw pixels)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, spValueInPx);
    }
}