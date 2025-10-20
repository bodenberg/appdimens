/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS
 *
 * Description:
 * The AppDimens library is a dimension management system that automatically
 * adjusts Dp, Sp, and Px values in a responsive and mathematically refined way,
 * ensuring layout consistency across any screen size or ratio.
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
import AppDimens

// MARK: - CGFloat Extensions

public extension CGFloat {
    
    /**
     * [EN] Creates a fixed dimension from a CGFloat value.
     * [PT] Cria uma dimensão fixa a partir de um valor CGFloat.
     */
    var fxpt: CGFloat {
        return AppDimens.fixed(self).toPoints()
    }
    
    /**
     * [EN] Creates a dynamic dimension from a CGFloat value.
     * [PT] Cria uma dimensão dinâmica a partir de um valor CGFloat.
     */
    var dypt: CGFloat {
        return AppDimens.dynamic(self).toPoints()
    }
    
    /**
     * [EN] Creates a fixed dimension in pixels from a CGFloat value.
     * [PT] Cria uma dimensão fixa em pixels a partir de um valor CGFloat.
     */
    var fxpx: CGFloat {
        return AppDimens.fixed(self).toPixels()
    }
    
    /**
     * [EN] Creates a dynamic dimension in pixels from a CGFloat value.
     * [PT] Cria uma dimensão dinâmica em pixels a partir de um valor CGFloat.
     */
    var dypx: CGFloat {
        return AppDimens.dynamic(self).toPixels()
    }
}

// MARK: - Int Extensions

public extension Int {
    
    /**
     * [EN] Creates a fixed dimension from an Int value.
     * [PT] Cria uma dimensão fixa a partir de um valor Int.
     */
    var fxpt: CGFloat {
        return AppDimens.fixed(self).toPoints()
    }
    
    /**
     * [EN] Creates a dynamic dimension from an Int value.
     * [PT] Cria uma dimensão dinâmica a partir de um valor Int.
     */
    var dypt: CGFloat {
        return AppDimens.dynamic(self).toPoints()
    }
    
    /**
     * [EN] Creates a fixed dimension in pixels from an Int value.
     * [PT] Cria uma dimensão fixa em pixels a partir de um valor Int.
     */
    var fxpx: CGFloat {
        return AppDimens.fixed(self).toPixels()
    }
    
    /**
     * [EN] Creates a dynamic dimension in pixels from an Int value.
     * [PT] Cria uma dimensão dinâmica em pixels a partir de um valor Int.
     */
    var dypx: CGFloat {
        return AppDimens.dynamic(self).toPixels()
    }
}

// MARK: - SwiftUI Extensions

#if canImport(SwiftUI)
import SwiftUI

@available(iOS 13.0, *)
public extension View {
    
    /**
     * [EN] Applies fixed padding to the view.
     * [PT] Aplica padding fixo à view.
     */
    func fxPadding(_ value: CGFloat) -> some View {
        self.padding(value.fxpt)
    }
    
    /**
     * [EN] Applies dynamic padding to the view.
     * [PT] Aplica padding dinâmico à view.
     */
    func dyPadding(_ value: CGFloat) -> some View {
        self.padding(value.dypt)
    }
    
    /**
     * [EN] Applies fixed padding with specific edges to the view.
     * [PT] Aplica padding fixo com bordas específicas à view.
     */
    func fxPadding(_ edges: Edge.Set = .all, _ value: CGFloat) -> some View {
        self.padding(edges, value.fxpt)
    }
    
    /**
     * [EN] Applies dynamic padding with specific edges to the view.
     * [PT] Aplica padding dinâmico com bordas específicas à view.
     */
    func dyPadding(_ edges: Edge.Set = .all, _ value: CGFloat) -> some View {
        self.padding(edges, value.dypt)
    }
    
    /**
     * [EN] Applies fixed frame size to the view.
     * [PT] Aplica tamanho de frame fixo à view.
     */
    func fxFrame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        self.frame(
            width: width?.fxpt,
            height: height?.fxpt
        )
    }
    
    /**
     * [EN] Applies dynamic frame size to the view.
     * [PT] Aplica tamanho de frame dinâmico à view.
     */
    func dyFrame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        self.frame(
            width: width?.dypt,
            height: height?.dypt
        )
    }
    
    /**
     * [EN] Applies fixed corner radius to the view.
     * [PT] Aplica raio de canto fixo à view.
     */
    func fxCornerRadius(_ radius: CGFloat) -> some View {
        self.cornerRadius(radius.fxpt)
    }
    
    /**
     * [EN] Applies dynamic corner radius to the view.
     * [PT] Aplica raio de canto dinâmico à view.
     */
    func dyCornerRadius(_ radius: CGFloat) -> some View {
        self.cornerRadius(radius.dypt)
    }
    
    /**
     * [EN] Wrapper function for dynamic dimensions (similar to Kotlin/Compose).
     * [PT] Função wrapper para dimensões dinâmicas (similar ao Kotlin/Compose).
     */
    func dynamicDp(_ base: CGFloat, @ViewBuilder content: @escaping (CGFloat) -> some View) -> some View {
        return base.dynamic().dimension(content: content)
    }

    /**
     * [EN] Wrapper function for fixed dimensions (similar to Kotlin/Compose).
     * [PT] Função wrapper para dimensões fixas (similar ao Kotlin/Compose).
     */
    func fixedDp(_ base: CGFloat, @ViewBuilder content: @escaping (CGFloat) -> some View) -> some View {
        return base.fixed().dimension(content: content)
    }
}

