/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - Unified Extensions
 *
 * Description:
 * Unified extensions and convenience methods for AppDimens iOS library,
 * providing a fluent API similar to Android Compose implementation.
 * Combines functionality from both AppDimens and AppDimensCore modules.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import Foundation
import UIKit
import SwiftUI

// MARK: - CGFloat Extensions

public extension CGFloat {
    
    /**
     * [EN] Creates a fixed dimension from a CGFloat value.
     * [PT] Cria uma dimensão fixa a partir de um valor CGFloat.
     */
    var fxdp: CGFloat {
        return AppDimens.fixed(self).toPoints()
    }
    
    /**
     * [EN] Creates a fixed dimension with highest screen type from a CGFloat value.
     * [PT] Cria uma dimensão fixa com tipo de tela mais alto a partir de um valor CGFloat.
     */
    var fxhdp: CGFloat {
        return AppDimens.fixed(self).type(.highest).toPoints()
    }
    
    /**
     * [EN] Creates a dynamic dimension from a CGFloat value.
     * [PT] Cria uma dimensão dinâmica a partir de um valor CGFloat.
     */
    var dydp: CGFloat {
        return AppDimens.dynamic(self).toPoints()
    }
    
    /**
     * [EN] Creates a dynamic dimension with highest screen type from a CGFloat value.
     * [PT] Cria uma dimensão dinâmica com tipo de tela mais alto a partir de um valor CGFloat.
     */
    var dyhdp: CGFloat {
        return AppDimens.dynamic(self).type(.highest).toPoints()
    }
    
    /**
     * [EN] Creates a fixed dimension in pixels from a CGFloat value.
     * [PT] Cria uma dimensão fixa em pixels a partir de um valor CGFloat.
     */
    var fxpx: CGFloat {
        return AppDimens.fixed(self).toPixels()
    }
    
    /**
     * [EN] Creates a fixed dimension with highest screen type in pixels from a CGFloat value.
     * [PT] Cria uma dimensão fixa com tipo de tela mais alto em pixels a partir de um valor CGFloat.
     */
    var fxhpx: CGFloat {
        return AppDimens.fixed(self).type(.highest).toPixels()
    }
    
    /**
     * [EN] Creates a dynamic dimension in pixels from a CGFloat value.
     * [PT] Cria uma dimensão dinâmica em pixels a partir de um valor CGFloat.
     */
    var dypx: CGFloat {
        return AppDimens.dynamic(self).toPixels()
    }
    
    /**
     * [EN] Creates a dynamic dimension with highest screen type in pixels from a CGFloat value.
     * [PT] Cria uma dimensão dinâmica com tipo de tela mais alto em pixels a partir de um valor CGFloat.
     */
    var dyhpx: CGFloat {
        return AppDimens.dynamic(self).type(.highest).toPixels()
    }
}

// MARK: - Int Extensions

public extension Int {
    
    /**
     * [EN] Creates a fixed dimension from an Int value.
     * [PT] Cria uma dimensão fixa a partir de um valor Int.
     */
    var fxdp: CGFloat {
        return AppDimens.fixed(self).toPoints()
    }
    
    /**
     * [EN] Creates a fixed dimension with highest screen type from an Int value.
     * [PT] Cria uma dimensão fixa com tipo de tela mais alto a partir de um valor Int.
     */
    var fxhdp: CGFloat {
        return AppDimens.fixed(self).type(.highest).toPoints()
    }
    
    /**
     * [EN] Creates a dynamic dimension from an Int value.
     * [PT] Cria uma dimensão dinâmica a partir de um valor Int.
     */
    var dydp: CGFloat {
        return AppDimens.dynamic(self).toPoints()
    }
    
    /**
     * [EN] Creates a dynamic dimension with highest screen type from an Int value.
     * [PT] Cria uma dimensão dinâmica com tipo de tela mais alto a partir de um valor Int.
     */
    var dyhdp: CGFloat {
        return AppDimens.dynamic(self).type(.highest).toPoints()
    }
    
    /**
     * [EN] Creates a fixed dimension in pixels from an Int value.
     * [PT] Cria uma dimensão fixa em pixels a partir de um valor Int.
     */
    var fxpx: CGFloat {
        return AppDimens.fixed(self).toPixels()
    }
    
