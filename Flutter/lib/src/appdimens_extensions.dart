/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Extensions
 *
 * Description:
 * Extensions for Flutter widgets and types to provide convenient AppDimens integration.
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

import 'package:flutter/material.dart';
import 'appdimens_fixed.dart';
import 'appdimens_dynamic.dart';
import 'appdimens_fluid.dart';

// MARK: - Double Extensions

/// [EN] Extension for double values to provide convenient AppDimens access.
/// [PT] Extensão para valores double para fornecer acesso conveniente ao AppDimens.
extension AppDimensDoubleExtension on double {
  /// [EN] Creates a fixed dimension builder from this double value.
  /// @return An AppDimensFixed instance for chaining.
  /// [PT] Cria um construtor de dimensão fixa a partir deste valor double.
  /// @return Uma instância AppDimensFixed para encadeamento.
  AppDimensFixed get fx => AppDimensFixed(this);

  /// [EN] Creates a dynamic dimension builder from this double value.
  /// @return An AppDimensDynamic instance for chaining.
  /// [PT] Cria um construtor de dimensão dinâmica a partir deste valor double.
  /// @return Uma instância AppDimensDynamic para encadeamento.
  AppDimensDynamic get dy => AppDimensDynamic(this);

  /// [EN] Creates a fixed dimension builder from this double value with multi-window adjustment ignored.
  /// @return An AppDimensFixed instance for chaining.
  /// [PT] Cria um construtor de dimensão fixa a partir deste valor double ignorando ajustes multi-window.
  /// @return Uma instância AppDimensFixed para encadeamento.
  AppDimensFixed get fxIgnoreMultiWindow => AppDimensFixed(this, ignoreMultiWindowAdjustment: true);

  /// [EN] Creates a dynamic dimension builder from this double value with multi-window adjustment ignored.
  /// @return An AppDimensDynamic instance for chaining.
  /// [PT] Cria um construtor de dimensão dinâmica a partir deste valor double ignorando ajustes multi-window.
  /// @return Uma instância AppDimensDynamic para encadeamento.
  AppDimensDynamic get dyIgnoreMultiWindow => AppDimensDynamic(this, ignoreMultiWindowAdjustment: true);

  /// [EN] Creates a fluid dimension builder from this double value to a maximum value.
  /// @param maxValue The maximum value.
  /// @return An AppDimensFluid instance for chaining.
  /// [PT] Cria um construtor de dimensão fluida a partir deste valor double até um valor máximo.
  /// @param maxValue O valor máximo.
  /// @return Uma instância AppDimensFluid para encadeamento.
  AppDimensFluid fluidTo(double maxValue, {double minWidth = 320, double maxWidth = 768}) {
    return AppDimensFluid(this, maxValue, minWidth: minWidth, maxWidth: maxWidth);
  }

  /// [EN] Creates a fluid dimension builder with this double as maximum value from a minimum.
  /// @param minValue The minimum value.
  /// @return An AppDimensFluid instance for chaining.
  /// [PT] Cria um construtor de dimensão fluida com este double como valor máximo a partir de um mínimo.
  /// @param minValue O valor mínimo.
  /// @return Uma instância AppDimensFluid para encadeamento.
  AppDimensFluid fluidFrom(double minValue, {double minWidth = 320, double maxWidth = 768}) {
    return AppDimensFluid(minValue, this, minWidth: minWidth, maxWidth: maxWidth);
  }
}

// MARK: - Int Extensions

/// [EN] Extension for int values to provide convenient AppDimens access.
/// [PT] Extensão para valores int para fornecer acesso conveniente ao AppDimens.
extension AppDimensIntExtension on int {
  /// [EN] Creates a fixed dimension builder from this int value.
  /// @return An AppDimensFixed instance for chaining.
  /// [PT] Cria um construtor de dimensão fixa a partir deste valor int.
  /// @return Uma instância AppDimensFixed para encadeamento.
  AppDimensFixed get fx => AppDimensFixed(toDouble());

