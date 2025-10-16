/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.views.java.pt;

import android.view.View;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

import com.appdimens.dynamic.code.AppDimens;

/**
 * [EN] Custom Data Binding Adapters to apply dynamic dimensions from the AppDimens library.
 *
 * [PT] Adaptadores de Data Binding personalizados para aplicar dimensões dinâmicas da biblioteca AppDimens.
 */
public class DimensBindingAdapters {

    // [EN] --- Adapters for Layout Dimensions (Dp -> Px) ---
    // [PT] --- Adaptadores para Dimensões de Layout (Dp -> Px) ---

    /**
     * [EN] Sets the width of a View, converting the Dp Float value (e.g., 48f) to PX
     * using the dynamic adjustment from AppDimensDynamic.
     * Usage in XML: app:dynamicWidthDp="@{48f}" or app:dynamicWidthDp="@{myFloatVariable}"
     *
     * [PT] Define a largura de uma View, convertendo o valor Dp Float (ex: 48f) para PX
     * usando o ajuste dinâmico do AppDimensDynamic.
     * Uso no XML: app:dynamicWidthDp="@{48f}" ou app:dynamicWidthDp="@{minhaVariavelFloat}"
     */
    @BindingAdapter("app:dynamicWidthDp")
    public static void setDynamicWidth(View view, float dpValue) {
        // [EN] 1. Creates the adjustable Dp object (48.dp)
        //      2. Calls toPx(resources) to get the dynamically adjusted value in Pixels
        // [PT] 1. Cria o objeto Dp ajustável (48.dp)
        //      2. Chama toPx(resources) para obter o valor dinamicamente ajustado em Pixels
        float pxValue = AppDimens.INSTANCE.dynamic(dpValue, false).toPx(view.getResources());

        view.getLayoutParams().width = (int) pxValue;
        view.requestLayout();
    }

    /**
     * [EN] Sets the height of a View, converting Dp Float to dynamic PX.
     *
     * [PT] Define a altura de uma View, convertendo Dp Float para PX dinâmico.
     */
    @BindingAdapter("app:dynamicHeightDp")
    public static void setDynamicHeight(View view, float dpValue) {
        float pxValue = AppDimens.INSTANCE.dynamic(dpValue, false).toPx(view.getResources());

        view.getLayoutParams().height = (int) pxValue;
        view.requestLayout();
    }

    // [EN] --- Adapter for Text Size (Dp -> Sp/Px) ---
    // [PT] --- Adaptador para Tamanho de Texto (Dp -> Sp/Px) ---

    /**
     * [EN] Sets the text size (TextView), converting Dp Float to dynamic SP/PX.
     * AppDimensDynamic.toSp() ensures that the scale adjustment is applied to the text.
     * Usage in XML: app:dynamicTextSizeDp="@{20f}"
     *
     * [PT] Define o tamanho do texto (TextView), convertendo Dp Float para SP/PX dinâmico.
     * O AppDimensDynamic.toSp() garante que o ajuste de escala seja aplicado ao texto.
     * Uso no XML: app:dynamicTextSizeDp="@{20f}"
     */
    @BindingAdapter("app:dynamicTextSizeDp")
    public static void setDynamicTextSize(TextView textView, float dpValue) {
        // [EN] Converts Dynamic Dp to Scaleable Pixels (SP) in Pixels (Float)
        // [PT] Converte o Dp Dinâmico para Scaleable Pixels (SP) em Pixels (Float)
        float spValueInPx = AppDimens.INSTANCE.dynamic(dpValue, false).toSp(textView.getResources());

        // [EN] Sets the text size using TypedValue.COMPLEX_UNIT_PX (raw pixels)
        // [PT] Define o texto usando TypedValue.COMPLEX_UNIT_PX (pixels brutos)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, spValueInPx);
    }
}