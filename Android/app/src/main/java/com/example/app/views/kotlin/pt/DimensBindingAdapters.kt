package com.example.app.views.kotlin.pt

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.appdimens.dynamic.code.AppDimens

/**
 * Data Binding Adapters customizados para aplicar dimensões dinâmicas da biblioteca AppDimens.
 */
object DimensBindingAdapters {

    // --- Adaptadores para Dimensões de Layout (Dp -> Px) ---

    /**
     * Define a largura de uma View, convertendo o valor Dp Float (ex: 48f) para PX
     * usando o ajuste dinâmico da AppDimensDynamic.
     * Uso no XML: app:dynamicWidthDp="@{48f}" ou app:dynamicWidthDp="@{minhaVariavelFloat}"
     */
    @JvmStatic
    @BindingAdapter("app:dynamicWidthDp")
    fun setDynamicWidth(view: View, dpValue: Float) {
        // 1. Cria o objeto Dp ajustável (48.dp)
        // 2. Chama toPx(resources) para obter o valor dinamicamente ajustado em Pixels
        val pxValue = AppDimens.dynamic(dpValue).toPx(view.resources)

        view.layoutParams.width = pxValue.toInt()
        view.requestLayout()
    }

    /**
     * Define a altura de uma View, convertendo Dp Float para PX dinâmico.
     */
    @JvmStatic
    @BindingAdapter("app:dynamicHeightDp")
    fun setDynamicHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.dynamic(dpValue).toPx(view.resources)

        view.layoutParams.height = pxValue.toInt()
        view.requestLayout()
    }

    // --- Adaptador para Tamanho de Texto (Dp -> Sp/Px) ---

    /**
     * Define o tamanho do texto (TextView), convertendo Dp Float para SP/PX dinâmico.
     * O AppDimensDynamic.toSp() garante que o ajuste de escala seja aplicado ao texto.
     * Uso no XML: app:dynamicTextSizeDp="@{20f}"
     */
    @JvmStatic
    @BindingAdapter("app:dynamicTextSizeDp")
    fun setDynamicTextSize(textView: TextView, dpValue: Float) {
        // Converte o Dp Dinâmico para Scaleable Pixels (SP) em Pixels (Float)
        val spValueInPx = AppDimens.dynamic(dpValue).toSp(textView.resources)

        // Define o texto usando TypedValue.COMPLEX_UNIT_PX (pixels brutos)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, spValueInPx)
    }
}