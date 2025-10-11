package com.example.app.views.java.pt;

import android.view.View;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

import com.appdimens.dynamic.code.AppDimens;

/**
 * Data Binding Adapters customizados para aplicar dimensões dinâmicas da biblioteca AppDimens.
 */
public class DimensBindingAdapters {

    // --- Adaptadores para Dimensões de Layout (Dp -> Px) ---

    /**
     * Define a largura de uma View, convertendo o valor Dp Float (ex: 48f) para PX
     * usando o ajuste dinâmico da AppDimensDynamic.
     * Uso no XML: app:dynamicWidthDp="@{48f}" ou app:dynamicWidthDp="@{minhaVariavelFloat}"
     */
    @BindingAdapter("app:dynamicWidthDp")
    public static void setDynamicWidth(View view, float dpValue) {
        // 1. Cria o objeto Dp ajustável (48.dp)
        // 2. Chama toPx(resources) para obter o valor dinamicamente ajustado em Pixels
        float pxValue = AppDimens.INSTANCE.dynamic(dpValue, false).toPx(view.getResources());

        view.getLayoutParams().width = (int) pxValue;
        view.requestLayout();
    }

    /**
     * Define a altura de uma View, convertendo Dp Float para PX dinâmico.
     */
    @BindingAdapter("app:dynamicHeightDp")
    public static void setDynamicHeight(View view, float dpValue) {
        float pxValue = AppDimens.INSTANCE.dynamic(dpValue, false).toPx(view.getResources());

        view.getLayoutParams().height = (int) pxValue;
        view.requestLayout();
    }

    // --- Adaptador para Tamanho de Texto (Dp -> Sp/Px) ---

    /**
     * Define o tamanho do texto (TextView), convertendo Dp Float para SP/PX dinâmico.
     * O AppDimensDynamic.toSp() garante que o ajuste de escala seja aplicado ao texto.
     * Uso no XML: app:dynamicTextSizeDp="@{20f}"
     */
    @BindingAdapter("app:dynamicTextSizeDp")
    public static void setDynamicTextSize(TextView textView, float dpValue) {
        // Converte o Dp Dinâmico para Scaleable Pixels (SP) em Pixels (Float)
        float spValueInPx = AppDimens.INSTANCE.dynamic(dpValue, false).toSp(textView.getResources());

        // Define o texto usando TypedValue.COMPLEX_UNIT_PX (pixels brutos)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, spValueInPx);
    }
}