  /// [EN] Creates a dynamic dimension builder from this int value.
  /// @return An AppDimensDynamic instance for chaining.
  /// [PT] Cria um construtor de dimensão dinâmica a partir deste valor int.
  /// @return Uma instância AppDimensDynamic para encadeamento.
  AppDimensDynamic get dy => AppDimensDynamic(toDouble());

  /// [EN] Creates a fixed dimension builder from this int value with multi-window adjustment ignored.
  /// @return An AppDimensFixed instance for chaining.
  /// [PT] Cria um construtor de dimensão fixa a partir deste valor int ignorando ajustes multi-window.
  /// @return Uma instância AppDimensFixed para encadeamento.
  AppDimensFixed get fxIgnoreMultiWindow => AppDimensFixed(toDouble(), ignoreMultiWindowAdjustment: true);

  /// [EN] Creates a dynamic dimension builder from this int value with multi-window adjustment ignored.
  /// @return An AppDimensDynamic instance for chaining.
  /// [PT] Cria um construtor de dimensão dinâmica a partir deste valor int ignorando ajustes multi-window.
  /// @return Uma instância AppDimensDynamic para encadeamento.
  AppDimensDynamic get dyIgnoreMultiWindow => AppDimensDynamic(toDouble(), ignoreMultiWindowAdjustment: true);

  /// [EN] Creates a fluid dimension builder from this int value to a maximum value.
  /// @param maxValue The maximum value.
  /// @return An AppDimensFluid instance for chaining.
  /// [PT] Cria um construtor de dimensão fluida a partir deste valor int até um valor máximo.
  /// @param maxValue O valor máximo.
  /// @return Uma instância AppDimensFluid para encadeamento.
  AppDimensFluid fluidTo(double maxValue, {double minWidth = 320, double maxWidth = 768}) {
    return AppDimensFluid(toDouble(), maxValue, minWidth: minWidth, maxWidth: maxWidth);
  }

  /// [EN] Creates a fluid dimension builder with this int as maximum value from a minimum.
  /// @param minValue The minimum value.
  /// @return An AppDimensFluid instance for chaining.
  /// [PT] Cria um construtor de dimensão fluida com este int como valor máximo a partir de um mínimo.
  /// @param minValue O valor mínimo.
  /// @return Uma instância AppDimensFluid para encadeamento.
  AppDimensFluid fluidFrom(double minValue, {double minWidth = 320, double maxWidth = 768}) {
    return AppDimensFluid(minValue, toDouble(), minWidth: minWidth, maxWidth: maxWidth);
  }
}

// MARK: - Widget Extensions