    /**
     * [EN] Creates a fixed dimension with highest screen type in pixels from an Int value.
     * [PT] Cria uma dimensão fixa com tipo de tela mais alto em pixels a partir de um valor Int.
     */
    var fxhpx: CGFloat {
        return AppDimens.fixed(self).type(.highest).toPixels()
    }
    
    /**
     * [EN] Creates a dynamic dimension in pixels from an Int value.
     * [PT] Cria uma dimensão dinâmica em pixels a partir de um valor Int.
     */
    var dypx: CGFloat {
        return AppDimens.dynamic(self).toPixels()
    }
    
    /**
     * [EN] Creates a dynamic dimension with highest screen type in pixels from an Int value.
     * [PT] Cria uma dimensão dinâmica com tipo de tela mais alto em pixels a partir de um valor Int.
     */
    var dyhpx: CGFloat {
        return AppDimens.dynamic(self).type(.highest).toPixels()
    }
}

// MARK: - SwiftUI Extensions

#if canImport(SwiftUI)
public extension View {
    
    /**
     * [EN] Applies fixed padding to the view.
     * [PT] Aplica padding fixo à view.
     */
    func fxPadding(_ value: CGFloat) -> some View {
        self.padding(value.fxdp)
    }
    
    /**
     * [EN] Applies dynamic padding to the view.
     * [PT] Aplica padding dinâmico à view.
     */
    func dyPadding(_ value: CGFloat) -> some View {
        self.padding(value.dydp)
    }
    
    /**
     * [EN] Applies fixed frame to the view.
     * [PT] Aplica frame fixo à view.
     */
    func fxFrame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        self.frame(width: width?.fxdp, height: height?.fxdp)
    }
    
    /**
     * [EN] Applies dynamic frame to the view.
     * [PT] Aplica frame dinâmico à view.
     */
    func dyFrame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        self.frame(width: width?.dydp, height: height?.dydp)
    }
    
    /**
     * [EN] Applies fixed corner radius to the view.
     * [PT] Aplica raio de canto fixo à view.
     */
    func fxCornerRadius(_ value: CGFloat) -> some View {
        self.cornerRadius(value.fxdp)
    }
    
    /**
     * [EN] Applies dynamic corner radius to the view.
     * [PT] Aplica raio de canto dinâmico à view.
     */
    func dyCornerRadius(_ value: CGFloat) -> some View {
        self.cornerRadius(value.dydp)
    }
    
    /**
     * [EN] Applies fixed system font size to the view.
     * [PT] Aplica tamanho de fonte do sistema fixo à view.
     */
    func fxSystem(size: CGFloat, weight: Font.Weight = .regular, design: Font.Design = .default) -> some View {
        self.font(.system(size: size.fxdp, weight: weight, design: design))
    }
    
    /**
     * [EN] Applies dynamic system font size to the view.
     * [PT] Aplica tamanho de fonte do sistema dinâmico à view.
     */
    func dySystem(size: CGFloat, weight: Font.Weight = .regular, design: Font.Design = .default) -> some View {
        self.font(.system(size: size.dydp, weight: weight, design: design))
    }
    
    /**
     * [EN] Applies fixed custom font to the view.
     * [PT] Aplica fonte customizada fixa à view.
     */
    func fxCustom(font: Font) -> some View {
        self.font(font)
    }
    
    /**
     * [EN] Applies dynamic custom font to the view.
     * [PT] Aplica fonte customizada dinâmica à view.
     */
    func dyCustom(font: Font) -> some View {
        self.font(font)
    }
    
    /**
     * [EN] Applies fixed minimum length constraint to the view.
     * [PT] Aplica restrição de comprimento mínimo fixo à view.
     */
    func fxMinLength(_ value: CGFloat) -> some View {
        self.frame(minWidth: value.fxdp, minHeight: value.fxdp)
    }
    
    /**
     * [EN] Applies dynamic minimum length constraint to the view.
     * [PT] Aplica restrição de comprimento mínimo dinâmico à view.
     */
    func dyMinLength(_ value: CGFloat) -> some View {
        self.frame(minWidth: value.dydp, minHeight: value.dydp)
    }
}

// MARK: - Font Extensions

public extension Font {
    
    /**
     * [EN] Creates a fixed system font with the specified size.
     * [PT] Cria uma fonte do sistema fixa com o tamanho especificado.
     */
    static func fxSystem(size: CGFloat, weight: Font.Weight = .regular, design: Font.Design = .default) -> Font {
        return .system(size: size.fxdp, weight: weight, design: design)
    }
    
