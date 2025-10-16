/**
 * @author Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 */
package com.example.app.views.kotlin.pt

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.appdimens.dynamic.code.AppDimens

/**
 * [EN] Custom Data Binding Adapters to apply dynamic dimensions from the AppDimens library.
 *
 * [PT] Adaptadores de Data Binding personalizados para aplicar dimensões dinâmicas da biblioteca AppDimens.
 */
object DimensBindingAdapters {

    // [EN] --- Adapters for Layout Dimensions (Dp -> Px) ---
    // [PT] --- Adaptadores para Dimensões de Layout (Dp -> Px) ---

    /**
     * [EN] Sets the width of a View, converting the Dp Float value (e.g., 48f) to PX
     * using the dynamic adjustment from AppDimensDynamic.
     * XML usage: app:dynamicWidthDp="@{48f}" or app:dynamicWidthDp="@{myFloatVariable}"
     *
     * [PT] Define a largura de uma View, convertendo o valor Dp Float (ex: 48f) para PX
     * usando o ajuste dinâmico do AppDimensDynamic.
     * Uso no XML: app:dynamicWidthDp="@{48f}" ou app:dynamicWidthDp="@{minhaVariavelFloat}"
     */
    @JvmStatic
    @BindingAdapter("app:dynamicWidthDp")
    fun setDynamicWidth(view: View, dpValue: Float) {
        // [EN] 1. Creates the adjustable Dp object (48.dp)
        //      2. Calls toPx(resources) to get the dynamically adjusted value in Pixels
        // [PT] 1. Cria o objeto Dp ajustável (48.dp)
        //      2. Chama toPx(resources) para obter o valor dinamicamente ajustado em Pixels
        val pxValue = AppDimens.dynamic(dpValue).toPx(view.resources)

        view.layoutParams.width = pxValue.toInt()
        view.requestLayout()
    }

    /**
     * [EN] Sets the height of a View, converting Dp Float to dynamic PX.
     *
     * [PT] Define a altura de uma View, convertendo Dp Float para PX dinâmico.
     */
    @JvmStatic
    @BindingAdapter("app:dynamicHeightDp")
    fun setDynamicHeight(view: View, dpValue: Float) {
        val pxValue = AppDimens.dynamic(dpValue).toPx(view.resources)

        view.layoutParams.height = pxValue.toInt()
        view.requestLayout()
    }

    // [EN] --- Adapter for Text Size (Dp -> Sp/Px) ---
    // [PT] --- Adaptador para Tamanho de Texto (Dp -> Sp/Px) ---

    /**
     * [EN] Sets the text size (TextView), converting Dp Float to dynamic SP/PX.
     * AppDimensDynamic.toSp() ensures that the scaling adjustment is applied to the text.
     * XML usage: app:dynamicTextSizeDp="@{20f}"
     *
     * [PT] Define o tamanho do texto (TextView), convertendo Dp Float para SP/PX dinâmico.
     * O AppDimensDynamic.toSp() garante que o ajuste de escala seja aplicado ao texto.
     * Uso no XML: app:dynamicTextSizeDp="@{20f}"
     */
    @JvmStatic
    @BindingAdapter("app:dynamicTextSizeDp")
    fun setDynamicTextSize(textView: TextView, dpValue: Float) {
        // [EN] Converts Dynamic Dp to Scaleable Pixels (SP) in Pixels (Float)
        // [PT] Converte o Dp Dinâmico para Scaleable Pixels (SP) em Pixels (Float)
        val spValueInPx = AppDimens.dynamic(dpValue).toSp(textView.resources)

        // [EN] Sets the text size using TypedValue.COMPLEX_UNIT_PX (raw pixels)
        // [PT] Define o texto usando TypedValue.COMPLEX_UNIT_PX (pixels brutos)
        textView.setTextSize(android.util.TypedValue.COMPLEX_UNIT_PX, spValueInPx)
    }
}