/// [EN] Extension for Widget to provide convenient AppDimens padding.
/// [PT] Extensão para Widget para fornecer padding conveniente do AppDimens.
extension AppDimensWidgetExtension on Widget {
  /// [EN] Applies fixed padding to this widget.
  /// @param padding The padding value.
  /// @param context The BuildContext.
  /// @return A Padding widget with the calculated padding.
  /// [PT] Aplica padding fixo a este widget.
  /// @param padding O valor do padding.
  /// @param context O BuildContext.
  /// @return Um widget Padding com o padding calculado.
  Widget fxPadding(double padding, BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(AppDimensFixed(padding).calculate(context)),
      child: this,
    );
  }

  /// [EN] Applies dynamic padding to this widget.
  /// @param padding The padding value.
  /// @param context The BuildContext.
  /// @return A Padding widget with the calculated padding.
  /// [PT] Aplica padding dinâmico a este widget.
  /// @param padding O valor do padding.
  /// @param context O BuildContext.
  /// @return Um widget Padding com o padding calculado.
  Widget dyPadding(double padding, BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(AppDimensDynamic(padding).calculate(context)),
      child: this,
    );
  }

  /// [EN] Applies fixed margin to this widget.
  /// @param margin The margin value.
  /// @param context The BuildContext.
  /// @return A Container widget with the calculated margin.
  /// [PT] Aplica margem fixa a este widget.
  /// @param margin O valor da margem.
  /// @param context O BuildContext.
  /// @return Um widget Container com a margem calculada.
  Widget fxMargin(double margin, BuildContext context) {
    return Container(
      margin: EdgeInsets.all(AppDimensFixed(margin).calculate(context)),
      child: this,
    );
  }

  /// [EN] Applies dynamic margin to this widget.
  /// @param margin The margin value.
  /// @param context The BuildContext.
  /// @return A Container widget with the calculated margin.
  /// [PT] Aplica margem dinâmica a este widget.
  /// @param margin O valor da margem.
  /// @param context O BuildContext.
  /// @return Um widget Container com a margem calculada.
  Widget dyMargin(double margin, BuildContext context) {
    return Container(
      margin: EdgeInsets.all(AppDimensDynamic(margin).calculate(context)),
      child: this,
    );
  }

  /// [EN] Applies fixed border radius to this widget.
  /// @param radius The border radius value.
  /// @param context The BuildContext.
  /// @return A ClipRRect widget with the calculated border radius.
  /// [PT] Aplica raio de borda fixo a este widget.
  /// @param radius O valor do raio de borda.
  /// @param context O BuildContext.
  /// @return Um widget ClipRRect com o raio de borda calculado.
  Widget fxBorderRadius(double radius, BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(AppDimensFixed(radius).calculate(context)),
      child: this,
    );
  }

  /// [EN] Applies dynamic border radius to this widget.
  /// @param radius The border radius value.
  /// @param context The BuildContext.
  /// @return A ClipRRect widget with the calculated border radius.
  /// [PT] Aplica raio de borda dinâmico a este widget.
  /// @param radius O valor do raio de borda.
  /// @param context O BuildContext.
  /// @return Um widget ClipRRect com o raio de borda calculado.
  Widget dyBorderRadius(double radius, BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(AppDimensDynamic(radius).calculate(context)),
      child: this,
    );
  }

  /// [EN] Applies fluid padding to this widget.
  /// @param minPadding The minimum padding value.
  /// @param maxPadding The maximum padding value.
  /// @param context The BuildContext.
  /// @return A Padding widget with the calculated fluid padding.
  /// [PT] Aplica padding fluido a este widget.
  /// @param minPadding O valor mínimo do padding.
  /// @param maxPadding O valor máximo do padding.
  /// @param context O BuildContext.
  /// @return Um widget Padding com o padding fluido calculado.
  Widget fluidPadding(double minPadding, double maxPadding, BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(AppDimensFluid(minPadding, maxPadding).calculate(context)),
      child: this,
    );
  }

  /// [EN] Applies fluid margin to this widget.
  /// @param minMargin The minimum margin value.
  /// @param maxMargin The maximum margin value.
  /// @param context The BuildContext.
  /// @return A Container widget with the calculated fluid margin.
  /// [PT] Aplica margem fluida a este widget.
  /// @param minMargin O valor mínimo da margem.
  /// @param maxMargin O valor máximo da margem.
  /// @param context O BuildContext.
  /// @return Um widget Container com a margem fluida calculada.
  Widget fluidMargin(double minMargin, double maxMargin, BuildContext context) {
    return Container(
      margin: EdgeInsets.all(AppDimensFluid(minMargin, maxMargin).calculate(context)),
      child: this,
    );
  }

  /// [EN] Applies fluid border radius to this widget.
  /// @param minRadius The minimum border radius value.
  /// @param maxRadius The maximum border radius value.
  /// @param context The BuildContext.
  /// @return A ClipRRect widget with the calculated fluid border radius.
  /// [PT] Aplica raio de borda fluido a este widget.
  /// @param minRadius O valor mínimo do raio de borda.
  /// @param maxRadius O valor máximo do raio de borda.
  /// @param context O BuildContext.
  /// @return Um widget ClipRRect com o raio de borda fluido calculado.
  Widget fluidBorderRadius(double minRadius, double maxRadius, BuildContext context) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(AppDimensFluid(minRadius, maxRadius).calculate(context)),
      child: this,
    );
  }
}

// MARK: - Text Style Extensions