@available(iOS 13.0, *)
public extension Font {
    
    /**
     * [EN] Creates a system font with fixed size.
     * [PT] Cria uma fonte do sistema com tamanho fixo.
     */
    static func fxSystem(size: CGFloat, weight: Font.Weight = .regular, design: Font.Design = .default) -> Font {
        return .system(size: size.fxpt, weight: weight, design: design)
    }
    
    /**
     * [EN] Creates a system font with dynamic size.
     * [PT] Cria uma fonte do sistema com tamanho dinâmico.
     */
    static func dySystem(size: CGFloat, weight: Font.Weight = .regular, design: Font.Design = .default) -> Font {
        return .system(size: size.dypt, weight: weight, design: design)
    }
    
    /**
     * [EN] Creates a custom font with fixed size.
     * [PT] Cria uma fonte customizada com tamanho fixo.
     */
    static func fxCustom(_ name: String, size: CGFloat) -> Font {
        return .custom(name, size: size.fxpt)
    }
    
    /**
     * [EN] Creates a custom font with dynamic size.
     * [PT] Cria uma fonte customizada com tamanho dinâmico.
     */
    static func dyCustom(_ name: String, size: CGFloat) -> Font {
        return .custom(name, size: size.dypt)
    }
}

@available(iOS 13.0, *)
public extension Spacer {
    
    /**
     * [EN] Creates a spacer with fixed minimum length.
     * [PT] Cria um espaçador com comprimento mínimo fixo.
     */
    static func fxMinLength(_ length: CGFloat) -> Spacer {
        return Spacer(minLength: length.fxpt)
    }
    
    /**
     * [EN] Creates a spacer with dynamic minimum length.
     * [PT] Cria um espaçador com comprimento mínimo dinâmico.
     */
    static func dyMinLength(_ length: CGFloat) -> Spacer {
        return Spacer(minLength: length.dypt)
    }
}

#endif

// MARK: - UIKit Extensions

public extension UIView {
    
    /**
     * [EN] Applies fixed corner radius to the view.
     * [PT] Aplica raio de canto fixo à view.
     */
    func fxCornerRadius(_ radius: CGFloat) {
        self.layer.cornerRadius = radius.fxpt
    }
    
    /**
     * [EN] Applies dynamic corner radius to the view.
     * [PT] Aplica raio de canto dinâmico à view.
     */
    func dyCornerRadius(_ radius: CGFloat) {
        self.layer.cornerRadius = radius.dypt
    }
    
    /**
     * [EN] Applies fixed border width to the view.
     * [PT] Aplica largura de borda fixa à view.
     */
    func fxBorderWidth(_ width: CGFloat) {
        self.layer.borderWidth = width.fxpt
    }
    
    /**
     * [EN] Applies dynamic border width to the view.
     * [PT] Aplica largura de borda dinâmica à view.
     */
    func dyBorderWidth(_ width: CGFloat) {
        self.layer.borderWidth = width.dypt
    }
}

public extension UILabel {
    
    /**
     * [EN] Sets the font size with fixed scaling.
     * [PT] Define o tamanho da fonte com escala fixa.
     */
    func fxFontSize(_ size: CGFloat) {
        self.font = self.font.withSize(size.fxpt)
    }
    
    /**
     * [EN] Sets the font size with dynamic scaling.
     * [PT] Define o tamanho da fonte com escala dinâmica.
     */
    func dyFontSize(_ size: CGFloat) {
        self.font = self.font.withSize(size.dypt)
    }
}

public extension UIButton {
    
    /**
     * [EN] Sets the title label font size with fixed scaling.
     * [PT] Define o tamanho da fonte do título com escala fixa.
     */
    func fxTitleFontSize(_ size: CGFloat) {
        self.titleLabel?.font = self.titleLabel?.font.withSize(size.fxpt)
    }
    
    /**
     * [EN] Sets the title label font size with dynamic scaling.
     * [PT] Define o tamanho da fonte do título com escala dinâmica.
     */
    func dyTitleFontSize(_ size: CGFloat) {
        self.titleLabel?.font = self.titleLabel?.font.withSize(size.dypt)
    }
}

public extension UITextField {
    
    /**
     * [EN] Sets the font size with fixed scaling.
     * [PT] Define o tamanho da fonte com escala fixa.
     */
    func fxFontSize(_ size: CGFloat) {
        self.font = self.font?.withSize(size.fxpt)
    }
    
    /**
     * [EN] Sets the font size with dynamic scaling.
     * [PT] Define o tamanho da fonte com escala dinâmica.
     */
    func dyFontSize(_ size: CGFloat) {
        self.font = self.font?.withSize(size.dypt)
    }
}

public extension UITextView {
    
    /**
     * [EN] Sets the font size with fixed scaling.
     * [PT] Define o tamanho da fonte com escala fixa.
     */
    func fxFontSize(_ size: CGFloat) {
        self.font = self.font?.withSize(size.fxpt)
    }
    
    /**
     * [EN] Sets the font size with dynamic scaling.
     * [PT] Define o tamanho da fonte com escala dinâmica.
     */
    func dyFontSize(_ size: CGFloat) {
        self.font = self.font?.withSize(size.dypt)
    }
}
