/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens iOS - UI Module
 *
 * Description:
 * Main export file for the AppDimens UI module, providing access to all
 * UIKit and SwiftUI integration functionality.
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

// MARK: - UI Module Exports

// Re-export all UI functionality
// Note: These are defined in the same module, so no import needed

// MARK: - SwiftUI Environment Integration

#if canImport(SwiftUI)
@available(iOS 13.0, *)
public extension View {
    
    /**
     * [EN] Wraps the view with AppDimens environment provider.
     * @return The view wrapped with dimension environment.
     * [PT] Envolve a view com o provedor de ambiente AppDimens.
     * @return A view envolvida com o ambiente de dimensão.
     */
    func withAppDimens() -> some View {
        DimensProvider {
            self
        }
    }
    
    /**
     * [EN] Applies fixed padding to all edges.
     * @param value The padding value.
     * @return The view with fixed padding.
     * [PT] Aplica padding fixo a todas as bordas.
     * @param value O valor do padding.
     * @return A view com padding fixo.
     */
    func fxPadding(_ value: CGFloat) -> some View {
        self.padding(value.fxpt)
    }
    
    /**
     * [EN] Applies dynamic padding to all edges.
     * @param value The padding value.
     * @return The view with dynamic padding.
     * [PT] Aplica padding dinâmico a todas as bordas.
     * @param value O valor do padding.
     * @return A view com padding dinâmico.
     */
    func dyPadding(_ value: CGFloat) -> some View {
        self.padding(value.dypt)
    }
    
    /**
     * [EN] Applies fixed frame size.
     * @param width The frame width.
     * @param height The frame height.
     * @return The view with fixed frame.
     * [PT] Aplica tamanho de frame fixo.
     * @param width A largura do frame.
     * @param height A altura do frame.
     * @return A view com frame fixo.
     */
    func fxFrame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        self.frame(
            width: width?.fxpt,
            height: height?.fxpt
        )
    }
    
    /**
     * [EN] Applies dynamic frame size.
     * @param width The frame width.
     * @param height The frame height.
     * @return The view with dynamic frame.
     * [PT] Aplica tamanho de frame dinâmico.
     * @param width A largura do frame.
     * @param height A altura do frame.
     * @return A view com frame dinâmico.
     */
    func dyFrame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        self.frame(
            width: width?.dypt,
            height: height?.dypt
        )
    }
    
    /**
     * [EN] Applies fixed corner radius.
     * @param radius The corner radius value.
     * @return The view with fixed corner radius.
     * [PT] Aplica raio de canto fixo.
     * @param radius O valor do raio de canto.
     * @return A view com raio de canto fixo.
     */
    func fxCornerRadius(_ radius: CGFloat) -> some View {
        self.cornerRadius(radius.fxpt)
    }
    
    /**
     * [EN] Applies dynamic corner radius.
     * @param radius The corner radius value.
     * @return The view with dynamic corner radius.
     * [PT] Aplica raio de canto dinâmico.
     * @param radius O valor do raio de canto.
     * @return A view com raio de canto dinâmico.
     */
    func dyCornerRadius(_ radius: CGFloat) -> some View {
        self.cornerRadius(radius.dypt)
    }
}

@available(iOS 13.0, *)
public extension Font {
    
    /**
     * [EN] Creates a system font with fixed size.
     * @param size The font size.
     * @param weight The font weight.
     * @param design The font design.
     * @return A system font with fixed size.
     * [PT] Cria uma fonte do sistema com tamanho fixo.
     * @param size O tamanho da fonte.
     * @param weight O peso da fonte.
     * @param design O design da fonte.
     * @return Uma fonte do sistema com tamanho fixo.
     */
    static func fxSystem(size: CGFloat, weight: Font.Weight = .regular, design: Font.Design = .default) -> Font {
        return .system(size: size.fxpt, weight: weight, design: design)
    }
    