/// [EN] Extension for TextStyle to provide convenient AppDimens font sizing.
/// [PT] Extensão para TextStyle para fornecer dimensionamento de fonte conveniente do AppDimens.
extension AppDimensTextStyleExtension on TextStyle {
  /// [EN] Applies fixed font size to this text style.
  /// @param fontSize The font size value.
  /// @param context The BuildContext.
  /// @return A new TextStyle with the calculated font size.
  /// [PT] Aplica tamanho de fonte fixo a este estilo de texto.
  /// @param fontSize O valor do tamanho da fonte.
  /// @param context O BuildContext.
  /// @return Um novo TextStyle com o tamanho da fonte calculado.
  TextStyle fxFontSize(double fontSize, BuildContext context) {
    return copyWith(
      fontSize: AppDimensFixed(fontSize).calculate(context),
    );
  }

  /// [EN] Applies dynamic font size to this text style.
  /// @param fontSize The font size value.
  /// @param context The BuildContext.
  /// @return A new TextStyle with the calculated font size.
  /// [PT] Aplica tamanho de fonte dinâmico a este estilo de texto.
  /// @param fontSize O valor do tamanho da fonte.
  /// @param context O BuildContext.
  /// @return Um novo TextStyle com o tamanho da fonte calculado.
  TextStyle dyFontSize(double fontSize, BuildContext context) {
    return copyWith(
      fontSize: AppDimensDynamic(fontSize).calculate(context),
    );
  }

  /// [EN] Applies fluid font size to this text style.
  /// @param minFontSize The minimum font size value.
  /// @param maxFontSize The maximum font size value.
  /// @param context The BuildContext.
  /// @return A new TextStyle with the calculated fluid font size.
  /// [PT] Aplica tamanho de fonte fluido a este estilo de texto.
  /// @param minFontSize O valor mínimo do tamanho da fonte.
  /// @param maxFontSize O valor máximo do tamanho da fonte.
  /// @param context O BuildContext.
  /// @return Um novo TextStyle com o tamanho da fonte fluido calculado.
  TextStyle fluidFontSize(double minFontSize, double maxFontSize, BuildContext context) {
    return copyWith(
      fontSize: AppDimensFluid(minFontSize, maxFontSize).calculate(context),
    );
  }
}

// MARK: - Container Extensions

/// [EN] Extension for Container to provide convenient AppDimens sizing.
/// [PT] Extensão para Container para fornecer dimensionamento conveniente do AppDimens.
extension AppDimensContainerExtension on Container {
  /// [EN] Applies fixed width to this container.
  /// @param width The width value.
  /// @param context The BuildContext.
  /// @return A new Container with the calculated width.
  /// [PT] Aplica largura fixa a este container.
  /// @param width O valor da largura.
  /// @param context O BuildContext.
  /// @return Um novo Container com a largura calculada.
  // NOTE: Container extensions commented out because Container doesn't expose
  // its properties for copying. Use SizedBox or BoxConstraints extensions instead.
  
  /* Container fxWidth(double width, BuildContext context) {
    // Container doesn't have a copyWith method and doesn't expose its properties
    // Use: SizedBox(width: width.fxdp(context), child: this)
    throw UnimplementedError('Use SizedBox with fxdp extension instead');
  }

  Container dyWidth(double width, BuildContext context) {
    // Container doesn't have a copyWith method and doesn't expose its properties
    // Use: SizedBox(width: width.dydp(context), child: this)
    throw UnimplementedError('Use SizedBox with dydp extension instead');
  }

  Container fxHeight(double height, BuildContext context) {
    // Container doesn't have a copyWith method and doesn't expose its properties
    // Use: SizedBox(height: height.fxdp(context), child: this)
    throw UnimplementedError('Use SizedBox with fxdp extension instead');
  }

  Container dyHeight(double height, BuildContext context) {
    // Container doesn't have a copyWith method and doesn't expose its properties
    // Use: SizedBox(height: height.dydp(context), child: this)
    throw UnimplementedError('Use SizedBox with dydp extension instead');
  } */
}
