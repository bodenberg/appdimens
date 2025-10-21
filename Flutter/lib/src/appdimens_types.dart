/**
 * Author & Developer: Jean Bodenberg
 * GIT: https://github.com/bodenberg/appdimens.git
 * Date: 2025-01-15
 *
 * Library: AppDimens Flutter - Types
 *
 * Description:
 * Core types and enums for the AppDimens library.
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

import 'dart:io';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

/// [EN] Device types for custom dimension values.
/// [PT] Tipos de dispositivos para valores de dimensão customizados.
enum DeviceType {
  phone,
  tablet,
  watch,
  tv,
  carPlay,
  desktop,
  foldable,
  unknown;

  /// [EN] Determines the device type based on the current platform and screen size.
  /// [PT] Determina o tipo de dispositivo baseado na plataforma atual e tamanho da tela.
  static DeviceType current(BuildContext context) {
    if (kIsWeb) {
      return _getWebDeviceType(context);
    }
    
    if (Platform.isAndroid || Platform.isIOS) {
      return _getMobileDeviceType(context);
    }
    
    if (Platform.isWindows || Platform.isMacOS || Platform.isLinux) {
      return DeviceType.desktop;
    }
    
    return DeviceType.unknown;
  }

  static DeviceType _getWebDeviceType(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    final devicePixelRatio = mediaQuery.devicePixelRatio;
    
    // Get the logical size
    final logicalWidth = size.width;
    final logicalHeight = size.height;
    
    if (logicalWidth < 600) {
      return DeviceType.phone;
    } else if (logicalWidth < 1024) {
      return DeviceType.tablet;
    } else {
      return DeviceType.desktop;
    }
  }

  static DeviceType _getMobileDeviceType(BuildContext context) {
    final mediaQuery = MediaQuery.of(context);
    final size = mediaQuery.size;
    
    // Use the smallest dimension to determine device type
    final smallestDimension = size.width < size.height ? size.width : size.height;
    
    if (smallestDimension < 600) {
      return DeviceType.phone;
    } else {
      return DeviceType.tablet;
    }
  }
}

/// [EN] UI mode types for custom dimension values.
/// [PT] Tipos de modo de UI para valores de dimensão customizados.
enum UiModeType {
  normal,
  carPlay,
  tv,
  watch,
  mac,
  unknown;

  /// [EN] Determines the UI mode type based on the current device and context.
  /// [PT] Determina o tipo de modo de UI baseado no dispositivo atual e contexto.
  static UiModeType current(BuildContext context) {
    final deviceType = DeviceType.current(context);
    
    switch (deviceType) {
      case DeviceType.carPlay:
        return UiModeType.carPlay;
      case DeviceType.tv:
        return UiModeType.tv;
      case DeviceType.watch:
        return UiModeType.watch;
      case DeviceType.desktop:
        return UiModeType.mac;
      default:
        return UiModeType.normal;
    }
  }
}

/// [EN] Screen qualifier types based on device dimensions.
/// [PT] Tipos de qualificador de tela baseados nas dimensões do dispositivo.
enum DpQualifier {
  smallestWidth160,
  smallestWidth240,
  smallestWidth320,
  smallestWidth360,
  smallestWidth480,
  smallestWidth600,
  smallestWidth720,
  smallestWidth840,
  smallestWidth960;
}

/// [EN] Represents a custom qualifier entry, combining the type and the minimum value
/// for the custom adjustment to be applied.
/// [PT] Representa uma entrada de qualificador customizado, combinando o tipo e o valor mínimo
/// para que o ajuste customizado seja aplicado.
class DpQualifierEntry {
  final DpQualifier type;
  final int value;

  const DpQualifierEntry({
    required this.type,
    required this.value,
  });

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is DpQualifierEntry &&
        other.type == type &&
        other.value == value;
  }

  @override
  int get hashCode => Object.hash(type, value);

  @override
  String toString() => 'DpQualifierEntry(type: $type, value: $value)';
}

/// [EN] Represents a qualifier entry that combines a UI Mode type AND a screen qualifier.
/// This combination has the HIGHEST PRIORITY.
/// [PT] Representa uma entrada de qualificador que combina um tipo de UI Mode E um qualificador de tela.
/// Esta combinação tem a PRIORIDADE MÁXIMA.
class UiModeQualifierEntry {
  final UiModeType uiModeType;
  final DpQualifierEntry dpQualifierEntry;

  const UiModeQualifierEntry({
    required this.uiModeType,
    required this.dpQualifierEntry,
  });

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    return other is UiModeQualifierEntry &&
        other.uiModeType == uiModeType &&
        other.dpQualifierEntry == dpQualifierEntry;
  }

  @override
  int get hashCode => Object.hash(uiModeType, dpQualifierEntry);

  @override
  String toString() => 'UiModeQualifierEntry(uiModeType: $uiModeType, dpQualifierEntry: $dpQualifierEntry)';
}

/// [EN] Defines which screen dimension (width or height) should be used
/// as the basis for dynamic and percentage-based sizing calculations.
/// [PT] Define qual dimensão da tela (largura ou altura) deve ser usada
/// como base para cálculos de dimensionamento dinâmico e percentual.
enum ScreenType {
  lowest,
  highest;
}

/// [EN] Screen qualifier types based on screen dimensions.
/// [PT] Tipos de qualificador de tela baseados nas dimensões da tela.
enum ScreenQualifier {
  w320h240,
  w640h480,
  w800h600,
  w1024h768,
  w1280h800,
  w1440h900,
  w1600h900,
  w1920h1080;
}

/// [EN] Defines the supported physical measurement units for conversion
/// into logical pixels.
/// [PT] Define as unidades de medida física suportadas para conversão
/// em pixels lógicos.
enum UnitType {
  inch,
  cm,
  mm,
  sp,
  pt,
  px;
}

/// [EN] Screen information containing dimensions and properties.
/// [PT] Informações da tela contendo dimensões e propriedades.
class ScreenInfo {
  final double width;
  final double height;
  final double devicePixelRatio;
  final DeviceType deviceType;
  final UiModeType uiModeType;
  final Orientation orientation;

  const ScreenInfo({
    required this.width,
    required this.height,
    required this.devicePixelRatio,
    required this.deviceType,
    required this.uiModeType,
    required this.orientation,
  });

  /// [EN] Gets the smallest dimension.
  /// [PT] Obtém a menor dimensão.
  double get smallestDimension => width < height ? width : height;

  /// [EN] Gets the largest dimension.
  /// [PT] Obtém a maior dimensão.
  double get largestDimension => width > height ? width : height;

  /// [EN] Calculates the aspect ratio.
  /// [PT] Calcula a proporção da tela.
  double get aspectRatio => width / height;

  @override
  String toString() => 'ScreenInfo(width: $width, height: $height, devicePixelRatio: $devicePixelRatio, deviceType: $deviceType, uiModeType: $uiModeType, orientation: $orientation)';
}

/// [EN] Stores the adjustment factors calculated from the screen dimensions.
/// The Aspect Ratio (AR) calculation is performed only once per screen configuration.
/// [PT] Armazena os fatores de ajuste calculados a partir das dimensões da tela.
/// O cálculo do Aspect Ratio (AR) é feito apenas uma vez por configuração de tela.
class ScreenAdjustmentFactors {
  /// [EN] The final and COMPLETE scaling factor, using the LOWEST base (smallest dimension) + AR.
  /// [PT] Fator de escala final e COMPLETO, usando a base LOWEST (menor dimensão) + AR.
  final double withArFactorLowest;

  /// [EN] The final and COMPLETE scaling factor, using the HIGHEST base (largest dimension) + AR.
  /// [PT] Fator de escala final e COMPLETO, usando a base HIGHEST (maior dimensão) + AR.
  final double withArFactorHighest;

  /// [EN] The final scaling factor WITHOUT AR (uses the LOWEST base for safety).
  /// [PT] Fator de escala final SEM AR (Usa a base LOWEST por segurança).
  final double withoutArFactor;

  /// [EN] The base adjustment factor (increment multiplier), LOWEST: smallest dimension.
  /// [PT] Fator base de ajuste (multiplicador do incremento), LOWEST: menor dimensão.
  final double adjustmentFactorLowest;

  /// [EN] The base adjustment factor (increment multiplier), HIGHEST: max(W, H).
  /// [PT] Fator base de ajuste (multiplicador do incremento), HIGHEST: max(W, H).
  final double adjustmentFactorHighest;

  const ScreenAdjustmentFactors({
    required this.withArFactorLowest,
    required this.withArFactorHighest,
    required this.withoutArFactor,
    required this.adjustmentFactorLowest,
    required this.adjustmentFactorHighest,
  });

  @override
  String toString() => 'ScreenAdjustmentFactors(withArFactorLowest: $withArFactorLowest, withArFactorHighest: $withArFactorHighest, withoutArFactor: $withoutArFactor, adjustmentFactorLowest: $adjustmentFactorLowest, adjustmentFactorHighest: $adjustmentFactorHighest)';
}