    /**
     * [EN] Creates a dynamic system font with the specified size.
     * [PT] Cria uma fonte do sistema dinâmica com o tamanho especificado.
     */
    static func dySystem(size: CGFloat, weight: Font.Weight = .regular, design: Font.Design = .default) -> Font {
        return .system(size: size.dydp, weight: weight, design: design)
    }
}

// MARK: - Spacer Extensions

public extension Spacer {
    
    /**
     * [EN] Creates a fixed spacer with the specified minimum length.
     * [PT] Cria um espaçador fixo com o comprimento mínimo especificado.
     */
    static func fxMinLength(_ value: CGFloat) -> some View {
        Spacer().frame(minWidth: value.fxdp, minHeight: value.fxdp)
    }
    
    /**
     * [EN] Creates a dynamic spacer with the specified minimum length.
     * [PT] Cria um espaçador dinâmico com o comprimento mínimo especificado.
     */
    static func dyMinLength(_ value: CGFloat) -> some View {
        Spacer().frame(minWidth: value.dydp, minHeight: value.dydp)
    }
}
#endif

// MARK: - UIKit Extensions

public extension UIView {
    
    /**
     * [EN] Applies fixed corner radius to the view.
     * [PT] Aplica raio de canto fixo à view.
     */
    func fxCornerRadius(_ value: CGFloat) {
        self.layer.cornerRadius = value.fxdp
    }
    
    /**
     * [EN] Applies dynamic corner radius to the view.
     * [PT] Aplica raio de canto dinâmico à view.
     */
    func dyCornerRadius(_ value: CGFloat) {
        self.layer.cornerRadius = value.dydp
    }
    
    /**
     * [EN] Applies fixed border width to the view.
     * [PT] Aplica largura de borda fixa à view.
     */
    func fxBorderWidth(_ value: CGFloat) {
        self.layer.borderWidth = value.fxdp
    }
    
    /**
     * [EN] Applies dynamic border width to the view.
     * [PT] Aplica largura de borda dinâmica à view.
     */
    func dyBorderWidth(_ value: CGFloat) {
        self.layer.borderWidth = value.dydp
    }
}

public extension UILabel {
    
    /**
     * [EN] Applies fixed font size to the label.
     * [PT] Aplica tamanho de fonte fixo ao label.
     */
    func fxFontSize(_ value: CGFloat) {
        self.font = self.font?.withSize(value.fxdp)
    }
    
    /**
     * [EN] Applies dynamic font size to the label.
     * [PT] Aplica tamanho de fonte dinâmico ao label.
     */
    func dyFontSize(_ value: CGFloat) {
        self.font = self.font?.withSize(value.dydp)
    }
}

public extension UIButton {
    
    /**
     * [EN] Applies fixed title font size to the button.
     * [PT] Aplica tamanho de fonte do título fixo ao botão.
     */
    func fxTitleFontSize(_ value: CGFloat) {
        self.titleLabel?.font = self.titleLabel?.font?.withSize(value.fxdp)
    }
    
    /**
     * [EN] Applies dynamic title font size to the button.
     * [PT] Aplica tamanho de fonte do título dinâmico ao botão.
     */
    func dyTitleFontSize(_ value: CGFloat) {
        self.titleLabel?.font = self.titleLabel?.font?.withSize(value.dydp)
    }
}

public extension UITextField {
    
    /**
     * [EN] Applies fixed font size to the text field.
     * [PT] Aplica tamanho de fonte fixo ao campo de texto.
     */
    func fxFontSize(_ value: CGFloat) {
        self.font = self.font?.withSize(value.fxdp)
    }
    
    /**
     * [EN] Applies dynamic font size to the text field.
     * [PT] Aplica tamanho de fonte dinâmico ao campo de texto.
     */
    func dyFontSize(_ value: CGFloat) {
        self.font = self.font?.withSize(value.dydp)
    }
}

public extension UITextView {
    
    /**
     * [EN] Applies fixed font size to the text view.
     * [PT] Aplica tamanho de fonte fixo à text view.
     */
    func fxFontSize(_ value: CGFloat) {
        self.font = self.font?.withSize(value.fxdp)
    }
    
    /**
     * [EN] Applies dynamic font size to the text view.
     * [PT] Aplica tamanho de fonte dinâmico à text view.
     */
    func dyFontSize(_ value: CGFloat) {
        self.font = self.font?.withSize(value.dydp)
    }
}