    /**
     * [EN] Creates a system font with dynamic size.
     * @param size The font size.
     * @param weight The font weight.
     * @param design The font design.
     * @return A system font with dynamic size.
     * [PT] Cria uma fonte do sistema com tamanho dinâmico.
     * @param size O tamanho da fonte.
     * @param weight O peso da fonte.
     * @param design O design da fonte.
     * @return Uma fonte do sistema com tamanho dinâmico.
     */
    static func dySystem(size: CGFloat, weight: Font.Weight = .regular, design: Font.Design = .default) -> Font {
        return .system(size: size.dypt, weight: weight, design: design)
    }
}
#endif

// MARK: - UIKit Extensions

public extension UIView {
    
    /**
     * [EN] Applies fixed corner radius to the view.
     * @param radius The corner radius value.
     * [PT] Aplica raio de canto fixo à view.
     * @param radius O valor do raio de canto.
     */
    func fxCornerRadius(_ radius: CGFloat) {
        self.layer.cornerRadius = radius.fxpt
    }
    
    /**
     * [EN] Applies dynamic corner radius to the view.
     * @param radius The corner radius value.
     * [PT] Aplica raio de canto dinâmico à view.
     * @param radius O valor do raio de canto.
     */
    func dyCornerRadius(_ radius: CGFloat) {
        self.layer.cornerRadius = radius.dypt
    }
    
    /**
     * [EN] Applies fixed border width to the view.
     * @param width The border width value.
     * [PT] Aplica largura de borda fixa à view.
     * @param width O valor da largura da borda.
     */
    func fxBorderWidth(_ width: CGFloat) {
        self.layer.borderWidth = width.fxpt
    }
    
    /**
     * [EN] Applies dynamic border width to the view.
     * @param width The border width value.
     * [PT] Aplica largura de borda dinâmica à view.
     * @param width O valor da largura da borda.
     */
    func dyBorderWidth(_ width: CGFloat) {
        self.layer.borderWidth = width.dypt
    }
}

public extension UILabel {
    
    /**
     * [EN] Sets the font size with fixed scaling.
     * @param size The font size.
     * [PT] Define o tamanho da fonte com escala fixa.
     * @param size O tamanho da fonte.
     */
    func fxFontSize(_ size: CGFloat) {
        self.font = self.font.withSize(size.fxpt)
    }
    
    /**
     * [EN] Sets the font size with dynamic scaling.
     * @param size The font size.
     * [PT] Define o tamanho da fonte com escala dinâmica.
     * @param size O tamanho da fonte.
     */
    func dyFontSize(_ size: CGFloat) {
        self.font = self.font.withSize(size.dypt)
    }
}

public extension UIButton {
    
    /**
     * [EN] Sets the title label font size with fixed scaling.
     * @param size The font size.
     * [PT] Define o tamanho da fonte do título com escala fixa.
     * @param size O tamanho da fonte.
     */
    func fxTitleFontSize(_ size: CGFloat) {
        self.titleLabel?.font = self.titleLabel?.font.withSize(size.fxpt)
    }
    
    /**
     * [EN] Sets the title label font size with dynamic scaling.
     * @param size The font size.
     * [PT] Define o tamanho da fonte do título com escala dinâmica.
     * @param size O tamanho da fonte.
     */
    func dyTitleFontSize(_ size: CGFloat) {
        self.titleLabel?.font = self.titleLabel?.font.withSize(size.dypt)
    }
}

// MARK: - Module Information

/**
 * [EN] Information about the AppDimens UI module.
 * [PT] Informações sobre o módulo AppDimens UI.
 */
public struct AppDimensUIInfo {
    public static let version = "1.0.5"
    public static let moduleName = "AppDimensUI"
    public static let description = "UIKit and SwiftUI integration for AppDimens"
    
    /**
     * [EN] Gets the module information as a dictionary.
     * @return A dictionary containing module information.
     * [PT] Obtém as informações do módulo como um dicionário.
     * @return Um dicionário contendo informações do módulo.
     */
    public static func info() -> [String: String] {
        return [
            "version": version,
            "moduleName": moduleName,
            "description": description
        ]
    }
}
